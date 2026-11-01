package com.miui.aod;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.doze.SettingsHolder;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.util.GsonUtils;
import com.miui.aod.widget.AODSettings;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AodViewManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AodViewManager extends SettingsHolder.AodSettingsChangeListener {
    public static AODView aodView;
    public static int cachedDensityDpi;
    public static boolean cachedDualClock;
    public static float cachedFontScale;
    public static String cachedLocale;
    public static int cachedSmallestScreenWidthDp;
    public static String cachedStyleJson;
    public static String cachedTemplateInfo;
    public static final AodViewManager INSTANCE = new AodViewManager();
    public static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public static final void onChange$lambda$1$lambda$0() {
        INSTANCE.clearAodViewCache();
    }

    public final void clearAodViewCache() {
        aodView = null;
        cachedStyleJson = null;
        cachedDualClock = false;
        cachedTemplateInfo = null;
        cachedLocale = null;
        cachedDensityDpi = 0;
        cachedFontScale = 0.0f;
        cachedSmallestScreenWidthDp = 0;
    }

    public final AODView createAodView(Context context, DozeHost dozeHost) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.aod_mode_layout, (ViewGroup) null);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.aod.AODView");
        AODView aODView = (AODView) viewInflate;
        aodView = aODView;
        if (aODView != null) {
            aODView.setDozeHost(dozeHost);
        }
        AODView aODView2 = aodView;
        if (aODView2 != null) {
            aODView2.onInflateComplete();
        }
        saveConfigInfo(context);
        AODView aODView3 = aodView;
        Intrinsics.checkNotNull(aODView3);
        return aODView3;
    }

    public final AODView findAodView(Context context, DozeHost host) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(host, "host");
        AODView lastAodView = getLastAodView(context);
        if (lastAodView == null) {
            DrawableCache.getInstance(context).releaseBitmapCache();
            return createAodView(context, host);
        }
        resetAodView(lastAodView, host);
        return lastAodView;
    }

    public final AODView getLastAodView(Context context) {
        if (aodView == null) {
            return null;
        }
        if (!isCacheInvalid(context)) {
            return aodView;
        }
        clearAodViewCache();
        return null;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SettingsHolder.getInstance(context).addListener(this);
    }

    public final boolean isCacheInvalid(Context context) {
        if (cachedDualClock == AODSettings.isDualClock(context) && TextUtils.equals(GsonUtils.toJson(AODStyleController.getStyleInfo(context)), cachedStyleJson)) {
            return (Intrinsics.areEqual(CategoryConstants.CATEGORY_LINKAGE, Utils.getAodUsingCategoryName(context)) && !TextUtils.equals(Utils.getCurrentTemplateInfo(), cachedTemplateInfo)) || isConfigChanged(context);
        }
        return true;
    }

    public final boolean isConfigChanged(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        return isConfigChanged(configuration);
    }

    public final boolean isConfigChanged(Configuration configuration) {
        Locale locale = configuration.getLocales().get(0);
        String string = locale != null ? locale.toString() : null;
        int i = configuration.densityDpi;
        float f = configuration.fontScale;
        int i2 = configuration.smallestScreenWidthDp;
        String str = cachedLocale;
        boolean z = (str == null || Intrinsics.areEqual(str, string)) ? false : true;
        int i3 = cachedDensityDpi;
        boolean z2 = (i3 == 0 || i3 == i) ? false : true;
        float f2 = cachedFontScale;
        boolean z3 = (f2 == 0.0f || f2 == f) ? false : true;
        int i4 = cachedSmallestScreenWidthDp;
        return z || z2 || z3 || (i4 != 0 && i4 != i2);
    }

    @Override // com.miui.aod.doze.SettingsHolder.AodSettingsChangeListener
    public void onChange(Uri uri) {
        String string;
        if (uri == null || (string = uri.toString()) == null || !INSTANCE.shouldClearCache(string)) {
            return;
        }
        mainHandler.post(new Runnable() { // from class: com.miui.aod.AodViewManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AodViewManager.onChange$lambda$1$lambda$0();
            }
        });
    }

    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        if (aodView != null && isConfigChanged(newConfig)) {
            clearAodViewCache();
        }
    }

    @Override // com.miui.aod.doze.SettingsHolder.AodSettingsChangeListener
    public void onUserSwitched() {
        Log.i("AodViewManager", "user switched, clear aod view cache");
        clearAodViewCache();
    }

    public final void resetAodView(AODView aODView, DozeHost dozeHost) {
        aODView.setBackgroundColor(-16777216);
        aODView.setDozeHost(dozeHost);
        aODView.onReuseAodView();
    }

    public final void saveConfigInfo(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        Locale locale = configuration.getLocales().get(0);
        cachedLocale = locale != null ? locale.toString() : null;
        cachedDensityDpi = configuration.densityDpi;
        cachedFontScale = configuration.fontScale;
        cachedSmallestScreenWidthDp = configuration.smallestScreenWidthDp;
        AODView aODView = aodView;
        StyleInfo styleInfo = aODView != null ? aODView.getStyleInfo() : null;
        cachedStyleJson = GsonUtils.toJson(styleInfo);
        cachedDualClock = AODSettings.isDualClock(context);
        cachedTemplateInfo = styleInfo instanceof LinkageClockCategoryInfo ? Utils.getCurrentTemplateInfo() : null;
    }

    public final boolean shouldClearCache(String str) {
        String str2;
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) "system_locales", false, 2, (Object) null) || (str2 = SettingsHolder.mCategoryName) == null || str2.length() == 0) {
            return true;
        }
        String mCategoryName = SettingsHolder.mCategoryName;
        Intrinsics.checkNotNullExpressionValue(mCategoryName, "mCategoryName");
        return StringsKt.contains$default((CharSequence) str, (CharSequence) mCategoryName, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "aod_category_name", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "constant_template_editor_info", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "auto_dual_clock", false, 2, (Object) null);
    }
}

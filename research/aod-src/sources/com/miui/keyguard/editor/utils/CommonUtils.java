package com.miui.keyguard.editor.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.segment.UtilsKt;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.miwallpaper.material.utils.MiuiWallpaperColors;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miui.os.Build;
import miuix.core.util.SystemProperties;
import miuix.os.DeviceHelper;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: CommonUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class CommonUtils {
    public static ActivityManager activityManager;
    public static final CommonUtils INSTANCE = new CommonUtils();
    public static final String TAG = "CommonUtils";
    public static Pair[] darkCache = new Pair[0];

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.CommonUtils$obtainHomeLayoutDrawable$1, reason: invalid class name */
    /* JADX INFO: compiled from: CommonUtils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.CommonUtils$obtainHomeLayoutDrawable$1", f = "CommonUtils.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ Ref.IntRef $drawable;
        public final /* synthetic */ boolean $isDark;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, Ref.IntRef intRef, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$drawable = intRef;
            this.$isDark = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$context, this.$drawable, this.$isDark, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Object objRunSyncWithTimeOut = UtilsKt.runSyncWithTimeOut(Dispatchers.getIO(), 100L, Boxing.boxBoolean(true), new CommonUtils$obtainHomeLayoutDrawable$1$columnNums$1(this.$context, null));
            if (objRunSyncWithTimeOut != null) {
                Ref.IntRef intRef = this.$drawable;
                boolean z = this.$isDark;
                Log.d(CommonUtils.TAG, "column number is " + objRunSyncWithTimeOut);
                if (Intrinsics.areEqual(objRunSyncWithTimeOut, Boxing.boxInt(5))) {
                    Log.d(CommonUtils.TAG, "obtainHomeLayoutDrawable end2");
                    intRef.element = !z ? R.drawable.kg_miui_home_desktop_five_black : R.drawable.kg_miui_home_desktop_five_white;
                    return Unit.INSTANCE;
                }
            }
            Log.d(CommonUtils.TAG, "obtainHomeLayoutDrawable end1");
            return Unit.INSTANCE;
        }
    }

    public final boolean activityVisible(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        return activity.getWindow().getDecorView().getVisibility() == 0;
    }

    public final boolean checkThemeSupportDownload(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (!Build.IS_INTERNATIONAL_BUILD) {
            return true;
        }
        ThemeProperty themeProperty = ThemeProperty.INSTANCE;
        if (themeProperty.isSupportDownload() != null) {
            Log.d(TAG, "checkThemeSupportDownload: cache is " + themeProperty.isSupportDownload());
            Boolean boolIsSupportDownload = themeProperty.isSupportDownload();
            Intrinsics.checkNotNull(boolIsSupportDownload);
            return boolIsSupportDownload.booleanValue();
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Bundle bundleCall = context.getContentResolver().call(Uri.parse("content://com.android.thememanager.theme_provider"), "isSupportDownload", (String) null, (Bundle) null);
            Boolean boolValueOf = bundleCall != null ? Boolean.valueOf(bundleCall.getBoolean("support")) : null;
            Log.d(TAG, "checkThemeSupportDownload: " + boolValueOf + ",consume time is " + (System.currentTimeMillis() - jCurrentTimeMillis));
            if (boolValueOf != null) {
                return boolValueOf.booleanValue();
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "checkThemeSupportDownload failed " + e);
            return false;
        }
    }

    public final int detectType() {
        if (miuix.os.Build.IS_FOLD_INSIDE) {
            return 3;
        }
        if (miuix.os.Build.IS_FLIP) {
            return 4;
        }
        if (miuix.os.Build.IS_FOLD_OUTSIDE) {
            return 5;
        }
        if (miuix.os.Build.IS_TABLET) {
            return 2;
        }
        return miuix.os.Build.IS_AUTOMOTIVE ? 11 : 1;
    }

    public final int getDeskColorBlockForColumn(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            if (deviceUtil.isDisplayLandScape(context)) {
                return !z ? R.drawable.kg_miui_home_desktop_black_sw711dp_land : R.drawable.kg_miui_home_desktop_white_sw711dp_land;
            }
            return !z ? R.drawable.kg_miui_home_desktop_black_sw711dp_port : R.drawable.kg_miui_home_desktop_white_sw711dp_port;
        }
        if (!deviceUtil.isFold()) {
            return !z ? R.drawable.kg_miui_home_desktop_black_sw392dp : R.drawable.kg_miui_home_desktop_white_sw392dp;
        }
        if (deviceUtil.isQ18()) {
            if (deviceUtil.isDisplayLandScape(context)) {
                return !z ? R.drawable.kg_miui_home_desktop_black_sw608dp_land : R.drawable.kg_miui_home_desktop_white_sw608dp_land;
            }
            if (deviceUtil.isLargeDisplay(context)) {
                return !z ? R.drawable.kg_miui_home_desktop_black_sw608dp_port : R.drawable.kg_miui_home_desktop_white_sw608dp_port;
            }
            return !z ? R.drawable.kg_miui_home_desktop_black_sw424dp : R.drawable.kg_miui_home_desktop_white_sw424dp;
        }
        if (deviceUtil.isDisplayLandScape(context)) {
            return !z ? R.drawable.kg_miui_home_desktop_black_sw696dp_land : R.drawable.kg_miui_home_desktop_white_sw696dp_land;
        }
        if (deviceUtil.isLargeDisplay(context)) {
            return !z ? R.drawable.kg_miui_home_desktop_black_sw696dp_port : R.drawable.kg_miui_home_desktop_white_sw696dp_port;
        }
        return !z ? R.drawable.kg_miui_home_desktop_black_sw392dp : R.drawable.kg_miui_home_desktop_white_sw392dp;
    }

    public final boolean isActivityPinned(Context context) {
        ActivityManager activityManager2;
        if (context == null) {
            return false;
        }
        if (activityManager == null) {
            if (context instanceof Activity) {
                Object systemService = ((Activity) context).getApplicationContext().getSystemService("activity");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
                activityManager2 = (ActivityManager) systemService;
            } else {
                Object systemService2 = context.getSystemService("activity");
                Intrinsics.checkNotNull(systemService2, "null cannot be cast to non-null type android.app.ActivityManager");
                activityManager2 = (ActivityManager) systemService2;
            }
            activityManager = activityManager2;
        }
        ActivityManager activityManager3 = activityManager;
        return activityManager3 != null && activityManager3.getLockTaskModeState() == 2;
    }

    public final synchronized boolean isDarkWallpaper(Bitmap bitmap) {
        if (bitmap == null) {
            return false;
        }
        for (Pair pair : darkCache) {
            WeakReference weakReference = (WeakReference) pair.component1();
            boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
            if (weakReference.get() == bitmap) {
                return zBooleanValue;
            }
        }
        boolean z = true;
        if ((MiuiWallpaperColors.generateWallpaperColors(bitmap).getColorHints() & 1) != 0) {
            z = false;
        }
        Pair[] pairArr = darkCache;
        darkCache = pairArr.length == 0 ? new Pair[]{new Pair(new WeakReference(bitmap), Boolean.valueOf(z))} : new Pair[]{new Pair(new WeakReference(bitmap), Boolean.valueOf(z)), pairArr[0]};
        return z;
    }

    public final boolean isFlipDevice(int i) {
        return i == 4;
    }

    public final boolean isFlipOrFoldDevice() {
        int iDetectType = detectType();
        return isFlipDevice(iDetectType) || isFoldDevice(iDetectType);
    }

    public final boolean isFoldDevice(int i) {
        return i == 3;
    }

    public final boolean isPhoneDevice(int i) {
        return i == 1;
    }

    public final boolean isPhoneOrFlipDevice(int i) {
        return isPhoneDevice(i) || isFlipDevice(i);
    }

    public final boolean isRuRegion() {
        return StringsKt.equals("RU", SystemProperties.get("ro.miui.build.region", "cn"), true);
    }

    public final boolean isTabletDevice(int i) {
        return i == 2;
    }

    public final Integer obtainHomeColumnNums(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            String str = SystemProperties.get("ro.product.brand", "");
            String str2 = StringsKt.equals("Poco", str, true) ? "content://com.mi.android.globallauncher.launcher.settings" : "content://com.miui.home.launcher.settings";
            Bundle bundleCall = context.getContentResolver().call(Uri.parse(str2), "getRowColumnNums", (String) null, (Bundle) null);
            Integer numValueOf = bundleCall != null ? Integer.valueOf(bundleCall.getInt("COLUMN_NUMS")) : null;
            Log.d(TAG, "obtainHomeColumnNums: " + numValueOf + ",product brand is " + str + ",author is " + str2 + ",consume time is " + (System.currentTimeMillis() - jCurrentTimeMillis));
            return numValueOf;
        } catch (Exception e) {
            Log.e(TAG, "obtainHomeColumnNums failed " + e);
            return 0;
        }
    }

    public final int obtainHomeLayoutDrawable(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad() || deviceUtil.isFold()) {
            return getDeskColorBlockForColumn(context, z);
        }
        String str = TAG;
        Log.d(str, "obtainHomeLayoutDrawable start,dark is " + z);
        Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = !z ? R.drawable.kg_miui_home_desktop_black : R.drawable.kg_miui_home_desktop_white;
        if (DeviceHelper.detectType(context) != 1 && DeviceHelper.detectType(context) != 4) {
            Log.d(str, "obtainHomeLayoutDrawable not phone");
            return intRef.element;
        }
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new AnonymousClass1(context, intRef, z, null), 3, null);
        Log.d(str, "obtainHomeLayoutDrawable end");
        return intRef.element;
    }

    public final float parseFloat(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        if (TextUtils.isEmpty(str)) {
            return -1.0f;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception unused) {
            return -1.0f;
        }
    }

    public final int parseInt(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return -1;
        }
    }
}

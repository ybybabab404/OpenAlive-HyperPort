package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.utils.PickWallpaperColorInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: TemplateApi.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface TemplateApi {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: TemplateApi.kt */
    @Metadata
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static volatile TemplateApi INSTANCE;

        public final TemplateApi getInstance(Context context) {
            TemplateApi templateApiImpl;
            Intrinsics.checkNotNullParameter(context, "context");
            TemplateApi templateApi = INSTANCE;
            if (templateApi != null) {
                return templateApi;
            }
            synchronized (this) {
                templateApiImpl = INSTANCE;
                if (templateApiImpl == null) {
                    Context applicationContext = context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                    templateApiImpl = new TemplateApiImpl(applicationContext);
                    INSTANCE = templateApiImpl;
                }
            }
            return templateApiImpl;
        }
    }

    /* JADX INFO: compiled from: TemplateApi.kt */
    @Metadata
    public static final class DefaultImpls {
        public static /* synthetic */ CommonConfig currentTemplate$default(TemplateApi templateApi, boolean z, boolean z2, boolean z3, boolean z4, WallpaperTypeInfo wallpaperTypeInfo, boolean z5, String str, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: currentTemplate");
            }
            if ((i & 1) != 0) {
                z = false;
            }
            if ((i & 2) != 0) {
                z2 = false;
            }
            if ((i & 4) != 0) {
                z3 = false;
            }
            if ((i & 8) != 0) {
                z4 = true;
            }
            if ((i & 16) != 0) {
                wallpaperTypeInfo = null;
            }
            if ((i & 32) != 0) {
                z5 = false;
            }
            if ((i & 64) != 0) {
                str = "all";
            }
            return templateApi.currentTemplate(z, z2, z3, z4, wallpaperTypeInfo, z5, str);
        }

        public static /* synthetic */ boolean deleteHistoryTemplate$default(TemplateApi templateApi, TemplateHistoryConfig templateHistoryConfig, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: deleteHistoryTemplate");
            }
            if ((i & 2) != 0) {
                z = true;
            }
            if ((i & 4) != 0) {
                z2 = false;
            }
            return templateApi.deleteHistoryTemplate(templateHistoryConfig, z, z2);
        }

        public static /* synthetic */ CommonConfig getCurrentTemplateConfigInternal$default(TemplateApi templateApi, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getCurrentTemplateConfigInternal");
            }
            if ((i & 1) != 0) {
                z = true;
            }
            return templateApi.getCurrentTemplateConfigInternal(z);
        }

        public static /* synthetic */ List getHistoryTemplates$default(TemplateApi templateApi, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getHistoryTemplates");
            }
            if ((i & 1) != 0) {
                z = false;
            }
            return templateApi.getHistoryTemplates(z);
        }

        public static /* synthetic */ Bitmap getLosslessTemplateBitmap$default(TemplateApi templateApi, String str, boolean z, int i, int i2, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getLosslessTemplateBitmap");
            }
            if ((i3 & 4) != 0) {
                i = -1;
            }
            if ((i3 & 8) != 0) {
                i2 = -1;
            }
            return templateApi.getLosslessTemplateBitmap(str, z, i, i2);
        }

        public static /* synthetic */ boolean isThirdPartyWallpaper$default(TemplateApi templateApi, WallpaperWhich wallpaperWhich, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: isThirdPartyWallpaper");
            }
            if ((i & 1) != 0) {
                wallpaperWhich = WallpaperWhich.LockWhich.INSTANCE;
            }
            return templateApi.isThirdPartyWallpaper(wallpaperWhich);
        }

        public static /* synthetic */ WallpaperSource requireLockScreenWallpaper$default(TemplateApi templateApi, CommonConfig commonConfig, int i, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: requireLockScreenWallpaper");
            }
            if ((i2 & 2) != 0) {
                i = 0;
            }
            return templateApi.requireLockScreenWallpaper(commonConfig, i);
        }

        public static /* synthetic */ void updateCurrentTemplate$default(TemplateApi templateApi, CommonConfig commonConfig, String str, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateCurrentTemplate");
            }
            if ((i & 4) != 0) {
                z = true;
            }
            if ((i & 8) != 0) {
                z2 = true;
            }
            templateApi.updateCurrentTemplate(commonConfig, str, z, z2);
        }
    }

    Object applyEditPageTemplate(CommonConfig commonConfig, ScreenshotSource screenshotSource, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, Continuation continuation);

    Object applyHomePageTemplate(String str, boolean z, ScreenshotSource screenshotSource, long j, boolean z2, boolean z3, int i, CommonConfig commonConfig, int i2, Continuation continuation);

    void clearHistoryTemplate();

    Bitmap cropBitmap(Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, boolean z);

    Bitmap currentOriginSubject(CommonConfig commonConfig);

    CommonConfig currentTemplate(boolean z, boolean z2, boolean z3, boolean z4, WallpaperTypeInfo wallpaperTypeInfo, boolean z5, String str);

    String currentWallPaperPath(WallpaperInfo wallpaperInfo);

    boolean deleteHistoryTemplate(TemplateHistoryConfig templateHistoryConfig, boolean z, boolean z2);

    void enableGetCurrentLockWallpaperFromWM();

    Bitmap getCurrentHomeScreenShot();

    Bitmap getCurrentLockScreenShot();

    CommonConfig getCurrentTemplateConfigInternal(boolean z);

    String getCurrentTemplateWallpaperPath();

    Bitmap getCurrentWallpaper(WallpaperInfo wallpaperInfo);

    TemplateHistoryConfig getHistoryTemplate(long j);

    List getHistoryTemplates(boolean z);

    Bitmap getLosslessTemplateBitmap(String str, boolean z, int i, int i2);

    PresetTemplateConfig getPresetTemplate(Context context, TemplateItemConfig templateItemConfig, boolean z, int i, int i2, int i3);

    List getPresetTemplateGroup();

    Bitmap getPreviewCache(String str);

    CommonConfig getTemplate(String str, boolean z, int i);

    Drawable getTemplateLoadingDrawable(int i);

    void insertHistoryConfig(TemplateHistoryConfig templateHistoryConfig);

    boolean isHomeSetPkgSupportDepth();

    boolean isLockSetPkgSupportDepth();

    boolean isMiuiSetLockWallpaper();

    boolean isThirdPartyTheme();

    boolean isThirdPartyWallpaper(WallpaperWhich wallpaperWhich);

    boolean isWallpaperFileChanged(CommonConfig commonConfig, CommonConfig commonConfig2);

    void loadTemplateWallpaper(WallpaperInfo wallpaperInfo, boolean z);

    boolean putPreviewCache(String str, Bitmap bitmap);

    String readTemplateInfo();

    void reapplyCurrentWallpaper(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, PickWallpaperColorInfo pickWallpaperColorInfo);

    CommonConfig requireLockScreenInfoBySettings();

    WallpaperSource requireLockScreenWallpaper(CommonConfig commonConfig, int i);

    void requireOriginWallpaperForScreenshot(WallpaperInfo wallpaperInfo, String str);

    Bitmap requireThirdPartyThemePreviewImage(int i, int i2);

    void saveCurrentTemplateScreenshot(ScreenshotSource screenshotSource);

    boolean shouldSubjectWithBackgroundColor(CommonConfig commonConfig);

    int tryClearLocalInvalidWallpaper();

    Bitmap tryComposeSubjectWithBackgroundColor(CommonConfig commonConfig, Bitmap bitmap);

    Bitmap tryComposeSubjectWithBackgroundColor(CommonConfig commonConfig, String str);

    void updateCurrentTemplate(CommonConfig commonConfig, String str, boolean z, boolean z2);

    void writeTemplateInfo(String str, String str2);
}

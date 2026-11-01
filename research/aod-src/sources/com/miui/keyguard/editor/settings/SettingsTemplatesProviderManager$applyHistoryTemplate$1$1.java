package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.SettingsEditorServiceManager;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.model.CrossListPreloader;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.SharedPreferencesUtil;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SettingsTemplatesProviderManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplatesProviderManager$applyHistoryTemplate$1$1", f = "SettingsTemplatesProviderManager.kt", l = {176}, m = "invokeSuspend")
public final class SettingsTemplatesProviderManager$applyHistoryTemplate$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Bundle $extras;
    public final /* synthetic */ Context $it;
    public final /* synthetic */ Bundle $result;
    public Object L$0;
    public Object L$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsTemplatesProviderManager$applyHistoryTemplate$1$1(Bundle bundle, Context context, Bundle bundle2, Continuation continuation) {
        super(2, continuation);
        this.$extras = bundle;
        this.$it = context;
        this.$result = bundle2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingsTemplatesProviderManager$applyHistoryTemplate$1$1(this.$extras, this.$it, this.$result, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SettingsTemplatesProviderManager$applyHistoryTemplate$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String string;
        Bitmap bitmapFromFile$default;
        Bundle bundle;
        Object objApplyHomePageTemplate;
        Context context;
        CrossListPreloader crossListPreloader;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Bundle bundle2 = this.$extras;
            if (bundle2 == null || (string = bundle2.getString("configUri")) == null) {
                string = "";
            }
            String str = string;
            Bundle bundle3 = this.$extras;
            int i2 = bundle3 != null ? bundle3.getInt("hideFlag") : 0;
            Bundle bundle4 = this.$extras;
            long j = bundle4 != null ? bundle4.getLong("entityId") : 0L;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            int screenStatus = deviceUtil.getScreenStatus(this.$it);
            Bundle bundle5 = this.$extras;
            boolean z = bundle5 != null ? bundle5.getBoolean("shouldApplyToAll") : true;
            CommonConfig templateConfig = TemplateDataUtil.getTemplateConfig(this.$it, str, false, i2);
            if (templateConfig == null) {
                return null;
            }
            Context context2 = this.$it;
            Bundle bundle6 = this.$result;
            WallpaperInfo wallpaperInfo = templateConfig.getLockscreenInfo().getWallpaperInfo();
            if (DeviceUtil.isHomeNeedUseOriginalEffect$default(deviceUtil, WallpaperController.Companion.getInstance(context2), null, wallpaperInfo != null ? wallpaperInfo.getMagicType() : 0, 2, null)) {
                BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
                WallpaperInfo wallpaperInfo2 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                bitmapFromFile$default = BitmapUtil.fromFile$default(bitmapUtil, wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null, 0, 0, null, 14, null);
            } else {
                bitmapFromFile$default = null;
            }
            TemplateApi.Companion companion = TemplateApi.Companion;
            TemplateHistoryConfig historyTemplate = companion.getInstance(context2).getHistoryTemplate(j);
            BitmapUtil bitmapUtil2 = BitmapUtil.INSTANCE;
            Bitmap bitmapFromFile$default2 = BitmapUtil.fromFile$default(bitmapUtil2, historyTemplate != null ? historyTemplate.getSmallScreenshotUri() : null, 0, 0, null, 14, null);
            Bitmap bitmapFromFile$default3 = BitmapUtil.fromFile$default(bitmapUtil2, historyTemplate != null ? historyTemplate.getLargePortraitScreenshotUri() : null, 0, 0, null, 14, null);
            Bitmap bitmapFromFile$default4 = BitmapUtil.fromFile$default(bitmapUtil2, historyTemplate != null ? historyTemplate.getLargeLandscapeScreenshotUri() : null, 0, 0, null, 14, null);
            Bitmap bitmapFromFile$default5 = BitmapUtil.fromFile$default(bitmapUtil2, historyTemplate != null ? historyTemplate.getSmallWallpaperScreenshotUri() : null, 0, 0, null, 14, null);
            Bitmap bitmapFromFile$default6 = BitmapUtil.fromFile$default(bitmapUtil2, historyTemplate != null ? historyTemplate.getLargePortWallpaperScreenshotUri() : null, 0, 0, null, 14, null);
            Bitmap bitmapFromFile$default7 = BitmapUtil.fromFile$default(bitmapUtil2, historyTemplate != null ? historyTemplate.getLargeLandWallpaperScreenshotUri() : null, 0, 0, null, 14, null);
            ScreenshotSource screenshotSource = new ScreenshotSource(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 262143, null);
            boolean z2 = z;
            screenshotSource.setSmallScreenshot(bitmapFromFile$default2);
            screenshotSource.setLargePortraitScreenshot(bitmapFromFile$default3);
            screenshotSource.setLargeLandscapeScreenshot(bitmapFromFile$default4);
            screenshotSource.setSmallWallpaperScreenshot(bitmapFromFile$default5);
            screenshotSource.setLargePortWallpaperScreenshot(bitmapFromFile$default6);
            screenshotSource.setLargeLandWallpaperScreenshot(bitmapFromFile$default7);
            screenshotSource.setHistoryWallpaper(bitmapFromFile$default);
            screenshotSource.setHistorySmallScreenshotUri(historyTemplate != null ? historyTemplate.getSmallScreenshotUri() : null);
            screenshotSource.setHistoryLargePortraitScreenshotUri(historyTemplate != null ? historyTemplate.getLargePortraitScreenshotUri() : null);
            screenshotSource.setHistoryLargeLandscapeScreenshotUri(historyTemplate != null ? historyTemplate.getLargeLandscapeScreenshotUri() : null);
            screenshotSource.setHistorySmallWallpaperScreenshotUri(historyTemplate != null ? historyTemplate.getSmallWallpaperScreenshotUri() : null);
            screenshotSource.setHistoryLargePortWallpaperScreenshotUri(historyTemplate != null ? historyTemplate.getLargePortWallpaperScreenshotUri() : null);
            screenshotSource.setHistoryLargeLandWallpaperScreenshotUri(historyTemplate != null ? historyTemplate.getLargeLandWallpaperScreenshotUri() : null);
            FashionGalleryHelper.INSTANCE.setCLOSE_SOURCE(2);
            TemplateApi companion2 = companion.getInstance(context2);
            this.L$0 = context2;
            this.L$1 = bundle6;
            this.label = 1;
            bundle = bundle6;
            objApplyHomePageTemplate = companion2.applyHomePageTemplate(str, false, screenshotSource, j, z2, false, screenStatus, templateConfig, i2, this);
            if (objApplyHomePageTemplate == coroutine_suspended) {
                return coroutine_suspended;
            }
            context = context2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Bundle bundle7 = (Bundle) this.L$1;
            context = (Context) this.L$0;
            ResultKt.throwOnFailure(obj);
            bundle = bundle7;
            objApplyHomePageTemplate = obj;
        }
        boolean zBooleanValue = ((Boolean) objApplyHomePageTemplate).booleanValue();
        if (zBooleanValue && (crossListPreloader = EditorApplicationProxy.Companion.getCrossListPreloader()) != null) {
            Log.i("SettingsTemplatesProviderManager", "deleteHistoryTemplate: clear preloader");
            crossListPreloader.takeAllHistoryTemplates();
        }
        SharedPreferencesUtil.INSTANCE.setBoolean(context, "has_screenshot_for_external_template", true);
        SettingsEditorServiceManager.INSTANCE.updateCrossPager();
        Log.d("SettingsTemplatesProviderManager", "CALL_APPLY_HISTORY_TEMPLATE applyResult: " + zBooleanValue);
        bundle.putBoolean("resultApplyHistoryTemplate", zBooleanValue);
        SettingsTemplatesProviderManager.INSTANCE.setApplying(false);
        DeviceScreenshotHelper.INSTANCE.setFirstScreenshotFinished(false);
        return Unit.INSTANCE;
    }
}

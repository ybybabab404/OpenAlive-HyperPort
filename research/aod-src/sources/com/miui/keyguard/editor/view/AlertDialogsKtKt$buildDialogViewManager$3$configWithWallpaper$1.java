package com.miui.keyguard.editor.view;

import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.utils.Wallpaper;
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
/* JADX INFO: compiled from: AlertDialogsKt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.view.AlertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1", f = "AlertDialogsKt.kt", l = {}, m = "invokeSuspend")
public final class AlertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super CommonConfig>, Object> {
    public final /* synthetic */ TemplateApi $templateApi;
    public final /* synthetic */ CommonConfig $templateConfig;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1(CommonConfig commonConfig, TemplateApi templateApi, Continuation continuation) {
        super(2, continuation);
        this.$templateConfig = commonConfig;
        this.$templateApi = templateApi;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AlertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1(this.$templateConfig, this.$templateApi, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AlertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        WallpaperInfo wallpaperInfo;
        SensorWallpaperPrams sensorWallpaperPrams;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommonConfig commonConfig = this.$templateConfig;
        String smallThumbnailPath = null;
        if (commonConfig == null) {
            return null;
        }
        TemplateApi templateApi = this.$templateApi;
        WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo2 != null) {
            WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            wallpaperInfo2.setCurrentWallpaper(TemplateApi.DefaultImpls.getLosslessTemplateBitmap$default(templateApi, wallpaperInfo3 != null ? wallpaperInfo3.getSource() : null, false, 0, 0, 12, null));
        }
        Wallpaper.Companion companion = Wallpaper.Companion;
        WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (companion.isSensorWallpaper(wallpaperInfo4 != null ? wallpaperInfo4.getResourceType() : null) && (wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo()) != null) {
            WallpaperInfo wallpaperInfo5 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo5 != null && (sensorWallpaperPrams = wallpaperInfo5.getSensorWallpaperPrams()) != null) {
                smallThumbnailPath = sensorWallpaperPrams.getSmallThumbnailPath();
            }
            wallpaperInfo.setCurrentSmallSensorWallpaper(TemplateApi.DefaultImpls.getLosslessTemplateBitmap$default(templateApi, smallThumbnailPath, false, 0, 0, 12, null));
        }
        return commonConfig;
    }
}

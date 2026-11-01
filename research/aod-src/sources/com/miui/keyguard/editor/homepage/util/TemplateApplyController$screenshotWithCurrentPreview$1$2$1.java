package com.miui.keyguard.editor.homepage.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
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
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$2$1", f = "TemplateApplyController.kt", l = {}, m = "invokeSuspend")
public final class TemplateApplyController$screenshotWithCurrentPreview$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
    public final /* synthetic */ PresetTemplateConfig $currentPreviewTemplate;
    public final /* synthetic */ Point $it;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$screenshotWithCurrentPreview$1$2$1(PresetTemplateConfig presetTemplateConfig, Point point, TemplateApplyController templateApplyController, Continuation continuation) {
        super(2, continuation);
        this.$currentPreviewTemplate = presetTemplateConfig;
        this.$it = point;
        this.this$0 = templateApplyController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplyController$screenshotWithCurrentPreview$1$2$1(this.$currentPreviewTemplate, this.$it, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplyController$screenshotWithCurrentPreview$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        SensorWallpaperPrams sensorWallpaperPrams;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommonConfig accessConfig = this.$currentPreviewTemplate.getAccessConfig();
        String thumbnailPath = (accessConfig == null || (lockscreenInfo = accessConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || (sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams()) == null) ? null : sensorWallpaperPrams.getThumbnailPath();
        Point point = this.$it;
        Bitmap templateBitmap = TemplateDataUtil.getTemplateBitmap(thumbnailPath, true, point.x, point.y, true);
        Log.d(this.this$0.TAG, "screenshotWithCurrentPreview: load wallpaper for apply w=" + (templateBitmap != null ? Boxing.boxInt(templateBitmap.getWidth()) : null) + " h=" + (templateBitmap != null ? Boxing.boxInt(templateBitmap.getHeight()) : null));
        return templateBitmap;
    }
}

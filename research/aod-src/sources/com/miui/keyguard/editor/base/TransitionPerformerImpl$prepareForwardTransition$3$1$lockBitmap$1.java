package com.miui.keyguard.editor.base;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.BitmapUtil;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: Transition.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.base.TransitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1", f = "Transition.kt", l = {}, m = "invokeSuspend")
public final class TransitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
    public final /* synthetic */ TemplateItemBean $itemData;
    public final /* synthetic */ Bitmap $lockPreview;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1(TemplateItemBean templateItemBean, Bitmap bitmap, Continuation continuation) {
        super(2, continuation);
        this.$itemData = templateItemBean;
        this.$lockPreview = bitmap;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TransitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1(this.$itemData, this.$lockPreview, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TransitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TemplateItemConfig bindItemConfig;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
        PresetTemplateConfig preset = this.$itemData.getPreset();
        String wallpaperPath = (preset == null || (bindItemConfig = preset.getBindItemConfig()) == null) ? null : bindItemConfig.getWallpaperPath();
        Intrinsics.checkNotNull(wallpaperPath);
        String presetWallpaperAbsolutePath = companion.getPresetWallpaperAbsolutePath(wallpaperPath);
        Bitmap bitmap = this.$lockPreview;
        int width = bitmap != null ? bitmap.getWidth() : -1;
        Bitmap bitmap2 = this.$lockPreview;
        return BitmapUtil.fromFile$default(bitmapUtil, presetWallpaperAbsolutePath, width, bitmap2 != null ? bitmap2.getHeight() : -1, null, 8, null);
    }
}

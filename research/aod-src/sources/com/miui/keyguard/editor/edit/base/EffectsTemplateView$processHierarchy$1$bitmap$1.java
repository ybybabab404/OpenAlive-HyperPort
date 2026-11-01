package com.miui.keyguard.editor.edit.base;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.utils.TemplatePreviewImageComposer;
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
/* JADX INFO: compiled from: EffectsTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$processHierarchy$1$bitmap$1", f = "EffectsTemplateView.kt", l = {}, m = "invokeSuspend")
public final class EffectsTemplateView$processHierarchy$1$bitmap$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
    public final /* synthetic */ WallpaperTypeInfo $info;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EffectsTemplateView$processHierarchy$1$bitmap$1(WallpaperTypeInfo wallpaperTypeInfo, Continuation continuation) {
        super(2, continuation);
        this.$info = wallpaperTypeInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EffectsTemplateView$processHierarchy$1$bitmap$1(this.$info, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((EffectsTemplateView$processHierarchy$1$bitmap$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String maskPath = this.$info.getMaskPath();
        if (maskPath != null) {
            WallpaperTypeInfo wallpaperTypeInfo = this.$info;
            TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
            if (companion.isPresetPath(wallpaperTypeInfo.getMaskPath())) {
                maskPath = companion.getPresetWallpaperAbsolutePath(maskPath);
            }
        } else {
            maskPath = null;
        }
        TemplatePreviewImageComposer templatePreviewImageComposer = TemplatePreviewImageComposer.INSTANCE;
        String resourcePath = this.$info.getResourcePath();
        Intrinsics.checkNotNull(resourcePath);
        return templatePreviewImageComposer.composeSubjectImage(resourcePath, maskPath, false);
    }
}

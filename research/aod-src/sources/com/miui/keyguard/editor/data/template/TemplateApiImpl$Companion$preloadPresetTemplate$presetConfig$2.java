package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
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
/* JADX INFO: compiled from: TemplateApiImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl$Companion$preloadPresetTemplate$presetConfig$2", f = "TemplateApiImpl.kt", l = {}, m = "invokeSuspend")
public final class TemplateApiImpl$Companion$preloadPresetTemplate$presetConfig$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ TemplateItemConfig $config;
    public final /* synthetic */ Context $context;
    public final /* synthetic */ Bitmap $previewImage;
    public final /* synthetic */ int $reqHeight;
    public final /* synthetic */ int $reqWidth;
    public final /* synthetic */ String $wallpaperPath;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApiImpl$Companion$preloadPresetTemplate$presetConfig$2(Context context, TemplateItemConfig templateItemConfig, String str, int i, int i2, Bitmap bitmap, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$config = templateItemConfig;
        this.$wallpaperPath = str;
        this.$reqWidth = i;
        this.$reqHeight = i2;
        this.$previewImage = bitmap;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApiImpl$Companion$preloadPresetTemplate$presetConfig$2(this.$context, this.$config, this.$wallpaperPath, this.$reqWidth, this.$reqHeight, this.$previewImage, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApiImpl$Companion$preloadPresetTemplate$presetConfig$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d("Keyguard-Editor-TemplateApiImpl", "save normal preview cache: " + TemplateApi.Companion.getInstance(this.$context).putPreviewCache(TemplateApiImpl.Companion.getPreviewCachedPath(this.$context, this.$config.getTemplateName(), this.$wallpaperPath, this.$config.getMaskPath(), this.$reqWidth, this.$reqHeight), this.$previewImage));
        return Unit.INSTANCE;
    }
}

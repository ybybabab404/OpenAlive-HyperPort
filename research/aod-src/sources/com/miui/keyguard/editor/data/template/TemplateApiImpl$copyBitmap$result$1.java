package com.miui.keyguard.editor.data.template;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.utils.BitmapUtil;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

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
@DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl$copyBitmap$result$1", f = "TemplateApiImpl.kt", l = {}, m = "invokeSuspend")
public final class TemplateApiImpl$copyBitmap$result$1 extends SuspendLambda implements Function1<Continuation<? super Boolean>, Object> {
    public final /* synthetic */ Bitmap $bitmap;
    public final /* synthetic */ boolean $forceSync;
    public final /* synthetic */ boolean $forceUsePNG;
    public final /* synthetic */ String $target;
    public final /* synthetic */ boolean $toWebp;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApiImpl$copyBitmap$result$1(boolean z, Bitmap bitmap, String str, boolean z2, boolean z3, Continuation continuation) {
        super(1, continuation);
        this.$forceUsePNG = z;
        this.$bitmap = bitmap;
        this.$target = str;
        this.$toWebp = z2;
        this.$forceSync = z3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new TemplateApiImpl$copyBitmap$result$1(this.$forceUsePNG, this.$bitmap, this.$target, this.$toWebp, this.$forceSync, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation continuation) {
        return ((TemplateApiImpl$copyBitmap$result$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean webp;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$forceUsePNG) {
            webp = BitmapUtil.INSTANCE.toPNG(this.$bitmap, this.$target);
        } else {
            webp = this.$toWebp ? BitmapUtil.INSTANCE.toWebp(this.$bitmap, this.$target, this.$forceSync) : BitmapUtil.INSTANCE.toJPEG(this.$bitmap, this.$target);
        }
        return Boxing.boxBoolean(webp);
    }
}

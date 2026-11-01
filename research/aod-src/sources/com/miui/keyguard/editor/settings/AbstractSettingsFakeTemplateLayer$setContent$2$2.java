package com.miui.keyguard.editor.settings;

import android.graphics.Bitmap;
import android.widget.ImageView;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function4;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AbstractSettingsFakeTemplateLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.settings.AbstractSettingsFakeTemplateLayer$setContent$2$2", f = "AbstractSettingsFakeTemplateLayer.kt", l = {}, m = "invokeSuspend")
public final class AbstractSettingsFakeTemplateLayer$setContent$2$2 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function0 $callback;
    public final /* synthetic */ ImageView $content;
    public /* synthetic */ Object L$0;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractSettingsFakeTemplateLayer$setContent$2$2(ImageView imageView, Function0 function0, Continuation continuation) {
        super(4, continuation);
        this.$content = imageView;
        this.$callback = function0;
    }

    public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
        AbstractSettingsFakeTemplateLayer$setContent$2$2 abstractSettingsFakeTemplateLayer$setContent$2$2 = new AbstractSettingsFakeTemplateLayer$setContent$2$2(this.$content, this.$callback, continuation);
        abstractSettingsFakeTemplateLayer$setContent$2$2.L$0 = bitmap;
        return abstractSettingsFakeTemplateLayer$setContent$2$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke((Bitmap) obj, ((Number) obj2).intValue(), (Bitmap) obj3, (Continuation) obj4);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$content.setImageBitmap((Bitmap) this.L$0);
        this.$callback.invoke();
        return Unit.INSTANCE;
    }
}

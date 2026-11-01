package com.miui.keyguard.editor.homepage.util;

import android.util.Log;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1", f = "TemplateApplyController.kt", l = {252}, m = "invokeSuspend")
public final class TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ TemplateItemBean $templateBean;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1(TemplateApplyController templateApplyController, TemplateItemBean templateItemBean, Continuation continuation) {
        super(2, continuation);
        this.this$0 = templateApplyController;
        this.$templateBean = templateItemBean;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1(this.this$0, this.$templateBean, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineDispatcher io2 = Dispatchers.getIO();
            TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1 templateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1 = new TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1(this.$templateBean, this.this$0, null);
            this.label = 1;
            obj = BuildersKt.withContext(io2, templateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        Log.i(this.this$0.TAG, "completeApplyTemplate -> apply completed, result: " + zBooleanValue + ", applyCallback=" + this.this$0.applyCallback);
        if (this.this$0.applyCallback == null) {
            TemplateApplyController.setApplying$default(this.this$0, false, false, 2, null);
        }
        ApplyCallback applyCallback = this.this$0.applyCallback;
        if (applyCallback != null) {
            applyCallback.onApplyComplete(zBooleanValue, null);
        }
        return Unit.INSTANCE;
    }
}

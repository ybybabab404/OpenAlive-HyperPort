package com.miui.keyguard.editor.edit.padclock;

import android.content.Context;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.view.AutoBottomSheet;
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
import kotlinx.coroutines.DelayKt;
import miuix.androidbasewidget.widget.ClearableEditText;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: PadATemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.padclock.PadATemplateView$onClickSignatureFrame$1$1$1", f = "PadATemplateView.kt", l = {206}, m = "invokeSuspend")
public final class PadATemplateView$onClickSignatureFrame$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ClearableEditText $this_apply;
    public int label;
    public final /* synthetic */ PadATemplateView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PadATemplateView$onClickSignatureFrame$1$1$1(PadATemplateView padATemplateView, ClearableEditText clearableEditText, Continuation continuation) {
        super(2, continuation);
        this.this$0 = padATemplateView;
        this.$this_apply = clearableEditText;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PadATemplateView$onClickSignatureFrame$1$1$1(this.this$0, this.$this_apply, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((PadATemplateView$onClickSignatureFrame$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(600L, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        AutoBottomSheet signatureSheetModal = this.this$0.getSignatureSheetModal();
        if (signatureSheetModal != null && signatureSheetModal.isShowing() && !this.$this_apply.hasFocus()) {
            this.$this_apply.requestFocus();
            KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
            Context context = this.$this_apply.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            keyboardUtils.showSoftInput(context, this.$this_apply);
            this.this$0.signatureShowing = false;
        }
        return Unit.INSTANCE;
    }
}

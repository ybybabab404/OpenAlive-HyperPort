package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.task.SingleTask;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
/* JADX INFO: compiled from: TemplateApplier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$onApplyTemplate$applyJob$1", f = "TemplateApplier.kt", l = {471}, m = "invokeSuspend")
public final class TemplateApplier$onApplyTemplate$applyJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ TemplateApplier.TemplateApplierParams $params;
    public final /* synthetic */ BaseTemplateView $templateView;
    public int label;
    public final /* synthetic */ TemplateApplier this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplier$onApplyTemplate$applyJob$1(TemplateApplier templateApplier, Context context, BaseTemplateView baseTemplateView, TemplateApplier.TemplateApplierParams templateApplierParams, Continuation continuation) {
        super(2, continuation);
        this.this$0 = templateApplier;
        this.$context = context;
        this.$templateView = baseTemplateView;
        this.$params = templateApplierParams;
    }

    public static final Unit invokeSuspend$lambda$0(TemplateApplier templateApplier, Context context, BaseTemplateView baseTemplateView) {
        templateApplier.onAppliedInternal(context, baseTemplateView);
        return Unit.INSTANCE;
    }

    public static final void invokeSuspend$lambda$1(TemplateApplier templateApplier, Unit unit) {
        templateApplier.applyCallback.onApplied(true, templateApplier.applyCallback, templateApplier.applyAnimationPerformer);
        EditorActivityViewModel editorActivityViewModel = templateApplier.editorViewModel;
        if (editorActivityViewModel != null) {
            editorActivityViewModel.updateApplyStatus(2);
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplier$onApplyTemplate$applyJob$1(this.this$0, this.$context, this.$templateView, this.$params, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplier$onApplyTemplate$applyJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineDispatcher io2 = Dispatchers.getIO();
            TemplateApplier$onApplyTemplate$applyJob$1$succeed$1 templateApplier$onApplyTemplate$applyJob$1$succeed$1 = new TemplateApplier$onApplyTemplate$applyJob$1$succeed$1(this.this$0, this.$templateView, this.$params, this.$context, null);
            this.label = 1;
            obj = BuildersKt.withContext(io2, templateApplier$onApplyTemplate$applyJob$1$succeed$1, this);
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
        Log.i(this.this$0.tag, "onApplyTemplate is success: " + zBooleanValue);
        if (zBooleanValue) {
            Context context = this.$context;
            EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
            if (editorActivity == null || !editorActivity.callingFromSystemUI()) {
                Context context2 = this.$context;
                EditorActivity editorActivity2 = context2 instanceof EditorActivity ? (EditorActivity) context2 : null;
                if (editorActivity2 == null || !editorActivity2.callingFromLockCross()) {
                    ToastUtil.INSTANCE.showText(this.$context, R.string.kg_dialog_apply_successful);
                }
            }
            this.this$0.applyCallback.onApplySuccessBeforeExitAnim();
            final TemplateApplier templateApplier = this.this$0;
            final Context context3 = this.$context;
            final BaseTemplateView baseTemplateView = this.$templateView;
            SingleTask singleTaskCreate = Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$onApplyTemplate$applyJob$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return TemplateApplier$onApplyTemplate$applyJob$1.invokeSuspend$lambda$0(templateApplier, context3, baseTemplateView);
                }
            });
            final TemplateApplier templateApplier2 = this.this$0;
            singleTaskCreate.post(new Consumer() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$onApplyTemplate$applyJob$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    TemplateApplier$onApplyTemplate$applyJob$1.invokeSuspend$lambda$1(templateApplier2, (Unit) obj2);
                }
            });
        } else {
            TemplateApplier.ApplyCallback.DefaultImpls.onApplied$default(this.this$0.applyCallback, false, null, null, 6, null);
            EditorActivityViewModel editorActivityViewModel = this.this$0.editorViewModel;
            if (editorActivityViewModel != null) {
                editorActivityViewModel.updateApplyStatus(2);
            }
        }
        return Unit.INSTANCE;
    }
}

package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import com.miui.keyguard.editor.EditorActivity;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: CombineEditView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.CombineEditView$combineEditListener$1$onTemplateReady$1", f = "CombineEditView.kt", l = {159}, m = "invokeSuspend")
public final class CombineEditView$combineEditListener$1$onTemplateReady$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $context;
    public int label;
    public final /* synthetic */ CombineEditView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CombineEditView$combineEditListener$1$onTemplateReady$1(CombineEditView combineEditView, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = combineEditView;
        this.$context = context;
    }

    public static final Unit invokeSuspend$lambda$0(CombineEditView combineEditView, Context context) {
        combineEditView.setTemplateStatus(0);
        EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
        if (editorActivity != null) {
            editorActivity.setApplyButtonEnabled(true);
        }
        combineEditView.requestLayout();
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CombineEditView$combineEditListener$1$onTemplateReady$1(this.this$0, this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((CombineEditView$combineEditListener$1$onTemplateReady$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x005c  */
    /* JADX WARN: Code duplicated, block: B:21:0x0067  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Function0<Unit> function0;
        BaseTemplateView lockTemplateView;
        HomeTemplateView homeTemplateView;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.this$0.loadingTimeStamp != 0) {
                long jCurrentTimeMillis = System.currentTimeMillis() - this.this$0.loadingTimeStamp;
                if (jCurrentTimeMillis < 300) {
                    this.label = 1;
                    if (DelayKt.delay(300 - jCurrentTimeMillis, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            }
            final CombineEditView combineEditView = this.this$0;
            final Context context = this.$context;
            function0 = new Function0() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$combineEditListener$1$onTemplateReady$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return CombineEditView$combineEditListener$1$onTemplateReady$1.invokeSuspend$lambda$0(combineEditView, context);
                }
            };
            this.this$0.performForwardTransition();
            lockTemplateView = this.this$0.getLockTemplateView();
            if (lockTemplateView != null) {
                lockTemplateView.allReady(function0);
            }
            homeTemplateView = this.this$0.getHomeTemplateView();
            if (homeTemplateView != null) {
                homeTemplateView.allReady(function0);
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.loadingTimeStamp = 0L;
        final CombineEditView combineEditView2 = this.this$0;
        final Context context2 = this.$context;
        function0 = new Function0() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$combineEditListener$1$onTemplateReady$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CombineEditView$combineEditListener$1$onTemplateReady$1.invokeSuspend$lambda$0(combineEditView2, context2);
            }
        };
        this.this$0.performForwardTransition();
        lockTemplateView = this.this$0.getLockTemplateView();
        if (lockTemplateView != null) {
            lockTemplateView.allReady(function0);
        }
        homeTemplateView = this.this$0.getHomeTemplateView();
        if (homeTemplateView != null) {
            homeTemplateView.allReady(function0);
        }
        return Unit.INSTANCE;
    }
}

package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
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
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1", f = "TemplateApplyController.kt", l = {358, 362}, m = "invokeSuspend")
public final class TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ TemplateItemBean $templateBean;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1(TemplateApplyController templateApplyController, TemplateItemBean templateItemBean, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = templateApplyController;
        this.$templateBean = templateItemBean;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1(this.this$0, this.$templateBean, this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0051, code lost:
    
        if (r7 == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1f
            if (r1 == r4) goto L1b
            if (r1 != r3) goto L13
            kotlin.ResultKt.throwOnFailure(r7)
            goto L54
        L13:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1b:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L36
        L1f:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.CoroutineDispatcher r7 = kotlinx.coroutines.Dispatchers.getIO()
            com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1$thirdTemplateTheme$1 r1 = new com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1$thirdTemplateTheme$1
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r5 = r6.this$0
            r1.<init>(r5, r2)
            r6.label = r4
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r1, r6)
            if (r7 != r0) goto L36
            goto L53
        L36:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L7c
            kotlinx.coroutines.CoroutineDispatcher r7 = kotlinx.coroutines.Dispatchers.getIO()
            com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1$applySuccess$1 r1 = new com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1$applySuccess$1
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r4 = r6.this$0
            android.content.Context r5 = r6.$context
            r1.<init>(r4, r5, r2)
            r6.label = r3
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r1, r6)
            if (r7 != r0) goto L54
        L53:
            return r0
        L54:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r0 = r6.this$0
            java.lang.String r0 = com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$getTAG$p(r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "checkOrApplyCurrentTemplateByProvider -> apply completed, result: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r0 = r6.this$0
            com.miui.keyguard.editor.homepage.bean.TemplateItemBean r6 = r6.$templateBean
            com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$currentTemplateApplyCompleted(r0, r6, r7)
            goto L83
        L7c:
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r7 = r6.this$0
            com.miui.keyguard.editor.homepage.bean.TemplateItemBean r6 = r6.$templateBean
            com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$currentTemplateApplyCompleted(r7, r6, r4)
        L83:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

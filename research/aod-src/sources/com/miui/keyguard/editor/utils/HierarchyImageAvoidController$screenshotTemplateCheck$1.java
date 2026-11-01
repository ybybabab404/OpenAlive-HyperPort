package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: HierarchyImageAvoidController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.utils.HierarchyImageAvoidController$screenshotTemplateCheck$1", f = "HierarchyImageAvoidController.kt", l = {138}, m = "invokeSuspend")
public final class HierarchyImageAvoidController$screenshotTemplateCheck$1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ HierarchyImageAvoidController this$0;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.HierarchyImageAvoidController$screenshotTemplateCheck$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: HierarchyImageAvoidController.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.HierarchyImageAvoidController$screenshotTemplateCheck$1$1", f = "HierarchyImageAvoidController.kt", l = {144, 153}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Map.Entry $calculateWorker;
        public Object L$0;
        public int label;
        public final /* synthetic */ HierarchyImageAvoidController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Map.Entry entry, HierarchyImageAvoidController hierarchyImageAvoidController, Continuation continuation) {
            super(2, continuation);
            this.$calculateWorker = entry;
            this.this$0 = hierarchyImageAvoidController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$calculateWorker, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x00c1, code lost:
        
            if (r9.notifyScreenshotCheckListener(r8) == r0) goto L17;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 0
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L24
                if (r1 == r4) goto L1c
                if (r1 != r3) goto L14
                kotlin.ResultKt.throwOnFailure(r9)
                goto Lc4
            L14:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L1c:
                java.lang.Object r1 = r8.L$0
                java.lang.String r1 = (java.lang.String) r1
                kotlin.ResultKt.throwOnFailure(r9)
                goto L4c
            L24:
                kotlin.ResultKt.throwOnFailure(r9)
                java.util.Map$Entry r9 = r8.$calculateWorker
                java.lang.Object r9 = r9.getKey()
                r1 = r9
                java.lang.String r1 = (java.lang.String) r1
                java.util.Map$Entry r9 = r8.$calculateWorker
                java.lang.Object r9 = r9.getValue()
                com.miui.keyguard.editor.utils.CalculateWorker r9 = (com.miui.keyguard.editor.utils.CalculateWorker) r9
                kotlinx.coroutines.CoroutineDispatcher r5 = kotlinx.coroutines.Dispatchers.getDefault()
                com.miui.keyguard.editor.utils.HierarchyImageAvoidController$screenshotTemplateCheck$1$1$avoidCheckEnable$1 r6 = new com.miui.keyguard.editor.utils.HierarchyImageAvoidController$screenshotTemplateCheck$1$1$avoidCheckEnable$1
                r6.<init>(r9, r2)
                r8.L$0 = r1
                r8.label = r4
                java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r8)
                if (r9 != r0) goto L4c
                goto Lc3
            L4c:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                java.lang.Boolean r4 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r9)
                com.miui.keyguard.editor.utils.HierarchyImageAvoidController r5 = r8.this$0
                java.util.Map r5 = com.miui.keyguard.editor.utils.HierarchyImageAvoidController.access$getScreenshotCheckResult$p(r5)
                r5.put(r1, r4)
                com.miui.keyguard.editor.utils.HierarchyImageAvoidController r4 = r8.this$0
                java.util.Map r4 = com.miui.keyguard.editor.utils.HierarchyImageAvoidController.access$getScreenshotCheckResult$p(r4)
                int r4 = r4.size()
                com.miui.keyguard.editor.utils.HierarchyImageAvoidController r5 = r8.this$0
                java.util.Map r5 = com.miui.keyguard.editor.utils.HierarchyImageAvoidController.access$getCalculateWorkers$p(r5)
                int r5 = r5.size()
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "screenshot template avoid check type "
                r6.append(r7)
                r6.append(r1)
                java.lang.String r1 = " result "
                r6.append(r1)
                r6.append(r9)
                java.lang.String r9 = " resultSize="
                r6.append(r9)
                r6.append(r4)
                java.lang.String r9 = " workerSize="
                r6.append(r9)
                r6.append(r5)
                java.lang.String r9 = r6.toString()
                java.lang.String r1 = "HierarchyImageAvoidCutController"
                android.util.Log.i(r1, r9)
                com.miui.keyguard.editor.utils.HierarchyImageAvoidController r9 = r8.this$0
                java.util.Map r9 = com.miui.keyguard.editor.utils.HierarchyImageAvoidController.access$getScreenshotCheckResult$p(r9)
                int r9 = r9.size()
                com.miui.keyguard.editor.utils.HierarchyImageAvoidController r1 = r8.this$0
                java.util.Map r1 = com.miui.keyguard.editor.utils.HierarchyImageAvoidController.access$getCalculateWorkers$p(r1)
                int r1 = r1.size()
                if (r9 != r1) goto Lc4
                com.miui.keyguard.editor.utils.HierarchyImageAvoidController r9 = r8.this$0
                r8.L$0 = r2
                r8.label = r3
                java.lang.Object r8 = com.miui.keyguard.editor.utils.HierarchyImageAvoidController.access$notifyScreenshotCheckListener(r9, r8)
                if (r8 != r0) goto Lc4
            Lc3:
                return r0
            Lc4:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.utils.HierarchyImageAvoidController$screenshotTemplateCheck$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HierarchyImageAvoidController$screenshotTemplateCheck$1(HierarchyImageAvoidController hierarchyImageAvoidController, Continuation continuation) {
        super(1, continuation);
        this.this$0 = hierarchyImageAvoidController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new HierarchyImageAvoidController$screenshotTemplateCheck$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation continuation) {
        return ((HierarchyImageAvoidController$screenshotTemplateCheck$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.this$0.screenshotCheckResult.clear();
                if (this.this$0.calculateWorkers.isEmpty()) {
                    HierarchyImageAvoidController hierarchyImageAvoidController = this.this$0;
                    this.label = 1;
                    if (hierarchyImageAvoidController.notifyScreenshotCheckListener(this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    for (Map.Entry entry : this.this$0.calculateWorkers.entrySet()) {
                        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
                        Context context = this.this$0.bindHierarchyImageView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(entry, this.this$0, null), 3, null);
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (Exception e) {
            Log.e("HierarchyImageAvoidCutController", "screenshotTemplateCheck: error", e);
            try {
                KeyguardEditorMiSightUtil.reportHierarchyInvisible$default(KeyguardEditorMiSightUtil.INSTANCE, "HierarchyImageAvoidController.screenshotTemplateCheck_exception", null, 2, null);
            } catch (Exception unused) {
            }
        }
        return Unit.INSTANCE;
    }
}

package com.miui.keyguard.editor.data.preset;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: FontFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.data.preset.FontFilterSelectInfo$resetAllModifyColor$1", f = "FontFilter.kt", l = {2260}, m = "invokeSuspend")
@SourceDebugExtension
final class FontFilterSelectInfo$resetAllModifyColor$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int I$0;
    public int I$1;
    public long J$0;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;
    public final /* synthetic */ FontFilterSelectInfo this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FontFilterSelectInfo$resetAllModifyColor$1(FontFilterSelectInfo fontFilterSelectInfo, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fontFilterSelectInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FontFilterSelectInfo$resetAllModifyColor$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((FontFilterSelectInfo$resetAllModifyColor$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0048  */
    /* JADX WARN: Code duplicated, block: B:12:0x006e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:16:0x007e A[Catch: all -> 0x008d, LOOP:0: B:14:0x0078->B:16:0x007e, LOOP_END, TryCatch #0 {all -> 0x008d, blocks: (B:13:0x006f, B:14:0x0078, B:16:0x007e, B:19:0x008f), top: B:25:0x006f }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x006c -> B:25:0x006f). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            r14 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r14.label
            java.lang.String r2 = "FontFilter"
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L2c
            if (r1 != r3) goto L24
            int r1 = r14.I$1
            int r5 = r14.I$0
            long r6 = r14.J$0
            java.lang.Object r8 = r14.L$2
            kotlinx.coroutines.sync.Mutex r8 = (kotlinx.coroutines.sync.Mutex) r8
            java.lang.Object r9 = r14.L$1
            com.miui.keyguard.editor.data.preset.LockedHashSet r9 = (com.miui.keyguard.editor.data.preset.LockedHashSet) r9
            java.lang.Object r10 = r14.L$0
            int[] r10 = (int[]) r10
            kotlin.ResultKt.throwOnFailure(r15)
            goto L6f
        L24:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L2c:
            kotlin.ResultKt.throwOnFailure(r15)
            long r5 = java.lang.System.currentTimeMillis()
            java.lang.String r15 = "start"
            android.util.Log.d(r2, r15)
            r15 = 2
            int[] r1 = new int[r15]
            r7 = 0
            r1[r7] = r7
            r1[r3] = r3
            r12 = r5
            r5 = r7
            r6 = r12
            r10 = r1
            r1 = r15
        L46:
            if (r5 >= r1) goto L9a
            r15 = r10[r5]
            com.miui.keyguard.editor.data.preset.FontFilterSelectInfo r8 = r14.this$0
            com.miui.keyguard.editor.data.preset.FilterTypeSelectInfo[] r8 = r8.getColorSelectInfo()
            r15 = r8[r15]
            com.miui.keyguard.editor.data.preset.LockedHashSet r9 = r15.getModifyColor()
            kotlinx.coroutines.sync.Mutex r8 = r9.getMutexLock()
            r14.L$0 = r10
            r14.L$1 = r9
            r14.L$2 = r8
            r14.J$0 = r6
            r14.I$0 = r5
            r14.I$1 = r1
            r14.label = r3
            java.lang.Object r15 = r8.lock(r4, r14)
            if (r15 != r0) goto L6f
            return r0
        L6f:
            java.util.Iterator r15 = r9.iterator()     // Catch: java.lang.Throwable -> L8d
            java.lang.String r9 = "iterator(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r15, r9)     // Catch: java.lang.Throwable -> L8d
        L78:
            boolean r9 = r15.hasNext()     // Catch: java.lang.Throwable -> L8d
            if (r9 == 0) goto L8f
            java.lang.Object r9 = r15.next()     // Catch: java.lang.Throwable -> L8d
            java.lang.String r11 = "next(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r11)     // Catch: java.lang.Throwable -> L8d
            com.miui.keyguard.editor.data.preset.FilterColor r9 = (com.miui.keyguard.editor.data.preset.FilterColor) r9     // Catch: java.lang.Throwable -> L8d
            r9.reset()     // Catch: java.lang.Throwable -> L8d
            goto L78
        L8d:
            r14 = move-exception
            goto L96
        L8f:
            kotlin.Unit r15 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L8d
            r8.unlock(r4)
            int r5 = r5 + r3
            goto L46
        L96:
            r8.unlock(r4)
            throw r14
        L9a:
            long r14 = java.lang.System.currentTimeMillis()
            long r14 = r14 - r6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "end:"
            r0.append(r1)
            r0.append(r14)
            java.lang.String r14 = " "
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            android.util.Log.d(r2, r14)
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.data.preset.FontFilterSelectInfo$resetAllModifyColor$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

package kotlin.text;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Regex.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "kotlin.text.Regex$splitToSequence$1", f = "Regex.kt", l = {275, 283, 287}, m = "invokeSuspend")
final class Regex$splitToSequence$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super String>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ CharSequence $input;
    public final /* synthetic */ int $limit;
    public int I$0;
    public /* synthetic */ Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ Regex this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Regex$splitToSequence$1(Regex regex, CharSequence charSequence, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = regex;
        this.$input = charSequence;
        this.$limit = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        Regex$splitToSequence$1 regex$splitToSequence$1 = new Regex$splitToSequence$1(this.this$0, this.$input, this.$limit, continuation);
        regex$splitToSequence$1.L$0 = obj;
        return regex$splitToSequence$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
        return ((Regex$splitToSequence$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0071 A[PHI: r1 r6 r10
  0x0071: PHI (r1v8 int) = (r1v7 int), (r1v12 int) binds: [B:19:0x006e, B:10:0x001e] A[DONT_GENERATE, DONT_INLINE]
  0x0071: PHI (r6v2 kotlin.sequences.SequenceScope) = (r6v1 kotlin.sequences.SequenceScope), (r6v4 kotlin.sequences.SequenceScope) binds: [B:19:0x006e, B:10:0x001e] A[DONT_GENERATE, DONT_INLINE]
  0x0071: PHI (r10v5 java.util.regex.Matcher) = (r10v4 java.util.regex.Matcher), (r10v9 java.util.regex.Matcher) binds: [B:19:0x006e, B:10:0x001e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:23:0x007b  */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x009a, code lost:
    
        if (r6.yield(r10, r9) == r0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00ac, code lost:
    
        if (r10.yield(r1, r9) == r0) goto L32;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x006e -> B:21:0x0071). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L32
            if (r1 == r4) goto L2d
            if (r1 == r3) goto L1e
            if (r1 != r2) goto L16
            kotlin.ResultKt.throwOnFailure(r10)
            goto L9d
        L16:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1e:
            int r1 = r9.I$0
            java.lang.Object r5 = r9.L$1
            java.util.regex.Matcher r5 = (java.util.regex.Matcher) r5
            java.lang.Object r6 = r9.L$0
            kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r5
            goto L71
        L2d:
            kotlin.ResultKt.throwOnFailure(r10)
            goto Laf
        L32:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlin.sequences.SequenceScope r10 = (kotlin.sequences.SequenceScope) r10
            kotlin.text.Regex r1 = r9.this$0
            java.util.regex.Pattern r1 = kotlin.text.Regex.access$getNativePattern$p(r1)
            java.lang.CharSequence r5 = r9.$input
            java.util.regex.Matcher r1 = r1.matcher(r5)
            int r5 = r9.$limit
            if (r5 == r4) goto La0
            boolean r5 = r1.find()
            if (r5 != 0) goto L50
            goto La0
        L50:
            r5 = 0
            r6 = r10
            r10 = r1
            r1 = r5
        L54:
            java.lang.CharSequence r7 = r9.$input
            int r8 = r10.start()
            java.lang.CharSequence r5 = r7.subSequence(r5, r8)
            java.lang.String r5 = r5.toString()
            r9.L$0 = r6
            r9.L$1 = r10
            r9.I$0 = r1
            r9.label = r3
            java.lang.Object r5 = r6.yield(r5, r9)
            if (r5 != r0) goto L71
            goto Lae
        L71:
            int r5 = r10.end()
            int r1 = r1 + r4
            int r7 = r9.$limit
            int r7 = r7 - r4
            if (r1 == r7) goto L81
            boolean r7 = r10.find()
            if (r7 != 0) goto L54
        L81:
            java.lang.CharSequence r10 = r9.$input
            int r1 = r10.length()
            java.lang.CharSequence r10 = r10.subSequence(r5, r1)
            java.lang.String r10 = r10.toString()
            r1 = 0
            r9.L$0 = r1
            r9.L$1 = r1
            r9.label = r2
            java.lang.Object r9 = r6.yield(r10, r9)
            if (r9 != r0) goto L9d
            goto Lae
        L9d:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        La0:
            java.lang.CharSequence r1 = r9.$input
            java.lang.String r1 = r1.toString()
            r9.label = r4
            java.lang.Object r9 = r10.yield(r1, r9)
            if (r9 != r0) goto Laf
        Lae:
            return r0
        Laf:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.Regex$splitToSequence$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

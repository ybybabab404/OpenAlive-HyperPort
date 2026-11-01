package kotlin.io.path;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

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
/* JADX INFO: compiled from: PathTreeWalk.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PathTreeWalk implements Sequence<Path> {
    public final PathWalkOption[] options;
    public final Path start;

    /* JADX INFO: renamed from: kotlin.io.path.PathTreeWalk$bfsIterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: PathTreeWalk.kt */
    @Metadata
    @DebugMetadata(c = "kotlin.io.path.PathTreeWalk$bfsIterator$1", f = "PathTreeWalk.kt", l = {191, 197}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public Object L$5;
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = PathTreeWalk.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:13:0x0083  */
        /* JADX WARN: Code duplicated, block: B:15:0x0095  */
        /* JADX WARN: Code duplicated, block: B:20:0x00b6  */
        /* JADX WARN: Code duplicated, block: B:22:0x00bc  */
        /* JADX WARN: Code duplicated, block: B:25:0x00d2  */
        /* JADX WARN: Code duplicated, block: B:29:0x00f8  */
        /* JADX WARN: Code duplicated, block: B:30:0x0101  */
        /* JADX WARN: Code duplicated, block: B:40:0x00b0 A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x00f6 -> B:11:0x007d). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00f8 -> B:11:0x007d). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
            /*
                Method dump skipped, instruction units count: 309
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.io.path.PathTreeWalk.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlin.io.path.PathTreeWalk$dfsIterator$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PathTreeWalk.kt */
    @Metadata
    @DebugMetadata(c = "kotlin.io.path.PathTreeWalk$dfsIterator$1", f = "PathTreeWalk.kt", l = {191, 197, 210, 216}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class C01801 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public Object L$5;
        public int label;

        public C01801(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01801 c01801 = PathTreeWalk.this.new C01801(continuation);
            c01801.L$0 = obj;
            return c01801;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((C01801) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:41:0x014d  */
        /* JADX WARN: Code duplicated, block: B:45:0x0172  */
        /* JADX WARN: Code duplicated, block: B:50:0x0193  */
        /* JADX WARN: Code duplicated, block: B:52:0x0199  */
        /* JADX WARN: Code duplicated, block: B:55:0x01af  */
        /* JADX WARN: Code duplicated, block: B:57:0x01bd A[PHI: r1 r4 r7 r8 r9 r14
  0x01bd: PHI (r1v9 kotlin.io.path.DirectoryEntriesReader) = (r1v7 kotlin.io.path.DirectoryEntriesReader), (r1v11 kotlin.io.path.DirectoryEntriesReader) binds: [B:51:0x0197, B:56:0x01b6] A[DONT_GENERATE, DONT_INLINE]
  0x01bd: PHI (r4v19 kotlin.collections.ArrayDeque) = (r4v17 kotlin.collections.ArrayDeque), (r4v21 kotlin.collections.ArrayDeque) binds: [B:51:0x0197, B:56:0x01b6] A[DONT_GENERATE, DONT_INLINE]
  0x01bd: PHI (r7v9 kotlin.sequences.SequenceScope) = (r7v7 kotlin.sequences.SequenceScope), (r7v11 kotlin.sequences.SequenceScope) binds: [B:51:0x0197, B:56:0x01b6] A[DONT_GENERATE, DONT_INLINE]
  0x01bd: PHI (r8v15 kotlin.io.path.PathTreeWalk) = (r8v13 kotlin.io.path.PathTreeWalk), (r8v25 kotlin.io.path.PathTreeWalk) binds: [B:51:0x0197, B:56:0x01b6] A[DONT_GENERATE, DONT_INLINE]
  0x01bd: PHI (r9v11 java.nio.file.Path) = (r9v9 java.nio.file.Path), (r9v13 java.nio.file.Path) binds: [B:51:0x0197, B:56:0x01b6] A[DONT_GENERATE, DONT_INLINE]
  0x01bd: PHI (r14v21 kotlin.io.path.PathNode) = (r14v13 kotlin.io.path.PathNode), (r14v23 kotlin.io.path.PathNode) binds: [B:51:0x0197, B:56:0x01b6] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:59:0x01d5  */
        /* JADX WARN: Code duplicated, block: B:60:0x01e5  */
        /* JADX WARN: Code duplicated, block: B:73:0x018d A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:75:0x0160 A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x01d3 -> B:39:0x0147). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x01d5 -> B:39:0x0147). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                Method dump skipped, instruction units count: 542
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.io.path.PathTreeWalk.C01801.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final Iterator bfsIterator() {
        return SequencesKt.iterator(new AnonymousClass1(null));
    }

    public final Iterator dfsIterator() {
        return SequencesKt.iterator(new C01801(null));
    }

    public final boolean getFollowLinks() {
        return ArraysKt.contains(this.options, PathWalkOption.FOLLOW_LINKS);
    }

    public final boolean getIncludeDirectories() {
        return ArraysKt.contains(this.options, PathWalkOption.INCLUDE_DIRECTORIES);
    }

    public final LinkOption[] getLinkOptions() {
        return LinkFollowing.INSTANCE.toLinkOptions(getFollowLinks());
    }

    public final boolean isBFS() {
        return ArraysKt.contains(this.options, PathWalkOption.BREADTH_FIRST);
    }

    @Override // kotlin.sequences.Sequence
    public Iterator iterator() {
        return isBFS() ? bfsIterator() : dfsIterator();
    }
}

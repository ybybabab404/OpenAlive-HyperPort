package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

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
/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FlatteningSequence<T, R, E> implements Sequence<E> {
    public final Function1 iterator;
    public final Sequence sequence;
    public final Function1 transformer;

    /* JADX INFO: compiled from: Sequences.kt */
    @Metadata
    public static final class State {
        public static final State INSTANCE = new State();
    }

    /* JADX INFO: renamed from: kotlin.sequences.FlatteningSequence$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: Sequences.kt */
    @Metadata
    public static final class AnonymousClass1 implements Iterator<E>, KMappedMarker {
        public Iterator itemIterator;
        public final Iterator iterator;
        public int state;

        public AnonymousClass1() {
            this.iterator = FlatteningSequence.this.sequence.iterator();
        }

        public final boolean ensureItemIterator() {
            Iterator it = this.itemIterator;
            if (it != null && it.hasNext()) {
                this.state = 1;
                return true;
            }
            while (this.iterator.hasNext()) {
                Iterator it2 = (Iterator) FlatteningSequence.this.iterator.invoke(FlatteningSequence.this.transformer.invoke(this.iterator.next()));
                if (it2.hasNext()) {
                    this.itemIterator = it2;
                    this.state = 1;
                    return true;
                }
            }
            this.state = 2;
            this.itemIterator = null;
            return false;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            int i = this.state;
            if (i == 1) {
                return true;
            }
            if (i == 2) {
                return false;
            }
            return ensureItemIterator();
        }

        @Override // java.util.Iterator
        public Object next() {
            int i = this.state;
            if (i == 2) {
                throw new NoSuchElementException();
            }
            if (i == 0 && !ensureItemIterator()) {
                throw new NoSuchElementException();
            }
            this.state = 0;
            Iterator it = this.itemIterator;
            Intrinsics.checkNotNull(it);
            return it.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // kotlin.sequences.Sequence
    public Iterator iterator() {
        return new AnonymousClass1();
    }
}

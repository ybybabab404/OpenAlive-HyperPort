package kotlin.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.ByteIterator;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: IOStreams.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ByteStreamsKt$iterator$1 extends ByteIterator {
    public final /* synthetic */ BufferedInputStream $this_iterator;
    public boolean finished;
    public int nextByte;
    public boolean nextPrepared;

    @Override // java.util.Iterator
    public boolean hasNext() throws IOException {
        prepareNext();
        return !this.finished;
    }

    @Override // kotlin.collections.ByteIterator
    public byte nextByte() throws IOException {
        prepareNext();
        if (this.finished) {
            throw new NoSuchElementException("Input stream is over.");
        }
        byte b = (byte) this.nextByte;
        this.nextPrepared = false;
        return b;
    }

    public final void prepareNext() throws IOException {
        if (this.nextPrepared || this.finished) {
            return;
        }
        int i = this.$this_iterator.read();
        this.nextByte = i;
        this.nextPrepared = true;
        this.finished = i == -1;
    }
}

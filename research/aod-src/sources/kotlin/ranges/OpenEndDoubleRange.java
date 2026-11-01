package kotlin.ranges;

import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Ranges.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
final class OpenEndDoubleRange implements OpenEndRange<Double> {
    public final double _endExclusive;
    public final double _start;

    public boolean equals(Object obj) {
        if (!(obj instanceof OpenEndDoubleRange)) {
            return false;
        }
        if (isEmpty() && ((OpenEndDoubleRange) obj).isEmpty()) {
            return true;
        }
        OpenEndDoubleRange openEndDoubleRange = (OpenEndDoubleRange) obj;
        return this._start == openEndDoubleRange._start && this._endExclusive == openEndDoubleRange._endExclusive;
    }

    @Override // kotlin.ranges.OpenEndRange
    public Double getEndExclusive() {
        return Double.valueOf(this._endExclusive);
    }

    @Override // kotlin.ranges.OpenEndRange
    public Double getStart() {
        return Double.valueOf(this._start);
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (Double.hashCode(this._start) * 31) + Double.hashCode(this._endExclusive);
    }

    public boolean isEmpty() {
        return this._start >= this._endExclusive;
    }

    public String toString() {
        return this._start + "..<" + this._endExclusive;
    }
}

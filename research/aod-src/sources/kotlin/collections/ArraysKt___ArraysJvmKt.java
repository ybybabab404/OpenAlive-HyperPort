package kotlin.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/* JADX INFO: Access modifiers changed from: package-private */
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
/* JADX INFO: compiled from: _ArraysJvm.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class ArraysKt___ArraysJvmKt extends ArraysKt__ArraysKt {

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$1, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    public final class AnonymousClass1 extends AbstractList<Byte> implements RandomAccess {
        public final /* synthetic */ byte[] $this_asList;

        public boolean contains(byte b) {
            return ArraysKt___ArraysKt.contains(this.$this_asList, b);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Byte) {
                return contains(((Number) obj).byteValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Byte get(int i) {
            return Byte.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        public int indexOf(byte b) {
            return ArraysKt___ArraysKt.indexOf(this.$this_asList, b);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Byte) {
                return indexOf(((Number) obj).byteValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(byte b) {
            return ArraysKt___ArraysKt.lastIndexOf(this.$this_asList, b);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Byte) {
                return lastIndexOf(((Number) obj).byteValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$2, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    public final class AnonymousClass2 extends AbstractList<Short> implements RandomAccess {
        public final /* synthetic */ short[] $this_asList;

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Short) {
                return contains(((Number) obj).shortValue());
            }
            return false;
        }

        public boolean contains(short s) {
            return ArraysKt___ArraysKt.contains(this.$this_asList, s);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Short get(int i) {
            return Short.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Short) {
                return indexOf(((Number) obj).shortValue());
            }
            return -1;
        }

        public int indexOf(short s) {
            return ArraysKt___ArraysKt.indexOf(this.$this_asList, s);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Short) {
                return lastIndexOf(((Number) obj).shortValue());
            }
            return -1;
        }

        public int lastIndexOf(short s) {
            return ArraysKt___ArraysKt.lastIndexOf(this.$this_asList, s);
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$3, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    public static final class AnonymousClass3 extends AbstractList<Integer> implements RandomAccess {
        public final /* synthetic */ int[] $this_asList;

        public AnonymousClass3(int[] iArr) {
            this.$this_asList = iArr;
        }

        public boolean contains(int i) {
            return ArraysKt___ArraysKt.contains(this.$this_asList, i);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Integer) {
                return contains(((Number) obj).intValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Integer get(int i) {
            return Integer.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        public int indexOf(int i) {
            return ArraysKt___ArraysKt.indexOf(this.$this_asList, i);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Integer) {
                return indexOf(((Number) obj).intValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(int i) {
            return ArraysKt___ArraysKt.lastIndexOf(this.$this_asList, i);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Integer) {
                return lastIndexOf(((Number) obj).intValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$4, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    public final class AnonymousClass4 extends AbstractList<Long> implements RandomAccess {
        public final /* synthetic */ long[] $this_asList;

        public boolean contains(long j) {
            return ArraysKt___ArraysKt.contains(this.$this_asList, j);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Long) {
                return contains(((Number) obj).longValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Long get(int i) {
            return Long.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        public int indexOf(long j) {
            return ArraysKt___ArraysKt.indexOf(this.$this_asList, j);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Long) {
                return indexOf(((Number) obj).longValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(long j) {
            return ArraysKt___ArraysKt.lastIndexOf(this.$this_asList, j);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Long) {
                return lastIndexOf(((Number) obj).longValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$5, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    @SourceDebugExtension
    public static final class AnonymousClass5 extends AbstractList<Float> implements RandomAccess {
        public final /* synthetic */ float[] $this_asList;

        public AnonymousClass5(float[] fArr) {
            this.$this_asList = fArr;
        }

        public boolean contains(float f) {
            for (float f2 : this.$this_asList) {
                if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                    return true;
                }
            }
            return false;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Float) {
                return contains(((Number) obj).floatValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Float get(int i) {
            return Float.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        public int indexOf(float f) {
            float[] fArr = this.$this_asList;
            int length = fArr.length;
            for (int i = 0; i < length; i++) {
                if (Float.floatToIntBits(fArr[i]) == Float.floatToIntBits(f)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Float) {
                return indexOf(((Number) obj).floatValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(float f) {
            float[] fArr = this.$this_asList;
            int length = fArr.length - 1;
            if (length >= 0) {
                while (true) {
                    int i = length - 1;
                    if (Float.floatToIntBits(fArr[length]) == Float.floatToIntBits(f)) {
                        return length;
                    }
                    if (i >= 0) {
                        length = i;
                    }
                }
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Float) {
                return lastIndexOf(((Number) obj).floatValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$6, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    @SourceDebugExtension
    public final class AnonymousClass6 extends AbstractList<Double> implements RandomAccess {
        public final /* synthetic */ double[] $this_asList;

        public boolean contains(double d) {
            for (double d2 : this.$this_asList) {
                if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
                    return true;
                }
            }
            return false;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Double) {
                return contains(((Number) obj).doubleValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Double get(int i) {
            return Double.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        public int indexOf(double d) {
            double[] dArr = this.$this_asList;
            int length = dArr.length;
            for (int i = 0; i < length; i++) {
                if (Double.doubleToLongBits(dArr[i]) == Double.doubleToLongBits(d)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Double) {
                return indexOf(((Number) obj).doubleValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(double d) {
            double[] dArr = this.$this_asList;
            int length = dArr.length - 1;
            if (length >= 0) {
                while (true) {
                    int i = length - 1;
                    if (Double.doubleToLongBits(dArr[length]) == Double.doubleToLongBits(d)) {
                        return length;
                    }
                    if (i >= 0) {
                        length = i;
                    }
                }
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Double) {
                return lastIndexOf(((Number) obj).doubleValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$7, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    public final class AnonymousClass7 extends AbstractList<Boolean> implements RandomAccess {
        public final /* synthetic */ boolean[] $this_asList;

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Boolean) {
                return contains(((Boolean) obj).booleanValue());
            }
            return false;
        }

        public boolean contains(boolean z) {
            return ArraysKt___ArraysKt.contains(this.$this_asList, z);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Boolean get(int i) {
            return Boolean.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Boolean) {
                return indexOf(((Boolean) obj).booleanValue());
            }
            return -1;
        }

        public int indexOf(boolean z) {
            return ArraysKt___ArraysKt.indexOf(this.$this_asList, z);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Boolean) {
                return lastIndexOf(((Boolean) obj).booleanValue());
            }
            return -1;
        }

        public int lastIndexOf(boolean z) {
            return ArraysKt___ArraysKt.lastIndexOf(this.$this_asList, z);
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$8, reason: invalid class name */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata
    public final class AnonymousClass8 extends AbstractList<Character> implements RandomAccess {
        public final /* synthetic */ char[] $this_asList;

        public boolean contains(char c) {
            return ArraysKt___ArraysKt.contains(this.$this_asList, c);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Character) {
                return contains(((Character) obj).charValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public Character get(int i) {
            return Character.valueOf(this.$this_asList[i]);
        }

        @Override // kotlin.collections.AbstractCollection
        public int getSize() {
            return this.$this_asList.length;
        }

        public int indexOf(char c) {
            return ArraysKt___ArraysKt.indexOf(this.$this_asList, c);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Character) {
                return indexOf(((Character) obj).charValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(char c) {
            return ArraysKt___ArraysKt.lastIndexOf(this.$this_asList, c);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Character) {
                return lastIndexOf(((Character) obj).charValue());
            }
            return -1;
        }
    }

    public static List asList(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<this>");
        return new AnonymousClass5(fArr);
    }

    public static List asList(int[] iArr) {
        Intrinsics.checkNotNullParameter(iArr, "<this>");
        return new AnonymousClass3(iArr);
    }

    public static List asList(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        List listAsList = ArraysUtilJVM.asList(objArr);
        Intrinsics.checkNotNullExpressionValue(listAsList, "asList(...)");
        return listAsList;
    }

    public static byte[] copyInto(byte[] bArr, byte[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy(bArr, i2, destination, i, i3 - i2);
        return destination;
    }

    public static int[] copyInto(int[] iArr, int[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(iArr, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy(iArr, i2, destination, i, i3 - i2);
        return destination;
    }

    public static long[] copyInto(long[] jArr, long[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(jArr, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy(jArr, i2, destination, i, i3 - i2);
        return destination;
    }

    public static Object[] copyInto(Object[] objArr, Object[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy(objArr, i2, destination, i, i3 - i2);
        return destination;
    }

    public static /* synthetic */ int[] copyInto$default(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = iArr.length;
        }
        return copyInto(iArr, iArr2, i, i2, i3);
    }

    public static /* synthetic */ Object[] copyInto$default(Object[] objArr, Object[] objArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = objArr.length;
        }
        return copyInto(objArr, objArr2, i, i2, i3);
    }

    public static Object[] copyOfRange(Object[] objArr, int i, int i2) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(i2, objArr.length);
        Object[] objArrCopyOfRange = Arrays.copyOfRange(objArr, i, i2);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOfRange, "copyOfRange(...)");
        return objArrCopyOfRange;
    }

    public static final void fill(int[] iArr, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(iArr, "<this>");
        Arrays.fill(iArr, i2, i3, i);
    }

    public static final void fill(long[] jArr, long j, int i, int i2) {
        Intrinsics.checkNotNullParameter(jArr, "<this>");
        Arrays.fill(jArr, i, i2, j);
    }

    public static void fill(Object[] objArr, Object obj, int i, int i2) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        Arrays.fill(objArr, i, i2, obj);
    }

    public static /* synthetic */ void fill$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        fill(iArr, i, i2, i3);
    }

    public static /* synthetic */ void fill$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        fill(jArr, j, i, i2);
    }

    public static /* synthetic */ void fill$default(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        fill(objArr, obj, i, i2);
    }

    public static final void sort(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        if (objArr.length > 1) {
            Arrays.sort(objArr);
        }
    }

    public static final void sortWith(Object[] objArr, Comparator comparator) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        if (objArr.length > 1) {
            Arrays.sort(objArr, comparator);
        }
    }
}

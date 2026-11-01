package okio;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import miuix.animation.internal.TransitionInfo;

/* JADX INFO: loaded from: classes3.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    public final ByteString[] byteStrings;
    public final int[] trie;

    public Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    public static void buildTrieRecursive(long j, Buffer buffer, int i, List list, int i2, int i3, List list2) {
        int i4;
        int i5;
        long j2;
        int i6;
        List list3;
        long j3;
        int i7;
        List list4 = list;
        List list5 = list2;
        if (i2 >= i3) {
            throw new AssertionError();
        }
        for (int i8 = i2; i8 < i3; i8++) {
            if (((ByteString) list4.get(i8)).size() < i) {
                throw new AssertionError();
            }
        }
        ByteString byteString = (ByteString) list.get(i2);
        ByteString byteString2 = (ByteString) list4.get(i3 - 1);
        if (i == byteString.size()) {
            int iIntValue = ((Integer) list5.get(i2)).intValue();
            int i9 = i2 + 1;
            ByteString byteString3 = (ByteString) list4.get(i9);
            i4 = i9;
            i5 = iIntValue;
            byteString = byteString3;
        } else {
            i4 = i2;
            i5 = -1;
        }
        long j4 = 2;
        if (byteString.getByte(i) == byteString2.getByte(i)) {
            int iMin = Math.min(byteString.size(), byteString2.size());
            int i10 = 0;
            int i11 = i;
            while (true) {
                if (i11 >= iMin) {
                    j2 = j4;
                    break;
                }
                j2 = j4;
                if (byteString.getByte(i11) != byteString2.getByte(i11)) {
                    break;
                }
                i10++;
                i11++;
                j4 = j2;
            }
            long jIntCount = j + ((long) intCount(buffer)) + j2 + ((long) i10) + 1;
            buffer.writeInt(-i10);
            buffer.writeInt(i5);
            int i12 = i;
            while (true) {
                i6 = i + i10;
                if (i12 >= i6) {
                    break;
                }
                buffer.writeInt(byteString.getByte(i12) & TransitionInfo.INIT);
                i12++;
            }
            if (i4 + 1 == i3) {
                if (i6 != ((ByteString) list4.get(i4)).size()) {
                    throw new AssertionError();
                }
                buffer.writeInt(((Integer) list5.get(i4)).intValue());
                return;
            } else {
                Buffer buffer2 = new Buffer();
                buffer.writeInt((int) ((((long) intCount(buffer2)) + jIntCount) * (-1)));
                buildTrieRecursive(jIntCount, buffer2, i6, list4, i4, i3, list5);
                buffer.write(buffer2, buffer2.size());
                return;
            }
        }
        int i13 = 1;
        for (int i14 = i4 + 1; i14 < i3; i14++) {
            if (((ByteString) list4.get(i14 - 1)).getByte(i) != ((ByteString) list4.get(i14)).getByte(i)) {
                i13++;
            }
        }
        long jIntCount2 = j + ((long) intCount(buffer)) + 2 + ((long) (i13 * 2));
        buffer.writeInt(i13);
        buffer.writeInt(i5);
        for (int i15 = i4; i15 < i3; i15++) {
            byte b = ((ByteString) list4.get(i15)).getByte(i);
            if (i15 == i4 || b != ((ByteString) list4.get(i15 - 1)).getByte(i)) {
                buffer.writeInt(b & TransitionInfo.INIT);
            }
        }
        Buffer buffer3 = new Buffer();
        int i16 = i4;
        while (i16 < i3) {
            byte b2 = ((ByteString) list4.get(i16)).getByte(i);
            int i17 = i16 + 1;
            int i18 = i17;
            while (true) {
                if (i18 >= i3) {
                    i18 = i3;
                    break;
                } else if (b2 != ((ByteString) list4.get(i18)).getByte(i)) {
                    break;
                } else {
                    i18++;
                }
            }
            if (i17 == i18 && i + 1 == ((ByteString) list4.get(i16)).size()) {
                buffer.writeInt(((Integer) list5.get(i16)).intValue());
                list3 = list5;
                j3 = jIntCount2;
                i7 = i18;
            } else {
                buffer.writeInt((int) ((((long) intCount(buffer3)) + jIntCount2) * (-1)));
                list3 = list5;
                j3 = jIntCount2;
                i7 = i18;
                buildTrieRecursive(j3, buffer3, i + 1, list, i16, i7, list3);
                list4 = list;
            }
            jIntCount2 = j3;
            i16 = i7;
            list5 = list3;
        }
        buffer.write(buffer3, buffer3.size());
    }

    public static int intCount(Buffer buffer) {
        return (int) (buffer.size() / 4);
    }

    public static Options of(ByteString... byteStringArr) {
        if (byteStringArr.length == 0) {
            return new Options(new ByteString[0], new int[]{0, -1});
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(byteStringArr));
        Collections.sort(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(-1);
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.set(Collections.binarySearch(arrayList, byteStringArr[i2]), Integer.valueOf(i2));
        }
        if (((ByteString) arrayList.get(0)).size() == 0) {
            throw new IllegalArgumentException("the empty byte string is not a supported option");
        }
        int i3 = 0;
        while (i3 < arrayList.size()) {
            ByteString byteString = (ByteString) arrayList.get(i3);
            int i4 = i3 + 1;
            int i5 = i4;
            while (i5 < arrayList.size()) {
                ByteString byteString2 = (ByteString) arrayList.get(i5);
                if (!byteString2.startsWith(byteString)) {
                    break;
                }
                if (byteString2.size() == byteString.size()) {
                    throw new IllegalArgumentException("duplicate option: " + byteString2);
                }
                if (((Integer) arrayList2.get(i5)).intValue() > ((Integer) arrayList2.get(i3)).intValue()) {
                    arrayList.remove(i5);
                    arrayList2.remove(i5);
                } else {
                    i5++;
                }
            }
            i3 = i4;
        }
        Buffer buffer = new Buffer();
        buildTrieRecursive(0L, buffer, 0, arrayList, 0, arrayList.size(), arrayList2);
        int iIntCount = intCount(buffer);
        int[] iArr = new int[iIntCount];
        for (int i6 = 0; i6 < iIntCount; i6++) {
            iArr[i6] = buffer.readInt();
        }
        if (buffer.exhausted()) {
            return new Options((ByteString[]) byteStringArr.clone(), iArr);
        }
        throw new AssertionError();
    }

    @Override // java.util.AbstractList, java.util.List
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.byteStrings.length;
    }
}

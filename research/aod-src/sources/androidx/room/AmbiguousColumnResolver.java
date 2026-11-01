package androidx.room;

import androidx.annotation.RestrictTo;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;

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
/* JADX INFO: compiled from: AmbiguousColumnResolver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
@SourceDebugExtension
public final class AmbiguousColumnResolver {
    public static final AmbiguousColumnResolver INSTANCE = new AmbiguousColumnResolver();

    /* JADX INFO: compiled from: AmbiguousColumnResolver.kt */
    @Metadata
    public static final class Match {
        public final List resultIndices;
        public final IntRange resultRange;

        public Match(IntRange resultRange, List resultIndices) {
            Intrinsics.checkNotNullParameter(resultRange, "resultRange");
            Intrinsics.checkNotNullParameter(resultIndices, "resultIndices");
            this.resultRange = resultRange;
            this.resultIndices = resultIndices;
        }

        public final List getResultIndices() {
            return this.resultIndices;
        }

        public final IntRange getResultRange() {
            return this.resultRange;
        }
    }

    /* JADX INFO: compiled from: AmbiguousColumnResolver.kt */
    @Metadata
    public static final class ResultColumn {
        public final int index;
        public final String name;

        public final String component1() {
            return this.name;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ResultColumn)) {
                return false;
            }
            ResultColumn resultColumn = (ResultColumn) obj;
            return Intrinsics.areEqual(this.name, resultColumn.name) && this.index == resultColumn.index;
        }

        public final int getIndex() {
            return this.index;
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + Integer.hashCode(this.index);
        }

        public String toString() {
            return "ResultColumn(name=" + this.name + ", index=" + this.index + ')';
        }
    }

    /* JADX INFO: compiled from: AmbiguousColumnResolver.kt */
    @Metadata
    public static final class Solution implements Comparable<Solution> {
        public static final Companion Companion = new Companion(null);
        public static final Solution NO_SOLUTION = new Solution(CollectionsKt.emptyList(), Integer.MAX_VALUE, Integer.MAX_VALUE);
        public final int coverageOffset;
        public final List matches;
        public final int overlaps;

        /* JADX INFO: compiled from: AmbiguousColumnResolver.kt */
        @Metadata
        @SourceDebugExtension
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final Solution build(List matches) {
                Intrinsics.checkNotNullParameter(matches, "matches");
                Iterator it = matches.iterator();
                int i = 0;
                int last = 0;
                while (it.hasNext()) {
                    Match match = (Match) it.next();
                    last += ((match.getResultRange().getLast() - match.getResultRange().getFirst()) + 1) - match.getResultIndices().size();
                }
                Iterator it2 = matches.iterator();
                if (!it2.hasNext()) {
                    throw new NoSuchElementException();
                }
                int first = ((Match) it2.next()).getResultRange().getFirst();
                while (it2.hasNext()) {
                    int first2 = ((Match) it2.next()).getResultRange().getFirst();
                    if (first > first2) {
                        first = first2;
                    }
                }
                Iterator it3 = matches.iterator();
                if (!it3.hasNext()) {
                    throw new NoSuchElementException();
                }
                int last2 = ((Match) it3.next()).getResultRange().getLast();
                while (it3.hasNext()) {
                    int last3 = ((Match) it3.next()).getResultRange().getLast();
                    if (last2 < last3) {
                        last2 = last3;
                    }
                }
                Iterable intRange = new IntRange(first, last2);
                if (!(intRange instanceof Collection) || !((Collection) intRange).isEmpty()) {
                    Iterator it4 = intRange.iterator();
                    int i2 = 0;
                    while (it4.hasNext()) {
                        int iNextInt = ((IntIterator) it4).nextInt();
                        Iterator it5 = matches.iterator();
                        int i3 = 0;
                        while (it5.hasNext()) {
                            if (((Match) it5.next()).getResultRange().contains(iNextInt)) {
                                i3++;
                            }
                            if (i3 > 1) {
                                i2++;
                                if (i2 >= 0) {
                                    break;
                                }
                                CollectionsKt.throwCountOverflow();
                                break;
                            }
                        }
                    }
                    i = i2;
                }
                return new Solution(matches, last, i);
            }
        }

        public Solution(List matches, int i, int i2) {
            Intrinsics.checkNotNullParameter(matches, "matches");
            this.matches = matches;
            this.coverageOffset = i;
            this.overlaps = i2;
        }

        @Override // java.lang.Comparable
        public int compareTo(Solution other) {
            Intrinsics.checkNotNullParameter(other, "other");
            int iCompare = Intrinsics.compare(this.overlaps, other.overlaps);
            return iCompare != 0 ? iCompare : Intrinsics.compare(this.coverageOffset, other.coverageOffset);
        }
    }
}

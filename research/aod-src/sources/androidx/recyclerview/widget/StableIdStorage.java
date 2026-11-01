package androidx.recyclerview.widget;

import androidx.collection.LongSparseArray;

/* JADX INFO: loaded from: classes.dex */
interface StableIdStorage {

    public static class IsolatedStableIdStorage implements StableIdStorage {
        public long mNextStableId;

        public class WrapperStableIdLookup implements StableIdLookup {
            public final LongSparseArray mLocalToGlobalLookup;
            public final /* synthetic */ IsolatedStableIdStorage this$0;

            @Override // androidx.recyclerview.widget.StableIdStorage.StableIdLookup
            public long localToGlobal(long j) {
                Long lValueOf = (Long) this.mLocalToGlobalLookup.get(j);
                if (lValueOf == null) {
                    lValueOf = Long.valueOf(this.this$0.obtainId());
                    this.mLocalToGlobalLookup.put(j, lValueOf);
                }
                return lValueOf.longValue();
            }
        }

        public long obtainId() {
            long j = this.mNextStableId;
            this.mNextStableId = 1 + j;
            return j;
        }
    }

    public static class NoStableIdStorage implements StableIdStorage {

        /* JADX INFO: renamed from: androidx.recyclerview.widget.StableIdStorage$NoStableIdStorage$1, reason: invalid class name */
        public class AnonymousClass1 implements StableIdLookup {
            @Override // androidx.recyclerview.widget.StableIdStorage.StableIdLookup
            public long localToGlobal(long j) {
                return -1L;
            }
        }
    }

    public static class SharedPoolStableIdStorage implements StableIdStorage {

        /* JADX INFO: renamed from: androidx.recyclerview.widget.StableIdStorage$SharedPoolStableIdStorage$1, reason: invalid class name */
        public class AnonymousClass1 implements StableIdLookup {
            @Override // androidx.recyclerview.widget.StableIdStorage.StableIdLookup
            public long localToGlobal(long j) {
                return j;
            }
        }
    }

    public interface StableIdLookup {
        long localToGlobal(long j);
    }
}

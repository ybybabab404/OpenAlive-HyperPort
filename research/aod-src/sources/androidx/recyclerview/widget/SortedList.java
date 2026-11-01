package androidx.recyclerview.widget;

import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public class SortedList<T> {

    public static class BatchedCallback<T2> extends Callback<T2> {
        public final BatchingListUpdateCallback mBatchingListUpdateCallback;
        public final Callback mWrappedCallback;

        @Override // androidx.recyclerview.widget.SortedList.Callback, java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return this.mWrappedCallback.compare(obj, obj2);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        public void onChanged(int i, int i2) {
            this.mBatchingListUpdateCallback.onChanged(i, i2, null);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback, androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int i, int i2, Object obj) {
            this.mBatchingListUpdateCallback.onChanged(i, i2, obj);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(int i, int i2) {
            this.mBatchingListUpdateCallback.onInserted(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(int i, int i2) {
            this.mBatchingListUpdateCallback.onMoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(int i, int i2) {
            this.mBatchingListUpdateCallback.onRemoved(i, i2);
        }
    }

    public static abstract class Callback<T2> implements Comparator<T2>, ListUpdateCallback {
        @Override // java.util.Comparator
        public abstract int compare(Object obj, Object obj2);

        public abstract void onChanged(int i, int i2);

        public void onChanged(int i, int i2, Object obj) {
            onChanged(i, i2);
        }
    }
}

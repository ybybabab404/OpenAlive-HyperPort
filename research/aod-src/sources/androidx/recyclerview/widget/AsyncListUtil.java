package androidx.recyclerview.widget;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes.dex */
public class AsyncListUtil<T> {
    public boolean mAllowScrollHints;
    public final ThreadUtil.BackgroundCallback mBackgroundProxy;
    public final DataCallback mDataCallback;
    public int mDisplayedGeneration;
    public int mItemCount;
    public final ThreadUtil.MainThreadCallback mMainThreadProxy;
    public final SparseIntArray mMissingPositions;
    public final int[] mPrevRange;
    public int mRequestedGeneration;
    public int mScrollHint;
    public final Class mTClass;
    public final TileList mTileList;
    public final int mTileSize;
    public final int[] mTmpRange;
    public final int[] mTmpRangeExtended;
    public final ViewCallback mViewCallback;

    /* JADX INFO: renamed from: androidx.recyclerview.widget.AsyncListUtil$1, reason: invalid class name */
    public class AnonymousClass1 implements ThreadUtil.MainThreadCallback<T> {
        public final /* synthetic */ AsyncListUtil this$0;

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void addTile(int i, TileList.Tile tile) {
            if (!isRequestedGeneration(i)) {
                this.this$0.mBackgroundProxy.recycleTile(tile);
                return;
            }
            TileList.Tile tileAddOrReplace = this.this$0.mTileList.addOrReplace(tile);
            if (tileAddOrReplace != null) {
                Log.e("AsyncListUtil", "duplicate tile @" + tileAddOrReplace.mStartPosition);
                this.this$0.mBackgroundProxy.recycleTile(tileAddOrReplace);
            }
            int i2 = tile.mStartPosition + tile.mItemCount;
            int i3 = 0;
            while (i3 < this.this$0.mMissingPositions.size()) {
                int iKeyAt = this.this$0.mMissingPositions.keyAt(i3);
                if (tile.mStartPosition > iKeyAt || iKeyAt >= i2) {
                    i3++;
                } else {
                    this.this$0.mMissingPositions.removeAt(i3);
                    this.this$0.mViewCallback.onItemLoaded(iKeyAt);
                }
            }
        }

        public final boolean isRequestedGeneration(int i) {
            return i == this.this$0.mRequestedGeneration;
        }

        public final void recycleAllTiles() {
            for (int i = 0; i < this.this$0.mTileList.size(); i++) {
                AsyncListUtil asyncListUtil = this.this$0;
                asyncListUtil.mBackgroundProxy.recycleTile(asyncListUtil.mTileList.getAtIndex(i));
            }
            this.this$0.mTileList.clear();
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void removeTile(int i, int i2) {
            if (isRequestedGeneration(i)) {
                TileList.Tile tileRemoveAtPos = this.this$0.mTileList.removeAtPos(i2);
                if (tileRemoveAtPos != null) {
                    this.this$0.mBackgroundProxy.recycleTile(tileRemoveAtPos);
                    return;
                }
                Log.e("AsyncListUtil", "tile not found @" + i2);
            }
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void updateItemCount(int i, int i2) {
            if (isRequestedGeneration(i)) {
                AsyncListUtil asyncListUtil = this.this$0;
                asyncListUtil.mItemCount = i2;
                asyncListUtil.mViewCallback.onDataRefresh();
                AsyncListUtil asyncListUtil2 = this.this$0;
                asyncListUtil2.mDisplayedGeneration = asyncListUtil2.mRequestedGeneration;
                recycleAllTiles();
                AsyncListUtil asyncListUtil3 = this.this$0;
                asyncListUtil3.mAllowScrollHints = false;
                asyncListUtil3.updateRange();
            }
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.AsyncListUtil$2, reason: invalid class name */
    public class AnonymousClass2 implements ThreadUtil.BackgroundCallback<T> {
        public int mFirstRequiredTileStart;
        public int mGeneration;
        public int mItemCount;
        public int mLastRequiredTileStart;
        public final SparseBooleanArray mLoadedTiles;
        public TileList.Tile mRecycledRoot;
        public final /* synthetic */ AsyncListUtil this$0;

        public final TileList.Tile acquireTile() {
            TileList.Tile tile = this.mRecycledRoot;
            if (tile != null) {
                this.mRecycledRoot = tile.mNext;
                return tile;
            }
            AsyncListUtil asyncListUtil = this.this$0;
            return new TileList.Tile(asyncListUtil.mTClass, asyncListUtil.mTileSize);
        }

        public final void addTile(TileList.Tile tile) {
            this.mLoadedTiles.put(tile.mStartPosition, true);
            this.this$0.mMainThreadProxy.addTile(this.mGeneration, tile);
        }

        public final void flushTileCache(int i) {
            int maxCachedTiles = this.this$0.mDataCallback.getMaxCachedTiles();
            while (this.mLoadedTiles.size() >= maxCachedTiles) {
                int iKeyAt = this.mLoadedTiles.keyAt(0);
                SparseBooleanArray sparseBooleanArray = this.mLoadedTiles;
                int iKeyAt2 = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
                int i2 = this.mFirstRequiredTileStart - iKeyAt;
                int i3 = iKeyAt2 - this.mLastRequiredTileStart;
                if (i2 > 0 && (i2 >= i3 || i == 2)) {
                    removeTile(iKeyAt);
                } else {
                    if (i3 <= 0) {
                        return;
                    }
                    if (i2 >= i3 && i != 1) {
                        return;
                    } else {
                        removeTile(iKeyAt2);
                    }
                }
            }
        }

        public final int getTileStart(int i) {
            return i - (i % this.this$0.mTileSize);
        }

        public final boolean isTileLoaded(int i) {
            return this.mLoadedTiles.get(i);
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void loadTile(int i, int i2) {
            if (isTileLoaded(i)) {
                return;
            }
            TileList.Tile tileAcquireTile = acquireTile();
            tileAcquireTile.mStartPosition = i;
            int iMin = Math.min(this.this$0.mTileSize, this.mItemCount - i);
            tileAcquireTile.mItemCount = iMin;
            this.this$0.mDataCallback.fillData(tileAcquireTile.mItems, tileAcquireTile.mStartPosition, iMin);
            flushTileCache(i2);
            addTile(tileAcquireTile);
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void recycleTile(TileList.Tile tile) {
            this.this$0.mDataCallback.recycleData(tile.mItems, tile.mItemCount);
            tile.mNext = this.mRecycledRoot;
            this.mRecycledRoot = tile;
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void refresh(int i) {
            this.mGeneration = i;
            this.mLoadedTiles.clear();
            int iRefreshData = this.this$0.mDataCallback.refreshData();
            this.mItemCount = iRefreshData;
            this.this$0.mMainThreadProxy.updateItemCount(this.mGeneration, iRefreshData);
        }

        public final void removeTile(int i) {
            this.mLoadedTiles.delete(i);
            this.this$0.mMainThreadProxy.removeTile(this.mGeneration, i);
        }

        public final void requestTiles(int i, int i2, int i3, boolean z) {
            int i4 = i;
            while (i4 <= i2) {
                this.this$0.mBackgroundProxy.loadTile(z ? (i2 + i) - i4 : i4, i3);
                i4 += this.this$0.mTileSize;
            }
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void updateRange(int i, int i2, int i3, int i4, int i5) {
            if (i > i2) {
                return;
            }
            int tileStart = getTileStart(i);
            int tileStart2 = getTileStart(i2);
            this.mFirstRequiredTileStart = getTileStart(i3);
            int tileStart3 = getTileStart(i4);
            this.mLastRequiredTileStart = tileStart3;
            if (i5 == 1) {
                requestTiles(this.mFirstRequiredTileStart, tileStart2, i5, true);
                requestTiles(tileStart2 + this.this$0.mTileSize, this.mLastRequiredTileStart, i5, false);
            } else {
                requestTiles(tileStart, tileStart3, i5, false);
                requestTiles(this.mFirstRequiredTileStart, tileStart - this.this$0.mTileSize, i5, true);
            }
        }
    }

    public static abstract class DataCallback<T> {
        public abstract void fillData(Object[] objArr, int i, int i2);

        public int getMaxCachedTiles() {
            return 10;
        }

        public void recycleData(Object[] objArr, int i) {
        }

        public abstract int refreshData();
    }

    public static abstract class ViewCallback {
        public void extendRangeInto(int[] iArr, int[] iArr2, int i) {
            int i2 = iArr[1];
            int i3 = iArr[0];
            int i4 = (i2 - i3) + 1;
            int i5 = i4 / 2;
            iArr2[0] = i3 - (i == 1 ? i4 : i5);
            if (i != 2) {
                i4 = i5;
            }
            iArr2[1] = i2 + i4;
        }

        public abstract void getItemRangeInto(int[] iArr);

        public abstract void onDataRefresh();

        public abstract void onItemLoaded(int i);
    }

    public void updateRange() {
        int i;
        this.mViewCallback.getItemRangeInto(this.mTmpRange);
        int[] iArr = this.mTmpRange;
        int i2 = iArr[0];
        int i3 = iArr[1];
        if (i2 > i3 || i2 < 0 || i3 >= this.mItemCount) {
            return;
        }
        if (this.mAllowScrollHints) {
            int[] iArr2 = this.mPrevRange;
            if (i2 > iArr2[1] || (i = iArr2[0]) > i3) {
                this.mScrollHint = 0;
            } else if (i2 < i) {
                this.mScrollHint = 1;
            } else if (i2 > i) {
                this.mScrollHint = 2;
            }
        } else {
            this.mScrollHint = 0;
        }
        int[] iArr3 = this.mPrevRange;
        iArr3[0] = i2;
        iArr3[1] = i3;
        this.mViewCallback.extendRangeInto(iArr, this.mTmpRangeExtended, this.mScrollHint);
        int[] iArr4 = this.mTmpRangeExtended;
        iArr4[0] = Math.min(this.mTmpRange[0], Math.max(iArr4[0], 0));
        int[] iArr5 = this.mTmpRangeExtended;
        iArr5[1] = Math.max(this.mTmpRange[1], Math.min(iArr5[1], this.mItemCount - 1));
        ThreadUtil.BackgroundCallback backgroundCallback = this.mBackgroundProxy;
        int[] iArr6 = this.mTmpRange;
        int i4 = iArr6[0];
        int i5 = iArr6[1];
        int[] iArr7 = this.mTmpRangeExtended;
        backgroundCallback.updateRange(i4, i5, iArr7[0], iArr7[1], this.mScrollHint);
    }
}

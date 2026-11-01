package androidx.recyclerview.widget;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
class TileList<T> {
    public Tile mLastAccessedTile;
    public final SparseArray mTiles;

    public static class Tile<T> {
        public int mItemCount;
        public final Object[] mItems;
        public Tile mNext;
        public int mStartPosition;

        public Tile(Class cls, int i) {
            this.mItems = (Object[]) Array.newInstance((Class<?>) cls, i);
        }
    }

    public Tile addOrReplace(Tile tile) {
        int iIndexOfKey = this.mTiles.indexOfKey(tile.mStartPosition);
        if (iIndexOfKey < 0) {
            this.mTiles.put(tile.mStartPosition, tile);
            return null;
        }
        Tile tile2 = (Tile) this.mTiles.valueAt(iIndexOfKey);
        this.mTiles.setValueAt(iIndexOfKey, tile);
        if (this.mLastAccessedTile == tile2) {
            this.mLastAccessedTile = tile;
        }
        return tile2;
    }

    public void clear() {
        this.mTiles.clear();
    }

    public Tile getAtIndex(int i) {
        if (i < 0 || i >= this.mTiles.size()) {
            return null;
        }
        return (Tile) this.mTiles.valueAt(i);
    }

    public Tile removeAtPos(int i) {
        Tile tile = (Tile) this.mTiles.get(i);
        if (this.mLastAccessedTile == tile) {
            this.mLastAccessedTile = null;
        }
        this.mTiles.delete(i);
        return tile;
    }

    public int size() {
        return this.mTiles.size();
    }
}

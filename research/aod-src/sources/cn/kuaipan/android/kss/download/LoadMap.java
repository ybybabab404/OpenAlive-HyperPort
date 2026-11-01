package cn.kuaipan.android.kss.download;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class LoadMap {
    public final BlockSpace[] mBlocks;
    public final ArrayList mEmptySpaces;
    public final HashMap mRecorders;

    public class BlockSpace {
        public final long end;
        public final ArrayList spaces;
        public final long start;
        public VerifyState verifyState;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Block(");
            sb.append(this.start);
            sb.append("-");
            sb.append(this.end);
            sb.append("):");
            if (this.spaces.isEmpty()) {
                sb.append(this.verifyState);
            } else {
                sb.append(Arrays.toString(this.spaces.toArray()));
            }
            return sb.toString();
        }

        public final synchronized boolean tryMerge(Space space) {
            if (space.size() <= 0) {
                this.spaces.remove(space);
                return true;
            }
            for (Space space2 : this.spaces) {
                if (space2 != space && space2.tryMerge(space)) {
                    return true;
                }
            }
            return false;
        }
    }

    public class Space {
        public final BlockSpace block;
        public long end;
        public long start;

        public long size() {
            long j;
            synchronized (this.block) {
                j = this.end - this.start;
            }
            return j;
        }

        public String toString() {
            return this.start + "-" + this.end;
        }

        public final boolean tryMerge() {
            return this.block.tryMerge(this);
        }

        public final boolean tryMerge(Space space) {
            if (space.start != this.end) {
                return false;
            }
            this.end = space.end;
            return true;
        }
    }

    public enum VerifyState {
        NOT_VERIFY,
        VERIFING,
        VERIFIED
    }

    public synchronized void recycleRecorder(LoadRecorder loadRecorder) {
        Space space = loadRecorder.getSpace();
        if (this.mRecorders.remove(space) == null) {
            return;
        }
        if (space.tryMerge()) {
            return;
        }
        this.mEmptySpaces.add(space);
    }

    public String toString() {
        return Arrays.toString(this.mBlocks);
    }
}

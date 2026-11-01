package com.bumptech.glide.load.engine.prefill;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class PreFillQueue {
    public final Map bitmapsPerType;
    public int bitmapsRemaining;
    public int keyIndex;
    public final List keyList;

    public boolean isEmpty() {
        return this.bitmapsRemaining == 0;
    }

    public PreFillType remove() {
        PreFillType preFillType = (PreFillType) this.keyList.get(this.keyIndex);
        Integer num = (Integer) this.bitmapsPerType.get(preFillType);
        if (num.intValue() == 1) {
            this.bitmapsPerType.remove(preFillType);
            this.keyList.remove(this.keyIndex);
        } else {
            this.bitmapsPerType.put(preFillType, Integer.valueOf(num.intValue() - 1));
        }
        this.bitmapsRemaining--;
        this.keyIndex = this.keyList.isEmpty() ? 0 : (this.keyIndex + 1) % this.keyList.size();
        return preFillType;
    }
}

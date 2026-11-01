package com.miui.aod.components.photo.Edit;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import java.util.BitSet;

/* JADX INFO: loaded from: classes.dex */
public class EventHandler extends Handler {
    private final BitSet mBitSet;
    private final SparseArray<Runnable> mCallbacks;

    public EventHandler(@NonNull Looper looper) {
        super(looper);
        this.mCallbacks = new SparseArray<>();
        this.mBitSet = new BitSet();
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Runnable runnable = this.mCallbacks.get(message.what);
        if (runnable != null) {
            runnable.run();
        }
    }

    public int register(Runnable runnable) {
        int iNextClearBit = this.mBitSet.nextClearBit(0);
        this.mCallbacks.put(iNextClearBit, runnable);
        this.mBitSet.set(iNextClearBit);
        return iNextClearBit;
    }

    public void unregister(int i) {
        this.mCallbacks.setValueAt(i, null);
        this.mBitSet.clear(i);
    }
}

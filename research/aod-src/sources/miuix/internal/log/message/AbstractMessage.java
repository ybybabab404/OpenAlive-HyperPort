package miuix.internal.log.message;

import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractMessage implements Message {
    public boolean mRecycled;

    public abstract void onRecycle();

    @Override // miuix.internal.log.message.Message
    public void prepareForReuse() {
        this.mRecycled = false;
    }

    @Override // miuix.internal.log.message.Message
    public void recycle() {
        if (this.mRecycled) {
            Log.w("AbstractMessage", "Recycle message twice");
            return;
        }
        onRecycle();
        this.mRecycled = true;
        MessageFactory.recycle(this);
    }
}

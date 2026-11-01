package androidx.legacy.content;

import android.content.BroadcastReceiver;
import android.util.SparseArray;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    public static final SparseArray sActiveWakeLocks = new SparseArray();
    public static int mNextId = 1;
}

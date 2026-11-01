package androidx.core.view;

import android.view.GestureDetector;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class GestureDetectorCompat {
    public final GestureDetector mDetector;

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mDetector.onTouchEvent(motionEvent);
    }
}

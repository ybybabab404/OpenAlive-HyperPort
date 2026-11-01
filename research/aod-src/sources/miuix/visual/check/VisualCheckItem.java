package miuix.visual.check;

import android.view.MotionEvent;

/* JADX INFO: loaded from: classes3.dex */
public interface VisualCheckItem {
    void onChecked(boolean z);

    void onVisualCheckBoxTouchEvent(VisualCheckBox visualCheckBox, MotionEvent motionEvent);
}

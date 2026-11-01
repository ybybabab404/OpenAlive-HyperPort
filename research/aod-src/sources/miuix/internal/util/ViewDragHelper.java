package miuix.internal.util;

import android.view.View;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes2.dex */
public class ViewDragHelper {
    public static final Interpolator sInterpolator = new Interpolator() { // from class: miuix.internal.util.ViewDragHelper.1
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    public final Callback mCallback;
    public View mCapturedView;
    public int mDragState;

    /* JADX INFO: renamed from: miuix.internal.util.ViewDragHelper$2, reason: invalid class name */
    public class AnonymousClass2 implements Runnable {
        public final /* synthetic */ ViewDragHelper this$0;

        @Override // java.lang.Runnable
        public void run() {
            this.this$0.setDragState(0);
        }
    }

    public static abstract class Callback {
        public void onViewDragStateChanged(int i) {
        }
    }

    public void setDragState(int i) {
        if (this.mDragState != i) {
            this.mDragState = i;
            this.mCallback.onViewDragStateChanged(i);
            if (i == 0) {
                this.mCapturedView = null;
            }
        }
    }
}

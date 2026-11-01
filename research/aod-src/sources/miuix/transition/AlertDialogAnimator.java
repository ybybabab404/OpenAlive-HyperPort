package miuix.transition;

import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import androidx.transition.MiuixTransitionManager;
import java.util.LinkedList;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;

/* JADX INFO: loaded from: classes3.dex */
public class AlertDialogAnimator extends LayoutAnimator {
    public final LinearLayout mButtonPanel;
    public final ViewGroup mDialogRootView;
    public boolean mDuringTransition;
    public LinkedList mPendingViewConfig;
    public AnimConfig mTransitionConfig;

    /* JADX INFO: renamed from: miuix.transition.AlertDialogAnimator$1, reason: invalid class name */
    class AnonymousClass1 implements ViewTreeObserver.OnPreDrawListener {
        public boolean lastUseVertical;
        public final /* synthetic */ AlertDialogAnimator this$0;

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            boolean z = this.this$0.mButtonPanel.getOrientation() == 1;
            if (z != this.lastUseVertical) {
                if (z) {
                    this.this$0.mTransitionConfig.setSpecial("left", (AnimSpecialConfig) null).setSpecial("right", (AnimSpecialConfig) null);
                } else {
                    this.this$0.mTransitionConfig.setSpecial("left", LayoutAnimator.spring(0.95f, 0.25f), new float[0]).setSpecial("right", LayoutAnimator.spring(0.95f, 0.25f), new float[0]);
                }
            }
            this.lastUseVertical = z;
            this.this$0.mButtonPanel.getViewTreeObserver().removeOnPreDrawListener(this);
            return true;
        }
    }

    /* JADX INFO: renamed from: miuix.transition.AlertDialogAnimator$2, reason: invalid class name */
    class AnonymousClass2 extends TransitionListenerAdapter {
        public final /* synthetic */ AlertDialogAnimator this$0;

        @Override // miuix.transition.TransitionListenerAdapter, miuix.transition.MiuixTransition.MiuixTransitionListener
        public void onTransitionEnd(MiuixTransition miuixTransition) {
            Log.i("AlertDialogAnimator", "on alertdialog animator end");
            this.this$0.mDuringTransition = false;
            if (this.this$0.mPendingViewConfig.isEmpty()) {
                return;
            }
            Log.i("AlertDialogAnimator", "dequeue an animation form the pending list and execute it.");
            Runnable runnable = (Runnable) this.this$0.mPendingViewConfig.remove();
            if (runnable != null) {
                MiuixTransitionManager.beginDelayedTransition(this.this$0.mDialogRootView, this.this$0.mTransition);
                runnable.run();
            }
        }

        @Override // miuix.transition.TransitionListenerAdapter, miuix.transition.MiuixTransition.MiuixTransitionListener
        public void onTransitionStart(MiuixTransition miuixTransition) {
            Log.i("AlertDialogAnimator", "on alertdialog animator start");
            this.this$0.mDuringTransition = true;
        }
    }
}

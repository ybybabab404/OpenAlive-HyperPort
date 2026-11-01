package miuix.flexible.animation;

import android.animation.Animator;
import android.view.View;
import java.util.HashMap;
import java.util.Map;
import miuix.core.util.MiuixUIUtils;
import miuix.flexible.R;
import miuix.flexible.view.HyperCellLayout;

/* JADX INFO: loaded from: classes2.dex */
public class HyperCellAnimationHelper {
    public static final Map ANIMATOR_CACHE = new HashMap();

    /* JADX INFO: renamed from: miuix.flexible.animation.HyperCellAnimationHelper$1, reason: invalid class name */
    class AnonymousClass1 implements Animator.AnimatorListener {
        public final /* synthetic */ int val$animGravity;
        public final /* synthetic */ int val$animSpec;
        public final /* synthetic */ boolean val$isShow;
        public final /* synthetic */ AnimationListener val$listener;
        public final /* synthetic */ View val$view;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            HyperCellAnimationHelper.stopAnimation(this.val$view);
            HyperCellAnimationHelper.ANIMATOR_CACHE.remove(this.val$view);
            AnimationListener animationListener = this.val$listener;
            if (animationListener != null) {
                animationListener.onAnimationCancel(this.val$view);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!this.val$isShow) {
                this.val$view.setVisibility(8);
            }
            HyperCellAnimationHelper.stopAnimation(this.val$view);
            HyperCellAnimationHelper.ANIMATOR_CACHE.remove(this.val$view);
            AnimationListener animationListener = this.val$listener;
            if (animationListener != null) {
                animationListener.onAnimationEnd(this.val$view);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            if (this.val$isShow) {
                this.val$view.setVisibility(0);
            }
            HyperCellAnimationHelper.startAnimation(this.val$view, this.val$animSpec, this.val$animGravity);
            AnimationListener animationListener = this.val$listener;
            if (animationListener != null) {
                animationListener.onAnimationStart(this.val$view);
            }
        }
    }

    /* JADX INFO: renamed from: miuix.flexible.animation.HyperCellAnimationHelper$2, reason: invalid class name */
    class AnonymousClass2 implements AnimationListener {
        public final /* synthetic */ HyperCellLayout val$hyperCellLayout;

        @Override // miuix.flexible.animation.HyperCellAnimationHelper.AnimationListener
        public void onAnimationCancel(View view) {
        }

        @Override // miuix.flexible.animation.HyperCellAnimationHelper.AnimationListener
        public void onAnimationEnd(View view) {
            this.val$hyperCellLayout.removeView(view);
        }

        @Override // miuix.flexible.animation.HyperCellAnimationHelper.AnimationListener
        public void onAnimationStart(View view) {
        }
    }

    public interface AnimationListener {
        void onAnimationCancel(View view);

        void onAnimationEnd(View view);

        void onAnimationStart(View view);
    }

    public static void autoConfigAnimation(View view) {
        HyperCellLayout.LayoutParams layoutParams = (HyperCellLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        if (layoutParams.getAreaId() == R.id.area_head) {
            layoutParams.setAnimationGravity(8388613);
            layoutParams.setAnimSpec(5);
            return;
        }
        if (layoutParams.getAreaId() == R.id.area_end) {
            layoutParams.setAnimationGravity(8388611);
            layoutParams.setAnimSpec(5);
            return;
        }
        if (layoutParams.getAreaId() != R.id.area_title_comment && layoutParams.getAreaId() != R.id.area_comment && layoutParams.getAreaId() != R.id.area_text_button) {
            layoutParams.setAnimationGravity(8388611);
            layoutParams.setAnimSpec(7);
        } else if (MiuixUIUtils.getFontLevel(view.getContext()) == 2) {
            layoutParams.setAnimationGravity(48);
            layoutParams.setAnimSpec(6);
        } else {
            layoutParams.setAnimationGravity(8388611);
            layoutParams.setAnimSpec(5);
        }
    }

    public static void startAnimation(View view, int i, int i2) {
        HyperCellLayout.LayoutParams layoutParams = (HyperCellLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setAnimating(true);
        if (i == 8) {
            autoConfigAnimation(view);
        } else {
            layoutParams.setAnimSpec(i);
            layoutParams.setAnimationGravity(i2);
        }
    }

    public static void stopAnimation(View view) {
        HyperCellLayout.LayoutParams layoutParams = (HyperCellLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams.isAnimating()) {
            layoutParams.setAnimating(false);
            view.requestLayout();
        }
    }
}

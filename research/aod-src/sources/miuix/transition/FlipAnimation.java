package miuix.transition;

import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import miuix.animation.listener.TransitionListener;
import miuix.core.util.MiuiBlurUtils;

/* JADX INFO: loaded from: classes3.dex */
public class FlipAnimation {
    public boolean mBlurEnabled;
    public final ArrayList mColorModes;
    public FlipListener mFlipListener;

    /* JADX INFO: renamed from: miuix.transition.FlipAnimation$1, reason: invalid class name */
    class AnonymousClass1 extends TransitionListener {
        public int lastRotationY;
        public boolean mHasTriggeredNegative;
        public boolean mHasTriggeredPositive;
        public final /* synthetic */ FlipAnimation this$0;
        public final /* synthetic */ boolean val$positive;
        public final /* synthetic */ View val$view;

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            this.mHasTriggeredPositive = false;
            this.mHasTriggeredNegative = false;
            this.lastRotationY = -1;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            this.val$view.setTag(R.id.miuix_animation_tag_is_flip, null);
            FlipAnimation flipAnimation = this.this$0;
            flipAnimation.setMiSelfBlur(this.val$view, 0, flipAnimation.mColorModes);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            this.val$view.setTag(R.id.miuix_animation_tag_is_flip, null);
            FlipAnimation flipAnimation = this.this$0;
            flipAnimation.setMiSelfBlur(this.val$view, 0, flipAnimation.mColorModes);
            if (this.this$0.mFlipListener != null) {
                this.this$0.mFlipListener.onAnimationEnd(this.val$view.getRotationY());
            }
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            int rotationY = ((int) this.val$view.getRotationY()) % 360;
            int i = this.lastRotationY;
            int i2 = rotationY < 0 ? rotationY + 360 : rotationY;
            int i3 = i < 0 ? i + 360 : i;
            if (i != -1) {
                boolean z = this.val$positive;
                if (z && !this.mHasTriggeredPositive && (i2 / 90) % 2 == 1 && (rotationY / 90) - (i / 90) == 1) {
                    this.mHasTriggeredPositive = true;
                    if (this.this$0.mFlipListener != null) {
                        this.this$0.mFlipListener.onFlip(true, this.val$view.getRotationY());
                    }
                } else if (!z && !this.mHasTriggeredNegative && (i3 / 90) % 2 == 1 && (rotationY / 90) - (i / 90) == -1) {
                    this.mHasTriggeredNegative = true;
                    if (this.this$0.mFlipListener != null) {
                        this.this$0.mFlipListener.onFlip(false, this.val$view.getRotationY());
                    }
                }
            }
            this.lastRotationY = rotationY;
            if (i2 <= 90 || i2 >= 270) {
                this.val$view.setScaleX(1.0f);
            } else {
                this.val$view.setScaleX(-1.0f);
            }
            int iAbs = Math.abs((int) (Math.sin((((double) i2) * 3.141592653589793d) / 180.0d) * 40.0d));
            FlipAnimation flipAnimation = this.this$0;
            flipAnimation.setMiSelfBlur(this.val$view, iAbs, flipAnimation.mColorModes);
        }
    }

    public interface FlipListener {
        void onAnimationEnd(float f);

        void onFlip(boolean z, float f);
    }

    public void setMiSelfBlur(View view, int i, ArrayList arrayList) {
        if (!this.mBlurEnabled || view == null) {
            return;
        }
        MiuiBlurUtils.setSelfBlur(view, i, arrayList);
    }
}

package miuix.preference;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.preference.Preference;

/* JADX INFO: loaded from: classes2.dex */
public class ConnectPreferenceHelper {
    public static final int[] STATE_ATTR_CONNECTED;
    public static final int[] STATE_ATTR_DISCONNECTED;
    public Drawable bgDrawableConnected;
    public AnimatedVectorDrawable connectingAnimDrawable;
    public boolean mIconAnimEnabled;
    public Preference mPreference;
    public int mState;
    public TextView mSummaryView;
    public TextView mTitleView;

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$1, reason: invalid class name */
    class AnonymousClass1 extends AnimatorListenerAdapter {
        public final /* synthetic */ ConnectPreferenceHelper this$0;
        public final /* synthetic */ int val$iconColorConnected;
        public final /* synthetic */ int val$iconColorDisConnected;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            Drawable icon = this.this$0.mPreference.getIcon();
            if (icon == null || !this.this$0.mIconAnimEnabled) {
                return;
            }
            if (this.this$0.mState == 1) {
                DrawableCompat.setTint(icon, this.val$iconColorConnected);
            } else {
                DrawableCompat.setTint(icon, this.val$iconColorDisConnected);
            }
        }
    }

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$2, reason: invalid class name */
    class AnonymousClass2 implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ ConnectPreferenceHelper this$0;

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Drawable icon = this.this$0.mPreference.getIcon();
            if (icon == null || !this.this$0.mIconAnimEnabled) {
                return;
            }
            DrawableCompat.setTint(icon, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$3, reason: invalid class name */
    class AnonymousClass3 extends AnimatorListenerAdapter {
        public final /* synthetic */ ConnectPreferenceHelper this$0;
        public final /* synthetic */ int val$titleColorConnected;
        public final /* synthetic */ int val$titleColorDisConnected;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.this$0.mTitleView != null) {
                if (this.this$0.mState == 1) {
                    this.this$0.mTitleView.setTextColor(this.val$titleColorConnected);
                } else {
                    this.this$0.mTitleView.setTextColor(this.val$titleColorDisConnected);
                }
            }
        }
    }

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$4, reason: invalid class name */
    class AnonymousClass4 implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ ConnectPreferenceHelper this$0;

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (this.this$0.mTitleView != null) {
                this.this$0.mTitleView.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        }
    }

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$5, reason: invalid class name */
    class AnonymousClass5 extends AnimatorListenerAdapter {
        public final /* synthetic */ ConnectPreferenceHelper this$0;
        public final /* synthetic */ int val$summaryColorConnected;
        public final /* synthetic */ int val$summaryColorDisConnected;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.this$0.mSummaryView != null) {
                if (this.this$0.mState == 1) {
                    this.this$0.mSummaryView.setTextColor(this.val$summaryColorConnected);
                } else {
                    this.this$0.mSummaryView.setTextColor(this.val$summaryColorDisConnected);
                }
            }
        }
    }

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$6, reason: invalid class name */
    class AnonymousClass6 implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ ConnectPreferenceHelper this$0;

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (this.this$0.mSummaryView != null) {
                this.this$0.mSummaryView.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        }
    }

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$7, reason: invalid class name */
    class AnonymousClass7 extends AnimatorListenerAdapter {
        public final /* synthetic */ ConnectPreferenceHelper this$0;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.this$0.mState != 2 && this.this$0.connectingAnimDrawable != null && this.this$0.connectingAnimDrawable.isRunning()) {
                this.this$0.connectingAnimDrawable.stop();
            }
            if (this.this$0.mState == 1) {
                this.this$0.bgDrawableConnected.setAlpha(255);
            } else {
                this.this$0.bgDrawableConnected.setAlpha(0);
            }
        }
    }

    /* JADX INFO: renamed from: miuix.preference.ConnectPreferenceHelper$8, reason: invalid class name */
    class AnonymousClass8 implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ ConnectPreferenceHelper this$0;

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.this$0.bgDrawableConnected.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    static {
        int i = R.attr.state_connected;
        STATE_ATTR_CONNECTED = new int[]{i};
        STATE_ATTR_DISCONNECTED = new int[]{-i};
    }
}

package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.util.ImageUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
import miuix.view.animation.ExponentialEaseInOutInterpolator;
import miuix.view.animation.ExponentialEaseOutInterpolator;

/* JADX INFO: loaded from: classes.dex */
public class ScopeClockView extends FrameLayout implements IAnimatable {
    public boolean mCancel;
    public final Runnable mClockAnimationEndTask;
    public ScopeImageView mClockPanel;
    public View mContainer;
    public boolean mDestroy;
    public AnimatorListenerAdapter mGlobalAnimatorListener;
    public boolean mHasWindowFocus;
    public long mInnerDelay;
    public ValueAnimator mOutAnimator;
    public final Runnable mScopeEndRunnable;
    public int mSize;
    public final Runnable mStartAnimationTask;

    public static /* synthetic */ void $r8$lambda$12f1PlE0whClZxU8pK2DCG4GUFI(ScopeClockView scopeClockView) {
        if (scopeClockView.mSize == 1 && scopeClockView.mHasWindowFocus && !Utils.isCloseAnimator(scopeClockView.getContext())) {
            View containerView = scopeClockView.getContainerView();
            if (containerView != null) {
                scopeClockView.mContainer = containerView.findViewById(R.id.aod_content_container);
            }
            scopeClockView.mCancel = false;
            scopeClockView.mOutAnimator.start();
        }
    }

    public static /* synthetic */ void $r8$lambda$MzCTmkLZ4uJsrwg6pnH5s0LU2M8(ScopeClockView scopeClockView) {
        scopeClockView.setOtherElementsVisibility(false, false);
        boolean z = scopeClockView.mSize != 1;
        ScopeImageView scopeImageView = scopeClockView.mClockPanel;
        if (scopeImageView != null) {
            if (!z) {
                scopeImageView.setEndAction(scopeClockView.mScopeEndRunnable);
            }
            scopeClockView.mClockPanel.startAnimation(z, scopeClockView.mGlobalAnimatorListener);
        }
        View containerView = scopeClockView.getContainerView();
        if (containerView != null) {
            scopeClockView.mContainer = containerView.findViewById(R.id.aod_content_container);
        }
        View view = scopeClockView.mContainer;
        if (view != null) {
            view.setAlpha(1.0f);
        }
        scopeClockView.removeCallbacks(scopeClockView.mClockAnimationEndTask);
        scopeClockView.postDelayed(scopeClockView.mClockAnimationEndTask, 0L);
    }

    public static /* synthetic */ void $r8$lambda$PgM_HFsZO5jcKHXIgxe8U5Q9Vpo(ScopeClockView scopeClockView, ValueAnimator valueAnimator) {
        scopeClockView.getClass();
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        View view = scopeClockView.mContainer;
        if (view != null) {
            view.setAlpha(fFloatValue);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$UX8j1e1HXv-fkqDC09wD0CyTi-k, reason: not valid java name */
    public static /* synthetic */ void m598$r8$lambda$UX8j1e1HXvfkqDC09wD0CyTik(int i, TextView textView) {
        textView.setTextAppearance(i);
        textView.setTypeface(null);
    }

    public static /* synthetic */ void $r8$lambda$Wp11Xy01Kh9PcNkDaUL4RKO1_V0(int i, TextView textView) {
        textView.setTextAppearance(i);
        textView.setTypeface(null);
    }

    public ScopeClockView(Context context) {
        super(context);
        this.mInnerDelay = 0L;
        this.mClockAnimationEndTask = new Runnable() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setOtherElementsVisibility(true, true);
            }
        };
        this.mScopeEndRunnable = new Runnable() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ScopeClockView.$r8$lambda$12f1PlE0whClZxU8pK2DCG4GUFI(this.f$0);
            }
        };
        this.mStartAnimationTask = new Runnable() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ScopeClockView.$r8$lambda$MzCTmkLZ4uJsrwg6pnH5s0LU2M8(this.f$0);
            }
        };
        init();
    }

    public ScopeClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInnerDelay = 0L;
        this.mClockAnimationEndTask = new Runnable() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setOtherElementsVisibility(true, true);
            }
        };
        this.mScopeEndRunnable = new Runnable() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ScopeClockView.$r8$lambda$12f1PlE0whClZxU8pK2DCG4GUFI(this.f$0);
            }
        };
        this.mStartAnimationTask = new Runnable() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ScopeClockView.$r8$lambda$MzCTmkLZ4uJsrwg6pnH5s0LU2M8(this.f$0);
            }
        };
        init();
    }

    private void init() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mOutAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(400L);
        this.mOutAnimator.setInterpolator(new ExponentialEaseOutInterpolator());
        this.mOutAnimator.setStartDelay(500L);
        this.mOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScopeClockView.$r8$lambda$PgM_HFsZO5jcKHXIgxe8U5Q9Vpo(this.f$0, valueAnimator);
            }
        });
        this.mOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.aod.widget.ScopeClockView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                ScopeClockView.this.mCancel = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (ScopeClockView.this.mSize == 1) {
                    ScopeClockView scopeClockView = ScopeClockView.this;
                    if (!scopeClockView.mHasWindowFocus || scopeClockView.mCancel) {
                        return;
                    }
                    scopeClockView.startAnimation();
                }
            }
        });
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        int i = this.mSize;
        if (i == 2 || i == 5) {
            return;
        }
        removeCallbacks(this.mStartAnimationTask);
        removeCallbacks(this.mClockAnimationEndTask);
        if (!this.mDestroy && this.mSize != 3) {
            this.mClockAnimationEndTask.run();
        }
        ScopeImageView scopeImageView = this.mClockPanel;
        if (scopeImageView != null) {
            scopeImageView.setEndAction(null);
            this.mClockPanel.clearAnimationView(this.mSize == 1);
        }
        this.mOutAnimator.cancel();
        setOtherElementsVisibility(false, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    public View getContainerView() {
        ?? r2 = this;
        while (r2.getParent() != null && (r2.getParent() instanceof View) && r2.getId() != R.id.aod_content_container) {
            r2 = (View) r2.getParent();
        }
        return r2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mHasWindowFocus = hasWindowFocus();
        this.mDestroy = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.mDestroy = true;
        clearAnimationView();
        View containerView = getContainerView();
        if (containerView != null) {
            int[] iArr = {R.id.gradient_layout, R.id.aod_battery_layout, R.id.step_component};
            for (int i = 0; i < 3; i++) {
                View viewFindViewById = containerView.findViewById(iArr[i]);
                if (viewFindViewById != null) {
                    viewFindViewById.animate().cancel();
                }
            }
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mHasWindowFocus = z;
        if (this.mSize == 1) {
            if (z) {
                startAnimation();
            } else {
                clearAnimationView();
            }
        }
    }

    public void setInnerDelay(long j) {
        this.mInnerDelay = j;
    }

    public void setOtherElementsVisibility(boolean z, boolean z2) {
        View containerView;
        int i = this.mSize;
        if (i == 2 || i == 5 || (containerView = getContainerView()) == null) {
            return;
        }
        int[] iArr = {R.id.gradient_layout, R.id.aod_battery_layout, R.id.step_component};
        for (int i2 = 0; i2 < 3; i2++) {
            View viewFindViewById = containerView.findViewById(iArr[i2]);
            if (viewFindViewById != null) {
                viewFindViewById.animate().cancel();
                if (z) {
                    if (z2) {
                        viewFindViewById.animate().alpha(1.0f).setDuration(1000L).setInterpolator(new ExponentialEaseInOutInterpolator()).start();
                    } else {
                        viewFindViewById.setAlpha(1.0f);
                    }
                } else if (z2) {
                    viewFindViewById.animate().alpha(0.0f).setDuration(1000L).setInterpolator(new ExponentialEaseInOutInterpolator()).start();
                } else {
                    viewFindViewById.setAlpha(0.0f);
                }
            }
        }
    }

    public void setSize(int i, String str) {
        final int i2;
        int i3;
        final int i4;
        this.mSize = i;
        if (i == 2) {
            i4 = R.style.Aod_date_s;
            i3 = R.style.Aod_scope_s;
            i2 = R.style.Aod_scope_time_s;
        } else {
            i2 = R.style.Aod_scope_time;
            i3 = R.style.Aod_scope;
            i4 = i == 1 ? R.style.Aod_date_scope : R.style.Aod_date;
        }
        this.mClockPanel = (ScopeImageView) findViewById(R.id.clock);
        ScopeInfo frameAnimationInfoByStyleName = ScopeInfoManager.getFrameAnimationInfoByStyleName(str);
        ScopeImageView scopeImageView = this.mClockPanel;
        if (scopeImageView != null) {
            int i5 = this.mSize;
            if (i5 == 2 || i5 == 5) {
                ImageUtils.loadAsBackground(scopeImageView, frameAnimationInfoByStyleName.mResIdThumbnail, 1);
            } else {
                scopeImageView.setScopeInfo(frameAnimationInfoByStyleName);
            }
        }
        TextView textView = (TextView) findViewById(R.id.clock_horizontal);
        TextView textView2 = (TextView) findViewById(R.id.date);
        TextView textView3 = (TextView) findViewById(R.id.date_lunar);
        ViewUtil.doIfNotNull(textView, new Consumer() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextAppearance(i2);
            }
        });
        ViewUtil.doIfNotNull(textView2, new Consumer() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ScopeClockView.$r8$lambda$Wp11Xy01Kh9PcNkDaUL4RKO1_V0(i4, (TextView) obj);
            }
        });
        ViewUtil.doIfNotNull(textView3, new Consumer() { // from class: com.miui.aod.widget.ScopeClockView$$ExternalSyntheticLambda2
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ScopeClockView.m598$r8$lambda$UX8j1e1HXvfkqDC09wD0CyTik(i4, (TextView) obj);
            }
        });
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(i3, R.styleable.scope);
        int dimension = (int) typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
        int dimension2 = (int) typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
        ScopeImageView scopeImageView2 = this.mClockPanel;
        if (scopeImageView2 != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) scopeImageView2.getLayoutParams();
            layoutParams.width = (int) typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
            layoutParams.height = (int) typedArrayObtainStyledAttributes.getDimension(1, 0.0f);
            this.mClockPanel.setLayoutParams(layoutParams);
        }
        typedArrayObtainStyledAttributes.recycle();
        if (textView != null) {
            textView.setPadding(0, dimension, 0, dimension2);
        }
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        int i = this.mSize;
        if (i == 2 || i == 5) {
            return;
        }
        this.mGlobalAnimatorListener = animatorListenerAdapter;
        setOtherElementsVisibility(false, false);
        removeCallbacks(this.mStartAnimationTask);
        removeCallbacks(this.mClockAnimationEndTask);
        postDelayed(this.mStartAnimationTask, this.mInnerDelay);
    }
}

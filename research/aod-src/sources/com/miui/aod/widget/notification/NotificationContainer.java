package com.miui.aod.widget.notification;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.miui.aod.Utils;
import com.miui.aod.widget.IAnimatable;
import miuix.view.animation.SineEaseInInterpolator;

/* JADX INFO: loaded from: classes.dex */
public class NotificationContainer extends LinearLayout implements IAnimatable {
    private ViewPropertyAnimator mAnimator;

    public NotificationContainer(Context context) {
        this(context, null);
    }

    public NotificationContainer(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationContainer(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        ViewPropertyAnimator viewPropertyAnimator = this.mAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        setAlpha(0.0f);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        if (Utils.isAodProcess()) {
            setLayoutTransition(null);
        }
    }

    @Override // com.miui.aod.widget.IAnimatable
    public /* bridge */ /* synthetic */ void startAnimation() {
        super.startAnimation();
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        this.mAnimator = animate().alpha(1.0f).setInterpolator(new SineEaseInInterpolator()).setStartDelay(600L).setDuration(500L);
    }
}

package com.miui.aod.other.step;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.R;
import com.miui.aod.aodannotation.FrameResBinder;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.ReflectUtil;
import com.miui.aod.util.wakelock.WakeLock;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class StepWidget extends LinearLayout {
    public static final String TAG = "StepWidget";
    public int[] RES_ARRAYS_DOING;
    public int[] RES_ARRAYS_DONE;
    public final Runnable animationRunnable;
    public Drawable mCurDrawable;
    public AnimationDrawable mDoingDrawable;
    public AnimationDrawable mDoneDrawable;
    public final WakeLock mDrawWakeLock;
    public final ImageView mImageView;
    public final TextView mTextView;

    /* JADX INFO: renamed from: $r8$lambda$K6hP51Iaa9wMAnt9-myuTw3yX3I, reason: not valid java name */
    public static /* synthetic */ void m434$r8$lambda$K6hP51Iaa9wMAnt9myuTw3yX3I(StepWidget stepWidget) {
        Drawable drawable = stepWidget.mCurDrawable;
        if (drawable != null) {
            try {
                stepWidget.mDrawWakeLock.setMaxAcquireTime(((Long) ReflectUtil.callObjectMethod2(drawable, Long.TYPE, "getTotalDuration", new Class[0], new Object[0])).longValue() + 100);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Log.d(TAG, "getDuration error: ", e);
            }
            stepWidget.mDrawWakeLock.acquire(TAG);
            ((AnimationDrawable) stepWidget.mCurDrawable).stop();
            ((AnimationDrawable) stepWidget.mCurDrawable).start();
        }
    }

    public StepWidget(Context context) {
        this(context, null);
    }

    public StepWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StepWidget(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawWakeLock = WakeLock.wrapForAnimation(getContext(), TAG);
        this.animationRunnable = new Runnable() { // from class: com.miui.aod.other.step.StepWidget$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                StepWidget.m434$r8$lambda$K6hP51Iaa9wMAnt9myuTw3yX3I(this.f$0);
            }
        };
        setOrientation(0);
        setGravity(17);
        setClipChildren(false);
        ImageView imageView = new ImageView(context);
        this.mImageView = imageView;
        addView(imageView, -2, -2);
        imageView.setVisibility(8);
        imageView.setScaleX(1.5f);
        imageView.setScaleY(1.5f);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        this.mTextView = appCompatTextView;
        appCompatTextView.setTextSize(0, getResources().getDimension(R.dimen.step_widget_text_size));
        appCompatTextView.setTextColor(-1);
        appCompatTextView.setIncludeFontPadding(false);
        addView(appCompatTextView, -2, -2);
        appCompatTextView.setVisibility(8);
        FrameResBinder.bind(this);
    }

    public static void updateImageSize(ImageView imageView, Drawable drawable, int i) {
        float intrinsicWidth = (drawable.getIntrinsicWidth() * 1.0f) / drawable.getIntrinsicHeight();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = (int) (intrinsicWidth * i);
        layoutParams.height = i;
    }

    public final AnimationDrawable createDrawable(boolean z) {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int i : z ? this.RES_ARRAYS_DONE : this.RES_ARRAYS_DOING) {
            animationDrawable.addFrame(ResourcesCompat.getDrawable(getResources(), i, null), 33);
        }
        animationDrawable.setOneShot(true);
        if (z) {
            this.mDoneDrawable = animationDrawable;
            return animationDrawable;
        }
        this.mDoingDrawable = animationDrawable;
        return animationDrawable;
    }

    public final AnimationDrawable getAnimationDrawable(boolean z) {
        if (z) {
            AnimationDrawable animationDrawable = this.mDoneDrawable;
            return animationDrawable == null ? createDrawable(true) : animationDrawable;
        }
        AnimationDrawable animationDrawable2 = this.mDoingDrawable;
        return animationDrawable2 == null ? createDrawable(false) : animationDrawable2;
    }

    public final void setClipChildrenInParent() {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.setClipChildren(false);
                viewGroup.setClipToPadding(false);
            }
        }
    }

    public void startAnimation() {
        if (this.mCurDrawable instanceof AnimationDrawable) {
            removeCallbacks(this.animationRunnable);
            postDelayed(this.animationRunnable, 1000L);
        }
    }

    public void updateStep(StepBean stepBean) {
        if (!StepUtils.stepActive(stepBean)) {
            this.mCurDrawable = null;
            this.mTextView.setVisibility(8);
            this.mImageView.setImageDrawable(null);
            this.mImageView.setVisibility(8);
            return;
        }
        this.mTextView.setText(stepBean.content + stepBean.unit);
        this.mTextView.measure(View.MeasureSpec.makeMeasureSpec(DeviceInfo.getRealSize().x, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(DeviceInfo.getRealSize().x, Integer.MIN_VALUE));
        int measuredHeight = this.mTextView.getMeasuredHeight();
        this.mTextView.setVisibility(0);
        AnimationDrawable animationDrawable = getAnimationDrawable(StepUtils.hasAchieveTarget(stepBean, (GoalLiveData.GoalBean) GoalLiveData.getInstance().getValue()));
        if (animationDrawable == null || animationDrawable.getIntrinsicHeight() <= 0) {
            return;
        }
        setClipChildrenInParent();
        updateImageSize(this.mImageView, animationDrawable, measuredHeight);
        this.mImageView.setImageDrawable(animationDrawable);
        this.mImageView.setVisibility(0);
        this.mCurDrawable = animationDrawable;
        startAnimation();
    }
}

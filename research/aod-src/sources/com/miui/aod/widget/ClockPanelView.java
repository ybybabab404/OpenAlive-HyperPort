package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.R;
import com.miui.aod.category.ClockPanelCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.view.IPreAodView;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.resource.ClockStylePool;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.util.ImageUtils;

/* JADX INFO: loaded from: classes.dex */
public class ClockPanelView extends FrameLayout implements IAnimatable, IPreAodView {
    public AnimationView mAnimationView;
    public boolean mBackGroundAnimEnd;
    public GradientImageView mCenterDot;
    public final Runnable mClockAnimationEndTask;
    public int mDotSize;
    public Animator mEmptyAnimator;
    public FrameAnimationView mFrameAnimationBg;
    public FrameAnimationManager.FrameAnimationInfo mFrameAnimationInfo;
    public AnimatorListenerAdapter mGlobalAnimatorListener;
    public boolean mHandAnimEnd;
    public boolean mHasInit;
    public int mHour;
    public GradientImageView mHourHand;
    public int mHourLength;
    public int mMinute;
    public GradientImageView mMinuteHand;
    public int mMinuteLength;
    public GradientImageView mPanel;
    public boolean mPendingAnimation;
    public int mSize;
    public boolean mTimeInitialized;

    public ClockPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHasInit = false;
        this.mClockAnimationEndTask = new Runnable() { // from class: com.miui.aod.widget.ClockPanelView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.resetClockHandState();
            }
        };
    }

    private Animator getEmptyAnimator() {
        if (this.mEmptyAnimator == null) {
            this.mEmptyAnimator = ValueAnimator.ofInt(0);
        }
        return this.mEmptyAnimator;
    }

    private void resetForAnimation() {
        int i = this.mSize;
        if (i == 2 || i == 4 || i == 5 || this.mFrameAnimationInfo == null) {
            return;
        }
        setOtherElementsVisibility(false, false);
        setBackground(null);
        GradientImageView gradientImageView = this.mHourHand;
        if (gradientImageView != null) {
            gradientImageView.setVisibility(8);
        }
        GradientImageView gradientImageView2 = this.mMinuteHand;
        if (gradientImageView2 != null) {
            gradientImageView2.setVisibility(8);
        }
        GradientImageView gradientImageView3 = this.mCenterDot;
        if (gradientImageView3 != null) {
            gradientImageView3.setVisibility(8);
        }
    }

    public final void addAnimationView() {
        if (this.mFrameAnimationInfo == null) {
            return;
        }
        clearAnimationView();
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        AnimationView animationView = new AnimationView(getContext());
        this.mAnimationView = animationView;
        addView(animationView, 0, layoutParams);
        if (this.mFrameAnimationInfo.resourceFromFile()) {
            Context context = getContext();
            FrameAnimationManager.FrameAnimationInfo frameAnimationInfo = this.mFrameAnimationInfo;
            this.mFrameAnimationBg = new FrameAnimationView(context, frameAnimationInfo.mFps, frameAnimationInfo.mResFilePathArrays);
        } else {
            Context context2 = getContext();
            FrameAnimationManager.FrameAnimationInfo frameAnimationInfo2 = this.mFrameAnimationInfo;
            this.mFrameAnimationBg = new FrameAnimationView(context2, frameAnimationInfo2.mFps, frameAnimationInfo2.mResIdArrays);
        }
        addView(this.mFrameAnimationBg, 0, layoutParams);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        FrameAnimationView frameAnimationView = this.mFrameAnimationBg;
        if (frameAnimationView != null) {
            frameAnimationView.setAnimationCallBack(null);
            removeView(this.mFrameAnimationBg);
            this.mFrameAnimationBg = null;
        }
        AnimationView animationView = this.mAnimationView;
        if (animationView != null) {
            removeView(animationView);
            this.mAnimationView = null;
        }
        resetForAnimation();
        this.mBackGroundAnimEnd = false;
        this.mHandAnimEnd = false;
        AnimatorListenerAdapter animatorListenerAdapter = this.mGlobalAnimatorListener;
        if (animatorListenerAdapter != null) {
            animatorListenerAdapter.onAnimationCancel(getEmptyAnimator());
            this.mGlobalAnimatorListener.onAnimationEnd(getEmptyAnimator());
            this.mGlobalAnimatorListener = null;
        }
    }

    public final Bitmap decodeBitmap(int i) {
        try {
            return BitmapFactory.decodeResource(getResources(), i);
        } catch (Exception e) {
            Log.e("ClockPanelView", "error...", e);
            return null;
        }
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

    public void init(int i, boolean z, StyleInfo styleInfo) {
        int i2;
        if (this.mHasInit) {
            return;
        }
        this.mHasInit = true;
        FrameAnimationManager.FrameAnimationInfo frameAnimationInfoCreateFrameAnimationInfoFromStyleInfo = FrameAnimationManager.createFrameAnimationInfoFromStyleInfo(getContext(), styleInfo);
        this.mFrameAnimationInfo = frameAnimationInfoCreateFrameAnimationInfoFromStyleInfo;
        this.mSize = i;
        boolean z2 = frameAnimationInfoCreateFrameAnimationInfoFromStyleInfo != null;
        if (i == 2) {
            i2 = R.style.Aod_clock_panel_animation_s;
        } else {
            i2 = i == 4 ? R.style.Aod_clock_panel_animation_t : R.style.Aod_clock_animation_panel;
        }
        boolean z3 = z && (styleInfo instanceof ClockPanelCategoryInfo);
        float f = z3 ? 0.3f : 1.0f;
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(i2, R.styleable.ClockPanelView);
        this.mHourLength = (int) (typedArrayObtainStyledAttributes.getDimension(0, 0.0f) * f);
        this.mMinuteLength = (int) (typedArrayObtainStyledAttributes.getDimension(1, 0.0f) * f);
        this.mDotSize = (int) (typedArrayObtainStyledAttributes.getDimension(2, 0.0f) * f);
        ViewGroup.LayoutParams layoutParams = this.mHourHand.getLayoutParams();
        int i3 = this.mHourLength;
        layoutParams.width = i3;
        layoutParams.height = i3;
        this.mHourHand.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.mMinuteHand.getLayoutParams();
        int i4 = this.mMinuteLength;
        layoutParams2.width = i4;
        layoutParams2.height = i4;
        this.mMinuteHand.setLayoutParams(layoutParams2);
        ViewGroup.LayoutParams layoutParams3 = this.mCenterDot.getLayoutParams();
        int i5 = this.mDotSize;
        layoutParams3.width = i5;
        layoutParams3.height = i5;
        this.mCenterDot.setLayoutParams(layoutParams3);
        if (z2) {
            ImageUtils.loadIntoImage(this.mHourHand, this.mFrameAnimationInfo.mResIdHour, 0);
            ImageUtils.loadIntoImage(this.mMinuteHand, this.mFrameAnimationInfo.mResIdMin, 0);
            ImageUtils.loadIntoImage(this.mCenterDot, this.mFrameAnimationInfo.mResIdDot, 0);
            int i6 = this.mSize;
            if (i6 == 2 || i6 == 4 || i6 == 5) {
                ImageUtils.loadAsBackground(this, this.mFrameAnimationInfo.mResIdBg, 1);
            } else {
                this.mCenterDot.setVisibility(8);
                startAnimation();
            }
        } else {
            ClockPanelCategoryInfo clockPanelCategoryInfo = (ClockPanelCategoryInfo) styleInfo;
            int iIntValue = ClockStylePool.getClockPanelBgByStyleName(clockPanelCategoryInfo.getPanelORDefault()).intValue();
            int i7 = this.mSize;
            if (i7 == 2 || i7 == 4) {
                setScaleX(1.28f);
                setScaleY(1.28f);
                ImageUtils.loadIntoImage(this.mPanel, iIntValue, 1);
            } else {
                ImageUtils.loadIntoImage(this.mPanel, iIntValue, 0);
            }
            this.mPanel.setContentDescription(clockPanelCategoryInfo.getPanelName(getContext()));
            if (z3) {
                setScaleX(getScaleX() * 1.5f);
                setScaleY(getScaleY() * 1.5f);
            }
        }
        ViewGroup.LayoutParams layoutParams4 = getLayoutParams();
        layoutParams4.width = (int) (typedArrayObtainStyledAttributes.getDimension(4, 0.0f) * f);
        layoutParams4.height = (int) (typedArrayObtainStyledAttributes.getDimension(3, 0.0f) * f);
        setLayoutParams(layoutParams4);
        typedArrayObtainStyledAttributes.recycle();
    }

    public boolean isAnimClock() {
        return this.mFrameAnimationInfo != null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.mClockAnimationEndTask);
        setOtherElementsVisibility(true, false);
        resetClockHandState();
        resetBackground();
        View containerView = getContainerView();
        if (containerView != null) {
            int[] iArr = {R.id.date, R.id.date_lunar, R.id.aod_battery_layout, R.id.icons, R.id.step_component, R.id.date_dual, R.id.date_container, R.id.date_container_dual};
            for (int i = 0; i < 8; i++) {
                View viewFindViewById = containerView.findViewById(iArr[i]);
                if (viewFindViewById != null) {
                    viewFindViewById.animate().cancel();
                }
            }
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHourHand = (GradientImageView) findViewById(R.id.hour_hand);
        this.mMinuteHand = (GradientImageView) findViewById(R.id.minute_hand);
        this.mPanel = (GradientImageView) findViewById(R.id.panel_iv);
        this.mCenterDot = (GradientImageView) findViewById(R.id.clock_panel_center_dot);
    }

    public final void onGlobalAnimationEnded() {
        AnimatorListenerAdapter animatorListenerAdapter;
        if (this.mHandAnimEnd && this.mBackGroundAnimEnd && (animatorListenerAdapter = this.mGlobalAnimatorListener) != null) {
            animatorListenerAdapter.onAnimationEnd(getEmptyAnimator());
            this.mGlobalAnimatorListener = null;
        }
    }

    public final void onGlobalAnimationStarted() {
        AnimatorListenerAdapter animatorListenerAdapter = this.mGlobalAnimatorListener;
        if (animatorListenerAdapter != null) {
            animatorListenerAdapter.onAnimationStart(getEmptyAnimator());
        }
    }

    public void resetBackground() {
        int i;
        Bitmap bitmapDecodeBitmap;
        if (this.mFrameAnimationInfo == null || (i = this.mSize) == 2 || i == 4 || i == 5) {
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            return;
        }
        if (this.mFrameAnimationInfo.resourceFromFile()) {
            String[] strArr = this.mFrameAnimationInfo.mResFilePathArrays;
            bitmapDecodeBitmap = BitmapFactory.decodeFile(strArr[strArr.length - 1]);
        } else {
            int[] iArr = this.mFrameAnimationInfo.mResIdArrays;
            bitmapDecodeBitmap = decodeBitmap(iArr[iArr.length - 1]);
        }
        Bitmap bitmap = bitmapDecodeBitmap;
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == 0 || height == 0) {
            return;
        }
        float fMin = Math.min((measuredWidth * 1.0f) / width, (measuredHeight * 1.0f) / height);
        Matrix matrix = new Matrix();
        matrix.postScale(fMin, fMin);
        setBackground(new BitmapDrawable(getResources(), Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)));
        FrameAnimationView frameAnimationView = this.mFrameAnimationBg;
        if (frameAnimationView != null) {
            frameAnimationView.setAnimationCallBack(null);
            removeView(this.mFrameAnimationBg);
            this.mFrameAnimationBg = null;
        }
    }

    public void resetClockHandState() {
        int i = this.mSize;
        if (i == 2 || i == 4 || i == 5) {
            return;
        }
        GradientImageView gradientImageView = this.mHourHand;
        if (gradientImageView != null) {
            gradientImageView.setVisibility(0);
            this.mHourHand.setRotation((((this.mHour % 12) * 360) / 12) + ((this.mMinute * 360) / 720.0f));
        }
        GradientImageView gradientImageView2 = this.mMinuteHand;
        if (gradientImageView2 != null) {
            gradientImageView2.setVisibility(0);
            this.mMinuteHand.setRotation((this.mMinute * 360) / 60);
        }
        GradientImageView gradientImageView3 = this.mCenterDot;
        if (gradientImageView3 != null) {
            gradientImageView3.setVisibility(0);
        }
        AnimationView animationView = this.mAnimationView;
        if (animationView != null) {
            removeView(animationView);
            this.mAnimationView = null;
        }
    }

    public void setOtherElementsVisibility(boolean z, boolean z2) {
        int i = this.mSize;
        if (i == 2 || i == 4 || i == 5) {
            return;
        }
        FrameAnimationView frameAnimationView = this.mFrameAnimationBg;
        long duration = frameAnimationView != null ? frameAnimationView.getDuration() : 0L;
        View containerView = getContainerView();
        if (containerView != null) {
            int[] iArr = {R.id.date, R.id.date_lunar, R.id.aod_battery_layout, R.id.icons, R.id.step_component, R.id.date_dual, R.id.date_container, R.id.date_container_dual};
            for (int i2 = 0; i2 < 8; i2++) {
                View viewFindViewById = containerView.findViewById(iArr[i2]);
                if (viewFindViewById != null) {
                    viewFindViewById.animate().cancel();
                    if (z) {
                        if (z2) {
                            viewFindViewById.animate().alpha(1.0f).setDuration(200L).setStartDelay(Math.max(0L, duration - 800)).start();
                        } else {
                            viewFindViewById.setAlpha(1.0f);
                        }
                    } else if (z2) {
                        viewFindViewById.animate().alpha(0.0f).setDuration(200L).setStartDelay(0L).start();
                    } else {
                        viewFindViewById.setAlpha(0.0f);
                    }
                }
            }
        }
    }

    public void setTime(int i, int i2) {
        this.mHour = i;
        this.mMinute = i2;
        this.mTimeInitialized = true;
        if (this.mPendingAnimation) {
            startAnimation();
            return;
        }
        GradientImageView gradientImageView = this.mHourHand;
        if (gradientImageView != null) {
            gradientImageView.setRotation((((i % 12) * 360) / 12) + ((i2 * 360) / 720.0f));
        }
        GradientImageView gradientImageView2 = this.mMinuteHand;
        if (gradientImageView2 != null) {
            gradientImageView2.setRotation((this.mMinute * 360) / 60);
        }
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        AnimationView animationView;
        int i = this.mSize;
        if (i == 2 || i == 4 || i == 5 || this.mFrameAnimationInfo == null) {
            return;
        }
        this.mGlobalAnimatorListener = animatorListenerAdapter;
        this.mPendingAnimation = false;
        if (!this.mTimeInitialized) {
            this.mPendingAnimation = true;
            return;
        }
        FrameAnimationView frameAnimationView = this.mFrameAnimationBg;
        if (frameAnimationView == null || frameAnimationView.getParent() == null || (animationView = this.mAnimationView) == null || animationView.getParent() == null) {
            addAnimationView();
        } else {
            resetForAnimation();
        }
        startBackgroundAnimation();
        float f = ((this.mHour % 12) * 360) / 12;
        int i2 = this.mMinute;
        startClockHandAnimation(f + ((i2 * 360) / 720.0f), (i2 * 360) / 60);
    }

    public final void startBackgroundAnimation() {
        FrameAnimationView frameAnimationView;
        int i = this.mSize;
        if (i == 2 || i == 4 || i == 5 || (frameAnimationView = this.mFrameAnimationBg) == null || frameAnimationView.getParent() == null) {
            return;
        }
        this.mFrameAnimationBg.setAnimationCallBack(new FrameAnimationController.AnimationCallBack() { // from class: com.miui.aod.widget.ClockPanelView.2
            @Override // com.miui.aod.widget.FrameAnimationController.AnimationCallBack
            public void onAnimationEnd() {
                ClockPanelView.this.resetBackground();
                ClockPanelView.this.mBackGroundAnimEnd = true;
                ClockPanelView.this.onGlobalAnimationEnded();
            }

            @Override // com.miui.aod.widget.FrameAnimationController.AnimationCallBack
            public void onAnimationStart() {
                ClockPanelView.this.setOtherElementsVisibility(true, true);
                ClockPanelView.this.onGlobalAnimationStarted();
            }
        });
        this.mFrameAnimationBg.startAnimation();
    }

    public final void startClockHandAnimation(float f, float f2) {
        AnimationView animationView;
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        int i = this.mSize;
        if (i == 2 || i == 4 || i == 5 || (animationView = this.mAnimationView) == null || animationView.getParent() == null) {
            return;
        }
        if (this.mFrameAnimationInfo.resourceFromFile()) {
            drawable = new BitmapDrawable(getResources(), ThemeUtils.getBitmap(this.mFrameAnimationInfo.getResFileNameHour()));
            drawable2 = new BitmapDrawable(getResources(), ThemeUtils.getBitmap(this.mFrameAnimationInfo.getResFileNameMin()));
            drawable3 = new BitmapDrawable(getResources(), ThemeUtils.getBitmap(this.mFrameAnimationInfo.getResFileNameDot()));
        } else {
            drawable = ResourcesCompat.getDrawable(getResources(), this.mFrameAnimationInfo.mResIdHour, null);
            drawable2 = ResourcesCompat.getDrawable(getResources(), this.mFrameAnimationInfo.mResIdMin, null);
            drawable3 = ResourcesCompat.getDrawable(getResources(), this.mFrameAnimationInfo.mResIdDot, null);
        }
        ClockHandDrawer clockHandDrawer = new ClockHandDrawer(drawable, drawable2, drawable3);
        clockHandDrawer.setAnimationValue(f, f2);
        clockHandDrawer.setStartDelay(600000000L);
        if (this.mHourHand != null) {
            clockHandDrawer.setHourDrawableSize(this.mHourLength);
        }
        if (this.mMinuteHand != null) {
            clockHandDrawer.setMinDrawableSize(this.mMinuteLength);
        }
        if (this.mCenterDot != null) {
            clockHandDrawer.setDotDrawableSize(this.mDotSize);
        }
        clockHandDrawer.setAnimationListener(new AnimationView.AnimationDrawer.AnimationStateListener() { // from class: com.miui.aod.widget.ClockPanelView.1
            @Override // com.miui.aod.widget.AnimationView.AnimationDrawer.AnimationStateListener
            public void onAnimationEnd() {
                ClockPanelView clockPanelView = ClockPanelView.this;
                clockPanelView.removeCallbacks(clockPanelView.mClockAnimationEndTask);
                ClockPanelView clockPanelView2 = ClockPanelView.this;
                clockPanelView2.post(clockPanelView2.mClockAnimationEndTask);
                ClockPanelView.this.mHandAnimEnd = true;
                ClockPanelView.this.onGlobalAnimationEnded();
            }

            @Override // com.miui.aod.widget.AnimationView.AnimationDrawer.AnimationStateListener
            public void onAnimationStart() {
                ClockPanelView.this.mAnimationView.setAlpha(0.0f);
                ClockPanelView.this.mAnimationView.animate().alpha(1.0f).setDuration(900L).start();
                ClockPanelView.this.onGlobalAnimationStarted();
            }
        });
        this.mAnimationView.addAnimationDrawer(clockHandDrawer);
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        if (this.mFrameAnimationInfo == null && (styleInfo instanceof ClockPanelCategoryInfo)) {
            DrawableData drawableDataByName = AodDrawables.getDrawableDataByName(styleInfo.getColorName());
            if (drawableDataByName == null) {
                drawableDataByName = (DrawableData) AodDrawables.sPureDrawableList.get(0);
            }
            this.mPanel.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), drawableDataByName.mResId));
            DrawableData drawableDataByName2 = AodDrawables.getDrawableDataByName(((ClockPanelCategoryInfo) styleInfo).getSecondaryColor());
            if (drawableDataByName2 == null) {
                drawableDataByName2 = (DrawableData) AodDrawables.sPureDrawableList.get(0);
            }
            int i2 = drawableDataByName2.mResId;
            this.mHourHand.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), i2));
            this.mMinuteHand.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), i2));
            this.mCenterDot.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), i2));
        }
    }
}

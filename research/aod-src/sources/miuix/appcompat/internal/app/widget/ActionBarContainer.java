package miuix.appcompat.internal.app.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.R;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.ActionBarTransitionListener;
import miuix.appcompat.app.OverlayMaskConfig;
import miuix.appcompat.internal.view.menu.action.ActionMenuView;
import miuix.appcompat.internal.view.menu.action.ResponsiveActionMenuView;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.RomUtils;
import miuix.core.util.WindowUtils;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.DeviceHelper;
import miuix.theme.token.hypermaterial.Blur;
import miuix.theme.token.hypermaterial.Mask;
import miuix.view.BlurApplier;
import miuix.view.BlurableWidget;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class ActionBarContainer extends FrameLayout implements BlurableWidget, ActionBar.FragmentViewPagerChangeListener {
    public ActionBarContextView mActionBarContextView;
    public int mActionBarHeightGap;
    public int mActionBarHeightTotalGap;
    public List mActionBarTransitionListeners;
    public ActionBarView mActionBarView;
    public int mActionButtonFloatingState;
    public ActionMenuView mActionModeMenuView;
    public Drawable mBackground;
    public Drawable[] mBackgroundArray;
    public final MiuiBlurUiHelper mBlurHelper;
    public boolean mContentFadeModeActive;
    public ActionBarCoordinateListener mCoordinateListener;
    public int mCoordinatedOffsetYInSearchModeAnimation;
    public int mCurBarExpandState;
    public boolean mCurBarResizable;
    public int mCurContextBarExpandState;
    public boolean mCurContextBarResizable;
    public boolean mCustomBackground;
    public boolean mCustomViewAutoFitSystemWindow;
    public int mDefaultMaskColor;
    public boolean mDrawBackground;
    public float mExtraPaddingTop;
    public final BlurApplier mGradientBlurApplier;
    public boolean mGradientNeedsUpdate;
    public Paint mGradientPaint;
    public Path mGradientPath;
    public Shader mGradientShader;
    public int mHeightMaxMeasureSpec;
    public boolean mInternalApplyBgBlur;
    public boolean mInternalApplySpiltBgBlur;
    public boolean mIsDarkThemeOverlay;
    public boolean mIsInActionMode;
    public boolean mIsInWideMode;
    public boolean mIsMiuixFloating;
    public boolean mIsSplit;
    public boolean mIsStacked;
    public boolean mIsTemporaryOverlayMode;
    public boolean mIsTransitioning;
    public float mLastActionBarResizingProcess;
    public int mLastGradientBandHeight;
    public int mLastGradientHeight;
    public int mLastGradientWidth;
    public int mLastToState;
    public float mMaskAlpha;
    public ValueAnimator mMaskAnimator;
    public int mMaskColor;
    public float mMaskExtraHeight;
    public MaterialDayNightConfig mMaterial;
    public boolean mNowShowing;
    public OverlayMaskConfig mOverlayMaskConfig;
    public boolean mOverlayMode;
    public ViewTreeObserver.OnGlobalLayoutListener mPendingGradientLayoutListener;
    public Rect mPendingInsets;
    public Runnable mPostExpandRecheckScheduler;
    public final Runnable mRefreshBlurRunnable;
    public boolean mRequestAnimation;
    public ActionMenuView mResidentActionMenuView;
    public boolean mShouldDrawOverlayMask;
    public AnimConfig mShowAnimConfig;
    public Drawable mSplitBackground;
    public boolean mSplitEnableBlur;
    public Drawable mStackedBackground;
    public View mStickyView;
    public boolean mStickyViewCollapsible;
    public View mTabContainer;
    public int mTabContainerPaddingTop;
    public Boolean mUserApplyBgBlur;
    public Boolean mUserApplySplitActionBarBgBlur;
    public Boolean mUserBgViewApplyBlur;
    public BlurApplier mUserBlurApplier;

    /* JADX INFO: renamed from: miuix.appcompat.internal.app.widget.ActionBarContainer$5, reason: invalid class name */
    class AnonymousClass5 extends TransitionListener {
        public final /* synthetic */ ActionBarContainer this$0;

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            this.this$0.setVisibility(8);
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean actionBarApplyBlur;
        public boolean actionBarEnableBlur;
        public boolean actionBarSupportBlur;
        public int userActionBarApplyBlur;
        public int userSplitActionBarApplyBlur;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.userActionBarApplyBlur = parcel.readInt();
            this.userSplitActionBarApplyBlur = parcel.readInt();
            this.actionBarSupportBlur = parcel.readInt() != 0;
            this.actionBarEnableBlur = parcel.readInt() != 0;
            this.actionBarApplyBlur = parcel.readInt() != 0;
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.userActionBarApplyBlur = parcel.readInt();
            this.userSplitActionBarApplyBlur = parcel.readInt();
            this.actionBarSupportBlur = parcel.readInt() != 0;
            this.actionBarEnableBlur = parcel.readInt() != 0;
            this.actionBarApplyBlur = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.userActionBarApplyBlur);
            parcel.writeInt(this.userSplitActionBarApplyBlur);
            parcel.writeInt(this.actionBarSupportBlur ? 1 : 0);
            parcel.writeInt(this.actionBarEnableBlur ? 1 : 0);
            parcel.writeInt(this.actionBarApplyBlur ? 1 : 0);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$5W3671uGuUMWat-WoZ3SJLwcris, reason: not valid java name */
    public static /* synthetic */ void m1315$r8$lambda$5W3671uGuUMWatWoZ3SJLwcris(ActionBarContainer actionBarContainer, ValueAnimator valueAnimator) {
        actionBarContainer.getClass();
        actionBarContainer.mMaskAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        actionBarContainer.invalidate();
    }

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNowShowing = true;
        this.mGradientBlurApplier = new BlurApplier() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer.1
            @Override // miuix.view.BlurApplier
            public void applyBlur(View view, int i, int i2) {
                ActionBarContainer.this.applyVerticalGradientBlur(view, i2);
            }
        };
        this.mRefreshBlurRunnable = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer.2
            @Override // java.lang.Runnable
            public void run() {
                if (ActionBarContainer.this.mBlurHelper == null || !ActionBarContainer.this.mBlurHelper.isApplyBlur()) {
                    return;
                }
                ActionBarContainer.this.mBlurHelper.refreshBlur();
            }
        };
        this.mInternalApplyBgBlur = false;
        this.mInternalApplySpiltBgBlur = false;
        this.mUserApplyBgBlur = null;
        this.mUserApplySplitActionBarBgBlur = null;
        this.mUserBgViewApplyBlur = null;
        this.mSplitEnableBlur = false;
        this.mResidentActionMenuView = null;
        this.mActionModeMenuView = null;
        this.mCustomBackground = false;
        this.mHeightMaxMeasureSpec = -1;
        this.mLastActionBarResizingProcess = 0.0f;
        this.mLastToState = 0;
        this.mActionBarHeightTotalGap = 0;
        this.mActionBarHeightGap = 0;
        this.mIsDarkThemeOverlay = false;
        this.mLastGradientHeight = -1;
        this.mLastGradientBandHeight = -1;
        this.mLastGradientWidth = -1;
        this.mGradientNeedsUpdate = true;
        this.mShouldDrawOverlayMask = false;
        this.mMaskAlpha = 0.0f;
        this.mActionButtonFloatingState = -1;
        this.mContentFadeModeActive = false;
        this.mIsTemporaryOverlayMode = false;
        this.mCoordinateListener = null;
        this.mActionBarTransitionListeners = new CopyOnWriteArrayList();
        setBackground(null);
        this.mGradientPath = new Path();
        Paint paint = new Paint(1);
        this.mGradientPaint = paint;
        paint.setDither(true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar);
        try {
            this.mIsDarkThemeOverlay = MiuixUIUtils.isDarkThemeOverlay(getContext(), R.color.miuix_default_color_on_surface_light);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_android_background);
            this.mBackground = drawable;
            this.mBackgroundArray = new Drawable[]{drawable, typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_actionBarEmbededTabsBackground), typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_actionBarStackedBackground)};
            this.mCustomViewAutoFitSystemWindow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ActionBar_customViewAutoFitSystemWindow, false);
            float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.ActionBar_maskExtraHeight, context.getResources().getDimension(R.dimen.miuix_appcompat_action_bar_mask_extra_height));
            this.mMaskExtraHeight = dimension;
            this.mMaskExtraHeight = dimension / context.getResources().getDisplayMetrics().density;
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.ActionBar_maskColor, !AttributeResolver.resolveBoolean(getContext(), android.R.attr.isLightTheme, true) ? -16777216 : -789517);
            this.mMaskColor = color;
            this.mDefaultMaskColor = color;
            this.mExtraPaddingTop = typedArrayObtainStyledAttributes.getDimension(R.styleable.ActionBar_extraPaddingTop, 0.0f) / context.getResources().getDisplayMetrics().density;
            if (getId() == R.id.split_action_bar) {
                this.mIsSplit = true;
                this.mSplitBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_android_backgroundSplit);
            }
            typedArrayObtainStyledAttributes.recycle();
            if (!this.mIsSplit) {
                setPadding(0, 0, 0, 0);
            }
            resizeSplitMaxHeight();
            updateWillNotDraw();
            this.mDrawBackground = true;
            if (!HyperMaterialUtils.isEnable() || this.mIsSplit) {
                this.mBlurHelper = null;
            } else {
                this.mMaterial = MaterialDayNightConfig.create(RomUtils.getHyperOsVersion() > 2 ? Mask.Pured_Regular : Blur.ExtraHeavy);
                this.mBlurHelper = new MiuiBlurUiHelper(context, this, false, false, new MiuiBlurUiHelper.BlurStateCallback() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer.3
                    public final boolean isDarkThemeOverlay;

                    {
                        this.isDarkThemeOverlay = MiuixUIUtils.isDarkThemeOverlay(ActionBarContainer.this.getContext(), R.color.miuix_default_color_on_surface_light);
                    }

                    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                    public Drawable getBackground() {
                        return ActionBarContainer.this.mBackground;
                    }

                    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                    public int getBackgroundColor() {
                        if (this.isDarkThemeOverlay) {
                            return AttributeResolver.resolveColor(ActionBarContainer.this.getContext(), miuix.theme.R.attr.colorSurface, 0);
                        }
                        return 0;
                    }

                    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                    public BlurApplier getBlurApplier() {
                        BlurApplier blurApplier = ActionBarContainer.this.mUserBlurApplier;
                        ActionBarContainer actionBarContainer = ActionBarContainer.this;
                        return blurApplier != null ? actionBarContainer.mUserBlurApplier : actionBarContainer.mGradientBlurApplier;
                    }

                    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                    public MaterialConfig.BlurConfig getBlurConfig(boolean z) {
                        MaterialDayNightConfig materialDayNightConfig = ActionBarContainer.this.mMaterial;
                        if (materialDayNightConfig != null) {
                            return materialDayNightConfig.getBlurConfig(z);
                        }
                        return null;
                    }

                    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                    public boolean isLightTheme() {
                        Integer colorFromDrawable;
                        if (ActionBarContainer.this.mBackground == null || (colorFromDrawable = MiuixUIUtils.getColorFromDrawable(ActionBarContainer.this.mBackground)) == null) {
                            return !this.isDarkThemeOverlay && AttributeResolver.resolveBoolean(ActionBarContainer.this.getContext(), R.attr.isLightTheme, true);
                        }
                        return MiuixUIUtils.isLightColor(colorFromDrawable.intValue()) && !this.isDarkThemeOverlay;
                    }

                    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                    public void onBlurApplyStateChanged(boolean z) {
                        ActionBarContainer.this.mDrawBackground = !z;
                        if (ActionBarContainer.this.mActionBarView != null) {
                            ActionBarContainer.this.mActionBarView.setApplyBgBlur(z);
                        }
                        if (ActionBarContainer.this.mActionBarContextView != null) {
                            ActionBarContainer.this.mActionBarContextView.updateBackground(z);
                        }
                        ActionBarContainer.this.invalidate();
                    }

                    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                    public void onBlurEnableStateChanged(boolean z) {
                        if (ActionBarContainer.this.mIsSplit) {
                            ActionBarContainer.this.mInternalApplySpiltBgBlur = z;
                            if (ActionBarContainer.this.mResidentActionMenuView != null) {
                                boolean zBooleanValue = ActionBarContainer.this.mUserApplySplitActionBarBgBlur != null ? ActionBarContainer.this.mUserApplySplitActionBarBgBlur.booleanValue() : ActionBarContainer.this.mInternalApplySpiltBgBlur;
                                if (z) {
                                    ActionBarContainer.this.mResidentActionMenuView.setSupportBlur(true);
                                    ActionBarContainer.this.mResidentActionMenuView.setEnableBlur(true);
                                }
                                ActionBarContainer.this.mResidentActionMenuView.applyBlur(zBooleanValue);
                            }
                        }
                    }
                });
            }
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public static int[] computeMaskBands(int i, int i2, int i3) {
        int i4 = i + i3;
        int i5 = i2 + i3;
        if (i5 > i4) {
            i5 = i4;
        }
        return new int[]{i4, i5, Math.max(0, i4 - i5)};
    }

    public static void resampleStops(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4) {
        int length = fArr.length;
        for (int i = 0; i < 17; i++) {
            float f = i / 16.0f;
            fArr3[i] = f;
            if (f <= fArr[0]) {
                fArr4[i] = fArr2[0];
            } else {
                int i2 = length - 1;
                if (f >= fArr[i2]) {
                    fArr4[i] = fArr2[i2];
                } else {
                    for (int i3 = 0; i3 < i2; i3++) {
                        float f2 = fArr[i3];
                        if (f >= f2) {
                            int i4 = i3 + 1;
                            float f3 = fArr[i4];
                            if (f <= f3) {
                                float f4 = f3 - f2;
                                float f5 = f4 > 1.0E-6f ? (f - f2) / f4 : 0.0f;
                                fArr4[i] = (fArr2[i3] * (1.0f - f5)) + (fArr2[i4] * f5);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void addActionBarTransitionListener(ActionBarTransitionListener actionBarTransitionListener) {
        if (actionBarTransitionListener != null) {
            this.mActionBarTransitionListeners.add(actionBarTransitionListener);
        }
    }

    public final void animateMaskAlpha(boolean z) {
        ValueAnimator valueAnimator = this.mMaskAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mMaskAnimator = null;
        }
        if (z) {
            this.mMaskAlpha = 1.0f;
            invalidate();
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mMaskAlpha, 0.0f);
        this.mMaskAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(100L);
        this.mMaskAnimator.setInterpolator(new LinearInterpolator());
        this.mMaskAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ActionBarContainer.m1315$r8$lambda$5W3671uGuUMWatWoZ3SJLwcris(this.f$0, valueAnimator2);
            }
        });
        this.mMaskAnimator.start();
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        if (this.mBlurHelper == null || this.mIsSplit) {
            return;
        }
        if (z && getVisibility() == 0) {
            this.mBlurHelper.applyBlur(true);
        } else {
            this.mBlurHelper.applyBlur(false);
        }
    }

    public final void applyInsetsTopByMargin(View view) {
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        Rect rect = this.mPendingInsets;
        int iDp2px = rect != null ? rect.top : 0;
        if (this.mExtraPaddingTop > 0.0f) {
            iDp2px += MiuixUIUtils.dp2px(getContext(), this.mExtraPaddingTop);
        }
        marginLayoutParams.topMargin = iDp2px;
        view.setLayoutParams(marginLayoutParams);
    }

    public final void applyStickyCollapseRatio(float f) {
        View view = this.mStickyView;
        if (view == null) {
            return;
        }
        if (!this.mStickyViewCollapsible) {
            view.setAlpha(1.0f);
            this.mStickyView.setTranslationY(0.0f);
        } else {
            float fMax = Math.max(0.0f, Math.min(1.0f, f));
            this.mStickyView.setAlpha(fMax);
            this.mStickyView.setTranslationY((-(1.0f - fMax)) * this.mStickyView.getMeasuredHeight());
        }
    }

    public final void applyVerticalGradientBlur(View view, final int i) {
        if (view != this) {
            throw new IllegalArgumentException("applyVerticalGradientBlur expects ActionBarContainer itself");
        }
        if (this.mPendingGradientLayoutListener != null) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this.mPendingGradientLayoutListener);
            this.mPendingGradientLayoutListener = null;
        }
        if (getHeight() > 0) {
            applyVerticalGradientBlurInternal(i);
        } else {
            this.mPendingGradientLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer.4
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    ActionBarContainer.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (ActionBarContainer.this.mPendingGradientLayoutListener == this) {
                        ActionBarContainer.this.mPendingGradientLayoutListener = null;
                    }
                    ActionBarContainer.this.applyVerticalGradientBlurInternal(i);
                }
            };
            getViewTreeObserver().addOnGlobalLayoutListener(this.mPendingGradientLayoutListener);
        }
    }

    public final void applyVerticalGradientBlurInternal(int i) {
        float fRound = Math.round(getResources().getDisplayMetrics().density * 40.0f);
        float height = getHeight();
        MiuiBlurUtils.setBackgroundBlurMode(this, 1);
        MiuiBlurUtils.setViewBlurMode(this, i);
        if (MiuiBlurUtils.setBackgroundLinearGradientBlur(this, new float[]{0.0f, 0.0f, fRound, 0.0f, height, 0.0f})) {
            return;
        }
        Log.w("miuix-appcompat", "Gradient blur unavailable (isDefaultFeatureEnable=" + HyperMaterialUtils.isDefaultFeatureEnable() + "), fallback to no blur");
        MiuiBlurUtils.clearBackgroundBlur(this);
        this.mDrawBackground = true;
        invalidate();
    }

    public final Shader createDitheredGradientShader(int i, int i2, float[] fArr, float[] fArr2, int i3, int i4) {
        RuntimeShader runtimeShader = new RuntimeShader("uniform half4 uPositions0;\nuniform half4 uPositions1;\nuniform half4 uPositions2;\nuniform half4 uPositions3;\nuniform half  uPosition16;\nuniform half4 uAlphas0;\nuniform half4 uAlphas1;\nuniform half4 uAlphas2;\nuniform half4 uAlphas3;\nuniform half  uAlpha16;\nuniform half4 uBaseColor;\nuniform half  uBandTop;\nuniform half  uBandHeight;\n\n// 单段 piecewise linear 求值: 若 t 在 [p0,p1] 区间, 返回插值后 alpha; 否则返回 -1\nhalf lerpSeg(half t, half p0, half p1, half a0, half a1) {\n    if (t >= p0 && t <= p1) {\n        half u = (t - p0) / max(p1 - p0, 1e-5);\n        return mix(a0, a1, u);\n    }\n    return -1.0;\n}\n\nhalf lerpAlpha(half t) {\n    if (t <= uPositions0.x) return uAlphas0.x;\n    if (t >= uPosition16)   return uAlpha16;\n    half r;\n    r = lerpSeg(t, uPositions0.x, uPositions0.y, uAlphas0.x, uAlphas0.y); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions0.y, uPositions0.z, uAlphas0.y, uAlphas0.z); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions0.z, uPositions0.w, uAlphas0.z, uAlphas0.w); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions0.w, uPositions1.x, uAlphas0.w, uAlphas1.x); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions1.x, uPositions1.y, uAlphas1.x, uAlphas1.y); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions1.y, uPositions1.z, uAlphas1.y, uAlphas1.z); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions1.z, uPositions1.w, uAlphas1.z, uAlphas1.w); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions1.w, uPositions2.x, uAlphas1.w, uAlphas2.x); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions2.x, uPositions2.y, uAlphas2.x, uAlphas2.y); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions2.y, uPositions2.z, uAlphas2.y, uAlphas2.z); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions2.z, uPositions2.w, uAlphas2.z, uAlphas2.w); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions2.w, uPositions3.x, uAlphas2.w, uAlphas3.x); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions3.x, uPositions3.y, uAlphas3.x, uAlphas3.y); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions3.y, uPositions3.z, uAlphas3.y, uAlphas3.z); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions3.z, uPositions3.w, uAlphas3.z, uAlphas3.w); if (r >= 0.0) return r;\n    r = lerpSeg(t, uPositions3.w, uPosition16,    uAlphas3.w, uAlpha16);    if (r >= 0.0) return r;\n    return uAlpha16;\n}\n\n// 4x4 ordered Bayer 矩阵, 用 mod 取模索引 (AGSL 不允许位运算)\n// 返回值范围 [-0.5, 0.5)\nhalf bayer4(half2 fc) {\n    half x = mod(fc.x, 4.0);\n    half y = mod(fc.y, 4.0);\n    // 4x4 Bayer = {{0,8,2,10},{12,4,14,6},{3,11,1,9},{15,7,13,5}}\n    half v;\n    if (y < 1.0) {\n        if      (x < 1.0) v =  0.0;\n        else if (x < 2.0) v =  8.0;\n        else if (x < 3.0) v =  2.0;\n        else              v = 10.0;\n    } else if (y < 2.0) {\n        if      (x < 1.0) v = 12.0;\n        else if (x < 2.0) v =  4.0;\n        else if (x < 3.0) v = 14.0;\n        else              v =  6.0;\n    } else if (y < 3.0) {\n        if      (x < 1.0) v =  3.0;\n        else if (x < 2.0) v = 11.0;\n        else if (x < 3.0) v =  1.0;\n        else              v =  9.0;\n    } else {\n        if      (x < 1.0) v = 15.0;\n        else if (x < 2.0) v =  7.0;\n        else if (x < 3.0) v = 13.0;\n        else              v =  5.0;\n    }\n    return v / 16.0 - 0.5;\n}\n\nhalf4 main(float2 fragCoord) {\n    // 归一化坐标映射到底部高度带: y <= uBandTop -> t=0 (不透明顶端), 带内线性映射到 [0,1]\n    half t = clamp((half(fragCoord.y) - uBandTop) / uBandHeight, 0.0, 1.0);\n    half af = lerpAlpha(t);\n    // ±0.5/255 dither 在最终 alpha 上叠加, 打散 8-bit 量化阶梯\n    half dither = bayer4(half2(fragCoord)) / 255.0;\n    half outA = clamp(uBaseColor.a * af + dither, 0.0, 1.0);\n    return half4(uBaseColor.rgb * outA, outA);\n}\n");
        float[] fArr3 = new float[17];
        float[] fArr4 = new float[17];
        resampleStops(fArr2, fArr, fArr3, fArr4);
        runtimeShader.setFloatUniform("uPositions0", fArr3[0], fArr3[1], fArr3[2], fArr3[3]);
        runtimeShader.setFloatUniform("uPositions1", fArr3[4], fArr3[5], fArr3[6], fArr3[7]);
        runtimeShader.setFloatUniform("uPositions2", fArr3[8], fArr3[9], fArr3[10], fArr3[11]);
        runtimeShader.setFloatUniform("uPositions3", fArr3[12], fArr3[13], fArr3[14], fArr3[15]);
        runtimeShader.setFloatUniform("uPosition16", fArr3[16]);
        runtimeShader.setFloatUniform("uAlphas0", fArr4[0], fArr4[1], fArr4[2], fArr4[3]);
        runtimeShader.setFloatUniform("uAlphas1", fArr4[4], fArr4[5], fArr4[6], fArr4[7]);
        runtimeShader.setFloatUniform("uAlphas2", fArr4[8], fArr4[9], fArr4[10], fArr4[11]);
        runtimeShader.setFloatUniform("uAlphas3", fArr4[12], fArr4[13], fArr4[14], fArr4[15]);
        runtimeShader.setFloatUniform("uAlpha16", fArr4[16]);
        runtimeShader.setFloatUniform("uBaseColor", ((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, i2 / 255.0f);
        runtimeShader.setFloatUniform("uBandTop", i3);
        runtimeShader.setFloatUniform("uBandHeight", Math.max(1, i4));
        return runtimeShader;
    }

    public final void dispatchOverlayMaskAndFloatingState(boolean z) {
        ActionBarView actionBarView;
        Iterator it = this.mActionBarTransitionListeners.iterator();
        while (it.hasNext()) {
            ((ActionBarTransitionListener) it.next()).onOverlayMaskStateChanged(!z);
        }
        if (this.mActionButtonFloatingState != -1 || (actionBarView = this.mActionBarView) == null) {
            return;
        }
        actionBarView.updateActionButtonFloatingState(z);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mRequestAnimation) {
            post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer.7
                @Override // java.lang.Runnable
                public void run() {
                    ActionBarContainer.this.show(true);
                }
            });
            this.mRequestAnimation = false;
        }
    }

    public final void drawMainBackground(Canvas canvas) {
        Drawable drawable = this.mBackground;
        if (drawable == null || !this.mDrawBackground) {
            return;
        }
        drawable.draw(canvas);
    }

    public final void drawNormalMode(Canvas canvas) {
        drawMainBackground(canvas);
        drawOverlayMaskIfNeeded(canvas);
    }

    public final void drawOverlayMask(Canvas canvas) {
        float[] defaultAlphaFactors;
        float[] defaultPositions;
        ActionBarContainer actionBarContainer;
        int[] iArrComputeMaskBands = computeMaskBands(getHeight(), getCollapsedHeight(), MiuixUIUtils.dp2px(getContext(), this.mMaskExtraHeight));
        int i = iArrComputeMaskBands[0];
        int i2 = iArrComputeMaskBands[1];
        int i3 = iArrComputeMaskBands[2];
        int width = getWidth();
        if (this.mLastGradientHeight == i && this.mLastGradientBandHeight == i2 && this.mLastGradientWidth == width && !this.mGradientNeedsUpdate) {
            actionBarContainer = this;
        } else {
            this.mLastGradientHeight = i;
            this.mLastGradientBandHeight = i2;
            this.mLastGradientWidth = width;
            this.mGradientNeedsUpdate = false;
            int iAlpha = Color.alpha(this.mMaskColor);
            int i4 = this.mMaskColor & 16777215;
            OverlayMaskConfig overlayMaskConfig = this.mOverlayMaskConfig;
            if (overlayMaskConfig != null) {
                defaultAlphaFactors = overlayMaskConfig.getAlphaFactors();
                defaultPositions = this.mOverlayMaskConfig.getPositions();
            } else {
                defaultAlphaFactors = OverlayMaskConfig.getDefaultAlphaFactors();
                defaultPositions = OverlayMaskConfig.getDefaultPositions();
            }
            actionBarContainer = this;
            Shader shaderCreateDitheredGradientShader = actionBarContainer.createDitheredGradientShader(i4, iAlpha, defaultAlphaFactors, defaultPositions, i3, i2);
            actionBarContainer.mGradientShader = shaderCreateDitheredGradientShader;
            actionBarContainer.mGradientPaint.setShader(shaderCreateDitheredGradientShader);
            actionBarContainer.mGradientPath.reset();
            actionBarContainer.mGradientPath.addRect(0.0f, 0.0f, width, i, Path.Direction.CW);
        }
        actionBarContainer.mGradientPaint.setAlpha(Math.round(actionBarContainer.mMaskAlpha * 255.0f));
        canvas.drawPath(actionBarContainer.mGradientPath, actionBarContainer.mGradientPaint);
    }

    public final void drawOverlayMaskIfNeeded(Canvas canvas) {
        if (this.mContentFadeModeActive || !this.mOverlayMode || this.mMaskAlpha <= 0.0f || Color.alpha(this.mMaskColor) == 0 || this.mCustomBackground || hasNonTransparentBackground()) {
            return;
        }
        drawOverlayMask(canvas);
    }

    public final void drawSplitMode(Canvas canvas) {
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mBackground;
        if (drawable != null && drawable.isStateful()) {
            this.mBackground.setState(getDrawableState());
        }
        Drawable drawable2 = this.mStackedBackground;
        if (drawable2 != null && drawable2.isStateful()) {
            this.mStackedBackground.setState(getDrawableState());
        }
        Drawable drawable3 = this.mSplitBackground;
        if (drawable3 == null || !drawable3.isStateful()) {
            return;
        }
        this.mSplitBackground.setState(getDrawableState());
    }

    public void finishActionMode() {
        ActionBarView actionBarView;
        this.mIsInActionMode = false;
        if (this.mOverlayMode && !this.mIsTemporaryOverlayMode && this.mShouldDrawOverlayMask && this.mActionButtonFloatingState == -1 && (actionBarView = this.mActionBarView) != null) {
            actionBarView.updateActionButtonFloatingState(true);
        }
    }

    public ActionBarCoordinateListener getActionBarCoordinateListener() {
        return this.mCoordinateListener;
    }

    public final int getActionMenuViewCollapseHeight(ActionMenuView actionMenuView) {
        if (actionMenuView == null || actionMenuView.getVisibility() != 0 || actionMenuView.getAlpha() == 0.0f || actionMenuView.getCollapsedHeight() <= 0) {
            return 0;
        }
        return Math.max(0, actionMenuView.getCollapsedHeight());
    }

    public final int getActionMenuViewInsetHeight(ActionMenuView actionMenuView) {
        if (actionMenuView == null || actionMenuView.getVisibility() != 0 || actionMenuView.getAlpha() == 0.0f || actionMenuView.getCollapsedHeight() <= 0) {
            return 0;
        }
        return Math.max(0, (int) (actionMenuView.getCollapsedHeight() - actionMenuView.getTranslationY()));
    }

    public int getCollapsedHeight() {
        int collapsedHeight;
        int i;
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView == null || actionBarContextView.getVisibility() == 8 || this.mActionBarContextView.getMeasuredHeight() <= 0) {
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView == null) {
                return 0;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) actionBarView.getLayoutParams();
            collapsedHeight = this.mActionBarView.getCollapsedHeight();
            i = marginLayoutParams.topMargin;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mActionBarContextView.getLayoutParams();
            collapsedHeight = this.mActionBarContextView.getCollapsedHeight();
            i = marginLayoutParams2.topMargin;
        }
        return collapsedHeight + i;
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialConfig getCurrentMaterial() {
        return super.getCurrentMaterial();
    }

    public int getExpandedHeight() {
        int expandedHeight;
        int i;
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView == null || actionBarContextView.getVisibility() == 8 || this.mActionBarContextView.getMeasuredHeight() <= 0) {
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView == null) {
                return 0;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) actionBarView.getLayoutParams();
            expandedHeight = this.mActionBarView.getExpandedHeight();
            i = marginLayoutParams.topMargin;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mActionBarContextView.getLayoutParams();
            expandedHeight = this.mActionBarContextView.getExpandedHeight();
            i = marginLayoutParams2.topMargin;
        }
        return expandedHeight + i;
    }

    public float getExtraPaddingTop() {
        return this.mExtraPaddingTop;
    }

    public int getInsetHeight() {
        if (this.mIsSplit) {
            return Math.max(Math.max(0, getActionMenuViewInsetHeight(this.mActionModeMenuView)), getActionMenuViewInsetHeight(this.mResidentActionMenuView));
        }
        return 0;
    }

    public float getMaskExtraHeight() {
        return this.mMaskExtraHeight;
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialDayNightConfig getMaterial() {
        return super.getMaterial();
    }

    public Rect getPendingInsets() {
        return this.mPendingInsets;
    }

    public Drawable getPrimaryBackground() {
        return this.mBackground;
    }

    public int getSplitCollapsedHeight() {
        if (this.mIsSplit) {
            return Math.max(Math.max(0, getActionMenuViewCollapseHeight(this.mActionModeMenuView)), getActionMenuViewCollapseHeight(this.mResidentActionMenuView));
        }
        return 0;
    }

    public int getStickyAreaHeight() {
        View view = this.mStickyView;
        if (view == null || view.getVisibility() == 8 || this.mStickyView.getMeasuredHeight() <= 0) {
            return 0;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mStickyView.getLayoutParams();
        return this.mStickyView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Nullable
    public View getStickyView() {
        return this.mStickyView;
    }

    public View getTabContainer() {
        return this.mTabContainer;
    }

    public final boolean hasNonTransparentBackground() {
        Integer colorFromDrawable;
        Drawable drawable = this.mBackground;
        return (drawable == null || (colorFromDrawable = MiuixUIUtils.getColorFromDrawable(drawable)) == null || Color.alpha(colorFromDrawable.intValue()) == 0) ? false : true;
    }

    public boolean isApplyBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isApplyBlur();
    }

    public boolean isEnableBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
        if (miuiBlurUiHelper == null) {
            return this.mIsSplit && this.mSplitEnableBlur && MiuiBlurUtils.isEffectEnable(getContext());
        }
        return miuiBlurUiHelper.isEnableBlur();
    }

    public boolean isShouldDrawOverlayMask() {
        return this.mShouldDrawOverlayMask;
    }

    public boolean isSupportBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isSupportBlur();
    }

    public void onActionBarExpandStateToExpand() {
        if (this.mShouldDrawOverlayMask) {
            this.mShouldDrawOverlayMask = false;
            animateMaskAlpha(false);
            Iterator it = this.mActionBarTransitionListeners.iterator();
            while (it.hasNext()) {
                ((ActionBarTransitionListener) it.next()).onOverlayMaskStateChanged(true);
            }
            Runnable runnable = this.mPostExpandRecheckScheduler;
            if (runnable != null) {
                runnable.run();
            }
        }
        if (this.mActionButtonFloatingState == -1) {
            ActionBarContextView actionBarContextView = this.mActionBarContextView;
            if (actionBarContextView != null) {
                actionBarContextView.updateActionButtonFloatingState(false);
            }
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView != null) {
                actionBarView.updateActionButtonFloatingState(false);
            }
        }
    }

    public void onActionModeMenuViewAdded(ActionMenuView actionMenuView) {
        this.mActionModeMenuView = actionMenuView;
        if (actionMenuView == null || !this.mIsSplit) {
            if (actionMenuView == null || !isSupportBlur()) {
                return;
            }
            Boolean bool = this.mUserApplySplitActionBarBgBlur;
            actionMenuView.applyBlur((bool != null ? bool.booleanValue() : isEnableBlur()) && actionMenuView.getMeasuredWidth() > 0 && actionMenuView.getMeasuredHeight() > 0);
            return;
        }
        boolean zIsEnableBlur = isEnableBlur();
        actionMenuView.setSupportBlur(true);
        actionMenuView.setEnableBlur(zIsEnableBlur);
        if (actionMenuView.getMeasuredWidth() <= 0 || actionMenuView.getMeasuredHeight() <= 0) {
            return;
        }
        actionMenuView.applyBlur(zIsEnableBlur);
    }

    public void onActionModeMenuViewRemoved(ActionMenuView actionMenuView) {
        if (this.mActionModeMenuView == actionMenuView) {
            this.mActionModeMenuView = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mOverlayMode) {
            updateParentClipChildren();
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        ActionMenuView actionMenuView;
        super.onConfigurationChanged(configuration);
        resizeSplitMaxHeight();
        this.mGradientNeedsUpdate = true;
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.onConfigChanged();
            if (!this.mBlurHelper.isEnableBlur() && this.mUserBgViewApplyBlur == null) {
                updateBackgroundInternal(false);
            }
        }
        if (this.mIsSplit && (actionMenuView = this.mResidentActionMenuView) != null) {
            actionMenuView.applyBlur(isEnableBlur());
        }
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.refreshFloatingState();
        }
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null) {
            actionBarContextView.refreshFloatingState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.mMaskAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mMaskAnimator = null;
        }
        setActionBarCoordinateListener(null);
        this.mActionBarTransitionListeners.clear();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if (this.mIsSplit) {
            drawSplitMode(canvas);
        } else {
            drawNormalMode(canvas);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = (ActionBarView) findViewById(R.id.action_bar);
        this.mActionBarContextView = (ActionBarContextView) findViewById(R.id.action_context_bar);
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.bindActionBarTransitionListeners(this.mActionBarTransitionListeners);
            this.mCurBarExpandState = this.mActionBarView.getExpandState();
            this.mCurBarResizable = this.mActionBarView.isResizable();
        }
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null) {
            this.mCurContextBarExpandState = actionBarContextView.getExpandState();
            this.mCurContextBarResizable = this.mActionBarContextView.isResizable();
            this.mActionBarContextView.setActionBarView(this.mActionBarView);
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        return !this.mIsSplit;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mIsTransitioning || super.onInterceptTouchEvent(motionEvent);
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:57:0x013d  */
    /* JADX WARN: Code duplicated, block: B:58:0x013f  */
    /* JADX WARN: Code duplicated, block: B:61:0x0154  */
    /* JADX WARN: Code duplicated, block: B:64:0x015c  */
    /* JADX WARN: Code duplicated, block: B:65:0x015e  */
    /* JADX WARN: Code duplicated, block: B:73:0x017a  */
    /* JADX WARN: Code duplicated, block: B:77:0x018a  */
    /* JADX WARN: Code duplicated, block: B:82:0x01a8  */
    /* JADX WARN: Code duplicated, block: B:90:0x019c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x0184 A[SYNTHETIC] */
    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        boolean z2;
        int i6;
        int expandedHeight;
        int collapsedHeight;
        int iMax;
        float f;
        float fMin;
        float f2;
        int i7;
        boolean zUpdateExpandStateOnLayout;
        ActionMenuView actionMenuView;
        ActionBarCoordinateListener actionBarCoordinateListener;
        super.onLayout(z, i, i2, i3, i4);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = (int) (getMeasuredWidth() / getContext().getResources().getDisplayMetrics().density);
        View view = this.mStickyView;
        if (view == null || view.getVisibility() == 8 || this.mStickyView.getMeasuredHeight() <= 0) {
            i5 = 0;
        } else {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mStickyView.getLayoutParams();
            int measuredHeight2 = this.mStickyView.getMeasuredHeight();
            int i8 = layoutParams.topMargin + measuredHeight2;
            int i9 = layoutParams.bottomMargin;
            i5 = i8 + i9;
            int i10 = measuredHeight - i9;
            this.mStickyView.layout(layoutParams.leftMargin + i, i10 - measuredHeight2, i3 - layoutParams.rightMargin, i10);
        }
        View view2 = this.mTabContainer;
        if (view2 != null && view2.getVisibility() != 8) {
            int measuredHeight3 = this.mTabContainer.getMeasuredHeight();
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView == null || actionBarView.getVisibility() != 0 || this.mActionBarView.getMeasuredHeight() <= 0) {
                Rect rect = this.mPendingInsets;
                measuredHeight3 += rect != null ? rect.top : 0;
                View view3 = this.mTabContainer;
                int paddingLeft = view3.getPaddingLeft();
                Rect rect2 = this.mPendingInsets;
                view3.setPadding(paddingLeft, rect2 != null ? rect2.top + this.mTabContainerPaddingTop : this.mTabContainerPaddingTop, this.mTabContainer.getPaddingRight(), this.mTabContainer.getPaddingBottom());
            } else {
                View view4 = this.mTabContainer;
                view4.setPadding(view4.getPaddingLeft(), this.mTabContainerPaddingTop, this.mTabContainer.getPaddingRight(), this.mTabContainer.getPaddingBottom());
            }
            int i11 = measuredHeight - i5;
            this.mTabContainer.layout(i, i11 - measuredHeight3, i3, i11);
        }
        if (this.mIsSplit) {
            Drawable drawable = this.mSplitBackground;
            if (drawable != null) {
                drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                z2 = true;
            } else {
                z2 = false;
            }
        } else {
            selectDrawable();
            Drawable drawable2 = this.mBackground;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, i3 - i, measuredHeight);
                z2 = true;
            } else {
                z2 = false;
            }
        }
        this.mIsInWideMode = measuredWidth > 600;
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView == null || !this.mIsInActionMode) {
            ActionBarView actionBarView2 = this.mActionBarView;
            if (actionBarView2 != null) {
                this.mActionBarHeightGap = actionBarView2.getMeasuredHeight() - this.mActionBarView.getCollapsedHeight();
                expandedHeight = this.mActionBarView.getExpandedHeight();
                collapsedHeight = this.mActionBarView.getCollapsedHeight();
            } else {
                i6 = 0;
            }
            iMax = Math.max(0, this.mActionBarHeightGap);
            this.mActionBarHeightGap = iMax;
            if (i6 == 0) {
                f = 1.0f;
            } else {
                f = ((i6 - iMax) * 1.0f) / i6;
            }
            this.mActionBarHeightTotalGap = i6;
            fMin = Math.min(1.0f, f);
            if (getTranslationY() < 0.0f) {
                fMin = 0.0f;
            }
            f2 = this.mLastActionBarResizingProcess - fMin;
            if (f2 > 0.0f) {
                i7 = 2;
            } else {
                i7 = 0;
            }
            this.mLastToState = i7;
            if (this.mOverlayMode && (actionBarCoordinateListener = this.mCoordinateListener) != null) {
                actionBarCoordinateListener.onActionBarResizing(i7, fMin, this.mActionBarHeightGap + this.mCoordinatedOffsetYInSearchModeAnimation, this.mActionBarHeightTotalGap);
            }
            applyStickyCollapseRatio(fMin);
            ActionBarView actionBarView3 = this.mActionBarView;
            zUpdateExpandStateOnLayout = actionBarView3 != null ? actionBarView3.updateExpandStateOnLayout() : false;
            for (ActionBarTransitionListener actionBarTransitionListener : this.mActionBarTransitionListeners) {
                actionBarTransitionListener.onActionBarMove(f2, fMin);
                actionBarTransitionListener.onActionBarResizing(this.mLastToState, fMin, this.mActionBarHeightGap);
                if (zUpdateExpandStateOnLayout) {
                    actionBarTransitionListener.onExpandStateChanged(this.mActionBarView.mExpandStateOnLayout);
                }
            }
            this.mLastActionBarResizingProcess = fMin;
            if (z2) {
                invalidate();
            }
            if (this.mIsSplit || (actionMenuView = this.mResidentActionMenuView) == null) {
            }
            actionMenuView.applyBlur(isEnableBlur());
            return;
        }
        this.mActionBarHeightGap = actionBarContextView.getMeasuredHeight() - this.mActionBarContextView.getCollapsedHeight();
        expandedHeight = this.mActionBarContextView.getExpandedHeight();
        collapsedHeight = this.mActionBarContextView.getCollapsedHeight();
        i6 = expandedHeight - collapsedHeight;
        iMax = Math.max(0, this.mActionBarHeightGap);
        this.mActionBarHeightGap = iMax;
        if (i6 == 0) {
            f = 1.0f;
        } else {
            f = ((i6 - iMax) * 1.0f) / i6;
        }
        this.mActionBarHeightTotalGap = i6;
        fMin = Math.min(1.0f, f);
        if (getTranslationY() < 0.0f) {
            fMin = 0.0f;
        }
        f2 = this.mLastActionBarResizingProcess - fMin;
        if (f2 > 0.0f) {
            i7 = 2;
        } else {
            i7 = 0;
        }
        this.mLastToState = i7;
        if (this.mOverlayMode) {
            actionBarCoordinateListener.onActionBarResizing(i7, fMin, this.mActionBarHeightGap + this.mCoordinatedOffsetYInSearchModeAnimation, this.mActionBarHeightTotalGap);
        }
        applyStickyCollapseRatio(fMin);
        ActionBarView actionBarView4 = this.mActionBarView;
        if (actionBarView4 != null) {
        }
        while (r11.hasNext()) {
            actionBarTransitionListener.onActionBarMove(f2, fMin);
            actionBarTransitionListener.onActionBarResizing(this.mLastToState, fMin, this.mActionBarHeightGap);
            if (zUpdateExpandStateOnLayout) {
                actionBarTransitionListener.onExpandStateChanged(this.mActionBarView.mExpandStateOnLayout);
            }
        }
        this.mLastActionBarResizingProcess = fMin;
        if (z2) {
            invalidate();
        }
        if (this.mIsSplit) {
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int measuredHeight;
        int measuredHeight2;
        int measuredHeight3;
        Rect rect;
        if (this.mIsSplit) {
            onMeasureSplit(i, i2);
            return;
        }
        View view = this.mTabContainer;
        if (view != null) {
            view.setPadding(view.getPaddingLeft(), this.mTabContainerPaddingTop, this.mTabContainer.getPaddingRight(), this.mTabContainer.getPaddingBottom());
        }
        applyInsetsTopByMargin(this.mActionBarView);
        applyInsetsTopByMargin(this.mActionBarContextView);
        super.onMeasure(i, i2);
        ActionBarView actionBarView = this.mActionBarView;
        boolean z = (actionBarView == null || actionBarView.getVisibility() == 8 || this.mActionBarView.getMeasuredHeight() <= 0) ? false : true;
        if (z) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mActionBarView.getLayoutParams();
            measuredHeight = this.mActionBarView.isCollapsed() ? layoutParams.topMargin : layoutParams.bottomMargin + this.mActionBarView.getMeasuredHeight() + layoutParams.topMargin;
        } else {
            measuredHeight = 0;
        }
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if ((actionBarContextView == null || actionBarContextView.getVisibility() == 8 || this.mActionBarContextView.getMeasuredHeight() <= 0) ? false : true) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mActionBarContextView.getLayoutParams();
            measuredHeight2 = this.mActionBarContextView.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
        } else {
            measuredHeight2 = 0;
        }
        if (measuredHeight > 0 || measuredHeight2 > 0) {
            setMeasuredDimension(getMeasuredWidth(), Math.max(measuredHeight, measuredHeight2));
        }
        View view2 = this.mStickyView;
        if (view2 == null || view2.getVisibility() == 8 || this.mStickyView.getMeasuredHeight() <= 0) {
            measuredHeight3 = 0;
        } else {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mStickyView.getLayoutParams();
            measuredHeight3 = this.mStickyView.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
        }
        View view3 = this.mTabContainer;
        if (view3 == null || view3.getVisibility() == 8) {
            if (measuredHeight3 > 0 && (measuredHeight > 0 || measuredHeight2 > 0)) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + measuredHeight3);
            }
        } else if (View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE) {
            setMeasuredDimension(getMeasuredWidth(), Math.min(measuredHeight + this.mTabContainer.getMeasuredHeight() + measuredHeight3, View.MeasureSpec.getSize(i2)) + ((z || (rect = this.mPendingInsets) == null) ? 0 : rect.top));
        }
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() == 0 && childAt.getMeasuredHeight() > 0 && childAt.getMeasuredWidth() > 0) {
                i3++;
            }
        }
        if (i3 == 0) {
            setMeasuredDimension(0, 0);
        }
    }

    public final void onMeasureSplit(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824);
        }
        int i3 = this.mHeightMaxMeasureSpec;
        if (i3 != -1) {
            i2 = i3;
        }
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int iMax = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            iMax = Math.max(iMax, getChildAt(i4).getMeasuredHeight());
        }
        if (iMax == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        ActionMenuView actionMenuView = this.mResidentActionMenuView;
        if (actionMenuView == null || !actionMenuView.hasOnlyCustomView()) {
            return;
        }
        ActionMenuView actionMenuView2 = this.mResidentActionMenuView;
        if (!(actionMenuView2 instanceof ResponsiveActionMenuView) || ((ResponsiveActionMenuView) actionMenuView2).isSuspend()) {
            return;
        }
        setMeasuredDimension(getMeasuredWidth(), iMax);
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3, int[] iArr2) {
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null && actionBarContextView.getVisibility() == 0) {
            this.mActionBarContextView.onNestedPreScroll(view, i, i2, iArr, i3, iArr2);
        } else if (!this.mIsSplit && getVisibility() != 8) {
            this.mActionBarView.onNestedPreScroll(view, i, i2, iArr, i3, iArr2);
        }
        if (!this.mOverlayMode || this.mIsTemporaryOverlayMode || i2 <= 0 || i2 - iArr[1] <= 0) {
            return;
        }
        if (!this.mShouldDrawOverlayMask) {
            this.mShouldDrawOverlayMask = true;
            animateMaskAlpha(true);
            Iterator it = this.mActionBarTransitionListeners.iterator();
            while (it.hasNext()) {
                ((ActionBarTransitionListener) it.next()).onOverlayMaskStateChanged(false);
            }
            if (this.mActionButtonFloatingState == -1) {
                ActionBarContextView actionBarContextView2 = this.mActionBarContextView;
                if (actionBarContextView2 == null || actionBarContextView2.getVisibility() != 0) {
                    ActionBarView actionBarView = this.mActionBarView;
                    if (actionBarView != null) {
                        actionBarView.updateActionButtonFloatingState(true);
                    }
                } else {
                    this.mActionBarContextView.updateActionButtonFloatingState(true);
                }
            }
        }
        if (!this.mIsSplit && getVisibility() == 8) {
            setActionBarBlurByNestedScrolled(true);
            this.mActionBarView.setExpandState(0);
            ActionBarCoordinateListener actionBarCoordinateListener = this.mCoordinateListener;
            if (actionBarCoordinateListener != null) {
                actionBarCoordinateListener.onActionBarResizing(0, 1.0f, 0, this.mActionBarHeightTotalGap);
            }
        }
        if (isLayoutRequested()) {
            return;
        }
        setActionBarBlurByNestedScrolled(true);
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr, int[] iArr2) {
        int i6 = iArr[1];
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null && actionBarContextView.getVisibility() == 0) {
            this.mActionBarContextView.onNestedScroll(view, i, i2, i3, i4, i5, iArr, iArr2);
        } else if (!this.mIsSplit && getVisibility() != 8) {
            this.mActionBarView.onNestedScroll(view, i, i2, i3, i4, i5, iArr, iArr2);
        }
        int i7 = iArr[1] - i6;
        if (!this.mOverlayMode || this.mIsTemporaryOverlayMode || i4 >= 0 || i7 > 0) {
            return;
        }
        setActionBarBlurByNestedScrolled(false);
        if (this.mShouldDrawOverlayMask) {
            this.mShouldDrawOverlayMask = false;
            animateMaskAlpha(false);
            Iterator it = this.mActionBarTransitionListeners.iterator();
            while (it.hasNext()) {
                ((ActionBarTransitionListener) it.next()).onOverlayMaskStateChanged(true);
            }
            if (this.mActionButtonFloatingState == -1) {
                ActionBarContextView actionBarContextView2 = this.mActionBarContextView;
                if (actionBarContextView2 != null) {
                    actionBarContextView2.updateActionButtonFloatingState(false);
                }
                ActionBarView actionBarView = this.mActionBarView;
                if (actionBarView != null) {
                    actionBarView.updateActionButtonFloatingState(false);
                }
            }
        }
        if (this.mIsSplit || getVisibility() != 8) {
            return;
        }
        this.mActionBarView.setExpandState(2);
        ActionBarCoordinateListener actionBarCoordinateListener = this.mCoordinateListener;
        if (actionBarCoordinateListener != null) {
            int i8 = this.mActionBarHeightTotalGap;
            actionBarCoordinateListener.onActionBarResizing(2, 0.0f, i8, i8);
        }
    }

    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null && actionBarContextView.getVisibility() == 0) {
            this.mActionBarContextView.onNestedScrollAccepted(view, view2, i, i2);
        } else {
            if (this.mIsSplit || getVisibility() == 8) {
                return;
            }
            this.mActionBarView.onNestedScrollAccepted(view, view2, i, i2);
        }
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
        ActionMenuView actionMenuView;
        if (!this.mIsSplit || (actionMenuView = this.mResidentActionMenuView) == null) {
            return;
        }
        actionMenuView.onPageScrolled(i, f, z, z2);
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageSelected(int i) {
    }

    public void onResidentActionMenuViewAdded(ActionMenuView actionMenuView) {
        this.mResidentActionMenuView = actionMenuView;
        if (actionMenuView != null && this.mIsSplit) {
            boolean zIsEnableBlur = isEnableBlur();
            actionMenuView.setSupportBlur(true);
            actionMenuView.setEnableBlur(zIsEnableBlur);
            actionMenuView.applyBlur(zIsEnableBlur);
            return;
        }
        if (actionMenuView == null || !isSupportBlur()) {
            return;
        }
        actionMenuView.setSupportBlur(isSupportBlur());
        actionMenuView.setEnableBlur(isEnableBlur());
        Boolean bool = this.mUserApplySplitActionBarBgBlur;
        actionMenuView.applyBlur((bool != null ? bool.booleanValue() : isEnableBlur()) && getMeasuredWidth() > 0 && getMeasuredHeight() > 0);
    }

    public void onResidentActionMenuViewRemoved(ActionMenuView actionMenuView) {
        if (this.mResidentActionMenuView == actionMenuView) {
            this.mResidentActionMenuView = null;
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.userActionBarApplyBlur;
        if (i == -1) {
            this.mUserApplyBgBlur = null;
        } else if (i == 0) {
            this.mUserApplyBgBlur = Boolean.FALSE;
        } else if (i == 1) {
            this.mUserApplyBgBlur = Boolean.TRUE;
        }
        int i2 = savedState.userSplitActionBarApplyBlur;
        if (i2 == -1) {
            this.mUserApplySplitActionBarBgBlur = null;
        } else if (i2 == 0) {
            this.mUserApplySplitActionBarBgBlur = Boolean.FALSE;
        } else if (i2 == 1) {
            this.mUserApplySplitActionBarBgBlur = Boolean.TRUE;
        }
        if (savedState.actionBarSupportBlur) {
            setSupportBlur(true);
        }
        if (savedState.actionBarEnableBlur) {
            setEnableBlur(true);
        }
        if (savedState.actionBarApplyBlur && isEnableBlur()) {
            applyBlur(true);
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        int i;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Boolean bool = this.mUserApplyBgBlur;
        int i2 = 0;
        if (bool == null) {
            i = -1;
        } else {
            i = bool.booleanValue() ? 1 : 0;
        }
        savedState.userActionBarApplyBlur = i;
        Boolean bool2 = this.mUserApplySplitActionBarBgBlur;
        if (bool2 == null) {
            i2 = -1;
        } else if (bool2.booleanValue()) {
            i2 = 1;
        }
        savedState.userSplitActionBarApplyBlur = i2;
        savedState.actionBarSupportBlur = isSupportBlur();
        savedState.actionBarEnableBlur = isEnableBlur();
        savedState.actionBarApplyBlur = isApplyBlur();
        return savedState;
    }

    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        return (actionBarContextView == null || actionBarContextView.getVisibility() != 0) ? this.mActionBarView.onStartNestedScroll(view, view2, i, i2) : this.mActionBarContextView.onStartNestedScroll(view, view2, i, i2);
    }

    public void onStopNestedScroll(View view, int i) {
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null && actionBarContextView.getVisibility() == 0) {
            this.mActionBarContextView.onStopNestedScroll(view, i);
        } else {
            if (this.mIsSplit || getVisibility() == 8) {
                return;
            }
            this.mActionBarView.onStopNestedScroll(view, i);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return !this.mIsSplit && super.onTouchEvent(motionEvent);
    }

    public void recheckOverlayMaskState(boolean z) {
        if (!this.mOverlayMode || this.mIsTemporaryOverlayMode || z == this.mShouldDrawOverlayMask) {
            return;
        }
        this.mShouldDrawOverlayMask = z;
        ValueAnimator valueAnimator = this.mMaskAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mMaskAlpha = z ? 1.0f : 0.0f;
        setActionBarBlurByNestedScrolled(z);
        invalidate();
        dispatchOverlayMaskAndFloatingState(z);
    }

    public void resetActionBarBlurConfigOnReshow() {
        Boolean bool = this.mUserApplyBgBlur;
        if (bool != null) {
            applyBlur(bool.booleanValue());
        } else {
            applyBlur(this.mInternalApplyBgBlur);
        }
    }

    public final void resizeSplitMaxHeight() {
        TypedValue typedValueResolveTypedValue;
        if (this.mIsSplit && (typedValueResolveTypedValue = AttributeResolver.resolveTypedValue(getContext(), R.attr.actionBarSplitMaxPercentageHeight)) != null && typedValueResolveTypedValue.type == 6) {
            float windowHeight = WindowUtils.getWindowHeight(getContext());
            this.mHeightMaxMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) typedValueResolveTypedValue.getFraction(windowHeight, windowHeight), Integer.MIN_VALUE);
        }
    }

    public final void selectDrawable() {
        ActionBarView actionBarView;
        Drawable[] drawableArr;
        char c;
        if (this.mCustomBackground || this.mIsSplit || (actionBarView = this.mActionBarView) == null || this.mBackground == null || (drawableArr = this.mBackgroundArray) == null || drawableArr.length < 3) {
            return;
        }
        if (actionBarView.isTightTitleWithEmbeddedTabs()) {
            int displayOptions = this.mActionBarView.getDisplayOptions();
            c = ((displayOptions & 2) == 0 && (displayOptions & 4) == 0 && (displayOptions & 16) == 0) ? (char) 1 : (char) 2;
        } else {
            c = 0;
        }
        Drawable drawable = this.mBackgroundArray[c];
        if (drawable != null) {
            this.mBackground = drawable;
        }
    }

    public void setActionBarBlur(@Nullable Boolean bool) {
        if (isEnableBlur()) {
            if (bool == null) {
                this.mUserApplyBgBlur = null;
                applyBlur(this.mInternalApplyBgBlur);
                return;
            }
            Boolean bool2 = this.mUserApplyBgBlur;
            if (bool2 == null || bool2.booleanValue() != bool.booleanValue()) {
                this.mUserApplyBgBlur = bool;
                applyBlur(bool.booleanValue());
            }
        }
    }

    public void setActionBarBlurByNestedScrolled(boolean z) {
        this.mInternalApplyBgBlur = z;
        if (this.mUserApplyBgBlur != null) {
            return;
        }
        applyBlur(z);
    }

    public void setActionBarContextView(ActionBarContextView actionBarContextView) {
        this.mActionBarContextView = actionBarContextView;
        if (actionBarContextView != null) {
            actionBarContextView.setActionBarView(this.mActionBarView);
            this.mCurContextBarExpandState = this.mActionBarContextView.getExpandState();
            this.mCurContextBarResizable = this.mActionBarContextView.isResizable();
        }
    }

    public void setActionBarCoordinateListener(ActionBarCoordinateListener actionBarCoordinateListener) {
        this.mCoordinateListener = actionBarCoordinateListener;
    }

    public void setActionButtonFloatingState(int i) {
        if (this.mActionButtonFloatingState == i) {
            return;
        }
        this.mActionButtonFloatingState = i;
        if (i != -1) {
            boolean z = i == 1;
            ActionBarContextView actionBarContextView = this.mActionBarContextView;
            if (actionBarContextView != null && actionBarContextView.getVisibility() == 0) {
                this.mActionBarContextView.updateActionButtonFloatingState(z);
            }
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView != null) {
                actionBarView.updateActionButtonFloatingState(z);
            }
        }
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        super.setAlpha(f);
    }

    public void setBlurApplier(@Nullable BlurApplier blurApplier) {
        if (this.mUserBlurApplier == blurApplier) {
            return;
        }
        this.mUserBlurApplier = blurApplier;
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
        if (miuiBlurUiHelper == null || !miuiBlurUiHelper.isApplyBlur()) {
            return;
        }
        this.mBlurHelper.refreshBlur();
    }

    public void setContentFadeModeActive(boolean z) {
        this.mContentFadeModeActive = z;
        invalidate();
    }

    public void setCoordinatedOffsetYInSearchModeAnimation(int i) {
        this.mCoordinatedOffsetYInSearchModeAnimation = i;
        ActionBarCoordinateListener actionBarCoordinateListener = this.mCoordinateListener;
        if (actionBarCoordinateListener != null) {
            actionBarCoordinateListener.onActionBarResizing(this.mLastToState, this.mLastActionBarResizingProcess, this.mActionBarHeightGap + i, this.mActionBarHeightTotalGap);
        }
    }

    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setEnableBlur(z);
            return;
        }
        if (this.mIsSplit) {
            this.mSplitEnableBlur = z;
            ActionMenuView actionMenuView = this.mResidentActionMenuView;
            if (actionMenuView != null) {
                actionMenuView.setSupportBlur(true);
                this.mResidentActionMenuView.setEnableBlur(z);
                this.mResidentActionMenuView.applyBlur(z);
            }
        }
    }

    public void setExtraPaddingTop(float f) {
        if (this.mExtraPaddingTop != f) {
            this.mExtraPaddingTop = f;
            if (this.mIsSplit) {
                return;
            }
            applyInsetsTopByMargin(this.mActionBarView);
            applyInsetsTopByMargin(this.mActionBarContextView);
            requestLayout();
            this.mGradientNeedsUpdate = true;
            invalidate();
            MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
            if (miuiBlurUiHelper == null || !miuiBlurUiHelper.isApplyBlur()) {
                return;
            }
            removeCallbacks(this.mRefreshBlurRunnable);
            post(this.mRefreshBlurRunnable);
        }
    }

    public void setIsMiuixFloating(boolean z) {
        this.mIsMiuixFloating = z;
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            if (z) {
                this.mCurBarExpandState = actionBarView.getExpandState();
                this.mCurBarResizable = this.mActionBarView.isResizable();
                this.mActionBarView.setExpandState(0);
                this.mActionBarView.setResizable(false);
            } else {
                actionBarView.setResizable(this.mCurBarResizable);
                this.mActionBarView.setExpandState(0);
            }
        }
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null) {
            if (!z) {
                actionBarContextView.setResizable(this.mCurContextBarResizable);
                this.mActionBarContextView.setExpandState(0);
            } else {
                this.mCurContextBarExpandState = actionBarContextView.getExpandState();
                this.mCurContextBarResizable = this.mActionBarContextView.isResizable();
                this.mActionBarContextView.setExpandState(0);
                this.mActionBarContextView.setResizable(false);
            }
        }
    }

    public void setMaskColor(int i) {
        if (this.mMaskColor != i) {
            this.mMaskColor = i;
            this.mGradientNeedsUpdate = true;
            invalidate();
        }
    }

    public void setMaskExtraHeight(float f) {
        if (this.mMaskExtraHeight != f) {
            this.mMaskExtraHeight = f;
            this.mGradientNeedsUpdate = true;
            invalidate();
        }
    }

    @Override // miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialConfig materialConfig) {
        super.setMaterial(materialConfig);
    }

    @Override // miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        super.setMaterial(materialDayNightConfig);
    }

    public void setMiuixFloatingOnInit(boolean z) {
        this.mIsMiuixFloating = z;
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null && z) {
            this.mCurBarResizable = actionBarView.isResizable();
            this.mActionBarView.setExpandState(0);
            this.mActionBarView.setResizable(false);
            this.mCurBarExpandState = this.mActionBarView.getExpandState();
        }
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView == null || !z) {
            return;
        }
        this.mCurContextBarResizable = actionBarContextView.isResizable();
        this.mActionBarContextView.setExpandState(0);
        this.mActionBarContextView.setResizable(false);
        this.mCurContextBarExpandState = this.mActionBarContextView.getExpandState();
    }

    public void setOverlayMaskConfig(@Nullable OverlayMaskConfig overlayMaskConfig) {
        this.mOverlayMaskConfig = overlayMaskConfig;
        if (overlayMaskConfig != null) {
            this.mMaskColor = overlayMaskConfig.getMaskColor();
        } else {
            this.mMaskColor = this.mDefaultMaskColor;
        }
        this.mGradientNeedsUpdate = true;
        invalidate();
    }

    public void setOverlayMode(boolean z) {
        this.mOverlayMode = z;
        updateParentClipChildren();
        updateWillNotDraw();
    }

    public void setPendingInsets(Rect rect) {
        if (this.mIsSplit) {
            return;
        }
        if (this.mPendingInsets == null) {
            this.mPendingInsets = new Rect();
        }
        if (Objects.equals(this.mPendingInsets, rect)) {
            return;
        }
        this.mPendingInsets.set(rect);
        applyInsetsTopByMargin(this.mActionBarView);
        applyInsetsTopByMargin(this.mActionBarContextView);
    }

    public void setPostExpandRecheckScheduler(Runnable runnable) {
        this.mPostExpandRecheckScheduler = runnable;
    }

    public void setPrimaryBackground(Drawable drawable) {
        Drawable drawable2 = this.mBackground;
        Rect rect = null;
        if (drawable2 != null) {
            Rect bounds = drawable2.getBounds();
            this.mBackground.setCallback(null);
            unscheduleDrawable(this.mBackground);
            rect = bounds;
        }
        this.mBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (rect == null) {
                requestLayout();
            } else {
                this.mBackground.setBounds(rect);
            }
            this.mCustomBackground = true;
        } else {
            this.mCustomBackground = false;
        }
        updateWillNotDraw();
        invalidate();
    }

    public void setSplitActionBarBlur(@Nullable Boolean bool) {
        if (this.mIsSplit) {
            this.mUserApplySplitActionBarBgBlur = bool;
            ActionMenuView actionMenuView = this.mActionModeMenuView;
            if (actionMenuView != null) {
                actionMenuView.applyBlur(bool != null ? bool.booleanValue() : this.mInternalApplySpiltBgBlur);
            }
            ActionMenuView actionMenuView2 = this.mResidentActionMenuView;
            if (actionMenuView2 != null) {
                actionMenuView2.applyBlur(bool != null ? bool.booleanValue() : this.mInternalApplySpiltBgBlur);
            }
        }
    }

    public void setSplitBackground(Drawable drawable) {
        Drawable drawable2 = this.mSplitBackground;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mSplitBackground);
        }
        this.mSplitBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        updateWillNotDraw();
        invalidate();
    }

    public void setStackedBackground(Drawable drawable) {
        Drawable drawable2 = this.mStackedBackground;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mStackedBackground);
        }
        this.mStackedBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        updateWillNotDraw();
        View view = this.mTabContainer;
        if (view != null) {
            view.setBackground(this.mStackedBackground);
        }
    }

    public void setStickyView(@Nullable View view) {
        View view2 = this.mStickyView;
        if (view2 == view) {
            return;
        }
        if (view2 != null) {
            removeView(view2);
            this.mStickyView.setTranslationY(0.0f);
            this.mStickyView.setAlpha(1.0f);
        }
        this.mStickyView = view;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new FrameLayout.LayoutParams(-1, -2);
            } else {
                layoutParams.width = -1;
                layoutParams.height = -2;
            }
            addView(view, layoutParams);
            applyStickyCollapseRatio(this.mLastActionBarResizingProcess);
        }
        requestLayout();
    }

    public void setStickyViewCollapsible(boolean z) {
        View view;
        if (this.mStickyViewCollapsible == z) {
            return;
        }
        this.mStickyViewCollapsible = z;
        if (z || (view = this.mStickyView) == null) {
            applyStickyCollapseRatio(this.mLastActionBarResizingProcess);
        } else {
            view.setAlpha(1.0f);
            this.mStickyView.setTranslationY(0.0f);
        }
    }

    public void setSupportBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setSupportBlur(z);
        }
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        View view = this.mTabContainer;
        if (view != null) {
            removeView(view);
        }
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView);
            ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
            this.mTabContainerPaddingTop = scrollingTabContainerView.getPaddingTop();
        } else {
            View view2 = this.mTabContainer;
            if (view2 != null) {
                view2.setBackground(null);
            }
        }
        this.mTabContainer = scrollingTabContainerView;
    }

    public void setTemporaryOverlayMode(boolean z) {
        this.mIsTemporaryOverlayMode = z;
    }

    public void setTransitioning(boolean z) {
        this.mIsTransitioning = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
        Drawable drawable2 = this.mStackedBackground;
        if (drawable2 != null) {
            drawable2.setVisible(z, false);
        }
        Drawable drawable3 = this.mSplitBackground;
        if (drawable3 != null) {
            drawable3.setVisible(z, false);
        }
    }

    public void show(boolean z) {
        if (this.mNowShowing) {
            return;
        }
        this.mNowShowing = true;
        Folme.useAt(this).state().cancel();
        setVisibility(0);
        if (!z) {
            setTranslationY(0.0f);
            setAlpha(1.0f);
            return;
        }
        if (this.mIsSplit) {
            setTranslationY(TypedValue.applyDimension(1, 15.0f, getContext().getResources().getDisplayMetrics()));
            setAlpha(0.0f);
            if (this.mShowAnimConfig == null) {
                this.mShowAnimConfig = new AnimConfig().setEase(-2, 0.82f, 0.3f).setSpecial(ViewProperty.ALPHA, FolmeEase.linear(150L), new float[0]).setSpecial(AbsActionBarView.SELF_BLUR, FolmeEase.linear(150L), new float[0]);
                this.mShowAnimConfig.addListeners(new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContainer.6
                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        super.onComplete(obj);
                    }
                });
            }
            if (!DeviceHelper.isFeatureWholeAnim()) {
                this.mShowAnimConfig.setEase(-2, 0.82f, 0.3f).setSpecial(ViewProperty.ALPHA, FolmeEase.linear(0L), new float[0]).setSpecial(AbsActionBarView.SELF_BLUR, FolmeEase.linear(0L), new float[0]);
            }
            Folme.useAt(this).state().to(new AnimState("show_end").add(ViewProperty.TRANSLATION_Y, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(ViewProperty.ALPHA, 1.0d).add(AbsActionBarView.SELF_BLUR, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), this.mShowAnimConfig);
            ActionMenuView actionMenuView = this.mResidentActionMenuView;
            if (actionMenuView != null) {
                actionMenuView.startLayoutAnimation();
            }
        }
    }

    public void startActionMode() {
        ActionBarContextView actionBarContextView;
        this.mIsInActionMode = true;
        if (this.mOverlayMode && !this.mIsTemporaryOverlayMode && this.mShouldDrawOverlayMask && this.mActionButtonFloatingState == -1 && (actionBarContextView = this.mActionBarContextView) != null) {
            actionBarContextView.updateActionButtonFloatingState(true);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    public void updateBackground(boolean z) {
        this.mUserBgViewApplyBlur = Boolean.valueOf(z);
        updateBackgroundInternal(z);
    }

    public void updateBackgroundInternal(boolean z) {
        if (z) {
            this.mDrawBackground = false;
        } else {
            this.mDrawBackground = true;
        }
        ActionBarContextView actionBarContextView = this.mActionBarContextView;
        if (actionBarContextView != null) {
            actionBarContextView.updateBackground(z);
        }
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.setApplyBgBlur(z);
        }
        invalidate();
    }

    public final void updateParentClipChildren() {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(!this.mOverlayMode);
        }
    }

    public final void updateWillNotDraw() {
        boolean z = false;
        if (this.mIsSplit) {
            setWillNotDraw(this.mSplitBackground == null);
            return;
        }
        if (this.mBackground == null && this.mStackedBackground == null && !this.mOverlayMode) {
            z = true;
        }
        setWillNotDraw(z);
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        if (drawable == this.mBackground && !this.mIsSplit) {
            return true;
        }
        if (drawable == this.mStackedBackground && this.mIsStacked) {
            return true;
        }
        return (drawable == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(drawable);
    }
}

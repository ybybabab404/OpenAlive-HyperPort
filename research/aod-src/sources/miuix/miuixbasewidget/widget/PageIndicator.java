package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.internal.util.ViewUtils;
import miuix.miuixbasewidget.R;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.theme.token.ShadowToken;

/* JADX INFO: loaded from: classes2.dex */
public class PageIndicator extends View {
    public static final MaterialToken Page_Indicator_Glass_Dark;
    public static final MaterialDayNightToken Page_Indicator_Glass_DayNight;
    public static final MaterialToken Page_Indicator_Glass_Light;
    public ViewProperty mBackgroundDrawableAlpha;
    public int mCurrentPosition;
    public float mCurrentPositionOffset;
    public final ArgbEvaluator mEvaluator;
    public int mHorizontalPadding;
    public int mIndicatorCount;
    public float mIndicatorGap;
    public float mIndicatorInterval;
    public Paint mIndicatorPaint;
    public float mIndicatorRadius;
    public boolean mIsRtl;
    public float mLargeSizeGap;
    public int mLargeSizeHorizontalPadding;
    public float mLargeSizeRadius;
    public float mLargeSizeVerticalPadding;
    public boolean mMaterialEnabled;
    public boolean mNeedBackground;
    public OnPageChangeListener mOnPageChangeListener;
    public boolean mPageScrolling;
    public int mSelectedColor;
    public int mSize;
    public float mSmallSizeGap;
    public int mSmallSizeHorizontalPadding;
    public float mSmallSizeRadius;
    public float mSmallSizeVerticalPadding;
    public int mUnselectedColor;
    public float mVerticalPadding;

    public interface OnPageChangeListener {
        void onPageSelected(int i);
    }

    public static class Size {
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(30, "page-indicator-glass", "light").setMaskBlur(20).setColorBlend(ColorBlendToken.Colored_Regular_Light).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Page_Indicator_Glass_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(30, "page-indicator-glass", "dark").setMaskBlur(20).setColorBlend(ColorBlendToken.Colored_Regular_Dark).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Page_Indicator_Glass_Dark = materialTokenBuild2;
        Page_Indicator_Glass_DayNight = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
    }

    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Widget_PageIndicator_DayNight);
    }

    public PageIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEvaluator = ArgbEvaluator.getInstance();
        this.mPageScrolling = false;
        this.mBackgroundDrawableAlpha = new ViewProperty("backgroundDrawableAlpha", 1.0f) { // from class: miuix.miuixbasewidget.widget.PageIndicator.1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                Drawable background = view.getBackground();
                if (background != null) {
                    return background.getAlpha();
                }
                return 0.0f;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f) {
                Drawable background;
                if (f < 0.0f || f > 255.0f || (background = view.getBackground()) == null) {
                    return;
                }
                background.setAlpha((int) f);
            }
        };
        this.mSmallSizeHorizontalPadding = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_page_indicator_small_size_horizontal_padding);
        this.mLargeSizeHorizontalPadding = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_page_indicator_large_size_horizontal_padding);
        this.mSmallSizeVerticalPadding = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_page_indicator_small_size__vertical_padding);
        this.mLargeSizeVerticalPadding = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_page_indicator_large_size__vertical_padding);
        this.mIndicatorPaint = createIndicatorPaint();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PageIndicator, i, i2);
        this.mIndicatorCount = typedArrayObtainStyledAttributes.getInt(R.styleable.PageIndicator_totalCount, 0);
        this.mNeedBackground = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PageIndicator_needBackground, false);
        this.mSelectedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PageIndicator_selectedColor, 0);
        this.mUnselectedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PageIndicator_unselectedColor, 0);
        this.mSize = typedArrayObtainStyledAttributes.getInt(R.styleable.PageIndicator_sizeLevel, 0);
        this.mSmallSizeRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.PageIndicator_smallSizeRadius, 0.0f);
        this.mLargeSizeRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.PageIndicator_largeSizeRadius, 0.0f);
        this.mSmallSizeGap = typedArrayObtainStyledAttributes.getDimension(R.styleable.PageIndicator_smallSizeGap, 0.0f);
        this.mLargeSizeGap = typedArrayObtainStyledAttributes.getDimension(R.styleable.PageIndicator_largeSizeGap, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        setSize(this.mSize);
        if (getBackground() == null || this.mNeedBackground) {
            return;
        }
        getBackground().setAlpha(0);
    }

    public final void applyMaterialEffects() {
        int i;
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(0);
        }
        boolean z = !ViewUtils.isNightMode(getContext());
        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(Page_Indicator_Glass_DayNight);
        MaterialConfig materialConfig = materialDayNightConfigCreate != null ? materialDayNightConfigCreate.get(z) : null;
        if (materialConfig != null) {
            float f = getResources().getDisplayMetrics().density;
            if (materialConfig.getBlurConfig() != null && (i = materialConfig.getBlurConfig().blurRadius) > 0) {
                MiuiBlurUtils.setBackgroundBlur(this, (int) ((i * f) + 0.5f));
            }
            MiuiBlurUtils.setViewBlurMode(this, 1);
            if (materialConfig.getColorBlendConfig() != null) {
                MiuiBlurUtils.setBackgroundBlendConfig(this, materialConfig.getColorBlendConfig().blendColors, materialConfig.getColorBlendConfig().blendModes);
            }
            if (materialConfig.getBloomStrokeConfig() != null) {
                HyperBloomStrokeUtils.setBloomStrokeConfig(this, materialConfig.getBloomStrokeConfig());
            }
            if (materialConfig.getShadowConfig() != null) {
                MiShadowUtils.setShadowConfig(this, materialConfig.getShadowConfig());
            }
        }
    }

    public final void clearMaterialEffects(int i) {
        MiuiBlurUtils.clearBackgroundBlur(this);
        MiuiBlurUtils.setViewBlurMode(this, 0);
        MiuiBlurUtils.clearBackgroundBlendConfig(this);
        HyperBloomStrokeUtils.clearBloomStroke(this);
        MiShadowUtils.clearMiShadow(this);
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(i);
        }
    }

    public final Paint createIndicatorPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        return paint;
    }

    public final void drawIndicator(Canvas canvas, float f, float f2, float f3, int i) {
        this.mIndicatorPaint.setColor(i);
        canvas.drawCircle(f, f2, f3, this.mIndicatorPaint);
    }

    public int getIndicatorCount() {
        return this.mIndicatorCount;
    }

    public final void notifyUpdate() {
        if (this.mSize == 0) {
            this.mIndicatorRadius = this.mSmallSizeRadius;
            this.mVerticalPadding = this.mSmallSizeVerticalPadding;
            this.mHorizontalPadding = this.mSmallSizeHorizontalPadding;
            this.mIndicatorGap = this.mSmallSizeGap;
        } else {
            this.mIndicatorRadius = this.mLargeSizeRadius;
            this.mVerticalPadding = this.mLargeSizeVerticalPadding;
            this.mHorizontalPadding = this.mLargeSizeHorizontalPadding;
            this.mIndicatorGap = this.mLargeSizeGap;
        }
        this.mIndicatorInterval = (this.mIndicatorRadius * 2.0f) + this.mIndicatorGap;
        requestLayout();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int iIntValue;
        int iIntValue2;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        this.mIsRtl = zIsLayoutRtl;
        float f = this.mHorizontalPadding;
        float f2 = this.mIndicatorRadius;
        float f3 = f + f2;
        float f4 = this.mVerticalPadding + f2;
        int i = 0;
        if (!zIsLayoutRtl) {
            float f5 = f3;
            while (i < this.mIndicatorCount) {
                int i2 = this.mCurrentPosition;
                if (i == i2) {
                    iIntValue = ((Integer) this.mEvaluator.evaluate(this.mCurrentPositionOffset, Integer.valueOf(this.mSelectedColor), Integer.valueOf(this.mUnselectedColor))).intValue();
                } else {
                    iIntValue = i == i2 + 1 ? ((Integer) this.mEvaluator.evaluate(this.mCurrentPositionOffset, Integer.valueOf(this.mUnselectedColor), Integer.valueOf(this.mSelectedColor))).intValue() : this.mUnselectedColor;
                }
                drawIndicator(canvas, f5, f4, this.mIndicatorRadius, iIntValue);
                f5 += this.mIndicatorInterval;
                i++;
            }
            return;
        }
        float f6 = f3;
        while (true) {
            int i3 = this.mIndicatorCount;
            if (i >= i3) {
                return;
            }
            int i4 = this.mCurrentPosition;
            if (i == (i3 - i4) - 1) {
                iIntValue2 = ((Integer) this.mEvaluator.evaluate(this.mCurrentPositionOffset, Integer.valueOf(this.mSelectedColor), Integer.valueOf(this.mUnselectedColor))).intValue();
            } else {
                iIntValue2 = i == (i3 - i4) + (-2) ? ((Integer) this.mEvaluator.evaluate(this.mCurrentPositionOffset, Integer.valueOf(this.mUnselectedColor), Integer.valueOf(this.mSelectedColor))).intValue() : this.mUnselectedColor;
            }
            PageIndicator pageIndicator = this;
            pageIndicator.drawIndicator(canvas, f6, f4, this.mIndicatorRadius, iIntValue2);
            f6 += pageIndicator.mIndicatorInterval;
            i++;
            this = pageIndicator;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        float f = (this.mIndicatorCount - 1) * this.mIndicatorInterval;
        float f2 = this.mIndicatorRadius;
        setMeasuredDimension((int) (f + (f2 * 2.0f) + (this.mHorizontalPadding * 2)), (int) ((f2 * 2.0f) + (this.mVerticalPadding * 2.0f)));
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0032  */
    /* JADX WARN: Code duplicated, block: B:16:0x003b  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && !this.mPageScrolling) {
            float x = motionEvent.getX();
            int i = this.mCurrentPosition;
            if (this.mIsRtl) {
                float f = this.mHorizontalPadding;
                int i2 = this.mIndicatorCount;
                float f2 = this.mIndicatorRadius;
                float f3 = f + (((i2 - i) - 1) * ((f2 * 2.0f) + this.mIndicatorGap));
                float f4 = (f2 * 2.0f) + f3;
                if (x < f3 && i < i2 - 1) {
                    i++;
                } else if (x > f4 && i > 0) {
                    i--;
                }
            } else {
                float f5 = this.mHorizontalPadding;
                float f6 = this.mIndicatorRadius;
                float f7 = f5 + (i * ((f6 * 2.0f) + this.mIndicatorGap));
                float f8 = (f6 * 2.0f) + f7;
                if (x < f7 && i > 0) {
                    i--;
                } else if (x > f8 && i < this.mIndicatorCount - 1) {
                    i++;
                }
            }
            OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(i);
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setBackgroundVisible(boolean z) {
        if (getBackground() == null || this.mNeedBackground == z) {
            return;
        }
        this.mNeedBackground = z;
        setBackgroundVisibleInternal(z);
    }

    public void setBackgroundVisibleInternal(boolean z) {
        if (z) {
            Folme.use((View) this).to(this.mBackgroundDrawableAlpha, Float.valueOf(255.0f), new AnimConfig().setEase(FolmeEase.sinOut(300L)));
        } else {
            Folme.use((View) this).to(this.mBackgroundDrawableAlpha, Float.valueOf(1.0f), new AnimConfig().setEase(FolmeEase.sinOut(100L)));
        }
    }

    public void setCurrentPosition(int i) {
        if (this.mCurrentPosition != i) {
            this.mCurrentPosition = i;
            invalidate();
        }
    }

    public void setCurrentPositionOffset(float f) {
        if (this.mCurrentPositionOffset != f) {
            this.mCurrentPositionOffset = f;
            invalidate();
        }
    }

    public void setIndicatorCount(int i) {
        if (i >= 0) {
            this.mIndicatorCount = i;
            requestLayout();
        }
    }

    public void setMaterialEnabled(boolean z) {
        setMaterialEnabled(z, 255);
    }

    public void setMaterialEnabled(boolean z, @IntRange int i) {
        if (!HyperMaterialUtils.isFeatureEnable(getContext()) || this.mMaterialEnabled == z) {
            return;
        }
        if (z) {
            applyMaterialEffects();
        } else {
            clearMaterialEffects(i);
        }
        this.mMaterialEnabled = z;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setSize(int i) {
        if (i == 0 || i == 1) {
            this.mSize = i;
            notifyUpdate();
        }
    }
}

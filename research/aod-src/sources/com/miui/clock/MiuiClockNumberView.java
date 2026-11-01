package com.miui.clock;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.DiffusionType;
import com.miui.clock.module.FontMiSansNormal;
import com.miui.clock.utils.DeviceConfig;
import java.lang.reflect.Method;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MiuiClockNumberView extends View {
    public float leftExtra;
    public float mBoxBothEmptySidesWidth;
    public int mCenterOffsetX;
    public int mCenterOffsetY;
    public Context mContext;
    public DiffusionType mDiffusionType;
    public int mDisplayType;
    public final Matrix mDrawMatrix;
    public BaseFontStyle mFontStyle;
    public float mFullDiffusionTextBoxWidth;
    public boolean mHasUserDefined;
    public int mIndex;
    public float mOriginAngel;
    public int mOriginImageHeight;
    public int mOriginImageWidth;
    public int mOriginLeftEmpty;
    public int mOriginRightEmpty;
    public int mOriginTextWidth;
    public final float[] mPercentages;
    public float mScale;
    public float mScaleRadio;
    public int mTargetCenterX;
    public int mTargetCenterY;
    public int mTextBoxHeight;
    public int mTextBoxWidth;
    public int mTextColor;
    public float mTranslateX;
    public float mTranslateY;
    public float mUserDefineAngel;
    public Drawable mVectorDrawable;
    public float rightExtra;
    public boolean svgAnimation;

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockNumberView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$DiffusionType;

        static {
            int[] iArr = new int[DiffusionType.values().length];
            $SwitchMap$com$miui$clock$module$DiffusionType = iArr;
            try {
                iArr[DiffusionType.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$DiffusionType[DiffusionType.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$DiffusionType[DiffusionType.BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$DiffusionType[DiffusionType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public MiuiClockNumberView(Context context) {
        super(context);
        this.mDiffusionType = DiffusionType.NONE;
        this.mFontStyle = new FontMiSansNormal();
        this.mDrawMatrix = new Matrix();
        this.mPercentages = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.svgAnimation = false;
        this.mScaleRadio = -1.0f;
        this.mHasUserDefined = false;
        init(context);
    }

    public MiuiClockNumberView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDiffusionType = DiffusionType.NONE;
        this.mFontStyle = new FontMiSansNormal();
        this.mDrawMatrix = new Matrix();
        this.mPercentages = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.svgAnimation = false;
        this.mScaleRadio = -1.0f;
        this.mHasUserDefined = false;
        init(context);
    }

    public MiuiClockNumberView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDiffusionType = DiffusionType.NONE;
        this.mFontStyle = new FontMiSansNormal();
        this.mDrawMatrix = new Matrix();
        this.mPercentages = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.svgAnimation = false;
        this.mScaleRadio = -1.0f;
        this.mHasUserDefined = false;
        init(context);
    }

    public static int dp2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    private Drawable getDrawableByUserDefinedResources() {
        int[] userDefinedResources = this.mFontStyle.getUserDefinedResources();
        if (!isUserDefinedResources()) {
            userDefinedResources = this.mFontStyle.getSvgResource();
        }
        return ContextCompat.getDrawable(this.mContext, userDefinedResources[this.mIndex]);
    }

    public void build() {
        Drawable drawableByUserDefinedResources = getDrawableByUserDefinedResources();
        if (!(drawableByUserDefinedResources instanceof AnimatedVectorDrawable) && !(drawableByUserDefinedResources instanceof VectorDrawable)) {
            Log.e("GlassNumberView", "set svgRes is invalid");
            return;
        }
        updateColor(drawableByUserDefinedResources);
        float[] percentages = this.mFontStyle.getPercentages();
        System.arraycopy(percentages, 0, this.mPercentages, 0, percentages.length);
        this.mOriginImageWidth = drawableByUserDefinedResources.getIntrinsicWidth();
        int intrinsicHeight = drawableByUserDefinedResources.getIntrinsicHeight();
        this.mOriginImageHeight = intrinsicHeight;
        this.mVectorDrawable = drawableByUserDefinedResources;
        drawableByUserDefinedResources.setBounds(0, 0, this.mOriginImageWidth, intrinsicHeight);
        this.mOriginLeftEmpty = dp2px(this.mFontStyle.getLeftEmpty()[this.mIndex]);
        int iDp2px = dp2px(this.mFontStyle.getRightEmpty()[this.mIndex]);
        this.mOriginRightEmpty = this.mOriginImageWidth - iDp2px;
        int iDp2px2 = dp2px(this.mFontStyle.getTextBoxWidth());
        this.mTextBoxWidth = iDp2px2;
        float[] fArr = this.mPercentages;
        this.mFullDiffusionTextBoxWidth = (iDp2px2 * (fArr[1] + fArr[2] + fArr[3]) * 2.0f) + (iDp2px2 * (1.0f - ((1.0f - fArr[0]) * 2.0f)));
        this.mTextBoxHeight = this.mOriginImageHeight;
        this.mOriginTextWidth = iDp2px - this.mOriginLeftEmpty;
        Log.i("GlassNumberView", "mZeroTextWidth = " + this.mOriginTextWidth + ", density = " + Resources.getSystem().getDisplayMetrics().density);
        if (isCopperplateStyle()) {
            float f = (this.mFullDiffusionTextBoxWidth - this.mOriginTextWidth) / 2.0f;
            this.leftExtra = f - this.mOriginLeftEmpty;
            this.rightExtra = f - this.mOriginRightEmpty;
            Log.i("GlassNumberView", "isCopperplateStyle leftExtra = " + this.leftExtra + ", rightExtra = " + this.rightExtra + ", mIndex = " + this.mIndex);
        }
        this.mBoxBothEmptySidesWidth = (this.mTextBoxWidth - this.mOriginImageWidth) / 2.0f;
        Log.i("GlassNumberView", "setPercentages, percentages = " + Arrays.toString(this.mPercentages));
        requestLayout();
    }

    public void configureImageMatrix() {
        float fRound;
        int iRound;
        int[] rectSize = getRectSize();
        int i = rectSize[0];
        int i2 = rectSize[1];
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        float fCalculateScale = this.mScaleRadio;
        if (fCalculateScale <= 0.0f) {
            fCalculateScale = (i > measuredWidth || i2 > measuredHeight) ? Math.min(measuredWidth / i, measuredHeight / i2) : 1.0f;
        }
        if (this.mScaleRadio > 0.0f) {
            fCalculateScale *= DeviceConfig.calculateScale(this.mContext);
        }
        int i3 = this.mTargetCenterX;
        if (i3 == 0 && this.mTargetCenterY == 0) {
            fRound = Math.round((measuredWidth - (i * fCalculateScale)) * 0.5f);
            iRound = Math.round((measuredHeight - (i2 * fCalculateScale)) * 0.5f);
        } else {
            fRound = Math.round((i3 + this.mCenterOffsetX) - ((this.mOriginImageWidth * 0.5f) * fCalculateScale));
            iRound = Math.round((this.mTargetCenterY + this.mCenterOffsetY) - ((i2 * 0.5f) * fCalculateScale));
        }
        float f = iRound;
        this.mScale = fCalculateScale;
        this.mTranslateX = fRound;
        this.mTranslateY = f;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        float fDp2px;
        float f;
        float f2;
        float fMin;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        int[] fontRotateValue;
        super.draw(canvas);
        if (this.mVectorDrawable == null) {
            return;
        }
        float f11 = this.mHasUserDefined ? this.mUserDefineAngel : this.mOriginAngel;
        canvas.save();
        canvas.translate(this.mTranslateX, this.mTranslateY);
        float f12 = this.mScale;
        canvas.scale(f12, f12);
        Log.i("GlassNumberView", "mDrawMatrix = " + this.mDrawMatrix + " index = " + this.mIndex);
        float f13 = ((float) this.mOriginImageWidth) / 2.0f;
        float f14 = ((float) this.mOriginImageHeight) / 2.0f;
        if (this.mDiffusionType == DiffusionType.NONE) {
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
            return;
        }
        if (f11 == 0.0f || !this.mFontStyle.enableRotate() || (fontRotateValue = this.mFontStyle.getFontRotateValue(this.mIndex, f11)) == null || fontRotateValue.length < 2) {
            fDp2px = this.mOriginTextWidth;
            f = this.mOriginLeftEmpty;
            int i = this.mOriginRightEmpty;
            f2 = i;
        } else {
            double radians = Math.toRadians(Math.abs(f11));
            float fSin = (float) ((Math.sin(radians) * ((double) this.mOriginImageHeight)) + (Math.cos(radians) * ((double) this.mOriginImageWidth)));
            fDp2px = dp2px(fontRotateValue[0] / 2.75f);
            float fDp2px2 = dp2px(fontRotateValue[1] / 2.75f);
            int i2 = this.mOriginImageWidth;
            f = (fDp2px2 - fDp2px) - ((fSin - i2) / 2.0f);
            f2 = (fSin - ((fSin - i2) / 2.0f)) - fDp2px2;
        }
        int width = getWidth();
        int i3 = this.mTextBoxWidth;
        float[] fArr = this.mPercentages;
        float f15 = i3 * fArr[3];
        float f16 = i3 * fArr[2];
        float f17 = i3 * fArr[1];
        float f18 = fArr[4] * fDp2px;
        float f19 = fArr[5] * fDp2px;
        float f20 = fArr[6] * fDp2px;
        DiffusionType diffusionType = this.mDiffusionType;
        if (diffusionType == DiffusionType.LEFT || diffusionType == DiffusionType.BOTH) {
            if (isCopperplateStyle()) {
                fMin = f15 - this.leftExtra;
                f3 = 0.0f;
            } else {
                float f21 = (this.mFullDiffusionTextBoxWidth / 2.0f) - (this.mOriginImageWidth / 2.0f);
                fMin = Math.min(this.mTranslateX, 0.0f) + f15;
                f3 = f21;
            }
            f4 = f18;
            float f22 = fMin + f16;
            f5 = f17;
            float f23 = f22 + f5;
            if (this.mTargetCenterX != 0) {
                canvas.save();
                canvas.translate(-f3, 0.0f);
            }
            if (this.mDiffusionType != DiffusionType.BOTH) {
                canvas.save();
                canvas.clipRect(f23, 0.0f, Math.max(fDp2px, this.mOriginImageWidth) + f23, this.mOriginImageHeight);
                canvas.translate(f3, 0.0f);
                canvas.rotate(f11, f13, f14);
                this.mVectorDrawable.draw(canvas);
                canvas.restore();
            }
            canvas.save();
            canvas.clipRect(f22, 0.0f, f23, this.mOriginImageHeight);
            canvas.translate((((f23 - f22) - f4) - f) + f22, 0.0f);
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(fMin, 0.0f, f22, this.mOriginImageHeight);
            canvas.translate((((f22 - fMin) - f19) - f) + fMin, 0.0f);
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(fMin - f15, 0.0f, fMin, this.mOriginImageHeight);
            canvas.translate((fMin - f20) - f, 0.0f);
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
            f6 = f3;
            f7 = f23;
        } else {
            f4 = f18;
            f5 = f17;
            f19 = f19;
            f7 = 0.0f;
            f6 = 0.0f;
        }
        DiffusionType diffusionType2 = this.mDiffusionType;
        DiffusionType diffusionType3 = DiffusionType.RIGHT;
        if (diffusionType2 == diffusionType3 || diffusionType2 == DiffusionType.BOTH) {
            int i4 = this.mTargetCenterX;
            if (i4 != 0) {
                f8 = (i4 + (this.mFullDiffusionTextBoxWidth / 2.0f)) - this.mTranslateX;
            } else {
                f8 = isCopperplateStyle() ? this.rightExtra + this.mOriginImageWidth : width;
            }
            float f24 = f8 - f15;
            float f25 = f24 - f16;
            f9 = f25 - f5;
            if (this.mDiffusionType == diffusionType3) {
                canvas.save();
                canvas.clipRect(f9 - Math.max(fDp2px, this.mOriginImageWidth), 0.0f, f9, this.mOriginImageHeight);
                canvas.rotate(f11, f13, f14);
                this.mVectorDrawable.draw(canvas);
                canvas.restore();
            }
            float f26 = ((f9 + f4) + f2) - this.mOriginImageWidth;
            canvas.save();
            canvas.clipRect(f9, 0.0f, f25, this.mOriginImageHeight);
            canvas.translate(f26, 0.0f);
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
            float f27 = ((f25 + f19) + f2) - this.mOriginImageWidth;
            canvas.save();
            canvas.clipRect(f25, 0.0f, f24, this.mOriginImageHeight);
            canvas.translate(f27, 0.0f);
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
            float f28 = ((f20 + f24) + f2) - this.mOriginImageWidth;
            canvas.save();
            canvas.clipRect(f24, 0.0f, f15 + f24, this.mOriginImageHeight);
            canvas.translate(f28, 0.0f);
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
        } else {
            f9 = 0.0f;
        }
        if (this.mDiffusionType == DiffusionType.BOTH) {
            canvas.save();
            f10 = 0.0f;
            canvas.clipRect(f7, 0.0f, f9, this.mOriginImageHeight);
            canvas.translate(f6, 0.0f);
            canvas.rotate(f11, f13, f14);
            this.mVectorDrawable.draw(canvas);
            canvas.restore();
        } else {
            f10 = 0.0f;
        }
        if (f6 != f10 && this.mTargetCenterX != 0) {
            canvas.restore();
        }
        canvas.restore();
    }

    public int getCenterOffsetX() {
        return this.mCenterOffsetX;
    }

    public int getCenterOffsetY() {
        return this.mCenterOffsetY;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getLeftPosition() {
        float f = this.mScaleRadio;
        return f > 0.0f ? (int) ((this.mTargetCenterX / f) - (getRealWidth() / 2)) : this.mTargetCenterX - (getRealWidth() / 2);
    }

    public int getRealHeight() {
        return (int) (this.mOriginImageHeight * DeviceConfig.calculateScale(getContext()));
    }

    public int getRealWidth() {
        return (int) ((this.mOriginImageWidth * DeviceConfig.calculateScale(getContext())) - this.mCenterOffsetX);
    }

    public int[] getRectSize() {
        int i;
        float f;
        int i2;
        int i3 = AnonymousClass1.$SwitchMap$com$miui$clock$module$DiffusionType[this.mDiffusionType.ordinal()];
        if (i3 != 1 && i3 != 2) {
            if (i3 != 3) {
                i = this.mOriginImageHeight;
                i2 = this.mOriginImageWidth;
            } else {
                i = this.mTextBoxHeight;
                if (isCopperplateStyle()) {
                    i2 = this.mOriginImageWidth;
                } else {
                    float f2 = this.mTextBoxWidth;
                    float[] fArr = this.mPercentages;
                    f = f2 * ((1.0f - ((1.0f - fArr[0]) * 2.0f)) + ((fArr[1] + fArr[2] + fArr[3]) * 2.0f));
                }
            }
            return new int[]{i2, i};
        }
        i = this.mTextBoxHeight;
        float f3 = this.mTextBoxWidth;
        float[] fArr2 = this.mPercentages;
        f = (f3 * (((fArr2[0] + fArr2[1]) + fArr2[2]) + fArr2[3])) - this.mBoxBothEmptySidesWidth;
        i2 = (int) f;
        return new int[]{i2, i};
    }

    public int getTargetCenterX() {
        return this.mTargetCenterX;
    }

    public int getTargetCenterY() {
        return this.mTargetCenterY;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public int getTopPosition() {
        float f = this.mScaleRadio;
        return f > 0.0f ? (int) ((this.mTargetCenterY / f) - (this.mOriginImageHeight / 2)) : this.mTargetCenterY - (this.mOriginImageHeight / 2);
    }

    public float getUserDefineAngel() {
        return this.mUserDefineAngel;
    }

    public Drawable getVectorDrawable() {
        return this.mVectorDrawable;
    }

    public final void init(Context context) {
        this.mContext = context;
    }

    public final boolean isCopperplateStyle() {
        return this.mFontStyle.getFontStyle() == BaseFontStyle.Style.Copperplate;
    }

    public boolean isSvgAnimation() {
        return this.svgAnimation;
    }

    public boolean isUserDefinedResources() {
        int[] userDefinedResources = this.mFontStyle.getUserDefinedResources();
        return (userDefinedResources == null || userDefinedResources.length == 0) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x0129 A[PHI: r7
  0x0129: PHI (r7v22 int) = (r7v20 int), (r7v24 int) binds: [B:36:0x0114, B:40:0x0124] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        float f;
        float f2;
        float fMax;
        int i5;
        int iResolveSizeAndState;
        int iResolveSizeAndState2;
        boolean z;
        if (this.mVectorDrawable == null) {
            i3 = 0;
            i4 = 0;
            f = 0.0f;
        } else {
            int i6 = AnonymousClass1.$SwitchMap$com$miui$clock$module$DiffusionType[this.mDiffusionType.ordinal()];
            if (i6 == 1 || i6 == 2) {
                i3 = this.mTextBoxHeight;
                float f3 = this.mTextBoxWidth;
                float[] fArr = this.mPercentages;
                i4 = (int) ((f3 * (((fArr[0] + fArr[1]) + fArr[2]) + fArr[3])) - this.mBoxBothEmptySidesWidth);
            } else if (i6 != 3) {
                i3 = this.mOriginImageHeight;
                i4 = this.mOriginImageWidth;
            } else {
                i3 = this.mTextBoxHeight;
                if (isCopperplateStyle()) {
                    f2 = this.mOriginImageWidth;
                    fMax = Math.max(Math.max(this.leftExtra, 0.0f), Math.max(this.rightExtra, 0.0f)) * 2.0f;
                } else {
                    f2 = this.mFullDiffusionTextBoxWidth;
                    fMax = 0.5f;
                }
                i4 = (int) (f2 + fMax);
            }
            if (i4 <= 0) {
                i4 = 1;
            }
            if (i3 <= 0) {
                i3 = 1;
            }
            f = i4 / i3;
        }
        Log.i("GlassNumberView", "setMeasuredDimension 111 w = " + i4 + ", h = " + i3 + ", index = " + this.mIndex);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        boolean z2 = mode != 1073741824;
        boolean z3 = mode2 != 1073741824;
        if (z2 || z3) {
            int iResolveAdjustedSize = resolveAdjustedSize(i4 + paddingLeft + paddingRight, Integer.MAX_VALUE, i);
            int iResolveAdjustedSize2 = resolveAdjustedSize(i3 + paddingTop + paddingBottom, Integer.MAX_VALUE, i2);
            if (f != 0.0f) {
                i5 = iResolveAdjustedSize2;
                float f4 = (iResolveAdjustedSize2 - paddingTop) - paddingBottom;
                boolean z4 = z2;
                if (Math.abs((((iResolveAdjustedSize - paddingLeft) - paddingRight) / f4) - f) > 1.0E-7d) {
                    if (z4) {
                        int i7 = ((int) (f * f4)) + paddingLeft + paddingRight;
                        if (!z3) {
                            iResolveAdjustedSize = resolveAdjustedSize(i7, Integer.MAX_VALUE, i);
                        }
                        if (i7 <= iResolveAdjustedSize) {
                            iResolveAdjustedSize = i7;
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    if (!z && z3) {
                        int i8 = ((int) (((iResolveAdjustedSize - paddingLeft) - paddingRight) / f)) + paddingTop + paddingBottom;
                        int iResolveAdjustedSize3 = !z4 ? resolveAdjustedSize(i8, Integer.MAX_VALUE, i2) : i5;
                        iResolveSizeAndState2 = i8 <= iResolveAdjustedSize3 ? i8 : iResolveAdjustedSize3;
                        iResolveSizeAndState = iResolveAdjustedSize;
                    }
                }
            } else {
                i5 = iResolveAdjustedSize2;
            }
            iResolveSizeAndState = iResolveAdjustedSize;
            iResolveSizeAndState2 = i5;
        } else {
            int iMax = Math.max(i4 + paddingLeft + paddingRight, getSuggestedMinimumWidth());
            int iMax2 = Math.max(i3 + paddingTop + paddingBottom, getSuggestedMinimumHeight());
            iResolveSizeAndState = View.resolveSizeAndState(iMax, i, 0);
            iResolveSizeAndState2 = View.resolveSizeAndState(iMax2, i2, 0);
        }
        Log.i("GlassNumberView", "setMeasuredDimension w = " + iResolveSizeAndState + ", h = " + iResolveSizeAndState2 + ", index = " + this.mIndex);
        setMeasuredDimension(iResolveSizeAndState, iResolveSizeAndState2);
        configureImageMatrix();
    }

    public final int resolveAdjustedSize(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i3);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(Math.min(i, size), i2);
        }
        if (mode != 0) {
            return mode != 1073741824 ? i : size;
        }
        return Math.min(i, i2);
    }

    public MiuiClockNumberView setAngel(float f) {
        this.mOriginAngel = f;
        return this;
    }

    public MiuiClockNumberView setCenterOffset(int i, int i2) {
        this.mCenterOffsetX = i;
        this.mCenterOffsetY = i2;
        return this;
    }

    public MiuiClockNumberView setCenterPoint(int i, int i2) {
        this.mTargetCenterX = i;
        this.mTargetCenterY = i2;
        return this;
    }

    public MiuiClockNumberView setDiffusionType(DiffusionType diffusionType) {
        return this;
    }

    public MiuiClockNumberView setDisplayType(int i) {
        this.mDisplayType = i;
        return this;
    }

    public MiuiClockNumberView setFontStyle(BaseFontStyle baseFontStyle) {
        this.mFontStyle = baseFontStyle;
        return this;
    }

    public MiuiClockNumberView setIndex(int i) {
        this.mIndex = i;
        return this;
    }

    public MiuiClockNumberView setScaleRadio(float f) {
        this.mScaleRadio = f;
        return this;
    }

    public void setSvgAnimation(boolean z) {
        this.svgAnimation = z;
    }

    public MiuiClockNumberView setTextColor(int i) {
        this.mTextColor = i;
        return this;
    }

    public MiuiClockNumberView updateColor() {
        updateColor(this.mVectorDrawable);
        invalidate();
        return this;
    }

    public final void updateColor(Drawable drawable) {
        Object objInvoke;
        if (drawable == null) {
            return;
        }
        boolean z = drawable instanceof AnimatedVectorDrawable;
        if (!z && !(drawable instanceof VectorDrawable)) {
            Log.e("GlassNumberView", "set svgRes is invalid");
            return;
        }
        if (this.mFontStyle.getFontStyle() != BaseFontStyle.Style.SKPupok || !z) {
            int i = this.mTextColor;
            if (i != 0) {
                drawable.setTint(i);
                return;
            }
            return;
        }
        try {
            VectorDrawable vectorDrawable = (VectorDrawable) AnimatedVectorDrawable.class.getMethod("getVectorDrawable", new Class[0]).invoke(drawable, new Object[0]);
            if (vectorDrawable != null) {
                Method declaredMethod = VectorDrawable.class.getDeclaredMethod("getTargetByName", String.class);
                declaredMethod.setAccessible(true);
                Object objInvoke2 = declaredMethod.invoke(vectorDrawable, "_R_G_L_0_G_D_1_P_0");
                Class cls = Integer.TYPE;
                if (objInvoke2 != null) {
                    Method declaredMethod2 = objInvoke2.getClass().getDeclaredMethod("setStrokeColor", cls);
                    declaredMethod2.setAccessible(true);
                    declaredMethod2.invoke(objInvoke2, Integer.valueOf(this.mTextColor));
                }
                if (ClockStyleInfo.isAODType(this.mDisplayType) || isSvgAnimation()) {
                    if (!ClockStyleInfo.isAODType(this.mDisplayType) || (objInvoke = declaredMethod.invoke(vectorDrawable, "_R_G_L_0_G_D_0_P_0")) == null) {
                        return;
                    }
                    Method declaredMethod3 = objInvoke2.getClass().getDeclaredMethod("setFillColor", cls);
                    declaredMethod3.setAccessible(true);
                    declaredMethod3.invoke(objInvoke, -16777216);
                    return;
                }
                Object objInvoke3 = declaredMethod.invoke(vectorDrawable, "_R_G_L_0_G_D_0_P_0");
                if (objInvoke3 != null) {
                    Method declaredMethod4 = objInvoke2.getClass().getDeclaredMethod("setFillColor", cls);
                    declaredMethod4.setAccessible(true);
                    declaredMethod4.invoke(objInvoke3, Integer.valueOf(this.mTextColor));
                }
            }
        } catch (Exception e) {
            Log.e("GlassNumberView", "AnimatedVectorDrawable reflect fail", e);
        }
    }
}

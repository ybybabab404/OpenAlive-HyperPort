package com.miui.support.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.miui.support.cardview.R;
import java.io.IOException;
import miuix.animation.styles.AlphaBlendingStateEffect;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class CardStateDrawable extends Drawable implements AlphaBlendingStateEffect.AlphaObserver {
    public static final boolean USE_FOLME = !DrawableUtils.isCommonLiteStrategy();
    public float mActivatedAlpha;
    public float[] mAllRadii;
    public int mCardRadiusMode;
    public float mCheckedAlpha;
    public float mFocusedAlpha;
    public int mHeight;
    public float mHoveredAlpha;
    public float mHoveredCheckedAlpha;
    public int mInsetB;
    public int mInsetL;
    public int mInsetR;
    public int mInsetT;
    public float mNormalAlpha;
    public final Paint mPaint;
    public final Path mPath;
    public float mPressedAlpha;
    public int mRadius;
    public final RectF mRect;
    public AlphaBlendingState mState;
    public AlphaBlendingStateEffect mStateEffect;
    public int mTintColor;
    public int mWidth;

    public static class AlphaBlendingState extends Drawable.ConstantState {
        public float mActivatedAlpha;
        public float mCheckedAlpha;
        public float mFocusedAlpha;
        public int mHeight;
        public float mHoveredAlpha;
        public float mHoveredCheckedAlpha;
        public float mNormalAlpha;
        public float mPressedAlpha;
        public int mRadius;
        public int mTintColor;
        public int mWidth;

        public AlphaBlendingState() {
        }

        public AlphaBlendingState(AlphaBlendingState alphaBlendingState) {
            this.mTintColor = alphaBlendingState.mTintColor;
            this.mRadius = alphaBlendingState.mRadius;
            this.mNormalAlpha = alphaBlendingState.mNormalAlpha;
            this.mPressedAlpha = alphaBlendingState.mPressedAlpha;
            this.mHoveredAlpha = alphaBlendingState.mHoveredAlpha;
            this.mFocusedAlpha = alphaBlendingState.mFocusedAlpha;
            this.mActivatedAlpha = alphaBlendingState.mActivatedAlpha;
            this.mCheckedAlpha = alphaBlendingState.mCheckedAlpha;
            this.mHoveredCheckedAlpha = alphaBlendingState.mHoveredCheckedAlpha;
            this.mWidth = alphaBlendingState.mWidth;
            this.mHeight = alphaBlendingState.mHeight;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new CardStateDrawable(new AlphaBlendingState(this), null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new CardStateDrawable(new AlphaBlendingState(this), resources);
        }
    }

    public CardStateDrawable() {
        this.mCardRadiusMode = -1;
        this.mRect = new RectF();
        this.mAllRadii = new float[8];
        this.mPath = new Path();
        this.mPaint = new Paint();
        this.mWidth = -1;
        this.mHeight = -1;
        AlphaBlendingStateEffect alphaBlendingStateEffect = new AlphaBlendingStateEffect(this);
        this.mStateEffect = alphaBlendingStateEffect;
        alphaBlendingStateEffect.setEnableAnim(USE_FOLME);
        this.mState = new AlphaBlendingState();
        initState();
    }

    public CardStateDrawable(AlphaBlendingState alphaBlendingState, Resources resources) {
        this.mCardRadiusMode = -1;
        this.mRect = new RectF();
        this.mAllRadii = new float[8];
        this.mPath = new Path();
        this.mPaint = new Paint();
        this.mWidth = -1;
        this.mHeight = -1;
        AlphaBlendingStateEffect alphaBlendingStateEffect = new AlphaBlendingStateEffect(this);
        this.mStateEffect = alphaBlendingStateEffect;
        alphaBlendingStateEffect.setEnableAnim(USE_FOLME);
        this.mTintColor = alphaBlendingState.mTintColor;
        this.mRadius = alphaBlendingState.mRadius;
        this.mNormalAlpha = alphaBlendingState.mNormalAlpha;
        this.mPressedAlpha = alphaBlendingState.mPressedAlpha;
        this.mHoveredAlpha = alphaBlendingState.mHoveredAlpha;
        this.mFocusedAlpha = alphaBlendingState.mFocusedAlpha;
        this.mActivatedAlpha = alphaBlendingState.mActivatedAlpha;
        this.mCheckedAlpha = alphaBlendingState.mCheckedAlpha;
        this.mHoveredCheckedAlpha = alphaBlendingState.mHoveredCheckedAlpha;
        this.mWidth = alphaBlendingState.mWidth;
        this.mHeight = alphaBlendingState.mHeight;
        this.mState = new AlphaBlendingState();
        updateLocalState();
        init();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mStateEffect.draw(canvas);
        if (isVisible()) {
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, this.mAllRadii, Path.Direction.CW);
            canvas.drawPath(this.mPath, this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray typedArrayObtainStyledAttributes = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.CardStateDrawable, 0, 0) : resources.obtainAttributes(attributeSet, R.styleable.CardStateDrawable);
        this.mTintColor = typedArrayObtainStyledAttributes.getColor(R.styleable.CardStateDrawable_tintColor, -16777216);
        this.mRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CardStateDrawable_tintRadius, 0);
        this.mNormalAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardStateDrawable_normalAlpha, 0.0f);
        this.mPressedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardStateDrawable_pressedAlpha, 0.0f);
        float f = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardStateDrawable_hoveredAlpha, 0.0f);
        this.mHoveredAlpha = f;
        this.mFocusedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardStateDrawable_focusedAlpha, f);
        this.mActivatedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardStateDrawable_activatedAlpha, 0.0f);
        this.mCheckedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardStateDrawable_checkedAlpha, 0.0f);
        this.mHoveredCheckedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardStateDrawable_hoveredCheckedAlpha, 0.0f);
        this.mWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CardStateDrawable_width, -1);
        this.mHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CardStateDrawable_height, -1);
        typedArrayObtainStyledAttributes.recycle();
        init();
        updateLocalState();
    }

    public final void init() {
        this.mPaint.setColor(this.mTintColor);
        AlphaBlendingStateEffect alphaBlendingStateEffect = this.mStateEffect;
        alphaBlendingStateEffect.normalAlpha = this.mNormalAlpha;
        alphaBlendingStateEffect.pressedAlpha = this.mPressedAlpha;
        alphaBlendingStateEffect.hoveredAlpha = this.mHoveredAlpha;
        alphaBlendingStateEffect.focusedAlpha = this.mFocusedAlpha;
        alphaBlendingStateEffect.checkedAlpha = this.mCheckedAlpha;
        alphaBlendingStateEffect.activatedAlpha = this.mActivatedAlpha;
        alphaBlendingStateEffect.hoveredCheckedAlpha = this.mHoveredCheckedAlpha;
        alphaBlendingStateEffect.initStates();
    }

    public void initState() {
        updateLocalState();
        init();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        this.mStateEffect.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        return this;
    }

    @Override // miuix.animation.styles.AlphaBlendingStateEffect.AlphaObserver
    public void onAlphaChanged(float f) {
        this.mPaint.setAlpha((int) (Math.min(Math.max(f, 0.0f), 1.0f) * 255.0f));
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.mRect.set(rect);
        RectF rectF = this.mRect;
        rectF.left += this.mInsetL;
        rectF.top += this.mInsetT;
        rectF.right -= this.mInsetR;
        rectF.bottom -= this.mInsetB;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        return this.mStateEffect.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setInset(int i, int i2, int i3, int i4) {
        this.mInsetL = i;
        this.mInsetT = i2;
        this.mInsetR = i3;
        this.mInsetB = i4;
    }

    public void setRadii(int i, int i2) {
        if (i2 == 3) {
            this.mAllRadii = new float[8];
            return;
        }
        if (i2 == 2) {
            float f = i;
            this.mAllRadii = new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f};
        } else if (i2 == 4) {
            float f2 = i;
            this.mAllRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f2, f2, f2, f2};
        } else {
            float f3 = i;
            this.mAllRadii = new float[]{f3, f3, f3, f3, f3, f3, f3, f3};
        }
    }

    public void setRadius(int i) {
        if (this.mRadius == i) {
            return;
        }
        this.mRadius = i;
        this.mState.mRadius = i;
        this.mAllRadii = new float[]{i, i, i, i, i, i, i, i};
        invalidateSelf();
    }

    public void setRadiusMode(int i, int i2) {
        this.mRadius = i;
        this.mState.mRadius = i;
        this.mCardRadiusMode = i2;
        setRadii(i, i2);
        invalidateSelf();
    }

    public final void updateLocalState() {
        AlphaBlendingState alphaBlendingState = this.mState;
        alphaBlendingState.mTintColor = this.mTintColor;
        int i = this.mRadius;
        alphaBlendingState.mRadius = i;
        alphaBlendingState.mNormalAlpha = this.mNormalAlpha;
        alphaBlendingState.mPressedAlpha = this.mPressedAlpha;
        alphaBlendingState.mHoveredAlpha = this.mHoveredAlpha;
        alphaBlendingState.mFocusedAlpha = this.mFocusedAlpha;
        alphaBlendingState.mActivatedAlpha = this.mActivatedAlpha;
        alphaBlendingState.mCheckedAlpha = this.mCheckedAlpha;
        alphaBlendingState.mHoveredCheckedAlpha = this.mHoveredCheckedAlpha;
        alphaBlendingState.mWidth = this.mWidth;
        alphaBlendingState.mHeight = this.mHeight;
        setRadii(i, this.mCardRadiusMode);
    }
}

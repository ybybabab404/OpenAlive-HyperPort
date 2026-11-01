package miuix.appcompat.app;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import java.io.IOException;
import miuix.animation.styles.AlphaBlendingStateEffect;
import miuix.appcompat.R;
import miuix.internal.util.LiteUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class AlphaBlendingDrawable extends Drawable implements AlphaBlendingStateEffect.AlphaObserver {
    public static final boolean USE_FOLME = !LiteUtils.isCommonLiteStrategy();
    public float mActivatedAlpha;
    public float[] mAllRadii;
    public int mAlpha;
    public float mCheckedAlpha;
    public float mDisabledAlpha;
    public float mFocusedAlpha;
    public float mHoveredAlpha;
    public float mHoveredCheckedAlpha;
    public int mInsetB;
    public int mInsetL;
    public int mInsetR;
    public int mInsetT;
    public int mMinHeight;
    public int mMinWidth;
    public float mNormalAlpha;
    public final Paint mPaint;
    public float mPressedAlpha;
    public int mRadius;
    public final RectF mRect;
    public final AlphaBlendingState mState;
    public final AlphaBlendingStateEffect mStateEffect;
    public int mTintColor;

    public static final class AlphaBlendingState extends Drawable.ConstantState {
        public float mActivatedAlpha;
        public int mAlpha;
        public float mCheckedAlpha;
        public float mDisabledAlpha;
        public float mFocusedAlpha;
        public float mHoveredAlpha;
        public float mHoveredCheckedAlpha;
        public int mMinHeight;
        public int mMinWidth;
        public float mNormalAlpha;
        public float mPressedAlpha;
        public int mRadius;
        public int mTintColor;

        public AlphaBlendingState() {
        }

        public AlphaBlendingState(AlphaBlendingState alphaBlendingState) {
            this.mTintColor = alphaBlendingState.mTintColor;
            this.mAlpha = alphaBlendingState.mAlpha;
            this.mMinWidth = alphaBlendingState.mMinWidth;
            this.mMinHeight = alphaBlendingState.mMinHeight;
            this.mRadius = alphaBlendingState.mRadius;
            this.mNormalAlpha = alphaBlendingState.mNormalAlpha;
            this.mPressedAlpha = alphaBlendingState.mPressedAlpha;
            this.mHoveredAlpha = alphaBlendingState.mHoveredAlpha;
            this.mFocusedAlpha = alphaBlendingState.mFocusedAlpha;
            this.mActivatedAlpha = alphaBlendingState.mActivatedAlpha;
            this.mCheckedAlpha = alphaBlendingState.mCheckedAlpha;
            this.mHoveredCheckedAlpha = alphaBlendingState.mHoveredCheckedAlpha;
            this.mDisabledAlpha = alphaBlendingState.mDisabledAlpha;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new AlphaBlendingDrawable(new AlphaBlendingState(this), null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new AlphaBlendingDrawable(new AlphaBlendingState(this), resources);
        }
    }

    public AlphaBlendingDrawable() {
        this.mRect = new RectF();
        this.mAllRadii = new float[8];
        this.mPaint = new Paint();
        this.mAlpha = 255;
        AlphaBlendingStateEffect alphaBlendingStateEffect = new AlphaBlendingStateEffect(this);
        this.mStateEffect = alphaBlendingStateEffect;
        alphaBlendingStateEffect.setEnableAnim(USE_FOLME);
        this.mState = new AlphaBlendingState();
    }

    public AlphaBlendingDrawable(AlphaBlendingState alphaBlendingState, Resources resources) {
        this.mRect = new RectF();
        this.mAllRadii = new float[8];
        this.mPaint = new Paint();
        this.mAlpha = 255;
        AlphaBlendingStateEffect alphaBlendingStateEffect = new AlphaBlendingStateEffect(this);
        this.mStateEffect = alphaBlendingStateEffect;
        alphaBlendingStateEffect.setEnableAnim(USE_FOLME);
        this.mTintColor = alphaBlendingState.mTintColor;
        this.mMinHeight = alphaBlendingState.mMinHeight;
        this.mRadius = alphaBlendingState.mRadius;
        this.mNormalAlpha = alphaBlendingState.mNormalAlpha;
        this.mPressedAlpha = alphaBlendingState.mPressedAlpha;
        this.mHoveredAlpha = alphaBlendingState.mHoveredAlpha;
        this.mFocusedAlpha = alphaBlendingState.mFocusedAlpha;
        this.mActivatedAlpha = alphaBlendingState.mActivatedAlpha;
        this.mCheckedAlpha = alphaBlendingState.mCheckedAlpha;
        this.mHoveredCheckedAlpha = alphaBlendingState.mHoveredCheckedAlpha;
        this.mDisabledAlpha = alphaBlendingState.mDisabledAlpha;
        this.mState = new AlphaBlendingState();
        updateLocalState();
        init();
    }

    private void init() {
        this.mPaint.setColor(this.mTintColor);
        AlphaBlendingStateEffect alphaBlendingStateEffect = this.mStateEffect;
        alphaBlendingStateEffect.normalAlpha = this.mNormalAlpha;
        alphaBlendingStateEffect.pressedAlpha = this.mPressedAlpha;
        alphaBlendingStateEffect.hoveredAlpha = this.mHoveredAlpha;
        alphaBlendingStateEffect.focusedAlpha = this.mFocusedAlpha;
        alphaBlendingStateEffect.checkedAlpha = this.mCheckedAlpha;
        alphaBlendingStateEffect.activatedAlpha = this.mActivatedAlpha;
        alphaBlendingStateEffect.hoveredCheckedAlpha = this.mHoveredCheckedAlpha;
        alphaBlendingStateEffect.disabledAlpha = this.mDisabledAlpha;
        alphaBlendingStateEffect.initStates();
    }

    private void updateLocalState() {
        AlphaBlendingState alphaBlendingState = this.mState;
        alphaBlendingState.mTintColor = this.mTintColor;
        alphaBlendingState.mAlpha = this.mAlpha;
        alphaBlendingState.mMinWidth = this.mMinWidth;
        alphaBlendingState.mMinHeight = this.mMinHeight;
        alphaBlendingState.mRadius = this.mRadius;
        alphaBlendingState.mNormalAlpha = this.mNormalAlpha;
        alphaBlendingState.mPressedAlpha = this.mPressedAlpha;
        alphaBlendingState.mHoveredAlpha = this.mHoveredAlpha;
        alphaBlendingState.mFocusedAlpha = this.mFocusedAlpha;
        alphaBlendingState.mActivatedAlpha = this.mActivatedAlpha;
        alphaBlendingState.mCheckedAlpha = this.mCheckedAlpha;
        alphaBlendingState.mHoveredCheckedAlpha = this.mHoveredCheckedAlpha;
        alphaBlendingState.mDisabledAlpha = this.mDisabledAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mStateEffect.draw(canvas);
        if (isVisible()) {
            RectF rectF = this.mRect;
            int i = this.mRadius;
            canvas.drawRoundRect(rectF, i, i, this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mState.mMinHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mState.mMinWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray typedArrayObtainStyledAttributes = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.StateTransitionDrawable, 0, 0) : resources.obtainAttributes(attributeSet, R.styleable.StateTransitionDrawable);
        this.mTintColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_miuixDrawableTintColor, -16777216);
        this.mRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StateTransitionDrawable_miuixDrawableTintRadius, 0);
        this.mNormalAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_normalAlpha, 0.0f);
        this.mPressedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_pressedAlpha, 0.0f);
        float f = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_hoveredAlpha, 0.0f);
        this.mHoveredAlpha = f;
        this.mFocusedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_focusedAlpha, f);
        this.mActivatedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_activatedAlpha, 0.0f);
        this.mCheckedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_checkedAlpha, 0.0f);
        this.mHoveredCheckedAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_hoveredCheckedAlpha, 0.0f);
        this.mDisabledAlpha = typedArrayObtainStyledAttributes.getFloat(R.styleable.StateTransitionDrawable_disabledAlpha, 0.0f);
        this.mMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StateTransitionDrawable_android_width, -1);
        this.mMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StateTransitionDrawable_android_height, -1);
        typedArrayObtainStyledAttributes.recycle();
        int i = this.mRadius;
        this.mAllRadii = new float[]{i, i, i, i, i, i, i, i};
        init();
        updateLocalState();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        this.mStateEffect.jumpToCurrentState();
    }

    @Override // miuix.animation.styles.AlphaBlendingStateEffect.AlphaObserver
    public void onAlphaChanged(float f) {
        this.mPaint.setAlpha((int) (((Math.min(Math.max(f, 0.0f), 1.0f) * this.mAlpha) / 255.0f) * 255.0f));
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
        if (this.mAlpha != i) {
            this.mAlpha = i;
            this.mState.mAlpha = i;
            this.mPaint.setAlpha((int) (i * this.mStateEffect.getAlphaF()));
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setRadius(int i) {
        if (this.mRadius == i) {
            return;
        }
        this.mRadius = i;
        this.mState.mRadius = i;
        invalidateSelf();
    }
}

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
import miuix.animation.styles.ColorStateEffect;
import miuix.appcompat.R;
import miuix.internal.util.LiteUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class ColorStateDrawable extends Drawable implements ColorStateEffect.ColorObserver {
    public static final boolean USE_FOLME = !LiteUtils.isCommonLiteStrategy();
    public float[] mAllRadii;
    public int mAlpha;
    public final Paint mPaint;
    public final RectF mRect;
    public final ColorState mState;
    public final ColorStateEffect mStateEffect;

    public static final class ColorState extends Drawable.ConstantState {
        public int mActivatedColor;
        public int mCheckedColor;
        public int mDisabledColor;
        public int mFocusedColor;
        public int mHoveredCheckedColor;
        public int mHoveredColor;
        public int mInsetB;
        public int mInsetL;
        public int mInsetR;
        public int mInsetT;
        public int mIntrinsicHeight;
        public int mIntrinsicWidth;
        public int mNormalColor;
        public int mPressedColor;
        public int mRadius;

        public ColorState() {
        }

        public ColorState(ColorState colorState) {
            this.mRadius = colorState.mRadius;
            this.mIntrinsicWidth = colorState.mIntrinsicWidth;
            this.mIntrinsicHeight = colorState.mIntrinsicHeight;
            this.mInsetL = colorState.mInsetL;
            this.mInsetT = colorState.mInsetT;
            this.mInsetR = colorState.mInsetR;
            this.mInsetB = colorState.mInsetB;
            this.mNormalColor = colorState.mNormalColor;
            this.mPressedColor = colorState.mPressedColor;
            this.mHoveredColor = colorState.mHoveredColor;
            this.mFocusedColor = colorState.mFocusedColor;
            this.mActivatedColor = colorState.mActivatedColor;
            this.mCheckedColor = colorState.mCheckedColor;
            this.mHoveredCheckedColor = colorState.mHoveredCheckedColor;
            this.mDisabledColor = colorState.mDisabledColor;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ColorStateDrawable(new ColorState(this), null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new ColorStateDrawable(new ColorState(this), resources);
        }
    }

    public ColorStateDrawable() {
        this.mRect = new RectF();
        this.mAllRadii = new float[8];
        this.mAlpha = 255;
        this.mPaint = new Paint();
        ColorStateEffect colorStateEffect = new ColorStateEffect(this);
        this.mStateEffect = colorStateEffect;
        colorStateEffect.setEnableAnim(USE_FOLME);
        this.mState = new ColorState();
    }

    public ColorStateDrawable(ColorState colorState, Resources resources) {
        this.mRect = new RectF();
        this.mAllRadii = new float[8];
        this.mAlpha = 255;
        this.mPaint = new Paint();
        ColorStateEffect colorStateEffect = new ColorStateEffect(this);
        this.mStateEffect = colorStateEffect;
        colorStateEffect.setEnableAnim(USE_FOLME);
        this.mState = new ColorState(colorState);
        init();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mStateEffect.draw(canvas);
        if (isVisible()) {
            RectF rectF = this.mRect;
            int i = this.mState.mRadius;
            canvas.drawRoundRect(rectF, i, i, this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mState.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mState.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray typedArrayObtainStyledAttributes = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.StateTransitionDrawable, 0, 0) : resources.obtainAttributes(attributeSet, R.styleable.StateTransitionDrawable);
        this.mState.mRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StateTransitionDrawable_miuixDrawableTintRadius, 0);
        this.mState.mIntrinsicWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StateTransitionDrawable_android_width, -1);
        this.mState.mIntrinsicHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StateTransitionDrawable_android_height, -1);
        this.mState.mNormalColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_normalColor, 0);
        ColorState colorState = this.mState;
        colorState.mPressedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_pressedColor, colorState.mNormalColor);
        ColorState colorState2 = this.mState;
        colorState2.mHoveredColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_hoveredColor, colorState2.mNormalColor);
        ColorState colorState3 = this.mState;
        colorState3.mFocusedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_focusedColor, colorState3.mHoveredColor);
        ColorState colorState4 = this.mState;
        colorState4.mActivatedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_activatedColor, colorState4.mNormalColor);
        ColorState colorState5 = this.mState;
        colorState5.mCheckedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_checkedColor, colorState5.mNormalColor);
        ColorState colorState6 = this.mState;
        colorState6.mHoveredCheckedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_hoveredCheckedColor, colorState6.mNormalColor);
        ColorState colorState7 = this.mState;
        colorState7.mDisabledColor = typedArrayObtainStyledAttributes.getColor(R.styleable.StateTransitionDrawable_disabledColor, colorState7.mNormalColor);
        typedArrayObtainStyledAttributes.recycle();
        int i = this.mState.mRadius;
        this.mAllRadii = new float[]{i, i, i, i, i, i, i, i};
        init();
    }

    public final void init() {
        this.mPaint.setColor(this.mState.mNormalColor);
        ColorStateEffect colorStateEffect = this.mStateEffect;
        ColorState colorState = this.mState;
        colorStateEffect.normalColor = colorState.mNormalColor;
        colorStateEffect.pressedColor = colorState.mPressedColor;
        colorStateEffect.hoveredColor = colorState.mHoveredColor;
        colorStateEffect.focusedColor = colorState.mFocusedColor;
        colorStateEffect.checkedColor = colorState.mCheckedColor;
        colorStateEffect.activatedColor = colorState.mActivatedColor;
        colorStateEffect.hoveredCheckedColor = colorState.mHoveredCheckedColor;
        colorStateEffect.disabledColor = colorState.mDisabledColor;
        colorStateEffect.initStates();
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
    public void onBoundsChange(Rect rect) {
        this.mRect.set(rect);
        RectF rectF = this.mRect;
        float f = rectF.left;
        ColorState colorState = this.mState;
        rectF.left = f + colorState.mInsetL;
        rectF.top += colorState.mInsetT;
        rectF.right -= colorState.mInsetR;
        rectF.bottom -= colorState.mInsetB;
    }

    @Override // miuix.animation.styles.ColorStateEffect.ColorObserver
    public void onColorChanged(int i) {
        this.mPaint.setColor(updateColorWithAlpha(i, this.mAlpha));
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        return this.mStateEffect.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        int iMax = Math.max(0, Math.min(i, 255));
        if (this.mAlpha != iMax) {
            this.mAlpha = iMax;
            this.mPaint.setColor(updateColorWithAlpha(this.mStateEffect.getStateColor(), this.mAlpha));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public final int updateColorWithAlpha(int i, int i2) {
        return ((((i >>> 24) * i2) / 255) << 24) | (i & 16777215);
    }
}

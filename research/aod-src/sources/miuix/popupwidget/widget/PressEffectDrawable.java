package miuix.popupwidget.widget;

import android.R;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import java.io.IOException;
import miuix.animation.Folme;
import miuix.animation.FolmeObject;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.utils.EaseManager;
import miuix.device.DeviceUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class PressEffectDrawable extends Drawable implements FolmeObject {
    public static final AnimConfig ACTIVATE_ENTER_CONFIG;
    public static final AnimConfig ACTIVATE_EXIT_CONFIG;
    public static final AnimConfig HOVER_ENTER_CONFIG;
    public static final AnimConfig HOVER_EXIT_CONFIG;
    public static final AnimConfig PRESS_ENTER_CONFIG;
    public static final AnimConfig PRESS_EXIT_CONFIG;
    public static final boolean USE_FOLME;
    public boolean mActivated;
    public float mActivatedAlpha;
    public AnimState mActivatedState;
    public Folme.ObjectFolmeImpl mFolmeAnimator;
    public boolean mHovered;
    public float mHoveredActivatedAlpha;
    public AnimState mHoveredActivatedState;
    public float mHoveredAlpha;
    public AnimState mHoveredState;
    public int mInsetB;
    public int mInsetL;
    public int mInsetR;
    public int mInsetT;
    public float mNormalAlpha;
    public AnimState mNormalState;
    public boolean mPressed;
    public float mPressedAlpha;
    public AnimState mPressedState;
    public float mRadius;
    public int mTintColor;
    public static final int[] STATE_PRESSED = {R.attr.state_pressed};
    public static final int[] STATE_DRAG_HOVERED = {R.attr.state_drag_hovered};
    public static final int[] STATE_SELECTED = {R.attr.state_selected};
    public static final int[] STATE_HOVERED_ACTIVATED = {R.attr.state_hovered, R.attr.state_activated};
    public static final int[] STATE_HOVERED = {R.attr.state_hovered};
    public static final int[] STATE_ACTIVATED = {R.attr.state_activated};
    public final RectF mRect = new RectF();
    public final Paint mPaint = new Paint();
    public PressEffectState mState = new PressEffectState();

    public static final class PressEffectState extends Drawable.ConstantState {
        public float mActivatedAlpha;
        public float mHoveredActivatedAlpha;
        public float mHoveredAlpha;
        public int mInsetB;
        public int mInsetL;
        public int mInsetR;
        public int mInsetT;
        public float mNormalAlpha;
        public float mPressedAlpha;
        public float mRadius;
        public int mTintColor;

        public PressEffectState() {
        }

        public PressEffectState(PressEffectState pressEffectState) {
            this.mTintColor = pressEffectState.mTintColor;
            this.mRadius = pressEffectState.mRadius;
            this.mInsetL = pressEffectState.mInsetL;
            this.mInsetT = pressEffectState.mInsetT;
            this.mInsetR = pressEffectState.mInsetR;
            this.mInsetB = pressEffectState.mInsetB;
            this.mNormalAlpha = pressEffectState.mNormalAlpha;
            this.mPressedAlpha = pressEffectState.mPressedAlpha;
            this.mHoveredAlpha = pressEffectState.mHoveredAlpha;
            this.mActivatedAlpha = pressEffectState.mActivatedAlpha;
            this.mHoveredActivatedAlpha = pressEffectState.mHoveredActivatedAlpha;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new PressEffectDrawable(new PressEffectState(this), null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new PressEffectDrawable(new PressEffectState(this), resources);
        }
    }

    static {
        boolean z = (DeviceUtils.isMiuiLiteV2() || DeviceUtils.isLiteV1StockPlus() || DeviceUtils.isMiuiMiddle()) ? false : true;
        USE_FOLME = z;
        if (!z) {
            HOVER_ENTER_CONFIG = null;
            HOVER_EXIT_CONFIG = null;
            PRESS_ENTER_CONFIG = null;
            PRESS_EXIT_CONFIG = null;
            ACTIVATE_ENTER_CONFIG = null;
            ACTIVATE_EXIT_CONFIG = null;
            return;
        }
        HOVER_ENTER_CONFIG = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.99f, 0.6f));
        HOVER_EXIT_CONFIG = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.2f));
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.99f, 0.25f));
        PRESS_ENTER_CONFIG = ease;
        AnimConfig ease2 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.99f, 0.35f));
        PRESS_EXIT_CONFIG = ease2;
        ACTIVATE_ENTER_CONFIG = ease;
        ACTIVATE_EXIT_CONFIG = ease2;
    }

    public PressEffectDrawable() {
    }

    public PressEffectDrawable(PressEffectState pressEffectState, Resources resources) {
        this.mTintColor = pressEffectState.mTintColor;
        this.mRadius = pressEffectState.mRadius;
        this.mInsetL = pressEffectState.mInsetL;
        this.mInsetT = pressEffectState.mInsetT;
        this.mInsetR = pressEffectState.mInsetR;
        this.mInsetB = pressEffectState.mInsetB;
        this.mNormalAlpha = pressEffectState.mNormalAlpha;
        this.mPressedAlpha = pressEffectState.mPressedAlpha;
        this.mHoveredAlpha = pressEffectState.mHoveredAlpha;
        this.mActivatedAlpha = pressEffectState.mActivatedAlpha;
        this.mHoveredActivatedAlpha = pressEffectState.mHoveredActivatedAlpha;
        updateLocalState();
        init();
    }

    private boolean toActivatedState() {
        if (this.mPressed) {
            this.mPressed = false;
            this.mHovered = false;
            this.mActivated = true;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mActivatedState, PRESS_EXIT_CONFIG);
            } else {
                setAlphaF(this.mActivatedAlpha);
            }
            return true;
        }
        if (this.mHovered) {
            this.mHovered = false;
            this.mActivated = true;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mActivatedState, HOVER_EXIT_CONFIG);
            } else {
                setAlphaF(this.mActivatedAlpha);
            }
            return true;
        }
        if (this.mActivated) {
            return false;
        }
        this.mActivated = true;
        if (isAnimEnabled()) {
            this.mFolmeAnimator.to(this.mActivatedState, ACTIVATE_ENTER_CONFIG);
        } else {
            setAlphaF(this.mActivatedAlpha);
        }
        return true;
    }

    private boolean toHoveredActivatedState() {
        if (this.mPressed) {
            this.mPressed = false;
            this.mHovered = true;
            this.mActivated = true;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mHoveredActivatedState, PRESS_EXIT_CONFIG);
            } else {
                setAlphaF(this.mHoveredActivatedAlpha);
            }
            return true;
        }
        boolean z = this.mHovered;
        if (z && this.mActivated) {
            return false;
        }
        if (z) {
            this.mActivated = true;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mHoveredActivatedState, ACTIVATE_ENTER_CONFIG);
            } else {
                setAlphaF(this.mHoveredActivatedAlpha);
            }
            return true;
        }
        if (this.mActivated) {
            this.mHovered = true;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mHoveredActivatedState, HOVER_ENTER_CONFIG);
            } else {
                setAlphaF(this.mHoveredActivatedAlpha);
            }
            return true;
        }
        this.mActivated = true;
        this.mHovered = true;
        if (isAnimEnabled()) {
            this.mFolmeAnimator.to(this.mHoveredActivatedState, HOVER_ENTER_CONFIG);
        } else {
            setAlphaF(this.mHoveredActivatedAlpha);
        }
        return true;
    }

    private boolean toHoveredState() {
        if (this.mPressed) {
            this.mPressed = false;
            this.mHovered = true;
            this.mActivated = false;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mHoveredState, PRESS_EXIT_CONFIG);
            } else {
                setAlphaF(this.mHoveredAlpha);
            }
            return true;
        }
        if (this.mHovered) {
            if (!this.mActivated) {
                return false;
            }
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mHoveredState, HOVER_EXIT_CONFIG);
            } else {
                setAlphaF(this.mHoveredAlpha);
            }
            return true;
        }
        this.mHovered = true;
        this.mActivated = false;
        if (isAnimEnabled()) {
            this.mFolmeAnimator.to(this.mHoveredState, HOVER_ENTER_CONFIG);
        } else {
            setAlphaF(this.mHoveredAlpha);
        }
        return true;
    }

    private boolean toNormalState() {
        if (this.mPressed) {
            this.mPressed = false;
            this.mHovered = false;
            this.mActivated = false;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mNormalState, PRESS_EXIT_CONFIG);
            } else {
                setAlphaF(this.mNormalAlpha);
            }
            return true;
        }
        if (this.mHovered) {
            this.mHovered = false;
            this.mActivated = false;
            if (isAnimEnabled()) {
                this.mFolmeAnimator.to(this.mNormalState, HOVER_EXIT_CONFIG);
            } else {
                setAlphaF(this.mNormalAlpha);
            }
            return true;
        }
        if (!this.mActivated) {
            return false;
        }
        this.mActivated = false;
        if (isAnimEnabled()) {
            this.mFolmeAnimator.to(this.mNormalState, ACTIVATE_EXIT_CONFIG);
        } else {
            setAlphaF(this.mNormalAlpha);
        }
        return true;
    }

    private boolean toPressedState() {
        if (this.mPressed) {
            return false;
        }
        if (isAnimEnabled()) {
            this.mFolmeAnimator.to(this.mPressedState, PRESS_ENTER_CONFIG);
        } else {
            setAlphaF(this.mPressedAlpha);
        }
        this.mPressed = true;
        this.mHovered = false;
        this.mActivated = false;
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (USE_FOLME && this.mFolmeAnimator == null) {
            this.mFolmeAnimator = Folme.use((FolmeObject) this);
        }
        if (isVisible()) {
            float f = this.mRadius;
            if (f > 0.0f) {
                canvas.drawRoundRect(this.mRect, f, f, this.mPaint);
            } else {
                canvas.drawRect(this.mRect, this.mPaint);
            }
        }
    }

    @Override // miuix.animation.FolmeObject
    public Folme.ObjectFolmeImpl folme() {
        return null;
    }

    public float getAlphaF() {
        return this.mPaint.getAlpha() / 255.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray typedArrayObtainStyledAttributes = theme != null ? theme.obtainStyledAttributes(attributeSet, miuix.popupwidget.R.styleable.StateTransitionDrawable, 0, 0) : resources.obtainAttributes(attributeSet, miuix.popupwidget.R.styleable.StateTransitionDrawable);
        this.mTintColor = typedArrayObtainStyledAttributes.getColor(miuix.popupwidget.R.styleable.StateTransitionDrawable_miuixDrawableTintColor, -16777216);
        this.mRadius = typedArrayObtainStyledAttributes.getDimension(miuix.popupwidget.R.styleable.StateTransitionDrawable_miuixDrawableTintRadius, 0.0f);
        this.mInsetL = typedArrayObtainStyledAttributes.getDimensionPixelSize(miuix.popupwidget.R.styleable.StateTransitionDrawable_android_insetLeft, 0);
        this.mInsetT = typedArrayObtainStyledAttributes.getDimensionPixelSize(miuix.popupwidget.R.styleable.StateTransitionDrawable_android_insetTop, 0);
        this.mInsetR = typedArrayObtainStyledAttributes.getDimensionPixelSize(miuix.popupwidget.R.styleable.StateTransitionDrawable_android_insetRight, 0);
        this.mInsetB = typedArrayObtainStyledAttributes.getDimensionPixelSize(miuix.popupwidget.R.styleable.StateTransitionDrawable_android_insetBottom, 0);
        this.mNormalAlpha = typedArrayObtainStyledAttributes.getFloat(miuix.popupwidget.R.styleable.StateTransitionDrawable_normalAlpha, 0.0f);
        this.mPressedAlpha = typedArrayObtainStyledAttributes.getFloat(miuix.popupwidget.R.styleable.StateTransitionDrawable_pressedAlpha, 0.0f);
        this.mHoveredAlpha = typedArrayObtainStyledAttributes.getFloat(miuix.popupwidget.R.styleable.StateTransitionDrawable_hoveredAlpha, 0.0f);
        this.mActivatedAlpha = typedArrayObtainStyledAttributes.getFloat(miuix.popupwidget.R.styleable.StateTransitionDrawable_activatedAlpha, 0.0f);
        this.mHoveredActivatedAlpha = typedArrayObtainStyledAttributes.getFloat(miuix.popupwidget.R.styleable.StateTransitionDrawable_hoveredActivatedAlpha, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        init();
        updateLocalState();
    }

    public final void init() {
        this.mPaint.setColor(this.mTintColor);
        this.mPaint.setAlpha(0);
        if (!USE_FOLME) {
            setAlphaF(this.mNormalAlpha);
            return;
        }
        this.mNormalState = new AnimState().add("alphaF", this.mNormalAlpha);
        this.mPressedState = new AnimState().add("alphaF", this.mPressedAlpha);
        this.mHoveredState = new AnimState().add("alphaF", this.mHoveredAlpha);
        this.mActivatedState = new AnimState().add("alphaF", this.mActivatedAlpha);
        this.mHoveredActivatedState = new AnimState().add("alphaF", this.mHoveredActivatedAlpha);
    }

    public boolean isAnimEnabled() {
        return USE_FOLME && this.mFolmeAnimator != null;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        if (isAnimEnabled()) {
            Folme.ObjectFolmeImpl objectFolmeImpl = this.mFolmeAnimator;
            objectFolmeImpl.setTo(objectFolmeImpl.state().getCurrentState());
        }
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
        if (StateSet.stateSetMatches(STATE_PRESSED, iArr) || StateSet.stateSetMatches(STATE_DRAG_HOVERED, iArr) || StateSet.stateSetMatches(STATE_SELECTED, iArr)) {
            return toPressedState();
        }
        if (StateSet.stateSetMatches(STATE_HOVERED_ACTIVATED, iArr)) {
            return toHoveredActivatedState();
        }
        if (StateSet.stateSetMatches(STATE_HOVERED, iArr)) {
            return toHoveredState();
        }
        return StateSet.stateSetMatches(STATE_ACTIVATED, iArr) ? toActivatedState() : toNormalState();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public void setAlphaF(float f) {
        this.mPaint.setAlpha((int) (f * 255.0f));
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // miuix.animation.FolmeObject
    public void setFolmeImpl(Folme.ObjectFolmeImpl objectFolmeImpl) {
    }

    public final void updateLocalState() {
        PressEffectState pressEffectState = this.mState;
        pressEffectState.mTintColor = this.mTintColor;
        pressEffectState.mRadius = this.mRadius;
        pressEffectState.mInsetL = this.mInsetL;
        pressEffectState.mInsetT = this.mInsetT;
        pressEffectState.mInsetR = this.mInsetR;
        pressEffectState.mInsetB = this.mInsetB;
        pressEffectState.mNormalAlpha = this.mNormalAlpha;
        pressEffectState.mPressedAlpha = this.mPressedAlpha;
        pressEffectState.mHoveredAlpha = this.mHoveredAlpha;
        pressEffectState.mActivatedAlpha = this.mActivatedAlpha;
        pressEffectState.mHoveredActivatedAlpha = this.mHoveredActivatedAlpha;
    }
}

package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class MenuItemPressBackgroundView extends View {
    public final AnimConfig mActivatedEnterConfig;
    public final AnimConfig mActivatedExitConfig;
    public float mCurrentAlpha;
    public float mCurrentScaleX;
    public float mCurrentScaleY;
    public final String mFolmeKey;
    public boolean mIsDarkMode;
    public final Paint mPaint;
    public View mParentView;
    public final AnimConfig mPressAnimConfig;
    public final float mPressShrinkPx;
    public float mRadius;
    public State mState;
    public final RectF mTargetRect;

    /* JADX INFO: renamed from: miuix.appcompat.internal.view.menu.action.MenuItemPressBackgroundView$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$miuix$appcompat$internal$view$menu$action$MenuItemPressBackgroundView$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$miuix$appcompat$internal$view$menu$action$MenuItemPressBackgroundView$State = iArr;
            try {
                iArr[State.PRESSED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miuix$appcompat$internal$view$menu$action$MenuItemPressBackgroundView$State[State.HOVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$miuix$appcompat$internal$view$menu$action$MenuItemPressBackgroundView$State[State.ACTIVATED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum State {
        NONE,
        PRESSED,
        HOVER,
        ACTIVATED
    }

    public MenuItemPressBackgroundView(Context context) {
        super(context);
        this.mCurrentAlpha = 0.0f;
        Float fValueOf = Float.valueOf(1.0f);
        this.mCurrentScaleX = 1.0f;
        this.mCurrentScaleY = 1.0f;
        this.mIsDarkMode = false;
        this.mState = State.NONE;
        this.mPaint = new Paint(1);
        this.mTargetRect = new RectF();
        this.mIsDarkMode = (context.getResources().getConfiguration().uiMode & 48) == 32;
        updatePaintColor();
        this.mPressShrinkPx = context.getResources().getDisplayMetrics().density * 3.0f;
        String str = "MenuPressBg_" + System.identityHashCode(this);
        this.mFolmeKey = str;
        TransitionListener transitionListener = new TransitionListener() { // from class: miuix.appcompat.internal.view.menu.action.MenuItemPressBackgroundView.1
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, AnimatedProperty.PROPERTY_NAME_ALPHA);
                if (updateInfoFindByName != null) {
                    MenuItemPressBackgroundView.this.mCurrentAlpha = updateInfoFindByName.getFloatValue();
                }
                UpdateInfo updateInfoFindByName2 = UpdateInfo.findByName(collection, AnimatedProperty.PROPERTY_NAME_SCALE_X);
                if (updateInfoFindByName2 != null) {
                    MenuItemPressBackgroundView.this.mCurrentScaleX = updateInfoFindByName2.getFloatValue();
                }
                UpdateInfo updateInfoFindByName3 = UpdateInfo.findByName(collection, AnimatedProperty.PROPERTY_NAME_SCALE_Y);
                if (updateInfoFindByName3 != null) {
                    MenuItemPressBackgroundView.this.mCurrentScaleY = updateInfoFindByName3.getFloatValue();
                }
                MenuItemPressBackgroundView.this.invalidateParent();
            }
        };
        this.mPressAnimConfig = new AnimConfig().setEase(1, 200.0f).addListeners(transitionListener);
        this.mActivatedEnterConfig = new AnimConfig().setEase(EaseManager.getStyle(-2, 1.0f, 0.2f)).addListeners(transitionListener);
        this.mActivatedExitConfig = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.35f)).addListeners(transitionListener, new TransitionListener() { // from class: miuix.appcompat.internal.view.menu.action.MenuItemPressBackgroundView.2
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                MenuItemPressBackgroundView.this.mState = State.NONE;
            }
        });
        Folme.useValue(str).setTo(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(0.0f), AnimatedProperty.PROPERTY_NAME_SCALE_X, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_Y, fValueOf);
    }

    private int getBaseAlpha() {
        int i = AnonymousClass3.$SwitchMap$miuix$appcompat$internal$view$menu$action$MenuItemPressBackgroundView$State[this.mState.ordinal()];
        if (i == 1) {
            return this.mIsDarkMode ? 36 : 26;
        }
        if (i == 2) {
            return this.mIsDarkMode ? 20 : 10;
        }
        if (i != 3) {
            return this.mIsDarkMode ? 36 : 26;
        }
        return this.mIsDarkMode ? 31 : 15;
    }

    public void drawBackground(Canvas canvas) {
        if (this.mCurrentAlpha <= 0.0f || this.mTargetRect.isEmpty()) {
            return;
        }
        this.mPaint.setAlpha((int) (this.mCurrentAlpha * getBaseAlpha()));
        float fCenterX = this.mTargetRect.centerX();
        float fCenterY = this.mTargetRect.centerY();
        float fWidth = this.mTargetRect.width() * 0.5f * this.mCurrentScaleX;
        float fHeight = this.mTargetRect.height() * 0.5f;
        float f = this.mCurrentScaleY;
        float f2 = fHeight * f;
        float fMin = this.mRadius * Math.min(this.mCurrentScaleX, f);
        canvas.drawRoundRect(fCenterX - fWidth, fCenterY - f2, fCenterX + fWidth, fCenterY + f2, fMin, fMin, this.mPaint);
    }

    public void hide() {
        this.mState = State.NONE;
        IStateStyle iStateStyleUseValue = Folme.useValue(this.mFolmeKey);
        Float fValueOf = Float.valueOf(0.0f);
        Float fValueOf2 = Float.valueOf(1.0f);
        iStateStyleUseValue.to(AnimatedProperty.PROPERTY_NAME_ALPHA, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_X, fValueOf2, AnimatedProperty.PROPERTY_NAME_SCALE_Y, fValueOf2, this.mPressAnimConfig);
    }

    public void hideActivated() {
        IStateStyle iStateStyleUseValue = Folme.useValue(this.mFolmeKey);
        Float fValueOf = Float.valueOf(0.0f);
        Float fValueOf2 = Float.valueOf(1.0f);
        iStateStyleUseValue.to(AnimatedProperty.PROPERTY_NAME_ALPHA, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_X, fValueOf2, AnimatedProperty.PROPERTY_NAME_SCALE_Y, fValueOf2, this.mActivatedExitConfig);
    }

    public void hideHover() {
        this.mState = State.NONE;
        IStateStyle iStateStyleUseValue = Folme.useValue(this.mFolmeKey);
        Float fValueOf = Float.valueOf(0.0f);
        Float fValueOf2 = Float.valueOf(1.0f);
        iStateStyleUseValue.to(AnimatedProperty.PROPERTY_NAME_ALPHA, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_X, fValueOf2, AnimatedProperty.PROPERTY_NAME_SCALE_Y, fValueOf2, this.mPressAnimConfig);
    }

    public final void invalidateParent() {
        View view = this.mParentView;
        if (view != null) {
            view.invalidate();
        }
    }

    public void setParentView(View view) {
        this.mParentView = view;
    }

    public void showActivatedAt(float f, float f2, float f3, float f4, float f5) {
        this.mTargetRect.set(f, f2, f3, f4);
        this.mRadius = f5;
        this.mState = State.ACTIVATED;
        IStateStyle iStateStyleUseValue = Folme.useValue(this.mFolmeKey);
        Float fValueOf = Float.valueOf(1.0f);
        iStateStyleUseValue.to(AnimatedProperty.PROPERTY_NAME_ALPHA, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_X, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_Y, fValueOf, this.mActivatedEnterConfig);
    }

    public void showAt(float f, float f2, float f3, float f4, float f5) {
        this.mTargetRect.set(f, f2, f3, f4);
        this.mRadius = f5;
        this.mState = State.PRESSED;
        float f6 = f3 - f;
        float f7 = f4 - f2;
        Folme.useValue(this.mFolmeKey).to(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(1.0f), AnimatedProperty.PROPERTY_NAME_SCALE_X, Float.valueOf(f6 > 0.0f ? 1.0f - ((this.mPressShrinkPx * 2.0f) / f6) : 1.0f), AnimatedProperty.PROPERTY_NAME_SCALE_Y, Float.valueOf(f7 > 0.0f ? 1.0f - ((this.mPressShrinkPx * 2.0f) / f7) : 1.0f), this.mPressAnimConfig);
    }

    public void showHoverAt(float f, float f2, float f3, float f4, float f5) {
        this.mTargetRect.set(f, f2, f3, f4);
        this.mRadius = f5;
        this.mState = State.HOVER;
        IStateStyle iStateStyleUseValue = Folme.useValue(this.mFolmeKey);
        Float fValueOf = Float.valueOf(1.0f);
        iStateStyleUseValue.to(AnimatedProperty.PROPERTY_NAME_ALPHA, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_X, fValueOf, AnimatedProperty.PROPERTY_NAME_SCALE_Y, fValueOf, this.mPressAnimConfig);
    }

    public void updateDarkMode(boolean z) {
        if (this.mIsDarkMode != z) {
            this.mIsDarkMode = z;
            updatePaintColor();
        }
    }

    public final void updatePaintColor() {
        this.mPaint.setColor(this.mIsDarkMode ? -1 : -16777216);
    }
}

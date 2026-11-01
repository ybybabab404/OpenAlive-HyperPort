package miuix.animation.styles;

import androidx.annotation.Keep;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ColorProperty;
import miuix.device.DeviceUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ColorStateEffect extends DrawableStateEffect {
    private static final AnimConfig ACTIVATE_ENTER_CONFIG;
    private static final AnimConfig ACTIVATE_EXIT_CONFIG;
    private static final AnimConfig DISABLE_ENTER_CONFIG;
    private static final AnimConfig DISABLE_EXIT_CONFIG;
    private static final AnimConfig HOVER_ENTER_CONFIG;
    private static final AnimConfig HOVER_EXIT_CONFIG;
    private static final AnimConfig NORMAL_ENTER_CONFIG;
    private static final AnimConfig NORMAL_EXIT_CONFIG;
    private static final AnimConfig PRESS_ENTER_CONFIG;
    private static final AnimConfig PRESS_EXIT_CONFIG;
    private static final ColorProperty<ColorStateEffect> STATE_COLOR = new ColorProperty<ColorStateEffect>("stateColor") { // from class: miuix.animation.styles.ColorStateEffect.1
        @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
        public int getIntValue(ColorStateEffect colorStateEffect) {
            return colorStateEffect.getStateColor();
        }

        @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
        public void setIntValue(ColorStateEffect colorStateEffect, int i) {
            colorStateEffect.setStateColor(i);
        }
    };
    public int activatedColor;
    public int checkedColor;
    public int disabledColor;
    public int focusCheckedColor;
    public int focusedColor;
    public int hoveredCheckedColor;
    public int hoveredColor;
    private int mColor;
    private final ColorObserver mColorObserver;
    public int normalColor;
    public int pressedColor;

    public interface ColorObserver {
        void onColorChanged(int i);
    }

    static {
        if (DeviceUtils.isMiuiLiteV2() || DeviceUtils.isLiteV1StockPlus() || DeviceUtils.isMiuiMiddle()) {
            NORMAL_ENTER_CONFIG = null;
            NORMAL_EXIT_CONFIG = null;
            DISABLE_ENTER_CONFIG = null;
            DISABLE_EXIT_CONFIG = null;
            HOVER_ENTER_CONFIG = null;
            HOVER_EXIT_CONFIG = null;
            PRESS_ENTER_CONFIG = null;
            PRESS_EXIT_CONFIG = null;
            ACTIVATE_ENTER_CONFIG = null;
            ACTIVATE_EXIT_CONFIG = null;
            return;
        }
        NORMAL_ENTER_CONFIG = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.35f));
        NORMAL_EXIT_CONFIG = new AnimConfig().setEase(FolmeEase.sineOut(350L));
        DISABLE_ENTER_CONFIG = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.35f));
        DISABLE_EXIT_CONFIG = new AnimConfig().setEase(FolmeEase.sineOut(350L));
        HOVER_ENTER_CONFIG = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.6f));
        HOVER_EXIT_CONFIG = new AnimConfig().setEase(FolmeEase.spring(0.9f, 0.2f));
        AnimConfig ease = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.2f));
        PRESS_ENTER_CONFIG = ease;
        AnimConfig ease2 = new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.35f));
        PRESS_EXIT_CONFIG = ease2;
        ACTIVATE_ENTER_CONFIG = ease;
        ACTIVATE_EXIT_CONFIG = ease2;
    }

    public ColorStateEffect(ColorObserver colorObserver) {
        this.mColorObserver = colorObserver;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getActivateEnterConfig() {
        return ACTIVATE_ENTER_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getActivateExitConfig() {
        return ACTIVATE_EXIT_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getDisableEnterConfig() {
        return DISABLE_ENTER_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getDisableExitConfig() {
        return DISABLE_EXIT_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getHoverEnterConfig() {
        return HOVER_ENTER_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getHoverExitConfig() {
        return HOVER_EXIT_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getNormalEnterConfig() {
        return NORMAL_ENTER_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getNormalExitConfig() {
        return NORMAL_EXIT_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getPressEnterConfig() {
        return PRESS_ENTER_CONFIG;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public AnimConfig getPressExitConfig() {
        return PRESS_EXIT_CONFIG;
    }

    @Keep
    public int getStateColor() {
        return this.mColor;
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void initStates() {
        if (!this.mEnableAnim) {
            setToNormal();
            return;
        }
        AnimState animState = new AnimState();
        ColorProperty<ColorStateEffect> colorProperty = STATE_COLOR;
        this.mNormalState = animState.add(colorProperty, this.normalColor);
        this.mPressedState = new AnimState().add(colorProperty, this.pressedColor);
        this.mHoveredState = new AnimState().add(colorProperty, this.hoveredColor);
        this.mFocusedState = new AnimState().add(colorProperty, this.focusedColor);
        this.mActivatedState = new AnimState().add(colorProperty, this.activatedColor);
        this.mActivatedPressedState = new AnimState().add(colorProperty, this.pressedColor);
        this.mHoveredActivatedState = new AnimState().add(colorProperty, this.hoveredColor);
        this.mHoveredPressedState = new AnimState().add(colorProperty, this.hoveredColor);
        this.mCheckedState = new AnimState().add(colorProperty, this.checkedColor);
        this.mHoveredCheckedState = new AnimState().add(colorProperty, this.hoveredCheckedColor);
        this.mDisabledState = new AnimState().add(colorProperty, this.disabledColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void jumpToCurrentState() {
        if (!isAnimEnabled()) {
            setStateColor(this.mColor);
        } else {
            Folme.ObjectFolmeImpl objectFolmeImpl = this.mFolmeAnimator;
            objectFolmeImpl.setTo(objectFolmeImpl.state().getCurrentState());
        }
    }

    @Keep
    public void setStateColor(int i) {
        this.mColor = i;
        this.mColorObserver.onColorChanged(i);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToActivated() {
        setStateColor(this.activatedColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToActivatedPressed() {
        setStateColor(this.activatedColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToChecked() {
        setStateColor(this.checkedColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToCheckedPressed() {
        setStateColor(this.checkedColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToDisable() {
        setStateColor(this.disabledColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToFocused() {
        setStateColor(this.focusedColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToFocusedPressed() {
        setStateColor(this.focusedColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToHovered() {
        setStateColor(this.hoveredColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToHoveredActivated() {
        setStateColor(this.hoveredColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToHoveredChecked() {
        setStateColor(this.hoveredColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToHoveredPressed() {
        setStateColor(this.hoveredColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToNormal() {
        setStateColor(this.normalColor);
    }

    @Override // miuix.animation.styles.DrawableStateEffect
    public void setToPressed() {
        setStateColor(this.pressedColor);
    }
}

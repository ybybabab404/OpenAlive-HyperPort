package miuix.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ColorProperty;
import miuix.appcompat.R;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.graphics.shadow.DropShadowConfig;
import miuix.graphics.shadow.DropShadowHelper;
import miuix.internal.util.LiteUtils;
import miuix.smooth.SmoothContainerDrawable2;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.view.BlurableWidget;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class Button extends AppCompatButton implements AnimatedTextView, BlurableWidget {
    public static final ColorProperty TEXT_COLOR_PROPERTY = new ColorProperty<Button>("btnTextColorInAnim") { // from class: miuix.appcompat.widget.Button.1
        @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
        public int getIntValue(Button button) {
            return button.getCurrentTextColorInAnim();
        }

        @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
        public void setIntValue(Button button, int i) {
            super.setIntValue(button, i);
            button.setCurrentTextColorInAnim(i);
        }
    };
    public boolean mApplyBlur;
    public ColorDrawable mBgColor;
    public final MiuiBlurUiHelper mBlurUiHelper;
    public MaterialConfig mCurrentMaterial;
    public int mCurrentTextColorInAnim;
    public ColorStateList mCurrentTextColorStateList;
    public IFolme mFolmeAnimator;
    public final Runnable mInitAnimatorTask;
    public final boolean mIsLightStyle;
    public MaterialDayNightConfig mMaterial;
    public DropShadowHelper mShadowHelper;
    public final AnimConfig mTextColorConfig;

    public Button(@NonNull Context context) {
        this(context, null);
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        Button button;
        super(context, attributeSet, i);
        this.mApplyBlur = false;
        this.mTextColorConfig = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.35f)).addListeners(new TransitionListener() { // from class: miuix.appcompat.widget.Button.2
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Button.this.restoreTextColor();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
            }
        });
        this.mInitAnimatorTask = new Runnable() { // from class: miuix.appcompat.widget.Button.3
            @Override // java.lang.Runnable
            public void run() {
                Button.this.mFolmeAnimator = LiteUtils.isCommonLiteStrategy() ? null : Folme.use((View) Button.this);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiuixButton, i, R.style.Widget_Button);
        this.mIsLightStyle = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixButton_isLightTheme, true);
        typedArrayObtainStyledAttributes.recycle();
        if (HyperMaterialUtils.isEnable()) {
            button = this;
            button.mBlurUiHelper = new MiuiBlurUiHelper(context, button, false, false, false, new MiuiBlurUiHelper.BlurStateCallback() { // from class: miuix.appcompat.widget.Button.4
                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public Drawable getBackground() {
                    return Button.this.mBgColor;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public MaterialConfig.BlurConfig getBlurConfig(boolean z) {
                    if (Button.this.mMaterial == null) {
                        return null;
                    }
                    MaterialConfig.BlurConfig blurConfig = Button.this.mMaterial.getBlurConfig(z);
                    MaterialConfig.ColorBlendConfig colorBlendConfig = Button.this.mMaterial.getColorBlendConfig(z);
                    return (blurConfig != null || colorBlendConfig == null) ? blurConfig : new MaterialConfig.BlurConfig(colorBlendConfig);
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public boolean isLightTheme() {
                    return Button.this.mIsLightStyle;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurApplyStateChanged(boolean z) {
                    Button.this.mApplyBlur = z;
                    Drawable background = Button.this.getBackground();
                    if (background != null) {
                        if (background instanceof LayerDrawable) {
                            background = ((LayerDrawable) background).getDrawable(0);
                            if (background instanceof SmoothContainerDrawable2) {
                                background = ((SmoothContainerDrawable2) background).getChildDrawable();
                            }
                        }
                        if (background != null) {
                            background.setAlpha(Button.this.mApplyBlur ? 0 : 255);
                        }
                        Button.this.invalidate();
                    }
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurEnableStateChanged(boolean z) {
                }
            });
            button.setSupportBlur(true);
        } else {
            button = this;
            button.mBlurUiHelper = null;
            button.mApplyBlur = false;
        }
        button.init();
    }

    private void init() {
        post(this.mInitAnimatorTask);
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.applyBlur(z);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        if (this.mFolmeAnimator == null) {
            super.drawableStateChanged();
            return;
        }
        int currentTextColor = getCurrentTextColor();
        super.drawableStateChanged();
        int currentTextColor2 = getCurrentTextColor();
        ColorStateList colorStateList = this.mCurrentTextColorStateList;
        if (colorStateList != null) {
            currentTextColor2 = colorStateList.getColorForState(getDrawableState(), this.mCurrentTextColorStateList.getDefaultColor());
        }
        if (currentTextColor != currentTextColor2) {
            this.mCurrentTextColorInAnim = currentTextColor;
            startTextColorTransition(currentTextColor2);
        }
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialConfig getCurrentMaterial() {
        return this.mCurrentMaterial;
    }

    public int getCurrentTextColorInAnim() {
        return this.mCurrentTextColorInAnim;
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialDayNightConfig getMaterial() {
        return this.mMaterial;
    }

    public boolean isApplyBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isApplyBlur();
    }

    public boolean isSupportBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isSupportBlur();
    }

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateMaterialEffect();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        IFolme iFolme = this.mFolmeAnimator;
        if (iFolme != null) {
            iFolme.state().cancel();
        }
        removeCallbacks(this.mInitAnimatorTask);
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(Math.min(getMaxWidth(), getMeasuredWidth()), getMeasuredHeight());
    }

    public void restoreTextColor() {
        ColorStateList colorStateList;
        if (this.mFolmeAnimator == null || (colorStateList = this.mCurrentTextColorStateList) == null) {
            return;
        }
        super.setTextColor(colorStateList);
        this.mCurrentTextColorStateList = null;
    }

    public void setCurrentTextColorInAnim(int i) {
        if (this.mFolmeAnimator == null || this.mCurrentTextColorInAnim == i) {
            return;
        }
        this.mCurrentTextColorInAnim = i;
        super.setTextColor(i);
    }

    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setEnableBlur(z);
        }
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialConfig materialConfig) {
        setMaterial(new MaterialDayNightConfig(materialConfig));
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        if (isSupportBlur()) {
            if (materialDayNightConfig != null) {
                this.mMaterial = materialDayNightConfig;
                updateMaterialEffect();
            } else {
                this.mMaterial = null;
                applyBlur(false);
                HyperBloomStrokeUtils.clearBloomStroke(this);
            }
        }
    }

    public void setMaterial(@Nullable MaterialDayNightToken materialDayNightToken) {
        setMaterial(MaterialDayNightConfig.create(materialDayNightToken));
    }

    public void setMaterial(@Nullable MaterialToken materialToken) {
        setMaterial(MaterialDayNightConfig.create(new MaterialDayNightToken(materialToken)));
    }

    public void setSupportBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setSupportBlur(z);
        }
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        IFolme iFolme = this.mFolmeAnimator;
        if (iFolme != null) {
            iFolme.state().cancel();
            restoreTextColor();
        }
        super.setTextColor(i);
    }

    @Override // android.widget.TextView
    public void setTextColor(ColorStateList colorStateList) {
        IFolme iFolme = this.mFolmeAnimator;
        if (iFolme != null) {
            iFolme.state().cancel();
            restoreTextColor();
        }
        super.setTextColor(colorStateList);
    }

    public void startTextColorTransition(int i) {
        if (this.mFolmeAnimator == null) {
            return;
        }
        if (this.mCurrentTextColorStateList == null) {
            this.mCurrentTextColorStateList = getTextColors();
        }
        this.mFolmeAnimator.state().to(TEXT_COLOR_PROPERTY, Integer.valueOf(i), this.mTextColorConfig);
    }

    public void updateMaterialEffect() {
        MaterialDayNightConfig materialDayNightConfig = this.mMaterial;
        if (materialDayNightConfig == null) {
            return;
        }
        MaterialConfig materialConfig = materialDayNightConfig.get(this.mIsLightStyle);
        this.mCurrentMaterial = materialConfig;
        if (materialConfig == null || !HyperMaterialUtils.isFeatureEnable(getContext())) {
            applyBlur(false);
            setEnableBlur(false);
            HyperBloomStrokeUtils.clearBloomStroke(this);
            return;
        }
        setEnableBlur(true);
        if (this.mBlurUiHelper != null && this.mCurrentMaterial.getBlurConfig() != null) {
            if (isApplyBlur()) {
                this.mBlurUiHelper.onConfigChanged();
                this.mBlurUiHelper.refreshBlur();
            } else {
                this.mBlurUiHelper.onConfigChanged();
                applyBlur(true);
            }
        }
        MaterialConfig.BloomStrokeConfig bloomStrokeConfig = this.mCurrentMaterial.getBloomStrokeConfig();
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(this, bloomStrokeConfig);
        } else {
            HyperBloomStrokeUtils.clearBloomStroke(this);
        }
        updateShadow(this.mIsLightStyle);
    }

    public final void updateShadow(boolean z) {
        MaterialConfig materialConfig = this.mCurrentMaterial;
        if (materialConfig == null) {
            return;
        }
        MaterialConfig.ShadowConfig shadowConfig = materialConfig.getShadowConfig();
        if (shadowConfig == null) {
            DropShadowHelper dropShadowHelper = this.mShadowHelper;
            if (dropShadowHelper != null) {
                dropShadowHelper.enableViewShadow(this, false, 2);
                return;
            }
            return;
        }
        DropShadowHelper dropShadowHelper2 = this.mShadowHelper;
        if (dropShadowHelper2 != null) {
            dropShadowHelper2.updateDropShadowConfig(shadowConfig);
            this.mShadowHelper.updateViewShadow(this, 2);
            return;
        }
        DropShadowHelper dropShadowHelper3 = new DropShadowHelper(getContext(), new DropShadowConfig.Builder(shadowConfig).create(), z);
        this.mShadowHelper = dropShadowHelper3;
        dropShadowHelper3.setClipShadow(true);
        if (this.mShadowHelper.isEnableMiShadow()) {
            this.mShadowHelper.enableViewShadow(this, true, 2);
            this.mShadowHelper.invalidateShadow(this);
        }
    }
}

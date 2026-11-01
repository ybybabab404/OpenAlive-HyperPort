package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ValueProperty;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.internal.util.AttributeResolver;
import miuix.miuixbasewidget.R;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.view.BlurableWidget;
import miuix.view.DynamicThemeWidget;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class FloatingActionButton extends ImageView implements BlurableWidget, DynamicThemeWidget {
    public static final ValueProperty SHADOW_ALPHA_PROPERTY = new ValueProperty<FloatingActionButton>("shadow_alpha") { // from class: miuix.miuixbasewidget.widget.FloatingActionButton.2
        @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
        public float getValue(FloatingActionButton floatingActionButton) {
            return floatingActionButton.mFabShadowAlpha;
        }

        @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
        public void setValue(FloatingActionButton floatingActionButton, float f) {
            floatingActionButton.updateShadowAlpha((int) f);
        }
    };
    public boolean mBlurEnabled;
    public MiuiBlurUiHelper mBlurUiHelper;
    public MaterialConfig mCurrentMaterial;
    public Drawable mDefaultBackground;
    public FabEmptyHolder mEmptyHolder;
    public int mFabColor;
    public int mFabShadowAlpha;
    public FabShadowHelper mFabShadowHelper;
    public final Runnable mFolmeInitTask;
    public boolean mHasFabColor;
    public int mImageAlpha;
    public boolean mIsLightStyle;
    public MaterialDayNightConfig mMaterial;
    public int mUserThemeType;

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFabShadowHelper = new FabShadowHelper();
        this.mImageAlpha = 255;
        this.mFabShadowAlpha = 255;
        this.mFolmeInitTask = new Runnable() { // from class: miuix.miuixbasewidget.widget.FloatingActionButton.1
            @Override // java.lang.Runnable
            public void run() {
                Folme.useAt(FloatingActionButton.this).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(FloatingActionButton.this, new AnimConfig[0]);
                Folme.useAt(FloatingActionButton.this).touch().setTintMode(3).setTouchRadius((float) Math.ceil(FloatingActionButton.this.getViewWidth() / 2.0f)).handleTouchOf(FloatingActionButton.this, new AnimConfig[0]);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, i, R.style.Widget_FloatingActionButton);
        this.mFabShadowHelper = new FabShadowHelper();
        int i2 = R.styleable.FloatingActionButton_fabColor;
        this.mHasFabColor = typedArrayObtainStyledAttributes.hasValue(i2);
        this.mBlurEnabled = typedArrayObtainStyledAttributes.hasValue(R.styleable.FloatingActionButton_fabBlurEnabled);
        this.mFabColor = typedArrayObtainStyledAttributes.getColor(i2, context.getResources().getColor(R.color.miuix_appcompat_fab_color));
        typedArrayObtainStyledAttributes.recycle();
        this.mEmptyHolder = new FabEmptyHolder(this, getContext().getResources().getDrawable(R.drawable.miuix_appcompat_fab_empty_holder));
        this.mIsLightStyle = AttributeResolver.resolveBoolean(context, R.attr.isLightTheme, true);
        initBackground();
        if (!HyperMaterialUtils.isSupport()) {
            this.mBlurUiHelper = null;
            return;
        }
        MiuiBlurUiHelper miuiBlurUiHelper = new MiuiBlurUiHelper(context, this, false, false, true, new FabBlurStateCallback(this));
        this.mBlurUiHelper = miuiBlurUiHelper;
        miuiBlurUiHelper.setSupportBlur(true);
    }

    public static int clampAlpha(int i) {
        return Math.max(0, Math.min(255, i));
    }

    private Drawable getDefaultBackground() {
        if (this.mDefaultBackground == null) {
            this.mFabColor = getContext().getResources().getColor(R.color.miuix_appcompat_fab_color_light);
            this.mHasFabColor = true;
            this.mDefaultBackground = createFabBackground();
        }
        return this.mDefaultBackground;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getViewWidth() {
        int i = getLayoutParams().width;
        int intrinsicWidth = getDrawable() != null ? getDrawable().getIntrinsicWidth() : 0;
        if (i == -2) {
            return intrinsicWidth;
        }
        if (i >= 0) {
            return i;
        }
        return 0;
    }

    public final void applyAlphaToDrawables(boolean z) {
        Drawable background = getBackground();
        if (background != null && !isApplyBlur()) {
            background.setAlpha(this.mImageAlpha);
        }
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.mutate().setAlpha(this.mImageAlpha);
        }
        FabShadowHelper fabShadowHelper = this.mFabShadowHelper;
        if (fabShadowHelper != null && fabShadowHelper.isMiShadowEnabled()) {
            this.mFabShadowHelper.invalidateShadow(this, this.mImageAlpha / 255.0f);
        }
        invalidate();
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.applyBlur(z);
        }
    }

    public final void applyMaterialEffect(boolean z) {
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
        updateBloomStroke();
    }

    public final void clearMaterialEffect() {
        applyBlur(false);
        setEnableBlur(false);
        HyperBloomStrokeUtils.clearBloomStroke(this);
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(this.mImageAlpha);
        }
    }

    public final Drawable createFabBackground() {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new FabOvalShapeWithPadding(this));
        shapeDrawable.getPaint().setColor(this.mFabColor);
        return shapeDrawable;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        FabShadowHelper fabShadowHelper = this.mFabShadowHelper;
        if (fabShadowHelper != null) {
            fabShadowHelper.draw(canvas, getHeight());
            this.mFabShadowHelper.postFirstDrawRefresh(this);
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    public float getAlpha() {
        return isApplyBlur() ? super.getAlpha() : (float) (((double) this.mImageAlpha) / 255.0d);
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialConfig getCurrentMaterial() {
        return this.mCurrentMaterial;
    }

    public int getFabColor() {
        return this.mFabColor;
    }

    @Override // android.widget.ImageView
    public int getImageAlpha() {
        return this.mImageAlpha;
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialDayNightConfig getMaterial() {
        return this.mMaterial;
    }

    public int getThemeType() {
        return this.mUserThemeType;
    }

    public boolean hasThemeType() {
        return this.mUserThemeType > 0;
    }

    public final void initBackground() {
        if (getBackground() != null) {
            this.mHasFabColor = false;
            return;
        }
        if (this.mHasFabColor) {
            super.setBackground(createFabBackground());
        } else {
            super.setBackground(getDefaultBackground());
        }
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(this.mImageAlpha);
        }
    }

    public final void initEmptyHolder() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int width = (((getWidth() - paddingLeft) - getPaddingRight()) / 2) * 2;
        this.mEmptyHolder.setBounds(paddingLeft, paddingTop, paddingLeft + width, width + paddingTop);
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

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FabShadowHelper fabShadowHelper = this.mFabShadowHelper;
        if (fabShadowHelper != null) {
            fabShadowHelper.onConfigChanged(this, configuration);
        }
        this.mIsLightStyle = AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true);
        updateMaterialEffect();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mFolmeInitTask);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        initEmptyHolder();
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        post(this.mFolmeInitTask);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        FabShadowHelper fabShadowHelper = this.mFabShadowHelper;
        if (fabShadowHelper != null) {
            fabShadowHelper.onLayout(this, i, i2, i3, i4);
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        HapticCompat.performHapticFeedback(this, HapticFeedbackConstants.MIUI_BUTTON_MIDDLE, HapticFeedbackConstants.MIUI_TAP_LIGHT);
        return super.performClick();
    }

    public boolean resolveIsLightTheme() {
        if (hasThemeType()) {
            return this.mUserThemeType == 1;
        }
        return this.mIsLightStyle;
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        if (isApplyBlur()) {
            super.setAlpha(f);
        } else {
            this.mImageAlpha = clampAlpha((int) (f * 255.0f));
            applyAlphaToDrawables(true);
        }
    }

    @Override // android.widget.ImageView
    public void setAlpha(int i) {
        this.mImageAlpha = clampAlpha(i);
        applyAlphaToDrawables(true);
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        this.mHasFabColor = false;
        if (drawable == null) {
            drawable = getDefaultBackground();
        }
        super.setBackground(drawable);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        if (!this.mHasFabColor || this.mFabColor != i) {
            this.mFabColor = i;
            super.setBackground(createFabBackground());
            if (this.mBlurEnabled) {
                updateMaterialEffect();
            }
        }
        this.mHasFabColor = true;
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        this.mHasFabColor = false;
        if (i == 0) {
            super.setBackground(getDefaultBackground());
        } else {
            super.setBackgroundResource(i);
        }
    }

    public void setBlurEnabled(boolean z) {
        if (this.mBlurEnabled == z) {
            return;
        }
        this.mBlurEnabled = z;
        if (z) {
            updateMaterialEffect();
        } else {
            clearMaterialEffect();
        }
    }

    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setEnableBlur(z);
        }
    }

    @Deprecated
    public void setFabShadowColor(int i) {
    }

    @Override // android.widget.ImageView
    public void setImageAlpha(int i) {
        this.mImageAlpha = clampAlpha(i);
        applyAlphaToDrawables(true);
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

    public void setShadowAlpha(int i) {
        Folme.use((View) this).setTo(SHADOW_ALPHA_PROPERTY, Integer.valueOf(i));
    }

    public void setSupportBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setSupportBlur(z);
        }
    }

    public void setThemeType(int i) {
        if (this.mUserThemeType != i) {
            this.mUserThemeType = i;
            updateMaterialEffect();
        }
    }

    public final void updateBloomStroke() {
        MaterialConfig materialConfig = this.mCurrentMaterial;
        if (materialConfig == null) {
            return;
        }
        MaterialConfig.BloomStrokeConfig bloomStrokeConfig = materialConfig.getBloomStrokeConfig();
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(this, bloomStrokeConfig);
        } else {
            HyperBloomStrokeUtils.clearBloomStroke(this);
        }
    }

    public void updateMaterialEffect() {
        if (this.mMaterial == null || !this.mBlurEnabled) {
            return;
        }
        boolean zResolveIsLightTheme = resolveIsLightTheme();
        MaterialConfig materialConfig = this.mMaterial.get(zResolveIsLightTheme);
        this.mCurrentMaterial = materialConfig;
        if (materialConfig == null || !HyperMaterialUtils.isFeatureEnable(getContext())) {
            clearMaterialEffect();
        } else {
            applyMaterialEffect(zResolveIsLightTheme);
        }
    }

    public final void updateShadowAlpha(int i) {
        int iMax = Math.max(Math.min(i, 255), 0);
        this.mFabShadowAlpha = iMax;
        FabShadowHelper fabShadowHelper = this.mFabShadowHelper;
        if (fabShadowHelper != null) {
            fabShadowHelper.setAlpha(iMax);
            this.mFabShadowHelper.invalidateShadow(this, iMax / 255.0f);
        }
    }
}

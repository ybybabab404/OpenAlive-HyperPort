package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IHoverStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiShadowUtils;
import miuix.internal.util.AttributeResolver;
import miuix.miuixbasewidget.R;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.view.BlurableWidget;
import miuix.view.DynamicThemeWidget;
import miuix.view.ITransformableView;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class OvalImageButton extends AppCompatImageView implements BlurableWidget, DynamicThemeWidget, ITransformableView {
    public OvalDrawable mBackground;
    public final MiuiBlurUiHelper mBlurUiHelper;
    public MaterialConfig mCurrentMaterial;
    public Drawable mDefaultBackground;
    public AnimConfig mDownConfig;
    public final FabEmptyHolder mEmptyHolder;
    public final ITouchStyle mFolmeTouch;
    public boolean mHasOibColor;
    public int mImageAlpha;
    public final boolean mIsGlassEnabled;
    public boolean mIsLightStyle;
    public final boolean mIsShadowEnabled;
    public MaterialDayNightConfig mMaterial;
    public ColorStateList mOibColor;
    public BaseWidgetDropShadowHelper mShadowHelper;
    public int mStrokeColor;
    public float[] mStrokeGradientColorPositions;
    public int[] mStrokeGradientColors;
    public int mStrokeWidth;
    public AnimConfig mUpConfig;
    public int mUserThemeType;

    public OvalImageButton(Context context) {
        this(context, null);
    }

    public OvalImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OvalImageButton(Context context, AttributeSet attributeSet, int i) {
        OvalImageButton ovalImageButton;
        super(context, attributeSet, i);
        this.mImageAlpha = 255;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.OvalImageButton, i, R.style.Widget_OvalImageButton);
        this.mIsLightStyle = typedArrayObtainStyledAttributes.getBoolean(R.styleable.OvalImageButton_isLightTheme, true);
        this.mIsShadowEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.OvalImageButton_oibShadowEnabled, true);
        this.mIsGlassEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.OvalImageButton_oibGlassEnabled, false);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.OvalImageButton_oibColor);
        this.mOibColor = colorStateList;
        this.mHasOibColor = colorStateList != null;
        int i2 = R.styleable.OvalImageButton_oibTouchColor;
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(i2);
        int color = typedArrayObtainStyledAttributes.getColor(i2, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.OvalImageButton_oibTouchScalable, true);
        typedArrayObtainStyledAttributes.recycle();
        this.mEmptyHolder = new FabEmptyHolder(this, getContext().getResources().getDrawable(R.drawable.miuix_appcompat_fab_empty_holder));
        initBackground();
        if (HyperMaterialUtils.isSupport()) {
            ovalImageButton = this;
            MiuiBlurUiHelper miuiBlurUiHelper = new MiuiBlurUiHelper(context, ovalImageButton, false, false, false, new MiuiBlurUiHelper.BlurStateCallback() { // from class: miuix.miuixbasewidget.widget.OvalImageButton.1
                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public Drawable getBackground() {
                    return null;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public MaterialConfig.BlurConfig getBlurConfig(boolean z2) {
                    if (OvalImageButton.this.mMaterial == null) {
                        return null;
                    }
                    MaterialConfig.BlurConfig blurConfig = OvalImageButton.this.mMaterial.getBlurConfig(z2);
                    MaterialConfig.ColorBlendConfig colorBlendConfig = OvalImageButton.this.mMaterial.getColorBlendConfig(z2);
                    return (blurConfig != null || colorBlendConfig == null) ? blurConfig : new MaterialConfig.BlurConfig(colorBlendConfig);
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public boolean isLightTheme() {
                    if (OvalImageButton.this.hasThemeType()) {
                        return OvalImageButton.this.mUserThemeType == 1;
                    }
                    return OvalImageButton.this.mIsLightStyle;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurApplyStateChanged(boolean z2) {
                    Drawable background = OvalImageButton.this.getBackground();
                    if (background != null) {
                        background.setAlpha(z2 ? 0 : 255);
                    }
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurEnableStateChanged(boolean z2) {
                }
            });
            ovalImageButton.mBlurUiHelper = miuiBlurUiHelper;
            miuiBlurUiHelper.setSupportBlur(true);
            ovalImageButton.setEnableBlur(true);
        } else {
            ovalImageButton = this;
            ovalImageButton.mBlurUiHelper = null;
        }
        ITouchStyle iTouchStyle = Folme.use((View) ovalImageButton).touch();
        ovalImageButton.mFolmeTouch = iTouchStyle;
        if (zHasValue) {
            iTouchStyle.setTint(color);
        }
        iTouchStyle.setNoScale(!z);
        iTouchStyle.setTintMode(3).handleTouchOf((View) ovalImageButton, true, new AnimConfig[0]);
        Folme.use((View) ovalImageButton).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(ovalImageButton, new AnimConfig[0]);
        AnimConfig animConfig = new AnimConfig();
        ovalImageButton.mDownConfig = animConfig;
        animConfig.setEase(FolmeEase.spring(1.0f, 0.2f));
        AnimConfig animConfig2 = new AnimConfig();
        ovalImageButton.mUpConfig = animConfig2;
        animConfig2.setEase(FolmeEase.spring(0.95f, 0.35f));
        ovalImageButton.initFolmeTouch();
    }

    private void clearMaterialEffect() {
        applyBlur(false);
        setEnableBlur(false);
        HyperBloomStrokeUtils.clearBloomStroke(this);
        HyperMaterialUtils.clearContainer(this);
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(this.mImageAlpha);
        }
    }

    private Drawable getDefaultBackground() {
        if (this.mDefaultBackground == null) {
            this.mOibColor = ContextCompat.getColorStateList(getContext(), R.color.miuix_color_black_level6);
            this.mHasOibColor = true;
            this.mDefaultBackground = createOibBackground();
        }
        return this.mDefaultBackground;
    }

    @Nullable
    private OvalDrawable getOvalBackground() {
        return this.mBackground;
    }

    private void initBackground() {
        if (getBackground() != null) {
            this.mHasOibColor = false;
            return;
        }
        if (this.mHasOibColor) {
            super.setBackground(createOibBackground());
        } else {
            super.setBackground(getDefaultBackground());
        }
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(this.mImageAlpha);
        }
    }

    private void initEmptyHolder() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int width = (((getWidth() - paddingLeft) - getPaddingRight()) / 2) * 2;
        this.mEmptyHolder.setBounds(paddingLeft, paddingTop, paddingLeft + width, width + paddingTop);
    }

    private boolean resolveIsLightTheme() {
        if (hasThemeType()) {
            return this.mUserThemeType == 1;
        }
        return this.mIsLightStyle;
    }

    private void updateShadow(boolean z) {
        MaterialConfig materialConfig = this.mCurrentMaterial;
        if (materialConfig == null) {
            return;
        }
        if (materialConfig.getShadowConfig() == null) {
            BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = this.mShadowHelper;
            if (baseWidgetDropShadowHelper != null) {
                baseWidgetDropShadowHelper.enableViewShadow(this, false, 2);
                return;
            }
            return;
        }
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper2 = this.mShadowHelper;
        if (baseWidgetDropShadowHelper2 == null) {
            this.mShadowHelper = new BaseWidgetDropShadowHelper(getContext(), new DropShadowConfig.Builder(this.mCurrentMaterial.getShadowConfig()).create(), z);
        } else {
            baseWidgetDropShadowHelper2.updateDropShadowConfig(this.mCurrentMaterial.getShadowConfig());
            this.mShadowHelper.updateViewShadow(this, 2);
        }
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.applyBlur(z);
        }
    }

    public final void applyBlurEffect(boolean z) {
        setEnableBlur(true);
        if (this.mBlurUiHelper != null && this.mCurrentMaterial.getBlurConfig() != null) {
            this.mBlurUiHelper.onConfigChanged();
            if (isApplyBlur()) {
                this.mBlurUiHelper.refreshBlur();
            } else {
                applyBlur(true);
            }
        }
        processStroke(z);
    }

    public final void applyGlassEffect(boolean z) {
        MaterialConfig materialConfig = this.mCurrentMaterial;
        if (materialConfig == null) {
            return;
        }
        HyperMaterialUtils.applyContainerWithGlass(this, materialConfig.getGlassConfig());
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(0);
        }
        processStroke(z);
    }

    @Override // miuix.view.ITransformableView
    public void applyTransformElevation(View view) {
        if (MiShadowUtils.SUPPORT_MI_SHADOW) {
            MiShadowUtils.clearMiShadow(view);
        } else {
            view.setElevation(0.0f);
        }
    }

    @Override // miuix.view.ITransformableView
    public void clearAppearance() {
        setScaleX(1.0f);
        setScaleY(1.0f);
    }

    public final Drawable createOibBackground() {
        OvalDrawable ovalDrawable = new OvalDrawable(this.mOibColor);
        if (this.mIsShadowEnabled) {
            boolean zResolveBoolean = AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true);
            BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = this.mShadowHelper;
            if (baseWidgetDropShadowHelper == null) {
                updateShadow(zResolveBoolean);
            } else {
                baseWidgetDropShadowHelper.onConfigChanged(this, getResources().getConfiguration(), zResolveBoolean);
            }
        } else {
            BaseWidgetDropShadowHelper baseWidgetDropShadowHelper2 = this.mShadowHelper;
            if (baseWidgetDropShadowHelper2 != null) {
                baseWidgetDropShadowHelper2.enableViewShadow(this, false, 2);
            }
            this.mShadowHelper = null;
        }
        this.mBackground = ovalDrawable;
        return ovalDrawable;
    }

    @Override // android.widget.ImageView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ImageButton.class.getName();
    }

    @Override // miuix.view.ITransformableView
    public View getContainerView() {
        return this;
    }

    @Override // miuix.view.ITransformableView
    public View getContentView() {
        return this;
    }

    @Override // miuix.view.ITransformableView
    public float getCornerRadius() {
        return 100.0f;
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialConfig getCurrentMaterial() {
        return this.mCurrentMaterial;
    }

    @Override // miuix.view.ITransformableView
    @Nullable
    public /* bridge */ /* synthetic */ View getElevationView() {
        return super.getElevationView();
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

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public int getThemeType() {
        return this.mUserThemeType;
    }

    public boolean hasThemeType() {
        return this.mUserThemeType > 0;
    }

    public final void initFolmeTouch() {
        setOnTouchListener(new View.OnTouchListener() { // from class: miuix.miuixbasewidget.widget.OvalImageButton.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    OvalImageButton.this.mFolmeTouch.touchDown(OvalImageButton.this.mDownConfig);
                    return false;
                }
                if (actionMasked != 1 && actionMasked != 3) {
                    return false;
                }
                OvalImageButton.this.mFolmeTouch.touchUp(OvalImageButton.this.mUpConfig);
                return false;
            }
        });
    }

    public boolean isApplyBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isApplyBlur();
    }

    public final boolean isGlassEffectActive() {
        return this.mIsGlassEnabled && HyperMaterialUtils.isSupportGlass() && HyperMaterialUtils.isEnableBionics(getContext());
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
        updateMaterialEffect();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        initEmptyHolder();
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = this.mShadowHelper;
        if (baseWidgetDropShadowHelper != null) {
            baseWidgetDropShadowHelper.enableViewShadow(this, this.mIsShadowEnabled, 2);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mFolmeTouch.setTouchRadius(getMeasuredWidth() / 2.0f);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z && HyperMaterialUtils.isSupport()) {
            HyperMaterialUtils.clearFeatureEnable();
            HyperMaterialUtils.isFeatureEnableNoCache(getContext());
            HyperMaterialUtils.getMaterialType(getContext());
            updateMaterialEffect();
        }
    }

    public final void processStroke(boolean z) {
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
        updateShadow(z);
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        this.mHasOibColor = false;
        if (drawable == null) {
            drawable = getDefaultBackground();
        }
        super.setBackground(drawable);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mOibColor = ColorStateList.valueOf(i);
        super.setBackground(createOibBackground());
        this.mHasOibColor = true;
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.view.View
    public void setBackgroundResource(int i) {
        this.mHasOibColor = false;
        if (i == 0) {
            super.setBackground(getDefaultBackground());
        } else {
            super.setBackgroundResource(i);
        }
    }

    @Override // miuix.view.ITransformableView
    public /* bridge */ /* synthetic */ void setCornerRadius(float f) {
        super.setCornerRadius(f);
    }

    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setEnableBlur(z);
        }
    }

    @Override // android.widget.ImageView
    public void setImageAlpha(int i) {
        boolean z = this.mImageAlpha != i;
        this.mImageAlpha = i;
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(i);
        }
        Drawable drawable = getDrawable();
        if (drawable != null && z) {
            drawable.mutate().setAlpha(i);
        }
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = this.mShadowHelper;
        if (baseWidgetDropShadowHelper != null && baseWidgetDropShadowHelper.mEnableMiShadow) {
            baseWidgetDropShadowHelper.invalidateShadow(this, this.mImageAlpha / 255.0f);
        }
        invalidate();
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialConfig materialConfig) {
        setMaterial(new MaterialDayNightConfig(materialConfig));
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        if (isSupportBlur()) {
            if (materialDayNightConfig == null) {
                this.mMaterial = null;
                clearMaterialEffect();
            } else {
                this.mMaterial = materialDayNightConfig;
                updateMaterialEffect();
            }
        }
    }

    public void setMaterial(@Nullable MaterialDayNightToken materialDayNightToken) {
        setMaterial(MaterialDayNightConfig.create(materialDayNightToken));
    }

    public void setMaterial(@Nullable MaterialToken materialToken) {
        setMaterial(MaterialDayNightConfig.create(new MaterialDayNightToken(materialToken)));
    }

    public void setStrokeColor(int i) {
        if (this.mStrokeColor != i) {
            this.mStrokeColor = i;
            OvalDrawable ovalBackground = getOvalBackground();
            if (ovalBackground != null) {
                ovalBackground.setStrokeColor(this.mStrokeColor);
            }
        }
    }

    public void setStrokeGradientColors(int i, int i2) {
        this.mStrokeGradientColors = new int[]{i, i2};
        this.mStrokeGradientColorPositions = new float[]{0.0f, 1.0f};
        OvalDrawable ovalBackground = getOvalBackground();
        if (ovalBackground != null) {
            ovalBackground.setStrokeGradientColors(this.mStrokeGradientColors);
            ovalBackground.setStrokeColorGradientPositions(this.mStrokeGradientColorPositions);
        }
    }

    public void setStrokeGradientColors(int[] iArr, float[] fArr) {
        this.mStrokeGradientColors = iArr;
        this.mStrokeGradientColorPositions = fArr;
        OvalDrawable ovalBackground = getOvalBackground();
        if (ovalBackground != null) {
            ovalBackground.setStrokeGradientColors(this.mStrokeGradientColors);
            ovalBackground.setStrokeColorGradientPositions(this.mStrokeGradientColorPositions);
        }
    }

    public void setStrokeWidth(int i) {
        if (this.mStrokeWidth != i) {
            this.mStrokeWidth = i;
            OvalDrawable ovalBackground = getOvalBackground();
            if (ovalBackground != null) {
                ovalBackground.setStrokeWidth(i);
            }
        }
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

    public void setTouchColor(int i) {
        this.mFolmeTouch.setTint(i);
    }

    public void setTouchScalable(boolean z) {
        this.mFolmeTouch.setNoScale(!z);
    }

    public void updateMaterialEffect() {
        if (this.mMaterial == null) {
            return;
        }
        boolean zResolveIsLightTheme = resolveIsLightTheme();
        MaterialConfig materialConfig = this.mMaterial.get(zResolveIsLightTheme);
        this.mCurrentMaterial = materialConfig;
        if (materialConfig == null || !HyperMaterialUtils.isFeatureEnable(getContext())) {
            clearMaterialEffect();
        } else if (isGlassEffectActive()) {
            applyGlassEffect(zResolveIsLightTheme);
        } else {
            applyBlurEffect(zResolveIsLightTheme);
        }
    }
}

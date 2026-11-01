package miuix.bottomsheet;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.AttributeResolver;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.view.BlurableWidget;
import miuix.view.DynamicThemeWidget;
import miuix.view.Fence;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class BottomSheetView extends FrameLayout implements BlurableWidget, DynamicThemeWidget, Fence {
    public static final MaterialToken Default_BottomSheet_Dark;
    public static final MaterialToken Default_BottomSheet_Light;
    public static final MaterialDayNightToken Default_BottomSheet_Material;
    public AttributeSet mAttrs;
    public Drawable mBackground;
    public Drawable mBackgroundInBlur;
    public boolean mBlurEnable;
    public MiuiBlurUiHelper mBlurUiHelper;
    public float[] mBottomModeRadii;
    public int mBottomModeRadius;
    public BottomModeOutlineProvider mBottomOutlineProvider;
    public boolean mClipByOutline;
    public Path mClipPath;
    public FrameLayout mContainerView;
    public MaterialConfig mCurrentMaterial;
    public int mCurrentMode;
    public float mDensityDpi;
    public View mDragHandleContainerView;
    public boolean mDragHandleViewEnabled;
    public View mExtraHeightView;
    public boolean mFenceEnabled;
    public FloatingModeOutlineProvider mFloatingModeOutlineProvider;
    public float[] mFloatingModeRadii;
    public int mFloatingModeRadius;
    public boolean mHandleOverlay;
    public MaterialDayNightConfig mMaterial;
    public int mUserThemeType;

    public static class BottomModeOutlineProvider extends ViewOutlineProvider {
        public final float mRadius;

        public BottomModeOutlineProvider(float f) {
            this.mRadius = f;
        }

        public final Path getConvexPath(View view) {
            Path path = new Path();
            int width = view.getWidth();
            int height = view.getHeight();
            float f = this.mRadius;
            float f2 = 0;
            path.addRoundRect(new RectF(f2, f2, width, height), new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f}, Path.Direction.CW);
            return path;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setConvexPath(getConvexPath(view));
        }
    }

    public static class FloatingModeOutlineProvider extends ViewOutlineProvider {
        public final float mRadius;

        public FloatingModeOutlineProvider(float f) {
            this.mRadius = f;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.mRadius);
        }
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(10, "bottomsheet-default", "light").setColorBlend(ColorBlendToken.Pured_Regular_Glass_Light).setElementBlur(60).setBloomStroke(BloomStrokeToken.Glass_Stroke_Big_Light).build();
        Default_BottomSheet_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(10, "bottomsheet-default", "dark").setColorBlend(ColorBlendToken.Pured_Regular_Glass_Dark).setElementBlur(60).setBloomStroke(BloomStrokeToken.Glass_Stroke_Big_Dark).build();
        Default_BottomSheet_Dark = materialTokenBuild2;
        Default_BottomSheet_Material = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
    }

    public BottomSheetView(@NonNull Context context) {
        super(context);
        this.mCurrentMode = -1;
        this.mDragHandleViewEnabled = true;
        this.mBlurEnable = false;
        this.mFenceEnabled = true;
        init(context, null);
    }

    public BottomSheetView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentMode = -1;
        this.mDragHandleViewEnabled = true;
        this.mBlurEnable = false;
        this.mFenceEnabled = true;
        init(context, attributeSet);
    }

    public BottomSheetView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentMode = -1;
        this.mDragHandleViewEnabled = true;
        this.mBlurEnable = false;
        this.mFenceEnabled = true;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetView, R.attr.bottomSheetStyle, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetView_contentHeightMatchParent, false);
        this.mHandleOverlay = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetView_handleOverlay, false);
        typedArrayObtainStyledAttributes.recycle();
        boolean z2 = this.mHandleOverlay;
        View viewInflate = LayoutInflater.from(z2 ? wrapWithOverlayDragHandleDefaultStyle(context) : context).inflate(z2 ? R.layout.miuix_handle_overlay_bottom_sheet_view : R.layout.miuix_bottom_sheet_view, (ViewGroup) this, false);
        if (z) {
            addView(viewInflate, new FrameLayout.LayoutParams(-1, -1));
        } else {
            addView(viewInflate, new FrameLayout.LayoutParams(-1, -2));
        }
        setClipToOutline(true);
        this.mAttrs = attributeSet;
        this.mClipByOutline = true;
        this.mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        refreshSizes(attributeSet);
        if (!HyperMaterialUtils.isEnable()) {
            this.mBlurUiHelper = null;
            return;
        }
        this.mBackground = getBackground();
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true);
        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(Default_BottomSheet_Material);
        this.mMaterial = materialDayNightConfigCreate;
        if (materialDayNightConfigCreate != null) {
            this.mCurrentMaterial = materialDayNightConfigCreate.get(zResolveBoolean);
        }
        this.mBlurUiHelper = new MiuiBlurUiHelper(getContext(), this, false, false, false, new MiuiBlurUiHelper.BlurStateCallback() { // from class: miuix.bottomsheet.BottomSheetView.1
            public final boolean isDarkThemeOverlay;

            {
                this.isDarkThemeOverlay = MiuixUIUtils.isDarkThemeOverlay(BottomSheetView.this.getContext(), R.color.miuix_default_color_on_surface_light);
            }

            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public int getBackgroundColor() {
                if (this.isDarkThemeOverlay) {
                    return AttributeResolver.resolveColor(BottomSheetView.this.getContext(), miuix.theme.R.attr.colorSurface, 0);
                }
                return 0;
            }

            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public MaterialConfig.BlurConfig getBlurConfig(boolean z3) {
                MaterialDayNightConfig materialDayNightConfig = BottomSheetView.this.mMaterial;
                if (materialDayNightConfig != null) {
                    return materialDayNightConfig.getBlurConfig(z3);
                }
                return null;
            }

            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public boolean isLightTheme() {
                if (BottomSheetView.this.hasThemeType()) {
                    return BottomSheetView.this.mUserThemeType == 1;
                }
                return !this.isDarkThemeOverlay && AttributeResolver.resolveBoolean(BottomSheetView.this.getContext(), R.attr.isLightTheme, true);
            }

            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public void onBlurApplyStateChanged(boolean z3) {
                if (z3) {
                    BottomSheetView bottomSheetView = BottomSheetView.this;
                    bottomSheetView.setBackground(bottomSheetView.mBackgroundInBlur);
                } else {
                    BottomSheetView bottomSheetView2 = BottomSheetView.this;
                    bottomSheetView2.setBackground(bottomSheetView2.mBackground);
                }
                BottomSheetView.this.invalidate();
            }

            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public void onBlurEnableStateChanged(boolean z3) {
            }
        });
        setSupportBlur(true);
        setEnableBlur(HyperMaterialUtils.isFeatureEnable(getContext()));
        this.mBlurUiHelper.applyBlur(this.mBlurEnable);
        MaterialConfig materialConfig = this.mCurrentMaterial;
        if (materialConfig == null || !this.mBlurEnable) {
            return;
        }
        MaterialConfig.BloomStrokeConfig bloomStrokeConfig = materialConfig.getBloomStrokeConfig();
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(this, bloomStrokeConfig);
        } else {
            HyperBloomStrokeUtils.clearBloomStroke(this);
        }
    }

    private void refreshSizes(AttributeSet attributeSet) {
        Resources resources = getResources();
        this.mBottomModeRadius = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_radius);
        this.mFloatingModeRadius = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_floating_radius);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.BottomSheetView, R.attr.bottomSheetStyle, 0);
            this.mBottomModeRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetView_bottomModeRadius, this.mBottomModeRadius);
            this.mFloatingModeRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetView_floatingModeRadius, this.mFloatingModeRadius);
            this.mBlurEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetView_blurEnabled, false);
            typedArrayObtainStyledAttributes.recycle();
        }
        int i = this.mBottomModeRadius;
        this.mBottomModeRadii = new float[]{i, i, i, i, 0.0f, 0.0f, 0.0f, 0.0f};
        int i2 = this.mFloatingModeRadius;
        this.mFloatingModeRadii = new float[]{i2, i2, i2, i2, i2, i2, i2, i2};
    }

    public static Context wrapWithOverlayDragHandleDefaultStyle(Context context) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(R.attr.bottomSheetDragHandleStyle, typedValue, true) || typedValue.resourceId == 0) {
            return new ContextThemeWrapper(context, AttributeResolver.resolveBoolean(context, R.attr.isLightTheme, true) ? R.style.Widget_Miuix_BottomSheet_OverlayDragHandle_DefaultStyle_Light : R.style.Widget_Miuix_BottomSheet_OverlayDragHandle_DefaultStyle_Dark);
        }
        return context;
    }

    public void addContentChildView(View view) {
        FrameLayout frameLayout = this.mContainerView;
        if (frameLayout != null) {
            frameLayout.addView(view);
        }
    }

    public void addContentChildView(View view, ViewGroup.LayoutParams layoutParams) {
        FrameLayout frameLayout = this.mContainerView;
        if (frameLayout != null) {
            frameLayout.addView(view, layoutParams);
        }
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        this.mBlurEnable = z;
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.applyBlur(z);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mClipByOutline) {
            super.draw(canvas);
            return;
        }
        if (this.mClipPath == null) {
            super.draw(canvas);
            return;
        }
        canvas.save();
        canvas.clipPath(this.mClipPath);
        super.draw(canvas);
        canvas.restore();
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialConfig getCurrentMaterial() {
        return this.mCurrentMaterial;
    }

    public int getExtraHeight() {
        View view = this.mExtraHeightView;
        if (view == null || view.getVisibility() == 8) {
            return 0;
        }
        return this.mExtraHeightView.getMeasuredHeight();
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

    public void hideDragHandleView() {
        View view = this.mDragHandleContainerView;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public boolean isApplyBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isApplyBlur();
    }

    public boolean isDragHandleViewEnabled() {
        return this.mDragHandleViewEnabled;
    }

    public boolean isEnableBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isEnableBlur();
    }

    @Override // miuix.view.Fence
    public boolean isFenceEnabled() {
        return this.mFenceEnabled;
    }

    public boolean isHandleOverlay() {
        return this.mHandleOverlay;
    }

    public boolean isSupportBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isSupportBlur();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateMaterialEffect();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        float f = configuration.densityDpi;
        if (f != this.mDensityDpi) {
            this.mDensityDpi = f;
            refreshSizes(this.mAttrs);
            if (this.mBottomOutlineProvider != null) {
                this.mBottomOutlineProvider = new BottomModeOutlineProvider(this.mBottomModeRadius);
            }
            if (this.mFloatingModeOutlineProvider != null) {
                this.mFloatingModeOutlineProvider = new FloatingModeOutlineProvider(this.mFloatingModeRadius);
            }
        }
        updateMaterialEffect();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContainerView = (FrameLayout) findViewById(R.id.miuix_bottom_sheet_container_view);
        this.mDragHandleContainerView = findViewById(R.id.drag_handle_container_view);
        this.mExtraHeightView = findViewById(R.id.extra_space_height_view);
        if (this.mDragHandleViewEnabled) {
            return;
        }
        hideDragHandleView();
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mClipByOutline) {
            return;
        }
        if (this.mClipPath == null) {
            this.mClipPath = new Path();
        }
        int i5 = this.mCurrentMode;
        if (i5 == 0) {
            this.mClipPath.reset();
            this.mClipPath.addRoundRect(new RectF(0.0f, 0.0f, i, i2), this.mBottomModeRadii, Path.Direction.CW);
        } else if (i5 == 1) {
            this.mClipPath.reset();
            this.mClipPath.addRoundRect(new RectF(0.0f, 0.0f, i, i2), this.mFloatingModeRadii, Path.Direction.CW);
        } else {
            throw new IllegalArgumentException("Unexpected bottom sheet mode: " + this.mCurrentMode);
        }
    }

    public void removeAllContentViews() {
        FrameLayout frameLayout = this.mContainerView;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
    }

    public void setBottomSheetMode(int i) {
        if (this.mCurrentMode != i) {
            this.mCurrentMode = i;
            if (!this.mClipByOutline) {
                invalidate();
                return;
            }
            if (i == 0) {
                if (this.mBottomOutlineProvider == null) {
                    this.mBottomOutlineProvider = new BottomModeOutlineProvider(this.mBottomModeRadius);
                }
                setOutlineProvider(this.mBottomOutlineProvider);
            } else if (i == 1) {
                if (this.mFloatingModeOutlineProvider == null) {
                    this.mFloatingModeOutlineProvider = new FloatingModeOutlineProvider(this.mFloatingModeRadius);
                }
                setOutlineProvider(this.mFloatingModeOutlineProvider);
            } else {
                throw new IllegalArgumentException("Unexpected bottom sheet mode: " + i);
            }
        }
    }

    public void setDragHandleViewEnabled(boolean z) {
        this.mDragHandleViewEnabled = z;
        if (z) {
            return;
        }
        hideDragHandleView();
    }

    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.setEnableBlur(z);
    }

    public void setExtraHeightEnabled(boolean z) {
        View view = this.mExtraHeightView;
        if (view != null) {
            if (z) {
                view.setVisibility(0);
            } else {
                view.setVisibility(8);
            }
        }
    }

    public void setFenceEnabled(boolean z) {
        this.mFenceEnabled = z;
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialConfig materialConfig) {
        setMaterial(new MaterialDayNightConfig(materialConfig));
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        this.mMaterial = materialDayNightConfig;
        if (isSupportBlur()) {
            if (materialDayNightConfig != null) {
                this.mMaterial = materialDayNightConfig;
                updateMaterialEffect();
                return;
            }
            this.mMaterial = null;
            MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
            if (miuiBlurUiHelper != null) {
                miuiBlurUiHelper.applyBlur(false);
            }
            HyperBloomStrokeUtils.clearBloomStroke(this);
        }
    }

    public void setMaterial(MaterialDayNightToken materialDayNightToken) {
        setMaterial(MaterialDayNightConfig.create(materialDayNightToken));
    }

    public void setMaterial(MaterialToken materialToken) {
        setMaterial(MaterialDayNightConfig.create(new MaterialDayNightToken(materialToken)));
    }

    public void setSupportBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.setSupportBlur(z);
        if (z) {
            this.mBackgroundInBlur = new ColorDrawable(0);
        }
    }

    public void setThemeType(int i) {
        if (this.mUserThemeType != i) {
            this.mUserThemeType = i;
            updateMaterialEffect();
        }
    }

    public void showDragHandleView() {
        View view;
        if (!this.mDragHandleViewEnabled || (view = this.mDragHandleContainerView) == null) {
            return;
        }
        view.setVisibility(0);
    }

    public void updateMaterialEffect() {
        if (this.mMaterial == null) {
            return;
        }
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true);
        if (hasThemeType()) {
            zResolveBoolean = this.mUserThemeType == 1;
        }
        MaterialConfig materialConfig = this.mMaterial.get(zResolveBoolean);
        this.mCurrentMaterial = materialConfig;
        if (materialConfig == null || !HyperMaterialUtils.isFeatureEnable(getContext())) {
            MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
            if (miuiBlurUiHelper != null) {
                miuiBlurUiHelper.applyBlur(false);
            }
            setEnableBlur(false);
            HyperBloomStrokeUtils.clearBloomStroke(this);
            return;
        }
        setEnableBlur(true);
        if (this.mBlurUiHelper != null && this.mCurrentMaterial.getBlurConfig() != null) {
            if (isApplyBlur() || !this.mBlurEnable) {
                this.mBlurUiHelper.onConfigChanged();
                this.mBlurUiHelper.refreshBlur();
            } else {
                this.mBlurUiHelper.onConfigChanged();
                applyBlur(this.mBlurEnable);
            }
        }
        if (this.mBlurEnable) {
            MaterialConfig.BloomStrokeConfig bloomStrokeConfig = this.mCurrentMaterial.getBloomStrokeConfig();
            if (bloomStrokeConfig != null) {
                HyperBloomStrokeUtils.setBloomStrokeConfig(this, bloomStrokeConfig);
            } else {
                HyperBloomStrokeUtils.clearBloomStroke(this);
            }
        }
    }
}

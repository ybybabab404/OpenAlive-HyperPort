package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import java.util.ArrayList;
import miuix.core.util.EnvStateManager;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.internal.util.ViewUtils;
import miuix.miuixbasewidget.R;
import miuix.miuixbasewidget.widget.internal.TabViewContainerView;
import miuix.os.DeviceHelper;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.theme.token.ShadowToken;
import miuix.util.HapticFeedbackCompat;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes2.dex */
public class FilterSortView2 extends HorizontalScrollView {
    public static final MaterialToken Filter_Sort_View2_Selected_Glass_Dark;
    public static final MaterialToken Filter_Sort_View2_Selected_Glass_Light;
    public static final MaterialDayNightToken Filter_Sort_View2_Selected_Glass_Material;
    public static final MaterialToken Filter_Sort_View2_Unselected_Glass_Dark;
    public static final MaterialToken Filter_Sort_View2_Unselected_Glass_Light;
    public static final MaterialDayNightToken Filter_Sort_View2_Unselected_Glass_Material;
    public final int mDeviceType;
    public boolean mEnabled;
    public int mFilteredId;
    public boolean mFilteredTabShadowEnabled;
    public boolean mIsParentApplyBlur;
    public int mLayoutConfig;
    public boolean mMaterialEnabled;
    public int mTabCount;
    public final ArrayList mTabViewChildIds;
    public TabViewContainerView mTabViewContainerView;
    public boolean mUnfilteredTabShadowBackgroundEnabled;
    public boolean mUnfilteredTabShadowEnabled;

    public static class TabView extends FrameLayout {
        public int mActivatedTextAppearanceId;
        public ImageView mArrow;
        public Drawable mArrowIcon;
        public boolean mDescending;
        public boolean mDescendingEnabled;
        public boolean mFiltered;
        public HapticFeedbackCompat mHapticFeedbackCompat;
        public int mIndicatorVisibility;
        public OnFilteredListener mOnFilteredListener;
        public int mOriginalBgAlpha;
        public ColorFilter mOriginalBgColorFilter;
        public boolean mOriginalBgColorFilterSaved;
        public int mTextAppearanceId;
        public TextView mTextView;

        public interface OnFilteredListener {
            void onFilteredChanged(TabView tabView, boolean z);
        }

        public static /* synthetic */ void $r8$lambda$M3hycZ3ErVLvbvmsBibJYio4Y9M(TabView tabView) {
            OnFilteredListener onFilteredListener = tabView.mOnFilteredListener;
            if (onFilteredListener != null) {
                onFilteredListener.onFilteredChanged(tabView, true);
            }
        }

        public static /* synthetic */ void $r8$lambda$kOztkJtwiaKbkpWO95G48q4mePE(TabView tabView, View.OnClickListener onClickListener, View view) {
            if (!tabView.mFiltered) {
                tabView.setFiltered(true);
            } else if (tabView.mDescendingEnabled) {
                tabView.setDescending(!tabView.mDescending);
            }
            onClickListener.onClick(view);
            if (HapticCompat.doesSupportHaptic("2.0")) {
                tabView.getHapticFeedbackCompat().performExtHapticFeedback(204);
            } else {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_MESH_NORMAL);
            }
        }

        public TabView(Context context) {
            this(context, null);
        }

        public TabView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.filterSortTabView2Style);
        }

        public TabView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.mDescendingEnabled = true;
            this.mOriginalBgAlpha = -1;
            LayoutInflater.from(context).inflate(getTabLayoutResource(), (ViewGroup) this, true);
            TextView textView = (TextView) findViewById(android.R.id.text1);
            this.mTextView = textView;
            textView.setMaxLines(1);
            this.mTextView.setEllipsize(TextUtils.TruncateAt.END);
            this.mArrow = (ImageView) findViewById(R.id.arrow);
            if (attributeSet != null) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FilterSortTabView2, i, R.style.Widget_FilterSortTabView2_DayNight);
                String string = typedArrayObtainStyledAttributes.getString(R.styleable.FilterSortTabView2_android_text);
                boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FilterSortTabView2_descending, true);
                this.mIndicatorVisibility = typedArrayObtainStyledAttributes.getInt(R.styleable.FilterSortTabView2_indicatorVisibility, 0);
                this.mArrowIcon = typedArrayObtainStyledAttributes.getDrawable(R.styleable.FilterSortTabView2_arrowFilterSortTabView);
                setBackground(typedArrayObtainStyledAttributes.getDrawable(R.styleable.FilterSortTabView2_filterSortTabViewBackground));
                setForeground(typedArrayObtainStyledAttributes.getDrawable(R.styleable.FilterSortTabView2_filterSortTabViewForeground));
                int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FilterSortTabView2_filterSortTabViewHorizontalPadding, R.dimen.miuix_appcompat_filter_sort_tab_view2_padding_horizontal);
                int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FilterSortTabView2_filterSortTabViewVerticalPadding, R.dimen.miuix_appcompat_filter_sort_tab_view2_padding_vertical);
                findViewById(R.id.container).setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
                this.mTextAppearanceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.FilterSortTabView2_filterSortTabViewTabTextAppearance, 0);
                this.mActivatedTextAppearanceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.FilterSortTabView2_filterSortTabViewTabActivatedTextAppearance, 0);
                typedArrayObtainStyledAttributes.recycle();
                initView(string, z);
            }
            if (getId() == -1) {
                setId(FrameLayout.generateViewId());
            }
            setImportantForAccessibility(1);
            ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: miuix.miuixbasewidget.widget.FilterSortView2.TabView.1
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                    accessibilityNodeInfoCompat.setSelected(view.isActivated());
                    TextView textView2 = (TextView) view.findViewById(android.R.id.text1);
                    if (textView2 != null && !TextUtils.isEmpty(textView2.getText())) {
                        accessibilityNodeInfoCompat.setContentDescription(textView2.getText());
                    }
                    if (view.isActivated()) {
                        accessibilityNodeInfoCompat.setClickable(false);
                        accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                    } else {
                        accessibilityNodeInfoCompat.setClickable(true);
                        accessibilityNodeInfoCompat.setStateDescription(TabView.this.getContext().getResources().getString(R.string.accessibility_tab_state_description_unselect));
                    }
                }
            });
        }

        private HapticFeedbackCompat getHapticFeedbackCompat() {
            if (this.mHapticFeedbackCompat == null) {
                this.mHapticFeedbackCompat = new HapticFeedbackCompat(getContext());
            }
            return this.mHapticFeedbackCompat;
        }

        private void setDescending(boolean z) {
            this.mDescending = z;
            if (z) {
                this.mArrow.setRotationX(0.0f);
            } else {
                this.mArrow.setRotationX(180.0f);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFiltered(boolean z) {
            TabView tabView;
            ViewGroup viewGroup = (ViewGroup) getParent();
            FilterSortView2 filterSortView2FindOwnerFilterSortView = findOwnerFilterSortView();
            if (z && viewGroup != null) {
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if ((childAt instanceof TabView) && (tabView = (TabView) childAt) != this && tabView.mFiltered) {
                        tabView.setFiltered(false);
                    }
                }
            }
            this.mFiltered = z;
            updateTextAppearance();
            this.mTextView.setActivated(z);
            this.mArrow.setActivated(z);
            setActivated(z);
            updateMaterialForFilteredChange(filterSortView2FindOwnerFilterSortView);
            if (filterSortView2FindOwnerFilterSortView != null) {
                filterSortView2FindOwnerFilterSortView.refreshAllTabShadows();
            }
            if (viewGroup != null && z) {
                viewGroup.post(new Runnable() { // from class: miuix.miuixbasewidget.widget.FilterSortView2$TabView$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilterSortView2.TabView.$r8$lambda$M3hycZ3ErVLvbvmsBibJYio4Y9M(this.f$0);
                    }
                });
            }
        }

        public final FilterSortView2 findOwnerFilterSortView() {
            ViewParent parent = getParent();
            while (parent != null && !(parent instanceof FilterSortView2)) {
                parent = parent.getParent();
            }
            if (parent instanceof FilterSortView2) {
                return (FilterSortView2) parent;
            }
            return null;
        }

        public View getArrowView() {
            return this.mArrow;
        }

        public boolean getDescendingEnabled() {
            return this.mDescendingEnabled;
        }

        public ImageView getIconView() {
            return this.mArrow;
        }

        public int getTabLayoutResource() {
            return R.layout.miuix_appcompat_filter_sort_tab_view_2;
        }

        public TextView getTextView() {
            return this.mTextView;
        }

        public void initView(CharSequence charSequence, boolean z) {
            this.mArrow.setBackground(this.mArrowIcon);
            this.mTextView.setText(charSequence);
            this.mArrow.setVisibility(this.mIndicatorVisibility);
            setDescending(z);
            updateTextAppearance();
        }

        public boolean isFiltered() {
            return this.mFiltered;
        }

        public void setActivatedTextAppearance(int i) {
            this.mActivatedTextAppearanceId = i;
            updateTextAppearance();
        }

        public void setDescendingEnabled(boolean z) {
            this.mDescendingEnabled = z;
        }

        @Override // android.view.View
        public void setEnabled(boolean z) {
            super.setEnabled(z);
            this.mTextView.setEnabled(z);
        }

        public void setIconView(ImageView imageView) {
            this.mArrow = imageView;
        }

        public void setIndicatorVisibility(int i) {
            this.mArrow.setVisibility(i);
        }

        @Override // android.view.View
        public void setOnClickListener(final View.OnClickListener onClickListener) {
            super.setOnClickListener(new View.OnClickListener() { // from class: miuix.miuixbasewidget.widget.FilterSortView2$TabView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterSortView2.TabView.$r8$lambda$kOztkJtwiaKbkpWO95G48q4mePE(this.f$0, onClickListener, view);
                }
            });
        }

        public void setOnFilteredListener(OnFilteredListener onFilteredListener) {
            this.mOnFilteredListener = onFilteredListener;
        }

        public void setTextAppearance(int i) {
            this.mTextAppearanceId = i;
            updateTextAppearance();
        }

        public void setTextView(TextView textView) {
            this.mTextView = textView;
        }

        public final void updateMaterialForFilteredChange(FilterSortView2 filterSortView2) {
            if (filterSortView2 != null && filterSortView2.mMaterialEnabled && HyperMaterialUtils.isFeatureEnable(getContext())) {
                filterSortView2.refreshMaterialEffects();
                filterSortView2.scheduleRefreshMaterialEffects();
            }
        }

        public final void updateTextAppearance() {
            if (this.mTextView != null) {
                if (isFiltered()) {
                    TextViewCompat.setTextAppearance(this.mTextView, this.mActivatedTextAppearanceId);
                } else {
                    TextViewCompat.setTextAppearance(this.mTextView, this.mTextAppearanceId);
                }
                requestLayout();
            }
        }
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(30, "filter-sort-view2-unselected-glass", "light").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Light).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Filter_Sort_View2_Unselected_Glass_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(30, "filter-sort-view2-unselected-glass", "dark").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Dark).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Filter_Sort_View2_Unselected_Glass_Dark = materialTokenBuild2;
        Filter_Sort_View2_Unselected_Glass_Material = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
        MaterialToken materialTokenBuild3 = new MaterialToken.Builder(30, "filter-sort-view2-selected-glass", "light").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Light).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Filter_Sort_View2_Selected_Glass_Light = materialTokenBuild3;
        MaterialToken materialTokenBuild4 = new MaterialToken.Builder(30, "filter-sort-view2-selected-glass", "dark").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Dark).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Filter_Sort_View2_Selected_Glass_Dark = materialTokenBuild4;
        Filter_Sort_View2_Selected_Glass_Material = new MaterialDayNightToken(materialTokenBuild3, materialTokenBuild4);
    }

    public FilterSortView2(Context context) {
        this(context, null);
    }

    public FilterSortView2(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.filterSortView2Style);
    }

    public FilterSortView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTabViewChildIds = new ArrayList();
        this.mFilteredId = -1;
        this.mIsParentApplyBlur = false;
        this.mTabCount = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FilterSortView2, i, R.style.Widget_FilterSortView2_DayNight);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.FilterSortView2_filterSortViewBackground);
        this.mEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FilterSortView2_android_enabled, true);
        this.mLayoutConfig = typedArrayObtainStyledAttributes.getInt(R.styleable.FilterSortView2_layoutConfig, 0);
        this.mUnfilteredTabShadowBackgroundEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FilterSortView2_unfilteredTabShadowBackgroundEnabled, true);
        typedArrayObtainStyledAttributes.recycle();
        initContentView();
        setBackground(drawable);
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mDeviceType = DeviceHelper.detectType(context);
        setOverScrollMode(2);
    }

    private int getUnfilteredTabShadowBackgroundColor() {
        return getResources().getColor(ViewUtils.isNightMode(getContext()) ? R.color.miuix_color_white_solid_40 : R.color.miuix_color_white_solid_100);
    }

    private void initContentView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        TabViewContainerView tabViewContainerView = new TabViewContainerView(getContext());
        this.mTabViewContainerView = tabViewContainerView;
        tabViewContainerView.setLayoutParams(layoutParams);
        this.mTabViewContainerView.setHorizontalScrollBarEnabled(false);
        addView(this.mTabViewContainerView);
    }

    private void refreshTabState() {
        for (int i = 0; i < this.mTabViewContainerView.getChildCount(); i++) {
            View childAt = this.mTabViewContainerView.getChildAt(i);
            if (childAt instanceof TabView) {
                ((TabView) childAt).setEnabled(this.mEnabled);
            }
        }
    }

    public final void addTab(TabView tabView, int i) {
        tabView.setEnabled(this.mEnabled);
        tabView.setSelected(this.mIsParentApplyBlur);
        addTabViewAt(tabView, i);
        this.mTabViewChildIds.add(Integer.valueOf(tabView.getId()));
    }

    public void addTabViewAt(TabView tabView, int i) {
        if (tabView != null) {
            if (i > this.mTabCount || i < 0) {
                this.mTabViewContainerView.addView(tabView, -1, new FrameLayout.LayoutParams(-2, -2));
            } else {
                this.mTabViewContainerView.addView(tabView, i, new FrameLayout.LayoutParams(-2, -2));
            }
            this.mTabCount++;
            applyShadowToTab(tabView);
            if (this.mMaterialEnabled) {
                applyMaterialToTabIfNeeded(tabView);
            }
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view) {
        addView(view, -1);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view, int i) {
        if (view == null) {
            throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        }
        addView(view, i, layoutParams);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (this.mTabViewContainerView == view) {
            super.addView(view, i, layoutParams);
        } else {
            checkView(view);
            addTab((TabView) view, i);
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addView(view, -1, (ViewGroup.LayoutParams) null);
    }

    public final void applyMaterialToTabIfNeeded(TabView tabView) {
        if (this.mMaterialEnabled && HyperMaterialUtils.isFeatureEnable(getContext())) {
            applyTabMaterial(tabView, tabView.mFiltered);
        }
    }

    public final void applyShadowToTab(TabView tabView) {
        if (tabView == null) {
            return;
        }
        if (!(tabView.mFiltered && this.mFilteredTabShadowEnabled) && (tabView.mFiltered || !this.mUnfilteredTabShadowEnabled)) {
            MiShadowUtils.clearMiShadow(tabView);
        } else {
            ShadowToken shadowToken = ShadowToken.Regular;
            MiShadowUtils.setMiShadow(tabView, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius);
            ViewParent parent = tabView.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                if (viewGroup.getClipToPadding() || viewGroup.getClipChildren()) {
                    disableParentClip(tabView);
                }
            }
        }
        applyUnfilteredTabShadowBackground(tabView);
    }

    public final void applyTabMaterial(TabView tabView, boolean z) {
        int i;
        boolean z2 = !ViewUtils.isNightMode(getContext());
        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(z ? Filter_Sort_View2_Selected_Glass_Material : Filter_Sort_View2_Unselected_Glass_Material);
        MaterialConfig materialConfig = materialDayNightConfigCreate != null ? materialDayNightConfigCreate.get(z2) : null;
        if (materialConfig == null) {
            return;
        }
        Drawable background = tabView.getBackground();
        if (background != null) {
            if (tabView.mOriginalBgAlpha < 0) {
                tabView.mOriginalBgAlpha = background.getAlpha();
            }
            if (z) {
                background.setAlpha(tabView.mOriginalBgAlpha);
            } else {
                background.setAlpha(0);
            }
        }
        float f = getResources().getDisplayMetrics().density;
        if (materialConfig.getBlurConfig() != null && (i = materialConfig.getBlurConfig().blurRadius) > 0) {
            MiuiBlurUtils.setBackgroundBlur(tabView, (int) ((i * f) + 0.5f));
        }
        MiuiBlurUtils.setViewBlurMode(tabView, 1);
        if (materialConfig.getColorBlendConfig() != null) {
            MiuiBlurUtils.setBackgroundBlendConfig(tabView, materialConfig.getColorBlendConfig().blendColors, materialConfig.getColorBlendConfig().blendModes);
        }
        if (materialConfig.getBloomStrokeConfig() != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(tabView, materialConfig.getBloomStrokeConfig());
        }
        if (materialConfig.getShadowConfig() != null) {
            MiShadowUtils.setShadowConfig(tabView, materialConfig.getShadowConfig());
            ViewParent parent = tabView.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                if (viewGroup.getClipToPadding() || viewGroup.getClipChildren()) {
                    disableParentClip(tabView);
                }
            }
        }
    }

    public final void applyUnfilteredTabShadowBackground(TabView tabView) {
        Drawable background = tabView.getBackground();
        if (background == null) {
            return;
        }
        if (!this.mUnfilteredTabShadowBackgroundEnabled || !this.mUnfilteredTabShadowEnabled || tabView.mFiltered) {
            background.setColorFilter(tabView.mOriginalBgColorFilterSaved ? tabView.mOriginalBgColorFilter : null);
            return;
        }
        if (!tabView.mOriginalBgColorFilterSaved) {
            tabView.mOriginalBgColorFilter = background.getColorFilter();
            tabView.mOriginalBgColorFilterSaved = true;
        }
        Drawable drawableMutate = background.mutate();
        if (drawableMutate != background) {
            tabView.setBackground(drawableMutate);
            background = drawableMutate;
        }
        background.setColorFilter(new PorterDuffColorFilter(getUnfilteredTabShadowBackgroundColor(), PorterDuff.Mode.SRC));
    }

    public boolean canScrollHorizontally() {
        View childAt = getChildAt(0);
        return childAt != null && childAt.getWidth() > getWidth();
    }

    public final void checkView(View view) {
        if (!(view instanceof TabView)) {
            throw new IllegalArgumentException("Illegal View! Only support TabView!");
        }
    }

    public final void clearTabMaterial(TabView tabView) {
        MiuiBlurUtils.clearBackgroundBlur(tabView);
        MiuiBlurUtils.setViewBlurMode(tabView, 0);
        MiuiBlurUtils.clearBackgroundBlendConfig(tabView);
        HyperBloomStrokeUtils.clearBloomStroke(tabView);
        MiShadowUtils.clearMiShadow(tabView);
        Drawable background = tabView.getBackground();
        if (background == null || tabView.mOriginalBgAlpha < 0) {
            return;
        }
        background.setAlpha(tabView.mOriginalBgAlpha);
    }

    public final void disableParentClip(View view) {
        int identifier = view.getResources().getIdentifier("action_bar_overlay_layout", "id", view.getContext().getPackageName());
        ViewParent parent = view.getParent();
        while (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            if (identifier != 0 && viewGroup.getId() == identifier) {
                return;
            } else {
                parent = viewGroup.getParent();
            }
        }
    }

    public boolean getEnabled() {
        return this.mEnabled;
    }

    public int getTabCount() {
        return this.mTabCount;
    }

    public TabView getTabViewAt(int i) {
        if (i <= -1) {
            return null;
        }
        View childAt = this.mTabViewContainerView.getChildAt((this.mTabViewContainerView.getChildCount() - this.mTabCount) + i);
        if (childAt instanceof TabView) {
            return (TabView) childAt;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        scheduleRefreshMaterialEffects();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mMaterialEnabled) {
            refreshMaterialEffects();
        }
        if (this.mUnfilteredTabShadowBackgroundEnabled) {
            refreshAllTabShadows();
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0054  */
    /* JADX WARN: Code duplicated, block: B:21:0x005a  */
    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        float f = getContext().getResources().getDisplayMetrics().density;
        int i3 = this.mLayoutConfig;
        int i4 = 2;
        if (i3 == 0) {
            int i5 = (int) ((size * 1.0f) / f);
            int i6 = (int) ((EnvStateManager.getWindowSize(getContext()).x * 1.0f) / f);
            if (this.mDeviceType != 2 || i5 <= 410 || i6 <= 600) {
                i4 = 0;
            } else {
                i4 = 1;
            }
        } else if (i3 == 1) {
            int i7 = (int) ((EnvStateManager.getWindowSize(getContext()).x * 1.0f) / f);
            if (this.mDeviceType != 2 || i7 <= 600) {
                i4 = 0;
            } else {
                i4 = 1;
            }
        } else if (i3 == 3) {
            i4 = 1;
        } else if (i3 != 4) {
            i4 = 0;
        }
        this.mTabViewContainerView.setTabViewLayoutMode(i4);
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            scheduleRefreshMaterialEffects();
        }
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0) {
            scheduleRefreshMaterialEffects();
        }
    }

    public void refreshAllTabShadows() {
        TabViewContainerView tabViewContainerView = this.mTabViewContainerView;
        if (tabViewContainerView == null) {
            return;
        }
        int childCount = tabViewContainerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mTabViewContainerView.getChildAt(i);
            if (childAt instanceof TabView) {
                applyShadowToTab((TabView) childAt);
            }
        }
    }

    public final void refreshMaterialEffects() {
        boolean z = this.mMaterialEnabled && HyperMaterialUtils.isFeatureEnable(getContext());
        TabViewContainerView tabViewContainerView = this.mTabViewContainerView;
        int childCount = tabViewContainerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = tabViewContainerView.getChildAt(i);
            if (childAt instanceof TabView) {
                TabView tabView = (TabView) childAt;
                if (z) {
                    applyTabMaterial(tabView, tabView.mFiltered);
                } else {
                    clearTabMaterial(tabView);
                }
            }
        }
    }

    public final void scheduleRefreshMaterialEffects() {
        if (this.mMaterialEnabled) {
            post(new Runnable() { // from class: miuix.miuixbasewidget.widget.FilterSortView2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.refreshMaterialEffects();
                }
            });
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (this.mEnabled != z) {
            this.mEnabled = z;
            refreshTabState();
        }
    }

    public void setFilteredTab(int i) {
        TabView tabViewAt = getTabViewAt(i);
        if (tabViewAt != null) {
            if (this.mFilteredId != tabViewAt.getId()) {
                this.mFilteredId = tabViewAt.getId();
            }
            tabViewAt.setFiltered(true);
        }
        updateChildIdsFromXml();
    }

    public void setFilteredTab(TabView tabView) {
        if (this.mFilteredId != tabView.getId()) {
            this.mFilteredId = tabView.getId();
        }
        tabView.setFiltered(true);
        updateChildIdsFromXml();
    }

    public void setFilteredTabShadowEnabled(boolean z) {
        if (this.mFilteredTabShadowEnabled != z) {
            this.mFilteredTabShadowEnabled = z;
            refreshAllTabShadows();
        }
    }

    public void setLayoutConfig(@FilterSortView2LayoutConfig int i) {
        if (this.mLayoutConfig != i) {
            this.mLayoutConfig = i;
            requestLayout();
        }
    }

    public void setMaterialEnabled(boolean z) {
        if (this.mMaterialEnabled != z) {
            this.mMaterialEnabled = z;
            refreshMaterialEffects();
        }
    }

    public void setParentApplyBlur(boolean z) {
        if (this.mIsParentApplyBlur != z) {
            this.mIsParentApplyBlur = z;
        }
        TabViewContainerView tabViewContainerView = this.mTabViewContainerView;
        if (tabViewContainerView != null) {
            int childCount = tabViewContainerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = tabViewContainerView.getChildAt(i);
                if (childAt instanceof TabView) {
                    ((TabView) childAt).setSelected(z);
                }
            }
        }
    }

    public void setTabIndicatorVisibility(int i) {
        for (int i2 = 0; i2 < this.mTabViewContainerView.getChildCount(); i2++) {
            View childAt = this.mTabViewContainerView.getChildAt(i2);
            if (childAt instanceof TabView) {
                ((TabView) childAt).setIndicatorVisibility(i);
            }
        }
    }

    public void setUnfilteredTabShadowBackgroundEnabled(boolean z) {
        if (this.mUnfilteredTabShadowBackgroundEnabled != z) {
            this.mUnfilteredTabShadowBackgroundEnabled = z;
            refreshAllTabShadows();
        }
    }

    public void setUnfilteredTabShadowEnabled(boolean z) {
        if (this.mUnfilteredTabShadowEnabled != z) {
            this.mUnfilteredTabShadowEnabled = z;
            refreshAllTabShadows();
        }
    }

    public void updateChildIdsFromXml() {
        if (this.mTabViewChildIds.isEmpty()) {
            int childCount = this.mTabViewContainerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.mTabViewContainerView.getChildAt(i);
                if (childAt instanceof TabView) {
                    this.mTabViewChildIds.add(Integer.valueOf(((TabView) childAt).getId()));
                }
            }
            requestLayout();
        }
    }
}

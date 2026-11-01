package miuix.miuixbasewidget.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import java.util.ArrayList;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.smooth.SmoothContainerDrawable2;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.theme.token.ShadowToken;
import miuix.util.HapticFeedbackCompat;
import miuix.view.CompatViewMethod;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes2.dex */
public class FilterSortView extends ConstraintLayout {
    public static final MaterialToken Filter_Sort_View_Glass_Dark;
    public static final MaterialToken Filter_Sort_View_Glass_Light;
    public static final MaterialDayNightToken Filter_Sort_View_Glass_Material;
    public int mActivePointerId;
    public Drawable mBackgroundBeforeMiShadow;
    public TabView mBackgroundTabView;
    public float mDownX;
    public float mDownY;
    public boolean mEnabled;
    public int mFilteredId;
    public boolean mFilteredUpdated;
    public boolean mFollowTouch;
    public boolean mMaterialEffectApplied;
    public boolean mMaterialEnabled;
    public boolean mMiShadowEnabled;
    public boolean mMiShadowFloatingBackgroundApplied;
    public final TabView.OnFilteredListener mOnFilteredListener;
    public final int mPadding;
    public IStateStyle mPillFolmeAnim;
    public final AnimConfig mPillPressConfig;
    public final AnimState mPillScaleState;
    public final int mPressScaleOffset;
    public float mSlideStartFingerX;
    public float mSlideStartPillCenter;
    public boolean mSliding;
    public final int mStrokeColor;
    public final int mStrokeWidth;
    public int mTabCount;
    public final int mTabHorizontalPadding;
    public final List mTabViewChildIds;
    public int mTouchSlop;

    public static class TabView extends LinearLayout {
        public ImageView mArrow;
        public Drawable mArrowIcon;
        public boolean mDescending;
        public boolean mDescendingEnabled;
        public boolean mFiltered;
        public HapticFeedbackCompat mHapticFeedbackCompat;
        public int mIndicatorVisibility;
        public OnFilteredListener mOnFilteredListener;
        public int mSelectedTextAppearanceId;
        public int mTextAppearanceId;
        public ColorStateList mTextColor;
        public TextView mTextView;
        public boolean mTouchActive;

        public interface OnFilteredListener {
            void cancelAnimation(View view);

            void onFilteredChanged(TabView tabView, boolean z, boolean z2);
        }

        /* JADX INFO: renamed from: $r8$lambda$-17PX1e8TU6OXiFNz0kjW7pDPRc, reason: not valid java name */
        public static /* synthetic */ boolean m1341$r8$lambda$17PX1e8TU6OXiFNz0kjW7pDPRc(View view, MotionEvent motionEvent) {
            if (motionEvent.getSource() != 4098) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 7 || actionMasked == 9) {
                    if (!view.isHovered()) {
                        view.setHovered(true);
                    }
                } else {
                    if (actionMasked != 10) {
                        return false;
                    }
                    if (view.isHovered()) {
                        view.setHovered(false);
                        return true;
                    }
                }
            } else if (view.isHovered()) {
                view.setHovered(false);
                return true;
            }
            return true;
        }

        public TabView(Context context) {
            this(context, null);
        }

        public TabView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public TabView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.mDescendingEnabled = true;
            int tabLayoutResource = getTabLayoutResource();
            LayoutInflater.from(context).inflate(tabLayoutResource, (ViewGroup) this, true);
            this.mTextView = (TextView) findViewById(R.id.text1);
            this.mArrow = (ImageView) findViewById(miuix.miuixbasewidget.R.id.arrow);
            this.mTextView.setImportantForAccessibility(2);
            this.mArrow.setImportantForAccessibility(2);
            if (tabLayoutResource == miuix.miuixbasewidget.R.layout.miuix_appcompat_filter_sort_tab_view) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, miuix.miuixbasewidget.R.styleable.FilterSortTabView, i, miuix.miuixbasewidget.R.style.Widget_FilterSortTabView_DayNight);
                String string = typedArrayObtainStyledAttributes.getString(miuix.miuixbasewidget.R.styleable.FilterSortTabView_android_text);
                boolean z = typedArrayObtainStyledAttributes.getBoolean(miuix.miuixbasewidget.R.styleable.FilterSortTabView_descending, true);
                this.mIndicatorVisibility = typedArrayObtainStyledAttributes.getInt(miuix.miuixbasewidget.R.styleable.FilterSortTabView_indicatorVisibility, 0);
                this.mArrowIcon = typedArrayObtainStyledAttributes.getDrawable(miuix.miuixbasewidget.R.styleable.FilterSortTabView_arrowFilterSortTabView);
                this.mTextColor = typedArrayObtainStyledAttributes.getColorStateList(miuix.miuixbasewidget.R.styleable.FilterSortTabView_filterSortTabViewTextColor);
                this.mTextAppearanceId = typedArrayObtainStyledAttributes.getResourceId(miuix.miuixbasewidget.R.styleable.FilterSortTabView_filterSortTabViewTabTextAppearance, 0);
                this.mSelectedTextAppearanceId = typedArrayObtainStyledAttributes.getResourceId(miuix.miuixbasewidget.R.styleable.FilterSortTabView_filterSortTabViewTabActivatedTextAppearance, 0);
                typedArrayObtainStyledAttributes.recycle();
                initView(string, z);
            }
            this.mArrow.setVisibility(this.mIndicatorVisibility);
            if (getId() == -1) {
                setId(LinearLayout.generateViewId());
            }
            ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: miuix.miuixbasewidget.widget.FilterSortView.TabView.1
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                    if (TabView.this.mTextView != null && !TextUtils.isEmpty(TabView.this.mTextView.getText())) {
                        accessibilityNodeInfoCompat.setContentDescription(TabView.this.mTextView.getText());
                    }
                    accessibilityNodeInfoCompat.setSelected(view.isSelected());
                    if (view.isSelected()) {
                        accessibilityNodeInfoCompat.setClickable(false);
                        accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                    } else {
                        accessibilityNodeInfoCompat.setClickable(true);
                        accessibilityNodeInfoCompat.setStateDescription(TabView.this.getContext().getResources().getString(miuix.miuixbasewidget.R.string.accessibility_tab_state_description_unselect));
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public HapticFeedbackCompat getHapticFeedbackCompat() {
            if (this.mHapticFeedbackCompat == null) {
                this.mHapticFeedbackCompat = new HapticFeedbackCompat(getContext());
            }
            return this.mHapticFeedbackCompat;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDescending(boolean z) {
            this.mDescending = z;
            if (z) {
                this.mArrow.setRotationX(0.0f);
            } else {
                this.mArrow.setRotationX(180.0f);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFiltered(boolean z) {
            setFiltered(z, true);
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
            return miuix.miuixbasewidget.R.layout.miuix_appcompat_filter_sort_tab_view;
        }

        public TextView getTextView() {
            return this.mTextView;
        }

        public void initView(CharSequence charSequence, boolean z) {
            updateTextAppearance(false);
            setGravity(17);
            if (getBackground() == null) {
                setBackground(parseBackground());
            }
            this.mArrow.setBackground(this.mArrowIcon);
            ColorStateList colorStateList = this.mTextColor;
            if (colorStateList != null) {
                this.mTextView.setTextColor(colorStateList);
            }
            this.mTextView.setText(charSequence);
            setDescending(z);
            setOnHoverListener(new View.OnHoverListener() { // from class: miuix.miuixbasewidget.widget.FilterSortView$TabView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    return FilterSortView.TabView.m1341$r8$lambda$17PX1e8TU6OXiFNz0kjW7pDPRc(view, motionEvent);
                }
            });
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            ViewParent parent = getParent();
            if (parent instanceof FilterSortView) {
                FilterSortView filterSortView = (FilterSortView) parent;
                TabView tabView = filterSortView.mBackgroundTabView;
                if (tabView != null && tabView != this) {
                    int actionMasked = motionEvent.getActionMasked();
                    if (actionMasked != 0) {
                        if (actionMasked == 1 || actionMasked == 3) {
                            this.mTouchActive = false;
                            if (!filterSortView.mSliding) {
                                filterSortView.pressOverlay(false);
                                tabView.setPressed(false);
                            }
                        }
                    } else if (isEnabled()) {
                        this.mTouchActive = true;
                        if (!this.mFiltered) {
                            filterSortView.movePillToVisually(this);
                        }
                        filterSortView.pressOverlay(true);
                        tabView.setPressed(true);
                    }
                }
            }
            return super.onTouchEvent(motionEvent);
        }

        public final Drawable parseBackground() {
            return getResources().getDrawable(miuix.miuixbasewidget.R.drawable.miuix_appcompat_filter_sort_tab_view_bg_normal);
        }

        public void setDescendingEnabled(boolean z) {
            this.mDescendingEnabled = z;
        }

        @Override // android.view.View
        public void setEnabled(boolean z) {
            super.setEnabled(z);
            this.mTextView.setEnabled(z);
        }

        public final void setFiltered(boolean z, boolean z2) {
            TabView tabView;
            if (isEnabled()) {
                FilterSortView filterSortView = (FilterSortView) getParent();
                if (z && filterSortView != null) {
                    int childCount = filterSortView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = filterSortView.getChildAt(i);
                        if ((childAt instanceof TabView) && (tabView = (TabView) childAt) != this && tabView.mFiltered) {
                            tabView.setFiltered(false);
                        }
                    }
                }
                this.mFiltered = z;
                updateTextAppearance(z);
                this.mTextView.setSelected(z);
                this.mArrow.setSelected(z);
                setSelected(z);
                OnFilteredListener onFilteredListener = this.mOnFilteredListener;
                if (onFilteredListener == null || !z) {
                    return;
                }
                onFilteredListener.onFilteredChanged(this, z, z2);
            }
        }

        public void setIconView(ImageView imageView) {
            this.mArrow = imageView;
        }

        public void setIndicatorVisibility(int i) {
            this.mArrow.setVisibility(i);
        }

        @Override // android.view.View
        public void setOnClickListener(final View.OnClickListener onClickListener) {
            super.setOnClickListener(new View.OnClickListener() { // from class: miuix.miuixbasewidget.widget.FilterSortView.TabView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (!TabView.this.mFiltered) {
                        TabView.this.setFiltered(true);
                    } else if (TabView.this.mDescendingEnabled) {
                        TabView tabView = TabView.this;
                        tabView.setDescending(true ^ tabView.mDescending);
                    }
                    onClickListener.onClick(view);
                    if (HapticCompat.doesSupportHaptic("2.0")) {
                        TabView.this.getHapticFeedbackCompat().performExtHapticFeedback(204);
                    } else {
                        HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_MESH_NORMAL);
                    }
                }
            });
        }

        public void setOnFilteredListener(OnFilteredListener onFilteredListener) {
            this.mOnFilteredListener = onFilteredListener;
        }

        @Override // android.view.View
        public void setPressed(boolean z) {
            TabView tabView;
            if (z || !this.mTouchActive) {
                super.setPressed(z);
                if (this.mFiltered) {
                    ViewParent parent = getParent();
                    if (!(parent instanceof FilterSortView) || (tabView = ((FilterSortView) parent).mBackgroundTabView) == null || tabView == this) {
                        return;
                    }
                    tabView.setPressed(z);
                }
            }
        }

        public void setTextView(TextView textView) {
            this.mTextView = textView;
        }

        public final void updateTextAppearance(boolean z) {
            TextView textView = this.mTextView;
            if (textView != null) {
                if (z) {
                    TextViewCompat.setTextAppearance(textView, this.mSelectedTextAppearanceId);
                } else {
                    TextViewCompat.setTextAppearance(textView, this.mTextAppearanceId);
                }
            }
        }
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(30, "filter-sort-view-glass", "light").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Light).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Filter_Sort_View_Glass_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(30, "filter-sort-view-glass", "dark").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Dark).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Filter_Sort_View_Glass_Dark = materialTokenBuild2;
        Filter_Sort_View_Glass_Material = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
    }

    public FilterSortView(Context context) {
        this(context, null);
    }

    public FilterSortView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FilterSortView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTabViewChildIds = new ArrayList();
        this.mFilteredId = -1;
        this.mEnabled = true;
        this.mFilteredUpdated = false;
        this.mTabCount = 0;
        this.mActivePointerId = -1;
        this.mPillPressConfig = new AnimConfig();
        this.mPillScaleState = new AnimState();
        this.mOnFilteredListener = new TabView.OnFilteredListener() { // from class: miuix.miuixbasewidget.widget.FilterSortView.1
            public BackgroundTabRunnable mRunnable;
            public IStateStyle mRunningAnimation;

            /* JADX INFO: renamed from: miuix.miuixbasewidget.widget.FilterSortView$1$BackgroundTabRunnable */
            public class BackgroundTabRunnable implements Runnable {
                public final boolean mAnimate;
                public TabView mBackgroundTabView;
                public boolean mCanceled = false;
                public IStateStyle mRunningAnimation;
                public TabView mSelectedTabView;

                public BackgroundTabRunnable(TabView tabView, TabView tabView2, boolean z) {
                    this.mBackgroundTabView = tabView;
                    this.mSelectedTabView = tabView2;
                    this.mAnimate = z;
                }

                public void cancelAnimation() {
                    this.mCanceled = true;
                    IStateStyle iStateStyle = this.mRunningAnimation;
                    if (iStateStyle != null) {
                        iStateStyle.cancel(ViewProperty.X, ViewProperty.WIDTH);
                    }
                    TabView tabView = this.mSelectedTabView;
                    if (tabView != null) {
                        tabView.removeCallbacks(this);
                    }
                    this.mBackgroundTabView = null;
                    this.mSelectedTabView = null;
                }

                @Override // java.lang.Runnable
                public void run() {
                    TabView tabView;
                    if (this.mCanceled || (tabView = this.mBackgroundTabView) == null || this.mSelectedTabView == null) {
                        return;
                    }
                    if (tabView.getVisibility() == 0) {
                        if (this.mAnimate) {
                            this.mRunningAnimation = Folme.useAt(this.mBackgroundTabView).state().to(new AnimState("target").add(ViewProperty.X, this.mSelectedTabView.getX()).add(ViewProperty.WIDTH, this.mSelectedTabView.getWidth()), new AnimConfig[0]);
                        } else {
                            FilterSortView.this.updateFiltered(this.mSelectedTabView);
                        }
                    }
                    FilterSortView.this.mFilteredId = this.mSelectedTabView.getId();
                }
            }

            @Override // miuix.miuixbasewidget.widget.FilterSortView.TabView.OnFilteredListener
            public void cancelAnimation(View view) {
                BackgroundTabRunnable backgroundTabRunnable = this.mRunnable;
                if (backgroundTabRunnable != null) {
                    backgroundTabRunnable.cancelAnimation();
                    view.removeCallbacks(this.mRunnable);
                    this.mRunnable = null;
                }
                IStateStyle iStateStyle = this.mRunningAnimation;
                if (iStateStyle != null) {
                    iStateStyle.cancel();
                    this.mRunningAnimation = null;
                }
            }

            @Override // miuix.miuixbasewidget.widget.FilterSortView.TabView.OnFilteredListener
            public void onFilteredChanged(TabView tabView, boolean z, boolean z2) {
                BackgroundTabRunnable backgroundTabRunnable = this.mRunnable;
                if (backgroundTabRunnable != null) {
                    backgroundTabRunnable.cancelAnimation();
                }
                if (z) {
                    FilterSortView.this.checkBackgroundTabViewAdded();
                    BackgroundTabRunnable backgroundTabRunnable2 = new BackgroundTabRunnable(FilterSortView.this.mBackgroundTabView, tabView, z2);
                    this.mRunnable = backgroundTabRunnable2;
                    tabView.post(backgroundTabRunnable2);
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, miuix.miuixbasewidget.R.styleable.FilterSortView, i, miuix.miuixbasewidget.R.style.Widget_FilterSortView_DayNight);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(miuix.miuixbasewidget.R.styleable.FilterSortView_filterSortViewBackground);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(miuix.miuixbasewidget.R.styleable.FilterSortView_filterSortTabViewCoverBg);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(miuix.miuixbasewidget.R.styleable.FilterSortView_filterSortViewStrokeEnabled, false);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(miuix.miuixbasewidget.R.styleable.FilterSortView_filterSortViewMiShadowEnabled, false);
        this.mStrokeWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(miuix.miuixbasewidget.R.styleable.FilterSortView_filterSortViewStrokeWidth, 0);
        this.mStrokeColor = typedArrayObtainStyledAttributes.getColor(miuix.miuixbasewidget.R.styleable.FilterSortView_filterSortViewStrokeColor, 0);
        this.mEnabled = typedArrayObtainStyledAttributes.getBoolean(miuix.miuixbasewidget.R.styleable.FilterSortView_android_enabled, true);
        this.mFollowTouch = typedArrayObtainStyledAttributes.getBoolean(miuix.miuixbasewidget.R.styleable.FilterSortView_filterSortTabViewFollowTouch, true);
        typedArrayObtainStyledAttributes.recycle();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mPadding = getResources().getDimensionPixelSize(miuix.miuixbasewidget.R.dimen.miuix_appcompat_filter_sort_view_padding);
        this.mTabHorizontalPadding = getResources().getDimensionPixelSize(miuix.miuixbasewidget.R.dimen.miuix_appcompat_filter_sort_tab_view_padding_horizontal);
        this.mPressScaleOffset = MiuixUIUtils.dp2px(context, 6.0f);
        setBackground(drawable);
        initCoverBg(drawable2);
        CompatViewMethod.setForceDarkAllowed(this, false);
        setStroke(z);
        setMiShadowEnabled(z2);
    }

    @ColorInt
    private int getMiShadowFloatingBackgroundColor() {
        return ContextCompat.getColor(getContext(), ViewUtils.isNightMode(getContext()) ? miuix.miuixbasewidget.R.color.miuix_color_white_solid_30 : miuix.miuixbasewidget.R.color.miuix_default_color_surface_high_light);
    }

    public TabView addTab(CharSequence charSequence) {
        return addTab(charSequence, -1, true);
    }

    public TabView addTab(CharSequence charSequence, int i, boolean z) {
        TabView tabViewInflateTabView = inflateTabView();
        addTab(tabViewInflateTabView, charSequence, i, z);
        return tabViewInflateTabView;
    }

    public void addTab(TabView tabView, CharSequence charSequence, int i, boolean z) {
        tabView.setOnFilteredListener(this.mOnFilteredListener);
        tabView.setEnabled(this.mEnabled);
        this.mFilteredUpdated = false;
        if (i < 0 || i > this.mTabCount) {
            addView(tabView, -1);
        } else {
            addView(tabView, i);
        }
        this.mTabCount++;
        this.mTabViewChildIds.add(Integer.valueOf(tabView.getId()));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        updateTabViews(constraintSet);
        constraintSet.applyTo(this);
        tabView.initView(charSequence, z);
        tabView.setPadding(this.mTabHorizontalPadding, tabView.getPaddingTop(), this.mTabHorizontalPadding, tabView.getPaddingBottom());
    }

    public final void applyFloatingBackgroundColor(Drawable drawable, int i) {
        if (!(drawable instanceof SmoothContainerDrawable2)) {
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setColor(i);
                return;
            } else {
                drawable.setTint(i);
                return;
            }
        }
        SmoothContainerDrawable2 smoothContainerDrawable2 = (SmoothContainerDrawable2) drawable;
        Drawable childDrawable = smoothContainerDrawable2.getChildDrawable();
        if (childDrawable instanceof GradientDrawable) {
            ((GradientDrawable) childDrawable).setColor(i);
            return;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(i);
        smoothContainerDrawable2.setChildDrawable(gradientDrawable);
    }

    public final void applyMaterialEffects() {
        int i;
        this.mMaterialEffectApplied = true;
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(0);
        }
        boolean z = !ViewUtils.isNightMode(getContext());
        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(Filter_Sort_View_Glass_Material);
        MaterialConfig materialConfig = materialDayNightConfigCreate != null ? materialDayNightConfigCreate.get(z) : null;
        if (materialConfig != null) {
            float f = getResources().getDisplayMetrics().density;
            if (materialConfig.getBlurConfig() != null && (i = materialConfig.getBlurConfig().blurRadius) > 0) {
                MiuiBlurUtils.setBackgroundBlur(this, (int) ((i * f) + 0.5f));
            }
            MiuiBlurUtils.setViewBlurMode(this, 1);
            if (materialConfig.getColorBlendConfig() != null) {
                MiuiBlurUtils.setBackgroundBlendConfig(this, materialConfig.getColorBlendConfig().blendColors, materialConfig.getColorBlendConfig().blendModes);
            }
            if (materialConfig.getBloomStrokeConfig() != null) {
                HyperBloomStrokeUtils.setBloomStrokeConfig(this, materialConfig.getBloomStrokeConfig());
            }
            if (materialConfig.getShadowConfig() != null) {
                MiShadowUtils.setShadowConfig(this, materialConfig.getShadowConfig());
            }
        }
    }

    public final void applyMiShadowEffect() {
        ShadowToken shadowToken = ShadowToken.Regular;
        MiShadowUtils.setMiShadow(this, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius);
    }

    public final void applyMiShadowFloatingBackground() {
        if (this.mMiShadowFloatingBackgroundApplied) {
            return;
        }
        Drawable background = getBackground();
        Drawable drawableCloneDrawable = cloneDrawable(background);
        this.mBackgroundBeforeMiShadow = drawableCloneDrawable;
        if (drawableCloneDrawable == null) {
            this.mBackgroundBeforeMiShadow = background;
        }
        Drawable drawableCloneDrawable2 = cloneDrawable(background);
        if (drawableCloneDrawable2 == null) {
            drawableCloneDrawable2 = createDefaultFloatingBackground();
        }
        applyFloatingBackgroundColor(drawableCloneDrawable2, getMiShadowFloatingBackgroundColor());
        drawableCloneDrawable2.setAlpha(255);
        setBackground(drawableCloneDrawable2);
        this.mMiShadowFloatingBackgroundApplied = true;
    }

    public final void applyMiShadowFloatingBackgroundIfNeeded() {
        if (!this.mMiShadowEnabled || this.mMaterialEffectApplied) {
            return;
        }
        applyMiShadowFloatingBackground();
    }

    public final void cancelSliding() {
        TabView tabView;
        if (this.mSliding) {
            this.mSliding = false;
            this.mActivePointerId = -1;
            pressOverlay(false);
            TabView tabView2 = this.mBackgroundTabView;
            if (tabView2 != null) {
                tabView2.setPressed(false);
            }
            int i = this.mFilteredId;
            if (i == -1 || (tabView = (TabView) findViewById(i)) == null) {
                return;
            }
            this.mOnFilteredListener.onFilteredChanged(tabView, true, true);
        }
    }

    public void checkBackgroundTabViewAdded() {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) == this.mBackgroundTabView) {
                return;
            }
        }
        addView(this.mBackgroundTabView, 0);
    }

    public final void clearMaterialEffects() {
        MiuiBlurUtils.clearBackgroundBlur(this);
        MiuiBlurUtils.setViewBlurMode(this, 0);
        MiuiBlurUtils.clearBackgroundBlendConfig(this);
        HyperBloomStrokeUtils.clearBloomStroke(this);
        MiShadowUtils.clearMiShadow(this);
        this.mMaterialEffectApplied = false;
        restoreBackgroundBeforeMiShadow();
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(255);
        }
        if (this.mMiShadowEnabled) {
            applyMiShadowEffect();
            applyMiShadowFloatingBackground();
        }
    }

    public final Drawable cloneDrawable(Drawable drawable) {
        if (drawable == null || drawable.getConstantState() == null) {
            return null;
        }
        return drawable.getConstantState().newDrawable(getResources()).mutate();
    }

    public final Drawable composeCoverBg(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        return new LayerDrawable(new Drawable[]{drawable, getResources().getDrawable(miuix.miuixbasewidget.R.drawable.miuix_appcompat_filter_sort_tab_view_fg_pressed_selector)});
    }

    public final Drawable createDefaultFloatingBackground() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(getResources().getDimensionPixelSize(miuix.miuixbasewidget.R.dimen.miuix_appcompat_filter_sort_view_corner));
        return gradientDrawable;
    }

    public final void disableParentClip() {
        int identifier = getResources().getIdentifier("action_bar_overlay_layout", "id", getContext().getPackageName());
        ViewParent parent = getParent();
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

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchSetPressed(boolean z) {
    }

    public boolean getEnabled() {
        return this.mEnabled;
    }

    public TabView.OnFilteredListener getOnFilteredListener() {
        return this.mOnFilteredListener;
    }

    public int getTabCount() {
        return this.mTabCount;
    }

    public TabView getTabViewAt(int i) {
        if (i <= -1) {
            return null;
        }
        View childAt = getChildAt((getChildCount() - this.mTabCount) + i);
        if (childAt instanceof TabView) {
            return (TabView) childAt;
        }
        return null;
    }

    public final TabView inflateTabView() {
        return (TabView) LayoutInflater.from(getContext()).inflate(miuix.miuixbasewidget.R.layout.layout_filter_tab_view, (ViewGroup) null);
    }

    public final void initCoverBg(Drawable drawable) {
        TabView tabViewInflateTabView = inflateTabView();
        this.mBackgroundTabView = tabViewInflateTabView;
        tabViewInflateTabView.setBackground(composeCoverBg(drawable));
        this.mBackgroundTabView.mArrow.setVisibility(8);
        this.mBackgroundTabView.mTextView.setVisibility(8);
        this.mBackgroundTabView.setVisibility(4);
        this.mBackgroundTabView.setEnabled(this.mEnabled);
        this.mBackgroundTabView.setImportantForAccessibility(2);
        addView(this.mBackgroundTabView);
    }

    public void movePillToVisually(TabView tabView) {
        TabView tabView2 = this.mBackgroundTabView;
        if (tabView2 == null || tabView2 == tabView || tabView2.getVisibility() != 0) {
            return;
        }
        if (this.mPillFolmeAnim == null) {
            this.mPillFolmeAnim = Folme.use((View) this.mBackgroundTabView).state();
        }
        this.mPillFolmeAnim.to(new AnimState("press-move").add(ViewProperty.X, tabView.getX()).add(ViewProperty.WIDTH, tabView.getWidth()), new AnimConfig[0]);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mFilteredUpdated = false;
        if (!this.mMaterialEnabled) {
            if (this.mMiShadowFloatingBackgroundApplied) {
                applyFloatingBackgroundColor(getBackground(), getMiShadowFloatingBackgroundColor());
            }
        } else if (!HyperMaterialUtils.isFeatureEnable(getContext())) {
            clearMaterialEffects();
        } else {
            restoreBackgroundBeforeMiShadow();
            applyMaterialEffects();
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00cb  */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        TabView tabView;
        int iFindPointerIndex;
        if (!this.mFollowTouch || !isEnabled() || this.mTabCount < 2 || this.mFilteredId == -1 || (tabView = this.mBackgroundTabView) == null || tabView.getVisibility() != 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDownX = motionEvent.getX();
            this.mDownY = motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (actionMasked == 1) {
            this.mActivePointerId = -1;
        } else if (actionMasked == 2) {
            int i = this.mActivePointerId;
            if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) >= 0) {
                float x = motionEvent.getX(iFindPointerIndex) - this.mDownX;
                float y = motionEvent.getY(iFindPointerIndex) - this.mDownY;
                if (Math.abs(x) > this.mTouchSlop && Math.abs(x) > Math.abs(y)) {
                    this.mOnFilteredListener.cancelAnimation(this);
                    IStateStyle iStateStyle = this.mPillFolmeAnim;
                    if (iStateStyle != null) {
                        iStateStyle.cancel(ViewProperty.X, ViewProperty.WIDTH);
                    }
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    this.mSliding = true;
                    this.mSlideStartFingerX = motionEvent.getX(iFindPointerIndex);
                    this.mSlideStartPillCenter = this.mBackgroundTabView.getX() + (this.mBackgroundTabView.getWidth() / 2.0f);
                    pressOverlay(true);
                    this.mBackgroundTabView.setPressed(true);
                    return true;
                }
                if (Math.abs(y) > this.mTouchSlop && Math.abs(y) > Math.abs(x)) {
                    this.mDownX = motionEvent.getX(iFindPointerIndex);
                    this.mDownY = motionEvent.getY(iFindPointerIndex);
                }
            }
        } else if (actionMasked == 3) {
            this.mActivePointerId = -1;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        TabView tabView;
        super.onLayout(z, i, i2, i3, i4);
        if (this.mBackgroundTabView.getVisibility() != 8) {
            int left = this.mBackgroundTabView.getLeft();
            int i5 = this.mPadding;
            this.mBackgroundTabView.layout(left, i5, this.mBackgroundTabView.getMeasuredWidth() + left, this.mBackgroundTabView.getMeasuredHeight() + i5);
        }
        int i6 = this.mFilteredId;
        if (i6 != -1 && !this.mFilteredUpdated && !this.mSliding && (tabView = (TabView) findViewById(i6)) != null) {
            updateFiltered(tabView);
            if (tabView.getWidth() > 0) {
                this.mFilteredUpdated = true;
            }
        }
        if (this.mMiShadowEnabled || this.mMaterialEnabled) {
            disableParentClip();
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        TabView tabView;
        int i3;
        super.onMeasure(i, i2);
        int measuredWidth = -1;
        if (this.mFilteredId == -1 || this.mBackgroundTabView.getVisibility() == 8) {
            return;
        }
        if (this.mSliding && (i3 = this.mBackgroundTabView.getLayoutParams().width) > 0) {
            measuredWidth = i3;
        }
        if (measuredWidth <= 0 && (tabView = (TabView) findViewById(this.mFilteredId)) != null) {
            measuredWidth = tabView.getMeasuredWidth();
        }
        if (measuredWidth > 0) {
            this.mBackgroundTabView.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() - (this.mPadding * 2), 1073741824));
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mFilteredUpdated = false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mSliding) {
            return super.onTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            float x = this.mBackgroundTabView.getX() + (this.mBackgroundTabView.getWidth() / 2.0f);
            pressOverlay(false);
            this.mBackgroundTabView.setPressed(false);
            snapToNearestTab(x);
            this.mSliding = false;
            this.mActivePointerId = -1;
            return true;
        }
        if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (iFindPointerIndex < 0) {
                return true;
            }
            updatePillByFingerX(motionEvent.getX(iFindPointerIndex));
            return true;
        }
        if (actionMasked == 3) {
            cancelSliding();
            return true;
        }
        if (actionMasked != 6) {
            return true;
        }
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            if (i < motionEvent.getPointerCount()) {
                this.mActivePointerId = motionEvent.getPointerId(i);
                this.mDownX = motionEvent.getX(i);
                this.mDownY = motionEvent.getY(i);
            }
        }
        return true;
    }

    public void pressOverlay(boolean z) {
        TabView tabView = this.mBackgroundTabView;
        if (tabView == null) {
            return;
        }
        int width = tabView.getWidth();
        int height = this.mBackgroundTabView.getHeight();
        if (width == 0 || height == 0) {
            return;
        }
        if (this.mPillFolmeAnim == null) {
            this.mPillFolmeAnim = Folme.use((View) this.mBackgroundTabView).state();
        }
        float fMax = z ? Math.max(0.5f, (width - this.mPressScaleOffset) / width) : 1.0f;
        float fMax2 = z ? Math.max(0.5f, (height - this.mPressScaleOffset) / height) : 1.0f;
        this.mPillScaleState.clear();
        this.mPillScaleState.add(ViewProperty.SCALE_X, fMax).add(ViewProperty.SCALE_Y, fMax2);
        this.mPillFolmeAnim.to(this.mPillScaleState, this.mPillPressConfig);
    }

    public final void refreshTabState() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof TabView) {
                ((TabView) childAt).setEnabled(this.mEnabled);
            }
        }
    }

    public final void restoreBackgroundBeforeMiShadow() {
        if (this.mMiShadowFloatingBackgroundApplied) {
            setBackground(this.mBackgroundBeforeMiShadow);
            this.mBackgroundBeforeMiShadow = null;
            this.mMiShadowFloatingBackgroundApplied = false;
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
            setFilteredTab(tabViewAt);
        }
    }

    public void setFilteredTab(TabView tabView) {
        if (tabView == null || !tabView.isEnabled()) {
            return;
        }
        if (this.mFilteredId != tabView.getId()) {
            this.mFilteredId = tabView.getId();
        }
        tabView.setFiltered(true);
        updateChildIdsFromXml();
    }

    public void setFilteredUpdated(boolean z) {
        this.mFilteredUpdated = z;
    }

    public void setFollowTouch(boolean z) {
        if (this.mFollowTouch == z) {
            return;
        }
        this.mFollowTouch = z;
        if (z) {
            return;
        }
        cancelSliding();
    }

    public void setMaterialEnabled(boolean z) {
        if (this.mMaterialEnabled != z) {
            this.mMaterialEnabled = z;
            if (!z) {
                clearMaterialEffects();
            } else if (HyperMaterialUtils.isFeatureEnable(getContext())) {
                restoreBackgroundBeforeMiShadow();
                applyMaterialEffects();
            } else {
                this.mMaterialEffectApplied = false;
                applyMiShadowFloatingBackgroundIfNeeded();
            }
        }
    }

    public void setMiShadowEnabled(boolean z) {
        if (this.mMiShadowEnabled == z) {
            return;
        }
        this.mMiShadowEnabled = z;
        if (z) {
            applyMiShadowEffect();
            applyMiShadowFloatingBackgroundIfNeeded();
        } else {
            MiShadowUtils.clearMiShadow(this);
            restoreBackgroundBeforeMiShadow();
        }
    }

    public void setStroke(boolean z) {
        setStroke(z, this.mStrokeWidth);
    }

    public void setStroke(boolean z, int i) {
        setStroke(z, i, this.mStrokeColor);
    }

    public void setStroke(boolean z, int i, @ColorInt int i2) {
        Drawable background = getBackground();
        if (!z) {
            i = 0;
        }
        if (!z) {
            i2 = 0;
        }
        if (background instanceof SmoothContainerDrawable2) {
            SmoothContainerDrawable2 smoothContainerDrawable2 = (SmoothContainerDrawable2) background;
            smoothContainerDrawable2.setStrokeWidth(i);
            smoothContainerDrawable2.setStrokeColor(i2);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setStroke(i, i2);
        }
    }

    public void setTabIncatorVisibility(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof TabView) {
                ((TabView) childAt).setIndicatorVisibility(i);
            }
        }
    }

    public final void snapToNearestTab(float f) {
        TabView tabView = null;
        float f2 = Float.MAX_VALUE;
        for (int i = 0; i < this.mTabCount; i++) {
            TabView tabViewAt = getTabViewAt(i);
            if (tabViewAt != null && tabViewAt.isEnabled()) {
                float fAbs = Math.abs(f - (tabViewAt.getX() + (tabViewAt.getWidth() / 2.0f)));
                if (fAbs < f2) {
                    tabView = tabViewAt;
                    f2 = fAbs;
                }
            }
        }
        if (tabView == null) {
            return;
        }
        if (tabView.getId() == this.mFilteredId) {
            this.mOnFilteredListener.onFilteredChanged(tabView, true, true);
        } else {
            tabView.performClick();
        }
    }

    public void updateChildIdsFromXml() {
        if (this.mTabViewChildIds.size() == 0) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof TabView) {
                    TabView tabView = (TabView) childAt;
                    if (tabView.getId() != this.mBackgroundTabView.getId()) {
                        tabView.setOnFilteredListener(this.mOnFilteredListener);
                        this.mTabViewChildIds.add(Integer.valueOf(tabView.getId()));
                        this.mTabCount++;
                        tabView.setPadding(this.mTabHorizontalPadding, tabView.getPaddingTop(), this.mTabHorizontalPadding, tabView.getPaddingBottom());
                    }
                }
            }
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            updateTabViews(constraintSet);
            constraintSet.applyTo(this);
        }
    }

    public final void updateFiltered(TabView tabView) {
        this.mOnFilteredListener.cancelAnimation(tabView);
        if (this.mBackgroundTabView.getVisibility() != 0) {
            this.mBackgroundTabView.setVisibility(0);
        }
        final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mBackgroundTabView.getLayoutParams();
        this.mBackgroundTabView.setX(tabView.getX());
        this.mBackgroundTabView.setY(this.mPadding);
        post(new Runnable() { // from class: miuix.miuixbasewidget.widget.FilterSortView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mBackgroundTabView.setLayoutParams(layoutParams);
            }
        });
    }

    public final void updatePillByFingerX(float f) {
        if (this.mTabCount < 2) {
            return;
        }
        int i = 0;
        TabView tabViewAt = getTabViewAt(0);
        TabView tabViewAt2 = getTabViewAt(this.mTabCount - 1);
        if (tabViewAt == null || tabViewAt2 == null) {
            return;
        }
        float fMax = Math.max(tabViewAt.getX() + (tabViewAt.getWidth() / 2.0f), Math.min(tabViewAt2.getX() + (tabViewAt2.getWidth() / 2.0f), this.mSlideStartPillCenter + (f - this.mSlideStartFingerX)));
        while (i < this.mTabCount - 1) {
            TabView tabViewAt3 = getTabViewAt(i);
            i++;
            TabView tabViewAt4 = getTabViewAt(i);
            if (tabViewAt3 != null && tabViewAt4 != null) {
                float x = tabViewAt3.getX() + (tabViewAt3.getWidth() / 2.0f);
                float x2 = tabViewAt4.getX() + (tabViewAt4.getWidth() / 2.0f);
                if (fMax >= x && fMax <= x2) {
                    tabViewAt = tabViewAt3;
                    tabViewAt2 = tabViewAt4;
                    break;
                }
            }
        }
        float x3 = tabViewAt.getX() + (tabViewAt.getWidth() / 2.0f);
        float x4 = (tabViewAt2.getX() + (tabViewAt2.getWidth() / 2.0f)) - x3;
        float width = tabViewAt.getWidth() + ((x4 > 0.0f ? (fMax - x3) / x4 : 0.0f) * (tabViewAt2.getWidth() - tabViewAt.getWidth()));
        float f2 = fMax - (width / 2.0f);
        ViewGroup.LayoutParams layoutParams = this.mBackgroundTabView.getLayoutParams();
        int iRound = Math.round(width);
        if (layoutParams.width != iRound) {
            layoutParams.width = iRound;
            this.mBackgroundTabView.setLayoutParams(layoutParams);
        }
        this.mBackgroundTabView.setX(f2);
    }

    public void updateTabViews(ConstraintSet constraintSet) {
        int i = 0;
        while (i < this.mTabViewChildIds.size()) {
            int iIntValue = ((Integer) this.mTabViewChildIds.get(i)).intValue();
            constraintSet.constrainWidth(iIntValue, 0);
            constraintSet.constrainHeight(iIntValue, -2);
            constraintSet.setHorizontalWeight(iIntValue, 1.0f);
            int iIntValue2 = i == 0 ? 0 : ((Integer) this.mTabViewChildIds.get(i - 1)).intValue();
            int iIntValue3 = i == this.mTabViewChildIds.size() + (-1) ? 0 : ((Integer) this.mTabViewChildIds.get(i + 1)).intValue();
            constraintSet.centerVertically(iIntValue, 0);
            ConstraintSet constraintSet2 = constraintSet;
            constraintSet2.connect(iIntValue, 6, iIntValue2, iIntValue2 == 0 ? 6 : 7, iIntValue2 == 0 ? this.mPadding : 0);
            constraintSet2.connect(iIntValue, 7, iIntValue3, iIntValue3 == 0 ? 7 : 6, iIntValue3 == 0 ? this.mPadding : 0);
            constraintSet2.connect(iIntValue, 3, 0, 3, this.mPadding);
            constraintSet2.connect(iIntValue, 4, 0, 4, this.mPadding);
            i++;
            constraintSet = constraintSet2;
        }
    }
}

package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import miuix.appcompat.R;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.internal.util.DeviceHelper;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes2.dex */
public class EndActionMenuView extends ActionMenuView {
    public int mActionCount;
    public Context mContext;
    public boolean mFloatingMode;
    public boolean mIsLightTheme;
    public boolean mMaterialEnabled;
    public int mMaxActionButtonWidth;
    public int mMenuItemGap;
    public int mMenuItemHeight;
    public int mMenuItemWidth;
    public int mStartPadding;

    public EndActionMenuView(Context context) {
        this(context, null);
    }

    public EndActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxActionButtonWidth = 0;
        this.mMenuItemGap = 0;
        this.mStartPadding = 0;
        this.mActionCount = 0;
        super.setBackground(null);
        this.mContext = context;
        this.mMenuItemGap = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_end_menu_button_gap);
        this.mStartPadding = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_bar_title_view_padding_horizontal);
        this.mMaxActionButtonWidth = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_button_max_width);
    }

    private int getActionMenuItemCount() {
        return getChildCount();
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.appcompat.internal.view.menu.MenuView
    public boolean filterLeftoverView(int i) {
        View childAt = getChildAt(i);
        if (isNotActionMenuItemChild(childAt)) {
            return false;
        }
        ActionMenuView.LayoutParams layoutParams = (ActionMenuView.LayoutParams) childAt.getLayoutParams();
        return (layoutParams == null || !layoutParams.isOverflowButton) && super.filterLeftoverView(i);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public ActionMenuView.LayoutParams generateOverflowButtonLayoutParams(View view) {
        ActionMenuView.LayoutParams layoutParamsGenerateLayoutParams = generateLayoutParams(view.getLayoutParams());
        layoutParamsGenerateLayoutParams.isOverflowButton = true;
        return layoutParamsGenerateLayoutParams;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public int getCollapsedHeight() {
        return this.mMenuItemHeight;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialConfig getCurrentMaterial() {
        return super.getCurrentMaterial();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialDayNightConfig getMaterial() {
        return super.getMaterial();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.appcompat.internal.view.menu.MenuView
    public boolean hasBackgroundView() {
        return false;
    }

    public final boolean isNotActionMenuItemChild(View view) {
        return false;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i4 - i2;
        int childCount = getChildCount();
        int measuredWidth = this.mStartPadding;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (!isNotActionMenuItemChild(childAt)) {
                ViewUtils.layoutChildView(this, childAt, measuredWidth, 0, measuredWidth + childAt.getMeasuredWidth(), i5);
                measuredWidth += childAt.getMeasuredWidth() + this.mMenuItemGap;
            }
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        EndActionMenuView endActionMenuView;
        int i3;
        int childCount = getChildCount();
        int actionMenuItemCount = getActionMenuItemCount();
        this.mActionCount = actionMenuItemCount;
        if (childCount == 0 || actionMenuItemCount == 0) {
            this.mMenuItemHeight = 0;
            setMeasuredDimension(0, 0);
            return;
        }
        int size = View.MeasureSpec.getSize(i);
        int iMin = Math.min(size / this.mActionCount, this.mMaxActionButtonWidth);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, Integer.MIN_VALUE);
        int i4 = 0;
        int iMin2 = 0;
        int iMax = 0;
        while (i4 < childCount) {
            View childAt = this.getChildAt(i4);
            if (this.isNotActionMenuItemChild(childAt)) {
                endActionMenuView = this;
                i3 = i2;
            } else {
                endActionMenuView = this;
                i3 = i2;
                endActionMenuView.measureChildWithMargins(childAt, iMakeMeasureSpec, 0, i3, 0);
                iMin2 += Math.min(childAt.getMeasuredWidth(), iMin);
                iMax = Math.max(iMax, childAt.getMeasuredHeight());
            }
            i4++;
            this = endActionMenuView;
            i2 = i3;
        }
        EndActionMenuView endActionMenuView2 = this;
        int i5 = endActionMenuView2.mMenuItemGap * (endActionMenuView2.mActionCount - 1);
        int i6 = endActionMenuView2.mStartPadding;
        if (i6 + iMin2 + i5 > size) {
            endActionMenuView2.mMenuItemGap = 0;
        }
        int i7 = iMin2 + i5 + i6;
        endActionMenuView2.mMenuItemWidth = i7;
        endActionMenuView2.mMenuItemHeight = iMax;
        endActionMenuView2.setMeasuredDimension(i7, iMax);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
        if (DeviceHelper.isFeatureWholeAnim()) {
            setAlpha(computeAlpha(f, z, z2));
        }
        float fComputeTranslationY = computeTranslationY(f, z, z2);
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (!isNotActionMenuItemChild(childAt)) {
                childAt.setTranslationY(fComputeTranslationY);
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof EndActionMenuItemView) {
            ((EndActionMenuItemView) view).setFloatingMode(this.mFloatingMode, this.mIsLightTheme, this.mMaterialEnabled);
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void setEnableBlur(boolean z) {
    }

    public void setFloatingMode(boolean z, boolean z2) {
        setFloatingMode(z, z2, false);
    }

    public void setFloatingMode(boolean z, boolean z2, boolean z3) {
        this.mFloatingMode = z;
        this.mIsLightTheme = z2;
        this.mMaterialEnabled = z3;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof EndActionMenuItemView) && !childAt.isSelected()) {
                ((EndActionMenuItemView) childAt).setFloatingMode(z, z2, z3);
            }
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialConfig materialConfig) {
        super.setMaterial(materialConfig);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        super.setMaterial(materialDayNightConfig);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void setSupportBlur(boolean z) {
    }
}

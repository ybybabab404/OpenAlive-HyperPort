package miuix.miuixbasewidget.widget.internal;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.miuixbasewidget.R;

/* JADX INFO: loaded from: classes2.dex */
public class TabViewContainerView extends FrameLayout {
    public int mChildrenTotalWidth;
    public int mDensityDpi;
    public int mGapBetweenTabs;
    public boolean mLayoutCenter;
    public int mLayoutMode;
    public final List mOverSizeViews;
    public final List mSmallSizeViews;
    public int mSpaciousLessThanTwoItemMinWidth;
    public int mSpaciousMoreThanFourItemMinWidth;
    public int mSpaciousThreeItemMinWidth;
    public int mVerticalPaddingBottom;
    public int mVerticalPaddingTop;

    public TabViewContainerView(Context context) {
        this(context, null);
    }

    public TabViewContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TabViewContainerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TabViewContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutCenter = false;
        this.mLayoutMode = 0;
        this.mOverSizeViews = new ArrayList();
        this.mSmallSizeViews = new ArrayList();
        updateLayoutParams();
    }

    public final boolean isViewGone(View view) {
        return view.getVisibility() == 8;
    }

    public final void measureByCompatMode(int i, int i2, int i3) {
        this.mOverSizeViews.clear();
        this.mSmallSizeViews.clear();
        int childCount = getChildCount();
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            getChildAt(i5).setMinimumWidth(0);
        }
        super.onMeasure(i, i2);
        int paddingStart = getPaddingStart() + getPaddingEnd();
        int i6 = i3 > 1 ? (i3 - 1) * this.mGapBetweenTabs : 0;
        int size = View.MeasureSpec.getSize(i);
        int i7 = (size - paddingStart) - i6;
        int i8 = i7 / i3;
        int i9 = i7 % i3;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (i10 < childCount) {
            View childAt = getChildAt(i10);
            childAt.setMinimumWidth(i4);
            if (!isViewGone(childAt)) {
                int measuredWidth = childAt.getMeasuredWidth();
                i11 += measuredWidth;
                if (measuredWidth > i8) {
                    this.mOverSizeViews.add(childAt);
                    i13 += measuredWidth;
                } else {
                    this.mSmallSizeViews.add(childAt);
                    i12 += measuredWidth;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
            }
            i10++;
            i4 = 0;
        }
        int measuredHeight = getMeasuredHeight() + this.mVerticalPaddingTop + this.mVerticalPaddingBottom;
        if (i11 > i7) {
            setMeasuredDimension(i11 + i6 + paddingStart, measuredHeight);
            return;
        }
        if (this.mOverSizeViews.isEmpty()) {
            int i14 = 0;
            while (i14 < childCount) {
                View childAt2 = getChildAt(i14);
                if (!isViewGone(childAt2)) {
                    childAt2.measure(View.MeasureSpec.makeMeasureSpec((i14 < i9 ? 1 : 0) + i8, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), 1073741824));
                }
                i14++;
            }
        } else if (i12 > 0) {
            int size2 = this.mSmallSizeViews.size();
            int i15 = i7 - i13;
            for (int i16 = 0; i16 < size2; i16++) {
                View view = (View) this.mSmallSizeViews.get(i16);
                int measuredWidth2 = (int) (((view.getMeasuredWidth() * 1.0f) / i12) * i15);
                if (!isViewGone(view)) {
                    view.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824), View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 1073741824));
                }
            }
        }
        setMeasuredDimension(size, measuredHeight);
    }

    public final boolean measureByWideMode(int i, int i2, int i3) {
        int paddingStart = getPaddingStart() + getPaddingEnd();
        int i4 = i3 > 1 ? (i3 - 1) * this.mGapBetweenTabs : 0;
        int size = View.MeasureSpec.getSize(i);
        int i5 = (size - paddingStart) - i4;
        int childCount = getChildCount();
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (i3 <= 2) {
                childAt.setMinimumWidth(this.mSpaciousLessThanTwoItemMinWidth);
                i6 = this.mSpaciousLessThanTwoItemMinWidth;
            } else if (i3 == 3) {
                childAt.setMinimumWidth(this.mSpaciousThreeItemMinWidth);
                i6 = this.mSpaciousThreeItemMinWidth;
            } else {
                childAt.setMinimumWidth(this.mSpaciousMoreThanFourItemMinWidth);
                i6 = this.mSpaciousMoreThanFourItemMinWidth;
            }
        }
        super.onMeasure(i, i2);
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt2 = getChildAt(i9);
            if (!isViewGone(childAt2)) {
                int measuredWidth = childAt2.getMeasuredWidth();
                i8 += measuredWidth;
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
            }
        }
        this.mChildrenTotalWidth = i4 + i8;
        setMeasuredDimension(size, getMeasuredHeight() + this.mVerticalPaddingTop + this.mVerticalPaddingBottom);
        return i8 >= i5 - i6;
    }

    public final void measureByWrapMode(int i, int i2, int i3) {
        int i4 = i3 > 1 ? (i3 - 1) * this.mGapBetweenTabs : 0;
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (!isViewGone(childAt)) {
                int measuredWidth = childAt.getMeasuredWidth();
                i5 += measuredWidth;
                childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
            }
        }
        setMeasuredDimension(getPaddingStart() + getPaddingEnd() + i5 + i4, getMeasuredHeight() + this.mVerticalPaddingTop + this.mVerticalPaddingBottom);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = configuration.densityDpi;
        if (i != this.mDensityDpi) {
            this.mDensityDpi = i;
            updateLayoutParams();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        TabViewContainerView tabViewContainerView;
        int i5 = i3 - i;
        int childCount = getChildCount();
        int i6 = this.mVerticalPaddingTop;
        int i7 = 0;
        int paddingStart = this.mLayoutCenter ? getPaddingStart() + ((i5 - this.mChildrenTotalWidth) / 2) : getPaddingStart();
        while (i7 < childCount) {
            View childAt = this.getChildAt(i7);
            if (this.isViewGone(childAt)) {
                tabViewContainerView = this;
            } else {
                int measuredWidth = paddingStart + childAt.getMeasuredWidth();
                tabViewContainerView = this;
                ViewUtils.layoutChildView(tabViewContainerView, childAt, paddingStart, i6, measuredWidth, i6 + childAt.getMeasuredHeight());
                paddingStart = measuredWidth + tabViewContainerView.mGapBetweenTabs;
            }
            i7++;
            this = tabViewContainerView;
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        this.mLayoutCenter = false;
        this.mChildrenTotalWidth = 0;
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            if (!isViewGone(getChildAt(i4))) {
                i3++;
            }
        }
        if (i3 <= 0) {
            super.onMeasure(i, i2);
            return;
        }
        int i5 = this.mLayoutMode;
        if (i5 == 2) {
            measureByWrapMode(i, i2, i3);
            return;
        }
        if (i5 != 0) {
            if (i5 != 1) {
                throw new IllegalStateException("Unexpected layout mode: " + this.mLayoutMode);
            }
            if (!measureByWideMode(i, i2, i3)) {
                this.mLayoutCenter = true;
                return;
            }
        }
        measureByCompatMode(i, i2, i3);
    }

    public void setTabViewLayoutMode(int i) {
        if (this.mLayoutMode != i) {
            this.mLayoutMode = i;
            requestLayout();
        }
    }

    public final void updateLayoutParams() {
        Context context = getContext();
        Resources resources = getResources();
        this.mGapBetweenTabs = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_filter_sort_view2_tab_gap);
        this.mVerticalPaddingTop = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_filter_sort_view2_vertical_padding_top);
        this.mVerticalPaddingBottom = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_filter_sort_view2_vertical_padding_bottom);
        this.mSpaciousLessThanTwoItemMinWidth = MiuixUIUtils.dp2px(context, 220.0f);
        this.mSpaciousThreeItemMinWidth = MiuixUIUtils.dp2px(context, 180.0f);
        this.mSpaciousMoreThanFourItemMinWidth = MiuixUIUtils.dp2px(context, 150.0f);
    }
}

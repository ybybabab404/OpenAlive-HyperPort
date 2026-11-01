package miuix.appcompat.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import miuix.appcompat.R;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes2.dex */
class CalendarFlexibleLayout extends ViewGroup {
    public View mDateView;
    public int mDateViewHeight;
    public int mDateViewWidth;
    public int mGapEnd;
    public int mGapVertical;
    public int mLayoutType;
    public int mParentWidth;
    public View mTimeView;
    public int mTimeViewHeight;
    public int mTimeViewWidth;
    public View mTitleView;
    public int mTitleViewHeight;
    public int mTitleViewWidth;

    public static class LayoutType {
        public static int DOUBLE_LINES = 1;
        public static int SINGLE_LINE;
    }

    public CalendarFlexibleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CalendarFlexibleLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CalendarFlexibleLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mGapEnd = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_date_picker_dialog_date_view_gap_end);
        this.mGapVertical = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_date_picker_dialog_date_view_gap_vertical);
    }

    public final void getChildrenMeasuredSize() {
        this.mTitleViewWidth = this.mTitleView.getMeasuredWidth();
        this.mTitleViewHeight = this.mTitleView.getMeasuredHeight();
        this.mDateViewWidth = this.mDateView.getMeasuredWidth();
        this.mDateViewHeight = this.mDateView.getMeasuredHeight();
        this.mTimeViewWidth = this.mTimeView.getMeasuredWidth();
        this.mTimeViewHeight = this.mTimeView.getMeasuredHeight();
    }

    public final int getLayoutTypeAfterMeasureChildren(int i, int i2) {
        measureChildren(i, i2);
        getChildrenMeasuredSize();
        this.mParentWidth = View.MeasureSpec.getSize(i);
        int i3 = 0;
        boolean z = this.mDateView.getVisibility() == 0;
        boolean z2 = this.mTimeView.getVisibility() == 0;
        int measuredWidth = z ? this.mDateView.getMeasuredWidth() : 0;
        int measuredWidth2 = z2 ? this.mTimeView.getMeasuredWidth() : 0;
        if (z && z2) {
            i3 = this.mGapEnd;
        }
        return ((this.mTitleViewWidth + measuredWidth) + measuredWidth2) + i3 <= (this.mParentWidth - getPaddingLeft()) - getPaddingRight() ? LayoutType.SINGLE_LINE : LayoutType.DOUBLE_LINES;
    }

    public final int getMeasuredHeightRaw() {
        int iMax;
        if (this.mLayoutType == LayoutType.SINGLE_LINE) {
            iMax = Math.max(this.mTitleViewHeight, Math.max(this.mDateViewHeight, this.mTimeViewHeight));
        } else {
            iMax = this.mTitleViewHeight + this.mGapVertical + Math.max(this.mDateViewHeight, this.mTimeViewHeight);
        }
        return iMax + getPaddingTop() + getPaddingBottom();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = getChildAt(0);
        this.mDateView = getChildAt(1);
        this.mTimeView = getChildAt(2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = i + getPaddingLeft();
        int paddingTop = i2 + getPaddingTop();
        int paddingRight = i3 - getPaddingRight();
        int paddingBottom = i4 - getPaddingBottom();
        if (this.mLayoutType != LayoutType.SINGLE_LINE) {
            ViewUtils.layoutChildView(this, this.mTitleView, paddingLeft, paddingTop, paddingLeft + this.mTitleViewWidth, paddingTop + this.mTitleViewHeight);
            int i5 = paddingTop + this.mTitleViewHeight + this.mGapVertical;
            ViewUtils.layoutChildView(this, this.mDateView, paddingLeft, i5, paddingLeft + this.mDateViewWidth, i5 + this.mDateViewHeight);
            int i6 = paddingLeft + this.mDateViewWidth + this.mGapEnd;
            ViewUtils.layoutChildView(this, this.mTimeView, i6, i5, i6 + this.mTimeViewWidth, i5 + this.mTimeViewHeight);
            return;
        }
        int i7 = this.mTitleViewHeight;
        int i8 = paddingTop + (((paddingBottom - paddingTop) - i7) >> 1);
        ViewUtils.layoutChildView(this, this.mTitleView, paddingLeft, i8, paddingLeft + this.mTitleViewWidth, i8 + i7);
        ViewUtils.layoutChildView(this, this.mTimeView, paddingRight - this.mTimeViewWidth, paddingTop, paddingRight, paddingTop + this.mTimeViewHeight);
        if (this.mTimeView.getVisibility() == 0) {
            paddingRight = (paddingRight - this.mTimeViewWidth) - this.mGapEnd;
        }
        ViewUtils.layoutChildView(this, this.mDateView, paddingRight - this.mDateViewWidth, paddingTop, paddingRight, paddingTop + this.mDateViewHeight);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        this.mLayoutType = getLayoutTypeAfterMeasureChildren(i, i2);
        setMeasuredDimension(this.mParentWidth, getMeasuredHeightRaw());
    }
}

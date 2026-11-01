package miuix.pickerwidget.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import miuix.internal.util.ViewUtils;
import miuix.pickerwidget.R;

/* JADX INFO: loaded from: classes2.dex */
public class ProperPaddingViewGroup extends ViewGroup {
    public final float DENSITY;
    public View mChildView;
    public boolean mFixedHorizontalPadding;
    public int mFixedHorizontalPaddingEnd;
    public int mFixedHorizontalPaddingStart;
    public final int mHorizontalPaddingEnd;
    public final int mHorizontalPaddingStart;
    public int mPaddingEnd;
    public int mPaddingStart;
    public final int mSmallHorizontalPaddingEnd;
    public final int mSmallHorizontalPaddingStart;

    public ProperPaddingViewGroup(Context context) {
        this(context, null);
    }

    public ProperPaddingViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ProperPaddingViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFixedHorizontalPadding = false;
        this.mFixedHorizontalPaddingStart = Integer.MIN_VALUE;
        this.mFixedHorizontalPaddingEnd = Integer.MIN_VALUE;
        TypedArray typedArrayObtainStyledAttributes = null;
        this.mChildView = null;
        this.DENSITY = context.getResources().getDisplayMetrics().density;
        try {
            typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ProperPaddingViewGroup);
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ProperPaddingViewGroup_horizontalPadding, 0);
            this.mHorizontalPaddingStart = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ProperPaddingViewGroup_horizontalPaddingStart, dimensionPixelSize);
            this.mHorizontalPaddingEnd = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ProperPaddingViewGroup_horizontalPaddingEnd, dimensionPixelSize);
            int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ProperPaddingViewGroup_smallHorizontalPadding, 0);
            this.mSmallHorizontalPaddingStart = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ProperPaddingViewGroup_smallHorizontalPaddingStart, dimensionPixelSize2);
            this.mSmallHorizontalPaddingEnd = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ProperPaddingViewGroup_smallHorizontalPaddingEnd, dimensionPixelSize2);
            typedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            if (typedArrayObtainStyledAttributes != null) {
                typedArrayObtainStyledAttributes.recycle();
            }
            throw th;
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("Only one child view can be added into the ProperPaddingViewGroup!");
        }
        this.mChildView = getChildAt(0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = ViewUtils.isLayoutRtl(this) ? this.mPaddingEnd : this.mPaddingStart;
        this.mChildView.layout(i5, 0, this.mChildView.getMeasuredWidth() + i5, this.mChildView.getMeasuredHeight());
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        float f = size;
        float f2 = this.DENSITY;
        float f3 = f / f2;
        if (this.mFixedHorizontalPadding) {
            this.mPaddingStart = this.mFixedHorizontalPaddingStart;
            this.mPaddingEnd = this.mFixedHorizontalPaddingEnd;
        } else if (f3 <= 340.0f) {
            int i3 = ((int) (f - (f2 * 290.0f))) / 2;
            if (i3 < 0) {
                i3 = 0;
            }
            int i4 = i3 / 2;
            this.mPaddingStart = this.mSmallHorizontalPaddingStart + i4;
            this.mPaddingEnd = this.mSmallHorizontalPaddingEnd + i4;
        } else {
            this.mPaddingStart = this.mHorizontalPaddingStart;
            this.mPaddingEnd = this.mHorizontalPaddingEnd;
        }
        ViewGroup.LayoutParams layoutParams = this.mChildView.getLayoutParams();
        int iMax = Math.max(0, (size - this.mPaddingStart) - this.mPaddingEnd);
        int i5 = layoutParams.width;
        int iMakeMeasureSpec = i5 == -1 ? View.MeasureSpec.makeMeasureSpec(iMax, 1073741824) : ViewGroup.getChildMeasureSpec(i, this.mPaddingStart + this.mPaddingEnd, i5);
        View view = this.mChildView;
        view.measure(iMakeMeasureSpec, ViewGroup.getChildMeasureSpec(i2, 0, view.getLayoutParams().height));
        setMeasuredDimension(size, this.mChildView.getMeasuredHeight());
    }

    public void setFixedContentHorizontalPadding(int i, int i2) {
        this.mFixedHorizontalPadding = true;
        int i3 = this.mFixedHorizontalPaddingStart;
        int i4 = this.mFixedHorizontalPaddingEnd;
        this.mFixedHorizontalPaddingStart = i;
        this.mFixedHorizontalPaddingEnd = i2;
        if (i2 == i4 ? i != i3 : true) {
            requestLayout();
        }
    }
}

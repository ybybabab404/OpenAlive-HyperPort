package miuix.visual.check;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import miuix.visualcheck.R;

/* JADX INFO: loaded from: classes3.dex */
@RestrictTo
public class FlowLayout extends ViewGroup {
    public int itemSpacing;
    public int lineSpacing;
    public int mLineGravity;
    public final ArrayList mVisibleChildren;
    public boolean singleLine;

    public FlowLayout(@NonNull Context context) {
        this(context, null);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVisibleChildren = new ArrayList();
        this.singleLine = false;
        loadFromAttributes(context, attributeSet);
    }

    public static int getMeasuredDimension(int i, int i2, int i3) {
        if (i2 != Integer.MIN_VALUE) {
            return i2 != 1073741824 ? i3 : i;
        }
        return Math.min(i3, i);
    }

    private int getVisibleChildrenCount() {
        this.mVisibleChildren.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                this.mVisibleChildren.add(childAt);
            }
        }
        return this.mVisibleChildren.size();
    }

    public int getItemSpacing() {
        return this.itemSpacing;
    }

    public int getLineSpacing() {
        return this.lineSpacing;
    }

    public final View getVisibleChildAt(int i) {
        return (View) this.mVisibleChildren.get(i);
    }

    public final void loadFromAttributes(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.FlowLayout, 0, 0);
        this.lineSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
        this.itemSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
        this.mLineGravity = typedArrayObtainStyledAttributes.getInt(R.styleable.FlowLayout_lineGravity, 4);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void offsetView(boolean z, int i, int i2, int i3, int i4, View view) {
        int i5;
        int i6 = this.mLineGravity;
        if (i6 == 4) {
            i5 = ((i2 - i) - (i4 - this.itemSpacing)) / 2;
        } else {
            if (i6 != 2) {
                throw new IllegalStateException("Unexpected line gravity: " + this.mLineGravity);
            }
            i5 = i3 + this.itemSpacing;
        }
        if (z) {
            i5 = -i5;
        }
        view.offsetLeftAndRight(i5);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int marginEnd;
        int i6;
        int i7;
        int i8;
        FlowLayout flowLayout = this;
        if (flowLayout.getChildCount() == 0) {
            return;
        }
        boolean z2 = ViewCompat.getLayoutDirection(flowLayout) == 1;
        int paddingRight = z2 ? flowLayout.getPaddingRight() : flowLayout.getPaddingLeft();
        int paddingLeft = z2 ? flowLayout.getPaddingLeft() : flowLayout.getPaddingRight();
        int paddingTop = flowLayout.getPaddingTop();
        int measuredWidth = flowLayout.getMeasuredWidth();
        int i9 = measuredWidth - paddingLeft;
        int visibleChildrenCount = flowLayout.getVisibleChildrenCount();
        int i10 = paddingTop;
        int measuredWidth2 = paddingRight;
        int i11 = i9;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        while (i13 < visibleChildrenCount) {
            View visibleChildAt = flowLayout.getVisibleChildAt(i13);
            ViewGroup.LayoutParams layoutParams = visibleChildAt.getLayoutParams();
            boolean z3 = z2;
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                int marginStart = MarginLayoutParamsCompat.getMarginStart(marginLayoutParams);
                marginEnd = MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
                i5 = marginStart;
            } else {
                i5 = 0;
                marginEnd = 0;
            }
            int measuredWidth3 = measuredWidth2 + i5 + visibleChildAt.getMeasuredWidth();
            int i16 = paddingLeft;
            if (flowLayout.singleLine || measuredWidth3 <= i9) {
                i6 = paddingTop;
                i7 = measuredWidth2;
                i8 = i12;
            } else {
                i6 = flowLayout.lineSpacing + i10;
                i7 = paddingRight;
                i8 = i13;
            }
            int i17 = i7 + i5;
            int measuredWidth4 = visibleChildAt.getMeasuredWidth() + i17;
            int measuredHeight = visibleChildAt.getMeasuredHeight() + i6;
            if (z3) {
                visibleChildAt.layout(measuredWidth - measuredWidth4, i6, (measuredWidth - i7) - i5, measuredHeight);
            } else {
                visibleChildAt.layout(i17, i6, measuredWidth4, measuredHeight);
            }
            if (flowLayout.mLineGravity != 1 && i8 == i13 && i8 != 0) {
                while (i14 < i8) {
                    int i18 = i15;
                    int i19 = i6;
                    boolean z4 = z3;
                    int i20 = i11;
                    flowLayout.offsetView(z4, i16, measuredWidth, i20, i18, flowLayout.getVisibleChildAt(i14));
                    i14++;
                    measuredHeight = measuredHeight;
                    i8 = i8;
                    visibleChildAt = visibleChildAt;
                    z3 = z4;
                    i11 = i20;
                    i6 = i19;
                    i15 = i18;
                }
            }
            int i21 = i6;
            int i22 = i8;
            z2 = z3;
            paddingLeft = i16;
            View view = visibleChildAt;
            int i23 = measuredHeight;
            measuredWidth2 = i7 + i5 + marginEnd + view.getMeasuredWidth() + flowLayout.itemSpacing;
            int i24 = i9 - measuredWidth2;
            if (flowLayout.mLineGravity != 1 && i13 == visibleChildrenCount - 1) {
                int i25 = i22;
                while (i25 <= i13) {
                    flowLayout.offsetView(z2, paddingLeft, measuredWidth, i24, measuredWidth2, flowLayout.getVisibleChildAt(i25));
                    i25++;
                    flowLayout = this;
                }
            }
            if (i10 < i23) {
                i10 = i23;
            }
            i13++;
            flowLayout = this;
            i12 = i22;
            i14 = i12;
            i11 = i24;
            paddingTop = i21;
            i15 = measuredWidth2;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int paddingLeft;
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i5 = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
        int paddingLeft2 = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = i5 - getPaddingRight();
        int i6 = paddingTop;
        int i7 = 0;
        int i8 = 0;
        while (i7 < getChildCount()) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i3 = marginLayoutParams.leftMargin;
                    i4 = marginLayoutParams.rightMargin;
                } else {
                    i3 = 0;
                    i4 = 0;
                }
                int i9 = paddingLeft2;
                if (paddingLeft2 + i3 + childAt.getMeasuredWidth() <= paddingRight || this.singleLine) {
                    paddingLeft = i9;
                } else {
                    paddingLeft = getPaddingLeft();
                    i6 = this.lineSpacing + paddingTop;
                }
                int measuredWidth = paddingLeft + i3 + childAt.getMeasuredWidth();
                int i10 = paddingLeft;
                int measuredHeight = i6 + childAt.getMeasuredHeight();
                if (measuredWidth > i8) {
                    i8 = measuredWidth;
                }
                int measuredWidth2 = i10 + i3 + i4 + childAt.getMeasuredWidth() + this.itemSpacing;
                if (i7 == getChildCount() - 1) {
                    i8 += i4;
                }
                if (paddingTop < measuredHeight) {
                    paddingTop = measuredHeight;
                }
                paddingLeft2 = measuredWidth2;
            }
            i7++;
            paddingRight = paddingRight;
        }
        setMeasuredDimension(getMeasuredDimension(size, mode, i8 + getPaddingRight()), getMeasuredDimension(size2, mode2, paddingTop + getPaddingBottom()));
    }

    public void setItemSpacing(int i) {
        this.itemSpacing = i;
    }

    public void setLineSpacing(int i) {
        this.lineSpacing = i;
    }
}

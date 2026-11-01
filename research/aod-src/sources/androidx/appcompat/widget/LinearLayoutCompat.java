package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.annotation.GravityInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mBaselineAligned;
    public int mBaselineAlignedChildIndex;
    public int mBaselineChildTop;
    public Drawable mDivider;
    public int mDividerHeight;
    public int mDividerPadding;
    public int mDividerWidth;
    public int mGravity;
    public int[] mMaxAscent;
    public int[] mMaxDescent;
    public int mOrientation;
    public int mShowDividers;
    public int mTotalLength;
    public boolean mUseLargestChild;
    public float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface DividerMode {
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(@NonNull Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        int[] iArr = R.styleable.LinearLayoutCompat;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        int i2 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0036  */
    private void forceUniformWidth(int i, int i2) {
        LinearLayoutCompat linearLayoutCompat;
        int i3;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        int i4 = 0;
        while (i4 < i) {
            View virtualChildAt = this.getVirtualChildAt(i4);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (((LinearLayout.LayoutParams) layoutParams).width == -1) {
                    int i5 = ((LinearLayout.LayoutParams) layoutParams).height;
                    ((LinearLayout.LayoutParams) layoutParams).height = virtualChildAt.getMeasuredHeight();
                    linearLayoutCompat = this;
                    i3 = i2;
                    linearLayoutCompat.measureChildWithMargins(virtualChildAt, iMakeMeasureSpec, 0, i3, 0);
                    ((LinearLayout.LayoutParams) layoutParams).height = i5;
                } else {
                    linearLayoutCompat = this;
                    i3 = i2;
                }
            } else {
                linearLayoutCompat = this;
                i3 = i2;
            }
            i4++;
            this = linearLayoutCompat;
            i2 = i3;
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void drawDividersHorizontal(Canvas canvas) {
        int right;
        int left;
        int i;
        int virtualChildCount = getVirtualChildCount();
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i2 = 0; i2 < virtualChildCount; i2++) {
            View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                drawVerticalDivider(canvas, zIsLayoutRtl ? virtualChildAt.getRight() + ((LinearLayout.LayoutParams) layoutParams).rightMargin : (virtualChildAt.getLeft() - ((LinearLayout.LayoutParams) layoutParams).leftMargin) - this.mDividerWidth);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 != null) {
                LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                if (zIsLayoutRtl) {
                    left = virtualChildAt2.getLeft() - ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    i = this.mDividerWidth;
                    right = left - i;
                } else {
                    right = virtualChildAt2.getRight() + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                }
            } else if (zIsLayoutRtl) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i = this.mDividerWidth;
                right = left - i;
            }
            drawVerticalDivider(canvas, right);
        }
    }

    public void drawDividersVertical(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        for (int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                drawHorizontalDivider(canvas, (virtualChildAt.getTop() - ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt.getLayoutParams())).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            drawHorizontalDivider(canvas, virtualChildAt2 == null ? (getHeight() - getPaddingBottom()) - this.mDividerHeight : virtualChildAt2.getBottom() + ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt2.getLayoutParams())).bottomMargin);
        }
    }

    public void drawHorizontalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    public void drawVerticalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0036  */
    public final void forceUniformHeight(int i, int i2) {
        LinearLayoutCompat linearLayoutCompat;
        int i3;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        int i4 = 0;
        while (i4 < i) {
            View virtualChildAt = this.getVirtualChildAt(i4);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (((LinearLayout.LayoutParams) layoutParams).height == -1) {
                    int i5 = ((LinearLayout.LayoutParams) layoutParams).width;
                    ((LinearLayout.LayoutParams) layoutParams).width = virtualChildAt.getMeasuredWidth();
                    linearLayoutCompat = this;
                    i3 = i2;
                    linearLayoutCompat.measureChildWithMargins(virtualChildAt, i3, 0, iMakeMeasureSpec, 0);
                    ((LinearLayout.LayoutParams) layoutParams).width = i5;
                } else {
                    linearLayoutCompat = this;
                    i3 = i2;
                }
            } else {
                linearLayoutCompat = this;
                i3 = i2;
            }
            i4++;
            this = linearLayoutCompat;
            i2 = i3;
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = this.mOrientation;
        if (i == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.mBaselineAlignedChildIndex;
        if (childCount <= i2) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) != 48) {
            if (i == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (i == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return bottom + ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public int getChildrenSkipCount(View view, int i) {
        return 0;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @GravityInt
    public int getGravity() {
        return this.mGravity;
    }

    public int getLocationOffset(View view) {
        return 0;
    }

    public int getNextLocationOffset(View view) {
        return 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public View getVirtualChildAt(int i) {
        return getChildAt(i);
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) != 0) {
            for (int i2 = i - 1; i2 >= 0; i2--) {
                if (getChildAt(i2).getVisibility() != 8) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:32:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:34:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:36:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:37:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:39:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:41:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:43:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:44:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:47:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:48:0x0103  */
    public void layoutHorizontal(int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        char c;
        char c2;
        int i7;
        int childrenSkipCount;
        int i8;
        int baseline;
        int i9;
        int i10;
        int i11;
        int measuredHeight;
        int i12;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int i13 = i4 - i2;
        int paddingBottom = i13 - getPaddingBottom();
        int paddingBottom2 = (i13 - paddingTop) - getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        int i14 = this.mGravity;
        int i15 = i14 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
        boolean z = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(8388615 & i14, ViewCompat.getLayoutDirection(this));
        char c3 = 2;
        char c4 = 1;
        if (absoluteGravity != 1) {
            paddingLeft = absoluteGravity != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.mTotalLength;
        } else {
            paddingLeft = getPaddingLeft() + (((i3 - i) - this.mTotalLength) / 2);
        }
        if (zIsLayoutRtl) {
            i5 = virtualChildCount - 1;
            i6 = -1;
        } else {
            i5 = 0;
            i6 = 1;
        }
        int i16 = 0;
        while (i16 < virtualChildCount) {
            int i17 = i5 + (i6 * i16);
            int i18 = i16;
            View virtualChildAt = getVirtualChildAt(i17);
            if (virtualChildAt == null) {
                paddingLeft += measureNullChild(i17);
                childrenSkipCount = i18;
                i7 = paddingTop;
                c = c3;
                c2 = c4;
            } else {
                c = c3;
                c2 = c4;
                if (virtualChildAt.getVisibility() != 8) {
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    int measuredHeight2 = virtualChildAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    int i19 = paddingLeft;
                    if (z) {
                        i8 = measuredHeight2;
                        baseline = ((LinearLayout.LayoutParams) layoutParams).height != -1 ? virtualChildAt.getBaseline() : -1;
                        i9 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                        if (i9 < 0) {
                            i9 = i15;
                        }
                        i10 = i9 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
                        i7 = paddingTop;
                        if (i10 != 16) {
                            if (i10 != 48) {
                                i11 = i7 + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                                if (baseline != -1) {
                                    i11 += iArr[c2] - baseline;
                                }
                            } else if (i10 != 80) {
                                i11 = i7;
                            } else {
                                i11 = (paddingBottom - i8) - ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                                if (baseline != -1) {
                                    measuredHeight = iArr2[c] - (virtualChildAt.getMeasuredHeight() - baseline);
                                }
                            }
                            if (hasDividerBeforeChildAt(i17)) {
                                i12 = i19 + this.mDividerWidth;
                            } else {
                                i12 = i19;
                            }
                            int i20 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + i12;
                            setChildFrame(virtualChildAt, getLocationOffset(virtualChildAt) + i20, i11, measuredWidth, i8);
                            int nextLocationOffset = i20 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                            childrenSkipCount = getChildrenSkipCount(virtualChildAt, i17) + i18;
                            paddingLeft = nextLocationOffset;
                        } else {
                            i11 = i7 + ((paddingBottom2 - i8) / 2) + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                            measuredHeight = ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                        }
                        i11 -= measuredHeight;
                        if (hasDividerBeforeChildAt(i17)) {
                            i12 = i19 + this.mDividerWidth;
                        } else {
                            i12 = i19;
                        }
                        int i21 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + i12;
                        setChildFrame(virtualChildAt, getLocationOffset(virtualChildAt) + i21, i11, measuredWidth, i8);
                        int nextLocationOffset2 = i21 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                        childrenSkipCount = getChildrenSkipCount(virtualChildAt, i17) + i18;
                        paddingLeft = nextLocationOffset2;
                    } else {
                        i8 = measuredHeight2;
                    }
                    i9 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                    if (i9 < 0) {
                        i9 = i15;
                    }
                    i10 = i9 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
                    i7 = paddingTop;
                    if (i10 != 16) {
                        if (i10 != 48) {
                            i11 = i7 + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                            if (baseline != -1) {
                                i11 += iArr[c2] - baseline;
                            }
                        } else if (i10 != 80) {
                            i11 = i7;
                        } else {
                            i11 = (paddingBottom - i8) - ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                            if (baseline != -1) {
                                measuredHeight = iArr2[c] - (virtualChildAt.getMeasuredHeight() - baseline);
                            }
                        }
                        if (hasDividerBeforeChildAt(i17)) {
                            i12 = i19 + this.mDividerWidth;
                        } else {
                            i12 = i19;
                        }
                        int i22 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + i12;
                        setChildFrame(virtualChildAt, getLocationOffset(virtualChildAt) + i22, i11, measuredWidth, i8);
                        int nextLocationOffset3 = i22 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                        childrenSkipCount = getChildrenSkipCount(virtualChildAt, i17) + i18;
                        paddingLeft = nextLocationOffset3;
                    } else {
                        i11 = i7 + ((paddingBottom2 - i8) / 2) + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                        measuredHeight = ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    }
                    i11 -= measuredHeight;
                    if (hasDividerBeforeChildAt(i17)) {
                        i12 = i19 + this.mDividerWidth;
                    } else {
                        i12 = i19;
                    }
                    int i23 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + i12;
                    setChildFrame(virtualChildAt, getLocationOffset(virtualChildAt) + i23, i11, measuredWidth, i8);
                    int nextLocationOffset4 = i23 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                    childrenSkipCount = getChildrenSkipCount(virtualChildAt, i17) + i18;
                    paddingLeft = nextLocationOffset4;
                } else {
                    i7 = paddingTop;
                    childrenSkipCount = i18;
                }
            }
            i16 = childrenSkipCount + 1;
            c3 = c;
            c4 = c2;
            paddingTop = i7;
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0099  */
    public void layoutVertical(int i, int i2, int i3, int i4) {
        int paddingTop;
        int i5;
        int i6;
        int i7;
        LinearLayoutCompat linearLayoutCompat;
        int paddingLeft = getPaddingLeft();
        int i8 = i3 - i;
        int paddingRight = i8 - getPaddingRight();
        int paddingRight2 = (i8 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i9 = this.mGravity;
        int i10 = i9 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
        int i11 = i9 & 8388615;
        if (i10 != 16) {
            paddingTop = i10 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.mTotalLength;
        } else {
            paddingTop = getPaddingTop() + (((i4 - i2) - this.mTotalLength) / 2);
        }
        int childrenSkipCount = 0;
        while (childrenSkipCount < virtualChildCount) {
            View virtualChildAt = this.getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                paddingTop += this.measureNullChild(childrenSkipCount);
            } else {
                if (virtualChildAt.getVisibility() != 8) {
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    int measuredHeight = virtualChildAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    int i12 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                    if (i12 < 0) {
                        i12 = i11;
                    }
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(i12, ViewCompat.getLayoutDirection(this)) & 7;
                    if (absoluteGravity != 1) {
                        if (absoluteGravity != 5) {
                            i7 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft;
                        } else {
                            i5 = paddingRight - measuredWidth;
                            i6 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                        }
                        int i13 = i7;
                        if (this.hasDividerBeforeChildAt(childrenSkipCount)) {
                            paddingTop += this.mDividerHeight;
                        }
                        int i14 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                        linearLayoutCompat = this;
                        linearLayoutCompat.setChildFrame(virtualChildAt, i13, i14 + this.getLocationOffset(virtualChildAt), measuredWidth, measuredHeight);
                        paddingTop = i14 + measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + linearLayoutCompat.getNextLocationOffset(virtualChildAt);
                        childrenSkipCount += linearLayoutCompat.getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                    } else {
                        i5 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                        i6 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    }
                    i7 = i5 - i6;
                    int i15 = i7;
                    if (this.hasDividerBeforeChildAt(childrenSkipCount)) {
                        paddingTop += this.mDividerHeight;
                    }
                    int i16 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    linearLayoutCompat = this;
                    linearLayoutCompat.setChildFrame(virtualChildAt, i15, i16 + this.getLocationOffset(virtualChildAt), measuredWidth, measuredHeight);
                    paddingTop = i16 + measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + linearLayoutCompat.getNextLocationOffset(virtualChildAt);
                    childrenSkipCount += linearLayoutCompat.getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                }
                childrenSkipCount++;
                this = linearLayoutCompat;
            }
            linearLayoutCompat = this;
            childrenSkipCount++;
            this = linearLayoutCompat;
        }
    }

    public void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    /* JADX WARN: Code duplicated, block: B:200:0x045e  */
    /* JADX WARN: Code duplicated, block: B:60:0x0182  */
    /* JADX WARN: Code duplicated, block: B:69:0x01c5  */
    /* JADX WARN: Code duplicated, block: B:75:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:78:0x01dd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:79:0x01df  */
    /* JADX WARN: Code duplicated, block: B:81:0x01e6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:82:0x01e8  */
    public void measureHorizontal(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int iMax;
        int i8;
        int iMax2;
        int i9;
        int baseline;
        int i10;
        int i11;
        byte b;
        int i12;
        int i13;
        int[] iArr;
        int i14;
        boolean z;
        int[] iArr2;
        View view;
        int i15;
        boolean z2;
        int i16;
        int measuredHeight;
        int baseline2;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr3 = this.mMaxAscent;
        int[] iArr4 = this.mMaxDescent;
        iArr3[3] = -1;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        iArr4[3] = -1;
        iArr4[2] = -1;
        iArr4[1] = -1;
        iArr4[0] = -1;
        boolean z3 = this.mBaselineAligned;
        boolean z4 = this.mUseLargestChild;
        int i17 = 1073741824;
        boolean z5 = mode == 1073741824;
        boolean z6 = z4;
        int childrenSkipCount = 0;
        int iMax3 = 0;
        int iMax4 = 0;
        boolean z7 = false;
        int iCombineMeasuredStates = 0;
        boolean z8 = false;
        boolean z9 = true;
        float f = 0.0f;
        int iMax5 = 0;
        int iMax6 = 0;
        while (true) {
            i3 = iMax3;
            if (childrenSkipCount >= virtualChildCount) {
                break;
            }
            boolean z10 = z3;
            View virtualChildAt = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(childrenSkipCount);
            } else {
                if (virtualChildAt.getVisibility() == 8) {
                    childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                } else {
                    if (hasDividerBeforeChildAt(childrenSkipCount)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    float f2 = ((LinearLayout.LayoutParams) layoutParams).weight;
                    float f3 = f + f2;
                    if (mode == i17 && ((LinearLayout.LayoutParams) layoutParams).width == 0 && f2 > 0.0f) {
                        if (z5) {
                            this.mTotalLength += ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                        } else {
                            int i18 = this.mTotalLength;
                            this.mTotalLength = Math.max(i18, ((LinearLayout.LayoutParams) layoutParams).leftMargin + i18 + ((LinearLayout.LayoutParams) layoutParams).rightMargin);
                        }
                        if (z10) {
                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            virtualChildAt.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                            i13 = mode;
                            iArr = iArr3;
                            iArr2 = iArr4;
                            i14 = i3;
                            z = z6;
                            view = virtualChildAt;
                        } else {
                            i13 = mode;
                            iArr = iArr3;
                            iArr2 = iArr4;
                            z7 = true;
                            i14 = i3;
                            i15 = 1073741824;
                            z = z6;
                            view = virtualChildAt;
                        }
                        if (mode2 == i15 && ((LinearLayout.LayoutParams) layoutParams).height == -1) {
                            z2 = true;
                            z8 = true;
                        } else {
                            z2 = false;
                        }
                        i16 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                        measuredHeight = view.getMeasuredHeight() + i16;
                        iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view.getMeasuredState());
                        if (!z10 && (baseline2 = view.getBaseline()) != -1) {
                            int i19 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                            if (i19 < 0) {
                                i19 = this.mGravity;
                            }
                            int i20 = (((i19 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) >> 4) & (-2)) >> 1;
                            iArr[i20] = Math.max(iArr[i20], baseline2);
                            iArr2[i20] = Math.max(iArr2[i20], measuredHeight - baseline2);
                        }
                        iMax3 = Math.max(i14, measuredHeight);
                        if (z9 || ((LinearLayout.LayoutParams) layoutParams).height != -1) {
                            z9 = false;
                        } else {
                            z9 = true;
                        }
                        if (((LinearLayout.LayoutParams) layoutParams).weight > 0.0f) {
                            if (z2) {
                                measuredHeight = i16;
                            }
                            iMax6 = Math.max(iMax6, measuredHeight);
                        } else {
                            if (z2 != 0) {
                                measuredHeight = i16;
                            }
                            iMax5 = Math.max(iMax5, measuredHeight);
                        }
                        childrenSkipCount += getChildrenSkipCount(view, childrenSkipCount);
                        f = f3;
                    } else {
                        if (((LinearLayout.LayoutParams) layoutParams).width != 0 || f2 <= 0.0f) {
                            b = -2;
                            i12 = Integer.MIN_VALUE;
                        } else {
                            b = -2;
                            ((LinearLayout.LayoutParams) layoutParams).width = -2;
                            i12 = 0;
                        }
                        i13 = mode;
                        iArr = iArr3;
                        i14 = i3;
                        int i21 = i12;
                        z = z6;
                        iArr2 = iArr4;
                        measureChildBeforeLayout(virtualChildAt, childrenSkipCount, i, f3 == 0.0f ? this.mTotalLength : 0, i2, 0);
                        view = virtualChildAt;
                        if (i21 != Integer.MIN_VALUE) {
                            ((LinearLayout.LayoutParams) layoutParams).width = i21;
                        }
                        int measuredWidth = view.getMeasuredWidth();
                        if (z5) {
                            this.mTotalLength += ((LinearLayout.LayoutParams) layoutParams).leftMargin + measuredWidth + ((LinearLayout.LayoutParams) layoutParams).rightMargin + getNextLocationOffset(view);
                        } else {
                            int i22 = this.mTotalLength;
                            this.mTotalLength = Math.max(i22, i22 + measuredWidth + ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin + getNextLocationOffset(view));
                        }
                        if (z) {
                            iMax4 = Math.max(measuredWidth, iMax4);
                        }
                    }
                    i15 = 1073741824;
                    if (mode2 == i15) {
                        z2 = false;
                    } else {
                        z2 = false;
                    }
                    i16 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    measuredHeight = view.getMeasuredHeight() + i16;
                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view.getMeasuredState());
                    if (!z10) {
                    }
                    iMax3 = Math.max(i14, measuredHeight);
                    if (z9) {
                        z9 = false;
                    } else {
                        z9 = false;
                    }
                    if (((LinearLayout.LayoutParams) layoutParams).weight > 0.0f) {
                        if (z2) {
                            measuredHeight = i16;
                        }
                        iMax6 = Math.max(iMax6, measuredHeight);
                    } else {
                        if (z2 != 0) {
                            measuredHeight = i16;
                        }
                        iMax5 = Math.max(iMax5, measuredHeight);
                    }
                    childrenSkipCount += getChildrenSkipCount(view, childrenSkipCount);
                    f = f3;
                }
                childrenSkipCount++;
                z6 = z;
                iArr4 = iArr2;
                z3 = z10;
                iArr3 = iArr;
                mode = i13;
                i17 = 1073741824;
            }
            i13 = mode;
            iArr = iArr3;
            iArr2 = iArr4;
            iMax3 = i3;
            z = z6;
            childrenSkipCount++;
            z6 = z;
            iArr4 = iArr2;
            z3 = z10;
            iArr3 = iArr;
            mode = i13;
            i17 = 1073741824;
        }
        boolean z11 = z3;
        int i23 = mode;
        int[] iArr5 = iArr3;
        int[] iArr6 = iArr4;
        int i24 = iCombineMeasuredStates;
        boolean z12 = z6;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth;
        }
        int i25 = iArr5[1];
        int iMax7 = (i25 == -1 && iArr5[0] == -1 && iArr5[2] == -1 && iArr5[3] == -1) ? i3 : Math.max(i3, Math.max(iArr5[3], Math.max(iArr5[0], Math.max(i25, iArr5[2]))) + Math.max(iArr6[3], Math.max(iArr6[0], Math.max(iArr6[1], iArr6[2]))));
        if (z12) {
            i5 = i23;
            if (i5 == Integer.MIN_VALUE || i5 == 0) {
                this.mTotalLength = 0;
                int childrenSkipCount2 = 0;
                while (childrenSkipCount2 < virtualChildCount) {
                    View virtualChildAt2 = getVirtualChildAt(childrenSkipCount2);
                    if (virtualChildAt2 == null) {
                        this.mTotalLength += measureNullChild(childrenSkipCount2);
                    } else {
                        if (virtualChildAt2.getVisibility() == 8) {
                            childrenSkipCount2 += getChildrenSkipCount(virtualChildAt2, childrenSkipCount2);
                        } else {
                            LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                            if (z5) {
                                this.mTotalLength += ((LinearLayout.LayoutParams) layoutParams2).leftMargin + iMax4 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + getNextLocationOffset(virtualChildAt2);
                            } else {
                                iMax7 = iMax7;
                                int i26 = this.mTotalLength;
                                this.mTotalLength = Math.max(i26, i26 + iMax4 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + getNextLocationOffset(virtualChildAt2));
                            }
                        }
                        childrenSkipCount2++;
                        iMax7 = iMax7;
                        f = f;
                    }
                    childrenSkipCount2++;
                    iMax7 = iMax7;
                    f = f;
                }
            }
            i4 = iMax7;
        } else {
            i4 = iMax7;
            i5 = i23;
        }
        float f4 = f;
        int paddingLeft = this.mTotalLength + getPaddingLeft() + getPaddingRight();
        this.mTotalLength = paddingLeft;
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i, 0);
        int i27 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
        if (z7 || (i27 != 0 && f4 > 0.0f)) {
            float f5 = this.mWeightSum;
            if (f5 > 0.0f) {
                f4 = f5;
            }
            iArr5[3] = -1;
            iArr5[2] = -1;
            iArr5[1] = -1;
            iArr5[0] = -1;
            iArr6[3] = -1;
            iArr6[2] = -1;
            iArr6[1] = -1;
            iArr6[0] = -1;
            this.mTotalLength = 0;
            int iCombineMeasuredStates2 = i24;
            int iMax8 = -1;
            int i28 = 0;
            while (i28 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i28);
                if (virtualChildAt3 == null || virtualChildAt3.getVisibility() == 8) {
                    iResolveSizeAndState = iResolveSizeAndState;
                } else {
                    LayoutParams layoutParams3 = (LayoutParams) virtualChildAt3.getLayoutParams();
                    float f6 = ((LinearLayout.LayoutParams) layoutParams3).weight;
                    if (f6 > 0.0f) {
                        int i29 = (int) ((i27 * f6) / f4);
                        f4 -= f6;
                        i27 -= i29;
                        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin, ((LinearLayout.LayoutParams) layoutParams3).height);
                        if (((LinearLayout.LayoutParams) layoutParams3).width == 0) {
                            i11 = 1073741824;
                            if (i5 == 1073741824) {
                                if (i29 <= 0) {
                                    i29 = 0;
                                }
                                virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(i29, 1073741824), childMeasureSpec);
                            }
                            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, virtualChildAt3.getMeasuredState() & (-16777216));
                        } else {
                            i11 = 1073741824;
                        }
                        int measuredWidth2 = virtualChildAt3.getMeasuredWidth() + i29;
                        if (measuredWidth2 < 0) {
                            measuredWidth2 = 0;
                        }
                        virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i11), childMeasureSpec);
                        iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, virtualChildAt3.getMeasuredState() & (-16777216));
                    }
                    if (z5) {
                        this.mTotalLength += virtualChildAt3.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams3).leftMargin + ((LinearLayout.LayoutParams) layoutParams3).rightMargin + getNextLocationOffset(virtualChildAt3);
                    } else {
                        int i30 = this.mTotalLength;
                        this.mTotalLength = Math.max(i30, virtualChildAt3.getMeasuredWidth() + i30 + ((LinearLayout.LayoutParams) layoutParams3).leftMargin + ((LinearLayout.LayoutParams) layoutParams3).rightMargin + getNextLocationOffset(virtualChildAt3));
                    }
                    boolean z13 = mode2 != 1073741824 && ((LinearLayout.LayoutParams) layoutParams3).height == -1;
                    int i31 = ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin;
                    int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i31;
                    iMax8 = Math.max(iMax8, measuredHeight2);
                    if (!z13) {
                        i31 = measuredHeight2;
                    }
                    int iMax9 = Math.max(iMax5, i31);
                    if (z9) {
                        i9 = -1;
                        boolean z14 = ((LinearLayout.LayoutParams) layoutParams3).height == -1;
                        if (z11 && (baseline = virtualChildAt3.getBaseline()) != i9) {
                            i10 = ((LinearLayout.LayoutParams) layoutParams3).gravity;
                            if (i10 < 0) {
                                i10 = this.mGravity;
                            }
                            int i32 = (((i10 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) >> 4) & (-2)) >> 1;
                            iArr5[i32] = Math.max(iArr5[i32], baseline);
                            iArr6[i32] = Math.max(iArr6[i32], measuredHeight2 - baseline);
                        }
                        iMax5 = iMax9;
                        z9 = z14;
                    } else {
                        i9 = -1;
                    }
                    if (z11) {
                        i10 = ((LinearLayout.LayoutParams) layoutParams3).gravity;
                        if (i10 < 0) {
                            i10 = this.mGravity;
                        }
                        int i33 = (((i10 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) >> 4) & (-2)) >> 1;
                        iArr5[i33] = Math.max(iArr5[i33], baseline);
                        iArr6[i33] = Math.max(iArr6[i33], measuredHeight2 - baseline);
                    }
                    iMax5 = iMax9;
                    z9 = z14;
                }
                i28++;
                iResolveSizeAndState = iResolveSizeAndState;
            }
            i6 = iResolveSizeAndState;
            i7 = -16777216;
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            int i34 = iArr5[1];
            iMax = (i34 == -1 && iArr5[0] == -1 && iArr5[2] == -1 && iArr5[3] == -1) ? iMax8 : Math.max(iMax8, Math.max(iArr5[3], Math.max(iArr5[0], Math.max(i34, iArr5[2]))) + Math.max(iArr6[3], Math.max(iArr6[0], Math.max(iArr6[1], iArr6[2]))));
            i8 = iCombineMeasuredStates2;
            iMax2 = iMax5;
        } else {
            iMax2 = Math.max(iMax5, iMax6);
            if (z12 && i5 != 1073741824) {
                for (int i35 = 0; i35 < virtualChildCount; i35++) {
                    View virtualChildAt4 = getVirtualChildAt(i35);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt4.getLayoutParams())).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(iMax4, 1073741824), View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i6 = iResolveSizeAndState;
            i8 = i24;
            iMax = i4;
            i7 = -16777216;
        }
        if (!z9 && mode2 != 1073741824) {
            iMax = iMax2;
        }
        setMeasuredDimension(i6 | (i8 & i7), View.resolveSizeAndState(Math.max(iMax + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, i8 << 16));
        if (z8) {
            forceUniformHeight(virtualChildCount, i);
        }
    }

    public int measureNullChild(int i) {
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:64:0x0158 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:66:0x015b  */
    /* JADX WARN: Code duplicated, block: B:68:0x0162 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:70:0x0165  */
    public void measureVertical(int i, int i2) {
        int i3;
        int iMax;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        boolean z;
        int iMax2;
        View view;
        int iMax3;
        boolean z2;
        int iMax4;
        int i13;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i14 = this.mBaselineAlignedChildIndex;
        boolean z3 = this.mUseLargestChild;
        int childrenSkipCount = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int iMax5 = 0;
        boolean z4 = false;
        boolean z5 = false;
        float f = 0.0f;
        boolean z6 = true;
        while (true) {
            int i19 = 8;
            if (childrenSkipCount >= virtualChildCount) {
                float f2 = f;
                int i20 = i15;
                int i21 = virtualChildCount;
                int i22 = mode2;
                boolean z7 = z3;
                int i23 = i16;
                int iMax6 = i17;
                int iCombineMeasuredStates = i18;
                if (this.mTotalLength > 0) {
                    i3 = i21;
                    if (hasDividerBeforeChildAt(i3)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                } else {
                    i3 = i21;
                }
                int i24 = i22;
                if (z7 && (i24 == Integer.MIN_VALUE || i24 == 0)) {
                    this.mTotalLength = 0;
                    int childrenSkipCount2 = 0;
                    while (childrenSkipCount2 < i3) {
                        View virtualChildAt = getVirtualChildAt(childrenSkipCount2);
                        if (virtualChildAt == null) {
                            this.mTotalLength += measureNullChild(childrenSkipCount2);
                        } else if (virtualChildAt.getVisibility() == i19) {
                            childrenSkipCount2 += getChildrenSkipCount(virtualChildAt, childrenSkipCount2);
                        } else {
                            LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                            int i25 = this.mTotalLength;
                            this.mTotalLength = Math.max(i25, i25 + i23 + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + getNextLocationOffset(virtualChildAt));
                        }
                        childrenSkipCount2++;
                        i19 = 8;
                    }
                }
                int paddingTop = this.mTotalLength + getPaddingTop() + getPaddingBottom();
                this.mTotalLength = paddingTop;
                int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i2, 0);
                int i26 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
                if (z4 || (i26 != 0 && f2 > 0.0f)) {
                    float f3 = this.mWeightSum;
                    if (f3 <= 0.0f) {
                        f3 = f2;
                    }
                    this.mTotalLength = 0;
                    float f4 = f3;
                    int i27 = i26;
                    int i28 = 0;
                    while (i28 < i3) {
                        View virtualChildAt2 = getVirtualChildAt(i28);
                        if (virtualChildAt2.getVisibility() == 8) {
                            i24 = i24;
                            i28 = i28;
                        } else {
                            LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                            float f5 = ((LinearLayout.LayoutParams) layoutParams2).weight;
                            if (f5 > 0.0f) {
                                int i29 = (int) ((i27 * f5) / f4);
                                f4 -= f5;
                                i27 -= i29;
                                int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin, ((LinearLayout.LayoutParams) layoutParams2).width);
                                if (((LinearLayout.LayoutParams) layoutParams2).height == 0) {
                                    i5 = 1073741824;
                                    if (i24 == 1073741824) {
                                        virtualChildAt2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i29 > 0 ? i29 : 0, 1073741824));
                                    }
                                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt2.getMeasuredState() & (-256));
                                } else {
                                    i5 = 1073741824;
                                }
                                int measuredHeight = virtualChildAt2.getMeasuredHeight() + i29;
                                if (measuredHeight < 0) {
                                    measuredHeight = 0;
                                }
                                virtualChildAt2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, i5));
                                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt2.getMeasuredState() & (-256));
                            } else {
                                i24 = i24;
                            }
                            int i30 = ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                            int measuredWidth = virtualChildAt2.getMeasuredWidth() + i30;
                            iMax6 = Math.max(iMax6, measuredWidth);
                            if (mode != 1073741824) {
                                i4 = -1;
                                if (((LinearLayout.LayoutParams) layoutParams2).width == -1) {
                                    measuredWidth = i30;
                                }
                            } else {
                                i4 = -1;
                            }
                            int iMax7 = Math.max(iMax5, measuredWidth);
                            boolean z8 = z6 && ((LinearLayout.LayoutParams) layoutParams2).width == i4;
                            int i31 = this.mTotalLength;
                            this.mTotalLength = Math.max(i31, i31 + virtualChildAt2.getMeasuredHeight() + ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin + getNextLocationOffset(virtualChildAt2));
                            iMax5 = iMax7;
                            z6 = z8;
                        }
                        i28++;
                        i24 = i24;
                    }
                    this.mTotalLength += getPaddingTop() + getPaddingBottom();
                    iMax = iMax5;
                } else {
                    iMax = Math.max(iMax5, i20);
                    if (z7 && i24 != 1073741824) {
                        for (int i32 = 0; i32 < i3; i32++) {
                            View virtualChildAt3 = getVirtualChildAt(i32);
                            if (virtualChildAt3 != null && virtualChildAt3.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt3.getLayoutParams())).weight > 0.0f) {
                                virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(virtualChildAt3.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i23, 1073741824));
                            }
                        }
                    }
                }
                if (!z6 && mode != 1073741824) {
                    iMax6 = iMax;
                }
                setMeasuredDimension(View.resolveSizeAndState(Math.max(iMax6 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, iCombineMeasuredStates), iResolveSizeAndState);
                if (z5) {
                    forceUniformWidth(i3, i2);
                    return;
                }
                return;
            }
            float f6 = f;
            View virtualChildAt4 = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt4 == null) {
                this.mTotalLength += measureNullChild(childrenSkipCount);
            } else {
                if (virtualChildAt4.getVisibility() == 8) {
                    childrenSkipCount += getChildrenSkipCount(virtualChildAt4, childrenSkipCount);
                } else {
                    if (hasDividerBeforeChildAt(childrenSkipCount)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams layoutParams3 = (LayoutParams) virtualChildAt4.getLayoutParams();
                    float f7 = ((LinearLayout.LayoutParams) layoutParams3).weight;
                    float f8 = f6 + f7;
                    if (mode2 == 1073741824 && ((LinearLayout.LayoutParams) layoutParams3).height == 0 && f7 > 0.0f) {
                        int i33 = this.mTotalLength;
                        this.mTotalLength = Math.max(i33, ((LinearLayout.LayoutParams) layoutParams3).topMargin + i33 + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin);
                        iMax2 = i15;
                        iMax3 = i16;
                        i9 = virtualChildCount;
                        i10 = mode2;
                        z4 = true;
                        view = virtualChildAt4;
                        i12 = i17;
                        i11 = i18;
                        z = z3;
                    } else {
                        if (((LinearLayout.LayoutParams) layoutParams3).height != 0 || f7 <= 0.0f) {
                            i6 = Integer.MIN_VALUE;
                        } else {
                            ((LinearLayout.LayoutParams) layoutParams3).height = -2;
                            i6 = 0;
                        }
                        if (f8 == 0.0f) {
                            int i34 = i18;
                            i8 = this.mTotalLength;
                            i7 = i34;
                        } else {
                            i7 = i18;
                            i8 = 0;
                        }
                        int i35 = i16;
                        i9 = virtualChildCount;
                        i10 = mode2;
                        i11 = i7;
                        i12 = i17;
                        z = z3;
                        iMax2 = i15;
                        measureChildBeforeLayout(virtualChildAt4, childrenSkipCount, i, 0, i2, i8);
                        view = virtualChildAt4;
                        if (i6 != Integer.MIN_VALUE) {
                            ((LinearLayout.LayoutParams) layoutParams3).height = i6;
                        }
                        int measuredHeight2 = view.getMeasuredHeight();
                        int i36 = this.mTotalLength;
                        this.mTotalLength = Math.max(i36, i36 + measuredHeight2 + ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin + getNextLocationOffset(view));
                        iMax3 = z ? Math.max(measuredHeight2, i35) : i35;
                    }
                    if (i14 >= 0 && i14 == childrenSkipCount + 1) {
                        this.mBaselineChildTop = this.mTotalLength;
                    }
                    if (childrenSkipCount < i14 && ((LinearLayout.LayoutParams) layoutParams3).weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    if (mode == 1073741824 || ((LinearLayout.LayoutParams) layoutParams3).width != -1) {
                        z2 = false;
                    } else {
                        z2 = true;
                        z5 = true;
                    }
                    int i37 = ((LinearLayout.LayoutParams) layoutParams3).leftMargin + ((LinearLayout.LayoutParams) layoutParams3).rightMargin;
                    int measuredWidth2 = view.getMeasuredWidth() + i37;
                    iMax4 = Math.max(i12, measuredWidth2);
                    int i38 = iMax3;
                    int iCombineMeasuredStates2 = View.combineMeasuredStates(i11, view.getMeasuredState());
                    if (z6) {
                        i13 = iCombineMeasuredStates2;
                        z6 = ((LinearLayout.LayoutParams) layoutParams3).width == -1;
                        if (((LinearLayout.LayoutParams) layoutParams3).weight > 0.0f) {
                            if (!z2) {
                                i37 = measuredWidth2;
                            }
                            iMax2 = Math.max(iMax2, i37);
                        } else {
                            if (!z2) {
                                i37 = measuredWidth2;
                            }
                            iMax5 = Math.max(iMax5, i37);
                        }
                        childrenSkipCount += getChildrenSkipCount(view, childrenSkipCount);
                        f = f8;
                        i16 = i38;
                        i18 = i13;
                    } else {
                        i13 = iCombineMeasuredStates2;
                    }
                    if (((LinearLayout.LayoutParams) layoutParams3).weight > 0.0f) {
                        if (!z2) {
                            i37 = measuredWidth2;
                        }
                        iMax2 = Math.max(iMax2, i37);
                    } else {
                        if (!z2) {
                            i37 = measuredWidth2;
                        }
                        iMax5 = Math.max(iMax5, i37);
                    }
                    childrenSkipCount += getChildrenSkipCount(view, childrenSkipCount);
                    f = f8;
                    i16 = i38;
                    i18 = i13;
                }
                childrenSkipCount++;
                i17 = iMax4;
                i15 = iMax2;
                z3 = z;
                mode2 = i10;
                virtualChildCount = i9;
            }
            iMax2 = i15;
            i9 = virtualChildCount;
            i10 = mode2;
            z = z3;
            f = f6;
            iMax4 = i17;
            childrenSkipCount++;
            i17 = iMax4;
            i15 = iMax2;
            z3 = z;
            mode2 = i10;
            virtualChildCount = i9;
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            layoutVertical(i, i2, i3, i4);
        } else {
            layoutHorizontal(i, i2, i3, i4);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            measureVertical(i, i2);
        } else {
            measureHorizontal(i, i2);
        }
    }

    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i >= 0 && i < getChildCount()) {
            this.mBaselineAlignedChildIndex = i;
            return;
        }
        throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
    }

    public void setGravity(@GravityInt int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= 8388611;
            }
            if ((i & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & 8388615;
        int i3 = this.mGravity;
        if ((8388615 & i3) != i2) {
            this.mGravity = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
        int i3 = this.mGravity;
        if ((i3 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) != i2) {
            this.mGravity = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}

package miuix.flexible.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import miuix.flexible.R;
import miuix.flexible.grid.strategy.DynamicColumnFixedCellWidthGridStrategy;
import miuix.flexible.grid.strategy.DynamicColumnFixedSpacingFullGridStrategy;
import miuix.flexible.grid.strategy.DynamicColumnFixedSpacingGridStrategy;
import miuix.flexible.grid.strategy.FixedColumnFixedSpacingGridStrategy;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes2.dex */
public class HyperGridLayout extends ViewGroup {
    public float mCellWidth;
    public int mColumnCount;
    public int mColumnMultiple;
    public float mColumnSpacing;
    public HyperGridConfiguration mConfiguration;
    public boolean mDisallowAutoColumnCount;
    public int mGravity;
    public float mMaxCellWidth;
    public float mMaxColumnSpacing;
    public float mMinCellWidth;
    public float mMinColumnSpacing;
    public float mRowSpacing;
    public float maxCellHeight;
    public int mode;

    public HyperGridLayout(Context context) {
        super(context);
        this.mode = 0;
        this.mMinColumnSpacing = 0.0f;
        this.mMaxColumnSpacing = Float.MAX_VALUE;
        this.mMaxCellWidth = Float.MAX_VALUE;
        this.mColumnCount = 1;
        this.mColumnMultiple = 1;
        this.mGravity = 0;
        this.mDisallowAutoColumnCount = false;
        init(context, null);
    }

    public HyperGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mode = 0;
        this.mMinColumnSpacing = 0.0f;
        this.mMaxColumnSpacing = Float.MAX_VALUE;
        this.mMaxCellWidth = Float.MAX_VALUE;
        this.mColumnCount = 1;
        this.mColumnMultiple = 1;
        this.mGravity = 0;
        this.mDisallowAutoColumnCount = false;
        init(context, attributeSet);
    }

    public HyperGridLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mode = 0;
        this.mMinColumnSpacing = 0.0f;
        this.mMaxColumnSpacing = Float.MAX_VALUE;
        this.mMaxCellWidth = Float.MAX_VALUE;
        this.mColumnCount = 1;
        this.mColumnMultiple = 1;
        this.mGravity = 0;
        this.mDisallowAutoColumnCount = false;
        init(context, attributeSet);
    }

    public float getCellWidth() {
        return this.mCellWidth;
    }

    public int getColumnCount() {
        return this.mColumnCount;
    }

    public int getColumnMultiple() {
        return this.mColumnMultiple;
    }

    public float getColumnSpacing() {
        return this.mColumnSpacing;
    }

    public boolean getDisallowAutoColumnCount() {
        return this.mDisallowAutoColumnCount;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public float getMaxCellWidth() {
        return this.mMaxCellWidth;
    }

    public float getMaxColumnSpacing() {
        return this.mMaxColumnSpacing;
    }

    public float getMinCellWidth() {
        return this.mMinCellWidth;
    }

    public float getMinColumnSpacing() {
        return this.mMinColumnSpacing;
    }

    public int getMode() {
        return this.mode;
    }

    public float getRowSpacing() {
        return this.mRowSpacing;
    }

    public final void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HyperGridLayout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R.styleable.HyperGridLayout_grid_mode) {
                    this.mode = typedArrayObtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.HyperGridLayout_android_gravity) {
                    this.mGravity = typedArrayObtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.HyperGridLayout_column_spacing) {
                    this.mColumnSpacing = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                } else if (index == R.styleable.HyperGridLayout_min_column_spacing) {
                    this.mMinColumnSpacing = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                } else if (index == R.styleable.HyperGridLayout_max_column_spacing) {
                    this.mMaxColumnSpacing = typedArrayObtainStyledAttributes.getDimension(index, Float.MAX_VALUE);
                } else if (index == R.styleable.HyperGridLayout_row_spacing) {
                    this.mRowSpacing = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                } else if (index == R.styleable.HyperGridLayout_cell_width) {
                    this.mCellWidth = typedArrayObtainStyledAttributes.getDimension(index, 1.0f);
                } else if (index == R.styleable.HyperGridLayout_min_cell_width) {
                    this.mMinCellWidth = typedArrayObtainStyledAttributes.getDimension(index, 1.0f);
                } else if (index == R.styleable.HyperGridLayout_max_cell_width) {
                    this.mMaxCellWidth = typedArrayObtainStyledAttributes.getDimension(index, Float.MAX_VALUE);
                } else if (index == R.styleable.HyperGridLayout_column_count) {
                    this.mColumnCount = typedArrayObtainStyledAttributes.getInt(index, 1);
                } else if (index == R.styleable.HyperGridLayout_column_multiple) {
                    this.mColumnMultiple = typedArrayObtainStyledAttributes.getInt(index, 1);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void measureChildView(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            view.measure(ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(i, 1073741824), 0, layoutParams.width), ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(i2, 0), 0, layoutParams.height));
        }
    }

    public void measureChildViewHeightForExactlyIfNeeded(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null || layoutParams.height != -1 || view.getMeasuredHeight() == i2) {
            return;
        }
        view.measure(ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(i, 1073741824), 0, layoutParams.width), ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), 0, layoutParams.height));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        HyperGridLayout hyperGridLayout;
        int childCount = getChildCount();
        int iMax = Math.max(1, this.mConfiguration.columnCount);
        float f = iMax;
        int iCeil = (int) Math.ceil(childCount / f);
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int paddingStart = getPaddingStart();
        int paddingTop = getPaddingTop();
        int i7 = this.mGravity;
        int i8 = i7 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
        int i9 = i7 & 7;
        if (i8 == 16) {
            float f2 = this.mRowSpacing;
            paddingTop = getPaddingTop() + ((((i6 - getPaddingTop()) - getPaddingBottom()) - ((int) ((iCeil * (this.maxCellHeight + f2)) - (iCeil > 0 ? f2 : 0.0f)))) / 2);
        } else if (i8 == 80) {
            float f3 = this.mRowSpacing;
            paddingTop = (i6 - ((int) ((iCeil * (this.maxCellHeight + f3)) - (iCeil > 0 ? f3 : 0.0f)))) - getPaddingBottom();
        }
        if (i9 == 1) {
            HyperGridConfiguration hyperGridConfiguration = this.mConfiguration;
            float f4 = hyperGridConfiguration.columnSpacing;
            paddingStart = getPaddingStart() + ((((i5 - getPaddingStart()) - getPaddingEnd()) - ((int) ((f * (hyperGridConfiguration.cellWidth + f4)) - f4))) / 2);
        } else if (i9 == 5) {
            HyperGridConfiguration hyperGridConfiguration2 = this.mConfiguration;
            float f5 = hyperGridConfiguration2.columnSpacing;
            paddingStart = (i5 - ((int) ((f * (hyperGridConfiguration2.cellWidth + f5)) - f5))) - getPaddingEnd();
        }
        int i10 = 0;
        int i11 = 0;
        while (i10 < childCount) {
            View childAt = this.getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                HyperGridConfiguration hyperGridConfiguration3 = this.mConfiguration;
                float f6 = hyperGridConfiguration3.columnSpacing;
                float f7 = hyperGridConfiguration3.cellWidth;
                int measuredWidth = (int) (paddingStart + ((i11 % iMax) * (f6 + f7)) + ((f7 - childAt.getMeasuredWidth()) / 2.0f));
                float f8 = this.mRowSpacing;
                float f9 = this.maxCellHeight;
                int measuredHeight = (int) (paddingTop + ((i11 / iMax) * (f8 + f9)) + ((f9 - childAt.getMeasuredHeight()) / 2.0f));
                int measuredWidth2 = measuredWidth + childAt.getMeasuredWidth();
                int measuredHeight2 = measuredHeight + childAt.getMeasuredHeight();
                hyperGridLayout = this;
                ViewUtils.layoutChildView(hyperGridLayout, childAt, measuredWidth, measuredHeight, measuredWidth2, measuredHeight2);
                i11++;
            } else {
                hyperGridLayout = this;
            }
            i10++;
            this = hyperGridLayout;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        HyperGridConfiguration hyperGridConfiguration;
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode == 0) {
            Log.w("HyperGridLayout", "The width mode of the HyperGridLayout can not be UNSPECIFIED! Container width must be determined.");
        }
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            if (getChildAt(i4).getVisibility() != 8) {
                i3++;
            }
        }
        int paddingStart = (size - getPaddingStart()) - getPaddingEnd();
        if (!this.mDisallowAutoColumnCount || (hyperGridConfiguration = this.mConfiguration) == null) {
            int i5 = this.mode;
            if (i5 == 1) {
                recycleHyperGridConfiguration(this.mConfiguration);
                this.mConfiguration = DynamicColumnFixedSpacingFullGridStrategy.getConfiguration(paddingStart, this.mColumnSpacing, this.mMinCellWidth, this.mMaxCellWidth, i3);
            } else if (i5 == 2) {
                recycleHyperGridConfiguration(this.mConfiguration);
                this.mConfiguration = DynamicColumnFixedCellWidthGridStrategy.getConfiguration(paddingStart, this.mMinColumnSpacing, this.mMaxColumnSpacing, this.mCellWidth, this.mColumnMultiple);
            } else if (i5 == 4) {
                recycleHyperGridConfiguration(this.mConfiguration);
                this.mConfiguration = FixedColumnFixedSpacingGridStrategy.getConfiguration(paddingStart, this.mColumnCount, this.mColumnSpacing);
            } else {
                recycleHyperGridConfiguration(this.mConfiguration);
                this.mConfiguration = DynamicColumnFixedSpacingGridStrategy.getConfiguration(paddingStart, this.mColumnSpacing, this.mMinCellWidth, this.mMaxCellWidth, this.mColumnMultiple);
            }
        } else {
            recycleHyperGridConfiguration(hyperGridConfiguration);
            HyperGridConfiguration hyperGridConfiguration2 = this.mConfiguration;
            this.mConfiguration = FixedColumnFixedSpacingGridStrategy.getConfiguration(paddingStart, hyperGridConfiguration2.columnCount, hyperGridConfiguration2.columnSpacing);
        }
        HyperGridConfiguration hyperGridConfiguration3 = this.mConfiguration;
        hyperGridConfiguration3.columnCount = Math.max(1, hyperGridConfiguration3.columnCount);
        HyperGridConfiguration hyperGridConfiguration4 = this.mConfiguration;
        hyperGridConfiguration4.cellWidth = Math.max(0.0f, hyperGridConfiguration4.cellWidth);
        HyperGridConfiguration hyperGridConfiguration5 = this.mConfiguration;
        hyperGridConfiguration5.columnSpacing = Math.max(0.0f, hyperGridConfiguration5.columnSpacing);
        this.maxCellHeight = 0.0f;
        int iMin = Integer.MAX_VALUE;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                measureChildView(childAt, (int) Math.ceil(this.mConfiguration.cellWidth), size2);
                this.maxCellHeight = Math.max(this.maxCellHeight, childAt.getMeasuredHeight());
                iMin = Math.min(iMin, childAt.getMeasuredHeight());
            }
        }
        if (iMin != this.maxCellHeight) {
            uniformChildViewHeightIfNeeded((int) Math.ceil(this.mConfiguration.cellWidth), (int) this.maxCellHeight);
        }
        int iCeil = (int) Math.ceil(((double) i3) / ((double) this.mConfiguration.columnCount));
        if (mode2 != 1073741824) {
            float f = this.maxCellHeight;
            float f2 = this.mRowSpacing;
            size2 = (int) (((iCeil * (f + f2)) - (iCeil > 0 ? f2 : 0.0f)) + getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(size, size2);
    }

    public final void recycleHyperGridConfiguration(HyperGridConfiguration hyperGridConfiguration) {
        if (hyperGridConfiguration != null) {
            hyperGridConfiguration.recycle();
        }
    }

    public void setCellWidth(float f) {
        this.mCellWidth = f;
        requestLayout();
    }

    public void setColumnCount(int i) {
        this.mColumnCount = i;
        requestLayout();
    }

    public void setColumnMultiple(int i) {
        this.mColumnMultiple = i;
        requestLayout();
    }

    public void setColumnSpacing(float f) {
        this.mColumnSpacing = f;
        requestLayout();
    }

    public void setDisallowAutoColumnCount(boolean z) {
        this.mDisallowAutoColumnCount = z;
        if (z) {
            return;
        }
        requestLayout();
    }

    public void setGravity(int i) {
        this.mGravity = i;
        requestLayout();
    }

    public void setMaxCellWidth(float f) {
        this.mMaxCellWidth = f;
        requestLayout();
    }

    public void setMaxColumnSpacing(float f) {
        this.mMaxColumnSpacing = f;
        requestLayout();
    }

    public void setMinCellWidth(float f) {
        this.mMinCellWidth = f;
        requestLayout();
    }

    public void setMinColumnSpacing(float f) {
        this.mMinColumnSpacing = f;
        requestLayout();
    }

    public void setMode(int i) {
        this.mode = i;
        requestLayout();
    }

    public void setRowSpacing(float f) {
        this.mRowSpacing = f;
        requestLayout();
    }

    public void uniformChildViewHeightIfNeeded(int i, int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                measureChildViewHeightForExactlyIfNeeded(childAt, i, i2);
            }
        }
    }
}

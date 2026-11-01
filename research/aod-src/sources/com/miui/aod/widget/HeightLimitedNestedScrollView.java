package com.miui.aod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import com.miui.aod.R;

/* JADX INFO: loaded from: classes.dex */
public class HeightLimitedNestedScrollView extends NestedScrollView {
    public int mMaxHeight;

    public HeightLimitedNestedScrollView(@NonNull Context context) {
        super(context);
        this.mMaxHeight = -1;
    }

    public HeightLimitedNestedScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxHeight = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.view_attr_addition);
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            this.mMaxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    @Override // androidx.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3 = this.mMaxHeight;
        if (i3 > 0 && i3 < View.MeasureSpec.getSize(i2)) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.mMaxHeight, Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
    }

    public void setMaxHeight(int i) {
        if (this.mMaxHeight != i) {
            this.mMaxHeight = i;
            requestLayout();
        }
    }
}

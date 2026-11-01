package miuix.appcompat.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes2.dex */
class DateTimePickerContainer extends LinearLayout {
    public int mInitialMeasuredHeight;

    public DateTimePickerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInitialMeasuredHeight = 0;
    }

    public DateTimePickerContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInitialMeasuredHeight = 0;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View childAt = getChildAt(1);
        if (childAt == null || childAt.getVisibility() != 0) {
            return;
        }
        int i5 = i3 - i;
        int measuredHeight = childAt.getMeasuredHeight();
        int i6 = ((i4 - i2) - measuredHeight) >> 1;
        childAt.layout(0, i6, i5, measuredHeight + i6);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mInitialMeasuredHeight == 0) {
            this.mInitialMeasuredHeight = getMeasuredHeight();
        } else {
            setMeasuredDimension(View.MeasureSpec.getSize(i), this.mInitialMeasuredHeight);
        }
    }
}

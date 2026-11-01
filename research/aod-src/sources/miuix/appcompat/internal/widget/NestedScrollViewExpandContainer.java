package miuix.appcompat.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/* JADX INFO: loaded from: classes2.dex */
public class NestedScrollViewExpandContainer extends NestedScrollView {
    public NestedScrollViewExpandContainer(@NonNull Context context) {
        super(context);
    }

    public NestedScrollViewExpandContainer(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NestedScrollViewExpandContainer(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public void measureChild(View view, int i, int i2) {
        ((NestedScrollViewExpander) view).setParentHeightMeasureSpec(i2);
        view.forceLayout();
        super.measureChild(view, i, i2);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ((NestedScrollViewExpander) view).setParentHeightMeasureSpec(i3);
        view.forceLayout();
        super.measureChildWithMargins(view, i, i2, i3, i4);
    }
}

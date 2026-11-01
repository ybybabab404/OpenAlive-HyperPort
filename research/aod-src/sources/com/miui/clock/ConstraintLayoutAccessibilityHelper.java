package com.miui.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintLayoutAccessibilityHelper extends ConstraintHelper {
    public ConstraintLayoutAccessibilityHelper(Context context) {
        super(context);
        init();
    }

    public ConstraintLayoutAccessibilityHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ConstraintLayoutAccessibilityHelper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public final void init() {
        setImportantForAccessibility(1);
        setScreenReaderFocusable(true);
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        ViewParent parent = getParent();
        if (!(parent instanceof ConstraintLayout)) {
            return;
        }
        ConstraintLayout constraintLayout = (ConstraintLayout) parent;
        accessibilityEvent.getText().clear();
        int i = 0;
        while (true) {
            int[] iArr = this.mIds;
            if (i >= iArr.length) {
                return;
            }
            View viewById = constraintLayout.getViewById(iArr[i]);
            if (viewById != null && viewById.getVisibility() == 0) {
                viewById.onPopulateAccessibilityEvent(accessibilityEvent);
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePreLayout(ConstraintLayout constraintLayout) {
        super.updatePreLayout(constraintLayout);
        int i = 0;
        while (true) {
            int[] iArr = this.mIds;
            if (i >= iArr.length) {
                return;
            }
            View viewById = constraintLayout.getViewById(iArr[i]);
            if (viewById != null) {
                viewById.setImportantForAccessibility(2);
            }
            i++;
        }
    }
}

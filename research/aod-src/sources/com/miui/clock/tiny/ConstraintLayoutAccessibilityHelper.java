package com.miui.clock.tiny;

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

    private void init() {
        setImportantForAccessibility(1);
        setScreenReaderFocusable(true);
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        ViewParent parent = getParent();
        if (parent instanceof ConstraintLayout) {
            ConstraintLayout constraintLayout = (ConstraintLayout) parent;
            accessibilityEvent.getText().clear();
            for (int i : this.mIds) {
                View viewById = constraintLayout.getViewById(i);
                if (viewById != null && viewById.getVisibility() == 0) {
                    viewById.onPopulateAccessibilityEvent(accessibilityEvent);
                }
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePreLayout(ConstraintLayout constraintLayout) {
        super.updatePreLayout(constraintLayout);
        for (int i : this.mIds) {
            View viewById = constraintLayout.getViewById(i);
            if (viewById != null) {
                viewById.setImportantForAccessibility(2);
            }
        }
    }
}

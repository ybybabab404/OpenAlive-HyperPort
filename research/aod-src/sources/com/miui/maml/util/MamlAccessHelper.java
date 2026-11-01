package com.miui.maml.util;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.miui.maml.CommandTriggers;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.elements.AnimatedScreenElement;
import com.miui.maml.elements.ButtonScreenElement;
import com.xiaomi.onetrack.h.d.a;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MamlAccessHelper extends ExploreByTouchHelper {
    private static final String TAG = "MamlAccessHelper";
    View mHostView;
    ScreenElementRoot mRoot;

    public MamlAccessHelper(ScreenElementRoot screenElementRoot, View view) {
        super(view);
        this.mRoot = screenElementRoot;
        this.mHostView = view;
        screenElementRoot.setMamlAccessHelper(this);
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    public int getVirtualViewAt(float f, float f2) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot == null) {
            return Integer.MIN_VALUE;
        }
        List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
        for (int size = accessibleElements.size() - 1; size >= 0; size--) {
            AnimatedScreenElement animatedScreenElement = accessibleElements.get(size);
            if (animatedScreenElement.isVisible() && animatedScreenElement.touched(f, f2)) {
                return size;
            }
        }
        return Integer.MIN_VALUE;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    public void getVisibleVirtualViews(List<Integer> list) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
            for (int i = 0; i < accessibleElements.size(); i++) {
                if (accessibleElements.get(i).isVisible()) {
                    list.add(Integer.valueOf(i));
                }
            }
        }
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    public boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot == null || i2 != 16) {
            return false;
        }
        List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
        if (i >= 0 && i < accessibleElements.size()) {
            AnimatedScreenElement animatedScreenElement = accessibleElements.get(i);
            animatedScreenElement.performAction(a.j);
            if (!(animatedScreenElement instanceof ButtonScreenElement)) {
                return true;
            }
            ((ButtonScreenElement) animatedScreenElement).onActionUp();
            return true;
        }
        return false;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
            if (i < 0 || i >= accessibleElements.size()) {
                return;
            }
            accessibilityEvent.setContentDescription(accessibleElements.get(i).getContentDescription());
        }
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    public void onPopulateNodeForVirtualView(int i, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        String str = "";
        if (screenElementRoot == null) {
            MamlLog.e(TAG, "mRoot == null, add an empty content description");
            accessibilityNodeInfoCompat.setContentDescription("");
            accessibilityNodeInfoCompat.setBoundsInParent(new Rect(0, 0, 0, 0));
            return;
        }
        List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
        if (i < 0 || i >= accessibleElements.size()) {
            MamlLog.e(TAG, "virtualViewId not found " + i);
            accessibilityNodeInfoCompat.setContentDescription("");
            accessibilityNodeInfoCompat.setBoundsInParent(new Rect(0, 0, 0, 0));
            return;
        }
        AnimatedScreenElement animatedScreenElement = accessibleElements.get(i);
        String contentDescription = animatedScreenElement.getContentDescription();
        if (contentDescription == null) {
            MamlLog.e(TAG, "element.getContentDescription() == null " + animatedScreenElement.getName());
        } else {
            str = contentDescription;
        }
        accessibilityNodeInfoCompat.setContentDescription(str);
        CommandTriggers triggers = animatedScreenElement.getTriggers();
        if ((animatedScreenElement instanceof ButtonScreenElement) || (triggers != null && (triggers.find(a.j) != null || triggers.find("down") != null))) {
            accessibilityNodeInfoCompat.addAction(16);
        }
        accessibilityNodeInfoCompat.setBoundsInParent(new Rect((int) animatedScreenElement.getAbsoluteLeft(), (int) animatedScreenElement.getAbsoluteTop(), (int) (animatedScreenElement.getAbsoluteLeft() + animatedScreenElement.getWidth()), (int) (animatedScreenElement.getAbsoluteTop() + animatedScreenElement.getHeight())));
    }

    public void performAccessibilityAction(final int i, final int i2) {
        this.mHostView.post(new Runnable() { // from class: com.miui.maml.util.MamlAccessHelper.1
            @Override // java.lang.Runnable
            public void run() {
                MamlAccessHelper mamlAccessHelper = MamlAccessHelper.this;
                mamlAccessHelper.getAccessibilityNodeProvider(mamlAccessHelper.mHostView).performAction(i, i2, null);
            }
        });
    }

    public void removeRoot() {
        this.mRoot = null;
    }
}

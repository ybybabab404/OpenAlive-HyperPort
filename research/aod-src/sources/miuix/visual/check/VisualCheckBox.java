package miuix.visual.check;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes3.dex */
public class VisualCheckBox extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mChecked;
    public OnCheckedChangeListener mOnCheckedChangeWidgetListener;
    public PassThroughHierarchyChangeListener mPassThroughListener;
    public List mVisualCheckItems;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(VisualCheckBox visualCheckBox, boolean z);
    }

    public class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        public PassThroughHierarchyChangeListener() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            VisualCheckBox visualCheckBox = VisualCheckBox.this;
            if (view == visualCheckBox && (view2 instanceof VisualCheckItem)) {
                visualCheckBox.mVisualCheckItems.add((VisualCheckItem) view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            VisualCheckBox visualCheckBox = VisualCheckBox.this;
            if (view == visualCheckBox && (view2 instanceof VisualCheckItem)) {
                visualCheckBox.mVisualCheckItems.remove(view2);
            }
        }
    }

    public VisualCheckBox(Context context) {
        this(context, null);
    }

    public VisualCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VisualCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVisualCheckItems = new ArrayList();
        setOrientation(1);
        PassThroughHierarchyChangeListener passThroughHierarchyChangeListener = new PassThroughHierarchyChangeListener();
        this.mPassThroughListener = passThroughHierarchyChangeListener;
        super.setOnHierarchyChangeListener(passThroughHierarchyChangeListener);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return RadioButton.class.getName();
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public final void notifyChecked(boolean z) {
        Iterator it = this.mVisualCheckItems.iterator();
        while (it.hasNext()) {
            ((VisualCheckItem) it.next()).onChecked(z);
        }
    }

    public final void notifyTouchEvent(MotionEvent motionEvent) {
        Iterator it = this.mVisualCheckItems.iterator();
        while (it.hasNext()) {
            ((VisualCheckItem) it.next()).onVisualCheckBoxTouchEvent(this, motionEvent);
        }
    }

    public final CharSequence obtainDescriptionFromChild() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                if (!TextUtils.isEmpty(childAt.getContentDescription())) {
                    return childAt.getContentDescription();
                }
                if (childAt instanceof TextView) {
                    return ((TextView) childAt).getText();
                }
            }
        }
        return null;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        if (TextUtils.isEmpty(getContentDescription())) {
            CharSequence charSequenceObtainDescriptionFromChild = obtainDescriptionFromChild();
            if (TextUtils.isEmpty(charSequenceObtainDescriptionFromChild)) {
                return;
            }
            setContentDescription(charSequenceObtainDescriptionFromChild);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setChecked(this.mChecked);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClickable(!this.mChecked);
        accessibilityNodeInfo.setCheckable(!this.mChecked);
        accessibilityNodeInfo.setSelected(this.mChecked);
        accessibilityNodeInfo.setChecked(this.mChecked);
        CharSequence charSequenceObtainDescriptionFromChild = obtainDescriptionFromChild();
        if (TextUtils.isEmpty(charSequenceObtainDescriptionFromChild)) {
            return;
        }
        accessibilityNodeInfo.setText(charSequenceObtainDescriptionFromChild);
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequenceObtainDescriptionFromChild = obtainDescriptionFromChild();
        if (TextUtils.isEmpty(charSequenceObtainDescriptionFromChild)) {
            return;
        }
        accessibilityEvent.getText().add(charSequenceObtainDescriptionFromChild);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mVisualCheckItems.size() <= 0) {
            return super.onTouchEvent(motionEvent);
        }
        notifyTouchEvent(motionEvent);
        if (motionEvent.getAction() == 1) {
            toggle();
            HapticCompat.performHapticFeedback(this, HapticFeedbackConstants.MIUI_BUTTON_LARGE, HapticFeedbackConstants.MIUI_MESH_NORMAL);
        }
        return true;
    }

    @Override // android.view.View
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            notifyChecked(z);
            OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeWidgetListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, this.mChecked);
            }
        }
    }

    public void setOnCheckedChangeWidgetListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeWidgetListener = onCheckedChangeListener;
    }

    public void toggle() {
        if (isChecked()) {
            return;
        }
        setChecked(!this.mChecked);
        sendAccessibilityEvent(32768);
    }
}

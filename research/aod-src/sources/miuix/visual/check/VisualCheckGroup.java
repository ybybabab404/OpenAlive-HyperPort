package miuix.visual.check;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import miuix.visualcheck.R;

/* JADX INFO: loaded from: classes3.dex */
public class VisualCheckGroup extends FlowLayout {
    public int mCheckedId;
    public VisualCheckBox.OnCheckedChangeListener mChildOnCheckedChangeListener;
    public OnCheckedChangeListener mOnCheckedChangeListener;
    public PassThroughHierarchyChangeListener mPassThroughListener;
    public boolean mProtectFromCheckedChange;

    public class CheckedStateTracker implements VisualCheckBox.OnCheckedChangeListener {
        public CheckedStateTracker() {
        }

        @Override // miuix.visual.check.VisualCheckBox.OnCheckedChangeListener
        public void onCheckedChanged(VisualCheckBox visualCheckBox, boolean z) {
            if (VisualCheckGroup.this.mProtectFromCheckedChange) {
                return;
            }
            VisualCheckGroup.this.mProtectFromCheckedChange = true;
            if (VisualCheckGroup.this.mCheckedId != -1) {
                VisualCheckGroup visualCheckGroup = VisualCheckGroup.this;
                visualCheckGroup.setCheckedStateForView(visualCheckGroup.mCheckedId, false);
            }
            VisualCheckGroup.this.mProtectFromCheckedChange = false;
            VisualCheckGroup.this.setCheckedId(visualCheckBox.getId());
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(VisualCheckGroup visualCheckGroup, int i);
    }

    public class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        public PassThroughHierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            if (view == VisualCheckGroup.this && (view2 instanceof VisualCheckBox)) {
                if (view2.getId() == -1) {
                    view2.setId(View.generateViewId());
                }
                ((VisualCheckBox) view2).setOnCheckedChangeWidgetListener(VisualCheckGroup.this.mChildOnCheckedChangeListener);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            if (view == VisualCheckGroup.this && (view2 instanceof VisualCheckBox)) {
                ((VisualCheckBox) view2).setOnCheckedChangeWidgetListener(null);
            }
        }
    }

    public VisualCheckGroup(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCheckedId = -1;
        this.mProtectFromCheckedChange = false;
        PassThroughHierarchyChangeListener passThroughHierarchyChangeListener = new PassThroughHierarchyChangeListener();
        this.mPassThroughListener = passThroughHierarchyChangeListener;
        super.setOnHierarchyChangeListener(passThroughHierarchyChangeListener);
        this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VisualCheckGroup);
        this.mCheckedId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.VisualCheckGroup_checkedButton, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedId(@IdRes int i) {
        this.mCheckedId = i;
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, i);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        int i = this.mCheckedId;
        if (i != -1) {
            this.mProtectFromCheckedChange = true;
            setCheckedStateForView(i, true);
            this.mProtectFromCheckedChange = false;
            setCheckedId(this.mCheckedId);
        }
    }

    public final void setCheckedStateForView(int i, boolean z) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById == null || !(viewFindViewById instanceof VisualCheckBox)) {
            return;
        }
        ((VisualCheckBox) viewFindViewById).setChecked(z);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }
}

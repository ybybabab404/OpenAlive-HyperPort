package miuix.flexible.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import miuix.flexible.R;
import miuix.flexible.template.IHyperCellTemplate;
import miuix.flexible.template.SimpleMarkTemplate;
import miuix.flexible.template.TemplateFactory;

/* JADX INFO: loaded from: classes2.dex */
public class HyperCellLayout extends ViewGroup {
    public IHyperCellTemplate mTemplate;

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int animSpec;
        public int animationGravity;
        public float animationProgress;
        public int areaId;
        public int customParams;
        public int gravity;
        public int groupPriority;
        public float groupWeight;
        public boolean isAnimating;
        public int mark;
        public int order;
        public int priority;
        public float weight;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
            this.isAnimating = false;
            this.animSpec = 7;
            this.animationGravity = 8388611;
            this.customParams = 0;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            this.isAnimating = false;
            this.animSpec = 7;
            this.animationGravity = 8388611;
            this.customParams = 0;
            if (attributeSet != null) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HyperCellLayout_Layout);
                int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = typedArrayObtainStyledAttributes.getIndex(i);
                    if (index == R.styleable.HyperCellLayout_Layout_mark) {
                        int i2 = typedArrayObtainStyledAttributes.getInt(index, 1);
                        this.mark = i2;
                        if (i2 < 1) {
                            throw new IllegalArgumentException("Layout Parameter 'mark' can not be smaller than 1");
                        }
                    } else if (index == R.styleable.HyperCellLayout_Layout_node_weight) {
                        this.weight = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
                    } else if (index == R.styleable.HyperCellLayout_Layout_group_weight) {
                        this.groupWeight = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
                    } else if (index == R.styleable.HyperCellLayout_Layout_android_layout_gravity) {
                        this.gravity = typedArrayObtainStyledAttributes.getInt(index, 0);
                    } else if (index == R.styleable.HyperCellLayout_Layout_node_order) {
                        this.order = typedArrayObtainStyledAttributes.getInt(index, 0);
                    } else if (index == R.styleable.HyperCellLayout_Layout_node_priority) {
                        this.priority = typedArrayObtainStyledAttributes.getInt(index, 0);
                    } else if (index == R.styleable.HyperCellLayout_Layout_group_priority) {
                        this.groupPriority = typedArrayObtainStyledAttributes.getInt(index, 0);
                    } else if (index == R.styleable.HyperCellLayout_Layout_area_id) {
                        this.areaId = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                    } else if (index == R.styleable.HyperCellLayout_Layout_custom_params) {
                        this.customParams = typedArrayObtainStyledAttributes.getInt(index, 0);
                    }
                }
                typedArrayObtainStyledAttributes.recycle();
            }
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
            this.isAnimating = false;
            this.animSpec = 7;
            this.animationGravity = 8388611;
            this.customParams = 0;
        }

        public int getAnimSpec() {
            return this.animSpec;
        }

        public float getAnimationProgress() {
            return this.animationProgress;
        }

        public int getAreaId() {
            return this.areaId;
        }

        public int getCustomParams() {
            return this.customParams;
        }

        public int getGravity() {
            return this.gravity;
        }

        public int getGroupPriority() {
            return this.groupPriority;
        }

        public float getGroupWeight() {
            return this.groupWeight;
        }

        public int getMark() {
            return this.mark;
        }

        public int getOrder() {
            return this.order;
        }

        public int getPriority() {
            return this.priority;
        }

        public float getWeight() {
            return this.weight;
        }

        public boolean isAnimating() {
            return this.isAnimating;
        }

        public LayoutParams setAnimSpec(int i) {
            this.animSpec = i;
            return this;
        }

        public void setAnimating(boolean z) {
            this.isAnimating = z;
        }

        public LayoutParams setAnimationGravity(int i) {
            this.animationGravity = i;
            return this;
        }

        public LayoutParams setAreaId(int i) {
            this.areaId = i;
            return this;
        }

        public LayoutParams setGravity(int i) {
            this.gravity = i;
            return this;
        }

        public LayoutParams setGroupPriority(int i) {
            this.groupPriority = i;
            return this;
        }

        public LayoutParams setGroupWeight(float f) {
            this.groupWeight = f;
            return this;
        }

        public LayoutParams setMargin(int i, int i2, int i3, int i4) {
            setMarginStart(i);
            setMarginEnd(i3);
            ((ViewGroup.MarginLayoutParams) this).topMargin = i2;
            ((ViewGroup.MarginLayoutParams) this).bottomMargin = i4;
            return this;
        }

        public LayoutParams setMark(int i) {
            this.mark = i;
            return this;
        }

        public LayoutParams setOrder(int i) {
            this.order = i;
            return this;
        }

        public LayoutParams setPriority(int i) {
            this.priority = i;
            return this;
        }

        public LayoutParams setWeight(float f) {
            this.weight = f;
            return this;
        }
    }

    public interface LevelCallback {
        void onLevelApply(int i, Object... objArr);
    }

    public HyperCellLayout(Context context) {
        super(context);
        init(context, null);
    }

    public HyperCellLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public HyperCellLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public IHyperCellTemplate createTemplate(Context context, String str, AttributeSet attributeSet) {
        return TemplateFactory.get(context, str);
    }

    public View findViewByAreaId(int i) {
        if (i == -1) {
            return null;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if ((layoutParams instanceof LayoutParams) && ((LayoutParams) layoutParams).getAreaId() == i) {
                return childAt;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getLevel() {
        return this.mTemplate.getLevel();
    }

    public IHyperCellTemplate getTemplate() {
        return this.mTemplate;
    }

    public final void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HyperCellLayout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R.styleable.HyperCellLayout_template) {
                    this.mTemplate = createTemplate(context, typedArrayObtainStyledAttributes.getString(index), attributeSet);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        if (this.mTemplate == null) {
            this.mTemplate = new SimpleMarkTemplate();
        }
        this.mTemplate.init(this, context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTemplate.onAttachedToWindow(this);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mTemplate.onConfigurationChanged(this, configuration);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTemplate.onDetachedFromWindow(this);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTemplate.onFinishInflate(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mTemplate.onLayout(this, z, i, i2, i3, i4);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int[] iArrOnMeasure = this.mTemplate.onMeasure(this, i, i2);
        setMeasuredDimension(iArrOnMeasure[0], iArrOnMeasure[1]);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        this.mTemplate.onViewAdded(this, view);
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.mTemplate.onViewRemoved(this, view);
    }

    public void setLevelCallback(LevelCallback levelCallback) {
        this.mTemplate.setLevelCallback(levelCallback);
    }
}

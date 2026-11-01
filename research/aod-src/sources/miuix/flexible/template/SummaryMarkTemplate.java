package miuix.flexible.template;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import miuix.flexible.R;
import miuix.flexible.view.HyperCellLayout;

/* JADX INFO: loaded from: classes2.dex */
public class SummaryMarkTemplate extends AbstractMarkTemplate {
    private static final SparseArray<HyperCellLayout.LayoutParams> LARGE_PARAMS;
    private static final SparseArray<HyperCellLayout.LayoutParams> NORMAL_PARAMS;

    static {
        SparseArray<HyperCellLayout.LayoutParams> sparseArray = new SparseArray<>();
        NORMAL_PARAMS = sparseArray;
        int i = R.id.area_title;
        sparseArray.put(i, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 1.0f, 16, 0));
        int i2 = R.id.area_content;
        sparseArray.put(i2, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 1, 0, 10, 0, 0));
        int i3 = R.id.view_auxiliary;
        sparseArray.put(i3, AbstractMarkTemplate.generateLayoutParams(3, 0.0f, 0.0f, 0, 2));
        int i4 = R.id.area_comment;
        sparseArray.put(i4, AbstractMarkTemplate.generateLayoutParams(3, 0.0f, 1.0f, 17, 3, 0, 10, 0, 0));
        int i5 = R.id.area_status;
        sparseArray.put(i5, AbstractMarkTemplate.generateLayoutParams(3, 0.0f, 0.0f, 16, 4, 10, 10, 0, 0));
        int i6 = R.id.area_end;
        sparseArray.put(i6, AbstractMarkTemplate.generateLayoutParams(1, 0.0f, 0.0f, 17, 5, 10, 0, 0, 0));
        SparseArray<HyperCellLayout.LayoutParams> sparseArray2 = new SparseArray<>();
        LARGE_PARAMS = sparseArray2;
        sparseArray2.put(i, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 1.0f, 16, 0));
        sparseArray2.put(i2, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 1, 0, 10, 0, 0));
        sparseArray2.put(i3, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 2));
        sparseArray2.put(i4, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 3, 0, 10, 0, 0));
        sparseArray2.put(i5, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 4, 0, 10, 0, 0));
        sparseArray2.put(i6, AbstractMarkTemplate.generateLayoutParams(1, 0.0f, 0.0f, 17, 5, 10, 0, 0, 0));
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate
    public HyperCellLayout.LayoutParams getLayoutParams(View view) {
        HyperCellLayout.LayoutParams childViewLayoutParamsSafe = getChildViewLayoutParamsSafe(view);
        int areaId = childViewLayoutParamsSafe.getAreaId();
        HyperCellLayout.LayoutParams layoutParams = getLevel() == 1 ? NORMAL_PARAMS.get(areaId) : LARGE_PARAMS.get(areaId);
        return layoutParams == null ? childViewLayoutParamsSafe : layoutParams;
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate
    public void onAddAuxiliaryViews(ViewGroup viewGroup) {
        super.onAddAuxiliaryViews(viewGroup);
        AbstractMarkTemplate.addAuxiliaryView(viewGroup, this.mContext, R.id.view_auxiliary);
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate
    public void onPreBuildViewTree(ViewGroup viewGroup) {
        super.onPreBuildViewTree(viewGroup);
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            HyperCellLayout.LayoutParams childViewLayoutParamsSafe = getChildViewLayoutParamsSafe(childAt);
            HyperCellLayout.LayoutParams layoutParams = getLayoutParams(childAt);
            setGravity(childViewLayoutParamsSafe, layoutParams);
            setMargin(childViewLayoutParamsSafe, layoutParams);
            setPriority(childViewLayoutParamsSafe, layoutParams);
        }
    }
}

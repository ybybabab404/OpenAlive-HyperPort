package miuix.flexible.template;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import miuix.flexible.R;
import miuix.flexible.template.level.LevelSupplier;
import miuix.flexible.view.HyperCellLayout;

/* JADX INFO: loaded from: classes2.dex */
public class TernaryLayoutTemplate extends AbstractMarkTemplate {
    public static final int LEVEL_NARROW = 1;
    public static final int LEVEL_REGULAR = 2;
    public static final int LEVEL_WIDE = 3;
    private static final SparseArray<HyperCellLayout.LayoutParams> NARROW_PARAMS;
    private static final SparseArray<HyperCellLayout.LayoutParams> REGULAR_PARAMS;
    public static final int UNDEFINED = -1;
    private static final SparseArray<HyperCellLayout.LayoutParams> WIDE_PARAMS;
    private ViewGroup mContainer;
    private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
    private int regularWidthThreshold = -1;
    private int wideWidthThreshold = -1;
    private int mCurrentLevel = -1;

    /* JADX INFO: renamed from: $r8$lambda$aN9S91pnvHOqbBGKJok0Il-9d8U, reason: not valid java name */
    public static /* synthetic */ boolean m1334$r8$lambda$aN9S91pnvHOqbBGKJok0Il9d8U(TernaryLayoutTemplate ternaryLayoutTemplate, ViewGroup viewGroup) {
        int level = ternaryLayoutTemplate.getLevel();
        if (ternaryLayoutTemplate.mCurrentLevel == level) {
            return true;
        }
        ternaryLayoutTemplate.mCurrentLevel = level;
        ternaryLayoutTemplate.onPreBuildViewTree(viewGroup);
        ternaryLayoutTemplate.buildViewTree(viewGroup);
        viewGroup.requestLayout();
        ternaryLayoutTemplate.applyLevel();
        return false;
    }

    public static /* synthetic */ int $r8$lambda$pgdPWf7m6rGbsUp7kchVhVX98Kc(TernaryLayoutTemplate ternaryLayoutTemplate) {
        ViewGroup viewGroup = ternaryLayoutTemplate.mContainer;
        int measuredWidth = viewGroup != null ? viewGroup.getMeasuredWidth() : 0;
        if (measuredWidth >= ternaryLayoutTemplate.wideWidthThreshold) {
            return 3;
        }
        return measuredWidth >= ternaryLayoutTemplate.regularWidthThreshold ? 2 : 1;
    }

    static {
        SparseArray<HyperCellLayout.LayoutParams> sparseArray = new SparseArray<>();
        NARROW_PARAMS = sparseArray;
        int i = R.id.area_item1;
        sparseArray.put(i, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 0));
        int i2 = R.id.area_item2;
        sparseArray.put(i2, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 1));
        int i3 = R.id.area_item3;
        sparseArray.put(i3, AbstractMarkTemplate.generateLayoutParams(2, 0.0f, 0.0f, 0, 2));
        SparseArray<HyperCellLayout.LayoutParams> sparseArray2 = new SparseArray<>();
        REGULAR_PARAMS = sparseArray2;
        sparseArray2.put(i, AbstractMarkTemplate.generateLayoutParams(1, 1.0f, 0.0f, 0, 0));
        sparseArray2.put(i2, AbstractMarkTemplate.generateLayoutParams(2, 1.0f, 1.0f, 0, 1));
        sparseArray2.put(i3, AbstractMarkTemplate.generateLayoutParams(2, 1.0f, 0.0f, 0, 2));
        SparseArray<HyperCellLayout.LayoutParams> sparseArray3 = new SparseArray<>();
        WIDE_PARAMS = sparseArray3;
        sparseArray3.put(i, AbstractMarkTemplate.generateLayoutParams(1, 1.0f, 0.0f, 0, 0));
        sparseArray3.put(i2, AbstractMarkTemplate.generateLayoutParams(1, 1.0f, 0.0f, 0, 1));
        sparseArray3.put(i3, AbstractMarkTemplate.generateLayoutParams(1, 1.0f, 0.0f, 0, 2));
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate
    public LevelSupplier createLevelSupplier() {
        return new LevelSupplier() { // from class: miuix.flexible.template.TernaryLayoutTemplate$$ExternalSyntheticLambda1
            @Override // miuix.flexible.template.level.LevelSupplier
            public final int getLevel() {
                return TernaryLayoutTemplate.$r8$lambda$pgdPWf7m6rGbsUp7kchVhVX98Kc(this.f$0);
            }
        };
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate
    public HyperCellLayout.LayoutParams getLayoutParams(View view) {
        HyperCellLayout.LayoutParams childViewLayoutParamsSafe = getChildViewLayoutParamsSafe(view);
        int areaId = childViewLayoutParamsSafe.getAreaId();
        int level = getLevel();
        if (level == 1) {
            return NARROW_PARAMS.get(areaId);
        }
        if (level != 2) {
            return level != 3 ? childViewLayoutParamsSafe : WIDE_PARAMS.get(areaId);
        }
        return REGULAR_PARAMS.get(areaId);
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate, miuix.flexible.template.IHyperCellTemplate
    public void onAttachedToWindow(final ViewGroup viewGroup) {
        super.onAttachedToWindow(viewGroup);
        this.mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: miuix.flexible.template.TernaryLayoutTemplate$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                return TernaryLayoutTemplate.m1334$r8$lambda$aN9S91pnvHOqbBGKJok0Il9d8U(this.f$0, viewGroup);
            }
        };
        viewGroup.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate, miuix.flexible.template.IHyperCellTemplate
    public void onDetachedFromWindow(ViewGroup viewGroup) {
        if (this.mOnPreDrawListener != null) {
            viewGroup.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
            this.mOnPreDrawListener = null;
        }
        super.onDetachedFromWindow(viewGroup);
    }

    @Override // miuix.flexible.template.AbstractMarkTemplate, miuix.flexible.template.IHyperCellTemplate
    public void onFinishInflate(ViewGroup viewGroup) {
        this.mContainer = viewGroup;
        super.onFinishInflate(viewGroup);
        if (this.regularWidthThreshold == -1) {
            this.regularWidthThreshold = dp2px(600.0f);
        }
        if (this.wideWidthThreshold == -1) {
            this.wideWidthThreshold = dp2px(960.0f);
        }
    }

    public void setThreshold(int i, int i2) {
        this.regularWidthThreshold = i;
        this.wideWidthThreshold = i2;
        ViewGroup viewGroup = this.mContainer;
        if (viewGroup != null) {
            viewGroup.requestLayout();
        }
    }
}

package miuix.appcompat.internal.view.menu.action;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuItemImpl;
import miuix.appcompat.internal.view.menu.MenuView;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.internal.util.DeviceHelper;
import miuix.view.BlurableWidget;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ActionMenuView extends LinearLayout implements MenuBuilder.ItemInvoker, MenuView, BlurableWidget {
    public boolean mBackgroundViewApplyBlur;
    public MenuBuilder mMenu;
    public OpenCloseAnimator mOpenCloseAnimator;
    public ActionMenuPresenter mPresenter;
    public boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public boolean isOverflowButton;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.isOverflowButton = false;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams) layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }
    }

    public class OpenCloseAnimator implements Animator.AnimatorListener {
        public Animator mCurrentAnimator;

        public OpenCloseAnimator() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.mCurrentAnimator = null;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            this.mCurrentAnimator = animator;
        }

        public void setTranslationY(float f) {
            for (int i = 0; i < ActionMenuView.this.getChildCount(); i++) {
                ActionMenuView.this.getChildAt(i).setTranslationY(f);
            }
        }
    }

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBackgroundViewApplyBlur = false;
        setBaselineAligned(false);
        this.mOpenCloseAnimator = new OpenCloseAnimator();
        setLayoutAnimation(null);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof LayoutParams);
    }

    public void clearBackground() {
    }

    public float computeAlpha(float f, boolean z, boolean z2) {
        int i;
        if (!z || !z2) {
            if (z) {
                i = (int) ((1.0f - f) * 10.0f);
            } else if (z2) {
                i = (int) (f * 10.0f);
            }
            return i / 10.0f;
        }
        return 1.0f;
    }

    public float computeTranslationY(float f, boolean z, boolean z2) {
        float collapsedHeight = getCollapsedHeight();
        if (z && z2) {
            f = ((double) f) < 0.5d ? f * 2.0f : (1.0f - f) * 2.0f;
        } else if (z2) {
            f = 1.0f - f;
        }
        return f * collapsedHeight;
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // miuix.appcompat.internal.view.menu.MenuView
    public boolean filterLeftoverView(int i) {
        View childAt = getChildAt(i);
        childAt.clearAnimation();
        removeView(childAt);
        return true;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : generateDefaultLayoutParams();
    }

    public LayoutParams generateOverflowButtonLayoutParams(View view) {
        LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams();
        layoutParamsGenerateDefaultLayoutParams.isOverflowButton = true;
        return layoutParamsGenerateDefaultLayoutParams;
    }

    public abstract int getCollapsedHeight();

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialConfig getCurrentMaterial() {
        return super.getCurrentMaterial();
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialDayNightConfig getMaterial() {
        return super.getMaterial();
    }

    public ActionMenuPresenter getPresenter() {
        return this.mPresenter;
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override // miuix.appcompat.internal.view.menu.MenuView
    public boolean hasBackgroundView() {
        return false;
    }

    public boolean hasDividerBeforeChildAt(int i) {
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        boolean zNeedsDividerAfter = (i >= getChildCount() || !(childAt instanceof ActionMenuChildView)) ? false : ((ActionMenuChildView) childAt).needsDividerAfter();
        return (i <= 0 || !(childAt2 instanceof ActionMenuChildView)) ? zNeedsDividerAfter : zNeedsDividerAfter | ((ActionMenuChildView) childAt2).needsDividerBefore();
    }

    public boolean hasOnlyCustomView() {
        return false;
    }

    @Override // miuix.appcompat.internal.view.menu.MenuView
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override // miuix.appcompat.internal.view.menu.MenuBuilder.ItemInvoker
    public boolean invokeItem(MenuItemImpl menuItemImpl, int i) {
        return this.mMenu.performItemAction(menuItemImpl, i);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.updateMenuView(false);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mPresenter.dismissPopupMenus(false);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else {
            super.onMeasure(i, i2);
        }
    }

    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
        if (DeviceHelper.isFeatureWholeAnim()) {
            setAlpha(computeAlpha(f, z, z2));
        }
        float fComputeTranslationY = computeTranslationY(f, z, z2);
        if (!z || !z2 || getTranslationY() != 0.0f) {
            setTranslationY(fComputeTranslationY);
        }
        this.mOpenCloseAnimator.setTranslationY(fComputeTranslationY);
    }

    public void onWillRemoved() {
    }

    public void resetBackground() {
    }

    public abstract /* synthetic */ void setEnableBlur(boolean z);

    @Override // miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialConfig materialConfig) {
        super.setMaterial(materialConfig);
    }

    @Override // miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        super.setMaterial(materialDayNightConfig);
    }

    public void setOverflowReserved(boolean z) {
        this.mReserveOverflow = z;
    }

    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
    }

    public abstract /* synthetic */ void setSupportBlur(boolean z);

    public void updateBackground(boolean z) {
        this.mBackgroundViewApplyBlur = z;
        if (z) {
            clearBackground();
        } else {
            resetBackground();
        }
    }
}

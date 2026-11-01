package miuix.appcompat.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import miuix.appcompat.R;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.view.DensityChangedHelper;

/* JADX INFO: loaded from: classes2.dex */
public class GroupButtonsPanel extends FrameLayout {
    public int mButtonGroupDividerSize;
    public int mButtonGroupMaxWidth;
    public LinearLayout mContentView;
    public boolean mHandleWindowInsetsEnabled;
    public int mOriginPaddingBottom;
    public int mOriginPaddingLeft;
    public int mOriginPaddingRight;
    public Runnable mResetPanelPaddingBottomRunnable;

    /* JADX INFO: renamed from: $r8$lambda$BcRXkB-jOjxXF2_TF8hA0gyUSHc, reason: not valid java name */
    public static /* synthetic */ WindowInsetsCompat m1304$r8$lambda$BcRXkBjOjxXF2_TF8hA0gyUSHc(GroupButtonsPanel groupButtonsPanel, View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
        if (groupButtonsPanel.mHandleWindowInsetsEnabled) {
            groupButtonsPanel.post(groupButtonsPanel.mResetPanelPaddingBottomRunnable);
        }
        return windowInsetsCompat;
    }

    public static /* synthetic */ void $r8$lambda$tY8zeean15UZnkow_PyE_S7bwLo(GroupButtonsPanel groupButtonsPanel) {
        groupButtonsPanel.getClass();
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(groupButtonsPanel);
        ViewUtils.resetPaddingBottom(groupButtonsPanel, groupButtonsPanel.mOriginPaddingBottom + ((rootWindowInsets == null || !groupButtonsPanel.isLayoutHideNavigation()) ? 0 : rootWindowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom));
    }

    public GroupButtonsPanel(Context context) {
        super(context);
        this.mHandleWindowInsetsEnabled = true;
        init(context);
    }

    public GroupButtonsPanel(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandleWindowInsetsEnabled = true;
        init(context);
    }

    public GroupButtonsPanel(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandleWindowInsetsEnabled = true;
        init(context);
    }

    public final void applyWindowInsets() {
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() { // from class: miuix.appcompat.app.GroupButtonsPanel$$ExternalSyntheticLambda1
            @Override // miuix.internal.util.ViewUtils.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                return GroupButtonsPanel.m1304$r8$lambda$BcRXkBjOjxXF2_TF8hA0gyUSHc(this.f$0, view, windowInsetsCompat, relativePadding);
            }
        });
    }

    public int getContentVisibleChildCount() {
        int i = 0;
        for (int i2 = 0; i2 < this.mContentView.getChildCount(); i2++) {
            if (this.mContentView.getChildAt(i2).getVisibility() != 8) {
                i++;
            }
        }
        return i;
    }

    public final void init(Context context) {
        this.mButtonGroupMaxWidth = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_button_max_width);
        this.mButtonGroupDividerSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_group_buttons_divider_size);
        this.mOriginPaddingBottom = getPaddingBottom();
        this.mOriginPaddingLeft = getPaddingLeft();
        this.mOriginPaddingRight = getPaddingRight();
        this.mResetPanelPaddingBottomRunnable = new Runnable() { // from class: miuix.appcompat.app.GroupButtonsPanel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GroupButtonsPanel.$r8$lambda$tY8zeean15UZnkow_PyE_S7bwLo(this.f$0);
            }
        };
        applyWindowInsets();
    }

    public boolean isAllChildrenInvisible() {
        boolean z = true;
        for (int i = 0; i < this.mContentView.getChildCount(); i++) {
            z = z && (this.mContentView.getChildAt(i).getVisibility() != 0);
        }
        return z;
    }

    public final boolean isEllipsized(Button button, int i) {
        return ((int) button.getPaint().measureText(button.getText().toString())) > Math.min(i, Math.max(0, (button.getMaxWidth() - button.getPaddingStart()) - button.getPaddingEnd()));
    }

    public final boolean isLayoutHideNavigation() {
        return MiuixUIUtils.isLayoutHideNavigation(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.mResetPanelPaddingBottomRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
            this.mResetPanelPaddingBottomRunnable = null;
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContentView = (LinearLayout) getChildAt(0);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        boolean z = this.mContentView.getOrientation() == 1;
        int size = (View.MeasureSpec.getSize(i) - this.mOriginPaddingLeft) - this.mOriginPaddingRight;
        int iMin = Math.min(this.mButtonGroupMaxWidth, size);
        int i3 = this.mButtonGroupMaxWidth;
        int i4 = i3 < size ? (size - i3) / 2 : 0;
        if (z) {
            resizeButtonTextSize(iMin);
        } else {
            int contentVisibleChildCount = getContentVisibleChildCount();
            if (contentVisibleChildCount >= 1) {
                resizeButtonTextSize((iMin - (this.mButtonGroupDividerSize * (contentVisibleChildCount - 1))) / contentVisibleChildCount);
            }
        }
        super.onMeasure(i, i2);
        if (i4 > 0) {
            measureChild(this.mContentView, View.MeasureSpec.makeMeasureSpec((size - (i4 * 2)) + this.mOriginPaddingLeft + this.mOriginPaddingRight, View.MeasureSpec.getMode(i)), i2);
        }
    }

    public final void resizeButtonTextSize(int i) {
        for (int i2 = 0; i2 < this.mContentView.getChildCount(); i2++) {
            View childAt = this.mContentView.getChildAt(i2);
            if ((childAt instanceof Button) && childAt.getVisibility() == 0) {
                Button button = (Button) childAt;
                DensityChangedHelper.updateTextSizeSpUnit(button, 17.0f);
                if (isEllipsized(button, i)) {
                    for (int i3 = 0; i3 < this.mContentView.getChildCount(); i3++) {
                        View childAt2 = this.mContentView.getChildAt(i3);
                        if (childAt2 instanceof Button) {
                            DensityChangedHelper.updateTextSizeSpUnit((Button) childAt2, 14.0f);
                        }
                    }
                    return;
                }
            }
        }
    }

    public void setHandleWindowInsetsEnabled(boolean z) {
        if (this.mHandleWindowInsetsEnabled != z) {
            this.mHandleWindowInsetsEnabled = z;
        }
    }
}

package miuix.appcompat.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import miuix.appcompat.R;
import miuix.appcompat.internal.widget.DialogButtonPanel;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.miuixbasewidget.widget.RoundCornerImageView;

/* JADX INFO: loaded from: classes2.dex */
public class UpgradeDialog extends AlertDialog {
    public Drawable mBannerImageDrawable;
    public int mBannerImageResource;
    public RoundCornerImageView mBannerView;
    public int mBannerViewHorizontalPadding;
    public DialogButtonPanel mButtonPanel;
    public NestedScrollView mButtonScrollView;
    public CharSequence mComment;
    public TextView mCommentView;
    public int mCommentViewLandscapeVerticalPadding;
    public ViewGroup mContentPanel;
    public float mCornerRadius;
    public TextView mDialogTitle;
    public LayoutInflater mInflater;
    public Drawable mLabelImageDrawable;
    public int mLabelImageResource;
    public View mLabelImageView;
    public CharSequence mLabelSummary;
    public TextView mLabelSummaryView;
    public CharSequence mLabelTitle;
    public TextView mLabelTitleView;
    public LinearLayout mLayoutAfterChangeHierarchy;
    public CharSequence mMessage;
    public TextView mMessageView;
    public int mMinDialogHeightWithBanner;
    public DialogParentPanel2 mParentPanel;
    public View.OnLayoutChangeListener mParentPanelLayoutChangeListener;
    public LinearLayout mRootView;
    public LinearLayout mTopPanel;

    private void afterInstallDialogContent() {
        Context context = getContext();
        this.mParentPanel = (DialogParentPanel2) findViewById(R.id.parentPanel);
        this.mButtonPanel = (DialogButtonPanel) findViewById(R.id.buttonPanel);
        this.mButtonScrollView = (NestedScrollView) this.mParentPanel.findViewById(R.id.button_scroll_view);
        addCommentView();
        addBannerView();
        boolean z = false;
        boolean z2 = MiuixUIUtils.getFontLevel(context) == 2;
        boolean z3 = MiuixUIUtils.isInMultiWindowMode(context) && !MiuixUIUtils.isFreeformMode(context);
        if (hasBanner() && this.mBannerView.getVisibility() == 0) {
            z = true;
        }
        setContentForceFullScroll(z3, z2, z);
    }

    private void beforeInstallDialogContent(View view) {
        this.mLabelImageView = view.findViewById(R.id.upgrade_dialog_label_image);
        this.mLabelTitleView = (TextView) view.findViewById(R.id.upgrade_dialog_label_title);
        this.mLabelSummaryView = (TextView) view.findViewById(R.id.upgrade_dialog_label_summary);
        this.mMessageView = (TextView) view.findViewById(R.id.upgrade_dialog_message);
        this.mCommentView = (TextView) this.mInflater.inflate(R.layout.miuix_appcompat_upgrade_dialog_comment, (ViewGroup) null);
        RoundCornerImageView roundCornerImageView = new RoundCornerImageView(getContext()) { // from class: miuix.appcompat.app.UpgradeDialog.1
            @Override // android.view.View
            public void onMeasure(int i, int i2) {
                int size = View.MeasureSpec.getSize(i);
                setMeasuredDimension(size, (int) (size / 2.3f));
            }
        };
        this.mBannerView = roundCornerImageView;
        int i = this.mBannerViewHorizontalPadding;
        roundCornerImageView.setPadding(i, 0, i, 0);
        if (view instanceof LinearLayout) {
            this.mRootView = (LinearLayout) view;
        }
        setPreferLandscape(true);
    }

    private void setupMessageView() {
        if (this.mMessageView == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mMessage)) {
            this.mMessageView.setVisibility(8);
        } else {
            this.mMessageView.setText(this.mMessage);
            this.mMessageView.setVisibility(0);
        }
    }

    public final void addBannerView() {
        if (hasBanner()) {
            if (this.mButtonScrollView != null) {
                changeHierarchyIfNeed();
                this.mBannerView.setClipToPadding(true);
                this.mBannerView.setCornerRadiusEnabled(true);
                this.mLayoutAfterChangeHierarchy.addView(this.mBannerView, 0);
                return;
            }
            DialogParentPanel2 dialogParentPanel2 = this.mParentPanel;
            if (dialogParentPanel2 != null) {
                int paddingTop = dialogParentPanel2.getPaddingTop();
                ViewUtils.resetPaddingTop(this.mParentPanel, 0);
                TextView textView = (TextView) this.mParentPanel.findViewById(R.id.alertTitle);
                if (textView != null) {
                    ViewUtils.resetPaddingTop(textView, paddingTop);
                }
                this.mParentPanel.addView(this.mBannerView, 0);
                this.mBannerView.setClipToPadding(false);
                this.mBannerView.setCornerRadiusEnabled(false);
            }
        }
    }

    public final void addCommentView() {
        if (this.mCommentView == null || TextUtils.isEmpty(this.mComment)) {
            return;
        }
        if (this.mButtonScrollView != null) {
            changeHierarchyIfNeed();
            TextView textView = this.mCommentView;
            textView.setPadding(textView.getPaddingLeft(), this.mCommentViewLandscapeVerticalPadding, this.mCommentView.getCompoundPaddingRight(), this.mCommentViewLandscapeVerticalPadding);
            this.mLayoutAfterChangeHierarchy.addView(this.mCommentView, 0);
            return;
        }
        LinearLayout linearLayout = this.mRootView;
        if (linearLayout != null) {
            linearLayout.addView(this.mCommentView);
        }
    }

    public final void changeHierarchyIfNeed() {
        if (this.mLayoutAfterChangeHierarchy == null) {
            this.mButtonScrollView.removeAllViews();
            LinearLayout linearLayout = new LinearLayout(getContext());
            this.mLayoutAfterChangeHierarchy = linearLayout;
            linearLayout.setOrientation(1);
            this.mLayoutAfterChangeHierarchy.addView(this.mButtonPanel);
            this.mButtonScrollView.addView(this.mLayoutAfterChangeHierarchy, -1, -2);
        }
    }

    public final View.OnLayoutChangeListener getOnDialogParentPanelLayoutChangeListener() {
        return new View.OnLayoutChangeListener() { // from class: miuix.appcompat.app.UpgradeDialog.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                UpgradeDialog.this.mBannerView.setVisibility(i4 - i2 < UpgradeDialog.this.mMinDialogHeightWithBanner ? 8 : 0);
            }
        };
    }

    public boolean hasBanner() {
        return (this.mBannerImageDrawable == null && this.mBannerImageResource == 0) ? false : true;
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!hasBanner() || this.mBannerView == null) {
            return;
        }
        if (this.mParentPanelLayoutChangeListener == null) {
            this.mParentPanelLayoutChangeListener = getOnDialogParentPanelLayoutChangeListener();
        }
        this.mParentPanel.addOnLayoutChangeListener(this.mParentPanelLayoutChangeListener);
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        View viewInflate = this.mInflater.inflate(R.layout.miuix_appcompat_upgrade_dialog, (ViewGroup) null);
        beforeInstallDialogContent(viewInflate);
        setupMessageView();
        setupCommentView();
        setupLabelImageView();
        setupLabelTitle();
        setupLabelSummary();
        setupBannerView();
        setView(viewInflate);
        super.onCreate(bundle);
        afterInstallDialogContent();
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mParentPanel.removeOnLayoutChangeListener(this.mParentPanelLayoutChangeListener);
    }

    public final void setContentForceFullScroll(boolean z, boolean z2, boolean z3) {
        boolean z4 = z && (z2 || z3);
        if (z && z2 && z3) {
            if (this.mDialogTitle == null) {
                this.mDialogTitle = (TextView) this.mParentPanel.findViewById(R.id.alertTitle);
            }
            if (this.mTopPanel == null) {
                this.mTopPanel = (LinearLayout) this.mParentPanel.findViewById(R.id.topPanel);
            }
            if (this.mContentPanel == null) {
                this.mContentPanel = (ViewGroup) this.mParentPanel.findViewById(R.id.contentPanel);
            }
            ViewParent parent = this.mTopPanel.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mTopPanel);
            }
            this.mContentPanel.addView(this.mTopPanel, 0);
        }
        this.mAlert.setContentForceFullScroll(z4);
    }

    public final void setupBannerView() {
        if (hasBanner()) {
            this.mBannerView.setCornerRadius(this.mCornerRadius);
            Drawable drawable = this.mBannerImageDrawable;
            if (drawable != null) {
                this.mBannerView.setImageDrawable(drawable);
                return;
            }
            int i = this.mBannerImageResource;
            if (i != 0) {
                this.mBannerView.setImageDrawable(i);
            }
        }
    }

    public final void setupCommentView() {
        if (this.mCommentView == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mComment)) {
            this.mCommentView.setVisibility(8);
        } else {
            this.mCommentView.setText(this.mComment);
            this.mCommentView.setVisibility(0);
        }
    }

    public final void setupLabelImageView() {
        View view = this.mLabelImageView;
        if (view == null) {
            return;
        }
        Drawable drawable = this.mLabelImageDrawable;
        if (drawable != null) {
            view.setBackground(drawable);
            this.mLabelImageView.setVisibility(0);
            return;
        }
        int i = this.mLabelImageResource;
        if (i == 0) {
            view.setVisibility(8);
        } else {
            view.setBackgroundResource(i);
            this.mLabelImageView.setVisibility(0);
        }
    }

    public final void setupLabelSummary() {
        if (this.mLabelSummaryView == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mLabelSummary)) {
            this.mLabelSummaryView.setVisibility(8);
        } else {
            this.mLabelSummaryView.setText(this.mLabelSummary);
            this.mLabelSummaryView.setVisibility(0);
        }
    }

    public final void setupLabelTitle() {
        if (this.mLabelTitleView == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mLabelTitle)) {
            this.mLabelTitleView.setVisibility(8);
        } else {
            this.mLabelTitleView.setText(this.mLabelTitle);
            this.mLabelTitleView.setVisibility(0);
        }
    }
}

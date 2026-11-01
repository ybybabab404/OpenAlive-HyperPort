package miuix.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import miuix.appcompat.R;
import miuix.appcompat.internal.widget.DialogButtonPanel;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.appcompat.internal.widget.PairingParentPanel;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.WindowUtils;
import miuix.core.widget.NestedScrollView;
import miuix.internal.util.AnimHelper;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes2.dex */
public class PairingDialog extends AlertDialog {
    public LinearLayout mCheckboxContainer;
    public CharSequence mCheckboxMessage;
    public DialogInterface.OnClickListener mCloseClickListener;
    public Context mContext;
    public int mCustomLayoutResId;
    public View mCustomView;
    public boolean mCustomViewVerticalCenterEnabled;
    public AppCompatCheckBox mDefaultCheckbox;
    public ViewGroup mDialogButtonPanel;
    public int mDialogButtonPanelHPadding;
    public ViewGroup mDialogContentPanel;
    public DialogParentPanel2 mDialogParentPanel;
    public int mDialogParentPanelFixedHeight;
    public int mDialogParentPanelFlipTinyFixedHeight;
    public int mDialogParentPanelLargeFontFixedHeight;
    public int mDialogParentPanelPaddingBottom;
    public boolean mDiscardNaviBarHeightEnabled;
    public LinearLayout mFeedbackContainer;
    public View mInflatedCustomView;
    public boolean mIsChecked;
    public boolean mIsFullScreenGestureMode;
    public Drawable mLabelDrawable;
    public int mLabelDrawableResId;
    public AppCompatImageView mLabelImage;
    public int mLabelImageHeight;
    public int mLabelImageWidth;
    public CharSequence mMessage;
    public AppCompatTextView mMessageView;
    public boolean mNavigationBarHiddenEnabled;
    public LinearLayout mPairingContentView;
    public ViewGroup mPairingCustom;
    public PairingParentPanel mPairingParentPanel;
    public NestedScrollView mPairingScrollView;
    public SpringBackLayout mPairingSpringBack;
    public CharSequence mTitle;
    public TextView mTitleView;

    public static /* synthetic */ void $r8$lambda$gEEzKmat3KHC3NRO85mKYGkDh3o(PairingDialog pairingDialog) {
        pairingDialog.setScrollViewToExpectedHeight();
        ViewGroup.LayoutParams layoutParams = pairingDialog.mPairingContentView.getLayoutParams();
        pairingDialog.mPairingSpringBack.setSpringBackEnable((layoutParams instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams).topMargin : 0) + pairingDialog.mPairingContentView.getHeight() > pairingDialog.mPairingScrollView.getHeight());
    }

    public final void adjustSpringBackEnabled() {
        PairingParentPanel pairingParentPanel = this.mPairingParentPanel;
        if (pairingParentPanel == null || this.mPairingSpringBack == null || this.mPairingScrollView == null || this.mPairingContentView == null) {
            return;
        }
        pairingParentPanel.post(new Runnable() { // from class: miuix.appcompat.app.PairingDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PairingDialog.$r8$lambda$gEEzKmat3KHC3NRO85mKYGkDh3o(this.f$0);
            }
        });
    }

    public final void afterInstallDialogContent() {
        this.mDialogContentPanel = (ViewGroup) findViewById(R.id.contentPanel);
        this.mDialogParentPanel = (DialogParentPanel2) findViewById(R.id.parentPanel);
        this.mDialogButtonPanel = (ViewGroup) findViewById(R.id.buttonPanel);
        installDefaultCheckbox();
        installFeedbackMessageView();
    }

    public final void beforeInstallDialogContent(View view) {
        if (view instanceof PairingParentPanel) {
            PairingParentPanel pairingParentPanel = (PairingParentPanel) view;
            pairingParentPanel.setCustomViewVerticalCenterEnabled(this.mCustomViewVerticalCenterEnabled);
            this.mPairingParentPanel = pairingParentPanel;
        }
        this.mPairingSpringBack = (SpringBackLayout) view.findViewById(R.id.pairingSpringBack);
        this.mPairingScrollView = (NestedScrollView) view.findViewById(R.id.pairingScrollView);
        this.mPairingContentView = (LinearLayout) view.findViewById(R.id.pairingContentView);
        this.mTitleView = (TextView) view.findViewById(R.id.pairingTitle);
        this.mPairingCustom = (ViewGroup) view.findViewById(R.id.pairingCustom);
        this.mMessageView = (AppCompatTextView) view.findViewById(R.id.pairingMessage);
        this.mLabelImage = (AppCompatImageView) view.findViewById(R.id.pairingLabelImage);
    }

    public final void fixedButtonPanelToBottom(ViewGroup viewGroup, ViewGroup viewGroup2, ViewGroup viewGroup3) {
        if (viewGroup == null || viewGroup2 == null || viewGroup3 == null) {
            return;
        }
        int i = R.id.buttonPanel;
        ViewGroup viewGroup4 = (ViewGroup) viewGroup.findViewById(i);
        ViewGroup viewGroup5 = (ViewGroup) viewGroup2.findViewById(i);
        if (viewGroup4 != null || viewGroup5 == null) {
            return;
        }
        this.mAlert.safeMoveView(viewGroup3, viewGroup);
    }

    public final void installDefaultCheckbox() {
        LinearLayout linearLayout;
        if (this.mDialogParentPanel == null || (linearLayout = this.mCheckboxContainer) == null) {
            return;
        }
        ViewGroup viewGroup = linearLayout.getParent() instanceof ViewGroup ? (ViewGroup) this.mCheckboxContainer.getParent() : null;
        if (viewGroup != null) {
            viewGroup.removeView(this.mCheckboxContainer);
        } else {
            this.mDialogParentPanel.addView(this.mCheckboxContainer, this.mDialogParentPanel.indexOfChild(this.mDialogButtonPanel));
        }
        if (TextUtils.isEmpty(this.mCheckboxMessage)) {
            this.mCheckboxContainer.setVisibility(8);
            return;
        }
        this.mDefaultCheckbox.setText(this.mCheckboxMessage);
        this.mDefaultCheckbox.setChecked(this.mIsChecked);
        this.mCheckboxContainer.setVisibility(0);
    }

    public final void installFeedbackMessageView() {
        LinearLayout linearLayout;
        if (this.mDialogParentPanel == null || (linearLayout = this.mFeedbackContainer) == null) {
            return;
        }
        ViewGroup viewGroup = linearLayout.getParent() instanceof ViewGroup ? (ViewGroup) this.mFeedbackContainer.getParent() : null;
        if (viewGroup != null) {
            viewGroup.removeView(this.mFeedbackContainer);
        } else {
            this.mFeedbackContainer.setVisibility(8);
            this.mDialogParentPanel.addView(this.mFeedbackContainer, this.mDialogParentPanel.indexOfChild(this.mDialogButtonPanel) + 1);
        }
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        this.mAlert.setNavigationHiddenEnabled(this.mNavigationBarHiddenEnabled && this.mIsFullScreenGestureMode);
        this.mAlert.setDiscardNaviBarHeightEnabled(this.mDiscardNaviBarHeightEnabled && this.mIsFullScreenGestureMode);
        updateParentPanelFixedHeight(this.mContext.getResources().getConfiguration());
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.miuix_appcompat_pairing_dialog_content, (ViewGroup) null);
        beforeInstallDialogContent(viewInflate);
        setupTitle();
        setupMessageView();
        setupCustomContent();
        setupLabelImage();
        setView(viewInflate);
        super.onCreate(bundle);
        afterInstallDialogContent();
        setParentPanelConfigChangedCallback(this.mDialogParentPanel);
        fixedButtonPanelToBottom(this.mDialogParentPanel, this.mDialogContentPanel, this.mDialogButtonPanel);
        DialogParentPanel2 dialogParentPanel2 = this.mDialogParentPanel;
        if (dialogParentPanel2 != null) {
            dialogParentPanel2.setPadding(dialogParentPanel2.getPaddingStart(), 0, this.mDialogParentPanel.getPaddingEnd(), this.mDialogParentPanelPaddingBottom);
        }
        ViewGroup viewGroup = this.mDialogButtonPanel;
        if (viewGroup != null) {
            if (viewGroup instanceof DialogButtonPanel) {
                ((DialogButtonPanel) viewGroup).setCustomPaddingEnabled(true);
                ((DialogButtonPanel) this.mDialogButtonPanel).setCustomPaddingHorizontal(this.mDialogButtonPanelHPadding);
            }
            ViewGroup viewGroup2 = this.mDialogButtonPanel;
            viewGroup2.setPadding(this.mDialogButtonPanelHPadding, viewGroup2.getPaddingTop(), this.mDialogButtonPanelHPadding, this.mDialogButtonPanel.getPaddingBottom());
            ViewGroup.LayoutParams layoutParams = this.mDialogButtonPanel.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
            }
        }
        ImageView imageView = (ImageView) findViewById(R.id.pairingClosable);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.PairingDialog.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PairingDialog.this.mCloseClickListener != null) {
                        PairingDialog.this.mCloseClickListener.onClick(PairingDialog.this.mAlert.mDialog, -2);
                    }
                    PairingDialog.this.dismiss();
                }
            });
            AnimHelper.addPressAnim(imageView);
        }
        adjustSpringBackEnabled();
    }

    public final void setParentPanelConfigChangedCallback(DialogParentPanel2 dialogParentPanel2) {
        if (dialogParentPanel2 == null) {
            return;
        }
        dialogParentPanel2.setConfigurationChangedCallback(new DialogParentPanel2.ConfigurationChangedCallback() { // from class: miuix.appcompat.app.PairingDialog.2
            @Override // miuix.appcompat.internal.widget.DialogParentPanel2.ConfigurationChangedCallback
            public void onConfigurationChanged(Configuration configuration) {
                int iUpdateParentPanelFixedHeight = PairingDialog.this.updateParentPanelFixedHeight(configuration);
                if (PairingDialog.this.mDialogParentPanel != null) {
                    PairingDialog.this.mDialogParentPanel.setPanelFixedHeight(iUpdateParentPanelFixedHeight);
                    PairingDialog.this.mDialogParentPanel.requestLayout();
                }
                PairingDialog.this.adjustSpringBackEnabled();
            }
        });
    }

    public final void setScrollViewToExpectedHeight() {
        PairingParentPanel pairingParentPanel;
        ViewGroup.LayoutParams layoutParams;
        if (this.mPairingScrollView == null || (pairingParentPanel = this.mPairingParentPanel) == null || pairingParentPanel.getScrollExpectedHeight() == 0 || (layoutParams = this.mPairingScrollView.getLayoutParams()) == null) {
            return;
        }
        layoutParams.height = this.mPairingParentPanel.getScrollExpectedHeight();
        this.mPairingScrollView.setLayoutParams(layoutParams);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(int i) {
        this.mTitle = this.mContext.getResources().getString(i);
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public final void setupCustomContent() {
        if (this.mPairingCustom == null) {
            this.mPairingCustom = (ViewGroup) findViewById(R.id.pairingCustom);
        }
        View view = this.mInflatedCustomView;
        View viewInflate = null;
        if (view != null && view.getParent() != null) {
            this.mAlert.safeRemoveFromParent(this.mInflatedCustomView);
            this.mInflatedCustomView = null;
        }
        View view2 = this.mCustomView;
        if (view2 != null) {
            viewInflate = view2;
        } else if (this.mCustomLayoutResId != 0) {
            viewInflate = LayoutInflater.from(this.mContext).inflate(this.mCustomLayoutResId, this.mPairingCustom, false);
            this.mInflatedCustomView = viewInflate;
        }
        if (viewInflate != null) {
            this.mAlert.safeMoveView(viewInflate, this.mPairingCustom);
        } else {
            this.mAlert.safeRemoveFromParent(this.mPairingCustom);
        }
    }

    public final void setupLabelImage() {
        AppCompatImageView appCompatImageView = this.mLabelImage;
        if (appCompatImageView == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) appCompatImageView.getLayoutParams();
        if (marginLayoutParams != null) {
            marginLayoutParams.width = this.mLabelImageWidth;
            marginLayoutParams.height = this.mLabelImageHeight;
            this.mLabelImage.setLayoutParams(marginLayoutParams);
        }
        Drawable drawable = this.mLabelDrawable;
        if (drawable != null) {
            this.mLabelImage.setImageDrawable(drawable);
            this.mLabelImage.setVisibility(0);
            return;
        }
        int i = this.mLabelDrawableResId;
        if (i == 0) {
            this.mLabelImage.setVisibility(8);
        } else {
            this.mLabelImage.setImageResource(i);
            this.mLabelImage.setVisibility(0);
        }
    }

    public final void setupMessageView() {
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

    public final void setupTitle() {
        if (this.mTitleView == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mTitle)) {
            this.mTitleView.setVisibility(8);
        } else {
            this.mTitleView.setVisibility(0);
            this.mTitleView.setText(this.mTitle);
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0046  */
    /* JADX WARN: Code duplicated, block: B:17:0x0049  */
    /* JADX WARN: Code duplicated, block: B:19:0x0051  */
    /* JADX WARN: Code duplicated, block: B:20:0x0054  */
    /* JADX WARN: Code duplicated, block: B:23:0x005a  */
    public final int updateParentPanelFixedHeight(Configuration configuration) {
        int i;
        int i2;
        int i3;
        Point screenSize = WindowUtils.getScreenSize(this.mContext);
        int statusBarHeight = MiuixUIUtils.getStatusBarHeight(this.mContext);
        int navigationBarHeight = MiuixUIUtils.getNavigationBarHeight(this.mContext);
        boolean zIsFullScreenGestureMode = MiuixUIUtils.isFullScreenGestureMode(this.mContext);
        boolean z = configuration.orientation == 2;
        boolean zIsFlipTinyScreen = this.mAlert.isFlipTinyScreen();
        int panelBottomMargin = this.mAlert.getPanelBottomMargin();
        if (!zIsFullScreenGestureMode) {
            if (z) {
                i2 = screenSize.y - statusBarHeight;
            } else {
                i = screenSize.y - statusBarHeight;
            }
            if (zIsFlipTinyScreen) {
                i3 = this.mDialogParentPanelFlipTinyFixedHeight;
            } else if (MiuixUIUtils.getFontLevel(this.mContext) == 2) {
                i3 = this.mDialogParentPanelLargeFontFixedHeight;
            } else {
                i3 = this.mDialogParentPanelFixedHeight;
            }
            if (i2 < i3 + panelBottomMargin) {
                i3 = i2 - panelBottomMargin;
            }
            this.mAlert.setPanelFixedSizeEnabled(true);
            this.mAlert.setPanelFixedHeight(i3);
            return i3;
        }
        i = screenSize.y - statusBarHeight;
        if (this.mNavigationBarHiddenEnabled) {
            navigationBarHeight = 0;
        }
        i2 = i - navigationBarHeight;
        if (zIsFlipTinyScreen) {
            i3 = this.mDialogParentPanelFlipTinyFixedHeight;
        } else if (MiuixUIUtils.getFontLevel(this.mContext) == 2) {
            i3 = this.mDialogParentPanelLargeFontFixedHeight;
        } else {
            i3 = this.mDialogParentPanelFixedHeight;
        }
        if (i2 < i3 + panelBottomMargin) {
            i3 = i2 - panelBottomMargin;
        }
        this.mAlert.setPanelFixedSizeEnabled(true);
        this.mAlert.setPanelFixedHeight(i3);
        return i3;
    }
}

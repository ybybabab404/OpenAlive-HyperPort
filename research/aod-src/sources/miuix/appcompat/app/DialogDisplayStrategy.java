package miuix.appcompat.app;

import android.graphics.Rect;
import miuix.appcompat.app.strategy.IDialogButtonBehavior;
import miuix.appcompat.app.strategy.IDialogPanelBehavior;

/* JADX INFO: loaded from: classes2.dex */
public class DialogDisplayStrategy {
    public IDialogButtonBehavior mButtonBehavior;
    public IDialogPanelBehavior mPanelBehavior;

    public int getPanelWidth(DialogContract.PanelWidthSpec panelWidthSpec, DialogContract.DimensConfig dimensConfig) {
        IDialogPanelBehavior iDialogPanelBehavior = this.mPanelBehavior;
        if (iDialogPanelBehavior == null) {
            return -1;
        }
        return iDialogPanelBehavior.calcDesignedPanelWidth(panelWidthSpec, dimensConfig);
    }

    public int getWidthMargin(DialogContract.DimensConfig dimensConfig, int i) {
        IDialogPanelBehavior iDialogPanelBehavior = this.mPanelBehavior;
        return iDialogPanelBehavior == null ? dimensConfig.widthSmallMargin : iDialogPanelBehavior.calcDesignedWidthMargin(dimensConfig, i);
    }

    public boolean isButtonScrollable(DialogContract.ButtonScrollSpec buttonScrollSpec) {
        IDialogButtonBehavior iDialogButtonBehavior = this.mButtonBehavior;
        if (iDialogButtonBehavior == null) {
            return false;
        }
        return iDialogButtonBehavior.isButtonScrollable(buttonScrollSpec);
    }

    public boolean isLandscapeWindow(DialogContract.OrientationSpec orientationSpec) {
        IDialogPanelBehavior iDialogPanelBehavior = this.mPanelBehavior;
        if (iDialogPanelBehavior == null) {
            return false;
        }
        return iDialogPanelBehavior.isLandscapeWindow(orientationSpec);
    }

    public DialogDisplayStrategy setButtonBehavior(IDialogButtonBehavior iDialogButtonBehavior) {
        this.mButtonBehavior = iDialogButtonBehavior;
        return this;
    }

    public DialogDisplayStrategy setPanelBehavior(IDialogPanelBehavior iDialogPanelBehavior) {
        this.mPanelBehavior = iDialogPanelBehavior;
        return this;
    }

    public boolean shouldLimitPanelWidth(int i) {
        IDialogPanelBehavior iDialogPanelBehavior = this.mPanelBehavior;
        if (iDialogPanelBehavior == null) {
            return true;
        }
        return iDialogPanelBehavior.shouldLimitPanelWidth(i);
    }

    public int updatePanelPosMargins(DialogContract.PanelPosSpec panelPosSpec, DialogContract.DimensConfig dimensConfig, Rect rect) {
        IDialogPanelBehavior iDialogPanelBehavior = this.mPanelBehavior;
        if (iDialogPanelBehavior == null) {
            return -1;
        }
        return iDialogPanelBehavior.calcPanelPosition(panelPosSpec, dimensConfig, rect);
    }
}

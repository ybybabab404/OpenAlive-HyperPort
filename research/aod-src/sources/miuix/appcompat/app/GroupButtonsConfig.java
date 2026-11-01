package miuix.appcompat.app;

import android.view.View;
import miuix.appcompat.widget.Button;

/* JADX INFO: loaded from: classes2.dex */
public class GroupButtonsConfig {
    public View.OnClickListener mOnPrimaryButtonClickListener;
    public View.OnLongClickListener mOnPrimaryButtonLongClickListener;
    public View.OnClickListener mOnSecondaryButtonClickListener;
    public View.OnLongClickListener mOnSecondaryButtonLongClickListener;
    public View.OnClickListener mOnTertiaryButtonClickListener;
    public View.OnLongClickListener mOnTertiaryButtonLongClickListener;
    public int mOrientation;
    public Button mPrimaryButton;
    public boolean mPrimaryButtonEnableState;
    public CharSequence mPrimaryButtonText;
    public int mPrimaryButtonVisibility;
    public Button mSecondaryButton;
    public boolean mSecondaryButtonEnableState;
    public CharSequence mSecondaryButtonText;
    public int mSecondaryButtonVisibility;
    public Button mTertiaryButton;
    public boolean mTertiaryButtonEnableState;
    public CharSequence mTertiaryButtonText;
    public int mTertiaryButtonVisibility;

    public static class Builder {
    }

    public static class ButtonLayoutType {
    }

    public static class ButtonType {
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void initButton(int i, Button button) {
        if (i == 0) {
            button.setText(this.mPrimaryButtonText);
            button.setOnClickListener(this.mOnPrimaryButtonClickListener);
            button.setOnLongClickListener(this.mOnPrimaryButtonLongClickListener);
            button.setEnabled(this.mPrimaryButtonEnableState);
            button.setVisibility(this.mPrimaryButtonVisibility);
            this.mPrimaryButton = button;
            return;
        }
        if (i == 1) {
            button.setText(this.mSecondaryButtonText);
            button.setOnClickListener(this.mOnSecondaryButtonClickListener);
            button.setOnLongClickListener(this.mOnSecondaryButtonLongClickListener);
            button.setEnabled(this.mSecondaryButtonEnableState);
            button.setVisibility(this.mSecondaryButtonVisibility);
            this.mSecondaryButton = button;
            return;
        }
        if (i != 2) {
            return;
        }
        button.setText(this.mTertiaryButtonText);
        button.setOnClickListener(this.mOnTertiaryButtonClickListener);
        button.setOnLongClickListener(this.mOnTertiaryButtonLongClickListener);
        button.setEnabled(this.mTertiaryButtonEnableState);
        button.setVisibility(this.mTertiaryButtonVisibility);
        this.mTertiaryButton = button;
    }
}

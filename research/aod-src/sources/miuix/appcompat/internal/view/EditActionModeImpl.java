package miuix.appcompat.internal.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.View;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.ActionBarContextView;
import miuix.appcompat.internal.app.widget.ActionModeView;
import miuix.view.EditActionMode;

/* JADX INFO: loaded from: classes2.dex */
public class EditActionModeImpl extends ActionModeImpl implements EditActionMode {
    public boolean mAnnounceAccessibilityEnabled;
    public String mFinishEditActionModeDescription;
    public String mStartEditActionModeDescription;

    public EditActionModeImpl(Context context, ActionMode.Callback callback) {
        super(context, callback);
        this.mAnnounceAccessibilityEnabled = true;
    }

    public void announceAccessibilityEvent(String str) {
        ActionModeView actionModeView = (ActionModeView) this.mActionModeView.get();
        if (actionModeView instanceof ActionBarContextView) {
            ((ActionBarContextView) actionModeView).announceForAccessibility(str);
        }
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl
    public boolean dispatchOnCreate() {
        boolean zDispatchOnCreate = super.dispatchOnCreate();
        if (this.mAnnounceAccessibilityEnabled && zDispatchOnCreate) {
            announceAccessibilityEvent(getStartEditActionModeDescription());
        }
        return zDispatchOnCreate;
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl, android.view.ActionMode
    public void finish() {
        super.finish();
        if (this.mAnnounceAccessibilityEnabled) {
            announceAccessibilityEvent(getFinishEditActionModeDescription());
        }
    }

    public final String getFinishEditActionModeDescription() {
        return TextUtils.isEmpty(this.mFinishEditActionModeDescription) ? this.mContext.getResources().getString(R.string.miuix_appcompat_accessibility_finish_edit_action_mode) : this.mFinishEditActionModeDescription;
    }

    public final String getStartEditActionModeDescription() {
        return TextUtils.isEmpty(this.mStartEditActionModeDescription) ? this.mContext.getResources().getString(R.string.miuix_appcompat_accessibility_start_edit_action_mode) : this.mStartEditActionModeDescription;
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl, android.view.ActionMode
    public CharSequence getTitle() {
        return ((ActionBarContextView) this.mActionModeView.get()).getTitle();
    }

    @Override // miuix.view.EditActionMode
    public void setButton(int i, CharSequence charSequence, int i2) {
        ((ActionBarContextView) this.mActionModeView.get()).setButton(i, charSequence, i2);
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl, android.view.ActionMode
    public void setCustomView(View view) {
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl, android.view.ActionMode
    public void setSubtitle(int i) {
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl, android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl, android.view.ActionMode
    public void setTitle(int i) {
        setTitle(this.mContext.getResources().getString(i));
    }

    @Override // miuix.appcompat.internal.view.ActionModeImpl, android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        ((ActionBarContextView) this.mActionModeView.get()).setTitle(charSequence);
    }
}

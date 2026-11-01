package com.miui.aod.history;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import androidx.lifecycle.Observer;
import com.miui.aod.R;
import com.miui.keyguard.editor.utils.ClickUtil;
import java.util.Iterator;
import java.util.List;
import miuix.appcompat.app.AppCompatActivity;
import miuix.view.EditActionMode;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class EditActionController implements ActionMode.Callback, Observer<List<HistoryEditViewModel.HistoryEditData>> {
    public final AppCompatActivity mActivity;
    public ActionMode mEditActionMode;
    public boolean mSelectAll = true;

    public EditActionController(AppCompatActivity appCompatActivity) {
        this.mActivity = appCompatActivity;
    }

    public static EditActionController newInstance(AppCompatActivity appCompatActivity) {
        return new EditActionController(appCompatActivity);
    }

    public void enterEditMode() {
        if (this.mEditActionMode != null) {
            return;
        }
        this.mActivity.startActionMode(this);
    }

    public void exitEditMode() {
        ActionMode actionMode = this.mEditActionMode;
        if (actionMode != null) {
            actionMode.finish();
            this.mEditActionMode = null;
        }
    }

    public boolean isEditMode() {
        return this.mEditActionMode != null;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (ClickUtil.INSTANCE.isFastDoubleClick()) {
            return true;
        }
        if (menuItem.getItemId() == 16908313) {
            ActionMode actionMode2 = this.mEditActionMode;
            if (actionMode2 != null) {
                actionMode2.finish();
                this.mEditActionMode = null;
            }
            EventBus.getDefault().post(new HistoryEditViewModel.SwitchModeEvent(false));
        } else if (menuItem.getItemId() == 16908314) {
            this.mSelectAll = !this.mSelectAll;
            updateButtonState();
            EventBus.getDefault().post(new HistoryEditViewModel.SelectEvent(-1, this.mSelectAll));
        } else if (103 == menuItem.getItemId()) {
            EventBus.getDefault().post(new HistoryEditViewModel.DeleteEvent());
        }
        return true;
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(List list) {
        if (list != null) {
            Iterator it = list.iterator();
            boolean z = false;
            int i = 0;
            boolean z2 = false;
            int i2 = 0;
            boolean z3 = true;
            boolean z4 = true;
            while (it.hasNext()) {
                HistoryEditViewModel.HistoryEditData historyEditData = (HistoryEditViewModel.HistoryEditData) it.next();
                if (historyEditData.isSelected()) {
                    i++;
                    if (!historyEditData.canBeDelete()) {
                        z2 = true;
                    }
                    if (historyEditData.canBeShare()) {
                        i2++;
                    } else {
                        z3 = false;
                    }
                } else {
                    z4 = false;
                }
            }
            boolean z5 = (i == 1 && z2) ? false : true;
            String quantityString = this.mActivity.getResources().getQuantityString(R.plurals.count_selected, i, Integer.valueOf(i));
            ActionMode actionMode = this.mEditActionMode;
            if (actionMode != null) {
                actionMode.setTitle(quantityString);
                this.mEditActionMode.getMenu().setGroupEnabled(103, z5 && i > 0);
                this.mEditActionMode.getMenu().setGroupEnabled(104, z3 && i2 > 0 && i2 <= 9);
            }
            if (z4 && i > 0) {
                z = true;
            }
            this.mSelectAll = z;
        }
        updateButtonState();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.mEditActionMode = actionMode;
        actionMode.setTitle(this.mActivity.getString(R.string.my_style_title));
        menu.add(103, 103, 0, this.mActivity.getString(R.string.delete)).setIcon(R.drawable.miuix_action_icon_delete_dark).setShowAsAction(1);
        ((EditActionMode) actionMode).setButton(android.R.id.button1, null, R.drawable.miuix_action_icon_cancel_dark);
        String quantityString = this.mActivity.getResources().getQuantityString(R.plurals.count_selected, 0, 0);
        ActionMode actionMode2 = this.mEditActionMode;
        if (actionMode2 != null) {
            actionMode2.setTitle(quantityString);
        }
        this.mSelectAll = false;
        updateButtonState();
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode actionMode) {
        this.mEditActionMode = null;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    public final void updateButtonState() {
        Object obj = this.mEditActionMode;
        if (obj == null) {
            return;
        }
        ((EditActionMode) obj).setButton(android.R.id.button2, null, !this.mSelectAll ? R.drawable.miuix_action_icon_select_all_dark : R.drawable.miuix_action_icon_deselect_all_dark);
    }
}

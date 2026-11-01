package com.miui.aod.settings;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.recyclerview.widget.GridLayoutManager;
import com.miui.aod.ImageShareReceiver;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.common.BaseActivity;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.history.EditActionController;
import com.miui.aod.history.GridSpaceItemDecoration;
import com.miui.aod.history.HistoryEditAdapter;
import com.miui.aod.history.HistoryEditViewModel;
import com.miui.aod.util.ActionBarHelper;
import java.util.ArrayList;
import java.util.List;
import miuix.appcompat.app.ProgressDialog;
import miuix.recyclerview.widget.MiuiDefaultItemAnimator;
import miuix.recyclerview.widget.MiuiScaleItemAnimator;
import miuix.recyclerview.widget.RecyclerView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class HistoryListActivity extends BaseActivity {
    public Dialog mDialog;
    public EditActionController mEditActionController;
    public Handler mHandler;
    public HistoryEditAdapter mHistoryEditAdapter;
    public HistoryEditViewModel mHistoryEditViewModel;
    public OnBackPressedCallback mOnBackCallback;
    public ShareTask mShareTask;
    public final Runnable showLoadingDialogRunnable = new Runnable() { // from class: com.miui.aod.settings.HistoryListActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.showLoadingDialog();
        }
    };

    public class ShareTask extends AsyncTask<List<StyleInfo>, Void, ArrayList<String>> {
        public final /* synthetic */ HistoryListActivity this$0;

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ ArrayList<String> doInBackground(List<StyleInfo>[] listArr) {
            return doInBackground2((List[]) listArr);
        }

        /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
        public ArrayList doInBackground2(List... listArr) {
            List<String> listFillImageUri;
            if (isCancelled() || this.this$0.isFinishing() || (listFillImageUri = ImageSelectorCategoryInfo.fillImageUri(listArr[0])) == null || listFillImageUri.isEmpty()) {
                return null;
            }
            return new ArrayList(listFillImageUri);
        }

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ void onPostExecute(ArrayList<String> arrayList) {
            onPostExecute2((ArrayList) arrayList);
        }

        /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
        public void onPostExecute2(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            if (isCancelled() || this.this$0.isFinishing() || arrayList == null) {
                return;
            }
            Utils.stepToImageShare(this.this$0.getApplicationContext(), arrayList);
            ImageShareReceiver.registerImageShareReceiver(this.this$0.getApplicationContext());
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            super.onPreExecute();
            this.this$0.mHandler.postDelayed(this.this$0.showLoadingDialogRunnable, 500L);
        }
    }

    public static /* synthetic */ void $r8$lambda$fMCMZ7_KYtUzlqfOOosdpHSdHLE(HistoryListActivity historyListActivity, DialogInterface dialogInterface) {
        ShareTask shareTask = historyListActivity.mShareTask;
        if (shareTask != null) {
            shareTask.cancel(true);
            historyListActivity.mShareTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchEditMode(boolean z) {
        if (z) {
            this.mEditActionController.enterEditMode();
        } else {
            this.mHistoryEditViewModel.selectAll(false);
            this.mEditActionController.exitEditMode();
        }
        this.mHistoryEditAdapter.switchEditMode(z);
    }

    public final void cancelDialog() {
        this.mHandler.removeCallbacks(this.showLoadingDialogRunnable);
        Dialog dialog = this.mDialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this.mDialog.cancel();
    }

    public final void cancelShareTask() {
        ShareTask shareTask = this.mShareTask;
        if (shareTask != null) {
            shareTask.cancel(true);
            this.mShareTask = null;
        }
    }

    @Override // com.miui.aod.common.BaseActivity
    public boolean needHandleUpdateTime() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickEvent(HistoryEditAdapter.ClickEvent clickEvent) {
        if (this.mEditActionController.isEditMode()) {
            this.mHistoryEditViewModel.switchSelectState(clickEvent.mPosition);
        } else {
            AodStyleSelectActivity.start(this, clickEvent.mData.getCategoryInfo(), "HistoryPage", clickEvent.mView);
        }
    }

    @Override // com.miui.aod.common.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        boolean z = true;
        attributes.layoutInDisplayCutoutMode = 1;
        window.setAttributes(attributes);
        setContentView(R.layout.layout_history_activity);
        this.mHandler = new Handler(getMainLooper());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.categories_container);
        recyclerView.setItemAnimator(new MiuiScaleItemAnimator());
        HistoryEditAdapter historyEditAdapter = new HistoryEditAdapter(this);
        this.mHistoryEditAdapter = historyEditAdapter;
        int i = historyEditAdapter.firstItemSpace;
        recyclerView.setPadding(i, i, i, i);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Utils.inLargeScreen(this) ? 4 : 3));
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(Utils.inLargeScreen(this) ? 4 : 3, this.mHistoryEditAdapter.itemSpace, false));
        HistoryEditViewModel historyEditViewModelNewInstance = HistoryEditViewModel.newInstance(this);
        this.mHistoryEditViewModel = historyEditViewModelNewInstance;
        historyEditViewModelNewInstance.observe(this, this.mHistoryEditAdapter);
        EditActionController editActionControllerNewInstance = EditActionController.newInstance(this);
        this.mEditActionController = editActionControllerNewInstance;
        this.mHistoryEditViewModel.observe(this, editActionControllerNewInstance);
        ActionBarHelper.setupOverlayActionBar(this, recyclerView);
        recyclerView.setAdapter(this.mHistoryEditAdapter);
        recyclerView.setItemAnimator(new MiuiDefaultItemAnimator());
        AnalyticManager.getInstance(this).trackAodStyleHistoryPage();
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(z) { // from class: com.miui.aod.settings.HistoryListActivity.1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                if (HistoryListActivity.this.mEditActionController.isEditMode()) {
                    HistoryListActivity.this.switchEditMode(false);
                } else {
                    setEnabled(false);
                    HistoryListActivity.this.getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        this.mOnBackCallback = onBackPressedCallback;
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteEvent(HistoryEditViewModel.DeleteEvent deleteEvent) {
        if (this.mEditActionController.isEditMode()) {
            this.mHistoryEditViewModel.delete();
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        cancelShareTask();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoDataEvent(HistoryEditViewModel.NoDataEvent noDataEvent) {
        finish();
    }

    @Override // com.miui.aod.common.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        OnBackPressedCallback onBackPressedCallback = this.mOnBackCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectEvent(HistoryEditViewModel.SelectEvent selectEvent) {
        if (this.mEditActionController.isEditMode()) {
            this.mHistoryEditViewModel.selectData(selectEvent.mPosition, selectEvent.mChecked);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        cancelDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchModeEvent(HistoryEditViewModel.SwitchModeEvent switchModeEvent) {
        switchEditMode(switchModeEvent.mEnterEditMode);
    }

    @Override // com.miui.aod.common.BaseActivity
    public void onTimeTick() {
        this.mHistoryEditAdapter.onTimeTick();
    }

    public final void showLoadingDialog() {
        Dialog dialog = this.mDialog;
        if (dialog == null || !dialog.isShowing()) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.aod.settings.HistoryListActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    HistoryListActivity.$r8$lambda$fMCMZ7_KYtUzlqfOOosdpHSdHLE(this.f$0, dialogInterface);
                }
            });
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            this.mDialog = progressDialog;
            progressDialog.show();
        }
    }

    @Override // com.miui.aod.common.BaseActivity
    public boolean useEventBus() {
        return true;
    }
}

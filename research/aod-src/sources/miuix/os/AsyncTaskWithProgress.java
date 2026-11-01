package miuix.os;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.HashMap;
import miuix.appcompat.app.ProgressDialog;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AsyncTaskWithProgress<Params, Result> extends AsyncTask<Params, Integer, Result> {
    public static final HashMap sAllTasks = new HashMap();
    public boolean mCancelable;
    public int mCurrentProgress;
    public volatile ProgressDialogFragment mFragment;
    public final FragmentManager mFragmentManager;
    public boolean mIndeterminate;
    public final Listeners mListeners;
    public int mMaxProgress;
    public CharSequence mMessage;
    public int mMessageId;
    public int mProgressStyle;
    public int mTheme;
    public CharSequence mTitle;
    public int mTitleId;

    public class Listeners implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
        public final /* synthetic */ AsyncTaskWithProgress this$0;

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            onClick(dialogInterface, -2);
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            Dialog dialog;
            if (this.this$0.mFragment == null || (dialog = this.this$0.mFragment.getDialog()) == null || dialogInterface != dialog || i != -2) {
                return;
            }
            this.this$0.cancel(true);
        }
    }

    public static class ProgressDialogFragment extends DialogFragment {
        public AsyncTaskWithProgress mTask;

        public static ProgressDialogFragment newInstance(String str) {
            ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("task", str);
            progressDialogFragment.setArguments(bundle);
            return progressDialogFragment;
        }

        @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            AsyncTaskWithProgress asyncTaskWithProgress = this.mTask;
            if (asyncTaskWithProgress != null && asyncTaskWithProgress.mCancelable) {
                this.mTask.mListeners.onCancel(dialogInterface);
            }
            super.onCancel(dialogInterface);
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            AsyncTaskWithProgress asyncTaskWithProgress = (AsyncTaskWithProgress) AsyncTaskWithProgress.sAllTasks.get(getArguments().getString("task"));
            this.mTask = asyncTaskWithProgress;
            if (asyncTaskWithProgress == null) {
                FragmentTransaction fragmentTransactionBeginTransaction = getFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.remove(this);
                fragmentTransactionBeginTransaction.commit();
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            if (this.mTask == null) {
                return super.onCreateDialog(bundle);
            }
            ProgressDialog progressDialog = new ProgressDialog(getActivity(), this.mTask.mTheme);
            if (this.mTask.mTitleId != 0) {
                progressDialog.setTitle(this.mTask.mTitleId);
            } else {
                progressDialog.setTitle(this.mTask.mTitle);
            }
            if (this.mTask.mMessageId != 0) {
                progressDialog.setMessage(getActivity().getText(this.mTask.mMessageId));
            } else {
                progressDialog.setMessage(this.mTask.mMessage);
            }
            progressDialog.setProgressStyle(this.mTask.mProgressStyle);
            progressDialog.setIndeterminate(this.mTask.mIndeterminate);
            if (!this.mTask.mIndeterminate) {
                progressDialog.setMax(this.mTask.mMaxProgress);
                progressDialog.setProgress(this.mTask.mCurrentProgress);
            }
            if (this.mTask.mCancelable) {
                progressDialog.setButton(-2, progressDialog.getContext().getText(R.string.cancel), this.mTask.mListeners);
                progressDialog.setCancelable(true);
                return progressDialog;
            }
            progressDialog.setButton(-2, null, null);
            progressDialog.setCancelable(false);
            return progressDialog;
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public void onStart() {
            super.onStart();
            AsyncTaskWithProgress asyncTaskWithProgress = this.mTask;
            if (asyncTaskWithProgress != null) {
                asyncTaskWithProgress.mFragment = this;
            }
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public void onStop() {
            AsyncTaskWithProgress asyncTaskWithProgress = this.mTask;
            if (asyncTaskWithProgress != null) {
                asyncTaskWithProgress.mFragment = null;
            }
            super.onStop();
        }

        public void setProgress(int i) {
            Dialog dialog = getDialog();
            if (dialog instanceof ProgressDialog) {
                ((ProgressDialog) dialog).setProgress(i);
            }
        }
    }

    public final void dismissDialog() {
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) this.mFragmentManager.findFragmentByTag("AsyncTaskWithProgress@" + hashCode());
        if (progressDialogFragment != null) {
            progressDialogFragment.dismissAllowingStateLoss();
        } else if (this.mFragment != null) {
            this.mFragment.dismissAllowingStateLoss();
        }
    }

    public Activity getActivity() {
        if (this.mFragment != null) {
            return this.mFragment.getActivity();
        }
        return null;
    }

    @Override // android.os.AsyncTask
    public void onCancelled() {
        sAllTasks.remove("AsyncTaskWithProgress@" + hashCode());
        dismissDialog();
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(Object obj) {
        sAllTasks.remove("AsyncTaskWithProgress@" + hashCode());
        dismissDialog();
    }

    @Override // android.os.AsyncTask
    public void onPreExecute() {
        String str = "AsyncTaskWithProgress@" + hashCode();
        sAllTasks.put(str, this);
        if (this.mFragmentManager != null) {
            this.mFragment = ProgressDialogFragment.newInstance(str);
            this.mFragment.setCancelable(this.mCancelable);
            Activity activity = getActivity();
            if ((activity == null || !(activity.isFinishing() || activity.isDestroyed())) && !this.mFragmentManager.isDestroyed()) {
                this.mFragment.show(this.mFragmentManager, str);
            }
        }
    }

    @Override // android.os.AsyncTask
    public void onProgressUpdate(Integer... numArr) {
        super.onProgressUpdate((Object[]) numArr);
        this.mCurrentProgress = numArr[0].intValue();
        if (this.mFragment != null) {
            this.mFragment.setProgress(this.mCurrentProgress);
        }
    }
}

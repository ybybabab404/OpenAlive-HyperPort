package com.miui.aod.components.view;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.miui.aod.R;
import java.lang.ref.WeakReference;
import miuix.appcompat.app.ProgressDialog;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class ExportFragment extends DialogFragment {
    private Callbacks mCallbacks;
    private Boolean mExportResult;

    public interface Callbacks {
        boolean doExport();

        void onCancelled(boolean z);

        void onExported(boolean z);
    }

    public static class ExportTask extends AsyncTask<Void, Void, Boolean> {
        private final WeakReference<ExportFragment> mFragmentWeakReference;

        public ExportTask(ExportFragment exportFragment) {
            this.mFragmentWeakReference = new WeakReference<>(exportFragment);
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void[] voidArr) {
            Callbacks callbacks;
            ExportFragment exportFragment = this.mFragmentWeakReference.get();
            return (exportFragment == null || (callbacks = exportFragment.mCallbacks) == null) ? Boolean.FALSE : Boolean.valueOf(callbacks.doExport());
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            ExportFragment exportFragment = this.mFragmentWeakReference.get();
            if (exportFragment != null) {
                exportFragment.publishResult(bool);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishResult(Boolean bool) {
        if (!isAdded() || isRemoving()) {
            Callbacks callbacks = this.mCallbacks;
            if (callbacks != null) {
                callbacks.onCancelled(bool.booleanValue());
                return;
            }
            return;
        }
        if (!isResumed()) {
            this.mExportResult = bool;
            return;
        }
        Callbacks callbacks2 = this.mCallbacks;
        if (callbacks2 != null) {
            callbacks2.onExported(bool.booleanValue());
        }
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    @NotNull
    public /* bridge */ /* synthetic */ CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        ProgressDialog progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage(requireActivity().getString(R.string.photo_editor_saving));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        return progressDialog;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        new ExportTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mCallbacks = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Boolean bool = this.mExportResult;
        if (bool != null) {
            publishResult(bool);
        }
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }
}

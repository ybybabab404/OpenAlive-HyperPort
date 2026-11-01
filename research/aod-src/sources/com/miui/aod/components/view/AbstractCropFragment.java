package com.miui.aod.components.view;

import android.view.MotionEvent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractCropFragment extends Fragment {
    private OnCropStateChangedListener mCropChangedListener;
    private boolean mHasChanged;

    public interface OnCropStateChangedListener {
        void changeRotationState(boolean z);

        void onCropped();

        void onRestored();
    }

    public void changeRotationState(boolean z) {
        OnCropStateChangedListener onCropStateChangedListener = this.mCropChangedListener;
        if (onCropStateChangedListener != null) {
            onCropStateChangedListener.changeRotationState(z);
        }
    }

    public void doMirror() {
        onChanged();
        onDoMirror();
    }

    public void doRotate() {
        onChanged();
        onDoRotate();
    }

    public boolean doTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public abstract void finishTuning();

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    @NotNull
    public /* bridge */ /* synthetic */ CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    public final void notifyCropped() {
        OnCropStateChangedListener onCropStateChangedListener = this.mCropChangedListener;
        if (onCropStateChangedListener != null) {
            onCropStateChangedListener.onCropped();
        }
    }

    public final void notifyRestored() {
        OnCropStateChangedListener onCropStateChangedListener = this.mCropChangedListener;
        if (onCropStateChangedListener != null) {
            onCropStateChangedListener.onRestored();
        }
    }

    public void onChanged() {
        if (this.mHasChanged) {
            return;
        }
        this.mHasChanged = true;
        notifyCropped();
    }

    public abstract void onDoMirror();

    public abstract void onDoRotate();

    public abstract void onTurning(float f);

    public abstract void prepareTuning();

    public void restore() {
        if (this.mHasChanged) {
            this.mHasChanged = false;
            notifyRestored();
        }
    }

    public void setOnCropChangedListener(OnCropStateChangedListener onCropStateChangedListener) {
        this.mCropChangedListener = onCropStateChangedListener;
    }

    public void tuning(float f) {
        onChanged();
        onTurning(f);
    }
}

package com.miui.aod.components.view;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.miui.aod.R;
import com.miui.aod.components.photo.Edit.Crop;
import java.io.File;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class CropFragment extends AbstractCropFragment {
    private Bitmap mBitmap;
    private int mCanvasRotateTimes;
    private Crop mCrop;
    public EditorView mEditorView;
    private ICropCanvasBoundInitListener mICropCanvasBoundInitListener;
    private Crop.IOnTabListener mIOnTabListener;
    private Matrix mMatrix;
    private boolean mMirrored;
    private final Crop.OnCropChangedListener mOnCropChangedListener = new Crop.OnCropChangedListener() { // from class: com.miui.aod.components.view.CropFragment.1
        @Override // com.miui.aod.components.photo.Edit.Crop.OnCropChangedListener
        public void changeRotationState(boolean z) {
            CropFragment.this.changeRotationState(z);
        }

        @Override // com.miui.aod.components.photo.Edit.Crop.OnCropChangedListener
        public void onChanged() {
            CropFragment.this.onChanged();
        }
    };
    private final View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.miui.aod.components.view.CropFragment.2
        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            CropFragment cropFragment;
            EditorView editorView;
            if (CropFragment.this.mICropCanvasBoundInitListener != null && (editorView = (cropFragment = CropFragment.this).mEditorView) != null) {
                editorView.setCanvasBounds(cropFragment.mICropCanvasBoundInitListener.initCanvasBound());
            }
            CropFragment.this.mCrop.start();
            CropFragment.this.mEditorView.removeOnLayoutChangeListener(this);
        }
    };
    private float mTuningDegree;

    public interface ICropCanvasBoundInitListener {
        RectF initCanvasBound();
    }

    public void CanvasBoundTranslate(float f) {
        EditorView editorView = this.mEditorView;
        if (editorView != null) {
            editorView.translateCanvasBounds(f);
        }
    }

    public void clear() {
        this.mCrop.reset();
    }

    public File clip() {
        return this.mEditorView.clip();
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment
    public boolean doTouchEvent(MotionEvent motionEvent) {
        motionEvent.setLocation(motionEvent.getX(), motionEvent.getY() + this.mEditorView.getHeight());
        return this.mEditorView.onTouchEvent(motionEvent);
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment
    public void finishTuning() {
        this.mCrop.finishRotate();
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment, androidx.lifecycle.HasDefaultViewModelProviderFactory
    @NotNull
    public /* bridge */ /* synthetic */ CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    public boolean isEmpty() {
        RectF sampleSize = this.mCrop.getSampleSize();
        RectF croppedSize = this.mCrop.getCroppedSize();
        return !this.mMirrored && this.mTuningDegree == 0.0f && this.mCanvasRotateTimes % 4 == 0 && !(Math.round(sampleSize.width()) != Math.round(croppedSize.width()) || Math.round(sampleSize.height()) != Math.round(croppedSize.height()));
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_image_editor, viewGroup, false);
        this.mEditorView = (EditorView) viewInflate.findViewById(R.id.editor_view);
        Crop crop = new Crop(requireActivity());
        this.mCrop = crop;
        crop.setTabListener(this.mIOnTabListener);
        this.mEditorView.install(this.mCrop);
        this.mEditorView.setDrawBoundLine(false);
        this.mEditorView.setBitmap(getBitmap(), this.mMatrix);
        this.mEditorView.setBackground(null);
        this.mEditorView.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mCrop.setOnCropChangedListener(this.mOnCropChangedListener);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Crop crop = this.mCrop;
        if (crop != null) {
            crop.onStop();
            this.mCrop = null;
        }
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment
    public void onDoMirror() {
        this.mCrop.mirror();
        this.mMirrored = !this.mMirrored;
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment
    public void onDoRotate() {
        this.mCrop.rotate();
        this.mCanvasRotateTimes++;
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment
    public void onTurning(float f) {
        this.mCrop.setRotateDegree(f);
        this.mTuningDegree = f;
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment
    public void prepareTuning() {
        this.mCrop.beginRotate();
    }

    @Override // com.miui.aod.components.view.AbstractCropFragment
    public void restore() {
        super.restore();
        this.mEditorView.setBitmap(getBitmap(), this.mMatrix);
        this.mCrop.reset();
    }

    public void setBitmap(Bitmap bitmap, Matrix matrix) {
        this.mBitmap = bitmap;
        this.mMatrix = matrix;
        EditorView editorView = this.mEditorView;
        if (editorView != null) {
            editorView.setBitmap(bitmap, matrix);
        }
    }

    public void setICropCanvasBoundInitListener(ICropCanvasBoundInitListener iCropCanvasBoundInitListener) {
        this.mICropCanvasBoundInitListener = iCropCanvasBoundInitListener;
    }

    public void setIOnTabListener(Crop.IOnTabListener iOnTabListener) {
        this.mIOnTabListener = iOnTabListener;
    }

    public void translateCanvasBounds(RectF rectF) {
        EditorView editorView = this.mEditorView;
        if (editorView != null) {
            editorView.translateCanvasBounds(rectF);
        }
    }
}

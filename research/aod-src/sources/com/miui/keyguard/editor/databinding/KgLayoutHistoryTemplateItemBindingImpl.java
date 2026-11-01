package com.miui.keyguard.editor.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.Converters;
import androidx.databinding.adapters.ViewBindingAdapter;
import androidx.lifecycle.LiveData;
import com.miui.keyguard.editor.BR;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.history.bean.HistoryItemBean;
import com.miui.keyguard.editor.history.common.BindAdapter;
import com.miui.keyguard.editor.viewmodel.EditHistoryViewModel;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.internal.widget.RoundFrameLayout;

/* JADX INFO: loaded from: classes2.dex */
public class KgLayoutHistoryTemplateItemBindingImpl extends KgLayoutHistoryTemplateItemBinding {
    public static final ViewDataBinding.IncludedLayouts sIncludes = null;
    public static final SparseIntArray sViewsWithIds;
    public long mDirtyFlags;
    public final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_lockscreen, 4);
        sparseIntArray.put(R.id.iv_desk, 5);
    }

    public KgLayoutHistoryTemplateItemBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, ViewDataBinding.mapBindings(dataBindingComponent, view, 6, sIncludes, sViewsWithIds));
    }

    public KgLayoutHistoryTemplateItemBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 2, (ProgressBar) objArr[3], (RoundFrameLayout) objArr[5], (AppCompatImageView) objArr[1], (RoundFrameLayout) objArr[4], (AppCompatTextView) objArr[2]);
        this.mDirtyFlags = -1L;
        this.applyProgress.setTag(null);
        this.ivDeskMask.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvTemplateState.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    private boolean onChangeVmDeleteMode(LiveData liveData, int i) {
        if (i != BR._all) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeVmNightMode(LiveData liveData, int i) {
        if (i != BR._all) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    public void executeBindings() {
        long j;
        long j2;
        long j3;
        Drawable drawable;
        boolean zSafeUnbox;
        boolean zSafeUnbox2;
        int colorFromResource;
        LiveData nightMode;
        LiveData deleteMode;
        int status;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        EditHistoryViewModel editHistoryViewModel = this.mVm;
        HistoryItemBean historyItemBean = this.mDataBean;
        long j4 = 31;
        Drawable drawable2 = null;
        int i = 0;
        if ((j & 31) != 0) {
            if (editHistoryViewModel != null) {
                deleteMode = editHistoryViewModel.getDeleteMode();
                nightMode = editHistoryViewModel.getNightMode();
            } else {
                nightMode = null;
                deleteMode = null;
            }
            updateLiveDataRegistration(0, deleteMode);
            j2 = 0;
            updateLiveDataRegistration(1, nightMode);
            Boolean bool = deleteMode != null ? (Boolean) deleteMode.getValue() : null;
            Boolean bool2 = nightMode != null ? (Boolean) nightMode.getValue() : null;
            zSafeUnbox2 = ViewDataBinding.safeUnbox(bool);
            zSafeUnbox = ViewDataBinding.safeUnbox(bool2);
            if ((j & 22) != 0) {
                j |= zSafeUnbox ? 5184L : 2592L;
            }
            if ((j & 22) != 0) {
                colorFromResource = ViewDataBinding.getColorFromResource(this.applyProgress, zSafeUnbox ? R.color.kg_white_30 : R.color.kg_black_30);
                Drawable drawable3 = zSafeUnbox ? AppCompatResources.getDrawable(this.tvTemplateState.getContext(), R.drawable.kg_editor_history_item_btn_bg_dark) : AppCompatResources.getDrawable(this.tvTemplateState.getContext(), R.drawable.kg_editor_history_item_btn_bg_light);
                drawable2 = AppCompatResources.getDrawable(this.mboundView0.getContext(), zSafeUnbox ? R.drawable.kg_editor_history_item_bg_dark : R.drawable.kg_editor_history_item_bg_light);
                drawable = drawable3;
            } else {
                j4 = 31;
                drawable = null;
                colorFromResource = 0;
            }
            long j5 = j & 24;
            if (j5 != 0) {
                if (historyItemBean != null) {
                    status = historyItemBean.getStatus();
                    j3 = 24;
                } else {
                    j3 = 24;
                    status = 0;
                }
                boolean z = status == 1;
                if (j5 != 0) {
                    j |= z ? 256L : 128L;
                }
                if (!z) {
                    i = 8;
                }
            } else {
                j3 = 24;
            }
        } else {
            j2 = 0;
            j4 = 31;
            j3 = 24;
            drawable = null;
            zSafeUnbox = false;
            zSafeUnbox2 = false;
            colorFromResource = 0;
        }
        if ((j & j3) != j2) {
            this.applyProgress.setVisibility(i);
            BindAdapter.updateMask(this.ivDeskMask, historyItemBean);
        }
        if ((j & 22) != j2) {
            if (ViewDataBinding.getBuildSdkInt() >= 21) {
                this.applyProgress.setIndeterminateTintList(Converters.convertColorToColorStateList(colorFromResource));
            }
            ViewBindingAdapter.setBackground(this.mboundView0, drawable2);
            ViewBindingAdapter.setBackground(this.tvTemplateState, drawable);
        }
        if ((j & j4) != j2) {
            BindAdapter.updateStatus(this.tvTemplateState, historyItemBean, zSafeUnbox, zSafeUnbox2);
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            try {
                return this.mDirtyFlags != 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean onFieldChange(int i, Object obj, int i2) {
        if (i == 0) {
            return onChangeVmDeleteMode((LiveData) obj, i2);
        }
        if (i != 1) {
            return false;
        }
        return onChangeVmNightMode((LiveData) obj, i2);
    }

    @Override // com.miui.keyguard.editor.databinding.KgLayoutHistoryTemplateItemBinding
    public void setDataBean(HistoryItemBean historyItemBean) {
        this.mDataBean = historyItemBean;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(BR.dataBean);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (BR.vm == i) {
            setVm((EditHistoryViewModel) obj);
            return true;
        }
        if (BR.dataBean != i) {
            return false;
        }
        setDataBean((HistoryItemBean) obj);
        return true;
    }

    @Override // com.miui.keyguard.editor.databinding.KgLayoutHistoryTemplateItemBinding
    public void setVm(EditHistoryViewModel editHistoryViewModel) {
        this.mVm = editHistoryViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(BR.vm);
        super.requestRebind();
    }
}

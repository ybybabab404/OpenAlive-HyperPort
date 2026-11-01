package com.miui.keyguard.editor.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
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
public class KgLayoutMyTemplateItemBindingImpl extends KgLayoutMyTemplateItemBinding {
    public static final ViewDataBinding.IncludedLayouts sIncludes = null;
    public static final SparseIntArray sViewsWithIds;
    public long mDirtyFlags;
    public final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.keyguard_container, 3);
        sparseIntArray.put(R.id.desk_container, 4);
        sparseIntArray.put(R.id.desk_click_view, 5);
    }

    public KgLayoutMyTemplateItemBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, ViewDataBinding.mapBindings(dataBindingComponent, view, 6, sIncludes, sViewsWithIds));
    }

    public KgLayoutMyTemplateItemBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 2, (ProgressBar) objArr[2], (View) objArr[5], (RoundFrameLayout) objArr[4], (RoundFrameLayout) objArr[3], (AppCompatTextView) objArr[1]);
        this.mDirtyFlags = -1L;
        this.applyProgress.setTag(null);
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
        boolean zSafeUnbox;
        boolean zSafeUnbox2;
        int colorFromResource;
        LiveData nightMode;
        LiveData deleteMode;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        EditHistoryViewModel editHistoryViewModel = this.mVm;
        HistoryItemBean historyItemBean = this.mDataBean;
        long j3 = 31;
        Drawable drawable = null;
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
                j |= zSafeUnbox ? 1088L : 544L;
            }
            if ((j & 22) != 0) {
                colorFromResource = ViewDataBinding.getColorFromResource(this.applyProgress, zSafeUnbox ? R.color.kg_white_30 : R.color.kg_black_30);
                drawable = zSafeUnbox ? AppCompatResources.getDrawable(this.mboundView0.getContext(), R.drawable.kg_editor_history_item_bg_dark) : AppCompatResources.getDrawable(this.mboundView0.getContext(), R.drawable.kg_editor_history_item_bg_light);
            } else {
                j3 = 31;
                colorFromResource = 0;
            }
            long j4 = j & 24;
            if (j4 != 0) {
                boolean z = (historyItemBean != null ? historyItemBean.getStatus() : 0) == 1;
                if (j4 != 0) {
                    j |= z ? 256L : 128L;
                }
                if (!z) {
                    i = 8;
                }
            }
        } else {
            j2 = 0;
            j3 = 31;
            zSafeUnbox = false;
            zSafeUnbox2 = false;
            colorFromResource = 0;
        }
        if ((j & 24) != j2) {
            this.applyProgress.setVisibility(i);
        }
        if ((j & 22) != j2) {
            if (ViewDataBinding.getBuildSdkInt() >= 21) {
                this.applyProgress.setIndeterminateTintList(Converters.convertColorToColorStateList(colorFromResource));
            }
            ViewBindingAdapter.setBackground(this.mboundView0, drawable);
        }
        if ((j & j3) != j2) {
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

    @Override // com.miui.keyguard.editor.databinding.KgLayoutMyTemplateItemBinding
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

    @Override // com.miui.keyguard.editor.databinding.KgLayoutMyTemplateItemBinding
    public void setVm(EditHistoryViewModel editHistoryViewModel) {
        this.mVm = editHistoryViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(BR.vm);
        super.requestRebind();
    }
}

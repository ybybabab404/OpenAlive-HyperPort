package com.miui.keyguard.editor.databinding;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.Converters;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.databinding.adapters.ViewBindingAdapter;
import androidx.lifecycle.LiveData;
import com.miui.keyguard.editor.BR;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.viewmodel.EditHistoryViewModel;
import java.util.List;
import miuix.appcompat.widget.Button;
import miuix.core.widget.NestedScrollView;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public class KgEditHistoryFragmentBindingImpl extends KgEditHistoryFragmentBinding {
    public static final ViewDataBinding.IncludedLayouts sIncludes = null;
    public static final SparseIntArray sViewsWithIds;
    public long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.scrollView, 3);
        sparseIntArray.put(R.id.history_list, 4);
        sparseIntArray.put(R.id.history_foot_view, 5);
    }

    public KgEditHistoryFragmentBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, ViewDataBinding.mapBindings(dataBindingComponent, view, 6, sIncludes, sViewsWithIds));
    }

    public KgEditHistoryFragmentBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 3, (Button) objArr[2], (View) objArr[5], (RecyclerView) objArr[4], (Button) objArr[1], (ConstraintLayout) objArr[0], (NestedScrollView) objArr[3]);
        this.mDirtyFlags = -1L;
        this.historyDelete.setTag(null);
        this.historyMore.setTag(null);
        this.historyRoot.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // androidx.databinding.ViewDataBinding
    public void executeBindings() {
        long j;
        long j2;
        long j3;
        boolean z;
        int i;
        int i2;
        int colorFromResource;
        int colorFromResource2;
        Drawable drawable;
        int colorFromResource3;
        Context context;
        int i3;
        ConstraintLayout constraintLayout;
        int i4;
        LiveData deleteMode;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        EditHistoryViewModel editHistoryViewModel = this.mVm;
        long j4 = 26;
        Drawable drawable2 = null;
        int i5 = 0;
        float f = 0.0f;
        if ((31 & j) != 0) {
            long j5 = j & 25;
            if (j5 != 0) {
                if (editHistoryViewModel != null) {
                    deleteMode = editHistoryViewModel.getDeleteMode();
                    j2 = 0;
                } else {
                    j2 = 0;
                    deleteMode = null;
                }
                updateLiveDataRegistration(0, deleteMode);
                boolean zSafeUnbox = ViewDataBinding.safeUnbox(deleteMode != null ? (Boolean) deleteMode.getValue() : null);
                if (j5 != 0) {
                    j |= zSafeUnbox ? 256L : 128L;
                }
                i = 8;
                i2 = zSafeUnbox ? 0 : 8;
                if ((j & 25) != j2) {
                    j |= !zSafeUnbox ? 65536L : 32768L;
                }
                if (!zSafeUnbox) {
                    i = 0;
                }
            } else {
                j2 = 0;
                i = 0;
                i2 = 0;
            }
            long j6 = j & 26;
            j3 = 28;
            if (j6 != j2) {
                LiveData nightMode = editHistoryViewModel != null ? editHistoryViewModel.getNightMode() : null;
                updateLiveDataRegistration(1, nightMode);
                boolean zSafeUnbox2 = ViewDataBinding.safeUnbox(nightMode != null ? (Boolean) nightMode.getValue() : null);
                if (j6 != j2) {
                    j |= zSafeUnbox2 ? 1066048L : 533024L;
                }
                colorFromResource3 = ViewDataBinding.getColorFromResource(this.historyDelete, zSafeUnbox2 ? R.color.kg_white_80 : R.color.kg_black_80);
                if (zSafeUnbox2) {
                    context = this.historyDelete.getContext();
                    i3 = R.drawable.kg_icon_history_delete_dark;
                } else {
                    context = this.historyDelete.getContext();
                    i3 = R.drawable.kg_icon_history_delete_light;
                }
                drawable = AppCompatResources.getDrawable(context, i3);
                colorFromResource2 = ViewDataBinding.getColorFromResource(this.historyMore, zSafeUnbox2 ? R.color.kg_white_80 : R.color.kg_black_80);
                if (zSafeUnbox2) {
                    constraintLayout = this.historyRoot;
                    i4 = R.color.kg_black_100;
                } else {
                    constraintLayout = this.historyRoot;
                    i4 = R.color.kg_editor_history_bg_color_light;
                }
                colorFromResource = ViewDataBinding.getColorFromResource(constraintLayout, i4);
            } else {
                j4 = 26;
                drawable = null;
                colorFromResource3 = 0;
                colorFromResource = 0;
                colorFromResource2 = 0;
            }
            long j7 = j & 28;
            if (j7 != j2) {
                LiveData selectedListLiveData = editHistoryViewModel != null ? editHistoryViewModel.getSelectedListLiveData() : null;
                updateLiveDataRegistration(2, selectedListLiveData);
                List list = selectedListLiveData != null ? (List) selectedListLiveData.getValue() : null;
                i5 = (list != null ? list.size() : 0) == 0 ? 1 : 0;
                if (j7 != j2) {
                    j |= i5 != 0 ? 266240L : 133120L;
                }
                int i6 = i5 ^ 1;
                f = i5 != 0 ? 0.3f : 1.0f;
                i5 = colorFromResource3;
                drawable2 = drawable;
                z = i6;
            } else {
                drawable2 = drawable;
                z = 0;
                i5 = colorFromResource3;
            }
        } else {
            j2 = 0;
            j4 = 26;
            j3 = 28;
            z = 0;
            i = 0;
            i2 = 0;
            colorFromResource = 0;
            colorFromResource2 = 0;
        }
        if ((j & j4) != j2) {
            TextViewBindingAdapter.setDrawableTop(this.historyDelete, drawable2);
            this.historyDelete.setTextColor(i5);
            this.historyMore.setTextColor(colorFromResource2);
            ViewBindingAdapter.setBackground(this.historyRoot, Converters.convertColorToDrawable(colorFromResource));
        }
        if ((j & j3) != j2) {
            this.historyDelete.setEnabled(z);
            if (ViewDataBinding.getBuildSdkInt() >= 11) {
                this.historyDelete.setAlpha(f);
            }
        }
        if ((j & 25) != j2) {
            this.historyDelete.setVisibility(i2);
            this.historyMore.setVisibility(i);
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

    public final boolean onChangeVmDeleteMode(LiveData liveData, int i) {
        if (i != BR._all) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    public final boolean onChangeVmNightMode(LiveData liveData, int i) {
        if (i != BR._all) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    public final boolean onChangeVmSelectedListLiveData(LiveData liveData, int i) {
        if (i != BR._all) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean onFieldChange(int i, Object obj, int i2) {
        if (i == 0) {
            return onChangeVmDeleteMode((LiveData) obj, i2);
        }
        if (i == 1) {
            return onChangeVmNightMode((LiveData) obj, i2);
        }
        if (i != 2) {
            return false;
        }
        return onChangeVmSelectedListLiveData((LiveData) obj, i2);
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (BR.vm != i) {
            return false;
        }
        setVm((EditHistoryViewModel) obj);
        return true;
    }

    @Override // com.miui.keyguard.editor.databinding.KgEditHistoryFragmentBinding
    public void setVm(EditHistoryViewModel editHistoryViewModel) {
        this.mVm = editHistoryViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(BR.vm);
        super.requestRebind();
    }
}

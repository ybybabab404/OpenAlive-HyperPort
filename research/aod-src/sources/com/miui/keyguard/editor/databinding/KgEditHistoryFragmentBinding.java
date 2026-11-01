package com.miui.keyguard.editor.databinding;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import com.miui.keyguard.editor.viewmodel.EditHistoryViewModel;
import miuix.appcompat.widget.Button;
import miuix.core.widget.NestedScrollView;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public abstract class KgEditHistoryFragmentBinding extends ViewDataBinding {
    public final Button historyDelete;
    public final View historyFootView;
    public final RecyclerView historyList;
    public final Button historyMore;
    public final ConstraintLayout historyRoot;
    public EditHistoryViewModel mVm;
    public final NestedScrollView scrollView;

    public KgEditHistoryFragmentBinding(Object obj, View view, int i, Button button, View view2, RecyclerView recyclerView, Button button2, ConstraintLayout constraintLayout, NestedScrollView nestedScrollView) {
        super(obj, view, i);
        this.historyDelete = button;
        this.historyFootView = view2;
        this.historyList = recyclerView;
        this.historyMore = button2;
        this.historyRoot = constraintLayout;
        this.scrollView = nestedScrollView;
    }

    public abstract void setVm(EditHistoryViewModel editHistoryViewModel);
}

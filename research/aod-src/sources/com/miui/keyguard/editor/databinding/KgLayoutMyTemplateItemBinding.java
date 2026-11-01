package com.miui.keyguard.editor.databinding;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import com.miui.keyguard.editor.history.bean.HistoryItemBean;
import com.miui.keyguard.editor.viewmodel.EditHistoryViewModel;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.internal.widget.RoundFrameLayout;

/* JADX INFO: loaded from: classes2.dex */
public abstract class KgLayoutMyTemplateItemBinding extends ViewDataBinding {
    public final ProgressBar applyProgress;
    public final View deskClickView;
    public final RoundFrameLayout deskContainer;
    public final RoundFrameLayout keyguardContainer;
    public HistoryItemBean mDataBean;
    public EditHistoryViewModel mVm;
    public final AppCompatTextView tvTemplateState;

    public KgLayoutMyTemplateItemBinding(Object obj, View view, int i, ProgressBar progressBar, View view2, RoundFrameLayout roundFrameLayout, RoundFrameLayout roundFrameLayout2, AppCompatTextView appCompatTextView) {
        super(obj, view, i);
        this.applyProgress = progressBar;
        this.deskClickView = view2;
        this.deskContainer = roundFrameLayout;
        this.keyguardContainer = roundFrameLayout2;
        this.tvTemplateState = appCompatTextView;
    }

    public abstract void setDataBean(HistoryItemBean historyItemBean);

    public abstract void setVm(EditHistoryViewModel editHistoryViewModel);
}

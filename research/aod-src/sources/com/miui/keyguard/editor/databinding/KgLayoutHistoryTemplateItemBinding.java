package com.miui.keyguard.editor.databinding;

import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import com.miui.keyguard.editor.history.bean.HistoryItemBean;
import com.miui.keyguard.editor.viewmodel.EditHistoryViewModel;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.internal.widget.RoundFrameLayout;

/* JADX INFO: loaded from: classes2.dex */
public abstract class KgLayoutHistoryTemplateItemBinding extends ViewDataBinding {
    public final ProgressBar applyProgress;
    public final RoundFrameLayout ivDesk;
    public final AppCompatImageView ivDeskMask;
    public final RoundFrameLayout ivLockscreen;
    public HistoryItemBean mDataBean;
    public EditHistoryViewModel mVm;
    public final AppCompatTextView tvTemplateState;

    public KgLayoutHistoryTemplateItemBinding(Object obj, View view, int i, ProgressBar progressBar, RoundFrameLayout roundFrameLayout, AppCompatImageView appCompatImageView, RoundFrameLayout roundFrameLayout2, AppCompatTextView appCompatTextView) {
        super(obj, view, i);
        this.applyProgress = progressBar;
        this.ivDesk = roundFrameLayout;
        this.ivDeskMask = appCompatImageView;
        this.ivLockscreen = roundFrameLayout2;
        this.tvTemplateState = appCompatTextView;
    }

    public abstract void setDataBean(HistoryItemBean historyItemBean);

    public abstract void setVm(EditHistoryViewModel editHistoryViewModel);
}

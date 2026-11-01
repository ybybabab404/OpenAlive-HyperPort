package com.miui.aod.fullaod;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;
import com.miui.aod.Utils;
import miuix.appcompat.widget.Spinner;

/* JADX INFO: loaded from: classes.dex */
public class FullAodSpinner extends Spinner {
    public FullAodSpinner(Context context) {
        super(context);
    }

    public FullAodSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // miuix.appcompat.widget.Spinner, android.widget.AbsSpinner, android.widget.AdapterView
    public void setSelection(int i) {
        if (i != 1 || Utils.isFullAodSupport(getContext())) {
            super.setSelection(i);
        } else {
            Toast.makeText(getContext(), FullAodStrategyKt.getToastReason(getContext()), 0).show();
        }
    }
}

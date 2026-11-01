package com.google.android.material.internal;

import android.widget.Checkable;
import androidx.annotation.RestrictTo;
import com.google.android.material.internal.MaterialCheckable;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public interface MaterialCheckable<T extends MaterialCheckable<T>> extends Checkable {

    public interface OnCheckedChangeListener<C> {
        void onCheckedChanged(Object obj, boolean z);
    }

    int getId();

    void setInternalOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener);
}

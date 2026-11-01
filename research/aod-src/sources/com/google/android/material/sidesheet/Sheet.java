package com.google.android.material.sidesheet;

import androidx.annotation.RestrictTo;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.sidesheet.SheetCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
interface Sheet<C extends SheetCallback> extends MaterialBackHandler {

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface SheetEdge {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface SheetState {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface StableSheetState {
    }

    void addCallback(SheetCallback sheetCallback);

    int getState();

    void setState(int i);
}

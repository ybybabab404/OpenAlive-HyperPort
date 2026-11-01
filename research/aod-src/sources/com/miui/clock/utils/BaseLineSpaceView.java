package com.miui.clock.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class BaseLineSpaceView extends View {
    public BaseLineSpaceView(Context context) {
        super(context);
    }

    public BaseLineSpaceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseLineSpaceView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public int getBaseline() {
        return getMeasuredHeight();
    }
}

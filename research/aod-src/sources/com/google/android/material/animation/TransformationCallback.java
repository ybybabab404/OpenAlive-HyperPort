package com.google.android.material.animation;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface TransformationCallback<T extends View> {
    void onScaleChanged(View view);

    void onTranslationChanged(View view);
}

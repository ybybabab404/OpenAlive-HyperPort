package com.bumptech.glide.util;

import com.bumptech.glide.ListPreloader;

/* JADX INFO: loaded from: classes.dex */
public class FixedPreloadSizeProvider<T> implements ListPreloader.PreloadSizeProvider<T> {
    public final int[] size;

    @Override // com.bumptech.glide.ListPreloader.PreloadSizeProvider
    public int[] getPreloadSize(Object obj, int i, int i2) {
        return this.size;
    }
}

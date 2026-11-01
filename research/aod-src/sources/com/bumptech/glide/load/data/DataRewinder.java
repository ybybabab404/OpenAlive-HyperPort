package com.bumptech.glide.load.data;

/* JADX INFO: loaded from: classes.dex */
public interface DataRewinder<T> {

    public interface Factory<T> {
        DataRewinder build(Object obj);

        Class getDataClass();
    }

    void cleanup();

    Object rewindAndGet();
}

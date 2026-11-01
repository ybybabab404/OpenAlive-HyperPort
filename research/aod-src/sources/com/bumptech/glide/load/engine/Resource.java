package com.bumptech.glide.load.engine;

/* JADX INFO: loaded from: classes.dex */
public interface Resource<Z> {
    Object get();

    Class getResourceClass();

    int getSize();

    void recycle();
}

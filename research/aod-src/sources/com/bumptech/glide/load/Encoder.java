package com.bumptech.glide.load;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public interface Encoder<T> {
    boolean encode(Object obj, File file, Options options);
}

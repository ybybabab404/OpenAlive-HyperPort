package cn.kuaipan.android.http.multipart;

import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public interface PartSource {
    InputStream createInputStream();

    String getFileName();

    long getLength();
}

package cn.kuaipan.android.utils;

import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class RandomInputStream extends InputStream {
    public abstract long getCurrentPos();

    public abstract void moveToPos(long j);
}

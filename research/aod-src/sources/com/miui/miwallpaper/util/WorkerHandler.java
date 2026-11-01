package com.miui.miwallpaper.util;

import android.util.SparseArray;

/* JADX INFO: loaded from: classes2.dex */
public class WorkerHandler {
    public final SparseArray handlers = new SparseArray();

    public static class Holder {
        public static final WorkerHandler INSTANCE = new WorkerHandler();
    }
}

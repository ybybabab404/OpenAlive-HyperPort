package com.miui.aod.util;

import com.google.gson.Gson;

/* JADX INFO: loaded from: classes.dex */
public class GsonUtils {

    public static final class GsonHolder {
        public static final Gson mGson = new Gson();
    }

    public static String toJson(Object obj) {
        return GsonHolder.mGson.toJson(obj);
    }
}

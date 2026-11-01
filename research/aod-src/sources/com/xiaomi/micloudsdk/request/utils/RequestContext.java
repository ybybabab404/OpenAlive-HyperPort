package com.xiaomi.micloudsdk.request.utils;

import android.content.Context;
import micloud.compat.independent.request.RequestEnvBuilderCompat;

/* JADX INFO: loaded from: classes2.dex */
public class RequestContext {
    public static Context sContext;
    public static RequestEnv sRequestEnv = RequestEnvBuilderCompat.build();

    public interface RequestEnv {
    }

    public static Context getContext() {
        Context context = sContext;
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("sContext=null! Please call Request.init(Context) at Application onCreate");
    }
}

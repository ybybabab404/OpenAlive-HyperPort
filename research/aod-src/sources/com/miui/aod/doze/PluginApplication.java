package com.miui.aod.doze;

import android.app.Application;
import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class PluginApplication extends Application {
    public static volatile PluginApplication sInstance;

    public PluginApplication(Context context) {
        attachBaseContext(context);
    }

    public static synchronized PluginApplication getInstance(Context context) {
        try {
            if (sInstance == null) {
                sInstance = new PluginApplication(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context getApplicationContext() {
        return this;
    }
}

package com.miui.aod.doze;

import android.content.Context;
import android.content.ContextWrapper;

/* JADX INFO: loaded from: classes.dex */
public class PluginContextWrapper extends ContextWrapper {
    public static volatile PluginContextWrapper sInstance;
    public final Context mApplicationContext;

    public PluginContextWrapper(Context context, Context context2) {
        super(context);
        this.mApplicationContext = context2;
    }

    public static synchronized PluginContextWrapper getInstance(Context context, Context context2) {
        try {
            if (sInstance == null) {
                sInstance = new PluginContextWrapper(context, context2);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context getApplicationContext() {
        return this.mApplicationContext;
    }
}

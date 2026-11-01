package com.miui.aod;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import miuix.app.Application;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationDelegate {
    public Context base;

    public static ApplicationDelegate getDelegate(Application application) {
        if ("com.miui.aod:keyguardeditor".equals(android.app.Application.getProcessName())) {
            return new KgEditorApplicationDelegate(application);
        }
        return "com.miui.aod:tinykeyguardeditor".equals(android.app.Application.getProcessName()) ? new TinyKgEditorApplicationDelegate(application) : new AODApplicationDelegate(application);
    }

    public void attachBaseContext(Context context) {
        this.base = context;
    }

    public SharedPreferences getSharedPreferences(String str, int i) {
        return this.base.getSharedPreferences(str, i);
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onCreateAfterSuper() {
    }

    public void onCreateBeforeSuper() {
    }

    public void refreshAllInOneClockPalette(Rect rect) {
    }
}

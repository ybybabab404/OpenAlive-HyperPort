package com.miui.aod;

import android.app.Application;
import android.content.res.Configuration;
import com.miui.keyguard.editor.EditorApplicationProxy;

/* JADX INFO: loaded from: classes.dex */
public class KgEditorApplicationDelegate extends ApplicationDelegate {
    public EditorApplicationProxy proxy;

    public KgEditorApplicationDelegate(Application application) {
        this.proxy = new EditorApplicationProxy(application);
    }

    @Override // com.miui.aod.ApplicationDelegate
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.proxy.onConfigurationChanged(configuration);
    }

    @Override // com.miui.aod.ApplicationDelegate
    public void onCreateAfterSuper() {
        super.onCreateAfterSuper();
        this.proxy.onCreate();
    }
}

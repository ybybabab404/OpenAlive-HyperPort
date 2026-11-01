package com.miui.aod;

import android.content.Context;
import android.content.res.Configuration;
import com.miui.aod.utils.UserHandleUtils;
import miuix.app.Application;
import miuix.autodensity.AutoDensityConfig;

/* JADX INFO: loaded from: classes.dex */
public final class TinyKgEditorApplicationDelegate extends ApplicationDelegate {
    public final Application application;

    public TinyKgEditorApplicationDelegate(Application application) {
        this.application = application;
    }

    @Override // com.miui.aod.ApplicationDelegate
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.miui.aod.ApplicationDelegate
    public void onCreateAfterSuper() {
        if (UserHandleUtils.USER_CURRENT == 0) {
            AutoDensityConfig.init(this.application);
        }
        Utils.updateTouchMode((Context) this.application, true, false);
    }
}

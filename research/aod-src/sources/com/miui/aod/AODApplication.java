package com.miui.aod;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;
import androidx.window.embedding.SplitController;
import com.miui.aod.util.EmbeddingInit;
import com.miui.keyguard.shortcuts.utils.HapticFeedbackUtils;
import miuix.app.Application;
import miuix.core.util.HyperMaterialUtils;
import miuix.os.DeviceHelper;

/* JADX INFO: loaded from: classes.dex */
public class AODApplication extends Application implements AodLifecycleCallback {
    public static Context aodSettingDensityContext;
    public static ApplicationDelegate delegate;
    public static Context sInstance;
    public static Context sysuiContext;

    public static ApplicationDelegate getApplicationDelegate() {
        return delegate;
    }

    public static void initContext(Context context, Context context2) {
        sInstance = context2;
        sysuiContext = context;
    }

    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        Log.i("AODApplication", "attachBaseContext");
        aodSettingDensityContext = createConfigurationContext(getResources().getConfiguration());
        ApplicationDelegate delegate2 = ApplicationDelegate.getDelegate(this);
        delegate = delegate2;
        delegate2.attachBaseContext(context);
        sInstance = context;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SharedPreferences getSharedPreferences(String str, int i) {
        return delegate.getSharedPreferences(str, i);
    }

    @Override // miuix.app.Application, android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        HyperMaterialUtils.clearFeatureEnable();
        super.onConfigurationChanged(configuration);
        delegate.onConfigurationChanged(configuration);
        aodSettingDensityContext = createConfigurationContext(getResources().getConfiguration());
    }

    @Override // miuix.app.Application, android.app.Application
    public void onCreate() {
        Log.i("AODApplication", "onCreate");
        delegate.onCreateBeforeSuper();
        super.onCreate();
        delegate.onCreateAfterSuper();
        if (SplitController.getInstance(getApplicationContext()).getSplitSupportStatus() == SplitController.SplitSupportStatus.SPLIT_AVAILABLE) {
            EmbeddingInit.init(getApplicationContext());
        }
        if (DeviceHelper.isTablet()) {
            return;
        }
        HapticFeedbackUtils.INSTANCE.init(this);
    }
}

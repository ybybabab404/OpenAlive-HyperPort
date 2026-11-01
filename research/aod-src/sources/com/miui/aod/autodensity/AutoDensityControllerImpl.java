package com.miui.aod.autodensity;

import android.annotation.SuppressLint;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.miui.aod.AodViewManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import miuix.autodensity.AutoDensityConfig;
import miuix.autodensity.DensityConfig;
import miuix.autodensity.DensityConfigManager;
import miuix.autodensity.DensityUtil;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AutoDensityControllerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SuppressLint({"StaticFieldLeak"})
public final class AutoDensityControllerImpl implements AutoDensityController {
    public static ActivityThread activityThread;
    public static ArrayList applicationList;
    public static boolean created;
    public static DisplayManager displayManager;
    public static DummyApplication dummyApplication;
    public static Context pluginContext;
    public static Object resManager;
    public static Context sysUIContext;
    public static final AutoDensityControllerImpl INSTANCE = new AutoDensityControllerImpl();
    public static final Handler uiHandler = new Handler(Looper.getMainLooper());
    public static final AutoDensityControllerImpl$displayListener$1 displayListener = new DisplayManager.DisplayListener() { // from class: com.miui.aod.autodensity.AutoDensityControllerImpl$displayListener$1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            int i2 = AutoDensityControllerImpl.INSTANCE.getPluginContext().getResources().getConfiguration().densityDpi;
            DensityConfig originConfig = DensityConfigManager.getInstance().getOriginConfig();
            Integer numValueOf = originConfig != null ? Integer.valueOf(originConfig.densityDpi) : null;
            DensityConfig targetConfig = DensityConfigManager.getInstance().getTargetConfig();
            Log.v("AODAutoDensityController", "on display changed " + i + " context dpi:" + i2 + " origin dpi:" + numValueOf + " target dpi:" + (targetConfig != null ? Integer.valueOf(targetConfig.densityDpi) : null));
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }
    };

    /* JADX INFO: compiled from: AutoDensityControllerImpl.kt */
    @Metadata
    public static final class DummyApplication extends Application {
        @Override // android.app.Application, android.content.ComponentCallbacks
        public void onConfigurationChanged(Configuration newConfig) {
            Intrinsics.checkNotNullParameter(newConfig, "newConfig");
            AutoDensityControllerImpl autoDensityControllerImpl = AutoDensityControllerImpl.INSTANCE;
            int i = autoDensityControllerImpl.getPluginContext().getResources().getConfiguration().densityDpi;
            DensityConfigManager.getInstance().updateConfig(autoDensityControllerImpl.getPluginContext(), autoDensityControllerImpl.getSysUIContext().getResources().getConfiguration());
            DensityUtil.updateCustomDensity(autoDensityControllerImpl.getPluginContext());
            int i2 = autoDensityControllerImpl.getPluginContext().getResources().getConfiguration().densityDpi;
            DensityConfig originConfig = DensityConfigManager.getInstance().getOriginConfig();
            Integer numValueOf = originConfig != null ? Integer.valueOf(originConfig.densityDpi) : null;
            DensityConfig targetConfig = DensityConfigManager.getInstance().getTargetConfig();
            Log.d("AODAutoDensityController", "custom density updated context dpi from " + i + " to " + i2 + " origin dpi:" + numValueOf + " target dpi:" + (targetConfig != null ? Integer.valueOf(targetConfig.densityDpi) : null));
            AodViewManager aodViewManager = AodViewManager.INSTANCE;
            Configuration configuration = autoDensityControllerImpl.getPluginContext().getResources().getConfiguration();
            Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
            aodViewManager.onConfigurationChanged(configuration);
            super.onConfigurationChanged(newConfig);
        }
    }

    public final Context getPluginContext() {
        Context context = pluginContext;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("pluginContext");
        return null;
    }

    public final Context getSysUIContext() {
        Context context = sysUIContext;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sysUIContext");
        return null;
    }

    public void onCreate(Context sysUIContext2, Context pluginContext2) {
        Intrinsics.checkNotNullParameter(sysUIContext2, "sysUIContext");
        Intrinsics.checkNotNullParameter(pluginContext2, "pluginContext");
        setSysUIContext(sysUIContext2);
        setPluginContext(pluginContext2);
        if (!created) {
            created = true;
            DensityConfigManager.getInstance().init(pluginContext2);
            AutoDensityConfig.setUpdateSystemRes(false);
            Object systemService = pluginContext2.getSystemService("display");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.display.DisplayManager");
            DisplayManager displayManager2 = (DisplayManager) systemService;
            displayManager = displayManager2;
            DummyApplication dummyApplication2 = null;
            if (displayManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("displayManager");
                displayManager2 = null;
            }
            displayManager2.registerDisplayListener(displayListener, uiHandler);
            dummyApplication = new DummyApplication();
            activityThread = ActivityThread.currentActivityThread();
            try {
                Field declaredField = ActivityThread.class.getDeclaredField("mResourcesManager");
                declaredField.setAccessible(true);
                ActivityThread activityThread2 = activityThread;
                if (activityThread2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("activityThread");
                    activityThread2 = null;
                }
                resManager = declaredField.get(activityThread2);
                Field declaredField2 = ActivityThread.class.getDeclaredField("mAllApplications");
                declaredField2.setAccessible(true);
                ActivityThread activityThread3 = activityThread;
                if (activityThread3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("activityThread");
                    activityThread3 = null;
                }
                Object obj = declaredField2.get(activityThread3);
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.ArrayList<android.app.Application>");
                applicationList = (ArrayList) obj;
                Object obj2 = resManager;
                if (obj2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("resManager");
                    obj2 = Unit.INSTANCE;
                }
                synchronized (obj2) {
                    try {
                        ArrayList arrayList = applicationList;
                        if (arrayList == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("applicationList");
                            arrayList = null;
                        }
                        DummyApplication dummyApplication3 = dummyApplication;
                        if (dummyApplication3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("dummyApplication");
                        } else {
                            dummyApplication2 = dummyApplication3;
                        }
                        arrayList.add(dummyApplication2);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
                Log.e("AODAutoDensityController", "register dummy application failed.", e);
            }
        }
        DensityUtil.updateCustomDensity(pluginContext2);
        Log.d("AODAutoDensityController", "created.");
    }

    public void onDestroy() {
    }

    public final void setPluginContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        pluginContext = context;
    }

    public final void setSysUIContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        sysUIContext = context;
    }
}

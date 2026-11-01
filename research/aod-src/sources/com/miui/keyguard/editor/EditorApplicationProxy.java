package com.miui.keyguard.editor;

import android.app.ActivityManager;
import android.app.Application;
import android.content.res.Configuration;
import android.os.SystemProperties;
import android.util.Log;
import com.miui.keyguard.editor.edit.base.CombineEditSnapHelper;
import com.miui.keyguard.editor.homepage.model.CrossListPreloader;
import com.miui.keyguard.editor.settings.SettingsTemplatesProviderManager;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyguardEditorConfig;
import com.miui.keyguard.editor.utils.ProcessManager;
import com.miui.keyguard.editor.utils.ThemeProperty;
import com.miui.keyguard.editor.utils.ThemeUtil;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.segment.ImageSegmentHandler;
import com.miui.keyguard.editor.utils.task.Task;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miui.os.Build;

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
/* JADX INFO: compiled from: EditorApplicationProxy.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorApplicationProxy {
    public static final Companion Companion = new Companion(null);
    public static final boolean EDITOR_DEBUG = SystemProperties.getBoolean("editor.debug", false);
    public static CrossListPreloader crossListPreloader;
    public static Application sInstance;
    public final String TAG;
    public final Application application;
    public Configuration currentConfig;

    /* JADX INFO: compiled from: EditorApplicationProxy.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Application getApplication() {
            Application application = EditorApplicationProxy.sInstance;
            Intrinsics.checkNotNull(application);
            return application;
        }

        public final CrossListPreloader getCrossListPreloader() {
            return EditorApplicationProxy.crossListPreloader;
        }

        public final boolean getEDITOR_DEBUG() {
            return EditorApplicationProxy.EDITOR_DEBUG;
        }
    }

    public EditorApplicationProxy(Application application) {
        Intrinsics.checkNotNullParameter(application, "application");
        this.application = application;
        sInstance = application;
        this.TAG = "Keyguard-Editor-EditorApplicationProxy";
    }

    public static final void onCreate$lambda$0(EditorApplicationProxy editorApplicationProxy) {
        ThemeProperty.INSTANCE.setSupportDownload(Boolean.valueOf(CommonUtils.INSTANCE.checkThemeSupportDownload(editorApplicationProxy.application)));
    }

    public static final void onCreate$lambda$1(EditorApplicationProxy editorApplicationProxy) {
        if (KeyguardEditorConfig.getInstance().isInitCross()) {
            ProcessManager.INSTANCE.startListenScreenState(editorApplicationProxy.application);
        }
        CombineEditSnapHelper.Companion.initSnapHelper();
        ThemeUtil.INSTANCE.makeWallpaperDirExists();
    }

    public final boolean isAppInForeground() {
        Object systemService = this.application.getSystemService("activity");
        Object obj = null;
        ActivityManager activityManager = systemService instanceof ActivityManager ? (ActivityManager) systemService : null;
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (Object obj2 : runningAppProcesses) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) obj2;
                if (Intrinsics.areEqual(runningAppProcessInfo.processName, "com.miui.aod:keyguardeditor") && runningAppProcessInfo.importance == 100) {
                    obj = obj2;
                    break;
                }
            }
            obj = (ActivityManager.RunningAppProcessInfo) obj;
        }
        return obj != null;
    }

    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        CrossListPreloader crossListPreloader2 = crossListPreloader;
        if (crossListPreloader2 != null) {
            crossListPreloader2.clear();
        }
        if (screenSizeChanged(newConfig)) {
            onDisplayFoldChanged(newConfig);
        }
    }

    public final void onCreate() {
        Log.i(this.TAG, "EditorApplicationProxy");
        ProcessManager processManager = ProcessManager.INSTANCE;
        processManager.onEditorApplicationCreate(this.application);
        processManager.registerActivityCallback(this.application);
        if (Build.IS_INTERNATIONAL_BUILD) {
            Task.run(new Runnable() { // from class: com.miui.keyguard.editor.EditorApplicationProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EditorApplicationProxy.onCreate$lambda$0(this.f$0);
                }
            });
        }
        if (KeyguardEditorConfig.getInstance().isInitCross()) {
            CrossListPreloader crossListPreloader2 = new CrossListPreloader(this.application);
            crossListPreloader = crossListPreloader2;
            crossListPreloader2.preload();
        }
        ImageSegmentHandler.INSTANCE.init(this.application);
        this.currentConfig = new Configuration(this.application.getResources().getConfiguration());
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.EditorApplicationProxy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EditorApplicationProxy.onCreate$lambda$1(this.f$0);
            }
        });
    }

    public final void onDisplayFoldChanged(Configuration configuration) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        boolean zIsDisplayFolded = deviceUtil.isDisplayFolded(configuration);
        if (deviceUtil.isFlip() && zIsDisplayFolded && isAppInForeground() && !SettingsTemplatesProviderManager.INSTANCE.isApplying()) {
            Log.i(this.TAG, "onDisplayFoldChanged: folded");
            ToastUtil.INSTANCE.dismiss();
            ProcessManager.INSTANCE.killProcess();
        }
    }

    public final boolean screenSizeChanged(Configuration configuration) {
        boolean z = (configuration.diff(this.currentConfig) & 1024) != 0;
        Configuration configuration2 = this.currentConfig;
        if (configuration2 != null) {
            configuration2.setTo(configuration);
        }
        return z;
    }
}

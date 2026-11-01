package com.miui.keyguard.shortcuts.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: DeviceFoldStateManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"StaticFieldLeak"})
@SourceDebugExtension
public final class DeviceFoldStateManager {
    public static final DeviceFoldStateManager INSTANCE = new DeviceFoldStateManager();
    public static final List callbacks = new ArrayList();
    public static Context context;
    public static DeviceStateManager deviceStateManager;
    public static boolean isFolded;
    public static DeviceStateManager.FoldStateListener listener;

    /* JADX INFO: compiled from: DeviceFoldStateManager.kt */
    @Metadata
    public interface FoldCallback {
        void onFoldUpdated(boolean z);
    }

    public static final void init$lambda$1(Boolean isFolded2) {
        Intrinsics.checkNotNullParameter(isFolded2, "isFolded");
        isFolded = isFolded2.booleanValue();
        Iterator it = callbacks.iterator();
        while (it.hasNext()) {
            ((FoldCallback) it.next()).onFoldUpdated(isFolded2.booleanValue());
        }
    }

    public final void init(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        context = context2;
        DeviceStateManager.FoldStateListener foldStateListenerNewDeviceStateManagerFoldStateListenerByReflection = newDeviceStateManagerFoldStateListenerByReflection(context2, new Consumer() { // from class: com.miui.keyguard.shortcuts.manager.DeviceFoldStateManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DeviceFoldStateManager.init$lambda$1((Boolean) obj);
            }
        });
        listener = foldStateListenerNewDeviceStateManagerFoldStateListenerByReflection;
        if (foldStateListenerNewDeviceStateManagerFoldStateListenerByReflection != null) {
            DeviceStateManager deviceStateManager2 = (DeviceStateManager) context2.getSystemService(DeviceStateManager.class);
            if (deviceStateManager2 != null) {
                deviceStateManager2.registerCallback(context2.getMainExecutor(), foldStateListenerNewDeviceStateManagerFoldStateListenerByReflection);
            } else {
                deviceStateManager2 = null;
            }
            deviceStateManager = deviceStateManager2;
        }
    }

    public final boolean isFolded() {
        return isFolded;
    }

    public final DeviceStateManager.FoldStateListener newDeviceStateManagerFoldStateListenerByReflection(Context context2, Consumer listener2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(listener2, "listener");
        try {
            Constructor constructor = DeviceStateManager.FoldStateListener.class.getConstructor(Context.class, Consumer.class);
            constructor.setAccessible(true);
            Object objNewInstance = constructor.newInstance(context2, listener2);
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type android.hardware.devicestate.DeviceStateManager.FoldStateListener");
            return (DeviceStateManager.FoldStateListener) objNewInstance;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "DeviceFoldStateManager", "newDeviceStateManagerFoldStateListenerByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public final void registerCallback(FoldCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        List list = callbacks;
        if (list.contains(callback)) {
            return;
        }
        list.add(callback);
        callback.onFoldUpdated(isFolded);
    }

    public final void release() {
        callbacks.clear();
        DeviceStateManager.FoldStateListener foldStateListener = listener;
        if (foldStateListener != null) {
            DeviceStateManager deviceStateManager2 = deviceStateManager;
            if (deviceStateManager2 != null) {
                deviceStateManager2.unregisterCallback(foldStateListener);
            }
            listener = null;
        }
        deviceStateManager = null;
        context = null;
    }

    public final void unregisterCallback(FoldCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        callbacks.remove(callback);
    }
}

package com.miui.keyguard.shortcuts.utils;

import android.content.Context;
import com.miui.keyguard.shortcuts.R;
import com.miui.keyguard.shortcuts.manager.DeviceFoldStateManager;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import miuix.device.DeviceUtils;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: DeviceHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DeviceHelper {
    public static final DeviceHelper INSTANCE = new DeviceHelper();
    public static final Lazy cpuLevel$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.DeviceHelper$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(DeviceHelper.cpuLevel_delegate$lambda$1());
        }
    });
    public static final Lazy gpuLevel$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.DeviceHelper$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(DeviceHelper.gpuLevel_delegate$lambda$3());
        }
    });
    public static boolean isForceMiddleDevice;

    public static final int cpuLevel_delegate$lambda$1() {
        int deviceLevel = DeviceUtils.getDeviceLevel(DeviceUtils.DEV_STANDARD_VERSION, DeviceUtils.TYPE_CPU);
        LogUtils.logI$default(LogUtils.INSTANCE, "DeviceHelper", "cpu level " + deviceLevel, null, 4, null);
        return deviceLevel;
    }

    public static final int gpuLevel_delegate$lambda$3() {
        int deviceLevel = DeviceUtils.getDeviceLevel(DeviceUtils.DEV_STANDARD_VERSION, DeviceUtils.TYPE_GPU);
        LogUtils.logI$default(LogUtils.INSTANCE, "DeviceHelper", "gpu level " + deviceLevel, null, 4, null);
        return deviceLevel;
    }

    public final int getCpuLevel() {
        return ((Number) cpuLevel$delegate.getValue()).intValue();
    }

    public final int getGpuLevel() {
        return ((Number) gpuLevel$delegate.getValue()).intValue();
    }

    public final int getShortcutHorizontalMargin(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isCornerCutoutDevice(context)) {
            return context.getResources().getDimensionPixelSize(R.dimen.keyguard_shortcut_container_margin_horizontal);
        }
        return 0;
    }

    public final boolean isCornerCutoutDevice(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return isFoldDevice(context) && isInternalScreen(context);
    }

    public final boolean isCpuAndGpuHighLevel() {
        return getCpuLevel() == 2 && getGpuLevel() == 2;
    }

    public final boolean isDeviceUseBlur() {
        return isCpuAndGpuHighLevel() && BlurHelper.Companion.supportsBlursOnWindows();
    }

    public final boolean isFlipDevice(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return miuix.os.DeviceHelper.detectType(context) == 4;
    }

    public final boolean isFoldDevice(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return miuix.os.DeviceHelper.detectType(context) == 3 || miuix.os.DeviceHelper.detectType(context) == 5;
    }

    public final boolean isForceMiddleDevice(Context context) {
        try {
            return context.getResources().getInteger(context.getResources().getIdentifier("force_middle_device", "integer", "com.android.systemui")) == 1;
        } catch (Throwable th) {
            LogUtils.logE$default(LogUtils.INSTANCE, "DeviceHelper", "get integer from systemUI failed: " + th, null, 4, null);
            return false;
        }
    }

    public final boolean isInternalScreen(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return isFlipDevice(context) ? !isTinyScreen(context) : miuix.os.DeviceHelper.isWideScreen(context);
    }

    public final boolean isTinyScreen(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isFlipDevice(context)) {
            return DeviceFoldStateManager.INSTANCE.isFolded() || miuix.os.DeviceHelper.isTinyScreen(context);
        }
        return false;
    }

    public final void updateForceMiddle(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        isForceMiddleDevice = isForceMiddleDevice(context);
    }
}

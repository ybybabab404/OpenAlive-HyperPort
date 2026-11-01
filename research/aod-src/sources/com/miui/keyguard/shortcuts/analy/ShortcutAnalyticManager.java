package com.miui.keyguard.shortcuts.analy;

import android.content.Context;
import android.util.ArrayMap;
import com.miui.aod.analy.AnalyticsWrapper;
import com.miui.keyguard.shortcuts.utils.DeviceHelper;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.am;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ShortcutAnalyticManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutAnalyticManager {
    public static final ShortcutAnalyticManager INSTANCE = new ShortcutAnalyticManager();
    public static OneTrack mOneTrack;

    public static final void init(Context context, boolean z, OneTrack.Mode oneTrackMode) {
        Intrinsics.checkNotNullParameter(oneTrackMode, "oneTrackMode");
        if (mOneTrack == null) {
            mOneTrack = AnalyticsWrapper.buildOneTrack(context, z, oneTrackMode);
        }
    }

    public final String getScreenType(Context context) {
        if (context == null) {
            return "unknown";
        }
        DeviceHelper deviceHelper = DeviceHelper.INSTANCE;
        if (deviceHelper.isFlipDevice(context) || deviceHelper.isFoldDevice(context)) {
            return deviceHelper.isInternalScreen(context) ? "内屏" : "外屏";
        }
        return "nothing";
    }

    public final void handleKeyguardActionEvent(Context context, String str, boolean z, String str2) {
        String screenType = getScreenType(context);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(am.af, "348.3.1.1.37047");
        arrayMap.put("function_name", str2);
        arrayMap.put("lock_screen_action", str);
        arrayMap.put("action_status", z ? "成功" : "失败");
        arrayMap.put("screen_type", screenType);
        arrayMap.put("data_version", "20250814");
        trackEvent("keyguard_action", arrayMap);
    }

    public final void handleKeyguardShortcutEnterEvent(String str, String str2) {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(am.af, "348.4.2.1.42184");
        arrayMap.put("left_function_name", str);
        arrayMap.put("right_function_name", str2);
        arrayMap.put("data_version", "20250814");
        trackEvent("enter", arrayMap);
    }

    public final void handleKeyguardShortcutExitEvent(boolean z, String str, String str2) {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(am.af, "348.4.2.1.42185");
        arrayMap.put("if_edit", z ? "是" : "否");
        if (z) {
            arrayMap.put("left_function_name", str);
            arrayMap.put("right_function_name", str2);
        }
        arrayMap.put("data_version", "20250814");
        trackEvent("quit", arrayMap);
    }

    public final void handleKeyguardShortcutExposeEvent(Context context, String str, String str2) {
        String screenType = getScreenType(context);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(am.af, "348.3.2.1.42181");
        arrayMap.put("expose_way", "点击icon");
        arrayMap.put("function_name", str2);
        arrayMap.put("screen_type", screenType);
        arrayMap.put("data_version", "20250814");
        trackEvent("expose", arrayMap);
    }

    public final void recordWhenMoveToOpen(Context context, boolean z, boolean z2, String str) {
        handleKeyguardActionEvent(context, z2 ? "左侧应用滑动" : "右侧应用滑动", z, str);
    }

    public final void trackEvent(String str, Map map) {
        OneTrack oneTrack = mOneTrack;
        if (oneTrack != null) {
            oneTrack.track(str, map);
        }
    }
}

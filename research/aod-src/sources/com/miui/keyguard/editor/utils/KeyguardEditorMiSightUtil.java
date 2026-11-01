package com.miui.keyguard.editor.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.StyleInfo;
import com.miui.maml.data.VariableNames;
import com.miui.maml.util.IntentInfo;
import com.miui.misight.mievent.MiEvent;
import com.miui.misight.mievent.MiSight;
import com.xiaomi.onetrack.h.b.a;
import com.xiaomi.onetrack.util.ac;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;

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
/* JADX INFO: compiled from: KeyguardEditorMiSightUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class KeyguardEditorMiSightUtil {
    public static boolean backupInProgress;
    public static Bundle backupParams;
    public static long backupSessionId;
    public static long firstFrameStartTs;
    public static boolean flashAlreadyReported;
    public static int flashCountInWindow;
    public static long flashWindowStartTs;
    public static boolean hierarchyInProgress;
    public static Bundle hierarchyParams;
    public static long hierarchySessionId;
    public static boolean restoreInProgress;
    public static Bundle restoreParams;
    public static long restoreSessionId;
    public static boolean templateApplyInProgress;
    public static Bundle templateApplyParams;
    public static long templateApplySessionId;
    public static boolean wallpaperDisplayInProgress;
    public static Bundle wallpaperDisplayParams;
    public static long wallpaperDisplaySessionId;
    public static final KeyguardEditorMiSightUtil INSTANCE = new KeyguardEditorMiSightUtil();
    public static final String tag = "KeyguardEditorMiSightUtil";
    public static final Lazy miSightHandler$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return KeyguardEditorMiSightUtil.miSightHandler_delegate$lambda$1();
        }
    });
    public static final int MISIGHT_TEMPLATE_APPLY_TIMEOUT = 923140101;
    public static final int MISIGHT_TEMPLATE_APPLY_STEP_EXCEPTION = 923140102;
    public static final int MISIGHT_TEMPLATE_APPLY_ABORT = 923140103;
    public static final long TEMPLATE_APPLY_STEP_TIMEOUT_THRESHOLD = 8000;
    public static final long[] templateStepStart = new long[9];
    public static final long[] templateStepDurations = new long[9];
    public static final int MISIGHT_WALLPAPER_BLACK_SCREEN = 923140201;
    public static final int MISIGHT_WALLPAPER_FLASH_SCREEN = 923140202;
    public static final int MISIGHT_WALLPAPER_NOT_DISPLAYED = 923140203;
    public static final int MISIGHT_WALLPAPER_FIRST_FRAME_TIMEOUT = 923140204;
    public static final long WALLPAPER_FIRST_FRAME_TIMEOUT_THRESHOLD = 3000;
    public static final long FLASH_DETECT_WINDOW_MS = 3000;
    public static final int FLASH_COUNT_THRESHOLD = 2;
    public static final int MISIGHT_HIERARCHY_INVISIBLE = 923140301;
    public static final int MISIGHT_HIERARCHY_COMPUTE_TIMEOUT = 923140302;
    public static final long HIERARCHY_COMPUTE_TIMEOUT_THRESHOLD = 2000;
    public static final HashMap hierarchyComputeStartMap = new HashMap();
    public static final int MISIGHT_PRIMARY_FONTFILTER_ERROR = 923140303;
    public static final int MISIGHT_SECONDARY_FONTFILTER_ERROR = 923140304;
    public static final int MISIGHT_BACKUP_STEP_TIMEOUT = 923140401;
    public static final int MISIGHT_BACKUP_STEP_EXCEPTION = 923140402;
    public static final int MISIGHT_BACKUP_ABORT = 923140403;
    public static final int MISIGHT_RESTORE_STEP_TIMEOUT = 923140404;
    public static final int MISIGHT_RESTORE_STEP_EXCEPTION = 923140405;
    public static final int MISIGHT_RESTORE_ABORT = 923140406;
    public static final int MISIGHT_BACKUP_DATA_LOSS = 923140407;
    public static final long BACKUP_STEP_TIMEOUT_THRESHOLD = 5000;
    public static final long RESTORE_STEP_TIMEOUT_THRESHOLD = 5000;
    public static final long[] backupStepStart = new long[7];
    public static final long[] backupStepDurations = new long[7];
    public static String backupAgentType = "";
    public static final long[] restoreStepStart = new long[8];
    public static final long[] restoreStepDurations = new long[8];
    public static String restoreAgentType = "";

    /* JADX INFO: compiled from: KeyguardEditorMiSightUtil.kt */
    @Metadata
    public static final class MiSightHandler extends Handler {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MiSightHandler(Looper looper) {
            super(looper);
            Intrinsics.checkNotNullParameter(looper, "looper");
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
        }
    }

    public static final void backupSessionFinish$lambda$29(String str, long j, String str2, String str3, String str4) {
        MiEvent miEvent = new MiEvent(MISIGHT_BACKUP_ABORT);
        miEvent.addStr("SessionId", String.valueOf(backupSessionId));
        miEvent.addStr("AgentType", str);
        miEvent.addStr("TotalDurationMs", String.valueOf(j));
        miEvent.addStr("StepDurations", str2);
        miEvent.addStr("Params", str3);
        if (str4 != null) {
            miEvent.addStr("Reason", str4);
        }
        sendMiSightEvent$default(INSTANCE, miEvent, "BACKUP_ABORT", null, 4, null);
    }

    public static /* synthetic */ void backupStep1EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.backupStep1EndFail(str, bundle);
    }

    public static /* synthetic */ void backupStep4EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.backupStep4EndFail(str, bundle);
    }

    public static /* synthetic */ void backupStepEnd$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, int i, boolean z, String str, Bundle bundle, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str = null;
        }
        if ((i2 & 8) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.backupStepEnd(i, z, str, bundle);
    }

    public static final void backupStepEnd$lambda$30(int i, long j, String str, Bundle bundle) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_BACKUP_STEP_EXCEPTION);
        miEvent.addInt("Step", i);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = INSTANCE;
        miEvent.addStr("StepName", keyguardEditorMiSightUtil.backupStepName(i));
        miEvent.addStr("DurationMs", String.valueOf(j));
        miEvent.addStr("Reason", str);
        if (bundle != null) {
            miEvent.addStr(IntentInfo.Extra.TAG_NAME, bundle.toString());
        }
        miEvent.addStr("SessionId", String.valueOf(backupSessionId));
        miEvent.addStr("AgentType", backupAgentType);
        Bundle bundle2 = backupParams;
        if (bundle2 == null || (string = bundle2.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("Params", string);
        sendMiSightEvent$default(keyguardEditorMiSightUtil, miEvent, "BACKUP_STEP_EXCEPTION[Step=" + i + "]", null, 4, null);
    }

    public static final void backupStepEnd$lambda$31(int i, long j) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_BACKUP_STEP_TIMEOUT);
        miEvent.addInt("Step", i);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = INSTANCE;
        miEvent.addStr("StepName", keyguardEditorMiSightUtil.backupStepName(i));
        miEvent.addStr("DurationMs", String.valueOf(j));
        miEvent.addStr("SessionId", String.valueOf(backupSessionId));
        miEvent.addStr("AgentType", backupAgentType);
        Bundle bundle = backupParams;
        if (bundle == null || (string = bundle.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("Params", string);
        sendMiSightEvent$default(keyguardEditorMiSightUtil, miEvent, "BACKUP_STEP_TIMEOUT[Step=" + i + "]", null, 4, null);
    }

    public static /* synthetic */ void hierarchyComputeEnd$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            l = null;
        }
        keyguardEditorMiSightUtil.hierarchyComputeEnd(str, l);
    }

    public static final void hierarchyComputeEnd$lambda$20(String str, long j) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_HIERARCHY_COMPUTE_TIMEOUT);
        miEvent.addStr("checkType", str);
        miEvent.addStr("durationMs", String.valueOf(j));
        miEvent.addStr("thresholdMs", String.valueOf(HIERARCHY_COMPUTE_TIMEOUT_THRESHOLD));
        miEvent.addStr("sessionId", String.valueOf(hierarchySessionId));
        Bundle bundle = hierarchyParams;
        if (bundle == null || (string = bundle.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        sendMiSightEvent$default(INSTANCE, miEvent, "HIERARCHY_COMPUTE_TIMEOUT[checkType=" + str + "]", null, 4, null);
    }

    public static final void markFirstFrameArrived$lambda$14(long j) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_WALLPAPER_FIRST_FRAME_TIMEOUT);
        miEvent.addStr("durationMs", String.valueOf(j));
        miEvent.addStr("thresholdMs", String.valueOf(WALLPAPER_FIRST_FRAME_TIMEOUT_THRESHOLD));
        miEvent.addStr("sessionId", String.valueOf(wallpaperDisplaySessionId));
        Bundle bundle = wallpaperDisplayParams;
        if (bundle == null || (string = bundle.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        sendMiSightEvent$default(INSTANCE, miEvent, "WALLPAPER_FIRST_FRAME_TIMEOUT[markArrived]", null, 4, null);
    }

    public static final MiSightHandler miSightHandler_delegate$lambda$1() {
        HandlerThread handlerThread = new HandlerThread(tag);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Intrinsics.checkNotNullExpressionValue(looper, "getLooper(...)");
        return new MiSightHandler(looper);
    }

    public static /* synthetic */ void reportApplyWallpaperFailed$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.reportApplyWallpaperFailed(str, bundle);
    }

    public static /* synthetic */ void reportDataLoss$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, String str2, String str3, String str4, Bundle bundle, int i, Object obj) {
        if ((i & 16) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.reportDataLoss(str, str2, str3, str4, bundle);
    }

    public static final void reportDataLoss$lambda$50(long j, String str, String str2, String str3, String str4, String str5, Bundle bundle, String str6) {
        MiEvent miEvent = new MiEvent(MISIGHT_BACKUP_DATA_LOSS);
        miEvent.addStr("SessionId", String.valueOf(j));
        miEvent.addStr("AgentType", str);
        miEvent.addStr("Phase", str2);
        miEvent.addStr("LossType", str3);
        miEvent.addStr("Level", str4);
        miEvent.addStr("Reason", str5);
        String strSubstring = "extra：" + bundle;
        if (strSubstring.length() > 300) {
            strSubstring = strSubstring.substring(0, 300);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        }
        miEvent.addStr(IntentInfo.Extra.TAG_NAME, strSubstring);
        miEvent.addStr("Params", str6);
        sendMiSightEvent$default(INSTANCE, miEvent, "DATA_LOSS[" + str2 + ac.b + str3 + "]", null, 4, null);
    }

    public static /* synthetic */ void reportHierarchyInvisible$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.reportHierarchyInvisible(str, bundle);
    }

    public static final void reportHierarchyInvisible$lambda$21(String str, Bundle bundle) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_HIERARCHY_INVISIBLE);
        miEvent.addStr(a.g, str);
        miEvent.addStr("extra", "extra: " + bundle);
        miEvent.addStr("sessionId", String.valueOf(hierarchySessionId));
        Bundle bundle2 = hierarchyParams;
        if (bundle2 == null || (string = bundle2.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        INSTANCE.sendMiSightEvent(miEvent, "HIERARCHY_INVISIBLE", str);
    }

    public static final void reportWaitWallpaperChangedTimeout$lambda$10(long j) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_TEMPLATE_APPLY_TIMEOUT);
        miEvent.addInt("step", 6);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = INSTANCE;
        miEvent.addStr("stepName", keyguardEditorMiSightUtil.stepName(6));
        miEvent.addStr("durationMs", String.valueOf(j));
        miEvent.addStr("sessionId", String.valueOf(templateApplySessionId));
        Bundle bundle = templateApplyParams;
        if (bundle == null || (string = bundle.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        sendMiSightEvent$default(keyguardEditorMiSightUtil, miEvent, "TEMPLATE_APPLY_TIMEOUT[Step=6,waitWallpaperChanged]", null, 4, null);
    }

    public static final void reportWallpaperBlackScreen$lambda$15(String str, Bundle bundle) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_WALLPAPER_BLACK_SCREEN);
        miEvent.addStr(a.g, str);
        miEvent.addStr("extra", "extra: " + bundle);
        miEvent.addStr("sessionId", String.valueOf(wallpaperDisplaySessionId));
        Bundle bundle2 = wallpaperDisplayParams;
        if (bundle2 == null || (string = bundle2.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        INSTANCE.sendMiSightEvent(miEvent, "WALLPAPER_BLACK_SCREEN", str);
    }

    public static final void reportWallpaperFlashScreen$lambda$17(String str, long j, Bundle bundle) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_WALLPAPER_FLASH_SCREEN);
        miEvent.addStr(a.g, str);
        miEvent.addStr("flashCount", String.valueOf(flashCountInWindow));
        miEvent.addStr("windowMs", String.valueOf(j - flashWindowStartTs));
        if (bundle != null) {
            miEvent.addStr("extra", bundle.toString());
        }
        miEvent.addStr("sessionId", String.valueOf(wallpaperDisplaySessionId));
        Bundle bundle2 = wallpaperDisplayParams;
        if (bundle2 == null || (string = bundle2.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        INSTANCE.sendMiSightEvent(miEvent, "WALLPAPER_FLASH_SCREEN", str);
    }

    public static /* synthetic */ void reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.reportWallpaperNotDisplayed(str, bundle);
    }

    public static final void reportWallpaperNotDisplayed$lambda$18(String str, Bundle bundle) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_WALLPAPER_NOT_DISPLAYED);
        miEvent.addStr(a.g, str);
        miEvent.addStr("extra", "extra: " + bundle);
        miEvent.addStr("sessionId", String.valueOf(wallpaperDisplaySessionId));
        Bundle bundle2 = wallpaperDisplayParams;
        if (bundle2 == null || (string = bundle2.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        INSTANCE.sendMiSightEvent(miEvent, "WALLPAPER_NOT_DISPLAYED", str);
    }

    public static final void restoreSessionFinish$lambda$34(String str, long j, String str2, String str3, String str4) {
        MiEvent miEvent = new MiEvent(MISIGHT_RESTORE_ABORT);
        miEvent.addStr("SessionId", String.valueOf(restoreSessionId));
        miEvent.addStr("AgentType", str);
        miEvent.addStr("TotalDurationMs", String.valueOf(j));
        miEvent.addStr("StepDurations", str2);
        miEvent.addStr("Params", str3);
        if (str4 != null) {
            miEvent.addStr("Reason", str4);
        }
        sendMiSightEvent$default(INSTANCE, miEvent, "RESTORE_ABORT", null, 4, null);
    }

    public static /* synthetic */ void restoreStep4EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.restoreStep4EndFail(str, bundle);
    }

    public static /* synthetic */ void restoreStep5EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.restoreStep5EndFail(str, bundle);
    }

    public static /* synthetic */ void restoreStep6EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.restoreStep6EndFail(str, bundle);
    }

    public static /* synthetic */ void restoreStepEnd$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, int i, boolean z, String str, Bundle bundle, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str = null;
        }
        if ((i2 & 8) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.restoreStepEnd(i, z, str, bundle);
    }

    public static final void restoreStepEnd$lambda$35(int i, long j, String str, Bundle bundle) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_RESTORE_STEP_EXCEPTION);
        miEvent.addInt("Step", i);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = INSTANCE;
        miEvent.addStr("StepName", keyguardEditorMiSightUtil.restoreStepName(i));
        miEvent.addStr("DurationMs", String.valueOf(j));
        miEvent.addStr("Reason", str);
        if (bundle != null) {
            miEvent.addStr(IntentInfo.Extra.TAG_NAME, bundle.toString());
        }
        miEvent.addStr("SessionId", String.valueOf(restoreSessionId));
        miEvent.addStr("AgentType", restoreAgentType);
        Bundle bundle2 = restoreParams;
        if (bundle2 == null || (string = bundle2.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("Params", string);
        sendMiSightEvent$default(keyguardEditorMiSightUtil, miEvent, "RESTORE_STEP_EXCEPTION[Step=" + i + "]", null, 4, null);
    }

    public static final void restoreStepEnd$lambda$36(int i, long j) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_RESTORE_STEP_TIMEOUT);
        miEvent.addInt("Step", i);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = INSTANCE;
        miEvent.addStr("StepName", keyguardEditorMiSightUtil.restoreStepName(i));
        miEvent.addStr("DurationMs", String.valueOf(j));
        miEvent.addStr("SessionId", String.valueOf(restoreSessionId));
        miEvent.addStr("AgentType", restoreAgentType);
        Bundle bundle = restoreParams;
        if (bundle == null || (string = bundle.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("Params", string);
        sendMiSightEvent$default(keyguardEditorMiSightUtil, miEvent, "RESTORE_STEP_TIMEOUT[Step=" + i + "]", null, 4, null);
    }

    public static /* synthetic */ void sendMiSightEvent$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, MiEvent miEvent, String str, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = null;
        }
        keyguardEditorMiSightUtil.sendMiSightEvent(miEvent, str, str2);
    }

    public static /* synthetic */ void step2EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.step2EndFail(str, bundle);
    }

    public static /* synthetic */ void step4EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.step4EndFail(str, bundle);
    }

    public static /* synthetic */ void step6EndFail$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, String str, Bundle bundle, int i, Object obj) {
        if ((i & 2) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.step6EndFail(str, bundle);
    }

    public static final void templateApplyFinish$lambda$4(long j, String str, String str2) {
        MiEvent miEvent = new MiEvent(MISIGHT_TEMPLATE_APPLY_ABORT);
        miEvent.addStr("totalDurationMs", String.valueOf(j));
        miEvent.addStr("stepDurations", str);
        miEvent.addStr("sessionId", String.valueOf(templateApplySessionId));
        miEvent.addStr("params", str2);
        sendMiSightEvent$default(INSTANCE, miEvent, "TEMPLATE_APPLY_ABORT", null, 4, null);
    }

    public static /* synthetic */ void templateApplyStepEnd$default(KeyguardEditorMiSightUtil keyguardEditorMiSightUtil, int i, boolean z, String str, Bundle bundle, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str = null;
        }
        if ((i2 & 8) != 0) {
            bundle = null;
        }
        keyguardEditorMiSightUtil.templateApplyStepEnd(i, z, str, bundle);
    }

    public static final void templateApplyStepEnd$lambda$5(int i, long j, String str, Bundle bundle) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_TEMPLATE_APPLY_STEP_EXCEPTION);
        miEvent.addInt("step", i);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = INSTANCE;
        miEvent.addStr("stepName", keyguardEditorMiSightUtil.stepName(i));
        miEvent.addStr("durationMs", String.valueOf(j));
        miEvent.addStr(a.g, str);
        miEvent.addStr("extra", "extra: " + bundle);
        miEvent.addStr("sessionId", String.valueOf(templateApplySessionId));
        Bundle bundle2 = templateApplyParams;
        if (bundle2 == null || (string = bundle2.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        sendMiSightEvent$default(keyguardEditorMiSightUtil, miEvent, "TEMPLATE_APPLY_STEP_EXCEPTION[Step=" + i + "]", null, 4, null);
    }

    public static final void templateApplyStepEnd$lambda$6(int i, long j) {
        String string;
        MiEvent miEvent = new MiEvent(MISIGHT_TEMPLATE_APPLY_TIMEOUT);
        miEvent.addInt("step", i);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = INSTANCE;
        miEvent.addStr("stepName", keyguardEditorMiSightUtil.stepName(i));
        miEvent.addStr("durationMs", String.valueOf(j));
        miEvent.addStr("sessionId", String.valueOf(templateApplySessionId));
        Bundle bundle = templateApplyParams;
        if (bundle == null || (string = bundle.toString()) == null) {
            string = CategoryConstants.DEFAULT_PARAM;
        }
        miEvent.addStr("params", string);
        sendMiSightEvent$default(keyguardEditorMiSightUtil, miEvent, "TEMPLATE_APPLY_TIMEOUT[Step=" + i + "]", null, 4, null);
    }

    public static final void trackPrimaryFontFilter$lambda$22(MiEvent miEvent, int i, Integer num) {
        miEvent.addInt("EditorEffect", i);
        miEvent.addInt("ClockBeanEffect", num.intValue());
        sendMiSightEvent$default(INSTANCE, miEvent, "PRIMARY_FONTFILTER_ERROR[effectMismatch]", null, 4, null);
    }

    public static final void trackPrimaryFontFilter$lambda$23(MiEvent miEvent, boolean z, Boolean bool) {
        miEvent.addBool("EditorIsAutoPrimary", z);
        miEvent.addBool("ClockBeanIsAutoPrimary", bool.booleanValue());
        sendMiSightEvent$default(INSTANCE, miEvent, "PRIMARY_FONTFILTER_ERROR[autoMismatch]", null, 4, null);
    }

    public static final void trackPrimaryFontFilter$lambda$24(MiEvent miEvent, int i, Integer num) {
        miEvent.addInt("EditorPrimaryColor", i);
        miEvent.addInt("ClockBeanPrimaryColor", num.intValue());
        sendMiSightEvent$default(INSTANCE, miEvent, "PRIMARY_FONTFILTER_ERROR[colorMismatch]", null, 4, null);
    }

    public static final void trackSecondaryFontFilter$lambda$25(MiEvent miEvent, boolean z, Boolean bool) {
        miEvent.addBool("EditorIsAutoSecondary", z);
        miEvent.addBool("ClockBeanIsAutoSecondary", bool.booleanValue());
        sendMiSightEvent$default(INSTANCE, miEvent, "SECONDARY_FONTFILTER_ERROR[autoMismatch]", null, 4, null);
    }

    public static final void trackSecondaryFontFilter$lambda$26(MiEvent miEvent, int i, Integer num) {
        miEvent.addInt("EditorSecondaryColor", i);
        miEvent.addInt("ClockBeanSecondaryColor", num.intValue());
        sendMiSightEvent$default(INSTANCE, miEvent, "SECONDARY_FONTFILTER_ERROR[colorMismatch]", null, 4, null);
    }

    public final void backupSessionFinish(boolean z, final String str) {
        String string;
        if (backupInProgress) {
            final long jCurrentTimeMillis = System.currentTimeMillis() - backupSessionId;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < 7; i++) {
                if (i > 1) {
                    sb.append(ac.b);
                }
                sb.append(i);
                sb.append(":");
                sb.append(backupStepDurations[i]);
            }
            final String string2 = sb.toString();
            Bundle bundle = backupParams;
            if (bundle == null || (string = bundle.toString()) == null) {
                string = CategoryConstants.DEFAULT_PARAM;
            }
            final String str2 = string;
            final String str3 = backupAgentType;
            if (!z) {
                getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardEditorMiSightUtil.backupSessionFinish$lambda$29(str3, jCurrentTimeMillis, string2, str2, str);
                    }
                });
            }
            backupInProgress = false;
            backupParams = null;
        }
    }

    public final void backupSessionStart(String agentType, int i, int i2, int i3, int i4, float f) {
        Intrinsics.checkNotNullParameter(agentType, "agentType");
        backupSessionId = System.currentTimeMillis();
        backupInProgress = true;
        backupAgentType = agentType;
        int length = backupStepStart.length;
        for (int i5 = 0; i5 < length; i5++) {
            backupStepStart[i5] = 0;
            backupStepDurations[i5] = 0;
        }
        Bundle bundle = new Bundle();
        bundle.putString("agentType", agentType);
        bundle.putInt("deviceType", i);
        bundle.putInt("osVersion", i2);
        bundle.putInt("screenWidth", i3);
        bundle.putInt("screenHeight", i4);
        bundle.putFloat("screenDensity", f);
        backupParams = bundle;
    }

    public final void backupStep1EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        backupStepEnd(1, false, reason, bundle);
    }

    public final void backupStep1EndSuccess() {
        backupStepEnd$default(this, 1, true, null, null, 12, null);
    }

    public final void backupStep1Start() {
        backupStepStart(1);
    }

    public final void backupStep2EndSuccess() {
        backupStepEnd$default(this, 2, true, null, null, 12, null);
    }

    public final void backupStep2Start() {
        backupStepStart(2);
    }

    public final void backupStep3EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        backupStepEnd(3, false, reason, bundle);
    }

    public final void backupStep3EndSuccess() {
        backupStepEnd$default(this, 3, true, null, null, 12, null);
    }

    public final void backupStep3Start() {
        backupStepStart(3);
    }

    public final void backupStep4EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        backupStepEnd(4, false, reason, bundle);
    }

    public final void backupStep4EndSuccess() {
        backupStepEnd$default(this, 4, true, null, null, 12, null);
    }

    public final void backupStep4Start() {
        backupStepStart(4);
    }

    public final void backupStep5EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        backupStepEnd(5, false, reason, bundle);
    }

    public final void backupStep5EndSuccess() {
        backupStepEnd$default(this, 5, true, null, null, 12, null);
    }

    public final void backupStep5Start() {
        backupStepStart(5);
    }

    public final void backupStep6EndSuccess() {
        backupStepEnd$default(this, 6, true, null, null, 12, null);
    }

    public final void backupStep6Start() {
        backupStepStart(6);
    }

    public final void backupStepEnd(final int i, boolean z, String str, final Bundle bundle) {
        if (1 > i || i >= 7) {
            return;
        }
        long j = backupStepStart[i];
        final long jCurrentTimeMillis = j > 0 ? System.currentTimeMillis() - j : -1L;
        if (jCurrentTimeMillis >= 0) {
            backupStepDurations[i] = jCurrentTimeMillis;
        }
        if (!z) {
            final String str2 = str == null ? "unknown" : str;
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.backupStepEnd$lambda$30(i, jCurrentTimeMillis, str2, bundle);
                }
            });
        } else if (jCurrentTimeMillis >= BACKUP_STEP_TIMEOUT_THRESHOLD) {
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.backupStepEnd$lambda$31(i, jCurrentTimeMillis);
                }
            });
        }
    }

    public final String backupStepName(int i) {
        switch (i) {
            case 1:
                return "1_backup_current_template";
            case 2:
                return "2_backup_system_params";
            case 3:
                return "3_backup_history_templates";
            case 4:
                return "4_backup_wallpaper";
            case 5:
                return "5_zip_and_attach";
            case 6:
                return "6_backup_cleanup";
            default:
                return "unknown";
        }
    }

    public final void backupStepStart(int i) {
        if (1 > i || i >= 7) {
            return;
        }
        backupStepStart[i] = System.currentTimeMillis();
    }

    public final void cancelFirstFrameTimer() {
        firstFrameStartTs = 0L;
    }

    public final void dfxLog(String str, String str2) {
        StackTraceElement stackTraceElement;
        String str3;
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
        int length = stackTrace.length;
        int i = 0;
        while (true) {
            stackTraceElement = null;
            if (i >= length) {
                break;
            }
            StackTraceElement stackTraceElement2 = stackTrace[i];
            String className = stackTraceElement2.getClassName();
            Intrinsics.checkNotNullExpressionValue(className, "getClassName(...)");
            if (!StringsKt.contains$default((CharSequence) className, (CharSequence) "KeyguardEditorMiSightUtil", false, 2, (Object) null)) {
                stackTraceElement = stackTraceElement2;
                break;
            }
            i++;
        }
        if (stackTraceElement != null) {
            str3 = stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber();
        } else {
            str3 = "unknown";
        }
        Log.w("KeyguardEditorDFX", "[" + str + "] reason=" + str2 + " caller=" + str3);
    }

    public final MiSightHandler getMiSightHandler() {
        return (MiSightHandler) miSightHandler$delegate.getValue();
    }

    public final void hierarchyComputeEnd(final String str, Long l) {
        if (hierarchyInProgress) {
            if (str == null) {
                str = StyleInfo.DEFAULT_PARAMETER;
            }
            Long l2 = (Long) hierarchyComputeStartMap.remove(str);
            if (l2 != null) {
                final long jLongValue = l != null ? l.longValue() : System.currentTimeMillis() - l2.longValue();
                long j = HIERARCHY_COMPUTE_TIMEOUT_THRESHOLD;
                if (jLongValue >= j) {
                    dfxLog("HIERARCHY_COMPUTE_TIMEOUT", "checkType=" + str + " duration=" + jLongValue + "ms threshold=" + j + "ms");
                    getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardEditorMiSightUtil.hierarchyComputeEnd$lambda$20(str, jLongValue);
                        }
                    });
                }
            }
        }
    }

    public final void hierarchyComputeStart(String str) {
        if (hierarchyInProgress) {
            if (str == null) {
                str = StyleInfo.DEFAULT_PARAMETER;
            }
            hierarchyComputeStartMap.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public final void hierarchySessionFinish() {
        hierarchyInProgress = false;
        hierarchyParams = null;
        hierarchyComputeStartMap.clear();
    }

    public final void hierarchySessionStart(String str, String str2, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        hierarchySessionId = System.currentTimeMillis();
        hierarchyInProgress = true;
        Bundle bundle = new Bundle();
        bundle.putString("source", str);
        bundle.putString("templateId", str2);
        bundle.putBoolean("isUserOpenHierarchy", z);
        bundle.putBoolean("isScreenshotTemplateCheck", z2);
        bundle.putBoolean("isLargeScreen", z3);
        bundle.putInt(VariableNames.ORIENTATION, i);
        bundle.putBoolean("dualClock", z4);
        hierarchyParams = bundle;
        hierarchyComputeStartMap.clear();
    }

    public final void markFirstFrameArrived(long j) {
        if (wallpaperDisplayInProgress) {
            long j2 = firstFrameStartTs;
            if (j2 <= 0) {
                return;
            }
            final long j3 = j - j2;
            firstFrameStartTs = 0L;
            long j4 = WALLPAPER_FIRST_FRAME_TIMEOUT_THRESHOLD;
            if (j3 >= j4) {
                dfxLog("FIRST_FRAME_TIMEOUT", "duration=" + j3 + "ms threshold=" + j4 + "ms");
                getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardEditorMiSightUtil.markFirstFrameArrived$lambda$14(j3);
                    }
                });
            }
        }
    }

    public final void reportApplyConfigIsNull() {
        step4EndFail$default(this, "applyConfig is null", null, 2, null);
    }

    public final void reportApplyWallpaperFailed(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        dfxLog("APPLY_WALLPAPER_FAILED", reason);
        step6EndFail(reason, bundle);
    }

    public final void reportBackupCurrentTemplateFail(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        backupStep1EndFail$default(this, reason, null, 2, null);
        reportDataLoss$default(this, "backup", "backup_current_template_fail", "critical", reason, null, 16, null);
    }

    public final void reportBackupHistoryFail(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        backupStep3EndFail("history templates serialization exception", bundle);
        String message = e.getMessage();
        if (message == null) {
            message = "unknown";
        }
        reportDataLoss("backup", "backup_history_fail", "medium", message, bundle);
    }

    public final void reportBackupMatchSkip() {
        reportDataLoss$default(this, "backup", "backup_match_skip", "high", "isMatchBackup() returned false", null, 16, null);
    }

    public final void reportBackupWallpaperNullType() {
        backupStep4EndFail$default(this, "wallpaper type is null", null, 2, null);
        reportDataLoss$default(this, "backup", "backup_wallpaper_null_type", "high", "wallpaper type is null, wallpaper not backed up", null, 16, null);
    }

    public final void reportBackupZipException(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        backupStep5EndFail("zip packaging exception", bundle);
        String message = e.getMessage();
        if (message == null) {
            message = "unknown";
        }
        reportDataLoss("backup", "backup_zip_exception", "critical", message, bundle);
    }

    public final void reportCopyResourceError(boolean z, boolean z2, boolean z3, boolean z4) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("lockWallpaperPathNull", z);
        bundle.putBoolean("homeWallpaperPathNull", z2);
        bundle.putBoolean("screenshotCopyError", z3);
        bundle.putBoolean("wallpaperScreenshotCopyError", z4);
        step5EndFail("copy resource to current dir failed", bundle);
    }

    public final void reportCurrentTemplateException(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        restoreStep5EndFail("restoreCurrentTemplate exception", bundle);
        String message = e.getMessage();
        if (message == null) {
            message = "unknown";
        }
        reportDataLoss("restore", "restore_current_template_fail", "critical", message, bundle);
    }

    public final void reportCurrentTemplateSkip(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStep5EndFail$default(this, "shouldRestoreCurrent=false: " + reason, null, 2, null);
        reportDataLoss$default(this, "restore", "restore_current_template_skip", "high", reason, null, 16, null);
    }

    public final void reportDataLoss(final String phase, final String lossType, final String level, final String reason, final Bundle bundle) {
        Bundle bundle2;
        String string;
        Bundle bundle3;
        Intrinsics.checkNotNullParameter(phase, "phase");
        Intrinsics.checkNotNullParameter(lossType, "lossType");
        Intrinsics.checkNotNullParameter(level, "level");
        Intrinsics.checkNotNullParameter(reason, "reason");
        final long j = Intrinsics.areEqual(phase, "backup") ? backupSessionId : restoreSessionId;
        final String str = Intrinsics.areEqual(phase, "backup") ? backupAgentType : restoreAgentType;
        final String str2 = (!Intrinsics.areEqual(phase, "backup") ? !((bundle2 = restoreParams) == null || (string = bundle2.toString()) == null) : !((bundle3 = backupParams) == null || (string = bundle3.toString()) == null)) ? CategoryConstants.DEFAULT_PARAM : string;
        getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardEditorMiSightUtil.reportDataLoss$lambda$50(j, str, phase, lossType, level, reason, bundle, str2);
            }
        });
    }

    public final void reportDeviceMismatch(int i, int i2, int i3, int i4, String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Bundle bundle = new Bundle();
        bundle.putInt("oldDeviceType", i);
        bundle.putInt("newDeviceType", i2);
        bundle.putInt("oldOsVersion", i3);
        bundle.putInt("newOsVersion", i4);
        restoreStep2EndFail(reason, bundle);
        reportDataLoss("restore", "restore_device_mismatch", "critical", reason, bundle);
    }

    public final void reportHierarchyInvisible(final String reason, final Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        if (hierarchyInProgress) {
            dfxLog("HIERARCHY_INVISIBLE", reason);
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.reportHierarchyInvisible$lambda$21(reason, bundle);
                }
            });
            return;
        }
        Log.d(tag, "reportHierarchyInvisible: skipped (no active session), reason=" + reason);
    }

    public final void reportHistoryAllFail(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        restoreStep4EndFail("restoreHistoryTemplates exception", bundle);
        String message = e.getMessage();
        if (message == null) {
            message = "unknown";
        }
        reportDataLoss("restore", "restore_history_all_fail", "high", message, bundle);
    }

    public final void reportHistoryAllSkip(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStep4EndFail$default(this, "history templates restore skipped: " + reason, null, 2, null);
        reportDataLoss$default(this, "restore", "restore_history_all_skip", "medium", reason, null, 16, null);
    }

    public final void reportHistoryTemplateSkip(String templateId, String reason) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(reason, "reason");
        Bundle bundle = new Bundle();
        bundle.putString("templateId", templateId);
        reportDataLoss("restore", "restore_history_template_skip", "medium", "templateId=" + templateId + ": " + reason, bundle);
    }

    public final void reportMakeCurrentToHistoryException(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        step1EndFail("makeCurrentTemplateToHistory exception", bundle);
    }

    public final void reportRestoreFileWriteFail(String path, Throwable th) {
        Intrinsics.checkNotNullParameter(path, "path");
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        if (th != null) {
            bundle.putString("exception", Log.getStackTraceString(th));
        }
        restoreStep3EndFail("restoreFile I/O write failed", bundle);
        reportDataLoss("restore", "restore_file_write_fail", "critical", "file write failed: " + path, bundle);
    }

    public final void reportSettingsParseFail(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        String message = e.getMessage();
        if (message == null) {
            message = "unknown";
        }
        reportDataLoss("restore", "restore_settings_parse_fail", "high", message, bundle);
    }

    public final void reportThirdPartyWallpaperBlocked() {
        step6EndFail$default(this, "third party wallpaper not allowed", null, 2, null);
    }

    public final void reportUnzipFail(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        restoreStep3EndFail("unzip failed", bundle);
        String message = e.getMessage();
        if (message == null) {
            message = "unknown";
        }
        reportDataLoss("restore", "restore_unzip_fail", "critical", message, bundle);
    }

    public final void reportVersionReject(int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("backupVersion", i);
        bundle.putInt("currentVersion", i2);
        restoreStep1EndFail("version downgrade rejected: backup=" + i + ", current=" + i2, bundle);
        reportDataLoss("restore", "restore_version_reject", "critical", "version downgrade rejected", bundle);
    }

    public final void reportVersionUnknown(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        restoreStep1EndFail("extractVersionFromPath returned null", bundle);
        reportDataLoss("restore", "restore_version_unknown", "critical", "cannot extract version from path: " + path, bundle);
    }

    public final void reportWaitWallpaperChangedTimeout(final long j) {
        dfxLog("WAIT_WALLPAPER_CHANGED_TIMEOUT", "duration=" + j + "ms");
        getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardEditorMiSightUtil.reportWaitWallpaperChangedTimeout$lambda$10(j);
            }
        });
    }

    public final void reportWallpaperBlackScreen(final String reason, final Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        dfxLog("BLACK_SCREEN", reason);
        getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardEditorMiSightUtil.reportWallpaperBlackScreen$lambda$15(reason, bundle);
            }
        });
    }

    public final void reportWallpaperFlashScreen(final String reason, final Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        dfxLog("FLASH_SCREEN", reason);
        final long jCurrentTimeMillis = System.currentTimeMillis();
        if (flashAlreadyReported) {
            return;
        }
        long j = flashWindowStartTs;
        if (j == 0 || jCurrentTimeMillis - j > FLASH_DETECT_WINDOW_MS) {
            flashWindowStartTs = jCurrentTimeMillis;
            flashCountInWindow = 1;
            return;
        }
        int i = flashCountInWindow + 1;
        flashCountInWindow = i;
        if (i >= FLASH_COUNT_THRESHOLD) {
            flashAlreadyReported = true;
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.reportWallpaperFlashScreen$lambda$17(reason, jCurrentTimeMillis, bundle);
                }
            });
        }
    }

    public final void reportWallpaperInfoInvalid(boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("lockWallpaperInfoNull", z);
        bundle.putBoolean("homeWallpaperInfoNull", z2);
        step5EndFail("wallpaper info invalid", bundle);
    }

    public final void reportWallpaperNotDisplayed(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        reportWallpaperNotDisplayed$default(this, reason, null, 2, null);
    }

    public final void reportWallpaperNotDisplayed(final String reason, final Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        dfxLog("NOT_DISPLAYED", reason);
        getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$lambda$18(reason, bundle);
            }
        });
    }

    public final void reportWallpaperRestoreFail(String scene, String attemptedTypes, String reason) {
        Intrinsics.checkNotNullParameter(scene, "scene");
        Intrinsics.checkNotNullParameter(attemptedTypes, "attemptedTypes");
        Intrinsics.checkNotNullParameter(reason, "reason");
        Bundle bundle = new Bundle();
        bundle.putString("scene", scene);
        bundle.putString("attemptedTypes", attemptedTypes);
        restoreStep6EndFail(reason, bundle);
        reportDataLoss("restore", "restore_wallpaper_fail", "high", reason, bundle);
    }

    public final void reportWallpaperRollback() {
        reportDataLoss$default(this, "restore", "restore_wallpaper_skip", "medium", "wallpaper rollback triggered due to shouldRestoreCurrent=false", null, 16, null);
    }

    public final void reportWallpaperRollbackFail(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Bundle bundle = new Bundle();
        bundle.putString("exception", Log.getStackTraceString(e));
        restoreStep6EndFail("wallpaper rollback also failed", bundle);
        String message = e.getMessage();
        if (message == null) {
            message = "unknown";
        }
        reportDataLoss("restore", "restore_wallpaper_rollback_fail", "critical", message, bundle);
    }

    public final void reportWallpaperSkip(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStep6EndFail$default(this, "wallpaper restore skipped: " + reason, null, 2, null);
        reportDataLoss$default(this, "restore", "restore_wallpaper_skip", "medium", reason, null, 16, null);
    }

    public final void restoreSessionFinish(boolean z, final String str) {
        String string;
        if (restoreInProgress) {
            final long jCurrentTimeMillis = System.currentTimeMillis() - restoreSessionId;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < 8; i++) {
                if (i > 1) {
                    sb.append(ac.b);
                }
                sb.append(i);
                sb.append(":");
                sb.append(restoreStepDurations[i]);
            }
            final String string2 = sb.toString();
            Bundle bundle = restoreParams;
            if (bundle == null || (string = bundle.toString()) == null) {
                string = CategoryConstants.DEFAULT_PARAM;
            }
            final String str2 = string;
            final String str3 = restoreAgentType;
            if (!z) {
                getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardEditorMiSightUtil.restoreSessionFinish$lambda$34(str3, jCurrentTimeMillis, string2, str2, str);
                    }
                });
            }
            restoreInProgress = false;
            restoreParams = null;
        }
    }

    public final void restoreSessionStart(String agentType, String fileType, int i, int i2, int i3, String str) {
        Intrinsics.checkNotNullParameter(agentType, "agentType");
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        restoreSessionId = System.currentTimeMillis();
        restoreInProgress = true;
        restoreAgentType = agentType;
        int length = restoreStepStart.length;
        for (int i4 = 0; i4 < length; i4++) {
            restoreStepStart[i4] = 0;
            restoreStepDurations[i4] = 0;
        }
        Bundle bundle = new Bundle();
        bundle.putString("agentType", agentType);
        bundle.putString("fileType", fileType);
        bundle.putInt("backupOsVersion", i);
        bundle.putInt("deviceType", i2);
        bundle.putInt("osVersion", i3);
        if (str != null) {
            if (str.length() > 200) {
                str = str.substring(0, 200);
                Intrinsics.checkNotNullExpressionValue(str, "substring(...)");
            }
            bundle.putString("backupMeta", str);
        }
        restoreParams = bundle;
    }

    public final void restoreStep1EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStepEnd(1, false, reason, bundle);
    }

    public final void restoreStep1EndSuccess() {
        restoreStepEnd$default(this, 1, true, null, null, 12, null);
    }

    public final void restoreStep1Start() {
        restoreStepStart(1);
    }

    public final void restoreStep2EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStepEnd(2, false, reason, bundle);
    }

    public final void restoreStep2EndSuccess() {
        restoreStepEnd$default(this, 2, true, null, null, 12, null);
    }

    public final void restoreStep2Start() {
        restoreStepStart(2);
    }

    public final void restoreStep3EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStepEnd(3, false, reason, bundle);
    }

    public final void restoreStep3EndSuccess() {
        restoreStepEnd$default(this, 3, true, null, null, 12, null);
    }

    public final void restoreStep3Start() {
        restoreStepStart(3);
    }

    public final void restoreStep4EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStepEnd(4, false, reason, bundle);
    }

    public final void restoreStep4EndSuccess() {
        restoreStepEnd$default(this, 4, true, null, null, 12, null);
    }

    public final void restoreStep4Start() {
        restoreStepStart(4);
    }

    public final void restoreStep5EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStepEnd(5, false, reason, bundle);
    }

    public final void restoreStep5EndSuccess() {
        restoreStepEnd$default(this, 5, true, null, null, 12, null);
    }

    public final void restoreStep5Start() {
        restoreStepStart(5);
    }

    public final void restoreStep6EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        restoreStepEnd(6, false, reason, bundle);
    }

    public final void restoreStep6EndSuccess() {
        restoreStepEnd$default(this, 6, true, null, null, 12, null);
    }

    public final void restoreStep6Start() {
        restoreStepStart(6);
    }

    public final void restoreStep7EndSuccess() {
        restoreStepEnd$default(this, 7, true, null, null, 12, null);
    }

    public final void restoreStep7Start() {
        restoreStepStart(7);
    }

    public final void restoreStepEnd(final int i, boolean z, String str, final Bundle bundle) {
        if (1 > i || i >= 8) {
            return;
        }
        long j = restoreStepStart[i];
        final long jCurrentTimeMillis = j > 0 ? System.currentTimeMillis() - j : -1L;
        if (jCurrentTimeMillis >= 0) {
            restoreStepDurations[i] = jCurrentTimeMillis;
        }
        if (!z) {
            final String str2 = str == null ? "unknown" : str;
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.restoreStepEnd$lambda$35(i, jCurrentTimeMillis, str2, bundle);
                }
            });
        } else if (jCurrentTimeMillis >= RESTORE_STEP_TIMEOUT_THRESHOLD) {
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.restoreStepEnd$lambda$36(i, jCurrentTimeMillis);
                }
            });
        }
    }

    public final String restoreStepName(int i) {
        switch (i) {
            case 1:
                return "1_version_check";
            case 2:
                return "2_device_match";
            case 3:
                return "3_unzip_backup";
            case 4:
                return "4_restore_history_templates";
            case 5:
                return "5_restore_current_template";
            case 6:
                return "6_restore_wallpaper";
            case 7:
                return "7_restore_cleanup";
            default:
                return "unknown";
        }
    }

    public final void restoreStepStart(int i) {
        if (1 > i || i >= 8) {
            return;
        }
        restoreStepStart[i] = System.currentTimeMillis();
    }

    public final void sendMiSightEvent(MiEvent miEvent, String str, String str2) {
        MiSight.sendEvent(miEvent);
    }

    public final void step1EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        templateApplyStepEnd(1, false, reason, bundle);
    }

    public final void step1EndSuccess() {
        templateApplyStepEnd$default(this, 1, true, null, null, 12, null);
    }

    public final void step1Start() {
        templateApplyStepStart(1);
    }

    public final void step2EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        templateApplyStepEnd(2, false, reason, bundle);
    }

    public final void step2EndSuccess() {
        templateApplyStepEnd$default(this, 2, true, null, null, 12, null);
    }

    public final void step2Start() {
        templateApplyStepStart(2);
    }

    public final void step3EndSuccess() {
        templateApplyStepEnd$default(this, 3, true, null, null, 12, null);
    }

    public final void step3Start() {
        templateApplyStepStart(3);
    }

    public final void step4EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        templateApplyStepEnd(4, false, reason, bundle);
    }

    public final void step4EndSuccess() {
        templateApplyStepEnd$default(this, 4, true, null, null, 12, null);
    }

    public final void step4Start() {
        templateApplyStepStart(4);
    }

    public final void step5EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        templateApplyStepEnd(5, false, reason, bundle);
    }

    public final void step5EndSuccess() {
        templateApplyStepEnd$default(this, 5, true, null, null, 12, null);
    }

    public final void step5Start() {
        templateApplyStepStart(5);
    }

    public final void step6EndFail(String reason, Bundle bundle) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        templateApplyStepEnd(6, false, reason, bundle);
    }

    public final void step6EndSuccess() {
        templateApplyStepEnd$default(this, 6, true, null, null, 12, null);
    }

    public final void step6Start() {
        templateApplyStepStart(6);
    }

    public final void step7EndSuccess() {
        templateApplyStepEnd$default(this, 7, true, null, null, 12, null);
    }

    public final void step7Start() {
        templateApplyStepStart(7);
    }

    public final void step8EndSuccess() {
        templateApplyStepEnd$default(this, 8, true, null, null, 12, null);
    }

    public final void step8Start() {
        templateApplyStepStart(8);
    }

    public final String stepName(int i) {
        switch (i) {
            case 1:
                return "1_backup_current_to_history";
            case 2:
                return "2_save_current_json";
            case 3:
                return "3_insert_history_db";
            case 4:
                return "4_parse_config";
            case 5:
                return "5_copy_resources";
            case 6:
                return "6_apply_wallpaper";
            case 7:
                return "7_write_settings";
            case 8:
                return "8_notify_aod";
            default:
                return "unknown";
        }
    }

    public final void templateApplyFinish(boolean z) {
        final String string;
        if (templateApplyInProgress) {
            final long jCurrentTimeMillis = System.currentTimeMillis() - templateApplySessionId;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < 9; i++) {
                if (i > 1) {
                    sb.append(ac.b);
                }
                sb.append(i);
                sb.append(":");
                sb.append(templateStepDurations[i]);
            }
            final String string2 = sb.toString();
            Bundle bundle = templateApplyParams;
            if (bundle == null || (string = bundle.toString()) == null) {
                string = CategoryConstants.DEFAULT_PARAM;
            }
            if (!z) {
                getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardEditorMiSightUtil.templateApplyFinish$lambda$4(jCurrentTimeMillis, string2, string);
                    }
                });
            }
            templateApplyInProgress = false;
            templateApplyParams = null;
        }
    }

    public final void templateApplyStart(String str, boolean z, long j, boolean z2, boolean z3, int i, int i2) {
        templateApplySessionId = System.currentTimeMillis();
        templateApplyInProgress = true;
        int length = templateStepStart.length;
        for (int i3 = 0; i3 < length; i3++) {
            templateStepStart[i3] = 0;
            templateStepDurations[i3] = 0;
        }
        Bundle bundle = new Bundle();
        bundle.putString("configUri", str);
        bundle.putBoolean("isPreset", z);
        bundle.putLong("templateSource", j);
        bundle.putBoolean("shouldApplyToAll", z2);
        bundle.putBoolean("waitWallpaperChanged", z3);
        bundle.putInt("screenStatus", i);
        bundle.putInt("hideFlag", i2);
        templateApplyParams = bundle;
    }

    public final void templateApplyStepEnd(final int i, boolean z, String str, final Bundle bundle) {
        if (1 > i || i >= 9) {
            return;
        }
        long j = templateStepStart[i];
        final long jCurrentTimeMillis = j > 0 ? System.currentTimeMillis() - j : -1L;
        if (jCurrentTimeMillis >= 0) {
            templateStepDurations[i] = jCurrentTimeMillis;
        }
        if (!z) {
            final String str2 = str == null ? "unknown" : str;
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.templateApplyStepEnd$lambda$5(i, jCurrentTimeMillis, str2, bundle);
                }
            });
        } else if (jCurrentTimeMillis >= TEMPLATE_APPLY_STEP_TIMEOUT_THRESHOLD) {
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.templateApplyStepEnd$lambda$6(i, jCurrentTimeMillis);
                }
            });
        }
    }

    public final void templateApplyStepStart(int i) {
        if (1 > i || i >= 9) {
            return;
        }
        templateStepStart[i] = System.currentTimeMillis();
    }

    public final void trackPrimaryFontFilter(final int i, final Integer num, final boolean z, final Boolean bool, final int i2, final Integer num2) {
        if (num == null || num2 == null || bool == null) {
            return;
        }
        final MiEvent miEvent = new MiEvent(MISIGHT_PRIMARY_FONTFILTER_ERROR);
        if (i != num.intValue()) {
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.trackPrimaryFontFilter$lambda$22(miEvent, i, num);
                }
            });
            return;
        }
        if (!Intrinsics.areEqual(Boolean.valueOf(z), bool)) {
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.trackPrimaryFontFilter$lambda$23(miEvent, z, bool);
                }
            });
        } else {
            if (bool.booleanValue() || z || i2 == num2.intValue()) {
                return;
            }
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.trackPrimaryFontFilter$lambda$24(miEvent, i2, num2);
                }
            });
        }
    }

    public final void trackSecondaryFontFilter(final boolean z, final Boolean bool, final int i, final Integer num) {
        if (num == null || bool == null) {
            return;
        }
        final MiEvent miEvent = new MiEvent(MISIGHT_SECONDARY_FONTFILTER_ERROR);
        if (!Intrinsics.areEqual(Boolean.valueOf(z), bool)) {
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.trackSecondaryFontFilter$lambda$25(miEvent, z, bool);
                }
            });
        } else {
            if (bool.booleanValue() || z || i == num.intValue()) {
                return;
            }
            getMiSightHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditorMiSightUtil.trackSecondaryFontFilter$lambda$26(miEvent, i, num);
                }
            });
        }
    }

    public final void wallpaperDisplayFinish() {
        wallpaperDisplayInProgress = false;
        wallpaperDisplayParams = null;
        firstFrameStartTs = 0L;
        flashWindowStartTs = 0L;
        flashCountInWindow = 0;
        flashAlreadyReported = false;
    }

    public final void wallpaperDisplayStart(String str, String str2, int i, boolean z, boolean z2, boolean z3, String str3, boolean z4, boolean z5) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        wallpaperDisplaySessionId = jCurrentTimeMillis;
        wallpaperDisplayInProgress = true;
        firstFrameStartTs = jCurrentTimeMillis;
        flashWindowStartTs = 0L;
        flashCountInWindow = 0;
        flashAlreadyReported = false;
        Bundle bundle = new Bundle();
        bundle.putString("source", str);
        bundle.putString("wallpaperType", str2);
        bundle.putInt("magicType", i);
        bundle.putBoolean("enableBlur", z);
        bundle.putBoolean("isVideo", z2);
        bundle.putBoolean("editable", z3);
        bundle.putString("infoType", str3);
        bundle.putBoolean("isFoldLarge", z4);
        bundle.putBoolean("isLiteDevice", z5);
        wallpaperDisplayParams = bundle;
    }
}

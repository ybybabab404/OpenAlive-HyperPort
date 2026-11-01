package com.miui.keyguard.editor.data.migration;

import android.content.Context;
import android.util.Log;
import com.miui.clock.allInOne.AllInOneUtil;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
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
/* JADX INFO: compiled from: AllInOneClockMigration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AllInOneClockMigration {
    public static final AllInOneClockMigration INSTANCE = new AllInOneClockMigration();
    public static final Map TEMPLATE_STYLE_MAP = MapsKt.mapOf(TuplesKt.to("classic_max", 1), TuplesKt.to("oversize_a", 4), TuplesKt.to("oversize_b", 5), TuplesKt.to("magazine_c", 6));
    public static final Set UPGRADABLE_TEMPLATES = SetsKt.setOf((Object[]) new String[]{"classic_max", "classic_plus", "oversize_a", "oversize_b", "magazine_c"});
    public static final Map OVERSIZE_A_HOLLOW_STYLE_MAP = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 4), TuplesKt.to(3, 3));
    public static final Map MAGAZINE_C_HOLLOW_STYLE_MAP = MapsKt.mapOf(TuplesKt.to(7, 1), TuplesKt.to(6, 3), TuplesKt.to(8, 4), TuplesKt.to(9, 1));

    public final ClockInfo adaptToNewScreen(ClockInfo oldClockInfo, DeviceUtil.ScreenInfo oldScreenParams, DeviceUtil.ScreenInfo newScreenParams) {
        Intrinsics.checkNotNullParameter(oldClockInfo, "oldClockInfo");
        Intrinsics.checkNotNullParameter(oldScreenParams, "oldScreenParams");
        Intrinsics.checkNotNullParameter(newScreenParams, "newScreenParams");
        String templateId = oldClockInfo.getTemplateId();
        int style = oldClockInfo.getStyle();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!shouldUpgradeToAllInOne(templateId, style, deviceUtil.isQ18Device())) {
            Log.d("Keyguard-Editor-AllInOneClockMigration", "adaptToNewScreen: not all in one clock, skip adaptation");
            return oldClockInfo;
        }
        if (oldScreenParams.getWidth() <= 0 || oldScreenParams.getHeight() <= 0 || newScreenParams.getWidth() <= 0 || newScreenParams.getHeight() <= 0) {
            Log.w("Keyguard-Editor-AllInOneClockMigration", "adaptToNewScreen: invalid screen params, old=" + oldScreenParams + ", new=" + newScreenParams + ", skip adaptation");
            return oldClockInfo;
        }
        float fCalculateNewDpPosition = deviceUtil.calculateNewDpPosition(oldClockInfo.getTimeWidth(), oldScreenParams, newScreenParams, true);
        float fCalculateNewDpPosition2 = deviceUtil.calculateNewDpPosition(oldClockInfo.getTimeHeight(), oldScreenParams, newScreenParams, false);
        Log.d("Keyguard-Editor-AllInOneClockMigration", "adaptToNewScreen: oldTimeWidth=" + oldClockInfo.getTimeWidth() + ", oldTimeHeight=" + oldClockInfo.getTimeHeight() + ", newTimeWidth=" + fCalculateNewDpPosition + ", newTimeHeight=" + fCalculateNewDpPosition2);
        return oldClockInfo.copy(((-1) & 1) != 0 ? oldClockInfo.templateId : null, ((-1) & 2) != 0 ? oldClockInfo.primaryColor : 0, ((-1) & 4) != 0 ? oldClockInfo.secondaryColor : 0, ((-1) & 8) != 0 ? oldClockInfo.blendColor : 0, ((-1) & 16) != 0 ? oldClockInfo.secondaryBlendColor : 0, ((-1) & 32) != 0 ? oldClockInfo.clockEffect : 0, ((-1) & 64) != 0 ? oldClockInfo.style : 0, ((-1) & 128) != 0 ? oldClockInfo.enableDiffusion : false, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? oldClockInfo.classicLine1 : 0, ((-1) & 512) != 0 ? oldClockInfo.classicLine2 : 0, ((-1) & 1024) != 0 ? oldClockInfo.classicLine3 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? oldClockInfo.classicLine4 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? oldClockInfo.classicLine5 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? oldClockInfo.primaryFontColorPanelId : 0, ((-1) & 16384) != 0 ? oldClockInfo.secondaryFontColorPanelId : 0, ((-1) & 32768) != 0 ? oldClockInfo.classicSignature : null, ((-1) & 65536) != 0 ? oldClockInfo.signatureLine1 : null, ((-1) & 131072) != 0 ? oldClockInfo.signatureLine2 : null, ((-1) & 262144) != 0 ? oldClockInfo.signatureLine3 : null, ((-1) & 524288) != 0 ? oldClockInfo.isAutoPrimaryColor : false, ((-1) & 1048576) != 0 ? oldClockInfo.isAutoSecondaryColor : false, ((-1) & 2097152) != 0 ? oldClockInfo.isDiffHourMinuteColor : false, ((-1) & 4194304) != 0 ? oldClockInfo.primaryColorValueInfo : null, ((-1) & 8388608) != 0 ? oldClockInfo.secondaryColorValueInfo : null, ((-1) & 16777216) != 0 ? oldClockInfo.isDarkClockArea : false, ((-1) & 33554432) != 0 ? oldClockInfo.clockWeight : 0, ((-1) & 67108864) != 0 ? oldClockInfo.extraFlag : 0, ((-1) & 134217728) != 0 ? oldClockInfo.clockTopRatio : 0.0f, ((-1) & 268435456) != 0 ? oldClockInfo.clockBottomRatio : 0.0f, ((-1) & 536870912) != 0 ? oldClockInfo.timeWidth : fCalculateNewDpPosition, ((-1) & 1073741824) != 0 ? oldClockInfo.timeHeight : fCalculateNewDpPosition2, ((-1) & Integer.MIN_VALUE) != 0 ? oldClockInfo.clockDepthType : 0, (255 & 1) != 0 ? oldClockInfo.glassTransparency : 0.0f, (255 & 2) != 0 ? oldClockInfo.fontStyle : 0, (255 & 4) != 0 ? oldClockInfo.hollowStyle : 0, (255 & 8) != 0 ? oldClockInfo.isDoubleRow : false, (255 & 16) != 0 ? oldClockInfo.isColonShow : false, (255 & 32) != 0 ? oldClockInfo.isSkipScreenTransform : false, (255 & 64) != 0 ? oldClockInfo.userModifiedPrimaryColor : false, (255 & 128) != 0 ? oldClockInfo.userModifiedSecondaryColor : false);
    }

    public final ClockBean getDefaultAllInOneParams(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ClockBean clockBean = new ClockBean("all_in_one", i);
        AllInOneUtil.initClockParams(clockBean, context);
        return clockBean;
    }

    public final Set getUPGRADABLE_TEMPLATES() {
        return UPGRADABLE_TEMPLATES;
    }

    public final boolean shouldUpgradeToAllInOne(String templateId, int i, boolean z) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        if (Intrinsics.areEqual(templateId, "magazine_c") && i == 5) {
            return false;
        }
        return z ? !Intrinsics.areEqual(templateId, "classic") : UPGRADABLE_TEMPLATES.contains(templateId);
    }
}

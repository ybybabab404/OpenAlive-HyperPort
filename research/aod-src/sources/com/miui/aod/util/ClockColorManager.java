package com.miui.aod.util;

import android.graphics.Color;
import com.miui.aod.AODApplicationDelegate;
import com.miui.aod.Utils;
import com.miui.aod.widget.OnClockColorChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import miuix.animation.utils.EaseManager;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClockColorManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class ClockColorManager {
    public static final ClockColorManager INSTANCE = new ClockColorManager();
    public static final int[] SVG_CLOCK_TIME_COLOR_DEFAULT_RESOURCE = {Color.parseColor("#D2E4FD"), Color.parseColor("#B3CEF1"), Color.parseColor("#E0EDFF"), Color.parseColor("#96B8E7"), Color.parseColor("#5087FF")};
    public static final int[] SVG_CLOCK_TIME_COLOR_1_RESOURCE = {Color.parseColor("#FF9055"), Color.parseColor("#FA5B50"), Color.parseColor("#FF7E57"), Color.parseColor("#F65151"), Color.parseColor("#FFD84B")};
    public static final int[] SVG_CLOCK_TIME_COLOR_2_RESOURCE = {Color.parseColor("#FFAA55"), Color.parseColor("#FF764F"), Color.parseColor("#FFAB4C"), Color.parseColor("#FFC44E"), Color.parseColor("#FC2A51")};
    public static final int[] SVG_CLOCK_TIME_COLOR_3_RESOURCE = {Color.parseColor("#FFD755"), Color.parseColor("#FFB554"), Color.parseColor("#F2E662"), Color.parseColor("#F8D54B"), Color.parseColor("#FA3D30")};
    public static final int[] SVG_CLOCK_TIME_COLOR_4_RESOURCE = {Color.parseColor("#BCFD9B"), Color.parseColor("#EBFD76"), Color.parseColor("#69FA69"), Color.parseColor("#36FF9B"), Color.parseColor("#FF7700")};
    public static final int[] SVG_CLOCK_TIME_COLOR_5_RESOURCE = {Color.parseColor("#79F88E"), Color.parseColor("#29F5B1"), Color.parseColor("#29EADA"), Color.parseColor("#05DFAF"), Color.parseColor("#EEFF00")};
    public static final int[] SVG_CLOCK_TIME_COLOR_6_RESOURCE = {Color.parseColor("#4CE8E8"), Color.parseColor("#60EEC3"), Color.parseColor("#3BF0E7"), Color.parseColor("#4FDDFD"), Color.parseColor("#FFD154")};
    public static final int[] SVG_CLOCK_TIME_COLOR_7_RESOURCE = {Color.parseColor("#2CB9FF"), Color.parseColor("#4DE1FF"), Color.parseColor("#269AFF"), Color.parseColor("#4EC4FF"), Color.parseColor("#F1FF54")};
    public static final int[] SVG_CLOCK_TIME_COLOR_8_RESOURCE = {Color.parseColor("#4DAFFF"), Color.parseColor("#4D79FF"), Color.parseColor("#3B97FF"), Color.parseColor("#32BBFF"), Color.parseColor("#66FFF2")};
    public static final int[] SVG_CLOCK_TIME_COLOR_9_RESOURCE = {Color.parseColor("#3EA8FF"), Color.parseColor("#5568FF"), Color.parseColor("#5194FF"), Color.parseColor("#7D63FE"), Color.parseColor("#F06CFF")};
    public static final int[] SVG_CLOCK_TIME_COLOR_10_RESOURCE = {Color.parseColor("#B46DFB"), Color.parseColor("#8066FF"), Color.parseColor("#6480FF"), Color.parseColor("#8C57FE"), Color.parseColor("#FF61B0")};
    public static final int[] SVG_CLOCK_TIME_COLOR_11_RESOURCE = {Color.parseColor("#CE5BF8"), Color.parseColor("#E57EFA"), Color.parseColor("#A560FF"), Color.parseColor("#855DFF"), Color.parseColor("#3FA5FF")};
    public static final int[] SVG_CLOCK_TIME_COLOR_12_RESOURCE = {Color.parseColor("#F88BFE"), Color.parseColor("#C756FF"), Color.parseColor("#FD68FD"), Color.parseColor("#FB84D9"), Color.parseColor("#5B3AFE")};
    public static final int[] SVG_CLOCK_TIME_COLOR_13_RESOURCE = {Color.parseColor("#FD69B5"), Color.parseColor("#FC64FC"), Color.parseColor("#FD80C9"), Color.parseColor("#FA7B94"), Color.parseColor("#832BFF")};
    public static final int[] SVG_CLOCK_TIME_COLOR_14_RESOURCE = {Color.parseColor("#F759A0"), Color.parseColor("#FF7587"), Color.parseColor("#FE6296"), Color.parseColor("#F15365"), Color.parseColor("#5E84FF")};
    public static final ArrayList mClockColorChangeListeners = new ArrayList();

    public final int[] getClockTimeColor(Integer num) {
        IntRange intRange = new IntRange(0, 20);
        if (num != null && intRange.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_1_RESOURCE;
        }
        IntRange intRange2 = new IntRange(21, 40);
        if (num != null && intRange2.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_2_RESOURCE;
        }
        IntRange intRange3 = new IntRange(41, 65);
        if (num != null && intRange3.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_3_RESOURCE;
        }
        IntRange intRange4 = new IntRange(66, 100);
        if (num != null && intRange4.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_4_RESOURCE;
        }
        IntRange intRange5 = new IntRange(101, 140);
        if (num != null && intRange5.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_5_RESOURCE;
        }
        IntRange intRange6 = new IntRange(141, 170);
        if (num != null && intRange6.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_6_RESOURCE;
        }
        IntRange intRange7 = new IntRange(171, 200);
        if (num != null && intRange7.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_7_RESOURCE;
        }
        IntRange intRange8 = new IntRange(EaseManager.EaseStyleDef.PERLIN, 220);
        if (num != null && intRange8.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_8_RESOURCE;
        }
        IntRange intRange9 = new IntRange(221, 250);
        if (num != null && intRange9.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_9_RESOURCE;
        }
        IntRange intRange10 = new IntRange(251, 270);
        if (num != null && intRange10.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_10_RESOURCE;
        }
        IntRange intRange11 = new IntRange(271, 290);
        if (num != null && intRange11.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_11_RESOURCE;
        }
        IntRange intRange12 = new IntRange(291, 310);
        if (num != null && intRange12.contains(num.intValue())) {
            return SVG_CLOCK_TIME_COLOR_12_RESOURCE;
        }
        IntRange intRange13 = new IntRange(311, 330);
        if (num == null || !intRange13.contains(num.intValue())) {
            return (num == null || !new IntRange(331, 359).contains(num.intValue())) ? SVG_CLOCK_TIME_COLOR_DEFAULT_RESOURCE : SVG_CLOCK_TIME_COLOR_14_RESOURCE;
        }
        return SVG_CLOCK_TIME_COLOR_13_RESOURCE;
    }

    public final Integer getWallpaperHue() {
        if (Utils.isAodProcess()) {
            Map map = AODApplicationDelegate.mFullImageRect;
            if (map != null) {
                return (Integer) map.get("image-hue");
            }
            return null;
        }
        Map map2 = AODApplicationDelegate.mRealFullImageMap;
        if (map2 != null) {
            return (Integer) map2.get("image-hue");
        }
        return null;
    }

    public final void notifyClockColorChange() {
        Iterator it = mClockColorChangeListeners.iterator();
        while (it.hasNext()) {
            ((OnClockColorChangeListener) it.next()).updateClockColor(-1);
        }
    }

    public final void registerClockColorChangeListener(OnClockColorChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        mClockColorChangeListeners.add(listener);
    }

    public final void unregisterClockColorChangeListener(OnClockColorChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        mClockColorChangeListeners.remove(listener);
    }
}

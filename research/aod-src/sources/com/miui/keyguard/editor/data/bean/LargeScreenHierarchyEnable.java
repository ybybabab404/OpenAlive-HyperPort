package com.miui.keyguard.editor.data.bean;

import com.miui.keyguard.editor.data.preset.FontFilterKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class LargeScreenHierarchyEnable {
    private boolean largeScreenLandHierarchyEnable;
    private boolean largeScreenLandHierarchyEnableInDualClock;
    private boolean largeScreenLandWithNotificationHierarchyEnable;
    private boolean largeScreenPortHierarchyEnable;
    private boolean largeScreenPortHierarchyEnableInDualClock;
    private boolean largeScreenPortWithNotificationHierarchyEnable;
    private boolean smallScreenHierarchyEnable;
    private boolean smallScreenHierarchyEnableInDualClock;
    private boolean smallScreenWithNotificationHierarchyEnable;

    public LargeScreenHierarchyEnable() {
        this.smallScreenHierarchyEnable = true;
        this.smallScreenHierarchyEnableInDualClock = false;
        this.smallScreenWithNotificationHierarchyEnable = true;
        this.largeScreenPortHierarchyEnable = true;
        this.largeScreenPortHierarchyEnableInDualClock = false;
        this.largeScreenPortWithNotificationHierarchyEnable = true;
        this.largeScreenLandHierarchyEnable = true;
        this.largeScreenLandHierarchyEnableInDualClock = false;
        this.largeScreenLandWithNotificationHierarchyEnable = true;
    }

    public LargeScreenHierarchyEnable(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9) {
        this.smallScreenHierarchyEnable = z;
        this.smallScreenHierarchyEnableInDualClock = z2;
        this.smallScreenWithNotificationHierarchyEnable = z3;
        this.largeScreenPortHierarchyEnable = z4;
        this.largeScreenPortHierarchyEnableInDualClock = z5;
        this.largeScreenPortWithNotificationHierarchyEnable = z6;
        this.largeScreenLandHierarchyEnable = z7;
        this.largeScreenLandHierarchyEnableInDualClock = z8;
        this.largeScreenLandWithNotificationHierarchyEnable = z9;
    }

    @NotNull
    public final LargeScreenHierarchyEnable copy() {
        return new LargeScreenHierarchyEnable(this.smallScreenHierarchyEnable, this.smallScreenHierarchyEnableInDualClock, this.smallScreenWithNotificationHierarchyEnable, this.largeScreenPortHierarchyEnable, this.largeScreenPortHierarchyEnableInDualClock, this.largeScreenPortWithNotificationHierarchyEnable, this.largeScreenLandHierarchyEnable, this.largeScreenLandHierarchyEnableInDualClock, this.largeScreenLandWithNotificationHierarchyEnable);
    }

    public final void enforceNotificationHierarchyByDevice(boolean z) {
        if (!z || FontFilterKt.getSUPPORT_LOCKSCREEN_VARIABLE_FONT()) {
            return;
        }
        this.smallScreenWithNotificationHierarchyEnable = false;
        this.largeScreenPortWithNotificationHierarchyEnable = false;
        this.largeScreenLandWithNotificationHierarchyEnable = false;
    }

    public final boolean getCurrentScreenHierarchy(int i, boolean z) {
        if (i == 2) {
            return z ? this.largeScreenPortHierarchyEnableInDualClock : this.largeScreenPortHierarchyEnable;
        }
        if (i != 3) {
            return z ? this.smallScreenHierarchyEnableInDualClock : this.smallScreenHierarchyEnable;
        }
        return z ? this.largeScreenLandHierarchyEnableInDualClock : this.largeScreenLandHierarchyEnable;
    }

    public final boolean getLargeScreenLandHierarchyEnable() {
        return this.largeScreenLandHierarchyEnable;
    }

    public final boolean getLargeScreenLandHierarchyEnableInDualClock() {
        return this.largeScreenLandHierarchyEnableInDualClock;
    }

    public final boolean getLargeScreenLandWithNotificationHierarchyEnable() {
        return this.largeScreenLandWithNotificationHierarchyEnable;
    }

    public final boolean getLargeScreenPortHierarchyEnable() {
        return this.largeScreenPortHierarchyEnable;
    }

    public final boolean getLargeScreenPortHierarchyEnableInDualClock() {
        return this.largeScreenPortHierarchyEnableInDualClock;
    }

    public final boolean getLargeScreenPortWithNotificationHierarchyEnable() {
        return this.largeScreenPortWithNotificationHierarchyEnable;
    }

    public final boolean getSmallScreenHierarchyEnable() {
        return this.smallScreenHierarchyEnable;
    }

    public final boolean getSmallScreenHierarchyEnableInDualClock() {
        return this.smallScreenHierarchyEnableInDualClock;
    }

    public final boolean getSmallScreenWithNotificationHierarchyEnable() {
        return this.smallScreenWithNotificationHierarchyEnable;
    }

    public final void setLargeScreenLandHierarchyEnable(boolean z) {
        this.largeScreenLandHierarchyEnable = z;
    }

    public final void setLargeScreenLandHierarchyEnableInDualClock(boolean z) {
        this.largeScreenLandHierarchyEnableInDualClock = z;
    }

    public final void setLargeScreenLandWithNotificationHierarchyEnable(boolean z) {
        this.largeScreenLandWithNotificationHierarchyEnable = z;
    }

    public final void setLargeScreenPortHierarchyEnable(boolean z) {
        this.largeScreenPortHierarchyEnable = z;
    }

    public final void setLargeScreenPortHierarchyEnableInDualClock(boolean z) {
        this.largeScreenPortHierarchyEnableInDualClock = z;
    }

    public final void setLargeScreenPortWithNotificationHierarchyEnable(boolean z) {
        this.largeScreenPortWithNotificationHierarchyEnable = z;
    }

    public final void setSmallScreenHierarchyEnable(boolean z) {
        this.smallScreenHierarchyEnable = z;
    }

    public final void setSmallScreenHierarchyEnableInDualClock(boolean z) {
        this.smallScreenHierarchyEnableInDualClock = z;
    }

    public final void setSmallScreenWithNotificationHierarchyEnable(boolean z) {
        this.smallScreenWithNotificationHierarchyEnable = z;
    }

    public final void updateFrom(@NotNull Map<String, Boolean> newResult) {
        Intrinsics.checkNotNullParameter(newResult, "newResult");
        Boolean bool = newResult.get("hierarchy_check_small_screen");
        if (bool != null) {
            this.smallScreenHierarchyEnable = bool.booleanValue();
        }
        Boolean bool2 = newResult.get("hierarchy_check_small_screen_dual_clock");
        if (bool2 != null) {
            this.smallScreenHierarchyEnableInDualClock = bool2.booleanValue();
        }
        Boolean bool3 = newResult.get("hierarchy_check_small_screen_with_notification");
        if (bool3 != null) {
            this.smallScreenWithNotificationHierarchyEnable = bool3.booleanValue();
        }
        Boolean bool4 = newResult.get("hierarchy_check_large_screen_port");
        if (bool4 != null) {
            this.largeScreenPortHierarchyEnable = bool4.booleanValue();
        }
        Boolean bool5 = newResult.get("hierarchy_check_large_screen_port_dual_clock");
        if (bool5 != null) {
            this.largeScreenPortHierarchyEnableInDualClock = bool5.booleanValue();
        }
        Boolean bool6 = newResult.get("hierarchy_check_large_screen_port_with_notification");
        if (bool6 != null) {
            this.largeScreenPortWithNotificationHierarchyEnable = bool6.booleanValue();
        }
        Boolean bool7 = newResult.get("hierarchy_check_large_screen_land");
        if (bool7 != null) {
            this.largeScreenLandHierarchyEnable = bool7.booleanValue();
        }
        Boolean bool8 = newResult.get("hierarchy_check_large_screen_land_dual_clock");
        if (bool8 != null) {
            this.largeScreenLandHierarchyEnableInDualClock = bool8.booleanValue();
        }
        Boolean bool9 = newResult.get("hierarchy_check_large_screen_land_with_notification");
        if (bool9 != null) {
            this.largeScreenLandWithNotificationHierarchyEnable = bool9.booleanValue();
        }
    }
}

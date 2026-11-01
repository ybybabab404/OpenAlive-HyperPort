package com.miui.keyguard.editor.data.template;

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
/* JADX INFO: compiled from: MatchManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SystemParams {
    public final String deviceCodename;
    public final int deviceType;
    public final boolean isDefaultSensorWallpaper;
    public final int osVersion;
    public final float screenDensity;
    public final int screenHeight;
    public final int screenWidth;

    public SystemParams(int i, int i2, int i3, int i4, float f, boolean z, String deviceCodename) {
        Intrinsics.checkNotNullParameter(deviceCodename, "deviceCodename");
        this.deviceType = i;
        this.osVersion = i2;
        this.screenWidth = i3;
        this.screenHeight = i4;
        this.screenDensity = f;
        this.isDefaultSensorWallpaper = z;
        this.deviceCodename = deviceCodename;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SystemParams)) {
            return false;
        }
        SystemParams systemParams = (SystemParams) obj;
        return this.deviceType == systemParams.deviceType && this.osVersion == systemParams.osVersion && this.screenWidth == systemParams.screenWidth && this.screenHeight == systemParams.screenHeight && Float.compare(this.screenDensity, systemParams.screenDensity) == 0 && this.isDefaultSensorWallpaper == systemParams.isDefaultSensorWallpaper && Intrinsics.areEqual(this.deviceCodename, systemParams.deviceCodename);
    }

    public final String getDeviceCodename() {
        return this.deviceCodename;
    }

    public final int getDeviceType() {
        return this.deviceType;
    }

    public final int getOsVersion() {
        return this.osVersion;
    }

    public final float getScreenDensity() {
        return this.screenDensity;
    }

    public final int getScreenHeight() {
        return this.screenHeight;
    }

    public final int getScreenWidth() {
        return this.screenWidth;
    }

    public int hashCode() {
        return (((((((((((Integer.hashCode(this.deviceType) * 31) + Integer.hashCode(this.osVersion)) * 31) + Integer.hashCode(this.screenWidth)) * 31) + Integer.hashCode(this.screenHeight)) * 31) + Float.hashCode(this.screenDensity)) * 31) + Boolean.hashCode(this.isDefaultSensorWallpaper)) * 31) + this.deviceCodename.hashCode();
    }

    public final boolean isDefaultSensorWallpaper() {
        return this.isDefaultSensorWallpaper;
    }

    public String toString() {
        return "SystemParams(deviceType=" + this.deviceType + ", osVersion=" + this.osVersion + ", screenWidth=" + this.screenWidth + ", screenHeight=" + this.screenHeight + ", screenDensity=" + this.screenDensity + ", isDefaultSensorWallpaper=" + this.isDefaultSensorWallpaper + ", deviceCodename=" + this.deviceCodename + ")";
    }
}

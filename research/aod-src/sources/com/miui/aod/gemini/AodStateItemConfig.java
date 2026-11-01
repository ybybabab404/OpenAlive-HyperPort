package com.miui.aod.gemini;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AodConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AodStateItemConfig {
    private final boolean enabled;

    @NotNull
    private final String hintText;

    @NotNull
    private final String screenKey;

    @NotNull
    private final String settingKey;

    public AodStateItemConfig(boolean z, @NotNull String settingKey, @NotNull String screenKey, @NotNull String hintText) {
        Intrinsics.checkNotNullParameter(settingKey, "settingKey");
        Intrinsics.checkNotNullParameter(screenKey, "screenKey");
        Intrinsics.checkNotNullParameter(hintText, "hintText");
        this.enabled = z;
        this.settingKey = settingKey;
        this.screenKey = screenKey;
        this.hintText = hintText;
    }

    public /* synthetic */ AodStateItemConfig(boolean z, String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, str, str2, (i & 8) != 0 ? "" : str3);
    }

    public static /* synthetic */ AodStateItemConfig copy$default(AodStateItemConfig aodStateItemConfig, boolean z, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            z = aodStateItemConfig.enabled;
        }
        if ((i & 2) != 0) {
            str = aodStateItemConfig.settingKey;
        }
        if ((i & 4) != 0) {
            str2 = aodStateItemConfig.screenKey;
        }
        if ((i & 8) != 0) {
            str3 = aodStateItemConfig.hintText;
        }
        return aodStateItemConfig.copy(z, str, str2, str3);
    }

    public final boolean component1() {
        return this.enabled;
    }

    @NotNull
    public final String component2() {
        return this.settingKey;
    }

    @NotNull
    public final String component3() {
        return this.screenKey;
    }

    @NotNull
    public final String component4() {
        return this.hintText;
    }

    @NotNull
    public final AodStateItemConfig copy(boolean z, @NotNull String settingKey, @NotNull String screenKey, @NotNull String hintText) {
        Intrinsics.checkNotNullParameter(settingKey, "settingKey");
        Intrinsics.checkNotNullParameter(screenKey, "screenKey");
        Intrinsics.checkNotNullParameter(hintText, "hintText");
        return new AodStateItemConfig(z, settingKey, screenKey, hintText);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AodStateItemConfig)) {
            return false;
        }
        AodStateItemConfig aodStateItemConfig = (AodStateItemConfig) obj;
        return this.enabled == aodStateItemConfig.enabled && Intrinsics.areEqual(this.settingKey, aodStateItemConfig.settingKey) && Intrinsics.areEqual(this.screenKey, aodStateItemConfig.screenKey) && Intrinsics.areEqual(this.hintText, aodStateItemConfig.hintText);
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    @NotNull
    public final String getHintText() {
        return this.hintText;
    }

    @NotNull
    public final String getScreenKey() {
        return this.screenKey;
    }

    @NotNull
    public final String getSettingKey() {
        return this.settingKey;
    }

    public int hashCode() {
        return (((((Boolean.hashCode(this.enabled) * 31) + this.settingKey.hashCode()) * 31) + this.screenKey.hashCode()) * 31) + this.hintText.hashCode();
    }

    @NotNull
    public String toString() {
        return "AodStateItemConfig(enabled=" + this.enabled + ", settingKey=" + this.settingKey + ", screenKey=" + this.screenKey + ", hintText=" + this.hintText + ")";
    }
}

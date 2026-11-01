package com.miui.aod.gemini;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
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
public final class PerAodScreenConfig {

    @NotNull
    private final Set<AodStateCategory> category;
    private final boolean enabled;

    @NotNull
    private final String screenKey;

    /* JADX WARN: Multi-variable type inference failed */
    public PerAodScreenConfig(boolean z, @NotNull String screenKey, @NotNull Set<? extends AodStateCategory> category) {
        Intrinsics.checkNotNullParameter(screenKey, "screenKey");
        Intrinsics.checkNotNullParameter(category, "category");
        this.enabled = z;
        this.screenKey = screenKey;
        this.category = category;
    }

    public /* synthetic */ PerAodScreenConfig(boolean z, String str, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, str, (i & 4) != 0 ? SetsKt.setOf(AodStateCategory.UNCATEGORIZED) : set);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PerAodScreenConfig copy$default(PerAodScreenConfig perAodScreenConfig, boolean z, String str, Set set, int i, Object obj) {
        if ((i & 1) != 0) {
            z = perAodScreenConfig.enabled;
        }
        if ((i & 2) != 0) {
            str = perAodScreenConfig.screenKey;
        }
        if ((i & 4) != 0) {
            set = perAodScreenConfig.category;
        }
        return perAodScreenConfig.copy(z, str, set);
    }

    public final boolean component1() {
        return this.enabled;
    }

    @NotNull
    public final String component2() {
        return this.screenKey;
    }

    @NotNull
    public final Set<AodStateCategory> component3() {
        return this.category;
    }

    @NotNull
    public final PerAodScreenConfig copy(boolean z, @NotNull String screenKey, @NotNull Set<? extends AodStateCategory> category) {
        Intrinsics.checkNotNullParameter(screenKey, "screenKey");
        Intrinsics.checkNotNullParameter(category, "category");
        return new PerAodScreenConfig(z, screenKey, category);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PerAodScreenConfig)) {
            return false;
        }
        PerAodScreenConfig perAodScreenConfig = (PerAodScreenConfig) obj;
        return this.enabled == perAodScreenConfig.enabled && Intrinsics.areEqual(this.screenKey, perAodScreenConfig.screenKey) && Intrinsics.areEqual(this.category, perAodScreenConfig.category);
    }

    @NotNull
    public final Set<AodStateCategory> getCategory() {
        return this.category;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    @NotNull
    public final String getScreenKey() {
        return this.screenKey;
    }

    public int hashCode() {
        return (((Boolean.hashCode(this.enabled) * 31) + this.screenKey.hashCode()) * 31) + this.category.hashCode();
    }

    @NotNull
    public String toString() {
        return "PerAodScreenConfig(enabled=" + this.enabled + ", screenKey=" + this.screenKey + ", category=" + this.category + ")";
    }
}

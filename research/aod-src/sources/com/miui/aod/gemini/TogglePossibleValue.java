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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AodConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class TogglePossibleValue {

    @NotNull
    private final ToggleDetail toggle;

    /* JADX INFO: compiled from: AodConfig.kt */
    @Metadata
    public static final class ToggleDetail {

        @NotNull
        private final String valueType;

        /* JADX WARN: Multi-variable type inference failed */
        public ToggleDetail() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public ToggleDetail(@NotNull String valueType) {
            Intrinsics.checkNotNullParameter(valueType, "valueType");
            this.valueType = valueType;
        }

        public /* synthetic */ ToggleDetail(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? "BOOLEAN" : str);
        }

        public static /* synthetic */ ToggleDetail copy$default(ToggleDetail toggleDetail, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = toggleDetail.valueType;
            }
            return toggleDetail.copy(str);
        }

        @NotNull
        public final String component1() {
            return this.valueType;
        }

        @NotNull
        public final ToggleDetail copy(@NotNull String valueType) {
            Intrinsics.checkNotNullParameter(valueType, "valueType");
            return new ToggleDetail(valueType);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ToggleDetail) && Intrinsics.areEqual(this.valueType, ((ToggleDetail) obj).valueType);
        }

        @NotNull
        public final String getValueType() {
            return this.valueType;
        }

        public int hashCode() {
            return this.valueType.hashCode();
        }

        @NotNull
        public String toString() {
            return "ToggleDetail(valueType=" + this.valueType + ")";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TogglePossibleValue() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public TogglePossibleValue(@NotNull ToggleDetail toggle) {
        Intrinsics.checkNotNullParameter(toggle, "toggle");
        this.toggle = toggle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ TogglePossibleValue(ToggleDetail toggleDetail, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ToggleDetail(null, 1, 0 == true ? 1 : 0) : toggleDetail);
    }

    public static /* synthetic */ TogglePossibleValue copy$default(TogglePossibleValue togglePossibleValue, ToggleDetail toggleDetail, int i, Object obj) {
        if ((i & 1) != 0) {
            toggleDetail = togglePossibleValue.toggle;
        }
        return togglePossibleValue.copy(toggleDetail);
    }

    @NotNull
    public final ToggleDetail component1() {
        return this.toggle;
    }

    @NotNull
    public final TogglePossibleValue copy(@NotNull ToggleDetail toggle) {
        Intrinsics.checkNotNullParameter(toggle, "toggle");
        return new TogglePossibleValue(toggle);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TogglePossibleValue) && Intrinsics.areEqual(this.toggle, ((TogglePossibleValue) obj).toggle);
    }

    @NotNull
    public final ToggleDetail getToggle() {
        return this.toggle;
    }

    public int hashCode() {
        return this.toggle.hashCode();
    }

    @NotNull
    public String toString() {
        return "TogglePossibleValue(toggle=" + this.toggle + ")";
    }
}

package com.miui.aod.gemini;

import java.util.List;
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
public final class PredeterminedFinitePossibleValue {

    @NotNull
    private final PredeterminedFiniteDetail predeterminedFinite;

    /* JADX INFO: compiled from: AodConfig.kt */
    @Metadata
    public static final class PredeterminedFiniteDetail {

        @NotNull
        private final String valueType;

        @NotNull
        private final List<String> values;

        public PredeterminedFiniteDetail(@NotNull String valueType, @NotNull List<String> values) {
            Intrinsics.checkNotNullParameter(valueType, "valueType");
            Intrinsics.checkNotNullParameter(values, "values");
            this.valueType = valueType;
            this.values = values;
        }

        public /* synthetic */ PredeterminedFiniteDetail(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? "STRING" : str, list);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ PredeterminedFiniteDetail copy$default(PredeterminedFiniteDetail predeterminedFiniteDetail, String str, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                str = predeterminedFiniteDetail.valueType;
            }
            if ((i & 2) != 0) {
                list = predeterminedFiniteDetail.values;
            }
            return predeterminedFiniteDetail.copy(str, list);
        }

        @NotNull
        public final String component1() {
            return this.valueType;
        }

        @NotNull
        public final List<String> component2() {
            return this.values;
        }

        @NotNull
        public final PredeterminedFiniteDetail copy(@NotNull String valueType, @NotNull List<String> values) {
            Intrinsics.checkNotNullParameter(valueType, "valueType");
            Intrinsics.checkNotNullParameter(values, "values");
            return new PredeterminedFiniteDetail(valueType, values);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PredeterminedFiniteDetail)) {
                return false;
            }
            PredeterminedFiniteDetail predeterminedFiniteDetail = (PredeterminedFiniteDetail) obj;
            return Intrinsics.areEqual(this.valueType, predeterminedFiniteDetail.valueType) && Intrinsics.areEqual(this.values, predeterminedFiniteDetail.values);
        }

        @NotNull
        public final String getValueType() {
            return this.valueType;
        }

        @NotNull
        public final List<String> getValues() {
            return this.values;
        }

        public int hashCode() {
            return (this.valueType.hashCode() * 31) + this.values.hashCode();
        }

        @NotNull
        public String toString() {
            return "PredeterminedFiniteDetail(valueType=" + this.valueType + ", values=" + this.values + ")";
        }
    }

    public PredeterminedFinitePossibleValue(@NotNull PredeterminedFiniteDetail predeterminedFinite) {
        Intrinsics.checkNotNullParameter(predeterminedFinite, "predeterminedFinite");
        this.predeterminedFinite = predeterminedFinite;
    }

    public static /* synthetic */ PredeterminedFinitePossibleValue copy$default(PredeterminedFinitePossibleValue predeterminedFinitePossibleValue, PredeterminedFiniteDetail predeterminedFiniteDetail, int i, Object obj) {
        if ((i & 1) != 0) {
            predeterminedFiniteDetail = predeterminedFinitePossibleValue.predeterminedFinite;
        }
        return predeterminedFinitePossibleValue.copy(predeterminedFiniteDetail);
    }

    @NotNull
    public final PredeterminedFiniteDetail component1() {
        return this.predeterminedFinite;
    }

    @NotNull
    public final PredeterminedFinitePossibleValue copy(@NotNull PredeterminedFiniteDetail predeterminedFinite) {
        Intrinsics.checkNotNullParameter(predeterminedFinite, "predeterminedFinite");
        return new PredeterminedFinitePossibleValue(predeterminedFinite);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PredeterminedFinitePossibleValue) && Intrinsics.areEqual(this.predeterminedFinite, ((PredeterminedFinitePossibleValue) obj).predeterminedFinite);
    }

    @NotNull
    public final PredeterminedFiniteDetail getPredeterminedFinite() {
        return this.predeterminedFinite;
    }

    public int hashCode() {
        return this.predeterminedFinite.hashCode();
    }

    @NotNull
    public String toString() {
        return "PredeterminedFinitePossibleValue(predeterminedFinite=" + this.predeterminedFinite + ")";
    }
}

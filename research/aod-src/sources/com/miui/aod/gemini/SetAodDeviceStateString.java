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
    */
/* JADX INFO: compiled from: AodConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class SetAodDeviceStateString extends AodPossibleValue {

    @NotNull
    private final List<Object> options;

    @NotNull
    private final String valueType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SetAodDeviceStateString(@NotNull List<? extends Object> options, @NotNull String valueType) {
        super(null);
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(valueType, "valueType");
        this.options = options;
        this.valueType = valueType;
    }

    public /* synthetic */ SetAodDeviceStateString(List list, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? "STRING" : str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SetAodDeviceStateString copy$default(SetAodDeviceStateString setAodDeviceStateString, List list, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            list = setAodDeviceStateString.options;
        }
        if ((i & 2) != 0) {
            str = setAodDeviceStateString.valueType;
        }
        return setAodDeviceStateString.copy(list, str);
    }

    @NotNull
    public final List<Object> component1() {
        return this.options;
    }

    @NotNull
    public final String component2() {
        return this.valueType;
    }

    @NotNull
    public final SetAodDeviceStateString copy(@NotNull List<? extends Object> options, @NotNull String valueType) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(valueType, "valueType");
        return new SetAodDeviceStateString(options, valueType);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SetAodDeviceStateString)) {
            return false;
        }
        SetAodDeviceStateString setAodDeviceStateString = (SetAodDeviceStateString) obj;
        return Intrinsics.areEqual(this.options, setAodDeviceStateString.options) && Intrinsics.areEqual(this.valueType, setAodDeviceStateString.valueType);
    }

    @NotNull
    public final List<Object> getOptions() {
        return this.options;
    }

    @Override // com.miui.aod.gemini.AodPossibleValue
    @NotNull
    public String getValueType() {
        return this.valueType;
    }

    public int hashCode() {
        return (this.options.hashCode() * 31) + this.valueType.hashCode();
    }

    @NotNull
    public String toString() {
        return "SetAodDeviceStateString(options=" + this.options + ", valueType=" + this.valueType + ")";
    }
}

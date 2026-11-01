package com.miui.aod.gemini;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
public enum AodStateCategory {
    UNCATEGORIZED("getUncategorizedDeviceState");


    @NotNull
    public static final String TAG = "DeviceStateCategory";

    @NotNull
    private final String functionId;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    @NotNull
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: AodConfig.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final AodStateCategory fromId(@NotNull String functionId) {
            AodStateCategory next;
            Intrinsics.checkNotNullParameter(functionId, "functionId");
            Iterator<AodStateCategory> it = AodStateCategory.getEntries().iterator();
            while (it.hasNext()) {
                next = it.next();
                if (Intrinsics.areEqual(next.getFunctionId(), functionId)) {
                    return next;
                }
            }
            next = null;
            return next;
        }
    }

    AodStateCategory(String str) {
        this.functionId = str;
    }

    @NotNull
    public static EnumEntries<AodStateCategory> getEntries() {
        return $ENTRIES;
    }

    @NotNull
    public final String getFunctionId() {
        return this.functionId;
    }
}

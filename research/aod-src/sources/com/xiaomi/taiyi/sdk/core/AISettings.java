package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.core.track.TrackSettings;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AISettings {
    public final int[] a;
    public final boolean b;
    public final boolean c;
    public final TrackSettings d;

    public AISettings(int[] memoryPool, boolean z, boolean z2, TrackSettings trackSettings) {
        Intrinsics.checkNotNullParameter(memoryPool, "memoryPool");
        Intrinsics.checkNotNullParameter(trackSettings, "trackSettings");
        this.a = memoryPool;
        this.b = z;
        this.c = z2;
        this.d = trackSettings;
    }

    public /* synthetic */ AISettings(int[] iArr, boolean z, boolean z2, TrackSettings trackSettings, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new int[0] : iArr, (i & 2) != 0 ? false : z, (i & 4) != 0 ? true : z2, (i & 8) != 0 ? new TrackSettings(0, 0, 0, 7, null) : trackSettings);
    }

    public final boolean getEnableTrack() {
        return this.c;
    }

    public final int[] getMemoryPool() {
        return this.a;
    }

    public final boolean getRunInternal() {
        return this.b;
    }

    public final TrackSettings getTrackSettings() {
        return this.d;
    }
}

package com.android.thememanager.util;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Keep;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: AodTrackMapData.kt */
/* JADX INFO: loaded from: classes.dex */
@Keep
@Metadata
public final class AodTrackMapData implements Parcelable {

    @NotNull
    public static final CREATOR CREATOR = new CREATOR(null);

    @NotNull
    private final Map<String, Object> trackMap;

    /* JADX INFO: compiled from: AodTrackMapData.kt */
    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AodTrackMapData> {
        public CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AodTrackMapData createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AodTrackMapData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AodTrackMapData[] newArray(int i) {
            return new AodTrackMapData[i];
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AodTrackMapData(@NotNull Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        HashMap hashMap = parcel.readHashMap(ClassLoader.getSystemClassLoader());
        Intrinsics.checkNotNull(hashMap, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.Any>");
        this(hashMap);
    }

    public AodTrackMapData(@NotNull Map<String, ? extends Object> trackMap) {
        Intrinsics.checkNotNullParameter(trackMap, "trackMap");
        this.trackMap = trackMap;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @NotNull
    public final Map<String, Object> getTrackMap() {
        return this.trackMap;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        Map<String, Object> map = this.trackMap;
        Intrinsics.checkNotNull(map, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        parcel.writeMap(map);
    }
}

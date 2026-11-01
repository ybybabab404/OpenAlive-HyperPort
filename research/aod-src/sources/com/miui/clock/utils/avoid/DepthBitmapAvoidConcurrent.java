package com.miui.clock.utils.avoid;

import android.content.Context;
import android.graphics.Bitmap;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: DepthBitmapAvoidConcurrent.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class DepthBitmapAvoidConcurrent {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG_LOG = DepthAvoidEvaluatorDebug.DEBUG_LOG;
    public final Context appContext;
    public volatile WeakReference avoidRectProviderRef;
    public volatile Config config;

    /* JADX INFO: compiled from: DepthBitmapAvoidConcurrent.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: DepthBitmapAvoidConcurrent.kt */
    @Metadata
    public static final class Config {
        public final Bitmap depthBitmap;
        public final Boolean isDepthVideo;
        public final Boolean supportWideColor;

        public Config(Bitmap bitmap, Boolean bool, Boolean bool2) {
            this.depthBitmap = bitmap;
            this.isDepthVideo = bool;
            this.supportWideColor = bool2;
        }

        public /* synthetic */ Config(Bitmap bitmap, Boolean bool, Boolean bool2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : bitmap, (i & 2) != 0 ? null : bool, (i & 4) != 0 ? null : bool2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Config)) {
                return false;
            }
            Config config = (Config) obj;
            return Intrinsics.areEqual(this.depthBitmap, config.depthBitmap) && Intrinsics.areEqual(this.isDepthVideo, config.isDepthVideo) && Intrinsics.areEqual(this.supportWideColor, config.supportWideColor);
        }

        public int hashCode() {
            Bitmap bitmap = this.depthBitmap;
            int iHashCode = (bitmap == null ? 0 : bitmap.hashCode()) * 31;
            Boolean bool = this.isDepthVideo;
            int iHashCode2 = (iHashCode + (bool == null ? 0 : bool.hashCode())) * 31;
            Boolean bool2 = this.supportWideColor;
            return iHashCode2 + (bool2 != null ? bool2.hashCode() : 0);
        }

        public String toString() {
            return "Config(depthBitmap=" + this.depthBitmap + ", isDepthVideo=" + this.isDepthVideo + ", supportWideColor=" + this.supportWideColor + ')';
        }
    }

    /* JADX INFO: compiled from: DepthBitmapAvoidConcurrent.kt */
    @Metadata
    public interface OnReadyListener {
    }

    public DepthBitmapAvoidConcurrent(Context context, DepthAvoidRectProvider avoidRectProvider) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(avoidRectProvider, "avoidRectProvider");
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext != null ? applicationContext : context;
        this.config = new Config(null, null, null, 7, null);
        this.avoidRectProviderRef = new WeakReference(avoidRectProvider);
    }
}

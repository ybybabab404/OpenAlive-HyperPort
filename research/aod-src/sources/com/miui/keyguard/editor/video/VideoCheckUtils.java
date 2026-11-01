package com.miui.keyguard.editor.video;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

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
/* JADX INFO: compiled from: VideoCheckUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class VideoCheckUtils {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: VideoCheckUtils.kt */
    @Metadata
    public static abstract class CheckVideoResult {

        /* JADX INFO: compiled from: VideoCheckUtils.kt */
        @Metadata
        public static final class Compression extends CheckVideoResult {
            public static final Compression INSTANCE = new Compression();

            public Compression() {
                super(null);
            }

            public boolean equals(Object obj) {
                return this == obj || (obj instanceof Compression);
            }

            public int hashCode() {
                return 1987181487;
            }

            public String toString() {
                return ExifInterface.TAG_COMPRESSION;
            }
        }

        /* JADX INFO: compiled from: VideoCheckUtils.kt */
        @Metadata
        public static final class DurationCrop extends CheckVideoResult {
            public static final DurationCrop INSTANCE = new DurationCrop();

            public DurationCrop() {
                super(null);
            }

            public boolean equals(Object obj) {
                return this == obj || (obj instanceof DurationCrop);
            }

            public int hashCode() {
                return -1904723685;
            }

            public String toString() {
                return "DurationCrop";
            }
        }

        /* JADX INFO: compiled from: VideoCheckUtils.kt */
        @Metadata
        public static final class Invalid extends CheckVideoResult {
            public final int code;
            public final String msg;

            public Invalid(int i, String str) {
                super(null);
                this.code = i;
                this.msg = str;
            }

            public /* synthetic */ Invalid(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
                this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? "" : str);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Invalid)) {
                    return false;
                }
                Invalid invalid = (Invalid) obj;
                return this.code == invalid.code && Intrinsics.areEqual(this.msg, invalid.msg);
            }

            public final int getCode() {
                return this.code;
            }

            public int hashCode() {
                int iHashCode = Integer.hashCode(this.code) * 31;
                String str = this.msg;
                return iHashCode + (str == null ? 0 : str.hashCode());
            }

            public String toString() {
                return "Invalid(code=" + this.code + ", msg=" + this.msg + ")";
            }
        }

        /* JADX INFO: compiled from: VideoCheckUtils.kt */
        @Metadata
        public static final class Success extends CheckVideoResult {
            public static final Success INSTANCE = new Success();

            public Success() {
                super(null);
            }

            public boolean equals(Object obj) {
                return this == obj || (obj instanceof Success);
            }

            public int hashCode() {
                return 897806028;
            }

            public String toString() {
                return "Success";
            }
        }

        public CheckVideoResult() {
        }

        public /* synthetic */ CheckVideoResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: VideoCheckUtils.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final long getVideoDuration(MediaFormat format) {
            Intrinsics.checkNotNullParameter(format, "format");
            long j = format.getLong("durationUs", -1L);
            if (j != -1) {
                return j;
            }
            Log.e("VideoCheckUtils", "getVideoDuration: get video duration error");
            return -1L;
        }

        public final MediaFormat getVideoFormat(MediaExtractor extractor) {
            Intrinsics.checkNotNullParameter(extractor, "extractor");
            int trackCount = extractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = extractor.getTrackFormat(i);
                String string = trackFormat.getString("mime");
                if (string != null && StringsKt.startsWith$default(string, "video/", false, 2, null)) {
                    return trackFormat;
                }
            }
            return null;
        }

        public final int getVideoFrameRate(MediaFormat format) {
            Intrinsics.checkNotNullParameter(format, "format");
            int integer = format.getInteger("frame-rate", -1);
            if (integer == -1) {
                Log.e("VideoCheckUtils", "getVideoFrameRate: get video frame rate error");
            }
            return integer;
        }

        public final int getVideoRotation(MediaFormat format) {
            Intrinsics.checkNotNullParameter(format, "format");
            int integer = format.getInteger("rotation-degrees", -1);
            if (integer == -1) {
                Log.e("VideoCheckUtils", "getVideoRotation: get video rotation error");
                return 0;
            }
            Log.d("VideoCheckUtils", "getVideoRotation: rotation = " + integer);
            return integer;
        }

        public final Pair getVideoSize(MediaFormat format) {
            Intrinsics.checkNotNullParameter(format, "format");
            return new Pair(Integer.valueOf(format.getInteger("width", -1)), Integer.valueOf(format.getInteger("height", -1)));
        }

        public final boolean isAtLeast4KVideo(int i, int i2) {
            return i2 > 10000 || i > 10000 || i2 * i >= 8294400;
        }

        public final boolean isHorizontalVideo(MediaFormat format) {
            Intrinsics.checkNotNullParameter(format, "format");
            int videoRotation = getVideoRotation(format);
            Pair videoSize = getVideoSize(format);
            Log.i("VideoCheckUtils", "videoRotation = " + videoRotation + " , size = " + videoSize);
            int iIntValue = ((Number) videoSize.getFirst()).intValue();
            int iIntValue2 = ((Number) videoSize.getSecond()).intValue();
            if (iIntValue > iIntValue2 && videoRotation != 90 && videoRotation != 270) {
                return true;
            }
            if (iIntValue < iIntValue2) {
                return videoRotation == 90 || videoRotation == 270;
            }
            return false;
        }

        public final boolean isLessThan2KVideo(int i, int i2) {
            return i2 <= 10000 && i <= 10000 && i2 * i <= 3276800;
        }

        public final boolean isVideoCodecSupported(String str) {
            if (str == null || str.length() == 0) {
                return false;
            }
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                try {
                    mediaExtractor.setDataSource(str);
                    MediaFormat videoFormat = getVideoFormat(mediaExtractor);
                    if (videoFormat == null) {
                        return false;
                    }
                    String string = videoFormat.getString("mime");
                    return Intrinsics.areEqual(string, "video/avc") || Intrinsics.areEqual(string, "video/hevc");
                } catch (Exception e) {
                    Log.e("VideoCheckUtils", "isVideoCodecSupported: " + e);
                    return false;
                }
            } finally {
                mediaExtractor.release();
            }
        }
    }
}

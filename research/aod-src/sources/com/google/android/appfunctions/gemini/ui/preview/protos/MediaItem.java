package com.google.android.appfunctions.gemini.ui.preview.protos;

import com.google.android.appfunctions.schema.internal.dependencies.zzar;
import com.google.android.appfunctions.schema.internal.dependencies.zzas;
import com.google.android.appfunctions.schema.internal.dependencies.zzaw;
import com.google.android.appfunctions.schema.internal.dependencies.zzax;
import com.google.android.appfunctions.schema.internal.dependencies.zzbb;
import com.google.android.appfunctions.schema.internal.dependencies.zzbc;
import com.google.android.appfunctions.schema.internal.dependencies.zzbd;
import com.google.android.appfunctions.schema.internal.dependencies.zzcf;
import com.google.android.appfunctions.schema.internal.dependencies.zzcm;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public final class MediaItem extends zzax<MediaItem, Builder> implements MediaItemOrBuilder {
    private static final MediaItem DEFAULT_INSTANCE;
    public static final int MEDIA_URL_FIELD_NUMBER = 2;
    private static volatile zzcm<MediaItem> PARSER = null;
    public static final int TYPE_FIELD_NUMBER = 1;
    public static final int VIDEO_METADATA_FIELD_NUMBER = 3;
    private int bitField0_;
    private String mediaUrl_ = "";
    private int type_;
    private VideoMetadata videoMetadata_;

    /* JADX INFO: renamed from: com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem$1, reason: invalid class name */
    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[zzaw.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[zzaw.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzaw.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzaw.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzaw.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzaw.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzaw.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzaw.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public static final class Builder extends zzar<MediaItem, Builder> implements MediaItemOrBuilder {
        public Builder() {
            super(MediaItem.DEFAULT_INSTANCE);
        }

        public /* synthetic */ Builder(byte[] bArr) {
            this();
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public enum Type implements zzbb {
        TYPE_UNSPECIFIED(0),
        IMAGE(1),
        VIDEO(2),
        UNRECOGNIZED(-1);

        public static final zzbc internalValueMap = new zzbc<Type>() { // from class: com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem.Type.1
        };
        public final int value;

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        public static final class TypeVerifier implements zzbd {
            public static final zzbd INSTANCE = new TypeVerifier();
        }

        Type(int i) {
            this.value = i;
        }

        @Override // com.google.android.appfunctions.schema.internal.dependencies.zzbb
        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Override // java.lang.Enum
        public final String toString() {
            return Integer.toString(this.value);
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public static final class VideoMetadata extends zzax<VideoMetadata, Builder> implements VideoMetadataOrBuilder {
        private static final VideoMetadata DEFAULT_INSTANCE;
        public static final int DURATION_MS_FIELD_NUMBER = 1;
        private static volatile zzcm<VideoMetadata> PARSER;
        private int bitField0_;
        private long durationMs_;

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        public static final class Builder extends zzar<VideoMetadata, Builder> implements VideoMetadataOrBuilder {
            public Builder() {
                super(VideoMetadata.DEFAULT_INSTANCE);
            }

            public /* synthetic */ Builder(byte[] bArr) {
                this();
            }
        }

        static {
            VideoMetadata videoMetadata = new VideoMetadata();
            DEFAULT_INSTANCE = videoMetadata;
            zzax.registerDefaultInstance(VideoMetadata.class, videoMetadata);
        }

        @Override // com.google.android.appfunctions.schema.internal.dependencies.zzax
        public final Object dynamicMethod(zzaw zzawVar, Object obj, Object obj2) {
            zzcm zzasVar;
            int iOrdinal = zzawVar.ordinal();
            if (iOrdinal == 0) {
                return (byte) 1;
            }
            if (iOrdinal == 2) {
                return zzax.newMessageInfo(DEFAULT_INSTANCE, "\u0004\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဂ\u0000", new Object[]{"bitField0_", "durationMs_"});
            }
            if (iOrdinal == 3) {
                return new VideoMetadata();
            }
            byte[] bArr = null;
            if (iOrdinal == 4) {
                return new Builder(bArr);
            }
            if (iOrdinal == 5) {
                return DEFAULT_INSTANCE;
            }
            if (iOrdinal != 6) {
                throw null;
            }
            zzcm<VideoMetadata> zzcmVar = PARSER;
            if (zzcmVar != null) {
                return zzcmVar;
            }
            synchronized (VideoMetadata.class) {
                try {
                    zzasVar = PARSER;
                    if (zzasVar == null) {
                        zzasVar = new zzas(DEFAULT_INSTANCE);
                        PARSER = zzasVar;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return zzasVar;
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public interface VideoMetadataOrBuilder extends zzcf {
    }

    static {
        MediaItem mediaItem = new MediaItem();
        DEFAULT_INSTANCE = mediaItem;
        zzax.registerDefaultInstance(MediaItem.class, mediaItem);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzax
    public final Object dynamicMethod(zzaw zzawVar, Object obj, Object obj2) {
        zzcm zzasVar;
        int iOrdinal = zzawVar.ordinal();
        if (iOrdinal == 0) {
            return (byte) 1;
        }
        if (iOrdinal == 2) {
            return zzax.newMessageInfo(DEFAULT_INSTANCE, "\u0004\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ለ\u0001\u0003ဉ\u0002", new Object[]{"bitField0_", "type_", "mediaUrl_", "videoMetadata_"});
        }
        if (iOrdinal == 3) {
            return new MediaItem();
        }
        byte[] bArr = null;
        if (iOrdinal == 4) {
            return new Builder(bArr);
        }
        if (iOrdinal == 5) {
            return DEFAULT_INSTANCE;
        }
        if (iOrdinal != 6) {
            throw null;
        }
        zzcm<MediaItem> zzcmVar = PARSER;
        if (zzcmVar != null) {
            return zzcmVar;
        }
        synchronized (MediaItem.class) {
            try {
                zzasVar = PARSER;
                if (zzasVar == null) {
                    zzasVar = new zzas(DEFAULT_INSTANCE);
                    PARSER = zzasVar;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return zzasVar;
    }
}

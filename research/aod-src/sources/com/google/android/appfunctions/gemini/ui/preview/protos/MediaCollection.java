package com.google.android.appfunctions.gemini.ui.preview.protos;

import com.google.android.appfunctions.schema.internal.dependencies.zzar;
import com.google.android.appfunctions.schema.internal.dependencies.zzas;
import com.google.android.appfunctions.schema.internal.dependencies.zzaw;
import com.google.android.appfunctions.schema.internal.dependencies.zzax;
import com.google.android.appfunctions.schema.internal.dependencies.zzbh;
import com.google.android.appfunctions.schema.internal.dependencies.zzcm;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public final class MediaCollection extends zzax<MediaCollection, Builder> implements MediaCollectionOrBuilder {
    private static final MediaCollection DEFAULT_INSTANCE;
    public static final int MEDIA_ITEMS_FIELD_NUMBER = 1;
    private static volatile zzcm<MediaCollection> PARSER;
    private zzbh<MediaItem> mediaItems_ = zzax.emptyProtobufList();

    /* JADX INFO: renamed from: com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollection$1, reason: invalid class name */
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
    public static final class Builder extends zzar<MediaCollection, Builder> implements MediaCollectionOrBuilder {
        public Builder() {
            super(MediaCollection.DEFAULT_INSTANCE);
        }

        public /* synthetic */ Builder(byte[] bArr) {
            this();
        }
    }

    static {
        MediaCollection mediaCollection = new MediaCollection();
        DEFAULT_INSTANCE = mediaCollection;
        zzax.registerDefaultInstance(MediaCollection.class, mediaCollection);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzax
    public final Object dynamicMethod(zzaw zzawVar, Object obj, Object obj2) {
        zzcm zzasVar;
        int iOrdinal = zzawVar.ordinal();
        if (iOrdinal == 0) {
            return (byte) 1;
        }
        if (iOrdinal == 2) {
            return zzax.newMessageInfo(DEFAULT_INSTANCE, "\u0004\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"mediaItems_", MediaItem.class});
        }
        if (iOrdinal == 3) {
            return new MediaCollection();
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
        zzcm<MediaCollection> zzcmVar = PARSER;
        if (zzcmVar != null) {
            return zzcmVar;
        }
        synchronized (MediaCollection.class) {
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

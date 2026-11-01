package com.xiaomi.taiyi.sdk.transfer.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIRequest extends AITransfer {
    public static final CREATOR CREATOR = new CREATOR(null);

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AIRequest> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ AIRequest create$default(CREATOR creator, Context context, String str, String str2, int i, Object obj) {
            if ((i & 4) != 0) {
                str2 = null;
            }
            return creator.create(context, str, str2);
        }

        public static /* synthetic */ AIRequest createDownloadRequest$default(CREATOR creator, Context context, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return creator.createDownloadRequest(context, str);
        }

        public static /* synthetic */ AIRequest createProviderRequest$default(CREATOR creator, Context context, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return creator.createProviderRequest(context, str);
        }

        public static /* synthetic */ AIRequest createServiceRequest$default(CREATOR creator, Context context, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return creator.createServiceRequest(context, str);
        }

        @JvmStatic
        public final AIRequest create(Context context, String scheme, String str) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(scheme, "scheme");
            return new AIRequest(context, scheme, str, null);
        }

        @JvmStatic
        public final AIRequest createAbilityRequest(Context context, String api) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(api, "api");
            return create(context, "ability", api);
        }

        @JvmStatic
        public final AIRequest createDownloadRequest(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return createDownloadRequest$default(this, context, null, 2, null);
        }

        @JvmStatic
        public final AIRequest createDownloadRequest(Context context, String str) {
            Intrinsics.checkNotNullParameter(context, "context");
            return create(context, "download", str);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIRequest createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AIRequest(parcel, null);
        }

        @JvmStatic
        public final AIRequest createProviderRequest(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return createProviderRequest$default(this, context, null, 2, null);
        }

        @JvmStatic
        public final AIRequest createProviderRequest(Context context, String str) {
            Intrinsics.checkNotNullParameter(context, "context");
            return create(context, "provider", str);
        }

        @JvmStatic
        public final AIRequest createServiceRequest(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return createServiceRequest$default(this, context, null, 2, null);
        }

        @JvmStatic
        public final AIRequest createServiceRequest(Context context, String str) {
            Intrinsics.checkNotNullParameter(context, "context");
            return create(context, "service", str);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIRequest[] newArray(int i) {
            return new AIRequest[i];
        }
    }

    private AIRequest() {
    }

    private AIRequest(Context context) {
        super(context);
    }

    private AIRequest(Context context, String str, String str2) {
        String str3;
        this(context);
        if (str2 == null || str2.length() == 0) {
            str3 = str + ":" + ExtsKt.getMY_PID() + ":" + UUID.randomUUID();
        } else {
            getHeaders().setAbilityApi(str2);
            str3 = str + ":" + str2 + ":" + ExtsKt.getMY_PID() + ":" + UUID.randomUUID();
        }
        setRequestId(str3);
    }

    public /* synthetic */ AIRequest(Context context, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, (i & 4) != 0 ? null : str2);
    }

    public /* synthetic */ AIRequest(Context context, String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, str2);
    }

    private AIRequest(Parcel parcel) {
        super(parcel);
    }

    public /* synthetic */ AIRequest(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @JvmStatic
    public static final AIRequest create(Context context, String str, String str2) {
        return CREATOR.create(context, str, str2);
    }

    @JvmStatic
    public static final AIRequest createAbilityRequest(Context context, String str) {
        return CREATOR.createAbilityRequest(context, str);
    }

    @JvmStatic
    public static final AIRequest createDownloadRequest(Context context) {
        return CREATOR.createDownloadRequest(context);
    }

    @JvmStatic
    public static final AIRequest createDownloadRequest(Context context, String str) {
        return CREATOR.createDownloadRequest(context, str);
    }

    @JvmStatic
    public static final AIRequest createProviderRequest(Context context) {
        return CREATOR.createProviderRequest(context);
    }

    @JvmStatic
    public static final AIRequest createProviderRequest(Context context, String str) {
        return CREATOR.createProviderRequest(context, str);
    }

    @JvmStatic
    public static final AIRequest createServiceRequest(Context context) {
        return CREATOR.createServiceRequest(context);
    }

    @JvmStatic
    public static final AIRequest createServiceRequest(Context context, String str) {
        return CREATOR.createServiceRequest(context, str);
    }

    private final void setRequestId(String str) {
        getHeaders().setRequestId$base_release(str);
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.data.AITransfer, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getApi() {
        return getHeaders().getAbilityApi();
    }

    public final String getRequestId() {
        return getHeaders().getRequestId();
    }

    public final String getUidPid() {
        return getHeaders().getUid() + ":" + getHeaders().getPid();
    }

    public String toString() {
        return "AIRequest{\nid = " + getRequestId() + ", \nheaders = " + getHeaders() + ", \nbody = " + getBody() + "\n}";
    }
}

package com.xiaomi.taiyi.sdk.transfer.data;

import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.transfer.core.IAIAbility;
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
public final class AIResponse extends AITransfer {
    public static final CREATOR CREATOR = new CREATOR(null);

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AIResponse> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final AIResponse create(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new AIResponse(context, (DefaultConstructorMarker) null);
        }

        @JvmStatic
        public final AIResponse create(Context context, AIRequest request) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(request, "request");
            return new AIResponse(context, request, null);
        }

        @JvmStatic
        public final AIResponse create(AIRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            return new AIResponse(request, (DefaultConstructorMarker) null);
        }

        @JvmStatic
        public final AIResponse create(AIRequest request, int i) {
            Intrinsics.checkNotNullParameter(request, "request");
            AIResponse aIResponse = new AIResponse(request, (DefaultConstructorMarker) null);
            aIResponse.setCode(i);
            return aIResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIResponse createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AIResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIResponse[] newArray(int i) {
            return new AIResponse[i];
        }
    }

    private AIResponse() {
    }

    private AIResponse(Context context) {
        super(context);
    }

    private AIResponse(Context context, AIRequest aIRequest) {
        this(context);
        setRequestId(aIRequest.getRequestId());
        getBody().setShareMemoryState(aIRequest.getBody().isShareMemoryEnable());
        ShareMemoryPool memoryPool = aIRequest.getBody().getMemoryPool();
        if (memoryPool != null) {
            getBody().setMemoryPool(memoryPool);
        }
    }

    public /* synthetic */ AIResponse(Context context, AIRequest aIRequest, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, aIRequest);
    }

    public /* synthetic */ AIResponse(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AIResponse(Parcel parcel) {
        super(parcel);
        Intrinsics.checkNotNullParameter(parcel, "parcel");
    }

    private AIResponse(AIRequest aIRequest) {
        this();
        setRequestId(aIRequest.getRequestId());
        getBody().setShareMemoryState(aIRequest.getBody().isShareMemoryEnable());
        ShareMemoryPool memoryPool = aIRequest.getBody().getMemoryPool();
        if (memoryPool != null) {
            getBody().setMemoryPool(memoryPool);
        }
    }

    public /* synthetic */ AIResponse(AIRequest aIRequest, DefaultConstructorMarker defaultConstructorMarker) {
        this(aIRequest);
    }

    @JvmStatic
    public static final AIResponse create(Context context) {
        return CREATOR.create(context);
    }

    @JvmStatic
    public static final AIResponse create(Context context, AIRequest aIRequest) {
        return CREATOR.create(context, aIRequest);
    }

    @JvmStatic
    public static final AIResponse create(AIRequest aIRequest) {
        return CREATOR.create(aIRequest);
    }

    @JvmStatic
    public static final AIResponse create(AIRequest aIRequest, int i) {
        return CREATOR.create(aIRequest, i);
    }

    private final void setRequestId(String str) {
        getHeaders().setRequestId$base_release(str);
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.data.AITransfer, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final IAIAbility getAbility() {
        IBinder binder = getBody().getBinder(AIKeys.KEY_AI_ABILITY, null);
        if (binder != null) {
            return IAIAbility.Stub.asInterface(binder);
        }
        return null;
    }

    public final int getCode() {
        return getBody().getErrorCode();
    }

    public final String getRequestId() {
        return getHeaders().getRequestId();
    }

    public final boolean isSuccessful() {
        return getCode() == 0;
    }

    public final void setAbility(IAIAbility iAIAbility) {
        getBody().putBinder(AIKeys.KEY_AI_ABILITY, iAIAbility != null ? iAIAbility.asBinder() : null);
    }

    public final void setCode(int i) {
        getBody().setErrorCode(i);
    }

    public String toString() {
        return "AIResponse{\nid = " + getRequestId() + ", \nheaders = " + getHeaders() + ", \nbody = " + getBody() + "\n}";
    }
}

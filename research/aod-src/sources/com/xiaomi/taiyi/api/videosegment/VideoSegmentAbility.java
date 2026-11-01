package com.xiaomi.taiyi.api.videosegment;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.xiaomi.taiyi.sdk.core.AIAbility;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
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
public final class VideoSegmentAbility extends AIAbility {
    public static final Companion Companion = new Companion(null);

    @Metadata
    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoSegmentAbility(Context context) {
        super(context, "video_segment");
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public static final Unit a(FastPlayerListener fastPlayerListener, AIResponse res) {
        Intrinsics.checkNotNullParameter(res, "res");
        fastPlayerListener.playerModeChange(res.getBody().getInt("player_mode", 0), res.getBody().getInt("player_code", 0), res.getBody().getLong("player_extra", 0L));
        return Unit.INSTANCE;
    }

    public static void a(final FastPlayerListener fastPlayerListener, AIRequest aIRequest) {
        aIRequest.getBody().putCallback(new Function1() { // from class: com.xiaomi.taiyi.api.videosegment.VideoSegmentAbility$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VideoSegmentAbility.a(fastPlayerListener, (AIResponse) obj);
            }
        });
    }

    @Override // com.xiaomi.taiyi.sdk.core.AIAbility
    public int apiLevel() {
        return 100;
    }

    public final int segmentByFd(ParcelFileDescriptor sourceFD, ParcelFileDescriptor targetFD, FastPlayerListener fastPlayerListener) {
        Intrinsics.checkNotNullParameter(sourceFD, "sourceFD");
        Intrinsics.checkNotNullParameter(targetFD, "targetFD");
        AIRequest aIRequestCreateRequest = createRequest();
        aIRequestCreateRequest.getHeaders().setMethod("segmentByFd");
        aIRequestCreateRequest.getBody().putParcelable("sourceFD", sourceFD);
        aIRequestCreateRequest.getBody().putParcelable("targetFD", targetFD);
        if (fastPlayerListener != null) {
            a(fastPlayerListener, aIRequestCreateRequest);
        }
        return execute(aIRequestCreateRequest).getBody().getSpecCode();
    }
}

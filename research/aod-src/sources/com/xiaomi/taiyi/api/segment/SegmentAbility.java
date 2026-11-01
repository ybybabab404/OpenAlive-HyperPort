package com.xiaomi.taiyi.api.segment;

import android.content.Context;
import android.graphics.Bitmap;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.core.AIAbility;
import com.xiaomi.taiyi.sdk.transfer.data.AIPackage;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import com.xiaomi.taiyi.segment.data.SegmentResult;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SegmentAbility extends AIAbility {
    public static final Companion Companion = new Companion(null);

    @Metadata
    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SegmentAbility(Context context) {
        super(context, "segment");
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.xiaomi.taiyi.sdk.core.AIAbility
    public int apiLevel() {
        return 100;
    }

    public final SegmentResult segment(Bitmap bitmap, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        AIRequest aIRequestCreateRequest = createRequest();
        aIRequestCreateRequest.getHeaders().setMethod("nativeSegmentBitmap");
        AIPackage.putBitmap$default(aIRequestCreateRequest.getBody(), "segment_bitmap", bitmap, false, 4, null);
        aIRequestCreateRequest.getBody().putBoolean("extract_combined_subject", z);
        aIRequestCreateRequest.getBody().putBoolean("max_area_as_single_subject", z2);
        AIResponse aIResponseExecute = execute(aIRequestCreateRequest);
        int errorCode = aIResponseExecute.getBody().getErrorCode();
        SdkLog.i(getTAG(), "segment response code: " + errorCode + ", spec_code: " + aIResponseExecute.getBody().getSpecCode());
        return (SegmentResult) aIResponseExecute.getBody().getParcelable("segment_result", SegmentResult.class, null);
    }
}

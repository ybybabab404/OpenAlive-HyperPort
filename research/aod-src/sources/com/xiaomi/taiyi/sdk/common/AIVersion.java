package com.xiaomi.taiyi.sdk.common;

import android.content.Context;
import com.xiaomi.taiyi.sdk.base.utils.AIUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIVersion {
    public static final AIVersion INSTANCE = new AIVersion();
    public static final String META_KEY_AI_SDK_CODE = "ai_sdk_code";
    public static final String META_KEY_AI_SDK_NAME = "ai_sdk_name";
    public static final int SDK_INT = 100;
    public static final String SDK_NAME = "1.0.0";

    @Metadata
    public static final class VERSION_CODES {
        public static final VERSION_CODES INSTANCE = new VERSION_CODES();
        public static final int INVALID = -1;
        public static final int OS_3_1_0 = 1;
    }

    @JvmStatic
    public static final int SERVICE_SDK_INT(Context context) {
        Integer intOrNull;
        Intrinsics.checkNotNullParameter(context, "context");
        String appMetadata = AIUtils.getAppMetadata(context, "com.xiaomi.aiservice", "ai_sdk_code");
        if (appMetadata == null || (intOrNull = StringsKt.toIntOrNull(appMetadata)) == null) {
            return -1;
        }
        return intOrNull.intValue();
    }

    @JvmStatic
    public static final String SERVICE_SDK_NAME(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String appMetadata = AIUtils.getAppMetadata(context, "com.xiaomi.aiservice", "ai_sdk_name");
        return appMetadata == null ? "" : appMetadata;
    }

    @JvmStatic
    public static final boolean isServiceSupport(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return SERVICE_SDK_INT(context) > -1;
    }
}

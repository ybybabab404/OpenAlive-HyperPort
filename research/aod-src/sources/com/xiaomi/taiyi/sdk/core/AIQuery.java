package com.xiaomi.taiyi.sdk.core;

import android.content.ContentProviderClient;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import com.xiaomi.taiyi.sdk.base.data.AIConfig;
import com.xiaomi.taiyi.sdk.base.data.AIDetail;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.common.AIVersion;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import kotlin.Metadata;
import kotlin.Pair;
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
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIQuery {
    public static final AIQuery INSTANCE = new AIQuery();

    public static final AIConfig getAIConfig(Context context, AIRequest request) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        String api = request.getApi();
        if (api.length() == 0) {
            SdkLog.e("AIQuery", "getAIConfig " + api + " empty!");
            return null;
        }
        AIResponse aIResponseQuery = INSTANCE.query(context, request, "getAIConfig");
        if (!aIResponseQuery.isSuccessful()) {
            SdkLog.e("AIQuery", "getAIConfig " + api + " failed! " + aIResponseQuery.getCode());
        }
        return AIConfig.Companion.parse(api, aIResponseQuery);
    }

    public static final AIDetail getAIDetail(Context context, AIRequest request) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        String api = request.getApi();
        if (api.length() == 0) {
            SdkLog.e("AIQuery", "getAIDetails " + api + " empty!");
            return null;
        }
        AIResponse aIResponseQuery = INSTANCE.query(context, request, "getAIDetail");
        if (!aIResponseQuery.isSuccessful()) {
            SdkLog.e("AIQuery", "getAIDetails " + api + " failed! " + aIResponseQuery.getCode());
        }
        return AIDetail.Companion.parse(api, aIResponseQuery);
    }

    public static final AIDetail getAIDetail(Context context, String api, Bundle bundle) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(api, "api");
        AIRequest aIRequestCreateProviderRequest = AIRequest.CREATOR.createProviderRequest(context, api);
        if (bundle != null) {
            aIRequestCreateProviderRequest.getHeaders().putAll(bundle);
        }
        return getAIDetail(context, aIRequestCreateProviderRequest);
    }

    public static /* synthetic */ AIDetail getAIDetail$default(Context context, String str, Bundle bundle, int i, Object obj) {
        if ((i & 4) != 0) {
            bundle = null;
        }
        return getAIDetail(context, str, bundle);
    }

    public static final AIResponse query(Context context, AIRequest request, String method, String subMethod) {
        Pair pair;
        AIResponse aIResponseCreate;
        AIResponse aIResponseCreate2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(subMethod, "subMethod");
        if (!AIVersion.isServiceSupport(context)) {
            SdkLog.e("AIQuery", "queryByCall service not support!");
            AIResponse aIResponseCreate3 = AIResponse.CREATOR.create(request);
            aIResponseCreate3.setCode(ErrorCode.ERROR_SERVICE_NOT_SUPPORT);
            return aIResponseCreate3;
        }
        try {
            ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient("com.xiaomi.aiservice.authority.open");
            pair = new Pair(contentProviderClientAcquireUnstableContentProviderClient, Integer.valueOf(contentProviderClientAcquireUnstableContentProviderClient == null ? ErrorCode.ERROR_PROVIDER_EMPTY : 0));
        } catch (DeadObjectException e) {
            SdkLog.e("AIQuery", "queryByCall " + subMethod + " DeadObjectException. " + e.getMessage());
            pair = new Pair(null, Integer.valueOf(ErrorCode.ERROR_PROVIDER_DEAD));
        } catch (RuntimeException e2) {
            SdkLog.e("AIQuery", "queryByCall " + subMethod + " RuntimeException. " + e2.getMessage());
            String message = e2.getMessage();
            pair = (message == null || !StringsKt.contains$default((CharSequence) message, (CharSequence) "Permission", false, 2, (Object) null)) ? new Pair(null, Integer.valueOf(ErrorCode.ERROR_PROVIDER_NOT_FOUND)) : new Pair(null, Integer.valueOf(ErrorCode.ERROR_PROVIDER_PERMISSION_DENIED));
        } catch (Throwable th) {
            SdkLog.e("AIQuery", "queryByCall " + subMethod + " Throwable. " + th.getMessage());
            pair = new Pair(null, Integer.valueOf(ErrorCode.ERROR_PROVIDER_ACQUIRE_ERROR));
        }
        ContentProviderClient contentProviderClient = (ContentProviderClient) pair.component1();
        int iIntValue = ((Number) pair.component2()).intValue();
        if (contentProviderClient == null) {
            AIResponse aIResponseCreate4 = AIResponse.CREATOR.create(request);
            aIResponseCreate4.setCode(iIntValue);
            return aIResponseCreate4;
        }
        Bundle bundle = new Bundle();
        bundle.putString(AIKeys.KEY_PROVIDER_SUB_METHOD, subMethod);
        bundle.putParcelable(AIKeys.KEY_REQUEST, request);
        try {
            try {
                Bundle bundleCall = contentProviderClient.call("com.xiaomi.aiservice.authority.open", method, "", bundle);
                if (bundleCall == null) {
                    aIResponseCreate2 = AIResponse.CREATOR.create(request);
                    aIResponseCreate2.setCode(ErrorCode.ERROR_PROVIDER_CALL_EMPTY);
                } else {
                    bundleCall.setClassLoader(AIQuery.class.getClassLoader());
                    aIResponseCreate2 = (AIResponse) bundleCall.getParcelable("response", AIResponse.class);
                    if (aIResponseCreate2 == null) {
                        aIResponseCreate2 = AIResponse.CREATOR.create(request);
                        aIResponseCreate2.setCode(ErrorCode.ERROR_PROVIDER_CALL_NO_RESPONSE);
                    }
                }
                contentProviderClient.close();
            } catch (Throwable th2) {
                contentProviderClient.close();
                throw th2;
            }
        } catch (DeadObjectException e3) {
            SdkLog.e("AIQuery", "queryByCall " + subMethod + " Dead. " + e3.getMessage());
            aIResponseCreate = AIResponse.CREATOR.create(request);
            aIResponseCreate.setCode(ErrorCode.ERROR_PROVIDER_CALL_DEAD);
            aIResponseCreate2 = aIResponseCreate;
            contentProviderClient.close();
        } catch (Throwable th3) {
            SdkLog.e("AIQuery", "queryByCall " + subMethod + " Error. " + th3.getMessage());
            aIResponseCreate = AIResponse.CREATOR.create(request);
            aIResponseCreate.setCode(ErrorCode.ERROR_PROVIDER_CALL_ERROR);
            aIResponseCreate2 = aIResponseCreate;
            contentProviderClient.close();
        }
        return aIResponseCreate2;
    }

    public final AIResponse query(Context context, AIRequest request, String subMethod) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(subMethod, "subMethod");
        return query(context, request, "query", subMethod);
    }
}

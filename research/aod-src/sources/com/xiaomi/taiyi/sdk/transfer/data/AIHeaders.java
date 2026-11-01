package com.xiaomi.taiyi.sdk.transfer.data;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.base.utils.BaseHeaders;
import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.common.AIVersion;
import kotlin.Deprecated;
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
public final class AIHeaders extends AIPackage {
    public static final CREATOR CREATOR = new CREATOR(null);

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AIHeaders> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIHeaders createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AIHeaders(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIHeaders[] newArray(int i) {
            return new AIHeaders[i];
        }
    }

    public AIHeaders() {
    }

    public AIHeaders(Context context) throws PackageManager.NameNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        BaseHeaders baseHeaders = BaseHeaders.Companion.get(context);
        setAppCode(baseHeaders.getAppCode());
        setAppSignature(baseHeaders.getAppSignature());
        setProductCode(baseHeaders.getProductCode());
        setUid(ExtsKt.getMY_UID());
        setPid(ExtsKt.getMY_PID());
        String my_process_name = ExtsKt.getMY_PROCESS_NAME();
        Intrinsics.checkNotNullExpressionValue(my_process_name, "<get-MY_PROCESS_NAME>(...)");
        setProcessName(my_process_name);
        setPackageName(baseHeaders.getAppPackage());
        setAppVersionCode(baseHeaders.getAppVersionCode());
        setAppVersionName(baseHeaders.getAppVersionName());
        setSdkCode(100);
        setSdkName(AIVersion.SDK_NAME);
    }

    private AIHeaders(Parcel parcel) {
        super(parcel);
    }

    public /* synthetic */ AIHeaders(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public final void enableLocalAffinity() {
        putInt(AIKeys.KEY_RUN_AFFINITY, 1);
    }

    public final String getAbilityApi() {
        String string = getString(AIKeys.KEY_AI_ABILITY_API, "");
        return string == null ? "" : string;
    }

    public final String getAbilityName() {
        String string = getString(AIKeys.KEY_AI_ABILITY_NAME, "");
        return string == null ? "" : string;
    }

    public final long getAbilityVersion() {
        return getLong(AIKeys.KEY_AI_ABILITY_VERSION, 0L);
    }

    public final int getApiLevel() {
        return getInt(AIKeys.KEY_AI_ABILITY_API_LEVEL, -1);
    }

    public final String getAppCode() {
        String string = getString(AIKeys.KEY_APP_CODE, "");
        return string == null ? "" : string;
    }

    public final String getAppSignature() {
        String string = getString(AIKeys.KEY_APP_SIGNATURE, "");
        return string == null ? "" : string;
    }

    public final long getAppVersionCode() {
        return getLong(AIKeys.KEY_APP_VERSION_CODE, -1L);
    }

    public final String getAppVersionName() {
        String string = getString(AIKeys.KEY_APP_VERSION_NAME, "");
        return string == null ? "" : string;
    }

    public final String getClientId() {
        String string = getString(AIKeys.KEY_AI_CLIENT_ID, "");
        return string == null ? "" : string;
    }

    public final String getFormatTime() {
        String string = getString(AIKeys.KEY_FORMAT_TIME, "");
        return string == null ? "" : string;
    }

    public final String getMethod() {
        String string = getString(AIKeys.KEY_AI_METHOD, "");
        return string == null ? "" : string;
    }

    public final String getPackageName() {
        String string = getString(AIKeys.KEY_PACKAGE_NAME, "");
        return string == null ? "" : string;
    }

    public final int getPid() {
        return getInt(AIKeys.KEY_PID, -1);
    }

    public final String getProcessName() {
        String string = getString(AIKeys.KEY_PROCESS_NAME, "");
        return string == null ? "" : string;
    }

    @Deprecated
    public final String getProductCode() {
        String string = getString(AIKeys.KEY_PRODUCT_CODE, "");
        return string == null ? "" : string;
    }

    public final String getRequestId() {
        String string = getString(AIKeys.KEY_REQUEST_ID, "");
        return string == null ? "" : string;
    }

    public final int getSdkCode() {
        return getInt("ai_sdk_code", -1);
    }

    public final String getSdkName() {
        String string = getString("ai_sdk_name", "");
        return string == null ? "" : string;
    }

    public final long getSysBootMills() {
        return getLong(AIKeys.KEY_SYS_BOOT_MILLS, -1L);
    }

    public final long getSysBootNanos() {
        return getLong(AIKeys.KEY_SYS_BOOT_NANOS, -1L);
    }

    public final long getTid() {
        return getLong(AIKeys.KEY_TID, -1L);
    }

    public final long getTimestamp() {
        return getLong("timestamp", -1L);
    }

    public final int getUid() {
        return getInt(AIKeys.KEY_UID, -1);
    }

    public final boolean isLocalAffinity() {
        return getInt(AIKeys.KEY_RUN_AFFINITY, 0) == 1;
    }

    public final void setAbilityApi(String abilityApi) {
        Intrinsics.checkNotNullParameter(abilityApi, "abilityApi");
        putString(AIKeys.KEY_AI_ABILITY_API, abilityApi);
    }

    public final void setAbilityName(String abilityName) {
        Intrinsics.checkNotNullParameter(abilityName, "abilityName");
        putString(AIKeys.KEY_AI_ABILITY_NAME, abilityName);
    }

    public final void setAbilityVersion(long j) {
        putLong(AIKeys.KEY_AI_ABILITY_VERSION, j);
    }

    public final void setApiLevel(int i) {
        putInt(AIKeys.KEY_AI_ABILITY_API_LEVEL, i);
    }

    public final void setAppCode(String code) {
        Intrinsics.checkNotNullParameter(code, "code");
        putString(AIKeys.KEY_APP_CODE, code);
    }

    public final void setAppSignature(String signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        putString(AIKeys.KEY_APP_SIGNATURE, signature);
    }

    public final void setAppVersionCode(long j) {
        putLong(AIKeys.KEY_APP_VERSION_CODE, j);
    }

    public final void setAppVersionName(String appVersionName) {
        Intrinsics.checkNotNullParameter(appVersionName, "appVersionName");
        putString(AIKeys.KEY_APP_VERSION_NAME, appVersionName);
    }

    public final void setClientId(String clientId) {
        Intrinsics.checkNotNullParameter(clientId, "clientId");
        putString(AIKeys.KEY_AI_CLIENT_ID, clientId);
    }

    public final void setFormatTime(String formatTime) {
        Intrinsics.checkNotNullParameter(formatTime, "formatTime");
        putString(AIKeys.KEY_FORMAT_TIME, formatTime);
    }

    public final void setMethod(String method) {
        Intrinsics.checkNotNullParameter(method, "method");
        putString(AIKeys.KEY_AI_METHOD, method);
    }

    public final void setPackageName(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        putString(AIKeys.KEY_PACKAGE_NAME, packageName);
    }

    public final void setPid(int i) {
        putInt(AIKeys.KEY_PID, i);
    }

    public final void setProcessName(String processName) {
        Intrinsics.checkNotNullParameter(processName, "processName");
        putString(AIKeys.KEY_PROCESS_NAME, processName);
    }

    @Deprecated
    public final void setProductCode(String code) {
        Intrinsics.checkNotNullParameter(code, "code");
        putString(AIKeys.KEY_PRODUCT_CODE, code);
    }

    public final void setRequestId$base_release(String requestId) {
        Intrinsics.checkNotNullParameter(requestId, "requestId");
        putString(AIKeys.KEY_REQUEST_ID, requestId);
    }

    public final void setSdkCode(int i) {
        putInt("ai_sdk_code", i);
    }

    public final void setSdkName(String sdkName) {
        Intrinsics.checkNotNullParameter(sdkName, "sdkName");
        putString("ai_sdk_name", sdkName);
    }

    public final void setSysBootMills(long j) {
        putLong(AIKeys.KEY_SYS_BOOT_MILLS, j);
    }

    public final void setSysBootNanos(long j) {
        putLong(AIKeys.KEY_SYS_BOOT_NANOS, j);
    }

    public final void setTid(long j) {
        putLong(AIKeys.KEY_TID, j);
    }

    public final AIHeaders setTime() {
        setTimestamp(ExtsKt.timestamp());
        setSysBootMills(ExtsKt.bootMills());
        String time = ExtsKt.formatTime();
        Intrinsics.checkNotNullExpressionValue(time, "formatTime(...)");
        setFormatTime(time);
        return this;
    }

    public final void setTimestamp(long j) {
        putLong("timestamp", j);
    }

    public final void setUid(int i) {
        putInt(AIKeys.KEY_UID, i);
    }
}

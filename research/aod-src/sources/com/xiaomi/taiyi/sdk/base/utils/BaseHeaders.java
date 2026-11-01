package com.xiaomi.taiyi.sdk.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.xiaomi.taiyi.sdk.common.AIKeys;
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
public final class BaseHeaders {
    public static volatile boolean initialized;
    public long appVersionCode;
    public static final Companion Companion = new Companion(null);
    public static BaseHeaders instance = new BaseHeaders();
    public String appPackage = "";
    public String appVersionName = "";
    public String appCode = "";
    public String productCode = "";
    public String appSignature = "";

    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final BaseHeaders get(Context context) throws PackageManager.NameNotFoundException {
            Intrinsics.checkNotNullParameter(context, "context");
            if (BaseHeaders.initialized) {
                return BaseHeaders.instance;
            }
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            BaseHeaders baseHeaders = BaseHeaders.instance;
            baseHeaders.setAppPackage(context.getPackageName());
            String str = packageInfo.versionName;
            if (str == null) {
                str = "";
            }
            baseHeaders.setAppVersionName(str);
            baseHeaders.setAppVersionCode(packageInfo.getLongVersionCode());
            String appMetadata = AIUtils.getAppMetadata(context, AIKeys.KEY_APP_CODE);
            if (appMetadata == null) {
                appMetadata = "";
            }
            baseHeaders.setAppCode(appMetadata);
            String appMetadata2 = AIUtils.getAppMetadata(context, AIKeys.KEY_PRODUCT_CODE);
            baseHeaders.setProductCode(appMetadata2 != null ? appMetadata2 : "");
            baseHeaders.setAppSignature(AIUtils.getAppSignature(context));
            BaseHeaders.initialized = true;
            return BaseHeaders.instance;
        }
    }

    public final String getAppCode() {
        return this.appCode;
    }

    public final String getAppPackage() {
        return this.appPackage;
    }

    public final String getAppSignature() {
        return this.appSignature;
    }

    public final long getAppVersionCode() {
        return this.appVersionCode;
    }

    public final String getAppVersionName() {
        return this.appVersionName;
    }

    public final String getProductCode() {
        return this.productCode;
    }

    public final void setAppCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appCode = str;
    }

    public final void setAppPackage(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appPackage = str;
    }

    public final void setAppSignature(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appSignature = str;
    }

    public final void setAppVersionCode(long j) {
        this.appVersionCode = j;
    }

    public final void setAppVersionName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appVersionName = str;
    }

    public final void setProductCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.productCode = str;
    }
}

package com.xiaomi.taiyi.sdk.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Process;
import com.miui.keyguard.editor.data.bean.MagicType;
import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

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
public final class AIUtils {
    public static final AIUtils INSTANCE = new AIUtils();
    public static Boolean tsbCheckResult;

    public static final boolean detectTSB(Context context) {
        Boolean boolValueOf;
        Intrinsics.checkNotNullParameter(context, "context");
        Boolean bool = tsbCheckResult;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            boolValueOf = Boolean.valueOf(new File(getTSBDir(context)).exists());
        } catch (Exception e) {
            SdkLog.e("AIUtils", "detectTSB failed. " + e.getMessage());
            boolValueOf = Boolean.FALSE;
        }
        tsbCheckResult = boolValueOf;
        return boolValueOf.booleanValue();
    }

    public static final String getAppMetadata(Context context, String key) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        String packageName = context.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
        return getAppMetadata(context, packageName, key);
    }

    public static final String getAppMetadata(Context context, String packageName, String key) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(key, "key");
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 128);
            Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
            Object obj = applicationInfo.metaData.get(key);
            if (obj == null) {
                obj = "";
            }
            return obj.toString();
        } catch (Exception e) {
            SdkLog.w("AIUtils", "getMetadata failed: " + packageName + " -> " + key + ". " + e.getMessage());
            return null;
        }
    }

    public static final String getAppSignature(Context context) {
        Signature signature;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            SigningInfo signingInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 134217728).signingInfo;
            if (signingInfo == null) {
                return "";
            }
            Signature[] apkContentsSigners = signingInfo.hasMultipleSigners() ? signingInfo.getApkContentsSigners() : signingInfo.getSigningCertificateHistory();
            if (apkContentsSigners == null || (signature = (Signature) ArraysKt.firstOrNull(apkContentsSigners)) == null) {
                return "";
            }
            byte[] bArrDigest = MessageDigest.getInstance("SHA-256").digest(signature.toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDigest) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                sb.append(str);
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        } catch (Exception e) {
            SdkLog.w("AIUtils", "getAppSignature failed. " + e.getMessage());
            return "";
        }
    }

    public static final String getTSBDir(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Context contextCreateDeviceProtectedStorageContext = context.isDeviceProtectedStorage() ? context : context.createDeviceProtectedStorageContext();
        if (Intrinsics.areEqual(context.getPackageName(), "com.xiaomi.aiservice")) {
            return contextCreateDeviceProtectedStorageContext.getDataDir().getAbsolutePath() + "/taiyi";
        }
        return "/data/user_de/" + (Process.myUid() / MagicType.TYPE_DEPTH_VIDEO) + "/com.xiaomi.aiservice/taiyi";
    }
}

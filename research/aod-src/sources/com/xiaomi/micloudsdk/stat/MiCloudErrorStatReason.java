package com.xiaomi.micloudsdk.stat;

import android.content.SyncResult;
import com.miui.maml.folme.AnimatedPropertyType;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.SyncLocalException;

/* JADX INFO: loaded from: classes2.dex */
public class MiCloudErrorStatReason {
    public static String getLocalExceptionReasonForStats(SyncLocalException syncLocalException) {
        return getLocalExceptionReasonForStatsByCode(syncLocalException.getErrorCode());
    }

    public static String getLocalExceptionReasonForStatsByCode(int i) {
        if (i == 3000) {
            return "masterkey_expired";
        }
        if (i == 3001) {
            return "masterkey_not_exist";
        }
        switch (i) {
            case 1000:
                return "cta_error";
            case 1001:
                return "activated_fail";
            case AnimatedPropertyType.TEXT_SIZE /* 1002 */:
                return "permission_limit";
            case AnimatedPropertyType.TEXT_COLOR /* 1003 */:
                return "secure_space_limit";
            default:
                switch (i) {
                    case 2000:
                        return "gdpr_deny";
                    case 2001:
                        return "cloud_space_full";
                    case 2002:
                        return "wlan_only_error";
                    default:
                        return "sync_unknown_error";
                }
        }
    }

    public static String getServerExceptionReasonForStats(CloudServerException cloudServerException) {
        int i = cloudServerException.statusCode;
        if (i == -10001) {
            return getLocalExceptionReasonForStatsByCode(cloudServerException.code);
        }
        if (i == 403) {
            return "sever_error_forbidden";
        }
        if (i == 406) {
            return "sever_error_not-acceptable";
        }
        if (i == 400) {
            return "sever_error_bad_request";
        }
        if (i == 401) {
            return "sever_error_unauthorized";
        }
        if (cloudServerException.code == 52003) {
            return "gdpr_deny";
        }
        return cloudServerException.is5xxServerException() ? "server_5xx_error" : "sync_unknown_error";
    }

    public static String getSyncResultErrorReasonForStats(SyncResult syncResult) {
        return syncResult.hasHardError() ? "sync_hard_error" : "sync_soft_error";
    }
}

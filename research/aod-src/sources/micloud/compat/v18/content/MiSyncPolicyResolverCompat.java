package micloud.compat.v18.content;

import com.xiaomi.micloudsdk.utils.MiCloudSdkBuild;

/* JADX INFO: loaded from: classes2.dex */
public class MiSyncPolicyResolverCompat {
    private static final IMiSyncPolicyResolverCompat sMiSyncPolicyResolverCompat;

    static {
        if (MiCloudSdkBuild.CURRENT_VERSION >= 29) {
            sMiSyncPolicyResolverCompat = new MiSyncPolicyResolverCompat_V29();
        } else {
            sMiSyncPolicyResolverCompat = new MiSyncPolicyResolverCompat_Base();
        }
    }

    private MiSyncPolicyResolverCompat() {
    }

    public static String SYNC_EXTRAS_MICLOUD_FORCE() {
        return sMiSyncPolicyResolverCompat.SYNC_EXTRAS_MICLOUD_FORCE();
    }
}

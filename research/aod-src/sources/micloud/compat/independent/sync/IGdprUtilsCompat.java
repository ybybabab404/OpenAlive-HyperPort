package micloud.compat.independent.sync;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
interface IGdprUtilsCompat {
    boolean isGdprPermissionGranted(Context context);

    void notifyPrivacyDenied(Context context);
}

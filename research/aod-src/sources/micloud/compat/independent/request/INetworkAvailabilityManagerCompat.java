package micloud.compat.independent.request;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
interface INetworkAvailabilityManagerCompat {
    boolean getAvailability(Context context);

    void setAvailability(Context context, boolean z);
}

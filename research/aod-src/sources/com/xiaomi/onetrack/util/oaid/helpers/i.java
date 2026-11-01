package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.xiaomi.onetrack.util.s;

/* JADX INFO: loaded from: classes2.dex */
public class i {
    private static final String a = "NubiaDeviceIDHelper";

    public String a(Context context) {
        try {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = context.getContentResolver().acquireContentProviderClient(Uri.parse("content://cn.nubia.identity/identity"));
            Bundle bundleCall = contentProviderClientAcquireContentProviderClient.call("getOAID", null, null);
            contentProviderClientAcquireContentProviderClient.close();
            return bundleCall.getInt(com.xiaomi.onetrack.g.a.d, -1) == 0 ? bundleCall.getString("id") : "";
        } catch (Exception e) {
            s.a(a, e.getMessage());
            return "";
        }
    }
}

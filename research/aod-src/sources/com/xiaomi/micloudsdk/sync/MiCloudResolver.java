package com.xiaomi.micloudsdk.sync;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncAdapterType;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.sync.utils.SyncContentUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MiCloudResolver {
    public static void assertAccount(Account account) {
        if (account == null || !"com.xiaomi".equals(account.type)) {
            throw new IllegalArgumentException("illegal account");
        }
    }

    public static void assertAuthority(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("illegal authority: empty");
        }
        if (getAuthorityList().contains(str)) {
            return;
        }
        throw new IllegalArgumentException("illegal authority: not registered authority: " + str);
    }

    public static void assertContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
    }

    public static List getAuthorityList() {
        ArrayList arrayList = new ArrayList();
        for (SyncAdapterType syncAdapterType : ContentResolver.getSyncAdapterTypes()) {
            if ("com.xiaomi".equals(syncAdapterType.accountType)) {
                arrayList.add(syncAdapterType.authority);
            }
        }
        return arrayList;
    }

    public static long getResumeTime(Context context, String str) {
        assertContext(context);
        assertAuthority(str);
        Log.d("MiCloudResolver", "getResumeTime: authority: " + str);
        boolean zIsPauseExceptAuthority = SyncContentUtils.isPauseExceptAuthority(context, str);
        long jLoadResumeTime = SyncContentUtils.loadResumeTime(context, str);
        if (!zIsPauseExceptAuthority) {
            long jLoadResumeTime2 = SyncContentUtils.loadResumeTime(context, "_all");
            if (jLoadResumeTime2 >= jLoadResumeTime) {
                return jLoadResumeTime2;
            }
        }
        return jLoadResumeTime;
    }

    public static boolean isSyncPausing(Context context, Account account, String str) {
        assertContext(context);
        assertAccount(account);
        assertAuthority(str);
        Log.d("MiCloudResolver", "isSyncPausing: authority: " + str);
        return System.currentTimeMillis() <= getResumeTime(context, str);
    }
}

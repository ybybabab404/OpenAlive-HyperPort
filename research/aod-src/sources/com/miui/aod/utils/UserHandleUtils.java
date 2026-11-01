package com.miui.aod.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public final class UserHandleUtils {
    public static int USER_CURRENT = ActivityManager.getCurrentUser();

    private UserHandleUtils() {
    }

    public static UserHandle getUserHandle(int i) {
        return UserHandle.of(i);
    }

    public static void refresh() {
        USER_CURRENT = ActivityManager.getCurrentUser();
    }

    public static void registerContentObserverForUser(Context context, Uri uri, boolean z, ContentObserver contentObserver, int i) {
        context.getContentResolver().registerContentObserver(uri, z, contentObserver, i);
    }

    public static void unregisterContentObserverForUser(Context context, ContentObserver contentObserver) {
        context.getContentResolver().unregisterContentObserver(contentObserver);
    }
}

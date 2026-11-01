package com.miui.aod.utils;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationUtils {
    private NotificationUtils() {
    }

    public static Uri maybeAddUserId(Uri uri, int i) {
        return ContentProvider.maybeAddUserId(uri, i);
    }

    public static void registerAsSystemService(NotificationListenerService notificationListenerService, Context context, ComponentName componentName, int i) throws RemoteException {
        notificationListenerService.registerAsSystemService(context, componentName, i);
    }
}

package com.miui.lockscreeninfo.compat;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.UserHandle;
import com.miui.lockscreeninfo.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ContentResolverCompat {
    public static void registerContentObserver(ContentResolver contentResolver, Uri uri, boolean z, ContentObserver contentObserver, UserHandle userHandle) {
        ReflectUtils.invokeObject(ContentResolver.class, contentResolver, "registerContentObserverAsUser", Void.TYPE, new Class[]{Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class}, uri, Boolean.valueOf(z), contentObserver, userHandle);
    }
}

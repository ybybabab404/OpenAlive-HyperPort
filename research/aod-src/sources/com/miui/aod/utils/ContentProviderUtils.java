package com.miui.aod.utils;

import android.content.ContentProvider;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public final class ContentProviderUtils {
    private ContentProviderUtils() {
    }

    public static Uri maybeAddUserId(Uri uri, int i) {
        return ContentProvider.maybeAddUserId(uri, i);
    }
}

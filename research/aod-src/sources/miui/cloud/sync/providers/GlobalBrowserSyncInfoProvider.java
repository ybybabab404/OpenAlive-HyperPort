package miui.cloud.sync.providers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import miui.cloud.sync.SyncInfoProviderBase;

/* JADX INFO: loaded from: classes2.dex */
public class GlobalBrowserSyncInfoProvider extends SyncInfoProviderBase {
    public static final Uri AUTHORITY_URI;
    public static final Uri BOOKMARK_CONTENT_URI;
    public static final Uri HISTORY_CONTENT_URI;

    public static final class Bookmarks {
    }

    static {
        Uri uri = Uri.parse("content://com.miui.browser.global");
        AUTHORITY_URI = uri;
        BOOKMARK_CONTENT_URI = Uri.withAppendedPath(uri, "bookmarks");
        HISTORY_CONTENT_URI = Uri.withAppendedPath(uri, "historysync");
    }

    public final int getBookmarksDirtyCount(Context context) {
        int iQueryCount = queryCount(context, BOOKMARK_CONTENT_URI, "_id != 1 AND dirty=1", (String[]) null);
        if (isDebug()) {
            Log.d("GlobalBrowserSyncInfoProvider", "getGlobalBrowserDirtyBookmarksCount count = " + iQueryCount);
        }
        return iQueryCount;
    }

    public final int getBookmarksSyncedCount(Context context) {
        int iQueryCount = queryCount(context, BOOKMARK_CONTENT_URI, "_id != 1 AND dirty =0  AND sourceid is not null", (String[]) null);
        if (isDebug()) {
            Log.d("GlobalBrowserSyncInfoProvider", "getGlobalBrowserSyncedBookmarksCount count = " + iQueryCount);
        }
        return iQueryCount;
    }

    public final int getHistoryDirtyCount(Context context) {
        int iQueryCount = queryCount(context, HISTORY_CONTENT_URI, "(sourceid IS NULL OR deleted=1)", (String[]) null);
        if (isDebug()) {
            Log.d("GlobalBrowserSyncInfoProvider", "getGlobalBrowserDirtyHistoryCount count = " + iQueryCount);
        }
        return iQueryCount;
    }

    public final int getHistorySyncedCount(Context context) {
        int iQueryCount = queryCount(context, HISTORY_CONTENT_URI, "(sourceid IS not NULL AND deleted=0)", (String[]) null);
        if (isDebug()) {
            Log.d("GlobalBrowserSyncInfoProvider", "getGlobalBrowserSyncedHistoryCount count = " + iQueryCount);
        }
        return iQueryCount;
    }

    public int getSyncedCount(Context context) {
        int bookmarksSyncedCount = getBookmarksSyncedCount(context);
        int historySyncedCount = getHistorySyncedCount(context);
        if (bookmarksSyncedCount == -1 || historySyncedCount == -1) {
            return -1;
        }
        return bookmarksSyncedCount + historySyncedCount;
    }

    public int getUnsyncedCount(Context context) {
        int bookmarksDirtyCount = getBookmarksDirtyCount(context);
        int historyDirtyCount = getHistoryDirtyCount(context);
        if (bookmarksDirtyCount == -1 || historyDirtyCount == -1) {
            return -1;
        }
        return bookmarksDirtyCount + historyDirtyCount;
    }

    public int getWifiOnlyUnsyncedCount(Context context) {
        return 0;
    }
}

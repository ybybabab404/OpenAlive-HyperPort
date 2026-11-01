package miui.cloud.sync.providers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import miui.cloud.sync.SyncInfoProviderBase;

/* JADX INFO: loaded from: classes2.dex */
public class BluetoothSyncInfoProvider extends SyncInfoProviderBase {
    public static final Uri URI_UNSYNCED = Uri.parse("content://com.android.bluetooth.ble.app.headsetdata.provider/unsynceddata");
    public static final Uri URI_SYNCED = Uri.parse("content://com.android.bluetooth.ble.app.headsetdata.provider/synceddata");

    public final int getBluetoothCount(Context context, Uri uri, String str, String[] strArr) {
        Log.d("BluetoothSyncInfoProvider", "getBluetoothCount, uri: " + uri);
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"id"}, str, strArr, null);
        if (cursorQuery == null) {
            Log.d("BluetoothSyncInfoProvider", "queryDirtyCount: cursor is null");
            return 0;
        }
        try {
            int count = cursorQuery.getCount();
            cursorQuery.close();
            Log.d("BluetoothSyncInfoProvider", "queryDirtyCount = " + count);
            return count;
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    public int getSyncedCount(Context context) {
        return getBluetoothCount(context, URI_SYNCED, null, null);
    }

    public int getUnsyncedCount(Context context) {
        return getBluetoothCount(context, URI_UNSYNCED, null, null);
    }

    public int getWifiOnlyUnsyncedCount(Context context) {
        return 0;
    }
}

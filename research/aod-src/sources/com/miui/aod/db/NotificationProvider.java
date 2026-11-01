package com.miui.aod.db;

import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.notification.NotificationController;
import com.miui.maml.FancyDrawable;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationProvider {
    public static final UriMatcher mMatcher;
    public static Uri URI_NOTIFICATION = Uri.parse("content://aod.notification/notifications");
    public static final String[] COLUMN_NAME = {"icon", "pkg", "title"};

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        mMatcher = uriMatcher;
        uriMatcher.addURI("aod.notification", "notifications", 1);
    }

    public static byte[] drawableToByte(Drawable drawable) {
        Drawable.ConstantState constantState;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof FancyDrawable) {
            FancyDrawable fancyDrawable = (FancyDrawable) drawable;
            Drawable quietDrawable = fancyDrawable.getQuietDrawable();
            if (quietDrawable == null) {
                fancyDrawable.getRoot().tick(SystemClock.elapsedRealtime());
            } else {
                drawable = quietDrawable;
            }
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return null;
        }
        if ((drawable instanceof AdaptiveIconDrawable) && (constantState = drawable.getConstantState()) != null) {
            drawable = constantState.newDrawable();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String getTableName(Uri uri) {
        if (1 == mMatcher.match(uri)) {
            return "notifications";
        }
        return null;
    }

    public static Cursor query(Uri uri) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            return null;
        }
        Log.i("NotificationProvider", "query: " + uri + " " + tableName);
        MatrixCursor matrixCursor = new MatrixCursor(COLUMN_NAME);
        List<NotificationController.NotificationData> notificationList = NotificationController.getInstance(AODApplication.sysuiContext).getNotificationList();
        HashSet hashSet = new HashSet();
        ArrayList<NotificationController.NotificationData> arrayList = new ArrayList();
        for (NotificationController.NotificationData notificationData : notificationList) {
            if (!notificationData.isForegroundService() && !hashSet.contains(notificationData.mPackageName) && hashSet.size() < 5) {
                arrayList.add(notificationData);
                hashSet.add(notificationData.mPackageName);
            }
        }
        for (NotificationController.NotificationData notificationData2 : arrayList) {
            matrixCursor.addRow(new Object[]{drawableToByte(notificationData2.mIconDrawable), notificationData2.mPackageName, notificationData2.mTitle});
        }
        Log.i("NotificationProvider", "query: " + uri + " " + tableName + " " + matrixCursor.getCount());
        return matrixCursor;
    }
}

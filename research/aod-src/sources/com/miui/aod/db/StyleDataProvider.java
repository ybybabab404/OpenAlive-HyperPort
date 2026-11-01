package com.miui.aod.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class StyleDataProvider extends ContentProvider {
    public static final UriMatcher mMatcher;
    public ContentResolver mContentResolver;
    public HistoryDbController mHistoryDbController;
    public ThemeDbController mThemeDbController;

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        mMatcher = uriMatcher;
        uriMatcher.addURI("com.miui.aod.StyleDataProvider", "theme", 1);
        uriMatcher.addURI("com.miui.aod.StyleDataProvider", "history", 2);
    }

    public static String[] createHistoryIdArray(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = String.valueOf(((HistoryEntity) list.get(i)).getId());
        }
        return strArr;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            return 0;
        }
        if ("theme.db".equals(tableName)) {
            if (str == null) {
                this.mThemeDbController.deleteAll();
                this.mContentResolver.notifyChange(uri, null);
            } else if ("id".equals(str) && strArr != null && strArr.length > 0) {
                for (String str2 : strArr) {
                    this.mThemeDbController.delete(str2);
                }
                this.mContentResolver.notifyChange(uri, null);
            }
        } else if ("history.db".equals(tableName)) {
            if (str == null) {
                this.mHistoryDbController.deleteAll();
                this.mContentResolver.notifyChange(uri, null);
            } else if ("id".equals(str) && strArr != null && strArr.length > 0) {
                for (String str3 : strArr) {
                    this.mHistoryDbController.delete(Long.parseLong(str3));
                }
                this.mContentResolver.notifyChange(uri, null);
            } else if ("cateName".equals(str) && strArr != null && strArr.length > 0) {
                for (String str4 : strArr) {
                    this.mHistoryDbController.delete(str4);
                }
                this.mContentResolver.notifyChange(uri, null);
            }
        }
        return 0;
    }

    public final String getTableName(Uri uri) {
        int iMatch = mMatcher.match(uri);
        if (iMatch == 1) {
            return "theme.db";
        }
        if (iMatch != 2) {
            return null;
        }
        return "history.db";
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        String tableName = getTableName(uri);
        if (tableName == null || contentValues == null) {
            Log.i("StyleDataProvider", "insert: tableName == null || values == null");
            return null;
        }
        if ("theme.db".equals(tableName)) {
            this.mThemeDbController.insertOrReplace(ThemeEntity.translateToThemeEntity(contentValues));
            this.mContentResolver.notifyChange(uri, null);
            return uri;
        }
        if ("history.db".equals(tableName)) {
            this.mHistoryDbController.insert(HistoryEntity.translateToThemeEntity(contentValues));
            this.mContentResolver.notifyChange(uri, null);
        }
        return uri;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mThemeDbController = ThemeDbController.getInstance(getContext());
        this.mHistoryDbController = HistoryDbController.getInstance(getContext());
        this.mContentResolver = getContext().getContentResolver();
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            return null;
        }
        if ("theme.db".equals(tableName)) {
            return (strArr2 == null || strArr2.length <= 0) ? this.mThemeDbController.getAllThemesCursorByIdentity(null) : this.mThemeDbController.getAllThemesCursorByIdentity(strArr2[0]);
        }
        if ("history.db".equals(tableName)) {
            return (!"cateName".equals(str) || strArr2 == null || strArr2.length <= 0) ? this.mHistoryDbController.getAllHistoryCursor("asc".equals(str2)) : this.mHistoryDbController.getAllHistoryCursorWithCateName(strArr2[0], "asc".equals(str2));
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        String tableName = getTableName(uri);
        if (tableName != null && "theme.db".equals(tableName)) {
            insert(uri, contentValues);
        }
        return 0;
    }
}

package com.miui.aod.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.xiaomi.onetrack.api.bf;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

/* JADX INFO: loaded from: classes.dex */
public class AodOpenHelper extends DaoMaster.OpenHelper {
    public static final Map TABLE_SCHEMAS;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        TABLE_SCHEMAS = linkedHashMap;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.put("TIMESTAMP", "INTEGER");
        linkedHashMap2.put("CATE_NAME", "TEXT");
        linkedHashMap2.put("JSON_PARAMETER", "TEXT");
        linkedHashMap.put(HistoryEntityDao.TABLENAME, linkedHashMap2);
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        linkedHashMap3.put("NAME", "TEXT");
        linkedHashMap3.put("TYPE_NAME", "TEXT");
        linkedHashMap3.put("JSON_PARAMETER", "TEXT");
        linkedHashMap3.put("THEME_DIR_NAME", "TEXT");
        linkedHashMap3.put("THEME_NAME", "TEXT");
        linkedHashMap3.put("THEME_DESCRIPTION", "TEXT");
        linkedHashMap.put(ThemeEntityDao.TABLENAME, linkedHashMap3);
    }

    public AodOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory) {
        super(context, str, cursorFactory);
    }

    public static Set getTableColumns(Database database, String str) {
        Cursor cursorRawQuery;
        HashSet hashSet = new HashSet();
        Cursor cursor = null;
        try {
            try {
                if (database instanceof StandardDatabase) {
                    cursorRawQuery = ((StandardDatabase) database).getSQLiteDatabase().rawQuery("PRAGMA table_info(\"" + str + "\")", null);
                } else {
                    cursorRawQuery = database.rawQuery("PRAGMA table_info(\"" + str + "\")", null);
                }
                cursor = cursorRawQuery;
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        hashSet.add(cursor.getString(cursor.getColumnIndexOrThrow(bf.a)));
                    }
                }
                if (cursor != null) {
                    return hashSet;
                }
            } catch (Exception e) {
                Log.w("AodOpenHelper", "Error getting table columns: " + e.getMessage());
            }
            return hashSet;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public final void dropAllTablesSafe(Database database) {
        try {
            HistoryEntityDao.dropTable(database, true);
        } catch (Exception e) {
            Log.w("AodOpenHelper", "Failed to drop HISTORY_ENTITY: " + e.getMessage());
        }
        try {
            ThemeEntityDao.dropTable(database, true);
        } catch (Exception e2) {
            Log.w("AodOpenHelper", "Failed to drop THEME_ENTITY: " + e2.getMessage());
        }
    }

    @Override // com.miui.aod.db.DaoMaster.OpenHelper, org.greenrobot.greendao.database.DatabaseOpenHelper
    public void onCreate(Database database) {
        try {
            Log.i("AodOpenHelper", "Creating tables for the first time");
            super.onCreate(database);
        } catch (Exception e) {
            Log.e("AodOpenHelper", "onCreate failed: " + e.getMessage(), e);
            try {
                dropAllTablesSafe(database);
                super.onCreate(database);
            } catch (Exception e2) {
                Log.e("AodOpenHelper", "Fallback onCreate also failed: " + e2.getMessage(), e2);
            }
        }
    }

    @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
    public void onUpgrade(Database database, int i, int i2) {
        Log.i("AodOpenHelper", "Upgrading schema from version " + i + " to " + i2 + " without dropping tables");
        try {
            DaoMaster.createAllTables(database, true);
        } catch (Exception e) {
            Log.e("AodOpenHelper", "createAllTables failed: " + e.getMessage(), e);
        }
        try {
            for (Map.Entry entry : TABLE_SCHEMAS.entrySet()) {
                String str = (String) entry.getKey();
                try {
                    upgradeTable(database, str, (Map) entry.getValue());
                } catch (Exception e2) {
                    Log.e("AodOpenHelper", "Failed to upgrade table " + str + ": " + e2.getMessage(), e2);
                }
            }
        } catch (Exception e3) {
            Log.e("AodOpenHelper", "onUpgrade outer loop failed: " + e3.getMessage(), e3);
        }
    }

    public final void upgradeTable(Database database, String str, Map map) {
        Set tableColumns = getTableColumns(database, str);
        if (tableColumns.isEmpty()) {
            Log.w("AodOpenHelper", "Table " + str + " does not exist or could not read columns, skip upgrade");
            return;
        }
        Log.i("AodOpenHelper", "Table " + str + " existing columns: " + tableColumns);
        for (Map.Entry entry : map.entrySet()) {
            String str2 = (String) entry.getKey();
            String str3 = (String) entry.getValue();
            if (!tableColumns.contains(str2)) {
                String str4 = "ALTER TABLE \"" + str + "\" ADD COLUMN \"" + str2 + "\" " + str3;
                Log.i("AodOpenHelper", "Executing migration: " + str4);
                try {
                    database.execSQL(str4);
                } catch (Exception e) {
                    Log.e("AodOpenHelper", "Failed to add column " + str2 + " to table " + str + ": " + e.getMessage(), e);
                }
            }
        }
    }
}

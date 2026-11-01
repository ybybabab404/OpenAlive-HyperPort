package com.xiaomi.onetrack.h.b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: classes2.dex */
public class a extends SQLiteOpenHelper {
    public static final String a = "app_stats";
    public static final String b = "event_stats";
    public static final String c = "_id";
    public static final String d = "app_id";
    public static final String e = "date";
    public static final String f = "stage";
    public static final String g = "reason";
    public static final String h = "count";
    public static final String i = "event";
    private static final String j = "OTMonitorDBHelper";
    private static final String k = "universal_ot_monitor_db";
    private static final int l = 1;
    private static final String m = "CREATE TABLE app_stats (_id INTEGER PRIMARY KEY AUTOINCREMENT,app_id TEXT,date INTEGER,stage TEXT,reason TEXT,count INTEGER)";
    private static final String n = "CREATE TABLE event_stats (_id INTEGER PRIMARY KEY AUTOINCREMENT,app_id TEXT,date INTEGER,event TEXT,count INTEGER)";

    public a(Context context) {
        super(context, k, (SQLiteDatabase.CursorFactory) null, 1);
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(m);
        sQLiteDatabase.execSQL(n);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            return;
        }
        a(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }
}

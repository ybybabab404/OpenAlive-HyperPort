package com.miui.aod.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import org.greenrobot.greendao.query.WhereCondition;

/* JADX INFO: loaded from: classes.dex */
public class ThemeDbController {
    public static volatile ThemeDbController mDbController;
    public final Context context;
    public AodOpenHelper mHelper;
    public final ThemeEntityDao mThemeEntityDao = new DaoMaster(getWritableDatabase()).newSession().getThemeEntityDao();

    public ThemeDbController(Context context) {
        this.context = context;
        this.mHelper = new AodOpenHelper(context, "theme.db", null);
    }

    public static ThemeDbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (ThemeDbController.class) {
                try {
                    if (mDbController == null) {
                        mDbController = new ThemeDbController(context.getApplicationContext());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return mDbController;
    }

    public synchronized void delete(ThemeEntity themeEntity) {
        this.mThemeEntityDao.delete(themeEntity);
    }

    public synchronized void delete(String str) {
        ThemeEntity themeEntitySearchByIdentity = searchByIdentity(str);
        if (themeEntitySearchByIdentity != null) {
            delete(themeEntitySearchByIdentity);
        }
    }

    public synchronized void deleteAll() {
        this.mThemeEntityDao.deleteAll();
    }

    public synchronized Cursor getAllThemesCursorByIdentity(String str) {
        if (TextUtils.isEmpty(str)) {
            return this.mThemeEntityDao.queryBuilder().buildCursor().query();
        }
        return this.mThemeEntityDao.queryBuilder().where(ThemeEntityDao.Properties.Name.eq(str), new WhereCondition[0]).buildCursor().query();
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (this.mHelper == null) {
            this.mHelper = new AodOpenHelper(this.context, "theme.db", null);
        }
        return this.mHelper.getWritableDatabase();
    }

    public synchronized void insertOrReplace(ThemeEntity themeEntity) {
        this.mThemeEntityDao.insertOrReplace(themeEntity);
    }

    public synchronized ThemeEntity parseThemeEntity(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        return this.mThemeEntityDao.readEntity(cursor, 0);
    }

    public synchronized ThemeEntity searchByIdentity(String str) {
        return this.mThemeEntityDao.queryBuilder().where(ThemeEntityDao.Properties.Name.eq(str), new WhereCondition[0]).build().unique();
    }
}

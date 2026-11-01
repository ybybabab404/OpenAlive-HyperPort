package com.miui.aod.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* JADX INFO: loaded from: classes.dex */
public class HistoryDbController {
    public static volatile HistoryDbController mDbController;
    public final Context context;
    public AodOpenHelper mHelper;
    public final HistoryEntityDao mHistoryEntityDao = new DaoMaster(getWritableDatabase()).newSession().getHistoryEntityDao();

    public HistoryDbController(Context context) {
        this.context = context;
        this.mHelper = new AodOpenHelper(context, "history.db", null);
    }

    public static HistoryDbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (HistoryDbController.class) {
                try {
                    if (mDbController == null) {
                        mDbController = new HistoryDbController(context.getApplicationContext());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return mDbController;
    }

    public synchronized void delete(long j) {
        this.mHistoryEntityDao.deleteByKey(Long.valueOf(j));
    }

    public synchronized void delete(String str) {
        Cursor allHistoryCursorWithCateName = getAllHistoryCursorWithCateName(str, false);
        while (allHistoryCursorWithCateName.moveToNext()) {
            this.mHistoryEntityDao.delete(parseHistoryEntity(allHistoryCursorWithCateName));
        }
    }

    public synchronized void deleteAll() {
        this.mHistoryEntityDao.deleteAll();
    }

    public synchronized Cursor getAllHistoryCursor(boolean z) {
        QueryBuilder<HistoryEntity> queryBuilder;
        try {
            queryBuilder = this.mHistoryEntityDao.queryBuilder();
            if (z) {
                queryBuilder.orderAsc(HistoryEntityDao.Properties.TimeStamp);
            } else {
                queryBuilder.orderDesc(HistoryEntityDao.Properties.TimeStamp);
            }
        } catch (Throwable th) {
            throw th;
        }
        return queryBuilder.buildCursor().query();
    }

    public synchronized Cursor getAllHistoryCursorWithCateName(String str, boolean z) {
        QueryBuilder<HistoryEntity> queryBuilderWhere;
        try {
            queryBuilderWhere = this.mHistoryEntityDao.queryBuilder().where(HistoryEntityDao.Properties.CateName.eq(str), new WhereCondition[0]);
            if (z) {
                queryBuilderWhere.orderAsc(HistoryEntityDao.Properties.TimeStamp);
            } else {
                queryBuilderWhere.orderDesc(HistoryEntityDao.Properties.TimeStamp);
            }
        } catch (Throwable th) {
            throw th;
        }
        return queryBuilderWhere.buildCursor().query();
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (this.mHelper == null) {
            this.mHelper = new AodOpenHelper(this.context, "history.db", null);
        }
        return this.mHelper.getWritableDatabase();
    }

    public synchronized void insert(HistoryEntity historyEntity) {
        try {
            if (historyEntity.getTimeStamp().longValue() <= 0) {
                historyEntity.setTimeStamp(Long.valueOf(System.currentTimeMillis()));
            }
            this.mHistoryEntityDao.insert(historyEntity);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized HistoryEntity parseHistoryEntity(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        return this.mHistoryEntityDao.readEntity(cursor, 0);
    }
}

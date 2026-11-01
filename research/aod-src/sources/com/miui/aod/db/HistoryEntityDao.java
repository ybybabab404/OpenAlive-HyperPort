package com.miui.aod.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class HistoryEntityDao extends AbstractDao<HistoryEntity, Long> {
    public static final String TABLENAME = "HISTORY_ENTITY";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property TimeStamp = new Property(1, Long.class, "timeStamp", false, "TIMESTAMP");
        public static final Property CateName = new Property(2, String.class, "cateName", false, "CATE_NAME");
        public static final Property JsonParameter = new Property(3, String.class, "jsonParameter", false, "JSON_PARAMETER");
    }

    public HistoryEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"HISTORY_ENTITY\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"TIMESTAMP\" INTEGER,\"CATE_NAME\" TEXT,\"JSON_PARAMETER\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"HISTORY_ENTITY\"");
        database.execSQL(sb.toString());
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, HistoryEntity historyEntity) {
        sQLiteStatement.clearBindings();
        Long id = historyEntity.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        Long timeStamp = historyEntity.getTimeStamp();
        if (timeStamp != null) {
            sQLiteStatement.bindLong(2, timeStamp.longValue());
        }
        String cateName = historyEntity.getCateName();
        if (cateName != null) {
            sQLiteStatement.bindString(3, cateName);
        }
        String jsonParameter = historyEntity.getJsonParameter();
        if (jsonParameter != null) {
            sQLiteStatement.bindString(4, jsonParameter);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, HistoryEntity historyEntity) {
        databaseStatement.clearBindings();
        Long id = historyEntity.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        Long timeStamp = historyEntity.getTimeStamp();
        if (timeStamp != null) {
            databaseStatement.bindLong(2, timeStamp.longValue());
        }
        String cateName = historyEntity.getCateName();
        if (cateName != null) {
            databaseStatement.bindString(3, cateName);
        }
        String jsonParameter = historyEntity.getJsonParameter();
        if (jsonParameter != null) {
            databaseStatement.bindString(4, jsonParameter);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(HistoryEntity historyEntity) {
        if (historyEntity != null) {
            return historyEntity.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(HistoryEntity historyEntity) {
        return historyEntity.getId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public HistoryEntity readEntity(Cursor cursor, int i) {
        Long lValueOf = cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i));
        int i2 = i + 1;
        int i3 = i + 2;
        int i4 = i + 3;
        return new HistoryEntity(lValueOf, cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : cursor.getString(i4));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, HistoryEntity historyEntity, int i) {
        historyEntity.setId(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)));
        int i2 = i + 1;
        historyEntity.setTimeStamp(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 2;
        historyEntity.setCateName(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 3;
        historyEntity.setJsonParameter(cursor.isNull(i4) ? null : cursor.getString(i4));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(HistoryEntity historyEntity, long j) {
        historyEntity.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }
}

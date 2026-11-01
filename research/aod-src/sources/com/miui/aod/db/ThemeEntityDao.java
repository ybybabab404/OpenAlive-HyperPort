package com.miui.aod.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.xiaomi.onetrack.api.bf;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class ThemeEntityDao extends AbstractDao<ThemeEntity, Long> {
    public static final String TABLENAME = "THEME_ENTITY";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property Name = new Property(1, String.class, bf.a, false, "NAME");
        public static final Property TypeName = new Property(2, String.class, "typeName", false, "TYPE_NAME");
        public static final Property JsonParameter = new Property(3, String.class, "jsonParameter", false, "JSON_PARAMETER");
        public static final Property ThemeDirName = new Property(4, String.class, "themeDirName", false, "THEME_DIR_NAME");
        public static final Property ThemeName = new Property(5, String.class, "themeName", false, "THEME_NAME");
        public static final Property ThemeDescription = new Property(6, String.class, "themeDescription", false, "THEME_DESCRIPTION");
    }

    public ThemeEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        String str = z ? "IF NOT EXISTS " : "";
        database.execSQL("CREATE TABLE " + str + "\"THEME_ENTITY\" (\"_id\" INTEGER PRIMARY KEY ,\"NAME\" TEXT,\"TYPE_NAME\" TEXT,\"JSON_PARAMETER\" TEXT,\"THEME_DIR_NAME\" TEXT,\"THEME_NAME\" TEXT,\"THEME_DESCRIPTION\" TEXT);");
        database.execSQL("CREATE UNIQUE INDEX " + str + "IDX_THEME_ENTITY_NAME ON \"THEME_ENTITY\" (\"NAME\" ASC);");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"THEME_ENTITY\"");
        database.execSQL(sb.toString());
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, ThemeEntity themeEntity) {
        sQLiteStatement.clearBindings();
        Long id = themeEntity.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String name = themeEntity.getName();
        if (name != null) {
            sQLiteStatement.bindString(2, name);
        }
        String typeName = themeEntity.getTypeName();
        if (typeName != null) {
            sQLiteStatement.bindString(3, typeName);
        }
        String jsonParameter = themeEntity.getJsonParameter();
        if (jsonParameter != null) {
            sQLiteStatement.bindString(4, jsonParameter);
        }
        String themeDirName = themeEntity.getThemeDirName();
        if (themeDirName != null) {
            sQLiteStatement.bindString(5, themeDirName);
        }
        String themeName = themeEntity.getThemeName();
        if (themeName != null) {
            sQLiteStatement.bindString(6, themeName);
        }
        String themeDescription = themeEntity.getThemeDescription();
        if (themeDescription != null) {
            sQLiteStatement.bindString(7, themeDescription);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, ThemeEntity themeEntity) {
        databaseStatement.clearBindings();
        Long id = themeEntity.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String name = themeEntity.getName();
        if (name != null) {
            databaseStatement.bindString(2, name);
        }
        String typeName = themeEntity.getTypeName();
        if (typeName != null) {
            databaseStatement.bindString(3, typeName);
        }
        String jsonParameter = themeEntity.getJsonParameter();
        if (jsonParameter != null) {
            databaseStatement.bindString(4, jsonParameter);
        }
        String themeDirName = themeEntity.getThemeDirName();
        if (themeDirName != null) {
            databaseStatement.bindString(5, themeDirName);
        }
        String themeName = themeEntity.getThemeName();
        if (themeName != null) {
            databaseStatement.bindString(6, themeName);
        }
        String themeDescription = themeEntity.getThemeDescription();
        if (themeDescription != null) {
            databaseStatement.bindString(7, themeDescription);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(ThemeEntity themeEntity) {
        if (themeEntity != null) {
            return themeEntity.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(ThemeEntity themeEntity) {
        return themeEntity.getId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public ThemeEntity readEntity(Cursor cursor, int i) {
        Long lValueOf = cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i));
        int i2 = i + 1;
        int i3 = i + 2;
        int i4 = i + 3;
        int i5 = i + 4;
        int i6 = i + 5;
        int i7 = i + 6;
        return new ThemeEntity(lValueOf, cursor.isNull(i2) ? null : cursor.getString(i2), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : cursor.getString(i4), cursor.isNull(i5) ? null : cursor.getString(i5), cursor.isNull(i6) ? null : cursor.getString(i6), cursor.isNull(i7) ? null : cursor.getString(i7));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, ThemeEntity themeEntity, int i) {
        themeEntity.setId(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)));
        int i2 = i + 1;
        themeEntity.setName(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 2;
        themeEntity.setTypeName(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 3;
        themeEntity.setJsonParameter(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 4;
        themeEntity.setThemeDirName(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 5;
        themeEntity.setThemeName(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 6;
        themeEntity.setThemeDescription(cursor.isNull(i7) ? null : cursor.getString(i7));
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
    public final Long updateKeyAfterInsert(ThemeEntity themeEntity, long j) {
        themeEntity.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }
}

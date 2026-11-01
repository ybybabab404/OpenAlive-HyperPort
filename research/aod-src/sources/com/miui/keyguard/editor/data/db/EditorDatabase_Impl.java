package com.miui.keyguard.editor.data.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public final class EditorDatabase_Impl extends EditorDatabase {
    public volatile PresetTemplateDao _presetTemplateDao;
    public volatile TemplateHistoryDao _templateHistoryDao;

    @Override // androidx.room.RoomDatabase
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "template_history_config", "preset_templates");
    }

    @Override // androidx.room.RoomDatabase
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(8) { // from class: com.miui.keyguard.editor.data.db.EditorDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `template_history_config` (`templateId` TEXT NOT NULL, `configUri` TEXT NOT NULL, `loadingColor` INTEGER NOT NULL, `smallScreenshotUri` TEXT, `largePortraitScreenshotUri` TEXT, `largeLandscapeScreenshotUri` TEXT, `smallWallpaperScreenshotUri` TEXT, `largePortWallpaperScreenshotUri` TEXT, `largeLandWallpaperScreenshotUri` TEXT, `isDualClock` INTEGER NOT NULL, `isThirdPartyWallpaper` INTEGER NOT NULL, `resourceType` TEXT NOT NULL, `originResourcePath` TEXT, `lastModifiedTime` INTEGER NOT NULL, `templateName` TEXT, `isCurrent` INTEGER NOT NULL, `hideFlag` INTEGER NOT NULL, `magicType` INTEGER NOT NULL, `isDarkWallpaper` INTEGER NOT NULL, `isAiGenerated` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `preset_templates` (`templateName` TEXT, `templateId` TEXT, `hideParam` INTEGER NOT NULL, `version` INTEGER NOT NULL, `systemVersion` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'df7897ca983f7b634d36036ae58445d2')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `template_history_config`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `preset_templates`");
                List list = EditorDatabase_Impl.this.mCallbacks;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((RoomDatabase.Callback) it.next()).onDestructiveMigration(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                List list = EditorDatabase_Impl.this.mCallbacks;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((RoomDatabase.Callback) it.next()).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                EditorDatabase_Impl.this.mDatabase = supportSQLiteDatabase;
                EditorDatabase_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                List list = EditorDatabase_Impl.this.mCallbacks;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((RoomDatabase.Callback) it.next()).onOpen(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) throws IOException {
                DBUtil.dropFtsSyncTriggers(supportSQLiteDatabase);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase supportSQLiteDatabase) {
                HashMap map = new HashMap(21);
                map.put("templateId", new TableInfo.Column("templateId", "TEXT", true, 0, null, 1));
                map.put("configUri", new TableInfo.Column("configUri", "TEXT", true, 0, null, 1));
                map.put("loadingColor", new TableInfo.Column("loadingColor", "INTEGER", true, 0, null, 1));
                map.put("smallScreenshotUri", new TableInfo.Column("smallScreenshotUri", "TEXT", false, 0, null, 1));
                map.put("largePortraitScreenshotUri", new TableInfo.Column("largePortraitScreenshotUri", "TEXT", false, 0, null, 1));
                map.put("largeLandscapeScreenshotUri", new TableInfo.Column("largeLandscapeScreenshotUri", "TEXT", false, 0, null, 1));
                map.put("smallWallpaperScreenshotUri", new TableInfo.Column("smallWallpaperScreenshotUri", "TEXT", false, 0, null, 1));
                map.put("largePortWallpaperScreenshotUri", new TableInfo.Column("largePortWallpaperScreenshotUri", "TEXT", false, 0, null, 1));
                map.put("largeLandWallpaperScreenshotUri", new TableInfo.Column("largeLandWallpaperScreenshotUri", "TEXT", false, 0, null, 1));
                map.put("isDualClock", new TableInfo.Column("isDualClock", "INTEGER", true, 0, null, 1));
                map.put("isThirdPartyWallpaper", new TableInfo.Column("isThirdPartyWallpaper", "INTEGER", true, 0, null, 1));
                map.put("resourceType", new TableInfo.Column("resourceType", "TEXT", true, 0, null, 1));
                map.put("originResourcePath", new TableInfo.Column("originResourcePath", "TEXT", false, 0, null, 1));
                map.put("lastModifiedTime", new TableInfo.Column("lastModifiedTime", "INTEGER", true, 0, null, 1));
                map.put("templateName", new TableInfo.Column("templateName", "TEXT", false, 0, null, 1));
                map.put("isCurrent", new TableInfo.Column("isCurrent", "INTEGER", true, 0, null, 1));
                map.put("hideFlag", new TableInfo.Column("hideFlag", "INTEGER", true, 0, null, 1));
                map.put("magicType", new TableInfo.Column("magicType", "INTEGER", true, 0, null, 1));
                map.put("isDarkWallpaper", new TableInfo.Column("isDarkWallpaper", "INTEGER", true, 0, null, 1));
                map.put("isAiGenerated", new TableInfo.Column("isAiGenerated", "INTEGER", true, 0, null, 1));
                map.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                TableInfo tableInfo = new TableInfo("template_history_config", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(supportSQLiteDatabase, "template_history_config");
                if (!tableInfo.equals(tableInfo2)) {
                    return new RoomOpenHelper.ValidationResult(false, "template_history_config(com.miui.keyguard.editor.data.bean.TemplateHistoryConfig).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                HashMap map2 = new HashMap(6);
                map2.put("templateName", new TableInfo.Column("templateName", "TEXT", false, 0, null, 1));
                map2.put("templateId", new TableInfo.Column("templateId", "TEXT", false, 0, null, 1));
                map2.put("hideParam", new TableInfo.Column("hideParam", "INTEGER", true, 0, null, 1));
                map2.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, 1));
                map2.put("systemVersion", new TableInfo.Column("systemVersion", "TEXT", false, 0, null, 1));
                map2.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                TableInfo tableInfo3 = new TableInfo("preset_templates", map2, new HashSet(0), new HashSet(0));
                TableInfo tableInfo4 = TableInfo.read(supportSQLiteDatabase, "preset_templates");
                if (tableInfo3.equals(tableInfo4)) {
                    return new RoomOpenHelper.ValidationResult(true, null);
                }
                return new RoomOpenHelper.ValidationResult(false, "preset_templates(com.miui.keyguard.editor.data.bean.DatabasePresetTemplate).\n Expected:\n" + tableInfo3 + "\n Found:\n" + tableInfo4);
            }
        }, "df7897ca983f7b634d36036ae58445d2", "11897fff48047f29193bdccf67dd0976")).build());
    }

    @Override // androidx.room.RoomDatabase
    public List getAutoMigrations(Map map) {
        return new ArrayList();
    }

    @Override // com.miui.keyguard.editor.data.db.EditorDatabase
    public PresetTemplateDao getPresetTemplateDao() {
        PresetTemplateDao presetTemplateDao;
        if (this._presetTemplateDao != null) {
            return this._presetTemplateDao;
        }
        synchronized (this) {
            try {
                if (this._presetTemplateDao == null) {
                    this._presetTemplateDao = new PresetTemplateDao_Impl(this);
                }
                presetTemplateDao = this._presetTemplateDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return presetTemplateDao;
    }

    @Override // androidx.room.RoomDatabase
    public Set getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override // androidx.room.RoomDatabase
    public Map getRequiredTypeConverters() {
        HashMap map = new HashMap();
        map.put(TemplateHistoryDao.class, TemplateHistoryDao_Impl.getRequiredConverters());
        map.put(PresetTemplateDao.class, PresetTemplateDao_Impl.getRequiredConverters());
        return map;
    }

    @Override // com.miui.keyguard.editor.data.db.EditorDatabase
    public TemplateHistoryDao getTemplateHistoryDao() {
        TemplateHistoryDao templateHistoryDao;
        if (this._templateHistoryDao != null) {
            return this._templateHistoryDao;
        }
        synchronized (this) {
            try {
                if (this._templateHistoryDao == null) {
                    this._templateHistoryDao = new TemplateHistoryDao_Impl(this);
                }
                templateHistoryDao = this._templateHistoryDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return templateHistoryDao;
    }
}

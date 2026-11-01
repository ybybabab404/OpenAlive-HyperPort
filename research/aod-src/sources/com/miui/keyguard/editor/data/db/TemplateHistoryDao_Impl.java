package com.miui.keyguard.editor.data.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public final class TemplateHistoryDao_Impl implements TemplateHistoryDao {
    public final RoomDatabase __db;
    public final EntityDeletionOrUpdateAdapter __deletionAdapterOfTemplateHistoryConfig;
    public final EntityInsertionAdapter __insertionAdapterOfTemplateHistoryConfig;
    public final SharedSQLiteStatement __preparedStmtOfClear;
    public final SharedSQLiteStatement __preparedStmtOfDelete;
    public final SharedSQLiteStatement __preparedStmtOfDeleteCurrent;
    public final SharedSQLiteStatement __preparedStmtOfDeleteInValid;

    public TemplateHistoryDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfTemplateHistoryConfig = new EntityInsertionAdapter<TemplateHistoryConfig>(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.TemplateHistoryDao_Impl.1
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, TemplateHistoryConfig templateHistoryConfig) {
                if (templateHistoryConfig.getTemplateId() == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, templateHistoryConfig.getTemplateId());
                }
                if (templateHistoryConfig.getConfigUri() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, templateHistoryConfig.getConfigUri());
                }
                supportSQLiteStatement.bindLong(3, templateHistoryConfig.getLoadingColor());
                if (templateHistoryConfig.getSmallScreenshotUri() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, templateHistoryConfig.getSmallScreenshotUri());
                }
                if (templateHistoryConfig.getLargePortraitScreenshotUri() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, templateHistoryConfig.getLargePortraitScreenshotUri());
                }
                if (templateHistoryConfig.getLargeLandscapeScreenshotUri() == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, templateHistoryConfig.getLargeLandscapeScreenshotUri());
                }
                if (templateHistoryConfig.getSmallWallpaperScreenshotUri() == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindString(7, templateHistoryConfig.getSmallWallpaperScreenshotUri());
                }
                if (templateHistoryConfig.getLargePortWallpaperScreenshotUri() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, templateHistoryConfig.getLargePortWallpaperScreenshotUri());
                }
                if (templateHistoryConfig.getLargeLandWallpaperScreenshotUri() == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, templateHistoryConfig.getLargeLandWallpaperScreenshotUri());
                }
                supportSQLiteStatement.bindLong(10, templateHistoryConfig.isDualClock() ? 1L : 0L);
                supportSQLiteStatement.bindLong(11, templateHistoryConfig.isThirdPartyWallpaper() ? 1L : 0L);
                if (templateHistoryConfig.getResourceType() == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindString(12, templateHistoryConfig.getResourceType());
                }
                if (templateHistoryConfig.getOriginResourcePath() == null) {
                    supportSQLiteStatement.bindNull(13);
                } else {
                    supportSQLiteStatement.bindString(13, templateHistoryConfig.getOriginResourcePath());
                }
                supportSQLiteStatement.bindLong(14, templateHistoryConfig.getLastModifiedTime());
                if (templateHistoryConfig.getTemplateName() == null) {
                    supportSQLiteStatement.bindNull(15);
                } else {
                    supportSQLiteStatement.bindString(15, templateHistoryConfig.getTemplateName());
                }
                supportSQLiteStatement.bindLong(16, templateHistoryConfig.isCurrent());
                supportSQLiteStatement.bindLong(17, templateHistoryConfig.getHideFlag());
                supportSQLiteStatement.bindLong(18, templateHistoryConfig.getMagicType());
                supportSQLiteStatement.bindLong(19, templateHistoryConfig.isDarkWallpaper());
                supportSQLiteStatement.bindLong(20, templateHistoryConfig.isAiGenerated() ? 1L : 0L);
                supportSQLiteStatement.bindLong(21, templateHistoryConfig.getId());
            }

            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `template_history_config` (`templateId`,`configUri`,`loadingColor`,`smallScreenshotUri`,`largePortraitScreenshotUri`,`largeLandscapeScreenshotUri`,`smallWallpaperScreenshotUri`,`largePortWallpaperScreenshotUri`,`largeLandWallpaperScreenshotUri`,`isDualClock`,`isThirdPartyWallpaper`,`resourceType`,`originResourcePath`,`lastModifiedTime`,`templateName`,`isCurrent`,`hideFlag`,`magicType`,`isDarkWallpaper`,`isAiGenerated`,`id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,nullif(?, 0))";
            }
        };
        this.__deletionAdapterOfTemplateHistoryConfig = new EntityDeletionOrUpdateAdapter<TemplateHistoryConfig>(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.TemplateHistoryDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, TemplateHistoryConfig templateHistoryConfig) {
                supportSQLiteStatement.bindLong(1, templateHistoryConfig.getId());
            }

            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM `template_history_config` WHERE `id` = ?";
            }
        };
        this.__preparedStmtOfDelete = new SharedSQLiteStatement(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.TemplateHistoryDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM template_history_config WHERE id == ?";
            }
        };
        this.__preparedStmtOfClear = new SharedSQLiteStatement(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.TemplateHistoryDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM template_history_config";
            }
        };
        this.__preparedStmtOfDeleteCurrent = new SharedSQLiteStatement(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.TemplateHistoryDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM template_history_config WHERE isCurrent = 1";
            }
        };
        this.__preparedStmtOfDeleteInValid = new SharedSQLiteStatement(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.TemplateHistoryDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM template_history_config WHERE (CASE    WHEN (SELECT EXISTS(SELECT * FROM preset_templates))      THEN isCurrent = 0            AND            templateId                NOT IN                    (SELECT templateName FROM preset_templates                        UNION                    SELECT templateId FROM preset_templates)   else 1 =0 END)";
            }
        };
    }

    public static List getRequiredConverters() {
        return Collections.EMPTY_LIST;
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public void addOrReplace(TemplateHistoryConfig templateHistoryConfig) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfTemplateHistoryConfig.insert(templateHistoryConfig);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public void clear() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfClear.acquire();
        try {
            this.__db.beginTransaction();
            try {
                supportSQLiteStatementAcquire.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
                this.__db.endTransaction();
                this.__preparedStmtOfClear.release(supportSQLiteStatementAcquire);
            } catch (Throwable th) {
                this.__db.endTransaction();
                throw th;
            }
        } catch (Throwable th2) {
            this.__preparedStmtOfClear.release(supportSQLiteStatementAcquire);
            throw th2;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public void delete(long j) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDelete.acquire();
        supportSQLiteStatementAcquire.bindLong(1, j);
        try {
            this.__db.beginTransaction();
            try {
                supportSQLiteStatementAcquire.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
                this.__db.endTransaction();
                this.__preparedStmtOfDelete.release(supportSQLiteStatementAcquire);
            } catch (Throwable th) {
                this.__db.endTransaction();
                throw th;
            }
        } catch (Throwable th2) {
            this.__preparedStmtOfDelete.release(supportSQLiteStatementAcquire);
            throw th2;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public void delete(TemplateHistoryConfig templateHistoryConfig) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfTemplateHistoryConfig.handle(templateHistoryConfig);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public void deleteAndUpdate(TemplateHistoryConfig templateHistoryConfig) {
        this.__db.beginTransaction();
        try {
            TemplateHistoryDao.DefaultImpls.deleteAndUpdate(this, templateHistoryConfig);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public void deleteCurrent() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteCurrent.acquire();
        try {
            this.__db.beginTransaction();
            try {
                supportSQLiteStatementAcquire.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
                this.__db.endTransaction();
                this.__preparedStmtOfDeleteCurrent.release(supportSQLiteStatementAcquire);
            } catch (Throwable th) {
                this.__db.endTransaction();
                throw th;
            }
        } catch (Throwable th2) {
            this.__preparedStmtOfDeleteCurrent.release(supportSQLiteStatementAcquire);
            throw th2;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public void deleteInValid() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteInValid.acquire();
        try {
            this.__db.beginTransaction();
            try {
                supportSQLiteStatementAcquire.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
                this.__db.endTransaction();
                this.__preparedStmtOfDeleteInValid.release(supportSQLiteStatementAcquire);
            } catch (Throwable th) {
                this.__db.endTransaction();
                throw th;
            }
        } catch (Throwable th2) {
            this.__preparedStmtOfDeleteInValid.release(supportSQLiteStatementAcquire);
            throw th2;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public TemplateHistoryConfig getCurrent() throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        TemplateHistoryConfig templateHistoryConfig;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM template_history_config WHERE isCurrent = 1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "configUri");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "loadingColor");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallScreenshotUri");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortraitScreenshotUri");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandscapeScreenshotUri");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallWallpaperScreenshotUri");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortWallpaperScreenshotUri");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandWallpaperScreenshotUri");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDualClock");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isThirdPartyWallpaper");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "resourceType");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "originResourcePath");
            int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "lastModifiedTime");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateName");
                int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isCurrent");
                int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "hideFlag");
                int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "magicType");
                int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDarkWallpaper");
                int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isAiGenerated");
                int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                if (cursorQuery.moveToFirst()) {
                    templateHistoryConfig = new TemplateHistoryConfig(cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.getInt(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5), cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6), cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7), cursorQuery.isNull(columnIndexOrThrow8) ? null : cursorQuery.getString(columnIndexOrThrow8), cursorQuery.isNull(columnIndexOrThrow9) ? null : cursorQuery.getString(columnIndexOrThrow9), cursorQuery.getInt(columnIndexOrThrow10) != 0, cursorQuery.getInt(columnIndexOrThrow11) != 0, cursorQuery.isNull(columnIndexOrThrow12) ? null : cursorQuery.getString(columnIndexOrThrow12), cursorQuery.isNull(columnIndexOrThrow13) ? null : cursorQuery.getString(columnIndexOrThrow13), cursorQuery.getLong(columnIndexOrThrow14), cursorQuery.isNull(columnIndexOrThrow15) ? null : cursorQuery.getString(columnIndexOrThrow15), cursorQuery.getInt(columnIndexOrThrow16), cursorQuery.getInt(columnIndexOrThrow17), cursorQuery.getInt(columnIndexOrThrow18), cursorQuery.getInt(columnIndexOrThrow19), cursorQuery.getInt(columnIndexOrThrow20) != 0);
                    templateHistoryConfig.setId(cursorQuery.getLong(columnIndexOrThrow21));
                } else {
                    templateHistoryConfig = null;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return templateHistoryConfig;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public int getHistoryCount() {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT COUNT(*) FROM template_history_config WHERE isCurrent = 0", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            return cursorQuery.moveToFirst() ? cursorQuery.getInt(0) : 0;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public List getInvalidList() throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM template_history_config WHERE isCurrent = 0    AND templateId    NOT IN (SELECT templateName FROM preset_templates    UNION SELECT templateId FROM preset_templates )", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "configUri");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "loadingColor");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallScreenshotUri");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortraitScreenshotUri");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandscapeScreenshotUri");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallWallpaperScreenshotUri");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortWallpaperScreenshotUri");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandWallpaperScreenshotUri");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDualClock");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isThirdPartyWallpaper");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "resourceType");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "originResourcePath");
            int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "lastModifiedTime");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateName");
                int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isCurrent");
                int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "hideFlag");
                int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "magicType");
                int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDarkWallpaper");
                int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isAiGenerated");
                int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                int i = columnIndexOrThrow14;
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                    String string2 = cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2);
                    int i2 = cursorQuery.getInt(columnIndexOrThrow3);
                    String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                    String string4 = cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5);
                    String string5 = cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6);
                    String string6 = cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7);
                    String string7 = cursorQuery.isNull(columnIndexOrThrow8) ? null : cursorQuery.getString(columnIndexOrThrow8);
                    String string8 = cursorQuery.isNull(columnIndexOrThrow9) ? null : cursorQuery.getString(columnIndexOrThrow9);
                    boolean z = cursorQuery.getInt(columnIndexOrThrow10) != 0;
                    boolean z2 = cursorQuery.getInt(columnIndexOrThrow11) != 0;
                    String string9 = cursorQuery.isNull(columnIndexOrThrow12) ? null : cursorQuery.getString(columnIndexOrThrow12);
                    String string10 = cursorQuery.isNull(columnIndexOrThrow13) ? null : cursorQuery.getString(columnIndexOrThrow13);
                    int i3 = i;
                    long j = cursorQuery.getLong(i3);
                    int i4 = columnIndexOrThrow;
                    int i5 = columnIndexOrThrow15;
                    String string11 = cursorQuery.isNull(i5) ? null : cursorQuery.getString(i5);
                    columnIndexOrThrow15 = i5;
                    int i6 = columnIndexOrThrow16;
                    int i7 = cursorQuery.getInt(i6);
                    columnIndexOrThrow16 = i6;
                    int i8 = columnIndexOrThrow17;
                    int i9 = cursorQuery.getInt(i8);
                    columnIndexOrThrow17 = i8;
                    int i10 = columnIndexOrThrow18;
                    int i11 = cursorQuery.getInt(i10);
                    columnIndexOrThrow18 = i10;
                    int i12 = columnIndexOrThrow19;
                    int i13 = cursorQuery.getInt(i12);
                    columnIndexOrThrow19 = i12;
                    int i14 = columnIndexOrThrow20;
                    TemplateHistoryConfig templateHistoryConfig = new TemplateHistoryConfig(string, string2, i2, string3, string4, string5, string6, string7, string8, z, z2, string9, string10, j, string11, i7, i9, i11, i13, cursorQuery.getInt(i14) != 0);
                    columnIndexOrThrow20 = i14;
                    int i15 = columnIndexOrThrow13;
                    int i16 = columnIndexOrThrow3;
                    int i17 = columnIndexOrThrow21;
                    int i18 = columnIndexOrThrow2;
                    templateHistoryConfig.setId(cursorQuery.getLong(i17));
                    arrayList.add(templateHistoryConfig);
                    columnIndexOrThrow2 = i18;
                    columnIndexOrThrow = i4;
                    columnIndexOrThrow3 = i16;
                    columnIndexOrThrow21 = i17;
                    i = i3;
                    columnIndexOrThrow13 = i15;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public List getOldestHistory(int i) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM template_history_config WHERE isCurrent = 0 ORDER BY lastModifiedTime ASC, id ASC LIMIT ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, i);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "configUri");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "loadingColor");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallScreenshotUri");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortraitScreenshotUri");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandscapeScreenshotUri");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallWallpaperScreenshotUri");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortWallpaperScreenshotUri");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandWallpaperScreenshotUri");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDualClock");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isThirdPartyWallpaper");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "resourceType");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "originResourcePath");
            int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "lastModifiedTime");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateName");
                int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isCurrent");
                int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "hideFlag");
                int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "magicType");
                int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDarkWallpaper");
                int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isAiGenerated");
                int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                int i2 = columnIndexOrThrow14;
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                    String string2 = cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2);
                    int i3 = cursorQuery.getInt(columnIndexOrThrow3);
                    String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                    String string4 = cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5);
                    String string5 = cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6);
                    String string6 = cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7);
                    String string7 = cursorQuery.isNull(columnIndexOrThrow8) ? null : cursorQuery.getString(columnIndexOrThrow8);
                    String string8 = cursorQuery.isNull(columnIndexOrThrow9) ? null : cursorQuery.getString(columnIndexOrThrow9);
                    boolean z = cursorQuery.getInt(columnIndexOrThrow10) != 0;
                    boolean z2 = cursorQuery.getInt(columnIndexOrThrow11) != 0;
                    String string9 = cursorQuery.isNull(columnIndexOrThrow12) ? null : cursorQuery.getString(columnIndexOrThrow12);
                    String string10 = cursorQuery.isNull(columnIndexOrThrow13) ? null : cursorQuery.getString(columnIndexOrThrow13);
                    int i4 = i2;
                    long j = cursorQuery.getLong(i4);
                    int i5 = columnIndexOrThrow;
                    int i6 = columnIndexOrThrow15;
                    String string11 = cursorQuery.isNull(i6) ? null : cursorQuery.getString(i6);
                    columnIndexOrThrow15 = i6;
                    int i7 = columnIndexOrThrow16;
                    int i8 = cursorQuery.getInt(i7);
                    columnIndexOrThrow16 = i7;
                    int i9 = columnIndexOrThrow17;
                    int i10 = cursorQuery.getInt(i9);
                    columnIndexOrThrow17 = i9;
                    int i11 = columnIndexOrThrow18;
                    int i12 = cursorQuery.getInt(i11);
                    columnIndexOrThrow18 = i11;
                    int i13 = columnIndexOrThrow19;
                    int i14 = cursorQuery.getInt(i13);
                    columnIndexOrThrow19 = i13;
                    int i15 = columnIndexOrThrow20;
                    TemplateHistoryConfig templateHistoryConfig = new TemplateHistoryConfig(string, string2, i3, string3, string4, string5, string6, string7, string8, z, z2, string9, string10, j, string11, i8, i10, i12, i14, cursorQuery.getInt(i15) != 0);
                    columnIndexOrThrow20 = i15;
                    int i16 = columnIndexOrThrow12;
                    int i17 = columnIndexOrThrow21;
                    int i18 = columnIndexOrThrow13;
                    templateHistoryConfig.setId(cursorQuery.getLong(i17));
                    arrayList.add(templateHistoryConfig);
                    columnIndexOrThrow13 = i18;
                    columnIndexOrThrow = i5;
                    i2 = i4;
                    columnIndexOrThrow21 = i17;
                    columnIndexOrThrow12 = i16;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public TemplateHistoryConfig query(long j) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        TemplateHistoryConfig templateHistoryConfig;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM template_history_config WHERE id == ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, j);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "configUri");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "loadingColor");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallScreenshotUri");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortraitScreenshotUri");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandscapeScreenshotUri");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallWallpaperScreenshotUri");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortWallpaperScreenshotUri");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandWallpaperScreenshotUri");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDualClock");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isThirdPartyWallpaper");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "resourceType");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "originResourcePath");
            int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "lastModifiedTime");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateName");
                int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isCurrent");
                int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "hideFlag");
                int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "magicType");
                int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDarkWallpaper");
                int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isAiGenerated");
                int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                if (cursorQuery.moveToFirst()) {
                    templateHistoryConfig = new TemplateHistoryConfig(cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.getInt(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5), cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6), cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7), cursorQuery.isNull(columnIndexOrThrow8) ? null : cursorQuery.getString(columnIndexOrThrow8), cursorQuery.isNull(columnIndexOrThrow9) ? null : cursorQuery.getString(columnIndexOrThrow9), cursorQuery.getInt(columnIndexOrThrow10) != 0, cursorQuery.getInt(columnIndexOrThrow11) != 0, cursorQuery.isNull(columnIndexOrThrow12) ? null : cursorQuery.getString(columnIndexOrThrow12), cursorQuery.isNull(columnIndexOrThrow13) ? null : cursorQuery.getString(columnIndexOrThrow13), cursorQuery.getLong(columnIndexOrThrow14), cursorQuery.isNull(columnIndexOrThrow15) ? null : cursorQuery.getString(columnIndexOrThrow15), cursorQuery.getInt(columnIndexOrThrow16), cursorQuery.getInt(columnIndexOrThrow17), cursorQuery.getInt(columnIndexOrThrow18), cursorQuery.getInt(columnIndexOrThrow19), cursorQuery.getInt(columnIndexOrThrow20) != 0);
                    templateHistoryConfig.setId(cursorQuery.getLong(columnIndexOrThrow21));
                } else {
                    templateHistoryConfig = null;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return templateHistoryConfig;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public List queryAll() throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM template_history_config ORDER BY lastModifiedTime DESC", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "configUri");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "loadingColor");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallScreenshotUri");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortraitScreenshotUri");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandscapeScreenshotUri");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallWallpaperScreenshotUri");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortWallpaperScreenshotUri");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandWallpaperScreenshotUri");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDualClock");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isThirdPartyWallpaper");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "resourceType");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "originResourcePath");
            int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "lastModifiedTime");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateName");
                int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isCurrent");
                int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "hideFlag");
                int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "magicType");
                int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDarkWallpaper");
                int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isAiGenerated");
                int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                int i = columnIndexOrThrow14;
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                    String string2 = cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2);
                    int i2 = cursorQuery.getInt(columnIndexOrThrow3);
                    String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                    String string4 = cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5);
                    String string5 = cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6);
                    String string6 = cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7);
                    String string7 = cursorQuery.isNull(columnIndexOrThrow8) ? null : cursorQuery.getString(columnIndexOrThrow8);
                    String string8 = cursorQuery.isNull(columnIndexOrThrow9) ? null : cursorQuery.getString(columnIndexOrThrow9);
                    boolean z = cursorQuery.getInt(columnIndexOrThrow10) != 0;
                    boolean z2 = cursorQuery.getInt(columnIndexOrThrow11) != 0;
                    String string9 = cursorQuery.isNull(columnIndexOrThrow12) ? null : cursorQuery.getString(columnIndexOrThrow12);
                    String string10 = cursorQuery.isNull(columnIndexOrThrow13) ? null : cursorQuery.getString(columnIndexOrThrow13);
                    int i3 = i;
                    long j = cursorQuery.getLong(i3);
                    int i4 = columnIndexOrThrow;
                    int i5 = columnIndexOrThrow15;
                    String string11 = cursorQuery.isNull(i5) ? null : cursorQuery.getString(i5);
                    columnIndexOrThrow15 = i5;
                    int i6 = columnIndexOrThrow16;
                    int i7 = cursorQuery.getInt(i6);
                    columnIndexOrThrow16 = i6;
                    int i8 = columnIndexOrThrow17;
                    int i9 = cursorQuery.getInt(i8);
                    columnIndexOrThrow17 = i8;
                    int i10 = columnIndexOrThrow18;
                    int i11 = cursorQuery.getInt(i10);
                    columnIndexOrThrow18 = i10;
                    int i12 = columnIndexOrThrow19;
                    int i13 = cursorQuery.getInt(i12);
                    columnIndexOrThrow19 = i12;
                    int i14 = columnIndexOrThrow20;
                    TemplateHistoryConfig templateHistoryConfig = new TemplateHistoryConfig(string, string2, i2, string3, string4, string5, string6, string7, string8, z, z2, string9, string10, j, string11, i7, i9, i11, i13, cursorQuery.getInt(i14) != 0);
                    columnIndexOrThrow20 = i14;
                    int i15 = columnIndexOrThrow13;
                    int i16 = columnIndexOrThrow3;
                    int i17 = columnIndexOrThrow21;
                    int i18 = columnIndexOrThrow2;
                    templateHistoryConfig.setId(cursorQuery.getLong(i17));
                    arrayList.add(templateHistoryConfig);
                    columnIndexOrThrow2 = i18;
                    columnIndexOrThrow = i4;
                    columnIndexOrThrow3 = i16;
                    columnIndexOrThrow21 = i17;
                    i = i3;
                    columnIndexOrThrow13 = i15;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.TemplateHistoryDao
    public List queryAllSupport() throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM template_history_config WHERE isCurrent = 0 ORDER BY lastModifiedTime DESC", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "configUri");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "loadingColor");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallScreenshotUri");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortraitScreenshotUri");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandscapeScreenshotUri");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "smallWallpaperScreenshotUri");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largePortWallpaperScreenshotUri");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "largeLandWallpaperScreenshotUri");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDualClock");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isThirdPartyWallpaper");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "resourceType");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "originResourcePath");
            int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "lastModifiedTime");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "templateName");
                int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isCurrent");
                int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "hideFlag");
                int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "magicType");
                int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isDarkWallpaper");
                int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isAiGenerated");
                int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                int i = columnIndexOrThrow14;
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                    String string2 = cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2);
                    int i2 = cursorQuery.getInt(columnIndexOrThrow3);
                    String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                    String string4 = cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5);
                    String string5 = cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6);
                    String string6 = cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7);
                    String string7 = cursorQuery.isNull(columnIndexOrThrow8) ? null : cursorQuery.getString(columnIndexOrThrow8);
                    String string8 = cursorQuery.isNull(columnIndexOrThrow9) ? null : cursorQuery.getString(columnIndexOrThrow9);
                    boolean z = cursorQuery.getInt(columnIndexOrThrow10) != 0;
                    boolean z2 = cursorQuery.getInt(columnIndexOrThrow11) != 0;
                    String string9 = cursorQuery.isNull(columnIndexOrThrow12) ? null : cursorQuery.getString(columnIndexOrThrow12);
                    String string10 = cursorQuery.isNull(columnIndexOrThrow13) ? null : cursorQuery.getString(columnIndexOrThrow13);
                    int i3 = i;
                    long j = cursorQuery.getLong(i3);
                    int i4 = columnIndexOrThrow;
                    int i5 = columnIndexOrThrow15;
                    String string11 = cursorQuery.isNull(i5) ? null : cursorQuery.getString(i5);
                    columnIndexOrThrow15 = i5;
                    int i6 = columnIndexOrThrow16;
                    int i7 = cursorQuery.getInt(i6);
                    columnIndexOrThrow16 = i6;
                    int i8 = columnIndexOrThrow17;
                    int i9 = cursorQuery.getInt(i8);
                    columnIndexOrThrow17 = i8;
                    int i10 = columnIndexOrThrow18;
                    int i11 = cursorQuery.getInt(i10);
                    columnIndexOrThrow18 = i10;
                    int i12 = columnIndexOrThrow19;
                    int i13 = cursorQuery.getInt(i12);
                    columnIndexOrThrow19 = i12;
                    int i14 = columnIndexOrThrow20;
                    TemplateHistoryConfig templateHistoryConfig = new TemplateHistoryConfig(string, string2, i2, string3, string4, string5, string6, string7, string8, z, z2, string9, string10, j, string11, i7, i9, i11, i13, cursorQuery.getInt(i14) != 0);
                    columnIndexOrThrow20 = i14;
                    int i15 = columnIndexOrThrow13;
                    int i16 = columnIndexOrThrow3;
                    int i17 = columnIndexOrThrow21;
                    int i18 = columnIndexOrThrow2;
                    templateHistoryConfig.setId(cursorQuery.getLong(i17));
                    arrayList.add(templateHistoryConfig);
                    columnIndexOrThrow2 = i18;
                    columnIndexOrThrow = i4;
                    columnIndexOrThrow3 = i16;
                    columnIndexOrThrow21 = i17;
                    i = i3;
                    columnIndexOrThrow13 = i15;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }
}

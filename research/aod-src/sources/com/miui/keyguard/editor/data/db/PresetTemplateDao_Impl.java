package com.miui.keyguard.editor.data.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.miui.keyguard.editor.data.bean.DatabasePresetTemplate;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public final class PresetTemplateDao_Impl implements PresetTemplateDao {
    public final RoomDatabase __db;
    public final EntityInsertionAdapter __insertionAdapterOfDatabasePresetTemplate;
    public final SharedSQLiteStatement __preparedStmtOfDeleteAllData;

    public PresetTemplateDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfDatabasePresetTemplate = new EntityInsertionAdapter<DatabasePresetTemplate>(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.PresetTemplateDao_Impl.1
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, DatabasePresetTemplate databasePresetTemplate) {
                if (databasePresetTemplate.getTemplateName() == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, databasePresetTemplate.getTemplateName());
                }
                if (databasePresetTemplate.getTemplateId() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, databasePresetTemplate.getTemplateId());
                }
                supportSQLiteStatement.bindLong(3, databasePresetTemplate.getHideParam());
                supportSQLiteStatement.bindLong(4, databasePresetTemplate.getVersion());
                if (databasePresetTemplate.getSystemVersion() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, databasePresetTemplate.getSystemVersion());
                }
                supportSQLiteStatement.bindLong(6, databasePresetTemplate.getId());
            }

            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `preset_templates` (`templateName`,`templateId`,`hideParam`,`version`,`systemVersion`,`id`) VALUES (?,?,?,?,?,nullif(?, 0))";
            }
        };
        this.__preparedStmtOfDeleteAllData = new SharedSQLiteStatement(roomDatabase) { // from class: com.miui.keyguard.editor.data.db.PresetTemplateDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM preset_templates";
            }
        };
    }

    public static List getRequiredConverters() {
        return Collections.EMPTY_LIST;
    }

    @Override // com.miui.keyguard.editor.data.db.PresetTemplateDao
    public int count() {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT count(*) FROM preset_templates ", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            return cursorQuery.moveToFirst() ? cursorQuery.getInt(0) : 0;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.PresetTemplateDao
    public int deleteAllData() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteAllData.acquire();
        try {
            this.__db.beginTransaction();
            try {
                int iExecuteUpdateDelete = supportSQLiteStatementAcquire.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
                this.__db.endTransaction();
                this.__preparedStmtOfDeleteAllData.release(supportSQLiteStatementAcquire);
                return iExecuteUpdateDelete;
            } catch (Throwable th) {
                this.__db.endTransaction();
                throw th;
            }
        } catch (Throwable th2) {
            this.__preparedStmtOfDeleteAllData.release(supportSQLiteStatementAcquire);
            throw th2;
        }
    }

    @Override // com.miui.keyguard.editor.data.db.PresetTemplateDao
    public void insertAll(List list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDatabasePresetTemplate.insert((Iterable) list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.PresetTemplateDao
    public boolean needUpdate(String str) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT (SELECT count(*) FROM preset_templates) <= 0 or ((SELECT count(*) FROM preset_templates where (version != 16 OR systemVersion != ?)) > 0)", 1);
        if (str == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        boolean z = false;
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            if (cursorQuery.moveToFirst()) {
                z = cursorQuery.getInt(0) != 0;
            }
            return z;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.PresetTemplateDao
    public boolean support(String str) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT EXISTS(select * from preset_templates where templateId = ? )", 1);
        if (str == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        boolean z = false;
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            if (cursorQuery.moveToFirst()) {
                z = cursorQuery.getInt(0) != 0;
            }
            return z;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.miui.keyguard.editor.data.db.PresetTemplateDao
    public void updateAll(List list) {
        this.__db.beginTransaction();
        try {
            PresetTemplateDao.DefaultImpls.updateAll(this, list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }
}

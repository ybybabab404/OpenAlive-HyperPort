package cn.kuaipan.android.kss.upload;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.kss.IDataFactory;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.utils.OAuthTimeUtils;
import cn.kuaipan.android.utils.SQLUtility;
import cn.kuaipan.android.utils.SyncAccessor;

/* JADX INFO: loaded from: classes.dex */
public class UploadTaskStore implements KssDef {
    public final DBHelper mDBHelper;
    public final IDataFactory mDataFactory;

    /* JADX INFO: renamed from: cn.kuaipan.android.kss.upload.UploadTaskStore$1, reason: invalid class name */
    public class AnonymousClass1 extends SyncAccessor {
        public final /* synthetic */ UploadTaskStore this$0;

        @Override // cn.kuaipan.android.utils.SyncAccessor
        public Object handleAccess(int i, Object... objArr) {
            if (i == 0) {
                this.this$0.mDBHelper.update(((Number) objArr[0]).intValue(), (KssUploadInfo) objArr[1], (UploadChunkInfoPersist) objArr[2]);
                return null;
            }
            if (i == 1) {
                this.this$0.mDBHelper.delete(((Number) objArr[0]).intValue());
                return null;
            }
            if (i == 2) {
                return this.this$0.mDBHelper.queryPos(((Number) objArr[0]).intValue());
            }
            if (i != 3) {
                return super.handleAccess(i, objArr);
            }
            int iIntValue = ((Number) objArr[0]).intValue();
            this.this$0.mDBHelper.deleteBefore(OAuthTimeUtils.currentTime() - 85376000);
            try {
                return this.this$0.mDBHelper.queryKss(iIntValue, this.this$0.mDataFactory);
            } catch (Throwable th) {
                Log.w("UploadTaskStore", "Meet exception when parser kss from db", th);
                return null;
            }
        }
    }

    public static class DBHelper extends SQLiteOpenHelper {
        public static final String WHERE_DEL = SQLUtility.getSelectionWithTemplete("%s<?", "gen_time");
        public static final String WHERE_QUERY = SQLUtility.getSelection("task_hash");
        public static final String[] QUERY_POS = {"chunk_pos", "upload_id"};
        public static final String[] QUERY_KSS = {"kss_request", "kss_file_info", "kss_upload_id", "gen_time"};

        public void delete(int i) {
            getWritableDatabase().delete("upload_chunks", WHERE_QUERY, new String[]{String.valueOf(i)});
        }

        public void deleteBefore(long j) {
            getWritableDatabase().delete("upload_chunks", WHERE_DEL, new String[]{String.valueOf(j)});
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            SQLUtility.createTable(sQLiteDatabase, "upload_chunks", "_id INTEGER PRIMARY KEY AUTOINCREMENT, task_hash INTEGER NOT NULL UNIQUE, kss_request STRING NOT NULL, kss_file_info STRING NOT NULL, kss_upload_id STRING NOT NULL, chunk_pos LONG NOT NULL DEFAULT 0, upload_id STRING NOT NULL, gen_time LONG NOT NULL DEFAULT 0");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i != 4) {
                Log.w("DBHelper", "Destroying all old data.");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS upload_chunks");
                onCreate(sQLiteDatabase);
            }
        }

        public KssUploadInfo queryKss(int i, IDataFactory iDataFactory) {
            Cursor cursorQuery = getReadableDatabase().query("upload_chunks", QUERY_KSS, WHERE_QUERY, new String[]{String.valueOf(i)}, null, null, null);
            KssUploadInfo kssUploadInfo = null;
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex("kss_request"));
                        String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("kss_file_info"));
                        long j = cursorQuery.getLong(cursorQuery.getColumnIndex("gen_time"));
                        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                            KssUploadInfo kssUploadInfo2 = new KssUploadInfo(new UploadFileInfo(string2), iDataFactory.createUploadRequestResult(string), j);
                            kssUploadInfo2.setUploadId(cursorQuery.getString(cursorQuery.getColumnIndex("kss_upload_id")));
                            kssUploadInfo = kssUploadInfo2;
                        }
                        cursorQuery.close();
                        return null;
                    }
                } catch (Throwable th) {
                    cursorQuery.close();
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return kssUploadInfo;
        }

        public UploadChunkInfoPersist queryPos(int i) {
            Cursor cursorQuery = getReadableDatabase().query("upload_chunks", QUERY_POS, WHERE_QUERY, new String[]{String.valueOf(i)}, null, null, null);
            try {
                UploadChunkInfoPersist uploadChunkInfoPersist = new UploadChunkInfoPersist();
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    long j = cursorQuery.getInt(cursorQuery.getColumnIndex("chunk_pos"));
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("upload_id"));
                    if (!TextUtils.isEmpty(string)) {
                        uploadChunkInfoPersist.pos = j;
                        uploadChunkInfoPersist.upload_id = string;
                    }
                }
                return uploadChunkInfoPersist;
            } finally {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        }

        public void update(int i, KssUploadInfo kssUploadInfo, UploadChunkInfoPersist uploadChunkInfoPersist) {
            if (kssUploadInfo == null) {
                return;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("task_hash", Integer.valueOf(i));
            contentValues.put("kss_file_info", kssUploadInfo.getFileInfo().toString());
            contentValues.put("kss_request", kssUploadInfo.getRequestResult().toString());
            if (TextUtils.isEmpty(kssUploadInfo.getUploadId())) {
                contentValues.put("kss_upload_id", "");
            } else {
                contentValues.put("kss_upload_id", kssUploadInfo.getUploadId());
            }
            contentValues.put("chunk_pos", Long.valueOf(uploadChunkInfoPersist.pos));
            if (TextUtils.isEmpty(uploadChunkInfoPersist.upload_id)) {
                contentValues.put("upload_id", "");
            } else {
                contentValues.put("upload_id", uploadChunkInfoPersist.upload_id);
            }
            contentValues.put("gen_time", Long.valueOf(kssUploadInfo.getGenerateTime()));
            getWritableDatabase().replace("upload_chunks", null, contentValues);
        }
    }
}

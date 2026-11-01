package com.miui.maml.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.miui.maml.util.MamlLog;
import miuix.core.util.IOUtils;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AsyncQueryHandler extends Handler {
    private static final int EVENT_ARG_DELETE = 4;
    private static final int EVENT_ARG_INSERT = 2;
    private static final int EVENT_ARG_QUERY = 1;
    private static final int EVENT_ARG_UPDATE = 3;
    private static final String TAG = "AsyncQuery";
    private static final boolean localLOGV = false;
    private static Looper sLooper;
    private final ContentResolver mResolver;
    private Handler mWorkerThreadHandler;

    public static final class WorkerArgs {
        public Object cookie;
        public Handler handler;
        public String orderBy;
        public String[] projection;
        public Object result;
        public String selection;
        public String[] selectionArgs;
        public Uri uri;
        public ContentValues values;
    }

    public class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Cursor cursorQuery;
            ContentResolver contentResolver = AsyncQueryHandler.this.mResolver;
            if (contentResolver == null) {
                MamlLog.e(ContentProviderBinder.TAG_NAME, "resolver == null");
                return;
            }
            MamlLog.e(ContentProviderBinder.TAG_NAME, "resolver not null, go on!");
            WorkerArgs workerArgs = (WorkerArgs) message.obj;
            int i = message.what;
            int i2 = message.arg1;
            if (i2 == 1) {
                try {
                    cursorQuery = contentResolver.query(workerArgs.uri, workerArgs.projection, workerArgs.selection, workerArgs.selectionArgs, workerArgs.orderBy);
                    if (cursorQuery != null) {
                        cursorQuery.getCount();
                    } else {
                        MamlLog.w(AsyncQueryHandler.TAG, "failed to query, cursor is null");
                    }
                } catch (Exception e) {
                    MamlLog.e(AsyncQueryHandler.TAG, "Exception thrown during handling EVENT_ARG_QUERY", e);
                    cursorQuery = null;
                }
                AsyncQueryHandler.this.doUpdateOfCursor(cursorQuery);
                IOUtils.closeQuietly(cursorQuery);
            } else if (i2 == 2) {
                workerArgs.result = contentResolver.insert(workerArgs.uri, workerArgs.values);
            } else if (i2 == 3) {
                workerArgs.result = Integer.valueOf(contentResolver.update(workerArgs.uri, workerArgs.values, workerArgs.selection, workerArgs.selectionArgs));
            } else if (i2 == 4) {
                workerArgs.result = Integer.valueOf(contentResolver.delete(workerArgs.uri, workerArgs.selection, workerArgs.selectionArgs));
            }
            Message messageObtainMessage = workerArgs.handler.obtainMessage(i);
            messageObtainMessage.obj = workerArgs;
            messageObtainMessage.arg1 = message.arg1;
            messageObtainMessage.sendToTarget();
        }
    }

    public AsyncQueryHandler(ContentResolver contentResolver) {
        this.mResolver = contentResolver;
        synchronized (android.content.AsyncQueryHandler.class) {
            try {
                if (sLooper == null) {
                    HandlerThread handlerThread = new HandlerThread("AsyncQueryWorker");
                    handlerThread.start();
                    sLooper = handlerThread.getLooper();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mWorkerThreadHandler = createHandler(sLooper);
    }

    public AsyncQueryHandler(Looper looper, ContentResolver contentResolver) {
        super(looper);
        this.mResolver = contentResolver;
        synchronized (android.content.AsyncQueryHandler.class) {
            try {
                if (sLooper == null) {
                    HandlerThread handlerThread = new HandlerThread("AsyncQueryWorker");
                    handlerThread.start();
                    sLooper = handlerThread.getLooper();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mWorkerThreadHandler = createHandler(sLooper);
    }

    public final void cancelOperation(int i) {
        this.mWorkerThreadHandler.removeMessages(i);
    }

    public Handler createHandler(Looper looper) {
        return new WorkerHandler(looper);
    }

    public void doUpdateOfCursor(Cursor cursor) {
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        WorkerArgs workerArgs = (WorkerArgs) message.obj;
        int i = message.what;
        int i2 = message.arg1;
        if (i2 == 1) {
            onQueryComplete(i, workerArgs.cookie);
            return;
        }
        if (i2 == 2) {
            onInsertComplete(i, workerArgs.cookie, (Uri) workerArgs.result);
        } else if (i2 == 3) {
            onUpdateComplete(i, workerArgs.cookie, ((Integer) workerArgs.result).intValue());
        } else {
            if (i2 != 4) {
                return;
            }
            onDeleteComplete(i, workerArgs.cookie, ((Integer) workerArgs.result).intValue());
        }
    }

    public void onDeleteComplete(int i, Object obj, int i2) {
    }

    public void onInsertComplete(int i, Object obj, Uri uri) {
    }

    public void onQueryComplete(int i, Object obj) {
    }

    public void onUpdateComplete(int i, Object obj, int i2) {
    }

    public final void startDelete(int i, Object obj, Uri uri, String str, String[] strArr) {
        Message messageObtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        messageObtainMessage.arg1 = 4;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = obj;
        workerArgs.selection = str;
        workerArgs.selectionArgs = strArr;
        messageObtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(messageObtainMessage);
    }

    public final void startInsert(int i, Object obj, Uri uri, ContentValues contentValues) {
        Message messageObtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        messageObtainMessage.arg1 = 2;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = obj;
        workerArgs.values = contentValues;
        messageObtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(messageObtainMessage);
    }

    public void startQuery(int i, Object obj, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Message messageObtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        messageObtainMessage.arg1 = 1;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.projection = strArr;
        workerArgs.selection = str;
        workerArgs.selectionArgs = strArr2;
        workerArgs.orderBy = str2;
        workerArgs.cookie = obj;
        messageObtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(messageObtainMessage);
    }

    public final void startUpdate(int i, Object obj, Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Message messageObtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        messageObtainMessage.arg1 = 3;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = obj;
        workerArgs.values = contentValues;
        workerArgs.selection = str;
        workerArgs.selectionArgs = strArr;
        messageObtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(messageObtainMessage);
    }
}

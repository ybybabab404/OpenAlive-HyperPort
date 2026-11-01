package org.greenrobot.greendao.test;

import android.app.Application;
import android.app.Instrumentation;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import java.util.Random;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.DbUtils;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DbTest extends AndroidTestCase {
    public static final String DB_NAME = "greendao-unittest-db.temp";
    private Application application;
    protected Database db;
    protected final boolean inMemory;
    protected final Random random;

    public DbTest() {
        this(true);
    }

    public DbTest(boolean z) {
        this.inMemory = z;
        this.random = new Random();
    }

    public <T extends Application> T createApplication(Class<T> cls) {
        AndroidTestCase.assertNull("Application already created", this.application);
        try {
            T t = (T) Instrumentation.newApplication(cls, getContext());
            t.onCreate();
            this.application = t;
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Could not create application " + cls, e);
        }
    }

    public Database createDatabase() {
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase;
        if (this.inMemory) {
            sQLiteDatabaseOpenOrCreateDatabase = SQLiteDatabase.create(null);
        } else {
            getContext().deleteDatabase(DB_NAME);
            sQLiteDatabaseOpenOrCreateDatabase = getContext().openOrCreateDatabase(DB_NAME, 0, null);
        }
        return new StandardDatabase(sQLiteDatabaseOpenOrCreateDatabase);
    }

    public <T extends Application> T getApplication() {
        AndroidTestCase.assertNotNull("Application not yet created", this.application);
        return (T) this.application;
    }

    public void logTableDump(String str) {
        Database database = this.db;
        if (database instanceof StandardDatabase) {
            DbUtils.logTableDump(((StandardDatabase) database).getSQLiteDatabase(), str);
            return;
        }
        DaoLog.w("Table dump unsupported for " + this.db);
    }

    public void setUp() throws Exception {
        super.setUp();
        this.db = createDatabase();
    }

    public void tearDown() throws Exception {
        if (this.application != null) {
            terminateApplication();
        }
        this.db.close();
        if (!this.inMemory) {
            getContext().deleteDatabase(DB_NAME);
        }
        super.tearDown();
    }

    public void terminateApplication() {
        AndroidTestCase.assertNotNull("Application not yet created", this.application);
        this.application.onTerminate();
        this.application = null;
    }
}

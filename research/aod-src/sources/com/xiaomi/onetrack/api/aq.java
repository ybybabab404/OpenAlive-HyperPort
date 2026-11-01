package com.xiaomi.onetrack.api;

import android.os.Process;
import android.util.Log;
import com.xiaomi.onetrack.CrashAnalysis;
import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class aq implements Thread.UncaughtExceptionHandler {
    private static final String a = "OneTrackExceptionHandler";
    private static final String c = "tombstone";
    private static final String d = ".java.xcrash";
    private static final String e = "backtrace feature id:\n\t";
    private static final String f = "error reason:\n\t";
    private static final long h = 2;
    private Thread.UncaughtExceptionHandler b;
    private c i;
    private final Date g = new Date();
    private int j = 50;
    private int k = 50;
    private int l = 200;
    private boolean m = true;
    private boolean n = true;

    public aq(c cVar) {
        this.i = cVar;
    }

    private String a(Date date, Thread thread, String str) {
        return com.xiaomi.onetrack.util.b.a(this.g, date, "java", com.xiaomi.onetrack.f.a.e(), com.xiaomi.onetrack.util.b.a(com.xiaomi.onetrack.f.a.b())) + "pid: " + Process.myPid() + ", tid: " + Process.myTid() + ", name: " + thread.getName() + "  >>> " + com.xiaomi.onetrack.f.a.e() + " <<<\n\njava stacktrace:\n" + str + "\n";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:29:0x00c4 A[Catch: all -> 0x00cc, Exception -> 0x00d1, TryCatch #11 {Exception -> 0x00d1, all -> 0x00cc, blocks: (B:27:0x0084, B:29:0x00c4, B:34:0x00d6, B:36:0x00da, B:38:0x00de, B:41:0x00f1, B:43:0x00f5, B:44:0x0100, B:46:0x0104, B:47:0x010f, B:49:0x012b, B:50:0x0137, B:52:0x013e, B:54:0x0144, B:40:0x00e2), top: B:92:0x0084 }] */
    /* JADX WARN: Code duplicated, block: B:40:0x00e2 A[Catch: all -> 0x00cc, Exception -> 0x00d1, TryCatch #11 {Exception -> 0x00d1, all -> 0x00cc, blocks: (B:27:0x0084, B:29:0x00c4, B:34:0x00d6, B:36:0x00da, B:38:0x00de, B:41:0x00f1, B:43:0x00f5, B:44:0x0100, B:46:0x0104, B:47:0x010f, B:49:0x012b, B:50:0x0137, B:52:0x013e, B:54:0x0144, B:40:0x00e2), top: B:92:0x0084 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x00f5 A[Catch: all -> 0x00cc, Exception -> 0x00d1, TryCatch #11 {Exception -> 0x00d1, all -> 0x00cc, blocks: (B:27:0x0084, B:29:0x00c4, B:34:0x00d6, B:36:0x00da, B:38:0x00de, B:41:0x00f1, B:43:0x00f5, B:44:0x0100, B:46:0x0104, B:47:0x010f, B:49:0x012b, B:50:0x0137, B:52:0x013e, B:54:0x0144, B:40:0x00e2), top: B:92:0x0084 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x0104 A[Catch: all -> 0x00cc, Exception -> 0x00d1, TryCatch #11 {Exception -> 0x00d1, all -> 0x00cc, blocks: (B:27:0x0084, B:29:0x00c4, B:34:0x00d6, B:36:0x00da, B:38:0x00de, B:41:0x00f1, B:43:0x00f5, B:44:0x0100, B:46:0x0104, B:47:0x010f, B:49:0x012b, B:50:0x0137, B:52:0x013e, B:54:0x0144, B:40:0x00e2), top: B:92:0x0084 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x012b A[Catch: all -> 0x00cc, Exception -> 0x00d1, TryCatch #11 {Exception -> 0x00d1, all -> 0x00cc, blocks: (B:27:0x0084, B:29:0x00c4, B:34:0x00d6, B:36:0x00da, B:38:0x00de, B:41:0x00f1, B:43:0x00f5, B:44:0x0100, B:46:0x0104, B:47:0x010f, B:49:0x012b, B:50:0x0137, B:52:0x013e, B:54:0x0144, B:40:0x00e2), top: B:92:0x0084 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x007d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:? A[RETURN, SYNTHETIC] */
    public void a(Thread thread, Throwable th) throws Throwable {
        File file;
        Throwable th2;
        String string;
        String strA;
        Throwable th3;
        Exception exc;
        RandomAccessFile randomAccessFile;
        int i;
        JSONObject jSONObjectA;
        Date date = new Date();
        RandomAccessFile randomAccessFile2 = null;
        randomAccessFile2 = null;
        try {
            file = new File(String.format(Locale.US, "%s/%s_%020d_%s__%s%s", CrashAnalysis.getCrashPath(), c, Long.valueOf(this.g.getTime() * 1000), com.xiaomi.onetrack.util.b.a(com.xiaomi.onetrack.f.a.b()), com.xiaomi.onetrack.f.a.e(), d));
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Exception e2) {
                e = e2;
                com.xiaomi.onetrack.util.s.b(a, "JavaCrashHandler createLogFile failed", e);
            }
        } catch (Exception e3) {
            e = e3;
            file = null;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            th2 = th;
            try {
                th2.printStackTrace(new PrintWriter(stringWriter));
                string = stringWriter.toString();
                try {
                    strA = a(date, thread, string);
                } catch (Exception e4) {
                    e = e4;
                    com.xiaomi.onetrack.util.s.b(a, "JavaCrashHandler getEmergency failed", e);
                    strA = null;
                }
            } catch (Exception e5) {
                e = e5;
                string = null;
                com.xiaomi.onetrack.util.s.b(a, "JavaCrashHandler getEmergency failed", e);
                strA = null;
                if (file == null) {
                }
                try {
                    try {
                        randomAccessFile = new RandomAccessFile(file, "rws");
                        try {
                            randomAccessFile.write((e + CrashAnalysis.calculateJavaDigest(string) + "\n\n").getBytes("UTF-8"));
                            randomAccessFile.write((f + th2.toString() + "\n\n").getBytes("UTF-8"));
                            if (strA != null) {
                                randomAccessFile.write(strA.getBytes("UTF-8"));
                            }
                            i = this.l;
                            if (i <= 0) {
                                randomAccessFile.write(com.xiaomi.onetrack.util.b.a(i, this.j, this.k).getBytes("UTF-8"));
                            } else {
                                randomAccessFile.write(com.xiaomi.onetrack.util.b.a(i, this.j, this.k).getBytes("UTF-8"));
                            }
                            if (this.m) {
                                randomAccessFile.write(com.xiaomi.onetrack.util.b.f().getBytes("UTF-8"));
                            }
                            if (this.n) {
                                randomAccessFile.write(com.xiaomi.onetrack.util.b.e().getBytes("UTF-8"));
                            }
                            randomAccessFile.write(com.xiaomi.onetrack.util.b.d().getBytes("UTF-8"));
                            randomAccessFile.write("foreground:\nyes\n\n".getBytes("UTF-8"));
                            jSONObjectA = com.xiaomi.onetrack.util.u.a(this.i.d() != null ? this.i.d().getDynamicProperty("onetrack_bug_report") : null, false);
                            if (jSONObjectA != null) {
                                randomAccessFile.write((com.xiaomi.onetrack.util.b.l + jSONObjectA + "\n\n").getBytes("UTF-8"));
                            }
                            randomAccessFile.close();
                        } catch (Exception e6) {
                            exc = e6;
                            randomAccessFile2 = randomAccessFile;
                            com.xiaomi.onetrack.util.s.b(a, "JavaCrashHandler write log file failed", exc);
                            if (randomAccessFile2 == null) {
                                return;
                            } else {
                                randomAccessFile2.close();
                            }
                        } catch (Throwable th4) {
                            th3 = th4;
                            randomAccessFile2 = randomAccessFile;
                            if (randomAccessFile2 == null) {
                                throw th3;
                            }
                            try {
                                randomAccessFile2.close();
                                throw th3;
                            } catch (Exception e7) {
                                com.xiaomi.onetrack.util.s.b(a, "JavaCrashHandler close RandomAccessFile failed", e7);
                                throw th3;
                            }
                        }
                    } catch (Exception e8) {
                        exc = e8;
                    }
                } catch (Throwable th5) {
                    th3 = th5;
                }
            }
        } catch (Exception e9) {
            e = e9;
            th2 = th;
        }
        try {
            if (file == null) {
                randomAccessFile = new RandomAccessFile(file, "rws");
                randomAccessFile.write((e + CrashAnalysis.calculateJavaDigest(string) + "\n\n").getBytes("UTF-8"));
                randomAccessFile.write((f + th2.toString() + "\n\n").getBytes("UTF-8"));
                if (strA != null) {
                    randomAccessFile.write(strA.getBytes("UTF-8"));
                }
                i = this.l;
                if (i <= 0 || this.j > 0 || this.k > 0) {
                    randomAccessFile.write(com.xiaomi.onetrack.util.b.a(i, this.j, this.k).getBytes("UTF-8"));
                }
                if (this.m) {
                    randomAccessFile.write(com.xiaomi.onetrack.util.b.f().getBytes("UTF-8"));
                }
                if (this.n) {
                    randomAccessFile.write(com.xiaomi.onetrack.util.b.e().getBytes("UTF-8"));
                }
                randomAccessFile.write(com.xiaomi.onetrack.util.b.d().getBytes("UTF-8"));
                randomAccessFile.write("foreground:\nyes\n\n".getBytes("UTF-8"));
                jSONObjectA = com.xiaomi.onetrack.util.u.a(this.i.d() != null ? this.i.d().getDynamicProperty("onetrack_bug_report") : null, false);
                if (jSONObjectA != null && jSONObjectA.length() != 0) {
                    randomAccessFile.write((com.xiaomi.onetrack.util.b.l + jSONObjectA + "\n\n").getBytes("UTF-8"));
                }
                randomAccessFile.close();
            }
        } catch (Exception e10) {
            com.xiaomi.onetrack.util.s.b(a, "JavaCrashHandler close RandomAccessFile failed", e10);
        }
    }

    public void a() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler instanceof aq) {
            return;
        }
        this.b = defaultUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Log.d(com.xiaomi.onetrack.util.s.a(a), "crash happened->stacktrace: " + th.getStackTrace());
        FutureTask futureTask = new FutureTask(new ar(this, thread, th), null);
        com.xiaomi.onetrack.util.j.a(futureTask);
        try {
            futureTask.get(2L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            Log.e(com.xiaomi.onetrack.util.s.a(a), "handleException error :" + e2.getMessage());
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.b;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}

package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import android.util.LruCache;
import com.xiaomi.onetrack.CrashAnalysis;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/* JADX INFO: loaded from: classes2.dex */
public class n {
    public static final String a = "onetrack_pa";
    private static final String b = "FileUtil";
    private static final String c = "onetrack";
    private static final String d = "tombstone";
    private static LruCache<String, a> e = new o(1048576);

    public static class a {
        String a;

        private a() {
        }

        public /* synthetic */ a(o oVar) {
            this();
        }
    }

    public static String a() {
        if (z.c()) {
            return c(d);
        }
        return c("tombstone_" + z.b());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.xiaomi.onetrack.util.o] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public static String a(String str) throws Throwable {
        ?? r0;
        BufferedReader bufferedReader;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        a aVar = e.get(str);
        if (aVar != null) {
            return aVar.a;
        }
        ?? r1 = 0;
        r1 = 0;
        try {
            try {
                File file = new File(b(), str);
                StringBuilder sb = new StringBuilder();
                if (file.exists()) {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        } catch (Exception e2) {
                            e = e2;
                            r1 = bufferedReader;
                            s.c(b, "get error:" + e.toString());
                            p.a((Closeable) r1);
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            r0 = bufferedReader;
                            p.a((Closeable) r0);
                            throw th;
                        }
                    }
                } else {
                    bufferedReader = null;
                }
                String string = sb.toString();
                a aVar2 = new a(r1);
                aVar2.a = string;
                e.put(str, aVar2);
                p.a(bufferedReader);
                return string;
            } catch (Throwable th2) {
                th = th2;
                r0 = r1;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static void a(File file) {
        try {
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e2) {
            s.c(b, "failed to remove file " + file.getName() + ", e :" + e2.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.xiaomi.onetrack.util.o] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    public static void a(String str, String str2) throws Throwable {
        ?? r0;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ?? r1 = 0;
        r1 = 0;
        try {
            try {
                a aVar = new a(r1);
                aVar.a = str2;
                e.put(str, aVar);
                String strB = b();
                File file = new File(strB);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(strB, str);
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2), 1024);
                try {
                    bufferedWriter.write(str2);
                    bufferedWriter.flush();
                    p.a(bufferedWriter);
                } catch (Exception e2) {
                    r1 = bufferedWriter;
                    e = e2;
                    s.c(b, "put error:" + e.toString());
                    p.a((Closeable) r1);
                } catch (Throwable th) {
                    r0 = bufferedWriter;
                    th = th;
                    p.a((Closeable) r0);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
            r0 = r1;
        }
    }

    public static String[] a(String str, int i) throws Throwable {
        String[] strArr = new String[3];
        BufferedReader bufferedReader = null;
        try {
            try {
                File file = new File(str);
                StringBuilder sb = new StringBuilder();
                if (file.exists()) {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                    boolean z = false;
                    boolean z2 = false;
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            if (z) {
                                strArr[0] = line;
                                z = false;
                            } else if (b.k.equals(line)) {
                                z = true;
                            }
                            if (!z2 && line.contains(CrashAnalysis.APP_VERSION_TEXT)) {
                                strArr[2] = line;
                                z2 = true;
                            }
                            if (sb.length() <= i) {
                                sb.append(line);
                                sb.append("\n");
                            } else if (!TextUtils.isEmpty(strArr[0])) {
                                break;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            s.c(b, "readCrashFile error:", e);
                            p.a(bufferedReader);
                            return strArr;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            p.a(bufferedReader);
                            throw th;
                        }
                    }
                    bufferedReader = bufferedReader2;
                }
                if (sb.length() > i) {
                    strArr[1] = sb.substring(0, i - 1);
                } else {
                    strArr[1] = sb.toString();
                }
                p.a(bufferedReader);
                return strArr;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private static String b() {
        return c("onetrack");
    }

    public static void b(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            e.remove(str);
            File file = new File(b(), str);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e2) {
            s.c(b, "clear error:" + e2.toString());
        }
    }

    private static String c(String str) {
        String str2 = com.xiaomi.onetrack.f.a.a().getFilesDir().getAbsolutePath() + File.separator + str;
        File file = new File(str2);
        if (!file.exists()) {
            try {
                file.mkdirs();
                return str2;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return str2;
    }
}

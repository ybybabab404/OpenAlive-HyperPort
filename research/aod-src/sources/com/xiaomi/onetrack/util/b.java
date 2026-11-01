package com.xiaomi.onetrack.util;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.system.Os;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class b {
    static final String a = "3.1.7";
    static final String b = "OneTrack 3.1.7";
    static final String c = "CrashUtil";
    public static final String d = "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***";
    public static final String e = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---";
    public static final String f = "+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++";
    static final String g = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String h = "java";
    static final String i = "tombstone";
    static final String j = ".java.xcrash";
    public static final String k = "dynamic common property:";
    public static final String l = "dynamic common property:\n\t";
    private static final String m = "%21s %8s\n";
    private static final String n = "%21s %8s %21s %8s\n";
    private static final String[] o = {"/data/local/su", "/data/local/bin/su", "/data/local/xbin/su", "/system/xbin/su", "/system/bin/su", "/system/bin/.ext/su", "/system/bin/failsafe/su", "/system/sd/xbin/su", "/system/usr/we-need-root/su", "/sbin/su", "/su/bin/su"};

    private b() {
    }

    public static long a(String str) {
        try {
            return new SimpleDateFormat(g, Locale.US).parse(str).getTime();
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    public static String a(int i2, int i3, int i4) throws Throwable {
        int iMyPid = Process.myPid();
        StringBuilder sb = new StringBuilder();
        sb.append("logcat:\n");
        if (i2 > 0) {
            a(iMyPid, sb, "main", i2, 'D');
        }
        if (i3 > 0) {
            a(iMyPid, sb, "system", i3, 'W');
        }
        if (i4 > 0) {
            a(iMyPid, sb, "events", i3, 'I');
        }
        sb.append("\n");
        return sb.toString();
    }

    public static String a(Context context) {
        String str;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            str = null;
        }
        return TextUtils.isEmpty(str) ? "unknown" : str;
    }

    public static String a(Context context, int i2) throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/" + i2 + "/cmdline"));
            try {
                String line = bufferedReader.readLine();
                if (!TextUtils.isEmpty(line)) {
                    String strTrim = line.trim();
                    if (!TextUtils.isEmpty(strTrim)) {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused) {
                        }
                        return strTrim;
                    }
                }
            } catch (Exception unused2) {
                if (bufferedReader != null) {
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused3) {
                    }
                }
                throw th;
            }
        } catch (Exception unused4) {
            bufferedReader = null;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
        }
        try {
            bufferedReader.close();
        } catch (Exception unused5) {
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v7 */
    private static String a(String str, int i2) throws Throwable {
        StringBuilder sb = new StringBuilder();
        ?? r1 = 0;
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
                    int i3 = 0;
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            String strTrim = line.trim();
                            if (strTrim.length() > 0) {
                                i3++;
                                if (i2 == 0 || i3 <= i2) {
                                    sb.append("  ");
                                    sb.append(strTrim);
                                    sb.append("\n");
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            s.c(c, "CrashUtil getInfo(" + str + ") failed", e);
                            r1 = bufferedReader;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                r1 = bufferedReader;
                            }
                        } catch (Throwable th) {
                            th = th;
                            r1 = bufferedReader2;
                            if (r1 != 0) {
                                try {
                                    r1.close();
                                } catch (Exception unused) {
                                }
                            }
                            throw th;
                        }
                    }
                    if (i2 > 0 && i3 > i2) {
                        sb.append("  ......\n");
                        sb.append("  (number of records: ");
                        sb.append(i3);
                        sb.append(")\n");
                    }
                    bufferedReader2.close();
                    r1 = i3;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception unused2) {
        }
        return sb.toString();
    }

    public static String a(Date date, Date date2, String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(g, Locale.US);
        StringBuilder sb = new StringBuilder();
        sb.append("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\nTombstone maker: 'OneTrack 3.1.7'\nCrash type: '");
        sb.append(str);
        sb.append("'\nStart time: '");
        sb.append(simpleDateFormat.format(date));
        sb.append("'\nCrash time: '");
        sb.append(simpleDateFormat.format(date2));
        sb.append("'\nApp ID: '");
        sb.append(str2);
        sb.append("'\nApp version: '");
        sb.append(str3);
        sb.append("'\nRooted: '");
        sb.append(a() ? "Yes" : "No");
        sb.append("'\nAPI level: '");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("'\nOS version: '");
        sb.append(Build.VERSION.RELEASE);
        sb.append("'\nABI list: '");
        sb.append(b());
        sb.append("'\nManufacturer: '");
        sb.append(Build.MANUFACTURER);
        sb.append("'\nBrand: '");
        sb.append(Build.BRAND);
        sb.append("'\nModel: '");
        sb.append(Build.MODEL);
        sb.append("'\nBuild fingerprint: '");
        sb.append(Build.FINGERPRINT);
        sb.append("'\n");
        return sb.toString();
    }

    private static void a(int i2, StringBuilder sb, String str, int i3, char c2) throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        Exception e2;
        String string = Integer.toString(i2);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" ");
        sb2.append(string);
        sb2.append(" ");
        ArrayList arrayList = new ArrayList();
        arrayList.add("/system/bin/logcat");
        arrayList.add("-b");
        arrayList.add(str);
        arrayList.add("-d");
        arrayList.add("-v");
        arrayList.add("threadtime");
        arrayList.add("-t");
        arrayList.add(Integer.toString(i3));
        arrayList.add("--pid");
        arrayList.add(string);
        arrayList.add("*:" + c2);
        Object[] array = arrayList.toArray();
        sb.append("--------- tail end of log ");
        sb.append(str);
        sb.append(" (");
        sb.append(TextUtils.join(" ", array));
        sb.append(")\n");
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new ProcessBuilder(new String[0]).command(arrayList).start().getInputStream()));
                while (true) {
                    try {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                bufferedReader.close();
                                return;
                            } else {
                                sb.append(line);
                                sb.append("\n");
                            }
                        } catch (Exception e3) {
                            e2 = e3;
                            s.b(c, "CrashUtil run logcat command failed", e2);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                return;
                            }
                            return;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused2) {
            }
        } catch (Exception e4) {
            bufferedReader = null;
            e2 = e4;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
        }
    }

    public static boolean a() {
        try {
            for (String str : o) {
                if (new File(str).exists()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static String b() {
        return TextUtils.join(ac.b, Build.SUPPORTED_ABIS);
    }

    public static String b(String str) {
        int i2;
        int iIndexOf;
        String[] strArrSplit;
        try {
            int iIndexOf2 = str.indexOf("\nbacktrace:\n");
            if (iIndexOf2 == -1 || (iIndexOf = str.indexOf("\n\n", (i2 = iIndexOf2 + 12))) == -1) {
                return "";
            }
            String strSubstring = str.substring(i2, iIndexOf);
            if (TextUtils.isEmpty(strSubstring.trim()) || (strArrSplit = strSubstring.split("\n")) == null || strArrSplit.length <= 0) {
                return "";
            }
            for (String str2 : strArrSplit) {
                int iIndexOf3 = str2.indexOf("(");
                if (iIndexOf3 != -1) {
                    String strSubstring2 = str2.substring(iIndexOf3);
                    s.a(c, "getErrorReason->errorReason: " + strSubstring2);
                    return strSubstring2;
                }
            }
            return "";
        } catch (Exception e2) {
            s.b(c, "getErrorReasonForNative error, e: ", e2);
            return "";
        }
    }

    public static String c() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Process Summary (From: android.os.Debug.MemoryInfo)\n");
        Locale locale = Locale.US;
        sb.append(String.format(locale, m, "", "Pss(KB)"));
        sb.append(String.format(locale, m, "", "------"));
        try {
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            sb.append(String.format(locale, m, "Java Heap:", memoryInfo.getMemoryStat("summary.java-heap")));
            sb.append(String.format(locale, m, "Native Heap:", memoryInfo.getMemoryStat("summary.native-heap")));
            sb.append(String.format(locale, m, "Code:", memoryInfo.getMemoryStat("summary.code")));
            sb.append(String.format(locale, m, "Stack:", memoryInfo.getMemoryStat("summary.stack")));
            sb.append(String.format(locale, m, "Graphics:", memoryInfo.getMemoryStat("summary.graphics")));
            sb.append(String.format(locale, m, "Private Other:", memoryInfo.getMemoryStat("summary.private-other")));
            sb.append(String.format(locale, m, "System:", memoryInfo.getMemoryStat("summary.system")));
            sb.append(String.format(locale, n, "TOTAL:", memoryInfo.getMemoryStat("summary.total-pss"), "TOTAL SWAP:", memoryInfo.getMemoryStat("summary.total-swap")));
        } catch (Exception e2) {
            s.b(c, "CrashUtil getProcessMemoryInfo failed", e2);
        }
        return sb.toString();
    }

    public static String c(String str) {
        int i2;
        int iIndexOf;
        try {
            int iIndexOf2 = str.indexOf("\njava stacktrace:\n");
            if (iIndexOf2 == -1 || (iIndexOf = str.indexOf("\n", (i2 = iIndexOf2 + 18))) == -1) {
                return "";
            }
            String strSubstring = str.substring(i2, iIndexOf);
            s.a(c, "getErrorReasonForJava->allBacktraces: \n" + strSubstring);
            return strSubstring;
        } catch (Exception e2) {
            s.b(c, "getErrorReasonForJava error, e: ", e2);
            return "";
        }
    }

    public static String d() {
        return "memory info:\n System Summary (From: /proc/meminfo)\n" + d("/proc/meminfo") + "-\n Process Status (From: /proc/PID/status)\n" + d("/proc/self/status") + "-\n Process Limits (From: /proc/PID/limits)\n" + d("/proc/self/limits") + "-\n" + c() + "\n";
    }

    private static String d(String str) {
        return a(str, 0);
    }

    public static String e() {
        return "network info:\nNot supported on Android Q (API level 29) and later.\n\n";
    }

    public static String f() {
        String str;
        StringBuilder sb = new StringBuilder("open files:\n");
        try {
            File[] fileArrListFiles = new File("/proc/self/fd").listFiles(new c());
            if (fileArrListFiles != null) {
                int i2 = 0;
                for (File file : fileArrListFiles) {
                    try {
                        str = Os.readlink(file.getAbsolutePath());
                    } catch (Exception unused) {
                        str = null;
                    }
                    sb.append("    fd ");
                    sb.append(file.getName());
                    sb.append(": ");
                    sb.append(TextUtils.isEmpty(str) ? "???" : str.trim());
                    sb.append('\n');
                    i2++;
                    if (i2 > 1024) {
                        break;
                    }
                }
                if (fileArrListFiles.length > 1024) {
                    sb.append("    ......\n");
                }
                sb.append("    (number of FDs: ");
                sb.append(fileArrListFiles.length);
                sb.append(")\n");
            }
        } catch (Exception unused2) {
        }
        sb.append('\n');
        return sb.toString();
    }
}

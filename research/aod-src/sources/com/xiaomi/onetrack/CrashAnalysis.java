package com.xiaomi.onetrack;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.c;
import com.xiaomi.onetrack.d.d;
import com.xiaomi.onetrack.f.a;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.af;
import com.xiaomi.onetrack.util.b;
import com.xiaomi.onetrack.util.n;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.z;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class CrashAnalysis {
    public static final String ANR_CRASH = "anr";
    public static final String APP_VERSION_TEXT = "App version: '";
    public static final String JAVA_CRASH = "java";
    public static final String NATIVE_CRASH = "native";
    private static final String a = "CrashAnalysis";
    private static final String b = "backtrace feature id:\n\t";
    private static final String c = "error reason:\n\t";
    private static final String d = "Crash time: '";
    private static final String e = ".xcrash";
    private static final String f = "l_c_c_t_";
    private static final String g = "r_c_t_";
    private static final int h = 604800000;
    private static final int i = 358400;
    private static final int j = 10;
    private static final int k = 20;
    private static final String l = "@[0-9a-fA-F]{1,10}";
    private static final String m = "\\$[0-9a-fA-F]{1,10}@[0-9a-fA-F]{1,10}";
    private static final String n = "0x[0-9a-fA-F]{1,10}";
    private static final String o = "\\d+[B,KB,MB]*";
    private static final String p = "((java:)|(length=)|(index=)|(Index:)|(Size:))\\d+";
    private static final int q = 20;
    private static final boolean r = false;
    private static volatile CrashAnalysis u;
    private final FileProcessor[] s;
    private final c t;
    private boolean v;

    public class FileProcessor {
        final List<File> a = new ArrayList();
        final String b;
        final String c;

        public FileProcessor(String str) {
            this.c = str;
            this.b = str + CrashAnalysis.e;
        }

        private String a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            String[] strArrSplit = str.split("__");
            if (strArrSplit.length < 2) {
                return null;
            }
            String[] strArrSplit2 = strArrSplit[0].split("_");
            if (strArrSplit2.length == 3) {
                return strArrSplit2[2];
            }
            return null;
        }

        private String b(String str) {
            if (TextUtils.isEmpty(str)) {
                s.d(CrashAnalysis.a, "parseVersionFromContent content is empty");
                return null;
            }
            try {
                int iIndexOf = str.indexOf(CrashAnalysis.APP_VERSION_TEXT);
                if (iIndexOf != -1) {
                    int i = iIndexOf + 14;
                    int iLastIndexOf = str.lastIndexOf("'");
                    if (iLastIndexOf != -1) {
                        return str.substring(i, iLastIndexOf);
                    }
                }
            } catch (Exception e) {
                s.b(CrashAnalysis.a, "parseVersionFromContent error, e: ", e);
            }
            return null;
        }

        public void a() throws Throwable {
            for (int i = 0; i < this.a.size(); i++) {
                String absolutePath = this.a.get(i).getAbsoluteFile().getAbsolutePath();
                String[] strArrA = n.a(absolutePath, CrashAnalysis.i);
                s.a(CrashAnalysis.a, "crash content size: " + strArrA[1].length());
                if (!TextUtils.isEmpty(strArrA[1]) && CrashAnalysis.this.t != null) {
                    String strD = CrashAnalysis.d(strArrA[1], this.c);
                    String strC = CrashAnalysis.c(strArrA[1], this.c);
                    String strB = b(strArrA[2]);
                    long jD = CrashAnalysis.d(strArrA[1]);
                    Map<String, Object> mapC = CrashAnalysis.c(strArrA[0]);
                    s.a(CrashAnalysis.a, "fileName: " + absolutePath);
                    s.a(CrashAnalysis.a, "feature id: " + strD);
                    s.a(CrashAnalysis.a, "error: " + strC);
                    s.a(CrashAnalysis.a, "crash app version: " + strB);
                    s.a(CrashAnalysis.a, "crashTimeStamp: " + jD);
                    StringBuilder sb = new StringBuilder();
                    sb.append("dynamicCommonProperty: ");
                    sb.append(mapC == null ? "" : mapC.toString());
                    s.a(CrashAnalysis.a, sb.toString());
                    CrashAnalysis.this.t.a(strArrA[1], strC, this.c, strB, strD, jD, mapC, false);
                    n.a(new File(absolutePath));
                    s.a(CrashAnalysis.a, "remove reported crash file");
                }
            }
        }

        public boolean a(File file) {
            if (!file.getName().contains(this.b)) {
                return false;
            }
            this.a.add(file);
            return true;
        }
    }

    private CrashAnalysis(Context context, c cVar) {
        try {
            Object objNewInstance = Class.forName("xcrash.XCrash$InitParameters").getConstructor(new Class[0]).newInstance(new Object[0]);
            Boolean bool = Boolean.FALSE;
            a(objNewInstance, "setNativeDumpAllThreads", bool);
            a(objNewInstance, "setLogDir", getCrashPath());
            a(objNewInstance, "setNativeDumpMap", bool);
            a(objNewInstance, "setNativeDumpFds", bool);
            a(objNewInstance, "setJavaDumpAllThreads", bool);
            if (DeviceUtil.g()) {
                s.a(a, "isMiTv:true");
                a(objNewInstance, "setAnrCheckProcessState", bool);
            } else {
                s.a(a, "isMiTv:false");
            }
            Class.forName("xcrash.XCrash").getDeclaredMethod("init", Context.class, objNewInstance.getClass()).invoke(null, context.getApplicationContext(), objNewInstance);
            s.a(a, "XCrash init success");
            this.v = true;
        } catch (Throwable th) {
            s.c(a, "XCrash init failed: ", th);
        }
        this.t = cVar;
        this.s = new FileProcessor[]{new FileProcessor("java"), new FileProcessor(ANR_CRASH), new FileProcessor(NATIVE_CRASH)};
        a();
    }

    private void a() {
        try {
            if (d()) {
                e();
            } else {
                s.a(a, "no crash file found");
            }
        } catch (Throwable th) {
            s.b(a, "processCrash error: ", th);
        }
    }

    private void a(long j2) throws Throwable {
        n.a(g + z.b(), String.valueOf((af.b() * 100) + j2));
    }

    public static void a(Context context) {
        try {
            a.a(context.getApplicationContext());
            Class.forName("xcrash.XCrash").getDeclaredMethod("initHooker", Context.class, String.class).invoke(null, context.getApplicationContext(), getCrashPath());
            s.a(a, "registerHook succeeded");
        } catch (Throwable th) {
            s.c(a, "registerHook failed: ", th);
        }
    }

    private void a(Object obj, String str, Object obj2) throws Exception {
        obj.getClass().getDeclaredMethod(str, obj2.getClass() == Boolean.class ? Boolean.TYPE : obj2.getClass()).invoke(obj, obj2);
    }

    private long b() throws Throwable {
        String strA = n.a(g + z.b());
        long j2 = TextUtils.isEmpty(strA) ? 0L : Long.parseLong(strA);
        if (j2 == 0) {
            s.a(a, "no ticket data found, return max count");
            return 10L;
        }
        long jB = af.b();
        if (j2 / 100 != jB) {
            s.a(a, "no today's ticket, return max count");
            return 10L;
        }
        long j3 = j2 - (jB * 100);
        s.a(a, "today's remain ticket is " + j3);
        return j3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(String str, String str2) {
        int iIndexOf;
        int iIndexOf2;
        if (TextUtils.isEmpty(str)) {
            return "uncategoried";
        }
        try {
            if (str2.equals(ANR_CRASH)) {
                int iIndexOf3 = str.indexOf(" tid=1 ");
                return (iIndexOf3 == -1 || (iIndexOf = str.indexOf("\n  at ", iIndexOf3)) == -1 || (iIndexOf2 = str.indexOf(10, iIndexOf + 6)) == -1) ? "uncategoried" : str.substring(iIndexOf + 2, iIndexOf2);
            }
            int iIndexOf4 = str.indexOf(c);
            if (iIndexOf4 != -1) {
                int i2 = iIndexOf4 + 15;
                int iIndexOf5 = str.indexOf("\n\n", i2);
                return iIndexOf5 != -1 ? str.substring(i2, iIndexOf5) : "uncategoried";
            }
            String strC = "";
            if (str2.equals(NATIVE_CRASH)) {
                strC = b.b(str);
            } else if (str2.equals("java")) {
                strC = b.c(str);
            }
            return !TextUtils.isEmpty(strC) ? strC : "uncategoried";
        } catch (Exception e2) {
            s.b(a, "getErrorReasonString error: " + e2.toString());
            return "uncategoried";
        }
    }

    private List<File> c() {
        File[] fileArrListFiles = new File(getCrashPath()).listFiles();
        if (fileArrListFiles == null) {
            s.a(a, "this path does not denote a directory, or if an I/O error occurs.");
            return null;
        }
        List<File> listAsList = Arrays.asList(fileArrListFiles);
        Collections.sort(listAsList, new Comparator<File>() { // from class: com.xiaomi.onetrack.CrashAnalysis.1
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                return (int) (file.lastModified() - file2.lastModified());
            }
        });
        int size = listAsList.size();
        if (size <= 20) {
            return listAsList;
        }
        int i2 = size - 20;
        for (int i3 = 0; i3 < i2; i3++) {
            n.a(listAsList.get(i3));
        }
        return listAsList.subList(i2, size);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, Object> c(String str) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str.substring(1));
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.opt(next));
                }
            } catch (Exception e2) {
                s.b(a, "getDynamicCommonProperty error：", e2);
            }
        }
        return map;
    }

    public static String calculateJavaDigest(String str) {
        String[] strArrSplit = str.replaceAll("\\t", "").split("\\n");
        StringBuilder sb = new StringBuilder();
        int iMin = Math.min(strArrSplit.length, 20);
        for (int i2 = 0; i2 < iMin; i2++) {
            strArrSplit[i2] = strArrSplit[i2].replaceAll(p, "$1XX").replaceAll("\\$[0-9a-fA-F]{1,10}@[0-9a-fA-F]{1,10}|@[0-9a-fA-F]{1,10}|0x[0-9a-fA-F]{1,10}", "XX").replaceAll(o, "");
        }
        for (int i3 = 0; i3 < iMin && (!strArrSplit[i3].contains("...") || !strArrSplit[i3].contains("more")); i3++) {
            sb.append(strArrSplit[i3]);
            sb.append('\n');
        }
        return d.h(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long d(String str) {
        int i2;
        int iIndexOf;
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            int iIndexOf2 = str.indexOf(d);
            if (iIndexOf2 == -1 || (iIndexOf = str.indexOf("'\n", (i2 = iIndexOf2 + 13))) == -1) {
                return 0L;
            }
            return b.a(str.substring(i2, iIndexOf));
        } catch (Exception e2) {
            s.b(a, "getCrashTimeStamp error: " + e2.toString());
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(String str, String str2) {
        int i2;
        int iIndexOf;
        int iIndexOf2;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            if (str2.equals(ANR_CRASH)) {
                int iIndexOf3 = str.indexOf(" tid=1 ");
                return (iIndexOf3 == -1 || (iIndexOf2 = str.indexOf("\n\n", iIndexOf3)) == -1) ? "" : calculateJavaDigest(str.substring(iIndexOf3, iIndexOf2));
            }
            int iIndexOf4 = str.indexOf(b);
            return (iIndexOf4 == -1 || (iIndexOf = str.indexOf("\n\n", (i2 = iIndexOf4 + 23))) == -1) ? "" : str.substring(i2, iIndexOf);
        } catch (Exception e2) {
            s.b(a, "calculateFeatureId error: " + e2.toString());
            return "";
        }
    }

    private boolean d() throws Throwable {
        boolean z;
        long j2;
        List<File> listC = c();
        long jB = b();
        if (listC == null || listC.size() <= 0) {
            z = false;
        } else {
            long jCurrentTimeMillis = System.currentTimeMillis();
            String strA = n.a(f + z.b());
            long j3 = 0;
            long j4 = TextUtils.isEmpty(strA) ? 0L : Long.parseLong(strA);
            long j5 = af.a;
            if (j4 > jCurrentTimeMillis) {
                j4 = jCurrentTimeMillis - af.a;
            }
            long j6 = 0;
            boolean z2 = false;
            for (File file : listC) {
                long jLastModified = file.lastModified();
                if (jLastModified < jCurrentTimeMillis - j5 || jLastModified > jCurrentTimeMillis) {
                    j2 = j3;
                    s.a(a, "remove obsolete crash files: " + file.getName());
                    n.a(file);
                } else if (jLastModified <= j4) {
                    s.a(a, "found already reported crash file, ignore");
                    j2 = j3;
                } else {
                    j2 = j3;
                    if (jB > j3) {
                        for (FileProcessor fileProcessor : this.s) {
                            if (fileProcessor.a(file)) {
                                s.a(a, "find crash file:" + file.getName());
                                jB--;
                                if (j6 < jLastModified) {
                                    z2 = true;
                                    j6 = jLastModified;
                                } else {
                                    z2 = true;
                                }
                            }
                        }
                    }
                }
                j3 = j2;
                j5 = af.a;
            }
            if (j6 > j3) {
                n.a(f + z.b(), String.valueOf(j6));
            }
            z = z2;
        }
        if (z) {
            a(jB);
        }
        return z;
    }

    private void e() throws Throwable {
        for (FileProcessor fileProcessor : this.s) {
            fileProcessor.a();
        }
    }

    public static String getCrashPath() {
        return n.a();
    }

    public static CrashAnalysis getInstance(Context context, c cVar) {
        if (u == null) {
            synchronized (CrashAnalysis.class) {
                try {
                    if (u == null) {
                        u = new CrashAnalysis(context, cVar);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return u;
    }

    public boolean isSupport() {
        return this.v;
    }
}

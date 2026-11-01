package miuix.device;

import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.util.a;
import dalvik.system.PathClassLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes2.dex */
public class DeviceUtils {
    public static int HIGH;
    public static int LOW;
    public static int MIDDLE;
    public static float PAD_THRESHOLD;
    public static int UNKNOWN;
    public static Application application;
    public static Context applicationContext;
    public static Constructor mCompuConstructor;
    public static Constructor mConstructor;
    public static Method mGetCompuVersion;
    public static Method mGetCompuVersionV2;
    public static Method mGetCurrentCompuLevel;
    public static Method mGetCurrentComputilityLevelV2;
    public static Method mGetMiuiLiteVersion;
    public static Method mGetMiuiMiddleVersion;
    public static Method mInitCompuLevel;
    public static Class sPerfClass;
    public static PathClassLoader sPerfClassLoader;
    public static Class sPerfCompuClass;
    public static Point sScreenRealSize;
    public static final Pattern SM_PATTERN = Pattern.compile("Inc ([A-Z]+)([\\d]+)");
    public static final Pattern MT_PATTERN = Pattern.compile("MT([\\d]{2})([\\d]+)");
    public static Boolean MIUI_LITE_ROM = null;
    public static int MIUI_LITE_VERSION = -2;
    public static int MIUI_MIDDLE_VERSION = -2;
    public static Boolean MIUI_LITE_STOCK_PLUS = null;
    public static int sMultiDisplayType = -1;
    public static Boolean sIsTablet = null;
    public static Boolean sIsPrimaryScreenOled = null;
    public static Boolean sIsSecondaryScreenOled = null;
    public static int mLevel = -1;
    public static int mCpuLevel = -1;
    public static int mGpuLevel = -1;
    public static int mRamLevel = -1;
    public static int mTotalRam = Integer.MAX_VALUE;
    public static final String[] STOCK_DEVICE = {"cactus", "cereus", "pine", "olive", "ginkgo", "olivelite", "olivewood", "willow", "wayne", "dandelion", "angelica", "angelicain", "whyred", "tulip", "onc", "onclite", "lavender", "lotus", "laurus", "merlinnfc", "merlin", "lancelot", "citrus", "pomelo", "lemon", "shiva", "lime", "cannon", "curtana", "durandal", "excalibur", "joyeuse", "gram", "sunny", "mojito", "rainbow", "cattail", "angelican", "camellia"};
    public static Object mPerf = null;
    public static Object mCompuPerf = null;
    public static Method mGetDeviceLevel = null;
    public static Method mGetDeviceLevelForWhole = null;
    public static Method mIsSupportPrune = null;
    public static int mCompuLevel = -1;
    public static int mCompuVersion = -1;
    public static int DEV_STANDARD_VERSION = 1;
    public static int mLastVersion = 1;
    public static boolean initDeviceLevelInfo = false;
    public static int TYPE_RAM = 1;
    public static int TYPE_CPU = 2;
    public static int TYPE_GPU = 3;

    public static class CpuInfo {
        public int architecture;
        public int id;
        public int implementor;
        public int maxFreq;
        public int part;

        public String toString() {
            return "CpuInfo{id=" + this.id + ", implementor=" + Integer.toHexString(this.implementor) + ", architecture=" + this.architecture + ", part=" + Integer.toHexString(this.part) + ", maxFreq=" + this.maxFreq + '}';
        }
    }

    public static class CpuStats {
        public int bigCoreCount;
        public int level = -1;
        public int maxFreq;
        public int smallCoreCount;

        public String toString() {
            return "CpuStats{level=" + this.level + ", maxFreq=" + this.maxFreq + ", bigCoreCount=" + this.bigCoreCount + ", smallCoreCount=" + this.smallCoreCount + '}';
        }
    }

    static {
        mConstructor = null;
        mCompuConstructor = null;
        mGetMiuiLiteVersion = null;
        mGetMiuiMiddleVersion = null;
        mInitCompuLevel = null;
        mGetCompuVersion = null;
        mGetCompuVersionV2 = null;
        mGetCurrentCompuLevel = null;
        mGetCurrentComputilityLevelV2 = null;
        try {
            if (Build.VERSION.SDK_INT > 33) {
                PathClassLoader pathClassLoader = new PathClassLoader("/system_ext/framework/MiuiBooster.jar", ClassLoader.getSystemClassLoader());
                sPerfClassLoader = pathClassLoader;
                sPerfClass = pathClassLoader.loadClass("com.miui.performance.DeviceLevelUtils");
            } else {
                PathClassLoader pathClassLoader2 = new PathClassLoader("/system/framework/MiuiBooster.jar", ClassLoader.getSystemClassLoader());
                sPerfClassLoader = pathClassLoader2;
                sPerfClass = pathClassLoader2.loadClass("com.miui.performance.DeviceLevelUtils");
            }
            mConstructor = sPerfClass.getConstructor(Context.class);
            mGetMiuiLiteVersion = sPerfClass.getDeclaredMethod("getMiuiLiteVersion", new Class[0]);
        } catch (Exception e) {
            Log.e("DeviceUtils", "static init(): Load Class Exception:" + e);
        }
        PathClassLoader pathClassLoader3 = sPerfClassLoader;
        if (pathClassLoader3 != null) {
            try {
                Class<?> clsLoadClass = pathClassLoader3.loadClass("com.miui.performance.ComputilityLevelUtils");
                sPerfCompuClass = clsLoadClass;
                mCompuConstructor = clsLoadClass.getConstructor(new Class[0]);
                mInitCompuLevel = sPerfCompuClass.getDeclaredMethod("initComputilityLevel", new Class[0]);
                mGetCompuVersion = sPerfCompuClass.getDeclaredMethod("getComputilityVersion", new Class[0]);
                mGetCurrentCompuLevel = sPerfCompuClass.getDeclaredMethod("getComputilityLevel", new Class[0]);
                mGetCompuVersionV2 = sPerfCompuClass.getDeclaredMethod("getComputilityVersionV2", new Class[0]);
                mGetCurrentComputilityLevelV2 = sPerfCompuClass.getDeclaredMethod("getComputilityLevelV2", new Class[0]);
            } catch (Exception e2) {
                Log.e("DeviceUtils", "static init: ComputilityLevel Load Class Exception:" + e2);
            }
        }
        try {
            mGetMiuiMiddleVersion = sPerfClass.getDeclaredMethod("getMiuiMiddleVersion", new Class[0]);
        } catch (Exception e3) {
            Log.e("DeviceUtils", "static init(): Load MiuiMiddle Class Exception:" + e3);
        }
        if (sPerfClass == null) {
            Log.e("DeviceUtils", "static init(): MiuiBooster is not in this rom");
        }
    }

    public static CpuInfo createCpuInfo(String str) throws Throwable {
        CpuInfo cpuInfo = new CpuInfo();
        int i = Integer.parseInt(str);
        cpuInfo.id = i;
        String contentFromFileInfo = getContentFromFileInfo(String.format(Locale.ENGLISH, "/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_max_freq", Integer.valueOf(i)));
        if (contentFromFileInfo != null) {
            cpuInfo.maxFreq = Integer.parseInt(contentFromFileInfo);
        }
        return cpuInfo;
    }

    public static void decideLevel(CpuStats cpuStats) {
        if (cpuStats.level != -1) {
            return;
        }
        if (cpuStats.bigCoreCount < 4) {
            if (cpuStats.maxFreq > 2300000) {
                cpuStats.level = 1;
                return;
            } else {
                cpuStats.level = 0;
                return;
            }
        }
        int i = cpuStats.maxFreq;
        if (i > 2700000) {
            cpuStats.level = 2;
        } else if (i > 2300000) {
            cpuStats.level = 1;
        } else {
            cpuStats.level = 0;
        }
    }

    public static void doCpuStats(CpuStats cpuStats, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CpuInfo cpuInfo = (CpuInfo) it.next();
            if (cpuInfo.architecture < 8) {
                cpuStats.level = 0;
            }
            int i = cpuInfo.maxFreq;
            if (i > cpuStats.maxFreq) {
                cpuStats.maxFreq = i;
            }
            if (i >= 2000000) {
                cpuStats.bigCoreCount++;
            } else {
                cpuStats.smallCoreCount++;
            }
        }
        decideLevel(cpuStats);
    }

    public static Context getAppContext() {
        if (applicationContext == null) {
            try {
                Application application2 = (Application) ActivityThread.class.getMethod("currentApplication", new Class[0]).invoke(null, null);
                application = application2;
                if (application2 != null) {
                    applicationContext = application2.getApplicationContext();
                }
            } catch (Exception e) {
                Log.e("DeviceUtils", "android.app.ActivityThread Exception:" + e);
            }
        }
        if (applicationContext == null) {
            try {
                Application application3 = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke(null, null);
                application = application3;
                if (application3 != null) {
                    applicationContext = application3.getApplicationContext();
                }
            } catch (Exception e2) {
                Log.e("DeviceUtils", "android.app.AppGlobals Exception:" + e2);
            }
        }
        return applicationContext;
    }

    public static String getContentFromFileInfo(String str) throws Throwable {
        FileInputStream fileInputStream;
        Throwable th;
        BufferedReader bufferedReader;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                try {
                    String line = bufferedReader.readLine();
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                    }
                    try {
                        bufferedReader.close();
                    } catch (IOException unused2) {
                    }
                    return line;
                } catch (IOException unused3) {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused4) {
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused5) {
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused6) {
                        }
                    }
                    if (bufferedReader == null) {
                        throw th;
                    }
                    try {
                        bufferedReader.close();
                        throw th;
                    } catch (IOException unused7) {
                        throw th;
                    }
                }
            } catch (IOException unused8) {
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
            }
        } catch (IOException unused9) {
            bufferedReader = null;
            fileInputStream = null;
        } catch (Throwable th4) {
            fileInputStream = null;
            th = th4;
            bufferedReader = null;
        }
    }

    public static void getCpuInfo(String str, String str2, CpuInfo cpuInfo) {
        if (str.contains("CPU implementer")) {
            cpuInfo.implementor = toInt(str2);
        } else if (str.contains("CPU architecture")) {
            cpuInfo.architecture = toInt(str2);
        } else if (str.contains("CPU part")) {
            cpuInfo.part = toInt(str2);
        }
    }

    public static List getCpuInfoList() {
        ArrayList arrayList = new ArrayList();
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            CpuInfo line = null;
            while (scanner.hasNextLine()) {
                try {
                    String[] strArrSplit = scanner.nextLine().split(": ");
                    if (strArrSplit.length > 1) {
                        line = parseLine(strArrSplit, arrayList, line);
                    }
                } catch (Throwable th) {
                    try {
                        scanner.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            scanner.close();
            return arrayList;
        } catch (Exception e) {
            Log.e("DeviceUtils", "getChipSetFromCpuInfo failed", e);
            return arrayList;
        }
    }

    public static int getCpuLevel() {
        int qualcommCpuLevel;
        String hardwareInfo = getHardwareInfo();
        if (hardwareInfo.length() > 0) {
            qualcommCpuLevel = hardwareInfo.contains("Qualcomm") ? getQualcommCpuLevel(hardwareInfo) : getMtkCpuLevel(hardwareInfo);
        } else {
            qualcommCpuLevel = -1;
        }
        return qualcommCpuLevel == -1 ? getCpuStats().level : qualcommCpuLevel;
    }

    public static CpuStats getCpuStats() {
        List cpuInfoList = getCpuInfoList();
        CpuStats cpuStats = new CpuStats();
        if (cpuInfoList.size() < 8) {
            cpuStats.level = 0;
        }
        doCpuStats(cpuStats, cpuInfoList);
        return cpuStats;
    }

    public static int getDeviceLevel() {
        return getDeviceLevel(DEV_STANDARD_VERSION);
    }

    public static int getDeviceLevel(int i) {
        int i2;
        if (mLastVersion == i && (i2 = mLevel) != -1) {
            return i2;
        }
        mLastVersion = i;
        int deviceLevel2 = getDeviceLevel2(i);
        mLevel = deviceLevel2;
        return deviceLevel2 != -1 ? deviceLevel2 : getDeviceLevel1();
    }

    public static int getDeviceLevel(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (i2 == TYPE_CPU) {
            if (mLastVersion == i && (i5 = mCpuLevel) != -1) {
                return i5;
            }
        } else if (i2 == TYPE_GPU) {
            if (mLastVersion == i && (i4 = mGpuLevel) != -1) {
                return i4;
            }
        } else if (i2 == TYPE_RAM && mLastVersion == i && (i3 = mRamLevel) != -1) {
            return i3;
        }
        int deviceLevel2 = getDeviceLevel2(i, i2);
        return deviceLevel2 != -1 ? setDeviceLevel(i, deviceLevel2, i2) : setDeviceLevel(i, getDeviceLevel1(i2), i2);
    }

    public static int getDeviceLevel1() {
        int i = mLevel;
        if (i != -1) {
            return i;
        }
        if (isMiuiLiteRom()) {
            mLevel = 0;
        } else {
            mLevel = getMinLevel(getDeviceLevel1(TYPE_CPU), getDeviceLevel1(TYPE_RAM), getDeviceLevel(DEV_STANDARD_VERSION, TYPE_GPU));
        }
        return mLevel;
    }

    public static int getDeviceLevel1(int i) {
        if (i != TYPE_RAM) {
            if (i == TYPE_CPU) {
                return getCpuLevel();
            }
            return -1;
        }
        int totalRam = getTotalRam();
        if (totalRam > 6) {
            return 2;
        }
        if (totalRam > 4) {
            return 1;
        }
        return totalRam > 0 ? 0 : -1;
    }

    public static int getDeviceLevel2(int i) {
        int iIntValue = -1;
        if (!initDeviceLevelInfo()) {
            return -1;
        }
        try {
            Object perf = getPerf();
            if (perf == null) {
                throw new Exception("perf is null!");
            }
            iIntValue = ((Integer) mGetDeviceLevelForWhole.invoke(perf, Integer.valueOf(i))).intValue();
            return transDeviceLevel(iIntValue);
        } catch (Exception e) {
            Log.e("DeviceUtils", "getDeviceLevel failed , e:" + e.toString());
        }
    }

    public static int getDeviceLevel2(int i, int i2) {
        int iIntValue = -1;
        if (!initDeviceLevelInfo()) {
            return -1;
        }
        try {
            Object perf = getPerf();
            if (perf == null) {
                throw new Exception("perf is null!");
            }
            iIntValue = ((Integer) mGetDeviceLevel.invoke(perf, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
            return transDeviceLevel(iIntValue);
        } catch (Exception e) {
            Log.e("DeviceUtils", "getDeviceLevel failed , e:" + e.toString());
        }
    }

    public static int getFloatingWindowHeightThreshold(int i) {
        if (isLhasaDevice()) {
            return 620;
        }
        return i;
    }

    public static String getHardwareInfo() {
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            while (scanner.hasNextLine()) {
                try {
                    String strNextLine = scanner.nextLine();
                    if (!scanner.hasNextLine()) {
                        String[] strArrSplit = strNextLine.split(": ");
                        if (strArrSplit.length > 1) {
                            String str = strArrSplit[1];
                            scanner.close();
                            return str;
                        }
                        Log.e("DeviceUtils", "getChipSetFromCpuInfo failed", e);
                        return "";
                    }
                } catch (Throwable th) {
                    try {
                        scanner.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            scanner.close();
            return "";
        } catch (Exception e) {
            Log.e("DeviceUtils", "getChipSetFromCpuInfo failed", e);
            return "";
        }
    }

    public static int getMinLevel(int... iArr) {
        if (iArr.length == 0) {
            return -1;
        }
        int i = iArr[0];
        for (int i2 : iArr) {
            if (i2 > -1 && i2 < i) {
                i = i2;
            }
        }
        return i;
    }

    public static int getMiuiLiteVersion() {
        int iIntValue;
        if (!isMiuiLiteRom()) {
            MIUI_LITE_VERSION = 0;
            return 0;
        }
        int i = MIUI_LITE_VERSION;
        if (i != -2) {
            return i;
        }
        try {
            Object perf = getPerf();
            if (perf == null) {
                throw new Exception("perf is null!");
            }
            iIntValue = ((Integer) getMiuiLiteVersionMethod().invoke(perf, new Object[0])).intValue();
            if (iIntValue >= 2) {
                MIUI_LITE_VERSION = iIntValue;
            } else {
                MIUI_LITE_VERSION = 1;
            }
            return MIUI_LITE_VERSION;
        } catch (Exception e) {
            Log.e("DeviceUtils", "getMiuiLiteVersion failed , e:" + e.toString());
            iIntValue = -1;
        }
    }

    public static Method getMiuiLiteVersionMethod() {
        if (mGetMiuiLiteVersion == null) {
            mGetMiuiLiteVersion = getPerfClass().getDeclaredMethod("getMiuiLiteVersion", new Class[0]);
        }
        return mGetMiuiLiteVersion;
    }

    public static int getMiuiMiddleVersion() {
        if (MIUI_MIDDLE_VERSION == -2) {
            try {
                Object objInvoke = mGetMiuiMiddleVersion.invoke(getPerf(), new Object[0]);
                if (objInvoke != null) {
                    MIUI_MIDDLE_VERSION = ((Integer) objInvoke).intValue();
                } else {
                    MIUI_MIDDLE_VERSION = -1;
                }
            } catch (Exception e) {
                MIUI_MIDDLE_VERSION = -1;
                Log.e("DeviceUtils", "getMiuiMiddleVersion failed , e:" + e.toString());
            }
        }
        return MIUI_MIDDLE_VERSION;
    }

    public static int getMtkCpuLevel(String str) {
        String strGroup;
        String strGroup2;
        Matcher matcher = MT_PATTERN.matcher(str);
        if (!matcher.find() || (strGroup = matcher.group(1)) == null || (strGroup2 = matcher.group(2)) == null) {
            return -1;
        }
        return (Integer.parseInt(strGroup) != 68 || Integer.parseInt(strGroup2) < 73) ? 0 : 1;
    }

    public static Object getPerf() {
        Constructor constructor;
        if (mPerf == null) {
            try {
                Context appContext = getAppContext();
                if (appContext == null || (constructor = mConstructor) == null) {
                    throw new Exception("getAppContext fail");
                }
                mPerf = constructor.newInstance(appContext);
            } catch (Exception e) {
                Log.e("DeviceUtils", "getPerf DeviceUtils(): newInstance Exception:" + e);
                e.printStackTrace();
            }
        }
        return mPerf;
    }

    public static Class getPerfClass() {
        return sPerfClass;
    }

    public static String getProductDevice() {
        return Build.DEVICE;
    }

    public static int getQualcommCpuLevel(String str) {
        String strGroup;
        String strGroup2;
        Matcher matcher = SM_PATTERN.matcher(str);
        if (!matcher.find() || (strGroup = matcher.group(1)) == null || (strGroup2 = matcher.group(2)) == null) {
            return -1;
        }
        String lowerCase = strGroup.toLowerCase(Locale.ENGLISH);
        if (!lowerCase.equals("sm")) {
            if (lowerCase.equals("sdm")) {
                return Integer.parseInt(strGroup2.substring(0, 1)) >= 7 ? 1 : 0;
            }
            return lowerCase.equals("msm") ? 0 : -1;
        }
        int i = Integer.parseInt(strGroup2.substring(0, 1));
        if (i >= 8) {
            return 2;
        }
        return i >= 7 ? 1 : 0;
    }

    public static Object getStaticObjectField(Class cls, String str, Class cls2) throws NoSuchFieldException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(null);
    }

    public static int getTotalRam() {
        if (mTotalRam == Integer.MAX_VALUE) {
            try {
                mTotalRam = (int) (((((Long) Class.forName("miui.util.HardwareInfo").getMethod("getTotalPhysicalMemory", new Class[0]).invoke(null, new Object[0])).longValue() / 1024) / 1024) / 1024);
            } catch (Throwable th) {
                Log.e("DeviceUtils", th.getMessage());
                mTotalRam = 0;
            }
        }
        return mTotalRam;
    }

    public static boolean initDeviceLevelInfo() {
        boolean z = initDeviceLevelInfo;
        if (z) {
            return z;
        }
        try {
            Class perfClass = getPerfClass();
            if (perfClass == null) {
                return initDeviceLevelInfo;
            }
            Class cls = Integer.TYPE;
            mGetDeviceLevel = perfClass.getDeclaredMethod("getDeviceLevel", cls, cls);
            mGetDeviceLevelForWhole = perfClass.getDeclaredMethod("getDeviceLevel", cls);
            TYPE_RAM = ((Integer) getStaticObjectField(perfClass, "DEVICE_LEVEL_FOR_RAM", cls)).intValue();
            TYPE_CPU = ((Integer) getStaticObjectField(perfClass, "DEVICE_LEVEL_FOR_CPU", cls)).intValue();
            TYPE_GPU = ((Integer) getStaticObjectField(perfClass, "DEVICE_LEVEL_FOR_GPU", cls)).intValue();
            LOW = ((Integer) getStaticObjectField(perfClass, "LOW_DEVICE", cls)).intValue();
            MIDDLE = ((Integer) getStaticObjectField(perfClass, "MIDDLE_DEVICE", cls)).intValue();
            HIGH = ((Integer) getStaticObjectField(perfClass, "HIGH_DEVICE", cls)).intValue();
            UNKNOWN = ((Integer) getStaticObjectField(perfClass, "DEVICE_LEVEL_UNKNOWN", cls)).intValue();
            initDeviceLevelInfo = true;
            return initDeviceLevelInfo;
        } catch (Exception e) {
            Log.e("DeviceUtils", "initDeviceLevelInfo Fail: Exception:" + e);
        }
    }

    public static boolean isFoldDevice() {
        if (sMultiDisplayType == -1) {
            sMultiDisplayType = LiteSystemProperties.getInt("persist.sys.muiltdisplay_type", 1);
        }
        return sMultiDisplayType == 2;
    }

    public static boolean isInternalScreen(Context context) {
        return isFoldDevice() && isWideScreen(context);
    }

    public static boolean isLhasaDevice() {
        return "lhasa".equals(Build.DEVICE);
    }

    public static boolean isLiteV1StockPlus() {
        if (MIUI_LITE_STOCK_PLUS == null) {
            MIUI_LITE_STOCK_PLUS = Boolean.valueOf(a.i.contentEquals(LiteSystemProperties.get("ro.config.low_ram.support_miuilite_plus", "false")));
        }
        return MIUI_LITE_STOCK_PLUS.booleanValue();
    }

    public static boolean isMiuiLiteRom() {
        if (MIUI_LITE_ROM == null) {
            try {
                Boolean bool = (Boolean) Class.forName("miui.os.Build").getDeclaredField("IS_MIUI_LITE_VERSION").get(null);
                bool.booleanValue();
                MIUI_LITE_ROM = bool;
            } catch (Throwable th) {
                Log.i("DeviceUtils", "isMiuiLiteRom failed", th);
                MIUI_LITE_ROM = null;
            }
        }
        return Boolean.TRUE.equals(MIUI_LITE_ROM);
    }

    public static boolean isMiuiLiteV2() {
        return isMiuiLiteRom() && getMiuiLiteVersion() >= 2;
    }

    public static boolean isMiuiMiddle() {
        return getMiuiMiddleVersion() >= 1;
    }

    public static boolean isTablet() {
        if (sIsTablet == null) {
            sIsTablet = Boolean.valueOf(LiteSystemProperties.get("ro.build.characteristics", "").contains("tablet"));
        }
        return sIsTablet.booleanValue();
    }

    public static boolean isTablet(Context context) {
        if (sScreenRealSize == null || isFoldDevice()) {
            Point point = new Point();
            sScreenRealSize = point;
            ScreenUtils.getScreenSize(context, point);
            PAD_THRESHOLD = context.getResources().getDisplayMetrics().density * 600.0f;
        }
        Point point2 = sScreenRealSize;
        return ((float) Math.min(point2.x, point2.y)) >= PAD_THRESHOLD;
    }

    public static boolean isWideScreen(Context context) {
        if (sScreenRealSize == null || isFoldDevice()) {
            Point point = new Point();
            sScreenRealSize = point;
            ScreenUtils.getScreenSize(context, point);
            PAD_THRESHOLD = context.getResources().getDisplayMetrics().density * 600.0f;
        }
        Point point2 = sScreenRealSize;
        return ((float) Math.min(point2.x, point2.y)) >= PAD_THRESHOLD;
    }

    public static CpuInfo parseLine(String[] strArr, List list, CpuInfo cpuInfo) throws Throwable {
        String strTrim = strArr[1].trim();
        if (strArr[0].contains("processor") && TextUtils.isDigitsOnly(strTrim)) {
            CpuInfo cpuInfoCreateCpuInfo = createCpuInfo(strTrim);
            list.add(cpuInfoCreateCpuInfo);
            return cpuInfoCreateCpuInfo;
        }
        if (cpuInfo != null) {
            getCpuInfo(strArr[0], strTrim, cpuInfo);
        }
        return cpuInfo;
    }

    public static int setDeviceLevel(int i, int i2, int i3) {
        mLastVersion = i;
        if (i3 == TYPE_CPU) {
            mCpuLevel = i2;
            return i2;
        }
        if (i3 == TYPE_GPU) {
            mGpuLevel = i2;
            return i2;
        }
        if (i3 != TYPE_RAM) {
            return -1;
        }
        mRamLevel = i2;
        return i2;
    }

    public static int toInt(String str) {
        return str.startsWith("0x") ? Integer.parseInt(str.substring(2), 16) : Integer.parseInt(str);
    }

    public static int transDeviceLevel(int i) {
        if (i == LOW) {
            return 0;
        }
        if (i == MIDDLE) {
            return 1;
        }
        return i == HIGH ? 2 : -1;
    }
}

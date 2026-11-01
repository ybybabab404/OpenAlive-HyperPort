package com.miui.miwallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import com.google.gson.Gson;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.StyleInfo;
import com.miui.maml.folme.AnimatedPropertyType;
import com.miui.miwallpaper.linkage.LinkageVideoParam;
import com.miui.miwallpaper.sensor.SensorParams;
import com.xiaomi.onetrack.util.ac;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import miui.os.Build;
import miui.util.MiuiMultiDisplayTypeInfo;

/* JADX INFO: loaded from: classes2.dex */
@SuppressLint({"InlinedApi, NewApi, MissingPermission"})
public class MiuiWallpaperManager {
    public static final int[] DEFAULT_CLOCK_TYPE_INFO;
    public static final boolean IS_FLIP_DEVICE;
    public static final boolean IS_FOLDABLE_DEVICE;
    public static final boolean IS_FOLD_DEVICE;
    public static final int MI_WALLPAPER_WHICH_ALL;
    public static final String TEMP_WALLPAPER;
    public static final String TEMP_WALLPAPER_DIR;
    public static final Executor WALLPAPER_INIT_EXECUTOR;
    public static volatile MiuiWallpaperManager sInstance;
    public boolean mAlphaVisible;
    public final Context mContext;
    public final boolean mInitSync;
    public volatile IMiuiWallpaperManagerService mService;
    public final UserHandle mUserHandle;
    public volatile Handler mWorkHandler;
    public HandlerThread mWorker;
    public static final ComponentName DEFAULT_WALLPAPER_COMPONENT = new ComponentName("com.miui.miwallpaper", "com.miui.miwallpaper.wallpaperservice.ImageWallpaper");
    public static final ComponentName MAML_WALLPAPER_COMPONENT = new ComponentName("com.miui.miwallpaper", "com.miui.miwallpaper.MiWallpaper");
    public final Object mLock = new Object();
    public final Deque mServiceStartHistory = new LinkedList();
    public final SimpleDateFormat mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    public final AtomicInteger mReconnectCount = new AtomicInteger(0);
    public int mServerVersionCode = 0;
    public float mServerVersion = 0.0f;
    public volatile boolean mServiceConnected = false;
    public volatile boolean mRebindServiceAllow = true;
    public volatile boolean mDestroyed = false;
    public IMiuiKeyguardWallpaperCallback mKeyguardCallback = null;
    public Surface mNormalSurface = null;
    public Surface mAlphaSurface = null;
    public boolean mNotifyInitCompleteStarted = false;
    public final ServiceConnection mServiceConnection = new AnonymousClass1();
    public final BroadcastReceiver mPackageUpdateReceiver = new BroadcastReceiver() { // from class: com.miui.miwallpaper.MiuiWallpaperManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data;
            String action = intent.getAction();
            if (("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_DATA_CLEARED".equals(action) || "android.intent.action.PACKAGE_RESTARTED".equals(action)) && (data = intent.getData()) != null && "com.miui.miwallpaper".equals(data.getSchemeSpecificPart())) {
                Log.i("MiuiWallpaperManager", "package update: action = " + action);
                MiuiWallpaperManager.this.bindService("doPackagesChanged");
            }
        }
    };
    public BroadcastReceiver mProcessReceiver = null;
    public final Runnable mRebindTask = new Runnable() { // from class: com.miui.miwallpaper.MiuiWallpaperManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$0();
        }
    };
    public final HashSet mAsyncInitCallbacks = new HashSet();
    public final HashSet mSyncInitCallbacks = new HashSet();
    public final HashMap mWallpaperChangeCallbacks = new HashMap();
    public final Runnable mResetRCountTask = new Runnable() { // from class: com.miui.miwallpaper.MiuiWallpaperManager$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$2();
        }
    };
    public final Handler mMHandler = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: com.miui.miwallpaper.MiuiWallpaperManager$1, reason: invalid class name */
    public class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {
        }

        public final /* synthetic */ void lambda$notifyInitComplete$0() {
            synchronized (MiuiWallpaperManager.this.mLock) {
                notifyAllCallback(MiuiWallpaperManager.this.mAsyncInitCallbacks);
            }
        }

        public final void notifyAllCallback(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                MiuiWallpaperManagerCallback miuiWallpaperManagerCallback = (MiuiWallpaperManagerCallback) it.next();
                if (miuiWallpaperManagerCallback != null) {
                    miuiWallpaperManagerCallback.onMiuiWallpaperInitialized(MiuiWallpaperManager.sInstance);
                }
                it.remove();
            }
        }

        public final void notifyInitComplete() {
            synchronized (MiuiWallpaperManager.this.mLock) {
                MiuiWallpaperManager.this.mNotifyInitCompleteStarted = true;
                notifyAllCallback(MiuiWallpaperManager.this.mSyncInitCallbacks);
            }
            MiuiWallpaperManager.this.mMHandler.post(new Runnable() { // from class: com.miui.miwallpaper.MiuiWallpaperManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyInitComplete$0();
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MiuiWallpaperManager.this.mService = IMiuiWallpaperManagerService.Stub.asInterface(iBinder);
            MiuiWallpaperManager.this.mServiceConnected = true;
            MiuiWallpaperManager miuiWallpaperManager = MiuiWallpaperManager.this;
            miuiWallpaperManager.mServerVersionCode = miuiWallpaperManager.getSdkVersionCodeInternal();
            MiuiWallpaperManager miuiWallpaperManager2 = MiuiWallpaperManager.this;
            miuiWallpaperManager2.mServerVersion = miuiWallpaperManager2.getSdkVersionInternal();
            Log.i("MiuiWallpaperManager", "onServiceConnected::componentName=" + componentName + " instance=" + MiuiWallpaperManager.sInstance + " asyncSize=" + MiuiWallpaperManager.this.mAsyncInitCallbacks.size() + " syncSize=" + MiuiWallpaperManager.this.mSyncInitCallbacks.size() + " versionCode=" + MiuiWallpaperManager.this.mServerVersionCode + " version=" + MiuiWallpaperManager.this.mServerVersion + " con=" + MiuiWallpaperManager.this.mServiceConnection);
            MiuiWallpaperManager.this.tryRegisterWallpaperChangeCallback();
            MiuiWallpaperManager.this.tryRebindKeyguardCallback();
            StringBuilder sb = new StringBuilder();
            sb.append("onServiceConnected::mAlphaSurface = ");
            sb.append(MiuiWallpaperManager.this.mAlphaSurface);
            sb.append(", mNormalSurface = ");
            sb.append(MiuiWallpaperManager.this.mNormalSurface);
            Log.i("MiuiWallpaperManager", sb.toString());
            if (MiuiWallpaperManager.this.mAlphaSurface != null && MiuiWallpaperManager.this.mNormalSurface != null) {
                MiuiWallpaperManager miuiWallpaperManager3 = MiuiWallpaperManager.this;
                miuiWallpaperManager3.setLockscreenVideoSurface(miuiWallpaperManager3.mNormalSurface, MiuiWallpaperManager.this.mAlphaSurface, true);
                MiuiWallpaperManager miuiWallpaperManager4 = MiuiWallpaperManager.this;
                miuiWallpaperManager4.setLockscreenVideoSurfaceVisible(miuiWallpaperManager4.mAlphaVisible);
            }
            notifyInitComplete();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("MiuiWallpaperManager", "onServiceDisconnected: componentName=" + componentName);
            synchronized (MiuiWallpaperManager.this.mLock) {
                MiuiWallpaperManager.this.mNotifyInitCompleteStarted = false;
            }
            MiuiWallpaperManager.this.mServiceConnected = false;
            MiuiWallpaperManager.this.removeResetCallback();
            MiuiWallpaperManager.this.unbindService();
            MiuiWallpaperManager.this.bindService(true, "Disconnected");
        }
    }

    public interface MiuiWallpaperManagerCallback {
        void onMiuiWallpaperInitialized(MiuiWallpaperManager miuiWallpaperManager);
    }

    public static class SyncMiuiWallpaperManagerCallback implements MiuiWallpaperManagerCallback {
        public final CountDownLatch mLatch = new CountDownLatch(1);
        public final MiuiWallpaperManagerCallback miuiWallpaperManagerCallback;

        public SyncMiuiWallpaperManagerCallback(MiuiWallpaperManagerCallback miuiWallpaperManagerCallback) {
            this.miuiWallpaperManagerCallback = miuiWallpaperManagerCallback;
        }

        @Override // com.miui.miwallpaper.MiuiWallpaperManager.MiuiWallpaperManagerCallback
        public void onMiuiWallpaperInitialized(MiuiWallpaperManager miuiWallpaperManager) {
            MiuiWallpaperManagerCallback miuiWallpaperManagerCallback = this.miuiWallpaperManagerCallback;
            if (miuiWallpaperManagerCallback != null) {
                miuiWallpaperManagerCallback.onMiuiWallpaperInitialized(miuiWallpaperManager);
            }
            this.mLatch.countDown();
        }

        public void waitBindingSuccess() {
            try {
                if (this.mLatch.await(3500L, TimeUnit.MILLISECONDS)) {
                    return;
                }
                Log.e("MiuiWallpaperManager", "waitBindingSuccess: timeout", new Throwable());
            } catch (InterruptedException e) {
                Log.e("MiuiWallpaperManager", "waitBindingSuccess: occur error", e);
            }
        }
    }

    public static class WallpaperApplierBuilder {
        public String clockStyleType;
        public String content;
        public boolean isVideoLoop;
        public LinkageVideoParam linkageVideoData;
        public final MiuiWallpaperManager manager;
        public int multiWhich;
        public SensorParams sensorParams;
        public Object source1;
        public Object source2;
        public Object source3;
        public Object source4;
        public String type;
        public String videoPath;
        public boolean supportDark = true;
        public ComponentName componentName = MiuiWallpaperManager.DEFAULT_WALLPAPER_COMPONENT;
        public boolean carousel = true;
        public int effectId = -1;
        public int[] clockTypeInfo = MiuiWallpaperManager.DEFAULT_CLOCK_TYPE_INFO;
        public int blurState = -1;
        public boolean supportMatting = false;
        public int doodleStatus = -1;
        public boolean isWallpaperFileChanged = true;
        public int originBitmapWidth = 0;
        public int originBitmapHeight = 0;
        public int allKeyFrame = -1;

        public WallpaperApplierBuilder(MiuiWallpaperManager miuiWallpaperManager) {
            this.manager = miuiWallpaperManager;
        }

        public boolean apply() {
            return applyForResult() == 0;
        }

        public int applyForResult() {
            if (this.isWallpaperFileChanged) {
                return CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(this.type) ? this.manager.setWallpaper(1, CategoryConstants.CATEGORY_SUPER_WALLPAPER, this.source1, this.source2, this.componentName, this.videoPath, this.content, this.isVideoLoop, this.carousel, this.supportDark, this.effectId, this.blurState, this.supportMatting, this.clockStyleType, this.clockTypeInfo, this.originBitmapWidth, this.originBitmapHeight, this.linkageVideoData, this.doodleStatus, this.sensorParams, this.allKeyFrame) | this.manager.setWallpaper(2, CategoryConstants.CATEGORY_SUPER_WALLPAPER, this.source3, this.source4, this.componentName, this.videoPath, this.content, this.isVideoLoop, this.carousel, this.supportDark, this.effectId, this.blurState, this.supportMatting, this.clockStyleType, this.clockTypeInfo, this.originBitmapWidth, this.originBitmapHeight, this.linkageVideoData, this.doodleStatus, this.sensorParams, this.allKeyFrame) : this.manager.setWallpaper(this.multiWhich, this.type, this.source1, this.source2, this.componentName, this.videoPath, this.content, this.isVideoLoop, this.carousel, this.supportDark, this.effectId, this.blurState, this.supportMatting, this.clockStyleType, this.clockTypeInfo, this.originBitmapWidth, this.originBitmapHeight, this.linkageVideoData, this.doodleStatus, this.sensorParams, this.allKeyFrame);
            }
            return this.manager.changeMiuiWallpaperInfo(this.multiWhich, this.effectId, this.blurState, this.supportMatting) ? 0 : 1001;
        }

        public WallpaperApplierBuilder clockStyleType(String str) {
            this.clockStyleType = str;
            return this;
        }

        public WallpaperApplierBuilder clockTypeInfo(int[] iArr) {
            this.clockTypeInfo = iArr;
            return this;
        }

        public WallpaperApplierBuilder effectId(int i) {
            this.effectId = i;
            return this;
        }

        public WallpaperApplierBuilder enableBlur(boolean z) {
            this.blurState = z ? 1 : 0;
            return this;
        }

        public WallpaperApplierBuilder gallery(Object obj, boolean z, String str) {
            this.type = "gallery";
            this.source1 = obj;
            this.carousel = z;
            this.content = str;
            return this;
        }

        public WallpaperApplierBuilder image(Object obj) {
            this.type = "image";
            this.source1 = obj;
            return this;
        }

        public WallpaperApplierBuilder sensor(Object obj, Object obj2, String str) {
            this.type = "sensor";
            this.source1 = obj;
            this.source2 = obj2;
            this.videoPath = str;
            return this;
        }

        public WallpaperApplierBuilder setDoodleStatus(int i) {
            this.doodleStatus = i;
            return this;
        }

        public WallpaperApplierBuilder setOriginBitmapWH(int i, int i2) {
            this.originBitmapWidth = i;
            this.originBitmapHeight = i2;
            return this;
        }

        public WallpaperApplierBuilder setSensorParams(SensorParams sensorParams) {
            this.sensorParams = sensorParams;
            return this;
        }

        public WallpaperApplierBuilder superWallpaper(ComponentName componentName, Object obj, Object obj2, Object obj3, Object obj4) {
            this.type = CategoryConstants.CATEGORY_SUPER_WALLPAPER;
            this.componentName = componentName;
            this.source1 = obj;
            this.source2 = obj2;
            this.source3 = obj3;
            this.source4 = obj4;
            return this;
        }

        public WallpaperApplierBuilder supportDark(boolean z) {
            this.supportDark = z;
            return this;
        }

        public WallpaperApplierBuilder supportMatting(boolean z) {
            this.supportMatting = z;
            return this;
        }

        public WallpaperApplierBuilder video(Object obj, String str, boolean z) {
            this.type = "video";
            this.videoPath = str;
            this.isVideoLoop = z;
            this.source1 = obj;
            return this;
        }

        public WallpaperApplierBuilder video(Object obj, String str, boolean z, int i) {
            this.allKeyFrame = i;
            return video(obj, str, z);
        }

        public WallpaperApplierBuilder wallpaperFileChanged(boolean z) {
            this.isWallpaperFileChanged = z;
            return this;
        }

        public WallpaperApplierBuilder which(int i) {
            this.multiWhich = i;
            return this;
        }
    }

    static {
        boolean z = isFoldDevices() || isFlipDevice();
        IS_FOLDABLE_DEVICE = z;
        IS_FOLD_DEVICE = isFoldDevices();
        IS_FLIP_DEVICE = isFlipDevice();
        MI_WALLPAPER_WHICH_ALL = z ? 15 : 3;
        TEMP_WALLPAPER = "/data/system/theme_magic/users/" + miui.os.UserHandle.myUserId() + "/wallpaper/data/temp_wallpaper";
        TEMP_WALLPAPER_DIR = "/data/system/theme_magic/users/" + miui.os.UserHandle.myUserId() + "/wallpaper/data/temp";
        DEFAULT_CLOCK_TYPE_INFO = new int[]{-1, -1};
        WALLPAPER_INIT_EXECUTOR = Executors.newSingleThreadExecutor();
    }

    public MiuiWallpaperManager(Context context, boolean z, UserHandle userHandle) {
        this.mContext = context;
        this.mInitSync = z;
        this.mUserHandle = userHandle;
    }

    public static void forAllWallpaper(Function function, int i) {
        int i2 = 1;
        while (i != 0) {
            if ((i2 & i) != 0) {
                function.apply(Integer.valueOf(i2));
                i ^= i2;
            }
            i2 <<= 1;
        }
    }

    public static Set getIntersection(String[] strArr, String[] strArr2, String[] strArr3) {
        HashSet hashSet = new HashSet(Arrays.asList(strArr));
        HashSet hashSet2 = new HashSet(Arrays.asList(strArr2));
        HashSet hashSet3 = new HashSet(Arrays.asList(strArr3));
        hashSet.retainAll(hashSet2);
        hashSet.retainAll(hashSet3);
        return hashSet;
    }

    public static void init(Context context, MiuiWallpaperManagerCallback miuiWallpaperManagerCallback) {
        synchronized (MiuiWallpaperManager.class) {
            try {
                if (sInstance == null) {
                    Log.i("MiuiWallpaperManager", "init...");
                    sInstance = new MiuiWallpaperManager(context, false, null);
                    sInstance.registerBroadcast();
                    sInstance.addInitCallback(miuiWallpaperManagerCallback);
                    sInstance.bindService("init");
                } else if (sInstance.mService != null) {
                    Log.i("MiuiWallpaperManager", "init: service ready");
                    miuiWallpaperManagerCallback.onMiuiWallpaperInitialized(sInstance);
                } else {
                    Log.i("MiuiWallpaperManager", "init... service not ready, wait for connect");
                    sInstance.addInitCallback(miuiWallpaperManagerCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static MiuiWallpaperManager initSync(Context context, MiuiWallpaperManagerCallback miuiWallpaperManagerCallback) {
        MiuiWallpaperManager miuiWallpaperManager;
        SyncMiuiWallpaperManagerCallback syncMiuiWallpaperManagerCallback = new SyncMiuiWallpaperManagerCallback(miuiWallpaperManagerCallback);
        synchronized (MiuiWallpaperManager.class) {
            try {
                if (sInstance == null) {
                    Log.i("MiuiWallpaperManager", "initSync...");
                    sInstance = new MiuiWallpaperManager(context, true, null);
                    sInstance.registerBroadcast();
                    sInstance.addInitCallback(syncMiuiWallpaperManagerCallback);
                    sInstance.bindService("initSync");
                } else {
                    if (sInstance.mService != null) {
                        Log.i("MiuiWallpaperManager", "initSync: service ready");
                        syncMiuiWallpaperManagerCallback.onMiuiWallpaperInitialized(sInstance);
                    } else {
                        Log.i("MiuiWallpaperManager", "initSync: service not ready, wait for connect");
                        sInstance.addInitCallback(syncMiuiWallpaperManagerCallback);
                    }
                    Log.i("MiuiWallpaperManager", "initSync: success");
                    miuiWallpaperManager = sInstance;
                }
                syncMiuiWallpaperManagerCallback.waitBindingSuccess();
                Log.i("MiuiWallpaperManager", "initSync: success");
                miuiWallpaperManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return miuiWallpaperManager;
    }

    public static boolean isFlipDevice() {
        try {
            Object objInvoke = MiuiMultiDisplayTypeInfo.class.getMethod("isFlipDevice", new Class[0]).invoke(MiuiMultiDisplayTypeInfo.class, new Object[0]);
            if (objInvoke instanceof Boolean) {
                return ((Boolean) objInvoke).booleanValue();
            }
        } catch (Exception e) {
            Log.e("MiuiWallpaperManager", "isFlipDevice fail", e);
        }
        return false;
    }

    public static boolean isFoldDevices() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return ((Integer) cls.getMethod("getInt", String.class, Integer.TYPE).invoke(cls, "persist.sys.muiltdisplay_type", 0)).intValue() == 2;
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "isFoldDevices fail : ", th);
            return false;
        }
    }

    public static boolean isLockWhich(int i) {
        return i == 2 || i == 8;
    }

    public static boolean isSingleWhich(int i) {
        boolean z = i == 1 || i == 2;
        if (IS_FOLDABLE_DEVICE) {
            z = z || i == 4 || i == 8;
        }
        if (!z) {
            Log.e("MiuiWallpaperManager", "is not single which: which = " + i);
        }
        return z;
    }

    public static boolean isSystemWhich(int i) {
        boolean z = !((i & 1) == 0 && (i & 2) == 0) && (i & 4) == 0 && (i & 8) == 0;
        if (!IS_FOLDABLE_DEVICE && !z) {
            Log.e("MiuiWallpaperManager", "isSystemWhich: which = " + i);
        }
        return z;
    }

    public static boolean isValidWhich(int i) {
        boolean z = i > 0 && i <= MI_WALLPAPER_WHICH_ALL;
        if (!z) {
            Log.e("MiuiWallpaperManager", "isValidWhich: which = " + i);
        }
        return z;
    }

    public static void staticDestroy() {
        synchronized (MiuiWallpaperManager.class) {
            try {
                if (sInstance != null) {
                    sInstance.destroy();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addInitCallback(final MiuiWallpaperManagerCallback miuiWallpaperManagerCallback) {
        if (miuiWallpaperManagerCallback == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mNotifyInitCompleteStarted) {
                    if (miuiWallpaperManagerCallback instanceof SyncMiuiWallpaperManagerCallback) {
                        miuiWallpaperManagerCallback.onMiuiWallpaperInitialized(sInstance);
                    } else {
                        this.mMHandler.post(new Runnable() { // from class: com.miui.miwallpaper.MiuiWallpaperManager$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                miuiWallpaperManagerCallback.onMiuiWallpaperInitialized(MiuiWallpaperManager.sInstance);
                            }
                        });
                    }
                } else {
                    if (miuiWallpaperManagerCallback instanceof SyncMiuiWallpaperManagerCallback) {
                        this.mSyncInitCallbacks.add(miuiWallpaperManagerCallback);
                    } else {
                        this.mAsyncInitCallbacks.add(miuiWallpaperManagerCallback);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean bindService(String str) {
        return bindService(false, str);
    }

    public final boolean bindService(boolean z, String str) {
        MiuiWallpaperManager miuiWallpaperManager;
        boolean zBindServiceAsUser;
        Log.i("MiuiWallpaperManager", "bindService: reason=" + str + " force=" + z + " mServiceConnected=" + this.mServiceConnected);
        if (!z && this.mServiceConnected) {
            Log.i("MiuiWallpaperManager", "bindService: service is already connected");
            return false;
        }
        try {
            Intent intent = new Intent("android.service.wallpaper.WallPaperControllerService");
            intent.setPackage("com.miui.miwallpaper");
            if (this.mUserHandle == null) {
                zBindServiceAsUser = this.mContext.bindService(intent, 1, WALLPAPER_INIT_EXECUTOR, this.mServiceConnection);
                miuiWallpaperManager = this;
            } else {
                miuiWallpaperManager = this;
                try {
                    zBindServiceAsUser = miuiWallpaperManager.bindServiceAsUser(this.mContext, intent, this.mServiceConnection, Context.BindServiceFlags.of(1L), getWHandler(), this.mUserHandle);
                } catch (Exception e) {
                    e = e;
                    Log.e("MiuiWallpaperManager", "bindService: occur error", e);
                }
            }
            if (zBindServiceAsUser) {
                return zBindServiceAsUser;
            }
        } catch (Exception e2) {
            e = e2;
            miuiWallpaperManager = this;
        }
        Log.e("MiuiWallpaperManager", "bindService failed! reason=" + str + " force=" + z + " mServiceConnected=" + miuiWallpaperManager.mServiceConnected);
        return false;
    }

    public final boolean bindServiceAsUser(Context context, Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags, Handler handler, UserHandle userHandle) {
        try {
            Method method = Class.forName("android.content.Context").getMethod("bindServiceAsUser", Intent.class, ServiceConnection.class, Context.BindServiceFlags.class, Handler.class, UserHandle.class);
            method.setAccessible(true);
            return ((Boolean) method.invoke(context, intent, serviceConnection, bindServiceFlags, handler, userHandle)).booleanValue();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("MiuiWallpaperManager", "bindServiceAsUser: occur error", e);
            return false;
        }
    }

    public void bindSystemUIProxy(IMiuiKeyguardWallpaperCallback iMiuiKeyguardWallpaperCallback) {
        if (iMiuiKeyguardWallpaperCallback != null) {
            this.mKeyguardCallback = iMiuiKeyguardWallpaperCallback;
        }
        if (isServiceReady()) {
            try {
                this.mService.bindSystemUIProxy(iMiuiKeyguardWallpaperCallback);
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "bindSystemUIProxy fail", th);
            }
        }
    }

    public boolean blurState(int i) {
        if (!isServiceReady()) {
            return false;
        }
        try {
            return this.mService.blurState(i);
        } catch (RemoteException e) {
            Log.e("MiuiWallpaperManager", "call method blurEnabled fail", e);
            return false;
        }
    }

    public final boolean changeMiuiWallpaperInfo(int i, int i2, int i3, boolean z) {
        if (!isServiceReady()) {
            return false;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("effect_id", i2);
            if (i3 != -1) {
                bundle.putInt("enable_blur", i3);
            }
            bundle.putBoolean("support_matting", z);
            this.mService.changeMiuiWallpaperInfo(i, bundle);
            return true;
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "changeMiuiWallpaperEffectType: fail", th);
            return false;
        }
    }

    public final void cleanListener() {
        synchronized (this.mLock) {
            try {
                if (this.mWallpaperChangeCallbacks.isEmpty()) {
                    return;
                }
                Log.w("MiuiWallpaperManager", "client may not call unregister method, cleanListener");
                if (isServiceReady()) {
                    Iterator it = this.mWallpaperChangeCallbacks.keySet().iterator();
                    while (it.hasNext()) {
                        unregisterListenerFromService((IMiuiWallpaperManagerCallback) it.next());
                    }
                }
                this.mWallpaperChangeCallbacks.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean copyWallpaper(Object obj, String str) {
        Log.d("MiuiWallpaperManager", "copyWallpaper: " + obj);
        if (obj instanceof Bitmap) {
            return MiuiWallpaperFileUtils.writeToFile((Bitmap) obj, str);
        }
        if (obj instanceof InputStream) {
            return MiuiWallpaperFileUtils.copyStreamToWallpaperFile((InputStream) obj, str);
        }
        if (obj instanceof String) {
            return MiuiWallpaperFileUtils.copyFile((String) obj, str);
        }
        return false;
    }

    public void destroy() {
        if (this.mDestroyed) {
            return;
        }
        this.mDestroyed = true;
        cleanListener();
        unbindService();
        this.mServiceConnected = false;
        unregisterReceiver();
        synchronized (this.mLock) {
            try {
                HandlerThread handlerThread = this.mWorker;
                if (handlerThread != null) {
                    handlerThread.quitSafely();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (MiuiWallpaperManager.class) {
            sInstance = null;
            Log.i("MiuiWallpaperManager", "destroy, set sInstance null");
        }
    }

    public final boolean featureSupport(String str, int i) {
        if ("linkage_video".equals(str)) {
            return this.mServerVersionCode >= 31;
        }
        return (IS_FLIP_DEVICE && "video".equals(str) && i / 10000 == 10 && this.mServerVersionCode < 328) ? false : true;
    }

    public Set getCanPreviewEffectList() {
        HashSet hashSet = new HashSet();
        hashSet.add("0");
        hashSet.add("10000");
        hashSet.add("10001");
        hashSet.add("10002");
        hashSet.add("10003");
        hashSet.add("10004");
        hashSet.add("30000");
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo("com.miui.aod", 128);
            ApplicationInfo applicationInfo2 = this.mContext.getPackageManager().getApplicationInfo("com.android.thememanager", 128);
            ApplicationInfo applicationInfo3 = this.mContext.getPackageManager().getApplicationInfo("com.miui.miwallpaper", 128);
            String string = applicationInfo.metaData.getString("effectSupportContent");
            String string2 = applicationInfo2.metaData.getString("effectSupportContent");
            String string3 = applicationInfo3.metaData.getString("effectSupportContent");
            Log.d("MiuiWallpaperManager", "getCanPreviewEffectList: aod-meta: " + applicationInfo.metaData + " theme-meta: " + applicationInfo2.metaData + " wallpaper-meta: " + applicationInfo3.metaData);
            if (string != null && string2 != null && string3 != null) {
                return getIntersection(string.split(ac.b), string2.split(ac.b), string3.split(ac.b));
            }
        } catch (Exception unused) {
        }
        return hashSet;
    }

    public String getDefaultVideoDepthSubject(int i) {
        if (isSingleWhich(i) && isServiceReady()) {
            try {
                Bundle wallpaperInfo = this.mService.getWallpaperInfo(i, "getSubjectPath", null);
                if (wallpaperInfo != null) {
                    return wallpaperInfo.getString("subjectPath");
                }
                Log.e("MiuiWallpaperManager", "getVideoDepthSubject: fail, result is null");
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "getVideoDepthSubject: fail", th);
            }
        }
        return null;
    }

    public int getEffectId(int i) {
        if (!isServiceReady() || !isSingleWhich(i)) {
            return 0;
        }
        try {
            return this.mService.getEffectId(i);
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getEffectId: fail", th);
            return 0;
        }
    }

    public Bitmap getFitScreenWallpaperPreview(Context context, int i) {
        int iMax;
        int iMin;
        int[] foldDisplaySize;
        Bitmap miuiWallpaperPreview = getMiuiWallpaperPreview(i);
        if (miuiWallpaperPreview != null) {
            Rect bounds = ((WindowManager) context.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
            if (IS_FOLDABLE_DEVICE || Build.IS_TABLET) {
                int iWidth = bounds.width();
                int iHeight = bounds.height();
                if (isSystemWhich(i) || (foldDisplaySize = getFoldDisplaySize(context, false)) == null) {
                    iMax = iHeight;
                    iMin = iWidth;
                } else {
                    int i2 = foldDisplaySize[0];
                    iMax = foldDisplaySize[1];
                    iMin = i2;
                }
            } else {
                int iWidth2 = bounds.width();
                int iHeight2 = bounds.height();
                iMin = Math.min(iWidth2, iHeight2);
                iMax = Math.max(iWidth2, iHeight2);
            }
            try {
                int width = miuiWallpaperPreview.getWidth();
                int height = miuiWallpaperPreview.getHeight();
                float fMax = Math.max(iMin / width, iMax / height);
                Matrix matrix = new Matrix();
                matrix.setScale(fMax, fMax);
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(miuiWallpaperPreview, 0, 0, width, height, matrix, true);
                if (miuiWallpaperPreview != bitmapCreateBitmap) {
                    BitmapUtils.recycleBitmap(miuiWallpaperPreview);
                }
                int width2 = bitmapCreateBitmap.getWidth();
                int height2 = bitmapCreateBitmap.getHeight();
                miuiWallpaperPreview = Bitmap.createBitmap(bitmapCreateBitmap, width2 > iMin ? (width2 - iMin) / 2 : 0, height2 > iMax ? (height2 - iMax) / 2 : 0, iMin, iMax);
                if (miuiWallpaperPreview != bitmapCreateBitmap) {
                    BitmapUtils.recycleBitmap(bitmapCreateBitmap);
                }
                return miuiWallpaperPreview;
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "getScreenCenterCropWallpaperPreview fail", th);
            }
        }
        return miuiWallpaperPreview;
    }

    public final int[] getFoldDisplaySize(Context context, boolean z) {
        Display display;
        Display[] displays = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
        if (displays.length != 2 || (display = displays[0]) == null || displays[1] == null) {
            return null;
        }
        Display display2 = display.getMode().getPhysicalWidth() < displays[1].getMode().getPhysicalWidth() ? displays[0] : displays[1];
        Display display3 = displays[0].getMode().getPhysicalWidth() < displays[1].getMode().getPhysicalWidth() ? displays[1] : displays[0];
        if (z) {
            display2 = display3;
        }
        return new int[]{display2.getMode().getPhysicalWidth(), display2.getMode().getPhysicalHeight()};
    }

    public String getGalleryJson(int i) {
        if (!isServiceReady() || !isSingleWhich(i)) {
            return "";
        }
        try {
            return this.mService.getGalleryJson(i);
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getMiuiWallpaperPath fail : ", th);
            return "";
        }
    }

    public Map getMiuiLockPartWallpaperIsDeep(int i, boolean z, Map map) {
        if (!isServiceReady()) {
            return null;
        }
        try {
            return this.mService.getMiuiLockPartWallpaperIsDeep(i, z, map);
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getMiuiLockPartWallpaperColors failed ", th);
            return null;
        }
    }

    public Map getMiuiWallpaperPalette(int i, boolean z, Map map) {
        if (!isServiceReady() || !isSingleWhich(i)) {
            return null;
        }
        try {
            return this.mService.getMiuiWallpaperPalette(i, z, map);
        } catch (Exception e) {
            Log.e("MiuiWallpaperManager", "getMiuiWallpaperColors fail : ", e);
            return null;
        }
    }

    public String getMiuiWallpaperPath(String str, int i, boolean z, boolean z2) {
        if (!isServiceReady() || !isSingleWhich(i)) {
            return null;
        }
        try {
            return this.mService.getMiuiWallpaperPath(str, i, z, z2);
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getMiuiWallpaperPath fail : ", th);
            return null;
        }
    }

    public Bitmap getMiuiWallpaperPreview(int i) {
        Bitmap fromFile = null;
        if (isServiceReady() && isSingleWhich(i)) {
            try {
                ParcelFileDescriptor miuiWallpaperPreview = this.mService.getMiuiWallpaperPreview(i);
                try {
                    if (miuiWallpaperPreview == null) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) WallpaperManager.getInstance(this.mContext).getDrawable();
                        if (bitmapDrawable != null) {
                            fromFile = bitmapDrawable.getBitmap();
                        }
                    } else {
                        fromFile = MiuiWallpaperFileUtils.readFromFile(miuiWallpaperPreview);
                    }
                    if (miuiWallpaperPreview == null) {
                        return fromFile;
                    }
                    miuiWallpaperPreview.close();
                    return fromFile;
                } catch (Throwable th) {
                    if (miuiWallpaperPreview != null) {
                        try {
                            miuiWallpaperPreview.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                Log.e("MiuiWallpaperManager", "getMiuiWallpaperPath fail : ", th3);
            }
        }
        return fromFile;
    }

    public String getMiuiWallpaperSensorFilePath(int i) {
        return getMiuiWallpaperPath(isMiuiDefaultWallpaper(i) ? StyleInfo.DEFAULT_PARAMETER : "sensor", i, false, false);
    }

    public String getMiuiWallpaperSensorPreviewFilePath(int i) {
        return getMiuiWallpaperPath(isMiuiDefaultWallpaper(i) ? StyleInfo.DEFAULT_PARAMETER : "sensor", i, false, true);
    }

    public String getMiuiWallpaperType(int i) {
        if (!isServiceReady() || !isSingleWhich(i)) {
            return null;
        }
        try {
            return this.mService.getMiuiWallpaperType(i);
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getMiuiWallpaperType fail : ", th);
            return null;
        }
    }

    public String getMiuiWallpaperVideoFilePath(int i) {
        return getMiuiWallpaperPath(isMiuiDefaultWallpaper(i) ? StyleInfo.DEFAULT_PARAMETER : "video", i, false, false);
    }

    public final int getSdkVersionCodeInternal() {
        if (!isServiceReady()) {
            return 0;
        }
        try {
            return this.mService.getMiuiWallpaperSdkVersionCode();
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getMiuiWallpaperSdkVersionCode: fail", th);
            return 0;
        }
    }

    public float getSdkVersionInternal() {
        if (!isServiceReady()) {
            return 0.0f;
        }
        try {
            return this.mService.getMiuiWallpaperSdkVersion();
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getMiuiWallpaperSdkVersion fail : ", th);
            return 0.0f;
        }
    }

    public List getVideoDepthScreenshot() {
        if (!isServiceReady()) {
            return null;
        }
        try {
            return this.mService.getVideoDepthScreenshot();
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "getVideoDepthScreenshot: fail", th);
            return null;
        }
    }

    public final Handler getWHandler() {
        if (this.mWorkHandler == null) {
            synchronized (this.mLock) {
                try {
                    if (this.mWorkHandler == null) {
                        HandlerThread handlerThread = new HandlerThread("MiuiWallpaperManager");
                        this.mWorker = handlerThread;
                        handlerThread.start();
                        this.mWorkHandler = new Handler(this.mWorker.getLooper());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return this.mWorkHandler;
    }

    public boolean isMiuiDefaultWallpaper(int i) {
        if (!isServiceReady() || !isSingleWhich(i)) {
            return false;
        }
        try {
            if (this.mServerVersion >= 1.3f) {
                return this.mService.isMiuiDefaultWallpaper(i);
            }
            Log.e("MiuiWallpaperManager", "call method isMiuiDefaultWallpaper fail,sdk version must >= 1.3");
            return false;
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "isMiuiDefaultWallpaper fail", th);
            return false;
        }
    }

    public boolean isMiuiWallpaperComponentUsing(int i) {
        if (!isServiceReady() || !isSingleWhich(i)) {
            return true;
        }
        try {
            return this.mService.isMiuiWallpaperComponentUsing(i);
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "isMiuiWallpaperComponentUsing: fail", th);
            return true;
        }
    }

    public final boolean isServiceReady() {
        if (this.mService == null) {
            Log.e("MiuiWallpaperManager", "mService is null.");
            return false;
        }
        if (!this.mServiceConnected) {
            int i = this.mReconnectCount.get();
            Log.e("MiuiWallpaperManager", "mService is not ready. mRebindServiceAllow=" + this.mRebindServiceAllow + " mDestroyed=" + this.mDestroyed + " mReconnectCount=" + i, new Throwable());
            if (!this.mRebindServiceAllow || this.mDestroyed || i >= 5) {
                Log.w("MiuiWallpaperManager", "isServiceReady: some went wrong, stop reconnect");
            } else {
                reconnectServiceSync();
            }
        }
        return this.mServiceConnected;
    }

    public final boolean isValidParam(int i, String str, Object obj, Object obj2, String str2, String str3, LinkageVideoParam linkageVideoParam, SensorParams sensorParams) {
        boolean zIsValidWhich = isValidWhich(i);
        str.getClass();
        switch (str) {
            case "sensor":
                if (MI_WALLPAPER_WHICH_ALL != i || obj != null || obj2 != null) {
                    if ((3 == i && obj == null) || str2 == null) {
                        zIsValidWhich = false;
                    } else if (sensorParams != null && !sensorParams.isValidParams()) {
                        zIsValidWhich = false;
                    }
                    break;
                } else {
                    zIsValidWhich = false;
                    break;
                }
                break;
            case "rotation_image":
            case "dark":
            case "depth_blur":
            case "super_wallpaper":
                if (obj == null || obj2 == null) {
                    zIsValidWhich = false;
                    break;
                }
                break;
            case "gallery":
                if (obj == null || str3 == null) {
                    zIsValidWhich = false;
                    break;
                }
                break;
            case "maml":
            case "image":
                if (obj == null) {
                    zIsValidWhich = false;
                    break;
                }
                break;
            case "video":
            case "video_gallery":
                if (obj == null || str2 == null) {
                    zIsValidWhich = false;
                    break;
                }
                break;
            case "linkage_video":
                if (obj == null || str2 == null || linkageVideoParam == null) {
                    zIsValidWhich = false;
                    break;
                }
                break;
            default:
                zIsValidWhich = false;
                break;
        }
        if (!zIsValidWhich) {
            Log.e("MiuiWallpaperManager", "set wallpaper param error: which = " + i + " type = " + str + " source1 = " + obj + " source2 = " + obj2 + " videoPath = " + str2 + " content = " + str3);
        }
        return zIsValidWhich;
    }

    public final /* synthetic */ void lambda$new$0() {
        this.mRebindServiceAllow = true;
        this.mServiceStartHistory.clear();
        bindService("rebind");
    }

    public final /* synthetic */ void lambda$new$2() {
        this.mReconnectCount.set(0);
        Log.i("MiuiWallpaperManager", "mResetRCountTask: reset reconnect count 0");
    }

    public final /* synthetic */ Integer lambda$setWallpaper$1(Map map, List list, String str, boolean[] zArr, String str2, Object obj, List list2, boolean z, Integer num) {
        List list3 = (List) map.get(num);
        if (list3 != null && !list3.isEmpty()) {
            final HashSet hashSet = new HashSet(list3);
            list.removeIf(new Predicate() { // from class: com.miui.miwallpaper.MiuiWallpaperManager$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj2) {
                    return hashSet.contains((String) obj2);
                }
            });
        }
        str.getClass();
        switch (str) {
            case "sensor":
                zArr[0] = writeMiuiWallpaperSensor(map, num.intValue(), str2, list2) & zArr[0];
                break;
            case "video":
            case "linkage_video":
            case "video_gallery":
                zArr[0] = writeMiuiWallpaperVideo(map, num.intValue(), str2, obj) & zArr[0];
                break;
            default:
                zArr[0] = writeMiuiWallpaper(map, num.intValue(), list2, z) & zArr[0];
                break;
        }
        return num;
    }

    public final void onWallpaperProcessStart() {
        int i = 0;
        if (this.mRebindServiceAllow) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.mServiceStartHistory.size() < 5) {
                this.mServiceStartHistory.add(Long.valueOf(jCurrentTimeMillis));
                bindService("onWallpaperProcessStart");
                Log.i("MiuiWallpaperManager", "onWallpaperProcessStart: rebind for less than max count, count=" + this.mServiceStartHistory.size());
            } else {
                Long l = (Long) this.mServiceStartHistory.peek();
                if (l == null || jCurrentTimeMillis - l.longValue() <= 10000) {
                    Log.e("MiuiWallpaperManager", "Wallpaper service may occur error, try it later delay=30000ms");
                    this.mRebindServiceAllow = false;
                    tryToRebind();
                } else {
                    this.mServiceStartHistory.poll();
                    this.mServiceStartHistory.add(Long.valueOf(jCurrentTimeMillis));
                    bindService("onWallpaperProcessStart#1");
                    Log.i("MiuiWallpaperManager", "onWallpaperProcessStart: less than time out, try rebind");
                }
            }
        } else {
            Log.w("MiuiWallpaperManager", "Wallpaper service rebind operation is not allow");
        }
        String[] strArr = new String[this.mServiceStartHistory.size()];
        Iterator it = this.mServiceStartHistory.iterator();
        while (it.hasNext()) {
            strArr[i] = this.mSdf.format(new Date(((Long) it.next()).longValue()));
            i++;
        }
        Log.i("MiuiWallpaperManager", "onWallpaperProcessStart: history=" + Arrays.toString(strArr));
    }

    public final void reconnectServiceSync() {
        if (this.mServiceConnected) {
            Log.i("MiuiWallpaperManager", "reconnectServiceSync: already connected, ignore");
            return;
        }
        this.mReconnectCount.incrementAndGet();
        SyncMiuiWallpaperManagerCallback syncMiuiWallpaperManagerCallback = new SyncMiuiWallpaperManagerCallback(null);
        this.mSyncInitCallbacks.add(syncMiuiWallpaperManagerCallback);
        if (bindService(true, "reconnectServiceSync")) {
            syncMiuiWallpaperManagerCallback.waitBindingSuccess();
        }
        removeResetCallback();
        this.mMHandler.postDelayed(this.mResetRCountTask, 5000L);
    }

    public final void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(this.mPackageUpdateReceiver, intentFilter);
        if ("com.miui.miwallpaper".equals(this.mContext.getPackageName())) {
            return;
        }
        this.mProcessReceiver = new BroadcastReceiver() { // from class: com.miui.miwallpaper.MiuiWallpaperManager.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.i("MiuiWallpaperManager", "onReceive action=" + action);
                if ("miui.action.WALLPAPER_PROCESS_START".equals(action)) {
                    MiuiWallpaperManager.this.onWallpaperProcessStart();
                }
            }
        };
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("miui.action.WALLPAPER_PROCESS_START");
        this.mContext.registerReceiver(this.mProcessReceiver, intentFilter2, "com.miui.miwallpaper.permission.BROADCAST", null, 2);
    }

    public void registerWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback, int i) {
        if (isServiceReady() && isValidWhich(i)) {
            try {
                synchronized (this.mLock) {
                    this.mWallpaperChangeCallbacks.put(iMiuiWallpaperManagerCallback, Integer.valueOf(i));
                }
                this.mService.registerWallpaperChangeListener(iMiuiWallpaperManagerCallback, i, this.mContext.getPackageName());
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "registerWallpaperChangeListener fail : ", th);
            }
        }
    }

    public final void removeResetCallback() {
        if (this.mMHandler.hasCallbacks(this.mResetRCountTask)) {
            this.mMHandler.removeCallbacks(this.mResetRCountTask);
        }
    }

    public final void removeTempWallpaper() {
        MiuiWallpaperFileUtils.removeFile(TEMP_WALLPAPER);
    }

    public final void saveTempWallpaper(Object obj) {
        removeTempWallpaper();
        copyWallpaper(obj, TEMP_WALLPAPER);
    }

    public final void setLockscreenVideoSurface(Surface surface, Surface surface2, boolean z) {
        if (z || surface != this.mNormalSurface || surface2 != this.mAlphaSurface || surface == null || surface2 == null) {
            this.mAlphaSurface = surface2;
            this.mNormalSurface = surface;
            if (isServiceReady()) {
                try {
                    Log.i("MiuiWallpaperManager", "setLockscreenVideoSurface::mAlphaSurface = " + this.mAlphaSurface + ", mNormalSurface = " + this.mNormalSurface);
                    this.mService.setLockscreenVideoSurface(surface, surface2);
                } catch (Throwable th) {
                    Log.e("MiuiWallpaperManager", "setLockscreenVideoSurface: fail", th);
                }
            }
        }
    }

    public void setLockscreenVideoSurfaceVisible(boolean z) {
        this.mAlphaVisible = z;
        if (isServiceReady()) {
            try {
                this.mService.setLockscreenVideoSurfaceVisible(z);
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "setLockscreenVideoSurfaceVisible: fail", th);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:68:0x01ac A[Catch: all -> 0x0068, TryCatch #0 {all -> 0x0068, blocks: (B:14:0x003d, B:17:0x004c, B:47:0x015c, B:50:0x0164, B:52:0x0175, B:54:0x017e, B:56:0x0184, B:58:0x018c, B:62:0x0197, B:64:0x019c, B:67:0x01a3, B:69:0x01b3, B:71:0x0204, B:68:0x01ac, B:23:0x0072, B:24:0x008d, B:26:0x00e8, B:27:0x00ed, B:30:0x0102, B:36:0x010f, B:38:0x0117, B:40:0x0129, B:42:0x012f, B:44:0x013f, B:45:0x0144, B:35:0x010c), top: B:80:0x003d }] */
    /* JADX WARN: Code duplicated, block: B:71:0x0204 A[Catch: all -> 0x0068, TRY_LEAVE, TryCatch #0 {all -> 0x0068, blocks: (B:14:0x003d, B:17:0x004c, B:47:0x015c, B:50:0x0164, B:52:0x0175, B:54:0x017e, B:56:0x0184, B:58:0x018c, B:62:0x0197, B:64:0x019c, B:67:0x01a3, B:69:0x01b3, B:71:0x0204, B:68:0x01ac, B:23:0x0072, B:24:0x008d, B:26:0x00e8, B:27:0x00ed, B:30:0x0102, B:36:0x010f, B:38:0x0117, B:40:0x0129, B:42:0x012f, B:44:0x013f, B:45:0x0144, B:35:0x010c), top: B:80:0x003d }] */
    /* JADX WARN: Code duplicated, block: B:74:0x020d A[RETURN] */
    public final int setWallpaper(int i, String str, final Object obj, Object obj2, ComponentName componentName, String str2, String str3, boolean z, boolean z2, boolean z3, int i2, int i3, boolean z4, String str4, int[] iArr, int i4, int i5, LinkageVideoParam linkageVideoParam, int i6, SensorParams sensorParams, int i7) {
        String str5;
        Map wallpaper3;
        String str6;
        ArrayList arrayList;
        final ArrayList arrayList2;
        final boolean[] zArr;
        if (!isServiceReady()) {
            return AnimatedPropertyType.TEXT_SIZE;
        }
        if (!featureSupport(str, i2)) {
            Log.w("MiuiWallpaperManager", "setWallpaper: fail, feature not support");
            return AnimatedPropertyType.CORNER_RADIUS_X;
        }
        if (!isValidParam(i, str, obj, obj2, str2, str3, linkageVideoParam, sensorParams)) {
            return AnimatedPropertyType.FILL_COLOR;
        }
        try {
            ArrayList arrayList3 = new ArrayList();
            float f = this.mServerVersion;
            if (f >= 1.0f) {
                if (f < 2.0f) {
                    wallpaper3 = this.mService.setWallpaper2(i, str, str3, componentName, z, z2, arrayList3);
                    str6 = str;
                    arrayList = arrayList3;
                } else {
                    Bundle bundle = new Bundle();
                    str5 = str2;
                    bundle.putString("video_path", str5);
                    bundle.putString("content", str3);
                    bundle.putBoolean("loop_video", z);
                    bundle.putBoolean("carousel", z2);
                    bundle.putInt("effect_id", i2);
                    bundle.putString("pending_package", this.mContext.getOpPackageName());
                    bundle.putBoolean("support_dark", z3);
                    bundle.putString("clock_style_type", str4);
                    bundle.putIntArray("clock_type_info", iArr);
                    bundle.putInt("origin_bitmap_width", i4);
                    bundle.putInt("origin_bitmap_height", i5);
                    if (i3 != -1) {
                        bundle.putInt("enable_blur", i3);
                    }
                    bundle.putBoolean("support_matting", z4);
                    if (this.mService.getMiuiWallpaperSdkVersionCode() >= 300334) {
                        bundle.putInt("doodle_enable", i6);
                    } else {
                        bundle.putBoolean("doodle_enable", i6 != 1);
                    }
                    if ("linkage_video".equals(str)) {
                        bundle.putString("linkage_video_param", new Gson().toJson(linkageVideoParam));
                    }
                    if (sensorParams != null && "sensor".equals(str)) {
                        bundle.putString("sensor_params", new Gson().toJson(sensorParams));
                    }
                    if (i7 != -1) {
                        bundle.putInt("all_key_frame", i7);
                    }
                    wallpaper3 = this.mService.setWallpaper3(i, str, componentName, arrayList3, bundle);
                    str6 = str;
                    arrayList = arrayList3;
                }
                if (wallpaper3 == null && !wallpaper3.isEmpty()) {
                    arrayList2 = new ArrayList();
                    final boolean zIsSingleWhich = isSingleWhich(i);
                    if (!"dark".equals(str6) || CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(str6) || "sensor".equals(str6) || "rotation_image".equals(str6) || "depth_blur".equals(str6)) {
                        arrayList2.add(obj);
                        arrayList2.add(obj2);
                    } else if (obj != null) {
                        arrayList2.add(obj);
                        if (obj2 != null) {
                            arrayList2.add(obj2);
                        } else if (!zIsSingleWhich) {
                            saveTempWallpaper(arrayList2.get(0));
                        }
                    }
                    zArr = new boolean[]{true};
                    final Map map = wallpaper3;
                    final String str7 = str6;
                    final ArrayList arrayList4 = arrayList;
                    final String str8 = str5;
                    forAllWallpaper(new Function() { // from class: com.miui.miwallpaper.MiuiWallpaperManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj3) {
                            return this.f$0.lambda$setWallpaper$1(map, arrayList4, str7, zArr, str8, obj, arrayList2, zIsSingleWhich, (Integer) obj3);
                        }
                    }, i);
                    MiuiWallpaperFileUtils.removeFile(arrayList4);
                    Log.i("MiuiWallpaperManager", "setWallpaper: needDeleteFile=" + TextUtils.join(", ", arrayList4));
                    removeTempWallpaper();
                    if (!zArr[0]) {
                        return 0;
                    }
                    Log.e("MiuiWallpaperManager", "setWallpaper: fail, write file occur error");
                    return AnimatedPropertyType.STROKE_COLOR;
                }
                return AnimatedPropertyType.PIVOT_X;
            }
            wallpaper3 = this.mService.setWallpaper(i, str, str3, componentName, z, arrayList3);
            arrayList = arrayList3;
            str6 = str;
            str5 = str2;
            if (wallpaper3 == null) {
                return AnimatedPropertyType.PIVOT_X;
            }
            arrayList2 = new ArrayList();
            final boolean zIsSingleWhich2 = isSingleWhich(i);
            if ("dark".equals(str6)) {
                arrayList2.add(obj);
                arrayList2.add(obj2);
            } else {
                arrayList2.add(obj);
                arrayList2.add(obj2);
            }
            zArr = new boolean[]{true};
            final Map map2 = wallpaper3;
            final String str9 = str6;
            final List arrayList5 = arrayList;
            final String str10 = str5;
            forAllWallpaper(new Function() { // from class: com.miui.miwallpaper.MiuiWallpaperManager$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj3) {
                    return this.f$0.lambda$setWallpaper$1(map2, arrayList5, str9, zArr, str10, obj, arrayList2, zIsSingleWhich2, (Integer) obj3);
                }
            }, i);
            MiuiWallpaperFileUtils.removeFile(arrayList5);
            Log.i("MiuiWallpaperManager", "setWallpaper: needDeleteFile=" + TextUtils.join(", ", arrayList5));
            removeTempWallpaper();
            if (!zArr[0]) {
                return 0;
            }
            Log.e("MiuiWallpaperManager", "setWallpaper: fail, write file occur error");
            return AnimatedPropertyType.STROKE_COLOR;
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "setWallpaper: fail : ", th);
            return AnimatedPropertyType.TEXT_COLOR;
        }
    }

    public boolean supportDark(int i) {
        if (!isServiceReady()) {
            return false;
        }
        try {
            return this.mService.supportDark(i);
        } catch (Throwable th) {
            Log.e("MiuiWallpaperManager", "supportDark: fail", th);
            return false;
        }
    }

    public boolean supportReversePlayback(int i) {
        if (isSystemWhich(i) && isServiceReady()) {
            try {
                Bundle wallpaperInfo = this.mService.getWallpaperInfo(i, "getAllKeyFrame", null);
                return wallpaperInfo != null && wallpaperInfo.getInt("all_key_frame", 0) == 1;
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "supportReversePlayback: fail", th);
            }
        }
        return false;
    }

    public final void tryRebindKeyguardCallback() {
        if (this.mKeyguardCallback != null) {
            Log.i("MiuiWallpaperManager", "tryRebindKeyguardCallback: mKeyguardCallback=" + this.mKeyguardCallback);
            bindSystemUIProxy(this.mKeyguardCallback);
        }
    }

    public final void tryRegisterWallpaperChangeCallback() {
        synchronized (this.mLock) {
            try {
                if (this.mWallpaperChangeCallbacks.isEmpty()) {
                    return;
                }
                for (Map.Entry entry : this.mWallpaperChangeCallbacks.entrySet()) {
                    IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback = (IMiuiWallpaperManagerCallback) entry.getKey();
                    Integer num = (Integer) entry.getValue();
                    if (num != null) {
                        registerWallpaperChangeListener(iMiuiWallpaperManagerCallback, num.intValue());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void tryToRebind() {
        if (this.mMHandler.hasCallbacks(this.mRebindTask)) {
            return;
        }
        this.mMHandler.postDelayed(this.mRebindTask, 30000L);
    }

    public void unRegisterWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback) {
        synchronized (this.mLock) {
            this.mWallpaperChangeCallbacks.remove(iMiuiWallpaperManagerCallback);
        }
        unregisterListenerFromService(iMiuiWallpaperManagerCallback);
    }

    public final void unbindService() {
        Log.i("MiuiWallpaperManager", "unbindService...");
        try {
            this.mContext.unbindService(this.mServiceConnection);
        } catch (Exception e) {
            Log.e("MiuiWallpaperManager", "unbindService occur exception... e=" + e, e);
        }
    }

    public final void unregisterListenerFromService(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback) {
        if (isServiceReady()) {
            try {
                this.mService.unRegisterWallpaperChangeListener(iMiuiWallpaperManagerCallback);
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "unRegisterWallpaperChangeListener fail : ", th);
            }
        }
    }

    public final void unregisterReceiver() {
        try {
            this.mContext.unregisterReceiver(this.mPackageUpdateReceiver);
            BroadcastReceiver broadcastReceiver = this.mProcessReceiver;
            if (broadcastReceiver != null) {
                this.mContext.unregisterReceiver(broadcastReceiver);
            }
        } catch (IllegalArgumentException e) {
            Log.e("MiuiWallpaperManager", "unregisterReceiver: e=" + e);
        }
    }

    public boolean videoLoop(int i) {
        if (isServiceReady() && isSingleWhich(i)) {
            try {
                Bundle wallpaperInfo = this.mService.getWallpaperInfo(i, "getVideoLoop", null);
                if (wallpaperInfo != null) {
                    return wallpaperInfo.getBoolean("loop_video", false);
                }
            } catch (Throwable th) {
                Log.e("MiuiWallpaperManager", "get videoLoop: fail", th);
            }
        }
        return false;
    }

    public final boolean writeMiuiWallpaper(Map map, int i, List list, boolean z) {
        List list2 = (List) map.get(Integer.valueOf(i));
        if (list2 == null || list == null) {
            return false;
        }
        boolean zCopyWallpaper = true;
        if (z) {
            if (list.size() == list2.size()) {
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    zCopyWallpaper &= copyWallpaper(list.get(i2), (String) list2.get(i2));
                }
            }
            return zCopyWallpaper;
        }
        Object obj = list.get(0);
        if (isLockWhich(i) && list.size() > 1) {
            return copyWallpaper(list.get(1), (String) list2.get(0));
        }
        String str = TEMP_WALLPAPER;
        return new File(str).exists() ? copyWallpaper(str, (String) list2.get(0)) : copyWallpaper(obj, (String) list2.get(0));
    }

    public final boolean writeMiuiWallpaperSensor(Map map, int i, String str, List list) {
        Object obj;
        List list2 = (List) map.get(Integer.valueOf(i));
        if (list2 == null || str == null || list2.size() < 2) {
            return false;
        }
        boolean zCopyFile = MiuiWallpaperFileUtils.copyFile(str, (String) list2.get(0));
        if (list.size() > 1) {
            obj = (i == 8 || i == 4) ? list.get(1) : list.get(0);
        } else {
            obj = list.get(0);
        }
        return copyWallpaper(obj, (String) list2.get(1)) & zCopyFile;
    }

    public final boolean writeMiuiWallpaperVideo(Map map, int i, String str, Object obj) {
        List list = (List) map.get(Integer.valueOf(i));
        if (list == null || str == null || list.size() < 2) {
            return false;
        }
        return copyWallpaper(obj, (String) list.get(1)) & MiuiWallpaperFileUtils.copyFile(str, (String) list.get(0));
    }
}

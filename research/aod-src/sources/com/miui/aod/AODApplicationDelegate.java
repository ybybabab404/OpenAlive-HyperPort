package com.miui.aod;

import android.app.WallpaperColors;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import android.view.ThreadedRenderer;
import com.miui.aod.analy.AnalyticsWrapper;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.MainThread;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.other.step.GoalLiveData;
import com.miui.aod.other.step.StepLiveData;
import com.miui.aod.settings.AODApplicationReceiver;
import com.miui.aod.util.CategoryPrefs;
import com.miui.aod.util.ClockColorManager;
import com.miui.aod.util.MMKVUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.notification.NotificationStyleManager;
import com.miui.keyguard.shortcuts.analy.ShortcutAnalyticManager;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import com.miui.miwallpaper.MiuiWallpaperManager;
import com.tencent.mmkv.MMKV;
import com.xiaomi.onetrack.OneTrack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import miuix.app.Application;
import miuix.autodensity.AutoDensityConfig;

/* JADX INFO: loaded from: classes.dex */
public class AODApplicationDelegate extends ApplicationDelegate implements AodLifecycleCallback {
    public static Rect mAllInOneColorPickupRect;
    public static Map mClockColorMap;
    public static Boolean mClockStyleIsDeep;
    public static Map mFullImageRect;
    public static MiuiWallpaperManager mMiuiWallpaperManager;
    public static Map mRealClockColorMap;
    public static Boolean mRealClockStyleIsDeep;
    public static Map mRealFullImageMap;
    public final Application application;
    public Runnable mPendingPaletteTask;
    public static final List mOnWallPaperChangeListenerList = new CopyOnWriteArrayList();
    public static IMiuiWallpaperManagerCallback wallpaperCallback = new AnonymousClass3();

    /* JADX INFO: renamed from: com.miui.aod.AODApplicationDelegate$3, reason: invalid class name */
    public class AnonymousClass3 extends IMiuiWallpaperManagerCallback.Stub {
        public static /* synthetic */ void $r8$lambda$_Vbu8cdvMhGBYPSX0IyJFz2actA(Map map, Map map2) {
            try {
                AODApplicationDelegate.mFullImageRect = (Map) map.get("full_image_rect");
                if (AODApplicationDelegate.mAllInOneColorPickupRect == null || AODApplication.getApplicationDelegate() == null) {
                    AODApplicationDelegate.mClockColorMap = (Map) map.get("clock_style_rect");
                    int iIntValue = ((Integer) map2.get("clock_style_rect")).intValue();
                    boolean z = true;
                    if (iIntValue != 1) {
                        z = false;
                    }
                    AODApplicationDelegate.mClockStyleIsDeep = Boolean.valueOf(z);
                    ClockColorManager.INSTANCE.notifyClockColorChange();
                } else {
                    AODApplication.getApplicationDelegate().refreshAllInOneClockPalette(AODApplicationDelegate.mAllInOneColorPickupRect);
                }
                Log.d("AODApplicationDelegate", "onPartColorComputeComplete: fullImageMap:" + AODApplicationDelegate.mFullImageRect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static /* synthetic */ void $r8$lambda$xp4RuGedszFTUCyUh_JVS1n_808(String str, int i) {
            Iterator it = AODApplicationDelegate.mOnWallPaperChangeListenerList.iterator();
            while (it.hasNext()) {
                ((OnWallPaperChangeListener) it.next()).onWallPaperChange(str, i);
            }
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onDrawFrameEnd() {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onPartColorComputeComplete(final Map map, final Map map2, int i) {
            if (Utils.isAodProcess()) {
                MainThread.post(new Runnable() { // from class: com.miui.aod.AODApplicationDelegate$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AODApplicationDelegate.AnonymousClass3.$r8$lambda$_Vbu8cdvMhGBYPSX0IyJFz2actA(map, map2);
                    }
                });
            }
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onWallpaperChanged(WallpaperColors wallpaperColors, final String str, final int i) {
            MainThread.post(new Runnable() { // from class: com.miui.aod.AODApplicationDelegate$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AODApplicationDelegate.AnonymousClass3.$r8$lambda$xp4RuGedszFTUCyUh_JVS1n_808(str, i);
                }
            });
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onWallpaperFirstFrameRendered(int i) {
        }
    }

    public interface OnWallPaperChangeListener {
        void onWallPaperChange(String str, int i);
    }

    public static /* synthetic */ void $r8$lambda$5Ry1W3qafaCDopNiRFb2uDNcQGE(Map map, Map map2) {
        if (map != null) {
            Map map3 = (Map) map.get("clock_palette_region");
            if (map3 != null) {
                mClockColorMap = map3;
            } else {
                Log.w("AODApplicationDelegate", "palette missing key: clock_palette_region");
            }
        }
        Integer num = (Integer) map2.get("clock_palette_region");
        if (num == null) {
            Log.w("AODApplicationDelegate", "partDeep missing key: clock_palette_region");
        } else {
            mClockStyleIsDeep = Boolean.valueOf(num.intValue() == 1);
        }
        ClockColorManager.INSTANCE.notifyClockColorChange();
    }

    public static /* synthetic */ void $r8$lambda$BVKZjYmmUkLrmfSvBe5t12Qu1uk(Rect rect, MiuiWallpaperManager miuiWallpaperManager) {
        HashMap map = new HashMap();
        map.put("clock_palette_region", rect);
        try {
            final Map miuiLockPartWallpaperIsDeep = miuiWallpaperManager.getMiuiLockPartWallpaperIsDeep(2, true, map);
            if (miuiLockPartWallpaperIsDeep != null) {
                final Map miuiWallpaperPalette = miuiWallpaperManager.getMiuiWallpaperPalette(2, true, map);
                MainThread.post(new Runnable() { // from class: com.miui.aod.AODApplicationDelegate$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        AODApplicationDelegate.$r8$lambda$5Ry1W3qafaCDopNiRFb2uDNcQGE(miuiWallpaperPalette, miuiLockPartWallpaperIsDeep);
                    }
                });
            }
        } catch (Exception e) {
            Log.w("AODApplicationDelegate", "refreshAllInOneClockPalette failed", e);
        }
    }

    public AODApplicationDelegate(Application application) {
        this.application = application;
    }

    public static void addOnWallPaperChangeListener(OnWallPaperChangeListener onWallPaperChangeListener) {
        mOnWallPaperChangeListenerList.add(onWallPaperChangeListener);
    }

    public static void initPluginWallpaperManager(Context context, final OnWallPaperChangeListener onWallPaperChangeListener) {
        if (mMiuiWallpaperManager == null) {
            MiuiWallpaperManager.init(context, new MiuiWallpaperManager.MiuiWallpaperManagerCallback() { // from class: com.miui.aod.AODApplicationDelegate.1
                @Override // com.miui.miwallpaper.MiuiWallpaperManager.MiuiWallpaperManagerCallback
                public void onMiuiWallpaperInitialized(MiuiWallpaperManager miuiWallpaperManager) {
                    AODApplicationDelegate.mMiuiWallpaperManager = miuiWallpaperManager;
                    OnWallPaperChangeListener onWallPaperChangeListener2 = onWallPaperChangeListener;
                    if (onWallPaperChangeListener2 != null) {
                        AODApplicationDelegate.addOnWallPaperChangeListener(onWallPaperChangeListener2);
                        onWallPaperChangeListener.onWallPaperChange(null, 0);
                    }
                    AODApplicationDelegate.mMiuiWallpaperManager.registerWallpaperChangeListener(AODApplicationDelegate.wallpaperCallback, 3);
                }
            });
        }
    }

    public static void removeOnWallPaperChangeListener(OnWallPaperChangeListener onWallPaperChangeListener) {
        mOnWallPaperChangeListenerList.remove(onWallPaperChangeListener);
    }

    @Override // com.miui.aod.ApplicationDelegate
    public SharedPreferences getSharedPreferences(String str, int i) {
        MMKV mmkvMmkvWithID = MMKV.mmkvWithID(str, 2);
        migrateToMMKV();
        return mmkvMmkvWithID;
    }

    public final void initKeyguardWallpaperManager() {
        MiuiWallpaperManager miuiWallpaperManager = mMiuiWallpaperManager;
        if (miuiWallpaperManager != null) {
            miuiWallpaperManager.unRegisterWallpaperChangeListener(wallpaperCallback);
            mMiuiWallpaperManager = null;
        }
        MiuiWallpaperManager.staticDestroy();
        Parcel.obtain().writeInt(UserHandleUtils.USER_CURRENT);
        MiuiWallpaperManager.init(this.base, new MiuiWallpaperManager.MiuiWallpaperManagerCallback() { // from class: com.miui.aod.AODApplicationDelegate.2
            @Override // com.miui.miwallpaper.MiuiWallpaperManager.MiuiWallpaperManagerCallback
            public void onMiuiWallpaperInitialized(MiuiWallpaperManager miuiWallpaperManager2) {
                AODApplicationDelegate.mMiuiWallpaperManager = miuiWallpaperManager2;
                miuiWallpaperManager2.registerWallpaperChangeListener(AODApplicationDelegate.wallpaperCallback, 2);
                AODApplicationDelegate.this.refreshDefaultColorPalette();
            }
        });
    }

    public final void migrateToMMKV() {
        if (MMKV.defaultMMKV(2, null).decodeInt("migrateToMMKV", 0) != 1) {
            SharedPreferences sharedPreferences = super.getSharedPreferences(BaseStyleSelectPresenter.KEY_CATE, 4);
            MMKV.mmkvWithID(BaseStyleSelectPresenter.KEY_CATE, 2).importFromSharedPreferences(sharedPreferences);
            sharedPreferences.edit().clear().apply();
            SharedPreferences sharedPreferences2 = super.getSharedPreferences("aod_visible_event", 4);
            MMKV.mmkvWithID("aod_visible_event", 2).importFromSharedPreferences(sharedPreferences2);
            sharedPreferences2.edit().clear().apply();
            MMKV.defaultMMKV(2, null).encode("migrateToMMKV", 1);
        }
        String string = CategoryPrefs.getString(this.application, "aod_category_name", "");
        if (TextUtils.isEmpty(MiuiSettingsUtils.Secure.getString(this.application.getContentResolver(), "aod_category_name")) && !TextUtils.isEmpty(string)) {
            Application application = this.application;
            Utils.setAodUsingCategoryParam(application, string, CategoryPrefs.getString(application, string, CategoryConstants.DEFAULT_PARAM));
        }
        String string2 = CategoryPrefs.getString(this.application, NotificationStyleManager.KEY_ANIMATION_STYLE, "");
        if (!TextUtils.isEmpty(MiuiSettingsUtils.Secure.getString(this.application.getContentResolver(), NotificationStyleManager.KEY_ANIMATION_STYLE)) || TextUtils.isEmpty(string2)) {
            return;
        }
        NotificationStyleManager.setSelectedStyleName(this.application, string2);
    }

    @Override // com.miui.aod.ApplicationDelegate
    public void onCreateAfterSuper() {
        MMKVUtils.initialize(this.application);
        AutoDensityConfig.init(this.application);
        Utils.updateTouchMode((Context) this.application, false, true);
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.AODApplicationDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.migrateToMMKV();
            }
        });
        AnalyticsWrapper.init(this.application, false);
        ShortcutAnalyticManager.init(this.application, false, OneTrack.Mode.APP);
        ThreadedRenderer.enableForegroundTrimming();
        this.application.registerActivityLifecycleSubCallbacks(this);
        HistoryProviderHelper.getInstance(this.application).clearSuperWallpaperHistory();
        initKeyguardWallpaperManager();
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.AODApplicationDelegate$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AODSettings.resetThreeGestureState(this.f$0.application);
            }
        });
        GoalLiveData.getInstance().freshData();
        StepLiveData.getInstance().freshData();
        BackgroundThread.post(new AODApplicationDelegate$$ExternalSyntheticLambda2());
        AODApplicationReceiver.register(this.application);
    }

    @Override // com.miui.aod.ApplicationDelegate
    public void onCreateBeforeSuper() {
    }

    @Override // com.miui.aod.ApplicationDelegate
    public void refreshAllInOneClockPalette(final Rect rect) {
        final MiuiWallpaperManager miuiWallpaperManager = mMiuiWallpaperManager;
        if (miuiWallpaperManager != null && mAllInOneColorPickupRect != null && rect == null) {
            refreshDefaultColorPalette();
        }
        mAllInOneColorPickupRect = rect;
        if (miuiWallpaperManager == null || rect == null) {
            Log.d("AODApplicationDelegate", "refreshAllInOneClockPalette: " + miuiWallpaperManager + ", " + rect);
            return;
        }
        Runnable runnable = this.mPendingPaletteTask;
        if (runnable != null) {
            BackgroundThread.removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: com.miui.aod.AODApplicationDelegate$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AODApplicationDelegate.$r8$lambda$BVKZjYmmUkLrmfSvBe5t12Qu1uk(rect, miuiWallpaperManager);
            }
        };
        this.mPendingPaletteTask = runnable2;
        BackgroundThread.post(runnable2);
    }

    public final void refreshDefaultColorPalette() {
        try {
            Map miuiLockPartWallpaperIsDeep = mMiuiWallpaperManager.getMiuiLockPartWallpaperIsDeep(2, true, null);
            if (miuiLockPartWallpaperIsDeep != null) {
                wallpaperCallback.onPartColorComputeComplete(mMiuiWallpaperManager.getMiuiWallpaperPalette(2, true, new HashMap()), miuiLockPartWallpaperIsDeep, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

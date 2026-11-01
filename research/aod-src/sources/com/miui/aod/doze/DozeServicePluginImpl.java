package com.miui.aod.doze;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.DozeServicePlugin;
import com.android.systemui.plugins.annotations.Requires;
import com.miui.aod.AODApplication;
import com.miui.aod.AODApplicationDelegate$$ExternalSyntheticLambda2;
import com.miui.aod.AODView;
import com.miui.aod.AodViewManager;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.analy.AnalyticalDataCollectorJobService;
import com.miui.aod.analy.AnalyticsWrapper;
import com.miui.aod.autodensity.AutoDensityControllerImpl;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.FlipLinkageClockCategoryInfoKt;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.notification.NotificationController;
import com.miui.aod.resource.CachedSettings;
import com.miui.aod.superwallpaper.SuperWallpaperReceiver;
import com.miui.aod.theme.ThemeResourceClearJobService;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.CategoryPrefs;
import com.miui.aod.util.ContextUtils;
import com.miui.aod.util.MMKVUtils;
import com.miui.aod.util.PluginManagerExt;
import com.miui.aod.util.PowerSaveModeManager;
import com.miui.aod.util.SmartCoverReceiver;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.LinkageClockKt;
import com.miui.aod.widget.notification.NotificationStyleManager;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.maml.elements.AdvancedSlider;
import com.tencent.mmkv.MMKV;
import com.xiaomi.onetrack.api.a;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
@Requires(target = DozeServicePlugin.class, version = 1)
public class DozeServicePluginImpl implements DozeServicePlugin {
    public static boolean hasInit = false;
    public Runnable aodInitRunnable;
    public Runnable mCheckFinishCallback;
    public Handler mHandler;
    public DozeServicePlugin.RequestDoze mRequestDoze;
    public SoftReference mSysUIContainerRef;
    public Context pluginContext;
    public DozeHost sHost;
    public Context sysuiContext;
    public Runnable updateSuppressAmbientDisplayRunnable;
    public final AtomicReference mDozeServiceRef = new AtomicReference();
    public boolean dozeRequested = false;
    public int pluginManagerVersion = 0;
    public boolean mWaitingDoAodAnimationFinishEvent = false;
    public boolean mLinkageAnimRunning = false;
    public ArrayList mFocusNotifiPenddingList = new ArrayList();
    public HashMap mSystemUiActionPendingList = new HashMap();
    public boolean mCheckResource = false;
    public Runnable mCheckResourceRunnable = new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            DozeServicePluginImpl.$r8$lambda$NFqhJsHpSb8q46G6_jNG_ZUEIfw(this.f$0);
        }
    };
    public Runnable mLinkageAnimTimeoutRunnable = new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            DozeServicePluginImpl.m327$r8$lambda$2qias8pZ5PCOB7Ec4PjlGvuKhU(this.f$0);
        }
    };
    public Map mAodPosition = null;
    public Bundle mPendingLinkageBundle = null;
    public boolean started = false;
    public boolean mAssembleMachinePending = false;

    /* JADX INFO: renamed from: $r8$lambda$2qias8pZ5P-COB7Ec4PjlGvuKhU, reason: not valid java name */
    public static /* synthetic */ void m327$r8$lambda$2qias8pZ5PCOB7Ec4PjlGvuKhU(DozeServicePluginImpl dozeServicePluginImpl) {
        dozeServicePluginImpl.mLinkageAnimRunning = false;
        final DozeService dozeService = dozeServicePluginImpl.getDozeService();
        if (dozeService == null || dozeService.getDozeMachine() == null || dozeServicePluginImpl.mFocusNotifiPenddingList.isEmpty()) {
            return;
        }
        dozeServicePluginImpl.mFocusNotifiPenddingList.forEach(new Consumer() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MiuiFocusNotification2.FocusNotificationWarp focusNotificationWarp = (MiuiFocusNotification2.FocusNotificationWarp) obj;
                dozeService.getDozeMachine().onRequestFocusView(focusNotificationWarp.bits, focusNotificationWarp.view, focusNotificationWarp.bundle);
            }
        });
        dozeServicePluginImpl.mFocusNotifiPenddingList.clear();
    }

    public static /* synthetic */ void $r8$lambda$NFqhJsHpSb8q46G6_jNG_ZUEIfw(DozeServicePluginImpl dozeServicePluginImpl) {
        dozeServicePluginImpl.mCheckResource = true;
        Utils.onDozeCreate(dozeServicePluginImpl.sysuiContext);
        Runnable runnable = dozeServicePluginImpl.mCheckFinishCallback;
        if (runnable != null) {
            dozeServicePluginImpl.mHandler.post(runnable);
        }
        dozeServicePluginImpl.mCheckResource = false;
    }

    public static /* synthetic */ void $r8$lambda$OkGf1PcrgOqZGPA5NdzpSoI7rK8(DozeServicePluginImpl dozeServicePluginImpl) {
        DozeHost dozeHost = dozeServicePluginImpl.sHost;
        if (dozeHost != null) {
            dozeHost.updateSuppressAmbientDisplay();
        }
    }

    public static /* synthetic */ void $r8$lambda$U3iD0_NgiSVYghF14IlLX5YB_jA(DozeServicePluginImpl dozeServicePluginImpl) {
        Context context = dozeServicePluginImpl.sysuiContext;
        Utils.verifyShowStyle(context, Utils.getShowStyle(context));
        Utils.updateTouchMode(dozeServicePluginImpl.sysuiContext, true, true);
    }

    public static /* synthetic */ void $r8$lambda$Y40OGq48m1Jhu58nWKfloijtVTQ(DozeServicePluginImpl dozeServicePluginImpl) {
        if (dozeServicePluginImpl.mHandler.hasCallbacks(dozeServicePluginImpl.aodInitRunnable)) {
            dozeServicePluginImpl.mHandler.removeCallbacks(dozeServicePluginImpl.aodInitRunnable);
            dozeServicePluginImpl.aodInitRunnable.run();
        } else {
            DozeHost dozeHost = dozeServicePluginImpl.sHost;
            if (dozeHost != null && dozeServicePluginImpl.started) {
                dozeHost.updateClockTypeForChange();
            }
        }
        dozeServicePluginImpl.mCheckFinishCallback = null;
    }

    public static /* synthetic */ void $r8$lambda$kByI8A8V2RIXE7oiT5LSNq3BIs4(final DozeServicePluginImpl dozeServicePluginImpl, boolean z, ViewGroup viewGroup) {
        dozeServicePluginImpl.mWaitingDoAodAnimationFinishEvent = false;
        NotificationController.getInstance(dozeServicePluginImpl.sysuiContext).setAodInitDelaying(false);
        final DozeService dozeService = dozeServicePluginImpl.getDozeService();
        if (dozeService == null) {
            return;
        }
        if (z) {
            Trace.beginSection("MiuiAodInit");
        }
        if (dozeServicePluginImpl.mAssembleMachinePending) {
            dozeService.assembleMachine();
        }
        dozeService.dreamingStarted(viewGroup);
        if (DozeHost.preDisplayedAod(dozeServicePluginImpl.sHost) && dozeService.getDozeMachine() != null && !dozeServicePluginImpl.mFocusNotifiPenddingList.isEmpty()) {
            dozeServicePluginImpl.mFocusNotifiPenddingList.forEach(new Consumer() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MiuiFocusNotification2.FocusNotificationWarp focusNotificationWarp = (MiuiFocusNotification2.FocusNotificationWarp) obj;
                    dozeService.getDozeMachine().onRequestFocusView(focusNotificationWarp.bits, focusNotificationWarp.view, focusNotificationWarp.bundle);
                }
            });
            dozeServicePluginImpl.mFocusNotifiPenddingList.clear();
        }
        if (!dozeServicePluginImpl.mSystemUiActionPendingList.isEmpty()) {
            dozeServicePluginImpl.mSystemUiActionPendingList.forEach(new BiConsumer() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda11
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.onSystemUIAction(((Integer) obj).intValue(), (Bundle) obj2);
                }
            });
            dozeServicePluginImpl.mSystemUiActionPendingList.clear();
        }
        if (z) {
            Trace.endSection();
        }
    }

    public static /* synthetic */ void $r8$lambda$xHvzOUGwAw0XElBBJXVUVWElDcY(DozeServicePluginImpl dozeServicePluginImpl, int i) {
        DozeService dozeService = dozeServicePluginImpl.getDozeService();
        Utils.updateTouchModeForKeepScreenOnIfNeeded(i, dozeService != null ? dozeService.isFolded() : Utils.isFolded(AODApplication.sysuiContext));
    }

    public static void registerBasicObserver(final Context context) {
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, new ContentObserver(BackgroundThread.getHandler()) { // from class: com.miui.aod.doze.DozeServicePluginImpl.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                Utils.updateTouchMode(context, false, true);
                sendSettingsQueryChangeBroadCast();
                if (DeviceUtil.INSTANCE.isPad()) {
                    return;
                }
                AnalyticManager.getInstance(context).trackAodModeStateSwitch();
                AnalyticManager.getInstance(context).trackNewAodModeStateSwitch();
            }

            public final void sendSettingsQueryChangeBroadCast() {
                Intent intent = new Intent();
                intent.setPackage("com.android.settings");
                intent.setAction("miui.intent.action.SEPARATE_APP_SEARCH_RESULT_UPDATE");
                context.sendBroadcast(intent);
            }
        });
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("aod_mode_user_set"), false, new ContentObserver(null) { // from class: com.miui.aod.doze.DozeServicePluginImpl.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                AnalyticManager.getInstance(context).trackAodModeUserSetStateSwitch();
            }
        });
    }

    public static void registerInversionObserver(final Context context) {
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_display_inversion_enabled"), false, new ContentObserver(null) { // from class: com.miui.aod.doze.DozeServicePluginImpl.3
            public boolean mLastAodState;
            public boolean mRestoreLastAod;

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (Utils.isInvertColorsEnable(context)) {
                    this.mLastAodState = Utils.isAodEnable(context);
                    this.mRestoreLastAod = true;
                    Utils.setAodEnable(context, false);
                } else if (this.mRestoreLastAod) {
                    this.mRestoreLastAod = false;
                    Utils.setAodEnable(context, this.mLastAodState);
                }
            }
        });
    }

    public static void registerMiuiOptimizationObserver(final Context context) {
        ContentObserver contentObserver = new ContentObserver(BackgroundThread.getHandler()) { // from class: com.miui.aod.doze.DozeServicePluginImpl.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                Utils.setMiuiOptimizationDisabled(Settings.Secure.getInt(context.getContentResolver(), "miui_optimization", 1) == 0);
            }
        };
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("miui_optimization"), false, contentObserver);
        contentObserver.onChange(false);
    }

    public static void registerSmartCoverService(Context context) {
        context.registerReceiver(SmartCoverReceiver.getReceiver(), new IntentFilter("miui.intent.action.SMART_COVER"), "android.permission.DEVICE_POWER", null, 2);
    }

    public final void create() {
        if (!hasInit) {
            AODApplication.initContext(this.sysuiContext, this.pluginContext);
            SettingsHolder.getInstance(this.sysuiContext).settingsInit();
            AodViewManager.INSTANCE.init(this.sysuiContext);
            MMKVUtils.initialize(this.pluginContext);
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    DozeServicePluginImpl.$r8$lambda$U3iD0_NgiSVYghF14IlLX5YB_jA(this.f$0);
                }
            });
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.migrateToMMKV();
                }
            });
            AnalyticsWrapper.init(this.pluginContext, false);
            PowerSaveModeManager.getInstance(this.sysuiContext);
            BatteryController.getInstance(this.sysuiContext);
            SuperWallpaperReceiver.register(this.sysuiContext);
            ThemeResourceClearJobService.schedule(this.pluginContext);
            if (!DeviceUtil.INSTANCE.isGteAndroidW()) {
                AnalyticalDataCollectorJobService.schedule(this.pluginContext);
            }
            registerBasicObserver(this.sysuiContext);
            registerInversionObserver(this.sysuiContext);
            registerMiuiOptimizationObserver(this.sysuiContext);
            registerSmartCoverService(this.sysuiContext);
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    AODSettings.resetThreeGestureState(this.f$0.sysuiContext);
                }
            });
            BackgroundThread.post(new AODApplicationDelegate$$ExternalSyntheticLambda2());
            hasInit = true;
        }
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public final boolean dealActionBeforeAnimationFinish(int i, Bundle bundle) {
        Map map;
        if ((262144 & i) != 0) {
            return handleApplyLinkageState(bundle);
        }
        if ((i & 16384) != 0) {
            dealWithDoAnimationToAodMsg(bundle);
            return true;
        }
        if ((32768 & i) != 0) {
            this.pluginManagerVersion = bundle.getInt("version", 0);
            return true;
        }
        if ((65536 & i) != 0) {
            dealWithDoAnimationToAodFinishMsg();
            return true;
        }
        if ((i & 64) != 0 && "gradient_index".equals(bundle.getString(a.a))) {
            LinkageClockKt.setGradientIndex(bundle.getInt(AdvancedSlider.STATE));
            return true;
        }
        DozeHost dozeHost = this.sHost;
        if (dozeHost != null) {
            if ((i & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0) {
                dozeHost.updateClockTranslation(bundle);
                return true;
            }
            if ((131072 & i) != 0) {
                FlipLinkageClockCategoryInfoKt.setClockInfoString(bundle.getString("clockInfo", FlipLinkageClockCategoryInfoKt.getDEFAULT_CLOCK_INFO()));
                return true;
            }
            if ((i & 1024) != 0) {
                try {
                    map = (Map) bundle.getSerializable("fullImageMap");
                } catch (ClassCastException e) {
                    Log.wtf("DozeServicePluginImpl", "get fullImageMap error: " + e.getMessage());
                    map = null;
                }
                this.sHost.updateClockColor(bundle.getBoolean("flag"), (HashMap) bundle.getSerializable("map"), map);
                return true;
            }
        }
        return false;
    }

    public final void dealWithDoAnimationToAodFinishMsg() {
        Log.w("DozeServicePluginImpl", "dealWithDoAnimationToAodFinishMsg: ");
        if (this.mWaitingDoAodAnimationFinishEvent) {
            this.mWaitingDoAodAnimationFinishEvent = false;
            Handler handler = this.mHandler;
            if (handler != null && handler.hasCallbacks(this.aodInitRunnable)) {
                this.mHandler.removeCallbacks(this.aodInitRunnable);
                this.aodInitRunnable.run();
            }
        }
        if (this.mLinkageAnimRunning) {
            Handler handler2 = this.mHandler;
            if (handler2 != null) {
                handler2.removeCallbacks(this.mLinkageAnimTimeoutRunnable);
            }
            this.mLinkageAnimTimeoutRunnable.run();
        }
    }

    public final boolean dealWithDoAnimationToAodMsg(Bundle bundle) {
        SoftReference softReference;
        boolean z = bundle.getBoolean("flags", false);
        boolean z2 = bundle.getBoolean("screenOffNeedLinkageAnim", false);
        if (z && z2) {
            this.mLinkageAnimRunning = true;
            this.mHandler.postDelayed(this.mLinkageAnimTimeoutRunnable, 1200L);
        }
        if (!this.dozeRequested && (softReference = this.mSysUIContainerRef) != null && softReference.get() != null) {
            if (!z) {
                this.mLinkageAnimRunning = false;
                NotificationController.getInstance(this.sysuiContext).setAodInitDelaying(false);
                ViewGroup viewGroup = (ViewGroup) this.mSysUIContainerRef.get();
                if (viewGroup != null) {
                    if (viewGroup.getChildAt(0) instanceof AODView) {
                        ((AODView) viewGroup.getChildAt(0)).releaseResourcesIfNeeded();
                    }
                    viewGroup.removeAllViews();
                }
                destroyDozeHostAndServiceIfNeeded();
                return false;
            }
            if (bundle.containsKey("flags") && bundle.containsKey("clockTranslation")) {
                CachedSettings.cleanCache();
                AODSettings.setNeedFullAod(bundle.getBoolean("screenOffNeedFullAodAnim", false));
                Log.w("DozeServicePluginImpl", "dealWithDoAnimationToAodMsg");
                initDozeHostIfNeeded();
                this.sHost.updateClockTranslation(bundle);
                this.sHost.onDoAodAnimation((ViewGroup) this.mSysUIContainerRef.get(), z2);
                NotificationController.getInstance(this.sysuiContext).setAodInitDelaying(true);
                return true;
            }
        }
        return false;
    }

    public final void destroyDozeHostAndServiceIfNeeded() {
        Runnable runnable = this.updateSuppressAmbientDisplayRunnable;
        if (runnable != null) {
            BackgroundThread.removeCallbacks(runnable);
            this.updateSuppressAmbientDisplayRunnable = null;
        }
        Handler handler = this.mHandler;
        if (handler != null && handler.hasCallbacks(this.mCheckFinishCallback)) {
            this.mHandler.removeCallbacks(this.mCheckFinishCallback);
        }
        this.mCheckFinishCallback = null;
        this.mCheckResource = false;
        DozeService dozeService = (DozeService) this.mDozeServiceRef.getAndSet(null);
        if (dozeService != null) {
            dozeService.destroy();
        }
        CategoryFactory.cleanCache();
        DozeHost dozeHost = this.sHost;
        if (dozeHost != null) {
            this.mAodPosition = dozeHost.getAodViewTopAndBottom();
            this.sHost.destroy();
            this.sHost = null;
        }
    }

    public final DozeService getDozeService() {
        return (DozeService) this.mDozeServiceRef.get();
    }

    public DozeHost getHost() {
        return this.sHost;
    }

    public int getVersion() {
        return 1;
    }

    public final boolean handleApplyLinkageState(Bundle bundle) {
        this.mPendingLinkageBundle = bundle;
        DozeHost dozeHost = this.sHost;
        if (dozeHost == null || bundle == null) {
            return true;
        }
        dozeHost.updateLinkagePosition(bundle);
        this.sHost.applyLinkageState();
        return true;
    }

    public final void initDozeHostIfNeeded() {
        if (this.sHost != null) {
            return;
        }
        this.mAodPosition = null;
        DozeHost dozeHost = new DozeHost(this.pluginContext);
        this.sHost = dozeHost;
        dozeHost.create();
        BatteryController.getInstance(this.sysuiContext).setDozeHost(this.sHost);
        Runnable runnable = new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DozeServicePluginImpl.$r8$lambda$OkGf1PcrgOqZGPA5NdzpSoI7rK8(this.f$0);
            }
        };
        this.updateSuppressAmbientDisplayRunnable = runnable;
        BackgroundThread.post(runnable);
        Bundle bundle = this.mPendingLinkageBundle;
        if (bundle != null) {
            this.sHost.updateLinkagePosition(bundle);
        }
    }

    public final void migrateToMMKV() {
        if (MMKV.defaultMMKV(2, null).decodeInt("migrateToMMKV", 0) != 1) {
            SharedPreferences sharedPreferences = this.pluginContext.getSharedPreferences(BaseStyleSelectPresenter.KEY_CATE, 4);
            MMKV.mmkvWithID(BaseStyleSelectPresenter.KEY_CATE, 2).importFromSharedPreferences(sharedPreferences);
            sharedPreferences.edit().clear().apply();
            SharedPreferences sharedPreferences2 = this.pluginContext.getSharedPreferences("aod_visible_event", 4);
            MMKV.mmkvWithID("aod_visible_event", 2).importFromSharedPreferences(sharedPreferences2);
            sharedPreferences2.edit().clear().apply();
            MMKV.defaultMMKV(2, null).encode("migrateToMMKV", 1);
        }
        String string = CategoryPrefs.getString(this.pluginContext, "aod_category_name", "");
        if (TextUtils.isEmpty(MiuiSettingsUtils.Secure.getString(this.pluginContext.getContentResolver(), "aod_category_name")) && !TextUtils.isEmpty(string)) {
            Context context = this.pluginContext;
            Utils.setAodUsingCategoryParam(context, string, CategoryPrefs.getString(context, string, CategoryConstants.DEFAULT_PARAM));
        }
        String string2 = CategoryPrefs.getString(this.pluginContext, NotificationStyleManager.KEY_ANIMATION_STYLE, "");
        if (!TextUtils.isEmpty(MiuiSettingsUtils.Secure.getString(this.pluginContext.getContentResolver(), NotificationStyleManager.KEY_ANIMATION_STYLE)) || TextUtils.isEmpty(string2)) {
            return;
        }
        NotificationStyleManager.setSelectedStyleName(this.pluginContext, string2);
    }

    public void onCreate(Context context, Context context2) {
        Log.i("DozeServicePluginImpl", "plugin onCreate: ");
        PluginManagerExt.Companion.checkVersionIfSupport(context);
        CachedSettings.cleanCache();
        FlipLinkageStyleController.INSTANCE.reset();
        PluginContextWrapper pluginContextWrapper = PluginContextWrapper.getInstance(context2, PluginApplication.getInstance(context2));
        ContextUtils.INSTANCE.fixClassLoader(context2);
        this.sysuiContext = context;
        this.pluginContext = pluginContextWrapper;
        AutoDensityControllerImpl.INSTANCE.onCreate(context, pluginContextWrapper);
        UserHandleUtils.refresh();
        create();
    }

    public void onDestroy() {
        Log.i("DozeServicePluginImpl", "plugin onDestroy: ");
        if (this.started) {
            onDreamingStopped();
        }
        AutoDensityControllerImpl.INSTANCE.onDestroy();
        destroyDozeHostAndServiceIfNeeded();
    }

    public void onDreamingStarted(final ViewGroup viewGroup) {
        if (this.started) {
            return;
        }
        if (!this.dozeRequested) {
            Log.wtf("DozeServicePluginImpl", "requested error1) !!!");
            return;
        }
        Log.i("DozeServicePluginImpl", "plugin onDreamingStarted: ");
        this.started = true;
        this.mSysUIContainerRef = new SoftReference(viewGroup);
        AODSettings.setNeedLinkageWallpaperAnim(viewGroup.getTag() != null && ((Boolean) viewGroup.getTag()).booleanValue());
        AODSettings.setNeedFullAod(viewGroup.getTag(viewGroup.getId()) != null && ((Boolean) viewGroup.getTag(viewGroup.getId())).booleanValue());
        final boolean z = (this.mAssembleMachinePending && AODSettings.needLinkageWallpaperAnim()) || DozeHost.preDisplayedAod(this.sHost);
        if (z && AODSettings.needKeepScreenOnAtFirst()) {
            updateTouchModeForKeepScreenOnIfNeeded(0);
        }
        this.aodInitRunnable = new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DozeServicePluginImpl.$r8$lambda$kByI8A8V2RIXE7oiT5LSNq3BIs4(this.f$0, z, viewGroup);
            }
        };
        if (z) {
            if (this.pluginManagerVersion <= 0 || !DozeHost.preDisplayedAod(this.sHost)) {
                this.mHandler.postDelayed(this.aodInitRunnable, 800L);
                return;
            } else {
                this.mWaitingDoAodAnimationFinishEvent = true;
                this.mHandler.postDelayed(this.aodInitRunnable, 1200L);
                return;
            }
        }
        if (!this.mCheckResource || AODSettings.needFullAod()) {
            this.aodInitRunnable.run();
            return;
        }
        Log.i("DozeServicePluginImpl", "delay init runnable by check resource");
        this.mCheckFinishCallback = new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DozeServicePluginImpl.$r8$lambda$Y40OGq48m1Jhu58nWKfloijtVTQ(this.f$0);
            }
        };
        this.mHandler.postDelayed(this.aodInitRunnable, 1200L);
    }

    public void onDreamingStopped() {
        if (!this.started || !this.dozeRequested) {
            destroyDozeHostAndServiceIfNeeded();
            Log.wtf("DozeServicePluginImpl", "requested error2) !!!");
            return;
        }
        Log.i("DozeServicePluginImpl", "plugin onDreamingStopped: ");
        this.mWaitingDoAodAnimationFinishEvent = false;
        this.started = false;
        this.mAssembleMachinePending = false;
        this.mLinkageAnimRunning = false;
        if (this.mHandler.hasCallbacks(this.mLinkageAnimTimeoutRunnable)) {
            this.mHandler.removeCallbacks(this.mLinkageAnimTimeoutRunnable);
        }
        if (this.mHandler.hasCallbacks(this.aodInitRunnable)) {
            this.mHandler.removeCallbacksAndMessages(null);
            if (AODSettings.needKeepScreenOnAtFirst()) {
                updateTouchModeForKeepScreenOnIfNeeded(1);
            }
        } else {
            DozeService dozeService = getDozeService();
            if (dozeService != null) {
                dozeService.dreamingStopped();
            }
        }
        this.dozeRequested = false;
        destroyDozeHostAndServiceIfNeeded();
    }

    public Map onRequestFocusView(int i, View view, Bundle bundle) {
        Log.d("DozeServicePluginImpl", "plugin onRequestFocusView: " + view + ", bits = " + i);
        DozeService dozeService = getDozeService();
        if (dozeService == null) {
            return null;
        }
        if (dozeService.getDozeMachine() != null && !this.mLinkageAnimRunning) {
            return dozeService.getDozeMachine().onRequestFocusView(i, view, bundle);
        }
        this.mFocusNotifiPenddingList.add(new MiuiFocusNotification2.FocusNotificationWarp(i, view, bundle));
        return null;
    }

    public Map onSystemUIAction(int i, Bundle bundle) {
        Log.i("DozeServicePluginImpl", "plugin onSystemUIAction: " + i);
        if (dealActionBeforeAnimationFinish(i, bundle)) {
            return null;
        }
        DozeService dozeService = getDozeService();
        if (dozeService != null && dozeService.getDozeMachine() != null) {
            return dozeService.getDozeMachine().onSystemUIAction(i, bundle);
        }
        if ((i & 8) != 0 || (i & 4) != 0 || (i & 2) != 0) {
            Log.d("DozeServicePluginImpl", "add to pending list");
            this.mSystemUiActionPendingList.put(Integer.valueOf(i), bundle);
        } else if ((i & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0) {
            Map map = this.mAodPosition;
            this.mAodPosition = null;
            return map;
        }
        return null;
    }

    public void setDozeRequester(DozeServicePlugin.RequestDoze requestDoze) {
        this.mSystemUiActionPendingList.clear();
        CachedSettings.cleanCache();
        FlipLinkageStyleController.INSTANCE.reset();
        UserHandleUtils.refresh();
        Log.i("DozeServicePluginImpl", "setDozeRequester: " + requestDoze + ", dozeRequested:" + this.dozeRequested + ", started:" + this.started);
        if (this.dozeRequested) {
            if (this.started) {
                return;
            } else {
                destroyDozeHostAndServiceIfNeeded();
            }
        }
        this.mRequestDoze = requestDoze;
        initDozeHostIfNeeded();
        DozeService dozeService = new DozeService(requestDoze, this.sysuiContext, this.pluginContext, this);
        setDozeService(dozeService);
        this.sHost.setService(dozeService);
        SmartCoverReceiver.getReceiver().setDozeService(dozeService);
        boolean z = true;
        if (DeviceUtil.INSTANCE.isGteAndroidW()) {
            this.mCheckResource = true;
            BackgroundThread.post(this.mCheckResourceRunnable);
        } else {
            Utils.onDozeCreate(AODApplication.sInstance);
        }
        if ((!Utils.isLinkageState(AODApplication.sInstance) || (Utils.isCpuAndGpuHighDevice() && !DozeHost.preDisplayedAod(this.sHost))) && !Utils.fullAodDisabledForScheduleMode(AODApplication.sInstance)) {
            z = false;
        }
        this.mAssembleMachinePending = z;
        this.dozeRequested = dozeService.create(z);
        this.mFocusNotifiPenddingList.clear();
    }

    public final void setDozeService(DozeService dozeService) {
        this.mDozeServiceRef.set(dozeService);
    }

    public final void updateTouchModeForKeepScreenOnIfNeeded(final int i) {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.DozeServicePluginImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                DozeServicePluginImpl.$r8$lambda$xHvzOUGwAw0XElBBJXVUVWElDcY(this.f$0, i);
            }
        });
    }
}

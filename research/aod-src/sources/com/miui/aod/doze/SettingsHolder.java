package com.miui.aod.doze;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.MiuiSettings;
import android.provider.Settings;
import android.text.TextUtils;
import com.miui.aod.Utils;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.PowerSaveModeManager;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.utils.UserHandleUtils;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class SettingsHolder {
    public static int mAodDisplayUpdate;
    public static int mAodUsingSuperWallpaper;
    public static int mAutoDualClock;
    public static String mCategoryName;
    public static String mCategoryParam;
    public static int mColorScheme;
    public static String mCurrentTemplateInfo;
    public static int mDisplayReversion;
    public static int mDozeTimeMode;
    public static int mMiuiKeyguard;
    public static int mNotchHide;
    public static boolean mOpenDoubleTapGoToSleep;
    public static int mPowerSuperSaveModeOpen;
    public static String mResidentTimezone;
    public static boolean mSupportGestureWakeup;
    public static String mSystemLocales;
    public static volatile SettingsHolder sInstance;
    public final ContentObserver mAodDisplayUpdateContentObserver;
    public final ContentObserver mAodTimeModeContentObserver;
    public final ContentObserver mAodUsingSuperWallpaperContentObserver;
    public final ContentObserver mAutoDualClockContentObserver;
    public final ContentObserver mCategoryNameContentObserver;
    public final ContentObserver mCategoryParamContentObserver;
    public final ContentObserver mColorSchemeContentObserver;
    public ContentResolver mContentResolver;
    public final ContentObserver mCurrentTemplateContentObserver;
    public final ContentObserver mDisplayReversionContentObserver;
    public final ContentObserver mGestureWakeupModeContentObserver;
    public CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    public final ContentObserver mMiuiKeyguardContentObserver;
    public final ContentObserver mNotchHideContentObserver;
    public final ContentObserver mPowerSuperSaveModeOpenContentObserver;
    public final ContentObserver mResidentTimezoneContentObserver;
    public final ContentObserver mSystemLocalesContentObserver;
    public UserReceiver mUserReceiver;
    public Context sysuiContext;

    public static class AodSettingsChangeListener {
        public void onChange(Uri uri) {
        }

        public void onUserSwitched() {
        }
    }

    public static class MiuiContentObserver extends ContentObserver {
        public MiuiContentObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            SettingsHolder.sInstance.onSettingsChanged(uri);
        }
    }

    public static class UserReceiver extends BroadcastReceiver {
        public static volatile UserReceiver userInstance;
        public Context mContext;
        public int mUserId = UserHandleUtils.USER_CURRENT;

        public UserReceiver(Context context) {
            this.mContext = context;
        }

        public static UserReceiver getInstance(Context context) {
            if (userInstance == null) {
                synchronized (UserReceiver.class) {
                    try {
                        if (userInstance == null) {
                            userInstance = new UserReceiver(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return userInstance;
        }

        public final void notifyUserSwitched(int i) {
            this.mUserId = i;
            UserHandleUtils.refresh();
            SettingsHolder.getInstance(this.mContext).onUserSwitched();
            SettingsHolder.getInstance(this.mContext).settingsInit();
            BatteryController.getInstance(this.mContext).onUserChanged(i);
            PowerSaveModeManager.getInstance(this.mContext).onUserChanged(i);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                notifyUserSwitched(intent.getIntExtra("android.intent.extra.user_handle", 0));
            }
        }

        public void register(Context context) {
            context.registerReceiver(this, new IntentFilter("android.intent.action.USER_SWITCHED"));
        }
    }

    public SettingsHolder(Context context) {
        Handler handler = null;
        this.mGestureWakeupModeContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mOpenDoubleTapGoToSleep = MiuiSettings.System.getBoolean(SettingsHolder.this.mContentResolver, "gesture_wakeup", false);
            }
        };
        this.mAodTimeModeContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mDozeTimeMode = MiuiSettingsUtils.getIntFromSecure(SettingsHolder.this.mContentResolver, "aod_mode_time", 0, SettingsHolder.this.mUserReceiver.mUserId);
            }
        };
        this.mMiuiKeyguardContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mMiuiKeyguard = MiuiSettingsUtils.Secure.getInt(SettingsHolder.this.mContentResolver, "miui_keyguard", 2);
            }
        };
        this.mAodDisplayUpdateContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mAodDisplayUpdate = Settings.Secure.getInt(SettingsHolder.this.mContentResolver, "aod_display_update_mode", 1);
            }
        };
        this.mDisplayReversionContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.5
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mDisplayReversion = MiuiSettingsUtils.Secure.getInt(SettingsHolder.this.mContentResolver, "accessibility_display_inversion_enabled", 0);
            }
        };
        this.mNotchHideContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.6
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mNotchHide = Settings.Global.getInt(SettingsHolder.this.mContentResolver, "force_black_v2", 0);
            }
        };
        this.mAodUsingSuperWallpaperContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.7
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mAodUsingSuperWallpaper = MiuiSettingsUtils.Secure.getInt(SettingsHolder.this.mContentResolver, "aod_using_super_wallpaper", 0);
            }
        };
        this.mColorSchemeContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.8
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mColorScheme = MiuiSettingsUtils.getIntFromSecure(SettingsHolder.this.mContentResolver, "color_scheme", 3, SettingsHolder.this.mUserReceiver.mUserId);
            }
        };
        this.mAutoDualClockContentObserver = new MiuiContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.9
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mAutoDualClock = MiuiSettingsUtils.System.getInt(SettingsHolder.this.mContentResolver, "auto_dual_clock", 0);
            }
        };
        this.mResidentTimezoneContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.10
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mResidentTimezone = MiuiSettingsUtils.System.getString(SettingsHolder.this.mContentResolver, "resident_timezone");
            }
        };
        this.mSystemLocalesContentObserver = new MiuiContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.11
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mSystemLocales = MiuiSettingsUtils.System.getString(SettingsHolder.this.mContentResolver, "system_locales");
            }
        };
        this.mPowerSuperSaveModeOpenContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.12
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mPowerSuperSaveModeOpen = MiuiSettingsUtils.System.getInt(SettingsHolder.this.mContentResolver, "power_supersave_mode_open", 0);
            }
        };
        this.mCategoryNameContentObserver = new MiuiContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.13
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (!TextUtils.isEmpty(SettingsHolder.mCategoryName)) {
                    UserHandleUtils.unregisterContentObserverForUser(SettingsHolder.this.sysuiContext, SettingsHolder.this.mCategoryParamContentObserver);
                }
                SettingsHolder.mCategoryName = Utils.getAodUsingCategoryName(SettingsHolder.this.sysuiContext);
                UserHandleUtils.registerContentObserverForUser(SettingsHolder.this.sysuiContext, Settings.Secure.getUriFor(SettingsHolder.mCategoryName), false, SettingsHolder.this.mCategoryParamContentObserver, SettingsHolder.this.mUserReceiver.mUserId);
                SettingsHolder.this.mCategoryParamContentObserver.onChange(false);
            }
        };
        this.mCategoryParamContentObserver = new MiuiContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.14
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mCategoryParam = MiuiSettingsUtils.Secure.getString(SettingsHolder.this.mContentResolver, SettingsHolder.mCategoryName);
            }
        };
        this.mCurrentTemplateContentObserver = new MiuiContentObserver(handler) { // from class: com.miui.aod.doze.SettingsHolder.15
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SettingsHolder.mCurrentTemplateInfo = MiuiSettingsUtils.Secure.getString(SettingsHolder.this.mContentResolver, "constant_template_editor_info");
            }
        };
        this.sysuiContext = context;
    }

    public static SettingsHolder getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SettingsHolder.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new SettingsHolder(context);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public void addListener(AodSettingsChangeListener aodSettingsChangeListener) {
        this.mListeners.add(aodSettingsChangeListener);
    }

    public final void onSettingsChanged(final Uri uri) {
        this.mListeners.forEach(new Consumer() { // from class: com.miui.aod.doze.SettingsHolder$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SettingsHolder.AodSettingsChangeListener) obj).onChange(uri);
            }
        });
    }

    public final void onUserSwitched() {
        this.mListeners.forEach(new Consumer() { // from class: com.miui.aod.doze.SettingsHolder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SettingsHolder.AodSettingsChangeListener) obj).onUserSwitched();
            }
        });
    }

    public void register() {
        UserReceiver userReceiver = UserReceiver.getInstance(this.sysuiContext);
        this.mUserReceiver = userReceiver;
        userReceiver.register(this.sysuiContext);
    }

    public void settingsInit() {
        register();
        this.mContentResolver = this.sysuiContext.getContentResolver();
        boolean zIsSupportGestureWakeup = Utils.isSupportGestureWakeup();
        mSupportGestureWakeup = zIsSupportGestureWakeup;
        if (zIsSupportGestureWakeup) {
            UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.System.getUriFor("gesture_wakeup"), false, this.mGestureWakeupModeContentObserver, this.mUserReceiver.mUserId);
            this.mGestureWakeupModeContentObserver.onChange(false);
        }
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("aod_mode_time"), false, this.mAodTimeModeContentObserver, this.mUserReceiver.mUserId);
        this.mAodTimeModeContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("miui_keyguard"), false, this.mMiuiKeyguardContentObserver, this.mUserReceiver.mUserId);
        this.mMiuiKeyguardContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("aod_display_update_mode"), false, this.mAodDisplayUpdateContentObserver, this.mUserReceiver.mUserId);
        this.mAodDisplayUpdateContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("accessibility_display_inversion_enabled"), false, this.mDisplayReversionContentObserver, this.mUserReceiver.mUserId);
        this.mDisplayReversionContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Global.getUriFor("force_black_v2"), false, this.mNotchHideContentObserver, this.mUserReceiver.mUserId);
        this.mNotchHideContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("aod_using_super_wallpaper"), false, this.mAodUsingSuperWallpaperContentObserver, this.mUserReceiver.mUserId);
        this.mAodUsingSuperWallpaperContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("color_scheme"), false, this.mColorSchemeContentObserver, this.mUserReceiver.mUserId);
        this.mColorSchemeContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.System.getUriFor("auto_dual_clock"), false, this.mAutoDualClockContentObserver, this.mUserReceiver.mUserId);
        this.mAutoDualClockContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.System.getUriFor("resident_timezone"), false, this.mResidentTimezoneContentObserver, this.mUserReceiver.mUserId);
        this.mResidentTimezoneContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.System.getUriFor("system_locales"), false, this.mSystemLocalesContentObserver, this.mUserReceiver.mUserId);
        this.mSystemLocalesContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.System.getUriFor("power_supersave_mode_open"), false, this.mPowerSuperSaveModeOpenContentObserver, this.mUserReceiver.mUserId);
        this.mPowerSuperSaveModeOpenContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("aod_category_name"), false, this.mCategoryNameContentObserver, this.mUserReceiver.mUserId);
        this.mCategoryNameContentObserver.onChange(false);
        UserHandleUtils.registerContentObserverForUser(this.sysuiContext, Settings.Secure.getUriFor("constant_template_editor_info"), false, this.mCurrentTemplateContentObserver, this.mUserReceiver.mUserId);
        this.mCurrentTemplateContentObserver.onChange(false);
    }
}

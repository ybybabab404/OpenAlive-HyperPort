package com.miui.aod.util;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;

/* JADX INFO: loaded from: classes.dex */
public class PowerSaveModeManager implements DozeHost.PowerSaveChangeCallback {
    public static volatile PowerSaveModeManager sInstance;
    public boolean isPowerSaveMode;
    public boolean isSuperPowerSaveMode;
    public final Context mContext;
    public boolean mIsPowerSaveModeMixRegistered = false;
    public final ContentObserver mDisableAodInPowerSaveModeObserver = new ContentObserver(BackgroundThread.getHandler()) { // from class: com.miui.aod.util.PowerSaveModeManager.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            boolean zDisableAodInPowerSaveModePermitted = Utils.disableAodInPowerSaveModePermitted(PowerSaveModeManager.this.mContext);
            int iAodModeStateUserSet = Utils.aodModeStateUserSet(PowerSaveModeManager.this.mContext);
            Log.i("PowerSaveModeManager", "onChange, disableAodInPowerSaveModePermitted: " + zDisableAodInPowerSaveModePermitted + ", isPowerSaveMode: " + PowerSaveModeManager.this.isPowerSaveMode + ", aodModeStateUserSet: " + iAodModeStateUserSet);
            if (PowerSaveModeManager.this.isPowerSaveMode) {
                if (zDisableAodInPowerSaveModePermitted) {
                    Utils.setAodEnable(PowerSaveModeManager.this.mContext, false);
                }
                if (zDisableAodInPowerSaveModePermitted) {
                    return;
                }
                Utils.setAodEnable(PowerSaveModeManager.this.mContext, iAodModeStateUserSet == 1);
            }
        }
    };
    public final ContentObserver mSuperSaveModeObserver = new AnonymousClass2(BackgroundThread.getHandler());
    public volatile int mLastUserId = UserHandleUtils.USER_CURRENT;
    public volatile boolean shouldIgnoreWallpaperChange = false;
    public int mUserId = UserHandleUtils.USER_CURRENT;

    /* JADX INFO: renamed from: com.miui.aod.util.PowerSaveModeManager$2, reason: invalid class name */
    public class AnonymousClass2 extends ContentObserver {
        public AnonymousClass2(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            boolean zIsSuperSaveModeOpen = AODSettings.isSuperSaveModeOpen(PowerSaveModeManager.this.mContext);
            Log.i("PowerSaveModeManager", "onChange: isSuperSaveMode " + zIsSuperSaveModeOpen);
            if (zIsSuperSaveModeOpen != PowerSaveModeManager.this.isSuperPowerSaveMode) {
                PowerSaveModeManager.this.shouldIgnoreWallpaperChange = true;
                BackgroundThread.postDelayed(new Runnable() { // from class: com.miui.aod.util.PowerSaveModeManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerSaveModeManager.this.shouldIgnoreWallpaperChange = false;
                    }
                }, 2000L);
            }
            PowerSaveModeManager.this.isSuperPowerSaveMode = zIsSuperSaveModeOpen;
        }
    }

    public PowerSaveModeManager(Context context) {
        this.mContext = context;
        this.isPowerSaveMode = Utils.isPowerSaveMode(context);
        this.isSuperPowerSaveMode = AODSettings.isSuperSaveModeOpen(context);
        if (Utils.isAodProcess()) {
            context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("aod_mode_user_set"), false, new ContentObserver(BackgroundThread.getHandler()) { // from class: com.miui.aod.util.PowerSaveModeManager.3
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    boolean z2 = Utils.aodModeStateUserSet(PowerSaveModeManager.this.mContext) == 1;
                    if (PowerSaveModeManager.this.isPowerSaveMode) {
                        if (z2) {
                            Settings.Secure.putInt(PowerSaveModeManager.this.mContext.getContentResolver(), "permit_disable_aod_in_power_save_mode", 0);
                        } else {
                            Settings.Secure.putInt(PowerSaveModeManager.this.mContext.getContentResolver(), "permit_disable_aod_in_power_save_mode", 1);
                        }
                    }
                    Log.i("PowerSaveModeManager", "onChange: aodModeStateUserSet " + z2 + "  isPowerSaveMode: " + PowerSaveModeManager.this.isPowerSaveMode);
                }
            });
        } else {
            registerPowerSaveModeMix(this.mUserId);
        }
    }

    public static PowerSaveModeManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PowerSaveModeManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new PowerSaveModeManager(context.getApplicationContext());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    @Override // com.miui.aod.DozeHost.PowerSaveChangeCallback
    public synchronized void onPowerSaveChanged(boolean z) {
        if (z == this.isPowerSaveMode && this.mLastUserId == UserHandleUtils.USER_CURRENT) {
            Log.i("PowerSaveModeManager", "onPowerSaveChanged, no change " + z);
            return;
        }
        this.mLastUserId = UserHandleUtils.USER_CURRENT;
        this.isPowerSaveMode = z;
        int iAodModeStateUserSet = Utils.aodModeStateUserSet(this.mContext);
        boolean zDisableAodInPowerSaveModePermitted = Utils.disableAodInPowerSaveModePermitted(this.mContext);
        Log.i("PowerSaveModeManager", "onPowerSaveChanged, userId: " + UserHandleUtils.USER_CURRENT + ", active: " + z + ", aodModeStateUserSet " + iAodModeStateUserSet + ", disableAodPermitted: " + zDisableAodInPowerSaveModePermitted + ", isAodSettingsEnabled: " + Utils.isAodSettingsEnabled(this.mContext));
        if (zDisableAodInPowerSaveModePermitted) {
            if (z) {
                if (iAodModeStateUserSet == -1 && Utils.isAodEnable(this.mContext)) {
                    Utils.setAodModeUserSet(this.mContext, true);
                }
                Utils.setAodEnable(this.mContext, false);
            } else if (iAodModeStateUserSet != -1) {
                Utils.setAodEnable(this.mContext, iAodModeStateUserSet == 1);
            }
        }
    }

    public synchronized void onUserChanged(int i) {
        try {
            if (this.mUserId != i) {
                this.mUserId = i;
                if (this.mIsPowerSaveModeMixRegistered) {
                    this.mContext.getContentResolver().unregisterContentObserver(this.mDisableAodInPowerSaveModeObserver);
                    this.mContext.getContentResolver().unregisterContentObserver(this.mSuperSaveModeObserver);
                }
                registerPowerSaveModeMix(i);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void registerPowerSaveModeMix(int i) {
        Log.i("PowerSaveModeManager", "registerPowerSaveModeMix " + i);
        this.mIsPowerSaveModeMixRegistered = true;
        UserHandleUtils.registerContentObserverForUser(this.mContext, Settings.Secure.getUriFor("permit_disable_aod_in_power_save_mode"), false, this.mDisableAodInPowerSaveModeObserver, i);
        UserHandleUtils.registerContentObserverForUser(this.mContext, Settings.System.getUriFor("power_supersave_mode_open"), false, this.mSuperSaveModeObserver, i);
        this.mDisableAodInPowerSaveModeObserver.onChange(false);
        this.mSuperSaveModeObserver.onChange(false);
    }

    public boolean shouldIgnoreWallpaperChange() {
        return this.shouldIgnoreWallpaperChange;
    }
}

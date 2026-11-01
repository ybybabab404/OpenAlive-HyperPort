package com.miui.aod.superwallpaper;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.miui.aod.AODApplication;
import com.miui.aod.AODApplicationDelegate;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.SuperWallpaperCategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.db.HistoryEntity;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.resource.CachedSettings;
import com.miui.aod.settings.RouterActivity;
import com.miui.aod.util.CategoryPrefs;
import com.miui.aod.util.PowerSaveModeManager;
import com.miui.aod.util.WallPaperUtils;
import com.miui.aod.utils.ContextUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.maml.elements.AdvancedSlider;
import com.miui.miwallpaper.MiuiWallpaperManager;
import com.xiaomi.onetrack.h.b.a;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class SuperWallpaperReceiver extends BroadcastReceiver {
    public static boolean homeSupportsSuper;
    public static boolean lockSupportsSuper;
    public AODApplicationDelegate.OnWallPaperChangeListener mWallpaperCallback = new AODApplicationDelegate.OnWallPaperChangeListener() { // from class: com.miui.aod.superwallpaper.SuperWallpaperReceiver.1
        @Override // com.miui.aod.AODApplicationDelegate.OnWallPaperChangeListener
        public void onWallPaperChange(String str, int i) {
            boolean zEquals = CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(str);
            if (i == 0) {
                MiuiWallpaperManager miuiWallpaperManager = AODApplicationDelegate.mMiuiWallpaperManager;
                if (miuiWallpaperManager != null) {
                    SuperWallpaperReceiver.homeSupportsSuper = CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(miuiWallpaperManager.getMiuiWallpaperType(1));
                    SuperWallpaperReceiver.lockSupportsSuper = CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(AODApplicationDelegate.mMiuiWallpaperManager.getMiuiWallpaperType(2));
                }
            } else if (i == 1) {
                SuperWallpaperReceiver.homeSupportsSuper = zEquals;
            } else if (i == 2) {
                SuperWallpaperReceiver.lockSupportsSuper = zEquals;
            }
            if (zEquals) {
                return;
            }
            SuperWallpaperReceiver.this.checkResource();
        }
    };
    public Runnable task = new Runnable() { // from class: com.miui.aod.superwallpaper.SuperWallpaperReceiver$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            SuperWallpaperReceiver.m505$r8$lambda$rXTNTbDFHDBFIQjSRiH82_IWI0();
        }
    };

    public static class PositionEvent {
        public final int position;

        public PositionEvent(int i) {
            this.position = i;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$r-XTNTbDFHDBFIQjSRiH82_IWI0, reason: not valid java name */
    public static /* synthetic */ void m505$r8$lambda$rXTNTbDFHDBFIQjSRiH82_IWI0() {
        Log.w("SuperWallpaperReceiver", "task run ");
        if (PowerSaveModeManager.getInstance(AODApplication.sInstance).shouldIgnoreWallpaperChange()) {
            Log.w("SuperWallpaperReceiver", "task skip");
            return;
        }
        WallPaperUtils.updateWallpaperSupportsAmbientModeState(homeSupportsSuper, lockSupportsSuper);
        if (WallPaperUtils.isWallpaperSupportsAmbientMode() || !Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance)) {
            return;
        }
        fallbackDefaultAod(AODApplication.sInstance);
    }

    public static boolean dealWithIntentForSuperWallpaperIfNeeded(Intent intent) {
        if (!intent.hasExtra(AdvancedSlider.STATE)) {
            return false;
        }
        boolean booleanExtra = intent.getBooleanExtra(AdvancedSlider.STATE, false);
        if (!Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance)) {
            Context context = AODApplication.sInstance;
            AODSettings.putInt(context, "super_wallpaper_linkage_state", Utils.isUsingLinkageStyle(context) ? 1 : 0);
        }
        Utils.setUsingSuperWallpaperStyleState(AODApplication.sInstance, booleanExtra);
        if (booleanExtra) {
            if (CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER.equals(getSuperWallpaperCategory())) {
                Utils.setAodUsingCategoryName(AODApplication.sInstance, CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER);
            } else {
                String stringExtra = intent.getStringExtra("super_wallpaper_bean");
                if (TextUtils.isEmpty(stringExtra)) {
                    stringExtra = getWallpaperJson();
                }
                String str = "{\n  \"name\":\"super_wallpaper\",\n  \"clock_position_x\":0.34,\n  \"clock_position_y\":0.12,\n  \"dual_clock_position_x_anchor_right\":0.65,\n  \"dual_clock_position_y\":0.6\n}";
                if (TextUtils.isEmpty(stringExtra)) {
                    Log.i("SuperWallpaperReceiver", "dealWithIntentForSuperWallpaperIfNeeded: set json with default value");
                    stringExtra = "{\n  \"name\":\"super_wallpaper\",\n  \"clock_position_x\":0.34,\n  \"clock_position_y\":0.12,\n  \"dual_clock_position_x_anchor_right\":0.65,\n  \"dual_clock_position_y\":0.6\n}";
                }
                try {
                    if (((SuperWallpaperCategoryInfo) new Gson().fromJson(stringExtra, SuperWallpaperCategoryInfo.class)).isInValid()) {
                        Log.e("SuperWallpaperReceiver", "dealWithIntentForSuperWallpaperIfNeeded: json isInValid");
                    } else {
                        str = stringExtra;
                    }
                    Utils.setAodUsingCategory(AODApplication.sInstance, CategoryConstants.CATEGORY_SUPER_WALLPAPER, str);
                    return true;
                } catch (JsonSyntaxException e) {
                    Log.e("SuperWallpaperReceiver", "error...", e);
                }
            }
        }
        return false;
    }

    public static void fallbackDefaultAod(Context context) {
        if (Utils.isSuperWallpaperCategory(context)) {
            Utils.setUsingSuperWallpaperStyleState(context, false);
            String defaultCategoryName = CategoryConstants.getDefaultCategoryName(context);
            Utils.setAodUsingStyleState(context, CategoryConstants.CATEGORY_LINKAGE.equals(defaultCategoryName) ? 1 : 2);
            Utils.setAodUsingCategoryName(context, defaultCategoryName);
            List preHistories = HistoryProviderHelper.getInstance(context).getPreHistories(1);
            boolean z = AODSettings.getInt(context, "super_wallpaper_linkage_state", 0) == 1;
            if (!preHistories.isEmpty() && !z) {
                Utils.setAodUsingStyleState(context, 0);
                HistoryEntity historyEntity = (HistoryEntity) preHistories.get(0);
                Utils.setAodUsingCategory(context, historyEntity.getCateName(), historyEntity.getJsonParameter());
            }
            if (z) {
                CategoryPrefs.putInt(context, "super_wallpaper_linkage_state", 0);
                AODSettings.putInt(context, "super_wallpaper_linkage_state", 0);
            }
        }
    }

    public static String getSuperWallpaperCategory() {
        WallpaperManager wallpaperManager = (WallpaperManager) AODApplication.sInstance.getSystemService("wallpaper");
        if (wallpaperManager == null) {
            Log.i("SuperWallpaperReceiver", "mWallpaperManager == null");
            return null;
        }
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();
        if (wallpaperInfo == null || wallpaperInfo.getServiceInfo() == null || wallpaperInfo.getServiceInfo().metaData == null) {
            return null;
        }
        return wallpaperInfo.getServiceInfo().metaData.getString("aod_category", "");
    }

    public static String getSuperWallpaperPkgName() {
        WallpaperInfo wallpaperInfo;
        WallpaperManager wallpaperManager = (WallpaperManager) AODApplication.sInstance.getSystemService("wallpaper");
        if (wallpaperManager == null || (wallpaperInfo = wallpaperManager.getWallpaperInfo()) == null || wallpaperInfo.getServiceInfo() == null || wallpaperInfo.getServiceInfo().applicationInfo == null) {
            return null;
        }
        return wallpaperInfo.getServiceInfo().applicationInfo.packageName;
    }

    public static String getWallpaperJson() {
        WallpaperManager wallpaperManager = (WallpaperManager) AODApplication.sInstance.getSystemService("wallpaper");
        if (wallpaperManager == null) {
            Log.i("SuperWallpaperReceiver", "mWallpaperManager == null");
            return null;
        }
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();
        if (wallpaperInfo == null || wallpaperInfo.getServiceInfo() == null || wallpaperInfo.getServiceInfo().metaData == null) {
            return null;
        }
        Bundle bundle = wallpaperInfo.getServiceInfo().metaData;
        float f = bundle.getFloat("clock_position_x", -1.0f);
        if (f < 0.0f) {
            return null;
        }
        float f2 = bundle.getFloat("clock_position_y", 0.12f);
        float f3 = bundle.getFloat("dual_clock_position_x_anchor_right", 0.65f);
        float f4 = bundle.getFloat("dual_clock_position_y", 0.6f);
        int i = bundle.getInt("support_change_with_time", 0);
        SuperWallpaperCategoryInfo superWallpaperCategoryInfo = new SuperWallpaperCategoryInfo();
        superWallpaperCategoryInfo.setData(f, f2, f3, f4, i);
        return new Gson().toJson(superWallpaperCategoryInfo);
    }

    public static void register(Context context) {
        IntentFilter intentFilter = new IntentFilter("miui.miwallpaper.action.SUPER_WALLPAPER_APPLY_STATE_CHANGED");
        intentFilter.addAction("miui.intent.action.ACTION_THEME_CHANGED");
        intentFilter.addAction("com.miui.miwallpaper.aod_init_positon");
        intentFilter.addAction("miui.keyguard.editor.action.LINKAGE_STYLE_APPLY_STATE_CHANGED");
        SuperWallpaperReceiver superWallpaperReceiver = new SuperWallpaperReceiver();
        AODApplicationDelegate.initPluginWallpaperManager(AODApplication.sInstance, superWallpaperReceiver.mWallpaperCallback);
        ContextUtils.registerReceiverForAllUsers(context, superWallpaperReceiver, intentFilter, null, null);
        superWallpaperReceiver.registerPackageBroadcast(context);
        if (Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance)) {
            int intFromSecure = MiuiSettingsUtils.getIntFromSecure(context.getContentResolver(), "super_wallpaper_aod_position", 0, UserHandleUtils.USER_CURRENT);
            Log.i("SuperWallpaperReceiver", "SuperWallpaperReceiver register get wallpaperAodPosition ：" + intFromSecure);
            EventBus.getDefault().removeStickyEvent(PositionEvent.class);
            EventBus.getDefault().postSticky(new PositionEvent(intFromSecure));
        }
    }

    public final void checkResource() {
        if (Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance)) {
            BackgroundThread.post(this.task);
        }
    }

    public final void dealWithIntentForSuperWallpaper(Intent intent) {
        Utils.setUsingSuperWallpaperStyleState(AODApplication.sInstance, true);
        Utils.setAodUsingStyleState(AODApplication.sInstance, 0);
        if (CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER.equals(getSuperWallpaperCategory())) {
            Utils.setAodUsingCategoryName(AODApplication.sInstance, CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER);
            return;
        }
        String stringExtra = intent.getStringExtra("super_wallpaper_bean");
        if (TextUtils.isEmpty(stringExtra)) {
            stringExtra = getWallpaperJson();
        }
        String str = "{\n  \"name\":\"super_wallpaper\",\n  \"clock_position_x\":0.34,\n  \"clock_position_y\":0.12,\n  \"dual_clock_position_x_anchor_right\":0.65,\n  \"dual_clock_position_y\":0.6\n}";
        if (TextUtils.isEmpty(stringExtra)) {
            Log.i("SuperWallpaperReceiver", "dealWithIntentForSuperWallpaperIfNeeded: set json with default value");
            stringExtra = "{\n  \"name\":\"super_wallpaper\",\n  \"clock_position_x\":0.34,\n  \"clock_position_y\":0.12,\n  \"dual_clock_position_x_anchor_right\":0.65,\n  \"dual_clock_position_y\":0.6\n}";
        }
        try {
            if (((SuperWallpaperCategoryInfo) new Gson().fromJson(stringExtra, SuperWallpaperCategoryInfo.class)).isInValid()) {
                Log.e("SuperWallpaperReceiver", "dealWithIntentForSuperWallpaper: json isInValid");
            } else {
                str = stringExtra;
            }
            Utils.setAodUsingCategory(AODApplication.sInstance, CategoryConstants.CATEGORY_SUPER_WALLPAPER, str);
        } catch (JsonSyntaxException e) {
            Log.e("SuperWallpaperReceiver", "error...", e);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) {
        Uri data;
        UserHandleUtils.refresh();
        CachedSettings.cleanCache();
        String action = intent.getAction();
        Log.i("SuperWallpaperReceiver", "onReceive: " + action);
        if ("miui.miwallpaper.action.SUPER_WALLPAPER_APPLY_STATE_CHANGED".equals(action)) {
            if ("superSavePower".equals(intent.getStringExtra(a.g)) || "forceStop".equals(intent.getStringExtra(a.g))) {
                BackgroundThread.postDelayed(new Runnable() { // from class: com.miui.aod.superwallpaper.SuperWallpaperReceiver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.dealWithIntentForSuperWallpaper(intent);
                    }
                }, 500L);
                Log.i("SuperWallpaperReceiver", "reason: " + intent.getStringExtra(a.g));
                return;
            }
            if ("common_immediately".equals(intent.getStringExtra(a.g))) {
                BackgroundThread.post(new Runnable() { // from class: com.miui.aod.superwallpaper.SuperWallpaperReceiver$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.dealWithIntentForSuperWallpaper(intent);
                    }
                });
                Log.i("SuperWallpaperReceiver", "reason: common_immediately");
                return;
            } else {
                if (!dealWithIntentForSuperWallpaperIfNeeded(intent) || intent.getBooleanExtra("theme", false)) {
                    return;
                }
                RouterActivity.tryToEnableAod(context, "SuperWallpaperReceiver");
                return;
            }
        }
        if ("com.miui.miwallpaper.aod_init_positon".equals(action)) {
            int intExtra = intent.getIntExtra("aod_position", -1);
            Log.i("SuperWallpaperReceiver", "onReceive: " + intExtra);
            if (intExtra > 0) {
                EventBus.getDefault().removeStickyEvent(PositionEvent.class);
                EventBus.getDefault().postSticky(new PositionEvent(intExtra));
                return;
            }
            return;
        }
        if ("miui.keyguard.editor.action.LINKAGE_STYLE_APPLY_STATE_CHANGED".equals(action)) {
            if (Utils.isSupportLinkageStyle()) {
                Utils.setUsingSuperWallpaperStyleState(context, false);
                Utils.setAodUsingStyleState(context, 1);
                Utils.setAodUsingCategoryName(context, CategoryConstants.CATEGORY_LINKAGE);
                if (TextUtils.isEmpty(MiuiSettingsUtils.Secure.getString(context.getContentResolver(), CategoryConstants.CATEGORY_LINKAGE))) {
                    Utils.setAodUsingCategoryParam(context, CategoryConstants.CATEGORY_LINKAGE, new Gson().toJson(CategoryFactory.createStyleInfo(context, CategoryConstants.CATEGORY_LINKAGE)));
                    return;
                }
                return;
            }
            return;
        }
        if (Utils.isUsingSuperWallpaperStyle(context) && "android.intent.action.PACKAGE_FULLY_REMOVED".equals(action) && (data = intent.getData()) != null) {
            String superWallpaperPkgName = getSuperWallpaperPkgName();
            String schemeSpecificPart = data.getSchemeSpecificPart();
            if (superWallpaperPkgName == null || !superWallpaperPkgName.equals(schemeSpecificPart)) {
                Log.i("SuperWallpaperReceiver", "onReceive:ACTION_PACKAGE_FULLY_REMOVED:pageName:" + superWallpaperPkgName);
                return;
            }
            Log.i("SuperWallpaperReceiver", "superWallpaper_is_remove:pageName:" + superWallpaperPkgName);
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.superwallpaper.SuperWallpaperReceiver$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SuperWallpaperReceiver.fallbackDefaultAod(context);
                }
            });
        }
    }

    public final void registerPackageBroadcast(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        ContextUtils.registerReceiverForAllUsers(context, this, intentFilter, null, null);
    }
}

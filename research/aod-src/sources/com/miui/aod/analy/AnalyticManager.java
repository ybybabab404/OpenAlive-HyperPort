package com.miui.aod.analy;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.collection.ArrayMap;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.BigTimeCategoryInfo;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.flip.AODTinyMiuiClockView;
import com.miui.aod.flip.FlipLinkageStyleClockView;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.notification.NotificationStyleManager;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.tencent.mmkv.MMKV;
import com.xiaomi.onetrack.api.am;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AnalyticManager {
    public static boolean DEBUG = true;
    public static volatile AnalyticManager sInstance;
    public final Context mContext;
    public boolean sAodVisible = false;
    public long mAodVisibleStartTime = -1;
    public final MMKV mAodVisibleEventSp = MMKV.mmkvWithID("aod_visible_event", 2);

    public static class AodVisibleEvent {

        @SerializedName("endTime")
        final long mEndTime;

        @SerializedName("startTime")
        final long mStartTime;

        public AodVisibleEvent(long j, long j2) {
            this.mEndTime = j2;
            this.mStartTime = j;
        }

        public long duration() {
            return this.mEndTime - this.mStartTime;
        }
    }

    public static /* synthetic */ void $r8$lambda$2mCWP5qn0KD7ObHVPUcxoWnj4N4(AnalyticManager analyticManager, boolean z, String str) {
        analyticManager.getClass();
        ArrayMap arrayMap = new ArrayMap();
        if (z) {
            arrayMap.put("after_set_status", "开");
        } else {
            arrayMap.put("after_set_status", "关");
        }
        str.getClass();
        switch (str) {
            case "aod_notification_status":
                arrayMap.put("switch_name", "显示通知");
                break;
            case "charging_aod_off":
                arrayMap.put("switch_name", "充电时始终显示");
                break;
            case "aod_on_status":
                arrayMap.put("switch_name", "息屏开关");
                break;
            case "lunar_calendar_status":
                arrayMap.put("switch_name", "显示农历");
                break;
            case "aod_date_status":
                arrayMap.put("switch_name", "显示日期");
                break;
            case "aod_energy_status":
                arrayMap.put("switch_name", "显示电量");
                break;
            case "batterysaver_aod_off":
                arrayMap.put("switch_name", "省电模式开关");
                break;
            default:
                arrayMap.put("switch_name", "未知");
                break;
        }
        arrayMap.put(am.af, "348.2.2.1.41298");
        analyticManager.trackEvent("click", arrayMap);
    }

    public static /* synthetic */ void $r8$lambda$L1TexMdOdtMlZ_cr3U6SvjvYXKU(AnalyticManager analyticManager) {
        analyticManager.getClass();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("after_set_status", analyticManager.getAodModeString());
        arrayMap.put(am.af, "348.2.2.1.36509");
        analyticManager.trackEvent("set", arrayMap);
    }

    public static /* synthetic */ void $r8$lambda$WyI8iG00EmHQsWarG8HWdQo5OEY(AnalyticManager analyticManager) {
        int iAodModeStateUserSet = Utils.aodModeStateUserSet(analyticManager.mContext);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("state_value", Integer.valueOf(iAodModeStateUserSet));
        analyticManager.trackEvent("aod_mode_user_switch", arrayMap);
    }

    public static /* synthetic */ void $r8$lambda$fOcLdRCCXvJUb32JAFGC8BbOCVk(AnalyticManager analyticManager, String str) {
        analyticManager.getClass();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("page_from", str);
        analyticManager.trackEvent("aod_style_select_page", arrayMap);
    }

    /* JADX INFO: renamed from: $r8$lambda$x0AlKeKISZdEm1bo4-LMo85-7vA, reason: not valid java name */
    public static /* synthetic */ void m207$r8$lambda$x0AlKeKISZdEm1bo4LMo857vA(AnalyticManager analyticManager) {
        int i = Settings.Secure.getInt(analyticManager.mContext.getContentResolver(), "doze_always_on", 0);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("state_value", Integer.valueOf(i));
        analyticManager.trackEvent("aod_mode_switch", arrayMap);
    }

    public AnalyticManager(Context context) {
        this.mContext = context;
    }

    public static AnalyticManager getInstance(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            applicationContext = AODApplication.sInstance;
        }
        if (sInstance == null) {
            synchronized (AnalyticManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new AnalyticManager(applicationContext);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public final Map createStyleInfoParameters() {
        return createStyleInfoParameters(CategoryFactory.createStyleInfo(this.mContext, Utils.getAodUsingCategoryName(this.mContext)));
    }

    public final Map createStyleInfoParameters(StyleInfo styleInfo) {
        ArrayMap arrayMap = new ArrayMap();
        if (styleInfo != null) {
            Map<String, Object> parameterForAnalytic = styleInfo.getParameterForAnalytic();
            if (parameterForAnalytic != null && !parameterForAnalytic.isEmpty()) {
                arrayMap.putAll(parameterForAnalytic);
            }
            arrayMap.put("style_color", styleInfo.getColorName());
            if (styleInfo.supportChangeColorAlongTime()) {
                arrayMap.put("color_change_with_time_enable", Boolean.valueOf(styleInfo.isChangeColorAlongTime()));
            }
            if (styleInfo.supportDateAndTime()) {
                arrayMap.put("date_time_enable", Boolean.valueOf(styleInfo.isDateAndTimeSwitchOn()));
            }
            if (styleInfo.supportBatteryIcon()) {
                arrayMap.put("battery_enable", Boolean.valueOf(styleInfo.isBatterySwitchOn()));
            }
            if (styleInfo.supportNotificationIcon()) {
                arrayMap.put("notification_enable", Boolean.valueOf(styleInfo.isNotificationSwitchOn()));
            }
        }
        return arrayMap;
    }

    public final String getAodModeString() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "doze_always_on", 0) == 1 ? "开" : "关";
    }

    public final String getAodShowModeString() {
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "aod_show_style", Utils.getDefaultShowStyle(this.mContext));
        if (i == 0) {
            return "显示10秒";
        }
        if (i == 1) {
            return "定时显示";
        }
        if (i != 2) {
            return i != 3 ? "" : "智能显示";
        }
        return "始终显示";
    }

    public final String getBatterySaverModeString() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "permit_disable_aod_in_power_save_mode", 1) == 1 ? "开" : "关";
    }

    public final String getChargingAodOff() {
        return Utils.getShowStyle(this.mContext) == 2 ? "开" : "关";
    }

    public final String getFullAodNotifiStateString() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "full_screen_aod_notification", 1) == 1 ? "开" : "关";
    }

    public final String getFullAodStyleString() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "full_screen_aod_on", 0) == 1 ? "和锁屏一致" : "自定义";
    }

    public final String[] getPadAodSettingStateString(BigTimeCategoryInfo bigTimeCategoryInfo) {
        if (bigTimeCategoryInfo == null || bigTimeCategoryInfo.getLabel() == null) {
            return new String[]{"开", "开", "开", "关"};
        }
        return new String[]{bigTimeCategoryInfo.isNotificationSwitchOn() ? "开" : "关", bigTimeCategoryInfo.isBatterySwitchOn() ? "开" : "关", bigTimeCategoryInfo.isDateAndTimeSwitchOn() ? "开" : "关", bigTimeCategoryInfo.isLunarSwitchOn(this.mContext) ? "开" : "关"};
    }

    public final BigTimeCategoryInfo getPadCategoryInfo() {
        return (BigTimeCategoryInfo) new Gson().fromJson(Utils.getAodUsingCategoryParam(this.mContext, CategoryConstants.CATEGORY_BIG_TIME), BigTimeCategoryInfo.class);
    }

    public final String getPadUsedAodStyleString(BigTimeCategoryInfo bigTimeCategoryInfo) {
        return bigTimeCategoryInfo.getTitle();
    }

    public String getTinyKeyguardTemplateStyle(String str) {
        str.getClass();
        switch (str) {
            case "splicing":
                return "拼接";
            case "bubble":
                return "泡泡";
            case "doodle":
                return "涂鸦";
            case "interactive_pets":
                return "萌宠";
            case "ai_pets":
                return "AI萌宠";
            case "pets":
                return "萌宠";
            case "classic":
                return "经典";
            default:
                return null;
        }
    }

    public String getTinyKeyguardTemplateStylePattern(String str, int i) {
        str.getClass();
        switch (str) {
            case "bubble":
                if (i == 0 || i == 1) {
                    return "3D";
                }
                if (i == 2 || i == 3 || i == 4 || i == 5) {
                    return "纯色3D";
                }
                return null;
            case "doodle":
                switch (i) {
                    case 0:
                        return "橙黑";
                    case 1:
                        return "绿灰";
                    case 2:
                        return "蓝黑";
                    case 3:
                        return "紫黑";
                    case 4:
                        return "橙灰";
                    case 5:
                        return "红黑";
                    case 6:
                        return "绿黑";
                    case 7:
                        return "蓝灰";
                    case 8:
                        return "青黑";
                    default:
                        return null;
                }
            case "interactive_pets":
                if (i == 1) {
                    return "锅盖鸟";
                }
                if (i == 2) {
                    return "黑脸羊";
                }
                if (i != 3) {
                    return null;
                }
                return "竖琴海豹";
            case "pets":
                switch (i) {
                    case 1:
                        return "德文";
                    case 2:
                        return "水豚";
                    case 3:
                        return "水獭";
                    case 4:
                        return "比熊";
                    case 5:
                        return "暹罗";
                    case 6:
                        return "黑豹";
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    public final String getUsedAodStyleString() {
        if (Utils.isFsAodSameToLock(this.mContext)) {
            return "和锁屏一致";
        }
        String aodUsingCategoryName = Utils.getAodUsingCategoryName(this.mContext);
        aodUsingCategoryName.getClass();
        switch (aodUsingCategoryName) {
            case "text_style":
                return "个性签名";
            case "paint_vertical":
            case "linear_timetext":
            case "smart_cover":
            case "sun":
                return "数字时钟";
            case "image_bg_selector":
                return "相册自定义";
            case "oneline":
                return "精选图案";
            case "animate_clock_panel":
                return "表盘时钟-波点";
            case "clock_panel":
                return "表盘时钟-标准";
            case "Flip_Linkage":
            case "Linkage":
                return "联动息屏";
            case "maml":
                return "三方主题";
            case "scope":
                return "万花筒";
            default:
                return "";
        }
    }

    public final void saveAodVisibleEvent(AodVisibleEvent aodVisibleEvent) {
        Log.i("AnalyticManager", "saveAodVisibleEvent: " + aodVisibleEvent.duration());
    }

    public void trackAodApplyClickAction() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticManager analyticManager = this.f$0;
                analyticManager.trackEvent("aod_apply_click_action", analyticManager.createStyleInfoParameters());
            }
        });
    }

    public void trackAodEnableState() {
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "doze_always_on", 0);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("state_value", Integer.valueOf(i));
        arrayMap.put("state_value_user_set", Integer.valueOf(Utils.aodModeStateUserSet(this.mContext)));
        trackEvent("aod_mode", arrayMap);
        if (i == 1) {
            trackEvent("aod_mode_on", null);
        }
    }

    public void trackAodEvent() {
        if (Utils.supportAod(this.mContext)) {
            Log.i("AnalyticManager", "trackAodEvent");
            HashMap map = new HashMap();
            int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "doze_always_on", -1);
            map.put("aod_mode", "" + i);
            int i2 = Settings.Secure.getInt(this.mContext.getContentResolver(), "aod_mode_time", 1);
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(i == 1 ? i2 : -1);
            map.put("aod_mode_time", sb.toString());
            AnalyticsWrapper.trackEvent("aod", map);
            trackAodStyleState();
            trackAodEnableState();
            trackAodShowModeState();
            trackNotificationStyleState();
            trackAodVisibleEvent();
            trackNewAodState();
        }
    }

    public void trackAodModeStateSwitch() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticManager.m207$r8$lambda$x0AlKeKISZdEm1bo4LMo857vA(this.f$0);
            }
        });
    }

    public void trackAodModeUserSetStateSwitch() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticManager.$r8$lambda$WyI8iG00EmHQsWarG8HWdQo5OEY(this.f$0);
            }
        });
    }

    public void trackAodSelectStylePage(final String str) {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticManager.$r8$lambda$fOcLdRCCXvJUb32JAFGC8BbOCVk(this.f$0, str);
            }
        });
    }

    public void trackAodShowEvent(View view) {
        StyleInfo clockStyleInfo = AODSettings.getClockStyleInfo(this.mContext);
        ArrayMap arrayMap = new ArrayMap();
        if (!(clockStyleInfo instanceof FlipLinkageClockCategoryInfo) || view == null) {
            return;
        }
        View viewFindViewById = view.findViewById(R.id.flip_linkage_clock_container);
        FlipLinkageStyleClockView flipLinkageStyleClockView = viewFindViewById instanceof FlipLinkageStyleClockView ? (FlipLinkageStyleClockView) viewFindViewById : null;
        AODTinyMiuiClockView miuiClockView = flipLinkageStyleClockView != null ? flipLinkageStyleClockView.getMiuiClockView() : null;
        TinyClockBean clockBean = miuiClockView != null ? miuiClockView.getClockBean() : null;
        String templateId = clockBean != null ? clockBean.getTemplateId() : null;
        int style = clockBean != null ? clockBean.getStyle() : 0;
        String tinyKeyguardTemplateStyle = getTinyKeyguardTemplateStyle(templateId);
        String tinyKeyguardTemplateStylePattern = getTinyKeyguardTemplateStylePattern(templateId, style);
        arrayMap.put(am.af, "348.5.3.1.40908");
        arrayMap.put("template_style", tinyKeyguardTemplateStyle);
        arrayMap.put("template_style_pattern", tinyKeyguardTemplateStylePattern);
        trackEvent("trigger", arrayMap);
        Log.d("AnalyticManager", "trackAodShowEvent");
    }

    public void trackAodShowModeState() {
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "aod_show_style", Utils.getDefaultShowStyle(this.mContext));
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("state_value", Integer.valueOf(i));
        trackEvent("aod_show_mode", arrayMap);
    }

    public void trackAodStepToImageCommunityAction() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.trackEvent("aod_step_to_image_community_action", null);
            }
        });
    }

    public void trackAodStyleClickAction(final StyleInfo styleInfo) {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticManager analyticManager = this.f$0;
                analyticManager.trackEvent("aod_style_click_action", analyticManager.createStyleInfoParameters(styleInfo));
            }
        });
    }

    public void trackAodStyleHistoryPage() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.trackEvent("aod_history_page", null);
            }
        });
    }

    public void trackAodStylePage() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager.1
            @Override // java.lang.Runnable
            public void run() {
                AnalyticManager.this.trackEvent("aod_style_page", null);
            }
        });
    }

    public void trackAodStyleState() {
        Map mapCreateStyleInfoParameters = createStyleInfoParameters();
        String str = (String) mapCreateStyleInfoParameters.get("style_event");
        if (!TextUtils.isEmpty(str)) {
            trackEvent(str, mapCreateStyleInfoParameters);
        }
        mapCreateStyleInfoParameters.put("history_count_grade", Integer.valueOf(HistoryProviderHelper.getInstance(this.mContext).getHistoryCount() / 10));
        trackEvent("aod_style", mapCreateStyleInfoParameters);
        if (Utils.isAodEnable(this.mContext)) {
            trackEvent("aod_style_active", mapCreateStyleInfoParameters);
        }
    }

    public void trackAodVisibility(boolean z) {
        if (z == this.sAodVisible) {
            return;
        }
        Log.i("AnalyticManager", "trackAodVisibility: " + z);
        this.sAodVisible = z;
        if (z) {
            this.mAodVisibleStartTime = System.currentTimeMillis();
            return;
        }
        if (this.mAodVisibleStartTime > 0) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = this.mAodVisibleStartTime;
            if (j < jCurrentTimeMillis) {
                saveAodVisibleEvent(new AodVisibleEvent(j, jCurrentTimeMillis));
            }
            this.mAodVisibleStartTime = -1L;
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0064  */
    /* JADX WARN: Code duplicated, block: B:33:? A[RETURN, SYNTHETIC] */
    public void trackAodVisibleEvent() {
        long jDecodeLong;
        if (this.sAodVisible && this.mAodVisibleStartTime > 0) {
            Log.i("AnalyticManager", "trackAodVisibleEvent: sAodVisible startTime:" + this.mAodVisibleStartTime);
            trackAodVisibility(false);
            trackAodVisibility(true);
        }
        String[] strArrAllKeys = this.mAodVisibleEventSp.allKeys();
        if (strArrAllKeys == null || strArrAllKeys.length <= 0) {
            return;
        }
        try {
            jDecodeLong = 0;
            for (String str : strArrAllKeys) {
                try {
                    jDecodeLong += this.mAodVisibleEventSp.decodeLong(str);
                } catch (Exception e) {
                    e = e;
                    Log.e("AnalyticManager", "trackAodVisibleEvent: ", e);
                    if (jDecodeLong > 0) {
                        ArrayMap arrayMap = new ArrayMap();
                        arrayMap.put("visible_duration_min", Long.valueOf(jDecodeLong / 60000));
                        trackEvent("aod_show_mode", arrayMap);
                    }
                }
            }
            this.mAodVisibleEventSp.edit().clear().apply();
        } catch (Exception e2) {
            e = e2;
            jDecodeLong = 0;
        }
        if (jDecodeLong > 0) {
            ArrayMap arrayMap2 = new ArrayMap();
            arrayMap2.put("visible_duration_min", Long.valueOf(jDecodeLong / 60000));
            trackEvent("aod_show_mode", arrayMap2);
        }
    }

    public final void trackEvent(String str, Map map) {
        String str2;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("trackEvent: ");
            sb.append(str);
            sb.append(" ");
            if (map == null) {
                str2 = "";
            } else {
                str2 = "parameter:" + map.toString();
            }
            sb.append(str2);
            Log.i("AnalyticManager", sb.toString());
        }
        AnalyticsWrapper.trackEvent(str, map);
    }

    public void trackNewAodModeStateSwitch() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticManager.$r8$lambda$L1TexMdOdtMlZ_cr3U6SvjvYXKU(this.f$0);
            }
        });
    }

    public void trackNewAodState() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("aod_on_status", getAodModeString());
        if (Utils.isAodEnable(this.mContext)) {
            if (Utils.isSupportFsAod()) {
                arrayMap.put("aod_style_mode", getFullAodStyleString());
                if (Utils.isFsAodSameToLock(this.mContext)) {
                    arrayMap.put("aod_notification_status", getFullAodNotifiStateString());
                }
            }
            arrayMap.put("batterysaver_aod_off", getBatterySaverModeString());
            if (DeviceUtil.INSTANCE.isPad()) {
                arrayMap.put("charging_aod_off", getChargingAodOff());
                BigTimeCategoryInfo padCategoryInfo = getPadCategoryInfo();
                String[] padAodSettingStateString = getPadAodSettingStateString(padCategoryInfo);
                arrayMap.put("used_aod_style", getPadUsedAodStyleString(padCategoryInfo));
                arrayMap.put("aod_notification_status", padAodSettingStateString[0]);
                arrayMap.put("aod_energy_status", padAodSettingStateString[1]);
                arrayMap.put("aod_date_status", padAodSettingStateString[2]);
                arrayMap.put("lunar_calendar_status", padAodSettingStateString[3]);
            } else {
                arrayMap.put("used_aod_style", getUsedAodStyleString());
                arrayMap.put("aod_off_time", getAodShowModeString());
            }
        }
        if (DeviceUtil.INSTANCE.isPad()) {
            arrayMap.put(am.af, "348.2.2.1.41260");
        } else {
            arrayMap.put(am.af, "348.2.2.1.36508");
        }
        trackEvent(am.M, arrayMap);
    }

    public void trackNotificationStyleState() {
        String selectedStyleName = NotificationStyleManager.getSelectedStyleName(this.mContext);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", selectedStyleName);
        trackEvent("aod_notification_style", arrayMap);
    }

    public void trackPadAodModeStateSwitch(final String str, final boolean z) {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticManager.$r8$lambda$2mCWP5qn0KD7ObHVPUcxoWnj4N4(this.f$0, z, str);
            }
        });
    }
}

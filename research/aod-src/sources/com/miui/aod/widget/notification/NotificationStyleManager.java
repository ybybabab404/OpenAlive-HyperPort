package com.miui.aod.widget.notification;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.DrawableData;
import com.miui.aod.util.CategoryPrefs;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.widget.NotificationAnimationView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NotificationStyleManager {
    public static final String KEY_ANIMATION_STYLE = "notification_animation_style";
    public static final int[] PARTICLE_WAVE_ARRAY;
    public static final String STYLE_NAME_BLUE_LINE = "blue_wave";
    public static final String STYLE_NAME_GREEN_WAVE = "green_wave";
    public static final String STYLE_NAME_NONE = "none";
    public static final String STYLE_NAME_PARTICLE_WAVE = "particle_wave";
    public static final String STYLE_NAME_RED_WAVE = "red_wave";
    public static final String STYLE_NAME_SCREEN_ON = "screen_on";
    private static final String TAG = "NotificationStyleManage";
    public static final Map<String, int[]> sFrameAnimationMap;
    public static List<DrawableData> sStyleNameList = new ArrayList();

    static {
        ArrayMap arrayMap = new ArrayMap();
        sFrameAnimationMap = arrayMap;
        int[] iArr = {R.drawable.particle_wave_1, R.drawable.particle_wave_2, R.drawable.particle_wave_3, R.drawable.particle_wave_4, R.drawable.particle_wave_5, R.drawable.particle_wave_6, R.drawable.particle_wave_7, R.drawable.particle_wave_8, R.drawable.particle_wave_9, R.drawable.particle_wave_10, R.drawable.particle_wave_11, R.drawable.particle_wave_12, R.drawable.particle_wave_13, R.drawable.particle_wave_14, R.drawable.particle_wave_15, R.drawable.particle_wave_16, R.drawable.particle_wave_17, R.drawable.particle_wave_18, R.drawable.particle_wave_19, R.drawable.particle_wave_20, R.drawable.particle_wave_21, R.drawable.particle_wave_22, R.drawable.particle_wave_23, R.drawable.particle_wave_24, R.drawable.particle_wave_25, R.drawable.particle_wave_26, R.drawable.particle_wave_27, R.drawable.particle_wave_28, R.drawable.particle_wave_29, R.drawable.particle_wave_30, R.drawable.particle_wave_31, R.drawable.particle_wave_32, R.drawable.particle_wave_33, R.drawable.particle_wave_34, R.drawable.particle_wave_35, R.drawable.particle_wave_36, R.drawable.particle_wave_37, R.drawable.particle_wave_38, R.drawable.particle_wave_39, R.drawable.particle_wave_40, R.drawable.particle_wave_41, R.drawable.particle_wave_42, R.drawable.particle_wave_43, R.drawable.particle_wave_44, R.drawable.particle_wave_45, R.drawable.particle_wave_46, R.drawable.particle_wave_47, R.drawable.particle_wave_48, R.drawable.particle_wave_49, R.drawable.particle_wave_50, R.drawable.particle_wave_51, R.drawable.particle_wave_52, R.drawable.particle_wave_53, R.drawable.particle_wave_54, R.drawable.particle_wave_55, R.drawable.particle_wave_56, R.drawable.particle_wave_57, R.drawable.particle_wave_58, R.drawable.particle_wave_59, R.drawable.particle_wave_60, R.drawable.particle_wave_61, R.drawable.particle_wave_62, R.drawable.particle_wave_63, R.drawable.particle_wave_64, R.drawable.particle_wave_65, R.drawable.particle_wave_66, R.drawable.particle_wave_67, R.drawable.particle_wave_68, R.drawable.particle_wave_69, R.drawable.particle_wave_70, R.drawable.particle_wave_71, R.drawable.particle_wave_72, R.drawable.particle_wave_73, R.drawable.particle_wave_74, R.drawable.particle_wave_75, R.drawable.particle_wave_76, R.drawable.particle_wave_77, R.drawable.particle_wave_78, R.drawable.particle_wave_79, R.drawable.particle_wave_80, R.drawable.particle_wave_81, R.drawable.particle_wave_82, R.drawable.particle_wave_83, R.drawable.particle_wave_84, R.drawable.particle_wave_85, R.drawable.particle_wave_86, R.drawable.particle_wave_87, R.drawable.particle_wave_88, R.drawable.particle_wave_89, R.drawable.particle_wave_90, R.drawable.particle_wave_91};
        PARTICLE_WAVE_ARRAY = iArr;
        sStyleNameList.add(new DrawableData(R.drawable.none_thumbnail, "none"));
        sStyleNameList.add(new DrawableData(R.drawable.screen_on_thumbnail, STYLE_NAME_SCREEN_ON));
        sStyleNameList.add(new DrawableData(R.drawable.blue_wave_thumbnail, STYLE_NAME_BLUE_LINE));
        sStyleNameList.add(new DrawableData(R.drawable.red_wave_thumbnail, STYLE_NAME_RED_WAVE));
        if (!Utils.isFoldDevice()) {
            sStyleNameList.add(new DrawableData(R.drawable.particle_wave_thumbnail, STYLE_NAME_PARTICLE_WAVE));
        }
        arrayMap.put(STYLE_NAME_GREEN_WAVE, iArr);
        arrayMap.put(STYLE_NAME_PARTICLE_WAVE, iArr);
    }

    public static NotificationAnimationView.AnimationDrawer createAnimationDrawer(Context context, String str) {
        NotificationAnimationView.AnimationDrawer redWaveDrawer;
        int[] iArr;
        if (str == null) {
            return new BlueWaveDrawer(context);
        }
        if (!TextUtils.isEmpty(str)) {
            switch (str) {
                case "red_wave":
                    redWaveDrawer = new RedWaveDrawer(context);
                    break;
                case "green_wave":
                case "particle_wave":
                    redWaveDrawer = new NotificationFrameAnimationDrawer(context);
                    break;
                case "blue_wave":
                    redWaveDrawer = new BlueWaveDrawer(context);
                    break;
                default:
                    redWaveDrawer = new BlueWaveDrawer(context);
                    Log.wtf(TAG, "wrong drawer: " + str, new Throwable());
                    break;
            }
        } else {
            redWaveDrawer = null;
        }
        if ((redWaveDrawer instanceof NotificationFrameAnimationDrawer) && (iArr = sFrameAnimationMap.get(str)) != null) {
            NotificationFrameAnimationDrawer notificationFrameAnimationDrawer = (NotificationFrameAnimationDrawer) redWaveDrawer;
            notificationFrameAnimationDrawer.setFrameArray(iArr);
            notificationFrameAnimationDrawer.startAnimation();
        }
        return redWaveDrawer;
    }

    public static String getDrawableTitle(Context context, DrawableData drawableData) {
        if ("none".equals(drawableData.mName)) {
            return context.getString(R.string.notification_nothing);
        }
        if (STYLE_NAME_SCREEN_ON.equals(drawableData.mName)) {
            return context.getString(R.string.notification_screen_on);
        }
        if (STYLE_NAME_BLUE_LINE.equals(drawableData.mName)) {
            return context.getString(R.string.notification_anim_rhythm);
        }
        if (STYLE_NAME_RED_WAVE.equals(drawableData.mName)) {
            return context.getString(R.string.notification_anim_pulse);
        }
        return STYLE_NAME_PARTICLE_WAVE.equals(drawableData.mName) ? context.getString(R.string.notification_anim_star) : "";
    }

    public static String getSelectedStyleName(Context context) {
        int keyguardNotificationStatus = Utils.getKeyguardNotificationStatus(context);
        if (keyguardNotificationStatus == 0) {
            return "none";
        }
        if (keyguardNotificationStatus == 1) {
            return STYLE_NAME_SCREEN_ON;
        }
        String string = MiuiSettingsUtils.Secure.getString(context.getContentResolver(), KEY_ANIMATION_STYLE);
        Log.d(TAG, "getSelectedStyleName: " + string);
        return TextUtils.isEmpty(string) ? CategoryPrefs.getString(context, KEY_ANIMATION_STYLE, STYLE_NAME_BLUE_LINE) : string;
    }

    public static List<DrawableData> getStyleList() {
        return Collections.unmodifiableList(sStyleNameList);
    }

    public static boolean isWaveDrawable(DrawableData drawableData) {
        if (drawableData != null) {
            return STYLE_NAME_BLUE_LINE.equals(drawableData.mName) || STYLE_NAME_RED_WAVE.equals(drawableData.mName) || STYLE_NAME_PARTICLE_WAVE.equals(drawableData.mName);
        }
        return false;
    }

    public static void setSelectedStyleName(Context context, String str) {
        Log.d(TAG, "setSelectedStyleName: " + str);
        MiuiSettingsUtils.Secure.putString(context.getContentResolver(), KEY_ANIMATION_STYLE, str);
    }
}

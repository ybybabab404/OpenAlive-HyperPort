package com.miui.clock.utils;

import android.content.Context;
import android.util.Log;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class OtaUtils {
    /* JADX WARN: Code duplicated, block: B:22:0x0066  */
    public static void dealWithClassicOTAColorEffect(Context context, ClockBean clockBean) {
        int i;
        if (DeviceConfig.supportBackgroundBlur(context)) {
            String templateId = clockBean.getTemplateId();
            templateId.getClass();
            i = 1;
            if (templateId.equals("classic_small") || templateId.equals("classic")) {
                if (clockBean.isAutoPrimaryColor() && clockBean.isAutoSecondaryColor()) {
                    Log.d("OtaUtils", "Auto primary color and auto second color");
                } else if (clockBean.isAutoPrimaryColor()) {
                    Log.d("OtaUtils", "Auto primary color");
                    clockBean.setSecondaryBlendColor(clockBean.getSecondaryColor());
                } else if (clockBean.isAutoSecondaryColor()) {
                    Log.d("OtaUtils", "Auto secondary color");
                    clockBean.setBlendColor(clockBean.getPrimaryColor());
                    clockBean.setSecondaryBlendColor(clockBean.getPrimaryColor());
                }
            } else if (!clockBean.isAutoPrimaryColor()) {
                i = 0;
            }
        } else {
            i = 0;
        }
        Log.d("OtaUtils", "dealWithClassicOTAColorEffect: " + i);
        clockBean.setClockEffect(i);
    }

    public static void dealWithClassicOTATextArea(ClockBean clockBean) {
        int classicLine1 = clockBean.getClassicLine1();
        if (classicLine1 == 11) {
            classicLine1 = DeviceConfig.isGlobalRom() ? 0 : 101;
        }
        clockBean.setClassicLine1(classicLine1);
    }

    public static void dealWithClassicPlusOTAFont(ClockBean clockBean) {
        switch (clockBean.getStyle()) {
            case 22:
                clockBean.setStyle(21);
                clockBean.setClockWeight(250);
                break;
            case 23:
                clockBean.setStyle(21);
                clockBean.setClockWeight(450);
                break;
            case 24:
                clockBean.setStyle(22);
                clockBean.setClockWeight(420);
                break;
            case 25:
                clockBean.setStyle(24);
                clockBean.setClockWeight(330);
                break;
            case 26:
                clockBean.setStyle(23);
                clockBean.setClockWeight(450);
                break;
            case 27:
                clockBean.setStyle(21);
                clockBean.setClockWeight(630);
                break;
            case 28:
                clockBean.setStyle(25);
                clockBean.setClockWeight(520);
                break;
            default:
                clockBean.setStyle(21);
                clockBean.setClockWeight(305);
                break;
        }
    }

    public static void dealWithClassicPlusOTATextArea(ClockBean clockBean) {
        int classicLine3 = clockBean.getClassicLine3();
        if (classicLine3 == 11) {
            classicLine3 = 100;
        }
        clockBean.setClassicLine1(classicLine3);
    }
}

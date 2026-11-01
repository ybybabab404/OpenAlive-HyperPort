package com.miui.clock.module;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.utils.DataUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.xiaomi.onetrack.api.bf;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ClockBean implements Cloneable {
    private int blendColor;
    private int classicLine1;
    private int classicLine2;
    private int classicLine3;
    private int classicLine4;
    private int classicLine5;
    private String classicSignature;
    private float clockBottomRatio;
    private int clockDepthType;
    private int clockEffect;
    private float clockTopRatio;
    private int clockWeight;
    private boolean disableContainerPassBlur;
    private String dualClockLocalCity;
    private boolean enableDiffusion;
    private Float glassTransparency;
    private int infoAreaColor;
    private boolean isAutoPrimaryColor;
    private boolean isAutoSecondaryColor;
    private boolean isDiffHourMinuteColor;
    private boolean isDoubleRow;
    private boolean isSkipScreenTransform;
    private boolean mSupportMultiWindowBlur;
    private String presetHealthJson;
    private String presetWeatherJson;
    private int primaryColor;
    private int secondaryBlendColor;
    private int secondaryColor;
    private String signatureLine1;
    private String signatureLine2;
    private String signatureLine3;
    private int style;
    private String templateId;
    private float timeHeight;
    private float timeWidth;
    private boolean unablePresetData;
    private int fontStyle = 0;
    private int extraFlag = 0;
    private boolean isColonShow = true;
    private int hollowStyle = 0;

    public ClockBean(String str) {
        this.templateId = str;
    }

    public ClockBean(String str, int i) {
        this.templateId = str;
        this.style = i;
    }

    @NonNull
    public static ClockBean getClassicDefaultBean(Context context) {
        String string;
        int iUpdateCurrentUserId = DateFormatUtils.updateCurrentUserId();
        ClockBean clockBean = null;
        if (DateFormatUtils.isCrossUser(context)) {
            try {
                string = (String) Settings.Secure.class.getMethod("getStringForUser", ContentResolver.class, String.class, Integer.TYPE).invoke(null, context.getContentResolver(), "miui_15_default_lockscreen_info", Integer.valueOf(iUpdateCurrentUserId));
            } catch (Exception e) {
                Log.e("ClockBean", "getStringForUser fail", e);
                string = "";
            }
        } else {
            string = Settings.Secure.getString(context.getContentResolver(), "miui_15_default_lockscreen_info");
        }
        if (!TextUtils.isEmpty(string)) {
            try {
                clockBean = (ClockBean) new Gson().fromJson(new JSONObject(string).getString("clockInfo"), ClockBean.class);
            } catch (Exception e2) {
                Log.e("ClockBean", "getIntForUser fail", e2);
            }
        }
        if (clockBean == null || TextUtils.isEmpty(clockBean.getTemplateId())) {
            Log.d("ClockBean", "getClassicDefaultBean");
            clockBean = new ClockBean("classic");
            boolean z = getShowCarrier(context, iUpdateCurrentUserId) == 1;
            boolean z2 = getShowLunarCalendar(context, iUpdateCurrentUserId) == 1;
            boolean zIsWeatherAppInstalled = DataUtils.isWeatherAppInstalled(context, iUpdateCurrentUserId);
            if (!z && z2) {
                clockBean.setClassicLine1(101);
            } else if (isDataCapable(context) && z) {
                clockBean.setClassicLine1(11);
            } else {
                clockBean.setClassicLine1(0);
            }
            clockBean.setClassicLine2(300);
            if (!zIsWeatherAppInstalled) {
                clockBean.setClassicLine3(206);
            } else if (z2 && z) {
                clockBean.setClassicLine3(210);
            } else {
                clockBean.setClassicLine3(202);
            }
            if (zIsWeatherAppInstalled) {
                clockBean.setClassicLine4(400);
            } else if (z2 && z) {
                clockBean.setClassicLine4(209);
            } else {
                clockBean.setClassicLine4(200);
            }
            clockBean.setClassicLine5(0);
            clockBean.setAutoPrimaryColor(true);
            clockBean.setAutoSecondaryColor(true);
        }
        return clockBean;
    }

    public static int getClassicDefaultLine1(Context context, int i) {
        return getShowCarrier(context, i) == 1 ? 11 : 0;
    }

    public static int getShowCarrier(Context context, int i) {
        if (!DateFormatUtils.isCrossUser(context)) {
            return Settings.System.getInt(context.getContentResolver(), "status_bar_show_carrier_under_keyguard", 1);
        }
        try {
            Class cls = Integer.TYPE;
            return ((Integer) Settings.System.class.getMethod("getIntForUser", ContentResolver.class, String.class, cls, cls).invoke(null, context.getContentResolver(), "status_bar_show_carrier_under_keyguard", 1, Integer.valueOf(i))).intValue();
        } catch (Exception e) {
            Log.e("ClockBean", "getIntForUser fail", e);
            return 1;
        }
    }

    public static int getShowLunarCalendar(Context context, int i) {
        if (!DateFormatUtils.isCrossUser(context)) {
            return Settings.System.getInt(context.getContentResolver(), "show_lunar_calendar", 0);
        }
        try {
            Class cls = Integer.TYPE;
            return ((Integer) Settings.System.class.getMethod("getIntForUser", ContentResolver.class, String.class, cls, cls).invoke(null, context.getContentResolver(), "show_lunar_calendar", 0, Integer.valueOf(i))).intValue();
        } catch (Exception e) {
            Log.e("ClockBean", "getIntForUser fail", e);
            return 0;
        }
    }

    public static boolean isDataCapable(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(bf.d)).isDataCapable();
        } catch (Exception e) {
            Log.e("ClockBean", "isDataCapable: ", e);
            return true;
        }
    }

    public void clearExtraFlag(int i) {
        this.extraFlag = (~i) & this.extraFlag;
    }

    public Object clone() {
        try {
            return (ClockBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ClockBean clockBean = (ClockBean) obj;
            if (this.infoAreaColor == clockBean.infoAreaColor && this.primaryColor == clockBean.primaryColor && this.secondaryColor == clockBean.secondaryColor && this.blendColor == clockBean.blendColor && this.secondaryBlendColor == clockBean.secondaryBlendColor && this.style == clockBean.style && this.enableDiffusion == clockBean.enableDiffusion && this.classicLine1 == clockBean.classicLine1 && this.classicLine2 == clockBean.classicLine2 && this.classicLine3 == clockBean.classicLine3 && this.classicLine4 == clockBean.classicLine4 && this.classicLine5 == clockBean.classicLine5 && this.clockWeight == clockBean.clockWeight && Objects.equals(this.signatureLine1, clockBean.signatureLine1) && Objects.equals(this.signatureLine2, clockBean.signatureLine2) && Objects.equals(this.signatureLine3, clockBean.signatureLine3) && this.isAutoPrimaryColor == clockBean.isAutoPrimaryColor && this.isAutoSecondaryColor == clockBean.isAutoSecondaryColor && this.isDiffHourMinuteColor == clockBean.isDiffHourMinuteColor && TextUtils.equals(this.templateId, clockBean.templateId) && clockBean.clockEffect == this.clockEffect && clockBean.extraFlag == this.extraFlag && this.fontStyle == clockBean.fontStyle && this.isDoubleRow == clockBean.isDoubleRow && this.isColonShow == clockBean.isColonShow && this.timeWidth == clockBean.timeWidth && this.timeHeight == clockBean.timeHeight && this.clockTopRatio == clockBean.clockTopRatio && this.clockBottomRatio == clockBean.clockBottomRatio && this.clockDepthType == clockBean.clockDepthType && this.glassTransparency == clockBean.glassTransparency && this.hollowStyle == clockBean.hollowStyle && TextUtils.equals(this.classicSignature, clockBean.classicSignature)) {
                return true;
            }
        }
        return false;
    }

    public int getBlendColor() {
        return this.blendColor;
    }

    public int getClassicLine1() {
        return this.classicLine1;
    }

    public int getClassicLine2() {
        return this.classicLine2;
    }

    public int getClassicLine3() {
        return this.classicLine3;
    }

    public int getClassicLine4() {
        return this.classicLine4;
    }

    public int getClassicLine5() {
        return this.classicLine5;
    }

    public String getClassicSignature() {
        return this.classicSignature;
    }

    public float getClockBottomRatio() {
        return this.clockBottomRatio;
    }

    public AllInOneClockStyle.Region getClockDepthType() {
        return AllInOneClockStyle.toRegion(this.clockDepthType);
    }

    public int getClockEffect() {
        return this.clockEffect;
    }

    public float getClockTopRatio() {
        return this.clockTopRatio;
    }

    public int getClockWeight() {
        return this.clockWeight;
    }

    public String getDualClockLocalCity() {
        return this.dualClockLocalCity;
    }

    public int getExtraFlag() {
        return this.extraFlag;
    }

    public int getFontStyle() {
        return this.fontStyle;
    }

    public Float getGlassTransparency() {
        return this.glassTransparency;
    }

    public int getHollowStyle() {
        return this.hollowStyle;
    }

    public int getInfoAreaColor() {
        return this.infoAreaColor;
    }

    public String getPresetHealthJson() {
        return this.presetHealthJson;
    }

    public String getPresetWeatherJson() {
        return this.presetWeatherJson;
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public int getSecondaryBlendColor() {
        return this.secondaryBlendColor;
    }

    public int getSecondaryColor() {
        return this.secondaryColor;
    }

    public String getSignatureLine1() {
        return this.signatureLine1;
    }

    public String getSignatureLine2() {
        return this.signatureLine2;
    }

    public String getSignatureLine3() {
        return this.signatureLine3;
    }

    public int getStyle() {
        return this.style;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public float getTimeHeight() {
        return this.timeHeight;
    }

    public float getTimeWidth() {
        return this.timeWidth;
    }

    public int hashCode() {
        return Objects.hash(this.templateId, Integer.valueOf(this.infoAreaColor), Integer.valueOf(this.primaryColor), Integer.valueOf(this.secondaryColor), Integer.valueOf(this.style), Integer.valueOf(this.fontStyle), Boolean.valueOf(this.enableDiffusion), Integer.valueOf(this.classicLine1), Integer.valueOf(this.classicLine2), Integer.valueOf(this.classicLine3), Integer.valueOf(this.classicLine4), Integer.valueOf(this.classicLine5), Integer.valueOf(this.clockWeight), this.classicSignature, Boolean.valueOf(this.isAutoPrimaryColor), Boolean.valueOf(this.isAutoSecondaryColor), Boolean.valueOf(this.isDiffHourMinuteColor), Integer.valueOf(this.clockEffect), Integer.valueOf(this.extraFlag), Integer.valueOf(this.blendColor), Integer.valueOf(this.secondaryBlendColor), this.signatureLine1, this.signatureLine2, this.signatureLine3, Float.valueOf(this.clockBottomRatio), Float.valueOf(this.clockTopRatio), Float.valueOf(this.timeHeight), Float.valueOf(this.timeWidth), Integer.valueOf(this.clockDepthType), Boolean.valueOf(this.isDoubleRow), Boolean.valueOf(this.isColonShow), this.glassTransparency, Integer.valueOf(this.hollowStyle));
    }

    public boolean isAutoPrimaryColor() {
        return this.isAutoPrimaryColor;
    }

    public boolean isAutoSecondaryColor() {
        return this.isAutoSecondaryColor;
    }

    public boolean isColonShow() {
        return this.isColonShow;
    }

    public boolean isDiffHourMinuteColor() {
        return this.isDiffHourMinuteColor;
    }

    public boolean isDisableContainerPassBlur() {
        return this.disableContainerPassBlur;
    }

    public boolean isDoubleRow() {
        return this.isDoubleRow;
    }

    public boolean isEnableDiffusion() {
        return this.enableDiffusion;
    }

    public boolean isSkipScreenTransform() {
        return this.isSkipScreenTransform;
    }

    public boolean isUnablePresetData() {
        return this.unablePresetData;
    }

    public void setAutoPrimaryColor(boolean z) {
        this.isAutoPrimaryColor = z;
    }

    public void setAutoSecondaryColor(boolean z) {
        this.isAutoSecondaryColor = z;
    }

    public void setBlendColor(int i) {
        this.blendColor = i;
    }

    public void setClassicLine1(int i) {
        this.classicLine1 = i;
    }

    public void setClassicLine2(int i) {
        this.classicLine2 = i;
    }

    public void setClassicLine3(int i) {
        this.classicLine3 = i;
    }

    public void setClassicLine4(int i) {
        this.classicLine4 = i;
    }

    public void setClassicLine5(int i) {
        this.classicLine5 = i;
    }

    public void setClassicSignature(String str) {
        this.classicSignature = str;
    }

    public void setClockBottomRatio(float f) {
        this.clockBottomRatio = f;
    }

    public void setClockDepthType(AllInOneClockStyle.Region region) {
        this.clockDepthType = AllInOneClockStyle.toRegionInt(region);
    }

    public void setClockEffect(int i) {
        this.clockEffect = i;
    }

    public void setClockTopRatio(float f) {
        this.clockTopRatio = f;
    }

    public void setClockWeight(int i) {
        this.clockWeight = i;
    }

    public void setColonShow(boolean z) {
        this.isColonShow = z;
    }

    public void setDiffHourMinuteColor(boolean z) {
        this.isDiffHourMinuteColor = z;
    }

    public void setDisableContainerPassBlur(boolean z) {
        this.disableContainerPassBlur = z;
    }

    public void setDoubleRow(boolean z) {
        this.isDoubleRow = z;
    }

    public void setDualClockLocalCity(String str) {
        this.dualClockLocalCity = str;
    }

    public void setEnableDiffusion(boolean z) {
        this.enableDiffusion = z;
    }

    public void setExtraFlag(int i) {
        this.extraFlag = i;
    }

    public void setFontStyle(int i) {
        this.fontStyle = i;
    }

    public void setGlassTransparency(Float f) {
        this.glassTransparency = f;
    }

    public void setHollowStyle(int i) {
        this.hollowStyle = i;
    }

    public void setInfoAreaColor(int i) {
        this.infoAreaColor = i;
    }

    public void setPresetHealthJson(String str) {
        this.presetHealthJson = str;
    }

    public void setPresetWeatherJson(String str) {
        this.presetWeatherJson = str;
    }

    public void setPrimaryColor(int i) {
        this.primaryColor = i;
    }

    public void setSecondaryBlendColor(int i) {
        this.secondaryBlendColor = i;
    }

    public void setSecondaryColor(int i) {
        this.secondaryColor = i;
    }

    public void setSignatureLine1(String str) {
        this.signatureLine1 = str;
    }

    public void setSignatureLine2(String str) {
        this.signatureLine2 = str;
    }

    public void setSignatureLine3(String str) {
        this.signatureLine3 = str;
    }

    public void setSkipScreenTransform(boolean z) {
        this.isSkipScreenTransform = z;
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public void setSupportMultiWindowBlur(boolean z) {
        this.mSupportMultiWindowBlur = z;
    }

    public void setTemplateId(String str) {
        this.templateId = str;
    }

    public void setTimeHeight(float f) {
        this.timeHeight = f;
    }

    public void setTimeWidth(float f) {
        this.timeWidth = f;
    }

    public void setUnablePresetData(boolean z) {
        this.unablePresetData = z;
    }

    public boolean supportMultiWindowBlur() {
        return this.mSupportMultiWindowBlur;
    }

    public String toString() {
        return "ClockBean{templateId='" + this.templateId + "', infoAreaColor=" + this.infoAreaColor + ", primaryColor=" + this.primaryColor + ", secondaryColor=" + this.secondaryColor + ", style=" + this.style + ", fontStyle=" + this.fontStyle + ", glassTransparency=" + this.glassTransparency + ", hollowStyle=" + this.hollowStyle + ", isDoubleRow=" + this.isDoubleRow + ", isColonShow=" + this.isColonShow + ", blendColor=" + this.blendColor + ", secondaryBlendColor=" + this.secondaryBlendColor + ", clockEffect=" + this.clockEffect + ", enableDiffusion=" + this.enableDiffusion + ", classicLine1=" + this.classicLine1 + ", classicLine2=" + this.classicLine2 + ", classicLine3=" + this.classicLine3 + ", classicLine4=" + this.classicLine4 + ", classicLine5=" + this.classicLine5 + ", clockWeight=" + this.clockWeight + ", signatureLine1='" + this.signatureLine1 + "', signatureLine2='" + this.signatureLine2 + "', signatureLine3='" + this.signatureLine3 + "', classicSignature='" + this.classicSignature + "', isAutoPrimaryColor=" + this.isAutoPrimaryColor + ", isAutoSecondaryColor=" + this.isAutoSecondaryColor + ", isDiffHourMinuteColor=" + this.isDiffHourMinuteColor + ", presetHealthJson='" + this.presetHealthJson + "', presetWeatherJson='" + this.presetWeatherJson + "', dualClockLocalCity='" + this.dualClockLocalCity + "', unablePresetData=" + this.unablePresetData + ", disableContainerPassBlur=" + this.disableContainerPassBlur + '}';
    }

    public void updateExtraFlag(int i) {
        this.extraFlag = i | this.extraFlag;
    }
}

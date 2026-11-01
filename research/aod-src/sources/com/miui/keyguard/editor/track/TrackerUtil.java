package com.miui.keyguard.editor.track;

import android.content.Context;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.SmartFrameInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.xiaomi.onetrack.api.an;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TrackerUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TrackerUtil {
    public static final TrackerUtil INSTANCE = new TrackerUtil();

    public final String getAllInOneTemplateClockFontStyle(Integer num) {
        if (num == null) {
            return "未知";
        }
        if (num.intValue() == 21) {
            return "MiSans_RC_F";
        }
        if (num.intValue() == 22) {
            return "MiSans_Rounded_F";
        }
        if (num.intValue() == 23) {
            return "MiSerif_F";
        }
        if (num.intValue() == 24) {
            return "MiSans_Square_F";
        }
        return num.intValue() == 25 ? "MiHaus_F" : num.toString();
    }

    public final String getAllInOneTemplateHollowStyle(int i) {
        if (i == 1) {
            return "不镂空";
        }
        if (i == 2) {
            return "时镂空";
        }
        if (i != 3) {
            return i != 4 ? String.valueOf(i) : "全镂空";
        }
        return "分镂空";
    }

    public final String getBackColor(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        SmartFrameInfo smartFrame;
        TemplateConfig lockscreenInfo2;
        SmartFrameInfo smartFrame2;
        TemplateConfig lockscreenInfo3;
        DoodleInfo doodle;
        TemplateConfig lockscreenInfo4;
        DoodleInfo doodle2;
        TemplateConfig lockscreenInfo5;
        ClockInfo clockInfo;
        Integer numValueOf = null;
        String templateId = (commonConfig2 == null || (lockscreenInfo5 = commonConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo5.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        if (StringsKt.equals$default(templateId, "doodle", false, 2, null)) {
            if (commonConfig2 != null && (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) != null && (doodle = lockscreenInfo3.getDoodle()) != null) {
                Integer numValueOf2 = Integer.valueOf(doodle.getSolidColor());
                if (commonConfig != null && (lockscreenInfo4 = commonConfig.getLockscreenInfo()) != null && (doodle2 = lockscreenInfo4.getDoodle()) != null) {
                    numValueOf = Integer.valueOf(doodle2.getSolidColor());
                }
                if (numValueOf2.equals(numValueOf)) {
                    return "否";
                }
            }
            return "是";
        }
        if (!StringsKt.equals$default(templateId, "smart_frame", false, 2, null)) {
            return null;
        }
        if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (smartFrame = lockscreenInfo.getSmartFrame()) != null) {
            Integer numValueOf3 = Integer.valueOf(smartFrame.getSolidColor());
            if (commonConfig != null && (lockscreenInfo2 = commonConfig.getLockscreenInfo()) != null && (smartFrame2 = lockscreenInfo2.getSmartFrame()) != null) {
                numValueOf = Integer.valueOf(smartFrame2.getSolidColor());
            }
            if (numValueOf3.equals(numValueOf)) {
                return "否";
            }
        }
        return "是";
    }

    public final String getClassicTemplateClockAreaTrackName(String str, ClockInfo clockInfo) {
        String str2;
        if (str != null) {
            switch (str.hashCode()) {
                case -1744945801:
                    if (str.equals("oversize_a")) {
                        Integer numValueOf = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
                        if (numValueOf != null && numValueOf.intValue() == 1) {
                            return "超大时间A—时间1";
                        }
                        if (numValueOf != null && numValueOf.intValue() == 2) {
                            return "超大时间A—时间2";
                        }
                        if (numValueOf != null && numValueOf.intValue() == 3) {
                            return "超大时间A—时间3";
                        }
                        return null;
                    }
                    break;
                case -1326135015:
                    if (str.equals("doodle")) {
                        Integer numValueOf2 = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
                        if (numValueOf2 != null && numValueOf2.intValue() == 1) {
                            return "涂鸦—中文";
                        }
                        if (numValueOf2 != null && numValueOf2.intValue() == 2) {
                            return "涂鸦—英文";
                        }
                        return null;
                    }
                    break;
                case -4941529:
                    if (str.equals("classic_plus")) {
                        Integer numValueOf3 = clockInfo != null ? Integer.valueOf(clockInfo.getClassicLine2()) : null;
                        if (numValueOf3 != null && numValueOf3.intValue() == 72) {
                            return "经典锁屏plus-竖向";
                        }
                        if (numValueOf3 != null && numValueOf3.intValue() == 71) {
                            return "经典锁屏plus-横向";
                        }
                        return null;
                    }
                    break;
                case 225289008:
                    if (str.equals("depth_pets")) {
                        return "萌宠";
                    }
                    break;
                case 853620882:
                    if (str.equals("classic")) {
                        Integer numValueOf4 = clockInfo != null ? Integer.valueOf(clockInfo.getClassicLine2()) : null;
                        if (numValueOf4 != null && numValueOf4.intValue() == 300) {
                            return "经典锁屏-数字时间";
                        }
                        if (numValueOf4 != null && numValueOf4.intValue() == 301) {
                            return "经典锁屏-中文时间";
                        }
                        if (numValueOf4 != null && numValueOf4.intValue() == 302) {
                            return "经典锁屏-数字时";
                        }
                        if (numValueOf4 != null && numValueOf4.intValue() == 303) {
                            return "经典锁屏-数字分";
                        }
                        if (numValueOf4 != null && numValueOf4.intValue() == 304) {
                            return "经典锁屏-中文时";
                        }
                        if (numValueOf4 != null && numValueOf4.intValue() == 305) {
                            return "经典锁屏-中文分";
                        }
                        Log.w("Keyguard-Editor-TrackerUtil", "getClassicTemplateClockAreaTrackName: invalid text area style " + numValueOf4);
                        return null;
                    }
                    break;
                case 1250520234:
                    if (str.equals("all_in_one")) {
                        Integer numValueOf5 = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
                        if (numValueOf5 != null && numValueOf5.intValue() == 1) {
                            return "all_in_one_max";
                        }
                        if (numValueOf5 != null && numValueOf5.intValue() == 2) {
                            return "all_in_one_plus1";
                        }
                        if (numValueOf5 != null && numValueOf5.intValue() == 3) {
                            return "all_in_one_plus2";
                        }
                        if (numValueOf5 != null && numValueOf5.intValue() == 4) {
                            return "all_in_one_超大时间A";
                        }
                        if (numValueOf5 != null && numValueOf5.intValue() == 5) {
                            return "all_in_one_超大时间B";
                        }
                        if (numValueOf5 != null && numValueOf5.intValue() == 7) {
                            return "all_in_one_超大时间C";
                        }
                        if (numValueOf5 != null && numValueOf5.intValue() == 8) {
                            return "all_in_one_超大时间D";
                        }
                        if (numValueOf5 != null && numValueOf5.intValue() == 6) {
                            return "all_in_one_影像杂志C";
                        }
                        return null;
                    }
                    break;
                case 1786073894:
                    if (str.equals("eastern_a")) {
                        Integer numValueOf6 = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
                        if (numValueOf6 != null && numValueOf6.intValue() == 1) {
                            return "东方美学A—宽双排";
                        }
                        if (numValueOf6 != null && numValueOf6.intValue() == 2) {
                            return "东方美学A—窄三排";
                        }
                        return null;
                    }
                    break;
                case 1786073896:
                    if (str.equals("eastern_c")) {
                        Integer numValueOf7 = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
                        if (numValueOf7 != null && numValueOf7.intValue() == 1) {
                            return "东方美学C—衬线体";
                        }
                        if (numValueOf7 != null && numValueOf7.intValue() == 2) {
                            return "东方美学C—手写体";
                        }
                        return null;
                    }
                    break;
            }
        }
        if ("magazine_a".equals(str)) {
            Integer numValueOf8 = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
            if (numValueOf8 != null && numValueOf8.intValue() == 1) {
                return "影像杂志A-左";
            }
            if (numValueOf8 != null && numValueOf8.intValue() == 2) {
                return "影像杂志A-中";
            }
            if (numValueOf8 != null && numValueOf8.intValue() == 3) {
                return "影像杂志A-右";
            }
            return null;
        }
        if ("magazine_c".equals(str)) {
            Integer numValueOf9 = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
            if (numValueOf9 != null && numValueOf9.intValue() == 6) {
                return "影像杂志C-样式1";
            }
            if (numValueOf9 != null && numValueOf9.intValue() == 7) {
                return "影像杂志C-样式2";
            }
            if (numValueOf9 != null && numValueOf9.intValue() == 5) {
                return "影像杂志C-样式3";
            }
            if (numValueOf9 != null && numValueOf9.intValue() == 8) {
                return "影像杂志C-样式4";
            }
            return null;
        }
        if (!Intrinsics.areEqual("rhombus", str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("菱形时间-样式");
        Integer numValueOf10 = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
        if (numValueOf10 != null && numValueOf10.intValue() == 1) {
            str2 = ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
        } else if (numValueOf10 != null && numValueOf10.intValue() == 3) {
            str2 = "C";
        } else if (numValueOf10 != null && numValueOf10.intValue() == 10) {
            str2 = "J";
        } else if (numValueOf10 != null && numValueOf10.intValue() == 4) {
            str2 = "D";
        } else if (numValueOf10 != null && numValueOf10.intValue() == 2) {
            str2 = an.a;
        } else if (numValueOf10 != null && numValueOf10.intValue() == 5) {
            str2 = ExifInterface.LONGITUDE_EAST;
        } else if (numValueOf10 != null && numValueOf10.intValue() == 9) {
            str2 = "I";
        } else if (numValueOf10 != null && numValueOf10.intValue() == 7) {
            str2 = "G";
        } else if (numValueOf10 != null && numValueOf10.intValue() == 8) {
            str2 = an.b;
        } else {
            str2 = (numValueOf10 != null && numValueOf10.intValue() == 6) ? "F" : "其他";
        }
        sb.append(str2);
        return sb.toString();
    }

    public final String getClassicTemplateContentAreaTrackName(String str, Integer num) {
        if (!Intrinsics.areEqual(str, "classic") && !Intrinsics.areEqual(str, "classic_plus")) {
            Log.w("Keyguard-Editor-TrackerUtil", "getClassicContentAreaStyle: unsupported template");
            return null;
        }
        if (num != null && num.intValue() == 0) {
            return "无";
        }
        if (num != null && num.intValue() == 300) {
            return "数字时间";
        }
        if (num != null && num.intValue() == 301) {
            return "中文时间";
        }
        if (num != null && num.intValue() == 302) {
            return "数字时";
        }
        if (num != null && num.intValue() == 303) {
            return "数字分";
        }
        if (num != null && num.intValue() == 304) {
            return "中文时";
        }
        if (num != null && num.intValue() == 305) {
            return "中文分";
        }
        if (num != null && num.intValue() == 200) {
            return "日期1";
        }
        if (num != null && num.intValue() == 202) {
            return "日期2";
        }
        if (num != null && num.intValue() == 209) {
            return "日期3";
        }
        if (num != null && num.intValue() == 210) {
            return "日期4";
        }
        if (num != null && num.intValue() == 201) {
            return "日期5";
        }
        if (num != null && num.intValue() == 203) {
            return "日期6";
        }
        if (num != null && num.intValue() == 205) {
            return "日期7";
        }
        if (num != null && num.intValue() == 204) {
            return "日期8";
        }
        if (num != null && num.intValue() == 206) {
            return "周";
        }
        if (num != null && num.intValue() == 207) {
            return "月";
        }
        if (num != null && num.intValue() == 208) {
            return "日";
        }
        if (num != null && num.intValue() == 211) {
            return "农历";
        }
        if (num != null && num.intValue() == 212) {
            return "农历年";
        }
        if (num != null && num.intValue() == 400) {
            return "温度";
        }
        if (num != null && num.intValue() == 401) {
            return "空气质量";
        }
        if (num != null && num.intValue() == 402) {
            return "降雨概率";
        }
        if (num != null && num.intValue() == 403) {
            return "日出日落";
        }
        if (num != null && num.intValue() == 405) {
            return "紫外线";
        }
        if (num != null && num.intValue() == 406) {
            return "体感";
        }
        if (num != null && num.intValue() == 407) {
            return "风";
        }
        if (num != null && num.intValue() == 408) {
            return "温度";
        }
        if (num != null && num.intValue() == 409) {
            return "气压";
        }
        if (num != null && num.intValue() == 500) {
            return "步数";
        }
        if (num != null && num.intValue() == 502) {
            return "卡路里";
        }
        if (num != null && num.intValue() == 508) {
            return "活动时长";
        }
        if (num != null && num.intValue() == 504) {
            return "站立";
        }
        if (num != null && num.intValue() == 506) {
            return "睡眠";
        }
        if (num != null && num.intValue() == 507) {
            return "活力指标";
        }
        if (num != null && num.intValue() == 501) {
            return "步数_进度条";
        }
        if (num != null && num.intValue() == 503) {
            return "卡路里_进度条";
        }
        if (num != null && num.intValue() == 505) {
            return "活动时长_进度条";
        }
        Log.d("Keyguard-Editor-TrackerUtil", "getClassicContentAreaStyle: invalid style " + num);
        return null;
    }

    public final String getClassicTemplateFontTrackName(String str, ClockInfo clockInfo) {
        if (!Intrinsics.areEqual(str, "classic") && !Intrinsics.areEqual(str, "classic_plus")) {
            Log.w("Keyguard-Editor-TrackerUtil", "getClassicTemplateFontTrackName: unsupported template");
            return null;
        }
        if (clockInfo != null) {
            return Integer.valueOf(clockInfo.getStyle()).toString();
        }
        return null;
    }

    public final String getClassicTemplateTextAreaTrackName(String str, ClockInfo clockInfo) {
        int iHashCode;
        if (str == null || ((iHashCode = str.hashCode()) == -138709961 ? !str.equals("classic_max") : iHashCode == -4941529 ? !str.equals("classic_plus") : !(iHashCode == 853620882 && str.equals("classic")))) {
            Log.w("Keyguard-Editor-TrackerUtil", "getClassicTemplateTextAreaTrackName: unsupported template");
            return null;
        }
        Integer numValueOf = clockInfo != null ? Integer.valueOf(clockInfo.getClassicLine1()) : null;
        if (numValueOf != null && numValueOf.intValue() == 11) {
            return "运营商";
        }
        if (numValueOf != null && numValueOf.intValue() == 100) {
            return "日期";
        }
        if (numValueOf != null && numValueOf.intValue() == 101) {
            return "农历";
        }
        if (numValueOf != null && numValueOf.intValue() == 400) {
            return "温度";
        }
        if (numValueOf != null && numValueOf.intValue() == 401) {
            return "空气质量";
        }
        if (numValueOf != null && numValueOf.intValue() == 404) {
            return "日出日落";
        }
        if (numValueOf != null && numValueOf.intValue() == 405) {
            return "紫外线";
        }
        if (numValueOf != null && numValueOf.intValue() == 406) {
            return "体感";
        }
        if (numValueOf != null && numValueOf.intValue() == 407) {
            return "风";
        }
        if (numValueOf != null && numValueOf.intValue() == 408) {
            return "湿度";
        }
        if (numValueOf != null && numValueOf.intValue() == 500) {
            return "步数";
        }
        if (numValueOf != null && numValueOf.intValue() == 502) {
            return "卡路里";
        }
        if (numValueOf != null && numValueOf.intValue() == 508) {
            return "活动时长";
        }
        if (numValueOf != null && numValueOf.intValue() == 504) {
            return "站立";
        }
        Log.w("Keyguard-Editor-TrackerUtil", "getClassicTemplateTextAreaTrackName: invalid text area style " + numValueOf);
        return null;
    }

    public final String getColorPanelLabel(int i) {
        if (i != 0) {
            return i != 13 ? "预置" : "自定义";
        }
        return "跟随锁屏";
    }

    public final String getDeleteTemplateTrackName(String str) {
        return getTemplateName(str);
    }

    public final String getDofState(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo;
        Integer numValueOf = null;
        String templateId = (commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        if (StringsKt.equals$default(templateId, "rhombus", false, 2, null) && StringsKt.equals$default(templateId, "smart_frame", false, 2, null)) {
            return null;
        }
        MagicType magicType = MagicType.INSTANCE;
        boolean zIsDepth = magicType.isDepth((commonConfig2 == null || (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo2.getMagicType()));
        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            numValueOf = Integer.valueOf(wallpaperInfo.getMagicType());
        }
        if (zIsDepth == magicType.isDepth(numValueOf)) {
            return "未编辑";
        }
        return zIsDepth ? "开启" : "关闭";
    }

    public final String getFontEffects(CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        Integer numValueOf = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo.getClockEffect());
        if (numValueOf != null && numValueOf.intValue() == 0) {
            return "纯色";
        }
        if (numValueOf != null && numValueOf.intValue() == 1) {
            return "混色";
        }
        if (numValueOf != null && numValueOf.intValue() == 2) {
            return "叠加";
        }
        if (numValueOf != null && numValueOf.intValue() == 4) {
            return "渐变模糊";
        }
        if (numValueOf != null && numValueOf.intValue() == 3) {
            return "辉光";
        }
        if (numValueOf != null && numValueOf.intValue() == 5) {
            return "玻璃";
        }
        return null;
    }

    public final Pair getHistoryTemplateTrackName(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int i = 0;
        List historyTemplates$default = TemplateApi.DefaultImpls.getHistoryTemplates$default(TemplateApi.Companion.getInstance(context), false, 1, null);
        StringBuilder sb = new StringBuilder();
        for (Object obj : historyTemplates$default) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TemplateHistoryConfig templateHistoryConfig = (TemplateHistoryConfig) obj;
            if (i == historyTemplates$default.size() - 1) {
                sb.append(templateHistoryConfig.getTemplateId());
            } else {
                sb.append(templateHistoryConfig.getTemplateId());
                sb.append(',');
            }
            i = i2;
        }
        return new Pair(Integer.valueOf(historyTemplates$default.size()), sb.toString());
    }

    public final String getIsChangeDoodleTextStyle(CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        if (!Intrinsics.areEqual((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId(), "doodle")) {
            return null;
        }
        int style = commonConfig.getLockscreenInfo().getClockInfo().getStyle();
        if (style == 1) {
            return "中文";
        }
        if (style != 2) {
            return null;
        }
        return "英文";
    }

    public final String getRhombusTemplateDifferentMinuteTrackName(String str, Boolean bool) {
        if (Intrinsics.areEqual(str, "rhombus")) {
            return Intrinsics.areEqual(bool, Boolean.TRUE) ? "是" : "否";
        }
        Log.w("Keyguard-Editor-TrackerUtil", "getRhombusTemplateDifferentMinuteTrackName: unsupported template");
        return null;
    }

    public final String getSmartFrameStyleTrackName(String str, SmartFrameInfo smartFrameInfo) {
        if (!Intrinsics.areEqual(str, "smart_frame")) {
            Log.w("Keyguard-Editor-TrackerUtil", "getSmartFrameStyleTrackName: unsupported template");
            return null;
        }
        if (smartFrameInfo != null) {
            return Integer.valueOf(smartFrameInfo.getShape()).toString();
        }
        return null;
    }

    public final String getTemplateDepthEffectTrackName(String str, Integer num) {
        if (Intrinsics.areEqual(str, "classic") || Intrinsics.areEqual(str, "classic_plus")) {
            return MagicType.INSTANCE.isDepth(num) ? "是" : "否";
        }
        Log.w("Keyguard-Editor-TrackerUtil", "getTemplateDepthEffectTrackName: unsupported template");
        return null;
    }

    public final String getTemplateEffectEnableDiffusionTrackName(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        Boolean boolValueOf = null;
        Boolean boolValueOf2 = (commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : Boolean.valueOf(wallpaperInfo3.getEnableBlur());
        if (commonConfig2 != null && (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
            boolValueOf = Boolean.valueOf(wallpaperInfo2.getEnableBlur());
        }
        if (Intrinsics.areEqual(boolValueOf2, boolValueOf)) {
            return "未编辑";
        }
        return (commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || !wallpaperInfo.getEnableBlur()) ? "关闭" : "开启";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x011c, code lost:
    
        if (r4.intValue() != 50004) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x011e, code lost:
    
        return "背景运动";
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0122, code lost:
    
        if (r4 != null) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x012b, code lost:
    
        if (r4.intValue() != 10000) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x012d, code lost:
    
        return "细条纹";
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0131, code lost:
    
        if (r4 != null) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x013a, code lost:
    
        if (r4.intValue() != 10001) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x013c, code lost:
    
        return "粗条纹";
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0140, code lost:
    
        if (r4 != null) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0149, code lost:
    
        if (r4.intValue() != 10002) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x014b, code lost:
    
        return "棱镜";
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x001e, code lost:
    
        if (r3.equals("eastern_b") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x014f, code lost:
    
        if (r4 != null) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0158, code lost:
    
        if (r4.intValue() != 10003) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x015a, code lost:
    
        return "细线";
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x015e, code lost:
    
        if (r4 != null) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0167, code lost:
    
        if (r4.intValue() != 10004) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0169, code lost:
    
        return "波纹";
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x016d, code lost:
    
        if (r4 != null) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0177, code lost:
    
        if (r4.intValue() != 70000) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0179, code lost:
    
        return "波浪";
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x017d, code lost:
    
        if (r4 != null) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0187, code lost:
    
        if (r4.intValue() != 70001) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0189, code lost:
    
        return "涟漪";
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x018d, code lost:
    
        if (r4 != null) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0197, code lost:
    
        if (r4.intValue() != 80000) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0199, code lost:
    
        return "结晶";
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        if (r3.equals("eastern_a") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x019d, code lost:
    
        if (r4 != null) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x01a7, code lost:
    
        if (r4.intValue() != 60000) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x01a9, code lost:
    
        return "透镜";
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x01ad, code lost:
    
        android.util.Log.w("Keyguard-Editor-TrackerUtil", "getTemplateEffectTrackName: invalid effect " + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x01c1, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x01c2, code lost:
    
        return "原图";
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:?, code lost:
    
        return "原图";
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0032, code lost:
    
        if (r3.equals("rhombus") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003c, code lost:
    
        if (r3.equals("classic") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0046, code lost:
    
        if (r3.equals("depth_pets") != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004f, code lost:
    
        if (r3.equals("classic_plus") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0059, code lost:
    
        if (r3.equals("classic_max") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0063, code lost:
    
        if (r3.equals("magazine_c") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006d, code lost:
    
        if (r3.equals("magazine_b") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0077, code lost:
    
        if (r3.equals("magazine_a") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0081, code lost:
    
        if (r3.equals("oversize_b") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x008b, code lost:
    
        if (r3.equals("oversize_a") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x008f, code lost:
    
        if (r4 != null) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0096, code lost:
    
        if (r4.intValue() == 0) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0098, code lost:
    
        if (r4 != null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a1, code lost:
    
        if (r4.intValue() != 20000) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00a5, code lost:
    
        if (r4 != null) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ae, code lost:
    
        if (r4.intValue() != 30000) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b0, code lost:
    
        return "均匀模糊";
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b4, code lost:
    
        if (r4 != null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00bd, code lost:
    
        if (r4.intValue() != 30001) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00bf, code lost:
    
        return "流体模糊";
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00c3, code lost:
    
        if (r4 != null) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00cc, code lost:
    
        if (r4.intValue() != 30002) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ce, code lost:
    
        return "渐变模糊";
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00d2, code lost:
    
        if (r4 != null) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00dc, code lost:
    
        if (r4.intValue() != 50000) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00de, code lost:
    
        return "聚焦";
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00e2, code lost:
    
        if (r4 != null) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00ec, code lost:
    
        if (r4.intValue() != 50001) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00ee, code lost:
    
        return "前景虚化";
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00f2, code lost:
    
        if (r4 != null) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00fc, code lost:
    
        if (r4.intValue() != 50002) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00fe, code lost:
    
        return "背景虚化";
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0014, code lost:
    
        if (r3.equals("eastern_c") == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0102, code lost:
    
        if (r4 != null) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x010c, code lost:
    
        if (r4.intValue() != 50003) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x010e, code lost:
    
        return "前景运动";
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0112, code lost:
    
        if (r4 != null) goto L99;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String getTemplateEffectTrackName(java.lang.String r3, java.lang.Integer r4) {
        /*
            Method dump skipped, instruction units count: 514
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.track.TrackerUtil.getTemplateEffectTrackName(java.lang.String, java.lang.Integer):java.lang.String");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final String getTemplateName(String str) {
        if (str != null) {
            switch (str.hashCode()) {
                case -1744945801:
                    if (str.equals("oversize_a")) {
                        return "超大时间A";
                    }
                    break;
                case -1744945800:
                    if (str.equals("oversize_b")) {
                        return "超大时间B";
                    }
                    break;
                case -1326135015:
                    if (str.equals("doodle")) {
                        return "涂鸦模版";
                    }
                    break;
                case -567074186:
                    if (str.equals("magazine_a")) {
                        return "影像杂志A锁屏模版";
                    }
                    break;
                case -567074185:
                    if (str.equals("magazine_b")) {
                        return "影像杂志B锁屏模版";
                    }
                    break;
                case -567074184:
                    if (str.equals("magazine_c")) {
                        return "影像杂志C锁屏模版";
                    }
                    break;
                case -159946889:
                    if (str.equals("smart_frame")) {
                        return "画框模版";
                    }
                    break;
                case -138709961:
                    if (str.equals("classic_max")) {
                        return "经典锁屏max模版";
                    }
                    break;
                case -4941529:
                    if (str.equals("classic_plus")) {
                        return "经典锁屏plus模版";
                    }
                    break;
                case 225289008:
                    if (str.equals("depth_pets")) {
                        return "宠物模版";
                    }
                    break;
                case 853620882:
                    if (str.equals("classic")) {
                        return "经典锁屏模版";
                    }
                    break;
                case 1179492204:
                    if (str.equals("rhombus")) {
                        return "菱形时间模版";
                    }
                    break;
                case 1250520234:
                    if (str.equals("all_in_one")) {
                        return "all_in_one";
                    }
                    break;
                case 1286956372:
                    if (str.equals("pad_exclusive_a")) {
                        return "平板专属A";
                    }
                    break;
                case 1286956373:
                    if (str.equals("pad_exclusive_b")) {
                        return "平板专属B";
                    }
                    break;
                case 1286956374:
                    if (str.equals("pad_exclusive_c")) {
                        return "平板专属C";
                    }
                    break;
                case 1786073894:
                    if (str.equals("eastern_a")) {
                        return "东方美学A";
                    }
                    break;
                case 1786073895:
                    if (str.equals("eastern_b")) {
                        return "东方美学B";
                    }
                    break;
                case 1786073896:
                    if (str.equals("eastern_c")) {
                        return "东方美学C";
                    }
                    break;
            }
        }
        Log.w("Keyguard-Editor-TrackerUtil", "getTemplateTrackName: invalid templateId " + str);
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003c, code lost:
    
        if (r4.equals("magazine_b") == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0045, code lost:
    
        if (r4.equals("magazine_a") == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0048, code lost:
    
        r4 = r5.getLockscreenInfo().getSignatureInfo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0050, code lost:
    
        if (r4 == null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0052, code lost:
    
        r3 = r4.getContent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0056, code lost:
    
        if (r3 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005c, code lost:
    
        if (r3.length() != 0) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005f, code lost:
    
        return "是";
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0060, code lost:
    
        return "否";
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String getTemplateSignatureTrackName(java.lang.String r4, com.miui.keyguard.editor.data.bean.CommonConfig r5) {
        /*
            r3 = this;
            java.lang.String r3 = "config"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r3)
            r3 = 0
            if (r4 == 0) goto L61
            int r0 = r4.hashCode()
            java.lang.String r1 = "是"
            java.lang.String r2 = "否"
            switch(r0) {
                case -567074186: goto L3f;
                case -567074185: goto L36;
                case 853620882: goto L16;
                default: goto L15;
            }
        L15:
            goto L61
        L16:
            java.lang.String r0 = "classic"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L1f
            goto L61
        L1f:
            com.miui.keyguard.editor.data.bean.TemplateConfig r3 = r5.getLockscreenInfo()
            com.miui.keyguard.editor.data.bean.ClockInfo r3 = r3.getClockInfo()
            java.lang.String r3 = r3.getClassicSignature()
            if (r3 == 0) goto L35
            int r3 = r3.length()
            if (r3 != 0) goto L34
            goto L35
        L34:
            return r1
        L35:
            return r2
        L36:
            java.lang.String r0 = "magazine_b"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L48
            goto L61
        L3f:
            java.lang.String r0 = "magazine_a"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L48
            goto L61
        L48:
            com.miui.keyguard.editor.data.bean.TemplateConfig r4 = r5.getLockscreenInfo()
            com.miui.keyguard.editor.data.bean.SignatureInfo r4 = r4.getSignatureInfo()
            if (r4 == 0) goto L56
            java.lang.String r3 = r4.getContent()
        L56:
            if (r3 == 0) goto L60
            int r3 = r3.length()
            if (r3 != 0) goto L5f
            goto L60
        L5f:
            return r1
        L60:
            return r2
        L61:
            java.lang.String r4 = "Keyguard-Editor-TrackerUtil"
            java.lang.String r5 = "getTemplateSignatureTrackName: unsupported template"
            android.util.Log.w(r4, r5)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.track.TrackerUtil.getTemplateSignatureTrackName(java.lang.String, com.miui.keyguard.editor.data.bean.CommonConfig):java.lang.String");
    }

    public final String getTemplateTrackName(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        return TemplateApi.Companion.getInstance(context).isThirdPartyTheme() ? "主题" : getTemplateName(str);
    }

    public final String getTextColorChanged(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo2;
        Integer numValueOf = null;
        Integer numValueOf2 = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo2.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo2.getPrimaryColor());
        if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (clockInfo = lockscreenInfo.getClockInfo()) != null) {
            numValueOf = Integer.valueOf(clockInfo.getPrimaryColor());
        }
        return !Intrinsics.areEqual(numValueOf2, numValueOf) ? "是" : "否";
    }

    public final String isChangedClockColor(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo3;
        Integer numValueOf = null;
        String templateId = (commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null || (clockInfo3 = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo3.getTemplateId();
        if (!StringsKt.equals$default(templateId, "magazine_a", false, 2, null) && !StringsKt.equals$default(templateId, "magazine_b", false, 2, null) && !StringsKt.equals$default(templateId, "magazine_c", false, 2, null)) {
            return null;
        }
        Integer numValueOf2 = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo2.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo2.getPrimaryColor());
        if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (clockInfo = lockscreenInfo.getClockInfo()) != null) {
            numValueOf = Integer.valueOf(clockInfo.getPrimaryColor());
        }
        return !Intrinsics.areEqual(numValueOf2, numValueOf) ? "是" : "否";
    }

    public final String isChangedSignatureColor(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        SignatureInfo signatureInfo;
        TemplateConfig lockscreenInfo2;
        SignatureInfo signatureInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo;
        Integer numValueOf = null;
        String templateId = (commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        if (!StringsKt.equals$default(templateId, "magazine_a", false, 2, null) && !StringsKt.equals$default(templateId, "magazine_b", false, 2, null)) {
            return null;
        }
        Integer numValueOf2 = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (signatureInfo2 = lockscreenInfo2.getSignatureInfo()) == null) ? null : Integer.valueOf(signatureInfo2.getPrimaryColor());
        if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (signatureInfo = lockscreenInfo.getSignatureInfo()) != null) {
            numValueOf = Integer.valueOf(signatureInfo.getPrimaryColor());
        }
        return !Intrinsics.areEqual(numValueOf2, numValueOf) ? "是" : "否";
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0050  */
    public final String isChangedSignatureContent(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        SignatureInfo signatureInfo;
        String content;
        TemplateConfig lockscreenInfo2;
        SignatureInfo signatureInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo4;
        ClockInfo clockInfo2;
        TemplateConfig lockscreenInfo5;
        ClockInfo clockInfo3;
        String content2 = null;
        String templateId = (commonConfig2 == null || (lockscreenInfo5 = commonConfig2.getLockscreenInfo()) == null || (clockInfo3 = lockscreenInfo5.getClockInfo()) == null) ? null : clockInfo3.getTemplateId();
        if ((templateId == null || !StringsKt.contains$default((CharSequence) templateId, (CharSequence) "classic", false, 2, (Object) null)) && !StringsKt.equals$default(templateId, "magazine_a", false, 2, null) && !StringsKt.equals$default(templateId, "magazine_b", false, 2, null)) {
            return null;
        }
        if (templateId == null || !StringsKt.contains$default((CharSequence) templateId, (CharSequence) "classic", false, 2, (Object) null)) {
            if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (signatureInfo = lockscreenInfo.getSignatureInfo()) == null) {
                content = null;
            } else {
                content = signatureInfo.getContent();
            }
        } else if (commonConfig == null || (lockscreenInfo4 = commonConfig.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo4.getClockInfo()) == null) {
            content = null;
        } else {
            content = clockInfo2.getClassicSignature();
        }
        if (content == null) {
            content = "";
        }
        if (templateId == null || !StringsKt.contains$default((CharSequence) templateId, (CharSequence) "classic", false, 2, (Object) null)) {
            if (commonConfig2 != null && (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) != null && (signatureInfo2 = lockscreenInfo2.getSignatureInfo()) != null) {
                content2 = signatureInfo2.getContent();
            }
        } else if (commonConfig2 != null && (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) != null && (clockInfo = lockscreenInfo3.getClockInfo()) != null) {
            content2 = clockInfo.getClassicSignature();
        }
        return Intrinsics.areEqual(content, content2 != null ? content2 : "") ? "否" : "是";
    }

    public final String isChangedSignatureStyle(CommonConfig commonConfig, CommonConfig commonConfig2) {
        TemplateConfig lockscreenInfo;
        SignatureInfo signatureInfo;
        TemplateConfig lockscreenInfo2;
        SignatureInfo signatureInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo;
        Integer numValueOf = null;
        if (!StringsKt.equals$default((commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo.getTemplateId(), "magazine_a", false, 2, null)) {
            return null;
        }
        Integer numValueOf2 = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (signatureInfo2 = lockscreenInfo2.getSignatureInfo()) == null) ? null : Integer.valueOf(signatureInfo2.getAlignment());
        if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (signatureInfo = lockscreenInfo.getSignatureInfo()) != null) {
            numValueOf = Integer.valueOf(signatureInfo.getAlignment());
        }
        return !Intrinsics.areEqual(numValueOf2, numValueOf) ? "是" : "否";
    }

    public final Map trackCommonParams(Context context, CommonConfig config, String trackId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String templateTrackName = getTemplateTrackName(context, config.getLockscreenInfo().getClockInfo().getTemplateId());
        if (templateTrackName != null) {
            linkedHashMap.put("used_template_style", templateTrackName);
        }
        linkedHashMap.put("track_id", trackId);
        return linkedHashMap;
    }
}

package com.miui.keyguard.editor.track;

import android.content.Context;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.thememanager.util.AodTrackMapData;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.homepage.MainFragment;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.task.Task;
import com.xiaomi.onetrack.api.bf;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
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
/* JADX INFO: compiled from: TrackHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TrackHelper {
    public static final TrackHelper INSTANCE = new TrackHelper();
    public static final List HOMEPAGE_FLOOR_TRACK_NAME = CollectionsKt.listOf((Object[]) new String[]{"if_zero_floor_exposed", "if_first_floor_exposed", "if_second_floor_exposed", "if_third_floor_exposed", "if_fourth_floor_exposed", "if_fifth_floor_exposed", "if_sixth_floor_exposed", "if_seventh_floor_exposed", "if_eighth_floor_exposed", "if_ninth_floor_exposed", "if_tenth_floor_exposed"});

    public static final void trackApplyWallpaper$lambda$37(AodTrackMapData aodTrackMapData, String str, String str2, int i, String str3, String str4, Context context) {
        try {
            Map<String, Object> trackMap = aodTrackMapData.getTrackMap();
            String string = trackMap.getOrDefault("trackId", "").toString();
            Map toMap = INSTANCE.parseToMap(string);
            toMap.put("cardId", trackMap.getOrDefault("cardId", ""));
            toMap.put("cardIndx", trackMap.getOrDefault("cardIndx", ""));
            toMap.put("trackId", string);
            toMap.put("type1", str);
            toMap.put("pageid", trackMap.getOrDefault("pageId", ""));
            toMap.put("entryType", TrackValues.INSTANCE.getENTER_EDITOR_MAP().getOrDefault(str2, ""));
            toMap.put("xRef", trackMap.getOrDefault("xRef", ""));
            toMap.put("xPrevRef", trackMap.getOrDefault("xPrevRef", ""));
            toMap.put("extra", trackMap.getOrDefault("extra", ""));
            toMap.put("contentIndex", trackMap.getOrDefault("contentIndex", ""));
            toMap.put("content", trackMap.getOrDefault("content", ""));
            toMap.put("productId", trackMap.getOrDefault("productId", ""));
            toMap.put(bf.a, trackMap.getOrDefault(bf.a, ""));
            toMap.put("source", trackMap.getOrDefault("source", ""));
            if (i != -1) {
                toMap.put("videoType", Integer.valueOf(i));
            }
            toMap.put("resourceType", str3);
            if (!TextUtils.isEmpty(str4)) {
                toMap.put(BaseStyleSelectPresenter.KEY_CATE, str4);
            }
            EditorTracker companion = EditorTracker.Companion.getInstance(context);
            if (companion != null) {
                companion.track("APPLY", "348.0.0.0.43922", toMap);
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TrackHelper", "trackApplyWallpaper error: " + e.getMessage());
        }
    }

    public static /* synthetic */ void trackApplyWallpaperOther$default(TrackHelper trackHelper, Context context, String str, String str2, String str3, int i, String str4, int i2, Object obj) {
        if ((i2 & 32) != 0) {
            str4 = "";
        }
        trackHelper.trackApplyWallpaperOther(context, str, str2, str3, i, str4);
    }

    public static final void trackApplyWallpaperOther$lambda$39(String str, String str2, int i, String str3, String str4, Context context) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("type1", str);
            Object obj = TrackValues.INSTANCE.getENTER_EDITOR_MAP().get(str2);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
            linkedHashMap.put("entryType", (String) obj);
            if (i != -1) {
                linkedHashMap.put("videoType", Integer.valueOf(i));
            }
            linkedHashMap.put("resourceType", str3);
            if (!TextUtils.isEmpty(str4)) {
                linkedHashMap.put(BaseStyleSelectPresenter.KEY_CATE, str4);
            }
            if (context != null) {
                EditorTracker.Companion companion = EditorTracker.Companion;
                Context applicationContext = context.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                EditorTracker companion2 = companion.getInstance(applicationContext);
                if (companion2 != null) {
                    companion2.track("APPLY", "348.0.0.0.43922", linkedHashMap);
                }
            }
        } catch (Exception e) {
            Log.e("TrackHelper", "trackApplyWallpaper error: " + e.getMessage());
        }
    }

    public static /* synthetic */ void trackClickEditorItem$default(TrackHelper trackHelper, Context context, int i, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 32) != 0) {
            str4 = EditorActivity.Companion.getTackId();
        }
        trackHelper.trackClickEditorItem(context, i, str, str2, str3, str4);
    }

    public static final void trackClickEditorItem$lambda$15(int i, String str, String str2, Context context, String str3, String str4) {
        String templateTrackName;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("editor_type", i == 0 ? "锁屏编辑器" : "桌面编辑器");
        linkedHashMap.put("button_name", str);
        if (!TextUtils.isEmpty(str2)) {
            linkedHashMap.put("click_status", str2);
        }
        if (i == 0 && (templateTrackName = TrackerUtil.INSTANCE.getTemplateTrackName(context, str3)) != null) {
            linkedHashMap.put("used_template_style", templateTrackName);
        }
        linkedHashMap.put("track_id", str4);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("click", "348.6.1.1.42833", linkedHashMap);
        }
        Log.i("Keyguard-Editor-TrackHelper", "trackCommonEditorClick: " + linkedHashMap);
    }

    public static final void trackCommonEditorOpen$lambda$13(String str, int i, String str2, Context context) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        TrackValues trackValues = TrackValues.INSTANCE;
        Object obj = trackValues.getENTER_EDITOR_MAP().get(str);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
        String str3 = (String) obj;
        if (TextUtils.isEmpty(str3)) {
            Log.i("Keyguard-Editor-TrackHelper", "trackCommonEditorOpen: enterWay=" + str3 + " is not match");
            return;
        }
        if (trackValues.getENTER_EDITOR_MAP().containsKey(str)) {
            linkedHashMap.put("enter_way", str3);
        }
        linkedHashMap.put("editor_type", i == 0 ? "锁屏编辑器" : "桌面编辑器");
        linkedHashMap.put("track_id", str2);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("enter", "348.6.1.1.42831", linkedHashMap);
        }
        Log.i("Keyguard-Editor-TrackHelper", "trackCommonEditorOpen: " + linkedHashMap);
    }

    public static final void trackDeleteHistoryTemplate$lambda$19(TemplateItemBean templateItemBean, int i, Context context) {
        TemplateHistoryConfig history;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String deleteTemplateTrackName = TrackerUtil.INSTANCE.getDeleteTemplateTrackName((templateItemBean == null || (history = templateItemBean.getHistory()) == null) ? null : history.getTemplateId());
        if (deleteTemplateTrackName != null) {
            linkedHashMap.put("delete_template_style", deleteTemplateTrackName);
        }
        linkedHashMap.put("after_num", Integer.valueOf(i));
        linkedHashMap.put("track_id", EditorActivity.Companion.getTackId());
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("delete", "348.6.2.1.43965", linkedHashMap);
        }
        Log.i("Keyguard-Editor-TrackHelper", "trackDeleteHistoryTemplate: " + linkedHashMap);
    }

    public static final void trackDownloadDepthVideo$lambda$36(Context context, CommonConfig commonConfig, String str) {
        Map mapTrackCommonParams = TrackerUtil.INSTANCE.trackCommonParams(context, commonConfig, str);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("click", "348.1.2.1.39614", mapTrackCommonParams);
        }
    }

    public static final void trackEditorEnterPreview$lambda$17(int i, Context context, String str, String str2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("editor_type", i == 0 ? "锁屏编辑器" : "桌面编辑器");
        String templateTrackName = TrackerUtil.INSTANCE.getTemplateTrackName(context, str);
        if (templateTrackName != null) {
            linkedHashMap.put("used_template_style", templateTrackName);
        }
        linkedHashMap.put("track_id", str2);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("enter", "348.6.1.1.42834", linkedHashMap);
        }
        Log.i("Keyguard-Editor-TrackHelper", "trackEditorEnterPreview: " + linkedHashMap);
    }

    public static final void trackOpenHomePage$lambda$12(Context context, boolean z, String str) {
        boolean zIsGalleryInstalledAndOpen = FashionGalleryHelper.INSTANCE.isGalleryInstalledAndOpen(context);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("enter_way", z ? "设置_壁纸与个性化" : "锁屏");
        linkedHashMap.put("if_theme_template", TemplateApi.Companion.getInstance(context).isThirdPartyTheme() ? "是" : "否");
        linkedHashMap.put("if_pictorial_carousel", zIsGalleryInstalledAndOpen ? "是" : "否");
        linkedHashMap.put("track_id", str);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("enter", "348.6.2.1.43962", linkedHashMap);
        }
        Log.i("Keyguard-Editor-TrackHelper", "进入首页埋点: " + linkedHashMap);
    }

    public static final void trackProductDepthVideo$lambda$35(Context context, CommonConfig commonConfig, String str, String str2, long j) {
        Map mapTrackCommonParams = TrackerUtil.INSTANCE.trackCommonParams(context, commonConfig, str);
        mapTrackCommonParams.put("proceed_result", str2);
        mapTrackCommonParams.put("proceed_duration", Long.valueOf(j));
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("enter", "348.1.2.1.39613", mapTrackCommonParams);
        }
    }

    /* JADX WARN: Code duplicated, block: B:296:0x047e  */
    public static final void trackQuitEditMode$lambda$34(String str, CommonConfig commonConfig, CommonConfig commonConfig2, Context context, int i, Boolean bool, boolean z, boolean z2, String str2, int i2, int i3, String str3) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfo2;
        HomeConfig homeInfo3;
        String templateTrackName;
        Object obj6;
        Object obj7;
        String str4;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo3;
        TemplateConfig lockscreenInfo4;
        ClockInfo clockInfo4;
        Object obj8;
        TemplateConfig lockscreenInfo5;
        ClockInfo clockInfo5;
        TemplateConfig lockscreenInfo6;
        ClockInfo clockInfo6;
        TemplateConfig lockscreenInfo7;
        ClockInfo clockInfo7;
        TemplateConfig lockscreenInfo8;
        ClockInfo clockInfo8;
        TemplateConfig lockscreenInfo9;
        ClockInfo clockInfo9;
        TemplateConfig lockscreenInfo10;
        ClockInfo clockInfo10;
        TemplateConfig lockscreenInfo11;
        ClockInfo clockInfo11;
        TemplateConfig lockscreenInfo12;
        ClockInfo clockInfo12;
        TemplateConfig lockscreenInfo13;
        ClockInfo clockInfo13;
        TemplateConfig lockscreenInfo14;
        TemplateConfig lockscreenInfo15;
        TemplateConfig lockscreenInfo16;
        TemplateConfig lockscreenInfo17;
        TemplateConfig lockscreenInfo18;
        TemplateConfig lockscreenInfo19;
        ClockInfo clockInfo14;
        TemplateConfig lockscreenInfo20;
        TemplateConfig lockscreenInfo21;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo22;
        WallpaperInfo wallpaperInfo4;
        String str5;
        TemplateConfig lockscreenInfo23;
        WallpaperInfo wallpaperInfo5;
        HomeConfig homeInfo4;
        WallpaperInfo wallpaperInfo6;
        TemplateConfig lockscreenInfo24;
        ClockInfo clockInfo15;
        TemplateConfig lockscreenInfo25;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("quit_way", str);
        Integer numValueOf = null;
        if (Intrinsics.areEqual("应用", str)) {
            ClockInfo clockInfo16 = (commonConfig == null || (lockscreenInfo25 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo25.getClockInfo();
            String templateId = clockInfo16 != null ? clockInfo16.getTemplateId() : null;
            obj = "关";
            obj2 = "开";
            if (StringsKt.equals$default(templateId, (commonConfig2 == null || (lockscreenInfo24 = commonConfig2.getLockscreenInfo()) == null || (clockInfo15 = lockscreenInfo24.getClockInfo()) == null) ? null : clockInfo15.getTemplateId(), false, 2, null) || (templateTrackName = TrackerUtil.INSTANCE.getTemplateTrackName(context, templateId)) == null) {
                templateTrackName = "未调整";
            }
            linkedHashMap.put("template_style", templateTrackName);
            String resourceType = (commonConfig == null || (homeInfo4 = commonConfig.getHomeInfo()) == null || (wallpaperInfo6 = homeInfo4.getWallpaperInfo()) == null) ? null : wallpaperInfo6.getResourceType();
            if (Intrinsics.areEqual(resourceType, "image")) {
                obj6 = "静态";
            } else {
                obj6 = Intrinsics.areEqual(resourceType, "video") ? "动态" : "未知";
            }
            linkedHashMap.put("set_desktop_wallpaper_type", obj6);
            String resourceType2 = (commonConfig == null || (lockscreenInfo23 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo5 = lockscreenInfo23.getWallpaperInfo()) == null) ? null : wallpaperInfo5.getResourceType();
            if (Intrinsics.areEqual(resourceType2, "image")) {
                obj7 = "静态";
            } else {
                obj7 = Intrinsics.areEqual(resourceType2, "video") ? "动态" : "未知";
            }
            linkedHashMap.put("set_keyguard_wallpaper_type", obj7);
            if (i == 0) {
                str4 = "未拖动";
            } else if (i == 1) {
                str4 = "右下角把手";
            } else if (i != 2) {
                str4 = i != 3 ? "其他" : "长按";
            } else {
                str4 = "时钟边框下方把手";
            }
            linkedHashMap.put("set_way", str4);
            if (clockInfo16 != 0) {
                int clockDepthType = clockInfo16.getClockDepthType();
                if (clockDepthType == 0) {
                    str5 = "全景深";
                } else if (clockDepthType != 1) {
                    str5 = clockDepthType != 2 ? "未编辑" : "分前时后";
                } else {
                    str5 = "时前分后";
                }
                linkedHashMap.put("dof_type", str5);
                linkedHashMap.put("set_time_layout", clockInfo16.isDoubleRow() ? "两行" : "一行");
                linkedHashMap.put("set_width", Float.valueOf(clockInfo16.getTimeWidth()));
                linkedHashMap.put("set_height", Float.valueOf(clockInfo16.getTimeHeight()));
                TrackerUtil trackerUtil = TrackerUtil.INSTANCE;
                linkedHashMap.put("set_font", trackerUtil.getAllInOneTemplateClockFontStyle(Integer.valueOf(clockInfo16.getFontStyle())));
                linkedHashMap.put("set_hollow_effect", trackerUtil.getAllInOneTemplateHollowStyle(clockInfo16.getHollowStyle()));
                linkedHashMap.put("set_hcolor", trackerUtil.getColorPanelLabel(clockInfo16.getPrimaryFontColorPanelId()));
                linkedHashMap.put("set_mcolor", trackerUtil.getColorPanelLabel(clockInfo16.getSecondaryFontColorPanelId()));
                Unit unit = Unit.INSTANCE;
            }
            linkedHashMap.put("if_edit", (bool == null || !bool.booleanValue()) ? "否" : "是");
            linkedHashMap.put("if_cut", z ? "是" : "否");
            if (z2) {
                linkedHashMap.put("wallpaper_changed", "是");
            } else {
                linkedHashMap.put("wallpaper_changed", "否");
            }
            TrackerUtil trackerUtil2 = TrackerUtil.INSTANCE;
            String templateEffectTrackName = trackerUtil2.getTemplateEffectTrackName(templateId, (commonConfig == null || (lockscreenInfo22 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo22.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo4.getMagicType()));
            String templateEffectTrackName2 = trackerUtil2.getTemplateEffectTrackName(templateId, (commonConfig2 == null || (lockscreenInfo21 = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo21.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo3.getMagicType()));
            if (templateEffectTrackName == null) {
                templateEffectTrackName = "未编辑";
            }
            if (Intrinsics.areEqual(templateEffectTrackName, templateEffectTrackName2)) {
                templateEffectTrackName = null;
            }
            if (templateEffectTrackName == null) {
                templateEffectTrackName = "未编辑";
            }
            linkedHashMap.put("set_texture_effect", templateEffectTrackName);
            linkedHashMap.put("if_set_scrub_effect", trackerUtil2.getTemplateEffectEnableDiffusionTrackName(commonConfig2, commonConfig));
            String dofState = trackerUtil2.getDofState(commonConfig2, commonConfig);
            if (dofState != null) {
                linkedHashMap.put("set_dof_status", dofState);
                Unit unit2 = Unit.INSTANCE;
            }
            String fontEffects = trackerUtil2.getFontEffects(commonConfig);
            String fontEffects2 = trackerUtil2.getFontEffects(commonConfig2);
            if (fontEffects == null) {
                fontEffects = "未编辑";
            }
            if (Intrinsics.areEqual(fontEffects, fontEffects2)) {
                fontEffects = null;
            }
            if (fontEffects == null) {
                fontEffects = "未编辑";
            }
            linkedHashMap.put("set_template_time_effect", fontEffects);
            String classicTemplateFontTrackName = trackerUtil2.getClassicTemplateFontTrackName(templateId, (commonConfig == null || (lockscreenInfo20 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo20.getClockInfo());
            obj3 = "未调整";
            if (StringsKt.equals$default(classicTemplateFontTrackName, trackerUtil2.getClassicTemplateFontTrackName((commonConfig2 == null || (lockscreenInfo19 = commonConfig2.getLockscreenInfo()) == null || (clockInfo14 = lockscreenInfo19.getClockInfo()) == null) ? null : clockInfo14.getTemplateId(), (commonConfig2 == null || (lockscreenInfo18 = commonConfig2.getLockscreenInfo()) == null) ? null : lockscreenInfo18.getClockInfo()), false, 2, null)) {
                linkedHashMap.put("set_global_font", "未编辑");
            } else if (classicTemplateFontTrackName != null) {
                linkedHashMap.put("set_global_font", classicTemplateFontTrackName);
            }
            if (StringsKt.equals$default(templateId, "classic", false, 2, null) || StringsKt.equals$default(templateId, "classic_plus", false, 2, null)) {
                if (Intrinsics.areEqual((commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo2.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo2.getPrimaryColor()), (commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo.getPrimaryColor()))) {
                    linkedHashMap.put("if_set_global_color", "否");
                } else {
                    linkedHashMap.put("if_set_global_color", "是");
                }
            }
            String classicTemplateTextAreaTrackName = trackerUtil2.getClassicTemplateTextAreaTrackName(templateId, (commonConfig == null || (lockscreenInfo17 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo17.getClockInfo());
            if (StringsKt.equals$default(classicTemplateTextAreaTrackName, trackerUtil2.getClassicTemplateTextAreaTrackName(templateId, (commonConfig2 == null || (lockscreenInfo16 = commonConfig2.getLockscreenInfo()) == null) ? null : lockscreenInfo16.getClockInfo()), false, 2, null)) {
                linkedHashMap.put("set_fine_print_content", "未编辑");
            } else if (classicTemplateTextAreaTrackName != null) {
                linkedHashMap.put("set_fine_print_content", classicTemplateTextAreaTrackName);
            }
            String classicTemplateClockAreaTrackName = trackerUtil2.getClassicTemplateClockAreaTrackName(templateId, (commonConfig == null || (lockscreenInfo15 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo15.getClockInfo());
            if (StringsKt.equals$default(classicTemplateClockAreaTrackName, trackerUtil2.getClassicTemplateClockAreaTrackName(templateId, (commonConfig2 == null || (lockscreenInfo14 = commonConfig2.getLockscreenInfo()) == null) ? null : lockscreenInfo14.getClockInfo()), false, 2, null)) {
                linkedHashMap.put("set_time_style", "未编辑");
            } else if (classicTemplateClockAreaTrackName != null) {
                linkedHashMap.put("set_time_style", classicTemplateClockAreaTrackName);
            }
            String classicTemplateContentAreaTrackName = trackerUtil2.getClassicTemplateContentAreaTrackName(templateId, (commonConfig == null || (lockscreenInfo13 = commonConfig.getLockscreenInfo()) == null || (clockInfo13 = lockscreenInfo13.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo13.getClassicLine3()));
            if (Intrinsics.areEqual(classicTemplateContentAreaTrackName, trackerUtil2.getClassicTemplateContentAreaTrackName((commonConfig2 == null || (lockscreenInfo12 = commonConfig2.getLockscreenInfo()) == null || (clockInfo12 = lockscreenInfo12.getClockInfo()) == null) ? null : clockInfo12.getTemplateId(), (commonConfig2 == null || (lockscreenInfo11 = commonConfig2.getLockscreenInfo()) == null || (clockInfo11 = lockscreenInfo11.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo11.getClassicLine3())))) {
                linkedHashMap.put("set_content_area_first_line", "未编辑");
            } else if (classicTemplateContentAreaTrackName != null) {
                linkedHashMap.put("set_content_area_first_line", classicTemplateContentAreaTrackName);
            }
            String classicTemplateContentAreaTrackName2 = trackerUtil2.getClassicTemplateContentAreaTrackName((commonConfig2 == null || (lockscreenInfo10 = commonConfig2.getLockscreenInfo()) == null || (clockInfo10 = lockscreenInfo10.getClockInfo()) == null) ? null : clockInfo10.getTemplateId(), (commonConfig2 == null || (lockscreenInfo9 = commonConfig2.getLockscreenInfo()) == null || (clockInfo9 = lockscreenInfo9.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo9.getClassicLine4()));
            String classicTemplateContentAreaTrackName3 = trackerUtil2.getClassicTemplateContentAreaTrackName(templateId, (commonConfig == null || (lockscreenInfo8 = commonConfig.getLockscreenInfo()) == null || (clockInfo8 = lockscreenInfo8.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo8.getClassicLine4()));
            if (Intrinsics.areEqual(classicTemplateContentAreaTrackName2, classicTemplateContentAreaTrackName3)) {
                linkedHashMap.put("set_content_area_second_line", "未编辑");
            } else if (classicTemplateContentAreaTrackName3 != null) {
                linkedHashMap.put("set_content_area_second_line", classicTemplateContentAreaTrackName3);
            }
            String strIsChangedSignatureContent = trackerUtil2.isChangedSignatureContent(commonConfig2, commonConfig);
            if (strIsChangedSignatureContent != null) {
                linkedHashMap.put("if_set_signature", strIsChangedSignatureContent);
                Unit unit3 = Unit.INSTANCE;
            }
            if (StringsKt.equals$default((commonConfig == null || (lockscreenInfo7 = commonConfig.getLockscreenInfo()) == null || (clockInfo7 = lockscreenInfo7.getClockInfo()) == null) ? null : clockInfo7.getTemplateId(), "rhombus", false, 2, null)) {
                if (commonConfig == null || (lockscreenInfo5 = commonConfig.getLockscreenInfo()) == null || (clockInfo5 = lockscreenInfo5.getClockInfo()) == null) {
                    obj8 = "是";
                } else {
                    if (Integer.valueOf(clockInfo5.getSecondaryColor()).equals((commonConfig2 == null || (lockscreenInfo6 = commonConfig2.getLockscreenInfo()) == null || (clockInfo6 = lockscreenInfo6.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo6.getSecondaryColor()))) {
                        obj8 = "否";
                    } else {
                        obj8 = "是";
                    }
                }
                linkedHashMap.put("if_set_colon_color", obj8);
            }
            if (StringsKt.equals$default(templateId, "rhombus", false, 2, null)) {
                String rhombusTemplateDifferentMinuteTrackName = trackerUtil2.getRhombusTemplateDifferentMinuteTrackName(templateId, (commonConfig == null || (lockscreenInfo4 = commonConfig.getLockscreenInfo()) == null || (clockInfo4 = lockscreenInfo4.getClockInfo()) == null) ? null : Boolean.valueOf(clockInfo4.isDiffHourMinuteColor()));
                numValueOf = null;
                if (StringsKt.equals$default(rhombusTemplateDifferentMinuteTrackName, trackerUtil2.getRhombusTemplateDifferentMinuteTrackName(templateId, (commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null || (clockInfo3 = lockscreenInfo3.getClockInfo()) == null) ? null : Boolean.valueOf(clockInfo3.isDiffHourMinuteColor())), false, 2, null)) {
                    linkedHashMap.put("set_digital_color_different", "未编辑");
                } else if (rhombusTemplateDifferentMinuteTrackName != null) {
                    linkedHashMap.put("set_digital_color_different", rhombusTemplateDifferentMinuteTrackName);
                }
            } else {
                numValueOf = null;
            }
            String strIsChangedSignatureStyle = trackerUtil2.isChangedSignatureStyle(commonConfig2, commonConfig);
            if (strIsChangedSignatureStyle != null) {
                linkedHashMap.put("if_set_signature_typography", strIsChangedSignatureStyle);
                Unit unit4 = Unit.INSTANCE;
            }
            String strIsChangedClockColor = trackerUtil2.isChangedClockColor(commonConfig2, commonConfig);
            if (strIsChangedClockColor != null) {
                linkedHashMap.put("if_set_clock_color", strIsChangedClockColor);
                Unit unit5 = Unit.INSTANCE;
            }
            String strIsChangedSignatureColor = trackerUtil2.isChangedSignatureColor(commonConfig2, commonConfig);
            if (strIsChangedSignatureColor != null) {
                linkedHashMap.put("if_set_signature_color", strIsChangedSignatureColor);
                Unit unit6 = Unit.INSTANCE;
            }
            String textColorChanged = trackerUtil2.getTextColorChanged(commonConfig2, commonConfig);
            if (textColorChanged != null) {
                linkedHashMap.put("if_set_text_color", textColorChanged);
                Unit unit7 = Unit.INSTANCE;
            }
            String backColor = trackerUtil2.getBackColor(commonConfig2, commonConfig);
            if (backColor != null) {
                linkedHashMap.put("if_set_background_color", backColor);
                Unit unit8 = Unit.INSTANCE;
            }
            if (!TextUtils.isEmpty(str2)) {
                linkedHashMap.put("video_status", str2);
            }
        } else {
            obj = "关";
            obj2 = "开";
            obj3 = "未调整";
        }
        if ((commonConfig2 == null || (homeInfo3 = commonConfig2.getHomeInfo()) == null || homeInfo3.getFollowStatus() != i2) && i2 != -1) {
            obj4 = (i2 == 0 || i2 == 3) ? obj : obj2;
        } else {
            obj4 = obj3;
        }
        linkedHashMap.put("follow_keyguard_status", obj4);
        Integer numValueOf2 = (commonConfig == null || (homeInfo2 = commonConfig.getHomeInfo()) == null || (wallpaperInfo2 = homeInfo2.getWallpaperInfo()) == null) ? numValueOf : Integer.valueOf(wallpaperInfo2.getMagicType());
        if (commonConfig2 != null && (homeInfo = commonConfig2.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
            numValueOf = Integer.valueOf(wallpaperInfo.getMagicType());
        }
        if (Intrinsics.areEqual(numValueOf2, numValueOf)) {
            obj5 = obj3;
        } else {
            obj5 = i3 == 1 ? obj2 : obj;
        }
        linkedHashMap.put("desktop_blur_status", obj5);
        if (commonConfig != null && commonConfig2 != null) {
            if (Intrinsics.areEqual(commonConfig.getLockscreenInfo().getTemplateName(), commonConfig2.getLockscreenInfo().getTemplateName())) {
                linkedHashMap.put("template_changed", "否");
            } else {
                linkedHashMap.put("template_changed", "是");
            }
        }
        String isChangeDoodleTextStyle = TrackerUtil.INSTANCE.getIsChangeDoodleTextStyle(commonConfig);
        if (isChangeDoodleTextStyle != null) {
            linkedHashMap.put("if_set_text_style", isChangeDoodleTextStyle);
            Unit unit9 = Unit.INSTANCE;
        }
        linkedHashMap.put("track_id", str3);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("quit", "348.6.1.1.42844", linkedHashMap);
            Unit unit10 = Unit.INSTANCE;
        }
        Log.i("Keyguard-Editor-TrackHelper", "trackQuitEdit: " + linkedHashMap);
    }

    public static /* synthetic */ void trackQuitHomePage$default(TrackHelper trackHelper, Context context, String str, CommonConfig commonConfig, CommonConfig commonConfig2, int[] iArr, boolean z, boolean z2, int i, Object obj) {
        if ((i & 32) != 0) {
            z = false;
        }
        if ((i & 64) != 0) {
            z2 = false;
        }
        trackHelper.trackQuitHomePage(context, str, commonConfig, commonConfig2, iArr, z, z2);
    }

    public static final void trackQuitHomePage$lambda$22(String str, CommonConfig commonConfig, CommonConfig commonConfig2, Context context, boolean z, boolean z2, int[] iArr) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("quit_way", str);
        if (Intrinsics.areEqual("应用", str)) {
            if (commonConfig != null && commonConfig2 != null) {
                if (Intrinsics.areEqual(commonConfig.getLockscreenInfo().getTemplateName(), commonConfig2.getLockscreenInfo().getTemplateName())) {
                    linkedHashMap.put("template_changed", "否");
                } else {
                    linkedHashMap.put("template_changed", "是");
                }
            }
            String templateTrackName = TrackerUtil.INSTANCE.getTemplateTrackName(context, (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId());
            if (templateTrackName != null) {
                linkedHashMap.put("new_template_style", templateTrackName);
            }
            linkedHashMap.put("is_carousel_match", z ? "是" : "否");
        }
        linkedHashMap.put("if_enter_editor", z2 ? "是" : "否");
        if (iArr != null) {
            int length = iArr.length;
            for (int i = 0; i < length; i++) {
                if (i != 0) {
                    linkedHashMap.put((String) HOMEPAGE_FLOOR_TRACK_NAME.get(i), iArr[i] == 1 ? "是" : "否");
                }
            }
        }
        linkedHashMap.put("track_id", EditorActivity.Companion.getTackId());
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("quit", "348.6.2.1.43966", linkedHashMap);
        }
        MainFragment.Companion.setEnterEditor(false);
        Log.i("Keyguard-Editor-TrackHelper", "trackQuitHomePage: " + linkedHashMap);
    }

    public final void negativeCardClick(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("position", "十字楼");
        linkedHashMap.put("track_id", EditorActivity.Companion.getTackId());
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("click", "348.6.2.1.43964", linkedHashMap);
        }
        Log.i("Keyguard-Editor-TrackHelper", "negativeCardExposure: " + linkedHashMap);
    }

    public final void negativeCardExposure(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("position", "十字楼");
        linkedHashMap.put("track_id", EditorActivity.Companion.getTackId());
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("expose", "348.6.2.1.43963", linkedHashMap);
        }
        Log.i("Keyguard-Editor-TrackHelper", "negativeCardExposure: " + linkedHashMap);
    }

    public final Map parseToMap(String str) {
        if (TextUtils.isEmpty(str)) {
            return new LinkedHashMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Intrinsics.checkNotNull(str);
        String[] strArr = (String[]) new Regex("_").split(str, 0).toArray(new String[0]);
        int length = strArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            switch (i) {
                case 0:
                    linkedHashMap.put("cardId", strArr[0]);
                    break;
                case 1:
                    linkedHashMap.put("cardIndx", strArr[1]);
                    break;
                case 2:
                    linkedHashMap.put("content", strArr[2]);
                    break;
                case 3:
                    linkedHashMap.put("contentIndex", strArr[3]);
                    break;
                case 4:
                    if (((String[]) new Regex(":").split(strArr[4], 0).toArray(new String[0])).length < 2) {
                        z = true;
                    }
                    Unit unit = Unit.INSTANCE;
                    break;
                case 5:
                    if (((String[]) new Regex(":").split(strArr[5], 0).toArray(new String[0])).length < 2) {
                        z = true;
                    }
                    Unit unit2 = Unit.INSTANCE;
                    break;
                case 6:
                    String[] strArr2 = (String[]) new Regex(":").split(strArr[6], 0).toArray(new String[0]);
                    if (strArr2.length < 2) {
                        z = true;
                    } else {
                        linkedHashMap.put("resourceType", strArr2[1]);
                    }
                    Unit unit3 = Unit.INSTANCE;
                    break;
                case 7:
                    linkedHashMap.put("secIndex", strArr[7]);
                    break;
                case 8:
                    linkedHashMap.put("secContent", strArr[8]);
                    break;
                case 9:
                case 10:
                default:
                    Unit unit4 = Unit.INSTANCE;
                    break;
            }
            if (z) {
                return linkedHashMap;
            }
        }
        return linkedHashMap;
    }

    public final void trackApplyWallpaper(final Context context, final String type, final AodTrackMapData parcelableMap, final String source, final String resourceType, final int i, final String category) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(parcelableMap, "parcelableMap");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(resourceType, "resourceType");
        Intrinsics.checkNotNullParameter(category, "category");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackApplyWallpaper$lambda$37(parcelableMap, type, source, i, resourceType, category, context);
            }
        });
    }

    public final void trackApplyWallpaperOther(final Context context, final String type, final String source, final String resourceType, final int i, final String category) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(resourceType, "resourceType");
        Intrinsics.checkNotNullParameter(category, "category");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackApplyWallpaperOther$lambda$39(type, source, i, resourceType, category, context);
            }
        });
    }

    public final void trackClickEditorItem(final Context context, final int i, final String buttonName, final String clickStatus, final String str, final String trackId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(buttonName, "buttonName");
        Intrinsics.checkNotNullParameter(clickStatus, "clickStatus");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackClickEditorItem$lambda$15(i, buttonName, clickStatus, context, str, trackId);
            }
        });
    }

    public final void trackCommonEditorOpen(final Context context, final String caller, final int i, final String trackId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(caller, "caller");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackCommonEditorOpen$lambda$13(caller, i, trackId, context);
            }
        });
    }

    public final void trackCurrentTemplate(Context context, CommonConfig config) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ClockInfo clockInfo = config.getLockscreenInfo().getClockInfo();
        String templateId = clockInfo != null ? clockInfo.getTemplateId() : null;
        WallpaperInfo wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo();
        Integer numValueOf = wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getMagicType()) : null;
        TrackerUtil trackerUtil = TrackerUtil.INSTANCE;
        String templateTrackName = trackerUtil.getTemplateTrackName(context, templateId);
        if (templateTrackName != null) {
            linkedHashMap.put("used_template_style", templateTrackName);
        }
        String templateEffectTrackName = trackerUtil.getTemplateEffectTrackName(templateId, numValueOf);
        if (templateEffectTrackName != null) {
            linkedHashMap.put("used_template_texture_effect", templateEffectTrackName);
        }
        linkedHashMap.put("used_template_if_only_time_effective", trackerUtil.getTemplateEffectEnableDiffusionTrackName(null, config));
        String templateDepthEffectTrackName = trackerUtil.getTemplateDepthEffectTrackName(templateId, numValueOf);
        if (templateDepthEffectTrackName != null) {
            linkedHashMap.put("used_template_if_dof", templateDepthEffectTrackName);
        }
        String classicTemplateTextAreaTrackName = trackerUtil.getClassicTemplateTextAreaTrackName(templateId, config.getLockscreenInfo().getClockInfo());
        if (classicTemplateTextAreaTrackName != null) {
            linkedHashMap.put("used_template_fine_print_content", classicTemplateTextAreaTrackName);
        }
        String classicTemplateClockAreaTrackName = trackerUtil.getClassicTemplateClockAreaTrackName(templateId, config.getLockscreenInfo().getClockInfo());
        if (classicTemplateClockAreaTrackName != null) {
            linkedHashMap.put("used_template_time_style", classicTemplateClockAreaTrackName);
        }
        ClockInfo clockInfo2 = config.getLockscreenInfo().getClockInfo();
        String classicTemplateContentAreaTrackName = trackerUtil.getClassicTemplateContentAreaTrackName(templateId, clockInfo2 != null ? Integer.valueOf(clockInfo2.getClassicLine3()) : null);
        if (classicTemplateContentAreaTrackName != null) {
            linkedHashMap.put("used_template_content_area_first_line", classicTemplateContentAreaTrackName);
        }
        ClockInfo clockInfo3 = config.getLockscreenInfo().getClockInfo();
        String classicTemplateContentAreaTrackName2 = trackerUtil.getClassicTemplateContentAreaTrackName(templateId, clockInfo3 != null ? Integer.valueOf(clockInfo3.getClassicLine4()) : null);
        if (classicTemplateContentAreaTrackName2 != null) {
            linkedHashMap.put("used_template_content_area_second_line", classicTemplateContentAreaTrackName2);
        }
        String templateSignatureTrackName = trackerUtil.getTemplateSignatureTrackName(templateId, config);
        if (templateSignatureTrackName != null) {
            linkedHashMap.put("used_template_has_signature", templateSignatureTrackName);
        }
        String classicTemplateFontTrackName = trackerUtil.getClassicTemplateFontTrackName(templateId, config.getLockscreenInfo().getClockInfo());
        if (classicTemplateFontTrackName != null) {
            linkedHashMap.put("used_template_global_font", classicTemplateFontTrackName);
        }
        ClockInfo clockInfo4 = config.getLockscreenInfo().getClockInfo();
        String rhombusTemplateDifferentMinuteTrackName = trackerUtil.getRhombusTemplateDifferentMinuteTrackName(templateId, clockInfo4 != null ? Boolean.valueOf(clockInfo4.isDiffHourMinuteColor()) : null);
        if (rhombusTemplateDifferentMinuteTrackName != null) {
            linkedHashMap.put("used_template_digital_color_different", rhombusTemplateDifferentMinuteTrackName);
        }
        String smartFrameStyleTrackName = trackerUtil.getSmartFrameStyleTrackName(templateId, config.getLockscreenInfo().getSmartFrame());
        if (smartFrameStyleTrackName != null) {
            linkedHashMap.put("used_template_frame_style", smartFrameStyleTrackName);
        }
        Pair historyTemplateTrackName = trackerUtil.getHistoryTemplateTrackName(context);
        linkedHashMap.put("favorite_templates_count", historyTemplateTrackName.getFirst());
        linkedHashMap.put("favorite_templates_content", historyTemplateTrackName.getSecond());
        EditorTracker.Companion.putCommonParams("348.1.0.1.28914", linkedHashMap);
        String json = GsonSingletonKt.getGSON_SINGLETON().toJson(linkedHashMap);
        Settings.Secure.putString(context.getContentResolver(), "keyguard_template_status", json);
        Log.d("Keyguard-Editor-TrackHelper", "trackCurrentTemplate: " + json);
    }

    public final void trackDeleteHistoryTemplate(final Context context, final TemplateItemBean templateItemBean, final int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackDeleteHistoryTemplate$lambda$19(templateItemBean, i, context);
            }
        });
    }

    public final void trackDownloadDepthVideo(final Context context, final CommonConfig config, final String trackId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackDownloadDepthVideo$lambda$36(context, config, trackId);
            }
        });
    }

    public final void trackEditorEnterPreview(final Context context, final int i, final String str, final String trackId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackEditorEnterPreview$lambda$17(i, context, str, trackId);
            }
        });
    }

    public final void trackEditorOpenTime(Context context, long j) {
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("duration", Long.valueOf(j));
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("start_keyguard_edit_mode", "348.1.2.1.36847", linkedHashMap);
        }
    }

    public final void trackOpenHomePage(final Context context, final boolean z, final String trackId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackOpenHomePage$lambda$12(context, z, trackId);
            }
        });
    }

    public final void trackProductDepthVideo(final Context context, final CommonConfig config, final String trackId, final String result, final long j) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        Intrinsics.checkNotNullParameter(result, "result");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackProductDepthVideo$lambda$35(context, config, trackId, result, j);
            }
        });
    }

    public final void trackQuitEditMode(final Context context, final String quitWay, final CommonConfig commonConfig, final CommonConfig commonConfig2, final Boolean bool, final boolean z, final boolean z2, final String trackId, final String videoStatus, final int i, final int i2, final int i3) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(quitWay, "quitWay");
        Intrinsics.checkNotNullParameter(trackId, "trackId");
        Intrinsics.checkNotNullParameter(videoStatus, "videoStatus");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackQuitEditMode$lambda$34(quitWay, commonConfig2, commonConfig, context, i3, bool, z, z2, videoStatus, i, i2, trackId);
            }
        });
    }

    public final void trackQuitHomePage(final Context context, final String quitWay, final CommonConfig commonConfig, final CommonConfig commonConfig2, final int[] iArr, final boolean z, final boolean z2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(quitWay, "quitWay");
        Runnable runnable = new Runnable() { // from class: com.miui.keyguard.editor.track.TrackHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TrackHelper.trackQuitHomePage$lambda$22(quitWay, commonConfig, commonConfig2, context, z2, z, iArr);
            }
        };
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            Task.run(runnable);
        } else {
            runnable.run();
        }
    }

    public final void trackVideoDialogClick(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("click_element", z ? "确定" : "取消");
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("window_click", "348.1.1.1.29335", linkedHashMap);
        }
    }

    public final void trackVideoDialogExpose(Context context, String enterWay) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(enterWay, "enterWay");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("enter_way", enterWay);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("window_expose", "348.1.1.1.29334", linkedHashMap);
        }
    }
}

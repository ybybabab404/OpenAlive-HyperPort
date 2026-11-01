package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateGroupConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.color.RandomColorUtil;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.TemplateHide;
import com.miui.keyguard.editor.view.ClockTemplateItem;
import com.miui.keyguard.editor.view.ClockTemplateRow;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.json.JSONArray;
import org.json.JSONObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateDataUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateDataUtil {
    public static final TemplateDataUtil INSTANCE = new TemplateDataUtil();

    public static final List getClockTemplate(Context context) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        String fileFromAssets = FileUtil.readFileFromAssets(context, "preset_clock_template_config.json");
        if (fileFromAssets == null || fileFromAssets.length() == 0) {
            Log.w("Keyguard-Editor-TemplateDataUtil", "getClockTemplate: presetJson is null or empty !");
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Map cLockContentDescriptionMap = INSTANCE.getCLockContentDescriptionMap(context);
        try {
            JSONArray jSONArray = new JSONArray(fileFromAssets);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("titleId");
                String string2 = jSONObject.getString("templates");
                if (!TemplateHide.INSTANCE.shouldHide(jSONObject.optInt("hideFlag"))) {
                    List<ClockTemplateItem> list = (List) GsonSingletonKt.getGSON_SINGLETON().fromJson(string2, new TypeToken<List<? extends ClockTemplateItem>>() { // from class: com.miui.keyguard.editor.data.template.TemplateDataUtil$getClockTemplate$list$1
                    }.getType());
                    final Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.data.template.TemplateDataUtil$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return Boolean.valueOf(TemplateDataUtil.getClockTemplate$lambda$3((ClockTemplateItem) obj));
                        }
                    };
                    list.removeIf(new Predicate() { // from class: com.miui.keyguard.editor.data.template.TemplateDataUtil$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return TemplateDataUtil.getClockTemplate$lambda$4(function1, obj);
                        }
                    });
                    if (list.isEmpty()) {
                        Log.w("Keyguard-Editor-TemplateDataUtil", "getPresetTemplateGroup: empty list find");
                    } else {
                        List list2 = (List) cLockContentDescriptionMap.get(string);
                        int i2 = 0;
                        for (ClockTemplateItem clockTemplateItem : list) {
                            int i3 = i2 + 1;
                            if (list2 != null && (str = (String) CollectionsKt.getOrNull(list2, i2)) != null) {
                                clockTemplateItem.setContentDescription(str);
                            }
                            i2 = i3;
                        }
                        TemplateDataUtil templateDataUtil = INSTANCE;
                        Intrinsics.checkNotNull(string);
                        arrayList.add(new ClockTemplateRow(templateDataUtil.findStringResIdByName(context, string), list, 0, 4, null));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateDataUtil", "getClockTemplate: parse json err!, " + e);
        }
        return arrayList;
    }

    public static final boolean getClockTemplate$lambda$3(ClockTemplateItem it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return TemplateHide.INSTANCE.shouldHide(it.getHideFlag());
    }

    public static final boolean getClockTemplate$lambda$4(Function1 function1, Object obj) {
        return ((Boolean) function1.invoke(obj)).booleanValue();
    }

    public static final List getPresetTemplateGroup(Context context) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        boolean zIsLiteDevice = deviceUtil.isLiteDevice();
        boolean zIsPad = deviceUtil.isPad();
        boolean zIsFold = deviceUtil.isFold();
        Log.i("Keyguard-Editor-TemplateDataUtil", "getPresetTemplateGroup -> isLite: " + zIsLiteDevice + ", isPad: " + zIsPad + "， isFold: " + zIsFold);
        if (zIsPad) {
            str = "preset_cross_config_pad.json";
        } else if (zIsLiteDevice) {
            str = "preset_cross_config_lite.json";
        } else {
            str = zIsFold ? "preset_cross_config_fold.json" : "preset_cross_config.json";
        }
        String fileFromAssets = FileUtil.readFileFromAssets(context, str);
        if (fileFromAssets == null || fileFromAssets.length() == 0) {
            Log.w("Keyguard-Editor-TemplateDataUtil", "getPresetTemplateGroup: presetJson is null or empty !");
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(fileFromAssets);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("title");
                String string2 = jSONObject.getString("desc");
                String string3 = jSONObject.getString("templates");
                if (!TemplateHide.INSTANCE.shouldHide(jSONObject.optInt("hideFlag"))) {
                    List list = (List) GsonSingletonKt.getGSON_SINGLETON().fromJson(string3, new TypeToken<List<? extends TemplateItemConfig>>() { // from class: com.miui.keyguard.editor.data.template.TemplateDataUtil$getPresetTemplateGroup$list$1
                    }.getType());
                    if (list != null) {
                        final Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.data.template.TemplateDataUtil$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return Boolean.valueOf(TemplateDataUtil.getPresetTemplateGroup$lambda$0((TemplateItemConfig) obj));
                            }
                        };
                        list.removeIf(new Predicate() { // from class: com.miui.keyguard.editor.data.template.TemplateDataUtil$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return TemplateDataUtil.getPresetTemplateGroup$lambda$1(function1, obj);
                            }
                        });
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        INSTANCE.loadTemplateDrawableResource(context, (TemplateItemConfig) it.next());
                    }
                    if (list == null || list.isEmpty()) {
                        Log.w("Keyguard-Editor-TemplateDataUtil", "getPresetTemplateGroup: empty list find");
                    } else {
                        TemplateDataUtil templateDataUtil = INSTANCE;
                        Intrinsics.checkNotNull(string);
                        int iFindStringResIdByName = templateDataUtil.findStringResIdByName(context, string);
                        Intrinsics.checkNotNull(string2);
                        arrayList.add(new TemplateGroupConfig(iFindStringResIdByName, templateDataUtil.findStringResIdByName(context, string2), list));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateDataUtil", "getPresetTemplateGroup: parse json err!, " + e);
        }
        return arrayList;
    }

    public static final boolean getPresetTemplateGroup$lambda$0(TemplateItemConfig it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return TemplateHide.INSTANCE.shouldHide(it.getHideFlag()) || !INSTANCE.isWallpaperExist(it.getWallpaperPath());
    }

    public static final boolean getPresetTemplateGroup$lambda$1(Function1 function1, Object obj) {
        return ((Boolean) function1.invoke(obj)).booleanValue();
    }

    public static final Bitmap getTemplateBitmap(String str, boolean z, int i, int i2, boolean z2) {
        if (str == null) {
            return null;
        }
        if (z) {
            try {
                str = TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(str);
            } catch (Exception e) {
                Log.e("Keyguard-Editor-TemplateDataUtil", "getTemplateBitmap: " + e);
                return null;
            }
        }
        String str2 = str;
        return (z2 || !z) ? BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, str2, i, i2, null, 8, null) : BitmapUtil.fromFile2$default(BitmapUtil.INSTANCE, str2, i, i2, null, 8, null);
    }

    public static /* synthetic */ Bitmap getTemplateBitmap$default(String str, boolean z, int i, int i2, boolean z2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i = -1;
        }
        if ((i3 & 8) != 0) {
            i2 = -1;
        }
        return getTemplateBitmap(str, z, i, i2, z2);
    }

    public static /* synthetic */ Bitmap getTemplateBitmapByProvider$default(TemplateDataUtil templateDataUtil, Context context, String str, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            i = -1;
        }
        if ((i4 & 8) != 0) {
            i2 = -1;
        }
        if ((i4 & 16) != 0) {
            i3 = 3;
        }
        return templateDataUtil.getTemplateBitmapByProvider(context, str, i, i2, i3);
    }

    public static final CommonConfig getTemplateConfig(Context context, String fileName, boolean z, int i) throws Throwable {
        TemplateConfig lockscreenInfo;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        if (TextUtils.isEmpty(fileName)) {
            Log.w("Keyguard-Editor-TemplateDataUtil", "getTemplateConfig: fileName is null !");
            return null;
        }
        try {
            if (!z) {
                File file = new File(fileName);
                if (!file.exists()) {
                    Log.w("Keyguard-Editor-TemplateDataUtil", "getTemplateConfig: config file not exists !");
                    return null;
                }
                String fileFromReader = FileUtil.readFileFromReader(new FileReader(file));
                CommonConfig.Companion companion = CommonConfig.Companion;
                Intrinsics.checkNotNull(fileFromReader);
                return CommonConfig.Companion.fromJson$default(companion, fileFromReader, null, context, 2, null);
            }
            CommonConfig commonConfig = (CommonConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(FileUtil.readFileFromAssets(context, fileName), CommonConfig.class);
            if (commonConfig != null && (lockscreenInfo2 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                wallpaperInfo2.setInfoType(WallpaperInfoType.LOCK_WALLPAPER);
            }
            if (commonConfig != null && (homeInfo = commonConfig.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
            }
            if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
                lockscreenInfo.setHideFlag(i);
            }
            RandomColorUtil.tryRandomColorType$default(RandomColorUtil.INSTANCE, commonConfig.getLockscreenInfo(), false, 2, null);
            INSTANCE.adaptClassicLineForGlobal(commonConfig.getLockscreenInfo().getClockInfo());
            return commonConfig;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateDataUtil", "getTemplateConfig: " + e);
            return null;
        }
    }

    public final void adaptClassicLineForGlobal(ClockInfo clockInfo) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isInternationalBuild() && !deviceUtil.isChineseLanguage() && Intrinsics.areEqual(clockInfo.getTemplateId(), "all_in_one") && clockInfo.getClassicLine1() == 101) {
            clockInfo.setClassicLine1(100);
        }
    }

    public final int findDrawableResIdByName(Context context, String drawableName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(drawableName, "drawableName");
        if (DeviceUtil.INSTANCE.isChineseLanguage()) {
            int iFindDrawableResIdByNameInternal = findDrawableResIdByNameInternal(context, drawableName + "_zh");
            if (iFindDrawableResIdByNameInternal > 0) {
                return iFindDrawableResIdByNameInternal;
            }
        }
        return findDrawableResIdByNameInternal(context, drawableName);
    }

    public final int findDrawableResIdByNameInternal(Context context, String str) {
        return context.getResources().getIdentifier(str, "drawable", context.getPackageName());
    }

    public final int findStringResIdByName(Context context, String str) {
        return context.getResources().getIdentifier(str, "string", context.getPackageName());
    }

    public final Map getCLockContentDescriptionMap(Context context) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String string = context.getResources().getString(R.string.kg_template_title_classic);
        String string2 = context.getResources().getString(R.string.kg_template_title_oriental_aesthetics);
        String string3 = context.getResources().getString(R.string.kg_template_title_oversize_time);
        String string4 = context.getResources().getString(R.string.kg_template_title_magazine);
        context.getResources().getString(R.string.kg_template_title_magazine_natural);
        linkedHashMap.put("kg_template_title_classic", CollectionsKt.mutableListOf(string + " max", string + " plus horizontal", string + " plus vertical", string3 + " A", string3 + " B", string4 + " C", string3 + " C", string3 + " D"));
        linkedHashMap.put("kg_template_title_oriental_aesthetics", CollectionsKt.mutableListOf(context.getResources().getString(R.string.kg_template_title_rhombus), string2 + " C", string2 + " A", string2 + " B"));
        linkedHashMap.put("kg_template_title_magazine", CollectionsKt.mutableListOf(string4 + " A", string4 + " B", string4 + " C", string3 + " C", string3 + " A", string3 + " B", string));
        linkedHashMap.put("kg_template_title_graffiti_lock", CollectionsKt.mutableListOf(context.getResources().getString(R.string.kg_doodle_clock_type_multilingual), context.getResources().getString(R.string.kg_doodle_clock_type_chinese), context.getResources().getString(R.string.kg_doodle_clock_type_digital)));
        return linkedHashMap;
    }

    public final Bitmap getTemplateBitmapByProvider(Context context, String str, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (str == null || str.length() == 0) {
            Log.i("Keyguard-Editor-TemplateDataUtil", "getTemplateBitmapByProvider: bitmapPath is null");
            return null;
        }
        int i4 = 0;
        Bitmap bitmapParcelFileDescriptorToBitmap$default = null;
        while (i4 < i3) {
            try {
                Bundle bundleCall = context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callGetFileDescriptor", str, (Bundle) null);
                int i5 = i;
                int i6 = i2;
                bitmapParcelFileDescriptorToBitmap$default = BitmapUtil.parcelFileDescriptorToBitmap$default(BitmapUtil.INSTANCE, bundleCall != null ? (ParcelFileDescriptor) bundleCall.getParcelable("parcelFileDescriptor") : null, i5, i6, Bitmap.Config.RGB_565, null, 16, null);
                if (bitmapParcelFileDescriptorToBitmap$default != null) {
                    return bitmapParcelFileDescriptorToBitmap$default;
                }
                Log.e("Keyguard-Editor-TemplateDataUtil", "contentProvider may have not been created, retry again");
                i4++;
                i = i5;
                i2 = i6;
            } catch (Exception e) {
                Log.i("Keyguard-Editor-TemplateDataUtil", "getTemplateBitmapByProvider: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return bitmapParcelFileDescriptorToBitmap$default;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:201:0x027d A[ADDED_TO_REGION, RETURN] */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final Integer getTemplateNameForAccessibility(ClockInfo clockInfo, String str) {
        if (clockInfo == null) {
            return null;
        }
        String templateId = clockInfo.getTemplateId();
        switch (templateId.hashCode()) {
            case -1744945801:
                if (templateId.equals("oversize_a")) {
                    return Integer.valueOf(R.string.kg_template_title_Ultra_longtime_a_talkback);
                }
                return null;
            case -1744945800:
                if (templateId.equals("oversize_b")) {
                    return Integer.valueOf(R.string.kg_template_title_Ultra_longtime_b_talkback);
                }
                return null;
            case -1326135015:
                if (templateId.equals("doodle")) {
                    int style = clockInfo.getStyle();
                    if (style == 1) {
                        return Integer.valueOf(R.string.kg_doodle_chinese_talkback);
                    }
                    if (style == 2) {
                        return Integer.valueOf(R.string.kg_doodle_multilingual_talkback);
                    }
                }
                return null;
            case -567074186:
                if (templateId.equals("magazine_a")) {
                    return Integer.valueOf(R.string.kg_template_title_magazine_a_talkback);
                }
                return null;
            case -567074185:
                if (templateId.equals("magazine_b")) {
                    return Integer.valueOf(R.string.kg_template_title_magazine_b_talkback);
                }
                return null;
            case -567074184:
                if (templateId.equals("magazine_c")) {
                    return clockInfo.getStyle() == 5 ? Integer.valueOf(R.string.kg_template_title_magazine_c_three_talkback) : Integer.valueOf(R.string.kg_template_title_magazine_c_one_talkback);
                }
                return null;
            case -138709961:
                if (templateId.equals("classic_max")) {
                    return Integer.valueOf(R.string.kg_template_title_classic_max_talkback);
                }
                return null;
            case -4941529:
                if (templateId.equals("classic_plus")) {
                    int classicLine2 = clockInfo.getClassicLine2();
                    if (classicLine2 == 72) {
                        return Integer.valueOf(R.string.kg_template_title_plus_vertical_talkback);
                    }
                    if (classicLine2 == 71) {
                        return Integer.valueOf(R.string.kg_template_title_plus_horizontal_talkback);
                    }
                }
                return null;
            case 225289008:
                if (!templateId.equals("depth_pets") || str == null) {
                    return null;
                }
                switch (str.hashCode()) {
                    case -897982453:
                        if (str.equals("pets_style1")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_potcoveredbird_talkback);
                        }
                        return null;
                    case -897982452:
                        if (str.equals("pets_style2")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_blackfacesheep_talkback);
                        }
                        return null;
                    case -897982451:
                        if (str.equals("pets_style3")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_harpseal_talkback);
                        }
                        return null;
                    case -897982450:
                        if (str.equals("pets_style4")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_bichondog_talkback);
                        }
                        return null;
                    case -897982449:
                        if (str.equals("pets_style5")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_panther_talkback);
                        }
                        return null;
                    case -897982448:
                        if (str.equals("pets_style6")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_siamesekitten_talkback);
                        }
                        return null;
                    case -897982447:
                        if (str.equals("pets_style7")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_capybara_talkback);
                        }
                        return null;
                    case -897982446:
                        if (str.equals("pets_style8")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_otter_talkback);
                        }
                        return null;
                    case -897982445:
                        if (str.equals("pets_style9")) {
                            return Integer.valueOf(R.string.kg_template_title_pets_germancat_talkback);
                        }
                        return null;
                    default:
                        return null;
                }
            case 853620882:
                if (templateId.equals("classic")) {
                    return Integer.valueOf(R.string.kg_template_title_classic);
                }
                return null;
            case 1179492204:
                if (templateId.equals("rhombus")) {
                    return Integer.valueOf(R.string.kg_template_title_rhombus);
                }
                return null;
            case 1250520234:
                if (templateId.equals("all_in_one")) {
                    int style2 = clockInfo.getStyle();
                    if (style2 == 1) {
                        return Integer.valueOf(R.string.kg_template_title_classic_max_talkback);
                    }
                    if (style2 == 2) {
                        return Integer.valueOf(R.string.kg_template_title_plus_horizontal_talkback);
                    }
                    if (style2 == 3) {
                        return Integer.valueOf(R.string.kg_template_title_plus_vertical_talkback);
                    }
                    if (style2 == 4) {
                        return Integer.valueOf(R.string.kg_template_title_Ultra_longtime_a_talkback);
                    }
                    if (style2 == 5) {
                        return Integer.valueOf(R.string.kg_template_title_Ultra_longtime_b_talkback);
                    }
                    if (style2 == 6) {
                        return Integer.valueOf(R.string.kg_template_title_magazine_c_one_talkback);
                    }
                    if (style2 == 7) {
                        return Integer.valueOf(R.string.kg_template_title_Ultra_longtime_c_talkback);
                    }
                    if (style2 == 8) {
                        return Integer.valueOf(R.string.kg_template_title_Ultra_longtime_d_talkback);
                    }
                }
                return null;
            case 1286956372:
                if (templateId.equals("pad_exclusive_a")) {
                    return Integer.valueOf(R.string.kg_template_title_tablet_exclusive_a_talkback);
                }
                return null;
            case 1286956373:
                if (templateId.equals("pad_exclusive_b")) {
                    return Integer.valueOf(R.string.kg_template_title_tablet_exclusive_b_talkback);
                }
                return null;
            case 1286956374:
                if (templateId.equals("pad_exclusive_c")) {
                    return Integer.valueOf(R.string.kg_template_title_tablet_exclusive_c_talkback);
                }
                return null;
            case 1786073894:
                if (templateId.equals("eastern_a")) {
                    int style3 = clockInfo.getStyle();
                    if (style3 == 1) {
                        return Integer.valueOf(R.string.kg_template_title_eastern_a_two_talkback);
                    }
                    if (style3 == 2) {
                        return Integer.valueOf(R.string.kg_template_title_eastern_a_three_talkback);
                    }
                }
                return null;
            case 1786073895:
                if (templateId.equals("eastern_b")) {
                    int style4 = clockInfo.getStyle();
                    if (style4 == 1) {
                        return Integer.valueOf(R.string.kg_template_title_eastern_art_b_vertical_talkback);
                    }
                    if (style4 == 2) {
                        return Integer.valueOf(R.string.kg_template_title_eastern_art_b_horizontal_talkback);
                    }
                }
                return null;
            case 1786073896:
                if (templateId.equals("eastern_c")) {
                    int style5 = clockInfo.getStyle();
                    if (style5 == 1) {
                        return Integer.valueOf(R.string.kg_template_title_eastern_art_c_verdana_talkback);
                    }
                    if (style5 == 2) {
                        return Integer.valueOf(R.string.kg_template_title_eastern_art_c_handwriting_talkback);
                    }
                }
                return null;
            default:
                return null;
        }
    }

    public final boolean isSupportMatting(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int i = Settings.Secure.getInt(context.getContentResolver(), "wallpaper_matting_support_2", 0);
        Log.i("Keyguard-Editor-TemplateDataUtil", "wallpaper_matting_support_2 " + i);
        return i == 1;
    }

    public final boolean isSupportShowDoodleText(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int i = Settings.Secure.getInt(context.getContentResolver(), "lockscreen_template_doodle_text_support", 0);
        Log.i("Keyguard-Editor-TemplateDataUtil", "lockscreen_template_doodle_text_support " + i);
        return i == 0;
    }

    public final boolean isWallpaperExist(String str) {
        boolean zIsFileExist = FileUtil.isFileExist(TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(str));
        if (!zIsFileExist) {
            Log.i("Keyguard-Editor-TemplateDataUtil", str + " is not exist!");
        }
        return zIsFileExist;
    }

    public final void loadTemplateDrawableResource(Context context, TemplateItemConfig templateItemConfig) {
        TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
        String presetDrawableName = companion.getPresetDrawableName(templateItemConfig.getTemplateName(), TemplateFilePathGenerator.PresetTemplateDrawableType.BELOW_LAYER);
        String presetDrawableName2 = companion.getPresetDrawableName(templateItemConfig.getTemplateName(), TemplateFilePathGenerator.PresetTemplateDrawableType.CLOCK_LAYER);
        templateItemConfig.setBelowLayerResId(findDrawableResIdByName(context, presetDrawableName));
        templateItemConfig.setClockLayerResId(findDrawableResIdByName(context, presetDrawableName2));
    }

    public final void setSupportMatting(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Settings.Secure.putInt(context.getContentResolver(), "wallpaper_matting_support_2", z ? 1 : 0);
    }

    public final void setSupportShowDoodleText(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Settings.Secure.putInt(context.getContentResolver(), "lockscreen_template_doodle_text_support", !z ? 1 : 0);
        Log.i("Keyguard-Editor-TemplateDataUtil", "setSupportShowDoodleText " + z);
    }
}

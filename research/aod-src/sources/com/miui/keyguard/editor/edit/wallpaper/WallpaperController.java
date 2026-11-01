package com.miui.keyguard.editor.edit.wallpaper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.WorkerThread;
import com.miui.aod.common.StyleInfo;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.WallpaperApplyParam;
import com.miui.keyguard.editor.utils.BaseWallpaperController;
import com.miui.keyguard.editor.utils.DeviceType;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.PickWallpaperColorInfo;
import com.miui.miwallpaper.material.MiuiColorScheme;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: WallpaperController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@WorkerThread
@SourceDebugExtension
public final class WallpaperController extends BaseWallpaperController implements IWallpaperController {
    public static volatile WallpaperController instance;
    public Set _supportPreviewEffectList;
    public boolean isThirdLockscreenWallpaper;
    public boolean isWallpaperSupportWaitImage;
    public static final Companion Companion = new Companion(null);
    public static final Regex CMF_PATH_REGEX = new Regex("wallpaper_([A-Z]{2})(?=[._]|$)");
    public static final Lazy cachedCmfDynamicEntries$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.wallpaper.WallpaperController$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return WallpaperController.cachedCmfDynamicEntries_delegate$lambda$9();
        }
    });

    /* JADX INFO: compiled from: WallpaperController.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {

        /* JADX INFO: compiled from: WallpaperController.kt */
        @Metadata
        public static final class CmfDynamicWallpaperEntry {
            public final String cmf;
            public final List previewPaths;
            public final String videoPath;

            public CmfDynamicWallpaperEntry(String str, String str2, List previewPaths) {
                Intrinsics.checkNotNullParameter(previewPaths, "previewPaths");
                this.cmf = str;
                this.videoPath = str2;
                this.previewPaths = previewPaths;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof CmfDynamicWallpaperEntry)) {
                    return false;
                }
                CmfDynamicWallpaperEntry cmfDynamicWallpaperEntry = (CmfDynamicWallpaperEntry) obj;
                return Intrinsics.areEqual(this.cmf, cmfDynamicWallpaperEntry.cmf) && Intrinsics.areEqual(this.videoPath, cmfDynamicWallpaperEntry.videoPath) && Intrinsics.areEqual(this.previewPaths, cmfDynamicWallpaperEntry.previewPaths);
            }

            public final String getCmf() {
                return this.cmf;
            }

            public final List getPreviewPaths() {
                return this.previewPaths;
            }

            public final String getVideoPath() {
                return this.videoPath;
            }

            public int hashCode() {
                String str = this.cmf;
                int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
                String str2 = this.videoPath;
                return ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + this.previewPaths.hashCode();
            }

            public String toString() {
                return "CmfDynamicWallpaperEntry(cmf=" + this.cmf + ", videoPath=" + this.videoPath + ", previewPaths=" + this.previewPaths + ")";
            }
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ boolean fillQ18SensorOuterVideoIfNeeded$default(Companion companion, String str, SensorWallpaperPrams sensorWallpaperPrams, String str2, List list, int i, Object obj) {
            if ((i & 4) != 0) {
                str2 = null;
            }
            if ((i & 8) != 0) {
                list = CollectionsKt.emptyList();
            }
            return companion.fillQ18SensorOuterVideoIfNeeded(str, sensorWallpaperPrams, str2, list);
        }

        public static /* synthetic */ String getPresetCmfDynamicPreviewPath$default(Companion companion, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            if ((i & 2) != 0) {
                str2 = null;
            }
            return companion.getPresetCmfDynamicPreviewPath(str, str2);
        }

        public static final String getPresetCmfDynamicPreviewPath$lambda$8(CmfDynamicWallpaperEntry entry) {
            Intrinsics.checkNotNullParameter(entry, "entry");
            Iterator it = entry.getPreviewPaths().iterator();
            while (it.hasNext()) {
                String strValidPath = WallpaperController.Companion.validPath((String) it.next());
                if (strValidPath != null) {
                    return strValidPath;
                }
            }
            return null;
        }

        public static /* synthetic */ String getPresetCmfDynamicVideoPath$default(Companion companion, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            if ((i & 2) != 0) {
                str2 = null;
            }
            return companion.getPresetCmfDynamicVideoPath(str, str2);
        }

        public static final String getPresetCmfDynamicVideoPath$lambda$6(CmfDynamicWallpaperEntry entry) {
            Intrinsics.checkNotNullParameter(entry, "entry");
            return WallpaperController.Companion.validPath(entry.getVideoPath());
        }

        public final String extractCmfCode(String str) {
            MatchResult matchResultFind$default;
            List groupValues;
            String str2;
            if (str == null || str.length() == 0 || (matchResultFind$default = Regex.find$default(getCMF_PATH_REGEX(), str, 0, 2, null)) == null || (groupValues = matchResultFind$default.getGroupValues()) == null || (str2 = (String) CollectionsKt.getOrNull(groupValues, 1)) == null) {
                return null;
            }
            Locale ROOT = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            String upperCase = str2.toUpperCase(ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            return upperCase;
        }

        /* JADX WARN: Code duplicated, block: B:18:0x004f  */
        /* JADX WARN: Code duplicated, block: B:21:0x0059  */
        /* JADX WARN: Code duplicated, block: B:74:0x0069 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:75:0x0067 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:76:? A[LOOP:0: B:19:0x0053->B:76:?, LOOP_END, SYNTHETIC] */
        public final boolean fillQ18SensorOuterVideoIfNeeded(String str, SensorWallpaperPrams sensorWallpaperPrams, String str2, List candidates) {
            Iterator it;
            String strExtractCmfCode;
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            boolean z = false;
            if (!DeviceUtil.INSTANCE.isQ18()) {
                return false;
            }
            if ((!Intrinsics.areEqual(str, "sensor") && !Intrinsics.areEqual(str, StyleInfo.DEFAULT_PARAMETER)) || sensorWallpaperPrams == null) {
                return false;
            }
            List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new String[]{sensorWallpaperPrams.getOuterVideoPreViewPath(), sensorWallpaperPrams.getOuterVideoPath(), sensorWallpaperPrams.getSmallThumbnailPath(), sensorWallpaperPrams.getThumbnailPath()}), (Iterable) CollectionsKt.filterNotNull(candidates));
            String presetCmfDynamicPreviewPath = null;
            if (str2 != null) {
                if (str2.length() == 0) {
                    str2 = null;
                }
                if (str2 == null) {
                    it = listPlus.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            str2 = null;
                            break;
                        }
                        strExtractCmfCode = WallpaperController.Companion.extractCmfCode((String) it.next());
                        if (strExtractCmfCode != null) {
                            str2 = strExtractCmfCode;
                            break;
                        }
                    }
                }
            } else {
                it = listPlus.iterator();
                while (true) {
                    if (it.hasNext()) {
                        str2 = null;
                        break;
                    }
                    strExtractCmfCode = WallpaperController.Companion.extractCmfCode((String) it.next());
                    if (strExtractCmfCode != null) {
                        str2 = strExtractCmfCode;
                        break;
                    }
                }
            }
            String strFirstValidPath = firstValidPath(sensorWallpaperPrams.getOuterVideoPath());
            if ((strFirstValidPath == null || strFirstValidPath.length() == 0) && (str2 == null || str2.length() == 0)) {
                Log.w("WallpaperController", "fillQ18SensorOuterVideoIfNeeded: no explicit cmf, skip preset resolution, resourceType=" + str + ", candidates=" + listPlus);
                return false;
            }
            String str3 = (String) CollectionsKt.firstOrNull(listPlus);
            boolean z2 = str2 == null || str2.length() == 0;
            if (strFirstValidPath == null) {
                strFirstValidPath = !z2 ? getPresetCmfDynamicVideoPath(str3, str2) : null;
            }
            String strFirstValidPath2 = firstValidPath(sensorWallpaperPrams.getOuterVideoPreViewPath());
            if (strFirstValidPath2 != null) {
                presetCmfDynamicPreviewPath = strFirstValidPath2;
            } else if (!z2) {
                presetCmfDynamicPreviewPath = getPresetCmfDynamicPreviewPath(str3, str2);
            }
            if (strFirstValidPath == null || strFirstValidPath.length() == 0) {
                Log.w("WallpaperController", "fillQ18SensorOuterVideoIfNeeded: no valid outer video, cmf=" + str2 + ", givenPath=" + str3 + ", candidates=" + listPlus);
                return false;
            }
            if (!Intrinsics.areEqual(sensorWallpaperPrams.getOuterVideoPath(), strFirstValidPath) || (presetCmfDynamicPreviewPath != null && presetCmfDynamicPreviewPath.length() != 0 && !Intrinsics.areEqual(sensorWallpaperPrams.getOuterVideoPreViewPath(), presetCmfDynamicPreviewPath))) {
                z = true;
            }
            sensorWallpaperPrams.setOuterVideoPath(strFirstValidPath);
            if (presetCmfDynamicPreviewPath != null && presetCmfDynamicPreviewPath.length() != 0) {
                sensorWallpaperPrams.setOuterVideoPreViewPath(presetCmfDynamicPreviewPath);
                sensorWallpaperPrams.setSmallThumbnailPath(presetCmfDynamicPreviewPath);
            }
            Log.i("WallpaperController", "fillQ18SensorOuterVideoIfNeeded: resolved outerVideo=" + strFirstValidPath + ", preview=" + presetCmfDynamicPreviewPath + ", cmf=" + str2 + ", changed=" + z);
            return true;
        }

        public final CmfDynamicWallpaperEntry findEntryByPath(List list, String str) {
            String absolutePath;
            File file = new File(str);
            File parentFile = file.getParentFile();
            Object obj = null;
            if (parentFile == null || (absolutePath = parentFile.getAbsolutePath()) == null) {
                return null;
            }
            String strRemoveSuffix = StringsKt.removeSuffix(FilesKt.getNameWithoutExtension(file), "_small");
            if (strRemoveSuffix.length() == 0) {
                return null;
            }
            loop0: for (Object obj2 : list) {
                CmfDynamicWallpaperEntry cmfDynamicWallpaperEntry = (CmfDynamicWallpaperEntry) obj2;
                List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(cmfDynamicWallpaperEntry.getVideoPath()), (Iterable) cmfDynamicWallpaperEntry.getPreviewPaths());
                if (listPlus == null || !listPlus.isEmpty()) {
                    Iterator it = listPlus.iterator();
                    while (it.hasNext()) {
                        File file2 = new File((String) it.next());
                        File parentFile2 = file2.getParentFile();
                        String absolutePath2 = parentFile2 != null ? parentFile2.getAbsolutePath() : null;
                        String strRemoveSuffix2 = StringsKt.removeSuffix(FilesKt.getNameWithoutExtension(file2), "_small");
                        if (Intrinsics.areEqual(absolutePath2, absolutePath) && Intrinsics.areEqual(strRemoveSuffix2, strRemoveSuffix)) {
                            obj = obj2;
                            break loop0;
                        }
                    }
                }
            }
            return (CmfDynamicWallpaperEntry) obj;
        }

        public final String firstValidPath(String... paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            for (String str : paths) {
                if (str != null && str.length() != 0) {
                    Intrinsics.checkNotNull(str);
                    if (new File(str).exists()) {
                        return str;
                    }
                }
            }
            return null;
        }

        public final Regex getCMF_PATH_REGEX() {
            return WallpaperController.CMF_PATH_REGEX;
        }

        public final List getCachedCmfDynamicEntries() {
            return (List) WallpaperController.cachedCmfDynamicEntries$delegate.getValue();
        }

        public final WallpaperWhich getCurrentWallpaperWhich(Context context) {
            if (context == null) {
                context = EditorApplicationProxy.Companion.getApplication();
            }
            return DeviceUtil.INSTANCE.isFoldSmallScreen(context) ? WallpaperWhich.SmallLock.INSTANCE : WallpaperWhich.LockWhich.INSTANCE;
        }

        public final WallpaperController getInstance() {
            return WallpaperController.instance;
        }

        public final WallpaperController getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return getInstance(context, true);
        }

        public final WallpaperController getInstance(Context context, boolean z) {
            WallpaperController wallpaperController;
            Intrinsics.checkNotNullParameter(context, "context");
            if (z && Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
                throw new RuntimeException("getInstance must be call in work thread!");
            }
            WallpaperController wallpaperController2 = WallpaperController.instance;
            if (wallpaperController2 != null) {
                return wallpaperController2;
            }
            synchronized (this) {
                wallpaperController = WallpaperController.instance;
                if (wallpaperController == null) {
                    Context applicationContext = context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                    wallpaperController = new WallpaperController(applicationContext, null);
                    WallpaperController.instance = wallpaperController;
                    wallpaperController.refreshMiuiWallpaperInfo(context);
                }
            }
            return wallpaperController;
        }

        public final PickWallpaperColorInfo getPickWallpaperColorInfo(String str, Integer num, Integer num2) {
            PickWallpaperColorInfo pickWallpaperColorInfo = new PickWallpaperColorInfo(str == null ? "classic" : str, null, null, 6, null);
            if (CollectionsKt.contains(WallpaperControllerKt.getAPPLY_WITH_CLOCK_STYLE_TEMPLATE(), str)) {
                pickWallpaperColorInfo.setClockInfoStyle(num);
                pickWallpaperColorInfo.setSignatureAlignment(num2);
                return pickWallpaperColorInfo;
            }
            pickWallpaperColorInfo.setClockInfoStyle(null);
            pickWallpaperColorInfo.setSignatureAlignment(null);
            return pickWallpaperColorInfo;
        }

        public final String getPresetCmfDynamicPreviewPath(String str, String str2) {
            return resolvePresetCmfDynamicPath(str, str2, "previewPath", new Function1() { // from class: com.miui.keyguard.editor.edit.wallpaper.WallpaperController$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return WallpaperController.Companion.getPresetCmfDynamicPreviewPath$lambda$8((WallpaperController.Companion.CmfDynamicWallpaperEntry) obj);
                }
            });
        }

        public final String getPresetCmfDynamicVideoPath(String str, String str2) {
            return resolvePresetCmfDynamicPath(str, str2, "videoPath", new Function1() { // from class: com.miui.keyguard.editor.edit.wallpaper.WallpaperController$Companion$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return WallpaperController.Companion.getPresetCmfDynamicVideoPath$lambda$6((WallpaperController.Companion.CmfDynamicWallpaperEntry) obj);
                }
            });
        }

        public final boolean hasFoldPresetSensorWallpaperPath(String str, SensorWallpaperPrams sensorWallpaperPrams, String... strArr) {
            String smallThumbnailPath;
            String strReplace$default;
            List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(str), (Iterable) ArraysKt.toList(strArr));
            String outerVideoPath = sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null;
            if (sensorWallpaperPrams == null || (smallThumbnailPath = sensorWallpaperPrams.getOuterVideoPreViewPath()) == null) {
                smallThumbnailPath = sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null;
            }
            List<String> listPlus2 = CollectionsKt.plus((Collection) listPlus, (Iterable) CollectionsKt.listOfNotNull((Object[]) new String[]{outerVideoPath, smallThumbnailPath}));
            if (listPlus2 != null && listPlus2.isEmpty()) {
                return false;
            }
            for (String str2 : listPlus2) {
                if ((str2 == null || (strReplace$default = StringsKt.replace$default(str2, '\\', '/', false, 4, (Object) null)) == null) ? false : StringsKt.contains$default((CharSequence) strReplace$default, (CharSequence) "fold_wallpaper_group", false, 2, (Object) null)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isWallpaperSupportWaitImage(Context context) {
            Object objM1061constructorimpl;
            Intrinsics.checkNotNullParameter(context, "context");
            try {
                Result.Companion companion = Result.Companion;
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.miui.miwallpaper", 128);
                Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
                Bundle bundle = applicationInfo.metaData;
                boolean z = false;
                Integer numValueOf = bundle != null ? Integer.valueOf(bundle.getInt("com_miui_miwallpaper_imageDrawEndCallback", 0)) : null;
                if (numValueOf != null && numValueOf.intValue() > 0) {
                    z = true;
                }
                objM1061constructorimpl = Result.m1061constructorimpl(Boolean.valueOf(z));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl != null) {
                Log.d("WallpaperController", "read miwallpaper error:" + thM1063exceptionOrNullimpl);
                objM1061constructorimpl = Boolean.FALSE;
            }
            return ((Boolean) objM1061constructorimpl).booleanValue();
        }

        public final List loadPresetCmfDynamicEntries() {
            CmfDynamicWallpaperEntry cmfDynamicWallpaperEntry;
            File file = new File("/system/media/wallpaper/wallpaper_cmf_dynamic/wallpaper_config.json");
            if (!file.exists()) {
                Log.w("WallpaperController", "loadPresetCmfDynamicEntries: config not found at /system/media/wallpaper/wallpaper_cmf_dynamic/wallpaper_config.json");
                return null;
            }
            try {
                JSONArray jSONArrayOptJSONArray = new JSONObject(FilesKt.readText$default(file, null, 1, null)).optJSONArray("wallpapers");
                if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() != 0) {
                    IntRange intRangeUntil = RangesKt.until(0, jSONArrayOptJSONArray.length());
                    ArrayList arrayList = new ArrayList();
                    Iterator<Integer> it = intRangeUntil.iterator();
                    while (it.hasNext()) {
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(((IntIterator) it).nextInt());
                        if (jSONObjectOptJSONObject != null) {
                            Companion companion = WallpaperController.Companion;
                            String strOptStringOrNull = companion.optStringOrNull(jSONObjectOptJSONObject, "cmf");
                            String strOptStringOrNull2 = companion.optStringOrNull(jSONObjectOptJSONObject, "videoPath");
                            List listListOf = CollectionsKt.listOf((Object[]) new String[]{"previewPath", "smallPreviewPath", "thumbnailPath", "imagePath", "coverPath"});
                            ArrayList arrayList2 = new ArrayList();
                            Iterator it2 = listListOf.iterator();
                            while (it2.hasNext()) {
                                String strOptStringOrNull3 = WallpaperController.Companion.optStringOrNull(jSONObjectOptJSONObject, (String) it2.next());
                                if (strOptStringOrNull3 != null) {
                                    arrayList2.add(strOptStringOrNull3);
                                }
                            }
                            cmfDynamicWallpaperEntry = ((strOptStringOrNull2 == null || strOptStringOrNull2.length() == 0) && arrayList2.isEmpty()) ? null : new CmfDynamicWallpaperEntry(strOptStringOrNull, strOptStringOrNull2, arrayList2);
                        }
                        if (cmfDynamicWallpaperEntry != null) {
                            arrayList.add(cmfDynamicWallpaperEntry);
                        }
                    }
                    return arrayList;
                }
                Log.w("WallpaperController", "loadPresetCmfDynamicEntries: wallpapers array empty");
                return CollectionsKt.emptyList();
            } catch (Exception e) {
                Log.e("WallpaperController", "loadPresetCmfDynamicEntries: error parsing config", e);
                return null;
            }
        }

        public final String optStringOrNull(JSONObject jSONObject, String str) {
            String strOptString = jSONObject.optString(str);
            if (strOptString == null || strOptString.length() == 0) {
                return null;
            }
            return strOptString;
        }

        /* JADX WARN: Code duplicated, block: B:23:0x0053  */
        public final String resolvePresetCmfDynamicPath(String str, String str2, String str3, Function1 function1) {
            String upperCase;
            String upperCase2;
            Object next;
            String str4;
            CmfDynamicWallpaperEntry cmfDynamicWallpaperEntryFindEntryByPath;
            String str5;
            Object next2;
            String str6;
            String strExtractCmfCode = str != null ? WallpaperController.Companion.extractCmfCode(str) : null;
            String str7 = SystemProperties.get("vendor.wallpaper.color.flag");
            if (str7 == null || str7.length() == 0) {
                str7 = null;
            }
            if (str7 != null) {
                Locale ROOT = Locale.ROOT;
                Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                upperCase = str7.toUpperCase(ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            } else {
                upperCase = null;
            }
            if (str2 == null) {
                upperCase2 = strExtractCmfCode;
            } else {
                String str8 = str2.length() == 0 ? null : str2;
                if (str8 != null) {
                    Locale ROOT2 = Locale.ROOT;
                    Intrinsics.checkNotNullExpressionValue(ROOT2, "ROOT");
                    upperCase2 = str8.toUpperCase(ROOT2);
                    Intrinsics.checkNotNullExpressionValue(upperCase2, "toUpperCase(...)");
                    if (upperCase2 == null) {
                        upperCase2 = strExtractCmfCode;
                    }
                } else {
                    upperCase2 = strExtractCmfCode;
                }
            }
            Log.i("WallpaperController", "resolvePresetCmfDynamicPath: valueName=" + str3 + ", cmfHint=" + str2 + ", pathCmf=" + strExtractCmfCode + ", vendorCmf=" + upperCase + ", explicitCmf=" + upperCase2 + ", givenPath=" + str);
            List cachedCmfDynamicEntries = getCachedCmfDynamicEntries();
            if (cachedCmfDynamicEntries == null || cachedCmfDynamicEntries.isEmpty()) {
                Log.w("WallpaperController", "resolvePresetCmfDynamicPath: " + str3 + " entries empty");
                return null;
            }
            if (upperCase2 != null && upperCase2.length() != 0) {
                Iterator it = cachedCmfDynamicEntries.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it.next();
                    String cmf = ((CmfDynamicWallpaperEntry) next2).getCmf();
                    if (cmf != null && StringsKt.equals(cmf, upperCase2, true)) {
                        break;
                    }
                }
                CmfDynamicWallpaperEntry cmfDynamicWallpaperEntry = (CmfDynamicWallpaperEntry) next2;
                if (cmfDynamicWallpaperEntry != null && (str6 = (String) function1.invoke(cmfDynamicWallpaperEntry)) != null) {
                    if (str6.length() == 0) {
                        str6 = null;
                    }
                    if (str6 != null) {
                        Log.i("WallpaperController", "resolvePresetCmfDynamicPath: " + str3 + " cmf match, cmf=" + upperCase2 + ", path=" + str6);
                        return str6;
                    }
                }
            }
            if (str != null && str.length() != 0 && (cmfDynamicWallpaperEntryFindEntryByPath = findEntryByPath(cachedCmfDynamicEntries, str)) != null && (str5 = (String) function1.invoke(cmfDynamicWallpaperEntryFindEntryByPath)) != null) {
                if (str5.length() == 0) {
                    str5 = null;
                }
                if (str5 != null) {
                    Log.i("WallpaperController", "resolvePresetCmfDynamicPath: " + str3 + " path match, givenPath=" + str + ", path=" + str5);
                    return str5;
                }
            }
            if (upperCase != null && upperCase.length() != 0) {
                Iterator it2 = cachedCmfDynamicEntries.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                    String cmf2 = ((CmfDynamicWallpaperEntry) next).getCmf();
                    if (cmf2 != null && StringsKt.equals(cmf2, upperCase, true)) {
                        break;
                    }
                }
                CmfDynamicWallpaperEntry cmfDynamicWallpaperEntry2 = (CmfDynamicWallpaperEntry) next;
                if (cmfDynamicWallpaperEntry2 != null && (str4 = (String) function1.invoke(cmfDynamicWallpaperEntry2)) != null) {
                    if (str4.length() == 0) {
                        str4 = null;
                    }
                    if (str4 != null) {
                        Log.i("WallpaperController", "resolvePresetCmfDynamicPath: " + str3 + " vendor fallback, vendorCmf=" + upperCase + ", path=" + str4);
                        return str4;
                    }
                }
            }
            Log.w("WallpaperController", "resolvePresetCmfDynamicPath: no valid " + str3 + " found (explicitCmf=" + upperCase2 + ", vendorCmf=" + upperCase + ", givenPath=" + str + ")");
            return null;
        }

        public final boolean shouldApplyOuterVideoWallpaper(WallpaperInfo wallpaperInfo, String... extraPaths) {
            Intrinsics.checkNotNullParameter(extraPaths, "extraPaths");
            SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo != null ? wallpaperInfo.getSensorWallpaperPrams() : null;
            String resourceType = wallpaperInfo != null ? wallpaperInfo.getResourceType() : null;
            String originResourcePath = wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null;
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(wallpaperInfo != null ? wallpaperInfo.getSource() : null);
            spreadBuilder.addSpread(extraPaths);
            return shouldApplyOuterVideoWallpaper(resourceType, originResourcePath, sensorWallpaperPrams, (String[]) spreadBuilder.toArray(new String[spreadBuilder.size()]));
        }

        public final boolean shouldApplyOuterVideoWallpaper(String str, String str2, SensorWallpaperPrams sensorWallpaperPrams, String... extraPaths) {
            Intrinsics.checkNotNullParameter(extraPaths, "extraPaths");
            if (!DeviceUtil.INSTANCE.isQ18()) {
                return false;
            }
            if (!Intrinsics.areEqual(str, "sensor") && !Intrinsics.areEqual(str, StyleInfo.DEFAULT_PARAMETER)) {
                return false;
            }
            if (firstValidPath(sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null) != null) {
                return true;
            }
            return hasFoldPresetSensorWallpaperPath(str2, sensorWallpaperPrams, (String[]) Arrays.copyOf(extraPaths, extraPaths.length));
        }

        public final String validPath(String str) {
            if (str == null || str.length() <= 0 || !new File(str).exists()) {
                return null;
            }
            return str;
        }
    }

    /* JADX INFO: compiled from: WallpaperController.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceType.values().length];
            try {
                iArr[DeviceType.PHONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceType.PAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceType.FOLD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public WallpaperController(Context context) {
        super(context);
    }

    public /* synthetic */ WallpaperController(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    public static /* synthetic */ void applyQ18OuterVideo$default(WallpaperController wallpaperController, String str, String str2, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            z = true;
        }
        if ((i & 8) != 0) {
            z2 = true;
        }
        wallpaperController.applyQ18OuterVideo(str, str2, z, z2);
    }

    public static final List cachedCmfDynamicEntries_delegate$lambda$9() {
        return Companion.loadPresetCmfDynamicEntries();
    }

    public static final Integer getCanPreviewEffectList$lambda$7(String str) {
        int i;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            Log.e("WallpaperController", "getCanPreviewEffectList: ", e);
            i = -1;
        }
        return Integer.valueOf(i);
    }

    public static final Integer getCanPreviewEffectList$lambda$8(Function1 function1, Object obj) {
        return (Integer) function1.invoke(obj);
    }

    public boolean applyFashionGalleryWallpaper(InputStream wallpaperInputStream, int i, boolean z, String str, boolean z2, boolean z3, boolean z4) {
        WallpaperWhich lockWhich;
        Intrinsics.checkNotNullParameter(wallpaperInputStream, "wallpaperInputStream");
        if (z4) {
            lockWhich = getAllWhich();
        } else {
            lockWhich = z ? getLockWhich() : getHomeWhich();
        }
        Log.i("WallpaperController", "applyFashionGalleryWallpaper: which=" + lockWhich);
        return setFashionGalleryWallpaper(wallpaperInputStream, i, lockWhich.getWhich(), str, z2, z3);
    }

    public void applyImageLockWallpaper(Bitmap wallpaper, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2, Pair originWallpaperWH, WallpaperApplyParam param, boolean z6) {
        WallpaperWhich lockWhich;
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        Intrinsics.checkNotNullParameter(originWallpaperWH, "originWallpaperWH");
        Intrinsics.checkNotNullParameter(param, "param");
        if (z6) {
            lockWhich = getAllWhich();
        } else {
            lockWhich = z ? getLockWhich() : getHomeWhich();
        }
        Log.d("WallpaperController", "applyImageLockWallpaper: lite device which=" + lockWhich + " isLock=" + z + ",enableBlur = " + z4 + ", homeEnableBlur = " + param.getHomeEnableBlur() + " applyAll=" + z6);
        applyImageWallpaper(wallpaper, i, lockWhich, z2, z3, z4, z5, i2, originWallpaperWH, param);
        Log.d("WallpaperController", "applyImageLockWallpaper: end");
    }

    public final void applyQ18OuterVideo(String str, String str2, boolean z, boolean z2) {
        if (str == null || str.length() == 0) {
            Log.w("WallpaperController", "applyQ18OuterVideo: outerVideoPath is empty");
            return;
        }
        Log.i("WallpaperController", "applyQ18OuterVideo: outerVideoPath=" + str + ", outerVideoPreViewPath=" + str2 + ", applyHome=" + z + ", applyLock=" + z2);
        if (z) {
            applyQ18OuterVideoForWhich(str, str2, WallpaperWhich.SmallHome.INSTANCE);
        }
        if (z2) {
            applyQ18OuterVideoForWhich(str, str2, WallpaperWhich.SmallLock.INSTANCE);
        }
    }

    public final void applyQ18OuterVideoForWhich(String str, String str2, WallpaperWhich wallpaperWhich) {
        Log.i("WallpaperController", "applyQ18OuterVideo: apply video to which=" + wallpaperWhich.getWhich() + ", path=" + str + ", preview=" + str2);
        applyVideoWallpaper(str, str2, false, 0, wallpaperWhich, true, false, 1, Boolean.TRUE);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperController
    public void applySuperWallpaper(String str, Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        setSuperWallpaper(context, str, z);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperController
    public void applyVideoWallpaper(String str, Object obj, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2, Boolean bool) {
        WallpaperWhich lockWhich;
        if (z5) {
            lockWhich = getAllWhich();
        } else {
            lockWhich = z2 ? getLockWhich() : getHomeWhich();
        }
        WallpaperWhich wallpaperWhich = lockWhich;
        Log.d("WallpaperController", "applyVideoWallpaper: which=" + wallpaperWhich + " videoPath=" + str + " applyAll=" + z5);
        applyVideoWallpaper(str, obj, z, i, wallpaperWhich, z3, z4, i2, bool);
    }

    public boolean curWallpaperIsSupportDark(int i) {
        return getMiuiWallpaperManager().supportDark(WallpaperWhich.LockWhich.INSTANCE.getWhich());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final Object extractParamsFromJson(JSONObject jSONObject, String str) {
        if (!jSONObject.has(str)) {
            return null;
        }
        switch (str.hashCode()) {
            case 132460769:
                if (str.equals("firstAnim")) {
                    return jSONObject.getJSONObject(str);
                }
                return null;
            case 320059199:
                if (str.equals("stiffness")) {
                    return Integer.valueOf(jSONObject.getInt(str));
                }
                return null;
            case 612205653:
                if (str.equals("smallScreenFrameCount")) {
                    return Integer.valueOf(jSONObject.getInt(str));
                }
                return null;
            case 934176381:
                if (str.equals("videoFrameCount")) {
                    return Integer.valueOf(jSONObject.getInt(str));
                }
                return null;
            case 1116678312:
                if (str.equals("anim_type")) {
                    return jSONObject.getString(str);
                }
                return null;
            default:
                return null;
        }
    }

    public Set getCanPreviewEffectList() {
        Set set = this._supportPreviewEffectList;
        if (set != null && (!set.isEmpty())) {
            Set set2 = this._supportPreviewEffectList;
            Intrinsics.checkNotNull(set2);
            return set2;
        }
        Stream stream = getMiuiWallpaperManager().getCanPreviewEffectList().stream();
        final Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.edit.wallpaper.WallpaperController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return WallpaperController.getCanPreviewEffectList$lambda$7((String) obj);
            }
        };
        Set set3 = (Set) stream.map(new Function() { // from class: com.miui.keyguard.editor.edit.wallpaper.WallpaperController$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return WallpaperController.getCanPreviewEffectList$lambda$8(function1, obj);
            }
        }).collect(Collectors.toSet());
        this._supportPreviewEffectList = set3;
        Log.i("WallpaperController", "getCanPreviewEffectList: " + set3);
        Set set4 = this._supportPreviewEffectList;
        Intrinsics.checkNotNull(set4);
        return set4;
    }

    public Boolean getCurBlurStatus() {
        return Boolean.valueOf(getMiuiWallpaperManager().blurState(WallpaperWhich.LockWhich.INSTANCE.getWhich()));
    }

    public int getCurrentMagicType(int i) {
        return getMiuiWallpaperManager().getEffectId(i);
    }

    public final String getDefaultVideoDepthSubject(WallpaperWhich which) {
        Intrinsics.checkNotNullParameter(which, "which");
        return getDefaultVideoDepthSubjectImpl(which);
    }

    public Map getDynamicColor(Context context, Bitmap bitmap, String str, String str2, int i, int i2, Rect rect, WallpaperWhich which) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(which, "which");
        Log.i("WallpaperController", "getDynamicColor: w=" + bitmap.getWidth() + " h=" + bitmap.getHeight() + " lockscreenInfo=" + str + ", rectWhich=" + str2 + ", clockStyle=" + i + ", signatureAlign=" + i2 + "，which=" + which.getWhich());
        return MiuiColorScheme.getMiuiWallpaperPalette(context, bitmap, str, i, i2, str2, rect, which.getWhich());
    }

    public String getGalleryJson(int i) {
        return getMiuiWallpaperManager().getGalleryJson(i);
    }

    public final String getHomeVideoWallpaperOriginPath() {
        return getVideoWallpaperOriginPathImpl(WallpaperWhich.HomeWhich.INSTANCE);
    }

    public Bitmap getHomeWallpaperPreview(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        return getWallpaperPreview((!deviceUtil.isFold() || deviceUtil.isFoldLargeScreen(context)) ? WallpaperWhich.HomeWhich.INSTANCE : WallpaperWhich.SmallHome.INSTANCE);
    }

    public String getHomeWallpaperType() {
        return getMiuiWallpaperType(WallpaperWhich.HomeWhich.INSTANCE);
    }

    public Bitmap getLockWallpaperPreview(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        return getWallpaperPreview((!deviceUtil.isFold() || deviceUtil.isFoldLargeScreen(context)) ? WallpaperWhich.LockWhich.INSTANCE : WallpaperWhich.SmallLock.INSTANCE);
    }

    public String getLockWallpaperType() {
        return getMiuiWallpaperType(WallpaperWhich.LockWhich.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:124:0x026b  */
    public Pair getSensorPrams(Context context, boolean z) {
        String strOptString;
        String str;
        String miuiWallpaperVideoFilePath;
        String str2;
        String str3;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            String miuiWallpaperSensorFilePath = getMiuiWallpaperManager().getMiuiWallpaperSensorFilePath(z ? WallpaperWhich.LockWhich.INSTANCE.getWhich() : WallpaperWhich.HomeWhich.INSTANCE.getWhich());
            int frameCount = getFrameCount(context, false);
            int frameCount2 = getFrameCount(context, true);
            int stiffness = getStiffness(context);
            int duration = getDuration(context);
            float factor = getFactor(context);
            String animType = getAnimType(context);
            boolean zIsPresetDefaultWallpaper = isPresetDefaultWallpaper();
            if (isMiuiDefaultWallpaper() && (frameCount <= 0 || frameCount2 < 0 || duration <= 0 || factor <= 0.0f || TextUtils.isEmpty(animType))) {
                JSONObject sensorPramsJson = getSensorPramsJson();
                if (sensorPramsJson != null) {
                    Object objExtractParamsFromJson = extractParamsFromJson(sensorPramsJson, "smallScreenFrameCount");
                    Integer num = objExtractParamsFromJson instanceof Integer ? (Integer) objExtractParamsFromJson : null;
                    if (num != null) {
                        frameCount2 = num.intValue();
                    }
                    Object objExtractParamsFromJson2 = extractParamsFromJson(sensorPramsJson, "stiffness");
                    Integer num2 = objExtractParamsFromJson2 instanceof Integer ? (Integer) objExtractParamsFromJson2 : null;
                    if (num2 != null) {
                        stiffness = num2.intValue();
                    }
                    Object objExtractParamsFromJson3 = extractParamsFromJson(sensorPramsJson, "anim_type");
                    String str4 = objExtractParamsFromJson3 instanceof String ? (String) objExtractParamsFromJson3 : null;
                    if (str4 != null) {
                        animType = str4;
                    }
                    Object objExtractParamsFromJson4 = extractParamsFromJson(sensorPramsJson, "videoFrameCount");
                    Integer num3 = objExtractParamsFromJson4 instanceof Integer ? (Integer) objExtractParamsFromJson4 : null;
                    if (num3 != null) {
                        frameCount = num3.intValue();
                    }
                    Object objExtractParamsFromJson5 = extractParamsFromJson(sensorPramsJson, "firstAnim");
                    JSONObject jSONObject = objExtractParamsFromJson5 instanceof JSONObject ? (JSONObject) objExtractParamsFromJson5 : null;
                    if (jSONObject != null) {
                        factor = jSONObject.getInt("factor");
                        duration = jSONObject.getInt("duration");
                    }
                }
                Log.i("WallpaperController", "getSensorPrams error, getSensorPramsJson: sensorParamsJson=" + sensorPramsJson);
            }
            String str5 = animType;
            Log.i("WallpaperController", "getSensorPrams: allFrame=" + frameCount + ", smallFrames=" + frameCount2 + ", stiffness=" + stiffness + ", duration=" + duration + ", factor=" + factor + ", animType=" + ((Object) str5));
            String miuiWallpaperSensorPreviewFilePath = getMiuiWallpaperManager().getMiuiWallpaperSensorPreviewFilePath(z ? WallpaperWhich.SmallLock.INSTANCE.getWhich() : WallpaperWhich.SmallHome.INSTANCE.getWhich());
            String miuiWallpaperSensorPreviewFilePath2 = getMiuiWallpaperManager().getMiuiWallpaperSensorPreviewFilePath(z ? WallpaperWhich.LockWhich.INSTANCE.getWhich() : WallpaperWhich.HomeWhich.INSTANCE.getWhich());
            JSONObject sensorPramsJson2 = getSensorPramsJson();
            if (sensorPramsJson2 == null || (strOptString = sensorPramsJson2.optString("cmf")) == null || strOptString.length() == 0) {
                strOptString = null;
            }
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isQ18()) {
                miuiWallpaperVideoFilePath = getMiuiWallpaperManager().getMiuiWallpaperVideoFilePath(WallpaperWhich.SmallHome.INSTANCE.getWhich());
                if (miuiWallpaperVideoFilePath == null || miuiWallpaperVideoFilePath.length() == 0) {
                    miuiWallpaperVideoFilePath = null;
                }
                if (miuiWallpaperVideoFilePath == null || !new File(miuiWallpaperVideoFilePath).exists()) {
                    miuiWallpaperVideoFilePath = null;
                }
                str = miuiWallpaperSensorPreviewFilePath;
                String miuiWallpaperVideoFilePath2 = getMiuiWallpaperManager().getMiuiWallpaperVideoFilePath(WallpaperWhich.SmallLock.INSTANCE.getWhich());
                if (miuiWallpaperVideoFilePath2 == null || miuiWallpaperVideoFilePath2.length() == 0) {
                    miuiWallpaperVideoFilePath2 = null;
                }
                if (miuiWallpaperVideoFilePath2 == null || !new File(miuiWallpaperVideoFilePath2).exists()) {
                    miuiWallpaperVideoFilePath2 = null;
                }
                if (miuiWallpaperVideoFilePath == null) {
                    if (miuiWallpaperVideoFilePath2 == null) {
                        miuiWallpaperVideoFilePath2 = Companion.getPresetCmfDynamicVideoPath$default(Companion, null, strOptString, 1, null);
                    }
                    miuiWallpaperVideoFilePath = miuiWallpaperVideoFilePath2;
                }
            } else {
                str = miuiWallpaperSensorPreviewFilePath;
                miuiWallpaperVideoFilePath = null;
            }
            String presetCmfDynamicPreviewPath$default = deviceUtil.isQ18() ? Companion.getPresetCmfDynamicPreviewPath$default(Companion, null, strOptString, 1, null) : null;
            Log.d("WallpaperController", "getSensorPrams: isDefaultWallpaper=" + zIsPresetDefaultWallpaper);
            if (!deviceUtil.isQ18() || miuiWallpaperVideoFilePath == null || miuiWallpaperVideoFilePath.length() == 0) {
                str2 = str;
                str3 = presetCmfDynamicPreviewPath$default;
            } else {
                String wallpaperPath = getWallpaperPath("video", (z ? WallpaperWhich.SmallLock.INSTANCE : WallpaperWhich.SmallHome.INSTANCE).getWhich(), false, true);
                if (presetCmfDynamicPreviewPath$default != null && presetCmfDynamicPreviewPath$default.length() != 0 && new File(presetCmfDynamicPreviewPath$default).exists()) {
                    Log.i("WallpaperController", "getSensorPrams: Q18 default wallpaper, use cmf preview: " + presetCmfDynamicPreviewPath$default);
                    str2 = presetCmfDynamicPreviewPath$default;
                } else if (wallpaperPath == null || wallpaperPath.length() == 0 || !new File(wallpaperPath).exists()) {
                    str2 = str;
                    str3 = presetCmfDynamicPreviewPath$default;
                } else {
                    Log.i("WallpaperController", "getSensorPrams: Q18 default wallpaper, use video preview: " + wallpaperPath);
                    str2 = wallpaperPath;
                }
                str3 = str2;
            }
            Log.d("WallpaperController", "after getSensorPrams: outerVideoPath=" + miuiWallpaperVideoFilePath + ", outerVideoPreViewPath=" + str3);
            return new Pair(miuiWallpaperSensorFilePath, new SensorWallpaperPrams(miuiWallpaperSensorPreviewFilePath2, str2, Integer.valueOf(frameCount), Integer.valueOf(frameCount2), Integer.valueOf(stiffness), Integer.valueOf(duration), Float.valueOf(factor), str5, miuiWallpaperVideoFilePath, str3));
        } catch (Exception e) {
            Log.e("WallpaperController", "getSensorPrams: ", e);
            return null;
        }
    }

    public final JSONObject getSensorPramsJson() {
        try {
            return new JSONObject(FilesKt.readText$default(new File("/system/media/wallpaper/fold_wallpaper_group/default_sensor_wallpaper_config.json"), null, 1, null));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final List getVideoDepthBitmap() {
        return getMiuiWallpaperManager().getVideoDepthScreenshot();
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperController
    public String getVideoWallpaperOriginPath(WallpaperWhich which) {
        Intrinsics.checkNotNullParameter(which, "which");
        return getVideoWallpaperOriginPathImpl(which);
    }

    public String getWallpaperType(DeviceType deviceType, boolean z) {
        Intrinsics.checkNotNullParameter(deviceType, "deviceType");
        int i = WhenMappings.$EnumSwitchMapping$0[deviceType.ordinal()];
        if (i == 1) {
            return getMiuiWallpaperType(z ? WallpaperWhich.LockWhich.INSTANCE : WallpaperWhich.HomeWhich.INSTANCE);
        }
        if (i == 2) {
            return getMiuiWallpaperType(z ? WallpaperWhich.LockWhich.INSTANCE : WallpaperWhich.HomeWhich.INSTANCE);
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        WallpaperWhich wallpaperWhich = z ? WallpaperWhich.SmallLock.INSTANCE : WallpaperWhich.SmallHome.INSTANCE;
        WallpaperWhich wallpaperWhich2 = z ? WallpaperWhich.LockWhich.INSTANCE : WallpaperWhich.HomeWhich.INSTANCE;
        String miuiWallpaperType = getMiuiWallpaperType(wallpaperWhich);
        return miuiWallpaperType == null ? getMiuiWallpaperType(wallpaperWhich2) : miuiWallpaperType;
    }

    public Boolean isDarkWallpaper(Context context, Bitmap bitmap, String str, String str2, int i, int i2, WallpaperWhich which) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(which, "which");
        Log.i("WallpaperController", "isDarkWallpaper: lockscreenInfo=" + str + ", rectWhich=" + str2 + ", clockStyle=" + i + ", signatureAlign=" + i2 + "， which=" + which.getWhich());
        return Boolean.valueOf(MiuiColorScheme.getIsDeepWallpaper(context, bitmap, str, i, i2, str2, which.getWhich()));
    }

    public boolean isMiuiWallpaperComponentUsing(int i) {
        return getMiuiWallpaperManager().isMiuiWallpaperComponentUsing(i);
    }

    public final boolean isPresetDefaultWallpaper() {
        JSONObject sensorPramsJson = getSensorPramsJson();
        if (sensorPramsJson != null) {
            return sensorPramsJson.optBoolean("isDefaultWallpaper", false);
        }
        Log.w("WallpaperController", "isPresetDefaultWallpaper: JSON parse failed, fallback to isMiuiDefaultWallpaper");
        return isMiuiDefaultWallpaper();
    }

    public final boolean isWallpaperSupportWaitImage() {
        return this.isWallpaperSupportWaitImage;
    }

    public final boolean needResetMagicType() {
        Log.d("WallpaperController", "needResetMagicType: isWallpaperSupportWaitImage=" + this.isWallpaperSupportWaitImage + " isThirdLockscreenWallpaper=" + this.isThirdLockscreenWallpaper);
        return this.isWallpaperSupportWaitImage && this.isThirdLockscreenWallpaper;
    }

    public final void refreshMiuiWallpaperInfo(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.isWallpaperSupportWaitImage = Companion.isWallpaperSupportWaitImage(context);
        boolean z = !TemplateApi.Companion.getInstance(context).isMiuiSetLockWallpaper();
        this.isThirdLockscreenWallpaper = z;
        Log.w("WallpaperController", "refreshMiuiWallpaperInfo: isWallpaperSupportWaitImage=" + this.isWallpaperSupportWaitImage + " isThirdLockscreenWallpaper=" + z);
    }

    public void setMiuiSensorWallpaper(Context context, String str, Bitmap bitmap, Bitmap bitmap2, Integer num, Integer num2, Integer num3, Integer num4, Float f, String str2, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        int which;
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isFlip() || deviceUtil.isQ18()) {
            if (z3) {
                which = 3;
            } else {
                which = z4 ? 2 : 1;
            }
        } else if (z3) {
            which = getAllWhich().getWhich();
        } else {
            which = z4 ? getLockWhich().getWhich() : getHomeWhich().getWhich();
        }
        int i2 = which;
        Log.i("WallpaperController", "setMiuiSensorWallpaper: multiWhich=" + i2 + ", applyAll=" + z3 + ", isLock=" + z4);
        setMiuiSensorWallpaper(context, str, bitmap, bitmap2, i2, num, num2, num3, num4, f, str2, i, z, z2);
    }

    public boolean supportReversePlayback(int i) {
        return getMiuiWallpaperManager().supportReversePlayback(i);
    }
}

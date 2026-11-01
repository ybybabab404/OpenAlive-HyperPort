package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.os.UserHandle;
import android.util.Log;
import com.miui.aod.common.StyleInfo;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import com.xiaomi.onetrack.c.ag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: TemplateFilePathGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateFilePathGenerator {
    public static final Companion Companion = new Companion(null);
    public static final String depthVideoWallpaperDirPath = "/data/system/theme_magic/users/" + UserHandle.myUserId() + "/wallpaper/video_depth_editor";
    public final String baseDirPath;
    public final String baseTemplateDirPath;
    public final String cacheDir;
    public final Lazy englishConfigPath$delegate;
    public final Lazy redMiConfigPath$delegate;

    /* JADX INFO: compiled from: TemplateFilePathGenerator.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final TemplateFileType getCurrentScreenshotType(int i, boolean z) {
            if (i == 1) {
                return z ? TemplateFileType.SMALL_SCREENSHOT : TemplateFileType.SMALL_WALLPAPER_SCREENSHOT;
            }
            if (i == 2) {
                return z ? TemplateFileType.LARGE_PORTRAIT_SCREENSHOT : TemplateFileType.LARGE_PORTRAIT_WALLPAPER_SCREENSHOT;
            }
            if (i == 3) {
                return z ? TemplateFileType.LARGE_LANDSCAPE_SCREENSHOT : TemplateFileType.LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT;
            }
            Log.w("TemplateFilePathGenerator", "getCurrentScreenshotType: error screenStatus=" + i + " isLock=" + z);
            return z ? TemplateFileType.SMALL_SCREENSHOT : TemplateFileType.SMALL_WALLPAPER_SCREENSHOT;
        }

        public final String getDepthVideoWallpaperDirPath() {
            return TemplateFilePathGenerator.depthVideoWallpaperDirPath;
        }

        public final String getPresetDrawableName(String templateName, PresetTemplateDrawableType type) {
            Intrinsics.checkNotNullParameter(templateName, "templateName");
            Intrinsics.checkNotNullParameter(type, "type");
            return templateName + type.getSuffix();
        }

        public final String getPresetWallpaperAbsolutePath(String relativePath) {
            Intrinsics.checkNotNullParameter(relativePath, "relativePath");
            return "product/media/wallpaper/" + relativePath;
        }

        public final boolean isPresetPath(String str) {
            if (str == null) {
                Log.d("TemplateFilePathGenerator", "path is null");
                return false;
            }
            if (FileUtil.isFileExist(str)) {
                return false;
            }
            if (FileUtil.isFileExist(getPresetWallpaperAbsolutePath(str))) {
                return true;
            }
            Log.d("TemplateFilePathGenerator", StyleInfo.DEFAULT_PARAMETER);
            return false;
        }
    }

    /* JADX INFO: compiled from: TemplateFilePathGenerator.kt */
    @Metadata
    public enum PresetTemplateDrawableType {
        CLOCK_LAYER("_clock_layer"),
        BELOW_LAYER("_below_layer");

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        public final String suffix;

        PresetTemplateDrawableType(String str) {
            this.suffix = str;
        }

        public final String getSuffix() {
            return this.suffix;
        }
    }

    /* JADX INFO: compiled from: TemplateFilePathGenerator.kt */
    @Metadata
    public enum TemplateBackupFileType {
        TEMPLATE_BACKUP_FOLDER(""),
        TEMPLATE_RESTORE_FOLDER(""),
        BACKUP_ZIP("template_backup_zip"),
        NEW_BACKUP_ZIP("template_new_backup_zip"),
        VERSION_BACKUP_ZIP("template_backup_os4_zip"),
        BACKUP_CURRENT_CACHE("current_cache"),
        HISTORY_TEMPLATE("history_template_backup"),
        HISTORY_TEMPLATE_V2("history_template_backup.2"),
        CURRENT_TEMPLATE("current_template_backup"),
        WALLPAPER("wallpaper_backup"),
        WALLPAPER_HOME("wallpaper_home_backup"),
        WALLPAPER_VIDEO("wallpaper_video_backup"),
        WALLPAPER_HOME_VIDEO("wallpaper_home_video_backup"),
        SETTINGS("settings_backup"),
        CROP_SUBJECT("crop_subject_backup"),
        SENSOR_WALLPAPER_PREVIEW("sensor_wallpaper_preview_backup"),
        SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN("sensor_wallpaper_preview_small_backup"),
        OUTER_VIDEO_WALLPAPER_BACKUP("outerVideoWallpaper_backup");

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        public final String fileName;

        TemplateBackupFileType(String str) {
            this.fileName = str;
        }

        public final String getFileName() {
            return this.fileName;
        }
    }

    /* JADX INFO: compiled from: TemplateFilePathGenerator.kt */
    @Metadata
    public enum TemplateFileType {
        CONFIG(ag.a),
        SMALL_SCREENSHOT("small_screenshot"),
        LARGE_PORTRAIT_SCREENSHOT("large_portrait_screenshot"),
        LARGE_LANDSCAPE_SCREENSHOT("large_landscape_screenshot"),
        SMALL_WALLPAPER_SCREENSHOT("small_wallpaper_screenshot"),
        LARGE_PORTRAIT_WALLPAPER_SCREENSHOT("large_portrait_wallpaper_screenshot"),
        LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT("large_landscape_wallpaper_screenshot"),
        WALLPAPER("wallpaper"),
        WALLPAPER_HOME("wallpaper_home"),
        WALLPAPER_COPIED("wallpaper_copied"),
        WALLPAPER_CROPPED("wallpaper_cropped"),
        VIDEO_WALLPAPER("videoWallpaper"),
        DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK("depthVideoScreenshotWallpaperLock"),
        VIDEO_WALLPAPER_HOME("videoWallpaperHome"),
        SUBJECT("subject"),
        SUBJECT_MASK("subject_mask"),
        CROP_SUBJECT("crop_subject"),
        THIRD_PARTY_THEME_PREVIEW("third_party_theme_preview"),
        SENSOR_WALLPAPER_PREVIEW("sensor_wallpaper_preview"),
        SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN("sensor_wallpaper_preview_small"),
        OUTER_VIDEO_WALLPAPER("outerVideoWallpaper"),
        CURRENT_DARKEN_WALLPAPER("current_darken_wallpaper"),
        FRONT_BACK("front_back"),
        CROPPED_FRONT_BACK("cropped_front_back"),
        WALLPAPER_SCALED("wallpaper_scaled.jpg");

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        public final String fileName;

        TemplateFileType(String str) {
            this.fileName = str;
        }

        public final String getFileName() {
            return this.fileName;
        }
    }

    /* JADX INFO: compiled from: TemplateFilePathGenerator.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TemplateFileType.values().length];
            try {
                iArr[TemplateFileType.CONFIG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TemplateFileType.WALLPAPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[TemplateBackupFileType.values().length];
            try {
                iArr2[TemplateBackupFileType.TEMPLATE_BACKUP_FOLDER.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[TemplateBackupFileType.TEMPLATE_RESTORE_FOLDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[TemplateBackupFileType.BACKUP_ZIP.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[TemplateBackupFileType.NEW_BACKUP_ZIP.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[TemplateBackupFileType.VERSION_BACKUP_ZIP.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[TemplateBackupFileType.BACKUP_CURRENT_CACHE.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public TemplateFilePathGenerator(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.englishConfigPath$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateFilePathGenerator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TemplateFilePathGenerator.englishConfigPath_delegate$lambda$1(context);
            }
        });
        this.redMiConfigPath$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateFilePathGenerator$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TemplateFilePathGenerator.redMiConfigPath_delegate$lambda$3(context);
            }
        });
        String absolutePath = context.getFilesDir().getAbsolutePath();
        this.baseDirPath = absolutePath;
        this.cacheDir = context.getCacheDir().getAbsolutePath() + "/preview";
        this.baseTemplateDirPath = absolutePath + "/templates";
    }

    public static final List englishConfigPath_delegate$lambda$1(Context context) throws IOException {
        List listAsList;
        String[] list = context.getResources().getAssets().list(ag.a);
        if (list == null || (listAsList = ArraysKt.asList(list)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAsList) {
            String str = (String) obj;
            if (str != null && StringsKt.endsWith$default(str, "_global.json", false, 2, (Object) null)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final List redMiConfigPath_delegate$lambda$3(Context context) throws IOException {
        List listAsList;
        String[] list = context.getResources().getAssets().list(ag.a);
        if (list == null || (listAsList = ArraysKt.asList(list)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAsList) {
            String str = (String) obj;
            if (str != null && StringsKt.endsWith$default(str, "_redmi.json", false, 2, (Object) null)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final String generateCurrentTemplateByFileName(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return this.baseTemplateDirPath + "/current/" + fileName;
    }

    public final String generateCurrentTemplateFilePath(TemplateFileType fileType) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        if (fileType != TemplateFileType.CROP_SUBJECT) {
            return generateCurrentTemplateByFileName(fileType.getFileName());
        }
        return "/data/system/theme_magic/users/" + UserHandle.myUserId() + "/wallpaper/crop_subject";
    }

    public final String generateCurrentTemplateFolder() {
        return this.baseTemplateDirPath + "/current";
    }

    public final String generateDepthVideoProduceFolder() {
        return this.baseDirPath + "/produceDepthVideo";
    }

    public final String generateDepthVideoWallpaperFilePath(TemplateFileType fileType) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        return depthVideoWallpaperDirPath + "/" + fileType.getFileName();
    }

    public final String generateFilePath(TemplateFileType fileType) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        return this.baseTemplateDirPath + "/" + fileType.getFileName();
    }

    public final String generateFilePathByName(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return this.baseTemplateDirPath + "/" + fileName;
    }

    public final String generateHistoryRootDir() {
        return this.baseTemplateDirPath + "/history";
    }

    public final String generateHistoryTemplateDir(long j) {
        return this.baseTemplateDirPath + "/history/" + j;
    }

    public final String generateHistoryTemplateFilePath(long j, TemplateFileType fileType) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        return this.baseTemplateDirPath + "/history/" + j + "/" + fileType.getFileName();
    }

    public final String generatePresetTemplateFilePath(String templateName, TemplateFileType fileType) {
        List redMiConfigPath;
        Intrinsics.checkNotNullParameter(templateName, "templateName");
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        int i = WhenMappings.$EnumSwitchMapping$0[fileType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return "";
            }
            return "wallpaper/" + templateName + ".jpg";
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isInternationalBuild() || !deviceUtil.isChineseLanguage()) {
            if (deviceUtil.isChineseLanguage()) {
                return "config/" + templateName + ".json";
            }
            List englishConfigPath = getEnglishConfigPath();
            if (englishConfigPath != null) {
                if (englishConfigPath.contains(templateName + "_global.json")) {
                    return "config/" + templateName + "_global.json";
                }
            }
        }
        if (deviceUtil.isRedmi() && !deviceUtil.isInternationalBuild() && (redMiConfigPath = getRedMiConfigPath()) != null) {
            if (redMiConfigPath.contains(templateName + "_redmi.json")) {
                return "config/" + templateName + "_redmi.json";
            }
        }
        return "config/" + templateName + ".json";
    }

    public final String generateTemplateBackupFilePath(TemplateBackupFileType fileType) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        switch (WhenMappings.$EnumSwitchMapping$1[fileType.ordinal()]) {
            case 1:
                return this.baseTemplateDirPath;
            case 2:
                String baseDirPath = this.baseDirPath;
                Intrinsics.checkNotNullExpressionValue(baseDirPath, "baseDirPath");
                return baseDirPath;
            case 3:
                return this.baseDirPath + "/" + fileType.getFileName();
            case 4:
                return this.baseDirPath + "/" + fileType.getFileName();
            case 5:
                return this.baseDirPath + "/" + fileType.getFileName();
            case 6:
                return this.baseTemplateDirPath + "/" + fileType.getFileName();
            default:
                return this.baseTemplateDirPath + "/" + fileType.getFileName();
        }
    }

    public final String generateTemplateFilePath(TemplateBackupFileType fileType) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        return "templates/" + fileType.getFileName();
    }

    public final String getCacheDirPath() {
        return this.cacheDir;
    }

    public final List getEnglishConfigPath() {
        return (List) this.englishConfigPath$delegate.getValue();
    }

    public final List getRedMiConfigPath() {
        return (List) this.redMiConfigPath$delegate.getValue();
    }
}

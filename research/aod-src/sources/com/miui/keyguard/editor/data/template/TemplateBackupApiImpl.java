package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.allInOne.AllInOneUtil;
import com.miui.clock.module.ClockBean;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.db.EditorDatabase;
import com.miui.keyguard.editor.data.migration.AllInOneClockMigration;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.model.CrossListDataModel;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.TemplateHide;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.maml.R;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miui.app.backup.FullBackupAgent;

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
/* JADX INFO: compiled from: TemplateBackupApiImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateBackupApiImpl implements TemplateBackupApi {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final TemplateFilePathGenerator filePathGenerator;
    public final TemplateApi templateApi;

    /* JADX INFO: compiled from: TemplateBackupApiImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.template.TemplateBackupApiImpl$restoreHistoryTemplates$1, reason: invalid class name */
    /* JADX INFO: compiled from: TemplateBackupApiImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateBackupApiImpl$restoreHistoryTemplates$1", f = "TemplateBackupApiImpl.kt", l = {616}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ SystemParams $backupSettings;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.data.template.TemplateBackupApiImpl$restoreHistoryTemplates$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TemplateBackupApiImpl.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateBackupApiImpl$restoreHistoryTemplates$1$1", f = "TemplateBackupApiImpl.kt", l = {}, m = "invokeSuspend")
        @SourceDebugExtension
        public static final class C00221 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ SystemParams $backupSettings;
            public int label;
            public final /* synthetic */ TemplateBackupApiImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00221(TemplateBackupApiImpl templateBackupApiImpl, SystemParams systemParams, Continuation continuation) {
                super(2, continuation);
                this.this$0 = templateBackupApiImpl;
                this.$backupSettings = systemParams;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00221(this.this$0, this.$backupSettings, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00221) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                try {
                    String backupHistory2TemplatePath = this.this$0.getBackupHistory2TemplatePath();
                    String backupHistoryTemplatePath = this.this$0.getBackupHistoryTemplatePath();
                    String str = FileUtil.isFileExist(backupHistory2TemplatePath) ? backupHistory2TemplatePath : backupHistoryTemplatePath;
                    Log.i("Keyguard-Editor-TemplateBackupApiImpl", "restoreHistoryTemplates: history is " + str);
                    List<TemplateHistoryConfig> list = (List) GsonSingletonKt.getGSON_SINGLETON().fromJson(new FileReader(str), new TypeToken<List<? extends TemplateHistoryConfig>>() { // from class: com.miui.keyguard.editor.data.template.TemplateBackupApiImpl$restoreHistoryTemplates$1$1$processedConfigs$1
                    }.getType());
                    MatchManager matchManager = new MatchManager(this.$backupSettings);
                    boolean zIsVersionBeforeOS4 = matchManager.isVersionBeforeOS4();
                    DeviceUtil.ScreenInfo screenInfo = new DeviceUtil.ScreenInfo(this.$backupSettings.getScreenWidth(), this.$backupSettings.getScreenHeight(), this.$backupSettings.getScreenDensity(), this.$backupSettings.getDeviceType());
                    DeviceUtil.ScreenInfo currentScreenInfo = DeviceUtil.INSTANCE.getCurrentScreenInfo(this.this$0.context);
                    this.this$0.templateApi.clearHistoryTemplate();
                    if (matchManager.shouldSkipHistoryTemplateRestore()) {
                        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "restoreHistoryTemplates: skip cross-fold-device migration, oldCodename=" + this.$backupSettings.getDeviceCodename());
                        KeyguardEditorMiSightUtil.INSTANCE.reportHistoryAllSkip("cross-fold-device migration, oldDevice=" + this.$backupSettings.getDeviceCodename());
                        FileUtil.delete(backupHistoryTemplatePath);
                        FileUtil.delete(backupHistory2TemplatePath);
                        return Unit.INSTANCE;
                    }
                    Intrinsics.checkNotNull(list);
                    TemplateBackupApiImpl templateBackupApiImpl = this.this$0;
                    SystemParams systemParams = this.$backupSettings;
                    for (TemplateHistoryConfig templateHistoryConfig : list) {
                        String templateId = templateHistoryConfig.getTemplateId();
                        String resourceType = templateHistoryConfig.getResourceType();
                        int hideFlag = templateHistoryConfig.getHideFlag();
                        String originResourcePath = templateHistoryConfig.getOriginResourcePath();
                        String str2 = backupHistory2TemplatePath;
                        StringBuilder sb = new StringBuilder();
                        String str3 = backupHistoryTemplatePath;
                        sb.append("restoreHistoryTemplates: templateId=");
                        sb.append(templateId);
                        sb.append(", resourceType=");
                        sb.append(resourceType);
                        sb.append(", hideFlag=");
                        sb.append(hideFlag);
                        sb.append(", originResourcePath=");
                        sb.append(originResourcePath);
                        Log.d("Keyguard-Editor-TemplateBackupApiImpl", sb.toString());
                        String str4 = "unknown";
                        if (templateHistoryConfig.isCurrent() == 1 || !TemplateHide.INSTANCE.shouldHide(templateHistoryConfig.getHideFlag())) {
                            TemplateHistoryConfig templateHistoryConfigProcessHistoryTemplateClockMigration = templateBackupApiImpl.processHistoryTemplateClockMigration(templateHistoryConfig, matchManager, zIsVersionBeforeOS4, systemParams.getDeviceType(), screenInfo, currentScreenInfo);
                            if (templateHistoryConfigProcessHistoryTemplateClockMigration != null) {
                                templateBackupApiImpl.templateApi.insertHistoryConfig(templateHistoryConfigProcessHistoryTemplateClockMigration);
                            } else {
                                Log.w("Keyguard-Editor-TemplateBackupApiImpl", "restoreHistoryConfig: skip invalid config, templateId=" + templateHistoryConfig.getTemplateId());
                                KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                                String templateId2 = templateHistoryConfig.getTemplateId();
                                if (templateId2 != null) {
                                    str4 = templateId2;
                                }
                                keyguardEditorMiSightUtil.reportHistoryTemplateSkip(str4, "migration failed, config returned null");
                            }
                        } else {
                            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil2 = KeyguardEditorMiSightUtil.INSTANCE;
                            String templateId3 = templateHistoryConfig.getTemplateId();
                            if (templateId3 != null) {
                                str4 = templateId3;
                            }
                            keyguardEditorMiSightUtil2.reportHistoryTemplateSkip(str4, "isCurrent=" + templateHistoryConfig.isCurrent() + " or hidden by hideFlag=" + templateHistoryConfig.getHideFlag());
                        }
                        Log.d("Keyguard-Editor-TemplateBackupApiImpl", "restoreHistoryConfig: " + templateHistoryConfig.getTemplateId());
                        backupHistory2TemplatePath = str2;
                        backupHistoryTemplatePath = str3;
                    }
                    String str5 = backupHistory2TemplatePath;
                    String str6 = backupHistoryTemplatePath;
                    EditorDatabase.Companion companion = EditorDatabase.Companion;
                    if (companion.getInstance(this.this$0.context).getPresetTemplateDao().count() > 0) {
                        companion.getInstance(this.this$0.context).getTemplateHistoryDao().deleteInValid();
                    }
                    FileUtil.delete(str6);
                    FileUtil.delete(str5);
                    return Unit.INSTANCE;
                } catch (Exception e) {
                    Log.e("Keyguard-Editor-TemplateBackupApiImpl", "restoreHistoryTemplates: " + e);
                    KeyguardEditorMiSightUtil.INSTANCE.reportHistoryAllFail(e);
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SystemParams systemParams, Continuation continuation) {
            super(2, continuation);
            this.$backupSettings = systemParams;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TemplateBackupApiImpl.this.new AnonymousClass1(this.$backupSettings, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                C00221 c00221 = new C00221(TemplateBackupApiImpl.this, this.$backupSettings, null);
                this.label = 1;
                if (BuildersKt.withContext(io2, c00221, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public TemplateBackupApiImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.filePathGenerator = new TemplateFilePathGenerator(context);
        this.templateApi = TemplateApi.Companion.getInstance(context);
    }

    public final void adaptToAllInOne(CommonConfig commonConfig, SystemParams systemParams) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) {
            return;
        }
        DeviceUtil.ScreenInfo screenInfo = new DeviceUtil.ScreenInfo(systemParams.getScreenWidth(), systemParams.getScreenHeight(), systemParams.getScreenDensity(), systemParams.getDeviceType());
        DeviceUtil.ScreenInfo currentScreenInfo = DeviceUtil.INSTANCE.getCurrentScreenInfo(this.context);
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "adaptToAllInOne: oldScreen=" + screenInfo + ", newScreen=" + currentScreenInfo);
        commonConfig.getLockscreenInfo().setClockInfo(AllInOneClockMigration.INSTANCE.adaptToNewScreen(clockInfo, screenInfo, currentScreenInfo));
        downgradeClockEffectByDeviceCapability(commonConfig);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public void backupAllTemplates(FullBackupAgent agent) {
        Intrinsics.checkNotNullParameter(agent, "agent");
        try {
            Log.w("Keyguard-Editor-TemplateBackupApiImpl", "begin backupAllTemplates");
            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
            keyguardEditorMiSightUtil.backupStep1Start();
            if (backupCurrentTemplateSettings() != null) {
                keyguardEditorMiSightUtil.backupStep1EndSuccess();
            }
            keyguardEditorMiSightUtil.backupStep2Start();
            backupSettings();
            keyguardEditorMiSightUtil.backupStep2EndSuccess();
            keyguardEditorMiSightUtil.backupStep3Start();
            backupHistoryTemplates(0);
            backupHistoryTemplates(1);
            keyguardEditorMiSightUtil.backupStep3EndSuccess();
            keyguardEditorMiSightUtil.backupStep4Start();
            backupCurrentWallpaper();
            keyguardEditorMiSightUtil.backupStep4EndSuccess();
            Log.w("Keyguard-Editor-TemplateBackupApiImpl", "backupAllTemplates: save to zip");
            keyguardEditorMiSightUtil.backupStep5Start();
            String backupZipFilePath = getBackupZipFilePath();
            FileUtil.zipFolder(new File(getBackupFolder()), new File(backupZipFilePath));
            Log.w("Keyguard-Editor-TemplateBackupApiImpl", "backupAllTemplates: addAttachedFile");
            agent.addAttachedFile(backupZipFilePath);
            keyguardEditorMiSightUtil.backupStep5EndSuccess();
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "backupAllTemplate: " + e);
            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil2 = KeyguardEditorMiSightUtil.INSTANCE;
            keyguardEditorMiSightUtil2.reportBackupZipException(e);
            keyguardEditorMiSightUtil2.backupSessionFinish(false, "backupAllTemplates exception: " + e.getMessage());
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public void backupAllTemplatesCompleted() {
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
        keyguardEditorMiSightUtil.backupStep6Start();
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "backupAllTemplatesCompleted: delete backup files start");
        FileUtil.delete(getBackupZipFilePath());
        FileUtil.delete(getBackupVideoWallpaperPath());
        FileUtil.delete(getBackupWallpaperPath());
        FileUtil.delete(getBackupHomeWallpaperPath());
        FileUtil.delete(getBackupHomeVideoWallpaperPath());
        FileUtil.delete(getBackupHistoryTemplatePath());
        FileUtil.delete(getBackupCurrentTemplatePath());
        FileUtil.delete(getBackupSettingsPath());
        FileUtil.delete(getBackupCropPath());
        FileUtil.delete(getBackupSensorPreviewPath());
        FileUtil.delete(getBackupSensorSmallPreviewPath());
        FileUtil.delete(getBackupOuterVideoPath());
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "backupAllTemplatesCompleted: delete backup files end");
        keyguardEditorMiSightUtil.backupStep6EndSuccess();
    }

    public final void backupCropSubject() {
        FileUtil.copyFile(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT), getBackupCropPath());
    }

    public String backupCurrentTemplateSettings() {
        try {
            Log.i("Keyguard-Editor-TemplateBackupApiImpl", "backupCurrentTemplate: begin");
            CommonConfig commonConfigCurrentTemplate$default = TemplateApi.DefaultImpls.currentTemplate$default(this.templateApi, false, false, true, false, null, false, null, R.styleable.AppCompatTheme_tooltipFrameBackground, null);
            if (commonConfigCurrentTemplate$default == null) {
                KeyguardEditorMiSightUtil.INSTANCE.reportBackupCurrentTemplateFail("currentConfig is null");
                return null;
            }
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "backupCurrentConfig: " + commonConfigCurrentTemplate$default);
            String json = GsonSingletonKt.getGSON_SINGLETON().toJson(commonConfigCurrentTemplate$default);
            String backupCurrentTemplatePath = getBackupCurrentTemplatePath();
            FileUtil.delete(backupCurrentTemplatePath);
            Log.i("Keyguard-Editor-TemplateBackupApiImpl", "backupCurrentTemplate end: " + FileUtil.writeStringToFile(backupCurrentTemplatePath, json));
            return backupCurrentTemplatePath;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "backupCurrentTemplate: ", e);
            KeyguardEditorMiSightUtil.INSTANCE.reportBackupCurrentTemplateFail("exception: " + e.getMessage());
            return null;
        }
    }

    public void backupCurrentWallpaper() {
        this.templateApi.tryClearLocalInvalidWallpaper();
        deleteFileBeforeBackup();
        backupCropSubject();
        new CurrentWallpaperBackupManager().backup(this.context, this.filePathGenerator);
    }

    public String backupHistoryTemplates(int i) {
        try {
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "backupHistoryTemplates: backupVersion = " + i);
            List historyTemplates = i > 0 ? this.templateApi.getHistoryTemplates(true) : this.templateApi.getHistoryTemplates(false);
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "backupHistoryTemplates: " + historyTemplates);
            String json = GsonSingletonKt.getGSON_SINGLETON().toJson(historyTemplates);
            String strGenerateTemplateBackupFilePath = this.filePathGenerator.generateTemplateBackupFilePath(i > 0 ? TemplateFilePathGenerator.TemplateBackupFileType.HISTORY_TEMPLATE_V2 : TemplateFilePathGenerator.TemplateBackupFileType.HISTORY_TEMPLATE);
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "backupHistoryTemplates: path is " + strGenerateTemplateBackupFilePath);
            FileUtil.delete(strGenerateTemplateBackupFilePath);
            FileUtil.writeStringToFile(strGenerateTemplateBackupFilePath, json);
            return strGenerateTemplateBackupFilePath;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "backupHistoryTemplates: " + e);
            KeyguardEditorMiSightUtil.INSTANCE.reportBackupHistoryFail(e);
            return null;
        }
    }

    public final void backupSettingInternal(Map map) {
        TemplateConfig lockscreenInfo;
        Log.d("Keyguard-Editor-TemplateBackupApiImpl", "backupSettingInternal: " + map);
        map.put("deviceType", Integer.valueOf(CommonUtils.INSTANCE.detectType()));
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        map.put("osVersion", Integer.valueOf(deviceUtil.osVersion()));
        map.put("wallpaper_matting_support_2", Boolean.valueOf(TemplateDataUtil.INSTANCE.isSupportMatting(this.context)));
        map.put("wallpaper_effect_type_2", Integer.valueOf(Settings.Secure.getInt(this.context.getContentResolver(), "wallpaper_effect_type_2", 0)));
        DeviceUtil.ScreenInfo currentScreenInfo = deviceUtil.getCurrentScreenInfo(this.context);
        map.put("screenWidth", Integer.valueOf(currentScreenInfo.getWidth()));
        map.put("screenHeight", Integer.valueOf(currentScreenInfo.getHeight()));
        map.put("screenDensity", Float.valueOf(currentScreenInfo.getDensity()));
        map.put("deviceCodename", Build.DEVICE);
        try {
            CommonConfig currentTemplateConfigInternal = this.templateApi.getCurrentTemplateConfigInternal(false);
            WallpaperInfo wallpaperInfo = (currentTemplateConfigInternal == null || (lockscreenInfo = currentTemplateConfigInternal.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo();
            String resourceType = wallpaperInfo != null ? wallpaperInfo.getResourceType() : null;
            String originResourcePath = wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null;
            SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo != null ? wallpaperInfo.getSensorWallpaperPrams() : null;
            boolean zShouldApplyOuterVideoWallpaper = WallpaperController.Companion.shouldApplyOuterVideoWallpaper(wallpaperInfo, new String[0]);
            map.put("defaultSensorWallpaper", Boolean.valueOf(zShouldApplyOuterVideoWallpaper));
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "backupSettingInternal: resourceType=" + resourceType + ", originPath=" + originResourcePath + ", source=" + (wallpaperInfo != null ? wallpaperInfo.getSource() : null) + ", outerVideoPath=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null) + ", outerVideoPreViewPath=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPreViewPath() : null) + ", isPreset=" + zShouldApplyOuterVideoWallpaper);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "backupSettingInternal: error checking default wallpaper", e);
            map.put("defaultSensorWallpaper", Boolean.FALSE);
        }
    }

    public String backupSettings() {
        String backupSettingsPath = getBackupSettingsPath();
        FileUtil.delete(backupSettingsPath);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        backupSettingInternal(linkedHashMap);
        String json = GsonSingletonKt.getGSON_SINGLETON().toJson(linkedHashMap);
        Log.d("Keyguard-Editor-TemplateBackupApiImpl", "backupSettings: " + json);
        FileUtil.writeStringToFile(backupSettingsPath, json);
        return backupSettingsPath;
    }

    public final void cacheCurrentWallpaperAndRollBack(boolean z, boolean z2) {
        Object objM1061constructorimpl;
        String strGenerateTemplateBackupFilePath = this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.BACKUP_CURRENT_CACHE);
        Log.d("Keyguard-Editor-TemplateBackupApiImpl", "cacheCurrentWallpaperAndRollBack rollBack = " + z + ", deleteonly = " + z2);
        try {
            Result.Companion companion = Result.Companion;
            if (z2) {
                FileUtil.deleteFolder(strGenerateTemplateBackupFilePath);
                return;
            }
            String strGenerateCurrentTemplateFolder = this.filePathGenerator.generateCurrentTemplateFolder();
            int i = 0;
            Object objValueOf = null;
            if (z) {
                File[] fileArrListFiles = new File(strGenerateCurrentTemplateFolder).listFiles();
                if (fileArrListFiles != null) {
                    int length = fileArrListFiles.length;
                    while (i < length) {
                        File file = fileArrListFiles[i];
                        if (file != null) {
                            file.delete();
                        }
                        Log.w("Keyguard-Editor-TemplateBackupApiImpl", "cacheCurrentWallpaperAndRollBack: file path is " + (file != null ? file.getPath() : null));
                        i++;
                    }
                }
                FileUtil.copyFolder(new File(strGenerateTemplateBackupFilePath), new File(strGenerateCurrentTemplateFolder));
                objValueOf = Boolean.valueOf(FileUtil.deleteFolder(strGenerateTemplateBackupFilePath));
            } else {
                FileUtil.deleteFolder(strGenerateTemplateBackupFilePath);
                FileUtil.makeDirExists(strGenerateTemplateBackupFilePath);
                FileUtil.copyFolder(new File(strGenerateCurrentTemplateFolder), new File(strGenerateTemplateBackupFilePath));
                File[] fileArrListFiles2 = new File(strGenerateCurrentTemplateFolder).listFiles();
                if (fileArrListFiles2 != null) {
                    int length2 = fileArrListFiles2.length;
                    while (i < length2) {
                        File file2 = fileArrListFiles2[i];
                        if (file2 != null) {
                            file2.delete();
                        }
                        Log.w("Keyguard-Editor-TemplateBackupApiImpl", "cacheCurrentWallpaperAndRollBack: file path is " + (file2 != null ? file2.getPath() : null));
                        i++;
                    }
                    objValueOf = Unit.INSTANCE;
                }
            }
            objM1061constructorimpl = Result.m1061constructorimpl(objValueOf);
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl == null) {
                return;
            }
            Log.w("Keyguard-Editor-TemplateBackupApiImpl", "cacheCurrentWallpaperAndRollBack: ", thM1063exceptionOrNullimpl);
            if (z) {
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperRollbackFail(thM1063exceptionOrNullimpl);
            }
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
    }

    public final void dealFallbackToDefaultAllInOne(CommonConfig commonConfig) {
        ClockInfo clockInfo = commonConfig.getLockscreenInfo().getClockInfo();
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "dealFallbackToDefaultAllInOne: converting templateId=" + clockInfo.getTemplateId() + " to default AllInOne");
        ClockBean clockBean = new ClockBean("all_in_one");
        clockBean.setClockEffect(1);
        clockBean.setStyle(1);
        clockBean.setClassicLine1(miui.os.Build.IS_INTERNATIONAL_BUILD ? 100 : 103);
        clockBean.setAutoPrimaryColor(true);
        clockBean.setAutoSecondaryColor(true);
        AllInOneUtil.initClockParams(clockBean, this.context);
        TemplateConfig lockscreenInfo = commonConfig.getLockscreenInfo();
        int style = clockBean.getStyle();
        int clockEffect = clockBean.getClockEffect();
        int classicLine1 = clockBean.getClassicLine1();
        int clockWeight = clockBean.getClockWeight();
        lockscreenInfo.setClockInfo(clockInfo.copy(((-1) & 1) != 0 ? clockInfo.templateId : "all_in_one", ((-1) & 2) != 0 ? clockInfo.primaryColor : 0, ((-1) & 4) != 0 ? clockInfo.secondaryColor : 0, ((-1) & 8) != 0 ? clockInfo.blendColor : 0, ((-1) & 16) != 0 ? clockInfo.secondaryBlendColor : 0, ((-1) & 32) != 0 ? clockInfo.clockEffect : clockEffect, ((-1) & 64) != 0 ? clockInfo.style : style, ((-1) & 128) != 0 ? clockInfo.enableDiffusion : false, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? clockInfo.classicLine1 : classicLine1, ((-1) & 512) != 0 ? clockInfo.classicLine2 : 0, ((-1) & 1024) != 0 ? clockInfo.classicLine3 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? clockInfo.classicLine4 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? clockInfo.classicLine5 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? clockInfo.primaryFontColorPanelId : 0, ((-1) & 16384) != 0 ? clockInfo.secondaryFontColorPanelId : 0, ((-1) & 32768) != 0 ? clockInfo.classicSignature : null, ((-1) & 65536) != 0 ? clockInfo.signatureLine1 : null, ((-1) & 131072) != 0 ? clockInfo.signatureLine2 : null, ((-1) & 262144) != 0 ? clockInfo.signatureLine3 : null, ((-1) & 524288) != 0 ? clockInfo.isAutoPrimaryColor : clockBean.isAutoPrimaryColor(), ((-1) & 1048576) != 0 ? clockInfo.isAutoSecondaryColor : clockBean.isAutoSecondaryColor(), ((-1) & 2097152) != 0 ? clockInfo.isDiffHourMinuteColor : false, ((-1) & 4194304) != 0 ? clockInfo.primaryColorValueInfo : null, ((-1) & 8388608) != 0 ? clockInfo.secondaryColorValueInfo : null, ((-1) & 16777216) != 0 ? clockInfo.isDarkClockArea : false, ((-1) & 33554432) != 0 ? clockInfo.clockWeight : clockWeight, ((-1) & 67108864) != 0 ? clockInfo.extraFlag : 0, ((-1) & 134217728) != 0 ? clockInfo.clockTopRatio : clockBean.getClockTopRatio(), ((-1) & 268435456) != 0 ? clockInfo.clockBottomRatio : clockBean.getClockBottomRatio(), ((-1) & 536870912) != 0 ? clockInfo.timeWidth : clockBean.getTimeWidth(), ((-1) & 1073741824) != 0 ? clockInfo.timeHeight : clockBean.getTimeHeight(), ((-1) & Integer.MIN_VALUE) != 0 ? clockInfo.clockDepthType : AllInOneClockStyle.toRegionInt(clockBean.getClockDepthType()), (255 & 1) != 0 ? clockInfo.glassTransparency : 0.0f, (255 & 2) != 0 ? clockInfo.fontStyle : clockBean.getFontStyle(), (255 & 4) != 0 ? clockInfo.hollowStyle : clockBean.getHollowStyle(), (255 & 8) != 0 ? clockInfo.isDoubleRow : clockBean.isDoubleRow(), (255 & 16) != 0 ? clockInfo.isColonShow : clockBean.isColonShow(), (255 & 32) != 0 ? clockInfo.isSkipScreenTransform : clockBean.isSkipScreenTransform(), (255 & 64) != 0 ? clockInfo.userModifiedPrimaryColor : false, (255 & 128) != 0 ? clockInfo.userModifiedSecondaryColor : false));
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "dealFallbackToDefaultAllInOne: done, style=" + clockBean.getStyle() + ", timeWidth=" + clockBean.getTimeWidth() + ", timeHeight=" + clockBean.getTimeHeight());
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public void deleteBackupZipFile(String zipPath) {
        Intrinsics.checkNotNullParameter(zipPath, "zipPath");
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "deleteBackupZipFile: ");
        try {
            FileUtil.delete(getBackupSettingsPath());
            FileUtil.delete(zipPath);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "deleteBackupZipFile: " + e);
        }
    }

    public final void deleteFileBeforeBackup() {
        FileUtil.delete(getBackupWallpaperPath());
        FileUtil.delete(getBackupVideoWallpaperPath());
        FileUtil.delete(getBackupCropPath());
        FileUtil.delete(getBackupHomeWallpaperPath());
        FileUtil.delete(getBackupHomeVideoWallpaperPath());
        FileUtil.delete(getBackupSensorPreviewPath());
        FileUtil.delete(getBackupSensorSmallPreviewPath());
        FileUtil.delete(getBackupOuterVideoPath());
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001f  */
    public final void downgradeClockEffectByDeviceCapability(CommonConfig commonConfig) {
        int i;
        int clockEffect = commonConfig.getLockscreenInfo().getClockInfo().getClockEffect();
        boolean z = DeviceConfig.SUPPORT_BACKGROUND_BLUR;
        boolean zSupportGlassEffect = DeviceConfig.supportGlassEffect();
        if (!ClockEffectUtils.isGlassType(clockEffect) || zSupportGlassEffect) {
            if ((ClockEffectUtils.isBlurMixType(clockEffect) || ClockEffectUtils.isDifferenceType(clockEffect) || ClockEffectUtils.isGradualType(clockEffect)) && !z) {
                i = 0;
            } else {
                i = clockEffect;
            }
        } else if (z) {
            i = 1;
        } else {
            i = 0;
        }
        if (i == clockEffect) {
            return;
        }
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "downgradeClockEffectByDeviceCapability: capability downgrade clockEffect=" + clockEffect + " -> " + i + " (supportBlur=" + z + ", supportGlass=" + zSupportGlassEffect + ")");
        commonConfig.getLockscreenInfo().getClockInfo().setClockEffect(i);
        if (i == 0) {
            commonConfig.getLockscreenInfo().getClockInfo().setBlendColor(0);
            commonConfig.getLockscreenInfo().getClockInfo().setSecondaryBlendColor(0);
        }
    }

    public final String getBackupCropPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.CROP_SUBJECT);
    }

    public final String getBackupCurrentTemplatePath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.CURRENT_TEMPLATE);
    }

    public String getBackupFolder() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.TEMPLATE_BACKUP_FOLDER);
    }

    public final String getBackupHistory2TemplatePath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.HISTORY_TEMPLATE_V2);
    }

    public final String getBackupHistoryTemplatePath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.HISTORY_TEMPLATE);
    }

    public final String getBackupHomeVideoWallpaperPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_HOME_VIDEO);
    }

    public final String getBackupHomeWallpaperPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_HOME);
    }

    public final String getBackupOuterVideoPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.OUTER_VIDEO_WALLPAPER_BACKUP);
    }

    public final String getBackupSensorPreviewPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SENSOR_WALLPAPER_PREVIEW);
    }

    public final String getBackupSensorSmallPreviewPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public SystemParams getBackupSettings() {
        String backupSettingsPath = getBackupSettingsPath();
        if (!FileUtil.isFileExist(backupSettingsPath)) {
            Log.w("Keyguard-Editor-TemplateBackupApiImpl", "getBackupSettings: settings file not exist: " + backupSettingsPath + ", use current device params as fallback");
            return getDefaultSystemParams();
        }
        try {
            Map map = (Map) GsonSingletonKt.getGSON_SINGLETON().fromJson((Reader) new FileReader(backupSettingsPath), (Type) Map.class);
            Object orDefault = map.getOrDefault("deviceType", 0);
            int iIntValue = orDefault instanceof Number ? ((Number) orDefault).intValue() : 0;
            Object orDefault2 = map.getOrDefault("osVersion", 0);
            int iIntValue2 = orDefault2 instanceof Number ? ((Number) orDefault2).intValue() : 0;
            Object orDefault3 = map.getOrDefault("screenWidth", 0);
            int iIntValue3 = orDefault3 instanceof Number ? ((Number) orDefault3).intValue() : 0;
            Object orDefault4 = map.getOrDefault("screenHeight", 0);
            int iIntValue4 = orDefault4 instanceof Number ? ((Number) orDefault4).intValue() : 0;
            Object orDefault5 = map.getOrDefault("screenDensity", Float.valueOf(0.0f));
            float fFloatValue = orDefault5 instanceof Number ? ((Number) orDefault5).floatValue() : 0.0f;
            Object orDefault6 = map.getOrDefault("defaultSensorWallpaper", Boolean.FALSE);
            boolean zBooleanValue = orDefault6 instanceof Boolean ? ((Boolean) orDefault6).booleanValue() : false;
            Object orDefault7 = map.getOrDefault("deviceCodename", "");
            Object obj = orDefault7 instanceof String ? orDefault7 : "";
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "getBackupSettings: deviceType=" + iIntValue + ", osVersion=" + iIntValue2 + ", screenWidth=" + iIntValue3 + ", screenHeight=" + iIntValue4 + ", screenDensity=" + fFloatValue + ", isDefaultSensorWallpaper=" + zBooleanValue + ", deviceCodename=" + obj);
            return new SystemParams(iIntValue, iIntValue2, iIntValue3, iIntValue4, fFloatValue, zBooleanValue, (String) obj);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "getBackupSettings: exception during reading settings file, use current device params as fallback", e);
            return getDefaultSystemParams();
        }
    }

    public final String getBackupSettingsPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SETTINGS);
    }

    public final String getBackupVideoWallpaperPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_VIDEO);
    }

    public final String getBackupWallpaperPath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER);
    }

    public String getBackupZipFilePath() {
        int iOsVersion = DeviceUtil.INSTANCE.osVersion();
        Log.d("Keyguard-Editor-TemplateBackupApiImpl", "getBackupZipFilePath: current version=" + iOsVersion);
        return getBackupZipFilePathByVersion(iOsVersion);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public String getBackupZipFilePathByVersion(int i) {
        String strGenerateZipFileName = BackupVersionManager.INSTANCE.generateZipFileName(i);
        String str = StringsKt.substringBeforeLast$default(this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.VERSION_BACKUP_ZIP), "/", null, 2, null) + "/" + strGenerateZipFileName;
        Log.d("Keyguard-Editor-TemplateBackupApiImpl", "getBackupZipFilePathByVersion: version=" + i + ", path=" + str);
        return str;
    }

    public final SystemParams getDefaultSystemParams() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        DeviceUtil.ScreenInfo currentScreenInfo = deviceUtil.getCurrentScreenInfo(this.context);
        int deviceType = currentScreenInfo.getDeviceType();
        int iOsVersion = deviceUtil.osVersion();
        int width = currentScreenInfo.getWidth();
        int height = currentScreenInfo.getHeight();
        float density = currentScreenInfo.getDensity();
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        SystemParams systemParams = new SystemParams(deviceType, iOsVersion, width, height, density, false, DEVICE);
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "getDefaultSystemParams: deviceType=" + systemParams.getDeviceType() + ", osVersion=" + systemParams.getOsVersion() + ", screenWidth=" + systemParams.getScreenWidth() + ", screenHeight=" + systemParams.getScreenHeight() + ", screenDensity=" + systemParams.getScreenDensity() + ", isDefaultSensorWallpaper=" + systemParams.isDefaultSensorWallpaper() + ", deviceCodename=" + systemParams.getDeviceCodename());
        return systemParams;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public String getOldBackupZipFilePath() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.BACKUP_ZIP);
    }

    public String getRestoreFolder() {
        return this.filePathGenerator.generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.TEMPLATE_RESTORE_FOLDER);
    }

    public final boolean isWallpaperFileExist() {
        String backupWallpaperPath = getBackupWallpaperPath();
        String backupVideoWallpaperPath = getBackupVideoWallpaperPath();
        boolean zIsFileExist = FileUtil.isFileExist(backupWallpaperPath);
        boolean zIsFileExist2 = FileUtil.isFileExist(backupVideoWallpaperPath);
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "isWallpaperFileExist: wallpaperExist = " + zIsFileExist + ",videoWallpaperExist = " + zIsFileExist2);
        return zIsFileExist || zIsFileExist2;
    }

    public final TemplateHistoryConfig processHistoryTemplateClockMigration(TemplateHistoryConfig templateHistoryConfig, MatchManager matchManager, boolean z, int i, DeviceUtil.ScreenInfo screenInfo, DeviceUtil.ScreenInfo screenInfo2) throws Throwable {
        String configUri = templateHistoryConfig.getConfigUri();
        if (StringsKt.isBlank(configUri) || Intrinsics.areEqual(configUri, "UNDEFINE") || !FileUtil.isFileExist(configUri)) {
            Log.w("Keyguard-Editor-TemplateBackupApiImpl", "processHistoryTemplateClockMigration: skip, invalid configUri=" + configUri);
            return templateHistoryConfig;
        }
        try {
            String fileFromReader = FileUtil.readFileFromReader(new FileReader(configUri));
            CommonConfig.Companion companion = CommonConfig.Companion;
            Intrinsics.checkNotNull(fileFromReader);
            CommonConfig commonConfigFromJson$default = CommonConfig.Companion.fromJson$default(companion, fileFromReader, null, this.context, 2, null);
            ClockInfo clockInfo = commonConfigFromJson$default.getLockscreenInfo().getClockInfo();
            if (!matchManager.isMatchAllInOne(clockInfo.getTemplateId(), clockInfo.getStyle())) {
                Log.d("Keyguard-Editor-TemplateBackupApiImpl", "processHistoryTemplateClockMigration: no need to migrate, templateId=" + clockInfo.getTemplateId());
                return templateHistoryConfig;
            }
            if (!z) {
                commonConfigFromJson$default.getLockscreenInfo().setClockInfo(AllInOneClockMigration.INSTANCE.adaptToNewScreen(clockInfo, screenInfo, screenInfo2));
            }
            if (!FileUtil.atomicWriteToFile(configUri, GsonSingletonKt.getGSON_SINGLETON().toJson(commonConfigFromJson$default))) {
                Log.e("Keyguard-Editor-TemplateBackupApiImpl", "processHistoryTemplateClockMigration: failed to write config, skip this history template");
                return null;
            }
            ClockInfo clockInfo2 = commonConfigFromJson$default.getLockscreenInfo().getClockInfo();
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "processHistoryTemplateClockMigration: updated clock, templateId=" + clockInfo2.getTemplateId() + ", style=" + clockInfo2.getStyle() + ", fontStyle=" + clockInfo2.getFontStyle());
            return TemplateHistoryConfig.copy$default(templateHistoryConfig, clockInfo2.getTemplateId(), null, 0, null, null, null, null, null, null, false, false, null, null, 0L, null, 0, 0, 0, 0, false, 1048574, null);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "processHistoryTemplateClockMigration: exception during migration, skip this history template", e);
            return null;
        }
    }

    public final void restoreCropSubject() {
        String backupCropPath = getBackupCropPath();
        String strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT);
        FileUtil.copyFile(backupCropPath, strGenerateCurrentTemplateFilePath);
        FileUtil.chmod(strGenerateCurrentTemplateFilePath, 511);
        FileUtil.delete(backupCropPath);
    }

    public final CommonConfig restoreCurrentTemplate(String str, SystemParams systemParams) throws Throwable {
        try {
            String fileFromReader = FileUtil.readFileFromReader(new FileReader(str));
            CommonConfig.Companion companion = CommonConfig.Companion;
            Intrinsics.checkNotNull(fileFromReader);
            CommonConfig commonConfigFromJson$default = CommonConfig.Companion.fromJson$default(companion, fileFromReader, null, this.context, 2, null);
            MatchManager matchManager = new MatchManager(systemParams);
            boolean zIsVersionBeforeOS4 = matchManager.isVersionBeforeOS4();
            boolean zIsMatchAllInOne = matchManager.isMatchAllInOne(commonConfigFromJson$default.getLockscreenInfo().getClockInfo().getTemplateId(), commonConfigFromJson$default.getLockscreenInfo().getClockInfo().getStyle());
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "restoreCurrentTemplate: beforeOS4=" + zIsVersionBeforeOS4 + ", isMatchAllInOne=" + zIsMatchAllInOne);
            if (zIsMatchAllInOne && !zIsVersionBeforeOS4) {
                if (matchManager.shouldFallbackToDefaultAllInOne(commonConfigFromJson$default.getLockscreenInfo().getClockInfo().getTemplateId(), commonConfigFromJson$default.getLockscreenInfo().getClockInfo().getStyle())) {
                    dealFallbackToDefaultAllInOne(commonConfigFromJson$default);
                } else {
                    adaptToAllInOne(commonConfigFromJson$default, systemParams);
                }
            }
            TemplateApi.DefaultImpls.updateCurrentTemplate$default(this.templateApi, commonConfigFromJson$default, "restore current template", false, false, 4, null);
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "restoreCurrentTemplate: " + commonConfigFromJson$default);
            FileUtil.delete(str);
            return commonConfigFromJson$default;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "restoreCurrentTemplate: " + e, e);
            KeyguardEditorMiSightUtil.INSTANCE.reportCurrentTemplateException(e);
            return null;
        }
    }

    public final void restoreHistoryTemplates(SystemParams systemParams) {
        BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(systemParams, null), 1, null);
    }

    public void restoreSettings(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "restoreSettings: filePath=" + filePath);
        try {
            Map map = (Map) GsonSingletonKt.getGSON_SINGLETON().fromJson((Reader) new FileReader(filePath), (Type) Map.class);
            Log.i("Keyguard-Editor-TemplateBackupApiImpl", "restoreSettings: settings is " + map);
            Intrinsics.checkNotNull(map);
            restoreSettingsInternal(map);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "restoreSettings: ", e);
            KeyguardEditorMiSightUtil.INSTANCE.reportSettingsParseFail(e);
        } finally {
            FileUtil.delete(filePath);
        }
    }

    public final void restoreSettingsInternal(Map map) {
        Object orDefault = map.getOrDefault("wallpaper_matting_support_2", Boolean.FALSE);
        if (orDefault != null) {
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "restoreSettingsInternal: wallpaper_matting_support_2=" + orDefault);
            if (orDefault instanceof Boolean) {
                TemplateDataUtil.INSTANCE.setSupportMatting(this.context, ((Boolean) orDefault).booleanValue());
            }
        }
        Object orDefault2 = map.getOrDefault("wallpaper_effect_type_2", 0);
        if (orDefault2 != null) {
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "restoreSettingsInternal: wallpaper_effect_type_2=" + orDefault2);
            if (orDefault2 instanceof Number) {
                Settings.Secure.putInt(this.context.getContentResolver(), "wallpaper_effect_type_2", ((Number) orDefault2).intValue());
            }
        }
    }

    public final boolean shouldRestoreCurrent(boolean z, boolean z2) {
        TemplateHistoryConfig current;
        if (z2) {
            Log.i("Keyguard-Editor-TemplateBackupApiImpl", "shouldRestoreCurrent: skip Q18->Fold preset default sensor wallpaper");
            return false;
        }
        boolean zIsWallpaperFileExist = isWallpaperFileExist();
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "shouldRestoreCurrent: " + zIsWallpaperFileExist + ", v2: " + z);
        if (!zIsWallpaperFileExist) {
            return false;
        }
        if (!z || (current = EditorDatabase.Companion.getInstance(this.context).getTemplateHistoryDao().getCurrent()) == null) {
            return true;
        }
        boolean zQuerySupport = CrossListDataModel.Companion.querySupport(this.context, current.getTemplateName(), current.getTemplateId());
        if (!zQuerySupport && AllInOneClockMigration.INSTANCE.shouldUpgradeToAllInOne(current.getTemplateId(), 0, DeviceUtil.INSTANCE.isQ18Device())) {
            Log.d("Keyguard-Editor-TemplateBackupApiImpl", "shouldRestoreCurrent: querySupport=false but shouldUpgradeToAllInOne=true, templateId=" + current.getTemplateId());
            zQuerySupport = true;
        }
        boolean zShouldHide = TemplateHide.INSTANCE.shouldHide(current.getHideFlag());
        boolean z3 = current.getMagicType() != 100000 || FileUtil.isSupportVideoDepth();
        boolean z4 = Intrinsics.areEqual(current.getResourceType(), "sensor") && !CommonUtils.INSTANCE.isFlipOrFoldDevice();
        Log.w("Keyguard-Editor-TemplateBackupApiImpl", "shouldRestoreCurrent: support = " + zQuerySupport + ",shouldHide = " + zShouldHide + ",shouldHideDepthVideo = " + z3 + ",shouldHideSensor = " + z4);
        return zQuerySupport && !zShouldHide && z3 && !z4;
    }

    public final boolean shouldSkipCurrentTemplateRestore(SystemParams systemParams) {
        if (systemParams == null || !systemParams.isDefaultSensorWallpaper()) {
            return false;
        }
        String deviceCodename = systemParams.getDeviceCodename();
        String str = Build.DEVICE;
        boolean z = !Intrinsics.areEqual(deviceCodename, str);
        Log.d("Keyguard-Editor-TemplateBackupApiImpl", "shouldSkipCurrentTemplateRestore: backup is preset default sensor wallpaper, oldDeviceCodename=" + systemParams.getDeviceCodename() + ", currentDeviceCodename=" + str + ", shouldSkip=" + z);
        return z;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public void tryRestoreAllTemplates(String zipPath) throws Throwable {
        Intrinsics.checkNotNullParameter(zipPath, "zipPath");
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "tryRestoreAllTemplates: ");
        try {
            cacheCurrentWallpaperAndRollBack(false, false);
            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
            keyguardEditorMiSightUtil.restoreStep3Start();
            FileUtil.unzip(new File(zipPath), new File(getRestoreFolder()), null);
            keyguardEditorMiSightUtil.restoreStep3EndSuccess();
            boolean zIsFileExist = FileUtil.isFileExist(getBackupHistory2TemplatePath());
            SystemParams backupSettings = getBackupSettings();
            boolean zShouldSkipCurrentTemplateRestore = shouldSkipCurrentTemplateRestore(backupSettings);
            keyguardEditorMiSightUtil.restoreStep4Start();
            restoreHistoryTemplates(backupSettings);
            keyguardEditorMiSightUtil.restoreStep4EndSuccess();
            if (shouldRestoreCurrent(zIsFileExist, zShouldSkipCurrentTemplateRestore)) {
                Log.w("Keyguard-Editor-TemplateBackupApiImpl", "tryRestoreAllTemplates: support");
                restoreSettings(getBackupSettingsPath());
                keyguardEditorMiSightUtil.restoreStep5Start();
                CommonConfig commonConfigRestoreCurrentTemplate = restoreCurrentTemplate(getBackupCurrentTemplatePath(), backupSettings);
                if (commonConfigRestoreCurrentTemplate != null) {
                    keyguardEditorMiSightUtil.restoreStep5EndSuccess();
                }
                tryRestoreCurrentWallpaper(commonConfigRestoreCurrentTemplate, backupSettings);
                cacheCurrentWallpaperAndRollBack(false, true);
            } else {
                keyguardEditorMiSightUtil.reportCurrentTemplateSkip("shouldRestoreCurrent=false, v2=" + zIsFileExist + ", skipCurrentTemplateRestore=" + zShouldSkipCurrentTemplateRestore);
                cacheCurrentWallpaperAndRollBack(true, false);
                keyguardEditorMiSightUtil.reportWallpaperRollback();
                Log.w("Keyguard-Editor-TemplateBackupApiImpl", "tryRestoreAllTemplates: not support");
            }
            keyguardEditorMiSightUtil.restoreStep7Start();
            FileUtil.delete(zipPath);
            keyguardEditorMiSightUtil.restoreStep7EndSuccess();
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "tryRestoreAllTemplates: " + e, e);
            KeyguardEditorMiSightUtil.INSTANCE.restoreSessionFinish(false, "tryRestoreAllTemplates exception: " + e.getMessage());
        }
    }

    public void tryRestoreCurrentWallpaper(CommonConfig commonConfig, SystemParams systemParams) {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo2;
        SignatureInfo signatureInfo;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo4;
        ClockInfo clockInfo2;
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
        keyguardEditorMiSightUtil.restoreStep6Start();
        WallpaperController.Companion companion = WallpaperController.Companion;
        WallpaperController companion2 = companion.getInstance(this.context);
        if (shouldSkipCurrentTemplateRestore(systemParams)) {
            Log.i("Keyguard-Editor-TemplateBackupApiImpl", "tryRestoreCurrentWallpaper: Q18->Fold preset default sensor wallpaper, skip wallpaper restore");
            keyguardEditorMiSightUtil.reportWallpaperSkip("Q18->Fold preset default sensor wallpaper");
            return;
        }
        Integer numValueOf = null;
        String templateId = (commonConfig == null || (lockscreenInfo4 = commonConfig.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo4.getClockInfo()) == null) ? null : clockInfo2.getTemplateId();
        Integer numValueOf2 = (commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo3.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo.getStyle());
        if (commonConfig != null && (lockscreenInfo2 = commonConfig.getLockscreenInfo()) != null && (signatureInfo = lockscreenInfo2.getSignatureInfo()) != null) {
            numValueOf = Integer.valueOf(signatureInfo.getAlignment());
        }
        companion2.setPickWallpaperColorInfo(companion.getPickWallpaperColorInfo(templateId, numValueOf2, numValueOf));
        restoreCropSubject();
        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) != null) {
            new CurrentWallpaperBackupManager().restoreLock(this.context, wallpaperInfo2, this.filePathGenerator);
        }
        if (commonConfig != null && (homeInfo = commonConfig.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
            new CurrentWallpaperBackupManager().restoreHome(this.context, wallpaperInfo, this.filePathGenerator);
        }
        keyguardEditorMiSightUtil.restoreStep6EndSuccess();
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateBackupApi
    public void unzipSettingBackupFile(ParcelFileDescriptor fd, String zipPath) throws Throwable {
        Intrinsics.checkNotNullParameter(fd, "fd");
        Intrinsics.checkNotNullParameter(zipPath, "zipPath");
        Log.i("Keyguard-Editor-TemplateBackupApiImpl", "unzipSettingBackupFile: ");
        try {
            FileUtil.writeFdToFile(fd, zipPath);
            FileUtil.unzip(new File(zipPath), new File(getRestoreFolder()), CollectionsKt.arrayListOf(this.filePathGenerator.generateTemplateFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SETTINGS)));
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateBackupApiImpl", "unzipSettingBackupFile: " + e);
            KeyguardEditorMiSightUtil.INSTANCE.reportUnzipFail(e);
        }
    }
}

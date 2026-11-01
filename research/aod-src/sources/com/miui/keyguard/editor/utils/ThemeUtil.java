package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.segment.UtilsKt;
import com.miui.maml.R;
import java.io.File;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: ThemeUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ThemeUtil {
    public static final int WALLPAPER_TYPE_IMAGE = 0;
    public static final ThemeUtil INSTANCE = new ThemeUtil();
    public static final String HISTORY_WALLPAPER_AUTHORITY = "content://com.android.thememanager.save_history_wallpaper";
    public static final String HISTORY_WALLPAPER_METHOD = "save_wallpaper";
    public static final String HISTORY_WALLPAPER_DIR = "/data/system/theme_magic/users/" + UserHandle.myUserId() + "/history";
    public static final String WALLPAPER_DIR = "/data/system/theme_magic/users/" + UserHandle.myUserId() + "/wallpaper";
    public static final int WALLPAPER_TYPE_NONE = -1;
    public static final int WALLPAPER_TYPE_VIDEO = 1;
    public static final int WALLPAPER_TYPE_IMAGE_FLIP = 2;
    public static final int WALLPAPER_TYPE_VIDEO_FLIP = 3;
    public static final String KEY_WALLPAPER_PATH = "resourcePath";
    public static final String KEY_MAKS_PATH = "mask_path";
    public static final String KEY_SUPPORT_SUBJECT = "support_subject";
    public static final String KEY_WALLPAPER_ID = "wallpaper_id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_MATRIX = "matirx";
    public static final String KEY_EXTRA_INFO = "extra_info";

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.ThemeUtil$addHistoryWallpaper$1, reason: invalid class name */
    /* JADX INFO: compiled from: ThemeUtil.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.ThemeUtil$addHistoryWallpaper$1", f = "ThemeUtil.kt", l = {R.styleable.AppCompatTheme_listPopupWindowStyle}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ String $subjectSnapshot;
        public final /* synthetic */ WallpaperInfo $wallpaperInfo;
        public Object L$0;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.ThemeUtil$addHistoryWallpaper$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ThemeUtil.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.utils.ThemeUtil$addHistoryWallpaper$1$1", f = "ThemeUtil.kt", l = {}, m = "invokeSuspend")
        public static final class C00351 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Integer>, Object> {
            public final /* synthetic */ Bundle $extras;
            public final /* synthetic */ String $subjectSnapshot;
            public final /* synthetic */ WallpaperInfo $wallpaperInfo;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00351(WallpaperInfo wallpaperInfo, String str, Bundle bundle, Continuation continuation) {
                super(2, continuation);
                this.$wallpaperInfo = wallpaperInfo;
                this.$subjectSnapshot = str;
                this.$extras = bundle;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00351(this.$wallpaperInfo, this.$subjectSnapshot, this.$extras, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00351) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                String imagePath;
                float[] matrixArr;
                String originResourcePath;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                FileUtil.makeDirExists(ThemeUtil.HISTORY_WALLPAPER_DIR);
                FileUtil.chmod(ThemeUtil.HISTORY_WALLPAPER_DIR, 511);
                ThemeUtil themeUtil = ThemeUtil.INSTANCE;
                HistoricalWallpaperExtraInfo extraInfo = themeUtil.getExtraInfo(this.$wallpaperInfo);
                UUID uuidRandomUUID = UUID.randomUUID();
                String strJoinToString$default = null;
                if ((extraInfo != null ? extraInfo.getImagePath() : null) == null) {
                    imagePath = ThemeUtil.HISTORY_WALLPAPER_DIR + File.separator + uuidRandomUUID;
                    if (FileUtil.isFileExist(this.$wallpaperInfo.getOriginResourcePath())) {
                        TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
                        if (companion.isPresetPath(this.$wallpaperInfo.getSource())) {
                            String source = this.$wallpaperInfo.getSource();
                            Intrinsics.checkNotNull(source);
                            originResourcePath = companion.getPresetWallpaperAbsolutePath(source);
                        } else {
                            originResourcePath = this.$wallpaperInfo.getOriginResourcePath();
                            if (originResourcePath == null) {
                                originResourcePath = this.$wallpaperInfo.getSource();
                            }
                        }
                        FileUtil.fastCopy(originResourcePath, imagePath, false);
                    } else {
                        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
                        Bitmap currentWallpaper = this.$wallpaperInfo.getCurrentWallpaper();
                        Intrinsics.checkNotNull(currentWallpaper);
                        BitmapUtil.toWebp$default(bitmapUtil, currentWallpaper, imagePath, false, 4, null);
                    }
                    FileUtil.chmod(imagePath, 511);
                } else {
                    imagePath = extraInfo.getImagePath();
                }
                String strCalculateMD5 = (imagePath == null || !FileUtil.isFileExist(imagePath)) ? null : FileUtil.calculateMD5(new File(imagePath));
                String str = ThemeUtil.HISTORY_WALLPAPER_DIR + File.separator + uuidRandomUUID + "_mask";
                String mask = this.$subjectSnapshot;
                if (mask == null) {
                    String subject = this.$wallpaperInfo.getSubject();
                    if (subject == null || !StringsKt.endsWith$default(subject, TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK.getFileName(), false, 2, (Object) null)) {
                        TemplateFilePathGenerator.Companion companion2 = TemplateFilePathGenerator.Companion;
                        if (companion2.isPresetPath(this.$wallpaperInfo.getMask())) {
                            String mask2 = this.$wallpaperInfo.getMask();
                            Intrinsics.checkNotNull(mask2);
                            mask = companion2.getPresetWallpaperAbsolutePath(mask2);
                        } else {
                            mask = this.$wallpaperInfo.getMask();
                        }
                    } else {
                        mask = this.$wallpaperInfo.getSubject();
                    }
                }
                if (!FileUtil.fastCopy(mask, str, false)) {
                    str = null;
                }
                boolean zIsDepthVideo = Intrinsics.areEqual(this.$wallpaperInfo.getResourceType(), "video") ? this.$wallpaperInfo.isDepthVideo() : this.$wallpaperInfo.getSupportSubject();
                this.$extras.putString(ThemeUtil.KEY_WALLPAPER_PATH, imagePath);
                this.$extras.putString(ThemeUtil.KEY_MAKS_PATH, str);
                this.$extras.putBoolean(ThemeUtil.KEY_SUPPORT_SUBJECT, zIsDepthVideo);
                this.$extras.putString(ThemeUtil.KEY_WALLPAPER_ID, strCalculateMD5);
                this.$extras.putInt(ThemeUtil.KEY_TYPE, themeUtil.getWallpaperType(this.$wallpaperInfo));
                Bundle bundle = this.$extras;
                String str2 = ThemeUtil.KEY_MATRIX;
                WallpaperPositionInfo positionInfo = this.$wallpaperInfo.getPositionInfo();
                if (positionInfo != null && (matrixArr = positionInfo.getMatrixArr()) != null) {
                    strJoinToString$default = ArraysKt.joinToString$default(matrixArr, (CharSequence) null, "{", "}", 0, (CharSequence) null, (Function1) null, 57, (Object) null);
                }
                bundle.putString(str2, strJoinToString$default);
                this.$extras.putString(ThemeUtil.KEY_EXTRA_INFO, GsonSingletonKt.getGSON_SINGLETON().toJson(extraInfo));
                return Boxing.boxInt(Log.e("ThemeUtil", "addHistoryWallpaper: wallpaperInfo=" + this.$wallpaperInfo + " extras=" + this.$extras));
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, String str, WallpaperInfo wallpaperInfo, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$subjectSnapshot = str;
            this.$wallpaperInfo = wallpaperInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$context, this.$subjectSnapshot, this.$wallpaperInfo, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Bundle bundle;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Bundle bundle2 = new Bundle();
                    CoroutineDispatcher io2 = Dispatchers.getIO();
                    C00351 c00351 = new C00351(this.$wallpaperInfo, this.$subjectSnapshot, bundle2, null);
                    this.L$0 = bundle2;
                    this.label = 1;
                    if (BuildersKt.withContext(io2, c00351, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    bundle = bundle2;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    bundle = (Bundle) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                Log.d("ThemeUtil", "addHistoryWallpaper: resultBundle=" + this.$context.getContentResolver().call(Uri.parse(ThemeUtil.HISTORY_WALLPAPER_AUTHORITY), ThemeUtil.HISTORY_WALLPAPER_METHOD, (String) null, bundle));
            } catch (Exception e) {
                Log.e("ThemeUtil", "addHistoryWallpaper: " + e);
            } finally {
                String str = this.$subjectSnapshot;
                if (str != null) {
                    FileUtil.delete(str);
                }
            }
            return Unit.INSTANCE;
        }
    }

    public final void addHistoryWallpaper(Context context, CommonConfig config) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        WallpaperInfo wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo();
        Intrinsics.checkNotNull(wallpaperInfo);
        addHistoryWallpaper(context, wallpaperInfo);
        if (config.getHomeInfo().getFollowStatus() == 0) {
            WallpaperInfo wallpaperInfo2 = config.getHomeInfo().getWallpaperInfo();
            Intrinsics.checkNotNull(wallpaperInfo2);
            addHistoryWallpaper(context, wallpaperInfo2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x005a  */
    public final void addHistoryWallpaper(Context context, WallpaperInfo wallpaperInfo) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        if (shouldAddToHistoryWallpaper(wallpaperInfo)) {
            String subject = wallpaperInfo.getSubject();
            if (subject == null) {
                str = null;
            } else {
                if (!StringsKt.endsWith$default(subject, TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK.getFileName(), false, 2, (Object) null) || !FileUtil.isFileExist(subject)) {
                    subject = null;
                }
                if (subject != null) {
                    String str2 = HISTORY_WALLPAPER_DIR;
                    FileUtil.makeDirExists(str2);
                    str = str2 + File.separator + UUID.randomUUID() + ".subject_snapshot";
                    if (!FileUtil.fastCopy(subject, str, false)) {
                        str = null;
                    }
                } else {
                    str = null;
                }
            }
            BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), null, null, new AnonymousClass1(context, str, wallpaperInfo, null), 3, null);
        }
    }

    public final String extractCmfTag(WallpaperInfo wallpaperInfo) {
        SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams();
        List<String> listListOf = CollectionsKt.listOf((Object[]) new String[]{sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPreViewPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null, wallpaperInfo.getOriginResourcePath(), wallpaperInfo.getSource()});
        for (String str : listListOf) {
            String strExtractCmfCode = WallpaperController.Companion.extractCmfCode(str);
            if (strExtractCmfCode != null) {
                Log.i("ThemeUtil", "extractCmfTag: found cmf=" + strExtractCmfCode + " from path=" + str);
                return "_wallpaper_" + strExtractCmfCode;
            }
        }
        Log.i("ThemeUtil", "extractCmfTag: no cmf found, candidates=" + listListOf);
        return "";
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0129  */
    /* JADX WARN: Code duplicated, block: B:35:0x012f  */
    /* JADX WARN: Code duplicated, block: B:36:0x0143  */
    /* JADX WARN: Code duplicated, block: B:46:0x0183  */
    public final HistoricalWallpaperExtraInfo getExtraInfo(WallpaperInfo wallpaperInfo) {
        String str;
        boolean zFastCopy;
        String str2;
        String animType;
        Float factor;
        Integer duration;
        Integer stiffness;
        Integer smallFrames;
        Integer allFrames;
        Integer stiffness2;
        Integer smallFrames2;
        Integer allFrames2;
        Wallpaper.Companion companion = Wallpaper.Companion;
        boolean zIsNormalLivePhotoResource = companion.isNormalLivePhotoResource(Integer.valueOf(wallpaperInfo.getVideoType()));
        if (!Intrinsics.areEqual(wallpaperInfo.getResourceType(), "sensor") && !zIsNormalLivePhotoResource && !Intrinsics.areEqual(wallpaperInfo.getResourceType(), "video")) {
            if (!Intrinsics.areEqual(wallpaperInfo.getResourceType(), "image")) {
                return null;
            }
            Log.d("ThemeUtil", "getExtraInfo: isAiGenerated=" + wallpaperInfo.isAiGenerated());
            HistoricalWallpaperExtraInfo historicalWallpaperExtraInfo = new HistoricalWallpaperExtraInfo(0, 0, 0, null, null, 0, null, null, null, false, 0, false, null, null, 16383, null);
            historicalWallpaperExtraInfo.setAiGenerated(wallpaperInfo.isAiGenerated());
            return historicalWallpaperExtraInfo;
        }
        String fileExtension = FileUtil.getFileExtension(FileUtil.resolveFileSuffix(wallpaperInfo.getOriginResourcePath()));
        int iIntValue = 0;
        String strExtractCmfTag = WallpaperController.Companion.shouldApplyOuterVideoWallpaper(wallpaperInfo, new String[0]) ? extractCmfTag(wallpaperInfo) : "";
        String str3 = HISTORY_WALLPAPER_DIR;
        String str4 = File.separator;
        UUID uuidRandomUUID = UUID.randomUUID();
        if (fileExtension != null) {
            str = "." + fileExtension;
        } else {
            str = "";
        }
        String str5 = str3 + str4 + uuidRandomUUID + strExtractCmfTag + str;
        Log.i("ThemeUtil", "historyVideoPath " + str5);
        boolean zFastCopy2 = FileUtil.fastCopy(wallpaperInfo.getOriginResourcePath(), str5, false);
        String str6 = str3 + str4 + UUID.randomUUID();
        if (Intrinsics.areEqual(wallpaperInfo.getResourceType(), "sensor")) {
            SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams();
            if (FileUtil.isFileExist(sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null)) {
                SensorWallpaperPrams sensorWallpaperPrams2 = wallpaperInfo.getSensorWallpaperPrams();
                zFastCopy = FileUtil.fastCopy(sensorWallpaperPrams2 != null ? sensorWallpaperPrams2.getThumbnailPath() : null, str6, false);
            } else if (wallpaperInfo.getCurrentWallpaper() != null) {
                BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
                Bitmap currentWallpaper = wallpaperInfo.getCurrentWallpaper();
                Intrinsics.checkNotNull(currentWallpaper);
                zFastCopy = BitmapUtil.toWebp$default(bitmapUtil, currentWallpaper, str6, false, 4, null);
            } else {
                zFastCopy = FileUtil.fastCopy(wallpaperInfo.getSource(), str6, false);
            }
        } else if (wallpaperInfo.getCurrentWallpaper() != null) {
            BitmapUtil bitmapUtil2 = BitmapUtil.INSTANCE;
            Bitmap currentWallpaper2 = wallpaperInfo.getCurrentWallpaper();
            Intrinsics.checkNotNull(currentWallpaper2);
            zFastCopy = BitmapUtil.toWebp$default(bitmapUtil2, currentWallpaper2, str6, false, 4, null);
        } else {
            zFastCopy = FileUtil.fastCopy(wallpaperInfo.getSource(), str6, false);
        }
        if (Intrinsics.areEqual(wallpaperInfo.getResourceType(), "sensor")) {
            str2 = str3 + str4 + UUID.randomUUID();
            SensorWallpaperPrams sensorWallpaperPrams3 = wallpaperInfo.getSensorWallpaperPrams();
            if (FileUtil.fastCopy(sensorWallpaperPrams3 != null ? sensorWallpaperPrams3.getSmallThumbnailPath() : null, str2, false)) {
                FileUtil.chmod(str2, 511);
            } else {
                str2 = null;
            }
        } else {
            str2 = null;
        }
        FileUtil.chmod(str5, 511);
        FileUtil.chmod(str6, 511);
        Log.d("ThemeUtil", "getExtraInfo: video:" + zFastCopy2 + " image:" + zFastCopy + " historyVideoPath=" + str5 + " historyImagePath=" + str6);
        HistoricalWallpaperExtraInfo historicalWallpaperExtraInfo2 = new HistoricalWallpaperExtraInfo(0, 0, 0, null, null, 0, null, null, null, false, 0, false, null, null, 16383, null);
        SensorWallpaperPrams sensorWallpaperPrams4 = wallpaperInfo.getSensorWallpaperPrams();
        historicalWallpaperExtraInfo2.setSensorVideoAllFrameCount((sensorWallpaperPrams4 == null || (allFrames2 = sensorWallpaperPrams4.getAllFrames()) == null) ? 0 : allFrames2.intValue());
        SensorWallpaperPrams sensorWallpaperPrams5 = wallpaperInfo.getSensorWallpaperPrams();
        historicalWallpaperExtraInfo2.setSensorVideoSmallScreenFrameCount((sensorWallpaperPrams5 == null || (smallFrames2 = sensorWallpaperPrams5.getSmallFrames()) == null) ? 0 : smallFrames2.intValue());
        historicalWallpaperExtraInfo2.setThumbnailPath(str6);
        historicalWallpaperExtraInfo2.setSmallThumbnailPath(str2);
        SensorWallpaperPrams sensorWallpaperPrams6 = wallpaperInfo.getSensorWallpaperPrams();
        int iIntValue2 = 10;
        historicalWallpaperExtraInfo2.setStiffness((sensorWallpaperPrams6 == null || (stiffness2 = sensorWallpaperPrams6.getStiffness()) == null) ? 10 : stiffness2.intValue());
        historicalWallpaperExtraInfo2.setWallpaperType(companion.isNormalLivePhotoResource(Integer.valueOf(wallpaperInfo.getVideoType())) ? "livephoto" : wallpaperInfo.getResourceType());
        historicalWallpaperExtraInfo2.setVideoPath(str5);
        historicalWallpaperExtraInfo2.setImagePath(str6);
        historicalWallpaperExtraInfo2.setVideoType(wallpaperInfo.getVideoType());
        historicalWallpaperExtraInfo2.setAiGenerated(wallpaperInfo.isAiGenerated());
        historicalWallpaperExtraInfo2.setSupportLoop(wallpaperInfo.isSupportLoop());
        historicalWallpaperExtraInfo2.setSupportFullScreenAod(wallpaperInfo.isSupportFullScreenAod());
        SensorWallpaperPrams sensorWallpaperPrams7 = wallpaperInfo.getSensorWallpaperPrams();
        String thumbnailPath = sensorWallpaperPrams7 != null ? sensorWallpaperPrams7.getThumbnailPath() : null;
        SensorWallpaperPrams sensorWallpaperPrams8 = wallpaperInfo.getSensorWallpaperPrams();
        String smallThumbnailPath = sensorWallpaperPrams8 != null ? sensorWallpaperPrams8.getSmallThumbnailPath() : null;
        SensorWallpaperPrams sensorWallpaperPrams9 = wallpaperInfo.getSensorWallpaperPrams();
        Integer numValueOf = Integer.valueOf((sensorWallpaperPrams9 == null || (allFrames = sensorWallpaperPrams9.getAllFrames()) == null) ? -1 : allFrames.intValue());
        SensorWallpaperPrams sensorWallpaperPrams10 = wallpaperInfo.getSensorWallpaperPrams();
        Integer numValueOf2 = Integer.valueOf((sensorWallpaperPrams10 == null || (smallFrames = sensorWallpaperPrams10.getSmallFrames()) == null) ? 0 : smallFrames.intValue());
        SensorWallpaperPrams sensorWallpaperPrams11 = wallpaperInfo.getSensorWallpaperPrams();
        if (sensorWallpaperPrams11 != null && (stiffness = sensorWallpaperPrams11.getStiffness()) != null) {
            iIntValue2 = stiffness.intValue();
        }
        Integer numValueOf3 = Integer.valueOf(iIntValue2);
        SensorWallpaperPrams sensorWallpaperPrams12 = wallpaperInfo.getSensorWallpaperPrams();
        if (sensorWallpaperPrams12 != null && (duration = sensorWallpaperPrams12.getDuration()) != null) {
            iIntValue = duration.intValue();
        }
        int i = iIntValue;
        SensorWallpaperPrams sensorWallpaperPrams13 = wallpaperInfo.getSensorWallpaperPrams();
        float fFloatValue = (sensorWallpaperPrams13 == null || (factor = sensorWallpaperPrams13.getFactor()) == null) ? 0.0f : factor.floatValue();
        SensorWallpaperPrams sensorWallpaperPrams14 = wallpaperInfo.getSensorWallpaperPrams();
        historicalWallpaperExtraInfo2.setSensorExtra(new SensorExtra(thumbnailPath, smallThumbnailPath, numValueOf, numValueOf2, numValueOf3, i, fFloatValue, (sensorWallpaperPrams14 == null || (animType = sensorWallpaperPrams14.getAnimType()) == null) ? "" : animType));
        Log.d("ThemeUtil", "getExtraInfo: historicalWallpaperExtraInfo=" + historicalWallpaperExtraInfo2);
        return historicalWallpaperExtraInfo2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0021, code lost:
    
        if (r2.equals("video") == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0036, code lost:
    
        if (r2.equals("sensor") == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x003b, code lost:
    
        return com.miui.keyguard.editor.utils.ThemeUtil.WALLPAPER_TYPE_VIDEO;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int getWallpaperType(com.miui.keyguard.editor.data.bean.WallpaperInfo r3) {
        /*
            r2 = this;
            java.lang.String r2 = r3.getResourceType()
            if (r2 == 0) goto L3c
            int r0 = r2.hashCode()
            r1 = -905948230(0xffffffffca0053ba, float:-2102510.5)
            if (r0 == r1) goto L30
            r1 = 100313435(0x5faa95b, float:2.3572098E-35)
            if (r0 == r1) goto L24
            r1 = 112202875(0x6b0147b, float:6.6233935E-35)
            if (r0 == r1) goto L1a
            goto L3c
        L1a:
            java.lang.String r0 = "video"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L39
            goto L3c
        L24:
            java.lang.String r0 = "image"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L2d
            goto L3c
        L2d:
            int r2 = com.miui.keyguard.editor.utils.ThemeUtil.WALLPAPER_TYPE_IMAGE
            return r2
        L30:
            java.lang.String r0 = "sensor"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L39
            goto L3c
        L39:
            int r2 = com.miui.keyguard.editor.utils.ThemeUtil.WALLPAPER_TYPE_VIDEO
            return r2
        L3c:
            com.miui.keyguard.editor.utils.Wallpaper$Companion r2 = com.miui.keyguard.editor.utils.Wallpaper.Companion
            int r3 = r3.getVideoType()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            boolean r2 = r2.isNormalLivePhotoResource(r3)
            if (r2 == 0) goto L4f
            int r2 = com.miui.keyguard.editor.utils.ThemeUtil.WALLPAPER_TYPE_VIDEO
            return r2
        L4f:
            int r2 = com.miui.keyguard.editor.utils.ThemeUtil.WALLPAPER_TYPE_NONE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.utils.ThemeUtil.getWallpaperType(com.miui.keyguard.editor.data.bean.WallpaperInfo):int");
    }

    public final void makeWallpaperDirExists() {
        FileUtil.mkdirs(new File(WALLPAPER_DIR), 511, -1, -1);
    }

    public final boolean shouldAddToHistoryWallpaper(WallpaperInfo wallpaperInfo) {
        return Intrinsics.areEqual(wallpaperInfo.getResourceType(), "image") || Intrinsics.areEqual(wallpaperInfo.getResourceType(), "video") || Intrinsics.areEqual(wallpaperInfo.getResourceType(), "sensor");
    }
}

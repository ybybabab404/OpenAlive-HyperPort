package com.miui.keyguard.editor.data.template;

import android.app.Application;
import android.app.KeyguardManager;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManager;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.lifecycle.LifecycleOwner;
import com.miui.aod.category.CategoryConstants;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.EditorServiceManager;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.LargeScreenHierarchyEnable;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.data.db.EditorDatabase;
import com.miui.keyguard.editor.data.db.TemplateHistoryDao;
import com.miui.keyguard.editor.data.migration.AllInOneClockMigration;
import com.miui.keyguard.editor.edit.base.DepthPresetDownloadManager;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.edit.color.RandomColorUtil;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.GLRenderHelper;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperController;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.guidance.UserGuidance;
import com.miui.keyguard.editor.homepage.bean.GalleryStatusBean;
import com.miui.keyguard.editor.homepage.model.CrossListPreloader;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.BitmapCreator;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.ConstantsKt;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.LockPatternUtils;
import com.miui.keyguard.editor.utils.PickWallpaperColorInfo;
import com.miui.keyguard.editor.utils.Process;
import com.miui.keyguard.editor.utils.SPUtilKt;
import com.miui.keyguard.editor.utils.ScenarioRecognitionUtils;
import com.miui.keyguard.editor.utils.SystemEditor;
import com.miui.keyguard.editor.utils.TemplatePreviewImageComposer;
import com.miui.keyguard.editor.utils.TemplateSource;
import com.miui.keyguard.editor.utils.ThemeUtil;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.keyguard.editor.utils.segment.UtilsKt;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

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
/* JADX INFO: compiled from: TemplateApiImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateApiImpl implements TemplateApi {
    public static volatile long applyClickTimestamp;
    public static volatile String configJsonForFlush;
    public static volatile boolean waitFlushConfigForLockTransform;
    public volatile boolean canDeleteWhenApplying;
    public final Context context;
    public boolean currentTemplateDualClock;
    public int currentTemplateLoadingColor;
    public boolean enableGetCurrentLockWallpaperFromWM;
    public final TemplateFilePathGenerator filePathGenerator;
    public boolean isDarkWallpaper;
    public boolean localDarkenWallpaperValid;
    public final Mutex mutex;
    public volatile boolean otaMigrationInProgress;
    public final Mutex otaMigrationLock;
    public final TemplateHistoryDao templateDao;
    public Bitmap thirdPartyWallpaperServicePreview;
    public final Lazy wallpaperChangedCallback;
    public static final Companion Companion = new Companion(null);
    public static final Object templateWriteLock = new Object();

    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ Bitmap getLayerBitmap$default(Companion companion, Context context, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 4) != 0) {
                i2 = -1;
            }
            if ((i4 & 8) != 0) {
                i3 = -1;
            }
            return companion.getLayerBitmap(context, i, i2, i3);
        }

        public final void clearOldConfig() {
            Log.d("Keyguard-Editor-TemplateApiImpl", "clearOldConfig");
            TemplateApiImpl.configJsonForFlush = null;
        }

        public final void flushConfigJson(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Log.i("Keyguard-Editor-TemplateApiImpl", "flushConfigJson: hasData=" + (TemplateApiImpl.configJsonForFlush != null));
            String str = TemplateApiImpl.configJsonForFlush;
            if (str != null) {
                TemplateApi.Companion.getInstance(context).writeTemplateInfo(str, "flush for transform");
            }
            setWaitFlushConfig(false);
            TemplateApiImpl.configJsonForFlush = null;
        }

        public final long getApplyClickTimestamp() {
            return TemplateApiImpl.applyClickTimestamp;
        }

        public final Bitmap getLayerBitmap(Context context, int i, int i2, int i3) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (i == 0) {
                return null;
            }
            return BitmapUtil.INSTANCE.fromDrawable(ContextCompat.getDrawable(context, i), i2, i3);
        }

        public final String getPreviewCachedPath(Context context, String str, String str2, String str3, int i, int i2) {
            long length;
            Locale locale = Locale.getDefault();
            int i3 = context.getApplicationContext().getResources().getConfiguration().orientation;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            boolean z = !deviceUtil.isFlip() && (deviceUtil.isFoldLargeScreen(context) || deviceUtil.isPad());
            String presetWallpaperAbsolutePath = TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(str2);
            if (FileUtil.isFileExist(presetWallpaperAbsolutePath)) {
                length = new File(presetWallpaperAbsolutePath).length();
            } else {
                Log.d("Keyguard-Editor-TemplateApiImpl", "getPreviewCachedPath: not preset");
                length = new File(str2).length();
            }
            return "preview_" + str + "_" + FileUtil.encodeMD5(str2 + str3 + locale + i3 + z + length + SystemEditor.INSTANCE.getSystemVersion() + "23") + "_" + i + "_" + i2;
        }

        public final boolean isWaitingFlush() {
            return TemplateApiImpl.waitFlushConfigForLockTransform;
        }

        public final PresetTemplateConfig preloadPresetTemplate(Context context, TemplateItemConfig config, String str, int i, int i2, int i3) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(config, "config");
            String strGeneratePresetTemplateFilePath = (str == null || str.length() == 0) ? new TemplateFilePathGenerator(context).generatePresetTemplateFilePath(config.getTemplateName(), TemplateFilePathGenerator.TemplateFileType.CONFIG) : str;
            String effectWallpaperPath = config.getEffectWallpaperPath();
            if (effectWallpaperPath == null) {
                effectWallpaperPath = config.getWallpaperPath();
            }
            String str2 = effectWallpaperPath;
            Bitmap previewCache = TemplateApi.Companion.getInstance(context).getPreviewCache(getPreviewCachedPath(context, config.getTemplateName(), str2, config.getMaskPath(), i, i2));
            CoroutineScope coroutineScope = context instanceof LifecycleOwner ? EditorActivityViewModel.Companion.getCoroutineScope(context) : UtilsKt.getDefaultCoroutineScope();
            if (previewCache != null) {
                Log.d("Keyguard-Editor-TemplateApiImpl", "preloadPresetTemplate: use cache:" + str2 + " " + config.getMaskPath() + " " + i + " " + i2);
                return new PresetTemplateConfig(null, previewCache, null, strGeneratePresetTemplateFilePath, i3);
            }
            if (!config.getDepth()) {
                String str3 = strGeneratePresetTemplateFilePath;
                Bitmap bitmapComposeTemplatePreviewImage = TemplatePreviewImageComposer.INSTANCE.composeTemplatePreviewImage(null, TemplateDataUtil.getTemplateBitmap(str2, true, i, i2, true), getLayerBitmap$default(this, context, config.getClockLayerResId(), 0, 0, 12, null), null, i, i2);
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getIO(), null, new TemplateApiImpl$Companion$preloadPresetTemplate$presetConfig$2(context, config, str2, i, i2, bitmapComposeTemplatePreviewImage, null), 2, null);
                Log.d("Keyguard-Editor-TemplateApiImpl", "compose normal preview");
                return new PresetTemplateConfig(null, bitmapComposeTemplatePreviewImage, null, str3, i3);
            }
            Bitmap templateBitmap$default = TemplateDataUtil.getTemplateBitmap$default(str2, true, 0, 0, true, 12, null);
            Bitmap layerBitmap$default = getLayerBitmap$default(this, context, config.getClockLayerResId(), 0, 0, 12, null);
            Bitmap layerBitmap$default2 = getLayerBitmap$default(this, context, config.getBelowLayerResId(), 0, 0, 12, null);
            Bitmap templateBitmap$default2 = TemplateDataUtil.getTemplateBitmap$default(config.getMaskPath(), true, 0, 0, true, 12, null);
            Log.d("Keyguard-Editor-TemplateApiImpl", "compose depth preview");
            Bitmap bitmapComposeTemplatePreviewImage2 = TemplatePreviewImageComposer.INSTANCE.composeTemplatePreviewImage(layerBitmap$default2, templateBitmap$default, layerBitmap$default, templateBitmap$default2, i, i2);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getIO(), null, new TemplateApiImpl$Companion$preloadPresetTemplate$presetConfig$1(context, config, str2, i, i2, bitmapComposeTemplatePreviewImage2, null), 2, null);
            return new PresetTemplateConfig(null, bitmapComposeTemplatePreviewImage2, null, strGeneratePresetTemplateFilePath, i3);
        }

        public final void reportApplyClickLatency$keyguardeditor_release(long j, String from) {
            Intrinsics.checkNotNullParameter(from, "from");
            long applyClickTimestamp = getApplyClickTimestamp();
            setApplyClickTimestamp(0L);
            if (applyClickTimestamp <= 0) {
                Log.i("Keyguard-Editor-TemplateApiImpl", "reportApplyClickLatency[" + from + "]: skip (no clickTs, non-UI apply path)");
                return;
            }
            long j2 = j - applyClickTimestamp;
            if (j2 <= 0 || j2 > 3000) {
                Log.i("Keyguard-Editor-TemplateApiImpl", "reportApplyClickLatency[" + from + "]: skip abnormal duration=" + j2 + "ms (threshold=3000ms, likely user-interaction inserted or clock skew, clickTs=" + applyClickTimestamp + ", mainStart=" + j + ")");
                return;
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "reportApplyClickLatency[" + from + "]: clickTs=" + applyClickTimestamp + ", mainStart=" + j + ", duration=" + j2 + "ms");
            try {
                ScenarioRecognitionUtils.INSTANCE.notifyScenarioChanged(1072L, j2);
            } catch (Exception unused) {
            }
        }

        public final void setApplyClickTimestamp(long j) {
            TemplateApiImpl.applyClickTimestamp = j;
        }

        public final void setWaitFlushConfig(boolean z) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
            Log.d("Keyguard-Editor-TemplateApiImpl", "setWaitFlushConfig: wait=" + z + ", caller=" + ArraysKt.getOrNull(stackTrace, 1));
            TemplateApiImpl.waitFlushConfigForLockTransform = z;
        }
    }

    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TemplateFilePathGenerator.TemplateFileType.values().length];
            try {
                iArr[TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TemplateFilePathGenerator.TemplateFileType.WALLPAPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TemplateFilePathGenerator.TemplateFileType.SUBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TemplateFilePathGenerator.TemplateFileType.FRONT_BACK.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TemplateFilePathGenerator.TemplateFileType.CROPPED_FRONT_BACK.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[WallpaperInfoType.values().length];
            try {
                iArr2[WallpaperInfoType.LOCK_WALLPAPER.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[WallpaperInfoType.HOME_WALLPAPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.template.TemplateApiImpl$applyEditPageTemplate$1, reason: invalid class name */
    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl", f = "TemplateApiImpl.kt", l = {6055}, m = "applyEditPageTemplate")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public long J$0;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public Object L$5;
        public Object L$6;
        public boolean Z$0;
        public boolean Z$1;
        public boolean Z$2;
        public boolean Z$3;
        public boolean Z$4;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TemplateApiImpl.this.applyEditPageTemplate(null, null, null, null, null, 0L, false, false, false, false, false, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.template.TemplateApiImpl$applyHomePageTemplate$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl", f = "TemplateApiImpl.kt", l = {6055}, m = "applyHomePageTemplate")
    public static final class C00891 extends ContinuationImpl {
        public int I$0;
        public int I$1;
        public long J$0;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public boolean Z$0;
        public boolean Z$1;
        public boolean Z$2;
        public int label;
        public /* synthetic */ Object result;

        public C00891(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TemplateApiImpl.this.applyHomePageTemplate(null, false, null, 0L, false, false, 0, null, 0, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.template.TemplateApiImpl$insertHistoryConfig$2, reason: invalid class name */
    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl$insertHistoryConfig$2", f = "TemplateApiImpl.kt", l = {}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TemplateApiImpl.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            int historyCount = TemplateApiImpl.this.templateDao.getHistoryCount();
            if (historyCount >= 20) {
                List<TemplateHistoryConfig> oldestHistory = TemplateApiImpl.this.templateDao.getOldestHistory(historyCount - 19);
                Log.d("Keyguard-Editor-TemplateApiImpl", "need delete oldHistory count=" + (oldestHistory != null ? Boxing.boxInt(oldestHistory.size()) : null));
                if (oldestHistory != null) {
                    TemplateApiImpl templateApiImpl = TemplateApiImpl.this;
                    for (TemplateHistoryConfig templateHistoryConfig : oldestHistory) {
                        Log.d("Keyguard-Editor-TemplateApiImpl", "delete id=" + templateHistoryConfig.getId() + " lastModifiedTime=" + templateHistoryConfig.getLastModifiedTime() + " templateId=" + templateHistoryConfig.getTemplateId());
                        TemplateApi.DefaultImpls.deleteHistoryTemplate$default(templateApiImpl, templateHistoryConfig, false, false, 4, null);
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.template.TemplateApiImpl$migrateHistoryTemplatesClockForOS4$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl", f = "TemplateApiImpl.kt", l = {6055}, m = "migrateHistoryTemplatesClockForOS4")
    public static final class C00901 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public int label;
        public /* synthetic */ Object result;

        public C00901(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TemplateApiImpl.this.migrateHistoryTemplatesClockForOS4(this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.template.TemplateApiImpl$runAsync$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl$runAsync$1", f = "TemplateApiImpl.kt", l = {}, m = "invokeSuspend")
    public static final class C00911 extends SuspendLambda implements Function2<CoroutineScope, Continuation<Object>, Object> {
        public final /* synthetic */ Function0 $block;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00911(Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$block = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C00911(this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00911) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.$block.invoke();
        }
    }

    public TemplateApiImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.templateDao = EditorDatabase.Companion.getInstance(context).getTemplateHistoryDao();
        this.filePathGenerator = new TemplateFilePathGenerator(context);
        this.currentTemplateLoadingColor = ContextCompat.getColor(context, R.color.kg_main_item_place_holder_color);
        this.enableGetCurrentLockWallpaperFromWM = true;
        this.canDeleteWhenApplying = true;
        this.mutex = MutexKt.Mutex$default(false, 1, null);
        this.wallpaperChangedCallback = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TemplateApiImpl.wallpaperChangedCallback$lambda$0(this.f$0);
            }
        });
        this.otaMigrationLock = MutexKt.Mutex$default(false, 1, null);
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$13(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo) {
        objectRef.element = templateApiImpl.cropBitmap(bitmap, wallpaperPositionInfo, true);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$14(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo) {
        objectRef.element = templateApiImpl.cropBitmap(bitmap, wallpaperPositionInfo, true);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$17(CommonConfig commonConfig, Ref.ObjectRef objectRef, Bitmap bitmap, TemplateApiImpl templateApiImpl, WallpaperInfo wallpaperInfo, Ref.ObjectRef objectRef2, Ref.ObjectRef objectRef3) {
        Bitmap bitmap2;
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        String strCopyFile$default = null;
        Point bitmapSize = bitmapUtil.getBitmapSize(wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null);
        WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (Intrinsics.areEqual("image", wallpaperInfo3 != null ? wallpaperInfo3.getResourceType() : null) && bitmapSize.x == bitmap.getWidth() && bitmapSize.y == bitmap.getHeight()) {
            Log.d("Keyguard-Editor-TemplateApiImpl", "applyTemplate: copy lock wallpaper from path");
            WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            String source = wallpaperInfo4 != null ? wallpaperInfo4.getSource() : null;
            String strGenerateCurrentTemplateFilePath = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER);
            TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
            WallpaperInfo wallpaperInfo5 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            strCopyFile$default = copyFile$default(templateApiImpl, source, strGenerateCurrentTemplateFilePath, companion.isPresetPath(wallpaperInfo5 != null ? wallpaperInfo5.getSource() : null), 1000L, true, false, 32, null);
        }
        objectRef.element = strCopyFile$default;
        if (strCopyFile$default == null) {
            if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType()))) {
                Deferred deferred = (Deferred) objectRef2.element;
                if (deferred != null) {
                    UtilsKt.waitingFinish(deferred);
                }
                bitmap2 = (Bitmap) objectRef3.element;
            } else {
                bitmap2 = bitmap;
            }
            objectRef.element = copyBitmap$default(templateApiImpl, bitmap2, templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER), true, 0L, false, false, 56, null);
        }
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$20(CommonConfig commonConfig, Ref.ObjectRef objectRef, Bitmap bitmap, TemplateApiImpl templateApiImpl, boolean z, WallpaperInfo wallpaperInfo, Ref.ObjectRef objectRef2, Ref.ObjectRef objectRef3) {
        Bitmap bitmap2;
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
        String strCopyFile$default = null;
        Point bitmapSize = bitmapUtil.getBitmapSize(wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null);
        WallpaperInfo wallpaperInfo3 = commonConfig.getHomeInfo().getWallpaperInfo();
        if (Intrinsics.areEqual("image", wallpaperInfo3 != null ? wallpaperInfo3.getResourceType() : null) && bitmapSize.x == bitmap.getWidth() && bitmapSize.y == bitmap.getHeight()) {
            Log.d("Keyguard-Editor-TemplateApiImpl", "applyTemplate: copy home wallpaper from path");
            WallpaperInfo wallpaperInfo4 = commonConfig.getHomeInfo().getWallpaperInfo();
            String source = wallpaperInfo4 != null ? wallpaperInfo4.getSource() : null;
            String strGenerateCurrentTemplateFilePath = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
            TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
            WallpaperInfo wallpaperInfo5 = commonConfig.getHomeInfo().getWallpaperInfo();
            strCopyFile$default = copyFile$default(templateApiImpl, source, strGenerateCurrentTemplateFilePath, companion.isPresetPath(wallpaperInfo5 != null ? wallpaperInfo5.getSource() : null), 1000L, true, false, 32, null);
        }
        objectRef.element = strCopyFile$default;
        if (strCopyFile$default == null) {
            if (z && MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType()))) {
                Deferred deferred = (Deferred) objectRef2.element;
                if (deferred != null) {
                    UtilsKt.waitingFinish(deferred);
                }
                bitmap2 = (Bitmap) objectRef3.element;
            } else {
                bitmap2 = bitmap;
            }
            objectRef.element = copyBitmap$default(templateApiImpl, bitmap2, templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME), true, 0L, false, false, 56, null);
        }
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$22(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, ScreenshotSource screenshotSource) {
        objectRef.element = copyBitmap$default(templateApiImpl, screenshotSource.getSmallScreenshot(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SMALL_SCREENSHOT), false, 400L, false, false, 52, null);
        screenshotSource.setSmallScreenshot(null);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$24(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, ScreenshotSource screenshotSource) {
        objectRef.element = copyBitmap$default(templateApiImpl, screenshotSource.getSmallWallpaperScreenshot(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SMALL_WALLPAPER_SCREENSHOT), false, 400L, false, false, 52, null);
        screenshotSource.setSmallWallpaperScreenshot(null);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$26(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, ScreenshotSource screenshotSource) {
        objectRef.element = copyBitmap$default(templateApiImpl, screenshotSource.getLargePortraitScreenshot(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_SCREENSHOT), false, 400L, false, false, 52, null);
        screenshotSource.setLargePortraitScreenshot(null);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$28(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, ScreenshotSource screenshotSource) {
        objectRef.element = copyBitmap$default(templateApiImpl, screenshotSource.getLargePortWallpaperScreenshot(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_WALLPAPER_SCREENSHOT), false, 400L, false, false, 52, null);
        screenshotSource.setLargePortWallpaperScreenshot(null);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$30(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, ScreenshotSource screenshotSource) {
        objectRef.element = copyBitmap$default(templateApiImpl, screenshotSource.getLargeLandscapeScreenshot(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_SCREENSHOT), false, 400L, false, false, 52, null);
        screenshotSource.setLargeLandscapeScreenshot(null);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$32(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, ScreenshotSource screenshotSource) {
        objectRef.element = copyBitmap$default(templateApiImpl, screenshotSource.getLargeLandWallpaperScreenshot(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT), false, 400L, false, false, 52, null);
        screenshotSource.setLargeLandWallpaperScreenshot(null);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$35(WallpaperInfo wallpaperInfo, TemplateApiImpl templateApiImpl) {
        Bitmap videoWallpaperBitmap;
        if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType())) && (videoWallpaperBitmap = wallpaperInfo.getVideoWallpaperBitmap()) != null) {
            String strGenerateDepthVideoWallpaperFilePath = templateApiImpl.filePathGenerator.generateDepthVideoWallpaperFilePath(TemplateFilePathGenerator.TemplateFileType.DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK);
            String strCopyBitmap$default = copyBitmap$default(templateApiImpl, videoWallpaperBitmap, strGenerateDepthVideoWallpaperFilePath, false, 400L, false, true, 20, null);
            FileUtil.chmod(TemplateFilePathGenerator.Companion.getDepthVideoWallpaperDirPath(), 511);
            FileUtil.chmod(strGenerateDepthVideoWallpaperFilePath, 511);
            if (strCopyBitmap$default == null) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "applyEditPageTemplate: save videoWallpaperBitmap failed");
            }
        }
        return Unit.INSTANCE;
    }

    public static final int applyEditPageTemplate$lambda$50$lambda$37(TemplateApiImpl templateApiImpl, Bitmap bitmap, String str, WallpaperInfo wallpaperInfo, String str2, String str3) {
        String strCopyBitmap$default = copyBitmap$default(templateApiImpl, bitmap, str, true, 0L, false, true, 24, null);
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: depthVideo corpSubject=" + strCopyBitmap$default);
        FileUtil.chmod(strCopyBitmap$default, 511);
        wallpaperInfo.setCropSubject(strCopyBitmap$default);
        wallpaperInfo.setSubject(null);
        FileUtil.delete(str2);
        FileUtil.delete(str3);
        return Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: depthVideo subject file copy completed");
    }

    public static final int applyEditPageTemplate$lambda$50$lambda$39(TemplateApiImpl templateApiImpl, Bitmap bitmap, String str) {
        String strCopyBitmap$default = copyBitmap$default(templateApiImpl, bitmap, str, true, 0L, false, false, 56, null);
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: newOriginalSubjectPath=" + strCopyBitmap$default);
        return FileUtil.chmod(strCopyBitmap$default, 511);
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$41(TemplateApiImpl templateApiImpl, Bitmap bitmap, String str, WallpaperInfo wallpaperInfo) {
        String strCopyBitmap$default = copyBitmap$default(templateApiImpl, TemplatePreviewImageComposer.convertSubjectMask$default(TemplatePreviewImageComposer.INSTANCE, bitmap, false, 2, null), str, false, 0L, false, false, 60, null);
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: newSubjectPath=" + strCopyBitmap$default);
        wallpaperInfo.setSubject(strCopyBitmap$default);
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$44(WallpaperPositionInfo wallpaperPositionInfo, WallpaperInfo wallpaperInfo, TemplateApiImpl templateApiImpl, Bitmap bitmap, CommonConfig commonConfig, String str, Ref.ObjectRef objectRef) {
        Bitmap bitmapCropBitmap = (wallpaperPositionInfo == null || !MagicType.INSTANCE.isDepth(Integer.valueOf(wallpaperInfo.getMagicType()))) ? null : templateApiImpl.cropBitmap(bitmap, wallpaperPositionInfo, false);
        if (bitmapCropBitmap != null) {
            Bitmap composedWallpaper = commonConfig.getLockscreenInfo().getComposedWallpaper();
            if (composedWallpaper == null) {
                composedWallpaper = templateApiImpl.tryComposeSubjectWithBackgroundColor(commonConfig, bitmapCropBitmap);
            }
            objectRef.element = composedWallpaper;
        }
        String strCopyBitmap$default = Intrinsics.areEqual("doodle", commonConfig.getLockscreenInfo().getClockInfo().getTemplateId()) ? copyBitmap$default(templateApiImpl, bitmapCropBitmap, str, true, 0L, true, true, 8, null) : copyBitmap$default(templateApiImpl, bitmapCropBitmap, str, true, 0L, false, true, 24, null);
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: corpSubject=" + strCopyBitmap$default);
        FileUtil.chmod(strCopyBitmap$default, 511);
        wallpaperInfo.setCropSubject(strCopyBitmap$default);
        if (!Intrinsics.areEqual(bitmap, bitmapCropBitmap)) {
            BitmapUtil.INSTANCE.recycleBitmap(bitmapCropBitmap, "crop subject");
        }
        return Unit.INSTANCE;
    }

    public static final Unit applyEditPageTemplate$lambda$50$lambda$46(TemplateApiImpl templateApiImpl, CommonConfig commonConfig) {
        templateApiImpl.copyVideoForLivePhotoResource(commonConfig);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void applyGalleryWallpaper$default(TemplateApiImpl templateApiImpl, Bitmap bitmap, boolean z, WallpaperInfo wallpaperInfo, String str, boolean z2, boolean z3, boolean z4, boolean z5, int i, Object obj) {
        templateApiImpl.applyGalleryWallpaper(bitmap, z, wallpaperInfo, str, z2, z3, (i & 64) != 0 ? true : z4, (i & 128) != 0 ? false : z5);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:23:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:51:0x017d A[PHI: r7 r9
  0x017d: PHI (r7v7 boolean) = (r7v6 boolean), (r7v9 boolean) binds: [B:68:0x01d1, B:50:0x017b] A[DONT_GENERATE, DONT_INLINE]
  0x017d: PHI (r9v5 boolean) = (r9v4 boolean), (r9v6 boolean) binds: [B:68:0x01d1, B:50:0x017b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:73:0x01ea  */
    /* JADX WARN: Code duplicated, block: B:74:0x01ed  */
    /* JADX WARN: Code duplicated, block: B:76:0x01f0  */
    /* JADX WARN: Code duplicated, block: B:78:0x0201  */
    public static final Unit applyWallpaperWithConfig$lambda$66(TemplateApiImpl templateApiImpl, WallpaperController wallpaperController, Ref.BooleanRef booleanRef, boolean z, Bitmap bitmap, Pair pair, Pair pair2, WallpaperApplyParam wallpaperApplyParam, WallpaperInfo info, Bitmap wallpaper, String path, boolean z2) {
        boolean z3;
        boolean z4;
        Bitmap bitmap2;
        Pair pair3;
        boolean z5;
        boolean z6;
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        Intrinsics.checkNotNullParameter(path, "path");
        WallpaperInfoType infoType = info.getInfoType();
        WallpaperInfoType wallpaperInfoType = WallpaperInfoType.LOCK_WALLPAPER;
        boolean z7 = false;
        boolean z8 = infoType == wallpaperInfoType;
        if (Wallpaper.Companion.isSupportBlurWallpaperType(info.getResourceType(), info.getMagicType()) && info.getEnableBlur()) {
            z7 = true;
        }
        FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
        Context context = templateApiImpl.context;
        ExtraInfo extraInfo = info.getExtraInfo();
        boolean zIsLoop = fashionGalleryHelper.isLoop(context, z8, extraInfo != null ? extraInfo.getLoopType() : null);
        String resourceType = info.getResourceType();
        ExtraInfo extraInfo2 = info.getExtraInfo();
        Log.d("Keyguard-Editor-TemplateApiImpl", "applyWallpaper: isLock=" + z8 + ", info.resourceType=" + resourceType + ", applyAll=" + z2 + ", loopType=" + (extraInfo2 != null ? extraInfo2.getLoopType() : null) + " isLoop=" + zIsLoop);
        String resourceType2 = info.getResourceType();
        if (resourceType2 != null) {
            switch (resourceType2.hashCode()) {
                case -905948230:
                    z5 = z8;
                    z6 = z7;
                    if (!resourceType2.equals("sensor")) {
                        z3 = z5;
                        z4 = z6;
                        boolean z9 = booleanRef.element;
                        if (z3) {
                            bitmap2 = bitmap;
                        } else {
                            bitmap2 = null;
                        }
                        if (z3) {
                            pair3 = pair;
                        } else {
                            pair3 = pair2;
                        }
                        templateApiImpl.applyImageWallpaper(wallpaper, z9, path, info, zIsLoop, z3, z4, bitmap2, pair3, wallpaperApplyParam, z2);
                    } else {
                        templateApiImpl.applySensorWallpaper(info, booleanRef.element, z6, z2, z5);
                    }
                    break;
                case -196315310:
                    z5 = z8;
                    z6 = z7;
                    if (!resourceType2.equals("gallery")) {
                        z3 = z5;
                        z4 = z6;
                        boolean z10 = booleanRef.element;
                        if (z3) {
                            bitmap2 = bitmap;
                        } else {
                            bitmap2 = null;
                        }
                        if (z3) {
                            pair3 = pair;
                        } else {
                            pair3 = pair2;
                        }
                        templateApiImpl.applyImageWallpaper(wallpaper, z10, path, info, zIsLoop, z3, z4, bitmap2, pair3, wallpaperApplyParam, z2);
                    } else {
                        Context context2 = templateApiImpl.context;
                        ExtraInfo extraInfo3 = info.getExtraInfo();
                        if (!fashionGalleryHelper.isLoop(context2, z5, extraInfo3 != null ? extraInfo3.getLoopType() : null)) {
                            templateApiImpl.applyImageWallpaper(wallpaper, booleanRef.element, path, info, false, z5, z6, z5 ? bitmap : null, z5 ? pair : pair2, wallpaperApplyParam, z2);
                        } else {
                            templateApiImpl.applyGalleryWallpaper(wallpaper, booleanRef.element, info, path, z5, z6, z, z2);
                        }
                    }
                    break;
                case 112202875:
                    if (!resourceType2.equals("video")) {
                        z3 = z8;
                        z4 = z7;
                        zIsLoop = zIsLoop;
                        boolean z11 = booleanRef.element;
                        if (z3) {
                            bitmap2 = bitmap;
                        } else {
                            bitmap2 = null;
                        }
                        if (z3) {
                            pair3 = pair;
                        } else {
                            pair3 = pair2;
                        }
                        templateApiImpl.applyImageWallpaper(wallpaper, z11, path, info, zIsLoop, z3, z4, bitmap2, pair3, wallpaperApplyParam, z2);
                    } else {
                        boolean z12 = z8;
                        boolean z13 = z7;
                        info.setOriginResourcePath(copyFile$default(templateApiImpl, info.getOriginResourcePath(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(info.getVideoWallpaperFileType()), false, 0L, false, false, 60, null));
                        if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(info.getMagicType())) || info.isDepthVideo()) {
                            info.setDepthVideo(info.getOriginResourcePath());
                        }
                        if (!z2) {
                            wallpaperController.applyVideoWallpaper(info.getOriginResourcePath(), wallpaper, info.getMagicType(), info.isSupportLoop(), z12, booleanRef.element, z13, false, zIsLoop ? 1 : info.getEnableDoodle(), info.isSupportFullScreenAod());
                        } else if (info.getInfoType() == wallpaperInfoType) {
                            wallpaperController.applyVideoWallpaper(info.getOriginResourcePath(), wallpaper, info.getMagicType(), info.isSupportLoop(), z12, booleanRef.element, z13, true, zIsLoop ? 1 : info.getEnableDoodle(), info.isSupportFullScreenAod());
                        }
                    }
                    break;
                case 1196792382:
                    if (!resourceType2.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
                        z3 = z8;
                        z4 = z7;
                        zIsLoop = zIsLoop;
                        boolean z14 = booleanRef.element;
                        if (z3) {
                            bitmap2 = bitmap;
                        } else {
                            bitmap2 = null;
                        }
                        if (z3) {
                            pair3 = pair;
                        } else {
                            pair3 = pair2;
                        }
                        templateApiImpl.applyImageWallpaper(wallpaper, z14, path, info, zIsLoop, z3, z4, bitmap2, pair3, wallpaperApplyParam, z2);
                    } else {
                        wallpaperController.applySuperWallpaper(info.getOriginResourcePath(), templateApiImpl.context, z7);
                    }
                    break;
                default:
                    z3 = z8;
                    z4 = z7;
                    zIsLoop = zIsLoop;
                    boolean z15 = booleanRef.element;
                    if (z3) {
                        bitmap2 = bitmap;
                    } else {
                        bitmap2 = null;
                    }
                    if (z3) {
                        pair3 = pair;
                    } else {
                        pair3 = pair2;
                    }
                    templateApiImpl.applyImageWallpaper(wallpaper, z15, path, info, zIsLoop, z3, z4, bitmap2, pair3, wallpaperApplyParam, z2);
                    break;
            }
        } else {
            z3 = z8;
            z4 = z7;
            zIsLoop = zIsLoop;
            boolean z16 = booleanRef.element;
            if (z3) {
                bitmap2 = bitmap;
            } else {
                bitmap2 = null;
            }
            if (z3) {
                pair3 = pair;
            } else {
                pair3 = pair2;
            }
            templateApiImpl.applyImageWallpaper(wallpaper, z16, path, info, zIsLoop, z3, z4, bitmap2, pair3, wallpaperApplyParam, z2);
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void applyWallpaperWithInfo$default(TemplateApiImpl templateApiImpl, WallpaperInfo wallpaperInfo, String str, boolean z, Bitmap bitmap, PickWallpaperColorInfo pickWallpaperColorInfo, WallpaperApplyParam wallpaperApplyParam, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            bitmap = null;
        }
        templateApiImpl.applyWallpaperWithInfo(wallpaperInfo, str, z, bitmap, pickWallpaperColorInfo, wallpaperApplyParam, z2);
    }

    public static /* synthetic */ String copyBitmap$default(TemplateApiImpl templateApiImpl, Bitmap bitmap, String str, boolean z, long j, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            j = 0;
        }
        if ((i & 16) != 0) {
            z2 = false;
        }
        if ((i & 32) != 0) {
            z3 = false;
        }
        return templateApiImpl.copyBitmap(bitmap, str, z, j, z2, z3);
    }

    public static /* synthetic */ String copyFile$default(TemplateApiImpl templateApiImpl, String str, String str2, boolean z, long j, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            j = -1;
        }
        if ((i & 16) != 0) {
            z2 = false;
        }
        if ((i & 32) != 0) {
            z3 = false;
        }
        return templateApiImpl.copyFile(str, str2, z, j, z2, z3);
    }

    public static /* synthetic */ void copyQ18OuterVideoToCurrent$default(TemplateApiImpl templateApiImpl, WallpaperInfo wallpaperInfo, SensorWallpaperPrams sensorWallpaperPrams, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            str = null;
        }
        templateApiImpl.copyQ18OuterVideoToCurrent(wallpaperInfo, sensorWallpaperPrams, str);
    }

    public static /* synthetic */ Bitmap getCurrentOriginWallpaperByProvider$default(TemplateApiImpl templateApiImpl, Context context, WallpaperInfoType wallpaperInfoType, int i, Object obj) {
        if ((i & 2) != 0) {
            wallpaperInfoType = null;
        }
        return templateApiImpl.getCurrentOriginWallpaperByProvider(context, wallpaperInfoType);
    }

    public static /* synthetic */ CommonConfig getCurrentTemplateInternal$default(TemplateApiImpl templateApiImpl, boolean z, boolean z2, boolean z3, boolean z4, WallpaperTypeInfo wallpaperTypeInfo, boolean z5, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        if ((i & 4) != 0) {
            z3 = false;
        }
        if ((i & 8) != 0) {
            z4 = true;
        }
        if ((i & 16) != 0) {
            wallpaperTypeInfo = null;
        }
        if ((i & 32) != 0) {
            z5 = false;
        }
        if ((i & 64) != 0) {
            str = "all";
        }
        return templateApiImpl.getCurrentTemplateInternal(z, z2, z3, z4, wallpaperTypeInfo, z5, str);
    }

    public static final Bitmap getCurrentWallpaper$lambda$117(WallpaperInfo wallpaperInfo, TemplateApiImpl templateApiImpl) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentWallpaper: processName=" + Application.getProcessName() + " getWallpaperPreview infoType=" + (wallpaperInfo != null ? wallpaperInfo.getInfoType() : null));
        return (wallpaperInfo != null ? wallpaperInfo.getInfoType() : null) == WallpaperInfoType.LOCK_WALLPAPER ? templateApiImpl.getCurrentLockWallpaperPreview() : templateApiImpl.getCurrentHomeWallpaperPreview();
    }

    public static final String makeCurrentTemplateToHistory$lambda$111(TemplateApiImpl templateApiImpl, long j, String str, String str2, WallpaperInfo it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String resourceType = it.getResourceType();
        if (resourceType != null) {
            int iHashCode = resourceType.hashCode();
            if (iHashCode != -905948230) {
                if (iHashCode != 112202875) {
                    if (iHashCode == 1196792382 && resourceType.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
                        return templateApiImpl.getSuperWallpaperName();
                    }
                } else if (resourceType.equals("video")) {
                    String strCopyFile$default = copyFile$default(templateApiImpl, it.getOriginResourcePath(), templateApiImpl.filePathGenerator.generateHistoryTemplateFilePath(j, it.getVideoWallpaperFileType()), false, 1000L, true, false, 36, null);
                    if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(it.getMagicType())) || it.isDepthVideo()) {
                        it.setDepthVideo(strCopyFile$default);
                    }
                    it.setOriginResourcePath(strCopyFile$default);
                    return strCopyFile$default;
                }
            } else if (resourceType.equals("sensor")) {
                SensorWallpaperPrams sensorWallpaperPrams = it.getSensorWallpaperPrams();
                if (sensorWallpaperPrams != null) {
                    sensorWallpaperPrams.setThumbnailPath(copyFile$default(templateApiImpl, sensorWallpaperPrams.getThumbnailPath(), templateApiImpl.filePathGenerator.generateHistoryTemplateFilePath(j, TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW), false, 1000L, true, false, 36, null));
                }
                if (sensorWallpaperPrams != null) {
                    sensorWallpaperPrams.setSmallThumbnailPath(copyFile$default(templateApiImpl, sensorWallpaperPrams.getSmallThumbnailPath(), templateApiImpl.filePathGenerator.generateHistoryTemplateFilePath(j, TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN), false, 1000L, true, false, 36, null));
                }
                WallpaperController.Companion companion = WallpaperController.Companion;
                boolean zShouldApplyOuterVideoWallpaper = companion.shouldApplyOuterVideoWallpaper(it, new String[0]);
                Log.d("Keyguard-Editor-TemplateApiImpl", "makeCurrentTemplateToHistory: isPresetSensorWallpaper=" + zShouldApplyOuterVideoWallpaper);
                if (zShouldApplyOuterVideoWallpaper) {
                    WallpaperController.Companion.fillQ18SensorOuterVideoIfNeeded$default(companion, it.getResourceType(), sensorWallpaperPrams, null, CollectionsKt.listOf((Object[]) new String[]{it.getSource(), it.getOriginResourcePath(), sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPreViewPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null}), 4, null);
                    TemplateFilePathGenerator templateFilePathGenerator = templateApiImpl.filePathGenerator;
                    TemplateFilePathGenerator.TemplateFileType templateFileType = TemplateFilePathGenerator.TemplateFileType.OUTER_VIDEO_WALLPAPER;
                    String strFirstValidPath = companion.firstValidPath(templateFilePathGenerator.generateCurrentTemplateFilePath(templateFileType), sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null);
                    if (strFirstValidPath != null) {
                        String strCopyFile$default2 = copyFile$default(templateApiImpl, strFirstValidPath, templateApiImpl.filePathGenerator.generateHistoryTemplateFilePath(j, templateFileType), false, 5000L, true, false, 36, null);
                        if (sensorWallpaperPrams != null) {
                            if (strCopyFile$default2 != null) {
                                strFirstValidPath = strCopyFile$default2;
                            }
                            sensorWallpaperPrams.setOuterVideoPath(strFirstValidPath);
                        }
                    }
                } else {
                    Log.i("Keyguard-Editor-TemplateApiImpl", "makeCurrentTemplateToHistory: skip Q18 outer video backup, isQ18=" + DeviceUtil.INSTANCE.isQ18() + ", resourceType=" + it.getResourceType() + ", source=" + it.getSource() + ", origin=" + it.getOriginResourcePath() + ", outerVideoPath=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null) + ", outerVideoPreViewPath=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPreViewPath() : null));
                }
                it.setOriginResourcePath(copyFile$default(templateApiImpl, it.getOriginResourcePath(), templateApiImpl.filePathGenerator.generateHistoryTemplateFilePath(j, it.getVideoWallpaperFileType()), false, 1000L, true, false, 36, null));
                return it.getInfoType() == WallpaperInfoType.LOCK_WALLPAPER ? str : str2;
            }
        }
        it.setOriginResourcePath(it.getInfoType() == WallpaperInfoType.LOCK_WALLPAPER ? str : str2);
        return it.getOriginResourcePath();
    }

    public static final void requireLockScreenWallpaper$lambda$71(TemplateApiImpl templateApiImpl, Ref.ObjectRef objectRef, boolean z, boolean z2, CountDownLatch countDownLatch) {
        Bitmap wallpaperPreview;
        long jUptimeMillis = SystemClock.uptimeMillis();
        WallpaperController companion = WallpaperController.Companion.getInstance(templateApiImpl.context);
        if (z) {
            wallpaperPreview = companion.getWallpaperPreview(z2 ? WallpaperWhich.SmallLock.INSTANCE : WallpaperWhich.SmallHome.INSTANCE);
        } else {
            wallpaperPreview = companion.getWallpaperPreview(z2 ? WallpaperWhich.LockWhich.INSTANCE : WallpaperWhich.HomeWhich.INSTANCE);
        }
        objectRef.element = wallpaperPreview;
        Log.i("Keyguard-Editor-TemplateApiImpl", "get another sensor cost " + (SystemClock.uptimeMillis() - jUptimeMillis));
        countDownLatch.countDown();
    }

    public static final void requireLockScreenWallpaper$lambda$72(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, CountDownLatch countDownLatch) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        objectRef.element = templateApiImpl.getCurrentLockWallpaperAsBitmap();
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmap cost " + (SystemClock.uptimeMillis() - jUptimeMillis));
        countDownLatch.countDown();
    }

    public static final void requireLockScreenWallpaper$lambda$73(Ref.ObjectRef objectRef, TemplateApiImpl templateApiImpl, CommonConfig commonConfig, CountDownLatch countDownLatch) {
        TemplateConfig lockscreenInfo;
        long jUptimeMillis = SystemClock.uptimeMillis();
        objectRef.element = templateApiImpl.getCurrentWallpaper((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo());
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentWallpaper cost " + (SystemClock.uptimeMillis() - jUptimeMillis));
        countDownLatch.countDown();
    }

    public static final void requireLockScreenWallpaper$lambda$75(TemplateApiImpl templateApiImpl, CommonConfig commonConfig, CountDownLatch countDownLatch, Ref.ObjectRef objectRef, Ref.ObjectRef objectRef2) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            List videoDepthBitmap = WallpaperController.Companion.getInstance(templateApiImpl.context).getVideoDepthBitmap();
            if (videoDepthBitmap == null || videoDepthBitmap.size() < 2) {
                Bitmap bitmapRequireVideoDepthScreenshotWallpaper = templateApiImpl.requireVideoDepthScreenshotWallpaper(commonConfig);
                Bitmap subjectBitmap = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getSubjectBitmap();
                if (bitmapRequireVideoDepthScreenshotWallpaper != null && subjectBitmap != null) {
                    videoDepthBitmap = CollectionsKt.mutableListOf(subjectBitmap, bitmapRequireVideoDepthScreenshotWallpaper);
                }
            }
            if (videoDepthBitmap != null) {
                objectRef.element = videoDepthBitmap.get(1);
                objectRef2.element = videoDepthBitmap.get(0);
                Log.i("Keyguard-Editor-TemplateApiImpl", " VideoDeep LockScreenWallpaper it.size " + videoDepthBitmap.size() + ",it[1]:" + videoDepthBitmap.get(1) + ",it[0]:" + videoDepthBitmap.get(0));
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", " VideoDeep LockScreenWallpaper time " + (System.currentTimeMillis() - jCurrentTimeMillis));
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "VideoDeep error: " + e);
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0057  */
    public static final void requireLockScreenWallpaper$lambda$80(Ref.ObjectRef objectRef, String str, Bitmap bitmap, boolean z, CommonConfig commonConfig, String str2, TemplateApiImpl templateApiImpl) {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        Object obj = objectRef.element;
        Intrinsics.checkNotNull(obj);
        boolean webp$default = BitmapUtil.toWebp$default(bitmapUtil, (Bitmap) obj, str, false, 4, null);
        Bitmap bitmap2 = (Bitmap) objectRef.element;
        boolean zSameAs = bitmap2 != null ? bitmap2.sameAs(bitmap) : false;
        if (zSameAs && z) {
            if (Intrinsics.areEqual((commonConfig == null || (homeInfo = commonConfig.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType(), "image")) {
                Bitmap bitmapFromFile$default = BitmapUtil.fromFile$default(bitmapUtil, str2, 0, 0, null, 14, null);
                Bitmap bitmap3 = (Bitmap) objectRef.element;
                if (bitmap3 != null) {
                    boolean zSameAs2 = bitmap3.sameAs(bitmapFromFile$default);
                    zSameAs = true;
                    if (!zSameAs2) {
                        zSameAs = false;
                    }
                } else {
                    zSameAs = false;
                }
            }
        }
        if (commonConfig != null) {
            templateApiImpl.updateCurrentFollowStatus(zSameAs ? 2 : 0, "same lock in cross");
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "requireLockScreenWallpaper: save lock result: " + webp$default);
    }

    public static final void requireLockScreenWallpaper$lambda$82(Bitmap bitmap, String str, Ref.ObjectRef objectRef, boolean z, CommonConfig commonConfig, String str2, TemplateApiImpl templateApiImpl) {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        boolean webp$default = BitmapUtil.toWebp$default(bitmapUtil, bitmap, str, false, 4, null);
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean zSameAs = bitmap.sameAs((Bitmap) objectRef.element);
        if (zSameAs && z) {
            if (Intrinsics.areEqual((commonConfig == null || (homeInfo = commonConfig.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType(), "image")) {
                zSameAs = bitmap.sameAs(BitmapUtil.fromFile$default(bitmapUtil, str2, 0, 0, null, 14, null));
            }
        }
        if (commonConfig != null) {
            templateApiImpl.updateCurrentFollowStatus(zSameAs ? 2 : 0, "same home in cross");
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "requireLockScreenWallpaper: save home result: " + webp$default + " isSameWallpaper=" + zSameAs + " cost=" + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    public static final void requireLockScreenWallpaper$lambda$83(WallpaperSource wallpaperSource, String str) {
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        Bitmap sensorLargeWallpaper = wallpaperSource.getSensorLargeWallpaper();
        Intrinsics.checkNotNull(sensorLargeWallpaper);
        Log.i("Keyguard-Editor-TemplateApiImpl", "save sensor large result: " + BitmapUtil.toWebp$default(bitmapUtil, sensorLargeWallpaper, str, false, 4, null));
    }

    public static final void requireLockScreenWallpaper$lambda$84(WallpaperSource wallpaperSource, String str) {
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        Bitmap sensorSmallWallpaper = wallpaperSource.getSensorSmallWallpaper();
        Intrinsics.checkNotNull(sensorSmallWallpaper);
        Log.i("Keyguard-Editor-TemplateApiImpl", "save sensor small result: " + BitmapUtil.toWebp$default(bitmapUtil, sensorSmallWallpaper, str, false, 4, null));
    }

    public static /* synthetic */ String saveScreenshotToCurrentDir$default(TemplateApiImpl templateApiImpl, Bitmap bitmap, String str, TemplateFilePathGenerator.TemplateFileType templateFileType, String str2, WallpaperPositionInfo wallpaperPositionInfo, int i, Object obj) {
        if ((i & 8) != 0) {
            str2 = null;
        }
        if ((i & 16) != 0) {
            wallpaperPositionInfo = null;
        }
        return templateApiImpl.saveScreenshotToCurrentDir(bitmap, str, templateFileType, str2, wallpaperPositionInfo);
    }

    public static final void showCantApplyThirdPartyWallpaperToast$lambda$12(TemplateApiImpl templateApiImpl, Unit unit) {
        ToastUtil.INSTANCE.showText(templateApiImpl.context, R.string.kg_tip_cant_get_third_party_wallpaper);
    }

    public static /* synthetic */ void tryLoadSubjectBitmap$default(TemplateApiImpl templateApiImpl, CommonConfig commonConfig, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 8) != 0) {
            z3 = false;
        }
        templateApiImpl.tryLoadSubjectBitmap(commonConfig, z, z2, z3);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final String updateCurrentTemplateWallpaperInfo$lambda$105(TemplateApiImpl templateApiImpl, Ref.ObjectRef objectRef, Ref.ObjectRef objectRef2, WallpaperController wallpaperController, Ref.ObjectRef objectRef3, CommonConfig commonConfig, String resourceType, boolean z) {
        Intrinsics.checkNotNullParameter(resourceType, "resourceType");
        Log.d("Keyguard-Editor-TemplateApiImpl", "getOriginPathByType: resourceType=" + resourceType + " isLock=" + z);
        switch (resourceType.hashCode()) {
            case -905948230:
                if (resourceType.equals("sensor")) {
                    Context applicationContext = templateApiImpl.context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                    Pair sensorPrams = wallpaperController.getSensorPrams(applicationContext, z);
                    SensorWallpaperPrams sensorWallpaperPrams = sensorPrams != null ? (SensorWallpaperPrams) sensorPrams.getSecond() : null;
                    objectRef3.element = sensorWallpaperPrams;
                    Log.d("Keyguard-Editor-TemplateApiImpl", "getOriginPathByType: sensor wallpaper path=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null));
                    Object obj = objectRef3.element;
                    SensorWallpaperPrams sensorWallpaperPrams2 = (SensorWallpaperPrams) obj;
                    if (sensorWallpaperPrams2 != null) {
                        SensorWallpaperPrams sensorWallpaperPrams3 = (SensorWallpaperPrams) obj;
                        sensorWallpaperPrams2.setThumbnailPath(copyFile$default(templateApiImpl, sensorWallpaperPrams3 != null ? sensorWallpaperPrams3.getThumbnailPath() : null, templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW), false, 5000L, true, false, 36, null));
                    }
                    if (z) {
                        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                        String source = wallpaperInfo != null ? wallpaperInfo.getSource() : null;
                        objectRef.element = source;
                        if (!FileUtil.isFileExist(source)) {
                            SensorWallpaperPrams sensorWallpaperPrams4 = (SensorWallpaperPrams) objectRef3.element;
                            objectRef.element = sensorWallpaperPrams4 != null ? sensorWallpaperPrams4.getThumbnailPath() : null;
                        }
                    } else {
                        WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
                        String source2 = wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null;
                        objectRef2.element = source2;
                        if (!FileUtil.isFileExist(source2)) {
                            SensorWallpaperPrams sensorWallpaperPrams5 = (SensorWallpaperPrams) objectRef3.element;
                            objectRef2.element = sensorWallpaperPrams5 != null ? sensorWallpaperPrams5.getThumbnailPath() : null;
                        }
                    }
                    SensorWallpaperPrams sensorWallpaperPrams6 = (SensorWallpaperPrams) objectRef3.element;
                    Log.d("Keyguard-Editor-TemplateApiImpl", "getOriginPathByType: smallThumbnailPath=" + (sensorWallpaperPrams6 != null ? sensorWallpaperPrams6.getSmallThumbnailPath() : null));
                    Object obj2 = objectRef3.element;
                    SensorWallpaperPrams sensorWallpaperPrams7 = (SensorWallpaperPrams) obj2;
                    if (sensorWallpaperPrams7 != null) {
                        SensorWallpaperPrams sensorWallpaperPrams8 = (SensorWallpaperPrams) obj2;
                        sensorWallpaperPrams7.setSmallThumbnailPath(copyFile$default(templateApiImpl, sensorWallpaperPrams8 != null ? sensorWallpaperPrams8.getSmallThumbnailPath() : null, templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN), false, 5000L, true, false, 36, null));
                    }
                    copyQ18OuterVideoToCurrent$default(templateApiImpl, z ? commonConfig.getLockscreenInfo().getWallpaperInfo() : commonConfig.getHomeInfo().getWallpaperInfo(), (SensorWallpaperPrams) objectRef3.element, null, 4, null);
                    return copyFile$default(templateApiImpl, sensorPrams != null ? (String) sensorPrams.getFirst() : null, templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(z ? TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER : TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME), false, 5000L, true, false, 36, null);
                }
                break;
            case 3343923:
                if (resourceType.equals(CategoryConstants.CATEGORY_MAML)) {
                    Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: MaMl Wallpaper");
                    return templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(z ? TemplateFilePathGenerator.TemplateFileType.WALLPAPER : TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
                }
                break;
            case 112202875:
                if (resourceType.equals("video")) {
                    if (z) {
                        objectRef.element = "";
                    } else {
                        objectRef2.element = "";
                    }
                    return copyFile$default(templateApiImpl, wallpaperController.getVideoWallpaperOriginPath(z ? WallpaperWhich.LockWhich.INSTANCE : WallpaperWhich.HomeWhich.INSTANCE), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(z ? TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER : TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME), false, -1L, false, false, 52, null);
                }
                break;
            case 1196792382:
                if (resourceType.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
                    return templateApiImpl.getSuperWallpaperName();
                }
                break;
        }
        return templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(z ? TemplateFilePathGenerator.TemplateFileType.WALLPAPER : TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.data.template.TemplateApiImpl$wallpaperChangedCallback$1$1] */
    public static final TemplateApiImpl$wallpaperChangedCallback$1$1 wallpaperChangedCallback$lambda$0(final TemplateApiImpl templateApiImpl) {
        return new IMiuiWallpaperManagerCallback.Stub() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$wallpaperChangedCallback$1$1
            {
                WallpaperController.Companion.getInstance(this.this$0.context).registerWallpaperChangedListener(this, WallpaperWhich.AllWhich.INSTANCE.getWhich());
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
            public void onDrawFrameEnd() {
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
            public void onPartColorComputeComplete(Map map, Map map2, int i) {
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
            public void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i) {
                DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                boolean z = deviceUtil.isFold() && !deviceUtil.isLargeDisplay(this.this$0.context);
                Log.i("Keyguard-Editor-TemplateApiImpl", "receive wallpaper changed from miWallpaper: type " + str + " which " + i + " foldSmall " + z);
                if (i == 2 || (i == 8 && z)) {
                    this.this$0.localDarkenWallpaperValid = false;
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
            public void onWallpaperFirstFrameRendered(int i) {
            }
        };
    }

    public final void addWallpaperToGallery(Bitmap bitmap, String str, String str2, String str3, boolean z, String str4, boolean z2, Integer num) {
        try {
            String strCopyOriginWallpaper = copyOriginWallpaper(str, str2);
            FashionGalleryHelper.INSTANCE.addWallpaperFromEditorAsync(this.context, str3, copyCroppedWallpaper(z, bitmap, strCopyOriginWallpaper), strCopyOriginWallpaper, z, str4, z2, num);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "addWallpaperToGallery error", e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:139:0x02fd A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:144:0x0326  */
    /* JADX WARN: Code duplicated, block: B:148:0x033c A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:151:0x0344  */
    /* JADX WARN: Code duplicated, block: B:153:0x0347 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:156:0x0356  */
    /* JADX WARN: Code duplicated, block: B:160:0x0369 A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:161:0x0382  */
    /* JADX WARN: Code duplicated, block: B:163:0x038b A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:164:0x03a0  */
    /* JADX WARN: Code duplicated, block: B:167:0x04b3 A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:168:0x04e5  */
    /* JADX WARN: Code duplicated, block: B:170:0x04ef A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:171:0x0551 A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:175:0x058e A[Catch: all -> 0x02d1, LOOP:0: B:173:0x0588->B:175:0x058e, LOOP_END, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:178:0x05a2 A[Catch: all -> 0x02d1, TryCatch #26 {all -> 0x02d1, blocks: (B:127:0x02bc, B:129:0x02c6, B:136:0x02eb, B:137:0x02ef, B:139:0x02fd, B:141:0x0309, B:145:0x0328, B:146:0x032c, B:148:0x033c, B:154:0x0349, B:157:0x0357, B:158:0x035c, B:160:0x0369, B:163:0x038b, B:165:0x03a1, B:167:0x04b3, B:172:0x0578, B:173:0x0588, B:175:0x058e, B:176:0x0598, B:178:0x05a2, B:179:0x05ab, B:184:0x05d7, B:185:0x05dc, B:187:0x0620, B:189:0x062a, B:191:0x0634, B:192:0x065a, B:194:0x0661, B:196:0x06b3, B:198:0x06bd, B:201:0x0701, B:202:0x0707, B:204:0x0715, B:209:0x071e, B:210:0x0721, B:211:0x0727, B:170:0x04ef, B:171:0x0551, B:143:0x0313, B:134:0x02d6), top: B:342:0x02bc }] */
    /* JADX WARN: Code duplicated, block: B:181:0x05d1 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:206:0x0719  */
    /* JADX WARN: Code duplicated, block: B:208:0x071c  */
    /* JADX WARN: Code duplicated, block: B:7:0x001d  */
    /* JADX WARN: Instruction removed from duplicated block: B:170:0x04ef, please report this as an issue */
    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Object applyEditPageTemplate(CommonConfig commonConfig, ScreenshotSource screenshotSource, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Mutex mutex;
        final ScreenshotSource screenshotSource2;
        final Bitmap bitmap4;
        final Bitmap bitmap5;
        final Bitmap bitmap6;
        long j2;
        boolean z6;
        boolean z7;
        final TemplateApiImpl templateApiImpl;
        CommonConfig commonConfig2;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        WallpaperInfo wallpaperInfo;
        boolean z12;
        CommonConfig commonConfig3;
        boolean zApplyWallpaperWithConfig;
        WallpaperChangedListener wallpaperChangedListenerRegisterWallpaperChangedListener;
        HomeWallpaperChangedListener homeWallpaperChangedListenerRegisterHome;
        boolean z13;
        WallpaperPositionInfo positionInfo;
        Float[] offset;
        boolean z14;
        final Ref.ObjectRef objectRef;
        final Ref.ObjectRef objectRef2;
        final Bitmap bitmap7;
        boolean z15;
        Bitmap bitmapCropBitmap;
        TemplateFilePathGenerator.TemplateFileType templateFileType;
        String strGenerateCurrentTemplateFilePath;
        String strSaveAlphaBitmap;
        String strSaveAlphaBitmap2;
        final Ref.ObjectRef objectRef3;
        final CommonConfig commonConfig4;
        final TemplateApiImpl templateApiImpl2;
        final WallpaperInfo wallpaperInfo2;
        final WallpaperPositionInfo wallpaperPositionInfo;
        ScreenshotSource screenshotSource3;
        final String strGenerateCurrentTemplateFilePath2;
        final String strGenerateCurrentTemplateFilePath3;
        final String strGenerateCurrentTemplateFilePath4;
        final Ref.ObjectRef objectRef4;
        final TemplateApiImpl templateApiImpl3;
        final CommonConfig commonConfig5;
        Ref.ObjectRef objectRef5;
        Iterator it;
        WallpaperInfo wallpaperInfo3;
        boolean zIsScreenshotCopyError;
        boolean zIsScreenshotCopyError2;
        Object obj;
        boolean z16;
        boolean z17;
        boolean z18;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj2 = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            mutex = this.mutex;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = commonConfig;
            screenshotSource2 = screenshotSource;
            anonymousClass1.L$2 = screenshotSource2;
            bitmap4 = bitmap;
            anonymousClass1.L$3 = bitmap4;
            bitmap5 = bitmap2;
            anonymousClass1.L$4 = bitmap5;
            bitmap6 = bitmap3;
            anonymousClass1.L$5 = bitmap6;
            anonymousClass1.L$6 = mutex;
            j2 = j;
            anonymousClass1.J$0 = j2;
            z6 = z;
            anonymousClass1.Z$0 = z6;
            z7 = z2;
            anonymousClass1.Z$1 = z7;
            anonymousClass1.Z$2 = z3;
            anonymousClass1.Z$3 = z4;
            anonymousClass1.Z$4 = z5;
            anonymousClass1.label = 1;
            if (mutex.lock(null, anonymousClass1) == coroutine_suspended) {
                return coroutine_suspended;
            }
            templateApiImpl = this;
            commonConfig2 = commonConfig;
            z8 = z3;
            z9 = z4;
            z10 = z5;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            boolean z19 = anonymousClass1.Z$4;
            boolean z20 = anonymousClass1.Z$3;
            boolean z21 = anonymousClass1.Z$2;
            boolean z22 = anonymousClass1.Z$1;
            boolean z23 = anonymousClass1.Z$0;
            long j3 = anonymousClass1.J$0;
            mutex = (Mutex) anonymousClass1.L$6;
            Bitmap bitmap8 = (Bitmap) anonymousClass1.L$5;
            bitmap5 = (Bitmap) anonymousClass1.L$4;
            bitmap4 = (Bitmap) anonymousClass1.L$3;
            ScreenshotSource screenshotSource4 = (ScreenshotSource) anonymousClass1.L$2;
            CommonConfig commonConfig6 = (CommonConfig) anonymousClass1.L$1;
            templateApiImpl = (TemplateApiImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj2);
            z7 = z22;
            bitmap6 = bitmap8;
            z6 = z23;
            j2 = j3;
            z10 = z19;
            commonConfig2 = commonConfig6;
            z9 = z20;
            z8 = z21;
            screenshotSource2 = screenshotSource4;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Companion.reportApplyClickLatency$keyguardeditor_release(jCurrentTimeMillis, "edit");
            Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: apply template from edit page, applyToAll=" + z7 + " shouldAddHistory=" + z6 + " templateSource=" + j2);
            try {
                long j4 = j2;
                boolean z24 = z7;
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.templateApplyStart(null, false, j4, z24, z8, 0, commonConfig2.getLockscreenInfo().getHideFlag());
                    z11 = z24;
                } catch (Exception unused) {
                    j2 = j4;
                    z11 = z24;
                }
            } catch (Exception unused2) {
                z11 = z7;
            }
            boolean zIsThirdPartyWallpaper = commonConfig2.getLockscreenInfo().isThirdPartyWallpaper();
            TemplateHistoryConfig historyTemplate = templateApiImpl.getHistoryTemplate(j2);
            Long lBoxLong = historyTemplate != null ? Boxing.boxLong(historyTemplate.getLastModifiedTime()) : null;
            MagicType magicType = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo4 = commonConfig2.getLockscreenInfo().getWallpaperInfo();
            int iMakeTypeValid = magicType.makeTypeValid(wallpaperInfo4 != null ? Boxing.boxInt(wallpaperInfo4.getMagicType()) : null);
            if (iMakeTypeValid == 20000 && !DeviceUtil.INSTANCE.isSupportHierarchyDevices()) {
                iMakeTypeValid = 0;
            }
            WallpaperInfo wallpaperInfo5 = commonConfig2.getLockscreenInfo().getWallpaperInfo();
            if ((wallpaperInfo5 == null || iMakeTypeValid != wallpaperInfo5.getMagicType()) && (wallpaperInfo = commonConfig2.getLockscreenInfo().getWallpaperInfo()) != null) {
                wallpaperInfo.updateMagicType(iMakeTypeValid);
                Unit unit = Unit.INSTANCE;
            }
            try {
                if (!TemplateSource.INSTANCE.isFromCurrentTemplate(j2) || z6) {
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.step1Start();
                    } catch (Exception unused3) {
                    }
                    templateApiImpl.makeCurrentTemplateToHistory(j2);
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.step1EndSuccess();
                    } catch (Exception unused4) {
                    }
                    Unit unit2 = Unit.INSTANCE;
                } else {
                    Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: apply current template from edit"));
                }
                Context context = templateApiImpl.context;
                WallpaperInfo wallpaperInfo6 = commonConfig2.getLockscreenInfo().getWallpaperInfo();
                String resourceType = wallpaperInfo6 != null ? wallpaperInfo6.getResourceType() : null;
                WallpaperInfo wallpaperInfo7 = commonConfig2.getHomeInfo().getWallpaperInfo();
                templateApiImpl.closeFashionGalleryIfNeeded(context, resourceType, wallpaperInfo7 != null ? wallpaperInfo7.getResourceType() : null);
                boolean z25 = commonConfig2.getHomeInfo().getFollowStatus() == 2 || commonConfig2.getHomeInfo().getFollowStatus() == 1;
                Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: isThirdPartyWallpaper=" + zIsThirdPartyWallpaper);
                if (!zIsThirdPartyWallpaper) {
                    final WallpaperInfo wallpaperInfo8 = commonConfig2.getLockscreenInfo().getWallpaperInfo();
                    WallpaperInfo wallpaperInfo9 = commonConfig2.getHomeInfo().getWallpaperInfo();
                    final WallpaperPositionInfo positionInfo2 = wallpaperInfo8 != null ? wallpaperInfo8.getPositionInfo() : null;
                    final WallpaperPositionInfo positionInfo3 = wallpaperInfo9 != null ? wallpaperInfo9.getPositionInfo() : null;
                    if (wallpaperInfo8 == null) {
                        Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: wallpaper info invalid! wallpaperInfo=" + wallpaperInfo8);
                        try {
                            KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperInfoInvalid(true, wallpaperInfo9 == null);
                        } catch (Exception unused5) {
                        }
                        try {
                            z18 = false;
                            try {
                                KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                            } catch (Exception unused6) {
                            }
                        } catch (Exception unused7) {
                            z18 = false;
                        }
                        Boolean boolBoxBoolean = Boxing.boxBoolean(z18);
                        mutex.unlock(null);
                        return boolBoxBoolean;
                    }
                    if (positionInfo2 == null && WallpaperEditor.Companion.canScale(wallpaperInfo8.getResourceType())) {
                        Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: wallpaper info invalid! wallpaperInfo=" + wallpaperInfo8);
                        try {
                            KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperInfoInvalid(false, wallpaperInfo9 == null);
                        } catch (Exception unused8) {
                        }
                        try {
                            z17 = false;
                            try {
                                KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                            } catch (Exception unused9) {
                            }
                        } catch (Exception unused10) {
                            z17 = false;
                        }
                        Boolean boolBoxBoolean2 = Boxing.boxBoolean(z17);
                        mutex.unlock(null);
                        return boolBoxBoolean2;
                    }
                    ArrayList arrayList = new ArrayList();
                    final Ref.ObjectRef objectRef6 = new Ref.ObjectRef();
                    final Ref.ObjectRef objectRef7 = new Ref.ObjectRef();
                    Mutex mutex2 = mutex;
                    try {
                        try {
                            try {
                                if (positionInfo2 != null) {
                                    z14 = z8;
                                    try {
                                        if (Intrinsics.areEqual(wallpaperInfo8.getResourceType(), "image") || Intrinsics.areEqual(wallpaperInfo8.getResourceType(), "gallery")) {
                                            Deferred deferredRunAsync = templateApiImpl.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$13(objectRef6, templateApiImpl, bitmap4, positionInfo2);
                                                }
                                            });
                                            objectRef7.element = deferredRunAsync;
                                            Boxing.boxBoolean(arrayList.add(deferredRunAsync));
                                        }
                                        objectRef = new Ref.ObjectRef();
                                        objectRef2 = new Ref.ObjectRef();
                                        if (positionInfo3 != null) {
                                            bitmap7 = bitmap4;
                                            if (!Intrinsics.areEqual(wallpaperInfo9.getResourceType(), "image") || Intrinsics.areEqual(wallpaperInfo9.getResourceType(), "gallery")) {
                                                Deferred deferredRunAsync2 = templateApiImpl.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda7
                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$14(objectRef, templateApiImpl, bitmap5, positionInfo3);
                                                    }
                                                });
                                                objectRef2.element = deferredRunAsync2;
                                                Boxing.boxBoolean(arrayList.add(deferredRunAsync2));
                                            }
                                            if (MagicType.INSTANCE.isFrontBack(Boxing.boxInt(wallpaperInfo8.getMagicType())) || wallpaperInfo8.getFrontBackBitmap() == null) {
                                                z15 = false;
                                            } else {
                                                z15 = true;
                                            }
                                            if (positionInfo2 == null && z15) {
                                                Bitmap frontBackBitmap = wallpaperInfo8.getFrontBackBitmap();
                                                Intrinsics.checkNotNull(frontBackBitmap);
                                                bitmapCropBitmap = templateApiImpl.cropBitmap(frontBackBitmap, positionInfo2, true);
                                            } else {
                                                bitmapCropBitmap = null;
                                            }
                                            KeyguardEditorMiSightUtil.INSTANCE.step5Start();
                                            TemplateFilePathGenerator templateFilePathGenerator = templateApiImpl.filePathGenerator;
                                            templateFileType = TemplateFilePathGenerator.TemplateFileType.FRONT_BACK;
                                            strGenerateCurrentTemplateFilePath = templateFilePathGenerator.generateCurrentTemplateFilePath(templateFileType);
                                            FileUtil.delete(strGenerateCurrentTemplateFilePath);
                                            if (bitmapCropBitmap != null) {
                                                strSaveAlphaBitmap = GLRenderHelper.INSTANCE.saveAlphaBitmap(bitmapCropBitmap, strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), TemplateFilePathGenerator.TemplateFileType.CROPPED_FRONT_BACK.getFileName());
                                            } else {
                                                strSaveAlphaBitmap = null;
                                            }
                                            if (z15) {
                                                strSaveAlphaBitmap2 = GLRenderHelper.INSTANCE.saveAlphaBitmap(wallpaperInfo8.getFrontBackBitmap(), strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), templateFileType.getFileName());
                                            } else {
                                                strSaveAlphaBitmap2 = null;
                                            }
                                            objectRef3 = new Ref.ObjectRef();
                                            final Ref.ObjectRef objectRef8 = new Ref.ObjectRef();
                                            commonConfig4 = commonConfig2;
                                            Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda8
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$17(commonConfig4, objectRef3, bitmap7, templateApiImpl, wallpaperInfo8, objectRef7, objectRef6);
                                                }
                                            };
                                            Bitmap bitmap9 = bitmap7;
                                            templateApiImpl2 = templateApiImpl;
                                            Bitmap bitmap10 = bitmapCropBitmap;
                                            arrayList.add(templateApiImpl2.runAsync(function0));
                                            final boolean z26 = z25;
                                            final Bitmap bitmap11 = bitmap5;
                                            Function0 function1 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda9
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$20(commonConfig4, objectRef8, bitmap11, templateApiImpl2, z26, wallpaperInfo8, objectRef2, objectRef);
                                                }
                                            };
                                            wallpaperInfo2 = wallpaperInfo8;
                                            wallpaperPositionInfo = positionInfo2;
                                            arrayList.add(templateApiImpl2.runAsync(function1));
                                            final Ref.ObjectRef objectRef9 = new Ref.ObjectRef();
                                            final Ref.ObjectRef objectRef10 = new Ref.ObjectRef();
                                            arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda10
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$22(objectRef9, templateApiImpl2, screenshotSource2);
                                                }
                                            }));
                                            arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda11
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$24(objectRef10, templateApiImpl2, screenshotSource2);
                                                }
                                            }));
                                            final Ref.ObjectRef objectRef11 = new Ref.ObjectRef();
                                            final Ref.ObjectRef objectRef12 = new Ref.ObjectRef();
                                            arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda12
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$26(objectRef11, templateApiImpl2, screenshotSource2);
                                                }
                                            }));
                                            arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda13
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$28(objectRef12, templateApiImpl2, screenshotSource2);
                                                }
                                            }));
                                            final Ref.ObjectRef objectRef13 = new Ref.ObjectRef();
                                            final Ref.ObjectRef objectRef14 = new Ref.ObjectRef();
                                            String str = strSaveAlphaBitmap2;
                                            arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda14
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$30(objectRef13, templateApiImpl2, screenshotSource2);
                                                }
                                            }));
                                            arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda15
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$32(objectRef14, templateApiImpl2, screenshotSource2);
                                                }
                                            }));
                                            arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda1
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$35(wallpaperInfo2, templateApiImpl2);
                                                }
                                            }));
                                            screenshotSource3 = screenshotSource2;
                                            strGenerateCurrentTemplateFilePath2 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT);
                                            strGenerateCurrentTemplateFilePath3 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK);
                                            strGenerateCurrentTemplateFilePath4 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT);
                                            objectRef4 = new Ref.ObjectRef();
                                            if (MagicType.INSTANCE.isDepthVideo(Boxing.boxInt(wallpaperInfo2.getMagicType()))) {
                                                Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType depthVideo");
                                                final Bitmap videoSubjectBitmap = wallpaperInfo2.getVideoSubjectBitmap();
                                                Function0 function2 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda2
                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$37(this.f$0, videoSubjectBitmap, strGenerateCurrentTemplateFilePath2, wallpaperInfo2, strGenerateCurrentTemplateFilePath4, strGenerateCurrentTemplateFilePath3));
                                                    }
                                                };
                                                templateApiImpl3 = templateApiImpl2;
                                                wallpaperInfo2 = wallpaperInfo2;
                                                Boxing.boxBoolean(arrayList.add(templateApiImpl3.runAsync(function2)));
                                                objectRef5 = objectRef4;
                                                commonConfig5 = commonConfig4;
                                            } else if (bitmap6 != null) {
                                                templateApiImpl3 = templateApiImpl2;
                                                Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType:" + wallpaperInfo2.getMagicType());
                                                arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda3
                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$39(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath4));
                                                    }
                                                }));
                                                arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda4
                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$41(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath3, wallpaperInfo2);
                                                    }
                                                }));
                                                final Bitmap bitmap12 = bitmap6;
                                                Function0 function3 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda5
                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$44(wallpaperPositionInfo, wallpaperInfo2, templateApiImpl3, bitmap12, commonConfig4, strGenerateCurrentTemplateFilePath2, objectRef4);
                                                    }
                                                };
                                                wallpaperInfo2 = wallpaperInfo2;
                                                templateApiImpl3 = templateApiImpl3;
                                                commonConfig5 = commonConfig4;
                                                objectRef5 = objectRef4;
                                                arrayList.add(templateApiImpl3.runAsync(function3));
                                                Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject file copy completed"));
                                            } else {
                                                templateApiImpl3 = templateApiImpl2;
                                                commonConfig5 = commonConfig4;
                                                objectRef5 = objectRef4;
                                                FileUtil.delete(strGenerateCurrentTemplateFilePath2);
                                                FileUtil.delete(strGenerateCurrentTemplateFilePath4);
                                                FileUtil.delete(strGenerateCurrentTemplateFilePath3);
                                                FileUtil.delete(TemplateFilePathGenerator.Companion.getDepthVideoWallpaperDirPath());
                                                Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject == null"));
                                            }
                                            arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda6
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$46(this.f$0, commonConfig5);
                                                }
                                            }));
                                            it = arrayList.iterator();
                                            while (it.hasNext()) {
                                                UtilsKt.waitingFinish((Deferred) it.next());
                                            }
                                            wallpaperInfo3 = commonConfig5.getLockscreenInfo().getWallpaperInfo();
                                            if (wallpaperInfo3 != null) {
                                                wallpaperInfo3.setLargeScreenHierarchyEnable(ScreenshotSourceKt.createScreenHierarchyEnable(screenshotSource3));
                                                Unit unit3 = Unit.INSTANCE;
                                            }
                                            zIsScreenshotCopyError = templateApiImpl3.isScreenshotCopyError((String) objectRef9.element, (String) objectRef11.element, (String) objectRef13.element);
                                            zIsScreenshotCopyError2 = templateApiImpl3.isScreenshotCopyError((String) objectRef10.element, (String) objectRef12.element, (String) objectRef14.element);
                                            obj = objectRef3.element;
                                            if (obj != null || zIsScreenshotCopyError || zIsScreenshotCopyError2) {
                                                KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                                                if (obj == null) {
                                                    z16 = true;
                                                } else {
                                                    z16 = false;
                                                }
                                                keyguardEditorMiSightUtil.reportCopyResourceError(z16, false, zIsScreenshotCopyError, zIsScreenshotCopyError2);
                                                KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                                                Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: newWallpaperPath=" + objectRef3.element + ", newSmallScreenshotPath=" + objectRef9.element + ", newLargePortraitScreenshotPath=" + objectRef11.element + ", newLargeLandscapeScreenshotPath=" + objectRef13.element + " newSmallWallpaperScreenshotPath=" + objectRef10.element + " newLargePortraitWallpaperScreenshotPath=" + objectRef12.element + " newLargeLandscapeWallpaperScreenshotPath=" + objectRef14.element);
                                                Boolean boolBoxBoolean3 = Boxing.boxBoolean(false);
                                                mutex2.unlock(null);
                                                return boolBoxBoolean3;
                                            }
                                            try {
                                                KeyguardEditorMiSightUtil.INSTANCE.step5EndSuccess();
                                            } catch (Exception unused11) {
                                            }
                                            Object obj3 = objectRef3.element;
                                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
                                            wallpaperInfo2.setSource((String) obj3);
                                            String str2 = strSaveAlphaBitmap;
                                            wallpaperInfo2.setCroppedFrontBack(str2);
                                            wallpaperInfo2.setFrontBack(str);
                                            Intrinsics.checkNotNull(wallpaperInfo9);
                                            Object obj4 = objectRef8.element;
                                            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.String");
                                            wallpaperInfo9.setSource((String) obj4);
                                            templateApiImpl3.setOwnerInfo(commonConfig5.getLockscreenInfo());
                                            templateApiImpl3.clearDepthVideoData(commonConfig5);
                                            RandomColorUtil.INSTANCE.updateCurrentRandomColorType(templateApiImpl3.context, commonConfig5.getLockscreenInfo());
                                            wallpaperChangedListenerRegisterWallpaperChangedListener = templateApiImpl3.registerWallpaperChangedListener(z14, commonConfig5);
                                            homeWallpaperChangedListenerRegisterHome = (!z10 || templateApiImpl3.needApplyAll((Bitmap) objectRef5.element, commonConfig5)) ? null : HomeWallpaperChangedListener.Companion.registerHome(templateApiImpl3.context);
                                            WallpaperApplyParam wallpaperApplyParam = new WallpaperApplyParam(str2, bitmap10, null, wallpaperInfo2.getMagicType(), wallpaperInfo2.getMagicTypeHome(), wallpaperInfo2.getEnableBlurHome(), 4, null);
                                            try {
                                                KeyguardEditorMiSightUtil.INSTANCE.step6Start();
                                            } catch (Exception unused12) {
                                            }
                                            Object obj5 = objectRef6.element;
                                            Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type android.graphics.Bitmap");
                                            Bitmap bitmap13 = (Bitmap) obj5;
                                            Object obj6 = objectRef.element;
                                            Intrinsics.checkNotNull(obj6, "null cannot be cast to non-null type android.graphics.Bitmap");
                                            Bitmap bitmap14 = (Bitmap) obj6;
                                            Object obj7 = objectRef3.element;
                                            Intrinsics.checkNotNull(obj7, "null cannot be cast to non-null type kotlin.String");
                                            String str3 = (String) obj7;
                                            Object obj8 = objectRef8.element;
                                            Intrinsics.checkNotNull(obj8, "null cannot be cast to non-null type kotlin.String");
                                            String str4 = (String) obj8;
                                            Bitmap bitmap15 = (Bitmap) objectRef5.element;
                                            WallpaperController.Companion companion = WallpaperController.Companion;
                                            String templateId = commonConfig5.getLockscreenInfo().getClockInfo().getTemplateId();
                                            Integer numBoxInt = Boxing.boxInt(commonConfig5.getLockscreenInfo().getClockInfo().getStyle());
                                            SignatureInfo signatureInfo = commonConfig5.getLockscreenInfo().getSignatureInfo();
                                            CommonConfig commonConfig7 = commonConfig5;
                                            TemplateApiImpl templateApiImpl4 = templateApiImpl3;
                                            zApplyWallpaperWithConfig = templateApiImpl4.applyWallpaperWithConfig(commonConfig7, bitmap13, bitmap14, z11, str3, str4, bitmap15, z9, companion.getPickWallpaperColorInfo(templateId, numBoxInt, signatureInfo != null ? Boxing.boxInt(signatureInfo.getAlignment()) : null), new Pair(Boxing.boxInt(bitmap9.getWidth()), Boxing.boxInt(bitmap9.getHeight())), new Pair(Boxing.boxInt(bitmap11.getWidth()), Boxing.boxInt(bitmap11.getHeight())), wallpaperApplyParam, z10);
                                            templateApiImpl = templateApiImpl4;
                                            commonConfig3 = commonConfig7;
                                            try {
                                                if (zApplyWallpaperWithConfig) {
                                                    KeyguardEditorMiSightUtil.INSTANCE.step6EndSuccess();
                                                } else {
                                                    KeyguardEditorMiSightUtil.step6EndFail$default(KeyguardEditorMiSightUtil.INSTANCE, "wallpaper_apply_failed", null, 2, null);
                                                }
                                            } catch (Exception unused13) {
                                            }
                                            mutex = mutex2;
                                        } else {
                                            bitmap7 = bitmap4;
                                        }
                                        objectRef.element = bitmap5;
                                        Unit unit4 = Unit.INSTANCE;
                                        if (MagicType.INSTANCE.isFrontBack(Boxing.boxInt(wallpaperInfo8.getMagicType()))) {
                                            z15 = false;
                                        } else {
                                            z15 = false;
                                        }
                                        if (positionInfo2 == null) {
                                            bitmapCropBitmap = null;
                                        } else {
                                            bitmapCropBitmap = null;
                                        }
                                        KeyguardEditorMiSightUtil.INSTANCE.step5Start();
                                        TemplateFilePathGenerator templateFilePathGenerator2 = templateApiImpl.filePathGenerator;
                                        templateFileType = TemplateFilePathGenerator.TemplateFileType.FRONT_BACK;
                                        strGenerateCurrentTemplateFilePath = templateFilePathGenerator2.generateCurrentTemplateFilePath(templateFileType);
                                        FileUtil.delete(strGenerateCurrentTemplateFilePath);
                                        if (bitmapCropBitmap != null) {
                                            strSaveAlphaBitmap = GLRenderHelper.INSTANCE.saveAlphaBitmap(bitmapCropBitmap, strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), TemplateFilePathGenerator.TemplateFileType.CROPPED_FRONT_BACK.getFileName());
                                        } else {
                                            strSaveAlphaBitmap = null;
                                        }
                                        if (z15) {
                                            strSaveAlphaBitmap2 = GLRenderHelper.INSTANCE.saveAlphaBitmap(wallpaperInfo8.getFrontBackBitmap(), strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), templateFileType.getFileName());
                                        } else {
                                            strSaveAlphaBitmap2 = null;
                                        }
                                        objectRef3 = new Ref.ObjectRef();
                                        final Ref.ObjectRef objectRef15 = new Ref.ObjectRef();
                                        commonConfig4 = commonConfig2;
                                        Function0 function4 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda8
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$17(commonConfig4, objectRef3, bitmap7, templateApiImpl, wallpaperInfo8, objectRef7, objectRef6);
                                            }
                                        };
                                        Bitmap bitmap16 = bitmap7;
                                        templateApiImpl2 = templateApiImpl;
                                        Bitmap bitmap17 = bitmapCropBitmap;
                                        arrayList.add(templateApiImpl2.runAsync(function4));
                                        final boolean z27 = z25;
                                        final Bitmap bitmap18 = bitmap5;
                                        Function0 function5 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda9
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$20(commonConfig4, objectRef15, bitmap18, templateApiImpl2, z27, wallpaperInfo8, objectRef2, objectRef);
                                            }
                                        };
                                        wallpaperInfo2 = wallpaperInfo8;
                                        wallpaperPositionInfo = positionInfo2;
                                        arrayList.add(templateApiImpl2.runAsync(function5));
                                        final Ref.ObjectRef objectRef16 = new Ref.ObjectRef();
                                        final Ref.ObjectRef objectRef17 = new Ref.ObjectRef();
                                        arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda10
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$22(objectRef16, templateApiImpl2, screenshotSource2);
                                            }
                                        }));
                                        arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda11
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$24(objectRef17, templateApiImpl2, screenshotSource2);
                                            }
                                        }));
                                        final Ref.ObjectRef objectRef18 = new Ref.ObjectRef();
                                        final Ref.ObjectRef objectRef19 = new Ref.ObjectRef();
                                        arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda12
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$26(objectRef18, templateApiImpl2, screenshotSource2);
                                            }
                                        }));
                                        arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda13
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$28(objectRef19, templateApiImpl2, screenshotSource2);
                                            }
                                        }));
                                        final Ref.ObjectRef objectRef110 = new Ref.ObjectRef();
                                        final Ref.ObjectRef objectRef111 = new Ref.ObjectRef();
                                        String str5 = strSaveAlphaBitmap2;
                                        arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda14
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$30(objectRef110, templateApiImpl2, screenshotSource2);
                                            }
                                        }));
                                        arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda15
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$32(objectRef111, templateApiImpl2, screenshotSource2);
                                            }
                                        }));
                                        arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda1
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$35(wallpaperInfo2, templateApiImpl2);
                                            }
                                        }));
                                        screenshotSource3 = screenshotSource2;
                                        strGenerateCurrentTemplateFilePath2 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT);
                                        strGenerateCurrentTemplateFilePath3 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK);
                                        strGenerateCurrentTemplateFilePath4 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT);
                                        objectRef4 = new Ref.ObjectRef();
                                        if (MagicType.INSTANCE.isDepthVideo(Boxing.boxInt(wallpaperInfo2.getMagicType()))) {
                                            Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType depthVideo");
                                            final Bitmap videoSubjectBitmap2 = wallpaperInfo2.getVideoSubjectBitmap();
                                            Function0 function6 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda2
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$37(this.f$0, videoSubjectBitmap2, strGenerateCurrentTemplateFilePath2, wallpaperInfo2, strGenerateCurrentTemplateFilePath4, strGenerateCurrentTemplateFilePath3));
                                                }
                                            };
                                            templateApiImpl3 = templateApiImpl2;
                                            wallpaperInfo2 = wallpaperInfo2;
                                            Boxing.boxBoolean(arrayList.add(templateApiImpl3.runAsync(function6)));
                                            objectRef5 = objectRef4;
                                            commonConfig5 = commonConfig4;
                                        } else if (bitmap6 != null) {
                                            templateApiImpl3 = templateApiImpl2;
                                            Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType:" + wallpaperInfo2.getMagicType());
                                            arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda3
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$39(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath4));
                                                }
                                            }));
                                            arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda4
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$41(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath3, wallpaperInfo2);
                                                }
                                            }));
                                            final Bitmap bitmap19 = bitmap6;
                                            Function0 function7 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda5
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$44(wallpaperPositionInfo, wallpaperInfo2, templateApiImpl3, bitmap19, commonConfig4, strGenerateCurrentTemplateFilePath2, objectRef4);
                                                }
                                            };
                                            wallpaperInfo2 = wallpaperInfo2;
                                            templateApiImpl3 = templateApiImpl3;
                                            commonConfig5 = commonConfig4;
                                            objectRef5 = objectRef4;
                                            arrayList.add(templateApiImpl3.runAsync(function7));
                                            Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject file copy completed"));
                                        } else {
                                            templateApiImpl3 = templateApiImpl2;
                                            commonConfig5 = commonConfig4;
                                            objectRef5 = objectRef4;
                                            FileUtil.delete(strGenerateCurrentTemplateFilePath2);
                                            FileUtil.delete(strGenerateCurrentTemplateFilePath4);
                                            FileUtil.delete(strGenerateCurrentTemplateFilePath3);
                                            FileUtil.delete(TemplateFilePathGenerator.Companion.getDepthVideoWallpaperDirPath());
                                            Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject == null"));
                                        }
                                        arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda6
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$46(this.f$0, commonConfig5);
                                            }
                                        }));
                                        it = arrayList.iterator();
                                        while (it.hasNext()) {
                                            UtilsKt.waitingFinish((Deferred) it.next());
                                        }
                                        wallpaperInfo3 = commonConfig5.getLockscreenInfo().getWallpaperInfo();
                                        if (wallpaperInfo3 != null) {
                                            wallpaperInfo3.setLargeScreenHierarchyEnable(ScreenshotSourceKt.createScreenHierarchyEnable(screenshotSource3));
                                            Unit unit5 = Unit.INSTANCE;
                                        }
                                        zIsScreenshotCopyError = templateApiImpl3.isScreenshotCopyError((String) objectRef16.element, (String) objectRef18.element, (String) objectRef110.element);
                                        zIsScreenshotCopyError2 = templateApiImpl3.isScreenshotCopyError((String) objectRef17.element, (String) objectRef19.element, (String) objectRef111.element);
                                        obj = objectRef3.element;
                                        if (obj != null) {
                                        }
                                        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil2 = KeyguardEditorMiSightUtil.INSTANCE;
                                        if (obj == null) {
                                            z16 = true;
                                        } else {
                                            z16 = false;
                                        }
                                        keyguardEditorMiSightUtil2.reportCopyResourceError(z16, false, zIsScreenshotCopyError, zIsScreenshotCopyError2);
                                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                                        Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: newWallpaperPath=" + objectRef3.element + ", newSmallScreenshotPath=" + objectRef16.element + ", newLargePortraitScreenshotPath=" + objectRef18.element + ", newLargeLandscapeScreenshotPath=" + objectRef110.element + " newSmallWallpaperScreenshotPath=" + objectRef17.element + " newLargePortraitWallpaperScreenshotPath=" + objectRef19.element + " newLargeLandscapeWallpaperScreenshotPath=" + objectRef111.element);
                                        Boolean boolBoxBoolean4 = Boxing.boxBoolean(false);
                                        mutex2.unlock(null);
                                        return boolBoxBoolean4;
                                    } catch (Throwable th) {
                                        th = th;
                                        mutex = mutex2;
                                        mutex.unlock(null);
                                        throw th;
                                    }
                                }
                                z14 = z8;
                                if (positionInfo3 != null) {
                                    bitmap7 = bitmap4;
                                    if (!Intrinsics.areEqual(wallpaperInfo9.getResourceType(), "image")) {
                                    }
                                    Deferred deferredRunAsync3 = templateApiImpl.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda7
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$14(objectRef, templateApiImpl, bitmap5, positionInfo3);
                                        }
                                    });
                                    objectRef2.element = deferredRunAsync3;
                                    Boxing.boxBoolean(arrayList.add(deferredRunAsync3));
                                    if (MagicType.INSTANCE.isFrontBack(Boxing.boxInt(wallpaperInfo8.getMagicType()))) {
                                        z15 = false;
                                    } else {
                                        z15 = false;
                                    }
                                    if (positionInfo2 == null) {
                                        bitmapCropBitmap = null;
                                    } else {
                                        bitmapCropBitmap = null;
                                    }
                                    KeyguardEditorMiSightUtil.INSTANCE.step5Start();
                                    TemplateFilePathGenerator templateFilePathGenerator3 = templateApiImpl.filePathGenerator;
                                    templateFileType = TemplateFilePathGenerator.TemplateFileType.FRONT_BACK;
                                    strGenerateCurrentTemplateFilePath = templateFilePathGenerator3.generateCurrentTemplateFilePath(templateFileType);
                                    FileUtil.delete(strGenerateCurrentTemplateFilePath);
                                    if (bitmapCropBitmap != null) {
                                        strSaveAlphaBitmap = GLRenderHelper.INSTANCE.saveAlphaBitmap(bitmapCropBitmap, strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), TemplateFilePathGenerator.TemplateFileType.CROPPED_FRONT_BACK.getFileName());
                                    } else {
                                        strSaveAlphaBitmap = null;
                                    }
                                    if (z15) {
                                        strSaveAlphaBitmap2 = GLRenderHelper.INSTANCE.saveAlphaBitmap(wallpaperInfo8.getFrontBackBitmap(), strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), templateFileType.getFileName());
                                    } else {
                                        strSaveAlphaBitmap2 = null;
                                    }
                                    objectRef3 = new Ref.ObjectRef();
                                    final Ref.ObjectRef objectRef112 = new Ref.ObjectRef();
                                    commonConfig4 = commonConfig2;
                                    Function0 function8 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda8
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$17(commonConfig4, objectRef3, bitmap7, templateApiImpl, wallpaperInfo8, objectRef7, objectRef6);
                                        }
                                    };
                                    Bitmap bitmap110 = bitmap7;
                                    templateApiImpl2 = templateApiImpl;
                                    Bitmap bitmap111 = bitmapCropBitmap;
                                    arrayList.add(templateApiImpl2.runAsync(function8));
                                    final boolean z28 = z25;
                                    final Bitmap bitmap112 = bitmap5;
                                    Function0 function9 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda9
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$20(commonConfig4, objectRef112, bitmap112, templateApiImpl2, z28, wallpaperInfo8, objectRef2, objectRef);
                                        }
                                    };
                                    wallpaperInfo2 = wallpaperInfo8;
                                    wallpaperPositionInfo = positionInfo2;
                                    arrayList.add(templateApiImpl2.runAsync(function9));
                                    final Ref.ObjectRef objectRef113 = new Ref.ObjectRef();
                                    final Ref.ObjectRef objectRef114 = new Ref.ObjectRef();
                                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda10
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$22(objectRef113, templateApiImpl2, screenshotSource2);
                                        }
                                    }));
                                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda11
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$24(objectRef114, templateApiImpl2, screenshotSource2);
                                        }
                                    }));
                                    final Ref.ObjectRef objectRef115 = new Ref.ObjectRef();
                                    final Ref.ObjectRef objectRef116 = new Ref.ObjectRef();
                                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda12
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$26(objectRef115, templateApiImpl2, screenshotSource2);
                                        }
                                    }));
                                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda13
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$28(objectRef116, templateApiImpl2, screenshotSource2);
                                        }
                                    }));
                                    final Ref.ObjectRef objectRef117 = new Ref.ObjectRef();
                                    final Ref.ObjectRef objectRef118 = new Ref.ObjectRef();
                                    String str6 = strSaveAlphaBitmap2;
                                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda14
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$30(objectRef117, templateApiImpl2, screenshotSource2);
                                        }
                                    }));
                                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda15
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$32(objectRef118, templateApiImpl2, screenshotSource2);
                                        }
                                    }));
                                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda1
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$35(wallpaperInfo2, templateApiImpl2);
                                        }
                                    }));
                                    screenshotSource3 = screenshotSource2;
                                    strGenerateCurrentTemplateFilePath2 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT);
                                    strGenerateCurrentTemplateFilePath3 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK);
                                    strGenerateCurrentTemplateFilePath4 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT);
                                    objectRef4 = new Ref.ObjectRef();
                                    if (MagicType.INSTANCE.isDepthVideo(Boxing.boxInt(wallpaperInfo2.getMagicType()))) {
                                        Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType depthVideo");
                                        final Bitmap videoSubjectBitmap3 = wallpaperInfo2.getVideoSubjectBitmap();
                                        Function0 function10 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda2
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$37(this.f$0, videoSubjectBitmap3, strGenerateCurrentTemplateFilePath2, wallpaperInfo2, strGenerateCurrentTemplateFilePath4, strGenerateCurrentTemplateFilePath3));
                                            }
                                        };
                                        templateApiImpl3 = templateApiImpl2;
                                        wallpaperInfo2 = wallpaperInfo2;
                                        Boxing.boxBoolean(arrayList.add(templateApiImpl3.runAsync(function10)));
                                        objectRef5 = objectRef4;
                                        commonConfig5 = commonConfig4;
                                    } else if (bitmap6 != null) {
                                        templateApiImpl3 = templateApiImpl2;
                                        Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType:" + wallpaperInfo2.getMagicType());
                                        arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda3
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$39(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath4));
                                            }
                                        }));
                                        arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda4
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$41(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath3, wallpaperInfo2);
                                            }
                                        }));
                                        final Bitmap bitmap113 = bitmap6;
                                        Function0 function11 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda5
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$44(wallpaperPositionInfo, wallpaperInfo2, templateApiImpl3, bitmap113, commonConfig4, strGenerateCurrentTemplateFilePath2, objectRef4);
                                            }
                                        };
                                        wallpaperInfo2 = wallpaperInfo2;
                                        templateApiImpl3 = templateApiImpl3;
                                        commonConfig5 = commonConfig4;
                                        objectRef5 = objectRef4;
                                        arrayList.add(templateApiImpl3.runAsync(function11));
                                        Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject file copy completed"));
                                    } else {
                                        templateApiImpl3 = templateApiImpl2;
                                        commonConfig5 = commonConfig4;
                                        objectRef5 = objectRef4;
                                        FileUtil.delete(strGenerateCurrentTemplateFilePath2);
                                        FileUtil.delete(strGenerateCurrentTemplateFilePath4);
                                        FileUtil.delete(strGenerateCurrentTemplateFilePath3);
                                        FileUtil.delete(TemplateFilePathGenerator.Companion.getDepthVideoWallpaperDirPath());
                                        Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject == null"));
                                    }
                                    arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda6
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$46(this.f$0, commonConfig5);
                                        }
                                    }));
                                    it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        UtilsKt.waitingFinish((Deferred) it.next());
                                    }
                                    wallpaperInfo3 = commonConfig5.getLockscreenInfo().getWallpaperInfo();
                                    if (wallpaperInfo3 != null) {
                                        wallpaperInfo3.setLargeScreenHierarchyEnable(ScreenshotSourceKt.createScreenHierarchyEnable(screenshotSource3));
                                        Unit unit6 = Unit.INSTANCE;
                                    }
                                    zIsScreenshotCopyError = templateApiImpl3.isScreenshotCopyError((String) objectRef113.element, (String) objectRef115.element, (String) objectRef117.element);
                                    zIsScreenshotCopyError2 = templateApiImpl3.isScreenshotCopyError((String) objectRef114.element, (String) objectRef116.element, (String) objectRef118.element);
                                    obj = objectRef3.element;
                                    if (obj != null) {
                                    }
                                    KeyguardEditorMiSightUtil keyguardEditorMiSightUtil3 = KeyguardEditorMiSightUtil.INSTANCE;
                                    if (obj == null) {
                                        z16 = true;
                                    } else {
                                        z16 = false;
                                    }
                                    keyguardEditorMiSightUtil3.reportCopyResourceError(z16, false, zIsScreenshotCopyError, zIsScreenshotCopyError2);
                                    KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                                    Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: newWallpaperPath=" + objectRef3.element + ", newSmallScreenshotPath=" + objectRef113.element + ", newLargePortraitScreenshotPath=" + objectRef115.element + ", newLargeLandscapeScreenshotPath=" + objectRef117.element + " newSmallWallpaperScreenshotPath=" + objectRef114.element + " newLargePortraitWallpaperScreenshotPath=" + objectRef116.element + " newLargeLandscapeWallpaperScreenshotPath=" + objectRef118.element);
                                    Boolean boolBoxBoolean5 = Boxing.boxBoolean(false);
                                    mutex2.unlock(null);
                                    return boolBoxBoolean5;
                                }
                                bitmap7 = bitmap4;
                                KeyguardEditorMiSightUtil keyguardEditorMiSightUtil4 = KeyguardEditorMiSightUtil.INSTANCE;
                                if (obj == null) {
                                    z16 = true;
                                } else {
                                    z16 = false;
                                }
                                keyguardEditorMiSightUtil4.reportCopyResourceError(z16, false, zIsScreenshotCopyError, zIsScreenshotCopyError2);
                            } catch (Exception unused14) {
                            }
                            KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                        } catch (Exception unused15) {
                        }
                        KeyguardEditorMiSightUtil.INSTANCE.step5Start();
                    } catch (Exception unused16) {
                    }
                    objectRef6.element = bitmap4;
                    Unit unit7 = Unit.INSTANCE;
                    objectRef = new Ref.ObjectRef();
                    objectRef2 = new Ref.ObjectRef();
                    objectRef.element = bitmap5;
                    Unit unit8 = Unit.INSTANCE;
                    if (MagicType.INSTANCE.isFrontBack(Boxing.boxInt(wallpaperInfo8.getMagicType()))) {
                        z15 = false;
                    } else {
                        z15 = false;
                    }
                    if (positionInfo2 == null) {
                        bitmapCropBitmap = null;
                    } else {
                        bitmapCropBitmap = null;
                    }
                    TemplateFilePathGenerator templateFilePathGenerator4 = templateApiImpl.filePathGenerator;
                    templateFileType = TemplateFilePathGenerator.TemplateFileType.FRONT_BACK;
                    strGenerateCurrentTemplateFilePath = templateFilePathGenerator4.generateCurrentTemplateFilePath(templateFileType);
                    FileUtil.delete(strGenerateCurrentTemplateFilePath);
                    if (bitmapCropBitmap != null) {
                        strSaveAlphaBitmap = GLRenderHelper.INSTANCE.saveAlphaBitmap(bitmapCropBitmap, strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), TemplateFilePathGenerator.TemplateFileType.CROPPED_FRONT_BACK.getFileName());
                    } else {
                        strSaveAlphaBitmap = null;
                    }
                    if (z15) {
                        strSaveAlphaBitmap2 = GLRenderHelper.INSTANCE.saveAlphaBitmap(wallpaperInfo8.getFrontBackBitmap(), strGenerateCurrentTemplateFilePath, Boxing.boxBoolean(true), templateFileType.getFileName());
                    } else {
                        strSaveAlphaBitmap2 = null;
                    }
                    objectRef3 = new Ref.ObjectRef();
                    final Ref.ObjectRef objectRef119 = new Ref.ObjectRef();
                    commonConfig4 = commonConfig2;
                    Function0 function12 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda8
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$17(commonConfig4, objectRef3, bitmap7, templateApiImpl, wallpaperInfo8, objectRef7, objectRef6);
                        }
                    };
                    Bitmap bitmap114 = bitmap7;
                    templateApiImpl2 = templateApiImpl;
                    Bitmap bitmap115 = bitmapCropBitmap;
                    arrayList.add(templateApiImpl2.runAsync(function12));
                    final boolean z29 = z25;
                    final Bitmap bitmap116 = bitmap5;
                    Function0 function13 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda9
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$20(commonConfig4, objectRef119, bitmap116, templateApiImpl2, z29, wallpaperInfo8, objectRef2, objectRef);
                        }
                    };
                    wallpaperInfo2 = wallpaperInfo8;
                    wallpaperPositionInfo = positionInfo2;
                    arrayList.add(templateApiImpl2.runAsync(function13));
                    final Ref.ObjectRef objectRef1110 = new Ref.ObjectRef();
                    final Ref.ObjectRef objectRef1111 = new Ref.ObjectRef();
                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda10
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$22(objectRef1110, templateApiImpl2, screenshotSource2);
                        }
                    }));
                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda11
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$24(objectRef1111, templateApiImpl2, screenshotSource2);
                        }
                    }));
                    final Ref.ObjectRef objectRef1112 = new Ref.ObjectRef();
                    final Ref.ObjectRef objectRef1113 = new Ref.ObjectRef();
                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda12
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$26(objectRef1112, templateApiImpl2, screenshotSource2);
                        }
                    }));
                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda13
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$28(objectRef1113, templateApiImpl2, screenshotSource2);
                        }
                    }));
                    final Ref.ObjectRef objectRef1114 = new Ref.ObjectRef();
                    final Ref.ObjectRef objectRef1115 = new Ref.ObjectRef();
                    String str7 = strSaveAlphaBitmap2;
                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda14
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$30(objectRef1114, templateApiImpl2, screenshotSource2);
                        }
                    }));
                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$32(objectRef1115, templateApiImpl2, screenshotSource2);
                        }
                    }));
                    arrayList.add(templateApiImpl2.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$35(wallpaperInfo2, templateApiImpl2);
                        }
                    }));
                    screenshotSource3 = screenshotSource2;
                    strGenerateCurrentTemplateFilePath2 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT);
                    strGenerateCurrentTemplateFilePath3 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK);
                    strGenerateCurrentTemplateFilePath4 = templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT);
                    objectRef4 = new Ref.ObjectRef();
                    if (MagicType.INSTANCE.isDepthVideo(Boxing.boxInt(wallpaperInfo2.getMagicType()))) {
                        Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType depthVideo");
                        final Bitmap videoSubjectBitmap4 = wallpaperInfo2.getVideoSubjectBitmap();
                        Function0 function14 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$37(this.f$0, videoSubjectBitmap4, strGenerateCurrentTemplateFilePath2, wallpaperInfo2, strGenerateCurrentTemplateFilePath4, strGenerateCurrentTemplateFilePath3));
                            }
                        };
                        templateApiImpl3 = templateApiImpl2;
                        wallpaperInfo2 = wallpaperInfo2;
                        Boxing.boxBoolean(arrayList.add(templateApiImpl3.runAsync(function14)));
                        objectRef5 = objectRef4;
                        commonConfig5 = commonConfig4;
                    } else if (bitmap6 != null) {
                        templateApiImpl3 = templateApiImpl2;
                        Log.d("Keyguard-Editor-TemplateApiImpl", "cropSubject magicType:" + wallpaperInfo2.getMagicType());
                        arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return Integer.valueOf(TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$39(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath4));
                            }
                        }));
                        arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$41(this.f$0, bitmap6, strGenerateCurrentTemplateFilePath3, wallpaperInfo2);
                            }
                        }));
                        final Bitmap bitmap117 = bitmap6;
                        Function0 function15 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda5
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$44(wallpaperPositionInfo, wallpaperInfo2, templateApiImpl3, bitmap117, commonConfig4, strGenerateCurrentTemplateFilePath2, objectRef4);
                            }
                        };
                        wallpaperInfo2 = wallpaperInfo2;
                        templateApiImpl3 = templateApiImpl3;
                        commonConfig5 = commonConfig4;
                        objectRef5 = objectRef4;
                        arrayList.add(templateApiImpl3.runAsync(function15));
                        Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject file copy completed"));
                    } else {
                        templateApiImpl3 = templateApiImpl2;
                        commonConfig5 = commonConfig4;
                        objectRef5 = objectRef4;
                        FileUtil.delete(strGenerateCurrentTemplateFilePath2);
                        FileUtil.delete(strGenerateCurrentTemplateFilePath4);
                        FileUtil.delete(strGenerateCurrentTemplateFilePath3);
                        FileUtil.delete(TemplateFilePathGenerator.Companion.getDepthVideoWallpaperDirPath());
                        Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject == null"));
                    }
                    arrayList.add(templateApiImpl3.runAsync(new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return TemplateApiImpl.applyEditPageTemplate$lambda$50$lambda$46(this.f$0, commonConfig5);
                        }
                    }));
                    it = arrayList.iterator();
                    while (it.hasNext()) {
                        UtilsKt.waitingFinish((Deferred) it.next());
                    }
                    wallpaperInfo3 = commonConfig5.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo3 != null) {
                        wallpaperInfo3.setLargeScreenHierarchyEnable(ScreenshotSourceKt.createScreenHierarchyEnable(screenshotSource3));
                        Unit unit9 = Unit.INSTANCE;
                    }
                    zIsScreenshotCopyError = templateApiImpl3.isScreenshotCopyError((String) objectRef1110.element, (String) objectRef1112.element, (String) objectRef1114.element);
                    zIsScreenshotCopyError2 = templateApiImpl3.isScreenshotCopyError((String) objectRef1111.element, (String) objectRef1113.element, (String) objectRef1115.element);
                    obj = objectRef3.element;
                    if (obj != null) {
                    }
                    Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: newWallpaperPath=" + objectRef3.element + ", newSmallScreenshotPath=" + objectRef1110.element + ", newLargePortraitScreenshotPath=" + objectRef1112.element + ", newLargeLandscapeScreenshotPath=" + objectRef1114.element + " newSmallWallpaperScreenshotPath=" + objectRef1111.element + " newLargePortraitWallpaperScreenshotPath=" + objectRef1113.element + " newLargeLandscapeWallpaperScreenshotPath=" + objectRef1115.element);
                    Boolean boolBoxBoolean6 = Boxing.boxBoolean(false);
                    mutex2.unlock(null);
                    return boolBoxBoolean6;
                }
                lBoxLong = lBoxLong;
                commonConfig3 = commonConfig2;
                templateApiImpl.showCantApplyThirdPartyWallpaperToast();
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportThirdPartyWallpaperBlocked();
                } catch (Exception unused17) {
                }
                zApplyWallpaperWithConfig = false;
                wallpaperChangedListenerRegisterWallpaperChangedListener = null;
                homeWallpaperChangedListenerRegisterHome = null;
                WallpaperInfo wallpaperInfo10 = commonConfig3.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo10 != null && (positionInfo = wallpaperInfo10.getPositionInfo()) != null && (offset = positionInfo.getOffset()) != null) {
                    if (offset.length == 4 && positionInfo.getMatrixArr() != null) {
                        float[] matrixArr = positionInfo.getMatrixArr();
                        Intrinsics.checkNotNull(matrixArr);
                        matrixArr[2] = matrixArr[2] + offset[0].floatValue();
                        float[] matrixArr2 = positionInfo.getMatrixArr();
                        Intrinsics.checkNotNull(matrixArr2);
                        matrixArr2[5] = matrixArr2[5] + offset[1].floatValue();
                    }
                    Unit unit10 = Unit.INSTANCE;
                }
                templateApiImpl.degradeClockEffect(commonConfig3);
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step7Start();
                } catch (Exception unused18) {
                }
                TemplateApi.DefaultImpls.updateCurrentTemplate$default(templateApiImpl, commonConfig3, "apply from edit", false, false, 12, null);
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step7EndSuccess();
                } catch (Exception unused19) {
                }
                templateApiImpl.updateCurrentHistory(commonConfig3);
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step8Start();
                } catch (Exception unused20) {
                }
                templateApiImpl.notifyAppsOnApplyStateChanged();
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step8EndSuccess();
                } catch (Exception unused21) {
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                if (wallpaperChangedListenerRegisterWallpaperChangedListener != null) {
                    wallpaperChangedListenerRegisterWallpaperChangedListener.waitWallpaperChanged(templateApiImpl.context, zApplyWallpaperWithConfig);
                    Unit unit11 = Unit.INSTANCE;
                }
                if (homeWallpaperChangedListenerRegisterHome != null) {
                    homeWallpaperChangedListenerRegisterHome.waitWallpaperChanged(templateApiImpl.context, zApplyWallpaperWithConfig);
                    Unit unit12 = Unit.INSTANCE;
                }
                long jCurrentTimeMillis3 = System.currentTimeMillis() - jCurrentTimeMillis2;
                if (jCurrentTimeMillis3 > 20000) {
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.reportWaitWallpaperChangedTimeout(jCurrentTimeMillis3);
                    } catch (Exception unused22) {
                    }
                }
                templateApiImpl.tryDeleteHistoryTemplateFiles(lBoxLong);
                Log.w("Keyguard-Editor-TemplateApiImpl", "applyEditPageTemplate: complete from edit 。cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
                try {
                    z13 = true;
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(true);
                    } catch (Exception unused23) {
                    }
                } catch (Exception unused24) {
                    z13 = true;
                }
                Boolean boolBoxBoolean7 = Boxing.boxBoolean(z13);
                mutex.unlock(null);
                return boolBoxBoolean7;
            } catch (Exception e) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "applyTemplate error : " + e.getMessage());
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportMakeCurrentToHistoryException(e);
                } catch (Exception unused25) {
                }
                try {
                    z12 = false;
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                    } catch (Exception unused26) {
                    }
                } catch (Exception unused27) {
                    z12 = false;
                }
                Boolean boolBoxBoolean8 = Boxing.boxBoolean(z12);
                mutex.unlock(null);
                return boolBoxBoolean8;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void applyGalleryWallpaper(Bitmap bitmap, boolean z, WallpaperInfo wallpaperInfo, String str, boolean z2, boolean z3, boolean z4, boolean z5) {
        ExtraInfo extraInfo = wallpaperInfo.getExtraInfo();
        Log.d("Keyguard-Editor-TemplateApiImpl", "applyGalleryWallpaper -> isLock=" + z2 + ", addToGallery=" + z4 + ", loopType=" + (extraInfo != null ? extraInfo.getLoopType() : null));
        WallpaperController.Companion.getInstance(this.context).applyFashionGalleryWallpaper(BitmapUtil.INSTANCE.toInputStream(bitmap), wallpaperInfo.getMagicType(), z2, wallpaperInfo.getGalleryContent(), z, z3, z5);
        if (shouldNotifyGallery(z2, z4)) {
            String originResourcePath = wallpaperInfo.getOriginResourcePath();
            String resourceType = wallpaperInfo.getResourceType();
            if (resourceType == null) {
                resourceType = "gallery";
            }
            String str2 = resourceType;
            boolean z6 = wallpaperInfo.getPositionInfo() != null;
            String galleryContent = wallpaperInfo.getGalleryContent();
            ExtraInfo extraInfo2 = wallpaperInfo.getExtraInfo();
            addWallpaperToGallery(bitmap, originResourcePath, str, str2, z6, galleryContent, z2, extraInfo2 != null ? extraInfo2.getLoopType() : null);
        }
    }

    public final void applyGalleryWallpaper(WallpaperInfo wallpaperInfo, String str, boolean z, boolean z2) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyGalleryWallpaper -> wallpaperInfo: " + wallpaperInfo + ",  wallpaperPath: " + str);
        Bitmap bitmapDecodeFile$default = BitmapCreator.decodeFile$default(BitmapCreator.INSTANCE, str, null, 2, null);
        WallpaperPositionInfo positionInfo = wallpaperInfo.getPositionInfo();
        if (bitmapDecodeFile$default != null) {
            if (positionInfo != null) {
                bitmapDecodeFile$default = cropBitmap(bitmapDecodeFile$default, positionInfo, true);
            }
            applyGalleryWallpaper$default(this, bitmapDecodeFile$default, true, wallpaperInfo, str, z, z2, false, false, 192, null);
        } else {
            Log.w("Keyguard-Editor-TemplateApiImpl", "applyGalleryWallpaper: wallpaper == null");
            try {
                KeyguardEditorMiSightUtil.reportApplyWallpaperFailed$default(KeyguardEditorMiSightUtil.INSTANCE, "TemplateApiImpl.applyGalleryWallpaper_wallpaper_null", null, 2, null);
            } catch (Exception unused) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0019  */
    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Object applyHomePageTemplate(String str, boolean z, ScreenshotSource screenshotSource, long j, boolean z2, boolean z3, int i, CommonConfig commonConfig, int i2, Continuation continuation) throws Throwable {
        C00891 c00891;
        Mutex mutex;
        ScreenshotSource screenshotSource2;
        boolean z4;
        long j2;
        boolean z5;
        int i3;
        CommonConfig templateConfig;
        boolean z6;
        int i4;
        String str2;
        boolean z7;
        WallpaperInfo wallpaperInfo;
        TemplateApiImpl templateApiImpl;
        WallpaperChangedListener wallpaperChangedListenerRegisterWallpaperChangedListener;
        boolean z8;
        WallpaperPositionInfo positionInfo;
        Float[] offset;
        boolean z9;
        boolean z10;
        String strCopyBitmap$default;
        Bitmap bitmapTryComposeSubjectWithBackgroundColor;
        String outerVideoPath;
        WallpaperInfo wallpaperInfo2;
        WallpaperInfo wallpaperInfo3;
        ExtraInfo extraInfo;
        LargeScreenHierarchyEnable largeScreenHierarchyEnable;
        WallpaperInfo wallpaperInfo4;
        LargeScreenHierarchyEnable largeScreenHierarchyEnableCreateScreenHierarchyEnable;
        WallpaperInfo wallpaperInfo5;
        boolean z11;
        TemplateApiImpl templateApiImpl2 = this;
        if (continuation instanceof C00891) {
            c00891 = (C00891) continuation;
            int i5 = c00891.label;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                c00891.label = i5 - Integer.MIN_VALUE;
            } else {
                c00891 = templateApiImpl2.new C00891(continuation);
            }
        } else {
            c00891 = templateApiImpl2.new C00891(continuation);
        }
        Object obj = c00891.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i6 = c00891.label;
        if (i6 == 0) {
            ResultKt.throwOnFailure(obj);
            mutex = templateApiImpl2.mutex;
            c00891.L$0 = templateApiImpl2;
            c00891.L$1 = str;
            screenshotSource2 = screenshotSource;
            c00891.L$2 = screenshotSource2;
            c00891.L$3 = commonConfig;
            c00891.L$4 = mutex;
            z4 = z;
            c00891.Z$0 = z4;
            j2 = j;
            c00891.J$0 = j2;
            c00891.Z$1 = z2;
            z5 = z3;
            c00891.Z$2 = z5;
            c00891.I$0 = i;
            i3 = i2;
            c00891.I$1 = i3;
            c00891.label = 1;
            if (mutex.lock(null, c00891) == coroutine_suspended) {
                return coroutine_suspended;
            }
            templateConfig = commonConfig;
            z6 = z2;
            i4 = i;
            str2 = str;
        } else {
            if (i6 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i7 = c00891.I$1;
            int i8 = c00891.I$0;
            boolean z12 = c00891.Z$2;
            z6 = c00891.Z$1;
            long j3 = c00891.J$0;
            boolean z13 = c00891.Z$0;
            mutex = (Mutex) c00891.L$4;
            templateConfig = (CommonConfig) c00891.L$3;
            ScreenshotSource screenshotSource3 = (ScreenshotSource) c00891.L$2;
            str2 = (String) c00891.L$1;
            TemplateApiImpl templateApiImpl3 = (TemplateApiImpl) c00891.L$0;
            ResultKt.throwOnFailure(obj);
            z5 = z12;
            screenshotSource2 = screenshotSource3;
            z4 = z13;
            j2 = j3;
            i3 = i7;
            templateApiImpl2 = templateApiImpl3;
            i4 = i8;
        }
        try {
            Companion.reportApplyClickLatency$keyguardeditor_release(System.currentTimeMillis(), "home");
            EditorActivityViewModel.Companion.updateApplyStatusClone(1);
            Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: apply template from main " + z6);
            try {
                int i9 = i3;
                boolean z14 = z4;
                long j4 = j2;
                boolean z15 = z5;
                String str3 = str2;
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.templateApplyStart(str3, z14, j4, z6, z15, i4, i9);
                    str2 = str3;
                    z4 = z14;
                    j2 = j4;
                    z5 = z15;
                    i3 = i9;
                } catch (Exception unused) {
                    str2 = str3;
                    z4 = z14;
                    j2 = j4;
                    z5 = z15;
                    i3 = i9;
                }
            } catch (Exception unused2) {
            }
            if (TemplateSource.INSTANCE.isFromCurrentTemplate(j2)) {
                Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: apply current template form main");
                EditorActivityViewModel.Companion.updateApplyStatusClone(2);
                try {
                    z11 = true;
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(true);
                    } catch (Exception unused3) {
                    }
                } catch (Exception unused4) {
                    z11 = true;
                }
                Boolean boolBoxBoolean = Boxing.boxBoolean(z11);
                mutex.unlock(null);
                return boolBoxBoolean;
            }
            TemplateHistoryConfig historyTemplate = templateApiImpl2.getHistoryTemplate(j2);
            Long lBoxLong = historyTemplate != null ? Boxing.boxLong(historyTemplate.getLastModifiedTime()) : null;
            try {
                KeyguardEditorMiSightUtil.INSTANCE.step1Start();
            } catch (Exception unused5) {
            }
            try {
                templateApiImpl2.makeCurrentTemplateToHistory(j2);
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step1EndSuccess();
                } catch (Exception unused6) {
                }
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step4Start();
                } catch (Exception unused7) {
                }
                if (templateConfig == null) {
                    Log.d("Keyguard-Editor-TemplateApiImpl", "applyHomePageTemplate: use configUri");
                    templateConfig = TemplateDataUtil.getTemplateConfig(templateApiImpl2.context, str2, z4, i3);
                } else {
                    Log.d("Keyguard-Editor-TemplateApiImpl", "applyHomePageTemplate: use cache config");
                }
                if (templateConfig == null) {
                    Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: applyConfig is null");
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.reportApplyConfigIsNull();
                    } catch (Exception unused8) {
                    }
                    EditorActivityViewModel.Companion.updateApplyStatusClone(2);
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                    } catch (Exception unused9) {
                    }
                    Boolean boolBoxBoolean2 = Boxing.boxBoolean(false);
                    mutex.unlock(null);
                    return boolBoxBoolean2;
                }
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step4EndSuccess();
                } catch (Exception unused10) {
                }
                templateApiImpl2.imageToVideoIfPresetDepthVideo(templateConfig, z4);
                Context context = templateApiImpl2.context;
                WallpaperInfo wallpaperInfo6 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                String resourceType = wallpaperInfo6 != null ? wallpaperInfo6.getResourceType() : null;
                WallpaperInfo wallpaperInfo7 = templateConfig.getHomeInfo().getWallpaperInfo();
                templateApiImpl2.closeFashionGalleryIfNeeded(context, resourceType, wallpaperInfo7 != null ? wallpaperInfo7.getResourceType() : null);
                MagicType magicType = MagicType.INSTANCE;
                WallpaperInfo wallpaperInfo8 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                int iMakeTypeValid = magicType.makeTypeValid(wallpaperInfo8 != null ? Boxing.boxInt(wallpaperInfo8.getMagicType()) : null);
                if (iMakeTypeValid == 20000 && !DeviceUtil.INSTANCE.isSupportHierarchyDevices()) {
                    iMakeTypeValid = 0;
                }
                WallpaperInfo wallpaperInfo9 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                if ((wallpaperInfo9 == null || iMakeTypeValid != wallpaperInfo9.getMagicType()) && (wallpaperInfo = templateConfig.getLockscreenInfo().getWallpaperInfo()) != null) {
                    wallpaperInfo.updateMagicType(iMakeTypeValid);
                    Unit unit = Unit.INSTANCE;
                }
                if (!DeviceUtil.INSTANCE.isPad() && z4) {
                    WallpaperInfo wallpaperInfo10 = templateConfig.getHomeInfo().getWallpaperInfo();
                    if (Intrinsics.areEqual(wallpaperInfo10 != null ? wallpaperInfo10.getResourceType() : null, "image") && (wallpaperInfo5 = templateConfig.getHomeInfo().getWallpaperInfo()) != null) {
                        wallpaperInfo5.setMagicType(30000);
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                templateApiImpl2.degradeClockEffect(templateConfig);
                Boolean boolBoxBoolean3 = historyTemplate != null ? Boxing.boxBoolean(historyTemplate.isThirdPartyWallpaper()) : null;
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step6Start();
                } catch (Exception unused11) {
                }
                if (Intrinsics.areEqual(boolBoxBoolean3, Boxing.boxBoolean(true))) {
                    templateApiImpl = templateApiImpl2;
                    templateApiImpl.showCantApplyThirdPartyWallpaperToast();
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.reportThirdPartyWallpaperBlocked();
                    } catch (Exception unused12) {
                    }
                    wallpaperChangedListenerRegisterWallpaperChangedListener = null;
                } else {
                    WallpaperInfo wallpaperInfo11 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                    WallpaperInfo wallpaperInfo12 = templateConfig.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo11 == null || wallpaperInfo12 == null) {
                        Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: applyLockWallpaperInfo:" + (wallpaperInfo11 == null) + " applyHomeWallpaperInfo:" + (wallpaperInfo12 == null));
                        try {
                            KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperInfoInvalid(wallpaperInfo11 == null, wallpaperInfo12 == null);
                        } catch (Exception unused13) {
                        }
                        try {
                            KeyguardEditorMiSightUtil.step6EndFail$default(KeyguardEditorMiSightUtil.INSTANCE, "wallpaperInfo_null", null, 2, null);
                        } catch (Exception unused14) {
                        }
                        EditorActivityViewModel.Companion.updateApplyStatusClone(2);
                        try {
                            z9 = false;
                            try {
                                KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                            } catch (Exception unused15) {
                            }
                        } catch (Exception unused16) {
                            z9 = false;
                        }
                        Boolean boolBoxBoolean4 = Boxing.boxBoolean(z9);
                        mutex.unlock(null);
                        return boolBoxBoolean4;
                    }
                    FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                    Context context2 = templateApiImpl2.context;
                    ExtraInfo extraInfo2 = wallpaperInfo11.getExtraInfo();
                    if (fashionGalleryHelper.isLoop(context2, true, extraInfo2 != null ? extraInfo2.getLoopType() : null) && MagicType.INSTANCE.isDepth(Boxing.boxInt(wallpaperInfo11.getMagicType()))) {
                        wallpaperInfo11.resetMagicType("Gallery Installed");
                    }
                    boolean zIsPresetDepthVideo = DepthPresetDownloadManager.Companion.isPresetDepthVideo(templateConfig.getLockscreenInfo().getWallpaperInfo(), z4);
                    if (z4 && !zIsPresetDepthVideo) {
                        TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
                        String source = wallpaperInfo11.getSource();
                        Intrinsics.checkNotNull(source);
                        wallpaperInfo11.setOriginResourcePath(companion.getPresetWallpaperAbsolutePath(source));
                    }
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.step5Start();
                    } catch (Exception unused17) {
                    }
                    TemplateApiImpl templateApiImpl4 = templateApiImpl2;
                    String strCopyFile$default = copyFile$default(templateApiImpl4, wallpaperInfo11.getSource(), templateApiImpl2.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER), z4, 1000L, true, false, 32, null);
                    String strCopyFile$default2 = copyFile$default(templateApiImpl4, wallpaperInfo12.getSource(), templateApiImpl4.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME), z4, 1000L, true, false, 32, null);
                    String strSaveScreenshotToCurrentDir$default = saveScreenshotToCurrentDir$default(templateApiImpl4, screenshotSource2.getSmallScreenshot(), screenshotSource2.getHistorySmallScreenshotUri(), TemplateFilePathGenerator.TemplateFileType.SMALL_SCREENSHOT, null, null, 24, null);
                    String strSaveScreenshotToCurrentDir = templateApiImpl4.saveScreenshotToCurrentDir(screenshotSource2.getSmallWallpaperScreenshot(), z4 ? screenshotSource2.getPresetSmallWallpaperScreenshot() : screenshotSource2.getHistorySmallWallpaperScreenshotUri(), TemplateFilePathGenerator.TemplateFileType.SMALL_WALLPAPER_SCREENSHOT, strCopyFile$default2, wallpaperInfo12.getPositionInfo());
                    String strSaveScreenshotToCurrentDir$default2 = saveScreenshotToCurrentDir$default(templateApiImpl4, screenshotSource2.getLargePortraitScreenshot(), screenshotSource2.getHistoryLargePortraitScreenshotUri(), TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_SCREENSHOT, null, null, 24, null);
                    String strSaveScreenshotToCurrentDir2 = templateApiImpl4.saveScreenshotToCurrentDir(screenshotSource2.getLargePortWallpaperScreenshot(), z4 ? screenshotSource2.getPresetLargePortWallpaperScreenshot() : screenshotSource2.getHistoryLargePortWallpaperScreenshotUri(), TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_WALLPAPER_SCREENSHOT, strCopyFile$default2, wallpaperInfo12.getPositionInfo());
                    ScreenshotSource screenshotSource4 = screenshotSource2;
                    String strSaveScreenshotToCurrentDir$default3 = saveScreenshotToCurrentDir$default(templateApiImpl4, screenshotSource2.getLargeLandscapeScreenshot(), screenshotSource2.getHistoryLargeLandscapeScreenshotUri(), TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_SCREENSHOT, null, null, 24, null);
                    String strSaveScreenshotToCurrentDir3 = templateApiImpl4.saveScreenshotToCurrentDir(screenshotSource4.getLargeLandWallpaperScreenshot(), z4 ? screenshotSource4.getPresetLargeLandWallpaperScreenshot() : screenshotSource4.getHistoryLargeLandWallpaperScreenshotUri(), TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT, strCopyFile$default2, wallpaperInfo12.getPositionInfo());
                    boolean z16 = z4;
                    String strCopyFile$default3 = copyFile$default(templateApiImpl4, zIsPresetDepthVideo ? wallpaperInfo11.getSource() : wallpaperInfo11.getDepthVideoScreenShot(), templateApiImpl4.filePathGenerator.generateDepthVideoWallpaperFilePath(TemplateFilePathGenerator.TemplateFileType.DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK), z16, 1000L, true, false, 32, null);
                    templateApiImpl = templateApiImpl4;
                    Mutex mutex2 = mutex;
                    if (strCopyFile$default3 != null) {
                        try {
                            FileUtil.chmod(TemplateFilePathGenerator.Companion.getDepthVideoWallpaperDirPath(), 511);
                            FileUtil.chmod(strCopyFile$default3, 511);
                        } catch (Throwable th) {
                            th = th;
                            mutex = mutex2;
                        }
                    } else {
                        strCopyFile$default3 = null;
                    }
                    templateApiImpl.copyVideoForLivePhotoResource(templateConfig);
                    WallpaperInfo wallpaperInfo13 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo13 != null) {
                        WallpaperInfo wallpaperInfo14 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                        if (wallpaperInfo14 == null || (largeScreenHierarchyEnableCreateScreenHierarchyEnable = wallpaperInfo14.getLargeScreenHierarchyEnable()) == null) {
                            largeScreenHierarchyEnableCreateScreenHierarchyEnable = ScreenshotSourceKt.createScreenHierarchyEnable(screenshotSource4);
                        }
                        wallpaperInfo13.setLargeScreenHierarchyEnable(largeScreenHierarchyEnableCreateScreenHierarchyEnable);
                        Unit unit3 = Unit.INSTANCE;
                    }
                    WallpaperInfo wallpaperInfo15 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                    if ((wallpaperInfo15 != null ? wallpaperInfo15.getLargeScreenHierarchyEnable() : null) == null && (wallpaperInfo4 = templateConfig.getLockscreenInfo().getWallpaperInfo()) != null) {
                        wallpaperInfo4.setLargeScreenHierarchyEnable(new LargeScreenHierarchyEnable());
                        Unit unit4 = Unit.INSTANCE;
                    }
                    WallpaperInfo wallpaperInfo16 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo16 != null && (largeScreenHierarchyEnable = wallpaperInfo16.getLargeScreenHierarchyEnable()) != null) {
                        largeScreenHierarchyEnable.updateFrom(ScreenshotSourceKt.getPresetAvoidResult());
                        Unit unit5 = Unit.INSTANCE;
                    }
                    boolean zIsScreenshotCopyError = templateApiImpl.isScreenshotCopyError(strSaveScreenshotToCurrentDir$default, strSaveScreenshotToCurrentDir$default2, strSaveScreenshotToCurrentDir$default3);
                    boolean zIsScreenshotCopyError2 = templateApiImpl.isScreenshotCopyError(strSaveScreenshotToCurrentDir, strSaveScreenshotToCurrentDir2, strSaveScreenshotToCurrentDir3);
                    if (strCopyFile$default == null || zIsScreenshotCopyError || zIsScreenshotCopyError2 || strCopyFile$default2 == null) {
                        Log.w("Keyguard-Editor-TemplateApiImpl", "applyTemplate: newHomeWallpaperPath=" + strCopyFile$default2 + " newLockWallpaperPath=" + strCopyFile$default + ", newSmallScreenshotPath=" + strSaveScreenshotToCurrentDir$default + ",  newLargePortraitScreenshotPath=" + strSaveScreenshotToCurrentDir$default2 + ",  newLargeLandscapeScreenshotPath=" + strSaveScreenshotToCurrentDir$default3 + "  newSmallWallpaperScreenshotPath=" + strSaveScreenshotToCurrentDir + ",  newLargePortraitWallpaperScreenshotPath=" + strSaveScreenshotToCurrentDir2 + ",  newLargeLandscapeWallpaperScreenshotPath=" + strSaveScreenshotToCurrentDir3 + " newVideoDepthScreenShotPath=" + strCopyFile$default3);
                        try {
                            KeyguardEditorMiSightUtil.INSTANCE.reportCopyResourceError(strCopyFile$default == null, strCopyFile$default2 == null, zIsScreenshotCopyError, zIsScreenshotCopyError2);
                        } catch (Exception unused18) {
                        }
                        EditorActivityViewModel.Companion.updateApplyStatusClone(2);
                        try {
                            z10 = false;
                            try {
                                KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                            } catch (Exception unused19) {
                            }
                        } catch (Exception unused20) {
                            z10 = false;
                        }
                        Boolean boolBoxBoolean5 = Boxing.boxBoolean(z10);
                        mutex2.unlock(null);
                        return boolBoxBoolean5;
                    }
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.step5EndSuccess();
                    } catch (Exception unused21) {
                    }
                    String strGenerateCurrentTemplateFilePath = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK);
                    TemplateFilePathGenerator templateFilePathGenerator = templateApiImpl.filePathGenerator;
                    TemplateFilePathGenerator.TemplateFileType templateFileType = TemplateFilePathGenerator.TemplateFileType.SUBJECT;
                    String strGenerateCurrentTemplateFilePath2 = templateFilePathGenerator.generateCurrentTemplateFilePath(templateFileType);
                    String strGenerateCurrentTemplateFilePath3 = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT);
                    if (z16) {
                        Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: compose preset subject files");
                        Bitmap subjectBitmap = wallpaperInfo11.getSubjectBitmap();
                        if (subjectBitmap == null) {
                            Log.d("Keyguard-Editor-TemplateApiImpl", "applyHomePageTemplate: use compose subject");
                            subjectBitmap = TemplatePreviewImageComposer.INSTANCE.composeSubjectImage(wallpaperInfo11, true);
                        } else {
                            Log.d("Keyguard-Editor-TemplateApiImpl", "applyHomePageTemplate: use cache subject");
                        }
                        String mask = wallpaperInfo11.getMask();
                        if (zIsPresetDepthVideo) {
                            wallpaperInfo11.setSubjectBitmap(TemplatePreviewImageComposer.composeSubjectImage$default(TemplatePreviewImageComposer.INSTANCE, wallpaperInfo11, false, 2, null));
                            String strCopyBitmap$default2 = copyBitmap$default(templateApiImpl, wallpaperInfo11.getSubjectBitmap(), strGenerateCurrentTemplateFilePath3, true, 0L, false, true, 24, null);
                            Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: depthVideo corpSubject=" + strCopyBitmap$default2);
                            FileUtil.chmod(strCopyBitmap$default2, 511);
                            wallpaperInfo11.setCropSubject(strCopyBitmap$default2);
                            FileUtil.delete(strGenerateCurrentTemplateFilePath2);
                            FileUtil.delete(strGenerateCurrentTemplateFilePath);
                            Unit unit6 = Unit.INSTANCE;
                        } else if (subjectBitmap == null || mask == null) {
                            FileUtil.delete(strGenerateCurrentTemplateFilePath2);
                            Boxing.boxInt(Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: subject is null"));
                        } else {
                            Bitmap bitmapTryComposeSubjectWithBackgroundColor2 = templateApiImpl.tryComposeSubjectWithBackgroundColor(templateConfig, subjectBitmap);
                            Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: save preset subject");
                            BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
                            if (BitmapUtil.toWebp$default(bitmapUtil, subjectBitmap, strGenerateCurrentTemplateFilePath2, false, 4, null)) {
                                FileUtil.chmod(strGenerateCurrentTemplateFilePath2, 511);
                            }
                            copyFile$default(templateApiImpl, TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(mask), strGenerateCurrentTemplateFilePath, false, 1000L, true, false, 36, null);
                            Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: save subject result " + bitmapUtil.toWebp(subjectBitmap, strGenerateCurrentTemplateFilePath3, true));
                            FileUtil.chmod(strGenerateCurrentTemplateFilePath3, 511);
                            wallpaperInfo11.setSubject(strGenerateCurrentTemplateFilePath);
                            wallpaperInfo11.setCropSubject(strGenerateCurrentTemplateFilePath3);
                            Unit unit7 = Unit.INSTANCE;
                            bitmapTryComposeSubjectWithBackgroundColor = bitmapTryComposeSubjectWithBackgroundColor2;
                        }
                        bitmapTryComposeSubjectWithBackgroundColor = null;
                    } else {
                        Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: copy history subject files");
                        String strCopySubjectFromHistoryCompact = templateApiImpl.copySubjectFromHistoryCompact(wallpaperInfo11.getSubject(), wallpaperInfo11.getMask());
                        if (strCopySubjectFromHistoryCompact == null) {
                            strCopyBitmap$default = null;
                        } else if (StringsKt.endsWith$default(strCopySubjectFromHistoryCompact, templateFileType.getFileName(), false, 2, (Object) null)) {
                            strCopyBitmap$default = strCopySubjectFromHistoryCompact;
                        } else {
                            Bitmap subjectBitmap2 = wallpaperInfo11.getSubjectBitmap();
                            if (subjectBitmap2 == null) {
                                subjectBitmap2 = TemplatePreviewImageComposer.INSTANCE.composeSubjectImage(strCopyFile$default, strCopySubjectFromHistoryCompact, false);
                            }
                            strCopyBitmap$default = copyBitmap$default(templateApiImpl, subjectBitmap2, strGenerateCurrentTemplateFilePath2, true, 0L, false, false, 56, null);
                        }
                        String strCopyFile$default4 = copyFile$default(templateApiImpl, wallpaperInfo11.getCropSubject(), strGenerateCurrentTemplateFilePath3, false, 1000L, true, true, 4, null);
                        bitmapTryComposeSubjectWithBackgroundColor = templateApiImpl.tryComposeSubjectWithBackgroundColor(templateConfig, strCopyFile$default4);
                        Log.i("Keyguard-Editor-TemplateApiImpl", "applyTemplate: copiedSubjectOrMaskPath=" + strCopySubjectFromHistoryCompact + ",newOriginalSubjectPath=" + strCopyBitmap$default + ",newCropSubjectPath=" + strCopyFile$default4);
                        FileUtil.chmod(strCopyBitmap$default, 511);
                        FileUtil.chmod(strCopyFile$default4, 511);
                        wallpaperInfo11.setSubject(strCopySubjectFromHistoryCompact);
                        wallpaperInfo11.setCropSubject(strCopyFile$default4);
                        Unit unit8 = Unit.INSTANCE;
                    }
                    SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo11.getSensorWallpaperPrams();
                    if (sensorWallpaperPrams == null || (outerVideoPath = sensorWallpaperPrams.getOuterVideoPath()) == null) {
                        SensorWallpaperPrams sensorWallpaperPrams2 = wallpaperInfo12.getSensorWallpaperPrams();
                        outerVideoPath = sensorWallpaperPrams2 != null ? sensorWallpaperPrams2.getOuterVideoPath() : null;
                    }
                    wallpaperInfo11.setSource(strCopyFile$default);
                    wallpaperInfo12.setSource(strCopyFile$default2);
                    templateApiImpl.setOwnerInfo(templateConfig.getLockscreenInfo());
                    templateApiImpl.clearDepthVideoData(templateConfig);
                    wallpaperChangedListenerRegisterWallpaperChangedListener = templateApiImpl.registerWallpaperChangedListener(z5, templateConfig);
                    WallpaperApplyParam wallpaperApplyParam = new WallpaperApplyParam(null, null, null, -1, wallpaperInfo11.getMagicTypeHome(), wallpaperInfo11.getEnableBlurHome());
                    ThemeUtil themeUtil = ThemeUtil.INSTANCE;
                    themeUtil.addHistoryWallpaper(templateApiImpl.context, wallpaperInfo11);
                    if (!z16 && templateConfig.getHomeInfo().getFollowStatus() == 0) {
                        themeUtil.addHistoryWallpaper(templateApiImpl.context, wallpaperInfo12);
                    }
                    if (templateApiImpl.needApplyAll(bitmapTryComposeSubjectWithBackgroundColor, templateConfig) || (z16 && MagicType.INSTANCE.isDepthVideo(Boxing.boxInt(wallpaperInfo11.getMagicType())))) {
                        wallpaperInfo2 = wallpaperInfo11;
                        wallpaperInfo3 = wallpaperInfo12;
                        WallpaperController.Companion companion2 = WallpaperController.Companion;
                        String templateId = templateConfig.getLockscreenInfo().getClockInfo().getTemplateId();
                        Integer numBoxInt = Boxing.boxInt(templateConfig.getLockscreenInfo().getClockInfo().getStyle());
                        SignatureInfo signatureInfo = templateConfig.getLockscreenInfo().getSignatureInfo();
                        templateApiImpl.applyWallpaperWithInfo(wallpaperInfo2, strCopyFile$default, true, bitmapTryComposeSubjectWithBackgroundColor, companion2.getPickWallpaperColorInfo(templateId, numBoxInt, signatureInfo != null ? Boxing.boxInt(signatureInfo.getAlignment()) : null), wallpaperApplyParam, true);
                        if (Intrinsics.areEqual(wallpaperInfo3.getResourceType(), "video")) {
                            String strCopyFile$default5 = copyFile$default(templateApiImpl, wallpaperInfo2.getOriginResourcePath(), templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME), false, 1000L, true, false, 36, null);
                            if (strCopyFile$default5 == null) {
                                strCopyFile$default5 = wallpaperInfo2.getOriginResourcePath();
                            }
                            wallpaperInfo3.setOriginResourcePath(strCopyFile$default5);
                        } else {
                            wallpaperInfo3.setOriginResourcePath(wallpaperInfo2.getOriginResourcePath());
                        }
                        wallpaperInfo3.setSensorWallpaperPrams(wallpaperInfo2.getSensorWallpaperPrams());
                        String resourceType2 = wallpaperInfo3.getResourceType();
                        String str4 = resourceType2 == null ? "image" : resourceType2;
                        WallpaperInfo wallpaperInfo17 = templateConfig.getHomeInfo().getWallpaperInfo();
                        templateApiImpl.notifyGalleryOnApply(false, str4, (wallpaperInfo17 == null || (extraInfo = wallpaperInfo17.getExtraInfo()) == null) ? null : extraInfo.getLoopType());
                    } else {
                        templateApiImpl.canDeleteWhenApplying = false;
                        templateApiImpl.applyWallpaperWithInfo(wallpaperInfo12, strCopyFile$default2, false, null, null, WallpaperApplyParam.Companion.defaultInstance(), false);
                        wallpaperInfo3 = wallpaperInfo12;
                        templateApiImpl.canDeleteWhenApplying = true;
                        WallpaperController.Companion companion3 = WallpaperController.Companion;
                        String templateId2 = templateConfig.getLockscreenInfo().getClockInfo().getTemplateId();
                        Integer numBoxInt2 = Boxing.boxInt(templateConfig.getLockscreenInfo().getClockInfo().getStyle());
                        SignatureInfo signatureInfo2 = templateConfig.getLockscreenInfo().getSignatureInfo();
                        templateApiImpl.applyWallpaperWithInfo(wallpaperInfo11, strCopyFile$default, true, bitmapTryComposeSubjectWithBackgroundColor, companion3.getPickWallpaperColorInfo(templateId2, numBoxInt2, signatureInfo2 != null ? Boxing.boxInt(signatureInfo2.getAlignment()) : null), wallpaperApplyParam, false);
                        wallpaperInfo2 = wallpaperInfo11;
                    }
                    templateApiImpl.applyQ18OuterVideo(wallpaperInfo2, wallpaperInfo3, outerVideoPath);
                    mutex = mutex2;
                }
                if (!Intrinsics.areEqual(boolBoxBoolean3, Boxing.boxBoolean(true))) {
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.step6EndSuccess();
                    } catch (Exception unused22) {
                    }
                }
                WallpaperInfo wallpaperInfo18 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo18 != null && (positionInfo = wallpaperInfo18.getPositionInfo()) != null && (offset = positionInfo.getOffset()) != null) {
                    if (offset.length == 4 && positionInfo.getMatrixArr() != null) {
                        float[] matrixArr = positionInfo.getMatrixArr();
                        Intrinsics.checkNotNull(matrixArr);
                        matrixArr[2] = matrixArr[2] + offset[0].floatValue();
                        float[] matrixArr2 = positionInfo.getMatrixArr();
                        Intrinsics.checkNotNull(matrixArr2);
                        matrixArr2[5] = matrixArr2[5] + offset[1].floatValue();
                    }
                    Unit unit9 = Unit.INSTANCE;
                }
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step7Start();
                } catch (Exception unused23) {
                }
                TemplateApi.DefaultImpls.updateCurrentTemplate$default(templateApiImpl, templateConfig, "apply from home", false, false, 12, null);
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step7EndSuccess();
                } catch (Exception unused24) {
                }
                templateApiImpl.updateCurrentHistory(templateConfig);
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step8Start();
                } catch (Exception unused25) {
                }
                templateApiImpl.notifyAppsOnApplyStateChanged();
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step8EndSuccess();
                } catch (Exception unused26) {
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (wallpaperChangedListenerRegisterWallpaperChangedListener != null) {
                    WallpaperChangedListener.waitWallpaperChanged$default(wallpaperChangedListenerRegisterWallpaperChangedListener, templateApiImpl.context, false, 2, null);
                    Unit unit10 = Unit.INSTANCE;
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                if (jCurrentTimeMillis2 > 20000) {
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.reportWaitWallpaperChangedTimeout(jCurrentTimeMillis2);
                    } catch (Exception unused27) {
                    }
                }
                templateApiImpl.tryDeleteHistoryTemplateFiles(lBoxLong);
                Log.w("Keyguard-Editor-TemplateApiImpl", "applyHomePageTemplate: complete from main");
                EditorActivityViewModel.Companion.updateApplyStatusClone(2);
                try {
                    z8 = true;
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(true);
                    } catch (Exception unused28) {
                    }
                } catch (Exception unused29) {
                    z8 = true;
                }
                Boolean boolBoxBoolean6 = Boxing.boxBoolean(z8);
                mutex.unlock(null);
                return boolBoxBoolean6;
            } catch (Exception e) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "applyTemplate error : " + e.getMessage());
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportMakeCurrentToHistoryException(e);
                } catch (Exception unused30) {
                }
                EditorActivityViewModel.Companion.updateApplyStatusClone(2);
                try {
                    z7 = false;
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                    } catch (Exception unused31) {
                    }
                } catch (Exception unused32) {
                    z7 = false;
                }
                Boolean boolBoxBoolean7 = Boxing.boxBoolean(z7);
                mutex.unlock(null);
                return boolBoxBoolean7;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        mutex.unlock(null);
        throw th;
    }

    public final void applyImageWallpaper(Bitmap bitmap, boolean z, String str, WallpaperInfo wallpaperInfo, boolean z2, boolean z3, boolean z4, Bitmap bitmap2, Pair pair, WallpaperApplyParam wallpaperApplyParam, boolean z5) {
        WallpaperController.Companion.getInstance(this.context).applyImageLockWallpaper(bitmap2 == null ? bitmap : bitmap2, wallpaperInfo.getMagicType(), z3, z, wallpaperInfo.isNeedDark(), z4, wallpaperInfo.getSupportSubject() && !z2, z2 ? 1 : wallpaperInfo.getEnableDoodle(), pair, wallpaperApplyParam, z5);
        if (shouldNotifyGallery(z3, z2)) {
            String originResourcePath = wallpaperInfo.getOriginResourcePath();
            String resourceType = wallpaperInfo.getResourceType();
            if (resourceType == null) {
                resourceType = "image";
            }
            String str2 = resourceType;
            boolean z6 = wallpaperInfo.getPositionInfo() != null;
            String galleryContent = wallpaperInfo.getGalleryContent();
            ExtraInfo extraInfo = wallpaperInfo.getExtraInfo();
            addWallpaperToGallery(bitmap, originResourcePath, str, str2, z6, galleryContent, z3, extraInfo != null ? extraInfo.getLoopType() : null);
        }
    }

    public final void applyImageWallpaper(WallpaperInfo wallpaperInfo, String str, boolean z, boolean z2, boolean z3, Bitmap bitmap, WallpaperApplyParam wallpaperApplyParam, boolean z4) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyImageWallpaper -> isLock=" + z2 + " isGalleryOpened: " + z + ", wallpaperPath: " + str + " applyAll=" + z4 + ", wallpaperInfo: " + wallpaperInfo);
        Bitmap sourceWallpaper = wallpaperApplyParam.getSourceWallpaper();
        if (sourceWallpaper == null) {
            sourceWallpaper = BitmapCreator.decodeFile$default(BitmapCreator.INSTANCE, str, null, 2, null);
        }
        WallpaperPositionInfo positionInfo = wallpaperInfo.getPositionInfo();
        if (sourceWallpaper != null) {
            applyImageWallpaper(positionInfo != null ? cropBitmap(sourceWallpaper, positionInfo, true) : sourceWallpaper, true, str, wallpaperInfo, z, z2, z3, bitmap, new Pair(Integer.valueOf(sourceWallpaper.getWidth()), Integer.valueOf(sourceWallpaper.getHeight())), wallpaperApplyParam, z4);
            return;
        }
        Log.w("Keyguard-Editor-TemplateApiImpl", "applyImageWallpaper: wallpaper == null");
        try {
            KeyguardEditorMiSightUtil.reportApplyWallpaperFailed$default(KeyguardEditorMiSightUtil.INSTANCE, "TemplateApiImpl.applyImageWallpaper_wallpaper_null", null, 2, null);
        } catch (Exception unused) {
        }
    }

    public final void applyQ18OuterVideo(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, String str) {
        Object next;
        SensorWallpaperPrams sensorWallpaperPrams;
        SensorWallpaperPrams sensorWallpaperPrams2;
        SensorWallpaperPrams sensorWallpaperPrams3;
        SensorWallpaperPrams sensorWallpaperPrams4;
        for (WallpaperInfo wallpaperInfo3 : CollectionsKt.listOfNotNull((Object[]) new WallpaperInfo[]{wallpaperInfo, wallpaperInfo2})) {
            WallpaperController.Companion.fillQ18SensorOuterVideoIfNeeded$default(WallpaperController.Companion, wallpaperInfo3.getResourceType(), wallpaperInfo3.getSensorWallpaperPrams(), null, CollectionsKt.listOf((Object[]) new String[]{wallpaperInfo3.getOriginResourcePath(), wallpaperInfo3.getSource(), str}), 4, null);
        }
        boolean zShouldApplyOuterVideoWallpaper = shouldApplyOuterVideoWallpaper(wallpaperInfo);
        boolean zShouldApplyOuterVideoWallpaper2 = shouldApplyOuterVideoWallpaper(wallpaperInfo2);
        String resourceType = null;
        if (!zShouldApplyOuterVideoWallpaper && !zShouldApplyOuterVideoWallpaper2) {
            String resourceType2 = wallpaperInfo != null ? wallpaperInfo.getResourceType() : null;
            String resourceType3 = wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null;
            String originResourcePath = wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null;
            String originResourcePath2 = wallpaperInfo2 != null ? wallpaperInfo2.getOriginResourcePath() : null;
            String outerVideoPath = (wallpaperInfo == null || (sensorWallpaperPrams4 = wallpaperInfo.getSensorWallpaperPrams()) == null) ? null : sensorWallpaperPrams4.getOuterVideoPath();
            if (wallpaperInfo2 != null && (sensorWallpaperPrams3 = wallpaperInfo2.getSensorWallpaperPrams()) != null) {
                resourceType = sensorWallpaperPrams3.getOuterVideoPath();
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "applyQ18OuterVideo: skip, no explicit outer video resolved, lockType=" + resourceType2 + ", homeType=" + resourceType3 + ", lockPath=" + originResourcePath + ", homePath=" + originResourcePath2 + ", lockOuter=" + outerVideoPath + ", homeOuter=" + resourceType);
            return;
        }
        WallpaperController companion = WallpaperController.Companion.getInstance(this.context);
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new WallpaperInfo[]{wallpaperInfo, wallpaperInfo2});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOfNotNull, 10));
        Iterator it = listListOfNotNull.iterator();
        while (it.hasNext()) {
            arrayList.add(((WallpaperInfo) it.next()).getSensorWallpaperPrams());
        }
        Iterator it2 = arrayList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            SensorWallpaperPrams sensorWallpaperPrams5 = (SensorWallpaperPrams) next;
            String outerVideoPath2 = sensorWallpaperPrams5 != null ? sensorWallpaperPrams5.getOuterVideoPath() : null;
            if (outerVideoPath2 != null && outerVideoPath2.length() != 0) {
                break;
            }
            String outerVideoPreViewPath = sensorWallpaperPrams5 != null ? sensorWallpaperPrams5.getOuterVideoPreViewPath() : null;
            if (outerVideoPreViewPath != null && outerVideoPreViewPath.length() != 0) {
                break;
            }
        }
        SensorWallpaperPrams sensorWallpaperPrams6 = (SensorWallpaperPrams) next;
        if (sensorWallpaperPrams6 == null) {
            sensorWallpaperPrams6 = wallpaperInfo != null ? wallpaperInfo.getSensorWallpaperPrams() : null;
            if (sensorWallpaperPrams6 == null) {
                sensorWallpaperPrams6 = wallpaperInfo2 != null ? wallpaperInfo2.getSensorWallpaperPrams() : null;
            }
        }
        String strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.OUTER_VIDEO_WALLPAPER);
        WallpaperController.Companion companion2 = WallpaperController.Companion;
        String strFirstValidPath = companion2.firstValidPath(sensorWallpaperPrams6 != null ? sensorWallpaperPrams6.getOuterVideoPath() : null, strGenerateCurrentTemplateFilePath, str);
        String strFirstValidPath2 = companion2.firstValidPath(sensorWallpaperPrams6 != null ? sensorWallpaperPrams6.getOuterVideoPreViewPath() : null, (wallpaperInfo == null || (sensorWallpaperPrams2 = wallpaperInfo.getSensorWallpaperPrams()) == null) ? null : sensorWallpaperPrams2.getOuterVideoPreViewPath(), (wallpaperInfo2 == null || (sensorWallpaperPrams = wallpaperInfo2.getSensorWallpaperPrams()) == null) ? null : sensorWallpaperPrams.getOuterVideoPreViewPath());
        String resourceType4 = wallpaperInfo != null ? wallpaperInfo.getResourceType() : null;
        resourceType = wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null;
        Log.d("Keyguard-Editor-TemplateApiImpl", "applyQ18OuterVideo: outerVideoPath=" + strFirstValidPath + ", lockType=" + resourceType4 + ", homeType=" + resourceType + ", needApplyHomeVideo=" + zShouldApplyOuterVideoWallpaper2 + ", needApplyLockVideo=" + zShouldApplyOuterVideoWallpaper + ", originalOuterVideoPath=" + str + ", currentOuterVideo=" + strGenerateCurrentTemplateFilePath + " exist=" + FileUtil.isFileExist(strGenerateCurrentTemplateFilePath));
        if (strFirstValidPath == null) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "applyQ18OuterVideo: no outer video path available, skip");
            return;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyQ18OuterVideo: outerVideoPath=" + strFirstValidPath + ", outerPreviewPath=" + strFirstValidPath2 + ", needApplyHomeVideo=" + zShouldApplyOuterVideoWallpaper2 + ", needApplyLockVideo=" + zShouldApplyOuterVideoWallpaper);
        companion.applyQ18OuterVideo(strFirstValidPath, strFirstValidPath2, zShouldApplyOuterVideoWallpaper2, zShouldApplyOuterVideoWallpaper);
    }

    public final void applySensorWallpaper(WallpaperInfo wallpaperInfo, boolean z, boolean z2, boolean z3, boolean z4) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "applySensorWallpaper: applyAll=" + z3 + ", isLock=" + z4);
        SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams();
        String originResourcePath = wallpaperInfo.getOriginResourcePath();
        wallpaperInfo.setOriginResourcePath(copyFile$default(this, wallpaperInfo.getOriginResourcePath(), this.filePathGenerator.generateCurrentTemplateFilePath(z4 ? TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER : TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME), false, 0L, false, false, 60, null));
        if (sensorWallpaperPrams != null) {
            sensorWallpaperPrams.setThumbnailPath(copyFile$default(this, sensorWallpaperPrams.getThumbnailPath(), this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW), false, 0L, false, false, 60, null));
        }
        if (sensorWallpaperPrams != null) {
            sensorWallpaperPrams.setSmallThumbnailPath(copyFile$default(this, sensorWallpaperPrams.getSmallThumbnailPath(), this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN), false, 0L, false, false, 60, null));
        }
        copyQ18OuterVideoToCurrent(wallpaperInfo, sensorWallpaperPrams, originResourcePath);
        WallpaperController companion = WallpaperController.Companion.getInstance(this.context);
        Context applicationContext = this.context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        String originResourcePath2 = wallpaperInfo.getOriginResourcePath();
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        companion.setMiuiSensorWallpaper(applicationContext, originResourcePath2, BitmapUtil.fromFile$default(bitmapUtil, sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null, 0, 0, null, 14, null), BitmapUtil.fromFile$default(bitmapUtil, sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null, 0, 0, null, 14, null), sensorWallpaperPrams != null ? sensorWallpaperPrams.getAllFrames() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallFrames() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getStiffness() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getDuration() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getFactor() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getAnimType() : null, wallpaperInfo.getMagicType(), z, z2, z3, z4);
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyWallpaper: sensor, " + wallpaperInfo);
    }

    public final boolean applyWallpaperWithConfig(CommonConfig commonConfig, Bitmap bitmap, Bitmap bitmap2, boolean z, String str, String str2, final Bitmap bitmap3, final boolean z2, PickWallpaperColorInfo pickWallpaperColorInfo, final Pair pair, final Pair pair2, final WallpaperApplyParam wallpaperApplyParam, boolean z3) {
        String outerVideoPath;
        WallpaperInfo wallpaperInfo;
        String str3;
        boolean z4;
        Ref.BooleanRef booleanRef;
        boolean z5;
        WallpaperInfo wallpaperInfo2;
        String str4;
        boolean z6;
        CommonConfig commonConfig2;
        String templateId;
        TemplateConfig lockscreenInfo;
        DoodleInfo doodle;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo;
        HomeConfig homeInfo;
        TemplateConfig lockscreenInfo3;
        this = this;
        ThemeUtil.INSTANCE.addHistoryWallpaper(this.context, commonConfig);
        WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        Intrinsics.checkNotNull(wallpaperInfo3);
        WallpaperInfo wallpaperInfo4 = commonConfig.getHomeInfo().getWallpaperInfo();
        Intrinsics.checkNotNull(wallpaperInfo4);
        SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo3.getSensorWallpaperPrams();
        if (sensorWallpaperPrams == null || (outerVideoPath = sensorWallpaperPrams.getOuterVideoPath()) == null) {
            SensorWallpaperPrams sensorWallpaperPrams2 = wallpaperInfo4.getSensorWallpaperPrams();
            outerVideoPath = sensorWallpaperPrams2 != null ? sensorWallpaperPrams2.getOuterVideoPath() : null;
        }
        String templateId2 = commonConfig.getLockscreenInfo().getClockInfo().getTemplateId();
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyWallpaper: lockWallpaperInfo=" + wallpaperInfo3 + ", homeWallpaperInfo=" + wallpaperInfo4 + ", applyToAll=" + z);
        boolean zIsThirdPartyTheme = this.isThirdPartyTheme();
        Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
        booleanRef2.element = true;
        boolean z7 = false;
        if (zIsThirdPartyTheme) {
            wallpaperInfo = wallpaperInfo4;
            str3 = outerVideoPath;
            z4 = zIsThirdPartyTheme;
            booleanRef = booleanRef2;
            z5 = false;
            wallpaperInfo2 = wallpaperInfo3;
            str4 = "Keyguard-Editor-TemplateApiImpl";
        } else {
            z5 = false;
            str3 = outerVideoPath;
            z4 = zIsThirdPartyTheme;
            str4 = "Keyguard-Editor-TemplateApiImpl";
            booleanRef = booleanRef2;
            CommonConfig currentTemplateInternal$default = getCurrentTemplateInternal$default(this, false, false, false, false, null, false, null, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, null);
            WallpaperInfo wallpaperInfo5 = (currentTemplateInternal$default == null || (lockscreenInfo3 = currentTemplateInternal$default.getLockscreenInfo()) == null) ? null : lockscreenInfo3.getWallpaperInfo();
            WallpaperInfo wallpaperInfo6 = (currentTemplateInternal$default == null || (homeInfo = currentTemplateInternal$default.getHomeInfo()) == null) ? null : homeInfo.getWallpaperInfo();
            if (currentTemplateInternal$default == null || (lockscreenInfo2 = currentTemplateInternal$default.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo2.getClockInfo()) == null) {
                commonConfig2 = currentTemplateInternal$default;
                templateId = null;
            } else {
                templateId = clockInfo.getTemplateId();
                commonConfig2 = currentTemplateInternal$default;
            }
            Integer numValueOf = (commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null || (doodle = lockscreenInfo.getDoodle()) == null) ? null : Integer.valueOf(doodle.getSolidColor());
            DoodleInfo doodle2 = commonConfig.getLockscreenInfo().getDoodle();
            boolean zShouldApplyWallpaperAgain = shouldApplyWallpaperAgain(templateId, wallpaperInfo5, wallpaperInfo6, templateId2, r25, r18, numValueOf, doodle2 != null ? Integer.valueOf(doodle2.getSolidColor()) : null, z3);
            wallpaperInfo2 = wallpaperInfo3;
            wallpaperInfo = wallpaperInfo4;
            booleanRef.element = zShouldApplyWallpaperAgain;
            z7 = wallpaperInfo5 != null && wallpaperInfo5.getMagicType() == wallpaperInfo2.getMagicType() && wallpaperInfo5.isNeedDark() == wallpaperInfo2.isNeedDark() && wallpaperInfo5.getEnableBlur() == wallpaperInfo2.getEnableBlur() && wallpaperInfo6 != null && wallpaperInfo6.getMagicType() == wallpaperInfo.getMagicType() && wallpaperInfo6.isNeedDark() == wallpaperInfo.isNeedDark() && wallpaperInfo6.getEnableBlur() == wallpaperInfo.getEnableBlur();
        }
        Log.i(str4, "applyWallpaper:isThirdTheme=" + z4 + ", wallpaperFileChanged=" + booleanRef.element + ", isSameMagicType=" + z7);
        if (!z4 && !booleanRef.element && z7) {
            boolean supportSubject = wallpaperInfo2.getSupportSubject();
            String resourceType = wallpaperInfo2.getResourceType();
            if (resourceType == null) {
                resourceType = "";
            }
            this.setSupportMattingWhenWallpaperNotChanged(supportSubject, resourceType);
            return z5;
        }
        try {
            final WallpaperController companion = WallpaperController.Companion.getInstance(this.context);
            companion.setPickWallpaperColorInfo(pickWallpaperColorInfo);
            final Ref.BooleanRef booleanRef3 = booleanRef;
            WallpaperInfo wallpaperInfo7 = wallpaperInfo;
            Function4 function4 = new Function4() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    return TemplateApiImpl.applyWallpaperWithConfig$lambda$66(this.f$0, companion, booleanRef3, z2, bitmap3, pair, pair2, wallpaperApplyParam, (WallpaperInfo) obj, (Bitmap) obj2, (String) obj3, ((Boolean) obj4).booleanValue());
                }
            };
            if (needApplyAll(bitmap3, commonConfig)) {
                Log.d(str4, "applyEditWallpaper: FOLLOW_STATUS_ALL");
                Boolean bool = Boolean.TRUE;
                function4.invoke(wallpaperInfo2, bitmap, str, bool);
                if (Intrinsics.areEqual(wallpaperInfo2.getResourceType(), "video")) {
                    function4.invoke(wallpaperInfo7, bitmap2, str2, bool);
                }
                String resourceType2 = wallpaperInfo7.getResourceType();
                if (resourceType2 == null) {
                    resourceType2 = "image";
                }
                ExtraInfo extraInfo = wallpaperInfo7.getExtraInfo();
                notifyGalleryOnApply(z5, resourceType2, extraInfo != null ? extraInfo.getLoopType() : null);
                z6 = true;
            } else {
                this.canDeleteWhenApplying = z5;
                Boolean bool2 = Boolean.FALSE;
                function4.invoke(wallpaperInfo7, bitmap2, str2, bool2);
                z6 = true;
                this.canDeleteWhenApplying = true;
                function4.invoke(wallpaperInfo2, bitmap, str, bool2);
            }
            applyQ18OuterVideo(wallpaperInfo2, wallpaperInfo7, str3);
            return z6;
        } catch (Exception e) {
            Log.e(str4, "applyWallpaper: " + e);
            try {
                KeyguardEditorMiSightUtil.reportApplyWallpaperFailed$default(KeyguardEditorMiSightUtil.INSTANCE, "TemplateApiImpl.applyWallpaperWithConfig_exception", null, 2, null);
                return false;
            } catch (Exception unused) {
                return false;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:19:0x0072  */
    public final void applyWallpaperWithInfo(WallpaperInfo wallpaperInfo, String str, boolean z, Bitmap bitmap, PickWallpaperColorInfo pickWallpaperColorInfo, WallpaperApplyParam wallpaperApplyParam, boolean z2) {
        boolean z3;
        boolean z4;
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyWallpaper: " + wallpaperInfo + ", isLock=" + z);
        try {
            WallpaperController companion = WallpaperController.Companion.getInstance(this.context);
            companion.setPickWallpaperColorInfo(pickWallpaperColorInfo);
            boolean z5 = Wallpaper.Companion.isSupportBlurWallpaperType(wallpaperInfo.getResourceType(), wallpaperInfo.getMagicType()) && wallpaperInfo.getEnableBlur();
            FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
            Context context = this.context;
            ExtraInfo extraInfo = wallpaperInfo.getExtraInfo();
            boolean zIsLoop = fashionGalleryHelper.isLoop(context, z, extraInfo != null ? extraInfo.getLoopType() : null);
            String resourceType = wallpaperInfo.getResourceType();
            if (resourceType != null) {
                switch (resourceType.hashCode()) {
                    case -905948230:
                        z3 = zIsLoop;
                        z4 = z5;
                        if (resourceType.equals("sensor")) {
                            applySensorWallpaper(wallpaperInfo, true, z4, z2, z);
                        }
                        break;
                    case -196315310:
                        z3 = zIsLoop;
                        z4 = z5;
                        if (resourceType.equals("gallery")) {
                            Log.i("Keyguard-Editor-TemplateApiImpl", "applyWallpaper: RESOURCE_TYPE_GALLERY -> isLoop = " + z3);
                            if (!z3) {
                                applyImageWallpaper(wallpaperInfo, str, false, z, z4, bitmap, wallpaperApplyParam, z2);
                            } else {
                                applyGalleryWallpaper(wallpaperInfo, str, z, z4);
                            }
                        }
                        break;
                    case 112202875:
                        if (!resourceType.equals("video")) {
                            z3 = zIsLoop;
                            z4 = z5;
                        } else {
                            wallpaperInfo.setOriginResourcePath(copyFile$default(this, wallpaperInfo.getOriginResourcePath(), this.filePathGenerator.generateCurrentTemplateFilePath(z ? TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER : TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME), false, 1000L, true, false, 36, null));
                            if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType())) || wallpaperInfo.isDepthVideo()) {
                                wallpaperInfo.setDepthVideo(wallpaperInfo.getOriginResourcePath());
                            }
                            companion.applyVideoWallpaper(wallpaperInfo.getOriginResourcePath(), BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, str, 0, 0, null, 14, null), wallpaperInfo.getMagicType(), wallpaperInfo.isSupportLoop(), z, true, z5, z2, zIsLoop ? 1 : wallpaperInfo.getEnableDoodle(), wallpaperInfo.isSupportFullScreenAod());
                        }
                        break;
                    case 1196792382:
                        if (!resourceType.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
                            z3 = zIsLoop;
                            z4 = z5;
                        } else {
                            IWallpaperController.DefaultImpls.applySuperWallpaper$default(companion, wallpaperInfo.getOriginResourcePath(), this.context, false, 4, null);
                        }
                        break;
                    default:
                        z3 = zIsLoop;
                        z4 = z5;
                        break;
                }
                return;
            }
            z3 = zIsLoop;
            z4 = z5;
            Log.i("Keyguard-Editor-TemplateApiImpl", "applyWallpaper: RESOURCE_TYPE_IMAGE -> isLoop = " + z3);
            applyImageWallpaper(wallpaperInfo, str, z3, z, z4, bitmap, wallpaperApplyParam, z2);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "applyWallpaper: " + e);
            try {
                KeyguardEditorMiSightUtil.reportApplyWallpaperFailed$default(KeyguardEditorMiSightUtil.INSTANCE, "TemplateApiImpl.applyWallpaperWithInfo_exception", null, 2, null);
            } catch (Exception unused) {
            }
        }
    }

    public final void checkProcess() {
        String processName = Application.getProcessName();
        if (Intrinsics.areEqual("com.android.thememanager", processName)) {
            return;
        }
        Process process = Process.INSTANCE;
        if (process.isKeyguardeditorProcess() || process.isAodProcess()) {
            return;
        }
        throw new IllegalStateException("error process, " + processName);
    }

    public final void clearDepthVideoData(CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo3;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo5;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfo6;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo7;
        Integer numValueOf = null;
        if ((commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo7 = lockscreenInfo3.getWallpaperInfo()) == null || wallpaperInfo7.getVideoType() != 2) && commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
            WallpaperInfo wallpaperInfo8 = lockscreenInfo.getWallpaperInfo();
            if (wallpaperInfo8 != null) {
                wallpaperInfo8.setDownloadUrl(null);
            }
            lockscreenInfo.setWallpaperInfoForVideo(null);
            MagicType magicType = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo9 = lockscreenInfo.getWallpaperInfo();
            if (!magicType.isDepthVideo(wallpaperInfo9 != null ? Integer.valueOf(wallpaperInfo9.getMagicType()) : null) && (((wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || !wallpaperInfo.isDepthVideo()) && (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) != null)) {
                wallpaperInfo2.setDepthVideo("");
            }
        }
        if ((commonConfig == null || (homeInfo2 = commonConfig.getHomeInfo()) == null || (wallpaperInfo6 = homeInfo2.getWallpaperInfo()) == null || wallpaperInfo6.getVideoType() != 2) && commonConfig != null && (homeInfo = commonConfig.getHomeInfo()) != null) {
            WallpaperInfo wallpaperInfo10 = homeInfo.getWallpaperInfo();
            if (wallpaperInfo10 != null) {
                wallpaperInfo10.setDownloadUrl(null);
            }
            homeInfo.setWallpaperInfoForVideo(null);
            MagicType magicType2 = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo11 = homeInfo.getWallpaperInfo();
            if (!magicType2.isDepthVideo(wallpaperInfo11 != null ? Integer.valueOf(wallpaperInfo11.getMagicType()) : null) && (((wallpaperInfo3 = homeInfo.getWallpaperInfo()) == null || !wallpaperInfo3.isDepthVideo()) && (wallpaperInfo4 = homeInfo.getWallpaperInfo()) != null)) {
                wallpaperInfo4.setDepthVideo("");
            }
        }
        MagicType magicType3 = MagicType.INSTANCE;
        if (commonConfig != null && (lockscreenInfo2 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo5 = lockscreenInfo2.getWallpaperInfo()) != null) {
            numValueOf = Integer.valueOf(wallpaperInfo5.getMagicType());
        }
        if (magicType3.isDepthVideo(numValueOf)) {
            return;
        }
        FileUtil.delete(this.filePathGenerator.generateDepthVideoWallpaperFilePath(TemplateFilePathGenerator.TemplateFileType.DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK));
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void clearHistoryTemplate() {
        this.templateDao.clear();
    }

    public final void closeFashionGalleryIfNeeded(Context context, String str, String str2) {
        int iHashCode;
        Log.d("Keyguard-Editor-TemplateApiImpl", "closeFashionGalleryIfNeeded : " + str + ", " + str2);
        if (str != null && ((iHashCode = str.hashCode()) == -905948230 ? str.equals("sensor") : iHashCode == 112202875 ? str.equals("video") : iHashCode == 1196792382 && str.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER))) {
            if (DeviceUtil.INSTANCE.isInternationalBuild()) {
                FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                if (fashionGalleryHelper.isGalleryInstalledAndOpen(context)) {
                    fashionGalleryHelper.closeLockscreenMagazine(context, str);
                }
            } else {
                notifyGalleryOnApply(true, str, -1);
            }
        }
        if (str2 != null) {
            int iHashCode2 = str2.hashCode();
            if (iHashCode2 != -905948230) {
                if (iHashCode2 != 112202875) {
                    if (iHashCode2 != 1196792382 || !str2.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
                        return;
                    }
                } else if (!str2.equals("video")) {
                    return;
                }
            } else if (!str2.equals("sensor")) {
                return;
            }
            if (DeviceUtil.INSTANCE.isInternationalBuild()) {
                return;
            }
            notifyGalleryOnApply(false, str2, -1);
        }
    }

    public final String copyBitmap(Bitmap bitmap, String str, boolean z, long j, boolean z2, boolean z3) {
        if (str != null && bitmap != null) {
            CoroutineDispatcher io2 = Dispatchers.getIO();
            Boolean bool = Boolean.TRUE;
            if (Intrinsics.areEqual((Boolean) UtilsKt.runSyncWithTimeOut(io2, j, bool, new TemplateApiImpl$copyBitmap$result$1(z2, bitmap, str, z, z3, null)), bool)) {
                return str;
            }
        }
        return null;
    }

    public final String copyCroppedWallpaper(boolean z, Bitmap bitmap, String str) {
        Uri uriCreateWallpaperContentUri;
        if (!z) {
            return str;
        }
        String strCopyBitmap$default = copyBitmap$default(this, bitmap, this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_CROPPED), false, 0L, false, false, 60, null);
        Log.i("Keyguard-Editor-TemplateApiImpl", "copyCroppedWallpaper -> copyPath = " + strCopyBitmap$default);
        if (strCopyBitmap$default == null || strCopyBitmap$default.length() == 0 || (uriCreateWallpaperContentUri = FashionGalleryHelper.INSTANCE.createWallpaperContentUri(new File(strCopyBitmap$default), "/template_current/wallpaper_cropped")) == null) {
            return null;
        }
        return uriCreateWallpaperContentUri.toString();
    }

    public final String copyFile(String str, String str2, boolean z, long j, boolean z2, boolean z3) {
        String presetWallpaperAbsolutePath;
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "copyFile failed: source or target is empty. source=" + str + ",target=" + str2);
            return null;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "copyFile: start copy file to " + str2);
        if (z) {
            presetWallpaperAbsolutePath = TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(str);
        } else {
            if (!new File(str).exists()) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "copyFile failed: source file not exists. source = " + str);
                return null;
            }
            presetWallpaperAbsolutePath = str;
        }
        if (Intrinsics.areEqual(presetWallpaperAbsolutePath, str2)) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "copyFile: src==dst, skip copy. path=" + str2);
            return str2;
        }
        Boolean boolValueOf = j > 0 ? (Boolean) UtilsKt.runSyncWithTimeOut(Dispatchers.getIO(), j, Boolean.valueOf(z2), new TemplateApiImpl$copyFile$copyResult$1(str2, presetWallpaperAbsolutePath, z3, null)) : Boolean.valueOf(FileUtil.copyFile(presetWallpaperAbsolutePath, str2));
        Log.i("Keyguard-Editor-TemplateApiImpl", "copyFile: copy file from " + str + " to " + str2 + " completed, result " + boolValueOf);
        if (Intrinsics.areEqual(boolValueOf, Boolean.TRUE)) {
            return str2;
        }
        return null;
    }

    public final String copyOriginWallpaper(String str, String str2) {
        String strValueOf;
        if (TextUtils.isEmpty(str)) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "originResourcePath is empty");
            return str2;
        }
        try {
            String strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_COPIED);
            FileUtil.copyFile(str, strGenerateCurrentTemplateFilePath);
            strValueOf = String.valueOf(FashionGalleryHelper.INSTANCE.createWallpaperContentUri(new File(strGenerateCurrentTemplateFilePath), "/template_current/wallpaper_copied"));
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "copyOriginWallpaper error", e);
            strValueOf = null;
        }
        return strValueOf == null ? String.valueOf(FashionGalleryHelper.INSTANCE.createWallpaperContentUri(new File(str2), "/template_current/wallpaper")) : strValueOf;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x008d  */
    /* JADX WARN: Code duplicated, block: B:91:0x0148  */
    public final void copyQ18OuterVideoToCurrent(WallpaperInfo wallpaperInfo, SensorWallpaperPrams sensorWallpaperPrams, String str) {
        boolean z;
        String source;
        String str2;
        WallpaperController.Companion companion = WallpaperController.Companion;
        WallpaperController.Companion.fillQ18SensorOuterVideoIfNeeded$default(companion, wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, sensorWallpaperPrams, null, CollectionsKt.listOf((Object[]) new String[]{str, wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null, wallpaperInfo != null ? wallpaperInfo.getSource() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPreViewPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null}), 4, null);
        boolean zShouldApplyOuterVideoWallpaper = companion.shouldApplyOuterVideoWallpaper(wallpaperInfo, str, sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPreViewPath() : null, sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null);
        if (DeviceUtil.INSTANCE.isQ18()) {
            String outerVideoPath = sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null;
            if (outerVideoPath == null || outerVideoPath.length() == 0) {
                z = false;
            } else {
                z = true;
            }
        } else {
            z = false;
        }
        if (!zShouldApplyOuterVideoWallpaper && !z) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "copyQ18OuterVideoToCurrent: skip, resourceType=" + (wallpaperInfo != null ? wallpaperInfo.getResourceType() : null) + ", source=" + (wallpaperInfo != null ? wallpaperInfo.getSource() : null) + ", origin=" + (wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null) + ", originalOriginPath=" + str + ", outerVideoPath=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null));
            return;
        }
        String strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.OUTER_VIDEO_WALLPAPER);
        String outerVideoPath2 = sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null;
        if (str == null) {
            String originResourcePath = wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null;
            if (originResourcePath == null) {
                source = wallpaperInfo != null ? wallpaperInfo.getSource() : null;
            } else {
                source = originResourcePath;
            }
        } else {
            source = str;
        }
        String strExtractCmfCode = companion.extractCmfCode(source);
        String presetCmfDynamicVideoPath = ((outerVideoPath2 != null && outerVideoPath2.length() != 0 && new File(outerVideoPath2).exists()) || strExtractCmfCode == null || strExtractCmfCode.length() == 0) ? null : companion.getPresetCmfDynamicVideoPath(source, strExtractCmfCode);
        if (outerVideoPath2 == null) {
            str2 = presetCmfDynamicVideoPath;
        } else {
            str2 = new File(outerVideoPath2).exists() ? outerVideoPath2 : null;
            if (str2 == null) {
                str2 = presetCmfDynamicVideoPath;
            }
        }
        if (str2 == null || str2.length() == 0 || !new File(str2).exists()) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "copyQ18OuterVideoToCurrent: no valid src, orig=" + outerVideoPath2 + ", fallback=" + presetCmfDynamicVideoPath + ", fallbackCmf=" + strExtractCmfCode + ", originalOriginPath=" + str);
            return;
        }
        String str3 = str2;
        String strCopyFile$default = copyFile$default(this, str3, strGenerateCurrentTemplateFilePath, false, 5000L, true, false, 36, null);
        if (strCopyFile$default != null && strCopyFile$default.length() != 0 && sensorWallpaperPrams != null) {
            sensorWallpaperPrams.setOuterVideoPath(strCopyFile$default);
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "copyQ18OuterVideoToCurrent: src=" + str3 + ", dest=" + strCopyFile$default);
    }

    public final String copySubjectFromHistoryCompact(String str, String str2) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "copySubjectFromHistoryCompact: subjectPath=" + str + ",maskPath=" + str2);
        if (str == null) {
            return null;
        }
        TemplateFilePathGenerator.TemplateFileType templateFileType = TemplateFilePathGenerator.TemplateFileType.SUBJECT;
        if (!StringsKt.endsWith$default(str, templateFileType.getFileName(), false, 2, (Object) null)) {
            return copyFile$default(this, str, this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK), false, 0L, false, false, 60, null);
        }
        String strCopyFile$default = copyFile$default(this, str, this.filePathGenerator.generateCurrentTemplateFilePath(templateFileType), false, 0L, false, false, 60, null);
        copyFile$default(this, str2, this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK), false, 0L, false, false, 60, null);
        return strCopyFile$default;
    }

    public final void copyVideoForLivePhotoResource(CommonConfig commonConfig) {
        WallpaperInfo wallpaperInfoForVideo;
        WallpaperInfo wallpaperInfoForVideo2;
        Wallpaper.Companion companion = Wallpaper.Companion;
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (companion.isLivePhotoResource(wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getVideoType()) : null) && (wallpaperInfoForVideo2 = commonConfig.getLockscreenInfo().getWallpaperInfoForVideo()) != null && Intrinsics.areEqual(wallpaperInfoForVideo2.getResourceType(), "video")) {
            wallpaperInfoForVideo2.setOriginResourcePath(copyFile$default(this, wallpaperInfoForVideo2.getOriginResourcePath(), this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER), false, 1000L, true, false, 36, null));
            if (wallpaperInfoForVideo2.getOriginResourcePath() == null) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "copyVideoForLivePhotoResource: copy lockVideoWallpaper failed!");
            }
        }
        if (commonConfig.getHomeInfo().getFollowStatus() == 2 || commonConfig.getHomeInfo().getFollowStatus() == 1) {
            return;
        }
        WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
        if (companion.isLivePhotoResource(wallpaperInfo2 != null ? Integer.valueOf(wallpaperInfo2.getVideoType()) : null) && (wallpaperInfoForVideo = commonConfig.getHomeInfo().getWallpaperInfoForVideo()) != null && Intrinsics.areEqual(wallpaperInfoForVideo.getResourceType(), "video")) {
            wallpaperInfoForVideo.setOriginResourcePath(copyFile$default(this, wallpaperInfoForVideo.getOriginResourcePath(), this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME), false, 1000L, true, false, 36, null));
            if (wallpaperInfoForVideo.getOriginResourcePath() == null) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "copyVideoForLivePhotoResource: copy homeVideoWallpaper failed!");
            }
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap cropBitmap(Bitmap bitmap, WallpaperPositionInfo positionInfo, boolean z) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(positionInfo, "positionInfo");
        Bitmap bitmapCropBitmap = BitmapUtil.INSTANCE.cropBitmap(bitmap, (int) positionInfo.getLeft(), (int) positionInfo.getRight(), (int) positionInfo.getTop(), (int) positionInfo.getBottom());
        if (bitmapCropBitmap != null) {
            return bitmapCropBitmap;
        }
        throw new NullPointerException("crop " + (z ? "wallpaper" : "subject") + " is null !");
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap currentOriginSubject(CommonConfig commonConfig) {
        return loadCurrentTemplateSubjectCompat(commonConfig);
    }

    public Bitmap currentOriginWallpaper(WallpaperInfo wallpaperInfo) {
        return getCurrentOriginalWallpaper(wallpaperInfo);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public CommonConfig currentTemplate(boolean z, boolean z2, boolean z3, boolean z4, WallpaperTypeInfo wallpaperTypeInfo, boolean z5, String wallpaperTarget) {
        Intrinsics.checkNotNullParameter(wallpaperTarget, "wallpaperTarget");
        return getCurrentTemplateInternal(z, z2, z3, z4, wallpaperTypeInfo, z5, wallpaperTarget);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public String currentWallPaperPath(WallpaperInfo wallpaperInfo) {
        TemplateFilePathGenerator.TemplateFileType wallpaperFileType;
        String currentWallpaperPathSafely;
        if (Intrinsics.areEqual("gallery", wallpaperInfo != null ? wallpaperInfo.getResourceType() : null) && Intrinsics.areEqual(getLockWallpaperSetPkg(), FashionGalleryHelper.INSTANCE.getFashionGalleryPackage())) {
            currentWallpaperPathSafely = this.filePathGenerator.generateCurrentTemplateFilePath(wallpaperInfo.getWallpaperFileType());
        } else {
            if (wallpaperInfo == null || (wallpaperFileType = wallpaperInfo.getWallpaperFileType()) == null) {
                wallpaperFileType = TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
            }
            currentWallpaperPathSafely = getCurrentWallpaperPathSafely(wallpaperInfo, wallpaperFileType);
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentOriginalWallpaper: " + currentWallpaperPathSafely + ", wallpaperInfo?.resourceType=" + (wallpaperInfo != null ? wallpaperInfo.getResourceType() : null));
        return currentWallpaperPathSafely;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0047  */
    /* JADX WARN: Code duplicated, block: B:15:0x0051  */
    public final void darkenCurWallpaperIfNeed(boolean z, CommonConfig commonConfig, boolean z2, boolean z3) {
        WallpaperInfo wallpaperInfo;
        if (z && isThemeManagerDarkenConfig(commonConfig)) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "start: darkenCurWallpaper; localDarkenWallpaperValid = " + this.localDarkenWallpaperValid);
            this.wallpaperChangedCallback.getValue();
            TemplateFilePathGenerator templateFilePathGenerator = this.filePathGenerator;
            TemplateFilePathGenerator.TemplateFileType templateFileType = TemplateFilePathGenerator.TemplateFileType.CURRENT_DARKEN_WALLPAPER;
            String strGenerateCurrentTemplateFilePath = templateFilePathGenerator.generateCurrentTemplateFilePath(templateFileType);
            if (!this.localDarkenWallpaperValid) {
                if (z3) {
                    wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo != null) {
                        wallpaperInfo.setCurrentWallpaper(getWallpaperInternal(commonConfig.getLockscreenInfo().getWallpaperInfo(), z2));
                    }
                } else {
                    WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                    if ((wallpaperInfo2 != null ? wallpaperInfo2.getCurrentWallpaper() : null) == null) {
                        wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                        if (wallpaperInfo != null) {
                            wallpaperInfo.setCurrentWallpaper(getWallpaperInternal(commonConfig.getLockscreenInfo().getWallpaperInfo(), z2));
                        }
                    }
                }
                WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if ((wallpaperInfo3 != null ? wallpaperInfo3.getCurrentWallpaper() : null) != null) {
                    BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
                    WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                    Bitmap currentWallpaper = wallpaperInfo4 != null ? wallpaperInfo4.getCurrentWallpaper() : null;
                    Intrinsics.checkNotNull(currentWallpaper);
                    bitmapUtil.toJPEG(currentWallpaper, strGenerateCurrentTemplateFilePath);
                }
                Bundle bundleCall = this.context.getContentResolver().call(Uri.parse("content://com.android.thememanager.theme_provider"), "darken_bitmap", (String) null, (Bundle) null);
                boolean z4 = bundleCall != null ? bundleCall.getBoolean("darkenBitmapSuccess") : false;
                Log.i("Keyguard-Editor-TemplateApiImpl", "theme dark current wallpaper ret is " + z4);
                if (z4) {
                    this.localDarkenWallpaperValid = true;
                }
            }
            if (this.localDarkenWallpaperValid) {
                commonConfig.getLockscreenInfo().setDarkenWallpaper(BitmapCreator.decodeFile$default(BitmapCreator.INSTANCE, this.filePathGenerator.generateCurrentTemplateFilePath(templateFileType), null, 2, null));
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "after theme dark current wallpaper update info");
        }
    }

    public final void degradeClockEffect(CommonConfig commonConfig) {
        ClockInfo clockInfo = commonConfig.getLockscreenInfo().getClockInfo();
        clockInfo.setClockEffect(EditFragmentViewModel.Companion.computeSupportedClockEffect(commonConfig, clockInfo.getClockEffect()));
    }

    /* JADX WARN: Code duplicated, block: B:28:0x008f  */
    /* JADX WARN: Code duplicated, block: B:34:0x00b8  */
    public final boolean deleteCurrentIfDefault(CommonConfig commonConfig) {
        WallpaperInfo wallpaperInfo;
        Object objM1061constructorimpl;
        if (isDefaultConfig(commonConfig) && isDefaultTheme()) {
            try {
                Result.Companion companion = Result.Companion;
                Log.w("Keyguard-Editor-TemplateApiImpl", "deleteCurrentIfDefault begin");
                FileUtil.deleteFolder(this.filePathGenerator.generateCurrentTemplateFolder());
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT));
                objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl != null) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "deleteCurrentIfDefault: ", thM1063exceptionOrNullimpl);
            }
            return true;
        }
        if (UserGuidance.INSTANCE.isNewUser(this.context)) {
            String strGenerateCurrentTemplateFolder = this.filePathGenerator.generateCurrentTemplateFolder();
            MagicType magicType = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (!magicType.isDepth(wallpaperInfo2 != null ? Integer.valueOf(wallpaperInfo2.getMagicType()) : null)) {
                WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (magicType.isDepthVideo(wallpaperInfo3 != null ? Integer.valueOf(wallpaperInfo3.getMagicType()) : null)) {
                    if (!FileUtil.isFileExist(strGenerateCurrentTemplateFolder)) {
                        FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT));
                        wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                        if (wallpaperInfo != null) {
                            wallpaperInfo.setMagicType(0);
                        }
                        Log.i("Keyguard-Editor-TemplateApiImpl", "deleteCurrentIfDefault: delete CROP_SUBJECT");
                        return true;
                    }
                }
            } else if (!FileUtil.isFileExist(strGenerateCurrentTemplateFolder) && Intrinsics.areEqual(commonConfig.getLastUpdate(), Application.getProcessName())) {
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT));
                wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo != null) {
                    wallpaperInfo.setMagicType(0);
                }
                Log.i("Keyguard-Editor-TemplateApiImpl", "deleteCurrentIfDefault: delete CROP_SUBJECT");
                return true;
            }
        }
        return false;
    }

    public final void deleteCurrentTemplateFiles() {
        Log.i("Keyguard-Editor-TemplateApiImpl", "deleteCurrentFolder: ");
        FileUtil.deleteFolder(this.filePathGenerator.generateCurrentTemplateFolder());
        FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT));
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean deleteHistoryTemplate(TemplateHistoryConfig config, boolean z, boolean z2) {
        CrossListPreloader crossListPreloader;
        Intrinsics.checkNotNullParameter(config, "config");
        Log.i("Keyguard-Editor-TemplateApiImpl", "deleteHistoryTemplate: " + config);
        this.templateDao.delete(config);
        FileUtil.deleteFolder(this.filePathGenerator.generateHistoryTemplateDir(config.getLastModifiedTime()));
        if (z2 && (crossListPreloader = EditorApplicationProxy.Companion.getCrossListPreloader()) != null) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "deleteHistoryTemplate: clear preloader");
            crossListPreloader.takeAllHistoryTemplates();
        }
        if (!z) {
            return true;
        }
        this.context.getContentResolver().notifyChange(Uri.parse("content://miui.keyguard.editor.templatefileprovider/notify_delete_histories"), (ContentObserver) null, 32768);
        return true;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void enableGetCurrentLockWallpaperFromWM() {
        Log.i("Keyguard-Editor-TemplateApiImpl", "enableGetCurrentLockWallpaperFromWM: ");
        this.enableGetCurrentLockWallpaperFromWM = true;
    }

    public final void fillDefaultClockParamsIfNeeded(CommonConfig commonConfig) {
        ClockInfo clockInfo = commonConfig.getLockscreenInfo().getClockInfo();
        if (Intrinsics.areEqual(clockInfo.getTemplateId(), "all_in_one") && clockInfo.getTimeWidth() == 0.0f && clockInfo.getTimeHeight() == 0.0f) {
            ClockBean defaultAllInOneParams = AllInOneClockMigration.INSTANCE.getDefaultAllInOneParams(clockInfo.getStyle(), this.context);
            clockInfo.setTimeWidth(defaultAllInOneParams.getTimeWidth());
            clockInfo.setTimeHeight(defaultAllInOneParams.getTimeHeight());
            clockInfo.setClockTopRatio(defaultAllInOneParams.getClockTopRatio());
            clockInfo.setClockBottomRatio(defaultAllInOneParams.getClockBottomRatio());
            clockInfo.setDoubleRow(defaultAllInOneParams.isDoubleRow());
            clockInfo.setClockDepthType(AllInOneClockStyle.toRegionInt(defaultAllInOneParams.getClockDepthType()));
            clockInfo.setSkipScreenTransform(defaultAllInOneParams.isSkipScreenTransform());
            String classicSignature = clockInfo.getClassicSignature();
            if (classicSignature == null) {
                classicSignature = "";
            }
            clockInfo.setClassicSignature(classicSignature);
            Log.d("Keyguard-Editor-TemplateApiImpl", "fillDefaultClockParamsIfNeeded: style=" + clockInfo.getStyle() + ", timeWidth=" + clockInfo.getTimeWidth() + ", timeHeight=" + clockInfo.getTimeHeight() + ", isDoubleRow=" + clockInfo.isDoubleRow() + ", clockDepthType=" + clockInfo.getClockDepthType());
        }
    }

    public Set getAllHistoryDirs() {
        return FileUtil.getChildDirs(this.filePathGenerator.generateHistoryRootDir());
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap getCurrentHomeScreenShot() {
        String strGenerateCurrentTemplateFilePath;
        if (DeviceScreenshotHelper.INSTANCE.isFirstScreenshotCacheInvalid()) {
            Log.d("Keyguard-Editor-TemplateApiImpl", "screenshot cache invalid");
            return null;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone() || (deviceUtil.isFold() && !deviceUtil.isFoldLargeScreen(this.context))) {
            strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SMALL_WALLPAPER_SCREENSHOT);
        } else {
            strGenerateCurrentTemplateFilePath = deviceUtil.isDisplayLandScape(this.context) ? this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT) : this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_WALLPAPER_SCREENSHOT);
        }
        return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, strGenerateCurrentTemplateFilePath, 0, 0, null, 14, null);
    }

    public final Bitmap getCurrentHomeWallpaperPreview() {
        return WallpaperController.Companion.getInstance(this.context).getHomeWallpaperPreview(this.context);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap getCurrentLockScreenShot() {
        String strGenerateCurrentTemplateFilePath;
        if (DeviceScreenshotHelper.INSTANCE.isFirstScreenshotCacheInvalid()) {
            Log.d("Keyguard-Editor-TemplateApiImpl", "screenshot cache invalid");
            return null;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone() || (deviceUtil.isFold() && !deviceUtil.isFoldLargeScreen(this.context))) {
            strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SMALL_SCREENSHOT);
        } else {
            strGenerateCurrentTemplateFilePath = deviceUtil.isDisplayLandScape(this.context) ? this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_SCREENSHOT) : this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_SCREENSHOT);
        }
        return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, strGenerateCurrentTemplateFilePath, 0, 0, null, 14, null);
    }

    public final Bitmap getCurrentLockWallpaperAsBitmap() {
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmap: ");
        if (!Process.INSTANCE.isKeyguardeditorProcess()) {
            return null;
        }
        try {
            Object systemService = this.context.getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            Boolean boolValueOf = keyguardManager != null ? Boolean.valueOf(keyguardManager.isKeyguardLocked()) : null;
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmap: isKeyguardLocked=" + boolValueOf + ",enableGetCurrentLockWallpaperFromWM=" + this.enableGetCurrentLockWallpaperFromWM);
            if (Intrinsics.areEqual(boolValueOf, Boolean.TRUE) && this.enableGetCurrentLockWallpaperFromWM) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                Bitmap currentLockWallpaperAsBitmapW = Build.VERSION.SDK_INT > 35 ? getCurrentLockWallpaperAsBitmapW(this.context) : getCurrentLockWallpaperAsBitmapV(this.context);
                if (currentLockWallpaperAsBitmapW != null) {
                    this.enableGetCurrentLockWallpaperFromWM = false;
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmap: copy");
                    Bitmap bitmapCopy = currentLockWallpaperAsBitmapW.copy(Bitmap.Config.ARGB_8888, false);
                    Log.i("Keyguard-Editor-TemplateApiImpl", "get wallpaper from wms, copy cost: " + (System.currentTimeMillis() - jCurrentTimeMillis2));
                    Log.i("Keyguard-Editor-TemplateApiImpl", "get wallpaper from wms: cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
                    return bitmapCopy;
                }
                Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmap: null");
                Log.i("Keyguard-Editor-TemplateApiImpl", "get wallpaper from wms: cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            }
            return null;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmap: ", e);
            return null;
        }
    }

    public final Bitmap getCurrentLockWallpaperAsBitmapV(Context context) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Object systemService = context.getSystemService("wallpaper");
            WallpaperManager wallpaperManager = systemService instanceof WallpaperManager ? (WallpaperManager) systemService : null;
            Method declaredMethod = WallpaperManager.class.getDeclaredMethod("getLockWallpaperAsBitmap", Context.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(wallpaperManager, context);
            Bitmap bitmap = objInvoke instanceof Bitmap ? (Bitmap) objInvoke : null;
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmapV: cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            return bitmap;
        } catch (Exception e) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmapV: error msg is " + e.getMessage());
            return null;
        }
    }

    public final Bitmap getCurrentLockWallpaperAsBitmapW(Context context) {
        Object next;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Object systemService = context.getSystemService("window");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            int displayId = ((WindowManager) systemService).getDefaultDisplay().getDisplayId();
            Method method = ServiceManager.class.getMethod("getService", String.class);
            Intrinsics.checkNotNullExpressionValue(method, "getMethod(...)");
            Object objInvoke = IWindowManager.Stub.class.getMethod("asInterface", IBinder.class).invoke(null, method.invoke(null, "window"));
            Iterator it = CollectionsKt.listOf((Object[]) new String[]{"android.window.ScreenCaptureInternal", "android.window.ScreenCapture"}).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                Class.forName((String) next);
                break;
            }
            String str = (String) next;
            if (str == null) {
                return null;
            }
            Class<?> cls = Class.forName(str);
            Class<?> cls2 = Class.forName(str + "$CaptureArgs");
            Class<?> cls3 = Class.forName(str + "$CaptureArgs$Builder");
            Class<?> cls4 = Class.forName(str + "$ScreenCaptureListener");
            Class<?> cls5 = Class.forName(str + "$SynchronousScreenCaptureListener");
            Method declaredMethod = cls3.getDeclaredMethod("setExcludeOrIncludeLayerNames", String[].class);
            Intrinsics.checkNotNullExpressionValue(declaredMethod, "getDeclaredMethod(...)");
            String str2 = StringsKt.endsWith$default(str, "ScreenCaptureInternal", false, 2, (Object) null) ? "setMiCaptureMode" : "setCaptureMode";
            Class cls6 = Integer.TYPE;
            Method declaredMethod2 = cls3.getDeclaredMethod(str2, cls6);
            Intrinsics.checkNotNullExpressionValue(declaredMethod2, "getDeclaredMethod(...)");
            Constructor<?> constructor = cls3.getConstructor(new Class[0]);
            Intrinsics.checkNotNullExpressionValue(constructor, "getConstructor(...)");
            Object objNewInstance = constructor.newInstance(new Object[0]);
            Intrinsics.checkNotNullExpressionValue(objNewInstance, "newInstance(...)");
            declaredMethod.invoke(objNewInstance, new String[]{"Wallpaper BBQ wrapper"});
            declaredMethod2.invoke(objNewInstance, 2);
            Method declaredMethod3 = cls3.getDeclaredMethod("build", new Class[0]);
            Intrinsics.checkNotNullExpressionValue(declaredMethod3, "getDeclaredMethod(...)");
            Object objInvoke2 = declaredMethod3.invoke(objNewInstance, new Object[0]);
            Method method2 = cls.getMethod("createSyncCaptureListener", new Class[0]);
            Intrinsics.checkNotNullExpressionValue(method2, "getMethod(...)");
            Object objInvoke3 = method2.invoke(null, new Object[0]);
            Method method3 = objInvoke.getClass().getMethod("captureDisplay", cls6, cls2, cls4);
            Intrinsics.checkNotNullExpressionValue(method3, "getMethod(...)");
            method3.invoke(objInvoke, Integer.valueOf(displayId), objInvoke2, objInvoke3);
            Method method4 = cls5.getMethod("getBuffer", new Class[0]);
            Intrinsics.checkNotNullExpressionValue(method4, "getMethod(...)");
            Object objInvoke4 = method4.invoke(objInvoke3, new Object[0]);
            Method method5 = objInvoke4 != null ? objInvoke4.getClass().getMethod("asBitmap", new Class[0]) : null;
            Object objInvoke5 = method5 != null ? method5.invoke(objInvoke4, new Object[0]) : null;
            if (!(objInvoke5 instanceof Bitmap)) {
                return null;
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmapW: cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            return (Bitmap) objInvoke5;
        } catch (Exception e) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaperAsBitmapW: error msg is " + e.getMessage());
            return null;
        }
    }

    public final Bitmap getCurrentLockWallpaperPreview() {
        return WallpaperController.Companion.getInstance(this.context).getLockWallpaperPreview(this.context);
    }

    public final int getCurrentMagicType(int i) {
        int currentMagicType = WallpaperController.Companion.getInstance(this.context).getCurrentMagicType(i);
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentMagicType: which=" + i + " " + currentMagicType);
        return currentMagicType;
    }

    public final Bitmap getCurrentOriginWallpaperByProvider(Context context, WallpaperInfoType wallpaperInfoType) {
        int i;
        if (wallpaperInfoType == null) {
            i = -1;
        } else {
            try {
                i = WhenMappings.$EnumSwitchMapping$1[wallpaperInfoType.ordinal()];
            } catch (Exception e) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "getCurrentOriginWallpaperByProvider: " + e);
                return null;
            }
        }
        String str = "content://miui.keyguard.editor.templatefileprovider/template_current/wallpaper";
        if (i != 1 && i == 2) {
            str = "content://miui.keyguard.editor.templatefileprovider/template_current/wallpaper_home";
        }
        return BitmapUtil.parcelFileDescriptorToBitmap$default(BitmapUtil.INSTANCE, context.getContentResolver().openFileDescriptor(Uri.parse(str), "r"), 0, 0, null, null, 30, null);
    }

    public final Bitmap getCurrentOriginalWallpaper(WallpaperInfo wallpaperInfo) {
        Bitmap currentOriginWallpaperByProvider$default;
        TemplateFilePathGenerator.TemplateFileType wallpaperFileType;
        String currentWallpaperPathSafely;
        if (Process.INSTANCE.isKeyguardeditorProcess()) {
            if (Intrinsics.areEqual("gallery", wallpaperInfo != null ? wallpaperInfo.getResourceType() : null) && Intrinsics.areEqual(getLockWallpaperSetPkg(), FashionGalleryHelper.INSTANCE.getFashionGalleryPackage())) {
                currentWallpaperPathSafely = this.filePathGenerator.generateCurrentTemplateFilePath(wallpaperInfo.getWallpaperFileType());
            } else {
                if (wallpaperInfo == null || (wallpaperFileType = wallpaperInfo.getWallpaperFileType()) == null) {
                    wallpaperFileType = TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
                }
                currentWallpaperPathSafely = getCurrentWallpaperPathSafely(wallpaperInfo, wallpaperFileType);
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentOriginalWallpaper: " + currentWallpaperPathSafely + ", wallpaperInfo?.resourceType=" + (wallpaperInfo != null ? wallpaperInfo.getResourceType() : null));
            currentOriginWallpaperByProvider$default = getCurrentOriginalWallpaperInternal(currentWallpaperPathSafely);
        } else {
            currentOriginWallpaperByProvider$default = getCurrentOriginWallpaperByProvider$default(this, this.context, null, 2, null);
        }
        if (currentOriginWallpaperByProvider$default != null) {
            return currentOriginWallpaperByProvider$default;
        }
        Log.e("Keyguard-Editor-TemplateApiImpl", "try miuiWallpaper, config wallpaper not exit");
        return getCurrentWallpaper(wallpaperInfo);
    }

    public final Bitmap getCurrentOriginalWallpaperInternal(String str) {
        try {
            if (!FileUtil.isFileExist(str)) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "getCurrentOriginalLockWallpaperInternal: file not exit " + str);
            }
            return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, str, 0, 0, null, 14, null);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "getCurrentWallpaperSource: " + e);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:119:0x0279  */
    /* JADX WARN: Code duplicated, block: B:123:0x029f  */
    /* JADX WARN: Code duplicated, block: B:199:0x041a A[Catch: Exception -> 0x02ee, TRY_LEAVE, TryCatch #1 {Exception -> 0x02ee, blocks: (B:129:0x02c3, B:131:0x02e4, B:133:0x02ea, B:137:0x02f3, B:139:0x02f9, B:148:0x031e, B:150:0x0330, B:152:0x033a, B:154:0x034f, B:156:0x0355, B:158:0x035f, B:160:0x0365, B:162:0x036b, B:166:0x0373, B:167:0x0376, B:169:0x0380, B:171:0x0395, B:173:0x039b, B:175:0x03a5, B:177:0x03ab, B:179:0x03b1, B:180:0x03b8, B:182:0x03c2, B:184:0x03c8, B:186:0x03d2, B:188:0x03d8, B:189:0x03f0, B:191:0x03fb, B:196:0x040a, B:197:0x040f, B:199:0x041a, B:140:0x0307, B:142:0x030d, B:145:0x0315, B:147:0x031b, B:193:0x0403), top: B:241:0x02c3, inners: #2 }] */
    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public CommonConfig getCurrentTemplateConfigInternal(boolean z) {
        WallpaperInfo wallpaperInfo;
        CommonConfig commonConfigFromOTA;
        ExtraInfo extraInfo;
        ExtraInfo extraInfo2;
        WallpaperInfo wallpaperInfo2;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo3;
        TemplateConfig lockscreenInfo4;
        TemplateConfig lockscreenInfo5;
        ExtraInfo extraInfo3;
        ExtraInfo extraInfo4;
        WallpaperInfo wallpaperInfo6;
        WallpaperInfo wallpaperInfo7;
        WallpaperInfo wallpaperInfo8;
        TemplateApiImpl templateApiImpl = this;
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateConfigInternal: requestGalleryContent = " + z);
        String templateInfo = templateApiImpl.readTemplateInfo();
        String string = Settings.Secure.getString(templateApiImpl.context.getContentResolver(), "constant_lockscreen_info");
        if ((templateInfo == null || templateInfo.length() == 0) && (string == null || string.length() == 0)) {
            Log.d("Keyguard-Editor-TemplateApiImpl", "use default setting");
            templateInfo = "{\"lockscreenInfo\":{\"clockInfo\":{\"blendColor\":0,\"classicLine1\":100,\"classicLine2\":71,\"classicLine3\":101,\"classicLine4\":0,\"classicLine5\":0,\"clockEffect\":1,\"clockWeight\":0,\"disableContainerPassBlur\":false,\"enableDiffusion\":false,\"extraFlag\":0,\"infoAreaColor\":0,\"isAutoPrimaryColor\":true,\"isAutoSecondaryColor\":true,\"isDiffHourMinuteColor\":false,\"mSupportMultiWindowBlur\":false,\"primaryColor\":0,\"secondaryBlendColor\":0,\"secondaryColor\":0,\"style\":21,\"templateId\":\"classic_max\",\"unablePresetData\":false},\"version\":3,\"wallpaperInfo\":{\"magicType\":0,\"supportSubject\":false}}}";
        }
        String str = "";
        Integer numValueOf = null;
        if (templateInfo != null && templateInfo.length() != 0) {
            try {
                commonConfigFromOTA = CommonConfig.Companion.fromJson(templateInfo, string, templateApiImpl.context);
                WallpaperInfo wallpaperInfo9 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                String resourceType = wallpaperInfo9 != null ? wallpaperInfo9.getResourceType() : null;
                WallpaperInfo wallpaperInfo10 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                Log.d("Keyguard-Editor-TemplateApiImpl", "locktype=" + resourceType + "\ndesktype=" + (wallpaperInfo10 != null ? wallpaperInfo10.getResourceType() : null));
                WallpaperInfo wallpaperInfo11 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                WallpaperPositionInfo positionInfo = wallpaperInfo11 != null ? wallpaperInfo11.getPositionInfo() : null;
                WallpaperInfo wallpaperInfo12 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                Log.d("Keyguard-Editor-TemplateApiImpl", "positionInfo home: " + positionInfo + "  lock:$" + (wallpaperInfo12 != null ? wallpaperInfo12.getPositionInfo() : null));
                boolean zIsThirdPartyTheme = templateApiImpl.isThirdPartyTheme();
                commonConfigFromOTA.getLockscreenInfo().setThirdPartyTheme(zIsThirdPartyTheme);
                WallpaperController.Companion companion = WallpaperController.Companion;
                if (companion.getInstance(templateApiImpl.context).isMiuiDefaultWallpaper() && (wallpaperInfo8 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo()) != null) {
                    wallpaperInfo8.setSupportSubject(false);
                }
                if (zIsThirdPartyTheme) {
                    WallpaperInfo wallpaperInfo13 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                    if ((wallpaperInfo13 != null ? wallpaperInfo13.getResourceType() : null) == null) {
                        WallpaperInfo wallpaperInfo14 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                        if (wallpaperInfo14 != null) {
                            wallpaperInfo14.setResourceType("image");
                        }
                        WallpaperInfo wallpaperInfo15 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                        if (wallpaperInfo15 != null) {
                            wallpaperInfo15.setResourceType("image");
                        }
                    }
                } else {
                    WallpaperController companion2 = companion.getInstance(templateApiImpl.context);
                    String lockWallpaperType = companion2.getLockWallpaperType();
                    String homeWallpaperType = companion2.getHomeWallpaperType();
                    boolean zIsLockSetPkgSupportDepth = templateApiImpl.isLockSetPkgSupportDepth();
                    WallpaperInfo wallpaperInfo16 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                    String resourceType2 = wallpaperInfo16 != null ? wallpaperInfo16.getResourceType() : null;
                    WallpaperInfo wallpaperInfo17 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                    String resourceType3 = wallpaperInfo17 != null ? wallpaperInfo17.getResourceType() : null;
                    Wallpaper.Companion companion3 = Wallpaper.Companion;
                    if (!companion3.isSuperSavePowerWallpaper(lockWallpaperType) && ((!zIsLockSetPkgSupportDepth || resourceType2 == null || resourceType2.length() == 0) && (wallpaperInfo7 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo()) != null)) {
                        wallpaperInfo7.setResourceType(lockWallpaperType);
                    }
                    boolean zIsHomeSetPkgSupportDepth = templateApiImpl.isHomeSetPkgSupportDepth();
                    if (!companion3.isSuperSavePowerWallpaper(homeWallpaperType) && ((!zIsHomeSetPkgSupportDepth || resourceType3 == null || resourceType3.length() == 0) && (wallpaperInfo6 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo()) != null)) {
                        wallpaperInfo6.setResourceType(homeWallpaperType);
                    }
                    templateApiImpl.setLoopTypeForCurrentTemplate(commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo());
                    templateApiImpl.setLoopTypeForCurrentTemplate(commonConfigFromOTA.getHomeInfo().getWallpaperInfo());
                }
                if (z) {
                    FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                    Context applicationContext = templateApiImpl.context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                    WallpaperInfo wallpaperInfo18 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                    String resourceType4 = wallpaperInfo18 != null ? wallpaperInfo18.getResourceType() : null;
                    WallpaperInfo wallpaperInfo19 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                    String lockGalleryJson = fashionGalleryHelper.getLockGalleryJson(applicationContext, resourceType4, (wallpaperInfo19 == null || (extraInfo4 = wallpaperInfo19.getExtraInfo()) == null) ? null : extraInfo4.getLoopType());
                    if (lockGalleryJson == null) {
                        lockGalleryJson = "";
                    }
                    Context applicationContext2 = templateApiImpl.context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
                    WallpaperInfo wallpaperInfo20 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                    String resourceType5 = wallpaperInfo20 != null ? wallpaperInfo20.getResourceType() : null;
                    WallpaperInfo wallpaperInfo21 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                    String deskGalleryJson = fashionGalleryHelper.getDeskGalleryJson(applicationContext2, resourceType5, (wallpaperInfo21 == null || (extraInfo3 = wallpaperInfo21.getExtraInfo()) == null) ? null : extraInfo3.getLoopType());
                    if (deskGalleryJson != null) {
                        str = deskGalleryJson;
                    }
                    Log.d("Keyguard-Editor-TemplateApiImpl", "lockGalleryJson = " + lockGalleryJson + "\n deskGalleryJson = " + str);
                    WallpaperInfo wallpaperInfo22 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo22 != null) {
                        wallpaperInfo22.setGalleryContent(lockGalleryJson);
                    }
                    WallpaperInfo wallpaperInfo23 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo23 != null) {
                        wallpaperInfo23.setGalleryContent(str);
                    }
                }
                if (commonConfigFromOTA.getVersion() < 3) {
                    try {
                        TemplateApi.DefaultImpls.updateCurrentTemplate$default(templateApiImpl, commonConfigFromOTA, "ota flush", false, false, 12, null);
                        if (commonConfigFromOTA.isOtaMigrated()) {
                            templateApiImpl = this;
                            TemplateApi.DefaultImpls.updateCurrentTemplate$default(templateApiImpl, commonConfigFromOTA, "ota os4 flush", false, false, 4, null);
                            templateApiImpl.otaMigrationInProgress = true;
                            BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), Dispatchers.getIO(), null, new TemplateApiImpl$getCurrentTemplateConfigInternal$currentConfig$2(templateApiImpl, null), 2, null);
                        } else {
                            templateApiImpl = this;
                        }
                    } catch (Exception e) {
                        e = e;
                        templateApiImpl = this;
                        Log.w("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateConfigInternal: " + e);
                        commonConfigFromOTA = null;
                    }
                } else if (commonConfigFromOTA.isOtaMigrated()) {
                    templateApiImpl = this;
                    TemplateApi.DefaultImpls.updateCurrentTemplate$default(templateApiImpl, commonConfigFromOTA, "ota os4 flush", false, false, 4, null);
                    templateApiImpl.otaMigrationInProgress = true;
                    BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), Dispatchers.getIO(), null, new TemplateApiImpl$getCurrentTemplateConfigInternal$currentConfig$2(templateApiImpl, null), 2, null);
                } else {
                    templateApiImpl = this;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } else if (string == null || string.length() == 0) {
            commonConfigFromOTA = null;
        } else {
            try {
                TemplateConfig templateConfig = (TemplateConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(string, TemplateConfig.class);
                boolean zIsThirdPartyTheme2 = templateApiImpl.isThirdPartyTheme();
                templateConfig.setThirdPartyTheme(zIsThirdPartyTheme2);
                WallpaperController.Companion companion4 = WallpaperController.Companion;
                if (companion4.getInstance(templateApiImpl.context).isMiuiDefaultWallpaper() && (wallpaperInfo2 = templateConfig.getWallpaperInfo()) != null) {
                    wallpaperInfo2.setSupportSubject(false);
                }
                if (zIsThirdPartyTheme2) {
                    WallpaperInfo wallpaperInfo24 = templateConfig.getWallpaperInfo();
                    if ((wallpaperInfo24 != null ? wallpaperInfo24.getResourceType() : null) == null && (wallpaperInfo = templateConfig.getWallpaperInfo()) != null) {
                        wallpaperInfo.setResourceType("image");
                    }
                } else {
                    WallpaperInfo wallpaperInfo25 = templateConfig.getWallpaperInfo();
                    if (wallpaperInfo25 != null) {
                        wallpaperInfo25.setResourceType(companion4.getInstance(templateApiImpl.context).getLockWallpaperType());
                    }
                }
                templateApiImpl.setLoopTypeForCurrentTemplate(templateConfig.getWallpaperInfo());
                CommonConfig.Companion companion5 = CommonConfig.Companion;
                Intrinsics.checkNotNull(templateConfig);
                commonConfigFromOTA = companion5.fromOTA(templateConfig);
                if (z) {
                    WallpaperInfo wallpaperInfo26 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo26 != null) {
                        FashionGalleryHelper fashionGalleryHelper2 = FashionGalleryHelper.INSTANCE;
                        Context applicationContext3 = templateApiImpl.context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext3, "getApplicationContext(...)");
                        WallpaperInfo wallpaperInfo27 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                        String resourceType6 = wallpaperInfo27 != null ? wallpaperInfo27.getResourceType() : null;
                        WallpaperInfo wallpaperInfo28 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                        String lockGalleryJson2 = fashionGalleryHelper2.getLockGalleryJson(applicationContext3, resourceType6, (wallpaperInfo28 == null || (extraInfo2 = wallpaperInfo28.getExtraInfo()) == null) ? null : extraInfo2.getLoopType());
                        if (lockGalleryJson2 != null) {
                            str = lockGalleryJson2;
                        }
                        wallpaperInfo26.setGalleryContent(str);
                    }
                    WallpaperInfo wallpaperInfo29 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo29 != null) {
                        FashionGalleryHelper fashionGalleryHelper3 = FashionGalleryHelper.INSTANCE;
                        Context applicationContext4 = templateApiImpl.context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext4, "getApplicationContext(...)");
                        WallpaperInfo wallpaperInfo30 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                        String resourceType7 = wallpaperInfo30 != null ? wallpaperInfo30.getResourceType() : null;
                        WallpaperInfo wallpaperInfo31 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                        wallpaperInfo29.setGalleryContent(fashionGalleryHelper3.getDeskGalleryJson(applicationContext4, resourceType7, (wallpaperInfo31 == null || (extraInfo = wallpaperInfo31.getExtraInfo()) == null) ? null : extraInfo.getLoopType()));
                    }
                    WallpaperInfo wallpaperInfo32 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                    String galleryContent = wallpaperInfo32 != null ? wallpaperInfo32.getGalleryContent() : null;
                    WallpaperInfo wallpaperInfo33 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
                    Log.d("Keyguard-Editor-TemplateApiImpl", "lockGalleryJson = " + galleryContent + "\n deskGalleryJson = " + (wallpaperInfo33 != null ? wallpaperInfo33.getGalleryContent() : null));
                }
                if (commonConfigFromOTA.getLockscreenInfo().getVersion() >= 4 || DeviceUtil.INSTANCE.osVersion() < 4) {
                    templateApiImpl.updateCurrentTemplate(commonConfigFromOTA, "OTA", false, false);
                    if (commonConfigFromOTA.isOtaMigrated()) {
                        templateApiImpl.otaMigrationInProgress = true;
                        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), Dispatchers.getIO(), null, new TemplateApiImpl$getCurrentTemplateConfigInternal$currentConfig$1$1(templateApiImpl, null), 2, null);
                    }
                } else {
                    try {
                        companion5.migrateClockForOTA(templateApiImpl.context, commonConfigFromOTA);
                    } catch (Exception e3) {
                        Log.w("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateConfigInternal: OTA clock migration failed, will retry next time", e3);
                    }
                    templateApiImpl.updateCurrentTemplate(commonConfigFromOTA, "OTA", false, false);
                    if (commonConfigFromOTA.isOtaMigrated()) {
                        templateApiImpl.otaMigrationInProgress = true;
                        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), Dispatchers.getIO(), null, new TemplateApiImpl$getCurrentTemplateConfigInternal$currentConfig$1$1(templateApiImpl, null), 2, null);
                    }
                }
            } catch (Exception e4) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateConfigInternal: ota error:" + e4);
                commonConfigFromOTA = null;
            }
        }
        templateApiImpl.updateConfigForOTA(commonConfigFromOTA);
        if (commonConfigFromOTA != null && (lockscreenInfo5 = commonConfigFromOTA.getLockscreenInfo()) != null) {
            lockscreenInfo5.setRandomColorType(RandomColorUtil.INSTANCE.getCurrentRandomColorType(templateApiImpl.context));
        }
        if (commonConfigFromOTA == null || (lockscreenInfo4 = commonConfigFromOTA.getLockscreenInfo()) == null || !lockscreenInfo4.isThirdPartyTheme()) {
            if (commonConfigFromOTA != null && (lockscreenInfo = commonConfigFromOTA.getLockscreenInfo()) != null && (wallpaperInfo4 = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo4.updateMagicType(templateApiImpl.getCurrentMagicType(WallpaperWhich.LockWhich.INSTANCE.getWhich()));
            }
            if (commonConfigFromOTA != null && (homeInfo = commonConfigFromOTA.getHomeInfo()) != null && (wallpaperInfo3 = homeInfo.getWallpaperInfo()) != null) {
                wallpaperInfo3.updateMagicType(templateApiImpl.getCurrentMagicType(WallpaperWhich.HomeWhich.INSTANCE.getWhich()));
            }
        } else {
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateConfigInternal: isThirdPartyTheme = false");
        }
        Integer numValueOf2 = (commonConfigFromOTA == null || (lockscreenInfo3 = commonConfigFromOTA.getLockscreenInfo()) == null) ? null : Integer.valueOf(lockscreenInfo3.getRandomColorType());
        if (commonConfigFromOTA != null && (lockscreenInfo2 = commonConfigFromOTA.getLockscreenInfo()) != null && (wallpaperInfo5 = lockscreenInfo2.getWallpaperInfo()) != null) {
            numValueOf = Integer.valueOf(wallpaperInfo5.getMagicType());
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateConfigInternal: randomColorType=" + numValueOf2 + "magicType=" + numValueOf);
        return commonConfigFromOTA;
    }

    /* JADX WARN: Code duplicated, block: B:105:0x01f8  */
    /* JADX WARN: Code duplicated, block: B:146:0x0289  */
    /* JADX WARN: Code duplicated, block: B:172:0x02f4  */
    /* JADX WARN: Code duplicated, block: B:181:0x0317  */
    /* JADX WARN: Code duplicated, block: B:24:0x009b  */
    /* JADX WARN: Code duplicated, block: B:59:0x0159  */
    /* JADX WARN: Code duplicated, block: B:69:0x017b  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final CommonConfig getCurrentTemplateInternal(boolean z, boolean z2, boolean z3, boolean z4, WallpaperTypeInfo wallpaperTypeInfo, boolean z5, String str) {
        CommonConfig commonConfig;
        String str2;
        CommonConfig commonConfig2;
        boolean z6;
        boolean z7;
        Bitmap lockWallpaper;
        Bitmap lockWallpaper2;
        Bitmap sensorSmallWallpaper;
        Bitmap bitmap;
        Bitmap homeWallpaper;
        Bitmap sensorSmallWallpaper2;
        Bitmap homeWallpaper2;
        Bitmap sensorLargeWallpaper;
        Bitmap lockWallpaper3;
        Bitmap bitmap2;
        boolean zSameAs;
        Object obj;
        CommonConfig commonConfig3;
        TemplateApiImpl templateApiImpl;
        Bitmap homeWallpaper3;
        boolean z8;
        Bitmap wallpaperForPickColor;
        try {
            CommonConfig currentTemplateConfigInternal$default = TemplateApi.DefaultImpls.getCurrentTemplateConfigInternal$default(this, false, 1, null);
            if (currentTemplateConfigInternal$default == null) {
                return null;
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateInternal: getWallpaper=" + z + " getSourceWallpaper=" + z2 + " needDarkenWallpaperFromTheme=" + z3 + " needUpdateCurrentTemplate=" + z4 + " outerWallpaperTypeInfo=" + (wallpaperTypeInfo != null) + " needReloadCurrentWallpaper=" + z5 + " wallpaperTargetReal=" + ((Object) str));
            if (wallpaperTypeInfo == null || Intrinsics.areEqual(str, "all")) {
                str2 = str;
            } else {
                WallpaperController companion = WallpaperController.Companion.getInstance(this.context, false);
                String lockWallpaperType = companion.getLockWallpaperType();
                String homeWallpaperType = companion.getHomeWallpaperType();
                Wallpaper.Companion companion2 = Wallpaper.Companion;
                if (companion2.needWallpaperTargetAll(lockWallpaperType) || companion2.needWallpaperTargetAll(homeWallpaperType) || TemplateApi.DefaultImpls.isThirdPartyWallpaper$default(this, null, 1, null)) {
                    str2 = "all";
                } else {
                    str2 = str;
                }
            }
            if (wallpaperTypeInfo == null || !(Intrinsics.areEqual(str2, "all") || Intrinsics.areEqual(str2, "lock"))) {
                commonConfig = null;
            } else {
                StringBuilder sb = new StringBuilder();
                commonConfig = null;
                try {
                    sb.append("reset auto color on outer wallpaper change, target=");
                    sb.append((Object) str2);
                    Log.i("Keyguard-Editor-TemplateApiImpl", sb.toString());
                    currentTemplateConfigInternal$default.getLockscreenInfo().getClockInfo().setAutoPrimaryColor(true);
                    currentTemplateConfigInternal$default.getLockscreenInfo().getClockInfo().setAutoSecondaryColor(true);
                    currentTemplateConfigInternal$default.getLockscreenInfo().getClockInfo().setPrimaryFontColorPanelId(0);
                    currentTemplateConfigInternal$default.getLockscreenInfo().getClockInfo().setSecondaryFontColorPanelId(0);
                } catch (Exception e) {
                    e = e;
                }
            }
            if (wallpaperTypeInfo != null && Intrinsics.areEqual(str2, "all")) {
                updateOuterWallpaperInfo(currentTemplateConfigInternal$default, wallpaperTypeInfo, str2);
                return currentTemplateConfigInternal$default;
            }
            TemplateApi companion3 = TemplateApi.Companion.getInstance(this.context);
            int iTryClearLocalInvalidWallpaper = z5 ? companion3.tryClearLocalInvalidWallpaper() : 0;
            Log.d("Keyguard-Editor-TemplateApiImpl", "deleteType " + iTryClearLocalInvalidWallpaper);
            if (!z5 || iTryClearLocalInvalidWallpaper == 0) {
                if (z4) {
                    updateCurrentTemplateWallpaperInfo(currentTemplateConfigInternal$default);
                }
                if (z) {
                    if (wallpaperTypeInfo != null) {
                        if (Intrinsics.areEqual(str2, "lock")) {
                            WallpaperInfo wallpaperInfo = currentTemplateConfigInternal$default.getHomeInfo().getWallpaperInfo();
                            Intrinsics.checkNotNull(wallpaperInfo);
                            updateCurrentTemplateWallpaper(wallpaperInfo, z2);
                        } else {
                            WallpaperInfo wallpaperInfo2 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                            Intrinsics.checkNotNull(wallpaperInfo2);
                            updateCurrentTemplateWallpaper(wallpaperInfo2, z2);
                        }
                        updateOuterWallpaperInfo(currentTemplateConfigInternal$default, wallpaperTypeInfo, str2);
                    } else {
                        WallpaperInfo wallpaperInfo3 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                        Intrinsics.checkNotNull(wallpaperInfo3);
                        updateCurrentTemplateWallpaper(wallpaperInfo3, z2);
                        WallpaperInfo wallpaperInfo4 = currentTemplateConfigInternal$default.getHomeInfo().getWallpaperInfo();
                        Intrinsics.checkNotNull(wallpaperInfo4);
                        updateCurrentTemplateWallpaper(wallpaperInfo4, z2);
                    }
                    commonConfig2 = currentTemplateConfigInternal$default;
                    tryLoadSubjectBitmap$default(this, commonConfig2, true, false, false, 8, null);
                    tryLoadDepthWallpaper(commonConfig2);
                } else {
                    commonConfig2 = currentTemplateConfigInternal$default;
                }
                darkenCurWallpaperIfNeed(z3, commonConfig2, z2, !z);
                return commonConfig2;
            }
            updateCurrentTemplateWallpaperInfo(currentTemplateConfigInternal$default);
            WallpaperSource wallpaperSourceRequireLockScreenWallpaper = companion3.requireLockScreenWallpaper(currentTemplateConfigInternal$default.deepCopy(), iTryClearLocalInvalidWallpaper);
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isFold()) {
                Wallpaper.Companion companion4 = Wallpaper.Companion;
                WallpaperInfo wallpaperInfo5 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                if (companion4.isSensorWallpaper(wallpaperInfo5 != null ? wallpaperInfo5.getResourceType() : commonConfig)) {
                    z6 = true;
                } else {
                    z6 = false;
                }
            } else {
                z6 = false;
            }
            if (deviceUtil.isFold()) {
                Wallpaper.Companion companion5 = Wallpaper.Companion;
                WallpaperInfo wallpaperInfo6 = currentTemplateConfigInternal$default.getHomeInfo().getWallpaperInfo();
                if (companion5.isSensorWallpaper(wallpaperInfo6 != null ? wallpaperInfo6.getResourceType() : commonConfig)) {
                    z7 = true;
                } else {
                    z7 = false;
                }
            } else {
                z7 = false;
            }
            if (iTryClearLocalInvalidWallpaper == 2 || iTryClearLocalInvalidWallpaper == 3) {
                if (z6) {
                    WallpaperInfo wallpaperInfo7 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo7 != null) {
                        if (wallpaperSourceRequireLockScreenWallpaper != null && (lockWallpaper = wallpaperSourceRequireLockScreenWallpaper.getSensorLargeWallpaper()) != null) {
                            bitmap = lockWallpaper;
                        } else if (wallpaperSourceRequireLockScreenWallpaper != null) {
                            Bitmap lockWallpaper4 = wallpaperSourceRequireLockScreenWallpaper.getLockWallpaper();
                            bitmap = lockWallpaper4;
                        } else {
                            bitmap = commonConfig;
                        }
                        wallpaperInfo7.setCurrentWallpaper(bitmap);
                    }
                    WallpaperInfo wallpaperInfo8 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo8 != null) {
                        if (wallpaperSourceRequireLockScreenWallpaper == null || (sensorSmallWallpaper = wallpaperSourceRequireLockScreenWallpaper.getSensorSmallWallpaper()) == null) {
                            lockWallpaper2 = wallpaperSourceRequireLockScreenWallpaper != null ? wallpaperSourceRequireLockScreenWallpaper.getLockWallpaper() : commonConfig;
                        }
                        lockWallpaper2 = sensorSmallWallpaper;
                        wallpaperInfo8.setCurrentSmallSensorWallpaper(lockWallpaper2);
                    }
                } else {
                    WallpaperInfo wallpaperInfo9 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo9 != null) {
                        MagicType magicType = MagicType.INSTANCE;
                        WallpaperInfo wallpaperInfo10 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                        if (magicType.isDepthVideo(wallpaperInfo10 != null ? Integer.valueOf(wallpaperInfo10.getMagicType()) : commonConfig)) {
                            if (wallpaperSourceRequireLockScreenWallpaper != null) {
                                lockWallpaper = wallpaperSourceRequireLockScreenWallpaper.getWallpaperForPickColor();
                            } else {
                                lockWallpaper = commonConfig;
                            }
                        } else if (wallpaperSourceRequireLockScreenWallpaper != null) {
                            lockWallpaper = wallpaperSourceRequireLockScreenWallpaper.getLockWallpaper();
                        } else {
                            lockWallpaper = commonConfig;
                        }
                        wallpaperInfo9.setCurrentWallpaper(lockWallpaper);
                    }
                }
            }
            if (iTryClearLocalInvalidWallpaper == 1 || iTryClearLocalInvalidWallpaper == 3) {
                if (z7) {
                    WallpaperInfo wallpaperInfo11 = currentTemplateConfigInternal$default.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo11 != null) {
                        if (wallpaperSourceRequireLockScreenWallpaper == null || (sensorLargeWallpaper = wallpaperSourceRequireLockScreenWallpaper.getSensorLargeWallpaper()) == null) {
                            homeWallpaper2 = wallpaperSourceRequireLockScreenWallpaper != null ? wallpaperSourceRequireLockScreenWallpaper.getHomeWallpaper() : commonConfig;
                        }
                        homeWallpaper2 = sensorLargeWallpaper;
                        wallpaperInfo11.setCurrentWallpaper(homeWallpaper2);
                    }
                    WallpaperInfo wallpaperInfo12 = currentTemplateConfigInternal$default.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo12 != null) {
                        if (wallpaperSourceRequireLockScreenWallpaper == null || (sensorSmallWallpaper2 = wallpaperSourceRequireLockScreenWallpaper.getSensorSmallWallpaper()) == null) {
                            homeWallpaper = wallpaperSourceRequireLockScreenWallpaper != null ? wallpaperSourceRequireLockScreenWallpaper.getHomeWallpaper() : commonConfig;
                        }
                        homeWallpaper = sensorSmallWallpaper2;
                        wallpaperInfo12.setCurrentSmallSensorWallpaper(homeWallpaper);
                    }
                } else {
                    WallpaperInfo wallpaperInfo13 = currentTemplateConfigInternal$default.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo13 != null) {
                        wallpaperInfo13.setCurrentWallpaper(wallpaperSourceRequireLockScreenWallpaper != null ? wallpaperSourceRequireLockScreenWallpaper.getHomeWallpaper() : commonConfig);
                    }
                }
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            MagicType magicType2 = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo14 = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
            if (magicType2.isDepthVideo(wallpaperInfo14 != null ? Integer.valueOf(wallpaperInfo14.getMagicType()) : commonConfig)) {
                if (wallpaperSourceRequireLockScreenWallpaper != null) {
                    wallpaperForPickColor = wallpaperSourceRequireLockScreenWallpaper.getWallpaperForPickColor();
                } else {
                    bitmap2 = commonConfig;
                }
            } else if (wallpaperSourceRequireLockScreenWallpaper != null) {
                lockWallpaper3 = wallpaperSourceRequireLockScreenWallpaper.getLockWallpaper();
            } else {
                bitmap2 = commonConfig;
            }
            if (bitmap2 != 0) {
                zSameAs = bitmap2.sameAs(wallpaperSourceRequireLockScreenWallpaper != null ? wallpaperSourceRequireLockScreenWallpaper.getHomeWallpaper() : commonConfig);
            } else {
                bitmap2 = lockWallpaper3;
                zSameAs = false;
            }
            if (zSameAs) {
                bitmap2 = wallpaperForPickColor;
                if (iTryClearLocalInvalidWallpaper != 3) {
                    WallpaperInfo wallpaperInfo15 = currentTemplateConfigInternal$default.getHomeInfo().getWallpaperInfo();
                    obj = "lock";
                    if (Intrinsics.areEqual(wallpaperInfo15 != null ? wallpaperInfo15.getResourceType() : commonConfig, "image")) {
                        String strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER);
                        String strGenerateCurrentTemplateFilePath2 = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
                        if (iTryClearLocalInvalidWallpaper == 2) {
                            Bitmap bitmapFromFile$default = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, strGenerateCurrentTemplateFilePath2, 0, 0, null, 14, null);
                            if (bitmap2 == 0 || !bitmap2.sameAs(bitmapFromFile$default)) {
                                z8 = false;
                            } else {
                                z8 = true;
                            }
                        } else {
                            Bitmap bitmapFromFile$default2 = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, strGenerateCurrentTemplateFilePath, 0, 0, null, 14, null);
                            if (wallpaperSourceRequireLockScreenWallpaper == null || (homeWallpaper3 = wallpaperSourceRequireLockScreenWallpaper.getHomeWallpaper()) == null || !homeWallpaper3.sameAs(bitmapFromFile$default2)) {
                                z8 = false;
                            } else {
                                z8 = true;
                            }
                        }
                        zSameAs = z8;
                    }
                } else {
                    bitmap2 = wallpaperForPickColor;
                    obj = "lock";
                }
            } else {
                bitmap2 = wallpaperForPickColor;
                obj = "lock";
            }
            Log.d("Keyguard-Editor-TemplateApiImpl", "reload for third wallpaper pkg isSameWallpaper=" + zSameAs + " cost=" + (System.currentTimeMillis() - jCurrentTimeMillis));
            if (zSameAs) {
                currentTemplateConfigInternal$default.getHomeInfo().setFollowStatus(2);
                commonConfig3 = currentTemplateConfigInternal$default;
                TemplateApi.DefaultImpls.updateCurrentTemplate$default(this, commonConfig3, "same home in common", false, false, 12, null);
                templateApiImpl = this;
            } else {
                commonConfig3 = currentTemplateConfigInternal$default;
                commonConfig3.getHomeInfo().setFollowStatus(0);
                templateApiImpl = this;
                TemplateApi.DefaultImpls.updateCurrentTemplate$default(templateApiImpl, commonConfig3, "diff home in common", false, false, 12, null);
            }
            if (wallpaperTypeInfo != null) {
                templateApiImpl.updateOuterWallpaperInfo(commonConfig3, wallpaperTypeInfo, str2);
            }
            if ((wallpaperTypeInfo == null || !Intrinsics.areEqual(str, "home")) && iTryClearLocalInvalidWallpaper == 2) {
                WallpaperInfo wallpaperInfo16 = commonConfig3.getHomeInfo().getWallpaperInfo();
                Intrinsics.checkNotNull(wallpaperInfo16);
                templateApiImpl.updateCurrentTemplateWallpaper(wallpaperInfo16, z2);
                Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaper home with origin");
                return commonConfig3;
            }
            if ((wallpaperTypeInfo != null && Intrinsics.areEqual(str, obj)) || iTryClearLocalInvalidWallpaper != 1) {
                CommonConfig commonConfig4 = commonConfig3;
                if (wallpaperTypeInfo != null && Intrinsics.areEqual(str, "home")) {
                    WallpaperInfo wallpaperInfo17 = commonConfig4.getLockscreenInfo().getWallpaperInfo();
                    Intrinsics.checkNotNull(wallpaperInfo17);
                    templateApiImpl.updateCurrentTemplateWallpaper(wallpaperInfo17, z2);
                }
                return commonConfig4;
            }
            WallpaperInfo wallpaperInfo18 = commonConfig3.getLockscreenInfo().getWallpaperInfo();
            Intrinsics.checkNotNull(wallpaperInfo18);
            templateApiImpl.updateCurrentTemplateWallpaper(wallpaperInfo18, z2);
            Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaper lock with origin");
            tryLoadSubjectBitmap$default(templateApiImpl, commonConfig3, true, false, false, 8, null);
            CommonConfig commonConfig5 = commonConfig3;
            templateApiImpl.tryLoadDepthWallpaper(commonConfig5);
            templateApiImpl.darkenCurWallpaperIfNeed(z3, commonConfig5, z2, !z);
            return commonConfig5;
        } catch (Exception e2) {
            e = e2;
            commonConfig = null;
        }
        Log.e("Keyguard-Editor-TemplateApiImpl", "currentTemplate: " + e, e);
        return commonConfig;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public String getCurrentTemplateWallpaperPath() {
        return this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap getCurrentWallpaper(final WallpaperInfo wallpaperInfo) {
        Bitmap placeholderWallpaper;
        TemplateFilePathGenerator.TemplateFileType wallpaperFileType;
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentWallpaper: lock:" + (wallpaperInfo != null));
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda30
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return TemplateApiImpl.getCurrentWallpaper$lambda$117(wallpaperInfo, this);
                }
            };
            if (!isThirdPartyTheme()) {
                if (isThirdPartyWallpaper((wallpaperInfo != null ? wallpaperInfo.getInfoType() : null) == WallpaperInfoType.LOCK_WALLPAPER ? WallpaperWhich.LockWhich.INSTANCE : WallpaperWhich.HomeWhich.INSTANCE)) {
                    Bitmap bitmap = this.thirdPartyWallpaperServicePreview;
                    placeholderWallpaper = bitmap == null ? getPlaceholderWallpaper() : bitmap;
                } else {
                    placeholderWallpaper = (Bitmap) function0.invoke();
                }
            } else if (Process.INSTANCE.isKeyguardeditorProcess()) {
                if (wallpaperInfo == null || (wallpaperFileType = wallpaperInfo.getWallpaperFileType()) == null) {
                    wallpaperFileType = TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
                }
                placeholderWallpaper = getCurrentOriginalWallpaperInternal(getCurrentWallpaperPathSafely(wallpaperInfo, wallpaperFileType));
                if (placeholderWallpaper == null) {
                    placeholderWallpaper = (Bitmap) function0.invoke();
                }
            } else {
                placeholderWallpaper = getCurrentOriginWallpaperByProvider(this.context, wallpaperInfo != null ? wallpaperInfo.getInfoType() : null);
                if (placeholderWallpaper == null) {
                    placeholderWallpaper = (Bitmap) function0.invoke();
                }
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "get lock from mwms: cost = " + (System.currentTimeMillis() - jCurrentTimeMillis));
            return placeholderWallpaper;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "getCurrentLockWallpaper: " + e);
            return null;
        }
    }

    public final String getCurrentWallpaperPathSafely(WallpaperInfo wallpaperInfo, TemplateFilePathGenerator.TemplateFileType templateFileType) {
        String source = null;
        switch (WhenMappings.$EnumSwitchMapping$0[templateFileType.ordinal()]) {
            case 1:
                if (wallpaperInfo != null) {
                    source = wallpaperInfo.getSource();
                }
                break;
            case 2:
                if (wallpaperInfo != null) {
                    source = wallpaperInfo.getSource();
                }
                break;
            case 3:
                if (wallpaperInfo != null) {
                    source = wallpaperInfo.getSubject();
                }
                break;
            case 4:
                if (wallpaperInfo != null) {
                    source = wallpaperInfo.getSubject();
                }
                break;
            case 5:
                if (wallpaperInfo != null) {
                    source = wallpaperInfo.getCropSubject();
                }
                break;
            case 6:
                if (wallpaperInfo != null) {
                    source = wallpaperInfo.getFrontBack();
                }
                break;
            case 7:
                if (wallpaperInfo != null) {
                    source = wallpaperInfo.getCroppedFrontBack();
                }
                break;
        }
        if (FileUtil.isFileExist(source)) {
            Intrinsics.checkNotNull(source);
            return source;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentOriginalWallpaperPath: config path not exit " + templateFileType);
        return this.filePathGenerator.generateCurrentTemplateFilePath(templateFileType);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public TemplateHistoryConfig getHistoryTemplate(long j) {
        if (!TemplateSource.INSTANCE.isFromHistoryTemplate(j)) {
            return null;
        }
        try {
            return this.templateDao.query(j);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "getHistoryTemplate: ", e);
            return null;
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public List getHistoryTemplates(boolean z) {
        List listQueryAll = z ? this.templateDao.queryAll() : this.templateDao.queryAllSupport();
        Set allHistoryDirs = getAllHistoryDirs();
        ArrayList arrayList = new ArrayList();
        for (Object obj : listQueryAll) {
            TemplateHistoryConfig templateHistoryConfig = (TemplateHistoryConfig) obj;
            if (allHistoryDirs == null || allHistoryDirs.contains(String.valueOf(templateHistoryConfig.getLastModifiedTime())) || templateHistoryConfig.isCurrent() == 1) {
                arrayList.add(obj);
            } else {
                Log.e("Keyguard-Editor-TemplateApiImpl", "history not exit:" + templateHistoryConfig.getLastModifiedTime() + " history=" + allHistoryDirs);
                this.templateDao.delete(templateHistoryConfig);
            }
        }
        if (!SPUtilKt.getBoolean(this.context, "clear_smart_frame_history_template", true)) {
            return arrayList;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "getHistoryTemplates: try clear smart frame templates");
        SPUtilKt.putBoolean(this.context, "clear_smart_frame_history_template", false);
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            TemplateHistoryConfig templateHistoryConfig2 = (TemplateHistoryConfig) obj2;
            boolean zAreEqual = Intrinsics.areEqual(templateHistoryConfig2.getTemplateId(), "smart_frame");
            if (zAreEqual) {
                Log.i("Keyguard-Editor-TemplateApiImpl", "getHistoryTemplates: clear smart frame template");
                tryDeleteHistoryTemplateFiles(Long.valueOf(templateHistoryConfig2.getLastModifiedTime()));
                this.templateDao.delete(templateHistoryConfig2);
            }
            if (!zAreEqual) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    public final String getHomeWallpaperSetPkg() {
        return Settings.Secure.getString(this.context.getContentResolver(), "wallpaper_changed_1");
    }

    public final String getLockWallpaperSetPkg() {
        return Settings.Secure.getString(this.context.getContentResolver(), "wallpaper_changed_2");
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap getLosslessTemplateBitmap(String str, boolean z, int i, int i2) {
        return TemplateDataUtil.getTemplateBitmap(str, z, i, i2, true);
    }

    public final String getOwnerInfo() {
        return LockPatternUtils.INSTANCE.getOwnerInfo(this.context);
    }

    public final Bitmap getPlaceholderWallpaper() {
        Drawable drawable = ContextCompat.getDrawable(this.context, R.drawable.kg_wallpaper_invalid_placeholder);
        if (drawable != null) {
            return DrawableKt.toBitmapOrNull$default(drawable, 0, 0, null, 7, null);
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public PresetTemplateConfig getPresetTemplate(Context uiContext, TemplateItemConfig config, boolean z, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(uiContext, "uiContext");
        Intrinsics.checkNotNullParameter(config, "config");
        String strGeneratePresetTemplateFilePath = this.filePathGenerator.generatePresetTemplateFilePath(config.getTemplateName(), TemplateFilePathGenerator.TemplateFileType.CONFIG);
        PresetTemplateConfig presetTemplateConfig = !z ? new PresetTemplateConfig(null, null, null, strGeneratePresetTemplateFilePath, i3) : Companion.preloadPresetTemplate(uiContext, config, strGeneratePresetTemplateFilePath, i, i2, i3);
        presetTemplateConfig.setLoadingDrawable(getTemplateLoadingDrawable(config.getLoadingColor()));
        config.setReqWidth(i);
        config.setReqHeight(i2);
        presetTemplateConfig.setLoadCompleted(z);
        presetTemplateConfig.setBindItemConfig(config);
        return presetTemplateConfig;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public List getPresetTemplateGroup() {
        return TemplateDataUtil.getPresetTemplateGroup(this.context);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap getPreviewCache(String cacheName) {
        Intrinsics.checkNotNullParameter(cacheName, "cacheName");
        return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, this.filePathGenerator.getCacheDirPath() + File.separator + cacheName, 0, 0, null, 14, null);
    }

    public final String getSuperWallpaperName() {
        android.app.WallpaperInfo wallpaperInfo = WallpaperManager.getInstance(this.context).getWallpaperInfo();
        String packageName = wallpaperInfo != null ? wallpaperInfo.getPackageName() : null;
        if (!TextUtils.isEmpty(packageName)) {
            Wallpaper.Companion companion = Wallpaper.Companion;
            if (companion.getSuperWallpaperPackageHashMap().containsKey(packageName)) {
                return (String) companion.getSuperWallpaperPackageHashMap().get(packageName);
            }
        }
        Log.w("Keyguard-Editor-TemplateApiImpl", "getSuperWallpaperName: wallpaper pkg " + packageName + " is not in wallpapers");
        return null;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public CommonConfig getTemplate(String configUri, boolean z, int i) throws Throwable {
        Intrinsics.checkNotNullParameter(configUri, "configUri");
        Log.i("Keyguard-Editor-TemplateApiImpl", "getTemplate: " + configUri);
        CommonConfig templateConfig = TemplateDataUtil.getTemplateConfig(this.context, configUri, z, i);
        if (templateConfig == null) {
            return null;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "getTemplate: config=" + templateConfig);
        tryLoadSubjectBitmap$default(this, templateConfig, false, z, false, 8, null);
        tryLoadDepthWallpaper(templateConfig);
        return templateConfig;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Drawable getTemplateLoadingDrawable(int i) {
        return new ColorDrawable(i);
    }

    public final Bitmap getWallpaperInternal(WallpaperInfo wallpaperInfo, boolean z) {
        TemplateFilePathGenerator.TemplateFileType wallpaperFileType;
        if (TemplateApi.DefaultImpls.isThirdPartyWallpaper$default(this, null, 1, null)) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "getLockWallpaperInternal: " + this.thirdPartyWallpaperServicePreview);
            return this.thirdPartyWallpaperServicePreview;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "getWallpaperInternal getSourceWallpaper:" + z);
        Bitmap currentOriginalWallpaper = z ? getCurrentOriginalWallpaper(wallpaperInfo) : getCurrentWallpaper(wallpaperInfo);
        if (wallpaperInfo == null || (wallpaperFileType = wallpaperInfo.getWallpaperFileType()) == null) {
            wallpaperFileType = TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
        }
        String currentWallpaperPathSafely = getCurrentWallpaperPathSafely(wallpaperInfo, wallpaperFileType);
        boolean zIsFileExist = FileUtil.isFileExist(currentWallpaperPathSafely);
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateInternal: currentWallpaperSourceExist=" + zIsFileExist + ", currentWallpaper=" + (currentOriginalWallpaper != null));
        if (!zIsFileExist && currentOriginalWallpaper != null) {
            BitmapUtil.INSTANCE.toJPEG(currentOriginalWallpaper, currentWallpaperPathSafely);
        }
        return currentOriginalWallpaper;
    }

    public final void imageToVideoIfPresetDepthVideo(CommonConfig commonConfig, boolean z) {
        String source;
        if (z) {
            if (commonConfig == null) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "createWallpaperInfo: templateConfig is null");
                return;
            }
            DepthPresetDownloadManager.Companion companion = DepthPresetDownloadManager.Companion;
            if (companion.isPresetDepthVideo(commonConfig.getLockscreenInfo().getWallpaperInfo(), z)) {
                String presetDepthVideoPath = companion.getPresetDepthVideoPath(commonConfig.getLockscreenInfo().getWallpaperInfo());
                WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                String str = (wallpaperInfo == null || (source = wallpaperInfo.getSource()) == null) ? presetDepthVideoPath : source;
                WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                WallpaperInfo wallpaperInfo3 = new WallpaperInfo(str, wallpaperInfo2 != null ? wallpaperInfo2.getMask() : null, null, null, null, MagicType.TYPE_DEPTH_VIDEO, null, null, "video", presetDepthVideoPath, false, null, false, null, false, null, false, true, presetDepthVideoPath, null, null, null, null, 0, false, 1, 0, null, false, null, null, 2088281088, null);
                WallpaperInfo wallpaperInfoCopy$default = WallpaperInfo.copy$default(wallpaperInfo3, null, null, null, null, null, 0, null, null, null, null, false, null, false, null, false, null, false, false, null, null, null, null, null, 0, false, 0, 0, null, false, null, null, 2147090399, null);
                WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                wallpaperInfo3.setSha1(wallpaperInfo4 != null ? wallpaperInfo4.getSha1() : null);
                WallpaperInfo wallpaperInfo5 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                wallpaperInfo3.setVideoName(wallpaperInfo5 != null ? wallpaperInfo5.getVideoName() : null);
                WallpaperInfo wallpaperInfo6 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                WallpaperInfo wallpaperInfo7 = commonConfig.getHomeInfo().getWallpaperInfo();
                commonConfig.getLockscreenInfo().setWallpaperInfo(wallpaperInfo3);
                commonConfig.getLockscreenInfo().setWallpaperInfoForVideo(wallpaperInfo6);
                commonConfig.getHomeInfo().setWallpaperInfo(wallpaperInfoCopy$default);
                commonConfig.getHomeInfo().setWallpaperInfoForVideo(wallpaperInfo7);
            }
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void insertHistoryConfig(TemplateHistoryConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        Log.i("Keyguard-Editor-TemplateApiImpl", "insertHistoryConfig: " + config);
        this.templateDao.addOrReplace(config);
        try {
            KeyguardEditorMiSightUtil.INSTANCE.step3EndSuccess();
        } catch (Exception unused) {
        }
        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), Dispatchers.getIO(), null, new AnonymousClass2(null), 2, null);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0034  */
    public final boolean isDefaultConfig(CommonConfig commonConfig) {
        boolean z;
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo = commonConfig.getLockscreenInfo();
        String originResourcePath = null;
        String source = (lockscreenInfo == null || (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getSource();
        if (source == null || source.length() == 0) {
            TemplateConfig lockscreenInfo2 = commonConfig.getLockscreenInfo();
            if (lockscreenInfo2 != null && (wallpaperInfo = lockscreenInfo2.getWallpaperInfo()) != null) {
                originResourcePath = wallpaperInfo.getOriginResourcePath();
            }
            if (originResourcePath == null || originResourcePath.length() == 0) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        Log.d("Keyguard-Editor-TemplateApiImpl", "isDefault=" + z + " config=" + commonConfig);
        return z;
    }

    public final boolean isDefaultTheme() {
        return SystemEditor.INSTANCE.getOsVersion() >= 2 && Settings.Secure.getInt(this.context.getContentResolver(), "isDefaultTheme", 1) == 1;
    }

    public boolean isDualClock() {
        return DualClockManager.isDualClockAvailable(this.context);
    }

    public final boolean isEditorSetLockWallpaper() {
        String lockWallpaperSetPkg = getLockWallpaperSetPkg();
        Log.d("Keyguard-Editor-TemplateApiImpl", "isEditorSetLockWallpaper: setLockWallpaperPkg=" + lockWallpaperSetPkg);
        return Intrinsics.areEqual(lockWallpaperSetPkg, this.context.getPackageName());
    }

    public final boolean isGallerySetLockWallpaper() {
        String lockWallpaperSetPkg = getLockWallpaperSetPkg();
        Log.d("Keyguard-Editor-TemplateApiImpl", "isGallerySetLockWallpaper: setLockWallpaperPkg=" + lockWallpaperSetPkg);
        return Intrinsics.areEqual(lockWallpaperSetPkg, FashionGalleryHelper.INSTANCE.getFashionGalleryPackage());
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean isHomeSetPkgSupportDepth() {
        String homeWallpaperSetPkg = getHomeWallpaperSetPkg();
        Log.d("Keyguard-Editor-TemplateApiImpl", "isHomeSetPkgSupportDepth: homeWallpaperSetPkg=" + homeWallpaperSetPkg);
        return Intrinsics.areEqual(homeWallpaperSetPkg, "com.miui.aod");
    }

    public final boolean isInvalidConfig(CommonConfig commonConfig) {
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        String source = wallpaperInfo != null ? wallpaperInfo.getSource() : null;
        WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
        return Intrinsics.areEqual(source, wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean isLockSetPkgSupportDepth() {
        String lockWallpaperSetPkg = getLockWallpaperSetPkg();
        Log.d("Keyguard-Editor-TemplateApiImpl", "isLockSetPkgSupportDepth: setLockWallpaperPkg=" + lockWallpaperSetPkg);
        return Intrinsics.areEqual(lockWallpaperSetPkg, "com.miui.aod");
    }

    public final boolean isMagicTypeChanged(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2) {
        boolean z = false;
        if (wallpaperInfo != null && wallpaperInfo.getMagicType() == wallpaperInfo2.getMagicType()) {
            z = true;
        }
        return !z;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean isMiuiSetLockWallpaper() {
        String lockWallpaperSetPkg = getLockWallpaperSetPkg();
        Log.d("Keyguard-Editor-TemplateApiImpl", "isMiuiSetLockWallpaper: setLockWallpaperPkg=" + lockWallpaperSetPkg);
        return Intrinsics.areEqual(lockWallpaperSetPkg, FashionGalleryHelper.INSTANCE.getFashionGalleryPackage()) || Intrinsics.areEqual(lockWallpaperSetPkg, "com.miui.aod") || Intrinsics.areEqual(lockWallpaperSetPkg, "com.android.thememanager");
    }

    public final boolean isScreenshotCopyError(String str, String str2, String str3) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            return str == null;
        }
        if (deviceUtil.isJ18()) {
            return str == null || str2 == null;
        }
        if (!deviceUtil.isFold() && !deviceUtil.isPad()) {
            return false;
        }
        int screenStatus = deviceUtil.getScreenStatus(this.context);
        if (screenStatus == 2) {
            return str2 == null;
        }
        if (screenStatus != 3) {
            return str == null;
        }
        return str3 == null;
    }

    public final boolean isScreenshotCopyErrorHistory(String str, String str2, String str3) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            return str == null;
        }
        if (deviceUtil.isJ18()) {
            return str == null || str2 == null;
        }
        if (deviceUtil.isFold()) {
            return str == null || str2 == null || str3 == null;
        }
        if (deviceUtil.isPad()) {
            return str2 == null || str3 == null;
        }
        return false;
    }

    public final boolean isScreenshotFilesExist(String str, String str2, String str3) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            return FileUtil.isFileExist(str);
        }
        if (deviceUtil.isJ18()) {
            return FileUtil.isFileExist(str) && FileUtil.isFileExist(str2);
        }
        if (deviceUtil.isFold()) {
            return FileUtil.isFileExist(str) && FileUtil.isFileExist(str2) && FileUtil.isFileExist(str3);
        }
        if (deviceUtil.isPad()) {
            return FileUtil.isFileExist(str2) && FileUtil.isFileExist(str3);
        }
        return FileUtil.isFileExist(str);
    }

    public final boolean isThemeManagerDarkenConfig(CommonConfig commonConfig) {
        if ((commonConfig.getLockscreenInfo().getWallpaperInfo() == null || !isEditorSetLockWallpaper()) && HierarchyImageView.Companion.isSupportHierarchy(commonConfig)) {
            WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo != null ? wallpaperInfo.isNeedDark() : false) {
                Wallpaper.Companion companion = Wallpaper.Companion;
                WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (companion.isNeedDark(null, wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean isThirdPartyTheme() {
        return Intrinsics.areEqual(WallpaperController.Companion.getInstance(this.context).getLockWallpaperType(), CategoryConstants.CATEGORY_MAML);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean isThirdPartyWallpaper(WallpaperWhich which) {
        Intrinsics.checkNotNullParameter(which, "which");
        boolean z = !WallpaperController.Companion.getInstance(this.context).isMiuiWallpaperComponentUsing(which.getWhich());
        Log.i("Keyguard-Editor-TemplateApiImpl", "isThirdPartyWallpaper: " + z);
        return z;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean isWallpaperFileChanged(CommonConfig commonConfig, CommonConfig applyTemplateConfig) {
        HomeConfig homeInfo;
        TemplateConfig lockscreenInfo;
        Intrinsics.checkNotNullParameter(applyTemplateConfig, "applyTemplateConfig");
        WallpaperInfo wallpaperInfo = null;
        WallpaperInfo wallpaperInfo2 = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo();
        if (commonConfig != null && (homeInfo = commonConfig.getHomeInfo()) != null) {
            wallpaperInfo = homeInfo.getWallpaperInfo();
        }
        WallpaperInfo wallpaperInfo3 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
        WallpaperInfo wallpaperInfo4 = applyTemplateConfig.getHomeInfo().getWallpaperInfo();
        if (wallpaperInfo3 != null && wallpaperInfo4 != null) {
            return (isWallpaperFileChanged(wallpaperInfo2, wallpaperInfo3) || isWallpaperFileChanged(wallpaperInfo, wallpaperInfo4)) || (isMagicTypeChanged(wallpaperInfo2, wallpaperInfo3) || isMagicTypeChanged(wallpaperInfo, wallpaperInfo4));
        }
        Log.e("Keyguard-Editor-TemplateApiImpl", "applyWallpaper error: ");
        return false;
    }

    public final boolean isWallpaperFileChanged(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2) {
        String originResourcePath;
        WallpaperPositionInfo positionInfo;
        return wallpaperInfo == null || (originResourcePath = wallpaperInfo.getOriginResourcePath()) == null || !originResourcePath.equals(wallpaperInfo2.getOriginResourcePath()) || (positionInfo = wallpaperInfo.getPositionInfo()) == null || !positionInfo.equals(wallpaperInfo2.getPositionInfo()) || !StringsKt.equals$default(wallpaperInfo.getResourceType(), wallpaperInfo2.getResourceType(), false, 2, null);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004c  */
    public final Bitmap loadCurrentTemplateSubjectCompat(CommonConfig commonConfig) {
        String str;
        Bitmap templateBitmapByProvider$default;
        String str2;
        TemplateConfig lockscreenInfo;
        String source;
        TemplateConfig lockscreenInfo2;
        Log.i("Keyguard-Editor-TemplateApiImpl", "loadCurrentTemplateSubjectCompat: ");
        String strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT);
        String strGenerateCurrentTemplateFilePath2 = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK);
        WallpaperInfo wallpaperInfo = null;
        WallpaperInfo wallpaperInfo2 = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo();
        if (isEditorSetLockWallpaper()) {
            if (!Intrinsics.areEqual(wallpaperInfo2 != null ? wallpaperInfo2.getSubject() : null, strGenerateCurrentTemplateFilePath)) {
                if (!Intrinsics.areEqual(wallpaperInfo2 != null ? wallpaperInfo2.getSubject() : null, strGenerateCurrentTemplateFilePath2)) {
                    str = null;
                }
            }
            str = strGenerateCurrentTemplateFilePath;
        } else {
            str = null;
        }
        if (str == null) {
            templateBitmapByProvider$default = null;
        } else if (Process.INSTANCE.isKeyguardeditorProcess()) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "loadCurrentTemplateSubjectCompat: subject exists, load it from aod");
            templateBitmapByProvider$default = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, str, 0, 0, null, 14, null);
        } else {
            Log.i("Keyguard-Editor-TemplateApiImpl", "loadCurrentTemplateSubjectCompat: subject exists, load it from provider");
            templateBitmapByProvider$default = TemplateDataUtil.getTemplateBitmapByProvider$default(TemplateDataUtil.INSTANCE, this.context, str, 0, 0, 0, 28, null);
        }
        if (templateBitmapByProvider$default != null) {
            return templateBitmapByProvider$default;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "loadCurrentTemplateSubjectCompat: load from mask");
        if (wallpaperInfo2 != null && (mask = wallpaperInfo2.getSubject()) != null) {
            str2 = mask;
        } else if (wallpaperInfo2 != null) {
            String mask = wallpaperInfo2.getMask();
            str2 = mask;
        } else {
            str2 = null;
        }
        if (Process.INSTANCE.isKeyguardeditorProcess()) {
            if (str2 != null && str2.length() != 0 && !isGallerySetLockWallpaper()) {
                if (!MagicType.INSTANCE.isDepthVideo(wallpaperInfo2 != null ? Integer.valueOf(wallpaperInfo2.getMagicType()) : null)) {
                    TemplatePreviewImageComposer templatePreviewImageComposer = TemplatePreviewImageComposer.INSTANCE;
                    if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
                        wallpaperInfo = lockscreenInfo.getWallpaperInfo();
                    }
                    return templatePreviewImageComposer.composeSubjectImage(getCurrentWallpaperPathSafely(wallpaperInfo, TemplateFilePathGenerator.TemplateFileType.WALLPAPER), str2, !FileUtil.isFileExist(str2));
                }
            }
            Log.w("Keyguard-Editor-TemplateApiImpl", "loadCurrentTemplateSubjectCompat: third part set wallpaper and no mask");
            return null;
        }
        if (str2 != null && wallpaperInfo2 != null && (source = wallpaperInfo2.getSource()) != null) {
            TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
            Bitmap templateBitmapByProvider$default2 = TemplateDataUtil.getTemplateBitmapByProvider$default(templateDataUtil, this.context, str2, 0, 0, 0, 28, null);
            Bitmap templateBitmapByProvider$default3 = TemplateDataUtil.getTemplateBitmapByProvider$default(templateDataUtil, this.context, source, 0, 0, 0, 28, null);
            if (templateBitmapByProvider$default2 != null && templateBitmapByProvider$default3 != null) {
                Bitmap bitmapComposeSubjectImage$default = TemplatePreviewImageComposer.composeSubjectImage$default(TemplatePreviewImageComposer.INSTANCE, templateBitmapByProvider$default3, templateBitmapByProvider$default2, 0, 0, 12, null);
                Log.i("Keyguard-Editor-TemplateApiImpl", "loadCurrentTemplateSubjectCompat: load from provider bitmap = " + (bitmapComposeSubjectImage$default != null));
                return bitmapComposeSubjectImage$default;
            }
            Log.w("Keyguard-Editor-TemplateApiImpl", "loadCurrentTemplateSubjectCompat: wallpaper is " + (templateBitmapByProvider$default3 != null) + ",subjectMask is " + (templateBitmapByProvider$default2 != null));
        }
        return null;
    }

    public final Bitmap loadHistoryTemplateSubjectCompat(WallpaperInfo wallpaperInfo) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "loadHistoryTemplateSubjectCompat: wallpaperInfo=" + wallpaperInfo);
        String subject = wallpaperInfo.getSubject();
        if (subject == null) {
            return null;
        }
        String source = wallpaperInfo.getSource();
        Intrinsics.checkNotNull(source);
        if (StringsKt.endsWith$default(subject, TemplateFilePathGenerator.TemplateFileType.SUBJECT.getFileName(), false, 2, (Object) null)) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "loadHistoryTemplateSubjectCompat: load from subject");
            return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, subject, 0, 0, null, 14, null);
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "loadHistoryTemplateSubjectCompat: load from mask");
        return TemplatePreviewImageComposer.INSTANCE.composeSubjectImage(source, subject, false);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void loadTemplateWallpaper(WallpaperInfo wallpaperInfo, boolean z) {
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        wallpaperInfo.setCurrentWallpaper(TemplateApi.DefaultImpls.getLosslessTemplateBitmap$default(this, wallpaperInfo.getSource(), z, 0, 0, 12, null));
        if (Wallpaper.Companion.isSensorWallpaper(wallpaperInfo.getResourceType())) {
            SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams();
            wallpaperInfo.setCurrentSmallSensorWallpaper(TemplateApi.DefaultImpls.getLosslessTemplateBitmap$default(this, sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null, z, 0, 0, 12, null));
        }
        String source = wallpaperInfo.getSource();
        Intrinsics.checkNotNull(source);
        if (source.length() <= 0 || !z) {
            return;
        }
        TemplateFilePathGenerator.Companion companion = TemplateFilePathGenerator.Companion;
        String source2 = wallpaperInfo.getSource();
        Intrinsics.checkNotNull(source2);
        wallpaperInfo.setOriginResourcePath(companion.getPresetWallpaperAbsolutePath(source2));
    }

    public final String makeCurrentSubjectToHistoryCompat(long j) {
        TemplateFilePathGenerator templateFilePathGenerator = this.filePathGenerator;
        TemplateFilePathGenerator.TemplateFileType templateFileType = TemplateFilePathGenerator.TemplateFileType.SUBJECT;
        String strCopyFile$default = copyFile$default(this, templateFilePathGenerator.generateCurrentTemplateFilePath(templateFileType), this.filePathGenerator.generateHistoryTemplateFilePath(j, templateFileType), false, 0L, false, false, 60, null);
        TemplateFilePathGenerator templateFilePathGenerator2 = this.filePathGenerator;
        TemplateFilePathGenerator.TemplateFileType templateFileType2 = TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK;
        return strCopyFile$default == null ? copyFile$default(this, templateFilePathGenerator2.generateCurrentTemplateFilePath(templateFileType2), this.filePathGenerator.generateHistoryTemplateFilePath(j, templateFileType2), false, 0L, false, false, 60, null) : strCopyFile$default;
    }

    public final void makeCurrentTemplateToHistory(long j) {
        String str;
        String str2;
        String str3;
        Wallpaper.Companion companion;
        String str4;
        try {
            KeyguardEditorMiSightUtil.INSTANCE.step2Start();
        } catch (Exception unused) {
        }
        CommonConfig currentTemplateInternal$default = getCurrentTemplateInternal$default(this, false, false, true, false, null, false, null, com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, null);
        try {
            if (currentTemplateInternal$default == null) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "makeCurrentTemplateToHistory: currentConfig == null");
                KeyguardEditorMiSightUtil.step2EndFail$default(KeyguardEditorMiSightUtil.INSTANCE, "currentConfig is null", null, 2, null);
            } else {
                WallpaperInfo wallpaperInfo = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfo();
                WallpaperInfo wallpaperInfo2 = currentTemplateInternal$default.getHomeInfo().getWallpaperInfo();
                TemplateFilePathGenerator.TemplateFileType templateFileType = TemplateFilePathGenerator.TemplateFileType.SMALL_SCREENSHOT;
                String currentWallpaperPathSafely = getCurrentWallpaperPathSafely(wallpaperInfo, templateFileType);
                TemplateFilePathGenerator.TemplateFileType templateFileType2 = TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_SCREENSHOT;
                String currentWallpaperPathSafely2 = getCurrentWallpaperPathSafely(wallpaperInfo, templateFileType2);
                TemplateFilePathGenerator.TemplateFileType templateFileType3 = TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_SCREENSHOT;
                String currentWallpaperPathSafely3 = getCurrentWallpaperPathSafely(wallpaperInfo, templateFileType3);
                if (isScreenshotFilesExist(currentWallpaperPathSafely, currentWallpaperPathSafely2, currentWallpaperPathSafely3)) {
                    Wallpaper.Companion companion2 = Wallpaper.Companion;
                    if (companion2.unSupportToHistory(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null)) {
                        Log.w("Keyguard-Editor-TemplateApiImpl", "makeCurrentTemplateToHistory: wallpaper not support to history, delete !lock = " + (wallpaperInfo != null ? wallpaperInfo.getResourceType() : null) + ", home = " + (wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null));
                        try {
                            if (TemplateSource.INSTANCE.isFromHistoryTemplate(j)) {
                                this.templateDao.delete(j);
                            }
                            Unit unit = Unit.INSTANCE;
                        } catch (Exception e) {
                            Log.w("Keyguard-Editor-TemplateApiImpl", "makeCurrentTemplateToHistory: ", e);
                        }
                        KeyguardEditorMiSightUtil.step2EndFail$default(KeyguardEditorMiSightUtil.INSTANCE, "wallpaper not support to history", null, 2, null);
                    } else {
                        if (FileUtil.isFileExist(this.filePathGenerator.generateCurrentTemplateFolder())) {
                            final long jCurrentTimeMillis = System.currentTimeMillis();
                            TemplateFilePathGenerator templateFilePathGenerator = this.filePathGenerator;
                            TemplateFilePathGenerator.TemplateFileType templateFileType4 = TemplateFilePathGenerator.TemplateFileType.SMALL_WALLPAPER_SCREENSHOT;
                            String strGenerateCurrentTemplateFilePath = templateFilePathGenerator.generateCurrentTemplateFilePath(templateFileType4);
                            TemplateFilePathGenerator templateFilePathGenerator2 = this.filePathGenerator;
                            TemplateFilePathGenerator.TemplateFileType templateFileType5 = TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_WALLPAPER_SCREENSHOT;
                            String strGenerateCurrentTemplateFilePath2 = templateFilePathGenerator2.generateCurrentTemplateFilePath(templateFileType5);
                            TemplateFilePathGenerator templateFilePathGenerator3 = this.filePathGenerator;
                            TemplateFilePathGenerator.TemplateFileType templateFileType6 = TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT;
                            String strGenerateCurrentTemplateFilePath3 = templateFilePathGenerator3.generateCurrentTemplateFilePath(templateFileType6);
                            WallpaperInfo wallpaperInfo3 = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfo();
                            TemplateFilePathGenerator.TemplateFileType templateFileType7 = TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
                            String currentWallpaperPathSafely4 = getCurrentWallpaperPathSafely(wallpaperInfo3, templateFileType7);
                            WallpaperInfo wallpaperInfo4 = currentTemplateInternal$default.getHomeInfo().getWallpaperInfo();
                            TemplateFilePathGenerator.TemplateFileType templateFileType8 = TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME;
                            String currentWallpaperPathSafely5 = getCurrentWallpaperPathSafely(wallpaperInfo4, templateFileType8);
                            WallpaperInfo wallpaperInfo5 = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfo();
                            TemplateFilePathGenerator.TemplateFileType templateFileType9 = TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT;
                            String currentWallpaperPathSafely6 = getCurrentWallpaperPathSafely(wallpaperInfo5, templateFileType9);
                            WallpaperInfo wallpaperInfo6 = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfo();
                            TemplateFilePathGenerator.TemplateFileType templateFileType10 = TemplateFilePathGenerator.TemplateFileType.FRONT_BACK;
                            String currentWallpaperPathSafely7 = getCurrentWallpaperPathSafely(wallpaperInfo6, templateFileType10);
                            WallpaperInfo wallpaperInfo7 = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfo();
                            TemplateFilePathGenerator.TemplateFileType templateFileType11 = TemplateFilePathGenerator.TemplateFileType.CROPPED_FRONT_BACK;
                            String currentWallpaperPathSafely8 = getCurrentWallpaperPathSafely(wallpaperInfo7, templateFileType11);
                            TemplateFilePathGenerator templateFilePathGenerator4 = this.filePathGenerator;
                            TemplateFilePathGenerator.TemplateFileType templateFileType12 = TemplateFilePathGenerator.TemplateFileType.DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK;
                            String strGenerateDepthVideoWallpaperFilePath = templateFilePathGenerator4.generateDepthVideoWallpaperFilePath(templateFileType12);
                            String strCopyFile$default = copyFile$default(this, currentWallpaperPathSafely, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType), false, 1000L, true, false, 36, null);
                            String strCopyFile$default2 = copyFile$default(this, currentWallpaperPathSafely2, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType2), false, 1000L, true, false, 36, null);
                            String strCopyFile$default3 = copyFile$default(this, currentWallpaperPathSafely3, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType3), false, 1000L, true, false, 36, null);
                            String strCopyFile$default4 = copyFile$default(this, strGenerateCurrentTemplateFilePath, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType4), false, 1000L, true, false, 36, null);
                            String strCopyFile$default5 = copyFile$default(this, strGenerateCurrentTemplateFilePath2, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType5), false, 1000L, true, false, 36, null);
                            String strCopyFile$default6 = copyFile$default(this, strGenerateCurrentTemplateFilePath3, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType6), false, 1000L, true, false, 36, null);
                            final String strCopyFile$default7 = copyFile$default(this, currentWallpaperPathSafely4, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType7), false, 1000L, true, false, 36, null);
                            final String strCopyFile$default8 = copyFile$default(this, currentWallpaperPathSafely5, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType8), false, 1000L, true, false, 36, null);
                            String strMakeCurrentSubjectToHistoryCompat = makeCurrentSubjectToHistoryCompat(jCurrentTimeMillis);
                            String strCopyFile$default9 = copyFile$default(this, currentWallpaperPathSafely6, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType9), false, 1000L, true, false, 36, null);
                            String strCopyFile$default10 = copyFile$default(this, currentWallpaperPathSafely7, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType10), false, 1000L, true, false, 36, null);
                            String strCopyFile$default11 = copyFile$default(this, currentWallpaperPathSafely8, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType11), false, 1000L, true, false, 36, null);
                            String strCopyFile$default12 = copyFile$default(this, strGenerateDepthVideoWallpaperFilePath, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, templateFileType12), false, 1000L, true, false, 36, null);
                            WallpaperInfo wallpaperInfoForVideo = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfoForVideo();
                            if (companion2.isLivePhotoResource(wallpaperInfoForVideo != null ? Integer.valueOf(wallpaperInfoForVideo.getVideoType()) : null)) {
                                WallpaperInfo wallpaperInfoForVideo2 = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfoForVideo();
                                str2 = strMakeCurrentSubjectToHistoryCompat;
                                str = strCopyFile$default12;
                                companion = companion2;
                                String strCopyFile$default13 = copyFile$default(this, wallpaperInfoForVideo2 != null ? wallpaperInfoForVideo2.getOriginResourcePath() : null, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER), false, 1000L, true, false, 36, null);
                                WallpaperInfo wallpaperInfoForVideo3 = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfoForVideo();
                                if (wallpaperInfoForVideo3 != null) {
                                    wallpaperInfoForVideo3.setOriginResourcePath(strCopyFile$default13);
                                }
                                if (strCopyFile$default13 == null) {
                                    str3 = "Keyguard-Editor-TemplateApiImpl";
                                    Log.d(str3, "makeCurrentTemplateToHistory: save lock livePhoto video failed");
                                } else {
                                    str3 = "Keyguard-Editor-TemplateApiImpl";
                                }
                            } else {
                                str = strCopyFile$default12;
                                str2 = strMakeCurrentSubjectToHistoryCompat;
                                str3 = "Keyguard-Editor-TemplateApiImpl";
                                companion = companion2;
                            }
                            WallpaperInfo wallpaperInfo8 = currentTemplateInternal$default.getHomeInfo().getWallpaperInfo();
                            if (companion.isLivePhotoResource(wallpaperInfo8 != null ? Integer.valueOf(wallpaperInfo8.getVideoType()) : null)) {
                                WallpaperInfo wallpaperInfoForVideo4 = currentTemplateInternal$default.getHomeInfo().getWallpaperInfoForVideo();
                                str4 = str3;
                                String strCopyFile$default14 = copyFile$default(this, wallpaperInfoForVideo4 != null ? wallpaperInfoForVideo4.getOriginResourcePath() : null, this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME), false, 1000L, true, false, 36, null);
                                WallpaperInfo wallpaperInfoForVideo5 = currentTemplateInternal$default.getHomeInfo().getWallpaperInfoForVideo();
                                if (wallpaperInfoForVideo5 != null) {
                                    wallpaperInfoForVideo5.setOriginResourcePath(strCopyFile$default14);
                                }
                                if (strCopyFile$default14 == null) {
                                    Log.d(str4, "makeCurrentTemplateToHistory: save home livePhoto video failed");
                                }
                            } else {
                                str4 = str3;
                            }
                            Log.i(str4, "makeCurrentTemplateToHistory: wallpaperInfo=" + wallpaperInfo);
                            Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda17
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return TemplateApiImpl.makeCurrentTemplateToHistory$lambda$111(this.f$0, jCurrentTimeMillis, strCopyFile$default7, strCopyFile$default8, (WallpaperInfo) obj);
                                }
                            };
                            Intrinsics.checkNotNull(wallpaperInfo);
                            String str5 = (String) function1.invoke(wallpaperInfo);
                            Intrinsics.checkNotNull(wallpaperInfo2);
                            boolean zIsScreenshotCopyErrorHistory = isScreenshotCopyErrorHistory(strCopyFile$default, strCopyFile$default2, strCopyFile$default3);
                            if (zIsScreenshotCopyErrorHistory || strCopyFile$default7 == null || strCopyFile$default8 == null) {
                                Log.w(str4, "applyTemplate: copy image error ! smallScreenshotActually=" + strCopyFile$default + ", largePortraitScreenshotActually = " + strCopyFile$default2 + ", largeLandscapeScreenshotActually = " + strCopyFile$default3 + ", lockWallpaperPathActually=" + strCopyFile$default7 + ", homeWallpaperPathActually=" + strCopyFile$default8 + ", cropSubjectPathActually=" + strCopyFile$default9 + ", isScreenshotCopyError=" + zIsScreenshotCopyErrorHistory);
                                throw new NullPointerException("screenshotPathActually or wallpaperPathActually is null !");
                            }
                            deleteCurrentTemplateFiles();
                            wallpaperInfo.setSource(strCopyFile$default7);
                            String str6 = str2;
                            wallpaperInfo.setSubject(str6);
                            wallpaperInfo.setCropSubject(strCopyFile$default9);
                            wallpaperInfo.setFrontBack(strCopyFile$default10);
                            wallpaperInfo.setCroppedFrontBack(strCopyFile$default11);
                            wallpaperInfo.setDepthVideoScreenShot(str);
                            wallpaperInfo2.setSource(strCopyFile$default8);
                            Log.i(str4, "applyTemplate: subjectPathActually=" + str6);
                            ExtraInfo extraInfo = wallpaperInfo.getExtraInfo();
                            if (extraInfo != null) {
                                extraInfo.setLoopType(null);
                            }
                            ExtraInfo extraInfo2 = wallpaperInfo2.getExtraInfo();
                            if (extraInfo2 != null) {
                                extraInfo2.setLoopType(null);
                            }
                            String strGenerateHistoryTemplateFilePath = this.filePathGenerator.generateHistoryTemplateFilePath(jCurrentTimeMillis, TemplateFilePathGenerator.TemplateFileType.CONFIG);
                            if (!saveTemplateConfig(currentTemplateInternal$default, strGenerateHistoryTemplateFilePath)) {
                                throw new RuntimeException("save current config error !");
                            }
                            String resourceType = wallpaperInfo.getResourceType();
                            String resourceType2 = wallpaperInfo.getResourceType();
                            String resourceType3 = wallpaperInfo2.getResourceType();
                            String source = wallpaperInfo.getSource();
                            String originResourcePath = wallpaperInfo.getOriginResourcePath();
                            String source2 = wallpaperInfo2.getSource();
                            String originResourcePath2 = wallpaperInfo2.getOriginResourcePath();
                            SensorWallpaperPrams sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams();
                            String outerVideoPath = sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null;
                            SensorWallpaperPrams sensorWallpaperPrams2 = wallpaperInfo.getSensorWallpaperPrams();
                            String outerVideoPreViewPath = sensorWallpaperPrams2 != null ? sensorWallpaperPrams2.getOuterVideoPreViewPath() : null;
                            SensorWallpaperPrams sensorWallpaperPrams3 = wallpaperInfo2.getSensorWallpaperPrams();
                            String outerVideoPath2 = sensorWallpaperPrams3 != null ? sensorWallpaperPrams3.getOuterVideoPath() : null;
                            SensorWallpaperPrams sensorWallpaperPrams4 = wallpaperInfo2.getSensorWallpaperPrams();
                            String outerVideoPreViewPath2 = sensorWallpaperPrams4 != null ? sensorWallpaperPrams4.getOuterVideoPreViewPath() : null;
                            StringBuilder sb = new StringBuilder();
                            String str7 = str4;
                            sb.append("makeCurrentTemplateToHistory save config: timestamp=");
                            sb.append(jCurrentTimeMillis);
                            sb.append(", configPath=");
                            sb.append(strGenerateHistoryTemplateFilePath);
                            sb.append(", historyResourceType=");
                            sb.append(resourceType);
                            sb.append(", lockType=");
                            sb.append(resourceType2);
                            sb.append(", homeType=");
                            sb.append(resourceType3);
                            sb.append(", lockSource=");
                            sb.append(source);
                            sb.append(", lockOrigin=");
                            sb.append(originResourcePath);
                            sb.append(", homeSource=");
                            sb.append(source2);
                            sb.append(", homeOrigin=");
                            sb.append(originResourcePath2);
                            sb.append(", lockOuterVideoPath=");
                            sb.append(outerVideoPath);
                            sb.append(", lockOuterVideoPreViewPath=");
                            sb.append(outerVideoPreViewPath);
                            sb.append(", homeOuterVideoPath=");
                            sb.append(outerVideoPath2);
                            sb.append(", homeOuterVideoPreViewPath=");
                            sb.append(outerVideoPreViewPath2);
                            Log.i(str7, sb.toString());
                            Log.i(str7, "applyTemplate: smallScreenshotUri=" + strCopyFile$default + ", largePortraitScreenshotUri = " + strCopyFile$default2 + ", largeLandscapeScreenshotUri = " + strCopyFile$default3 + ", wallpaperUri=" + strCopyFile$default7 + ",subjectUri=" + str6 + ",configUri=" + strGenerateHistoryTemplateFilePath);
                            boolean zIsDualClock = isDualClock();
                            this.currentTemplateDualClock = zIsDualClock;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("makeCurrentTemplateToHistory: currentTemplateDualClock=");
                            sb2.append(zIsDualClock);
                            sb2.append(" lockWallpaperInfo=");
                            sb2.append(wallpaperInfo);
                            sb2.append(" homeWallpaperInfo=");
                            sb2.append(wallpaperInfo2);
                            Log.i(str7, sb2.toString());
                            this.isDarkWallpaper = CommonUtils.INSTANCE.isDarkWallpaper(getCurrentWallpaper(currentTemplateInternal$default.getHomeInfo().getWallpaperInfo()));
                            String templateId = currentTemplateInternal$default.getLockscreenInfo().getClockInfo().getTemplateId();
                            int i = this.currentTemplateLoadingColor;
                            boolean z = this.currentTemplateDualClock;
                            boolean zIsThirdPartyWallpaper$default = TemplateApi.DefaultImpls.isThirdPartyWallpaper$default(this, null, 1, null);
                            String resourceType4 = wallpaperInfo.getResourceType();
                            if (resourceType4 == null) {
                                resourceType4 = "image";
                            }
                            TemplateHistoryConfig templateHistoryConfig = new TemplateHistoryConfig(templateId, strGenerateHistoryTemplateFilePath, i, strCopyFile$default, strCopyFile$default2, strCopyFile$default3, strCopyFile$default4, strCopyFile$default5, strCopyFile$default6, z, zIsThirdPartyWallpaper$default, resourceType4, str5, jCurrentTimeMillis, currentTemplateInternal$default.getLockscreenInfo().getTemplateName(), 0, currentTemplateInternal$default.getLockscreenInfo().getHideFlag(), wallpaperInfo.getMagicType(), this.isDarkWallpaper ? 1 : 0, wallpaperInfo.isAiGenerated());
                            if (TemplateSource.INSTANCE.isFromHistoryTemplate(j)) {
                                templateHistoryConfig.setId(j);
                            }
                            int hideFlag = templateHistoryConfig.getHideFlag();
                            String resourceType5 = wallpaperInfo.getResourceType();
                            String originResourcePath3 = wallpaperInfo.getOriginResourcePath();
                            WallpaperInfo wallpaperInfo9 = currentTemplateInternal$default.getLockscreenInfo().getWallpaperInfo();
                            String originResourcePath4 = wallpaperInfo9 != null ? wallpaperInfo9.getOriginResourcePath() : null;
                            WallpaperController.Companion companion3 = WallpaperController.Companion;
                            Log.i(str7, "makeCurrentTemplateToHistory: hideFlag=" + hideFlag + ", resourceType=" + resourceType5 + ", originResourcePath=" + originResourcePath3 + ", originLockWallpaperPath=" + str5 + ", configOriginPath=" + originResourcePath4 + ", shouldApplyOuterVideoWallpaper=" + companion3.shouldApplyOuterVideoWallpaper(wallpaperInfo, new String[0]) + ", isMiuiDefault=" + companion3.getInstance(this.context).isMiuiDefaultWallpaper());
                            try {
                                KeyguardEditorMiSightUtil.INSTANCE.step2EndSuccess();
                            } catch (Exception unused2) {
                            }
                            try {
                                KeyguardEditorMiSightUtil.INSTANCE.step3Start();
                            } catch (Exception unused3) {
                            }
                            insertHistoryConfig(templateHistoryConfig);
                            return;
                        }
                        Log.w("Keyguard-Editor-TemplateApiImpl", "makeCurrentTemplateToHistory current not exist,not need save to history");
                        KeyguardEditorMiSightUtil.step2EndFail$default(KeyguardEditorMiSightUtil.INSTANCE, "current folder not exist", null, 2, null);
                    }
                } else {
                    Log.e("Keyguard-Editor-TemplateApiImpl", "makeCurrentTemplateToHistory: screenshot files not exist");
                    KeyguardEditorMiSightUtil.step2EndFail$default(KeyguardEditorMiSightUtil.INSTANCE, "screenshot files not exist", null, 2, null);
                }
            }
        } catch (Exception unused4) {
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001f  */
    public final Object migrateHistoryTemplatesClockForOS4(Continuation continuation) throws Throwable {
        C00901 c00901;
        Mutex mutex;
        TemplateApiImpl templateApiImpl;
        boolean z;
        Object obj;
        SharedPreferences sharedPreferences;
        boolean z2;
        String str;
        if (continuation instanceof C00901) {
            c00901 = (C00901) continuation;
            int i = c00901.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c00901.label = i - Integer.MIN_VALUE;
            } else {
                c00901 = new C00901(continuation);
            }
        } else {
            c00901 = new C00901(continuation);
        }
        Object obj2 = c00901.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c00901.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            mutex = this.otaMigrationLock;
            c00901.L$0 = this;
            c00901.L$1 = mutex;
            c00901.label = 1;
            if (mutex.lock(null, c00901) == coroutine_suspended) {
                return coroutine_suspended;
            }
            templateApiImpl = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Mutex mutex2 = (Mutex) c00901.L$1;
            templateApiImpl = (TemplateApiImpl) c00901.L$0;
            ResultKt.throwOnFailure(obj2);
            mutex = mutex2;
        }
        try {
            try {
                SharedPreferences sharedPreferences2 = templateApiImpl.context.getSharedPreferences("editor_migration", 0);
                try {
                    try {
                        try {
                            if (sharedPreferences2.getBoolean("os4_history_clock_migrated", false)) {
                                Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: already migrated, skip");
                            } else {
                                Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: migration start");
                                ArrayList arrayList = new ArrayList();
                                ArrayList arrayList2 = new ArrayList();
                                List listQueryAll = templateApiImpl.templateDao.queryAll();
                                if (!listQueryAll.isEmpty()) {
                                    Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: rawTemplates size=" + listQueryAll.size());
                                    Set allHistoryDirs = templateApiImpl.getAllHistoryDirs();
                                    ArrayList<TemplateHistoryConfig> arrayList3 = new ArrayList();
                                    for (Object obj3 : listQueryAll) {
                                        TemplateHistoryConfig templateHistoryConfig = (TemplateHistoryConfig) obj3;
                                        if (allHistoryDirs == null || allHistoryDirs.contains(String.valueOf(templateHistoryConfig.getLastModifiedTime()))) {
                                            sharedPreferences = sharedPreferences2;
                                        } else {
                                            sharedPreferences = sharedPreferences2;
                                            if (templateHistoryConfig.isCurrent() != 1) {
                                                try {
                                                    listQueryAll = listQueryAll;
                                                    Log.w("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: history folder not exist, cleaning orphan record for templateId=" + templateHistoryConfig.getTemplateId() + ", lastModifiedTime=" + templateHistoryConfig.getLastModifiedTime());
                                                    templateApiImpl.templateDao.delete(templateHistoryConfig);
                                                } catch (Exception e) {
                                                    e = e;
                                                    Log.e("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: batch migration failed, marking as migrated to avoid retry loop", e);
                                                    sharedPreferences.edit().putBoolean("os4_history_clock_migrated", true).apply();
                                                    z2 = false;
                                                    templateApiImpl.otaMigrationInProgress = z2;
                                                    Unit unit = Unit.INSTANCE;
                                                    mutex.unlock(null);
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            sharedPreferences2 = sharedPreferences;
                                            listQueryAll = listQueryAll;
                                            allHistoryDirs = allHistoryDirs;
                                        }
                                        arrayList3.add(obj3);
                                        sharedPreferences2 = sharedPreferences;
                                        listQueryAll = listQueryAll;
                                        allHistoryDirs = allHistoryDirs;
                                    }
                                    List list = listQueryAll;
                                    SharedPreferences sharedPreferences3 = sharedPreferences2;
                                    if (arrayList3.isEmpty()) {
                                        sharedPreferences3.edit().putBoolean("os4_history_clock_migrated", true).apply();
                                        Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: no valid history templates after filtering, skip");
                                        z2 = false;
                                    } else {
                                        Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: starting migration for " + arrayList3.size() + " valid templates (filtered " + (list.size() - arrayList3.size()) + " orphans)");
                                        for (TemplateHistoryConfig templateHistoryConfig2 : arrayList3) {
                                            String configUri = templateHistoryConfig2.getConfigUri();
                                            String templateId = templateHistoryConfig2.getTemplateId();
                                            if (StringsKt.isBlank(configUri) || Intrinsics.areEqual(configUri, "UNDEFINE")) {
                                                Log.w("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: skip invalid configUri for templateId=" + templateId);
                                                arrayList2.add(templateId + ": invalid configUri");
                                            } else if (FileUtil.isFileExist(configUri)) {
                                                try {
                                                    FileReader fileReader = new FileReader(configUri);
                                                    try {
                                                        String fileFromReader = FileUtil.readFileFromReader(fileReader);
                                                        CloseableKt.closeFinally(fileReader, null);
                                                        CommonConfig.Companion companion = CommonConfig.Companion;
                                                        Intrinsics.checkNotNull(fileFromReader);
                                                        CommonConfig commonConfigFromJson$default = CommonConfig.Companion.fromJson$default(companion, fileFromReader, null, templateApiImpl.context, 2, null);
                                                        File file = new File(configUri);
                                                        File file2 = new File(file.getParentFile(), file.getName() + ".backup");
                                                        FilesKt.copyTo$default(file, file2, true, 0, 4, null);
                                                        if (FileUtil.atomicWriteToFile(configUri, GsonSingletonKt.getGSON_SINGLETON().toJson(commonConfigFromJson$default))) {
                                                            try {
                                                                Result.Companion companion2 = Result.Companion;
                                                                Result.m1061constructorimpl(Boxing.boxBoolean(file2.delete()));
                                                            } catch (Throwable th) {
                                                                Result.Companion companion3 = Result.Companion;
                                                                Result.m1061constructorimpl(ResultKt.createFailure(th));
                                                            }
                                                            arrayList.add(templateId);
                                                            String templateId2 = commonConfigFromJson$default.getLockscreenInfo().getClockInfo().getTemplateId();
                                                            if (Intrinsics.areEqual(templateId2, templateId)) {
                                                                str = templateId2;
                                                            } else {
                                                                TemplateHistoryConfig templateHistoryConfigCopy$default = TemplateHistoryConfig.copy$default(templateHistoryConfig2, templateId2, null, 0, null, null, null, null, null, null, false, false, null, null, 0L, null, 0, 0, 0, 0, false, 1048574, null);
                                                                str = templateId2;
                                                                templateHistoryConfigCopy$default.setId(templateHistoryConfig2.getId());
                                                                templateApiImpl.templateDao.addOrReplace(templateHistoryConfigCopy$default);
                                                            }
                                                            Boxing.boxInt(Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: successfully migrated " + templateId + " to " + str + ", version=" + commonConfigFromJson$default.getVersion() + ", clockStyle=" + commonConfigFromJson$default.getLockscreenInfo().getClockInfo().getStyle()));
                                                        } else {
                                                            Log.e("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: atomic write failed, restoring backup for templateId=" + templateId);
                                                            FilesKt.copyTo$default(file2, new File(configUri), true, 0, 4, null);
                                                            try {
                                                                Result.Companion companion4 = Result.Companion;
                                                                Result.m1061constructorimpl(Boxing.boxBoolean(file2.delete()));
                                                            } catch (Throwable th2) {
                                                                Result.Companion companion5 = Result.Companion;
                                                                Result.m1061constructorimpl(ResultKt.createFailure(th2));
                                                            }
                                                            Boxing.boxBoolean(arrayList2.add(templateId + ": write failed"));
                                                        }
                                                    } catch (Throwable th3) {
                                                        try {
                                                            throw th3;
                                                        } catch (Throwable th4) {
                                                            CloseableKt.closeFinally(fileReader, th3);
                                                            throw th4;
                                                        }
                                                    }
                                                } catch (Exception e2) {
                                                    Log.e("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: failed for templateId=" + templateId, e2);
                                                    arrayList2.add(templateId + ": " + e2.getClass().getSimpleName() + ": " + e2.getMessage());
                                                    try {
                                                        Result.Companion companion6 = Result.Companion;
                                                        Result.m1061constructorimpl(Boxing.boxBoolean(new File(configUri + ".backup").delete()));
                                                    } catch (Throwable th5) {
                                                        Result.Companion companion7 = Result.Companion;
                                                        Result.m1061constructorimpl(ResultKt.createFailure(th5));
                                                    }
                                                }
                                            } else {
                                                Log.w("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: config file not exist, templateId=" + templateId + ", path=" + configUri);
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(templateId);
                                                sb.append(": file not found");
                                                arrayList2.add(sb.toString());
                                            }
                                        }
                                        sharedPreferences3.edit().putBoolean("os4_history_clock_migrated", true).apply();
                                        Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: migration completed (success: " + arrayList.size() + ", failure: " + arrayList2.size() + ")");
                                        z2 = false;
                                    }
                                    templateApiImpl.otaMigrationInProgress = z2;
                                    Unit unit2 = Unit.INSTANCE;
                                    mutex.unlock(null);
                                    return Unit.INSTANCE;
                                }
                                sharedPreferences2.edit().putBoolean("os4_history_clock_migrated", true).apply();
                                Log.d("Keyguard-Editor-TemplateApiImpl", "migrateHistoryTemplatesClockForOS4: no history templates, skip");
                            }
                            templateApiImpl.otaMigrationInProgress = false;
                        } catch (Throwable th6) {
                            th = th6;
                            obj = null;
                            mutex.unlock(obj);
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        z = false;
                        templateApiImpl.otaMigrationInProgress = z;
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    sharedPreferences = sharedPreferences2;
                }
                Unit unit3 = Unit.INSTANCE;
                mutex.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th8) {
                th = th8;
                obj = null;
            }
        } catch (Throwable th9) {
            th = th9;
            z = false;
        }
    }

    public final boolean needApplyAll(Bitmap bitmap, CommonConfig commonConfig) {
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
        if (bitmap != null) {
            return false;
        }
        if (Wallpaper.Companion.isSpecialWallpaperType(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null) || commonConfig.getHomeInfo().getFollowStatus() != 2) {
            return false;
        }
        return Intrinsics.areEqual(wallpaperInfo2 != null ? Integer.valueOf(wallpaperInfo2.getMagicType()) : null, wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getMagicType()) : null);
    }

    public final boolean needUpdateConfig(CommonConfig commonConfig, String str) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "needUpdateConfig: " + str + " lockWallpaperInfo=" + commonConfig.getLockscreenInfo().getWallpaperInfo() + " homeWallpaperInfo=" + commonConfig.getHomeInfo().getWallpaperInfo());
        if (commonConfig.getLockscreenInfo().getWallpaperInfo() == null || isDefaultConfig(commonConfig)) {
            return true;
        }
        if (isLockSetPkgSupportDepth() || Wallpaper.Companion.isSuperSavePowerWallpaper(str)) {
            return DeviceUtil.INSTANCE.isFold() && Intrinsics.areEqual("com.android.thememanager", getLockWallpaperSetPkg()) && Intrinsics.areEqual("sensor", str);
        }
        return true;
    }

    public final boolean needUpdateHome(CommonConfig commonConfig, String str) {
        if (commonConfig.getHomeInfo().getWallpaperInfo() == null || isDefaultConfig(commonConfig)) {
            return true;
        }
        if (isHomeSetPkgSupportDepth() || Wallpaper.Companion.isSuperSavePowerWallpaper(str)) {
            return DeviceUtil.INSTANCE.isFold() && Intrinsics.areEqual("com.android.thememanager", getHomeWallpaperSetPkg()) && Intrinsics.areEqual("sensor", str);
        }
        return true;
    }

    public final void notifyAppsOnApplyStateChanged() {
        for (String str : ConstantsKt.getNOTIFY_PACKAGES_ON_APPLY()) {
            Intent intent = new Intent("miui.keyguard.editor.action.LINKAGE_STYLE_APPLY_STATE_CHANGED");
            intent.setPackage(str);
            this.context.sendBroadcast(intent);
        }
    }

    public final void notifyGalleryOnApply(boolean z, String str, Integer num) {
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return;
        }
        FashionGalleryHelper.addWallpaperFromEditorAsync$default(FashionGalleryHelper.INSTANCE, this.context, str, null, null, false, null, z, num, 12, null);
    }

    public final void pickWallpaperLoadingColor(Bitmap bitmap, WallpaperWhich wallpaperWhich) {
        try {
            if (bitmap == null) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "pickWallpaperLoadingColor: wallpaper == null");
                return;
            }
            Map dynamicColor = WallpaperController.Companion.getInstance(this.context).getDynamicColor(this.context, bitmap, null, null, -1, -1, null, wallpaperWhich);
            Object obj = dynamicColor != null ? dynamicColor.get("secondary70") : null;
            Integer num = obj instanceof Integer ? (Integer) obj : null;
            if (num != null) {
                this.currentTemplateLoadingColor = num.intValue();
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "pickWallpaperSolidColor: " + num);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "pickWallpaperSolidColor: ", e);
        }
    }

    public final void processDepthVideo(String str, WallpaperTypeInfo wallpaperTypeInfo, CommonConfig templateConfig) {
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        WallpaperInfo wallpaperInfo3;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        if (wallpaperTypeInfo == null) {
            return;
        }
        boolean zIsDepthVideo = WallpaperEditor.Companion.isDepthVideo(wallpaperTypeInfo);
        boolean zAreEqual = Intrinsics.areEqual(templateConfig.getLockscreenInfo().getClockInfo().getTemplateId(), "rhombus");
        if (str != null) {
            int iHashCode = str.hashCode();
            if (iHashCode != 96673) {
                if (iHashCode != 3208415) {
                    if (iHashCode == 3327275 && str.equals("lock") && (wallpaperInfo3 = templateConfig.getLockscreenInfo().getWallpaperInfo()) != null) {
                        wallpaperInfo3.setSupportFullScreenAod(wallpaperTypeInfo.isSupportFullScreenAod());
                    }
                } else if (str.equals("home") && (wallpaperInfo2 = templateConfig.getHomeInfo().getWallpaperInfo()) != null) {
                    wallpaperInfo2.setSupportFullScreenAod(wallpaperTypeInfo.isSupportFullScreenAod());
                }
            } else if (str.equals("all")) {
                WallpaperInfo wallpaperInfo4 = templateConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo4 != null) {
                    wallpaperInfo4.setSupportFullScreenAod(wallpaperTypeInfo.isSupportFullScreenAod());
                }
                WallpaperInfo wallpaperInfo5 = templateConfig.getHomeInfo().getWallpaperInfo();
                if (wallpaperInfo5 != null) {
                    wallpaperInfo5.setSupportFullScreenAod(wallpaperTypeInfo.isSupportFullScreenAod());
                }
            }
        }
        if (!zIsDepthVideo) {
            if (Intrinsics.areEqual(str, "home")) {
                WallpaperInfo wallpaperInfo6 = templateConfig.getHomeInfo().getWallpaperInfo();
                if (wallpaperInfo6 != null) {
                    wallpaperInfo6.setDepthVideo((String) null);
                }
                WallpaperInfo wallpaperInfo7 = templateConfig.getHomeInfo().getWallpaperInfo();
                if (wallpaperInfo7 != null) {
                    wallpaperInfo7.setDepthVideo(false);
                    return;
                }
                return;
            }
            WallpaperInfo wallpaperInfo8 = templateConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo8 != null) {
                wallpaperInfo8.setDepthVideo((String) null);
            }
            WallpaperInfo wallpaperInfo9 = templateConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo9 != null) {
                wallpaperInfo9.setDepthVideo(false);
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(str, "home")) {
            WallpaperInfo wallpaperInfo10 = templateConfig.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo10 != null) {
                wallpaperInfo10.setDepthVideo(wallpaperTypeInfo.getResourcePath());
            }
            WallpaperInfo wallpaperInfo11 = templateConfig.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo11 != null) {
                wallpaperInfo11.setDepthVideo(true);
                return;
            }
            return;
        }
        if (!zAreEqual && (wallpaperInfo = templateConfig.getLockscreenInfo().getWallpaperInfo()) != null) {
            wallpaperInfo.updateMagicType(MagicType.TYPE_DEPTH_VIDEO);
        }
        WallpaperInfo wallpaperInfo12 = templateConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo12 != null) {
            wallpaperInfo12.setDepthVideo(wallpaperTypeInfo.getResourcePath());
        }
        WallpaperInfo wallpaperInfo13 = templateConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo13 != null) {
            wallpaperInfo13.setDepthVideo(true);
        }
        int clockEffect = templateConfig.getLockscreenInfo().getClockInfo().getClockEffect();
        if (clockEffect == 2 || clockEffect == 5) {
            templateConfig.getLockscreenInfo().getClockInfo().setClockEffect(1);
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean putPreviewCache(String cacheName, Bitmap preview) {
        Intrinsics.checkNotNullParameter(cacheName, "cacheName");
        Intrinsics.checkNotNullParameter(preview, "preview");
        String cacheDirPath = this.filePathGenerator.getCacheDirPath();
        if (!FileUtil.isFileExist(cacheDirPath)) {
            FileUtil.makeDirExists(cacheDirPath);
        }
        return BitmapUtil.INSTANCE.toJPEG(preview, this.filePathGenerator.getCacheDirPath() + File.separator + cacheName);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public String readTemplateInfo() {
        String str = configJsonForFlush;
        return str == null ? Settings.Secure.getString(this.context.getContentResolver(), "constant_template_editor_info") : str;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void reapplyCurrentWallpaper(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, PickWallpaperColorInfo pickWallpaperColorInfo) {
        Intrinsics.checkNotNullParameter(pickWallpaperColorInfo, "pickWallpaperColorInfo");
        Log.i("Keyguard-Editor-TemplateApiImpl", "applyWallpaperInThirdPartyTheme");
        if (wallpaperInfo == null) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "applyWallpaperInThirdPartyTheme: wallpaperInfo is null");
            return;
        }
        if (wallpaperInfo2 == null) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "applyWallpaperInThirdPartyTheme: homeWallpaperInfo is null");
            return;
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        companion.updateApplyStatusClone(1);
        WallpaperChangedListener wallpaperChangedListenerRegister = WallpaperChangedListener.Companion.register(this.context, wallpaperInfo.getMagicType(), wallpaperInfo.getResourceType());
        WallpaperApplyParam.Companion companion2 = WallpaperApplyParam.Companion;
        WallpaperApplyParam wallpaperApplyParamDefaultInstance = companion2.defaultInstance();
        String source = wallpaperInfo2.getSource();
        Intrinsics.checkNotNull(source);
        applyWallpaperWithInfo(wallpaperInfo2, source, false, null, null, companion2.defaultInstance(), false);
        String source2 = wallpaperInfo.getSource();
        Intrinsics.checkNotNull(source2);
        applyWallpaperWithInfo$default(this, wallpaperInfo, source2, wallpaperInfo.getInfoType() == WallpaperInfoType.LOCK_WALLPAPER, null, pickWallpaperColorInfo, wallpaperApplyParamDefaultInstance, false, 8, null);
        WallpaperChangedListener.waitWallpaperChanged$default(wallpaperChangedListenerRegister, this.context, false, 2, null);
        companion.updateApplyStatusClone(2);
    }

    public final WallpaperChangedListener registerWallpaperChangedListener(boolean z, CommonConfig commonConfig) {
        if (z) {
            EditorServiceManager.Companion.getInstance().sendTemplateData(commonConfig.getLockscreenInfo());
        }
        WallpaperChangedListener.Companion companion = WallpaperChangedListener.Companion;
        Context context = this.context;
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        int magicType = wallpaperInfo != null ? wallpaperInfo.getMagicType() : 0;
        WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        return companion.register(context, magicType, wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null);
    }

    public final void reloadDataIfClearData(WallpaperInfo wallpaperInfo) {
        if (TextUtils.equals(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "video")) {
            if (MagicType.INSTANCE.isDepthVideo(wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getMagicType()) : null)) {
                updateVideoPath(wallpaperInfo, wallpaperInfo != null ? wallpaperInfo.getDepthVideo() : null);
            } else {
                updateVideoPath(wallpaperInfo, wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null);
            }
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public CommonConfig requireLockScreenInfoBySettings() {
        try {
            CommonConfig currentTemplateConfigInternal = getCurrentTemplateConfigInternal(false);
            if (currentTemplateConfigInternal == null) {
                return null;
            }
            updateCurrentTemplateWallpaperInfo(currentTemplateConfigInternal);
            tryLoadSubjectBitmap(currentTemplateConfigInternal, true, false, true);
            tryLoadDepthWallpaper(currentTemplateConfigInternal);
            return currentTemplateConfigInternal;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "requireLockScreenInfoBySettings failed: " + e);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:42:0x008b  */
    /* JADX WARN: Code duplicated, block: B:56:0x00b1  */
    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public WallpaperSource requireLockScreenWallpaper(final CommonConfig commonConfig, int i) {
        final boolean z;
        boolean z2;
        final Ref.ObjectRef objectRef;
        Ref.ObjectRef objectRef2;
        Bitmap bitmap;
        Ref.ObjectRef objectRef3;
        boolean z3;
        boolean z4;
        Bitmap bitmap2;
        Ref.ObjectRef objectRef4;
        String str;
        Ref.ObjectRef objectRef5;
        String str2;
        String str3;
        TemplateFilePathGenerator.TemplateFileType templateFileType;
        final Ref.ObjectRef objectRef6;
        final String str4;
        final String str5;
        Ref.ObjectRef objectRef7;
        Bitmap bitmap3;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        Object obj;
        HomeConfig homeInfo2;
        HomeConfig homeInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo4;
        HomeConfig homeInfo4;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo6;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo7;
        final TemplateApiImpl templateApiImpl = this;
        try {
            templateApiImpl.thirdPartyWallpaperServicePreview = null;
            MagicType magicType = MagicType.INSTANCE;
            boolean zIsDepthVideo = magicType.isDepthVideo((commonConfig == null || (lockscreenInfo4 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo7 = lockscreenInfo4.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo7.getMagicType()));
            long jCurrentTimeMillis = System.currentTimeMillis();
            boolean zAreEqual = Intrinsics.areEqual((commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo6 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo6.getResourceType(), "video");
            boolean zAreEqual2 = Intrinsics.areEqual((commonConfig == null || (homeInfo4 = commonConfig.getHomeInfo()) == null || (wallpaperInfo5 = homeInfo4.getWallpaperInfo()) == null) ? null : wallpaperInfo5.getResourceType(), "video");
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isFold()) {
                if (Wallpaper.Companion.isSensorWallpaper((commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getResourceType())) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            if (deviceUtil.isFold()) {
                if (Wallpaper.Companion.isSensorWallpaper((commonConfig == null || (homeInfo3 = commonConfig.getHomeInfo()) == null || (wallpaperInfo3 = homeInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType())) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            int i2 = (z || z2) ? 3 : 2;
            final CountDownLatch countDownLatch = new CountDownLatch(i2);
            final boolean zIsFoldLargeScreen = deviceUtil.isFoldLargeScreen(templateApiImpl.context);
            Ref.ObjectRef objectRef8 = new Ref.ObjectRef();
            final Ref.ObjectRef objectRef9 = new Ref.ObjectRef();
            final Ref.ObjectRef objectRef10 = new Ref.ObjectRef();
            if (i2 == 3) {
                objectRef = objectRef8;
                templateApiImpl = this;
                Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        TemplateApiImpl.requireLockScreenWallpaper$lambda$71(this.f$0, objectRef, zIsFoldLargeScreen, z, countDownLatch);
                    }
                });
            } else {
                objectRef = objectRef8;
            }
            Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    TemplateApiImpl.requireLockScreenWallpaper$lambda$72(objectRef9, templateApiImpl, countDownLatch);
                }
            });
            Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    TemplateApiImpl.requireLockScreenWallpaper$lambda$73(objectRef10, templateApiImpl, commonConfig, countDownLatch);
                }
            });
            Bitmap currentWallpaper = templateApiImpl.getCurrentWallpaper((commonConfig == null || (homeInfo2 = commonConfig.getHomeInfo()) == null) ? null : homeInfo2.getWallpaperInfo());
            countDownLatch.await();
            Log.i("Keyguard-Editor-TemplateApiImpl", "get wallpaper cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            Bitmap bitmap4 = (Bitmap) objectRef10.element;
            Object systemService = templateApiImpl.context.getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            Boolean boolValueOf = keyguardManager != null ? Boolean.valueOf(keyguardManager.isKeyguardLocked()) : null;
            final Ref.ObjectRef objectRef11 = new Ref.ObjectRef();
            WallpaperController.Companion companion = WallpaperController.Companion;
            WallpaperController companion2 = companion.getInstance(templateApiImpl.context);
            WallpaperWhich.LockWhich lockWhich = WallpaperWhich.LockWhich.INSTANCE;
            int currentMagicType = companion2.getCurrentMagicType(lockWhich.getWhich());
            StringBuilder sb = new StringBuilder();
            Ref.ObjectRef objectRef12 = objectRef;
            sb.append("getCurrentMagicType: ");
            sb.append(currentMagicType);
            sb.append(" callingSystemUI is ");
            sb.append(boolValueOf);
            Log.d("Keyguard-Editor-TemplateApiImpl", sb.toString());
            if (Intrinsics.areEqual(boolValueOf, Boolean.TRUE) && zIsDepthVideo && !Intrinsics.areEqual(companion.getInstance(templateApiImpl.context).getLockWallpaperType(), "linkage_video")) {
                final CountDownLatch countDownLatch2 = new CountDownLatch(1);
                bitmap = currentWallpaper;
                objectRef3 = objectRef12;
                templateApiImpl = this;
                objectRef2 = objectRef9;
                Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        TemplateApiImpl.requireLockScreenWallpaper$lambda$75(this.f$0, commonConfig, countDownLatch2, objectRef9, objectRef11);
                    }
                });
                countDownLatch2.await();
            } else {
                objectRef2 = objectRef9;
                bitmap = currentWallpaper;
                objectRef3 = objectRef12;
                if (Intrinsics.areEqual(boolValueOf, Boolean.FALSE) && zIsDepthVideo && !Intrinsics.areEqual(companion.getInstance(templateApiImpl.context).getLockWallpaperType(), "linkage_video")) {
                    objectRef2.element = requireVideoDepthScreenshotWallpaper(commonConfig);
                }
            }
            boolean z5 = objectRef2.element != null;
            boolean z6 = bitmap != null;
            boolean z7 = objectRef10.element != null;
            Ref.ObjectRef objectRef13 = objectRef3;
            Log.w("Keyguard-Editor-TemplateApiImpl", "require VideoDeep LockScreenWallpaper lockWallpaperFromWMS:" + z5 + " homeWallpaperFromMiuiWMS=" + z6 + " lockWallpaperFromMiuiWMS=" + z7 + " isThirdPartyTheme()=" + templateApiImpl.isThirdPartyTheme());
            final WallpaperSource wallpaperSource = (objectRef2.element == null || (templateApiImpl.isThirdPartyTheme() && !zIsDepthVideo)) ? new WallpaperSource((Bitmap) objectRef10.element, bitmap, bitmap4, LockWallpaperSource.MIUI_WALLPAPER_MANAGER) : new WallpaperSource((Bitmap) objectRef2.element, bitmap, bitmap4, LockWallpaperSource.WALLPAPER_MANAGER);
            if (magicType.isDepthVideo(Integer.valueOf(companion.getInstance(templateApiImpl.context).getCurrentMagicType(lockWhich.getWhich())))) {
                wallpaperSource.setVideoDeepSubject((Bitmap) objectRef11.element);
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "requireLockScreenWallpaper -> wallpaper = " + wallpaperSource);
            templateApiImpl.pickWallpaperLoadingColor(wallpaperSource.getWallpaperForPickColor(), companion.getCurrentWallpaperWhich(templateApiImpl.context));
            if (zIsDepthVideo) {
                String strGenerateDepthVideoWallpaperFilePath = templateApiImpl.filePathGenerator.generateDepthVideoWallpaperFilePath(TemplateFilePathGenerator.TemplateFileType.DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK);
                if (!FileUtil.isFileExist(strGenerateDepthVideoWallpaperFilePath) && (obj = objectRef10.element) != null) {
                    BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
                    Intrinsics.checkNotNull(obj);
                    boolean webp$default = BitmapUtil.toWebp$default(bitmapUtil, (Bitmap) obj, strGenerateDepthVideoWallpaperFilePath, false, 4, null);
                    FileUtil.chmod(TemplateFilePathGenerator.Companion.getDepthVideoWallpaperDirPath(), 511);
                    FileUtil.chmod(strGenerateDepthVideoWallpaperFilePath, 511);
                    Log.i("Keyguard-Editor-TemplateApiImpl", "requireLockScreenWallpaper: save depth lock result: " + webp$default);
                }
            }
            String strGenerateCurrentTemplateFilePath = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER);
            String strGenerateCurrentTemplateFilePath2 = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
            String strGenerateCurrentTemplateFilePath3 = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW);
            String strGenerateCurrentTemplateFilePath4 = templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN);
            TemplateFilePathGenerator templateFilePathGenerator = templateApiImpl.filePathGenerator;
            TemplateFilePathGenerator.TemplateFileType templateFileType2 = TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER;
            String strGenerateCurrentTemplateFilePath5 = templateFilePathGenerator.generateCurrentTemplateFilePath(templateFileType2);
            TemplateFilePathGenerator templateFilePathGenerator2 = templateApiImpl.filePathGenerator;
            TemplateFilePathGenerator.TemplateFileType templateFileType3 = TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME;
            String strGenerateCurrentTemplateFilePath6 = templateFilePathGenerator2.generateCurrentTemplateFilePath(templateFileType3);
            Ref.ObjectRef objectRef14 = objectRef2;
            if (TemplateApi.DefaultImpls.isThirdPartyWallpaper$default(templateApiImpl, null, 1, null)) {
                templateApiImpl.thirdPartyWallpaperServicePreview = wallpaperSource.getLockWallpaper();
                FileUtil.delete(strGenerateCurrentTemplateFilePath);
                FileUtil.delete(strGenerateCurrentTemplateFilePath2);
            } else {
                templateApiImpl.thirdPartyWallpaperServicePreview = null;
            }
            boolean zIsFileExist = FileUtil.isFileExist(strGenerateCurrentTemplateFilePath);
            boolean zIsFileExist2 = FileUtil.isFileExist(strGenerateCurrentTemplateFilePath2);
            boolean zIsFileExist3 = FileUtil.isFileExist(strGenerateCurrentTemplateFilePath3);
            boolean zIsFileExist4 = FileUtil.isFileExist(strGenerateCurrentTemplateFilePath4);
            boolean zIsFileExist5 = FileUtil.isFileExist(strGenerateCurrentTemplateFilePath5);
            boolean zIsFileExist6 = FileUtil.isFileExist(strGenerateCurrentTemplateFilePath6);
            Log.i("Keyguard-Editor-TemplateApiImpl", "requireLockScreenWallpaper: currentLockWallpaperSourceExist=" + zIsFileExist + " currentHomeWallpaperSourceExist=" + zIsFileExist2 + " currentWallpaper=" + (objectRef10.element != null) + " deleteType=" + i);
            WallpaperController companion3 = companion.getInstance();
            if (zAreEqual && !zIsFileExist5 && Process.INSTANCE.isKeyguardeditorProcess()) {
                z4 = zIsFileExist2;
                z3 = zIsFileExist;
                bitmap2 = bitmap;
                str = strGenerateCurrentTemplateFilePath;
                objectRef5 = objectRef14;
                str2 = strGenerateCurrentTemplateFilePath3;
                templateFileType = templateFileType3;
                str3 = strGenerateCurrentTemplateFilePath4;
                objectRef4 = objectRef10;
                String strCopyFile$default = copyFile$default(templateApiImpl, companion3 != null ? companion3.getVideoWallpaperOriginPath(lockWhich) : null, templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(templateFileType2), false, 0L, false, false, 60, null);
                if (strCopyFile$default != null && commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) != null) {
                    wallpaperInfo2.setOriginResourcePath(strCopyFile$default);
                }
            } else {
                z3 = zIsFileExist;
                z4 = zIsFileExist2;
                bitmap2 = bitmap;
                objectRef4 = objectRef10;
                str = strGenerateCurrentTemplateFilePath;
                objectRef5 = objectRef14;
                str2 = strGenerateCurrentTemplateFilePath3;
                str3 = strGenerateCurrentTemplateFilePath4;
                templateFileType = templateFileType3;
            }
            if (zAreEqual2 && !zIsFileExist6 && Process.INSTANCE.isKeyguardeditorProcess()) {
                String strCopyFile$default2 = copyFile$default(templateApiImpl, companion3 != null ? companion3.getVideoWallpaperOriginPath(WallpaperWhich.HomeWhich.INSTANCE) : null, templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(templateFileType), false, 0L, false, false, 60, null);
                if (strCopyFile$default2 != null && commonConfig != null && (homeInfo = commonConfig.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
                    wallpaperInfo.setOriginResourcePath(strCopyFile$default2);
                }
            }
            if (!z3 || i == 3 || i == 2) {
                objectRef6 = objectRef4;
                if (objectRef6.element != null) {
                    str4 = strGenerateCurrentTemplateFilePath2;
                    final boolean z8 = z4;
                    final Bitmap bitmap5 = bitmap2;
                    str5 = str;
                    bitmap2 = bitmap5;
                    z4 = z8;
                    Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda25
                        @Override // java.lang.Runnable
                        public final void run() {
                            TemplateApiImpl.requireLockScreenWallpaper$lambda$80(objectRef6, str5, bitmap5, z8, commonConfig, str4, this);
                        }
                    });
                } else {
                    str4 = strGenerateCurrentTemplateFilePath2;
                    str5 = str;
                }
            } else {
                str4 = strGenerateCurrentTemplateFilePath2;
                str5 = str;
                objectRef6 = objectRef4;
            }
            if (!z4 || i == 3 || i == 1) {
                if (bitmap2 != null) {
                    final String str6 = str4;
                    final String str7 = str5;
                    final Ref.ObjectRef objectRef15 = objectRef6;
                    final boolean z9 = z3;
                    final Bitmap bitmap6 = bitmap2;
                    Runnable runnable = new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda26
                        @Override // java.lang.Runnable
                        public final void run() {
                            TemplateApiImpl.requireLockScreenWallpaper$lambda$82(bitmap6, str6, objectRef15, z9, commonConfig, str7, this);
                        }
                    };
                    bitmap2 = bitmap6;
                    objectRef6 = objectRef15;
                    Task.run(runnable);
                }
            }
            if (i2 == 3) {
                if (zIsFoldLargeScreen) {
                    if (z) {
                        Bitmap bitmap7 = (Bitmap) objectRef5.element;
                        bitmap3 = bitmap7 == null ? (Bitmap) objectRef6.element : bitmap7;
                    } else {
                        bitmap3 = bitmap2;
                    }
                    wallpaperSource.setSensorLargeWallpaper(bitmap3);
                    objectRef7 = objectRef13;
                    wallpaperSource.setSensorSmallWallpaper((Bitmap) objectRef7.element);
                } else {
                    objectRef7 = objectRef13;
                    wallpaperSource.setSensorLargeWallpaper((Bitmap) objectRef7.element);
                    wallpaperSource.setSensorSmallWallpaper(z != 0 ? (Bitmap) objectRef6.element : bitmap2);
                }
                if (!zIsFileExist3 && wallpaperSource.getSensorLargeWallpaper() != null) {
                    final String str8 = str2;
                    Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda27
                        @Override // java.lang.Runnable
                        public final void run() {
                            TemplateApiImpl.requireLockScreenWallpaper$lambda$83(wallpaperSource, str8);
                        }
                    });
                }
                if (!zIsFileExist4 && wallpaperSource.getSensorSmallWallpaper() != null) {
                    final String str9 = str3;
                    Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda28
                        @Override // java.lang.Runnable
                        public final void run() {
                            TemplateApiImpl.requireLockScreenWallpaper$lambda$84(wallpaperSource, str9);
                        }
                    });
                }
            } else {
                objectRef7 = objectRef13;
            }
            Log.d("Keyguard-Editor-TemplateApiImpl", "=====requireLockScreenWallpaper: anotherScreenSensorWallpaper=" + objectRef7.element);
            return wallpaperSource;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "requireLockScreenWallpaper failed: " + e);
            return null;
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void requireOriginWallpaperForScreenshot(WallpaperInfo wallpaperInfo, String target) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (DeviceUtil.INSTANCE.isPhone()) {
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        Bitmap bitmapCurrentOriginWallpaper = currentOriginWallpaper(wallpaperInfo);
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentOriginWallpaper cost " + (SystemClock.uptimeMillis() - jUptimeMillis));
        BitmapTempStore.Companion.getInstance().putBitmap(target, bitmapCurrentOriginWallpaper);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap requireThirdPartyThemePreviewImage(int i, int i2) {
        if (!isThirdPartyTheme()) {
            return null;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "requireThirdPartyPreviewPath: third party theme");
        return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, this.filePathGenerator.generateFilePath(TemplateFilePathGenerator.TemplateFileType.THIRD_PARTY_THEME_PREVIEW), i, i2, null, 8, null);
    }

    public Bitmap requireVideoDepthScreenshotWallpaper(CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (MagicType.INSTANCE.isDepthVideo((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getMagicType()))) {
            return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, this.filePathGenerator.generateDepthVideoWallpaperFilePath(TemplateFilePathGenerator.TemplateFileType.DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK), 0, 0, null, 14, null);
        }
        return null;
    }

    public final Deferred runAsync(Function0 function0) {
        return BuildersKt__Builders_commonKt.async$default(UtilsKt.getDefaultCoroutineScope(), Dispatchers.getIO(), null, new C00911(function0, null), 2, null);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void saveCurrentTemplateScreenshot(ScreenshotSource screenshotSource) {
        Intrinsics.checkNotNullParameter(screenshotSource, "screenshotSource");
        boolean zIsDualClock = isDualClock();
        this.currentTemplateDualClock = zIsDualClock;
        Log.i("Keyguard-Editor-TemplateApiImpl", "saveCurrentTemplateScreenshot: currentTemplateDualClock=" + zIsDualClock);
        Bitmap smallScreenshot = screenshotSource.getSmallScreenshot();
        if (smallScreenshot != null) {
            saveCurrentTemplateScreenshotForScreenshotType(smallScreenshot, TemplateFilePathGenerator.TemplateFileType.SMALL_SCREENSHOT);
        }
        Bitmap largePortraitScreenshot = screenshotSource.getLargePortraitScreenshot();
        if (largePortraitScreenshot != null) {
            saveCurrentTemplateScreenshotForScreenshotType(largePortraitScreenshot, TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_SCREENSHOT);
        }
        Bitmap largeLandscapeScreenshot = screenshotSource.getLargeLandscapeScreenshot();
        if (largeLandscapeScreenshot != null) {
            saveCurrentTemplateScreenshotForScreenshotType(largeLandscapeScreenshot, TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_SCREENSHOT);
        }
        Bitmap smallWallpaperScreenshot = screenshotSource.getSmallWallpaperScreenshot();
        if (smallWallpaperScreenshot != null) {
            saveCurrentTemplateScreenshotForScreenshotType(smallWallpaperScreenshot, TemplateFilePathGenerator.TemplateFileType.SMALL_WALLPAPER_SCREENSHOT);
        }
        Bitmap largePortWallpaperScreenshot = screenshotSource.getLargePortWallpaperScreenshot();
        if (largePortWallpaperScreenshot != null) {
            saveCurrentTemplateScreenshotForScreenshotType(largePortWallpaperScreenshot, TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_WALLPAPER_SCREENSHOT);
        }
        Bitmap largeLandWallpaperScreenshot = screenshotSource.getLargeLandWallpaperScreenshot();
        if (largeLandWallpaperScreenshot != null) {
            saveCurrentTemplateScreenshotForScreenshotType(largeLandWallpaperScreenshot, TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT);
        }
    }

    public final boolean saveCurrentTemplateScreenshotByProvider(Bitmap bitmap, String str) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("file_type", 1);
            bundle.putString("file_mode", AnimatedProperty.PROPERTY_NAME_W);
            Bundle bundleCall = this.context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callGetFileDescriptorByFileName", str, bundle);
            parcelFileDescriptor = bundleCall != null ? (ParcelFileDescriptor) bundleCall.getParcelable("parcelFileDescriptor") : null;
            return BitmapUtil.INSTANCE.toJPEG(bitmap, parcelFileDescriptor);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "saveCurrentTemplateScreenshotByProvider: " + e);
        } finally {
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        }
    }

    public final void saveCurrentTemplateScreenshotForScreenshotType(Bitmap bitmap, TemplateFilePathGenerator.TemplateFileType templateFileType) {
        if (bitmap == null) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "saveCurrentTemplateScreenshotForScreenshotType -> save failed: screenshotBitmap = null. screenshotType = " + templateFileType);
            return;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "saveCurrentTemplateScreenshotForScreenshotType -> save result: " + (!Process.INSTANCE.isKeyguardeditorProcess() ? saveCurrentTemplateScreenshotByProvider(bitmap, templateFileType.getFileName()) : BitmapUtil.INSTANCE.toJPEG(bitmap, this.filePathGenerator.generateCurrentTemplateFilePath(templateFileType))) + ", screenshotType = " + templateFileType);
    }

    public final String saveScreenshotToCurrentDir(Bitmap bitmap, String str, TemplateFilePathGenerator.TemplateFileType templateFileType, String str2, WallpaperPositionInfo wallpaperPositionInfo) {
        String strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(templateFileType);
        Log.w("Keyguard-Editor-TemplateApiImpl", "saveScreenshotToCurrent: currentScreenshotPath = " + strGenerateCurrentTemplateFilePath);
        if (bitmap != null) {
            if (BitmapUtil.INSTANCE.toJPEG(bitmap, strGenerateCurrentTemplateFilePath)) {
                Log.d("Keyguard-Editor-TemplateApiImpl", "saveScreenshotToCurrentDir: return currentScreenshotPath");
                return strGenerateCurrentTemplateFilePath;
            }
            Log.w("Keyguard-Editor-TemplateApiImpl", "saveScreenshotToCurrent: save screenshot to current error! screenshotType = " + templateFileType);
            return null;
        }
        if (str != null) {
            Log.d("Keyguard-Editor-TemplateApiImpl", "saveScreenshotToCurrentDir: historyScreenshotUri != null");
            return copyFile$default(this, str, strGenerateCurrentTemplateFilePath, false, 0L, false, false, 60, null);
        }
        if (templateFileType == TemplateFilePathGenerator.TemplateFileType.SMALL_WALLPAPER_SCREENSHOT || templateFileType == TemplateFilePathGenerator.TemplateFileType.LARGE_PORTRAIT_WALLPAPER_SCREENSHOT || templateFileType == TemplateFilePathGenerator.TemplateFileType.LARGE_LANDSCAPE_WALLPAPER_SCREENSHOT) {
            if (str2 == null || str2.length() == 0 || !FileUtil.isFileExist(str2)) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "saveScreenshotToCurrentDir: fallback wallpaper path invalid, type=" + templateFileType + " path=" + str2);
            } else {
                BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
                Bitmap bitmapFromFile$default = BitmapUtil.fromFile$default(bitmapUtil, str2, 0, 0, null, 14, null);
                if (bitmapFromFile$default != null && wallpaperPositionInfo != null && wallpaperPositionInfo.getRight() > wallpaperPositionInfo.getLeft() && wallpaperPositionInfo.getBottom() > wallpaperPositionInfo.getTop()) {
                    bitmapFromFile$default = cropBitmap(bitmapFromFile$default, wallpaperPositionInfo, true);
                }
                if (bitmapFromFile$default != null && bitmapUtil.toJPEG(bitmapFromFile$default, strGenerateCurrentTemplateFilePath)) {
                    Log.w("Keyguard-Editor-TemplateApiImpl", "saveScreenshotToCurrentDir: create fallback screenshot success, type=" + templateFileType);
                    return strGenerateCurrentTemplateFilePath;
                }
                Log.w("Keyguard-Editor-TemplateApiImpl", "saveScreenshotToCurrentDir: create fallback screenshot failed, type=" + templateFileType);
            }
        }
        return null;
    }

    public final boolean saveTemplateConfig(CommonConfig commonConfig, String str) {
        return FileUtil.saveFile(str, GsonSingletonKt.getGSON_SINGLETON().toJson(commonConfig));
    }

    public final void setClassicSignatureClassLine(CommonConfig commonConfig, String str) {
        ClockInfo clockInfo;
        String templateId;
        TemplateConfig lockscreenInfo = commonConfig.getLockscreenInfo();
        if (lockscreenInfo == null || (clockInfo = lockscreenInfo.getClockInfo()) == null || (templateId = clockInfo.getTemplateId()) == null) {
            return;
        }
        if ((StringsKt.contains$default((CharSequence) templateId, (CharSequence) "classic", false, 2, (Object) null) || Intrinsics.areEqual(templateId, "all_in_one")) && FashionGalleryHelper.INSTANCE.obtainFashionGalleryOpened(this.context)) {
            clockInfo.setClassicLine5(!TextUtils.isEmpty(str) || !TextUtils.isEmpty(clockInfo.getClassicSignature()) ? 12 : 0);
            Log.i("Keyguard-Editor-TemplateApiImpl", "setClassicSignatureClassLine classicLine5 = " + clockInfo.getClassicLine5());
        }
    }

    public final void setClassicSignatureForResetDefault(CommonConfig commonConfig, String str) {
        String templateId;
        TemplateConfig lockscreenInfo = commonConfig.getLockscreenInfo();
        ClockInfo clockInfo = lockscreenInfo != null ? lockscreenInfo.getClockInfo() : null;
        if (isDefaultTheme() && isDefaultConfig(commonConfig) && clockInfo != null && (templateId = clockInfo.getTemplateId()) != null && StringsKt.contains$default((CharSequence) templateId, (CharSequence) "classic", false, 2, (Object) null) && TextUtils.isEmpty(clockInfo.getClassicSignature()) && !TextUtils.isEmpty(str)) {
            Intrinsics.checkNotNull(str);
            clockInfo.setClassicSignature(str);
            clockInfo.setClassicLine5(12);
            Log.i("Keyguard-Editor-TemplateApiImpl", "setClassicSignatureForResetDefault: inject classicSignature");
        }
    }

    public final void setLoopTypeForCurrentTemplate(WallpaperInfo wallpaperInfo) {
        if (wallpaperInfo == null) {
            return;
        }
        GalleryStatusBean galleryStatus = FashionGalleryHelper.INSTANCE.getGalleryStatus(this.context, true);
        Log.d("Keyguard-Editor-TemplateApiImpl", "setLoopTypeForCurrentTemplate: galleryStatusBean=" + galleryStatus);
        if (wallpaperInfo.getExtraInfo() == null) {
            wallpaperInfo.setExtraInfo(new ExtraInfo(null, null, 3, null));
        }
        ExtraInfo extraInfo = wallpaperInfo.getExtraInfo();
        Intrinsics.checkNotNull(extraInfo);
        int loopType = galleryStatus.getLoopType();
        if (loopType == null) {
            loopType = -1;
        }
        extraInfo.setLoopType(loopType);
    }

    public final void setOwnerInfo(TemplateConfig templateConfig) {
        ClockInfo clockInfo;
        String classicSignature;
        ClockInfo clockInfo2;
        String templateId = (templateConfig == null || (clockInfo2 = templateConfig.getClockInfo()) == null) ? null : clockInfo2.getTemplateId();
        String str = "";
        if (!TextUtils.equals(templateId, "classic") && !TextUtils.equals(templateId, "classic_plus") && !TextUtils.equals(templateId, "classic_max") && !TextUtils.equals(templateId, "all_in_one")) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "setOwnerInfo: clear ownerInfo for non-signature template=" + templateId);
            LockPatternUtils.INSTANCE.setOwnerInfo(this.context, "");
            return;
        }
        if (templateConfig != null && (clockInfo = templateConfig.getClockInfo()) != null && (classicSignature = clockInfo.getClassicSignature()) != null) {
            str = classicSignature;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "classicSignature:=" + str);
        LockPatternUtils.INSTANCE.setOwnerInfo(this.context, str);
    }

    public final void setSupportMattingWhenWallpaperNotChanged(boolean z, String str) {
        Log.i("Keyguard-Editor-TemplateApiImpl", "setSupportMattingWhenWallpaperNotChanged supportSubject " + z + " resourceType " + str);
        if (Intrinsics.areEqual(str, "image")) {
            boolean z2 = !FashionGalleryHelper.INSTANCE.isGalleryInstalledAndOpen(this.context) && z;
            TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
            if (z2 != templateDataUtil.isSupportMatting(this.context)) {
                Log.i("Keyguard-Editor-TemplateApiImpl", "setSupportMattingWhenWallpaperNotChanged value " + z2);
                templateDataUtil.setSupportMatting(this.context, z2);
            }
        }
    }

    public final boolean shouldApplyOuterVideoWallpaper(WallpaperInfo wallpaperInfo) {
        SensorWallpaperPrams sensorWallpaperPrams;
        return WallpaperController.Companion.shouldApplyOuterVideoWallpaper(wallpaperInfo, (wallpaperInfo == null || (sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams()) == null) ? null : sensorWallpaperPrams.getOuterVideoPreViewPath());
    }

    public final boolean shouldApplyWallpaperAgain(String str, WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, String str2, WallpaperInfo wallpaperInfo3, WallpaperInfo wallpaperInfo4, Integer num, Integer num2, boolean z) {
        String originResourcePath;
        WallpaperPositionInfo positionInfo;
        String originResourcePath2;
        WallpaperPositionInfo positionInfo2;
        if (Intrinsics.areEqual(str, str2)) {
            if (StringsKt.equals$default(wallpaperInfo != null ? wallpaperInfo.getSource() : null, wallpaperInfo3.getSource(), false, 2, null) && wallpaperInfo != null && (originResourcePath = wallpaperInfo.getOriginResourcePath()) != null && originResourcePath.equals(wallpaperInfo3.getOriginResourcePath()) && (positionInfo = wallpaperInfo.getPositionInfo()) != null && positionInfo.equals(wallpaperInfo3.getPositionInfo()) && StringsKt.equals$default(wallpaperInfo.getResourceType(), wallpaperInfo3.getResourceType(), false, 2, null)) {
                if (!StringsKt.equals$default(wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null, wallpaperInfo4.getSource(), false, 2, null) || wallpaperInfo2 == null || (originResourcePath2 = wallpaperInfo2.getOriginResourcePath()) == null || !originResourcePath2.equals(wallpaperInfo4.getOriginResourcePath()) || (positionInfo2 = wallpaperInfo2.getPositionInfo()) == null || !positionInfo2.equals(wallpaperInfo4.getPositionInfo()) || !StringsKt.equals$default(wallpaperInfo2.getResourceType(), wallpaperInfo4.getResourceType(), false, 2, null) || !Intrinsics.areEqual("doodle", str) || z) {
                    return true;
                }
                MagicType magicType = MagicType.INSTANCE;
                if (!magicType.isFrontBack(wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getMagicType()) : null) && !magicType.isFrontBack(Integer.valueOf(wallpaperInfo3.getMagicType()))) {
                    if (magicType.isDepth(wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getMagicType()) : null) == magicType.isDepth(Integer.valueOf(wallpaperInfo3.getMagicType())) && Intrinsics.areEqual(num, num2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final boolean shouldNotifyGallery(boolean z, boolean z2) {
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return z && z2;
        }
        return FashionGalleryHelper.INSTANCE.isFashionGalleryInstalled(this.context);
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public boolean shouldSubjectWithBackgroundColor(CommonConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        if (!Intrinsics.areEqual(config.getLockscreenInfo().getClockInfo().getTemplateId(), "doodle")) {
            return false;
        }
        MagicType magicType = MagicType.INSTANCE;
        WallpaperInfo wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo();
        return magicType.isDepth(wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getMagicType()) : null);
    }

    public final void showCantApplyThirdPartyWallpaperToast() {
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda19
            @Override // java.util.function.Supplier
            public final Object get() {
                return Unit.INSTANCE;
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TemplateApiImpl.showCantApplyThirdPartyWallpaperToast$lambda$12(this.f$0, (Unit) obj);
            }
        });
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public int tryClearLocalInvalidWallpaper() {
        boolean z;
        boolean z2;
        TemplateApiImpl templateApiImpl;
        if (!Process.INSTANCE.isKeyguardeditorProcess() || !this.canDeleteWhenApplying) {
            return 0;
        }
        WallpaperController companion = WallpaperController.Companion.getInstance(this.context, false);
        String lockWallpaperSetPkg = getLockWallpaperSetPkg();
        String homeWallpaperSetPkg = getHomeWallpaperSetPkg();
        boolean zAreEqual = Intrinsics.areEqual(companion.getLockWallpaperType(), "super_save_power");
        boolean zAreEqual2 = Intrinsics.areEqual(companion.getHomeWallpaperType(), "super_save_power");
        boolean z3 = zAreEqual || zAreEqual2;
        Log.i("Keyguard-Editor-TemplateApiImpl", "lockWallpaperSetPkg = " + lockWallpaperSetPkg + " homeWallpaperSetPkg = " + homeWallpaperSetPkg + " isLockSuperSavePower=" + zAreEqual + " isHomeSuperSavePower=" + zAreEqual2);
        if (!z3 && !isThirdPartyTheme() && lockWallpaperSetPkg != null && Intrinsics.areEqual(getLockWallpaperSetPkg(), "com.android.thememanager")) {
            FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK));
        }
        if (z3 || isThirdPartyTheme() || homeWallpaperSetPkg == null || isHomeSetPkgSupportDepth()) {
            z = false;
        } else {
            try {
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME));
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME));
            } catch (Exception e) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "tryClearLocalInvalidWallpaper: " + e);
            }
            z = true;
        }
        if (z3 || isThirdPartyTheme() || lockWallpaperSetPkg == null || isLockSetPkgSupportDepth()) {
            z2 = false;
        } else {
            Log.i("Keyguard-Editor-TemplateApiImpl", "deleteOriginalLockWallpaper");
            try {
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER));
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER));
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT));
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT_MASK));
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW));
                FileUtil.delete(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN));
                FileUtil.delete(this.filePathGenerator.generateDepthVideoWallpaperFilePath(TemplateFilePathGenerator.TemplateFileType.DEPTH_VIDEO_SCREENSHOT_WALLPAPER_LOCK));
                CommonConfig currentTemplateConfigInternal$default = TemplateApi.DefaultImpls.getCurrentTemplateConfigInternal$default(this, false, 1, null);
                if (currentTemplateConfigInternal$default != null) {
                    WallpaperInfo wallpaperInfo = currentTemplateConfigInternal$default.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo != null) {
                        wallpaperInfo.getMagicType();
                    }
                    templateApiImpl = this;
                    TemplateApi.DefaultImpls.updateCurrentTemplate$default(templateApiImpl, currentTemplateConfigInternal$default, "clearInvalidWallpaper", false, false, 8, null);
                } else {
                    templateApiImpl = this;
                }
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.step5EndSuccess();
                } catch (Exception unused) {
                }
                FileUtil.delete(templateApiImpl.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT));
            } catch (Exception e2) {
                Log.e("Keyguard-Editor-TemplateApiImpl", "tryClearLocalInvalidWallpaper: " + e2);
            }
            z2 = true;
        }
        if (z && z2) {
            return 3;
        }
        if (z) {
            return 1;
        }
        return z2 ? 2 : 0;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0047  */
    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap tryComposeSubjectWithBackgroundColor(CommonConfig config, Bitmap subject) {
        boolean z;
        WallpaperInfo wallpaperInfo;
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(subject, "subject");
        if (!shouldSubjectWithBackgroundColor(config)) {
            return null;
        }
        DoodleInfo doodle = config.getLockscreenInfo().getDoodle();
        int solidColor = doodle != null ? doodle.getSolidColor() : -1;
        BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
        int width = subject.getWidth();
        int height = subject.getHeight();
        Bitmap.Config config2 = Bitmap.Config.ARGB_8888;
        if (WideColorGamutManager.INSTANCE.isWideColorBitmap(subject) && (wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo()) != null) {
            z = wallpaperInfo.isWideColorGamut();
        }
        Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(bitmapCreator, width, height, config2, z, false, 16, null);
        Canvas canvas = new Canvas(bitmapCreateBitmap$default);
        canvas.drawColor(solidColor);
        canvas.drawBitmap(subject, 0.0f, 0.0f, new Paint());
        return bitmapCreateBitmap$default;
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public Bitmap tryComposeSubjectWithBackgroundColor(CommonConfig config, String str) {
        Bitmap bitmapFromFile$default;
        Intrinsics.checkNotNullParameter(config, "config");
        if (str == null || str.length() == 0 || !shouldSubjectWithBackgroundColor(config) || (bitmapFromFile$default = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, getCurrentWallpaperPathSafely(config.getLockscreenInfo().getWallpaperInfo(), TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT), 0, 0, null, 14, null)) == null) {
            return null;
        }
        return tryComposeSubjectWithBackgroundColor(config, bitmapFromFile$default);
    }

    public final void tryDeleteHistoryTemplateFiles(Long l) {
        if (l != null) {
            FileUtil.deleteFolder(this.filePathGenerator.generateHistoryTemplateDir(l.longValue()));
            Log.i("Keyguard-Editor-TemplateApiImpl", "clearTemplateFilesByLastModifyTime: delete folder lastModifyTime");
        }
    }

    public final void tryLoadDepthWallpaper(CommonConfig commonConfig) {
        String croppedFrontBack;
        WallpaperInfo wallpaperInfo;
        String frontBack;
        WallpaperInfo wallpaperInfo2;
        MagicType magicType = MagicType.INSTANCE;
        WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (magicType.isFrontBack(wallpaperInfo3 != null ? Integer.valueOf(wallpaperInfo3.getMagicType()) : null)) {
            WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            String frontBack2 = wallpaperInfo4 != null ? wallpaperInfo4.getFrontBack() : null;
            if (frontBack2 == null || frontBack2.length() == 0) {
                WallpaperInfo wallpaperInfo5 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo5 != null) {
                    wallpaperInfo5.resetMagicType("null path");
                    return;
                }
                return;
            }
            WallpaperInfo wallpaperInfo6 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo6 != null && (frontBack = wallpaperInfo6.getFrontBack()) != null) {
                WallpaperInfo wallpaperInfo7 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo7 != null) {
                    wallpaperInfo7.setFrontBackBitmap(GLRenderHelper.getCachedAlphaDepth$default(GLRenderHelper.INSTANCE, frontBack, null, null, TemplateFilePathGenerator.TemplateFileType.FRONT_BACK.getFileName(), 6, null));
                }
                WallpaperInfo wallpaperInfo8 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if ((wallpaperInfo8 != null ? wallpaperInfo8.getFrontBackBitmap() : null) == null && (wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo()) != null) {
                    wallpaperInfo2.resetMagicType("load failed");
                }
            }
            WallpaperInfo wallpaperInfo9 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo9 == null || (croppedFrontBack = wallpaperInfo9.getCroppedFrontBack()) == null || (wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo()) == null) {
                return;
            }
            wallpaperInfo.setCroppedFrontBackBitmap(GLRenderHelper.getCachedAlphaDepth$default(GLRenderHelper.INSTANCE, croppedFrontBack, null, null, TemplateFilePathGenerator.TemplateFileType.CROPPED_FRONT_BACK.getFileName(), 6, null));
        }
    }

    public final void tryLoadSubjectBitmap(CommonConfig commonConfig, boolean z, boolean z2, boolean z3) {
        Log.d("Keyguard-Editor-TemplateApiImpl", "tryLoadSubjectBitmap: config=" + commonConfig);
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo == null) {
            Log.w("Keyguard-Editor-TemplateApiImpl", "tryLoadSubjectBitmap: wallpaper info == null");
            return;
        }
        Log.d("Keyguard-Editor-TemplateApiImpl", "tryLoadSubjectBitmap: current=" + z + ",isPreset=" + z2 + ",corp=" + z3 + ",thirdPartyTheme=" + commonConfig.getLockscreenInfo().isThirdPartyTheme() + ",depthVideo=" + MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType())));
        Bitmap bitmapLoadHistoryTemplateSubjectCompat = null;
        if (z2) {
            bitmapLoadHistoryTemplateSubjectCompat = TemplatePreviewImageComposer.composeSubjectImage$default(TemplatePreviewImageComposer.INSTANCE, wallpaperInfo, false, 2, null);
        } else if (!z) {
            bitmapLoadHistoryTemplateSubjectCompat = loadHistoryTemplateSubjectCompat(wallpaperInfo);
        } else if (!Intrinsics.areEqual(WallpaperController.Companion.getInstance(this.context).getLockWallpaperType(), "linkage_video")) {
            bitmapLoadHistoryTemplateSubjectCompat = z3 ? BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, getCurrentWallpaperPathSafely(commonConfig.getLockscreenInfo().getWallpaperInfo(), TemplateFilePathGenerator.TemplateFileType.CROP_SUBJECT), 0, 0, this.context, 6, null) : loadCurrentTemplateSubjectCompat(commonConfig);
        }
        wallpaperInfo.setSubjectBitmap(bitmapLoadHistoryTemplateSubjectCompat);
    }

    public final void updateConfigForOTA(CommonConfig commonConfig) {
        if (commonConfig == null || commonConfig.getVersion() >= 3) {
            return;
        }
        if (commonConfig.getLockscreenInfo().getClockInfo().isAutoPrimaryColor()) {
            commonConfig.getLockscreenInfo().getClockInfo().setClockEffect(1);
            commonConfig.getLockscreenInfo().getClockInfo().setPrimaryFontColorPanelId(0);
            commonConfig.getLockscreenInfo().getClockInfo().setSecondaryFontColorPanelId(0);
        } else {
            commonConfig.getLockscreenInfo().getClockInfo().setClockEffect(0);
            commonConfig.getLockscreenInfo().getClockInfo().setPrimaryFontColorPanelId(13);
            commonConfig.getLockscreenInfo().getClockInfo().setSecondaryFontColorPanelId(13);
        }
        Log.w("Keyguard-Editor-TemplateApiImpl", "updateConfigForOTA: newConfig=" + commonConfig);
    }

    public final void updateCurrentFollowStatus(int i, String str) {
        synchronized (templateWriteLock) {
            CommonConfig currentTemplateConfigInternal = getCurrentTemplateConfigInternal(false);
            if (currentTemplateConfigInternal == null) {
                Log.w("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplatePartial: tag=" + str + " skip, no current config");
                return;
            }
            if (i != currentTemplateConfigInternal.getHomeInfo().getFollowStatus()) {
                Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentFollowStatus: tag=" + str + ", newFollowStatus=" + i);
                currentTemplateConfigInternal.getHomeInfo().setFollowStatus(i);
                TemplateApi.DefaultImpls.updateCurrentTemplate$default(this, currentTemplateConfigInternal, str, false, false, 12, null);
                Unit unit = Unit.INSTANCE;
            } else {
                Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplatePartial: tag=" + str + " skip write (no change)");
            }
        }
    }

    public final void updateCurrentHistory(CommonConfig commonConfig) {
        String resourceType;
        Object objM1061constructorimpl;
        String templateId = commonConfig.getLockscreenInfo().getClockInfo().getTemplateId();
        int i = this.currentTemplateLoadingColor;
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo == null || (resourceType = wallpaperInfo.getResourceType()) == null) {
            resourceType = "image";
        }
        String str = resourceType;
        WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        String originResourcePath = wallpaperInfo2 != null ? wallpaperInfo2.getOriginResourcePath() : null;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String templateName = commonConfig.getLockscreenInfo().getTemplateName();
        int hideFlag = commonConfig.getLockscreenInfo().getHideFlag();
        WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        int magicType = wallpaperInfo3 != null ? wallpaperInfo3.getMagicType() : 0;
        boolean z = this.isDarkWallpaper;
        WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        TemplateHistoryConfig templateHistoryConfig = new TemplateHistoryConfig(templateId, "UNDEFINE", i, "UNDEFINE", "UNDEFINE", "UNDEFINE", "UNDEFINE", "UNDEFINE", "UNDEFINE", false, false, str, originResourcePath, jCurrentTimeMillis, templateName, 1, hideFlag, magicType, z ? 1 : 0, wallpaperInfo4 != null ? wallpaperInfo4.isAiGenerated() : false);
        try {
            Result.Companion companion = Result.Companion;
            this.templateDao.deleteAndUpdate(templateHistoryConfig);
            objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentHistory: " + (thM1063exceptionOrNullimpl != null ? thM1063exceptionOrNullimpl.getMessage() : null));
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void updateCurrentTemplate(CommonConfig config, String tag, boolean z, boolean z2) {
        LargeScreenHierarchyEnable largeScreenHierarchyEnable;
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(tag, "tag");
        synchronized (templateWriteLock) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplate: tag=" + tag + " timeStamp=" + jCurrentTimeMillis + " " + config);
                if (z2) {
                    checkProcess();
                }
                fillDefaultClockParamsIfNeeded(config);
                if (DeviceUtil.INSTANCE.osVersion() >= 4) {
                    config.setVersion(4);
                    config.getLockscreenInfo().setVersion(4);
                }
                config.setUpdateTemplate(jCurrentTimeMillis);
                config.setLastUpdate(Application.getProcessName());
                String ownerInfo = getOwnerInfo();
                setClassicSignatureClassLine(config, ownerInfo);
                setClassicSignatureForResetDefault(config, ownerInfo);
                WallpaperInfo wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo != null && (largeScreenHierarchyEnable = wallpaperInfo.getLargeScreenHierarchyEnable()) != null) {
                    largeScreenHierarchyEnable.enforceNotificationHierarchyByDevice(Intrinsics.areEqual(config.getLockscreenInfo().getClockInfo().getTemplateId(), "all_in_one"));
                }
                String json = GsonSingletonKt.getGSON_SINGLETON().toJson(config);
                if (waitFlushConfigForLockTransform) {
                    ClockInfo clockInfo = config.getLockscreenInfo().getClockInfo();
                    Integer numValueOf = clockInfo != null ? Integer.valueOf(clockInfo.getStyle()) : null;
                    Log.w("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplate: DEFERRED write, tag=" + tag + ", style=" + numValueOf + ", thread=" + Thread.currentThread().getName());
                    configJsonForFlush = json;
                } else {
                    Intrinsics.checkNotNull(json);
                    writeTemplateInfo(json, "update template");
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        RandomColorUtil.INSTANCE.updateCurrentRandomColorType(this.context, config.getLockscreenInfo());
        if (z) {
            TrackHelper.INSTANCE.trackCurrentTemplate(this.context, config);
        }
    }

    public final void updateCurrentTemplateWallpaper(WallpaperInfo wallpaperInfo, boolean z) {
        boolean z2 = wallpaperInfo.getInfoType() == WallpaperInfoType.LOCK_WALLPAPER;
        if (!Wallpaper.Companion.isSensorWallpaper(wallpaperInfo.getResourceType()) || !z) {
            Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateInternal: getSourceWallpaper");
            wallpaperInfo.setCurrentWallpaper(getWallpaperInternal(wallpaperInfo, z));
            return;
        }
        Log.i("Keyguard-Editor-TemplateApiImpl", "getCurrentTemplateInternal: getSourceSensorWallpaper");
        if (TemplateApi.DefaultImpls.isThirdPartyWallpaper$default(this, null, 1, null)) {
            wallpaperInfo.setCurrentWallpaper(this.thirdPartyWallpaperServicePreview);
            wallpaperInfo.setCurrentSmallSensorWallpaper(this.thirdPartyWallpaperServicePreview);
        } else {
            boolean z3 = z2;
            BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
            wallpaperInfo.setCurrentWallpaper(BitmapUtil.fromFile$default(bitmapUtil, getCurrentWallpaperPathSafely(wallpaperInfo, z3 ? TemplateFilePathGenerator.TemplateFileType.WALLPAPER : TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME), 0, 0, null, 14, null));
            wallpaperInfo.setCurrentSmallSensorWallpaper(BitmapUtil.fromFile$default(bitmapUtil, this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN), 0, 0, null, 14, null));
        }
    }

    /* JADX WARN: Code duplicated, block: B:221:0x06f5  */
    public void updateCurrentTemplateWallpaperInfo(final CommonConfig config) {
        boolean z;
        String str;
        String str2;
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        String str3;
        String str4;
        String strGenerateCurrentTemplateFilePath;
        String cropSubject;
        String str5;
        String str6;
        String str7;
        boolean zIsNeedDark;
        WallpaperInfo wallpaperInfo;
        boolean z5;
        boolean zIsNeedDark2;
        WallpaperInfo wallpaperInfo2;
        String resourceType;
        String resourceType2;
        Intrinsics.checkNotNullParameter(config, "config");
        Process process = Process.INSTANCE;
        Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: isKeyguardeditorProcess = " + process.isKeyguardeditorProcess() + ",config = " + config);
        if (process.isKeyguardeditorProcess()) {
            WallpaperWhich wallpaperWhich = WallpaperWhich.LockWhich.INSTANCE;
            boolean zIsThirdPartyWallpaper = isThirdPartyWallpaper(wallpaperWhich);
            WallpaperWhich.HomeWhich homeWhich = WallpaperWhich.HomeWhich.INSTANCE;
            boolean zIsThirdPartyWallpaper2 = isThirdPartyWallpaper(homeWhich);
            config.getLockscreenInfo().setThirdPartyWallpaper(zIsThirdPartyWallpaper);
            config.getHomeInfo().setThirdPartyWallpaper(zIsThirdPartyWallpaper2);
            WallpaperController.Companion companion = WallpaperController.Companion;
            WallpaperController companion2 = companion.getInstance(this.context);
            boolean zSupportReversePlayback = companion2.supportReversePlayback(wallpaperWhich.getWhich());
            boolean zSupportReversePlayback2 = companion2.supportReversePlayback(homeWhich.getWhich());
            Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: isLockThirdPartyWallpaper=" + zIsThirdPartyWallpaper + " isHomeThirdPartyWallpaper=" + zIsThirdPartyWallpaper2 + ",config = " + config);
            String str8 = "image";
            if (zIsThirdPartyWallpaper) {
                String currentWallpaperPathSafely = getCurrentWallpaperPathSafely(config.getLockscreenInfo().getWallpaperInfo(), TemplateFilePathGenerator.TemplateFileType.WALLPAPER);
                TemplateConfig lockscreenInfo = config.getLockscreenInfo();
                WallpaperInfo wallpaperInfo3 = config.getLockscreenInfo().getWallpaperInfo();
                int magicType = wallpaperInfo3 != null ? wallpaperInfo3.getMagicType() : getCurrentMagicType(wallpaperWhich.getWhich());
                WallpaperInfo wallpaperInfo4 = config.getLockscreenInfo().getWallpaperInfo();
                String str9 = (wallpaperInfo4 == null || (resourceType2 = wallpaperInfo4.getResourceType()) == null) ? "image" : resourceType2;
                WallpaperInfo wallpaperInfo5 = config.getLockscreenInfo().getWallpaperInfo();
                int magicTypeHome = wallpaperInfo5 != null ? wallpaperInfo5.getMagicTypeHome() : 0;
                WallpaperInfo wallpaperInfo6 = config.getLockscreenInfo().getWallpaperInfo();
                lockscreenInfo.setWallpaperInfo(new WallpaperInfo(currentWallpaperPathSafely, null, null, null, null, magicType, null, null, str9, currentWallpaperPathSafely, false, null, false, null, false, null, companion2.isSupportLoop(wallpaperWhich.getWhich()), false, null, null, null, null, null, magicTypeHome, wallpaperInfo6 != null ? wallpaperInfo6.getEnableBlurHome() : false, 0, 0, null, false, Boolean.valueOf(zSupportReversePlayback), null, 1551825920, null));
                String currentWallpaperPathSafely2 = getCurrentWallpaperPathSafely(config.getHomeInfo().getWallpaperInfo(), TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
                HomeConfig homeInfo = config.getHomeInfo();
                WallpaperInfo wallpaperInfo7 = config.getHomeInfo().getWallpaperInfo();
                int magicType2 = wallpaperInfo7 != null ? wallpaperInfo7.getMagicType() : getCurrentMagicType(wallpaperWhich.getWhich());
                WallpaperInfo wallpaperInfo8 = config.getHomeInfo().getWallpaperInfo();
                String str10 = (wallpaperInfo8 == null || (resourceType = wallpaperInfo8.getResourceType()) == null) ? "image" : resourceType;
                WallpaperInfo wallpaperInfo9 = config.getLockscreenInfo().getWallpaperInfo();
                int magicTypeHome2 = wallpaperInfo9 != null ? wallpaperInfo9.getMagicTypeHome() : 0;
                WallpaperInfo wallpaperInfo10 = config.getLockscreenInfo().getWallpaperInfo();
                WallpaperInfo wallpaperInfo11 = new WallpaperInfo(currentWallpaperPathSafely2, null, null, null, null, magicType2, null, null, str10, currentWallpaperPathSafely, false, null, false, null, false, null, companion2.isSupportLoop(homeWhich.getWhich()), false, null, null, null, null, null, magicTypeHome2, wallpaperInfo10 != null ? wallpaperInfo10.getEnableBlurHome() : false, 0, 0, null, false, Boolean.valueOf(zSupportReversePlayback2), null, 1551825920, null);
                wallpaperInfo11.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
                homeInfo.setWallpaperInfo(wallpaperInfo11);
                Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: isThirdPartyWallpaper: " + config.getLockscreenInfo().getWallpaperInfo());
                return;
            }
            final WallpaperController companion3 = companion.getInstance(this.context);
            String lockWallpaperType = companion3.getLockWallpaperType();
            if (lockWallpaperType == null) {
                lockWallpaperType = "image";
            }
            String homeWallpaperType = companion3.getHomeWallpaperType();
            if (homeWallpaperType == null) {
                homeWallpaperType = "image";
            }
            boolean zDeleteCurrentIfDefault = deleteCurrentIfDefault(config);
            boolean zAreEqual = Intrinsics.areEqual(config.getLockscreenInfo().getLastUpdate(), "com.android.thememanager");
            boolean z6 = zDeleteCurrentIfDefault;
            Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: oldDeviceThemeApply = " + zAreEqual + ",config.lockscreenInfo.lastUpdate = " + config.getLockscreenInfo().getLastUpdate());
            boolean zNeedUpdateConfig = needUpdateConfig(config, lockWallpaperType);
            boolean zIsInvalidConfig = isInvalidConfig(config);
            boolean zNeedUpdateHome = needUpdateHome(config, homeWallpaperType);
            if (!z6 || zNeedUpdateConfig || UserGuidance.INSTANCE.isNewUser(this.context)) {
                z = zAreEqual;
                str = lockWallpaperType;
                str2 = homeWallpaperType;
                z2 = zIsInvalidConfig;
                z3 = zNeedUpdateHome;
            } else {
                homeWhich = homeWhich;
                companion = companion;
                zSupportReversePlayback = zSupportReversePlayback;
                zSupportReversePlayback2 = zSupportReversePlayback2;
                str8 = "image";
                z6 = z6;
                z = zAreEqual;
                z3 = zNeedUpdateHome;
                str2 = homeWallpaperType;
                str = lockWallpaperType;
                z2 = zIsInvalidConfig;
                TemplateApi.DefaultImpls.updateCurrentTemplate$default(this, config, "has delete", false, false, 12, null);
            }
            Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: needUpdate = " + zNeedUpdateConfig + ",isInvalidConfig = " + z2 + ",hasDelete = " + z6 + ",needUpdateHome = " + z3);
            if (zNeedUpdateConfig || z2 || z3 || z) {
                Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo begin");
                if (Intrinsics.areEqual(str, CategoryConstants.CATEGORY_MAML)) {
                    WallpaperInfo wallpaperInfo12 = config.getLockscreenInfo().getWallpaperInfo();
                    if ((wallpaperInfo12 != null ? wallpaperInfo12.getResourceType() : null) != null) {
                        WallpaperInfo wallpaperInfo13 = config.getLockscreenInfo().getWallpaperInfo();
                        String source = wallpaperInfo13 != null ? wallpaperInfo13.getSource() : null;
                        if (source != null && source.length() != 0 && FileUtil.isFileExist(this.filePathGenerator.generateFilePath(TemplateFilePathGenerator.TemplateFileType.THIRD_PARTY_THEME_PREVIEW))) {
                            Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: skip MaMl");
                            return;
                        }
                    }
                }
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
                Function2 function2 = new Function2() { // from class: com.miui.keyguard.editor.data.template.TemplateApiImpl$$ExternalSyntheticLambda29
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return TemplateApiImpl.updateCurrentTemplateWallpaperInfo$lambda$105(this.f$0, objectRef2, objectRef3, companion3, objectRef, config, (String) obj, ((Boolean) obj2).booleanValue());
                    }
                };
                String str11 = (String) function2.invoke(str, Boolean.TRUE);
                Boolean bool = Boolean.FALSE;
                Object objInvoke = function2.invoke(str2, bool);
                Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: originLockResourcePath = " + str11 + ",originHomeResourcePath = " + objInvoke);
                WallpaperInfo wallpaperInfo14 = config.getLockscreenInfo().getWallpaperInfo();
                String galleryContent = wallpaperInfo14 != null ? wallpaperInfo14.getGalleryContent() : null;
                WallpaperInfo wallpaperInfo15 = config.getLockscreenInfo().getWallpaperInfo();
                Boolean boolValueOf = wallpaperInfo15 != null ? Boolean.valueOf(wallpaperInfo15.getSupportSubject()) : null;
                int currentMagicType = getCurrentMagicType(wallpaperWhich.getWhich());
                int currentMagicType2 = getCurrentMagicType(homeWhich.getWhich());
                Wallpaper.Companion companion4 = Wallpaper.Companion;
                boolean zBooleanValue = companion4.isSupportBlurWallpaperType(str, currentMagicType) ? companion3.getCurBlurStatus().booleanValue() : false;
                MagicType magicType3 = MagicType.INSTANCE;
                Boolean bool2 = boolValueOf;
                if (magicType3.isDepth(Integer.valueOf(currentMagicType)) && zNeedUpdateConfig) {
                    Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: isDepth");
                    WallpaperInfo wallpaperInfo16 = config.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo16 != null) {
                        wallpaperInfo16.resetMagicType("for not support pkg");
                        Unit unit = Unit.INSTANCE;
                    }
                    str3 = null;
                    i = 0;
                    str4 = null;
                    z4 = false;
                } else if (magicType3.isDepthVideo(Integer.valueOf(currentMagicType))) {
                    boolean zIsMiuiDefaultWallpaper = companion3.isMiuiDefaultWallpaper();
                    Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: isDefaultConfig = " + zIsMiuiDefaultWallpaper + " , other isDefaultConfig = " + companion3.isMiuiDefaultWallpaper());
                    if (zIsMiuiDefaultWallpaper) {
                        objectRef2.element = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER);
                        objectRef3.element = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
                        cropSubject = companion3.getDefaultVideoDepthSubject(wallpaperWhich);
                        str5 = cropSubject;
                    } else {
                        WallpaperInfo wallpaperInfo17 = config.getLockscreenInfo().getWallpaperInfo();
                        objectRef2.element = wallpaperInfo17 != null ? wallpaperInfo17.getSource() : null;
                        WallpaperInfo wallpaperInfo18 = config.getHomeInfo().getWallpaperInfo();
                        if (wallpaperInfo18 == null || (strGenerateCurrentTemplateFilePath = wallpaperInfo18.getSource()) == null) {
                            strGenerateCurrentTemplateFilePath = this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
                        }
                        objectRef3.element = strGenerateCurrentTemplateFilePath;
                        WallpaperInfo wallpaperInfo19 = config.getLockscreenInfo().getWallpaperInfo();
                        cropSubject = wallpaperInfo19 != null ? wallpaperInfo19.getCropSubject() : null;
                        str5 = null;
                    }
                    Log.d("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: lockSource = " + objectRef2.element + ",homeSource = " + objectRef3.element + ",cropSubject = " + cropSubject);
                    str4 = cropSubject;
                    i = currentMagicType;
                    z4 = zBooleanValue;
                    str3 = str5;
                } else {
                    i = currentMagicType;
                    z4 = zBooleanValue;
                    str3 = null;
                    str4 = null;
                }
                Boolean bool3 = (zNeedUpdateConfig && str4 == null && !isGallerySetLockWallpaper()) ? bool : bool2;
                String str12 = "gallery";
                if (zNeedUpdateConfig || z) {
                    TemplateConfig lockscreenInfo2 = config.getLockscreenInfo();
                    boolean z7 = zSupportReversePlayback;
                    String str13 = (String) objectRef2.element;
                    if (str13 == null) {
                        str6 = str11 == null ? "" : str11;
                    } else {
                        str6 = str13;
                    }
                    boolean zBooleanValue2 = bool3 != null ? bool3.booleanValue() : true;
                    SensorWallpaperPrams sensorWallpaperPrams = (SensorWallpaperPrams) objectRef.element;
                    boolean zIsSupportLoop = companion2.isSupportLoop(wallpaperWhich.getWhich());
                    WallpaperInfo wallpaperInfo20 = config.getLockscreenInfo().getWallpaperInfo();
                    int magicTypeHome3 = wallpaperInfo20 != null ? wallpaperInfo20.getMagicTypeHome() : 0;
                    WallpaperInfo wallpaperInfo21 = config.getLockscreenInfo().getWallpaperInfo();
                    boolean enableBlurHome = wallpaperInfo21 != null ? wallpaperInfo21.getEnableBlurHome() : false;
                    WallpaperInfo wallpaperInfo22 = config.getLockscreenInfo().getWallpaperInfo();
                    int enableDoodle = wallpaperInfo22 != null ? wallpaperInfo22.getEnableDoodle() : 0;
                    WallpaperInfo wallpaperInfo23 = config.getLockscreenInfo().getWallpaperInfo();
                    WallpaperInfo wallpaperInfo24 = new WallpaperInfo(str6, str3, null, null, null, i, str4, null, str, str11, zBooleanValue2, sensorWallpaperPrams, false, galleryContent, z4, null, zIsSupportLoop, false, null, null, null, null, null, magicTypeHome3, enableBlurHome, enableDoodle, 0, wallpaperInfo23 != null ? wallpaperInfo23.getExtraInfo() : null, false, Boolean.valueOf(z7), null, 1417580544, null);
                    String str14 = str;
                    wallpaperInfo24.setInfoType(WallpaperInfoType.LOCK_WALLPAPER);
                    WallpaperInfo wallpaperInfo25 = config.getLockscreenInfo().getWallpaperInfo();
                    wallpaperInfo24.setMattingMode(wallpaperInfo25 != null ? wallpaperInfo25.getMattingMode() : 0);
                    lockscreenInfo2.setWallpaperInfo(wallpaperInfo24);
                    if (magicType3.isDepthVideo(Integer.valueOf(i))) {
                        WallpaperInfo wallpaperInfo26 = config.getLockscreenInfo().getWallpaperInfo();
                        if (wallpaperInfo26 != null) {
                            WallpaperInfo wallpaperInfo27 = config.getLockscreenInfo().getWallpaperInfo();
                            wallpaperInfo26.setDepthVideo(wallpaperInfo27 != null ? wallpaperInfo27.getOriginResourcePath() : null);
                            Unit unit2 = Unit.INSTANCE;
                        }
                        WallpaperInfo wallpaperInfo28 = config.getLockscreenInfo().getWallpaperInfo();
                        if (wallpaperInfo28 != null) {
                            wallpaperInfo28.setDepthVideo(true);
                            Unit unit3 = Unit.INSTANCE;
                        }
                        WallpaperInfo wallpaperInfo29 = config.getLockscreenInfo().getWallpaperInfo();
                        if (wallpaperInfo29 != null) {
                            wallpaperInfo29.setVideoType(1);
                            Unit unit4 = Unit.INSTANCE;
                        }
                    }
                    WallpaperInfo wallpaperInfo30 = config.getLockscreenInfo().getWallpaperInfo();
                    if (TextUtils.isEmpty(wallpaperInfo30 != null ? wallpaperInfo30.getOriginResourcePath() : null)) {
                        str7 = str8;
                        if ((Intrinsics.areEqual(str14, str7) || Intrinsics.areEqual(str14, "gallery")) && (wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo()) != null) {
                            wallpaperInfo.setOriginResourcePath(companion.getInstance(this.context).getWallpaperPath(str14, 2, false, false));
                            Unit unit5 = Unit.INSTANCE;
                        }
                    } else {
                        str7 = str8;
                    }
                    WallpaperInfo wallpaperInfo31 = config.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo31 != null) {
                        if (!companion4.isSupportDarkWallpaperType(str14)) {
                            WallpaperInfo wallpaperInfo32 = config.getLockscreenInfo().getWallpaperInfo();
                            if (wallpaperInfo32 != null) {
                                zIsNeedDark = wallpaperInfo32.isNeedDark();
                            } else {
                                zIsNeedDark = false;
                            }
                        } else if (!companion3.curWallpaperIsSupportDark(wallpaperWhich.getWhich()) || companion4.isSystemWallpaper(str11)) {
                            zIsNeedDark = false;
                        } else {
                            zIsNeedDark = true;
                        }
                        wallpaperInfo31.setNeedDark(zIsNeedDark);
                        Unit unit6 = Unit.INSTANCE;
                    }
                    if (z) {
                        config.getLockscreenInfo().setLastUpdate("");
                        Bitmap lockWallpaperPreview = companion3.getLockWallpaperPreview(this.context);
                        if (lockWallpaperPreview != null) {
                            BitmapUtil.toWebp$default(BitmapUtil.INSTANCE, lockWallpaperPreview, this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER), false, 4, null);
                        }
                    }
                    Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo - lock : " + config.getLockscreenInfo().getWallpaperInfo());
                } else {
                    z3 = z3;
                    str7 = str8;
                    str12 = "gallery";
                }
                if (z3) {
                    HomeConfig homeInfo2 = config.getHomeInfo();
                    String str15 = (String) objectRef3.element;
                    String str16 = (str15 == null && (str15 = (String) objInvoke) == null) ? "" : str15;
                    String str17 = (String) objInvoke;
                    SensorWallpaperPrams sensorWallpaperPrams2 = (SensorWallpaperPrams) objectRef.element;
                    WallpaperInfo wallpaperInfo33 = config.getHomeInfo().getWallpaperInfo();
                    String galleryContent2 = wallpaperInfo33 != null ? wallpaperInfo33.getGalleryContent() : null;
                    WallpaperInfo wallpaperInfo34 = config.getHomeInfo().getWallpaperInfo();
                    int magicTypeHome4 = wallpaperInfo34 != null ? wallpaperInfo34.getMagicTypeHome() : 0;
                    WallpaperInfo wallpaperInfo35 = config.getHomeInfo().getWallpaperInfo();
                    boolean enableBlurHome2 = wallpaperInfo35 != null ? wallpaperInfo35.getEnableBlurHome() : false;
                    WallpaperInfo wallpaperInfo36 = config.getHomeInfo().getWallpaperInfo();
                    int enableDoodle2 = wallpaperInfo36 != null ? wallpaperInfo36.getEnableDoodle() : 0;
                    WallpaperInfo wallpaperInfo37 = config.getHomeInfo().getWallpaperInfo();
                    String str18 = str2;
                    WallpaperInfo wallpaperInfo38 = new WallpaperInfo(str16, str3, null, null, null, currentMagicType2, null, null, str18, str17, false, sensorWallpaperPrams2, false, galleryContent2, false, null, companion2.isSupportLoop(homeWhich.getWhich()), false, null, null, null, null, null, magicTypeHome4, enableBlurHome2, enableDoodle2, 0, wallpaperInfo37 != null ? wallpaperInfo37.getExtraInfo() : null, false, Boolean.valueOf(zSupportReversePlayback2), null, 1417580544, null);
                    wallpaperInfo38.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
                    homeInfo2.setWallpaperInfo(wallpaperInfo38);
                    if (magicType3.isDepthVideo(Integer.valueOf(currentMagicType2))) {
                        WallpaperInfo wallpaperInfo39 = config.getHomeInfo().getWallpaperInfo();
                        if (wallpaperInfo39 != null) {
                            WallpaperInfo wallpaperInfo40 = config.getHomeInfo().getWallpaperInfo();
                            wallpaperInfo39.setDepthVideo(wallpaperInfo40 != null ? wallpaperInfo40.getOriginResourcePath() : null);
                            Unit unit7 = Unit.INSTANCE;
                        }
                        WallpaperInfo wallpaperInfo41 = config.getHomeInfo().getWallpaperInfo();
                        if (wallpaperInfo41 != null) {
                            wallpaperInfo41.setDepthVideo(true);
                            Unit unit8 = Unit.INSTANCE;
                        }
                        WallpaperInfo wallpaperInfo42 = config.getHomeInfo().getWallpaperInfo();
                        if (wallpaperInfo42 != null) {
                            wallpaperInfo42.setVideoType(1);
                            Unit unit9 = Unit.INSTANCE;
                        }
                    }
                    WallpaperInfo wallpaperInfo43 = config.getHomeInfo().getWallpaperInfo();
                    if (!TextUtils.isEmpty(wallpaperInfo43 != null ? wallpaperInfo43.getOriginResourcePath() : null) || (!(Intrinsics.areEqual(str18, str7) || Intrinsics.areEqual(str18, str12)) || (wallpaperInfo2 = config.getHomeInfo().getWallpaperInfo()) == null)) {
                        z5 = true;
                        zIsNeedDark2 = false;
                    } else {
                        z5 = true;
                        zIsNeedDark2 = false;
                        wallpaperInfo2.setOriginResourcePath(companion.getInstance(this.context).getWallpaperPath(str18, 1, false, false));
                        Unit unit10 = Unit.INSTANCE;
                    }
                    WallpaperInfo wallpaperInfo44 = config.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo44 != null) {
                        if (!companion4.isSupportDarkWallpaperType(str18)) {
                            WallpaperInfo wallpaperInfo45 = config.getHomeInfo().getWallpaperInfo();
                            if (wallpaperInfo45 != null) {
                                zIsNeedDark2 = wallpaperInfo45.isNeedDark();
                            }
                        } else if (companion3.curWallpaperIsSupportDark(homeWhich.getWhich()) && !companion4.isSystemWallpaper(str17)) {
                            zIsNeedDark2 = z5;
                        }
                        wallpaperInfo44.setNeedDark(zIsNeedDark2);
                        Unit unit11 = Unit.INSTANCE;
                    }
                    Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo - home:" + config.getHomeInfo().getWallpaperInfo());
                }
                TemplateApi.DefaultImpls.updateCurrentTemplate$default(this, config, "update wallpaper info", false, false, 12, null);
                if (process.isKeyguardeditorProcess()) {
                    UserGuidance userGuidance = UserGuidance.INSTANCE;
                    if (userGuidance.isNewUser(this.context)) {
                        Log.d("Keyguard-Editor-TemplateApiImpl", "mark new user");
                        userGuidance.markNotNewUser(this.context);
                    }
                }
            } else if (process.isKeyguardeditorProcess()) {
                UserGuidance userGuidance2 = UserGuidance.INSTANCE;
                if (userGuidance2.isNewUser(this.context)) {
                    Log.i("Keyguard-Editor-TemplateApiImpl", "updateCurrentTemplateWallpaperInfo: new user, clear positionInfo");
                    userGuidance2.markNotNewUser(this.context);
                    WallpaperInfo wallpaperInfo46 = config.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo46 != null) {
                        wallpaperInfo46.setPositionInfo(null);
                        Unit unit12 = Unit.INSTANCE;
                    }
                    WallpaperInfo wallpaperInfo47 = config.getHomeInfo().getWallpaperInfo();
                    if (wallpaperInfo47 != null) {
                        wallpaperInfo47.setPositionInfo(null);
                        Unit unit13 = Unit.INSTANCE;
                    }
                    reloadDataIfClearData(config.getLockscreenInfo().getWallpaperInfo());
                    TemplateApi.DefaultImpls.updateCurrentTemplate$default(this, config, "new user", false, false, 12, null);
                }
            }
            Unit unit14 = Unit.INSTANCE;
        }
    }

    public final void updateOuterWallpaperInfo(CommonConfig commonConfig, WallpaperTypeInfo wallpaperTypeInfo, String str) {
        WallpaperInfo wallpaperInfo;
        WallpaperController.Companion companion = WallpaperController.Companion;
        String type = wallpaperTypeInfo.getType();
        SensorWallpaperPrams sensorWallpaperPrams = wallpaperTypeInfo.getSensorWallpaperPrams();
        String resourcePath = wallpaperTypeInfo.getResourcePath();
        SensorWallpaperPrams sensorWallpaperPrams2 = wallpaperTypeInfo.getSensorWallpaperPrams();
        String outerVideoPreViewPath = sensorWallpaperPrams2 != null ? sensorWallpaperPrams2.getOuterVideoPreViewPath() : null;
        SensorWallpaperPrams sensorWallpaperPrams3 = wallpaperTypeInfo.getSensorWallpaperPrams();
        String outerVideoPath = sensorWallpaperPrams3 != null ? sensorWallpaperPrams3.getOuterVideoPath() : null;
        SensorWallpaperPrams sensorWallpaperPrams4 = wallpaperTypeInfo.getSensorWallpaperPrams();
        String smallThumbnailPath = sensorWallpaperPrams4 != null ? sensorWallpaperPrams4.getSmallThumbnailPath() : null;
        SensorWallpaperPrams sensorWallpaperPrams5 = wallpaperTypeInfo.getSensorWallpaperPrams();
        WallpaperController.Companion.fillQ18SensorOuterVideoIfNeeded$default(companion, type, sensorWallpaperPrams, null, CollectionsKt.listOf((Object[]) new String[]{resourcePath, outerVideoPreViewPath, outerVideoPath, smallThumbnailPath, sensorWallpaperPrams5 != null ? sensorWallpaperPrams5.getThumbnailPath() : null}), 4, null);
        if ((Intrinsics.areEqual(str, "all") || Intrinsics.areEqual(str, "lock")) && (wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo()) != null) {
            wallpaperInfo.setCurrentWallpaper(wallpaperTypeInfo.getBitmap());
            SensorWallpaperPrams sensorWallpaperPrams6 = wallpaperTypeInfo.getSensorWallpaperPrams();
            wallpaperInfo.setCurrentSmallSensorWallpaper(sensorWallpaperPrams6 != null ? sensorWallpaperPrams6.getCurrentSmallSensorWallpaper() : null);
            String resourcePath2 = wallpaperTypeInfo.getResourcePath();
            if (resourcePath2 == null) {
                resourcePath2 = "";
            }
            wallpaperInfo.setSource(resourcePath2);
            wallpaperInfo.setMask(wallpaperTypeInfo.getMaskPath());
            wallpaperInfo.setSubject(null);
            wallpaperInfo.setFrontBack(null);
            wallpaperInfo.setCroppedFrontBack(null);
            wallpaperInfo.setMagicType(0);
            wallpaperInfo.setCropSubject(null);
            wallpaperInfo.setPositionInfo(null);
            wallpaperInfo.setResourceType(wallpaperTypeInfo.getType());
            wallpaperInfo.setOriginResourcePath(wallpaperTypeInfo.getResourcePath());
            wallpaperInfo.setSupportSubject(wallpaperTypeInfo.getSupportSubject());
            wallpaperInfo.setSensorWallpaperPrams(wallpaperTypeInfo.getSensorWallpaperPrams());
            wallpaperInfo.setNeedDark(wallpaperTypeInfo.isNeedDark());
            wallpaperInfo.setEnableBlur(false);
            Boolean boolIsSupportLoop = wallpaperTypeInfo.isSupportLoop();
            wallpaperInfo.setSupportLoop(boolIsSupportLoop != null ? boolIsSupportLoop.booleanValue() : true);
            Integer videoType = wallpaperTypeInfo.getVideoType();
            wallpaperInfo.setVideoType(videoType != null ? videoType.intValue() : 0);
            wallpaperInfo.setImagePath(wallpaperTypeInfo.getImagePath());
            wallpaperInfo.setAiGenerated(wallpaperTypeInfo.isAiGenerated());
            wallpaperInfo.setDirectGenerate(wallpaperTypeInfo.isDirectGenerate());
            wallpaperInfo.setExtraInfo(new ExtraInfo(wallpaperTypeInfo.getExtraPath(), null, 2, null));
            wallpaperInfo.setSupportFullScreenAod(wallpaperTypeInfo.isSupportFullScreenAod());
            if (wallpaperTypeInfo.getSupportSubject() && wallpaperTypeInfo.getMattingMode() == 2) {
                wallpaperInfo.setSubjectBitmap(TemplatePreviewImageComposer.INSTANCE.composeSubjectImage(wallpaperInfo, false));
                if (wallpaperInfo.getSubjectBitmap() != null) {
                    wallpaperInfo.setMagicType(MagicType.TYPE_DEPTH);
                }
            }
            TemplateConfig lockscreenInfo = commonConfig.getLockscreenInfo();
            lockscreenInfo.setDarkenWallpaper(null);
            lockscreenInfo.setWallpaperInfoForVideo(null);
            lockscreenInfo.setThirdPartyWallpaper(false);
        }
        if (Intrinsics.areEqual(str, "all") || Intrinsics.areEqual(str, "home")) {
            WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo2 != null) {
                wallpaperInfo2.setCurrentWallpaper(wallpaperTypeInfo.getBitmap());
                SensorWallpaperPrams sensorWallpaperPrams7 = wallpaperTypeInfo.getSensorWallpaperPrams();
                wallpaperInfo2.setCurrentSmallSensorWallpaper(sensorWallpaperPrams7 != null ? sensorWallpaperPrams7.getCurrentSmallSensorWallpaper() : null);
                String resourcePath3 = wallpaperTypeInfo.getResourcePath();
                wallpaperInfo2.setSource(resourcePath3 != null ? resourcePath3 : "");
                wallpaperInfo2.setMask(wallpaperTypeInfo.getMaskPath());
                wallpaperInfo2.setSubject(null);
                wallpaperInfo2.setFrontBack(null);
                wallpaperInfo2.setCroppedFrontBack(null);
                wallpaperInfo2.setMagicType(0);
                wallpaperInfo2.setCropSubject(null);
                wallpaperInfo2.setPositionInfo(null);
                wallpaperInfo2.setResourceType(wallpaperTypeInfo.getType());
                wallpaperInfo2.setOriginResourcePath(wallpaperTypeInfo.getResourcePath());
                wallpaperInfo2.setSupportSubject(false);
                wallpaperInfo2.setSensorWallpaperPrams(wallpaperTypeInfo.getSensorWallpaperPrams());
                wallpaperInfo2.setNeedDark(wallpaperTypeInfo.isNeedDark());
                wallpaperInfo2.setEnableBlur(false);
                Boolean boolIsSupportLoop2 = wallpaperTypeInfo.isSupportLoop();
                wallpaperInfo2.setSupportLoop(boolIsSupportLoop2 != null ? boolIsSupportLoop2.booleanValue() : true);
                Integer videoType2 = wallpaperTypeInfo.getVideoType();
                wallpaperInfo2.setVideoType(videoType2 != null ? videoType2.intValue() : 0);
                wallpaperInfo2.setExtraInfo(null);
                wallpaperInfo2.setSupportFullScreenAod(wallpaperTypeInfo.isSupportFullScreenAod());
            }
            HomeConfig homeInfo = commonConfig.getHomeInfo();
            homeInfo.setDarkenWallpaper(null);
            homeInfo.setWallpaperInfoForVideo(null);
            homeInfo.setThirdPartyWallpaper(false);
        }
        commonConfig.getHomeInfo().setFollowStatus(Intrinsics.areEqual(str, "all") ? 2 : 0);
        processDepthVideo(str, wallpaperTypeInfo, commonConfig);
    }

    public final void updateVideoPath(WallpaperInfo wallpaperInfo, String str) {
        WallpaperController companion = WallpaperController.Companion.getInstance(this.context);
        if (TextUtils.isEmpty(str)) {
            updateVideoPath(companion, wallpaperInfo);
            return;
        }
        File file = str != null ? new File(str) : null;
        if (file == null || !file.exists()) {
            updateVideoPath(companion, wallpaperInfo);
        }
    }

    public final void updateVideoPath(WallpaperController wallpaperController, WallpaperInfo wallpaperInfo) {
        String videoWallpaperOriginPath$default = IWallpaperController.DefaultImpls.getVideoWallpaperOriginPath$default(wallpaperController, null, 1, null);
        File file = videoWallpaperOriginPath$default != null ? new File(videoWallpaperOriginPath$default) : null;
        if (file != null && file.exists()) {
            if (wallpaperInfo != null) {
                wallpaperInfo.setOriginResourcePath(videoWallpaperOriginPath$default);
            }
            if (wallpaperInfo == null || !wallpaperInfo.isDepthVideo()) {
                return;
            }
            wallpaperInfo.setDepthVideo(videoWallpaperOriginPath$default);
            return;
        }
        if (wallpaperInfo != null) {
            wallpaperInfo.resetMagicType("for file not exists");
        }
        if (wallpaperInfo != null) {
            wallpaperInfo.setResourceType("image");
        }
        if (wallpaperInfo != null) {
            wallpaperInfo.setOriginResourcePath(wallpaperInfo.getSource());
        }
        if (wallpaperInfo != null) {
            wallpaperInfo.setDepthVideo("");
        }
        if (wallpaperInfo != null) {
            wallpaperInfo.setDepthVideo(false);
        }
    }

    @Override // com.miui.keyguard.editor.data.template.TemplateApi
    public void writeTemplateInfo(String jsonStr, String tag) {
        Intrinsics.checkNotNullParameter(jsonStr, "jsonStr");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Log.d("Keyguard-Editor-TemplateApiImpl", "writeTemplateInfo: tag=" + tag);
        Settings.Secure.putString(this.context.getContentResolver(), "constant_template_editor_info", jsonStr);
        this.context.getContentResolver().notifyChange(Settings.Secure.getUriFor("constant_template_editor_info"), (ContentObserver) null, 32768);
    }
}

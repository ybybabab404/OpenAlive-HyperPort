package com.miui.keyguard.editor.edit.base;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelFileDescriptor;
import android.os.Trace;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.lifecycle.LifecycleOwner;
import com.miui.aod.category.CategoryConstants;
import com.miui.clock.MiuiClockController;
import com.miui.clock.MiuiClockView;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockBean;
import com.miui.fastplayer.FastPlayer;
import com.miui.keyguard.editor.CommonEditorActivity;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.ActivityUIAdapter;
import com.miui.keyguard.editor.base.ExitAnimationPerformer;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.migration.AllInOneClockMigration;
import com.miui.keyguard.editor.data.preset.FilterGroup;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterData;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.data.preset.WallpaperFilterKt;
import com.miui.keyguard.editor.data.template.BitmapTempStore;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateChangeRecorder;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.aiwapper.IAIWallPaperChangeListener;
import com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.ColorPickerCache;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.style.RealtimeStyleEditor;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.GestureManager;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.VideoTextureView;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperChangedListener;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.view.EditorLoadingView;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.BitmapCreator;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.MiuiSettingsUtils;
import com.miui.keyguard.editor.utils.TalkBackUtil;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.segment.ImageSegmentHandler;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.utils.segment.VideoSegmentServiceListener;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor;
import com.miui.keyguard.editor.video.VideoCheckUtils;
import com.miui.keyguard.editor.view.AlertDialogBuilder;
import com.miui.keyguard.editor.view.AlertDialogsKtKt;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.CenterCropDrawable;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.KgVisualCheckBox;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.view.WallpaperFilterView;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.folme.AnimatedTarget;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.taiyi.api.videosegment.FastPlayerListener;
import com.xiaomi.taiyi.api.videosegment.VideoSegmentAbility;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.app.AlertDialog;
import miuix.bottomsheet.BottomSheetModal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public abstract class BaseTemplateView extends FrameLayout implements EditorActivity.NavigationActionListener, EditCallback, WallpaperCallback, TemplateApplier.ApplyCallback, ExitAnimationPerformer, MiuiClockController.MiuiClockInfoListener, CombineEditListener, IAIWallPaperChangeListener {

    @NotNull
    public static final Companion Companion = new Companion(null);
    public static boolean edited;
    public KgVisualCheckBox aiGenerateBtn;
    public AlertDialog alertDialog;
    public boolean allowToClickDynamicSwitchBtn;
    public ExitAnimationPerformer applyAnimationPerformer;
    public TemplateApplier.ApplyCallback applyTransition;
    public boolean applying;
    public AutoColorPicker autoColorPicker;
    public boolean cancelDialogShow;
    public final Function0 checkRunnable;
    public ColorModes colorModes;
    public ColorPickHandler colorPickHandler;
    public CombineEditListener combineEditListener;
    public AutoBottomSheet customFontColorBottomSheet;
    public DepthPresetDownloadButton depthPresetDownloadBtn;
    public final BaseTemplateView$depthVideoAiServiceListener$1 depthVideoAiServiceListener;
    public final DepthFirstFrameCallBack depthVideoFirstFrameCallBack;
    public boolean depthVideoFirstFrameReady;
    public View depthVideoForegroundLayer;
    public AlertDialog depthVideoProgressDialog;
    public TextView depthVideoProgressDialogPercent;
    public ProgressBar depthVideoProgressDialogProgress;
    public int displayClockEffect;
    public DynamicSwitchButton dynamicSwitchBtn;
    public final DynamicSwitchCallback dynamicSwitchCallback;
    public EditFragment editFragment;
    public EditModeChangedListener editModeChangedListener;
    public boolean editable;
    public boolean enableOriginWallpaperPlaceholder;
    public FastPlayerManager fastPlayerManager;
    public KgVisualCheckBox filterBtn;
    public FollowSystemListener followSystemListener;
    public final FontStyleEditCallback fontStyleEditCallback;
    public int from;
    public KgVisualCheckBox hierarchyBtn;
    public boolean hierarchyEnable;
    public View hierarchyLayer;
    public View homeWallpaperLayer;
    public boolean initFinish;
    public boolean isAnimLayer;
    public boolean isAodDialogTemplate;
    public Boolean isAutoDownload;
    public boolean isCallingFromKeyguard;
    public boolean isChangedTimeMagic;
    public boolean isCheckBtn;
    public boolean isClockTemplateChanged;
    public boolean isDarkWallpaper;
    public boolean isDepthProgressInterrupted;
    public boolean isDismissTemplateTitle;
    public boolean isDualClock;
    public boolean isEditHierarchy;
    public boolean isEditTransition;
    public boolean isEnterSecond;
    public boolean isExit;
    public boolean isGalleryOpened;
    public boolean isHistoryTemplate;
    public boolean isPositionInfoChanged;
    public Boolean isPreset;
    public boolean isStopped;
    public boolean isTemplateSwitching;
    public boolean isWallpaperChanged;
    public volatile EditorLoadingView loadingView;
    public View maskView;
    public BaseClockView miuiClockView;
    public Consumer navigationCallback;
    public boolean needRemoveBackground;
    public boolean needResetBackground;
    public final FirstFrameCallBack normalVideoFirstFrame;
    public Function0 onCancelDialogNegativeClick;
    public Function1 onWallpaperDarkResultReadyListener;
    public Function0 onceWallpaperReadyListener;
    public CommonConfig originConfig;
    public int originVideoStatus;
    public boolean pendingDismissAfterSwitch;
    public AtomicBoolean pickColorFirst;
    public View presetButtonGroup;
    public int previousMagicType;
    public String previousTemplateId;
    public TemplateButtonBar primaryButtonBar;
    public long produceDepthVideoTime;
    public FastPlayer produceVideoFastPlayer;
    public final Runnable removeBackgroundRunnable;
    public boolean shouldSetColorSelectionToAuto;
    public int state;
    public boolean successUpdateClockView;
    public final TemplateApi templateApi;
    public CommonConfig templateConfig;
    public final Set templateReadyIds;
    public long templateSource;
    public TextView templateTitle;
    public final TimeUpdateReceiver timeUpdateReceiver;
    public FrameLayout topButtonBar;
    public String trackId;
    public volatile Bitmap transitionBackground;
    public boolean videoProcessedByAI;
    public VideoSegmentAbility videoSegmentAbility;
    public final WallpaperChangedListener wallpaperChangedListener;
    public Job wallpaperDarkResultJob;
    public WallpaperEditor wallpaperEditor;
    public AutoBottomSheet wallpaperFilterAutoBottomSheet;
    public int wallpaperFilterBottomSheetHeight;
    public WallpaperFilterView wallpaperFilterView;
    public boolean wallpaperHasChanged;
    public View wallpaperLayer;
    public WallpaperEditor.WallpaperPickerLauncher wallpaperPickerLauncher;
    public HandlerThread worker;

    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    public final class TimeUpdateReceiver extends BroadcastReceiver {
        public TimeUpdateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent != null ? intent.getAction() : null;
            if (Intrinsics.areEqual(action, "android.intent.action.TIME_TICK") || Intrinsics.areEqual(action, "android.intent.action.TIME_SET")) {
                BaseTemplateView.this.updateEditorTime();
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$checkColorModesChanged$1, reason: invalid class name */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$checkColorModesChanged$1", f = "BaseTemplateView.kt", l = {3567}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Bitmap $bitmap;
        public final /* synthetic */ boolean $cropped;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Bitmap bitmap, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$bitmap = bitmap;
            this.$cropped = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new AnonymousClass1(this.$bitmap, this.$cropped, continuation);
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
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                BaseTemplateView$checkColorModesChanged$1$data$1 baseTemplateView$checkColorModesChanged$1$data$1 = new BaseTemplateView$checkColorModesChanged$1$data$1(BaseTemplateView.this, this.$bitmap, this.$cropped, null);
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, baseTemplateView$checkColorModesChanged$1$data$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ColorModes colorModes = (ColorModes) obj;
            if (colorModes != null && !Intrinsics.areEqual(colorModes, BaseTemplateView.this.getColorModes())) {
                BaseTemplateView.this.setColorModes(colorModes);
                BaseTemplateView.this.onColorModesChanged(colorModes);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$checkVideoValid$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$checkVideoValid$1", f = "BaseTemplateView.kt", l = {1672}, m = "invokeSuspend")
    public static final class C01011 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ FastPlayerManager.VideoMetaData $metaData;
        public final /* synthetic */ String $originResourcePath;
        public Object L$0;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$checkVideoValid$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: BaseTemplateView.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$checkVideoValid$1$1", f = "BaseTemplateView.kt", l = {1673}, m = "invokeSuspend")
        public static final class C00231 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Ref.ObjectRef $isValidVideoSource;
            public final /* synthetic */ FastPlayerManager.VideoMetaData $metaData;
            public final /* synthetic */ String $originResourcePath;
            public Object L$0;
            public int label;
            public final /* synthetic */ BaseTemplateView this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00231(Ref.ObjectRef objectRef, BaseTemplateView baseTemplateView, String str, FastPlayerManager.VideoMetaData videoMetaData, Continuation continuation) {
                super(2, continuation);
                this.$isValidVideoSource = objectRef;
                this.this$0 = baseTemplateView;
                this.$originResourcePath = str;
                this.$metaData = videoMetaData;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00231(this.$isValidVideoSource, this.this$0, this.$originResourcePath, this.$metaData, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00231) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Ref.ObjectRef objectRef;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Ref.ObjectRef objectRef2 = this.$isValidVideoSource;
                    Context context = this.this$0.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    VideoCheckForDepthInterceptor videoCheckForDepthInterceptor = new VideoCheckForDepthInterceptor(context, false, false, 6, null);
                    Uri uri = Uri.parse(this.$originResourcePath);
                    int width = this.$metaData.getWidth();
                    int height = this.$metaData.getHeight();
                    int duration = this.$metaData.getDuration();
                    int rotation = this.$metaData.getRotation();
                    float frameRate = this.$metaData.getFrameRate();
                    this.L$0 = objectRef2;
                    this.label = 1;
                    Object objCheckVideo = videoCheckForDepthInterceptor.checkVideo(uri, width, height, duration, rotation, frameRate, this);
                    if (objCheckVideo == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    obj = objCheckVideo;
                    objectRef = objectRef2;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    objectRef = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                objectRef.element = obj;
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01011(String str, FastPlayerManager.VideoMetaData videoMetaData, Continuation continuation) {
            super(2, continuation);
            this.$originResourcePath = str;
            this.$metaData = videoMetaData;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new C01011(this.$originResourcePath, this.$metaData, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01011) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.ObjectRef objectRef;
            BaseClockView baseClockView;
            EditFragmentViewModel viewModel;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                CoroutineDispatcher io2 = Dispatchers.getIO();
                C00231 c00231 = new C00231(objectRef2, BaseTemplateView.this, this.$originResourcePath, this.$metaData, null);
                this.L$0 = objectRef2;
                this.label = 1;
                if (BuildersKt.withContext(io2, c00231, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            if (!Intrinsics.areEqual(objectRef.element, VideoCheckUtils.CheckVideoResult.Success.INSTANCE) && !Intrinsics.areEqual(objectRef.element, VideoCheckUtils.CheckVideoResult.Compression.INSTANCE) && DeviceUtil.INSTANCE.isSupportHierarchyDevices() && (baseClockView = (BaseClockView) BaseTemplateView.this.getClockLayer()) != null && baseClockView.supportHierarchy() && (viewModel = EditFragmentViewModel.Companion.getViewModel(BaseTemplateView.this.getEditFragment())) != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$getCurCropWallpaper$2, reason: invalid class name */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$getCurCropWallpaper$2", f = "BaseTemplateView.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            GestureManager gestureManager;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            WallpaperInfo currentWallpaperInfo = BaseTemplateView.this.getCurrentWallpaperInfo();
            Bitmap currentWallpaper = currentWallpaperInfo != null ? currentWallpaperInfo.getCurrentWallpaper() : null;
            IWallpaperLayer wallpaperLayer = BaseTemplateView.this.getWallpaperLayer();
            RectF positionRect = (wallpaperLayer == null || (gestureManager = wallpaperLayer.getGestureManager()) == null) ? null : gestureManager.getPositionRect(currentWallpaper);
            if (currentWallpaper == null || positionRect == null) {
                return null;
            }
            return BitmapUtil.INSTANCE.cropBitmap(currentWallpaper, (int) positionRect.left, (int) positionRect.right, (int) positionRect.top, (int) positionRect.bottom);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$handleProduceVideoCallback$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$handleProduceVideoCallback$1", f = "BaseTemplateView.kt", l = {}, m = "invokeSuspend")
    public static final class C01021 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ int $code;
        public final /* synthetic */ int $mode;
        public final /* synthetic */ String $path;
        public final /* synthetic */ long $pts;
        public int label;
        public final /* synthetic */ BaseTemplateView this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01021(int i, BaseTemplateView baseTemplateView, int i2, String str, long j, Continuation continuation) {
            super(2, continuation);
            this.$mode = i;
            this.this$0 = baseTemplateView;
            this.$code = i2;
            this.$path = str;
            this.$pts = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01021(this.$mode, this.this$0, this.$code, this.$path, this.$pts, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01021) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            FastPlayer fastPlayer;
            FastPlayer fastPlayer2;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            int i = this.$mode;
            if (i == 11) {
                this.this$0.showProduceDepthVideoProgressDialog(this.$code);
            } else if (i == 12 && this.$code == 1) {
                this.this$0.onEdited(31, this.$path);
                VideoSegmentAbility videoSegmentAbility = this.this$0.videoSegmentAbility;
                if ((videoSegmentAbility == null || !videoSegmentAbility.isSupport()) && (fastPlayer2 = this.this$0.produceVideoFastPlayer) != null) {
                    fastPlayer2.release();
                }
                CommonConfig templateConfig = this.this$0.getTemplateConfig();
                if (templateConfig != null) {
                    BaseTemplateView baseTemplateView = this.this$0;
                    long jCurrentTimeMillis = System.currentTimeMillis() - baseTemplateView.produceDepthVideoTime;
                    TrackHelper trackHelper = TrackHelper.INSTANCE;
                    Context context = baseTemplateView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    String trackId = baseTemplateView.getTrackId();
                    trackHelper.trackProductDepthVideo(context, templateConfig, trackId == null ? "" : trackId, "成功", jCurrentTimeMillis);
                }
                this.this$0.setVideoProcessedByAI(true);
            } else if (i == 12 && this.$code == 0) {
                this.this$0.interruptProduceDepthVideoProgressDialog(this.$path);
                long j = this.$pts;
                if (j != 1000) {
                    if (this.this$0.isAiDepthNotSupported(j)) {
                        this.this$0.updateHierarchyStatus(0);
                        ToastUtil toastUtil = ToastUtil.INSTANCE;
                        Context context2 = this.this$0.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                        toastUtil.showText(context2, R.string.kg_hierarchy_not_support_wallpaper_tip);
                    } else {
                        ToastUtil toastUtil2 = ToastUtil.INSTANCE;
                        Context context3 = this.this$0.getContext();
                        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                        toastUtil2.showText(context3, R.string.kg_depth_produce_video_failed);
                    }
                    CommonConfig templateConfig2 = this.this$0.getTemplateConfig();
                    if (templateConfig2 != null) {
                        BaseTemplateView baseTemplateView2 = this.this$0;
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - baseTemplateView2.produceDepthVideoTime;
                        TrackHelper trackHelper2 = TrackHelper.INSTANCE;
                        Context context4 = baseTemplateView2.getContext();
                        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
                        String trackId2 = baseTemplateView2.getTrackId();
                        trackHelper2.trackProductDepthVideo(context4, templateConfig2, trackId2 == null ? "" : trackId2, "壁纸不支持", jCurrentTimeMillis2);
                    }
                    this.this$0.setVideoProcessedByAI(true);
                } else {
                    CommonConfig templateConfig3 = this.this$0.getTemplateConfig();
                    if (templateConfig3 != null) {
                        BaseTemplateView baseTemplateView3 = this.this$0;
                        long jCurrentTimeMillis3 = System.currentTimeMillis() - baseTemplateView3.produceDepthVideoTime;
                        TrackHelper trackHelper3 = TrackHelper.INSTANCE;
                        Context context5 = baseTemplateView3.getContext();
                        Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                        String trackId3 = baseTemplateView3.getTrackId();
                        trackHelper3.trackProductDepthVideo(context5, templateConfig3, trackId3 == null ? "" : trackId3, "取消", jCurrentTimeMillis3);
                    }
                }
                VideoSegmentAbility videoSegmentAbility2 = this.this$0.videoSegmentAbility;
                if ((videoSegmentAbility2 == null || !videoSegmentAbility2.isSupport()) && (fastPlayer = this.this$0.produceVideoFastPlayer) != null) {
                    fastPlayer.release();
                }
            }
            Log.d("Keyguard-Editor-BaseTemplateView", "PlayerModeChange : " + this.$code + ", mode = " + this.$mode);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$loadGalleryOpenedIfNeed$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$loadGalleryOpenedIfNeed$1", f = "BaseTemplateView.kt", l = {496}, m = "invokeSuspend")
    public static final class C01031 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public Object L$0;
        public int label;

        public C01031(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new C01031(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01031) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            BaseTemplateView baseTemplateView;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BaseTemplateView baseTemplateView2 = BaseTemplateView.this;
                FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                Context context = baseTemplateView2.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                this.L$0 = baseTemplateView2;
                this.label = 1;
                Object objIsFashionGalleryOpened = fashionGalleryHelper.isFashionGalleryOpened(context, this);
                if (objIsFashionGalleryOpened == coroutine_suspended) {
                    return coroutine_suspended;
                }
                obj = objIsFashionGalleryOpened;
                baseTemplateView = baseTemplateView2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                baseTemplateView = (BaseTemplateView) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            baseTemplateView.setGalleryOpened(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$onDismissBottomSheet$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$onDismissBottomSheet$2", f = "BaseTemplateView.kt", l = {2848}, m = "invokeSuspend")
    public static final class C01042 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01042(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new C01042(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01042) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            FrameLayout root;
            AutoBottomSheet wallpaperFilterAutoBottomSheet;
            FrameLayout root2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(150L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (BaseTemplateView.this.getWallpaperFilterAutoBottomSheet() == null || (wallpaperFilterAutoBottomSheet = BaseTemplateView.this.getWallpaperFilterAutoBottomSheet()) == null || wallpaperFilterAutoBottomSheet.isShowing()) {
                TemplateButtonBar primaryButtonBar = BaseTemplateView.this.getPrimaryButtonBar();
                if (primaryButtonBar != null && (root = primaryButtonBar.getRoot()) != null) {
                    root.setVisibility(0);
                }
            } else {
                TemplateButtonBar primaryButtonBar2 = BaseTemplateView.this.getPrimaryButtonBar();
                if (primaryButtonBar2 != null && (root2 = primaryButtonBar2.getRoot()) != null) {
                    root2.setVisibility(0);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$onWallpaperReady$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$onWallpaperReady$1", f = "BaseTemplateView.kt", l = {762}, m = "invokeSuspend")
    public static final class C01051 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Bitmap $darkBitmap;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01051(Bitmap bitmap, Continuation continuation) {
            super(2, continuation);
            this.$darkBitmap = bitmap;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new C01051(this.$darkBitmap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01051) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                BaseTemplateView$onWallpaperReady$1$dark$1 baseTemplateView$onWallpaperReady$1$dark$1 = new BaseTemplateView$onWallpaperReady$1$dark$1(this.$darkBitmap, null);
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, baseTemplateView$onWallpaperReady$1$dark$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            if (!BaseTemplateView.this.isAttachedToWindow()) {
                return Unit.INSTANCE;
            }
            BaseTemplateView.this.setDarkWallpaper(zBooleanValue);
            BaseTemplateView.this.onWallpaperDarkResultReady();
            Function1<Boolean, Unit> onWallpaperDarkResultReadyListener = BaseTemplateView.this.getOnWallpaperDarkResultReadyListener();
            if (onWallpaperDarkResultReadyListener != null) {
                onWallpaperDarkResultReadyListener.invoke(Boxing.boxBoolean(zBooleanValue));
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$refreshTransitionBackground$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$refreshTransitionBackground$1", f = "BaseTemplateView.kt", l = {698}, m = "invokeSuspend")
    public static final class C01061 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01061(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new C01061(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01061) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BaseTemplateView baseTemplateView = BaseTemplateView.this;
                this.label = 1;
                obj = baseTemplateView.getCurCropWallpaper(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Bitmap bitmap = (Bitmap) obj;
            if (bitmap != null) {
                BaseTemplateView.this.setTransitionBackground(bitmap);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$resetPrimaryButtonBar$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$resetPrimaryButtonBar$1", f = "BaseTemplateView.kt", l = {1877}, m = "invokeSuspend")
    public static final class C01071 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01071(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new C01071(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01071) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(200L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            BaseTemplateView.this.checkRunnable.invoke();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$startPickColorFromWallpaper$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$startPickColorFromWallpaper$1", f = "BaseTemplateView.kt", l = {1802}, m = "invokeSuspend")
    public static final class C01081 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Bitmap $bitmap;
        public final /* synthetic */ Rect $colorPickupRect;
        public final /* synthetic */ boolean $fromStyleChanged;
        public final /* synthetic */ boolean $needCache;
        public final /* synthetic */ boolean $wallpaperChanged;
        public int label;
        public final /* synthetic */ BaseTemplateView this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01081(boolean z, BaseTemplateView baseTemplateView, boolean z2, Bitmap bitmap, Rect rect, boolean z3, Continuation continuation) {
            super(2, continuation);
            this.$needCache = z;
            this.this$0 = baseTemplateView;
            this.$wallpaperChanged = z2;
            this.$bitmap = bitmap;
            this.$colorPickupRect = rect;
            this.$fromStyleChanged = z3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01081(this.$needCache, this.this$0, this.$wallpaperChanged, this.$bitmap, this.$colorPickupRect, this.$fromStyleChanged, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01081) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                BaseTemplateView$startPickColorFromWallpaper$1$data$1 baseTemplateView$startPickColorFromWallpaper$1$data$1 = new BaseTemplateView$startPickColorFromWallpaper$1$data$1(this.this$0, this.$bitmap, this.$wallpaperChanged, this.$colorPickupRect, this.$fromStyleChanged, null);
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, baseTemplateView$startPickColorFromWallpaper$1$data$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ColorData colorData = (ColorData) obj;
            if (colorData != null) {
                if (this.$needCache) {
                    ColorPickerCache.INSTANCE.cacheMyColorData(colorData);
                }
                if (this.this$0.editable) {
                    ColorPickerCache colorPickerCache = ColorPickerCache.INSTANCE;
                    colorPickerCache.cacheAodColorData(colorData);
                    colorPickerCache.cacheEditableColorData(colorData);
                }
                this.this$0.onColorPickComplete(colorData, this.$wallpaperChanged);
            } else {
                Boxing.boxInt(Log.w("Keyguard-Editor-BaseTemplateView", "startPickColorFromWallpaper: data is null"));
            }
            this.this$0.onFirstColorPickComplete();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$updateVisualCheckBoxChecked$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$updateVisualCheckBoxChecked$1", f = "BaseTemplateView.kt", l = {2207}, m = "invokeSuspend")
    public static final class C01091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isDelay;
        public final /* synthetic */ boolean $mChecked;
        public final /* synthetic */ View $view;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$updateVisualCheckBoxChecked$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: BaseTemplateView.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$updateVisualCheckBoxChecked$1$1", f = "BaseTemplateView.kt", l = {2209}, m = "invokeSuspend")
        public static final class C00241 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ boolean $isDelay;
            public final /* synthetic */ boolean $mChecked;
            public final /* synthetic */ View $view;
            public /* synthetic */ Object L$0;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00241(boolean z, View view, boolean z2, Continuation continuation) {
                super(2, continuation);
                this.$isDelay = z;
                this.$view = view;
                this.$mChecked = z2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00241 c00241 = new C00241(this.$isDelay, this.$view, this.$mChecked, continuation);
                c00241.L$0 = obj;
                return c00241;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00241) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    if (this.$isDelay && CoroutineScopeKt.isActive(coroutineScope)) {
                        this.label = 1;
                        if (DelayKt.delay(200L, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                View view = this.$view;
                if (view != null && (view instanceof KgVisualCheckBox)) {
                    ((KgVisualCheckBox) view).updateVisualCheckBoxChecked(this.$mChecked);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01091(boolean z, View view, boolean z2, Continuation continuation) {
            super(2, continuation);
            this.$isDelay = z;
            this.$view = view;
            this.$mChecked = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01091(this.$isDelay, this.$view, this.$mChecked, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                C00241 c00241 = new C00241(this.$isDelay, this.$view, this.$mChecked, null);
                this.label = 1;
                if (BuildersKt.withContext(io2, c00241, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseTemplateView$waitTemplateInit$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$waitTemplateInit$1", f = "BaseTemplateView.kt", l = {646}, m = "invokeSuspend")
    public static final class C01101 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01101(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseTemplateView.this.new C01101(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01101) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(800L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            BaseTemplateView.this.setInitFinish(true);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public BaseTemplateView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r10v8, types: [com.miui.keyguard.editor.edit.base.BaseTemplateView$depthVideoAiServiceListener$1] */
    @JvmOverloads
    public BaseTemplateView(@NotNull final Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.displayClockEffect = -1;
        this.templateReadyIds = new LinkedHashSet();
        this.isDismissTemplateTitle = true;
        this.allowToClickDynamicSwitchBtn = true;
        this.wallpaperEditor = new WallpaperEditor(this);
        this.editable = true;
        this.templateSource = -2L;
        this.isDualClock = DualClockManager.isDualClockAvailable(context);
        this.timeUpdateReceiver = new TimeUpdateReceiver();
        this.wallpaperFilterBottomSheetHeight = context.getResources().getDimensionPixelOffset(R.dimen.kg_wallpaper_filter_bottom_sheet_height);
        this.enableOriginWallpaperPlaceholder = true;
        this.state = 3;
        this.checkRunnable = new Function0() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseTemplateView.checkRunnable$lambda$0(this.f$0);
            }
        };
        this.fastPlayerManager = new FastPlayerManager(false, 0L, 3, null);
        this.trackId = EditorActivity.Companion.getTackId();
        this.previousTemplateId = "";
        this.templateApi = TemplateApi.Companion.getInstance(context);
        this.pickColorFirst = new AtomicBoolean(false);
        this.removeBackgroundRunnable = new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BaseTemplateView.removeBackgroundRunnable$lambda$2(this.f$0);
            }
        };
        this.wallpaperChangedListener = new BaseTemplateView$wallpaperChangedListener$1(this);
        this.fontStyleEditCallback = new FontStyleEditCallback() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$fontStyleEditCallback$1
            @Override // com.miui.keyguard.editor.edit.style.FontStyleEditCallback
            public View getFontStyleView() {
                View fontStyleContentView;
                BaseClockView baseClockView = (BaseClockView) this.this$0.getClockLayer();
                return (baseClockView == null || (fontStyleContentView = baseClockView.getFontStyleContentView()) == null) ? new View(context) : fontStyleContentView;
            }
        };
        setPivotY(DeviceUtil.INSTANCE.currentDisplaySize(context).height() / 2);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 49;
        setLayoutParams(layoutParams);
        ViewUtil.INSTANCE.setSmoothCornerEnabled(this, true);
        setOutlineProvider(new RoundOutlineProvider(getResources().getDimension(R.dimen.kg_template_view_radius)));
        setClipToOutline(true);
        loadGalleryOpenedIfNeed();
        this.normalVideoFirstFrame = new FirstFrameCallBack(new WeakReference(context), new WeakReference(this.depthVideoForegroundLayer), new WeakReference(this));
        this.depthVideoFirstFrameCallBack = new DepthFirstFrameCallBack(new WeakReference(this));
        this.dynamicSwitchCallback = new DynamicSwitchCallback() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$dynamicSwitchCallback$1
            @Override // com.miui.keyguard.editor.edit.base.DynamicSwitchCallback
            public void removeDownloadButton() {
                DepthPresetDownloadButton depthPresetDownloadBtn;
                DepthPresetDownloadManager downloadManager;
                DepthPresetDownloadButton depthPresetDownloadBtn2 = this.this$0.getDepthPresetDownloadBtn();
                if (depthPresetDownloadBtn2 != null && (downloadManager = depthPresetDownloadBtn2.getDownloadManager()) != null) {
                    downloadManager.setDownloadSuccessCallbackSuppressed(true);
                }
                DepthPresetDownloadButton depthPresetDownloadBtn3 = this.this$0.getDepthPresetDownloadBtn();
                if (depthPresetDownloadBtn3 != null) {
                    depthPresetDownloadBtn3.setVisibility(8);
                }
                DepthPresetDownloadButton depthPresetDownloadBtn4 = this.this$0.getDepthPresetDownloadBtn();
                if (depthPresetDownloadBtn4 == null || depthPresetDownloadBtn4.getStatus() != 1 || (depthPresetDownloadBtn = this.this$0.getDepthPresetDownloadBtn()) == null) {
                    return;
                }
                depthPresetDownloadBtn.updateDownloadStatus(1);
            }

            @Override // com.miui.keyguard.editor.edit.base.DynamicSwitchCallback
            public void removeDynamicSwitchButton() {
                DynamicSwitchButton dynamicSwitchBtn = this.this$0.getDynamicSwitchBtn();
                if (dynamicSwitchBtn != null) {
                    dynamicSwitchBtn.setVisibility(8);
                }
            }

            @Override // com.miui.keyguard.editor.edit.base.DynamicSwitchCallback
            public void showDynamicSwitchButton() {
                DynamicSwitchButton dynamicSwitchBtn = this.this$0.getDynamicSwitchBtn();
                if (dynamicSwitchBtn != null) {
                    dynamicSwitchBtn.setVisibility(0);
                }
            }
        };
        this.depthVideoAiServiceListener = new VideoSegmentServiceListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$depthVideoAiServiceListener$1
            @Override // com.miui.keyguard.editor.utils.segment.VideoSegmentServiceListener
            public void onAiServiceDisconnected() {
                Context context2 = context;
                Activity activity = context2 instanceof Activity ? (Activity) context2 : null;
                if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                    return;
                }
                AlertDialog alertDialog = this.depthVideoProgressDialog;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    this.isDepthProgressInterrupted = true;
                } else {
                    this.dismissDepthVideoProgressDialogOnInterrupt();
                }
            }
        };
    }

    public /* synthetic */ BaseTemplateView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void adapterWallpaperLayerSizeForFullAod$lambda$59(float f, float f2, BaseTemplateView baseTemplateView, float f3, float f4) {
        Log.d("Keyguard-Editor-BaseTemplateView", "wallpaperLayer: scaleX=" + f + " scaleY=" + f2 + " " + (baseTemplateView.getWidth() * f3) + " " + (baseTemplateView.getHeight() * f4));
        View view = baseTemplateView.wallpaperLayer;
        if (view != null) {
            view.setScaleX(f);
            view.setScaleY(f2);
            view.setPivotX(view.getWidth() * f3);
            view.setPivotY(view.getHeight() * f4);
        }
    }

    public static final void adapterWallpaperLayerSizeForFullAod$lambda$61(float f, float f2, BaseTemplateView baseTemplateView, float f3, float f4) {
        Log.d("Keyguard-Editor-BaseTemplateView", "wallpaperLayer: scaleX=" + f + " scaleY=" + f2 + " " + (baseTemplateView.getWidth() * f3) + " " + (baseTemplateView.getHeight() * f4));
        View view = baseTemplateView.depthVideoForegroundLayer;
        if (view != null) {
            view.setScaleX(f);
            view.setScaleY(f2);
            view.setPivotX(view.getWidth() * f3);
            view.setPivotY(view.getHeight() * f4);
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0021  */
    /* JADX WARN: Code duplicated, block: B:15:0x0028  */
    /* JADX WARN: Code duplicated, block: B:18:0x002f  */
    /* JADX WARN: Code duplicated, block: B:20:0x0038  */
    /* JADX WARN: Code duplicated, block: B:22:0x003c  */
    /* JADX WARN: Code duplicated, block: B:24:0x0043  */
    public static final void adapterWallpaperLayerSizeForFullAod$lambda$63(BaseTemplateView baseTemplateView, float f, float f2, float f3, float f4) {
        Integer numValueOf;
        int width;
        float fIntValue;
        View view;
        int height;
        View view2 = baseTemplateView.hierarchyLayer;
        if (view2 != null) {
            Integer numValueOf2 = null;
            if (view2.getWidth() == 0) {
                View view3 = baseTemplateView.wallpaperLayer;
                if (view3 != null) {
                    width = view3.getWidth();
                } else {
                    numValueOf = null;
                }
                if (numValueOf != null) {
                    fIntValue = numValueOf.intValue() * f;
                } else {
                    fIntValue = 0.0f;
                }
                if (view2.getHeight() != 0) {
                    view = baseTemplateView.wallpaperLayer;
                    if (view != null) {
                        height = view.getHeight();
                    }
                    float fIntValue2 = numValueOf2 != null ? numValueOf2.intValue() * f2 : 0.0f;
                    Log.d("Keyguard-Editor-BaseTemplateView", "hierarchyLayer: scaleX=" + f3 + " scaleY=" + f4 + " " + view2.getWidth() + " " + view2.getHeight() + " hierarchyPivotX=" + fIntValue + " hierarchyPivotY=" + fIntValue2);
                    view2.setScaleX(f3);
                    view2.setScaleY(f4);
                    view2.setPivotX(fIntValue);
                    view2.setPivotY(fIntValue2);
                }
                height = view2.getHeight();
                numValueOf2 = Integer.valueOf(height);
                if (numValueOf2 != null) {
                }
                Log.d("Keyguard-Editor-BaseTemplateView", "hierarchyLayer: scaleX=" + f3 + " scaleY=" + f4 + " " + view2.getWidth() + " " + view2.getHeight() + " hierarchyPivotX=" + fIntValue + " hierarchyPivotY=" + fIntValue2);
                view2.setScaleX(f3);
                view2.setScaleY(f4);
                view2.setPivotX(fIntValue);
                view2.setPivotY(fIntValue2);
            }
            width = view2.getWidth();
            numValueOf = Integer.valueOf(width);
            if (numValueOf != null) {
                fIntValue = numValueOf.intValue() * f;
            } else {
                fIntValue = 0.0f;
            }
            if (view2.getHeight() != 0) {
                view = baseTemplateView.wallpaperLayer;
                if (view != null) {
                    height = view.getHeight();
                }
                if (numValueOf2 != null) {
                }
                Log.d("Keyguard-Editor-BaseTemplateView", "hierarchyLayer: scaleX=" + f3 + " scaleY=" + f4 + " " + view2.getWidth() + " " + view2.getHeight() + " hierarchyPivotX=" + fIntValue + " hierarchyPivotY=" + fIntValue2);
                view2.setScaleX(f3);
                view2.setScaleY(f4);
                view2.setPivotX(fIntValue);
                view2.setPivotY(fIntValue2);
            }
            height = view2.getHeight();
            numValueOf2 = Integer.valueOf(height);
            if (numValueOf2 != null) {
            }
            Log.d("Keyguard-Editor-BaseTemplateView", "hierarchyLayer: scaleX=" + f3 + " scaleY=" + f4 + " " + view2.getWidth() + " " + view2.getHeight() + " hierarchyPivotX=" + fIntValue + " hierarchyPivotY=" + fIntValue2);
            view2.setScaleX(f3);
            view2.setScaleY(f4);
            view2.setPivotX(fIntValue);
            view2.setPivotY(fIntValue2);
        }
    }

    public static /* synthetic */ void addViewToTemplate$default(BaseTemplateView baseTemplateView, View view, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addViewToTemplate");
        }
        if ((i2 & 2) != 0) {
            i = -1;
        }
        baseTemplateView.addViewToTemplate(view, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void allReady$default(BaseTemplateView baseTemplateView, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: allReady");
        }
        if ((i & 1) != 0) {
            function0 = null;
        }
        baseTemplateView.allReady(function0);
    }

    public static final Unit allReady$lambda$10(BaseTemplateView baseTemplateView, Function0 function0) {
        baseTemplateView.removeView(baseTemplateView.loadingView);
        baseTemplateView.loadingView = null;
        if (function0 != null) {
            function0.invoke();
        }
        return Unit.INSTANCE;
    }

    public static final Unit checkRunnable$lambda$0(BaseTemplateView baseTemplateView) {
        IWallpaperLayer wallpaperLayer = baseTemplateView.getWallpaperLayer();
        if ((wallpaperLayer != null ? Intrinsics.areEqual(wallpaperLayer.getScaleable(), Boolean.FALSE) : false) || baseTemplateView.isGlassClockEffect()) {
            KgVisualCheckBox kgVisualCheckBox = baseTemplateView.filterBtn;
            if (kgVisualCheckBox == null) {
                return null;
            }
            kgVisualCheckBox.disable();
            return Unit.INSTANCE;
        }
        baseTemplateView.isCheckBtn = false;
        KgVisualCheckBox kgVisualCheckBox2 = baseTemplateView.filterBtn;
        if (kgVisualCheckBox2 == null) {
            return null;
        }
        kgVisualCheckBox2.setClickable(!baseTemplateView.isGalleryOpened);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ BaseClockView createClockLayer$default(BaseTemplateView baseTemplateView, Context context, String str, EditFragment editFragment, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createClockLayer");
        }
        if ((i & 4) != 0) {
            editFragment = null;
        }
        return baseTemplateView.createClockLayer(context, str, editFragment);
    }

    public static /* synthetic */ View createWallpaperLayer$default(BaseTemplateView baseTemplateView, Context context, WallpaperInfo wallpaperInfo, boolean z, EditFragment editFragment, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createWallpaperLayer");
        }
        if ((i & 8) != 0) {
            editFragment = null;
        }
        EditFragment editFragment2 = editFragment;
        if ((i & 16) != 0) {
            str = baseTemplateView.getFromWhereTag();
        }
        return baseTemplateView.createWallpaperLayer(context, wallpaperInfo, z, editFragment2, str);
    }

    public static final void createWallpaperLayer$lambda$101$lambda$100(final BaseTemplateView baseTemplateView, final String str) {
        try {
            CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) baseTemplateView.getWallpaperLayer();
            boolean z = true;
            boolean z2 = false;
            boolean z3 = baseTemplateView.getBackground() == null;
            if (combinedWallpaperView == null) {
                combinedWallpaperView = null;
            }
            if (combinedWallpaperView != null) {
                if (combinedWallpaperView.getAlpha() > 0.0f) {
                    z = false;
                }
                z2 = z;
            }
            if (z3 && z2) {
                baseTemplateView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda37
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseTemplateView.createWallpaperLayer$lambda$101$lambda$100$lambda$99(this.f$0, str);
                    }
                }, 1000L);
            }
        } catch (Throwable unused) {
        }
    }

    public static final void createWallpaperLayer$lambda$101$lambda$100$lambda$99(BaseTemplateView baseTemplateView, String str) {
        try {
            CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) baseTemplateView.getWallpaperLayer();
            boolean z = true;
            boolean z2 = false;
            boolean z3 = baseTemplateView.getBackground() == null;
            if (combinedWallpaperView == null) {
                combinedWallpaperView = null;
            }
            if (combinedWallpaperView != null) {
                if (combinedWallpaperView.getAlpha() > 0.0f) {
                    z = false;
                }
                z2 = z;
            }
            if (z3 && z2) {
                KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                Bundle bundle = new Bundle();
                if (str == null) {
                    str = "";
                }
                bundle.putString(AnimatedTarget.STATE_TAG_FROM, str);
                Unit unit = Unit.INSTANCE;
                keyguardEditorMiSightUtil.reportWallpaperBlackScreen("BaseTemplateView.createWallpaperLayer_black_after_delay", bundle);
            }
        } catch (Throwable unused) {
        }
    }

    private final void exitEditFrame() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.exitEditFrame();
        }
    }

    private final void exitEdite() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.exitEdite();
        }
    }

    public static /* synthetic */ void exitFullScreen$default(BaseTemplateView baseTemplateView, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: exitFullScreen");
        }
        if ((i & 1) != 0) {
            z = false;
        }
        baseTemplateView.exitFullScreen(z);
    }

    private final int getBlurStatus() {
        IntWrapper homeFilterId;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel == null || (homeFilterId = viewModel.getHomeFilterId()) == null) {
            return 0;
        }
        return homeFilterId.getIntValue();
    }

    private final <T extends View> T getColorLayer() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            return (T) baseClockView.getColorLayer();
        }
        return null;
    }

    private final String getVideoStatus() {
        DynamicSwitchButton dynamicSwitchButton = this.dynamicSwitchBtn;
        if (dynamicSwitchButton != null && dynamicSwitchButton.isClicked() && dynamicSwitchButton.getStatus() == 2) {
            return "开";
        }
        return (dynamicSwitchButton == null || !dynamicSwitchButton.isClicked()) ? "未编辑" : "关";
    }

    public static final Unit initLoading$lambda$8(BaseTemplateView baseTemplateView) {
        baseTemplateView.onTemplateReady(2);
        return Unit.INSTANCE;
    }

    public static final void onDismissBottomSheet$lambda$110(BaseTemplateView baseTemplateView) {
        TextView textView = baseTemplateView.templateTitle;
        if (textView != null) {
            AnimatorKt.alphaTo$default(textView, 1.0f, 0.0f, 0.0f, null, 14, null);
        }
    }

    public static final Bitmap onFirstBindWallpaper$lambda$15(BaseTemplateView baseTemplateView, Bitmap bitmap) {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(baseTemplateView.getPickColorBitmap());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            Log.w("Keyguard-Editor-BaseTemplateView", "onFirstBindWallpaper: getPickColorBitmap failed", thM1063exceptionOrNullimpl);
        }
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = null;
        }
        Bitmap bitmap2 = (Bitmap) objM1061constructorimpl;
        return bitmap2 == null ? bitmap : bitmap2;
    }

    public static final void onFirstBindWallpaper$lambda$16(BaseTemplateView baseTemplateView, Bitmap bitmap, Bitmap bitmap2) {
        try {
            baseTemplateView.onWallpaperReady(bitmap2, true, baseTemplateView.getWallpaperBitmapForDarkResult(bitmap, bitmap2));
        } finally {
            if (baseTemplateView.editable) {
                BitmapTempStore.Companion.getInstance().removeBitmap("wallpaper_for_pick_color");
            }
        }
    }

    public static /* synthetic */ void onWallpaperReady$default(BaseTemplateView baseTemplateView, Bitmap bitmap, boolean z, Bitmap bitmap2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onWallpaperReady");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            bitmap2 = bitmap;
        }
        baseTemplateView.onWallpaperReady(bitmap, z, bitmap2);
    }

    public static final Integer produceDepthVideo$lambda$94(BaseTemplateView baseTemplateView, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, FastPlayerListener fastPlayerListener) {
        VideoSegmentAbility videoSegmentAbility = baseTemplateView.videoSegmentAbility;
        if (videoSegmentAbility == null) {
            return null;
        }
        Intrinsics.checkNotNull(parcelFileDescriptor2);
        return Integer.valueOf(videoSegmentAbility.segmentByFd(parcelFileDescriptor, parcelFileDescriptor2, fastPlayerListener));
    }

    public static final int produceDepthVideo$lambda$95(BaseTemplateView baseTemplateView, String str, int i, int i2, long j) {
        Intrinsics.checkNotNull(str);
        return baseTemplateView.handleProduceVideoCallback(str, i, i2, j);
    }

    public static final void refreshBar$lambda$83$lambda$69$lambda$68(BaseTemplateView baseTemplateView, KgVisualCheckBox it) {
        ClockInfo clockInfo;
        String templateId;
        KgVisualCheckBox wallpaperPickerButton;
        Intrinsics.checkNotNullParameter(it, "it");
        BaseClockView baseClockView = (BaseClockView) baseTemplateView.getClockLayer();
        if (baseClockView != null) {
            baseClockView.onEditorSubUIShown();
        }
        TemplateButtonBar templateButtonBar = baseTemplateView.primaryButtonBar;
        if (templateButtonBar != null && (wallpaperPickerButton = templateButtonBar.getWallpaperPickerButton()) != null) {
            wallpaperPickerButton.setClickable(false);
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new BaseTemplateView$refreshBar$1$2$1$1(baseTemplateView, null), 2, null);
        WallpaperEditor.WallpaperPickerLauncher wallpaperPickerLauncher = baseTemplateView.wallpaperPickerLauncher;
        if (wallpaperPickerLauncher != null) {
            wallpaperPickerLauncher.launch();
        }
        CommonConfig commonConfig = baseTemplateView.templateConfig;
        if (commonConfig != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context context2 = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            EditFragment editFragment = baseTemplateView.editFragment;
            int defaultSelect = editFragment != null ? editFragment.getDefaultSelect() : 0;
            TextView label = it.getLabel();
            String strValueOf = String.valueOf(label != null ? label.getText() : null);
            TemplateConfig lockscreenInfo = commonConfig.getLockscreenInfo();
            String str = (lockscreenInfo == null || (clockInfo = lockscreenInfo.getClockInfo()) == null || (templateId = clockInfo.getTemplateId()) == null) ? "" : templateId;
            String str2 = baseTemplateView.trackId;
            trackHelper.trackClickEditorItem(context2, defaultSelect, strValueOf, "", str, str2 == null ? "" : str2);
        }
    }

    public static final void refreshBar$lambda$83$lambda$78$lambda$75(final BaseTemplateView baseTemplateView, TemplateButtonBar templateButtonBar, KgVisualCheckBox it) {
        ImageView imageView;
        ClockInfo clockInfo;
        String templateId;
        Intrinsics.checkNotNullParameter(it, "it");
        CombineEditListener combineEditListener = baseTemplateView.combineEditListener;
        if (combineEditListener == null || combineEditListener.getCurrentSelect() != 0) {
            it.setCheckedOnly(false);
            return;
        }
        CommonConfig commonConfig = baseTemplateView.templateConfig;
        if (commonConfig != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context context = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            EditFragment editFragment = baseTemplateView.editFragment;
            int defaultSelect = editFragment != null ? editFragment.getDefaultSelect() : 0;
            TextView label = it.getLabel();
            String strValueOf = String.valueOf(label != null ? label.getText() : null);
            TemplateConfig lockscreenInfo = commonConfig.getLockscreenInfo();
            String str = (lockscreenInfo == null || (clockInfo = lockscreenInfo.getClockInfo()) == null || (templateId = clockInfo.getTemplateId()) == null) ? "" : templateId;
            String str2 = baseTemplateView.trackId;
            trackHelper.trackClickEditorItem(context, defaultSelect, strValueOf, "", str, str2 == null ? "" : str2);
        }
        baseTemplateView.inTalkBackCloseDialogs(new AutoBottomSheet[0]);
        baseTemplateView.isCheckBtn = true;
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context2 = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), Dispatchers.getMain(), null, new BaseTemplateView$refreshBar$1$3$1$2(baseTemplateView, null), 2, null);
        if (baseTemplateView.wallpaperFilterAutoBottomSheet == null) {
            Context context3 = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            baseTemplateView.wallpaperFilterView = new WallpaperFilterView(context3);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, baseTemplateView.wallpaperFilterBottomSheetHeight);
            WallpaperFilterView wallpaperFilterView = baseTemplateView.wallpaperFilterView;
            if (wallpaperFilterView != null) {
                wallpaperFilterView.setLayoutParams(layoutParams);
            }
            WallpaperFilterView wallpaperFilterView2 = baseTemplateView.wallpaperFilterView;
            if (wallpaperFilterView2 != null) {
                wallpaperFilterView2.setWallpaperFilterData(baseTemplateView.getWallpaperFilter(), templateButtonBar.getTemplateView(), EditFragmentViewModel.Companion.getViewModel(baseTemplateView.editFragment));
            }
            WallpaperFilterView wallpaperFilterView3 = baseTemplateView.wallpaperFilterView;
            if (wallpaperFilterView3 != null && (imageView = (ImageView) wallpaperFilterView3.findViewById(R.id.head_icon)) != null) {
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda30
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BaseTemplateView.refreshBar$lambda$83$lambda$78$lambda$75$lambda$71(this.f$0, view);
                    }
                });
            }
            Context context4 = baseTemplateView.getContext();
            Intrinsics.checkNotNull(context4, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
            AutoBottomSheet margin = new AutoBottomSheet((EditorActivity) context4).setContentView(baseTemplateView.wallpaperFilterView).setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda31
                @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
                public final void onDismiss() {
                    BaseTemplateView.refreshBar$lambda$83$lambda$78$lambda$75$lambda$72(this.f$0);
                }
            }).setOnBeforeShowListener(new BottomSheetModal.OnShowListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda32
                @Override // miuix.bottomsheet.BottomSheetModal.OnShowListener
                public final void onShow() {
                    BaseTemplateView.refreshBar$lambda$83$lambda$78$lambda$75$lambda$73(this.f$0);
                }
            }).setAnchorView(baseTemplateView.filterBtn, baseTemplateView.getFilterBottomSheetAlignModel()).setMargin(0, 0, 0, baseTemplateView.sheetMarginBottom());
            margin.setSkipCollapsed(true);
            margin.setSkipHalfExpanded(true);
            baseTemplateView.wallpaperFilterAutoBottomSheet = margin;
        }
        BaseClockView baseClockView = (BaseClockView) baseTemplateView.getClockLayer();
        if (baseClockView != null && baseClockView.showBottomSheetModal()) {
            BaseClockView baseClockView2 = (BaseClockView) baseTemplateView.getColorLayer();
            if (baseClockView2 != null) {
                baseClockView2.dismissBottomSheet();
            }
            Context context5 = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context5), Dispatchers.getMain(), null, new BaseTemplateView$refreshBar$1$3$1$7(baseTemplateView, null), 2, null);
        }
        AutoBottomSheet autoBottomSheet = baseTemplateView.wallpaperFilterAutoBottomSheet;
        if (autoBottomSheet != null) {
            autoBottomSheet.show();
        }
        baseTemplateView.isDismissTemplateTitle = true;
    }

    public static final void refreshBar$lambda$83$lambda$78$lambda$75$lambda$71(BaseTemplateView baseTemplateView, View view) {
        AutoBottomSheet autoBottomSheet = baseTemplateView.wallpaperFilterAutoBottomSheet;
        if (autoBottomSheet != null) {
            autoBottomSheet.dismiss();
        }
    }

    public static final void refreshBar$lambda$83$lambda$78$lambda$75$lambda$72(BaseTemplateView baseTemplateView) {
        baseTemplateView.onDismissBottomSheet();
        KgVisualCheckBox kgVisualCheckBox = baseTemplateView.filterBtn;
        if (kgVisualCheckBox != null) {
            kgVisualCheckBox.setCheckedOnly(false);
        }
        baseTemplateView.checkRunnable.invoke();
        baseTemplateView.controlTopButtonState(true);
        baseTemplateView.clearEditeFocus();
        AutoBottomSheet autoBottomSheet = baseTemplateView.wallpaperFilterAutoBottomSheet;
        if (autoBottomSheet != null) {
            autoBottomSheet.setShowing(false);
        }
        BaseClockView baseClockView = (BaseClockView) baseTemplateView.getClockLayer();
        if (baseClockView != null) {
            baseClockView.onEditorSubUIDismissed();
        }
    }

    public static final void refreshBar$lambda$83$lambda$78$lambda$75$lambda$73(BaseTemplateView baseTemplateView) {
        baseTemplateView.onShowBottomSheet();
        baseTemplateView.controlTopButtonState(false);
        WallpaperFilterView wallpaperFilterView = baseTemplateView.wallpaperFilterView;
        if (wallpaperFilterView != null) {
            wallpaperFilterView.onShow();
        }
        BaseClockView baseClockView = (BaseClockView) baseTemplateView.getClockLayer();
        if (baseClockView != null) {
            baseClockView.onEditorSubUIShown();
        }
    }

    public static final void refreshBar$lambda$83$lambda$78$lambda$76(BaseTemplateView baseTemplateView, KgVisualCheckBox it) {
        Intrinsics.checkNotNullParameter(it, "it");
        AutoBottomSheet autoBottomSheet = baseTemplateView.wallpaperFilterAutoBottomSheet;
        if (autoBottomSheet != null) {
            autoBottomSheet.dismiss();
        }
    }

    public static final void refreshBar$lambda$83$lambda$78$lambda$77(BaseTemplateView baseTemplateView, View view) {
        if (baseTemplateView.isInThirdPartyWallpaperService()) {
            ToastUtil toastUtil = ToastUtil.INSTANCE;
            Context context = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            toastUtil.showText(context, R.string.kg_effects_tip_not_support_third_party_wallpaper_service);
            return;
        }
        if (baseTemplateView.isGalleryOpened) {
            ToastUtil toastUtil2 = ToastUtil.INSTANCE;
            Context context2 = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            toastUtil2.showText(context2, R.string.kg_effects_not_support_gallery_tip);
            return;
        }
        if (baseTemplateView.isGlassClockEffect()) {
            ToastUtil toastUtil3 = ToastUtil.INSTANCE;
            Context context3 = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            toastUtil3.showText(context3, R.string.kg_effects_tip_not_support_glass_filter);
            return;
        }
        ToastUtil toastUtil4 = ToastUtil.INSTANCE;
        Context context4 = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        toastUtil4.showText(context4, R.string.kg_effects_tip_not_support);
    }

    public static final void removeBackgroundRunnable$lambda$2(BaseTemplateView baseTemplateView) {
        IWallpaperLayer wallpaperLayer = baseTemplateView.getWallpaperLayer();
        Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
        ((CombinedWallpaperView) wallpaperLayer).setBackgroundDrawable(null);
    }

    public static final boolean setLongClickListenerForLivePhoto$lambda$102(BaseTemplateView baseTemplateView, View view) {
        Log.d("Keyguard-Editor-BaseTemplateView", "replay live photo");
        baseTemplateView.startForcePlayerWithDepthVideo("long click");
        return true;
    }

    private final int sheetMarginBottom() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            return baseClockView.sheetMarginBottom();
        }
        return 0;
    }

    public static final void showCancelDialog$lambda$105(BaseTemplateView baseTemplateView, Ref.ObjectRef objectRef, DialogInterface dialogInterface, int i) {
        Consumer consumer = (Consumer) objectRef.element;
        if (consumer != null) {
            consumer.accept(Boolean.FALSE);
        }
        objectRef.element = null;
    }

    public static final void showCancelDialog$lambda$108(BaseTemplateView baseTemplateView, DialogInterface dialogInterface, int i) {
        Function0 function0 = baseTemplateView.onCancelDialogNegativeClick;
        if (function0 != null) {
            function0.invoke();
        }
    }

    public static final void showProduceDepthVideoProgressDialog$lambda$119(BaseTemplateView baseTemplateView, DialogInterface dialogInterface, int i) {
        baseTemplateView.isDepthProgressInterrupted = false;
        ImageSegmentHandler.INSTANCE.removeVideoSegmentServiceListener(baseTemplateView.depthVideoAiServiceListener);
        VideoSegmentAbility videoSegmentAbility = baseTemplateView.videoSegmentAbility;
        if (videoSegmentAbility == null || !videoSegmentAbility.isSupport()) {
            FastPlayer fastPlayer = baseTemplateView.produceVideoFastPlayer;
            if (fastPlayer != null) {
                fastPlayer.release();
            }
        } else {
            VideoSegmentAbility videoSegmentAbility2 = baseTemplateView.videoSegmentAbility;
            if (videoSegmentAbility2 != null) {
                videoSegmentAbility2.destroy();
            }
        }
        dialogInterface.dismiss();
    }

    public static /* synthetic */ void startForcePlayerWithNormalVideo$default(BaseTemplateView baseTemplateView, String str, boolean z, String str2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: startForcePlayerWithNormalVideo");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        baseTemplateView.startForcePlayerWithNormalVideo(str, z, str2);
    }

    public static /* synthetic */ void startPickColorFromWallpaper$default(BaseTemplateView baseTemplateView, Bitmap bitmap, boolean z, boolean z2, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: startPickColorFromWallpaper");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            rect = null;
        }
        baseTemplateView.startPickColorFromWallpaper(bitmap, z, z2, rect);
    }

    public static final void stopForcePlayerWithDepthVideo$lambda$54(BaseTemplateView baseTemplateView) {
        View view = baseTemplateView.depthVideoForegroundLayer;
        if (view != null) {
            view.setVisibility(8);
        }
        baseTemplateView.releaseFastPlayer();
    }

    public static final void switchClockTemplate$lambda$40$lambda$38(final BaseClockView baseClockView, final BaseTemplateView baseTemplateView, String str, BaseClockView baseClockView2) {
        IntWrapper hierarchyStatus;
        IntWrapper hierarchyStatus2;
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        IntWrapper hierarchyStatus3;
        IntWrapper hierarchyStatus4;
        IntWrapper hierarchyStatus5;
        FontFilterView clockFontFilterView;
        AutoBottomSheet autoBottomSheet;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        IntWrapper wallpaperFilterId;
        TemplateChangeRecorder templateChangeRecorder;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(baseClockView.getEditFragment());
        HierarchyImageView hierarchyImageView = (HierarchyImageView) baseTemplateView.getHierarchyLayer();
        if (hierarchyImageView != null) {
            HierarchyImageAvoidController avoidController = hierarchyImageView.getAvoidController();
            if (avoidController != null) {
                avoidController.resetAvoidRect(baseClockView.hierarchyAvoidRect());
            }
            BaseClockView baseClockView3 = baseTemplateView.miuiClockView;
            if (baseClockView3 != null) {
                baseClockView3.removeView(hierarchyImageView);
            }
            baseClockView2.addHierarchyLayer(hierarchyImageView);
        }
        Object objM733getDepthVideoForegroundLayer = baseTemplateView.m733getDepthVideoForegroundLayer();
        if (objM733getDepthVideoForegroundLayer != null) {
            View view = (View) objM733getDepthVideoForegroundLayer;
            view.setVisibility(0);
            BaseClockView baseClockView4 = baseTemplateView.miuiClockView;
            if (baseClockView4 != null) {
                baseClockView4.removeView(view);
            }
            baseClockView2.addDepthVideoForegroundLayer(view);
        }
        if (Intrinsics.areEqual(str, "doodle")) {
            if (viewModel != null && (templateChangeRecorder = viewModel.getTemplateChangeRecorder()) != null) {
                templateChangeRecorder.clearAllRecord();
            }
            if (MagicType.INSTANCE.isShader((viewModel == null || (wallpaperFilterId = viewModel.getWallpaperFilterId()) == null) ? 0 : wallpaperFilterId.getIntValue()) && viewModel != null) {
                EditFragmentViewModel.updateWallpaperFilterIdSync$default(viewModel, 0, false, 2, null);
            }
        }
        BaseClockView baseClockView5 = baseTemplateView.miuiClockView;
        boolean zIsPickPanelLifted = baseClockView5 != null ? baseClockView5.isPickPanelLifted() : false;
        BaseClockView baseClockView6 = baseTemplateView.miuiClockView;
        int pickLiftClickedViewId = baseClockView6 != null ? baseClockView6.getPickLiftClickedViewId() : 0;
        BaseClockView baseClockView7 = baseTemplateView.miuiClockView;
        if (baseClockView7 != null) {
            baseClockView7.setVisibility(8);
        }
        BaseClockView baseClockView8 = baseTemplateView.miuiClockView;
        if (baseClockView8 != null) {
            BaseClockView.resetSpecialBottomSheet$default(baseClockView8, false, 1, null);
        }
        BaseClockView baseClockView9 = baseTemplateView.miuiClockView;
        AutoBottomSheet[] autoBottomSheetList = baseClockView9 != null ? baseClockView9.getAutoBottomSheetList() : null;
        baseTemplateView.removeView(baseTemplateView.miuiClockView);
        BaseClockView baseClockView10 = baseTemplateView.miuiClockView;
        if (baseClockView10 != null) {
            baseClockView10.destroy();
        }
        baseTemplateView.miuiClockView = baseClockView2;
        baseTemplateView.autoColorPicker = baseClockView.createAutoColorPicker();
        MultiTouchFrameLayout multiTouchFrameLayout = (MultiTouchFrameLayout) baseClockView.getForeContentLayer();
        if (multiTouchFrameLayout != null) {
            View view2 = baseTemplateView.wallpaperLayer;
            CombinedWallpaperView combinedWallpaperView = view2 instanceof CombinedWallpaperView ? (CombinedWallpaperView) view2 : null;
            if (combinedWallpaperView != null) {
                combinedWallpaperView.setObserveTouchClickListener(multiTouchFrameLayout);
            }
        }
        baseTemplateView.colorPickHandler = baseClockView.createColorPickHandler();
        CommonConfig commonConfig2 = baseTemplateView.templateConfig;
        if (commonConfig2 != null && viewModel != null) {
            EditFragmentViewModel.reloadTemplateConfigForFontFilter$default(viewModel, commonConfig2, false, false, 6, null);
        }
        baseTemplateView.updateFilterButtonVisible();
        BaseClockView baseClockView11 = (BaseClockView) baseTemplateView.getClockLayer();
        if (baseClockView11 != null) {
            baseClockView11.refreshColorPickupRect();
        }
        Rect rectCurrentColorPickupRect = baseClockView.currentColorPickupRect();
        final Rect rect = rectCurrentColorPickupRect != null ? new Rect(rectCurrentColorPickupRect) : null;
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda33
            @Override // java.util.function.Supplier
            public final Object get() {
                return BaseTemplateView.switchClockTemplate$lambda$40$lambda$38$lambda$36(baseClockView, baseTemplateView);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda34
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.startPickColorFromWallpaper((Bitmap) obj, true, false, rect);
            }
        });
        CommonConfig commonConfig3 = baseTemplateView.templateConfig;
        if (Intrinsics.areEqual((commonConfig3 == null || (lockscreenInfo4 = commonConfig3.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getResourceType(), "video")) {
            if (DeviceUtil.INSTANCE.isSupportHierarchyDevices() && baseClockView2.supportHierarchy()) {
                KgVisualCheckBox kgVisualCheckBox = baseTemplateView.hierarchyBtn;
                if (kgVisualCheckBox != null) {
                    kgVisualCheckBox.setVisibility(0);
                }
                CommonConfig commonConfig4 = baseTemplateView.templateConfig;
                if (commonConfig4 != null && (lockscreenInfo3 = commonConfig4.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null && wallpaperInfo3.isDepthVideo()) {
                    baseTemplateView.onEdited(36, null);
                    baseTemplateView.updateHierarchyButton(5);
                }
            } else {
                KgVisualCheckBox kgVisualCheckBox2 = baseTemplateView.hierarchyBtn;
                if (kgVisualCheckBox2 != null) {
                    kgVisualCheckBox2.setVisibility(8);
                }
                MagicType magicType = MagicType.INSTANCE;
                CommonConfig commonConfig5 = baseTemplateView.templateConfig;
                if (magicType.isDepthVideo((commonConfig5 == null || (lockscreenInfo2 = commonConfig5.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo2.getMagicType()))) {
                    baseTemplateView.onEdited(35, null);
                    baseTemplateView.updateHierarchyButton(6);
                }
            }
        } else if (DeviceUtil.INSTANCE.isSupportHierarchyDevices() && baseClockView2.supportHierarchy()) {
            KgVisualCheckBox kgVisualCheckBox3 = baseTemplateView.hierarchyBtn;
            if (kgVisualCheckBox3 != null) {
                kgVisualCheckBox3.setVisibility(0);
            }
            if ((viewModel == null || (hierarchyStatus5 = viewModel.getHierarchyStatus()) == null || hierarchyStatus5.getIntValue() != 0) && (commonConfig = baseTemplateView.templateConfig) != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null && wallpaperInfo.getSupportSubject() && ((viewModel != null && (hierarchyStatus4 = viewModel.getHierarchyStatus()) != null && hierarchyStatus4.getIntValue() == 2) || (viewModel != null && (hierarchyStatus3 = viewModel.getHierarchyStatus()) != null && hierarchyStatus3.getIntValue() == 4))) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 1, false, false, false, 14, null);
            }
        } else {
            KgVisualCheckBox kgVisualCheckBox4 = baseTemplateView.hierarchyBtn;
            if (kgVisualCheckBox4 != null) {
                kgVisualCheckBox4.setVisibility(8);
            }
            if ((viewModel != null && (hierarchyStatus2 = viewModel.getHierarchyStatus()) != null && hierarchyStatus2.getIntValue() == 1) || (viewModel != null && (hierarchyStatus = viewModel.getHierarchyStatus()) != null && hierarchyStatus.getIntValue() == 3)) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 14, null);
            }
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = baseClockView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new BaseTemplateView$switchClockTemplate$1$1$7(baseClockView2, baseTemplateView, null), 3, null);
        BaseClockView baseClockView12 = baseTemplateView.miuiClockView;
        if (baseClockView12 != null) {
            baseClockView12.updateBottomSheet(autoBottomSheetList);
        }
        boolean z = (autoBottomSheetList == null || (autoBottomSheet = (AutoBottomSheet) ArraysKt.firstOrNull(autoBottomSheetList)) == null || !autoBottomSheet.isShowing()) ? false : true;
        if (zIsPickPanelLifted || z) {
            if (pickLiftClickedViewId == 0) {
                pickLiftClickedViewId = R.id.kg_time_area_frame;
            }
            baseClockView2.restorePickPanelLift(pickLiftClickedViewId);
        }
        baseTemplateView.isTemplateSwitching = false;
        if (baseTemplateView.pendingDismissAfterSwitch) {
            baseTemplateView.pendingDismissAfterSwitch = false;
            Log.d("Keyguard-Editor-BaseTemplateView", "switchClockTemplate: executing deferred dismiss");
            BaseClockView baseClockView13 = (BaseClockView) baseTemplateView.getClockLayer();
            if (baseClockView13 != null && (clockFontFilterView = baseClockView13.getClockFontFilterView()) != null) {
                clockFontFilterView.reset();
            }
            baseClockView.clearSelectState();
            baseClockView.onDismissBottomSheet();
        }
    }

    public static final Bitmap switchClockTemplate$lambda$40$lambda$38$lambda$36(BaseClockView baseClockView, BaseTemplateView baseTemplateView) {
        Object objM1061constructorimpl;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(baseTemplateView.getPickColorBitmap());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            Log.w("Keyguard-Editor-BaseTemplateView", "switchClockTemplate: getPickColorBitmap failed", thM1063exceptionOrNullimpl);
        }
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = null;
        }
        Bitmap bitmap = (Bitmap) objM1061constructorimpl;
        if (bitmap != null) {
            return bitmap;
        }
        CommonConfig commonConfig = baseTemplateView.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) {
            return null;
        }
        return wallpaperInfo.getCurrentWallpaper();
    }

    public static final Unit takeVideoDepthScreenshot$lambda$52$lambda$49(CountDownLatch countDownLatch) {
        countDownLatch.countDown();
        Log.d("Keyguard-Editor-BaseTemplateView", "first frame ready");
        return Unit.INSTANCE;
    }

    public static final void takeVideoDepthScreenshot$lambda$52$lambda$50(Ref.LongRef longRef, Bitmap bitmap, BaseTemplateView baseTemplateView, HandlerThread handlerThread, CountDownLatch countDownLatch, int i) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (i == 0) {
            Log.d("Keyguard-Editor-BaseTemplateView", "background bitmap consume time is " + (System.currentTimeMillis() - longRef.element) + ", bitmap is " + bitmap.getByteCount());
            CommonConfig commonConfig = baseTemplateView.templateConfig;
            if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.setVideoWallpaperBitmap(bitmap);
            }
            if (handlerThread.isAlive()) {
                countDownLatch.countDown();
            }
        }
    }

    public static final void takeVideoDepthScreenshot$lambda$52$lambda$51(Ref.LongRef longRef, Bitmap bitmap, BaseTemplateView baseTemplateView, HandlerThread handlerThread, CountDownLatch countDownLatch, int i) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (i == 0) {
            Log.d("Keyguard-Editor-BaseTemplateView", "foreground bitmap consume time is " + (System.currentTimeMillis() - longRef.element) + ", bitmap is " + bitmap.getByteCount());
            CommonConfig commonConfig = baseTemplateView.templateConfig;
            if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.setVideoSubjectBitmap(bitmap);
            }
            if (handlerThread.isAlive()) {
                countDownLatch.countDown();
            }
        }
    }

    public static final void updateFilterButtonVisible$lambda$131(BaseTemplateView baseTemplateView) {
        TemplateButtonBar templateButtonBar = baseTemplateView.primaryButtonBar;
        if (templateButtonBar != null) {
            templateButtonBar.updateButtonGroupRec();
        }
    }

    public static /* synthetic */ void updateVisualCheckBoxChecked$default(BaseTemplateView baseTemplateView, View view, boolean z, boolean z2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateVisualCheckBoxChecked");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        baseTemplateView.updateVisualCheckBoxChecked(view, z, z2);
    }

    @NotNull
    public List<View> aboveWallpaperLayers() {
        List listListOfNotNull;
        List<View> listEmptyList;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (Intrinsics.areEqual(getTemplateId(), "doodle")) {
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new View[]{baseClockView != null ? baseClockView.getBackContentLayer() : null, baseClockView != null ? baseClockView.getForeContentLayer() : null});
        } else {
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new View[]{baseClockView != null ? baseClockView.getBackContentLayer() : null, getHierarchyLayer(), baseClockView != null ? baseClockView.getForeContentLayer() : null});
        }
        if (baseClockView == null || (listEmptyList = baseClockView.getAdditionalExitLayers()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) listEmptyList);
    }

    public final void adapterWallpaperLayerSizeForFullAod() {
        final BaseTemplateView baseTemplateView;
        MiuiSettingsUtils miuiSettingsUtils = MiuiSettingsUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zFullAodEnable = miuiSettingsUtils.fullAodEnable(context);
        Context context2 = getContext();
        EditorActivity editorActivity = context2 instanceof EditorActivity ? (EditorActivity) context2 : null;
        boolean zCallingFromSystemUI = editorActivity != null ? editorActivity.callingFromSystemUI() : false;
        if (zFullAodEnable && zCallingFromSystemUI) {
            Context context3 = getContext();
            Intrinsics.checkNotNull(context3, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
            Bundle fullAodExtraArgs = ((EditorActivity) context3).getFullAodExtraArgs();
            float f = 1.05f;
            final float f2 = (fullAodExtraArgs == null || !fullAodExtraArgs.containsKey(AnimatedProperty.PROPERTY_NAME_SCALE_X)) ? 1.05f : fullAodExtraArgs.getFloat(AnimatedProperty.PROPERTY_NAME_SCALE_X);
            if (fullAodExtraArgs != null && fullAodExtraArgs.containsKey(AnimatedProperty.PROPERTY_NAME_SCALE_Y)) {
                f = fullAodExtraArgs.getFloat(AnimatedProperty.PROPERTY_NAME_SCALE_Y);
            }
            final float f3 = f;
            final float f4 = (fullAodExtraArgs == null || !fullAodExtraArgs.containsKey(AnimatedProperty.PROPERTY_NAME_PIVOT_X)) ? 0.5f : fullAodExtraArgs.getFloat(AnimatedProperty.PROPERTY_NAME_PIVOT_X);
            final float f5 = (fullAodExtraArgs == null || !fullAodExtraArgs.containsKey(AnimatedProperty.PROPERTY_NAME_PIVOT_Y)) ? 0.4f : fullAodExtraArgs.getFloat(AnimatedProperty.PROPERTY_NAME_PIVOT_Y);
            if (this.editable) {
                View view = this.wallpaperLayer;
                if (view != null) {
                    baseTemplateView = this;
                    view.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseTemplateView.adapterWallpaperLayerSizeForFullAod$lambda$59(f2, f3, baseTemplateView, f4, f5);
                        }
                    });
                } else {
                    baseTemplateView = this;
                }
                View view2 = baseTemplateView.depthVideoForegroundLayer;
                if (view2 != null) {
                    view2.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda19
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseTemplateView.adapterWallpaperLayerSizeForFullAod$lambda$61(f2, f3, baseTemplateView, f4, f5);
                        }
                    });
                }
            } else {
                baseTemplateView = this;
            }
            View view3 = baseTemplateView.hierarchyLayer;
            if (view3 != null) {
                final float f6 = f2;
                final BaseTemplateView baseTemplateView2 = baseTemplateView;
                view3.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseTemplateView.adapterWallpaperLayerSizeForFullAod$lambda$63(this.f$0, f4, f5, f6, f3);
                    }
                });
            }
        }
    }

    public void addAIGenerateButton() {
    }

    /* JADX WARN: Code duplicated, block: B:104:0x0180  */
    /* JADX WARN: Code duplicated, block: B:109:0x0195  */
    /* JADX WARN: Code duplicated, block: B:112:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:114:0x01a8  */
    /* JADX WARN: Code duplicated, block: B:115:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:117:0x01af  */
    /* JADX WARN: Code duplicated, block: B:119:0x01b6  */
    public void addLayers(@Nullable CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        View viewCreateWallpaperLayer$default;
        CombinedWallpaperView combinedWallpaperView;
        HomeConfig homeInfo;
        HomeConfig homeInfo2;
        MultiTouchFrameLayout multiTouchFrameLayout;
        TemplateConfig lockscreenInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo;
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("BaseTemplateView:addLayers:createClockLayer");
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        String resourceType = null;
        this.miuiClockView = createClockLayer(context, (commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo.getTemplateId(), this.editFragment);
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
            Trace.beginSection("BaseTemplateView:addLayers:createWallpaperLayer");
        }
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        View viewCreateWallpaperLayer$default2 = createWallpaperLayer$default(this, context2, (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo(), this.isAnimLayer, this.editFragment, null, 16, null);
        if (viewCreateWallpaperLayer$default2 != null) {
            addViewToTemplate$default(this, viewCreateWallpaperLayer$default2, 0, 2, null);
        } else {
            viewCreateWallpaperLayer$default2 = null;
        }
        this.wallpaperLayer = viewCreateWallpaperLayer$default2;
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            wallpaperLayer.addGestureListener(this.wallpaperChangedListener);
        }
        CombinedWallpaperView combinedWallpaperView2 = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView2 != null) {
            combinedWallpaperView2.setOnceCombineEditListener(this);
        }
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
            Trace.beginSection("BaseTemplateView:addLayers:addClockLayer");
        }
        BaseClockView baseClockView = this.miuiClockView;
        if (baseClockView != null) {
            addViewToTemplate$default(this, baseClockView, 0, 2, null);
            if (this.editable && (multiTouchFrameLayout = (MultiTouchFrameLayout) baseClockView.getForeContentLayer()) != null) {
                View view = this.wallpaperLayer;
                CombinedWallpaperView combinedWallpaperView3 = view instanceof CombinedWallpaperView ? (CombinedWallpaperView) view : null;
                if (combinedWallpaperView3 != null) {
                    combinedWallpaperView3.setObserveTouchClickListener(multiTouchFrameLayout);
                }
            }
        }
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
            Trace.beginSection("BaseTemplateView:addLayers:createHierarchyLayer");
        }
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        View viewCreateHierarchyLayer = createHierarchyLayer(context3, commonConfig != null ? commonConfig.getLockscreenInfo() : null);
        if (viewCreateHierarchyLayer != null) {
            BaseClockView baseClockView2 = this.miuiClockView;
            if (baseClockView2 != null) {
                baseClockView2.addHierarchyLayer(viewCreateHierarchyLayer);
            }
        } else {
            viewCreateHierarchyLayer = null;
        }
        this.hierarchyLayer = viewCreateHierarchyLayer;
        Context context4 = getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        View viewCreateDepthVideoForegroundLayer = createDepthVideoForegroundLayer(context4, commonConfig != null ? commonConfig.getLockscreenInfo() : null, this.isAnimLayer);
        if (viewCreateDepthVideoForegroundLayer != null) {
            BaseClockView baseClockView3 = this.miuiClockView;
            if (baseClockView3 != null) {
                baseClockView3.addDepthVideoForegroundLayer(viewCreateDepthVideoForegroundLayer);
            }
            VideoTextureView videoTextureView = viewCreateDepthVideoForegroundLayer instanceof VideoTextureView ? (VideoTextureView) viewCreateDepthVideoForegroundLayer : null;
            if (videoTextureView != null) {
                HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
                videoTextureView.setAvoidController(hierarchyImageView != null ? hierarchyImageView.getAvoidController() : null);
            }
        } else {
            viewCreateDepthVideoForegroundLayer = null;
        }
        this.depthVideoForegroundLayer = viewCreateDepthVideoForegroundLayer;
        adapterWallpaperLayerSizeForFullAod();
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
            Trace.beginSection("BaseTemplateView:addLayers:createHomeWallpaperLayer");
        }
        if (getContext() instanceof DeviceScreenshotHelper.ScreenshotContext) {
            if (commonConfig == null || (homeInfo2 = commonConfig.getHomeInfo()) == null || homeInfo2.getFollowStatus() != 2) {
                Context context5 = getContext();
                Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                if (commonConfig != null || (homeInfo = commonConfig.getHomeInfo()) == null) {
                    wallpaperInfo2 = null;
                } else {
                    wallpaperInfo2 = homeInfo.getWallpaperInfo();
                }
                viewCreateWallpaperLayer$default = createWallpaperLayer$default(this, context5, wallpaperInfo2, this.isAnimLayer, this.editFragment, null, 16, null);
                if (viewCreateWallpaperLayer$default != null) {
                    if (viewCreateWallpaperLayer$default instanceof CombinedWallpaperView) {
                        combinedWallpaperView = (CombinedWallpaperView) viewCreateWallpaperLayer$default;
                    } else {
                        combinedWallpaperView = null;
                    }
                    if (combinedWallpaperView != null) {
                        combinedWallpaperView.setOnceCombineEditListener(this);
                    }
                    addViewToTemplate$default(this, viewCreateWallpaperLayer$default, 0, 2, null);
                } else {
                    viewCreateWallpaperLayer$default = null;
                }
                this.homeWallpaperLayer = viewCreateWallpaperLayer$default;
            } else {
                WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                Integer numValueOf = wallpaperInfo3 != null ? Integer.valueOf(wallpaperInfo3.getMagicType()) : null;
                WallpaperInfo wallpaperInfo4 = commonConfig.getHomeInfo().getWallpaperInfo();
                if (Intrinsics.areEqual(numValueOf, wallpaperInfo4 != null ? Integer.valueOf(wallpaperInfo4.getMagicType()) : null)) {
                    CombineEditListener combineEditListener = this.combineEditListener;
                    if (combineEditListener != null) {
                        combineEditListener.onTemplateReady(1);
                    }
                } else {
                    Context context6 = getContext();
                    Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
                    if (commonConfig != null) {
                        wallpaperInfo2 = null;
                    } else {
                        wallpaperInfo2 = null;
                    }
                    viewCreateWallpaperLayer$default = createWallpaperLayer$default(this, context6, wallpaperInfo2, this.isAnimLayer, this.editFragment, null, 16, null);
                    if (viewCreateWallpaperLayer$default != null) {
                        if (viewCreateWallpaperLayer$default instanceof CombinedWallpaperView) {
                            combinedWallpaperView = (CombinedWallpaperView) viewCreateWallpaperLayer$default;
                        } else {
                            combinedWallpaperView = null;
                        }
                        if (combinedWallpaperView != null) {
                            combinedWallpaperView.setOnceCombineEditListener(this);
                        }
                        addViewToTemplate$default(this, viewCreateWallpaperLayer$default, 0, 2, null);
                    } else {
                        viewCreateWallpaperLayer$default = null;
                    }
                    this.homeWallpaperLayer = viewCreateWallpaperLayer$default;
                }
            }
        }
        if (this.editable && this.from == 1) {
            CommonConfig commonConfig2 = this.templateConfig;
            if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                resourceType = wallpaperInfo.getResourceType();
            }
            if (TextUtils.equals(resourceType, "video")) {
                this.needResetBackground = true;
            }
            this.state = 2;
            startForcePlayerWithDepthVideo("add layers");
        }
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
    }

    public void addMiuiClockViewInitListener(@NotNull Runnable listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.addMiuiClockViewInitListener(listener);
        }
    }

    public final void addViewToTemplate(@NotNull View view, int i) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (i == -1 && this.loadingView != null) {
            i = RangesKt.coerceAtLeast(getChildCount() - 1, -1);
        }
        addView(view, i);
    }

    public final Triple adjustClockColor(ClockInfo clockInfo, String str, int i, int i2, int i3) {
        boolean z = clockInfo != null && clockInfo.getUserModifiedPrimaryColor();
        boolean z2 = clockInfo != null && clockInfo.getUserModifiedSecondaryColor();
        if (!z) {
            i = 0;
        }
        if (Intrinsics.areEqual(str, "doodle")) {
            if (!z2 || i3 == 0) {
                i2 = getContext().getColor(R.color.kg_editor_template_default_background_color_doodle);
                i3 = 13;
            }
        } else if (!z2) {
            i3 = 0;
        }
        return new Triple(Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public final int adjustClockEffect(int i, String str, BaseClockView baseClockView) {
        if (Intrinsics.areEqual(str, "doodle")) {
            return 0;
        }
        if (4 == i && !baseClockView.supportGradientBlur()) {
            return 1;
        }
        if (5 != i || Intrinsics.areEqual(str, "all_in_one")) {
            return i;
        }
        return 1;
    }

    public final void allReady(@Nullable final Function0<Unit> function0) {
        EditorLoadingView editorLoadingView = this.loadingView;
        if (editorLoadingView != null) {
            editorLoadingView.hideLoading(new Function0() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return BaseTemplateView.allReady$lambda$10(this.f$0, function0);
                }
            });
        }
    }

    public void applyPrimaryMode() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.applyPrimaryMode();
        }
    }

    public void applySecondaryMode() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.applySecondaryMode();
        }
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0073  */
    public void applyTemplateInfo() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        WallpaperPositionInfo position;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        BaseClockView baseClockView = this.miuiClockView;
        Bitmap currentWallpaper = null;
        if (baseClockView != null) {
            CommonConfig commonConfig2 = this.templateConfig;
            baseClockView.applyTemplateInfo(commonConfig2 != null ? commonConfig2.getLockscreenInfo() : null);
        }
        CommonConfig commonConfig3 = this.templateConfig;
        if (commonConfig3 == null || (lockscreenInfo = commonConfig3.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) {
            return;
        }
        SegmentInfo subjectInfo = getSubjectInfo();
        wallpaperInfo.setSubjectBitmap(subjectInfo != null ? subjectInfo.getBitmap() : null);
        Wallpaper.Companion companion = Wallpaper.Companion;
        String resourceType = wallpaperInfo.getResourceType();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (companion.needSmallSensorWallpaper(resourceType, context)) {
            CommonConfig commonConfig4 = this.templateConfig;
            if (((commonConfig4 == null || (lockscreenInfo4 = commonConfig4.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getCurrentSmallSensorWallpaper()) != null) {
                CommonConfig commonConfig5 = this.templateConfig;
                if (commonConfig5 != null && (lockscreenInfo3 = commonConfig5.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null) {
                    currentWallpaper = wallpaperInfo3.getCurrentSmallSensorWallpaper();
                }
            } else {
                commonConfig = this.templateConfig;
                if (commonConfig != null) {
                    currentWallpaper = wallpaperInfo2.getCurrentWallpaper();
                }
            }
        } else {
            commonConfig = this.templateConfig;
            if (commonConfig != null && (lockscreenInfo2 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                currentWallpaper = wallpaperInfo2.getCurrentWallpaper();
            }
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer == null || (position = wallpaperLayer.getPosition(currentWallpaper)) == null) {
            return;
        }
        wallpaperInfo.setPositionInfo(position);
    }

    public int backContentLayerId() {
        return 0;
    }

    public int backgroundColorMode() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Bitmap currentWallpaper;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || (currentWallpaper = wallpaperInfo.getCurrentWallpaper()) == null) {
            return 0;
        }
        return BitmapUtil.INSTANCE.getColorMode(currentWallpaper);
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public void beforeApply() {
    }

    public final void beginDrawing(@NotNull Canvas canvas) {
        List<View> hideViewInScreenShot;
        List<View> listFilterNotNull;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (!this.isEnterSecond || (hideViewInScreenShot = getHideViewInScreenShot()) == null || (listFilterNotNull = CollectionsKt.filterNotNull(hideViewInScreenShot)) == null) {
            return;
        }
        for (View view : listFilterNotNull) {
            RectF rectF = new RectF();
            ViewUtil.INSTANCE.getShowRect(view, rectF, getScaleX(), getScaleY());
            Matrix matrix = new Matrix();
            Matrix matrix2 = new Matrix();
            matrix.postScale(getScaleX(), getScaleY(), getPivotX(), getPivotY());
            matrix.invert(matrix2);
            matrix2.mapRect(rectF);
            canvas.clipOutRect(rectF);
        }
    }

    public final void cacheBottomSheet(@NotNull AutoBottomSheet bottomSheet) {
        Intrinsics.checkNotNullParameter(bottomSheet, "bottomSheet");
        this.customFontColorBottomSheet = bottomSheet;
    }

    public final boolean canDownloadButton(@Nullable WallpaperInfo wallpaperInfo) {
        if ((wallpaperInfo != null ? wallpaperInfo.getDownloadUrl() : null) == null || !FileUtil.isSupportVideoDepth()) {
            return false;
        }
        CommonUtils commonUtils = CommonUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (commonUtils.checkThemeSupportDownload(context)) {
            return wallpaperInfo.getVideoType() == 0 || wallpaperInfo.getVideoType() == 1;
        }
        return false;
    }

    @CallSuper
    public final void changeEditMode(int i, int i2) {
        onEditModeChanged(i, i2);
        EditModeChangedListener editModeChangedListener = this.editModeChangedListener;
        if (editModeChangedListener != null) {
            editModeChangedListener.onEditModeChanged(i, i2);
        }
    }

    public void checkColorModesChanged(@Nullable Bitmap bitmap, boolean z) {
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(bitmap, z, null), 3, null);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback
    public void checkNeedRemoveBackground() {
        IntWrapper followStatus;
        FollowSystemListener followSystemListener;
        if (this.needRemoveBackground) {
            this.needRemoveBackground = false;
            IWallpaperLayer wallpaperLayer = getWallpaperLayer();
            if (wallpaperLayer != null) {
                ((CombinedWallpaperView) wallpaperLayer).setBackgroundDrawable(null);
            }
            EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
            if (viewModel == null || (followStatus = viewModel.getFollowStatus()) == null || followStatus.getIntValue() != 2 || (followSystemListener = this.followSystemListener) == null) {
                return;
            }
            followSystemListener.trigger(4, null);
        }
    }

    public final void checkThemeCTA() {
        DepthPresetDownloadButton depthPresetDownloadButton = this.depthPresetDownloadBtn;
        if (depthPresetDownloadButton == null || !depthPresetDownloadButton.getNeedCheck()) {
            return;
        }
        depthPresetDownloadButton.setNeedCheck(false);
        if (depthPresetDownloadButton.getDownloadManager().checkThemeCTA()) {
            Log.i("Keyguard-Editor-BaseTemplateView", "depthPresetDownloadBtn needCheck");
            depthPresetDownloadButton.bind();
        }
    }

    public final void checkVideoValid(@Nullable Integer num, @Nullable String str) {
        FastPlayerManager.VideoMetaData videoMetaData = this.fastPlayerManager.getVideoMetaData();
        if (videoMetaData.getWidth() < 0 || videoMetaData.getHeight() < 0 || videoMetaData.getDuration() < 0 || videoMetaData.getRotation() < 0 || videoMetaData.getFrameRate() < 0.0f || MagicType.INSTANCE.isDepthVideo(num)) {
            return;
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C01011(str, videoMetaData, null), 2, null);
    }

    public final void cleanupTempDepthSource() {
        File[] fileArrListFiles = getContext().getCacheDir().listFiles();
        if (fileArrListFiles != null) {
            ArrayList arrayList = new ArrayList();
            for (File file : fileArrListFiles) {
                String name = file.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                if (StringsKt.startsWith$default(name, "depth_source_temp_", false, 2, null)) {
                    arrayList.add(file);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((File) it.next()).delete();
            }
        }
    }

    public final void cleanupVideoForCrossFade() {
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        CombinedWallpaperView combinedWallpaperView = wallpaperLayer instanceof CombinedWallpaperView ? (CombinedWallpaperView) wallpaperLayer : null;
        if (combinedWallpaperView != null) {
            combinedWallpaperView.setBackgroundDrawable(null);
        }
        releaseFastPlayer();
    }

    public void clearEditeFocus() {
    }

    public final void clearEditeStatus() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.clearEditeStatus();
        }
    }

    public final void clearGalleryContent() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) {
            return;
        }
        wallpaperInfo.setGalleryContent("");
    }

    public final void clearSubjectWhenSuperWallpaper(WallpaperTypeInfo wallpaperTypeInfo) {
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (!StringsKt.equals$default(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null, CategoryConstants.CATEGORY_SUPER_WALLPAPER, false, 2, null) || (commonConfig = this.templateConfig) == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) {
            return;
        }
        wallpaperInfo.setSubject(null);
        wallpaperInfo.resetMagicType("clear subject");
    }

    public final boolean completeWithCacheColor(boolean z) {
        ColorData colorDataMyColorData;
        if (!this.editable && !this.isAodDialogTemplate && (colorDataMyColorData = ColorPickerCache.INSTANCE.myColorData()) != null) {
            Log.i("Keyguard-Editor-BaseTemplateView", "startPickColorFromWallpaper: use cache");
            onColorPickComplete(colorDataMyColorData, z);
            return true;
        }
        if (this.isAodDialogTemplate) {
            Log.i("Keyguard-Editor-BaseTemplateView", "current is aod dialog template");
            ColorData colorDataAodColorData = ColorPickerCache.INSTANCE.aodColorData();
            if (colorDataAodColorData != null) {
                Log.i("Keyguard-Editor-BaseTemplateView", "startPickColorFromWallpaper: use cache");
                onColorPickComplete(colorDataAodColorData, z);
                return true;
            }
        }
        if (this.editable && !z) {
            ColorData colorDataEditableColorData = ColorPickerCache.INSTANCE.editableColorData();
            Log.i("Keyguard-Editor-BaseTemplateView", "editable cache " + (colorDataEditableColorData != null));
            if (colorDataEditableColorData != null) {
                Log.i("Keyguard-Editor-BaseTemplateView", "startPickColorFromWallpaper: use editable cache");
                onColorPickComplete(colorDataEditableColorData, z);
                return true;
            }
        }
        return false;
    }

    public void controlTopButtonState(boolean z) {
        FrameLayout frameLayout = this.topButtonBar;
        if (frameLayout != null) {
            AnimatorKt.alphaTo$default(frameLayout, z ? 1.0f : 0.0f, 0.0f, 0.0f, null, 14, null);
        }
        TextView textView = (TextView) getRootView().findViewById(R.id.kg_btn_editor_cancel);
        if (textView != null) {
            textView.setClickable(z);
        }
        TextView textView2 = (TextView) getRootView().findViewById(R.id.kg_btn_editor_apply);
        if (textView2 != null) {
            textView2.setClickable(z);
        }
    }

    @Nullable
    public AutoColorPicker createAutoColorPicker() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            return baseClockView.createAutoColorPicker();
        }
        return null;
    }

    @Nullable
    public BaseClockView createClockLayer(@NotNull Context context, @Nullable String str, @Nullable EditFragment editFragment) {
        Intrinsics.checkNotNullParameter(context, "context");
        BaseClockView baseClockViewCreate = TemplateViewFactory.INSTANCE.create(context, str);
        if (baseClockViewCreate == null) {
            return null;
        }
        baseClockViewCreate.init(this, editFragment);
        baseClockViewCreate.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        baseClockViewCreate.setFontStyleEditCallback(this.fontStyleEditCallback);
        return baseClockViewCreate;
    }

    @Nullable
    public ColorPickHandler createColorPickHandler() {
        ColorPickHandler colorPickHandlerCreateColorPickHandler;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView == null || (colorPickHandlerCreateColorPickHandler = baseClockView.createColorPickHandler()) == null) {
            return null;
        }
        return colorPickHandlerCreateColorPickHandler;
    }

    @Nullable
    public View createDepthVideoForegroundLayer(@NotNull Context context, @Nullable TemplateConfig templateConfig, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    @Nullable
    public View createHierarchyLayer(@NotNull Context context, @Nullable TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    @Nullable
    public View createWallpaperLayer(@NotNull Context context, @Nullable WallpaperInfo wallpaperInfo, boolean z, @Nullable EditFragment editFragment, @Nullable final String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (wallpaperInfo == null) {
            Log.w("Keyguard-Editor-BaseTemplateView", "createWallpaperLayer: wallpaperInfo is null");
            try {
                KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "BaseTemplateView.createWallpaperLayer_wallpaperInfo_null", null, 2, null);
            } catch (Exception unused) {
            }
            return null;
        }
        CombinedWallpaperView combinedWallpaperViewCreateWallpaperView = IWallpaperLayer.Companion.createWallpaperView(context, wallpaperInfo, this.editable, z, this.isAodDialogTemplate, editFragment, str, this.enableOriginWallpaperPlaceholder);
        combinedWallpaperViewCreateWallpaperView.setWallpaperCallback(this);
        if (this.editable) {
            combinedWallpaperViewCreateWallpaperView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$createWallpaperLayer$2$1
            });
            setAccessibilityDelegate(combinedWallpaperViewCreateWallpaperView.getAccessibilityDelegate());
            return combinedWallpaperViewCreateWallpaperView;
        }
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView != null) {
            combinedWallpaperView.requestRender();
        }
        postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                BaseTemplateView.createWallpaperLayer$lambda$101$lambda$100(this.f$0, str);
            }
        }, 500L);
        return combinedWallpaperViewCreateWallpaperView;
    }

    @Nullable
    public Rect currentColorPickupRect() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            return baseClockView.currentColorPickupRect();
        }
        return null;
    }

    public final void disableTouch() {
        this.editable = false;
    }

    public final void dismissAutoBottomSheetIfNeed() {
        AutoBottomSheet autoBottomSheet = this.wallpaperFilterAutoBottomSheet;
        if (autoBottomSheet == null || !autoBottomSheet.isShowing()) {
            return;
        }
        autoBottomSheet.dismiss();
    }

    public final void dismissCancelDialog() {
        AlertDialog alertDialog = this.alertDialog;
        if (alertDialog != null) {
            if (alertDialog != null) {
                alertDialog.dismissWithoutAnimation();
            }
            this.alertDialog = null;
        }
    }

    public final void dismissDepthVideoProgressDialogOnInterrupt() {
        Log.i("Keyguard-Editor-BaseTemplateView", "dismissDepthVideoProgressDialogOnInterrupt");
        this.isDepthProgressInterrupted = false;
        ImageSegmentHandler.INSTANCE.removeVideoSegmentServiceListener(this.depthVideoAiServiceListener);
        VideoSegmentAbility videoSegmentAbility = this.videoSegmentAbility;
        if (videoSegmentAbility == null || !videoSegmentAbility.isSupport()) {
            FastPlayer fastPlayer = this.produceVideoFastPlayer;
            if (fastPlayer != null) {
                fastPlayer.release();
            }
        } else {
            VideoSegmentAbility videoSegmentAbility2 = this.videoSegmentAbility;
            if (videoSegmentAbility2 != null) {
                videoSegmentAbility2.destroy();
            }
        }
        AlertDialog alertDialog = this.depthVideoProgressDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel != null) {
            EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 6, false, false, false, 14, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(@Nullable MotionEvent motionEvent) {
        if (this.editable) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return false;
    }

    public final boolean editable() {
        return this.editable;
    }

    public void editingTemplateActionBeforeTakeScreenshot() {
    }

    public final void ensureQ18SensorOuterVideo(@Nullable WallpaperTypeInfo wallpaperTypeInfo) {
        SensorWallpaperPrams sensorWallpaperPrams;
        if (wallpaperTypeInfo == null || (sensorWallpaperPrams = wallpaperTypeInfo.getSensorWallpaperPrams()) == null || !WallpaperController.Companion.fillQ18SensorOuterVideoIfNeeded$default(WallpaperController.Companion, wallpaperTypeInfo.getType(), sensorWallpaperPrams, null, CollectionsKt.listOf((Object[]) new String[]{wallpaperTypeInfo.getResourcePath(), sensorWallpaperPrams.getOuterVideoPreViewPath(), sensorWallpaperPrams.getOuterVideoPath(), sensorWallpaperPrams.getSmallThumbnailPath(), sensorWallpaperPrams.getThumbnailPath()}), 4, null)) {
            return;
        }
        Log.i("Keyguard-Editor-BaseTemplateView", "ensureQ18SensorOuterVideo: filled for selected wallpaper, path=" + wallpaperTypeInfo.getResourcePath());
    }

    public final void enterFullScreen() {
        applySecondaryMode();
        this.isEnterSecond = true;
    }

    public final void exitFullScreen(boolean z) {
        WallpaperEditor.Companion companion = WallpaperEditor.Companion;
        WallpaperInfo wallpaperInfo = getWallpaperInfo();
        boolean zCanScale = companion.canScale(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null);
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            wallpaperLayer.setScaleable(Boolean.valueOf(zCanScale));
        }
        if (!this.isDualClock) {
            hideAllFrame();
            exitEdite();
        }
        if (z) {
            setClipToOutline(true);
        } else {
            AnimatorKt.animateOutFullScreen(this, getResources().getDimension(R.dimen.kg_template_view_radius));
        }
        applyPrimaryMode();
        this.isEnterSecond = false;
    }

    public void exitWithAnimation() {
        TemplateButtonBar templateButtonBar = this.primaryButtonBar;
        if (templateButtonBar != null) {
            templateButtonBar.hide();
        }
        exitEditFrame();
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView != null) {
            WallpaperInfo wallpaperInfo = getWallpaperInfo();
            combinedWallpaperView.setBackground(new BitmapDrawable(wallpaperInfo != null ? wallpaperInfo.getCurrentWallpaper() : null));
        }
        this.isExit = true;
    }

    @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
    public void finish() {
        Consumer consumer = this.navigationCallback;
        if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
    }

    public int foreContentLayerId() {
        return 0;
    }

    public final Bitmap getActualBitmap(Bitmap bitmap) {
        GestureManager gestureManager;
        RectF positionRect;
        if (bitmap == null) {
            Log.d("Keyguard-Editor-BaseTemplateView", "getActualBitmap bitmap is null");
            return null;
        }
        if (DeviceUtil.INSTANCE.isPad()) {
            IWallpaperLayer wallpaperLayer = getWallpaperLayer();
            CombinedWallpaperView combinedWallpaperView = wallpaperLayer instanceof CombinedWallpaperView ? (CombinedWallpaperView) wallpaperLayer : null;
            if (combinedWallpaperView != null && (gestureManager = combinedWallpaperView.getGestureManager()) != null && (positionRect = gestureManager.getPositionRect(bitmap)) != null) {
                Log.d("Keyguard-Editor-BaseTemplateView", "rectF=" + positionRect + " bitmap.width=" + bitmap.getWidth() + " bitmap.height=" + bitmap.getHeight());
                if (bitmap.getWidth() >= positionRect.right) {
                    float height = bitmap.getHeight();
                    float f = positionRect.bottom;
                    if (height >= f) {
                        Bitmap bitmapCropBitmap = BitmapUtil.INSTANCE.cropBitmap(bitmap, (int) positionRect.left, (int) positionRect.right, (int) positionRect.top, (int) f);
                        return bitmapCropBitmap == null ? bitmap : bitmapCropBitmap;
                    }
                }
            }
        }
        return bitmap;
    }

    @Nullable
    public final KgVisualCheckBox getAiGenerateBtn() {
        return this.aiGenerateBtn;
    }

    public final boolean getAllowToClickDynamicSwitchBtn() {
        return this.allowToClickDynamicSwitchBtn;
    }

    @Nullable
    public final TemplateApplier.ApplyCallback getApplyTransition() {
        return this.applyTransition;
    }

    public final boolean getApplying() {
        return this.applying;
    }

    @Nullable
    public final AutoColorPicker getAutoColorPicker() {
        return this.autoColorPicker;
    }

    @Nullable
    public final <T extends View> T getClockLayer() {
        BaseClockView baseClockView = this.miuiClockView;
        if (baseClockView != null) {
            return baseClockView;
        }
        return null;
    }

    @Nullable
    public final ColorModes getColorModes() {
        return this.colorModes;
    }

    @Nullable
    public final ColorPickHandler getColorPickHandler() {
        return this.colorPickHandler;
    }

    @Nullable
    public final CombineEditListener getCombineEditListener() {
        return this.combineEditListener;
    }

    public final Object getCurCropWallpaper(Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getDefault(), new AnonymousClass2(null), continuation);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getCurrentSelect() {
        return CombineEditListener.DefaultImpls.getCurrentSelect(this);
    }

    @Nullable
    public WallpaperInfo getCurrentWallpaperInfo() {
        TemplateConfig lockscreenInfo;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) {
            return null;
        }
        return lockscreenInfo.getWallpaperInfo();
    }

    @Override // com.miui.keyguard.editor.edit.base.EditCallback
    @Nullable
    public Object getData(@NotNull String key, @NotNull Object... params) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(params, "params");
        if (!Intrinsics.areEqual(key, MagicType.MAGIC_TYPE)) {
            return EditCallback.DefaultImpls.getData(this, key, params);
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            return Integer.valueOf(wallpaperLayer.currentMagicType());
        }
        return null;
    }

    @Nullable
    public final DepthPresetDownloadButton getDepthPresetDownloadBtn() {
        return this.depthPresetDownloadBtn;
    }

    public final boolean getDepthVideoFirstFrameReady$keyguardeditor_release() {
        return this.depthVideoFirstFrameReady;
    }

    @Nullable
    public final View getDepthVideoForegroundLayer() {
        return this.depthVideoForegroundLayer;
    }

    @Nullable
    /* JADX INFO: renamed from: getDepthVideoForegroundLayer, reason: collision with other method in class */
    public final <T extends IWallpaperLayer> T m733getDepthVideoForegroundLayer() {
        KeyEvent.Callback callback = this.depthVideoForegroundLayer;
        if (callback instanceof IWallpaperLayer) {
            return (T) callback;
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getDialogOffset() {
        return CombineEditListener.DefaultImpls.getDialogOffset(this);
    }

    public final int getDisplayClockEffect() {
        return this.displayClockEffect;
    }

    @Nullable
    public final DynamicSwitchButton getDynamicSwitchBtn() {
        return this.dynamicSwitchBtn;
    }

    @NotNull
    public final DynamicSwitchCallback getDynamicSwitchCallback() {
        return this.dynamicSwitchCallback;
    }

    @Nullable
    public final EditFragment getEditFragment() {
        return this.editFragment;
    }

    public final boolean getEnableOriginWallpaperPlaceholder() {
        return this.enableOriginWallpaperPlaceholder;
    }

    @NotNull
    public final FastPlayerManager getFastPlayerManager() {
        return this.fastPlayerManager;
    }

    public int getFilterBottomSheetAlignModel() {
        return 1280;
    }

    @Nullable
    public final KgVisualCheckBox getFilterBtn() {
        return this.filterBtn;
    }

    @NotNull
    public FontFilter getFontFilter() {
        if (getClockLayer() == null) {
            return new FontFilter(new Integer[0], new FilterGroup[0][], false, 4, null);
        }
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        FontFilter fontFilter = baseClockView != null ? baseClockView.getFontFilter() : null;
        Intrinsics.checkNotNull(fontFilter);
        return fontFilter;
    }

    @Nullable
    public MiuiClockView getForeClock() {
        return null;
    }

    public final int getFrom() {
        return this.from;
    }

    @Nullable
    public String getFromWhereTag() {
        return "BaseTemplateView";
    }

    @Nullable
    public List<View> getHideViewInScreenShot() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            return baseClockView.getHideViewInScreenShot();
        }
        return null;
    }

    @Nullable
    public final KgVisualCheckBox getHierarchyBtn() {
        return this.hierarchyBtn;
    }

    public final boolean getHierarchyEnable() {
        return this.hierarchyEnable;
    }

    @Nullable
    public final <T extends View> T getHierarchyLayer() {
        T t = (T) this.hierarchyLayer;
        if (t != null) {
            return t;
        }
        return null;
    }

    public final boolean getHomeFollowWapper() {
        IntWrapper followStatus;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        int intValue = (viewModel == null || (followStatus = viewModel.getFollowStatus()) == null) ? 0 : followStatus.getIntValue();
        return intValue == 2 || intValue == 1;
    }

    @Nullable
    public final <T extends View> T getHomeWallpaperLayer() {
        T t = (T) this.homeWallpaperLayer;
        if (t != null) {
            return t;
        }
        return null;
    }

    public final boolean getInitFinish() {
        return this.initFinish;
    }

    @NotNull
    public List<View> getLayersForScreenshot() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return CollectionsKt.listOfNotNull((Object[]) new View[]{baseClockView != null ? baseClockView.getBackContentLayer() : null, getHierarchyLayer(), baseClockView != null ? baseClockView.getForeContentLayer() : null});
    }

    @Nullable
    public final EditorLoadingView getLoadingView() {
        return this.loadingView;
    }

    @Nullable
    public final View getMaskView() {
        return this.maskView;
    }

    @Nullable
    public final Consumer<Object> getNavigationCallback() {
        return this.navigationCallback;
    }

    public final boolean getNeedRemoveBackground() {
        return this.needRemoveBackground;
    }

    public final boolean getNeedResetBackground() {
        return this.needResetBackground;
    }

    @Nullable
    public final Function0<Unit> getOnCancelDialogNegativeClick() {
        return this.onCancelDialogNegativeClick;
    }

    @Nullable
    public final Function1<Boolean, Unit> getOnWallpaperDarkResultReadyListener() {
        return this.onWallpaperDarkResultReadyListener;
    }

    @Nullable
    public final CommonConfig getOriginConfig() {
        return this.originConfig;
    }

    public final boolean getPendingDismissAfterSwitch$keyguardeditor_release() {
        return this.pendingDismissAfterSwitch;
    }

    @Nullable
    public Bitmap getPickColorBitmap() {
        Bitmap currentWallpaper;
        GestureManager gestureManager;
        TemplateConfig lockscreenInfo;
        ColorSpace colorSpace;
        String name = null;
        if (!this.editable && !this.isAodDialogTemplate) {
            Bitmap bitmap = BitmapTempStore.Companion.getInstance().getBitmap("wallpaper_for_pick_color");
            Bitmap.Config config = bitmap != null ? bitmap.getConfig() : null;
            if (bitmap != null && (colorSpace = bitmap.getColorSpace()) != null) {
                name = colorSpace.getName();
            }
            Log.d("Keyguard-Editor-BaseTemplateView", "getPickColorBitmap: config=" + config + ", colorSpace=" + name);
            return bitmap;
        }
        CommonConfig commonConfig = this.templateConfig;
        WallpaperInfo wallpaperInfo = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo();
        if (wallpaperInfo == null || (currentWallpaper = wallpaperInfo.getCurrentWallpaper()) == null) {
            Log.d("Keyguard-Editor-BaseTemplateView", "getPickColorBitmap: currentWallpaper is null");
            return null;
        }
        String resourceType = wallpaperInfo.getResourceType();
        if (!Intrinsics.areEqual(resourceType, "image") && !Intrinsics.areEqual(resourceType, "gallery")) {
            int width = currentWallpaper.getWidth();
            int height = currentWallpaper.getHeight();
            ColorSpace colorSpace2 = currentWallpaper.getColorSpace();
            Log.d("Keyguard-Editor-BaseTemplateView", "getPickColorBitmap: wallpaper=" + width + AnimatedProperty.PROPERTY_NAME_X + height + ", colorSpace=" + (colorSpace2 != null ? colorSpace2.getName() : null));
            return currentWallpaper;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        WallpaperPositionInfo position = wallpaperLayer != null ? wallpaperLayer.getPosition(currentWallpaper) : null;
        if (position == null) {
            int width2 = currentWallpaper.getWidth();
            int height2 = currentWallpaper.getHeight();
            ColorSpace colorSpace3 = currentWallpaper.getColorSpace();
            Log.d("Keyguard-Editor-BaseTemplateView", "getPickColorBitmap: positionInfo is null, wallpaper=" + width2 + AnimatedProperty.PROPERTY_NAME_X + height2 + ", colorSpace=" + (colorSpace3 != null ? colorSpace3.getName() : null));
            return null;
        }
        IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
        CombinedWallpaperView combinedWallpaperView = wallpaperLayer2 instanceof CombinedWallpaperView ? (CombinedWallpaperView) wallpaperLayer2 : null;
        if (combinedWallpaperView != null && (gestureManager = combinedWallpaperView.getGestureManager()) != null) {
            gestureManager.trackPositionInfoChanged(position);
        }
        Bitmap bitmapCropBitmap = BitmapUtil.INSTANCE.cropBitmap(currentWallpaper, (int) position.getLeft(), (int) position.getRight(), (int) position.getTop(), (int) position.getBottom());
        if (bitmapCropBitmap == null) {
            bitmapCropBitmap = currentWallpaper;
        }
        int width3 = currentWallpaper.getWidth();
        int height3 = currentWallpaper.getHeight();
        ColorSpace colorSpace4 = currentWallpaper.getColorSpace();
        name = colorSpace4 != null ? colorSpace4.getName() : null;
        Log.d("Keyguard-Editor-BaseTemplateView", "getPickColorBitmap: wallpaper=" + width3 + AnimatedProperty.PROPERTY_NAME_X + height3 + ", colorSpace=" + name + ", cropped=" + bitmapCropBitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmapCropBitmap.getHeight() + ", positionInfo=(" + position.getLeft() + ac.b + position.getTop() + ac.b + position.getRight() + ac.b + position.getBottom() + ")");
        return bitmapCropBitmap;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0029 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:14:0x002b  */
    /* JADX WARN: Code duplicated, block: B:15:0x0030  */
    @Nullable
    public final WallpaperPositionInfo getPositionInfo(@Nullable WallpaperInfo wallpaperInfo) {
        Bitmap currentWallpaper;
        Wallpaper.Companion companion = Wallpaper.Companion;
        String resourceType = wallpaperInfo != null ? wallpaperInfo.getResourceType() : null;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (companion.needSmallSensorWallpaper(resourceType, context)) {
            if ((wallpaperInfo != null ? wallpaperInfo.getCurrentSmallSensorWallpaper() : null) != null) {
                currentWallpaper = wallpaperInfo.getCurrentSmallSensorWallpaper();
            } else if (wallpaperInfo != null) {
                currentWallpaper = wallpaperInfo.getCurrentWallpaper();
            } else {
                currentWallpaper = null;
            }
        } else if (wallpaperInfo != null) {
            currentWallpaper = wallpaperInfo.getCurrentWallpaper();
        } else {
            currentWallpaper = null;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            return wallpaperLayer.getPosition(currentWallpaper);
        }
        return null;
    }

    @Nullable
    public final View getPresetButtonGroup() {
        return this.presetButtonGroup;
    }

    @Nullable
    public final TemplateButtonBar getPrimaryButtonBar() {
        return this.primaryButtonBar;
    }

    public final boolean getShouldSetColorSelectionToAuto() {
        return this.shouldSetColorSelectionToAuto;
    }

    public final int getState() {
        return this.state;
    }

    @Nullable
    public SegmentInfo getSubjectInfo() {
        return null;
    }

    public final boolean getSuccessUpdateClockView() {
        return this.successUpdateClockView;
    }

    @NotNull
    public final TemplateApi getTemplateApi() {
        return this.templateApi;
    }

    @Nullable
    public final CommonConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public final int getTemplateDialogOffset() {
        CombineEditListener combineEditListener = this.combineEditListener;
        if (combineEditListener != null) {
            return combineEditListener.getDialogOffset();
        }
        return 0;
    }

    @Nullable
    public final String getTemplateId() {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) {
            return null;
        }
        return clockInfo.getTemplateId();
    }

    public final long getTemplateSource() {
        return this.templateSource;
    }

    @Nullable
    public final TextView getTemplateTitle() {
        return this.templateTitle;
    }

    @Nullable
    public final String getTrackId() {
        return this.trackId;
    }

    @Nullable
    public final Bitmap getTransitionBackground() {
        return this.transitionBackground;
    }

    public final boolean getVideoProcessedByAI() {
        return this.videoProcessedByAI;
    }

    @Nullable
    public Bitmap getWallpaperBitmapForDarkResult(@Nullable Bitmap bitmap, @Nullable Bitmap bitmap2) {
        return bitmap2;
    }

    @NotNull
    public final WallpaperEditor getWallpaperEditor() {
        return this.wallpaperEditor;
    }

    @NotNull
    public List<WallpaperFilter> getWallpaperFilter() {
        return WallpaperFilterKt.getDEFAULT_WALLPAPER_FILTER_DATA();
    }

    @Nullable
    public final AutoBottomSheet getWallpaperFilterAutoBottomSheet() {
        return this.wallpaperFilterAutoBottomSheet;
    }

    public final int getWallpaperFilterBottomSheetHeight() {
        return this.wallpaperFilterBottomSheetHeight;
    }

    @Nullable
    public final WallpaperFilterView getWallpaperFilterView() {
        return this.wallpaperFilterView;
    }

    public boolean getWallpaperHasChanged() {
        return this.wallpaperHasChanged;
    }

    @Nullable
    public WallpaperInfo getWallpaperInfo() {
        TemplateConfig lockscreenInfo;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) {
            return null;
        }
        return lockscreenInfo.getWallpaperInfo();
    }

    @Nullable
    public final <T extends IWallpaperLayer> T getWallpaperLayer() {
        KeyEvent.Callback callback = this.wallpaperLayer;
        if (callback instanceof IWallpaperLayer) {
            return (T) callback;
        }
        return null;
    }

    @Nullable
    public final WallpaperEditor.WallpaperPickerLauncher getWallpaperPickerLauncher() {
        return this.wallpaperPickerLauncher;
    }

    public final void handleDoodleTemplate(boolean z, String str) {
        IWallpaperLayer wallpaperLayer;
        if (z && !Intrinsics.areEqual(this.previousTemplateId, "doodle")) {
            IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
            this.previousMagicType = wallpaperLayer2 != null ? wallpaperLayer2.currentMagicType() : 0;
        }
        if (z) {
            IWallpaperLayer wallpaperLayer3 = getWallpaperLayer();
            if (wallpaperLayer3 != null) {
                wallpaperLayer3.switchGlassShader(0, null);
                return;
            }
            return;
        }
        if (!Intrinsics.areEqual(this.previousTemplateId, "doodle") || Intrinsics.areEqual(str, "doodle") || (wallpaperLayer = getWallpaperLayer()) == null) {
            return;
        }
        wallpaperLayer.switchGlassShader(this.previousMagicType, null);
    }

    @Override // com.miui.keyguard.editor.edit.aiwapper.IAIWallPaperChangeListener
    public void handleGenerateFailed(@Nullable Triple<String, String, Boolean> triple, int i) {
        IAIWallPaperChangeListener.DefaultImpls.handleGenerateFailed(this, triple, i);
    }

    public final int handleProduceVideoCallback(String str, int i, int i2, long j) {
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C01021(i, this, i2, str, j, null), 2, null);
        return 0;
    }

    public void hideAllFrame() {
        BaseClockView baseClockView;
        if (!this.editable || (baseClockView = (BaseClockView) getClockLayer()) == null) {
            return;
        }
        BaseClockView.hideAllFrame$default(baseClockView, false, 1, null);
    }

    public final boolean hierarchyEnable() {
        return this.hierarchyEnable;
    }

    public void inTalkBackCloseDialogs(@NotNull AutoBottomSheet... dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        AutoBottomSheet[] autoBottomSheetList = baseClockView != null ? baseClockView.getAutoBottomSheetList() : null;
        TalkBackUtil talkBackUtil = TalkBackUtil.INSTANCE;
        Object systemService = getContext().getSystemService("accessibility");
        if (talkBackUtil.isTalkBackEnabled(systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null)) {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (!deviceUtil.isPad()) {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                if (!deviceUtil.isFoldLargeScreen(context)) {
                    return;
                }
            }
            if (autoBottomSheetList != null) {
                for (AutoBottomSheet autoBottomSheet : autoBottomSheetList) {
                    this.isDismissTemplateTitle = false;
                    if (autoBottomSheet != null) {
                        autoBottomSheet.dismiss();
                    }
                }
            }
            this.isDismissTemplateTitle = false;
            AutoBottomSheet autoBottomSheet2 = this.wallpaperFilterAutoBottomSheet;
            if (autoBottomSheet2 != null) {
                autoBottomSheet2.dismiss();
            }
            BaseClockView baseClockView2 = (BaseClockView) getClockLayer();
            if (baseClockView2 != null) {
                baseClockView2.resetSpecialBottomSheet(true);
            }
        }
    }

    public void initAIWallPaperManager() {
    }

    public void initForeClock(@NotNull FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
    }

    public void initHierarchyButton(@NotNull TemplateButtonBar primaryButtonBar) {
        Intrinsics.checkNotNullParameter(primaryButtonBar, "primaryButtonBar");
    }

    public final void initLoading() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        EditorLoadingView editorLoadingView = new EditorLoadingView(context, R.color.kg_black_100);
        editorLoadingView.setPivotY(getPivotY());
        editorLoadingView.setPivotX(getPivotX());
        float f = 1;
        editorLoadingView.setScaleX(f / getScaleX());
        editorLoadingView.setScaleY(f / getScaleY());
        ((ProgressBar) editorLoadingView.findViewById(R.id.kg_hierarchy_loading)).setIndeterminateTintList(ColorStateList.valueOf(-1));
        ((TextView) editorLoadingView.findViewById(R.id.kg_hierarchy_loading_text)).setText(editorLoadingView.getContext().getResources().getString(R.string.kg_settings_template_loading));
        addViewToTemplate$default(this, editorLoadingView, 0, 2, null);
        this.loadingView = editorLoadingView;
        this.fastPlayerManager.isFirstFrameReady(new Function0() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseTemplateView.initLoading$lambda$8(this.f$0);
            }
        });
    }

    @CallSuper
    public void initTemplateBean(@NotNull TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        initTemplateBean(this.miuiClockView, templateConfig);
    }

    public final void initTemplateBean(BaseClockView baseClockView, TemplateConfig templateConfig) {
        if (baseClockView != null) {
            baseClockView.setDisplayClockEffect(this.displayClockEffect);
        }
        if (baseClockView != null) {
            baseClockView.initTemplateBean(templateConfig);
        }
    }

    @CallSuper
    @Nullable
    public TemplateButtonBar initializePrimaryButtonBar(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("createTemplateButtonBar");
        }
        TemplateButtonBar templateButtonBar = new TemplateButtonBar(parent, this, 0, 4, null);
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        this.primaryButtonBar = templateButtonBar;
        templateButtonBar.attach();
        if (!(getContext() instanceof CommonEditorActivity) && !(this instanceof HomeTemplateView)) {
            templateButtonBar.hide();
        }
        return templateButtonBar;
    }

    public final void interruptProduceDepthVideoProgressDialog(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        this.isDepthProgressInterrupted = false;
        ImageSegmentHandler.INSTANCE.removeVideoSegmentServiceListener(this.depthVideoAiServiceListener);
        AlertDialog alertDialog = this.depthVideoProgressDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel != null) {
            EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 6, false, false, false, 14, null);
        }
    }

    public final boolean isAiDepthNotSupported(long j) {
        VideoSegmentAbility videoSegmentAbility;
        return ((int) j) == 6 || (videoSegmentAbility = this.videoSegmentAbility) == null || !videoSegmentAbility.isSupport();
    }

    public final boolean isAnimLayer() {
        return this.isAnimLayer;
    }

    public final void isCallingFromKeyguard(boolean z) {
        this.isCallingFromKeyguard = z;
    }

    public final boolean isCallingFromKeyguard() {
        return this.isCallingFromKeyguard;
    }

    public final boolean isChangedTimeMagic() {
        return this.isChangedTimeMagic;
    }

    public final boolean isCheckBtn() {
        return this.isCheckBtn;
    }

    public final boolean isClockTemplateChanged() {
        return this.isClockTemplateChanged;
    }

    public final boolean isCreateForScreenShot() {
        return getContext() instanceof DeviceScreenshotHelper.ScreenshotContext;
    }

    public final boolean isCut() {
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            return wallpaperLayer.isCut();
        }
        return false;
    }

    public final boolean isDarkWallpaper() {
        return this.isDarkWallpaper;
    }

    public final boolean isDismissTemplateTitle() {
        return this.isDismissTemplateTitle;
    }

    public final boolean isDualClock() {
        return this.isDualClock;
    }

    public final boolean isEditHierarchy() {
        return this.isEditHierarchy;
    }

    public final boolean isEditTransition() {
        return this.isEditTransition;
    }

    public final boolean isEdited() {
        return edited;
    }

    public final boolean isEnableAlphaTitle() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        MagicType magicType = MagicType.INSTANCE;
        CommonConfig commonConfig = this.templateConfig;
        if (magicType.isDepthVideo((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getMagicType()))) {
            return !this.isGalleryOpened || this.isWallpaperChanged;
        }
        return false;
    }

    public final boolean isEnterSecond() {
        return this.isEnterSecond;
    }

    public final boolean isGalleryOpened() {
        return this.isGalleryOpened;
    }

    public final boolean isGlassClockEffect() {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        return viewModel != null && viewModel.getFontFilterId() == 5;
    }

    public final boolean isGlassDisabled() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig commonConfig = this.templateConfig;
        String resourceType = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType();
        return (Intrinsics.areEqual(resourceType, "image") || Intrinsics.areEqual(resourceType, "gallery")) ? false : true;
    }

    public final boolean isHistoryTemplate() {
        return this.isHistoryTemplate && !this.isWallpaperChanged;
    }

    public boolean isInThirdPartyWallpaperService() {
        TemplateConfig lockscreenInfo;
        CommonConfig commonConfig = this.templateConfig;
        return (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || !lockscreenInfo.isThirdPartyWallpaper() || getWallpaperHasChanged()) ? false : true;
    }

    public final boolean isInternationalGalleryOpen() {
        return DeviceUtil.INSTANCE.isInternationalBuild() && this.isGalleryOpened;
    }

    public final boolean isPositionInfoChanged() {
        return this.isPositionInfoChanged;
    }

    @Nullable
    public final Boolean isPreset() {
        return this.isPreset;
    }

    public final boolean isSelect() {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        WallpaperInfo wallpaperInfo = getWallpaperInfo();
        if ((wallpaperInfo != null ? wallpaperInfo.getInfoType() : null) == WallpaperInfoType.LOCK_WALLPAPER) {
            return viewModel != null && viewModel.getSelectTemplate() == 0;
        }
        return viewModel != null && viewModel.getSelectTemplate() == 1;
    }

    public final boolean isStopped() {
        return this.isStopped;
    }

    public final boolean isTemplateSwitching$keyguardeditor_release() {
        return this.isTemplateSwitching;
    }

    public final boolean isVideoToImageTransition(@Nullable String str, @Nullable String str2) {
        return TextUtils.equals(str, "video") && !TextUtils.equals(str2, "video");
    }

    public final boolean isWallpaperChanged() {
        return this.isWallpaperChanged;
    }

    public final boolean isWallpaperHasChanged() {
        return getWallpaperHasChanged();
    }

    public final void loadGalleryOpenedIfNeed() {
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new C01031(null), 3, null);
        }
    }

    public void loadOriginTempConfig(@Nullable CommonConfig commonConfig) {
        this.originConfig = commonConfig;
    }

    public void loadTemplate(@Nullable CommonConfig commonConfig) {
        loadTemplate(commonConfig, false);
    }

    public final void loadTemplate(CommonConfig commonConfig, boolean z) {
        WallpaperController companion;
        CommonConfig commonConfig2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        ExtraInfo extraInfo;
        TemplateConfig lockscreenInfo4;
        SignatureInfo signatureInfo;
        TemplateConfig lockscreenInfo5;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo6;
        WallpaperInfo wallpaperInfo5;
        EditorApplicationProxy.Companion companion2 = EditorApplicationProxy.Companion;
        if (companion2.getEDITOR_DEBUG()) {
            Trace.beginSection("BaseTemplateView:loadTemplate");
        }
        Integer loopType = null;
        if (Intrinsics.areEqual(commonConfig, this.templateConfig)) {
            Bitmap currentWallpaper = (commonConfig == null || (lockscreenInfo6 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo5 = lockscreenInfo6.getWallpaperInfo()) == null) ? null : wallpaperInfo5.getCurrentWallpaper();
            CommonConfig commonConfig3 = this.templateConfig;
            if (Intrinsics.areEqual(currentWallpaper, (commonConfig3 == null || (lockscreenInfo5 = commonConfig3.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo5.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getCurrentWallpaper()) && !z) {
                Log.i("Keyguard-Editor-BaseTemplateView", "loadTemplate: templateConfig is same, return");
                return;
            }
        }
        if (this.loadingView == null) {
            removeAllViews();
        }
        this.templateConfig = commonConfig;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel != null) {
            viewModel.setTemplateConfig(commonConfig != null ? commonConfig.getLockscreenInfo() : null);
        }
        Log.i("Keyguard-Editor-BaseTemplateView", "loadTemplate: " + commonConfig);
        CommonConfig commonConfig4 = this.templateConfig;
        int magicType = 0;
        if (commonConfig4 != null && (lockscreenInfo4 = commonConfig4.getLockscreenInfo()) != null) {
            ClockInfo clockInfo = lockscreenInfo4.getClockInfo();
            if (clockInfo != null && Intrinsics.areEqual(FontFilterKt.getFILTER_SUPPORT_INFO().get(Integer.valueOf(clockInfo.getClockEffect())), Boolean.FALSE)) {
                if (clockInfo.getClockEffect() == 5) {
                    clockInfo.setClockEffect(1);
                } else {
                    clockInfo.setClockEffect(0);
                }
            }
            updateDisplayClockEffect();
            Map filter_support_info = FontFilterKt.getFILTER_SUPPORT_INFO();
            SignatureInfo signatureInfo2 = lockscreenInfo4.getSignatureInfo();
            if (Intrinsics.areEqual(filter_support_info.get(signatureInfo2 != null ? Integer.valueOf(signatureInfo2.getClockEffect()) : null), Boolean.FALSE) && (signatureInfo = lockscreenInfo4.getSignatureInfo()) != null) {
                signatureInfo.setClockEffect(0);
            }
        }
        if (!DeviceUtil.INSTANCE.isInternationalBuild()) {
            FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            CommonConfig commonConfig5 = this.templateConfig;
            if (commonConfig5 != null && (lockscreenInfo3 = commonConfig5.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null && (extraInfo = wallpaperInfo3.getExtraInfo()) != null) {
                loopType = extraInfo.getLoopType();
            }
            setGalleryOpened(fashionGalleryHelper.isLoop(context, true, loopType));
        }
        WallpaperController.Companion companion3 = WallpaperController.Companion;
        WallpaperController companion4 = companion3.getInstance();
        if (this.isGalleryOpened || (getTemplateSource() == -1 && companion4 != null && companion4.needResetMagicType())) {
            CommonConfig commonConfig6 = this.templateConfig;
            if (commonConfig6 != null && (lockscreenInfo2 = commonConfig6.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                magicType = wallpaperInfo2.getMagicType();
            }
            if (MagicType.INSTANCE.isShader(magicType) && (companion = companion3.getInstance()) != null && companion.isWallpaperSupportWaitImage() && (commonConfig2 = this.templateConfig) != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.resetMagicType("gallery open when loadTemplate");
            }
        }
        if (companion2.getEDITOR_DEBUG()) {
            Trace.beginSection("BaseTemplateView:addLayers");
        }
        addLayers(commonConfig);
        CommonConfig commonConfig7 = this.templateConfig;
        if (commonConfig7 != null) {
            initTemplateBean(this.miuiClockView, commonConfig7.getLockscreenInfo());
        }
        if (companion2.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        if (this.editable) {
            this.colorPickHandler = createColorPickHandler();
            exitFullScreen(true);
        }
    }

    public final boolean lockPageFocus() {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        Intrinsics.checkNotNull(viewModel);
        return viewModel.getSelectTemplate() == 0;
    }

    public final void markClockTemplateChanged(boolean z) {
        this.isClockTemplateChanged = z;
    }

    public final void markEdited() {
        EditFragment editFragment = this.editFragment;
        if (editFragment == null || !editFragment.isUserOperation()) {
            return;
        }
        edited = this.initFinish;
    }

    public final void markIsAodDialogTemplate() {
        this.isAodDialogTemplate = true;
    }

    @Nullable
    public final Matrix obtainGlassMatrix(@Nullable Matrix matrix, int i) {
        IWallpaperLayer wallpaperLayer;
        View viewWallpaperLayerView;
        if (matrix == null || !MagicType.INSTANCE.isGlassVertical(i) || (wallpaperLayer = getWallpaperLayer()) == null || (viewWallpaperLayerView = wallpaperLayer.wallpaperLayerView()) == null) {
            return matrix;
        }
        IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
        float glassInitScaleX = wallpaperLayer2 != null ? wallpaperLayer2.getGlassInitScaleX() : 1.0f;
        Log.d("Keyguard-Editor-BaseTemplateView", "obtainMatrix:current glass init scale x is " + glassInitScaleX);
        if (glassInitScaleX == 0.0f || glassInitScaleX == 1.0f) {
            return matrix;
        }
        Matrix matrix2 = new Matrix();
        matrix2.set(matrix);
        float f = 2;
        matrix2.postScale(1.0f / glassInitScaleX, 1.0f, (viewWallpaperLayerView.getWidth() * 1.0f) / f, (viewWallpaperLayerView.getHeight() * 1.0f) / f);
        return matrix2;
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public /* bridge */ /* synthetic */ void onAccessibilityHighTextEnableChange(boolean z) {
        super.onAccessibilityHighTextEnableChange(z);
    }

    @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
    public void onApplied(boolean z, @Nullable TemplateApplier.ApplyCallback applyCallback, @Nullable ExitAnimationPerformer exitAnimationPerformer) {
        Consumer consumer;
        CombineEditView combineEditView;
        HomeTemplateView homeTemplateView;
        this.applying = false;
        TemplateApplier.ApplyCallback applyCallback2 = this.applyTransition;
        if (applyCallback2 != null) {
            applyCallback2.onApplied(z, applyCallback, exitAnimationPerformer);
        }
        if (z) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            CommonConfig commonConfigOriginTempConfig = originTempConfig();
            CommonConfig commonConfigTemplateConfig = templateConfig();
            Boolean boolValueOf = Boolean.valueOf(isEdited());
            boolean zIsCut = isCut();
            boolean z2 = this.isWallpaperChanged;
            String str = this.trackId;
            if (str == null) {
                str = "";
            }
            String str2 = str;
            String videoStatus = getVideoStatus();
            EditFragment editFragment = this.editFragment;
            int iFollowStatusIsChanged = (editFragment == null || (combineEditView = editFragment.getCombineEditView()) == null || (homeTemplateView = combineEditView.getHomeTemplateView()) == null) ? -1 : homeTemplateView.followStatusIsChanged();
            int blurStatus = getBlurStatus();
            BaseClockView baseClockView = (BaseClockView) getClockLayer();
            trackHelper.trackQuitEditMode(context, "应用", commonConfigOriginTempConfig, commonConfigTemplateConfig, boolValueOf, zIsCut, z2, str2, videoStatus, iFollowStatusIsChanged, blurStatus, baseClockView != null ? baseClockView.getAllInOneSetWay() : 0);
            edited = false;
            if (!this.isCallingFromKeyguard && (consumer = this.navigationCallback) != null) {
                consumer.accept(Boolean.TRUE);
            }
        }
        BaseClockView baseClockView2 = (BaseClockView) getClockLayer();
        if (baseClockView2 != null) {
            baseClockView2.onApplied(z);
        }
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    @CallSuper
    public final boolean onApply(@Nullable Consumer<Object> consumer, @Nullable LifecycleOwner lifecycleOwner) {
        this.navigationCallback = consumer;
        if (this.applying) {
            Log.i("Keyguard-Editor-BaseTemplateView", "applying config");
            return true;
        }
        tryApplyTemplate(consumer);
        return true;
    }

    public final boolean onApply(@Nullable Consumer<Object> consumer, @Nullable TemplateApplier.ApplyCallback applyCallback) {
        this.applyTransition = applyCallback;
        return EditorActivity.NavigationActionListener.DefaultImpls.onApply$default(this, consumer, null, 2, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
    public void onApplySuccessBeforeExitAnim() {
        TemplateApplier.ApplyCallback applyCallback = this.applyTransition;
        if (applyCallback != null) {
            applyCallback.onApplySuccessBeforeExitAnim();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.topButtonBar = (FrameLayout) getRootView().findViewById(R.id.kg_editor_button_bar);
        registerTimeUpdateBroadcast();
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public /* bridge */ /* synthetic */ void onBackgroundBlurEnableChange(boolean z) {
        super.onBackgroundBlurEnableChange(z);
    }

    public void onBackwardTransitionStart() {
        exitFullScreen(false);
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public boolean onCancel(@Nullable Consumer<Object> consumer) {
        EditFragment editFragment;
        Log.i("Keyguard-Editor-BaseTemplateView", "onCancel");
        if (this.applying) {
            Log.w("Keyguard-Editor-BaseTemplateView", "applying, can't cancel now");
            return true;
        }
        if (!isEdited() || ((editFragment = this.editFragment) != null && editFragment.getTemplateStatus() == 1)) {
            Log.i("Keyguard-Editor-BaseTemplateView", "onCancel should handle by fragment");
            return false;
        }
        showCancelDialog(consumer);
        return true;
    }

    public void onClickDepthButtonClick() throws IOException {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        HierarchyImageAvoidController avoidController;
        HierarchyImageAvoidController avoidController2;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || !wallpaperInfo.isDepthVideo()) {
            KgVisualCheckBox kgVisualCheckBox = this.hierarchyBtn;
            if (kgVisualCheckBox != null) {
                kgVisualCheckBox.setCheckedOnly(false);
            }
            produceDepthVideo();
        } else {
            MagicType magicType = MagicType.INSTANCE;
            CommonConfig commonConfig2 = this.templateConfig;
            if (magicType.isDepthVideo((commonConfig2 == null || (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo2.getMagicType()))) {
                HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
                if (hierarchyImageView != null && (avoidController2 = hierarchyImageView.getAvoidController()) != null) {
                    avoidController2.setUserOpenDepthVideo(false);
                }
                onEdited(35, null);
            } else {
                HierarchyImageView hierarchyImageView2 = (HierarchyImageView) getHierarchyLayer();
                if (hierarchyImageView2 != null && (avoidController = hierarchyImageView2.getAvoidController()) != null) {
                    avoidController.setUserOpenDepthVideo(true);
                }
                onEdited(36, null);
                startCheckDepthVideoAvoid();
            }
        }
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.resetStyleEditor();
        }
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onClockBeanChange(@Nullable ClockBean clockBean, boolean z) {
    }

    public void onClockInfoUpdate() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.onClockInfoUpdate();
        }
    }

    public void onColorModesChanged(@NotNull ColorModes colorModes) {
        BaseClockView baseClockView;
        Intrinsics.checkNotNullParameter(colorModes, "colorModes");
        if (!this.isDualClock && (baseClockView = this.miuiClockView) != null) {
            baseClockView.setColorMode(colorModes.getWallpaper());
        }
        View clockLayer = getClockLayer();
        AllInOneTemplateView allInOneTemplateView = clockLayer instanceof AllInOneTemplateView ? (AllInOneTemplateView) clockLayer : null;
        if (allInOneTemplateView != null) {
            allInOneTemplateView.updateHandleDrawablesForColorMode(colorModes.getWallpaper());
        }
        BaseClockView baseClockView2 = this.miuiClockView;
        if (baseClockView2 != null) {
            baseClockView2.setScaleTipsColorMode(colorModes.getScaleHint());
        }
    }

    @CallSuper
    public void onColorPickComplete(@NotNull ColorData colorData, boolean z) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        ColorPickHandler colorPickHandler = this.colorPickHandler;
        if (colorPickHandler != null) {
            colorPickHandler.onAutoPickColorComplete(colorData);
        }
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (clockInfo = lockscreenInfo.getClockInfo()) != null) {
            clockInfo.setDarkClockArea(colorData.getDarkClockArea());
        }
        BaseClockView baseClockView = this.miuiClockView;
        if (baseClockView != null) {
            baseClockView.updateClockColor(colorData);
        }
        if (this.shouldSetColorSelectionToAuto) {
            this.shouldSetColorSelectionToAuto = false;
            BaseClockView baseClockView2 = this.miuiClockView;
            if (baseClockView2 != null) {
                baseClockView2.resetColorSelectionToAuto();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x005a  */
    @Override // android.view.View
    public final void onConfigurationChanged(@NotNull Configuration newConfig) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        saveWallpaperPositionInfoBeforeConfigChange();
        super.onConfigurationChanged(newConfig);
        onScreenSizeChanged(newConfig);
        if (this.editable) {
            if (!DeviceUtil.INSTANCE.isPhone()) {
                ActivityUIAdapter.Companion.correctActivityResource(getContext());
            }
            updatePivot();
            if (!this.isDualClock) {
                onScreenSizeChangedWhenEditable(newConfig);
            }
            ColorPickHandler colorPickHandler = this.colorPickHandler;
            if (colorPickHandler != null) {
                colorPickHandler.onConfigurationChanged();
            }
            WallpaperInfo wallpaperInfo2 = getWallpaperInfo();
            if ((wallpaperInfo2 != null ? wallpaperInfo2.getInfoType() : null) != WallpaperInfoType.HOME_WALLPAPER || getHomeFollowWapper()) {
                CommonConfig commonConfig = this.templateConfig;
                if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) {
                    wallpaperInfo = null;
                } else {
                    wallpaperInfo = lockscreenInfo.getWallpaperInfo();
                }
            } else {
                CommonConfig commonConfig2 = this.templateConfig;
                if (commonConfig2 == null || (homeInfo = commonConfig2.getHomeInfo()) == null) {
                    wallpaperInfo = null;
                } else {
                    wallpaperInfo = homeInfo.getWallpaperInfo();
                }
            }
            if (wallpaperInfo != null) {
                if (Intrinsics.areEqual(wallpaperInfo.getResourceType(), "video")) {
                    releaseFastPlayer();
                    refreshWallpaperOnConfigChange(wallpaperInfo);
                    BaseClockView baseClockView = (BaseClockView) getClockLayer();
                    boolean zSupportHierarchy = baseClockView != null ? baseClockView.supportHierarchy() : false;
                    if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType())) && zSupportHierarchy) {
                        String depthVideo = wallpaperInfo.getDepthVideo();
                        boolean zIsSelect = isSelect();
                        WallpaperInfo wallpaperInfo3 = getWallpaperInfo();
                        startForcePlayerWithDepthVideo(depthVideo, true, zIsSelect, "config_changed_" + (wallpaperInfo3 != null ? wallpaperInfo3.getInfoType() : null) + "_show");
                    } else if (wallpaperInfo.isDepthVideo() && zSupportHierarchy && FileUtil.isSupportVideoDepth()) {
                        String originResourcePath = wallpaperInfo.getOriginResourcePath();
                        boolean zIsSelect2 = isSelect();
                        WallpaperInfo wallpaperInfo4 = getWallpaperInfo();
                        startForcePlayerWithDepthVideo(originResourcePath, false, zIsSelect2, "config_changed_" + (wallpaperInfo4 != null ? wallpaperInfo4.getInfoType() : null) + "_hide");
                    } else {
                        String originResourcePath2 = wallpaperInfo.getOriginResourcePath();
                        boolean zIsSelect3 = isSelect();
                        WallpaperInfo wallpaperInfo5 = getWallpaperInfo();
                        startForcePlayerWithNormalVideo(originResourcePath2, zIsSelect3, "config_changed_" + (wallpaperInfo5 != null ? wallpaperInfo5.getInfoType() : null) + " normal");
                    }
                } else if (Intrinsics.areEqual(wallpaperInfo.getResourceType(), "sensor") || WallpaperEditor.Companion.canScale(wallpaperInfo.getResourceType())) {
                    refreshWallpaperOnConfigChange(wallpaperInfo);
                }
            }
            post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.resetAllBottomSheet();
                }
            });
        }
        dismissCancelDialog();
    }

    public final void onDepthVideoProduced(String str) {
        HierarchyImageAvoidController avoidController;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig != null && (lockscreenInfo4 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) != null) {
            wallpaperInfo4.setDepthVideo(str);
        }
        CommonConfig commonConfig2 = this.templateConfig;
        if (commonConfig2 != null && (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null) {
            wallpaperInfo3.setOriginResourcePath(str);
        }
        CommonConfig commonConfig3 = this.templateConfig;
        if (commonConfig3 != null && (lockscreenInfo2 = commonConfig3.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
            wallpaperInfo2.setDepthVideo(true);
        }
        CommonConfig commonConfig4 = this.templateConfig;
        if (commonConfig4 != null && (lockscreenInfo = commonConfig4.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.updateMagicType(MagicType.TYPE_DEPTH_VIDEO);
        }
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        if (hierarchyImageView != null && (avoidController = hierarchyImageView.getAvoidController()) != null) {
            avoidController.setUserOpenDepthVideo(true);
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel != null) {
            EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 5, false, false, false, 14, null);
        }
        startForcePlayerWithDepthVideo("produced depth");
    }

    public void onDestroy() {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        CommonConfig commonConfig = this.templateConfig;
        Log.i("Keyguard-Editor-BaseTemplateView", "Template " + ((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId()) + " Destroyed");
        this.isTemplateSwitching = false;
        this.pendingDismissAfterSwitch = false;
        removeClockLoadListener();
        setEditModeChangedListener(null);
        this.applyAnimationPerformer = null;
        setWallpaperPickerLauncher(null);
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView != null) {
            combinedWallpaperView.releaseRender();
        }
        CombinedWallpaperView combinedWallpaperView2 = (CombinedWallpaperView) getHomeWallpaperLayer();
        if (combinedWallpaperView2 != null) {
            combinedWallpaperView2.releaseRender();
        }
        this.onCancelDialogNegativeClick = null;
        Folme.clean(this);
        releaseFastPlayer();
        FastPlayer fastPlayer = this.produceVideoFastPlayer;
        if (fastPlayer != null) {
            fastPlayer.release();
        }
        HandlerThread handlerThread = this.worker;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        dismissCancelDialog();
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.destroy();
            baseClockView.resetAllBottomSheet();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Job job = this.wallpaperDarkResultJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.wallpaperDarkResultJob = null;
        unRegisterTimeUpdateBroadcast();
        ImageSegmentHandler.INSTANCE.removeVideoSegmentServiceListener(this.depthVideoAiServiceListener);
        if (this.editable) {
            ColorPickerCache.INSTANCE.cacheAodColorData(null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheet() {
        FrameLayout root;
        if (!this.isExit && this.isDismissTemplateTitle) {
            postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    BaseTemplateView.onDismissBottomSheet$lambda$110(this.f$0);
                }
            }, 150L);
        }
        this.isDismissTemplateTitle = true;
        CombineEditListener combineEditListener = this.combineEditListener;
        if (combineEditListener != null) {
            combineEditListener.onDismissBottomSheet();
        }
        scaleTipsIsVisible(true);
        controlTopButtonState(true);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isPad()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!deviceUtil.isFoldLargeScreen(context)) {
                TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
                if (primaryButtonBar == null || (root = primaryButtonBar.getRoot()) == null) {
                    return;
                }
                root.setVisibility(0);
                return;
            }
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), Dispatchers.getMain(), null, new C01042(null), 2, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheetForScreenChange(@NotNull Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        CombineEditListener combineEditListener = this.combineEditListener;
        if (combineEditListener != null) {
            combineEditListener.onDismissBottomSheetForScreenChange(newConfig);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissKeyboard() {
        CombineEditListener.DefaultImpls.onDismissKeyboard(this);
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onDualClockChange(boolean z) {
    }

    @CallSuper
    public void onEditModeChanged(int i, int i2) {
    }

    @Override // com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, @Nullable Object obj) {
        EditFragmentViewModel viewModel;
        TemplateChangeRecorder templateChangeRecorder;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        TemplateConfig lockscreenInfo5;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo6;
        HierarchyImageAvoidController avoidController;
        TemplateConfig lockscreenInfo7;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo8;
        TemplateConfig lockscreenInfo9;
        TemplateConfig lockscreenInfo10;
        TemplateConfig lockscreenInfo11;
        TemplateConfig lockscreenInfo12;
        WallpaperInfo wallpaperInfoForVideo;
        TemplateConfig lockscreenInfo13;
        WallpaperInfo wallpaperInfo6;
        TemplateConfig lockscreenInfo14;
        TemplateConfig lockscreenInfo15;
        TemplateConfig lockscreenInfo16;
        WallpaperInfo wallpaperInfo7;
        TemplateConfig lockscreenInfo17;
        TemplateConfig lockscreenInfo18;
        WallpaperInfo wallpaperInfo8;
        Bitmap currentWallpaper;
        TemplateConfig lockscreenInfo19;
        WallpaperInfo wallpaperInfo9;
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo20;
        WallpaperInfo wallpaperInfo10;
        TemplateConfig lockscreenInfo21;
        WallpaperInfo wallpaperInfo11;
        TemplateConfig lockscreenInfo22;
        TemplateConfig lockscreenInfo23;
        TemplateConfig lockscreenInfo24;
        TemplateConfig lockscreenInfo25;
        TemplateConfig lockscreenInfo26;
        Log.i("Keyguard-Editor-BaseTemplateView", "onEdited: " + i + ", " + obj + " " + this.initFinish);
        markEdited();
        Object wallpaperInfo12 = null;
        if (i == 30) {
            WallpaperTypeInfo wallpaperTypeInfo = (WallpaperTypeInfo) obj;
            if (!Intrinsics.areEqual(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null, "image") && (viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment)) != null && (templateChangeRecorder = viewModel.getTemplateChangeRecorder()) != null) {
                templateChangeRecorder.clearAllRecord();
            }
            clearGalleryContent();
            this.isWallpaperChanged = true;
            onWallpaperSelected(wallpaperTypeInfo);
            clearSubjectWhenSuperWallpaper(wallpaperTypeInfo);
            this.shouldSetColorSelectionToAuto = true;
            BaseClockView baseClockView = (BaseClockView) getClockLayer();
            if (baseClockView != null) {
                baseClockView.resetStyleEditor();
            }
        } else if (i == 31) {
            this.isDepthProgressInterrupted = false;
            ImageSegmentHandler.INSTANCE.removeVideoSegmentServiceListener(this.depthVideoAiServiceListener);
            AlertDialog alertDialog = this.depthVideoProgressDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
            onDepthVideoProduced((String) obj);
            resetClockFontColor();
        } else if (i != 71) {
            switch (i) {
                case 35:
                    CommonConfig commonConfig2 = this.templateConfig;
                    if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                        wallpaperInfo.updateMagicType(0);
                    }
                    setForegroundVisibleWithDepthVideo();
                    resetClockFontColor();
                    break;
                case 36:
                    CommonConfig commonConfig3 = this.templateConfig;
                    if (commonConfig3 != null && (lockscreenInfo2 = commonConfig3.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                        wallpaperInfo2.updateMagicType(MagicType.TYPE_DEPTH_VIDEO);
                    }
                    setForegroundVisibleWithDepthVideo();
                    resetClockFontColor();
                    break;
                case 37:
                    CommonConfig commonConfig4 = this.templateConfig;
                    if (((commonConfig4 == null || (lockscreenInfo14 = commonConfig4.getLockscreenInfo()) == null) ? null : lockscreenInfo14.getWallpaperInfo()) != null && (getWallpaperLayer() instanceof CombinedWallpaperView)) {
                        CommonConfig commonConfig5 = this.templateConfig;
                        if (commonConfig5 != null && (lockscreenInfo12 = commonConfig5.getLockscreenInfo()) != null && (wallpaperInfoForVideo = lockscreenInfo12.getWallpaperInfoForVideo()) != null) {
                            CommonConfig commonConfig6 = this.templateConfig;
                            wallpaperInfoForVideo.setCurrentWallpaper((commonConfig6 == null || (lockscreenInfo13 = commonConfig6.getLockscreenInfo()) == null || (wallpaperInfo6 = lockscreenInfo13.getWallpaperInfo()) == null) ? null : wallpaperInfo6.getCurrentWallpaper());
                        }
                        CommonConfig commonConfig7 = this.templateConfig;
                        WallpaperInfo wallpaperInfo13 = (commonConfig7 == null || (lockscreenInfo11 = commonConfig7.getLockscreenInfo()) == null) ? null : lockscreenInfo11.getWallpaperInfo();
                        CommonConfig commonConfig8 = this.templateConfig;
                        if (commonConfig8 != null && (lockscreenInfo9 = commonConfig8.getLockscreenInfo()) != null) {
                            CommonConfig commonConfig9 = this.templateConfig;
                            lockscreenInfo9.setWallpaperInfo((commonConfig9 == null || (lockscreenInfo10 = commonConfig9.getLockscreenInfo()) == null) ? null : lockscreenInfo10.getWallpaperInfoForVideo());
                        }
                        CommonConfig commonConfig10 = this.templateConfig;
                        if (commonConfig10 != null && (lockscreenInfo8 = commonConfig10.getLockscreenInfo()) != null) {
                            lockscreenInfo8.setWallpaperInfoForVideo(wallpaperInfo13);
                        }
                        if (shouldOpenVideoDepth()) {
                            CommonConfig commonConfig11 = this.templateConfig;
                            if (commonConfig11 != null && (lockscreenInfo7 = commonConfig11.getLockscreenInfo()) != null && (wallpaperInfo5 = lockscreenInfo7.getWallpaperInfo()) != null) {
                                wallpaperInfo5.updateMagicType(MagicType.TYPE_DEPTH_VIDEO);
                            }
                            HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
                            if (hierarchyImageView != null && (avoidController = hierarchyImageView.getAvoidController()) != null) {
                                avoidController.setUserOpenDepthVideo(true);
                            }
                        } else {
                            CommonConfig commonConfig12 = this.templateConfig;
                            if (commonConfig12 != null && (lockscreenInfo3 = commonConfig12.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null) {
                                wallpaperInfo3.resetMagicType("image to video: not depth video");
                            }
                        }
                        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
                        Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
                        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
                        CommonConfig commonConfig13 = this.templateConfig;
                        WallpaperInfo wallpaperInfo14 = (commonConfig13 == null || (lockscreenInfo6 = commonConfig13.getLockscreenInfo()) == null) ? null : lockscreenInfo6.getWallpaperInfo();
                        Intrinsics.checkNotNull(wallpaperInfo14);
                        CommonConfig commonConfig14 = this.templateConfig;
                        CombinedWallpaperView.switchWallpaperView$default(combinedWallpaperView, wallpaperInfo14, (commonConfig14 == null || (lockscreenInfo5 = commonConfig14.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo5.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getCurrentWallpaper(), null, false, false, null, false, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, null);
                        if (this.transitionBackground == null) {
                            this.needResetBackground = true;
                        } else {
                            combinedWallpaperView.setBackgroundDrawable(new BitmapDrawable(getResources(), this.transitionBackground));
                            this.needRemoveBackground = true;
                        }
                        startForcePlayerWithDepthVideo("img to video");
                        BaseClockView baseClockView2 = (BaseClockView) getClockLayer();
                        if (baseClockView2 != null) {
                            baseClockView2.switchTips(false);
                        }
                        setLongClickListenerForLivePhoto();
                        resetClockFontColor();
                        CommonConfig commonConfig15 = this.templateConfig;
                        if (commonConfig15 != null && (lockscreenInfo4 = commonConfig15.getLockscreenInfo()) != null) {
                            wallpaperInfo12 = lockscreenInfo4.getWallpaperInfo();
                        }
                        Log.d("AIWallPaperManager", "video to image " + wallpaperInfo12);
                    }
                    BaseClockView baseClockView3 = (BaseClockView) getClockLayer();
                    if (baseClockView3 != null) {
                        baseClockView3.resetStyleEditor();
                    }
                    break;
                case 38:
                    CommonConfig commonConfig16 = this.templateConfig;
                    if (((commonConfig16 == null || (lockscreenInfo26 = commonConfig16.getLockscreenInfo()) == null) ? null : lockscreenInfo26.getWallpaperInfo()) != null && (getWallpaperLayer() instanceof CombinedWallpaperView)) {
                        CommonConfig commonConfig17 = this.templateConfig;
                        WallpaperInfo wallpaperInfoForVideo2 = (commonConfig17 == null || (lockscreenInfo25 = commonConfig17.getLockscreenInfo()) == null) ? null : lockscreenInfo25.getWallpaperInfoForVideo();
                        CommonConfig commonConfig18 = this.templateConfig;
                        if (commonConfig18 != null && (lockscreenInfo23 = commonConfig18.getLockscreenInfo()) != null) {
                            CommonConfig commonConfig19 = this.templateConfig;
                            lockscreenInfo23.setWallpaperInfoForVideo((commonConfig19 == null || (lockscreenInfo24 = commonConfig19.getLockscreenInfo()) == null) ? null : lockscreenInfo24.getWallpaperInfo());
                        }
                        CommonConfig commonConfig20 = this.templateConfig;
                        if (commonConfig20 != null && (lockscreenInfo22 = commonConfig20.getLockscreenInfo()) != null) {
                            lockscreenInfo22.setWallpaperInfo(wallpaperInfoForVideo2);
                        }
                        CommonConfig commonConfig21 = this.templateConfig;
                        if (commonConfig21 != null && (lockscreenInfo19 = commonConfig21.getLockscreenInfo()) != null && (wallpaperInfo9 = lockscreenInfo19.getWallpaperInfo()) != null && wallpaperInfo9.getSupportSubject()) {
                            CommonConfig commonConfig22 = this.templateConfig;
                            if (((commonConfig22 == null || (lockscreenInfo21 = commonConfig22.getLockscreenInfo()) == null || (wallpaperInfo11 = lockscreenInfo21.getWallpaperInfo()) == null) ? null : wallpaperInfo11.getSubjectBitmap()) != null && shouldEnableHierarchy() && (commonConfig = this.templateConfig) != null && (lockscreenInfo20 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo10 = lockscreenInfo20.getWallpaperInfo()) != null) {
                                wallpaperInfo10.updateMagicType(MagicType.TYPE_DEPTH);
                            }
                        }
                        setLongClickListenerForLivePhoto();
                        IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
                        Intrinsics.checkNotNull(wallpaperLayer2, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
                        CombinedWallpaperView combinedWallpaperView2 = (CombinedWallpaperView) wallpaperLayer2;
                        if (this.transitionBackground == null) {
                            CommonConfig commonConfig23 = this.templateConfig;
                            if (commonConfig23 != null && (lockscreenInfo18 = commonConfig23.getLockscreenInfo()) != null && (wallpaperInfo8 = lockscreenInfo18.getWallpaperInfo()) != null && (currentWallpaper = wallpaperInfo8.getCurrentWallpaper()) != null) {
                                combinedWallpaperView2.setBackgroundDrawable(new CenterCropDrawable(currentWallpaper));
                            }
                            this.needResetBackground = true;
                        } else {
                            combinedWallpaperView2.setBackgroundDrawable(new BitmapDrawable(getResources(), this.transitionBackground));
                            this.needRemoveBackground = true;
                        }
                        CommonConfig commonConfig24 = this.templateConfig;
                        WallpaperInfo wallpaperInfo15 = (commonConfig24 == null || (lockscreenInfo17 = commonConfig24.getLockscreenInfo()) == null) ? null : lockscreenInfo17.getWallpaperInfo();
                        Intrinsics.checkNotNull(wallpaperInfo15);
                        CommonConfig commonConfig25 = this.templateConfig;
                        CombinedWallpaperView.switchWallpaperView$default(combinedWallpaperView2, wallpaperInfo15, (commonConfig25 == null || (lockscreenInfo16 = commonConfig25.getLockscreenInfo()) == null || (wallpaperInfo7 = lockscreenInfo16.getWallpaperInfo()) == null) ? null : wallpaperInfo7.getCurrentWallpaper(), null, false, false, null, false, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, null);
                        View view = this.depthVideoForegroundLayer;
                        if (view != null) {
                            view.setVisibility(8);
                        }
                        stopForcePlayerWithDepthVideo();
                        BaseClockView baseClockView4 = (BaseClockView) getClockLayer();
                        if (baseClockView4 != null) {
                            baseClockView4.switchTips(true);
                        }
                        resetClockFontColor();
                        CommonConfig commonConfig26 = this.templateConfig;
                        if (commonConfig26 != null && (lockscreenInfo15 = commonConfig26.getLockscreenInfo()) != null) {
                            wallpaperInfo12 = lockscreenInfo15.getWallpaperInfo();
                        }
                        Log.d("AIWallPaperManager", " image to video " + wallpaperInfo12);
                    }
                    BaseClockView baseClockView5 = (BaseClockView) getClockLayer();
                    if (baseClockView5 != null) {
                        baseClockView5.resetStyleEditor();
                    }
                    break;
            }
        } else if (!(obj instanceof ColorSelectBean)) {
            return;
        }
        FollowSystemListener followSystemListener = this.followSystemListener;
        if (followSystemListener != null) {
            followSystemListener.trigger(2, new EditData(i, obj));
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback
    public void onFirstBindWallpaper(@Nullable final Bitmap bitmap) {
        final Bitmap bitmap2 = BitmapTempStore.Companion.getInstance().getBitmap("wallpaper_for_pick_color");
        Log.i("Keyguard-Editor-BaseTemplateView", getFromWhereTag() + ": onFirstBindWallpaper: editable=" + this.editable + ", bitmap=" + (bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null) + " * " + (bitmap != null ? Integer.valueOf(bitmap.getHeight()) : null) + ", wallpaperForPickColor=" + (bitmap2 != null ? Integer.valueOf(bitmap2.getWidth()) : null) + " * " + (bitmap2 != null ? Integer.valueOf(bitmap2.getHeight()) : null));
        if (this.editable || this.isAodDialogTemplate || bitmap2 == null) {
            bitmap2 = bitmap;
        }
        refreshTransitionBackground(bitmap2);
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
        if (this.needResetBackground) {
            if (bitmap2 != null) {
                combinedWallpaperView.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap2));
            }
            this.needRemoveBackground = true;
            this.needResetBackground = false;
        }
        combinedWallpaperView.onFirstBindWallpaper();
        this.pickColorFirst.set(true);
        if (bitmap == null) {
            bitmap = bitmap2;
        }
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return BaseTemplateView.onFirstBindWallpaper$lambda$15(this.f$0, bitmap2);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BaseTemplateView.onFirstBindWallpaper$lambda$16(this.f$0, bitmap, (Bitmap) obj);
            }
        });
    }

    @CallSuper
    public void onFirstColorPickComplete() {
        if (this.pickColorFirst.get()) {
            onTemplateReady(4);
            this.pickColorFirst.set(false);
        }
    }

    public void onForwardTransitionComplete() {
        refreshBar();
        enterFullScreen();
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback
    public void onGestureWallpaperEnd(@Nullable Bitmap bitmap) {
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        CombinedWallpaperView combinedWallpaperView = wallpaperLayer instanceof CombinedWallpaperView ? (CombinedWallpaperView) wallpaperLayer : null;
        GestureManager gestureManager = combinedWallpaperView != null ? combinedWallpaperView.getGestureManager() : null;
        boolean zPositionInfoHasChanged = gestureManager != null ? gestureManager.positionInfoHasChanged() : false;
        Log.i("Keyguard-Editor-BaseTemplateView", "onGestureWallpaperEnd: usePositionInfoChanged=" + zPositionInfoHasChanged);
        this.isPositionInfoChanged = true;
        if (zPositionInfoHasChanged) {
            if (gestureManager != null) {
                gestureManager.resetPositionInfoChanged();
            }
            markEdited();
            onPositionInfoChanged();
            refreshTransitionBackground(bitmap);
        }
        onWallpaperReady$default(this, bitmap, true, null, 4, null);
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onMiuiClockInfoChange(@Nullable ClockBean clockBean, boolean z) {
    }

    public void onPositionInfoChanged() {
    }

    @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
    public void onPrepareApply() {
        this.applying = true;
        TemplateApplier.ApplyCallback applyCallback = this.applyTransition;
        if (applyCallback != null) {
            applyCallback.onPrepareApply();
        }
        applyTemplateInfo();
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onPreviewChanged(boolean z, boolean z2) {
        BaseClockView baseClockView = this.miuiClockView;
        if (baseClockView != null) {
            CombineEditListener.DefaultImpls.onPreviewChanged$default(baseClockView, z, false, 2, null);
        }
        if (z) {
            TemplateButtonBar templateButtonBar = this.primaryButtonBar;
            if (templateButtonBar != null) {
                templateButtonBar.changeAlpha(0.0f);
                return;
            }
            return;
        }
        TemplateButtonBar templateButtonBar2 = this.primaryButtonBar;
        if (templateButtonBar2 != null) {
            templateButtonBar2.changeAlpha(1.0f);
        }
    }

    public final void onReceiveActivityResult(int i, int i2, @Nullable Intent intent) {
        WallpaperEditor wallpaperEditor = this.wallpaperEditor;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        wallpaperEditor.handleThemePickerResult(i, i2, intent, context);
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.onEditorSubUIDismissed();
        }
    }

    public void onScreenSizeChanged(@NotNull Configuration newConfig) {
        CombineEditView combineEditView;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.onScreenSizeChanged(newConfig);
        }
        EditFragment editFragment = this.editFragment;
        if (editFragment == null || (combineEditView = editFragment.getCombineEditView()) == null) {
            return;
        }
        float combineEditorViewY = combineEditView.getCombineEditorViewY();
        TemplateButtonBar templateButtonBar = this.primaryButtonBar;
        if (templateButtonBar != null) {
            templateButtonBar.updateTemplateTitle(combineEditorViewY);
        }
    }

    public void onScreenSizeChangedWhenEditable(@NotNull Configuration newConfig) {
        CombineEditView combineEditView;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.onScreenSizeChangedWhenEditable(newConfig);
        }
        EditFragment editFragment = this.editFragment;
        if (editFragment == null || (combineEditView = editFragment.getCombineEditView()) == null) {
            return;
        }
        float combineEditorViewY = combineEditView.getCombineEditorViewY();
        TemplateButtonBar templateButtonBar = this.primaryButtonBar;
        if (templateButtonBar != null) {
            templateButtonBar.resetMarginTop(combineEditorViewY);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onScrolledChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onScrolledChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onShowBottomSheet() {
        FrameLayout root;
        Log.d("Keyguard-Editor-BaseTemplateView", "onShowBottomSheet: combineEditListener=" + (this.combineEditListener != null));
        TextView textView = this.templateTitle;
        if (textView != null) {
            AnimatorKt.alphaTo$default(textView, 0.0f, 1.0f, 0.15f, null, 8, null);
        }
        scaleTipsIsVisible(false);
        CombineEditListener combineEditListener = this.combineEditListener;
        if (combineEditListener != null) {
            combineEditListener.onShowBottomSheet();
        }
        TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
        if (primaryButtonBar == null || (root = primaryButtonBar.getRoot()) == null) {
            return;
        }
        root.setVisibility(8);
    }

    public void onShowButtonBar(float f) {
        TemplateButtonBar templateButtonBar = this.primaryButtonBar;
        if (templateButtonBar != null) {
            templateButtonBar.changeAlpha(RangesKt.coerceAtLeast(RangesKt.coerceAtMost(f, 1.0f), 0.0f));
        }
    }

    public final void onShowTitleText() {
        TextView textView = this.templateTitle;
        if (textView == null || textView.getVisibility() == 0) {
            return;
        }
        textView.setVisibility(0);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.editable) {
            setPivotX((i * 1.0f) / 2.0f);
            setPivotY((i2 * 1.0f) / 2.0f);
        }
    }

    public void onStart() {
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView != null) {
            combinedWallpaperView.requestRender();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
    public void onStartApply(boolean z) {
        TemplateApplier.ApplyCallback applyCallback = this.applyTransition;
        if (applyCallback != null) {
            applyCallback.onStartApply(z);
        }
    }

    public void onStop() {
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public /* bridge */ /* synthetic */ void onSuperSaveChange(boolean z) {
        super.onSuperSaveChange(z);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onTemplateReady(int i) {
        this.templateReadyIds.add(Integer.valueOf(i));
        CombineEditListener combineEditListener = this.combineEditListener;
        if (combineEditListener != null) {
            combineEditListener.onTemplateReady(i);
        }
        if (i == 1 || i == 2) {
            Function0 function0 = this.onceWallpaperReadyListener;
            if (function0 != null) {
                function0.invoke();
            }
            this.onceWallpaperReadyListener = null;
        }
    }

    @Override // com.miui.keyguard.editor.edit.aiwapper.IAIWallPaperChangeListener
    public void onWallPaperChange(@Nullable Bundle bundle) {
        IAIWallPaperChangeListener.DefaultImpls.onWallPaperChange(this, bundle);
    }

    @Override // com.miui.keyguard.editor.edit.aiwapper.IAIWallPaperChangeListener
    public void onWallPaperReset(@Nullable WallpaperTypeInfo wallpaperTypeInfo) {
        IAIWallPaperChangeListener.DefaultImpls.onWallPaperReset(this, wallpaperTypeInfo);
    }

    public void onWallpaperDarkResultReady() {
    }

    public final void onWallpaperReady(Bitmap bitmap, boolean z, Bitmap bitmap2) {
        startPickColorFromWallpaper(bitmap, z, false, currentColorPickupRect());
        Job job = this.wallpaperDarkResultJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.wallpaperDarkResultJob = BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new C01051(bitmap2, null), 3, null);
        if (this.editable) {
            checkColorModesChanged(bitmap, true);
        }
    }

    public final void onWallpaperScaleChanged(boolean z) {
        if (DeviceUtil.INSTANCE.isLiteDevice()) {
            if (this instanceof HomeTemplateView) {
                ((HomeTemplateView) this).setBlurBtnOnScanle(z);
                return;
            }
            KgVisualCheckBox kgVisualCheckBox = this.filterBtn;
            if (kgVisualCheckBox != null) {
                kgVisualCheckBox.setScale(z);
            }
        }
    }

    public void onWallpaperSelected(@Nullable WallpaperTypeInfo wallpaperTypeInfo) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        TemplateConfig lockscreenInfo5;
        TemplateConfig lockscreenInfo6;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo7;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo8;
        WallpaperInfo wallpaperInfo6;
        Integer videoType;
        TemplateConfig lockscreenInfo9;
        WallpaperInfo wallpaperInfo7;
        Boolean boolIsSupportLoop;
        TemplateConfig lockscreenInfo10;
        WallpaperInfo wallpaperInfo8;
        TemplateConfig lockscreenInfo11;
        WallpaperInfo wallpaperInfo9;
        TemplateConfig lockscreenInfo12;
        WallpaperInfo wallpaperInfo10;
        TemplateConfig lockscreenInfo13;
        WallpaperInfo wallpaperInfo11;
        TemplateConfig lockscreenInfo14;
        WallpaperInfo wallpaperInfo12;
        TemplateConfig lockscreenInfo15;
        WallpaperInfo wallpaperInfo13;
        TemplateConfig lockscreenInfo16;
        WallpaperInfo wallpaperInfo14;
        TemplateConfig lockscreenInfo17;
        WallpaperInfo wallpaperInfo15;
        String resourcePath;
        TemplateConfig lockscreenInfo18;
        WallpaperInfo wallpaperInfo16;
        TemplateConfig lockscreenInfo19;
        WallpaperInfo wallpaperInfo17;
        TemplateConfig lockscreenInfo20;
        WallpaperInfo wallpaperInfo18;
        SensorWallpaperPrams sensorWallpaperPrams;
        TemplateConfig lockscreenInfo21;
        WallpaperInfo wallpaperInfo19;
        ensureQ18SensorOuterVideo(wallpaperTypeInfo);
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig != null && (lockscreenInfo21 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo19 = lockscreenInfo21.getWallpaperInfo()) != null) {
            wallpaperInfo19.setCurrentWallpaper(wallpaperTypeInfo != null ? wallpaperTypeInfo.getBitmap() : null);
        }
        CommonConfig commonConfig2 = this.templateConfig;
        if (commonConfig2 != null && (lockscreenInfo20 = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo18 = lockscreenInfo20.getWallpaperInfo()) != null) {
            wallpaperInfo18.setCurrentSmallSensorWallpaper((wallpaperTypeInfo == null || (sensorWallpaperPrams = wallpaperTypeInfo.getSensorWallpaperPrams()) == null) ? null : sensorWallpaperPrams.getCurrentSmallSensorWallpaper());
        }
        CommonConfig commonConfig3 = this.templateConfig;
        if (commonConfig3 != null && (lockscreenInfo19 = commonConfig3.getLockscreenInfo()) != null && (wallpaperInfo17 = lockscreenInfo19.getWallpaperInfo()) != null) {
            wallpaperInfo17.setResourceType(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null);
        }
        CommonConfig commonConfig4 = this.templateConfig;
        if (commonConfig4 != null && (lockscreenInfo18 = commonConfig4.getLockscreenInfo()) != null && (wallpaperInfo16 = lockscreenInfo18.getWallpaperInfo()) != null) {
            wallpaperInfo16.setOriginResourcePath(wallpaperTypeInfo != null ? wallpaperTypeInfo.getResourcePath() : null);
        }
        CommonConfig commonConfig5 = this.templateConfig;
        if (commonConfig5 != null && (lockscreenInfo17 = commonConfig5.getLockscreenInfo()) != null && (wallpaperInfo15 = lockscreenInfo17.getWallpaperInfo()) != null) {
            if (wallpaperTypeInfo == null || (resourcePath = wallpaperTypeInfo.getResourcePath()) == null) {
                resourcePath = "";
            }
            wallpaperInfo15.setSource(resourcePath);
        }
        CommonConfig commonConfig6 = this.templateConfig;
        if (commonConfig6 != null && (lockscreenInfo16 = commonConfig6.getLockscreenInfo()) != null && (wallpaperInfo14 = lockscreenInfo16.getWallpaperInfo()) != null) {
            wallpaperInfo14.setMask(wallpaperTypeInfo != null ? wallpaperTypeInfo.getMaskPath() : null);
        }
        CommonConfig commonConfig7 = this.templateConfig;
        if (commonConfig7 != null && (lockscreenInfo15 = commonConfig7.getLockscreenInfo()) != null && (wallpaperInfo13 = lockscreenInfo15.getWallpaperInfo()) != null) {
            wallpaperInfo13.setSensorWallpaperPrams(wallpaperTypeInfo != null ? wallpaperTypeInfo.getSensorWallpaperPrams() : null);
        }
        CommonConfig commonConfig8 = this.templateConfig;
        if (commonConfig8 != null && (lockscreenInfo14 = commonConfig8.getLockscreenInfo()) != null && (wallpaperInfo12 = lockscreenInfo14.getWallpaperInfo()) != null) {
            wallpaperInfo12.setPositionInfo(null);
        }
        CommonConfig commonConfig9 = this.templateConfig;
        if (commonConfig9 != null && (lockscreenInfo13 = commonConfig9.getLockscreenInfo()) != null && (wallpaperInfo11 = lockscreenInfo13.getWallpaperInfo()) != null) {
            wallpaperInfo11.setNeedDark(wallpaperTypeInfo != null ? wallpaperTypeInfo.isNeedDark() : false);
        }
        CommonConfig commonConfig10 = this.templateConfig;
        boolean enableBlur = (commonConfig10 == null || (lockscreenInfo12 = commonConfig10.getLockscreenInfo()) == null || (wallpaperInfo10 = lockscreenInfo12.getWallpaperInfo()) == null) ? false : wallpaperInfo10.getEnableBlur();
        CommonConfig commonConfig11 = this.templateConfig;
        boolean zBooleanValue = true;
        if (commonConfig11 != null && (lockscreenInfo11 = commonConfig11.getLockscreenInfo()) != null && (wallpaperInfo9 = lockscreenInfo11.getWallpaperInfo()) != null) {
            wallpaperInfo9.setEnableBlur((wallpaperTypeInfo != null ? wallpaperTypeInfo.getEnableBlur() : false) && enableBlur);
        }
        CommonConfig commonConfig12 = this.templateConfig;
        if (commonConfig12 != null && (lockscreenInfo10 = commonConfig12.getLockscreenInfo()) != null && (wallpaperInfo8 = lockscreenInfo10.getWallpaperInfo()) != null) {
            boolean enableBlur2 = wallpaperInfo8.getEnableBlur();
            if (wallpaperTypeInfo != null) {
                wallpaperTypeInfo.setEnableBlur(enableBlur2);
            }
        }
        CommonConfig commonConfig13 = this.templateConfig;
        if (commonConfig13 != null && (lockscreenInfo9 = commonConfig13.getLockscreenInfo()) != null && (wallpaperInfo7 = lockscreenInfo9.getWallpaperInfo()) != null) {
            if (wallpaperTypeInfo != null && (boolIsSupportLoop = wallpaperTypeInfo.isSupportLoop()) != null) {
                zBooleanValue = boolIsSupportLoop.booleanValue();
            }
            wallpaperInfo7.setSupportLoop(zBooleanValue);
        }
        CommonConfig commonConfig14 = this.templateConfig;
        if (commonConfig14 != null && (lockscreenInfo8 = commonConfig14.getLockscreenInfo()) != null && (wallpaperInfo6 = lockscreenInfo8.getWallpaperInfo()) != null) {
            wallpaperInfo6.setVideoType((wallpaperTypeInfo == null || (videoType = wallpaperTypeInfo.getVideoType()) == null) ? 0 : videoType.intValue());
        }
        CommonConfig commonConfig15 = this.templateConfig;
        if (commonConfig15 != null && (lockscreenInfo7 = commonConfig15.getLockscreenInfo()) != null && (wallpaperInfo5 = lockscreenInfo7.getWallpaperInfo()) != null) {
            wallpaperInfo5.setImagePath(wallpaperTypeInfo != null ? wallpaperTypeInfo.getImagePath() : null);
        }
        CommonConfig commonConfig16 = this.templateConfig;
        if (commonConfig16 != null && (lockscreenInfo6 = commonConfig16.getLockscreenInfo()) != null && (wallpaperInfo4 = lockscreenInfo6.getWallpaperInfo()) != null) {
            wallpaperInfo4.setAiGenerated(wallpaperTypeInfo != null ? wallpaperTypeInfo.isAiGenerated() : false);
        }
        CommonConfig commonConfig17 = this.templateConfig;
        if (commonConfig17 != null && (lockscreenInfo5 = commonConfig17.getLockscreenInfo()) != null) {
            lockscreenInfo5.setDarkenWallpaper(null);
        }
        CommonConfig commonConfig18 = this.templateConfig;
        if (commonConfig18 != null && (lockscreenInfo4 = commonConfig18.getLockscreenInfo()) != null) {
            lockscreenInfo4.setWallpaperInfoForVideo(null);
        }
        CommonConfig commonConfig19 = this.templateConfig;
        if (commonConfig19 != null && (lockscreenInfo3 = commonConfig19.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null) {
            wallpaperInfo3.setDirectGenerate(wallpaperTypeInfo != null ? wallpaperTypeInfo.isDirectGenerate() : false);
        }
        CommonConfig commonConfig20 = this.templateConfig;
        if (commonConfig20 != null && (lockscreenInfo2 = commonConfig20.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
            wallpaperInfo2.setMattingMode(wallpaperTypeInfo != null ? wallpaperTypeInfo.getMattingMode() : 0);
        }
        CommonConfig commonConfig21 = this.templateConfig;
        if (commonConfig21 != null && (lockscreenInfo = commonConfig21.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.setSupportFullScreenAod(wallpaperTypeInfo != null ? wallpaperTypeInfo.isSupportFullScreenAod() : null);
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            wallpaperLayer.onWallpaperSelected(wallpaperTypeInfo, getFromWhereTag());
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        BaseClockView baseClockView;
        KgVisualCheckBox wallpaperPickerButton;
        KgVisualCheckBox wallpaperPickerButton2;
        super.onWindowFocusChanged(z);
        if (z) {
            TemplateButtonBar templateButtonBar = this.primaryButtonBar;
            if (templateButtonBar != null && (wallpaperPickerButton2 = templateButtonBar.getWallpaperPickerButton()) != null) {
                wallpaperPickerButton2.setClickable(true);
            }
            if (this.isDepthProgressInterrupted) {
                dismissDepthVideoProgressDialogOnInterrupt();
            }
        }
        if (reloadTemplate(z) || !z) {
            return;
        }
        TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
        if (primaryButtonBar != null && (wallpaperPickerButton = primaryButtonBar.getWallpaperPickerButton()) != null && wallpaperPickerButton.isChecked()) {
            wallpaperPickerButton.setChecked(false);
        }
        AutoBottomSheet autoBottomSheet = this.wallpaperFilterAutoBottomSheet;
        if (autoBottomSheet == null || !autoBottomSheet.isShowing()) {
            BaseClockView baseClockView2 = (BaseClockView) getClockLayer();
            if ((baseClockView2 != null && baseClockView2.showBottomSheetModal()) || this.isExit || this.applying || (baseClockView = (BaseClockView) getClockLayer()) == null) {
                return;
            }
            baseClockView.onEditorSubUIDismissed();
        }
    }

    public final CommonConfig originTempConfig() {
        return this.originConfig;
    }

    public final void pauseFastPlayer() {
        this.fastPlayerManager.pauseFastPlayer();
    }

    @NotNull
    public Pair<Bitmap, Boolean> preProcessBitmapForPickColor(@NotNull Bitmap bitmap, boolean z) {
        Pair<Bitmap, Boolean> pairPreProcessBitmapForPickColor;
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return (baseClockView == null || (pairPreProcessBitmapForPickColor = baseClockView.preProcessBitmapForPickColor(bitmap, z)) == null) ? new Pair<>(null, Boolean.FALSE) : pairPreProcessBitmapForPickColor;
    }

    public final void produceDepthVideo() throws IOException {
        String originResourcePath;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        CommonConfig commonConfig = this.templateConfig;
        String originResourcePath2 = null;
        if (TextUtils.isEmpty((commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getOriginResourcePath())) {
            Log.d("Keyguard-Editor-BaseTemplateView", "produceDepthVideo: source path is null");
            return;
        }
        CommonConfig commonConfig2 = this.templateConfig;
        if (commonConfig2 != null && (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
            originResourcePath2 = wallpaperInfo2.getOriginResourcePath();
        }
        Intrinsics.checkNotNull(originResourcePath2);
        File file = new File(originResourcePath2);
        if (!file.exists()) {
            Log.d("Keyguard-Editor-BaseTemplateView", "produceDepthVideo: source file not exist");
            return;
        }
        this.produceDepthVideoTime = System.currentTimeMillis();
        this.isDepthProgressInterrupted = false;
        ImageSegmentHandler imageSegmentHandler = ImageSegmentHandler.INSTANCE;
        imageSegmentHandler.removeVideoSegmentServiceListener(this.depthVideoAiServiceListener);
        imageSegmentHandler.addVideoSegmentServiceListener(this.depthVideoAiServiceListener);
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        File file2 = new File(new TemplateFilePathGenerator(context).generateDepthVideoProduceFolder(), file.getName() + "_out.mp4");
        cleanupTempDepthSource();
        if (Intrinsics.areEqual(file.getParent(), file2.getParent())) {
            Log.d("Keyguard-Editor-BaseTemplateView", "produceDepthVideo: source file is inside output dir, copy to temp first. source=" + file.getAbsolutePath() + ", outDir=" + file2.getParent());
            File file3 = new File(getContext().getCacheDir(), "depth_source_temp_" + file.getName());
            try {
                FilesKt.copyTo$default(file, file3, true, 0, 4, null);
                originResourcePath = file3.getAbsolutePath();
            } catch (Exception e) {
                Log.e("Keyguard-Editor-BaseTemplateView", "produceDepthVideo: failed to copy source to temp", e);
                return;
            }
        } else {
            CommonConfig commonConfig3 = this.templateConfig;
            if (commonConfig3 == null || (lockscreenInfo = commonConfig3.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || (originResourcePath = wallpaperInfo.getOriginResourcePath()) == null) {
                originResourcePath = "";
            }
        }
        FileUtil.deleteFolder(file2.getParent());
        if (!TextUtils.isEmpty(file2.getParent())) {
            String parent = file2.getParent();
            Intrinsics.checkNotNull(parent);
            File file4 = new File(parent);
            if (!file4.exists()) {
                file4.mkdirs();
            }
        }
        final String path = file2.getPath();
        if (this.videoSegmentAbility == null) {
            Context applicationContext = getContext().getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            this.videoSegmentAbility = new VideoSegmentAbility(applicationContext);
        }
        VideoSegmentAbility videoSegmentAbility = this.videoSegmentAbility;
        if (videoSegmentAbility != null && videoSegmentAbility.isSupport()) {
            Log.d("Keyguard-Editor-BaseTemplateView", "produceDepthVideo: videoSegmentAbility support");
            try {
                final ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file2, 939524096);
                try {
                    final ParcelFileDescriptor parcelFileDescriptorOpen2 = ParcelFileDescriptor.open(new File(originResourcePath), 268435456);
                    final FastPlayerListener fastPlayerListener = new FastPlayerListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$produceDepthVideo$fastPlayerListener$1
                        @Override // com.xiaomi.taiyi.api.videosegment.FastPlayerListener
                        public int playerModeChange(int i, int i2, long j) {
                            BaseTemplateView baseTemplateView = this.this$0;
                            String str = path;
                            Intrinsics.checkNotNull(str);
                            return baseTemplateView.handleProduceVideoCallback(str, i, i2, j);
                        }
                    };
                    Task.submit(new Callable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda16
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            return BaseTemplateView.produceDepthVideo$lambda$94(this.f$0, parcelFileDescriptorOpen2, parcelFileDescriptorOpen, fastPlayerListener);
                        }
                    });
                    return;
                } catch (Exception e2) {
                    parcelFileDescriptorOpen.close();
                    throw e2;
                }
            } catch (Exception e3) {
                Log.e("Keyguard-Editor-BaseTemplateView", "produceDepthVideo: failed to open file descriptor", e3);
                return;
            }
        }
        Log.d("Keyguard-Editor-BaseTemplateView", "produceDepthVideo: videoSegmentAbility not support");
        FastPlayer fastPlayer = this.produceVideoFastPlayer;
        if (fastPlayer != null) {
            fastPlayer.release();
        }
        FastPlayer fastPlayer2 = new FastPlayer(getContext());
        this.produceVideoFastPlayer = fastPlayer2;
        fastPlayer2.initializeModel("XXX");
        FastPlayer fastPlayer3 = this.produceVideoFastPlayer;
        if (fastPlayer3 != null) {
            fastPlayer3.addDataSource(getContext(), Uri.parse(originResourcePath), 0);
        }
        FastPlayer fastPlayer4 = this.produceVideoFastPlayer;
        if (fastPlayer4 != null) {
            fastPlayer4.set_callback(new FastPlayer.FastPlayerListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda17
                @Override // com.miui.fastplayer.FastPlayer.FastPlayerListener
                public final int playerModeChange(int i, int i2, long j) {
                    return BaseTemplateView.produceDepthVideo$lambda$95(this.f$0, path, i, i2, j);
                }
            });
        }
        FastPlayer fastPlayer5 = this.produceVideoFastPlayer;
        if (fastPlayer5 != null) {
            fastPlayer5.produceAlphaVideo(path);
        }
    }

    public void refreshBar() {
        TemplateConfig lockscreenInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo;
        ExtraInfo extraInfo;
        Integer loopType;
        CombineEditView combineEditView;
        boolean z = false;
        edited = false;
        final TemplateButtonBar templateButtonBar = this.primaryButtonBar;
        if (templateButtonBar != null) {
            EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
            EditFragment editFragment = this.editFragment;
            if (editFragment != null && (combineEditView = editFragment.getCombineEditView()) != null) {
                this.templateTitle = templateButtonBar.addTemplateTitle(R.string.kg_template_title_lock_screen, combineEditView.getCombineEditorViewY());
            }
            CommonConfig commonConfig = this.templateConfig;
            int iIntValue = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo2.getWallpaperInfo()) == null || (extraInfo = wallpaperInfo.getExtraInfo()) == null || (loopType = extraInfo.getLoopType()) == null) ? -1 : loopType.intValue();
            Log.d("Keyguard-Editor-BaseTemplateView", "loopType = " + iIntValue + "， templateSource = " + getTemplateSource());
            boolean zIsLoopTemplate = FashionGalleryHelper.INSTANCE.isLoopTemplate(Integer.valueOf(iIntValue));
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (!deviceUtil.isInternationalBuild()) {
                zIsLoopTemplate = this.isGalleryOpened;
            }
            WallpaperInfo wallpaperInfo2 = null;
            if (zIsLoopTemplate) {
                templateButtonBar.addBottomTip(R.string.kg_editing_not_available_when_using_loop);
            } else {
                KgVisualCheckBox kgVisualCheckBoxAddWallpaperPickerButton$default = TemplateButtonBar.addWallpaperPickerButton$default(templateButtonBar, 0, 0, 3, null);
                kgVisualCheckBoxAddWallpaperPickerButton$default.setContentDescription(getResources().getString(R.string.kg_talkback_pick_wallpaper));
                kgVisualCheckBoxAddWallpaperPickerButton$default.setOnCheckListener(new KgVisualCheckBox.OnCheckListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda21
                    @Override // com.miui.keyguard.editor.view.KgVisualCheckBox.OnCheckListener
                    public final void onChecked(KgVisualCheckBox kgVisualCheckBox) {
                        BaseTemplateView.refreshBar$lambda$83$lambda$69$lambda$68(this.f$0, kgVisualCheckBox);
                    }
                });
                boolean z2 = !deviceUtil.isInternationalBuild() && iIntValue == 1;
                if (isInternationalGalleryOpen()) {
                    addAIGenerateButton();
                } else {
                    FunctionButtonBar.addDivideLine$default(templateButtonBar, 0, 0, 3, null);
                    if (!z2) {
                        addAIGenerateButton();
                    }
                    initHierarchyButton(templateButtonBar);
                    KgVisualCheckBox kgVisualCheckBoxAddFilterButton$default = TemplateButtonBar.addFilterButton$default(templateButtonBar, 0, 1, null);
                    IWallpaperLayer wallpaperLayer = getWallpaperLayer();
                    if (wallpaperLayer != null ? Intrinsics.areEqual(wallpaperLayer.getScaleable(), Boolean.FALSE) : false) {
                        kgVisualCheckBoxAddFilterButton$default.disable();
                    }
                    if (this.isGalleryOpened) {
                        Log.d("Keyguard-Editor-BaseTemplateView", "disable wallpaper filter for gallery");
                        kgVisualCheckBoxAddFilterButton$default.disable();
                    }
                    if (isGlassClockEffect()) {
                        Log.d("Keyguard-Editor-BaseTemplateView", "disable wallpaper filter for glass effect");
                        kgVisualCheckBoxAddFilterButton$default.disable();
                    }
                    kgVisualCheckBoxAddFilterButton$default.setOnCheckListener(new KgVisualCheckBox.OnCheckListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda22
                        @Override // com.miui.keyguard.editor.view.KgVisualCheckBox.OnCheckListener
                        public final void onChecked(KgVisualCheckBox kgVisualCheckBox) {
                            BaseTemplateView.refreshBar$lambda$83$lambda$78$lambda$75(this.f$0, templateButtonBar, kgVisualCheckBox);
                        }
                    });
                    kgVisualCheckBoxAddFilterButton$default.setOnUncheckListener(new KgVisualCheckBox.OnUncheckListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda23
                        @Override // com.miui.keyguard.editor.view.KgVisualCheckBox.OnUncheckListener
                        public final void onUncheck(KgVisualCheckBox kgVisualCheckBox) {
                            BaseTemplateView.refreshBar$lambda$83$lambda$78$lambda$76(this.f$0, kgVisualCheckBox);
                        }
                    });
                    kgVisualCheckBoxAddFilterButton$default.setDisableClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda24
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            BaseTemplateView.refreshBar$lambda$83$lambda$78$lambda$77(this.f$0, view);
                        }
                    });
                    this.filterBtn = kgVisualCheckBoxAddFilterButton$default;
                    updateFilterButtonVisible();
                }
            }
            CommonConfig commonConfig2 = this.templateConfig;
            if (commonConfig2 != null) {
                if (canDownloadButton(commonConfig2.getLockscreenInfo().getWallpaperInfo())) {
                    DepthPresetDownloadButton depthPresetDownloadButtonAddDepthPresetButton = templateButtonBar.addDepthPresetButton(commonConfig2);
                    depthPresetDownloadButtonAddDepthPresetButton.setClickCallback(new DownloadButtonClickCallBack(new WeakReference(this)));
                    this.depthPresetDownloadBtn = depthPresetDownloadButtonAddDepthPresetButton;
                    DynamicSwitchButton dynamicSwitchButtonAddDynamicSwitchButton = templateButtonBar.addDynamicSwitchButton();
                    dynamicSwitchButtonAddDynamicSwitchButton.setVisibility(8);
                    dynamicSwitchButtonAddDynamicSwitchButton.setClickCallback(new DynamicButtonClickCallBack(new WeakReference(this)));
                    dynamicSwitchButtonAddDynamicSwitchButton.setDynamicSwitchCallback(this.dynamicSwitchCallback);
                    this.dynamicSwitchBtn = dynamicSwitchButtonAddDynamicSwitchButton;
                    View viewAddDynamicButtonGroup = this.presetButtonGroup;
                    if (viewAddDynamicButtonGroup == null) {
                        TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
                        viewAddDynamicButtonGroup = primaryButtonBar != null ? primaryButtonBar.addDynamicButtonGroup() : null;
                    }
                    this.presetButtonGroup = viewAddDynamicButtonGroup;
                    if (Intrinsics.areEqual(this.isAutoDownload, Boolean.TRUE)) {
                        DepthPresetDownloadButton depthPresetDownloadButton = this.depthPresetDownloadBtn;
                        if (depthPresetDownloadButton != null) {
                            depthPresetDownloadButton.startDownload();
                        }
                    } else {
                        DepthPresetDownloadButton depthPresetDownloadButton2 = this.depthPresetDownloadBtn;
                        updateDownloadStatus(depthPresetDownloadButton2 != null ? depthPresetDownloadButton2.getStatus() : 0);
                    }
                    DepthPresetDownloadButton depthPresetDownloadButton3 = this.depthPresetDownloadBtn;
                    this.originVideoStatus = depthPresetDownloadButton3 != null ? depthPresetDownloadButton3.getStatus() : 0;
                }
                CommonConfig commonConfig3 = this.templateConfig;
                if (commonConfig3 != null && (lockscreenInfo = commonConfig3.getLockscreenInfo()) != null) {
                    wallpaperInfo2 = lockscreenInfo.getWallpaperInfo();
                }
                setDynamicSwitchButton(wallpaperInfo2);
            }
            ColorModes colorModes = this.colorModes;
            if (colorModes != null) {
                onColorModesChanged(colorModes);
            }
            if (viewModel != null && viewModel.getSelectTemplate() == 0) {
                z = true;
            }
            templateButtonBar.show(z);
        }
    }

    public void refreshBarLayout() {
        TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
        if (primaryButtonBar != null) {
            ViewGroup buttonGroup = primaryButtonBar.getButtonGroup();
            ViewGroup.LayoutParams layoutParams = primaryButtonBar.getButtonGroup().getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isPad()) {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                layoutParams2.bottomMargin = deviceUtil.isDisplayLandScape(context) ? 0 : getResources().getDimensionPixelSize(R.dimen.kg_function_button_group_margin_bottom);
            }
            buttonGroup.setLayoutParams(layoutParams2);
            if (primaryButtonBar.getDynamicButtonGroup().getLayoutParams() instanceof LinearLayout.LayoutParams) {
                RelativeLayout dynamicButtonGroup = primaryButtonBar.getDynamicButtonGroup();
                ViewGroup.LayoutParams layoutParams3 = primaryButtonBar.getDynamicButtonGroup().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) layoutParams3;
                if (deviceUtil.isPad() || deviceUtil.isFold()) {
                    layoutParams4.bottomMargin = getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_margin_bottom);
                }
                dynamicButtonGroup.setLayoutParams(layoutParams4);
                DynamicSwitchButton dynamicSwitchButton = primaryButtonBar.getDynamicSwitchButton();
                ViewGroup.LayoutParams layoutParams5 = primaryButtonBar.getDynamicSwitchButton().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
                RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) layoutParams5;
                if (deviceUtil.isPad() || deviceUtil.isFold()) {
                    layoutParams6.setMarginEnd(getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_margin_right));
                }
                dynamicSwitchButton.setLayoutParams(layoutParams6);
            }
        }
    }

    public final void refreshTransitionBackground(Bitmap bitmap) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        this.transitionBackground = bitmap;
        Wallpaper.Companion companion = Wallpaper.Companion;
        CommonConfig commonConfig = this.templateConfig;
        if (companion.isLivePhotoResource((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getVideoType())) && this.editable && this.from == 1) {
            EditorActivityViewModel.Companion companion2 = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            CoroutineScope coroutineScope = companion2.getCoroutineScope(context);
            if (coroutineScope != null) {
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C01061(null), 3, null);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x002d  */
    public final void refreshWallpaperOnConfigChange(WallpaperInfo wallpaperInfo) {
        Bitmap currentWallpaper;
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
        if (wallpaperInfo.getCurrentSmallSensorWallpaper() != null) {
            Wallpaper.Companion companion = Wallpaper.Companion;
            String resourceType = wallpaperInfo.getResourceType();
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (companion.needSmallSensorWallpaper(resourceType, context)) {
                currentWallpaper = wallpaperInfo.getCurrentSmallSensorWallpaper();
            } else {
                currentWallpaper = wallpaperInfo.getCurrentWallpaper();
            }
        } else {
            currentWallpaper = wallpaperInfo.getCurrentWallpaper();
        }
        Bitmap bitmap = currentWallpaper;
        CombinedWallpaperView.switchWallpaperView$default(combinedWallpaperView, wallpaperInfo, bitmap, WallpaperEditor.Companion.canScale(wallpaperInfo.getResourceType()) ? wallpaperInfo.getPositionInfo() : null, false, false, null, false, 104, null);
        if (bitmap != null) {
            combinedWallpaperView.setBackgroundDrawable(new CenterCropDrawable(bitmap));
        }
    }

    public final void registerTimeUpdateBroadcast() {
        Log.i("Keyguard-Editor-BaseTemplateView", "registerTimeUpdateBroadcast: editable=" + this.editable);
        if (this.editable) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_TICK");
            intentFilter.addAction("android.intent.action.TIME_SET");
            try {
                getContext().registerReceiver(this.timeUpdateReceiver, intentFilter);
            } catch (Exception e) {
                Log.e("Keyguard-Editor-BaseTemplateView", "registerTimeUpdateBroadcast: ", e);
            }
        }
    }

    public final void releaseFastPlayer() {
        this.fastPlayerManager.releaseFastPlayer();
    }

    public final boolean reloadTemplate(boolean z) {
        Integer numValueOf;
        TemplateConfig lockscreenInfo;
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo;
        if (this.isAodDialogTemplate && indexOfChild(this.wallpaperLayer) != -1) {
            removeView(this.wallpaperLayer);
            View view = this.wallpaperLayer;
            if (view != null) {
                addViewToTemplate(view, 0);
            }
        }
        if (z) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            boolean zIsDualClockAvailable = DualClockManager.isDualClockAvailable(context);
            boolean z2 = this.isDualClock;
            if (zIsDualClockAvailable != z2) {
                this.isDualClock = !z2;
                BaseClockView baseClockView = (BaseClockView) getClockLayer();
                if (baseClockView != null) {
                    baseClockView.updateIsDualClock(this.isDualClock);
                }
                WallpaperInfo wallpaperInfo2 = null;
                this.colorModes = null;
                try {
                    TemplateApi.Companion companion = TemplateApi.Companion;
                    Context context2 = getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                    String templateInfo = companion.getInstance(context2).readTemplateInfo();
                    numValueOf = (templateInfo == null || (commonConfig = (CommonConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(templateInfo, CommonConfig.class)) == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo2.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getMagicType());
                } catch (Exception e) {
                    Log.w("Keyguard-Editor-BaseTemplateView", "reloadTemplate: failed to parse saved template info", e);
                }
                CommonConfig commonConfig2 = this.templateConfig;
                if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null) {
                    wallpaperInfo2 = lockscreenInfo.getWallpaperInfo();
                }
                if (numValueOf != null && wallpaperInfo2 != null) {
                    wallpaperInfo2.updateMagicType(numValueOf.intValue());
                    DataUtil dataUtil = DataUtil.INSTANCE;
                    Context context3 = getContext();
                    Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                    dataUtil.resetWallpaperInfoMagicType(wallpaperInfo2, wallpaperInfo2, context3);
                }
                if (this.editable) {
                    FrameLayout frameLayout = this.topButtonBar;
                    if (frameLayout != null) {
                        frameLayout.setTranslationY(0.0f);
                    }
                    setOutlineProvider(new RoundOutlineProvider(getResources().getDimension(R.dimen.kg_template_view_radius)));
                    if (edited) {
                        applyTemplateInfo();
                    }
                }
                loadTemplate(this.templateConfig, true);
                return true;
            }
        }
        return false;
    }

    public final void removeBackground(@Nullable Long l) {
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
        combinedWallpaperView.removeCallbacks(this.removeBackgroundRunnable);
        if (l == null) {
            combinedWallpaperView.post(this.removeBackgroundRunnable);
        } else {
            combinedWallpaperView.postDelayed(this.removeBackgroundRunnable, l.longValue());
        }
    }

    public final void removeClockLoadListener() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.removeClockLoadListener();
        }
    }

    @CallSuper
    public void resetAllBottomSheet() {
        this.isDismissTemplateTitle = true;
        AutoBottomSheet autoBottomSheet = this.wallpaperFilterAutoBottomSheet;
        if (autoBottomSheet != null) {
            AutoBottomSheet.reset$default(autoBottomSheet, false, 1, null);
        }
        this.wallpaperFilterAutoBottomSheet = null;
        this.wallpaperFilterView = null;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.resetAllBottomSheet();
        }
        CombineEditListener combineEditListener = this.combineEditListener;
        if (combineEditListener != null) {
            combineEditListener.onDismissKeyboard();
        }
    }

    public final void resetClockFontColor() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.resetClockFontColor();
        }
    }

    public void resetColorModes() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        ColorModes colorModes = this.colorModes;
        if (colorModes != null) {
            onColorModesChanged(colorModes);
        }
        CommonConfig commonConfig = this.templateConfig;
        checkColorModesChanged((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper(), false);
    }

    public void resetPrimaryButtonBar() {
        KgVisualCheckBox kgVisualCheckBox = this.filterBtn;
        if (kgVisualCheckBox != null) {
            kgVisualCheckBox.setClickable(false);
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C01071(null), 2, null);
    }

    public final void resetStyleEditor() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.resetStyleEditor();
        }
    }

    public final void saveWallpaperPositionInfoBeforeConfigChange() {
        TemplateConfig lockscreenInfo;
        WallpaperPositionInfo positionInfo;
        HomeConfig homeInfo;
        if (this.editable) {
            WallpaperInfo wallpaperInfo = getWallpaperInfo();
            WallpaperInfo wallpaperInfo2 = null;
            if ((wallpaperInfo != null ? wallpaperInfo.getInfoType() : null) != WallpaperInfoType.HOME_WALLPAPER || getHomeFollowWapper()) {
                CommonConfig commonConfig = this.templateConfig;
                if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
                    wallpaperInfo2 = lockscreenInfo.getWallpaperInfo();
                }
            } else {
                CommonConfig commonConfig2 = this.templateConfig;
                if (commonConfig2 != null && (homeInfo = commonConfig2.getHomeInfo()) != null) {
                    wallpaperInfo2 = homeInfo.getWallpaperInfo();
                }
            }
            if (wallpaperInfo2 == null || !WallpaperEditor.Companion.canScale(wallpaperInfo2.getResourceType()) || (positionInfo = getPositionInfo(wallpaperInfo2)) == null) {
                return;
            }
            wallpaperInfo2.setPositionInfo(positionInfo);
        }
    }

    public void scaleTipsIsVisible(boolean z) {
        BaseClockView baseClockView;
        if (!this.editable || (baseClockView = (BaseClockView) getClockLayer()) == null) {
            return;
        }
        baseClockView.scaleTipsIsVisible(z);
    }

    public final void setAiGenerateBtn(@Nullable KgVisualCheckBox kgVisualCheckBox) {
        this.aiGenerateBtn = kgVisualCheckBox;
    }

    public final void setAllowToClickDynamicSwitchBtn(boolean z) {
        this.allowToClickDynamicSwitchBtn = z;
    }

    public final void setAnimLayer(boolean z) {
        this.isAnimLayer = z;
    }

    public final void setApplyAnimationPerformer(@Nullable ExitAnimationPerformer exitAnimationPerformer) {
        this.applyAnimationPerformer = exitAnimationPerformer;
    }

    public final void setApplyTransition(@Nullable TemplateApplier.ApplyCallback applyCallback) {
        this.applyTransition = applyCallback;
    }

    public final void setApplying(boolean z) {
        this.applying = z;
    }

    public final void setAutoColorPicker(@Nullable AutoColorPicker autoColorPicker) {
        this.autoColorPicker = autoColorPicker;
    }

    public final void setAutoDownload(boolean z) {
        this.isAutoDownload = Boolean.valueOf(z);
    }

    public final void setChangedTimeMagic(boolean z) {
        this.isChangedTimeMagic = z;
    }

    public final void setCheckBtn(boolean z) {
        this.isCheckBtn = z;
    }

    public final void setColorModes(@Nullable ColorModes colorModes) {
        this.colorModes = colorModes;
    }

    public final void setColorPickHandler(@Nullable ColorPickHandler colorPickHandler) {
        this.colorPickHandler = colorPickHandler;
    }

    public final void setCombineAnimListener(@Nullable CombineEditListener combineEditListener) {
        this.combineEditListener = combineEditListener;
    }

    public final void setCombineEditListener(@Nullable CombineEditListener combineEditListener) {
        this.combineEditListener = combineEditListener;
    }

    public final void setDarkWallpaper(boolean z) {
        this.isDarkWallpaper = z;
    }

    public final void setDepthPresetDownloadBtn(@Nullable DepthPresetDownloadButton depthPresetDownloadButton) {
        this.depthPresetDownloadBtn = depthPresetDownloadButton;
    }

    public final void setDepthVideoFirstFrameReady$keyguardeditor_release(boolean z) {
        this.depthVideoFirstFrameReady = z;
    }

    public final void setDepthVideoForegroundLayer(@Nullable View view) {
        this.depthVideoForegroundLayer = view;
    }

    public final void setDismissTemplateTitle(boolean z) {
        this.isDismissTemplateTitle = z;
    }

    public final void setDisplayClockEffect(int i) {
        this.displayClockEffect = i;
    }

    public final void setDualClock(boolean z) {
        this.isDualClock = z;
    }

    public final void setDynamicSwitchBtn(@Nullable DynamicSwitchButton dynamicSwitchButton) {
        this.dynamicSwitchBtn = dynamicSwitchButton;
    }

    public final void setDynamicSwitchButton(@Nullable WallpaperInfo wallpaperInfo) {
        DynamicSwitchButton dynamicSwitchButton;
        View viewAddDynamicButtonGroup = null;
        if (Wallpaper.Companion.isNormalLivePhotoResource(wallpaperInfo != null ? Integer.valueOf(wallpaperInfo.getVideoType()) : null)) {
            DynamicSwitchButton dynamicSwitchButtonAddDynamicSwitchButton = this.dynamicSwitchBtn;
            if (dynamicSwitchButtonAddDynamicSwitchButton == null) {
                TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
                dynamicSwitchButtonAddDynamicSwitchButton = primaryButtonBar != null ? primaryButtonBar.addDynamicSwitchButton() : null;
            }
            this.dynamicSwitchBtn = dynamicSwitchButtonAddDynamicSwitchButton;
            if (dynamicSwitchButtonAddDynamicSwitchButton != null) {
                dynamicSwitchButtonAddDynamicSwitchButton.setClickCallback(new DynamicButtonClickCallBack(new WeakReference(this)));
                dynamicSwitchButtonAddDynamicSwitchButton.setDynamicSwitchCallback(this.dynamicSwitchCallback);
                dynamicSwitchButtonAddDynamicSwitchButton.setVisibility(0);
                CommonConfig commonConfig = this.templateConfig;
                if (commonConfig != null) {
                    dynamicSwitchButtonAddDynamicSwitchButton.refreshLockConfig(commonConfig.getLockscreenInfo(), isHistoryTemplate());
                }
            }
            if (Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "video")) {
                DynamicSwitchButton dynamicSwitchButton2 = this.dynamicSwitchBtn;
                if (dynamicSwitchButton2 != null) {
                    DynamicSwitchButton.changeStatus$default(dynamicSwitchButton2, 2, false, false, 4, null);
                }
            } else {
                if (Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "image") && (dynamicSwitchButton = this.dynamicSwitchBtn) != null) {
                    DynamicSwitchButton.changeStatus$default(dynamicSwitchButton, 3, false, false, 4, null);
                }
            }
        }
        View view = this.presetButtonGroup;
        if (view == null) {
            TemplateButtonBar primaryButtonBar2 = getPrimaryButtonBar();
            if (primaryButtonBar2 != null) {
                viewAddDynamicButtonGroup = primaryButtonBar2.addDynamicButtonGroup();
            }
        } else {
            viewAddDynamicButtonGroup = view;
        }
        this.presetButtonGroup = viewAddDynamicButtonGroup;
    }

    public final void setEditFragment(@Nullable EditFragment editFragment) {
        this.editFragment = editFragment;
    }

    public final void setEditHierarchy(boolean z) {
        this.isEditHierarchy = z;
    }

    public final void setEditModeChangedListener(@Nullable EditModeChangedListener editModeChangedListener) {
        this.editModeChangedListener = editModeChangedListener;
    }

    public final void setEditTransition(boolean z) {
        this.isEditTransition = z;
    }

    public final void setEditable(boolean z) {
        this.editable = z;
    }

    public final void setEnableOriginWallpaperPlaceholder(boolean z) {
        this.enableOriginWallpaperPlaceholder = z;
    }

    public final void setEnterSecond(boolean z) {
        this.isEnterSecond = z;
    }

    public final void setExtraParameters(@Nullable Bundle bundle) {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.setExtraParameters(bundle);
        }
    }

    public final void setFastPlayerManager(@NotNull FastPlayerManager fastPlayerManager) {
        Intrinsics.checkNotNullParameter(fastPlayerManager, "<set-?>");
        this.fastPlayerManager = fastPlayerManager;
    }

    public final void setFilterBtn(@Nullable KgVisualCheckBox kgVisualCheckBox) {
        this.filterBtn = kgVisualCheckBox;
    }

    public final void setFollowSystemListener(@Nullable FollowSystemListener followSystemListener) {
        this.followSystemListener = followSystemListener;
    }

    public final void setForegroundVisibleWithDepthVideo() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || !TextUtils.equals(wallpaperInfo.getResourceType(), "video")) {
            return;
        }
        boolean zIsDepthVideo = MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType()));
        if (zIsDepthVideo && !this.depthVideoFirstFrameReady) {
            this.fastPlayerManager.setOpenGLSurfaceVisible(true);
            return;
        }
        View view = this.depthVideoForegroundLayer;
        if (view != null) {
            view.setVisibility(zIsDepthVideo ? 0 : 8);
        }
        this.fastPlayerManager.setOpenGLSurfaceVisible(zIsDepthVideo);
    }

    public final void setFrom(int i) {
        this.from = i;
    }

    public final void setGalleryOpened(boolean z) {
        this.isGalleryOpened = z;
    }

    public final void setHierarchyBtn(@Nullable KgVisualCheckBox kgVisualCheckBox) {
        this.hierarchyBtn = kgVisualCheckBox;
    }

    public final void setHierarchyEnable(boolean z) {
        this.hierarchyEnable = z;
    }

    public final void setHistoryTemplate(boolean z) {
        this.isHistoryTemplate = z;
    }

    public final void setInitFinish(boolean z) {
        this.initFinish = z;
    }

    public final void setLoadingView(@Nullable EditorLoadingView editorLoadingView) {
        this.loadingView = editorLoadingView;
    }

    public final void setLongClickListenerForLivePhoto() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        CommonConfig commonConfig = this.templateConfig;
        boolean zAreEqual = Intrinsics.areEqual((commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getResourceType(), "video");
        Wallpaper.Companion companion = Wallpaper.Companion;
        CommonConfig commonConfig2 = this.templateConfig;
        boolean zIsNormalLivePhotoResource = companion.isNormalLivePhotoResource((commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getVideoType()));
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (!zIsNormalLivePhotoResource || !zAreEqual) {
            Log.d("Keyguard-Editor-BaseTemplateView", "reset long click listener");
            if (combinedWallpaperView != null) {
                combinedWallpaperView.setWallpaperLongClickListener(null);
                return;
            }
            return;
        }
        Log.d("Keyguard-Editor-BaseTemplateView", "set long click listener for LivePhoto:" + (combinedWallpaperView != null));
        if (combinedWallpaperView != null) {
            combinedWallpaperView.setWallpaperLongClickListener(new View.OnLongClickListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda27
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return BaseTemplateView.setLongClickListenerForLivePhoto$lambda$102(this.f$0, view);
                }
            });
        }
    }

    public final void setMaskView(@Nullable View view) {
        this.maskView = view;
    }

    public final void setNavigationCallback(@Nullable Consumer<Object> consumer) {
        this.navigationCallback = consumer;
    }

    public final void setNeedRemoveBackground(boolean z) {
        this.needRemoveBackground = z;
    }

    public final void setNeedResetBackground(boolean z) {
        this.needResetBackground = z;
    }

    public final void setOnCancelDialogNegativeClick(@Nullable Function0<Unit> function0) {
        this.onCancelDialogNegativeClick = function0;
    }

    public final void setOnWallpaperDarkResultReadyListener(@Nullable Function1<? super Boolean, Unit> function1) {
        this.onWallpaperDarkResultReadyListener = function1;
    }

    public final void setOnceWallpaperReadyListener(@Nullable Function0<Unit> function0) {
        if (function0 == null || !(this.templateReadyIds.contains(1) || this.templateReadyIds.contains(2))) {
            this.onceWallpaperReadyListener = function0;
        } else {
            function0.invoke();
        }
    }

    public final void setOriginConfig(@Nullable CommonConfig commonConfig) {
        this.originConfig = commonConfig;
    }

    public final void setPendingDismissAfterSwitch$keyguardeditor_release(boolean z) {
        this.pendingDismissAfterSwitch = z;
    }

    public final void setPreset(@Nullable Boolean bool) {
        this.isPreset = bool;
    }

    public final void setPresetButtonGroup(@Nullable View view) {
        this.presetButtonGroup = view;
    }

    public final void setPresetData(@Nullable Boolean bool) {
        this.isPreset = bool;
    }

    public final void setShouldSetColorSelectionToAuto(boolean z) {
        this.shouldSetColorSelectionToAuto = z;
    }

    public final void setSourceType(int i) {
        this.from = i;
    }

    public final void setState(int i) {
        this.state = i;
    }

    public final void setStopped(boolean z) {
        this.isStopped = z;
    }

    public final void setSuccessUpdateClockView(boolean z) {
        this.successUpdateClockView = z;
    }

    public final void setTemplateConfig(@Nullable CommonConfig commonConfig) {
        this.templateConfig = commonConfig;
    }

    public final void setTemplateSource(long j) {
        Log.i("Keyguard-Editor-BaseTemplateView", "setTemplateSource: " + j);
        this.templateSource = j;
    }

    public final void setTemplateTitle(@Nullable TextView textView) {
        this.templateTitle = textView;
    }

    public final void setTrackId(@Nullable String str) {
        this.trackId = str;
    }

    public final void setTransitionBackground(@Nullable Bitmap bitmap) {
        this.transitionBackground = bitmap;
    }

    public final void setVideoProcessedByAI(boolean z) {
        this.videoProcessedByAI = z;
    }

    public final void setWallpaperChanged(boolean z) {
        this.isWallpaperChanged = z;
    }

    public final void setWallpaperEditor(@NotNull WallpaperEditor wallpaperEditor) {
        Intrinsics.checkNotNullParameter(wallpaperEditor, "<set-?>");
        this.wallpaperEditor = wallpaperEditor;
    }

    public final void setWallpaperFilterAutoBottomSheet(@Nullable AutoBottomSheet autoBottomSheet) {
        this.wallpaperFilterAutoBottomSheet = autoBottomSheet;
    }

    public final void setWallpaperFilterBottomSheetHeight(int i) {
        this.wallpaperFilterBottomSheetHeight = i;
    }

    public final void setWallpaperFilterView(@Nullable WallpaperFilterView wallpaperFilterView) {
        this.wallpaperFilterView = wallpaperFilterView;
    }

    public void setWallpaperHasChanged(boolean z) {
        this.wallpaperHasChanged = z;
    }

    public final void setWallpaperLayer(@NotNull View wallpaperView) {
        Intrinsics.checkNotNullParameter(wallpaperView, "wallpaperView");
        this.wallpaperLayer = wallpaperView;
    }

    public final void setWallpaperPickerLauncher(@Nullable WallpaperEditor.WallpaperPickerLauncher wallpaperPickerLauncher) {
        this.wallpaperPickerLauncher = wallpaperPickerLauncher;
    }

    public final boolean shouldEnableHierarchy() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return DeviceUtil.INSTANCE.isSupportHierarchyDevices() && (baseClockView != null ? baseClockView.supportHierarchy() : true) && !isInternationalGalleryOpen();
    }

    public final boolean shouldEnableVideoDepthWithCloseStatus() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Wallpaper.Companion companion = Wallpaper.Companion;
        CommonConfig commonConfig = this.templateConfig;
        return companion.isLivePhotoResource((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getVideoType())) && FileUtil.isSupportVideoDepth() && !this.videoProcessedByAI;
    }

    public final boolean shouldOpenVideoDepth() {
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        return (!FileUtil.isSupportVideoDepth() || (commonConfig = this.templateConfig) == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || !wallpaperInfo.isDepthVideo()) ? false : true;
    }

    public void showAllFrame() {
        BaseClockView baseClockView;
        if (!this.editable || (baseClockView = (BaseClockView) getClockLayer()) == null) {
            return;
        }
        baseClockView.showAllFrame();
    }

    public boolean showBottomSheetModal() {
        CombineEditView combineEditView;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null && baseClockView.showBottomSheetModal()) {
            return true;
        }
        EditFragment editFragment = this.editFragment;
        return (editFragment == null || (combineEditView = editFragment.getCombineEditView()) == null || combineEditView.getTemplateStatus() != 2) ? false : true;
    }

    public final void showCancelDialog(Consumer consumer) {
        if (this.cancelDialogShow) {
            return;
        }
        dismissCancelDialog();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = consumer;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        ActivityUIAdapter.Companion companion = ActivityUIAdapter.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        AlertDialog alertDialogShow = new AlertDialogBuilder(context, companion.isLightTheme(context2) ? miuix.appcompat.R.style.AlertDialog_Theme_Light_Danger : miuix.appcompat.R.style.AlertDialog_Theme_Dark_Danger).setTitle(R.string.kg_discard_changes_text).setPositiveButton(R.string.kg_dialog_fashion_gallery_button_abandon, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseTemplateView.showCancelDialog$lambda$105(this.f$0, objectRef, dialogInterface, i);
            }
        }).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.cancelDialogShow = true;
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.cancelDialogShow = false;
            }
        }).setNegativeButton(R.string.kg_dialog_fashion_gallery_button_positive, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseTemplateView.showCancelDialog$lambda$108(this.f$0, dialogInterface, i);
            }
        }).show();
        AlertDialogsKtKt.adaptN8AlertDialog$default(alertDialogShow, true, false, false, 12, null);
        this.alertDialog = alertDialogShow;
    }

    public final void showLoading() {
        EditorLoadingView editorLoadingView = this.loadingView;
        if (editorLoadingView != null) {
            editorLoadingView.showLoading();
        }
    }

    public final void showProduceDepthVideoProgressDialog(int i) {
        if (this.depthVideoProgressDialog == null) {
            View viewInflate = View.inflate(getContext(), R.layout.kg_depth_video_progress_dialog_view, null);
            this.depthVideoProgressDialogPercent = (TextView) viewInflate.findViewById(R.id.depthVideoProgressDialogPercent);
            this.depthVideoProgressDialogProgress = (ProgressBar) viewInflate.findViewById(R.id.depthVideoProgressDialogProgress);
            TextView textView = this.depthVideoProgressDialogPercent;
            if (textView != null) {
                textView.setText(i + "%");
            }
            ProgressBar progressBar = this.depthVideoProgressDialogProgress;
            if (progressBar != null) {
                progressBar.setProgress(i);
            }
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            this.depthVideoProgressDialog = new AlertDialogBuilder(context, miuix.appcompat.R.style.AlertDialog_Theme_DayNight_Danger).setView(viewInflate).setCancelable(false).setNegativeButton(R.string.kg_dialog_apply_super_wallpaper_negative_button, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    BaseTemplateView.showProduceDepthVideoProgressDialog$lambda$119(this.f$0, dialogInterface, i2);
                }
            }).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    this.f$0.cancelDialogShow = true;
                }
            }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda11
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.cancelDialogShow = false;
                }
            }).show();
            return;
        }
        TextView textView2 = this.depthVideoProgressDialogPercent;
        if (textView2 != null) {
            textView2.setText(i + "%");
        }
        ProgressBar progressBar2 = this.depthVideoProgressDialogProgress;
        if (progressBar2 != null) {
            progressBar2.setProgress(i);
        }
        AlertDialog alertDialog = this.depthVideoProgressDialog;
        Intrinsics.checkNotNull(alertDialog);
        if (alertDialog.isShowing()) {
            return;
        }
        AlertDialog alertDialog2 = this.depthVideoProgressDialog;
        Intrinsics.checkNotNull(alertDialog2);
        alertDialog2.show();
    }

    public void startCheckDepthVideoAvoid() {
    }

    @Override // com.miui.keyguard.editor.base.ExitAnimationPerformer
    public void startExitTransformerAnimation(boolean z, @Nullable TemplateApplier.ApplyCallback applyCallback, @Nullable ExitAnimationPerformer exitAnimationPerformer) {
        if (z) {
            AnimState animStateAdd = new AnimState("alpha_dismiss").add(ViewProperty.ALPHA, 0.0f, new long[0]);
            AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 1.0f, 0.45f));
            Iterator<T> it = aboveWallpaperLayers().iterator();
            while (it.hasNext()) {
                Folme.useAt((View) it.next()).state().to(animStateAdd, ease);
            }
        } else {
            switchTips(false);
            hideAllFrame();
            BaseClockView baseClockView = (BaseClockView) getClockLayer();
            if (baseClockView != null) {
                baseClockView.onExitTransform();
            }
        }
        TemplateButtonBar templateButtonBar = this.primaryButtonBar;
        if (templateButtonBar != null) {
            templateButtonBar.exitForApply(z);
        }
    }

    public final void startFastPlayer() {
        this.fastPlayerManager.startFastPlayer();
    }

    public final void startForcePlayerWithDepthVideo(@NotNull String tag) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        Intrinsics.checkNotNullParameter(tag, "tag");
        CommonConfig commonConfig = this.templateConfig;
        Log.d("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: " + ((commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo()));
        CommonConfig commonConfig2 = this.templateConfig;
        if (commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) {
            return;
        }
        if (!TextUtils.equals(wallpaperInfo.getResourceType(), "video")) {
            stopForcePlayerWithDepthVideo();
            return;
        }
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        boolean zSupportHierarchy = baseClockView != null ? baseClockView.supportHierarchy() : false;
        boolean zLockPageFocus = lockPageFocus();
        if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType())) && zSupportHierarchy) {
            startForcePlayerWithDepthVideo(wallpaperInfo.getDepthVideo(), true, zLockPageFocus, tag + "_show");
            return;
        }
        if (!wallpaperInfo.isDepthVideo() || !zSupportHierarchy || !FileUtil.isSupportVideoDepth()) {
            startForcePlayerWithNormalVideo(wallpaperInfo.getOriginResourcePath(), zLockPageFocus, "lock normal");
            return;
        }
        startForcePlayerWithDepthVideo(wallpaperInfo.getOriginResourcePath(), false, zLockPageFocus, tag + "_hide");
    }

    public final void startForcePlayerWithDepthVideo(String str, boolean z, boolean z2, String str2) {
        View viewWallpaperLayerView;
        Log.d("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if ((wallpaperLayer != null ? wallpaperLayer.wallpaperLayerView() : null) instanceof VideoTextureView) {
            IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
            if (wallpaperLayer2 == null || (viewWallpaperLayerView = wallpaperLayer2.wallpaperLayerView()) == null) {
                return;
            }
            viewWallpaperLayerView.requestLayout();
            viewWallpaperLayerView.addOnLayoutChangeListener(new BaseTemplateView$startForcePlayerWithDepthVideo$3$1(viewWallpaperLayerView, this, str, z2, str2, z));
            return;
        }
        IWallpaperLayer wallpaperLayer3 = getWallpaperLayer();
        Log.e("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: background view is not VideoTextureView! is " + (wallpaperLayer3 != null ? wallpaperLayer3.wallpaperLayerView() : null));
        try {
            KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "BaseTemplateView.startForcePlayerWithDepthVideo_not_VideoTextureView", null, 2, null);
        } catch (Exception unused) {
        }
    }

    public final void startForcePlayerWithNormalVideo(@Nullable String str, boolean z, @NotNull String tag) {
        View viewWallpaperLayerView;
        Intrinsics.checkNotNullParameter(tag, "tag");
        Log.d("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithNormalVideo: " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if ((wallpaperLayer != null ? wallpaperLayer.wallpaperLayerView() : null) instanceof VideoTextureView) {
            IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
            if (wallpaperLayer2 == null || (viewWallpaperLayerView = wallpaperLayer2.wallpaperLayerView()) == null) {
                return;
            }
            viewWallpaperLayerView.requestLayout();
            viewWallpaperLayerView.addOnLayoutChangeListener(new BaseTemplateView$startForcePlayerWithNormalVideo$2$1(viewWallpaperLayerView, this, str, z, tag));
            return;
        }
        IWallpaperLayer wallpaperLayer3 = getWallpaperLayer();
        Log.e("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithNormalVideo: background view is not VideoTextureView! is " + (wallpaperLayer3 != null ? wallpaperLayer3.wallpaperLayerView() : null));
        try {
            KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "BaseTemplateView.startForcePlayerWithNormalVideo_not_VideoTextureView", null, 2, null);
        } catch (Exception unused) {
        }
    }

    public void startPickColorFromWallpaper(@Nullable Bitmap bitmap, boolean z, boolean z2, @Nullable Rect rect) {
        if (this.autoColorPicker == null) {
            this.autoColorPicker = createAutoColorPicker();
        }
        if (bitmap == null) {
            Log.w("Keyguard-Editor-BaseTemplateView", "startPickColorFromWallpaper: bitmap is null");
            onFirstColorPickComplete();
        } else {
            if (completeWithCacheColor(z)) {
                onFirstColorPickComplete();
                return;
            }
            boolean z3 = (this.editable || this.isAodDialogTemplate) ? false : true;
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new C01081(z3, this, z, bitmap, rect, z2, null), 3, null);
        }
    }

    public final void stopForcePlayerWithDepthVideo() {
        post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                BaseTemplateView.stopForcePlayerWithDepthVideo$lambda$54(this.f$0);
            }
        });
    }

    public final void switchClockTemplate(@NotNull TemplateConfig desTemplateConfig) {
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        CommonConfig commonConfig2;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo3;
        TemplateConfig lockscreenInfo4;
        ClockInfo clockInfo4;
        TemplateConfig lockscreenInfo5;
        ClockInfo clockInfo5;
        TemplateConfig lockscreenInfo6;
        ClockInfo clockInfo6;
        Intrinsics.checkNotNullParameter(desTemplateConfig, "desTemplateConfig");
        CommonConfig commonConfig3 = this.templateConfig;
        String templateId = (commonConfig3 == null || (lockscreenInfo6 = commonConfig3.getLockscreenInfo()) == null || (clockInfo6 = lockscreenInfo6.getClockInfo()) == null) ? null : clockInfo6.getTemplateId();
        final String templateId2 = desTemplateConfig.getClockInfo().getTemplateId();
        CommonConfig commonConfig4 = this.templateConfig;
        Integer numValueOf = (commonConfig4 == null || (lockscreenInfo5 = commonConfig4.getLockscreenInfo()) == null || (clockInfo5 = lockscreenInfo5.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo5.getStyle());
        int style = desTemplateConfig.getClockInfo().getStyle();
        CommonConfig commonConfig5 = this.templateConfig;
        Integer numValueOf2 = (commonConfig5 == null || (lockscreenInfo4 = commonConfig5.getLockscreenInfo()) == null || (clockInfo4 = lockscreenInfo4.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo4.getClassicLine1());
        Log.d("Keyguard-Editor-BaseTemplateView", "switchClockTemplate: " + templateId + "(style=" + numValueOf + ") → " + templateId2 + "(style=" + style + "), cur.classicLine1=" + numValueOf2 + ", des.classicLine1=" + desTemplateConfig.getClockInfo().getClassicLine1());
        CommonConfig commonConfig6 = this.templateConfig;
        boolean zEquals = TextUtils.equals(templateId2, (commonConfig6 == null || (lockscreenInfo3 = commonConfig6.getLockscreenInfo()) == null || (clockInfo3 = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo3.getTemplateId());
        boolean z = !TextUtils.equals(templateId, "classic_plus") ? (commonConfig = this.templateConfig) == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null || desTemplateConfig.getClockInfo().getStyle() != clockInfo.getStyle() : (commonConfig2 = this.templateConfig) == null || (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo2.getClockInfo()) == null || desTemplateConfig.getClockInfo().getClassicLine2() != clockInfo2.getClassicLine2();
        if (zEquals && z) {
            return;
        }
        this.isTemplateSwitching = true;
        this.pendingDismissAfterSwitch = false;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        final BaseClockView baseClockViewCreateClockLayer = createClockLayer(context, templateId2, this.editFragment);
        if (baseClockViewCreateClockLayer == null) {
            this.isTemplateSwitching = false;
            this.pendingDismissAfterSwitch = false;
            return;
        }
        baseClockViewCreateClockLayer.setVisibility(4);
        addViewToTemplate$default(this, baseClockViewCreateClockLayer, 0, 2, null);
        baseClockViewCreateClockLayer.addMiuiClockViewInitListener(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                BaseTemplateView.switchClockTemplate$lambda$40$lambda$38(baseClockViewCreateClockLayer, this, templateId2, baseClockViewCreateClockLayer);
            }
        });
        CommonConfig commonConfig7 = this.templateConfig;
        updateCurrentTemplateConfig(commonConfig7 != null ? commonConfig7.getLockscreenInfo() : null, desTemplateConfig, baseClockViewCreateClockLayer);
        updateDisplayClockEffect();
        CommonConfig commonConfig8 = this.templateConfig;
        if (commonConfig8 != null) {
            initTemplateBean(baseClockViewCreateClockLayer, commonConfig8.getLockscreenInfo());
        }
        startCheckDepthVideoAvoid();
        markClockTemplateChanged(true);
    }

    public void switchTips(boolean z) {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.switchTips(z);
        }
    }

    public final void syncClockEffectConfig(TemplateConfig templateConfig, TemplateConfig templateConfig2, BaseClockView baseClockView) {
        DoodleInfo doodle;
        SignatureInfo signatureInfo;
        ClockInfo clockInfo;
        ClockInfo clockInfo2;
        String templateId = (templateConfig == null || (clockInfo2 = templateConfig.getClockInfo()) == null) ? null : clockInfo2.getTemplateId();
        String templateId2 = templateConfig2.getClockInfo().getTemplateId();
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel != null) {
            FontFilterData primaryFontFilter = viewModel.getPrimaryFontFilter();
            FontFilterData secondaryFontFilter = viewModel.getSecondaryFontFilter();
            Log.i("Keyguard-Editor-BaseTemplateView", "syncClockEffectConfig: primaryFilterData=" + primaryFontFilter + ", secondaryFilterData=" + secondaryFontFilter);
            int color = primaryFontFilter.getColor();
            Triple tripleAdjustClockColor = adjustClockColor(templateConfig != null ? templateConfig.getClockInfo() : null, templateId2, primaryFontFilter.getColorId(), secondaryFontFilter.getColor(), secondaryFontFilter.getColorId());
            int iIntValue = ((Number) tripleAdjustClockColor.component1()).intValue();
            int iIntValue2 = ((Number) tripleAdjustClockColor.component2()).intValue();
            int iIntValue3 = ((Number) tripleAdjustClockColor.component3()).intValue();
            int iAdjustClockEffect = adjustClockEffect(primaryFontFilter.getFilterId(), templateId2, baseClockView);
            if (templateConfig != null && (clockInfo = templateConfig.getClockInfo()) != null) {
                clockInfo.setClockEffect(iAdjustClockEffect);
                clockInfo.setPrimaryColor(color);
                clockInfo.setPrimaryFontColorPanelId(iIntValue);
                clockInfo.setAutoPrimaryColor(iIntValue == 0);
                clockInfo.setSecondaryColor(iIntValue2);
                clockInfo.setSecondaryFontColorPanelId(iIntValue3);
                clockInfo.setAutoSecondaryColor(iIntValue3 == 0);
            }
            if (templateConfig != null && (signatureInfo = templateConfig.getSignatureInfo()) != null) {
                signatureInfo.setClockEffect(iAdjustClockEffect);
                signatureInfo.setPrimaryColor(iIntValue2);
                signatureInfo.setPrimaryFontColorPanelId(iIntValue3);
                signatureInfo.setAutoPrimaryColor(iIntValue3 == 0);
                signatureInfo.setBlendColor(iIntValue2);
            }
            if (templateConfig != null && (doodle = templateConfig.getDoodle()) != null) {
                doodle.setSolidColor(iIntValue2);
                doodle.setPrimaryFontColorPanelId(iIntValue3);
                doodle.setAutoSolidColor(false);
            }
        }
        Log.d("Keyguard-Editor-BaseTemplateView", "syncClockEffectConfig: lastTemplateId=" + templateId + ", desTemplateId=" + templateId2 + " curTemplateConfig=" + templateConfig);
    }

    public final void takeVideoDepthScreenshot() throws InterruptedException {
        IWallpaperLayer iWallpaperLayerM733getDepthVideoForegroundLayer;
        Surface videoSurface;
        IWallpaperLayer wallpaperLayer;
        Surface videoSurface2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        MagicType magicType = MagicType.INSTANCE;
        CommonConfig commonConfig = this.templateConfig;
        if (!magicType.isDepthVideo((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getMagicType())) || (iWallpaperLayerM733getDepthVideoForegroundLayer = m733getDepthVideoForegroundLayer()) == null || (videoSurface = iWallpaperLayerM733getDepthVideoForegroundLayer.getVideoSurface()) == null || (wallpaperLayer = getWallpaperLayer()) == null || (videoSurface2 = wallpaperLayer.getVideoSurface()) == null) {
            return;
        }
        if (this.worker == null) {
            HandlerThread handlerThread = new HandlerThread("takeVideoDepthScreenshot");
            this.worker = handlerThread;
            Intrinsics.checkNotNull(handlerThread);
            handlerThread.start();
        }
        final HandlerThread handlerThread2 = this.worker;
        if (handlerThread2 != null) {
            Handler handler = new Handler(handlerThread2.getLooper());
            final CountDownLatch countDownLatch = new CountDownLatch(3);
            final Ref.LongRef longRef = new Ref.LongRef();
            longRef.element = System.currentTimeMillis();
            View view = this.wallpaperLayer;
            int width = view != null ? (int) (view.getWidth() * 1.0f) : 0;
            View view2 = this.wallpaperLayer;
            int height = view2 != null ? (int) (view2.getHeight() * 1.0f) : 0;
            BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            final Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(bitmapCreator, width, height, config, false, false, 24, null);
            this.fastPlayerManager.isFirstFrameReady(new Function0() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return BaseTemplateView.takeVideoDepthScreenshot$lambda$52$lambda$49(countDownLatch);
                }
            });
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            if (!countDownLatch.await(600L, timeUnit)) {
                Log.w("Keyguard-Editor-BaseTemplateView", "Timeout waiting for first frame");
            }
            this.fastPlayerManager.pauseFastPlayer();
            PixelCopy.request(videoSurface2, bitmapCreateBitmap$default, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda13
                @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                public final void onPixelCopyFinished(int i) {
                    BaseTemplateView.takeVideoDepthScreenshot$lambda$52$lambda$50(longRef, bitmapCreateBitmap$default, this, handlerThread2, countDownLatch, i);
                }
            }, handler);
            final Bitmap bitmapCreateBitmap$default2 = BitmapCreator.createBitmap$default(bitmapCreator, width, height, config, false, false, 24, null);
            PixelCopy.request(videoSurface, bitmapCreateBitmap$default2, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda14
                @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                public final void onPixelCopyFinished(int i) {
                    BaseTemplateView.takeVideoDepthScreenshot$lambda$52$lambda$51(longRef, bitmapCreateBitmap$default2, this, handlerThread2, countDownLatch, i);
                }
            }, handler);
            countDownLatch.await(500L, timeUnit);
            this.fastPlayerManager.startFastPlayer();
            Log.d("Keyguard-Editor-BaseTemplateView", "bitmap consume time is " + (System.currentTimeMillis() - longRef.element));
        }
    }

    @Nullable
    public final CommonConfig templateConfig() {
        return this.templateConfig;
    }

    public final void tryApplyTemplate(Consumer consumer) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        android.app.WallpaperInfo wallpaperInfo2;
        Log.i("Keyguard-Editor-BaseTemplateView", "apply template");
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView != null && combinedWallpaperView.isScrollingOrScaling()) {
            Log.i("Keyguard-Editor-BaseTemplateView", "tryApplyTemplate: isGesturing = true, so end apply");
            return;
        }
        this.navigationCallback = consumer;
        HashMap superWallpaperPackageHashMap = Wallpaper.Companion.getSuperWallpaperPackageHashMap();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        EditorActivityViewModel viewModel = null;
        String str = (String) superWallpaperPackageHashMap.get((wallpaperManager == null || (wallpaperInfo2 = wallpaperManager.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getPackageName());
        CommonConfig commonConfig = this.templateConfig;
        boolean z = !StringsKt.equals$default(str, (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getOriginResourcePath(), false, 2, null);
        if (getContext() instanceof EditorActivity) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
            viewModel = companion.getViewModel((EditorActivity) context);
        }
        new TemplateApplier(this, this.applyAnimationPerformer, viewModel).applyTemplate(this, z);
    }

    public final void unRegisterTimeUpdateBroadcast() {
        Log.i("Keyguard-Editor-BaseTemplateView", "unRegisterTimeUpdateBroadcast: editable=" + this.editable);
        if (this.editable) {
            try {
                getContext().unregisterReceiver(this.timeUpdateReceiver);
            } catch (Exception e) {
                Log.e("Keyguard-Editor-BaseTemplateView", "unRegisterTimeUpdateBroadcast: ", e);
            }
        }
    }

    public final void updataClockConfig(@NotNull Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.dispatchConfigurationChanged(newConfig);
        }
        onConfigurationChanged(newConfig);
    }

    /* JADX WARN: Code duplicated, block: B:115:0x023c  */
    /* JADX WARN: Code duplicated, block: B:116:0x023e  */
    /* JADX WARN: Code duplicated, block: B:120:0x0251  */
    /* JADX WARN: Code duplicated, block: B:124:0x0264  */
    /* JADX WARN: Code duplicated, block: B:128:0x0277  */
    /* JADX WARN: Code duplicated, block: B:132:0x028a  */
    /* JADX WARN: Code duplicated, block: B:136:0x029d  */
    /* JADX WARN: Code duplicated, block: B:140:0x02b0  */
    /* JADX WARN: Code duplicated, block: B:143:0x02b8  */
    /* JADX WARN: Code duplicated, block: B:147:0x02c8  */
    /* JADX WARN: Code duplicated, block: B:149:0x02ce  */
    /* JADX WARN: Code duplicated, block: B:150:0x02d0  */
    /* JADX WARN: Code duplicated, block: B:158:0x02e7  */
    /* JADX WARN: Code duplicated, block: B:29:0x0064  */
    /* JADX WARN: Code duplicated, block: B:30:0x0066  */
    /* JADX WARN: Code duplicated, block: B:34:0x0079  */
    /* JADX WARN: Code duplicated, block: B:38:0x008c  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void updateCurrentTemplateConfig(TemplateConfig templateConfig, TemplateConfig templateConfig2, BaseClockView baseClockView) {
        ClockInfo clockInfo;
        ClockInfo clockInfo2;
        DoodleInfo doodle;
        SignatureInfo signatureInfo;
        ClockInfo clockInfo3;
        String classicSignature;
        ClockInfo clockInfo4;
        ClockInfo clockInfo5;
        ClockInfo clockInfo6;
        ClockInfo clockInfo7;
        ClockInfo clockInfo8;
        ClockInfo clockInfo9;
        ClockInfo clockInfo10;
        float timeWidth;
        float timeHeight;
        float clockTopRatio;
        float clockBottomRatio;
        int clockDepthType;
        boolean zIsDoubleRow;
        boolean zIsColonShow;
        ClockInfo clockInfo11;
        ClockInfo clockInfo12;
        ClockInfo clockInfo13;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo14;
        CommonConfig commonConfig = this.templateConfig;
        ClockBean defaultAllInOneParams = null;
        String templateId = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo14 = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo14.getTemplateId();
        String templateId2 = templateConfig2.getClockInfo().getTemplateId();
        BaseClockView baseClockView2 = (BaseClockView) getClockLayer();
        Float fValueOf = baseClockView2 != null ? Float.valueOf(baseClockView2.getLastStyleGlassTransparency()) : null;
        String lastStyleClassicSignature = baseClockView2 != null ? baseClockView2.getLastStyleClassicSignature() : null;
        switch (templateId2.hashCode()) {
            case -1326135015:
                if (templateId2.equals("doodle")) {
                    if ((templateConfig != null ? templateConfig.getDoodle() : null) == null && (doodle = templateConfig2.getDoodle()) != null && templateConfig != null) {
                        templateConfig.setDoodle(doodle);
                    }
                }
                break;
            case -567074186:
                if (templateId2.equals("magazine_a")) {
                    signatureInfo = templateConfig2.getSignatureInfo();
                    if (signatureInfo != null && templateConfig != null) {
                        templateConfig.setSignatureInfo(signatureInfo);
                    }
                }
                break;
            case -567074185:
                if (templateId2.equals("magazine_b")) {
                    signatureInfo = templateConfig2.getSignatureInfo();
                    if (signatureInfo != null) {
                        templateConfig.setSignatureInfo(signatureInfo);
                    }
                }
                break;
            case -138709961:
                if (templateId2.equals("classic_max")) {
                    if (templateConfig != null && (clockInfo9 = templateConfig.getClockInfo()) != null) {
                        clockInfo9.setClassicLine1(templateConfig2.getClockInfo().getClassicLine1());
                    }
                    if (templateConfig != null && (clockInfo8 = templateConfig.getClockInfo()) != null) {
                        clockInfo8.setClassicLine2(templateConfig2.getClockInfo().getClassicLine2());
                    }
                    if (templateConfig != null && (clockInfo7 = templateConfig.getClockInfo()) != null) {
                        clockInfo7.setClassicLine3(templateConfig2.getClockInfo().getClassicLine3());
                    }
                    if (templateConfig != null && (clockInfo6 = templateConfig.getClockInfo()) != null) {
                        clockInfo6.setClassicLine4(templateConfig2.getClockInfo().getClassicLine4());
                    }
                    if (templateConfig != null && (clockInfo5 = templateConfig.getClockInfo()) != null) {
                        clockInfo5.setClassicLine5(templateConfig2.getClockInfo().getClassicLine5());
                    }
                    if (templateConfig != null && (clockInfo4 = templateConfig.getClockInfo()) != null) {
                        clockInfo4.setClockWeight(templateConfig2.getClockInfo().getClockWeight());
                    }
                    if (templateConfig != null && (clockInfo3 = templateConfig.getClockInfo()) != null) {
                        if (lastStyleClassicSignature == null && (lastStyleClassicSignature = templateConfig2.getClockInfo().getClassicSignature()) == null) {
                            ClockInfo clockInfo15 = templateConfig.getClockInfo();
                            classicSignature = clockInfo15 != null ? clockInfo15.getClassicSignature() : null;
                            if (classicSignature == null) {
                                lastStyleClassicSignature = "";
                            } else {
                                lastStyleClassicSignature = classicSignature;
                            }
                        }
                        clockInfo3.setClassicSignature(lastStyleClassicSignature);
                    }
                }
                break;
            case -4941529:
                if (templateId2.equals("classic_plus")) {
                    if (templateConfig != null) {
                        clockInfo9.setClassicLine1(templateConfig2.getClockInfo().getClassicLine1());
                    }
                    if (templateConfig != null) {
                        clockInfo8.setClassicLine2(templateConfig2.getClockInfo().getClassicLine2());
                    }
                    if (templateConfig != null) {
                        clockInfo7.setClassicLine3(templateConfig2.getClockInfo().getClassicLine3());
                    }
                    if (templateConfig != null) {
                        clockInfo6.setClassicLine4(templateConfig2.getClockInfo().getClassicLine4());
                    }
                    if (templateConfig != null) {
                        clockInfo5.setClassicLine5(templateConfig2.getClockInfo().getClassicLine5());
                    }
                    if (templateConfig != null) {
                        clockInfo4.setClockWeight(templateConfig2.getClockInfo().getClockWeight());
                    }
                    if (templateConfig != null) {
                        if (lastStyleClassicSignature == null) {
                            ClockInfo clockInfo16 = templateConfig.getClockInfo();
                            if (clockInfo16 != null) {
                            }
                            if (classicSignature == null) {
                                lastStyleClassicSignature = "";
                            } else {
                                lastStyleClassicSignature = classicSignature;
                            }
                        }
                        clockInfo3.setClassicSignature(lastStyleClassicSignature);
                    }
                }
                break;
            case 853620882:
                if (templateId2.equals("classic")) {
                    if (templateConfig != null) {
                        clockInfo9.setClassicLine1(templateConfig2.getClockInfo().getClassicLine1());
                    }
                    if (templateConfig != null) {
                        clockInfo8.setClassicLine2(templateConfig2.getClockInfo().getClassicLine2());
                    }
                    if (templateConfig != null) {
                        clockInfo7.setClassicLine3(templateConfig2.getClockInfo().getClassicLine3());
                    }
                    if (templateConfig != null) {
                        clockInfo6.setClassicLine4(templateConfig2.getClockInfo().getClassicLine4());
                    }
                    if (templateConfig != null) {
                        clockInfo5.setClassicLine5(templateConfig2.getClockInfo().getClassicLine5());
                    }
                    if (templateConfig != null) {
                        clockInfo4.setClockWeight(templateConfig2.getClockInfo().getClockWeight());
                    }
                    if (templateConfig != null) {
                        if (lastStyleClassicSignature == null) {
                            ClockInfo clockInfo17 = templateConfig.getClockInfo();
                            if (clockInfo17 != null) {
                            }
                            if (classicSignature == null) {
                                lastStyleClassicSignature = "";
                            } else {
                                lastStyleClassicSignature = classicSignature;
                            }
                        }
                        clockInfo3.setClassicSignature(lastStyleClassicSignature);
                    }
                }
                break;
            case 1250520234:
                if (templateId2.equals("all_in_one")) {
                    ClockInfo clockInfo18 = templateConfig2.getClockInfo();
                    boolean z = clockInfo18.getTimeWidth() == 0.0f && clockInfo18.getTimeHeight() == 0.0f;
                    if (z) {
                        AllInOneClockMigration allInOneClockMigration = AllInOneClockMigration.INSTANCE;
                        int style = clockInfo18.getStyle();
                        Context context = getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                        defaultAllInOneParams = allInOneClockMigration.getDefaultAllInOneParams(style, context);
                    }
                    if (templateConfig != null && (clockInfo10 = templateConfig.getClockInfo()) != null) {
                        if (z) {
                            Intrinsics.checkNotNull(defaultAllInOneParams);
                            timeWidth = defaultAllInOneParams.getTimeWidth();
                        } else {
                            timeWidth = clockInfo18.getTimeWidth();
                        }
                        clockInfo10.setTimeWidth(timeWidth);
                        if (z) {
                            Intrinsics.checkNotNull(defaultAllInOneParams);
                            timeHeight = defaultAllInOneParams.getTimeHeight();
                        } else {
                            timeHeight = clockInfo18.getTimeHeight();
                        }
                        clockInfo10.setTimeHeight(timeHeight);
                        if (z) {
                            Intrinsics.checkNotNull(defaultAllInOneParams);
                            clockTopRatio = defaultAllInOneParams.getClockTopRatio();
                        } else {
                            clockTopRatio = clockInfo18.getClockTopRatio();
                        }
                        clockInfo10.setClockTopRatio(clockTopRatio);
                        if (z) {
                            Intrinsics.checkNotNull(defaultAllInOneParams);
                            clockBottomRatio = defaultAllInOneParams.getClockBottomRatio();
                        } else {
                            clockBottomRatio = clockInfo18.getClockBottomRatio();
                        }
                        clockInfo10.setClockBottomRatio(clockBottomRatio);
                        if (z) {
                            Intrinsics.checkNotNull(defaultAllInOneParams);
                            clockDepthType = AllInOneClockStyle.toRegionInt(defaultAllInOneParams.getClockDepthType());
                        } else {
                            clockDepthType = clockInfo18.getClockDepthType();
                        }
                        clockInfo10.setClockDepthType(clockDepthType);
                        if (z) {
                            Intrinsics.checkNotNull(defaultAllInOneParams);
                            zIsDoubleRow = defaultAllInOneParams.isDoubleRow();
                        } else {
                            zIsDoubleRow = clockInfo18.isDoubleRow();
                        }
                        clockInfo10.setDoubleRow(zIsDoubleRow);
                        if (z) {
                            Intrinsics.checkNotNull(defaultAllInOneParams);
                            zIsColonShow = defaultAllInOneParams.isColonShow();
                        } else {
                            zIsColonShow = clockInfo18.isColonShow();
                        }
                        clockInfo10.setColonShow(zIsColonShow);
                        if (Intrinsics.areEqual(templateId, "all_in_one")) {
                            clockInfo10.setGlassTransparency(fValueOf != null ? fValueOf.floatValue() : clockInfo18.getGlassTransparency());
                            if (lastStyleClassicSignature == null && (lastStyleClassicSignature = clockInfo18.getClassicSignature()) == null) {
                                lastStyleClassicSignature = "";
                            }
                            clockInfo10.setClassicSignature(lastStyleClassicSignature);
                        } else {
                            clockInfo10.setGlassTransparency(clockInfo18.getGlassTransparency());
                            if (Intrinsics.areEqual(templateId, "classic")) {
                                if (lastStyleClassicSignature == null && (lastStyleClassicSignature = clockInfo18.getClassicSignature()) == null) {
                                    lastStyleClassicSignature = "";
                                }
                                clockInfo10.setClassicSignature(lastStyleClassicSignature);
                            } else {
                                String classicSignature2 = clockInfo18.getClassicSignature();
                                clockInfo10.setClassicSignature(classicSignature2 != null ? classicSignature2 : "");
                            }
                        }
                        clockInfo10.setFontStyle(clockInfo18.getFontStyle());
                        clockInfo10.setHollowStyle(clockInfo18.getHollowStyle());
                        clockInfo10.setClockWeight(clockInfo18.getClockWeight());
                        int classicLine1 = clockInfo10.getClassicLine1();
                        clockInfo10.setClassicLine1(clockInfo18.getClassicLine1());
                        clockInfo10.setClassicLine2(clockInfo18.getClassicLine2());
                        clockInfo10.setClassicLine3(clockInfo18.getClassicLine3());
                        clockInfo10.setClassicLine4(clockInfo18.getClassicLine4());
                        clockInfo10.setClassicLine5(clockInfo18.getClassicLine5());
                        Log.d("Keyguard-Editor-BaseTemplateView", "updateCurrentTemplateConfig: AllInOne classicLine1: " + classicLine1 + " → " + clockInfo10.getClassicLine1() + " (src=" + clockInfo18.getClassicLine1() + "), currentTemplateId=" + templateId + ", desStyle=" + clockInfo18.getStyle());
                    }
                }
                break;
            case 1286956372:
                if (templateId2.equals("pad_exclusive_a")) {
                    if (templateConfig != null && (clockInfo13 = templateConfig.getClockInfo()) != null) {
                        clockInfo13.setSignatureLine1(templateConfig2.getClockInfo().getSignatureLine1());
                    }
                    if (templateConfig != null && (clockInfo12 = templateConfig.getClockInfo()) != null) {
                        clockInfo12.setSignatureLine2(templateConfig2.getClockInfo().getSignatureLine2());
                    }
                    if (templateConfig != null && (clockInfo11 = templateConfig.getClockInfo()) != null) {
                        clockInfo11.setSignatureLine3(templateConfig2.getClockInfo().getSignatureLine3());
                    }
                }
                break;
            case 1286956373:
                if (templateId2.equals("pad_exclusive_b")) {
                    if (templateConfig != null) {
                        clockInfo13.setSignatureLine1(templateConfig2.getClockInfo().getSignatureLine1());
                    }
                    if (templateConfig != null) {
                        clockInfo12.setSignatureLine2(templateConfig2.getClockInfo().getSignatureLine2());
                    }
                    if (templateConfig != null) {
                        clockInfo11.setSignatureLine3(templateConfig2.getClockInfo().getSignatureLine3());
                    }
                }
                break;
            case 1286956374:
                if (templateId2.equals("pad_exclusive_c")) {
                    if (templateConfig != null) {
                        clockInfo13.setSignatureLine1(templateConfig2.getClockInfo().getSignatureLine1());
                    }
                    if (templateConfig != null) {
                        clockInfo12.setSignatureLine2(templateConfig2.getClockInfo().getSignatureLine2());
                    }
                    if (templateConfig != null) {
                        clockInfo11.setSignatureLine3(templateConfig2.getClockInfo().getSignatureLine3());
                    }
                }
                break;
        }
        syncClockEffectConfig(templateConfig, templateConfig2, baseClockView);
        if (templateConfig != null) {
            templateConfig.setTemplateName(templateConfig2.getTemplateName());
        }
        if (templateConfig != null && (clockInfo2 = templateConfig.getClockInfo()) != null) {
            clockInfo2.setTemplateId(templateId2);
        }
        if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
            return;
        }
        clockInfo.setStyle(templateConfig2.getClockInfo().getStyle());
    }

    public final void updateDisplayClockEffect() {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) {
            return;
        }
        int clockEffect = clockInfo.getClockEffect();
        if (EditFragmentViewModel.Companion.shouldDisableGlassEffect(clockEffect, this.templateConfig)) {
            Log.d("Keyguard-Editor-BaseTemplateView", "updateDisplayClockEffect, clockEffect=" + clockEffect + ", fallback to obscure");
            clockEffect = 1;
        }
        this.displayClockEffect = clockEffect;
    }

    public final void updateDownloadStatus(int i) {
        if (i != 1) {
            if (i == 2) {
                updateVideoOrImageStatus(2);
                return;
            } else {
                if (i != 3) {
                    return;
                }
                updateVideoOrImageStatus(3);
                return;
            }
        }
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            String str = this.trackId;
            if (str == null) {
                str = "";
            }
            trackHelper.trackDownloadDepthVideo(context, commonConfig, str);
        }
    }

    public final void updateEditorTime() {
        RealtimeStyleEditor realtimeEditor;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView == null || (realtimeEditor = baseClockView.getRealtimeEditor()) == null) {
            return;
        }
        realtimeEditor.onTimeUpdate();
    }

    public final void updateFilterButtonVisible() {
        String templateId;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null || (templateId = clockInfo.getTemplateId()) == null) {
            templateId = "";
        }
        boolean zAreEqual = Intrinsics.areEqual(templateId, "doodle");
        KgVisualCheckBox kgVisualCheckBox = this.filterBtn;
        if (kgVisualCheckBox != null) {
            kgVisualCheckBox.setVisibility(!zAreEqual ? 0 : 8);
        }
        handleDoodleTemplate(zAreEqual, templateId);
        post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                BaseTemplateView.updateFilterButtonVisible$lambda$131(this.f$0);
            }
        });
        this.previousTemplateId = templateId;
    }

    public void updateHierarchyButton(int i) {
    }

    public final void updateHierarchyStatus(int i) {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel != null) {
            EditFragmentViewModel.updateHierarchyStatus$default(viewModel, i, false, false, false, 14, null);
        }
    }

    public final void updateLoadingView() {
        EditorLoadingView editorLoadingView = this.loadingView;
        if (editorLoadingView != null) {
            editorLoadingView.setPivotY(getPivotY());
            editorLoadingView.setPivotX(getPivotX());
            float f = 1;
            editorLoadingView.setScaleX(f / getScaleX());
            editorLoadingView.setScaleY(f / getScaleY());
        }
    }

    public final void updatePivot() {
        setPivotX((getWidth() * 1.0f) / 2.0f);
        setPivotY(getResources().getInteger(R.integer.kg_combine_edit_template_offset_y) + (getHeight() / 2.0f));
    }

    public void updateVideoOrImageStatus(int i) {
        this.state = i;
        if (i != 2) {
            if (i != 3) {
                return;
            }
            BaseClockView baseClockView = (BaseClockView) getClockLayer();
            if (baseClockView != null) {
                baseClockView.onEdited(38, null);
            }
            onEdited(38, null);
            return;
        }
        BaseClockView baseClockView2 = (BaseClockView) getClockLayer();
        if (baseClockView2 != null) {
            baseClockView2.onEdited(37, null);
        }
        onEdited(37, null);
        this.dynamicSwitchCallback.removeDownloadButton();
        this.dynamicSwitchCallback.showDynamicSwitchButton();
    }

    public final void updateVisualCheckBoxChecked(@Nullable View view, boolean z, boolean z2) {
        CoroutineScope coroutineScope = EditFragmentViewModel.Companion.getCoroutineScope(this.editFragment);
        if (coroutineScope != null) {
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C01091(z2, view, z, null), 3, null);
        }
    }

    public final void updateWallPaper(@Nullable WallpaperInfo wallpaperInfo) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        Bitmap currentWallpaper;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo3;
        TemplateConfig lockscreenInfo4;
        if (wallpaperInfo != null) {
            CommonConfig commonConfig = this.templateConfig;
            if (commonConfig != null && (lockscreenInfo4 = commonConfig.getLockscreenInfo()) != null) {
                lockscreenInfo4.setWallpaperInfo(wallpaperInfo);
            }
            IWallpaperLayer wallpaperLayer = getWallpaperLayer();
            if (wallpaperLayer == null || !(wallpaperLayer instanceof CombinedWallpaperView)) {
                return;
            }
            CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
            CommonConfig commonConfig2 = this.templateConfig;
            Bitmap currentWallpaper2 = null;
            WallpaperInfo wallpaperInfo4 = (commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null) ? null : lockscreenInfo3.getWallpaperInfo();
            Intrinsics.checkNotNull(wallpaperInfo4);
            CommonConfig commonConfig3 = this.templateConfig;
            if (commonConfig3 != null && (lockscreenInfo2 = commonConfig3.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo2.getWallpaperInfo()) != null) {
                currentWallpaper2 = wallpaperInfo3.getCurrentWallpaper();
            }
            CombinedWallpaperView.switchWallpaperView$default(combinedWallpaperView, wallpaperInfo4, currentWallpaper2, null, false, false, null, this.editable, 44, null);
            CommonConfig commonConfig4 = this.templateConfig;
            if (commonConfig4 == null || (lockscreenInfo = commonConfig4.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) == null || (currentWallpaper = wallpaperInfo2.getCurrentWallpaper()) == null) {
                return;
            }
            combinedWallpaperView.setBackgroundDrawable(new CenterCropDrawable(currentWallpaper));
        }
    }

    public final void waitTemplateInit() {
        CoroutineScope coroutineScope = EditFragmentViewModel.Companion.getCoroutineScope(this.editFragment);
        if (coroutineScope != null) {
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getDefault(), null, new C01101(null), 2, null);
        }
    }
}

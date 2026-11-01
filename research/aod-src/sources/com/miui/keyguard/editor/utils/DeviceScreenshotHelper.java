package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.BitmapTempStore;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt__JobKt;

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
/* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class DeviceScreenshotHelper {
    public static final int SCREENSHOT_SKIP = 0;
    public static WeakReference currentHomeTemplateWeak;
    public static boolean isFirstScreenshotCacheInvalid;
    public static volatile boolean isFirstScreenshotFinished;
    public static final DeviceScreenshotHelper INSTANCE = new DeviceScreenshotHelper();
    public static final String HIDE_SMLL_TAG = "hide_small";
    public static final String HIDE_LARGE_PORT_TAG = "hide_large_port";
    public static final String HIDE_LARGE_LAND_TAG = "hide_large_land";
    public static final int SCREENSHOT_CURRENT = 1;
    public static final int SCREENSHOT_SMALL = 2;
    public static final int SCREENSHOT_LARGE_PORT = 3;
    public static final int SCREENSHOT_LARGE_LAND = 4;

    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    public static final class RemoveTemplateViewRunnable implements Runnable {
        public final ViewGroup container;
        public final BaseTemplateView templateView;

        public RemoveTemplateViewRunnable(ViewGroup container, BaseTemplateView baseTemplateView) {
            Intrinsics.checkNotNullParameter(container, "container");
            this.container = container;
            this.templateView = baseTemplateView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.container.removeView(this.templateView);
        }
    }

    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    public static final class ScreenshotContext extends ContextWrapper {
        public boolean isLargeScreen;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ScreenshotContext(Context base) {
            super(base);
            Intrinsics.checkNotNullParameter(base, "base");
        }

        public final boolean isLargeScreen() {
            return this.isLargeScreen;
        }

        public final void setLargeScreen(boolean z) {
            this.isLargeScreen = z;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$screenshot$1, reason: invalid class name */
    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$screenshot$1", f = "DeviceScreenshotHelper.kt", l = {134}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function1 $captureFailedCallback;
        public final /* synthetic */ ViewGroup $containerView;
        public final /* synthetic */ Context $context;
        public final /* synthetic */ CommonConfig $copied;
        public final /* synthetic */ Function0 $hideScreenshotCallback;
        public final /* synthetic */ BaseTemplateView $homeTemplateView;
        public final /* synthetic */ BaseTemplateView $lockTemplateView;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CommonConfig commonConfig, Context context, ViewGroup viewGroup, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, Function0 function0, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$copied = commonConfig;
            this.$context = context;
            this.$containerView = viewGroup;
            this.$lockTemplateView = baseTemplateView;
            this.$homeTemplateView = baseTemplateView2;
            this.$hideScreenshotCallback = function0;
            this.$captureFailedCallback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$copied, this.$context, this.$containerView, this.$lockTemplateView, this.$homeTemplateView, this.$hideScreenshotCallback, this.$captureFailedCallback, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Deferred deferred;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Deferred deferredAsync$default = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new DeviceScreenshotHelper$screenshot$1$asyncJob$1(null), 3, null);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                DeviceScreenshotHelper$screenshot$1$bitmap$1 deviceScreenshotHelper$screenshot$1$bitmap$1 = new DeviceScreenshotHelper$screenshot$1$bitmap$1(this.$context, this.$copied, null);
                this.L$0 = deferredAsync$default;
                this.label = 1;
                Object objWithContext = BuildersKt.withContext(coroutineDispatcher, deviceScreenshotHelper$screenshot$1$bitmap$1, this);
                if (objWithContext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                deferred = deferredAsync$default;
                obj = objWithContext;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                deferred = (Deferred) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            Bitmap bitmap = (Bitmap) obj;
            Job.DefaultImpls.cancel$default(deferred, null, 1, null);
            WallpaperInfo wallpaperInfo = this.$copied.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo != null) {
                wallpaperInfo.setSubjectBitmap(bitmap);
            }
            DeviceScreenshotHelper.INSTANCE.takeCaptureForMultiScreenMode(this.$context, this.$containerView, this.$lockTemplateView, this.$homeTemplateView, this.$copied, this.$hideScreenshotCallback, this.$captureFailedCallback);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeCaptureForMultiScreenMode$2, reason: invalid class name */
    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeCaptureForMultiScreenMode$2", f = "DeviceScreenshotHelper.kt", l = {186}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function0 $captureCompletedCallback;
        public final /* synthetic */ ViewGroup $containerView;
        public final /* synthetic */ Context $context;
        public final /* synthetic */ BaseTemplateView $lockTemplateView;
        public final /* synthetic */ CommonConfig $templateConfig;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeCaptureForMultiScreenMode$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeCaptureForMultiScreenMode$2$1", f = "DeviceScreenshotHelper.kt", l = {217}, m = "invokeSuspend")
        public static final class AnonymousClass1 extends SuspendLambda implements Function3<Screenshots, Integer, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Function0 $captureCompletedCallback;
            public final /* synthetic */ Context $context;
            public final /* synthetic */ Job $daemon;
            public final /* synthetic */ Ref.IntRef $size;
            public final /* synthetic */ int $total;
            public /* synthetic */ int I$0;
            public /* synthetic */ Object L$0;
            public int label;

            /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeCaptureForMultiScreenMode$2$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
            @Metadata
            @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeCaptureForMultiScreenMode$2$1$1", f = "DeviceScreenshotHelper.kt", l = {}, m = "invokeSuspend")
            public static final class C00331 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                public final /* synthetic */ Context $context;
                public final /* synthetic */ ScreenshotSource $screenshotSource;
                public int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00331(Context context, ScreenshotSource screenshotSource, Continuation continuation) {
                    super(2, continuation);
                    this.$context = context;
                    this.$screenshotSource = screenshotSource;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00331(this.$context, this.$screenshotSource, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((C00331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    TemplateApi.Companion.getInstance(this.$context).saveCurrentTemplateScreenshot(this.$screenshotSource);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Ref.IntRef intRef, int i, Context context, Job job, Function0 function0, Continuation continuation) {
                super(3, continuation);
                this.$size = intRef;
                this.$total = i;
                this.$context = context;
                this.$daemon = job;
                this.$captureCompletedCallback = function0;
            }

            public final Object invoke(Screenshots screenshots, int i, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$size, this.$total, this.$context, this.$daemon, this.$captureCompletedCallback, continuation);
                anonymousClass1.L$0 = screenshots;
                anonymousClass1.I$0 = i;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return invoke((Screenshots) obj, ((Number) obj2).intValue(), (Continuation) obj3);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                int i;
                ScreenshotSource screenshotSource;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i2 = this.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Screenshots screenshots = (Screenshots) this.L$0;
                    int i3 = this.I$0;
                    Ref.IntRef intRef = this.$size;
                    int i4 = intRef.element + 1;
                    intRef.element = i4;
                    Log.i("Keyguard-Editor-DeviceScreenshotHelper", "takeCaptureForMultiScreenMode -> screenshots=" + screenshots + " bitmapId=" + i3 + " total=" + this.$total + " size=" + i4);
                    ScreenshotSource screenshotSource2 = new ScreenshotSource(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 262143, null);
                    DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                    if (i3 == deviceScreenshotHelper.getSCREENSHOT_CURRENT()) {
                        Context context = this.$context;
                        ScreenshotSourceKt.setCurrentScreenshot(screenshotSource2, context, screenshots.getCurrentScreenshot(context));
                        Context context2 = this.$context;
                        ScreenshotSourceKt.setCurrentWallpaperScreenshot(screenshotSource2, context2, screenshots.getCurrentWallpaperScreenshot(context2));
                    } else if (i3 == deviceScreenshotHelper.getSCREENSHOT_SMALL()) {
                        screenshotSource2.setSmallScreenshot(screenshots.getSmall());
                        screenshotSource2.setSmallWallpaperScreenshot(screenshots.getSmallWallpaper());
                    } else if (i3 == deviceScreenshotHelper.getSCREENSHOT_LARGE_PORT()) {
                        screenshotSource2.setLargePortraitScreenshot(screenshots.getLargePortrait());
                        screenshotSource2.setLargePortWallpaperScreenshot(screenshots.getLargePortWallpaper());
                    } else if (i3 == deviceScreenshotHelper.getSCREENSHOT_LARGE_LAND()) {
                        screenshotSource2.setLargeLandscapeScreenshot(screenshots.getLargeLandscape());
                        screenshotSource2.setLargeLandWallpaperScreenshot(screenshots.getLargeLandWallpaper());
                    }
                    CoroutineDispatcher io2 = Dispatchers.getIO();
                    C00331 c00331 = new C00331(this.$context, screenshotSource2, null);
                    this.L$0 = screenshotSource2;
                    this.I$0 = i3;
                    this.label = 1;
                    if (BuildersKt.withContext(io2, c00331, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    i = i3;
                    screenshotSource = screenshotSource2;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    i = this.I$0;
                    screenshotSource = (ScreenshotSource) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                DeviceScreenshotHelper deviceScreenshotHelper2 = DeviceScreenshotHelper.INSTANCE;
                if (i == deviceScreenshotHelper2.getSCREENSHOT_CURRENT()) {
                    deviceScreenshotHelper2.setFirstScreenshotFinished(true);
                }
                Log.d("Keyguard-Editor-DeviceScreenshotHelper", "takeCaptureForMultiScreenMode: save bitmapId=" + i + " finish");
                ScreenshotSourceKt.releaseAllScreenshot(screenshotSource);
                deviceScreenshotHelper2.setCurrentHomeTemplateWeak(null);
                if (this.$size.element >= this.$total) {
                    Job.DefaultImpls.cancel$default(this.$daemon, null, 1, null);
                    Log.i("Keyguard-Editor-DeviceScreenshotHelper", "takeCaptureForMultiScreenMode: complete");
                    this.$captureCompletedCallback.invoke();
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Context context, ViewGroup viewGroup, BaseTemplateView baseTemplateView, CommonConfig commonConfig, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$containerView = viewGroup;
            this.$lockTemplateView = baseTemplateView;
            this.$templateConfig = commonConfig;
            this.$captureCompletedCallback = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$context, this.$containerView, this.$lockTemplateView, this.$templateConfig, this.$captureCompletedCallback, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, null, null, new DeviceScreenshotHelper$takeCaptureForMultiScreenMode$2$daemon$1(null), 3, null);
                DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                int screenshotTemplateCount = deviceScreenshotHelper.getScreenshotTemplateCount();
                Ref.IntRef intRef = new Ref.IntRef();
                Context context = this.$context;
                ViewGroup viewGroup = this.$containerView;
                if (viewGroup == null) {
                    viewGroup = this.$lockTemplateView;
                }
                CommonConfig commonConfig = this.$templateConfig;
                BaseTemplateView baseTemplateView = this.$lockTemplateView;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(intRef, screenshotTemplateCount, context, jobLaunch$default, this.$captureCompletedCallback, null);
                this.label = 1;
                if (deviceScreenshotHelper.takeMultiScreenshotsForTemplateView(context, viewGroup, commonConfig, baseTemplateView, true, anonymousClass1, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper", f = "DeviceScreenshotHelper.kt", l = {251, 269, 292, 313, 314, 315, 333, 334, 335, 353, 354, 355}, m = "takeMultiScreenshotsForTemplateView")
    public static final class C01411 extends ContinuationImpl {
        public int I$0;
        public int I$1;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public Object L$5;
        public Object L$6;
        public boolean Z$0;
        public int label;
        public /* synthetic */ Object result;

        public C01411(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DeviceScreenshotHelper.this.takeMultiScreenshotsForTemplateView(null, null, null, null, false, null, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$2", f = "DeviceScreenshotHelper.kt", l = {261}, m = "invokeSuspend")
    public static final class C01422 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function3 $onScreenshotsCaptured;
        public final /* synthetic */ Screenshots $screenshots;
        public /* synthetic */ int I$0;
        public /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01422(Screenshots screenshots, Function3 function3, Continuation continuation) {
            super(4, continuation);
            this.$screenshots = screenshots;
            this.$onScreenshotsCaptured = function3;
        }

        public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
            C01422 c01422 = new C01422(this.$screenshots, this.$onScreenshotsCaptured, continuation);
            c01422.L$0 = bitmap;
            c01422.I$0 = i;
            c01422.L$1 = bitmap2;
            return c01422.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.jvm.functions.Function4
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            return invoke((Bitmap) obj, ((Number) obj2).intValue(), (Bitmap) obj3, (Continuation) obj4);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap bitmap = (Bitmap) this.L$0;
                int i2 = this.I$0;
                Bitmap bitmap2 = (Bitmap) this.L$1;
                Log.d("Keyguard-Editor-DeviceScreenshotHelper", "currentTemplateView phone screenshot finish: i=" + i2);
                DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                this.$screenshots.setSmallWallpaper(deviceScreenshotHelper.getCurrentHomeWallpaperScreenshot(bitmap2));
                this.$screenshots.setSmall(bitmap);
                Function3 function3 = this.$onScreenshotsCaptured;
                Screenshots screenshots = this.$screenshots;
                Integer numBoxInt = Boxing.boxInt(deviceScreenshotHelper.getSCREENSHOT_CURRENT());
                this.L$0 = null;
                this.label = 1;
                if (function3.invoke(screenshots, numBoxInt, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$3, reason: invalid class name */
    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$3", f = "DeviceScreenshotHelper.kt", l = {282}, m = "invokeSuspend")
    public static final class AnonymousClass3 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ BaseTemplateView $currentTemplateView;
        public final /* synthetic */ Function3 $onScreenshotsCaptured;
        public final /* synthetic */ Screenshots $screenshots;
        public /* synthetic */ int I$0;
        public /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Screenshots screenshots, BaseTemplateView baseTemplateView, Context context, Function3 function3, Continuation continuation) {
            super(4, continuation);
            this.$screenshots = screenshots;
            this.$currentTemplateView = baseTemplateView;
            this.$context = context;
            this.$onScreenshotsCaptured = function3;
        }

        public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$screenshots, this.$currentTemplateView, this.$context, this.$onScreenshotsCaptured, continuation);
            anonymousClass3.L$0 = bitmap;
            anonymousClass3.I$0 = i;
            anonymousClass3.L$1 = bitmap2;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.jvm.functions.Function4
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            return invoke((Bitmap) obj, ((Number) obj2).intValue(), (Bitmap) obj3, (Continuation) obj4);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap bitmap = (Bitmap) this.L$0;
                int i2 = this.I$0;
                Bitmap bitmap2 = (Bitmap) this.L$1;
                Log.d("Keyguard-Editor-DeviceScreenshotHelper", "currentTemplateView large screenshot finish: i=" + i2);
                DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                Bitmap currentHomeWallpaperScreenshot = deviceScreenshotHelper.getCurrentHomeWallpaperScreenshot(bitmap2);
                Screenshots screenshots = this.$screenshots;
                Context context = this.$currentTemplateView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                screenshots.setCurrentWallpaperScreenshot(context, currentHomeWallpaperScreenshot);
                this.$screenshots.setCurrentScreenshot(this.$context, bitmap);
                Function3 function3 = this.$onScreenshotsCaptured;
                Screenshots screenshots2 = this.$screenshots;
                Integer numBoxInt = Boxing.boxInt(deviceScreenshotHelper.getSCREENSHOT_CURRENT());
                this.L$0 = null;
                this.label = 1;
                if (function3.invoke(screenshots2, numBoxInt, this) == coroutine_suspended) {
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

    public static final PresetTemplateConfig createScreenshotPreview$lambda$18(Context context, PresetTemplateConfig presetTemplateConfig, int i, float f) {
        try {
            return DataUtil.INSTANCE.getPresetTemplateForScreenshot(context, presetTemplateConfig, i, (int) f);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-DeviceScreenshotHelper", "createScreenshotPreview#refreshScreenshotDataForPreset", e);
            return null;
        }
    }

    public static final void createScreenshotPreview$lambda$20(boolean z, int i, final ImageView imageView, final ScreenshotPreviewLoaded screenshotPreviewLoaded, PresetTemplateConfig presetTemplateConfig) {
        Log.i("Keyguard-Editor-DeviceScreenshotHelper", "isLargeScreen = " + z + ", orientation = " + i + ", presetTemplate = " + presetTemplateConfig + " -> data loaded. update view and callback.");
        INSTANCE.composePresetTemplateImage(imageView, presetTemplateConfig);
        imageView.post(new Runnable() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                screenshotPreviewLoaded.onScreenshotPreviewLoaded(imageView);
            }
        });
    }

    public static final Unit screenshot$lambda$2(Function0 function0, String str, ViewGroup viewGroup, BaseTemplateView baseTemplateView, final CommonConfig commonConfig) {
        function0.invoke();
        if (Intrinsics.areEqual(str, "wallpaper_for_screenshot_history")) {
            BitmapTempStore.Companion.getInstance().removeBitmap("wallpaper_for_screenshot");
        } else {
            BitmapTempStore.Companion.getInstance().removeBitmap("wallpaper_for_screenshot_history");
        }
        if (viewGroup == null) {
            viewGroup = baseTemplateView;
        }
        viewGroup.post(new Runnable() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DeviceScreenshotHelper.screenshot$lambda$2$lambda$1(commonConfig);
            }
        });
        return Unit.INSTANCE;
    }

    public static final void screenshot$lambda$2$lambda$1(CommonConfig commonConfig) {
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo != null) {
            BitmapUtil.INSTANCE.recycleBitmap(wallpaperInfo.getSubjectBitmap(), "copy subject");
            wallpaperInfo.setCurrentWallpaper(null);
            wallpaperInfo.setSubjectBitmap(null);
        }
    }

    public static final Unit takeMultiScreenshotsForPreview$lambda$16(Ref.IntRef intRef, Ref.ObjectRef objectRef, Function1 function1, Screenshots screenshots) {
        int i = intRef.element + 1;
        intRef.element = i;
        ScreenshotPreviews screenshotPreviews = (ScreenshotPreviews) objectRef.element;
        Log.i("Keyguard-Editor-DeviceScreenshotHelper", "takeMultiScreenshots1 -> size: " + i + ", total: " + (screenshotPreviews != null ? Integer.valueOf(screenshotPreviews.getTotal()) : null));
        int i2 = intRef.element;
        ScreenshotPreviews screenshotPreviews2 = (ScreenshotPreviews) objectRef.element;
        if (i2 >= (screenshotPreviews2 != null ? screenshotPreviews2.getTotal() : 0)) {
            function1.invoke(screenshots);
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Object takeMultiScreenshotsForTemplateView$default(DeviceScreenshotHelper deviceScreenshotHelper, Context context, ViewGroup viewGroup, CommonConfig commonConfig, BaseTemplateView baseTemplateView, boolean z, Function3 function3, Continuation continuation, int i, Object obj) {
        if ((i & 16) != 0) {
            z = false;
        }
        return deviceScreenshotHelper.takeMultiScreenshotsForTemplateView(context, viewGroup, commonConfig, baseTemplateView, z, function3, continuation);
    }

    public static /* synthetic */ Object takeScreenshotSync$default(DeviceScreenshotHelper deviceScreenshotHelper, BaseTemplateView baseTemplateView, Screenshots screenshots, boolean z, boolean z2, Function4 function4, Continuation continuation, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        return deviceScreenshotHelper.takeScreenshotSync(baseTemplateView, screenshots, z, z2, function4, continuation);
    }

    public final void composePresetTemplateImage(ImageView imageView, PresetTemplateConfig presetTemplateConfig) {
        if (presetTemplateConfig != null) {
            imageView.setImageBitmap(presetTemplateConfig.getPreviewImage());
            imageView.setForeground(presetTemplateConfig.getForeground());
        }
    }

    public final Context createConfigurationContext(Context context, Point point, boolean z, int i) {
        Configuration configuration = new Configuration();
        configuration.orientation = i;
        configuration.screenLayout = z ? 3 : 1;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isJ18()) {
            setJ18Configuration(z, point, configuration);
        } else if (deviceUtil.isPad()) {
            setPadConfiguration(point, configuration);
        } else {
            if (!deviceUtil.isFold()) {
                Log.w("Keyguard-Editor-DeviceScreenshotHelper", "current device has only one display");
                return null;
            }
            setFoldConfiguration(point, z, configuration);
        }
        return context.createConfigurationContext(configuration);
    }

    public final BaseTemplateView createCurrentScreenTemplate(Context context, CommonConfig templateConfig) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "createCurrentScreenTemplate: screenWidth=" + i + " screenHeight=" + i2);
        BaseTemplateView baseTemplateViewCreateScaledPreviewTemplateView$default = TemplateViewFactory.createScaledPreviewTemplateView$default(TemplateViewFactory.INSTANCE, context, templateConfig.getLockscreenInfo().getClockInfo().getTemplateId(), new FrameLayout.LayoutParams(i, i2), 1.0E-4f, 1.0E-4f, false, 32, null);
        if (baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
            baseTemplateViewCreateScaledPreviewTemplateView$default.loadTemplate(templateConfig);
        }
        return baseTemplateViewCreateScaledPreviewTemplateView$default;
    }

    public final ImageView createScreenshotPreview(Context context, final PresetTemplateConfig presetTemplateConfig, final boolean z, final int i, final ScreenshotPreviewLoaded screenshotPreviewLoaded) {
        final Context contextCreateConfigurationContext;
        Point pointScreenPixelSize = screenPixelSize(context, z, i);
        if (pointScreenPixelSize == null || (contextCreateConfigurationContext = createConfigurationContext(context, pointScreenPixelSize, z, i)) == null) {
            return null;
        }
        final int dimensionPixelSize = contextCreateConfigurationContext.getResources().getDimensionPixelSize(R.dimen.kg_main_item_container_width);
        final float f = dimensionPixelSize / ((pointScreenPixelSize.x * 1.0f) / pointScreenPixelSize.y);
        final ImageView imageView = new ImageView(contextCreateConfigurationContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(dimensionPixelSize, (int) f));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setScaleX(1.0E-4f);
        imageView.setScaleY(1.0E-4f);
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return DeviceScreenshotHelper.createScreenshotPreview$lambda$18(contextCreateConfigurationContext, presetTemplateConfig, dimensionPixelSize, f);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DeviceScreenshotHelper.createScreenshotPreview$lambda$20(z, i, imageView, screenshotPreviewLoaded, (PresetTemplateConfig) obj);
            }
        });
        return imageView;
    }

    public final ScreenshotPreviews createScreenshotPreviews(Context context, PresetTemplateConfig presetTemplateConfig, ScreenshotPreviewLoaded screenshotPreviewLoaded) {
        ScreenshotPreviews screenshotPreviews = new ScreenshotPreviews(null, null, null, 0, 15, null);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isJ18()) {
            setJ18ScreenshotPreviews(context, presetTemplateConfig, screenshotPreviews, screenshotPreviewLoaded);
            return screenshotPreviews;
        }
        if (deviceUtil.isPad()) {
            setPadScreenshotPreviews(context, presetTemplateConfig, screenshotPreviews, screenshotPreviewLoaded);
            return screenshotPreviews;
        }
        if (deviceUtil.isFold()) {
            setFoldScreenshotPreviews(context, presetTemplateConfig, screenshotPreviews, screenshotPreviewLoaded);
        }
        return screenshotPreviews;
    }

    public final BaseTemplateView createScreenshotTemplateView(Context context, CommonConfig commonConfig, boolean z, boolean z2, int i, HideScreenshotCallback hideScreenshotCallback) {
        Context contextCreateConfigurationContext;
        Point pointScreenPixelSize = screenPixelSize(context, z2, i);
        if (pointScreenPixelSize == null || (contextCreateConfigurationContext = createConfigurationContext(context, pointScreenPixelSize, z2, i)) == null) {
            return null;
        }
        ScreenshotContext screenshotContext = new ScreenshotContext(contextCreateConfigurationContext);
        screenshotContext.setLargeScreen(z2);
        BaseTemplateView baseTemplateViewCreateScaledPreviewTemplateView$default = TemplateViewFactory.createScaledPreviewTemplateView$default(TemplateViewFactory.INSTANCE, screenshotContext, commonConfig.getLockscreenInfo().getClockInfo().getTemplateId(), new FrameLayout.LayoutParams(pointScreenPixelSize.x, pointScreenPixelSize.y), 1.0E-4f, 1.0E-4f, false, 32, null);
        if (z && baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
            baseTemplateViewCreateScaledPreviewTemplateView$default.setTemplateSource(-1L);
        }
        if (baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
            baseTemplateViewCreateScaledPreviewTemplateView$default.setCombineAnimListener(hideScreenshotCallback);
        }
        if (baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
            baseTemplateViewCreateScaledPreviewTemplateView$default.loadTemplate(commonConfig);
        }
        return baseTemplateViewCreateScaledPreviewTemplateView$default;
    }

    public final void createScreenshotTemplateViews(Context context, CommonConfig commonConfig, boolean z, ScreenshotTemplateViews screenshotTemplateViews) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isJ18()) {
            setJ18ScreenshotTemplateViews(context, commonConfig, z, screenshotTemplateViews);
        } else if (deviceUtil.isPad()) {
            setPadScreenshotTemplateViews(context, commonConfig, z, screenshotTemplateViews);
        } else if (deviceUtil.isFold()) {
            setFoldScreenshotTemplateViews(context, commonConfig, z, screenshotTemplateViews);
        }
    }

    public final WeakReference getCurrentHomeTemplateWeak() {
        return currentHomeTemplateWeak;
    }

    public final Bitmap getCurrentHomeWallpaperScreenshot(Bitmap bitmap) {
        BaseTemplateView baseTemplateView;
        IWallpaperLayer wallpaperLayer;
        Bitmap bitmapCreateBitmap$default;
        WeakReference weakReference = currentHomeTemplateWeak;
        if (weakReference != null && (baseTemplateView = (BaseTemplateView) weakReference.get()) != null && (wallpaperLayer = baseTemplateView.getWallpaperLayer()) != null) {
            if (bitmap == null) {
                BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
                View viewWallpaperLayerView = wallpaperLayer.wallpaperLayerView();
                Intrinsics.checkNotNull(viewWallpaperLayerView);
                int width = viewWallpaperLayerView.getWidth();
                View viewWallpaperLayerView2 = wallpaperLayer.wallpaperLayerView();
                Intrinsics.checkNotNull(viewWallpaperLayerView2);
                bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(bitmapCreator, width, viewWallpaperLayerView2.getHeight(), Bitmap.Config.ARGB_8888, WideColorGamutManager.INSTANCE.isWideColorBitmap(bitmap), false, 16, null);
            } else {
                bitmapCreateBitmap$default = bitmap;
            }
            wallpaperLayer.getWallpaperBitmap(bitmapCreateBitmap$default);
            if (bitmapCreateBitmap$default != null) {
                return bitmapCreateBitmap$default;
            }
        }
        return bitmap;
    }

    public final int getSCREENSHOT_CURRENT() {
        return SCREENSHOT_CURRENT;
    }

    public final int getSCREENSHOT_LARGE_LAND() {
        return SCREENSHOT_LARGE_LAND;
    }

    public final int getSCREENSHOT_LARGE_PORT() {
        return SCREENSHOT_LARGE_PORT;
    }

    public final int getSCREENSHOT_SKIP() {
        return SCREENSHOT_SKIP;
    }

    public final int getSCREENSHOT_SMALL() {
        return SCREENSHOT_SMALL;
    }

    public final Point getScreenPixelSize(Context context, boolean z, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        return screenPixelSize(context, z, i);
    }

    public final int getScreenshotTemplateCount() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isJ18() || deviceUtil.isPad()) {
            return 2;
        }
        return deviceUtil.isFold() ? 3 : 1;
    }

    public final boolean isFirstScreenshotCacheInvalid() {
        return isFirstScreenshotCacheInvalid;
    }

    public final boolean isFirstScreenshotFinished() {
        return isFirstScreenshotFinished;
    }

    public final boolean needCopyForScreenshot() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        return deviceUtil.isFold() || deviceUtil.isPad();
    }

    public final void removeTemplateViewSafely(ViewGroup viewGroup, BaseTemplateView baseTemplateView) {
        viewGroup.post(new RemoveTemplateViewRunnable(viewGroup, baseTemplateView));
    }

    public final Point screenPixelSize(Context context, boolean z, int i) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isJ18()) {
            return screenPixelSizeJ18(context, z);
        }
        if (deviceUtil.isPad()) {
            return screenPixelSizePad(context, i);
        }
        if (deviceUtil.isFold()) {
            return screenPixelSizeFold(context, z, i);
        }
        return null;
    }

    public final Point screenPixelSizeFold(Context context, boolean z, int i) {
        Point point = new Point();
        if (z) {
            DeviceUtil.DisplayInfo screenSizeFromDisplays = DeviceUtil.INSTANCE.getScreenSizeFromDisplays(context, DeviceUtil.ScreenType.LARGE);
            point.x = screenSizeFromDisplays.getWidth();
            point.y = screenSizeFromDisplays.getHeight();
        } else {
            DeviceUtil.DisplayInfo screenSizeFromDisplays2 = DeviceUtil.INSTANCE.getScreenSizeFromDisplays(context, DeviceUtil.ScreenType.SMALL);
            point.x = screenSizeFromDisplays2.getWidth();
            point.y = screenSizeFromDisplays2.getHeight();
        }
        setScreenPixelSize(point, i);
        return point;
    }

    public final Point screenPixelSizeJ18(Context context, boolean z) {
        Point point = new Point();
        if (z) {
            point.x = (int) context.getResources().getFloat(R.dimen.kg_miui_j18_inner_width);
            point.y = (int) context.getResources().getFloat(R.dimen.kg_miui_j18_inner_height);
            return point;
        }
        point.x = (int) context.getResources().getFloat(R.dimen.kg_miui_j18_outer_width);
        point.y = (int) context.getResources().getFloat(R.dimen.kg_miui_j18_outer_height);
        return point;
    }

    public final Point screenPixelSizePad(Context context, int i) {
        Point screenSize = DeviceUtil.INSTANCE.getScreenSize(context);
        Point point = new Point(screenSize.x, screenSize.y);
        setScreenPixelSize(point, i);
        return point;
    }

    public final void screenshot(Context context, final ViewGroup viewGroup, final BaseTemplateView lockTemplateView, BaseTemplateView baseTemplateView, CommonConfig templateConfig, final String originScreenshotWallpaperCache, final Function0 captureCompletedCallback, Function1 function1) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Intrinsics.checkNotNullParameter(originScreenshotWallpaperCache, "originScreenshotWallpaperCache");
        Intrinsics.checkNotNullParameter(captureCompletedCallback, "captureCompletedCallback");
        if (!needCopyForScreenshot()) {
            takeCaptureForMultiScreenMode(context, viewGroup, lockTemplateView, baseTemplateView, templateConfig, captureCompletedCallback, function1);
            return;
        }
        final CommonConfig commonConfigDeepCopy = templateConfig.deepCopy();
        WallpaperInfo wallpaperInfo = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo != null) {
            wallpaperInfo.setCurrentLockWallpaperSource(LockWallpaperSource.MIUI_WALLPAPER_MANAGER);
        }
        Bitmap bitmap = BitmapTempStore.Companion.getInstance().getBitmap(originScreenshotWallpaperCache);
        if (bitmap == null) {
            WallpaperInfo wallpaperInfo2 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo2 != null) {
                wallpaperInfo2.setPositionInfo(null);
            }
            takeCaptureForMultiScreenMode(context, viewGroup, lockTemplateView, baseTemplateView, commonConfigDeepCopy, captureCompletedCallback, function1);
            return;
        }
        WallpaperInfo wallpaperInfo3 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo3 != null) {
            wallpaperInfo3.setCurrentWallpaper(bitmap);
        }
        Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return DeviceScreenshotHelper.screenshot$lambda$2(captureCompletedCallback, originScreenshotWallpaperCache, viewGroup, lockTemplateView, commonConfigDeepCopy);
            }
        };
        MagicType magicType = MagicType.INSTANCE;
        WallpaperInfo wallpaperInfo4 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
        if (magicType.isDepth(wallpaperInfo4 != null ? Integer.valueOf(wallpaperInfo4.getMagicType()) : null)) {
            BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), JobKt__JobKt.Job$default(null, 1, null).plus(new DeviceScreenshotHelper$screenshot$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key, commonConfigDeepCopy, context, viewGroup, lockTemplateView, baseTemplateView, function0, function1)), null, new AnonymousClass1(commonConfigDeepCopy, context, viewGroup, lockTemplateView, baseTemplateView, function0, function1, null), 2, null);
        } else {
            takeCaptureForMultiScreenMode(context, viewGroup, lockTemplateView, baseTemplateView, commonConfigDeepCopy, captureCompletedCallback, function1);
        }
    }

    public final void setConfigurationScreenSize(Point point, Configuration configuration) {
        float f = (configuration.densityDpi * 1.0f) / 160;
        configuration.screenWidthDp = (int) (point.x / f);
        configuration.screenHeightDp = (int) (point.y / f);
    }

    public final void setCurrentHomeTemplateWeak(WeakReference weakReference) {
        currentHomeTemplateWeak = weakReference;
    }

    public final void setFirstScreenShotFinished() {
        if (isFirstScreenshotFinished) {
            return;
        }
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "takeCaptureForMultiScreenMode not finish");
        isFirstScreenshotFinished = true;
        isFirstScreenshotCacheInvalid = true;
    }

    public final void setFirstScreenshotCacheInvalid(boolean z) {
        isFirstScreenshotCacheInvalid = z;
    }

    public final void setFirstScreenshotFinished(boolean z) {
        isFirstScreenshotFinished = z;
    }

    public final void setFoldConfiguration(Point point, boolean z, Configuration configuration) {
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        configuration.densityDpi = companion.getApplication().getResources().getConfiguration().densityDpi;
        if (z) {
            configuration.smallestScreenWidthDp = DeviceUtil.INSTANCE.getScreenSizeFromDisplays(companion.getApplication(), DeviceUtil.ScreenType.LARGE).getSw();
        } else {
            configuration.smallestScreenWidthDp = DeviceUtil.INSTANCE.getScreenSizeFromDisplays(companion.getApplication(), DeviceUtil.ScreenType.SMALL).getSw();
        }
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "setFoldConfiguration: sw=" + configuration.smallestScreenWidthDp + " dpi=" + configuration.densityDpi + " isLargeScreen=" + z);
        setConfigurationScreenSize(point, configuration);
    }

    public final void setFoldScreenshotPreviews(Context context, PresetTemplateConfig presetTemplateConfig, ScreenshotPreviews screenshotPreviews, ScreenshotPreviewLoaded screenshotPreviewLoaded) {
        screenshotPreviews.setTotal(3);
        if (DeviceUtil.INSTANCE.isLargeDisplay(context)) {
            screenshotPreviews.setSmall(createScreenshotPreview(context, presetTemplateConfig.clone(), false, 1, screenshotPreviewLoaded));
            setLargeScreenScreenshotPreviews(context, presetTemplateConfig.clone(), screenshotPreviews, screenshotPreviewLoaded);
        } else {
            screenshotPreviews.setLargePortrait(createScreenshotPreview(context, presetTemplateConfig.clone(), true, 1, screenshotPreviewLoaded));
            screenshotPreviews.setLargeLandscape(createScreenshotPreview(context, presetTemplateConfig.clone(), true, 2, screenshotPreviewLoaded));
        }
    }

    public final void setFoldScreenshotTemplateViews(Context context, CommonConfig commonConfig, boolean z, ScreenshotTemplateViews screenshotTemplateViews) {
        if (DeviceUtil.INSTANCE.isLargeDisplay(context)) {
            screenshotTemplateViews.setSmall(createScreenshotTemplateView(context, commonConfig, z, false, 1, screenshotTemplateViews.getSmallCallback()));
            setLargeScreenScreenshotTemplateViews(context, commonConfig, z, screenshotTemplateViews);
        } else {
            screenshotTemplateViews.setLargePortrait(createScreenshotTemplateView(context, commonConfig, z, true, 1, screenshotTemplateViews.getLargePortCallback()));
            screenshotTemplateViews.setLargeLandscape(createScreenshotTemplateView(context, commonConfig, z, true, 2, screenshotTemplateViews.getLargeLandCallback()));
        }
    }

    public final void setJ18Configuration(boolean z, Point point, Configuration configuration) {
        configuration.densityDpi = 440;
        if (z) {
            configuration.smallestScreenWidthDp = 675;
        } else {
            configuration.smallestScreenWidthDp = 320;
        }
        setConfigurationScreenSize(point, configuration);
    }

    public final void setJ18ScreenshotPreviews(Context context, PresetTemplateConfig presetTemplateConfig, ScreenshotPreviews screenshotPreviews, ScreenshotPreviewLoaded screenshotPreviewLoaded) {
        screenshotPreviews.setTotal(2);
        PresetTemplateConfig presetTemplateConfigClone = presetTemplateConfig.clone();
        boolean zIsLargeDisplay = DeviceUtil.INSTANCE.isLargeDisplay(context);
        ImageView imageViewCreateScreenshotPreview = createScreenshotPreview(context, presetTemplateConfigClone, !zIsLargeDisplay, 1, screenshotPreviewLoaded);
        if (zIsLargeDisplay) {
            screenshotPreviews.setSmall(imageViewCreateScreenshotPreview);
        } else {
            screenshotPreviews.setLargePortrait(imageViewCreateScreenshotPreview);
        }
    }

    public final void setJ18ScreenshotTemplateViews(Context context, CommonConfig commonConfig, boolean z, ScreenshotTemplateViews screenshotTemplateViews) {
        boolean zIsLargeDisplay = DeviceUtil.INSTANCE.isLargeDisplay(context);
        BaseTemplateView baseTemplateViewCreateScreenshotTemplateView = createScreenshotTemplateView(context, commonConfig, z, !zIsLargeDisplay, 1, zIsLargeDisplay ? screenshotTemplateViews.getLargePortCallback() : screenshotTemplateViews.getSmallCallback());
        if (zIsLargeDisplay) {
            screenshotTemplateViews.setSmall(baseTemplateViewCreateScreenshotTemplateView);
        } else {
            screenshotTemplateViews.setLargePortrait(baseTemplateViewCreateScreenshotTemplateView);
        }
    }

    public final void setLargeScreenScreenshotPreviews(Context context, PresetTemplateConfig presetTemplateConfig, ScreenshotPreviews screenshotPreviews, ScreenshotPreviewLoaded screenshotPreviewLoaded) {
        boolean zIsDisplayLandScape = DeviceUtil.INSTANCE.isDisplayLandScape(context);
        ImageView imageViewCreateScreenshotPreview = createScreenshotPreview(context, presetTemplateConfig, true, !zIsDisplayLandScape ? 2 : 1, screenshotPreviewLoaded);
        if (zIsDisplayLandScape) {
            screenshotPreviews.setLargePortrait(imageViewCreateScreenshotPreview);
        } else {
            screenshotPreviews.setLargeLandscape(imageViewCreateScreenshotPreview);
        }
    }

    public final void setLargeScreenScreenshotTemplateViews(Context context, CommonConfig commonConfig, boolean z, ScreenshotTemplateViews screenshotTemplateViews) {
        boolean zIsDisplayLandScape = DeviceUtil.INSTANCE.isDisplayLandScape(context);
        BaseTemplateView baseTemplateViewCreateScreenshotTemplateView = createScreenshotTemplateView(context, commonConfig, z, true, !zIsDisplayLandScape ? 2 : 1, !zIsDisplayLandScape ? screenshotTemplateViews.getLargeLandCallback() : screenshotTemplateViews.getLargePortCallback());
        if (zIsDisplayLandScape) {
            screenshotTemplateViews.setLargePortrait(baseTemplateViewCreateScreenshotTemplateView);
        } else {
            screenshotTemplateViews.setLargeLandscape(baseTemplateViewCreateScreenshotTemplateView);
        }
    }

    public final void setPadConfiguration(Point point, Configuration configuration) {
        configuration.densityDpi = DeviceUtil.initialDensityDpi$default(DeviceUtil.INSTANCE, 0, 1, null);
        setConfigurationScreenSize(point, configuration);
        configuration.smallestScreenWidthDp = RangesKt.coerceAtMost(configuration.screenWidthDp, configuration.screenHeightDp);
    }

    public final void setPadScreenshotPreviews(Context context, PresetTemplateConfig presetTemplateConfig, ScreenshotPreviews screenshotPreviews, ScreenshotPreviewLoaded screenshotPreviewLoaded) {
        screenshotPreviews.setTotal(2);
        setLargeScreenScreenshotPreviews(context, presetTemplateConfig.clone(), screenshotPreviews, screenshotPreviewLoaded);
    }

    public final void setPadScreenshotTemplateViews(Context context, CommonConfig commonConfig, boolean z, ScreenshotTemplateViews screenshotTemplateViews) {
        setLargeScreenScreenshotTemplateViews(context, commonConfig, z, screenshotTemplateViews);
    }

    public final void setScreenPixelSize(Point point, int i) {
        int iMin = Math.min(point.x, point.y);
        int iMax = Math.max(point.x, point.y);
        if (i == 1) {
            point.x = iMin;
            point.y = iMax;
        } else {
            point.x = iMax;
            point.y = iMin;
        }
    }

    public final void setScreenshotCapture(View view, Screenshots screenshots, ScreenshotPreviews screenshotPreviews) {
        if (screenshotPreviews == null) {
            return;
        }
        if (Intrinsics.areEqual(screenshotPreviews.getSmall(), view)) {
            screenshots.setSmall(ViewUtil.INSTANCE.drawToBitmap(view));
        } else if (Intrinsics.areEqual(screenshotPreviews.getLargePortrait(), view)) {
            screenshots.setLargePortrait(ViewUtil.INSTANCE.drawToBitmap(view));
        } else if (Intrinsics.areEqual(screenshotPreviews.getLargeLandscape(), view)) {
            screenshots.setLargeLandscape(ViewUtil.INSTANCE.drawToBitmap(view));
        }
    }

    public final void takeCaptureForMultiScreenMode(Context context, ViewGroup viewGroup, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, CommonConfig commonConfig, Function0 function0, Function1 function1) {
        if (baseTemplateView2 != null) {
            currentHomeTemplateWeak = new WeakReference(baseTemplateView2);
        }
        isFirstScreenshotFinished = false;
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), new DeviceScreenshotHelper$takeCaptureForMultiScreenMode$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key, function1).plus(JobKt__JobKt.Job$default(null, 1, null)), null, new AnonymousClass2(context, viewGroup, baseTemplateView, commonConfig, function0, null), 2, null);
    }

    public final void takeMultiScreenshotsForPreview(Context context, final FrameLayout screenshotContainer, PresetTemplateConfig presetTemplate, View currentTemplateView, final Function1 onScreenshotsCaptured) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(screenshotContainer, "screenshotContainer");
        Intrinsics.checkNotNullParameter(presetTemplate, "presetTemplate");
        Intrinsics.checkNotNullParameter(currentTemplateView, "currentTemplateView");
        Intrinsics.checkNotNullParameter(onScreenshotsCaptured, "onScreenshotsCaptured");
        final Screenshots screenshots = new Screenshots(null, null, null, null, null, null, null, 127, null);
        if (DeviceUtil.INSTANCE.isPhone()) {
            screenshots.setSmall(ViewUtil.INSTANCE.drawToBitmap(currentTemplateView));
            onScreenshotsCaptured.invoke(screenshots);
            return;
        }
        final Ref.IntRef intRef = new Ref.IntRef();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return DeviceScreenshotHelper.takeMultiScreenshotsForPreview$lambda$16(intRef, objectRef, onScreenshotsCaptured, screenshots);
            }
        };
        ScreenshotPreviews screenshotPreviewsCreateScreenshotPreviews = createScreenshotPreviews(context, presetTemplate, new ScreenshotPreviewLoaded() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper.takeMultiScreenshotsForPreview.1
            @Override // com.miui.keyguard.editor.utils.ScreenshotPreviewLoaded
            public void onScreenshotPreviewLoaded(View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                DeviceScreenshotHelper.INSTANCE.setScreenshotCapture(view, screenshots, (ScreenshotPreviews) objectRef.element);
                screenshotContainer.removeView(view);
                function0.invoke();
            }
        });
        objectRef.element = screenshotPreviewsCreateScreenshotPreviews;
        if (screenshotPreviewsCreateScreenshotPreviews.getSmall() != null) {
            screenshotContainer.addView(((ScreenshotPreviews) objectRef.element).getSmall());
        }
        if (((ScreenshotPreviews) objectRef.element).getLargePortrait() != null) {
            screenshotContainer.addView(((ScreenshotPreviews) objectRef.element).getLargePortrait());
        }
        if (((ScreenshotPreviews) objectRef.element).getLargeLandscape() != null) {
            screenshotContainer.addView(((ScreenshotPreviews) objectRef.element).getLargeLandscape());
        }
        screenshots.setCurrentScreenshot(context, ViewUtil.INSTANCE.drawToBitmap(currentTemplateView));
        function0.invoke();
    }

    /* JADX WARN: Code duplicated, block: B:102:0x037a  */
    /* JADX WARN: Code duplicated, block: B:107:0x038b  */
    /* JADX WARN: Code duplicated, block: B:110:0x0394 A[Catch: all -> 0x0044, TryCatch #0 {all -> 0x0044, blocks: (B:14:0x003f, B:151:0x04c6, B:139:0x0469, B:27:0x009f, B:129:0x0434, B:130:0x0439, B:132:0x043f, B:134:0x0448, B:117:0x03be, B:36:0x00fa, B:103:0x0381, B:108:0x038e, B:110:0x0394, B:112:0x039d, B:91:0x030d), top: B:166:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:112:0x039d A[Catch: all -> 0x0044, TryCatch #0 {all -> 0x0044, blocks: (B:14:0x003f, B:151:0x04c6, B:139:0x0469, B:27:0x009f, B:129:0x0434, B:130:0x0439, B:132:0x043f, B:134:0x0448, B:117:0x03be, B:36:0x00fa, B:103:0x0381, B:108:0x038e, B:110:0x0394, B:112:0x039d, B:91:0x030d), top: B:166:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:114:0x03b8  */
    /* JADX WARN: Code duplicated, block: B:115:0x03ba  */
    /* JADX WARN: Code duplicated, block: B:117:0x03be A[Catch: all -> 0x0044, PHI: r0 r1 r2 r3 r5 r6 r7 r33
  0x03be: PHI (r0v30 int) = (r0v27 int), (r0v31 int) binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE]
  0x03be: PHI (r1v39 com.miui.keyguard.editor.edit.base.BaseTemplateView) = 
  (r1v36 com.miui.keyguard.editor.edit.base.BaseTemplateView)
  (r1v41 com.miui.keyguard.editor.edit.base.BaseTemplateView)
 binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE]
  0x03be: PHI (r2v28 int) = (r2v25 int), (r2v29 int) binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE]
  0x03be: PHI (r3v30 ??) = (r3v67 ??), (r3v32 ??) binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE]
  0x03be: PHI (r5v25 com.miui.keyguard.editor.utils.Screenshots) = (r5v21 com.miui.keyguard.editor.utils.Screenshots), (r5v26 com.miui.keyguard.editor.utils.Screenshots) binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE]
  0x03be: PHI (r6v25 kotlin.jvm.functions.Function3) = (r6v21 kotlin.jvm.functions.Function3), (r6v26 kotlin.jvm.functions.Function3) binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE]
  0x03be: PHI (r7v18 android.view.ViewGroup) = (r7v16 android.view.ViewGroup), (r7v19 android.view.ViewGroup) binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE]
  0x03be: PHI (r33v14 boolean) = (r33v11 boolean), (r33v15 boolean) binds: [B:111:0x039b, B:116:0x03bc] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {all -> 0x0044, blocks: (B:14:0x003f, B:151:0x04c6, B:139:0x0469, B:27:0x009f, B:129:0x0434, B:130:0x0439, B:132:0x043f, B:134:0x0448, B:117:0x03be, B:36:0x00fa, B:103:0x0381, B:108:0x038e, B:110:0x0394, B:112:0x039d, B:91:0x030d), top: B:166:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:120:0x03e2  */
    /* JADX WARN: Code duplicated, block: B:123:0x03e9  */
    /* JADX WARN: Code duplicated, block: B:124:0x03ec  */
    /* JADX WARN: Code duplicated, block: B:128:0x042d  */
    /* JADX WARN: Code duplicated, block: B:130:0x0439 A[Catch: all -> 0x0044, PHI: r0 r2 r3 r5 r6 r7 r33
  0x0439: PHI (r0v35 int) = (r0v27 int), (r0v36 int) binds: [B:109:0x0392, B:129:0x0434] A[DONT_GENERATE, DONT_INLINE]
  0x0439: PHI (r2v33 int) = (r2v25 int), (r2v34 int) binds: [B:109:0x0392, B:129:0x0434] A[DONT_GENERATE, DONT_INLINE]
  0x0439: PHI (r3v40 ??) = (r3v65 ??), (r3v66 ??) binds: [B:109:0x0392, B:129:0x0434] A[DONT_GENERATE, DONT_INLINE]
  0x0439: PHI (r5v35 com.miui.keyguard.editor.utils.Screenshots) = (r5v21 com.miui.keyguard.editor.utils.Screenshots), (r5v37 com.miui.keyguard.editor.utils.Screenshots) binds: [B:109:0x0392, B:129:0x0434] A[DONT_GENERATE, DONT_INLINE]
  0x0439: PHI (r6v36 kotlin.jvm.functions.Function3) = (r6v21 kotlin.jvm.functions.Function3), (r6v38 kotlin.jvm.functions.Function3) binds: [B:109:0x0392, B:129:0x0434] A[DONT_GENERATE, DONT_INLINE]
  0x0439: PHI (r7v27 android.view.ViewGroup) = (r7v16 android.view.ViewGroup), (r7v28 android.view.ViewGroup) binds: [B:109:0x0392, B:129:0x0434] A[DONT_GENERATE, DONT_INLINE]
  0x0439: PHI (r33v19 boolean) = (r33v11 boolean), (r33v20 boolean) binds: [B:109:0x0392, B:129:0x0434] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {all -> 0x0044, blocks: (B:14:0x003f, B:151:0x04c6, B:139:0x0469, B:27:0x009f, B:129:0x0434, B:130:0x0439, B:132:0x043f, B:134:0x0448, B:117:0x03be, B:36:0x00fa, B:103:0x0381, B:108:0x038e, B:110:0x0394, B:112:0x039d, B:91:0x030d), top: B:166:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:132:0x043f A[Catch: all -> 0x0044, TryCatch #0 {all -> 0x0044, blocks: (B:14:0x003f, B:151:0x04c6, B:139:0x0469, B:27:0x009f, B:129:0x0434, B:130:0x0439, B:132:0x043f, B:134:0x0448, B:117:0x03be, B:36:0x00fa, B:103:0x0381, B:108:0x038e, B:110:0x0394, B:112:0x039d, B:91:0x030d), top: B:166:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:134:0x0448 A[Catch: all -> 0x0044, TryCatch #0 {all -> 0x0044, blocks: (B:14:0x003f, B:151:0x04c6, B:139:0x0469, B:27:0x009f, B:129:0x0434, B:130:0x0439, B:132:0x043f, B:134:0x0448, B:117:0x03be, B:36:0x00fa, B:103:0x0381, B:108:0x038e, B:110:0x0394, B:112:0x039d, B:91:0x030d), top: B:166:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:136:0x0464  */
    /* JADX WARN: Code duplicated, block: B:137:0x0465  */
    /* JADX WARN: Code duplicated, block: B:139:0x0469 A[Catch: all -> 0x0044, PHI: r0 r1 r2 r3 r5 r6 r7 r33
  0x0469: PHI (r0v38 int) = (r0v35 int), (r0v39 int) binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE]
  0x0469: PHI (r1v48 com.miui.keyguard.editor.edit.base.BaseTemplateView) = 
  (r1v45 com.miui.keyguard.editor.edit.base.BaseTemplateView)
  (r1v50 com.miui.keyguard.editor.edit.base.BaseTemplateView)
 binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE]
  0x0469: PHI (r2v36 int) = (r2v33 int), (r2v37 int) binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE]
  0x0469: PHI (r3v45 ??) = (r3v63 ??), (r3v47 ??) binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE]
  0x0469: PHI (r5v40 com.miui.keyguard.editor.utils.Screenshots) = (r5v35 com.miui.keyguard.editor.utils.Screenshots), (r5v41 com.miui.keyguard.editor.utils.Screenshots) binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE]
  0x0469: PHI (r6v41 kotlin.jvm.functions.Function3) = (r6v36 kotlin.jvm.functions.Function3), (r6v42 kotlin.jvm.functions.Function3) binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE]
  0x0469: PHI (r7v29 android.view.ViewGroup) = (r7v27 android.view.ViewGroup), (r7v30 android.view.ViewGroup) binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE]
  0x0469: PHI (r33v22 boolean) = (r33v19 boolean), (r33v23 boolean) binds: [B:133:0x0446, B:138:0x0467] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {all -> 0x0044, blocks: (B:14:0x003f, B:151:0x04c6, B:139:0x0469, B:27:0x009f, B:129:0x0434, B:130:0x0439, B:132:0x043f, B:134:0x0448, B:117:0x03be, B:36:0x00fa, B:103:0x0381, B:108:0x038e, B:110:0x0394, B:112:0x039d, B:91:0x030d), top: B:166:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:142:0x048c  */
    /* JADX WARN: Code duplicated, block: B:145:0x0492  */
    /* JADX WARN: Code duplicated, block: B:146:0x0495  */
    /* JADX WARN: Code duplicated, block: B:150:0x04c4  */
    /* JADX WARN: Code duplicated, block: B:156:0x04d3  */
    /* JADX WARN: Code duplicated, block: B:158:0x04d9  */
    /* JADX WARN: Code duplicated, block: B:161:0x04e2  */
    /* JADX WARN: Code duplicated, block: B:164:0x04eb  */
    /* JADX WARN: Code duplicated, block: B:59:0x021e  */
    /* JADX WARN: Code duplicated, block: B:61:0x022a  */
    /* JADX WARN: Code duplicated, block: B:62:0x0237  */
    /* JADX WARN: Code duplicated, block: B:65:0x0240  */
    /* JADX WARN: Code duplicated, block: B:68:0x0258 A[LOOP:0: B:66:0x0252->B:68:0x0258, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:74:0x029c  */
    /* JADX WARN: Code duplicated, block: B:77:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:81:0x02d6 A[Catch: all -> 0x0305, TryCatch #1 {all -> 0x0305, blocks: (B:79:0x02cd, B:81:0x02d6, B:83:0x02df), top: B:168:0x02cd }] */
    /* JADX WARN: Code duplicated, block: B:83:0x02df A[Catch: all -> 0x0305, TRY_LEAVE, TryCatch #1 {all -> 0x0305, blocks: (B:79:0x02cd, B:81:0x02d6, B:83:0x02df), top: B:168:0x02cd }] */
    /* JADX WARN: Code duplicated, block: B:86:0x02fc  */
    /* JADX WARN: Code duplicated, block: B:8:0x001a  */
    /* JADX WARN: Code duplicated, block: B:90:0x0309  */
    /* JADX WARN: Code duplicated, block: B:94:0x0330  */
    /* JADX WARN: Code duplicated, block: B:97:0x0337  */
    /* JADX WARN: Code duplicated, block: B:98:0x033a  */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0295, code lost:
    
        if (r5.invoke(r4, r0, r9) == r12) goto L149;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2, types: [com.miui.keyguard.editor.utils.DeviceScreenshotHelper] */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v33, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r1v40 */
    /* JADX WARN: Type inference failed for: r1v42, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v44 */
    /* JADX WARN: Type inference failed for: r1v49 */
    /* JADX WARN: Type inference failed for: r1v51 */
    /* JADX WARN: Type inference failed for: r1v52, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v54 */
    /* JADX WARN: Type inference failed for: r26v0, types: [com.miui.keyguard.editor.utils.DeviceScreenshotHelper, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10, types: [com.miui.keyguard.editor.utils.ScreenshotTemplateViews, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v25, types: [com.miui.keyguard.editor.utils.ScreenshotTemplateViews, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v30, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v32 */
    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.miui.keyguard.editor.utils.ScreenshotTemplateViews] */
    /* JADX WARN: Type inference failed for: r3v40, types: [com.miui.keyguard.editor.utils.ScreenshotTemplateViews, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v42 */
    /* JADX WARN: Type inference failed for: r3v45, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v47 */
    /* JADX WARN: Type inference failed for: r3v50 */
    /* JADX WARN: Type inference failed for: r3v52 */
    /* JADX WARN: Type inference failed for: r3v55 */
    /* JADX WARN: Type inference failed for: r3v60 */
    /* JADX WARN: Type inference failed for: r3v61 */
    /* JADX WARN: Type inference failed for: r3v62 */
    /* JADX WARN: Type inference failed for: r3v63 */
    /* JADX WARN: Type inference failed for: r3v64 */
    /* JADX WARN: Type inference failed for: r3v65 */
    /* JADX WARN: Type inference failed for: r3v66 */
    /* JADX WARN: Type inference failed for: r3v67 */
    /* JADX WARN: Type inference failed for: r3v68 */
    /* JADX WARN: Type inference failed for: r3v69 */
    /* JADX WARN: Type inference failed for: r3v70 */
    /* JADX WARN: Type inference failed for: r3v71 */
    /* JADX WARN: Type inference failed for: r3v72 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r4v48 */
    /* JADX WARN: Type inference failed for: r4v56 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v63 */
    /* JADX WARN: Type inference failed for: r4v64 */
    /* JADX WARN: Type inference failed for: r4v65 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object takeMultiScreenshotsForTemplateView(android.content.Context r27, android.view.ViewGroup r28, com.miui.keyguard.editor.data.bean.CommonConfig r29, com.miui.keyguard.editor.edit.base.BaseTemplateView r30, boolean r31, kotlin.jvm.functions.Function3 r32, kotlin.coroutines.Continuation r33) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 1294
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.utils.DeviceScreenshotHelper.takeMultiScreenshotsForTemplateView(android.content.Context, android.view.ViewGroup, com.miui.keyguard.editor.data.bean.CommonConfig, com.miui.keyguard.editor.edit.base.BaseTemplateView, boolean, kotlin.jvm.functions.Function3, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object takeScreenshotSync(BaseTemplateView baseTemplateView, Screenshots screenshots, boolean z, boolean z2, Function4 function4, Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        final Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1(baseTemplateView, screenshots, z, z2, function4, cancellableContinuationImpl, null), 3, null);
        cancellableContinuationImpl.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeScreenshotSync$2$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                Log.e("Keyguard-Editor-DeviceScreenshotHelper", "single screenshot coroutine cancel");
                Job.DefaultImpls.cancel$default(jobLaunch$default, null, 1, null);
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}

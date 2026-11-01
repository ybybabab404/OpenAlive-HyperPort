package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.util.Consumer;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.template.CurrentTemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.edit.base.DepthPresetDownloadManager;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.MainFragment;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.ClickUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.Process;
import com.miui.keyguard.editor.utils.Screenshots;
import com.miui.keyguard.editor.utils.SharedPreferencesUtil;
import com.miui.keyguard.editor.utils.SpecialWallpaperHelper;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.segment.UtilsKt;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.AlertDialogBuilder;
import com.miui.keyguard.editor.view.AlertDialogsKtKt;
import com.miui.keyguard.editor.view.FashionGalleryDialogsKt;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.util.Arrays;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.appcompat.app.AlertDialog;

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
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateApplyController implements DialogInterface.OnDismissListener {
    public final String TAG;
    public final FragmentActivity activity;
    public AlertDialog aodApplyDialog;
    public final Context applicationContext;
    public ApplyCallback applyCallback;
    public Consumer applyMyTemplateCallback;
    public Point displayRealSize;
    public AlertDialog downloadDialog;
    public AlertDialog dualClockDialog;
    public int[] floorTrackShowTag;
    public AlertDialog galleryDialog;
    public boolean hasPendingApplyTemplateTask;
    public AlertDialog historyDeleteDialog;
    public boolean isApplying;
    public boolean isCurrentTemplateScreenshotsSaving;
    public long lastApplyTime;
    public final SelectItemProvider mSelectItemProvider;
    public final Resources resources;
    public FrameLayout screenshotContainer;
    public Float screenshotRadius;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1, reason: invalid class name */
    /* JADX INFO: compiled from: TemplateApplyController.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1", f = "TemplateApplyController.kt", l = {412}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isPreset;
        public final /* synthetic */ int $screenStatus;
        public final /* synthetic */ TemplateItemBean $templateBean;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, TemplateItemBean templateItemBean, int i, Continuation continuation) {
            super(2, continuation);
            this.$isPreset = z;
            this.$templateBean = templateItemBean;
            this.$screenStatus = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TemplateApplyController.this.new AnonymousClass1(this.$isPreset, this.$templateBean, this.$screenStatus, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object objWithContext;
            ScreenshotSource screenshotSource;
            PresetTemplateConfig preset;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1 templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1 = new TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1(TemplateApplyController.this, this.$isPreset, this.$templateBean, this.$screenStatus, null);
                this.label = 1;
                objWithContext = BuildersKt.withContext(io2, templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1, this);
                if (objWithContext == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                objWithContext = obj;
            }
            TaskResult taskResult = (TaskResult) objWithContext;
            String currentWallpaperType = taskResult.getCurrentWallpaperType();
            boolean zIsVideoWallpaper = taskResult.isVideoWallpaper();
            int galleryStatus = taskResult.getGalleryStatus();
            Log.i(TemplateApplyController.this.TAG, "checkGalleryOrApplyTemplate -> currentWallpaperType = " + currentWallpaperType + ", isVideoWallpaper = " + zIsVideoWallpaper + ", galleryStatus = " + galleryStatus);
            if (this.$isPreset) {
                screenshotSource = new ScreenshotSource(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 262143, null);
                TemplateApplyController templateApplyController = TemplateApplyController.this;
                TemplateItemBean templateItemBean = this.$templateBean;
                ScreenshotSourceKt.setCurrentScreenshot(screenshotSource, templateApplyController.activity, (templateItemBean == null || (preset = templateItemBean.getPreset()) == null) ? null : preset.getPreviewImage());
                ScreenshotSourceKt.setCurrentWallpaperScreenshot(screenshotSource, templateApplyController.activity, taskResult.getPresetWallpaper());
            } else {
                ScreenshotSource screenshotSource2 = new ScreenshotSource(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 262143, null);
                ScreenshotSourceKt.setHistoryScreenshot(screenshotSource2, this.$screenStatus, taskResult.getHistoryScreenshot(), taskResult.getHistoryWallpaperScreenshot(), taskResult.getHistoryWallpaper());
                screenshotSource = screenshotSource2;
            }
            TemplateApplyController.this.startApplyTemplate(this.$isPreset, this.$templateBean, currentWallpaperType, zIsVideoWallpaper, galleryStatus, screenshotSource);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$finalApplyTemplate$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplyController.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController", f = "TemplateApplyController.kt", l = {1191}, m = "finalApplyTemplate")
    public static final class C01241 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public C01241(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TemplateApplyController.this.finalApplyTemplate(null, false, null, 0L, false, 0, 0, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$loadTemplateAndScreenshot$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplyController.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$loadTemplateAndScreenshot$1", f = "TemplateApplyController.kt", l = {1585, 1622}, m = "invokeSuspend")
    public static final class C01251 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ScreenshotCallback $callback;
        public final /* synthetic */ WallpaperInfo $homeWallpaperInfo;
        public final /* synthetic */ boolean $isPreset;
        public final /* synthetic */ WallpaperInfo $lockWallpaperInfo;
        public final /* synthetic */ CommonConfig $templateConfig;
        public final /* synthetic */ String $templateId;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$loadTemplateAndScreenshot$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TemplateApplyController.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$loadTemplateAndScreenshot$1$1", f = "TemplateApplyController.kt", l = {}, m = "invokeSuspend")
        public static final class C00291 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ WallpaperInfo $homeWallpaperInfo;
            public final /* synthetic */ boolean $isPreset;
            public final /* synthetic */ WallpaperInfo $lockWallpaperInfo;
            public int label;
            public final /* synthetic */ TemplateApplyController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00291(TemplateApplyController templateApplyController, WallpaperInfo wallpaperInfo, boolean z, WallpaperInfo wallpaperInfo2, Continuation continuation) {
                super(2, continuation);
                this.this$0 = templateApplyController;
                this.$lockWallpaperInfo = wallpaperInfo;
                this.$isPreset = z;
                this.$homeWallpaperInfo = wallpaperInfo2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00291(this.this$0, this.$lockWallpaperInfo, this.$isPreset, this.$homeWallpaperInfo, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                TemplateApi companion = TemplateApi.Companion.getInstance(this.this$0.activity);
                WallpaperInfo wallpaperInfo = this.$lockWallpaperInfo;
                boolean z = this.$isPreset;
                WallpaperInfo wallpaperInfo2 = this.$homeWallpaperInfo;
                companion.loadTemplateWallpaper(wallpaperInfo, z);
                companion.loadTemplateWallpaper(wallpaperInfo2, z);
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$loadTemplateAndScreenshot$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: TemplateApplyController.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$loadTemplateAndScreenshot$1$3", f = "TemplateApplyController.kt", l = {}, m = "invokeSuspend")
        public static final class AnonymousClass3 extends SuspendLambda implements Function3<Screenshots, Integer, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Screenshots $allScreenshot;
            public final /* synthetic */ ScreenshotCallback $callback;
            public final /* synthetic */ Ref.IntRef $size;
            public final /* synthetic */ int $total;
            public /* synthetic */ Object L$0;
            public int label;
            public final /* synthetic */ TemplateApplyController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(Ref.IntRef intRef, Screenshots screenshots, int i, TemplateApplyController templateApplyController, ScreenshotCallback screenshotCallback, Continuation continuation) {
                super(3, continuation);
                this.$size = intRef;
                this.$allScreenshot = screenshots;
                this.$total = i;
                this.this$0 = templateApplyController;
                this.$callback = screenshotCallback;
            }

            public final Object invoke(Screenshots screenshots, int i, Continuation continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$size, this.$allScreenshot, this.$total, this.this$0, this.$callback, continuation);
                anonymousClass3.L$0 = screenshots;
                return anonymousClass3.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return invoke((Screenshots) obj, ((Number) obj2).intValue(), (Continuation) obj3);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Screenshots screenshots = (Screenshots) this.L$0;
                this.$size.element++;
                this.$allScreenshot.putScreenshot(screenshots);
                if (this.$size.element >= this.$total) {
                    Log.d(this.this$0.TAG, "loadTemplateAndScreenshot: allScreenshot=" + this.$allScreenshot);
                    this.$callback.onScreenshotComplete(this.$allScreenshot);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01251(String str, ScreenshotCallback screenshotCallback, CommonConfig commonConfig, WallpaperInfo wallpaperInfo, boolean z, WallpaperInfo wallpaperInfo2, Continuation continuation) {
            super(2, continuation);
            this.$templateId = str;
            this.$callback = screenshotCallback;
            this.$templateConfig = commonConfig;
            this.$lockWallpaperInfo = wallpaperInfo;
            this.$isPreset = z;
            this.$homeWallpaperInfo = wallpaperInfo2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TemplateApplyController.this.new C01251(this.$templateId, this.$callback, this.$templateConfig, this.$lockWallpaperInfo, this.$isPreset, this.$homeWallpaperInfo, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01251) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0100, code lost:
        
            if (com.miui.keyguard.editor.utils.DeviceScreenshotHelper.takeMultiScreenshotsForTemplateView$default(r0, r2, r3, r3, r4, false, r6, r25, 16, null) == r10) goto L31;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r26) {
            /*
                Method dump skipped, instruction units count: 262
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.homepage.util.TemplateApplyController.C01251.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplyController.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1", f = "TemplateApplyController.kt", l = {1403, 1418, 1448, 1465, 1486, 1503, 1524}, m = "invokeSuspend")
    public static final class C01261 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ScreenshotCallback $callback;
        public final /* synthetic */ PresetTemplateConfig $currentPreviewTemplate;
        public final /* synthetic */ View $currentPreviewView;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public int label;
        public final /* synthetic */ TemplateApplyController this$0;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$4, reason: invalid class name */
        /* JADX INFO: compiled from: TemplateApplyController.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$4", f = "TemplateApplyController.kt", l = {}, m = "invokeSuspend")
        public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            public final /* synthetic */ PresetTemplateConfig $currentPreviewTemplate;
            public int label;
            public final /* synthetic */ TemplateApplyController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(PresetTemplateConfig presetTemplateConfig, TemplateApplyController templateApplyController, Continuation continuation) {
                super(2, continuation);
                this.$currentPreviewTemplate = presetTemplateConfig;
                this.this$0 = templateApplyController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$currentPreviewTemplate, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                String str;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                TemplateItemConfig bindItemConfig = this.$currentPreviewTemplate.getBindItemConfig();
                if (bindItemConfig == null || (wallpaperPath = bindItemConfig.getEffectWallpaperPath()) == null) {
                    TemplateItemConfig bindItemConfig2 = this.$currentPreviewTemplate.getBindItemConfig();
                    if (bindItemConfig2 != null) {
                        String wallpaperPath = bindItemConfig2.getWallpaperPath();
                        str = wallpaperPath;
                    } else {
                        str = null;
                    }
                } else {
                    str = wallpaperPath;
                }
                Bitmap templateBitmap$default = TemplateDataUtil.getTemplateBitmap$default(str, true, 0, 0, true, 12, null);
                Log.d(this.this$0.TAG, "screenshotWithCurrentPreview: load wallpaper for apply w=" + (templateBitmap$default != null ? Boxing.boxInt(templateBitmap$default.getWidth()) : null) + " h=" + (templateBitmap$default != null ? Boxing.boxInt(templateBitmap$default.getHeight()) : null));
                return templateBitmap$default;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01261(View view, TemplateApplyController templateApplyController, PresetTemplateConfig presetTemplateConfig, ScreenshotCallback screenshotCallback, Continuation continuation) {
            super(2, continuation);
            this.$currentPreviewView = view;
            this.this$0 = templateApplyController;
            this.$currentPreviewTemplate = presetTemplateConfig;
            this.$callback = screenshotCallback;
        }

        public static final Unit invokeSuspend$lambda$3(TemplateApplyController templateApplyController, Ref.ObjectRef objectRef, Ref.ObjectRef objectRef2, Ref.ObjectRef objectRef3, ScreenshotCallback screenshotCallback, Screenshots screenshots) {
            Log.i(templateApplyController.TAG, "screenshotWithCurrentPreview -> screenshots = " + screenshots);
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isPad()) {
                screenshots.setLargePortWallpaper((Bitmap) objectRef.element);
                screenshots.setLargeLandWallpaper((Bitmap) objectRef2.element);
            } else if (deviceUtil.isFold()) {
                screenshots.setSmallWallpaper((Bitmap) objectRef3.element);
                screenshots.setLargePortWallpaper((Bitmap) objectRef.element);
                screenshots.setLargeLandWallpaper((Bitmap) objectRef2.element);
            } else {
                screenshots.setCurrentWallpaperScreenshot(templateApplyController.activity, (Bitmap) objectRef3.element);
            }
            screenshotCallback.onScreenshotComplete(screenshots);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01261(this.$currentPreviewView, this.this$0, this.$currentPreviewTemplate, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01261) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:50:0x01b8  */
        /* JADX WARN: Code duplicated, block: B:52:0x01c2  */
        /* JADX WARN: Code duplicated, block: B:57:0x01d3  */
        /* JADX WARN: Code duplicated, block: B:60:0x01da  */
        /* JADX WARN: Code duplicated, block: B:63:0x01f8  */
        /* JADX WARN: Code duplicated, block: B:65:0x01fc  */
        /* JADX WARN: Code duplicated, block: B:68:0x021a  */
        /* JADX WARN: Code duplicated, block: B:71:0x021f  */
        /* JADX WARN: Code duplicated, block: B:73:0x0223  */
        /* JADX WARN: Code duplicated, block: B:75:0x022d  */
        /* JADX WARN: Code duplicated, block: B:80:0x023e  */
        /* JADX WARN: Code duplicated, block: B:83:0x0245  */
        /* JADX WARN: Code duplicated, block: B:86:0x0262  */
        /* JADX WARN: Code duplicated, block: B:89:0x0269  */
        /* JADX WARN: Code duplicated, block: B:92:0x0286  */
        /* JADX WARN: Code duplicated, block: B:94:0x028b  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.ObjectRef objectRef;
            Ref.ObjectRef objectRef2;
            Ref.ObjectRef objectRef3;
            Ref.ObjectRef objectRef4;
            Object objWithContext;
            Ref.ObjectRef objectRef5;
            Ref.ObjectRef objectRef6;
            Object objWithContext2;
            Ref.ObjectRef objectRef7;
            TemplateConfig lockscreenInfo;
            WallpaperInfo wallpaperInfo;
            Object objWithContext3;
            Ref.ObjectRef objectRef8;
            Ref.ObjectRef objectRef9;
            Ref.ObjectRef objectRef10;
            Point screenPixelSize;
            Point screenPixelSize2;
            Ref.ObjectRef objectRef11;
            PresetTemplateConfig presetTemplateConfig;
            TemplateApplyController templateApplyController;
            CommonConfig accessConfig;
            String resourceType;
            Object objWithContext4;
            Object objWithContext5;
            TemplateConfig lockscreenInfo2;
            WallpaperInfo wallpaperInfo2;
            final Ref.ObjectRef objectRef12;
            final Ref.ObjectRef objectRef13;
            final Ref.ObjectRef objectRef14;
            PresetTemplateConfig presetTemplateConfig2;
            TemplateApplyController templateApplyController2;
            CommonConfig accessConfig2;
            String resourceType2;
            Object objWithContext6;
            Ref.ObjectRef objectRef15;
            Object objWithContext7;
            Ref.ObjectRef objectRef16;
            TemplateConfig lockscreenInfo3;
            WallpaperInfo wallpaperInfo3;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    objectRef = new Ref.ObjectRef();
                    objectRef2 = new Ref.ObjectRef();
                    Ref.ObjectRef objectRef17 = new Ref.ObjectRef();
                    DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                    if (!deviceUtil.isFold() && !deviceUtil.isPad()) {
                        CoroutineDispatcher io2 = Dispatchers.getIO();
                        AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$currentPreviewTemplate, this.this$0, null);
                        this.L$0 = objectRef;
                        this.L$1 = objectRef2;
                        this.L$2 = objectRef17;
                        this.L$3 = objectRef17;
                        this.label = 7;
                        objWithContext3 = BuildersKt.withContext(io2, anonymousClass4, this);
                        if (objWithContext3 != coroutine_suspended) {
                            objectRef8 = objectRef;
                            objectRef9 = objectRef17;
                            objectRef10 = objectRef9;
                            objectRef9.element = objWithContext3;
                            objectRef12 = objectRef10;
                            objectRef14 = objectRef8;
                            objectRef13 = objectRef2;
                            DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                            Context context = this.$currentPreviewView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                            FrameLayout frameLayout = this.this$0.screenshotContainer;
                            Intrinsics.checkNotNull(frameLayout);
                            PresetTemplateConfig presetTemplateConfig3 = this.$currentPreviewTemplate;
                            View view = this.$currentPreviewView;
                            final TemplateApplyController templateApplyController3 = this.this$0;
                            final ScreenshotCallback screenshotCallback = this.$callback;
                            deviceScreenshotHelper.takeMultiScreenshotsForPreview(context, frameLayout, presetTemplateConfig3, view, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController3, objectRef14, objectRef13, objectRef12, screenshotCallback, (Screenshots) obj2);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    } else if (deviceUtil.isFold()) {
                        DeviceScreenshotHelper deviceScreenshotHelper2 = DeviceScreenshotHelper.INSTANCE;
                        Context context2 = this.$currentPreviewView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                        deviceScreenshotHelper2.getScreenPixelSize(context2, false, 1);
                        PresetTemplateConfig presetTemplateConfig4 = this.$currentPreviewTemplate;
                        TemplateApplyController templateApplyController4 = this.this$0;
                        CommonConfig accessConfig3 = presetTemplateConfig4.getAccessConfig();
                        if (Intrinsics.areEqual((accessConfig3 == null || (lockscreenInfo = accessConfig3.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType(), "sensor")) {
                            CoroutineDispatcher io3 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$1$1 templateApplyController$screenshotWithCurrentPreview$1$1$1 = new TemplateApplyController$screenshotWithCurrentPreview$1$1$1(presetTemplateConfig4, templateApplyController4, null);
                            this.L$0 = objectRef;
                            this.L$1 = objectRef2;
                            this.L$2 = objectRef17;
                            this.L$3 = objectRef17;
                            this.label = 1;
                            objWithContext2 = BuildersKt.withContext(io3, templateApplyController$screenshotWithCurrentPreview$1$1$1, this);
                            if (objWithContext2 != coroutine_suspended) {
                                objectRef5 = objectRef;
                                objectRef7 = objectRef17;
                                objectRef3 = objectRef2;
                                objectRef4 = objectRef7;
                                objectRef7.element = objWithContext2;
                                objectRef = objectRef5;
                                DeviceScreenshotHelper deviceScreenshotHelper3 = DeviceScreenshotHelper.INSTANCE;
                                Context context3 = this.$currentPreviewView.getContext();
                                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                                screenPixelSize = deviceScreenshotHelper3.getScreenPixelSize(context3, true, 1);
                                Context context4 = this.$currentPreviewView.getContext();
                                Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
                                screenPixelSize2 = deviceScreenshotHelper3.getScreenPixelSize(context4, true, 2);
                                if (screenPixelSize == null) {
                                    objectRef11 = objectRef;
                                    objectRef10 = objectRef4;
                                    if (screenPixelSize2 != null) {
                                        presetTemplateConfig2 = this.$currentPreviewTemplate;
                                        templateApplyController2 = this.this$0;
                                        accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                        if (accessConfig2 != null) {
                                            resourceType2 = null;
                                        } else {
                                            resourceType2 = null;
                                        }
                                        if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                            CoroutineDispatcher io4 = Dispatchers.getIO();
                                            TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$1 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                            this.L$0 = objectRef11;
                                            this.L$1 = objectRef3;
                                            this.L$2 = objectRef10;
                                            this.L$3 = objectRef3;
                                            this.L$4 = null;
                                            this.label = 5;
                                            objWithContext7 = BuildersKt.withContext(io4, templateApplyController$screenshotWithCurrentPreview$1$3$1, this);
                                            if (objWithContext7 != coroutine_suspended) {
                                                objectRef16 = objectRef3;
                                                objectRef8 = objectRef11;
                                                objectRef16.element = objWithContext7;
                                                objectRef2 = objectRef3;
                                                objectRef12 = objectRef10;
                                                objectRef14 = objectRef8;
                                                objectRef13 = objectRef2;
                                            }
                                        } else {
                                            CoroutineDispatcher io5 = Dispatchers.getIO();
                                            TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$2 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                            this.L$0 = objectRef11;
                                            this.L$1 = objectRef3;
                                            this.L$2 = objectRef10;
                                            this.L$3 = objectRef3;
                                            this.L$4 = null;
                                            this.label = 6;
                                            objWithContext6 = BuildersKt.withContext(io5, templateApplyController$screenshotWithCurrentPreview$1$3$2, this);
                                            if (objWithContext6 != coroutine_suspended) {
                                                objectRef15 = objectRef3;
                                                objectRef8 = objectRef11;
                                                objectRef15.element = objWithContext6;
                                                objectRef2 = objectRef3;
                                                objectRef12 = objectRef10;
                                                objectRef14 = objectRef8;
                                                objectRef13 = objectRef2;
                                            }
                                        }
                                    } else {
                                        objectRef12 = objectRef10;
                                        objectRef13 = objectRef3;
                                        objectRef14 = objectRef11;
                                    }
                                    DeviceScreenshotHelper deviceScreenshotHelper4 = DeviceScreenshotHelper.INSTANCE;
                                    Context context5 = this.$currentPreviewView.getContext();
                                    Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                                    FrameLayout frameLayout2 = this.this$0.screenshotContainer;
                                    Intrinsics.checkNotNull(frameLayout2);
                                    PresetTemplateConfig presetTemplateConfig5 = this.$currentPreviewTemplate;
                                    View view2 = this.$currentPreviewView;
                                    final TemplateApplyController templateApplyController5 = this.this$0;
                                    final ScreenshotCallback screenshotCallback2 = this.$callback;
                                    deviceScreenshotHelper4.takeMultiScreenshotsForPreview(context5, frameLayout2, presetTemplateConfig5, view2, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj2) {
                                            return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController5, objectRef14, objectRef13, objectRef12, screenshotCallback2, (Screenshots) obj2);
                                        }
                                    });
                                    return Unit.INSTANCE;
                                }
                                presetTemplateConfig = this.$currentPreviewTemplate;
                                templateApplyController = this.this$0;
                                accessConfig = presetTemplateConfig.getAccessConfig();
                                if (accessConfig != null || (lockscreenInfo2 = accessConfig.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) {
                                    resourceType = null;
                                } else {
                                    resourceType = wallpaperInfo2.getResourceType();
                                }
                                if (Intrinsics.areEqual(resourceType, "sensor")) {
                                    CoroutineDispatcher io6 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$2$1 templateApplyController$screenshotWithCurrentPreview$1$2$1 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$1(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                                    this.L$0 = objectRef;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef4;
                                    this.L$3 = screenPixelSize2;
                                    this.L$4 = objectRef;
                                    this.label = 3;
                                    objWithContext5 = BuildersKt.withContext(io6, templateApplyController$screenshotWithCurrentPreview$1$2$1, this);
                                    if (objWithContext5 != coroutine_suspended) {
                                        objectRef11 = objectRef;
                                        objectRef.element = objWithContext5;
                                        objectRef10 = objectRef4;
                                        if (screenPixelSize2 != null) {
                                            presetTemplateConfig2 = this.$currentPreviewTemplate;
                                            templateApplyController2 = this.this$0;
                                            accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                            if (accessConfig2 != null || (lockscreenInfo3 = accessConfig2.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) {
                                                resourceType2 = null;
                                            } else {
                                                resourceType2 = wallpaperInfo3.getResourceType();
                                            }
                                            if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                                CoroutineDispatcher io7 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$3 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 5;
                                                objWithContext7 = BuildersKt.withContext(io7, templateApplyController$screenshotWithCurrentPreview$1$3$3, this);
                                                if (objWithContext7 != coroutine_suspended) {
                                                    objectRef16 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef16.element = objWithContext7;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            } else {
                                                CoroutineDispatcher io8 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$4 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 6;
                                                objWithContext6 = BuildersKt.withContext(io8, templateApplyController$screenshotWithCurrentPreview$1$3$4, this);
                                                if (objWithContext6 != coroutine_suspended) {
                                                    objectRef15 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef15.element = objWithContext6;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            }
                                        } else {
                                            objectRef12 = objectRef10;
                                            objectRef13 = objectRef3;
                                            objectRef14 = objectRef11;
                                        }
                                        DeviceScreenshotHelper deviceScreenshotHelper5 = DeviceScreenshotHelper.INSTANCE;
                                        Context context6 = this.$currentPreviewView.getContext();
                                        Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
                                        FrameLayout frameLayout3 = this.this$0.screenshotContainer;
                                        Intrinsics.checkNotNull(frameLayout3);
                                        PresetTemplateConfig presetTemplateConfig6 = this.$currentPreviewTemplate;
                                        View view3 = this.$currentPreviewView;
                                        final TemplateApplyController templateApplyController6 = this.this$0;
                                        final ScreenshotCallback screenshotCallback3 = this.$callback;
                                        deviceScreenshotHelper5.takeMultiScreenshotsForPreview(context6, frameLayout3, presetTemplateConfig6, view3, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj2) {
                                                return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController6, objectRef14, objectRef13, objectRef12, screenshotCallback3, (Screenshots) obj2);
                                            }
                                        });
                                        return Unit.INSTANCE;
                                    }
                                } else {
                                    CoroutineDispatcher io9 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$2$2 templateApplyController$screenshotWithCurrentPreview$1$2$2 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$2(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                                    this.L$0 = objectRef;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef4;
                                    this.L$3 = screenPixelSize2;
                                    this.L$4 = objectRef;
                                    this.label = 4;
                                    objWithContext4 = BuildersKt.withContext(io9, templateApplyController$screenshotWithCurrentPreview$1$2$2, this);
                                    if (objWithContext4 != coroutine_suspended) {
                                        objectRef11 = objectRef;
                                        objectRef.element = objWithContext4;
                                        objectRef10 = objectRef4;
                                        if (screenPixelSize2 != null) {
                                            presetTemplateConfig2 = this.$currentPreviewTemplate;
                                            templateApplyController2 = this.this$0;
                                            accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                            if (accessConfig2 != null) {
                                                resourceType2 = null;
                                            } else {
                                                resourceType2 = null;
                                            }
                                            if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                                CoroutineDispatcher io10 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$5 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 5;
                                                objWithContext7 = BuildersKt.withContext(io10, templateApplyController$screenshotWithCurrentPreview$1$3$5, this);
                                                if (objWithContext7 != coroutine_suspended) {
                                                    objectRef16 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef16.element = objWithContext7;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            } else {
                                                CoroutineDispatcher io11 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$6 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 6;
                                                objWithContext6 = BuildersKt.withContext(io11, templateApplyController$screenshotWithCurrentPreview$1$3$6, this);
                                                if (objWithContext6 != coroutine_suspended) {
                                                    objectRef15 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef15.element = objWithContext6;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            }
                                        } else {
                                            objectRef12 = objectRef10;
                                            objectRef13 = objectRef3;
                                            objectRef14 = objectRef11;
                                        }
                                        DeviceScreenshotHelper deviceScreenshotHelper6 = DeviceScreenshotHelper.INSTANCE;
                                        Context context7 = this.$currentPreviewView.getContext();
                                        Intrinsics.checkNotNullExpressionValue(context7, "getContext(...)");
                                        FrameLayout frameLayout4 = this.this$0.screenshotContainer;
                                        Intrinsics.checkNotNull(frameLayout4);
                                        PresetTemplateConfig presetTemplateConfig7 = this.$currentPreviewTemplate;
                                        View view4 = this.$currentPreviewView;
                                        final TemplateApplyController templateApplyController7 = this.this$0;
                                        final ScreenshotCallback screenshotCallback4 = this.$callback;
                                        deviceScreenshotHelper6.takeMultiScreenshotsForPreview(context7, frameLayout4, presetTemplateConfig7, view4, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj2) {
                                                return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController7, objectRef14, objectRef13, objectRef12, screenshotCallback4, (Screenshots) obj2);
                                            }
                                        });
                                        return Unit.INSTANCE;
                                    }
                                }
                            }
                        } else {
                            CoroutineDispatcher io12 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$1$2 templateApplyController$screenshotWithCurrentPreview$1$1$2 = new TemplateApplyController$screenshotWithCurrentPreview$1$1$2(presetTemplateConfig4, templateApplyController4, null);
                            this.L$0 = objectRef;
                            this.L$1 = objectRef2;
                            this.L$2 = objectRef17;
                            this.L$3 = objectRef17;
                            this.label = 2;
                            objWithContext = BuildersKt.withContext(io12, templateApplyController$screenshotWithCurrentPreview$1$1$2, this);
                            if (objWithContext != coroutine_suspended) {
                                objectRef5 = objectRef;
                                objectRef6 = objectRef17;
                                objectRef3 = objectRef2;
                                objectRef4 = objectRef6;
                                objectRef6.element = objWithContext;
                                objectRef = objectRef5;
                                DeviceScreenshotHelper deviceScreenshotHelper7 = DeviceScreenshotHelper.INSTANCE;
                                Context context8 = this.$currentPreviewView.getContext();
                                Intrinsics.checkNotNullExpressionValue(context8, "getContext(...)");
                                screenPixelSize = deviceScreenshotHelper7.getScreenPixelSize(context8, true, 1);
                                Context context9 = this.$currentPreviewView.getContext();
                                Intrinsics.checkNotNullExpressionValue(context9, "getContext(...)");
                                screenPixelSize2 = deviceScreenshotHelper7.getScreenPixelSize(context9, true, 2);
                                if (screenPixelSize == null) {
                                    objectRef11 = objectRef;
                                    objectRef10 = objectRef4;
                                    if (screenPixelSize2 != null) {
                                        presetTemplateConfig2 = this.$currentPreviewTemplate;
                                        templateApplyController2 = this.this$0;
                                        accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                        if (accessConfig2 != null) {
                                            resourceType2 = null;
                                        } else {
                                            resourceType2 = null;
                                        }
                                        if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                            CoroutineDispatcher io13 = Dispatchers.getIO();
                                            TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$7 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                            this.L$0 = objectRef11;
                                            this.L$1 = objectRef3;
                                            this.L$2 = objectRef10;
                                            this.L$3 = objectRef3;
                                            this.L$4 = null;
                                            this.label = 5;
                                            objWithContext7 = BuildersKt.withContext(io13, templateApplyController$screenshotWithCurrentPreview$1$3$7, this);
                                            if (objWithContext7 != coroutine_suspended) {
                                                objectRef16 = objectRef3;
                                                objectRef8 = objectRef11;
                                                objectRef16.element = objWithContext7;
                                                objectRef2 = objectRef3;
                                                objectRef12 = objectRef10;
                                                objectRef14 = objectRef8;
                                                objectRef13 = objectRef2;
                                            }
                                        } else {
                                            CoroutineDispatcher io14 = Dispatchers.getIO();
                                            TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$8 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                            this.L$0 = objectRef11;
                                            this.L$1 = objectRef3;
                                            this.L$2 = objectRef10;
                                            this.L$3 = objectRef3;
                                            this.L$4 = null;
                                            this.label = 6;
                                            objWithContext6 = BuildersKt.withContext(io14, templateApplyController$screenshotWithCurrentPreview$1$3$8, this);
                                            if (objWithContext6 != coroutine_suspended) {
                                                objectRef15 = objectRef3;
                                                objectRef8 = objectRef11;
                                                objectRef15.element = objWithContext6;
                                                objectRef2 = objectRef3;
                                                objectRef12 = objectRef10;
                                                objectRef14 = objectRef8;
                                                objectRef13 = objectRef2;
                                            }
                                        }
                                    } else {
                                        objectRef12 = objectRef10;
                                        objectRef13 = objectRef3;
                                        objectRef14 = objectRef11;
                                    }
                                    DeviceScreenshotHelper deviceScreenshotHelper8 = DeviceScreenshotHelper.INSTANCE;
                                    Context context10 = this.$currentPreviewView.getContext();
                                    Intrinsics.checkNotNullExpressionValue(context10, "getContext(...)");
                                    FrameLayout frameLayout5 = this.this$0.screenshotContainer;
                                    Intrinsics.checkNotNull(frameLayout5);
                                    PresetTemplateConfig presetTemplateConfig8 = this.$currentPreviewTemplate;
                                    View view5 = this.$currentPreviewView;
                                    final TemplateApplyController templateApplyController8 = this.this$0;
                                    final ScreenshotCallback screenshotCallback5 = this.$callback;
                                    deviceScreenshotHelper8.takeMultiScreenshotsForPreview(context10, frameLayout5, presetTemplateConfig8, view5, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj2) {
                                            return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController8, objectRef14, objectRef13, objectRef12, screenshotCallback5, (Screenshots) obj2);
                                        }
                                    });
                                    return Unit.INSTANCE;
                                }
                                presetTemplateConfig = this.$currentPreviewTemplate;
                                templateApplyController = this.this$0;
                                accessConfig = presetTemplateConfig.getAccessConfig();
                                if (accessConfig != null) {
                                    resourceType = null;
                                } else {
                                    resourceType = null;
                                }
                                if (Intrinsics.areEqual(resourceType, "sensor")) {
                                    CoroutineDispatcher io15 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$2$1 templateApplyController$screenshotWithCurrentPreview$1$2$3 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$1(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                                    this.L$0 = objectRef;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef4;
                                    this.L$3 = screenPixelSize2;
                                    this.L$4 = objectRef;
                                    this.label = 3;
                                    objWithContext5 = BuildersKt.withContext(io15, templateApplyController$screenshotWithCurrentPreview$1$2$3, this);
                                    if (objWithContext5 != coroutine_suspended) {
                                        objectRef11 = objectRef;
                                        objectRef.element = objWithContext5;
                                        objectRef10 = objectRef4;
                                        if (screenPixelSize2 != null) {
                                            presetTemplateConfig2 = this.$currentPreviewTemplate;
                                            templateApplyController2 = this.this$0;
                                            accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                            if (accessConfig2 != null) {
                                                resourceType2 = null;
                                            } else {
                                                resourceType2 = null;
                                            }
                                            if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                                CoroutineDispatcher io16 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$9 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 5;
                                                objWithContext7 = BuildersKt.withContext(io16, templateApplyController$screenshotWithCurrentPreview$1$3$9, this);
                                                if (objWithContext7 != coroutine_suspended) {
                                                    objectRef16 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef16.element = objWithContext7;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            } else {
                                                CoroutineDispatcher io17 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$10 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 6;
                                                objWithContext6 = BuildersKt.withContext(io17, templateApplyController$screenshotWithCurrentPreview$1$3$10, this);
                                                if (objWithContext6 != coroutine_suspended) {
                                                    objectRef15 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef15.element = objWithContext6;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            }
                                        } else {
                                            objectRef12 = objectRef10;
                                            objectRef13 = objectRef3;
                                            objectRef14 = objectRef11;
                                        }
                                        DeviceScreenshotHelper deviceScreenshotHelper9 = DeviceScreenshotHelper.INSTANCE;
                                        Context context11 = this.$currentPreviewView.getContext();
                                        Intrinsics.checkNotNullExpressionValue(context11, "getContext(...)");
                                        FrameLayout frameLayout6 = this.this$0.screenshotContainer;
                                        Intrinsics.checkNotNull(frameLayout6);
                                        PresetTemplateConfig presetTemplateConfig9 = this.$currentPreviewTemplate;
                                        View view6 = this.$currentPreviewView;
                                        final TemplateApplyController templateApplyController9 = this.this$0;
                                        final ScreenshotCallback screenshotCallback6 = this.$callback;
                                        deviceScreenshotHelper9.takeMultiScreenshotsForPreview(context11, frameLayout6, presetTemplateConfig9, view6, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj2) {
                                                return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController9, objectRef14, objectRef13, objectRef12, screenshotCallback6, (Screenshots) obj2);
                                            }
                                        });
                                        return Unit.INSTANCE;
                                    }
                                } else {
                                    CoroutineDispatcher io18 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$2$2 templateApplyController$screenshotWithCurrentPreview$1$2$4 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$2(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                                    this.L$0 = objectRef;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef4;
                                    this.L$3 = screenPixelSize2;
                                    this.L$4 = objectRef;
                                    this.label = 4;
                                    objWithContext4 = BuildersKt.withContext(io18, templateApplyController$screenshotWithCurrentPreview$1$2$4, this);
                                    if (objWithContext4 != coroutine_suspended) {
                                        objectRef11 = objectRef;
                                        objectRef.element = objWithContext4;
                                        objectRef10 = objectRef4;
                                        if (screenPixelSize2 != null) {
                                            presetTemplateConfig2 = this.$currentPreviewTemplate;
                                            templateApplyController2 = this.this$0;
                                            accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                            if (accessConfig2 != null) {
                                                resourceType2 = null;
                                            } else {
                                                resourceType2 = null;
                                            }
                                            if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                                CoroutineDispatcher io19 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$11 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 5;
                                                objWithContext7 = BuildersKt.withContext(io19, templateApplyController$screenshotWithCurrentPreview$1$3$11, this);
                                                if (objWithContext7 != coroutine_suspended) {
                                                    objectRef16 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef16.element = objWithContext7;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            } else {
                                                CoroutineDispatcher io110 = Dispatchers.getIO();
                                                TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$12 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                                this.L$0 = objectRef11;
                                                this.L$1 = objectRef3;
                                                this.L$2 = objectRef10;
                                                this.L$3 = objectRef3;
                                                this.L$4 = null;
                                                this.label = 6;
                                                objWithContext6 = BuildersKt.withContext(io110, templateApplyController$screenshotWithCurrentPreview$1$3$12, this);
                                                if (objWithContext6 != coroutine_suspended) {
                                                    objectRef15 = objectRef3;
                                                    objectRef8 = objectRef11;
                                                    objectRef15.element = objWithContext6;
                                                    objectRef2 = objectRef3;
                                                    objectRef12 = objectRef10;
                                                    objectRef14 = objectRef8;
                                                    objectRef13 = objectRef2;
                                                }
                                            }
                                        } else {
                                            objectRef12 = objectRef10;
                                            objectRef13 = objectRef3;
                                            objectRef14 = objectRef11;
                                        }
                                        DeviceScreenshotHelper deviceScreenshotHelper10 = DeviceScreenshotHelper.INSTANCE;
                                        Context context12 = this.$currentPreviewView.getContext();
                                        Intrinsics.checkNotNullExpressionValue(context12, "getContext(...)");
                                        FrameLayout frameLayout7 = this.this$0.screenshotContainer;
                                        Intrinsics.checkNotNull(frameLayout7);
                                        PresetTemplateConfig presetTemplateConfig10 = this.$currentPreviewTemplate;
                                        View view7 = this.$currentPreviewView;
                                        final TemplateApplyController templateApplyController10 = this.this$0;
                                        final ScreenshotCallback screenshotCallback7 = this.$callback;
                                        deviceScreenshotHelper10.takeMultiScreenshotsForPreview(context12, frameLayout7, presetTemplateConfig10, view7, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj2) {
                                                return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController10, objectRef14, objectRef13, objectRef12, screenshotCallback7, (Screenshots) obj2);
                                            }
                                        });
                                        return Unit.INSTANCE;
                                    }
                                }
                            }
                        }
                    } else {
                        objectRef3 = objectRef2;
                        objectRef4 = objectRef17;
                        DeviceScreenshotHelper deviceScreenshotHelper11 = DeviceScreenshotHelper.INSTANCE;
                        Context context13 = this.$currentPreviewView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context13, "getContext(...)");
                        screenPixelSize = deviceScreenshotHelper11.getScreenPixelSize(context13, true, 1);
                        Context context14 = this.$currentPreviewView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context14, "getContext(...)");
                        screenPixelSize2 = deviceScreenshotHelper11.getScreenPixelSize(context14, true, 2);
                        if (screenPixelSize == null) {
                            objectRef11 = objectRef;
                            objectRef10 = objectRef4;
                            if (screenPixelSize2 != null) {
                                presetTemplateConfig2 = this.$currentPreviewTemplate;
                                templateApplyController2 = this.this$0;
                                accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                if (accessConfig2 != null) {
                                    resourceType2 = null;
                                } else {
                                    resourceType2 = null;
                                }
                                if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                    CoroutineDispatcher io111 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$13 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 5;
                                    objWithContext7 = BuildersKt.withContext(io111, templateApplyController$screenshotWithCurrentPreview$1$3$13, this);
                                    if (objWithContext7 != coroutine_suspended) {
                                        objectRef16 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef16.element = objWithContext7;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                } else {
                                    CoroutineDispatcher io112 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$14 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 6;
                                    objWithContext6 = BuildersKt.withContext(io112, templateApplyController$screenshotWithCurrentPreview$1$3$14, this);
                                    if (objWithContext6 != coroutine_suspended) {
                                        objectRef15 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef15.element = objWithContext6;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                }
                            } else {
                                objectRef12 = objectRef10;
                                objectRef13 = objectRef3;
                                objectRef14 = objectRef11;
                            }
                            DeviceScreenshotHelper deviceScreenshotHelper12 = DeviceScreenshotHelper.INSTANCE;
                            Context context15 = this.$currentPreviewView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context15, "getContext(...)");
                            FrameLayout frameLayout8 = this.this$0.screenshotContainer;
                            Intrinsics.checkNotNull(frameLayout8);
                            PresetTemplateConfig presetTemplateConfig11 = this.$currentPreviewTemplate;
                            View view8 = this.$currentPreviewView;
                            final TemplateApplyController templateApplyController11 = this.this$0;
                            final ScreenshotCallback screenshotCallback8 = this.$callback;
                            deviceScreenshotHelper12.takeMultiScreenshotsForPreview(context15, frameLayout8, presetTemplateConfig11, view8, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController11, objectRef14, objectRef13, objectRef12, screenshotCallback8, (Screenshots) obj2);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                        presetTemplateConfig = this.$currentPreviewTemplate;
                        templateApplyController = this.this$0;
                        accessConfig = presetTemplateConfig.getAccessConfig();
                        if (accessConfig != null) {
                            resourceType = null;
                        } else {
                            resourceType = null;
                        }
                        if (Intrinsics.areEqual(resourceType, "sensor")) {
                            CoroutineDispatcher io113 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$2$1 templateApplyController$screenshotWithCurrentPreview$1$2$5 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$1(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                            this.L$0 = objectRef;
                            this.L$1 = objectRef3;
                            this.L$2 = objectRef4;
                            this.L$3 = screenPixelSize2;
                            this.L$4 = objectRef;
                            this.label = 3;
                            objWithContext5 = BuildersKt.withContext(io113, templateApplyController$screenshotWithCurrentPreview$1$2$5, this);
                            if (objWithContext5 != coroutine_suspended) {
                                objectRef11 = objectRef;
                                objectRef.element = objWithContext5;
                                objectRef10 = objectRef4;
                                if (screenPixelSize2 != null) {
                                    presetTemplateConfig2 = this.$currentPreviewTemplate;
                                    templateApplyController2 = this.this$0;
                                    accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                    if (accessConfig2 != null) {
                                        resourceType2 = null;
                                    } else {
                                        resourceType2 = null;
                                    }
                                    if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                        CoroutineDispatcher io114 = Dispatchers.getIO();
                                        TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$15 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                        this.L$0 = objectRef11;
                                        this.L$1 = objectRef3;
                                        this.L$2 = objectRef10;
                                        this.L$3 = objectRef3;
                                        this.L$4 = null;
                                        this.label = 5;
                                        objWithContext7 = BuildersKt.withContext(io114, templateApplyController$screenshotWithCurrentPreview$1$3$15, this);
                                        if (objWithContext7 != coroutine_suspended) {
                                            objectRef16 = objectRef3;
                                            objectRef8 = objectRef11;
                                            objectRef16.element = objWithContext7;
                                            objectRef2 = objectRef3;
                                            objectRef12 = objectRef10;
                                            objectRef14 = objectRef8;
                                            objectRef13 = objectRef2;
                                        }
                                    } else {
                                        CoroutineDispatcher io115 = Dispatchers.getIO();
                                        TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$16 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                        this.L$0 = objectRef11;
                                        this.L$1 = objectRef3;
                                        this.L$2 = objectRef10;
                                        this.L$3 = objectRef3;
                                        this.L$4 = null;
                                        this.label = 6;
                                        objWithContext6 = BuildersKt.withContext(io115, templateApplyController$screenshotWithCurrentPreview$1$3$16, this);
                                        if (objWithContext6 != coroutine_suspended) {
                                            objectRef15 = objectRef3;
                                            objectRef8 = objectRef11;
                                            objectRef15.element = objWithContext6;
                                            objectRef2 = objectRef3;
                                            objectRef12 = objectRef10;
                                            objectRef14 = objectRef8;
                                            objectRef13 = objectRef2;
                                        }
                                    }
                                } else {
                                    objectRef12 = objectRef10;
                                    objectRef13 = objectRef3;
                                    objectRef14 = objectRef11;
                                }
                                DeviceScreenshotHelper deviceScreenshotHelper13 = DeviceScreenshotHelper.INSTANCE;
                                Context context16 = this.$currentPreviewView.getContext();
                                Intrinsics.checkNotNullExpressionValue(context16, "getContext(...)");
                                FrameLayout frameLayout9 = this.this$0.screenshotContainer;
                                Intrinsics.checkNotNull(frameLayout9);
                                PresetTemplateConfig presetTemplateConfig12 = this.$currentPreviewTemplate;
                                View view9 = this.$currentPreviewView;
                                final TemplateApplyController templateApplyController12 = this.this$0;
                                final ScreenshotCallback screenshotCallback9 = this.$callback;
                                deviceScreenshotHelper13.takeMultiScreenshotsForPreview(context16, frameLayout9, presetTemplateConfig12, view9, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj2) {
                                        return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController12, objectRef14, objectRef13, objectRef12, screenshotCallback9, (Screenshots) obj2);
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        } else {
                            CoroutineDispatcher io116 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$2$2 templateApplyController$screenshotWithCurrentPreview$1$2$6 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$2(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                            this.L$0 = objectRef;
                            this.L$1 = objectRef3;
                            this.L$2 = objectRef4;
                            this.L$3 = screenPixelSize2;
                            this.L$4 = objectRef;
                            this.label = 4;
                            objWithContext4 = BuildersKt.withContext(io116, templateApplyController$screenshotWithCurrentPreview$1$2$6, this);
                            if (objWithContext4 != coroutine_suspended) {
                                objectRef11 = objectRef;
                                objectRef.element = objWithContext4;
                                objectRef10 = objectRef4;
                                if (screenPixelSize2 != null) {
                                    presetTemplateConfig2 = this.$currentPreviewTemplate;
                                    templateApplyController2 = this.this$0;
                                    accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                    if (accessConfig2 != null) {
                                        resourceType2 = null;
                                    } else {
                                        resourceType2 = null;
                                    }
                                    if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                        CoroutineDispatcher io117 = Dispatchers.getIO();
                                        TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$17 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                        this.L$0 = objectRef11;
                                        this.L$1 = objectRef3;
                                        this.L$2 = objectRef10;
                                        this.L$3 = objectRef3;
                                        this.L$4 = null;
                                        this.label = 5;
                                        objWithContext7 = BuildersKt.withContext(io117, templateApplyController$screenshotWithCurrentPreview$1$3$17, this);
                                        if (objWithContext7 != coroutine_suspended) {
                                            objectRef16 = objectRef3;
                                            objectRef8 = objectRef11;
                                            objectRef16.element = objWithContext7;
                                            objectRef2 = objectRef3;
                                            objectRef12 = objectRef10;
                                            objectRef14 = objectRef8;
                                            objectRef13 = objectRef2;
                                        }
                                    } else {
                                        CoroutineDispatcher io118 = Dispatchers.getIO();
                                        TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$18 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                        this.L$0 = objectRef11;
                                        this.L$1 = objectRef3;
                                        this.L$2 = objectRef10;
                                        this.L$3 = objectRef3;
                                        this.L$4 = null;
                                        this.label = 6;
                                        objWithContext6 = BuildersKt.withContext(io118, templateApplyController$screenshotWithCurrentPreview$1$3$18, this);
                                        if (objWithContext6 != coroutine_suspended) {
                                            objectRef15 = objectRef3;
                                            objectRef8 = objectRef11;
                                            objectRef15.element = objWithContext6;
                                            objectRef2 = objectRef3;
                                            objectRef12 = objectRef10;
                                            objectRef14 = objectRef8;
                                            objectRef13 = objectRef2;
                                        }
                                    }
                                } else {
                                    objectRef12 = objectRef10;
                                    objectRef13 = objectRef3;
                                    objectRef14 = objectRef11;
                                }
                                DeviceScreenshotHelper deviceScreenshotHelper14 = DeviceScreenshotHelper.INSTANCE;
                                Context context17 = this.$currentPreviewView.getContext();
                                Intrinsics.checkNotNullExpressionValue(context17, "getContext(...)");
                                FrameLayout frameLayout10 = this.this$0.screenshotContainer;
                                Intrinsics.checkNotNull(frameLayout10);
                                PresetTemplateConfig presetTemplateConfig13 = this.$currentPreviewTemplate;
                                View view10 = this.$currentPreviewView;
                                final TemplateApplyController templateApplyController13 = this.this$0;
                                final ScreenshotCallback screenshotCallback10 = this.$callback;
                                deviceScreenshotHelper14.takeMultiScreenshotsForPreview(context17, frameLayout10, presetTemplateConfig13, view10, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj2) {
                                        return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController13, objectRef14, objectRef13, objectRef12, screenshotCallback10, (Screenshots) obj2);
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        }
                    }
                    return coroutine_suspended;
                case 1:
                    objectRef7 = (Ref.ObjectRef) this.L$3;
                    objectRef4 = (Ref.ObjectRef) this.L$2;
                    objectRef3 = (Ref.ObjectRef) this.L$1;
                    Ref.ObjectRef objectRef18 = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objectRef5 = objectRef18;
                    objWithContext2 = obj;
                    objectRef7.element = objWithContext2;
                    objectRef = objectRef5;
                    DeviceScreenshotHelper deviceScreenshotHelper15 = DeviceScreenshotHelper.INSTANCE;
                    Context context18 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context18, "getContext(...)");
                    screenPixelSize = deviceScreenshotHelper15.getScreenPixelSize(context18, true, 1);
                    Context context19 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context19, "getContext(...)");
                    screenPixelSize2 = deviceScreenshotHelper15.getScreenPixelSize(context19, true, 2);
                    if (screenPixelSize == null) {
                        objectRef11 = objectRef;
                        objectRef10 = objectRef4;
                        if (screenPixelSize2 != null) {
                            presetTemplateConfig2 = this.$currentPreviewTemplate;
                            templateApplyController2 = this.this$0;
                            accessConfig2 = presetTemplateConfig2.getAccessConfig();
                            if (accessConfig2 != null) {
                                resourceType2 = null;
                            } else {
                                resourceType2 = null;
                            }
                            if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                CoroutineDispatcher io119 = Dispatchers.getIO();
                                TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$19 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                this.L$0 = objectRef11;
                                this.L$1 = objectRef3;
                                this.L$2 = objectRef10;
                                this.L$3 = objectRef3;
                                this.L$4 = null;
                                this.label = 5;
                                objWithContext7 = BuildersKt.withContext(io119, templateApplyController$screenshotWithCurrentPreview$1$3$19, this);
                                if (objWithContext7 != coroutine_suspended) {
                                    objectRef16 = objectRef3;
                                    objectRef8 = objectRef11;
                                    objectRef16.element = objWithContext7;
                                    objectRef2 = objectRef3;
                                    objectRef12 = objectRef10;
                                    objectRef14 = objectRef8;
                                    objectRef13 = objectRef2;
                                }
                            } else {
                                CoroutineDispatcher io1110 = Dispatchers.getIO();
                                TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$110 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                this.L$0 = objectRef11;
                                this.L$1 = objectRef3;
                                this.L$2 = objectRef10;
                                this.L$3 = objectRef3;
                                this.L$4 = null;
                                this.label = 6;
                                objWithContext6 = BuildersKt.withContext(io1110, templateApplyController$screenshotWithCurrentPreview$1$3$110, this);
                                if (objWithContext6 != coroutine_suspended) {
                                    objectRef15 = objectRef3;
                                    objectRef8 = objectRef11;
                                    objectRef15.element = objWithContext6;
                                    objectRef2 = objectRef3;
                                    objectRef12 = objectRef10;
                                    objectRef14 = objectRef8;
                                    objectRef13 = objectRef2;
                                }
                            }
                        } else {
                            objectRef12 = objectRef10;
                            objectRef13 = objectRef3;
                            objectRef14 = objectRef11;
                        }
                        DeviceScreenshotHelper deviceScreenshotHelper16 = DeviceScreenshotHelper.INSTANCE;
                        Context context110 = this.$currentPreviewView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context110, "getContext(...)");
                        FrameLayout frameLayout11 = this.this$0.screenshotContainer;
                        Intrinsics.checkNotNull(frameLayout11);
                        PresetTemplateConfig presetTemplateConfig14 = this.$currentPreviewTemplate;
                        View view11 = this.$currentPreviewView;
                        final TemplateApplyController templateApplyController14 = this.this$0;
                        final ScreenshotCallback screenshotCallback11 = this.$callback;
                        deviceScreenshotHelper16.takeMultiScreenshotsForPreview(context110, frameLayout11, presetTemplateConfig14, view11, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController14, objectRef14, objectRef13, objectRef12, screenshotCallback11, (Screenshots) obj2);
                            }
                        });
                        return Unit.INSTANCE;
                    }
                    presetTemplateConfig = this.$currentPreviewTemplate;
                    templateApplyController = this.this$0;
                    accessConfig = presetTemplateConfig.getAccessConfig();
                    if (accessConfig != null) {
                        resourceType = null;
                    } else {
                        resourceType = null;
                    }
                    if (Intrinsics.areEqual(resourceType, "sensor")) {
                        CoroutineDispatcher io1111 = Dispatchers.getIO();
                        TemplateApplyController$screenshotWithCurrentPreview$1$2$1 templateApplyController$screenshotWithCurrentPreview$1$2$7 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$1(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                        this.L$0 = objectRef;
                        this.L$1 = objectRef3;
                        this.L$2 = objectRef4;
                        this.L$3 = screenPixelSize2;
                        this.L$4 = objectRef;
                        this.label = 3;
                        objWithContext5 = BuildersKt.withContext(io1111, templateApplyController$screenshotWithCurrentPreview$1$2$7, this);
                        if (objWithContext5 != coroutine_suspended) {
                            objectRef11 = objectRef;
                            objectRef.element = objWithContext5;
                            objectRef10 = objectRef4;
                            if (screenPixelSize2 != null) {
                                presetTemplateConfig2 = this.$currentPreviewTemplate;
                                templateApplyController2 = this.this$0;
                                accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                if (accessConfig2 != null) {
                                    resourceType2 = null;
                                } else {
                                    resourceType2 = null;
                                }
                                if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                    CoroutineDispatcher io1112 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$111 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 5;
                                    objWithContext7 = BuildersKt.withContext(io1112, templateApplyController$screenshotWithCurrentPreview$1$3$111, this);
                                    if (objWithContext7 != coroutine_suspended) {
                                        objectRef16 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef16.element = objWithContext7;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                } else {
                                    CoroutineDispatcher io1113 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$112 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 6;
                                    objWithContext6 = BuildersKt.withContext(io1113, templateApplyController$screenshotWithCurrentPreview$1$3$112, this);
                                    if (objWithContext6 != coroutine_suspended) {
                                        objectRef15 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef15.element = objWithContext6;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                }
                            } else {
                                objectRef12 = objectRef10;
                                objectRef13 = objectRef3;
                                objectRef14 = objectRef11;
                            }
                            DeviceScreenshotHelper deviceScreenshotHelper17 = DeviceScreenshotHelper.INSTANCE;
                            Context context111 = this.$currentPreviewView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context111, "getContext(...)");
                            FrameLayout frameLayout12 = this.this$0.screenshotContainer;
                            Intrinsics.checkNotNull(frameLayout12);
                            PresetTemplateConfig presetTemplateConfig15 = this.$currentPreviewTemplate;
                            View view12 = this.$currentPreviewView;
                            final TemplateApplyController templateApplyController15 = this.this$0;
                            final ScreenshotCallback screenshotCallback12 = this.$callback;
                            deviceScreenshotHelper17.takeMultiScreenshotsForPreview(context111, frameLayout12, presetTemplateConfig15, view12, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController15, objectRef14, objectRef13, objectRef12, screenshotCallback12, (Screenshots) obj2);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    } else {
                        CoroutineDispatcher io1114 = Dispatchers.getIO();
                        TemplateApplyController$screenshotWithCurrentPreview$1$2$2 templateApplyController$screenshotWithCurrentPreview$1$2$8 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$2(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                        this.L$0 = objectRef;
                        this.L$1 = objectRef3;
                        this.L$2 = objectRef4;
                        this.L$3 = screenPixelSize2;
                        this.L$4 = objectRef;
                        this.label = 4;
                        objWithContext4 = BuildersKt.withContext(io1114, templateApplyController$screenshotWithCurrentPreview$1$2$8, this);
                        if (objWithContext4 != coroutine_suspended) {
                            objectRef11 = objectRef;
                            objectRef.element = objWithContext4;
                            objectRef10 = objectRef4;
                            if (screenPixelSize2 != null) {
                                presetTemplateConfig2 = this.$currentPreviewTemplate;
                                templateApplyController2 = this.this$0;
                                accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                if (accessConfig2 != null) {
                                    resourceType2 = null;
                                } else {
                                    resourceType2 = null;
                                }
                                if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                    CoroutineDispatcher io1115 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$113 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 5;
                                    objWithContext7 = BuildersKt.withContext(io1115, templateApplyController$screenshotWithCurrentPreview$1$3$113, this);
                                    if (objWithContext7 != coroutine_suspended) {
                                        objectRef16 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef16.element = objWithContext7;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                } else {
                                    CoroutineDispatcher io1116 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$114 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 6;
                                    objWithContext6 = BuildersKt.withContext(io1116, templateApplyController$screenshotWithCurrentPreview$1$3$114, this);
                                    if (objWithContext6 != coroutine_suspended) {
                                        objectRef15 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef15.element = objWithContext6;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                }
                            } else {
                                objectRef12 = objectRef10;
                                objectRef13 = objectRef3;
                                objectRef14 = objectRef11;
                            }
                            DeviceScreenshotHelper deviceScreenshotHelper18 = DeviceScreenshotHelper.INSTANCE;
                            Context context112 = this.$currentPreviewView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context112, "getContext(...)");
                            FrameLayout frameLayout13 = this.this$0.screenshotContainer;
                            Intrinsics.checkNotNull(frameLayout13);
                            PresetTemplateConfig presetTemplateConfig16 = this.$currentPreviewTemplate;
                            View view13 = this.$currentPreviewView;
                            final TemplateApplyController templateApplyController16 = this.this$0;
                            final ScreenshotCallback screenshotCallback13 = this.$callback;
                            deviceScreenshotHelper18.takeMultiScreenshotsForPreview(context112, frameLayout13, presetTemplateConfig16, view13, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController16, objectRef14, objectRef13, objectRef12, screenshotCallback13, (Screenshots) obj2);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    }
                    return coroutine_suspended;
                case 2:
                    objectRef6 = (Ref.ObjectRef) this.L$3;
                    objectRef4 = (Ref.ObjectRef) this.L$2;
                    objectRef3 = (Ref.ObjectRef) this.L$1;
                    Ref.ObjectRef objectRef19 = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objectRef5 = objectRef19;
                    objWithContext = obj;
                    objectRef6.element = objWithContext;
                    objectRef = objectRef5;
                    DeviceScreenshotHelper deviceScreenshotHelper19 = DeviceScreenshotHelper.INSTANCE;
                    Context context113 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context113, "getContext(...)");
                    screenPixelSize = deviceScreenshotHelper19.getScreenPixelSize(context113, true, 1);
                    Context context114 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context114, "getContext(...)");
                    screenPixelSize2 = deviceScreenshotHelper19.getScreenPixelSize(context114, true, 2);
                    if (screenPixelSize == null) {
                        objectRef11 = objectRef;
                        objectRef10 = objectRef4;
                        if (screenPixelSize2 != null) {
                            presetTemplateConfig2 = this.$currentPreviewTemplate;
                            templateApplyController2 = this.this$0;
                            accessConfig2 = presetTemplateConfig2.getAccessConfig();
                            if (accessConfig2 != null) {
                                resourceType2 = null;
                            } else {
                                resourceType2 = null;
                            }
                            if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                CoroutineDispatcher io1117 = Dispatchers.getIO();
                                TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$115 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                this.L$0 = objectRef11;
                                this.L$1 = objectRef3;
                                this.L$2 = objectRef10;
                                this.L$3 = objectRef3;
                                this.L$4 = null;
                                this.label = 5;
                                objWithContext7 = BuildersKt.withContext(io1117, templateApplyController$screenshotWithCurrentPreview$1$3$115, this);
                                if (objWithContext7 != coroutine_suspended) {
                                    objectRef16 = objectRef3;
                                    objectRef8 = objectRef11;
                                    objectRef16.element = objWithContext7;
                                    objectRef2 = objectRef3;
                                    objectRef12 = objectRef10;
                                    objectRef14 = objectRef8;
                                    objectRef13 = objectRef2;
                                }
                            } else {
                                CoroutineDispatcher io1118 = Dispatchers.getIO();
                                TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$116 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                this.L$0 = objectRef11;
                                this.L$1 = objectRef3;
                                this.L$2 = objectRef10;
                                this.L$3 = objectRef3;
                                this.L$4 = null;
                                this.label = 6;
                                objWithContext6 = BuildersKt.withContext(io1118, templateApplyController$screenshotWithCurrentPreview$1$3$116, this);
                                if (objWithContext6 != coroutine_suspended) {
                                    objectRef15 = objectRef3;
                                    objectRef8 = objectRef11;
                                    objectRef15.element = objWithContext6;
                                    objectRef2 = objectRef3;
                                    objectRef12 = objectRef10;
                                    objectRef14 = objectRef8;
                                    objectRef13 = objectRef2;
                                }
                            }
                        } else {
                            objectRef12 = objectRef10;
                            objectRef13 = objectRef3;
                            objectRef14 = objectRef11;
                        }
                        DeviceScreenshotHelper deviceScreenshotHelper110 = DeviceScreenshotHelper.INSTANCE;
                        Context context115 = this.$currentPreviewView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context115, "getContext(...)");
                        FrameLayout frameLayout14 = this.this$0.screenshotContainer;
                        Intrinsics.checkNotNull(frameLayout14);
                        PresetTemplateConfig presetTemplateConfig17 = this.$currentPreviewTemplate;
                        View view14 = this.$currentPreviewView;
                        final TemplateApplyController templateApplyController17 = this.this$0;
                        final ScreenshotCallback screenshotCallback14 = this.$callback;
                        deviceScreenshotHelper110.takeMultiScreenshotsForPreview(context115, frameLayout14, presetTemplateConfig17, view14, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController17, objectRef14, objectRef13, objectRef12, screenshotCallback14, (Screenshots) obj2);
                            }
                        });
                        return Unit.INSTANCE;
                    }
                    presetTemplateConfig = this.$currentPreviewTemplate;
                    templateApplyController = this.this$0;
                    accessConfig = presetTemplateConfig.getAccessConfig();
                    if (accessConfig != null) {
                        resourceType = null;
                    } else {
                        resourceType = null;
                    }
                    if (Intrinsics.areEqual(resourceType, "sensor")) {
                        CoroutineDispatcher io1119 = Dispatchers.getIO();
                        TemplateApplyController$screenshotWithCurrentPreview$1$2$1 templateApplyController$screenshotWithCurrentPreview$1$2$9 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$1(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                        this.L$0 = objectRef;
                        this.L$1 = objectRef3;
                        this.L$2 = objectRef4;
                        this.L$3 = screenPixelSize2;
                        this.L$4 = objectRef;
                        this.label = 3;
                        objWithContext5 = BuildersKt.withContext(io1119, templateApplyController$screenshotWithCurrentPreview$1$2$9, this);
                        if (objWithContext5 != coroutine_suspended) {
                            objectRef11 = objectRef;
                            objectRef.element = objWithContext5;
                            objectRef10 = objectRef4;
                            if (screenPixelSize2 != null) {
                                presetTemplateConfig2 = this.$currentPreviewTemplate;
                                templateApplyController2 = this.this$0;
                                accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                if (accessConfig2 != null) {
                                    resourceType2 = null;
                                } else {
                                    resourceType2 = null;
                                }
                                if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                    CoroutineDispatcher io11110 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$117 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 5;
                                    objWithContext7 = BuildersKt.withContext(io11110, templateApplyController$screenshotWithCurrentPreview$1$3$117, this);
                                    if (objWithContext7 != coroutine_suspended) {
                                        objectRef16 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef16.element = objWithContext7;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                } else {
                                    CoroutineDispatcher io11111 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$118 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 6;
                                    objWithContext6 = BuildersKt.withContext(io11111, templateApplyController$screenshotWithCurrentPreview$1$3$118, this);
                                    if (objWithContext6 != coroutine_suspended) {
                                        objectRef15 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef15.element = objWithContext6;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                }
                            } else {
                                objectRef12 = objectRef10;
                                objectRef13 = objectRef3;
                                objectRef14 = objectRef11;
                            }
                            DeviceScreenshotHelper deviceScreenshotHelper111 = DeviceScreenshotHelper.INSTANCE;
                            Context context116 = this.$currentPreviewView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context116, "getContext(...)");
                            FrameLayout frameLayout15 = this.this$0.screenshotContainer;
                            Intrinsics.checkNotNull(frameLayout15);
                            PresetTemplateConfig presetTemplateConfig18 = this.$currentPreviewTemplate;
                            View view15 = this.$currentPreviewView;
                            final TemplateApplyController templateApplyController18 = this.this$0;
                            final ScreenshotCallback screenshotCallback15 = this.$callback;
                            deviceScreenshotHelper111.takeMultiScreenshotsForPreview(context116, frameLayout15, presetTemplateConfig18, view15, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController18, objectRef14, objectRef13, objectRef12, screenshotCallback15, (Screenshots) obj2);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    } else {
                        CoroutineDispatcher io11112 = Dispatchers.getIO();
                        TemplateApplyController$screenshotWithCurrentPreview$1$2$2 templateApplyController$screenshotWithCurrentPreview$1$2$10 = new TemplateApplyController$screenshotWithCurrentPreview$1$2$2(presetTemplateConfig, screenPixelSize, templateApplyController, null);
                        this.L$0 = objectRef;
                        this.L$1 = objectRef3;
                        this.L$2 = objectRef4;
                        this.L$3 = screenPixelSize2;
                        this.L$4 = objectRef;
                        this.label = 4;
                        objWithContext4 = BuildersKt.withContext(io11112, templateApplyController$screenshotWithCurrentPreview$1$2$10, this);
                        if (objWithContext4 != coroutine_suspended) {
                            objectRef11 = objectRef;
                            objectRef.element = objWithContext4;
                            objectRef10 = objectRef4;
                            if (screenPixelSize2 != null) {
                                presetTemplateConfig2 = this.$currentPreviewTemplate;
                                templateApplyController2 = this.this$0;
                                accessConfig2 = presetTemplateConfig2.getAccessConfig();
                                if (accessConfig2 != null) {
                                    resourceType2 = null;
                                } else {
                                    resourceType2 = null;
                                }
                                if (Intrinsics.areEqual(resourceType2, "sensor")) {
                                    CoroutineDispatcher io11113 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$119 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 5;
                                    objWithContext7 = BuildersKt.withContext(io11113, templateApplyController$screenshotWithCurrentPreview$1$3$119, this);
                                    if (objWithContext7 != coroutine_suspended) {
                                        objectRef16 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef16.element = objWithContext7;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                } else {
                                    CoroutineDispatcher io11114 = Dispatchers.getIO();
                                    TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$1110 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                                    this.L$0 = objectRef11;
                                    this.L$1 = objectRef3;
                                    this.L$2 = objectRef10;
                                    this.L$3 = objectRef3;
                                    this.L$4 = null;
                                    this.label = 6;
                                    objWithContext6 = BuildersKt.withContext(io11114, templateApplyController$screenshotWithCurrentPreview$1$3$1110, this);
                                    if (objWithContext6 != coroutine_suspended) {
                                        objectRef15 = objectRef3;
                                        objectRef8 = objectRef11;
                                        objectRef15.element = objWithContext6;
                                        objectRef2 = objectRef3;
                                        objectRef12 = objectRef10;
                                        objectRef14 = objectRef8;
                                        objectRef13 = objectRef2;
                                    }
                                }
                            } else {
                                objectRef12 = objectRef10;
                                objectRef13 = objectRef3;
                                objectRef14 = objectRef11;
                            }
                            DeviceScreenshotHelper deviceScreenshotHelper112 = DeviceScreenshotHelper.INSTANCE;
                            Context context117 = this.$currentPreviewView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context117, "getContext(...)");
                            FrameLayout frameLayout16 = this.this$0.screenshotContainer;
                            Intrinsics.checkNotNull(frameLayout16);
                            PresetTemplateConfig presetTemplateConfig19 = this.$currentPreviewTemplate;
                            View view16 = this.$currentPreviewView;
                            final TemplateApplyController templateApplyController19 = this.this$0;
                            final ScreenshotCallback screenshotCallback16 = this.$callback;
                            deviceScreenshotHelper112.takeMultiScreenshotsForPreview(context117, frameLayout16, presetTemplateConfig19, view16, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController19, objectRef14, objectRef13, objectRef12, screenshotCallback16, (Screenshots) obj2);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    }
                    return coroutine_suspended;
                case 3:
                    objectRef = (Ref.ObjectRef) this.L$4;
                    screenPixelSize2 = (Point) this.L$3;
                    Ref.ObjectRef objectRef20 = (Ref.ObjectRef) this.L$2;
                    Ref.ObjectRef objectRef21 = (Ref.ObjectRef) this.L$1;
                    Ref.ObjectRef objectRef22 = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objectRef11 = objectRef22;
                    objectRef3 = objectRef21;
                    objectRef4 = objectRef20;
                    objWithContext5 = obj;
                    objectRef.element = objWithContext5;
                    objectRef10 = objectRef4;
                    if (screenPixelSize2 != null) {
                        presetTemplateConfig2 = this.$currentPreviewTemplate;
                        templateApplyController2 = this.this$0;
                        accessConfig2 = presetTemplateConfig2.getAccessConfig();
                        if (accessConfig2 != null) {
                            resourceType2 = null;
                        } else {
                            resourceType2 = null;
                        }
                        if (Intrinsics.areEqual(resourceType2, "sensor")) {
                            CoroutineDispatcher io11115 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$1111 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                            this.L$0 = objectRef11;
                            this.L$1 = objectRef3;
                            this.L$2 = objectRef10;
                            this.L$3 = objectRef3;
                            this.L$4 = null;
                            this.label = 5;
                            objWithContext7 = BuildersKt.withContext(io11115, templateApplyController$screenshotWithCurrentPreview$1$3$1111, this);
                            if (objWithContext7 != coroutine_suspended) {
                                objectRef16 = objectRef3;
                                objectRef8 = objectRef11;
                                objectRef16.element = objWithContext7;
                                objectRef2 = objectRef3;
                                objectRef12 = objectRef10;
                                objectRef14 = objectRef8;
                                objectRef13 = objectRef2;
                            }
                        } else {
                            CoroutineDispatcher io11116 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$1112 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                            this.L$0 = objectRef11;
                            this.L$1 = objectRef3;
                            this.L$2 = objectRef10;
                            this.L$3 = objectRef3;
                            this.L$4 = null;
                            this.label = 6;
                            objWithContext6 = BuildersKt.withContext(io11116, templateApplyController$screenshotWithCurrentPreview$1$3$1112, this);
                            if (objWithContext6 != coroutine_suspended) {
                                objectRef15 = objectRef3;
                                objectRef8 = objectRef11;
                                objectRef15.element = objWithContext6;
                                objectRef2 = objectRef3;
                                objectRef12 = objectRef10;
                                objectRef14 = objectRef8;
                                objectRef13 = objectRef2;
                            }
                        }
                        return coroutine_suspended;
                    }
                    objectRef12 = objectRef10;
                    objectRef13 = objectRef3;
                    objectRef14 = objectRef11;
                    DeviceScreenshotHelper deviceScreenshotHelper113 = DeviceScreenshotHelper.INSTANCE;
                    Context context118 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context118, "getContext(...)");
                    FrameLayout frameLayout17 = this.this$0.screenshotContainer;
                    Intrinsics.checkNotNull(frameLayout17);
                    PresetTemplateConfig presetTemplateConfig110 = this.$currentPreviewTemplate;
                    View view17 = this.$currentPreviewView;
                    final TemplateApplyController templateApplyController110 = this.this$0;
                    final ScreenshotCallback screenshotCallback17 = this.$callback;
                    deviceScreenshotHelper113.takeMultiScreenshotsForPreview(context118, frameLayout17, presetTemplateConfig110, view17, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController110, objectRef14, objectRef13, objectRef12, screenshotCallback17, (Screenshots) obj2);
                        }
                    });
                    return Unit.INSTANCE;
                case 4:
                    objectRef = (Ref.ObjectRef) this.L$4;
                    screenPixelSize2 = (Point) this.L$3;
                    Ref.ObjectRef objectRef23 = (Ref.ObjectRef) this.L$2;
                    Ref.ObjectRef objectRef24 = (Ref.ObjectRef) this.L$1;
                    Ref.ObjectRef objectRef25 = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objectRef11 = objectRef25;
                    objectRef3 = objectRef24;
                    objectRef4 = objectRef23;
                    objWithContext4 = obj;
                    objectRef.element = objWithContext4;
                    objectRef10 = objectRef4;
                    if (screenPixelSize2 != null) {
                        presetTemplateConfig2 = this.$currentPreviewTemplate;
                        templateApplyController2 = this.this$0;
                        accessConfig2 = presetTemplateConfig2.getAccessConfig();
                        if (accessConfig2 != null) {
                            resourceType2 = null;
                        } else {
                            resourceType2 = null;
                        }
                        if (Intrinsics.areEqual(resourceType2, "sensor")) {
                            CoroutineDispatcher io11117 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$3$1 templateApplyController$screenshotWithCurrentPreview$1$3$1113 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$1(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                            this.L$0 = objectRef11;
                            this.L$1 = objectRef3;
                            this.L$2 = objectRef10;
                            this.L$3 = objectRef3;
                            this.L$4 = null;
                            this.label = 5;
                            objWithContext7 = BuildersKt.withContext(io11117, templateApplyController$screenshotWithCurrentPreview$1$3$1113, this);
                            if (objWithContext7 != coroutine_suspended) {
                                objectRef16 = objectRef3;
                                objectRef8 = objectRef11;
                                objectRef16.element = objWithContext7;
                                objectRef2 = objectRef3;
                                objectRef12 = objectRef10;
                                objectRef14 = objectRef8;
                                objectRef13 = objectRef2;
                            }
                        } else {
                            CoroutineDispatcher io11118 = Dispatchers.getIO();
                            TemplateApplyController$screenshotWithCurrentPreview$1$3$2 templateApplyController$screenshotWithCurrentPreview$1$3$1114 = new TemplateApplyController$screenshotWithCurrentPreview$1$3$2(presetTemplateConfig2, screenPixelSize2, templateApplyController2, null);
                            this.L$0 = objectRef11;
                            this.L$1 = objectRef3;
                            this.L$2 = objectRef10;
                            this.L$3 = objectRef3;
                            this.L$4 = null;
                            this.label = 6;
                            objWithContext6 = BuildersKt.withContext(io11118, templateApplyController$screenshotWithCurrentPreview$1$3$1114, this);
                            if (objWithContext6 != coroutine_suspended) {
                                objectRef15 = objectRef3;
                                objectRef8 = objectRef11;
                                objectRef15.element = objWithContext6;
                                objectRef2 = objectRef3;
                                objectRef12 = objectRef10;
                                objectRef14 = objectRef8;
                                objectRef13 = objectRef2;
                            }
                        }
                        return coroutine_suspended;
                    }
                    objectRef12 = objectRef10;
                    objectRef13 = objectRef3;
                    objectRef14 = objectRef11;
                    DeviceScreenshotHelper deviceScreenshotHelper114 = DeviceScreenshotHelper.INSTANCE;
                    Context context119 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context119, "getContext(...)");
                    FrameLayout frameLayout18 = this.this$0.screenshotContainer;
                    Intrinsics.checkNotNull(frameLayout18);
                    PresetTemplateConfig presetTemplateConfig111 = this.$currentPreviewTemplate;
                    View view18 = this.$currentPreviewView;
                    final TemplateApplyController templateApplyController111 = this.this$0;
                    final ScreenshotCallback screenshotCallback18 = this.$callback;
                    deviceScreenshotHelper114.takeMultiScreenshotsForPreview(context119, frameLayout18, presetTemplateConfig111, view18, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController111, objectRef14, objectRef13, objectRef12, screenshotCallback18, (Screenshots) obj2);
                        }
                    });
                    return Unit.INSTANCE;
                case 5:
                    objectRef16 = (Ref.ObjectRef) this.L$3;
                    objectRef10 = (Ref.ObjectRef) this.L$2;
                    Ref.ObjectRef objectRef26 = (Ref.ObjectRef) this.L$1;
                    objectRef8 = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objectRef3 = objectRef26;
                    objWithContext7 = obj;
                    objectRef16.element = objWithContext7;
                    objectRef2 = objectRef3;
                    objectRef12 = objectRef10;
                    objectRef14 = objectRef8;
                    objectRef13 = objectRef2;
                    DeviceScreenshotHelper deviceScreenshotHelper115 = DeviceScreenshotHelper.INSTANCE;
                    Context context1110 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context1110, "getContext(...)");
                    FrameLayout frameLayout19 = this.this$0.screenshotContainer;
                    Intrinsics.checkNotNull(frameLayout19);
                    PresetTemplateConfig presetTemplateConfig112 = this.$currentPreviewTemplate;
                    View view19 = this.$currentPreviewView;
                    final TemplateApplyController templateApplyController112 = this.this$0;
                    final ScreenshotCallback screenshotCallback19 = this.$callback;
                    deviceScreenshotHelper115.takeMultiScreenshotsForPreview(context1110, frameLayout19, presetTemplateConfig112, view19, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController112, objectRef14, objectRef13, objectRef12, screenshotCallback19, (Screenshots) obj2);
                        }
                    });
                    return Unit.INSTANCE;
                case 6:
                    objectRef15 = (Ref.ObjectRef) this.L$3;
                    objectRef10 = (Ref.ObjectRef) this.L$2;
                    Ref.ObjectRef objectRef27 = (Ref.ObjectRef) this.L$1;
                    objectRef8 = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objectRef3 = objectRef27;
                    objWithContext6 = obj;
                    objectRef15.element = objWithContext6;
                    objectRef2 = objectRef3;
                    objectRef12 = objectRef10;
                    objectRef14 = objectRef8;
                    objectRef13 = objectRef2;
                    DeviceScreenshotHelper deviceScreenshotHelper116 = DeviceScreenshotHelper.INSTANCE;
                    Context context1111 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context1111, "getContext(...)");
                    FrameLayout frameLayout110 = this.this$0.screenshotContainer;
                    Intrinsics.checkNotNull(frameLayout110);
                    PresetTemplateConfig presetTemplateConfig113 = this.$currentPreviewTemplate;
                    View view110 = this.$currentPreviewView;
                    final TemplateApplyController templateApplyController113 = this.this$0;
                    final ScreenshotCallback screenshotCallback110 = this.$callback;
                    deviceScreenshotHelper116.takeMultiScreenshotsForPreview(context1111, frameLayout110, presetTemplateConfig113, view110, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController113, objectRef14, objectRef13, objectRef12, screenshotCallback110, (Screenshots) obj2);
                        }
                    });
                    return Unit.INSTANCE;
                case 7:
                    objectRef9 = (Ref.ObjectRef) this.L$3;
                    objectRef10 = (Ref.ObjectRef) this.L$2;
                    Ref.ObjectRef objectRef28 = (Ref.ObjectRef) this.L$1;
                    objectRef8 = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objectRef2 = objectRef28;
                    objWithContext3 = obj;
                    objectRef9.element = objWithContext3;
                    objectRef12 = objectRef10;
                    objectRef14 = objectRef8;
                    objectRef13 = objectRef2;
                    DeviceScreenshotHelper deviceScreenshotHelper117 = DeviceScreenshotHelper.INSTANCE;
                    Context context1112 = this.$currentPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context1112, "getContext(...)");
                    FrameLayout frameLayout111 = this.this$0.screenshotContainer;
                    Intrinsics.checkNotNull(frameLayout111);
                    PresetTemplateConfig presetTemplateConfig114 = this.$currentPreviewTemplate;
                    View view111 = this.$currentPreviewView;
                    final TemplateApplyController templateApplyController114 = this.this$0;
                    final ScreenshotCallback screenshotCallback111 = this.$callback;
                    deviceScreenshotHelper117.takeMultiScreenshotsForPreview(context1112, frameLayout111, presetTemplateConfig114, view111, new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$screenshotWithCurrentPreview$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return TemplateApplyController.C01261.invokeSuspend$lambda$3(templateApplyController114, objectRef14, objectRef13, objectRef12, screenshotCallback111, (Screenshots) obj2);
                        }
                    });
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.TemplateApplyController$showAllAskDialog$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplyController.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$showAllAskDialog$1", f = "TemplateApplyController.kt", l = {}, m = "invokeSuspend")
    public static final class C01271 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isGalleryOpened;
        public final /* synthetic */ boolean $isPreset;
        public final /* synthetic */ ScreenshotSource $screenshotSource;
        public final /* synthetic */ TemplateItemBean $templateBean;
        public final /* synthetic */ CommonConfig $templateConfig;
        public int label;
        public final /* synthetic */ TemplateApplyController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01271(CommonConfig commonConfig, TemplateApplyController templateApplyController, boolean z, TemplateItemBean templateItemBean, boolean z2, ScreenshotSource screenshotSource, Continuation continuation) {
            super(2, continuation);
            this.$templateConfig = commonConfig;
            this.this$0 = templateApplyController;
            this.$isPreset = z;
            this.$templateBean = templateItemBean;
            this.$isGalleryOpened = z2;
            this.$screenshotSource = screenshotSource;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01271(this.$templateConfig, this.this$0, this.$isPreset, this.$templateBean, this.$isGalleryOpened, this.$screenshotSource, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01271) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            TemplateConfig lockscreenInfo;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommonConfig commonConfig = this.$templateConfig;
            if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
                lockscreenInfo.setApplyToDeskTop(true);
            }
            this.this$0.applyPresetOrHistoryTemplate(this.$isPreset, this.$templateBean, true, this.$isGalleryOpened, false, this.$screenshotSource);
            return Unit.INSTANCE;
        }
    }

    public TemplateApplyController(FragmentActivity activity, SelectItemProvider selectItemProvider) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        this.mSelectItemProvider = selectItemProvider;
        this.TAG = "Keyguard-Editor-TemplateApplyController";
        this.resources = activity.getResources();
        this.applicationContext = activity.getApplicationContext();
    }

    public static final void applyHistoryTemplate$lambda$22(TemplateApplyController templateApplyController, String str, ScreenshotSource screenshotSource, long j, boolean z, boolean z2, boolean z3, int i, DialogInterface dialogInterface, int i2) {
        templateApplyController.checkOrAskAodApply(str, false, screenshotSource, j, i2 == -2, i2 == -1, z, z2, z3, i);
    }

    public static final Unit applyHistoryTemplateByProvider$lambda$1(TemplateApplyController templateApplyController, Throwable th) {
        Log.d(templateApplyController.TAG, "finish main apply:" + th + " " + (th != null ? th.getCause() : null) + " " + (th != null ? th.getMessage() : null));
        return Unit.INSTANCE;
    }

    public static final void applyPresetTemplate$lambda$23(TemplateApplyController templateApplyController, String str, ScreenshotSource screenshotSource, boolean z, boolean z2, boolean z3, int i, DialogInterface dialogInterface, int i2) {
        checkOrAskAodApply$default(templateApplyController, str, true, screenshotSource, 0L, i2 == -2, i2 == -1, z, z2, z3, i, 8, null);
    }

    public static /* synthetic */ int applyTemplate$default(TemplateApplyController templateApplyController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return templateApplyController.applyTemplate(z);
    }

    public static final Boolean checkOrApplyCurrentTemplate$lambda$4(TemplateApplyController templateApplyController) {
        TemplateApi.Companion companion = TemplateApi.Companion;
        Context applicationContext = templateApplyController.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        return Boolean.valueOf(companion.getInstance(applicationContext).isThirdPartyTheme());
    }

    public static final void checkOrApplyCurrentTemplate$lambda$7(final TemplateApplyController templateApplyController, final TemplateItemBean templateItemBean, Boolean bool) {
        if (!bool.booleanValue()) {
            templateApplyController.currentTemplateApplyCompleted(templateItemBean, false);
        } else {
            templateApplyController.actionBeforeScheduleApply();
            Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final Object get() {
                    return TemplateApplyController.checkOrApplyCurrentTemplate$lambda$7$lambda$5(this.f$0, templateItemBean);
                }
            }).post(new java.util.function.Consumer() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.currentTemplateApplyCompleted(templateItemBean, true);
                }
            });
        }
    }

    public static final Unit checkOrApplyCurrentTemplate$lambda$7$lambda$5(TemplateApplyController templateApplyController, TemplateItemBean templateItemBean) {
        TemplateConfig lockscreenInfo;
        SignatureInfo signatureInfo;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo2;
        HomeConfig homeInfo;
        TemplateConfig lockscreenInfo4;
        TemplateApi.Companion companion = TemplateApi.Companion;
        Context applicationContext = templateApplyController.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        TemplateApi companion2 = companion.getInstance(applicationContext);
        CommonConfig current = templateItemBean.getCurrent();
        Integer numValueOf = null;
        WallpaperInfo wallpaperInfo = (current == null || (lockscreenInfo4 = current.getLockscreenInfo()) == null) ? null : lockscreenInfo4.getWallpaperInfo();
        CommonConfig current2 = templateItemBean.getCurrent();
        WallpaperInfo wallpaperInfo2 = (current2 == null || (homeInfo = current2.getHomeInfo()) == null) ? null : homeInfo.getWallpaperInfo();
        WallpaperController.Companion companion3 = WallpaperController.Companion;
        CommonConfig current3 = templateItemBean.getCurrent();
        String templateId = (current3 == null || (lockscreenInfo3 = current3.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo2.getTemplateId();
        CommonConfig current4 = templateItemBean.getCurrent();
        Integer numValueOf2 = (current4 == null || (lockscreenInfo2 = current4.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo2.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo.getStyle());
        CommonConfig current5 = templateItemBean.getCurrent();
        if (current5 != null && (lockscreenInfo = current5.getLockscreenInfo()) != null && (signatureInfo = lockscreenInfo.getSignatureInfo()) != null) {
            numValueOf = Integer.valueOf(signatureInfo.getAlignment());
        }
        companion2.reapplyCurrentWallpaper(wallpaperInfo, wallpaperInfo2, companion3.getPickWallpaperColorInfo(templateId, numValueOf2, numValueOf));
        return Unit.INSTANCE;
    }

    public static final Unit checkOrApplyCurrentTemplateByProvider$lambda$8(TemplateApplyController templateApplyController, Throwable th) {
        Log.d(templateApplyController.TAG, "finish main apply current template :" + th + " " + (th != null ? th.getCause() : null) + " " + (th != null ? th.getMessage() : null));
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void checkOrAskAodApply$default(TemplateApplyController templateApplyController, String str, boolean z, ScreenshotSource screenshotSource, long j, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, int i2, Object obj) {
        templateApplyController.checkOrAskAodApply(str, z, screenshotSource, (i2 & 8) != 0 ? 0L : j, (i2 & 16) != 0 ? false : z2, (i2 & 32) != 0 ? false : z3, z4, z5, z6, i);
    }

    public static final Pair checkOrAskAodApply$lambda$24(boolean z, TemplateApplyController templateApplyController, String str, boolean z2, int i, boolean z3) throws Throwable {
        if (z) {
            Context applicationContext = templateApplyController.applicationContext;
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            boolean zCloseDualClock = DualClockManager.closeDualClock(applicationContext);
            Log.i(templateApplyController.TAG, "checkOrAskAodApply closeDualClock:  " + zCloseDualClock);
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context applicationContext2 = templateApplyController.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "applicationContext");
        boolean zSupportAod = deviceUtil.supportAod(applicationContext2);
        Context applicationContext3 = templateApplyController.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext3, "applicationContext");
        boolean zIsAodEnabled = deviceUtil.isAodEnabled(applicationContext3);
        EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(templateApplyController.activity);
        Log.i(templateApplyController.TAG, "checkOrAskAodApply isAodEnabled = " + zIsAodEnabled);
        CommonConfig applyTemplateConfig = null;
        if ((viewModel != null ? viewModel.getApplyTemplateConfig() : null) == null) {
            Log.d(templateApplyController.TAG, "load config for aod dialog");
            try {
                applyTemplateConfig = TemplateDataUtil.getTemplateConfig(templateApplyController.activity, str, z2, i);
            } catch (Exception e) {
                Log.e(templateApplyController.TAG, "checkOrAskAodApply@getTemplate failed: " + e);
            }
        } else {
            Log.d(templateApplyController.TAG, "use cache config for aod dialog");
            applyTemplateConfig = viewModel.getApplyTemplateConfig();
        }
        return new Pair(Boolean.valueOf(!zIsAodEnabled && zSupportAod && z3), applyTemplateConfig);
    }

    public static final void checkOrAskAodApply$lambda$25(TemplateApplyController templateApplyController, String str, boolean z, ScreenshotSource screenshotSource, long j, boolean z2, boolean z3, int i, boolean z4, Pair pair) {
        if (!((Boolean) pair.getFirst()).booleanValue()) {
            templateApplyController.applyAfterScreenshot((CommonConfig) pair.getSecond(), str, z, screenshotSource, j, z2, z3, i);
            return;
        }
        CommonConfig commonConfig = (CommonConfig) pair.getSecond();
        if (commonConfig != null) {
            templateApplyController.showAodApplyAskDialog(commonConfig, str, z, screenshotSource, j, z2, z3, z4, i);
        } else {
            Log.w(templateApplyController.TAG, "checkOrAskAodApply(isAodEnabled: false) failed: templateConfig = null");
            setApplying$default(templateApplyController, false, false, 2, null);
        }
    }

    public static final Unit completeApplyTemplate$lambda$27(TemplateApplyController templateApplyController, Throwable th) {
        Log.d(templateApplyController.TAG, "finish main apply:" + th + " " + (th != null ? th.getCause() : null) + " " + (th != null ? th.getMessage() : null));
        return Unit.INSTANCE;
    }

    public static final Unit createAndShowDownloadDialog$lambda$11(TemplateApplyController templateApplyController, TemplateItemBean templateItemBean, DialogInterface dialogInterface, int i) {
        TemplateItemBean templateItemBean2;
        SelectItemProvider selectItemProvider = templateApplyController.mSelectItemProvider;
        RecyclerView.ViewHolder selectedViewHolder = selectItemProvider != null ? selectItemProvider.getSelectedViewHolder() : null;
        if (selectedViewHolder instanceof TemplatePreviewViewHolder) {
            TemplatePreviewViewHolder templatePreviewViewHolder = (TemplatePreviewViewHolder) selectedViewHolder;
            CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) templatePreviewViewHolder.getCallback();
            int x = templatePreviewViewHolder.getX();
            int y = templatePreviewViewHolder.getY();
            Log.i(templateApplyController.TAG, "createAndShowDownloadDialog -> callback: " + crossViewPagerCallback + ", x: " + x + ", y: " + y);
            if (templateItemBean != null) {
                templateItemBean.setAutoDownload(true);
            }
            if (crossViewPagerCallback != null) {
                templateItemBean2 = templateItemBean;
                CrossViewPagerCallback.DefaultImpls.onTemplateCustomized$default(crossViewPagerCallback, x, y, templateItemBean2, 0, 8, null);
            } else {
                templateItemBean2 = templateItemBean;
            }
            if (templateItemBean2 != null) {
                templateItemBean2.setAutoDownload(false);
            }
        }
        return Unit.INSTANCE;
    }

    public static final Unit createAndShowDownloadDialog$lambda$12(TemplateApplyController templateApplyController, boolean z, TemplateItemBean templateItemBean, String str, int i, ScreenshotSource screenshotSource, DialogInterface dialogInterface, int i2) throws Throwable {
        templateApplyController.checkOrApplyTemplate(z, templateItemBean, str, i, screenshotSource);
        return Unit.INSTANCE;
    }

    public static final Unit createAndShowGalleryDialog$lambda$10(TemplateApplyController templateApplyController, boolean z, TemplateItemBean templateItemBean, String str, ScreenshotSource screenshotSource, DialogInterface dialogInterface, int i) throws Throwable {
        Log.i(templateApplyController.TAG, "createAndShowGalleryDialog -> closeLockscreenMagazine");
        templateApplyController.checkOrApplyTemplate(z, templateItemBean, str, 0, screenshotSource);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ boolean finalApplyTemplateByProvider$default(TemplateApplyController templateApplyController, Context context, String str, long j, boolean z, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            j = 0;
        }
        return templateApplyController.finalApplyTemplateByProvider(context, str, j, z, i, (i3 & 32) != 0 ? 3 : i2);
    }

    public static /* synthetic */ void onCurrentTemplateScreenshotsSaveComplete$default(TemplateApplyController templateApplyController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        templateApplyController.onCurrentTemplateScreenshotsSaveComplete(z);
    }

    public static /* synthetic */ void setApplying$default(TemplateApplyController templateApplyController, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = false;
        }
        templateApplyController.setApplying(z, z2);
    }

    public static final void showAodApplyAskDialog$lambda$26(TemplateApplyController templateApplyController, CommonConfig commonConfig, String str, boolean z, ScreenshotSource screenshotSource, long j, boolean z2, boolean z3, int i, DialogInterface dialogInterface, int i2) {
        AlertDialog alertDialog;
        if (dialogInterface != null) {
            ViewUtil.INSTANCE.writeApplyFlag(dialogInterface, false);
        }
        AlertDialog alertDialog2 = templateApplyController.aodApplyDialog;
        if (alertDialog2 != null && alertDialog2.isShowing() && (alertDialog = templateApplyController.aodApplyDialog) != null) {
            alertDialog.dismiss();
        }
        templateApplyController.applyAfterScreenshot(commonConfig, str, z, screenshotSource, j, z2, z3, i);
    }

    public static final CommonConfig showApplyAllAskDialog$lambda$17(TemplateApplyController templateApplyController, String str, boolean z, Ref.IntRef intRef) throws Throwable {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        Log.d(templateApplyController.TAG, "showApplyAllAskDialog: load template config for apply preset");
        CommonConfig templateConfig = TemplateDataUtil.getTemplateConfig(templateApplyController.activity, str, z, intRef.element);
        if (DepthPresetDownloadManager.Companion.isPresetDepthVideo((templateConfig == null || (lockscreenInfo2 = templateConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo(), z) && templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.setResourceType("video");
        }
        return templateConfig;
    }

    public static final void showApplyAllAskDialog$lambda$18(TemplateApplyController templateApplyController, boolean z, TemplateItemBean templateItemBean, boolean z2, ScreenshotSource screenshotSource, CommonConfig commonConfig) {
        EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(templateApplyController.activity);
        if (viewModel != null) {
            viewModel.setApplyTemplateConfig(commonConfig);
        }
        templateApplyController.showAllAskDialog(z, templateItemBean, z2, commonConfig, screenshotSource);
    }

    public static final void showDualClockCloseDialog$lambda$19(boolean z, TemplateApplyController templateApplyController, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface dialogInterface, int i) {
        AlertDialog alertDialog;
        if (z && dialogInterface != null) {
            ViewUtil.INSTANCE.writeApplyFlag(dialogInterface, false);
        }
        AlertDialog alertDialog2 = templateApplyController.dualClockDialog;
        if (alertDialog2 != null && alertDialog2.isShowing() && (alertDialog = templateApplyController.dualClockDialog) != null) {
            alertDialog.dismiss();
        }
        if (i == -2) {
            if (ClickUtil.INSTANCE.isFastDoubleClick()) {
                return;
            }
            onClickListener2.onClick(dialogInterface, i);
        } else if (i == -1 && !ClickUtil.INSTANCE.isFastDoubleClick()) {
            onClickListener.onClick(dialogInterface, i);
        }
    }

    public static final void showHistoryDeleteDialog$lambda$35(boolean z, TemplateApplyController templateApplyController, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface dialogInterface, int i) {
        AlertDialog alertDialog;
        if (z && dialogInterface != null) {
            ViewUtil.INSTANCE.writeApplyFlag(dialogInterface, false);
        }
        AlertDialog alertDialog2 = templateApplyController.historyDeleteDialog;
        if (alertDialog2 != null && alertDialog2.isShowing() && (alertDialog = templateApplyController.historyDeleteDialog) != null) {
            alertDialog.dismiss();
        }
        if (i == -2) {
            if (ClickUtil.INSTANCE.isFastDoubleClick()) {
                return;
            }
            onClickListener2.onClick(dialogInterface, i);
        } else if (i == -1 && !ClickUtil.INSTANCE.isFastDoubleClick()) {
            onClickListener.onClick(dialogInterface, i);
        }
    }

    public final void actionBeforeScheduleApply() {
        FragmentActivity fragmentActivity = this.activity;
        if (fragmentActivity instanceof EditorActivity) {
            ((EditorActivity) fragmentActivity).changeIsDismissKeyguardWhenPause(false);
            ((EditorActivity) this.activity).showOrHideApplyLoading(true);
        }
    }

    public final void applyAfterScreenshot(final CommonConfig commonConfig, final String str, final boolean z, final ScreenshotSource screenshotSource, final long j, boolean z2, final boolean z3, final int i) {
        Log.i(this.TAG, "applyAfterScreenshot: isPreset = " + z + ", shouldScreenshotUseTemplate = " + z2);
        actionBeforeScheduleApply();
        ScreenshotCallback screenshotCallback = new ScreenshotCallback() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$applyAfterScreenshot$screenshotCallback$1
            @Override // com.miui.keyguard.editor.homepage.util.ScreenshotCallback
            public void onScreenshotComplete(Screenshots screenshots) {
                Bitmap smallWallpaper;
                Bitmap largeLandWallpaper;
                Bitmap largePortWallpaper;
                Bitmap largeLandscape;
                Bitmap largePortrait;
                Bitmap small;
                CommonConfig commonConfig2;
                HomeConfig homeInfo;
                WallpaperInfo wallpaperInfo;
                String source;
                HomeConfig homeInfo2;
                WallpaperInfo wallpaperInfo2;
                String str2 = this.this$0.TAG;
                boolean z4 = z;
                CommonConfig commonConfig3 = commonConfig;
                String presetWallpaperAbsolutePath = null;
                Log.i(str2, "applyAfterScreenshot@onScreenshotComplete: " + screenshots + ", isPreset: " + z4 + "，source = " + ((commonConfig3 == null || (homeInfo2 = commonConfig3.getHomeInfo()) == null || (wallpaperInfo2 = homeInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getSource()));
                if (z && (commonConfig2 = commonConfig) != null && (homeInfo = commonConfig2.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null && (source = wallpaperInfo.getSource()) != null) {
                    presetWallpaperAbsolutePath = TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(source);
                }
                if (screenshots != null && (small = screenshots.getSmall()) != null) {
                    ScreenshotSource screenshotSource2 = screenshotSource;
                    screenshotSource2.setSmallScreenshot(small);
                    screenshotSource2.setPresetSmallWallpaperScreenshot(presetWallpaperAbsolutePath);
                }
                if (screenshots != null && (largePortrait = screenshots.getLargePortrait()) != null) {
                    ScreenshotSource screenshotSource3 = screenshotSource;
                    screenshotSource3.setLargePortraitScreenshot(largePortrait);
                    screenshotSource3.setPresetLargePortWallpaperScreenshot(presetWallpaperAbsolutePath);
                }
                if (screenshots != null && (largeLandscape = screenshots.getLargeLandscape()) != null) {
                    ScreenshotSource screenshotSource4 = screenshotSource;
                    screenshotSource4.setLargeLandscapeScreenshot(largeLandscape);
                    screenshotSource4.setPresetLargeLandWallpaperScreenshot(presetWallpaperAbsolutePath);
                }
                if (screenshots != null && (largePortWallpaper = screenshots.getLargePortWallpaper()) != null) {
                    screenshotSource.setLargePortWallpaperScreenshot(largePortWallpaper);
                }
                if (screenshots != null && (largeLandWallpaper = screenshots.getLargeLandWallpaper()) != null) {
                    screenshotSource.setLargeLandWallpaperScreenshot(largeLandWallpaper);
                }
                if (screenshots != null && (smallWallpaper = screenshots.getSmallWallpaper()) != null) {
                    screenshotSource.setSmallWallpaperScreenshot(smallWallpaper);
                }
                Log.d(this.this$0.TAG, "applyAfterScreenshot: apply template with screenshot");
                this.this$0.completeApplyTemplate(str, z, screenshotSource, j, z3, i);
            }
        };
        if (z2) {
            loadTemplateAndScreenshot(commonConfig, screenshotCallback, z);
        } else if (z) {
            screenshotWithCurrentPreview(screenshotCallback);
        } else {
            screenshotCallback.onScreenshotComplete(null);
        }
    }

    public final void applyHistoryTemplate(TemplateItemBean templateItemBean, final boolean z, final boolean z2, final boolean z3, final ScreenshotSource screenshotSource) {
        String configUri;
        TemplateHistoryConfig history = templateItemBean != null ? templateItemBean.getHistory() : null;
        if (history == null || (configUri = history.getConfigUri()) == null) {
            configUri = "";
        }
        long id = history != null ? history.getId() : 0L;
        int hideFlag = history != null ? history.getHideFlag() : 0;
        boolean zIsDualClock = history != null ? history.isDualClock() : false;
        Context applicationContext = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        boolean zIsDualClockAvailable = DualClockManager.isDualClockAvailable(applicationContext);
        Log.d(this.TAG, "applyHistoryTemplate -> dualClockOpened: " + zIsDualClockAvailable + ", useDualClockInHistory: " + zIsDualClock);
        if (zIsDualClock && !zIsDualClockAvailable) {
            ToastUtil toastUtil = ToastUtil.INSTANCE;
            Context applicationContext2 = this.applicationContext;
            Intrinsics.checkNotNullExpressionValue(applicationContext2, "applicationContext");
            toastUtil.showText(applicationContext2, R.string.kg_main_toast_recover_default_style);
        }
        screenshotSource.setHistorySmallScreenshotUri(history != null ? history.getSmallScreenshotUri() : null);
        screenshotSource.setHistoryLargePortraitScreenshotUri(history != null ? history.getLargePortraitScreenshotUri() : null);
        screenshotSource.setHistoryLargeLandscapeScreenshotUri(history != null ? history.getLargeLandscapeScreenshotUri() : null);
        screenshotSource.setHistoryLargePortWallpaperScreenshotUri(history != null ? history.getLargePortWallpaperScreenshotUri() : null);
        screenshotSource.setHistoryLargeLandWallpaperScreenshotUri(history != null ? history.getLargeLandWallpaperScreenshotUri() : null);
        if (!zIsDualClockAvailable || zIsDualClock) {
            checkOrAskAodApply(configUri, false, screenshotSource, id, zIsDualClock != zIsDualClockAvailable, false, z, z2, z3, hideFlag);
            return;
        }
        final String str = configUri;
        final long j = id;
        final int i = hideFlag;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                TemplateApplyController.applyHistoryTemplate$lambda$22(this.f$0, str, screenshotSource, j, z, z2, z3, i, dialogInterface, i2);
            }
        };
        showDualClockCloseDialog(onClickListener, onClickListener, true);
    }

    public final void applyHistoryTemplateByProvider(TemplateItemBean templateItemBean) {
        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), null, null, new TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1(this, templateItemBean, null), 3, null).invokeOnCompletion(new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TemplateApplyController.applyHistoryTemplateByProvider$lambda$1(this.f$0, (Throwable) obj);
            }
        });
    }

    public final void applyPresetOrHistoryTemplate(boolean z, TemplateItemBean templateItemBean, boolean z2, boolean z3, boolean z4, ScreenshotSource screenshotSource) {
        if (z) {
            applyPresetTemplate(templateItemBean, z2, z3, z4, screenshotSource);
        } else {
            applyHistoryTemplate(templateItemBean, z2, z3, z4, screenshotSource);
        }
    }

    public final void applyPresetTemplate(TemplateItemBean templateItemBean, final boolean z, final boolean z2, final boolean z3, final ScreenshotSource screenshotSource) {
        final String configUri;
        PresetTemplateConfig preset = templateItemBean != null ? templateItemBean.getPreset() : null;
        if (preset == null || (configUri = preset.getConfigUri()) == null) {
            configUri = "";
        }
        final int hideFlag = preset != null ? preset.getHideFlag() : 0;
        Context applicationContext = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        boolean zIsDualClockAvailable = DualClockManager.isDualClockAvailable(applicationContext);
        Log.d(this.TAG, "applyPresetTemplate -> dualClockOpened: " + zIsDualClockAvailable);
        if (!zIsDualClockAvailable) {
            checkOrAskAodApply$default(this, configUri, true, screenshotSource, 0L, false, false, z, z2, z3, hideFlag, 8, null);
        } else {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    TemplateApplyController.applyPresetTemplate$lambda$23(this.f$0, configUri, screenshotSource, z, z2, z3, hideFlag, dialogInterface, i);
                }
            };
            showDualClockCloseDialog(onClickListener, onClickListener, true);
        }
    }

    public final int applyTemplate(boolean z) {
        int i;
        if (CurrentTemplateApi.Companion.isGalleryContentRequesting()) {
            Log.w(this.TAG, "applyTemplate failed: isGalleryContentRequesting = true.");
            return 1;
        }
        if (!z && this.isApplying && SystemClock.uptimeMillis() - this.lastApplyTime < 3000) {
            Log.w(this.TAG, "applyTemplate ignored: isApplying = true and too little time since last apply.");
            return 2;
        }
        SelectItemProvider selectItemProvider = this.mSelectItemProvider;
        TemplateItemBean selectedItem = selectItemProvider != null ? selectItemProvider.getSelectedItem() : null;
        if (selectedItem == null) {
            Log.w(this.TAG, "applyCurrentTemplate failed: currentTemplateBean = null");
            return 3;
        }
        if (this.isCurrentTemplateScreenshotsSaving) {
            Log.w(this.TAG, "applyTemplate delayed: screenshots for current template is saving.");
            setHasPendingApplyTemplateTask(true);
            return 4;
        }
        setApplying$default(this, true, false, 2, null);
        this.lastApplyTime = SystemClock.uptimeMillis();
        FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
        if (z) {
            i = 2;
        } else {
            if (z) {
                throw new NoWhenBranchMatchedException();
            }
            i = 1;
        }
        fashionGalleryHelper.setCLOSE_SOURCE(i);
        int itemType = selectedItem.getItemType();
        if (itemType == 1) {
            if (Process.INSTANCE.isKeyguardeditorProcess()) {
                checkOrApplyCurrentTemplate(selectedItem);
                return 0;
            }
            checkOrApplyCurrentTemplateByProvider(this.activity, selectedItem);
            return 0;
        }
        if (itemType == 2) {
            if (Process.INSTANCE.isKeyguardeditorProcess()) {
                checkGalleryOrApplyTemplate(false, selectedItem);
                return 0;
            }
            applyHistoryTemplateByProvider(selectedItem);
            return 0;
        }
        if (itemType == 3) {
            checkGalleryOrApplyTemplate(true, selectedItem);
            return 0;
        }
        setApplying$default(this, false, false, 2, null);
        Log.d(this.TAG, "applyTemplate: type error:" + selectedItem.getItemType());
        return 5;
    }

    public final int calcSampleSize(int i, int i2, int i3, int i4) {
        int i5 = 1;
        if (i > 0 && i2 > 0 && i3 > 0 && i4 > 0) {
            int iMax = Math.max(i, i2);
            int iMax2 = Math.max(i3, i4) * 2;
            while (iMax / i5 > iMax2 && i5 < 32) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public final boolean callingFromSystemUI() {
        Intent intent = this.activity.getIntent();
        return intent != null && intent.getIntExtra("openSource", -1) == 0;
    }

    public final void checkGalleryOrApplyTemplate(boolean z, TemplateItemBean templateItemBean) {
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this.activity), Dispatchers.getMain(), null, new AnonymousClass1(z, templateItemBean, DeviceUtil.INSTANCE.getScreenStatus(this.activity), null), 2, null);
    }

    public final void checkOrApplyCurrentTemplate(final TemplateItemBean templateBean) {
        Intrinsics.checkNotNullParameter(templateBean, "templateBean");
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return TemplateApplyController.checkOrApplyCurrentTemplate$lambda$4(this.f$0);
            }
        }).post(new java.util.function.Consumer() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TemplateApplyController.checkOrApplyCurrentTemplate$lambda$7(this.f$0, templateBean, (Boolean) obj);
            }
        });
    }

    public final void checkOrApplyCurrentTemplateByProvider(Context context, TemplateItemBean templateItemBean) {
        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), null, null, new TemplateApplyController$checkOrApplyCurrentTemplateByProvider$applyJob$1(this, templateItemBean, context, null), 3, null).invokeOnCompletion(new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TemplateApplyController.checkOrApplyCurrentTemplateByProvider$lambda$8(this.f$0, (Throwable) obj);
            }
        });
    }

    public final void checkOrApplyOnSpecialWallpaper(final boolean z, final TemplateItemBean templateItemBean, String str, boolean z2, final boolean z3, final boolean z4, final ScreenshotSource screenshotSource) {
        final TemplateApplyController templateApplyController;
        AlertDialog alertDialogCreateConfirmDialogReplaceSuperWallpaper$default;
        TemplateHistoryConfig history;
        if (!Intrinsics.areEqual(str, CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
            if (!Intrinsics.areEqual(str, "linkage_video")) {
                applyPresetOrHistoryTemplate(z, templateItemBean, z2, z3, z4, screenshotSource);
                return;
            }
            AlertDialog alertDialogCreateConfirmDialogReplaceLinkageVideoWallpaper$default = SpecialWallpaperHelper.createConfirmDialogReplaceLinkageVideoWallpaper$default(SpecialWallpaperHelper.INSTANCE, this.activity, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda21
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.applyPresetOrHistoryTemplate(z, templateItemBean, true, z3, z4, screenshotSource);
                }
            }, null, 4, null);
            alertDialogCreateConfirmDialogReplaceLinkageVideoWallpaper$default.setOnDismissListener(this);
            alertDialogCreateConfirmDialogReplaceLinkageVideoWallpaper$default.show();
            return;
        }
        String templateId = (templateItemBean == null || (history = templateItemBean.getHistory()) == null) ? null : history.getTemplateId();
        Log.i(this.TAG, "checkOrApplyOnSuperWallpaper -> templateType: " + templateId);
        SpecialWallpaperHelper specialWallpaperHelper = SpecialWallpaperHelper.INSTANCE;
        if (specialWallpaperHelper.isUnSupportSuperWallpaper(templateId)) {
            templateApplyController = this;
            alertDialogCreateConfirmDialogReplaceSuperWallpaper$default = SpecialWallpaperHelper.createConfirmDialogWithUnSupportTemplate$default(specialWallpaperHelper, this.activity, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda19
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.applyPresetOrHistoryTemplate(z, templateItemBean, true, z3, z4, screenshotSource);
                }
            }, null, 4, null);
        } else {
            templateApplyController = this;
            alertDialogCreateConfirmDialogReplaceSuperWallpaper$default = SpecialWallpaperHelper.createConfirmDialogReplaceSuperWallpaper$default(specialWallpaperHelper, templateApplyController.activity, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda20
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.applyPresetOrHistoryTemplate(z, templateItemBean, true, z3, z4, screenshotSource);
                }
            }, null, 4, null);
        }
        alertDialogCreateConfirmDialogReplaceSuperWallpaper$default.setOnDismissListener(templateApplyController);
        alertDialogCreateConfirmDialogReplaceSuperWallpaper$default.show();
    }

    public final void checkOrApplyTemplate(boolean z, TemplateItemBean templateItemBean, String str, int i, ScreenshotSource screenshotSource) throws Throwable {
        String resourceType;
        TemplateHistoryConfig history;
        Log.i(this.TAG, "checkOrApplyTemplate -> currentWallpaperType: " + str);
        if (z) {
            resourceType = "image";
        } else {
            resourceType = (templateItemBean == null || (history = templateItemBean.getHistory()) == null) ? null : history.getResourceType();
        }
        Wallpaper.Companion companion = Wallpaper.Companion;
        boolean z2 = false;
        boolean z3 = companion.isSpecialWallpaperType(resourceType) || companion.isSpecialWallpaperType(str);
        boolean z4 = i == 2 || i == 3;
        if (z3 && !Intrinsics.areEqual(resourceType, "sensor")) {
            z2 = true;
        }
        Log.i(this.TAG, "checkOrApplyTemplate: isSpecialWallpaperType =" + z3 + ",isGalleryOpened = " + z4);
        if (z3 || i == 1) {
            checkOrApplyOnSpecialWallpaper(z, templateItemBean, str, z3, z4, z2, screenshotSource);
        } else {
            showApplyAllAskDialog(z, templateItemBean, z4, screenshotSource);
        }
    }

    public final void checkOrAskAodApply(final String str, final boolean z, final ScreenshotSource screenshotSource, final long j, final boolean z2, final boolean z3, final boolean z4, final boolean z5, final boolean z6, final int i) {
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                return TemplateApplyController.checkOrAskAodApply$lambda$24(z3, this, str, z, i, z6);
            }
        }).post(new java.util.function.Consumer() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TemplateApplyController.checkOrAskAodApply$lambda$25(this.f$0, str, z, screenshotSource, j, z2, z4, i, z5, (Pair) obj);
            }
        });
    }

    public final void completeApplyTemplate(String str, boolean z, ScreenshotSource screenshotSource, long j, boolean z2, int i) {
        Log.i(this.TAG, "completeApplyTemplate: configUri = " + str + ", isPreset = " + z + ", screenshotSource = " + screenshotSource + ", shouldApplyToAll = " + z2);
        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), null, null, new TemplateApplyController$completeApplyTemplate$applyJob$1(this, z, screenshotSource, str, j, z2, DeviceUtil.INSTANCE.getScreenStatus(this.activity), i, null), 3, null).invokeOnCompletion(new Function1() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TemplateApplyController.completeApplyTemplate$lambda$27(this.f$0, (Throwable) obj);
            }
        });
    }

    public final void createAndShowDownloadDialog(final boolean z, final TemplateItemBean templateItemBean, final String str, final int i, final ScreenshotSource screenshotSource) {
        AlertDialog alertDialogCreateDepthVideoDownloadDialog = FashionGalleryDialogsKt.createDepthVideoDownloadDialog(this.activity, new Function2() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TemplateApplyController.createAndShowDownloadDialog$lambda$11(this.f$0, templateItemBean, (DialogInterface) obj, ((Integer) obj2).intValue());
            }
        }, new Function2() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TemplateApplyController.createAndShowDownloadDialog$lambda$12(this.f$0, z, templateItemBean, str, i, screenshotSource, (DialogInterface) obj, ((Integer) obj2).intValue());
            }
        });
        this.downloadDialog = alertDialogCreateDepthVideoDownloadDialog;
        if (alertDialogCreateDepthVideoDownloadDialog != null) {
            alertDialogCreateDepthVideoDownloadDialog.setOnDismissListener(this);
        }
        AlertDialog alertDialog = this.downloadDialog;
        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    public final void createAndShowGalleryDialog(final boolean z, final TemplateItemBean templateItemBean, final String str, String str2, final ScreenshotSource screenshotSource) {
        AlertDialog alertDialogCreateFashionGalleryDialog = FashionGalleryDialogsKt.createFashionGalleryDialog(this.activity, new Function2() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TemplateApplyController.createAndShowGalleryDialog$lambda$10(this.f$0, z, templateItemBean, str, screenshotSource, (DialogInterface) obj, ((Integer) obj2).intValue());
            }
        });
        this.galleryDialog = alertDialogCreateFashionGalleryDialog;
        if (alertDialogCreateFashionGalleryDialog != null) {
            alertDialogCreateFashionGalleryDialog.setOnDismissListener(this);
        }
        AlertDialog alertDialog = this.galleryDialog;
        if (alertDialog != null) {
            alertDialog.show();
        }
        TrackHelper trackHelper = TrackHelper.INSTANCE;
        Context applicationContext = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        trackHelper.trackVideoDialogExpose(applicationContext, "锁屏编辑首页");
    }

    public final FrameLayout.LayoutParams createScreenshotLayoutParams() {
        Point displayRealSize = getDisplayRealSize();
        return new FrameLayout.LayoutParams(displayRealSize.x, displayRealSize.y);
    }

    public final void currentTemplateApplyCompleted(TemplateItemBean templateItemBean, boolean z) {
        Consumer consumer = this.applyMyTemplateCallback;
        if (consumer != null) {
            consumer.accept(new Pair(templateItemBean, Boolean.valueOf(z)));
        }
        setApplying$default(this, false, false, 2, null);
    }

    public final Pair decodeWithSampling(String str, int i, int i2) {
        Bitmap bitmapDecodeFile;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int iCalcSampleSize = calcSampleSize(options.outWidth, options.outHeight, i, i2);
        options.inJustDecodeBounds = false;
        options.inSampleSize = iCalcSampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        } catch (Exception e) {
            Log.w(this.TAG, "decodeWithSampling: decode failed path=" + str + ", e=" + e);
            bitmapDecodeFile = null;
        }
        return TuplesKt.to(bitmapDecodeFile, Integer.valueOf(iCalcSampleSize));
    }

    public final void dismissDialogIfShowing(AlertDialog alertDialog) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    /* JADX WARN: Code duplicated, block: B:57:0x013d A[Catch: Exception -> 0x0159, TRY_LEAVE, TryCatch #5 {Exception -> 0x0159, blocks: (B:55:0x0137, B:57:0x013d, B:62:0x015c, B:65:0x0167, B:67:0x016f, B:69:0x019c, B:71:0x01a2, B:73:0x01a8, B:75:0x01ae, B:77:0x01b6, B:79:0x01c3, B:81:0x01d1, B:85:0x01da, B:86:0x01de, B:54:0x012c), top: B:102:0x012c }] */
    /* JADX WARN: Code duplicated, block: B:64:0x0166  */
    /* JADX WARN: Code duplicated, block: B:67:0x016f A[Catch: Exception -> 0x0159, TryCatch #5 {Exception -> 0x0159, blocks: (B:55:0x0137, B:57:0x013d, B:62:0x015c, B:65:0x0167, B:67:0x016f, B:69:0x019c, B:71:0x01a2, B:73:0x01a8, B:75:0x01ae, B:77:0x01b6, B:79:0x01c3, B:81:0x01d1, B:85:0x01da, B:86:0x01de, B:54:0x012c), top: B:102:0x012c }] */
    /* JADX WARN: Code duplicated, block: B:69:0x019c A[Catch: Exception -> 0x0159, TryCatch #5 {Exception -> 0x0159, blocks: (B:55:0x0137, B:57:0x013d, B:62:0x015c, B:65:0x0167, B:67:0x016f, B:69:0x019c, B:71:0x01a2, B:73:0x01a8, B:75:0x01ae, B:77:0x01b6, B:79:0x01c3, B:81:0x01d1, B:85:0x01da, B:86:0x01de, B:54:0x012c), top: B:102:0x012c }] */
    /* JADX WARN: Code duplicated, block: B:74:0x01ad  */
    /* JADX WARN: Code duplicated, block: B:77:0x01b6 A[Catch: Exception -> 0x0159, TryCatch #5 {Exception -> 0x0159, blocks: (B:55:0x0137, B:57:0x013d, B:62:0x015c, B:65:0x0167, B:67:0x016f, B:69:0x019c, B:71:0x01a2, B:73:0x01a8, B:75:0x01ae, B:77:0x01b6, B:79:0x01c3, B:81:0x01d1, B:85:0x01da, B:86:0x01de, B:54:0x012c), top: B:102:0x012c }] */
    /* JADX WARN: Code duplicated, block: B:79:0x01c3 A[Catch: Exception -> 0x0159, TryCatch #5 {Exception -> 0x0159, blocks: (B:55:0x0137, B:57:0x013d, B:62:0x015c, B:65:0x0167, B:67:0x016f, B:69:0x019c, B:71:0x01a2, B:73:0x01a8, B:75:0x01ae, B:77:0x01b6, B:79:0x01c3, B:81:0x01d1, B:85:0x01da, B:86:0x01de, B:54:0x012c), top: B:102:0x012c }] */
    /* JADX WARN: Code duplicated, block: B:81:0x01d1 A[Catch: Exception -> 0x0159, TryCatch #5 {Exception -> 0x0159, blocks: (B:55:0x0137, B:57:0x013d, B:62:0x015c, B:65:0x0167, B:67:0x016f, B:69:0x019c, B:71:0x01a2, B:73:0x01a8, B:75:0x01ae, B:77:0x01b6, B:79:0x01c3, B:81:0x01d1, B:85:0x01da, B:86:0x01de, B:54:0x012c), top: B:102:0x012c }] */
    /* JADX WARN: Code duplicated, block: B:84:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:86:0x01de A[Catch: Exception -> 0x0159, TRY_LEAVE, TryCatch #5 {Exception -> 0x0159, blocks: (B:55:0x0137, B:57:0x013d, B:62:0x015c, B:65:0x0167, B:67:0x016f, B:69:0x019c, B:71:0x01a2, B:73:0x01a8, B:75:0x01ae, B:77:0x01b6, B:79:0x01c3, B:81:0x01d1, B:85:0x01da, B:86:0x01de, B:54:0x012c), top: B:102:0x012c }] */
    /* JADX WARN: Code duplicated, block: B:8:0x0018  */
    /* JADX WARN: Instruction removed from duplicated block: B:57:0x013d, please report this as an issue */
    public final Object finalApplyTemplate(String str, boolean z, ScreenshotSource screenshotSource, long j, boolean z2, int i, int i2, Continuation continuation) throws Throwable {
        C01241 c01241;
        boolean z3;
        String str2;
        boolean z4;
        int i3;
        String str3;
        CommonConfig commonConfig;
        CommonConfig commonConfig2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        boolean z5;
        Object objM1061constructorimpl;
        CommonConfig commonConfig3;
        CommonConfig commonConfig4;
        Throwable thM1063exceptionOrNullimpl;
        Boolean boolBoxBoolean;
        boolean zBooleanValue;
        String str4;
        String templateId;
        FashionGalleryHelper fashionGalleryHelper;
        Context context;
        ClockInfo clockInfo;
        boolean z6;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo2;
        TemplateApplyController templateApplyController = this;
        if (continuation instanceof C01241) {
            c01241 = (C01241) continuation;
            int i4 = c01241.label;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                c01241.label = i4 - Integer.MIN_VALUE;
            } else {
                c01241 = templateApplyController.new C01241(continuation);
            }
        } else {
            c01241 = templateApplyController.new C01241(continuation);
        }
        C01241 c01242 = c01241;
        Object objApplyHomePageTemplate = c01242.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i5 = c01242.label;
        if (i5 == 0) {
            ResultKt.throwOnFailure(objApplyHomePageTemplate);
            try {
                EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(templateApplyController.activity);
                CommonConfig applyTemplateConfig = viewModel != null ? viewModel.getApplyTemplateConfig() : null;
                Log.i(templateApplyController.TAG, "start complete apply template~ cacheConfig=" + applyTemplateConfig + " magicType=" + ((applyTemplateConfig == null || (lockscreenInfo = applyTemplateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Boxing.boxInt(wallpaperInfo.getMagicType())));
                if (applyTemplateConfig == null) {
                    Context applicationContext = templateApplyController.applicationContext;
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
                    str2 = str;
                    z4 = z;
                    i3 = i2;
                    applyTemplateConfig = TemplateDataUtil.getTemplateConfig(applicationContext, str2, z4, i3);
                } else {
                    str2 = str;
                    z4 = z;
                    i3 = i2;
                }
                CommonConfig commonConfig5 = applyTemplateConfig;
                TemplateApi.Companion companion = TemplateApi.Companion;
                Context applicationContext2 = templateApplyController.applicationContext;
                Intrinsics.checkNotNullExpressionValue(applicationContext2, "applicationContext");
                CommonConfig commonConfigCurrentTemplate$default = TemplateApi.DefaultImpls.currentTemplate$default(companion.getInstance(applicationContext2), false, false, false, false, null, false, null, com.miui.maml.R.styleable.AppCompatTheme_viewInflaterClass, null);
                try {
                    Result.Companion companion2 = Result.Companion;
                    Context applicationContext3 = templateApplyController.applicationContext;
                    Intrinsics.checkNotNullExpressionValue(applicationContext3, "applicationContext");
                    TemplateApi companion3 = companion.getInstance(applicationContext3);
                    boolean zCallingFromSystemUI = templateApplyController.callingFromSystemUI();
                    c01242.L$0 = templateApplyController;
                    c01242.L$1 = commonConfig5;
                    c01242.L$2 = commonConfigCurrentTemplate$default;
                    c01242.label = 1;
                    str3 = "applicationContext";
                    z3 = false;
                    commonConfig = commonConfigCurrentTemplate$default;
                    try {
                        objApplyHomePageTemplate = companion3.applyHomePageTemplate(str2, z4, screenshotSource, j, z2, zCallingFromSystemUI, i, commonConfig5, i3, c01242);
                        if (objApplyHomePageTemplate == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        commonConfig2 = commonConfig5;
                        objM1061constructorimpl = Result.m1061constructorimpl(Boxing.boxBoolean(((Boolean) objApplyHomePageTemplate).booleanValue()));
                        commonConfig3 = commonConfig2;
                        commonConfig4 = commonConfig;
                        thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
                        if (thM1063exceptionOrNullimpl != null) {
                            Log.d(templateApplyController.TAG, "apply home error: " + thM1063exceptionOrNullimpl);
                            KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(z3);
                        }
                        boolBoxBoolean = Boxing.boxBoolean(z3);
                        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
                            objM1061constructorimpl = boolBoxBoolean;
                        }
                        zBooleanValue = ((Boolean) objM1061constructorimpl).booleanValue();
                        if (zBooleanValue) {
                            templateApplyController.setTemplateConfigForHistoryBean(commonConfig3);
                            SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.INSTANCE;
                            Context context2 = templateApplyController.applicationContext;
                            str4 = str3;
                            Intrinsics.checkNotNullExpressionValue(context2, str4);
                            sharedPreferencesUtil.setBoolean(context2, "has_screenshot_for_external_template", true);
                            TrackHelper trackHelper = TrackHelper.INSTANCE;
                            Context context3 = templateApplyController.applicationContext;
                            Intrinsics.checkNotNullExpressionValue(context3, str4);
                            TrackHelper.trackQuitHomePage$default(trackHelper, context3, "应用", commonConfig3, commonConfig4, templateApplyController.floorTrackShowTag, MainFragment.Companion.isEnterEditor(), false, 64, null);
                            if (commonConfig3 != null) {
                                templateId = null;
                            } else {
                                templateId = null;
                            }
                            if (Intrinsics.areEqual(templateId, "doodle")) {
                                fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                                context = templateApplyController.applicationContext;
                                Intrinsics.checkNotNullExpressionValue(context, str4);
                                if (fashionGalleryHelper.isGalleryInstalledAndOpen(context)) {
                                    TemplateDataUtil.INSTANCE.setSupportShowDoodleText(templateApplyController.activity, z3);
                                } else {
                                    TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
                                    FragmentActivity fragmentActivity = templateApplyController.activity;
                                    clockInfo = commonConfig3.getLockscreenInfo().getClockInfo();
                                    if (clockInfo == null) {
                                        z6 = z3;
                                    } else {
                                        z6 = z3;
                                    }
                                    templateDataUtil.setSupportShowDoodleText(fragmentActivity, z6);
                                }
                            }
                        }
                        z5 = zBooleanValue;
                    } catch (Throwable th) {
                        th = th;
                        commonConfig2 = commonConfig5;
                        Result.Companion companion4 = Result.Companion;
                        objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
                        commonConfig3 = commonConfig2;
                        commonConfig4 = commonConfig;
                        thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
                        if (thM1063exceptionOrNullimpl != null) {
                            Log.d(templateApplyController.TAG, "apply home error: " + thM1063exceptionOrNullimpl);
                            KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(z3);
                        }
                        boolBoxBoolean = Boxing.boxBoolean(z3);
                        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
                            objM1061constructorimpl = boolBoxBoolean;
                        }
                        zBooleanValue = ((Boolean) objM1061constructorimpl).booleanValue();
                        if (zBooleanValue) {
                            templateApplyController.setTemplateConfigForHistoryBean(commonConfig3);
                            SharedPreferencesUtil sharedPreferencesUtil2 = SharedPreferencesUtil.INSTANCE;
                            Context context4 = templateApplyController.applicationContext;
                            str4 = str3;
                            Intrinsics.checkNotNullExpressionValue(context4, str4);
                            sharedPreferencesUtil2.setBoolean(context4, "has_screenshot_for_external_template", true);
                            TrackHelper trackHelper2 = TrackHelper.INSTANCE;
                            Context context5 = templateApplyController.applicationContext;
                            Intrinsics.checkNotNullExpressionValue(context5, str4);
                            TrackHelper.trackQuitHomePage$default(trackHelper2, context5, "应用", commonConfig3, commonConfig4, templateApplyController.floorTrackShowTag, MainFragment.Companion.isEnterEditor(), false, 64, null);
                            if (commonConfig3 != null) {
                                templateId = null;
                            } else {
                                templateId = null;
                            }
                            if (Intrinsics.areEqual(templateId, "doodle")) {
                                fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                                context = templateApplyController.applicationContext;
                                Intrinsics.checkNotNullExpressionValue(context, str4);
                                if (fashionGalleryHelper.isGalleryInstalledAndOpen(context)) {
                                    TemplateDataUtil templateDataUtil2 = TemplateDataUtil.INSTANCE;
                                    FragmentActivity fragmentActivity2 = templateApplyController.activity;
                                    clockInfo = commonConfig3.getLockscreenInfo().getClockInfo();
                                    if (clockInfo == null) {
                                        z6 = z3;
                                    } else {
                                        z6 = z3;
                                    }
                                    templateDataUtil2.setSupportShowDoodleText(fragmentActivity2, z6);
                                } else {
                                    TemplateDataUtil.INSTANCE.setSupportShowDoodleText(templateApplyController.activity, z3);
                                }
                            }
                        }
                        z5 = zBooleanValue;
                        return Boxing.boxBoolean(z5);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    str3 = "applicationContext";
                    z3 = false;
                    commonConfig = commonConfigCurrentTemplate$default;
                }
            } catch (Exception e) {
                e = e;
                z3 = false;
                Log.e(templateApplyController.TAG, "execApplyWithScreenshot error: " + e);
                z5 = z3;
            }
        } else {
            if (i5 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            CommonConfig commonConfig6 = (CommonConfig) c01242.L$2;
            commonConfig2 = (CommonConfig) c01242.L$1;
            TemplateApplyController templateApplyController2 = (TemplateApplyController) c01242.L$0;
            try {
                ResultKt.throwOnFailure(objApplyHomePageTemplate);
                commonConfig = commonConfig6;
                templateApplyController = templateApplyController2;
                str3 = "applicationContext";
                z3 = false;
                try {
                    objM1061constructorimpl = Result.m1061constructorimpl(Boxing.boxBoolean(((Boolean) objApplyHomePageTemplate).booleanValue()));
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        Result.Companion companion5 = Result.Companion;
                        objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
                    } catch (Exception e2) {
                        e = e2;
                        Log.e(templateApplyController.TAG, "execApplyWithScreenshot error: " + e);
                        z5 = z3;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                commonConfig = commonConfig6;
                templateApplyController = templateApplyController2;
                str3 = "applicationContext";
                z3 = false;
                Result.Companion companion6 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
                commonConfig3 = commonConfig2;
                commonConfig4 = commonConfig;
                thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
                if (thM1063exceptionOrNullimpl != null) {
                    Log.d(templateApplyController.TAG, "apply home error: " + thM1063exceptionOrNullimpl);
                    try {
                        KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(z3);
                    } catch (Exception unused) {
                    }
                }
                boolBoxBoolean = Boxing.boxBoolean(z3);
                if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
                    objM1061constructorimpl = boolBoxBoolean;
                }
                zBooleanValue = ((Boolean) objM1061constructorimpl).booleanValue();
                if (zBooleanValue) {
                    templateApplyController.setTemplateConfigForHistoryBean(commonConfig3);
                    SharedPreferencesUtil sharedPreferencesUtil3 = SharedPreferencesUtil.INSTANCE;
                    Context context6 = templateApplyController.applicationContext;
                    str4 = str3;
                    Intrinsics.checkNotNullExpressionValue(context6, str4);
                    sharedPreferencesUtil3.setBoolean(context6, "has_screenshot_for_external_template", true);
                    TrackHelper trackHelper3 = TrackHelper.INSTANCE;
                    Context context7 = templateApplyController.applicationContext;
                    Intrinsics.checkNotNullExpressionValue(context7, str4);
                    TrackHelper.trackQuitHomePage$default(trackHelper3, context7, "应用", commonConfig3, commonConfig4, templateApplyController.floorTrackShowTag, MainFragment.Companion.isEnterEditor(), false, 64, null);
                    if (commonConfig3 != null) {
                        templateId = null;
                    } else {
                        templateId = null;
                    }
                    if (Intrinsics.areEqual(templateId, "doodle")) {
                        fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                        context = templateApplyController.applicationContext;
                        Intrinsics.checkNotNullExpressionValue(context, str4);
                        if (fashionGalleryHelper.isGalleryInstalledAndOpen(context)) {
                            TemplateDataUtil templateDataUtil3 = TemplateDataUtil.INSTANCE;
                            FragmentActivity fragmentActivity3 = templateApplyController.activity;
                            clockInfo = commonConfig3.getLockscreenInfo().getClockInfo();
                            if (clockInfo == null) {
                                z6 = z3;
                            } else {
                                z6 = z3;
                            }
                            templateDataUtil3.setSupportShowDoodleText(fragmentActivity3, z6);
                        } else {
                            TemplateDataUtil.INSTANCE.setSupportShowDoodleText(templateApplyController.activity, z3);
                        }
                    }
                }
                z5 = zBooleanValue;
                return Boxing.boxBoolean(z5);
            }
            commonConfig3 = commonConfig2;
            commonConfig4 = commonConfig;
            thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl != null) {
                Log.d(templateApplyController.TAG, "apply home error: " + thM1063exceptionOrNullimpl);
                KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(z3);
            }
            boolBoxBoolean = Boxing.boxBoolean(z3);
            if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
                objM1061constructorimpl = boolBoxBoolean;
            }
            zBooleanValue = ((Boolean) objM1061constructorimpl).booleanValue();
            if (zBooleanValue) {
                templateApplyController.setTemplateConfigForHistoryBean(commonConfig3);
                SharedPreferencesUtil sharedPreferencesUtil4 = SharedPreferencesUtil.INSTANCE;
                Context context8 = templateApplyController.applicationContext;
                str4 = str3;
                Intrinsics.checkNotNullExpressionValue(context8, str4);
                sharedPreferencesUtil4.setBoolean(context8, "has_screenshot_for_external_template", true);
                TrackHelper trackHelper4 = TrackHelper.INSTANCE;
                Context context9 = templateApplyController.applicationContext;
                Intrinsics.checkNotNullExpressionValue(context9, str4);
                TrackHelper.trackQuitHomePage$default(trackHelper4, context9, "应用", commonConfig3, commonConfig4, templateApplyController.floorTrackShowTag, MainFragment.Companion.isEnterEditor(), false, 64, null);
                if (commonConfig3 != null || (lockscreenInfo2 = commonConfig3.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo2.getClockInfo()) == null) {
                    templateId = null;
                } else {
                    templateId = clockInfo2.getTemplateId();
                }
                if (Intrinsics.areEqual(templateId, "doodle")) {
                    fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                    context = templateApplyController.applicationContext;
                    Intrinsics.checkNotNullExpressionValue(context, str4);
                    if (fashionGalleryHelper.isGalleryInstalledAndOpen(context)) {
                        TemplateDataUtil templateDataUtil4 = TemplateDataUtil.INSTANCE;
                        FragmentActivity fragmentActivity4 = templateApplyController.activity;
                        clockInfo = commonConfig3.getLockscreenInfo().getClockInfo();
                        if (clockInfo == null && clockInfo.getExtraFlag() == 0) {
                            z6 = true;
                        } else {
                            z6 = z3;
                        }
                        templateDataUtil4.setSupportShowDoodleText(fragmentActivity4, z6);
                    } else {
                        TemplateDataUtil.INSTANCE.setSupportShowDoodleText(templateApplyController.activity, z3);
                    }
                }
            }
            z5 = zBooleanValue;
        }
        return Boxing.boxBoolean(z5);
    }

    public final boolean finalApplyTemplateByProvider(Context context, String str, long j, boolean z, int i, int i2) {
        Object objM1061constructorimpl;
        int i3 = 0;
        while (i3 < i2) {
            try {
                Result.Companion companion = Result.Companion;
                Bundle bundle = new Bundle();
                bundle.putString("configUri", str);
                bundle.putInt("hideFlag", i);
                bundle.putLong("entityId", j);
                bundle.putBoolean("shouldApplyToAll", z);
                objM1061constructorimpl = Result.m1061constructorimpl(context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callApplyHistoryTemplate", (String) null, bundle));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl != null) {
                Log.d(this.TAG, "finalApplyTemplateByProvider error: " + thM1063exceptionOrNullimpl);
            }
            Bundle bundle2 = (Bundle) (Result.m1065isFailureimpl(objM1061constructorimpl) ? null : objM1061constructorimpl);
            if (bundle2 != null) {
                return bundle2.getBoolean("resultApplyHistoryTemplate", false);
            }
            i3++;
            Log.e(this.TAG, "contentProvider may have not been created, retry again, time: " + i3);
        }
        return false;
    }

    public final boolean finalReApplyCurrentWallpaper(Context context) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("configUri", "@MINE");
            for (int i = 0; i < 4; i++) {
                Bundle bundleCall = context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callApplyCurrentTemplate", (String) null, bundle);
                Log.i(this.TAG, "finalApplyCurrentTemplateByProvider result:" + bundleCall);
                if (bundleCall != null) {
                    return bundleCall.getBoolean("resultApplyCurrentTemplate");
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(this.TAG, "finalApplyCurrentTemplateByProvider error: " + e);
            return false;
        }
    }

    public final float getDimen(int i) {
        return this.resources.getDimension(i);
    }

    public final Point getDisplayRealSize() {
        if (this.displayRealSize == null) {
            this.displayRealSize = CrossListViewUtil.INSTANCE.requireDisplayRealSize(this.activity);
        }
        Point point = this.displayRealSize;
        Intrinsics.checkNotNull(point);
        return point;
    }

    public final float getScreenshotRadius() {
        if (this.screenshotRadius == null) {
            this.screenshotRadius = Float.valueOf(getDimen(R.dimen.kg_main_item_preview_radius));
        }
        Float f = this.screenshotRadius;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final CommonConfig getSelectedItemConfig(boolean z, TemplateItemBean templateItemBean) throws Throwable {
        WallpaperInfo wallpaperInfo;
        TemplateItemConfig bindItemConfig;
        WallpaperInfo wallpaperInfo2;
        PresetTemplateConfig preset = templateItemBean != null ? templateItemBean.getPreset() : null;
        TemplateHistoryConfig history = templateItemBean != null ? templateItemBean.getHistory() : null;
        boolean z2 = false;
        if (!z) {
            FragmentActivity fragmentActivity = this.activity;
            Intrinsics.checkNotNull(history);
            CommonConfig templateConfig = TemplateDataUtil.getTemplateConfig(fragmentActivity, history.getConfigUri(), false, history.getHideFlag());
            if (templateConfig == null) {
                return null;
            }
            TemplateConfig lockscreenInfo = templateConfig.getLockscreenInfo();
            TemplateApi companion = TemplateApi.Companion.getInstance(this.activity);
            WallpaperInfo wallpaperInfo3 = templateConfig.getLockscreenInfo().getWallpaperInfo();
            lockscreenInfo.setComposedWallpaper(companion.tryComposeSubjectWithBackgroundColor(templateConfig, wallpaperInfo3 != null ? wallpaperInfo3.getCropSubject() : null));
            return templateConfig;
        }
        FragmentActivity fragmentActivity2 = this.activity;
        Intrinsics.checkNotNull(preset);
        CommonConfig templateConfig2 = TemplateDataUtil.getTemplateConfig(fragmentActivity2, preset.getConfigUri(), true, preset.getHideFlag());
        if (templateConfig2 == null) {
            return null;
        }
        if (DepthPresetDownloadManager.Companion.isPresetDepthVideo(templateConfig2.getLockscreenInfo().getWallpaperInfo(), true) && (wallpaperInfo2 = templateConfig2.getLockscreenInfo().getWallpaperInfo()) != null) {
            wallpaperInfo2.setResourceType("video");
        }
        PresetTemplateConfig preset2 = templateItemBean.getPreset();
        if (((preset2 == null || (bindItemConfig = preset2.getBindItemConfig()) == null) ? false : bindItemConfig.getDepthVideo()) && FileUtil.isSupportVideoDepth()) {
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            Context applicationContext = this.activity.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            if (commonUtils.checkThemeSupportDownload(applicationContext)) {
                z2 = true;
            }
        }
        if (z2 && (wallpaperInfo = templateConfig2.getLockscreenInfo().getWallpaperInfo()) != null) {
            wallpaperInfo.setResourceType("video");
        }
        return templateConfig2;
    }

    public final boolean hasPendingApplyTemplateTask() {
        return this.hasPendingApplyTemplateTask;
    }

    public final void initScreenshotContainer(FrameLayout frameLayout) {
        if (frameLayout != null) {
            frameLayout.setClipToOutline(true);
            frameLayout.setOutlineProvider(new RoundOutlineProvider(getScreenshotRadius()));
        }
    }

    public final void initScreenshotContainers(View contentView) {
        Intrinsics.checkNotNullParameter(contentView, "contentView");
        FrameLayout frameLayout = (FrameLayout) contentView.findViewById(R.id.kg_screenshot_container);
        this.screenshotContainer = frameLayout;
        initScreenshotContainer(frameLayout);
    }

    public final void loadTemplateAndScreenshot(CommonConfig commonConfig, ScreenshotCallback screenshotCallback, boolean z) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        String templateId = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        if (templateId == null || templateId.length() == 0) {
            Log.w(this.TAG, "loadTemplateAndScreenshot skipped! (config = " + commonConfig + ") templateId in clockInfo is null.");
            screenshotCallback.onScreenshotComplete(null);
            return;
        }
        if (this.screenshotContainer == null) {
            Log.w(this.TAG, "loadTemplateAndScreenshot failed: screenshotContainer = null.");
            screenshotCallback.onScreenshotComplete(null);
            return;
        }
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
        if (wallpaperInfo != null && wallpaperInfo2 != null) {
            BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this.activity), null, null, new C01251(templateId, screenshotCallback, commonConfig, wallpaperInfo, z, wallpaperInfo2, null), 3, null);
            return;
        }
        Log.e(this.TAG, "loadTemplateAndScreenshot: failed, lockWallpaperInfo:" + (wallpaperInfo != null) + " homeWallpaperInfo:" + (wallpaperInfo2 != null));
    }

    public final void onBackPressed() {
        dismissDialogIfShowing(this.dualClockDialog);
        dismissDialogIfShowing(this.aodApplyDialog);
        dismissDialogIfShowing(this.galleryDialog);
        dismissDialogIfShowing(this.downloadDialog);
    }

    public final void onCurrentTemplateScreenshotsSaveComplete(boolean z) {
        if (!this.hasPendingApplyTemplateTask) {
            Log.i(this.TAG, "onSaveComplete: pending apply task not found.");
            return;
        }
        Log.i(this.TAG, "onCurrentTemplateScreenshotsSaveComplete");
        applyTemplate(z);
        setHasPendingApplyTemplateTask(false);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        Boolean applyFlag = dialogInterface != null ? ViewUtil.INSTANCE.readApplyFlag(dialogInterface) : null;
        Log.i(this.TAG, "onDismiss -> isApplyCanceled: " + applyFlag);
        if (applyFlag != null && !applyFlag.booleanValue()) {
            this.lastApplyTime = SystemClock.uptimeMillis();
            return;
        }
        setApplying$default(this, false, false, 2, null);
        EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(this.activity);
        if (viewModel != null) {
            viewModel.setApplyTemplateConfig(null);
        }
    }

    public final void processScreenConfigurationChanged() {
        AlertDialog alertDialog;
        AlertDialog alertDialog2;
        AlertDialog alertDialog3;
        AlertDialog alertDialog4;
        resetDimensions();
        FrameLayout frameLayout = this.screenshotContainer;
        if (frameLayout != null) {
            frameLayout.setOutlineProvider(new RoundOutlineProvider(getScreenshotRadius()));
        }
        AlertDialog alertDialog5 = this.aodApplyDialog;
        if (alertDialog5 != null && alertDialog5.isShowing() && (alertDialog4 = this.aodApplyDialog) != null) {
            alertDialog4.dismiss();
        }
        AlertDialog alertDialog6 = this.dualClockDialog;
        if (alertDialog6 != null && alertDialog6.isShowing() && (alertDialog3 = this.dualClockDialog) != null) {
            alertDialog3.dismiss();
        }
        AlertDialog alertDialog7 = this.galleryDialog;
        if (alertDialog7 != null && alertDialog7.isShowing() && (alertDialog2 = this.galleryDialog) != null) {
            alertDialog2.dismiss();
        }
        AlertDialog alertDialog8 = this.downloadDialog;
        if (alertDialog8 == null || !alertDialog8.isShowing() || (alertDialog = this.downloadDialog) == null) {
            return;
        }
        alertDialog.dismiss();
    }

    public final void resetDimensions() {
        this.displayRealSize = null;
        this.screenshotRadius = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v2, types: [com.miui.keyguard.editor.homepage.util.ScreenshotCallback] */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r12v8 */
    public final void screenshotWithCurrentPreview(ScreenshotCallback screenshotCallback) {
        TemplateApplyController templateApplyController;
        ?? r12;
        Pair pairRequireScreenshotPreview;
        PresetTemplateConfig preset;
        try {
            try {
                if (this.screenshotContainer == null) {
                    throw new NullPointerException("screenshotContainer = null");
                }
                SelectItemProvider selectItemProvider = this.mSelectItemProvider;
                if (selectItemProvider == null || (pairRequireScreenshotPreview = selectItemProvider.requireScreenshotPreview()) == null) {
                    throw new NullPointerException("screenshot preview is not found");
                }
                View view = (View) pairRequireScreenshotPreview.getFirst();
                if (view == null) {
                    throw new NullPointerException("currentPreviewView = null");
                }
                TemplateItemBean templateItemBean = (TemplateItemBean) pairRequireScreenshotPreview.getSecond();
                if (templateItemBean == null || (preset = templateItemBean.getPreset()) == null) {
                    throw new NullPointerException("currentPreviewTemplate.preset = null");
                }
                FrameLayout frameLayout = this.screenshotContainer;
                if (frameLayout != null) {
                    frameLayout.setVisibility(0);
                }
                TemplateApplyController templateApplyController2 = this;
                try {
                    BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this.activity), Dispatchers.getMain(), null, new C01261(view, templateApplyController2, preset, screenshotCallback, null), 2, null);
                    return;
                } catch (Exception e) {
                    e = e;
                    r12 = screenshotCallback;
                    templateApplyController = templateApplyController2;
                }
            } catch (Exception e2) {
                e = e2;
                this = this;
            }
        } catch (Exception e3) {
            e = e3;
            templateApplyController = this;
            r12 = screenshotCallback;
        }
        Log.e(templateApplyController.TAG, "screenshotWithCurrentPreview", e);
        r12.onScreenshotComplete(null);
    }

    public final void setApplyCallback(ApplyCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.applyCallback = callback;
    }

    public final void setApplyMyTemplateCallback(Consumer callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.applyMyTemplateCallback = callback;
    }

    public final void setApplying(boolean z, boolean z2) {
        ApplyCallback applyCallback;
        this.isApplying = z;
        if (z) {
            Log.i(this.TAG, "start applyTemplate...");
            return;
        }
        Log.i(this.TAG, "complete applyTemplate...");
        if (z2 || (applyCallback = this.applyCallback) == null) {
            return;
        }
        applyCallback.onApplyCanceled();
    }

    public final void setCurrentTemplateScreenshotsSaving(boolean z) {
        this.isCurrentTemplateScreenshotsSaving = z;
        Log.i(this.TAG, "setCurrentTemplateScreenshotsSaving: " + z);
    }

    public final void setFloorTrackShowTag(int[] iArr) {
        this.floorTrackShowTag = iArr;
    }

    public final void setHasPendingApplyTemplateTask(boolean z) {
        this.hasPendingApplyTemplateTask = z;
        Log.i(this.TAG, "setHasPendingApplyTemplateTask: hasPendingApplyTemplateTask = " + z);
    }

    public final CommonConfig setSelectedItemConfig() {
        SelectItemProvider selectItemProvider = this.mSelectItemProvider;
        TemplateItemBean selectedItem = selectItemProvider != null ? selectItemProvider.getSelectedItem() : null;
        Integer numValueOf = selectedItem != null ? Integer.valueOf(selectedItem.getItemType()) : null;
        if (numValueOf != null && numValueOf.intValue() == 1) {
            return selectedItem.getCurrent();
        }
        if (numValueOf != null && numValueOf.intValue() == 2) {
            return getSelectedItemConfig(false, selectedItem);
        }
        if (numValueOf != null && numValueOf.intValue() == 3) {
            return getSelectedItemConfig(true, selectedItem);
        }
        return null;
    }

    public final void setTemplateConfigForHistoryBean(CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        SelectItemProvider selectItemProvider = this.mSelectItemProvider;
        TemplateItemBean selectedItem = selectItemProvider != null ? selectItemProvider.getSelectedItem() : null;
        if (selectedItem == null || selectedItem.getItemType() != 2) {
            return;
        }
        Context applicationContext = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        TemplateFilePathGenerator templateFilePathGenerator = new TemplateFilePathGenerator(applicationContext);
        String strGenerateCurrentTemplateFilePath = templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER);
        TemplateApi.Companion companion = TemplateApi.Companion;
        Context applicationContext2 = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "applicationContext");
        companion.getInstance(applicationContext2);
        DisplayMetrics displayMetrics = this.applicationContext.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        CommonConfig commonConfigDeepCopy = commonConfig != null ? commonConfig.deepCopy() : null;
        if (commonConfigDeepCopy != null && (lockscreenInfo = commonConfigDeepCopy.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            Pair pairDecodeWithSampling = decodeWithSampling(strGenerateCurrentTemplateFilePath, i, i2);
            Bitmap bitmap = (Bitmap) pairDecodeWithSampling.component1();
            int iIntValue = ((Number) pairDecodeWithSampling.component2()).intValue();
            wallpaperInfo.setCurrentWallpaper(bitmap);
            if (MagicType.INSTANCE.isDepth(Integer.valueOf(wallpaperInfo.getMagicType()))) {
                wallpaperInfo.setSubjectBitmap((Bitmap) decodeWithSampling(templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.SUBJECT), i, i2).getFirst());
            }
            WallpaperPositionInfo positionInfo = wallpaperInfo.getPositionInfo();
            float[] matrixArr = positionInfo != null ? positionInfo.getMatrixArr() : null;
            if (iIntValue > 1 && matrixArr != null && matrixArr.length >= 6) {
                float[] fArrCopyOf = Arrays.copyOf(matrixArr, matrixArr.length);
                Intrinsics.checkNotNullExpressionValue(fArrCopyOf, "copyOf(...)");
                float f = iIntValue;
                fArrCopyOf[0] = matrixArr[0] * f;
                fArrCopyOf[4] = matrixArr[4] * f;
                WallpaperPositionInfo positionInfo2 = wallpaperInfo.getPositionInfo();
                if (positionInfo2 != null) {
                    positionInfo2.setMatrixArr(fArrCopyOf);
                }
            }
        }
        selectedItem.setCurrent(commonConfigDeepCopy);
    }

    public final void showAllAskDialog(boolean z, TemplateItemBean templateItemBean, boolean z2, CommonConfig commonConfig, ScreenshotSource screenshotSource) {
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this.activity), null, null, new C01271(commonConfig, this, z, templateItemBean, z2, screenshotSource, null), 3, null);
    }

    public final void showAodApplyAskDialog(final CommonConfig commonConfig, final String str, final boolean z, final ScreenshotSource screenshotSource, final long j, final boolean z2, final boolean z3, boolean z4, final int i) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                TemplateApplyController.showAodApplyAskDialog$lambda$26(this.f$0, commonConfig, str, z, screenshotSource, j, z2, z3, i, dialogInterface, i2);
            }
        };
        AlertDialog alertDialogApplyToAodAlertDialog$default = AlertDialogsKtKt.applyToAodAlertDialog$default(this.activity, commonConfig, screenshotSource, z4, onClickListener, onClickListener, null, null, 192, null);
        this.aodApplyDialog = alertDialogApplyToAodAlertDialog$default;
        if (alertDialogApplyToAodAlertDialog$default == null) {
            setApplying$default(this, false, false, 2, null);
            return;
        }
        if (alertDialogApplyToAodAlertDialog$default != null) {
            alertDialogApplyToAodAlertDialog$default.setOnDismissListener(this);
        }
        AlertDialog alertDialog = this.aodApplyDialog;
        if (alertDialog != null) {
            alertDialog.show();
        }
        AlertDialogsKtKt.adaptN8AlertDialog$default(this.aodApplyDialog, false, false, false, 14, null);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0029 A[PHI: r3
  0x0029: PHI (r3v15 java.lang.String) = (r3v3 java.lang.String), (r3v19 java.lang.String) binds: [B:27:0x0047, B:14:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    public final void showApplyAllAskDialog(final boolean z, final TemplateItemBean templateItemBean, final boolean z2, final ScreenshotSource screenshotSource) throws Throwable {
        TemplateHistoryConfig history;
        String configUri;
        TemplateHistoryConfig history2;
        PresetTemplateConfig preset;
        PresetTemplateConfig preset2;
        final Ref.IntRef intRef = new Ref.IntRef();
        final String str = "";
        if (z) {
            intRef.element = (templateItemBean == null || (preset2 = templateItemBean.getPreset()) == null) ? 0 : preset2.getHideFlag();
            if (templateItemBean != null && (preset = templateItemBean.getPreset()) != null && (configUri = preset.getConfigUri()) != null) {
                str = configUri;
            }
        } else {
            intRef.element = (templateItemBean == null || (history2 = templateItemBean.getHistory()) == null) ? 0 : history2.getHideFlag();
            if (templateItemBean != null && (history = templateItemBean.getHistory()) != null && (configUri = history.getConfigUri()) != null) {
                str = configUri;
            }
        }
        EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(this.activity);
        CommonConfig applyTemplateConfig = viewModel != null ? viewModel.getApplyTemplateConfig() : null;
        if (applyTemplateConfig == null && !(this.activity instanceof EditorActivity)) {
            applyTemplateConfig = getSelectedItemConfig(false, templateItemBean);
        }
        CommonConfig commonConfig = applyTemplateConfig;
        if (commonConfig == null) {
            Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final Object get() {
                    return TemplateApplyController.showApplyAllAskDialog$lambda$17(this.f$0, str, z, intRef);
                }
            }).post(new java.util.function.Consumer() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda17
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TemplateApplyController.showApplyAllAskDialog$lambda$18(this.f$0, z, templateItemBean, z2, screenshotSource, (CommonConfig) obj);
                }
            });
        } else {
            Log.d(this.TAG, "showApplyAllAskDialog: use cache config for apply");
            showAllAskDialog(z, templateItemBean, z2, commonConfig, screenshotSource);
        }
    }

    public final void showDualClockCloseDialog(final DialogInterface.OnClickListener negativeListener, final DialogInterface.OnClickListener positiveListener, final boolean z) {
        Intrinsics.checkNotNullParameter(negativeListener, "negativeListener");
        Intrinsics.checkNotNullParameter(positiveListener, "positiveListener");
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TemplateApplyController.showDualClockCloseDialog$lambda$19(z, this, positiveListener, negativeListener, dialogInterface, i);
            }
        };
        AlertDialogBuilder alertDialogBuilder = new AlertDialogBuilder(this.activity, 0, 2, null);
        alertDialogBuilder.setTitle(R.string.kg_main_dual_clock_close_ask_dialog_title);
        alertDialogBuilder.setMessage(R.string.kg_main_dual_clock_close_ask_dialog_desc);
        alertDialogBuilder.setNegativeButton(R.string.kg_main_dual_clock_close_ask_dialog_negative_button, onClickListener);
        alertDialogBuilder.setPositiveButton(R.string.kg_main_dual_clock_close_ask_dialog_positive_button, onClickListener);
        AlertDialog alertDialogCreate = alertDialogBuilder.create();
        this.dualClockDialog = alertDialogCreate;
        if (alertDialogCreate == null) {
            setApplying$default(this, false, false, 2, null);
            return;
        }
        if (z) {
            Intrinsics.checkNotNull(alertDialogCreate);
            alertDialogCreate.setOnDismissListener(this);
        }
        AlertDialog alertDialog = this.dualClockDialog;
        Intrinsics.checkNotNull(alertDialog);
        alertDialog.show();
    }

    public final void showHistoryDeleteDialog(final DialogInterface.OnClickListener negativeListener, final DialogInterface.OnClickListener positiveListener, final boolean z, int i) {
        Intrinsics.checkNotNullParameter(negativeListener, "negativeListener");
        Intrinsics.checkNotNullParameter(positiveListener, "positiveListener");
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.util.TemplateApplyController$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                TemplateApplyController.showHistoryDeleteDialog$lambda$35(z, this, positiveListener, negativeListener, dialogInterface, i2);
            }
        };
        FragmentActivity fragmentActivity = this.activity;
        AlertDialogBuilder alertDialogBuilder = new AlertDialogBuilder(fragmentActivity, AlertDialogsKtKt.isDarkMode(fragmentActivity) ? miuix.appcompat.R.style.AlertDialog_Theme_Dark_Danger : miuix.appcompat.R.style.AlertDialog_Theme_Light_Danger);
        alertDialogBuilder.setTitle(alertDialogBuilder.getContext().getResources().getQuantityString(R.plurals.kg_history_delete_tip_title, i, Integer.valueOf(i)));
        alertDialogBuilder.setMessage(R.string.kg_history_delete_tip_desc);
        alertDialogBuilder.setNegativeButton(R.string.kg_dialog_apply_super_wallpaper_negative_button, onClickListener);
        alertDialogBuilder.setPositiveButton(R.string.kg_main_item_delete_button_text, onClickListener);
        AlertDialog alertDialogCreate = alertDialogBuilder.create();
        this.historyDeleteDialog = alertDialogCreate;
        if (alertDialogCreate == null) {
            setApplying$default(this, false, false, 2, null);
            return;
        }
        if (z && alertDialogCreate != null) {
            alertDialogCreate.setOnDismissListener(this);
        }
        AlertDialog alertDialog = this.historyDeleteDialog;
        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    public final void startApplyTemplate(boolean z, TemplateItemBean templateItemBean, String str, boolean z2, int i, ScreenshotSource screenshotSource) throws Throwable {
        String resourceType;
        TemplateHistoryConfig history;
        PresetTemplateConfig preset;
        TemplateItemConfig bindItemConfig;
        if (z) {
            resourceType = "image";
        } else {
            resourceType = (templateItemBean == null || (history = templateItemBean.getHistory()) == null) ? null : history.getResourceType();
        }
        if ((z2 || Intrinsics.areEqual(resourceType, "sensor")) && (i == 2 || i == 3 || i == 1)) {
            createAndShowGalleryDialog(z, templateItemBean, str, resourceType, screenshotSource);
            return;
        }
        if (!FileUtil.isSupportVideoDepth() || templateItemBean == null || (preset = templateItemBean.getPreset()) == null || (bindItemConfig = preset.getBindItemConfig()) == null || !bindItemConfig.getDepthVideo() || z2 || !CommonUtils.INSTANCE.checkThemeSupportDownload(this.activity)) {
            checkOrApplyTemplate(z, templateItemBean, str, i, screenshotSource);
        } else {
            createAndShowDownloadDialog(z, templateItemBean, str, i, screenshotSource);
        }
    }
}

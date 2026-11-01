package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorServiceManager;
import com.miui.keyguard.editor.base.ExitAnimationPerformer;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.ConstantsKt;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.Screenshots;
import com.miui.keyguard.editor.utils.SharedPreferencesUtil;
import com.miui.keyguard.editor.utils.SpecialWallpaperHelper;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.utils.segment.UtilsKt;
import com.miui.keyguard.editor.view.AlertDialogsKtKt;
import com.miui.keyguard.editor.view.FashionGalleryDialogsKt;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.maml.R;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandler;
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
/* JADX INFO: compiled from: TemplateApplier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateApplier {
    public final ExitAnimationPerformer applyAnimationPerformer;
    public final ApplyCallback applyCallback;
    public final EditorActivityViewModel editorViewModel;
    public final String tag;

    /* JADX INFO: compiled from: TemplateApplier.kt */
    @Metadata
    public interface ApplyCallback {

        /* JADX INFO: compiled from: TemplateApplier.kt */
        @Metadata
        public static final class DefaultImpls {
            public static /* synthetic */ void onApplied$default(ApplyCallback applyCallback, boolean z, ApplyCallback applyCallback2, ExitAnimationPerformer exitAnimationPerformer, int i, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onApplied");
                }
                if ((i & 2) != 0) {
                    applyCallback2 = null;
                }
                if ((i & 4) != 0) {
                    exitAnimationPerformer = null;
                }
                applyCallback.onApplied(z, applyCallback2, exitAnimationPerformer);
            }
        }

        void finish();

        void onApplied(boolean z, ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer);

        void onApplySuccessBeforeExitAnim();

        void onPrepareApply();

        void onStartApply(boolean z);
    }

    /* JADX INFO: compiled from: TemplateApplier.kt */
    @Metadata
    public static final class TemplateApplierParams {
        public boolean applyToAll;
        public CommonConfig config;
        public Bitmap homeOriginWallpaper;
        public Bitmap lockOriginWallpaper;
        public ScreenshotSource screenshots;
        public boolean shouldAddHistory;
        public long source;
        public Bitmap subject;

        public TemplateApplierParams(CommonConfig commonConfig, long j, boolean z, Bitmap bitmap, Bitmap bitmap2, ScreenshotSource screenshotSource, Bitmap bitmap3, boolean z2) {
            this.config = commonConfig;
            this.source = j;
            this.shouldAddHistory = z;
            this.lockOriginWallpaper = bitmap;
            this.homeOriginWallpaper = bitmap2;
            this.screenshots = screenshotSource;
            this.subject = bitmap3;
            this.applyToAll = z2;
        }

        public /* synthetic */ TemplateApplierParams(CommonConfig commonConfig, long j, boolean z, Bitmap bitmap, Bitmap bitmap2, ScreenshotSource screenshotSource, Bitmap bitmap3, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : commonConfig, (i & 2) != 0 ? 0L : j, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : bitmap, (i & 16) != 0 ? null : bitmap2, (i & 32) != 0 ? null : screenshotSource, (i & 64) != 0 ? null : bitmap3, (i & 128) != 0 ? false : z2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TemplateApplierParams)) {
                return false;
            }
            TemplateApplierParams templateApplierParams = (TemplateApplierParams) obj;
            return Intrinsics.areEqual(this.config, templateApplierParams.config) && this.source == templateApplierParams.source && this.shouldAddHistory == templateApplierParams.shouldAddHistory && Intrinsics.areEqual(this.lockOriginWallpaper, templateApplierParams.lockOriginWallpaper) && Intrinsics.areEqual(this.homeOriginWallpaper, templateApplierParams.homeOriginWallpaper) && Intrinsics.areEqual(this.screenshots, templateApplierParams.screenshots) && Intrinsics.areEqual(this.subject, templateApplierParams.subject) && this.applyToAll == templateApplierParams.applyToAll;
        }

        public final boolean getApplyToAll() {
            return this.applyToAll;
        }

        public final CommonConfig getConfig() {
            return this.config;
        }

        public final Bitmap getHomeOriginWallpaper() {
            return this.homeOriginWallpaper;
        }

        public final Bitmap getLockOriginWallpaper() {
            return this.lockOriginWallpaper;
        }

        public final ScreenshotSource getScreenshots() {
            return this.screenshots;
        }

        public final boolean getShouldAddHistory() {
            return this.shouldAddHistory;
        }

        public final long getSource() {
            return this.source;
        }

        public final Bitmap getSubject() {
            return this.subject;
        }

        public int hashCode() {
            CommonConfig commonConfig = this.config;
            int iHashCode = (((((commonConfig == null ? 0 : commonConfig.hashCode()) * 31) + Long.hashCode(this.source)) * 31) + Boolean.hashCode(this.shouldAddHistory)) * 31;
            Bitmap bitmap = this.lockOriginWallpaper;
            int iHashCode2 = (iHashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
            Bitmap bitmap2 = this.homeOriginWallpaper;
            int iHashCode3 = (iHashCode2 + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
            ScreenshotSource screenshotSource = this.screenshots;
            int iHashCode4 = (iHashCode3 + (screenshotSource == null ? 0 : screenshotSource.hashCode())) * 31;
            Bitmap bitmap3 = this.subject;
            return ((iHashCode4 + (bitmap3 != null ? bitmap3.hashCode() : 0)) * 31) + Boolean.hashCode(this.applyToAll);
        }

        public final void setApplyToAll(boolean z) {
            this.applyToAll = z;
        }

        public final void setScreenshots(ScreenshotSource screenshotSource) {
            this.screenshots = screenshotSource;
        }

        public String toString() {
            return "TemplateApplierParams(config=" + this.config + ", source=" + this.source + ", shouldAddHistory=" + this.shouldAddHistory + ", lockOriginWallpaper=" + this.lockOriginWallpaper + ", homeOriginWallpaper=" + this.homeOriginWallpaper + ", screenshots=" + this.screenshots + ", subject=" + this.subject + ", applyToAll=" + this.applyToAll + ")";
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$showApplyDialogIfWallpaperChanged$1, reason: invalid class name */
    /* JADX INFO: compiled from: TemplateApplier.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$showApplyDialogIfWallpaperChanged$1", f = "TemplateApplier.kt", l = {238, 241, 254, 263}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ TemplateApplierParams $params;
        public final /* synthetic */ BaseTemplateView $templateView;
        public final /* synthetic */ boolean $wallpaperChanged;
        public Object L$0;
        public boolean Z$0;
        public boolean Z$1;
        public int label;
        public final /* synthetic */ TemplateApplier this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, TemplateApplierParams templateApplierParams, TemplateApplier templateApplier, BaseTemplateView baseTemplateView, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$params = templateApplierParams;
            this.this$0 = templateApplier;
            this.$templateView = baseTemplateView;
            this.$wallpaperChanged = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$context, this.$params, this.this$0, this.$templateView, this.$wallpaperChanged, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:23:0x00a3  */
        /* JADX WARN: Code duplicated, block: B:26:0x00ad A[ADDED_TO_REGION] */
        /* JADX WARN: Code duplicated, block: B:28:0x00bd  */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x00d5, code lost:
        
            if (r3.showDialogIfApplySpecialWallpaper(r4, r5, r6, r7, false, r10) == r0) goto L30;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r11) {
            /*
                Method dump skipped, instruction units count: 219
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.edit.base.TemplateApplier.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogByWallpaperType$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplier.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogByWallpaperType$1", f = "TemplateApplier.kt", l = {182, 198, 206}, m = "invokeSuspend")
    public static final class C01181 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ TemplateApplierParams $params;
        public final /* synthetic */ BaseTemplateView $templateView;
        public final /* synthetic */ boolean $wallpaperChanged;
        public Object L$0;
        public boolean Z$0;
        public int label;
        public final /* synthetic */ TemplateApplier this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01181(TemplateApplierParams templateApplierParams, BaseTemplateView baseTemplateView, Context context, TemplateApplier templateApplier, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$params = templateApplierParams;
            this.$templateView = baseTemplateView;
            this.$context = context;
            this.this$0 = templateApplier;
            this.$wallpaperChanged = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01181(this.$params, this.$templateView, this.$context, this.this$0, this.$wallpaperChanged, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01181) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:47:0x00c4  */
        /* JADX WARN: Code duplicated, block: B:49:0x00d4  */
        /* JADX WARN: Code duplicated, block: B:54:0x00e5  */
        /* JADX WARN: Code duplicated, block: B:58:0x00fa  */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00f7, code lost:
        
            if (r6.showDialogIfApplySpecialWallpaper(r7, r8, r9, r10, r11, r13) == r0) goto L57;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                Method dump skipped, instruction units count: 267
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.edit.base.TemplateApplier.C01181.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogIfApplySpecialWallpaper$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplier.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier", f = "TemplateApplier.kt", l = {289}, m = "showDialogIfApplySpecialWallpaper")
    public static final class C01191 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public boolean Z$0;
        public boolean Z$1;
        public int label;
        public /* synthetic */ Object result;

        public C01191(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TemplateApplier.this.showDialogIfApplySpecialWallpaper(null, null, null, false, false, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogIfGalleryAvailable$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplier.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogIfGalleryAvailable$1", f = "TemplateApplier.kt", l = {R.styleable.AppCompatTheme_toolbarNavigationButtonStyle}, m = "invokeSuspend")
    public static final class C01201 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ Ref.ObjectRef $params;
        public final /* synthetic */ Ref.IntRef $size;
        public final /* synthetic */ BaseTemplateView $templateView;
        public final /* synthetic */ int $total;
        public final /* synthetic */ boolean $wallpaperChanged;
        public int label;
        public final /* synthetic */ TemplateApplier this$0;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogIfGalleryAvailable$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TemplateApplier.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogIfGalleryAvailable$1$1", f = "TemplateApplier.kt", l = {}, m = "invokeSuspend")
        public static final class C00271 extends SuspendLambda implements Function3<Screenshots, Integer, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Context $context;
            public final /* synthetic */ Ref.ObjectRef $params;
            public final /* synthetic */ Ref.IntRef $size;
            public final /* synthetic */ BaseTemplateView $templateView;
            public final /* synthetic */ int $total;
            public final /* synthetic */ boolean $wallpaperChanged;
            public /* synthetic */ int I$0;
            public /* synthetic */ Object L$0;
            public int label;
            public final /* synthetic */ TemplateApplier this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00271(Ref.IntRef intRef, Ref.ObjectRef objectRef, TemplateApplier templateApplier, BaseTemplateView baseTemplateView, int i, Context context, boolean z, Continuation continuation) {
                super(3, continuation);
                this.$size = intRef;
                this.$params = objectRef;
                this.this$0 = templateApplier;
                this.$templateView = baseTemplateView;
                this.$total = i;
                this.$context = context;
                this.$wallpaperChanged = z;
            }

            public static final Unit invokeSuspend$lambda$0(TemplateApplier templateApplier, Context context, BaseTemplateView baseTemplateView, Ref.ObjectRef objectRef, boolean z, DialogInterface dialogInterface, int i) {
                Object obj = objectRef.element;
                Intrinsics.checkNotNull(obj);
                templateApplier.showDialogByWallpaperType(context, baseTemplateView, (TemplateApplierParams) obj, z);
                return Unit.INSTANCE;
            }

            public static final void invokeSuspend$lambda$2$lambda$1(TemplateApplier templateApplier, DialogInterface dialogInterface) {
                ApplyCallback.DefaultImpls.onApplied$default(templateApplier.applyCallback, false, null, null, 6, null);
                EditorActivityViewModel editorActivityViewModel = templateApplier.editorViewModel;
                if (editorActivityViewModel != null) {
                    editorActivityViewModel.updateApplyStatus(2);
                }
            }

            public final Object invoke(Screenshots screenshots, int i, Continuation continuation) {
                C00271 c00271 = new C00271(this.$size, this.$params, this.this$0, this.$templateView, this.$total, this.$context, this.$wallpaperChanged, continuation);
                c00271.L$0 = screenshots;
                c00271.I$0 = i;
                return c00271.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return invoke((Screenshots) obj, ((Number) obj2).intValue(), (Continuation) obj3);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ScreenshotSource screenshots;
                Map<String, Boolean> hierarchyCheckResult;
                ScreenshotSource screenshots2;
                ScreenshotSource screenshots3;
                ScreenshotSource screenshots4;
                Map<String, Boolean> hierarchyCheckResult2;
                ScreenshotSource screenshots5;
                ScreenshotSource screenshots6;
                ScreenshotSource screenshots7;
                Map<String, Boolean> hierarchyCheckResult3;
                ScreenshotSource screenshots8;
                ScreenshotSource screenshots9;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Screenshots screenshots10 = (Screenshots) this.L$0;
                int i = this.I$0;
                boolean z = true;
                this.$size.element++;
                DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                if (i == deviceScreenshotHelper.getSCREENSHOT_CURRENT()) {
                    this.$params.element = this.this$0.buildApplierParams(this.$templateView, screenshots10);
                } else if (i == deviceScreenshotHelper.getSCREENSHOT_SMALL()) {
                    TemplateApplierParams templateApplierParams = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams != null && (screenshots9 = templateApplierParams.getScreenshots()) != null) {
                        screenshots9.setSmallScreenshot(screenshots10.getSmall());
                    }
                    TemplateApplierParams templateApplierParams2 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams2 != null && (screenshots8 = templateApplierParams2.getScreenshots()) != null) {
                        screenshots8.setSmallWallpaperScreenshot(screenshots10.getSmallWallpaper());
                    }
                    TemplateApplierParams templateApplierParams3 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams3 != null && (screenshots7 = templateApplierParams3.getScreenshots()) != null && (hierarchyCheckResult3 = screenshots7.getHierarchyCheckResult()) != null) {
                        hierarchyCheckResult3.putAll(screenshots10.getHierarchyCheckResult());
                    }
                } else if (i == deviceScreenshotHelper.getSCREENSHOT_LARGE_PORT()) {
                    TemplateApplierParams templateApplierParams4 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams4 != null && (screenshots6 = templateApplierParams4.getScreenshots()) != null) {
                        screenshots6.setLargePortraitScreenshot(screenshots10.getLargePortrait());
                    }
                    TemplateApplierParams templateApplierParams5 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams5 != null && (screenshots5 = templateApplierParams5.getScreenshots()) != null) {
                        screenshots5.setLargePortWallpaperScreenshot(screenshots10.getLargePortWallpaper());
                    }
                    TemplateApplierParams templateApplierParams6 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams6 != null && (screenshots4 = templateApplierParams6.getScreenshots()) != null && (hierarchyCheckResult2 = screenshots4.getHierarchyCheckResult()) != null) {
                        hierarchyCheckResult2.putAll(screenshots10.getHierarchyCheckResult());
                    }
                } else if (i == deviceScreenshotHelper.getSCREENSHOT_LARGE_LAND()) {
                    TemplateApplierParams templateApplierParams7 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams7 != null && (screenshots3 = templateApplierParams7.getScreenshots()) != null) {
                        screenshots3.setLargeLandscapeScreenshot(screenshots10.getLargeLandscape());
                    }
                    TemplateApplierParams templateApplierParams8 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams8 != null && (screenshots2 = templateApplierParams8.getScreenshots()) != null) {
                        screenshots2.setLargeLandWallpaperScreenshot(screenshots10.getLargeLandWallpaper());
                    }
                    TemplateApplierParams templateApplierParams9 = (TemplateApplierParams) this.$params.element;
                    if (templateApplierParams9 != null && (screenshots = templateApplierParams9.getScreenshots()) != null && (hierarchyCheckResult = screenshots.getHierarchyCheckResult()) != null) {
                        hierarchyCheckResult.putAll(screenshots10.getHierarchyCheckResult());
                    }
                } else {
                    Boxing.boxInt(Log.e(this.this$0.tag, "screen shot error bitmapId=" + i));
                }
                if (this.$size.element >= this.$total || i == deviceScreenshotHelper.getSCREENSHOT_SKIP()) {
                    Log.d(this.this$0.tag, "showDialogIfGalleryAvailable: edit screenshot finish params=" + (this.$params.element != null));
                    deviceScreenshotHelper.setCurrentHomeTemplateWeak(null);
                    TemplateApplierParams templateApplierParams10 = (TemplateApplierParams) this.$params.element;
                    CommonConfig config = templateApplierParams10 != null ? templateApplierParams10.getConfig() : null;
                    Intrinsics.checkNotNull(config);
                    WallpaperInfo wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo();
                    String resourceType = wallpaperInfo != null ? wallpaperInfo.getResourceType() : null;
                    if (!Intrinsics.areEqual(resourceType, "video") && !Intrinsics.areEqual(resourceType, "sensor")) {
                        z = false;
                    }
                    if (this.$templateView.isGalleryOpened() && z) {
                        final Context context = this.$context;
                        final TemplateApplier templateApplier = this.this$0;
                        final BaseTemplateView baseTemplateView = this.$templateView;
                        final Ref.ObjectRef objectRef = this.$params;
                        final boolean z2 = this.$wallpaperChanged;
                        AlertDialog alertDialogCreateFashionGalleryDialog = FashionGalleryDialogsKt.createFashionGalleryDialog(context, new Function2() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogIfGalleryAvailable$1$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                return TemplateApplier.C01201.C00271.invokeSuspend$lambda$0(templateApplier, context, baseTemplateView, objectRef, z2, (DialogInterface) obj2, ((Integer) obj3).intValue());
                            }
                        });
                        final TemplateApplier templateApplier2 = this.this$0;
                        alertDialogCreateFashionGalleryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogIfGalleryAvailable$1$1$$ExternalSyntheticLambda1
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                TemplateApplier.C01201.C00271.invokeSuspend$lambda$2$lambda$1(templateApplier2, dialogInterface);
                            }
                        });
                        alertDialogCreateFashionGalleryDialog.show();
                        TrackHelper trackHelper = TrackHelper.INSTANCE;
                        Context applicationContext = this.$context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                        trackHelper.trackVideoDialogExpose(applicationContext, "一级编辑页");
                    } else {
                        TemplateApplier templateApplier3 = this.this$0;
                        Context context2 = this.$context;
                        BaseTemplateView baseTemplateView2 = this.$templateView;
                        Object obj2 = this.$params.element;
                        Intrinsics.checkNotNull(obj2);
                        templateApplier3.showDialogByWallpaperType(context2, baseTemplateView2, (TemplateApplierParams) obj2, this.$wallpaperChanged);
                    }
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01201(Context context, BaseTemplateView baseTemplateView, Ref.IntRef intRef, Ref.ObjectRef objectRef, TemplateApplier templateApplier, int i, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$templateView = baseTemplateView;
            this.$size = intRef;
            this.$params = objectRef;
            this.this$0 = templateApplier;
            this.$total = i;
            this.$wallpaperChanged = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01201(this.$context, this.$templateView, this.$size, this.$params, this.this$0, this.$total, this.$wallpaperChanged, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01201) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                Context context = this.$context;
                BaseTemplateView baseTemplateView = this.$templateView;
                CommonConfig templateConfig = baseTemplateView.getTemplateConfig();
                Intrinsics.checkNotNull(templateConfig);
                CommonConfig commonConfigDeepCopy = templateConfig.deepCopy();
                BaseTemplateView baseTemplateView2 = this.$templateView;
                C00271 c00271 = new C00271(this.$size, this.$params, this.this$0, baseTemplateView2, this.$total, this.$context, this.$wallpaperChanged, null);
                this.label = 1;
                if (DeviceScreenshotHelper.takeMultiScreenshotsForTemplateView$default(deviceScreenshotHelper, context, baseTemplateView, commonConfigDeepCopy, baseTemplateView2, false, c00271, this, 16, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$takeScreenshotThenApplyTemplate$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateApplier.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$takeScreenshotThenApplyTemplate$1", f = "TemplateApplier.kt", l = {406, 415}, m = "invokeSuspend")
    public static final class C01211 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ TemplateApplierParams $params;
        public final /* synthetic */ BaseTemplateView $templateView;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$takeScreenshotThenApplyTemplate$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TemplateApplier.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$takeScreenshotThenApplyTemplate$1$1", f = "TemplateApplier.kt", l = {}, m = "invokeSuspend")
        public static final class C00281 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ BaseTemplateView $templateView;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00281(BaseTemplateView baseTemplateView, Continuation continuation) {
                super(2, continuation);
                this.$templateView = baseTemplateView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00281(this.$templateView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00281) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws InterruptedException {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$templateView.takeVideoDepthScreenshot();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.TemplateApplier$takeScreenshotThenApplyTemplate$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: TemplateApplier.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$takeScreenshotThenApplyTemplate$1$2", f = "TemplateApplier.kt", l = {}, m = "invokeSuspend")
        public static final class AnonymousClass2 extends SuspendLambda implements Function3<Screenshots, Integer, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Context $context;
            public final /* synthetic */ TemplateApplierParams $params;
            public final /* synthetic */ Ref.IntRef $size;
            public final /* synthetic */ BaseTemplateView $templateView;
            public final /* synthetic */ int $total;
            public /* synthetic */ int I$0;
            public /* synthetic */ Object L$0;
            public int label;
            public final /* synthetic */ TemplateApplier this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(Ref.IntRef intRef, TemplateApplierParams templateApplierParams, TemplateApplier templateApplier, int i, Context context, BaseTemplateView baseTemplateView, Continuation continuation) {
                super(3, continuation);
                this.$size = intRef;
                this.$params = templateApplierParams;
                this.this$0 = templateApplier;
                this.$total = i;
                this.$context = context;
                this.$templateView = baseTemplateView;
            }

            public final Object invoke(Screenshots screenshots, int i, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$size, this.$params, this.this$0, this.$total, this.$context, this.$templateView, continuation);
                anonymousClass2.L$0 = screenshots;
                anonymousClass2.I$0 = i;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return invoke((Screenshots) obj, ((Number) obj2).intValue(), (Continuation) obj3);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Map<String, Boolean> hierarchyCheckResult;
                Map<String, Boolean> hierarchyCheckResult2;
                Map<String, Boolean> hierarchyCheckResult3;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Screenshots screenshots = (Screenshots) this.L$0;
                int i = this.I$0;
                this.$size.element++;
                DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
                if (i == deviceScreenshotHelper.getSCREENSHOT_CURRENT()) {
                    TemplateApplierParams templateApplierParams = this.$params;
                    ScreenshotSource screenshotSource = new ScreenshotSource(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 262143, null);
                    Context context = this.$context;
                    ScreenshotSourceKt.setCurrentScreenshot(screenshotSource, context, screenshots.getCurrentScreenshot(context));
                    ScreenshotSourceKt.setCurrentWallpaperScreenshot(screenshotSource, context, screenshots.getCurrentWallpaperScreenshot(context));
                    screenshotSource.setHierarchyCheckResult(screenshots.getHierarchyCheckResult());
                    templateApplierParams.setScreenshots(screenshotSource);
                } else if (i == deviceScreenshotHelper.getSCREENSHOT_SMALL()) {
                    ScreenshotSource screenshots2 = this.$params.getScreenshots();
                    if (screenshots2 != null) {
                        screenshots2.setSmallScreenshot(screenshots.getSmall());
                    }
                    ScreenshotSource screenshots3 = this.$params.getScreenshots();
                    if (screenshots3 != null) {
                        screenshots3.setSmallWallpaperScreenshot(screenshots.getSmallWallpaper());
                    }
                    ScreenshotSource screenshots4 = this.$params.getScreenshots();
                    if (screenshots4 != null && (hierarchyCheckResult3 = screenshots4.getHierarchyCheckResult()) != null) {
                        hierarchyCheckResult3.putAll(screenshots.getHierarchyCheckResult());
                    }
                } else if (i == deviceScreenshotHelper.getSCREENSHOT_LARGE_PORT()) {
                    ScreenshotSource screenshots5 = this.$params.getScreenshots();
                    if (screenshots5 != null) {
                        screenshots5.setLargePortraitScreenshot(screenshots.getLargePortrait());
                    }
                    ScreenshotSource screenshots6 = this.$params.getScreenshots();
                    if (screenshots6 != null) {
                        screenshots6.setLargePortWallpaperScreenshot(screenshots.getLargePortWallpaper());
                    }
                    ScreenshotSource screenshots7 = this.$params.getScreenshots();
                    if (screenshots7 != null && (hierarchyCheckResult2 = screenshots7.getHierarchyCheckResult()) != null) {
                        hierarchyCheckResult2.putAll(screenshots.getHierarchyCheckResult());
                    }
                } else if (i == deviceScreenshotHelper.getSCREENSHOT_LARGE_LAND()) {
                    ScreenshotSource screenshots8 = this.$params.getScreenshots();
                    if (screenshots8 != null) {
                        screenshots8.setLargeLandscapeScreenshot(screenshots.getLargeLandscape());
                    }
                    ScreenshotSource screenshots9 = this.$params.getScreenshots();
                    if (screenshots9 != null) {
                        screenshots9.setLargeLandWallpaperScreenshot(screenshots.getLargeLandWallpaper());
                    }
                    ScreenshotSource screenshots10 = this.$params.getScreenshots();
                    if (screenshots10 != null && (hierarchyCheckResult = screenshots10.getHierarchyCheckResult()) != null) {
                        hierarchyCheckResult.putAll(screenshots.getHierarchyCheckResult());
                    }
                } else {
                    Boxing.boxInt(Log.e(this.this$0.tag, "screen shot error bitmapId=" + i));
                }
                if (this.$size.element >= this.$total || i == deviceScreenshotHelper.getSCREENSHOT_SKIP()) {
                    this.$params.setScreenshots(new ScreenshotSource(screenshots.getSmall(), screenshots.getLargePortrait(), screenshots.getLargeLandscape(), null, null, null, null, null, null, null, null, null, null, null, null, null, screenshots.getHierarchyCheckResult(), null, 196600, null));
                    this.this$0.onApplyTemplate(this.$context, this.$templateView, this.$params);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01211(BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, Context context, Continuation continuation) {
            super(2, continuation);
            this.$templateView = baseTemplateView;
            this.$params = templateApplierParams;
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TemplateApplier.this.new C01211(this.$templateView, this.$params, this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01211) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:33:0x00c5  */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x008d, code lost:
        
            if (com.miui.keyguard.editor.utils.DeviceScreenshotHelper.takeMultiScreenshotsForTemplateView$default(r3, r14, r1, r12, r7, false, r4, r10, 16, null) == r0) goto L24;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                Method dump skipped, instruction units count: 203
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.edit.base.TemplateApplier.C01211.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public TemplateApplier(ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer, EditorActivityViewModel editorActivityViewModel) {
        Intrinsics.checkNotNullParameter(applyCallback, "applyCallback");
        this.applyCallback = applyCallback;
        this.applyAnimationPerformer = exitAnimationPerformer;
        this.editorViewModel = editorActivityViewModel;
        this.tag = "Keyguard-Editor-TemplateApplier";
    }

    public static final Unit onApplyTemplate$lambda$10(TemplateApplier templateApplier, Throwable th) {
        Log.d(templateApplier.tag, "applyJob finish: " + th + " " + (th != null ? th.getCause() : null) + " " + (th != null ? th.getMessage() : null));
        return Unit.INSTANCE;
    }

    public static final void showAodDialog$lambda$7(Ref.BooleanRef booleanRef, TemplateApplier templateApplier, Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, DialogInterface dialogInterface, int i) {
        booleanRef.element = true;
        templateApplier.takeScreenshotThenApplyTemplate(context, baseTemplateView, templateApplierParams);
    }

    public static final void showAodDialog$lambda$8(TemplateApplier templateApplier, Context context, Ref.BooleanRef booleanRef, DialogInterface dialogInterface) {
        ApplyCallback.DefaultImpls.onApplied$default(templateApplier.applyCallback, false, null, null, 6, null);
        templateApplier.updateEnterApplying(context, booleanRef.element);
    }

    public static final void showAodDialog$lambda$9(TemplateApplier templateApplier, DialogInterface dialogInterface) {
        EditorActivityViewModel editorActivityViewModel = templateApplier.editorViewModel;
        if (editorActivityViewModel != null) {
            editorActivityViewModel.updateApplyStatus(2);
        }
    }

    public static final void showSuperWallpaperOrLinkageVideoDialog$lambda$1(Ref.BooleanRef booleanRef, TemplateApplier templateApplier, Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, boolean z, DialogInterface dialogInterface, int i) {
        booleanRef.element = true;
        templateApplier.showDialogIfAodDisabled(context, baseTemplateView, templateApplierParams, z);
    }

    public static final void showSuperWallpaperOrLinkageVideoDialog$lambda$2(TemplateApplier templateApplier, Ref.BooleanRef booleanRef, DialogInterface dialogInterface, int i) {
        ApplyCallback.DefaultImpls.onApplied$default(templateApplier.applyCallback, false, null, null, 6, null);
        booleanRef.element = false;
        EditorActivityViewModel editorActivityViewModel = templateApplier.editorViewModel;
        if (editorActivityViewModel != null) {
            editorActivityViewModel.updateApplyStatus(2);
        }
    }

    public static final void showSuperWallpaperOrLinkageVideoDialog$lambda$4$lambda$3(TemplateApplier templateApplier, Context context, Ref.BooleanRef booleanRef, DialogInterface dialogInterface) {
        ApplyCallback.DefaultImpls.onApplied$default(templateApplier.applyCallback, false, null, null, 6, null);
        templateApplier.updateEnterApplying(context, booleanRef.element);
        EditorActivityViewModel editorActivityViewModel = templateApplier.editorViewModel;
        if (editorActivityViewModel != null) {
            editorActivityViewModel.updateApplyStatus(2);
        }
    }

    public static final void showSuperWallpaperOrLinkageVideoDialog$lambda$6$lambda$5(TemplateApplier templateApplier, Context context, Ref.BooleanRef booleanRef, DialogInterface dialogInterface) {
        ApplyCallback.DefaultImpls.onApplied$default(templateApplier.applyCallback, false, null, null, 6, null);
        templateApplier.updateEnterApplying(context, booleanRef.element);
        EditorActivityViewModel editorActivityViewModel = templateApplier.editorViewModel;
        if (editorActivityViewModel != null) {
            editorActivityViewModel.updateApplyStatus(2);
        }
    }

    public final void applyTemplate(BaseTemplateView templateView, boolean z) {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo3;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        CommonConfig commonConfigTemplateConfig = templateView.templateConfig();
        refreshPositionInfo(templateView, commonConfigTemplateConfig);
        Object currentWallpaper = null;
        if (((commonConfigTemplateConfig == null || (lockscreenInfo2 = commonConfigTemplateConfig.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getCurrentWallpaper()) != null) {
            WallpaperInfo wallpaperInfo4 = commonConfigTemplateConfig.getHomeInfo().getWallpaperInfo();
            if ((wallpaperInfo4 != null ? wallpaperInfo4.getCurrentWallpaper() : null) != null) {
                this.applyCallback.onPrepareApply();
                EditorActivityViewModel editorActivityViewModel = this.editorViewModel;
                if (editorActivityViewModel != null) {
                    editorActivityViewModel.updateApplyStatus(1);
                }
                if (checkWallpaperPositionInfo(commonConfigTemplateConfig)) {
                    Context context = templateView.getContext();
                    Intrinsics.checkNotNull(context);
                    showDialogIfGalleryAvailable(context, templateView, z);
                    return;
                }
                String str = this.tag;
                WallpaperInfo wallpaperInfo5 = commonConfigTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                WallpaperPositionInfo positionInfo = wallpaperInfo5 != null ? wallpaperInfo5.getPositionInfo() : null;
                WallpaperInfo wallpaperInfo6 = commonConfigTemplateConfig.getHomeInfo().getWallpaperInfo();
                Log.e(str, "positionInfo error lock=" + positionInfo + " home=" + (wallpaperInfo6 != null ? wallpaperInfo6.getPositionInfo() : null));
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
                } catch (Exception unused) {
                }
                ApplyCallback.DefaultImpls.onApplied$default(this.applyCallback, false, null, null, 6, null);
                EditorActivityViewModel editorActivityViewModel2 = this.editorViewModel;
                if (editorActivityViewModel2 != null) {
                    editorActivityViewModel2.updateApplyStatus(2);
                    return;
                }
                return;
            }
        }
        String str2 = this.tag;
        boolean z2 = commonConfigTemplateConfig == null;
        boolean z3 = ((commonConfigTemplateConfig == null || (lockscreenInfo = commonConfigTemplateConfig.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getCurrentWallpaper()) == null;
        if (commonConfigTemplateConfig != null && (homeInfo = commonConfigTemplateConfig.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
            currentWallpaper = wallpaperInfo.getCurrentWallpaper();
        }
        Log.e(str2, "applyTemplate error, templateConfig is null: lock=" + z2 + " lock=" + z3 + " home=" + (currentWallpaper == null));
        try {
            KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
        } catch (Exception unused2) {
        }
    }

    public final TemplateApplierParams buildApplierParams(BaseTemplateView baseTemplateView, Screenshots screenshots) {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        CommonConfig commonConfigTemplateConfig = baseTemplateView.templateConfig();
        Context context = baseTemplateView.getContext();
        ScreenshotSource screenshotSource = new ScreenshotSource(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 262143, null);
        screenshotSource.setHierarchyCheckResult(screenshots.getHierarchyCheckResult());
        Intrinsics.checkNotNull(context);
        ScreenshotSourceKt.setCurrentScreenshot(screenshotSource, context, screenshots.getCurrentScreenshot(context));
        ScreenshotSourceKt.setCurrentWallpaperScreenshot(screenshotSource, context, screenshots.getCurrentWallpaperScreenshot(context));
        long templateSource = baseTemplateView.getTemplateSource();
        EditFragment editFragment = baseTemplateView.getEditFragment();
        boolean shouldAddHistory = editFragment != null ? editFragment.getShouldAddHistory() : false;
        Bitmap currentWallpaper = (commonConfigTemplateConfig == null || (lockscreenInfo = commonConfigTemplateConfig.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getCurrentWallpaper();
        Bitmap currentWallpaper2 = (commonConfigTemplateConfig == null || (homeInfo = commonConfigTemplateConfig.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper();
        SegmentInfo subjectInfo = baseTemplateView.getSubjectInfo();
        return new TemplateApplierParams(commonConfigTemplateConfig, templateSource, shouldAddHistory, currentWallpaper, currentWallpaper2, screenshotSource, subjectInfo != null ? subjectInfo.getBitmap() : null, false, 128, null);
    }

    public final boolean checkInvalidSubject(Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo) {
        if (wallpaperPositionInfo.getRight() <= wallpaperPositionInfo.getLeft() || wallpaperPositionInfo.getBottom() <= wallpaperPositionInfo.getTop()) {
            Log.e(this.tag, "checkInvalidSubject -> invalid size, positionInfo = " + wallpaperPositionInfo);
            return false;
        }
        if (wallpaperPositionInfo.getRight() <= bitmap.getWidth() && wallpaperPositionInfo.getBottom() <= bitmap.getHeight()) {
            return true;
        }
        Log.e(this.tag, "checkInvalidSubject -> invalid positionInfo = " + wallpaperPositionInfo + " , bitmap = " + bitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmap.getHeight());
        return false;
    }

    public final boolean checkWallpaperPositionInfo(CommonConfig commonConfig) {
        WallpaperPositionInfo positionInfo;
        WallpaperInfo wallpaperInfo;
        WallpaperPositionInfo positionInfo2;
        WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        return (wallpaperInfo2 == null || (positionInfo = wallpaperInfo2.getPositionInfo()) == null || (wallpaperInfo = commonConfig.getHomeInfo().getWallpaperInfo()) == null || (positionInfo2 = wallpaperInfo.getPositionInfo()) == null || !isPositionInfoValid(positionInfo) || !isPositionInfoValid(positionInfo2)) ? false : true;
    }

    public final boolean isPositionInfoValid(WallpaperPositionInfo wallpaperPositionInfo) {
        return wallpaperPositionInfo.getLeft() < wallpaperPositionInfo.getRight() && wallpaperPositionInfo.getTop() < wallpaperPositionInfo.getBottom();
    }

    public final void markHasScreenshotForExternalTemplate(Context context) {
        SharedPreferencesUtil.INSTANCE.setBoolean(context, "has_screenshot_for_external_template", true);
    }

    public final void onAppliedInternal(Context context, BaseTemplateView baseTemplateView) {
        markHasScreenshotForExternalTemplate(context);
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(baseTemplateView.getEditFragment());
        boolean z = false;
        if (viewModel != null && viewModel.getSelectTemplate() == 0) {
            z = true;
        }
        Log.i(this.tag, "isCallingFromKeyguard " + baseTemplateView.isCallingFromKeyguard() + " selectLock=" + z);
        if (baseTemplateView.isCallingFromKeyguard() && z) {
            EditorServiceManager.Companion.getInstance().sendStartAnimationRequest(baseTemplateView, true, this.applyCallback, this.applyAnimationPerformer);
        }
    }

    public final void onApplyTemplate(Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams) {
        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), null, null, new TemplateApplier$onApplyTemplate$applyJob$1(this, context, baseTemplateView, templateApplierParams, null), 3, null).invokeOnCompletion(new Function1() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TemplateApplier.onApplyTemplate$lambda$10(this.f$0, (Throwable) obj);
            }
        });
    }

    public final void refreshPositionInfo(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || wallpaperInfo.getPositionInfo() != null || wallpaperInfo.getResourceType() == null || !WallpaperEditor.Companion.canScale(wallpaperInfo.getResourceType())) {
            return;
        }
        IWallpaperLayer wallpaperLayer = baseTemplateView.getWallpaperLayer();
        if (wallpaperLayer == null) {
            Log.w(this.tag, "refreshPositionInfo: wallpaperLayer is null, skip refresh");
            return;
        }
        Bitmap currentWallpaper = wallpaperInfo.getCurrentWallpaper();
        if (currentWallpaper == null) {
            currentWallpaper = wallpaperInfo.getCurrentSmallSensorWallpaper();
        }
        if (currentWallpaper == null) {
            Log.w(this.tag, "refreshPositionInfo: bitmap is null, skip refresh");
            return;
        }
        wallpaperInfo.setPositionInfo(wallpaperLayer.getPosition(currentWallpaper));
        Log.i(this.tag, "refreshPositionInfo: refreshed lock positionInfo=" + wallpaperInfo.getPositionInfo());
    }

    public final boolean shouldAddToGallery(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        boolean zIsGalleryOpened = baseTemplateView.isGalleryOpened();
        if (!zIsGalleryOpened) {
            return false;
        }
        if (baseTemplateView.getTemplateSource() == -1) {
            return baseTemplateView.isWallpaperChanged() || baseTemplateView.isPositionInfoChanged();
        }
        return zIsGalleryOpened;
    }

    public final void showAodDialog(final Context context, final BaseTemplateView baseTemplateView, final TemplateApplierParams templateApplierParams) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TemplateApplier.showAodDialog$lambda$7(booleanRef, this, context, baseTemplateView, templateApplierParams, dialogInterface, i);
            }
        };
        CommonConfig config = templateApplierParams.getConfig();
        ScreenshotSource screenshots = templateApplierParams.getScreenshots();
        Intrinsics.checkNotNull(screenshots);
        AlertDialog alertDialogApplyToAodAlertDialog = AlertDialogsKtKt.applyToAodAlertDialog(context, config, screenshots, baseTemplateView.isGalleryOpened(), onClickListener, onClickListener, new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                TemplateApplier.showAodDialog$lambda$8(this.f$0, context, booleanRef, dialogInterface);
            }
        }, new DialogInterface.OnCancelListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                TemplateApplier.showAodDialog$lambda$9(this.f$0, dialogInterface);
            }
        });
        alertDialogApplyToAodAlertDialog.show();
        AlertDialogsKtKt.adaptN8AlertDialog$default(alertDialogApplyToAodAlertDialog, false, false, false, 14, null);
    }

    public final void showApplyAllDialog(Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, boolean z, boolean z2) {
        TemplateConfig lockscreenInfo;
        templateApplierParams.setApplyToAll(true);
        CommonConfig templateConfig = baseTemplateView.getTemplateConfig();
        if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null) {
            lockscreenInfo.setApplyToDeskTop(true);
        }
        takeScreenshotThenApplyTemplate(context, baseTemplateView, templateApplierParams);
    }

    public final void showApplyDialogIfWallpaperChanged(Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, boolean z) {
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), Dispatchers.getMain(), null, new AnonymousClass1(context, templateApplierParams, this, baseTemplateView, z, null), 2, null);
    }

    public final void showDialogByWallpaperType(Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, boolean z) {
        Log.i(this.tag, "showDialogByWallpaperType -> params = " + templateApplierParams.getConfig());
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), Dispatchers.getMain().plus(new TemplateApplier$showDialogByWallpaperType$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key, this)), null, new C01181(templateApplierParams, baseTemplateView, context, this, z, null), 2, null);
    }

    public final void showDialogIfAodDisabled(Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, boolean z) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.supportAod(context) && !deviceUtil.isAodEnabled(context) && z) {
            showAodDialog(context, baseTemplateView, templateApplierParams);
        } else {
            takeScreenshotThenApplyTemplate(context, baseTemplateView, templateApplierParams);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object showDialogIfApplySpecialWallpaper(Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams, boolean z, boolean z2, Continuation continuation) {
        C01191 c01191;
        if (continuation instanceof C01191) {
            c01191 = (C01191) continuation;
            int i = c01191.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01191.label = i - Integer.MIN_VALUE;
            } else {
                c01191 = new C01191(continuation);
            }
        } else {
            c01191 = new C01191(continuation);
        }
        Object objWithContext = c01191.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01191.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
            TemplateApplier$showDialogIfApplySpecialWallpaper$wallpaperType$1 templateApplier$showDialogIfApplySpecialWallpaper$wallpaperType$1 = new TemplateApplier$showDialogIfApplySpecialWallpaper$wallpaperType$1(context, null);
            c01191.L$0 = this;
            c01191.L$1 = context;
            c01191.L$2 = baseTemplateView;
            c01191.L$3 = templateApplierParams;
            c01191.Z$0 = z;
            c01191.Z$1 = z2;
            c01191.label = 1;
            objWithContext = BuildersKt.withContext(coroutineDispatcher, templateApplier$showDialogIfApplySpecialWallpaper$wallpaperType$1, c01191);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z2 = c01191.Z$1;
            z = c01191.Z$0;
            templateApplierParams = (TemplateApplierParams) c01191.L$3;
            baseTemplateView = (BaseTemplateView) c01191.L$2;
            context = (Context) c01191.L$1;
            this = (TemplateApplier) c01191.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        TemplateApplier templateApplier = this;
        Context context2 = context;
        BaseTemplateView baseTemplateView2 = baseTemplateView;
        TemplateApplierParams templateApplierParams2 = templateApplierParams;
        boolean z3 = z2;
        String str = (String) objWithContext;
        ConstantsKt.checkOnMainThread();
        CommonConfig config = templateApplierParams2.getConfig();
        Intrinsics.checkNotNull(config);
        Log.i(templateApplier.tag, "tryApplyTemplate -> wallpaperType = " + str);
        boolean zIsSpecialWallpaperType = Wallpaper.Companion.isSpecialWallpaperType(str);
        templateApplierParams2.setApplyToAll(zIsSpecialWallpaperType);
        if (z && zIsSpecialWallpaperType) {
            String templateId = config.getLockscreenInfo().getClockInfo().getTemplateId();
            Log.i(templateApplier.tag, "tryApplyTemplate -> templateType = " + templateId);
            templateApplier.showSuperWallpaperOrLinkageVideoDialog(context2, templateId, baseTemplateView2, templateApplierParams2, z3, str);
        } else {
            templateApplier.showDialogIfAodDisabled(context2, baseTemplateView2, templateApplierParams2, z3);
        }
        return Unit.INSTANCE;
    }

    public final void showDialogIfGalleryAvailable(Context context, BaseTemplateView baseTemplateView, boolean z) {
        int screenshotTemplateCount = DeviceScreenshotHelper.INSTANCE.getScreenshotTemplateCount();
        BuildersKt__Builders_commonKt.launch$default(EditFragmentViewModel.Companion.getCoroutineScope(context), null, null, new C01201(context, baseTemplateView, new Ref.IntRef(), new Ref.ObjectRef(), this, screenshotTemplateCount, z, null), 3, null);
    }

    public final void showSuperWallpaperOrLinkageVideoDialog(final Context context, String str, final BaseTemplateView baseTemplateView, final TemplateApplierParams templateApplierParams, final boolean z, String str2) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TemplateApplier.showSuperWallpaperOrLinkageVideoDialog$lambda$1(booleanRef, this, context, baseTemplateView, templateApplierParams, z, dialogInterface, i);
            }
        };
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TemplateApplier.showSuperWallpaperOrLinkageVideoDialog$lambda$2(this.f$0, booleanRef, dialogInterface, i);
            }
        };
        if (Intrinsics.areEqual(str2, CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
            SpecialWallpaperHelper specialWallpaperHelper = SpecialWallpaperHelper.INSTANCE;
            AlertDialog alertDialogCreateConfirmDialogWithUnSupportTemplate = specialWallpaperHelper.isUnSupportSuperWallpaper(str) ? specialWallpaperHelper.createConfirmDialogWithUnSupportTemplate(context, onClickListener, onClickListener2) : specialWallpaperHelper.createConfirmDialogReplaceSuperWallpaper(context, onClickListener, onClickListener2);
            alertDialogCreateConfirmDialogWithUnSupportTemplate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    TemplateApplier.showSuperWallpaperOrLinkageVideoDialog$lambda$4$lambda$3(this.f$0, context, booleanRef, dialogInterface);
                }
            });
            alertDialogCreateConfirmDialogWithUnSupportTemplate.show();
            return;
        }
        if (Intrinsics.areEqual(str2, "linkage_video")) {
            AlertDialog alertDialogCreateConfirmDialogReplaceLinkageVideoWallpaper = SpecialWallpaperHelper.INSTANCE.createConfirmDialogReplaceLinkageVideoWallpaper(context, onClickListener, onClickListener2);
            alertDialogCreateConfirmDialogReplaceLinkageVideoWallpaper.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateApplier$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    TemplateApplier.showSuperWallpaperOrLinkageVideoDialog$lambda$6$lambda$5(this.f$0, context, booleanRef, dialogInterface);
                }
            });
            alertDialogCreateConfirmDialogReplaceLinkageVideoWallpaper.show();
        } else {
            booleanRef.element = true;
            updateEnterApplying(context, true);
            showDialogIfAodDisabled(context, baseTemplateView, templateApplierParams, z);
        }
    }

    public final void takeScreenshotThenApplyTemplate(Context context, BaseTemplateView baseTemplateView, TemplateApplierParams templateApplierParams) {
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new C01211(baseTemplateView, templateApplierParams, context, null), 3, null);
    }

    public final void updateEnterApplying(Context context, boolean z) {
        EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
        if (editorActivity != null) {
            editorActivity.setEnterApplying(z);
        }
    }
}

package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.GLTextureView;
import com.miui.keyguard.editor.edit.wallpaper.GestureListener;
import com.miui.keyguard.editor.edit.wallpaper.GestureManager;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperChangedListener;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.view.CenterCropDrawable;
import com.miui.keyguard.editor.view.KgPreviewVisualCheckBox;
import com.miui.keyguard.editor.view.KgVisualCheckBox;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import com.miui.maml.folme.AnimatedPropertyType;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
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
/* JADX INFO: compiled from: HomeTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class HomeTemplateView extends BaseTemplateView implements FollowSystemListener {
    public KgVisualCheckBox blurBtn;
    public Bitmap currentSelectWallpaper;
    public KgPreviewVisualCheckBox followBtn;
    public boolean followStatusChanged;
    public ImageView homeIconPreview;
    public Integer lastFollowBlurStatus;
    public Matrix lastFollowMatrix;
    public WallpaperPositionInfo lastHalfFollowPositionInfo;
    public Integer lastWallpaperBlurStatus;
    public WallpaperPositionInfo lastWallpaperPositionInfo;
    public KgPreviewVisualCheckBox wallpaperBtn;
    public final WallpaperChangedListener wallpaperChangedListener;
    public boolean wallpaperHasChanged;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1, reason: invalid class name */
    /* JADX INFO: compiled from: HomeTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1", f = "HomeTemplateView.kt", l = {AnimatedPropertyType.CORNER_RADIUS_Y, 1018}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ String $resourcePath;
        public int label;
        public final /* synthetic */ HomeTemplateView this$0;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: HomeTemplateView.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1$1", f = "HomeTemplateView.kt", l = {}, m = "invokeSuspend")
        public static final class C00251 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public /* synthetic */ Object L$0;
            public int label;
            public final /* synthetic */ HomeTemplateView this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00251(HomeTemplateView homeTemplateView, Continuation continuation) {
                super(2, continuation);
                this.this$0 = homeTemplateView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00251 c00251 = new C00251(this.this$0, continuation);
                c00251.L$0 = obj;
                return c00251;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00251) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object objM1061constructorimpl;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                HomeTemplateView homeTemplateView = this.this$0;
                try {
                    Result.Companion companion = Result.Companion;
                    CommonConfig templateConfig = homeTemplateView.getTemplateConfig();
                    if (templateConfig != null) {
                        TemplateApi.Companion companion2 = TemplateApi.Companion;
                        Context context = homeTemplateView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                        companion2.getInstance(context).updateCurrentTemplate(templateConfig, "home video recovered", false, false);
                    }
                    objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
                } catch (Throwable th) {
                    Result.Companion companion3 = Result.Companion;
                    objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
                }
                Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
                if (thM1063exceptionOrNullimpl != null) {
                    Log.e("Keyguard-Editor-HomeTemplateView", "startHomeVideoWithFallback: updateCurrentTemplate threw", thM1063exceptionOrNullimpl);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, HomeTemplateView homeTemplateView, Continuation continuation) {
            super(2, continuation);
            this.$resourcePath = str;
            this.this$0 = homeTemplateView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$resourcePath, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x008c, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r8, r1, r7) == r0) goto L26;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 0
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L20
                if (r1 == r4) goto L1c
                if (r1 != r3) goto L14
                kotlin.ResultKt.throwOnFailure(r8)
                goto L8f
            L14:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L1c:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L39
            L20:
                kotlin.ResultKt.throwOnFailure(r8)
                kotlinx.coroutines.CoroutineDispatcher r8 = kotlinx.coroutines.Dispatchers.getIO()
                com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1$effective$1 r1 = new com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1$effective$1
                com.miui.keyguard.editor.edit.base.HomeTemplateView r5 = r7.this$0
                java.lang.String r6 = r7.$resourcePath
                r1.<init>(r5, r6, r2)
                r7.label = r4
                java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r1, r7)
                if (r8 != r0) goto L39
                goto L8e
            L39:
                java.lang.String r8 = (java.lang.String) r8
                if (r8 != 0) goto L59
                java.lang.String r7 = r7.$resourcePath
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r0 = "startHomeVideoWithFallback: async recovery failed, path="
                r8.append(r0)
                r8.append(r7)
                java.lang.String r7 = r8.toString()
                java.lang.String r8 = "Keyguard-Editor-HomeTemplateView"
                android.util.Log.e(r8, r7)
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L59:
                com.miui.keyguard.editor.edit.base.HomeTemplateView r1 = r7.this$0
                com.miui.keyguard.editor.data.bean.CommonConfig r1 = r1.getTemplateConfig()
                if (r1 == 0) goto L70
                com.miui.keyguard.editor.data.bean.HomeConfig r1 = r1.getHomeInfo()
                if (r1 == 0) goto L70
                com.miui.keyguard.editor.data.bean.WallpaperInfo r1 = r1.getWallpaperInfo()
                if (r1 == 0) goto L70
                r1.setOriginResourcePath(r8)
            L70:
                com.miui.keyguard.editor.edit.base.HomeTemplateView r1 = r7.this$0
                boolean r4 = com.miui.keyguard.editor.edit.base.HomeTemplateView.access$homePageFocus(r1)
                java.lang.String r5 = "home normal"
                r1.startForcePlayerWithNormalVideo(r8, r4, r5)
                kotlinx.coroutines.CoroutineDispatcher r8 = kotlinx.coroutines.Dispatchers.getIO()
                com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1$1 r1 = new com.miui.keyguard.editor.edit.base.HomeTemplateView$startHomeVideoWithFallback$1$1
                com.miui.keyguard.editor.edit.base.HomeTemplateView r4 = r7.this$0
                r1.<init>(r4, r2)
                r7.label = r3
                java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r8, r1, r7)
                if (r7 != r0) goto L8f
            L8e:
                return r0
            L8f:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.edit.base.HomeTemplateView.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.HomeTemplateView$wallpaperButtonClick$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: HomeTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.HomeTemplateView$wallpaperButtonClick$1", f = "HomeTemplateView.kt", l = {605}, m = "invokeSuspend")
    public static final class C01171 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.HomeTemplateView$wallpaperButtonClick$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: HomeTemplateView.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.HomeTemplateView$wallpaperButtonClick$1$1", f = "HomeTemplateView.kt", l = {606}, m = "invokeSuspend")
        public static final class C00261 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;

            public C00261(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00261(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00261) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    if (DelayKt.delay(600L, this) == coroutine_suspended) {
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

        public C01171(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return HomeTemplateView.this.new C01171(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01171) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                C00261 c00261 = new C00261(null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, c00261, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (HomeTemplateView.this.hasWindowFocus()) {
                HomeTemplateView.this.setWallpaperBtnClickable(true);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public HomeTemplateView(@NotNull Context context) {
        super(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
        this.wallpaperChangedListener = new WallpaperChangedListener() { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$wallpaperChangedListener$1
            @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperChangedListener
            public void onChanged(Matrix matrix) {
            }
        };
    }

    public static final void followButtonCheckedFromDisable$lambda$18$lambda$17(HomeTemplateView homeTemplateView) {
        homeTemplateView.onMatrixChanged(homeTemplateView.lastFollowMatrix);
    }

    private final int getFollowStatus() {
        IntWrapper followStatus;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel == null || (followStatus = viewModel.getFollowStatus()) == null) {
            return 0;
        }
        return followStatus.getIntValue();
    }

    private final int getHomeFilterId() {
        IntWrapper homeFilterId;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel == null || (homeFilterId = viewModel.getHomeFilterId()) == null) {
            return 0;
        }
        return homeFilterId.getIntValue();
    }

    private final int getHomeWallpaperStatus() {
        IntWrapper homeWallpaperStatus;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel == null || (homeWallpaperStatus = viewModel.getHomeWallpaperStatus()) == null) {
            return 0;
        }
        return homeWallpaperStatus.getIntValue();
    }

    public static final void refreshBar$lambda$14$lambda$12$lambda$10(HomeTemplateView homeTemplateView, KgVisualCheckBox it) {
        Intrinsics.checkNotNullParameter(it, "it");
        homeTemplateView.blurButtonUnchecked();
        homeTemplateView.trackBlurButtonStatus();
    }

    public static final void refreshBar$lambda$14$lambda$12$lambda$11(HomeTemplateView homeTemplateView, View view) {
        HomeConfig homeInfo;
        CommonConfig templateConfig = homeTemplateView.getTemplateConfig();
        if (templateConfig != null && (homeInfo = templateConfig.getHomeInfo()) != null && homeInfo.isThirdPartyWallpaper()) {
            ToastUtil toastUtil = ToastUtil.INSTANCE;
            Context context = homeTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            toastUtil.showText(context, R.string.kg_effects_tip_not_support_third_party_wallpaper_service);
            return;
        }
        if (homeTemplateView.isGalleryOpened()) {
            ToastUtil toastUtil2 = ToastUtil.INSTANCE;
            Context context2 = homeTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            toastUtil2.showText(context2, R.string.kg_effects_not_support_gallery_tip);
            return;
        }
        ToastUtil toastUtil3 = ToastUtil.INSTANCE;
        Context context3 = homeTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        toastUtil3.showText(context3, R.string.kg_effects_tip_not_support);
    }

    public static final void refreshBar$lambda$14$lambda$12$lambda$9(HomeTemplateView homeTemplateView, KgVisualCheckBox it) {
        Intrinsics.checkNotNullParameter(it, "it");
        homeTemplateView.blurButtonChecked();
        homeTemplateView.trackBlurButtonStatus();
    }

    public static final void refreshBar$lambda$14$lambda$5$lambda$4(HomeTemplateView homeTemplateView, View view) {
        homeTemplateView.followStatusChanged = true;
        homeTemplateView.followButtonClick();
        homeTemplateView.trackFollowStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setWallpaperBtnClickable(boolean z) {
        KgPreviewVisualCheckBox kgPreviewVisualCheckBox = this.wallpaperBtn;
        if (kgPreviewVisualCheckBox != null) {
            kgPreviewVisualCheckBox.setClickable(z);
        }
    }

    public static /* synthetic */ void switchWallpaperView$default(HomeTemplateView homeTemplateView, WallpaperInfo wallpaperInfo, int i, WallpaperPositionInfo wallpaperPositionInfo, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            wallpaperPositionInfo = null;
        }
        homeTemplateView.switchWallpaperView(wallpaperInfo, i, wallpaperPositionInfo);
    }

    public static /* synthetic */ void updateHomeIconPreview$default(HomeTemplateView homeTemplateView, Bitmap bitmap, Boolean bool, int i, Object obj) {
        if ((i & 1) != 0) {
            bitmap = null;
        }
        if ((i & 2) != 0) {
            bool = Boolean.FALSE;
        }
        homeTemplateView.updateHomeIconPreview(bitmap, bool);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void addLayers(CommonConfig commonConfig) {
        HomeConfig homeInfo;
        HomeConfig homeInfo2;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        WallpaperInfo wallpaperInfo = null;
        View viewCreateWallpaperLayer = createWallpaperLayer(context, (commonConfig == null || (homeInfo2 = commonConfig.getHomeInfo()) == null) ? null : homeInfo2.getWallpaperInfo(), isAnimLayer(), getEditFragment(), getFromWhereTag());
        if (viewCreateWallpaperLayer != null) {
            setWallpaperLayer(viewCreateWallpaperLayer);
            BaseTemplateView.addViewToTemplate$default(this, viewCreateWallpaperLayer, 0, 2, null);
        }
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView != null) {
            combinedWallpaperView.setOnceCombineEditListener(this);
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            wallpaperLayer.addGestureListener(this.wallpaperChangedListener);
        }
        ImageView imageViewCreateHomeIconPreview = createHomeIconPreview();
        BaseTemplateView.addViewToTemplate$default(this, imageViewCreateHomeIconPreview, 0, 2, null);
        this.homeIconPreview = imageViewCreateHomeIconPreview;
        if (editable() && getFrom() == 1) {
            if (commonConfig != null && (homeInfo = commonConfig.getHomeInfo()) != null) {
                wallpaperInfo = homeInfo.getWallpaperInfo();
            }
            startPlayerIfVideoType(wallpaperInfo);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void applyTemplateInfo() {
        TemplateConfig lockscreenInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfoForVideo;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfoForVideo2;
        HomeConfig homeInfo3;
        WallpaperInfo wallpaperInfo2;
        HomeConfig homeInfo4;
        WallpaperInfo wallpaperInfo3;
        int i;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        HomeConfig homeInfo5;
        WallpaperInfo wallpaperInfo5;
        HomeConfig homeInfo6;
        HomeConfig homeInfo7;
        int followStatus = getFollowStatus();
        if (followStatus == 1 || followStatus == 2) {
            CommonConfig templateConfig = getTemplateConfig();
            WallpaperInfo wallpaperInfoDeepCopy = (templateConfig == null || (lockscreenInfo3 = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo.deepCopy();
            if (wallpaperInfoDeepCopy != null) {
                wallpaperInfoDeepCopy.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
            }
            CommonConfig templateConfig2 = getTemplateConfig();
            WallpaperInfo wallpaperInfoDeepCopy2 = (templateConfig2 == null || (lockscreenInfo2 = templateConfig2.getLockscreenInfo()) == null || (wallpaperInfoForVideo = lockscreenInfo2.getWallpaperInfoForVideo()) == null) ? null : wallpaperInfoForVideo.deepCopy();
            if (wallpaperInfoDeepCopy2 != null) {
                wallpaperInfoDeepCopy2.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
            }
            CommonConfig templateConfig3 = getTemplateConfig();
            if (templateConfig3 != null) {
                CommonConfig templateConfig4 = getTemplateConfig();
                templateConfig3.setHomeInfo(new HomeConfig(2, wallpaperInfoDeepCopy, wallpaperInfoDeepCopy2, (templateConfig4 == null || (lockscreenInfo = templateConfig4.getLockscreenInfo()) == null) ? 0 : lockscreenInfo.getHideParam()));
            }
        }
        if (isHalfFollowWallpaperDisable()) {
            CommonConfig templateConfig5 = getTemplateConfig();
            if (templateConfig5 != null && (homeInfo7 = templateConfig5.getHomeInfo()) != null) {
                homeInfo7.setFollowStatus(0);
            }
        } else {
            CommonConfig templateConfig6 = getTemplateConfig();
            if (templateConfig6 != null && (homeInfo = templateConfig6.getHomeInfo()) != null) {
                homeInfo.setFollowStatus(getFollowStatus());
            }
        }
        CommonConfig templateConfig7 = getTemplateConfig();
        if (templateConfig7 != null && (homeInfo5 = templateConfig7.getHomeInfo()) != null && (wallpaperInfo5 = homeInfo5.getWallpaperInfo()) != null) {
            CommonConfig templateConfig8 = getTemplateConfig();
            wallpaperInfo5.setPositionInfo(getPositionInfo((templateConfig8 == null || (homeInfo6 = templateConfig8.getHomeInfo()) == null) ? null : homeInfo6.getWallpaperInfo()));
        }
        CommonConfig templateConfig9 = getTemplateConfig();
        if (templateConfig9 != null && (homeInfo4 = templateConfig9.getHomeInfo()) != null && (wallpaperInfo3 = homeInfo4.getWallpaperInfo()) != null) {
            if (getHomeFilterId() == 1) {
                i = 30000;
            } else {
                MagicType magicType = MagicType.INSTANCE;
                CommonConfig templateConfig10 = getTemplateConfig();
                i = (magicType.isDepthVideo((templateConfig10 == null || (lockscreenInfo4 = templateConfig10.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo4.getMagicType())) && (getFollowStatus() == 1 || getFollowStatus() == 2)) ? MagicType.TYPE_DEPTH_VIDEO : 0;
            }
            wallpaperInfo3.setMagicType(i);
        }
        CommonConfig templateConfig11 = getTemplateConfig();
        if (templateConfig11 != null && (homeInfo3 = templateConfig11.getHomeInfo()) != null && (wallpaperInfo2 = homeInfo3.getWallpaperInfo()) != null) {
            wallpaperInfo2.setEnableBlur(false);
        }
        CommonConfig templateConfig12 = getTemplateConfig();
        if (templateConfig12 != null && (homeInfo2 = templateConfig12.getHomeInfo()) != null && (wallpaperInfoForVideo2 = homeInfo2.getWallpaperInfoForVideo()) != null) {
            wallpaperInfoForVideo2.setEnableBlur(false);
        }
        CommonConfig templateConfig13 = getTemplateConfig();
        Log.d("Keyguard-Editor-HomeTemplateView", "apply home info is " + (templateConfig13 != null ? templateConfig13.getHomeInfo() : null));
    }

    public final void blurButtonChecked() {
        if (isFollowWallpaper()) {
            this.lastFollowBlurStatus = 1;
        } else {
            this.lastWallpaperBlurStatus = 1;
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateHomeFilterId$default(viewModel, 1, false, false, 6, null);
        }
    }

    public final void blurButtonUnchecked() {
        if (isFollowWallpaper()) {
            this.lastFollowBlurStatus = 2;
        } else {
            this.lastWallpaperBlurStatus = 2;
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateHomeFilterId$default(viewModel, 2, false, false, 6, null);
        }
    }

    public final void clearDepthVideoStateIfNeeded(String str) {
        CommonConfig templateConfig;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        if (!Intrinsics.areEqual(str, "image") || (templateConfig = getTemplateConfig()) == null || (homeInfo = templateConfig.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null || !wallpaperInfo.isDepthVideo()) {
            return;
        }
        wallpaperInfo.setDepthVideo(false);
        wallpaperInfo.setDepthVideo((String) null);
    }

    public final ImageView createHomeIconPreview() {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return imageView;
    }

    public final void followButtonCheckedFromDisable() {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        HomeConfig homeInfo3;
        WallpaperInfo wallpaperInfo3;
        HomeConfig homeInfo4;
        CommonConfig templateConfig = getTemplateConfig();
        this.lastWallpaperPositionInfo = getPositionInfo((templateConfig == null || (homeInfo4 = templateConfig.getHomeInfo()) == null) ? null : homeInfo4.getWallpaperInfo());
        CommonConfig templateConfig2 = getTemplateConfig();
        if (templateConfig2 != null && (lockscreenInfo = templateConfig2.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) != null) {
            CommonConfig templateConfig3 = getTemplateConfig();
            int switchType = getSwitchType((templateConfig3 == null || (homeInfo3 = templateConfig3.getHomeInfo()) == null || (wallpaperInfo3 = homeInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType(), wallpaperInfo2.getResourceType());
            CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
            if (combinedWallpaperView != null) {
                CombinedWallpaperView.resetCurrentMagicType$default(combinedWallpaperView, 0, 1, null);
            }
            if (DeviceUtil.INSTANCE.isPad()) {
                WallpaperInfo wallpaperInfoDeepCopy = wallpaperInfo2.deepCopy();
                wallpaperInfoDeepCopy.setEnableBlur(false);
                switchWallpaperView(wallpaperInfoDeepCopy, switchType, null);
            } else {
                switchWallpaperView(wallpaperInfo2, switchType, null);
            }
            if (WallpaperEditor.Companion.canScale(wallpaperInfo2.getResourceType())) {
                post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        HomeTemplateView.followButtonCheckedFromDisable$lambda$18$lambda$17(this.f$0);
                    }
                });
            }
            clearDepthVideoStateIfNeeded(wallpaperInfo2.getResourceType());
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateFollowStatus$default(viewModel, 2, false, false, 6, null);
        }
        uncheckWallpaperButton();
        CommonConfig templateConfig4 = getTemplateConfig();
        if (templateConfig4 != null && (homeInfo2 = templateConfig4.getHomeInfo()) != null && homeInfo2.isThirdPartyWallpaper()) {
            this.lastFollowBlurStatus = 0;
        }
        if (viewModel != null) {
            Integer num = this.lastFollowBlurStatus;
            EditFragmentViewModel.updateHomeFilterId$default(viewModel, num != null ? num.intValue() : 2, false, false, 6, null);
        }
        CommonConfig templateConfig5 = getTemplateConfig();
        if (templateConfig5 == null || (homeInfo = templateConfig5.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) {
            return;
        }
        showDynamicButtonStatus(wallpaperInfo);
    }

    public final void followButtonCheckedFromHalf() {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfo3;
        HomeConfig homeInfo3;
        CommonConfig templateConfig = getTemplateConfig();
        this.lastWallpaperPositionInfo = getPositionInfo((templateConfig == null || (homeInfo3 = templateConfig.getHomeInfo()) == null) ? null : homeInfo3.getWallpaperInfo());
        CommonConfig templateConfig2 = getTemplateConfig();
        if (templateConfig2 != null && (lockscreenInfo = templateConfig2.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) != null) {
            CommonConfig templateConfig3 = getTemplateConfig();
            int switchType = getSwitchType((templateConfig3 == null || (homeInfo2 = templateConfig3.getHomeInfo()) == null || (wallpaperInfo3 = homeInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType(), wallpaperInfo2.getResourceType());
            CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
            if (combinedWallpaperView != null) {
                CombinedWallpaperView.resetCurrentMagicType$default(combinedWallpaperView, 0, 1, null);
            }
            switchWallpaperView(wallpaperInfo2, switchType, this.lastHalfFollowPositionInfo);
            clearDepthVideoStateIfNeeded(wallpaperInfo2.getResourceType());
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateFollowStatus$default(viewModel, 1, false, false, 6, null);
        }
        uncheckWallpaperButton();
        if (viewModel != null) {
            Integer num = this.lastFollowBlurStatus;
            EditFragmentViewModel.updateHomeFilterId$default(viewModel, num != null ? num.intValue() : 2, false, false, 6, null);
        }
        CommonConfig templateConfig4 = getTemplateConfig();
        if (templateConfig4 == null || (homeInfo = templateConfig4.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) {
            return;
        }
        showDynamicButtonStatus(wallpaperInfo);
    }

    public final void followButtonClick() {
        GestureManager gestureManager;
        Log.d("Keyguard-Editor-HomeTemplateView", "refreshBar: follow click:" + getFollowStatus());
        int followStatus = getFollowStatus();
        if (followStatus == 0) {
            followButtonCheckedFromDisable();
            return;
        }
        if (followStatus != 1) {
            if (followStatus != 3) {
                return;
            }
            followButtonCheckedFromHalf();
            return;
        }
        markEdited();
        onMatrixChanged(this.lastFollowMatrix);
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateFollowStatus$default(viewModel, 2, false, false, 6, null);
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        CombinedWallpaperView combinedWallpaperView = wallpaperLayer instanceof CombinedWallpaperView ? (CombinedWallpaperView) wallpaperLayer : null;
        if (combinedWallpaperView == null || (gestureManager = combinedWallpaperView.getGestureManager()) == null) {
            return;
        }
        gestureManager.resetLastUsePositionInfo();
    }

    public final int followStatusIsChanged() {
        IntWrapper followStatus;
        if (!this.followStatusChanged) {
            return -1;
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel == null || (followStatus = viewModel.getFollowStatus()) == null) {
            return 0;
        }
        return followStatus.getIntValue();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @Nullable
    public WallpaperInfo getCurrentWallpaperInfo() {
        HomeConfig homeInfo;
        CommonConfig templateConfig = getTemplateConfig();
        if (templateConfig == null || (homeInfo = templateConfig.getHomeInfo()) == null) {
            return null;
        }
        return homeInfo.getWallpaperInfo();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @Nullable
    public String getFromWhereTag() {
        return "HomeTemplateView";
    }

    public final int getMagicType(int i) {
        return i == 1 ? 30000 : 0;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @Nullable
    public Bitmap getPickColorBitmap() {
        return null;
    }

    public final int getSwitchType(String str, String str2) {
        int i = (Intrinsics.areEqual(str2, "sensor") || Intrinsics.areEqual(str2, "video")) ? 1 : 3;
        if (str != null) {
            int iHashCode = str.hashCode();
            if (iHashCode != -905948230) {
                if (iHashCode != 100313435) {
                    if (iHashCode == 112202875 && str.equals("video")) {
                        if (Intrinsics.areEqual(str2, "image")) {
                            return 2;
                        }
                        if (Intrinsics.areEqual(str2, "video")) {
                            return 4;
                        }
                    }
                } else if (str.equals("image")) {
                    if (Intrinsics.areEqual(str2, "image")) {
                        return 3;
                    }
                    if (Intrinsics.areEqual(str2, "video")) {
                        return 1;
                    }
                }
            } else if (str.equals("sensor")) {
                if (Intrinsics.areEqual(str2, "image")) {
                    return 2;
                }
                if (Intrinsics.areEqual(str2, "video")) {
                    return 4;
                }
            }
        }
        return i;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public Bitmap getWallpaperBitmapForDarkResult(Bitmap bitmap, Bitmap bitmap2) {
        return bitmap == null ? bitmap2 : bitmap;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public boolean getWallpaperHasChanged() {
        return this.wallpaperHasChanged;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @Nullable
    public WallpaperInfo getWallpaperInfo() {
        HomeConfig homeInfo;
        CommonConfig templateConfig = getTemplateConfig();
        if (templateConfig == null || (homeInfo = templateConfig.getHomeInfo()) == null) {
            return null;
        }
        return homeInfo.getWallpaperInfo();
    }

    public final boolean homePageFocus() {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        return viewModel != null && viewModel.getSelectTemplate() == 1;
    }

    public final boolean isFollowAll() {
        return getFollowStatus() == 2;
    }

    public final boolean isFollowWallpaper() {
        return getFollowStatus() == 2 || getFollowStatus() == 1;
    }

    public final boolean isFromLockButNotCheckFollow(boolean z) {
        return z && !isFollowWallpaper();
    }

    public final boolean isHalfFollowWallpaper() {
        return getFollowStatus() == 1;
    }

    public final boolean isHalfFollowWallpaperDisable() {
        return getFollowStatus() == 3;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public boolean isInThirdPartyWallpaperService() {
        HomeConfig homeInfo;
        CommonConfig templateConfig = getTemplateConfig();
        return (templateConfig == null || (homeInfo = templateConfig.getHomeInfo()) == null || !homeInfo.isThirdPartyWallpaper() || getWallpaperHasChanged()) ? false : true;
    }

    public final boolean isSelectHomeTemplate() {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        return viewModel != null && viewModel.getSelectTemplate() == 1;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void loadTemplate(@Nullable CommonConfig commonConfig) {
        IWallpaperLayer wallpaperLayer;
        CommonConfig templateConfig;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        Log.d("Keyguard-Editor-HomeTemplateView", "loadTemplate: " + commonConfig);
        if (getLoadingView() == null) {
            removeAllViews();
        }
        setTemplateConfig(commonConfig);
        if (DeviceUtil.INSTANCE.isPad() && (templateConfig = getTemplateConfig()) != null && (homeInfo = templateConfig.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.setEnableBlur(false);
        }
        addLayers(commonConfig);
        exitFullScreen(true);
        if (!isInThirdPartyWallpaperService() || (wallpaperLayer = getWallpaperLayer()) == null) {
            return;
        }
        wallpaperLayer.setScaleable(Boolean.FALSE);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public /* bridge */ /* synthetic */ void onAccessibilityHighTextEnableChange(boolean z) {
        super.onAccessibilityHighTextEnableChange(z);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public /* bridge */ /* synthetic */ void onBackgroundBlurEnableChange(boolean z) {
        super.onBackgroundBlurEnableChange(z);
    }

    public final void onChangeButton(boolean z, Bitmap bitmap) {
        ImageView extraIcon;
        KgPreviewVisualCheckBox kgPreviewVisualCheckBox;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (z) {
            if (isFollowWallpaper()) {
                if (viewModel != null) {
                    EditFragmentViewModel.updateFollowStatus$default(viewModel, 2, false, false, 6, null);
                }
            } else if (isHalfFollowWallpaperDisable() && viewModel != null) {
                EditFragmentViewModel.updateFollowStatus$default(viewModel, 0, false, false, 6, null);
            }
            if (bitmap == null || (kgPreviewVisualCheckBox = this.followBtn) == null) {
                return;
            }
            kgPreviewVisualCheckBox.setPreviewThumbnail(bitmap);
            return;
        }
        if (viewModel != null) {
            EditFragmentViewModel.updateHomeWallpaperStatus$default(viewModel, 2, false, false, 6, null);
            uncheckFollowButton();
        }
        if (bitmap != null) {
            KgPreviewVisualCheckBox kgPreviewVisualCheckBox2 = this.wallpaperBtn;
            if (kgPreviewVisualCheckBox2 != null) {
                kgPreviewVisualCheckBox2.setPreviewThumbnail(bitmap);
            }
            KgPreviewVisualCheckBox kgPreviewVisualCheckBox3 = this.wallpaperBtn;
            if (kgPreviewVisualCheckBox3 == null || (extraIcon = kgPreviewVisualCheckBox3.getExtraIcon()) == null) {
                return;
            }
            TemplateViewsKt.setColorMode(extraIcon, BitmapUtil.INSTANCE.getColorMode(bitmap));
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onDestroy() {
        super.onDestroy();
        this.currentSelectWallpaper = null;
    }

    /* JADX WARN: Code duplicated, block: B:144:0x0216  */
    /* JADX WARN: Code duplicated, block: B:146:0x021a  */
    /* JADX WARN: Code duplicated, block: B:178:0x0292  */
    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, @Nullable Object obj) {
        WallpaperTypeInfo wallpaperTypeInfo;
        boolean z;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo3;
        String resourceType;
        TemplateConfig lockscreenInfo2;
        HomeConfig homeInfo3;
        HomeConfig homeInfo4;
        WallpaperInfo wallpaperInfo4;
        HomeConfig homeInfo5;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo6;
        EditFragmentViewModel viewModel;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo7;
        EditFragmentViewModel viewModel2;
        HomeConfig homeInfo6;
        TemplateConfig lockscreenInfo5;
        HomeConfig homeInfo7;
        HomeConfig homeInfo8;
        HomeConfig homeInfo9;
        HomeConfig homeInfo10;
        HomeConfig homeInfo11;
        TemplateConfig lockscreenInfo6;
        HomeConfig homeInfo12;
        HomeConfig homeInfo13;
        HomeConfig homeInfo14;
        HomeConfig homeInfo15;
        Log.i("Keyguard-Editor-HomeTemplateView", "home onEdited: " + i + ", " + obj);
        markEdited();
        int iIntValue = 0;
        View viewAddDynamicButtonGroup = null;
        wallpaperInfo = null;
        wallpaperInfo = null;
        wallpaperInfo = null;
        WallpaperInfo wallpaperInfo8 = null;
        wallpaperInfo = null;
        wallpaperInfo = null;
        wallpaperInfo = null;
        WallpaperInfo wallpaperInfo9 = null;
        if (i == 30) {
            setWallpaperHasChanged(true);
            if (obj instanceof EditData) {
                wallpaperTypeInfo = (WallpaperTypeInfo) ((EditData) obj).getData();
                z = true;
            } else {
                wallpaperTypeInfo = (WallpaperTypeInfo) obj;
                z = false;
            }
            if (DeviceUtil.INSTANCE.isPad() && wallpaperTypeInfo != null) {
                wallpaperTypeInfo.setEnableBlur(false);
            }
            if (wallpaperTypeInfo != null) {
                updateLastBlurStatus(z, wallpaperTypeInfo.getType());
                if (!isFromLockButNotCheckFollow(z)) {
                    setWallpaperChanged(true);
                    if (z) {
                        CommonConfig templateConfig = getTemplateConfig();
                        if (templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo.getWallpaperInfo()) == null) {
                            resourceType = null;
                        } else {
                            resourceType = wallpaperInfo3.getResourceType();
                        }
                    } else {
                        CommonConfig templateConfig2 = getTemplateConfig();
                        if (templateConfig2 == null || (homeInfo4 = templateConfig2.getHomeInfo()) == null || (wallpaperInfo4 = homeInfo4.getWallpaperInfo()) == null) {
                            resourceType = null;
                        } else {
                            resourceType = wallpaperInfo4.getResourceType();
                        }
                    }
                    if (isVideoToImageTransition(resourceType, wallpaperTypeInfo.getType())) {
                        cleanupVideoForCrossFade();
                    }
                    updateHomeInfo(wallpaperTypeInfo, z);
                    onWallpaperSelected(wallpaperTypeInfo);
                    if (z) {
                        CommonConfig templateConfig3 = getTemplateConfig();
                        startPlayerIfVideoTypeWhenSwitchWallpaper((templateConfig3 == null || (lockscreenInfo2 = templateConfig3.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo());
                    } else {
                        CommonConfig templateConfig4 = getTemplateConfig();
                        startPlayerIfVideoTypeWhenSwitchWallpaper((templateConfig4 == null || (homeInfo3 = templateConfig4.getHomeInfo()) == null) ? null : homeInfo3.getWallpaperInfo());
                    }
                }
                onChangeButton(z, wallpaperTypeInfo.getBitmap());
                updateHomeIconPreview(wallpaperTypeInfo.getBitmap(), Boolean.valueOf(z));
            }
            Wallpaper.Companion companion = Wallpaper.Companion;
            CommonConfig templateConfig5 = getTemplateConfig();
            if (!companion.isLivePhotoResource((templateConfig5 == null || (homeInfo2 = templateConfig5.getHomeInfo()) == null || (wallpaperInfo2 = homeInfo2.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo2.getVideoType()))) {
                getDynamicSwitchCallback().removeDynamicSwitchButton();
                return;
            }
            final WeakReference weakReference = new WeakReference(this);
            DynamicButtonClickCallBack dynamicButtonClickCallBack = new DynamicButtonClickCallBack(weakReference) { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$onEdited$livePhotoCallback$1
                @Override // com.miui.keyguard.editor.edit.base.DynamicButtonClickCallBack
                public void onClick(int i2) {
                    this.this$0.updateVideoOrImageStatus(i2);
                }
            };
            DynamicSwitchButton dynamicSwitchBtn = getDynamicSwitchBtn();
            if (dynamicSwitchBtn == null) {
                TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
                dynamicSwitchBtn = primaryButtonBar != null ? primaryButtonBar.addDynamicSwitchButton() : null;
            }
            setDynamicSwitchBtn(dynamicSwitchBtn);
            DynamicSwitchButton dynamicSwitchBtn2 = getDynamicSwitchBtn();
            if (dynamicSwitchBtn2 != null) {
                dynamicSwitchBtn2.setClickCallback(dynamicButtonClickCallBack);
                dynamicSwitchBtn2.setDynamicSwitchCallback(getDynamicSwitchCallback());
                dynamicSwitchBtn2.setVisibility(isFollowWallpaper() ? 8 : 0);
            }
            if (!isFromLockButNotCheckFollow(z)) {
                CommonConfig templateConfig6 = getTemplateConfig();
                if (companion.isNormalLivePhotoResource((templateConfig6 == null || (homeInfo = templateConfig6.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getVideoType()))) {
                    DynamicSwitchButton dynamicSwitchBtn3 = getDynamicSwitchBtn();
                    if (dynamicSwitchBtn3 != null) {
                        DynamicSwitchButton.changeStatus$default(dynamicSwitchBtn3, 2, false, false, 4, null);
                    }
                } else {
                    DynamicSwitchButton dynamicSwitchBtn4 = getDynamicSwitchBtn();
                    if (dynamicSwitchBtn4 != null) {
                        DynamicSwitchButton.changeStatus$default(dynamicSwitchBtn4, 3, false, false, 4, null);
                    }
                }
            }
            View presetButtonGroup = getPresetButtonGroup();
            if (presetButtonGroup == null) {
                TemplateButtonBar primaryButtonBar2 = getPrimaryButtonBar();
                if (primaryButtonBar2 != null) {
                    viewAddDynamicButtonGroup = primaryButtonBar2.addDynamicButtonGroup();
                }
            } else {
                viewAddDynamicButtonGroup = presetButtonGroup;
            }
            setPresetButtonGroup(viewAddDynamicButtonGroup);
            return;
        }
        if (i == 210) {
            if (obj instanceof EditData) {
                EditData editData = (EditData) obj;
                if (editData.getData() instanceof Integer) {
                    iIntValue = ((Number) editData.getData()).intValue();
                } else if (obj instanceof Integer) {
                    iIntValue = ((Number) obj).intValue();
                }
            } else if (obj instanceof Integer) {
                iIntValue = ((Number) obj).intValue();
            }
            int magicType = getMagicType(iIntValue);
            CommonConfig templateConfig7 = getTemplateConfig();
            if (templateConfig7 != null && (homeInfo5 = templateConfig7.getHomeInfo()) != null && (wallpaperInfo5 = homeInfo5.getWallpaperInfo()) != null) {
                wallpaperInfo5.updateMagicType(magicType);
            }
            IWallpaperLayer wallpaperLayer = getWallpaperLayer();
            if (wallpaperLayer != null) {
                wallpaperLayer.switchGlassShader(magicType, null);
                return;
            }
            return;
        }
        if (i == 37) {
            if (isHalfFollowWallpaper() && (viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment())) != null) {
                EditFragmentViewModel.updateFollowStatus$default(viewModel, 2, false, false, 6, null);
            }
            CommonConfig templateConfig8 = getTemplateConfig();
            if (templateConfig8 == null || (lockscreenInfo3 = templateConfig8.getLockscreenInfo()) == null || (wallpaperInfo6 = lockscreenInfo3.getWallpaperInfo()) == null) {
                return;
            }
            updateLastBlurStatus(true, wallpaperInfo6.getResourceType());
            if (isFollowWallpaper()) {
                switchWallpaperView$default(this, wallpaperInfo6, 1, null, 4, null);
                return;
            }
            return;
        }
        if (i == 38) {
            if (isHalfFollowWallpaper() && (viewModel2 = EditFragmentViewModel.Companion.getViewModel(getEditFragment())) != null) {
                EditFragmentViewModel.updateFollowStatus$default(viewModel2, 2, false, false, 6, null);
            }
            CommonConfig templateConfig9 = getTemplateConfig();
            if (templateConfig9 == null || (lockscreenInfo4 = templateConfig9.getLockscreenInfo()) == null || (wallpaperInfo7 = lockscreenInfo4.getWallpaperInfo()) == null) {
                return;
            }
            updateLastBlurStatus(true, wallpaperInfo7.getResourceType());
            if (isFollowWallpaper()) {
                switchWallpaperView$default(this, wallpaperInfo7, 2, null, 4, null);
                return;
            }
            return;
        }
        if (i == 220) {
            if (!isFollowWallpaper()) {
                DynamicSwitchButton dynamicSwitchBtn5 = getDynamicSwitchBtn();
                if (dynamicSwitchBtn5 != null) {
                    CommonConfig templateConfig10 = getTemplateConfig();
                    dynamicSwitchBtn5.checkHomeLivePhotoVideo(templateConfig10 != null ? templateConfig10.getHomeInfo() : null, isHistoryTemplate());
                }
                CommonConfig templateConfig11 = getTemplateConfig();
                WallpaperInfo wallpaperInfoForVideo = (templateConfig11 == null || (homeInfo10 = templateConfig11.getHomeInfo()) == null) ? null : homeInfo10.getWallpaperInfoForVideo();
                CommonConfig templateConfig12 = getTemplateConfig();
                if (templateConfig12 != null && (homeInfo8 = templateConfig12.getHomeInfo()) != null) {
                    CommonConfig templateConfig13 = getTemplateConfig();
                    homeInfo8.setWallpaperInfoForVideo((templateConfig13 == null || (homeInfo9 = templateConfig13.getHomeInfo()) == null) ? null : homeInfo9.getWallpaperInfo());
                }
                CommonConfig templateConfig14 = getTemplateConfig();
                if (templateConfig14 != null && (homeInfo7 = templateConfig14.getHomeInfo()) != null) {
                    homeInfo7.setWallpaperInfo(wallpaperInfoForVideo);
                }
            }
            if (isFollowWallpaper()) {
                CommonConfig templateConfig15 = getTemplateConfig();
                if (templateConfig15 != null && (lockscreenInfo5 = templateConfig15.getLockscreenInfo()) != null) {
                    wallpaperInfo9 = lockscreenInfo5.getWallpaperInfo();
                }
            } else {
                CommonConfig templateConfig16 = getTemplateConfig();
                if (templateConfig16 != null && (homeInfo6 = templateConfig16.getHomeInfo()) != null) {
                    wallpaperInfo9 = homeInfo6.getWallpaperInfo();
                }
            }
            WallpaperInfo wallpaperInfo10 = wallpaperInfo9;
            if (wallpaperInfo10 != null) {
                updateLastBlurStatus(false, wallpaperInfo10.getResourceType());
                switchWallpaperView$default(this, wallpaperInfo10, 1, null, 4, null);
                return;
            }
            return;
        }
        if (i != 221) {
            return;
        }
        if (!isFollowWallpaper()) {
            DynamicSwitchButton dynamicSwitchBtn6 = getDynamicSwitchBtn();
            if (dynamicSwitchBtn6 != null) {
                CommonConfig templateConfig17 = getTemplateConfig();
                dynamicSwitchBtn6.checkHomeLivePhotoVideo(templateConfig17 != null ? templateConfig17.getHomeInfo() : null, isHistoryTemplate());
            }
            CommonConfig templateConfig18 = getTemplateConfig();
            WallpaperInfo wallpaperInfoForVideo2 = (templateConfig18 == null || (homeInfo15 = templateConfig18.getHomeInfo()) == null) ? null : homeInfo15.getWallpaperInfoForVideo();
            CommonConfig templateConfig19 = getTemplateConfig();
            if (templateConfig19 != null && (homeInfo13 = templateConfig19.getHomeInfo()) != null) {
                CommonConfig templateConfig20 = getTemplateConfig();
                homeInfo13.setWallpaperInfoForVideo((templateConfig20 == null || (homeInfo14 = templateConfig20.getHomeInfo()) == null) ? null : homeInfo14.getWallpaperInfo());
            }
            CommonConfig templateConfig21 = getTemplateConfig();
            if (templateConfig21 != null && (homeInfo12 = templateConfig21.getHomeInfo()) != null) {
                homeInfo12.setWallpaperInfo(wallpaperInfoForVideo2);
            }
        }
        if (isFollowWallpaper()) {
            CommonConfig templateConfig22 = getTemplateConfig();
            if (templateConfig22 != null && (lockscreenInfo6 = templateConfig22.getLockscreenInfo()) != null) {
                wallpaperInfo8 = lockscreenInfo6.getWallpaperInfo();
            }
        } else {
            CommonConfig templateConfig23 = getTemplateConfig();
            if (templateConfig23 != null && (homeInfo11 = templateConfig23.getHomeInfo()) != null) {
                wallpaperInfo8 = homeInfo11.getWallpaperInfo();
            }
        }
        WallpaperInfo wallpaperInfo11 = wallpaperInfo8;
        if (wallpaperInfo11 != null) {
            updateLastBlurStatus(false, wallpaperInfo11.getResourceType());
            switchWallpaperView$default(this, wallpaperInfo11, 2, null, 4, null);
        }
    }

    public final void onMatrixChanged(Matrix matrix) {
        if (matrix == null) {
            Log.w("Keyguard-Editor-HomeTemplateView", "onChanged: matrix is null");
            return;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        View viewWallpaperLayerView = wallpaperLayer != null ? wallpaperLayer.wallpaperLayerView() : null;
        if (viewWallpaperLayerView instanceof GLTextureView) {
            Log.w("Keyguard-Editor-HomeTemplateView", "GLTextureView onChanged: matrix is " + matrix);
            GestureListener.DefaultImpls.onApply$default((GestureListener) viewWallpaperLayerView, matrix, null, null, 6, null);
            ((GLTextureView) viewWallpaperLayerView).setLastMatrix(matrix);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onPositionInfoChanged() {
        EditFragmentViewModel viewModel;
        CombineEditListener combineEditListener = getCombineEditListener();
        if (combineEditListener == null || combineEditListener.getCurrentSelect() != 1 || (viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment())) == null || viewModel.getFollowStatus().getIntValue() != 2) {
            return;
        }
        EditFragmentViewModel.updateFollowStatus$default(viewModel, 1, false, false, 6, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
    public void onPrepareApply() {
        setApplying(true);
        applyTemplateInfo();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public /* bridge */ /* synthetic */ void onSuperSaveChange(boolean z) {
        super.onSuperSaveChange(z);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onWallpaperDarkResultReady() {
        ImageView imageView = this.homeIconPreview;
        if (imageView != null) {
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            imageView.setImageResource(commonUtils.obtainHomeLayoutDrawable(context, isDarkWallpaper()));
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onWallpaperSelected(WallpaperTypeInfo wallpaperTypeInfo) {
        HomeConfig homeInfo;
        TemplateConfig lockscreenInfo;
        if (wallpaperTypeInfo != null) {
            wallpaperTypeInfo.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
        }
        CommonConfig templateConfig = getTemplateConfig();
        if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null) {
            lockscreenInfo.setThirdPartyWallpaper(false);
        }
        CommonConfig templateConfig2 = getTemplateConfig();
        if (templateConfig2 != null && (homeInfo = templateConfig2.getHomeInfo()) != null) {
            homeInfo.setThirdPartyWallpaper(false);
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            wallpaperLayer.onWallpaperSelected(wallpaperTypeInfo, getFromWhereTag());
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            setWallpaperBtnClickable(true);
        }
    }

    public final String recoverHomeVideoFromWms(String str) {
        String videoWallpaperOriginPath;
        Log.w("Keyguard-Editor-HomeTemplateView", "recoverHomeVideoFromWms: try WMS fallback. missing=" + str);
        try {
            WallpaperController.Companion companion = WallpaperController.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            WallpaperController companion2 = companion.getInstance(context);
            videoWallpaperOriginPath = companion2 != null ? companion2.getVideoWallpaperOriginPath(WallpaperWhich.HomeWhich.INSTANCE) : null;
        } catch (Throwable th) {
            Log.e("Keyguard-Editor-HomeTemplateView", "recoverHomeVideoFromWms: getVideoWallpaperOriginPath threw", th);
        }
        if (videoWallpaperOriginPath == null || videoWallpaperOriginPath.length() == 0 || !FileUtil.isFileExist(videoWallpaperOriginPath)) {
            Log.e("Keyguard-Editor-HomeTemplateView", "recoverHomeVideoFromWms: WMS home video origin also unavailable, wmsOrigin=" + videoWallpaperOriginPath);
            return null;
        }
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        String strGenerateCurrentTemplateFilePath = new TemplateFilePathGenerator(context2).generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME);
        if (FileUtil.fastCopy(videoWallpaperOriginPath, strGenerateCurrentTemplateFilePath, false) && FileUtil.isFileExist(strGenerateCurrentTemplateFilePath)) {
            Log.i("Keyguard-Editor-HomeTemplateView", "recoverHomeVideoFromWms: recovered from WMS to " + strGenerateCurrentTemplateFilePath);
            return strGenerateCurrentTemplateFilePath;
        }
        Log.e("Keyguard-Editor-HomeTemplateView", "recoverHomeVideoFromWms: fastCopy failed src=" + videoWallpaperOriginPath + " dst=" + strGenerateCurrentTemplateFilePath);
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0209  */
    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void refreshBar() {
        boolean z;
        CombinedWallpaperView combinedWallpaperView;
        int i;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        HomeConfig homeInfo;
        HomeConfig homeInfo2;
        HomeConfig homeInfo3;
        WallpaperInfo wallpaperInfo3;
        Bitmap currentWallpaper;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo4;
        Bitmap currentWallpaper2;
        HomeConfig homeInfo4;
        WallpaperInfo wallpaperInfo5;
        ExtraInfo extraInfo;
        Integer loopType;
        CombineEditView combineEditView;
        TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
        if (primaryButtonBar != null) {
            EditFragment editFragment = getEditFragment();
            if (editFragment != null && (combineEditView = editFragment.getCombineEditView()) != null) {
                primaryButtonBar.addTemplateTitle(R.string.kg_template_title_desktop, combineEditView.getCombineEditorViewY());
            }
            CommonConfig templateConfig = getTemplateConfig();
            int iIntValue = (templateConfig == null || (homeInfo4 = templateConfig.getHomeInfo()) == null || (wallpaperInfo5 = homeInfo4.getWallpaperInfo()) == null || (extraInfo = wallpaperInfo5.getExtraInfo()) == null || (loopType = extraInfo.getLoopType()) == null) ? -1 : loopType.intValue();
            FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
            boolean zIsLoopTemplate = fashionGalleryHelper.isLoopTemplate(Integer.valueOf(iIntValue));
            Log.d("Keyguard-Editor-HomeTemplateView", "loopType = " + iIntValue + ", isLoopTemplate = " + zIsLoopTemplate);
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isInternationalBuild()) {
                z = (isGalleryOpened() && zIsLoopTemplate) ? false : true;
            } else {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                setGalleryOpened(fashionGalleryHelper.isLoop(context, false, Integer.valueOf(iIntValue)));
                if (isGalleryOpened()) {
                    primaryButtonBar.addBottomTip(R.string.kg_editing_not_available_when_using_loop);
                    if (iIntValue != 2 || (combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer()) == null) {
                        return;
                    }
                    combinedWallpaperView.setScaleable(Boolean.FALSE);
                    return;
                }
                z = !zIsLoopTemplate;
            }
            WallpaperInfo wallpaperInfo6 = null;
            if (z) {
                KgVisualCheckBox kgVisualCheckBoxAddFollowButton$default = TemplateButtonBar.addFollowButton$default(primaryButtonBar, 0, 1, null);
                CommonConfig templateConfig2 = getTemplateConfig();
                if (templateConfig2 != null && (lockscreenInfo3 = templateConfig2.getLockscreenInfo()) != null && (wallpaperInfo4 = lockscreenInfo3.getWallpaperInfo()) != null && (currentWallpaper2 = wallpaperInfo4.getCurrentWallpaper()) != null) {
                    kgVisualCheckBoxAddFollowButton$default.setPreviewThumbnail(currentWallpaper2);
                }
                kgVisualCheckBoxAddFollowButton$default.setClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        HomeTemplateView.refreshBar$lambda$14$lambda$5$lambda$4(this.f$0, view);
                    }
                });
                Intrinsics.checkNotNull(kgVisualCheckBoxAddFollowButton$default, "null cannot be cast to non-null type com.miui.keyguard.editor.view.KgPreviewVisualCheckBox");
                KgPreviewVisualCheckBox kgPreviewVisualCheckBox = (KgPreviewVisualCheckBox) kgVisualCheckBoxAddFollowButton$default;
                this.followBtn = kgPreviewVisualCheckBox;
                BaseTemplateView.updateVisualCheckBoxChecked$default(this, kgPreviewVisualCheckBox, true, false, 4, null);
            }
            KgVisualCheckBox kgVisualCheckBoxAddHomeWallpaperPickerButton$default = TemplateButtonBar.addHomeWallpaperPickerButton$default(primaryButtonBar, 0, R.string.kg_function_button_text_home_pick, 1, null);
            kgVisualCheckBoxAddHomeWallpaperPickerButton$default.enableDoubleClick(true);
            CommonConfig templateConfig3 = getTemplateConfig();
            if (templateConfig3 != null && (homeInfo3 = templateConfig3.getHomeInfo()) != null && (wallpaperInfo3 = homeInfo3.getWallpaperInfo()) != null && (currentWallpaper = wallpaperInfo3.getCurrentWallpaper()) != null) {
                kgVisualCheckBoxAddHomeWallpaperPickerButton$default.setPreviewThumbnail(currentWallpaper);
                ImageView extraIcon = kgVisualCheckBoxAddHomeWallpaperPickerButton$default.getExtraIcon();
                if (extraIcon != null) {
                    TemplateViewsKt.setColorMode(extraIcon, BitmapUtil.INSTANCE.getColorMode(currentWallpaper));
                }
            }
            kgVisualCheckBoxAddHomeWallpaperPickerButton$default.setClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.wallpaperButtonClick();
                }
            });
            Intrinsics.checkNotNull(kgVisualCheckBoxAddHomeWallpaperPickerButton$default, "null cannot be cast to non-null type com.miui.keyguard.editor.view.KgPreviewVisualCheckBox");
            this.wallpaperBtn = (KgPreviewVisualCheckBox) kgVisualCheckBoxAddHomeWallpaperPickerButton$default;
            if (!deviceUtil.isPad()) {
                FunctionButtonBar.addDivideLine$default(primaryButtonBar, 0, 0, 3, null);
                KgVisualCheckBox kgVisualCheckBoxAddHomeBlur$default = TemplateButtonBar.addHomeBlur$default(primaryButtonBar, 0, 1, null);
                kgVisualCheckBoxAddHomeBlur$default.setOnCheckListener(new KgVisualCheckBox.OnCheckListener() { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$$ExternalSyntheticLambda2
                    @Override // com.miui.keyguard.editor.view.KgVisualCheckBox.OnCheckListener
                    public final void onChecked(KgVisualCheckBox kgVisualCheckBox) {
                        HomeTemplateView.refreshBar$lambda$14$lambda$12$lambda$9(this.f$0, kgVisualCheckBox);
                    }
                });
                kgVisualCheckBoxAddHomeBlur$default.setOnUncheckListener(new KgVisualCheckBox.OnUncheckListener() { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$$ExternalSyntheticLambda3
                    @Override // com.miui.keyguard.editor.view.KgVisualCheckBox.OnUncheckListener
                    public final void onUncheck(KgVisualCheckBox kgVisualCheckBox) {
                        HomeTemplateView.refreshBar$lambda$14$lambda$12$lambda$10(this.f$0, kgVisualCheckBox);
                    }
                });
                kgVisualCheckBoxAddHomeBlur$default.setDisableClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.HomeTemplateView$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        HomeTemplateView.refreshBar$lambda$14$lambda$12$lambda$11(this.f$0, view);
                    }
                });
                BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.blurBtn, true, false, 4, null);
                this.blurBtn = kgVisualCheckBoxAddHomeBlur$default;
            }
            ColorModes colorModes = getColorModes();
            if (colorModes != null) {
                onColorModesChanged(colorModes);
            }
            primaryButtonBar.show(isSelectHomeTemplate());
            updateFollowButtonStatus(Integer.valueOf(getFollowStatus()));
            updateWallpaperStatus(Integer.valueOf(getHomeWallpaperStatus()));
            CommonConfig templateConfig4 = getTemplateConfig();
            updateBlurButtonStatus(Integer.valueOf((templateConfig4 == null || (homeInfo2 = templateConfig4.getHomeInfo()) == null || !homeInfo2.isThirdPartyWallpaper()) ? false : true ? 0 : getHomeFilterId()));
            if (isFollowWallpaper()) {
                this.lastFollowBlurStatus = Integer.valueOf(getHomeFilterId());
            } else {
                this.lastWallpaperBlurStatus = Integer.valueOf(getHomeFilterId());
                CommonConfig templateConfig5 = getTemplateConfig();
                if (Intrinsics.areEqual((templateConfig5 == null || (lockscreenInfo2 = templateConfig5.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getResourceType(), "image")) {
                    i = 2;
                } else {
                    CommonConfig templateConfig6 = getTemplateConfig();
                    if (Intrinsics.areEqual((templateConfig6 == null || (lockscreenInfo = templateConfig6.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType(), "gallery")) {
                        i = 2;
                    } else {
                        i = 0;
                    }
                }
                this.lastFollowBlurStatus = i;
            }
            if (isFollowWallpaper()) {
                return;
            }
            CommonConfig templateConfig7 = getTemplateConfig();
            if (templateConfig7 != null && (homeInfo = templateConfig7.getHomeInfo()) != null) {
                wallpaperInfo6 = homeInfo.getWallpaperInfo();
            }
            setDynamicSwitchButton(wallpaperInfo6);
        }
    }

    public final void setBlurBtnOnScanle(boolean z) {
        KgVisualCheckBox kgVisualCheckBox = this.blurBtn;
        if (kgVisualCheckBox != null) {
            kgVisualCheckBox.setScale(z);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void setWallpaperHasChanged(boolean z) {
        this.wallpaperHasChanged = z;
    }

    public final boolean shouldFollowEventType(int i) {
        return i == 30 || i == 210 || i == 37 || i == 38;
    }

    public final void showDynamicButtonStatus(WallpaperInfo wallpaperInfo) {
        if (isFollowWallpaper() || !Wallpaper.Companion.isLivePhotoResource(Integer.valueOf(wallpaperInfo.getVideoType()))) {
            return;
        }
        getDynamicSwitchCallback().showDynamicSwitchButton();
    }

    public final void startHomeVideoWithFallback(String str) {
        if (str != null && str.length() != 0 && FileUtil.isFileExist(str)) {
            startForcePlayerWithNormalVideo(str, homePageFocus(), "home normal");
            return;
        }
        Log.w("Keyguard-Editor-HomeTemplateView", "startHomeVideoWithFallback: home video missing, async recovery. path=" + str);
        onTemplateReady(2);
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(str, this, null), 3, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void startPickColorFromWallpaper(@Nullable Bitmap bitmap, boolean z, boolean z2, @Nullable Rect rect) {
    }

    public final void startPlayerIfVideoType(WallpaperInfo wallpaperInfo) {
        Log.d("Keyguard-Editor-HomeTemplateView", "startPlayerIfVideoType: " + wallpaperInfo);
        if (wallpaperInfo == null || !TextUtils.equals(wallpaperInfo.getResourceType(), "video")) {
            return;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
        Bitmap currentWallpaper = wallpaperInfo.getCurrentWallpaper();
        if (currentWallpaper != null) {
            combinedWallpaperView.setBackgroundDrawable(new CenterCropDrawable(currentWallpaper));
        }
        startHomeVideoWithFallback(wallpaperInfo.getOriginResourcePath());
    }

    public final void startPlayerIfVideoTypeWhenSwitchWallpaper(WallpaperInfo wallpaperInfo) {
        Log.d("Keyguard-Editor-HomeTemplateView", "startPlayerIfVideoType: " + wallpaperInfo);
        if (wallpaperInfo == null || !TextUtils.equals(wallpaperInfo.getResourceType(), "video")) {
            return;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
        Bitmap currentWallpaper = wallpaperInfo.getCurrentWallpaper();
        if (currentWallpaper != null) {
            combinedWallpaperView.setBackgroundDrawable(new CenterCropDrawable(currentWallpaper));
        }
        if (getTransitionBackground() == null) {
            setNeedResetBackground(true);
        } else {
            if (combinedWallpaperView != null) {
                combinedWallpaperView.setBackgroundDrawable(new BitmapDrawable(getResources(), getTransitionBackground()));
            }
            setNeedRemoveBackground(true);
        }
        startHomeVideoWithFallback(wallpaperInfo.getOriginResourcePath());
    }

    /* JADX WARN: Code duplicated, block: B:20:0x006d  */
    /* JADX WARN: Code duplicated, block: B:8:0x002d  */
    public final void switchWallpaperView(WallpaperInfo wallpaperInfo, int i, WallpaperPositionInfo wallpaperPositionInfo) {
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
        CombinedWallpaperView.switchWallpaperView$default(combinedWallpaperView, wallpaperInfo, currentWallpaper, wallpaperPositionInfo, true, true, WallpaperInfoType.HOME_WALLPAPER, false, 64, null);
        updateBlurButtonStatus(wallpaperInfo);
        if (i == 1) {
            startPlayerIfVideoTypeWhenSwitchWallpaper(wallpaperInfo);
        } else if (i == 2) {
            if (getTransitionBackground() == null) {
                setNeedResetBackground(true);
            } else {
                combinedWallpaperView.setBackgroundDrawable(new BitmapDrawable(getResources(), getTransitionBackground()));
                setNeedRemoveBackground(true);
            }
            releaseFastPlayer();
        } else if (i == 4) {
            startPlayerIfVideoTypeWhenSwitchWallpaper(wallpaperInfo);
        }
        updateHomeIconPreview$default(this, wallpaperInfo.getCurrentWallpaper(), null, 2, null);
    }

    public final void trackBlurButtonStatus() {
        String str;
        int homeFilterId = getHomeFilterId();
        if (homeFilterId != 0) {
            str = homeFilterId != 1 ? "关" : "开";
        } else {
            str = "不支持";
        }
        String str2 = str;
        Context context = getContext();
        if (context != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            String string = context.getString(R.string.kg_wallpaper_filter_type_obscure);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String trackId = getTrackId();
            if (trackId == null) {
                trackId = "";
            }
            trackHelper.trackClickEditorItem(context, 1, string, str2, null, trackId);
        }
    }

    public final void trackFollowStatus() {
        int followStatus = getFollowStatus();
        String str = (followStatus == 0 || !(followStatus == 1 || followStatus == 2)) ? "关" : "开";
        Context context = getContext();
        if (context != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            String string = context.getString(R.string.kg_function_button_text_follow);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            TrackHelper.trackClickEditorItem$default(trackHelper, context, 1, string, str, null, null, 32, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.FollowSystemListener
    public void trigger(int i, @Nullable Object obj) {
        Log.d("Keyguard-Editor-HomeTemplateView", "type is " + i + ",data is " + obj + ",fellowStatus is " + getFollowStatus());
        if (i == 1) {
            if (obj instanceof Matrix) {
                Matrix matrix = (Matrix) obj;
                this.lastFollowMatrix = matrix;
                if (isFollowAll()) {
                    onMatrixChanged(matrix);
                    return;
                }
                return;
            }
            return;
        }
        if (i == 2) {
            if (obj instanceof EditData) {
                EditData editData = (EditData) obj;
                if (shouldFollowEventType(editData.getEventType())) {
                    onEdited(editData.getEventType(), obj);
                    return;
                }
                return;
            }
            return;
        }
        if (i != 3) {
            if (i != 4) {
                return;
            }
            checkNeedRemoveBackground();
        } else if (obj instanceof Matrix) {
            this.lastFollowMatrix = (Matrix) obj;
        }
    }

    public final void uncheckFollowButton() {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        int followStatus = getFollowStatus();
        if (followStatus == 1) {
            if (viewModel != null) {
                EditFragmentViewModel.updateFollowStatus$default(viewModel, 3, false, false, 6, null);
            }
        } else if (followStatus == 2 && viewModel != null) {
            EditFragmentViewModel.updateFollowStatus$default(viewModel, 0, false, false, 6, null);
        }
    }

    public final void uncheckWallpaperButton() {
        EditFragmentViewModel viewModel;
        if (getHomeWallpaperStatus() != 2 || (viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment())) == null) {
            return;
        }
        EditFragmentViewModel.updateHomeWallpaperStatus$default(viewModel, 1, false, false, 6, null);
    }

    public final void updateBlurButtonStatus(WallpaperInfo wallpaperInfo) {
        wallpaperInfo.getMagicType();
        int i = Intrinsics.areEqual(wallpaperInfo.getResourceType(), "image") ? 2 : 0;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateHomeFilterId$default(viewModel, i, false, false, 6, null);
        }
    }

    public final void updateBlurButtonStatus(@Nullable Integer num) {
        boolean z;
        if (num == null || num.intValue() != 0) {
            if (num != null && num.intValue() == 1) {
                KgVisualCheckBox kgVisualCheckBox = this.blurBtn;
                if (kgVisualCheckBox != null) {
                    kgVisualCheckBox.enable();
                }
                KgVisualCheckBox kgVisualCheckBox2 = this.blurBtn;
                if (kgVisualCheckBox2 != null) {
                    kgVisualCheckBox2.setCheckedOnly(true);
                }
                z = true;
            } else if (num != null && num.intValue() == 2) {
                KgVisualCheckBox kgVisualCheckBox3 = this.blurBtn;
                if (kgVisualCheckBox3 != null) {
                    kgVisualCheckBox3.enable();
                }
                KgVisualCheckBox kgVisualCheckBox4 = this.blurBtn;
                if (kgVisualCheckBox4 != null) {
                    kgVisualCheckBox4.setCheckedOnly(false);
                }
            }
            BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.blurBtn, z, false, 4, null);
        }
        KgVisualCheckBox kgVisualCheckBox5 = this.blurBtn;
        if (kgVisualCheckBox5 != null) {
            kgVisualCheckBox5.disable();
        }
        z = false;
        BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.blurBtn, z, false, 4, null);
    }

    public final void updateBlurButtonStatus(boolean z, int i) {
        EditFragmentViewModel viewModel;
        if (isFromLockButNotCheckFollow(z) || (viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment())) == null) {
            return;
        }
        EditFragmentViewModel.updateHomeFilterId$default(viewModel, i, false, false, 6, null);
    }

    /* JADX WARN: Code duplicated, block: B:37:0x005c  */
    /* JADX WARN: Code duplicated, block: B:39:? A[RETURN, SYNTHETIC] */
    public final void updateFollowButtonStatus(@Nullable Integer num) {
        KgPreviewVisualCheckBox kgPreviewVisualCheckBox;
        boolean z;
        if (num == null || num.intValue() != 0) {
            if (num != null && num.intValue() == 1) {
                KgPreviewVisualCheckBox kgPreviewVisualCheckBox2 = this.followBtn;
                if (kgPreviewVisualCheckBox2 != null) {
                    kgPreviewVisualCheckBox2.updateStatus(3);
                }
            } else if (num != null && num.intValue() == 2) {
                KgPreviewVisualCheckBox kgPreviewVisualCheckBox3 = this.followBtn;
                if (kgPreviewVisualCheckBox3 != null) {
                    kgPreviewVisualCheckBox3.updateStatus(2);
                }
                getDynamicSwitchCallback().removeDynamicSwitchButton();
            } else if (num != null && num.intValue() == 3 && (kgPreviewVisualCheckBox = this.followBtn) != null) {
                kgPreviewVisualCheckBox.updateStatus(1);
            }
            z = true;
            BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.followBtn, z, false, 4, null);
            if (z) {
                updateVisualCheckBoxChecked(this.wallpaperBtn, false, true);
            }
        }
        KgPreviewVisualCheckBox kgPreviewVisualCheckBox4 = this.followBtn;
        if (kgPreviewVisualCheckBox4 != null) {
            kgPreviewVisualCheckBox4.updateStatus(1);
        }
        z = false;
        BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.followBtn, z, false, 4, null);
        if (z) {
            updateVisualCheckBoxChecked(this.wallpaperBtn, false, true);
        }
    }

    public final void updateHomeIconPreview(@Nullable Bitmap bitmap, @Nullable Boolean bool) {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        if ((Intrinsics.areEqual(bool, Boolean.FALSE) || isFollowWallpaper()) && bitmap != null) {
            this.currentSelectWallpaper = bitmap;
        }
        if (this.currentSelectWallpaper == null) {
            CommonConfig templateConfig = getTemplateConfig();
            this.currentSelectWallpaper = (templateConfig == null || (homeInfo = templateConfig.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper();
        }
        ImageView imageView = this.homeIconPreview;
        if (imageView != null) {
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            imageView.setImageResource(commonUtils.obtainHomeLayoutDrawable(context, isDarkWallpaper()));
        }
    }

    public final void updateHomeInfo(WallpaperTypeInfo wallpaperTypeInfo, boolean z) {
        HomeConfig homeInfo;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfo;
        String resourcePath;
        Integer videoType;
        Boolean boolIsSupportLoop;
        if (wallpaperTypeInfo != null) {
            wallpaperTypeInfo.setEnableBlur(false);
        }
        if (z) {
            return;
        }
        CommonConfig templateConfig = getTemplateConfig();
        if (templateConfig != null && (homeInfo2 = templateConfig.getHomeInfo()) != null && (wallpaperInfo = homeInfo2.getWallpaperInfo()) != null) {
            wallpaperInfo.setCurrentWallpaper(wallpaperTypeInfo != null ? wallpaperTypeInfo.getBitmap() : null);
            wallpaperInfo.setResourceType(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null);
            wallpaperInfo.setOriginResourcePath(wallpaperTypeInfo != null ? wallpaperTypeInfo.getResourcePath() : null);
            if (wallpaperTypeInfo == null || (resourcePath = wallpaperTypeInfo.getResourcePath()) == null) {
                resourcePath = "";
            }
            wallpaperInfo.setSource(resourcePath);
            wallpaperInfo.setMask(wallpaperTypeInfo != null ? wallpaperTypeInfo.getMaskPath() : null);
            wallpaperInfo.setSupportSubject(wallpaperTypeInfo != null ? wallpaperTypeInfo.getSupportSubject() : false);
            wallpaperInfo.setPositionInfo(null);
            wallpaperInfo.setNeedDark(wallpaperTypeInfo != null ? wallpaperTypeInfo.isNeedDark() : false);
            wallpaperInfo.setEnableBlur((wallpaperTypeInfo != null ? wallpaperTypeInfo.getEnableBlur() : false) && wallpaperInfo.getEnableBlur());
            if (wallpaperTypeInfo != null) {
                wallpaperTypeInfo.setEnableBlur(wallpaperInfo.getEnableBlur());
            }
            wallpaperInfo.setSupportLoop((wallpaperTypeInfo == null || (boolIsSupportLoop = wallpaperTypeInfo.isSupportLoop()) == null) ? true : boolIsSupportLoop.booleanValue());
            wallpaperInfo.setSensorWallpaperPrams(wallpaperTypeInfo != null ? wallpaperTypeInfo.getSensorWallpaperPrams() : null);
            wallpaperInfo.setVideoType((wallpaperTypeInfo == null || (videoType = wallpaperTypeInfo.getVideoType()) == null) ? 0 : videoType.intValue());
            wallpaperInfo.setAiGenerated(wallpaperTypeInfo != null ? wallpaperTypeInfo.isAiGenerated() : false);
            if (WallpaperEditor.Companion.isDepthVideo(wallpaperTypeInfo)) {
                wallpaperInfo.setDepthVideo(wallpaperTypeInfo != null ? wallpaperTypeInfo.getResourcePath() : null);
                wallpaperInfo.setDepthVideo(true);
            } else {
                wallpaperInfo.setDepthVideo((String) null);
                wallpaperInfo.setDepthVideo(false);
            }
            wallpaperInfo.setSupportFullScreenAod(wallpaperTypeInfo != null ? wallpaperTypeInfo.isSupportFullScreenAod() : null);
        }
        CommonConfig templateConfig2 = getTemplateConfig();
        if (templateConfig2 == null || (homeInfo = templateConfig2.getHomeInfo()) == null) {
            return;
        }
        homeInfo.setDarkenWallpaper(null);
        homeInfo.setWallpaperInfoForVideo(null);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0016  */
    public final void updateLastBlurStatus(boolean z, String str) {
        int i;
        KgVisualCheckBox kgVisualCheckBox;
        if (!Intrinsics.areEqual(str, "image")) {
            i = 0;
        } else if (!z || (kgVisualCheckBox = this.blurBtn) == null) {
            i = 2;
        } else {
            i = 1;
            if (!kgVisualCheckBox.isChecked()) {
                i = 2;
            }
        }
        if (z) {
            this.lastFollowBlurStatus = Integer.valueOf(i);
        } else {
            this.lastWallpaperBlurStatus = Integer.valueOf(i);
        }
        updateBlurButtonStatus(z, i);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void updateVideoOrImageStatus(int i) {
        if (i == 2) {
            onEdited(220, null);
        } else {
            if (i != 3) {
                return;
            }
            onEdited(221, null);
        }
    }

    public final void updateWallpaperStatus(@Nullable Integer num) {
        boolean z;
        if (num == null || num.intValue() != 0) {
            if (num == null || num.intValue() != 1) {
                if (num != null && num.intValue() == 2) {
                    KgPreviewVisualCheckBox kgPreviewVisualCheckBox = this.wallpaperBtn;
                    if (kgPreviewVisualCheckBox != null) {
                        kgPreviewVisualCheckBox.updateStatus(3);
                    }
                    this.followStatusChanged = true;
                }
                BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.wallpaperBtn, z, false, 4, null);
            }
            KgPreviewVisualCheckBox kgPreviewVisualCheckBox2 = this.wallpaperBtn;
            if (kgPreviewVisualCheckBox2 != null) {
                kgPreviewVisualCheckBox2.updateStatus(1);
            }
            this.followStatusChanged = true;
            z = true;
            BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.wallpaperBtn, z, false, 4, null);
        }
        KgPreviewVisualCheckBox kgPreviewVisualCheckBox3 = this.wallpaperBtn;
        if (kgPreviewVisualCheckBox3 != null) {
            kgPreviewVisualCheckBox3.updateStatus(0);
        }
        z = false;
        BaseTemplateView.updateVisualCheckBoxChecked$default(this, this.wallpaperBtn, z, false, 4, null);
    }

    public final void wallpaperButtonChecked() {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo2;
        CommonConfig templateConfig = getTemplateConfig();
        this.lastHalfFollowPositionInfo = getPositionInfo((templateConfig == null || (lockscreenInfo2 = templateConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo());
        CommonConfig templateConfig2 = getTemplateConfig();
        if (templateConfig2 != null && (homeInfo2 = templateConfig2.getHomeInfo()) != null && (wallpaperInfo2 = homeInfo2.getWallpaperInfo()) != null) {
            CommonConfig templateConfig3 = getTemplateConfig();
            int switchType = getSwitchType((templateConfig3 == null || (lockscreenInfo = templateConfig3.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType(), wallpaperInfo2.getResourceType());
            CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
            if (combinedWallpaperView != null) {
                CombinedWallpaperView.resetCurrentMagicType$default(combinedWallpaperView, 0, 1, null);
            }
            switchWallpaperView(wallpaperInfo2, switchType, this.lastWallpaperPositionInfo);
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateHomeWallpaperStatus$default(viewModel, 2, false, false, 6, null);
        }
        uncheckFollowButton();
        if (viewModel != null) {
            Integer num = this.lastWallpaperBlurStatus;
            EditFragmentViewModel.updateHomeFilterId$default(viewModel, num != null ? num.intValue() : 2, false, false, 6, null);
        }
        CommonConfig templateConfig4 = getTemplateConfig();
        if (templateConfig4 == null || (homeInfo = templateConfig4.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) {
            return;
        }
        showDynamicButtonStatus(wallpaperInfo);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x002d  */
    public final void wallpaperButtonClick() {
        Log.d("Keyguard-Editor-HomeTemplateView", "refreshBar: home wallpaper click:" + getHomeWallpaperStatus());
        int homeWallpaperStatus = getHomeWallpaperStatus();
        if (homeWallpaperStatus == 0) {
            setWallpaperBtnClickable(false);
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C01171(null), 2, null);
            wallpaperButtonLaunch();
        } else if (homeWallpaperStatus == 1) {
            wallpaperButtonChecked();
        } else if (homeWallpaperStatus == 2) {
            setWallpaperBtnClickable(false);
            EditorActivityViewModel.Companion companion2 = EditorActivityViewModel.Companion;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion2.getCoroutineScope(context2), Dispatchers.getMain(), null, new C01171(null), 2, null);
            wallpaperButtonLaunch();
        }
        Context context3 = getContext();
        if (context3 != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            String string = context3.getString(R.string.kg_function_button_text_home_pick);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String trackId = getTrackId();
            if (trackId == null) {
                trackId = "";
            }
            trackHelper.trackClickEditorItem(context3, 1, string, "", null, trackId);
        }
    }

    public final void wallpaperButtonLaunch() {
        TemplateConfig lockscreenInfo;
        Log.d("Keyguard-Editor-HomeTemplateView", "refreshBar: ");
        if (isFollowWallpaper()) {
            CommonConfig templateConfig = getTemplateConfig();
            this.lastHalfFollowPositionInfo = getPositionInfo((templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo());
        }
        WallpaperEditor.WallpaperPickerLauncher wallpaperPickerLauncher = getWallpaperPickerLauncher();
        if (wallpaperPickerLauncher != null) {
            wallpaperPickerLauncher.launch();
        }
    }
}

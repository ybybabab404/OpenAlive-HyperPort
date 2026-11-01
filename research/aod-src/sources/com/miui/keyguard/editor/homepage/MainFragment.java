package com.miui.keyguard.editor.homepage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.activity.OnBackPressedCallback;
import androidx.core.os.BundleKt;
import androidx.core.view.KeyEventDispatcher;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorActivityForSettings;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.FashionGalleryCompat;
import com.miui.keyguard.editor.NavigationController;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.BaseFragment;
import com.miui.keyguard.editor.base.BaseOnBackPressedCallback;
import com.miui.keyguard.editor.base.TransitionHost;
import com.miui.keyguard.editor.base.TransitionLayer;
import com.miui.keyguard.editor.base.TransitionPerformer;
import com.miui.keyguard.editor.base.TransitionPerformerImpl;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.StatusRecord;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateChangeRecorder;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.GalleryStatusBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.model.CrossListDataModel;
import com.miui.keyguard.editor.homepage.util.ApplyCallback;
import com.miui.keyguard.editor.homepage.util.OnVerticalScrollAnimationCallback;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.util.TemplateApplyController;
import com.miui.keyguard.editor.homepage.util.TitleDescController;
import com.miui.keyguard.editor.homepage.util.TransformerController;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.homepage.view.generator.DynamicViewFactory;
import com.miui.keyguard.editor.homepage.view.generator.MainFragmentDynamicViewFactoryImpl;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.settings.SettingsTemplatesProviderManager;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.ScenarioRecognitionUtils;
import com.miui.keyguard.editor.utils.TemplateSource;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.LockScreenTransformerController;
import com.miui.keyguard.editor.view.LockScreenTransformerLayer;
import com.miui.keyguard.editor.view.TransformerInfo;
import com.miui.keyguard.editor.view.TransformerPositionInfo;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
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
/* JADX INFO: compiled from: MainFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class MainFragment extends BaseFragment implements CrossViewPagerCallback, EditorActivity.NavigationActionListener, OnVerticalScrollAnimationCallback {
    public static final Companion Companion = new Companion(null);
    public static final long TAKE_SCREENSHUT_DELY_TIME = 100;
    public static boolean isEnterCrossTower;
    public static boolean isEnterEditor;
    public FrameLayout containerForScreenshot;
    public FashionGalleryCompat fashionGalleryCompat;
    public boolean inEnterEditFragment;
    public volatile boolean isDataListRefreshed;
    public boolean isFillData;
    public BaseItemViewHolder lastSelectedHolder;
    public CrossViewPager mCrossViewPager;
    public CrossListDataModel mDataModel;
    public boolean mIsApply;
    public boolean mIsCancel;
    public boolean mScrolled;
    public TemplateApplyController mTemplateApplyController;
    public TransformerController mTransformerController;
    public String myCustomize;
    public String mySets;
    public Function0 myTemplateUpdateCallback;
    public WeakReference myTemplateView;
    public boolean needReloadData;
    public boolean shouldCaptureCurrentTemplate;
    public long startCancelTime;
    public long startPickerTime;
    public int[] trackShowTag;
    public final Point mSelectedPosition = new Point();
    public boolean hasCurrentTemplate = true;
    public Runnable mCancelLongClickInterceptRunnable = new Runnable() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.interceptItemLongClick(false);
        }
    };
    public boolean isFirstEnter = true;

    /* JADX INFO: compiled from: MainFragment.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public long getTAKE_SCREENSHUT_DELY_TIME() {
            return MainFragment.TAKE_SCREENSHUT_DELY_TIME;
        }

        public boolean isEnterCrossTower() {
            return MainFragment.isEnterCrossTower;
        }

        public boolean isEnterEditor() {
            return MainFragment.isEnterEditor;
        }

        public void setEnterEditor(boolean z) {
            MainFragment.isEnterEditor = z;
        }
    }

    /* JADX INFO: compiled from: MainFragment.kt */
    @Metadata
    public static final class MainBackPressBack extends BaseOnBackPressedCallback {
        public final FragmentActivity activity;
        public final MainFragment fragment;
        public final int[] trackShowTag;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MainBackPressBack(FragmentActivity activity, MainFragment fragment, int[] iArr) {
            super(activity);
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            this.activity = activity;
            this.fragment = fragment;
            this.trackShowTag = iArr;
        }

        @Override // com.miui.keyguard.editor.base.BaseOnBackPressedCallback, androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            Log.i("Keyguard-Editor-MainFragment", "MainBackPressBack： handleOnBackPressed");
            this.fragment.onBackPressedDispatched();
            this.fragment.completeExitIfCancelOrBackPressed(this.activity);
            TrackHelper.trackQuitHomePage$default(TrackHelper.INSTANCE, this.activity, "其他", null, null, this.trackShowTag, MainFragment.Companion.isEnterEditor(), false, 64, null);
        }
    }

    /* JADX INFO: compiled from: MainFragment.kt */
    @Metadata
    public static final class ResourcePreloadTask implements Runnable {
        public WeakReference contextReference;

        public ResourcePreloadTask(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.contextReference = new WeakReference(context);
        }

        @Override // java.lang.Runnable
        public void run() {
            Context context = (Context) this.contextReference.get();
            if (context == null) {
                Log.w("Keyguard-Editor-MainFragment", "ResourcePreloadTask#run ignored: context is recycled.");
            } else {
                Log.i("Keyguard-Editor-MainFragment", "ResourcePreloadTask#run: start preload.");
                ResourcesProvider.INSTANCE.loadResources(context);
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.MainFragment$updateMyTemplateViewForGallery$1, reason: invalid class name */
    /* JADX INFO: compiled from: MainFragment.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.MainFragment$updateMyTemplateViewForGallery$1", f = "MainFragment.kt", l = {193}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MainFragment.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            boolean zIsLoop;
            CommonConfig templateConfig;
            TemplateConfig lockscreenInfo;
            WallpaperInfo wallpaperInfo;
            ExtraInfo extraInfo;
            TemplateConfig lockscreenInfo2;
            WallpaperInfo wallpaperInfo2;
            TemplateConfig lockscreenInfo3;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                MainFragment$updateMyTemplateViewForGallery$1$galleryStatusBean$1 mainFragment$updateMyTemplateViewForGallery$1$galleryStatusBean$1 = new MainFragment$updateMyTemplateViewForGallery$1$galleryStatusBean$1(MainFragment.this, null);
                this.label = 1;
                obj = BuildersKt.withContext(io2, mainFragment$updateMyTemplateViewForGallery$1$galleryStatusBean$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            GalleryStatusBean galleryStatusBean = (GalleryStatusBean) obj;
            WeakReference weakReference = MainFragment.this.myTemplateView;
            BaseTemplateView baseTemplateView = weakReference != null ? (BaseTemplateView) weakReference.get() : null;
            if (DeviceUtil.INSTANCE.isInternationalBuild()) {
                zIsLoop = FashionGalleryHelper.INSTANCE.isLoopTemplate(galleryStatusBean.getLoopType());
            } else if (baseTemplateView == null || !TemplateSource.INSTANCE.isFromCurrentTemplate(baseTemplateView.getTemplateSource())) {
                FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                Context contextRequireContext = MainFragment.this.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                zIsLoop = fashionGalleryHelper.isLoop(contextRequireContext, true, (baseTemplateView == null || (templateConfig = baseTemplateView.getTemplateConfig()) == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || (extraInfo = wallpaperInfo.getExtraInfo()) == null) ? null : extraInfo.getLoopType());
            } else {
                CommonConfig templateConfig2 = baseTemplateView.getTemplateConfig();
                if (templateConfig2 != null && (lockscreenInfo2 = templateConfig2.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                    if (wallpaperInfo2.getExtraInfo() == null) {
                        wallpaperInfo2.setExtraInfo(new ExtraInfo(null, null, 3, null));
                    }
                    ExtraInfo extraInfo2 = wallpaperInfo2.getExtraInfo();
                    Intrinsics.checkNotNull(extraInfo2);
                    extraInfo2.setLoopType(galleryStatusBean.getLoopType());
                }
                FashionGalleryHelper fashionGalleryHelper2 = FashionGalleryHelper.INSTANCE;
                Context contextRequireContext2 = MainFragment.this.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
                zIsLoop = fashionGalleryHelper2.isLoop(contextRequireContext2, true, galleryStatusBean.getLoopType());
            }
            Log.d("Keyguard-Editor-MainFragment", "updateMyTemplateViewForGallery: isGalleryLoop=" + zIsLoop);
            if (zIsLoop) {
                if (baseTemplateView != null && !baseTemplateView.isGalleryOpened()) {
                    CommonConfig templateConfig3 = baseTemplateView.getTemplateConfig();
                    WallpaperInfo wallpaperInfo3 = (templateConfig3 == null || (lockscreenInfo3 = templateConfig3.getLockscreenInfo()) == null) ? null : lockscreenInfo3.getWallpaperInfo();
                    int magicType = wallpaperInfo3 != null ? wallpaperInfo3.getMagicType() : 0;
                    if (magicType != 0) {
                        Log.d("Keyguard-Editor-MainFragment", "updateMyTemplateViewForGallery: reset wallpaper filter");
                        if (MagicType.INSTANCE.isDepth(Boxing.boxInt(magicType))) {
                            baseTemplateView.onEdited(40, null);
                        } else {
                            baseTemplateView.onEdited(10, Boxing.boxInt(0));
                            if (wallpaperInfo3 != null && wallpaperInfo3.getEnableBlur()) {
                                baseTemplateView.onEdited(90, Boxing.boxBoolean(false));
                            }
                        }
                        DeviceScreenshotHelper.INSTANCE.setFirstScreenshotCacheInvalid(true);
                    }
                    baseTemplateView.setGalleryOpened(true);
                }
            } else if (baseTemplateView != null && baseTemplateView.isGalleryOpened()) {
                baseTemplateView.setGalleryOpened(false);
            }
            return Unit.INSTANCE;
        }
    }

    public static final void completeExitIfCancelOrBackPressed$lambda$27(MainFragment mainFragment, FragmentActivity fragmentActivity) {
        TransformerController transformerController;
        CrossViewPager crossViewPager = mainFragment.mCrossViewPager;
        TransformerInfo transformerInfoGenerateTransformerInfo = null;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        Point selectedPosition = crossViewPager.getSelectedPosition();
        int i = selectedPosition != null ? selectedPosition.x : -1;
        int i2 = selectedPosition != null ? selectedPosition.y : -1;
        if (i != -1 && i2 != -1 && (transformerController = mainFragment.mTransformerController) != null) {
            transformerInfoGenerateTransformerInfo = transformerController.generateTransformerInfo(i, i2);
        }
        ((EditorActivity) fragmentActivity).onCancel(transformerInfoGenerateTransformerInfo);
    }

    public static /* synthetic */ TransformerInfo generateTransformerInfoForSelected$default(MainFragment mainFragment, Bitmap bitmap, int i, Object obj) {
        if ((i & 1) != 0) {
            bitmap = null;
        }
        return mainFragment.generateTransformerInfoForSelected(bitmap);
    }

    public static final void initControllers$lambda$3(MainFragment mainFragment, Pair it) {
        Intrinsics.checkNotNullParameter(it, "it");
        mainFragment.onApplyMyTemplateCompleted((TemplateItemBean) it.getFirst(), ((Boolean) it.getSecond()).booleanValue());
    }

    private final void initFashionGalleryCompat() {
        if (DeviceUtil.INSTANCE.isInternationalBuild() && this.fashionGalleryCompat == null) {
            this.fashionGalleryCompat = new FashionGalleryCompat(this);
        }
    }

    public static /* synthetic */ void navigateToEditFragment$default(MainFragment mainFragment, int i, int i2, String str, long j, boolean z, boolean z2, int i3, int i4, Object obj) {
        if ((i4 & 16) != 0) {
            z = false;
        }
        if ((i4 & 32) != 0) {
            z2 = false;
        }
        mainFragment.navigateToEditFragment(i, i2, str, j, z, z2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void onApplyCompleted$lambda$21(MainFragment mainFragment, Bitmap bitmap, FragmentActivity fragmentActivity, boolean z) {
        if (mainFragment.isAdded()) {
            TransformerInfo transformerInfoGenerateTransformerInfoForSelected = mainFragment.generateTransformerInfoForSelected(bitmap);
            boolean z2 = fragmentActivity instanceof LockScreenTransformerController;
            Log.d("Keyguard-Editor-MainFragment", "onApplyCompleted: " + z2);
            if (z2) {
                ((LockScreenTransformerController) fragmentActivity).onApplyCompleted(z, transformerInfoGenerateTransformerInfoForSelected);
            } else {
                mainFragment.resetApplyingFlag("onApplyCompletedNotController");
            }
        }
    }

    private final void onApplyMyTemplateCompleted(final TemplateItemBean templateItemBean, final boolean z) {
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        crossViewPager.post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MainFragment.onApplyMyTemplateCompleted$lambda$23(this.f$0, templateItemBean, z);
            }
        });
    }

    public static final void onApplyMyTemplateCompleted$lambda$23(MainFragment mainFragment, TemplateItemBean templateItemBean, boolean z) {
        TransformerInfo transformerInfoGenerateTransformerInfoForSelected$default = generateTransformerInfoForSelected$default(mainFragment, null, 1, null);
        FragmentActivity activity = mainFragment.getActivity();
        if (activity instanceof EditorActivity) {
            ((EditorActivity) activity).onApplyMyTemplate(templateItemBean, transformerInfoGenerateTransformerInfoForSelected$default, z);
        }
    }

    public static final Unit onCurrentTemplateScreenshot$lambda$20$lambda$18(MainFragment mainFragment) {
        Log.w("Keyguard-Editor-MainFragment", "takeCaptureForMultiScreenMode success: onCurrentTemplateScreenshotsSaveComplete");
        TemplateApplyController templateApplyController = mainFragment.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(false);
        }
        TemplateApplyController templateApplyController2 = mainFragment.mTemplateApplyController;
        CrossViewPager crossViewPager = null;
        if (templateApplyController2 != null) {
            TemplateApplyController.onCurrentTemplateScreenshotsSaveComplete$default(templateApplyController2, false, 1, null);
        }
        if (Intrinsics.areEqual(mainFragment.myCustomize, "my_customize")) {
            CrossViewPager crossViewPager2 = mainFragment.mCrossViewPager;
            if (crossViewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            } else {
                crossViewPager = crossViewPager2;
            }
            RecyclerView.ViewHolder selectedViewHolder = crossViewPager.getSelectedViewHolder();
            Intrinsics.checkNotNull(selectedViewHolder, "null cannot be cast to non-null type com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder");
            BaseItemViewHolder baseItemViewHolder = (BaseItemViewHolder) selectedViewHolder;
            CrossViewPagerCallback.DefaultImpls.onTemplateCustomized$default(mainFragment, baseItemViewHolder.getX(), baseItemViewHolder.getY(), (TemplateItemBean) baseItemViewHolder.getItemData(), 0, 8, null);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onCurrentTemplateScreenshot$lambda$20$lambda$19(MainFragment mainFragment, Throwable it) {
        Intrinsics.checkNotNullParameter(it, "it");
        TemplateApplyController templateApplyController = mainFragment.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(false);
        }
        return Unit.INSTANCE;
    }

    public static final List refreshWithDefaultData$lambda$28(MainFragment mainFragment, List list) {
        CrossListDataModel crossListDataModel = mainFragment.mDataModel;
        if (crossListDataModel != null) {
            FragmentActivity activity = mainFragment.getActivity();
            EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
            boolean z = false;
            if (editorActivity != null && editorActivity.callingFromSystemUI()) {
                z = true;
            }
            crossListDataModel.refreshDefaultDataListAsync$keyguardeditor_release(list, z);
        }
        return list;
    }

    public static final void refreshWithDefaultData$lambda$32(final MainFragment mainFragment, List list) {
        mainFragment.resetLockTransLayerPositionInfo();
        Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MainFragment.refreshWithDefaultData$lambda$32$lambda$31(this.f$0);
            }
        };
        WeakReference weakReference = mainFragment.myTemplateView;
        if ((weakReference != null ? (BaseTemplateView) weakReference.get() : null) != null) {
            function0.invoke();
        } else {
            mainFragment.myTemplateUpdateCallback = function0;
        }
    }

    public static final Unit refreshWithDefaultData$lambda$32$lambda$31(final MainFragment mainFragment) {
        final BaseTemplateView baseTemplateView;
        CommonConfig currentTemplateConfig;
        TemplateConfig lockscreenInfo;
        WeakReference weakReference = mainFragment.myTemplateView;
        if (weakReference != null && (baseTemplateView = (BaseTemplateView) weakReference.get()) != null) {
            CrossListDataModel crossListDataModel = mainFragment.mDataModel;
            baseTemplateView.updateWallPaper((crossListDataModel == null || (currentTemplateConfig = crossListDataModel.getCurrentTemplateConfig()) == null || (lockscreenInfo = currentTemplateConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo());
            baseTemplateView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    MainFragment.refreshWithDefaultData$lambda$32$lambda$31$lambda$30$lambda$29(this.f$0, baseTemplateView);
                }
            }, TAKE_SCREENSHUT_DELY_TIME);
        }
        return Unit.INSTANCE;
    }

    public static final void refreshWithDefaultData$lambda$32$lambda$31$lambda$30$lambda$29(MainFragment mainFragment, BaseTemplateView baseTemplateView) {
        FragmentActivity activity = mainFragment.getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        CrossListDataModel crossListDataModel = mainFragment.mDataModel;
        if ((crossListDataModel != null ? crossListDataModel.getCurrentTemplateConfig() : null) != null) {
            CrossListDataModel crossListDataModel2 = mainFragment.mDataModel;
            CommonConfig currentTemplateConfig = crossListDataModel2 != null ? crossListDataModel2.getCurrentTemplateConfig() : null;
            Intrinsics.checkNotNull(currentTemplateConfig);
            mainFragment.onCurrentTemplateScreenshot(baseTemplateView, null, currentTemplateConfig);
        }
    }

    public static final List refreshWithDefaultDataWhenUnFilled$lambda$33(MainFragment mainFragment, List list) {
        CrossListDataModel crossListDataModel = mainFragment.mDataModel;
        if (crossListDataModel != null) {
            FragmentActivity activity = mainFragment.getActivity();
            EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
            boolean z = false;
            if (editorActivity != null && editorActivity.callingFromSystemUI()) {
                z = true;
            }
            crossListDataModel.refreshDefaultDataListAsync$keyguardeditor_release(list, z);
        }
        return list;
    }

    public static /* synthetic */ void showDualClockDialog$default(MainFragment mainFragment, Context context, int i, int i2, String str, long j, boolean z, boolean z2, int i3, int i4, Object obj) {
        if ((i4 & 32) != 0) {
            z = false;
        }
        if ((i4 & 64) != 0) {
            z2 = false;
        }
        mainFragment.showDualClockDialog(context, i, i2, str, j, z, z2, i3);
    }

    public static final void showDualClockDialog$lambda$14(final MainFragment mainFragment, final int i, final int i2, final String str, final long j, final boolean z, final boolean z2, final int i3, final Context context, DialogInterface dialogInterface, int i4) {
        if (i4 == -2) {
            mainFragment.navigateToEditFragment(i, i2, str, j, z, z2, i3);
        } else {
            if (i4 != -1) {
                return;
            }
            DeviceScreenshotHelper.INSTANCE.setFirstScreenshotCacheInvalid(true);
            Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    return MainFragment.showDualClockDialog$lambda$14$lambda$10(context);
                }
            }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MainFragment.showDualClockDialog$lambda$14$lambda$13(j, mainFragment, i, i2, str, z, z2, i3, (Boolean) obj);
                }
            });
        }
    }

    public static final Boolean showDualClockDialog$lambda$14$lambda$10(Context context) {
        return Boolean.valueOf(DualClockManager.closeDualClock(context));
    }

    public static final void showDualClockDialog$lambda$14$lambda$13(long j, MainFragment mainFragment, int i, int i2, String str, boolean z, boolean z2, int i3, Boolean bool) {
        BaseTemplateView baseTemplateView;
        Integer magicType;
        BaseTemplateView baseTemplateView2;
        CommonConfig templateConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateChangeRecorder templateChangeRecorder;
        Log.i("Keyguard-Editor-MainFragment", "showDualClockDialog@closeDualClock: isDualClockClosed = " + bool + ", source = " + j);
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context contextRequireContext = mainFragment.requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        EditorActivityViewModel viewModel = companion.getViewModel(contextRequireContext);
        StatusRecord lastStatusRecord = (viewModel == null || (templateChangeRecorder = viewModel.getTemplateChangeRecorder()) == null) ? null : templateChangeRecorder.getLastStatusRecord(1);
        if (lastStatusRecord != null && (magicType = lastStatusRecord.getMagicType()) != null) {
            int iIntValue = magicType.intValue();
            WeakReference weakReference = mainFragment.myTemplateView;
            if (weakReference != null && (baseTemplateView2 = (BaseTemplateView) weakReference.get()) != null && (templateConfig = baseTemplateView2.getTemplateConfig()) != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.updateMagicType(iIntValue);
            }
        }
        mainFragment.navigateToEditFragment(i, i2, str, j, z, z2, i3);
        WeakReference weakReference2 = mainFragment.myTemplateView;
        if (weakReference2 == null || (baseTemplateView = (BaseTemplateView) weakReference2.get()) == null || !mainFragment.myTemplateViewHiding()) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(mainFragment), null, null, new MainFragment$showDualClockDialog$dualClockDialogListener$1$2$2$1(baseTemplateView, null), 3, null);
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public void beforeApply() {
        EditorActivityViewModel viewModel;
        if (isForwardTransition()) {
            Log.d("Keyguard-Editor-MainFragment", "beforeApply failed: isForwardTransition = true.");
            return;
        }
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        CommonConfig selectedItemConfig = templateApplyController != null ? templateApplyController.setSelectedItemConfig() : null;
        Context context = getContext();
        if (context != null && (viewModel = EditorActivityViewModel.Companion.getViewModel(context)) != null) {
            viewModel.setApplyTemplateConfig(selectedItemConfig);
        }
        if (!DeviceUtil.INSTANCE.isInternationalBuild()) {
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type androidx.lifecycle.LifecycleOwner");
            EditorActivity.NavigationActionListener.DefaultImpls.onApply$default(this, null, activity, 1, null);
            return;
        }
        FashionGalleryCompat fashionGalleryCompat = this.fashionGalleryCompat;
        if (fashionGalleryCompat == null || !fashionGalleryCompat.shouldContinue()) {
            FragmentActivity activity2 = getActivity();
            Intrinsics.checkNotNull(activity2, "null cannot be cast to non-null type androidx.lifecycle.LifecycleOwner");
            EditorActivity.NavigationActionListener.DefaultImpls.onApply$default(this, null, activity2, 1, null);
        }
    }

    public final void completeExitIfCancelOrBackPressed(final FragmentActivity fragmentActivity) {
        if (fragmentActivity instanceof EditorActivity) {
            CrossViewPager crossViewPager = this.mCrossViewPager;
            if (crossViewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager = null;
            }
            crossViewPager.post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MainFragment.completeExitIfCancelOrBackPressed$lambda$27(this.f$0, fragmentActivity);
                }
            });
        }
    }

    public final void ensureDataModel() {
        if (this.mDataModel == null) {
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            this.mDataModel = new CrossListDataModel(contextRequireContext, EditorApplicationProxy.Companion.getCrossListPreloader());
        }
    }

    public final void fillViewPagerData() {
        Intent intent;
        CrossListDataModel crossListDataModel = this.mDataModel;
        CrossViewPager crossViewPager = null;
        List<FloorItemBean> dataList = crossListDataModel != null ? crossListDataModel.getDataList() : null;
        Log.i("Keyguard-Editor-MainFragment", "fillViewPagerData: dataSize = " + (dataList != null ? Integer.valueOf(dataList.size()) : null));
        if (dataList != null) {
            CrossViewPager crossViewPager2 = this.mCrossViewPager;
            if (crossViewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager2 = null;
            }
            crossViewPager2.setData(dataList);
        } else {
            CrossViewPager crossViewPager3 = this.mCrossViewPager;
            if (crossViewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager3 = null;
            }
            crossViewPager3.setData(CollectionsKt.emptyList());
        }
        FragmentActivity activity = getActivity();
        int intExtra = (activity == null || (intent = activity.getIntent()) == null) ? 1 : intent.getIntExtra("cross_position", 1);
        if ((getActivity() instanceof EditorActivityForSettings) && !Intrinsics.areEqual(this.mySets, "navigate_to_my_sets") && intExtra >= 0) {
            if (intExtra <= (dataList != null ? dataList.size() : 0)) {
                CrossViewPager crossViewPager4 = this.mCrossViewPager;
                if (crossViewPager4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                } else {
                    crossViewPager = crossViewPager4;
                }
                crossViewPager.getViewPager2().setCurrentItem(intExtra, false);
            }
        }
        if (this.needReloadData) {
            refreshWithDefaultData(this.mSelectedPosition.y);
        }
        this.isFillData = true;
    }

    public final TransformerInfo generateTransformerInfoForSelected(Bitmap bitmap) {
        TransformerController transformerController;
        TransformerInfo transformerInfoGenerateTransformerInfo;
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        Point selectedPosition = crossViewPager.getSelectedPosition();
        int i = selectedPosition != null ? selectedPosition.x : -1;
        int i2 = selectedPosition != null ? selectedPosition.y : -1;
        if (i == -1 || i2 == -1 || (transformerController = this.mTransformerController) == null || (transformerInfoGenerateTransformerInfo = transformerController.generateTransformerInfo(i, i2)) == null) {
            return null;
        }
        TransformerPositionInfo currentPositionInfo = transformerInfoGenerateTransformerInfo.getCurrentPositionInfo();
        if (currentPositionInfo != null) {
            currentPositionInfo.setPresetWallpaper(bitmap);
        }
        return transformerInfoGenerateTransformerInfo;
    }

    public final TitleDescController getCurrentFloorTitleDescController() {
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        FloorViewHolder floorViewHolder = crossViewPager.getFloorViewHolder(this.mSelectedPosition.y);
        if (floorViewHolder != null) {
            return floorViewHolder.getTitleController();
        }
        return null;
    }

    public final long getStartCancelTime() {
        return this.startCancelTime;
    }

    public final long getStartPickerTime() {
        return this.startPickerTime;
    }

    public final int[] getTrackShowTag() {
        return this.trackShowTag;
    }

    public final CrossViewPager getViewPaper() {
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager != null) {
            return crossViewPager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
        return null;
    }

    public final void initControllers(View view) {
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        CrossViewPager crossViewPager = this.mCrossViewPager;
        CrossViewPager crossViewPager2 = null;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        TemplateApplyController templateApplyController = new TemplateApplyController(fragmentActivityRequireActivity, crossViewPager);
        templateApplyController.setFloorTrackShowTag(this.trackShowTag);
        this.mTemplateApplyController = templateApplyController;
        Intrinsics.checkNotNull(templateApplyController);
        templateApplyController.setApplyCallback(new ApplyCallback() { // from class: com.miui.keyguard.editor.homepage.MainFragment.initControllers.2
            @Override // com.miui.keyguard.editor.homepage.util.ApplyCallback
            public void onApplyCanceled() {
                Log.w("Keyguard-Editor-MainFragment", "onApplyCanceled: main fragment");
                MainFragment.this.mIsApply = false;
                KeyEventDispatcher.Component componentRequireActivity = MainFragment.this.requireActivity();
                Intrinsics.checkNotNullExpressionValue(componentRequireActivity, "requireActivity(...)");
                if (componentRequireActivity instanceof LockScreenTransformerController) {
                    ((LockScreenTransformerController) componentRequireActivity).onDisMissApplyLoading();
                }
                MainFragment.this.setInterceptAllEventsOnApply(false);
            }

            @Override // com.miui.keyguard.editor.homepage.util.ApplyCallback
            public void onApplyComplete(boolean z, Bitmap bitmap) {
                Log.d("Keyguard-Editor-MainFragment", "onApplyComplete: main fragment");
                MainFragment.this.onApplyCompleted(z, bitmap);
            }
        });
        TemplateApplyController templateApplyController2 = this.mTemplateApplyController;
        Intrinsics.checkNotNull(templateApplyController2);
        templateApplyController2.setApplyMyTemplateCallback(new androidx.core.util.Consumer() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda2
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                MainFragment.initControllers$lambda$3(this.f$0, (Pair) obj);
            }
        });
        TemplateApplyController templateApplyController3 = this.mTemplateApplyController;
        Intrinsics.checkNotNull(templateApplyController3);
        templateApplyController3.initScreenshotContainers(view);
        CrossListDataModel crossListDataModel = this.mDataModel;
        CrossViewPager crossViewPager3 = this.mCrossViewPager;
        if (crossViewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
        } else {
            crossViewPager2 = crossViewPager3;
        }
        this.mTransformerController = new TransformerController(fragmentActivityRequireActivity, crossListDataModel, crossViewPager2);
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment
    public OnBackPressedCallback instanceOnBackPressedCallback(FragmentActivity fragmentActivity) {
        Intrinsics.checkNotNullParameter(fragmentActivity, "fragmentActivity");
        return new MainBackPressBack(fragmentActivity, this, this.trackShowTag);
    }

    public final void interceptItemLongClick(boolean z) {
        Log.i("Keyguard-Editor-MainFragment", "interceptItemLongClick:" + z);
        CrossViewPager crossViewPager = this.mCrossViewPager;
        CrossViewPager crossViewPager2 = null;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        Point point = this.mSelectedPosition;
        RecyclerView.ViewHolder viewHolder = crossViewPager.getViewHolder(point.x, point.y);
        if (viewHolder instanceof BaseItemViewHolder) {
            ((BaseItemViewHolder) viewHolder).interceptLongClick(z);
        }
        if (z) {
            CrossViewPager crossViewPager3 = this.mCrossViewPager;
            if (crossViewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager3 = null;
            }
            crossViewPager3.removeCallbacks(this.mCancelLongClickInterceptRunnable);
            CrossViewPager crossViewPager4 = this.mCrossViewPager;
            if (crossViewPager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            } else {
                crossViewPager2 = crossViewPager4;
            }
            crossViewPager2.postDelayed(this.mCancelLongClickInterceptRunnable, 1500L);
        }
    }

    public final boolean isForwardTransition() {
        if (!(requireContext() instanceof TransitionLayer)) {
            return false;
        }
        Object objRequireContext = requireContext();
        Intrinsics.checkNotNull(objRequireContext, "null cannot be cast to non-null type com.miui.keyguard.editor.base.TransitionLayer");
        TransitionPerformer transitionPerformer = ((TransitionLayer) objRequireContext).getTransitionPerformer();
        Intrinsics.checkNotNull(transitionPerformer, "null cannot be cast to non-null type com.miui.keyguard.editor.base.TransitionPerformerImpl");
        return ((TransitionPerformerImpl) transitionPerformer).isForwardTransition();
    }

    public final void loadAllData() {
        Context context = getContext();
        if (context != null) {
            BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new MainFragment$loadAllData$1$1(this, context, null), 3, null);
        }
    }

    public final boolean myTemplateViewHiding() {
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        Point selectedPosition = crossViewPager.getSelectedPosition();
        return selectedPosition == null || selectedPosition.y != 0 || selectedPosition.x > 2;
    }

    public final void navigateToEditFragment(int i, int i2, String str, long j, boolean z, boolean z2, int i3) {
        this.inEnterEditFragment = true;
        setViewPageUserInputEnabled(false);
        performTransitionAnimation(i, i2);
        isEnterEditor = true;
        EditFragment editFragment = new EditFragment();
        editFragment.comeFromCrossTower(true);
        Pair pair = TuplesKt.to("argConfigPath", str);
        Pair pair2 = TuplesKt.to("argTemplateSource", Long.valueOf(j));
        Pair pair3 = TuplesKt.to("argThirdPartyWallpaper", Boolean.valueOf(z));
        CrossListDataModel crossListDataModel = this.mDataModel;
        navigateTo(editFragment, "Keyguard-Editor-EditFragment", BundleKt.bundleOf(pair, pair2, pair3, TuplesKt.to("argClockExtras", crossListDataModel != null ? crossListDataModel.getClockExtras() : null), TuplesKt.to("argAutoDownload", Boolean.valueOf(z2)), TuplesKt.to("hideFlag", Integer.valueOf(i3))));
    }

    public final void notifyTransformerRefresh(List list) {
        if (this.isDataListRefreshed) {
            Log.w("Keyguard-Editor-MainFragment", "notifyTransformRefresh failed: isDataListRefreshed = true");
            return;
        }
        this.isDataListRefreshed = true;
        TransformerController transformerController = this.mTransformerController;
        if (transformerController != null) {
            transformerController.onDataLoadCompleted(list, false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 117) {
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type androidx.lifecycle.LifecycleOwner");
            EditorActivity.NavigationActionListener.DefaultImpls.onApply$default(this, null, activity, 1, null);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onAddTemplate() {
        CrossViewPagerCallback.DefaultImpls.onAddTemplate(this);
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public boolean onApply(Consumer consumer, LifecycleOwner lifecycleOwner) {
        if (this.mScrolled) {
            Log.w("Keyguard-Editor-MainFragment", "onApply failed: mScrolled == true.");
            return true;
        }
        if (this.hasCurrentTemplate && !DeviceScreenshotHelper.INSTANCE.isFirstScreenshotFinished()) {
            Log.w("Keyguard-Editor-MainFragment", "onApply failed: isFirstScreenshotFinished == false.");
            return true;
        }
        if (SettingsTemplatesProviderManager.INSTANCE.isApplying()) {
            Log.w("Keyguard-Editor-MainFragment", "onApply failed: theme is applying template.");
            return true;
        }
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null && TemplateApplyController.applyTemplate$default(templateApplyController, false, 1, null) == 0) {
            Log.i("Keyguard-Editor-MainFragment", "onApply: all events should be intercepted because apply will be started.");
            setInterceptAllEventsOnApply(true);
            this.mIsApply = true;
        }
        return true;
    }

    public final void onApplyCompleted(final boolean z, final Bitmap bitmap) {
        if (isAdded()) {
            final FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            Log.d("Keyguard-Editor-MainFragment", "onApplyCompleted: call editor activity");
            CrossViewPager crossViewPager = this.mCrossViewPager;
            if (crossViewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager = null;
            }
            crossViewPager.post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MainFragment.onApplyCompleted$lambda$21(this.f$0, bitmap, fragmentActivityRequireActivity, z);
                }
            });
        }
    }

    public final void onBackPressedDispatched() {
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.onBackPressed();
        }
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public boolean onCancel(Consumer consumer) {
        boolean zIsForwardTransition = isForwardTransition();
        if (zIsForwardTransition) {
            Log.w("Keyguard-Editor-MainFragment", "onCancel failed: isForwardTransition = " + zIsForwardTransition + ".");
            return false;
        }
        this.startCancelTime = ScenarioRecognitionUtils.INSTANCE.getCurrentTime();
        this.mIsCancel = true;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        completeExitIfCancelOrBackPressed(fragmentActivityRequireActivity);
        TrackHelper.trackQuitHomePage$default(TrackHelper.INSTANCE, fragmentActivityRequireActivity, "其他", null, null, this.trackShowTag, isEnterEditor, false, 64, null);
        return true;
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intent intent;
        Intent intent2;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentActivity activity = getActivity();
        this.mySets = (activity == null || (intent2 = activity.getIntent()) == null) ? null : intent2.getStringExtra("is_navigate_to_my_sets");
        FragmentActivity activity2 = getActivity();
        this.myCustomize = (activity2 == null || (intent = activity2.getIntent()) == null) ? null : intent.getStringExtra("is_my_customize");
        Log.i("Keyguard-Editor-MainFragment", "onCreateView container=" + viewGroup);
        if (viewGroup == null) {
            return super.onCreateView(inflater, viewGroup, bundle);
        }
        Context context = viewGroup.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        View viewCreate$default = DynamicViewFactory.create$default(new MainFragmentDynamicViewFactoryImpl(context), null, 1, null);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isPad() && !deviceUtil.isFold()) {
            return viewCreate$default;
        }
        this.containerForScreenshot = viewCreate$default instanceof FrameLayout ? (FrameLayout) viewCreate$default : null;
        return viewCreate$default;
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onCurrentTemplateScreenshot(BaseTemplateView lockTemplateView, BaseTemplateView baseTemplateView, CommonConfig templateConfig) {
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Log.i("Keyguard-Editor-MainFragment", "onCurrentTemplateScreenshot -> shouldCaptureCurrentTemplate = " + this.shouldCaptureCurrentTemplate);
        this.myTemplateView = new WeakReference(lockTemplateView);
        if (this.shouldCaptureCurrentTemplate) {
            TemplateApplyController templateApplyController = this.mTemplateApplyController;
            if (templateApplyController != null) {
                templateApplyController.setCurrentTemplateScreenshotsSaving(true);
            }
            DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
            if (deviceScreenshotHelper.isFirstScreenshotFinished()) {
                this.shouldCaptureCurrentTemplate = false;
            }
            if (checkFragmentInactive()) {
                Log.w("Keyguard-Editor-MainFragment", "takeCaptureForMultiScreenMode failed: fragment not attached to a context.");
                TemplateApplyController templateApplyController2 = this.mTemplateApplyController;
                if (templateApplyController2 != null) {
                    templateApplyController2.setCurrentTemplateScreenshotsSaving(false);
                    return;
                }
                return;
            }
            Context context = getContext();
            if (context != null) {
                deviceScreenshotHelper.screenshot(context, this.containerForScreenshot, lockTemplateView, baseTemplateView, templateConfig, (32 & 32) != 0 ? "wallpaper_for_screenshot" : null, new Function0() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return MainFragment.onCurrentTemplateScreenshot$lambda$20$lambda$18(this.f$0);
                    }
                }, (32 & 128) != 0 ? null : new Function1() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda7
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return MainFragment.onCurrentTemplateScreenshot$lambda$20$lambda$19(this.f$0, (Throwable) obj);
                    }
                });
            }
        }
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        CrossViewPager.offscreenPageLimit$default(crossViewPager, 1, false, 2, null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.myTemplateView = null;
        DeviceScreenshotHelper.INSTANCE.setFirstScreenshotFinished(false);
        BaseItemViewFactoryImpl.Companion.clearCache();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        FashionGalleryHelper.INSTANCE.resetFashionGalleryOpened();
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        crossViewPager.removeCallbacks(this.mCancelLongClickInterceptRunnable);
        this.mDataModel = null;
        CoroutineScopeKt.cancel$default(LifecycleOwnerKt.getLifecycleScope(this), null, 1, null);
        super.onDestroyView();
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHistoryTemplateReady() {
        CrossViewPagerCallback.DefaultImpls.onHistoryTemplateReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHomeWallpaperDarkResultReady() {
        CrossViewPagerCallback.DefaultImpls.onHomeWallpaperDarkResultReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyHomeTemplateCreated(BaseTemplateView baseTemplateView) {
        CrossViewPagerCallback.DefaultImpls.onMyHomeTemplateCreated(this, baseTemplateView);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyLockTemplateCreated(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        this.myTemplateView = new WeakReference(baseTemplateView);
        Function0 function0 = this.myTemplateUpdateCallback;
        if (function0 != null) {
            if (function0 != null) {
                function0.invoke();
            }
            this.myTemplateUpdateCallback = null;
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageDeleteCompleted(int i, int i2, TemplateItemBean templateItemBean) {
        CrossViewPagerCallback.DefaultImpls.onPageDeleteCompleted(this, i, i2, templateItemBean);
        CrossListDataModel crossListDataModel = this.mDataModel;
        if (crossListDataModel != null) {
            crossListDataModel.deleteHistoryTemplate(templateItemBean);
        }
        Context context = getContext();
        if (context != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            CrossListDataModel crossListDataModel2 = this.mDataModel;
            trackHelper.trackDeleteHistoryTemplate(context, templateItemBean, crossListDataModel2 != null ? crossListDataModel2.requireHistoryTemplateCount() : 0);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public boolean onPageDeleted(int i, int i2, TemplateItemBean templateItemBean) {
        if (templateItemBean == null || templateItemBean.getItemType() != 2) {
            return false;
        }
        try {
            TemplateHistoryConfig history = templateItemBean.getHistory();
            if (history == null) {
                return false;
            }
            TemplateApi.Companion companion = TemplateApi.Companion;
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            return TemplateApi.DefaultImpls.deleteHistoryTemplate$default(companion.getInstance(contextRequireContext), history, false, false, 6, null);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-MainFragment", "onPageDeleted error: " + e);
            return false;
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageEnterDeleteMode(int i, int i2, View view, boolean z) {
        CrossViewPagerCallback.DefaultImpls.onPageEnterDeleteMode(this, i, i2, view, z);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageScrollStateChanged(int i, int i2, int i3, int i4) {
        CrossViewPagerCallback.DefaultImpls.onPageScrollStateChanged(this, i, i2, i3, i4);
        Log.i("Keyguard-Editor-MainFragment", "onPageScrollStateChanged -> orientation = " + i + ", x = " + i2 + ", y = " + i3 + ", state = " + i4);
        this.mScrolled = i4 != 0;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x006c  */
    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageSelected(int i, int i2, int i3, int i4) {
        Context context;
        boolean z;
        int[] iArr;
        CrossViewPagerCallback.DefaultImpls.onPageSelected(this, i, i2, i3, i4);
        Log.i("Keyguard-Editor-MainFragment", "onPageSelected -> orientation = " + i + ", x = " + i2 + ", y = " + i3);
        this.mSelectedPosition.set(i2, i3);
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setHasPendingApplyTemplateTask(false);
        }
        if (i == 1 && i3 > 0 && (iArr = this.trackShowTag) != null) {
            iArr[i3] = 1;
        }
        Object context2 = getContext();
        CrossViewPager crossViewPager = null;
        NavigationController navigationController = context2 instanceof NavigationController ? (NavigationController) context2 : null;
        if (navigationController != null) {
            if (i2 == 0) {
                z = false;
            } else {
                CrossViewPager crossViewPager2 = this.mCrossViewPager;
                if (crossViewPager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                    crossViewPager2 = null;
                }
                TemplateItemBean selectedItem = crossViewPager2.getSelectedItem();
                if (selectedItem == null || selectedItem.getItemType() != 4) {
                    z = true;
                } else {
                    z = false;
                }
            }
            navigationController.setApplyButtonEnabled(z);
        }
        if (i2 == 0 && (context = getContext()) != null) {
            TrackHelper.INSTANCE.negativeCardExposure(context);
        }
        BaseItemViewHolder baseItemViewHolder = this.lastSelectedHolder;
        if (baseItemViewHolder != null && baseItemViewHolder != null) {
            baseItemViewHolder.setBaseViewHolderAccess(false);
        }
        CrossViewPager crossViewPager3 = this.mCrossViewPager;
        if (crossViewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager3 = null;
        }
        RecyclerView.ViewHolder viewHolder = crossViewPager3.getViewHolder(i2, i3);
        if (viewHolder instanceof BaseItemViewHolder) {
            BaseItemViewHolder baseItemViewHolder2 = (BaseItemViewHolder) viewHolder;
            baseItemViewHolder2.setBaseViewHolderAccess(true);
            if (!this.isFirstEnter) {
                baseItemViewHolder2.requestFocus();
            }
            if (i4 == 0) {
                CrossViewPager crossViewPager4 = this.mCrossViewPager;
                if (crossViewPager4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                } else {
                    crossViewPager = crossViewPager4;
                }
                if (crossViewPager.getFirstSelect()) {
                    baseItemViewHolder2.setSelectedUI(true);
                }
            }
            this.lastSelectedHolder = baseItemViewHolder2;
        }
        this.isFirstEnter = false;
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageTransformed(int i, BaseViewHolder pageHolder, View page, float f, float f2, int i2) {
        Intrinsics.checkNotNullParameter(pageHolder, "pageHolder");
        Intrinsics.checkNotNullParameter(page, "page");
    }

    @Override // com.miui.keyguard.editor.homepage.util.OnVerticalScrollAnimationCallback
    public void onPageZIndexChanged(float f) {
        if (f == 0.0f) {
            updateButtonLayoutZInActivity(0.0f);
        } else {
            updateButtonLayoutZInActivity(2.0f);
        }
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        updateMyTemplateViewForGallery();
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment
    public void onScreenConfigurationChanged(boolean z, boolean z2) {
        super.onScreenConfigurationChanged(z, z2);
        Log.d("Keyguard-Editor-MainFragment", "onScreenOrientationOrSizeChanged");
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.processScreenConfigurationChanged();
        }
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager != null) {
            if (crossViewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager = null;
            }
            crossViewPager.processScreenConfigurationChanged();
        }
        CrossListDataModel crossListDataModel = this.mDataModel;
        if (crossListDataModel != null) {
            crossListDataModel.processScreenConfigurationChanged();
        }
        this.mScrolled = false;
        Point point = this.mSelectedPosition;
        int i = point.x;
        int i2 = point.y;
        Log.i("Keyguard-Editor-MainFragment", "onScreenConfigurationChanged -> lastSelectedPosition: x = " + i + ", y = " + i2);
        BaseItemViewFactoryImpl.Companion.clearCache();
        if (this.isFillData) {
            refreshWithDefaultData(i2);
            this.needReloadData = false;
        } else {
            refreshWithDefaultDataWhenUnFilled();
            this.needReloadData = true;
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onTemplateCustomized(int i, int i2, TemplateItemBean templateItemBean, int i3) {
        FragmentActivity activity;
        TemplateConfig lockscreenInfo;
        CommonConfig current;
        TemplateConfig lockscreenInfo2;
        CrossViewPagerCallback.DefaultImpls.onTemplateCustomized(this, i, i2, templateItemBean, i3);
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        crossViewPager.logSelectedItemState("onTemplateCustomized", i, i2);
        int hideFlag = 0;
        if (templateItemBean != null && templateItemBean.isAutoDownload()) {
            Log.w("Keyguard-Editor-MainFragment", "onTemplateCustomized is auto download");
            this.mIsApply = false;
        }
        boolean z = this.mScrolled;
        if (z || this.mIsCancel || this.mIsApply || this.inEnterEditFragment) {
            Log.w("Keyguard-Editor-MainFragment", "onTemplateCustomized ignored: mScrolled = " + z + ", mIsCancel = " + this.mIsCancel + ", mIsApply = " + this.mIsApply);
            return;
        }
        Point point = this.mSelectedPosition;
        int i4 = point.x;
        if (i != i4 || i2 != point.y) {
            Log.w("Keyguard-Editor-MainFragment", "onTemplateCustomized ignored: position not matched. x = " + i + ", y = " + i2 + ", sx = " + i4 + ", sy: " + point.y);
            return;
        }
        Integer numValueOf = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
        if (numValueOf != null && numValueOf.intValue() == 1) {
            boolean zIsThirdPartyWallpaper = (templateItemBean == null || (current = templateItemBean.getCurrent()) == null || (lockscreenInfo2 = current.getLockscreenInfo()) == null) ? false : lockscreenInfo2.isThirdPartyWallpaper();
            CommonConfig current2 = templateItemBean.getCurrent();
            if (current2 != null && (lockscreenInfo = current2.getLockscreenInfo()) != null) {
                hideFlag = lockscreenInfo.getHideFlag();
            }
            navigateToEditFragment$default(this, i, i2, "@MINE", -1L, zIsThirdPartyWallpaper, false, hideFlag, 32, null);
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == 2) {
            processCustomHistoryTemplate(templateItemBean.getHistory(), i, i2);
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == 3) {
            processCustomPresetTemplate(templateItemBean, i, i2);
            return;
        }
        if (numValueOf == null || numValueOf.intValue() != -1 || (activity = getActivity()) == null) {
            return;
        }
        if (activity instanceof EditorActivity) {
            EditorActivity editorActivity = (EditorActivity) activity;
            editorActivity.setPickWallpaperSetButtonDisbable(true);
            editorActivity.setButtonClickable(false);
        }
        this.startPickerTime = ScenarioRecognitionUtils.INSTANCE.getCurrentTime();
        WallpaperEditor.Companion.launchThemePicker(activity);
        TrackHelper.INSTANCE.negativeCardClick(activity);
    }

    @Override // com.miui.keyguard.editor.homepage.util.OnVerticalScrollAnimationCallback
    public void onVerticalScrollStateChanged(int i) {
        if (i == 1) {
            setTitleBarBlurMode(true);
        } else {
            setTitleBarBlurMode(false);
        }
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        CrossViewPager crossViewPager = (CrossViewPager) view.findViewById(R.id.kg_viewpager_cross);
        this.mCrossViewPager = crossViewPager;
        CrossViewPager crossViewPager2 = null;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        crossViewPager.setViewPagerCallback(this);
        CrossViewPager crossViewPager3 = this.mCrossViewPager;
        if (crossViewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
        } else {
            crossViewPager2 = crossViewPager3;
        }
        crossViewPager2.setVerticalScrollAnimationCallback(this);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        CrossListDataModel crossListDataModel = new CrossListDataModel(contextRequireContext, EditorApplicationProxy.Companion.getCrossListPreloader());
        this.mDataModel = crossListDataModel;
        crossListDataModel.setClockExtras(getArguments());
        initControllers(view);
        preloadResources();
        loadAllData();
        initFashionGalleryCompat();
        isEnterCrossTower = true;
    }

    public final void performTransitionAnimation(int i, int i2) {
        CrossViewPager crossViewPager = this.mCrossViewPager;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        Object viewHolder = crossViewPager.getViewHolder(i, i2);
        if (viewHolder != null && (viewHolder instanceof PreviewHolder) && (requireContext() instanceof TransitionLayer)) {
            FragmentActivity activity = getActivity();
            EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
            if (editorActivity != null) {
                editorActivity.lockOrientationForTransition();
            }
            Object objRequireContext = requireContext();
            Intrinsics.checkNotNull(objRequireContext, "null cannot be cast to non-null type com.miui.keyguard.editor.base.TransitionLayer");
            ((TransitionLayer) objRequireContext).getTransitionPerformer().prepareForwardTransition((PreviewHolder) viewHolder, new TransitionHost(this, i, i2));
        }
    }

    public final void preloadResources() {
        Context context = getContext();
        if (context != null) {
            BuildersKt__Builders_commonKt.async$default(EditorActivityViewModel.Companion.getCoroutineScope(context), Dispatchers.getIO(), null, new MainFragment$preloadResources$1$1(context, null), 2, null);
        }
    }

    public final void processCustomHistoryTemplate(TemplateHistoryConfig templateHistoryConfig, int i, int i2) {
        String configUri;
        Context applicationContext = requireContext().getApplicationContext();
        long id = templateHistoryConfig != null ? templateHistoryConfig.getId() : 0L;
        if (templateHistoryConfig == null || (configUri = templateHistoryConfig.getConfigUri()) == null) {
            configUri = "";
        }
        String str = configUri;
        boolean zIsThirdPartyWallpaper = templateHistoryConfig != null ? templateHistoryConfig.isThirdPartyWallpaper() : false;
        boolean zIsDualClock = templateHistoryConfig != null ? templateHistoryConfig.isDualClock() : false;
        Intrinsics.checkNotNull(applicationContext);
        boolean zIsDualClockAvailable = DualClockManager.isDualClockAvailable(applicationContext);
        int hideFlag = templateHistoryConfig != null ? templateHistoryConfig.getHideFlag() : 0;
        if (zIsDualClock && !zIsDualClockAvailable) {
            ToastUtil.INSTANCE.showText(applicationContext, R.string.kg_main_toast_recover_default_style);
        }
        if (!zIsDualClockAvailable || zIsDualClock) {
            navigateToEditFragment$default(this, i, i2, str, id, zIsThirdPartyWallpaper, false, hideFlag, 32, null);
        } else {
            showDualClockDialog$default(this, applicationContext, i, i2, str, id, zIsThirdPartyWallpaper, false, hideFlag, 64, null);
        }
    }

    public final void processCustomPresetTemplate(TemplateItemBean templateItemBean, int i, int i2) {
        String configUri;
        PresetTemplateConfig preset;
        PresetTemplateConfig preset2;
        Context applicationContext = requireContext().getApplicationContext();
        if (templateItemBean == null || (preset2 = templateItemBean.getPreset()) == null || (configUri = preset2.getConfigUri()) == null) {
            configUri = "";
        }
        String str = configUri;
        boolean z = false;
        int hideFlag = (templateItemBean == null || (preset = templateItemBean.getPreset()) == null) ? 0 : preset.getHideFlag();
        Intrinsics.checkNotNull(applicationContext);
        if (DualClockManager.isDualClockAvailable(applicationContext)) {
            showDualClockDialog$default(this, applicationContext, i, i2, str, -2L, false, templateItemBean != null && templateItemBean.isAutoDownload(), hideFlag, 32, null);
            return;
        }
        if (templateItemBean != null && templateItemBean.isAutoDownload()) {
            z = true;
        }
        navigateToEditFragment$default(this, i, i2, str, -2L, false, z, hideFlag, 16, null);
    }

    public final void reFillViewPagerData() {
        CrossListDataModel crossListDataModel = this.mDataModel;
        CrossViewPager crossViewPager = null;
        List dataList = crossListDataModel != null ? crossListDataModel.getDataList() : null;
        Log.i("Keyguard-Editor-MainFragment", "fillViewPagerData: dataSize = " + (dataList != null ? Integer.valueOf(dataList.size()) : null));
        if (dataList != null) {
            CrossViewPager crossViewPager2 = this.mCrossViewPager;
            if (crossViewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager2 = null;
            }
            crossViewPager2.resetAdapter(dataList);
            CrossViewPager crossViewPager3 = this.mCrossViewPager;
            if (crossViewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager3 = null;
            }
            CrossViewPager.offscreenPageLimit$default(crossViewPager3, 0, true, 1, null);
        } else {
            CrossViewPager crossViewPager4 = this.mCrossViewPager;
            if (crossViewPager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                crossViewPager4 = null;
            }
            crossViewPager4.setData(CollectionsKt.emptyList());
        }
        Point point = this.mSelectedPosition;
        int size = dataList != null ? dataList.size() : 0;
        int i = point.y;
        if (i < 0 || i > size) {
            return;
        }
        CrossViewPager crossViewPager5 = this.mCrossViewPager;
        if (crossViewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
        } else {
            crossViewPager = crossViewPager5;
        }
        crossViewPager.getViewPager2().setCurrentItem(point.y, false);
    }

    public final void refreshWithDefaultData(int i) {
        CrossListDataModel crossListDataModel = this.mDataModel;
        CrossViewPager crossViewPager = null;
        final List dataList = crossListDataModel != null ? crossListDataModel.getDataList() : null;
        if (dataList != null) {
            if (!dataList.isEmpty()) {
                CrossListDataModel crossListDataModel2 = this.mDataModel;
                if (crossListDataModel2 != null) {
                    FragmentActivity activity = getActivity();
                    EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
                    crossListDataModel2.refreshDefaultDataListSync$keyguardeditor_release(dataList, editorActivity != null && editorActivity.callingFromSystemUI());
                }
                this.myTemplateView = null;
                this.myTemplateUpdateCallback = null;
                CrossViewPager crossViewPager2 = this.mCrossViewPager;
                if (crossViewPager2 != null) {
                    if (crossViewPager2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                        crossViewPager2 = null;
                    }
                    crossViewPager2.resetAdapter(dataList);
                    CrossViewPager crossViewPager3 = this.mCrossViewPager;
                    if (crossViewPager3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                        crossViewPager3 = null;
                    }
                    crossViewPager3.resetScrollEventTarget();
                    CrossViewPager crossViewPager4 = this.mCrossViewPager;
                    if (crossViewPager4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
                    } else {
                        crossViewPager = crossViewPager4;
                    }
                    crossViewPager.selectTargetFloor(i);
                }
                Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda12
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return MainFragment.refreshWithDefaultData$lambda$28(this.f$0, dataList);
                    }
                }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        MainFragment.refreshWithDefaultData$lambda$32(this.f$0, (List) obj);
                    }
                });
            }
        }
    }

    public final void refreshWithDefaultDataWhenUnFilled() {
        CrossListDataModel crossListDataModel = this.mDataModel;
        final List dataList = crossListDataModel != null ? crossListDataModel.getDataList() : null;
        if (dataList != null) {
            if (!dataList.isEmpty()) {
                CrossListDataModel crossListDataModel2 = this.mDataModel;
                if (crossListDataModel2 != null) {
                    FragmentActivity activity = getActivity();
                    EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
                    crossListDataModel2.refreshDefaultDataListSync$keyguardeditor_release(dataList, editorActivity != null && editorActivity.callingFromSystemUI());
                }
                Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda10
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return MainFragment.refreshWithDefaultDataWhenUnFilled$lambda$33(this.f$0, dataList);
                    }
                }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.resetLockTransLayerPositionInfo();
                    }
                });
            }
        }
    }

    public final void reloadAllData() {
        Context context;
        CrossListDataModel crossListDataModel = this.mDataModel;
        List dataList = crossListDataModel != null ? crossListDataModel.getDataList() : null;
        if (dataList == null || !(!dataList.isEmpty()) || (context = getContext()) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new MainFragment$reloadAllData$1$1(this, context, null), 3, null);
    }

    public final void resetApplyingFlag(String source) {
        Intrinsics.checkNotNullParameter(source, "source");
        Log.i("Keyguard-Editor-MainFragment", "resetApplyingFlag: source = " + source);
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            TemplateApplyController.setApplying$default(templateApplyController, false, false, 2, null);
        }
    }

    public final void resetLockTransLayerPositionInfo() {
        CommonConfig currentTemplateConfig;
        TransformerPositionInfo entranceCurrentPositionInfo;
        CrossListDataModel crossListDataModel = this.mDataModel;
        if (crossListDataModel == null || (currentTemplateConfig = crossListDataModel.getCurrentTemplateConfig()) == null) {
            Log.i("Keyguard-Editor-MainFragment", "reset lockTransLayerCurPositionInfo fail because cur config is null");
            return;
        }
        FragmentActivity activity = getActivity();
        EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
        if (editorActivity == null) {
            Log.i("Keyguard-Editor-MainFragment", "reset lockTransLayerCurPositionInfo fail because activity null");
        } else {
            LockScreenTransformerLayer lockScreenTransformerLayer = editorActivity.getLockScreenTransformerLayer();
            editorActivity.resetLockTransLayerCurPositionInfo((lockScreenTransformerLayer == null || (entranceCurrentPositionInfo = lockScreenTransformerLayer.getEntranceCurrentPositionInfo()) == null || entranceCurrentPositionInfo.getItemType() != 4) ? new TransformerPositionInfo(1, null, null, null, null, currentTemplateConfig, 30, null) : new TransformerPositionInfo(4, null, null, null, null, currentTemplateConfig, 30, null));
        }
    }

    public final void resetStateWhenPinned() {
        this.mIsApply = false;
        this.mIsCancel = false;
    }

    public final void setInEnterEditFragment(boolean z) {
        this.inEnterEditFragment = z;
    }

    public final void setShouldCaptureCurrentTemplate(boolean z) {
        this.shouldCaptureCurrentTemplate = z;
    }

    public final void setStartCancelTime(long j) {
        this.startCancelTime = j;
    }

    public final void setStartPickerTime(long j) {
        this.startPickerTime = j;
    }

    public final void setTitleBarBlurMode(boolean z) {
        try {
            if (checkFragmentInactive()) {
                Log.e("Keyguard-Editor-MainFragment", "setTitleBarBlurMode ignored: fragment is not attached or detached.");
                return;
            }
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            if (fragmentActivityRequireActivity instanceof EditorActivity) {
                ((EditorActivity) fragmentActivityRequireActivity).setTitleBarBlurEnabled(z);
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-MainFragment", "setTitleBarBlurMode", e);
        }
    }

    public final void setTrackShowTag(int[] iArr) {
        this.trackShowTag = iArr;
    }

    public final void setViewPageUserInputEnabled(boolean z) {
        CrossViewPager crossViewPager = this.mCrossViewPager;
        CrossViewPager crossViewPager2 = null;
        if (crossViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager = null;
        }
        crossViewPager.getViewPager2().setUserInputEnabled(z);
        CrossViewPager crossViewPager3 = this.mCrossViewPager;
        if (crossViewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
            crossViewPager3 = null;
        }
        int currentItem = crossViewPager3.getViewPager2().getCurrentItem();
        CrossViewPager crossViewPager4 = this.mCrossViewPager;
        if (crossViewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrossViewPager");
        } else {
            crossViewPager2 = crossViewPager4;
        }
        FloorViewHolder floorViewHolder = crossViewPager2.getFloorViewHolder(currentItem);
        if (floorViewHolder != null) {
            View targetContentView = floorViewHolder.getTargetContentView();
            if (targetContentView instanceof DampViewPager2) {
                ((DampViewPager2) targetContentView).setUserInputEnabled(z);
            }
        }
    }

    public final void showDualClockDialog(final Context context, final int i, final int i2, final String str, final long j, final boolean z, final boolean z2, final int i3) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.MainFragment$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i4) {
                MainFragment.showDualClockDialog$lambda$14(this.f$0, i, i2, str, j, z, z2, i3, context, dialogInterface, i4);
            }
        };
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.showDualClockCloseDialog(onClickListener, onClickListener, false);
        }
    }

    public final void updateButtonLayoutZInActivity(float f) {
        try {
            if (isAdded() && !isDetached()) {
                FragmentActivity fragmentActivityRequireActivity = requireActivity();
                Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                if (fragmentActivityRequireActivity instanceof EditorActivity) {
                    ((EditorActivity) fragmentActivityRequireActivity).updateButtonLayoutZ(f);
                    return;
                }
                return;
            }
            Log.e("Keyguard-Editor-MainFragment", "updateButtonLayoutZInActivity ignored: fragment is not attached or detached.");
        } catch (Exception e) {
            Log.e("Keyguard-Editor-MainFragment", "updateButtonLayoutZInActivity", e);
        }
    }

    public final void updateMyTemplateViewForGallery() {
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(contextRequireContext), null, null, new AnonymousClass1(null), 3, null);
    }
}

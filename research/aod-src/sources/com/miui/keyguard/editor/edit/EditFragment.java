package com.miui.keyguard.editor.edit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.OnBackPressedCallback;
import androidx.core.view.KeyEventDispatcher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import com.android.thememanager.util.AodTrackMapData;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.CommonEditorActivity;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorServiceManager;
import com.miui.keyguard.editor.FashionGalleryCompat;
import com.miui.keyguard.editor.OnActivityConfigChangedListener;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.BaseFragment;
import com.miui.keyguard.editor.base.BaseOnBackPressedCallback;
import com.miui.keyguard.editor.base.ExitAnimationPerformer;
import com.miui.keyguard.editor.base.TransitionLayer;
import com.miui.keyguard.editor.base.TransitionPerformer;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.preset.FontFilterData;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.CombineEditView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.base.EditModeChangedListener;
import com.miui.keyguard.editor.edit.base.EffectsTemplateView;
import com.miui.keyguard.editor.edit.base.HomeTemplateView;
import com.miui.keyguard.editor.edit.base.ImageSegmentEditor;
import com.miui.keyguard.editor.edit.base.TemplateApplier;
import com.miui.keyguard.editor.edit.base.TemplateApplierAnimatorKt;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.homepage.MainFragment;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.ScenarioRecognitionUtils;
import com.miui.keyguard.editor.utils.TemplateRadiusAnimationListener;
import com.miui.keyguard.editor.utils.TemplateSource;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.MiuiClockViewSafeHolder;
import com.miui.keyguard.editor.view.WallpaperFilterView;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import com.xiaomi.onetrack.api.am;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miuix.animation.property.ViewProperty;

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
/* JADX INFO: compiled from: EditFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EditFragment extends BaseFragment implements EditorActivity.NavigationActionListener, EditModeChangedListener, ExitAnimationPerformer, EditCallback, EditorActivity.UserOperationListener {
    public static final Companion Companion = new Companion(null);
    public final EditFragment$applyTransaction$1 applyTransaction;
    public CombineEditView combineEditView;
    public CommonConfig currentConfig;
    public int defaultSelect;
    public FashionGalleryCompat fashionGalleryCompat;
    public boolean fromRecentLaunch;
    public HomeTemplateView homeTemplateView;
    public boolean isFromTower;
    public BaseTemplateView lockTemplateView;
    public int mPopFontFilterPaperDialog;
    public final Consumer navigationActionCallback;
    public CommonConfig originTempConfig;
    public boolean shouldAddHistory;
    public String source;
    public TemplateFilePathGenerator templateFilePathGenerator;
    public boolean userOperation;
    public final WallpaperEditor wallpaperEditor;
    public String wallpaperTarget;

    /* JADX INFO: compiled from: EditFragment.kt */
    @Metadata
    public final class BackPressedCallback extends BaseOnBackPressedCallback {
        public final /* synthetic */ EditFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BackPressedCallback(EditFragment editFragment, FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            Intrinsics.checkNotNullParameter(fragmentActivity, "fragmentActivity");
            this.this$0 = editFragment;
        }

        public static final Unit handleOnBackPressed$lambda$1(BackPressedCallback backPressedCallback) {
            super.handleOnBackPressed();
            return Unit.INSTANCE;
        }

        public static final Unit superHandleOnBackPressed$lambda$0(BackPressedCallback backPressedCallback) {
            super.handleOnBackPressed();
            return Unit.INSTANCE;
        }

        @Override // com.miui.keyguard.editor.base.BaseOnBackPressedCallback, androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            BaseTemplateView baseTemplateView;
            BaseClockView baseClockView;
            AutoBottomSheet wallpaperFilterAutoBottomSheet;
            WallpaperFilterView wallpaperFilterView;
            FragmentActivity activity = this.this$0.getActivity();
            EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
            if ((editorActivity == null || !editorActivity.isForwardTransitionRunning()) && !this.this$0.isInPreviewAnimation()) {
                BaseTemplateView baseTemplateView2 = this.this$0.lockTemplateView;
                if ((baseTemplateView2 != null && (wallpaperFilterView = baseTemplateView2.getWallpaperFilterView()) != null && wallpaperFilterView.isShown()) || ((baseTemplateView = this.this$0.lockTemplateView) != null && baseTemplateView.showBottomSheetModal())) {
                    BaseTemplateView baseTemplateView3 = this.this$0.lockTemplateView;
                    if (baseTemplateView3 != null && (wallpaperFilterAutoBottomSheet = baseTemplateView3.getWallpaperFilterAutoBottomSheet()) != null) {
                        wallpaperFilterAutoBottomSheet.dismiss();
                    }
                    BaseTemplateView baseTemplateView4 = this.this$0.lockTemplateView;
                    if (baseTemplateView4 != null && (baseClockView = (BaseClockView) baseTemplateView4.getClockLayer()) != null) {
                        baseClockView.dismissBottomSheet();
                    }
                    if (this.this$0.getActivity() instanceof EditorActivity) {
                        FragmentActivity activity2 = this.this$0.getActivity();
                        Intrinsics.checkNotNull(activity2, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
                        ((EditorActivity) activity2).isDirectFinishWhenBackPress();
                        return;
                    }
                    return;
                }
                EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.this$0);
                if (viewModel != null) {
                    BaseTemplateView baseTemplateView5 = this.this$0.lockTemplateView;
                    EditFragmentViewModel.reloadTemplateConfig$default(viewModel, baseTemplateView5 != null ? baseTemplateView5.getTemplateConfig() : null, true, false, 4, null);
                }
                BaseTemplateView baseTemplateView6 = this.this$0.lockTemplateView;
                BaseTemplateView baseTemplateView7 = baseTemplateView6 != null ? baseTemplateView6 : null;
                if (baseTemplateView7 == null || baseTemplateView7.onCancel(this.this$0.navigationActionCallback)) {
                    return;
                }
                this.this$0.performBackPress(new Function0() { // from class: com.miui.keyguard.editor.edit.EditFragment$BackPressedCallback$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return EditFragment.BackPressedCallback.handleOnBackPressed$lambda$1(this.f$0);
                    }
                });
                BaseTemplateView baseTemplateView8 = this.this$0.lockTemplateView;
                if (baseTemplateView8 != null) {
                    baseTemplateView8.resetAllBottomSheet();
                }
            }
        }

        public final void superHandleOnBackPressed() {
            this.this$0.performBackPress(new Function0() { // from class: com.miui.keyguard.editor.edit.EditFragment$BackPressedCallback$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return EditFragment.BackPressedCallback.superHandleOnBackPressed$lambda$0(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: compiled from: EditFragment.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: EditFragment.kt */
    @Metadata
    public static final class TemplateData {
        public final CommonConfig config;
        public final boolean isAutoDownload;
        public final boolean isGalleryOpened;
        public final long templateSource;

        public TemplateData(long j, CommonConfig commonConfig, boolean z, boolean z2) {
            this.templateSource = j;
            this.config = commonConfig;
            this.isGalleryOpened = z;
            this.isAutoDownload = z2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TemplateData)) {
                return false;
            }
            TemplateData templateData = (TemplateData) obj;
            return this.templateSource == templateData.templateSource && Intrinsics.areEqual(this.config, templateData.config) && this.isGalleryOpened == templateData.isGalleryOpened && this.isAutoDownload == templateData.isAutoDownload;
        }

        public final CommonConfig getConfig() {
            return this.config;
        }

        public final long getTemplateSource() {
            return this.templateSource;
        }

        public int hashCode() {
            int iHashCode = Long.hashCode(this.templateSource) * 31;
            CommonConfig commonConfig = this.config;
            return ((((iHashCode + (commonConfig == null ? 0 : commonConfig.hashCode())) * 31) + Boolean.hashCode(this.isGalleryOpened)) * 31) + Boolean.hashCode(this.isAutoDownload);
        }

        public final boolean isAutoDownload() {
            return this.isAutoDownload;
        }

        public final boolean isGalleryOpened() {
            return this.isGalleryOpened;
        }

        public String toString() {
            return "TemplateData(templateSource=" + this.templateSource + ", config=" + this.config + ", isGalleryOpened=" + this.isGalleryOpened + ", isAutoDownload=" + this.isAutoDownload + ")";
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.EditFragment$isInLockMode$2, reason: invalid class name */
    /* JADX INFO: compiled from: EditFragment.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.EditFragment$isInLockMode$2", f = "EditFragment.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(continuation);
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
            boolean zBooleanValue = false;
            try {
                Class<?> cls = Class.forName("android.app.ActivityTaskManager");
                Object objInvoke = cls.getMethod("isInLockTaskMode", new Class[0]).invoke(cls.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]), new Object[0]);
                Log.i("Keyguard-Editor-", "isInLockMode: " + objInvoke);
                Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
                zBooleanValue = ((Boolean) objInvoke).booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boxing.boxBoolean(zBooleanValue);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.EditFragment$onApply$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EditFragment.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.EditFragment$onApply$1", f = "EditFragment.kt", l = {1110}, m = "invokeSuspend")
    public static final class C00921 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $applyResult;
        public final /* synthetic */ LifecycleOwner $lifecycleOwner;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00921(LifecycleOwner lifecycleOwner, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$lifecycleOwner = lifecycleOwner;
            this.$applyResult = z;
        }

        public static final void invokeSuspend$lambda$0(EditorActivityViewModel editorActivityViewModel, EditFragment editFragment, int i) {
            EditorActivity editorActivity;
            if (i == 2) {
                Log.i("Keyguard-Editor-EditFragment", "onApply: END");
                editorActivityViewModel.updateApplyStatus(0);
                OnBackPressedCallback onBackPressedCallback = editFragment.getOnBackPressedCallback();
                Intrinsics.checkNotNull(onBackPressedCallback, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.EditFragment.BackPressedCallback");
                ((BackPressedCallback) onBackPressedCallback).handleOnBackPressed();
                if (editFragment.getActivity() instanceof CommonEditorActivity) {
                    FragmentActivity activity = editFragment.getActivity();
                    editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
                    if (editorActivity != null) {
                        editorActivity.onApplyEndFromCommonEditorActivity();
                        return;
                    }
                    return;
                }
                FragmentActivity activity2 = editFragment.getActivity();
                editorActivity = activity2 instanceof EditorActivity ? (EditorActivity) activity2 : null;
                if (editorActivity != null) {
                    editorActivity.onApplyEndWhenPinned();
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditFragment.this.new C00921(this.$lifecycleOwner, this.$applyResult, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00921) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            OnBackPressedCallback onBackPressedCallback;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                EditFragment editFragment = EditFragment.this;
                this.label = 1;
                obj = editFragment.isInLockMode(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (!((Boolean) obj).booleanValue() || !(EditFragment.this.getActivity() instanceof EditorActivity) || this.$lifecycleOwner == null) {
                if (this.$applyResult && (onBackPressedCallback = EditFragment.this.getOnBackPressedCallback()) != null) {
                    onBackPressedCallback.handleOnBackPressed();
                }
                return Unit.INSTANCE;
            }
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            FragmentActivity activity = EditFragment.this.getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
            final EditorActivityViewModel viewModel = companion.getViewModel((EditorActivity) activity);
            LifecycleOwner lifecycleOwner = this.$lifecycleOwner;
            final EditFragment editFragment2 = EditFragment.this;
            viewModel.observeApplyStatus(lifecycleOwner, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$onApply$1$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj2) {
                    EditFragment.C00921.invokeSuspend$lambda$0(viewModel, editFragment2, ((Integer) obj2).intValue());
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.EditFragment$onViewCreated$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EditFragment.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.EditFragment$onViewCreated$2", f = "EditFragment.kt", l = {177}, m = "invokeSuspend")
    public static final class C00932 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Bundle $clockExtras;
        public final /* synthetic */ String $configPath;
        public final /* synthetic */ int $hideFlag;
        public final /* synthetic */ boolean $historyIsThirdPartyWallpaper;
        public final /* synthetic */ boolean $isAutoDownload;
        public final /* synthetic */ long $templateSource;
        public final /* synthetic */ View $view;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00932(View view, long j, String str, boolean z, Bundle bundle, boolean z2, int i, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
            this.$templateSource = j;
            this.$configPath = str;
            this.$historyIsThirdPartyWallpaper = z;
            this.$clockExtras = bundle;
            this.$isAutoDownload = z2;
            this.$hideFlag = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditFragment.this.new C00932(this.$view, this.$templateSource, this.$configPath, this.$historyIsThirdPartyWallpaper, this.$clockExtras, this.$isAutoDownload, this.$hideFlag, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00932) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                EditFragment$onViewCreated$2$data$1 editFragment$onViewCreated$2$data$1 = new EditFragment$onViewCreated$2$data$1(EditFragment.this, this.$view, this.$templateSource, this.$configPath, this.$historyIsThirdPartyWallpaper, this.$clockExtras, this.$isAutoDownload, this.$hideFlag, null);
                this.label = 1;
                obj = BuildersKt.withContext(io2, editFragment$onViewCreated$2$data$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Log.d("EditFragment", "start init template");
            EditFragment.this.attachTemplateView((TemplateData) obj);
            EditFragment.this.afterTemplateViewAttach();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.miui.keyguard.editor.edit.EditFragment$applyTransaction$1] */
    public EditFragment() {
        super(R.layout.kg_edit_fragment);
        this.wallpaperEditor = new WallpaperEditor(this);
        this.source = "";
        this.mPopFontFilterPaperDialog = -1;
        this.navigationActionCallback = new Consumer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                EditFragment.navigationActionCallback$lambda$30(this.f$0, obj);
            }
        };
        this.applyTransaction = new TemplateApplier.ApplyCallback() { // from class: com.miui.keyguard.editor.edit.EditFragment$applyTransaction$1
            @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
            public void finish() {
            }

            @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
            public void onApplied(boolean z, TemplateApplier.ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer) {
                Intent intent;
                Intent intent2;
                this.this$0.trackApplyWallpaper();
                FragmentActivity activity = this.this$0.getActivity();
                boolean z2 = (activity == null || (intent2 = activity.getIntent()) == null || intent2.getIntExtra("openSource", -1) != 0) ? false : true;
                EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.this$0);
                boolean z3 = viewModel != null && viewModel.getSelectTemplate() == 0;
                Log.i("EditFragment", "onApplied " + z + " selectLock=" + z3);
                if (z && !z3 && z2 && z2) {
                    KeyEventDispatcher.Component activity2 = this.this$0.getActivity();
                    ExitAnimationPerformer exitAnimationPerformer2 = activity2 instanceof ExitAnimationPerformer ? (ExitAnimationPerformer) activity2 : null;
                    if (exitAnimationPerformer2 != null) {
                        ExitAnimationPerformer.DefaultImpls.startExitTransformerAnimation$default(exitAnimationPerformer2, false, null, null, 6, null);
                    }
                    CombineEditView combineEditView = this.this$0.combineEditView;
                    if (combineEditView != null) {
                        combineEditView.startExitTransformerAnimation(false, applyCallback, exitAnimationPerformer);
                    }
                }
                if (DeviceUtil.INSTANCE.isInternationalBuild() && z) {
                    FragmentActivity activity3 = this.this$0.getActivity();
                    EditorActivity editorActivity = activity3 instanceof EditorActivity ? (EditorActivity) activity3 : null;
                    String stringExtra = (editorActivity == null || (intent = editorActivity.getIntent()) == null) ? null : intent.getStringExtra("caller");
                    if (TextUtils.equals(stringExtra, "gallery")) {
                        WallpaperEditor.Companion companion = WallpaperEditor.Companion;
                        Context context = this.this$0.getContext();
                        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
                        Intrinsics.checkNotNull(stringExtra);
                        companion.launchThemeWallpaperRecommend((EditorActivity) context, stringExtra);
                    }
                }
                this.this$0.setInterceptAllEventsOnApply(false);
                if (z) {
                    return;
                }
                FragmentActivity activity4 = this.this$0.getActivity();
                EditorActivity editorActivity2 = activity4 instanceof EditorActivity ? (EditorActivity) activity4 : null;
                if (editorActivity2 != null) {
                    editorActivity2.onDisMissApplyLoading();
                }
            }

            @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
            public void onApplySuccessBeforeExitAnim() {
                FragmentActivity activity = this.this$0.getActivity();
                EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
                if (editorActivity != null) {
                    editorActivity.setApplyStatus(true);
                }
                this.this$0.actionBeforeScheduleApply(false);
            }

            @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
            public void onPrepareApply() {
                Log.i("Keyguard-Editor-EditFragment", "onPrepareApply");
                this.this$0.setInterceptAllEventsOnApply(true);
            }

            @Override // com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
            public void onStartApply(boolean z) {
                this.this$0.actionBeforeScheduleApply(z);
                FragmentActivity activity = this.this$0.getActivity();
                EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
                if (editorActivity != null) {
                    editorActivity.detachMainFragment();
                }
            }
        };
    }

    public static final void afterTemplateViewAttach$lambda$12(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, IntWrapper it) {
        Intrinsics.checkNotNullParameter(it, "it");
        IntWrapper homeFilterId = editFragmentViewModel.getHomeFilterId();
        HomeTemplateView homeTemplateView = editFragment.homeTemplateView;
        if (homeTemplateView != null) {
            homeTemplateView.updateBlurButtonStatus(Integer.valueOf(homeFilterId.getIntValue()));
        }
        if (homeFilterId.getOnlySet()) {
            Log.d("EditFragment", "init home filter");
            return;
        }
        HomeTemplateView homeTemplateView2 = editFragment.homeTemplateView;
        if (homeTemplateView2 != null) {
            homeTemplateView2.onEdited(210, Integer.valueOf(homeFilterId.getIntValue()));
        }
    }

    public static final void afterTemplateViewAttach$lambda$13(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, IntWrapper it) {
        Intrinsics.checkNotNullParameter(it, "it");
        IntWrapper homeWallpaperStatus = editFragmentViewModel.getHomeWallpaperStatus();
        HomeTemplateView homeTemplateView = editFragment.homeTemplateView;
        if (homeTemplateView != null) {
            homeTemplateView.updateWallpaperStatus(Integer.valueOf(homeWallpaperStatus.getIntValue()));
        }
        if (homeWallpaperStatus.getOnlySet()) {
            Log.d("EditFragment", "init home wallpaper status");
        } else {
            homeWallpaperStatus.getIntValue();
        }
    }

    public static final void afterTemplateViewAttach$lambda$14(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, IntWrapper it) {
        Intrinsics.checkNotNullParameter(it, "it");
        IntWrapper followStatus = editFragmentViewModel.getFollowStatus();
        HomeTemplateView homeTemplateView = editFragment.homeTemplateView;
        if (homeTemplateView != null) {
            homeTemplateView.updateFollowButtonStatus(Integer.valueOf(followStatus.getIntValue()));
        }
        if (followStatus.getOnlySet()) {
            Log.d("EditFragment", "init follow status");
            return;
        }
        int intValue = followStatus.getIntValue();
        if (intValue == 0) {
            Log.d("EditFragment", "disable follow");
            return;
        }
        if (intValue == 1) {
            Log.d("EditFragment", "only wallpaper");
            return;
        }
        if (intValue == 2) {
            Log.d("EditFragment", "follow all");
            return;
        }
        Log.e("EditFragment", "error follow status:" + followStatus);
    }

    public static final void afterTemplateViewAttach$lambda$15(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, IntWrapper it) {
        WallpaperFilterView wallpaperFilterView;
        Intrinsics.checkNotNullParameter(it, "it");
        IntWrapper wallpaperFilterId = editFragmentViewModel.getWallpaperFilterId();
        if (wallpaperFilterId.getOnlySet()) {
            Log.d("EditFragment", "init wallpaper filter id");
            return;
        }
        Log.d("EditFragment", "observeWallpaperFilterStatus filterId=" + wallpaperFilterId);
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        if (baseTemplateView != null && (wallpaperFilterView = baseTemplateView.getWallpaperFilterView()) != null) {
            wallpaperFilterView.updateWallpaperFilterId(wallpaperFilterId.getIntValue());
        }
        BaseTemplateView baseTemplateView2 = editFragment.lockTemplateView;
        if (baseTemplateView2 != null) {
            baseTemplateView2.onEdited(10, Integer.valueOf(wallpaperFilterId.getIntValue()));
        }
    }

    public static final void afterTemplateViewAttach$lambda$17(EditFragment editFragment, EditFragmentViewModel editFragmentViewModel, IntWrapper it) throws IOException {
        HierarchyImageAvoidController avoidController;
        HierarchyImageAvoidController avoidController2;
        HierarchyImageAvoidController avoidController3;
        CommonConfig commonConfigTemplateConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        HierarchyImageAvoidController avoidController4;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        HierarchyImageAvoidController avoidController5;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        Intrinsics.checkNotNullParameter(it, "it");
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        EffectsTemplateView effectsTemplateView = baseTemplateView instanceof EffectsTemplateView ? (EffectsTemplateView) baseTemplateView : null;
        IntWrapper hierarchyStatus = editFragmentViewModel.getHierarchyStatus();
        if (hierarchyStatus.getOnlySet()) {
            Log.d("EditFragment", "init hierarchy");
            return;
        }
        if (effectsTemplateView != null) {
            ImageSegmentEditor segmentEditor = effectsTemplateView.getSegmentEditor();
            SegmentInfo subjectInfo = segmentEditor != null ? segmentEditor.getSubjectInfo() : null;
            switch (hierarchyStatus.getIntValue()) {
                case 0:
                    effectsTemplateView.onEdited(40, null);
                    HierarchyImageView hierarchyImageView = (HierarchyImageView) effectsTemplateView.getHierarchyLayer();
                    HierarchyImageAvoidController avoidController6 = hierarchyImageView != null ? hierarchyImageView.getAvoidController() : null;
                    if (avoidController6 != null) {
                        avoidController6.setUserOpenHierarchy(false);
                    }
                    if (avoidController6 != null) {
                        avoidController6.setHierarchyEnable(false);
                    }
                    EditFragmentViewModel.updateDoodleStatus$default(editFragmentViewModel, 0, false, 2, null);
                    editFragmentViewModel.getTemplateChangeRecorder().clearRecord(new Integer[]{4, 2});
                    break;
                case 1:
                    effectsTemplateView.onEdited(40, subjectInfo);
                    HierarchyImageView hierarchyImageView2 = (HierarchyImageView) effectsTemplateView.getHierarchyLayer();
                    HierarchyImageAvoidController avoidController7 = hierarchyImageView2 != null ? hierarchyImageView2.getAvoidController() : null;
                    if (avoidController7 != null) {
                        avoidController7.setUserOpenHierarchy(true);
                    }
                    if (avoidController7 != null) {
                        avoidController7.setHierarchyEnable(true);
                    }
                    break;
                case 2:
                    effectsTemplateView.onEdited(40, null);
                    HierarchyImageView hierarchyImageView3 = (HierarchyImageView) effectsTemplateView.getHierarchyLayer();
                    if (hierarchyImageView3 != null && (avoidController = hierarchyImageView3.getAvoidController()) != null) {
                        avoidController.setUserOpenHierarchy(false);
                    }
                    break;
                case 3:
                    HierarchyImageView hierarchyImageView4 = (HierarchyImageView) effectsTemplateView.getHierarchyLayer();
                    if (hierarchyImageView4 != null && (avoidController2 = hierarchyImageView4.getAvoidController()) != null) {
                        avoidController2.setHierarchyEnable(true);
                    }
                    break;
                case 4:
                    HierarchyImageView hierarchyImageView5 = (HierarchyImageView) effectsTemplateView.getHierarchyLayer();
                    if (hierarchyImageView5 != null && (avoidController3 = hierarchyImageView5.getAvoidController()) != null) {
                        avoidController3.setHierarchyEnable(false);
                    }
                    break;
                case 5:
                    CommonConfig commonConfigTemplateConfig2 = effectsTemplateView.templateConfig();
                    if ((commonConfigTemplateConfig2 == null || (lockscreenInfo2 = commonConfigTemplateConfig2.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null || wallpaperInfo2.getMagicType() != 100000) && (commonConfigTemplateConfig = effectsTemplateView.templateConfig()) != null && (lockscreenInfo = commonConfigTemplateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                        wallpaperInfo.updateMagicType(MagicType.TYPE_DEPTH_VIDEO);
                    }
                    HierarchyImageView hierarchyImageView6 = (HierarchyImageView) effectsTemplateView.getHierarchyLayer();
                    if (hierarchyImageView6 != null && (avoidController4 = hierarchyImageView6.getAvoidController()) != null) {
                        avoidController4.setUserOpenDepthVideo(true);
                    }
                    break;
                case 6:
                    CommonConfig commonConfigTemplateConfig3 = effectsTemplateView.templateConfig();
                    if (commonConfigTemplateConfig3 != null && (lockscreenInfo3 = commonConfigTemplateConfig3.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null) {
                        wallpaperInfo3.resetMagicType("video hierarchy close");
                    }
                    HierarchyImageView hierarchyImageView7 = (HierarchyImageView) effectsTemplateView.getHierarchyLayer();
                    if (hierarchyImageView7 != null && (avoidController5 = hierarchyImageView7.getAvoidController()) != null) {
                        avoidController5.setUserOpenDepthVideo(false);
                    }
                    break;
                default:
                    Log.d("EditFragment", "error hierarchy status=" + hierarchyStatus);
                    break;
            }
            effectsTemplateView.updateHierarchyButton(hierarchyStatus.getIntValue());
        }
    }

    public static final void afterTemplateViewAttach$lambda$18(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, IntWrapper it) {
        WallpaperFilterView wallpaperFilterView;
        Intrinsics.checkNotNullParameter(it, "it");
        IntWrapper matteStatus = editFragmentViewModel.getMatteStatus();
        if (matteStatus.getOnlySet()) {
            Log.d("EditFragment", "init matte");
            return;
        }
        if (!MagicType.INSTANCE.isGlassVertical(editFragmentViewModel.getWallpaperFilterId().getIntValue())) {
            Log.d("EditFragment", "current magic is not glass");
            matteStatus.setIntValue(3);
        }
        Log.d("EditFragment", "update matte=" + matteStatus);
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.onEdited(90, Boolean.valueOf(matteStatus.getIntValue() == 2));
        }
        BaseTemplateView baseTemplateView2 = editFragment.lockTemplateView;
        if (baseTemplateView2 == null || (wallpaperFilterView = baseTemplateView2.getWallpaperFilterView()) == null) {
            return;
        }
        wallpaperFilterView.updateMatte(matteStatus.getIntValue());
    }

    public static final void afterTemplateViewAttach$lambda$19(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, IntWrapper it) {
        BaseClockView baseClockView;
        FontFilterView clockFontFilterView;
        BaseClockView baseClockView2;
        FontFilterView clockFontFilterView2;
        Intrinsics.checkNotNullParameter(it, "it");
        IntWrapper doodleStatus = editFragmentViewModel.getDoodleStatus();
        if (doodleStatus.getOnlySet()) {
            Log.d("EditFragment", "init doodle font");
            return;
        }
        Log.i("EditFragment", "update doodle=" + doodleStatus.getIntValue());
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        BaseClockView baseClockView3 = baseTemplateView != null ? (BaseClockView) baseTemplateView.getClockLayer() : null;
        DoodleTemplateView doodleTemplateView = baseClockView3 instanceof DoodleTemplateView ? (DoodleTemplateView) baseClockView3 : null;
        FragmentActivity activity = editFragment.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
        if (DualClockManager.isDualClockAvailable((EditorActivity) activity)) {
            BaseTemplateView baseTemplateView2 = editFragment.lockTemplateView;
            if (baseTemplateView2 != null && (baseClockView = (BaseClockView) baseTemplateView2.getClockLayer()) != null && (clockFontFilterView = baseClockView.getClockFontFilterView()) != null) {
                clockFontFilterView.updateDoodle(0);
            }
        } else {
            BaseTemplateView baseTemplateView3 = editFragment.lockTemplateView;
            if (baseTemplateView3 != null && (baseClockView2 = (BaseClockView) baseTemplateView3.getClockLayer()) != null && (clockFontFilterView2 = baseClockView2.getClockFontFilterView()) != null) {
                clockFontFilterView2.updateDoodle(doodleStatus.getIntValue());
            }
        }
        if (doodleTemplateView != null) {
            doodleTemplateView.setDoodleTextVisible(doodleStatus.getIntValue() == 1);
        }
    }

    public static final void afterTemplateViewAttach$lambda$20(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, FontFilterData it) {
        ColorPickHandler colorPickHandler;
        ClockBean clockBean;
        ClockBean clockBean2;
        ClockBean clockBean3;
        ClockBean clockBean4;
        ClockBean clockBean5;
        ClockBean clockBean6;
        BaseClockView baseClockView;
        MiuiClockViewSafeHolder miuiClockViewSafeHolder;
        WeakReference primaryClockView;
        Intrinsics.checkNotNullParameter(it, "it");
        FontFilterData primaryFontFilter = editFragmentViewModel.getPrimaryFontFilter();
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        Integer numValueOf = null;
        MiuiClockView miuiClockView = (baseTemplateView == null || (baseClockView = (BaseClockView) baseTemplateView.getClockLayer()) == null || (miuiClockViewSafeHolder = baseClockView.getMiuiClockViewSafeHolder()) == null || (primaryClockView = miuiClockViewSafeHolder.getPrimaryClockView()) == null) ? null : (MiuiClockView) primaryClockView.get();
        if (primaryFontFilter.getOnlySet()) {
            Log.d("EditFragment", "init primary color");
            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
            int filterId = primaryFontFilter.getFilterId();
            Integer numValueOf2 = (miuiClockView == null || (clockBean6 = miuiClockView.getClockBean()) == null) ? null : Integer.valueOf(clockBean6.getClockEffect());
            boolean z = primaryFontFilter.getColorId() == 0;
            Boolean boolValueOf = (miuiClockView == null || (clockBean5 = miuiClockView.getClockBean()) == null) ? null : Boolean.valueOf(clockBean5.isAutoPrimaryColor());
            int color = primaryFontFilter.getColor();
            if (miuiClockView != null && (clockBean4 = miuiClockView.getClockBean()) != null) {
                numValueOf = Integer.valueOf(clockBean4.getPrimaryColor());
            }
            keyguardEditorMiSightUtil.trackPrimaryFontFilter(filterId, numValueOf2, z, boolValueOf, color, numValueOf);
            return;
        }
        BaseTemplateView baseTemplateView2 = editFragment.lockTemplateView;
        EffectsTemplateView effectsTemplateView = baseTemplateView2 instanceof EffectsTemplateView ? (EffectsTemplateView) baseTemplateView2 : null;
        if (effectsTemplateView != null) {
            EffectsTemplateView.refreshGlassFilterEnable$default(effectsTemplateView, null, 1, null);
        }
        if (primaryFontFilter.getColorId() == 0) {
            BaseTemplateView baseTemplateView3 = editFragment.lockTemplateView;
            ColorPickHandler colorPickHandler2 = baseTemplateView3 != null ? baseTemplateView3.getColorPickHandler() : null;
            if (colorPickHandler2 == null) {
                Log.d("EditFragment", "color picker is null");
                return;
            }
            colorPickHandler2.onEdited(50, new ColorSelectBean(colorPickHandler2.autoPickColorData().getPrimaryColor(), true, true, primaryFontFilter.getFilterId(), 0));
        } else {
            BaseTemplateView baseTemplateView4 = editFragment.lockTemplateView;
            if (baseTemplateView4 != null && (colorPickHandler = baseTemplateView4.getColorPickHandler()) != null) {
                colorPickHandler.onEdited(50, new ColorSelectBean(primaryFontFilter.getColor(), true, false, primaryFontFilter.getFilterId(), primaryFontFilter.getColorId(), 4, null));
            }
        }
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil2 = KeyguardEditorMiSightUtil.INSTANCE;
        int filterId2 = primaryFontFilter.getFilterId();
        Integer numValueOf3 = (miuiClockView == null || (clockBean3 = miuiClockView.getClockBean()) == null) ? null : Integer.valueOf(clockBean3.getClockEffect());
        boolean z2 = primaryFontFilter.getColorId() == 0;
        Boolean boolValueOf2 = (miuiClockView == null || (clockBean2 = miuiClockView.getClockBean()) == null) ? null : Boolean.valueOf(clockBean2.isAutoPrimaryColor());
        int color2 = primaryFontFilter.getColor();
        if (miuiClockView != null && (clockBean = miuiClockView.getClockBean()) != null) {
            numValueOf = Integer.valueOf(clockBean.getPrimaryColor());
        }
        keyguardEditorMiSightUtil2.trackPrimaryFontFilter(filterId2, numValueOf3, z2, boolValueOf2, color2, numValueOf);
    }

    public static final void afterTemplateViewAttach$lambda$21(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, FontFilterData it) {
        ColorPickHandler colorPickHandler;
        boolean z;
        ClockBean clockBean;
        ClockBean clockBean2;
        CommonConfig templateConfig;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        ClockBean clockBean3;
        ClockBean clockBean4;
        BaseClockView baseClockView;
        MiuiClockViewSafeHolder miuiClockViewSafeHolder;
        WeakReference primaryClockView;
        Intrinsics.checkNotNullParameter(it, "it");
        FontFilterData secondaryFontFilter = editFragmentViewModel.getSecondaryFontFilter();
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        Integer numValueOf = null;
        MiuiClockView miuiClockView = (baseTemplateView == null || (baseClockView = (BaseClockView) baseTemplateView.getClockLayer()) == null || (miuiClockViewSafeHolder = baseClockView.getMiuiClockViewSafeHolder()) == null || (primaryClockView = miuiClockViewSafeHolder.getPrimaryClockView()) == null) ? null : (MiuiClockView) primaryClockView.get();
        if (secondaryFontFilter.getOnlySet()) {
            Log.d("EditFragment", "init secondary color");
            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
            z = secondaryFontFilter.getColorId() == 0;
            Boolean boolValueOf = (miuiClockView == null || (clockBean4 = miuiClockView.getClockBean()) == null) ? null : Boolean.valueOf(clockBean4.isAutoSecondaryColor());
            int color = secondaryFontFilter.getColor();
            if (miuiClockView != null && (clockBean3 = miuiClockView.getClockBean()) != null) {
                numValueOf = Integer.valueOf(clockBean3.getSecondaryColor());
            }
            keyguardEditorMiSightUtil.trackSecondaryFontFilter(z, boolValueOf, color, numValueOf);
            return;
        }
        if (secondaryFontFilter.getColorId() == 0) {
            BaseTemplateView baseTemplateView2 = editFragment.lockTemplateView;
            ColorPickHandler colorPickHandler2 = baseTemplateView2 != null ? baseTemplateView2.getColorPickHandler() : null;
            if (colorPickHandler2 == null) {
                Log.d("EditFragment", "color picker is null");
                return;
            }
            colorPickHandler2.onEdited(50, new ColorSelectBean(colorPickHandler2.autoPickColorData().getSecondaryColor(), false, true, secondaryFontFilter.getFilterId(), 0));
        } else {
            BaseTemplateView baseTemplateView3 = editFragment.lockTemplateView;
            if (baseTemplateView3 != null && (colorPickHandler = baseTemplateView3.getColorPickHandler()) != null) {
                colorPickHandler.onEdited(50, new ColorSelectBean(secondaryFontFilter.getColor(), false, false, secondaryFontFilter.getFilterId(), secondaryFontFilter.getColorId(), 4, null));
            }
        }
        BaseTemplateView baseTemplateView4 = editFragment.lockTemplateView;
        if (Intrinsics.areEqual("doodle", (baseTemplateView4 == null || (templateConfig = baseTemplateView4.getTemplateConfig()) == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId())) {
            BaseTemplateView baseTemplateView5 = editFragment.lockTemplateView;
            BaseClockView baseClockView2 = baseTemplateView5 != null ? (BaseClockView) baseTemplateView5.getClockLayer() : null;
            DoodleTemplateView doodleTemplateView = baseClockView2 instanceof DoodleTemplateView ? (DoodleTemplateView) baseClockView2 : null;
            if (doodleTemplateView != null) {
                doodleTemplateView.updateRibbonColor();
            }
        }
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil2 = KeyguardEditorMiSightUtil.INSTANCE;
        z = secondaryFontFilter.getColorId() == 0;
        Boolean boolValueOf2 = (miuiClockView == null || (clockBean2 = miuiClockView.getClockBean()) == null) ? null : Boolean.valueOf(clockBean2.isAutoSecondaryColor());
        int color2 = secondaryFontFilter.getColor();
        if (miuiClockView != null && (clockBean = miuiClockView.getClockBean()) != null) {
            numValueOf = Integer.valueOf(clockBean.getSecondaryColor());
        }
        keyguardEditorMiSightUtil2.trackSecondaryFontFilter(z, boolValueOf2, color2, numValueOf);
    }

    public static final void afterTemplateViewAttach$lambda$22(EditFragmentViewModel editFragmentViewModel, EditFragment editFragment, IntWrapper it) {
        BaseClockView baseClockView;
        FontFilterView clockFontFilterView;
        ColorPickHandler colorPickHandler;
        Intrinsics.checkNotNullParameter(it, "it");
        IntWrapper differentColorStatus = editFragmentViewModel.getDifferentColorStatus();
        if (differentColorStatus.getOnlySet()) {
            Log.d("EditFragment", "init different color");
            return;
        }
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        if (baseTemplateView != null && (colorPickHandler = baseTemplateView.getColorPickHandler()) != null) {
            colorPickHandler.onEdited(51, Boolean.valueOf(differentColorStatus.getIntValue() == 1));
        }
        BaseTemplateView baseTemplateView2 = editFragment.lockTemplateView;
        if (baseTemplateView2 == null || (baseClockView = (BaseClockView) baseTemplateView2.getClockLayer()) == null || (clockFontFilterView = baseClockView.getClockFontFilterView()) == null) {
            return;
        }
        clockFontFilterView.updateDifferentColor(differentColorStatus.getIntValue());
    }

    public static /* synthetic */ void initCombineEditView$default(EditFragment editFragment, boolean z, TemplateData templateData, int i, Object obj) {
        if ((i & 2) != 0) {
            templateData = null;
        }
        editFragment.initCombineEditView(z, templateData);
    }

    private final void initFashionGalleryCompat() {
        if (DeviceUtil.INSTANCE.isInternationalBuild() && this.fashionGalleryCompat == null) {
            this.fashionGalleryCompat = new FashionGalleryCompat(this);
        }
    }

    public static final void navigationActionCallback$lambda$30(EditFragment editFragment, Object obj) {
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
        if (((Boolean) obj).booleanValue()) {
            Log.d("Keyguard-Editor-EditFragment", "navigationActionCallback finish");
            FragmentActivity activity = editFragment.getActivity();
            EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
            if (editorActivity != null && CommonUtils.INSTANCE.isActivityPinned(editorActivity.getApplicationContext())) {
                Log.i("EditFragment", "reset when pinned");
                editorActivity.setEnterApplying(false);
                editorActivity.setApplyStatus(false);
            }
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(editFragment), null, null, new EditFragment$navigationActionCallback$1$2(editFragment, null), 3, null);
            return;
        }
        editFragment.changeActivityIsDismissKgWhenPause(true);
        OnBackPressedCallback onBackPressedCallback = editFragment.getOnBackPressedCallback();
        Intrinsics.checkNotNull(onBackPressedCallback, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.EditFragment.BackPressedCallback");
        ((BackPressedCallback) onBackPressedCallback).superHandleOnBackPressed();
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        if (baseTemplateView == null || !baseTemplateView.isEdited()) {
            return;
        }
        editFragment.trackOtherQuitEdit();
    }

    public static final Unit startExitTransformerAnimation$lambda$41(EditFragment editFragment) {
        BaseTemplateView baseTemplateView = editFragment.lockTemplateView;
        Intrinsics.checkNotNull(baseTemplateView);
        baseTemplateView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                EditFragment.startExitTransformerAnimation$lambda$41$lambda$40();
            }
        }, 200L);
        return Unit.INSTANCE;
    }

    public static final void startExitTransformerAnimation$lambda$41$lambda$40() {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(1467L);
        EditorServiceManager.Companion.getInstance().onReceiveFinishCommand();
    }

    public final void actionBeforeScheduleApply(boolean z) {
        FragmentActivity activity = getActivity();
        if (activity == null || !(activity instanceof EditorActivity)) {
            return;
        }
        ((EditorActivity) activity).showOrHideApplyLoading(z);
        if (z) {
            changeActivityIsDismissKgWhenPause(false);
        }
    }

    public final void afterTemplateViewAttach() {
        final EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this);
        if (viewModel != null) {
            BaseTemplateView baseTemplateView = this.lockTemplateView;
            viewModel.initHomeButtonStatus(baseTemplateView != null ? baseTemplateView.getTemplateConfig() : null, true);
        }
        if (viewModel != null) {
            BaseTemplateView baseTemplateView2 = this.lockTemplateView;
            viewModel.reloadTemplateConfig(baseTemplateView2 != null ? baseTemplateView2.getTemplateConfig() : null, true, true);
        }
        if (viewModel != null) {
            viewModel.observeHomeFilterId(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$12(viewModel, this, (IntWrapper) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeHomeWallpaperStatus(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$13(viewModel, this, (IntWrapper) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeFollowStatus(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$14(viewModel, this, (IntWrapper) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeWallpaperFilterStatus(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$15(viewModel, this, (IntWrapper) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeHierarchyStatus(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) throws IOException {
                    EditFragment.afterTemplateViewAttach$lambda$17(this.f$0, viewModel, (IntWrapper) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeMatteStatus(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda5
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$18(viewModel, this, (IntWrapper) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeDoodleStatus(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda6
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$19(viewModel, this, (IntWrapper) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observePrimaryFontFilter(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda7
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$20(viewModel, this, (FontFilterData) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeSecondaryFontFilter(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$21(viewModel, this, (FontFilterData) obj);
                }
            });
        }
        if (viewModel != null) {
            viewModel.observeDifferentColorStatus(this, new Observer() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda9
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    EditFragment.afterTemplateViewAttach$lambda$22(viewModel, this, (IntWrapper) obj);
                }
            });
        }
    }

    public final void attachTemplateView(TemplateData templateData) {
        Context context = getContext();
        CommonConfig config = templateData.getConfig();
        if (config != null && context != null) {
            initCombineEditView(false, templateData);
            return;
        }
        Log.e("Keyguard-Editor-EditFragment", "attachTemplateView: env error, templateData.config:" + (config == null) + " ctx:" + (context == null));
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public void beforeApply() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        EditorActivityViewModel viewModel = companion.getViewModel(contextRequireContext);
        if (viewModel != null) {
            viewModel.setApplyTemplateConfig(this.currentConfig);
        }
        CommonConfig commonConfig = this.currentConfig;
        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null && Intrinsics.areEqual(wallpaperInfo.getResourceType(), "video")) {
            BaseTemplateView baseTemplateView = this.lockTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.removeBackground(null);
            }
            HomeTemplateView homeTemplateView = this.homeTemplateView;
            if (homeTemplateView != null) {
                homeTemplateView.removeBackground(null);
            }
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

    public final void beforeTemplateViewAttach(Bundle bundle) {
        FragmentActivity activity = getActivity();
        EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
        if (editorActivity == null) {
            return;
        }
        initCombineEditView$default(this, true, null, 2, null);
        editorActivity.setOnActivityConfigChangedListener(new OnActivityConfigChangedListener() { // from class: com.miui.keyguard.editor.edit.EditFragment.beforeTemplateViewAttach.1
            @Override // com.miui.keyguard.editor.OnActivityConfigChangedListener
            public void onConfigChanged(Configuration newConfig) {
                Intrinsics.checkNotNullParameter(newConfig, "newConfig");
                BaseTemplateView baseTemplateView = EditFragment.this.lockTemplateView;
                if (baseTemplateView != null) {
                    baseTemplateView.onDismissBottomSheetForScreenChange(newConfig);
                }
                BaseTemplateView baseTemplateView2 = EditFragment.this.lockTemplateView;
                if (baseTemplateView2 != null) {
                    baseTemplateView2.resetAllBottomSheet();
                }
                BaseTemplateView baseTemplateView3 = EditFragment.this.lockTemplateView;
                if (baseTemplateView3 != null) {
                    baseTemplateView3.resetStyleEditor();
                }
                HomeTemplateView homeTemplateView = EditFragment.this.homeTemplateView;
                if (homeTemplateView != null) {
                    HomeTemplateView.updateHomeIconPreview$default(homeTemplateView, null, null, 3, null);
                }
                DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                if (deviceUtil.isPad() || deviceUtil.isFold()) {
                    BaseTemplateView baseTemplateView4 = EditFragment.this.lockTemplateView;
                    if (baseTemplateView4 != null) {
                        baseTemplateView4.refreshBarLayout();
                    }
                    HomeTemplateView homeTemplateView2 = EditFragment.this.homeTemplateView;
                    if (homeTemplateView2 != null) {
                        homeTemplateView2.refreshBarLayout();
                    }
                }
                if (deviceUtil.isPhone()) {
                    return;
                }
                CombineEditView combineEditView = EditFragment.this.combineEditView;
                if (combineEditView != null) {
                    combineEditView.updateConfiguration(newConfig);
                }
                BaseTemplateView baseTemplateView5 = EditFragment.this.lockTemplateView;
                if (baseTemplateView5 != null) {
                    baseTemplateView5.updataClockConfig(newConfig);
                }
                HomeTemplateView homeTemplateView3 = EditFragment.this.homeTemplateView;
                if (homeTemplateView3 != null) {
                    homeTemplateView3.updataClockConfig(newConfig);
                }
            }
        });
    }

    public final void changeActivityIsDismissKgWhenPause(boolean z) {
        FragmentActivity activity = getActivity();
        if (activity == null || !(activity instanceof EditorActivity)) {
            return;
        }
        ((EditorActivity) activity).changeIsDismissKeyguardWhenPause(z);
    }

    public final CommonConfig checkAndLoadCurrentTemplateConfig(long j, Bundle bundle, WallpaperTypeInfo wallpaperTypeInfo) {
        FragmentActivity activity = getActivity();
        if (activity != null && TemplateSource.INSTANCE.isFromHistoryTemplate(j) && (activity instanceof CommonEditorActivity) && this.fromRecentLaunch) {
            TemplateApi companion = TemplateApi.Companion.getInstance(activity);
            boolean z = getActivity() instanceof CommonEditorActivity;
            String str = this.wallpaperTarget;
            if (str == null) {
                str = "all";
            }
            CommonConfig commonConfigCurrentTemplate$default = TemplateApi.DefaultImpls.currentTemplate$default(companion, true, true, true, false, wallpaperTypeInfo, z, str, 8, null);
            if (commonConfigCurrentTemplate$default != null) {
                commonConfigCurrentTemplate$default.getLockscreenInfo().setClockExtras(bundle);
                return commonConfigCurrentTemplate$default;
            }
        }
        return null;
    }

    public final boolean checkScreenShotExit() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            TemplateFilePathGenerator templateFilePathGenerator = this.templateFilePathGenerator;
            return FileUtil.isFileExist(templateFilePathGenerator != null ? templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(1, true)) : null);
        }
        if (deviceUtil.isJ18()) {
            TemplateFilePathGenerator templateFilePathGenerator2 = this.templateFilePathGenerator;
            if (!FileUtil.isFileExist(templateFilePathGenerator2 != null ? templateFilePathGenerator2.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(1, true)) : null)) {
                TemplateFilePathGenerator templateFilePathGenerator3 = this.templateFilePathGenerator;
                if (!FileUtil.isFileExist(templateFilePathGenerator3 != null ? templateFilePathGenerator3.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(2, true)) : null)) {
                    return false;
                }
            }
            return true;
        }
        if (deviceUtil.isFold()) {
            TemplateFilePathGenerator templateFilePathGenerator4 = this.templateFilePathGenerator;
            if (!FileUtil.isFileExist(templateFilePathGenerator4 != null ? templateFilePathGenerator4.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(1, true)) : null)) {
                TemplateFilePathGenerator templateFilePathGenerator5 = this.templateFilePathGenerator;
                if (!FileUtil.isFileExist(templateFilePathGenerator5 != null ? templateFilePathGenerator5.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(2, true)) : null)) {
                    TemplateFilePathGenerator templateFilePathGenerator6 = this.templateFilePathGenerator;
                    if (!FileUtil.isFileExist(templateFilePathGenerator6 != null ? templateFilePathGenerator6.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(3, true)) : null)) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (!deviceUtil.isPad()) {
            TemplateFilePathGenerator templateFilePathGenerator7 = this.templateFilePathGenerator;
            return FileUtil.isFileExist(templateFilePathGenerator7 != null ? templateFilePathGenerator7.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(1, true)) : null);
        }
        TemplateFilePathGenerator templateFilePathGenerator8 = this.templateFilePathGenerator;
        if (!FileUtil.isFileExist(templateFilePathGenerator8 != null ? templateFilePathGenerator8.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(2, true)) : null)) {
            TemplateFilePathGenerator templateFilePathGenerator9 = this.templateFilePathGenerator;
            if (!FileUtil.isFileExist(templateFilePathGenerator9 != null ? templateFilePathGenerator9.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(3, true)) : null)) {
                return false;
            }
        }
        return true;
    }

    public void comeFromCrossTower(boolean z) {
        this.isFromTower = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void createCombineEditView(EditorActivity editorActivity) {
        CombineEditView combineEditView = new CombineEditView(editorActivity, 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0);
        combineEditView.init(this, getDefaultSelect());
        View view = getView();
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.view.ViewGroup");
        ((ViewGroup) view).addView(combineEditView);
        this.combineEditView = combineEditView;
        this.lockTemplateView = combineEditView.getLockTemplateView();
        CombineEditView combineEditView2 = this.combineEditView;
        this.homeTemplateView = combineEditView2 != null ? combineEditView2.getHomeTemplateView() : null;
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.setContentDescription(getResources().getString(R.string.kg_talkback_keyguard_wallpaper_preview));
        }
        HomeTemplateView homeTemplateView = this.homeTemplateView;
        if (homeTemplateView != null) {
            homeTemplateView.setContentDescription(getResources().getString(R.string.kg_talkback_desktop_wallpaper_preview));
        }
    }

    public final void fixFollowStatus(CommonConfig commonConfig) {
        if (commonConfig == null || commonConfig.getHomeInfo().getFollowStatus() != 2) {
            return;
        }
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "image")) {
            WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            Bitmap currentWallpaper = wallpaperInfo2 != null ? wallpaperInfo2.getCurrentWallpaper() : null;
            WallpaperInfo wallpaperInfo3 = commonConfig.getHomeInfo().getWallpaperInfo();
            Bitmap currentWallpaper2 = wallpaperInfo3 != null ? wallpaperInfo3.getCurrentWallpaper() : null;
            if (currentWallpaper == null || currentWallpaper2 == null) {
                Log.e("EditFragment", "lockWallpaper = " + currentWallpaper + ",homeWallpaper = " + currentWallpaper2 + ",change follow status");
                commonConfig.getHomeInfo().setFollowStatus(0);
                return;
            }
            if (currentWallpaper.sameAs(currentWallpaper2)) {
                return;
            }
            Log.e("EditFragment", "lockWallpaper: width = " + currentWallpaper.getWidth() + ",height = " + currentWallpaper.getHeight() + " ; homeWallpaper: width = " + currentWallpaper2.getWidth() + ",height = " + currentWallpaper2.getHeight() + " , change follow status");
            commonConfig.getHomeInfo().setFollowStatus(0);
        }
    }

    public CombineEditView getCombineEditView() {
        return this.combineEditView;
    }

    @Override // com.miui.keyguard.editor.edit.base.EditCallback
    public Object getData(String str, Object... objArr) {
        return EditCallback.DefaultImpls.getData(this, str, objArr);
    }

    public int getDefaultSelect() {
        return this.defaultSelect;
    }

    public final int getMPopFontFilterPaperDialog() {
        return this.mPopFontFilterPaperDialog;
    }

    public final CommonConfig getOriginTempConfig() {
        return this.originTempConfig;
    }

    public final boolean getShouldAddHistory() {
        return this.shouldAddHistory;
    }

    public final int getTemplateStatus() {
        CombineEditView combineEditView = this.combineEditView;
        if (combineEditView != null) {
            return combineEditView.getTemplateStatus();
        }
        return 0;
    }

    public final void hidAllBottomSheet() {
        BaseClockView baseClockView;
        AutoBottomSheet wallpaperFilterAutoBottomSheet;
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        if (baseTemplateView != null && (wallpaperFilterAutoBottomSheet = baseTemplateView.getWallpaperFilterAutoBottomSheet()) != null) {
            wallpaperFilterAutoBottomSheet.dismiss();
        }
        BaseTemplateView baseTemplateView2 = this.lockTemplateView;
        if (baseTemplateView2 != null && (baseClockView = (BaseClockView) baseTemplateView2.getClockLayer()) != null) {
            baseClockView.dismissBottomSheet();
        }
        BaseTemplateView baseTemplateView3 = this.lockTemplateView;
        if (baseTemplateView3 != null) {
            baseTemplateView3.dismissCancelDialog();
        }
    }

    public final void initCombineEditView(boolean z, TemplateData templateData) {
        FragmentActivity activity = getActivity();
        EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
        if (editorActivity == null) {
            return;
        }
        boolean z2 = editorActivity instanceof CommonEditorActivity;
        if (!z || z2) {
            try {
                if (z && z2) {
                    createCombineEditView(editorActivity);
                    return;
                }
                if (!z && z2) {
                    if (templateData != null) {
                        loadCombineEditView(templateData);
                    }
                } else {
                    createCombineEditView(editorActivity);
                    if (templateData != null) {
                        loadCombineEditView(templateData);
                    }
                }
            } catch (Exception e) {
                Log.e("Keyguard-Editor-EditFragment", "initTemplateView error", e);
            }
        }
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment
    public OnBackPressedCallback instanceOnBackPressedCallback(FragmentActivity fragmentActivity) {
        Intrinsics.checkNotNullParameter(fragmentActivity, "fragmentActivity");
        return new BackPressedCallback(this, fragmentActivity);
    }

    public final boolean isDirectFinishActivityWhenBackPress() {
        FragmentActivity activity = getActivity();
        CombineEditView combineEditView = this.combineEditView;
        Integer numValueOf = combineEditView != null ? Integer.valueOf(combineEditView.getTemplateStatus()) : null;
        if (activity instanceof CommonEditorActivity) {
            if (numValueOf != null && numValueOf.intValue() == 0) {
                return true;
            }
            if (numValueOf != null && numValueOf.intValue() == -1) {
                return true;
            }
        }
        if (activity instanceof EditorActivity) {
            return ((EditorActivity) activity).isDirectFinishWhenBackPress();
        }
        return false;
    }

    public final boolean isEdited() {
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        return baseTemplateView != null && baseTemplateView.isEdited();
    }

    public final Object isInLockMode(Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getDefault(), new AnonymousClass2(null), continuation);
    }

    public final boolean isInPreviewAnimation() {
        CombineEditView combineEditView;
        CombineEditView combineEditView2 = this.combineEditView;
        if (combineEditView2 == null || !combineEditView2.isInAnim() || (combineEditView = this.combineEditView) == null || combineEditView.getTemplateStatus() != 0) {
            return false;
        }
        Log.w("EditFragment", "onCancel: is in anim");
        CombineEditView combineEditView3 = this.combineEditView;
        if (combineEditView3 != null) {
            combineEditView3.setPendingExitPreview(true);
        }
        return true;
    }

    public final boolean isUserOperation() {
        return this.userOperation;
    }

    public final void loadCombineEditView(TemplateData templateData) {
        CombineEditView combineEditView = this.combineEditView;
        if (combineEditView != null) {
            CommonConfig config = templateData.getConfig();
            if (config != null) {
                combineEditView.loadTemplate(config, templateData, templateData.getTemplateSource());
            }
            BaseTemplateView baseTemplateView = this.lockTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.setHistoryTemplate(TemplateSource.INSTANCE.isFromHistoryTemplate(templateData.getTemplateSource()));
            }
            HomeTemplateView homeTemplateView = this.homeTemplateView;
            if (homeTemplateView != null) {
                homeTemplateView.setHistoryTemplate(TemplateSource.INSTANCE.isFromHistoryTemplate(templateData.getTemplateSource()));
            }
            View view = getView();
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.view.ViewGroup");
            ((ViewGroup) view).addView(combineEditView);
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0045  */
    public final CommonConfig loadTemplateConfig(long j, String str, Bundle bundle, int i, WallpaperTypeInfo wallpaperTypeInfo) {
        CommonConfig commonConfigCheckAndLoadCurrentTemplateConfig;
        Context context = getContext();
        if (context == null) {
            return null;
        }
        TemplateApi companion = TemplateApi.Companion.getInstance(context);
        try {
            TemplateSource templateSource = TemplateSource.INSTANCE;
            if (templateSource.isFromCurrentTemplate(j)) {
                boolean z = getActivity() instanceof CommonEditorActivity;
                String str2 = this.wallpaperTarget;
                if (str2 == null) {
                    str2 = "all";
                }
                commonConfigCheckAndLoadCurrentTemplateConfig = TemplateApi.DefaultImpls.currentTemplate$default(companion, true, true, true, false, wallpaperTypeInfo, z, str2, 8, null);
                if (commonConfigCheckAndLoadCurrentTemplateConfig != null) {
                    commonConfigCheckAndLoadCurrentTemplateConfig.getLockscreenInfo().setClockExtras(bundle);
                } else {
                    commonConfigCheckAndLoadCurrentTemplateConfig = null;
                }
            } else {
                boolean zIsFromPresetTemplate = templateSource.isFromPresetTemplate(j);
                CommonConfig template = companion.getTemplate(str, zIsFromPresetTemplate, i);
                if (template != null) {
                    WallpaperInfo wallpaperInfo = template.getLockscreenInfo().getWallpaperInfo();
                    Intrinsics.checkNotNull(wallpaperInfo);
                    companion.loadTemplateWallpaper(wallpaperInfo, zIsFromPresetTemplate);
                    WallpaperInfo wallpaperInfo2 = template.getHomeInfo().getWallpaperInfo();
                    Intrinsics.checkNotNull(wallpaperInfo2);
                    companion.loadTemplateWallpaper(wallpaperInfo2, zIsFromPresetTemplate);
                    commonConfigCheckAndLoadCurrentTemplateConfig = template;
                } else {
                    commonConfigCheckAndLoadCurrentTemplateConfig = null;
                }
            }
            if (commonConfigCheckAndLoadCurrentTemplateConfig == null) {
                Log.d("EditFragment", "config is null,templateSource = " + j + " ,activity is " + (getActivity() instanceof CommonEditorActivity));
                commonConfigCheckAndLoadCurrentTemplateConfig = checkAndLoadCurrentTemplateConfig(j, bundle, wallpaperTypeInfo);
            }
            fixFollowStatus(commonConfigCheckAndLoadCurrentTemplateConfig);
            tryAutoPickColor(commonConfigCheckAndLoadCurrentTemplateConfig);
            return commonConfigCheckAndLoadCurrentTemplateConfig;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-EditFragment", "loadTemplateConfig error", e);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:80:0x018f  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final TemplateData loadTemplateData(Context context, long j, String str, Boolean bool, Bundle bundle, boolean z, int i) {
        boolean zCheckScreenShotExit;
        WallpaperTypeInfo wallpaperTypeInfoProcessWallpaperInfo;
        TemplateConfig lockscreenInfo;
        Intent intent;
        Intent intent2;
        FragmentActivity activity = getActivity();
        String stringExtra = null;
        String stringExtra2 = (activity == null || (intent2 = activity.getIntent()) == null) ? null : intent2.getStringExtra("caller");
        if (j == -1) {
            zCheckScreenShotExit = checkScreenShotExit();
            this.shouldAddHistory = (!zCheckScreenShotExit || stringExtra2 == null || stringExtra2.length() == 0 || Intrinsics.areEqual(stringExtra2, "lock") || Intrinsics.areEqual(stringExtra2, "desktop") || !Intrinsics.areEqual(this.wallpaperTarget, "all")) ? false : true;
        } else {
            zCheckScreenShotExit = true;
        }
        Log.d("EditFragment", "loadTemplateData: caller=" + stringExtra2 + " templateSource=" + j + " shouldAddHistory=" + this.shouldAddHistory + " isSmallScreenShotExit=" + zCheckScreenShotExit + " fromRecentLaunch = " + this.fromRecentLaunch + " wallpaperTarget=" + this.wallpaperTarget);
        if (stringExtra2 != null) {
            setDefaultSelect(Intrinsics.areEqual(this.wallpaperTarget, "home") ? 1 : 0);
            if (this.fromRecentLaunch) {
                wallpaperTypeInfoProcessWallpaperInfo = null;
            } else {
                WallpaperEditor wallpaperEditor = this.wallpaperEditor;
                Intent intent3 = requireActivity().getIntent();
                Intrinsics.checkNotNull(intent3);
                wallpaperTypeInfoProcessWallpaperInfo = wallpaperEditor.processWallpaperInfo(intent3, new WeakReference(requireContext()));
            }
        } else {
            wallpaperTypeInfoProcessWallpaperInfo = null;
        }
        tryFinishActivity(wallpaperTypeInfoProcessWallpaperInfo, stringExtra2);
        CommonConfig commonConfigLoadTemplateConfig = loadTemplateConfig(j, str, bundle, i, wallpaperTypeInfoProcessWallpaperInfo);
        this.originTempConfig = (CommonConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(GsonSingletonKt.getGSON_SINGLETON().toJson(commonConfigLoadTemplateConfig), CommonConfig.class);
        this.currentConfig = commonConfigLoadTemplateConfig;
        FragmentActivity activity2 = getActivity();
        if (activity2 != null && (intent = activity2.getIntent()) != null) {
            stringExtra = intent.getStringExtra("whereFrom");
        }
        if (stringExtra != null) {
            stringExtra2 = stringExtra;
        } else if (stringExtra2 == null) {
            stringExtra2 = "";
        }
        this.source = stringExtra2;
        TemplateSource templateSource = TemplateSource.INSTANCE;
        if ((templateSource.isFromHistoryTemplate(j) || templateSource.isFromCurrentTemplate(j)) && this.isFromTower) {
            if (commonConfigLoadTemplateConfig != null && (lockscreenInfo = commonConfigLoadTemplateConfig.getLockscreenInfo()) != null) {
                lockscreenInfo.setThirdPartyWallpaper(bool != null ? bool.booleanValue() : false);
            }
            this.source = "cross_match";
        }
        if (templateSource.isFromPresetTemplate(j)) {
            this.source = "cross_recommend";
        }
        if (!TextUtils.isEmpty(this.source)) {
            trackCommonEditorOpen(context, this.source, getDefaultSelect(), EditorActivity.Companion.getTackId());
            String str2 = this.source;
            switch (str2.hashCode()) {
                case -1750177296:
                    if (str2.equals("lock_negative_one")) {
                        FashionGalleryHelper.INSTANCE.setCLOSE_SOURCE(3);
                    }
                    break;
                case -1349088399:
                    if (str2.equals(am.s)) {
                        FashionGalleryHelper.INSTANCE.setCLOSE_SOURCE(4);
                    }
                    break;
                case -1129339642:
                    if (str2.equals("cross_match")) {
                        FashionGalleryHelper.INSTANCE.setCLOSE_SOURCE(1);
                    }
                    break;
                case -196315310:
                    if (str2.equals("gallery")) {
                        FashionGalleryHelper.INSTANCE.setCLOSE_SOURCE(5);
                    }
                    break;
                case -114404483:
                    if (str2.equals("cross_recommend")) {
                        FashionGalleryHelper.INSTANCE.setCLOSE_SOURCE(1);
                    }
                    break;
                case 1474694658:
                    if (str2.equals("wallpaper")) {
                        FashionGalleryHelper.INSTANCE.setCLOSE_SOURCE(MainFragment.Companion.isEnterCrossTower() ? 3 : 6);
                    }
                    break;
            }
        }
        Log.i("EditFragment", "loadTemplateData: source=" + this.source);
        return new TemplateData(j, commonConfigLoadTemplateConfig, FashionGalleryHelper.INSTANCE.isGalleryInstalledAndOpen(context), z);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 117) {
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type androidx.lifecycle.LifecycleOwner");
            EditorActivity.NavigationActionListener.DefaultImpls.onApply$default(this, null, activity, 1, null);
        } else {
            CombineEditView combineEditView = this.combineEditView;
            if (combineEditView != null) {
                combineEditView.onReceiveActivityResult(i, i2, intent);
            }
        }
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public boolean onApply(Consumer consumer, LifecycleOwner lifecycleOwner) {
        CombineEditView combineEditView = this.combineEditView;
        if (combineEditView != null && combineEditView.isAllInOneDragging()) {
            Log.w("Keyguard-Editor-EditFragment", "onApply ignored: AllInOne clock is in drag mode.");
            return true;
        }
        CombineEditView combineEditView2 = this.combineEditView;
        boolean z = false;
        if (combineEditView2 != null && !combineEditView2.onApply(this.navigationActionCallback, this.applyTransaction)) {
            z = true;
        }
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C00921(lifecycleOwner, z, null), 3, null);
        return true;
    }

    @Override // com.miui.keyguard.editor.EditorActivity.NavigationActionListener
    public boolean onCancel(Consumer consumer) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig commonConfig = this.currentConfig;
        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null && Intrinsics.areEqual(wallpaperInfo.getResourceType(), "video")) {
            BaseTemplateView baseTemplateView = this.lockTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.removeBackground(null);
            }
            HomeTemplateView homeTemplateView = this.homeTemplateView;
            if (homeTemplateView != null) {
                homeTemplateView.removeBackground(null);
            }
        }
        if (isInPreviewAnimation()) {
            return false;
        }
        CombineEditView combineEditView = this.combineEditView;
        if (combineEditView != null && combineEditView.getTemplateStatus() == 1) {
            CombineEditView combineEditView2 = this.combineEditView;
            if (combineEditView2 != null) {
                combineEditView2.animSwitchPreview();
            }
            return false;
        }
        OnBackPressedCallback onBackPressedCallback = getOnBackPressedCallback();
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackPressed();
        }
        BaseTemplateView baseTemplateView2 = this.lockTemplateView;
        if (baseTemplateView2 != null) {
            baseTemplateView2.setNavigationCallback(null);
        }
        BaseTemplateView baseTemplateView3 = this.lockTemplateView;
        if (baseTemplateView3 != null) {
            baseTemplateView3.setApplyTransition(null);
        }
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        try {
            FragmentActivity activity = getActivity();
            EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
            if (editorActivity != null) {
                editorActivity.setOnActivityConfigChangedListener(null);
            }
            BaseTemplateView baseTemplateView = this.lockTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.setEditModeChangedListener(null);
            }
            BaseTemplateView baseTemplateView2 = this.lockTemplateView;
            if (baseTemplateView2 != null) {
                baseTemplateView2.setApplyAnimationPerformer(null);
            }
            CoroutineScopeKt.cancel$default(LifecycleOwnerKt.getLifecycleScope(this), null, 1, null);
        } catch (Exception e) {
            Log.w("EditFragment", "startExitTransformerAnimation: ", e);
        }
        super.onDestroy();
        BaseTemplateView baseTemplateView3 = this.lockTemplateView;
        if (baseTemplateView3 != null) {
            baseTemplateView3.onDestroy();
        }
        BaseTemplateView baseTemplateView4 = this.lockTemplateView;
        if (baseTemplateView4 != null) {
            baseTemplateView4.resetAllBottomSheet();
        }
        HomeTemplateView homeTemplateView = this.homeTemplateView;
        if (homeTemplateView != null) {
            homeTemplateView.onDestroy();
        }
        try {
            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
            keyguardEditorMiSightUtil.wallpaperDisplayFinish();
            keyguardEditorMiSightUtil.hierarchySessionFinish();
        } catch (Throwable unused) {
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.EditModeChangedListener
    public void onEditModeChanged(int i, int i2) {
        KeyEventDispatcher.Component activity = getActivity();
        if (activity instanceof EditModeChangedListener) {
            ((EditModeChangedListener) activity).onEditModeChanged(i, i2);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        CombinedWallpaperView combinedWallpaperView;
        super.onResume();
        this.wallpaperEditor.resetPickerLaunchingState();
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        if (baseTemplateView != null && (combinedWallpaperView = (CombinedWallpaperView) baseTemplateView.getWallpaperLayer()) != null) {
            combinedWallpaperView.requestRender();
        }
        BaseTemplateView baseTemplateView2 = this.lockTemplateView;
        if (baseTemplateView2 != null) {
            baseTemplateView2.checkThemeCTA();
        }
        updateWallpaperFilterForGallery();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.onStart();
        }
        HomeTemplateView homeTemplateView = this.homeTemplateView;
        if (homeTemplateView != null) {
            homeTemplateView.onStart();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.onStop();
        }
        HomeTemplateView homeTemplateView = this.homeTemplateView;
        if (homeTemplateView != null) {
            homeTemplateView.onStop();
        }
        try {
            KeyguardEditorMiSightUtil.INSTANCE.cancelFirstFrameTimer();
        } catch (Throwable unused) {
        }
    }

    @Override // com.miui.keyguard.editor.EditorActivity.UserOperationListener
    public void onUserOperation() {
        this.userOperation = true;
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        String stringExtra;
        Intent intent;
        Intent intent2;
        Window window;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        Context context = getContext();
        String stringExtra2 = null;
        this.templateFilePathGenerator = context != null ? new TemplateFilePathGenerator(context) : null;
        FragmentActivity activity = getActivity();
        if (activity != null && (window = activity.getWindow()) != null) {
            window.clearFlags(131072);
        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        String string = arguments.getString("argConfigPath", "");
        long j = arguments.getLong("argTemplateSource", -2L);
        boolean z = arguments.getBoolean("argThirdPartyWallpaper", false);
        Bundle bundle2 = arguments.getBundle("argClockExtras");
        boolean z2 = arguments.getBoolean("argAutoDownload");
        int i = arguments.getInt("hideFlag");
        FragmentActivity activity2 = getActivity();
        if (activity2 != null && (intent2 = activity2.getIntent()) != null) {
            stringExtra2 = intent2.getStringExtra("caller");
        }
        this.fromRecentLaunch = arguments.getBoolean("argFromRecentLaunch", false);
        FragmentActivity activity3 = getActivity();
        if (activity3 == null || (intent = activity3.getIntent()) == null || (stringExtra = intent.getStringExtra("wallpaperTarget")) == null) {
            stringExtra = Intrinsics.areEqual(stringExtra2, "desktop") ? "home" : "all";
        }
        this.wallpaperTarget = stringExtra;
        setDefaultSelect(Intrinsics.areEqual(stringExtra, "home") ? 1 : 0);
        beforeTemplateViewAttach(arguments);
        CoroutineScope coroutineScope = EditFragmentViewModel.Companion.getCoroutineScope(this);
        if (coroutineScope != null) {
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C00932(view, j, string, z, bundle2, z2, i, null), 3, null);
        }
        initFashionGalleryCompat();
        popWallPaperDialog();
        popFontFilterPaperDialog();
    }

    public final void performBackPress(Function0 function0) {
        CombineEditView combineEditView;
        HomeTemplateView homeTemplateView;
        BaseClockView baseClockView;
        AutoBottomSheet wallpaperFilterAutoBottomSheet;
        Log.i("Keyguard-Editor-EditFragment", "performBackPress");
        if (isDirectFinishActivityWhenBackPress()) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.finish();
            }
        } else if ((getContext() instanceof TransitionLayer) && (combineEditView = this.combineEditView) != null) {
            Log.i("Keyguard-Editor-EditFragment", "performBackPress: getTemplateStatus = " + getTemplateStatus());
            int templateStatus = getTemplateStatus();
            if (templateStatus == 0) {
                Object context = combineEditView.getContext();
                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.base.TransitionLayer");
                TransitionPerformer transitionPerformer = ((TransitionLayer) context).getTransitionPerformer();
                Fragment fragmentFindFragmentByTag = getParentFragmentManager().findFragmentByTag("EDITOR_MAIN_FRAGMENT");
                if (fragmentFindFragmentByTag != null) {
                    getParentFragmentManager().beginTransaction().show(fragmentFindFragmentByTag).commitNow();
                    BaseTemplateView baseTemplateView = this.lockTemplateView;
                    if (baseTemplateView != null) {
                        baseTemplateView.exitWithAnimation();
                    }
                    HomeTemplateView homeTemplateView2 = this.homeTemplateView;
                    if (homeTemplateView2 != null) {
                        homeTemplateView2.exitWithAnimation();
                    }
                    BaseTemplateView baseTemplateView2 = this.lockTemplateView;
                    if (baseTemplateView2 != null && (homeTemplateView = this.homeTemplateView) != null) {
                        CombineEditView combineEditView2 = this.combineEditView;
                        if (combineEditView2 != null) {
                            combineEditView2.onCancel();
                        }
                        transitionPerformer.performBackwardTransition(baseTemplateView2, homeTemplateView, function0);
                    }
                    transitionPerformer.performSlideIn();
                }
            } else if (templateStatus == 1) {
                combineEditView.animSwitchPreview();
            } else if (templateStatus == 2) {
                BaseTemplateView baseTemplateView3 = this.lockTemplateView;
                if (baseTemplateView3 != null && (wallpaperFilterAutoBottomSheet = baseTemplateView3.getWallpaperFilterAutoBottomSheet()) != null) {
                    wallpaperFilterAutoBottomSheet.dismiss();
                }
                BaseTemplateView baseTemplateView4 = this.lockTemplateView;
                if (baseTemplateView4 != null && (baseClockView = (BaseClockView) baseTemplateView4.getClockLayer()) != null) {
                    baseClockView.dismissBottomSheet();
                }
            }
        }
        BaseTemplateView baseTemplateView5 = this.lockTemplateView;
        if (baseTemplateView5 == null || baseTemplateView5.isEdited()) {
            return;
        }
        trackOtherQuitEdit();
    }

    public final void popFontFilterPaperDialog() {
        int iIntValue;
        Integer intOrNull;
        String string;
        Integer intOrNull2;
        Intent intent;
        FragmentActivity activity = getActivity();
        Bundle extras = (activity == null || (intent = activity.getIntent()) == null) ? null : intent.getExtras();
        Object obj = extras != null ? extras.get("is_pop_font_filter_dialog") : null;
        int iIntValue2 = -1;
        if (obj instanceof Integer) {
            iIntValue = ((Number) obj).intValue();
        } else {
            iIntValue = (!(obj instanceof String) || (intOrNull = StringsKt.toIntOrNull((String) obj)) == null) ? -1 : intOrNull.intValue();
        }
        this.mPopFontFilterPaperDialog = iIntValue;
        if (iIntValue == -1) {
            if (extras != null && (string = extras.getString("is_pop_font_filter_dialog_value")) != null && (intOrNull2 = StringsKt.toIntOrNull(string)) != null) {
                iIntValue2 = intOrNull2.intValue();
            }
            this.mPopFontFilterPaperDialog = iIntValue2;
        }
    }

    public final void popWallPaperDialog() {
        Intent intent;
        FragmentActivity activity = getActivity();
        String stringExtra = (activity == null || (intent = activity.getIntent()) == null) ? null : intent.getStringExtra("is_pop_wallpaper_dialog");
        Intent intent2 = new Intent();
        intent2.setAction("miui.intent.action.THEME_WALLPAPER_PICKER_PAGE");
        if (Intrinsics.areEqual(stringExtra, "is_pop")) {
            startActivity(intent2);
        }
    }

    public void setDefaultSelect(int i) {
        this.defaultSelect = i;
    }

    @Override // com.miui.keyguard.editor.base.ExitAnimationPerformer
    public void startExitTransformerAnimation(boolean z, TemplateApplier.ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer) {
        TemplateRadiusAnimationListener templateRadiusAnimationListener;
        FragmentActivity activity = getActivity();
        if (activity == null) {
            Log.w("Keyguard-Editor-EditFragment", "startExitTransformerAnimation activity is null");
            return;
        }
        if (this.lockTemplateView == null) {
            Log.w("Keyguard-Editor-EditFragment", "startExitTransformerAnimation templateView is null");
            return;
        }
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(1467L);
        Log.i("Keyguard-Editor-EditFragment", "startExitTransformerAnimation");
        CombineEditView combineEditView = this.combineEditView;
        Intrinsics.checkNotNull(combineEditView);
        ExitAnimationPerformer.DefaultImpls.startExitTransformerAnimation$default(combineEditView, z, null, null, 6, null);
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this);
        boolean z2 = viewModel != null && viewModel.getSelectTemplate() == 0;
        View viewFindViewById = activity.findViewById(R.id.kg_editor_button_bar);
        if (viewFindViewById != null) {
            Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.edit.EditFragment$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return EditFragment.startExitTransformerAnimation$lambda$41(this.f$0);
                }
            };
            if (z2) {
                BaseTemplateView baseTemplateView = this.lockTemplateView;
                Intrinsics.checkNotNull(baseTemplateView);
                float dimension = getResources().getDimension(R.dimen.kg_template_view_radius);
                String name = ViewProperty.ALPHA.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                templateRadiusAnimationListener = new TemplateRadiusAnimationListener(baseTemplateView, dimension, false, name);
            } else {
                templateRadiusAnimationListener = null;
            }
            TemplateApplierAnimatorKt.startAlphaAnimation(viewFindViewById, function0, templateRadiusAnimationListener);
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x008b  */
    public final void trackApplyWallpaper() {
        BaseTemplateView baseTemplateView;
        HomeTemplateView homeTemplateView;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo;
        TemplateConfig lockscreenInfo2;
        String resourceType;
        Context context;
        Intent intent;
        Context context2;
        TemplateConfig lockscreenInfo3;
        HomeTemplateView homeTemplateView2;
        Intent intent2;
        Intent intent3;
        if (TextUtils.isEmpty(this.source)) {
            return;
        }
        FragmentActivity activity = getActivity();
        Unit unit = null;
        String stringExtra = (activity == null || (intent3 = activity.getIntent()) == null) ? null : intent3.getStringExtra("caller");
        FragmentActivity activity2 = getActivity();
        String stringExtra2 = (activity2 == null || (intent2 = activity2.getIntent()) == null) ? null : intent2.getStringExtra("wallpaperTarget");
        BaseTemplateView baseTemplateView2 = this.lockTemplateView;
        String str = "apply_lockscreen_and_home_wallpaper";
        int i = 1;
        if ((baseTemplateView2 == null || !baseTemplateView2.isWallpaperChanged() || (homeTemplateView2 = this.homeTemplateView) == null || !homeTemplateView2.isWallpaperChanged()) && !(((baseTemplateView = this.lockTemplateView) != null && baseTemplateView.isWallpaperChanged() && TextUtils.equals(stringExtra2, "home")) || (((homeTemplateView = this.homeTemplateView) != null && homeTemplateView.isWallpaperChanged() && TextUtils.equals(stringExtra2, "lock")) || TextUtils.equals(stringExtra2, "all")))) {
            BaseTemplateView baseTemplateView3 = this.lockTemplateView;
            if ((baseTemplateView3 == null || !baseTemplateView3.isWallpaperChanged()) && !TextUtils.equals(stringExtra2, "lock")) {
                HomeTemplateView homeTemplateView3 = this.homeTemplateView;
                if ((homeTemplateView3 != null && homeTemplateView3.isWallpaperChanged()) || TextUtils.equals(stringExtra2, "home") || TextUtils.equals(stringExtra, "desktop")) {
                    CommonConfig commonConfig = this.currentConfig;
                    wallpaperInfo = (commonConfig == null || (homeInfo = commonConfig.getHomeInfo()) == null) ? null : homeInfo.getWallpaperInfo();
                    str = "apply_home_wallpaper";
                } else {
                    CommonConfig commonConfig2 = this.currentConfig;
                    if (commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null) {
                        wallpaperInfo = null;
                    } else {
                        wallpaperInfo = lockscreenInfo.getWallpaperInfo();
                    }
                }
            } else {
                CommonConfig commonConfig3 = this.currentConfig;
                wallpaperInfo = (commonConfig3 == null || (lockscreenInfo2 = commonConfig3.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo();
                str = "apply_lockscreen_wallpaper";
            }
        } else {
            CommonConfig commonConfig4 = this.currentConfig;
            if (commonConfig4 == null || (lockscreenInfo3 = commonConfig4.getLockscreenInfo()) == null) {
                wallpaperInfo = null;
            } else {
                wallpaperInfo = lockscreenInfo3.getWallpaperInfo();
            }
        }
        String str2 = str;
        Boolean boolValueOf = wallpaperInfo != null ? Boolean.valueOf(wallpaperInfo.isAiGenerated()) : null;
        if (wallpaperInfo == null || (resourceType = wallpaperInfo.getResourceType()) == null) {
            resourceType = "";
        }
        int videoType = wallpaperInfo != null ? wallpaperInfo.getVideoType() : -1;
        if (videoType == 2 || videoType == 3) {
            resourceType = "livephoto";
        }
        String str3 = "video";
        if (wallpaperInfo == null || !wallpaperInfo.isDepthVideo()) {
            Boolean bool = Boolean.TRUE;
            if (Intrinsics.areEqual(boolValueOf, bool) && TextUtils.equals(resourceType, "video")) {
                i = 4;
            } else if (Intrinsics.areEqual(boolValueOf, bool) && TextUtils.equals(resourceType, "image")) {
                i = 5;
            } else {
                str3 = resourceType;
                i = videoType;
            }
            str3 = resourceType;
        }
        int i2 = (TextUtils.equals(str3, "image") && i == 0) ? -1 : i;
        if (!TextUtils.equals(stringExtra, "wallpaper") && !TextUtils.equals(stringExtra, "store") && !TextUtils.equals(stringExtra, "dynamic") && !TextUtils.equals(stringExtra, "desktop") && !TextUtils.equals(stringExtra, am.s)) {
            if (TextUtils.isEmpty(this.source) || (context2 = getContext()) == null) {
                return;
            }
            TrackHelper.trackApplyWallpaperOther$default(TrackHelper.INSTANCE, context2, str2, this.source, str3, i2, null, 32, null);
            Unit unit2 = Unit.INSTANCE;
            return;
        }
        FragmentActivity activity3 = getActivity();
        AodTrackMapData aodTrackMapData = (activity3 == null || (intent = activity3.getIntent()) == null) ? null : (AodTrackMapData) intent.getParcelableExtra("aod_track_id_params");
        if (aodTrackMapData != null) {
            Context context3 = getContext();
            if (context3 != null) {
                int i3 = i2;
                String str4 = str3;
                TrackHelper.INSTANCE.trackApplyWallpaper(context3, str2, aodTrackMapData, this.source, str4, i3, (64 & 64) != 0 ? "" : null);
                str3 = str4;
                i2 = i3;
                unit = Unit.INSTANCE;
            }
            if (unit != null) {
                return;
            }
        }
        if (TextUtils.isEmpty(this.source) || (context = getContext()) == null) {
            return;
        }
        TrackHelper.trackApplyWallpaperOther$default(TrackHelper.INSTANCE, context, str2, this.source, str3, i2, null, 32, null);
        Unit unit3 = Unit.INSTANCE;
    }

    public final void trackCommonEditorOpen(Context context, String str, int i, String str2) {
        TrackHelper.INSTANCE.trackCommonEditorOpen(context, str, i, str2);
    }

    public final void trackOtherQuitEdit() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            TrackHelper.INSTANCE.trackQuitEditMode(activity, "其他", null, null, null, false, false, EditorActivity.Companion.getTackId(), (3840 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? "未调整" : null, (3840 & 512) != 0 ? -1 : 0, (3840 & 1024) != 0 ? -1 : 0, (3840 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? 0 : 0);
        }
    }

    public final void tryAutoPickColor(CommonConfig commonConfig) {
        getContext();
    }

    public final void tryFinishActivity(WallpaperTypeInfo wallpaperTypeInfo, String str) {
        if (wallpaperTypeInfo != null && wallpaperTypeInfo.isAiGenerated() && (getActivity() instanceof CommonEditorActivity) && Intrinsics.areEqual(str, "ai_generate_notification")) {
            Intent intent = new Intent("com.miui.aod.action_finish_editor_activity");
            Context context = getContext();
            if (context != null) {
                context.sendBroadcast(intent);
            }
        }
    }

    public final void updateWallpaperFilterForGallery() {
        CoroutineScope coroutineScope;
        Context context = getContext();
        if (context == null || (coroutineScope = EditFragmentViewModel.Companion.getCoroutineScope(this)) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new EditFragment$updateWallpaperFilterForGallery$1$1(this, context, null), 3, null);
    }

    public final boolean useCurrentLockWallpaper() {
        Intent intent;
        Intent intent2;
        Intent intent3;
        FragmentActivity activity = getActivity();
        EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
        String stringExtra = (editorActivity == null || (intent3 = editorActivity.getIntent()) == null) ? null : intent3.getStringExtra("caller");
        FragmentActivity activity2 = getActivity();
        EditorActivity editorActivity2 = activity2 instanceof EditorActivity ? (EditorActivity) activity2 : null;
        boolean z = (editorActivity2 == null || (intent2 = editorActivity2.getIntent()) == null || !intent2.getBooleanExtra("fromFrontDialog", false)) ? false : true;
        FragmentActivity activity3 = getActivity();
        EditorActivity editorActivity3 = activity3 instanceof EditorActivity ? (EditorActivity) activity3 : null;
        return (!z && ArraysKt.contains(new String[]{null, "lock", "desktop"}, stringExtra)) || (z && Intrinsics.areEqual((editorActivity3 == null || (intent = editorActivity3.getIntent()) == null) ? null : intent.getStringExtra("wallpaperTarget"), "home"));
    }
}

package com.miui.keyguard.editor.edit;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.StatusRecord;
import com.miui.keyguard.editor.data.template.TemplateChangeRecorder;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.TemplateButtonBar;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.homepage.bean.GalleryStatusBean;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.TemplateSource;
import com.miui.keyguard.editor.view.KgVisualCheckBox;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
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
    */
/* JADX INFO: compiled from: EditFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.EditFragment$updateWallpaperFilterForGallery$1$1", f = "EditFragment.kt", l = {1334}, m = "invokeSuspend")
public final class EditFragment$updateWallpaperFilterForGallery$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $it;
    public int label;
    public final /* synthetic */ EditFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditFragment$updateWallpaperFilterForGallery$1$1(EditFragment editFragment, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = editFragment;
        this.$it = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditFragment$updateWallpaperFilterForGallery$1$1(this.this$0, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((EditFragment$updateWallpaperFilterForGallery$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0077  */
    /* JADX WARN: Code duplicated, block: B:30:0x00a7  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        BaseTemplateView baseTemplateView;
        Integer loopType;
        boolean zIsLoop;
        CommonConfig commonConfigTemplateConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        ExtraInfo extraInfo;
        KgVisualCheckBox filterButton;
        TemplateChangeRecorder templateChangeRecorder;
        KgVisualCheckBox filterButton2;
        TemplateChangeRecorder templateChangeRecorder2;
        KgVisualCheckBox filterButton3;
        IntWrapper hierarchyStatus;
        IntWrapper wallpaperFilterId;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        StatusRecord lastStatusRecord = null;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineDispatcher io2 = Dispatchers.getIO();
            EditFragment$updateWallpaperFilterForGallery$1$1$galleryStatusBean$1 editFragment$updateWallpaperFilterForGallery$1$1$galleryStatusBean$1 = new EditFragment$updateWallpaperFilterForGallery$1$1$galleryStatusBean$1(this.$it, null);
            this.label = 1;
            obj = BuildersKt.withContext(io2, editFragment$updateWallpaperFilterForGallery$1$1$galleryStatusBean$1, this);
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
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isInternationalBuild()) {
            zIsLoop = FashionGalleryHelper.INSTANCE.isLoopTemplate(galleryStatusBean.getLoopType());
        } else if (this.this$0.lockTemplateView != null) {
            TemplateSource templateSource = TemplateSource.INSTANCE;
            BaseTemplateView baseTemplateView2 = this.this$0.lockTemplateView;
            Intrinsics.checkNotNull(baseTemplateView2);
            if (templateSource.isFromCurrentTemplate(baseTemplateView2.getTemplateSource())) {
                FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
                Context contextRequireContext = this.this$0.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                zIsLoop = fashionGalleryHelper.isLoop(contextRequireContext, true, galleryStatusBean.getLoopType());
            } else {
                FashionGalleryHelper fashionGalleryHelper2 = FashionGalleryHelper.INSTANCE;
                Context contextRequireContext2 = this.this$0.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
                baseTemplateView = this.this$0.lockTemplateView;
                if (baseTemplateView != null || (commonConfigTemplateConfig = baseTemplateView.templateConfig()) == null || (lockscreenInfo = commonConfigTemplateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || (extraInfo = wallpaperInfo.getExtraInfo()) == null) {
                    loopType = null;
                } else {
                    loopType = extraInfo.getLoopType();
                }
                zIsLoop = fashionGalleryHelper2.isLoop(contextRequireContext2, true, loopType);
            }
        } else {
            FashionGalleryHelper fashionGalleryHelper3 = FashionGalleryHelper.INSTANCE;
            Context contextRequireContext3 = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext3, "requireContext(...)");
            baseTemplateView = this.this$0.lockTemplateView;
            if (baseTemplateView != null) {
                loopType = null;
            } else {
                loopType = null;
            }
            zIsLoop = fashionGalleryHelper3.isLoop(contextRequireContext3, true, loopType);
        }
        Log.d("EditFragment", "updateWallpaperFilterForGallery: isGalleryLockLoop=" + zIsLoop);
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.this$0);
        BaseTemplateView baseTemplateView3 = this.this$0.lockTemplateView;
        TemplateButtonBar primaryButtonBar = baseTemplateView3 != null ? baseTemplateView3.getPrimaryButtonBar() : null;
        if (zIsLoop) {
            BaseTemplateView baseTemplateView4 = this.this$0.lockTemplateView;
            if (baseTemplateView4 != null && !baseTemplateView4.isGalleryOpened()) {
                DeviceScreenshotHelper.INSTANCE.setFirstScreenshotCacheInvalid(true);
                int intValue = (viewModel == null || (wallpaperFilterId = viewModel.getWallpaperFilterId()) == null) ? 0 : wallpaperFilterId.getIntValue();
                int intValue2 = (viewModel == null || (hierarchyStatus = viewModel.getHierarchyStatus()) == null) ? 0 : hierarchyStatus.getIntValue();
                StatusRecord statusRecord = new StatusRecord(null, null, null, 7, null);
                if (intValue != 0) {
                    Log.d("EditFragment", "updateWallpaperFilterForGallery: reset wallpaper filter");
                    if (viewModel != null) {
                        EditFragmentViewModel.updateWallpaperFilterId$default(viewModel, 0, false, 2, null);
                    }
                }
                if (deviceUtil.isInternationalBuild()) {
                    if (primaryButtonBar != null && (filterButton3 = primaryButtonBar.getFilterButton()) != null) {
                        filterButton3.setVisibility(8);
                    }
                    if (intValue2 != 2) {
                        statusRecord.setHierarchyStatus(Boxing.boxInt(intValue2));
                        if (viewModel != null) {
                            EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 14, null);
                        }
                    }
                } else {
                    if (primaryButtonBar != null && (filterButton2 = primaryButtonBar.getFilterButton()) != null) {
                        filterButton2.disable();
                    }
                    if (intValue2 != 0) {
                        statusRecord.setHierarchyStatus(Boxing.boxInt(intValue2));
                        if (viewModel != null) {
                            EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
                        }
                    }
                }
                if (statusRecord.isNotEmpty() && viewModel != null && (templateChangeRecorder2 = viewModel.getTemplateChangeRecorder()) != null) {
                    templateChangeRecorder2.putLastStatusRecord(0, statusRecord);
                }
                BaseTemplateView baseTemplateView5 = this.this$0.lockTemplateView;
                if (baseTemplateView5 != null) {
                    baseTemplateView5.setGalleryOpened(true);
                }
            }
        } else {
            BaseTemplateView baseTemplateView6 = this.this$0.lockTemplateView;
            if (baseTemplateView6 != null && baseTemplateView6.isGalleryOpened()) {
                DeviceScreenshotHelper.INSTANCE.setFirstScreenshotCacheInvalid(true);
                if (viewModel != null && (templateChangeRecorder = viewModel.getTemplateChangeRecorder()) != null) {
                    lastStatusRecord = templateChangeRecorder.getLastStatusRecord(0);
                }
                if (primaryButtonBar != null && (filterButton = primaryButtonBar.getFilterButton()) != null) {
                    filterButton.enable();
                }
                if (lastStatusRecord != null && lastStatusRecord.getHierarchyStatus() != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 14, null);
                }
                BaseTemplateView baseTemplateView7 = this.this$0.lockTemplateView;
                if (baseTemplateView7 != null) {
                    baseTemplateView7.setGalleryOpened(deviceUtil.isInternationalBuild() ? FashionGalleryHelper.INSTANCE.obtainFashionGalleryOpened(this.$it) : false);
                }
            }
        }
        return Unit.INSTANCE;
    }
}

package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelKt;
import com.miui.fastplayer.FastPlayer;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.LargeScreenHierarchyEnable;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.data.template.StatusRecord;
import com.miui.keyguard.editor.data.template.TemplateChangeRecorder;
import com.miui.keyguard.editor.data.util.DataUtilKt;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.aiwapper.AIGenerateFinishCallback;
import com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.GLTextureView;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.VideoTextureView;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperEditParam;
import com.miui.keyguard.editor.homepage.util.CrossListFolmeUtil;
import com.miui.keyguard.editor.homepage.view.EditorLoadingView;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.DepthVideoParam;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.HierarchyImageBrightAnimController;
import com.miui.keyguard.editor.utils.OnHierarchyEnableChangeListener;
import com.miui.keyguard.editor.utils.SPUtilKt;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.segment.CacheInfo;
import com.miui.keyguard.editor.utils.segment.ImageSegmentCache;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.view.AlertDialogBuilder;
import com.miui.keyguard.editor.view.CenterCropDrawable;
import com.miui.keyguard.editor.view.KgVisualCheckBox;
import com.miui.keyguard.editor.view.WallpaperFilterView;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.animation.listener.TransitionListener;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.AppCompatActivity;
import miuix.visual.check.BorderLayout;
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
/* JADX INFO: compiled from: EffectsTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EffectsTemplateView extends BaseTemplateView {
    public AIWallPaperManager aiWallPaperManager;
    public FastPlayer fastPlayer;
    public View hierarchyLoadingView;
    public final ImageSegmentCache segmentCache;
    public ImageSegmentEditor segmentEditor;
    public Boolean shouldShowEffectsTip;
    public WallpaperTypeInfo wallpaperTypeInfo;
    public WeakReference weakContext;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.EffectsTemplateView$onEdited$9, reason: invalid class name */
    /* JADX INFO: compiled from: EffectsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$onEdited$9", f = "EffectsTemplateView.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass9 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ EditFragmentViewModel $editViewModel;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass9(EditFragmentViewModel editFragmentViewModel, Continuation continuation) {
            super(2, continuation);
            this.$editViewModel = editFragmentViewModel;
        }

        public static final Unit invokeSuspend$lambda$1$lambda$0(EffectsTemplateView effectsTemplateView, SegmentInfo segmentInfo) {
            effectsTemplateView.onHierarchySelected(segmentInfo, true);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EffectsTemplateView.this.new AnonymousClass9(this.$editViewModel, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass9) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            TemplateChangeRecorder templateChangeRecorder;
            TemplateConfig lockscreenInfo;
            WallpaperInfo wallpaperInfo;
            HierarchyImageAvoidController avoidController;
            TemplateConfig lockscreenInfo2;
            WallpaperInfo wallpaperInfo2;
            TemplateConfig lockscreenInfo3;
            WallpaperInfo wallpaperInfo3;
            TemplateConfig lockscreenInfo4;
            WallpaperInfo wallpaperInfo4;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            EffectsTemplateView.refreshGlassFilterEnable$default(EffectsTemplateView.this, null, 1, null);
            CommonConfig templateConfig = EffectsTemplateView.this.getTemplateConfig();
            int magicType = (templateConfig == null || (lockscreenInfo4 = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) == null) ? 0 : wallpaperInfo4.getMagicType();
            Log.i("Keyguard-Editor-EffectsTemplateView", "VIDEO_TO_IMAGE: restoredMagicType=" + magicType);
            CommonConfig templateConfig2 = EffectsTemplateView.this.getTemplateConfig();
            if (((templateConfig2 == null || (lockscreenInfo3 = templateConfig2.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getSubjectBitmap()) == null) {
                IWallpaperLayer wallpaperLayer = EffectsTemplateView.this.getWallpaperLayer();
                if (wallpaperLayer != null) {
                    wallpaperLayer.switchGlassShader(magicType, null);
                }
                EditFragmentViewModel editFragmentViewModel = this.$editViewModel;
                if (editFragmentViewModel != null) {
                    EditFragmentViewModel.reloadTemplateConfigForWallpaperFilter$default(editFragmentViewModel, EffectsTemplateView.this.getTemplateConfig(), false, 2, null);
                }
                EditFragmentViewModel editFragmentViewModel2 = this.$editViewModel;
                if (editFragmentViewModel2 != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel2, 0, false, true, false, 10, null);
                }
                return Unit.INSTANCE;
            }
            CommonConfig templateConfig3 = EffectsTemplateView.this.getTemplateConfig();
            Bitmap subjectBitmap = (templateConfig3 == null || (lockscreenInfo2 = templateConfig3.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getSubjectBitmap();
            Intrinsics.checkNotNull(subjectBitmap);
            final SegmentInfo segmentInfo = new SegmentInfo(0L, subjectBitmap, null, 1, null);
            ImageSegmentEditor segmentEditor = EffectsTemplateView.this.getSegmentEditor();
            if (segmentEditor != null) {
                segmentEditor.setSubjectInfo(segmentInfo);
            }
            HierarchyImageView hierarchyImageView = (HierarchyImageView) EffectsTemplateView.this.getHierarchyLayer();
            if (hierarchyImageView != null && (avoidController = hierarchyImageView.getAvoidController()) != null) {
                avoidController.setUserOpenHierarchy(true);
            }
            if (EffectsTemplateView.this.supportFilter()) {
                CommonConfig templateConfig4 = EffectsTemplateView.this.getTemplateConfig();
                if (templateConfig4 != null && (lockscreenInfo = templateConfig4.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                    final EffectsTemplateView effectsTemplateView = EffectsTemplateView.this;
                    MagicType magicType2 = MagicType.INSTANCE;
                    if (magicType2.isShader(wallpaperInfo.getMagicType()) || (magicType2.isOrigin(wallpaperInfo.getMagicType()) && wallpaperInfo.getEnableBlur())) {
                        effectsTemplateView.showEffectsTipDialog(new Function0() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$onEdited$9$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return EffectsTemplateView.AnonymousClass9.invokeSuspend$lambda$1$lambda$0(effectsTemplateView, segmentInfo);
                            }
                        });
                    } else {
                        effectsTemplateView.onHierarchySelected(segmentInfo, true);
                    }
                }
            } else {
                EffectsTemplateView.onHierarchySelected$default(EffectsTemplateView.this, segmentInfo, false, 2, null);
            }
            if (EffectsTemplateView.this.isGalleryOpened()) {
                EditFragmentViewModel editFragmentViewModel3 = this.$editViewModel;
                if (editFragmentViewModel3 != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel3, 0, false, true, false, 10, null);
                }
            } else if (EffectsTemplateView.this.shouldEnableHierarchy()) {
                EditFragmentViewModel editFragmentViewModel4 = this.$editViewModel;
                if (editFragmentViewModel4 != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel4, 1, false, true, false, 10, null);
                }
            } else {
                EditFragmentViewModel editFragmentViewModel5 = this.$editViewModel;
                if (editFragmentViewModel5 != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel5, 2, false, true, false, 10, null);
                }
            }
            EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(EffectsTemplateView.this.getEditFragment());
            if (viewModel != null && (templateChangeRecorder = viewModel.getTemplateChangeRecorder()) != null) {
                templateChangeRecorder.clearAllRecord();
            }
            EffectsTemplateView.updateAiGenerateBtnStatus$default(EffectsTemplateView.this, false, 1, null);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.EffectsTemplateView$onGlassFilterSelected$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EffectsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$onGlassFilterSelected$1", f = "EffectsTemplateView.kt", l = {1123}, m = "invokeSuspend")
    public static final class C01121 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ int $magicType;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01121(int i, Continuation continuation) {
            super(2, continuation);
            this.$magicType = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EffectsTemplateView.this.new C01121(this.$magicType, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01121) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            TemplateConfig lockscreenInfo;
            WallpaperInfo wallpaperInfo;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                EffectsTemplateView$onGlassFilterSelected$1$depth$1 effectsTemplateView$onGlassFilterSelected$1$depth$1 = new EffectsTemplateView$onGlassFilterSelected$1$depth$1(EffectsTemplateView.this, null);
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, effectsTemplateView$onGlassFilterSelected$1$depth$1, this);
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
            CommonConfig templateConfig = EffectsTemplateView.this.getTemplateConfig();
            if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.setFrontBackBitmap(bitmap);
            }
            IWallpaperLayer wallpaperLayer = EffectsTemplateView.this.getWallpaperLayer();
            if (wallpaperLayer != null) {
                wallpaperLayer.switchGlassShader(this.$magicType, new WallpaperEditParam(bitmap));
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.EffectsTemplateView$onWallpaperChangedWhenGalleryOpened$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EffectsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$onWallpaperChangedWhenGalleryOpened$1", f = "EffectsTemplateView.kt", l = {1458}, m = "invokeSuspend")
    public static final class C01131 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ WallpaperTypeInfo $info;
        public int label;
        public final /* synthetic */ EffectsTemplateView this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01131(WallpaperTypeInfo wallpaperTypeInfo, EffectsTemplateView effectsTemplateView, Continuation continuation) {
            super(2, continuation);
            this.$info = wallpaperTypeInfo;
            this.this$0 = effectsTemplateView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01131(this.$info, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01131) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            TemplateConfig lockscreenInfo;
            WallpaperInfo wallpaperInfo;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                EffectsTemplateView$onWallpaperChangedWhenGalleryOpened$1$b$1 effectsTemplateView$onWallpaperChangedWhenGalleryOpened$1$b$1 = new EffectsTemplateView$onWallpaperChangedWhenGalleryOpened$1$b$1(this.$info, null);
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, effectsTemplateView$onWallpaperChangedWhenGalleryOpened$1$b$1, this);
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
                EffectsTemplateView effectsTemplateView = this.this$0;
                WeakReference weakReference = effectsTemplateView.weakContext;
                EffectsTemplateView effectsTemplateView2 = weakReference != null ? (EffectsTemplateView) weakReference.get() : null;
                if (effectsTemplateView2 != null) {
                    CommonConfig templateConfig = effectsTemplateView.getTemplateConfig();
                    if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                        wallpaperInfo.setSupportSubject(true);
                    }
                    SegmentInfo segmentInfo = new SegmentInfo(0L, bitmap, null, 1, null);
                    ImageSegmentEditor segmentEditor = effectsTemplateView2.getSegmentEditor();
                    if (segmentEditor != null) {
                        segmentEditor.setSubjectInfo(segmentInfo);
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.EffectsTemplateView$processHierarchy$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EffectsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$processHierarchy$1", f = "EffectsTemplateView.kt", l = {1331}, m = "invokeSuspend")
    public static final class C01141 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $doGlass;
        public final /* synthetic */ boolean $fromInit;
        public final /* synthetic */ WallpaperTypeInfo $info;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01141(boolean z, WallpaperTypeInfo wallpaperTypeInfo, boolean z2, Continuation continuation) {
            super(2, continuation);
            this.$fromInit = z;
            this.$info = wallpaperTypeInfo;
            this.$doGlass = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EffectsTemplateView.this.new C01141(this.$fromInit, this.$info, this.$doGlass, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01141) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object objWithContext;
            TemplateConfig lockscreenInfo;
            WallpaperInfo wallpaperInfo;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                EffectsTemplateView$processHierarchy$1$bitmap$1 effectsTemplateView$processHierarchy$1$bitmap$1 = new EffectsTemplateView$processHierarchy$1$bitmap$1(this.$info, null);
                this.label = 1;
                objWithContext = BuildersKt.withContext(coroutineDispatcher, effectsTemplateView$processHierarchy$1$bitmap$1, this);
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
            Bitmap bitmap = (Bitmap) objWithContext;
            WeakReference weakReference = EffectsTemplateView.this.weakContext;
            EffectsTemplateView effectsTemplateView = weakReference != null ? (EffectsTemplateView) weakReference.get() : null;
            if (effectsTemplateView != null) {
                if (bitmap != null) {
                    EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(EffectsTemplateView.this.getEditFragment());
                    SegmentInfo segmentInfo = new SegmentInfo(0L, bitmap, null, 1, null);
                    boolean z = this.$fromInit;
                    EffectsTemplateView effectsTemplateView2 = EffectsTemplateView.this;
                    ImageSegmentEditor segmentEditor = effectsTemplateView.getSegmentEditor();
                    if (segmentEditor != null) {
                        segmentEditor.setSubjectInfo(segmentInfo);
                    }
                    if (!z) {
                        if (viewModel != null) {
                            EditFragmentViewModel.reloadTemplateConfigForWallpaperFilter$default(viewModel, effectsTemplateView2.getTemplateConfig(), false, 2, null);
                        }
                        if (effectsTemplateView2.shouldEnableHierarchy()) {
                            if (viewModel != null) {
                                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 1, false, false, false, 14, null);
                            }
                            if (viewModel != null) {
                                EditFragmentViewModel.updateDoodleStatus$default(viewModel, 1, false, 2, null);
                            }
                        } else {
                            if (viewModel != null) {
                                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 14, null);
                            }
                            if (viewModel != null) {
                                EditFragmentViewModel.updateDoodleStatus$default(viewModel, 2, false, 2, null);
                            }
                        }
                    } else if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 1, false, false, false, 14, null);
                    }
                    CommonConfig templateConfig = EffectsTemplateView.this.getTemplateConfig();
                    if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                        wallpaperInfo.setSupportSubject(true);
                    }
                    Boxing.boxInt(Log.i("Keyguard-Editor-EffectsTemplateView", "onSelectedImageWallpaper: load subject from mask"));
                } else {
                    effectsTemplateView.showHierarchyLoading(this.$fromInit);
                    ImageSegmentEditor segmentEditor2 = effectsTemplateView.getSegmentEditor();
                    if (segmentEditor2 != null) {
                        segmentEditor2.onWallpaperChanged(this.$info.getBitmap(), new WallpaperChangedCallBack(effectsTemplateView, this.$fromInit, this.$info, this.$doGlass, null, 16, null));
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.EffectsTemplateView$processHierarchy$2, reason: invalid class name */
    /* JADX INFO: compiled from: EffectsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$processHierarchy$2", f = "EffectsTemplateView.kt", l = {1389}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $doGlass;
        public final /* synthetic */ boolean $fromInit;
        public final /* synthetic */ WallpaperTypeInfo $info;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, WallpaperTypeInfo wallpaperTypeInfo, boolean z2, Continuation continuation) {
            super(2, continuation);
            this.$fromInit = z;
            this.$info = wallpaperTypeInfo;
            this.$doGlass = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EffectsTemplateView.this.new AnonymousClass2(this.$fromInit, this.$info, this.$doGlass, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object objWithContext;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                EffectsTemplateView$processHierarchy$2$pair$1 effectsTemplateView$processHierarchy$2$pair$1 = new EffectsTemplateView$processHierarchy$2$pair$1(this.$info, EffectsTemplateView.this, null);
                this.label = 1;
                objWithContext = BuildersKt.withContext(io2, effectsTemplateView$processHierarchy$2$pair$1, this);
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
            Pair pair = (Pair) objWithContext;
            WeakReference weakReference = EffectsTemplateView.this.weakContext;
            EffectsTemplateView effectsTemplateView = weakReference != null ? (EffectsTemplateView) weakReference.get() : null;
            if (effectsTemplateView != null) {
                CacheInfo cacheInfo = pair != null ? (CacheInfo) pair.getSecond() : null;
                if (cacheInfo != null) {
                    new WallpaperChangedCallBack(effectsTemplateView, this.$fromInit, this.$info, this.$doGlass, null, 16, null).doCallBack(new SegmentInfo(0L, cacheInfo.getBitmap(), null, 1, null));
                } else {
                    if (Intrinsics.areEqual(this.$info.getType(), "image")) {
                        effectsTemplateView.showHierarchyLoading(this.$fromInit);
                    }
                    ImageSegmentEditor segmentEditor = effectsTemplateView.getSegmentEditor();
                    if (segmentEditor != null) {
                        segmentEditor.onWallpaperChanged(this.$info.getBitmap(), new WallpaperChangedCallBack(effectsTemplateView, this.$fromInit, this.$info, this.$doGlass, pair));
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.EffectsTemplateView$startCheckDepthVideoAvoid$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EffectsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$startCheckDepthVideoAvoid$1", f = "EffectsTemplateView.kt", l = {}, m = "invokeSuspend")
    public static final class C01151 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01151(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EffectsTemplateView.this.new C01151(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01151) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            HierarchyImageView hierarchyImageView;
            HierarchyImageAvoidController avoidController;
            HierarchyImageView hierarchyImageView2;
            HierarchyImageAvoidController avoidController2;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FastPlayer fastPlayer = EffectsTemplateView.this.fastPlayer;
            Bitmap lastDepthFrame = null;
            if (fastPlayer != null) {
                WallpaperInfo wallpaperInfo = EffectsTemplateView.this.getWallpaperInfo();
                lastDepthFrame = fastPlayer.getLastDepthFrame(wallpaperInfo != null ? wallpaperInfo.getOriginResourcePath() : null);
            }
            if (lastDepthFrame != null && (hierarchyImageView = (HierarchyImageView) EffectsTemplateView.this.getHierarchyLayer()) != null && (avoidController = hierarchyImageView.getAvoidController()) != null && avoidController.isUserOpenDepthVideo() && (hierarchyImageView2 = (HierarchyImageView) EffectsTemplateView.this.getHierarchyLayer()) != null && (avoidController2 = hierarchyImageView2.getAvoidController()) != null) {
                avoidController2.onDepthVideoChange(new DepthVideoParam(lastDepthFrame, EffectsTemplateView.this.getWidth(), EffectsTemplateView.this.getHeight()));
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.EffectsTemplateView$updateAiGenerateBtnStatus$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EffectsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$updateAiGenerateBtnStatus$1", f = "EffectsTemplateView.kt", l = {474}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class C01161 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $resetEnableStatus;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01161(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$resetEnableStatus = z;
        }

        public static final void invokeSuspend$lambda$1(EffectsTemplateView effectsTemplateView) {
            TemplateButtonBar primaryButtonBar = effectsTemplateView.getPrimaryButtonBar();
            if (primaryButtonBar != null) {
                primaryButtonBar.updateButtonGroupRec();
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EffectsTemplateView.this.new C01161(this.$resetEnableStatus, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01161) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:30:0x0088  */
        /* JADX WARN: Code duplicated, block: B:35:0x0099  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Integer num;
            KgVisualCheckBox aiGenerateBtn;
            C01161 c01161;
            EffectsTemplateView effectsTemplateView;
            KgVisualCheckBox kgVisualCheckBox;
            EffectsTemplateView effectsTemplateView2;
            TemplateConfig lockscreenInfo;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CommonConfig templateConfig = EffectsTemplateView.this.getTemplateConfig();
                num = null;
                WallpaperInfo wallpaperInfo = (templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo();
                aiGenerateBtn = EffectsTemplateView.this.getAiGenerateBtn();
                if (aiGenerateBtn != null) {
                    effectsTemplateView = EffectsTemplateView.this;
                    boolean z = this.$resetEnableStatus;
                    AIWallPaperManager aIWallPaperManager = effectsTemplateView.aiWallPaperManager;
                    if (aIWallPaperManager != null) {
                        Context context = aiGenerateBtn.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                        boolean zCanDownloadButton = effectsTemplateView.canDownloadButton(wallpaperInfo);
                        this.L$0 = aiGenerateBtn;
                        this.L$1 = effectsTemplateView;
                        this.L$2 = aiGenerateBtn;
                        this.label = 1;
                        c01161 = this;
                        Object aiGenerateStatus = aIWallPaperManager.getAiGenerateStatus(context, wallpaperInfo, z, zCanDownloadButton, c01161);
                        if (aiGenerateStatus == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        kgVisualCheckBox = aiGenerateBtn;
                        effectsTemplateView2 = effectsTemplateView;
                        obj = aiGenerateStatus;
                    } else {
                        c01161 = this;
                    }
                    if (num != null && num.intValue() == 1) {
                        aiGenerateBtn.setVisibility(0);
                        aiGenerateBtn.enable();
                    } else if (num != null && num.intValue() == 2) {
                        aiGenerateBtn.setVisibility(0);
                        aiGenerateBtn.disable();
                    } else if (num != null && num.intValue() == 3) {
                        aiGenerateBtn.setVisibility(8);
                    }
                    effectsTemplateView.updateGenerateView();
                } else {
                    c01161 = this;
                }
                final EffectsTemplateView effectsTemplateView3 = EffectsTemplateView.this;
                effectsTemplateView3.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$updateAiGenerateBtnStatus$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EffectsTemplateView.C01161.invokeSuspend$lambda$1(effectsTemplateView3);
                    }
                });
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kgVisualCheckBox = (KgVisualCheckBox) this.L$2;
            effectsTemplateView2 = (EffectsTemplateView) this.L$1;
            ResultKt.throwOnFailure(obj);
            c01161 = this;
            Integer num2 = (Integer) obj;
            aiGenerateBtn = kgVisualCheckBox;
            effectsTemplateView = effectsTemplateView2;
            num = num2;
            if (num != null) {
                aiGenerateBtn.setVisibility(0);
                aiGenerateBtn.enable();
            } else if (num != null) {
                aiGenerateBtn.setVisibility(0);
                aiGenerateBtn.disable();
            } else if (num != null) {
                aiGenerateBtn.setVisibility(8);
            }
            effectsTemplateView.updateGenerateView();
            final EffectsTemplateView effectsTemplateView4 = EffectsTemplateView.this;
            effectsTemplateView4.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$updateAiGenerateBtnStatus$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EffectsTemplateView.C01161.invokeSuspend$lambda$1(effectsTemplateView4);
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public EffectsTemplateView(@NotNull Context context) {
        super(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
        Log.d("Keyguard-Editor-EffectsTemplateView", "EffectsTemplateView: init");
        this.weakContext = new WeakReference(this);
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.segmentCache = new ImageSegmentCache(applicationContext);
    }

    public static final void addAIGenerateButton$lambda$12(final EffectsTemplateView effectsTemplateView, View view) {
        FragmentActivity activity;
        Intent intent;
        TemplateConfig lockscreenInfo;
        FragmentActivity activity2;
        Intent intent2;
        AIWallPaperManager aIWallPaperManager = effectsTemplateView.aiWallPaperManager;
        if (aIWallPaperManager != null && aIWallPaperManager.isGenerateRunning()) {
            EditFragment editFragment = effectsTemplateView.getEditFragment();
            boolean z = (editFragment == null || (activity2 = editFragment.getActivity()) == null || (intent2 = activity2.getIntent()) == null || intent2.getIntExtra("openSource", -1) != 0) ? false : true;
            AIWallPaperManager aIWallPaperManager2 = effectsTemplateView.aiWallPaperManager;
            if (aIWallPaperManager2 != null) {
                Context context = effectsTemplateView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                aIWallPaperManager2.openGeneratingPage(context, z);
                return;
            }
            return;
        }
        BaseClockView baseClockView = (BaseClockView) effectsTemplateView.getClockLayer();
        if (baseClockView != null) {
            baseClockView.onEditorSubUIShown();
        }
        CommonConfig templateConfig = effectsTemplateView.getTemplateConfig();
        WallpaperInfo wallpaperInfo = (templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo();
        EditFragment editFragment2 = effectsTemplateView.getEditFragment();
        boolean z2 = (editFragment2 == null || (activity = editFragment2.getActivity()) == null || (intent = activity.getIntent()) == null || intent.getIntExtra("openSource", -1) != 0) ? false : true;
        AIWallPaperManager aIWallPaperManager3 = effectsTemplateView.aiWallPaperManager;
        if (aIWallPaperManager3 != null) {
            aIWallPaperManager3.startAIGenerate(effectsTemplateView.getEditFragment(), wallpaperInfo, z2, new Function1() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return EffectsTemplateView.addAIGenerateButton$lambda$12$lambda$10(this.f$0, (WallpaperInfo) obj);
                }
            }, new AIGenerateFinishCallback(new WeakReference(effectsTemplateView)));
        }
        updateAiGenerateBtnStatus$default(effectsTemplateView, false, 1, null);
        Context context2 = effectsTemplateView.getContext();
        if (context2 != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            String string = context2.getString(R.string.kg_title_ai_generate);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            TrackHelper.trackClickEditorItem$default(trackHelper, context2, 0, string, "开", null, null, 32, null);
        }
    }

    public static final WallpaperTypeInfo addAIGenerateButton$lambda$12$lambda$10(EffectsTemplateView effectsTemplateView, WallpaperInfo wallpaperInfo) {
        WallpaperTypeInfo wallpaperTypeInfoConvertToWallpaperTypeInfo = effectsTemplateView.getWallpaperEditor().convertToWallpaperTypeInfo(wallpaperInfo);
        if (wallpaperTypeInfoConvertToWallpaperTypeInfo == null) {
            return null;
        }
        IWallpaperLayer wallpaperLayer = effectsTemplateView.getWallpaperLayer();
        wallpaperTypeInfoConvertToWallpaperTypeInfo.setPositionInfo(wallpaperLayer != null ? IWallpaperLayer.DefaultImpls.getPosition$default(wallpaperLayer, null, 1, null) : null);
        return wallpaperTypeInfoConvertToWallpaperTypeInfo;
    }

    public static final void addAIGenerateButton$lambda$14(EffectsTemplateView effectsTemplateView, View view) {
        BaseClockView baseClockView = (BaseClockView) effectsTemplateView.getClockLayer();
        if (baseClockView != null) {
            baseClockView.onEditorSubUIShown();
        }
        AIWallPaperManager aIWallPaperManager = effectsTemplateView.aiWallPaperManager;
        if (aIWallPaperManager != null) {
            Context context = effectsTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            Triple disableTips = aIWallPaperManager.getDisableTips(context);
            if (disableTips != null) {
                CharSequence charSequence = (CharSequence) disableTips.getFirst();
                if (charSequence != null && charSequence.length() != 0) {
                    effectsTemplateView.showDisableAlert(disableTips);
                    return;
                }
                ToastUtil toastUtil = ToastUtil.INSTANCE;
                Context context2 = effectsTemplateView.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                toastUtil.showText(context2, (String) disableTips.getSecond());
            }
        }
    }

    private final Rect dualClockAvoidRect() {
        Rect rectDualClockAvoidRect;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return (baseClockView == null || (rectDualClockAvoidRect = baseClockView.dualClockAvoidRect()) == null) ? new Rect() : rectDualClockAvoidRect;
    }

    public static final void handleGenerateFailed$lambda$7(EffectsTemplateView effectsTemplateView, Triple triple) {
        updateAiGenerateBtnStatus$default(effectsTemplateView, false, 1, null);
        String str = triple != null ? (String) triple.getFirst() : null;
        if (str == null || str.length() == 0) {
            return;
        }
        String str2 = triple != null ? (String) triple.getSecond() : null;
        if (str2 == null || str2.length() == 0) {
            return;
        }
        effectsTemplateView.showDisableAlert(triple);
    }

    private final Rect hierarchyAvoidRect() {
        Rect rectHierarchyAvoidRect;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return (baseClockView == null || (rectHierarchyAvoidRect = baseClockView.hierarchyAvoidRect()) == null) ? new Rect() : rectHierarchyAvoidRect;
    }

    public static final void initHierarchyButton$lambda$24$lambda$23$lambda$17(EffectsTemplateView effectsTemplateView, EditFragmentViewModel editFragmentViewModel, KgVisualCheckBox it) throws IOException {
        ClockInfo clockInfo;
        String templateId;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Intrinsics.checkNotNullParameter(it, "it");
        CommonConfig templateConfig = effectsTemplateView.getTemplateConfig();
        if (Intrinsics.areEqual((templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType(), "video")) {
            effectsTemplateView.onClickDepthButtonClick();
        } else {
            if (editFragmentViewModel != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel, 1, false, false, false, 12, null);
            }
            BaseClockView baseClockView = (BaseClockView) effectsTemplateView.getClockLayer();
            if (baseClockView != null) {
                baseClockView.resetAllBottomSheet();
            }
        }
        CommonConfig templateConfig2 = effectsTemplateView.getTemplateConfig();
        if (templateConfig2 != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context context = effectsTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            String string = effectsTemplateView.getContext().getString(R.string.kg_function_button_text_hierarchy);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            TemplateConfig lockscreenInfo2 = templateConfig2.getLockscreenInfo();
            String str = (lockscreenInfo2 == null || (clockInfo = lockscreenInfo2.getClockInfo()) == null || (templateId = clockInfo.getTemplateId()) == null) ? "" : templateId;
            String trackId = effectsTemplateView.getTrackId();
            trackHelper.trackClickEditorItem(context, 0, string, "开", str, trackId == null ? "" : trackId);
        }
    }

    public static final void initHierarchyButton$lambda$24$lambda$23$lambda$19(EffectsTemplateView effectsTemplateView, EditFragmentViewModel editFragmentViewModel, KgVisualCheckBox it) throws IOException {
        ClockInfo clockInfo;
        String templateId;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Intrinsics.checkNotNullParameter(it, "it");
        CommonConfig templateConfig = effectsTemplateView.getTemplateConfig();
        if (Intrinsics.areEqual((templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType(), "video")) {
            effectsTemplateView.onClickDepthButtonClick();
        } else {
            if (editFragmentViewModel != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel, 2, false, false, false, 12, null);
            }
            BaseClockView baseClockView = (BaseClockView) effectsTemplateView.getClockLayer();
            if (baseClockView != null) {
                baseClockView.resetAllBottomSheet();
            }
        }
        CommonConfig templateConfig2 = effectsTemplateView.getTemplateConfig();
        if (templateConfig2 != null) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context context = effectsTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            String string = effectsTemplateView.getContext().getString(R.string.kg_function_button_text_hierarchy);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            TemplateConfig lockscreenInfo2 = templateConfig2.getLockscreenInfo();
            String str = (lockscreenInfo2 == null || (clockInfo = lockscreenInfo2.getClockInfo()) == null || (templateId = clockInfo.getTemplateId()) == null) ? "" : templateId;
            String trackId = effectsTemplateView.getTrackId();
            trackHelper.trackClickEditorItem(context, 0, string, "关", str, trackId == null ? "" : trackId);
        }
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0095  */
    public static final void initHierarchyButton$lambda$24$lambda$23$lambda$22(EffectsTemplateView effectsTemplateView, View view) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig templateConfig;
        ClockInfo clockInfo;
        String templateId;
        IntWrapper hierarchyStatus;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(effectsTemplateView.getEditFragment());
        Integer numValueOf = (viewModel == null || (hierarchyStatus = viewModel.getHierarchyStatus()) == null) ? null : Integer.valueOf(hierarchyStatus.getIntValue());
        if (effectsTemplateView.isInThirdPartyWallpaperService()) {
            ToastUtil toastUtil = ToastUtil.INSTANCE;
            Context context = effectsTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            toastUtil.showText(context, R.string.kg_hierarchy_not_support_third_party_wallpaper_service_tip);
        } else if (effectsTemplateView.isGalleryOpened()) {
            ToastUtil toastUtil2 = ToastUtil.INSTANCE;
            Context context2 = effectsTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            toastUtil2.showText(context2, R.string.kg_hierarchy_not_support_gallery_tip);
        } else {
            SegmentInfo subjectInfo = effectsTemplateView.getSubjectInfo();
            if ((subjectInfo != null ? subjectInfo.getBitmap() : null) != null || (numValueOf != null && numValueOf.intValue() == 4)) {
                SegmentInfo subjectInfo2 = effectsTemplateView.getSubjectInfo();
                if ((subjectInfo2 != null ? subjectInfo2.getBitmap() : null) != null) {
                    Wallpaper.Companion companion = Wallpaper.Companion;
                    CommonConfig templateConfig2 = effectsTemplateView.getTemplateConfig();
                    if (companion.isLivePhotoResource((templateConfig2 == null || (lockscreenInfo = templateConfig2.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getVideoType()))) {
                        ToastUtil toastUtil3 = ToastUtil.INSTANCE;
                        Context context3 = effectsTemplateView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                        toastUtil3.showText(context3, R.string.kg_hierarchy_not_support_wallpaper_tip);
                    }
                }
                SegmentInfo subjectInfo3 = effectsTemplateView.getSubjectInfo();
                if ((subjectInfo3 != null ? subjectInfo3.getBitmap() : null) != null || (numValueOf != null && numValueOf.intValue() == 4)) {
                    ToastUtil toastUtil4 = ToastUtil.INSTANCE;
                    Context context4 = effectsTemplateView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
                    toastUtil4.showText(context4, R.string.kg_dialog_too_much_time_blocking);
                }
            } else {
                ToastUtil toastUtil5 = ToastUtil.INSTANCE;
                Context context5 = effectsTemplateView.getContext();
                Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                toastUtil5.showText(context5, R.string.kg_hierarchy_not_support_wallpaper_tip);
            }
        }
        if (effectsTemplateView.getTemplateConfig() == null || (templateConfig = effectsTemplateView.getTemplateConfig()) == null) {
            return;
        }
        TrackHelper trackHelper = TrackHelper.INSTANCE;
        Context context6 = effectsTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
        String string = effectsTemplateView.getContext().getString(R.string.kg_function_button_text_hierarchy);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        TemplateConfig lockscreenInfo2 = templateConfig.getLockscreenInfo();
        String str = (lockscreenInfo2 == null || (clockInfo = lockscreenInfo2.getClockInfo()) == null || (templateId = clockInfo.getTemplateId()) == null) ? "" : templateId;
        String trackId = effectsTemplateView.getTrackId();
        trackHelper.trackClickEditorItem(context6, 0, string, "不支持", str, trackId == null ? "" : trackId);
    }

    private final Rect largeScreenWithNotificationAvoidRect(boolean z) {
        Rect rectLargeScreenWithNotificationAvoidRect;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return (baseClockView == null || (rectLargeScreenWithNotificationAvoidRect = baseClockView.largeScreenWithNotificationAvoidRect(z)) == null) ? new Rect() : rectLargeScreenWithNotificationAvoidRect;
    }

    public static final Unit onEdited$lambda$33$lambda$32(EffectsTemplateView effectsTemplateView, Object obj) {
        effectsTemplateView.onHierarchySelected(obj, true);
        return Unit.INSTANCE;
    }

    public static final Unit onEdited$lambda$35$lambda$34(EffectsTemplateView effectsTemplateView, Object obj) {
        effectsTemplateView.onGlassFilterSelected(obj);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void onHierarchySelected$default(EffectsTemplateView effectsTemplateView, Object obj, boolean z, int i, Object obj2) {
        if ((i & 2) != 0) {
            z = false;
        }
        effectsTemplateView.onHierarchySelected(obj, z);
    }

    public static /* synthetic */ void processHierarchy$default(EffectsTemplateView effectsTemplateView, WallpaperTypeInfo wallpaperTypeInfo, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        effectsTemplateView.processHierarchy(wallpaperTypeInfo, z, z2);
    }

    public static /* synthetic */ void refreshGlassFilterEnable$default(EffectsTemplateView effectsTemplateView, String str, int i, Object obj) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if ((i & 1) != 0) {
            CommonConfig templateConfig = effectsTemplateView.getTemplateConfig();
            str = (templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType();
        }
        effectsTemplateView.refreshGlassFilterEnable(str);
    }

    public static final void showHierarchyLoading$lambda$49$lambda$48(View view) {
    }

    private final Rect smaleScreenWithNotificationAvoidRect() {
        Rect rectSmaleScreenWithNotificationAvoidRect;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return (baseClockView == null || (rectSmaleScreenWithNotificationAvoidRect = baseClockView.smaleScreenWithNotificationAvoidRect()) == null) ? new Rect() : rectSmaleScreenWithNotificationAvoidRect;
    }

    private final Rect templateHierarchyAvoidRect() {
        Rect rectTemplateHierarchyAvoidRect;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return (baseClockView == null || (rectTemplateHierarchyAvoidRect = baseClockView.templateHierarchyAvoidRect()) == null) ? new Rect(0, 0, 0, 0) : rectTemplateHierarchyAvoidRect;
    }

    private final Set unSupportHierarchyTypes() {
        Set setUnSupportHierarchyTypes;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        return (baseClockView == null || (setUnSupportHierarchyTypes = baseClockView.unSupportHierarchyTypes()) == null) ? ScreenshotSourceKt.getDefaultNotSupportTypes() : setUnSupportHierarchyTypes;
    }

    public static /* synthetic */ void updateAiGenerateBtnStatus$default(EffectsTemplateView effectsTemplateView, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        effectsTemplateView.updateAiGenerateBtnStatus(z);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @NotNull
    public List<View> aboveWallpaperLayers() {
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (Intrinsics.areEqual(getTemplateId(), "doodle")) {
            return CollectionsKt.listOfNotNull((Object[]) new View[]{baseClockView != null ? baseClockView.getBackContentLayer() : null, baseClockView != null ? baseClockView.getForeContentLayer() : null});
        }
        return depthAwareLayerOrder();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void addAIGenerateButton() {
        Resources resources;
        BorderLayout borderLayout;
        ImageView icon;
        Log.i("AIWallPaperManager", "addAIGenerateButton");
        AIWallPaperManager companion = AIWallPaperManager.Companion.getInstance();
        this.aiWallPaperManager = companion;
        if (companion != null) {
            companion.init(getContext(), this);
        }
        TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
        if (primaryButtonBar == null) {
            return;
        }
        int dimensionPixelSize = 0;
        setAiGenerateBtn(TemplateButtonBar.addAiGenerateButton$default(primaryButtonBar, 0, 1, null));
        KgVisualCheckBox aiGenerateBtn = getAiGenerateBtn();
        if (aiGenerateBtn != null && (icon = aiGenerateBtn.getIcon()) != null) {
            icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        KgVisualCheckBox aiGenerateBtn2 = getAiGenerateBtn();
        if (aiGenerateBtn2 != null && (borderLayout = aiGenerateBtn2.getBorderLayout()) != null) {
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_function_button_padding);
            borderLayout.setPadding(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
        }
        Context context = getContext();
        if (context != null && (resources = context.getResources()) != null) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_function_ai_generate_button_height);
        }
        aiGenerateButtonIconSize(dimensionPixelSize);
        updateAiGenerateBtnStatus(true);
        final View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EffectsTemplateView.addAIGenerateButton$lambda$12(this.f$0, view);
            }
        };
        final View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EffectsTemplateView.addAIGenerateButton$lambda$14(this.f$0, view);
            }
        };
        KgVisualCheckBox aiGenerateBtn3 = getAiGenerateBtn();
        if (aiGenerateBtn3 != null) {
            aiGenerateBtn3.setClickListener(onClickListener);
        }
        KgVisualCheckBox aiGenerateBtn4 = getAiGenerateBtn();
        if (aiGenerateBtn4 != null) {
            aiGenerateBtn4.setDisableClickListener(onClickListener2);
        }
        KgVisualCheckBox aiGenerateBtn5 = getAiGenerateBtn();
        if (aiGenerateBtn5 != null) {
            aiGenerateBtn5.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView.addAIGenerateButton.1
                @Override // android.view.View.AccessibilityDelegate
                public boolean performAccessibilityAction(View host, int i, Bundle bundle) {
                    Intrinsics.checkNotNullParameter(host, "host");
                    if (i != 16) {
                        return super.performAccessibilityAction(host, i, bundle);
                    }
                    KgVisualCheckBox aiGenerateBtn6 = EffectsTemplateView.this.getAiGenerateBtn();
                    if (aiGenerateBtn6 == null || !aiGenerateBtn6.isEnabled()) {
                        onClickListener2.onClick(EffectsTemplateView.this.getAiGenerateBtn());
                    } else {
                        onClickListener.onClick(EffectsTemplateView.this.getAiGenerateBtn());
                    }
                    return true;
                }
            });
        }
        updateGenerateView();
    }

    public final void aiGenerateButtonIconSize(int i) {
        ImageView icon;
        KgVisualCheckBox aiGenerateBtn = getAiGenerateBtn();
        ViewGroup.LayoutParams layoutParams = (aiGenerateBtn == null || (icon = aiGenerateBtn.getIcon()) == null) ? null : icon.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i;
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0064 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:27:0x0066  */
    public final void changeEffectData(WallpaperTypeInfo wallpaperTypeInfo) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo5;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo6;
        WallpaperInfo wallpaperInfo6;
        boolean zIsDepthVideo = WallpaperEditor.Companion.isDepthVideo(wallpaperTypeInfo);
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        boolean zSupportHierarchy = baseClockView != null ? baseClockView.supportHierarchy() : true;
        if (!zIsDepthVideo || !zSupportHierarchy) {
            MagicType magicType = MagicType.INSTANCE;
            CommonConfig templateConfig = getTemplateConfig();
            if (!magicType.isDepthVideo((templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getMagicType()))) {
                if (Intrinsics.areEqual(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null, "video")) {
                    if (viewModel != null) {
                        EditFragmentViewModel.updateWallpaperFilterIdSync$default(viewModel, 0, false, 2, null);
                    }
                }
            } else if (viewModel != null) {
                EditFragmentViewModel.updateWallpaperFilterIdSync$default(viewModel, 0, false, 2, null);
            }
        } else if (viewModel != null) {
            EditFragmentViewModel.updateWallpaperFilterIdSync$default(viewModel, MagicType.TYPE_DEPTH_VIDEO, false, 2, null);
        }
        if (zIsDepthVideo) {
            CommonConfig templateConfig2 = getTemplateConfig();
            if (templateConfig2 != null && (lockscreenInfo6 = templateConfig2.getLockscreenInfo()) != null && (wallpaperInfo6 = lockscreenInfo6.getWallpaperInfo()) != null) {
                wallpaperInfo6.setDepthVideo(wallpaperTypeInfo != null ? wallpaperTypeInfo.getResourcePath() : null);
            }
            CommonConfig templateConfig3 = getTemplateConfig();
            if (templateConfig3 != null && (lockscreenInfo5 = templateConfig3.getLockscreenInfo()) != null && (wallpaperInfo5 = lockscreenInfo5.getWallpaperInfo()) != null) {
                wallpaperInfo5.setDepthVideo(true);
            }
        } else {
            CommonConfig templateConfig4 = getTemplateConfig();
            if (templateConfig4 != null && (lockscreenInfo3 = templateConfig4.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null) {
                wallpaperInfo3.setDepthVideo((String) null);
            }
            CommonConfig templateConfig5 = getTemplateConfig();
            if (templateConfig5 != null && (lockscreenInfo2 = templateConfig5.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                wallpaperInfo2.setDepthVideo(false);
            }
        }
        CommonConfig templateConfig6 = getTemplateConfig();
        if (templateConfig6 == null || (lockscreenInfo4 = templateConfig6.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) == null) {
            return;
        }
        wallpaperInfo4.setAiGenerated(wallpaperTypeInfo != null ? wallpaperTypeInfo.isAiGenerated() : false);
    }

    public final void closeEffect() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig templateConfig = getTemplateConfig();
        if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.setEnableBlur(false);
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateMatteStatus$default(viewModel, 3, false, 2, null);
        }
        Log.i("Keyguard-Editor-EffectsTemplateView", "closeEffect: enableBlur =false");
        CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
        if (combinedWallpaperView != null) {
            combinedWallpaperView.setEnableBlurStatus(false, false);
        }
        CombinedWallpaperView combinedWallpaperView2 = (CombinedWallpaperView) getWallpaperLayer();
        View viewWallpaperLayerView = combinedWallpaperView2 != null ? combinedWallpaperView2.wallpaperLayerView() : null;
        if (viewWallpaperLayerView instanceof GLTextureView) {
            ((GLTextureView) viewWallpaperLayerView).setGlassWithBlur(false);
        }
    }

    public final void closeHierarchy() {
        final HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        if (hierarchyImageView != null) {
            CrossListFolmeUtil.INSTANCE.startHierarchyAlphaAnimation(hierarchyImageView, false, new TransitionListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$closeHierarchy$1$1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    hierarchyImageView.setImageBitmap(null);
                    hierarchyImageView.setVisibility(8);
                }
            });
        }
        onHierarchyDisable();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public View createDepthVideoForegroundLayer(Context context, TemplateConfig templateConfig, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templateConfig == null || !editable() || !DeviceUtil.INSTANCE.isSupportHierarchyDevices() || !FileUtil.isSupportVideoDepth()) {
            return null;
        }
        if (isPresetVideoDepthBlocked(templateConfig.getWallpaperInfo())) {
            WallpaperInfo wallpaperInfo = templateConfig.getWallpaperInfo();
            Log.d("Keyguard-Editor-EffectsTemplateView", "createDepthVideoForegroundLayer: blocked by preset video depth rule, imagePath=" + (wallpaperInfo != null ? wallpaperInfo.getImagePath() : null));
            return null;
        }
        IWallpaperLayer.Companion companion = IWallpaperLayer.Companion;
        WallpaperInfo wallpaperInfo2 = templateConfig.getWallpaperInfo();
        VideoTextureView videoTextureViewCreateDepthVideoForegroundView = companion.createDepthVideoForegroundView(context, wallpaperInfo2 != null ? wallpaperInfo2.getCurrentWallpaper() : null, null, getFromWhereTag());
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        WallpaperInfo wallpaperInfo3 = templateConfig.getWallpaperInfo();
        if (Intrinsics.areEqual(wallpaperInfo3 != null ? wallpaperInfo3.getResourceType() : null, "video")) {
            MagicType magicType = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo4 = templateConfig.getWallpaperInfo();
            if (magicType.isDepthVideo(wallpaperInfo4 != null ? Integer.valueOf(wallpaperInfo4.getMagicType()) : null)) {
                if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 5, false, false, false, 14, null);
                    return videoTextureViewCreateDepthVideoForegroundView;
                }
            } else if (viewModel != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 6, false, false, false, 14, null);
            }
        }
        return videoTextureViewCreateDepthVideoForegroundView;
    }

    /* JADX WARN: Code duplicated, block: B:137:0x0339  */
    /* JADX WARN: Code duplicated, block: B:139:0x0340  */
    /* JADX WARN: Code duplicated, block: B:62:0x01a2  */
    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public View createHierarchyLayer(Context context, TemplateConfig templateConfig) {
        HierarchyImageAvoidController avoidController;
        int i;
        int i2;
        Bitmap bitmap;
        IWallpaperLayer wallpaperLayer;
        IntWrapper hierarchyStatus;
        IntWrapper hierarchyStatus2;
        boolean zIsDepth;
        LargeScreenHierarchyEnable largeScreenHierarchyEnable;
        ImageSegmentEditor imageSegmentEditor;
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isSupportHierarchyDevices()) {
            Log.i("Keyguard-Editor-EffectsTemplateView", "createHierarchyLayer: disable");
            return null;
        }
        WallpaperInfo wallpaperInfo = templateConfig != null ? templateConfig.getWallpaperInfo() : null;
        if (wallpaperInfo == null) {
            Log.w("Keyguard-Editor-EffectsTemplateView", "createHierarchyLayer: wallpaper info is null");
            return null;
        }
        if (isGalleryOpened()) {
            disableHierarchyWhenGalleryOpened();
            if (wallpaperInfo.getSubjectBitmap() != null && (imageSegmentEditor = this.segmentEditor) != null) {
                Bitmap subjectBitmap = wallpaperInfo.getSubjectBitmap();
                Intrinsics.checkNotNull(subjectBitmap);
                imageSegmentEditor.setSubjectInfo(new SegmentInfo(0L, subjectBitmap, null, 1, null));
            }
            return null;
        }
        final HierarchyImageView hierarchyImageView = new HierarchyImageView(context, null, 0, 6, null);
        final EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (editable() || isCreateForScreenShot()) {
            boolean zIsCreateForScreenShot = isCreateForScreenShot();
            hierarchyImageView.initComponentWhenTemplateViewEditable(zIsCreateForScreenShot);
            if (zIsCreateForScreenShot) {
                Map mapScreenShotHierarchyAvoidRectMap = screenShotHierarchyAvoidRectMap();
                Log.i("Keyguard-Editor-EffectsTemplateView", EffectsTemplateView.class.getSimpleName() + " " + hierarchyImageView + " template create for screenshot hierarchy avoid check map " + mapScreenShotHierarchyAvoidRectMap);
                HierarchyImageAvoidController avoidController2 = hierarchyImageView.getAvoidController();
                if (avoidController2 != null) {
                    avoidController2.setUpAvoidRect(mapScreenShotHierarchyAvoidRectMap, unSupportHierarchyTypes());
                }
            } else {
                Rect rectHierarchyAvoidRect = hierarchyAvoidRect();
                Log.i("Keyguard-Editor-EffectsTemplateView", EffectsTemplateView.class.getSimpleName() + " " + hierarchyImageView + " avoid rect " + rectHierarchyAvoidRect);
                HierarchyImageAvoidController avoidController3 = hierarchyImageView.getAvoidController();
                if (avoidController3 != null) {
                    avoidController3.setUpAvoidRect(rectHierarchyAvoidRect);
                }
                if (TextUtils.equals(wallpaperInfo.getResourceType(), "video") && (avoidController = hierarchyImageView.getAvoidController()) != null) {
                    avoidController.setUserOpenDepthVideo(MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType())));
                }
                HierarchyImageAvoidController avoidController4 = hierarchyImageView.getAvoidController();
                if (avoidController4 != null) {
                    avoidController4.addHierarchyEnableChangeListener(new OnHierarchyEnableChangeListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$createHierarchyLayer$1$1
                        @Override // com.miui.keyguard.editor.utils.OnHierarchyEnableChangeListener
                        public void onHierarchyEnableChange(boolean z) throws IOException {
                            TemplateConfig lockscreenInfo;
                            WallpaperInfo wallpaperInfo2;
                            TemplateConfig lockscreenInfo2;
                            WallpaperInfo wallpaperInfo3;
                            TemplateConfig lockscreenInfo3;
                            WallpaperInfo wallpaperInfo4;
                            TemplateConfig lockscreenInfo4;
                            WallpaperInfo wallpaperInfo5;
                            Log.i("Keyguard-Editor-EffectsTemplateView", "onHierarchyEnableChange " + z);
                            BaseClockView baseClockView = (BaseClockView) this.this$0.getClockLayer();
                            if (baseClockView != null) {
                                baseClockView.resetStyleEditor();
                            }
                            if (z) {
                                CommonConfig templateConfig2 = this.this$0.getTemplateConfig();
                                if (TextUtils.equals((templateConfig2 == null || (lockscreenInfo4 = templateConfig2.getLockscreenInfo()) == null || (wallpaperInfo5 = lockscreenInfo4.getWallpaperInfo()) == null) ? null : wallpaperInfo5.getResourceType(), "video")) {
                                    EditFragmentViewModel editFragmentViewModel = viewModel;
                                    if (editFragmentViewModel != null) {
                                        EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel, 5, false, false, false, 14, null);
                                    }
                                    this.this$0.onEdited(36, null);
                                    return;
                                }
                                CommonConfig templateConfig3 = this.this$0.getTemplateConfig();
                                if (templateConfig3 != null && (lockscreenInfo3 = templateConfig3.getLockscreenInfo()) != null && (wallpaperInfo4 = lockscreenInfo3.getWallpaperInfo()) != null) {
                                    wallpaperInfo4.updateMagicType(MagicType.TYPE_DEPTH);
                                }
                                EditFragmentViewModel editFragmentViewModel2 = viewModel;
                                if (editFragmentViewModel2 != null) {
                                    editFragmentViewModel2.reloadTemplateConfigForWallpaperFilter(this.this$0.getTemplateConfig(), true);
                                    EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel2, 3, false, false, false, 14, null);
                                }
                                CrossListFolmeUtil.startHierarchyAlphaAnimation$default(CrossListFolmeUtil.INSTANCE, hierarchyImageView, true, null, 4, null);
                                this.this$0.onHierarchyEnable();
                                return;
                            }
                            CommonConfig templateConfig4 = this.this$0.getTemplateConfig();
                            if (TextUtils.equals((templateConfig4 == null || (lockscreenInfo2 = templateConfig4.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType(), "video")) {
                                EditFragmentViewModel editFragmentViewModel3 = viewModel;
                                if (editFragmentViewModel3 != null) {
                                    KgVisualCheckBox hierarchyBtn = this.this$0.getHierarchyBtn();
                                    EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel3, 4, false, false, hierarchyBtn != null ? hierarchyBtn.isChecked() : false, 6, null);
                                }
                                this.this$0.onEdited(35, null);
                                return;
                            }
                            CommonConfig templateConfig5 = this.this$0.getTemplateConfig();
                            if (templateConfig5 != null && (lockscreenInfo = templateConfig5.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) != null) {
                                wallpaperInfo2.updateMagicType(0);
                            }
                            EditFragmentViewModel editFragmentViewModel4 = viewModel;
                            if (editFragmentViewModel4 != null) {
                                editFragmentViewModel4.reloadTemplateConfigForWallpaperFilter(this.this$0.getTemplateConfig(), true);
                                EditFragmentViewModel.updateHierarchyStatus$default(editFragmentViewModel4, 4, false, false, false, 14, null);
                            }
                            CrossListFolmeUtil.startHierarchyAlphaAnimation$default(CrossListFolmeUtil.INSTANCE, hierarchyImageView, false, null, 4, null);
                            this.this$0.onHierarchyDisable();
                        }
                    });
                }
            }
        }
        hierarchyImageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        Log.i("Keyguard-Editor-EffectsTemplateView", "createHierarchyLayer: " + wallpaperInfo.getMagicType());
        hierarchyImageView.setEditMode(editable());
        int i3 = 0;
        if (wallpaperInfo.getSubjectBitmap() == null || isInThirdPartyWallpaperService()) {
            if (shouldSegmentOnInit() && editable()) {
                if (isWallpaperSupportHierarchy(wallpaperInfo.getResourceType(), Integer.valueOf(wallpaperInfo.getVideoType()))) {
                    Bitmap darkenWallpaper = templateConfig.getDarkenWallpaper();
                    if (darkenWallpaper != null) {
                        bitmap = darkenWallpaper;
                    } else {
                        WallpaperInfo wallpaperInfo2 = templateConfig.getWallpaperInfo();
                        if (wallpaperInfo2 != null) {
                            darkenWallpaper = wallpaperInfo2.getCurrentWallpaper();
                            bitmap = darkenWallpaper;
                        } else {
                            bitmap = null;
                        }
                    }
                    i = 1;
                    processHierarchy$default(this, new WallpaperTypeInfo(bitmap, wallpaperInfo.getResourceType(), wallpaperInfo.getOriginResourcePath(), null, 1, wallpaperInfo.getSupportSubject(), null, false, false, null, null, null, null, false, false, null, null, null, wallpaperInfo.isSupportFullScreenAod(), null, 786376, null), true, false, 4, null);
                    WallpaperInfo wallpaperInfo3 = templateConfig.getWallpaperInfo();
                    if (wallpaperInfo3 != null) {
                        wallpaperInfo3.setMattingMode(1);
                    }
                } else {
                    if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
                    }
                    i = 1;
                }
                refreshGlassFilterEnable(wallpaperInfo.getResourceType());
                hierarchyImageView.setVisibility(8);
            } else {
                i = 1;
                hierarchyImageView.setVisibility(8);
                if (viewModel != null) {
                    i2 = 1;
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, true, false, false, 12, null);
                }
            }
            i2 = i;
        } else {
            ImageSegmentEditor imageSegmentEditor2 = this.segmentEditor;
            if (imageSegmentEditor2 != null) {
                Bitmap subjectBitmap2 = wallpaperInfo.getSubjectBitmap();
                Intrinsics.checkNotNull(subjectBitmap2);
                imageSegmentEditor2.setSubjectInfo(new SegmentInfo(0L, subjectBitmap2, null, 1, null));
            }
            if (editable() || isCreateForScreenShot()) {
                zIsDepth = MagicType.INSTANCE.isDepth(Integer.valueOf(wallpaperInfo.getMagicType()));
            } else if (MagicType.INSTANCE.isDepth(Integer.valueOf(wallpaperInfo.getMagicType()))) {
                WallpaperInfo wallpaperInfo4 = templateConfig.getWallpaperInfo();
                if ((wallpaperInfo4 == null || (largeScreenHierarchyEnable = wallpaperInfo4.getLargeScreenHierarchyEnable()) == null) ? true : largeScreenHierarchyEnable.getCurrentScreenHierarchy(deviceUtil.getScreenStatus(context), isDualClock())) {
                    zIsDepth = true;
                } else {
                    zIsDepth = false;
                }
            } else {
                zIsDepth = false;
            }
            if (zIsDepth || MagicType.INSTANCE.isDepthVideo(Integer.valueOf(wallpaperInfo.getMagicType()))) {
                Log.i("Keyguard-Editor-EffectsTemplateView", " depth subject, magicType=" + wallpaperInfo.getMagicType());
                WallpaperInfo wallpaperInfo5 = templateConfig.getWallpaperInfo();
                if (wallpaperInfo5 != null) {
                    wallpaperInfo5.setMattingMode(2);
                }
                if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 1, true, false, false, 12, null);
                }
                hierarchyImageView.setImageBitmap(wallpaperInfo.getSubjectBitmap());
                boolean z = zIsDepth && !DepthPresetDownloadManager.Companion.isPresetDepthVideo(wallpaperInfo, Intrinsics.areEqual(isPreset(), Boolean.TRUE));
                HierarchyImageAvoidController avoidController5 = hierarchyImageView.getAvoidController();
                if (avoidController5 != null) {
                    avoidController5.setUserOpenHierarchy(z);
                }
                Log.i("Keyguard-Editor-EffectsTemplateView", "create hierarchyLayer, isOpenHierarchyEnable=" + z);
            } else if (viewModel != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, true, false, false, 12, null);
            }
            i2 = 1;
        }
        WallpaperInfo wallpaperInfo6 = templateConfig.getWallpaperInfo();
        if (((wallpaperInfo6 != null ? wallpaperInfo6.getCurrentLockWallpaperSource() : null) != LockWallpaperSource.WALLPAPER_MANAGER || templateConfig.isThirdPartyTheme()) && (wallpaperLayer = getWallpaperLayer()) != null) {
            wallpaperLayer.addGestureListener(hierarchyImageView);
        }
        if (!shouldEnableHierarchy()) {
            if (((viewModel == null || (hierarchyStatus2 = viewModel.getHierarchyStatus()) == null || hierarchyStatus2.getIntValue() != i2) ? 0 : i2) != 0) {
                Log.i("Keyguard-Editor-EffectsTemplateView", "createHierarchyLayer: init close");
                if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 8, null);
                }
            } else {
                if (viewModel != null && (hierarchyStatus = viewModel.getHierarchyStatus()) != null && hierarchyStatus.getIntValue() == 3) {
                    i3 = i2;
                }
                if (i3 != 0) {
                    Log.i("Keyguard-Editor-EffectsTemplateView", "createHierarchyLayer: init close");
                    if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 8, null);
                    }
                }
            }
        }
        return hierarchyImageView;
    }

    public final List depthAwareLayerOrder() {
        List depthLayerOrder;
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        IWallpaperLayer iWallpaperLayerM733getDepthVideoForegroundLayer = m733getDepthVideoForegroundLayer();
        View viewWallpaperLayerView = iWallpaperLayerM733getDepthVideoForegroundLayer != null ? iWallpaperLayerM733getDepthVideoForegroundLayer.wallpaperLayerView() : null;
        if (baseClockView == null || (depthLayerOrder = baseClockView.getDepthLayerOrder(getHierarchyLayer(), viewWallpaperLayerView)) == null) {
            return CollectionsKt.listOfNotNull((Object[]) new View[]{baseClockView != null ? baseClockView.getBackContentLayer() : null, getHierarchyLayer(), viewWallpaperLayerView, baseClockView != null ? baseClockView.getForeContentLayer() : null});
        }
        return depthLayerOrder;
    }

    public final void disableClippingForParents(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && (parent instanceof ViewGroup)) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            parent = viewGroup.getParent();
        }
    }

    public final void disableGlassFilter() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        CommonConfig templateConfig;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        CommonConfig templateConfig2 = getTemplateConfig();
        Integer numValueOf = (templateConfig2 == null || (lockscreenInfo4 = templateConfig2.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo4.getMagicType());
        Log.d("Keyguard-Editor-EffectsTemplateView", "disableGlassFilter magicType=" + numValueOf);
        MagicType magicType = MagicType.INSTANCE;
        if (magicType.isShader(numValueOf != null ? numValueOf.intValue() : 0) || (((templateConfig = getTemplateConfig()) != null && (lockscreenInfo3 = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) != null && wallpaperInfo3.getEnableBlur()) || magicType.isGlassFilter(isGlassClockEffect(), numValueOf))) {
            CommonConfig templateConfig3 = getTemplateConfig();
            if (templateConfig3 != null && (lockscreenInfo2 = templateConfig3.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                wallpaperInfo2.updateMagicType(0);
            }
            IWallpaperLayer wallpaperLayer = getWallpaperLayer();
            if (wallpaperLayer != null) {
                wallpaperLayer.switchGlassShader(0, null);
            }
            CommonConfig templateConfig4 = getTemplateConfig();
            if (templateConfig4 != null && (lockscreenInfo = templateConfig4.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.setEnableBlur(false);
            }
            EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
            if (viewModel != null) {
                EditFragmentViewModel.reloadTemplateConfigForWallpaperFilter$default(viewModel, getTemplateConfig(), false, 2, null);
            }
            CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
            if (combinedWallpaperView != null) {
                combinedWallpaperView.disableBlur();
            }
        }
        KgVisualCheckBox filterBtn = getFilterBtn();
        if (filterBtn != null) {
            filterBtn.disable();
        }
    }

    public final void disableHierarchyWhenGalleryOpened() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
        }
        CommonConfig templateConfig = getTemplateConfig();
        if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.updateMagicType(MagicType.INSTANCE.isDepth(Integer.valueOf(wallpaperInfo.getMagicType())) ? 0 : wallpaperInfo.getMagicType());
        }
        onHierarchyDisable();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void editingTemplateActionBeforeTakeScreenshot() {
        super.editingTemplateActionBeforeTakeScreenshot();
        setCurTemplateOtherShouldCheckRect();
    }

    public final void enableGlassFilter() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig templateConfig = getTemplateConfig();
        if (isWallpaperSupportGlassFilter((templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType())) {
            if (isGlassClockEffect()) {
                Log.d("Keyguard-Editor-EffectsTemplateView", "enableGlassFilter ignored for glass clock");
                disableGlassFilter();
                return;
            }
            if (isInThirdPartyWallpaperService() || isGalleryOpened()) {
                Log.d("Keyguard-Editor-EffectsTemplateView", "enableGlassFilter button disable");
                KgVisualCheckBox filterBtn = getFilterBtn();
                if (filterBtn != null) {
                    filterBtn.disable();
                    return;
                }
                return;
            }
            Log.d("Keyguard-Editor-EffectsTemplateView", "enableGlassFilter button enable");
            KgVisualCheckBox filterBtn2 = getFilterBtn();
            if (filterBtn2 != null) {
                filterBtn2.enable();
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void exitWithAnimation() {
        super.exitWithAnimation();
        hideHierarchyLoading();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.base.EditCallback
    @Nullable
    public Object getData(@NotNull String key, @NotNull Object... params) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(params, "params");
        if (Intrinsics.areEqual(key, "support_font_back")) {
            if (!(params.length == 0)) {
                Object obj = params[0];
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                return Boolean.valueOf(isWallpaperSupportFontBackFilter((String) obj));
            }
        }
        return super.getData(key, params);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @Nullable
    public String getFromWhereTag() {
        return "EffectsTemplateView";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @NotNull
    public List<View> getLayersForScreenshot() {
        return depthAwareLayerOrder();
    }

    @NotNull
    public final ImageSegmentCache getSegmentCache() {
        return this.segmentCache;
    }

    @Nullable
    public final ImageSegmentEditor getSegmentEditor() {
        return this.segmentEditor;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @Nullable
    public SegmentInfo getSubjectInfo() {
        ImageSegmentEditor imageSegmentEditor = this.segmentEditor;
        if (imageSegmentEditor != null) {
            return imageSegmentEditor.getSubjectInfo();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.aiwapper.IAIWallPaperChangeListener
    public void handleGenerateFailed(@Nullable final Triple<String, String, Boolean> triple, int i) {
        post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EffectsTemplateView.handleGenerateFailed$lambda$7(this.f$0, triple);
            }
        });
    }

    public final void hideHierarchyLoading() {
        if (editable() && shouldEnableHierarchy()) {
            View view = this.hierarchyLoadingView;
            if (view != null && view.getParent() != null) {
                AnimatorKt.alphaTo$default(view, 0.0f, 0.0f, 0.0f, null, 14, null);
                Unit unit = Unit.INSTANCE;
                view.setVisibility(8);
            }
            TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
            if (primaryButtonBar != null) {
                primaryButtonBar.enableAllExceptHierarchy();
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void initAIWallPaperManager() {
        AIWallPaperManager companion = AIWallPaperManager.Companion.getInstance();
        this.aiWallPaperManager = companion;
        if (companion != null) {
            companion.init(getContext(), this);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void initHierarchyButton(TemplateButtonBar primaryButtonBar) {
        IntWrapper hierarchyStatus;
        Intrinsics.checkNotNullParameter(primaryButtonBar, "primaryButtonBar");
        final EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        KgVisualCheckBox kgVisualCheckBoxAddHierarchyButton$default = TemplateButtonBar.addHierarchyButton$default(primaryButtonBar, 0, 1, null);
        if (!shouldEnableHierarchy()) {
            kgVisualCheckBoxAddHierarchyButton$default.setVisibility(8);
        }
        kgVisualCheckBoxAddHierarchyButton$default.enableDoubleClick(true);
        kgVisualCheckBoxAddHierarchyButton$default.setOnCheckListener(new KgVisualCheckBox.OnCheckListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda8
            @Override // com.miui.keyguard.editor.view.KgVisualCheckBox.OnCheckListener
            public final void onChecked(KgVisualCheckBox kgVisualCheckBox) throws IOException {
                EffectsTemplateView.initHierarchyButton$lambda$24$lambda$23$lambda$17(this.f$0, viewModel, kgVisualCheckBox);
            }
        });
        kgVisualCheckBoxAddHierarchyButton$default.setOnUncheckListener(new KgVisualCheckBox.OnUncheckListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda9
            @Override // com.miui.keyguard.editor.view.KgVisualCheckBox.OnUncheckListener
            public final void onUncheck(KgVisualCheckBox kgVisualCheckBox) throws IOException {
                EffectsTemplateView.initHierarchyButton$lambda$24$lambda$23$lambda$19(this.f$0, viewModel, kgVisualCheckBox);
            }
        });
        kgVisualCheckBoxAddHierarchyButton$default.setDisableClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EffectsTemplateView.initHierarchyButton$lambda$24$lambda$23$lambda$22(this.f$0, view);
            }
        });
        setHierarchyBtn(kgVisualCheckBoxAddHierarchyButton$default);
        updateHierarchyButton((viewModel == null || (hierarchyStatus = viewModel.getHierarchyStatus()) == null) ? 2 : hierarchyStatus.getIntValue());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        WallpaperInfo wallpaperInfo = templateConfig.getWallpaperInfo();
        if (wallpaperInfo != null && -1 == getTemplateSource()) {
            if (!wallpaperInfo.getSupportSubject()) {
                wallpaperInfo.setSupportSubject(wallpaperInfo.getSubjectBitmap() != null);
            }
            if (MagicType.INSTANCE.isDepth(Integer.valueOf(wallpaperInfo.getMagicType()))) {
                wallpaperInfo.updateMagicType(wallpaperInfo.getSubjectBitmap() != null ? MagicType.TYPE_DEPTH : 0);
            }
        }
        super.initTemplateBean(templateConfig);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    @Nullable
    public TemplateButtonBar initializePrimaryButtonBar(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        TemplateButtonBar templateButtonBarInitializePrimaryButtonBar = super.initializePrimaryButtonBar(parent);
        if (templateButtonBarInitializePrimaryButtonBar == null) {
            return null;
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (DeviceUtil.INSTANCE.isSupportHierarchyDevices() && viewModel != null) {
            this.segmentEditor = new ImageSegmentEditor(viewModel);
        }
        return templateButtonBarInitializePrimaryButtonBar;
    }

    public final boolean isPresetVideoDepthBlocked(WallpaperInfo wallpaperInfo) {
        return Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "video") && wallpaperInfo.getVideoType() == 0 && Wallpaper.Companion.isSystemWallpaper(wallpaperInfo.getImagePath());
    }

    public final boolean isWallpaperSupportFontBackFilter(String str) {
        Wallpaper.Companion companion = Wallpaper.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (!companion.isVideoSupportGlassFilter(context) && (Intrinsics.areEqual(str, "video") || Intrinsics.areEqual(str, "sensor"))) {
            Log.i("Keyguard-Editor-EffectsTemplateView", "isWallpaperSupportBackFilter: type = " + str);
            return false;
        }
        if (isInThirdPartyWallpaperService()) {
            Log.i("Keyguard-Editor-EffectsTemplateView", "isWallpaperSupportBackFilter: isThirdPartyWallpaper");
            return false;
        }
        if (str == null) {
            Log.w("Keyguard-Editor-EffectsTemplateView", "isWallpaperSupportBackFilter type is null");
            return false;
        }
        if (companion.isStaticWallpaper(str)) {
            Log.w("Keyguard-Editor-EffectsTemplateView", "isStaticWallpaper true");
            return true;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        return !deviceUtil.isPad() && deviceUtil.isCpuAndGpuHighLevel();
    }

    public final boolean isWallpaperSupportGlassFilter(@Nullable String str) {
        Wallpaper.Companion companion = Wallpaper.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (!companion.isVideoSupportGlassFilter(context) && (Intrinsics.areEqual(str, "video") || Intrinsics.areEqual(str, "sensor") || Intrinsics.areEqual(str, "linkage_video"))) {
            Log.i("Keyguard-Editor-EffectsTemplateView", "isWallpaperSupportGlassFilter: type = " + str);
            return false;
        }
        if (isInThirdPartyWallpaperService()) {
            Log.i("Keyguard-Editor-EffectsTemplateView", "isWallpaperSupportGlassFilter: isThirdPartyWallpaper");
            return false;
        }
        if (str == null) {
            Log.w("Keyguard-Editor-EffectsTemplateView", "isWallpaperSupportGlassFilter type is null");
            return false;
        }
        if (companion.isStaticWallpaper(str)) {
            Log.w("Keyguard-Editor-EffectsTemplateView", "isStaticWallpaper true");
            return true;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        return !deviceUtil.isPad() && deviceUtil.isCpuAndGpuHighLevel();
    }

    public final boolean isWallpaperSupportHierarchy(String str, Integer num) {
        if (Intrinsics.areEqual("sensor", str) || Intrinsics.areEqual("video", str) || Intrinsics.areEqual("linkage_video", str) || isInThirdPartyWallpaperService()) {
            return Intrinsics.areEqual("video", str) && Wallpaper.Companion.isLivePhotoResource(num);
        }
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void loadTemplate(@Nullable CommonConfig commonConfig) {
        IWallpaperLayer wallpaperLayer;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        super.loadTemplate(commonConfig);
        refreshGlassFilterEnable((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType());
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

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onDestroy() {
        super.onDestroy();
        AIWallPaperManager companion = AIWallPaperManager.Companion.getInstance();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        companion.release(context);
        FastPlayer fastPlayer = this.fastPlayer;
        if (fastPlayer != null) {
            fastPlayer.release();
        }
        this.fastPlayer = null;
    }

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
    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.base.EditCallback
    @CallSuper
    public void onEdited(int i, @Nullable final Object obj) throws IOException {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        boolean z;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        TemplateConfig lockscreenInfo5;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo6;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo7;
        WallpaperInfo wallpaperInfo6;
        BaseClockView baseClockView;
        TemplateConfig lockscreenInfo8;
        WallpaperInfo wallpaperInfo7;
        TemplateConfig lockscreenInfo9;
        WallpaperInfo wallpaperInfo8;
        ExtraInfo extraInfo;
        TemplateConfig lockscreenInfo10;
        WallpaperInfo wallpaperInfo9;
        HomeConfig homeInfo;
        TemplateConfig lockscreenInfo11;
        TemplateConfig lockscreenInfo12;
        WallpaperInfo wallpaperInfo10;
        TemplateConfig lockscreenInfo13;
        WallpaperInfo wallpaperInfo11;
        TemplateConfig lockscreenInfo14;
        WallpaperInfo wallpaperInfo12;
        TemplateConfig lockscreenInfo15;
        HierarchyImageView hierarchyImageView;
        HierarchyImageAvoidController avoidController;
        TemplateConfig lockscreenInfo16;
        HierarchyImageView hierarchyImageView2;
        HierarchyImageAvoidController avoidController2;
        TemplateChangeRecorder templateChangeRecorder;
        HierarchyImageAvoidController avoidController3;
        TemplateConfig lockscreenInfo17;
        WallpaperInfo wallpaperInfo13;
        CommonConfig templateConfig = getTemplateConfig();
        Integer numValueOf = null;
        String resourceType = (templateConfig == null || (lockscreenInfo17 = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo13 = lockscreenInfo17.getWallpaperInfo()) == null) ? null : wallpaperInfo13.getResourceType();
        super.onEdited(i, obj);
        EditFragmentViewModel.Companion companion = EditFragmentViewModel.Companion;
        EditFragmentViewModel viewModel = companion.getViewModel(getEditFragment());
        z = false;
        z = false;
        boolean z2 = false;
        if (i == 10) {
            if (!shouldEnableHierarchy()) {
                onGlassFilterSelected(obj);
                return;
            }
            CommonConfig templateConfig2 = getTemplateConfig();
            if (templateConfig2 == null || (lockscreenInfo = templateConfig2.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) {
                return;
            }
            MagicType magicType = MagicType.INSTANCE;
            if (magicType.isDepth(Integer.valueOf(wallpaperInfo.getMagicType()))) {
                numValueOf = obj instanceof Integer ? (Integer) obj : null;
                if (magicType.isShader(numValueOf != null ? numValueOf.intValue() : 0)) {
                    showEffectsTipDialog(new Function0() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda7
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return EffectsTemplateView.onEdited$lambda$35$lambda$34(this.f$0, obj);
                        }
                    });
                    return;
                }
            }
            onGlassFilterSelected(obj);
            return;
        }
        int i2 = 8;
        int i3 = 2;
        if (i != 30) {
            if (i == 40) {
                setEditHierarchy(true);
                if (!supportFilter() || obj == null) {
                    onHierarchySelected$default(this, obj, false, 2, null);
                    return;
                }
                CommonConfig templateConfig3 = getTemplateConfig();
                if (templateConfig3 == null || (lockscreenInfo13 = templateConfig3.getLockscreenInfo()) == null || (wallpaperInfo11 = lockscreenInfo13.getWallpaperInfo()) == null) {
                    return;
                }
                MagicType magicType2 = MagicType.INSTANCE;
                if (magicType2.isShader(wallpaperInfo11.getMagicType()) || (magicType2.isOrigin(wallpaperInfo11.getMagicType()) && wallpaperInfo11.getEnableBlur())) {
                    showEffectsTipDialog(new Function0() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return EffectsTemplateView.onEdited$lambda$33$lambda$32(this.f$0, obj);
                        }
                    });
                    return;
                } else {
                    onHierarchySelected(obj, true);
                    return;
                }
            }
            if (i == 90) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                Boolean bool = (Boolean) obj;
                boolean zBooleanValue = bool.booleanValue();
                CommonConfig templateConfig4 = getTemplateConfig();
                if (templateConfig4 != null && (lockscreenInfo14 = templateConfig4.getLockscreenInfo()) != null && (wallpaperInfo12 = lockscreenInfo14.getWallpaperInfo()) != null) {
                    wallpaperInfo12.setEnableBlur(zBooleanValue);
                }
                boolean zBooleanValue2 = bool.booleanValue();
                Log.i("Keyguard-Editor-EffectsTemplateView", "onEdited: enableBlur =" + zBooleanValue2);
                CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) getWallpaperLayer();
                if (combinedWallpaperView != null) {
                    CombinedWallpaperView.setEnableBlurStatus$default(combinedWallpaperView, zBooleanValue2, false, 2, null);
                    return;
                }
                return;
            }
            switch (i) {
                case 35:
                    CommonConfig templateConfig5 = getTemplateConfig();
                    if (templateConfig5 != null && (lockscreenInfo15 = templateConfig5.getLockscreenInfo()) != null && lockscreenInfo15.isPresetTemplate() && (hierarchyImageView = (HierarchyImageView) getHierarchyLayer()) != null && (avoidController = hierarchyImageView.getAvoidController()) != null) {
                        avoidController.setUserOpenHierarchy(false);
                        break;
                    }
                    break;
                case 36:
                    CommonConfig templateConfig6 = getTemplateConfig();
                    if (templateConfig6 != null && (lockscreenInfo16 = templateConfig6.getLockscreenInfo()) != null && lockscreenInfo16.isPresetTemplate() && (hierarchyImageView2 = (HierarchyImageView) getHierarchyLayer()) != null && (avoidController2 = hierarchyImageView2.getAvoidController()) != null) {
                        avoidController2.setUserOpenHierarchy(true);
                        break;
                    }
                    break;
                case 37:
                    HierarchyImageView hierarchyImageView3 = (HierarchyImageView) getHierarchyLayer();
                    if (hierarchyImageView3 != null) {
                        hierarchyImageView3.setImageBitmap(null);
                        hierarchyImageView3.setVisibility(8);
                    }
                    refreshGlassFilterEnable$default(this, null, 1, null);
                    ImageSegmentEditor imageSegmentEditor = this.segmentEditor;
                    if (imageSegmentEditor != null) {
                        imageSegmentEditor.setSubjectInfo(null);
                    }
                    HierarchyImageView hierarchyImageView4 = (HierarchyImageView) getHierarchyLayer();
                    if (hierarchyImageView4 != null && (avoidController3 = hierarchyImageView4.getAvoidController()) != null) {
                        avoidController3.setUserOpenHierarchy(false);
                    }
                    if (shouldOpenVideoDepth()) {
                        KgVisualCheckBox hierarchyBtn = getHierarchyBtn();
                        if (hierarchyBtn != null) {
                            hierarchyBtn.enable();
                        }
                        KgVisualCheckBox hierarchyBtn2 = getHierarchyBtn();
                        if (hierarchyBtn2 != null) {
                            hierarchyBtn2.setCheckedOnly(true);
                        }
                    } else if (shouldEnableVideoDepthWithCloseStatus()) {
                        KgVisualCheckBox hierarchyBtn3 = getHierarchyBtn();
                        if (hierarchyBtn3 != null) {
                            hierarchyBtn3.enable();
                        }
                        KgVisualCheckBox hierarchyBtn4 = getHierarchyBtn();
                        if (hierarchyBtn4 != null) {
                            hierarchyBtn4.setCheckedOnly(false);
                        }
                    } else {
                        KgVisualCheckBox hierarchyBtn5 = getHierarchyBtn();
                        if (hierarchyBtn5 != null) {
                            hierarchyBtn5.disable();
                        }
                        onHierarchyDisable();
                    }
                    EditFragmentViewModel viewModel2 = companion.getViewModel(getEditFragment());
                    if (viewModel2 != null && (templateChangeRecorder = viewModel2.getTemplateChangeRecorder()) != null) {
                        templateChangeRecorder.clearAllRecord();
                    }
                    updateAiGenerateBtnStatus$default(this, false, 1, null);
                    break;
                case 38:
                    EditorActivityViewModel.Companion companion2 = EditorActivityViewModel.Companion;
                    Context context = getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    BuildersKt__Builders_commonKt.launch$default(companion2.getCoroutineScope(context), Dispatchers.getMain(), null, new AnonymousClass9(viewModel, null), 2, null);
                    break;
            }
            return;
        }
        WallpaperTypeInfo wallpaperTypeInfo = (WallpaperTypeInfo) obj;
        if (isVideoToImageTransition(resourceType, wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null)) {
            cleanupVideoForCrossFade();
        }
        getDynamicSwitchCallback().removeDownloadButton();
        this.wallpaperTypeInfo = wallpaperTypeInfo;
        AIWallPaperManager.Companion.getInstance().onWallPaperSelected(wallpaperTypeInfo);
        CommonConfig templateConfig7 = getTemplateConfig();
        setBackgroundBeforeVideoPlay((templateConfig7 == null || (lockscreenInfo12 = templateConfig7.getLockscreenInfo()) == null || (wallpaperInfo10 = lockscreenInfo12.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo10.getMagicType()), wallpaperTypeInfo);
        setWallpaperHasChanged(true);
        CommonConfig templateConfig8 = getTemplateConfig();
        if (templateConfig8 != null && (lockscreenInfo11 = templateConfig8.getLockscreenInfo()) != null) {
            lockscreenInfo11.setThirdPartyWallpaper(false);
        }
        CommonConfig templateConfig9 = getTemplateConfig();
        if (templateConfig9 != null && (homeInfo = templateConfig9.getHomeInfo()) != null) {
            homeInfo.setThirdPartyWallpaper(false);
        }
        changeEffectData(wallpaperTypeInfo);
        refreshGlassFilterEnable(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null);
        if (isGalleryOpened()) {
            if (viewModel != null) {
                z = true;
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 12, null);
            } else {
                z = true;
            }
            onWallpaperChangedWhenGalleryOpened(wallpaperTypeInfo);
        } else {
            ImageSegmentEditor imageSegmentEditor2 = this.segmentEditor;
            if (imageSegmentEditor2 != null) {
                imageSegmentEditor2.setSubjectInfo(null);
            }
            closeHierarchy();
            onWallpaperChanged(wallpaperTypeInfo);
            i2 = 8;
            i3 = 2;
            z = true;
        }
        View depthVideoForegroundLayer = getDepthVideoForegroundLayer();
        if (depthVideoForegroundLayer != null) {
            depthVideoForegroundLayer.setVisibility(i2);
        }
        startForcePlayerWithDepthVideo("select wallpaper");
        setLongClickListenerForLivePhoto();
        Wallpaper.Companion companion3 = Wallpaper.Companion;
        CommonConfig templateConfig10 = getTemplateConfig();
        if (companion3.isLivePhotoResource((templateConfig10 == null || (lockscreenInfo10 = templateConfig10.getLockscreenInfo()) == null || (wallpaperInfo9 = lockscreenInfo10.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo9.getVideoType()))) {
            CommonConfig templateConfig11 = getTemplateConfig();
            if (templateConfig11 == null || (lockscreenInfo9 = templateConfig11.getLockscreenInfo()) == null || (wallpaperInfo8 = lockscreenInfo9.getWallpaperInfo()) == null || (extraInfo = wallpaperInfo8.getExtraInfo()) == null) {
                CommonConfig templateConfig12 = getTemplateConfig();
                if (templateConfig12 != null && (lockscreenInfo7 = templateConfig12.getLockscreenInfo()) != null && (wallpaperInfo6 = lockscreenInfo7.getWallpaperInfo()) != null) {
                    wallpaperInfo6.setExtraInfo(new ExtraInfo(wallpaperTypeInfo != null ? wallpaperTypeInfo.getExtraPath() : null, null, i3, null));
                }
            } else {
                extraInfo.setOriginLivePhotoImagePath(wallpaperTypeInfo != null ? wallpaperTypeInfo.getExtraPath() : null);
            }
            DepthPresetDownloadButton depthPresetDownloadBtn = getDepthPresetDownloadBtn();
            if (depthPresetDownloadBtn != null) {
                CommonConfig templateConfig13 = getTemplateConfig();
                if (templateConfig13 != null) {
                    depthPresetDownloadBtn.refreshConfig(templateConfig13);
                }
                depthPresetDownloadBtn.setVisibility(i2);
            }
            DynamicSwitchButton dynamicSwitchBtn = getDynamicSwitchBtn();
            if (dynamicSwitchBtn == null) {
                TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
                dynamicSwitchBtn = primaryButtonBar != null ? primaryButtonBar.addDynamicSwitchButton() : null;
            }
            setDynamicSwitchBtn(dynamicSwitchBtn);
            DynamicSwitchButton dynamicSwitchBtn2 = getDynamicSwitchBtn();
            if (dynamicSwitchBtn2 != null) {
                CommonConfig templateConfig14 = getTemplateConfig();
                if (templateConfig14 != null) {
                    dynamicSwitchBtn2.refreshLockConfig(templateConfig14.getLockscreenInfo(), isHistoryTemplate());
                }
                dynamicSwitchBtn2.setClickCallback(new DynamicButtonClickCallBack(new WeakReference(this)));
                dynamicSwitchBtn2.setDynamicSwitchCallback(getDynamicSwitchCallback());
                dynamicSwitchBtn2.setVisibility(0);
            }
            View presetButtonGroup = getPresetButtonGroup();
            if (presetButtonGroup == null) {
                TemplateButtonBar primaryButtonBar2 = getPrimaryButtonBar();
                presetButtonGroup = primaryButtonBar2 != null ? primaryButtonBar2.addDynamicButtonGroup() : null;
            }
            setPresetButtonGroup(presetButtonGroup);
            CommonConfig templateConfig15 = getTemplateConfig();
            if (companion3.isNormalLivePhotoResource((templateConfig15 == null || (lockscreenInfo8 = templateConfig15.getLockscreenInfo()) == null || (wallpaperInfo7 = lockscreenInfo8.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo7.getVideoType()))) {
                if (shouldEnableHierarchy() && FileUtil.isSupportVideoDepth() && (baseClockView = (BaseClockView) getClockLayer()) != null && baseClockView.supportHierarchy() == z) {
                    onClickDepthButtonClick();
                }
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
        } else {
            getDynamicSwitchCallback().removeDynamicSwitchButton();
        }
        BaseClockView baseClockView2 = (BaseClockView) getClockLayer();
        if (baseClockView2 != null) {
            WallpaperEditor.Companion companion4 = WallpaperEditor.Companion;
            CommonConfig templateConfig16 = getTemplateConfig();
            boolean zCanScale = companion4.canScale((templateConfig16 == null || (lockscreenInfo6 = templateConfig16.getLockscreenInfo()) == null || (wallpaperInfo5 = lockscreenInfo6.getWallpaperInfo()) == null) ? null : wallpaperInfo5.getResourceType());
            EditFragment editFragment = baseClockView2.getEditFragment();
            Integer numValueOf2 = editFragment != null ? Integer.valueOf(editFragment.getTemplateStatus()) : null;
            if (zCanScale && numValueOf2 != null && numValueOf2.intValue() == 0) {
                z2 = z;
            }
            baseClockView2.switchTips(z2);
            baseClockView2.onEdited(30, obj);
        }
        CommonConfig templateConfig17 = getTemplateConfig();
        if (Intrinsics.areEqual((templateConfig17 == null || (lockscreenInfo5 = templateConfig17.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo5.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getResourceType(), "video")) {
            CommonConfig templateConfig18 = getTemplateConfig();
            boolean zIsPresetVideoDepthBlocked = isPresetVideoDepthBlocked((templateConfig18 == null || (lockscreenInfo4 = templateConfig18.getLockscreenInfo()) == null) ? null : lockscreenInfo4.getWallpaperInfo());
            CommonConfig templateConfig19 = getTemplateConfig();
            Log.d("Keyguard-Editor-EffectsTemplateView", "TYPE_WALLPAPER_SELECTED: presetVideoDepthBlocked=" + zIsPresetVideoDepthBlocked + ", imagePath=" + ((templateConfig19 == null || (lockscreenInfo3 = templateConfig19.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getImagePath()));
            if (FileUtil.isSupportVideoDepth() && !zIsPresetVideoDepthBlocked) {
                MagicType magicType3 = MagicType.INSTANCE;
                CommonConfig templateConfig20 = getTemplateConfig();
                if (templateConfig20 != null && (lockscreenInfo2 = templateConfig20.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                    numValueOf = Integer.valueOf(wallpaperInfo2.getMagicType());
                }
                if (magicType3.isDepthVideo(numValueOf)) {
                    if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 5, false, false, false, 14, null);
                    }
                } else if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 6, false, false, false, 14, null);
                }
            } else if (viewModel != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
            }
        }
        resetClockFontColor();
        updateAiGenerateBtnStatus(z);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onForwardTransitionComplete() {
        super.onForwardTransitionComplete();
        View view = this.hierarchyLoadingView;
        if (view != null) {
            view.setAlpha(getAlpha());
        }
    }

    public final void onGlassFilterSelected(Object obj) {
        TemplateChangeRecorder templateChangeRecorder;
        StatusRecord lastStatusRecord;
        TemplateChangeRecorder templateChangeRecorder2;
        StatusRecord lastStatusRecord2;
        LifecycleCoroutineScope lifecycleScope;
        IntWrapper hierarchyStatus;
        IntWrapper hierarchyStatus2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        Integer num = (Integer) obj;
        int iIntValue = num.intValue();
        CommonConfig templateConfig = getTemplateConfig();
        if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.updateMagicType(iIntValue);
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        List<WallpaperFilter> wallpaperFilter = getWallpaperFilter();
        Integer hierarchyStatus3 = null;
        TemplateChangeRecorder templateChangeRecorder3 = viewModel != null ? viewModel.getTemplateChangeRecorder() : null;
        MagicType magicType = MagicType.INSTANCE;
        if (magicType.isShader(iIntValue) && templateChangeRecorder3 != null) {
            templateChangeRecorder3.clearRecord(4);
        }
        WallpaperFilterView wallpaperFilterView = getWallpaperFilterView();
        int currentFilterGroupId = wallpaperFilterView != null ? wallpaperFilterView.getCurrentFilterGroupId() : -1;
        boolean z = currentFilterGroupId > -1 && currentFilterGroupId < wallpaperFilter.size();
        if (z && !wallpaperFilter.get(currentFilterGroupId).getSupportMatte()) {
            if (!magicType.isDepth(num) && templateChangeRecorder3 != null) {
                IntWrapper matteStatus = viewModel.getMatteStatus();
                templateChangeRecorder3.putLastStatusRecord(3, new StatusRecord(null, null, matteStatus != null ? Integer.valueOf(matteStatus.getIntValue()) : null, 3, null));
            }
            closeEffect();
        }
        if (z && !wallpaperFilter.get(currentFilterGroupId).getSupportHierarchy()) {
            Log.d("Keyguard-Editor-EffectsTemplateView", "filter:" + iIntValue + " not support hierarchy");
            Integer numValueOf = (viewModel == null || (hierarchyStatus2 = viewModel.getHierarchyStatus()) == null) ? null : Integer.valueOf(hierarchyStatus2.getIntValue());
            if ((numValueOf == null || numValueOf.intValue() != 0) && (numValueOf == null || numValueOf.intValue() != 2)) {
                if (templateChangeRecorder3 != null) {
                    templateChangeRecorder3.putLastStatusRecord(2, new StatusRecord((viewModel == null || (hierarchyStatus = viewModel.getHierarchyStatus()) == null) ? null : Integer.valueOf(hierarchyStatus.getIntValue()), null, null, 6, null));
                }
                if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 14, null);
                }
            }
        }
        if (magicType.isFrontBack(num)) {
            Context context = getContext();
            AppCompatActivity appCompatActivity = context instanceof AppCompatActivity ? (AppCompatActivity) context : null;
            if (appCompatActivity == null || (lifecycleScope = LifecycleOwnerKt.getLifecycleScope(appCompatActivity)) == null) {
                return;
            }
            BuildersKt__Builders_commonKt.launch$default(lifecycleScope, null, null, new C01121(iIntValue, null), 3, null);
            return;
        }
        IWallpaperLayer wallpaperLayer = getWallpaperLayer();
        if (wallpaperLayer != null) {
            wallpaperLayer.switchGlassShader(iIntValue, null);
        }
        if (!magicType.isOrigin(iIntValue)) {
            if (magicType.isGlassVertical(iIntValue)) {
                Integer matteStatus2 = (viewModel == null || (templateChangeRecorder = viewModel.getTemplateChangeRecorder()) == null || (lastStatusRecord = templateChangeRecorder.getLastStatusRecord(3)) == null) ? null : lastStatusRecord.getMatteStatus();
                if (matteStatus2 != null) {
                    int iIntValue2 = matteStatus2.intValue();
                    if (viewModel != null) {
                        EditFragmentViewModel.updateMatteStatus$default(viewModel, iIntValue2, false, 2, null);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        if (viewModel != null && (templateChangeRecorder2 = viewModel.getTemplateChangeRecorder()) != null && (lastStatusRecord2 = templateChangeRecorder2.getLastStatusRecord(2)) != null) {
            hierarchyStatus3 = lastStatusRecord2.getHierarchyStatus();
        }
        if (hierarchyStatus3 != null) {
            int iIntValue3 = hierarchyStatus3.intValue();
            if (iIntValue3 == 3) {
                if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 1, false, false, false, 14, null);
                }
            } else if (viewModel != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, iIntValue3, false, false, false, 14, null);
            }
        }
    }

    @CallSuper
    public void onHierarchyDisable() {
        setHierarchyEnable(false);
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.onHierarchyDisable();
        }
    }

    @CallSuper
    public void onHierarchyEnable() {
        setHierarchyEnable(true);
        BaseClockView baseClockView = (BaseClockView) getClockLayer();
        if (baseClockView != null) {
            baseClockView.onHierarchyEnable();
        }
    }

    /* JADX WARN: Code duplicated, block: B:66:0x010d  */
    public void onHierarchySelected(@Nullable Object obj, boolean z) {
        Integer matteStatus;
        Integer magicType;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig templateConfig;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateChangeRecorder templateChangeRecorder;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo3;
        TemplateChangeRecorder templateChangeRecorder2;
        IntWrapper wallpaperFilterId;
        HierarchyImageBrightAnimController brightAnimController;
        if ((obj instanceof SegmentInfo ? (SegmentInfo) obj : null) == null) {
            EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
            StatusRecord lastStatusRecord = (viewModel == null || (templateChangeRecorder = viewModel.getTemplateChangeRecorder()) == null) ? null : templateChangeRecorder.getLastStatusRecord(4);
            if (isGlassClockEffect()) {
                if (MagicType.INSTANCE.isMagicTypeSupportGlassClockEffect(lastStatusRecord != null ? lastStatusRecord.getMagicType() : null)) {
                    if (lastStatusRecord != null && (magicType = lastStatusRecord.getMagicType()) != null) {
                        EditFragmentViewModel.updateWallpaperFilterId$default(viewModel, magicType.intValue(), false, 2, null);
                    }
                    if (lastStatusRecord != null && (matteStatus = lastStatusRecord.getMatteStatus()) != null) {
                        EditFragmentViewModel.updateMatteStatus$default(viewModel, matteStatus.intValue(), false, 2, null);
                    }
                }
            } else {
                if (lastStatusRecord != null) {
                    EditFragmentViewModel.updateWallpaperFilterId$default(viewModel, magicType.intValue(), false, 2, null);
                }
                if (lastStatusRecord != null) {
                    EditFragmentViewModel.updateMatteStatus$default(viewModel, matteStatus.intValue(), false, 2, null);
                }
            }
            Log.e("Keyguard-Editor-EffectsTemplateView", "onHierarchySelected data is not SegmentInfo, closeHierarchy");
            CommonConfig templateConfig2 = getTemplateConfig();
            if (templateConfig2 != null && (lockscreenInfo = templateConfig2.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null && wallpaperInfo.getMagicType() == 20000 && (templateConfig = getTemplateConfig()) != null && (lockscreenInfo2 = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
                wallpaperInfo2.resetMagicType("user close hierarchy");
            }
            closeHierarchy();
            return;
        }
        EditFragmentViewModel viewModel2 = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        if (hierarchyImageView != null) {
            hierarchyImageView.setImageBitmap(((SegmentInfo) obj).getBitmap());
            CrossListFolmeUtil crossListFolmeUtil = CrossListFolmeUtil.INSTANCE;
            crossListFolmeUtil.resetViewAlpha(hierarchyImageView);
            hierarchyImageView.setVisibility(0);
            CrossListFolmeUtil.startHierarchyAlphaAnimation$default(crossListFolmeUtil, hierarchyImageView, true, null, 4, null);
            if (z && (brightAnimController = hierarchyImageView.getBrightAnimController()) != null) {
                brightAnimController.changeBitmapBrightNessAnim();
            }
            IWallpaperLayer wallpaperLayer = getWallpaperLayer();
            hierarchyImageView.onChanged(wallpaperLayer != null ? wallpaperLayer.getWallpaperMatrix() : null);
        }
        if (MagicType.INSTANCE.isShader((viewModel2 == null || (wallpaperFilterId = viewModel2.getWallpaperFilterId()) == null) ? 0 : wallpaperFilterId.getIntValue()) && viewModel2 != null && (templateChangeRecorder2 = viewModel2.getTemplateChangeRecorder()) != null) {
            templateChangeRecorder2.putLastStatusRecord(4, new StatusRecord(null, Integer.valueOf(viewModel2.getWallpaperFilterId().getIntValue()), Integer.valueOf(viewModel2.getMatteStatus().getIntValue()), 1, null));
        }
        closeEffect();
        IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
        if (wallpaperLayer2 != null) {
            wallpaperLayer2.switchGlassShader(0, null);
        }
        CommonConfig templateConfig3 = getTemplateConfig();
        if (templateConfig3 != null && (lockscreenInfo4 = templateConfig3.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo4.getWallpaperInfo()) != null) {
            wallpaperInfo3.updateMagicType(MagicType.TYPE_DEPTH);
        }
        CommonConfig templateConfig4 = getTemplateConfig();
        if (templateConfig4 != null && (lockscreenInfo3 = templateConfig4.getLockscreenInfo()) != null && (clockInfo = lockscreenInfo3.getClockInfo()) != null) {
            clockInfo.setEnableDiffusion(false);
        }
        if (viewModel2 != null) {
            viewModel2.reloadTemplateConfigForWallpaperFilter(getTemplateConfig(), !getInitFinish());
        }
        onHierarchyEnable();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        HierarchyImageView hierarchyImageView;
        HierarchyImageAvoidController avoidController;
        if (z && (hierarchyImageView = (HierarchyImageView) getHierarchyLayer()) != null && (avoidController = hierarchyImageView.getAvoidController()) != null) {
            avoidController.setUpAvoidRect(hierarchyAvoidRect());
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onScreenSizeChangedWhenEditable(Configuration newConfig) {
        HierarchyImageAvoidController avoidController;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onScreenSizeChangedWhenEditable(newConfig);
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        if (hierarchyImageView == null || (avoidController = hierarchyImageView.getAvoidController()) == null) {
            return;
        }
        avoidController.resetAvoidRect(hierarchyAvoidRect());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onStart() {
        super.onStart();
        setStopped(false);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.base.TemplateApplier.ApplyCallback
    public void onStartApply(boolean z) {
        super.onStartApply(z);
        AIWallPaperManager companion = AIWallPaperManager.Companion.getInstance();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        companion.release(context);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void onStop() {
        super.onStop();
        setStopped(true);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public /* bridge */ /* synthetic */ void onSuperSaveChange(boolean z) {
        super.onSuperSaveChange(z);
    }

    @CallSuper
    public void onTimeGlassDiffusionChanged(boolean z) {
        onClockInfoUpdate();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.aiwapper.IAIWallPaperChangeListener
    public void onWallPaperChange(@Nullable Bundle bundle) {
        Log.i("AIWallPaperManager", "onWallPaperChange" + bundle);
        Intent intent = bundle != null ? (Intent) bundle.getParcelable("data") : null;
        Intent intent2 = intent != null ? intent : null;
        if (intent2 != null) {
            intent2.putExtra("isDirectGenerate", true);
        }
        if (intent2 != null) {
            intent2.putExtra("isAiGenerated", true);
        }
        WallpaperEditor wallpaperEditor = getWallpaperEditor();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        wallpaperEditor.handleWallpaperTypeInfo(intent2, context);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView, com.miui.keyguard.editor.edit.aiwapper.IAIWallPaperChangeListener
    public void onWallPaperReset(@Nullable final WallpaperTypeInfo wallpaperTypeInfo) {
        post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f$0.onEdited(30, wallpaperTypeInfo);
            }
        });
    }

    public final void onWallpaperChanged(WallpaperTypeInfo wallpaperTypeInfo) {
        if (TextUtils.isEmpty(wallpaperTypeInfo != null ? wallpaperTypeInfo.getResourcePath() : null)) {
            Log.e("Keyguard-Editor-EffectsTemplateView", "resourcePath is empty");
            return;
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (wallpaperTypeInfo != null) {
            Log.i("Keyguard-Editor-EffectsTemplateView", "onWallpaperChanged type = " + wallpaperTypeInfo.getType());
            Wallpaper.Companion companion = Wallpaper.Companion;
            if (companion.isLivePhotoResource(wallpaperTypeInfo.getVideoType()) && DeviceUtil.INSTANCE.isSupportHierarchyDevices()) {
                setAllowToClickDynamicSwitchBtn(false);
            }
            if (Intrinsics.areEqual(wallpaperTypeInfo.getType(), "image") || companion.isLivePhotoResource(wallpaperTypeInfo.getVideoType())) {
                if (DeviceUtil.INSTANCE.isSupportHierarchyDevices()) {
                    processHierarchy(wallpaperTypeInfo, false, true);
                    return;
                } else {
                    refreshGlassFilterEnable(wallpaperTypeInfo.getType());
                    return;
                }
            }
            ImageSegmentEditor imageSegmentEditor = this.segmentEditor;
            if (imageSegmentEditor != null) {
                imageSegmentEditor.setSubjectInfo(null);
            }
            if (viewModel != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
            }
            refreshGlassFilterEnable(wallpaperTypeInfo.getType());
        }
    }

    public final void onWallpaperChangedWhenGalleryOpened(WallpaperTypeInfo wallpaperTypeInfo) {
        if (wallpaperTypeInfo == null || wallpaperTypeInfo.getMattingMode() != 2) {
            ImageSegmentEditor imageSegmentEditor = this.segmentEditor;
            if (imageSegmentEditor != null) {
                imageSegmentEditor.setSubjectInfo(null);
                return;
            }
            return;
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new C01131(wallpaperTypeInfo, this, null), 3, null);
    }

    public final void processHierarchy(WallpaperTypeInfo wallpaperTypeInfo, boolean z, boolean z2) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CoroutineScope viewModelScope;
        CoroutineScope viewModelScope2;
        Log.d("Keyguard-Editor-EffectsTemplateView", "processHierarchy WallpaperTypeInfo : " + wallpaperTypeInfo);
        if (wallpaperTypeInfo.getMattingMode() == 2) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            EditorActivityViewModel viewModel = companion.getViewModel(context);
            if (viewModel == null) {
                DataUtilKt.throwIfDebug("context error " + getContext());
            }
            if (viewModel == null || (viewModelScope2 = ViewModelKt.getViewModelScope(viewModel)) == null) {
                return;
            }
            BuildersKt__Builders_commonKt.launch$default(viewModelScope2, null, null, new C01141(z, wallpaperTypeInfo, z2, null), 3, null);
            return;
        }
        if (wallpaperTypeInfo.getMattingMode() != 1 || (!wallpaperTypeInfo.getSupportSubject() && !Intrinsics.areEqual(wallpaperTypeInfo.getType(), "gallery"))) {
            CommonConfig templateConfig = getTemplateConfig();
            if (templateConfig != null && (lockscreenInfo = templateConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                wallpaperInfo.setSupportSubject(false);
            }
            EditFragmentViewModel viewModel2 = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
            if (viewModel2 != null) {
                EditFragmentViewModel.updateHierarchyStatus$default(viewModel2, 0, !getInitFinish(), false, false, 12, null);
            }
            onHierarchyDisable();
            return;
        }
        EditorActivityViewModel.Companion companion2 = EditorActivityViewModel.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        EditorActivityViewModel viewModel3 = companion2.getViewModel(context2);
        if (viewModel3 == null) {
            DataUtilKt.throwIfDebug("context error " + getContext());
        }
        if (viewModel3 == null || (viewModelScope = ViewModelKt.getViewModelScope(viewModel3)) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(viewModelScope, null, null, new AnonymousClass2(z, wallpaperTypeInfo, z2, null), 3, null);
    }

    public final void refreshGlassFilterEnable(@Nullable String str) {
        if (isWallpaperSupportGlassFilter(str)) {
            enableGlassFilter();
        } else {
            disableGlassFilter();
        }
    }

    public final Map screenShotHierarchyAvoidRectMap() {
        Rect rectSmaleScreenWithNotificationAvoidRect;
        String str;
        String str2;
        String str3;
        if (!DeviceUtil.INSTANCE.isFold()) {
            return MapsKt.emptyMap();
        }
        Context context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.utils.DeviceScreenshotHelper.ScreenshotContext");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (((DeviceScreenshotHelper.ScreenshotContext) context).isLargeScreen()) {
            boolean z = getResources().getConfiguration().orientation == 1;
            if (z) {
                str = "hierarchy_check_large_screen_port";
                str2 = "hierarchy_check_large_screen_port_dual_clock";
            } else {
                str = "hierarchy_check_large_screen_land";
                str2 = "hierarchy_check_large_screen_land_dual_clock";
            }
            str3 = z ? "hierarchy_check_large_screen_port_with_notification" : "hierarchy_check_large_screen_land_with_notification";
            rectSmaleScreenWithNotificationAvoidRect = largeScreenWithNotificationAvoidRect(z);
        } else {
            rectSmaleScreenWithNotificationAvoidRect = smaleScreenWithNotificationAvoidRect();
            str = "hierarchy_check_small_screen";
            str2 = "hierarchy_check_small_screen_dual_clock";
            str3 = "hierarchy_check_small_screen_with_notification";
        }
        Rect rectHierarchyAvoidRect = hierarchyAvoidRect();
        if (rectHierarchyAvoidRect.width() > 0 && rectHierarchyAvoidRect.height() > 0) {
            linkedHashMap.put(str, rectHierarchyAvoidRect);
        }
        Rect rectDualClockAvoidRect = dualClockAvoidRect();
        if (rectDualClockAvoidRect.width() > 0 && rectDualClockAvoidRect.height() > 0) {
            linkedHashMap.put(str2, rectDualClockAvoidRect);
        }
        if (rectSmaleScreenWithNotificationAvoidRect.width() > 0 && rectSmaleScreenWithNotificationAvoidRect.height() > 0) {
            linkedHashMap.put(str3, rectSmaleScreenWithNotificationAvoidRect);
        }
        return linkedHashMap;
    }

    public final void setBackgroundBeforeVideoPlay(Integer num, WallpaperTypeInfo wallpaperTypeInfo) {
        HierarchyImageView hierarchyImageView;
        if (Intrinsics.areEqual(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null, "video")) {
            if (getWallpaperLayer() instanceof CombinedWallpaperView) {
                IWallpaperLayer wallpaperLayer = getWallpaperLayer();
                Intrinsics.checkNotNull(wallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView");
                CombinedWallpaperView combinedWallpaperView = (CombinedWallpaperView) wallpaperLayer;
                Bitmap bitmap = wallpaperTypeInfo.getBitmap();
                if (bitmap != null) {
                    combinedWallpaperView.setBackgroundDrawable(new CenterCropDrawable(bitmap));
                }
            }
            if (m733getDepthVideoForegroundLayer() instanceof View) {
                Object objM733getDepthVideoForegroundLayer = m733getDepthVideoForegroundLayer();
                Intrinsics.checkNotNull(objM733getDepthVideoForegroundLayer, "null cannot be cast to non-null type android.view.View");
                ((View) objM733getDepthVideoForegroundLayer).setVisibility(8);
            }
            if (!MagicType.INSTANCE.isDepth(num) || (hierarchyImageView = (HierarchyImageView) getHierarchyLayer()) == null) {
                return;
            }
            hierarchyImageView.setVisibility(8);
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x003c  */
    public final void setCurTemplateOtherShouldCheckRect() {
        boolean z;
        String str;
        String str2;
        Rect rectLargeScreenWithNotificationAvoidRect;
        HierarchyImageAvoidController avoidController;
        HierarchyImageAvoidController avoidController2;
        if (isCreateForScreenShot()) {
            return;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsDualClockEnable = DualClockManager.isDualClockEnable(context);
        if (deviceUtil.isPhone()) {
            z = true;
        } else {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (deviceUtil.isFoldSmallScreen(context2)) {
                z = true;
            } else {
                z = false;
            }
        }
        Rect rectTemplateHierarchyAvoidRect = zIsDualClockEnable ? templateHierarchyAvoidRect() : dualClockAvoidRect();
        if (z) {
            str = zIsDualClockEnable ? "hierarchy_check_small_screen" : "hierarchy_check_small_screen_dual_clock";
            rectLargeScreenWithNotificationAvoidRect = smaleScreenWithNotificationAvoidRect();
            str2 = "hierarchy_check_small_screen_with_notification";
        } else {
            boolean z2 = getResources().getConfiguration().orientation == 1;
            if (zIsDualClockEnable) {
                str = z2 ? "hierarchy_check_large_screen_port" : "hierarchy_check_large_screen_land";
            } else {
                str = z2 ? "hierarchy_check_large_screen_port_dual_clock" : "hierarchy_check_large_screen_land_dual_clock";
            }
            str2 = z2 ? "hierarchy_check_large_screen_port_with_notification" : "hierarchy_check_large_screen_land_with_notification";
            rectLargeScreenWithNotificationAvoidRect = largeScreenWithNotificationAvoidRect(z2);
        }
        if (rectTemplateHierarchyAvoidRect.width() > 0 && rectTemplateHierarchyAvoidRect.height() > 0) {
            linkedHashMap.put(str, rectTemplateHierarchyAvoidRect);
        }
        if (rectLargeScreenWithNotificationAvoidRect.width() > 0 && rectLargeScreenWithNotificationAvoidRect.height() > 0) {
            linkedHashMap.put(str2, rectLargeScreenWithNotificationAvoidRect);
        }
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        if (hierarchyImageView != null && (avoidController2 = hierarchyImageView.getAvoidController()) != null) {
            avoidController2.setUpAvoidRect(linkedHashMap, unSupportHierarchyTypes());
        }
        Log.d("Keyguard-Editor-EffectsTemplateView", "forceCalculateWorkers " + this + " " + hierarchyImageView + " ," + (hierarchyImageView != null ? hierarchyImageView.getAvoidController() : null) + " " + linkedHashMap);
        if (hierarchyImageView == null || (avoidController = hierarchyImageView.getAvoidController()) == null) {
            return;
        }
        avoidController.onImageViewLayout(hierarchyImageView.getLeft(), hierarchyImageView.getTop(), hierarchyImageView.getRight(), hierarchyImageView.getBottom(), true);
    }

    public final void setSegmentEditor(@Nullable ImageSegmentEditor imageSegmentEditor) {
        this.segmentEditor = imageSegmentEditor;
    }

    public final boolean shouldSegmentOnInit() {
        return getTemplateSource() != -2;
    }

    public final void showDisableAlert(Triple triple) {
        if (isStopped()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (triple != null && ((Boolean) triple.getThird()).booleanValue()) {
            builder.setIcon(R.drawable.kg_alert_ai_generate_fail_icon);
        }
        builder.setTitle(triple != null ? (String) triple.getFirst() : null);
        builder.setMessage(triple != null ? (String) triple.getSecond() : null);
        builder.setHapticFeedbackEnabled(true);
        builder.setNegativeButton(getContext().getString(R.string.kg_effects_tip_button), (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        alertDialogCreate.show();
    }

    public final void showEffectsTipDialog(final Function0 function0) {
        if (this.shouldShowEffectsTip == null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            this.shouldShowEffectsTip = Boolean.valueOf(SPUtilKt.getBoolean(context, "has_show_effects_tip", true));
        }
        Boolean bool = this.shouldShowEffectsTip;
        Intrinsics.checkNotNull(bool);
        if (!bool.booleanValue()) {
            function0.invoke();
            return;
        }
        this.shouldShowEffectsTip = Boolean.FALSE;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        SPUtilKt.putBoolean(context2, "has_show_effects_tip", false);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        new AlertDialogBuilder(context3, 0, 2, null).setCancelable(false).setTitle(R.string.kg_effects_tip_title).setMessage(R.string.kg_effects_tip_desc).setPositiveButton(R.string.kg_effects_tip_button, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                function0.invoke();
            }
        }).create().show();
    }

    public final void showHierarchyLoading(boolean z) {
        if (editable() && shouldEnableHierarchy()) {
            if (this.hierarchyLoadingView == null) {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                EditorLoadingView editorLoadingView = new EditorLoadingView(context);
                ((ProgressBar) editorLoadingView.findViewById(R.id.kg_hierarchy_loading)).setIndeterminateTintList(ColorStateList.valueOf(-1));
                this.hierarchyLoadingView = editorLoadingView;
            }
            View view = this.hierarchyLoadingView;
            if (view != null) {
                if (view.getParent() == null) {
                    ViewParent parent = getParent();
                    Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
                    ((ViewGroup) parent).addView(view, new FrameLayout.LayoutParams(-1, -1));
                }
                view.setAlpha(z ? 0.0f : 1.0f);
                view.setVisibility(0);
                view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.EffectsTemplateView$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        EffectsTemplateView.showHierarchyLoading$lambda$49$lambda$48(view2);
                    }
                });
                TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
                if (primaryButtonBar != null) {
                    primaryButtonBar.disableAllExceptHierarchy();
                }
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void startCheckDepthVideoAvoid() {
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2 = getWallpaperInfo();
        String resourceType = wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null;
        WallpaperInfo wallpaperInfo3 = getWallpaperInfo();
        Boolean boolValueOf = wallpaperInfo3 != null ? Boolean.valueOf(wallpaperInfo3.isDepthVideo()) : null;
        WallpaperInfo wallpaperInfo4 = getWallpaperInfo();
        Log.d("Keyguard-Editor-EffectsTemplateView", "startCheckDepthVideoAvoid: " + resourceType + " " + boolValueOf + " " + (wallpaperInfo4 != null ? Integer.valueOf(wallpaperInfo4.getMagicType()) : null));
        WallpaperInfo wallpaperInfo5 = getWallpaperInfo();
        if (Intrinsics.areEqual(wallpaperInfo5 != null ? wallpaperInfo5.getResourceType() : null, "video") && (wallpaperInfo = getWallpaperInfo()) != null && wallpaperInfo.isDepthVideo()) {
            if (this.fastPlayer == null) {
                this.fastPlayer = new FastPlayer(getContext());
            }
            CoroutineScope coroutineScope = EditFragmentViewModel.Companion.getCoroutineScope(getEditFragment());
            if (coroutineScope != null) {
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getIO(), null, new C01151(null), 2, null);
            }
        }
    }

    public final boolean supportFilter() {
        return !Intrinsics.areEqual("doodle", getTemplateId());
    }

    public final void updateAiGenerateBtnStatus(boolean z) {
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new C01161(z, null), 3, null);
    }

    public final void updateGenerateView() {
        KgVisualCheckBox aiGenerateButton;
        Resources resources;
        Resources resources2;
        AIWallPaperManager aIWallPaperManager = this.aiWallPaperManager;
        Log.d("AIWallPaperManager", "updateGenerateView isGenerateRunning - " + (aIWallPaperManager != null ? Boolean.valueOf(aIWallPaperManager.isGenerateRunning()) : null));
        TemplateButtonBar primaryButtonBar = getPrimaryButtonBar();
        if (primaryButtonBar == null || (aiGenerateButton = primaryButtonBar.getAiGenerateButton()) == null) {
            return;
        }
        AIWallPaperManager aIWallPaperManager2 = this.aiWallPaperManager;
        int dimensionPixelSize = 0;
        if (aIWallPaperManager2 == null || !aIWallPaperManager2.isGenerateRunning()) {
            TextView label = aiGenerateButton.getLabel();
            if (label != null) {
                label.setText(getContext().getString(R.string.kg_title_ai_generate));
            }
            Context context = getContext();
            if (context != null && (resources = context.getResources()) != null) {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_function_ai_generate_button_height);
            }
            aiGenerateButtonIconSize(dimensionPixelSize);
            ImageView icon = aiGenerateButton.getIcon();
            if (icon != null) {
                icon.setImageResource(R.drawable.kg_function_button_icon_generate);
            }
            ImageView maskView = aiGenerateButton.getMaskView();
            if (maskView != null) {
                maskView.setVisibility(8);
                return;
            }
            return;
        }
        TextView label2 = aiGenerateButton.getLabel();
        if (label2 != null) {
            label2.setText(getContext().getString(R.string.kg_title_ai_generating));
        }
        Context context2 = getContext();
        aiGenerateButtonIconSize((context2 == null || (resources2 = context2.getResources()) == null) ? 0 : resources2.getDimensionPixelSize(R.dimen.kg_function_button_size));
        ImageView icon2 = aiGenerateButton.getIcon();
        if (icon2 != null) {
            icon2.setImageResource(R.drawable.kg_function_button_icon_generating);
        }
        ImageView maskView2 = aiGenerateButton.getMaskView();
        if (maskView2 != null) {
            maskView2.setImageResource(R.drawable.kg_function_button_bg_generate);
        }
        ImageView maskView3 = aiGenerateButton.getMaskView();
        if (maskView3 != null) {
            maskView3.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        ImageView maskView4 = aiGenerateButton.getMaskView();
        if (maskView4 != null) {
            maskView4.setVisibility(0);
        }
        ImageView maskView5 = aiGenerateButton.getMaskView();
        if (maskView5 != null) {
            disableClippingForParents(maskView5);
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0007. Please report as an issue. */
    @Override // com.miui.keyguard.editor.edit.base.BaseTemplateView
    public void updateHierarchyButton(int i) {
        boolean z;
        switch (i) {
            case 0:
                Log.d("Keyguard-Editor-EffectsTemplateView", "updateHierarchyButton: DISABLE");
                KgVisualCheckBox hierarchyBtn = getHierarchyBtn();
                if (hierarchyBtn != null) {
                    hierarchyBtn.disable();
                }
                z = false;
                break;
            case 1:
                Log.d("Keyguard-Editor-EffectsTemplateView", "updateHierarchyButton: USER OPEN");
                KgVisualCheckBox hierarchyBtn2 = getHierarchyBtn();
                if (hierarchyBtn2 != null) {
                    hierarchyBtn2.enable();
                }
                KgVisualCheckBox hierarchyBtn3 = getHierarchyBtn();
                if (hierarchyBtn3 != null) {
                    hierarchyBtn3.setCheckedOnly(true);
                }
                z = true;
                break;
            case 2:
                Log.d("Keyguard-Editor-EffectsTemplateView", "updateHierarchyButton: USER CLOSE");
                KgVisualCheckBox hierarchyBtn4 = getHierarchyBtn();
                if (hierarchyBtn4 != null) {
                    hierarchyBtn4.enable();
                }
                KgVisualCheckBox hierarchyBtn5 = getHierarchyBtn();
                if (hierarchyBtn5 != null) {
                    hierarchyBtn5.setCheckedOnly(false);
                }
                z = false;
                break;
            case 3:
                Log.d("Keyguard-Editor-EffectsTemplateView", "updateHierarchyButton: AVOID OPEN");
                KgVisualCheckBox hierarchyBtn6 = getHierarchyBtn();
                if (hierarchyBtn6 != null) {
                    hierarchyBtn6.enable();
                }
                KgVisualCheckBox hierarchyBtn7 = getHierarchyBtn();
                if (hierarchyBtn7 != null) {
                    hierarchyBtn7.setCheckedOnly(true);
                }
                z = true;
                break;
            case 4:
                Log.d("Keyguard-Editor-EffectsTemplateView", "updateHierarchyButton: AVOID OPEN");
                KgVisualCheckBox hierarchyBtn8 = getHierarchyBtn();
                if (hierarchyBtn8 != null) {
                    hierarchyBtn8.disable();
                }
                KgVisualCheckBox hierarchyBtn9 = getHierarchyBtn();
                if (hierarchyBtn9 != null) {
                    hierarchyBtn9.setCheckedOnly(false);
                }
                z = false;
                break;
            case 5:
                KgVisualCheckBox hierarchyBtn10 = getHierarchyBtn();
                if (hierarchyBtn10 != null) {
                    hierarchyBtn10.enable();
                }
                KgVisualCheckBox hierarchyBtn11 = getHierarchyBtn();
                if (hierarchyBtn11 != null) {
                    hierarchyBtn11.setCheckedOnly(true);
                }
                z = true;
                break;
            case 6:
                KgVisualCheckBox hierarchyBtn12 = getHierarchyBtn();
                if (hierarchyBtn12 != null) {
                    hierarchyBtn12.enable();
                }
                KgVisualCheckBox hierarchyBtn13 = getHierarchyBtn();
                if (hierarchyBtn13 != null) {
                    hierarchyBtn13.setCheckedOnly(false);
                }
                z = false;
                break;
            default:
                Log.d("Keyguard-Editor-EffectsTemplateView", "error hierarchy status");
                z = false;
                break;
        }
        BaseTemplateView.updateVisualCheckBoxChecked$default(this, getHierarchyBtn(), z, false, 4, null);
    }
}

package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.utils.TemplateSource;
import com.miui.keyguard.editor.utils.segment.ImageSegmentCache;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: WallpaperChangedCallBack.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperChangedCallBack {
    public static final Companion Companion = new Companion(null);
    public static volatile ThreadPoolExecutor sSerialExecutor;
    public boolean doGlass;
    public boolean fromInit;
    public Pair pair;
    public WallpaperTypeInfo wallpaperTypeInfo;
    public final WeakReference weakContext;

    /* JADX INFO: compiled from: WallpaperChangedCallBack.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public WallpaperChangedCallBack(EffectsTemplateView effectsTemplateView, boolean z, WallpaperTypeInfo wallpaperTypeInfo, boolean z2, Pair pair) {
        Intrinsics.checkNotNullParameter(effectsTemplateView, "effectsTemplateView");
        Intrinsics.checkNotNullParameter(wallpaperTypeInfo, "wallpaperTypeInfo");
        this.fromInit = z;
        this.wallpaperTypeInfo = wallpaperTypeInfo;
        this.doGlass = z2;
        this.pair = pair;
        this.weakContext = new WeakReference(effectsTemplateView);
    }

    public /* synthetic */ WallpaperChangedCallBack(EffectsTemplateView effectsTemplateView, boolean z, WallpaperTypeInfo wallpaperTypeInfo, boolean z2, Pair pair, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(effectsTemplateView, z, wallpaperTypeInfo, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? null : pair);
    }

    public static final Thread getSerialTaskExecutor$lambda$1$lambda$0(Runnable runnable) {
        return new Thread(runnable, "SerialTask");
    }

    public static final void store$lambda$2(WallpaperChangedCallBack wallpaperChangedCallBack, SegmentInfo segmentInfo) {
        ImageSegmentCache segmentCache;
        EffectsTemplateView effectsTemplateView = (EffectsTemplateView) wallpaperChangedCallBack.weakContext.get();
        if (effectsTemplateView == null) {
            return;
        }
        Pair pair = wallpaperChangedCallBack.pair;
        Intrinsics.checkNotNull(pair);
        String str = (String) pair.getFirst();
        if (str == null || (segmentCache = effectsTemplateView.getSegmentCache()) == null) {
            return;
        }
        segmentCache.store(segmentInfo.getBitmap(), str);
    }

    public final void doCallBack(SegmentInfo segmentInfo) {
        Intrinsics.checkNotNullParameter(segmentInfo, "segmentInfo");
        EffectsTemplateView effectsTemplateView = (EffectsTemplateView) this.weakContext.get();
        Log.i("Keyguard-Editor-WallpaperChangedCallBack", "doCallBack effectsTemplateView=" + effectsTemplateView + " ,pair=" + this.pair + ", doGlass=" + this.doGlass + ", fromInit=" + this.fromInit);
        if (effectsTemplateView == null) {
            return;
        }
        store(segmentInfo);
        hierarchy(segmentInfo);
        glass(segmentInfo);
    }

    public final ThreadPoolExecutor getSerialTaskExecutor() {
        if (sSerialExecutor == null) {
            synchronized (Companion.getClass()) {
                try {
                    if (sSerialExecutor == null) {
                        sSerialExecutor = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.miui.keyguard.editor.edit.base.WallpaperChangedCallBack$$ExternalSyntheticLambda1
                            @Override // java.util.concurrent.ThreadFactory
                            public final Thread newThread(Runnable runnable) {
                                return WallpaperChangedCallBack.getSerialTaskExecutor$lambda$1$lambda$0(runnable);
                            }
                        });
                        ThreadPoolExecutor threadPoolExecutor = sSerialExecutor;
                        Intrinsics.checkNotNull(threadPoolExecutor);
                        threadPoolExecutor.allowCoreThreadTimeOut(true);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        ThreadPoolExecutor threadPoolExecutor2 = sSerialExecutor;
        Intrinsics.checkNotNull(threadPoolExecutor2);
        return threadPoolExecutor2;
    }

    public final void glass(SegmentInfo segmentInfo) {
        EffectsTemplateView effectsTemplateView = (EffectsTemplateView) this.weakContext.get();
        if (effectsTemplateView == null || !this.doGlass) {
            return;
        }
        effectsTemplateView.refreshGlassFilterEnable(this.wallpaperTypeInfo.getType());
        if (effectsTemplateView.isWallpaperSupportGlassFilter(this.wallpaperTypeInfo.getType())) {
            if (segmentInfo.getBitmap() != null) {
                EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(effectsTemplateView.getEditFragment());
                if (viewModel != null) {
                    EditFragmentViewModel.reloadTemplateConfigForWallpaperFilter$default(viewModel, effectsTemplateView.getTemplateConfig(), false, 2, null);
                }
                if (effectsTemplateView.shouldEnableHierarchy()) {
                    if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 1, false, false, false, 14, null);
                    }
                } else if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, false, false, false, 14, null);
                }
            } else {
                Log.e("Keyguard-Editor-WallpaperChangedCallBack", "segment bitmap is null");
            }
        }
        EffectsTemplateView effectsTemplateView2 = (EffectsTemplateView) this.weakContext.get();
        if (effectsTemplateView2 != null) {
            effectsTemplateView2.setAllowToClickDynamicSwitchBtn(true);
        }
    }

    /* JADX WARN: Code duplicated, block: B:107:0x01bb  */
    /* JADX WARN: Code duplicated, block: B:109:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:65:0x0100  */
    public final void hierarchy(SegmentInfo segmentInfo) {
        CommonConfig templateConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        EffectsTemplateView effectsTemplateView = (EffectsTemplateView) this.weakContext.get();
        if (effectsTemplateView == null) {
            return;
        }
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(effectsTemplateView.getEditFragment());
        CommonConfig templateConfig2 = effectsTemplateView.getTemplateConfig();
        if (templateConfig2 != null) {
            WallpaperInfo wallpaperInfo4 = templateConfig2.getLockscreenInfo().getWallpaperInfo();
            boolean zAreEqual = Intrinsics.areEqual(wallpaperInfo4 != null ? wallpaperInfo4.getResourceType() : null, "video");
            if (zAreEqual) {
                WallpaperInfo wallpaperInfoForVideo = templateConfig2.getLockscreenInfo().getWallpaperInfoForVideo();
                if (wallpaperInfoForVideo != null) {
                    wallpaperInfoForVideo.setSupportSubject((segmentInfo.getBitmap() == null || segmentInfo.getBitmap().isRecycled()) ? false : true);
                }
            } else {
                WallpaperInfo wallpaperInfo5 = templateConfig2.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo5 != null) {
                    wallpaperInfo5.setSupportSubject((segmentInfo.getBitmap() == null || segmentInfo.getBitmap().isRecycled()) ? false : true);
                }
            }
            if (zAreEqual) {
                WallpaperInfo wallpaperInfoForVideo2 = templateConfig2.getLockscreenInfo().getWallpaperInfoForVideo();
                if (wallpaperInfoForVideo2 != null) {
                    wallpaperInfoForVideo2.setSubjectBitmap(segmentInfo.getBitmap());
                }
                ImageSegmentEditor segmentEditor = effectsTemplateView.getSegmentEditor();
                if (segmentEditor != null) {
                    segmentEditor.setSubjectInfo(segmentInfo);
                    return;
                }
                return;
            }
        }
        EditFragment editFragment = effectsTemplateView.getEditFragment();
        boolean z = editFragment != null && editFragment.useCurrentLockWallpaper();
        CommonConfig templateConfig3 = effectsTemplateView.getTemplateConfig();
        boolean zAreEqual2 = Intrinsics.areEqual("image", (templateConfig3 == null || (lockscreenInfo3 = templateConfig3.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType());
        boolean zIsFromCurrentTemplate = TemplateSource.INSTANCE.isFromCurrentTemplate(effectsTemplateView.getTemplateSource());
        if (z && zAreEqual2 && this.fromInit && zIsFromCurrentTemplate) {
            TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
            Context context = effectsTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (templateDataUtil.isSupportMatting(context)) {
                templateConfig = effectsTemplateView.getTemplateConfig();
                if (templateConfig != null) {
                    Log.d("Keyguard-Editor-WallpaperChangedCallBack", "hierarchy hierarchy false");
                    if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
                    }
                    effectsTemplateView.onHierarchyDisable();
                } else {
                    Log.d("Keyguard-Editor-WallpaperChangedCallBack", "hierarchy hierarchy false");
                    if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
                    }
                    effectsTemplateView.onHierarchyDisable();
                }
            } else {
                Log.d("Keyguard-Editor-WallpaperChangedCallBack", "hierarchy hierarchy false due to settings value");
                if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
                }
                effectsTemplateView.onHierarchyDisable();
            }
        } else {
            templateConfig = effectsTemplateView.getTemplateConfig();
            if (templateConfig != null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || !wallpaperInfo.getSupportSubject()) {
                Log.d("Keyguard-Editor-WallpaperChangedCallBack", "hierarchy hierarchy false");
                if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 0, false, false, false, 14, null);
                }
                effectsTemplateView.onHierarchyDisable();
            } else {
                Log.d("Keyguard-Editor-WallpaperChangedCallBack", "hierarchy hierarchy true");
                ImageSegmentEditor segmentEditor2 = effectsTemplateView.getSegmentEditor();
                if (segmentEditor2 != null) {
                    segmentEditor2.setSubjectInfo(segmentInfo);
                }
                CommonConfig templateConfig4 = effectsTemplateView.getTemplateConfig();
                int magicType = (templateConfig4 == null || (lockscreenInfo2 = templateConfig4.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? 0 : wallpaperInfo2.getMagicType();
                boolean z2 = this.fromInit && MagicType.INSTANCE.isShader(magicType);
                Log.d("Keyguard-Editor-WallpaperChangedCallBack", "hierarchy isInitWithShader=" + z2 + ", currentMagicType=" + magicType + ", fromInit=" + this.fromInit);
                if (!effectsTemplateView.shouldEnableHierarchy() || z2) {
                    if (viewModel != null) {
                        EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 2, z2, false, false, 12, null);
                    }
                } else if (viewModel != null) {
                    EditFragmentViewModel.updateHierarchyStatus$default(viewModel, 1, false, false, false, 14, null);
                }
                effectsTemplateView.onHierarchyEnable();
                TemplateDataUtil templateDataUtil2 = TemplateDataUtil.INSTANCE;
                Context context2 = effectsTemplateView.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                boolean zIsSupportShowDoodleText = templateDataUtil2.isSupportShowDoodleText(context2);
                if (!z || effectsTemplateView.isWallpaperChanged() || zIsSupportShowDoodleText) {
                    if (viewModel != null) {
                        EditFragmentViewModel.updateDoodleStatus$default(viewModel, 1, false, 2, null);
                    }
                } else if (viewModel != null) {
                    EditFragmentViewModel.updateDoodleStatus$default(viewModel, 2, false, 2, null);
                }
            }
        }
        effectsTemplateView.hideHierarchyLoading();
    }

    public final void store(final SegmentInfo segmentInfo) {
        if (this.pair == null) {
            return;
        }
        getSerialTaskExecutor().execute(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.WallpaperChangedCallBack$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WallpaperChangedCallBack.store$lambda$2(this.f$0, segmentInfo);
            }
        });
    }
}

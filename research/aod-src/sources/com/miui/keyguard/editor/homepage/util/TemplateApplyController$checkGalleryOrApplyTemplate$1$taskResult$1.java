package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.edit.base.DepthPresetDownloadManager;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1", f = "TemplateApplyController.kt", l = {457}, m = "invokeSuspend")
public final class TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super TaskResult>, Object> {
    public final /* synthetic */ boolean $isPreset;
    public final /* synthetic */ int $screenStatus;
    public final /* synthetic */ TemplateItemBean $templateBean;
    public int I$0;
    public int I$1;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public Object L$3;
    public Object L$4;
    public Object L$5;
    public Object L$6;
    public Object L$7;
    public Object L$8;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1(TemplateApplyController templateApplyController, boolean z, TemplateItemBean templateItemBean, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = templateApplyController;
        this.$isPreset = z;
        this.$templateBean = templateItemBean;
        this.$screenStatus = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1(this.this$0, this.$isPreset, this.$templateBean, this.$screenStatus, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:103:0x028c  */
    /* JADX WARN: Code duplicated, block: B:105:0x0290  */
    /* JADX WARN: Code duplicated, block: B:106:0x0297  */
    /* JADX WARN: Code duplicated, block: B:110:0x02ab  */
    /* JADX WARN: Code duplicated, block: B:112:0x02b7  */
    /* JADX WARN: Code duplicated, block: B:113:0x02be  */
    /* JADX WARN: Code duplicated, block: B:116:0x02d2  */
    /* JADX WARN: Code duplicated, block: B:118:0x02dc  */
    /* JADX WARN: Code duplicated, block: B:119:0x02e3  */
    /* JADX WARN: Code duplicated, block: B:96:0x0269  */
    /* JADX WARN: Code duplicated, block: B:98:0x026d  */
    /* JADX WARN: Code duplicated, block: B:99:0x0274  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String lockWallpaperType;
        Ref.BooleanRef booleanRef;
        boolean zAreEqual;
        TemplateHistoryConfig history;
        int galleryMagazineStatus;
        TemplateHistoryConfig history2;
        Ref.ObjectRef objectRef;
        CommonConfig applyTemplateConfig;
        Ref.ObjectRef objectRef2;
        TemplateApplyController templateApplyController;
        int i;
        Ref.ObjectRef objectRef3;
        Ref.ObjectRef objectRef4;
        WallpaperController companion;
        String str;
        int i2;
        int i3;
        Ref.ObjectRef objectRef5;
        TemplateApplyController templateApplyController2;
        CommonConfig commonConfig;
        Ref.ObjectRef objectRef6;
        Ref.ObjectRef objectRef7;
        CommonConfig applyTemplateConfig2;
        TemplateItemConfig bindItemConfig;
        String wallpaperPath;
        TemplateHistoryConfig history3;
        Bitmap bitmapFromFile$default;
        Bitmap bitmapFromFile$default2;
        BitmapUtil bitmapUtil;
        WallpaperInfo wallpaperInfo;
        String source;
        Bitmap bitmapFromFile$default3;
        WallpaperInfo wallpaperInfo2;
        String source2;
        String currentWallpaperScreenshotUri;
        String currentScreenshotUri;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i4 = this.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            WallpaperController.Companion companion2 = WallpaperController.Companion;
            Context context = this.this$0.applicationContext;
            Intrinsics.checkNotNullExpressionValue(context, "access$getApplicationContext$p(...)");
            lockWallpaperType = companion2.getInstance(context).getLockWallpaperType();
            String str2 = this.this$0.TAG;
            boolean z = this.$isPreset;
            TemplateItemBean templateItemBean = this.$templateBean;
            Log.i(str2, "checkGalleryOrApplyTemplate -> isPreset = " + z + ", currentWallpaperType = " + lockWallpaperType + ", historyWallpaperType = " + ((templateItemBean == null || (history3 = templateItemBean.getHistory()) == null) ? null : history3.getResourceType()));
            booleanRef = new Ref.BooleanRef();
            if (this.$isPreset) {
                zAreEqual = false;
            } else {
                TemplateItemBean templateItemBean2 = this.$templateBean;
                zAreEqual = Intrinsics.areEqual((templateItemBean2 == null || (history = templateItemBean2.getHistory()) == null) ? null : history.getResourceType(), "video");
            }
            booleanRef.element = zAreEqual;
            FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
            Context context2 = this.this$0.applicationContext;
            Intrinsics.checkNotNullExpressionValue(context2, "access$getApplicationContext$p(...)");
            galleryMagazineStatus = fashionGalleryHelper.getGalleryMagazineStatus(context2);
            TemplateItemBean templateItemBean3 = this.$templateBean;
            history2 = templateItemBean3 != null ? templateItemBean3.getHistory() : null;
            TemplateItemBean templateItemBean4 = this.$templateBean;
            PresetTemplateConfig preset = templateItemBean4 != null ? templateItemBean4.getPreset() : null;
            objectRef = new Ref.ObjectRef();
            Ref.ObjectRef objectRef8 = new Ref.ObjectRef();
            Ref.ObjectRef objectRef9 = new Ref.ObjectRef();
            Ref.ObjectRef objectRef10 = new Ref.ObjectRef();
            EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(this.this$0.activity);
            if (!this.$isPreset) {
                Log.d(this.this$0.TAG, "checkGalleryOrApplyTemplate: wallpaperScreenshot=" + (history2 != null ? history2.getCurrentWallpaperScreenshotUri(this.$screenStatus) : null));
                if (viewModel != null && (applyTemplateConfig = viewModel.getApplyTemplateConfig()) != null) {
                    objectRef2 = objectRef10;
                    templateApplyController = this.this$0;
                    i = this.$screenStatus;
                    WallpaperInfo wallpaperInfo3 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                    int magicType = wallpaperInfo3 != null ? wallpaperInfo3.getMagicType() : 0;
                    WallpaperController companion3 = companion2.getInstance(templateApplyController.activity);
                    DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                    if (deviceUtil.isInternationalBuild() && DeviceUtil.isHomeNeedUseOriginalEffect$default(deviceUtil, companion3, null, magicType, 2, null)) {
                        BitmapUtil bitmapUtil2 = BitmapUtil.INSTANCE;
                        WallpaperInfo wallpaperInfo4 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                        objectRef9.element = BitmapUtil.fromFile$default(bitmapUtil2, wallpaperInfo4 != null ? wallpaperInfo4.getSource() : null, 0, 0, null, 14, null);
                        if ((galleryMagazineStatus == 3 || galleryMagazineStatus == 2) && (companion = companion2.getInstance()) != null && companion.isWallpaperSupportWaitImage()) {
                            WallpaperInfo wallpaperInfo5 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                            if (wallpaperInfo5 != null) {
                                wallpaperInfo5.setCurrentWallpaper((Bitmap) objectRef9.element);
                            }
                            WallpaperInfo wallpaperInfo6 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                            if (wallpaperInfo6 != null) {
                                wallpaperInfo6.resetMagicType("apply history when gallery open");
                            }
                            MainCoroutineDispatcher main = Dispatchers.getMain();
                            TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1 templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1 = new TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1(templateApplyController, applyTemplateConfig, objectRef, objectRef8, null);
                            this.L$0 = lockWallpaperType;
                            this.L$1 = booleanRef;
                            this.L$2 = history2;
                            this.L$3 = objectRef;
                            this.L$4 = objectRef8;
                            this.L$5 = objectRef9;
                            this.L$6 = objectRef2;
                            this.L$7 = templateApplyController;
                            this.L$8 = applyTemplateConfig;
                            this.I$0 = galleryMagazineStatus;
                            this.I$1 = i;
                            this.label = 1;
                            if (BuildersKt.withContext(main, templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            str = lockWallpaperType;
                            i2 = galleryMagazineStatus;
                            i3 = i;
                            objectRef5 = objectRef9;
                            objectRef4 = objectRef8;
                            templateApplyController2 = templateApplyController;
                            commonConfig = applyTemplateConfig;
                            objectRef6 = objectRef;
                            objectRef7 = objectRef2;
                        }
                        bitmapFromFile$default = (Bitmap) objectRef.element;
                        if (bitmapFromFile$default == null) {
                            BitmapUtil bitmapUtil3 = BitmapUtil.INSTANCE;
                            if (history2 != null) {
                                currentScreenshotUri = history2.getCurrentScreenshotUri(i);
                            } else {
                                currentScreenshotUri = null;
                            }
                            bitmapFromFile$default = BitmapUtil.fromFile$default(bitmapUtil3, currentScreenshotUri, 0, 0, null, 14, null);
                        }
                        objectRef.element = bitmapFromFile$default;
                        bitmapFromFile$default2 = (Bitmap) objectRef4.element;
                        if (bitmapFromFile$default2 == null) {
                            BitmapUtil bitmapUtil4 = BitmapUtil.INSTANCE;
                            if (history2 != null) {
                                currentWallpaperScreenshotUri = history2.getCurrentWallpaperScreenshotUri(i);
                            } else {
                                currentWallpaperScreenshotUri = null;
                            }
                            bitmapFromFile$default2 = BitmapUtil.fromFile$default(bitmapUtil4, currentWallpaperScreenshotUri, 0, 0, null, 14, null);
                        }
                        objectRef4.element = bitmapFromFile$default2;
                        if (bitmapFromFile$default2 == null) {
                            bitmapUtil = BitmapUtil.INSTANCE;
                            wallpaperInfo = applyTemplateConfig.getHomeInfo().getWallpaperInfo();
                            if (wallpaperInfo != null) {
                                source = wallpaperInfo.getSource();
                            } else {
                                source = null;
                            }
                            bitmapFromFile$default3 = BitmapUtil.fromFile$default(bitmapUtil, source, 0, 0, null, 14, null);
                            objectRef4.element = bitmapFromFile$default3;
                            if (bitmapFromFile$default3 == null) {
                                wallpaperInfo2 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                                if (wallpaperInfo2 != null) {
                                    source2 = wallpaperInfo2.getSource();
                                } else {
                                    source2 = null;
                                }
                                objectRef4.element = BitmapUtil.fromFile$default(bitmapUtil, source2, 0, 0, null, 14, null);
                                Log.e(templateApplyController.TAG, "checkGalleryOrApplyTemplate: load historyWallpaperScreenshot from originResourcePath");
                            }
                        }
                    }
                    objectRef3 = objectRef9;
                    objectRef4 = objectRef8;
                    bitmapFromFile$default = (Bitmap) objectRef.element;
                    if (bitmapFromFile$default == null) {
                        BitmapUtil bitmapUtil5 = BitmapUtil.INSTANCE;
                        if (history2 != null) {
                            currentScreenshotUri = history2.getCurrentScreenshotUri(i);
                        } else {
                            currentScreenshotUri = null;
                        }
                        bitmapFromFile$default = BitmapUtil.fromFile$default(bitmapUtil5, currentScreenshotUri, 0, 0, null, 14, null);
                    }
                    objectRef.element = bitmapFromFile$default;
                    bitmapFromFile$default2 = (Bitmap) objectRef4.element;
                    if (bitmapFromFile$default2 == null) {
                        BitmapUtil bitmapUtil6 = BitmapUtil.INSTANCE;
                        if (history2 != null) {
                            currentWallpaperScreenshotUri = history2.getCurrentWallpaperScreenshotUri(i);
                        } else {
                            currentWallpaperScreenshotUri = null;
                        }
                        bitmapFromFile$default2 = BitmapUtil.fromFile$default(bitmapUtil6, currentWallpaperScreenshotUri, 0, 0, null, 14, null);
                    }
                    objectRef4.element = bitmapFromFile$default2;
                    if (bitmapFromFile$default2 == null) {
                        bitmapUtil = BitmapUtil.INSTANCE;
                        wallpaperInfo = applyTemplateConfig.getHomeInfo().getWallpaperInfo();
                        if (wallpaperInfo != null) {
                            source = wallpaperInfo.getSource();
                        } else {
                            source = null;
                        }
                        bitmapFromFile$default3 = BitmapUtil.fromFile$default(bitmapUtil, source, 0, 0, null, 14, null);
                        objectRef4.element = bitmapFromFile$default3;
                        if (bitmapFromFile$default3 == null) {
                            wallpaperInfo2 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                            if (wallpaperInfo2 != null) {
                                source2 = wallpaperInfo2.getSource();
                            } else {
                                source2 = null;
                            }
                            objectRef4.element = BitmapUtil.fromFile$default(bitmapUtil, source2, 0, 0, null, 14, null);
                            Log.e(templateApplyController.TAG, "checkGalleryOrApplyTemplate: load historyWallpaperScreenshot from originResourcePath");
                        }
                    }
                }
                return new TaskResult(lockWallpaperType, booleanRef.element, galleryMagazineStatus, (Bitmap) objectRef.element, (Bitmap) objectRef4.element, (Bitmap) objectRef2.element, (Bitmap) objectRef3.element);
            }
            if (preset != null && (bindItemConfig = preset.getBindItemConfig()) != null && (wallpaperPath = bindItemConfig.getWallpaperPath()) != null) {
                objectRef10.element = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(wallpaperPath), 0, 0, null, 14, null);
            }
            if (viewModel != null && (applyTemplateConfig2 = viewModel.getApplyTemplateConfig()) != null) {
                boolean zIsPresetDepthVideo = DepthPresetDownloadManager.Companion.isPresetDepthVideo(applyTemplateConfig2.getLockscreenInfo().getWallpaperInfo(), true);
                booleanRef.element = zIsPresetDepthVideo;
                if (zIsPresetDepthVideo) {
                    WallpaperInfo wallpaperInfo7 = applyTemplateConfig2.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo7 != null) {
                        wallpaperInfo7.setResourceType("video");
                    }
                } else {
                    WallpaperInfo wallpaperInfo8 = applyTemplateConfig2.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo8 != null) {
                        wallpaperInfo8.setResourceType("image");
                    }
                }
            }
            objectRef2 = objectRef10;
            objectRef3 = objectRef9;
            objectRef4 = objectRef8;
            return new TaskResult(lockWallpaperType, booleanRef.element, galleryMagazineStatus, (Bitmap) objectRef.element, (Bitmap) objectRef4.element, (Bitmap) objectRef2.element, (Bitmap) objectRef3.element);
        }
        if (i4 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        i3 = this.I$1;
        i2 = this.I$0;
        commonConfig = (CommonConfig) this.L$8;
        templateApplyController2 = (TemplateApplyController) this.L$7;
        objectRef7 = (Ref.ObjectRef) this.L$6;
        objectRef5 = (Ref.ObjectRef) this.L$5;
        objectRef4 = (Ref.ObjectRef) this.L$4;
        objectRef6 = (Ref.ObjectRef) this.L$3;
        history2 = (TemplateHistoryConfig) this.L$2;
        booleanRef = (Ref.BooleanRef) this.L$1;
        str = (String) this.L$0;
        ResultKt.throwOnFailure(obj);
        applyTemplateConfig = commonConfig;
        templateApplyController = templateApplyController2;
        objectRef2 = objectRef7;
        objectRef3 = objectRef5;
        objectRef = objectRef6;
        lockWallpaperType = str;
        i = i3;
        galleryMagazineStatus = i2;
        bitmapFromFile$default = (Bitmap) objectRef.element;
        if (bitmapFromFile$default == null) {
            BitmapUtil bitmapUtil7 = BitmapUtil.INSTANCE;
            if (history2 != null) {
                currentScreenshotUri = history2.getCurrentScreenshotUri(i);
            } else {
                currentScreenshotUri = null;
            }
            bitmapFromFile$default = BitmapUtil.fromFile$default(bitmapUtil7, currentScreenshotUri, 0, 0, null, 14, null);
        }
        objectRef.element = bitmapFromFile$default;
        bitmapFromFile$default2 = (Bitmap) objectRef4.element;
        if (bitmapFromFile$default2 == null) {
            BitmapUtil bitmapUtil8 = BitmapUtil.INSTANCE;
            if (history2 != null) {
                currentWallpaperScreenshotUri = history2.getCurrentWallpaperScreenshotUri(i);
            } else {
                currentWallpaperScreenshotUri = null;
            }
            bitmapFromFile$default2 = BitmapUtil.fromFile$default(bitmapUtil8, currentWallpaperScreenshotUri, 0, 0, null, 14, null);
        }
        objectRef4.element = bitmapFromFile$default2;
        if (bitmapFromFile$default2 == null) {
            bitmapUtil = BitmapUtil.INSTANCE;
            wallpaperInfo = applyTemplateConfig.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo != null) {
                source = wallpaperInfo.getSource();
            } else {
                source = null;
            }
            bitmapFromFile$default3 = BitmapUtil.fromFile$default(bitmapUtil, source, 0, 0, null, 14, null);
            objectRef4.element = bitmapFromFile$default3;
            if (bitmapFromFile$default3 == null) {
                wallpaperInfo2 = applyTemplateConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo2 != null) {
                    source2 = wallpaperInfo2.getSource();
                } else {
                    source2 = null;
                }
                objectRef4.element = BitmapUtil.fromFile$default(bitmapUtil, source2, 0, 0, null, 14, null);
                Log.e(templateApplyController.TAG, "checkGalleryOrApplyTemplate: load historyWallpaperScreenshot from originResourcePath");
            }
        }
        return new TaskResult(lockWallpaperType, booleanRef.element, galleryMagazineStatus, (Bitmap) objectRef.element, (Bitmap) objectRef4.element, (Bitmap) objectRef2.element, (Bitmap) objectRef3.element);
    }
}

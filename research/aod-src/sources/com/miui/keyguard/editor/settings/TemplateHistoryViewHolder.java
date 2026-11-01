package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.history.common.ViewExtKt;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.settings.data.HomeColumnDataSource;
import com.miui.keyguard.editor.settings.util.ViewUtilKt;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

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
/* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateHistoryViewHolder extends BaseSettingsTemplateViewHolder {
    public static final Companion Companion = new Companion(null);
    public final ImageView deleteTemplateButton;
    public ImageView desktopIconPreview;
    public ImageView desktopPreviewView;
    public ImageView keyguardPreviewView;
    public int previewHeight;
    public int previewWidth;
    public final int version;

    /* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.TemplateHistoryViewHolder$loadLockAndHomeBitmap$1, reason: invalid class name */
    /* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.TemplateHistoryViewHolder$loadLockAndHomeBitmap$1", f = "SettingsTemplateAdapterAndHoldImpl.kt", l = {489}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ TemplateItemBean $data;
        public final /* synthetic */ TemplateHistoryConfig $history;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(TemplateItemBean templateItemBean, TemplateHistoryConfig templateHistoryConfig, Continuation continuation) {
            super(2, continuation);
            this.$data = templateItemBean;
            this.$history = templateHistoryConfig;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TemplateHistoryViewHolder.this.new AnonymousClass1(this.$data, this.$history, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:17:0x0052  */
        /* JADX WARN: Code duplicated, block: B:18:0x0059  */
        /* JADX WARN: Code duplicated, block: B:21:0x0061  */
        /* JADX WARN: Code duplicated, block: B:22:0x0068  */
        /* JADX WARN: Code duplicated, block: B:25:0x0079  */
        /* JADX WARN: Code duplicated, block: B:27:0x0081  */
        /* JADX WARN: Code duplicated, block: B:28:0x0088  */
        /* JADX WARN: Code duplicated, block: B:31:0x0094  */
        /* JADX WARN: Code duplicated, block: B:37:0x00a7 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:38:0x00a9  */
        /* JADX WARN: Code duplicated, block: B:41:0x00bd  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            AnonymousClass1 anonymousClass1;
            Pair pair;
            Bitmap bitmap;
            Bitmap bitmap2;
            ImageView imageView;
            Bitmap bitmap3;
            ImageView imageView2;
            CrossViewPagerCallback crossViewPagerCallback;
            Bitmap bitmap4;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            Bitmap bitmap5 = null;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SettingsTemplateDataModelProvider dataModelProvider = TemplateHistoryViewHolder.this.getDataModelProvider();
                if (dataModelProvider != null) {
                    Context context = TemplateHistoryViewHolder.this.itemView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    TemplateItemBean templateItemBean = this.$data;
                    int i2 = TemplateHistoryViewHolder.this.previewWidth;
                    int i3 = TemplateHistoryViewHolder.this.previewHeight;
                    this.label = 1;
                    anonymousClass1 = this;
                    obj = dataModelProvider.getHistoryLockAndHomeBitmap(context, templateItemBean, i2, i3, anonymousClass1);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    anonymousClass1 = this;
                    pair = null;
                }
                TemplateHistoryConfig templateHistoryConfig = anonymousClass1.$history;
                if (pair != null) {
                    bitmap = (Bitmap) pair.getFirst();
                } else {
                    bitmap = null;
                }
                templateHistoryConfig.setScreenshotImage(bitmap);
                TemplateHistoryConfig templateHistoryConfig2 = anonymousClass1.$history;
                if (pair != null) {
                    bitmap2 = (Bitmap) pair.getSecond();
                } else {
                    bitmap2 = null;
                }
                templateHistoryConfig2.setHomeScreenshot(bitmap2);
                anonymousClass1.$history.setLoadCompleted(true);
                imageView = TemplateHistoryViewHolder.this.keyguardPreviewView;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("keyguardPreviewView");
                    imageView = null;
                }
                if (pair != null) {
                    bitmap3 = (Bitmap) pair.getFirst();
                } else {
                    bitmap3 = null;
                }
                imageView.setImageBitmap(bitmap3);
                imageView2 = TemplateHistoryViewHolder.this.desktopPreviewView;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("desktopPreviewView");
                    imageView2 = null;
                }
                if (pair == null && (bitmap4 = (Bitmap) pair.getSecond()) != null) {
                    bitmap5 = bitmap4;
                } else if (pair != null) {
                    bitmap5 = (Bitmap) pair.getFirst();
                }
                imageView2.setImageBitmap(bitmap5);
                crossViewPagerCallback = (CrossViewPagerCallback) TemplateHistoryViewHolder.this.getCallback();
                if (crossViewPagerCallback != null) {
                    crossViewPagerCallback.onHistoryTemplateReady();
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            anonymousClass1 = this;
            pair = (Pair) obj;
            TemplateHistoryConfig templateHistoryConfig3 = anonymousClass1.$history;
            if (pair != null) {
                bitmap = (Bitmap) pair.getFirst();
            } else {
                bitmap = null;
            }
            templateHistoryConfig3.setScreenshotImage(bitmap);
            TemplateHistoryConfig templateHistoryConfig4 = anonymousClass1.$history;
            if (pair != null) {
                bitmap2 = (Bitmap) pair.getSecond();
            } else {
                bitmap2 = null;
            }
            templateHistoryConfig4.setHomeScreenshot(bitmap2);
            anonymousClass1.$history.setLoadCompleted(true);
            imageView = TemplateHistoryViewHolder.this.keyguardPreviewView;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("keyguardPreviewView");
                imageView = null;
            }
            if (pair != null) {
                bitmap3 = (Bitmap) pair.getFirst();
            } else {
                bitmap3 = null;
            }
            imageView.setImageBitmap(bitmap3);
            imageView2 = TemplateHistoryViewHolder.this.desktopPreviewView;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("desktopPreviewView");
                imageView2 = null;
            }
            if (pair == null) {
                if (pair != null) {
                    bitmap5 = (Bitmap) pair.getFirst();
                }
            } else if (pair != null) {
                bitmap5 = (Bitmap) pair.getFirst();
            }
            imageView2.setImageBitmap(bitmap5);
            crossViewPagerCallback = (CrossViewPagerCallback) TemplateHistoryViewHolder.this.getCallback();
            if (crossViewPagerCallback != null) {
                crossViewPagerCallback.onHistoryTemplateReady();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateHistoryViewHolder(SettingsTemplateDataModelProvider settingsTemplateDataModelProvider, View itemView) {
        super(settingsTemplateDataModelProvider, itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        View viewFindViewById = itemView.findViewById(R.id.template_delete_button);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.deleteTemplateButton = (ImageView) viewFindViewById;
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "<get-context>(...)");
        this.previewWidth = (int) (((double) settingsResourceProvider.provideTemplateContainerWidth(context)) * 0.8d);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "<get-context>(...)");
        this.previewHeight = (int) (((double) settingsResourceProvider.provideTemplateContainerHeight(context2)) * 0.8d);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "<get-context>(...)");
        this.version = deviceUtil.getEditorVersion(context3);
    }

    private final void deletePage() {
        CrossViewPagerCallback crossViewPagerCallback;
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean == null || templateItemBean.getItemType() != 2 || (crossViewPagerCallback = (CrossViewPagerCallback) getCallback()) == null) {
            return;
        }
        crossViewPagerCallback.onPageDeleted(getX(), getY(), templateItemBean);
    }

    private final void onBindHistoryTemplate(TemplateItemBean templateItemBean, int i) {
        TemplateHistoryConfig history = templateItemBean.getHistory();
        if (history == null) {
            Log.w("TemplateHistoryViewHolder", "onViewHolderBound failed: history == null, position = " + i);
            return;
        }
        if (this.version >= 49) {
            this.deleteTemplateButton.setVisibility(0);
            this.deleteTemplateButton.setAlpha(0.0f);
        } else {
            this.deleteTemplateButton.setVisibility(8);
        }
        loadLockAndHomeBitmap(templateItemBean);
        setLockIsDarkColor(history.isDarkWallpaper() == 1);
        setHomeIsDarkColor(history.isDarkWallpaper() == 1);
        bindDesktopIconPreview(getHomeIsDarkColor());
        decorateCustomizeBackground(getCustomizeKeyguardBtn(), getLockIsDarkColor());
        decorateCustomizeBackground(getCustomizeDesktopBtn(), getHomeIsDarkColor());
    }

    public static final Unit onViewHolderCreated$lambda$3(TemplateHistoryViewHolder templateHistoryViewHolder, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (ViewUtilKt.isNoTransparent(templateHistoryViewHolder.deleteTemplateButton)) {
            templateHistoryViewHolder.deletePage();
        }
        return Unit.INSTANCE;
    }

    public final void bindDesktopIconPreview(boolean z) {
        ImageView imageView = null;
        if (!DeviceUtil.INSTANCE.isPhone()) {
            ImageView imageView2 = this.desktopIconPreview;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("desktopIconPreview");
            } else {
                imageView = imageView2;
            }
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            Context context = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            imageView.setImageResource(commonUtils.getDeskColorBlockForColumn(context, z));
            return;
        }
        HomeColumnDataSource homeColumnDataSource = HomeColumnDataSource.INSTANCE;
        Context context2 = this.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        if (homeColumnDataSource.getHomeColumns(context2) == 5) {
            ImageView imageView3 = this.desktopIconPreview;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("desktopIconPreview");
            } else {
                imageView = imageView3;
            }
            imageView.setImageResource(!z ? R.drawable.kg_miui_home_desktop_five_black : R.drawable.kg_miui_home_desktop_five_white);
            return;
        }
        ImageView imageView4 = this.desktopIconPreview;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("desktopIconPreview");
        } else {
            imageView = imageView4;
        }
        imageView.setImageResource(!z ? R.drawable.kg_miui_home_desktop_black : R.drawable.kg_miui_home_desktop_white);
    }

    public final ImageView createImageView() {
        ImageView imageView = new ImageView(this.itemView.getContext());
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return imageView;
    }

    public final ImageView getDeleteTemplateButton() {
        return this.deleteTemplateButton;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public View getHomeTemplateView() {
        ImageView imageView = this.desktopPreviewView;
        if (imageView != null) {
            return imageView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("desktopPreviewView");
        return null;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public View getLockTemplateView() {
        ImageView imageView = this.keyguardPreviewView;
        if (imageView != null) {
            return imageView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("keyguardPreviewView");
        return null;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public View getSelfItemView() {
        View itemView = this.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return itemView;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public View getTargetContentView() {
        View itemView = this.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return itemView;
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isHomeDarkColor() {
        return getHomeIsDarkColor();
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isLockDarkColor() {
        return getLockIsDarkColor();
    }

    public final void loadLockAndHomeBitmap(TemplateItemBean templateItemBean) {
        TemplateHistoryConfig history = templateItemBean.getHistory();
        if (history == null) {
            return;
        }
        ImageView imageView = null;
        if (!history.isLoadCompleted()) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "<get-context>(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(templateItemBean, history, null), 3, null);
            return;
        }
        Log.d("TemplateHistoryViewHolder", "has loaded,keyguardBitmap=" + history.getScreenshotImage() + ", homeBitmap=" + history.getHomeScreenshot());
        ImageView imageView2 = this.keyguardPreviewView;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keyguardPreviewView");
            imageView2 = null;
        }
        imageView2.setImageBitmap(history.getScreenshotImage());
        ImageView imageView3 = this.desktopPreviewView;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("desktopPreviewView");
        } else {
            imageView = imageView3;
        }
        Bitmap homeScreenshot = history.getHomeScreenshot();
        if (homeScreenshot == null) {
            homeScreenshot = history.getScreenshotImage();
        }
        imageView.setImageBitmap(homeScreenshot);
        CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) getCallback();
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onHistoryTemplateReady();
        }
    }

    public final void onBindThirdPartyTheme(TemplateItemBean templateItemBean) {
        this.deleteTemplateButton.setVisibility(8);
        Bitmap thirdPartyThemePreview = templateItemBean.getThirdPartyThemePreview();
        ImageView imageView = null;
        if (thirdPartyThemePreview != null) {
            ImageView imageView2 = this.keyguardPreviewView;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("keyguardPreviewView");
                imageView2 = null;
            }
            imageView2.setImageBitmap(thirdPartyThemePreview);
        }
        Bitmap thirdPartyThemeHomePreview = templateItemBean.getThirdPartyThemeHomePreview();
        if (thirdPartyThemeHomePreview != null) {
            ImageView imageView3 = this.desktopPreviewView;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("desktopPreviewView");
            } else {
                imageView = imageView3;
            }
            imageView.setImageBitmap(thirdPartyThemeHomePreview);
        }
        bindDesktopIconPreview(templateItemBean.isThirdPartyDarkMode());
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
        super.onViewHolderBound(templateItemBean, i);
        Log.d("TemplateHistoryViewHolder", "onViewHolderBound: data == " + templateItemBean + ", position = " + i);
        Integer numValueOf = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
        if (numValueOf != null && numValueOf.intValue() == 2) {
            onBindHistoryTemplate(templateItemBean, i);
        } else if (numValueOf != null && numValueOf.intValue() == 4) {
            onBindThirdPartyTheme(templateItemBean);
        } else {
            Log.w("TemplateHistoryViewHolder", "onViewHolderBound skipped: Unknown itemType: " + numValueOf);
        }
        setCustomButtonVisible(shouldShowCustomButton());
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        super.onViewHolderCreated(itemView);
        Log.d("TemplateHistoryViewHolder", "onViewHolderCreated");
        this.previewWidth = (int) (((double) getTemplateContainerWidth()) * 0.8d);
        this.previewHeight = (int) (((double) getTemplateContainerHeight()) * 0.8d);
        ImageView imageViewCreateImageView = createImageView();
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        imageViewCreateImageView.setScaleType(scaleType);
        this.keyguardPreviewView = imageViewCreateImageView;
        FrameLayout keyguardContainer = getKeyguardContainer();
        ImageView imageView = this.keyguardPreviewView;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keyguardPreviewView");
            imageView = null;
        }
        addTemplateView(keyguardContainer, imageView);
        ImageView imageViewCreateImageView2 = createImageView();
        imageViewCreateImageView2.setScaleType(scaleType);
        this.desktopPreviewView = imageViewCreateImageView2;
        FrameLayout desktopContainer = getDesktopContainer();
        ImageView imageView3 = this.desktopPreviewView;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("desktopPreviewView");
            imageView3 = null;
        }
        addTemplateView(desktopContainer, imageView3);
        ImageView imageViewCreateImageView3 = createImageView();
        imageViewCreateImageView3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.desktopIconPreview = imageViewCreateImageView3;
        FrameLayout desktopContainer2 = getDesktopContainer();
        ImageView imageView4 = this.desktopIconPreview;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("desktopIconPreview");
        } else {
            imageView2 = imageView4;
        }
        addTemplateView(desktopContainer2, imageView2);
        ViewExtKt.clickNoRepeat$default(this.deleteTemplateButton, 0L, new Function1() { // from class: com.miui.keyguard.editor.settings.TemplateHistoryViewHolder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TemplateHistoryViewHolder.onViewHolderCreated$lambda$3(this.f$0, (View) obj);
            }
        }, 1, null);
    }

    public boolean shouldShowCustomButton() {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        boolean z = false;
        if (templateItemBean != null && templateItemBean.getItemType() == 4) {
            z = true;
        }
        return !z;
    }
}

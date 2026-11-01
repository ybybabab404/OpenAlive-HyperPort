package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.TemplatePreviewView;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.task.Task;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplatePreviewViewHolder extends BaseItemViewHolder {
    public final float PREVIEW_SCALE_RATE;
    public final String TAG;
    public final boolean isSupportDownload;
    public final ViewGroup.LayoutParams layoutParamsForAdd;
    public final Lazy previewHeight$delegate;
    public final Lazy previewWidth$delegate;
    public TemplatePreviewView templatePreviewView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplatePreviewViewHolder(LayoutInflater inflater, final ViewGroup parent, BaseItemViewFactoryImpl itemViewFactoryImpl, FactoryParams params, boolean z) {
        super(inflater, parent, itemViewFactoryImpl, params);
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(itemViewFactoryImpl, "itemViewFactoryImpl");
        Intrinsics.checkNotNullParameter(params, "params");
        this.isSupportDownload = z;
        this.TAG = "Keyguard-Editor-TemplatePreviewViewHolder";
        this.PREVIEW_SCALE_RATE = 0.8f;
        this.layoutParamsForAdd = new ViewGroup.LayoutParams(-1, -1);
        this.previewWidth$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(TemplatePreviewViewHolder.previewWidth_delegate$lambda$0(parent, this));
            }
        });
        this.previewHeight$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(TemplatePreviewViewHolder.previewHeight_delegate$lambda$1(parent, this));
            }
        });
    }

    public static final Triple onBindHistoryTemplate$lambda$4(TemplatePreviewViewHolder templatePreviewViewHolder, TemplateHistoryConfig templateHistoryConfig) {
        DataUtil dataUtil = DataUtil.INSTANCE;
        TemplatePreviewView templatePreviewView = templatePreviewViewHolder.templatePreviewView;
        TemplatePreviewView templatePreviewView2 = null;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        Context applicationContext = templatePreviewView.getContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        Bitmap historyScreenshotImage = dataUtil.getHistoryScreenshotImage(applicationContext, templateHistoryConfig, templatePreviewViewHolder.getPreviewWidth(), templatePreviewViewHolder.getPreviewHeight());
        TemplatePreviewView templatePreviewView3 = templatePreviewViewHolder.templatePreviewView;
        if (templatePreviewView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView3 = null;
        }
        Context applicationContext2 = templatePreviewView3.getContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
        Bitmap historyHomeScreenshotImage = dataUtil.getHistoryHomeScreenshotImage(applicationContext2, templateHistoryConfig, templatePreviewViewHolder.getPreviewWidth(), templatePreviewViewHolder.getPreviewHeight());
        TemplatePreviewView templatePreviewView4 = templatePreviewViewHolder.templatePreviewView;
        if (templatePreviewView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
        } else {
            templatePreviewView2 = templatePreviewView4;
        }
        Context applicationContext3 = templatePreviewView2.getContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext3, "getApplicationContext(...)");
        return new Triple(historyScreenshotImage, historyHomeScreenshotImage, dataUtil.getHistoryCommonConfig(applicationContext3, templateHistoryConfig));
    }

    public static final void onBindHistoryTemplate$lambda$5(TemplatePreviewViewHolder templatePreviewViewHolder, TemplateHistoryConfig templateHistoryConfig, Triple triple) {
        Log.i(templatePreviewViewHolder.TAG, "onBindHistoryTemplate: screenshot=" + triple);
        templateHistoryConfig.setScreenshotImage((Bitmap) triple.getFirst());
        templateHistoryConfig.setHomeScreenshot((Bitmap) triple.getSecond());
        templateHistoryConfig.setLoadCompleted(true);
        TemplatePreviewView templatePreviewView = templatePreviewViewHolder.templatePreviewView;
        TemplatePreviewView templatePreviewView2 = null;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        templatePreviewView.setPreviewImage((Bitmap) triple.getFirst(), null, true);
        TemplatePreviewView templatePreviewView3 = templatePreviewViewHolder.templatePreviewView;
        if (templatePreviewView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
        } else {
            templatePreviewView2 = templatePreviewView3;
        }
        templatePreviewView2.setHomePreviewImage((Bitmap) triple.getSecond());
        templateHistoryConfig.setAccessConfig((CommonConfig) triple.getThird());
    }

    public static final Pair onBindPresetTemplate$lambda$9$lambda$6(TemplatePreviewViewHolder templatePreviewViewHolder, TemplateItemConfig templateItemConfig, PresetTemplateConfig presetTemplateConfig) {
        TemplatePreviewView templatePreviewView = templatePreviewViewHolder.templatePreviewView;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        Context applicationContext = templatePreviewView.getContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        return templatePreviewViewHolder.getPresetPair(applicationContext, templateItemConfig, presetTemplateConfig.getConfigUri(), presetTemplateConfig.getHideFlag());
    }

    public static final void onBindPresetTemplate$lambda$9$lambda$8(PresetTemplateConfig presetTemplateConfig, TemplatePreviewViewHolder templatePreviewViewHolder, Pair pair) {
        TemplateItemConfig bindItemConfig;
        TemplateItemConfig bindItemConfig2;
        PresetTemplateConfig presetTemplateConfig2 = pair != null ? (PresetTemplateConfig) pair.getFirst() : null;
        if (presetTemplateConfig2 == null) {
            Log.w(templatePreviewViewHolder.TAG, "onBindPresetTemplate: presetTemplate is null");
            return;
        }
        presetTemplateConfig.setPreviewImage(presetTemplateConfig2.getPreviewImage());
        presetTemplateConfig.setForeground(presetTemplateConfig2.getForeground());
        presetTemplateConfig.setWallpaper(presetTemplateConfig2.getWallpaper());
        presetTemplateConfig.setLoadCompleted(true);
        Bitmap previewImage = presetTemplateConfig.getPreviewImage();
        if (previewImage != null && ((bindItemConfig = presetTemplateConfig.getBindItemConfig()) == null || previewImage.getWidth() != bindItemConfig.getReqWidth() || (bindItemConfig2 = presetTemplateConfig.getBindItemConfig()) == null || previewImage.getHeight() != bindItemConfig2.getReqHeight())) {
            presetTemplateConfig.setLoadCompleted(false);
        }
        presetTemplateConfig.setAccessConfig(pair != null ? (CommonConfig) pair.getSecond() : null);
        templatePreviewViewHolder.setPresetTemplateImage(presetTemplateConfig, true);
    }

    public static final int previewHeight_delegate$lambda$1(ViewGroup viewGroup, TemplatePreviewViewHolder templatePreviewViewHolder) {
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = viewGroup.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return (int) (resourcesProvider.provideTemplatePreviewHeight(context) * templatePreviewViewHolder.PREVIEW_SCALE_RATE);
    }

    public static final int previewWidth_delegate$lambda$0(ViewGroup viewGroup, TemplatePreviewViewHolder templatePreviewViewHolder) {
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = viewGroup.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return (int) (resourcesProvider.provideTemplatePreviewWidth(context) * templatePreviewViewHolder.PREVIEW_SCALE_RATE);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public View getHomePreview() {
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        return templatePreviewView.getHomeLayer();
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public View getLockPreview() {
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        return templatePreviewView.getLockLayer();
    }

    public final Pair getPresetPair(Context context, TemplateItemConfig templateItemConfig, String str, int i) {
        TemplateApi.Companion companion = TemplateApi.Companion;
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        TemplatePreviewView templatePreviewView2 = null;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        Context applicationContext = templatePreviewView.getContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        TemplateApi companion2 = companion.getInstance(applicationContext);
        TemplatePreviewView templatePreviewView3 = this.templatePreviewView;
        if (templatePreviewView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
        } else {
            templatePreviewView2 = templatePreviewView3;
        }
        Context context2 = templatePreviewView2.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        return new Pair(companion2.getPresetTemplate(context2, templateItemConfig, true, getPreviewWidth(), getPreviewHeight(), i), TemplateDataUtil.getTemplateConfig(context, str, true, i));
    }

    public final int getPreviewHeight() {
        return ((Number) this.previewHeight$delegate.getValue()).intValue();
    }

    public final int getPreviewWidth() {
        return ((Number) this.previewWidth$delegate.getValue()).intValue();
    }

    public final void onBindHistoryTemplate(TemplateItemBean templateItemBean, int i) {
        final TemplateHistoryConfig history = templateItemBean.getHistory();
        if (history == null) {
            Log.w(this.TAG, "onViewHolderBound failed: history == null, position = " + i);
            return;
        }
        TemplatePreviewView templatePreviewView = null;
        if (!history.isLoadCompleted()) {
            TemplatePreviewView templatePreviewView2 = this.templatePreviewView;
            if (templatePreviewView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            } else {
                templatePreviewView = templatePreviewView2;
            }
            templatePreviewView.setLoadingDrawable(history.getLoadingDrawable());
            Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return TemplatePreviewViewHolder.onBindHistoryTemplate$lambda$4(this.f$0, history);
                }
            }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TemplatePreviewViewHolder.onBindHistoryTemplate$lambda$5(this.f$0, history, (Triple) obj);
                }
            });
            return;
        }
        TemplatePreviewView templatePreviewView3 = this.templatePreviewView;
        if (templatePreviewView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView3 = null;
        }
        templatePreviewView3.setPreviewImage(history.getScreenshotImage(), null, false);
        TemplatePreviewView templatePreviewView4 = this.templatePreviewView;
        if (templatePreviewView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
        } else {
            templatePreviewView = templatePreviewView4;
        }
        templatePreviewView.setHomePreviewImage(history.getHomeScreenshot());
    }

    public final void onBindNegativeCardTemplate(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.kg_negative_card);
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        View itemView = this.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        ColorDrawable colorDrawable = new ColorDrawable(viewUtil.getColor(itemView, R.color.kg_negative_card_background_color));
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        TemplatePreviewView templatePreviewView2 = null;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        templatePreviewView.setNavigateImage(drawable, colorDrawable, true);
        TemplatePreviewView templatePreviewView3 = this.templatePreviewView;
        if (templatePreviewView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView3 = null;
        }
        templatePreviewView3.addPreviewBackground();
        TemplatePreviewView templatePreviewView4 = this.templatePreviewView;
        if (templatePreviewView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
        } else {
            templatePreviewView2 = templatePreviewView4;
        }
        templatePreviewView2.switchPreview(false, false);
    }

    public final void onBindPresetTemplate(TemplateItemBean templateItemBean, int i) {
        final PresetTemplateConfig preset = templateItemBean.getPreset();
        if (preset == null) {
            Log.w(this.TAG, "onViewHolderBound failed: template == null, position = " + i);
            return;
        }
        if (preset.isLoadCompleted()) {
            setPresetTemplateImage(preset, false);
            return;
        }
        setPresetTemplateLoadingDrawable(preset);
        final TemplateItemConfig bindItemConfig = preset.getBindItemConfig();
        if (bindItemConfig != null) {
            Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return TemplatePreviewViewHolder.onBindPresetTemplate$lambda$9$lambda$6(this.f$0, bindItemConfig, preset);
                }
            }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TemplatePreviewViewHolder.onBindPresetTemplate$lambda$9$lambda$8(preset, this, (Pair) obj);
                }
            });
        }
    }

    public final void onBindThirdPartyTemplate(TemplateItemBean templateItemBean) {
        Bitmap thirdPartyThemePreview = templateItemBean.getThirdPartyThemePreview();
        TemplatePreviewView templatePreviewView = null;
        if (thirdPartyThemePreview != null) {
            TemplatePreviewView templatePreviewView2 = this.templatePreviewView;
            if (templatePreviewView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
                templatePreviewView2 = null;
            }
            templatePreviewView2.setPreviewImage(thirdPartyThemePreview, null, false);
        }
        Bitmap thirdPartyThemeHomePreview = templateItemBean.getThirdPartyThemeHomePreview();
        if (thirdPartyThemeHomePreview != null) {
            TemplatePreviewView templatePreviewView3 = this.templatePreviewView;
            if (templatePreviewView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            } else {
                templatePreviewView = templatePreviewView3;
            }
            templatePreviewView.setHomePreviewImage(thirdPartyThemeHomePreview);
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        Boolean boolValueOf = templateItemBean != null ? Boolean.valueOf(templateItemBean.isSelected()) : null;
        Log.i(this.TAG, "onLongClick isOutsideTouchEvent:" + isOutsideTouchEvent() + " selected:" + boolValueOf + " canLongClick:" + getCanLongClick());
        if (getCanLongClick() && !isOutsideTouchEvent() && !Intrinsics.areEqual(boolValueOf, Boolean.FALSE)) {
            TemplateItemBean templateItemBean2 = (TemplateItemBean) getItemData();
            Integer numValueOf = templateItemBean2 != null ? Integer.valueOf(templateItemBean2.getItemType()) : null;
            if (numValueOf != null && numValueOf.intValue() == 2) {
                showDeleteLayer(true);
                return true;
            }
            if (numValueOf != null && numValueOf.intValue() == 4) {
                if (view != null) {
                    ToastUtil toastUtil = ToastUtil.INSTANCE;
                    Context context = view.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    toastUtil.showText(context, R.string.kg_main_toast_current_template_delete);
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
        super.onViewHolderBound(templateItemBean, i);
        showDeleteLayer(false);
        Integer numValueOf = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
        if (numValueOf != null && numValueOf.intValue() == -1) {
            Context context = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            onBindNegativeCardTemplate(context);
        } else if (numValueOf != null && numValueOf.intValue() == 2) {
            onBindHistoryTemplate(templateItemBean, i);
        } else if (numValueOf != null && numValueOf.intValue() == 3) {
            onBindPresetTemplate(templateItemBean, i);
        } else if (numValueOf != null && numValueOf.intValue() == 4) {
            onBindThirdPartyTemplate(templateItemBean);
        } else {
            Log.w(this.TAG, "onViewHolderBound skipped: Unknown itemType: " + numValueOf);
        }
        setCustomButtonVisible(shouldShowCustomButton());
        setDepthVideoIcon(shouldShowDepthVideoIcon());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        super.onViewHolderCreated(itemView);
        Context context = itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        TemplatePreviewView templatePreviewView = new TemplatePreviewView(context, null, 2, 0 == true ? 1 : 0);
        templatePreviewView.init(getParams());
        templatePreviewView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.templatePreviewView = templatePreviewView;
        addTemplateView(templatePreviewView, this.layoutParamsForAdd);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder
    public void onViewHolderSelectChanged(boolean z) {
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        TemplatePreviewView.switchHomeLayer$default(templatePreviewView, z, false, 2, null);
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean != null && templateItemBean.getItemType() == -1) {
            TemplatePreviewView templatePreviewView2 = this.templatePreviewView;
            if (templatePreviewView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
                templatePreviewView2 = null;
            }
            TemplatePreviewView.switchPreview$default(templatePreviewView2, z, false, 2, null);
        }
        super.onViewHolderSelectChanged(z);
    }

    public final void setPresetTemplateImage(PresetTemplateConfig presetTemplateConfig, boolean z) {
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        templatePreviewView.setPreviewImage(presetTemplateConfig.getPreviewImage(), presetTemplateConfig.getForeground(), z);
    }

    public final void setPresetTemplateLoadingDrawable(PresetTemplateConfig presetTemplateConfig) {
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        templatePreviewView.setLoadingDrawable(presetTemplateConfig.getLoadingDrawable());
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder
    public boolean shouldShowCustomButton() {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean == null) {
            return true;
        }
        int itemType = templateItemBean.getItemType();
        return (itemType == 4 || itemType == -1) ? false : true;
    }

    public final boolean shouldShowDepthVideoIcon() {
        PresetTemplateConfig preset;
        TemplateItemConfig bindItemConfig;
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        return templateItemBean != null && (preset = templateItemBean.getPreset()) != null && (bindItemConfig = preset.getBindItemConfig()) != null && bindItemConfig.getDepthVideo() && FileUtil.isSupportVideoDepth() && this.isSupportDownload;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder, com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public void switchHome(boolean z, boolean z2) {
        super.switchHome(z, z2);
        TemplatePreviewView templatePreviewView = this.templatePreviewView;
        if (templatePreviewView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView = null;
        }
        templatePreviewView.switchHomeLayer(z, z2);
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean == null || templateItemBean.getItemType() != -1) {
            return;
        }
        TemplatePreviewView templatePreviewView2 = this.templatePreviewView;
        if (templatePreviewView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templatePreviewView");
            templatePreviewView2 = null;
        }
        TemplatePreviewView.switchPreview$default(templatePreviewView2, z, false, 2, null);
    }
}

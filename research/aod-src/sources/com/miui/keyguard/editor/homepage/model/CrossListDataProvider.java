package com.miui.keyguard.editor.homepage.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Trace;
import androidx.core.util.Supplier;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateGroupConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.template.CurrentTemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: CrossListDataProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class CrossListDataProvider {
    public final Lazy allHistoryTemplatesSupplier$delegate;
    public final Lazy allPresetTemplateSupplier$delegate;
    public final Context context;
    public CurrentTemplateApi currentTemplateApi;
    public final Lazy currentTemplateSupplier$delegate;
    public final TemplateApi dataApi;
    public int previewHeight;
    public int previewWidth;
    public String screenshotWallpaperCacheTarget;
    public final Lazy thirdPartyThemeSupplier$delegate;

    public CrossListDataProvider(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.context = applicationContext;
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        this.previewWidth = (int) resourcesProvider.provideTemplatePreviewWidth(context);
        this.previewHeight = (int) resourcesProvider.provideTemplatePreviewHeight(context);
        this.dataApi = TemplateApi.Companion.getInstance(context);
        this.thirdPartyThemeSupplier$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataProvider.thirdPartyThemeSupplier_delegate$lambda$1(this.f$0);
            }
        });
        this.currentTemplateSupplier$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataProvider.currentTemplateSupplier_delegate$lambda$4(this.f$0, context);
            }
        });
        this.allHistoryTemplatesSupplier$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataProvider.allHistoryTemplatesSupplier_delegate$lambda$7(this.f$0, context);
            }
        });
        this.allPresetTemplateSupplier$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataProvider.allPresetTemplateSupplier_delegate$lambda$11(this.f$0, context);
            }
        });
    }

    public static final Supplier allHistoryTemplatesSupplier_delegate$lambda$7(final CrossListDataProvider crossListDataProvider, final Context context) {
        return new Supplier() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda4
            @Override // androidx.core.util.Supplier
            public final Object get() {
                return CrossListDataProvider.allHistoryTemplatesSupplier_delegate$lambda$7$lambda$6(this.f$0, context);
            }
        };
    }

    public static final List allHistoryTemplatesSupplier_delegate$lambda$7$lambda$6(CrossListDataProvider crossListDataProvider, Context context) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : TemplateApi.DefaultImpls.getHistoryTemplates$default(crossListDataProvider.dataApi, false, 1, null)) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TemplateHistoryConfig templateHistoryConfig = (TemplateHistoryConfig) obj;
            boolean z = templateHistoryConfig.getMagicType() != 100000 || FileUtil.isSupportVideoDepth();
            boolean z2 = Intrinsics.areEqual(templateHistoryConfig.getResourceType(), "sensor") && !CommonUtils.INSTANCE.isFlipOrFoldDevice();
            if (z && !z2) {
                if (i < 1) {
                    DataUtil dataUtil = DataUtil.INSTANCE;
                    templateHistoryConfig.setScreenshotImage(dataUtil.getHistoryScreenshotImage(context, templateHistoryConfig, crossListDataProvider.previewWidth, crossListDataProvider.previewHeight));
                    templateHistoryConfig.setHomeScreenshot(dataUtil.getHistoryHomeScreenshotImage(context, templateHistoryConfig, crossListDataProvider.previewWidth, crossListDataProvider.previewHeight));
                    templateHistoryConfig.setLoadCompleted(true);
                    templateHistoryConfig.setAccessConfig(TemplateDataUtil.getTemplateConfig(context, templateHistoryConfig.getConfigUri(), false, templateHistoryConfig.getHideFlag()));
                }
                templateHistoryConfig.setLoadingDrawable(crossListDataProvider.dataApi.getTemplateLoadingDrawable(templateHistoryConfig.getLoadingColor()));
                arrayList.add(new TemplateItemBean(2, 0, Integer.valueOf(R.string.kg_template_title_mine_collocation), null, templateHistoryConfig, null, null, null, null, false, false, false, false, 8160, null));
            }
            i = i2;
        }
        return arrayList;
    }

    public static final Supplier allPresetTemplateSupplier_delegate$lambda$11(final CrossListDataProvider crossListDataProvider, final Context context) {
        return new Supplier() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda5
            @Override // androidx.core.util.Supplier
            public final Object get() {
                return CrossListDataProvider.allPresetTemplateSupplier_delegate$lambda$11$lambda$10(this.f$0, context);
            }
        };
    }

    public static final List allPresetTemplateSupplier_delegate$lambda$11$lambda$10(CrossListDataProvider crossListDataProvider, Context context) {
        ArrayList arrayList = new ArrayList();
        List presetTemplateGroup = crossListDataProvider.dataApi.getPresetTemplateGroup();
        CrossListDataModel.Companion.updatePresetDatabaseAsync(context, presetTemplateGroup);
        Context contextCreateConfigurationContext = context.createConfigurationContext(DeviceUtil.INSTANCE.initialConfiguration(context));
        int i = 0;
        for (Object obj : presetTemplateGroup) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TemplateGroupConfig templateGroupConfig = (TemplateGroupConfig) obj;
            ArrayList arrayList2 = new ArrayList();
            int i3 = 0;
            for (Object obj2 : templateGroupConfig.getTemplates()) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                TemplateItemConfig templateItemConfig = (TemplateItemConfig) obj2;
                boolean preload = templateItemConfig.getPreload();
                TemplateApi templateApi = crossListDataProvider.dataApi;
                Intrinsics.checkNotNull(contextCreateConfigurationContext);
                PresetTemplateConfig presetTemplate = templateApi.getPresetTemplate(contextCreateConfigurationContext, templateItemConfig, preload, crossListDataProvider.previewWidth, crossListDataProvider.previewHeight, templateItemConfig.getHideFlag());
                if (presetTemplate != null) {
                    String configUri = presetTemplate.getConfigUri();
                    Intrinsics.checkNotNull(configUri);
                    presetTemplate.setAccessConfig(TemplateDataUtil.getTemplateConfig(context, configUri, true, presetTemplate.getHideFlag()));
                }
                ArrayList arrayList3 = arrayList2;
                int i5 = i2;
                arrayList3.add(new TemplateItemBean(3, i5, Integer.valueOf(templateGroupConfig.getTitle()), Integer.valueOf(templateGroupConfig.getDesc()), null, presetTemplate, null, null, null, false, false, false, false, 8144, null));
                arrayList2 = arrayList3;
                i2 = i5;
                i3 = i4;
            }
            int i6 = i2;
            arrayList.add(new FloorItemBean(i6, Integer.valueOf(templateGroupConfig.getTitle()), Integer.valueOf(templateGroupConfig.getDesc()), arrayList2));
            i = i6;
        }
        return arrayList;
    }

    public static final Supplier currentTemplateSupplier_delegate$lambda$4(final CrossListDataProvider crossListDataProvider, final Context context) {
        return new Supplier() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda6
            @Override // androidx.core.util.Supplier
            public final Object get() {
                return CrossListDataProvider.currentTemplateSupplier_delegate$lambda$4$lambda$3(this.f$0, context);
            }
        };
    }

    public static final TemplateItemBean currentTemplateSupplier_delegate$lambda$4$lambda$3(CrossListDataProvider crossListDataProvider, Context context) throws InterruptedException {
        if (crossListDataProvider.currentTemplateApi == null) {
            crossListDataProvider.currentTemplateApi = new CurrentTemplateApi(context);
        }
        String str = crossListDataProvider.screenshotWallpaperCacheTarget;
        if (str == null) {
            str = "wallpaper_for_screenshot";
        }
        CurrentTemplateApi currentTemplateApi = crossListDataProvider.currentTemplateApi;
        Intrinsics.checkNotNull(currentTemplateApi);
        CommonConfig commonConfigRequireCurrentTemplate = currentTemplateApi.requireCurrentTemplate(str);
        if (commonConfigRequireCurrentTemplate != null) {
            return new TemplateItemBean(1, 0, Integer.valueOf(R.string.kg_template_title_mine_collocation), null, null, null, commonConfigRequireCurrentTemplate, null, null, false, false, false, false, 8112, null);
        }
        return null;
    }

    public static final Supplier thirdPartyThemeSupplier_delegate$lambda$1(final CrossListDataProvider crossListDataProvider) {
        return new Supplier() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataProvider$$ExternalSyntheticLambda7
            @Override // androidx.core.util.Supplier
            public final Object get() {
                return CrossListDataProvider.thirdPartyThemeSupplier_delegate$lambda$1$lambda$0(this.f$0);
            }
        };
    }

    public static final TemplateItemBean thirdPartyThemeSupplier_delegate$lambda$1$lambda$0(CrossListDataProvider crossListDataProvider) {
        if (crossListDataProvider.dataApi.isThirdPartyTheme()) {
            Trace.endSection();
            Bitmap bitmapRequireThirdPartyThemePreviewImage = crossListDataProvider.dataApi.requireThirdPartyThemePreviewImage(crossListDataProvider.previewWidth, crossListDataProvider.previewHeight);
            if (bitmapRequireThirdPartyThemePreviewImage != null) {
                return new TemplateItemBean(4, 0, Integer.valueOf(R.string.kg_template_title_mine_collocation), null, null, null, null, bitmapRequireThirdPartyThemePreviewImage, null, false, false, false, false, 8048, null);
            }
        }
        return null;
    }

    public Supplier getAllHistoryTemplatesSupplier() {
        return (Supplier) this.allHistoryTemplatesSupplier$delegate.getValue();
    }

    public final Supplier getAllPresetTemplateSupplier() {
        return (Supplier) this.allPresetTemplateSupplier$delegate.getValue();
    }

    public final Context getContext() {
        return this.context;
    }

    public final Supplier getCurrentTemplateSupplier() {
        return (Supplier) this.currentTemplateSupplier$delegate.getValue();
    }

    public final TemplateApi getDataApi() {
        return this.dataApi;
    }

    public final int getPreviewHeight() {
        return this.previewHeight;
    }

    public final int getPreviewWidth() {
        return this.previewWidth;
    }

    public final String getScreenshotWallpaperCacheTarget() {
        return this.screenshotWallpaperCacheTarget;
    }

    public Supplier getThirdPartyThemeSupplier() {
        return (Supplier) this.thirdPartyThemeSupplier$delegate.getValue();
    }

    public final void setCurrentTemplateApi(CurrentTemplateApi currentTemplateApi) {
        this.currentTemplateApi = currentTemplateApi;
    }

    public final void setPreviewHeight(int i) {
        this.previewHeight = i;
    }

    public final void setPreviewWidth(int i) {
        this.previewWidth = i;
    }

    public final void setScreenshotWallpaperCacheTarget(String str) {
        this.screenshotWallpaperCacheTarget = str;
    }
}

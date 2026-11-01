package com.miui.keyguard.editor.homepage.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.DatabasePresetTemplate;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateGroupConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.db.EditorDatabase;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.data.preset.PresetColorInfo;
import com.miui.keyguard.editor.data.template.BitmapTempStore;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.SystemEditor;
import com.miui.keyguard.editor.utils.task.Task;
import com.xiaomi.onetrack.api.bf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
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
/* JADX INFO: compiled from: CrossListDataModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class CrossListDataModel extends CrossListDataProvider {
    public static final List alwaysSupportTemplate;
    public final String TAG$1;
    public final int _maxTemplateCount;
    public CountDownLatch allCountdownLatch;
    public final Lazy allFloorItemBeans$delegate;
    public final Lazy allHistoryTemplates$delegate;
    public final Lazy allHistoryTemplatesTask$delegate;
    public final Lazy allPresetTemplateTask$delegate;
    public final Lazy allPresetTemplates$delegate;
    public Bundle clockExtraForCurrentTemplate;
    public TemplateItemBean currentTemplate;
    public volatile CommonConfig currentTemplateConfig;
    public final Lazy currentTemplateTask$delegate;
    public List historyTemplateGroup;
    public final CrossListPreloader preloader;
    public final Lazy thirdPartyThemeTask$delegate;
    public TemplateItemBean thirdPartyThemeTemplate;
    public static final Companion Companion = new Companion(null);
    public static final Lazy sTemplatePattern$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda7
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return CrossListDataModel.sTemplatePattern_delegate$lambda$46();
        }
    });

    /* JADX INFO: compiled from: CrossListDataModel.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Pattern getSTemplatePattern() {
            Object value = CrossListDataModel.sTemplatePattern$delegate.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Pattern) value;
        }

        public final String getTemplateId(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            Matcher matcher = getSTemplatePattern().matcher(name);
            if (matcher.find()) {
                return matcher.group(bf.a);
            }
            Log.i("PresetDB", "getTemplateName: " + name);
            return name;
        }

        public final boolean querySupport(Context context, String str, String str2) {
            Intrinsics.checkNotNullParameter(context, "context");
            EditorDatabase.Companion companion = EditorDatabase.Companion;
            if (companion.getInstance(context).getPresetTemplateDao().needUpdate(SystemEditor.INSTANCE.getSystemVersion())) {
                Log.i("PresetDB", "querySupport: begin update");
                realUpdate(context, TemplateApi.Companion.getInstance(context).getPresetTemplateGroup());
            }
            Log.i("PresetDB", "querySupport: " + str + " " + str2);
            if ((str2 == null || str2.length() == 0) && str != null) {
                str2 = getTemplateId(str);
            }
            Log.i("PresetDB", "querySupport: tId is " + str2);
            if (str2 == null) {
                return false;
            }
            if (Intrinsics.areEqual(str2, "all_in_one")) {
                return true;
            }
            return companion.getInstance(context).getPresetTemplateDao().support(str2);
        }

        public final void realUpdate(Context context, List list) {
            Object objM1061constructorimpl;
            String templateName;
            if (list == null || list.isEmpty()) {
                Log.i("PresetDB", "updatePresetDatabase: error");
                return;
            }
            try {
                Result.Companion companion = Result.Companion;
                if (!EditorDatabase.Companion.getInstance(context).getPresetTemplateDao().needUpdate(SystemEditor.INSTANCE.getSystemVersion())) {
                    Log.i("PresetDB", "updatePresetDatabase: not need ");
                    return;
                }
                Log.i("editor", "updatePresetDatabase: need update");
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    for (TemplateItemConfig templateItemConfig : ((TemplateGroupConfig) it.next()).getTemplates()) {
                        if (StringsKt.startsWith$default(templateItemConfig.getTemplateName(), "kg_", false, 2, null)) {
                            templateName = templateItemConfig.getTemplateName().substring(3);
                            Intrinsics.checkNotNullExpressionValue(templateName, "substring(...)");
                        } else {
                            templateName = templateItemConfig.getTemplateName();
                        }
                        String str = templateName;
                        String templateId = templateItemConfig.getTemplateId();
                        arrayList.add(new DatabasePresetTemplate(str, (templateId == null || templateId.length() == 0) ? getTemplateId(templateItemConfig.getTemplateName()) : templateItemConfig.getTemplateId(), templateItemConfig.getHideFlag(), 0, SystemEditor.INSTANCE.getSystemVersion(), 8, null));
                    }
                }
                arrayList.addAll(CrossListDataModel.alwaysSupportTemplate);
                Log.i("PresetDB", "updatePresetDatabase: update");
                EditorDatabase.Companion.getInstance(context).getPresetTemplateDao().updateAll(arrayList);
                objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
                Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
                if (thM1063exceptionOrNullimpl == null) {
                    return;
                }
                Log.e("PresetDB", "updatePresetDatabase: ", thM1063exceptionOrNullimpl);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
        }

        public final void updatePresetDatabaseAsync(Context context, List list) {
            Intrinsics.checkNotNullParameter(context, "context");
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new CrossListDataModel$Companion$updatePresetDatabaseAsync$1(context, list, null), 3, null);
        }
    }

    static {
        SystemEditor systemEditor = SystemEditor.INSTANCE;
        alwaysSupportTemplate = CollectionsKt.listOf((Object[]) new DatabasePresetTemplate[]{new DatabasePresetTemplate("classic_style1", "classic", 0, 0, systemEditor.getSystemVersion(), 8, null), new DatabasePresetTemplate("classic_plus_style2", "classic_plus", 0, 0, systemEditor.getSystemVersion(), 8, null), new DatabasePresetTemplate("all_in_one", "all_in_one", 0, 0, systemEditor.getSystemVersion(), 8, null)});
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CrossListDataModel(final Context context, CrossListPreloader crossListPreloader) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.preloader = crossListPreloader;
        this.TAG$1 = "Keyguard-Editor-CrossDataModel";
        this._maxTemplateCount = 20;
        this.allHistoryTemplates$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataModel.allHistoryTemplates_delegate$lambda$0();
            }
        });
        this.allPresetTemplates$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataModel.allPresetTemplates_delegate$lambda$1();
            }
        });
        this.allFloorItemBeans$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataModel.allFloorItemBeans_delegate$lambda$2();
            }
        });
        this.thirdPartyThemeTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataModel.thirdPartyThemeTask_delegate$lambda$4(this.f$0);
            }
        });
        this.currentTemplateTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataModel.currentTemplateTask_delegate$lambda$9(this.f$0, context);
            }
        });
        this.allHistoryTemplatesTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataModel.allHistoryTemplatesTask_delegate$lambda$12(this.f$0);
            }
        });
        this.allPresetTemplateTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListDataModel.allPresetTemplateTask_delegate$lambda$15(this.f$0);
            }
        });
    }

    public static final List allFloorItemBeans_delegate$lambda$2() {
        return new ArrayList();
    }

    public static final Runnable allHistoryTemplatesTask_delegate$lambda$12(final CrossListDataModel crossListDataModel) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                CrossListDataModel.allHistoryTemplatesTask_delegate$lambda$12$lambda$11(this.f$0);
            }
        };
    }

    public static final void allHistoryTemplatesTask_delegate$lambda$12$lambda$11(CrossListDataModel crossListDataModel) {
        try {
            Log.i(crossListDataModel.TAG$1, "allHistoryTemplatesTask start");
            crossListDataModel.historyTemplateGroup = null;
            crossListDataModel.getAllHistoryTemplates().clear();
            CrossListPreloader crossListPreloader = crossListDataModel.preloader;
            List listTakeAllHistoryTemplates = crossListPreloader != null ? crossListPreloader.takeAllHistoryTemplates() : null;
            Log.i(crossListDataModel.TAG$1, "allHistoryTemplatesTask: historyTemplates.size = " + (listTakeAllHistoryTemplates != null ? Integer.valueOf(listTakeAllHistoryTemplates.size()) : null) + "，historyTemplates = " + listTakeAllHistoryTemplates);
            if (listTakeAllHistoryTemplates == null || listTakeAllHistoryTemplates.size() == 0) {
                listTakeAllHistoryTemplates = (List) crossListDataModel.getAllHistoryTemplatesSupplier().get();
            } else {
                Log.i(crossListDataModel.TAG$1, "allHistoryTemplates preload hit");
            }
            if (listTakeAllHistoryTemplates != null) {
                crossListDataModel.getAllHistoryTemplates().addAll(listTakeAllHistoryTemplates);
            }
        } catch (Exception e) {
            Log.e(crossListDataModel.TAG$1, "allHistoryTemplatesTask error: " + e);
        } finally {
            Log.i(crossListDataModel.TAG$1, "allHistoryTemplatesTask completed");
            CountDownLatch countDownLatch = crossListDataModel.allCountdownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    public static final List allHistoryTemplates_delegate$lambda$0() {
        return new ArrayList();
    }

    public static final Runnable allPresetTemplateTask_delegate$lambda$15(final CrossListDataModel crossListDataModel) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                CrossListDataModel.allPresetTemplateTask_delegate$lambda$15$lambda$14(this.f$0);
            }
        };
    }

    public static final void allPresetTemplateTask_delegate$lambda$15$lambda$14(CrossListDataModel crossListDataModel) {
        try {
            Log.i(crossListDataModel.TAG$1, "allPresetTemplateTask start");
            crossListDataModel.getAllPresetTemplates().clear();
            CrossListPreloader crossListPreloader = crossListDataModel.preloader;
            List listTakePresetTemplates = crossListPreloader != null ? crossListPreloader.takePresetTemplates() : null;
            if (listTakePresetTemplates == null) {
                listTakePresetTemplates = (List) crossListDataModel.getAllPresetTemplateSupplier().get();
            } else {
                Log.i(crossListDataModel.TAG$1, "allPresetTemplateTask preload hit");
            }
            if (listTakePresetTemplates != null) {
                crossListDataModel.getAllPresetTemplates().addAll(listTakePresetTemplates);
            }
            crossListDataModel.loadPresetColorInfo();
        } catch (Exception e) {
            Log.e(crossListDataModel.TAG$1, "allPresetTemplateTask error: ", e);
        } finally {
            Log.i(crossListDataModel.TAG$1, "allPresetTemplateTask completed");
            CountDownLatch countDownLatch = crossListDataModel.allCountdownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    public static final List allPresetTemplates_delegate$lambda$1() {
        return new ArrayList();
    }

    public static final Runnable currentTemplateTask_delegate$lambda$9(final CrossListDataModel crossListDataModel, final Context context) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                CrossListDataModel.currentTemplateTask_delegate$lambda$9$lambda$8(this.f$0, context);
            }
        };
    }

    public static final void currentTemplateTask_delegate$lambda$9$lambda$8(CrossListDataModel crossListDataModel, Context context) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        crossListDataModel.currentTemplate = null;
        try {
            Log.i(crossListDataModel.TAG$1, "currentTemplateTask start");
            CrossListPreloader crossListPreloader = crossListDataModel.preloader;
            TemplateItemBean templateItemBeanTakeCurrentTemplate = crossListPreloader != null ? crossListPreloader.takeCurrentTemplate() : null;
            crossListDataModel.currentTemplate = templateItemBeanTakeCurrentTemplate;
            if (templateItemBeanTakeCurrentTemplate == null) {
                crossListDataModel.currentTemplate = (TemplateItemBean) crossListDataModel.getCurrentTemplateSupplier().get();
            } else {
                String screenshotWallpaperCacheTarget = crossListDataModel.getScreenshotWallpaperCacheTarget();
                if (screenshotWallpaperCacheTarget != null) {
                    BitmapTempStore.Companion companion = BitmapTempStore.Companion;
                    Bitmap bitmap = companion.getInstance().getBitmap("wallpaper_for_screenshot");
                    if (bitmap != null) {
                        companion.getInstance().putBitmap(screenshotWallpaperCacheTarget, bitmap);
                        companion.getInstance().removeBitmap("wallpaper_for_screenshot");
                    }
                }
                Log.i(crossListDataModel.TAG$1, "currentTemplate preload hit");
            }
            TemplateItemBean templateItemBean = crossListDataModel.currentTemplate;
            crossListDataModel.currentTemplateConfig = templateItemBean != null ? templateItemBean.getCurrent() : null;
            CommonConfig commonConfig = crossListDataModel.currentTemplateConfig;
            if (commonConfig != null && (lockscreenInfo2 = commonConfig.getLockscreenInfo()) != null) {
                lockscreenInfo2.setClockExtras(crossListDataModel.clockExtraForCurrentTemplate);
            }
            CommonConfig commonConfig2 = crossListDataModel.currentTemplateConfig;
            if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null && wallpaperInfo.getCurrentWallpaper() == null) {
                wallpaperInfo.setCurrentWallpaper(TemplateApi.Companion.getInstance(context).getCurrentWallpaper(wallpaperInfo));
                wallpaperInfo.setCurrentLockWallpaperSource(LockWallpaperSource.MIUI_WALLPAPER_MANAGER);
            }
            Log.d(crossListDataModel.TAG$1, "currentTemplateConfig: " + crossListDataModel.currentTemplateConfig);
        } catch (Exception e) {
            Log.e(crossListDataModel.TAG$1, "currentTemplateTask error: " + e);
        } finally {
            Log.i(crossListDataModel.TAG$1, "currentTemplateTask completed");
            CountDownLatch countDownLatch = crossListDataModel.allCountdownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    public static final void deleteHistoryTemplate$lambda$23(CrossListDataModel crossListDataModel, TemplateItemBean templateItemBean) {
        synchronized (crossListDataModel.getAllFloorItemBeans()) {
            try {
                List items = ((FloorItemBean) crossListDataModel.getAllFloorItemBeans().get(0)).getItems();
                if (!items.isEmpty() && CollectionsKt.contains(items, templateItemBean)) {
                    TypeIntrinsics.asMutableCollection(items).remove(templateItemBean);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static final List getAllDataList$lambda$17(CrossListDataModel crossListDataModel) throws InterruptedException {
        Log.i(crossListDataModel.TAG$1, "getAllDataList@Task start");
        Task.run(crossListDataModel.getThirdPartyThemeTask());
        Task.run(crossListDataModel.getCurrentTemplateTask());
        Task.run(crossListDataModel.getAllHistoryTemplatesTask());
        Task.run(crossListDataModel.getAllPresetTemplateTask());
        CountDownLatch countDownLatch = crossListDataModel.allCountdownLatch;
        if (countDownLatch != null) {
            countDownLatch.await();
        }
        Log.i(crossListDataModel.TAG$1, "getAllDataList@Task completed");
        return crossListDataModel.composeAllInternal();
    }

    public static final void getAllDataList$lambda$18(CrossListDataModel crossListDataModel, Consumer consumer, List list) {
        crossListDataModel.allCountdownLatch = null;
        consumer.accept(list);
    }

    public static final List getAllHistoryList$lambda$19(CrossListDataModel crossListDataModel) throws InterruptedException {
        crossListDataModel.setScreenshotWallpaperCacheTarget("wallpaper_for_screenshot_history");
        Log.i(crossListDataModel.TAG$1, "getAllHistoryList@Task start");
        Task.run(crossListDataModel.getThirdPartyThemeTask());
        Task.run(crossListDataModel.getCurrentTemplateTask());
        Task.run(crossListDataModel.getAllHistoryTemplatesTask());
        CountDownLatch countDownLatch = crossListDataModel.allCountdownLatch;
        if (countDownLatch != null) {
            countDownLatch.await();
        }
        Log.i(crossListDataModel.TAG$1, "getAllHistoryList@Task completed");
        crossListDataModel.setScreenshotWallpaperCacheTarget(null);
        return crossListDataModel.composeHistoryInternal();
    }

    public static final void getAllHistoryList$lambda$20(CrossListDataModel crossListDataModel, Consumer consumer, List list) {
        crossListDataModel.allCountdownLatch = null;
        consumer.accept(list);
    }

    public static final Pattern sTemplatePattern_delegate$lambda$46() {
        return Pattern.compile("^(kg_)?(?<name>\\w*)(_style)\\d");
    }

    public static final Runnable thirdPartyThemeTask_delegate$lambda$4(final CrossListDataModel crossListDataModel) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                CrossListDataModel.thirdPartyThemeTask_delegate$lambda$4$lambda$3(this.f$0);
            }
        };
    }

    public static final void thirdPartyThemeTask_delegate$lambda$4$lambda$3(CrossListDataModel crossListDataModel) {
        try {
            Log.i(crossListDataModel.TAG$1, "thirdPartyThemeTask start");
            CrossListPreloader crossListPreloader = crossListDataModel.preloader;
            TemplateItemBean templateItemBeanTakeThirdPartyThemeTemplate = crossListPreloader != null ? crossListPreloader.takeThirdPartyThemeTemplate() : null;
            crossListDataModel.thirdPartyThemeTemplate = templateItemBeanTakeThirdPartyThemeTemplate;
            if (templateItemBeanTakeThirdPartyThemeTemplate == null) {
                crossListDataModel.thirdPartyThemeTemplate = (TemplateItemBean) crossListDataModel.getThirdPartyThemeSupplier().get();
            } else {
                Log.i(crossListDataModel.TAG$1, "thirdPartyThemeTemplate preload hit");
            }
        } catch (Exception e) {
            Log.e(crossListDataModel.TAG$1, "thirdPartyThemeTask error: " + e);
        } finally {
            Log.i(crossListDataModel.TAG$1, "thirdPartyThemeTask completed");
            CountDownLatch countDownLatch = crossListDataModel.allCountdownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    public final List composeAllInternal() {
        getAllFloorItemBeans().clear();
        if (this.thirdPartyThemeTemplate != null && !getDataApi().isThirdPartyTheme()) {
            Log.i(this.TAG$1, "composeAllInternal: discard stale thirdPartyThemeTemplate");
            this.thirdPartyThemeTemplate = null;
        }
        if (this.thirdPartyThemeTemplate != null || this.currentTemplate != null || !getAllHistoryTemplates().isEmpty()) {
            FloorItemBean floorItemBean = new FloorItemBean(0, Integer.valueOf(R.string.kg_template_title_mine_collocation), null, new ArrayList());
            floorItemBean.getItems().add(new TemplateItemBean(-1, 0, null, null, null, null, null, null, null, false, false, false, false, 8188, null));
            TemplateItemBean templateItemBean = this.thirdPartyThemeTemplate;
            if (templateItemBean != null) {
                floorItemBean.getItems().add(templateItemBean);
            }
            TemplateItemBean templateItemBean2 = this.currentTemplate;
            if (templateItemBean2 != null) {
                floorItemBean.getItems().add(templateItemBean2);
                floorItemBean.setSelectPosition(1);
            }
            if (!getAllHistoryTemplates().isEmpty()) {
                Log.i(this.TAG$1, "composeAllInternal -> allHistoryTemplates = " + getAllHistoryTemplates().size());
                floorItemBean.getItems().addAll(getAllHistoryTemplates());
            }
            floorItemBean.setLoop(Boolean.FALSE);
            floorItemBean.setItemType(1);
            getAllFloorItemBeans().add(floorItemBean);
        }
        if (!getAllPresetTemplates().isEmpty()) {
            Log.i(this.TAG$1, "composeAllInternal -> allPresetTemplates = " + getAllPresetTemplates().size());
            List allFloorItemBeans = getAllFloorItemBeans();
            List allPresetTemplates = getAllPresetTemplates();
            Intrinsics.checkNotNull(allPresetTemplates);
            allFloorItemBeans.addAll(allPresetTemplates);
        }
        return getAllFloorItemBeans();
    }

    public final List composeHistoryInternal() {
        getAllFloorItemBeans().clear();
        if (this.thirdPartyThemeTemplate != null && !getDataApi().isThirdPartyTheme()) {
            Log.i(this.TAG$1, "composeHistoryInternal: discard stale thirdPartyThemeTemplate");
            this.thirdPartyThemeTemplate = null;
        }
        if (this.thirdPartyThemeTemplate != null || this.currentTemplate != null || !getAllHistoryTemplates().isEmpty()) {
            FloorItemBean floorItemBean = new FloorItemBean(0, Integer.valueOf(R.string.kg_template_title_mine_collocation), null, new ArrayList());
            TemplateItemBean templateItemBean = this.thirdPartyThemeTemplate;
            if (templateItemBean != null) {
                floorItemBean.getItems().add(templateItemBean);
            }
            TemplateItemBean templateItemBean2 = this.currentTemplate;
            if (templateItemBean2 != null) {
                floorItemBean.getItems().add(templateItemBean2);
            }
            if (!getAllHistoryTemplates().isEmpty()) {
                Log.i(this.TAG$1, "composeHistoryInternal -> allHistoryTemplates = " + getAllHistoryTemplates().size());
                floorItemBean.getItems().addAll(getAllHistoryTemplates());
            }
            floorItemBean.setLoop(Boolean.FALSE);
            floorItemBean.setItemType(1);
            getAllFloorItemBeans().add(floorItemBean);
        }
        return getAllFloorItemBeans();
    }

    public final void deleteHistoryTemplate(final TemplateItemBean templateItemBean) {
        if (CollectionsKt.contains(getAllHistoryTemplates(), templateItemBean)) {
            TypeIntrinsics.asMutableCollection(getAllHistoryTemplates()).remove(templateItemBean);
        }
        if (getAllFloorItemBeans().isEmpty()) {
            return;
        }
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                CrossListDataModel.deleteHistoryTemplate$lambda$23(this.f$0, templateItemBean);
            }
        });
    }

    public final List filterSpecialWallpapers(List list) {
        if (list == null) {
            return null;
        }
        List items = ((FloorItemBean) list.get(0)).getItems();
        TemplateItemBean templateItemBean = (TemplateItemBean) items.get(0);
        if (templateItemBean == null || templateItemBean.getItemType() != -1) {
            TemplateItemBean templateItemBean2 = (TemplateItemBean) items.get(0);
            if (templateItemBean2 != null && templateItemBean2.getItemType() == 4) {
                removeSpecialWallpaper(items, 1);
            }
            return list;
        }
        TemplateItemBean templateItemBean3 = (TemplateItemBean) items.get(1);
        if (templateItemBean3 != null && templateItemBean3.getItemType() == 4) {
            removeSpecialWallpaper(items, 2);
        }
        return list;
    }

    public final void getAllDataList(final Consumer consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        this.allCountdownLatch = new CountDownLatch(4);
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return CrossListDataModel.getAllDataList$lambda$17(this.f$0);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CrossListDataModel.getAllDataList$lambda$18(this.f$0, consumer, (List) obj);
            }
        });
    }

    public final List getAllFloorItemBeans() {
        return (List) this.allFloorItemBeans$delegate.getValue();
    }

    public final void getAllHistoryList(final Consumer consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        this.allCountdownLatch = new CountDownLatch(3);
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda14
            @Override // java.util.function.Supplier
            public final Object get() {
                return CrossListDataModel.getAllHistoryList$lambda$19(this.f$0);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.model.CrossListDataModel$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CrossListDataModel.getAllHistoryList$lambda$20(this.f$0, consumer, (List) obj);
            }
        });
    }

    public final List getAllHistoryTemplates() {
        return (List) this.allHistoryTemplates$delegate.getValue();
    }

    public final Runnable getAllHistoryTemplatesTask() {
        return (Runnable) this.allHistoryTemplatesTask$delegate.getValue();
    }

    public final Runnable getAllPresetTemplateTask() {
        return (Runnable) this.allPresetTemplateTask$delegate.getValue();
    }

    public final List getAllPresetTemplates() {
        return (List) this.allPresetTemplates$delegate.getValue();
    }

    public final Bundle getClockExtras() {
        return this.clockExtraForCurrentTemplate;
    }

    public final CommonConfig getCurrentTemplateConfig() {
        return this.currentTemplateConfig;
    }

    public final Runnable getCurrentTemplateTask() {
        return (Runnable) this.currentTemplateTask$delegate.getValue();
    }

    public final List getDataList() {
        return getAllFloorItemBeans();
    }

    public final Runnable getThirdPartyThemeTask() {
        return (Runnable) this.thirdPartyThemeTask$delegate.getValue();
    }

    public final boolean isHistoryTemplateExists(TemplateItemBean templateItemBean) {
        return CollectionsKt.contains(getAllHistoryTemplates(), templateItemBean);
    }

    public final void loadPresetColorInfo() {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        PresetColorInfo presetColorInfo;
        int i = 0;
        for (Object obj : getDataApi().getPresetTemplateGroup()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            int i3 = 0;
            for (Object obj2 : ((TemplateGroupConfig) obj).getTemplates()) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                CommonConfig commonConfig = (CommonConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(FileUtil.readFileFromAssets(getContext(), new TemplateFilePathGenerator(getContext()).generatePresetTemplateFilePath(((TemplateItemConfig) obj2).getTemplateName(), TemplateFilePathGenerator.TemplateFileType.CONFIG)), CommonConfig.class);
                if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (clockInfo = lockscreenInfo.getClockInfo()) != null && (presetColorInfo = (PresetColorInfo) FontFilterKt.getPRESET_FILTER_COLOR_SELECT_INFO().get(clockInfo.getTemplateId())) != null) {
                    presetColorInfo.setClockEffect(clockInfo.getClockEffect());
                    presetColorInfo.setPrimaryColor(clockInfo.getPrimaryColor());
                    presetColorInfo.setSecondaryColor(clockInfo.getSecondaryColor());
                    presetColorInfo.setPrimaryFontColorPanelId(clockInfo.getPrimaryFontColorPanelId());
                    presetColorInfo.setSecondaryFontColorPanelId(clockInfo.getSecondaryFontColorPanelId());
                }
                i3 = i4;
            }
            i = i2;
        }
    }

    public final void printBundle(Bundle bundle) {
        Log.d(this.TAG$1, "CrossListDataModel clockExtras:");
        Set<String> setKeySet = bundle != null ? bundle.keySet() : null;
        if (setKeySet == null || setKeySet.isEmpty()) {
            Log.i(this.TAG$1, "printBundle: bundle is empty");
            return;
        }
        for (String str : setKeySet) {
            Log.i(this.TAG$1, "printBundle: key = " + str + ", value = " + bundle.get(str));
        }
    }

    public final void processScreenConfigurationChanged() {
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        setPreviewWidth((int) resourcesProvider.provideTemplatePreviewWidth(getContext()));
        setPreviewHeight((int) resourcesProvider.provideTemplatePreviewHeight(getContext()));
    }

    public final void refreshDefaultDataListAsync$keyguardeditor_release(List defaultDataList, boolean z) {
        TemplateConfig lockscreenInfo;
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo;
        Intrinsics.checkNotNullParameter(defaultDataList, "defaultDataList");
        Iterator it = defaultDataList.iterator();
        while (it.hasNext()) {
            for (TemplateItemBean templateItemBean : ((FloorItemBean) it.next()).getItems()) {
                int itemType = templateItemBean.getItemType();
                if (itemType == 1) {
                    if (z && (commonConfig = this.currentTemplateConfig) != null && (lockscreenInfo2 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo2.getWallpaperInfo()) != null) {
                        wallpaperInfo.setCurrentWallpaper(TemplateApi.Companion.getInstance(getContext()).getCurrentWallpaper(wallpaperInfo));
                        wallpaperInfo.setCurrentLockWallpaperSource(LockWallpaperSource.MIUI_WALLPAPER_MANAGER);
                    }
                    CommonConfig commonConfig2 = this.currentTemplateConfig;
                    if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null) {
                        lockscreenInfo.setClockExtras(this.clockExtraForCurrentTemplate);
                    }
                } else if (itemType == 4) {
                    templateItemBean.setThirdPartyThemePreview(getDataApi().requireThirdPartyThemePreviewImage(getPreviewWidth(), getPreviewHeight()));
                }
            }
        }
    }

    public final void refreshDefaultDataListSync$keyguardeditor_release(List defaultDataList, boolean z) {
        PresetTemplateConfig preset;
        TemplateConfig lockscreenInfo;
        Intrinsics.checkNotNullParameter(defaultDataList, "defaultDataList");
        Iterator it = defaultDataList.iterator();
        while (it.hasNext()) {
            for (TemplateItemBean templateItemBean : ((FloorItemBean) it.next()).getItems()) {
                int itemType = templateItemBean.getItemType();
                if (itemType == 1) {
                    CommonConfig commonConfig = this.currentTemplateConfig;
                    if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
                        lockscreenInfo.setClockExtras(this.clockExtraForCurrentTemplate);
                    }
                } else if (itemType == 2) {
                    TemplateHistoryConfig history = templateItemBean.getHistory();
                    if (history != null) {
                        history.setLoadCompleted(false);
                    }
                } else if (itemType == 3 && (preset = templateItemBean.getPreset()) != null) {
                    preset.setLoadCompleted(false);
                    TemplateItemConfig bindItemConfig = preset.getBindItemConfig();
                    if (bindItemConfig != null) {
                        bindItemConfig.setReqWidth(getPreviewWidth());
                    }
                    TemplateItemConfig bindItemConfig2 = preset.getBindItemConfig();
                    if (bindItemConfig2 != null) {
                        bindItemConfig2.setReqHeight(getPreviewHeight());
                    }
                }
            }
        }
    }

    public final List removeSpecialWallpaper(List list, int i) {
        int iHashCode;
        CommonConfig current;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateItemBean templateItemBean = (TemplateItemBean) list.get(i);
        String resourceType = (templateItemBean == null || (current = templateItemBean.getCurrent()) == null || (lockscreenInfo = current.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType();
        if (resourceType != null && ((iHashCode = resourceType.hashCode()) == 3343923 ? resourceType.equals(CategoryConstants.CATEGORY_MAML) : iHashCode == 1196792382 ? resourceType.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER) : !(iHashCode != 1564712577 || !resourceType.equals("linkage_video")))) {
            list.remove(i);
        }
        return list;
    }

    public final int requireHistoryTemplateCount() {
        return getAllHistoryTemplates().size();
    }

    public final void setClockExtras(Bundle bundle) {
        TemplateConfig lockscreenInfo;
        this.clockExtraForCurrentTemplate = bundle;
        CommonConfig commonConfig = this.currentTemplateConfig;
        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
            lockscreenInfo.setClockExtras(bundle);
        }
        printBundle(bundle);
    }
}

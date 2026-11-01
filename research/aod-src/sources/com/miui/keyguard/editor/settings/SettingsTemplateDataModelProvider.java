package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.util.Log;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource;
import com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource;
import com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource;
import com.miui.keyguard.editor.settings.data.ThirdThemeDataSource;
import com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;

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
/* JADX INFO: compiled from: SettingsTemplateDataProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class SettingsTemplateDataModelProvider {
    public static final Companion Companion = new Companion(null);
    public final MutableSharedFlow _applyPositionFlow;
    public final Flow appPositionFlow;
    public final Context context;
    public final CurrentTemplateDataSource currentTemplateDataSource;
    public volatile boolean galleryOpenState;
    public final HistoryTemplateDataSource historyTemplateDataSource;
    public int previewHeight;
    public int previewWidth;
    public final SettingsConfigChangeDataSource settingsConfigChangeDataSource;
    public final ThirdThemeDataSource thirdThemeDataSource;
    public final WallpaperChangeNotifyDataSource wallpaperChangedDataSource;

    /* JADX INFO: compiled from: SettingsTemplateDataProvider.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateDataModelProvider$getResultTemplatesFlow$1, reason: invalid class name */
    /* JADX INFO: compiled from: SettingsTemplateDataProvider.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateDataModelProvider$getResultTemplatesFlow$1", f = "SettingsTemplateDataProvider.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function4<TemplateItemBean, TemplateItemBean, List<? extends TemplateItemBean>, Continuation<? super List<? extends TemplateItemBean>>, Object> {
        public final /* synthetic */ int $applyPosition;
        public /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public /* synthetic */ Object L$2;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i, Continuation continuation) {
            super(4, continuation);
            this.$applyPosition = i;
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(TemplateItemBean templateItemBean, TemplateItemBean templateItemBean2, List list, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SettingsTemplateDataModelProvider.this.new AnonymousClass1(this.$applyPosition, continuation);
            anonymousClass1.L$0 = templateItemBean;
            anonymousClass1.L$1 = templateItemBean2;
            anonymousClass1.L$2 = list;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return SettingsTemplateDataModelProvider.this.composeAllInternal(this.$applyPosition, (TemplateItemBean) this.L$0, (TemplateItemBean) this.L$1, (List) this.L$2);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateDataModelProvider$refreshMiuiWallpaperInfo$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SettingsTemplateDataProvider.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateDataModelProvider$refreshMiuiWallpaperInfo$1", f = "SettingsTemplateDataProvider.kt", l = {156}, m = "invokeSuspend")
    public static final class C01311 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateDataModelProvider$refreshMiuiWallpaperInfo$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SettingsTemplateDataProvider.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateDataModelProvider$refreshMiuiWallpaperInfo$1$1", f = "SettingsTemplateDataProvider.kt", l = {}, m = "invokeSuspend")
        public static final class C00301 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Context $context;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00301(Context context, Continuation continuation) {
                super(2, continuation);
                this.$context = context;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00301(this.$context, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00301) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                WallpaperController.Companion.getInstance(this.$context).refreshMiuiWallpaperInfo(this.$context);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01311(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01311(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01311) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                C00301 c00301 = new C00301(this.$context, null);
                this.label = 1;
                if (BuildersKt.withContext(io2, c00301, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public SettingsTemplateDataModelProvider(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        this.previewWidth = settingsResourceProvider.provideTemplateContainerWidth(context);
        this.previewHeight = settingsResourceProvider.provideTemplateContainerHeight(context);
        this.thirdThemeDataSource = new ThirdThemeDataSource(context);
        this.currentTemplateDataSource = new CurrentTemplateDataSource(context);
        this.historyTemplateDataSource = new HistoryTemplateDataSource(context);
        this.wallpaperChangedDataSource = new WallpaperChangeNotifyDataSource(context);
        this.settingsConfigChangeDataSource = new SettingsConfigChangeDataSource(context);
        MutableSharedFlow mutableSharedFlowMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2, null);
        this._applyPositionFlow = mutableSharedFlowMutableSharedFlow$default;
        this.appPositionFlow = mutableSharedFlowMutableSharedFlow$default;
        refreshData$default(this, 0, 1, null);
    }

    public static /* synthetic */ void refreshData$default(SettingsTemplateDataModelProvider settingsTemplateDataModelProvider, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        settingsTemplateDataModelProvider.refreshData(i);
    }

    public final List composeAllInternal(int i, TemplateItemBean templateItemBean, TemplateItemBean templateItemBean2, List list) {
        Log.i("SettingsTemplateDataModel", "composeAllInternal start");
        LinkedList linkedList = new LinkedList();
        linkedList.add(new TemplateItemBean(-1, 0, Integer.valueOf(R.string.kg_template_title_mine_collocation), null, null, null, null, null, null, false, false, false, false, 8176, null));
        if (templateItemBean != null || templateItemBean2 != null || !list.isEmpty()) {
            if (i <= 0) {
                if (templateItemBean != null) {
                    linkedList.add(templateItemBean);
                }
                if (templateItemBean2 != null) {
                    linkedList.add(templateItemBean2);
                }
                if (!list.isEmpty()) {
                    linkedList.addAll(list);
                }
            } else {
                linkedList.addAll(list);
                if (i >= linkedList.size()) {
                    if (templateItemBean != null) {
                        linkedList.add(templateItemBean);
                    }
                    if (templateItemBean2 != null) {
                        linkedList.add(templateItemBean2);
                    }
                } else {
                    if (templateItemBean2 != null) {
                        linkedList.add(i, templateItemBean2);
                    }
                    if (templateItemBean != null) {
                        linkedList.add(i, templateItemBean);
                    }
                }
            }
            Log.i("SettingsTemplateDataModel", "composeAllInternal -> myTemplateList = " + linkedList.size());
        }
        return filterSpecialWallpapers(linkedList);
    }

    public final Object deleteHistory(TemplateItemBean templateItemBean, Continuation continuation) {
        return this.historyTemplateDataSource.deleteHistory(templateItemBean, continuation);
    }

    public final LinkedList filterSpecialWallpapers(LinkedList linkedList) {
        TemplateItemBean templateItemBean;
        TemplateItemBean templateItemBean2;
        int iHashCode;
        CommonConfig current;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (linkedList.size() > 2 && (templateItemBean = (TemplateItemBean) linkedList.get(0)) != null && templateItemBean.getItemType() == -1 && (templateItemBean2 = (TemplateItemBean) linkedList.get(1)) != null && templateItemBean2.getItemType() == 4) {
            TemplateItemBean templateItemBean3 = (TemplateItemBean) linkedList.get(2);
            String resourceType = (templateItemBean3 == null || (current = templateItemBean3.getCurrent()) == null || (lockscreenInfo = current.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType();
            if (resourceType != null && ((iHashCode = resourceType.hashCode()) == 3343923 ? resourceType.equals(CategoryConstants.CATEGORY_MAML) : iHashCode == 1196792382 ? resourceType.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER) : !(iHashCode != 1564712577 || !resourceType.equals("linkage_video")))) {
                linkedList.remove(2);
            }
        }
        return linkedList;
    }

    public final CurrentTemplateDataSource getCurrentTemplateDataSource() {
        return this.currentTemplateDataSource;
    }

    public final boolean getGalleryOpenState() {
        return this.galleryOpenState;
    }

    public final Object getHistoryLockAndHomeBitmap(Context context, TemplateItemBean templateItemBean, int i, int i2, Continuation continuation) {
        return this.historyTemplateDataSource.getHistoryTemplateBitmap(context, templateItemBean, i, i2, continuation);
    }

    public final Flow getRefreshTemplatesFlow() {
        return FlowKt.flowOn(FlowKt.transformLatest(this.appPositionFlow, new SettingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1(null, this)), Dispatchers.getIO());
    }

    public final Flow getResultTemplatesFlow(int i) {
        return FlowKt.combine(this.thirdThemeDataSource.getThirdThemeTemplateFlow(this.previewWidth, this.previewHeight), this.currentTemplateDataSource.getCurrentTemplateFlow(), this.historyTemplateDataSource.getHistoryTemplatesFlow(), new AnonymousClass1(i, null));
    }

    public final Flow mergeWallPaperAndSettingsConfigChange() {
        return FlowKt.flowOn(FlowKt.merge(this.wallpaperChangedDataSource.getWallPaperChangedEventFlow(), this.settingsConfigChangeDataSource.getSettingsConfigChangedEventFlow(), this.historyTemplateDataSource.getHistoryTemplatesChangedEventFlow()), Dispatchers.getIO());
    }

    public final void processScreenConfigurationChanged() {
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        this.previewWidth = settingsResourceProvider.provideTemplateContainerWidth(this.context);
        this.previewHeight = settingsResourceProvider.provideTemplateContainerHeight(this.context);
    }

    public final void refreshData(int i) {
        Log.i("SettingsTemplateDataModel", "_applyPositionFlow.tryEmit(applyPosition) -> applyPosition = " + i + ", emitResult=" + this._applyPositionFlow.tryEmit(Integer.valueOf(i)));
    }

    public final void refreshMiuiWallpaperInfo(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new C01311(context, null), 3, null);
    }

    public final void setGalleryOpenState(boolean z) {
        this.galleryOpenState = z;
    }
}

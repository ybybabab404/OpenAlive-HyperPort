package com.miui.keyguard.editor.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Consumer;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateApiImpl;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.CombineEditListener;
import com.miui.keyguard.editor.edit.color.picker.ColorPickerCache;
import com.miui.keyguard.editor.history.common.ViewExtKt;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ApplyCallback;
import com.miui.keyguard.editor.homepage.util.SelectItemProvider;
import com.miui.keyguard.editor.homepage.util.TemplateApplyController;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.homepage.view.viewpager.HorizontalSpringTouchForSettingsManager;
import com.miui.keyguard.editor.homepage.view.viewpager.SpringLayout;
import com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource;
import com.miui.keyguard.editor.settings.data.HomeColumnDataSource;
import com.miui.keyguard.editor.settings.util.ContextUtilKt;
import com.miui.keyguard.editor.settings.util.SettingsTrackHelper;
import com.miui.keyguard.editor.settings.util.ViewUtilKt;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.keyguard.editor.view.viewpager2.CompositePageTransformer;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.xiaomi.onetrack.api.am;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.app.AlertDialog;
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
/* JADX INFO: compiled from: SettingsTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class SettingsTemplateView extends ConstraintLayout implements SelectItemProvider, CrossViewPagerCallback, TemplateApplyListener, CombineEditListener {
    public static final Companion Companion = new Companion(null);
    public final Lazy applyBtn$delegate;
    public final Lazy applyBtnText$delegate;
    public AlertDialog applyFailedDialog;
    public volatile int configChangedCount;
    public SettingsTemplateDataModelProvider dataModel;
    public AlertDialog deleteTemplateDialog;
    public final Lazy deskTopLoadingView$delegate;
    public ContentObserver galleryStateObserver;
    public final Handler handler;
    public RecyclerView.OnChildAttachStateChangeListener hideNonNegativeListener;
    public BaseTemplateView homeTemplateView;
    public boolean isApplyCurrentTemplate;
    public boolean isApplyingTemplateInternal;
    public boolean isCurrentTemplateReady;
    public boolean isFromMatchInternalApply;
    public boolean isFromMatchInternalDelete;
    public boolean isHistoryTemplateReady;
    public boolean isHomeWallpaperDarkResultReady;
    public Boolean isInFreeform;
    public Boolean isLargeScreen;
    public boolean isPageVisible;
    public boolean isUseBlur;
    public final Lazy keyGuardLoadingView$delegate;
    public SettingsConfigSnapshot lastConfigSnapshot;
    public Boolean lastGalleryOpenState;
    public final Function0 loadDataCallback;
    public final Lazy loadingView$delegate;
    public BaseTemplateView lockTemplateView;
    public boolean mScrolled;
    public TemplateApplyController mTemplateApplyController;
    public final Lazy newMatchTextView$delegate;
    public int orientation;
    public boolean pendingExternalRefresh;
    public final Lazy progressBar$delegate;
    public CommonConfig screenshotConfig;
    public int selectedPosition;
    public final Lazy settingView$delegate;
    public boolean shouldCaptureCurrentTemplate;
    public final Lazy springLayout$delegate;
    public final HorizontalSpringTouchForSettingsManager springTouchForSettingsManager;
    public SettingsTemplateAdapter templateAdapter;
    public SettingsTemplateApplyingLayer templateApplyingLayer;
    public SettingsTemplateDeleteLayer templateDeleteLayer;
    public List templateListResult;
    public AtomicInteger templateReadyCount;
    public int templateStatus;
    public int usingPosition;
    public final Lazy usingTextView$delegate;
    public final int version;
    public final Lazy viewPager2$delegate;
    public volatile int wallpaperChangedCount;

    /* JADX INFO: compiled from: SettingsTemplateView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: SettingsTemplateView.kt */
    @Metadata
    public static final class SettingsConfigSnapshot {
        public final int densityDpi;
        public final int displayOrientation;
        public final boolean isInFreeform;
        public final boolean isLargeScreen;
        public final int newConfigOrientation;
        public final int screenHeightDp;
        public final int screenWidthDp;

        public SettingsConfigSnapshot(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            this.densityDpi = i;
            this.screenWidthDp = i2;
            this.screenHeightDp = i3;
            this.newConfigOrientation = i4;
            this.displayOrientation = i5;
            this.isInFreeform = z;
            this.isLargeScreen = z2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SettingsConfigSnapshot)) {
                return false;
            }
            SettingsConfigSnapshot settingsConfigSnapshot = (SettingsConfigSnapshot) obj;
            return this.densityDpi == settingsConfigSnapshot.densityDpi && this.screenWidthDp == settingsConfigSnapshot.screenWidthDp && this.screenHeightDp == settingsConfigSnapshot.screenHeightDp && this.newConfigOrientation == settingsConfigSnapshot.newConfigOrientation && this.displayOrientation == settingsConfigSnapshot.displayOrientation && this.isInFreeform == settingsConfigSnapshot.isInFreeform && this.isLargeScreen == settingsConfigSnapshot.isLargeScreen;
        }

        public int hashCode() {
            return (((((((((((Integer.hashCode(this.densityDpi) * 31) + Integer.hashCode(this.screenWidthDp)) * 31) + Integer.hashCode(this.screenHeightDp)) * 31) + Integer.hashCode(this.newConfigOrientation)) * 31) + Integer.hashCode(this.displayOrientation)) * 31) + Boolean.hashCode(this.isInFreeform)) * 31) + Boolean.hashCode(this.isLargeScreen);
        }

        public String toString() {
            return "SettingsConfigSnapshot(densityDpi=" + this.densityDpi + ", screenWidthDp=" + this.screenWidthDp + ", screenHeightDp=" + this.screenHeightDp + ", newConfigOrientation=" + this.newConfigOrientation + ", displayOrientation=" + this.displayOrientation + ", isInFreeform=" + this.isInFreeform + ", isLargeScreen=" + this.isLargeScreen + ")";
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$checkGalleryStateChanged$1, reason: invalid class name */
    /* JADX INFO: compiled from: SettingsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$checkGalleryStateChanged$1", f = "SettingsTemplateView.kt", l = {715}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsTemplateView.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                SettingsTemplateView$checkGalleryStateChanged$1$currentOpen$1 settingsTemplateView$checkGalleryStateChanged$1$currentOpen$1 = new SettingsTemplateView$checkGalleryStateChanged$1$currentOpen$1(SettingsTemplateView.this, null);
                this.label = 1;
                obj = BuildersKt.withContext(io2, settingsTemplateView$checkGalleryStateChanged$1$currentOpen$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = SettingsTemplateView.this.dataModel;
            if (settingsTemplateDataModelProvider != null) {
                settingsTemplateDataModelProvider.setGalleryOpenState(zBooleanValue);
            }
            if (SettingsTemplateView.this.lastGalleryOpenState != null && !Intrinsics.areEqual(SettingsTemplateView.this.lastGalleryOpenState, Boxing.boxBoolean(zBooleanValue))) {
                Log.i("SettingsTemplateView", "gallery state changed: " + SettingsTemplateView.this.lastGalleryOpenState + " -> " + zBooleanValue + ", notify adapter");
                RecyclerView.Adapter adapter = SettingsTemplateView.this.getViewPager2().getAdapter();
                if (adapter != null) {
                    adapter.notifyItemChanged(SettingsTemplateView.this.getViewPager2().getCurrentItem());
                }
            }
            SettingsTemplateView.this.lastGalleryOpenState = Boxing.boxBoolean(zBooleanValue);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$deletePage$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SettingsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$deletePage$1", f = "SettingsTemplateView.kt", l = {895}, m = "invokeSuspend")
    public static final class C01321 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ TemplateItemBean $itemBean;
        public final /* synthetic */ int $position;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01321(TemplateItemBean templateItemBean, int i, Continuation continuation) {
            super(2, continuation);
            this.$itemBean = templateItemBean;
            this.$position = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsTemplateView.this.new C01321(this.$itemBean, this.$position, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01321) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:19:0x009e  */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int i;
            int i2;
            int i3;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i4 = this.label;
            if (i4 == 0) {
                ResultKt.throwOnFailure(obj);
                SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = SettingsTemplateView.this.dataModel;
                if (settingsTemplateDataModelProvider != null) {
                    TemplateItemBean templateItemBean = this.$itemBean;
                    this.label = 1;
                    obj = settingsTemplateDataModelProvider.deleteHistory(templateItemBean, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                SettingsTemplateView settingsTemplateView = SettingsTemplateView.this;
                Context context = SettingsTemplateView.this.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                SettingsTemplateDeleteLayer settingsTemplateDeleteLayer = new SettingsTemplateDeleteLayer(context, null, 2, 0 == true ? 1 : 0);
                int i5 = this.$position;
                SettingsTemplateView settingsTemplateView2 = SettingsTemplateView.this;
                settingsTemplateDeleteLayer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                settingsTemplateDeleteLayer.setClipChildren(false);
                settingsTemplateDeleteLayer.initLayout(i5, settingsTemplateView2.getViewPager2(), settingsTemplateView2.isUseBlur);
                settingsTemplateView.templateDeleteLayer = settingsTemplateDeleteLayer;
                SettingsTemplateView.this.getSettingView().addView(SettingsTemplateView.this.templateDeleteLayer);
                SettingsTemplateView settingsTemplateView3 = SettingsTemplateView.this;
                i = settingsTemplateView3.selectedPosition;
                i2 = SettingsTemplateView.this.usingPosition;
                i3 = SettingsTemplateView.this.usingPosition;
                if (i < i2) {
                    i3--;
                }
                settingsTemplateView3.refreshMyTemplate(i3);
                return Unit.INSTANCE;
            }
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!((Boolean) obj).booleanValue()) {
                Log.e("SettingsTemplateView", "onPageDeleted failed: data delete failed.");
                SettingsTemplateView.this.springTouchForSettingsManager.setEnableTouch(true);
                return Unit.INSTANCE;
            }
            SettingsTemplateView settingsTemplateView4 = SettingsTemplateView.this;
            Context context2 = SettingsTemplateView.this.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            SettingsTemplateDeleteLayer settingsTemplateDeleteLayer2 = new SettingsTemplateDeleteLayer(context2, null, 2, 0 == true ? 1 : 0);
            int i6 = this.$position;
            SettingsTemplateView settingsTemplateView5 = SettingsTemplateView.this;
            settingsTemplateDeleteLayer2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            settingsTemplateDeleteLayer2.setClipChildren(false);
            settingsTemplateDeleteLayer2.initLayout(i6, settingsTemplateView5.getViewPager2(), settingsTemplateView5.isUseBlur);
            settingsTemplateView4.templateDeleteLayer = settingsTemplateDeleteLayer2;
            SettingsTemplateView.this.getSettingView().addView(SettingsTemplateView.this.templateDeleteLayer);
            SettingsTemplateView settingsTemplateView6 = SettingsTemplateView.this;
            i = settingsTemplateView6.selectedPosition;
            i2 = SettingsTemplateView.this.usingPosition;
            i3 = SettingsTemplateView.this.usingPosition;
            if (i < i2) {
                i3--;
            }
            settingsTemplateView6.refreshMyTemplate(i3);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2, reason: invalid class name */
    /* JADX INFO: compiled from: SettingsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2", f = "SettingsTemplateView.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: SettingsTemplateView.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$1", f = "SettingsTemplateView.kt", l = {364}, m = "invokeSuspend")
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;
            public final /* synthetic */ SettingsTemplateView this$0;

            /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: SettingsTemplateView.kt */
            @Metadata
            @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$1$1", f = "SettingsTemplateView.kt", l = {}, m = "invokeSuspend")
            public static final class C00311 extends SuspendLambda implements Function2<String, Continuation<? super Unit>, Object> {
                public /* synthetic */ Object L$0;
                public int label;
                public final /* synthetic */ SettingsTemplateView this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00311(SettingsTemplateView settingsTemplateView, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = settingsTemplateView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00311 c00311 = new C00311(this.this$0, continuation);
                    c00311.L$0 = obj;
                    return c00311;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(String str, Continuation continuation) {
                    return ((C00311) create(str, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    String str = (String) this.L$0;
                    if (Intrinsics.areEqual(str, "wallpaperChanged")) {
                        this.this$0.wallpaperChangedCount++;
                    } else if (Intrinsics.areEqual(str, "settings_template_info_changed")) {
                        this.this$0.configChangedCount++;
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(SettingsTemplateView settingsTemplateView, Continuation continuation) {
                super(2, continuation);
                this.this$0 = settingsTemplateView;
            }

            public static final long invokeSuspend$lambda$0(SettingsTemplateView settingsTemplateView, String str) {
                boolean zAreEqual = Intrinsics.areEqual(str, "settings_template_info_changed");
                int i = settingsTemplateView.wallpaperChangedCount;
                boolean z = false;
                boolean z2 = 1 <= i && i < 2;
                boolean z3 = settingsTemplateView.configChangedCount > 0;
                if (!settingsTemplateView.isApplyCurrentTemplate && !CurrentTemplateDataSource.Companion.getApplyingMaml()) {
                    z = true;
                }
                if (z2 && (zAreEqual || z)) {
                    return 1500L;
                }
                return (z2 || z3) ? 0L : 100L;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Flow flowMergeWallPaperAndSettingsConfigChange;
                Flow flowOnEach;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = this.this$0.dataModel;
                    if (settingsTemplateDataModelProvider != null && (flowMergeWallPaperAndSettingsConfigChange = settingsTemplateDataModelProvider.mergeWallPaperAndSettingsConfigChange()) != null && (flowOnEach = FlowKt.onEach(flowMergeWallPaperAndSettingsConfigChange, new C00311(this.this$0, null))) != null) {
                        final SettingsTemplateView settingsTemplateView = this.this$0;
                        Flow flowDebounce = FlowKt.debounce(flowOnEach, new Function1() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                return Long.valueOf(SettingsTemplateView.AnonymousClass2.AnonymousClass1.invokeSuspend$lambda$0(settingsTemplateView, (String) obj2));
                            }
                        });
                        if (flowDebounce != null) {
                            final SettingsTemplateView settingsTemplateView2 = this.this$0;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView.onFinishInflate.2.1.3
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(String str, Continuation continuation) {
                                    Log.i("SettingsTemplateView", "onMergeWallPaperAndSettingsConfigChange: dataSourceChanged=" + str + ", wallpaperChangedCount=" + settingsTemplateView2.wallpaperChangedCount + ", configChangedCount=" + settingsTemplateView2.configChangedCount + ", applyingMaml=" + CurrentTemplateDataSource.Companion.getApplyingMaml() + ", isFromMatchInternal=" + settingsTemplateView2.isFromMatchInternalApply + ", isApplyCurrentTemplate=" + settingsTemplateView2.isApplyCurrentTemplate + ", isApplyingTemplateInternal=" + settingsTemplateView2.isApplyingTemplateInternal);
                                    if (!Intrinsics.areEqual(str, "settings_template_info_changed")) {
                                        settingsTemplateView2.shouldCaptureCurrentTemplate = true;
                                        Log.d("SettingsTemplateView", "capture for wallpaper changed");
                                        if (settingsTemplateView2.selectedPosition <= 0) {
                                            settingsTemplateView2.refreshButtonWhenApply();
                                        }
                                    }
                                    if (settingsTemplateView2.isApplyingTemplateInternal && settingsTemplateView2.isFromMatchInternalApply) {
                                        return Unit.INSTANCE;
                                    }
                                    if (Intrinsics.areEqual(str, "settings_template_info_changed")) {
                                        if (settingsTemplateView2.isFromMatchInternalApply) {
                                            SettingsTemplateView settingsTemplateView3 = settingsTemplateView2;
                                            settingsTemplateView3.refreshMyTemplate(settingsTemplateView3.selectedPosition);
                                        } else if (settingsTemplateView2.isPageVisible) {
                                            SettingsTemplateView.refreshMyTemplate$default(settingsTemplateView2, 0, 1, null);
                                        } else {
                                            settingsTemplateView2.pendingExternalRefresh = true;
                                            Boxing.boxInt(Log.i("SettingsTemplateView", "defer settings_template_info refresh (page not visible)"));
                                        }
                                    } else if (Intrinsics.areEqual(str, "history_templates_changed")) {
                                        if (!settingsTemplateView2.isFromMatchInternalDelete) {
                                            SettingsTemplateView.refreshMyTemplate$default(settingsTemplateView2, 0, 1, null);
                                        }
                                    } else if (!settingsTemplateView2.isFromMatchInternalApply || settingsTemplateView2.isApplyCurrentTemplate) {
                                        if (settingsTemplateView2.isPageVisible) {
                                            SettingsTemplateView.refreshMyTemplate$default(settingsTemplateView2, 0, 1, null);
                                        } else {
                                            settingsTemplateView2.pendingExternalRefresh = true;
                                            Boxing.boxInt(Log.i("SettingsTemplateView", "defer wallpaper refresh (page not visible)"));
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (flowDebounce.collect(flowCollector, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
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

        /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SettingsTemplateView.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$2", f = "SettingsTemplateView.kt", l = {407}, m = "invokeSuspend")
        public static final class C00322 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;
            public final /* synthetic */ SettingsTemplateView this$0;

            /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$2$1, reason: invalid class name */
            /* JADX INFO: compiled from: SettingsTemplateView.kt */
            @Metadata
            @SourceDebugExtension
            public static final class AnonymousClass1<T> implements FlowCollector {
                public final /* synthetic */ SettingsTemplateView this$0;

                public AnonymousClass1(SettingsTemplateView settingsTemplateView) {
                    this.this$0 = settingsTemplateView;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(List list, Continuation continuation) {
                    boolean z;
                    CurrentTemplateDataSource currentTemplateDataSource;
                    if (list.isEmpty()) {
                        return Unit.INSTANCE;
                    }
                    this.this$0.wallpaperChangedCount = 0;
                    this.this$0.configChangedCount = 0;
                    ColorPickerCache.INSTANCE.clearMyColorData();
                    Log.i("SettingsTemplateView", "myTemplateList.size=" + list.size());
                    this.this$0.templateListResult = list;
                    if (!this.this$0.shouldCaptureCurrentTemplate) {
                        SettingsTemplateView settingsTemplateView = this.this$0;
                        SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = settingsTemplateView.dataModel;
                        settingsTemplateView.shouldCaptureCurrentTemplate = (settingsTemplateDataModelProvider == null || (currentTemplateDataSource = settingsTemplateDataModelProvider.getCurrentTemplateDataSource()) == null) ? true : currentTemplateDataSource.isCurrentConfigChanged();
                        Log.d("SettingsTemplateView", "capture for config changed=" + this.this$0.shouldCaptureCurrentTemplate);
                    }
                    SettingsTemplateView settingsTemplateView2 = this.this$0;
                    Iterator it = list.iterator();
                    int i = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            i = -1;
                            break;
                        }
                        TemplateItemBean templateItemBean = (TemplateItemBean) it.next();
                        if (templateItemBean.getItemType() == 4 || templateItemBean.getItemType() == 1) {
                            break;
                        }
                        i++;
                    }
                    settingsTemplateView2.usingPosition = i;
                    Log.i("SettingsTemplateView", "usingPosition=" + this.this$0.usingPosition);
                    if (!this.this$0.isFromMatchInternalDelete && this.this$0.usingPosition == 1) {
                        this.this$0.hideNonNegativeItems();
                    }
                    if (this.this$0.getViewPager2().getAdapter() == null) {
                        this.this$0.setPagerTransform();
                        SettingsTemplateAdapter settingsTemplateAdapter = this.this$0.templateAdapter;
                        if (settingsTemplateAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("templateAdapter");
                            settingsTemplateAdapter = null;
                        }
                        settingsTemplateAdapter.setData(list);
                        DampViewPager2 viewPager2 = this.this$0.getViewPager2();
                        SettingsTemplateAdapter settingsTemplateAdapter2 = this.this$0.templateAdapter;
                        if (settingsTemplateAdapter2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("templateAdapter");
                            settingsTemplateAdapter2 = null;
                        }
                        viewPager2.setAdapter(settingsTemplateAdapter2);
                    } else {
                        SettingsTemplateAdapter settingsTemplateAdapter3 = this.this$0.templateAdapter;
                        if (settingsTemplateAdapter3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("templateAdapter");
                            settingsTemplateAdapter3 = null;
                        }
                        settingsTemplateAdapter3.setData(list);
                        settingsTemplateAdapter3.notifyDataSetChanged();
                    }
                    if (this.this$0.isFromMatchInternalDelete) {
                        if (this.this$0.selectedPosition >= list.size()) {
                            this.this$0.selectedPosition = list.size() - 1;
                        }
                        this.this$0.getViewPager2().setCurrentItem(this.this$0.selectedPosition, false);
                        if (this.this$0.usingPosition == this.this$0.selectedPosition) {
                            this.this$0.refreshButtonWhenApply();
                        }
                        this.this$0.startDeleteTemplateAnim();
                    } else {
                        this.this$0.getViewPager2().setCurrentItem(this.this$0.usingPosition, false);
                        SettingsTemplateView settingsTemplateView3 = this.this$0;
                        settingsTemplateView3.selectedPosition = settingsTemplateView3.usingPosition;
                        if (this.this$0.selectedPosition != 1) {
                            ViewUtilKt.fakeHide(this.this$0.getSpringLayout());
                        }
                    }
                    Iterator it2 = list.iterator();
                    int i2 = 0;
                    while (true) {
                        if (!it2.hasNext()) {
                            i2 = -1;
                            break;
                        }
                        if (((TemplateItemBean) it2.next()).getItemType() == 1) {
                            break;
                        }
                        i2++;
                    }
                    boolean z2 = i2 != -1;
                    if (!list.isEmpty()) {
                        Iterator<T> it3 = list.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                z = false;
                                break;
                            }
                            if (((TemplateItemBean) it3.next()).getItemType() == 2) {
                                z = true;
                                break;
                            }
                        }
                    } else {
                        z = false;
                        break;
                    }
                    Log.i("SettingsTemplateView", "hasCurrentTemplate=" + z2 + ", hasHistoryTemplate=" + z);
                    this.this$0.isCurrentTemplateReady = false;
                    this.this$0.isHistoryTemplateReady = z ^ true;
                    this.this$0.templateStatus = 2;
                    if (z2) {
                        final Function0 function0 = this.this$0.loadDataCallback;
                        SettingsTemplateView settingsTemplateView4 = this.this$0;
                        settingsTemplateView4.handler.removeCallbacks(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$2$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                function0.invoke();
                            }
                        });
                        settingsTemplateView4.handler.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$2$2$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                function0.invoke();
                            }
                        }, 2000L);
                    } else {
                        this.this$0.refreshButtonWhenApply();
                        SettingsTemplateView.restoreTemplate$default(this.this$0, null, 1, null);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00322(SettingsTemplateView settingsTemplateView, Continuation continuation) {
                super(2, continuation);
                this.this$0 = settingsTemplateView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00322(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00322) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Flow refreshTemplatesFlow;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = this.this$0.dataModel;
                    if (settingsTemplateDataModelProvider != null && (refreshTemplatesFlow = settingsTemplateDataModelProvider.getRefreshTemplatesFlow()) != null) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
                        this.label = 1;
                        if (refreshTemplatesFlow.collect(anonymousClass1, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = SettingsTemplateView.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(SettingsTemplateView.this, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C00322(SettingsTemplateView.this, null), 3, null);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$onVisibilityChanged$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SettingsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$onVisibilityChanged$2", f = "SettingsTemplateView.kt", l = {}, m = "invokeSuspend")
    public static final class C01332 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01332(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsTemplateView.this.new C01332(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01332) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsTemplateView settingsTemplateView = SettingsTemplateView.this;
            Context context = settingsTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            settingsTemplateView.isUseBlur = ViewStylerKt.canUseBlur(context);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.SettingsTemplateView$takeScreenshotAfterRestore$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SettingsTemplateView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateView$takeScreenshotAfterRestore$2", f = "SettingsTemplateView.kt", l = {}, m = "invokeSuspend")
    public static final class C01352 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01352(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsTemplateView.this.new C01352(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01352) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsTemplateView settingsTemplateView = SettingsTemplateView.this;
            BaseTemplateView baseTemplateView = settingsTemplateView.lockTemplateView;
            Intrinsics.checkNotNull(baseTemplateView);
            BaseTemplateView baseTemplateView2 = SettingsTemplateView.this.homeTemplateView;
            CommonConfig commonConfig = SettingsTemplateView.this.screenshotConfig;
            Intrinsics.checkNotNull(commonConfig);
            settingsTemplateView.onCurrentTemplateScreenshot(baseTemplateView, baseTemplateView2, commonConfig);
            return Unit.INSTANCE;
        }
    }

    public static void $r8$lambda$7LUkO1oauh_b7GDsMJ_WXuJN1xU(View view) {
    }

    public static Unit $r8$lambda$ANMXoLDBO4j72DLA0WgHj3CJIOU(SettingsTemplateView settingsTemplateView, Context context, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (settingsTemplateView.getUsingTextView().getVisibility() == 0) {
            return Unit.INSTANCE;
        }
        ContextUtilKt.navigatorToFloorActivity(context, settingsTemplateView.version);
        return Unit.INSTANCE;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SettingsTemplateView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SettingsTemplateView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SettingsTemplateView(@NotNull final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.settingView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.settingView_delegate$lambda$0(this.f$0);
            }
        });
        this.springLayout$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.springLayout_delegate$lambda$1(this.f$0);
            }
        });
        this.viewPager2$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.viewPager2_delegate$lambda$2(this.f$0);
            }
        });
        this.loadingView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.loadingView_delegate$lambda$3(this.f$0);
            }
        });
        this.keyGuardLoadingView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.keyGuardLoadingView_delegate$lambda$4(this.f$0);
            }
        });
        this.deskTopLoadingView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.deskTopLoadingView_delegate$lambda$5(this.f$0);
            }
        });
        this.applyBtn$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.applyBtn_delegate$lambda$6(this.f$0);
            }
        });
        this.progressBar$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.progressBar_delegate$lambda$7(this.f$0);
            }
        });
        this.applyBtnText$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.applyBtnText_delegate$lambda$8(this.f$0);
            }
        });
        this.usingTextView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.usingTextView_delegate$lambda$9(this.f$0);
            }
        });
        this.newMatchTextView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.newMatchTextView_delegate$lambda$10(this.f$0);
            }
        });
        this.templateListResult = CollectionsKt.emptyList();
        this.selectedPosition = -1;
        this.usingPosition = 1;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        int editorVersion = deviceUtil.getEditorVersion(context);
        this.version = editorVersion;
        this.templateReadyCount = new AtomicInteger(0);
        Boolean bool = Boolean.FALSE;
        this.isInFreeform = bool;
        this.handler = new Handler(Looper.getMainLooper());
        this.isLargeScreen = bool;
        this.loadDataCallback = new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateView.loadDataCallback$lambda$11(this.f$0);
            }
        };
        Configuration configuration = context.getResources().getConfiguration();
        this.orientation = deviceUtil.isDisplayLandScape(context) ? 2 : 1;
        this.isInFreeform = Boolean.valueOf(deviceUtil.isInFreeform(context));
        this.isLargeScreen = Boolean.valueOf(deviceUtil.isLargeDisplay(context));
        Intrinsics.checkNotNull(configuration);
        this.lastConfigSnapshot = buildConfigSnapshot(configuration);
        Log.i("SettingsTemplateView", "SettingsTemplateView: init:density=" + configuration.densityDpi + ", screenWidthDp=" + configuration.screenWidthDp + ",screenHeightDp=" + configuration.screenHeightDp + ",orientation = " + this.orientation);
        ViewGroup.inflate(context, R.layout.kg_settings_view_template, this);
        updateResourceWhenConfigurationChanged();
        HorizontalSpringTouchForSettingsManager horizontalSpringTouchForSettingsManager = new HorizontalSpringTouchForSettingsManager(context, getSpringLayout());
        this.springTouchForSettingsManager = horizontalSpringTouchForSettingsManager;
        getSpringLayout().setSpringTouchManager(horizontalSpringTouchForSettingsManager);
        ViewUtilKt.fakeHide(getSpringLayout());
        DampViewPager2 viewPager2 = getViewPager2();
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        View childAt = viewPager2.getChildAt(0);
        RecyclerView recyclerView = childAt instanceof RecyclerView ? (RecyclerView) childAt : null;
        if (recyclerView != null) {
            recyclerView.setClipChildren(false);
            recyclerView.setClipToPadding(false);
        }
        viewPager2.setOrientation(0);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$1$2
            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i2) {
                super.onPageScrollStateChanged(i2);
                this.this$0.mScrolled = i2 != 0;
            }

            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i2) {
                if (this.this$0.selectedPosition != i2) {
                    this.this$0.selectedPosition = i2;
                    this.this$0.switchApplyBtn(i2);
                }
                this.this$0.selectedPosition = i2;
                if (this.this$0.selectedPosition == 0) {
                    SettingsTrackHelper.INSTANCE.trackNegativeCardExpose(context);
                }
            }
        });
        setPagerTransform();
        Folme.useAt(getApplyBtn()).touch().handleTouchOf(getApplyBtn(), new AnimConfig[0]);
        if (editorVersion >= 49) {
            TextView newMatchTextView = getNewMatchTextView();
            newMatchTextView.setTextColor(context.getColor(R.color.kg_settings_template_apply_text_normal_color));
            newMatchTextView.setBackgroundResource(R.drawable.kg_settings_background_template_apply_button);
            ViewExtKt.clickNoRepeat$default(newMatchTextView, 0L, new Function1() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return SettingsTemplateView.$r8$lambda$ANMXoLDBO4j72DLA0WgHj3CJIOU(this.f$0, context, (View) obj);
                }
            }, 1, null);
        } else {
            TextView newMatchTextView2 = getNewMatchTextView();
            newMatchTextView2.setTextColor(context.getColor(R.color.kg_settings_template_apply_text_disable_color));
            newMatchTextView2.setBackgroundResource(R.drawable.kg_settings_background_template_using_button);
        }
        getApplyBtn().setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsTemplateView._init_$lambda$20(this.f$0, context, view);
            }
        });
    }

    public /* synthetic */ SettingsTemplateView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void _init_$lambda$20(SettingsTemplateView settingsTemplateView, Context context, View view) {
        TemplateApplyController templateApplyController;
        if (settingsTemplateView.mScrolled) {
            Log.w("SettingsTemplateView", "onApply failed: mScrolled == true.");
            return;
        }
        if (!settingsTemplateView.springTouchForSettingsManager.shouldSpringBack() && settingsTemplateView.springTouchForSettingsManager.getEnableTouch()) {
            settingsTemplateView.springTouchForSettingsManager.setEnableTouch(false);
            SettingsTemplateApplyingLayer settingsTemplateApplyingLayer = new SettingsTemplateApplyingLayer(context, 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0);
            settingsTemplateApplyingLayer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            settingsTemplateApplyingLayer.setClipChildren(false);
            settingsTemplateApplyingLayer.initLayout(settingsTemplateView.selectedPosition, settingsTemplateView.getViewPager2(), settingsTemplateView.isUseBlur);
            settingsTemplateApplyingLayer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda20
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SettingsTemplateView.$r8$lambda$7LUkO1oauh_b7GDsMJ_WXuJN1xU(view2);
                }
            });
            settingsTemplateView.templateApplyingLayer = settingsTemplateApplyingLayer;
            settingsTemplateView.getSettingView().addView(settingsTemplateView.templateApplyingLayer);
            settingsTemplateView.templateStatus = 1;
            settingsTemplateView.isApplyingTemplateInternal = true;
            settingsTemplateView.isFromMatchInternalApply = true;
            settingsTemplateView.isApplyCurrentTemplate = false;
            settingsTemplateView.getApplyBtn().setEnabled(false);
            ViewUtilKt.show(settingsTemplateView.getProgressBar());
            TextView applyBtnText = settingsTemplateView.getApplyBtnText();
            applyBtnText.setText(context.getString(R.string.kg_settings_apply_loading));
            applyBtnText.setTextColor(context.getColor(R.color.kg_settings_template_apply_text_disable_color));
            SettingsTrackHelper settingsTrackHelper = SettingsTrackHelper.INSTANCE;
            TemplateHistoryConfig history = ((TemplateItemBean) settingsTemplateView.templateListResult.get(settingsTemplateView.selectedPosition)).getHistory();
            SettingsTrackHelper.trackUsedTemplate$default(settingsTrackHelper, context, null, "应用", history != null ? history.getTemplateId() : null, 2, null);
            TemplateApiImpl.Companion companion = TemplateApiImpl.Companion;
            companion.setApplyClickTimestamp(System.currentTimeMillis());
            Log.i("SettingsTemplateView", "applyBtn onClick: setting clickTs=" + companion.getApplyClickTimestamp());
            TemplateApplyController templateApplyController2 = settingsTemplateView.mTemplateApplyController;
            Integer numValueOf = templateApplyController2 != null ? Integer.valueOf(TemplateApplyController.applyTemplate$default(templateApplyController2, false, 1, null)) : null;
            if ((numValueOf != null && numValueOf.intValue() == 0) || (templateApplyController = settingsTemplateView.mTemplateApplyController) == null || templateApplyController.hasPendingApplyTemplateTask() || numValueOf == null || numValueOf.intValue() != 3) {
                return;
            }
            settingsTemplateView.updateStatusWhenApplyFailed();
        }
    }

    public static final TextView applyBtnText_delegate$lambda$8(SettingsTemplateView settingsTemplateView) {
        return (TextView) settingsTemplateView.findViewById(R.id.apply_button_text);
    }

    public static final View applyBtn_delegate$lambda$6(SettingsTemplateView settingsTemplateView) {
        return settingsTemplateView.findViewById(R.id.apply_button);
    }

    public static final View deskTopLoadingView_delegate$lambda$5(SettingsTemplateView settingsTemplateView) {
        return settingsTemplateView.findViewById(R.id.template_desktop_loading);
    }

    private final View getApplyBtn() {
        Object value = this.applyBtn$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (View) value;
    }

    private final TextView getApplyBtnText() {
        Object value = this.applyBtnText$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (TextView) value;
    }

    private final View getDeskTopLoadingView() {
        Object value = this.deskTopLoadingView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (View) value;
    }

    private final View getKeyGuardLoadingView() {
        Object value = this.keyGuardLoadingView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (View) value;
    }

    private final View getLoadingView() {
        Object value = this.loadingView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (View) value;
    }

    private final TextView getNewMatchTextView() {
        Object value = this.newMatchTextView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (TextView) value;
    }

    private final ProgressBar getProgressBar() {
        Object value = this.progressBar$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (ProgressBar) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConstraintLayout getSettingView() {
        Object value = this.settingView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (ConstraintLayout) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SpringLayout getSpringLayout() {
        Object value = this.springLayout$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (SpringLayout) value;
    }

    private final TextView getUsingTextView() {
        Object value = this.usingTextView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (TextView) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DampViewPager2 getViewPager2() {
        Object value = this.viewPager2$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (DampViewPager2) value;
    }

    public static final void initModelAndController$lambda$23$lambda$22(SettingsTemplateView settingsTemplateView, Pair it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Log.i("SettingsTemplateView", "refreshData:onApplyMyTemplateComplete: isSuccessful=" + it.getSecond());
        settingsTemplateView.isApplyCurrentTemplate = true;
        settingsTemplateView.refreshMyTemplate(settingsTemplateView.selectedPosition);
    }

    public static final View keyGuardLoadingView_delegate$lambda$4(SettingsTemplateView settingsTemplateView) {
        return settingsTemplateView.findViewById(R.id.template_keyguard_loading);
    }

    public static final Unit loadDataCallback$lambda$11(SettingsTemplateView settingsTemplateView) {
        if (settingsTemplateView.templateStatus == 2) {
            settingsTemplateView.refreshButtonWhenApply();
            restoreTemplate$default(settingsTemplateView, null, 1, null);
            Log.i("SettingsTemplateView", "restoreTemplate if currentTemplate has not drawn");
        }
        return Unit.INSTANCE;
    }

    public static final View loadingView_delegate$lambda$3(SettingsTemplateView settingsTemplateView) {
        return settingsTemplateView.findViewById(R.id.template_container_loading);
    }

    public static final TextView newMatchTextView_delegate$lambda$10(SettingsTemplateView settingsTemplateView) {
        return (TextView) settingsTemplateView.findViewById(R.id.new_match_text);
    }

    public static final Unit onCurrentTemplateScreenshot$lambda$55(SettingsTemplateView settingsTemplateView) {
        Log.w("SettingsTemplateView", "screenshot success:onCurrentTemplateScreenshotsSaveComplete");
        TemplateApplyController templateApplyController = settingsTemplateView.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(false);
        }
        TemplateApplyController templateApplyController2 = settingsTemplateView.mTemplateApplyController;
        if (templateApplyController2 != null) {
            TemplateApplyController.onCurrentTemplateScreenshotsSaveComplete$default(templateApplyController2, false, 1, null);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onCurrentTemplateScreenshot$lambda$56(SettingsTemplateView settingsTemplateView, Throwable it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Log.e("SettingsTemplateView", "screenshot failed:onCurrentTemplateScreenshotsSaveComplete");
        TemplateApplyController templateApplyController = settingsTemplateView.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(false);
        }
        return Unit.INSTANCE;
    }

    public static final void onPageDeleted$lambda$51(DialogInterface dialogInterface, int i) {
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    public static final void onPageDeleted$lambda$53(SettingsTemplateView settingsTemplateView, Ref.BooleanRef booleanRef, int i, TemplateItemBean templateItemBean, DialogInterface dialogInterface) {
        settingsTemplateView.deleteTemplateDialog = null;
        if (booleanRef.element) {
            settingsTemplateView.deletePage(i, templateItemBean);
        } else {
            settingsTemplateView.springTouchForSettingsManager.setEnableTouch(true);
        }
    }

    public static final void onVisibilityChanged$lambda$31(SettingsTemplateView settingsTemplateView) {
        settingsTemplateView.getViewPager2().requestTransform();
    }

    public static final ProgressBar progressBar_delegate$lambda$7(SettingsTemplateView settingsTemplateView) {
        return (ProgressBar) settingsTemplateView.findViewById(R.id.progress_bar);
    }

    public static /* synthetic */ void refreshMyTemplate$default(SettingsTemplateView settingsTemplateView, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        settingsTemplateView.refreshMyTemplate(i);
    }

    public static /* synthetic */ void restoreTemplate$default(SettingsTemplateView settingsTemplateView, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        settingsTemplateView.restoreTemplate(function0);
    }

    public static final Unit restoreTemplate$lambda$45(Function0 function0) {
        if (function0 != null) {
            function0.invoke();
        }
        return Unit.INSTANCE;
    }

    public static final Unit restoreTemplate$lambda$46(SettingsTemplateView settingsTemplateView) {
        try {
            settingsTemplateView.getSettingView().removeView(settingsTemplateView.templateApplyingLayer);
        } catch (Exception e) {
            Log.e("SettingsTemplateView", "onPageDeleted failed: " + e);
        }
        return Unit.INSTANCE;
    }

    public static final void restoreTemplate$lambda$47(SettingsTemplateView settingsTemplateView) {
        settingsTemplateView.springTouchForSettingsManager.setEnableTouch(true);
    }

    public static final ConstraintLayout settingView_delegate$lambda$0(SettingsTemplateView settingsTemplateView) {
        return (ConstraintLayout) settingsTemplateView.findViewById(R.id.settings_view);
    }

    public static final void showApplyFailedDialog$lambda$49(DialogInterface dialogInterface, int i) {
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    public static final SpringLayout springLayout_delegate$lambda$1(SettingsTemplateView settingsTemplateView) {
        return (SpringLayout) settingsTemplateView.findViewById(R.id.spring_layout);
    }

    public static final void startDeleteTemplateAnim$lambda$27(final SettingsTemplateView settingsTemplateView) {
        SettingsTemplateDeleteLayer settingsTemplateDeleteLayer = settingsTemplateView.templateDeleteLayer;
        if (settingsTemplateDeleteLayer != null) {
            settingsTemplateDeleteLayer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda29
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsTemplateView.startDeleteTemplateAnim$lambda$27$lambda$25(view);
                }
            });
        }
        SettingsTemplateDeleteLayer settingsTemplateDeleteLayer2 = settingsTemplateView.templateDeleteLayer;
        if (settingsTemplateDeleteLayer2 != null) {
            settingsTemplateDeleteLayer2.startDeleteTransformAnim(settingsTemplateView.selectedPosition, new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda30
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return SettingsTemplateView.startDeleteTemplateAnim$lambda$27$lambda$26(this.f$0);
                }
            });
        }
    }

    public static final void startDeleteTemplateAnim$lambda$27$lambda$25(View view) {
    }

    public static final Unit startDeleteTemplateAnim$lambda$27$lambda$26(SettingsTemplateView settingsTemplateView) {
        try {
            settingsTemplateView.getSettingView().removeView(settingsTemplateView.templateDeleteLayer);
            settingsTemplateView.isFromMatchInternalDelete = false;
            settingsTemplateView.springTouchForSettingsManager.setEnableTouch(true);
        } catch (Exception e) {
            Log.e("SettingsTemplateView", "onPageDeleted failed: " + e);
        }
        return Unit.INSTANCE;
    }

    public static final Unit tryRestoreIfAllReady$lambda$43(SettingsTemplateView settingsTemplateView) {
        settingsTemplateView.takeScreenshotAfterRestore();
        return Unit.INSTANCE;
    }

    public static final TextView usingTextView_delegate$lambda$9(SettingsTemplateView settingsTemplateView) {
        return (TextView) settingsTemplateView.findViewById(R.id.using_text);
    }

    public static final DampViewPager2 viewPager2_delegate$lambda$2(SettingsTemplateView settingsTemplateView) {
        return (DampViewPager2) settingsTemplateView.findViewById(R.id.view_pager);
    }

    public final SettingsConfigSnapshot buildConfigSnapshot(Configuration configuration) {
        int i = configuration.densityDpi;
        int i2 = configuration.screenWidthDp;
        int i3 = configuration.screenHeightDp;
        int i4 = configuration.orientation;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int i5 = deviceUtil.isDisplayLandScape(context) ? 2 : 1;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        boolean zIsInFreeform = deviceUtil.isInFreeform(context2);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        return new SettingsConfigSnapshot(i, i2, i3, i4, i5, zIsInFreeform, deviceUtil.isLargeDisplay(context3));
    }

    public final void checkGalleryStateChanged() {
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(null), 3, null);
    }

    public final void deletePage(int i, TemplateItemBean templateItemBean) {
        if (this.isFromMatchInternalDelete) {
            return;
        }
        this.isFromMatchInternalDelete = true;
        Log.i("SettingsTemplateView", "deleteHistory, itemBean=" + templateItemBean);
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new C01321(templateItemBean, i, null), 3, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getCurrentSelect() {
        return CombineEditListener.DefaultImpls.getCurrentSelect(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getDialogOffset() {
        return CombineEditListener.DefaultImpls.getDialogOffset(this);
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    @Nullable
    public TemplateItemBean getSelectedItem() {
        int i = this.selectedPosition;
        if (i < 0 || i >= this.templateListResult.size()) {
            return null;
        }
        return (TemplateItemBean) this.templateListResult.get(this.selectedPosition);
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    @Nullable
    public RecyclerView.ViewHolder getSelectedViewHolder() {
        View childAt = getViewPager2().getChildAt(0);
        RecyclerView recyclerView = childAt instanceof RecyclerView ? (RecyclerView) childAt : null;
        if (recyclerView != null) {
            return recyclerView.findViewHolderForAdapterPosition(this.selectedPosition);
        }
        return null;
    }

    public final void handleConfigurationChanged(Configuration configuration, String str) {
        SettingsConfigSnapshot settingsConfigSnapshotBuildConfigSnapshot = buildConfigSnapshot(configuration);
        this.lastConfigSnapshot = settingsConfigSnapshotBuildConfigSnapshot;
        Log.i("SettingsTemplateView", "handleConfigurationChanged from=" + str + ", snapshot=" + settingsConfigSnapshotBuildConfigSnapshot + ", visibility=" + getVisibility() + ", isAttached=" + isAttachedToWindow());
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int i = deviceUtil.isDisplayLandScape(context) ? 2 : 1;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        boolean zIsInFreeform = deviceUtil.isInFreeform(context2);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        boolean zIsLargeDisplay = deviceUtil.isLargeDisplay(context3);
        if (i != this.orientation || !Intrinsics.areEqual(Boolean.valueOf(zIsInFreeform), this.isInFreeform) || !Intrinsics.areEqual(Boolean.valueOf(zIsLargeDisplay), this.isLargeScreen)) {
            this.orientation = i;
            this.isInFreeform = Boolean.valueOf(zIsInFreeform);
            this.isLargeScreen = Boolean.valueOf(zIsLargeDisplay);
            this.pendingExternalRefresh = false;
            ViewUtilKt.show(getLoadingView());
            AlertDialog alertDialog = this.deleteTemplateDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.springTouchForSettingsManager.setEnableTouch(true);
            }
            Log.i("SettingsTemplateView", "orientation changed and need refresh,isApplyingTemplateInternal=" + this.isApplyingTemplateInternal + ", templateStatus=" + this.templateStatus);
            removeHideNonNegativeListener();
            getViewPager2().setAdapter(null);
            TemplateApplyController templateApplyController = this.mTemplateApplyController;
            if (templateApplyController != null) {
                templateApplyController.processScreenConfigurationChanged();
            }
            SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = this.dataModel;
            if (settingsTemplateDataModelProvider != null) {
                settingsTemplateDataModelProvider.processScreenConfigurationChanged();
            }
            if (this.templateStatus != 1) {
                refreshMyTemplate$default(this, 0, 1, null);
            }
            SettingsTemplateApplyingLayer settingsTemplateApplyingLayer = this.templateApplyingLayer;
            if (settingsTemplateApplyingLayer != null) {
                getSettingView().removeView(settingsTemplateApplyingLayer);
            }
            SettingsTemplateDeleteLayer settingsTemplateDeleteLayer = this.templateDeleteLayer;
            if (settingsTemplateDeleteLayer != null) {
                getSettingView().removeView(settingsTemplateDeleteLayer);
            }
        }
        updateResourceWhenConfigurationChanged();
        this.mScrolled = false;
    }

    public final void hideNonNegativeItems() {
        View childAt = getViewPager2().getChildAt(0);
        final RecyclerView recyclerView = childAt instanceof RecyclerView ? (RecyclerView) childAt : null;
        if (recyclerView == null) {
            return;
        }
        removeHideNonNegativeListener();
        RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$hideNonNegativeItems$listener$1
            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewAttachedToWindow(View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                if (recyclerView.getChildAdapterPosition(view) > 0) {
                    view.setAlpha(0.0f);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewDetachedFromWindow(View view) {
                Intrinsics.checkNotNullParameter(view, "view");
            }
        };
        this.hideNonNegativeListener = onChildAttachStateChangeListener;
        recyclerView.addOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
    }

    public final void initModelAndController() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.dataModel = new SettingsTemplateDataModelProvider(context);
        Context context2 = getContext();
        Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type androidx.fragment.app.FragmentActivity");
        TemplateApplyController templateApplyController = new TemplateApplyController((FragmentActivity) context2, this);
        templateApplyController.setApplyCallback(new ApplyCallback() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$initModelAndController$1$1
            @Override // com.miui.keyguard.editor.homepage.util.ApplyCallback
            public void onApplyCanceled() {
                Log.w("SettingsTemplateView", "onApplyCanceled: setting");
                if (this.this$0.isApplyCurrentTemplate) {
                    return;
                }
                this.this$0.refreshButtonWhenApply();
                SettingsTemplateView.restoreTemplate$default(this.this$0, null, 1, null);
            }

            @Override // com.miui.keyguard.editor.homepage.util.ApplyCallback
            public void onApplyComplete(boolean z, Bitmap bitmap) {
                Log.i("SettingsTemplateView", "refreshData:onApplyComplete: isSuccessful=" + z);
                if (z) {
                    AlertDialog alertDialog = this.this$0.applyFailedDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    SettingsTemplateView settingsTemplateView = this.this$0;
                    settingsTemplateView.refreshMyTemplate(settingsTemplateView.selectedPosition);
                } else {
                    this.this$0.updateStatusWhenApplyFailed();
                }
                TemplateApplyController templateApplyController2 = this.this$0.mTemplateApplyController;
                if (templateApplyController2 != null) {
                    templateApplyController2.setApplying(false, true);
                }
            }
        });
        templateApplyController.setApplyMyTemplateCallback(new Consumer() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SettingsTemplateView.initModelAndController$lambda$23$lambda$22(this.f$0, (Pair) obj);
            }
        });
        this.mTemplateApplyController = templateApplyController;
    }

    public final void maybeHandleLatestConfiguration(String str) {
        Configuration configuration = getResources().getConfiguration();
        Intrinsics.checkNotNull(configuration);
        SettingsConfigSnapshot settingsConfigSnapshotBuildConfigSnapshot = buildConfigSnapshot(configuration);
        SettingsConfigSnapshot settingsConfigSnapshot = this.lastConfigSnapshot;
        if (Intrinsics.areEqual(settingsConfigSnapshot, settingsConfigSnapshotBuildConfigSnapshot)) {
            return;
        }
        Log.i("SettingsTemplateView", "missed configuration changed from=" + str + ", old=" + settingsConfigSnapshot + ", new=" + settingsConfigSnapshotBuildConfigSnapshot);
        handleConfigurationChanged(configuration, str);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onAddTemplate() {
        Log.i("SettingsTemplateView", "onAddTemplate selectedPosition=" + this.selectedPosition);
        if (this.selectedPosition != 0) {
            return;
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        ContextUtilKt.navigatorToThemePicker(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i("SettingsTemplateView", "onAttachedToWindow");
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        wideColorGamutManager.updateSupportWideColor(context);
        registerGalleryStateObserver();
        maybeHandleLatestConfiguration("attached");
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        handleConfigurationChanged(newConfig, am.ai);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onCurrentTemplateScreenshot(BaseTemplateView lockTemplateView, BaseTemplateView baseTemplateView, CommonConfig templateConfig) {
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Log.i("SettingsTemplateView", "onCurrentTemplateScreenshot -> setting shouldCaptureCurrentTemplate  = " + this.shouldCaptureCurrentTemplate);
        if (this.shouldCaptureCurrentTemplate) {
            this.shouldCaptureCurrentTemplate = false;
            TemplateApplyController templateApplyController = this.mTemplateApplyController;
            if (templateApplyController != null) {
                templateApplyController.setCurrentTemplateScreenshotsSaving(true);
            }
            DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            deviceScreenshotHelper.screenshot(context, getSettingView(), lockTemplateView, baseTemplateView, templateConfig, (32 & 32) != 0 ? "wallpaper_for_screenshot" : null, new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return SettingsTemplateView.onCurrentTemplateScreenshot$lambda$55(this.f$0);
                }
            }, (32 & 128) != 0 ? null : new Function1() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return SettingsTemplateView.onCurrentTemplateScreenshot$lambda$56(this.f$0, (Throwable) obj);
                }
            });
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Log.i("SettingsTemplateView", "onDetachedFromWindow");
        super.onDetachedFromWindow();
        unregisterGalleryStateObserver();
        removeHideNonNegativeListener();
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        wideColorGamutManager.unregisterObservers(context);
        wideColorGamutManager.removeColorModeChangeListener(null);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheet() {
        CombineEditListener.DefaultImpls.onDismissBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheetForScreenChange(Configuration configuration) {
        CombineEditListener.DefaultImpls.onDismissBottomSheetForScreenChange(this, configuration);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissKeyboard() {
        CombineEditListener.DefaultImpls.onDismissKeyboard(this);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        initModelAndController();
        SettingsTemplateAdapter settingsTemplateAdapter = new SettingsTemplateAdapter(this.dataModel);
        settingsTemplateAdapter.setCallback(this);
        settingsTemplateAdapter.registerAdapterDataObserver(new SettingsTemplateView$onFinishInflate$1$1(this));
        this.templateAdapter = settingsTemplateAdapter;
        DampViewPager2 viewPager2 = getViewPager2();
        SettingsTemplateAdapter settingsTemplateAdapter2 = this.templateAdapter;
        if (settingsTemplateAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateAdapter");
            settingsTemplateAdapter2 = null;
        }
        viewPager2.setAdapter(settingsTemplateAdapter2);
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass2(null), 3, null);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHistoryTemplateReady() {
        if (this.isHistoryTemplateReady) {
            return;
        }
        Log.i("SettingsTemplateView", "onHistoryTemplateReady");
        this.isHistoryTemplateReady = true;
        tryRestoreIfAllReady();
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHomeWallpaperDarkResultReady() {
        if (this.isHomeWallpaperDarkResultReady) {
            return;
        }
        Log.i("SettingsTemplateView", "onHomeWallpaperDarkResultReady");
        this.isHomeWallpaperDarkResultReady = true;
        tryRestoreIfAllReady();
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyHomeTemplateCreated(BaseTemplateView baseTemplateView) {
        Log.i("SettingsTemplateView", "onMyHomeTemplateCreated");
        this.homeTemplateView = baseTemplateView;
        this.isHomeWallpaperDarkResultReady = false;
        if (baseTemplateView != null) {
            baseTemplateView.setCombineAnimListener(this);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyLockTemplateCreated(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        Log.i("SettingsTemplateView", "onMyLockTemplateCreated");
        this.templateStatus = 3;
        this.lockTemplateView = baseTemplateView;
        this.screenshotConfig = commonConfig;
        if (baseTemplateView != null) {
            baseTemplateView.setCombineAnimListener(this);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageDeleteCompleted(int i, int i2, TemplateItemBean templateItemBean) {
        CrossViewPagerCallback.DefaultImpls.onPageDeleteCompleted(this, i, i2, templateItemBean);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public boolean onPageDeleted(final int i, int i2, final TemplateItemBean templateItemBean) {
        if (!this.springTouchForSettingsManager.getEnableTouch()) {
            return false;
        }
        this.springTouchForSettingsManager.setEnableTouch(false);
        AlertDialog alertDialog = this.deleteTemplateDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        AlertDialog alertDialogCreate = new AlertDialog.Builder(getContext(), R.style.AlertDialog_Theme_DayNight_Danger).setTitle(R.string.kg_settings_delete_this_collocation).setMessage(R.string.kg_settings_delete_this_collocation_desc).setNegativeButton(R.string.kg_settings_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda25
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                SettingsTemplateView.onPageDeleted$lambda$51(dialogInterface, i3);
            }
        }).setPositiveButton(R.string.kg_settings_delete, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda26
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                booleanRef.element = true;
            }
        }).setCancelable(true).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda27
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SettingsTemplateView.onPageDeleted$lambda$53(this.f$0, booleanRef, i, templateItemBean, dialogInterface);
            }
        }).create();
        this.deleteTemplateDialog = alertDialogCreate;
        if (alertDialogCreate != null) {
            alertDialogCreate.show();
        }
        return true;
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageEnterDeleteMode(int i, int i2, View view, boolean z) {
        CrossViewPagerCallback.DefaultImpls.onPageEnterDeleteMode(this, i, i2, view, z);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageScrollStateChanged(int i, int i2, int i3, int i4) {
        CrossViewPagerCallback.DefaultImpls.onPageScrollStateChanged(this, i, i2, i3, i4);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageSelected(int i, int i2, int i3, int i4) {
        CrossViewPagerCallback.DefaultImpls.onPageSelected(this, i, i2, i3, i4);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageTransformed(int i, BaseViewHolder baseViewHolder, View view, float f, float f2, int i2) {
        CrossViewPagerCallback.DefaultImpls.onPageTransformed(this, i, baseViewHolder, view, f, f2, i2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onPreviewChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onPreviewChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onScrolledChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onScrolledChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onShowBottomSheet() {
        CombineEditListener.DefaultImpls.onShowBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onTemplateCustomized(int i, int i2, TemplateItemBean templateItemBean, int i3) {
        String configUri;
        String resourceType;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        String resourceType2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        if (this.mScrolled) {
            Log.w("SettingsTemplateView", "onTemplateCustomized ignored: mScrolled = true");
            return;
        }
        Integer numValueOf = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
        String str = "";
        if (numValueOf == null || numValueOf.intValue() != 1) {
            if (numValueOf != null && numValueOf.intValue() == 2) {
                TemplateHistoryConfig history = templateItemBean.getHistory();
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                if (history != null && (configUri = history.getConfigUri()) != null) {
                    str = configUri;
                }
                ContextUtilKt.navigatorToCommonEditorActivity(context, i3, str, history != null ? history.getId() : 0L);
                return;
            }
            return;
        }
        CommonConfig current = templateItemBean.getCurrent();
        if (current == null || (lockscreenInfo = current.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) == null || (resourceType = wallpaperInfo2.getResourceType()) == null) {
            resourceType = "";
        }
        CommonConfig current2 = templateItemBean.getCurrent();
        if (current2 != null && (homeInfo = current2.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null && (resourceType2 = wallpaperInfo.getResourceType()) != null) {
            str = resourceType2;
        }
        if (Wallpaper.Companion.unSupportToEdit(resourceType, str)) {
            return;
        }
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        ContextUtilKt.navigatorToCommonEditorActivity(context2, i3, "@MINE", -1L);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onTemplateReady(int i) {
        int iIncrementAndGet = this.templateReadyCount.incrementAndGet();
        Log.i("SettingsTemplateView", "onTemplateReady id =" + i + ", readyCount=" + iIncrementAndGet);
        if (iIncrementAndGet < 4 || iIncrementAndGet % 4 != 0) {
            return;
        }
        this.isCurrentTemplateReady = true;
        tryRestoreIfAllReady();
    }

    @Override // android.view.View
    public void onVisibilityChanged(View changedView, int i) {
        Intrinsics.checkNotNullParameter(changedView, "changedView");
        super.onVisibilityChanged(changedView, i);
        if (Intrinsics.areEqual(changedView, this)) {
            Log.i("SettingsTemplateView", "onVisibilityChanged: visibility=" + i + ", isAttached=" + isAttachedToWindow());
            if (i == 0 && isAttachedToWindow()) {
                maybeHandleLatestConfiguration("visible");
            }
        }
        HomeColumnDataSource.INSTANCE.clearHomeColumns();
        if (i != 0) {
            this.isPageVisible = false;
            this.isFromMatchInternalApply = false;
            AlertDialog alertDialog = this.applyFailedDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            AlertDialog alertDialog2 = this.deleteTemplateDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
                this.springTouchForSettingsManager.setEnableTouch(true);
                return;
            }
            return;
        }
        this.isPageVisible = true;
        SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = this.dataModel;
        if (settingsTemplateDataModelProvider != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            settingsTemplateDataModelProvider.refreshMiuiWallpaperInfo(context);
        }
        this.handler.post(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SettingsTemplateView.onVisibilityChanged$lambda$31(this.f$0);
            }
        });
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), null, null, new C01332(null), 3, null);
        checkGalleryStateChanged();
        if (this.pendingExternalRefresh) {
            this.pendingExternalRefresh = false;
            Log.i("SettingsTemplateView", "execute pending external refresh after page visible");
            refreshMyTemplate$default(this, 0, 1, null);
        }
    }

    public final void refreshButtonWhenApply() {
        Log.i("SettingsTemplateView", "refreshButtonWhenApply");
        getApplyBtn().setEnabled(true);
        ViewUtilKt.hide(getProgressBar());
        TextView applyBtnText = getApplyBtnText();
        applyBtnText.setText(applyBtnText.getContext().getString(R.string.kg_settings_apply));
        applyBtnText.setTextColor(applyBtnText.getContext().getColor(R.color.kg_settings_template_apply_text_normal_color));
        int i = this.selectedPosition;
        if (i <= 0) {
            Log.w("SettingsTemplateView", "refreshButtonWhenApply: selectedPosition=" + i + " is invalid, using usingPosition=" + this.usingPosition + " instead");
            i = this.usingPosition;
        }
        if (i >= this.templateListResult.size() || i < 0) {
            Log.w("SettingsTemplateView", "refreshButtonWhenApply: currentPosition=" + i + " is out of bounds, size=" + this.templateListResult.size());
            return;
        }
        Log.i("SettingsTemplateView", "refreshButtonWhenApply: itemtype=" + ((TemplateItemBean) this.templateListResult.get(i)).getItemType() + ", currentPosition=" + i);
        int itemType = ((TemplateItemBean) this.templateListResult.get(i)).getItemType();
        if (itemType == -1) {
            ViewUtilKt.hide(getUsingTextView());
            ViewUtilKt.hide(getApplyBtn());
            ViewUtilKt.show(getNewMatchTextView());
            return;
        }
        if (itemType == 4) {
            ViewUtilKt.show(getUsingTextView());
            ViewUtilKt.hide(getApplyBtn());
            ViewUtilKt.hide(getNewMatchTextView());
            return;
        }
        if (itemType != 1) {
            if (itemType != 2) {
                return;
            }
            ViewUtilKt.hide(getUsingTextView());
            ViewUtilKt.show(getApplyBtn());
            ViewUtilKt.hide(getNewMatchTextView());
            return;
        }
        if (this.usingPosition == i) {
            ViewUtilKt.show(getUsingTextView());
            ViewUtilKt.hide(getApplyBtn());
            ViewUtilKt.hide(getNewMatchTextView());
        } else {
            ViewUtilKt.hide(getUsingTextView());
            ViewUtilKt.show(getApplyBtn());
            ViewUtilKt.hide(getNewMatchTextView());
        }
    }

    public final void refreshMyTemplate(int i) {
        if (i == -1) {
            ViewUtilKt.show(getLoadingView());
            ViewUtilKt.fakeHide(getSpringLayout());
        }
        SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = this.dataModel;
        if (settingsTemplateDataModelProvider != null) {
            settingsTemplateDataModelProvider.refreshData(i);
        }
    }

    public final void registerGalleryStateObserver() {
        if (this.galleryStateObserver != null) {
            return;
        }
        this.galleryStateObserver = new ContentObserver(this.handler) { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView.registerGalleryStateObserver.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                Log.i("SettingsTemplateView", "lock_screen_magazine_status changed, refresh loopFlag");
                SettingsTemplateView.this.checkGalleryStateChanged();
            }
        };
        try {
            ContentResolver contentResolver = getContext().getContentResolver();
            Uri uriFor = Settings.Secure.getUriFor("lock_screen_magazine_status");
            ContentObserver contentObserver = this.galleryStateObserver;
            Intrinsics.checkNotNull(contentObserver);
            contentResolver.registerContentObserver(uriFor, false, contentObserver);
        } catch (Exception e) {
            Log.w("SettingsTemplateView", "registerGalleryStateObserver failed", e);
        }
    }

    public final void removeHideNonNegativeListener() {
        RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener;
        View childAt = getViewPager2().getChildAt(0);
        RecyclerView recyclerView = childAt instanceof RecyclerView ? (RecyclerView) childAt : null;
        if (recyclerView == null || (onChildAttachStateChangeListener = this.hideNonNegativeListener) == null) {
            return;
        }
        recyclerView.removeOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
        this.hideNonNegativeListener = null;
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt2 = recyclerView.getChildAt(i);
            if (childAt2 != null && childAt2.getAlpha() < 1.0f) {
                childAt2.setAlpha(1.0f);
            }
        }
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    public Pair requireScreenshotPreview() {
        return null;
    }

    public final void restoreTemplate(final Function0 function0) {
        Log.i("SettingsTemplateView", "restoreTemplate");
        removeHideNonNegativeListener();
        if (this.isFromMatchInternalDelete) {
            ViewUtilKt.hide(getLoadingView());
            ViewUtilKt.show(getSpringLayout());
            if (function0 != null) {
                function0.invoke();
                return;
            }
            return;
        }
        ViewUtilKt.hideByAlphaAnim$default(getLoadingView(), null, 1, null);
        if (this.isApplyingTemplateInternal) {
            ViewUtilKt.show(getSpringLayout());
            if (function0 != null) {
                function0.invoke();
            }
        } else {
            ViewUtilKt.showByAlphaAnim(getSpringLayout(), new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda22
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return SettingsTemplateView.restoreTemplate$lambda$45(function0);
                }
            });
        }
        SettingsTemplateApplyingLayer settingsTemplateApplyingLayer = this.templateApplyingLayer;
        if (settingsTemplateApplyingLayer != null) {
            settingsTemplateApplyingLayer.hideLoadingByAnim(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda23
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return SettingsTemplateView.restoreTemplate$lambda$46(this.f$0);
                }
            });
        }
        this.isApplyingTemplateInternal = false;
        this.handler.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                SettingsTemplateView.restoreTemplate$lambda$47(this.f$0);
            }
        }, 200L);
        this.templateStatus = 4;
    }

    public final void setPagerTransform() {
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        compositePageTransformer.addTransformer(new SettingsPagerMarginTransformer(context));
        compositePageTransformer.addTransformer(new SettingsHorizontalScaleTransformer());
        compositePageTransformer.addTransformer(new SettingsPagerDeleteAnimTransformer());
        getViewPager2().setPageTransformer(compositePageTransformer);
    }

    public final void showApplyFailedDialog() {
        AlertDialog alertDialog = this.applyFailedDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(getContext()).setMessage(R.string.kg_settings_template_apply_dialog_failed).setPositiveButton(R.string.kg_settings_template_apply_dialog_btn_known, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda28
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsTemplateView.showApplyFailedDialog$lambda$49(dialogInterface, i);
            }
        }).setCancelable(true).create();
        this.applyFailedDialog = alertDialogCreate;
        if (alertDialogCreate != null) {
            alertDialogCreate.show();
        }
    }

    public final void startDeleteTemplateAnim() {
        this.handler.post(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                SettingsTemplateView.startDeleteTemplateAnim$lambda$27(this.f$0);
            }
        });
    }

    public final void switchApplyBtn(int i) {
        int itemType = ((TemplateItemBean) this.templateListResult.get(i)).getItemType();
        if (itemType == -1) {
            ViewUtilKt.switchApplyBtnWithAnim(getUsingTextView(), false);
            ViewUtilKt.switchApplyBtnWithAnim(getApplyBtn(), false);
            ViewUtilKt.switchApplyBtnWithAnim(getNewMatchTextView(), true);
            return;
        }
        if (itemType == 4) {
            ViewUtilKt.switchApplyBtnWithAnim(getUsingTextView(), true);
            ViewUtilKt.switchApplyBtnWithAnim(getApplyBtn(), false);
            ViewUtilKt.switchApplyBtnWithAnim(getNewMatchTextView(), false);
            return;
        }
        if (itemType != 1) {
            if (itemType != 2) {
                return;
            }
            ViewUtilKt.switchApplyBtnWithAnim(getUsingTextView(), false);
            ViewUtilKt.switchApplyBtnWithAnim(getApplyBtn(), true);
            ViewUtilKt.switchApplyBtnWithAnim(getNewMatchTextView(), false);
            return;
        }
        if (this.usingPosition == i) {
            ViewUtilKt.switchApplyBtnWithAnim(getUsingTextView(), true);
            ViewUtilKt.switchApplyBtnWithAnim(getApplyBtn(), false);
            ViewUtilKt.switchApplyBtnWithAnim(getNewMatchTextView(), false);
        } else {
            ViewUtilKt.switchApplyBtnWithAnim(getUsingTextView(), false);
            ViewUtilKt.switchApplyBtnWithAnim(getApplyBtn(), true);
            ViewUtilKt.switchApplyBtnWithAnim(getNewMatchTextView(), false);
        }
    }

    public final void takeScreenshotAfterRestore() {
        LifecycleCoroutineScope lifecycleScope;
        if (this.lockTemplateView == null || this.screenshotConfig == null) {
            return;
        }
        BaseTemplateView baseTemplateView = this.homeTemplateView;
        if (baseTemplateView != null) {
            DeviceScreenshotHelper.INSTANCE.setCurrentHomeTemplateWeak(new WeakReference(baseTemplateView));
        }
        Object context = getContext();
        LifecycleOwner lifecycleOwner = context instanceof LifecycleOwner ? (LifecycleOwner) context : null;
        if (lifecycleOwner == null || (lifecycleScope = LifecycleOwnerKt.getLifecycleScope(lifecycleOwner)) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(lifecycleScope, null, null, new C01352(null), 3, null);
    }

    public final void tryRestoreIfAllReady() {
        if (this.isCurrentTemplateReady && this.isHistoryTemplateReady && this.isHomeWallpaperDarkResultReady) {
            final Function0 function0 = this.loadDataCallback;
            this.handler.removeCallbacks(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    function0.invoke();
                }
            });
            refreshButtonWhenApply();
            restoreTemplate(new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return SettingsTemplateView.tryRestoreIfAllReady$lambda$43(this.f$0);
                }
            });
            this.templateReadyCount.set(0);
            BaseTemplateView baseTemplateView = this.lockTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.setCombineAnimListener(null);
            }
            BaseTemplateView baseTemplateView2 = this.homeTemplateView;
            if (baseTemplateView2 != null) {
                baseTemplateView2.setCombineAnimListener(null);
            }
        }
    }

    public final void unregisterGalleryStateObserver() {
        ContentObserver contentObserver = this.galleryStateObserver;
        if (contentObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(contentObserver);
            this.galleryStateObserver = null;
        }
    }

    public final void updateResourceWhenConfigurationChanged() {
        TextView usingTextView = getUsingTextView();
        if (usingTextView != null) {
            ViewGroup.LayoutParams layoutParams = usingTextView.getLayoutParams();
            SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
            Context context = usingTextView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            layoutParams.height = (int) settingsResourceProvider.provideUsingTextViewHeight(context);
            Context context2 = usingTextView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            ViewUtil.setMarginTop(usingTextView, (int) settingsResourceProvider.provideUsingTextViewTopMargin(context2));
        }
        View applyBtn = getApplyBtn();
        if (applyBtn != null) {
            ViewGroup.LayoutParams layoutParams2 = applyBtn.getLayoutParams();
            SettingsResourceProvider settingsResourceProvider2 = SettingsResourceProvider.INSTANCE;
            Context context3 = applyBtn.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            layoutParams2.height = (int) settingsResourceProvider2.provideUsingTextViewHeight(context3);
            Context context4 = applyBtn.getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            ViewUtil.setMarginTop(applyBtn, (int) settingsResourceProvider2.provideUsingTextViewTopMargin(context4));
        }
        TextView newMatchTextView = getNewMatchTextView();
        if (newMatchTextView != null) {
            ViewGroup.LayoutParams layoutParams3 = newMatchTextView.getLayoutParams();
            SettingsResourceProvider settingsResourceProvider3 = SettingsResourceProvider.INSTANCE;
            Context context5 = newMatchTextView.getContext();
            Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
            layoutParams3.height = (int) settingsResourceProvider3.provideUsingTextViewHeight(context5);
            Context context6 = newMatchTextView.getContext();
            Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
            ViewUtil.setMarginTop(newMatchTextView, (int) settingsResourceProvider3.provideUsingTextViewTopMargin(context6));
        }
        ConstraintLayout settingView = getSettingView();
        ViewGroup.LayoutParams layoutParams4 = getSettingView().getLayoutParams();
        SettingsResourceProvider settingsResourceProvider4 = SettingsResourceProvider.INSTANCE;
        Context context7 = getContext();
        Intrinsics.checkNotNullExpressionValue(context7, "getContext(...)");
        layoutParams4.height = (int) settingsResourceProvider4.provideWholeSettingsViewHeight(context7);
        settingView.setLayoutParams(layoutParams4);
        View keyGuardLoadingView = getKeyGuardLoadingView();
        ViewGroup.LayoutParams layoutParams5 = getKeyGuardLoadingView().getLayoutParams();
        Context context8 = getContext();
        Intrinsics.checkNotNullExpressionValue(context8, "getContext(...)");
        layoutParams5.width = settingsResourceProvider4.provideTemplateContainerWidth(context8);
        Context context9 = getContext();
        Intrinsics.checkNotNullExpressionValue(context9, "getContext(...)");
        layoutParams5.height = settingsResourceProvider4.provideTemplateContainerHeight(context9);
        keyGuardLoadingView.setLayoutParams(layoutParams5);
        View deskTopLoadingView = getDeskTopLoadingView();
        ViewGroup.LayoutParams layoutParams6 = getDeskTopLoadingView().getLayoutParams();
        Context context10 = getContext();
        Intrinsics.checkNotNullExpressionValue(context10, "getContext(...)");
        layoutParams6.width = settingsResourceProvider4.provideTemplateContainerWidth(context10);
        Context context11 = getContext();
        Intrinsics.checkNotNullExpressionValue(context11, "getContext(...)");
        layoutParams6.height = settingsResourceProvider4.provideTemplateContainerHeight(context11);
        deskTopLoadingView.setLayoutParams(layoutParams6);
        DampViewPager2 viewPager2 = getViewPager2();
        ViewGroup.LayoutParams layoutParams7 = viewPager2.getLayoutParams();
        Context context12 = viewPager2.getContext();
        Intrinsics.checkNotNullExpressionValue(context12, "getContext(...)");
        layoutParams7.width = settingsResourceProvider4.provideSettingsViewWidth(context12);
        Context context13 = viewPager2.getContext();
        Intrinsics.checkNotNullExpressionValue(context13, "getContext(...)");
        layoutParams7.height = (int) settingsResourceProvider4.provideSettingsViewWithRedDotHeight(context13);
        viewPager2.setLayoutParams(layoutParams7);
    }

    public final void updateStatusWhenApplyFailed() {
        showApplyFailedDialog();
        if (getViewPager2().getAdapter() == null) {
            refreshMyTemplate$default(this, 0, 1, null);
        } else {
            refreshButtonWhenApply();
            restoreTemplate$default(this, null, 1, null);
        }
    }
}

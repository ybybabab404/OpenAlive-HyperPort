package com.miui.keyguard.shortcuts.list;

import android.app.WallpaperColors;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.GridLayoutManager;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.keyguard.shortcuts.R;
import com.miui.keyguard.shortcuts.analy.ShortcutAnalyticManager;
import com.miui.keyguard.shortcuts.callback.MiuiWallpaperCallbackWrapper;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.databinding.ActivityLockscreenShortcutsSettingsBinding;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.manager.ShortcutManager;
import com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import com.miui.miwallpaper.MiuiWallpaperManager;
import defpackage.GridSpaceItemDecoration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.LayoutUiModeHelper;
import miuix.container.ExtraPaddingPolicy;
import miuix.core.util.MiuixUIUtils;
import miuix.miuixbasewidget.widget.FilterSortView;
import miuix.os.Build;
import miuix.os.DeviceHelper;
import miuix.recyclerview.widget.RecyclerView;
import miuix.theme.token.ContainerToken;

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
/* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class LockScreenShortcutsSettingsActivity extends AppCompatActivity {
    public static final Companion Companion = new Companion(null);
    public ActivityLockscreenShortcutsSettingsBinding binding;
    public Job createAndUpdateWallpaperJob;
    public int currentLeftSelectedIndex;
    public int currentRightSelectedIndex;
    public int currentSelectedTab;
    public final AnimState dismissIconAnim;
    public ShortcutEntity emptyItem;
    public final Flow finalShortcutsFlow;
    public String initialLeftName;
    public String initialRightName;
    public final MutableStateFlow installedShortcutsFlow;
    public boolean isDark;
    public boolean isLargeScreen;
    public MiuiWallpaperCallbackWrapper miuiWallpaperCallbackWrapper;
    public final Flow overlayShortcutsFlow;
    public final LockScreenShortcutsSettingsActivity$packageChangedCallback$1 packageChangedCallback;
    public ShortcutListRecyclerAdapter shortcutListRecyclerAdapter;
    public final AnimState showIconAnimation;
    public LockScreenShortcutsSettingsActivity$wallpaperCallback$1 wallpaperCallback;
    public MiuiWallpaperManager wallpaperManager;
    public final StateFlow whileListChangeFlow;
    public final List filteredShortcuts = new ArrayList();
    public final List showShortcutItems = new ArrayList();
    public final Lazy shortcutManager$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return LockScreenShortcutsSettingsActivity.shortcutManager_delegate$lambda$0();
        }
    });
    public final Lazy shortcutOverlayRepository$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return LockScreenShortcutsSettingsActivity.shortcutOverlayRepository_delegate$lambda$1(this.f$0);
        }
    });

    /* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$initWallpaperManager$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$initWallpaperManager$1", f = "LockScreenShortcutsSettingsActivity.kt", l = {}, m = "invokeSuspend")
    public static final class C01611 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01611(Continuation continuation) {
            super(2, continuation);
        }

        public static final void invokeSuspend$lambda$0(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, MiuiWallpaperManager miuiWallpaperManager) {
            lockScreenShortcutsSettingsActivity.wallpaperManager = miuiWallpaperManager;
            MiuiWallpaperManager miuiWallpaperManager2 = lockScreenShortcutsSettingsActivity.wallpaperManager;
            if (miuiWallpaperManager2 != null) {
                miuiWallpaperManager2.registerWallpaperChangeListener(lockScreenShortcutsSettingsActivity.miuiWallpaperCallbackWrapper, 2);
            }
            Job job = lockScreenShortcutsSettingsActivity.createAndUpdateWallpaperJob;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            lockScreenShortcutsSettingsActivity.createAndUpdateWallpaperJob = BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lockScreenShortcutsSettingsActivity), Dispatchers.getIO(), null, new LockScreenShortcutsSettingsActivity$initWallpaperManager$1$1$1(lockScreenShortcutsSettingsActivity, null), 2, null);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenShortcutsSettingsActivity.this.new C01611(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01611) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Context applicationContext = LockScreenShortcutsSettingsActivity.this.getApplicationContext();
            final LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = LockScreenShortcutsSettingsActivity.this;
            MiuiWallpaperManager.initSync(applicationContext, new MiuiWallpaperManager.MiuiWallpaperManagerCallback() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$initWallpaperManager$1$$ExternalSyntheticLambda0
                @Override // com.miui.miwallpaper.MiuiWallpaperManager.MiuiWallpaperManagerCallback
                public final void onMiuiWallpaperInitialized(MiuiWallpaperManager miuiWallpaperManager) {
                    LockScreenShortcutsSettingsActivity.C01611.invokeSuspend$lambda$0(lockScreenShortcutsSettingsActivity, miuiWallpaperManager);
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$2, reason: invalid class name */
    /* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$2", f = "LockScreenShortcutsSettingsActivity.kt", l = {296}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenShortcutsSettingsActivity.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataUtils dataUtils = DataUtils.INSTANCE;
                Context applicationContext = LockScreenShortcutsSettingsActivity.this.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                this.label = 1;
                obj = dataUtils.fetchInstalledShortcuts(applicationContext, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            LockScreenShortcutsSettingsActivity.this.installedShortcutsFlow.setValue((List) obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$4, reason: invalid class name */
    /* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$4", f = "LockScreenShortcutsSettingsActivity.kt", l = {305}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenShortcutsSettingsActivity.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final StateFlow leftShortcutFlow = LockScreenShortcutsSettingsActivity.this.getShortcutManager().getLeftShortcutFlow();
                if (leftShortcutFlow != null) {
                    Flow<ShortcutEntity> flow = new Flow<ShortcutEntity>() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$4$invokeSuspend$$inlined$filter$1

                        /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$4$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        /* JADX INFO: compiled from: Emitters.kt */
                        @Metadata
                        @SourceDebugExtension
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$4$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                            /* JADX INFO: compiled from: Emitters.kt */
                            @Metadata
                            @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$4$invokeSuspend$$inlined$filter$1$2", f = "LockScreenShortcutsSettingsActivity.kt", l = {223}, m = "emit")
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public int label;
                                public /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Code duplicated, block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i = anonymousClass1.label;
                                    if ((i & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = new AnonymousClass1(continuation);
                                    }
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                                Object obj2 = anonymousClass1.result;
                                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                int i2 = anonymousClass1.label;
                                if (i2 == 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    FlowCollector flowCollector = this.$this_unsafeFlow;
                                    if (!((ShortcutEntity) obj).isUnknown()) {
                                        anonymousClass1.label = 1;
                                        if (flowCollector.emit(obj, anonymousClass1) == coroutine_suspended) {
                                            return coroutine_suspended;
                                        }
                                    }
                                } else {
                                    if (i2 != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = leftShortcutFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = LockScreenShortcutsSettingsActivity.this;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity.onCreate.4.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(ShortcutEntity shortcutEntity, Continuation continuation) {
                            LogUtils.logI$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "left shortcut changed, " + shortcutEntity, null, 4, null);
                            if (lockScreenShortcutsSettingsActivity.initialLeftName == null) {
                                LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity2 = lockScreenShortcutsSettingsActivity;
                                String shortcutTrackName = shortcutEntity.getShortcutTrackName();
                                if (shortcutTrackName == null) {
                                    shortcutTrackName = "无";
                                }
                                lockScreenShortcutsSettingsActivity2.initialLeftName = shortcutTrackName;
                                lockScreenShortcutsSettingsActivity.recordShortcutEnter();
                            }
                            lockScreenShortcutsSettingsActivity.updateLeftIcon(shortcutEntity.getDrawableWithAvailable(), lockScreenShortcutsSettingsActivity.currentLeftSelectedIndex == 0);
                            lockScreenShortcutsSettingsActivity.refreshCurrentSelectedItems();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$5, reason: invalid class name */
    /* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$5", f = "LockScreenShortcutsSettingsActivity.kt", l = {318}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass5(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenShortcutsSettingsActivity.this.new AnonymousClass5(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final StateFlow rightShortcutFlow = LockScreenShortcutsSettingsActivity.this.getShortcutManager().getRightShortcutFlow();
                if (rightShortcutFlow != null) {
                    Flow<ShortcutEntity> flow = new Flow<ShortcutEntity>() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$5$invokeSuspend$$inlined$filter$1

                        /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$5$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        /* JADX INFO: compiled from: Emitters.kt */
                        @Metadata
                        @SourceDebugExtension
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$5$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                            /* JADX INFO: compiled from: Emitters.kt */
                            @Metadata
                            @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$5$invokeSuspend$$inlined$filter$1$2", f = "LockScreenShortcutsSettingsActivity.kt", l = {223}, m = "emit")
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public int label;
                                public /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Code duplicated, block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i = anonymousClass1.label;
                                    if ((i & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = new AnonymousClass1(continuation);
                                    }
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                                Object obj2 = anonymousClass1.result;
                                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                int i2 = anonymousClass1.label;
                                if (i2 == 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    FlowCollector flowCollector = this.$this_unsafeFlow;
                                    if (!((ShortcutEntity) obj).isUnknown()) {
                                        anonymousClass1.label = 1;
                                        if (flowCollector.emit(obj, anonymousClass1) == coroutine_suspended) {
                                            return coroutine_suspended;
                                        }
                                    }
                                } else {
                                    if (i2 != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = rightShortcutFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = LockScreenShortcutsSettingsActivity.this;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity.onCreate.5.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(ShortcutEntity shortcutEntity, Continuation continuation) {
                            LogUtils.logI$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "right shortcut changed, " + shortcutEntity, null, 4, null);
                            if (lockScreenShortcutsSettingsActivity.initialRightName == null) {
                                LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity2 = lockScreenShortcutsSettingsActivity;
                                String shortcutTrackName = shortcutEntity.getShortcutTrackName();
                                if (shortcutTrackName == null) {
                                    shortcutTrackName = "无";
                                }
                                lockScreenShortcutsSettingsActivity2.initialRightName = shortcutTrackName;
                                lockScreenShortcutsSettingsActivity.recordShortcutEnter();
                            }
                            lockScreenShortcutsSettingsActivity.updateRightIcon(shortcutEntity.getDrawableWithAvailable(), lockScreenShortcutsSettingsActivity.currentRightSelectedIndex == 0);
                            lockScreenShortcutsSettingsActivity.refreshCurrentSelectedItems();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$6, reason: invalid class name */
    /* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$onCreate$6", f = "LockScreenShortcutsSettingsActivity.kt", l = {330}, m = "invokeSuspend")
    public static final class AnonymousClass6 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass6(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenShortcutsSettingsActivity.this.new AnonymousClass6(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass6) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = LockScreenShortcutsSettingsActivity.this.finalShortcutsFlow;
                final LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = LockScreenShortcutsSettingsActivity.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity.onCreate.6.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Pair pair, Continuation continuation) {
                        lockScreenShortcutsSettingsActivity.updateAllShortcuts(pair);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX WARN: Type inference failed for: r0v14, types: [com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$wallpaperCallback$1] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$packageChangedCallback$1] */
    public LockScreenShortcutsSettingsActivity() {
        AnimState animState = new AnimState("dismiss");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        ViewProperty viewProperty2 = ViewProperty.SCALE_X;
        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, 0.9d);
        ViewProperty viewProperty3 = ViewProperty.SCALE_Y;
        this.dismissIconAnim = animStateAdd2.add(viewProperty3, 0.9d);
        this.showIconAnimation = new AnimState("show").add(viewProperty, 1.0d).add(viewProperty2, 1.0d).add(viewProperty3, 1.0d);
        this.wallpaperCallback = new MiuiWallpaperCallbackWrapper.WallpaperCallback() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$wallpaperCallback$1
            @Override // com.miui.keyguard.shortcuts.callback.MiuiWallpaperCallbackWrapper.WallpaperCallback
            public void onWallpaperChanged(WallpaperColors wallpaperColors, String type, int i) {
                Intrinsics.checkNotNullParameter(wallpaperColors, "wallpaperColors");
                Intrinsics.checkNotNullParameter(type, "type");
                LogUtils.logD$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "onWallpaperChanged type: " + type + ", which: " + i, null, 4, null);
                Job job = this.this$0.createAndUpdateWallpaperJob;
                if (job != null) {
                    Job.DefaultImpls.cancel$default(job, null, 1, null);
                }
                LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = this.this$0;
                lockScreenShortcutsSettingsActivity.createAndUpdateWallpaperJob = BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lockScreenShortcutsSettingsActivity), Dispatchers.getIO(), null, new LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1(this.this$0, null), 2, null);
            }
        };
        this.packageChangedCallback = new MiuiShortcutManager.PackageChangedCallback() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$packageChangedCallback$1
            @Override // com.miui.keyguard.shortcuts.manager.MiuiShortcutManager.PackageChangedCallback
            public void onPackageAdded(String packageName, List list) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                if (list != null) {
                    LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = this.this$0;
                    if (list.isEmpty()) {
                        return;
                    }
                    lockScreenShortcutsSettingsActivity.handlePackageAddedOrUpdated(packageName, list, false);
                }
            }

            @Override // com.miui.keyguard.shortcuts.manager.MiuiShortcutManager.PackageChangedCallback
            public void onPackageDataCleared(String str) {
                MiuiShortcutManager.PackageChangedCallback.DefaultImpls.onPackageDataCleared(this, str);
            }

            @Override // com.miui.keyguard.shortcuts.manager.MiuiShortcutManager.PackageChangedCallback
            public void onPackageRemoved(String packageName) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                this.this$0.handlePackageRemoved(packageName);
            }

            @Override // com.miui.keyguard.shortcuts.manager.MiuiShortcutManager.PackageChangedCallback
            public void onPackageUpdate(String packageName, List list) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                if (list != null) {
                    LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = this.this$0;
                    if (list.isEmpty()) {
                        return;
                    }
                    lockScreenShortcutsSettingsActivity.handlePackageAddedOrUpdated(packageName, list, true);
                }
            }
        };
        Flow flowCallbackFlow = FlowKt.callbackFlow(new LockScreenShortcutsSettingsActivity$whileListChangeFlow$1(this, null));
        LifecycleCoroutineScope lifecycleScope = LifecycleOwnerKt.getLifecycleScope(this);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StateFlow stateFlowStateIn = FlowKt.stateIn(flowCallbackFlow, lifecycleScope, companion.getEagerly(), CollectionsKt.emptyList());
        this.whileListChangeFlow = stateFlowStateIn;
        StateFlow stateFlowStateIn2 = FlowKt.stateIn(FlowKt.callbackFlow(new LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1(this, null)), LifecycleOwnerKt.getLifecycleScope(this), SharingStarted.Companion.WhileSubscribed$default(companion, 0L, 0L, 3, null), CollectionsKt.emptyList());
        this.overlayShortcutsFlow = stateFlowStateIn2;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.installedShortcutsFlow = MutableStateFlow;
        final Flow flowCombine = FlowKt.combine(MutableStateFlow, stateFlowStateIn, stateFlowStateIn2, new LockScreenShortcutsSettingsActivity$finalShortcutsFlow$1(this, null));
        this.finalShortcutsFlow = FlowKt.flowOn(new Flow<Pair<? extends List<? extends ShortcutEntity>, ? extends List<ShortcutItem>>>() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$special$$inlined$map$1

            /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$special$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            @Metadata
            @SourceDebugExtension
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$special$$inlined$map$1$2$1, reason: invalid class name */
                /* JADX INFO: compiled from: Emitters.kt */
                @Metadata
                @DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$special$$inlined$map$1$2", f = "LockScreenShortcutsSettingsActivity.kt", l = {223}, m = "emit")
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public int label;
                    public /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Code duplicated, block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    } else {
                        anonymousClass1 = new AnonymousClass1(continuation);
                    }
                    Object obj2 = anonymousClass1.result;
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        FlowCollector flowCollector = this.$this_unsafeFlow;
                        List list = (List) obj;
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(new ShortcutItem((ShortcutEntity) it.next(), false, 2, null));
                        }
                        arrayList.addAll(arrayList2);
                        Pair pair = new Pair(list, arrayList);
                        anonymousClass1.label = 1;
                        if (flowCollector.emit(pair, anonymousClass1) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowCombine.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }, Dispatchers.getDefault());
    }

    public static final boolean handlePackageAddedOrUpdated$lambda$27(String str, ShortcutEntity it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Intrinsics.areEqual(it.getPackageName(), str);
    }

    private final void initView() {
        addTintForPreviewIcon();
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding2 = null;
        if (activityLockscreenShortcutsSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding = null;
        }
        RecyclerView recyclerView = activityLockscreenShortcutsSettingsBinding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(3, recyclerView.getResources().getDimensionPixelSize(R.dimen.keyguard_shortcut_item_horizontal_margin), recyclerView.getResources().getDimensionPixelSize(R.dimen.keyguard_shortcut_item_vertical_margin)));
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.keyguard_shortcut_content_bottom_padding) + MiuixUIUtils.getNavigationBarHeight(this);
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding3 = this.binding;
        if (activityLockscreenShortcutsSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding3 = null;
        }
        activityLockscreenShortcutsSettingsBinding3.scrollView.setPadding(0, 0, 0, dimensionPixelSize);
        initAdapter();
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding4 = this.binding;
        if (activityLockscreenShortcutsSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding4 = null;
        }
        FilterSortView filterSortView = activityLockscreenShortcutsSettingsBinding4.filterSortView;
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding5 = this.binding;
        if (activityLockscreenShortcutsSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding5 = null;
        }
        filterSortView.setFilteredTab(activityLockscreenShortcutsSettingsBinding5.leftTab);
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding6 = this.binding;
        if (activityLockscreenShortcutsSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding6 = null;
        }
        activityLockscreenShortcutsSettingsBinding6.leftTab.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockScreenShortcutsSettingsActivity.initView$lambda$16(this.f$0, view);
            }
        });
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding7 = this.binding;
        if (activityLockscreenShortcutsSettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityLockscreenShortcutsSettingsBinding2 = activityLockscreenShortcutsSettingsBinding7;
        }
        activityLockscreenShortcutsSettingsBinding2.rightTab.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockScreenShortcutsSettingsActivity.initView$lambda$17(this.f$0, view);
            }
        });
        initWallpaperManager();
    }

    public static final void initView$lambda$16(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, View view) {
        if (lockScreenShortcutsSettingsActivity.currentSelectedTab == 0) {
            return;
        }
        lockScreenShortcutsSettingsActivity.currentSelectedTab = 0;
        lockScreenShortcutsSettingsActivity.refreshListChangedItem(lockScreenShortcutsSettingsActivity.getPositionIdBySelectId(lockScreenShortcutsSettingsActivity.currentRightSelectedIndex));
    }

    public static final void initView$lambda$17(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, View view) {
        if (lockScreenShortcutsSettingsActivity.currentSelectedTab == 1) {
            return;
        }
        lockScreenShortcutsSettingsActivity.currentSelectedTab = 1;
        lockScreenShortcutsSettingsActivity.refreshListChangedItem(lockScreenShortcutsSettingsActivity.getPositionIdBySelectId(lockScreenShortcutsSettingsActivity.currentLeftSelectedIndex));
    }

    public static final void refreshListChangedItem$lambda$24(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, int i, int i2) {
        ShortcutListRecyclerAdapter shortcutListRecyclerAdapter = lockScreenShortcutsSettingsActivity.shortcutListRecyclerAdapter;
        if (shortcutListRecyclerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shortcutListRecyclerAdapter");
            shortcutListRecyclerAdapter = null;
        }
        if (i < lockScreenShortcutsSettingsActivity.showShortcutItems.size()) {
            ((ShortcutItem) lockScreenShortcutsSettingsActivity.showShortcutItems.get(i)).setSelected(false);
            shortcutListRecyclerAdapter.notifyItemChanged(i);
        }
        if (i2 < lockScreenShortcutsSettingsActivity.showShortcutItems.size()) {
            ((ShortcutItem) lockScreenShortcutsSettingsActivity.showShortcutItems.get(i2)).setSelected(true);
            shortcutListRecyclerAdapter.notifyItemChanged(i2);
        }
    }

    public static final ShortcutManager shortcutManager_delegate$lambda$0() {
        return new ShortcutManager();
    }

    public static final ShortcutOverlayRepositoryImpl shortcutOverlayRepository_delegate$lambda$1(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity) {
        return new ShortcutOverlayRepositoryImpl(lockScreenShortcutsSettingsActivity);
    }

    public final void addAnimationForPreviewIcon(final ImageView imageView, final Drawable drawable, boolean z) {
        if (z) {
            displayShowIconAnimation(imageView, drawable);
        } else {
            Folme.useAt(imageView).state().to(this.dismissIconAnim, new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.25f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity.addAnimationForPreviewIcon.1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object toTag) {
                    Intrinsics.checkNotNullParameter(toTag, "toTag");
                    super.onComplete(toTag);
                    LockScreenShortcutsSettingsActivity.this.displayShowIconAnimation(imageView, drawable);
                }
            }));
        }
    }

    public final void addTintForPreviewIcon() {
        ViewUtils viewUtils = ViewUtils.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        ColorStateList iconTintList = viewUtils.getIconTintList(this, viewUtils.getBottomIconDeep(applicationContext));
        if (iconTintList != null) {
            ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
            ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding2 = null;
            if (activityLockscreenShortcutsSettingsBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityLockscreenShortcutsSettingsBinding = null;
            }
            activityLockscreenShortcutsSettingsBinding.leftImage.setImageTintList(iconTintList);
            ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding3 = this.binding;
            if (activityLockscreenShortcutsSettingsBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityLockscreenShortcutsSettingsBinding2 = activityLockscreenShortcutsSettingsBinding3;
            }
            activityLockscreenShortcutsSettingsBinding2.rightImage.setImageTintList(iconTintList);
        }
    }

    public final void displayShowIconAnimation(ImageView imageView, Drawable drawable) {
        Folme.useAt(imageView).state().to(this.showIconAnimation, new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.35f)).setDelay(50L));
        imageView.setImageDrawable(drawable);
    }

    public final int getPositionIdBySelectId(int i) {
        int size = this.filteredShortcuts.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i == ((ShortcutEntity) this.filteredShortcuts.get(i2)).getIndex()) {
                return i2;
            }
        }
        return 0;
    }

    public final ShortcutManager getShortcutManager() {
        return (ShortcutManager) this.shortcutManager$delegate.getValue();
    }

    public final ShortcutOverlayRepositoryImpl getShortcutOverlayRepository() {
        return (ShortcutOverlayRepositoryImpl) this.shortcutOverlayRepository$delegate.getValue();
    }

    public final void handlePackageAddedOrUpdated(final String str, List list, boolean z) {
        List mutableList = CollectionsKt.toMutableList((Collection) this.installedShortcutsFlow.getValue());
        if (z) {
            CollectionsKt.removeAll(mutableList, new Function1() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(LockScreenShortcutsSettingsActivity.handlePackageAddedOrUpdated$lambda$27(str, (ShortcutEntity) obj));
                }
            });
        }
        mutableList.addAll(list);
        this.installedShortcutsFlow.setValue(mutableList);
    }

    public final void handlePackageRemoved(String str) {
        List list = (List) this.installedShortcutsFlow.getValue();
        if (list.isEmpty()) {
            return;
        }
        int size = list.size();
        Iterable iterable = (Iterable) this.installedShortcutsFlow.getValue();
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (!Intrinsics.areEqual(((ShortcutEntity) obj).getPackageName(), str)) {
                arrayList.add(obj);
            }
        }
        if (arrayList.size() != size) {
            this.installedShortcutsFlow.setValue(arrayList);
        }
    }

    public final void initAdapter() {
        this.shortcutListRecyclerAdapter = new ShortcutListRecyclerAdapter(this.showShortcutItems, new OnItemClickListener() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity.initAdapter.1
            @Override // com.miui.keyguard.shortcuts.list.OnItemClickListener
            public void onItemClick(int i) {
                LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = LockScreenShortcutsSettingsActivity.this;
                lockScreenShortcutsSettingsActivity.performItemClick(i, lockScreenShortcutsSettingsActivity.currentSelectedTab == 0);
            }
        });
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
        ShortcutListRecyclerAdapter shortcutListRecyclerAdapter = null;
        if (activityLockscreenShortcutsSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding = null;
        }
        RecyclerView recyclerView = activityLockscreenShortcutsSettingsBinding.recyclerView;
        ShortcutListRecyclerAdapter shortcutListRecyclerAdapter2 = this.shortcutListRecyclerAdapter;
        if (shortcutListRecyclerAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shortcutListRecyclerAdapter");
        } else {
            shortcutListRecyclerAdapter = shortcutListRecyclerAdapter2;
        }
        recyclerView.setAdapter(shortcutListRecyclerAdapter);
    }

    public final void initWallpaperManager() {
        MiuiWallpaperCallbackWrapper miuiWallpaperCallbackWrapper = new MiuiWallpaperCallbackWrapper();
        this.miuiWallpaperCallbackWrapper = miuiWallpaperCallbackWrapper;
        miuiWallpaperCallbackWrapper.setCallback(this.wallpaperCallback);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C01611(null), 2, null);
    }

    public final List mergeDataSource(List list, List list2, List list3) {
        if (!list.isEmpty() && !list2.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ShortcutEntity shortcutEntity = this.emptyItem;
            if (shortcutEntity != null) {
                arrayList.add(shortcutEntity);
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                ArrayList arrayList3 = new ArrayList();
                for (Object obj : list3) {
                    if (Intrinsics.areEqual(((ShortcutEntity) obj).getUniqueTag(), str)) {
                        arrayList3.add(obj);
                    }
                }
                if (arrayList3.isEmpty()) {
                    arrayList3 = new ArrayList();
                    for (Object obj2 : list2) {
                        if (Intrinsics.areEqual(((ShortcutEntity) obj2).getUniqueTag(), str)) {
                            arrayList3.add(obj2);
                        }
                    }
                }
                ArrayList arrayList4 = new ArrayList();
                for (Object obj3 : arrayList3) {
                    ShortcutEntity shortcutEntity2 = (ShortcutEntity) obj3;
                    boolean z = shortcutEntity2.isAvailable() && shortcutEntity2.getDrawable() != null;
                    if (!z) {
                        LogUtils.logE$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "shortcut item is not valid. " + shortcutEntity2, null, 4, null);
                    }
                    if (z) {
                        arrayList4.add(obj3);
                    }
                }
                CollectionsKt.addAll(arrayList2, arrayList4);
            }
            Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(arrayList2);
            ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
            for (IndexedValue indexedValue : iterableWithIndex) {
                int iComponent1 = indexedValue.component1();
                ShortcutEntity shortcutEntity3 = (ShortcutEntity) indexedValue.component2();
                shortcutEntity3.setIndex(iComponent1 + 1);
                arrayList5.add(shortcutEntity3);
            }
            arrayList.addAll(arrayList5);
            return arrayList;
        }
        return CollectionsKt.emptyList();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        boolean z = (newConfig.uiMode & 48) == 32;
        if (this.isDark != z) {
            this.isDark = z;
            AppCompatDelegate.setDefaultNightMode(z ? 2 : 1);
            recreate();
            return;
        }
        boolean zIsLargeScreen = ViewUtils.INSTANCE.isLargeScreen();
        if (this.isLargeScreen != zIsLargeScreen) {
            this.isLargeScreen = zIsLargeScreen;
            int dimensionPixelSize = !zIsLargeScreen ? getResources().getDimensionPixelSize(R.dimen.miuix_theme_margin_horizontal_common) : getExtraHorizontalPadding();
            ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
            if (activityLockscreenShortcutsSettingsBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityLockscreenShortcutsSettingsBinding = null;
            }
            activityLockscreenShortcutsSettingsBinding.getRoot().setPaddingRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = ActivityLockscreenShortcutsSettingsBinding.inflate(getLayoutInflater());
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        getWindow().getAttributes().layoutInDisplayCutoutMode = 1;
        ActionBar appCompatActionBar = getAppCompatActionBar();
        if (appCompatActionBar != null) {
            appCompatActionBar.setActionBarStrategy(new InnerStrategy(this));
        }
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
        if (activityLockscreenShortcutsSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding = null;
        }
        setContentView(activityLockscreenShortcutsSettingsBinding.getRoot());
        if (Build.IS_TABLET) {
            finish();
            return;
        }
        this.isLargeScreen = ViewUtils.INSTANCE.isLargeScreen();
        setHorizontalPaddingPolicy();
        this.isDark = (getResources().getConfiguration().uiMode & 48) == 32;
        initView();
        this.emptyItem = new ShortcutEntity(0, null, null, null, null, null, null, null, null, false, false, ContextCompat.getString(this, R.string.keyguard_shortcut_empty), null, ContextCompat.getDrawable(this, R.drawable.shortcut_empty), false, 22526, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass2(null), 3, null);
        ShortcutManager shortcutManager = getShortcutManager();
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        shortcutManager.init(applicationContext, false);
        shortcutManager.addPackageChangedCallback(this.packageChangedCallback);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass4(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass5(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass6(null), 3, null);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        recordShortcutExit();
        Job job = this.createAndUpdateWallpaperJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        CoroutineScopeKt.cancel$default(LifecycleOwnerKt.getLifecycleScope(this), null, 1, null);
        MiuiWallpaperManager miuiWallpaperManager = this.wallpaperManager;
        if (miuiWallpaperManager != null) {
            miuiWallpaperManager.unRegisterWallpaperChangeListener(this.miuiWallpaperCallbackWrapper);
        }
        MiuiWallpaperCallbackWrapper miuiWallpaperCallbackWrapper = this.miuiWallpaperCallbackWrapper;
        if (miuiWallpaperCallbackWrapper != null) {
            miuiWallpaperCallbackWrapper.setCallback(null);
        }
        this.miuiWallpaperCallbackWrapper = null;
        MiuiWallpaperManager.staticDestroy();
        ShortcutManager shortcutManager = getShortcutManager();
        shortcutManager.removePackageChangedCallback(this.packageChangedCallback);
        shortcutManager.release();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        super.onExtraPaddingChanged(i);
        if (this.isLargeScreen) {
            ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
            if (activityLockscreenShortcutsSettingsBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityLockscreenShortcutsSettingsBinding = null;
            }
            activityLockscreenShortcutsSettingsBinding.getRoot().setPaddingRelative(i, 0, i, 0);
        }
        LogUtils.logD$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "onExtraPaddingChanged: extraHorizontalPadding = " + i + ", isLargeScreen = " + this.isLargeScreen, null, 4, null);
    }

    public final void performItemClick(int i, boolean z) {
        int i2 = z ? this.currentLeftSelectedIndex : this.currentRightSelectedIndex;
        int i3 = z ? this.currentRightSelectedIndex : this.currentLeftSelectedIndex;
        if (i == i2) {
            return;
        }
        LogUtils.logD$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "performItemClick, newSelectedIndex=" + i + ", currentSelectedIndex=" + i2 + ", otherSelectedIndex=" + i3, null, 4, null);
        if (i == i3 && i != 0) {
            DataUtils.INSTANCE.saveShortcutDataToSettings(this, !z, (ShortcutEntity) this.filteredShortcuts.get(i2), "itemClick");
        }
        DataUtils.INSTANCE.saveShortcutDataToSettings(this, z, (ShortcutEntity) this.filteredShortcuts.get(i), "itemClick");
    }

    public final void recordShortcutEnter() {
        String str;
        String str2 = this.initialLeftName;
        if (str2 == null || (str = this.initialRightName) == null) {
            return;
        }
        ShortcutAnalyticManager.INSTANCE.handleKeyguardShortcutEnterEvent(str2, str);
    }

    public final void recordShortcutExit() {
        String shortcutTrackName;
        ShortcutEntity shortcutEntity;
        String shortcutTrackName2;
        ShortcutEntity shortcutEntity2;
        StateFlow leftShortcutFlow = getShortcutManager().getLeftShortcutFlow();
        String str = "无";
        if (leftShortcutFlow == null || (shortcutEntity2 = (ShortcutEntity) leftShortcutFlow.getValue()) == null || (shortcutTrackName = shortcutEntity2.getShortcutTrackName()) == null) {
            shortcutTrackName = "无";
        }
        StateFlow rightShortcutFlow = getShortcutManager().getRightShortcutFlow();
        if (rightShortcutFlow != null && (shortcutEntity = (ShortcutEntity) rightShortcutFlow.getValue()) != null && (shortcutTrackName2 = shortcutEntity.getShortcutTrackName()) != null) {
            str = shortcutTrackName2;
        }
        ShortcutAnalyticManager.INSTANCE.handleKeyguardShortcutExitEvent((Intrinsics.areEqual(this.initialLeftName, shortcutTrackName) && Intrinsics.areEqual(this.initialRightName, str)) ? false : true, shortcutTrackName, str);
    }

    public final void refreshCurrentSelectedItems() {
        int i = this.currentSelectedTab == 0 ? this.currentLeftSelectedIndex : this.currentRightSelectedIndex;
        StateFlow leftShortcutFlow = getShortcutManager().getLeftShortcutFlow();
        ShortcutEntity shortcutEntity = leftShortcutFlow != null ? (ShortcutEntity) leftShortcutFlow.getValue() : null;
        if (shortcutEntity != null) {
            Iterator it = this.filteredShortcuts.iterator();
            int i2 = 0;
            while (true) {
                if (!it.hasNext()) {
                    i2 = -1;
                    break;
                } else if (Intrinsics.areEqual(((ShortcutEntity) it.next()).getUniqueTag(), shortcutEntity.getUniqueTag())) {
                    break;
                } else {
                    i2++;
                }
            }
            this.currentLeftSelectedIndex = i2;
            if (i2 == -1 || !shortcutEntity.isAvailable()) {
                this.currentLeftSelectedIndex = 0;
            }
        }
        StateFlow rightShortcutFlow = getShortcutManager().getRightShortcutFlow();
        ShortcutEntity shortcutEntity2 = rightShortcutFlow != null ? (ShortcutEntity) rightShortcutFlow.getValue() : null;
        if (shortcutEntity2 != null) {
            Iterator it2 = this.filteredShortcuts.iterator();
            int i3 = 0;
            while (true) {
                if (!it2.hasNext()) {
                    i3 = -1;
                    break;
                } else if (Intrinsics.areEqual(((ShortcutEntity) it2.next()).getUniqueTag(), shortcutEntity2.getUniqueTag())) {
                    break;
                } else {
                    i3++;
                }
            }
            this.currentRightSelectedIndex = i3;
            if (i3 == -1 || !shortcutEntity2.isAvailable()) {
                this.currentRightSelectedIndex = 0;
            }
        }
        LogUtils.logD$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "refreshCurrentSelectedItems: currentSelectedTab=" + this.currentSelectedTab + ", preSelectIndex=" + i + ", currentLeftSelectedIndex=" + this.currentLeftSelectedIndex + ", currentRightSelectedIndex=" + this.currentRightSelectedIndex, null, 4, null);
        refreshListChangedItem(getPositionIdBySelectId(i));
    }

    public final void refreshListChangedItem(final int i) {
        final int positionIdBySelectId = this.currentSelectedTab == 0 ? getPositionIdBySelectId(this.currentLeftSelectedIndex) : getPositionIdBySelectId(this.currentRightSelectedIndex);
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
        if (activityLockscreenShortcutsSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding = null;
        }
        activityLockscreenShortcutsSettingsBinding.recyclerView.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                LockScreenShortcutsSettingsActivity.refreshListChangedItem$lambda$24(this.f$0, i, positionIdBySelectId);
            }
        });
    }

    public final void setHorizontalPaddingPolicy() {
        if (DeviceHelper.detectType(this) == 2 || DeviceHelper.isFoldable()) {
            ExtraPaddingPolicy.Builder thresholds = new ExtraPaddingPolicy.Builder().setPaddingHorizontalCommonDp(ContainerToken.PADDING_HORIZONTAL_COMMON).setThresholds(420, 640);
            int i = ContainerToken.PADDING_BASE_DP;
            setExtraPaddingPolicy(thresholds.setPaddingsDp(i * 3, i * 5, i * 9).create());
        }
    }

    public final void updateAllShortcuts(Pair pair) {
        this.filteredShortcuts.clear();
        this.filteredShortcuts.addAll((Collection) pair.getFirst());
        this.showShortcutItems.clear();
        this.showShortcutItems.addAll((Collection) pair.getSecond());
        ShortcutListRecyclerAdapter shortcutListRecyclerAdapter = this.shortcutListRecyclerAdapter;
        if (shortcutListRecyclerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shortcutListRecyclerAdapter");
            shortcutListRecyclerAdapter = null;
        }
        shortcutListRecyclerAdapter.notifyDataSetChanged();
        refreshCurrentSelectedItems();
    }

    public final void updateIcon(ImageView imageView, Drawable drawable, boolean z) {
        if (drawable != null) {
            addAnimationForPreviewIcon(imageView, drawable, z);
        } else {
            Folme.useAt(imageView).state().to(this.dismissIconAnim, new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.25f)));
        }
    }

    public final void updateLeftIcon(Drawable drawable, boolean z) {
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
        if (activityLockscreenShortcutsSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding = null;
        }
        ImageView leftImage = activityLockscreenShortcutsSettingsBinding.leftImage;
        Intrinsics.checkNotNullExpressionValue(leftImage, "leftImage");
        updateIcon(leftImage, drawable, z);
    }

    public final void updateRightIcon(Drawable drawable, boolean z) {
        ActivityLockscreenShortcutsSettingsBinding activityLockscreenShortcutsSettingsBinding = this.binding;
        if (activityLockscreenShortcutsSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockscreenShortcutsSettingsBinding = null;
        }
        ImageView rightImage = activityLockscreenShortcutsSettingsBinding.rightImage;
        Intrinsics.checkNotNullExpressionValue(rightImage, "rightImage");
        updateIcon(rightImage, drawable, z);
    }
}

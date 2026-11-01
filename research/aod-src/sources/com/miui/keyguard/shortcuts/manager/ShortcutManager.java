package com.miui.keyguard.shortcuts.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;
import com.miui.aod.utils.ContextUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.keyguard.shortcuts.analy.ShortcutAnalyticManager;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;
import miuix.core.util.EnvStateManager;

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
/* JADX INFO: compiled from: MiuiShortcutManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"StaticFieldLeak"})
@SourceDebugExtension
public class ShortcutManager {
    public static final Companion Companion = new Companion(null);
    public AccessibilityManager accessibilityManager;
    public final ShortcutManager$broadcastReceiver$1 broadcastReceiver;
    public Context context;
    public CoroutineScope coroutineScope;
    public boolean interactive;
    public boolean isBouncerShowing;
    public boolean isDefaultTheme;
    public boolean isInAppExiting;
    public boolean isInAppOpening;
    public boolean isKeyguardGoingAway;
    public boolean isKeyguardShowing;
    public volatile boolean isOccluded;
    public boolean isSystemUIProcess;
    public MiuiShortcutManager.OccludedMovedShortcut occludedMovedShortcut;
    public final ShortcutManager$packageChangedCallback$1 packageChangedCallback;
    public final List packageChangedCallbacks;
    public PackageChangedManager packageChangedManager;
    public final List shortcutDataCallbacks;
    public ShortcutEntity shortcutDataLeft;
    public ShortcutEntity shortcutDataRight;
    public ShortcutOverlayManager shortcutOverlayManager;
    public ShortcutRepository shortcutRepository;
    public ShortcutWhiteListManager shortcutWhiteListManager;
    public boolean talkbackEnabled;
    public Context userContext;
    public int userId;
    public final ShortcutManager$whiteListChangedCallback$1 whiteListChangedCallback;
    public final List whiteListChangedCallbacks;
    public final String GXZW_POSITION = SystemProperties.get("ro.hardware.fp.fod.location");
    public Configuration configuration = new Configuration();

    /* JADX INFO: compiled from: MiuiShortcutManager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: MiuiShortcutManager.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MiuiShortcutManager.OccludedMovedShortcut.values().length];
            try {
                iArr[MiuiShortcutManager.OccludedMovedShortcut.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MiuiShortcutManager.OccludedMovedShortcut.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$clearOverlayShortcutsIfNeed$1, reason: invalid class name */
    /* JADX INFO: compiled from: MiuiShortcutManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutManager$clearOverlayShortcutsIfNeed$1", f = "MiuiShortcutManager.kt", l = {441}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ String $pkgName;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, Continuation continuation) {
            super(2, continuation);
            this.$pkgName = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutManager.this.new AnonymousClass1(this.$pkgName, continuation);
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
                ShortcutOverlayManager shortcutOverlayManager = ShortcutManager.this.shortcutOverlayManager;
                if (shortcutOverlayManager != null) {
                    String str = this.$pkgName;
                    this.label = 1;
                    if (shortcutOverlayManager.deleteShortcuts(str, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MiuiShortcutManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutManager$init$1", f = "MiuiShortcutManager.kt", l = {202}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class C01621 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ShortcutRepositoryImpl $shortcutRepository;
        public int label;
        public final /* synthetic */ ShortcutManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01621(ShortcutRepositoryImpl shortcutRepositoryImpl, ShortcutManager shortcutManager, Continuation continuation) {
            super(2, continuation);
            this.$shortcutRepository = shortcutRepositoryImpl;
            this.this$0 = shortcutManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01621(this.$shortcutRepository, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01621) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                booleanRef.element = true;
                final StateFlow leftShortcut = this.$shortcutRepository.getLeftShortcut();
                Flow<ShortcutEntity> flow = new Flow<ShortcutEntity>() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$1$invokeSuspend$$inlined$filter$1

                    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    /* JADX INFO: compiled from: Emitters.kt */
                    @Metadata
                    @SourceDebugExtension
                    public static final class AnonymousClass2<T> implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                        /* JADX INFO: compiled from: Emitters.kt */
                        @Metadata
                        @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutManager$init$1$invokeSuspend$$inlined$filter$1$2", f = "MiuiShortcutManager.kt", l = {223}, m = "emit")
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
                        Object objCollect = leftShortcut.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                };
                final ShortcutManager shortcutManager = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager.init.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(ShortcutEntity shortcutEntity, Continuation continuation) {
                        shortcutManager.onShortcutChanged(shortcutEntity, true, booleanRef.element);
                        booleanRef.element = false;
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$2, reason: invalid class name */
    /* JADX INFO: compiled from: MiuiShortcutManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutManager$init$2", f = "MiuiShortcutManager.kt", l = {209}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ShortcutRepositoryImpl $shortcutRepository;
        public int label;
        public final /* synthetic */ ShortcutManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ShortcutRepositoryImpl shortcutRepositoryImpl, ShortcutManager shortcutManager, Continuation continuation) {
            super(2, continuation);
            this.$shortcutRepository = shortcutRepositoryImpl;
            this.this$0 = shortcutManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$shortcutRepository, this.this$0, continuation);
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
                final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                booleanRef.element = true;
                final StateFlow rightShortcut = this.$shortcutRepository.getRightShortcut();
                Flow<ShortcutEntity> flow = new Flow<ShortcutEntity>() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$2$invokeSuspend$$inlined$filter$1

                    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$2$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    /* JADX INFO: compiled from: Emitters.kt */
                    @Metadata
                    @SourceDebugExtension
                    public static final class AnonymousClass2<T> implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$init$2$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                        /* JADX INFO: compiled from: Emitters.kt */
                        @Metadata
                        @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutManager$init$2$invokeSuspend$$inlined$filter$1$2", f = "MiuiShortcutManager.kt", l = {223}, m = "emit")
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
                        Object objCollect = rightShortcut.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                };
                final ShortcutManager shortcutManager = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager.init.2.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(ShortcutEntity shortcutEntity, Continuation continuation) {
                        shortcutManager.onShortcutChanged(shortcutEntity, false, booleanRef.element);
                        booleanRef.element = false;
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutManager$onShortcutOverlayChanged$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MiuiShortcutManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutManager$onShortcutOverlayChanged$2", f = "MiuiShortcutManager.kt", l = {313}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class C01632 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $currentContext;
        public final /* synthetic */ boolean $isLeft;
        public final /* synthetic */ ShortcutEntity $newShortcut;
        public Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01632(Context context, boolean z, ShortcutEntity shortcutEntity, Continuation continuation) {
            super(2, continuation);
            this.$currentContext = context;
            this.$isLeft = z;
            this.$newShortcut = shortcutEntity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01632(this.$currentContext, this.$isLeft, this.$newShortcut, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01632) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Context context = this.$currentContext;
                if (context != null) {
                    boolean z = this.$isLeft;
                    ShortcutEntity shortcutEntity = this.$newShortcut;
                    DataUtils dataUtils = DataUtils.INSTANCE;
                    this.L$0 = context;
                    this.label = 1;
                    if (dataUtils.saveShortcutDataToSettingsAsync(context, z, shortcutEntity, "overlay changed", this) == coroutine_suspended) {
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

    /* JADX WARN: Type inference failed for: r0v10, types: [com.miui.keyguard.shortcuts.manager.ShortcutManager$packageChangedCallback$1] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.miui.keyguard.shortcuts.manager.ShortcutManager$whiteListChangedCallback$1] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.miui.keyguard.shortcuts.manager.ShortcutManager$broadcastReceiver$1] */
    public ShortcutManager() {
        DataUtils dataUtils = DataUtils.INSTANCE;
        this.shortcutDataLeft = dataUtils.getUNKNOWN_ENTITY();
        this.shortcutDataRight = dataUtils.getUNKNOWN_ENTITY();
        this.shortcutDataCallbacks = new ArrayList();
        this.packageChangedCallbacks = new ArrayList();
        this.whiteListChangedCallbacks = new ArrayList();
        this.occludedMovedShortcut = MiuiShortcutManager.OccludedMovedShortcut.NONE;
        this.interactive = true;
        this.isDefaultTheme = true;
        this.isKeyguardShowing = true;
        this.isSystemUIProcess = true;
        this.packageChangedCallback = new PackageChangedManager.PackageChangedCallback() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager$packageChangedCallback$1
            @Override // com.miui.keyguard.shortcuts.manager.PackageChangedManager.PackageChangedCallback
            public void onPackageAdded(String packageName, List list) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                if (list != null) {
                    this.this$0.updateShortcutsDatabase(list, packageName + " added");
                }
                Iterator it = this.this$0.packageChangedCallbacks.iterator();
                while (it.hasNext()) {
                    ((MiuiShortcutManager.PackageChangedCallback) it.next()).onPackageAdded(packageName, list);
                }
            }

            @Override // com.miui.keyguard.shortcuts.manager.PackageChangedManager.PackageChangedCallback
            public void onPackageDataCleared(String packageName) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                this.this$0.clearOverlayShortcutsIfNeed(packageName);
                Iterator it = this.this$0.packageChangedCallbacks.iterator();
                while (it.hasNext()) {
                    ((MiuiShortcutManager.PackageChangedCallback) it.next()).onPackageDataCleared(packageName);
                }
            }

            @Override // com.miui.keyguard.shortcuts.manager.PackageChangedManager.PackageChangedCallback
            public void onPackageRemoved(String packageName) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                this.this$0.clearShortcutsDatabase(packageName, packageName + " removed");
                this.this$0.clearOverlayShortcutsIfNeed(packageName);
                Iterator it = this.this$0.packageChangedCallbacks.iterator();
                while (it.hasNext()) {
                    ((MiuiShortcutManager.PackageChangedCallback) it.next()).onPackageRemoved(packageName);
                }
            }

            @Override // com.miui.keyguard.shortcuts.manager.PackageChangedManager.PackageChangedCallback
            public void onPackageUpdate(String packageName, List list) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                if (list != null) {
                    this.this$0.updateShortcutsDatabase(list, packageName + " updated");
                }
                Iterator it = this.this$0.packageChangedCallbacks.iterator();
                while (it.hasNext()) {
                    ((MiuiShortcutManager.PackageChangedCallback) it.next()).onPackageUpdate(packageName, list);
                }
            }
        };
        this.whiteListChangedCallback = new ShortcutWhiteListManager.WhiteListChangedCallback() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager$whiteListChangedCallback$1
            @Override // com.miui.keyguard.shortcuts.manager.ShortcutWhiteListManager.WhiteListChangedCallback
            public void onWhiteListChanged(List whiteList) {
                Intrinsics.checkNotNullParameter(whiteList, "whiteList");
                this.this$0.handleShortcutWhiteListChanged(whiteList);
                Iterator it = this.this$0.whiteListChangedCallbacks.iterator();
                while (it.hasNext()) {
                    ((MiuiShortcutManager.WhiteListChangedCallback) it.next()).onWhiteListChanged(whiteList);
                }
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                String action = intent.getAction();
                if (Intrinsics.areEqual(action, "miui.intent.action.MIUI_REGION_CHANGED")) {
                    this.this$0.onRegionChanged();
                } else if (Intrinsics.areEqual(action, "android.intent.action.USER_UNLOCKED")) {
                    this.this$0.onUserUnlocked();
                }
            }
        };
    }

    public static /* synthetic */ void init$default(ShortcutManager shortcutManager, Context context, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: init");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        shortcutManager.init(context, z);
    }

    public static final void init$lambda$3(ShortcutManager shortcutManager, boolean z) {
        LogUtils.logI$default(LogUtils.INSTANCE, "MiuiShortcutManager", "touchExplorationStateEnable: " + z, null, 4, null);
        shortcutManager.talkbackEnabled = z;
    }

    public final void addPackageChangedCallback(MiuiShortcutManager.PackageChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.packageChangedCallbacks.contains(callback)) {
            return;
        }
        this.packageChangedCallbacks.add(callback);
    }

    public final void addShortDataCallback(MiuiShortcutManager.ShortcutDataCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.shortcutDataCallbacks.contains(callback)) {
            return;
        }
        this.shortcutDataCallbacks.add(callback);
        callback.onShortcutDataChanged(this.shortcutDataLeft, true);
        callback.onShortcutDataChanged(this.shortcutDataRight, false);
    }

    public final void addWhiteListChangedCallback(MiuiShortcutManager.WhiteListChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.whiteListChangedCallbacks.contains(callback)) {
            return;
        }
        this.whiteListChangedCallbacks.add(callback);
    }

    public final boolean aodEnable(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "doze_always_on", 0) == 1;
    }

    public final void clearOverlayShortcutsIfNeed(String str) {
        CoroutineScope coroutineScope;
        if (!this.isSystemUIProcess || (coroutineScope = this.coroutineScope) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(str, null), 3, null);
    }

    public final void clearShortcutDatabaseIfNeed(ShortcutEntity shortcutEntity, boolean z, String str, String str2) {
        Context context;
        if (!Intrinsics.areEqual(shortcutEntity.getPackageName(), str) || (context = this.context) == null) {
            return;
        }
        DataUtils dataUtils = DataUtils.INSTANCE;
        dataUtils.saveShortcutDataToSettings(context, z, dataUtils.getEMPTY_ENTITY(), str2);
    }

    public final void clearShortcutDatabaseWhenWhiteListChanged(ShortcutEntity shortcutEntity, boolean z, List list) {
        Context context;
        if (shortcutEntity.isEmptyOrUnknown()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (Intrinsics.areEqual(shortcutEntity.getUniqueTag(), (String) obj)) {
                arrayList.add(obj);
            }
        }
        if (!arrayList.isEmpty() || (context = this.context) == null) {
            return;
        }
        DataUtils dataUtils = DataUtils.INSTANCE;
        dataUtils.saveShortcutDataToSettings(context, z, dataUtils.getEMPTY_ENTITY(), "white list changed");
    }

    public final void clearShortcutsDatabase(String str, String str2) {
        clearShortcutDatabaseIfNeed(this.shortcutDataLeft, true, str, str2);
        clearShortcutDatabaseIfNeed(this.shortcutDataRight, false, str, str2);
    }

    public final boolean fullAodEnable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return aodEnable(context) && (Settings.Secure.getInt(context.getContentResolver(), "full_screen_aod_on", 0) == 1);
    }

    public final boolean getInteractive() {
        return this.interactive;
    }

    public final StateFlow getLeftShortcutFlow() {
        ShortcutRepository shortcutRepository = this.shortcutRepository;
        if (shortcutRepository != null) {
            return shortcutRepository.getLeftShortcut();
        }
        return null;
    }

    public final StateFlow getRightShortcutFlow() {
        ShortcutRepository shortcutRepository = this.shortcutRepository;
        if (shortcutRepository != null) {
            return shortcutRepository.getRightShortcut();
        }
        return null;
    }

    public final ShortcutEntity getShortcutDataLeft() {
        return this.shortcutDataLeft;
    }

    public final ShortcutEntity getShortcutDataRight() {
        return this.shortcutDataRight;
    }

    public final Context getSysuiContext() {
        return this.context;
    }

    public final boolean getTalkbackEnabled() {
        return this.talkbackEnabled;
    }

    public final List getWhiteList() {
        List whiteList;
        ShortcutWhiteListManager shortcutWhiteListManager = this.shortcutWhiteListManager;
        return (shortcutWhiteListManager == null || (whiteList = shortcutWhiteListManager.getWhiteList()) == null) ? CollectionsKt.emptyList() : whiteList;
    }

    public final void handleShortcutWhiteListChanged(List list) {
        clearShortcutDatabaseWhenWhiteListChanged(this.shortcutDataLeft, true, list);
        clearShortcutDatabaseWhenWhiteListChanged(this.shortcutDataRight, false, list);
    }

    public final void init(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        LogUtils.logD$default(LogUtils.INSTANCE, "MiuiShortcutManager", "init, left and right", null, 4, null);
        this.context = context;
        int i = UserHandleUtils.USER_CURRENT;
        this.userId = i;
        Context contextCreateContextAsUser = z ? ContextUtils.createContextAsUser(context, i, 2) : context;
        this.userContext = contextCreateContextAsUser;
        this.isSystemUIProcess = z;
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain().getImmediate());
        this.coroutineScope = CoroutineScope;
        Intrinsics.checkNotNull(contextCreateContextAsUser);
        ShortcutRepositoryImpl shortcutRepositoryImpl = new ShortcutRepositoryImpl(contextCreateContextAsUser, CoroutineScope, z);
        this.shortcutRepository = shortcutRepositoryImpl;
        ShortcutOverlayManager shortcutOverlayManager = z ? new ShortcutOverlayManager(contextCreateContextAsUser) : null;
        this.shortcutOverlayManager = shortcutOverlayManager;
        shortcutRepositoryImpl.observe();
        BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new C01621(shortcutRepositoryImpl, this, null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new AnonymousClass2(shortcutRepositoryImpl, this, null), 3, null);
        if (shortcutOverlayManager != null) {
            shortcutOverlayManager.init();
            BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new ShortcutManager$init$3$1(shortcutOverlayManager, this, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new ShortcutManager$init$3$2(shortcutOverlayManager, this, null), 3, null);
        }
        registerBroadcastReceiver();
        this.configuration.updateFrom(context.getResources().getConfiguration());
        PackageChangedManager packageChangedManager = new PackageChangedManager(context);
        packageChangedManager.addCallback(this.packageChangedCallback);
        this.packageChangedManager = packageChangedManager;
        ShortcutWhiteListManager shortcutWhiteListManager = new ShortcutWhiteListManager(context);
        shortcutWhiteListManager.addCallback(this.whiteListChangedCallback);
        this.shortcutWhiteListManager = shortcutWhiteListManager;
        if (z) {
            Object systemService = context.getSystemService("accessibility");
            AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
            this.accessibilityManager = accessibilityManager;
            if (accessibilityManager != null) {
                accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutManager$$ExternalSyntheticLambda0
                    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                    public final void onTouchExplorationStateChanged(boolean z2) {
                        ShortcutManager.init$lambda$3(this.f$0, z2);
                    }
                });
            }
            AccessibilityManager accessibilityManager2 = this.accessibilityManager;
            this.talkbackEnabled = accessibilityManager2 != null ? accessibilityManager2.isTouchExplorationEnabled() : false;
        }
    }

    public final boolean isBouncerShowing() {
        return this.isBouncerShowing;
    }

    public final boolean isDefaultTheme() {
        return this.isDefaultTheme;
    }

    public final boolean isGxzwLowPosition() {
        return Intrinsics.areEqual("low", this.GXZW_POSITION);
    }

    public final boolean isInAppExiting() {
        return this.isInAppExiting;
    }

    public final boolean isInAppOpening() {
        return this.isInAppOpening;
    }

    public final boolean isKeyguardGoingAway() {
        return this.isKeyguardGoingAway;
    }

    public final boolean isKeyguardShowing() {
        return this.isKeyguardShowing;
    }

    public final boolean isLeftShortcutApp(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return this.shortcutDataLeft.isCurrentApp(packageName);
    }

    public final boolean isOccluded() {
        return this.isOccluded;
    }

    public final boolean isOccludedMovedShortcutLeft() {
        return this.occludedMovedShortcut == MiuiShortcutManager.OccludedMovedShortcut.LEFT;
    }

    public final boolean isOccludedMovedShortcutRight() {
        return this.occludedMovedShortcut == MiuiShortcutManager.OccludedMovedShortcut.RIGHT;
    }

    public final boolean isRightShortcutApp(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return this.shortcutDataRight.isCurrentApp(packageName);
    }

    public final void onConfigurationChanged(Configuration newConfiguration) {
        Intrinsics.checkNotNullParameter(newConfiguration, "newConfiguration");
        EnvStateManager.markEnvStateDirty(this.context);
        int iUpdateFrom = this.configuration.updateFrom(newConfiguration);
        if ((iUpdateFrom & 4) != 0) {
            onLanguageChanged();
        }
        boolean z = (iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0;
        boolean z2 = (iUpdateFrom & 1073741824) != 0;
        if (z || z2) {
            onDensityOrFontScaleChanged();
        }
    }

    public final void onDensityOrFontScaleChanged() {
        LogUtils.logI$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onDensityOrFontScaleChanged", null, 4, null);
        ShortcutRepository shortcutRepository = this.shortcutRepository;
        if (shortcutRepository != null) {
            shortcutRepository.updateDrawable();
        }
    }

    public final void onFashionGalleryConfigUpdate(int i, boolean z, String packageName) {
        ShortcutRepository shortcutRepository;
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (this.userId == i && Intrinsics.areEqual(packageName, "com.miui.android.fashiongallery") && (shortcutRepository = this.shortcutRepository) != null) {
            ShortcutEntity shortcutEntity = (ShortcutEntity) shortcutRepository.getLeftShortcut().getValue();
            ShortcutEntity shortcutEntity2 = (ShortcutEntity) shortcutRepository.getRightShortcut().getValue();
            if ((!shortcutEntity.isFashionGallery() || shortcutEntity.isAvailable() == z) && (!shortcutEntity2.isFashionGallery() || shortcutEntity2.isAvailable() == z)) {
                return;
            }
            LogUtils.logD$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onFashionGalleryConfigUpdate updateAvailable userId:" + i + ", isAvailable:" + z, null, 4, null);
            shortcutRepository.updateAvailable();
        }
    }

    public final void onLanguageChanged() {
        LogUtils.logI$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onLanguageChanged", null, 4, null);
        ShortcutRepository shortcutRepository = this.shortcutRepository;
        if (shortcutRepository != null) {
            shortcutRepository.updateName();
        }
    }

    public final void onRegionChanged() {
        LogUtils.logI$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onRegionChanged", null, 4, null);
        ShortcutRepository shortcutRepository = this.shortcutRepository;
        if (shortcutRepository != null) {
            shortcutRepository.updateAvailable();
        }
    }

    public final void onShortcutChanged(ShortcutEntity shortcutEntity, boolean z, boolean z2) {
        LogUtils.logI$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onShortcutChanged isLeft:" + z + ", shortcut:" + shortcutEntity + ", isFirstLoad:" + z2, null, 4, null);
        if (z) {
            this.shortcutDataLeft = shortcutEntity;
        } else {
            this.shortcutDataRight = shortcutEntity;
        }
        ShortcutOverlayManager shortcutOverlayManager = this.shortcutOverlayManager;
        if (shortcutOverlayManager != null) {
            shortcutOverlayManager.attachShortcut(shortcutEntity, z);
        }
        Iterator it = this.shortcutDataCallbacks.iterator();
        while (it.hasNext()) {
            ((MiuiShortcutManager.ShortcutDataCallback) it.next()).onShortcutDataChanged(shortcutEntity, z);
        }
        ShortcutWhiteListManager shortcutWhiteListManager = this.shortcutWhiteListManager;
        if (shortcutWhiteListManager != null && this.isSystemUIProcess && z2 && shortcutWhiteListManager.isWhiteListLoaded()) {
            clearShortcutDatabaseWhenWhiteListChanged(shortcutEntity, z, shortcutWhiteListManager.getWhiteList());
        }
    }

    public final void onShortcutOverlayChanged(String str, ShortcutEntity shortcutEntity, boolean z) {
        CoroutineScope coroutineScope;
        ShortcutEntity shortcutEntity2 = z ? this.shortcutDataLeft : this.shortcutDataRight;
        if (!shortcutEntity2.isEmptyOrUnknown() && Intrinsics.areEqual(str, shortcutEntity2.getUniqueTag())) {
            Context context = this.userContext;
            if (context == null) {
                context = this.context;
            }
            if (shortcutEntity == null) {
                if (shortcutEntity2.isOverlay()) {
                    LogUtils.logD$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onShortcutOverlayChanged overlay removed, should restore to default or cleared, currentShortcut: " + shortcutEntity2 + ", isLeft: " + z, null, 4, null);
                    if (context == null || (coroutineScope = this.coroutineScope) == null) {
                        return;
                    }
                    BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new ShortcutManager$onShortcutOverlayChanged$1$1(context, shortcutEntity2, z, this, null), 3, null);
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(str, shortcutEntity.getUniqueTag())) {
                if (shortcutEntity2.isSameShortcutFingerprint(shortcutEntity)) {
                    LogUtils.logD$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onShortcutOverlayChanged overlay not changed, isLeft: " + z + ", overlayShortcut: " + shortcutEntity, null, 4, null);
                    return;
                }
                Context context2 = context;
                ShortcutEntity shortcutEntityCopy = shortcutEntity.copy((32767 & 1) != 0 ? shortcutEntity.index : 0, (32767 & 2) != 0 ? shortcutEntity.packageName : null, (32767 & 4) != 0 ? shortcutEntity.nameId : null, (32767 & 8) != 0 ? shortcutEntity.action : null, (32767 & 16) != 0 ? shortcutEntity.data : null, (32767 & 32) != 0 ? shortcutEntity.iconId : null, (32767 & 64) != 0 ? shortcutEntity.tag : null, (32767 & 128) != 0 ? shortcutEntity.targetClass : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? shortcutEntity.targetPackage : null, (32767 & 512) != 0 ? shortcutEntity.isSystemDefault : false, (32767 & 1024) != 0 ? shortcutEntity.isOverlay : false, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? shortcutEntity.shortcutName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? shortcutEntity.shortcutTrackName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? shortcutEntity.drawable : null, (32767 & 16384) != 0 ? shortcutEntity.isAvailable : false);
                shortcutEntityCopy.setSystemDefault(shortcutEntity2.isSystemDefault());
                LogUtils.logD$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onShortcutOverlayChanged overlay changed, isLeft: " + z + ", newShortcut: " + shortcutEntityCopy, null, 4, null);
                CoroutineScope coroutineScope2 = this.coroutineScope;
                if (coroutineScope2 != null) {
                    BuildersKt__Builders_commonKt.launch$default(coroutineScope2, null, null, new C01632(context2, z, shortcutEntityCopy, null), 3, null);
                }
            }
        }
    }

    public final void onUserUnlocked() {
        LogUtils.logI$default(LogUtils.INSTANCE, "MiuiShortcutManager", "onUserUnlocked", null, 4, null);
        ShortcutRepository shortcutRepository = this.shortcutRepository;
        if (shortcutRepository != null) {
            shortcutRepository.updateAvailable();
        }
    }

    public final void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("miui.intent.action.MIUI_REGION_CHANGED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        Context context = this.context;
        if (context != null) {
            context.registerReceiver(this.broadcastReceiver, intentFilter, 2);
        }
    }

    public final void release() {
        ShortcutOverlayManager shortcutOverlayManager = this.shortcutOverlayManager;
        if (shortcutOverlayManager != null) {
            shortcutOverlayManager.release();
        }
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope != null) {
            CoroutineScopeKt.cancel$default(coroutineScope, null, 1, null);
        }
        LogUtils.logD$default(LogUtils.INSTANCE, "MiuiShortcutManager", "release, context=" + this.context, null, 4, null);
        if (this.context != null) {
            unregisterBroadcastReceiver();
            PackageChangedManager packageChangedManager = this.packageChangedManager;
            if (packageChangedManager != null) {
                packageChangedManager.removeCallback(this.packageChangedCallback);
                packageChangedManager.release();
                this.packageChangedManager = null;
            }
            ShortcutWhiteListManager shortcutWhiteListManager = this.shortcutWhiteListManager;
            if (shortcutWhiteListManager != null) {
                shortcutWhiteListManager.removeCallback(this.whiteListChangedCallback);
                shortcutWhiteListManager.release();
                this.shortcutWhiteListManager = null;
            }
            this.accessibilityManager = null;
            this.context = null;
        }
    }

    public final void removePackageChangedCallback(MiuiShortcutManager.PackageChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.packageChangedCallbacks.remove(callback);
    }

    public final void removeShortDataCallback(MiuiShortcutManager.ShortcutDataCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.shortcutDataCallbacks.remove(callback);
    }

    public final void removeWhiteListChangedCallback(MiuiShortcutManager.WhiteListChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.whiteListChangedCallbacks.remove(callback);
    }

    public final void removeWindowIfNeed() {
        if (this.isKeyguardShowing || this.isInAppOpening || this.isInAppExiting) {
            return;
        }
        ShortcutWindowManager.INSTANCE.removeRootViewFromWindow();
    }

    public final void resetOccludedMovedShortcut() {
        this.occludedMovedShortcut = MiuiShortcutManager.OccludedMovedShortcut.NONE;
    }

    public final void setBouncerShowing(boolean z) {
        this.isBouncerShowing = z;
    }

    public final void setDefaultTheme(boolean z) {
        this.isDefaultTheme = z;
    }

    public final void setInAppExiting(boolean z) {
        this.isInAppExiting = z;
        if (z) {
            return;
        }
        removeWindowIfNeed();
    }

    public final void setInAppOpening(boolean z) {
        this.isInAppOpening = z;
        if (z) {
            return;
        }
        removeWindowIfNeed();
    }

    public final void setInteractive(boolean z) {
        this.interactive = z;
    }

    public final void setKeyguardGoingAway(boolean z) {
        this.isKeyguardGoingAway = z;
    }

    public final void setKeyguardShowing(boolean z) {
        this.isKeyguardShowing = z;
        if (z) {
            return;
        }
        removeWindowIfNeed();
    }

    public final void setOccluded(boolean z) {
        this.isOccluded = z;
    }

    public final void setOccludedMovedShortcut(boolean z) {
        this.occludedMovedShortcut = z ? MiuiShortcutManager.OccludedMovedShortcut.LEFT : MiuiShortcutManager.OccludedMovedShortcut.RIGHT;
    }

    public final boolean shouldRequestFaceAuth() {
        return (this.isInAppOpening || this.isInAppExiting || this.isOccluded || !this.isKeyguardShowing) ? false : true;
    }

    public final boolean shouldShowFingerprintOrFaceIcon() {
        if (this.interactive) {
            return (this.isInAppOpening || this.isInAppExiting) ? false : true;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0030  */
    public final boolean startShortcutActivity() {
        ShortcutEntity shortcutEntity;
        boolean zBooleanValue;
        int i = WhenMappings.$EnumSwitchMapping$0[this.occludedMovedShortcut.ordinal()];
        if (i != 1) {
            shortcutEntity = i != 2 ? null : this.shortcutDataRight;
        } else {
            shortcutEntity = this.shortcutDataLeft;
        }
        Context context = this.context;
        if (context == null) {
            zBooleanValue = false;
        } else {
            Boolean boolValueOf = shortcutEntity != null ? Boolean.valueOf(shortcutEntity.startActivity(context)) : null;
            if (boolValueOf != null) {
                zBooleanValue = boolValueOf.booleanValue();
            } else {
                zBooleanValue = false;
            }
        }
        ShortcutAnalyticManager.INSTANCE.recordWhenMoveToOpen(this.context, zBooleanValue, this.occludedMovedShortcut == MiuiShortcutManager.OccludedMovedShortcut.LEFT, shortcutEntity != null ? shortcutEntity.getShortcutTrackName() : null);
        return zBooleanValue;
    }

    public final void unregisterBroadcastReceiver() {
        Context context = this.context;
        if (context != null) {
            context.unregisterReceiver(this.broadcastReceiver);
        }
    }

    public final void updateShortcutDatabaseIfNeed(ShortcutEntity shortcutEntity, boolean z, List list, String str) {
        Context context;
        Context context2;
        ArrayList<ShortcutEntity> arrayList = new ArrayList();
        for (Object obj : list) {
            if (Intrinsics.areEqual(((ShortcutEntity) obj).getPackageName(), shortcutEntity.getPackageName())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        boolean z2 = false;
        boolean z3 = false;
        for (ShortcutEntity shortcutEntity2 : arrayList) {
            if (Intrinsics.areEqual(shortcutEntity2.getTag(), shortcutEntity.getTag())) {
                if (!shortcutEntity2.isSameShortcut(shortcutEntity) && (context2 = this.context) != null) {
                    DataUtils.INSTANCE.saveShortcutDataToSettings(context2, z, shortcutEntity2, str);
                }
                z3 = true;
            }
            arrayList2.add(Unit.INSTANCE);
            z2 = true;
        }
        if (!z2 || z3 || (context = this.context) == null) {
            return;
        }
        DataUtils dataUtils = DataUtils.INSTANCE;
        dataUtils.saveShortcutDataToSettings(context, z, dataUtils.getEMPTY_ENTITY(), str);
    }

    public final void updateShortcutsDatabase(List list, String str) {
        updateShortcutDatabaseIfNeed(this.shortcutDataLeft, true, list, str);
        updateShortcutDatabaseIfNeed(this.shortcutDataRight, false, list, str);
    }
}

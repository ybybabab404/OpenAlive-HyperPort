package com.miui.keyguard.shortcuts.manager;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

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
/* JADX INFO: compiled from: ShortcutOverlayManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutOverlayManager {
    public static final Companion Companion = new Companion(null);
    public final MutableStateFlow _shortcutLeftOverlayFlow;
    public final MutableStateFlow _shortcutRightOverlayFlow;
    public ShortcutEntity attachedLeftShortcut;
    public ShortcutEntity attachedRightShortcut;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final ShortcutOverlayRepositoryImpl overlayRepository;
    public final StateFlow shortcutLeftOverlayFlow;
    public final ShortcutOverlayManager$shortcutObserver$1 shortcutObserver;
    public final StateFlow shortcutRightOverlayFlow;

    /* JADX INFO: compiled from: ShortcutOverlayManager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$attachShortcut$1, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutOverlayManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$attachShortcut$1", f = "ShortcutOverlayManager.kt", l = {103}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Ref.BooleanRef $forceNotify;
        public final /* synthetic */ boolean $isLeft;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, Ref.BooleanRef booleanRef, Continuation continuation) {
            super(2, continuation);
            this.$isLeft = z;
            this.$forceNotify = booleanRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutOverlayManager.this.new AnonymousClass1(this.$isLeft, this.$forceNotify, continuation);
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
                ShortcutOverlayManager shortcutOverlayManager = ShortcutOverlayManager.this;
                boolean z = this.$isLeft;
                boolean z2 = this.$forceNotify.element;
                this.label = 1;
                if (shortcutOverlayManager.updateShortcutOverlayFromRemote(z, z2, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$updateShortcutOverlayFromRemote$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutOverlayManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager", f = "ShortcutOverlayManager.kt", l = {61}, m = "updateShortcutOverlayFromRemote")
    public static final class C01641 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public boolean Z$0;
        public boolean Z$1;
        public int label;
        public /* synthetic */ Object result;

        public C01641(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutOverlayManager.this.updateShortcutOverlayFromRemote(false, false, this);
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$shortcutObserver$1] */
    public ShortcutOverlayManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.overlayRepository = new ShortcutOverlayRepositoryImpl(context);
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain().getImmediate());
        DataUtils dataUtils = DataUtils.INSTANCE;
        this.attachedLeftShortcut = dataUtils.getUNKNOWN_ENTITY();
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(new Pair("empty_unique_tag", dataUtils.getUNKNOWN_ENTITY()));
        this._shortcutLeftOverlayFlow = MutableStateFlow;
        this.shortcutLeftOverlayFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.attachedRightShortcut = dataUtils.getUNKNOWN_ENTITY();
        MutableStateFlow MutableStateFlow2 = StateFlowKt.MutableStateFlow(new Pair("empty_unique_tag", dataUtils.getUNKNOWN_ENTITY()));
        this._shortcutRightOverlayFlow = MutableStateFlow2;
        this.shortcutRightOverlayFlow = FlowKt.asStateFlow(MutableStateFlow2);
        final Handler handler = new Handler(Looper.getMainLooper());
        this.shortcutObserver = new ContentObserver(handler) { // from class: com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$shortcutObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                BuildersKt__Builders_commonKt.launch$default(this.this$0.coroutineScope, null, null, new ShortcutOverlayManager$shortcutObserver$1$onChange$1(this.this$0, null), 3, null);
            }
        };
    }

    public static /* synthetic */ Object updateShortcutOverlayFromRemote$default(ShortcutOverlayManager shortcutOverlayManager, boolean z, boolean z2, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = false;
        }
        return shortcutOverlayManager.updateShortcutOverlayFromRemote(z, z2, continuation);
    }

    public final void attachShortcut(ShortcutEntity shortcut, boolean z) {
        Intrinsics.checkNotNullParameter(shortcut, "shortcut");
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        if (z && shortcut.isUniqueTagSame(this.attachedLeftShortcut)) {
            if (shortcut.isOverlay() == this.attachedLeftShortcut.isOverlay()) {
                return;
            } else {
                booleanRef.element = true;
            }
        }
        if (!z && shortcut.isUniqueTagSame(this.attachedRightShortcut)) {
            if (shortcut.isOverlay() == this.attachedRightShortcut.isOverlay()) {
                return;
            } else {
                booleanRef.element = true;
            }
        }
        LogUtils.logI$default(LogUtils.INSTANCE, "ShortcutOverlayManager", "attachShortcut shortcut:" + shortcut + ", isLeft: " + z + ", forceNotify:" + booleanRef.element, null, 4, null);
        if (z) {
            this.attachedLeftShortcut = shortcut;
        } else {
            this.attachedRightShortcut = shortcut;
        }
        BuildersKt__Builders_commonKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(z, booleanRef, null), 3, null);
    }

    public final Object deleteShortcuts(String str, Continuation continuation) {
        Object objDeleteShortcuts = this.overlayRepository.deleteShortcuts(str, continuation);
        return objDeleteShortcuts == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDeleteShortcuts : Unit.INSTANCE;
    }

    public final StateFlow getShortcutLeftOverlayFlow() {
        return this.shortcutLeftOverlayFlow;
    }

    public final StateFlow getShortcutRightOverlayFlow() {
        return this.shortcutRightOverlayFlow;
    }

    public final void init() {
        try {
            this.context.getContentResolver().registerContentObserver(ShortcutOverlayRepositoryImpl.Companion.getCONTENT_URI(), true, this.shortcutObserver);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutOverlayManager", "registerContentObserver error, " + e.getMessage(), null, 4, null);
        }
    }

    public final void release() {
        try {
            this.context.getContentResolver().unregisterContentObserver(this.shortcutObserver);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutOverlayManager", "unregisterContentObserver error, " + e.getMessage(), null, 4, null);
        }
        CoroutineScopeKt.cancel$default(this.coroutineScope, null, 1, null);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object updateShortcutOverlayFromRemote(boolean z, boolean z2, Continuation continuation) {
        C01641 c01641;
        String uniqueTag;
        MutableStateFlow mutableStateFlow;
        if (continuation instanceof C01641) {
            c01641 = (C01641) continuation;
            int i = c01641.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01641.label = i - Integer.MIN_VALUE;
            } else {
                c01641 = new C01641(continuation);
            }
        } else {
            c01641 = new C01641(continuation);
        }
        Object shortcut = c01641.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01641.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(shortcut);
            ShortcutEntity shortcutEntity = z ? this.attachedLeftShortcut : this.attachedRightShortcut;
            MutableStateFlow mutableStateFlow2 = z ? this._shortcutLeftOverlayFlow : this._shortcutRightOverlayFlow;
            if (shortcutEntity.isEmptyOrUnknown()) {
                mutableStateFlow2.setValue(new Pair(shortcutEntity.getUniqueTag(), null));
                return Unit.INSTANCE;
            }
            uniqueTag = shortcutEntity.getUniqueTag();
            ShortcutOverlayRepositoryImpl shortcutOverlayRepositoryImpl = this.overlayRepository;
            String packageName = shortcutEntity.getPackageName();
            String tag = shortcutEntity.getTag();
            c01641.L$0 = this;
            c01641.L$1 = mutableStateFlow2;
            c01641.L$2 = uniqueTag;
            c01641.Z$0 = z;
            c01641.Z$1 = z2;
            c01641.label = 1;
            shortcut = shortcutOverlayRepositoryImpl.getShortcut(packageName, tag, c01641);
            if (shortcut == coroutine_suspended) {
                return coroutine_suspended;
            }
            mutableStateFlow = mutableStateFlow2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z2 = c01641.Z$1;
            z = c01641.Z$0;
            String str = (String) c01641.L$2;
            mutableStateFlow = (MutableStateFlow) c01641.L$1;
            ShortcutOverlayManager shortcutOverlayManager = (ShortcutOverlayManager) c01641.L$0;
            ResultKt.throwOnFailure(shortcut);
            uniqueTag = str;
            this = shortcutOverlayManager;
        }
        ShortcutEntity shortcutEntity2 = (ShortcutEntity) shortcut;
        if (Intrinsics.areEqual(uniqueTag, (z ? this.attachedLeftShortcut : this.attachedRightShortcut).getUniqueTag())) {
            if (z2) {
                mutableStateFlow.setValue(new Pair(DataUtils.INSTANCE.getUNKNOWN_ENTITY().getUniqueTag(), null));
            }
            mutableStateFlow.setValue(new Pair(uniqueTag, shortcutEntity2));
        }
        return Unit.INSTANCE;
    }
}

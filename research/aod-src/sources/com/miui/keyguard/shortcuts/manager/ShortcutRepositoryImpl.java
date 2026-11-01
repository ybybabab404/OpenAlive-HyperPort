package com.miui.keyguard.shortcuts.manager;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseBooleanArray;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.maml.R;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

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
/* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ShortcutRepositoryImpl implements ShortcutRepository {
    public static final Companion Companion = new Companion(null);
    public final MutableStateFlow _leftShortcut;
    public final MutableStateFlow _rightShortcut;
    public final Context context;
    public final boolean isSystemUiProcess;
    public final Mutex leftMutex;
    public final SparseBooleanArray leftPendingTasks;
    public final StateFlow leftShortcut;
    public final Mutex rightMutex;
    public final SparseBooleanArray rightPendingTasks;
    public final StateFlow rightShortcut;
    public final CoroutineScope scope;

    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$executePendingTasks$1, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$executePendingTasks$1", f = "ShortcutRepositoryImpl.kt", l = {222}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isLeft;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isLeft = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new AnonymousClass1(this.$isLeft, continuation);
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
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                boolean z = this.$isLeft;
                this.label = 1;
                if (shortcutRepositoryImpl.updateAvailable(z, true, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$executePendingTasks$2, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$executePendingTasks$2", f = "ShortcutRepositoryImpl.kt", l = {227}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isLeft;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isLeft = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new AnonymousClass2(this.$isLeft, continuation);
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
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                boolean z = this.$isLeft;
                this.label = 1;
                if (shortcutRepositoryImpl.updateDrawable(z, true, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$executePendingTasks$3, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$executePendingTasks$3", f = "ShortcutRepositoryImpl.kt", l = {232}, m = "invokeSuspend")
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isLeft;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isLeft = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new AnonymousClass3(this.$isLeft, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                boolean z = this.$isLeft;
                this.label = 1;
                if (shortcutRepositoryImpl.updateName(z, true, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observe$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observe$1", f = "ShortcutRepositoryImpl.kt", l = {195}, m = "invokeSuspend")
    public static final class C01651 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01651(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01651(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01651) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowObserveFlow = ShortcutRepositoryImpl.this.observeFlow(true);
                final ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl.observe.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(ShortcutEntity shortcutEntity, Continuation continuation) {
                        shortcutRepositoryImpl._leftShortcut.setValue(shortcutEntity);
                        if (!shortcutEntity.isUnknown()) {
                            shortcutRepositoryImpl.executePendingTasks(true);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowObserveFlow.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observe$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observe$2", f = "ShortcutRepositoryImpl.kt", l = {203}, m = "invokeSuspend")
    public static final class C01662 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01662(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01662(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01662) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowObserveFlow = ShortcutRepositoryImpl.this.observeFlow(false);
                final ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl.observe.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(ShortcutEntity shortcutEntity, Continuation continuation) {
                        shortcutRepositoryImpl._rightShortcut.setValue(shortcutEntity);
                        if (!shortcutEntity.isUnknown()) {
                            shortcutRepositoryImpl.executePendingTasks(false);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowObserveFlow.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observeFlow$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observeFlow$1", f = "ShortcutRepositoryImpl.kt", l = {303}, m = "invokeSuspend")
    public static final class C01671 extends SuspendLambda implements Function2<ProducerScope<? super ShortcutEntity>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isLeft;
        public /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ ShortcutRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01671(boolean z, ShortcutRepositoryImpl shortcutRepositoryImpl, Continuation continuation) {
            super(2, continuation);
            this.$isLeft = z;
            this.this$0 = shortcutRepositoryImpl;
        }

        public static final Unit invokeSuspend$lambda$0(ShortcutRepositoryImpl shortcutRepositoryImpl, ShortcutRepositoryImpl$observeFlow$1$contentObserver$1 shortcutRepositoryImpl$observeFlow$1$contentObserver$1) {
            shortcutRepositoryImpl.unregisterObserver(shortcutRepositoryImpl$observeFlow$1$contentObserver$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01671 c01671 = new C01671(this.$isLeft, this.this$0, continuation);
            c01671.L$0 = obj;
            return c01671;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((C01671) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ProducerScope producerScope = (ProducerScope) this.L$0;
                final ShortcutRepositoryImpl$observeFlow$1$contentObserver$1 shortcutRepositoryImpl$observeFlow$1$contentObserver$1 = new ShortcutRepositoryImpl$observeFlow$1$contentObserver$1(this.this$0, this.$isLeft, producerScope, new Handler(Looper.getMainLooper()));
                this.this$0.registerObserver(this.$isLeft ? DataUtils.INSTANCE.getSHORTCUT_DATA_LEFT_URI() : DataUtils.INSTANCE.getSHORTCUT_DATA_RIGHT_URI(), shortcutRepositoryImpl$observeFlow$1$contentObserver$1);
                final ShortcutRepositoryImpl shortcutRepositoryImpl = this.this$0;
                Function0 function0 = new Function0() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observeFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return ShortcutRepositoryImpl.C01671.invokeSuspend$lambda$0(shortcutRepositoryImpl, shortcutRepositoryImpl$observeFlow$1$contentObserver$1);
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateAvailable$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateAvailable$1", f = "ShortcutRepositoryImpl.kt", l = {49}, m = "invokeSuspend")
    public static final class C01681 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01681(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01681(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01681) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                this.label = 1;
                if (ShortcutRepositoryImpl.updateAvailable$default(shortcutRepositoryImpl, true, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateAvailable$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateAvailable$2", f = "ShortcutRepositoryImpl.kt", l = {52}, m = "invokeSuspend")
    public static final class C01692 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01692(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01692(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01692) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                this.label = 1;
                if (ShortcutRepositoryImpl.updateAvailable$default(shortcutRepositoryImpl, false, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateAvailable$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl", f = "ShortcutRepositoryImpl.kt", l = {382, R.styleable.AppCompatTheme_imageButtonStyle}, m = "updateAvailable")
    public static final class C01703 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public boolean Z$0;
        public boolean Z$1;
        public int label;
        public /* synthetic */ Object result;

        public C01703(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutRepositoryImpl.this.updateAvailable(false, false, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateDrawable$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateDrawable$1", f = "ShortcutRepositoryImpl.kt", l = {100}, m = "invokeSuspend")
    public static final class C01711 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01711(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01711(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                this.label = 1;
                if (ShortcutRepositoryImpl.updateDrawable$default(shortcutRepositoryImpl, true, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateDrawable$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateDrawable$2", f = "ShortcutRepositoryImpl.kt", l = {103}, m = "invokeSuspend")
    public static final class C01722 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01722(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01722(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01722) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                this.label = 1;
                if (ShortcutRepositoryImpl.updateDrawable$default(shortcutRepositoryImpl, false, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateDrawable$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl", f = "ShortcutRepositoryImpl.kt", l = {R.styleable.AppCompatTheme_windowFixedHeightMinor}, m = "updateDrawable")
    public static final class C01733 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public boolean Z$0;
        public int label;
        public /* synthetic */ Object result;

        public C01733(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutRepositoryImpl.this.updateDrawable(false, false, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateName$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateName$1", f = "ShortcutRepositoryImpl.kt", l = {144}, m = "invokeSuspend")
    public static final class C01741 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01741(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01741(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                this.label = 1;
                if (ShortcutRepositoryImpl.updateName$default(shortcutRepositoryImpl, true, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateName$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateName$2", f = "ShortcutRepositoryImpl.kt", l = {147}, m = "invokeSuspend")
    public static final class C01752 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01752(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutRepositoryImpl.this.new C01752(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01752) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutRepositoryImpl shortcutRepositoryImpl = ShortcutRepositoryImpl.this;
                this.label = 1;
                if (ShortcutRepositoryImpl.updateName$default(shortcutRepositoryImpl, false, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$updateName$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl", f = "ShortcutRepositoryImpl.kt", l = {165}, m = "updateName")
    public static final class C01763 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public boolean Z$0;
        public int label;
        public /* synthetic */ Object result;

        public C01763(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutRepositoryImpl.this.updateName(false, false, this);
        }
    }

    public ShortcutRepositoryImpl(Context context, CoroutineScope scope, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.context = context;
        this.scope = scope;
        this.isSystemUiProcess = z;
        DataUtils dataUtils = DataUtils.INSTANCE;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(dataUtils.getUNKNOWN_ENTITY());
        this._leftShortcut = MutableStateFlow;
        MutableStateFlow MutableStateFlow2 = StateFlowKt.MutableStateFlow(dataUtils.getUNKNOWN_ENTITY());
        this._rightShortcut = MutableStateFlow2;
        this.leftMutex = MutexKt.Mutex$default(false, 1, null);
        this.rightMutex = MutexKt.Mutex$default(false, 1, null);
        this.leftPendingTasks = new SparseBooleanArray();
        this.rightPendingTasks = new SparseBooleanArray();
        this.leftShortcut = FlowKt.asStateFlow(MutableStateFlow);
        this.rightShortcut = FlowKt.asStateFlow(MutableStateFlow2);
    }

    public static /* synthetic */ Object updateAvailable$default(ShortcutRepositoryImpl shortcutRepositoryImpl, boolean z, boolean z2, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = false;
        }
        return shortcutRepositoryImpl.updateAvailable(z, z2, continuation);
    }

    public static /* synthetic */ Object updateDrawable$default(ShortcutRepositoryImpl shortcutRepositoryImpl, boolean z, boolean z2, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = false;
        }
        return shortcutRepositoryImpl.updateDrawable(z, z2, continuation);
    }

    public static /* synthetic */ Object updateName$default(ShortcutRepositoryImpl shortcutRepositoryImpl, boolean z, boolean z2, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = false;
        }
        return shortcutRepositoryImpl.updateName(z, z2, continuation);
    }

    public final void executePendingTasks(boolean z) {
        SparseBooleanArray sparseBooleanArray = z ? this.leftPendingTasks : this.rightPendingTasks;
        if (sparseBooleanArray.size() != 0) {
            int iKeyAt = sparseBooleanArray.keyAt(0);
            sparseBooleanArray.delete(iKeyAt);
            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutRepositoryImpl", "executePendingTasks isLeft:" + z + ", taskType:" + iKeyAt, null, 4, null);
            if (iKeyAt == 1) {
                BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass1(z, null), 3, null);
            } else if (iKeyAt == 2) {
                BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass2(z, null), 3, null);
            } else if (iKeyAt == 3) {
                BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass3(z, null), 3, null);
            }
            executePendingTasks(z);
        }
    }

    @Override // com.miui.keyguard.shortcuts.manager.ShortcutRepository
    public StateFlow getLeftShortcut() {
        return this.leftShortcut;
    }

    @Override // com.miui.keyguard.shortcuts.manager.ShortcutRepository
    public StateFlow getRightShortcut() {
        return this.rightShortcut;
    }

    public final void observe() {
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01651(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01662(null), 3, null);
    }

    public final Flow observeFlow(boolean z) {
        return FlowKt.callbackFlow(new C01671(z, this, null));
    }

    public final void registerObserver(Uri uri, ContentObserver contentObserver) {
        UserHandleUtils.registerContentObserverForUser(this.context, uri, false, contentObserver, UserHandleUtils.USER_CURRENT);
        contentObserver.onChange(false);
    }

    public final void unregisterObserver(ContentObserver contentObserver) {
        UserHandleUtils.unregisterContentObserverForUser(this.context, contentObserver);
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00db A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:50:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:55:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:57:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:59:0x0118  */
    /* JADX WARN: Code duplicated, block: B:60:0x011e  */
    /* JADX WARN: Code duplicated, block: B:61:0x0124  */
    /* JADX WARN: Code duplicated, block: B:7:0x0019  */
    /* JADX WARN: Instruction removed from duplicated block: B:61:0x0124, please report this as an issue */
    public final Object updateAvailable(boolean z, boolean z2, Continuation continuation) throws Throwable {
        C01703 c01703;
        ShortcutEntity shortcutEntity;
        boolean zIsAvailable;
        Mutex mutex;
        Mutex mutex2;
        ShortcutRepositoryImpl shortcutRepositoryImpl;
        boolean z3;
        ShortcutEntity shortcutEntity2;
        boolean zBooleanValue;
        MutableStateFlow mutableStateFlow;
        ShortcutEntity shortcutEntity3;
        ShortcutEntity shortcutEntityCopy;
        ShortcutRepositoryImpl shortcutRepositoryImpl2 = this;
        boolean z4 = z;
        if (continuation instanceof C01703) {
            c01703 = (C01703) continuation;
            int i = c01703.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01703.label = i - Integer.MIN_VALUE;
            } else {
                c01703 = shortcutRepositoryImpl2.new C01703(continuation);
            }
        } else {
            c01703 = shortcutRepositoryImpl2.new C01703(continuation);
        }
        Object obj = c01703.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01703.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                shortcutEntity = (ShortcutEntity) (z4 ? shortcutRepositoryImpl2._leftShortcut : shortcutRepositoryImpl2._rightShortcut).getValue();
                if (shortcutEntity.isEmptyOrUnknown()) {
                    if (!z2 && shortcutEntity.isUnknown()) {
                        if (z4) {
                            shortcutRepositoryImpl2.leftPendingTasks.put(1, true);
                        } else {
                            shortcutRepositoryImpl2.rightPendingTasks.put(1, true);
                        }
                    }
                    return Unit.INSTANCE;
                }
                zIsAvailable = shortcutEntity.isAvailable();
                mutex = z4 ? shortcutRepositoryImpl2.leftMutex : shortcutRepositoryImpl2.rightMutex;
                c01703.L$0 = shortcutRepositoryImpl2;
                c01703.L$1 = shortcutEntity;
                c01703.L$2 = mutex;
                c01703.Z$0 = z4;
                c01703.Z$1 = zIsAvailable;
                c01703.label = 1;
                if (mutex.lock(null, c01703) != coroutine_suspended) {
                }
                return coroutine_suspended;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                z3 = c01703.Z$1;
                z4 = c01703.Z$0;
                mutex2 = (Mutex) c01703.L$2;
                shortcutEntity2 = (ShortcutEntity) c01703.L$1;
                shortcutRepositoryImpl = (ShortcutRepositoryImpl) c01703.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    zBooleanValue = ((Boolean) obj).booleanValue();
                    mutex2.unlock(null);
                    if (z3 != zBooleanValue) {
                        if (z4) {
                            mutableStateFlow = shortcutRepositoryImpl._leftShortcut;
                        } else {
                            mutableStateFlow = shortcutRepositoryImpl._rightShortcut;
                        }
                        shortcutEntity3 = (ShortcutEntity) mutableStateFlow.getValue();
                        if (shortcutEntity3.isUniqueTagSame(shortcutEntity2)) {
                            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutRepositoryImpl", "updateAvailable return, not same shortcut. isLeft:" + z4 + " old:" + shortcutEntity2 + ", new:" + shortcutEntity3, null, 4, null);
                        } else if (shortcutEntity3.isAvailable() != zBooleanValue) {
                            shortcutEntityCopy = shortcutEntity3.copy((32767 & 1) != 0 ? shortcutEntity3.index : 0, (32767 & 2) != 0 ? shortcutEntity3.packageName : null, (32767 & 4) != 0 ? shortcutEntity3.nameId : null, (32767 & 8) != 0 ? shortcutEntity3.action : null, (32767 & 16) != 0 ? shortcutEntity3.data : null, (32767 & 32) != 0 ? shortcutEntity3.iconId : null, (32767 & 64) != 0 ? shortcutEntity3.tag : null, (32767 & 128) != 0 ? shortcutEntity3.targetClass : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? shortcutEntity3.targetPackage : null, (32767 & 512) != 0 ? shortcutEntity3.isSystemDefault : false, (32767 & 1024) != 0 ? shortcutEntity3.isOverlay : false, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? shortcutEntity3.shortcutName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? shortcutEntity3.shortcutTrackName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? shortcutEntity3.drawable : null, (32767 & 16384) != 0 ? shortcutEntity3.isAvailable : false);
                            shortcutEntityCopy.setAvailable(zBooleanValue);
                            if (z4) {
                                shortcutRepositoryImpl._leftShortcut.setValue(shortcutEntityCopy);
                            } else {
                                shortcutRepositoryImpl._rightShortcut.setValue(shortcutEntityCopy);
                            }
                        }
                    }
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    th = th;
                    mutex2.unlock(null);
                    throw th;
                }
            }
            boolean z5 = c01703.Z$1;
            z4 = c01703.Z$0;
            Mutex mutex3 = (Mutex) c01703.L$2;
            ShortcutEntity shortcutEntity4 = (ShortcutEntity) c01703.L$1;
            ShortcutRepositoryImpl shortcutRepositoryImpl3 = (ShortcutRepositoryImpl) c01703.L$0;
            ResultKt.throwOnFailure(obj);
            zIsAvailable = z5;
            shortcutRepositoryImpl2 = shortcutRepositoryImpl3;
            mutex = mutex3;
            shortcutEntity = shortcutEntity4;
            Context context = shortcutRepositoryImpl2.context;
            c01703.L$0 = shortcutRepositoryImpl2;
            c01703.L$1 = shortcutEntity;
            c01703.L$2 = mutex;
            c01703.Z$0 = z4;
            c01703.Z$1 = zIsAvailable;
            c01703.label = 2;
            Object objIsNewAvailable = shortcutEntity.isNewAvailable(context, c01703);
            if (objIsNewAvailable != coroutine_suspended) {
                shortcutRepositoryImpl = shortcutRepositoryImpl2;
                z3 = zIsAvailable;
                shortcutEntity2 = shortcutEntity;
                obj = objIsNewAvailable;
                mutex2 = mutex;
                zBooleanValue = ((Boolean) obj).booleanValue();
                mutex2.unlock(null);
                if (z3 != zBooleanValue) {
                    if (z4) {
                        mutableStateFlow = shortcutRepositoryImpl._leftShortcut;
                    } else {
                        mutableStateFlow = shortcutRepositoryImpl._rightShortcut;
                    }
                    shortcutEntity3 = (ShortcutEntity) mutableStateFlow.getValue();
                    if (shortcutEntity3.isUniqueTagSame(shortcutEntity2)) {
                        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutRepositoryImpl", "updateAvailable return, not same shortcut. isLeft:" + z4 + " old:" + shortcutEntity2 + ", new:" + shortcutEntity3, null, 4, null);
                    } else if (shortcutEntity3.isAvailable() != zBooleanValue) {
                        shortcutEntityCopy = shortcutEntity3.copy((32767 & 1) != 0 ? shortcutEntity3.index : 0, (32767 & 2) != 0 ? shortcutEntity3.packageName : null, (32767 & 4) != 0 ? shortcutEntity3.nameId : null, (32767 & 8) != 0 ? shortcutEntity3.action : null, (32767 & 16) != 0 ? shortcutEntity3.data : null, (32767 & 32) != 0 ? shortcutEntity3.iconId : null, (32767 & 64) != 0 ? shortcutEntity3.tag : null, (32767 & 128) != 0 ? shortcutEntity3.targetClass : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? shortcutEntity3.targetPackage : null, (32767 & 512) != 0 ? shortcutEntity3.isSystemDefault : false, (32767 & 1024) != 0 ? shortcutEntity3.isOverlay : false, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? shortcutEntity3.shortcutName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? shortcutEntity3.shortcutTrackName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? shortcutEntity3.drawable : null, (32767 & 16384) != 0 ? shortcutEntity3.isAvailable : false);
                        shortcutEntityCopy.setAvailable(zBooleanValue);
                        if (z4) {
                            shortcutRepositoryImpl._leftShortcut.setValue(shortcutEntityCopy);
                        } else {
                            shortcutRepositoryImpl._rightShortcut.setValue(shortcutEntityCopy);
                        }
                    }
                }
                return Unit.INSTANCE;
            }
            return coroutine_suspended;
        } catch (Throwable th2) {
            th = th2;
            mutex2 = mutex;
            mutex2.unlock(null);
            throw th;
        }
    }

    @Override // com.miui.keyguard.shortcuts.manager.ShortcutRepository
    public void updateAvailable() {
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01681(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01692(null), 3, null);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0019  */
    public final Object updateDrawable(boolean z, boolean z2, Continuation continuation) {
        C01733 c01733;
        Drawable drawable;
        ShortcutEntity shortcutEntity;
        ShortcutRepositoryImpl shortcutRepositoryImpl = this;
        boolean z3 = z;
        if (continuation instanceof C01733) {
            c01733 = (C01733) continuation;
            int i = c01733.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01733.label = i - Integer.MIN_VALUE;
            } else {
                c01733 = shortcutRepositoryImpl.new C01733(continuation);
            }
        } else {
            c01733 = shortcutRepositoryImpl.new C01733(continuation);
        }
        Object obj = c01733.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01733.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ShortcutEntity shortcutEntity2 = (ShortcutEntity) (z3 ? shortcutRepositoryImpl._leftShortcut : shortcutRepositoryImpl._rightShortcut).getValue();
            if (shortcutEntity2.isEmptyOrUnknown()) {
                if (!z2 && shortcutEntity2.isUnknown()) {
                    if (z3) {
                        shortcutRepositoryImpl.leftPendingTasks.put(2, true);
                    } else {
                        shortcutRepositoryImpl.rightPendingTasks.put(2, true);
                    }
                }
                return Unit.INSTANCE;
            }
            drawable = shortcutEntity2.getDrawable();
            Context context = shortcutRepositoryImpl.context;
            c01733.L$0 = shortcutRepositoryImpl;
            c01733.L$1 = shortcutEntity2;
            c01733.L$2 = drawable;
            c01733.Z$0 = z3;
            c01733.label = 1;
            Object newDrawable = shortcutEntity2.getNewDrawable(context, c01733);
            if (newDrawable == coroutine_suspended) {
                return coroutine_suspended;
            }
            shortcutEntity = shortcutEntity2;
            obj = newDrawable;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            boolean z4 = c01733.Z$0;
            Drawable drawable2 = (Drawable) c01733.L$2;
            shortcutEntity = (ShortcutEntity) c01733.L$1;
            ShortcutRepositoryImpl shortcutRepositoryImpl2 = (ShortcutRepositoryImpl) c01733.L$0;
            ResultKt.throwOnFailure(obj);
            drawable = drawable2;
            z3 = z4;
            shortcutRepositoryImpl = shortcutRepositoryImpl2;
        }
        Drawable drawable3 = (Drawable) obj;
        if (!Intrinsics.areEqual(drawable3, drawable)) {
            ShortcutEntity shortcutEntity3 = (ShortcutEntity) (z3 ? shortcutRepositoryImpl._leftShortcut : shortcutRepositoryImpl._rightShortcut).getValue();
            if (!shortcutEntity3.isUniqueTagSame(shortcutEntity) || !Intrinsics.areEqual(shortcutEntity3.getIconId(), shortcutEntity.getIconId())) {
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutRepositoryImpl", "updateDrawable return, not same shortcut. isLeft:" + z3 + " old:" + shortcutEntity + ", new:" + shortcutEntity3, null, 4, null);
            } else if (!Intrinsics.areEqual(shortcutEntity3.getDrawable(), drawable3)) {
                ShortcutEntity shortcutEntityCopy = shortcutEntity3.copy((32767 & 1) != 0 ? shortcutEntity3.index : 0, (32767 & 2) != 0 ? shortcutEntity3.packageName : null, (32767 & 4) != 0 ? shortcutEntity3.nameId : null, (32767 & 8) != 0 ? shortcutEntity3.action : null, (32767 & 16) != 0 ? shortcutEntity3.data : null, (32767 & 32) != 0 ? shortcutEntity3.iconId : null, (32767 & 64) != 0 ? shortcutEntity3.tag : null, (32767 & 128) != 0 ? shortcutEntity3.targetClass : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? shortcutEntity3.targetPackage : null, (32767 & 512) != 0 ? shortcutEntity3.isSystemDefault : false, (32767 & 1024) != 0 ? shortcutEntity3.isOverlay : false, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? shortcutEntity3.shortcutName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? shortcutEntity3.shortcutTrackName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? shortcutEntity3.drawable : null, (32767 & 16384) != 0 ? shortcutEntity3.isAvailable : false);
                shortcutEntityCopy.setDrawable(drawable3);
                if (z3) {
                    shortcutRepositoryImpl._leftShortcut.setValue(shortcutEntityCopy);
                } else {
                    shortcutRepositoryImpl._rightShortcut.setValue(shortcutEntityCopy);
                }
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.miui.keyguard.shortcuts.manager.ShortcutRepository
    public void updateDrawable() {
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01711(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01722(null), 3, null);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0019  */
    public final Object updateName(boolean z, boolean z2, Continuation continuation) {
        C01763 c01763;
        String shortcutName;
        ShortcutEntity shortcutEntity;
        ShortcutRepositoryImpl shortcutRepositoryImpl = this;
        boolean z3 = z;
        if (continuation instanceof C01763) {
            c01763 = (C01763) continuation;
            int i = c01763.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01763.label = i - Integer.MIN_VALUE;
            } else {
                c01763 = shortcutRepositoryImpl.new C01763(continuation);
            }
        } else {
            c01763 = shortcutRepositoryImpl.new C01763(continuation);
        }
        Object obj = c01763.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01763.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ShortcutEntity shortcutEntity2 = (ShortcutEntity) (z3 ? shortcutRepositoryImpl._leftShortcut : shortcutRepositoryImpl._rightShortcut).getValue();
            if (shortcutEntity2.isEmptyOrUnknown()) {
                if (!z2 && shortcutEntity2.isUnknown()) {
                    if (z3) {
                        shortcutRepositoryImpl.leftPendingTasks.put(3, true);
                    } else {
                        shortcutRepositoryImpl.rightPendingTasks.put(3, true);
                    }
                }
                return Unit.INSTANCE;
            }
            shortcutName = shortcutEntity2.getShortcutName();
            Context context = shortcutRepositoryImpl.context;
            c01763.L$0 = shortcutRepositoryImpl;
            c01763.L$1 = shortcutEntity2;
            c01763.L$2 = shortcutName;
            c01763.Z$0 = z3;
            c01763.label = 1;
            Object newShortcutName = shortcutEntity2.getNewShortcutName(context, c01763);
            if (newShortcutName == coroutine_suspended) {
                return coroutine_suspended;
            }
            shortcutEntity = shortcutEntity2;
            obj = newShortcutName;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            boolean z4 = c01763.Z$0;
            String str = (String) c01763.L$2;
            shortcutEntity = (ShortcutEntity) c01763.L$1;
            ShortcutRepositoryImpl shortcutRepositoryImpl2 = (ShortcutRepositoryImpl) c01763.L$0;
            ResultKt.throwOnFailure(obj);
            shortcutName = str;
            z3 = z4;
            shortcutRepositoryImpl = shortcutRepositoryImpl2;
        }
        String str2 = (String) obj;
        if (!Intrinsics.areEqual(str2, shortcutName)) {
            ShortcutEntity shortcutEntity3 = (ShortcutEntity) (z3 ? shortcutRepositoryImpl._leftShortcut : shortcutRepositoryImpl._rightShortcut).getValue();
            if (shortcutEntity3.isUniqueTagSame(shortcutEntity) && Intrinsics.areEqual(shortcutEntity3.getNameId(), shortcutEntity.getNameId())) {
                LogUtils.logI$default(LogUtils.INSTANCE, "ShortcutRepositoryImpl", "updateName isLeft:" + z3 + ", currentShortcut=" + shortcutEntity3 + ", newShortcutName=" + str2, null, 4, null);
                if (!Intrinsics.areEqual(shortcutEntity3.getShortcutName(), str2)) {
                    ShortcutEntity shortcutEntityCopy = shortcutEntity3.copy((32767 & 1) != 0 ? shortcutEntity3.index : 0, (32767 & 2) != 0 ? shortcutEntity3.packageName : null, (32767 & 4) != 0 ? shortcutEntity3.nameId : null, (32767 & 8) != 0 ? shortcutEntity3.action : null, (32767 & 16) != 0 ? shortcutEntity3.data : null, (32767 & 32) != 0 ? shortcutEntity3.iconId : null, (32767 & 64) != 0 ? shortcutEntity3.tag : null, (32767 & 128) != 0 ? shortcutEntity3.targetClass : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? shortcutEntity3.targetPackage : null, (32767 & 512) != 0 ? shortcutEntity3.isSystemDefault : false, (32767 & 1024) != 0 ? shortcutEntity3.isOverlay : false, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? shortcutEntity3.shortcutName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? shortcutEntity3.shortcutTrackName : null, (32767 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? shortcutEntity3.drawable : null, (32767 & 16384) != 0 ? shortcutEntity3.isAvailable : false);
                    shortcutEntityCopy.setShortcutName(str2);
                    if (z3) {
                        shortcutRepositoryImpl._leftShortcut.setValue(shortcutEntityCopy);
                    } else {
                        shortcutRepositoryImpl._rightShortcut.setValue(shortcutEntityCopy);
                    }
                }
            } else {
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutRepositoryImpl", "updateName return, not same shortcut. isLeft:" + z3 + " old:" + shortcutEntity + ", new:" + shortcutEntity3, null, 4, null);
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.miui.keyguard.shortcuts.manager.ShortcutRepository
    public void updateName() {
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01741(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01752(null), 3, null);
    }

    public final ShortcutEntity updateShortcutWhenFirstLoad(ShortcutEntity shortcutEntity, boolean z) {
        DataUtils dataUtils;
        ShortcutEntity shortcutEntityFetchShortcutFromApp;
        if (shortcutEntity == null) {
            DataUtils dataUtils2 = DataUtils.INSTANCE;
            ShortcutEntity shortcutEntityBuildDefaultShortcutData = dataUtils2.buildDefaultShortcutData(this.context, z);
            dataUtils2.saveShortcutDataToSettings(this.context, z, shortcutEntityBuildDefaultShortcutData, "buildDefault");
            return shortcutEntityBuildDefaultShortcutData;
        }
        if (shortcutEntity.isEmptyOrUnknown()) {
            return shortcutEntity;
        }
        if (shortcutEntity.isOverlay() || (shortcutEntityFetchShortcutFromApp = (dataUtils = DataUtils.INSTANCE).fetchShortcutFromApp(this.context, shortcutEntity.getPackageName(), shortcutEntity.getTag(), true)) == null) {
            shortcutEntity.buildShortcutNameAndDrawable(this.context);
            shortcutEntity.updateAvailable(this.context);
            return shortcutEntity;
        }
        shortcutEntityFetchShortcutFromApp.setSystemDefault(shortcutEntity.isSystemDefault());
        if (!shortcutEntityFetchShortcutFromApp.isSameShortcutFingerprint(shortcutEntity)) {
            dataUtils.saveShortcutDataToSettings(this.context, z, shortcutEntityFetchShortcutFromApp, "update when first load");
        }
        return shortcutEntityFetchShortcutFromApp;
    }
}

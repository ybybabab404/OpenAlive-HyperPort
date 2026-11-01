package com.miui.keyguard.shortcuts.manager;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
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
/* JADX INFO: compiled from: ShortcutOverlayManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$shortcutObserver$1$onChange$1", f = "ShortcutOverlayManager.kt", l = {}, m = "invokeSuspend")
public final class ShortcutOverlayManager$shortcutObserver$1$onChange$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ ShortcutOverlayManager this$0;

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$shortcutObserver$1$onChange$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutOverlayManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$shortcutObserver$1$onChange$1$1", f = "ShortcutOverlayManager.kt", l = {44}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;
        public final /* synthetic */ ShortcutOverlayManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ShortcutOverlayManager shortcutOverlayManager, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shortcutOverlayManager;
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
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutOverlayManager shortcutOverlayManager = this.this$0;
                this.label = 1;
                if (ShortcutOverlayManager.updateShortcutOverlayFromRemote$default(shortcutOverlayManager, true, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$shortcutObserver$1$onChange$1$2, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutOverlayManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayManager$shortcutObserver$1$onChange$1$2", f = "ShortcutOverlayManager.kt", l = {47}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;
        public final /* synthetic */ ShortcutOverlayManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ShortcutOverlayManager shortcutOverlayManager, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shortcutOverlayManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
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
                ShortcutOverlayManager shortcutOverlayManager = this.this$0;
                this.label = 1;
                if (ShortcutOverlayManager.updateShortcutOverlayFromRemote$default(shortcutOverlayManager, false, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutOverlayManager$shortcutObserver$1$onChange$1(ShortcutOverlayManager shortcutOverlayManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutOverlayManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShortcutOverlayManager$shortcutObserver$1$onChange$1 shortcutOverlayManager$shortcutObserver$1$onChange$1 = new ShortcutOverlayManager$shortcutObserver$1$onChange$1(this.this$0, continuation);
        shortcutOverlayManager$shortcutObserver$1$onChange$1.L$0 = obj;
        return shortcutOverlayManager$shortcutObserver$1$onChange$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ShortcutOverlayManager$shortcutObserver$1$onChange$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3, null);
        return Unit.INSTANCE;
    }
}

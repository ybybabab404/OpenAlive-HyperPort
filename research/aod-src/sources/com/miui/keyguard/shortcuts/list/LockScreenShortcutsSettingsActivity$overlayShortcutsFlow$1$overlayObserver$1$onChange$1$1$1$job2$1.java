package com.miui.keyguard.shortcuts.list;

import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1$onChange$1$1$1$job2$1", f = "LockScreenShortcutsSettingsActivity.kt", l = {196}, m = "invokeSuspend")
public final class LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1$onChange$1$1$1$job2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ShortcutEntity $it;
    public Object L$0;
    public int label;
    public final /* synthetic */ LockScreenShortcutsSettingsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1$onChange$1$1$1$job2$1(ShortcutEntity shortcutEntity, LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, Continuation continuation) {
        super(2, continuation);
        this.$it = shortcutEntity;
        this.this$0 = lockScreenShortcutsSettingsActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1$onChange$1$1$1$job2$1(this.$it, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1$onChange$1$1$1$job2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ShortcutEntity shortcutEntity;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ShortcutEntity shortcutEntity2 = this.$it;
            LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = this.this$0;
            this.L$0 = shortcutEntity2;
            this.label = 1;
            Object objIsNewAvailable = shortcutEntity2.isNewAvailable(lockScreenShortcutsSettingsActivity, this);
            if (objIsNewAvailable == coroutine_suspended) {
                return coroutine_suspended;
            }
            obj = objIsNewAvailable;
            shortcutEntity = shortcutEntity2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            shortcutEntity = (ShortcutEntity) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        shortcutEntity.setAvailable(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}

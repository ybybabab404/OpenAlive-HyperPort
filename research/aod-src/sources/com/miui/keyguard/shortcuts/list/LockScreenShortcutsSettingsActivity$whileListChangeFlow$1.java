package com.miui.keyguard.shortcuts.list;

import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

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
@DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$whileListChangeFlow$1", f = "LockScreenShortcutsSettingsActivity.kt", l = {175}, m = "invokeSuspend")
public final class LockScreenShortcutsSettingsActivity$whileListChangeFlow$1 extends SuspendLambda implements Function2<ProducerScope<? super List<? extends String>>, Continuation<? super Unit>, Object> {
    public /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ LockScreenShortcutsSettingsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenShortcutsSettingsActivity$whileListChangeFlow$1(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockScreenShortcutsSettingsActivity;
    }

    public static final Unit invokeSuspend$lambda$0(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, LockScreenShortcutsSettingsActivity$whileListChangeFlow$1$whiteListChangedCallback$1 lockScreenShortcutsSettingsActivity$whileListChangeFlow$1$whiteListChangedCallback$1) {
        lockScreenShortcutsSettingsActivity.getShortcutManager().removeWhiteListChangedCallback(lockScreenShortcutsSettingsActivity$whileListChangeFlow$1$whiteListChangedCallback$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LockScreenShortcutsSettingsActivity$whileListChangeFlow$1 lockScreenShortcutsSettingsActivity$whileListChangeFlow$1 = new LockScreenShortcutsSettingsActivity$whileListChangeFlow$1(this.this$0, continuation);
        lockScreenShortcutsSettingsActivity$whileListChangeFlow$1.L$0 = obj;
        return lockScreenShortcutsSettingsActivity$whileListChangeFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((LockScreenShortcutsSettingsActivity$whileListChangeFlow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$whileListChangeFlow$1$whiteListChangedCallback$1, com.miui.keyguard.shortcuts.manager.MiuiShortcutManager$WhiteListChangedCallback] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new MiuiShortcutManager.WhiteListChangedCallback() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$whileListChangeFlow$1$whiteListChangedCallback$1
                @Override // com.miui.keyguard.shortcuts.manager.MiuiShortcutManager.WhiteListChangedCallback
                public void onWhiteListChanged(List whiteList) {
                    Intrinsics.checkNotNullParameter(whiteList, "whiteList");
                    producerScope.mo1228trySendJP2dKIU(whiteList);
                }
            };
            this.this$0.getShortcutManager().addWhiteListChangedCallback(r1);
            producerScope.mo1228trySendJP2dKIU(this.this$0.getShortcutManager().getWhiteList());
            final LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = this.this$0;
            Function0 function0 = new Function0() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$whileListChangeFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return LockScreenShortcutsSettingsActivity$whileListChangeFlow$1.invokeSuspend$lambda$0(lockScreenShortcutsSettingsActivity, r1);
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

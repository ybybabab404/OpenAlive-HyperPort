package com.miui.keyguard.shortcuts.list;

import android.os.Handler;
import android.os.Looper;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
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
@DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1", f = "LockScreenShortcutsSettingsActivity.kt", l = {212}, m = "invokeSuspend")
public final class LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1 extends SuspendLambda implements Function2<ProducerScope<? super List<? extends ShortcutEntity>>, Continuation<? super Unit>, Object> {
    public /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ LockScreenShortcutsSettingsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockScreenShortcutsSettingsActivity;
    }

    public static final Unit invokeSuspend$lambda$4(ProducerScope producerScope, LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1 lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1) {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            lockScreenShortcutsSettingsActivity.getContentResolver().unregisterContentObserver(lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1);
            objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logW$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "unregister overlay URI error, " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1 lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1 = new LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1(this.this$0, continuation);
        lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1.L$0 = obj;
        return lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object objM1061constructorimpl;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1 lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1 = new LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1(this.this$0, producerScope, new Handler(Looper.getMainLooper()));
            LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = this.this$0;
            try {
                Result.Companion companion = Result.Companion;
                lockScreenShortcutsSettingsActivity.getContentResolver().registerContentObserver(ShortcutOverlayRepositoryImpl.Companion.getCONTENT_URI(), false, lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1);
                objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl != null) {
                LogUtils.logW$default(LogUtils.INSTANCE, "LockScreenShortcutsSettingsActivity", "register overlay URI error, " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
            }
            lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1.onChange(false);
            final LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1.invokeSuspend$lambda$4(producerScope, lockScreenShortcutsSettingsActivity2, lockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1);
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

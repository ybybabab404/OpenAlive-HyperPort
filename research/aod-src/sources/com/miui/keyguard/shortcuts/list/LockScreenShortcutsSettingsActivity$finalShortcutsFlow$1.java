package com.miui.keyguard.shortcuts.list;

import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

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
@DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$finalShortcutsFlow$1", f = "LockScreenShortcutsSettingsActivity.kt", l = {}, m = "invokeSuspend")
public final class LockScreenShortcutsSettingsActivity$finalShortcutsFlow$1 extends SuspendLambda implements Function4<List<? extends ShortcutEntity>, List<? extends String>, List<? extends ShortcutEntity>, Continuation<? super List<? extends ShortcutEntity>>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public /* synthetic */ Object L$2;
    public int label;
    public final /* synthetic */ LockScreenShortcutsSettingsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenShortcutsSettingsActivity$finalShortcutsFlow$1(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, Continuation continuation) {
        super(4, continuation);
        this.this$0 = lockScreenShortcutsSettingsActivity;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(List list, List list2, List list3, Continuation continuation) {
        LockScreenShortcutsSettingsActivity$finalShortcutsFlow$1 lockScreenShortcutsSettingsActivity$finalShortcutsFlow$1 = new LockScreenShortcutsSettingsActivity$finalShortcutsFlow$1(this.this$0, continuation);
        lockScreenShortcutsSettingsActivity$finalShortcutsFlow$1.L$0 = list;
        lockScreenShortcutsSettingsActivity$finalShortcutsFlow$1.L$1 = list2;
        lockScreenShortcutsSettingsActivity$finalShortcutsFlow$1.L$2 = list3;
        return lockScreenShortcutsSettingsActivity$finalShortcutsFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        return this.this$0.mergeDataSource((List) this.L$1, list, (List) this.L$2);
    }
}

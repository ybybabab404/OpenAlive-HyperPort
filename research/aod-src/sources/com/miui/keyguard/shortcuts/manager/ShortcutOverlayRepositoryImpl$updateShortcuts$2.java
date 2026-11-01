package com.miui.keyguard.shortcuts.manager;

import android.os.Bundle;
import com.google.gson.Gson;
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
/* JADX INFO: compiled from: ShortcutOverlayRepositoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl$updateShortcuts$2", f = "ShortcutOverlayRepositoryImpl.kt", l = {}, m = "invokeSuspend")
final class ShortcutOverlayRepositoryImpl$updateShortcuts$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
    public final /* synthetic */ List $shortcuts;
    public /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ ShortcutOverlayRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutOverlayRepositoryImpl$updateShortcuts$2(List list, ShortcutOverlayRepositoryImpl shortcutOverlayRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$shortcuts = list;
        this.this$0 = shortcutOverlayRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShortcutOverlayRepositoryImpl$updateShortcuts$2 shortcutOverlayRepositoryImpl$updateShortcuts$2 = new ShortcutOverlayRepositoryImpl$updateShortcuts$2(this.$shortcuts, this.this$0, continuation);
        shortcutOverlayRepositoryImpl$updateShortcuts$2.L$0 = obj;
        return shortcutOverlayRepositoryImpl$updateShortcuts$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ShortcutOverlayRepositoryImpl$updateShortcuts$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object objM1061constructorimpl;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Bundle bundle = new Bundle();
        bundle.putString("overlays", new Gson().toJson(this.$shortcuts));
        ShortcutOverlayRepositoryImpl shortcutOverlayRepositoryImpl = this.this$0;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(shortcutOverlayRepositoryImpl.context.getContentResolver().call(ShortcutOverlayRepositoryImpl.Companion.getCONTENT_URI(), "UPDATE_OVERLAYS", (String) null, bundle));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl == null) {
            return objM1061constructorimpl;
        }
        LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutOverlayRepositoryImpl", "updateShortcuts error, " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
        return Unit.INSTANCE;
    }
}

package com.miui.keyguard.shortcuts.list;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.lifecycle.LifecycleOwnerKt;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import com.miui.miwallpaper.MiuiWallpaperManager;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
@DebugMetadata(c = "com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1", f = "LockScreenShortcutsSettingsActivity.kt", l = {}, m = "invokeSuspend")
public final class LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ LockScreenShortcutsSettingsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockScreenShortcutsSettingsActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, Bitmap bitmap) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lockScreenShortcutsSettingsActivity), Dispatchers.getMain(), null, new LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1$1$1(lockScreenShortcutsSettingsActivity, bitmap, null), 2, null);
        lockScreenShortcutsSettingsActivity.createAndUpdateWallpaperJob = null;
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ViewUtils viewUtils = ViewUtils.INSTANCE;
        Context applicationContext = this.this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        MiuiWallpaperManager miuiWallpaperManager = this.this$0.wallpaperManager;
        final LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity = this.this$0;
        viewUtils.createAndUpdateWallpaper(applicationContext, miuiWallpaperManager, new Function1() { // from class: com.miui.keyguard.shortcuts.list.LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return LockScreenShortcutsSettingsActivity$wallpaperCallback$1$onWallpaperChanged$1.invokeSuspend$lambda$0(lockScreenShortcutsSettingsActivity, (Bitmap) obj2);
            }
        });
        return Unit.INSTANCE;
    }
}

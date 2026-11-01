package com.miui.keyguard.shortcuts.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.miui.keyguard.shortcuts.utils.PackageUtils;
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
/* JADX INFO: compiled from: ShortcutEntity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.shortcuts.data.ShortcutEntity$getNewDrawable$2$1", f = "ShortcutEntity.kt", l = {}, m = "invokeSuspend")
public final class ShortcutEntity$getNewDrawable$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Drawable>, Object> {
    public final /* synthetic */ Context $context;
    public int label;
    public final /* synthetic */ ShortcutEntity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutEntity$getNewDrawable$2$1(Context context, ShortcutEntity shortcutEntity, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.this$0 = shortcutEntity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutEntity$getNewDrawable$2$1(this.$context, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ShortcutEntity$getNewDrawable$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PackageUtils packageUtils = PackageUtils.INSTANCE;
        Resources resourcesForApplication = packageUtils.getResourcesForApplication(this.$context, this.this$0.getPackageName());
        if (resourcesForApplication == null) {
            return null;
        }
        Context context = this.$context;
        ShortcutEntity shortcutEntity = this.this$0;
        return packageUtils.getDrawableFromApplication(context, resourcesForApplication, shortcutEntity.getPackageName(), shortcutEntity.getIconId());
    }
}

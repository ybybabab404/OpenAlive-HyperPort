package com.miui.keyguard.editor.homepage.util;

import android.graphics.Bitmap;
import android.util.Log;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Ref;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1", f = "TemplateApplyController.kt", l = {}, m = "invokeSuspend")
public final class TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Ref.ObjectRef $historyScreenshot;
    public final /* synthetic */ Ref.ObjectRef $historyWallpaperScreenshot;
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1(TemplateApplyController templateApplyController, Ref.ObjectRef objectRef, Ref.ObjectRef objectRef2, Continuation continuation) {
        super(4, continuation);
        this.this$0 = templateApplyController;
        this.$historyScreenshot = objectRef;
        this.$historyWallpaperScreenshot = objectRef2;
    }

    public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
        TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1 templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1 = new TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1(this.this$0, this.$historyScreenshot, this.$historyWallpaperScreenshot, continuation);
        templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1.L$0 = bitmap;
        templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1.L$1 = bitmap2;
        return templateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke((Bitmap) obj, ((Number) obj2).intValue(), (Bitmap) obj3, (Continuation) obj4);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Bitmap bitmap = (Bitmap) this.L$0;
        Bitmap bitmap2 = (Bitmap) this.L$1;
        Log.d(this.this$0.TAG, "history screenshot finish");
        this.$historyScreenshot.element = bitmap;
        this.$historyWallpaperScreenshot.element = bitmap2;
        return Unit.INSTANCE;
    }
}

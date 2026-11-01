package com.miui.keyguard.editor.utils;

import android.graphics.Bitmap;
import android.graphics.RenderNode;
import android.util.Log;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
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
/* JADX INFO: compiled from: ViewUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.utils.ViewUtil$invokeOnScreenshotCaptured$softwareBitmap$1", f = "ViewUtil.kt", l = {}, m = "invokeSuspend")
public final class ViewUtil$invokeOnScreenshotCaptured$softwareBitmap$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
    public final /* synthetic */ int $height;
    public final /* synthetic */ Ref.ObjectRef $renderNode;
    public final /* synthetic */ Bitmap $wallpaper;
    public final /* synthetic */ int $width;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewUtil$invokeOnScreenshotCaptured$softwareBitmap$1(Ref.ObjectRef objectRef, int i, int i2, Bitmap bitmap, Continuation continuation) {
        super(2, continuation);
        this.$renderNode = objectRef;
        this.$width = i;
        this.$height = i2;
        this.$wallpaper = bitmap;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ViewUtil$invokeOnScreenshotCaptured$softwareBitmap$1(this.$renderNode, this.$width, this.$height, this.$wallpaper, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ViewUtil$invokeOnScreenshotCaptured$softwareBitmap$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Bitmap bitmapCopy;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            Object obj2 = this.$renderNode.element;
            if (obj2 != null) {
                Bitmap bitmapCreateHardwareBitmap = ViewUtil.INSTANCE.createHardwareBitmap((RenderNode) obj2, this.$width, this.$height);
                bitmapCopy = bitmapCreateHardwareBitmap != null ? bitmapCreateHardwareBitmap.copy(Bitmap.Config.ARGB_8888, false) : null;
                BitmapUtil.INSTANCE.recycleBitmap(bitmapCreateHardwareBitmap, "hardware bitmap");
            } else {
                bitmapCopy = this.$wallpaper;
            }
            Log.i("ViewUtil", "hardwareBitmap is null " + (bitmapCopy == null));
            return bitmapCopy;
        } catch (Exception e) {
            Log.i("ViewUtil", "convert to soft bitmap error", e);
            return null;
        }
    }
}

package com.miui.keyguard.editor.edit.base;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseTemplateView$startPickColorFromWallpaper$1$data$1", f = "BaseTemplateView.kt", l = {}, m = "invokeSuspend")
@SourceDebugExtension
public final class BaseTemplateView$startPickColorFromWallpaper$1$data$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ColorData>, Object> {
    public final /* synthetic */ Bitmap $bitmap;
    public final /* synthetic */ Rect $colorPickupRect;
    public final /* synthetic */ boolean $fromStyleChanged;
    public final /* synthetic */ boolean $wallpaperChanged;
    public int label;
    public final /* synthetic */ BaseTemplateView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseTemplateView$startPickColorFromWallpaper$1$data$1(BaseTemplateView baseTemplateView, Bitmap bitmap, boolean z, Rect rect, boolean z2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = baseTemplateView;
        this.$bitmap = bitmap;
        this.$wallpaperChanged = z;
        this.$colorPickupRect = rect;
        this.$fromStyleChanged = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BaseTemplateView$startPickColorFromWallpaper$1$data$1(this.this$0, this.$bitmap, this.$wallpaperChanged, this.$colorPickupRect, this.$fromStyleChanged, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((BaseTemplateView$startPickColorFromWallpaper$1$data$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ColorData colorDataPickColor;
        Bitmap bitmap;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            Pair<Bitmap, Boolean> pairPreProcessBitmapForPickColor = this.this$0.preProcessBitmapForPickColor(this.$bitmap, this.$wallpaperChanged);
            Rect rect = this.$colorPickupRect;
            Rect rect2 = rect != null ? new Rect(rect) : null;
            AutoColorPicker autoColorPicker = this.this$0.getAutoColorPicker();
            if (autoColorPicker != null) {
                Bitmap bitmap2 = (Bitmap) pairPreProcessBitmapForPickColor.getFirst();
                if (bitmap2 == null) {
                    bitmap2 = this.$bitmap;
                }
                colorDataPickColor = autoColorPicker.pickColor(bitmap2, this.$fromStyleChanged, rect2);
            } else {
                colorDataPickColor = null;
            }
            if (((Boolean) pairPreProcessBitmapForPickColor.getSecond()).booleanValue() && (bitmap = (Bitmap) pairPreProcessBitmapForPickColor.getFirst()) != null) {
                bitmap.recycle();
            }
            return colorDataPickColor;
        } catch (Exception e) {
            Log.w("Keyguard-Editor-BaseTemplateView", "startPickColorFromWallpaper: " + e);
            return null;
        }
    }
}

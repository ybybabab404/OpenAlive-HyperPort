package com.miui.keyguard.editor.edit;

import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.maml.R;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
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
/* JADX INFO: compiled from: ThemeClockView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.ThemeClockView$init$1$2$1$1", f = "ThemeClockView.kt", l = {R.styleable.AppCompatTheme_editTextBackground}, m = "invokeSuspend")
public final class ThemeClockView$init$1$2$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public Object L$0;
    public int label;
    public final /* synthetic */ ThemeClockView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThemeClockView$init$1$2$1$1(ThemeClockView themeClockView, Continuation continuation) {
        super(2, continuation);
        this.this$0 = themeClockView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ThemeClockView$init$1$2$1$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ThemeClockView$init$1$2$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ThemeClockView themeClockView;
        BaseClockView baseClockView;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Bitmap bitmap = this.this$0.wallpaperForPickColor;
            if (bitmap != null) {
                ThemeClockView themeClockView2 = this.this$0;
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                ThemeClockView$init$1$2$1$1$1$colorData$1 themeClockView$init$1$2$1$1$1$colorData$1 = new ThemeClockView$init$1$2$1$1$1$colorData$1(themeClockView2, bitmap, null);
                this.L$0 = themeClockView2;
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, themeClockView$init$1$2$1$1$1$colorData$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                themeClockView = themeClockView2;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        themeClockView = (ThemeClockView) this.L$0;
        ResultKt.throwOnFailure(obj);
        ColorData colorData = (ColorData) obj;
        Bitmap bitmap2 = themeClockView.wallpaperForPickColor;
        Integer numBoxInt = bitmap2 != null ? Boxing.boxInt(bitmap2.getWidth()) : null;
        Bitmap bitmap3 = themeClockView.wallpaperForPickColor;
        Log.d("ThemeClockView", "init: wallpaperForPickColor w=" + numBoxInt + " h=" + (bitmap3 != null ? Boxing.boxInt(bitmap3.getHeight()) : null) + " autoColorPicker:" + (themeClockView.getAutoColorPicker() != null) + " colorData:" + ((colorData != null ? colorData.getClockPalette() : null) != null));
        if (colorData != null && (baseClockView = themeClockView.clockView) != null) {
            baseClockView.updateClockColor(colorData);
        }
        return Unit.INSTANCE;
    }
}

package com.miui.keyguard.editor.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
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
/* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1", f = "DeviceScreenshotHelper.kt", l = {326}, m = "invokeSuspend")
public final class DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
    public final /* synthetic */ BaseTemplateView $it;
    public final /* synthetic */ Function3 $onScreenshotsCaptured;
    public final /* synthetic */ ViewGroup $screenshotContainer;
    public final /* synthetic */ Screenshots $screenshots;
    public /* synthetic */ int I$0;
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1(Screenshots screenshots, BaseTemplateView baseTemplateView, ViewGroup viewGroup, Function3 function3, Continuation continuation) {
        super(4, continuation);
        this.$screenshots = screenshots;
        this.$it = baseTemplateView;
        this.$screenshotContainer = viewGroup;
        this.$onScreenshotsCaptured = function3;
    }

    public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
        DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1 deviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1 = new DeviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1(this.$screenshots, this.$it, this.$screenshotContainer, this.$onScreenshotsCaptured, continuation);
        deviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1.L$0 = bitmap;
        deviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1.I$0 = i;
        deviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1.L$1 = bitmap2;
        return deviceScreenshotHelper$takeMultiScreenshotsForTemplateView$6$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke((Bitmap) obj, ((Number) obj2).intValue(), (Bitmap) obj3, (Continuation) obj4);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Bitmap bitmap = (Bitmap) this.L$0;
            int i2 = this.I$0;
            Bitmap bitmap2 = (Bitmap) this.L$1;
            this.$screenshots.resetBitmap();
            Log.d("Keyguard-Editor-DeviceScreenshotHelper", "small screenshot finish: i=" + i2);
            this.$screenshots.setSmallWallpaper(bitmap2);
            this.$screenshots.setSmall(bitmap);
            this.$it.setCombineAnimListener(null);
            DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
            deviceScreenshotHelper.removeTemplateViewSafely(this.$screenshotContainer, this.$it);
            Function3 function3 = this.$onScreenshotsCaptured;
            Screenshots screenshots = this.$screenshots;
            Integer numBoxInt = Boxing.boxInt(deviceScreenshotHelper.getSCREENSHOT_SMALL());
            this.L$0 = null;
            this.label = 1;
            if (function3.invoke(screenshots, numBoxInt, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$it.onDestroy();
        return Unit.INSTANCE;
    }
}

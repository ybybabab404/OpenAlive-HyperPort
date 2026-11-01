package com.miui.keyguard.editor.utils;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineScope;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1", f = "DeviceScreenshotHelper.kt", l = {406}, m = "invokeSuspend")
public final class DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ CancellableContinuation $cont;
    public final /* synthetic */ boolean $isCurrent;
    public final /* synthetic */ Function4 $onScreenshotCaptured;
    public final /* synthetic */ Screenshots $screenshots;
    public final /* synthetic */ boolean $skipScreenshot;
    public final /* synthetic */ BaseTemplateView $templateView;
    public int label;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1$1", f = "DeviceScreenshotHelper.kt", l = {410}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
        public final /* synthetic */ CancellableContinuation $cont;
        public final /* synthetic */ Function4 $onScreenshotCaptured;
        public /* synthetic */ int I$0;
        public /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function4 function4, CancellableContinuation cancellableContinuation, Continuation continuation) {
            super(4, continuation);
            this.$onScreenshotCaptured = function4;
            this.$cont = cancellableContinuation;
        }

        public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$onScreenshotCaptured, this.$cont, continuation);
            anonymousClass1.L$0 = bitmap;
            anonymousClass1.I$0 = i;
            anonymousClass1.L$1 = bitmap2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
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
                Function4 function4 = this.$onScreenshotCaptured;
                Integer numBoxInt = Boxing.boxInt(i2);
                this.L$0 = null;
                this.label = 1;
                if (function4.invoke(bitmap, numBoxInt, bitmap2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$cont.resumeWith(Result.m1061constructorimpl(null));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1(BaseTemplateView baseTemplateView, Screenshots screenshots, boolean z, boolean z2, Function4 function4, CancellableContinuation cancellableContinuation, Continuation continuation) {
        super(2, continuation);
        this.$templateView = baseTemplateView;
        this.$screenshots = screenshots;
        this.$skipScreenshot = z;
        this.$isCurrent = z2;
        this.$onScreenshotCaptured = function4;
        this.$cont = cancellableContinuation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1(this.$templateView, this.$screenshots, this.$skipScreenshot, this.$isCurrent, this.$onScreenshotCaptured, this.$cont, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((DeviceScreenshotHelper$takeScreenshotSync$2$screenshotJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            BaseTemplateView baseTemplateView = this.$templateView;
            Screenshots screenshots = this.$screenshots;
            boolean z = this.$skipScreenshot && !this.$isCurrent;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$onScreenshotCaptured, this.$cont, null);
            this.label = 1;
            if (viewUtil.checkHierarchyThenTakeScreenshot(baseTemplateView, screenshots, z, anonymousClass1, this) == coroutine_suspended) {
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

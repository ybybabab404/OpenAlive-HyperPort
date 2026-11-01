package com.miui.keyguard.editor.homepage.util;

import android.util.Log;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.util.DataUtil;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
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
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$completeApplyTemplate$applyJob$1$applySuccess$1", f = "TemplateApplyController.kt", l = {1150}, m = "invokeSuspend")
public final class TemplateApplyController$completeApplyTemplate$applyJob$1$applySuccess$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ String $configUri;
    public final /* synthetic */ long $entityId;
    public final /* synthetic */ int $hideFlag;
    public final /* synthetic */ boolean $isPreset;
    public final /* synthetic */ int $screenStatus;
    public final /* synthetic */ ScreenshotSource $screenshotSource;
    public final /* synthetic */ boolean $shouldApplyToAll;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$completeApplyTemplate$applyJob$1$applySuccess$1(boolean z, TemplateApplyController templateApplyController, ScreenshotSource screenshotSource, String str, long j, boolean z2, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.$isPreset = z;
        this.this$0 = templateApplyController;
        this.$screenshotSource = screenshotSource;
        this.$configUri = str;
        this.$entityId = j;
        this.$shouldApplyToAll = z2;
        this.$screenStatus = i;
        this.$hideFlag = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplyController$completeApplyTemplate$applyJob$1$applySuccess$1(this.$isPreset, this.this$0, this.$screenshotSource, this.$configUri, this.$entityId, this.$shouldApplyToAll, this.$screenStatus, this.$hideFlag, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplyController$completeApplyTemplate$applyJob$1$applySuccess$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        boolean zBooleanValue;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            zBooleanValue = false;
            if (this.$isPreset && DataUtil.INSTANCE.checkPresetScreenshot(this.this$0.activity, this.$screenshotSource)) {
                Log.w(this.this$0.TAG, "completeApplyTemplate failed: preset template screenshotBitmaps = null");
            } else if (this.$isPreset || !DataUtil.INSTANCE.checkHistoryScreenshotUri(this.$screenshotSource.getHistorySmallScreenshotUri(), this.$screenshotSource.getHistoryLargePortraitScreenshotUri(), this.$screenshotSource.getHistoryLargeLandscapeScreenshotUri())) {
                TemplateApplyController templateApplyController = this.this$0;
                String str = this.$configUri;
                boolean z = this.$isPreset;
                ScreenshotSource screenshotSource = this.$screenshotSource;
                long j = this.$entityId;
                boolean z2 = this.$shouldApplyToAll;
                int i2 = this.$screenStatus;
                int i3 = this.$hideFlag;
                this.label = 1;
                obj = templateApplyController.finalApplyTemplate(str, z, screenshotSource, j, z2, i2, i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                Log.w(this.this$0.TAG, "completeApplyTemplate failed: history template screenshotUri missed.");
            }
            return Boxing.boxBoolean(zBooleanValue);
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        zBooleanValue = ((Boolean) obj).booleanValue();
        return Boxing.boxBoolean(zBooleanValue);
    }
}

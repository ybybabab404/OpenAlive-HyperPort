package com.miui.keyguard.editor.homepage.util;

import com.miui.keyguard.editor.data.bean.CommonConfig;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
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
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1", f = "TemplateApplyController.kt", l = {467, 468}, m = "invokeSuspend")
public final class TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation, Object> {
    public final /* synthetic */ CommonConfig $config;
    public final /* synthetic */ Ref.ObjectRef $historyScreenshot;
    public final /* synthetic */ Ref.ObjectRef $historyWallpaperScreenshot;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public Object L$3;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1(TemplateApplyController templateApplyController, CommonConfig commonConfig, Ref.ObjectRef objectRef, Ref.ObjectRef objectRef2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = templateApplyController;
        this.$config = commonConfig;
        this.$historyScreenshot = objectRef;
        this.$historyWallpaperScreenshot = objectRef2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1(this.this$0, this.$config, this.$historyScreenshot, this.$historyWallpaperScreenshot, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00b9, code lost:
    
        if (r0 == r7) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r20) {
        /*
            r19 = this;
            r6 = r19
            java.lang.Object r7 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r0 = r6.label
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L34
            if (r0 == r2) goto L20
            if (r0 != r1) goto L18
            kotlin.ResultKt.throwOnFailure(r20)
            r0 = r20
            goto Lbc
        L18:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L20:
            java.lang.Object r0 = r6.L$3
            com.miui.keyguard.editor.edit.base.BaseTemplateView r0 = (com.miui.keyguard.editor.edit.base.BaseTemplateView) r0
            java.lang.Object r2 = r6.L$2
            kotlin.jvm.internal.Ref$ObjectRef r2 = (kotlin.jvm.internal.Ref.ObjectRef) r2
            java.lang.Object r4 = r6.L$1
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref.ObjectRef) r4
            java.lang.Object r5 = r6.L$0
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r5 = (com.miui.keyguard.editor.homepage.util.TemplateApplyController) r5
            kotlin.ResultKt.throwOnFailure(r20)
            goto L8d
        L34:
            kotlin.ResultKt.throwOnFailure(r20)
            com.miui.keyguard.editor.utils.ToastUtil r0 = com.miui.keyguard.editor.utils.ToastUtil.INSTANCE
            int r4 = com.miui.keyguard.editor.R.string.kg_gallery_reset_magic_type_tips
            r5 = 0
            r0.showText(r4, r5)
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r0 = r6.this$0
            android.widget.FrameLayout r0 = com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$getScreenshotContainer$p(r0)
            if (r0 == 0) goto Lbf
            com.miui.keyguard.editor.homepage.util.TemplateApplyController r0 = r6.this$0
            com.miui.keyguard.editor.data.bean.CommonConfig r4 = r6.$config
            kotlin.jvm.internal.Ref$ObjectRef r8 = r6.$historyScreenshot
            kotlin.jvm.internal.Ref$ObjectRef r9 = r6.$historyWallpaperScreenshot
            com.miui.keyguard.editor.utils.DeviceScreenshotHelper r10 = com.miui.keyguard.editor.utils.DeviceScreenshotHelper.INSTANCE
            androidx.fragment.app.FragmentActivity r11 = com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$getActivity$p(r0)
            com.miui.keyguard.editor.edit.base.BaseTemplateView r4 = r10.createCurrentScreenTemplate(r11, r4)
            if (r4 == 0) goto Lbf
            android.widget.FrameLayout r10 = com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$getScreenshotContainer$p(r0)
            if (r10 == 0) goto L64
            r10.removeAllViews()
        L64:
            android.widget.FrameLayout r10 = com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$getScreenshotContainer$p(r0)
            if (r10 == 0) goto L6d
            r10.setVisibility(r5)
        L6d:
            android.widget.FrameLayout r5 = com.miui.keyguard.editor.homepage.util.TemplateApplyController.access$getScreenshotContainer$p(r0)
            if (r5 == 0) goto L76
            r5.addView(r4)
        L76:
            r6.L$0 = r0
            r6.L$1 = r8
            r6.L$2 = r9
            r6.L$3 = r4
            r6.label = r2
            r10 = 100
            java.lang.Object r2 = kotlinx.coroutines.DelayKt.delay(r10, r6)
            if (r2 != r7) goto L89
            goto Lbb
        L89:
            r5 = r0
            r0 = r4
            r4 = r8
            r2 = r9
        L8d:
            com.miui.keyguard.editor.utils.DeviceScreenshotHelper r8 = com.miui.keyguard.editor.utils.DeviceScreenshotHelper.INSTANCE
            com.miui.keyguard.editor.utils.Screenshots r9 = new com.miui.keyguard.editor.utils.Screenshots
            r17 = 127(0x7f, float:1.78E-43)
            r18 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18)
            com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1 r10 = new com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1$1$1$1
            r10.<init>(r5, r4, r2, r3)
            r6.L$0 = r3
            r6.L$1 = r3
            r6.L$2 = r3
            r6.L$3 = r3
            r6.label = r1
            r3 = 0
            r4 = 0
            r1 = r0
            r0 = r8
            r2 = r9
            r5 = r10
            java.lang.Object r0 = r0.takeScreenshotSync(r1, r2, r3, r4, r5, r6)
            if (r0 != r7) goto Lbc
        Lbb:
            return r7
        Lbc:
            java.lang.Void r0 = (java.lang.Void) r0
            return r0
        Lbf:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.homepage.util.TemplateApplyController$checkGalleryOrApplyTemplate$1$taskResult$1$3$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

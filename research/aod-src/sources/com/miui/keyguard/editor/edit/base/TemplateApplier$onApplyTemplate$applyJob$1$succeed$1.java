package com.miui.keyguard.editor.edit.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.xiaomi.onetrack.api.am;
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
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: TemplateApplier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$onApplyTemplate$applyJob$1$succeed$1", f = "TemplateApplier.kt", l = {476}, m = "invokeSuspend")
public final class TemplateApplier$onApplyTemplate$applyJob$1$succeed$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ TemplateApplier.TemplateApplierParams $params;
    public final /* synthetic */ BaseTemplateView $templateView;
    public /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ TemplateApplier this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplier$onApplyTemplate$applyJob$1$succeed$1(TemplateApplier templateApplier, BaseTemplateView baseTemplateView, TemplateApplier.TemplateApplierParams templateApplierParams, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = templateApplier;
        this.$templateView = baseTemplateView;
        this.$params = templateApplierParams;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TemplateApplier$onApplyTemplate$applyJob$1$succeed$1 templateApplier$onApplyTemplate$applyJob$1$succeed$1 = new TemplateApplier$onApplyTemplate$applyJob$1$succeed$1(this.this$0, this.$templateView, this.$params, this.$context, continuation);
        templateApplier$onApplyTemplate$applyJob$1$succeed$1.L$0 = obj;
        return templateApplier$onApplyTemplate$applyJob$1$succeed$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplier$onApplyTemplate$applyJob$1$succeed$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object objM1061constructorimpl;
        Object objApplyEditPageTemplate;
        Intent intent;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                boolean zShouldAddToGallery = this.this$0.shouldAddToGallery(this.$templateView, this.$params.getConfig());
                Context context = this.$templateView.getContext();
                String stringExtra = null;
                Activity activity = context instanceof Activity ? (Activity) context : null;
                if (activity != null && (intent = activity.getIntent()) != null) {
                    stringExtra = intent.getStringExtra("caller");
                }
                boolean zAreEqual = Intrinsics.areEqual(stringExtra, am.s);
                Log.i(this.this$0.tag, "onApplyTemplate addToGallery " + zShouldAddToGallery + " fromPersonalizationAdd=" + zAreEqual);
                Context context2 = this.$context;
                TemplateApplier.TemplateApplierParams templateApplierParams = this.$params;
                BaseTemplateView baseTemplateView = this.$templateView;
                Result.Companion companion = Result.Companion;
                TemplateApi companion2 = TemplateApi.Companion.getInstance(context2);
                CommonConfig config = templateApplierParams.getConfig();
                Intrinsics.checkNotNull(config);
                ScreenshotSource screenshots = templateApplierParams.getScreenshots();
                Intrinsics.checkNotNull(screenshots);
                Bitmap lockOriginWallpaper = templateApplierParams.getLockOriginWallpaper();
                Intrinsics.checkNotNull(lockOriginWallpaper);
                Bitmap homeOriginWallpaper = templateApplierParams.getHomeOriginWallpaper();
                Intrinsics.checkNotNull(homeOriginWallpaper);
                Bitmap subject = templateApplierParams.getSubject();
                long source = templateApplierParams.getSource();
                boolean applyToAll = templateApplierParams.getApplyToAll();
                boolean shouldAddHistory = templateApplierParams.getShouldAddHistory();
                boolean zIsCallingFromKeyguard = baseTemplateView.isCallingFromKeyguard();
                this.label = 1;
                objApplyEditPageTemplate = companion2.applyEditPageTemplate(config, screenshots, lockOriginWallpaper, homeOriginWallpaper, subject, source, shouldAddHistory, applyToAll, zIsCallingFromKeyguard, zShouldAddToGallery, zAreEqual, this);
                if (objApplyEditPageTemplate == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                objApplyEditPageTemplate = obj;
            }
            objM1061constructorimpl = Result.m1061constructorimpl(Boxing.boxBoolean(((Boolean) objApplyEditPageTemplate).booleanValue()));
        } catch (Throwable th) {
            Result.Companion companion3 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        TemplateApplier templateApplier = this.this$0;
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            Log.e(templateApplier.tag, "apply edit error: " + thM1063exceptionOrNullimpl);
            try {
                KeyguardEditorMiSightUtil.INSTANCE.templateApplyFinish(false);
            } catch (Exception unused) {
            }
        }
        Boolean boolBoxBoolean = Boxing.boxBoolean(false);
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = boolBoxBoolean;
        }
        boolean zBooleanValue = ((Boolean) objM1061constructorimpl).booleanValue();
        if (zBooleanValue) {
            this.$templateView.setGalleryOpened(false);
        }
        return Boxing.boxBoolean(zBooleanValue);
    }
}

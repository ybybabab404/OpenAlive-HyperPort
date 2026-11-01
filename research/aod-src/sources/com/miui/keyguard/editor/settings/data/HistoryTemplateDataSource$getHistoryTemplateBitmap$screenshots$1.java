package com.miui.keyguard.editor.settings.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
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
/* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplateBitmap$screenshots$1", f = "HistoryTemplateDataSource.kt", l = {}, m = "invokeSuspend")
@SourceDebugExtension
public final class HistoryTemplateDataSource$getHistoryTemplateBitmap$screenshots$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Pair<? extends Bitmap, ? extends Bitmap>>, Object> {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ TemplateHistoryConfig $history;
    public final /* synthetic */ int $previewHeight;
    public final /* synthetic */ int $previewWidth;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HistoryTemplateDataSource$getHistoryTemplateBitmap$screenshots$1(Context context, TemplateHistoryConfig templateHistoryConfig, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$history = templateHistoryConfig;
        this.$previewWidth = i;
        this.$previewHeight = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HistoryTemplateDataSource$getHistoryTemplateBitmap$screenshots$1(this.$context, this.$history, this.$previewWidth, this.$previewHeight, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((HistoryTemplateDataSource$getHistoryTemplateBitmap$screenshots$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(this.$context);
        TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
        Bitmap templateBitmapByProvider$default = TemplateDataUtil.getTemplateBitmapByProvider$default(templateDataUtil, this.$context, this.$history.getCurrentScreenshotUri(screenStatus), this.$previewWidth, this.$previewHeight, 0, 16, null);
        Bitmap templateBitmapByProvider$default2 = TemplateDataUtil.getTemplateBitmapByProvider$default(templateDataUtil, this.$context, this.$history.getCurrentWallpaperScreenshotUri(screenStatus), this.$previewWidth, this.$previewHeight, 0, 16, null);
        if (templateBitmapByProvider$default2 == null) {
            Bundle bundle = new Bundle();
            TemplateHistoryConfig templateHistoryConfig = this.$history;
            bundle.putString("configUri", templateHistoryConfig.getConfigUri());
            bundle.putInt("hideFlag", templateHistoryConfig.getHideFlag());
            Bundle bundleCall = this.$context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callGetHistoryWallpaper", (String) null, bundle);
            ParcelFileDescriptor parcelFileDescriptor = bundleCall != null ? (ParcelFileDescriptor) bundleCall.getParcelable("parcelFileDescriptor") : null;
            if (parcelFileDescriptor != null) {
                templateBitmapByProvider$default2 = BitmapUtil.parcelFileDescriptorToBitmap$default(BitmapUtil.INSTANCE, parcelFileDescriptor, this.$previewWidth, this.$previewHeight, null, null, 24, null);
            } else {
                templateBitmapByProvider$default2 = null;
            }
            Log.d("HistoryTemplateDataSource", "home screenshot is null, use origin wallpaper homeBitmap=" + templateBitmapByProvider$default2);
        }
        return new Pair(templateBitmapByProvider$default, templateBitmapByProvider$default2);
    }
}

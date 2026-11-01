package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineExceptionHandler;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: CoroutineExceptionHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class DeviceScreenshotHelper$screenshot$$inlined$CoroutineExceptionHandler$1 extends AbstractCoroutineContextElement implements CoroutineExceptionHandler {
    public final /* synthetic */ Function1 $captureFailedCallback$inlined;
    public final /* synthetic */ ViewGroup $containerView$inlined;
    public final /* synthetic */ Context $context$inlined;
    public final /* synthetic */ CommonConfig $copied$inlined;
    public final /* synthetic */ Function0 $hideScreenshotCallback$inlined;
    public final /* synthetic */ BaseTemplateView $homeTemplateView$inlined;
    public final /* synthetic */ BaseTemplateView $lockTemplateView$inlined;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceScreenshotHelper$screenshot$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key key, CommonConfig commonConfig, Context context, ViewGroup viewGroup, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, Function0 function0, Function1 function1) {
        super(key);
        this.$copied$inlined = commonConfig;
        this.$context$inlined = context;
        this.$containerView$inlined = viewGroup;
        this.$lockTemplateView$inlined = baseTemplateView;
        this.$homeTemplateView$inlined = baseTemplateView2;
        this.$hideScreenshotCallback$inlined = function0;
        this.$captureFailedCallback$inlined = function1;
    }

    @Override // kotlinx.coroutines.CoroutineExceptionHandler
    public void handleException(CoroutineContext coroutineContext, Throwable th) {
        Log.w("Keyguard-Editor-DeviceScreenshotHelper", "onCurrentTemplateScreenshot: ", th);
        WallpaperInfo wallpaperInfo = this.$copied$inlined.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo != null) {
            wallpaperInfo.setSubjectBitmap(null);
        }
        DeviceScreenshotHelper.INSTANCE.takeCaptureForMultiScreenMode(this.$context$inlined, this.$containerView$inlined, this.$lockTemplateView$inlined, this.$homeTemplateView$inlined, this.$copied$inlined, this.$hideScreenshotCallback$inlined, this.$captureFailedCallback$inlined);
    }
}

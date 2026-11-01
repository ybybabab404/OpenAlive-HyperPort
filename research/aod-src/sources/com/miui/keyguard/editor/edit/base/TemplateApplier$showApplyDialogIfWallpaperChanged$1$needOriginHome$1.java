package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.DeviceUtil;
import kotlin.Metadata;
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
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$showApplyDialogIfWallpaperChanged$1$needOriginHome$1", f = "TemplateApplier.kt", l = {}, m = "invokeSuspend")
public final class TemplateApplier$showApplyDialogIfWallpaperChanged$1$needOriginHome$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ BaseTemplateView $templateView;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplier$showApplyDialogIfWallpaperChanged$1$needOriginHome$1(Context context, BaseTemplateView baseTemplateView, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$templateView = baseTemplateView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplier$showApplyDialogIfWallpaperChanged$1$needOriginHome$1(this.$context, this.$templateView, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplier$showApplyDialogIfWallpaperChanged$1$needOriginHome$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WallpaperController companion = WallpaperController.Companion.getInstance(this.$context);
        CommonConfig templateConfig = this.$templateView.getTemplateConfig();
        boolean z = false;
        if (DeviceUtil.isHomeNeedUseOriginalEffect$default(DeviceUtil.INSTANCE, companion, null, (templateConfig == null || (lockscreenInfo2 = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo2.getWallpaperInfo()) == null) ? 0 : wallpaperInfo.getMagicType(), 2, null)) {
            CommonConfig templateConfig2 = this.$templateView.getTemplateConfig();
            if (!Intrinsics.areEqual((templateConfig2 == null || (lockscreenInfo = templateConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId(), "doodle")) {
                z = true;
            }
        }
        return Boxing.boxBoolean(z);
    }
}

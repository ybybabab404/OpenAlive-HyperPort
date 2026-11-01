package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.Wallpaper;
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
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.TemplateApplier$showDialogByWallpaperType$1$isSpecialWallpaperType$1", f = "TemplateApplier.kt", l = {}, m = "invokeSuspend")
public final class TemplateApplier$showDialogByWallpaperType$1$isSpecialWallpaperType$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ TemplateApplier.TemplateApplierParams $params;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplier$showDialogByWallpaperType$1$isSpecialWallpaperType$1(Context context, TemplateApplier.TemplateApplierParams templateApplierParams, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$params = templateApplierParams;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplier$showDialogByWallpaperType$1$isSpecialWallpaperType$1(this.$context, this.$params, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplier$showDialogByWallpaperType$1$isSpecialWallpaperType$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String lockWallpaperType = WallpaperController.Companion.getInstance(this.$context).getLockWallpaperType();
        CommonConfig config = this.$params.getConfig();
        Intrinsics.checkNotNull(config);
        WallpaperInfo wallpaperInfo = config.getLockscreenInfo().getWallpaperInfo();
        String resourceType = wallpaperInfo != null ? wallpaperInfo.getResourceType() : null;
        Wallpaper.Companion companion = Wallpaper.Companion;
        return Boxing.boxBoolean(companion.isSpecialWallpaperType(resourceType) || companion.isSpecialWallpaperType(lockWallpaperType));
    }
}

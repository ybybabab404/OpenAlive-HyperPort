package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
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
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.util.TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1", f = "TemplateApplyController.kt", l = {}, m = "invokeSuspend")
public final class TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ TemplateItemBean $templateBean;
    public int label;
    public final /* synthetic */ TemplateApplyController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1(TemplateItemBean templateItemBean, TemplateApplyController templateApplyController, Continuation continuation) {
        super(2, continuation);
        this.$templateBean = templateItemBean;
        this.this$0 = templateApplyController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1(this.$templateBean, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((TemplateApplyController$applyHistoryTemplateByProvider$applyJob$1$applySuccess$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String configUri;
        TemplateHistoryConfig history;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TemplateItemBean templateItemBean = this.$templateBean;
        String resourceType = null;
        TemplateHistoryConfig history2 = templateItemBean != null ? templateItemBean.getHistory() : null;
        if (history2 == null || (configUri = history2.getConfigUri()) == null) {
            configUri = "";
        }
        String str = configUri;
        long id = history2 != null ? history2.getId() : 0L;
        int hideFlag = history2 != null ? history2.getHideFlag() : 0;
        TemplateItemBean templateItemBean2 = this.$templateBean;
        if (templateItemBean2 != null && (history = templateItemBean2.getHistory()) != null) {
            resourceType = history.getResourceType();
        }
        WallpaperController.Companion companion = WallpaperController.Companion;
        Context context = this.this$0.applicationContext;
        Intrinsics.checkNotNullExpressionValue(context, "access$getApplicationContext$p(...)");
        String lockWallpaperType = companion.getInstance(context).getLockWallpaperType();
        Wallpaper.Companion companion2 = Wallpaper.Companion;
        boolean z = companion2.isSpecialWallpaperType(resourceType) || companion2.isSpecialWallpaperType(lockWallpaperType);
        TemplateApplyController templateApplyController = this.this$0;
        return Boxing.boxBoolean(TemplateApplyController.finalApplyTemplateByProvider$default(templateApplyController, templateApplyController.activity, str, id, z, hideFlag, 0, 32, null));
    }
}

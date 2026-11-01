package com.miui.keyguard.editor.edit;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.miui.keyguard.editor.base.TemplateEnvironment;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.utils.ClassPreLoadHelperKt;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.TemplateSource;
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
/* JADX INFO: compiled from: EditFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.EditFragment$onViewCreated$2$data$1", f = "EditFragment.kt", l = {}, m = "invokeSuspend")
public final class EditFragment$onViewCreated$2$data$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super EditFragment.TemplateData>, Object> {
    public final /* synthetic */ Bundle $clockExtras;
    public final /* synthetic */ String $configPath;
    public final /* synthetic */ int $hideFlag;
    public final /* synthetic */ boolean $historyIsThirdPartyWallpaper;
    public final /* synthetic */ boolean $isAutoDownload;
    public final /* synthetic */ long $templateSource;
    public final /* synthetic */ View $view;
    public int label;
    public final /* synthetic */ EditFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditFragment$onViewCreated$2$data$1(EditFragment editFragment, View view, long j, String str, boolean z, Bundle bundle, boolean z2, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = editFragment;
        this.$view = view;
        this.$templateSource = j;
        this.$configPath = str;
        this.$historyIsThirdPartyWallpaper = z;
        this.$clockExtras = bundle;
        this.$isAutoDownload = z2;
        this.$hideFlag = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditFragment$onViewCreated$2$data$1(this.this$0, this.$view, this.$templateSource, this.$configPath, this.$historyIsThirdPartyWallpaper, this.$clockExtras, this.$isAutoDownload, this.$hideFlag, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((EditFragment$onViewCreated$2$data$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        WallpaperInfo wallpaperInfo;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ClassPreLoadHelperKt.preloadEditFragmentRelateRes(this.this$0.getContext());
        EditFragment editFragment = this.this$0;
        Context context = this.$view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        long j = this.$templateSource;
        String str = this.$configPath;
        Intrinsics.checkNotNull(str);
        EditFragment.TemplateData templateDataLoadTemplateData = editFragment.loadTemplateData(context, j, str, Boxing.boxBoolean(this.$historyIsThirdPartyWallpaper), this.$clockExtras, this.$isAutoDownload, this.$hideFlag);
        long j2 = this.$templateSource;
        EditFragment editFragment2 = this.this$0;
        if (templateDataLoadTemplateData.getConfig() != null) {
            templateDataLoadTemplateData.getConfig().getLockscreenInfo().setEnvironment(TemplateEnvironment.EditFragmentEnvironment.INSTANCE);
            boolean z = TemplateSource.INSTANCE.isFromPresetTemplate(j2) || editFragment2.getShouldAddHistory();
            if (!DeviceUtil.INSTANCE.isPad() && z) {
                WallpaperInfo wallpaperInfo2 = templateDataLoadTemplateData.getConfig().getHomeInfo().getWallpaperInfo();
                if (Intrinsics.areEqual(wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null, "image") && (wallpaperInfo = templateDataLoadTemplateData.getConfig().getHomeInfo().getWallpaperInfo()) != null) {
                    wallpaperInfo.setMagicType(30000);
                }
            }
        }
        return templateDataLoadTemplateData;
    }
}

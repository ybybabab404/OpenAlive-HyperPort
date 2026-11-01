package com.miui.keyguard.editor.edit.base;

import android.util.Log;
import android.view.View;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
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
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.DepthFirstFrameCallBack$onFirstFrame$1$1", f = "BaseTemplateView.kt", l = {}, m = "invokeSuspend")
public final class DepthFirstFrameCallBack$onFirstFrame$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ BaseTemplateView $it;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DepthFirstFrameCallBack$onFirstFrame$1$1(BaseTemplateView baseTemplateView, Continuation continuation) {
        super(2, continuation);
        this.$it = baseTemplateView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DepthFirstFrameCallBack$onFirstFrame$1$1(this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((DepthFirstFrameCallBack$onFirstFrame$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommonConfig templateConfig = this.$it.getTemplateConfig();
        boolean zIsDepthVideo = MagicType.INSTANCE.isDepthVideo((templateConfig == null || (lockscreenInfo2 = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : Boxing.boxInt(wallpaperInfo2.getMagicType()));
        this.$it.setDepthVideoFirstFrameReady$keyguardeditor_release(true);
        Log.d("Keyguard-Editor-BaseTemplateView", "BaseTemplateView::startPlayerWithDepthVideo:EVENT_FIRST_FRAME");
        this.$it.setNeedRemoveBackground(true);
        if (zIsDepthVideo) {
            View depthVideoForegroundLayer = this.$it.getDepthVideoForegroundLayer();
            if (depthVideoForegroundLayer != null) {
                depthVideoForegroundLayer.setVisibility(0);
            }
        } else {
            View depthVideoForegroundLayer2 = this.$it.getDepthVideoForegroundLayer();
            if (depthVideoForegroundLayer2 != null) {
                depthVideoForegroundLayer2.setVisibility(8);
            }
        }
        CommonConfig templateConfig2 = this.$it.getTemplateConfig();
        if (templateConfig2 != null && (lockscreenInfo = templateConfig2.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            this.$it.checkVideoValid(Boxing.boxInt(wallpaperInfo.getMagicType()), wallpaperInfo.getOriginResourcePath());
        }
        return Unit.INSTANCE;
    }
}

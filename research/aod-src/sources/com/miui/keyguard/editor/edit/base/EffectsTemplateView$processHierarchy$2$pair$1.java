package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.segment.CacheInfo;
import java.io.File;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
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
/* JADX INFO: compiled from: EffectsTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.base.EffectsTemplateView$processHierarchy$2$pair$1", f = "EffectsTemplateView.kt", l = {}, m = "invokeSuspend")
public final class EffectsTemplateView$processHierarchy$2$pair$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Pair<? extends String, ? extends CacheInfo>>, Object> {
    public final /* synthetic */ WallpaperTypeInfo $info;
    public int label;
    public final /* synthetic */ EffectsTemplateView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EffectsTemplateView$processHierarchy$2$pair$1(WallpaperTypeInfo wallpaperTypeInfo, EffectsTemplateView effectsTemplateView, Continuation continuation) {
        super(2, continuation);
        this.$info = wallpaperTypeInfo;
        this.this$0 = effectsTemplateView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EffectsTemplateView$processHierarchy$2$pair$1(this.$info, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((EffectsTemplateView$processHierarchy$2$pair$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String strCalculateMD5;
        String resourcePath;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            if (this.$info.getResourcePath() != null) {
                TemplateApi.Companion companion = TemplateApi.Companion;
                Context context = this.this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                TemplateApi companion2 = companion.getInstance(context);
                if (Intrinsics.areEqual(this.$info.getType(), "gallery") && this.this$0.getTemplateSource() == -1) {
                    resourcePath = companion2.getCurrentTemplateWallpaperPath();
                } else {
                    resourcePath = this.$info.getResourcePath();
                    Intrinsics.checkNotNull(resourcePath);
                }
                File file = new File(resourcePath);
                strCalculateMD5 = (!file.exists() || file.length() <= 0) ? null : FileUtil.calculateMD5(file);
                if (strCalculateMD5 == null) {
                    Log.w("Keyguard-Editor-EffectsTemplateView", "processHierarchy file exists =" + file.exists() + " , size : " + file.length() + " ");
                }
            } else {
                strCalculateMD5 = null;
            }
            Log.i("Keyguard-Editor-EffectsTemplateView", "processHierarchy resourceMD5=" + strCalculateMD5);
            if (strCalculateMD5 == null) {
                return null;
            }
            return new Pair(strCalculateMD5, this.this$0.getSegmentCache().get(strCalculateMD5));
        } catch (Exception e) {
            Log.e("Keyguard-Editor-EffectsTemplateView", "calculate md5 error", e);
            return null;
        }
    }
}

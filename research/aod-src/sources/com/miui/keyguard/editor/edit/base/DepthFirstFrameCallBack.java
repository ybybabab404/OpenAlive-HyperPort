package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;

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
public final class DepthFirstFrameCallBack {
    public final WeakReference templateWeak;

    public DepthFirstFrameCallBack(WeakReference templateWeak) {
        Intrinsics.checkNotNullParameter(templateWeak, "templateWeak");
        this.templateWeak = templateWeak;
    }

    public final void onFirstFrame() {
        BaseTemplateView baseTemplateView = (BaseTemplateView) this.templateWeak.get();
        if (baseTemplateView != null) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new DepthFirstFrameCallBack$onFirstFrame$1$1(baseTemplateView, null), 2, null);
        }
    }
}

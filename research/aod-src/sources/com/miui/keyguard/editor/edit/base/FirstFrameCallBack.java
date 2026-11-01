package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.view.View;
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
public final class FirstFrameCallBack {
    public final WeakReference ctxWeak;
    public final WeakReference foregroundWeak;
    public final WeakReference templateWeak;

    public FirstFrameCallBack(WeakReference ctxWeak, WeakReference foregroundWeak, WeakReference templateWeak) {
        Intrinsics.checkNotNullParameter(ctxWeak, "ctxWeak");
        Intrinsics.checkNotNullParameter(foregroundWeak, "foregroundWeak");
        Intrinsics.checkNotNullParameter(templateWeak, "templateWeak");
        this.ctxWeak = ctxWeak;
        this.foregroundWeak = foregroundWeak;
        this.templateWeak = templateWeak;
    }

    public final void onFirstFrame(String str) {
        BaseTemplateView baseTemplateView = (BaseTemplateView) this.templateWeak.get();
        View view = (View) this.foregroundWeak.get();
        if (baseTemplateView != null) {
            baseTemplateView.setNeedRemoveBackground(true);
        }
        Context context = (Context) this.ctxWeak.get();
        if (context != null) {
            BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), Dispatchers.getMain(), null, new FirstFrameCallBack$onFirstFrame$1$1(view, baseTemplateView, str, null), 2, null);
        }
    }
}

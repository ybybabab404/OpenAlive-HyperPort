package com.miui.keyguard.editor.edit.aiwapper;

import android.util.Log;
import com.miui.keyguard.editor.edit.base.EffectsTemplateView;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AIWallPaperManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIGenerateFinishCallback {
    public final WeakReference templateWeak;

    public AIGenerateFinishCallback(WeakReference templateWeak) {
        Intrinsics.checkNotNullParameter(templateWeak, "templateWeak");
        this.templateWeak = templateWeak;
    }

    public final void generateFinish(boolean z) {
        Log.d("AIWallPaperManager", "AI生成任务完成 - success:" + z + ":");
        EffectsTemplateView effectsTemplateView = (EffectsTemplateView) this.templateWeak.get();
        if (effectsTemplateView != null) {
            EffectsTemplateView.updateAiGenerateBtnStatus$default(effectsTemplateView, false, 1, null);
        }
    }
}

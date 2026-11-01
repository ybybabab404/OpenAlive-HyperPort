package com.miui.keyguard.editor.base;

import com.miui.keyguard.editor.edit.base.TemplateApplier;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ExitAnimationPerformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface ExitAnimationPerformer {

    /* JADX INFO: compiled from: ExitAnimationPerformer.kt */
    @Metadata
    public static final class DefaultImpls {
        public static /* synthetic */ void startExitTransformerAnimation$default(ExitAnimationPerformer exitAnimationPerformer, boolean z, TemplateApplier.ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: startExitTransformerAnimation");
            }
            if ((i & 2) != 0) {
                applyCallback = null;
            }
            if ((i & 4) != 0) {
                exitAnimationPerformer2 = null;
            }
            exitAnimationPerformer.startExitTransformerAnimation(z, applyCallback, exitAnimationPerformer2);
        }
    }

    void startExitTransformerAnimation(boolean z, TemplateApplier.ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer);
}

package com.miui.keyguard.editor.base;

import android.view.View;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: Transition.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface TransitionPerformer {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: Transition.kt */
    @Metadata
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int FORWARD_FREE = 0;

        public final int getFORWARD_FREE() {
            return FORWARD_FREE;
        }

        public final TransitionPerformer getImpl(TransitionLayer transitionLayer) {
            Intrinsics.checkNotNullParameter(transitionLayer, "transitionLayer");
            return new TransitionPerformerImpl(transitionLayer);
        }
    }

    /* JADX INFO: compiled from: Transition.kt */
    @Metadata
    public static final class DefaultImpls {
    }

    boolean onConfigChanged();

    void performBackwardTransition(BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, Function0 function0);

    void performForwardTransition(View view, View view2, boolean z);

    void performSlideIn();

    void performSlideOut(View view, View view2, View view3);

    void prepareForwardTransition(PreviewHolder previewHolder, TransitionHost transitionHost);
}

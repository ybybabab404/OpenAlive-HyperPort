package com.miui.keyguard.editor.view;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class LockScreenTransformerLayerKt {
    public static final Function1 backupRunnable = new Function1() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayerKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return LockScreenTransformerLayerKt.backupRunnable$lambda$2((LockScreenTransformerLayer) obj);
        }
    };

    public static final void addTemplatePreviewView(FrameLayout frameLayout, View view, RoundOutlineProvider roundOutlineProvider, boolean z) {
        setTemplatePreviewRoundOutline(view, roundOutlineProvider);
        if (z) {
            int childCount = frameLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = frameLayout.getChildAt(i);
                if (!(childAt instanceof TextView)) {
                    frameLayout.removeView(childAt);
                }
            }
        }
        frameLayout.addView(view, 0);
    }

    public static /* synthetic */ void addTemplatePreviewView$default(FrameLayout frameLayout, View view, RoundOutlineProvider roundOutlineProvider, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        addTemplatePreviewView(frameLayout, view, roundOutlineProvider, z);
    }

    public static final Unit backupRunnable$lambda$2(LockScreenTransformerLayer lockScreenTransformerLayer) {
        if (lockScreenTransformerLayer == null || lockScreenTransformerLayer.isBackupInvalid()) {
            return Unit.INSTANCE;
        }
        lockScreenTransformerLayer.onAllTransformerCompleted();
        return Unit.INSTANCE;
    }

    public static final Function1 getBackupRunnable() {
        return backupRunnable;
    }

    public static final void setTemplatePreviewRoundOutline(View view, RoundOutlineProvider roundOutlineProvider) {
        view.setClipToOutline(true);
        float f = 1;
        roundOutlineProvider.setRadius(roundOutlineProvider.getRadius() * RangesKt.coerceAtLeast(f / view.getScaleX(), f / view.getScaleY()));
        view.setOutlineProvider(roundOutlineProvider);
    }
}

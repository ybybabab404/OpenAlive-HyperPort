package com.miui.keyguard.shortcuts;

import android.os.Bundle;
import com.android.systemui.plugins.miui.keyguard.MiuiShortcutPlugin;
import com.miui.keyguard.shortcuts.controller.ShortcutMoveController;
import com.miui.keyguard.shortcuts.controller.ShortcutViewLayoutController;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ShortcutPluginImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutPluginImpl$shortcutMoveControllerCallback$1 implements ShortcutMoveController.Callback {
    public final /* synthetic */ ShortcutPluginImpl this$0;

    public ShortcutPluginImpl$shortcutMoveControllerCallback$1(ShortcutPluginImpl shortcutPluginImpl) {
        this.this$0 = shortcutPluginImpl;
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void onBackAnimationEnd() {
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onBackAnimationEnd", (Bundle) null, 2, (Object) null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void onDismissAnimationEnd() {
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onDismissAnimationEnd", (Bundle) null, 2, (Object) null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void onFullscreenAnimationEnd() {
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onFullscreenAnimationEnd", (Bundle) null, 2, (Object) null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void onFullscreenAnimationStart() {
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onFullscreenAnimationStart", (Bundle) null, 2, (Object) null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void onMoveDistanceUpdate(float f) {
        ShortcutViewLayoutController shortcutViewLayoutController = this.this$0.shortcutViewLayoutController;
        if (shortcutViewLayoutController != null) {
            shortcutViewLayoutController.cancelTipsAnimation();
        }
        Bundle bundle = new Bundle();
        bundle.putFloat("distance", f);
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            shortcutPluginCallback.onShortcutPluginCallback("onMoveDistanceUpdate", bundle);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void onOccludedAnimationEnd() {
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onOccludedAnimationEnd", (Bundle) null, 2, (Object) null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void onOccludedAnimationStart() {
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onOccludedAnimationStart", (Bundle) null, 2, (Object) null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void updateCanShowBiometricAuthentication(boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("moveHelperCanShow", z);
        bundle.putBoolean("requestFaceAuth", z2);
        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
        if (shortcutPluginCallback != null) {
            shortcutPluginCallback.onShortcutPluginCallback("updateCanShowBiometricAuthentication", bundle);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.ShortcutMoveController.Callback
    public void updateSwipingInProgress(boolean z) {
        ShortcutMoveController.Callback.DefaultImpls.updateSwipingInProgress(this, z);
    }
}

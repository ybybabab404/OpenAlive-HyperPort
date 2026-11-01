package com.miui.keyguard.shortcuts.controller;

import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.UnoccludedAnimationUtils;
import java.io.IOException;
import java.util.Collection;
import kotlin.Metadata;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ShortcutUnoccludedAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutUnoccludedAnimController$doUnoccludedAnimationForShortcut$1 extends TransitionListener {
    public final /* synthetic */ ShortcutUnoccludedAnimController this$0;

    @Override // miuix.animation.listener.TransitionListener
    public void onBegin(Object obj) {
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutUnoccludedAnimController", "doUnoccludedAnimationForShortcut, onUnoccludedAnimationStart", null, 4, null);
        this.this$0.getShortcutAnimView().initSynSurfaceTransactionApplier();
        this.this$0.getShortcutAnimView().reparentUnoccludedSurfaceControl();
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onCancel(Object obj) throws IOException {
        this.this$0.handleAnimEnd("doUnoccludedAnimationForShortcut#onCancel");
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onComplete(Object obj) throws IOException {
        this.this$0.handleAnimEnd("doUnoccludedAnimationForShortcut#onComplete");
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onUpdate(Object obj, Collection collection) {
        UnoccludedAnimationUtils unoccludedAnimationUtils = UnoccludedAnimationUtils.INSTANCE;
        UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_LEASH_WIDTH());
        UpdateInfo updateInfoFindBy2 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_LEASH_HEIGHT());
        UpdateInfo updateInfoFindBy3 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_LEASH_X());
        UpdateInfo updateInfoFindBy4 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_LEASH_Y());
        UpdateInfo updateInfoFindBy5 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_LEASH_ALPHA());
        UpdateInfo updateInfoFindBy6 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_ICON_SCALE());
        UpdateInfo updateInfoFindBy7 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_ICON_ALPHA());
        UpdateInfo updateInfoFindBy8 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_SHORTCUT_ICON_CIRCLE_ALPHA());
        this.this$0.getShortcutAnimView().doUnoccludedAnimationUpdateForShortcut(updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 1.0f, updateInfoFindBy2 != null ? updateInfoFindBy2.getFloatValue() : 1.0f, updateInfoFindBy3 != null ? updateInfoFindBy3.getFloatValue() : Float.NaN, updateInfoFindBy4 != null ? updateInfoFindBy4.getFloatValue() : Float.NaN, updateInfoFindBy5 != null ? updateInfoFindBy5.getFloatValue() : 1.0f, updateInfoFindBy6 != null ? updateInfoFindBy6.getFloatValue() : 1.8f, updateInfoFindBy7 != null ? updateInfoFindBy7.getFloatValue() : 0.0f, updateInfoFindBy8 != null ? updateInfoFindBy8.getFloatValue() : 0.4f);
    }
}

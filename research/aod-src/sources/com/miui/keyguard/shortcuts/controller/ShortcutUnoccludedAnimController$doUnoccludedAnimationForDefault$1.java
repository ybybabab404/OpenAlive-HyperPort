package com.miui.keyguard.shortcuts.controller;

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
public final class ShortcutUnoccludedAnimController$doUnoccludedAnimationForDefault$1 extends TransitionListener {
    public final /* synthetic */ ShortcutUnoccludedAnimController this$0;

    @Override // miuix.animation.listener.TransitionListener
    public void onBegin(Object obj) {
        this.this$0.getShortcutAnimView().initSynSurfaceTransactionApplier();
        this.this$0.getShortcutAnimView().reparentUnoccludedSurfaceControl();
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onCancel(Object obj) throws IOException {
        this.this$0.handleAnimEnd("doUnoccludedAnimationForDefault#onCancel");
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onComplete(Object obj) throws IOException {
        this.this$0.handleAnimEnd("doUnoccludedAnimationForDefault#onComplete");
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onUpdate(Object obj, Collection collection) {
        UnoccludedAnimationUtils unoccludedAnimationUtils = UnoccludedAnimationUtils.INSTANCE;
        UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_DEFAULT_LEASH_ALPHA());
        UpdateInfo updateInfoFindBy2 = UpdateInfo.findBy(collection, unoccludedAnimationUtils.getTAG_DEFAULT_LEASH_SCALE());
        this.this$0.getShortcutAnimView().doUnoccludedAnimationUpdateForDefault(updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 1.0f, updateInfoFindBy2 != null ? updateInfoFindBy2.getFloatValue() : 1.0f);
    }
}

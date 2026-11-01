package com.miui.keyguard.editor.utils;

import android.content.res.Configuration;
import android.util.Log;
import com.miui.keyguard.editor.edit.base.CombineEditListener;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class HideScreenshotCallback implements CombineEditListener {
    public CancellableContinuation screenshotCont;
    public final String tag;
    public AtomicInteger templateReadyCount;

    public HideScreenshotCallback(String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        this.tag = tag;
        this.templateReadyCount = new AtomicInteger(0);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getCurrentSelect() {
        return CombineEditListener.DefaultImpls.getCurrentSelect(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getDialogOffset() {
        return CombineEditListener.DefaultImpls.getDialogOffset(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheet() {
        CombineEditListener.DefaultImpls.onDismissBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheetForScreenChange(Configuration configuration) {
        CombineEditListener.DefaultImpls.onDismissBottomSheetForScreenChange(this, configuration);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissKeyboard() {
        CombineEditListener.DefaultImpls.onDismissKeyboard(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onPreviewChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onPreviewChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onScrolledChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onScrolledChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onShowBottomSheet() {
        CombineEditListener.DefaultImpls.onShowBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onTemplateReady(int i) {
        CancellableContinuation cancellableContinuation;
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "onTemplateReady: tag=" + this.tag + " id=" + i + " count=" + this.templateReadyCount);
        if (this.templateReadyCount.incrementAndGet() != 4 || (cancellableContinuation = this.screenshotCont) == null) {
            return;
        }
        Result.Companion companion = Result.Companion;
        cancellableContinuation.resumeWith(Result.m1061constructorimpl(Unit.INSTANCE));
    }

    public final Object waitTemplateReady(Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        if (this.templateReadyCount.get() >= 4) {
            Log.d("Keyguard-Editor-DeviceScreenshotHelper", "template ready:" + this.tag);
            Result.Companion companion = Result.Companion;
            cancellableContinuationImpl.resumeWith(Result.m1061constructorimpl(Unit.INSTANCE));
        } else {
            Log.d("Keyguard-Editor-DeviceScreenshotHelper", "wait template ready:" + this.tag);
            this.screenshotCont = cancellableContinuationImpl;
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }
}

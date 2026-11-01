package com.miui.keyguard.editor.view;

import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.lockscreeninfo.model.SignatureInfo;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FontFilterView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MiuiClockViewSafeHolder {
    public final DoodleInfo doodleInfo;
    public final WeakReference primaryClockView;
    public final SignatureInfo signatureInfo;
    public final String templateId;

    public MiuiClockViewSafeHolder(String str, WeakReference weakReference, DoodleInfo doodleInfo, SignatureInfo signatureInfo) {
        this.templateId = str;
        this.primaryClockView = weakReference;
        this.doodleInfo = doodleInfo;
        this.signatureInfo = signatureInfo;
    }

    public /* synthetic */ MiuiClockViewSafeHolder(String str, WeakReference weakReference, DoodleInfo doodleInfo, SignatureInfo signatureInfo, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : weakReference, (i & 4) != 0 ? null : doodleInfo, (i & 8) != 0 ? null : signatureInfo);
    }

    public final DoodleInfo getDoodleInfo() {
        return this.doodleInfo;
    }

    public final WeakReference getPrimaryClockView() {
        return this.primaryClockView;
    }

    public final SignatureInfo getSignatureInfo() {
        return this.signatureInfo;
    }

    public final String getTemplateId() {
        return this.templateId;
    }
}

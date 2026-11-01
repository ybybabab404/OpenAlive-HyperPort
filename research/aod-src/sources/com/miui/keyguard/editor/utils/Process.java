package com.miui.keyguard.editor.utils;

import android.app.Application;
import android.text.TextUtils;
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
/* JADX INFO: compiled from: Constants.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class Process {
    public static final Process INSTANCE = new Process();

    public final boolean isAodProcess() {
        return TextUtils.equals("com.miui.aod", Application.getProcessName());
    }

    public final boolean isKeyguardeditorProcess() {
        return TextUtils.equals("com.miui.aod:keyguardeditor", Application.getProcessName());
    }

    public final boolean isThemeManagerProcess() {
        return TextUtils.equals("com.android.thememanager", Application.getProcessName());
    }
}

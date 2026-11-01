package com.miui.keyguard.editor;

import android.os.Bundle;
import android.util.Log;
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
/* JADX INFO: compiled from: EditorActivityForSettings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorActivityForSettings extends EditorActivity {
    @Override // com.miui.keyguard.editor.EditorActivity
    public int getTheme(boolean z) {
        Log.d("EditorActivityForSettings", "getTheme: isNight=" + z);
        return z ? R.style.KgEditorActivityForSettingsThemeNight : R.style.KgEditorActivityForSettingsThemeLight;
    }

    @Override // com.miui.keyguard.editor.EditorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        finishEditorTask();
    }
}

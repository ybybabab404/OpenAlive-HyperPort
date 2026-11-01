package com.miui.keyguard.editor;

import android.util.Log;
import com.miui.keyguard.editor.homepage.MainFragment;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SettingsEditorServiceManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsEditorServiceManager {
    public static final SettingsEditorServiceManager INSTANCE = new SettingsEditorServiceManager();
    public static final String TAG = "SettingsKeyguardEditorServiceManager";
    public static WeakReference activityReference;

    public final void setActivity(EditorActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        WeakReference weakReference = activityReference;
        if (weakReference == null) {
            activityReference = new WeakReference(activity);
            return;
        }
        Log.d(TAG, "already have activity=" + (weakReference != null ? (EditorActivity) weakReference.get() : null));
        activityReference = new WeakReference(activity);
    }

    public final void updateCrossPager() {
        EditorActivity editorActivity;
        MainFragment mainFragment;
        WeakReference weakReference = activityReference;
        if (weakReference == null || (editorActivity = (EditorActivity) weakReference.get()) == null || (mainFragment = editorActivity.getMainFragment()) == null) {
            return;
        }
        mainFragment.reloadAllData();
    }
}

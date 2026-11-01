package com.miui.keyguard.editor;

import android.content.Context;
import android.view.OrientationEventListener;
import com.miui.keyguard.editor.utils.DeviceUtil;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: EditorActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorOrientationEventListener {
    public final WeakReference editorActivityWeak;
    public OrientationEventListener orientationListener;

    public EditorOrientationEventListener(WeakReference editorActivityWeak) {
        Intrinsics.checkNotNullParameter(editorActivityWeak, "editorActivityWeak");
        this.editorActivityWeak = editorActivityWeak;
    }

    public final WeakReference getEditorActivityWeak() {
        return this.editorActivityWeak;
    }

    public final void registerListener() {
        OrientationEventListener orientationEventListener;
        Context applicationContext;
        if (this.orientationListener == null) {
            EditorActivity editorActivity = (EditorActivity) this.editorActivityWeak.get();
            if (editorActivity == null || (applicationContext = editorActivity.getApplicationContext()) == null) {
                return;
            } else {
                this.orientationListener = new OrientationEventListener(applicationContext) { // from class: com.miui.keyguard.editor.EditorOrientationEventListener.registerListener.1
                    @Override // android.view.OrientationEventListener
                    public void onOrientationChanged(int i) {
                        EditorActivity editorActivity2;
                        EditorActivity editorActivity3 = (EditorActivity) this.getEditorActivityWeak().get();
                        if (editorActivity3 == null || editorActivity3.getOnConfigChanging() || (editorActivity2 = (EditorActivity) this.getEditorActivityWeak().get()) == null) {
                            return;
                        }
                        editorActivity2.onOrientationChange();
                    }
                };
            }
        }
        EditorActivity editorActivity2 = (EditorActivity) this.editorActivityWeak.get();
        if (editorActivity2 != null) {
            editorActivity2.setOrientation(DeviceUtil.INSTANCE.getScreenOrientation(editorActivity2));
        }
        OrientationEventListener orientationEventListener2 = this.orientationListener;
        if (orientationEventListener2 == null || orientationEventListener2 == null || !orientationEventListener2.canDetectOrientation() || (orientationEventListener = this.orientationListener) == null) {
            return;
        }
        orientationEventListener.enable();
    }

    public final void unregisterListener() {
        OrientationEventListener orientationEventListener = this.orientationListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
        this.orientationListener = null;
    }
}

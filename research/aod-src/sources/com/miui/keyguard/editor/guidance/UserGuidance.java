package com.miui.keyguard.editor.guidance;

import android.content.Context;
import android.provider.Settings;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.SharedPreferencesUtil;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.TransformerEventListener;
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
/* JADX INFO: compiled from: UserGuidance.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class UserGuidance implements TransformerEventListener {
    public static boolean isEditorStartPerformExitAnim;
    public static final UserGuidance INSTANCE = new UserGuidance();
    public static final int[] texts = {R.string.kg_guidance_dialog_tip_browse_more, R.string.kg_guidance_dialog_tip_customize};

    public static final void saveCalledFromSystemUI$lambda$5(Context context) {
        if (Settings.System.getInt(context.getContentResolver(), "is_lock_edit_called_from_system_ui_key", 0) == 0) {
            Settings.System.putInt(context.getContentResolver(), "is_lock_edit_called_from_system_ui_key", 1);
        }
    }

    public final boolean isNewUser(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return SharedPreferencesUtil.INSTANCE.getBoolean(context, "is_new_user", true);
    }

    public final void markNotNewUser(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferencesUtil.INSTANCE.setBoolean(context, "is_new_user", false);
    }

    @Override // com.miui.keyguard.editor.view.TransformerEventListener
    public void onTransformerBegin(int i) {
        isEditorStartPerformExitAnim = i == 4;
    }

    @Override // com.miui.keyguard.editor.view.TransformerEventListener
    public void onTransformerFinished(int i, boolean z) {
    }

    public final void saveCalledFromSystemUI(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.guidance.UserGuidance$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UserGuidance.saveCalledFromSystemUI$lambda$5(context);
            }
        });
    }
}

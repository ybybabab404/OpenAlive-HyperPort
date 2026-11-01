package com.miui.keyguard.editor;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.miui.keyguardeditor.IMiuiKeyguardEditorCallback;
import com.miui.keyguardeditor.IMiuiKeyguardEditorService;
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
/* JADX INFO: compiled from: KeyguardEditorService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class KeyguardEditorService extends Service {
    public final String TAG = "KeyguardEditorService";
    public final IMiuiKeyguardEditorService.Stub mBinder = new IMiuiKeyguardEditorService.Stub() { // from class: com.miui.keyguard.editor.KeyguardEditorService$mBinder$1
        @Override // com.miui.keyguardeditor.IMiuiKeyguardEditorService
        public void registerCallback(IMiuiKeyguardEditorCallback callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            Log.d(this.this$0.TAG, "registerCallback");
            EditorServiceManager.Companion.getInstance().setIMiuiKeyguardEditorCallback(callback);
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // com.miui.keyguardeditor.IMiuiKeyguardEditorService
        public void sendCommand(String action, Bundle bundle) {
            Intrinsics.checkNotNullParameter(action, "action");
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Log.d(this.this$0.TAG, "editor service action " + action);
            switch (action.hashCode()) {
                case -1440369352:
                    if (action.equals("action_start_transformer_animation")) {
                        EditorServiceManager.Companion.getInstance().onReceivedStartTransformerAnimation(bundle.getBoolean("isUnlock"), bundle.getLong("startTouchTime"), bundle.getLong("startAnimTime"));
                    }
                    break;
                case -1146553965:
                    if (action.equals("action_keyguard_unlocked")) {
                        EditorServiceManager.Companion.getInstance().onReceiveKeyguardUnlocked();
                        break;
                    }
                    break;
                case -257625501:
                    if (action.equals("action_start_exit_transformer_animation")) {
                        EditorServiceManager.Companion.getInstance().onReceivedStartExitTransformerAnimation();
                        break;
                    }
                    break;
                case 207574099:
                    if (action.equals("action_exit_without_unlock")) {
                        EditorServiceManager.Companion.getInstance().onReceiveExitWithoutUnLockCommand();
                        break;
                    }
                    break;
                case 788205648:
                    if (action.equals("action_finish_editor")) {
                        EditorServiceManager.Companion.getInstance().onReceiveFinishCommand();
                        break;
                    }
                    break;
                case 1209939624:
                    if (action.equals("action_hide_loading")) {
                        EditorServiceManager.Companion.getInstance().onReceiveHideLoadingCommand();
                        break;
                    }
                    break;
            }
        }

        @Override // com.miui.keyguardeditor.IMiuiKeyguardEditorService
        public void unregisterCallback() {
            Log.d(this.this$0.TAG, "unregisterCallback");
            EditorServiceManager.Companion.getInstance().setIMiuiKeyguardEditorCallback(null);
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(this.TAG, "onBind");
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d(this.TAG, "onCreate");
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.TAG, "onDestroy");
    }
}

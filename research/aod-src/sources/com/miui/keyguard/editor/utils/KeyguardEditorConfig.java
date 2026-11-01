package com.miui.keyguard.editor.utils;

/* JADX INFO: loaded from: classes2.dex */
public class KeyguardEditorConfig {
    public static volatile KeyguardEditorConfig instance;
    public boolean isInitCross = true;

    public static KeyguardEditorConfig getInstance() {
        if (instance == null) {
            synchronized (KeyguardEditorConfig.class) {
                try {
                    if (instance == null) {
                        instance = new KeyguardEditorConfig();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return instance;
    }

    public boolean isInitCross() {
        return this.isInitCross;
    }
}

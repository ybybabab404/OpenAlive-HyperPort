package com.miui.aod.backup;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import miui.app.backup.FullBackupAgent;

/* JADX INFO: loaded from: classes.dex */
public class TinyKeyguardBackupUtil {
    public static void backupTinyKeyguardData(FullBackupAgent fullBackupAgent) {
        try {
            Log.d("TinyKeyguardBackupAgent", "backupTinyKeyguardData begain");
            Class<?> cls = Class.forName("com.miui.tinykeyguard.editor.utils.TinyKeyguardBackupDelegate");
            cls.getDeclaredMethod("backupTinyKeyguardData", FullBackupAgent.class).invoke(cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]), fullBackupAgent);
            Log.d("TinyKeyguardBackupAgent", "backup TinyKeyguardData finished  ---");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void backupTinyKeyguardDataCompleted(Context context) {
        try {
            Log.d("TinyKeyguardBackupAgent", "tarAttaches: backup Tiny Keyguard Data Start");
            Class<?> cls = Class.forName("com.miui.tinykeyguard.editor.utils.TinyKeyguardBackupDelegate");
            cls.getDeclaredMethod("backupTinyKeyguardDataCompleted", Context.class).invoke(cls.newInstance(), context);
            Log.d("TinyKeyguardBackupAgent", "tarAttaches: backup Tiny Keyguard Data Completed  ---");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
    }

    public static void restoreTinyKeyguardData(ParcelFileDescriptor parcelFileDescriptor, String str, Context context) {
        try {
            Log.e("TinyKeyguardBackupAgent", "onAttachRestore: restore Tiny Keyguard Data Start");
            Class<?> cls = Class.forName("com.miui.tinykeyguard.editor.utils.TinyKeyguardBackupDelegate");
            cls.getDeclaredMethod("tryRestoreTinyKeyguardData", ParcelFileDescriptor.class, String.class, Context.class).invoke(cls.newInstance(), parcelFileDescriptor, str, context);
            Log.e("TinyKeyguardBackupAgent", "onAttachRestore: restore Tiny Keyguard Data finished ---");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InstantiationException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
    }
}

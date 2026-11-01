package com.miui.aod.util.foldchange;

import android.os.ServiceManager;
import android.util.Log;
import android.view.IDisplayFoldListener;
import android.view.IWindowManager;

/* JADX INFO: loaded from: classes.dex */
public final class FoldDisplayChangeUtils {
    public static void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).registerDisplayFoldListener(iDisplayFoldListener);
        } catch (Exception e) {
            Log.e("AodFoldDisplayChangeUtils", "call error while get registerDisplayFoldListener", e);
        }
    }

    public static void unRegisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).unregisterDisplayFoldListener(iDisplayFoldListener);
        } catch (Exception e) {
            Log.e("AodFoldDisplayChangeUtils", "call error while get unregisterDisplayFoldListener", e);
        }
    }
}

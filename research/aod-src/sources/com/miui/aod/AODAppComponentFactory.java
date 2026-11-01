package com.miui.aod;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import androidx.core.app.AppComponentFactory;
import androidx.fragment.app.FragmentActivity;
import com.miui.aod.common.BaseBroadcastReceiver;

/* JADX INFO: loaded from: classes.dex */
public class AODAppComponentFactory extends AppComponentFactory {
    @Override // androidx.core.app.AppComponentFactory
    public Activity instantiateActivityCompat(ClassLoader classLoader, String str, Intent intent) {
        Activity activityInstantiateActivityCompat = super.instantiateActivityCompat(classLoader, str, intent);
        if (!str.contains("Edit") && !"com.miui.aod:tinykeyguardeditor".equals(Application.getProcessName()) && (activityInstantiateActivityCompat instanceof FragmentActivity)) {
            ((FragmentActivity) activityInstantiateActivityCompat).getLifecycle().addObserver(LifeCycleObserver.INSTANCE);
        }
        return activityInstantiateActivityCompat;
    }

    @Override // androidx.core.app.AppComponentFactory
    public BroadcastReceiver instantiateReceiverCompat(ClassLoader classLoader, String str, Intent intent) {
        BroadcastReceiver broadcastReceiverInstantiateReceiverCompat = super.instantiateReceiverCompat(classLoader, str, intent);
        if (broadcastReceiverInstantiateReceiverCompat instanceof BaseBroadcastReceiver) {
            ((BaseBroadcastReceiver) broadcastReceiverInstantiateReceiverCompat).getLifecycle().addObserver(LifeCycleObserver.INSTANCE);
        }
        return broadcastReceiverInstantiateReceiverCompat;
    }
}

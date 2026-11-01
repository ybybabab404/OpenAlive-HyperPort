package com.bumptech.glide.manager;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
final class DefaultConnectivityMonitor implements ConnectivityMonitor {
    public final Context context;
    public final ConnectivityMonitor.ConnectivityListener listener;

    public DefaultConnectivityMonitor(Context context, ConnectivityMonitor.ConnectivityListener connectivityListener) {
        this.context = context.getApplicationContext();
        this.listener = connectivityListener;
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        register();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        unregister();
    }

    public final void register() {
        SingletonConnectivityReceiver.get(this.context).register(this.listener);
    }

    public final void unregister() {
        SingletonConnectivityReceiver.get(this.context).unregister(this.listener);
    }
}

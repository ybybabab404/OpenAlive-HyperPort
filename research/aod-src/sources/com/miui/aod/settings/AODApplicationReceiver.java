package com.miui.aod.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class AODApplicationReceiver extends BroadcastReceiver {
    public static final String TAG = "AODApplicationReceiver";
    public static AODApplicationReceiver receiver;
    public final List actions = new ArrayList();

    public interface AODReceiverAction {
        void onReceiveAction(String str, Intent intent);
    }

    public static void addAction(AODReceiverAction aODReceiverAction) {
        AODApplicationReceiver aODApplicationReceiver = receiver;
        if (aODApplicationReceiver == null) {
            return;
        }
        aODApplicationReceiver.actions.add(aODReceiverAction);
    }

    public static void register(Context context) {
        if (receiver != null) {
            return;
        }
        Log.d(TAG, "register: ");
        IntentFilter intentFilter = new IntentFilter("miui.keyguard.editor.action.LINKAGE_STYLE_APPLY_STATE_CHANGED");
        AODApplicationReceiver aODApplicationReceiver = new AODApplicationReceiver();
        receiver = aODApplicationReceiver;
        context.registerReceiver(aODApplicationReceiver, intentFilter, null, null, 2);
    }

    public static void removeAction(AODReceiverAction aODReceiverAction) {
        AODApplicationReceiver aODApplicationReceiver = receiver;
        if (aODApplicationReceiver == null) {
            return;
        }
        aODApplicationReceiver.actions.remove(aODReceiverAction);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, final Intent intent) {
        final String action = intent.getAction();
        this.actions.forEach(new Consumer() { // from class: com.miui.aod.settings.AODApplicationReceiver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AODApplicationReceiver.AODReceiverAction) obj).onReceiveAction(action, intent);
            }
        });
    }
}

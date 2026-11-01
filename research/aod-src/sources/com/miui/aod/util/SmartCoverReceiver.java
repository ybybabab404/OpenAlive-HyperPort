package com.miui.aod.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.miui.aod.doze.DozeMachine;
import com.miui.aod.doze.DozeService;
import com.miui.aod.widget.AODSettings;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class SmartCoverReceiver extends BroadcastReceiver {
    public static volatile SmartCoverReceiver receiver;
    public WeakReference mRef;

    private SmartCoverReceiver() {
    }

    public static synchronized SmartCoverReceiver getReceiver() {
        try {
            if (receiver == null) {
                receiver = new SmartCoverReceiver();
            }
        } catch (Throwable th) {
            throw th;
        }
        return receiver;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.i("SmartCoverReceiver", "onReceive: " + intent.getAction());
        if ("miui.intent.action.SMART_COVER".equals(intent.getAction())) {
            boolean booleanExtra = intent.getBooleanExtra("is_smart_cover_open", false);
            boolean z = !booleanExtra;
            if (!AODSettings.isSupportSmartCoverAodStyle()) {
                Log.i("SmartCoverReceiver", "onReceive: not supported device isClosed: " + z);
                return;
            }
            AODSettings.setSmartCoverState(z);
            DozeService dozeService = (DozeService) this.mRef.get();
            if (dozeService == null || booleanExtra || dozeService.getDozeMachine() == null) {
                return;
            }
            DozeMachine.State state = dozeService.getDozeMachine().getState();
            if (state == DozeMachine.State.DOZE_AOD || state == DozeMachine.State.DOZE) {
                dozeService.getDozeMachine().requestState(DozeMachine.State.DOZE_INTO_COVERMODE);
            }
        }
    }

    public void setDozeService(DozeService dozeService) {
        this.mRef = new WeakReference(dozeService);
    }
}

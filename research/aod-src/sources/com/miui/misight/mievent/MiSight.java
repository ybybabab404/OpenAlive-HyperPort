package com.miui.misight.mievent;

import android.util.Log;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes2.dex */
public class MiSight {
    static {
        System.loadLibrary("mievent");
    }

    private static native int nativeSendEvent(byte[] bArr);

    public static void sendEvent(MiEvent miEvent) {
        Log.d("MiSightSdkMiSight:", " sendEvent id=" + miEvent.getEventId());
        nativeSendEvent(("EventId " + miEvent.getEventId() + " -t " + (System.currentTimeMillis() / 1000) + " -paraList " + miEvent.getMiEventStr()).getBytes(StandardCharsets.UTF_8));
    }
}

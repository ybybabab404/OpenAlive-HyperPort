package com.xiaomi.onetrack.c;

import android.content.Intent;

/* JADX INFO: loaded from: classes2.dex */
class g implements Runnable {
    final /* synthetic */ Intent a;
    final /* synthetic */ f b;

    public g(f fVar, Intent intent) {
        this.b = fVar;
        this.a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        String action = this.a.getAction();
        if (action.equals("android.intent.action.SCREEN_OFF") || action.equals("android.intent.action.SCREEN_ON")) {
            com.xiaomi.onetrack.util.s.a("EventManager", "screen on/off");
            ag.a().a(action.equals("android.intent.action.SCREEN_ON") ? 0 : 2, false);
            com.xiaomi.onetrack.a.c.b.a().b(false);
        }
    }
}

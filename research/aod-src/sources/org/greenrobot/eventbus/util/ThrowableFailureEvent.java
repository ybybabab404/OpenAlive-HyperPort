package org.greenrobot.eventbus.util;

/* JADX INFO: loaded from: classes3.dex */
public class ThrowableFailureEvent implements HasExecutionScope {
    public final boolean suppressErrorUi = false;
    public final Throwable throwable;

    public ThrowableFailureEvent(Throwable th) {
        this.throwable = th;
    }
}

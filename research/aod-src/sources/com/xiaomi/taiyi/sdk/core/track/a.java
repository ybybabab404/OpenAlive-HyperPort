package com.xiaomi.taiyi.sdk.core.track;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class a {
    public final TrackSettings a;
    public final long b;
    public long c;
    public int d;

    public a(String api, TrackSettings settings) {
        Intrinsics.checkNotNullParameter(api, "api");
        Intrinsics.checkNotNullParameter(settings, "settings");
        this.a = settings;
        this.b = ((long) settings.getPeriod()) * 60000;
    }
}

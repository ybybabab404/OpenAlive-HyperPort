package com.xiaomi.taiyi.sdk.core.connect;

import android.content.Context;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class a extends FunctionReferenceImpl implements Function1 {
    public static final a a = new a();

    public a() {
        super(1, AIManager.class, "<init>", "<init>(Landroid/content/Context;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Context p0 = (Context) obj;
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new AIManager(p0);
    }
}

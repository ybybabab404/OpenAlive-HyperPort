package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.transfer.core.IAIClient;
import com.xiaomi.taiyi.sdk.transfer.core.IAIMemory;
import com.xiaomi.taiyi.sdk.transfer.data.AIHeaders;
import com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryPool;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class b extends IAIClient.Stub {
    public final AIAbility a;
    public final String b;
    public final String c;
    public final int[] d;
    public final Lazy e;
    public final a f;

    public b(AIAbility ability) {
        Intrinsics.checkNotNullParameter(ability, "ability");
        this.a = ability;
        this.b = "AIClient(" + ability.getId() + ")";
        this.c = ability.getId();
        this.d = ability.getSettings().getMemoryPool();
        this.e = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.b$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return b.a(this.f$0);
            }
        });
        this.f = new a(this);
    }

    public static final ShareMemoryPool a(b bVar) {
        return new ShareMemoryPool(bVar.c, bVar.d);
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
    public final String getId() {
        return this.a.getId();
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
    public final AIHeaders getInfo() {
        return this.a.createRequest().getHeaders();
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
    public final IAIMemory getMemory() {
        return this.f;
    }
}

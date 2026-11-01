package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.transfer.core.IAIMemory;
import com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryPool;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class a extends IAIMemory.Stub {
    public final /* synthetic */ b a;

    public a(b bVar) {
        this.a = bVar;
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
    public final void free(List shmIds) {
        Intrinsics.checkNotNullParameter(shmIds, "shmIds");
        List<String> listFree = ((ShareMemoryPool) this.a.e.getValue()).free((List<String>) shmIds);
        SdkLog.i(this.a.b, "free " + shmIds + " -> " + listFree);
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
    public final ShareMemoryPool get() {
        return (ShareMemoryPool) this.a.e.getValue();
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
    public final void sync(ShareMemoryPool shareMemoryPool) {
        if (shareMemoryPool == null) {
            return;
        }
        ((ShareMemoryPool) this.a.e.getValue()).add(shareMemoryPool.all());
        b bVar = this.a;
        SdkLog.i(bVar.b, "sync " + shareMemoryPool + " -> " + ((ShareMemoryPool) bVar.e.getValue()));
    }
}

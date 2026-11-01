package com.xiaomi.taiyi.sdk.core;

import android.content.Context;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.data.ServiceInfo;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.core.track.ProcTrack;
import com.xiaomi.taiyi.sdk.transfer.core.IAIManager;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class g {
    public final ServiceInfo a;
    public final String b;
    public final j c;
    public final ConcurrentHashMap d;

    public g(ServiceInfo serviceId) {
        Intrinsics.checkNotNullParameter(serviceId, "serviceId");
        this.a = serviceId;
        this.b = "RemoteAbilityLoader(" + serviceId.getId() + ")";
        this.c = new j(serviceId);
        this.d = new ConcurrentHashMap();
    }

    public final AIResponse a(Context context, AIRequest request) {
        Pair pair;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        if (this.c.b()) {
            IAIManager iAIManager = this.c.f;
            pair = new Pair(iAIManager, Integer.valueOf(iAIManager == null ? 10101 : 0));
        } else {
            ProcTrack.Companion companion = ProcTrack.Companion;
            companion.startBind(request);
            pair = this.c.a(context);
            companion.endBind(request);
        }
        IAIManager iAIManager2 = (IAIManager) pair.component1();
        int iIntValue = ((Number) pair.component2()).intValue();
        if (iAIManager2 == null) {
            AIResponse aIResponseCreate = AIResponse.CREATOR.create(request);
            aIResponseCreate.setCode(iIntValue);
            return aIResponseCreate;
        }
        try {
            ProcTrack.Companion companion2 = ProcTrack.Companion;
            companion2.startLoad(request);
            AIResponse aIResponseLoadSync = iAIManager2.loadSync(request);
            companion2.endLoad(request);
            return aIResponseLoadSync;
        } catch (Throwable th) {
            try {
                SdkLog.e(this.b, "load bindSync exception: " + th.getMessage());
                return AIResponse.CREATOR.create(request, ErrorCode.ERROR_SERVICE_INVALID);
            } finally {
                ProcTrack.Companion.endLoad(request);
            }
        }
    }

    public final String toString() {
        return this.b;
    }
}

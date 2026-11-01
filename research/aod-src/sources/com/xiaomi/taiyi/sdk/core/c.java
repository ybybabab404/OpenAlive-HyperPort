package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AICallback;
import com.xiaomi.taiyi.sdk.core.ability.AbilityCallback;
import com.xiaomi.taiyi.sdk.core.ability.ServiceAbility;
import com.xiaomi.taiyi.sdk.core.connect.AIManager;
import com.xiaomi.taiyi.sdk.transfer.core.IAIAbility;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class c implements AICallback {
    public final /* synthetic */ String a;
    public final /* synthetic */ g b;
    public final /* synthetic */ AIManager c;

    @Override // com.xiaomi.taiyi.sdk.common.AICallback
    public final void onResponse(AIResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        IAIAbility ability = response.getAbility();
        SdkLog.i("AIManager", "loadAsyncByRemote " + this.a + " onResponse " + response + ". ability = " + ability);
        if (ability != null) {
            ServiceAbility serviceAbility = new ServiceAbility(ability, this.b.a);
            this.c.g.put(this.a, serviceAbility);
            Set set = (Set) this.c.f.get(this.a);
            if (set != null) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    ((AbilityCallback) it.next()).onSuccess(serviceAbility);
                }
            }
        } else {
            Set set2 = (Set) this.c.f.get(this.a);
            if (set2 != null) {
                Iterator it2 = set2.iterator();
                while (it2.hasNext()) {
                    ((AbilityCallback) it2.next()).onFailure(response.getCode());
                }
            }
        }
        this.c.f.remove(this.a);
    }
}

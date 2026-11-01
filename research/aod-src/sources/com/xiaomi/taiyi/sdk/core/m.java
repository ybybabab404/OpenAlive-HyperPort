package com.xiaomi.taiyi.sdk.core;

import android.content.ContentProviderClient;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.utils.AIUtils;
import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.core.track.ProcTrack;
import com.xiaomi.taiyi.sdk.core.track.TrackSettings;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class m {
    public static final l e = new l();
    public final Context a;
    public final Lazy b = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.m$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return m.a();
        }
    });
    public final HashMap c = new HashMap();
    public final Lazy d = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.m$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(m.a(this.f$0));
        }
    });

    public m(Context context) {
        this.a = context;
    }

    public static final Handler a() {
        HandlerThread handlerThread = new HandlerThread("AITrack");
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    public static final void a(AIRequest request, m mVar, TrackSettings trackSettings, AIResponse response, AIRequest aIRequest) {
        ProcTrack procTrackCreate;
        String api = request.getApi();
        com.xiaomi.taiyi.sdk.core.track.a aVar = (com.xiaomi.taiyi.sdk.core.track.a) mVar.c.get(api);
        if (aVar == null) {
            aVar = new com.xiaomi.taiyi.sdk.core.track.a(api, trackSettings);
            mVar.c.put(api, aVar);
        }
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(response, "response");
        if (aVar.a.getType() == 0 || aVar.d >= aVar.a.getNum() - 1 || ExtsKt.bootMills() - aVar.c >= aVar.b) {
            aVar.c = ExtsKt.bootMills();
            int i = aVar.d + 1;
            aVar.d = 0;
            procTrackCreate = ProcTrack.Companion.create(request, response);
            procTrackCreate.setSample(i);
        } else {
            aVar.d++;
            procTrackCreate = null;
        }
        if (procTrackCreate == null) {
            return;
        }
        aIRequest.getBody().putBundle("app_track", procTrackCreate.bundle());
        mVar.a(aIRequest);
    }

    public static final boolean a(m mVar) {
        return Boolean.parseBoolean(AIUtils.getAppMetadata(mVar.a, "com.xiaomi.aiservice", "ai_track_enable"));
    }

    public final void a(AIAbility ability, final AIRequest request, final AIResponse response) {
        Intrinsics.checkNotNullParameter(ability, "ability");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(response, "response");
        if (!((Boolean) this.d.getValue()).booleanValue()) {
            SdkLog.w("TrackManager", "track not support!");
            return;
        }
        final TrackSettings trackSettings = ability.getSettings().getTrackSettings();
        final AIRequest aIRequestCreateRequest = ability.createRequest();
        ((Handler) this.b.getValue()).post(new Runnable() { // from class: com.xiaomi.taiyi.sdk.core.m$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                m.a(request, this, trackSettings, response, aIRequestCreateRequest);
            }
        });
    }

    public final void a(AIRequest aIRequest) {
        Pair pair;
        try {
            ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = this.a.getContentResolver().acquireUnstableContentProviderClient("com.xiaomi.aiservice.authority.data");
            pair = new Pair(contentProviderClientAcquireUnstableContentProviderClient, Integer.valueOf(contentProviderClientAcquireUnstableContentProviderClient == null ? ErrorCode.ERROR_PROVIDER_EMPTY : 0));
        } catch (DeadObjectException e2) {
            SdkLog.e("TrackManager", "track DeadObjectException. " + e2.getMessage());
            pair = new Pair(null, Integer.valueOf(ErrorCode.ERROR_PROVIDER_DEAD));
        } catch (RuntimeException e3) {
            SdkLog.e("TrackManager", "track RuntimeException. " + e3.getMessage());
            pair = new Pair(null, Integer.valueOf(ErrorCode.ERROR_PROVIDER_NOT_FOUND));
        } catch (Throwable th) {
            SdkLog.e("TrackManager", "track Throwable. " + th.getMessage());
            pair = new Pair(null, Integer.valueOf(ErrorCode.ERROR_PROVIDER_ACQUIRE_ERROR));
        }
        ContentProviderClient contentProviderClient = (ContentProviderClient) pair.component1();
        if (contentProviderClient == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(AIKeys.KEY_REQUEST, aIRequest);
        try {
            try {
                try {
                    contentProviderClient.call("com.xiaomi.aiservice.authority.data", "app_track", "", bundle);
                } catch (DeadObjectException e4) {
                    SdkLog.e("TrackManager", "track Dead. " + e4.getMessage());
                }
            } catch (Throwable th2) {
                SdkLog.e("TrackManager", "track Error. " + th2.getMessage());
            }
        } finally {
            contentProviderClient.close();
        }
    }
}

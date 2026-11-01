package com.xiaomi.taiyi.sdk.core.connect;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import com.xiaomi.taiyi.sdk.base.data.AIDetail;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.data.ServiceInfo;
import com.xiaomi.taiyi.sdk.base.utils.AIUtils;
import com.xiaomi.taiyi.sdk.base.utils.IDLock;
import com.xiaomi.taiyi.sdk.base.utils.IDLockManager;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.base.utils.SingletonHolder;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.common.ServiceConnector;
import com.xiaomi.taiyi.sdk.core.AIQuery;
import com.xiaomi.taiyi.sdk.core.ability.IAbilityLoader;
import com.xiaomi.taiyi.sdk.core.ability.ServiceAbility;
import com.xiaomi.taiyi.sdk.core.g;
import com.xiaomi.taiyi.sdk.core.track.ProcTrack;
import com.xiaomi.taiyi.sdk.transfer.core.IAIAbility;
import com.xiaomi.taiyi.sdk.transfer.core.IAICoreService;
import com.xiaomi.taiyi.sdk.transfer.data.AIPackage;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIManager {
    public static final Companion Companion = new Companion(null);
    public final Context a;
    public final IDLockManager b = new IDLockManager();
    public final ExecutorService c = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.xiaomi.taiyi.sdk.core.connect.AIManager$$ExternalSyntheticLambda0
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return AIManager.a(runnable);
        }
    });
    public final ConcurrentHashMap d = new ConcurrentHashMap();
    public final Lazy e = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.connect.AIManager$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AIManager.a(this.f$0);
        }
    });
    public final ConcurrentHashMap f = new ConcurrentHashMap();
    public final ConcurrentHashMap g = new ConcurrentHashMap();
    public final Lazy h = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.connect.AIManager$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AIManager.a();
        }
    });

    @Metadata
    public static final class Companion extends SingletonHolder<AIManager, Context> {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            super(a.a);
        }
    }

    public AIManager(Context context) {
        this.a = context;
    }

    public static final ServiceConnector a() {
        return new ServiceConnector("com.xiaomi.aiservice.AI_CORE", new ComponentName("com.xiaomi.aiservice", "com.xiaomi.aiservice.core.AICoreService"), 2000L);
    }

    public static final IAbilityLoader a(AIManager aIManager) {
        return IAbilityLoader.Companion.localLoader(aIManager.a);
    }

    public static final Thread a(Runnable runnable) {
        return new Thread(runnable, "Taiyi-Sdk-AIManager");
    }

    public final ServiceAbility a(String str) {
        g gVar;
        ServiceAbility serviceAbility = (ServiceAbility) this.g.get(str);
        if (serviceAbility == null) {
            return null;
        }
        Intrinsics.checkNotNullParameter(serviceAbility, "serviceAbility");
        ServiceInfo service = serviceAbility.getService();
        Intrinsics.checkNotNullParameter(service, "service");
        if ((service.isLocal() || ((gVar = (g) this.d.get(service.getId())) != null && gVar.c.b())) && serviceAbility.isAlive()) {
            return serviceAbility;
        }
        SdkLog.w("AIManager", "getAliveCache " + str + " is dead, remove " + serviceAbility + " from cache");
        this.g.remove(str);
        return null;
    }

    public final AIResponse a(String api, AIRequest request) {
        IAbilityLoader iAbilityLoader;
        Intrinsics.checkNotNullParameter(api, "api");
        Intrinsics.checkNotNullParameter(request, "request");
        ServiceAbility serviceAbilityB = b(api);
        IAIAbility proxy$core_release = serviceAbilityB != null ? serviceAbilityB.getProxy$core_release() : null;
        if (proxy$core_release == null) {
            AIResponse aIResponseCreate = AIResponse.CREATOR.create(this.a, request);
            aIResponseCreate.setCode(ErrorCode.ERROR_ABILITY_NOT_FOUND);
            return aIResponseCreate;
        }
        IDLock iDLockAddAndGet = this.b.addAndGet(api);
        try {
            iDLockAddAndGet.lock();
            int iUnbind = proxy$core_release.unbind(request.getHeaders().getClientId(), request);
            if (!proxy$core_release.hasSession()) {
                SdkLog.i("AIManager", "unbindSync " + api + " remove by session empty");
                this.g.remove(api);
                if (proxy$core_release.isInternal() && (iAbilityLoader = (IAbilityLoader) this.e.getValue()) != null) {
                    String strName = proxy$core_release.name();
                    Intrinsics.checkNotNullExpressionValue(strName, "name(...)");
                    iAbilityLoader.destroySync(strName);
                }
            }
            AIResponse aIResponseCreate2 = AIResponse.CREATOR.create(this.a, request);
            aIResponseCreate2.setCode(iUnbind);
            iDLockAddAndGet.unlock();
            return aIResponseCreate2;
        } catch (Throwable th) {
            try {
                SdkLog.w("AIManager", "unbindSync " + api + " err. " + th.getMessage());
                AIResponse aIResponseCreate3 = AIResponse.CREATOR.create(this.a, request);
                aIResponseCreate3.setCode(ErrorCode.ERROR_ABILITY_UNBIND_FAILED);
                return aIResponseCreate3;
            } finally {
                iDLockAddAndGet.unlock();
            }
        }
    }

    public final Pair a(AIRequest request) {
        Pair pairB;
        Intrinsics.checkNotNullParameter(request, "request");
        String api = request.getApi();
        if (api.length() == 0) {
            SdkLog.w("AIManager", "loadSync api is empty!");
            return new Pair(null, Integer.valueOf(ErrorCode.ERROR_PARAM_API_EMPTY));
        }
        IDLock iDLockAddAndGet = this.b.addAndGet(api);
        iDLockAddAndGet.lock();
        ServiceAbility serviceAbilityA = a(api);
        if (serviceAbilityA != null) {
            iDLockAddAndGet.unlock();
            SdkLog.i("AIManager", "loadSync " + api + " from cache " + serviceAbilityA);
            return new Pair(serviceAbilityA, 0);
        }
        AIDetail aIDetail = AIQuery.getAIDetail(this.a, request);
        if (aIDetail == null) {
            iDLockAddAndGet.unlock();
            SdkLog.e("AIManager", "loadSync " + api + " detail is null!");
            return new Pair(null, Integer.valueOf(ErrorCode.ERROR_DETAIL_EMPTY));
        }
        if (!aIDetail.isSupport()) {
            iDLockAddAndGet.unlock();
            SdkLog.e("AIManager", "loadSync " + aIDetail + " not support!");
            return new Pair(null, Integer.valueOf(ErrorCode.ERROR_UNSUPPORTED_BY_DISABLED));
        }
        if (!aIDetail.isAvailable()) {
            iDLockAddAndGet.unlock();
            SdkLog.e("AIManager", "loadSync " + aIDetail + " not available!");
            return new Pair(null, Integer.valueOf(aIDetail.getCode()));
        }
        if (!request.getHeaders().isLocalAffinity()) {
            pairB = c(request, aIDetail);
        } else if (((IAbilityLoader) this.e.getValue()) == null) {
            SdkLog.w("AIManager", "loadSync " + api + " localLoader is null");
            pairB = c(request, aIDetail);
        } else if (!AIUtils.detectTSB(this.a)) {
            SdkLog.w("AIManager", "loadSync " + aIDetail + " tsb detect failed!");
            pairB = c(request, aIDetail);
        } else if (aIDetail.getAllowInternal() || Intrinsics.areEqual(this.a.getPackageName(), "com.xiaomi.aiservice")) {
            pairB = b(request, aIDetail);
        } else {
            SdkLog.w("AIManager", "loadSync " + aIDetail + " not allowInternal!");
            pairB = c(request, aIDetail);
        }
        ServiceAbility serviceAbility = (ServiceAbility) pairB.getFirst();
        if (serviceAbility != null) {
            this.g.put(api, serviceAbility);
        }
        iDLockAddAndGet.unlock();
        return pairB;
    }

    public final Pair a(AIRequest aIRequest, AIDetail aIDetail) {
        ServiceInfo service = aIDetail.getService();
        if (service == null) {
            SdkLog.w("AIManager", "acquireLoader " + aIRequest + " service is null!");
            return new Pair(null, Integer.valueOf(ErrorCode.ERROR_AI_INFO_EMPTY));
        }
        g gVar = (g) this.d.get(service.getId());
        if (gVar == null) {
            gVar = new g(service);
            SdkLog.i("AIManager", "acquireLoader " + aIRequest + " create new loader " + gVar);
            this.d.put(service.getId(), gVar);
        }
        return new Pair(gVar, 0);
    }

    public final void a(String serviceId, String api) {
        Object objM1061constructorimpl;
        Object next;
        Intrinsics.checkNotNullParameter(serviceId, "serviceId");
        Intrinsics.checkNotNullParameter(api, "api");
        ConcurrentHashMap concurrentHashMap = this.g;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : concurrentHashMap.entrySet()) {
            if (Intrinsics.areEqual(((ServiceAbility) entry.getValue()).getService().getId(), serviceId)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Set setKeySet = linkedHashMap.keySet();
        try {
            Result.Companion companion = Result.Companion;
            Iterator it = linkedHashMap.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                ServiceAbility serviceAbility = (ServiceAbility) next;
                if (serviceAbility.isAlive() && !Intrinsics.areEqual(serviceAbility.getProxy$core_release().api(), api)) {
                    break;
                }
            }
            if (next == null) {
                SdkLog.i("AIManager", "destroy " + serviceId + " by " + api + " for " + setKeySet);
                g gVar = (g) this.d.get(serviceId);
                if (gVar != null) {
                    gVar.c.a();
                }
                Iterator it2 = setKeySet.iterator();
                while (it2.hasNext()) {
                    this.g.remove((String) it2.next());
                }
            }
            objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            SdkLog.w("AIManager", "destroy " + serviceId + " by " + api + " err. " + thM1063exceptionOrNullimpl.getMessage());
        }
        if (this.g.isEmpty()) {
            ((ServiceConnector) this.h.getValue()).destroy();
        }
    }

    public final ServiceAbility b(String str) {
        g gVar;
        ServiceAbility serviceAbility = (ServiceAbility) this.g.get(str);
        if (serviceAbility != null) {
            Intrinsics.checkNotNullParameter(serviceAbility, "serviceAbility");
            ServiceInfo service = serviceAbility.getService();
            Intrinsics.checkNotNullParameter(service, "service");
            if ((service.isLocal() || ((gVar = (g) this.d.get(service.getId())) != null && gVar.c.b())) && serviceAbility.isBinderAlive()) {
                return serviceAbility;
            }
        }
        SdkLog.i("AIManager", "getBinderAliveCache " + str + " is dead, remove " + serviceAbility + " from cache");
        this.g.remove(str);
        return null;
    }

    public final Pair b(AIRequest aIRequest, AIDetail aIDetail) {
        String api = aIRequest.getApi();
        IAbilityLoader iAbilityLoader = (IAbilityLoader) this.e.getValue();
        if (iAbilityLoader == null) {
            SdkLog.w("AIManager", "loadSyncByLocal " + api + " localLoader is null");
            return new Pair(null, Integer.valueOf(ErrorCode.ERROR_NO_LOCAL_LOADER));
        }
        ProcTrack.Companion companion = ProcTrack.Companion;
        companion.startLoad(aIRequest);
        AIPackage result = aIDetail.getResult();
        if (result != null && !result.getBoolean(AIKeys.KEY_RESOURCE_UNWRAPPED, true)) {
            b(aIRequest);
            aIDetail = AIQuery.getAIDetail(this.a, aIRequest);
            if (aIDetail == null) {
                return new Pair(null, Integer.valueOf(ErrorCode.ERROR_AI_INFO_EMPTY));
            }
            AIPackage result2 = aIDetail.getResult();
            if (result2 != null && !result2.getBoolean(AIKeys.KEY_RESOURCE_UNWRAPPED, true)) {
                SdkLog.e("AIManager", "loadSyncByLocal " + api + " resource not unwrapped");
                return new Pair(null, Integer.valueOf(ErrorCode.ERROR_RESOURCE_PREPARE_FAILED));
            }
        }
        AIResponse aIResponseLoadSync = iAbilityLoader.loadSync(aIRequest, aIDetail);
        companion.endLoad(aIRequest);
        IAIAbility ability = aIResponseLoadSync.getAbility();
        return ability == null ? new Pair(null, Integer.valueOf(aIResponseLoadSync.getCode())) : new Pair(new ServiceAbility(ability, ServiceInfo.Companion.localService(this.a)), Integer.valueOf(aIResponseLoadSync.getCode()));
    }

    public final void b(AIRequest aIRequest) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AIKeys.KEY_REQUEST, aIRequest);
        Pair pairConnect = ((ServiceConnector) this.h.getValue()).connect(this.a, bundle);
        IBinder iBinder = (IBinder) pairConnect.component1();
        ((Number) pairConnect.component2()).intValue();
        if (iBinder == null) {
            return;
        }
        AIResponse aIResponsePrepare = IAICoreService.Stub.asInterface(iBinder).prepare(aIRequest);
        SdkLog.i("AIManager", "prepare " + aIResponsePrepare.getCode());
        aIResponsePrepare.getCode();
    }

    public final Pair c(AIRequest aIRequest, AIDetail aIDetail) {
        String api = aIRequest.getApi();
        Pair pairA = a(aIRequest, aIDetail);
        g gVar = (g) pairA.component1();
        int iIntValue = ((Number) pairA.component2()).intValue();
        if (gVar != null) {
            AIResponse aIResponseA = gVar.a(this.a, aIRequest);
            IAIAbility ability = aIResponseA.getAbility();
            return ability == null ? new Pair(null, Integer.valueOf(aIResponseA.getCode())) : new Pair(new ServiceAbility(ability, gVar.a), Integer.valueOf(aIResponseA.getCode()));
        }
        SdkLog.w("AIManager", "loadSyncByRemote " + api + " acquireLoader failed! code " + iIntValue);
        return new Pair(null, Integer.valueOf(iIntValue));
    }
}

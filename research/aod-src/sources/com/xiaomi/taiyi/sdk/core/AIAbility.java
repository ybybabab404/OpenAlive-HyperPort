package com.xiaomi.taiyi.sdk.core;

import android.app.Application;
import android.content.Context;
import android.os.RemoteException;
import com.xiaomi.taiyi.sdk.base.data.AIConfig;
import com.xiaomi.taiyi.sdk.base.data.AIDetail;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.data.ServiceInfo;
import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.core.ability.ServiceAbility;
import com.xiaomi.taiyi.sdk.core.connect.AIManager;
import com.xiaomi.taiyi.sdk.core.track.ProcTrack;
import com.xiaomi.taiyi.sdk.transfer.core.IAIAbility;
import com.xiaomi.taiyi.sdk.transfer.core.IAICallback;
import com.xiaomi.taiyi.sdk.transfer.core.IAIMemory;
import com.xiaomi.taiyi.sdk.transfer.data.AIFrame;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryPool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
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
public abstract class AIAbility {
    public static final Companion Companion = new Companion(null);
    public static final HashMap l = new HashMap();
    public final String a;
    public final String b;
    public final Context c;
    public final AIManager d;
    public ServiceAbility e;
    public String f;
    public AISettings g;
    public final Lazy h;
    public final String i;
    public final Lazy j;
    public final Lazy k;

    @Metadata
    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
        }
    }

    public AIAbility(Context context, String api) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(api, "api");
        this.a = api;
        String str = "AIAbility(" + api + ")";
        this.b = str;
        Context app = context instanceof Application ? (Application) context : null;
        app = app == null ? context.getApplicationContext() : app;
        this.c = app;
        AIManager.Companion companion = AIManager.Companion;
        Intrinsics.checkNotNullExpressionValue(app, "app");
        this.d = (AIManager) companion.getInstance(app);
        this.f = "";
        HashMap map = l;
        Integer num = (Integer) map.get(api);
        map.put(api, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
        SdkLog.d(str, "init counter: " + map.get(api));
        this.h = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.AIAbility$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AIAbility.b(this.f$0);
            }
        });
        this.i = "ability:" + ExtsKt.getMY_UID() + ":" + ExtsKt.getMY_PID() + ":" + api + "$" + map.get(api);
        this.j = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.AIAbility$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AIAbility.a(this.f$0);
            }
        });
        this.k = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.core.AIAbility$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AIAbility.c(this.f$0);
            }
        });
    }

    public static final b a(AIAbility aIAbility) {
        return new b(aIAbility);
    }

    public static final b access$getClient(AIAbility aIAbility) {
        return (b) aIAbility.j.getValue();
    }

    public static final m access$getTrackManager(AIAbility aIAbility) {
        return (m) aIAbility.k.getValue();
    }

    public static final void access$post(AIAbility aIAbility, IAIAbility iAIAbility, AIRequest aIRequest, IAICallback iAICallback) {
        int iB = aIAbility.b(aIRequest);
        if (iB == 0) {
            iAIAbility.post(aIRequest, iAICallback);
            return;
        }
        AIResponse aIResponseCreate = AIResponse.CREATOR.create(aIRequest);
        aIResponseCreate.setCode(iB);
        iAICallback.onResponse(aIResponseCreate);
    }

    public static final AISettings b(AIAbility aIAbility) {
        if (aIAbility.g == null) {
            aIAbility.g = new AISettings(null, false, false, null, 15, null);
        }
        AISettings aISettings = aIAbility.g;
        if (aISettings != null) {
            return aISettings;
        }
        Intrinsics.throwUninitializedPropertyAccessException("_settings");
        return null;
    }

    public static final m c(AIAbility aIAbility) {
        l lVar = m.e;
        Context app = aIAbility.c;
        Intrinsics.checkNotNullExpressionValue(app, "app");
        return (m) lVar.getInstance(app);
    }

    public final int a(ServiceAbility serviceAbility) {
        this.e = serviceAbility;
        this.f = serviceAbility.getService().getId();
        try {
            return serviceAbility.getProxy$core_release().bind((b) this.j.getValue(), createRequest());
        } catch (Throwable th) {
            SdkLog.e(this.b, "bind error. " + th.getMessage());
            return ErrorCode.ERROR_ABILITY_BIND_FAILED;
        }
    }

    public final AIResponse a(AIRequest aIRequest) {
        if (ExtsKt.isMainThread()) {
            SdkLog.e(this.b, "execute must on work thread!");
            AIResponse aIResponseCreate = AIResponse.CREATOR.create(aIRequest);
            aIResponseCreate.setCode(ErrorCode.ERROR_WORK_ON_MAIN_THREAD);
            return aIResponseCreate;
        }
        if (!isInitialized()) {
            int iC = c(aIRequest);
            ProcTrack.Companion.enableLoad(aIRequest, iC);
            if (iC != 0) {
                SdkLog.e(this.b, "execute start fail. " + iC);
                AIResponse aIResponseCreate2 = AIResponse.CREATOR.create(aIRequest);
                aIResponseCreate2.setCode(iC);
                return aIResponseCreate2;
            }
        }
        ServiceAbility serviceAbility = this.e;
        if (serviceAbility == null) {
            SdkLog.e(this.b, "execute ability is null");
            AIResponse aIResponseCreate3 = AIResponse.CREATOR.create(aIRequest);
            aIResponseCreate3.setCode(ErrorCode.ERROR_ABILITY_NOT_INIT);
            return aIResponseCreate3;
        }
        try {
            int iB = b(aIRequest);
            if (iB != 0) {
                AIResponse aIResponseCreate4 = AIResponse.CREATOR.create(aIRequest);
                aIResponseCreate4.setCode(iB);
                return aIResponseCreate4;
            }
            AIResponse aIResponseExecute = serviceAbility.getProxy$core_release().execute(aIRequest);
            Intrinsics.checkNotNull(aIResponseExecute);
            a(aIRequest, aIResponseExecute);
            return aIResponseExecute;
        } catch (RemoteException e) {
            SdkLog.e(this.b, "execute remote error. " + e.getMessage());
            AIResponse aIResponseCreate5 = AIResponse.CREATOR.create(aIRequest);
            aIResponseCreate5.setCode(ErrorCode.ERROR_EXECUTE_REMOTE_EXCEPTION);
            return aIResponseCreate5;
        } catch (Throwable th) {
            SdkLog.e(this.b, "execute error. " + th.getMessage());
            AIResponse aIResponseCreate6 = AIResponse.CREATOR.create(aIRequest);
            aIResponseCreate6.setCode(ErrorCode.ERROR_EXECUTE_EXCEPTION);
            return aIResponseCreate6;
        }
    }

    public final void a(AIRequest aIRequest, AIResponse aIResponse) {
        Object objM1061constructorimpl;
        Unit unit;
        IAIAbility proxy$core_release;
        IAIMemory memory;
        ServiceAbility serviceAbility;
        IAIAbility proxy$core_release2;
        ServiceAbility serviceAbility2;
        IAIAbility proxy$core_release3;
        String strName;
        ProcTrack.Companion companion = ProcTrack.Companion;
        companion.startAppPostProcess(aIResponse);
        companion.runInternal(aIResponse, isInternal());
        try {
            Result.Companion companion2 = Result.Companion;
            if (aIResponse.getHeaders().getAbilityName().length() == 0 && (serviceAbility2 = this.e) != null && (proxy$core_release3 = serviceAbility2.getProxy$core_release()) != null && (strName = proxy$core_release3.name()) != null) {
                aIResponse.getHeaders().setAbilityName(strName);
            }
            if (aIResponse.getHeaders().getAbilityVersion() <= 0 && (serviceAbility = this.e) != null && (proxy$core_release2 = serviceAbility.getProxy$core_release()) != null) {
                aIResponse.getHeaders().setAbilityVersion(proxy$core_release2.version());
            }
            Result.m1061constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion3 = Result.Companion;
            Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        try {
            Pair<Integer, List<AIFrame>> pairCollect = aIResponse.getBody().collect(true);
            int iIntValue = ((Number) pairCollect.component1()).intValue();
            List list = (List) pairCollect.component2();
            SdkLog.i(this.b, "postProcess " + iIntValue + ". " + list.size());
            ServiceAbility serviceAbility3 = this.e;
            if (serviceAbility3 != null && (proxy$core_release = serviceAbility3.getProxy$core_release()) != null && (memory = proxy$core_release.getMemory(this.i)) != null) {
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(((AIFrame) it.next()).getShareMemId());
                }
                memory.free(arrayList);
            }
            if (aIResponse.getCode() == 0) {
                aIResponse.setCode(iIntValue);
            }
            objM1061constructorimpl = Result.m1061constructorimpl(Unit.INSTANCE);
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th2));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            SdkLog.e(this.b, "postProcess error. " + thM1063exceptionOrNullimpl.getMessage());
        }
        try {
            aIRequest.getBody().free();
            aIResponse.getBody().free();
            ShareMemoryPool memoryPool = aIResponse.getBody().getMemoryPool();
            if (memoryPool != null) {
                ((b) this.j.getValue()).f.sync(memoryPool);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            Result.m1061constructorimpl(unit);
        } catch (Throwable th3) {
            Result.Companion companion5 = Result.Companion;
            Result.m1061constructorimpl(ResultKt.createFailure(th3));
        }
        ProcTrack.Companion.endAppPostProcess(aIResponse);
    }

    public abstract int apiLevel();

    public final int b(AIRequest aIRequest) {
        ProcTrack.Companion companion = ProcTrack.Companion;
        companion.startAppPreProcess(aIRequest);
        aIRequest.getHeaders().setTid(ExtsKt.getMY_TID());
        ShareMemoryPool shareMemoryPool = ((b) this.j.getValue()).f.get();
        if (shareMemoryPool != null) {
            aIRequest.getBody().setMemoryPool(shareMemoryPool);
        }
        aIRequest.getBody().setShareMemoryState(!isInternal());
        Pair<Integer, List<AIFrame>> pairDistribute = aIRequest.getBody().distribute();
        int iIntValue = ((Number) pairDistribute.component1()).intValue();
        List list = (List) pairDistribute.component2();
        SdkLog.i(this.b, "preProcess " + iIntValue + ". " + list.size());
        companion.endAppPreProcess(aIRequest);
        return iIntValue;
    }

    public final int c(AIRequest aIRequest) {
        if (isInitialized()) {
            SdkLog.i(this.b, "start already initialized");
            return 0;
        }
        if (ExtsKt.isMainThread()) {
            SdkLog.e(this.b, "start must on work thread");
            return ErrorCode.ERROR_WORK_ON_MAIN_THREAD;
        }
        ((ShareMemoryPool) ((b) this.j.getValue()).e.getValue()).closeAndClear();
        AIManager aIManager = this.d;
        aIRequest.getHeaders().setTid(ExtsKt.getMY_TID());
        if (getSettings().getRunInternal()) {
            SdkLog.i(this.b, "startSync enableLocalAffinity");
            aIRequest.getHeaders().enableLocalAffinity();
        }
        Pair pairA = aIManager.a(aIRequest);
        ServiceAbility serviceAbility = (ServiceAbility) pairA.component1();
        int iIntValue = ((Number) pairA.component2()).intValue();
        SdkLog.i(this.b, "startSync code = " + iIntValue + " ability = " + serviceAbility);
        if (serviceAbility == null) {
            return iIntValue;
        }
        int iA = a(serviceAbility);
        SdkLog.i(this.b, "startSync bind = " + iA);
        return iA;
    }

    public AIRequest createRequest() {
        AIRequest.CREATOR creator = AIRequest.CREATOR;
        Context app = this.c;
        Intrinsics.checkNotNullExpressionValue(app, "app");
        AIRequest aIRequestCreateAbilityRequest = creator.createAbilityRequest(app, this.a);
        aIRequestCreateAbilityRequest.getHeaders().setApiLevel(apiLevel());
        aIRequestCreateAbilityRequest.getHeaders().setClientId(this.i);
        aIRequestCreateAbilityRequest.getHeaders().putInt(AIKeys.KEY_TRACK_TYPE, getSettings().getTrackSettings().getType());
        aIRequestCreateAbilityRequest.getHeaders().putInt(AIKeys.KEY_TRACK_PERIOD, getSettings().getTrackSettings().getPeriod());
        aIRequestCreateAbilityRequest.getHeaders().putInt(AIKeys.KEY_TRACK_NUM, getSettings().getTrackSettings().getNum());
        return aIRequestCreateAbilityRequest;
    }

    public final void destroy() {
        String id;
        ServiceInfo service;
        SdkLog.i(this.b, "destroy");
        ServiceAbility serviceAbility = this.e;
        if (serviceAbility == null || (service = serviceAbility.getService()) == null || (id = service.getId()) == null) {
            id = this.f;
        }
        stopSync();
        if (id.length() == 0) {
            return;
        }
        this.d.a(id, this.a);
    }

    public final AIResponse execute(AIRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        AIResponse aIResponseA = a(request);
        if (getSettings().getEnableTrack()) {
            ((m) this.k.getValue()).a(this, request, aIResponseA);
        }
        return aIResponseA;
    }

    public final AIConfig getAIConfig() {
        Context app = this.c;
        Intrinsics.checkNotNullExpressionValue(app, "app");
        return AIQuery.getAIConfig(app, createRequest());
    }

    public final String getId() {
        return this.i;
    }

    public final AISettings getSettings() {
        return (AISettings) this.h.getValue();
    }

    public final String getTAG() {
        return this.b;
    }

    public final boolean isInitialized() {
        g gVar;
        ServiceAbility serviceAbility = this.e;
        if (serviceAbility != null) {
            AIManager aIManager = this.d;
            aIManager.getClass();
            Intrinsics.checkNotNullParameter(serviceAbility, "serviceAbility");
            ServiceInfo service = serviceAbility.getService();
            Intrinsics.checkNotNullParameter(service, "service");
            if ((service.isLocal() || ((gVar = (g) aIManager.d.get(service.getId())) != null && gVar.c.b())) && serviceAbility.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isInternal() {
        Boolean boolValueOf;
        boolean allowInternal;
        if (!getSettings().getRunInternal()) {
            return false;
        }
        ServiceAbility serviceAbility = this.e;
        if (serviceAbility == null) {
            Context app = this.c;
            Intrinsics.checkNotNullExpressionValue(app, "app");
            AIDetail aIDetail$default = AIQuery.getAIDetail$default(app, this.a, null, 4, null);
            if (aIDetail$default != null) {
                allowInternal = aIDetail$default.getAllowInternal();
            } else {
                boolValueOf = null;
            }
            return Intrinsics.areEqual(boolValueOf, Boolean.TRUE);
        }
        allowInternal = serviceAbility.isInternal();
        boolValueOf = Boolean.valueOf(allowInternal);
        return Intrinsics.areEqual(boolValueOf, Boolean.TRUE);
    }

    public boolean isSupport() {
        if (this.e != null) {
            return true;
        }
        AIConfig aIConfig = getAIConfig();
        return (aIConfig == null || aIConfig.getDisabled()) ? false : true;
    }

    public final int stopSync() {
        AIManager aIManager = this.d;
        String str = this.a;
        AIRequest aIRequestCreateRequest = createRequest();
        aIRequestCreateRequest.getHeaders().setTid(ExtsKt.getMY_TID());
        Unit unit = Unit.INSTANCE;
        int code = aIManager.a(str, aIRequestCreateRequest).getCode();
        ((ShareMemoryPool) ((b) this.j.getValue()).e.getValue()).closeAndClear();
        this.e = null;
        return code;
    }
}

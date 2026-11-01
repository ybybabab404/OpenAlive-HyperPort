package com.xiaomi.taiyi.sdk.base.data;

import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.transfer.data.AIBody;
import com.xiaomi.taiyi.sdk.transfer.data.AIPackage;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
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
public final class AIDetail {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "AIDetail";
    private boolean allowInternal;
    private final String api;
    private int code;
    private final AIConfig config;
    private HashMap<String, AIConfig> configs;
    private Boolean isLLMInner;
    private final String name;
    private HashMap<String, AIResource> resources;
    private AIPackage result;
    private ServiceInfo service;
    private Integer typeInner;

    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final AIDetail parse(String api, AIResponse response) {
            Intrinsics.checkNotNullParameter(api, "api");
            Intrinsics.checkNotNullParameter(response, "response");
            AIBody body = response.getBody();
            ArrayList<String> stringArrayList = body.getStringArrayList(AIKeys.KEY_AI_CONFIG_JSON_LIST, new ArrayList<>());
            ArrayList<String> stringArrayList2 = body.getStringArrayList(AIKeys.KEY_AI_RESOURCE_JSON_LIST, new ArrayList<>());
            if (stringArrayList == null || stringArrayList.isEmpty()) {
                SdkLog.e(AIDetail.TAG, "parse " + api + " data empty! configs = " + stringArrayList);
                return null;
            }
            HashMap<String, AIConfig> map = new HashMap<>();
            HashMap<String, AIResource> map2 = new HashMap<>();
            Iterator<T> it = stringArrayList.iterator();
            String name = "";
            while (it.hasNext()) {
                AIConfig aIConfigFromJson = AIConfig.Companion.fromJson((String) it.next());
                if (aIConfigFromJson != null) {
                    map.put(aIConfigFromJson.getName(), aIConfigFromJson);
                    if (Intrinsics.areEqual(aIConfigFromJson.getApi(), api)) {
                        name = aIConfigFromJson.getName();
                    }
                }
            }
            if (stringArrayList2 != null) {
                Iterator<T> it2 = stringArrayList2.iterator();
                while (it2.hasNext()) {
                    AIResource aIResourceFromJson = AIResource.Companion.fromJson((String) it2.next());
                    if (aIResourceFromJson != null) {
                        map2.put(aIResourceFromJson.getId(), aIResourceFromJson);
                    }
                }
            }
            AIConfig aIConfig = map.get(name);
            if (aIConfig == null) {
                SdkLog.e(AIDetail.TAG, "parse " + api + " no config for " + ((Object) name));
                return null;
            }
            AIDetail aIDetail = new AIDetail(api, name, aIConfig);
            aIDetail.setResult(body);
            aIDetail.setCode(response.getCode());
            aIDetail.setConfigs(map);
            aIDetail.setResources(map2);
            aIDetail.setAllowInternal(body.getBoolean(AIKeys.KEY_AI_ALLOW_INTERNAL, false));
            aIDetail.setService(ServiceInfo.Companion.parse(response));
            SdkLog.d(AIDetail.TAG, "parse " + api + ". " + aIDetail);
            return aIDetail;
        }
    }

    public AIDetail(String api, String name, AIConfig config) {
        Intrinsics.checkNotNullParameter(api, "api");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(config, "config");
        this.api = api;
        this.name = name;
        this.config = config;
        this.configs = new HashMap<>();
        this.resources = new HashMap<>();
        this.isLLMInner = Boolean.FALSE;
    }

    @JvmStatic
    public static final AIDetail parse(String str, AIResponse aIResponse) {
        return Companion.parse(str, aIResponse);
    }

    public final long downloadSize() {
        AIPackage aIPackage = this.result;
        if (aIPackage == null) {
            return 0L;
        }
        return aIPackage.getLong(AIKeys.KEY_AI_DOWNLOAD_SIZE, 0L);
    }

    public final int downloadType() {
        AIPackage aIPackage = this.result;
        if (aIPackage != null && isSupport()) {
            return aIPackage.getInt(AIKeys.KEY_AI_DOWNLOAD_TYPE, 0);
        }
        return 0;
    }

    public final void fill(AIBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        ArrayList<String> arrayList = new ArrayList<>();
        Collection<AIConfig> collectionValues = this.configs.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((AIConfig) it.next()).getJsonSource());
        }
        body.putStringArrayList(AIKeys.KEY_AI_CONFIG_JSON_LIST, arrayList);
        ArrayList<String> arrayList2 = new ArrayList<>();
        Collection<AIResource> collectionValues2 = this.resources.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues2, "<get-values>(...)");
        Iterator<T> it2 = collectionValues2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((AIResource) it2.next()).getJsonSource());
        }
        body.putStringArrayList(AIKeys.KEY_AI_RESOURCE_JSON_LIST, arrayList2);
        body.setErrorCode(this.code);
        body.putBoolean(AIKeys.KEY_AI_ALLOW_INTERNAL, this.allowInternal);
        ServiceInfo serviceInfo = this.service;
        if (serviceInfo != null) {
            serviceInfo.fill(body);
        }
    }

    public final AIConfig findByApi(String findApi) {
        Object next;
        Intrinsics.checkNotNullParameter(findApi, "findApi");
        if (Intrinsics.areEqual(findApi, this.api)) {
            return this.config;
        }
        Collection<AIConfig> collectionValues = this.configs.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((AIConfig) next).getApi(), findApi)) {
                return (AIConfig) next;
            }
        }
        next = null;
        return (AIConfig) next;
    }

    public final AIConfig findByName(String findName) {
        Intrinsics.checkNotNullParameter(findName, "findName");
        return Intrinsics.areEqual(findName, this.name) ? this.config : this.configs.get(this.name);
    }

    public final boolean getAllowInternal() {
        return this.allowInternal;
    }

    public final String getApi() {
        return this.api;
    }

    public final int getCode() {
        return this.code;
    }

    public final AIConfig getConfig() {
        return this.config;
    }

    public final HashMap<String, AIConfig> getConfigs() {
        return this.configs;
    }

    public final String getName() {
        return this.name;
    }

    public final HashMap<String, AIResource> getResources() {
        return this.resources;
    }

    public final AIPackage getResult() {
        return this.result;
    }

    public final ServiceInfo getService() {
        return this.service;
    }

    public final boolean isAvailable() {
        return isSupport() && this.code == 0;
    }

    public final boolean isLLM() {
        Boolean bool = this.isLLMInner;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            return bool.booleanValue();
        }
        Collection<AIConfig> collectionValues = this.configs.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            if (((AIConfig) it.next()).isLLM()) {
                this.isLLMInner = Boolean.TRUE;
                return true;
            }
        }
        this.isLLMInner = Boolean.FALSE;
        return false;
    }

    public final boolean isSupport() {
        return !this.config.getDisabled();
    }

    public final long localSize() {
        AIPackage aIPackage = this.result;
        if (aIPackage == null) {
            return 0L;
        }
        return aIPackage.getLong(AIKeys.KEY_AI_LOCAL_SIZE, 0L);
    }

    public final boolean needDownload() {
        return downloadType() != 0;
    }

    public final void setAllowInternal(boolean z) {
        this.allowInternal = z;
    }

    public final void setCode(int i) {
        this.code = i;
    }

    public final void setConfigs(HashMap<String, AIConfig> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.configs = map;
    }

    public final void setResources(HashMap<String, AIResource> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.resources = map;
    }

    public final void setResult(AIPackage aIPackage) {
        this.result = aIPackage;
    }

    public final void setService(ServiceInfo serviceInfo) {
        this.service = serviceInfo;
    }

    public String toString() {
        return "AIDetail(" + this.api + ", " + this.name + ", " + this.configs + ", " + this.allowInternal + ", " + this.code + ")";
    }

    public final long totalSize() {
        AIPackage aIPackage = this.result;
        if (aIPackage == null) {
            return 0L;
        }
        return aIPackage.getLong(AIKeys.KEY_AI_TOTAL_SIZE, 0L);
    }

    public final int type() {
        Integer num = this.typeInner;
        if (num != null) {
            Intrinsics.checkNotNull(num);
            return num.intValue();
        }
        int size = this.configs.size();
        if (size == 0) {
            this.typeInner = -1;
            return -1;
        }
        Iterator<AIConfig> it = this.configs.values().iterator();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AIConfig next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            AIConfig aIConfig = next;
            if (aIConfig.getType() == 0) {
                i++;
            } else if (aIConfig.getType() == 1) {
                i2++;
            }
        }
        if (i == size) {
            return 0;
        }
        int i3 = i2 != size ? 2 : 1;
        this.typeInner = Integer.valueOf(i3);
        return i3;
    }
}

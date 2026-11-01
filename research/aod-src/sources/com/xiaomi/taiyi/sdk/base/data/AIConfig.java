package com.xiaomi.taiyi.sdk.base.data;

import com.google.gson.reflect.TypeToken;
import com.xiaomi.taiyi.sdk.base.utils.JsonUtil;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

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
public class AIConfig {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "AIConfig";
    public static final int TYPE_CLOUD = 1;
    public static final int TYPE_LOCAL = 0;
    public static final int TYPE_MIXED = 2;
    public static final int TYPE_UNKNOWN = -1;
    private boolean disabled;
    private boolean isLLM;
    private String parent;
    private AIPlugin plugin;
    private int type;
    private long version;
    private String code = "";
    private String api = "";
    private String name = "";
    private List<String> filter = new ArrayList();
    private List<AILibrary> libraries = new ArrayList();
    private List<AIModel> models = new ArrayList();
    private String jsonSource = "";

    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final AIConfig fromJson(String json) {
            Intrinsics.checkNotNullParameter(json, "json");
            Type type = new TypeToken<AIConfig>() { // from class: com.xiaomi.taiyi.sdk.base.data.AIConfig$Companion$fromJson$result$1
            }.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            AIConfig aIConfig = (AIConfig) JsonUtil.fromJson(json, type);
            if (aIConfig != null) {
                aIConfig.setJsonSource(json);
            }
            return aIConfig;
        }

        @JvmStatic
        public final AIConfig parse(String api, AIResponse response) {
            Intrinsics.checkNotNullParameter(api, "api");
            Intrinsics.checkNotNullParameter(response, "response");
            String string = response.getBody().getString(AIKeys.KEY_AI_CONFIG_JSON, "");
            if (string != null && string.length() != 0) {
                return fromJson(string);
            }
            SdkLog.e(AIConfig.TAG, "parse " + api + " empty!");
            return null;
        }
    }

    @JvmStatic
    public static final AIConfig fromJson(String str) {
        return Companion.fromJson(str);
    }

    @Deprecated
    public static /* synthetic */ void getCode$annotations() {
    }

    @JvmStatic
    public static final AIConfig parse(String str, AIResponse aIResponse) {
        return Companion.parse(str, aIResponse);
    }

    public final String getApi() {
        return this.api;
    }

    public final String getCode() {
        return this.code;
    }

    public final boolean getDisabled() {
        return this.disabled;
    }

    public final List<String> getFilter() {
        return this.filter;
    }

    public final String getJsonSource() {
        return this.jsonSource;
    }

    public final List<AILibrary> getLibraries() {
        return this.libraries;
    }

    public final List<AIModel> getModels() {
        return this.models;
    }

    public final String getName() {
        return this.name;
    }

    public final JSONObject getParams() {
        JSONObject jSONObjectOptJSONObject = new JSONObject(this.jsonSource).optJSONObject("params");
        return jSONObjectOptJSONObject == null ? new JSONObject() : jSONObjectOptJSONObject;
    }

    public final String getParent() {
        return this.parent;
    }

    public final AIPlugin getPlugin() {
        return this.plugin;
    }

    public final int getType() {
        return this.type;
    }

    public final long getVersion() {
        return this.version;
    }

    public final String identifier() {
        return this.name + ":" + this.version;
    }

    public final boolean isAtom() {
        String id;
        AIPlugin aIPlugin = this.plugin;
        return (aIPlugin == null || (id = aIPlugin.getId()) == null || id.length() <= 0) ? false : true;
    }

    public final boolean isFilter() {
        return !this.filter.isEmpty();
    }

    public final boolean isLLM() {
        return this.isLLM;
    }

    public final boolean match(AIConfig aIConfig) {
        return Intrinsics.areEqual(identifier(), aIConfig != null ? aIConfig.identifier() : null);
    }

    public final void setApi(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.api = str;
    }

    public final void setCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.code = str;
    }

    public final void setDisabled(boolean z) {
        this.disabled = z;
    }

    public final void setFilter(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.filter = list;
    }

    public final void setJsonSource(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.jsonSource = str;
    }

    public final void setLLM(boolean z) {
        this.isLLM = z;
    }

    public final void setLibraries(List<AILibrary> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.libraries = list;
    }

    public final void setModels(List<AIModel> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.models = list;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final void setParent(String str) {
        this.parent = str;
    }

    public final void setPlugin(AIPlugin aIPlugin) {
        this.plugin = aIPlugin;
    }

    public final void setType(int i) {
        this.type = i;
    }

    public final void setVersion(long j) {
        this.version = j;
    }

    public String toString() {
        return "AI(" + this.api + ", " + this.name + ", " + this.version + ", " + this.disabled + ")";
    }
}

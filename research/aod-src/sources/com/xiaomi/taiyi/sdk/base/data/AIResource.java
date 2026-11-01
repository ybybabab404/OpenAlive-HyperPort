package com.xiaomi.taiyi.sdk.base.data;

import com.google.gson.reflect.TypeToken;
import com.xiaomi.taiyi.sdk.base.utils.JsonUtil;
import java.lang.reflect.Type;
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
public final class AIResource {
    public static final Companion Companion = new Companion(null);
    private String id;
    private JSONObject jsonObject;
    private String jsonSource;
    private String path;
    private String type;
    private long version;

    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final AIResource fromJson(String json) {
            Intrinsics.checkNotNullParameter(json, "json");
            Type type = new TypeToken<AIResource>() { // from class: com.xiaomi.taiyi.sdk.base.data.AIResource$Companion$fromJson$1
            }.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            AIResource aIResource = (AIResource) JsonUtil.fromJson(json, type);
            if (aIResource == null) {
                return null;
            }
            aIResource.setJsonSource(json);
            aIResource.jsonObject = new JSONObject(json);
            return aIResource;
        }

        @JvmStatic
        public final String identifier(String id, long j) {
            Intrinsics.checkNotNullParameter(id, "id");
            return id + ":" + j;
        }
    }

    public AIResource(String id, long j, String path, String type) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(type, "type");
        this.id = id;
        this.version = j;
        this.path = path;
        this.type = type;
        this.jsonSource = "";
        this.jsonObject = new JSONObject();
    }

    public static /* synthetic */ AIResource copy$default(AIResource aIResource, String str, long j, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = aIResource.id;
        }
        if ((i & 2) != 0) {
            j = aIResource.version;
        }
        if ((i & 4) != 0) {
            str2 = aIResource.path;
        }
        if ((i & 8) != 0) {
            str3 = aIResource.type;
        }
        return aIResource.copy(str, j, str2, str3);
    }

    @JvmStatic
    public static final AIResource fromJson(String str) {
        return Companion.fromJson(str);
    }

    @JvmStatic
    public static final String identifier(String str, long j) {
        return Companion.identifier(str, j);
    }

    public final String component1() {
        return this.id;
    }

    public final long component2() {
        return this.version;
    }

    public final String component3() {
        return this.path;
    }

    public final String component4() {
        return this.type;
    }

    public final AIResource copy(String id, long j, String path, String type) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(type, "type");
        return new AIResource(id, j, path, type);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AIResource)) {
            return false;
        }
        AIResource aIResource = (AIResource) obj;
        return Intrinsics.areEqual(this.id, aIResource.id) && this.version == aIResource.version && Intrinsics.areEqual(this.path, aIResource.path) && Intrinsics.areEqual(this.type, aIResource.type);
    }

    public final String getApkPath() {
        if (Intrinsics.areEqual(this.type, "PLUGIN")) {
            return this.jsonObject.optString("apkPath");
        }
        return null;
    }

    public final String getId() {
        return this.id;
    }

    public final String getJsonSource() {
        return this.jsonSource;
    }

    public final String getLibDir() {
        if (Intrinsics.areEqual(this.type, "PLUGIN")) {
            return this.jsonObject.optString("libDir");
        }
        return null;
    }

    public final String getPath() {
        return this.path;
    }

    public final String getType() {
        return this.type;
    }

    public final long getVersion() {
        return this.version;
    }

    public int hashCode() {
        return this.type.hashCode() + ((this.path.hashCode() + ((Long.hashCode(this.version) + (this.id.hashCode() * 31)) * 31)) * 31);
    }

    public final String identifier() {
        return this.id + ":" + this.version;
    }

    public final void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setJsonSource(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.jsonSource = str;
    }

    public final void setPath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.path = str;
    }

    public final void setType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.type = str;
    }

    public final void setVersion(long j) {
        this.version = j;
    }

    public String toString() {
        return "AIResource(id=" + this.id + ", version=" + this.version + ", path=" + this.path + ", type=" + this.type + ")";
    }
}

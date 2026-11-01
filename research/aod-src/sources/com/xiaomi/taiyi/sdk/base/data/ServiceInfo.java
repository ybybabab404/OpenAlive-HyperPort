package com.xiaomi.taiyi.sdk.base.data;

import android.content.Context;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.transfer.data.AIBody;
import com.xiaomi.taiyi.sdk.transfer.data.AIHeaders;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
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
public final class ServiceInfo {
    public static final Companion Companion = new Companion(null);
    public static final String LOCAL_SERVICE_ACTION = "com.xiaomi.taiyi.LOCAL_ABILITY";
    public static final String LOCAL_SERVICE_ID = "localAbility";
    public static final String LOCAL_SERVICE_NAME = "com.xiaomi.taiyi.local.ability";
    private final String action;
    private final String id;
    private final String name;
    private AIHeaders pack;
    private final String packageName;

    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final ServiceInfo localService(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            String packageName = context.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
            ServiceInfo serviceInfo = new ServiceInfo(ServiceInfo.LOCAL_SERVICE_ID, ServiceInfo.LOCAL_SERVICE_NAME, ServiceInfo.LOCAL_SERVICE_ACTION, packageName);
            serviceInfo.setPack(new AIHeaders(context));
            return serviceInfo;
        }

        @JvmStatic
        public final ServiceInfo parse(AIResponse response) {
            Intrinsics.checkNotNullParameter(response, "response");
            AIBody body = response.getBody();
            String string = body.getString(AIKeys.KEY_AI_SERVICE_ID, "");
            if (string == null) {
                string = "";
            }
            if (string.length() == 0) {
                return null;
            }
            String string2 = body.getString(AIKeys.KEY_AI_SERVICE_NAME, "");
            if (string2 == null) {
                string2 = "";
            }
            if (string2.length() == 0) {
                return null;
            }
            String string3 = body.getString(AIKeys.KEY_AI_SERVICE_ACTION, "");
            if (string3 == null) {
                string3 = "";
            }
            if (string3.length() == 0) {
                return null;
            }
            String string4 = body.getString(AIKeys.KEY_AI_SERVICE_PACKAGE, "");
            String packageName = string4 != null ? string4 : "";
            if (packageName.length() == 0) {
                packageName = response.getHeaders().getPackageName();
            }
            if (packageName.length() == 0) {
                return null;
            }
            ServiceInfo serviceInfo = new ServiceInfo(string, string2, string3, packageName);
            serviceInfo.setPack(response.getHeaders());
            return serviceInfo;
        }
    }

    public ServiceInfo(String id, String name, String action, String packageName) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        this.id = id;
        this.name = name;
        this.action = action;
        this.packageName = packageName;
        this.pack = new AIHeaders();
    }

    public static /* synthetic */ ServiceInfo copy$default(ServiceInfo serviceInfo, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = serviceInfo.id;
        }
        if ((i & 2) != 0) {
            str2 = serviceInfo.name;
        }
        if ((i & 4) != 0) {
            str3 = serviceInfo.action;
        }
        if ((i & 8) != 0) {
            str4 = serviceInfo.packageName;
        }
        return serviceInfo.copy(str, str2, str3, str4);
    }

    @JvmStatic
    public static final ServiceInfo localService(Context context) {
        return Companion.localService(context);
    }

    @JvmStatic
    public static final ServiceInfo parse(AIResponse aIResponse) {
        return Companion.parse(aIResponse);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.name;
    }

    public final String component3() {
        return this.action;
    }

    public final String component4() {
        return this.packageName;
    }

    public final ServiceInfo copy(String id, String name, String action, String packageName) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return new ServiceInfo(id, name, action, packageName);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServiceInfo)) {
            return false;
        }
        ServiceInfo serviceInfo = (ServiceInfo) obj;
        return Intrinsics.areEqual(this.id, serviceInfo.id) && Intrinsics.areEqual(this.name, serviceInfo.name) && Intrinsics.areEqual(this.action, serviceInfo.action) && Intrinsics.areEqual(this.packageName, serviceInfo.packageName);
    }

    public final void fill(AIBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        body.putString(AIKeys.KEY_AI_SERVICE_ID, this.id);
        body.putString(AIKeys.KEY_AI_SERVICE_NAME, this.name);
        body.putString(AIKeys.KEY_AI_SERVICE_ACTION, this.action);
        body.putString(AIKeys.KEY_AI_SERVICE_PACKAGE, this.packageName);
    }

    public final String getAction() {
        return this.action;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final AIHeaders getPack() {
        return this.pack;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public int hashCode() {
        return this.packageName.hashCode() + ((this.action.hashCode() + ((this.name.hashCode() + (this.id.hashCode() * 31)) * 31)) * 31);
    }

    public final boolean isLocal() {
        return Intrinsics.areEqual(this.id, LOCAL_SERVICE_ID) && Intrinsics.areEqual(this.name, LOCAL_SERVICE_NAME);
    }

    public final void setPack(AIHeaders aIHeaders) {
        Intrinsics.checkNotNullParameter(aIHeaders, "<set-?>");
        this.pack = aIHeaders;
    }

    public String toString() {
        return "ServiceInfo(id=" + this.id + ", name=" + this.name + ", action=" + this.action + ", packageName=" + this.packageName + ")";
    }
}

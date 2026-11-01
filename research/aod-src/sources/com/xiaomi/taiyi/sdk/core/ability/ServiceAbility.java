package com.xiaomi.taiyi.sdk.core.ability;

import android.os.DeadObjectException;
import com.xiaomi.taiyi.sdk.base.data.ServiceInfo;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.transfer.core.IAIAbility;
import kotlin.Metadata;
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
public final class ServiceAbility {
    public static final Companion Companion = new Companion(null);
    public final IAIAbility a;
    public final ServiceInfo b;

    @Metadata
    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
        }
    }

    public ServiceAbility(IAIAbility proxy, ServiceInfo service) {
        Intrinsics.checkNotNullParameter(proxy, "proxy");
        Intrinsics.checkNotNullParameter(service, "service");
        this.a = proxy;
        this.b = service;
    }

    public final IAIAbility getProxy$core_release() {
        return this.a;
    }

    public final ServiceInfo getService() {
        return this.b;
    }

    public final boolean isAlive() {
        try {
            return isBinderAlive() && this.a.isAlive();
        } catch (DeadObjectException e) {
            SdkLog.e("ServiceAbility", "isAlive DeadObjectException! " + e.getMessage());
            return true;
        } catch (Throwable th) {
            SdkLog.e("ServiceAbility", "isAlive Throwable! " + th.getMessage());
            return true;
        }
    }

    public final boolean isBinderAlive() {
        try {
            if (!this.a.asBinder().isBinderAlive()) {
                return false;
            }
            this.a.name();
            return true;
        } catch (DeadObjectException e) {
            SdkLog.e("ServiceAbility", "isBinderAlive DeadObjectException! " + e.getMessage());
            return false;
        } catch (Throwable th) {
            SdkLog.e("ServiceAbility", "isBinderAlive Throwable! " + th.getMessage());
            return false;
        }
    }

    public final boolean isInternal() {
        return this.b.isLocal();
    }
}

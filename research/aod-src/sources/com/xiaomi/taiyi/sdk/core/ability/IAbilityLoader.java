package com.xiaomi.taiyi.sdk.core.ability;

import android.content.Context;
import com.xiaomi.taiyi.sdk.base.data.AIDetail;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.transfer.data.AIPackage;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import java.lang.reflect.Constructor;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
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
public interface IAbilityLoader {
    public static final Companion Companion = Companion.a;

    @Metadata
    public static final class Companion {
        public static final /* synthetic */ Companion a = new Companion();

        @JvmStatic
        public final IAbilityLoader localLoader(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            ClassLoader classLoader = AIPackage.class.getClassLoader();
            if (classLoader == null) {
                SdkLog.e("IAbilityLoader", "localLoader classloader is null");
                return null;
            }
            try {
                Constructor<?> constructor = classLoader.loadClass("com.xiaomi.taiyi.plugin.local.LocalAbilityLoader").getConstructor(Context.class);
                Intrinsics.checkNotNullExpressionValue(constructor, "getConstructor(...)");
                constructor.setAccessible(true);
                Object objNewInstance = constructor.newInstance(context);
                if (objNewInstance instanceof IAbilityLoader) {
                    return (IAbilityLoader) objNewInstance;
                }
                return null;
            } catch (Exception e) {
                SdkLog.e("IAbilityLoader", "localLoader exception: " + e.getMessage());
                return null;
            }
        }
    }

    @JvmStatic
    static IAbilityLoader localLoader(Context context) {
        return Companion.localLoader(context);
    }

    void destroySync(String str);

    AIResponse loadSync(AIRequest aIRequest, AIDetail aIDetail);
}

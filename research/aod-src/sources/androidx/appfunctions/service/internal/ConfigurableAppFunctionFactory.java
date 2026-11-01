package androidx.appfunctions.service.internal;

import android.content.Context;
import android.util.Log;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.service.AppFunctionConfiguration;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: ConfigurableAppFunctionFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
public final class ConfigurableAppFunctionFactory<T> {
    public final Context context;
    public final Function0 defaultFactory;

    /* JADX INFO: compiled from: ConfigurableAppFunctionFactory.kt */
    @Metadata
    public static final class AppFunctionInstantiationException extends RuntimeException {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AppFunctionInstantiationException(String errorMessage) {
            super(errorMessage);
            Intrinsics.checkNotNullParameter(errorMessage, "errorMessage");
        }
    }

    public ConfigurableAppFunctionFactory(Context context, Function0 function0) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.defaultFactory = function0;
    }

    public final Object createEnclosingClass(Class enclosingClass) {
        AppFunctionConfiguration appFunctionConfiguration;
        Map enclosingClassFactories;
        Intrinsics.checkNotNullParameter(enclosingClass, "enclosingClass");
        Object applicationContext = this.context.getApplicationContext();
        Function0 function0 = null;
        AppFunctionConfiguration.Provider provider = applicationContext instanceof AppFunctionConfiguration.Provider ? (AppFunctionConfiguration.Provider) applicationContext : null;
        if (provider != null && (appFunctionConfiguration = provider.getAppFunctionConfiguration()) != null && (enclosingClassFactories = appFunctionConfiguration.getEnclosingClassFactories()) != null) {
            function0 = (Function0) enclosingClassFactories.get(enclosingClass);
        }
        if (function0 != null) {
            Object objInvoke = function0.invoke();
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type T of androidx.appfunctions.service.internal.ConfigurableAppFunctionFactory");
            return objInvoke;
        }
        Log.d("AppFunctions", "Unable to find custom factory for [" + enclosingClass + ']');
        return createFromDefault(enclosingClass);
    }

    public final Object createFromDefault(Class cls) {
        Function0 function0 = this.defaultFactory;
        if (function0 != null) {
            return function0.invoke();
        }
        throw new AppFunctionInstantiationException("Unable to instantiate " + cls + ". Either setup a custom factory with AppFunctionConfiguration or provide a public no-arg constructor.");
    }
}

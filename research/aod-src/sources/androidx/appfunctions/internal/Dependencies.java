package androidx.appfunctions.internal;

import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Dependencies.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
@RestrictTo
public final class Dependencies {
    public static final Dependencies INSTANCE = new Dependencies();
    public static final Lazy translatorSelector$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dependencies$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dependencies.translatorSelector_delegate$lambda$0();
        }
    });
    public static final Lazy schemaAppFunctionInventory$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dependencies$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dependencies.schemaAppFunctionInventory_delegate$lambda$1();
        }
    });
    public static final Lazy aggregatedAppFunctionInventory$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dependencies$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dependencies.aggregatedAppFunctionInventory_delegate$lambda$2();
        }
    });
    public static final Lazy appFunctionInventory$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dependencies$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dependencies.appFunctionInventory_delegate$lambda$3();
        }
    });

    public static final AggregatedAppFunctionInventory aggregatedAppFunctionInventory_delegate$lambda$2() {
        try {
            return (AggregatedAppFunctionInventory) ClassUtilsKt.findImpl(AggregatedAppFunctionInventory.class, "$", "_Impl");
        } catch (Exception e) {
            Log.d("AppFunctions", "Cannot find AggregatedAppFunctionInventory implementation", e);
            return null;
        }
    }

    public static final AppFunctionInventory appFunctionInventory_delegate$lambda$3() {
        Dependencies dependencies = INSTANCE;
        return dependencies.getAggregatedAppFunctionInventory() != null ? dependencies.getAggregatedAppFunctionInventory() : dependencies.getSchemaAppFunctionInventory$appfunctions_release();
    }

    public static final SchemaAppFunctionInventory schemaAppFunctionInventory_delegate$lambda$1() {
        try {
            return (SchemaAppFunctionInventory) ClassUtilsKt.findImpl(SchemaAppFunctionInventory.class, "$", "_Impl");
        } catch (Exception unused) {
            Log.d("AppFunctions", "Cannot find SchemaAppFunctionInventory implementation");
            return null;
        }
    }

    public static final TranslatorSelector translatorSelector_delegate$lambda$0() {
        try {
            return (TranslatorSelector) ClassUtilsKt.findImpl(TranslatorSelector.class, "", "Impl");
        } catch (Exception unused) {
            Log.d("AppFunctions", "Cannot find TranslatorSelectorImpl");
            return new NullTranslatorSelector();
        }
    }

    public final AggregatedAppFunctionInventory getAggregatedAppFunctionInventory() {
        return (AggregatedAppFunctionInventory) aggregatedAppFunctionInventory$delegate.getValue();
    }

    public final AppFunctionInventory getAppFunctionInventory$appfunctions_release() {
        return (AppFunctionInventory) appFunctionInventory$delegate.getValue();
    }

    public final SchemaAppFunctionInventory getSchemaAppFunctionInventory$appfunctions_release() {
        return (SchemaAppFunctionInventory) schemaAppFunctionInventory$delegate.getValue();
    }

    public final TranslatorSelector getTranslatorSelector() {
        return (TranslatorSelector) translatorSelector$delegate.getValue();
    }
}

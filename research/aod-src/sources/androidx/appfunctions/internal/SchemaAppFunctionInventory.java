package androidx.appfunctions.internal;

import androidx.annotation.RestrictTo;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SchemaAppFunctionInventory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
public abstract class SchemaAppFunctionInventory implements AppFunctionInventory {

    @NotNull
    private final Lazy schemaFunctionsMap$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.SchemaAppFunctionInventory$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return SchemaAppFunctionInventory.schemaFunctionsMap_delegate$lambda$1(this.f$0);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map schemaFunctionsMap_delegate$lambda$1(SchemaAppFunctionInventory schemaAppFunctionInventory) {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata : schemaAppFunctionInventory.getFunctionIdToMetadataMap().values()) {
            AppFunctionSchemaMetadata schema = compileTimeAppFunctionMetadata.getSchema();
            if (schema != null) {
                mapCreateMapBuilder.put(schema, compileTimeAppFunctionMetadata);
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    @Override // androidx.appfunctions.internal.AppFunctionInventory
    @NotNull
    public abstract /* synthetic */ AppFunctionComponentsMetadata getComponentsMetadata();

    @Override // androidx.appfunctions.internal.AppFunctionInventory
    @NotNull
    public abstract /* synthetic */ Map getFunctionIdToMetadataMap();

    @NotNull
    public final Map<AppFunctionSchemaMetadata, CompileTimeAppFunctionMetadata> getSchemaFunctionsMap() {
        return (Map) this.schemaFunctionsMap$delegate.getValue();
    }
}

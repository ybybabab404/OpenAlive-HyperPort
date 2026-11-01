package androidx.appfunctions;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AppFunctionSearchSpec.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AppFunctionSearchSpec {
    public final int minSchemaVersion;
    public final Set packageNames;
    public final String schemaCategory;
    public final String schemaName;

    public static final CharSequence getOrQueryExpression$lambda$3(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return '\"' + it + '\"';
    }

    public final String getOrQueryExpression(Set set) {
        return CollectionsKt.joinToString$default(set, " OR ", null, null, 0, null, new Function1() { // from class: androidx.appfunctions.AppFunctionSearchSpec$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AppFunctionSearchSpec.getOrQueryExpression$lambda$3((String) obj);
            }
        }, 30, null);
    }

    public final Set getPackageNames() {
        return this.packageNames;
    }

    public final String toStaticMetadataAppSearchQuery$appfunctions_release() {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Set set = this.packageNames;
        if (set != null) {
            if (set.isEmpty()) {
                throw new IllegalStateException("Cannot filter by empty set of package names.");
            }
            listCreateListBuilder.add("packageName:(" + getOrQueryExpression(this.packageNames) + ')');
        }
        if (this.schemaName != null) {
            listCreateListBuilder.add("schemaName:\"" + this.schemaName + '\"');
        }
        if (this.schemaCategory != null) {
            listCreateListBuilder.add("schemaCategory:\"" + this.schemaCategory + '\"');
        }
        if (this.minSchemaVersion > 0) {
            listCreateListBuilder.add("schemaVersion>=" + this.minSchemaVersion);
        }
        return CollectionsKt.joinToString$default(CollectionsKt.build(listCreateListBuilder), " ", null, null, 0, null, null, 62, null);
    }
}

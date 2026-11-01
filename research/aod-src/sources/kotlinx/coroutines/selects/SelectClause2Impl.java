package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Select.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SelectClause2Impl<P, Q> implements SelectClause2<P, Q> {
    public final Object clauseObject;
    public final Function3 onCancellationConstructor;
    public final Function3 processResFunc;
    public final Function3 regFunc;

    public SelectClause2Impl(Object obj, Function3 function3, Function3 function4, Function3 function5) {
        this.clauseObject = obj;
        this.regFunc = function3;
        this.processResFunc = function4;
        this.onCancellationConstructor = function5;
    }

    public /* synthetic */ SelectClause2Impl(Object obj, Function3 function3, Function3 function4, Function3 function5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, function3, function4, (i & 8) != 0 ? null : function5);
    }

    @Override // kotlinx.coroutines.selects.SelectClause
    public Function3 getProcessResFunc() {
        return this.processResFunc;
    }

    @Override // kotlinx.coroutines.selects.SelectClause
    public Function3 getRegFunc() {
        return this.regFunc;
    }
}

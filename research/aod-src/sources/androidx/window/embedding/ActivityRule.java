package androidx.window.embedding;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: ActivityRule.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ActivityRule extends EmbeddingRule {
    public final boolean alwaysExpand;
    public final Set filters;

    /* JADX INFO: compiled from: ActivityRule.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Builder {
        public boolean alwaysExpand;
        public final Set filters;
        public String tag;

        public Builder(Set filters) {
            Intrinsics.checkNotNullParameter(filters, "filters");
            this.filters = filters;
        }

        public final ActivityRule build() {
            return new ActivityRule(this.tag, this.filters, this.alwaysExpand);
        }

        public final Builder setAlwaysExpand(boolean z) {
            this.alwaysExpand = z;
            return this;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityRule(String str, Set filters, boolean z) {
        super(str);
        Intrinsics.checkNotNullParameter(filters, "filters");
        this.filters = filters;
        this.alwaysExpand = z;
    }

    @Override // androidx.window.embedding.EmbeddingRule
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityRule) || !super.equals(obj)) {
            return false;
        }
        ActivityRule activityRule = (ActivityRule) obj;
        return Intrinsics.areEqual(this.filters, activityRule.filters) && this.alwaysExpand == activityRule.alwaysExpand;
    }

    public final boolean getAlwaysExpand() {
        return this.alwaysExpand;
    }

    public final Set getFilters() {
        return this.filters;
    }

    @Override // androidx.window.embedding.EmbeddingRule
    public int hashCode() {
        return (((super.hashCode() * 31) + this.filters.hashCode()) * 31) + Boolean.hashCode(this.alwaysExpand);
    }

    public String toString() {
        return "ActivityRule:{tag={" + getTag() + "},filters={" + this.filters + "}, alwaysExpand={" + this.alwaysExpand + "}}";
    }
}

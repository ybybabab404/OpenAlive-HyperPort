package androidx.appsearch.platformstorage.converter;

import android.annotation.SuppressLint;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.AppSearchSchema;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class SchemaToPlatformConverter {

    @RequiresApi
    @SuppressLint({"NewApi"})
    public static class ApiHelperForB {
        @DoNotInline
        @SuppressLint({"WrongConstant"})
        public static AppSearchSchema.EmbeddingPropertyConfig createJetpackEmbeddingPropertyConfig(android.app.appsearch.AppSearchSchema.EmbeddingPropertyConfig embeddingPropertyConfig) {
            return new AppSearchSchema.EmbeddingPropertyConfig.Builder(embeddingPropertyConfig.getName()).setCardinality(embeddingPropertyConfig.getCardinality()).setIndexingType(embeddingPropertyConfig.getIndexingType()).build();
        }

        @DoNotInline
        @SuppressLint({"WrongConstant"})
        public static android.app.appsearch.AppSearchSchema.PropertyConfig createPlatformEmbeddingPropertyConfig(AppSearchSchema.EmbeddingPropertyConfig embeddingPropertyConfig) {
            SchemaToPlatformConverter$ApiHelperForB$$ExternalSyntheticApiModelOutline7.m();
            return SchemaToPlatformConverter$ApiHelperForB$$ExternalSyntheticApiModelOutline6.m(embeddingPropertyConfig.getName()).setCardinality(embeddingPropertyConfig.getCardinality()).setIndexingType(embeddingPropertyConfig.getIndexingType()).build();
        }
    }

    @RequiresApi
    public static class ApiHelperForU {
        @DoNotInline
        @SuppressLint({"WrongConstant"})
        public static int getIndexingType(android.app.appsearch.AppSearchSchema.LongPropertyConfig longPropertyConfig) {
            return longPropertyConfig.getIndexingType();
        }

        @DoNotInline
        @SuppressLint({"WrongConstant"})
        public static int getJoinableValueType(android.app.appsearch.AppSearchSchema.StringPropertyConfig stringPropertyConfig) {
            return stringPropertyConfig.getJoinableValueType();
        }

        @DoNotInline
        public static void setIndexingType(android.app.appsearch.AppSearchSchema.LongPropertyConfig.Builder builder, int i) {
            builder.setIndexingType(i);
        }

        @DoNotInline
        public static void setJoinableValueType(android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder builder, int i) {
            builder.setJoinableValueType(i);
        }
    }

    @RequiresApi
    public static class ApiHelperForV {
        @DoNotInline
        public static void addIndexableNestedProperties(android.app.appsearch.AppSearchSchema.DocumentPropertyConfig.Builder builder, Collection<String> collection) {
            builder.addIndexableNestedProperties(collection);
        }

        @DoNotInline
        @SuppressLint({"NewApi"})
        public static void addParentType(android.app.appsearch.AppSearchSchema.Builder builder, String str) {
            builder.addParentType(str);
        }

        @DoNotInline
        public static List<String> getIndexableNestedProperties(android.app.appsearch.AppSearchSchema.DocumentPropertyConfig documentPropertyConfig) {
            return documentPropertyConfig.getIndexableNestedProperties();
        }

        @DoNotInline
        public static List<String> getParentTypes(android.app.appsearch.AppSearchSchema appSearchSchema) {
            return appSearchSchema.getParentTypes();
        }
    }
}

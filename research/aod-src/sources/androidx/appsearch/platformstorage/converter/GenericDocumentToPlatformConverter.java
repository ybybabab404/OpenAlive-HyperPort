package androidx.appsearch.platformstorage.converter;

import android.annotation.SuppressLint;
import android.app.appsearch.EmbeddingVector;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.AppSearchBlobHandle;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.core.util.Preconditions;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class GenericDocumentToPlatformConverter {

    @RequiresApi
    public static class ApiHelperForB {
        @DoNotInline
        @SuppressLint({"NewApi"})
        public static void setJetpackPropertyEmbedding(GenericDocument.Builder<GenericDocument.Builder<?>> builder, String str, EmbeddingVector[] embeddingVectorArr) {
            androidx.appsearch.app.EmbeddingVector[] embeddingVectorArr2 = new androidx.appsearch.app.EmbeddingVector[embeddingVectorArr.length];
            for (int i = 0; i < embeddingVectorArr.length; i++) {
                embeddingVectorArr2[i] = new androidx.appsearch.app.EmbeddingVector(embeddingVectorArr[i].getValues(), embeddingVectorArr[i].getModelSignature());
            }
            builder.setPropertyEmbedding(str, embeddingVectorArr2);
        }

        @DoNotInline
        @SuppressLint({"NewApi"})
        public static void setPlatformPropertyEmbedding(android.app.appsearch.GenericDocument.Builder<android.app.appsearch.GenericDocument.Builder<?>> builder, String str, androidx.appsearch.app.EmbeddingVector[] embeddingVectorArr) {
            EmbeddingVector[] embeddingVectorArr2 = new EmbeddingVector[embeddingVectorArr.length];
            for (int i = 0; i < embeddingVectorArr.length; i++) {
                GenericDocumentToPlatformConverter$ApiHelperForB$$ExternalSyntheticApiModelOutline3.m();
                embeddingVectorArr2[i] = GenericDocumentToPlatformConverter$ApiHelperForB$$ExternalSyntheticApiModelOutline2.m(embeddingVectorArr[i].getValues(), embeddingVectorArr[i].getModelSignature());
            }
            builder.setPropertyEmbedding(str, embeddingVectorArr2);
        }
    }

    public static GenericDocument toJetpackGenericDocument(android.app.appsearch.GenericDocument genericDocument) {
        Preconditions.checkNotNull(genericDocument);
        GenericDocument.Builder builder = new GenericDocument.Builder(genericDocument.getNamespace(), genericDocument.getId(), genericDocument.getSchemaType());
        builder.setScore(genericDocument.getScore()).setTtlMillis(genericDocument.getTtlMillis()).setCreationTimestampMillis(genericDocument.getCreationTimestampMillis());
        for (String str : genericDocument.getPropertyNames()) {
            Object property = genericDocument.getProperty(str);
            if (str.equals("$$__AppSearch__parentTypes")) {
                if (!(property instanceof String[])) {
                    throw new IllegalStateException(String.format("Parents list must be of String[] type, but got %s", property.getClass().toString()));
                }
                builder.setParentTypes(Arrays.asList((String[]) property));
            } else if (property instanceof String[]) {
                builder.setPropertyString(str, (String[]) property);
            } else if (property instanceof long[]) {
                builder.setPropertyLong(str, (long[]) property);
            } else if (property instanceof double[]) {
                builder.setPropertyDouble(str, (double[]) property);
            } else if (property instanceof boolean[]) {
                builder.setPropertyBoolean(str, (boolean[]) property);
            } else if (property instanceof byte[][]) {
                builder.setPropertyBytes(str, (byte[][]) property);
            } else if (property instanceof android.app.appsearch.GenericDocument[]) {
                android.app.appsearch.GenericDocument[] genericDocumentArr = (android.app.appsearch.GenericDocument[]) property;
                GenericDocument[] genericDocumentArr2 = new GenericDocument[genericDocumentArr.length];
                for (int i = 0; i < genericDocumentArr.length; i++) {
                    genericDocumentArr2[i] = toJetpackGenericDocument(genericDocumentArr[i]);
                }
                builder.setPropertyDocument(str, genericDocumentArr2);
            } else {
                if (!AppSearchVersionUtil.isAtLeastB() || !(property instanceof EmbeddingVector[])) {
                    throw new IllegalStateException(String.format("Property \"%s\" has unsupported value type %s", str, property.getClass().toString()));
                }
                ApiHelperForB.setJetpackPropertyEmbedding(builder, str, (EmbeddingVector[]) property);
            }
        }
        return builder.build();
    }

    public static android.app.appsearch.GenericDocument toPlatformGenericDocument(GenericDocument genericDocument) {
        Preconditions.checkNotNull(genericDocument);
        android.app.appsearch.GenericDocument.Builder builder = new android.app.appsearch.GenericDocument.Builder(genericDocument.getNamespace(), genericDocument.getId(), genericDocument.getSchemaType());
        builder.setScore(genericDocument.getScore()).setTtlMillis(genericDocument.getTtlMillis()).setCreationTimestampMillis(genericDocument.getCreationTimestampMillis());
        for (String str : genericDocument.getPropertyNames()) {
            Object property = genericDocument.getProperty(str);
            if (property instanceof String[]) {
                builder.setPropertyString(str, (String[]) property);
            } else if (property instanceof long[]) {
                builder.setPropertyLong(str, (long[]) property);
            } else if (property instanceof double[]) {
                builder.setPropertyDouble(str, (double[]) property);
            } else if (property instanceof boolean[]) {
                builder.setPropertyBoolean(str, (boolean[]) property);
            } else if (property instanceof byte[][]) {
                builder.setPropertyBytes(str, (byte[][]) property);
            } else if (property instanceof GenericDocument[]) {
                GenericDocument[] genericDocumentArr = (GenericDocument[]) property;
                android.app.appsearch.GenericDocument[] genericDocumentArr2 = new android.app.appsearch.GenericDocument[genericDocumentArr.length];
                for (int i = 0; i < genericDocumentArr.length; i++) {
                    genericDocumentArr2[i] = toPlatformGenericDocument(genericDocumentArr[i]);
                }
                builder.setPropertyDocument(str, genericDocumentArr2);
            } else {
                if (!(property instanceof androidx.appsearch.app.EmbeddingVector[])) {
                    if (property instanceof AppSearchBlobHandle[]) {
                        throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
                    }
                    throw new IllegalStateException(String.format("Property \"%s\" has unsupported value type %s", str, property.getClass().toString()));
                }
                if (!AppSearchVersionUtil.isAtLeastB()) {
                    throw new UnsupportedOperationException("SCHEMA_EMBEDDING_PROPERTY_CONFIG is not available on this AppSearch implementation.");
                }
                ApiHelperForB.setPlatformPropertyEmbedding(builder, str, (androidx.appsearch.app.EmbeddingVector[]) property);
            }
        }
        return builder.build();
    }
}

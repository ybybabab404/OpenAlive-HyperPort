package androidx.appsearch.annotation;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.app.LongSerializer;
import androidx.appsearch.app.StringSerializer;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX WARN: Method from annotation default annotation not found: name */
/* JADX WARN: Method from annotation default annotation not found: parent */
/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Document {

    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: required */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @ExperimentalAppSearchApi
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BlobHandleProperty {
    }

    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: required */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BooleanProperty {
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BuilderProducer {
    }

    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: required */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BytesProperty {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface CreationTimestampMillis {
    }

    /* JADX WARN: Method from annotation default annotation not found: indexNestedProperties */
    /* JADX WARN: Method from annotation default annotation not found: indexableNestedPropertiesList */
    /* JADX WARN: Method from annotation default annotation not found: inheritIndexableNestedPropertiesFromSuperclass */
    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: required */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface DocumentProperty {
    }

    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: required */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface DoubleProperty {
    }

    /* JADX WARN: Method from annotation default annotation not found: indexingType */
    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: quantizationType */
    /* JADX WARN: Method from annotation default annotation not found: required */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface EmbeddingProperty {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface Id {
    }

    /* JADX WARN: Method from annotation default annotation not found: indexingType */
    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: required */
    /* JADX WARN: Method from annotation default annotation not found: serializer */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface LongProperty {

        public static final class DefaultSerializer implements LongSerializer<Long> {
        }
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface Namespace {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface Score {
    }

    /* JADX WARN: Method from annotation default annotation not found: indexingType */
    /* JADX WARN: Method from annotation default annotation not found: joinableValueType */
    /* JADX WARN: Method from annotation default annotation not found: name */
    /* JADX WARN: Method from annotation default annotation not found: required */
    /* JADX WARN: Method from annotation default annotation not found: serializer */
    /* JADX WARN: Method from annotation default annotation not found: tokenizerType */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface StringProperty {

        public static final class DefaultSerializer implements StringSerializer<String> {
        }
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface TtlMillis {
    }
}

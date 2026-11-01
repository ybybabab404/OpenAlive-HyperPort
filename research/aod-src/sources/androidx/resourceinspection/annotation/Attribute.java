package androidx.resourceinspection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX WARN: Method from annotation default annotation not found: intMapping */
/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Attribute {

    /* JADX WARN: Method from annotation default annotation not found: mask */
    @Target({})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IntMap {
    }
}

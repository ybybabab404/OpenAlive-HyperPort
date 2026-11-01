package androidx.appsearch.safeparcel;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public abstract class AbstractSafeParcelable implements SafeParcelable {

    public @interface Constructor {
    }

    /* JADX WARN: Method from annotation default annotation not found: defaultValue */
    /* JADX WARN: Method from annotation default annotation not found: defaultValueUnchecked */
    /* JADX WARN: Method from annotation default annotation not found: getter */
    /* JADX WARN: Method from annotation default annotation not found: type */
    public @interface Field {
    }

    /* JADX WARN: Method from annotation default annotation not found: getter */
    public @interface Indicator {
    }

    public @interface Param {
    }

    /* JADX WARN: Method from annotation default annotation not found: defaultValue */
    /* JADX WARN: Method from annotation default annotation not found: defaultValueUnchecked */
    public @interface RemovedParam {
    }

    public @interface Reserved {
    }

    /* JADX WARN: Method from annotation default annotation not found: getter */
    /* JADX WARN: Method from annotation default annotation not found: type */
    public @interface VersionField {
    }

    public final int describeContents() {
        return 0;
    }
}

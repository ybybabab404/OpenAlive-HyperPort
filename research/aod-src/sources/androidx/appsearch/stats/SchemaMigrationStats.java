package androidx.appsearch.stats;

import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
@SafeParcelable.Class
public final class SchemaMigrationStats extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.SchemaMigrationStatsCreator();

    public static class Builder {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SchemaMigrationCallType {
    }
}

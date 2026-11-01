package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class SearchSuggestionSpec extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.SearchSuggestionSpecCreator();

    public static final class Builder {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface SuggestionRankingStrategy {
    }
}

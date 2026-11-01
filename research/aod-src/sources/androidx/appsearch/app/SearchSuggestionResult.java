package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class SearchSuggestionResult extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.SearchSuggestionResultCreator();
    public Integer mHashCode;
    public final String mSuggestedResult;

    public static final class Builder {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SearchSuggestionResult) {
            return this.mSuggestedResult.equals(((SearchSuggestionResult) obj).mSuggestedResult);
        }
        return false;
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(this.mSuggestedResult.hashCode());
        }
        return this.mHashCode.intValue();
    }
}

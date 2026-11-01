package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
@SafeParcelable.Class
public class SearchResultPage extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.SearchResultPageCreator();
}

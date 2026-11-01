package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
@SafeParcelable.Class
public final class CommitBlobResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.CommitBlobResponseCreator();
}

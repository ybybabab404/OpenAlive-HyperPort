package androidx.appsearch.app;

import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import androidx.appsearch.app.aidl.AppSearchBatchResultParcelV2;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
@SafeParcelable.Class
public final class OpenBlobForWriteResponse extends AbstractSafeParcelable implements Closeable {
    public static final Parcelable.Creator CREATOR = new StubCreators.OpenBlobForWriteResponseCreator();
    public final AppSearchBatchResultParcelV2 mResultParcel;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Iterator it = this.mResultParcel.getResult().getSuccesses().values().iterator();
        while (it.hasNext()) {
            try {
                ((ParcelFileDescriptor) it.next()).close();
            } catch (IOException unused) {
            }
        }
    }
}

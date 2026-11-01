package androidx.appsearch.safeparcel.stub;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
abstract class AbstractCreator<T> implements Parcelable.Creator<T> {
    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        throw new UnsupportedOperationException("createFromParcel is not implemented and should not be used.");
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        throw new UnsupportedOperationException("newArray is not implemented and should not be used.");
    }
}

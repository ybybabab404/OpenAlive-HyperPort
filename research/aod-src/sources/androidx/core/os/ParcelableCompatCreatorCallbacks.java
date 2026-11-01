package androidx.core.os;

import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface ParcelableCompatCreatorCallbacks<T> {
    Object createFromParcel(Parcel parcel, ClassLoader classLoader);

    Object[] newArray(int i);
}

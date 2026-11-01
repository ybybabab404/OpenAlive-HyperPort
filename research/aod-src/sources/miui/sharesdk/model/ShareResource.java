package miui.sharesdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public final class ShareResource implements Parcelable {
    public static final Parcelable.Creator<ShareResource> CREATOR = new Parcelable.Creator<ShareResource>() { // from class: miui.sharesdk.model.ShareResource.1
        @Override // android.os.Parcelable.Creator
        public ShareResource createFromParcel(Parcel parcel) {
            return new ShareResource(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ShareResource[] newArray(int i) {
            return new ShareResource[i];
        }
    };
    public final String resId;
    public final String subResId;

    public ShareResource(Parcel parcel) {
        this.resId = parcel.readString();
        this.subResId = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.resId);
        parcel.writeString(this.subResId);
    }
}

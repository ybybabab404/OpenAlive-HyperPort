package miui.sharesdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public final class InviteServerExtension implements Parcelable {
    public static final Parcelable.Creator<InviteServerExtension> CREATOR = new Parcelable.Creator<InviteServerExtension>() { // from class: miui.sharesdk.model.InviteServerExtension.1
        @Override // android.os.Parcelable.Creator
        public InviteServerExtension createFromParcel(Parcel parcel) {
            return new InviteServerExtension(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public InviteServerExtension[] newArray(int i) {
            return new InviteServerExtension[i];
        }
    };
    public final String extraInfo;
    public final String shareContent;

    public InviteServerExtension(Parcel parcel) {
        this.extraInfo = parcel.readString();
        this.shareContent = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.extraInfo);
        parcel.writeString(this.shareContent);
    }
}

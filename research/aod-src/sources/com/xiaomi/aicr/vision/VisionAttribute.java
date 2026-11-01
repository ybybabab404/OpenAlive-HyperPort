package com.xiaomi.aicr.vision;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class VisionAttribute implements Parcelable {
    public static final Parcelable.Creator<VisionAttribute> CREATOR = new Parcelable.Creator<VisionAttribute>() { // from class: com.xiaomi.aicr.vision.VisionAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisionAttribute createFromParcel(Parcel parcel) {
            return new VisionAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisionAttribute[] newArray(int i) {
            return new VisionAttribute[i];
        }
    };
    public boolean mIsExtractCombinedSubject;
    public boolean mIsMaxAreaAsSingleSubject;
    public boolean mIsPrivicy;
    public Bundle mParams;
    public int mVisionType;

    public static class Builder {
    }

    public VisionAttribute(int i, boolean z, boolean z2, boolean z3) {
        this.mVisionType = i;
        this.mIsPrivicy = z;
        this.mParams = new Bundle();
        this.mIsExtractCombinedSubject = z2;
        this.mIsMaxAreaAsSingleSubject = z3;
    }

    public VisionAttribute(Parcel parcel) {
        this.mVisionType = parcel.readInt();
        this.mIsPrivicy = parcel.readBoolean();
        this.mParams = parcel.readBundle();
        this.mIsExtractCombinedSubject = parcel.readBoolean();
        this.mIsMaxAreaAsSingleSubject = parcel.readBoolean();
    }

    public VisionAttribute(boolean z, boolean z2) {
        this(-1, false, z, z2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getType() {
        return this.mVisionType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mVisionType);
        parcel.writeBoolean(this.mIsPrivicy);
        parcel.writeBundle(this.mParams);
        parcel.writeBoolean(this.mIsExtractCombinedSubject);
        parcel.writeBoolean(this.mIsMaxAreaAsSingleSubject);
    }
}

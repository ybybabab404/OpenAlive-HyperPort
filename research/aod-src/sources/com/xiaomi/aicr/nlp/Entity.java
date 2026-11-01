package com.xiaomi.aicr.nlp;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class Entity implements Parcelable {
    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() { // from class: com.xiaomi.aicr.nlp.Entity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entity createFromParcel(Parcel parcel) {
            return new Entity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entity[] newArray(int i) {
            return new Entity[i];
        }
    };
    public int end;
    public int mType;
    public int start;
    public String str;

    public Entity(Parcel parcel) {
        this.start = -1;
        this.end = -1;
        this.str = parcel.readString();
        this.mType = parcel.readInt();
        this.start = parcel.readInt();
        this.end = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "text: " + this.str + ",mType:" + this.mType + ",start:" + this.start + ",end" + this.end;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.str);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.start);
        parcel.writeInt(this.end);
    }
}

package com.miui.miwallpaper.sensor;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class SensorParams implements Parcelable {
    public static final Parcelable.Creator<SensorParams> CREATOR = new Parcelable.Creator<SensorParams>() { // from class: com.miui.miwallpaper.sensor.SensorParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorParams createFromParcel(Parcel parcel) {
            return new SensorParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorParams[] newArray(int i) {
            return new SensorParams[i];
        }
    };
    public final String TAG = "SensorParams";
    public String animType;
    public int duration;
    public float factor;
    public int largeFrameCount;
    public int smallFrameCount;
    public int stiffness;

    public SensorParams(Parcel parcel) {
        this.stiffness = parcel.readInt();
        this.largeFrameCount = parcel.readInt();
        this.smallFrameCount = parcel.readInt();
        this.animType = parcel.readString();
        this.factor = parcel.readFloat();
        this.duration = parcel.readInt();
    }

    public SensorParams(String str, int i, int i2, int i3, float f, int i4) {
        this.stiffness = i;
        this.largeFrameCount = i2;
        this.smallFrameCount = i3;
        this.animType = str;
        this.factor = f;
        this.duration = i4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isValidParams() {
        Log.d("SensorParams", "isValid: largeFrameCount: " + this.largeFrameCount + " ,smallFrameCount: " + this.smallFrameCount + " ,duration: " + this.duration);
        return this.largeFrameCount >= 0 && this.smallFrameCount >= 0 && this.duration >= 0;
    }

    public String toString() {
        return "SensorParams{stiffness=" + this.stiffness + ", largeFrameCount=" + this.largeFrameCount + ", smallFrameCount=" + this.smallFrameCount + ", animType='" + this.animType + "', factor=" + this.factor + ", duration=" + this.duration + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.stiffness);
        parcel.writeInt(this.largeFrameCount);
        parcel.writeInt(this.smallFrameCount);
        parcel.writeString(this.animType);
        parcel.writeFloat(this.factor);
        parcel.writeInt(this.duration);
    }
}

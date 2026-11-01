package com.xiaomi.aicr.vision.imagesegment;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SegmentResult implements Parcelable {
    public static final Parcelable.Creator<SegmentResult> CREATOR = new Parcelable.Creator<SegmentResult>() { // from class: com.xiaomi.aicr.vision.imagesegment.SegmentResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SegmentResult createFromParcel(Parcel parcel) {
            return new SegmentResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SegmentResult[] newArray(int i) {
            return new SegmentResult[i];
        }
    };
    public List mBitmaps;
    public List mContours;
    public List mRects;

    public SegmentResult(Parcel parcel) {
        this.mBitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
        ArrayList arrayList = new ArrayList();
        this.mRects = arrayList;
        parcel.readList(arrayList, float[].class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        this.mContours = arrayList2;
        parcel.readList(arrayList2, float[].class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List getBitmaps() {
        return this.mBitmaps;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mBitmaps);
        parcel.writeList(this.mRects);
        parcel.writeList(this.mContours);
    }
}

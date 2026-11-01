package com.xiaomi.taiyi.segment.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SegmentResult implements Parcelable {
    public static final Parcelable.Creator<SegmentResult> CREATOR = new Parcelable.Creator<SegmentResult>() { // from class: com.xiaomi.taiyi.segment.data.SegmentResult.1
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
    private List<Bitmap> mBitmaps;
    private List<float[]> mContours;
    private List<float[]> mRects;

    public SegmentResult() {
    }

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

    public List<Bitmap> getBitmaps() {
        return this.mBitmaps;
    }

    public List<float[]> getContours() {
        return this.mContours;
    }

    public List<float[]> getRects() {
        return this.mRects;
    }

    public void readFromParcel(Parcel parcel) {
        this.mBitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
        ArrayList arrayList = new ArrayList();
        this.mRects = arrayList;
        parcel.readList(arrayList, float[].class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        this.mContours = arrayList2;
        parcel.readList(arrayList2, float[].class.getClassLoader());
    }

    public void setBitmaps(List<Bitmap> list) {
        this.mBitmaps = list;
    }

    public void setContours(List<float[]> list) {
        this.mContours = list;
    }

    public void setRects(List<float[]> list) {
        this.mRects = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mBitmaps);
        parcel.writeList(this.mRects);
        parcel.writeList(this.mContours);
    }
}

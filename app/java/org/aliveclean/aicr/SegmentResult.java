package org.aliveclean.aicr;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * 手写的 Parcel 代理：字段顺序按反编译得到的
 * {@code com.xiaomi.aicr.vision.imagesegment.SegmentResult} 排列。
 * 改字段顺序或类型会与 ROM 侧解析错位，跨进程直接崩。
 */
public final class SegmentResult implements Parcelable {
    public static final Creator<SegmentResult> CREATOR = new Creator<SegmentResult>() {
        @Override public SegmentResult createFromParcel(Parcel parcel) { return new SegmentResult(parcel); }
        @Override public SegmentResult[] newArray(int size) { return new SegmentResult[size]; }
    };

    public List<Bitmap> bitmaps;
    public List<float[]> rects;
    public List<float[]> contours;

    public SegmentResult() { }

    private SegmentResult(Parcel parcel) {
        bitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
        rects = new ArrayList<>();
        parcel.readList(rects, float[].class.getClassLoader());
        contours = new ArrayList<>();
        parcel.readList(contours, float[].class.getClassLoader());
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedList(bitmaps);
        parcel.writeList(rects);
        parcel.writeList(contours);
    }

    /** 第一张位图就是主体蒙版（小米自己的用法：取 getBitmaps().get(0)）。 */
    public Bitmap first() { return bitmaps == null || bitmaps.isEmpty() ? null : bitmaps.get(0); }
}

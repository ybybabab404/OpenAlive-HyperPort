package org.aliveclean.aicr;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 手写的 Parcel 代理：字段顺序按反编译得到的 {@code com.xiaomi.aicr.vision.VisionAttribute} 排列。
 * 改字段顺序或类型会与 ROM 侧解析错位，跨进程直接崩。
 */
public final class VisionAttribute implements Parcelable {
    public static final Creator<VisionAttribute> CREATOR = new Creator<VisionAttribute>() {
        @Override public VisionAttribute createFromParcel(Parcel parcel) { return new VisionAttribute(parcel); }
        @Override public VisionAttribute[] newArray(int size) { return new VisionAttribute[size]; }
    };

    public int visionType;
    public boolean privacy;
    public Bundle params;
    public boolean extractCombinedSubject;
    public boolean maxAreaAsSingleSubject;

    /** 与 MIUI 锁屏编辑器一致：VisionAttribute(false, true)。 */
    public VisionAttribute(boolean privacy, boolean maxAreaAsSingleSubject) {
        this(-1, privacy, false, maxAreaAsSingleSubject);
    }

    public VisionAttribute(int visionType, boolean privacy, boolean extractCombinedSubject, boolean maxAreaAsSingleSubject) {
        this.visionType = visionType;
        this.privacy = privacy;
        this.params = new Bundle();
        this.extractCombinedSubject = extractCombinedSubject;
        this.maxAreaAsSingleSubject = maxAreaAsSingleSubject;
    }

    private VisionAttribute(Parcel parcel) {
        visionType = parcel.readInt();
        privacy = parcel.readBoolean();
        params = parcel.readBundle(VisionAttribute.class.getClassLoader());
        extractCombinedSubject = parcel.readBoolean();
        maxAreaAsSingleSubject = parcel.readBoolean();
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(visionType);
        parcel.writeBoolean(privacy);
        parcel.writeBundle(params);
        parcel.writeBoolean(extractCombinedSubject);
        parcel.writeBoolean(maxAreaAsSingleSubject);
    }
}

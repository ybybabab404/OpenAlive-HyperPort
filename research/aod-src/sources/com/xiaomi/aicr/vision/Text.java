package com.xiaomi.aicr.vision;

import android.os.Parcel;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
public class Text extends VisionObject {
    public String info;

    public Text(Parcel parcel, VisionAttribute visionAttribute) {
        super(parcel, visionAttribute);
        this.mAttr = visionAttribute;
        this.info = parcel.readString();
    }

    public String toString() {
        String str = "info: " + this.info;
        Iterator it = this.mBoundingBox.values().iterator();
        while (it.hasNext()) {
            str = str + "box: " + Arrays.toString((float[]) it.next());
        }
        return str;
    }

    @Override // com.xiaomi.aicr.vision.VisionObject, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.info);
    }
}

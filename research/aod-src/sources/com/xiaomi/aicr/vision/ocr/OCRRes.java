package com.xiaomi.aicr.vision.ocr;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class OCRRes {

    public enum FocusFlags {
        FOCUS_TYPE_AUTO,
        FOCUS_TYPE_FORCE
    }

    public static class FocusPoint {
    }

    public static class Location implements Parcelable {
        public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.Location.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Location createFromParcel(Parcel parcel) {
                return new Location(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Location[] newArray(int i) {
                return new Location[i];
            }
        };
        public float[] box;
        public float[] poly;

        public Location(Parcel parcel) {
            this.poly = parcel.createFloatArray();
            this.box = parcel.createFloatArray();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloatArray(this.poly);
            parcel.writeFloatArray(this.box);
        }
    }

    public static class OCRChar implements Parcelable {
        public static final Parcelable.Creator<OCRChar> CREATOR = new Parcelable.Creator<OCRChar>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRChar.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRChar createFromParcel(Parcel parcel) {
                return new OCRChar(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRChar[] newArray(int i) {
                return new OCRChar[i];
            }
        };
        public float char_confidence;
        public String char_text;
        public Location location;

        public OCRChar(Parcel parcel) {
            this.location = (Location) parcel.readParcelable(Location.class.getClassLoader());
            this.char_text = parcel.readString();
            this.char_confidence = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.location, i);
            parcel.writeString(this.char_text);
            parcel.writeFloat(this.char_confidence);
        }
    }

    public static class OCREntity {
    }

    public static class OCRLine implements Parcelable {
        public static final Parcelable.Creator<OCRLine> CREATOR = new Parcelable.Creator<OCRLine>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRLine.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRLine createFromParcel(Parcel parcel) {
                return new OCRLine(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRLine[] newArray(int i) {
                return new OCRLine[i];
            }
        };
        public OCRChar[] Character;
        public float line_confidence;
        public int line_id;
        public String line_text;
        public Location location;

        public OCRLine(Parcel parcel) {
            this.location = (Location) parcel.readParcelable(Location.class.getClassLoader());
            this.line_text = parcel.readString();
            this.line_id = parcel.readInt();
            this.Character = (OCRChar[]) parcel.createTypedArray(OCRChar.CREATOR);
            this.line_confidence = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.location, i);
            parcel.writeString(this.line_text);
            parcel.writeInt(this.line_id);
            parcel.writeTypedArray(this.Character, i);
            parcel.writeFloat(this.line_confidence);
        }
    }

    public static class OCRParagraph implements Parcelable {
        public static final Parcelable.Creator<OCRParagraph> CREATOR = new Parcelable.Creator<OCRParagraph>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRParagraph.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRParagraph createFromParcel(Parcel parcel) {
                return new OCRParagraph(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRParagraph[] newArray(int i) {
                return new OCRParagraph[i];
            }
        };
        public OCRLine[] lines;
        public String paragraph_text;

        public OCRParagraph(Parcel parcel) {
            this.lines = (OCRLine[]) parcel.createTypedArray(OCRLine.CREATOR);
            this.paragraph_text = parcel.readString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedArray(this.lines, i);
            parcel.writeString(this.paragraph_text);
        }
    }

    public static class OCRResult implements Parcelable {
        public static final Parcelable.Creator<OCRResult> CREATOR = new Parcelable.Creator<OCRResult>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRResult.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRResult createFromParcel(Parcel parcel) {
                return new OCRResult(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRResult[] newArray(int i) {
                return new OCRResult[i];
            }
        };
        public OCRParagraph[] paragraphs;
        public String total_text;

        public OCRResult(Parcel parcel) {
            this.paragraphs = (OCRParagraph[]) parcel.createTypedArray(OCRParagraph.CREATOR);
            this.total_text = parcel.readString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedArray(this.paragraphs, i);
            parcel.writeString(this.total_text);
        }
    }

    public static class RegionData {
    }

    public static class RegionDetectResult {
    }

    public enum RotateFlags {
        ROTATE_0,
        ROTATE_90,
        ROTATE_180,
        ROTATE_270
    }

    public static class YUVImage {
    }
}

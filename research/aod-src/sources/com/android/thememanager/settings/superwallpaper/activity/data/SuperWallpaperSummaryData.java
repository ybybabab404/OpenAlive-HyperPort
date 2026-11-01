package com.android.thememanager.settings.superwallpaper.activity.data;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class SuperWallpaperSummaryData implements Parcelable {
    public static final Parcelable.Creator<SuperWallpaperSummaryData> CREATOR = new Parcelable.Creator<SuperWallpaperSummaryData>() { // from class: com.android.thememanager.settings.superwallpaper.activity.data.SuperWallpaperSummaryData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuperWallpaperSummaryData createFromParcel(Parcel parcel) {
            return new SuperWallpaperSummaryData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuperWallpaperSummaryData[] newArray(int i) {
            return new SuperWallpaperSummaryData[i];
        }
    };
    public float aodClockPositionX;
    public float aodClockPositionY;
    public float aodDualClockPositionXAnchorRight;
    public float aodDualClockPositionY;
    public Icon banner;
    public String completePackageName;
    public int deskPositionCount;
    public boolean hasCompleteInstall;
    public String id;
    public boolean isCanPreview;
    public SuperWallpaperLandData landData;
    public String packageName;
    public String summaries;
    public String title;

    public static class SuperWallpaperLandData implements Parcelable {
        public static final Parcelable.Creator<SuperWallpaperLandData> CREATOR = new Parcelable.Creator<SuperWallpaperLandData>() { // from class: com.android.thememanager.settings.superwallpaper.activity.data.SuperWallpaperSummaryData.SuperWallpaperLandData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SuperWallpaperLandData createFromParcel(Parcel parcel) {
                return new SuperWallpaperLandData(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SuperWallpaperLandData[] newArray(int i) {
                return new SuperWallpaperLandData[i];
            }
        };
        public Icon aodSmallPreview;
        public Icon aodSmallPreviewDark;
        public Icon desktopSmallPreview;
        public Icon desktopSmallPreviewDark;
        public Icon[] landDarkBanners;
        public Icon[] landLightBanners;
        public LandPositionData[] landPositionData;
        public Icon lockscreenSmallPreview;
        public Icon lockscreenSmallPreviewDark;

        public static class LandPositionData implements Parcelable {
            public static final Parcelable.Creator<LandPositionData> CREATOR = new Parcelable.Creator<LandPositionData>() { // from class: com.android.thememanager.settings.superwallpaper.activity.data.SuperWallpaperSummaryData.SuperWallpaperLandData.LandPositionData.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LandPositionData createFromParcel(Parcel parcel) {
                    return new LandPositionData(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LandPositionData[] newArray(int i) {
                    return new LandPositionData[i];
                }
            };
            public String coordinate_latitude;
            public String coordinate_longitude;
            public String position_content;
            public String position_title;
            public String view_height;

            public LandPositionData(Parcel parcel) {
                this.view_height = parcel.readString();
                this.coordinate_longitude = parcel.readString();
                this.coordinate_latitude = parcel.readString();
                this.position_title = parcel.readString();
                this.position_content = parcel.readString();
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.view_height);
                parcel.writeString(this.coordinate_longitude);
                parcel.writeString(this.coordinate_latitude);
                parcel.writeString(this.position_title);
                parcel.writeString(this.position_content);
            }
        }

        public SuperWallpaperLandData(Parcel parcel) {
            Parcelable.Creator creator = Icon.CREATOR;
            this.landLightBanners = (Icon[]) parcel.createTypedArray(creator);
            this.landDarkBanners = (Icon[]) parcel.createTypedArray(creator);
            this.landPositionData = (LandPositionData[]) parcel.createTypedArray(LandPositionData.CREATOR);
            this.aodSmallPreview = (Icon) parcel.readParcelable(Icon.class.getClassLoader());
            this.aodSmallPreviewDark = (Icon) parcel.readParcelable(Icon.class.getClassLoader());
            this.lockscreenSmallPreview = (Icon) parcel.readParcelable(Icon.class.getClassLoader());
            this.lockscreenSmallPreviewDark = (Icon) parcel.readParcelable(Icon.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedArray(this.landLightBanners, i);
            parcel.writeTypedArray(this.landDarkBanners, i);
            parcel.writeTypedArray(this.landPositionData, i);
            parcel.writeParcelable(this.aodSmallPreview, i);
            parcel.writeParcelable(this.aodSmallPreviewDark, i);
            parcel.writeParcelable(this.lockscreenSmallPreview, i);
            parcel.writeParcelable(this.lockscreenSmallPreviewDark, i);
        }
    }

    public SuperWallpaperSummaryData(Parcel parcel) {
        this.deskPositionCount = parcel.readInt();
        this.aodClockPositionX = parcel.readFloat();
        this.aodClockPositionY = parcel.readFloat();
        this.aodDualClockPositionXAnchorRight = parcel.readFloat();
        this.aodDualClockPositionY = parcel.readFloat();
        this.id = parcel.readString();
        this.title = parcel.readString();
        this.summaries = parcel.readString();
        this.packageName = parcel.readString();
        this.completePackageName = parcel.readString();
        this.banner = (Icon) parcel.readParcelable(Icon.class.getClassLoader());
        this.hasCompleteInstall = parcel.readByte() != 0;
        this.isCanPreview = parcel.readByte() != 0;
        this.landData = (SuperWallpaperLandData) parcel.readParcelable(SuperWallpaperLandData.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.deskPositionCount);
        parcel.writeFloat(this.aodClockPositionX);
        parcel.writeFloat(this.aodClockPositionY);
        parcel.writeFloat(this.aodDualClockPositionXAnchorRight);
        parcel.writeFloat(this.aodDualClockPositionY);
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.summaries);
        parcel.writeString(this.packageName);
        parcel.writeString(this.completePackageName);
        parcel.writeParcelable(this.banner, i);
        parcel.writeByte(this.hasCompleteInstall ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCanPreview ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.landData, i);
    }
}

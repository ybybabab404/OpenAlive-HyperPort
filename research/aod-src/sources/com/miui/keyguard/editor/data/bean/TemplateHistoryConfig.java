package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: TemplateHistoryConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Entity
@Metadata
public final class TemplateHistoryConfig implements Parcelable {

    @NotNull
    public static final CREATOR CREATOR = new CREATOR(null);

    @Ignore
    @Nullable
    private CommonConfig accessConfig;

    @NotNull
    private final String configUri;
    private final int hideFlag;

    @Ignore
    @Nullable
    private Bitmap homeScreenshot;

    @PrimaryKey
    private long id;
    private final boolean isAiGenerated;
    private final int isCurrent;
    private final int isDarkWallpaper;
    private final boolean isDualClock;

    @Ignore
    private boolean isLoadCompleted;
    private final boolean isThirdPartyWallpaper;

    @Nullable
    private final String largeLandWallpaperScreenshotUri;

    @Nullable
    private final String largeLandscapeScreenshotUri;

    @Nullable
    private final String largePortWallpaperScreenshotUri;

    @Nullable
    private final String largePortraitScreenshotUri;
    private final long lastModifiedTime;
    private final int loadingColor;

    @Ignore
    @Nullable
    private Drawable loadingDrawable;
    private final int magicType;

    @Nullable
    private String originResourcePath;

    @NotNull
    private final String resourceType;

    @Ignore
    @Nullable
    private Bitmap screenshotImage;

    @Nullable
    private final String smallScreenshotUri;

    @Nullable
    private final String smallWallpaperScreenshotUri;

    @NotNull
    private final String templateId;

    @Nullable
    private final String templateName;

    /* JADX INFO: compiled from: TemplateHistoryConfig.kt */
    @Metadata
    public static final class CREATOR implements Parcelable.Creator<TemplateHistoryConfig> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public TemplateHistoryConfig createFromParcel(@NotNull Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new TemplateHistoryConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public TemplateHistoryConfig[] newArray(int i) {
            return new TemplateHistoryConfig[i];
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TemplateHistoryConfig(@NotNull Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        String string = parcel.readString();
        Intrinsics.checkNotNull(string);
        String string2 = parcel.readString();
        Intrinsics.checkNotNull(string2);
        int i = parcel.readInt();
        String string3 = parcel.readString();
        String string4 = parcel.readString();
        String string5 = parcel.readString();
        String string6 = parcel.readString();
        String string7 = parcel.readString();
        String string8 = parcel.readString();
        boolean z = parcel.readByte() != 0;
        boolean z2 = parcel.readByte() != 0;
        String string9 = parcel.readString();
        Intrinsics.checkNotNull(string9);
        this(string, string2, i, string3, string4, string5, string6, string7, string8, z, z2, string9, parcel.readString(), parcel.readLong(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt() == 1);
        this.id = parcel.readLong();
    }

    public TemplateHistoryConfig(@NotNull String templateId, @NotNull String configUri, int i, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, boolean z, boolean z2, @NotNull String resourceType, @Nullable String str7, long j, @Nullable String str8, int i2, int i3, int i4, int i5, boolean z3) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(configUri, "configUri");
        Intrinsics.checkNotNullParameter(resourceType, "resourceType");
        this.templateId = templateId;
        this.configUri = configUri;
        this.loadingColor = i;
        this.smallScreenshotUri = str;
        this.largePortraitScreenshotUri = str2;
        this.largeLandscapeScreenshotUri = str3;
        this.smallWallpaperScreenshotUri = str4;
        this.largePortWallpaperScreenshotUri = str5;
        this.largeLandWallpaperScreenshotUri = str6;
        this.isDualClock = z;
        this.isThirdPartyWallpaper = z2;
        this.resourceType = resourceType;
        this.originResourcePath = str7;
        this.lastModifiedTime = j;
        this.templateName = str8;
        this.isCurrent = i2;
        this.hideFlag = i3;
        this.magicType = i4;
        this.isDarkWallpaper = i5;
        this.isAiGenerated = z3;
    }

    public static /* synthetic */ TemplateHistoryConfig copy$default(TemplateHistoryConfig templateHistoryConfig, String str, String str2, int i, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, boolean z2, String str9, String str10, long j, String str11, int i2, int i3, int i4, int i5, boolean z3, int i6, Object obj) {
        boolean z4;
        int i7;
        String str12 = (i6 & 1) != 0 ? templateHistoryConfig.templateId : str;
        String str13 = (i6 & 2) != 0 ? templateHistoryConfig.configUri : str2;
        int i8 = (i6 & 4) != 0 ? templateHistoryConfig.loadingColor : i;
        String str14 = (i6 & 8) != 0 ? templateHistoryConfig.smallScreenshotUri : str3;
        String str15 = (i6 & 16) != 0 ? templateHistoryConfig.largePortraitScreenshotUri : str4;
        String str16 = (i6 & 32) != 0 ? templateHistoryConfig.largeLandscapeScreenshotUri : str5;
        String str17 = (i6 & 64) != 0 ? templateHistoryConfig.smallWallpaperScreenshotUri : str6;
        String str18 = (i6 & 128) != 0 ? templateHistoryConfig.largePortWallpaperScreenshotUri : str7;
        String str19 = (i6 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? templateHistoryConfig.largeLandWallpaperScreenshotUri : str8;
        boolean z5 = (i6 & 512) != 0 ? templateHistoryConfig.isDualClock : z;
        boolean z6 = (i6 & 1024) != 0 ? templateHistoryConfig.isThirdPartyWallpaper : z2;
        String str20 = (i6 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? templateHistoryConfig.resourceType : str9;
        String str21 = (i6 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? templateHistoryConfig.originResourcePath : str10;
        String str22 = str12;
        String str23 = str13;
        long j2 = (i6 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? templateHistoryConfig.lastModifiedTime : j;
        String str24 = (i6 & 16384) != 0 ? templateHistoryConfig.templateName : str11;
        int i9 = (i6 & 32768) != 0 ? templateHistoryConfig.isCurrent : i2;
        int i10 = (i6 & 65536) != 0 ? templateHistoryConfig.hideFlag : i3;
        int i11 = (i6 & 131072) != 0 ? templateHistoryConfig.magicType : i4;
        int i12 = (i6 & 262144) != 0 ? templateHistoryConfig.isDarkWallpaper : i5;
        if ((i6 & 524288) != 0) {
            i7 = i12;
            z4 = templateHistoryConfig.isAiGenerated;
        } else {
            z4 = z3;
            i7 = i12;
        }
        return templateHistoryConfig.copy(str22, str23, i8, str14, str15, str16, str17, str18, str19, z5, z6, str20, str21, j2, str24, i9, i10, i11, i7, z4);
    }

    @NotNull
    public final String component1() {
        return this.templateId;
    }

    public final boolean component10() {
        return this.isDualClock;
    }

    public final boolean component11() {
        return this.isThirdPartyWallpaper;
    }

    @NotNull
    public final String component12() {
        return this.resourceType;
    }

    @Nullable
    public final String component13() {
        return this.originResourcePath;
    }

    public final long component14() {
        return this.lastModifiedTime;
    }

    @Nullable
    public final String component15() {
        return this.templateName;
    }

    public final int component16() {
        return this.isCurrent;
    }

    public final int component17() {
        return this.hideFlag;
    }

    public final int component18() {
        return this.magicType;
    }

    public final int component19() {
        return this.isDarkWallpaper;
    }

    @NotNull
    public final String component2() {
        return this.configUri;
    }

    public final boolean component20() {
        return this.isAiGenerated;
    }

    public final int component3() {
        return this.loadingColor;
    }

    @Nullable
    public final String component4() {
        return this.smallScreenshotUri;
    }

    @Nullable
    public final String component5() {
        return this.largePortraitScreenshotUri;
    }

    @Nullable
    public final String component6() {
        return this.largeLandscapeScreenshotUri;
    }

    @Nullable
    public final String component7() {
        return this.smallWallpaperScreenshotUri;
    }

    @Nullable
    public final String component8() {
        return this.largePortWallpaperScreenshotUri;
    }

    @Nullable
    public final String component9() {
        return this.largeLandWallpaperScreenshotUri;
    }

    @NotNull
    public final TemplateHistoryConfig copy(@NotNull String templateId, @NotNull String configUri, int i, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, boolean z, boolean z2, @NotNull String resourceType, @Nullable String str7, long j, @Nullable String str8, int i2, int i3, int i4, int i5, boolean z3) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(configUri, "configUri");
        Intrinsics.checkNotNullParameter(resourceType, "resourceType");
        return new TemplateHistoryConfig(templateId, configUri, i, str, str2, str3, str4, str5, str6, z, z2, resourceType, str7, j, str8, i2, i3, i4, i5, z3);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TemplateHistoryConfig)) {
            return false;
        }
        TemplateHistoryConfig templateHistoryConfig = (TemplateHistoryConfig) obj;
        return Intrinsics.areEqual(this.templateId, templateHistoryConfig.templateId) && Intrinsics.areEqual(this.configUri, templateHistoryConfig.configUri) && this.loadingColor == templateHistoryConfig.loadingColor && Intrinsics.areEqual(this.smallScreenshotUri, templateHistoryConfig.smallScreenshotUri) && Intrinsics.areEqual(this.largePortraitScreenshotUri, templateHistoryConfig.largePortraitScreenshotUri) && Intrinsics.areEqual(this.largeLandscapeScreenshotUri, templateHistoryConfig.largeLandscapeScreenshotUri) && Intrinsics.areEqual(this.smallWallpaperScreenshotUri, templateHistoryConfig.smallWallpaperScreenshotUri) && Intrinsics.areEqual(this.largePortWallpaperScreenshotUri, templateHistoryConfig.largePortWallpaperScreenshotUri) && Intrinsics.areEqual(this.largeLandWallpaperScreenshotUri, templateHistoryConfig.largeLandWallpaperScreenshotUri) && this.isDualClock == templateHistoryConfig.isDualClock && this.isThirdPartyWallpaper == templateHistoryConfig.isThirdPartyWallpaper && Intrinsics.areEqual(this.resourceType, templateHistoryConfig.resourceType) && Intrinsics.areEqual(this.originResourcePath, templateHistoryConfig.originResourcePath) && this.lastModifiedTime == templateHistoryConfig.lastModifiedTime && Intrinsics.areEqual(this.templateName, templateHistoryConfig.templateName) && this.isCurrent == templateHistoryConfig.isCurrent && this.hideFlag == templateHistoryConfig.hideFlag && this.magicType == templateHistoryConfig.magicType && this.isDarkWallpaper == templateHistoryConfig.isDarkWallpaper && this.isAiGenerated == templateHistoryConfig.isAiGenerated;
    }

    @Nullable
    public final CommonConfig getAccessConfig() {
        return this.accessConfig;
    }

    @NotNull
    public final String getConfigUri() {
        return this.configUri;
    }

    @Nullable
    public final String getCurrentScreenshotUri(int i) {
        if (i == 1) {
            return this.smallScreenshotUri;
        }
        if (i == 2) {
            return this.largePortraitScreenshotUri;
        }
        if (i == 3) {
            return this.largeLandscapeScreenshotUri;
        }
        Log.w("TemplateHistoryConfig", "getCurrentScreenshotUri: error screenStatus=" + i);
        return null;
    }

    @Nullable
    public final String getCurrentWallpaperScreenshotUri(int i) {
        if (i == 1) {
            return this.smallWallpaperScreenshotUri;
        }
        if (i == 2) {
            return this.largePortWallpaperScreenshotUri;
        }
        if (i == 3) {
            return this.largeLandWallpaperScreenshotUri;
        }
        Log.w("TemplateHistoryConfig", "getCurrentWallpaperScreenshotUri: error screenStatus=" + i);
        return null;
    }

    public final int getHideFlag() {
        return this.hideFlag;
    }

    @Nullable
    public final Bitmap getHomeScreenshot() {
        return this.homeScreenshot;
    }

    public final long getId() {
        return this.id;
    }

    @Nullable
    public final String getLargeLandWallpaperScreenshotUri() {
        return this.largeLandWallpaperScreenshotUri;
    }

    @Nullable
    public final String getLargeLandscapeScreenshotUri() {
        return this.largeLandscapeScreenshotUri;
    }

    @Nullable
    public final String getLargePortWallpaperScreenshotUri() {
        return this.largePortWallpaperScreenshotUri;
    }

    @Nullable
    public final String getLargePortraitScreenshotUri() {
        return this.largePortraitScreenshotUri;
    }

    public final long getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    public final int getLoadingColor() {
        return this.loadingColor;
    }

    @Nullable
    public final Drawable getLoadingDrawable() {
        return this.loadingDrawable;
    }

    public final int getMagicType() {
        return this.magicType;
    }

    @Nullable
    public final String getOriginResourcePath() {
        return this.originResourcePath;
    }

    @NotNull
    public final String getResourceType() {
        return this.resourceType;
    }

    @Nullable
    public final Bitmap getScreenshotImage() {
        return this.screenshotImage;
    }

    @Nullable
    public final String getSmallScreenshotUri() {
        return this.smallScreenshotUri;
    }

    @Nullable
    public final String getSmallWallpaperScreenshotUri() {
        return this.smallWallpaperScreenshotUri;
    }

    @NotNull
    public final String getTemplateId() {
        return this.templateId;
    }

    @Nullable
    public final String getTemplateName() {
        return this.templateName;
    }

    public int hashCode() {
        int iHashCode = ((((this.templateId.hashCode() * 31) + this.configUri.hashCode()) * 31) + Integer.hashCode(this.loadingColor)) * 31;
        String str = this.smallScreenshotUri;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.largePortraitScreenshotUri;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.largeLandscapeScreenshotUri;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.smallWallpaperScreenshotUri;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.largePortWallpaperScreenshotUri;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.largeLandWallpaperScreenshotUri;
        int iHashCode7 = (((((((iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31) + Boolean.hashCode(this.isDualClock)) * 31) + Boolean.hashCode(this.isThirdPartyWallpaper)) * 31) + this.resourceType.hashCode()) * 31;
        String str7 = this.originResourcePath;
        int iHashCode8 = (((iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31) + Long.hashCode(this.lastModifiedTime)) * 31;
        String str8 = this.templateName;
        return ((((((((((iHashCode8 + (str8 != null ? str8.hashCode() : 0)) * 31) + Integer.hashCode(this.isCurrent)) * 31) + Integer.hashCode(this.hideFlag)) * 31) + Integer.hashCode(this.magicType)) * 31) + Integer.hashCode(this.isDarkWallpaper)) * 31) + Boolean.hashCode(this.isAiGenerated);
    }

    public final boolean isAiGenerated() {
        return this.isAiGenerated;
    }

    public final int isCurrent() {
        return this.isCurrent;
    }

    public final int isDarkWallpaper() {
        return this.isDarkWallpaper;
    }

    public final boolean isDualClock() {
        return this.isDualClock;
    }

    public final boolean isLoadCompleted() {
        return this.isLoadCompleted;
    }

    public final boolean isThirdPartyWallpaper() {
        return this.isThirdPartyWallpaper;
    }

    public final void setAccessConfig(@Nullable CommonConfig commonConfig) {
        this.accessConfig = commonConfig;
    }

    public final void setHomeScreenshot(@Nullable Bitmap bitmap) {
        this.homeScreenshot = bitmap;
    }

    public final void setId(long j) {
        this.id = j;
    }

    public final void setLoadCompleted(boolean z) {
        this.isLoadCompleted = z;
    }

    public final void setLoadingDrawable(@Nullable Drawable drawable) {
        this.loadingDrawable = drawable;
    }

    public final void setOriginResourcePath(@Nullable String str) {
        this.originResourcePath = str;
    }

    public final void setScreenshotImage(@Nullable Bitmap bitmap) {
        this.screenshotImage = bitmap;
    }

    @NotNull
    public String toString() {
        return "TemplateHistoryConfig(templateId=" + this.templateId + ", configUri=" + this.configUri + ", loadingColor=" + this.loadingColor + ", smallScreenshotUri=" + this.smallScreenshotUri + ", largePortraitScreenshotUri=" + this.largePortraitScreenshotUri + ", largeLandscapeScreenshotUri=" + this.largeLandscapeScreenshotUri + ", smallWallpaperScreenshotUri=" + this.smallWallpaperScreenshotUri + ", largePortWallpaperScreenshotUri=" + this.largePortWallpaperScreenshotUri + ", largeLandWallpaperScreenshotUri=" + this.largeLandWallpaperScreenshotUri + ", isDualClock=" + this.isDualClock + ", isThirdPartyWallpaper=" + this.isThirdPartyWallpaper + ", resourceType=" + this.resourceType + ", originResourcePath=" + this.originResourcePath + ", lastModifiedTime=" + this.lastModifiedTime + ", templateName=" + this.templateName + ", isCurrent=" + this.isCurrent + ", hideFlag=" + this.hideFlag + ", magicType=" + this.magicType + ", isDarkWallpaper=" + this.isDarkWallpaper + ", isAiGenerated=" + this.isAiGenerated + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.templateId);
        parcel.writeString(this.configUri);
        parcel.writeInt(this.loadingColor);
        parcel.writeString(this.smallScreenshotUri);
        parcel.writeString(this.largePortraitScreenshotUri);
        parcel.writeString(this.largeLandscapeScreenshotUri);
        parcel.writeString(this.smallWallpaperScreenshotUri);
        parcel.writeString(this.largePortWallpaperScreenshotUri);
        parcel.writeString(this.largeLandWallpaperScreenshotUri);
        parcel.writeByte(this.isDualClock ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isThirdPartyWallpaper ? (byte) 1 : (byte) 0);
        parcel.writeString(this.resourceType);
        parcel.writeString(this.originResourcePath);
        parcel.writeLong(this.lastModifiedTime);
        parcel.writeString(this.templateName);
        parcel.writeInt(this.isCurrent);
        parcel.writeInt(this.hideFlag);
        parcel.writeInt(this.magicType);
        parcel.writeInt(this.isDarkWallpaper);
        parcel.writeInt(this.isAiGenerated ? 1 : 0);
        parcel.writeLong(this.id);
    }
}

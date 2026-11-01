package com.miui.aod.category;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.collection.ArrayMap;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.components.ApplyInstanceStyleSelectPresenter;
import com.miui.aod.components.view.SuperWallpaperStyleSelectView;
import com.miui.aod.util.WallPaperUtils;
import com.miui.aod.widget.BlankSuperWallpaperClock;
import com.miui.aod.widget.IAodClock;
import java.util.Map;
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
    */
/* JADX INFO: compiled from: BlankSuperWallpaperCategoryInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class BlankSuperWallpaperCategoryInfo extends CategoryInfo {

    @NotNull
    private final String EXTRA_IS_BATTERY_ON;

    @NotNull
    private final String EXTRA_IS_LUNAR_ON;

    @NotNull
    private final String EXTRA_IS_NOTIFICATION_ON;

    @NotNull
    private final String RECEIVER_AOD_CONTENT_SWITCH;

    @NotNull
    private final String TAG;

    /* JADX WARN: Multi-variable type inference failed */
    public BlankSuperWallpaperCategoryInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlankSuperWallpaperCategoryInfo(@NotNull String name) {
        super(name);
        Intrinsics.checkNotNullParameter(name, "name");
        this.TAG = "BlankSuperWallpaperCategoryInfo";
        this.RECEIVER_AOD_CONTENT_SWITCH = "miui.aod.action.AOD_CONTENT_SWITCH";
        this.EXTRA_IS_BATTERY_ON = "aod_battery_on";
        this.EXTRA_IS_NOTIFICATION_ON = "aod_notification_on";
        this.EXTRA_IS_LUNAR_ON = "aod_lunar_on";
    }

    public /* synthetic */ BlankSuperWallpaperCategoryInfo(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER : str);
    }

    private final void sendSwitchChangeBroadcast(String str, boolean z) {
        Intent intent = new Intent(this.RECEIVER_AOD_CONTENT_SWITCH);
        intent.putExtra(str, z);
        AODApplication.sInstance.sendBroadcast(intent);
    }

    @Override // com.miui.aod.common.StyleInfo
    @NotNull
    public IAodClock createAodClock(boolean z, int i) {
        return new BlankSuperWallpaperClock();
    }

    @Override // com.miui.aod.common.StyleInfo
    @Nullable
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER);
        arrayMap.put("style_event", CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER);
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    @NotNull
    public String getStyleSelectPresenterName(int i) {
        String name = ApplyInstanceStyleSelectPresenter.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return name;
    }

    @Override // com.miui.aod.common.StyleInfo
    @NotNull
    public String getStyleSelectViewName() {
        String name = SuperWallpaperStyleSelectView.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return name;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateBattery(@Nullable Context context) {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needSendNotificationCommand() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needSendUpdateTimeCommand() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(@NotNull View aodView, int i) {
        ImageView imageView;
        Intrinsics.checkNotNullParameter(aodView, "aodView");
        super.setBg(aodView, i);
        if (i != 2 || (imageView = (ImageView) aodView.findViewById(R.id.aod_bg)) == null) {
            return;
        }
        imageView.setVisibility(0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(WallPaperUtils.getBlankSuperWallpaperThumbnail());
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setLunarSwitchOn(boolean z) {
        super.setLunarSwitchOn(z);
        sendSwitchChangeBroadcast(this.EXTRA_IS_LUNAR_ON, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportSuperWallpaperMode() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void switchOnBatteryIcon(boolean z) {
        super.switchOnBatteryIcon(z);
        sendSwitchChangeBroadcast(this.EXTRA_IS_BATTERY_ON, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public void switchOnNotificationIcon(boolean z) {
        super.switchOnNotificationIcon(z);
        sendSwitchChangeBroadcast(this.EXTRA_IS_NOTIFICATION_ON, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean usingNormalPositionStrategy() {
        return true;
    }
}

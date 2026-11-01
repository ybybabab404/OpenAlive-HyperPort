package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
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
/* JADX INFO: compiled from: DynamicSwitchButton.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DynamicSwitchButton extends FrameLayout {
    public final String TAG;
    public DynamicButtonClickCallBack clickCallback;
    public final LinearLayout content;
    public DynamicSwitchCallback dynamicSwitchCallback;
    public final ImageView icon;
    public boolean isClicked;
    public long lastClickTime;
    public int status;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public DynamicSwitchButton(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DynamicSwitchButton(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "Keyguard-Editor-DynamicPresetButton";
        this.status = 2;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setClipChildren(false);
        linearLayout.setPadding(linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_padding_start), 0, linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_padding_end), 0);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_height)));
        linearLayout.setGravity(16);
        this.content = linearLayout;
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        imageView.setImageResource(R.drawable.kg_function_icon_depth_dynamic);
        imageView.setContentDescription(imageView.getResources().getString(R.string.kg_depth_preset_button_dynamic));
        this.icon = imageView;
        linearLayout.addView(imageView);
        addView(linearLayout);
        setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.DynamicSwitchButton$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DynamicSwitchButton._init_$lambda$2(this.f$0, view);
            }
        });
    }

    public /* synthetic */ DynamicSwitchButton(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void _init_$lambda$2(DynamicSwitchButton dynamicSwitchButton, View view) {
        if (System.currentTimeMillis() - dynamicSwitchButton.lastClickTime <= 300) {
            Log.w(dynamicSwitchButton.TAG, "the interval between clicks is too short");
        } else {
            dynamicSwitchButton.lastClickTime = System.currentTimeMillis();
            dynamicSwitchButton.onClick();
        }
    }

    public static /* synthetic */ void changeStatus$default(DynamicSwitchButton dynamicSwitchButton, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        dynamicSwitchButton.changeStatus(i, z, z2);
    }

    public final void changeStatus(int i, boolean z, boolean z2) {
        DynamicButtonClickCallBack dynamicButtonClickCallBack;
        DynamicButtonClickCallBack dynamicButtonClickCallBack2;
        WeakReference templateViewWeak;
        BaseTemplateView baseTemplateView;
        DynamicButtonClickCallBack dynamicButtonClickCallBack3 = this.clickCallback;
        WallpaperInfo wallpaperInfo = (dynamicButtonClickCallBack3 == null || (templateViewWeak = dynamicButtonClickCallBack3.getTemplateViewWeak()) == null || (baseTemplateView = (BaseTemplateView) templateViewWeak.get()) == null) ? null : baseTemplateView.getWallpaperInfo();
        if (i == 2 && wallpaperInfo != null && wallpaperInfo.getVideoType() == 3) {
            Log.w(this.TAG, "changeStatus: invalid live photo");
            return;
        }
        DynamicButtonClickCallBack dynamicButtonClickCallBack4 = this.clickCallback;
        if (dynamicButtonClickCallBack4 != null && !dynamicButtonClickCallBack4.allowToClickDynamicSwitchBtn() && z2) {
            Log.w(this.TAG, "changeStatus: not allow to click dynamicSwitchBtn");
            return;
        }
        this.status = i;
        if (i == 2) {
            this.icon.setImageResource(R.drawable.kg_function_icon_depth_dynamic);
            this.icon.setContentDescription(getResources().getString(R.string.kg_depth_preset_button_dynamic));
            if (!z || (dynamicButtonClickCallBack = this.clickCallback) == null) {
                return;
            }
            dynamicButtonClickCallBack.onClick(2);
            return;
        }
        if (i != 3) {
            return;
        }
        this.icon.setImageResource(R.drawable.kg_function_icon_depth_static);
        this.icon.setContentDescription(getResources().getString(R.string.kg_depth_preset_button_static));
        if (!z || (dynamicButtonClickCallBack2 = this.clickCallback) == null) {
            return;
        }
        dynamicButtonClickCallBack2.onClick(3);
    }

    public final boolean checkHomeLivePhotoVideo(HomeConfig homeConfig, boolean z) {
        if (homeConfig == null) {
            Log.e(this.TAG, "checkHomeLivePhotoVideo: homeConfig is null");
            return false;
        }
        WallpaperInfo wallpaperInfoForVideo = homeConfig.getWallpaperInfoForVideo();
        if (wallpaperInfoForVideo != null) {
            WallpaperInfo wallpaperInfo = homeConfig.getWallpaperInfo();
            wallpaperInfoForVideo.setCurrentWallpaper(wallpaperInfo != null ? wallpaperInfo.getCurrentWallpaper() : null);
        }
        if (z && homeConfig.getWallpaperInfoForVideo() != null) {
            Log.d(this.TAG, "checkHomeLivePhotoVideo: wallpaperInfoForVideo exist already");
            return true;
        }
        WallpaperInfo wallpaperInfo2 = homeConfig.getWallpaperInfo();
        String source = wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null;
        WallpaperInfo wallpaperInfo3 = homeConfig.getWallpaperInfo();
        String originResourcePath = wallpaperInfo3 != null ? wallpaperInfo3.getOriginResourcePath() : null;
        Wallpaper.Companion companion = Wallpaper.Companion;
        WallpaperInfo wallpaperInfo4 = homeConfig.getWallpaperInfo();
        if (!companion.isLivePhotoResource(wallpaperInfo4 != null ? Integer.valueOf(wallpaperInfo4.getVideoType()) : null) || !FileUtil.isFileExist(originResourcePath)) {
            return false;
        }
        createHomeLivePhotoWallpaperInfo(source, originResourcePath, homeConfig);
        return true;
    }

    public final boolean checkLockLivePhotoVideo(TemplateConfig templateConfig, boolean z) {
        if (templateConfig == null) {
            Log.e(this.TAG, "checkLockLivePhotoVideo: templateConfig is null");
            return false;
        }
        WallpaperInfo wallpaperInfoForVideo = templateConfig.getWallpaperInfoForVideo();
        if (wallpaperInfoForVideo != null) {
            WallpaperInfo wallpaperInfo = templateConfig.getWallpaperInfo();
            wallpaperInfoForVideo.setCurrentWallpaper(wallpaperInfo != null ? wallpaperInfo.getCurrentWallpaper() : null);
        }
        if (z && templateConfig.getWallpaperInfoForVideo() != null) {
            Log.d(this.TAG, "checkLockLivePhotoVideo: wallpaperInfoForVideo exist already");
            return true;
        }
        WallpaperInfo wallpaperInfo2 = templateConfig.getWallpaperInfo();
        String source = wallpaperInfo2 != null ? wallpaperInfo2.getSource() : null;
        WallpaperInfo wallpaperInfo3 = templateConfig.getWallpaperInfo();
        String originResourcePath = wallpaperInfo3 != null ? wallpaperInfo3.getOriginResourcePath() : null;
        Wallpaper.Companion companion = Wallpaper.Companion;
        WallpaperInfo wallpaperInfo4 = templateConfig.getWallpaperInfo();
        if (!companion.isLivePhotoResource(wallpaperInfo4 != null ? Integer.valueOf(wallpaperInfo4.getVideoType()) : null) || !FileUtil.isFileExist(originResourcePath)) {
            return false;
        }
        createLockLivePhotoWallpaperInfo(source, originResourcePath, templateConfig);
        return true;
    }

    public final void createHomeLivePhotoWallpaperInfo(String str, String str2, HomeConfig homeConfig) {
        if (str == null || str2 == null || homeConfig == null) {
            Log.e(this.TAG, "createHomeLivePhotoWallpaperInfo error: source=" + (str != null) + ", originResourcePath=" + (str2 != null) + " homeConfig=" + (homeConfig != null));
            return;
        }
        WallpaperInfo wallpaperInfo = homeConfig.getWallpaperInfo();
        WallpaperInfo wallpaperInfo2 = null;
        String str3 = Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "image") ? "video" : "image";
        Wallpaper.Companion companion = Wallpaper.Companion;
        WallpaperInfo wallpaperInfo3 = homeConfig.getWallpaperInfo();
        if (companion.isNormalLivePhotoResource(wallpaperInfo3 != null ? Integer.valueOf(wallpaperInfo3.getVideoType()) : null)) {
            WallpaperInfo wallpaperInfo4 = homeConfig.getWallpaperInfo();
            int videoType = wallpaperInfo4 != null ? wallpaperInfo4.getVideoType() : 0;
            WallpaperInfo wallpaperInfo5 = homeConfig.getWallpaperInfo();
            ExtraInfo extraInfo = wallpaperInfo5 != null ? wallpaperInfo5.getExtraInfo() : null;
            WallpaperInfo wallpaperInfo6 = homeConfig.getWallpaperInfo();
            WallpaperInfo wallpaperInfo7 = new WallpaperInfo(str, null, null, null, null, 0, null, null, str3, str2, false, null, false, null, false, null, false, false, null, null, null, null, null, 0, false, 1, videoType, extraInfo, false, wallpaperInfo6 != null ? wallpaperInfo6.isSupportFullScreenAod() : null, null, 1350083584, null);
            WallpaperInfo wallpaperInfo8 = homeConfig.getWallpaperInfo();
            wallpaperInfo7.setCurrentWallpaper(wallpaperInfo8 != null ? wallpaperInfo8.getCurrentWallpaper() : null);
            wallpaperInfo7.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
            wallpaperInfo2 = wallpaperInfo7;
        }
        homeConfig.setWallpaperInfoForVideo(wallpaperInfo2);
    }

    public final void createLockLivePhotoWallpaperInfo(String str, String str2, TemplateConfig templateConfig) {
        WallpaperInfo wallpaperInfo;
        if (str == null || str2 == null || templateConfig == null) {
            Log.e(this.TAG, "createLockLivePhotoWallpaperInfo error: source=" + (str != null) + ", originResourcePath=" + (str2 != null) + " templateConfig=" + (templateConfig != null));
            return;
        }
        WallpaperInfo wallpaperInfo2 = templateConfig.getWallpaperInfo();
        String str3 = Intrinsics.areEqual(wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null, "image") ? "video" : "image";
        WallpaperInfo wallpaperInfoForVideo = templateConfig.getWallpaperInfoForVideo();
        Bitmap subjectBitmap = wallpaperInfoForVideo != null ? wallpaperInfoForVideo.getSubjectBitmap() : null;
        Wallpaper.Companion companion = Wallpaper.Companion;
        WallpaperInfo wallpaperInfo3 = templateConfig.getWallpaperInfo();
        if (companion.isNormalLivePhotoResource(wallpaperInfo3 != null ? Integer.valueOf(wallpaperInfo3.getVideoType()) : null)) {
            WallpaperInfo wallpaperInfo4 = templateConfig.getWallpaperInfo();
            int videoType = wallpaperInfo4 != null ? wallpaperInfo4.getVideoType() : 0;
            WallpaperInfo wallpaperInfo5 = templateConfig.getWallpaperInfo();
            ExtraInfo extraInfo = wallpaperInfo5 != null ? wallpaperInfo5.getExtraInfo() : null;
            WallpaperInfo wallpaperInfo6 = templateConfig.getWallpaperInfo();
            wallpaperInfo = new WallpaperInfo(str, null, null, null, null, 0, null, null, str3, str2, false, null, false, null, false, null, false, false, null, null, null, null, null, 0, false, 1, videoType, extraInfo, false, wallpaperInfo6 != null ? wallpaperInfo6.isSupportFullScreenAod() : null, null, 1350083584, null);
            WallpaperInfo wallpaperInfo7 = templateConfig.getWallpaperInfo();
            wallpaperInfo.setCurrentWallpaper(wallpaperInfo7 != null ? wallpaperInfo7.getCurrentWallpaper() : null);
            wallpaperInfo.setInfoType(WallpaperInfoType.LOCK_WALLPAPER);
            wallpaperInfo.setSubjectBitmap(subjectBitmap);
        } else {
            wallpaperInfo = null;
        }
        templateConfig.setWallpaperInfoForVideo(wallpaperInfo);
        String str4 = this.TAG;
        WallpaperInfo wallpaperInfoForVideo2 = templateConfig.getWallpaperInfoForVideo();
        Log.i(str4, "wallpaperInfoForLivePhotoVideo: " + (wallpaperInfoForVideo2 != null ? wallpaperInfoForVideo2.toString() : null));
    }

    @Nullable
    public final DynamicButtonClickCallBack getClickCallback() {
        return this.clickCallback;
    }

    @NotNull
    public final LinearLayout getContent() {
        return this.content;
    }

    @Nullable
    public final DynamicSwitchCallback getDynamicSwitchCallback() {
        return this.dynamicSwitchCallback;
    }

    @NotNull
    public final ImageView getIcon() {
        return this.icon;
    }

    public final int getStatus() {
        return this.status;
    }

    public final boolean isClicked() {
        return this.isClicked;
    }

    public final void onAddToBar() {
        setDecorate();
    }

    public final void onClick() {
        Log.i(this.TAG, "onClick: " + this.status);
        updateDynamicStatus(this.status);
        this.isClicked = true;
    }

    public final void refreshLockConfig(TemplateConfig templateConfig, boolean z) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        if (checkLockLivePhotoVideo(templateConfig, z)) {
            return;
        }
        Log.e(this.TAG, "refreshConfig: check live photo error, config=" + templateConfig);
    }

    public final void setClickCallback(@Nullable DynamicButtonClickCallBack dynamicButtonClickCallBack) {
        this.clickCallback = dynamicButtonClickCallBack;
    }

    public final void setClicked(boolean z) {
        this.isClicked = z;
    }

    public final void setDecorate() {
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        float fProvideCustomButtonRadius = resourcesProvider.provideCustomButtonRadius(context);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        ViewUtil.decorateFrostedPured$default(viewUtil, this, fProvideCustomButtonRadius, false, resourcesProvider.provideCustomButtonBackgroundColor(context2), 2, null);
    }

    public final void setDynamicSwitchCallback(@Nullable DynamicSwitchCallback dynamicSwitchCallback) {
        this.dynamicSwitchCallback = dynamicSwitchCallback;
    }

    public final void setStatus(int i) {
        this.status = i;
    }

    public final void updateDynamicStatus(int i) {
        if (i == 2) {
            changeStatus$default(this, 3, false, true, 2, null);
        } else {
            if (i != 3) {
                return;
            }
            changeStatus$default(this, 2, false, true, 2, null);
        }
    }
}

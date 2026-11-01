package com.miui.keyguard.editor.edit.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.ClickUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.androidbasewidget.widget.ProgressBar;
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
/* JADX INFO: compiled from: DepthPresetDownloadButton.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DepthPresetDownloadButton extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    public final String TAG;
    public DownloadButtonClickCallBack clickCallback;
    public final ColorStateList color;
    public final LinearLayout content;
    public final DepthPresetDownloadButton$downloadCallback$1 downloadCallback;
    public final DepthPresetDownloadManager downloadManager;
    public final ImageView icon;
    public long lastClickTime;
    public boolean needCheck;
    public int progress;
    public final ProgressBar progressBar;
    public int status;
    public final TextView textView;
    public boolean videoFinished;

    /* JADX INFO: compiled from: DepthPresetDownloadButton.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public DepthPresetDownloadButton(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DepthPresetDownloadButton(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "Keyguard-Editor-DepthPresetDownloadButton";
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setClipChildren(false);
        linearLayout.setPadding(linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_padding_start), 0, linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_padding_end), 0);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_height)));
        linearLayout.setGravity(16);
        this.content = linearLayout;
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        imageView.setImageResource(R.drawable.kg_function_icon_cloud_download);
        this.icon = imageView;
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.setMarginStart(textView.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_text_margin_start));
        textView.setLayoutParams(layoutParams);
        textView.setMaxWidth(textView.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_text_max_width));
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        int i = R.color.kg_white_100;
        textView.setTextColor(context.getColor(i));
        textView.setTextSize(0, textView.getResources().getDimension(R.dimen.kg_function_depth_preset_text_size));
        textView.setText(context.getResources().getString(R.string.kg_depth_preset_button_download));
        this.textView = textView;
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(context.getColor(i));
        this.color = colorStateListValueOf;
        ProgressBar progressBar = new ProgressBar(context);
        Resources resources = progressBar.getResources();
        int i2 = R.dimen.kg_function_process_bar_width;
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(resources.getDimensionPixelSize(i2), progressBar.getResources().getDimensionPixelSize(i2)));
        progressBar.setIndeterminateTintList(colorStateListValueOf);
        progressBar.setVisibility(8);
        this.progressBar = progressBar;
        setId(R.id.kg_depth_download_button);
        linearLayout.addView(imageView);
        linearLayout.addView(progressBar);
        linearLayout.addView(textView);
        addView(linearLayout);
        setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.DepthPresetDownloadButton$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DepthPresetDownloadButton._init_$lambda$5(this.f$0, view);
            }
        });
        DepthPresetDownloadButton$downloadCallback$1 depthPresetDownloadButton$downloadCallback$1 = new DepthPresetDownloadButton$downloadCallback$1(this, context);
        this.downloadCallback = depthPresetDownloadButton$downloadCallback$1;
        this.downloadManager = new DepthPresetDownloadManager(new WeakReference(context), new WeakReference(depthPresetDownloadButton$downloadCallback$1));
    }

    public /* synthetic */ DepthPresetDownloadButton(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void _init_$lambda$5(DepthPresetDownloadButton depthPresetDownloadButton, View view) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (System.currentTimeMillis() - depthPresetDownloadButton.lastClickTime <= 300) {
            Log.w(depthPresetDownloadButton.TAG, "the interval between clicks is too short");
            return;
        }
        depthPresetDownloadButton.lastClickTime = System.currentTimeMillis();
        Wallpaper.Companion companion = Wallpaper.Companion;
        CommonConfig templateConfig = depthPresetDownloadButton.downloadManager.getTemplateConfig();
        if (companion.isLivePhotoResource((templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getVideoType()))) {
            depthPresetDownloadButton.onLivePhotoClick();
        } else {
            depthPresetDownloadButton.onClick();
        }
    }

    public static /* synthetic */ void changeStatus$default(DepthPresetDownloadButton depthPresetDownloadButton, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        depthPresetDownloadButton.changeStatus(i, z);
    }

    public final void bind() {
        changeStatus$default(this, 1, false, 2, null);
        if (this.downloadManager.bind()) {
            return;
        }
        Log.w(this.TAG, "downloadManager bind failed");
        changeStatus$default(this, 0, false, 2, null);
    }

    public final void changeProgressStatus(int i, int i2) {
        if (this.progress < i2) {
            this.progress = i2;
        }
        if (i == 1002) {
            this.icon.setVisibility(8);
            this.icon.setImageResource(R.drawable.kg_function_icon_cloud_download);
            this.progressBar.setVisibility(0);
            this.textView.setText(this.progress + "%");
        }
    }

    public final void changeStatus(int i, boolean z) {
        DownloadButtonClickCallBack downloadButtonClickCallBack;
        DownloadButtonClickCallBack downloadButtonClickCallBack2;
        this.status = i;
        if (i == 0) {
            this.icon.setVisibility(0);
            this.progressBar.setVisibility(8);
            this.textView.setText(getContext().getResources().getString(R.string.kg_depth_preset_button_download));
            return;
        }
        if (i != 1) {
            if (i != 2) {
                if (i == 3 && (downloadButtonClickCallBack2 = this.clickCallback) != null) {
                    downloadButtonClickCallBack2.onClick(3);
                    return;
                }
                return;
            }
            DownloadButtonClickCallBack downloadButtonClickCallBack3 = this.clickCallback;
            if (downloadButtonClickCallBack3 != null) {
                downloadButtonClickCallBack3.onClick(2);
                return;
            }
            return;
        }
        this.icon.setVisibility(8);
        this.icon.setImageResource(R.drawable.kg_function_icon_cloud_download);
        this.progressBar.setVisibility(0);
        this.textView.setText(getContext().getResources().getString(R.string.kg_depth_preset_button_downloading));
        DownloadButtonClickCallBack downloadButtonClickCallBack4 = this.clickCallback;
        if (downloadButtonClickCallBack4 != null) {
            downloadButtonClickCallBack4.onClick(1);
        }
        if (!z || (downloadButtonClickCallBack = this.clickCallback) == null) {
            return;
        }
        downloadButtonClickCallBack.onClick(1);
    }

    public final void checkThemeCTA() {
        if (this.downloadManager.checkThemeCTA()) {
            bind();
        } else {
            this.needCheck = true;
            this.downloadManager.jumpToThemeCta();
        }
    }

    public final void checkVideoFinish() {
        if (this.downloadManager.checkVideoFinish()) {
            Log.i(this.TAG, "checkVideoFinish true");
            this.videoFinished = true;
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
            if (commonUtils.activityVisible((Activity) context)) {
                Log.i(this.TAG, "checkVideoFinish activityVisible true");
                changeStatus$default(this, 2, false, 2, null);
            } else {
                Log.i(this.TAG, "checkVideoFinish activityVisible false");
                this.status = 3;
            }
        }
    }

    @Nullable
    public final DownloadButtonClickCallBack getClickCallback() {
        return this.clickCallback;
    }

    public final ColorStateList getColor() {
        return this.color;
    }

    @NotNull
    public final LinearLayout getContent() {
        return this.content;
    }

    @NotNull
    public final DepthPresetDownloadManager getDownloadManager() {
        return this.downloadManager;
    }

    @NotNull
    public final ImageView getIcon() {
        return this.icon;
    }

    public final boolean getNeedCheck() {
        return this.needCheck;
    }

    public final int getProgress() {
        return this.progress;
    }

    @NotNull
    public final ProgressBar getProgressBar() {
        return this.progressBar;
    }

    public final int getStatus() {
        return this.status;
    }

    @NotNull
    public final TextView getTextView() {
        return this.textView;
    }

    public final void onAddToBar(CommonConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        setDecorate();
        this.downloadManager.setTemplateConfig(config);
        checkVideoFinish();
    }

    public final void onClick() {
        Log.i(this.TAG, "onClick: " + this.status);
        updateDownloadStatus(this.status);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Log.d(this.TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
        this.downloadManager.unbind();
        this.clickCallback = null;
    }

    public final void onLivePhotoClick() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Wallpaper.Companion companion = Wallpaper.Companion;
        CommonConfig templateConfig = this.downloadManager.getTemplateConfig();
        if (companion.isNormalLivePhotoResource((templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getVideoType()))) {
            updateDownloadStatus(this.status);
        } else {
            ToastUtil.INSTANCE.showText(R.string.kg_live_photo_land_tips, 0);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null && motionEvent.getAction() == 0) {
            ClickUtil clickUtil = ClickUtil.INSTANCE;
            clickUtil.getEditButtonLock().compareAndSet(clickUtil.getBUTTON_FREE(), getId());
        } else if (motionEvent != null && motionEvent.getAction() == 1) {
            ClickUtil clickUtil2 = ClickUtil.INSTANCE;
            if (clickUtil2.getEditButtonLock().compareAndSet(getId(), clickUtil2.getBUTTON_FREE())) {
                performClick();
            }
        } else if (motionEvent != null && motionEvent.getAction() == 3) {
            ClickUtil clickUtil3 = ClickUtil.INSTANCE;
            clickUtil3.getEditButtonLock().compareAndSet(getId(), clickUtil3.getBUTTON_FREE());
        }
        if (ClickUtil.INSTANCE.getEditButtonLock().get() == getId()) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public final void refreshConfig(CommonConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.downloadManager.setTemplateConfig(config);
    }

    public final void setClickCallback(@Nullable DownloadButtonClickCallBack downloadButtonClickCallBack) {
        this.clickCallback = downloadButtonClickCallBack;
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

    public final void setNeedCheck(boolean z) {
        this.needCheck = z;
    }

    public final void setProgress(int i) {
        this.progress = i;
    }

    public final void setStatus(int i) {
        this.status = i;
    }

    public final void startDownload() {
        if (!this.downloadManager.getConnectStatus()) {
            checkThemeCTA();
        } else {
            changeStatus$default(this, 1, false, 2, null);
            this.downloadManager.startDownload();
        }
    }

    public final void updateDownloadStatus(int i) {
        if (i == 0) {
            startDownload();
            return;
        }
        if (i == 1) {
            changeStatus$default(this, 0, false, 2, null);
            this.downloadManager.pauseDownload();
        } else if (i == 2) {
            changeStatus$default(this, 3, false, 2, null);
        } else {
            if (i != 3) {
                return;
            }
            changeStatus$default(this, 2, false, 2, null);
        }
    }
}

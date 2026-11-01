package com.miui.aod.template;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.template.data.template.TemplateDataFactory;
import com.miui.aod.template.data.template.base.BaseTemplateConfig;
import com.miui.aod.widget.EdgeFrameLayout;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.task.Task;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: FullAodTemplateView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FullAodTemplateView extends EdgeFrameLayout {
    public View backContentLayer;
    public BaseTemplateConfig preViewConfig;
    public final TemplateConfig templateConfig;
    public Function0 wallpaperChangedCallback;
    public MiuiWallpaperManagerListener wallpaperChangedListener;
    public CombinedWallpaperView wallpaperImg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FullAodTemplateView(Context context, TemplateConfig templateConfig) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        this.templateConfig = templateConfig;
        initData(templateConfig);
        initView();
    }

    public static final void registerWallpaperListener$lambda$5(FullAodTemplateView fullAodTemplateView) {
        if (fullAodTemplateView.wallpaperChangedListener == null) {
            fullAodTemplateView.wallpaperChangedListener = new MiuiWallpaperManagerListener(fullAodTemplateView);
        }
        WallpaperController.Companion companion = WallpaperController.Companion;
        Context context = fullAodTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        WallpaperController companion2 = companion.getInstance(context);
        MiuiWallpaperManagerListener miuiWallpaperManagerListener = fullAodTemplateView.wallpaperChangedListener;
        Intrinsics.checkNotNull(miuiWallpaperManagerListener);
        companion2.registerWallpaperChangedListener(miuiWallpaperManagerListener, 2);
    }

    @Nullable
    public final <T extends View> T getBackContentLayer() {
        return (T) this.backContentLayer;
    }

    @Nullable
    public final Function0<Unit> getWallpaperChangedCallback() {
        return this.wallpaperChangedCallback;
    }

    public final void initData(TemplateConfig templateConfig) {
        if (templateConfig == null) {
            return;
        }
        TemplateDataFactory templateDataFactory = new TemplateDataFactory();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.preViewConfig = templateDataFactory.getTemplateConfig(context, templateConfig, this);
        Log.d(FullAodTemplateViewKt.TAG, "initData:preViewConfig=" + this.preViewConfig);
    }

    public final void initView() {
        initWallpaperImageView();
        View view = new View(getContext());
        view.setBackgroundColor(Color.parseColor("#66000000"));
        BaseTemplateConfig baseTemplateConfig = this.preViewConfig;
        if (baseTemplateConfig != null) {
            baseTemplateConfig.setBackgroundView(view);
        }
        addView(view);
        BaseTemplateConfig baseTemplateConfig2 = this.preViewConfig;
        int iBackContentLayerId = baseTemplateConfig2 != null ? baseTemplateConfig2.backContentLayerId() : 0;
        if (iBackContentLayerId != 0) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(iBackContentLayerId, (ViewGroup) null);
            this.backContentLayer = viewInflate;
            if (viewInflate != null) {
                addView(viewInflate, new FrameLayout.LayoutParams(-1, -1));
            }
        }
        BaseTemplateConfig baseTemplateConfig3 = this.preViewConfig;
        int iForeContentLayerId = baseTemplateConfig3 != null ? baseTemplateConfig3.foreContentLayerId() : 0;
        if (iForeContentLayerId != 0) {
            LayoutInflater.from(getContext()).inflate(iForeContentLayerId, this);
        }
        if (findViewById(R.id.clock_view) == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.full_aod_pre_template_view, this);
        }
    }

    public final void initWallpaperImageView() {
        View view = this.wallpaperImg;
        if (view != null) {
            removeView(view);
        }
        WallpaperInfo wallpaperInfo = this.templateConfig.getWallpaperInfo();
        CombinedWallpaperView combinedWallpaperViewCreateWallpaperView$default = null;
        if (wallpaperInfo != null) {
            wallpaperInfo.setPositionInfo(null);
        }
        WallpaperInfo wallpaperInfo2 = this.templateConfig.getWallpaperInfo();
        if (wallpaperInfo2 != null) {
            IWallpaperLayer.Companion companion = IWallpaperLayer.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            WallpaperInfo wallpaperInfo3 = this.templateConfig.getWallpaperInfo();
            combinedWallpaperViewCreateWallpaperView$default = IWallpaperLayer.Companion.createWallpaperView$default(companion, context, wallpaperInfo2, false, (wallpaperInfo3 != null ? wallpaperInfo3.getCurrentLockWallpaperSource() : null) == LockWallpaperSource.WALLPAPER_MANAGER, false, null, null, false, 224, null);
            combinedWallpaperViewCreateWallpaperView$default.setWallpaperCallback(this.preViewConfig);
        }
        this.wallpaperImg = combinedWallpaperViewCreateWallpaperView$default;
        addView(combinedWallpaperViewCreateWallpaperView$default);
    }

    public final void loadData() {
        BaseTemplateConfig baseTemplateConfig = this.preViewConfig;
        if (baseTemplateConfig == null) {
            return;
        }
        Log.d(FullAodTemplateViewKt.TAG, "loadData:preViewConfig=" + baseTemplateConfig);
        baseTemplateConfig.udpateFullAodTextColor(baseTemplateConfig.getTemplateConfig());
        baseTemplateConfig.initTemplateBean(baseTemplateConfig.getTemplateConfig());
        baseTemplateConfig.setAutoColorPicker(baseTemplateConfig.createAutoColorPicker());
        baseTemplateConfig.initForeClock(baseTemplateConfig.getTemplateView());
        baseTemplateConfig.initClock();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        BaseTemplateConfig baseTemplateConfig = this.preViewConfig;
        if (baseTemplateConfig == null || !baseTemplateConfig.onWindowFocusChanged(z)) {
            return;
        }
        removeAllViews();
        initData(this.templateConfig);
        initView();
        loadData();
    }

    public final void registerWallpaperListener(Function0 refresh) {
        Intrinsics.checkNotNullParameter(refresh, "refresh");
        this.wallpaperChangedCallback = refresh;
        Task.run(new Runnable() { // from class: com.miui.aod.template.FullAodTemplateView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FullAodTemplateView.registerWallpaperListener$lambda$5(this.f$0);
            }
        });
    }

    public final void setWallpaperChangedCallback(@Nullable Function0<Unit> function0) {
        this.wallpaperChangedCallback = function0;
    }

    @Override // com.miui.aod.widget.EdgeFrameLayout
    public void updateRadiusAndColor(int i, final int i2) {
        super.updateRadiusAndColor(i, i2);
        CombinedWallpaperView combinedWallpaperView = this.wallpaperImg;
        if (combinedWallpaperView != null) {
            combinedWallpaperView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.aod.template.FullAodTemplateView$updateRadiusAndColor$1$1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    if (view == null) {
                        return;
                    }
                    int imageShowStrokeWidth = (int) Utils.getImageShowStrokeWidth();
                    Rect rect = new Rect(imageShowStrokeWidth, imageShowStrokeWidth, view.getWidth() - imageShowStrokeWidth, view.getHeight() - imageShowStrokeWidth);
                    if (outline != null) {
                        outline.setRoundRect(rect, i2);
                    }
                }
            });
            combinedWallpaperView.setClipToOutline(true);
            combinedWallpaperView.setClipChildren(true);
            combinedWallpaperView.setClipToPadding(true);
        }
    }
}

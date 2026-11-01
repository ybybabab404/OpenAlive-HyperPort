package com.miui.keyguard.editor.edit.doodleclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.content.res.AppCompatResources;
import com.miui.aod.category.CategoryConstants;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.data.preset.WallpaperFilterKt;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.RandomColorUtil;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.handler.DoodleColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.DoodleAutoColorPicker;
import com.miui.keyguard.editor.edit.style.DoodleStyleEditor;
import com.miui.keyguard.editor.edit.wallpaper.GestureManager;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.utils.BitmapCreator;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.MiuiClockViewSafeHolder;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import com.miui.keyguardtemplate.doodle.DoodleView;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: DoodleTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class DoodleTemplateView extends BaseClockView implements View.OnClickListener {
    public final long PICK_COLOR_DELAY;
    public final int PICK_COLOR_MSG;
    public View doodleClockView;
    public DoodleView doodleLayout;
    public boolean doodleTextVisible;
    public Handler pickColorHandler;
    public ImageView ribbonBackEnd;
    public ImageView ribbonBackStart;
    public ImageView ribbonForeEnd;
    public ImageView ribbonForeStart;
    public boolean setDoodleVisibleFromTheme;
    public long updateTime;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.doodleTextVisible = true;
        this.setDoodleVisibleFromTheme = true;
        this.updateTime = -1L;
        this.PICK_COLOR_MSG = 1001;
        this.PICK_COLOR_DELAY = DeviceUtil.INSTANCE.isFoldLargeScreen(context) ? 300L : 500L;
        this.pickColorHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView$$ExternalSyntheticLambda2
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return DoodleTemplateView.pickColorHandler$lambda$0(this.f$0, message);
            }
        });
    }

    private final boolean getCurrentWallpaperSupportSubject() {
        TemplateConfig templateConfig;
        WallpaperInfo wallpaperInfo;
        return (isGalleryOpened() || (templateConfig = templateConfig()) == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null || !wallpaperInfo.getSupportSubject() || getSubjectInfo() == null) ? false : true;
    }

    public static final Bitmap onClockInfoUpdate$lambda$12(DoodleTemplateView doodleTemplateView) {
        DoodleView doodleView = doodleTemplateView.doodleLayout;
        if (doodleView != null) {
            return doodleView.preLoadBitmap(doodleTemplateView.getContext(), doodleTemplateView.getCurrentClockBean().getStyle());
        }
        return null;
    }

    public static final void onClockInfoUpdate$lambda$13(int i, DoodleTemplateView doodleTemplateView, Bitmap bitmap) {
        DoodleView doodleView;
        if (i != doodleTemplateView.getCurrentClockBean().getStyle() || (doodleView = doodleTemplateView.doodleLayout) == null) {
            return;
        }
        doodleView.setDoodleBitmap(bitmap, i);
    }

    public static final Bitmap onMiuiClockViewCreated$lambda$3(DoodleTemplateView doodleTemplateView) {
        DoodleView doodleView = doodleTemplateView.doodleLayout;
        if (doodleView != null) {
            return doodleView.preLoadBitmap(doodleTemplateView.getContext(), doodleTemplateView.getCurrentClockBean().getStyle());
        }
        return null;
    }

    public static final void onMiuiClockViewCreated$lambda$4(DoodleTemplateView doodleTemplateView, Bitmap bitmap) {
        DoodleView doodleView = doodleTemplateView.doodleLayout;
        if (doodleView != null) {
            doodleView.setDoodleBitmap(bitmap, doodleTemplateView.getCurrentClockBean().getStyle());
        }
    }

    public static final Boolean onMiuiClockViewCreated$lambda$5(DoodleTemplateView doodleTemplateView) {
        return Boolean.valueOf(doodleTemplateView.isSupportMatting());
    }

    public static final void onMiuiClockViewCreated$lambda$6(DoodleTemplateView doodleTemplateView, TemplateConfig templateConfig, Boolean bool) {
        Log.i("DoodleTemplateView", "support:==" + bool);
        Intrinsics.checkNotNull(bool);
        doodleTemplateView.setWallpaperSupportDepth(bool.booleanValue());
        doodleTemplateView.processDoodleVisible(templateConfig, bool.booleanValue());
    }

    public static final boolean pickColorHandler$lambda$0(DoodleTemplateView doodleTemplateView, Message message) {
        CommonConfig commonConfigTemplateConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        if (message.what != doodleTemplateView.PICK_COLOR_MSG) {
            return false;
        }
        BaseTemplateView baseTemplateView = doodleTemplateView.getBaseTemplateView();
        BaseClockView.startPickColorFromWallpaper$default(doodleTemplateView, (baseTemplateView == null || (commonConfigTemplateConfig = baseTemplateView.templateConfig()) == null || (lockscreenInfo = commonConfigTemplateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper(), true, false, null, 12, null);
        Log.d("DoodleTemplateView", "pickColor success");
        return true;
    }

    private final void setRibbonVisibility(boolean z) {
        setViewsVisibility(z, this.ribbonBackEnd, this.ribbonBackStart, this.ribbonForeEnd, this.ribbonForeStart);
    }

    private final void setWallpaperSupportDepth(boolean z) {
        Log.d("DoodleTemplateView", "setWallpaperSupportDepth: status=" + z);
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setWallpaperSupportDepth(z);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyTemplateInfo(TemplateConfig templateConfig) {
        WallpaperInfo wallpaperInfo;
        ClockInfo clockInfo;
        super.applyTemplateInfo(templateConfig);
        if (templateConfig != null && (clockInfo = templateConfig.getClockInfo()) != null) {
            clockInfo.setExtraFlag(getCurrentClockBean().getExtraFlag());
            Log.i("DoodleTemplateView", "it.extraFlag=" + clockInfo.getExtraFlag());
        }
        if (templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null) {
            return;
        }
        wallpaperInfo.setEnableDoodle(!this.doodleTextVisible ? 1 : 0);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_doodle_clock_back_content_layer;
    }

    public final Bitmap composeBitmapForPickColor(Bitmap bitmap) {
        DoodleInfo doodle;
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rect2 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(bitmapCreator, width, height, config, WideColorGamutManager.INSTANCE.isWideColorBitmap(bitmap), false, 16, null);
        Canvas canvas = new Canvas(bitmapCreateBitmap$default);
        TemplateConfig templateConfig = templateConfig();
        canvas.drawColor((templateConfig == null || (doodle = templateConfig.getDoodle()) == null) ? -1 : doodle.getSolidColor());
        canvas.drawBitmap(bitmap, rect, rect2, (Paint) null);
        Log.d("DoodleTemplateView", "composeBitmapForPickColor: composeBitmap= " + bitmap.getWidth() + " x " + bitmap.getHeight());
        return bitmapCreateBitmap$default;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        TemplateConfig templateConfig = templateConfig();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new DoodleAutoColorPicker(context, getClockStyleType(), templateConfig);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        DoodleInfo doodle;
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (doodle = templateConfig.getDoodle()) == null) {
            return null;
        }
        return new DoodleColorPickHandler(this, getCurrentClockBean(), doodle, hierarchyEnable());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void destroy() {
        super.destroy();
        this.pickColorHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int foreContentLayerId() {
        return R.layout.kg_layout_doodle_clock_fore_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "doodle";
    }

    /* JADX WARN: Code duplicated, block: B:19:0x007f  */
    /* JADX WARN: Code duplicated, block: B:8:0x0029  */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        int iWidth;
        int dimensionPixelSize;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            iWidth = (deviceUtil.currentDisplaySize(context).width() / 2) - getResources().getDimensionPixelSize(R.dimen.kg_doodle_clock_editor_margin_time_pad_left);
        } else {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context2)) {
                Context context3 = getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                iWidth = (deviceUtil.currentDisplaySize(context3).width() / 2) - getResources().getDimensionPixelSize(R.dimen.kg_doodle_clock_editor_margin_time_pad_left);
            } else {
                iWidth = getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_frame_extra_left);
            }
        }
        View view = this.doodleClockView;
        int top = view != null ? view.getTop() : 0;
        if (deviceUtil.isPad()) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_doodle_clock_editor_margin_time_pad_width) + iWidth;
        } else {
            Context context4 = getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context4)) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_doodle_clock_editor_margin_time_pad_width) + iWidth;
            } else {
                Context context5 = getContext();
                Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                dimensionPixelSize = deviceUtil.currentDisplaySize(context5).width() - getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_frame_extra_right);
            }
        }
        View view2 = this.doodleClockView;
        return CollectionsKt.listOf(BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, new Rect(iWidth, top, dimensionPixelSize, view2 != null ? view2.getBottom() : 0), CustomMarginType.ALL, new View[0], 0, 16, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int getFilterBottomSheetAlignModel() {
        return 264;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        HierarchyImageAvoidController avoidController;
        FontFilter doodle_template_font_filter = FontFilterKt.getDOODLE_TEMPLATE_FONT_FILTER();
        Boolean[] targetMutable = doodle_template_font_filter.getTargetMutable();
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        boolean z = false;
        if (hierarchyImageView != null && (avoidController = hierarchyImageView.getAvoidController()) != null && avoidController.isHierarchyEnable()) {
            z = true;
        }
        targetMutable[1] = Boolean.valueOf(z);
        return doodle_template_font_filter;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public MiuiClockViewSafeHolder getMiuiClockViewSafeHolder() {
        String templateId;
        DoodleInfo doodle;
        ClockInfo clockInfo;
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
            templateId = null;
            doodle = null;
        } else {
            templateId = clockInfo.getTemplateId();
            doodle = null;
        }
        WeakReference weakReference = new WeakReference(getMiuiClockView());
        TemplateConfig templateConfig2 = templateConfig();
        if (templateConfig2 != null) {
            doodle = templateConfig2.getDoodle();
        }
        return new MiuiClockViewSafeHolder(templateId, weakReference, doodle, null, 8, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new DoodleStyleEditor(this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public List<WallpaperFilter> getWallpaperFilter() {
        return WallpaperFilterKt.getDOODLE_WALLPAPER_FILTER_DATA();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        Log.i("DoodleTemplateView", "currentTemple " + templateConfig.getClockInfo().getExtraFlag());
        getCurrentClockBean().setStyle(templateConfig.getClockInfo().getStyle());
        getCurrentClockBean().setPrimaryColor(templateConfig.getClockInfo().getPrimaryColor());
        getCurrentClockBean().setAutoPrimaryColor(templateConfig.getClockInfo().isAutoPrimaryColor());
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig, String str) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig, str);
        if (str != null) {
            switch (str.hashCode()) {
                case -905948230:
                    if (!str.equals("sensor")) {
                        return;
                    }
                    break;
                case 112202875:
                    if (!str.equals("video")) {
                        return;
                    }
                    break;
                case 1196792382:
                    if (!str.equals(CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
                        return;
                    }
                    break;
                case 1564712577:
                    if (!str.equals("linkage_video")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            this.setDoodleVisibleFromTheme = false;
        }
    }

    public final boolean isDisableDoodleTextView() {
        TemplateConfig templateConfig;
        WallpaperInfo wallpaperInfo;
        return isDynamicWallpaper() || (templateConfig = templateConfig()) == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null || !wallpaperInfo.getSupportSubject();
    }

    public final boolean isDynamicWallpaper() {
        WallpaperInfo wallpaperInfo;
        TemplateConfig templateConfig = templateConfig();
        String resourceType = (templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType();
        return Intrinsics.areEqual(resourceType, "video") || Intrinsics.areEqual(resourceType, CategoryConstants.CATEGORY_SUPER_WALLPAPER) || Intrinsics.areEqual(resourceType, "sensor") || Intrinsics.areEqual(resourceType, "linkage_video") || Intrinsics.areEqual(resourceType, "livephoto");
    }

    public final boolean isLockWallPaperChanged() {
        EditFragment editFragment = getEditFragment();
        return editFragment == null || !editFragment.useCurrentLockWallpaper() || wallpaperHasChanged();
    }

    /* JADX WARN: Code duplicated, block: B:13:0x003d  */
    /* JADX WARN: Code duplicated, block: B:14:0x003f  */
    /* JADX WARN: Code duplicated, block: B:16:0x0045  */
    /* JADX WARN: Code duplicated, block: B:21:0x0056  */
    public final boolean isSupportMatting() {
        TemplateConfig templateConfig;
        String resourceType;
        TemplateConfig templateConfig2;
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        boolean z = true;
        if (getTemplateSource() == -1) {
            TemplateApi.Companion companion = TemplateApi.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!companion.getInstance(context).isThirdPartyTheme() && !isGalleryOpened()) {
                TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                if (!templateDataUtil.isSupportMatting(context2) && !isLockWallPaperChanged()) {
                    z = false;
                }
            } else if (isGalleryOpened()) {
                z = false;
            } else {
                templateConfig = templateConfig();
                if (templateConfig != null || (wallpaperInfo2 = templateConfig.getWallpaperInfo()) == null) {
                    resourceType = null;
                } else {
                    resourceType = wallpaperInfo2.getResourceType();
                }
                if (Intrinsics.areEqual(resourceType, "video") || (templateConfig2 = templateConfig()) == null || (wallpaperInfo = templateConfig2.getWallpaperInfo()) == null || !wallpaperInfo.getSupportSubject()) {
                    z = false;
                }
            }
        } else if (isGalleryOpened()) {
            z = false;
        } else {
            templateConfig = templateConfig();
            if (templateConfig != null) {
                resourceType = null;
            } else {
                resourceType = null;
            }
            if (Intrinsics.areEqual(resourceType, "video")) {
                z = false;
            } else {
                z = false;
            }
        }
        Log.d("DoodleTemplateView", "isSupportMatting: supportMatting=" + z);
        return z;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onApplied(boolean z) {
        super.onApplied(z);
        if (z) {
            Log.i("DoodleTemplateView", "doodleTextVisible " + this.doodleTextVisible);
            TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            templateDataUtil.setSupportShowDoodleText(context, this.doodleTextVisible);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view != null && view.getId() == R.id.kg_time_area_frame) {
            onClickClockFrame(view);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
        DoodleView doodleView = this.doodleLayout;
        if (doodleView != null) {
            doodleView.setDoodleColor(getCurrentClockBean().getPrimaryColor());
        }
        final int style = getCurrentClockBean().getStyle();
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return DoodleTemplateView.onClockInfoUpdate$lambda$12(this.f$0);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DoodleTemplateView.onClockInfoUpdate$lambda$13(style, this, (Bitmap) obj);
            }
        });
    }

    public final void onDoodleInfoUpdate() {
        updateColorLayer();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        EditFragmentViewModel viewModel;
        TemplateConfig templateConfig;
        WallpaperInfo wallpaperInfo;
        super.onEdited(i, obj);
        if (i == 30) {
            if (!wallpaperHasChanged() && (templateConfig = templateConfig()) != null) {
                RandomColorUtil.INSTANCE.tryRandomColorType(templateConfig, true);
            }
            if ((obj instanceof WallpaperTypeInfo) && Intrinsics.areEqual(((WallpaperTypeInfo) obj).getType(), "video") && (viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment())) != null) {
                EditFragmentViewModel.updateDoodleStatus$default(viewModel, 0, false, 2, null);
                return;
            }
            return;
        }
        if (i == 140) {
            if (!(obj instanceof Integer) || Intrinsics.areEqual(obj, Integer.valueOf(getCurrentClockBean().getStyle()))) {
                return;
            }
            getCurrentClockBean().setStyle(((Number) obj).intValue());
            onClockInfoUpdate();
            return;
        }
        if (i == 37) {
            ImageView imageView = (ImageView) getColorLayer();
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            setRibbonVisibility(false);
            EditFragmentViewModel viewModel2 = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
            if (viewModel2 != null) {
                EditFragmentViewModel.updateDoodleStatus$default(viewModel2, 0, false, 2, null);
                return;
            }
            return;
        }
        if (i != 38) {
            return;
        }
        ImageView imageView2 = (ImageView) getColorLayer();
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        TemplateConfig templateConfig2 = templateConfig();
        setRibbonVisibility((templateConfig2 == null || (wallpaperInfo = templateConfig2.getWallpaperInfo()) == null || wallpaperInfo.getMagicType() != 20000) ? false : true);
        if (this.doodleTextVisible) {
            EditFragmentViewModel viewModel3 = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
            if (viewModel3 != null) {
                EditFragmentViewModel.updateDoodleStatus$default(viewModel3, 1, false, 2, null);
            }
        } else {
            EditFragmentViewModel viewModel4 = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
            if (viewModel4 != null) {
                EditFragmentViewModel.updateDoodleStatus$default(viewModel4, 2, false, 2, null);
            }
        }
        setDoodleTextVisible(this.doodleTextVisible);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onHierarchyDisable() {
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        super.onHierarchyDisable();
        updateColorLayer(0);
        setRibbonVisibility(false);
        Log.i("DoodleTemplateView", "onHierarchyDisable");
        if (isGalleryOpened()) {
            setWallpaperSupportDepth(false);
        } else {
            TemplateConfig templateConfig = templateConfig();
            if (templateConfig != null && (wallpaperInfo2 = templateConfig.getWallpaperInfo()) != null) {
                wallpaperInfo2.setSupportSubject(getCurrentWallpaperSupportSubject());
            }
            setWallpaperSupportDepth(getCurrentWallpaperSupportSubject());
        }
        ColorPickHandler colorPickHandler = getColorPickHandler();
        if (colorPickHandler != null) {
            colorPickHandler.onHierarchyEnableChange(false);
        }
        TemplateConfig templateConfig2 = templateConfig();
        BaseClockView.startPickColorFromWallpaper$default(this, (templateConfig2 == null || (wallpaperInfo = templateConfig2.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper(), true, false, null, 8, null);
        resetStyleEditor();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0043  */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onHierarchyEnable() {
        int solidColor;
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        DoodleInfo doodle;
        WallpaperInfo wallpaperInfo3;
        super.onHierarchyEnable();
        MagicType magicType = MagicType.INSTANCE;
        TemplateConfig templateConfig = templateConfig();
        Bitmap currentWallpaper = null;
        if (magicType.isDepth((templateConfig == null || (wallpaperInfo3 = templateConfig.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo3.getMagicType()))) {
            SegmentInfo subjectInfo = getSubjectInfo();
            if ((subjectInfo != null ? subjectInfo.getBitmap() : null) != null) {
                TemplateConfig templateConfig2 = templateConfig();
                solidColor = (templateConfig2 == null || (doodle = templateConfig2.getDoodle()) == null) ? -1 : doodle.getSolidColor();
            } else {
                solidColor = 0;
            }
        } else {
            solidColor = 0;
        }
        updateColorLayer(solidColor);
        TemplateConfig templateConfig3 = templateConfig();
        if (templateConfig3 != null && (wallpaperInfo2 = templateConfig3.getWallpaperInfo()) != null) {
            wallpaperInfo2.setSupportSubject(true);
        }
        setWallpaperSupportDepth(true);
        ColorPickHandler colorPickHandler = getColorPickHandler();
        if (colorPickHandler != null) {
            colorPickHandler.onHierarchyEnableChange(true);
        }
        TemplateConfig templateConfig4 = templateConfig();
        if (templateConfig4 != null && (wallpaperInfo = templateConfig4.getWallpaperInfo()) != null) {
            currentWallpaper = wallpaperInfo.getCurrentWallpaper();
        }
        BaseClockView.startPickColorFromWallpaper$default(this, currentWallpaper, true, false, null, 8, null);
        setRibbonVisibility(true);
        resetStyleEditor();
        Log.i("DoodleTemplateView", "onHierarchyEnable");
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(final TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.onMiuiClockViewCreated(templateConfig);
        updateColorLayer();
        if (!getSuccessUpdateClockView()) {
            completeWithCacheColor(false);
        }
        if (isDualClock()) {
            return;
        }
        this.doodleLayout = (DoodleView) findViewById(R.id.doodle_layout);
        this.ribbonBackStart = (ImageView) findViewById(R.id.ribbon_back_start);
        this.ribbonBackEnd = (ImageView) findViewById(R.id.ribbon_back_end);
        this.ribbonForeStart = (ImageView) findViewById(R.id.ribbon_fore_start);
        this.ribbonForeEnd = (ImageView) findViewById(R.id.ribbon_fore_end);
        MiuiClockView miuiClockView = getMiuiClockView();
        this.doodleClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.CLOCK_CONTAINER) : null;
        setRibbonColor();
        DoodleView doodleView = this.doodleLayout;
        if (doodleView != null) {
            doodleView.setDoodleColor(getCurrentClockBean().getPrimaryColor());
        }
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return DoodleTemplateView.onMiuiClockViewCreated$lambda$3(this.f$0);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DoodleTemplateView.onMiuiClockViewCreated$lambda$4(this.f$0, (Bitmap) obj);
            }
        });
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return DoodleTemplateView.onMiuiClockViewCreated$lambda$5(this.f$0);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DoodleTemplateView.onMiuiClockViewCreated$lambda$6(this.f$0, templateConfig, (Boolean) obj);
            }
        });
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void popFontFilterDialog(int i) {
        super.popFontFilterDialog(i);
        if (i == 0) {
            BaseClockView.popClockFontFilterDialog$default(this, R.id.kg_time_area_frame, 0, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Pair preProcessBitmapForPickColor(Bitmap bitmap, boolean z) {
        IWallpaperLayer wallpaperLayer;
        GestureManager gestureManager;
        DoodleInfo doodle;
        WallpaperInfo wallpaperInfo;
        SegmentInfo subjectInfo;
        WallpaperInfo wallpaperInfo2;
        WallpaperInfo wallpaperInfo3;
        WallpaperInfo wallpaperInfo4;
        WallpaperInfo wallpaperInfo5;
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        MagicType magicType = MagicType.INSTANCE;
        TemplateConfig templateConfig = templateConfig();
        if (!magicType.isDepthVideo((templateConfig == null || (wallpaperInfo5 = templateConfig.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo5.getMagicType()))) {
            TemplateConfig templateConfig2 = templateConfig();
            if (!Intrinsics.areEqual((templateConfig2 == null || (wallpaperInfo4 = templateConfig2.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getResourceType(), "video")) {
                Wallpaper.Companion companion = Wallpaper.Companion;
                TemplateConfig templateConfig3 = templateConfig();
                if (!companion.isSpecialWallpaperType((templateConfig3 == null || (wallpaperInfo3 = templateConfig3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType())) {
                    TemplateConfig templateConfig4 = templateConfig();
                    boolean zIsDepth = magicType.isDepth((templateConfig4 == null || (wallpaperInfo2 = templateConfig4.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo2.getMagicType()));
                    float fAbs = 0.0f;
                    if (!zIsDepth) {
                        if (bitmap.getWidth() > 0 && getWidth() > 0) {
                            fAbs = Math.abs((bitmap.getHeight() / bitmap.getWidth()) - (getHeight() / getWidth()));
                        }
                        boolean z2 = fAbs > 0.001f;
                        Log.i("DoodleTemplateView", "preProcessBitmapForPickColor: " + fAbs + " , bitmap : " + bitmap.getWidth() + " x " + bitmap.getHeight());
                        Pair curCropWallpaperAndRecycle = (!z2 || (wallpaperLayer = getWallpaperLayer()) == null || (gestureManager = wallpaperLayer.getGestureManager()) == null) ? null : gestureManager.getCurCropWallpaperAndRecycle();
                        return curCropWallpaperAndRecycle == null ? new Pair(null, Boolean.FALSE) : curCropWallpaperAndRecycle;
                    }
                    BaseTemplateView baseTemplateView = getBaseTemplateView();
                    Boolean boolValueOf = baseTemplateView != null ? Boolean.valueOf(baseTemplateView.editable()) : null;
                    BaseTemplateView baseTemplateView2 = getBaseTemplateView();
                    Bitmap bitmap2 = (baseTemplateView2 == null || (subjectInfo = baseTemplateView2.getSubjectInfo()) == null) ? null : subjectInfo.getBitmap();
                    TemplateConfig templateConfig5 = templateConfig();
                    Bitmap subjectBitmap = (templateConfig5 == null || (wallpaperInfo = templateConfig5.getWallpaperInfo()) == null) ? null : wallpaperInfo.getSubjectBitmap();
                    Bitmap bitmap3 = bitmap2 == null ? subjectBitmap : bitmap2;
                    IWallpaperLayer wallpaperLayer2 = getWallpaperLayer();
                    WallpaperPositionInfo position$default = wallpaperLayer2 != null ? IWallpaperLayer.DefaultImpls.getPosition$default(wallpaperLayer2, null, 1, null) : null;
                    Log.d("DoodleTemplateView", "preProcessBitmapForPickColor: editable=" + boolValueOf + ", subjectBitmapFromSegmentInfo=" + (bitmap2 != null) + ", subjectBitmapFromWallpaperInfo=" + (subjectBitmap != null) + ", positionInfo=" + (position$default != null));
                    if (Intrinsics.areEqual(boolValueOf, Boolean.TRUE) && bitmap3 != null && position$default != null) {
                        WallpaperPositionInfo wallpaperPositionInfo = position$default;
                        Bitmap bitmapCropBitmap = BitmapUtil.INSTANCE.cropBitmap(bitmap3, (int) wallpaperPositionInfo.getLeft(), (int) wallpaperPositionInfo.getRight(), (int) wallpaperPositionInfo.getTop(), (int) wallpaperPositionInfo.getBottom());
                        return bitmapCropBitmap == null ? new Pair(null, Boolean.FALSE) : new Pair(composeBitmapForPickColor(bitmapCropBitmap), Boolean.FALSE);
                    }
                    Boolean bool = Boolean.FALSE;
                    if (Intrinsics.areEqual(boolValueOf, bool) && bitmap3 != null) {
                        return new Pair(composeBitmapForPickColor(bitmap3), bool);
                    }
                    float fMin = getHierarchyLayer() != null ? Math.min(getWidth() / bitmap.getWidth(), getHeight() / bitmap.getHeight()) : 0.0f;
                    int width = fMin > 0.0f ? (int) (bitmap.getWidth() * fMin) : bitmap.getWidth();
                    int height = fMin > 0.0f ? (int) (bitmap.getHeight() * fMin) : bitmap.getHeight();
                    BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
                    Bitmap.Config config = bitmap.getConfig();
                    if (config == null) {
                        config = Bitmap.Config.ARGB_8888;
                    }
                    Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(bitmapCreator, width, height, config, WideColorGamutManager.INSTANCE.isWideColorBitmap(bitmap), false, 16, null);
                    Canvas canvas = new Canvas(bitmapCreateBitmap$default);
                    TemplateConfig templateConfig6 = templateConfig();
                    canvas.drawColor((templateConfig6 == null || (doodle = templateConfig6.getDoodle()) == null) ? -1 : doodle.getSolidColor());
                    View hierarchyLayer = getHierarchyLayer();
                    if (hierarchyLayer != null) {
                        hierarchyLayer.draw(canvas);
                    }
                    Log.i("DoodleTemplateView", "preProcessBitmapForPickColor: scale = " + fMin + ", width = " + canvas.getWidth() + " , height = " + canvas.getHeight());
                    return new Pair(bitmapCreateBitmap$default, Boolean.valueOf(!Intrinsics.areEqual(bitmap, bitmapCreateBitmap$default)));
                }
            }
        }
        return new Pair(bitmap, Boolean.FALSE);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0040  */
    /* JADX WARN: Code duplicated, block: B:31:0x006a  */
    public final void processDoodleVisible(TemplateConfig templateConfig, boolean z) {
        boolean z2;
        IntWrapper hierarchyStatus;
        boolean zIsSupportShowDoodleText;
        ClockInfo clockInfo;
        WallpaperInfo wallpaperInfo;
        long templateSource = getTemplateSource();
        EditFragmentViewModel.Companion companion = EditFragmentViewModel.Companion;
        EditFragmentViewModel viewModel = companion.getViewModel(getEditFragment());
        if (!z) {
            setRibbonVisibility(false);
            if (viewModel != null) {
                EditFragmentViewModel.updateDoodleStatus$default(viewModel, 0, false, 2, null);
                return;
            }
            return;
        }
        setRibbonVisibility((templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null || wallpaperInfo.getMagicType() != 20000) ? false : true);
        if (this.setDoodleVisibleFromTheme) {
            if (templateSource == -1) {
                if (editable() && isLockWallPaperChanged()) {
                    zIsSupportShowDoodleText = true;
                } else {
                    TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
                    Context context = getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    zIsSupportShowDoodleText = templateDataUtil.isSupportShowDoodleText(context);
                }
            } else if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null || !showDoodleText(clockInfo.getExtraFlag())) {
                zIsSupportShowDoodleText = false;
            } else {
                zIsSupportShowDoodleText = true;
            }
            if (zIsSupportShowDoodleText) {
                z2 = true;
            } else {
                z2 = false;
            }
        } else {
            z2 = false;
        }
        this.doodleTextVisible = z2;
        if (isDisableDoodleTextView()) {
            if (viewModel != null) {
                EditFragmentViewModel.updateDoodleStatus$default(viewModel, 0, false, 2, null);
            }
            setWallpaperSupportDepth(false);
        } else {
            if (!isLockWallPaperChanged()) {
                if (viewModel != null) {
                    EditFragmentViewModel.updateDoodleStatus$default(viewModel, this.doodleTextVisible ? 1 : 2, false, 2, null);
                    return;
                }
                return;
            }
            EditFragmentViewModel viewModel2 = companion.getViewModel(getEditFragment());
            boolean z3 = (viewModel2 == null || (hierarchyStatus = viewModel2.getHierarchyStatus()) == null || hierarchyStatus.getIntValue() != 1) ? false : true;
            if (this.doodleTextVisible && z3) {
                if (viewModel != null) {
                    EditFragmentViewModel.updateDoodleStatus$default(viewModel, 1, false, 2, null);
                }
            } else if (viewModel != null) {
                EditFragmentViewModel.updateDoodleStatus$default(viewModel, 2, false, 2, null);
            }
            setDoodleTextVisible(this.doodleTextVisible);
        }
    }

    public final Drawable ribbonDrawable(int i, int i2) {
        Drawable drawable = AppCompatResources.getDrawable(getContext(), i);
        if (drawable != null) {
            drawable.setTint(i2);
        }
        return drawable;
    }

    public final void setDoodleTextVisible(boolean z) {
        DoodleView doodleView = this.doodleLayout;
        if (doodleView != null) {
            doodleView.setVisibility(z ? 0 : 8);
        }
        this.doodleTextVisible = z;
        if (z) {
            getCurrentClockBean().clearExtraFlag(1);
        } else {
            getCurrentClockBean().updateExtraFlag(1);
        }
        onClockInfoUpdate();
        Log.i("DoodleTemplateView", "visible:=" + z + "     currentClockBean.extraFlag=" + getCurrentClockBean().getExtraFlag());
    }

    public final void setRibbonColor() {
        DoodleInfo doodle;
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (doodle = templateConfig.getDoodle()) == null) {
            return;
        }
        Log.d("DoodleTemplateView", "setRibbonColor: doodleInfo.ribbonColor1=" + doodle.getRibbonColor1() + ", doodleInfo.ribbonColor2=" + doodle.getRibbonColor2());
        ImageView imageView = this.ribbonBackStart;
        if (imageView != null) {
            imageView.setImageDrawable(ribbonDrawable(R.drawable.kg_doodle_ribbon_back_start, doodle.getRibbonColor1()));
        }
        ImageView imageView2 = this.ribbonBackEnd;
        if (imageView2 != null) {
            imageView2.setImageDrawable(ribbonDrawable(R.drawable.kg_doodle_ribbon_back_end, doodle.getRibbonColor1()));
        }
        ImageView imageView3 = this.ribbonForeStart;
        if (imageView3 != null) {
            imageView3.setImageDrawable(ribbonDrawable(R.drawable.kg_doodle_ribbon_fore_start, doodle.getRibbonColor2()));
        }
        ImageView imageView4 = this.ribbonForeEnd;
        if (imageView4 != null) {
            imageView4.setImageDrawable(ribbonDrawable(R.drawable.kg_doodle_ribbon_fore_end, doodle.getRibbonColor2()));
        }
    }

    public final void setViewsVisibility(boolean z, View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setVisibility(z ? 0 : 8);
            }
        }
    }

    public final boolean showDoodleText(int i) {
        return (i & 1) == 0;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        WallpaperInfo wallpaperInfo;
        DoodleInfo doodle;
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(colorData.getRandomColorType(), colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        setSuccessUpdateClockView(getMiuiClockView() != null);
        if (isDualClock()) {
            return;
        }
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig != null && (doodle = templateConfig.getDoodle()) != null) {
            if (doodle.isAutoSolidColor()) {
                doodle.setRibbonColor1(colorData.getExtraColor1());
                doodle.setRibbonColor2(colorData.getExtraColor2());
                doodle.setSolidColor(colorData.getSecondaryColor());
            } else {
                if (doodle.getRibbonColor1() == 0) {
                    doodle.setRibbonColor1(colorData.getExtraColor1());
                }
                if (doodle.getRibbonColor2() == 0) {
                    doodle.setRibbonColor2(colorData.getExtraColor2());
                }
            }
            updateColorLayer();
        }
        if (getCurrentClockBean().isAutoPrimaryColor()) {
            getCurrentClockBean().setPrimaryColor(colorData.getPrimaryColor());
            onClockInfoUpdate();
        }
        MagicType magicType = MagicType.INSTANCE;
        TemplateConfig templateConfig2 = templateConfig();
        if (magicType.isDepth((templateConfig2 == null || (wallpaperInfo = templateConfig2.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getMagicType()))) {
            setRibbonColor();
        }
    }

    public final void updateColorLayer() {
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        HierarchyImageAvoidController avoidController;
        DoodleInfo doodle;
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null) {
            return;
        }
        int magicType = wallpaperInfo.getMagicType();
        TemplateConfig templateConfig2 = templateConfig();
        int solidColor = (templateConfig2 == null || (doodle = templateConfig2.getDoodle()) == null) ? -1 : doodle.getSolidColor();
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        boolean zIsHierarchyEnable = (hierarchyImageView == null || (avoidController = hierarchyImageView.getAvoidController()) == null) ? true : avoidController.isHierarchyEnable();
        Bitmap bitmap = null;
        if (editable()) {
            SegmentInfo subjectInfo = getSubjectInfo();
            if (subjectInfo != null) {
                bitmap = subjectInfo.getBitmap();
            }
        } else {
            TemplateConfig templateConfig3 = templateConfig();
            if (templateConfig3 != null && (wallpaperInfo2 = templateConfig3.getWallpaperInfo()) != null) {
                bitmap = wallpaperInfo2.getSubjectBitmap();
            }
        }
        if (!MagicType.INSTANCE.isDepth(Integer.valueOf(magicType)) || !zIsHierarchyEnable || bitmap == null) {
            solidColor = 0;
        }
        ImageView imageView = (ImageView) getColorLayer();
        if (imageView != null) {
            imageView.setImageDrawable(new ColorDrawable(solidColor));
        }
    }

    public final void updateColorLayer(int i) {
        ImageView imageView = (ImageView) getColorLayer();
        if (imageView != null) {
            imageView.setImageDrawable(new ColorDrawable(i));
        }
    }

    public final void updateRibbonColor() {
        long j = this.updateTime;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.updateTime = jCurrentTimeMillis;
        if (j >= 0 && jCurrentTimeMillis - j < this.PICK_COLOR_DELAY) {
            this.pickColorHandler.removeMessages(this.PICK_COLOR_MSG);
        }
        this.pickColorHandler.sendEmptyMessageDelayed(this.PICK_COLOR_MSG, this.PICK_COLOR_DELAY);
    }
}

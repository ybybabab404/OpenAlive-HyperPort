package com.miui.keyguard.editor.edit.rhombusclock;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.data.preset.WallpaperFilterKt;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.handler.RhombusColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.RhombusAutoColorPicker;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: RhombusTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class RhombusTemplateView extends BaseClockView implements View.OnClickListener {
    public View clickHotspotView;
    public View rhombusClockView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RhombusTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyTemplateInfo(TemplateConfig templateConfig) {
        WallpaperInfo wallpaperInfo;
        super.applyTemplateInfo(templateConfig);
        if (templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null || wallpaperInfo.getMagicType() != 20000) {
            return;
        }
        wallpaperInfo.resetMagicType("RhombusTemplateView don't support hierarchy");
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_rhombus_clock_back_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new RhombusAutoColorPicker(context, getClockStyleType(), getCurrentClockBean());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        return new RhombusColorPickHandler(this, getCurrentClockBean());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "rhombus";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        MiuiClockView miuiClockView = getMiuiClockView();
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_TIME) : null;
        return CollectionsKt.listOf(BaseClockView.getFixationFrameParam$default(this, R.id.clock_frame, new Rect(iClockView != null ? iClockView.getLeft() + getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_style_frame_margin) : 0, iClockView != null ? iClockView.getTop() + getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_bottom_style_margin_top) : 0, iClockView != null ? iClockView.getRight() - getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_style_frame_margin) : 0, iClockView != null ? (iClockView.getBottom() - iClockView.getTop()) - getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_font_style_margin_bottom) : 0), CustomMarginType.ALL, new View[0], 0, 16, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getRHOMBUS_TEMPLATE_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new RhombusClockStyleEditor(this, this, getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.rhombusclock.RhombusClockStyleEditor");
        ((RhombusClockStyleEditor) clockStyleEditor2).select(getCurrentClockBean().getStyle());
        AbstractPopupEditor clockStyleEditor3 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor3);
        return clockStyleEditor3;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public List<WallpaperFilter> getWallpaperFilter() {
        return WallpaperFilterKt.getRHOMBUS_WALLPAPER_FILTER_DATA();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        getCurrentClockBean().setStyle(templateConfig.getClockInfo().getStyle());
        getCurrentClockBean().setPrimaryColor(templateConfig.getClockInfo().getPrimaryColor());
        getCurrentClockBean().setSecondaryColor(templateConfig.getClockInfo().getSecondaryColor());
        getCurrentClockBean().setAutoPrimaryColor(templateConfig.getClockInfo().isAutoPrimaryColor());
        getCurrentClockBean().setAutoSecondaryColor(templateConfig.getClockInfo().isAutoSecondaryColor());
        getCurrentClockBean().setDiffHourMinuteColor(templateConfig.getClockInfo().isDiffHourMinuteColor());
        getCurrentClockBean().setBlendColor(templateConfig.getClockInfo().getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(templateConfig.getClockInfo().getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(templateConfig.getClockInfo().getClockEffect());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view != null && view.getId() == R.id.clock_frame) {
            inTalkBackCloseDialogs();
            onClickClockFrame(view);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        super.onEdited(i, obj);
        if (i == 120 && (obj instanceof Integer)) {
            getCurrentClockBean().setStyle(((Number) obj).intValue());
            onClockInfoUpdate();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.onMiuiClockViewCreated(templateConfig);
        if (!getSuccessUpdateClockView()) {
            completeWithCacheColor(false);
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        this.rhombusClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_TIME) : null;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onScreenSizeChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onScreenSizeChanged(newConfig);
        View view = this.clickHotspotView;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_click_hotspot_width);
            layoutParams.height = getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_click_hotspot_height);
            view.setLayoutParams(layoutParams);
            ViewUtil.setMarginTop(view, getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_click_hotspot_margin_top));
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onScreenSizeChangedWhenEditable(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onScreenSizeChangedWhenEditable(newConfig);
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        RhombusClockStyleEditor rhombusClockStyleEditor = clockStyleEditor instanceof RhombusClockStyleEditor ? (RhombusClockStyleEditor) clockStyleEditor : null;
        if (rhombusClockStyleEditor != null) {
            rhombusClockStyleEditor.initViews();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void popFontFilterDialog(int i) {
        super.popFontFilterDialog(i);
        if (i == 0) {
            BaseClockView.popClockFontFilterDialog$default(this, R.id.clock_frame, 0, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean supportGradientBlur() {
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean supportHierarchy() {
        return false;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        MiuiClockView miuiClockView2 = getMiuiClockView();
        if (miuiClockView2 != null) {
            miuiClockView2.setInfoTextColorDark(colorData.getDarkInfoText());
        }
        setSuccessUpdateClockView(getMiuiClockView() != null);
    }
}

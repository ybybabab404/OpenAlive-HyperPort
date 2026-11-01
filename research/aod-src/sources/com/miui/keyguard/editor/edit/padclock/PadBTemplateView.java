package com.miui.keyguard.editor.edit.padclock;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.data.preset.WallpaperFilterKt;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.style.PadBStyleEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.List;
import java.util.Set;
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
/* JADX INFO: compiled from: PadBTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PadBTemplateView extends BaseClockView implements View.OnClickListener, EditCallback {
    public View containerView;
    public View dateView;
    public View timeView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PadBTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_clock_back_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new AutoColorPicker(context, getClockStyleType(), getCurrentClockBean());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        return new ColorPickHandler(this, getCurrentClockBean(), null, 4, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int foreContentLayerId() {
        return R.layout.kg_layout_clock_fore_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "pad_exclusive_b";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        int top;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        View view = this.containerView;
        int left = view != null ? view.getLeft() : 0;
        View view2 = this.timeView;
        int top2 = view2 != null ? view2.getTop() : 0;
        View view3 = this.containerView;
        int right = view3 != null ? view3.getRight() : 0;
        View view4 = this.dateView;
        if (view4 != null) {
            int bottom = view4.getBottom();
            View view5 = this.timeView;
            top = bottom - (view5 != null ? view5.getTop() : 0);
        } else {
            top = 0;
        }
        return CollectionsKt.mutableListOf(BaseClockView.getFixationFrameParam$default(this, R.id.kg_pad_b_clock_area_frame, new Rect(left, top2, right, top), CustomMarginType.ALL, new View[0], 0, 16, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getPAD_B_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new PadBStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
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
        ClockInfo clockInfo = templateConfig.getClockInfo();
        getCurrentClockBean().setStyle(clockInfo.getStyle());
        getCurrentClockBean().setPrimaryColor(clockInfo.getPrimaryColor());
        getCurrentClockBean().setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        getCurrentClockBean().setSecondaryColor(clockInfo.getSecondaryColor());
        getCurrentClockBean().setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        getCurrentClockBean().setBlendColor(clockInfo.getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(clockInfo.getClockEffect());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect largeScreenWithNotificationAvoidRect(boolean z) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_pad_b_style_hierarchy_notification_avoid_check_width);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize) / 2;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_pad_b_style_hierarchy_notification_avoid_check_top);
        return new Rect(width, dimensionPixelSize2, dimensionPixelSize + width, getResources().getDimensionPixelSize(R.dimen.kg_pad_b_style_hierarchy_notification_avoid_check_height) + dimensionPixelSize2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
        int i = R.id.kg_pad_b_clock_area_frame;
        if (numValueOf != null && numValueOf.intValue() == i) {
            onClickClockFrame(view);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.onMiuiClockViewCreated(templateConfig);
        if (!getSuccessUpdateClockView()) {
            completeWithCacheColor(false);
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        this.timeView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_TIME) : null;
        MiuiClockView miuiClockView2 = getMiuiClockView();
        this.dateView = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.FULL_DATE) : null;
        MiuiClockView miuiClockView3 = getMiuiClockView();
        this.containerView = miuiClockView3 != null ? miuiClockView3.getIClockView(ClockViewType.GRADIENT_CONTAINER) : null;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void popFontFilterDialog(int i) {
        super.popFontFilterDialog(i);
        if (i == 0) {
            BaseClockView.popClockFontFilterDialog$default(this, R.id.kg_pad_b_clock_area_frame, 0, 2, null);
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
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_pad_b_style_hierarchy_avoid_check_width);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize) / 2;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_pad_b_style_hierarchy_avoid_check_top);
        return new Rect(width, dimensionPixelSize2, dimensionPixelSize + width, getResources().getDimensionPixelSize(R.dimen.kg_pad_b_style_hierarchy_avoid_check_height) + dimensionPixelSize2);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Set unSupportHierarchyTypes() {
        return hierarchyWithNotificationTypes();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        setSuccessUpdateClockView(getMiuiClockView() != null);
    }
}

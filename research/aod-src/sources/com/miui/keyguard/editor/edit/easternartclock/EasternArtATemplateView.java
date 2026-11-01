package com.miui.keyguard.editor.edit.easternartclock;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.style.EasternArtAStyleEditor;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: EasternArtATemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EasternArtATemplateView extends BaseClockView implements View.OnClickListener, EditCallback {
    public final ClockBean foreClockBean;
    public MiuiClockView foreMiuiClockView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasternArtATemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.foreClockBean = new ClockBean("eastern_a_minute");
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
        return new ColorPickHandler(this, getCurrentClockBean(), this.foreClockBean);
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
        return "eastern_a";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        MiuiClockView miuiClockView = this.foreMiuiClockView;
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_DATE) : null;
        return CollectionsKt.mutableListOf(BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, new Rect(iClockView != null ? iClockView.getLeft() - getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style1_frame_extra_left) : 0, iClockView != null ? iClockView.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style1_frame_extra_top) : 0, iClockView != null ? iClockView.getRight() + getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style1_frame_extra_right) : 0, getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style1_frame_extra_bottom)), CustomMarginType.ALL, new View[0], 0, 16, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        int style = getCurrentClockBean().getStyle();
        if (style != 1 && style == 2) {
            return FontFilterKt.getEASTERN_ART_A_STYLE_2_FONT_FILTER();
        }
        return FontFilterKt.getEASTERN_ART_A_STYLE_1_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @Nullable
    public MiuiClockView getForeClock() {
        return this.foreMiuiClockView;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new EasternArtAStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreMiuiClockView = (MiuiClockView) templateView.findViewById(R.id.fore_clock);
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
        this.foreClockBean.setBlendColor(clockInfo.getBlendColor());
        this.foreClockBean.setSecondaryBlendColor(clockInfo.getSecondaryColor());
        this.foreClockBean.setClockEffect(clockInfo.getClockEffect());
        this.foreClockBean.setStyle(clockInfo.getStyle());
        this.foreClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.foreClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.foreClockBean.setSecondaryColor(clockInfo.getSecondaryColor());
        this.foreClockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (v.getId() == R.id.kg_time_area_frame) {
            inTalkBackCloseDialogs();
            onClickClockFrame(v);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
        MiuiClockView miuiClockView = this.foreMiuiClockView;
        if (miuiClockView != null) {
            miuiClockView.setClockBean(this.foreClockBean);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        WallpaperInfo wallpaperInfo;
        HierarchyImageAvoidController avoidController;
        super.onEdited(i, obj);
        if (i == 170 && (obj instanceof Integer)) {
            Number number = (Number) obj;
            getCurrentClockBean().setStyle(number.intValue());
            this.foreClockBean.setStyle(number.intValue());
            HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
            if (hierarchyImageView != null && (avoidController = hierarchyImageView.getAvoidController()) != null) {
                avoidController.resetAvoidRect(hierarchyAvoidRect());
            }
            onClockInfoUpdate();
            TemplateConfig templateConfig = templateConfig();
            BaseClockView.startPickColorFromWallpaper$default(this, (templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper(), false, true, null, 8, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        MiuiClockView miuiClockView;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.onMiuiClockViewCreated(templateConfig);
        MiuiClockView miuiClockView2 = this.foreMiuiClockView;
        if (miuiClockView2 != null) {
            miuiClockView2.setVisibility(!miuiClockView2.isDualClock() ? 0 : 8);
        }
        MiuiClockView miuiClockView3 = this.foreMiuiClockView;
        if (miuiClockView3 != null) {
            MiuiClockView miuiClockView4 = getMiuiClockView();
            miuiClockView3.setBackgroundBlurContainer(miuiClockView4 != null ? miuiClockView4.getBackgroundBlurContainer() : null);
        }
        if (!isDualClock() && (miuiClockView = this.foreMiuiClockView) != null) {
            miuiClockView.init(this.foreClockBean, false);
        }
        if (getSuccessUpdateClockView()) {
            return;
        }
        completeWithCacheColor(false);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void popFontFilterDialog(int i) {
        super.popFontFilterDialog(i);
        if (i == 0) {
            BaseClockView.popClockFontFilterDialog$default(this, R.id.kg_time_area_frame, 0, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int width;
        int dimensionPixelSize3;
        ClockInfo clockInfo;
        TemplateConfig templateConfig = templateConfig();
        Integer numValueOf = (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo.getStyle());
        if (numValueOf != null && numValueOf.intValue() == 1) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style1_hierarchy_avoid_check_width);
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize) / 2;
            dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style1_hierarchy_avoid_check_top);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style1_hierarchy_avoid_check_height);
        } else if (numValueOf != null && numValueOf.intValue() == 2) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style2_hierarchy_avoid_check_width);
            DeviceUtil deviceUtil2 = DeviceUtil.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            width = (deviceUtil2.currentDisplaySizeByContext(context2).getWidth() - dimensionPixelSize) / 2;
            dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style2_hierarchy_avoid_check_top);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style2_hierarchy_avoid_check_height);
        } else {
            dimensionPixelSize = 0;
            dimensionPixelSize2 = 0;
            width = 0;
            dimensionPixelSize3 = 0;
        }
        return new Rect(width, dimensionPixelSize3, dimensionPixelSize + width, dimensionPixelSize2 + dimensionPixelSize3);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Set unSupportHierarchyTypes() {
        return hierarchyWithNotificationTypes();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        boolean z = false;
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        MiuiClockView miuiClockView2 = this.foreMiuiClockView;
        if (miuiClockView2 != null) {
            miuiClockView2.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        if (getMiuiClockView() != null && this.foreMiuiClockView != null) {
            z = true;
        }
        setSuccessUpdateClockView(z);
    }
}

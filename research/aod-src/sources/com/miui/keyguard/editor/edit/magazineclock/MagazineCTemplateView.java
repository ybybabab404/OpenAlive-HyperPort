package com.miui.keyguard.editor.edit.magazineclock;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.handler.MagazineCColorPickerHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.MagazineCAutoColorPicker;
import com.miui.keyguard.editor.edit.style.MagazineCStyleEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.ColorModificationTarget;
import com.miui.keyguard.editor.view.FontFilterView;
import java.util.List;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: MagazineCTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class MagazineCTemplateView extends BaseClockView implements View.OnClickListener {
    public final ClockBean currentDateClockBean;
    public MiuiClockView dateView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineCTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.currentDateClockBean = new ClockBean("magazine_c_date_only");
    }

    private final ColorModificationTarget getColorModificationTarget() {
        int style = getCurrentClockBean().getStyle();
        return (style == 6 || style == 9) ? ColorModificationTarget.PRIMARY_PART : ColorModificationTarget.BOTH_PARTS;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyPrimaryMode() {
        if (isDualClock()) {
            setViewMarginTop(getMiuiClockView(), R.dimen.kg_magazine_c_dualclock_top_margin);
        } else {
            setViewMarginTop(getMiuiClockView(), R.dimen.kg_0dp);
        }
        setViewMarginTop(this.dateView, R.dimen.kg_0dp);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_magazine_c_clock_back_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new MagazineCAutoColorPicker(context, getClockStyleType(), getCurrentClockBean());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        return new MagazineCColorPickerHandler(this, getCurrentClockBean(), this.currentDateClockBean);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int foreContentLayerId() {
        return R.layout.kg_layout_magazine_c_clock_fore_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "magazine_c";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_clock_time_area_frame_margin_top);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_clock_time_area_frame_left);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return CollectionsKt.listOf(BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, new Rect(dimensionPixelSize2, dimensionPixelSize, deviceUtil.currentDisplaySize(context).width() - getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_clock_time_area_frame_right), getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_clock_time_area_frame_height)), CustomMarginType.ALL, new View[0], 0, 16, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int getFilterBottomSheetAlignModel() {
        return 264;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        switch (getCurrentClockBean().getStyle()) {
            case 5:
                return FontFilterKt.getMAGAZINE_C_STYLE_1_FONT_FILTER();
            case 6:
                return FontFilterKt.getMAGAZINE_C_STYLE_2_FONT_FILTER();
            case 7:
                return FontFilterKt.getMAGAZINE_C_STYLE_3_FONT_FILTER();
            case 8:
                return FontFilterKt.getMAGAZINE_C_STYLE_4_FONT_FILTER();
            case 9:
                return FontFilterKt.getMAGAZINE_C_STYLE_5_FONT_FILTER();
            default:
                Log.d("MagazineCTemplateView", "error style=" + getCurrentClockBean().getStyle() + " use default");
                return FontFilterKt.getMAGAZINE_C_STYLE_3_FONT_FILTER();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new MagazineCStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
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
        this.currentDateClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.currentDateClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.currentDateClockBean.setBlendColor(clockInfo.getBlendColor());
        this.currentDateClockBean.setClockEffect(clockInfo.getClockEffect());
        this.currentDateClockBean.setSecondaryColor(clockInfo.getSecondaryColor());
        this.currentDateClockBean.setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        this.currentDateClockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect largeScreenWithNotificationAvoidRect(boolean z) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_hierarchy_notification_avoid_check_top);
        int dimensionPixelSize2 = z ? getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_hierarchy_port_notification_avoid_check_left) : getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_hierarchy_land_notification_avoid_check_left);
        return new Rect(dimensionPixelSize2, dimensionPixelSize, getResources().getDimensionPixelSize(R.dimen.kg_hierarchy_notification_avoid_check_width) + dimensionPixelSize2, getResources().getDimensionPixelSize(R.dimen.kg_hierarchy_notification_avoid_check_height) + dimensionPixelSize);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (v.getId() == R.id.kg_time_area_frame) {
            onClickClockFrame(v);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
        MiuiClockView miuiClockView = this.dateView;
        if (miuiClockView != null) {
            miuiClockView.setClockBean(this.currentDateClockBean);
        }
        updateDateViewVisible();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        FontFilterView clockFontFilterView;
        super.onEdited(i, obj);
        if (i == 131 && (obj instanceof Integer)) {
            int style = getCurrentClockBean().getStyle();
            Number number = (Number) obj;
            getCurrentClockBean().setStyle(number.intValue());
            onClockInfoUpdate();
            if ((obj != null && style == number.intValue()) || (clockFontFilterView = getClockFontFilterView()) == null) {
                return;
            }
            clockFontFilterView.updateFontFilterData(getFontFilter(), getColorModificationTarget());
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        MiuiClockView miuiClockView = (MiuiClockView) findViewById(R.id.date_view);
        Intrinsics.checkNotNull(miuiClockView);
        miuiClockView.setVisibility(!miuiClockView.isDualClock() ? 0 : 8);
        this.dateView = miuiClockView;
        super.onMiuiClockViewCreated(templateConfig);
        if (!isDualClock()) {
            MiuiClockView miuiClockView2 = this.dateView;
            if (miuiClockView2 != null) {
                miuiClockView2.init(this.currentDateClockBean, false);
            }
            updateDateViewVisible();
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

    public final void setViewMarginTop(View view, int i) {
        if (view != null) {
            ViewUtil.setMarginTop(view, getResources().getDimensionPixelSize(i));
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean supportGradientBlur() {
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        int width;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsRtl = deviceUtil.isRtl(context);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_hierarchy_avoid_check_top);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_classic_hierarchy_avoid_check_width);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_classic_hierarchy_avoid_check_height);
        if (zIsRtl) {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            width = deviceUtil.currentDisplaySizeByContext(context2).getWidth() - dimensionPixelSize2;
        } else {
            width = 0;
        }
        return new Rect(width, dimensionPixelSize, dimensionPixelSize2 + width, dimensionPixelSize3 + dimensionPixelSize);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        boolean z = false;
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        MiuiClockView miuiClockView2 = this.dateView;
        if (miuiClockView2 != null) {
            miuiClockView2.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        if (getMiuiClockView() != null && this.dateView != null) {
            z = true;
        }
        setSuccessUpdateClockView(z);
    }

    public final void updateDateViewVisible() {
        MiuiClockView miuiClockView = this.dateView;
        if (miuiClockView != null) {
            miuiClockView.setVisibility(getCurrentClockBean().getStyle() != 5 ? 0 : 8);
        }
    }
}

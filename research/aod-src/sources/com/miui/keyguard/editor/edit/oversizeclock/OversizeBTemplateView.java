package com.miui.keyguard.editor.edit.oversizeclock;

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
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.style.OversizeBStyleEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
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
/* JADX INFO: compiled from: OversizeBTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class OversizeBTemplateView extends BaseClockView implements View.OnClickListener {
    public final ClockBean foreClockBean;
    public MiuiClockView foreMiuiClockView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OversizeBTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.foreClockBean = new ClockBean("oversize_b_hour");
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
        return "oversize_b";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        MiuiClockView miuiClockView = this.foreMiuiClockView;
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_HOUR) : null;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_frame_extra_left);
        int top = iClockView != null ? iClockView.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_frame_extra_top) : 0;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return CollectionsKt.mutableListOf(BaseClockView.getFixationFrameParam$default(this, R.id.clock_frame, new Rect(dimensionPixelSize, top, deviceUtil.currentDisplaySize(context).width() - getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_frame_extra_right), iClockView != null ? iClockView.getHeight() + getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_frame_extra_bottom) : 0), CustomMarginType.ALL, new View[0], 0, 16, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getOVERSIZE_B_FONT_FILTER();
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
            clockStyleEditor = new OversizeBStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
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
        this.foreClockBean.setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        this.foreClockBean.setClockEffect(clockInfo.getClockEffect());
        this.foreClockBean.setStyle(clockInfo.getStyle());
        this.foreClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.foreClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.foreClockBean.setSecondaryColor(clockInfo.getSecondaryColor());
        this.foreClockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
        int i = R.id.clock_frame;
        if (numValueOf != null && numValueOf.intValue() == i) {
            onClickClockFrame(view);
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
            BaseClockView.popClockFontFilterDialog$default(this, R.id.clock_frame, 0, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean supportGradientBlur() {
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_hierarchy_avoid_check_width);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_hierarchy_avoid_check_right)) - dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_hierarchy_avoid_check_top);
        return new Rect(width, dimensionPixelSize2, dimensionPixelSize + width, getResources().getDimensionPixelSize(R.dimen.kg_oversize_b_style_hierarchy_avoid_check_height) + dimensionPixelSize2);
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

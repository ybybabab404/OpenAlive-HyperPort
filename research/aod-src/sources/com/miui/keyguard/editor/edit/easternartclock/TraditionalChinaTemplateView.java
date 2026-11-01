package com.miui.keyguard.editor.edit.easternartclock;

import android.content.Context;
import android.content.res.Resources;
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
import com.miui.keyguard.editor.edit.style.EasternArtCStyleEditor;
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
/* JADX INFO: compiled from: TraditionalChinaTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TraditionalChinaTemplateView extends BaseClockView implements View.OnClickListener, EditCallback {
    public MiuiClockView minuteClock;
    public final ClockBean minuteDateClockBean;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TraditionalChinaTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.minuteDateClockBean = new ClockBean("eastern_c_minute");
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
        return new ColorPickHandler(this, getCurrentClockBean(), this.minuteDateClockBean);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public TraditionalChinaTemplateView editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int foreContentLayerId() {
        return R.layout.kg_layout_clock_fore_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "eastern_c";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        MiuiClockView miuiClockView = this.minuteClock;
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.CLOCK_CONTAINER) : null;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int iWidth = deviceUtil.currentDisplaySize(context).width() / 2;
        Resources resources = getResources();
        int i = R.dimen.kg_eastern_c_style1_frame_extra_width;
        int dimensionPixelSize = iWidth - resources.getDimensionPixelSize(i);
        int top = iClockView != null ? iClockView.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_frame_extra_h1_margin_top) : 0;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        return CollectionsKt.mutableListOf(BaseClockView.getFixationFrameParam$default(this, R.id.clock_frame, new Rect(dimensionPixelSize, top, (deviceUtil.currentDisplaySize(context2).width() / 2) + getResources().getDimensionPixelSize(i), getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_frame_extra_height)), CustomMarginType.ALL, new View[0], 0, 16, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getTRADITIONAL_CHINA_C_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @Nullable
    public MiuiClockView getForeClock() {
        return this.minuteClock;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new EasternArtCStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.minuteClock = (MiuiClockView) templateView.findViewById(R.id.fore_clock);
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
        this.minuteDateClockBean.setBlendColor(clockInfo.getBlendColor());
        this.minuteDateClockBean.setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        this.minuteDateClockBean.setClockEffect(clockInfo.getClockEffect());
        this.minuteDateClockBean.setStyle(clockInfo.getStyle());
        this.minuteDateClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.minuteDateClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.minuteDateClockBean.setSecondaryColor(clockInfo.getSecondaryColor());
        this.minuteDateClockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect largeScreenWithNotificationAvoidRect(boolean z) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_hierarchy_notification_avoid_check_width);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize) / 2;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_hierarchy_notification_avoid_check_top);
        return new Rect(width, dimensionPixelSize2, dimensionPixelSize + width, getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_hierarchy_notification_avoid_check_height) + dimensionPixelSize2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (v.getId() == R.id.clock_frame) {
            inTalkBackCloseDialogs();
            onClickClockFrame(v);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
        MiuiClockView miuiClockView = this.minuteClock;
        if (miuiClockView != null) {
            miuiClockView.setClockBean(this.minuteDateClockBean);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        WallpaperInfo wallpaperInfo;
        super.onEdited(i, obj);
        if (i == 190 && (obj instanceof Integer)) {
            Number number = (Number) obj;
            getCurrentClockBean().setStyle(number.intValue());
            this.minuteDateClockBean.setStyle(number.intValue());
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
        MiuiClockView miuiClockView2 = this.minuteClock;
        if (miuiClockView2 != null) {
            miuiClockView2.setVisibility(!miuiClockView2.isDualClock() ? 0 : 8);
        }
        if (!isDualClock() && (miuiClockView = this.minuteClock) != null) {
            miuiClockView.init(this.minuteDateClockBean, false);
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
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_hierarchy_avoid_check_width);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize) / 2;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_hierarchy_avoid_check_top);
        return new Rect(width, dimensionPixelSize2, dimensionPixelSize + width, getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_hierarchy_avoid_check_height) + dimensionPixelSize2);
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
        MiuiClockView miuiClockView2 = this.minuteClock;
        if (miuiClockView2 != null) {
            miuiClockView2.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        if (getMiuiClockView() != null && this.minuteClock != null) {
            z = true;
        }
        setSuccessUpdateClockView(z);
    }
}

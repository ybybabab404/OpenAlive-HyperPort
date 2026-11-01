package com.miui.keyguard.editor.edit.classicclock;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.clock.ClassicSelectorBean;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.style.ClassicPlusStyleEditor;
import com.miui.keyguard.editor.edit.style.RealtimeStyleEditor;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.view.KgFrame;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
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
/* JADX INFO: compiled from: ClassicPlusTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClassicPlusTemplateView extends AbstractClassicTemplateView {
    public View area1;
    public KgFrame mKgTextAreaFrame;
    public ClassicTextAreaView textAreaView2;
    public MiuiTextGlassView timeAreaView;
    public MiuiTextGlassView timeAreaView2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicPlusTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new AutoColorPicker(context, getClockStyleType(), getCurrentClockBean());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void exitEdite() {
        Editable text;
        EditText signatureAreaView;
        super.exitEdite();
        EditText signatureAreaView2 = getSignatureAreaView();
        if (signatureAreaView2 == null || (text = signatureAreaView2.getText()) == null || text.length() != 0 || (signatureAreaView = getSignatureAreaView()) == null) {
            return;
        }
        signatureAreaView.setVisibility(8);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "classic_plus";
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0076  */
    /* JADX WARN: Code duplicated, block: B:8:0x002b  */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        int iWidth;
        int dimensionPixelSize;
        int top;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            iWidth = (deviceUtil.currentDisplaySize(context).width() / 2) - getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_frame_margin_time_pad_left);
        } else {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context2)) {
                Context context3 = getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                iWidth = (deviceUtil.currentDisplaySize(context3).width() / 2) - getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_frame_margin_time_pad_left);
            } else {
                iWidth = getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_frame_extra_left);
            }
        }
        if (deviceUtil.isPad()) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_frame_pad_extra_width) + iWidth;
        } else {
            Context context4 = getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context4)) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_frame_pad_extra_width) + iWidth;
            } else {
                Context context5 = getContext();
                Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                dimensionPixelSize = deviceUtil.currentDisplaySize(context5).width() - getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_frame_extra_right);
            }
        }
        EditText signatureAreaView = getSignatureAreaView();
        Object parent = signatureAreaView != null ? signatureAreaView.getParent() : null;
        View view = parent instanceof View ? (View) parent : null;
        if (view != null) {
            int top2 = view.getTop();
            View view2 = this.area1;
            top = (top2 - (view2 != null ? view2.getTop() : 0)) - getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_time_area_edite_end_bottom);
        } else {
            top = 0;
        }
        EditText signatureAreaView2 = getSignatureAreaView();
        Object parent2 = signatureAreaView2 != null ? signatureAreaView2.getParent() : null;
        Intrinsics.checkNotNull(parent2, "null cannot be cast to non-null type android.view.View");
        int top3 = ((View) parent2).getTop() - getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_signature_edite_area_margin_top);
        ClassicTextAreaView textAreaView = getTextAreaView();
        Rect rect = new Rect(iWidth, textAreaView != null ? textAreaView.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_time_area_margin_top) : 0, dimensionPixelSize, getResources().getDimensionPixelSize(com.miui.clock.R.dimen.classic_plus_text_area_height) + getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_time_area_margin_bottom));
        View view3 = this.area1;
        Rect rect2 = new Rect(iWidth, view3 != null ? view3.getTop() + getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_time_area_extra_frame_end_top) : 0, dimensionPixelSize, top);
        Rect rect3 = new Rect(iWidth, top3, dimensionPixelSize, getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_signature_area_height));
        ArrayList arrayList = new ArrayList();
        int i = R.id.kg_text_area_frame;
        CustomMarginType customMarginType = CustomMarginType.ALL;
        arrayList.add(BaseClockView.getFixationFrameParam$default(this, i, rect, customMarginType, new View[]{getTextAreaView()}, 0, 16, null));
        arrayList.add(BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, rect2, customMarginType, new View[]{this.textAreaView2}, 0, 16, null));
        arrayList.add(getFixationFrameParam(R.id.kg_signature_area_frame, rect3, customMarginType, new View[]{getSignatureAreaView()}, R.string.kg_classic_signature_label));
        return arrayList;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getCLASSIC_PLUS_TEMPLATE_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    @NotNull
    public String getForeClockStyleType() {
        return "classic_plus_signature";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void getFramesComplete() {
        super.getFramesComplete();
        KgFrame kgFrame = (KgFrame) findViewById(R.id.kg_text_area_frame);
        this.mKgTextAreaFrame = kgFrame;
        if (kgFrame != null) {
            ClassicTextAreaView textAreaView = getTextAreaView();
            CharSequence string = null;
            CharSequence text = textAreaView != null ? textAreaView.getText() : null;
            if (text == null || StringsKt.isBlank(text)) {
                string = getContext().getString(R.string.kg_dialog_no_content_available_the_moment);
            } else {
                ClassicTextAreaView textAreaView2 = getTextAreaView();
                if (textAreaView2 != null) {
                    string = textAreaView2.getText();
                }
            }
            kgFrame.setContentDescription(string);
        }
        setMKgSignatureAreaFrame((KgFrame) findViewById(R.id.kg_signature_area_frame));
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    public boolean isPlusMaxTemplate() {
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onClockBeanChange(ClockBean clockBean, boolean z) {
        super.onClockBeanChange(clockBean, z);
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            MiuiClockView miuiClockView = getMiuiClockView();
            foreMiuiClockView.setBackgroundBlurContainer(miuiClockView != null ? miuiClockView.getBackgroundBlurContainer() : null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            MiuiClockView miuiClockView = getMiuiClockView();
            foreMiuiClockView.setBackgroundBlurContainer(miuiClockView != null ? miuiClockView.getBackgroundBlurContainer() : null);
        }
        MiuiClockView foreMiuiClockView2 = getForeMiuiClockView();
        if (foreMiuiClockView2 != null) {
            foreMiuiClockView2.setClockBean(getForeClockBean());
        }
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        HierarchyImageAvoidController avoidController;
        super.onEdited(i, obj);
        if ((obj instanceof ClassicSelectorBean) && i == 100) {
            getCurrentClockBean().setClassicLine1(((ClassicSelectorBean) obj).getStyle());
            onClockInfoUpdate();
            ClassicTextAreaView textAreaView = getTextAreaView();
            CharSequence text = textAreaView != null ? textAreaView.getText() : null;
            if (text == null || text.length() == 0) {
                KgFrame kgFrame = this.mKgTextAreaFrame;
                if (kgFrame != null) {
                    kgFrame.setImportantForAccessibility(2);
                }
            } else {
                KgFrame kgFrame2 = this.mKgTextAreaFrame;
                if (kgFrame2 != null) {
                    kgFrame2.setImportantForAccessibility(1);
                }
                KgFrame kgFrame3 = this.mKgTextAreaFrame;
                if (kgFrame3 != null) {
                    ClassicTextAreaView textAreaView2 = getTextAreaView();
                    kgFrame3.setContentDescription(textAreaView2 != null ? textAreaView2.getText() : null);
                }
            }
        }
        if (i == 20) {
            RealtimeStyleEditor realtimeEditor = getRealtimeEditor();
            ClassicPlusStyleEditor classicPlusStyleEditor = realtimeEditor instanceof ClassicPlusStyleEditor ? (ClassicPlusStyleEditor) realtimeEditor : null;
            if (classicPlusStyleEditor != null) {
                classicPlusStyleEditor.updateClockStyle(getCurrentClockBean().getStyle());
                return;
            }
            return;
        }
        if (i != 110) {
            return;
        }
        ClockBean currentClockBean = getCurrentClockBean();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        currentClockBean.setClassicLine2(((Integer) obj).intValue());
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        if (hierarchyImageView != null && (avoidController = hierarchyImageView.getAvoidController()) != null) {
            avoidController.resetAvoidRect(hierarchyAvoidRect());
        }
        onClockInfoUpdate();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        MiuiClockView foreMiuiClockView;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        MiuiClockView foreMiuiClockView2 = getForeMiuiClockView();
        if (foreMiuiClockView2 != null) {
            foreMiuiClockView2.setVisibility(!foreMiuiClockView2.isDualClock() ? 0 : 8);
        }
        MiuiClockView foreMiuiClockView3 = getForeMiuiClockView();
        if (foreMiuiClockView3 != null) {
            MiuiClockView miuiClockView = getMiuiClockView();
            foreMiuiClockView3.setBackgroundBlurContainer(miuiClockView != null ? miuiClockView.getBackgroundBlurContainer() : null);
        }
        if (!isDualClock() && (foreMiuiClockView = getForeMiuiClockView()) != null) {
            foreMiuiClockView.init(getForeClockBean(), false);
        }
        MiuiClockView miuiClockView2 = getMiuiClockView();
        View iClockView = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.TEXT_AREA) : null;
        setTextAreaView(iClockView instanceof ClassicTextAreaView ? (ClassicTextAreaView) iClockView : null);
        MiuiClockView miuiClockView3 = getMiuiClockView();
        View iClockView2 = miuiClockView3 != null ? miuiClockView3.getIClockView(ClockViewType.TEXT_AREA2) : null;
        this.textAreaView2 = iClockView2 instanceof ClassicTextAreaView ? (ClassicTextAreaView) iClockView2 : null;
        MiuiClockView miuiClockView4 = getMiuiClockView();
        View iClockView3 = miuiClockView4 != null ? miuiClockView4.getIClockView(ClockViewType.TIME_AREA) : null;
        this.timeAreaView = iClockView3 instanceof MiuiTextGlassView ? (MiuiTextGlassView) iClockView3 : null;
        MiuiClockView miuiClockView5 = getMiuiClockView();
        View iClockView4 = miuiClockView5 != null ? miuiClockView5.getIClockView(ClockViewType.TIME_AREA2) : null;
        this.timeAreaView2 = iClockView4 instanceof MiuiTextGlassView ? (MiuiTextGlassView) iClockView4 : null;
        this.area1 = findViewById(com.miui.clock.R.id.area1);
        setClickView(findViewById(R.id.click_view));
        if (!getSuccessUpdateClockView()) {
            completeWithCacheColor(false);
        }
        super.onMiuiClockViewCreated(templateConfig);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetAllBottomSheet() {
        super.resetAllBottomSheet();
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        keyboardUtils.hideSoftInput(context);
        EditText signatureAreaView = getSignatureAreaView();
        if (signatureAreaView != null) {
            signatureAreaView.clearFocus();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int dimensionPixelSize3;
        ClockInfo clockInfo;
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null || clockInfo.getClassicLine2() != 71) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_hierarchy_double_avoid_check_top);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_hierarchy_double_avoid_check_width);
            dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_hierarchy_double_avoid_check_height);
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_line2_same_line_hierarchy_avoid_check_top);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_line2_same_line_hierarchy_avoid_check_width);
            dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_line2_same_line_hierarchy_avoid_check_height);
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize2) / 2;
        return new Rect(width, dimensionPixelSize, dimensionPixelSize2 + width, dimensionPixelSize3 + dimensionPixelSize);
    }
}

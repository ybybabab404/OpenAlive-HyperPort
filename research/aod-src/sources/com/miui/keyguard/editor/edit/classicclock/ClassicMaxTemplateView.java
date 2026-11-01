package com.miui.keyguard.editor.edit.classicclock;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.clock.ClassicSelectorBean;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.view.KgFrame;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
/* JADX INFO: compiled from: ClassicMaxTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClassicMaxTemplateView extends AbstractClassicTemplateView {
    public View containerView;
    public KgFrame mKgTextAreaFrame;
    public View mTimeAreaView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicMaxTemplateView(@NotNull Context context) {
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
    @NotNull
    public String getClockStyleType() {
        return "classic_max";
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0075  */
    /* JADX WARN: Code duplicated, block: B:8:0x002a  */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        int iWidth;
        int dimensionPixelSize;
        int top;
        int bottom;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            iWidth = (deviceUtil.currentDisplaySize(context).width() / 2) - getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_frame_margin_time_pad_left);
        } else {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context2)) {
                Context context3 = getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                iWidth = (deviceUtil.currentDisplaySize(context3).width() / 2) - getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_frame_margin_time_pad_left);
            } else {
                iWidth = getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_style_frame_extra_left);
            }
        }
        if (deviceUtil.isPad()) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_frame_pad_extra_width) + iWidth;
        } else {
            Context context4 = getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context4)) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_frame_pad_extra_width) + iWidth;
            } else {
                Context context5 = getContext();
                Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                dimensionPixelSize = deviceUtil.currentDisplaySize(context5).width() - getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_style_frame_extra_right);
            }
        }
        View view = this.containerView;
        Integer numValueOf = view != null ? Integer.valueOf(view.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_style_frame_time_extra_top)) : null;
        int iIntValue = numValueOf != null ? numValueOf.intValue() - getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_text_area_margin_top) : 0;
        ClassicTextAreaView textAreaView = getTextAreaView();
        Rect rect = new Rect(iWidth, iIntValue, dimensionPixelSize, textAreaView != null ? textAreaView.getBottom() + getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_text_area_height) : 0);
        if (numValueOf != null) {
            int iIntValue2 = numValueOf.intValue();
            View view2 = this.mTimeAreaView;
            top = iIntValue2 + (view2 != null ? view2.getTop() : 0) + getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_time_area_margin_top);
        } else {
            top = 0;
        }
        View view3 = this.mTimeAreaView;
        Rect rect2 = new Rect(iWidth, top, dimensionPixelSize, view3 != null ? view3.getHeight() - getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_time_area_margin_bottom) : 0);
        if (numValueOf != null) {
            int iIntValue3 = numValueOf.intValue();
            View view4 = this.mTimeAreaView;
            bottom = (iIntValue3 + (view4 != null ? view4.getBottom() : 0)) - getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_signature_area_margin_top);
        } else {
            bottom = 0;
        }
        Rect rect3 = new Rect(iWidth, bottom, dimensionPixelSize, getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_signature_area_height));
        int i = R.id.kg_text_area_frame;
        CustomMarginType customMarginType = CustomMarginType.ALL;
        return CollectionsKt.mutableListOf(BaseClockView.getFixationFrameParam$default(this, i, rect, customMarginType, new View[]{getTextAreaView()}, 0, 16, null), BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, rect2, customMarginType, new View[]{this.mTimeAreaView}, 0, 16, null), getFixationFrameParam(R.id.kg_signature_area_frame, rect3, customMarginType, new View[]{this.mTimeAreaView}, R.string.kg_classic_signature_label));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getCLASSIC_MAX_TEMPLATE_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    @NotNull
    public String getForeClockStyleType() {
        return "classic_max_minute";
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
            ClassicSelectorBean classicSelectorBean = (ClassicSelectorBean) obj;
            getForeClockBean().setClassicLine1(classicSelectorBean.getStyle());
            getCurrentClockBean().setClassicLine1(classicSelectorBean.getStyle());
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
            ClockBean foreClockBean = getForeClockBean();
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            foreClockBean.setStyle(((Integer) obj).intValue());
            onClockInfoUpdate();
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
        MiuiClockView miuiClockView = getMiuiClockView();
        setTextAreaView((ClassicTextAreaView) (miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.TEXT_AREA) : null));
        MiuiClockView miuiClockView2 = getMiuiClockView();
        this.containerView = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.CLOCK_CONTAINER) : null;
        MiuiClockView miuiClockView3 = getMiuiClockView();
        this.mTimeAreaView = miuiClockView3 != null ? miuiClockView3.getIClockView(ClockViewType.TIME_AREA) : null;
        MiuiClockView foreMiuiClockView3 = getForeMiuiClockView();
        if (foreMiuiClockView3 != null) {
            MiuiClockView miuiClockView4 = getMiuiClockView();
            foreMiuiClockView3.setBackgroundBlurContainer(miuiClockView4 != null ? miuiClockView4.getBackgroundBlurContainer() : null);
        }
        if (!isDualClock() && (foreMiuiClockView = getForeMiuiClockView()) != null) {
            foreMiuiClockView.init(getForeClockBean(), false);
        }
        if (!getSuccessUpdateClockView()) {
            completeWithCacheColor(false);
        }
        setClickView(findViewById(R.id.click_view));
        super.onMiuiClockViewCreated(templateConfig);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetAllBottomSheet() {
        super.resetAllBottomSheet();
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        keyboardUtils.hideSoftInput(context);
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame != null) {
            mKgSignatureAreaFrame.setSelected(false);
        }
        EditText signatureAreaView = getSignatureAreaView();
        if (signatureAreaView != null) {
            signatureAreaView.clearFocus();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_hierarchy_avoid_check_width);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_hierarchy_avoid_check_height);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_hierarchy_avoid_check_left);
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.kg_classic_max_clock_hierarchy_avoid_check_top);
        return new Rect(dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize + dimensionPixelSize3, dimensionPixelSize2 + dimensionPixelSize4);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Set unSupportHierarchyTypes() {
        return hierarchyWithNotificationTypes();
    }
}

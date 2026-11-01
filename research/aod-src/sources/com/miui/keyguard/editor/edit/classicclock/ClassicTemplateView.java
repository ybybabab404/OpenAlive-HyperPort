package com.miui.keyguard.editor.edit.classicclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.widget.NestedScrollView;
import com.miui.clock.MiuiClockView;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.classic.ClassicClockView;
import com.miui.clock.classic.ClassicContentAreaView;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.clock.ClassicSelectorBean;
import com.miui.keyguard.editor.edit.clock.ClassicTextStyleEditor;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.style.RealtimeStyleEditor;
import com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.KgFrame;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
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
/* JADX INFO: compiled from: ClassicTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClassicTemplateView extends AbstractClassicTemplateView {
    public View area1;
    public ClassicContentAreaView contentAreaView1;
    public ClassicContentAreaView contentAreaView2;
    public View line3;
    public View line4;
    public KgFrame mTextAreaFrame;
    public KgFrame mTimeAreaFrame;
    public MiuiTextGlassView timeAreaView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final View addDialogView(LinearLayout linearLayout, ClockViewType clockViewType) {
        setRealtimeEditor(new ClassicTextStyleEditor(this, this, clockViewType, getCurrentClockBean(), getStyleEditorFontColor(), clockViewType == ClockViewType.TEXT_AREA, getCurrentClockBean().getStyle() == 0, !isPlusMaxTemplate(), getFontStyleEditCallback()));
        RealtimeStyleEditor realtimeEditor = getRealtimeEditor();
        Intrinsics.checkNotNull(realtimeEditor, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.clock.ClassicTextStyleEditor");
        EditorDialogNoTitleBarView container = ((ClassicTextStyleEditor) realtimeEditor).getContainer();
        if (container != null) {
            container.setOnCloseDialogListener(this);
            container.setTitleVisible(false);
        } else {
            container = null;
        }
        linearLayout.addView(container);
        return linearLayout;
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyPrimaryMode() {
        super.applyPrimaryMode();
        setContentLinesVisibility(getCurrentClockBean().getClassicLine3() != 0, getCurrentClockBean().getClassicLine4() != 0);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applySecondaryMode() {
        setContentLinesVisibility(true, true);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_classic_clock_back_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new AutoColorPicker(context, getClockStyleType(), getCurrentClockBean());
    }

    public final void createDialogView(LinearLayout linearLayout, ClockViewType clockViewType) {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        EditorDialogNoTitleBarView editorDialogNoTitleBarView = new EditorDialogNoTitleBarView(context, null, 2, null);
        String string = editorDialogNoTitleBarView.getContext().getString(R.string.kg_function_text_omponent_contents);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        editorDialogNoTitleBarView.setTitle(string);
        editorDialogNoTitleBarView.setOnCloseDialogListener(new EditorDialogNoTitleBarView.OnCloseDialogListener() { // from class: com.miui.keyguard.editor.edit.classicclock.ClassicTemplateView$createDialogView$1$1
            @Override // com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView.OnCloseDialogListener
            public void closeDialog() {
                AutoBottomSheet mTimeContentBottomSheetModal = this.this$0.getMTimeContentBottomSheetModal();
                if (mTimeContentBottomSheetModal != null) {
                    mTimeContentBottomSheetModal.dismiss();
                }
            }
        });
        editorDialogNoTitleBarView.setOrientation(1);
        setMEditorDialogNoTitleBarView(editorDialogNoTitleBarView);
        linearLayout.addView(getMEditorDialogNoTitleBarView());
        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
        nestedScrollView.setLayoutParams(new FrameLayout.LayoutParams(dialogWidth(), dialogHeight()));
        LinearLayout linearLayout2 = new LinearLayout(nestedScrollView.getContext());
        linearLayout2.setOrientation(1);
        Unit unit = Unit.INSTANCE;
        nestedScrollView.addView(addDialogView(linearLayout2, clockViewType));
        linearLayout.addView(nestedScrollView);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    public final void ensureContentAreaAndSignatureVisible() {
        if (editable()) {
            setContentLinesVisibility(true, true);
            EditText signatureAreaView = getSignatureAreaView();
            if (signatureAreaView != null) {
                signatureAreaView.setVisibility(0);
            }
        }
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
        return "classic";
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0095 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:20:0x0097  */
    /* JADX WARN: Code duplicated, block: B:21:0x00ab  */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Integer numValueOf;
        Integer numValueOf2;
        ClassicTemplateView classicTemplateView;
        int top;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (deviceUtil.isRtl(context)) {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            int iWidth = deviceUtil.currentDisplaySize(context2).width();
            ClassicTextAreaView textAreaView = getTextAreaView();
            numValueOf = Integer.valueOf(iWidth - (textAreaView != null ? textAreaView.getRight() + getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_area_frame_margin_left) : 0));
        } else {
            ClassicTextAreaView textAreaView2 = getTextAreaView();
            numValueOf = textAreaView2 != null ? Integer.valueOf(textAreaView2.getLeft() - getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_area_frame_margin_left)) : null;
        }
        if (!deviceUtil.isPad()) {
            Context context3 = getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            if (!deviceUtil.isFoldLargeScreen(context3)) {
                Context context4 = getContext();
                Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
                numValueOf2 = Integer.valueOf(deviceUtil.currentDisplaySize(context4).width() - getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_frame_extra_right));
            } else if (numValueOf != null) {
                numValueOf2 = Integer.valueOf(numValueOf.intValue() + getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_frame_pad_extra_width));
            } else {
                numValueOf2 = null;
            }
        } else if (numValueOf != null) {
            numValueOf2 = Integer.valueOf(numValueOf.intValue() + getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_frame_pad_extra_width));
        } else {
            numValueOf2 = null;
        }
        EditText signatureAreaView = getSignatureAreaView();
        Object parent = signatureAreaView != null ? signatureAreaView.getParent() : null;
        View view = parent instanceof View ? (View) parent : null;
        Integer numValueOf3 = view != null ? Integer.valueOf(view.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_signature_margin_time_top)) : null;
        int iIntValue = numValueOf != null ? numValueOf.intValue() : 0;
        ClassicTextAreaView textAreaView3 = getTextAreaView();
        Rect rect = new Rect(iIntValue, textAreaView3 != null ? textAreaView3.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_time_area_margin_top) : 0, numValueOf2 != null ? numValueOf2.intValue() : 0, getResources().getDimensionPixelSize(com.miui.clock.R.dimen.classic_plus_text_area_height) + getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_time_area_margin_bottom));
        int iIntValue2 = numValueOf != null ? numValueOf.intValue() : 0;
        ClassicTextAreaView textAreaView4 = getTextAreaView();
        int bottom = textAreaView4 != null ? textAreaView4.getBottom() + getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_time_area_frame_extra_bottom) : 0;
        int iIntValue3 = numValueOf2 != null ? numValueOf2.intValue() : 0;
        EditText signatureAreaView2 = getSignatureAreaView();
        ViewParent parent2 = signatureAreaView2 != null ? signatureAreaView2.getParent() : null;
        View view2 = parent2 instanceof View ? (View) parent2 : null;
        if (view2 != null) {
            int top2 = view2.getTop();
            classicTemplateView = this;
            View view3 = classicTemplateView.area1;
            top = (top2 - (view3 != null ? view3.getTop() : 0)) - classicTemplateView.getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_style_signature_padding_time_extra);
        } else {
            classicTemplateView = this;
            top = 0;
        }
        Rect rect2 = new Rect(iIntValue2, bottom, iIntValue3, top);
        Rect rect3 = new Rect(numValueOf != null ? numValueOf.intValue() : 0, numValueOf3 != null ? numValueOf3.intValue() : 0, numValueOf2 != null ? numValueOf2.intValue() : 0, classicTemplateView.getResources().getDimensionPixelSize(R.dimen.kg_classic_plus_clock_signature_area_height));
        ArrayList arrayList = new ArrayList();
        int i = R.id.kg_text_area_frame;
        CustomMarginType customMarginType = CustomMarginType.ALL;
        arrayList.add(BaseClockView.getFixationFrameParam$default(classicTemplateView, i, rect, customMarginType, new View[0], 0, 16, null));
        arrayList.add(BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, rect2, customMarginType, new View[0], 0, 16, null));
        arrayList.add(getFixationFrameParam(R.id.kg_signature_area_frame, rect3, customMarginType, new View[0], R.string.kg_classic_signature_label));
        return arrayList;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getCLASSIC_TEMPLATE_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    @NotNull
    public String getForeClockStyleType() {
        return "classic_signature";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @SuppressLint({"ClickableViewAccessibility"})
    public void getFramesComplete() {
        super.getFramesComplete();
        setMKgSignatureAreaFrame((KgFrame) findViewById(R.id.kg_signature_area_frame));
        KgFrame kgFrame = (KgFrame) findViewById(R.id.kg_text_area_frame);
        this.mTextAreaFrame = kgFrame;
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
        this.mTimeAreaFrame = (KgFrame) findViewById(R.id.kg_time_area_frame);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    public boolean isPlusMaxTemplate() {
        return false;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect largeScreenWithNotificationAvoidRect(boolean z) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_classic_hierarchy_notification_avoid_check_top);
        int dimensionPixelSize2 = z ? getResources().getDimensionPixelSize(R.dimen.kg_classic_hierarchy_port_notification_avoid_check_left) : getResources().getDimensionPixelSize(R.dimen.kg_classic_hierarchy_land_notification_avoid_check_left);
        return new Rect(dimensionPixelSize2, dimensionPixelSize, getResources().getDimensionPixelSize(R.dimen.kg_hierarchy_notification_avoid_check_width) + dimensionPixelSize2, getResources().getDimensionPixelSize(R.dimen.kg_hierarchy_notification_avoid_check_height) + dimensionPixelSize);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
        ensureContentAreaAndSignatureVisible();
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
        super.onEdited(i, obj);
        if (obj instanceof ClassicSelectorBean) {
            switch (i) {
                case 100:
                    getCurrentClockBean().setClassicLine1(((ClassicSelectorBean) obj).getStyle());
                    break;
                case 101:
                    getCurrentClockBean().setClassicLine2(((ClassicSelectorBean) obj).getStyle());
                    break;
                case 102:
                    getCurrentClockBean().setClassicLine3(((ClassicSelectorBean) obj).getStyle());
                    break;
                case 103:
                    getCurrentClockBean().setClassicLine4(((ClassicSelectorBean) obj).getStyle());
                    break;
            }
            MiuiClockView miuiClockView = getMiuiClockView();
            if (miuiClockView != null) {
                miuiClockView.setClockBean(getCurrentClockBean());
            }
            ClassicTextAreaView textAreaView = getTextAreaView();
            CharSequence text = textAreaView != null ? textAreaView.getText() : null;
            if (text == null || text.length() == 0) {
                KgFrame kgFrame = this.mTextAreaFrame;
                if (kgFrame != null) {
                    kgFrame.setImportantForAccessibility(2);
                }
            } else {
                KgFrame kgFrame2 = this.mTextAreaFrame;
                if (kgFrame2 != null) {
                    kgFrame2.setImportantForAccessibility(1);
                }
                KgFrame kgFrame3 = this.mTextAreaFrame;
                if (kgFrame3 != null) {
                    ClassicTextAreaView textAreaView2 = getTextAreaView();
                    kgFrame3.setContentDescription(textAreaView2 != null ? textAreaView2.getText() : null);
                }
            }
            ensureContentAreaAndSignatureVisible();
        }
        if (i == 20) {
            RealtimeStyleEditor realtimeEditor = getRealtimeEditor();
            ClassicTextStyleEditor classicTextStyleEditor = realtimeEditor instanceof ClassicTextStyleEditor ? (ClassicTextStyleEditor) realtimeEditor : null;
            if (classicTextStyleEditor != null) {
                classicTextStyleEditor.updateClockStyle();
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        MiuiClockView foreMiuiClockView;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        if (!isDualClock() && (foreMiuiClockView = getForeMiuiClockView()) != null) {
            foreMiuiClockView.init(getForeClockBean(), false);
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.TEXT_AREA) : null;
        setTextAreaView(iClockView instanceof ClassicTextAreaView ? (ClassicTextAreaView) iClockView : null);
        this.area1 = findViewById(com.miui.clock.R.id.area1);
        MiuiClockView miuiClockView2 = getMiuiClockView();
        View iClockView2 = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.TIME_AREA) : null;
        this.timeAreaView = iClockView2 instanceof MiuiTextGlassView ? (MiuiTextGlassView) iClockView2 : null;
        MiuiClockView miuiClockView3 = getMiuiClockView();
        View iClockView3 = miuiClockView3 != null ? miuiClockView3.getIClockView(ClockViewType.CONTENT_AREA1) : null;
        this.contentAreaView1 = iClockView3 instanceof ClassicContentAreaView ? (ClassicContentAreaView) iClockView3 : null;
        MiuiClockView miuiClockView4 = getMiuiClockView();
        View iClockView4 = miuiClockView4 != null ? miuiClockView4.getIClockView(ClockViewType.CONTENT_AREA2) : null;
        this.contentAreaView2 = iClockView4 instanceof ClassicContentAreaView ? (ClassicContentAreaView) iClockView4 : null;
        if (editable()) {
            MiuiClockView miuiClockView5 = getMiuiClockView();
            if (miuiClockView5 != null) {
                miuiClockView5.setEditMode(true);
            }
            MiuiClockView foreMiuiClockView2 = getForeMiuiClockView();
            View iClockView5 = foreMiuiClockView2 != null ? foreMiuiClockView2.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
            setSignatureAreaView(iClockView5 instanceof EditText ? (EditText) iClockView5 : null);
        }
        View clock = getClock();
        this.line3 = clock != null ? clock.findViewById(com.miui.clock.R.id.line3) : null;
        View clock2 = getClock();
        this.line4 = clock2 != null ? clock2.findViewById(com.miui.clock.R.id.line4) : null;
        setClickView(findViewById(R.id.click_view));
        setSignatureTextHintVisible();
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
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame != null) {
            mKgSignatureAreaFrame.setSelected(false);
        }
        EditText signatureAreaView = getSignatureAreaView();
        if (signatureAreaView != null) {
            signatureAreaView.clearFocus();
        }
    }

    public final void setContentLinesVisibility(boolean z, boolean z2) {
        View clock = getClock();
        ClassicClockView classicClockView = clock instanceof ClassicClockView ? (ClassicClockView) clock : null;
        if (classicClockView != null) {
            classicClockView.setUserDefineShowLine3(z);
            classicClockView.setUserDefineShowLine4(z2);
            View view = this.line3;
            if (view != null) {
                view.setVisibility(z ? 0 : 8);
            }
            View view2 = this.line4;
            if (view2 != null) {
                view2.setVisibility(z2 ? 0 : 8);
            }
        }
    }

    public final void setSignatureTextHintVisible() {
        EditText signatureAreaView;
        if (TextUtils.isEmpty(getCurrentClockBean().getClassicSignature()) && editable() && (signatureAreaView = getSignatureAreaView()) != null) {
            signatureAreaView.setVisibility(0);
        }
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    public void showComponentView(View anchor) {
        ClassicTemplateView classicTemplateView;
        View view;
        AutoBottomSheet mTimeContentBottomSheetModal;
        View anchorView;
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        super.showComponentView(anchor);
        if (getMTimeContentBottomSheetModal() == null) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(1);
            createDialogView(linearLayout, ClockViewType.TIME_AREA);
            classicTemplateView = this;
            view = anchor;
            classicTemplateView.setMTimeContentBottomSheetModal(classicTemplateView.createBottomSheet(view, 1280, 0, 0, 0, sheetMarginBottom()));
            AutoBottomSheet mTimeContentBottomSheetModal2 = classicTemplateView.getMTimeContentBottomSheetModal();
            if (mTimeContentBottomSheetModal2 != null) {
                mTimeContentBottomSheetModal2.setContentView(linearLayout);
            }
        } else {
            classicTemplateView = this;
            view = anchor;
        }
        AutoBottomSheet mTimeContentBottomSheetModal3 = classicTemplateView.getMTimeContentBottomSheetModal();
        View anchorView2 = mTimeContentBottomSheetModal3 != null ? mTimeContentBottomSheetModal3.getAnchorView() : null;
        AutoBottomSheet clockCustomFontColorBottomSheetModal = classicTemplateView.getClockCustomFontColorBottomSheetModal();
        if (!Intrinsics.areEqual(anchorView2, clockCustomFontColorBottomSheetModal != null ? clockCustomFontColorBottomSheetModal.getAnchorView() : null) && (mTimeContentBottomSheetModal = classicTemplateView.getMTimeContentBottomSheetModal()) != null) {
            AutoBottomSheet clockCustomFontColorBottomSheetModal2 = classicTemplateView.getClockCustomFontColorBottomSheetModal();
            if (clockCustomFontColorBottomSheetModal2 == null || (anchorView = clockCustomFontColorBottomSheetModal2.getAnchorView()) == null) {
                anchorView = view;
            }
            AutoBottomSheet clockCustomFontColorBottomSheetModal3 = classicTemplateView.getClockCustomFontColorBottomSheetModal();
            AutoBottomSheet anchorView3 = mTimeContentBottomSheetModal.setAnchorView(anchorView, clockCustomFontColorBottomSheetModal3 != null ? clockCustomFontColorBottomSheetModal3.getAlignMode() : 1280);
            if (anchorView3 != null) {
                anchorView3.setMargin(0, 0, 0, classicTemplateView.sheetMarginBottom());
            }
        }
        EditorDialogNoTitleBarView mEditorDialogNoTitleBarView = classicTemplateView.getMEditorDialogNoTitleBarView();
        if (mEditorDialogNoTitleBarView != null) {
            mEditorDialogNoTitleBarView.sendAccessibilityEvent();
        }
        AutoBottomSheet mTimeContentBottomSheetModal4 = classicTemplateView.getMTimeContentBottomSheetModal();
        if (mTimeContentBottomSheetModal4 != null) {
            mTimeContentBottomSheetModal4.show();
        }
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
}

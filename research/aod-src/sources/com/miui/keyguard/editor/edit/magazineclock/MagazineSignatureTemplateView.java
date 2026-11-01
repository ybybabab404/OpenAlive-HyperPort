package com.miui.keyguard.editor.edit.magazineclock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.handler.MagazineABColorPickerHandler;
import com.miui.keyguard.editor.edit.signature.MagazineABSignatureEditor;
import com.miui.keyguard.editor.edit.signature.SignatureEditor;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.KgFrame;
import com.miui.keyguard.editor.view.MiuiClockViewSafeHolder;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.lockscreeninfo.LockScreenInfoLayout;
import com.miui.lockscreeninfo.model.ModelFactory;
import com.miui.lockscreeninfo.model.SignatureInfo;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.bottomsheet.BottomSheetModal;
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
/* JADX INFO: compiled from: MagazineSignatureTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public abstract class MagazineSignatureTemplateView extends BaseClockView {
    public View clockContainer;
    public SignatureInfo currentSignatureInfo;
    public KgFrame signatureAreaFrame;
    public LockScreenInfoLayout signatureView;
    public boolean updateSignatureSuccess;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineSignatureTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        SignatureInfo signatureInfoCreateModel = ModelFactory.createModel(getClockStyleType());
        Intrinsics.checkNotNullExpressionValue(signatureInfoCreateModel, "createModel(...)");
        this.currentSignatureInfo = signatureInfoCreateModel;
    }

    public static final void onMiuiClockViewCreated$lambda$8(MagazineSignatureTemplateView magazineSignatureTemplateView, MiuiClockView miuiClockView) {
        ViewGroup viewGroup = (ViewGroup) magazineSignatureTemplateView.getBackContentLayer();
        if (viewGroup != null) {
            viewGroup.addView(miuiClockView);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyTemplateInfo(TemplateConfig templateConfig) {
        com.miui.keyguard.editor.data.bean.SignatureInfo signatureInfo;
        super.applyTemplateInfo(templateConfig);
        if (templateConfig == null || (signatureInfo = templateConfig.getSignatureInfo()) == null) {
            return;
        }
        signatureInfo.setTemplateId(this.currentSignatureInfo.getTemplateId());
        signatureInfo.setClockEffect(this.currentSignatureInfo.getClockEffect());
        signatureInfo.setContent(this.currentSignatureInfo.getContent());
        signatureInfo.setAlignment(this.currentSignatureInfo.getAlignment());
        signatureInfo.setPrimaryColor(this.currentSignatureInfo.getPrimaryColor());
        signatureInfo.setBlendColor(this.currentSignatureInfo.getBlendColor());
        signatureInfo.setAutoPrimaryColor(this.currentSignatureInfo.isAutoPrimaryColor());
        signatureInfo.setTextSize(this.currentSignatureInfo.getTextsize());
        signatureInfo.setLetterSpaceValue(this.currentSignatureInfo.getAlignment() == 106 ? 0.0f : this.currentSignatureInfo.getLetterSpaceValue());
        signatureInfo.setLineSpaceValue(this.currentSignatureInfo.getLineSpaceValue());
        signatureInfo.setTemplateFontType(this.currentSignatureInfo.getTemplateFontType());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        return new MagazineABColorPickerHandler(this, getCurrentClockBean(), this.currentSignatureInfo);
    }

    @Nullable
    public final View getClockContainer() {
        return this.clockContainer;
    }

    @NotNull
    public final SignatureInfo getCurrentSignatureInfo() {
        return this.currentSignatureInfo;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getMAGAZINE_A_B_TEMPLATE_FONT_FILTER();
    }

    @Nullable
    public final MagazineABSignatureEditor getMagazineABSignatureEditor() {
        SignatureEditor signatureStyleEditor = getSignatureStyleEditor();
        if (signatureStyleEditor instanceof MagazineABSignatureEditor) {
            return (MagazineABSignatureEditor) signatureStyleEditor;
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public MiuiClockViewSafeHolder getMiuiClockViewSafeHolder() {
        ClockInfo clockInfo;
        TemplateConfig templateConfig = templateConfig();
        return new MiuiClockViewSafeHolder((templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) ? null : clockInfo.getTemplateId(), new WeakReference(getMiuiClockView()), null, this.currentSignatureInfo, 4, null);
    }

    @Nullable
    public final KgFrame getSignatureAreaFrame() {
        return this.signatureAreaFrame;
    }

    @Nullable
    public final LockScreenInfoLayout getSignatureView() {
        return this.signatureView;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initClock(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initClock(templateConfig);
        this.signatureView = (LockScreenInfoLayout) findViewById(R.id.signature_view);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        com.miui.keyguard.editor.data.bean.SignatureInfo signatureInfo = templateConfig.getSignatureInfo();
        if (signatureInfo != null) {
            this.currentSignatureInfo.setContent(signatureInfo.getContent());
            this.currentSignatureInfo.setAlignment(signatureInfo.getAlignment());
            this.currentSignatureInfo.setPrimaryColor(signatureInfo.getPrimaryColor());
            this.currentSignatureInfo.setAutoPrimaryColor(signatureInfo.isAutoPrimaryColor());
            this.currentSignatureInfo.setTextsize(signatureInfo.getTextSize());
            this.currentSignatureInfo.setLetterSpaceValue(signatureInfo.getLetterSpaceValue());
            this.currentSignatureInfo.setLineSpaceValue(signatureInfo.getLineSpaceValue());
            this.currentSignatureInfo.setTemplateFontType(signatureInfo.getTemplateFontType());
            this.currentSignatureInfo.setBlendColor(signatureInfo.getBlendColor());
            this.currentSignatureInfo.setClockEffect(signatureInfo.getClockEffect());
        }
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
    public void onClickClockFrame(View anchor) {
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        FontFilterView clockFontFilterView = getClockFontFilterView();
        if (clockFontFilterView != null) {
            clockFontFilterView.refreshFilterTitle();
        }
        super.onClickClockFrame(anchor);
    }

    public void onClickSignatureFrame(View anchor) {
        final MagazineSignatureTemplateView magazineSignatureTemplateView;
        View view;
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        resetPrimaryButtonBar();
        if (getSignatureSheetModal() == null) {
            magazineSignatureTemplateView = this;
            view = anchor;
            magazineSignatureTemplateView.setSignatureSheetModal(createBottomSheet(anchor, 1280, 0, 0, 0, sheetMarginBottom()));
        } else {
            magazineSignatureTemplateView = this;
            view = anchor;
        }
        if (magazineSignatureTemplateView.getSignatureCustomColorBottomSheetModal() == null) {
            magazineSignatureTemplateView.setSignatureCustomColorBottomSheetModal(magazineSignatureTemplateView.createBottomSheet(view, 1280, 0, 0, 0, magazineSignatureTemplateView.sheetMarginBottom()));
        }
        AutoBottomSheet signatureSheetModal = magazineSignatureTemplateView.getSignatureSheetModal();
        if (signatureSheetModal != null) {
            signatureSheetModal.setContentView(magazineSignatureTemplateView.getSignatureStyleEditorView(view));
        }
        MagazineABSignatureEditor magazineABSignatureEditor = magazineSignatureTemplateView.getMagazineABSignatureEditor();
        if (magazineABSignatureEditor != null) {
            magazineABSignatureEditor.setHostBottomSheet(magazineSignatureTemplateView.getSignatureSheetModal());
        }
        AutoBottomSheet signatureSheetModal2 = magazineSignatureTemplateView.getSignatureSheetModal();
        if (signatureSheetModal2 != null) {
            MagazineABSignatureEditor magazineABSignatureEditor2 = magazineSignatureTemplateView.getMagazineABSignatureEditor();
            signatureSheetModal2.setEdits(CollectionsKt.listOf(magazineABSignatureEditor2 != null ? magazineABSignatureEditor2.getMSignatureEditeTextView() : null));
        }
        AutoBottomSheet signatureSheetModal3 = magazineSignatureTemplateView.getSignatureSheetModal();
        if (signatureSheetModal3 != null) {
            signatureSheetModal3.setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView$$ExternalSyntheticLambda0
                @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
                public final void onDismiss() {
                    this.f$0.onSignatureBottomSheetDismiss();
                }
            });
        }
        magazineSignatureTemplateView.setMShow(true);
        FontFilterView signatureFontFilterView = magazineSignatureTemplateView.getSignatureFontFilterView();
        if (signatureFontFilterView != null) {
            signatureFontFilterView.refreshFilterTitle();
        }
        AutoBottomSheet signatureSheetModal4 = magazineSignatureTemplateView.getSignatureSheetModal();
        if (signatureSheetModal4 != null) {
            signatureSheetModal4.show();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        ClockBean currentClockBean = getCurrentClockBean();
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            getCurrentClockBean().setClockEffect(viewModel.getFontFilterId());
            if (viewModel.getFontFilterId() == 1 || viewModel.getFontFilterId() == 2) {
                currentClockBean.setBlendColor(viewModel.getPrimaryFontFilter().getColor());
            } else {
                currentClockBean.setPrimaryColor(viewModel.getPrimaryFontFilter().getColor());
            }
        }
        super.onClockInfoUpdate();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        super.onEdited(i, obj);
        if (i == 60 && (obj instanceof String)) {
            if (!Intrinsics.areEqual(this.currentSignatureInfo.getContent(), obj)) {
                markEdited();
            }
            this.currentSignatureInfo.setContent((String) obj);
            onSignatureInfoUpdate();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        if (lockScreenInfoLayout != null) {
            if (isDualClock()) {
                lockScreenInfoLayout.setVisibility(8);
            } else {
                lockScreenInfoLayout.setModel(this.currentSignatureInfo);
            }
        }
        super.onMiuiClockViewCreated(templateConfig);
        final MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView == null) {
            return;
        }
        if (isDualClock()) {
            ViewParent parent = miuiClockView.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
            ((ViewGroup) parent).removeView(miuiClockView);
            post(new Runnable() { // from class: com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MagazineSignatureTemplateView.onMiuiClockViewCreated$lambda$8(this.f$0, miuiClockView);
                }
            });
        }
        if (!getSuccessUpdateClockView() || !this.updateSignatureSuccess) {
            completeWithCacheColor(false);
        }
        MiuiClockView miuiClockView2 = getMiuiClockView();
        this.clockContainer = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.CLOCK_CONTAINER) : null;
    }

    public void onSignatureInfoUpdate() {
        SignatureInfo signatureInfo = this.currentSignatureInfo;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getEditFragment());
        if (viewModel != null) {
            this.currentSignatureInfo.setClockEffect(viewModel.getFontFilterId());
            if (viewModel.getFontFilterId() == 1 || viewModel.getFontFilterId() == 2) {
                signatureInfo.setBlendColor(viewModel.getSecondaryFontFilter().getColor());
            } else {
                signatureInfo.setPrimaryColor(viewModel.getSecondaryFontFilter().getColor());
            }
        }
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        if (lockScreenInfoLayout != null) {
            lockScreenInfoLayout.setModel(this.currentSignatureInfo);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetAllBottomSheet() {
        super.resetAllBottomSheet();
        MagazineABSignatureEditor magazineABSignatureEditor = getMagazineABSignatureEditor();
        if (magazineABSignatureEditor != null) {
            magazineABSignatureEditor.setHostBottomSheet(null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetSpecialBottomSheet(boolean z) {
        super.resetSpecialBottomSheet(z);
        MagazineABSignatureEditor magazineABSignatureEditor = getMagazineABSignatureEditor();
        if (magazineABSignatureEditor != null) {
            magazineABSignatureEditor.setHostBottomSheet(null);
        }
        AutoBottomSheet signatureSheetModal = getSignatureSheetModal();
        if (signatureSheetModal != null) {
            signatureSheetModal.reset(z);
        }
        setSignatureSheetModal(null);
        AutoBottomSheet signatureCustomColorBottomSheetModal = getSignatureCustomColorBottomSheetModal();
        if (signatureCustomColorBottomSheetModal != null) {
            signatureCustomColorBottomSheetModal.reset(false);
        }
        setSignatureCustomColorBottomSheetModal(null);
    }

    public final void setClockContainer(@Nullable View view) {
        this.clockContainer = view;
    }

    public void setClockTranslateInSecondaryMode() {
    }

    public final void setCurrentSignatureInfo(@NotNull SignatureInfo signatureInfo) {
        Intrinsics.checkNotNullParameter(signatureInfo, "<set-?>");
        this.currentSignatureInfo = signatureInfo;
    }

    public final void setSignatureAreaFrame(@Nullable KgFrame kgFrame) {
        this.signatureAreaFrame = kgFrame;
    }

    public final void setSignatureView(@Nullable LockScreenInfoLayout lockScreenInfoLayout) {
        this.signatureView = lockScreenInfoLayout;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        if (lockScreenInfoLayout != null) {
            lockScreenInfoLayout.setClockPalette(colorData.getDarkSignatureArea(), colorData.getSignaturePalette());
        }
        setSuccessUpdateClockView(getMiuiClockView() != null);
        this.updateSignatureSuccess = this.signatureView != null;
    }
}

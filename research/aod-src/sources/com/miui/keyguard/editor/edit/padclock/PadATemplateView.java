package com.miui.keyguard.editor.edit.padclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
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
import com.miui.keyguard.editor.edit.color.handler.PadAColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.signature.PadASignatureEditor;
import com.miui.keyguard.editor.edit.signature.SignatureEditor;
import com.miui.keyguard.editor.edit.style.PadAStyleEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.KgFrameParam;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import miuix.androidbasewidget.widget.ClearableEditText;
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
/* JADX INFO: compiled from: PadATemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class PadATemplateView extends BaseClockView implements View.OnClickListener, EditCallback {
    public TextView clockView;
    public PadASignatureEditor editor;
    public final ClockBean foreClockBean;
    public MiuiClockView foreMiuiClockView;
    public boolean signatureShowing;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PadATemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.foreClockBean = new ClockBean("pad_exclusive_a_signature");
    }

    private final PadASignatureEditor getPadASignatureEditor() {
        SignatureEditor signatureStyleEditor = getSignatureStyleEditor();
        if (signatureStyleEditor instanceof PadASignatureEditor) {
            return (PadASignatureEditor) signatureStyleEditor;
        }
        return null;
    }

    public static final void onClickSignatureFrame$lambda$8(PadATemplateView padATemplateView) {
        ClearableEditText mSignatureInput1;
        PadASignatureEditor padASignatureEditor = padATemplateView.getPadASignatureEditor();
        if (padASignatureEditor == null || (mSignatureInput1 = padASignatureEditor.getMSignatureInput1()) == null) {
            return;
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = mSignatureInput1.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new PadATemplateView$onClickSignatureFrame$1$1$1(padATemplateView, mSignatureInput1, null), 3, null);
    }

    public static final void onClickSignatureFrame$lambda$9(PadATemplateView padATemplateView) {
        padATemplateView.onSignatureBottomSheetDismiss();
        padATemplateView.signatureShowing = false;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyTemplateInfo(TemplateConfig templateConfig) {
        ClockInfo clockInfo;
        super.applyTemplateInfo(templateConfig);
        if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
            return;
        }
        clockInfo.setSignatureLine1(this.foreClockBean.getSignatureLine1());
        clockInfo.setSignatureLine2(this.foreClockBean.getSignatureLine2());
        clockInfo.setSignatureLine3(this.foreClockBean.getSignatureLine3());
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
        return new PadAColorPickHandler(this, getCurrentClockBean(), this.foreClockBean);
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
        return "pad_exclusive_a";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        MiuiClockView miuiClockView = this.foreMiuiClockView;
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.SIGNATURE_AREA) : null;
        TextView textView = this.clockView;
        int left = textView != null ? textView.getLeft() : 0;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_time_clock_frame_extra_margin_top);
        TextView textView2 = this.clockView;
        int right = textView2 != null ? textView2.getRight() : 0;
        TextView textView3 = this.clockView;
        Rect rect = new Rect(left, dimensionPixelSize, right, textView3 != null ? textView3.getHeight() - getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_time_clock_frame_extra_margin_bottom) : 0);
        Rect rect2 = new Rect(iClockView != null ? iClockView.getLeft() - getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left) : 0, iClockView != null ? iClockView.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left) : 0, iClockView != null ? iClockView.getRight() + getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left) : 0, iClockView != null ? iClockView.getHeight() + getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left) : 0);
        int i = R.id.kg_pad_a_clock_area_frame;
        CustomMarginType customMarginType = CustomMarginType.ALL;
        KgFrameParam fixationFrameParam$default = BaseClockView.getFixationFrameParam$default(this, i, rect, customMarginType, new View[0], 0, 16, null);
        KgFrameParam fixationFrameParam = getFixationFrameParam(R.id.kg_signature_area_frame, rect2, customMarginType, new View[0], R.string.kg_signature_title);
        if (iClockView != null) {
            fixationFrameParam.setLayoutDirection(iClockView.getLayoutDirection());
        }
        Unit unit = Unit.INSTANCE;
        return CollectionsKt.mutableListOf(fixationFrameParam$default, fixationFrameParam);
    }

    @Nullable
    public final PadASignatureEditor getEditor() {
        return this.editor;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getPAD_A_FONT_FILTER();
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
            clockStyleEditor = new PadAStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @Nullable
    public SignatureEditor getOrCreateSignatureStyleEditor() {
        SignatureEditor signatureStyleEditor;
        SignatureEditor signatureStyleEditor2 = getSignatureStyleEditor();
        if (signatureStyleEditor2 == null) {
            signatureStyleEditor2 = new PadASignatureEditor(this, this, this.foreClockBean);
        }
        setSignatureStyleEditor(signatureStyleEditor2);
        if (DeviceUtil.INSTANCE.isPad() && (signatureStyleEditor = getSignatureStyleEditor()) != null) {
            signatureStyleEditor.setEditorViewPagerHeight(getContext().getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_dialog_content_height_common));
        }
        return getSignatureStyleEditor();
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
        this.foreClockBean.setSignatureLine1(clockInfo.getSignatureLine1());
        this.foreClockBean.setSignatureLine2(clockInfo.getSignatureLine2());
        this.foreClockBean.setSignatureLine3(clockInfo.getSignatureLine3());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect largeScreenWithNotificationAvoidRect(boolean z) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_hierarchy_notification_avoid_check_width);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize) / 2;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_hierarchy_notification_avoid_check_top);
        return new Rect(width, dimensionPixelSize2, dimensionPixelSize + width, getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_hierarchy_notification_avoid_check_height) + dimensionPixelSize2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        int id = v.getId();
        if (id == R.id.kg_pad_a_clock_area_frame) {
            onClickClockFrame(v);
        } else {
            if (id != R.id.kg_signature_area_frame || this.signatureShowing) {
                return;
            }
            onClickSignatureFrame(v);
        }
    }

    public void onClickSignatureFrame(View anchor) {
        final PadATemplateView padATemplateView;
        View view;
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        resetPrimaryButtonBar();
        inTalkBackCloseDialogs();
        this.signatureShowing = true;
        if (getSignatureSheetModal() == null) {
            padATemplateView = this;
            view = anchor;
            padATemplateView.setSignatureSheetModal(createBottomSheet(anchor, 1280, 0, 0, 0, sheetMarginBottom()));
        } else {
            padATemplateView = this;
            view = anchor;
        }
        if (padATemplateView.getSignatureCustomColorBottomSheetModal() == null) {
            padATemplateView.setSignatureCustomColorBottomSheetModal(padATemplateView.createBottomSheet(view, 1280, 0, 0, 0, padATemplateView.sheetMarginBottom()));
        }
        AutoBottomSheet signatureSheetModal = padATemplateView.getSignatureSheetModal();
        if (signatureSheetModal != null) {
            signatureSheetModal.setContentView(padATemplateView.getSignatureStyleEditorView(view));
        }
        AutoBottomSheet signatureSheetModal2 = padATemplateView.getSignatureSheetModal();
        if (signatureSheetModal2 != null) {
            PadASignatureEditor padASignatureEditor = padATemplateView.getPadASignatureEditor();
            ClearableEditText mSignatureInput1 = padASignatureEditor != null ? padASignatureEditor.getMSignatureInput1() : null;
            PadASignatureEditor padASignatureEditor2 = padATemplateView.getPadASignatureEditor();
            ClearableEditText mSignatureInput2 = padASignatureEditor2 != null ? padASignatureEditor2.getMSignatureInput2() : null;
            PadASignatureEditor padASignatureEditor3 = padATemplateView.getPadASignatureEditor();
            signatureSheetModal2.setEdits(CollectionsKt.listOf((Object[]) new ClearableEditText[]{mSignatureInput1, mSignatureInput2, padASignatureEditor3 != null ? padASignatureEditor3.getMSignatureInput3() : null}));
        }
        AutoBottomSheet signatureSheetModal3 = padATemplateView.getSignatureSheetModal();
        if (signatureSheetModal3 != null) {
            signatureSheetModal3.setOnShowListener(new BottomSheetModal.OnShowListener() { // from class: com.miui.keyguard.editor.edit.padclock.PadATemplateView$$ExternalSyntheticLambda0
                @Override // miuix.bottomsheet.BottomSheetModal.OnShowListener
                public final void onShow() {
                    PadATemplateView.onClickSignatureFrame$lambda$8(this.f$0);
                }
            });
        }
        AutoBottomSheet signatureSheetModal4 = padATemplateView.getSignatureSheetModal();
        if (signatureSheetModal4 != null) {
            signatureSheetModal4.setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.padclock.PadATemplateView$$ExternalSyntheticLambda1
                @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
                public final void onDismiss() {
                    PadATemplateView.onClickSignatureFrame$lambda$9(this.f$0);
                }
            });
        }
        padATemplateView.setMShow(true);
        AutoBottomSheet signatureSheetModal5 = padATemplateView.getSignatureSheetModal();
        if (signatureSheetModal5 != null) {
            signatureSheetModal5.show();
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
        PadATemplateView padATemplateView;
        WallpaperInfo wallpaperInfo;
        super.onEdited(i, obj);
        Bitmap currentWallpaper = null;
        currentWallpaper = null;
        if (i == 160) {
            if (obj instanceof Integer) {
                Number number = (Number) obj;
                getCurrentClockBean().setStyle(number.intValue());
                this.foreClockBean.setStyle(number.intValue());
                TemplateConfig templateConfig = templateConfig();
                if (templateConfig != null && (wallpaperInfo = templateConfig.getWallpaperInfo()) != null) {
                    currentWallpaper = wallpaperInfo.getCurrentWallpaper();
                }
                padATemplateView = this;
                BaseClockView.startPickColorFromWallpaper$default(padATemplateView, currentWallpaper, false, true, null, 8, null);
            }
            padATemplateView.onClockInfoUpdate();
        }
        switch (i) {
            case 63:
                if (obj instanceof String) {
                    this.foreClockBean.setSignatureLine1((String) obj);
                    MiuiClockView miuiClockView = this.foreMiuiClockView;
                    View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.TEXT_AREA) : null;
                    TextView textView = iClockView instanceof TextView ? (TextView) iClockView : null;
                    if (textView != null) {
                        textView.setText((CharSequence) obj);
                    }
                }
                break;
            case 64:
                if (obj instanceof String) {
                    this.foreClockBean.setSignatureLine2((String) obj);
                    MiuiClockView miuiClockView2 = this.foreMiuiClockView;
                    View iClockView2 = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.TEXT_AREA2) : null;
                    TextView textView2 = iClockView2 instanceof TextView ? (TextView) iClockView2 : null;
                    if (textView2 != null) {
                        textView2.setText((CharSequence) obj);
                    }
                }
                break;
            case 65:
                if (obj instanceof String) {
                    this.foreClockBean.setSignatureLine3((String) obj);
                    MiuiClockView miuiClockView3 = this.foreMiuiClockView;
                    View iClockView3 = miuiClockView3 != null ? miuiClockView3.getIClockView(ClockViewType.TEXT_AREA3) : null;
                    TextView textView3 = iClockView3 instanceof TextView ? (TextView) iClockView3 : null;
                    if (textView3 != null) {
                        textView3.setText((CharSequence) obj);
                    }
                }
                break;
        }
        padATemplateView = this;
        padATemplateView.onClockInfoUpdate();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        View iClockView;
        View iClockView2;
        View iClockView3;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.onMiuiClockViewCreated(templateConfig);
        MiuiClockView miuiClockView = getMiuiClockView();
        this.clockView = (TextView) (miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_TIME) : null);
        MiuiClockView miuiClockView2 = this.foreMiuiClockView;
        if (miuiClockView2 != null) {
            miuiClockView2.setVisibility(!miuiClockView2.isDualClock() ? 0 : 8);
        }
        MiuiClockView miuiClockView3 = this.foreMiuiClockView;
        if (miuiClockView3 != null) {
            MiuiClockView miuiClockView4 = getMiuiClockView();
            miuiClockView3.setBackgroundBlurContainer(miuiClockView4 != null ? miuiClockView4.getBackgroundBlurContainer() : null);
        }
        if (!isDualClock()) {
            MiuiClockView miuiClockView5 = this.foreMiuiClockView;
            if (miuiClockView5 != null) {
                miuiClockView5.init(this.foreClockBean, false);
            }
            MiuiClockView miuiClockView6 = this.foreMiuiClockView;
            if (miuiClockView6 != null && (iClockView3 = miuiClockView6.getIClockView(ClockViewType.TEXT_AREA)) != null) {
                ((TextView) iClockView3).setText(this.foreClockBean.getSignatureLine1());
            }
            MiuiClockView miuiClockView7 = this.foreMiuiClockView;
            if (miuiClockView7 != null && (iClockView2 = miuiClockView7.getIClockView(ClockViewType.TEXT_AREA2)) != null) {
                ((TextView) iClockView2).setText(this.foreClockBean.getSignatureLine2());
            }
            MiuiClockView miuiClockView8 = this.foreMiuiClockView;
            if (miuiClockView8 != null && (iClockView = miuiClockView8.getIClockView(ClockViewType.TEXT_AREA3)) != null) {
                ((TextView) iClockView).setText(this.foreClockBean.getSignatureLine3());
            }
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
            BaseClockView.popClockFontFilterDialog$default(this, R.id.kg_pad_a_clock_area_frame, 0, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetAllBottomSheet() {
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        keyboardUtils.hideSoftInput(context);
        super.resetAllBottomSheet();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetSpecialBottomSheet(boolean z) {
        super.resetSpecialBottomSheet(z);
        AutoBottomSheet signatureSheetModal = getSignatureSheetModal();
        if (signatureSheetModal != null) {
            signatureSheetModal.reset(z);
        }
        setSignatureSheetModal(null);
        this.signatureShowing = false;
        AutoBottomSheet signatureCustomColorBottomSheetModal = getSignatureCustomColorBottomSheetModal();
        if (signatureCustomColorBottomSheetModal != null) {
            AutoBottomSheet.reset$default(signatureCustomColorBottomSheetModal, false, 1, null);
        }
        setSignatureCustomColorBottomSheetModal(null);
    }

    public final void setEditor(@Nullable PadASignatureEditor padASignatureEditor) {
        this.editor = padASignatureEditor;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean supportGradientBlur() {
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_hierarchy_avoid_check_width);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize) / 2;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_hierarchy_avoid_check_top);
        return new Rect(width, dimensionPixelSize2, dimensionPixelSize + width, getResources().getDimensionPixelSize(R.dimen.kg_pad_a_style_hierarchy_avoid_check_height) + dimensionPixelSize2);
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

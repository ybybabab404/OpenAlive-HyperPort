package com.miui.keyguard.editor.edit.classicclock;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.R;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.CombineEditView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.base.MultiTouchFrameLayout;
import com.miui.keyguard.editor.edit.clock.ClassicTextStyleEditor;
import com.miui.keyguard.editor.edit.clock.ClassicTimeStyleEditor;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.Debouncer;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.utils.LockPatternUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.KgFrame;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
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
/* JADX INFO: compiled from: AbstractClassicTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public abstract class AbstractClassicTemplateView extends BaseClockView implements View.OnClickListener, EditCallback, EditorDialogNoTitleBarView.OnCloseDialogListener, TextWatcher {
    public boolean activelyClick;
    public boolean clickSignatureFocus;
    public View clickView;
    public final ClockBean foreClockBean;
    public MiuiClockView foreMiuiClockView;
    public ViewTreeObserver.OnGlobalLayoutListener keyboardListener;
    public ClassicTextStyleEditor mClassicTextStyleEditor;
    public EditorDialogNoTitleBarView mEditorDialogNoTitleBarView;
    public KgFrame mKgSignatureAreaFrame;
    public AutoBottomSheet mTextAreaBottomSheetModal;
    public AutoBottomSheet mTimeContentBottomSheetModal;
    public boolean screenChanged;
    public EditText signatureAreaView;
    public String signatureTextBeforeEdit;
    public boolean tapManually;
    public ClassicTextAreaView textAreaView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClassicTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.foreClockBean = new ClockBean(getForeClockStyleType());
    }

    private final String getOwnerInfo() {
        LockPatternUtils lockPatternUtils = LockPatternUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return lockPatternUtils.getOwnerInfo(context);
    }

    public static final void initListener$lambda$12(final AbstractClassicTemplateView abstractClassicTemplateView, View view, boolean z) {
        EditText editText;
        Context context;
        EditText editText2;
        final KgFrame kgFrame;
        Log.d("AbstractClassicTemplateView", "signatureFocusChanged: hasFocus=" + z + ", clickSignatureFocus=" + abstractClassicTemplateView.clickSignatureFocus + ", isDualClock=" + abstractClassicTemplateView.isDualClock() + ", baseTemplateView=" + (abstractClassicTemplateView.getBaseTemplateView() != null));
        abstractClassicTemplateView.onSignatureNeedDispatchChanged(z);
        if (z) {
            EditText editText3 = abstractClassicTemplateView.signatureAreaView;
            if (editText3 != null) {
                editText3.setHint("");
            }
        } else {
            EditText editText4 = abstractClassicTemplateView.signatureAreaView;
            if (TextUtils.isEmpty(editText4 != null ? editText4.getText() : null) && (editText = abstractClassicTemplateView.signatureAreaView) != null) {
                editText.setHint((editText == null || (context = editText.getContext()) == null) ? null : context.getString(R.string.miui_clock_classic_signature_area_hint));
            }
        }
        if (!abstractClassicTemplateView.clickSignatureFocus) {
            if (!z || (editText2 = abstractClassicTemplateView.signatureAreaView) == null) {
                return;
            }
            editText2.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractClassicTemplateView.initListener$lambda$12$lambda$11(this.f$0);
                }
            });
            return;
        }
        View view2 = abstractClassicTemplateView.clickView;
        if (view2 != null) {
            view2.setVisibility(z ? 0 : 8);
        }
        abstractClassicTemplateView.controlTopButtonState(!z);
        if (abstractClassicTemplateView.isDualClock() || (kgFrame = abstractClassicTemplateView.mKgSignatureAreaFrame) == null) {
            return;
        }
        kgFrame.setSelected(z);
        if (!z) {
            abstractClassicTemplateView.showAllFrame();
            final View view3 = abstractClassicTemplateView.clickView;
            if (view3 != null) {
                view3.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        AbstractClassicTemplateView.initListener$lambda$12$lambda$10$lambda$9$lambda$8(kgFrame, abstractClassicTemplateView, view3);
                    }
                });
                return;
            }
            return;
        }
        BaseClockView.hideAllFrame$default(abstractClassicTemplateView, false, 1, null);
        kgFrame.setVisibility(0);
        BaseTemplateView baseTemplateView = abstractClassicTemplateView.getBaseTemplateView();
        if (baseTemplateView != null) {
            baseTemplateView.onShowBottomSheet();
        }
    }

    public static final void initListener$lambda$12$lambda$10$lambda$9$lambda$8(KgFrame kgFrame, AbstractClassicTemplateView abstractClassicTemplateView, View view) {
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = kgFrame.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (keyboardUtils.keyboardIsShow(context)) {
            abstractClassicTemplateView.cancelEdite(view);
            BaseTemplateView baseTemplateView = abstractClassicTemplateView.getBaseTemplateView();
            if (baseTemplateView != null) {
                baseTemplateView.onDismissBottomSheet();
            }
            abstractClassicTemplateView.clickSignatureFocus = false;
        }
    }

    public static final void initListener$lambda$12$lambda$11(AbstractClassicTemplateView abstractClassicTemplateView) {
        EditText editText = abstractClassicTemplateView.signatureAreaView;
        if (editText != null) {
            editText.clearFocus();
        }
    }

    public static final void initListener$lambda$14(AbstractClassicTemplateView abstractClassicTemplateView, View view) {
        View view2;
        EditFragment editFragment = abstractClassicTemplateView.getEditFragment();
        Integer numValueOf = editFragment != null ? Integer.valueOf(editFragment.getTemplateStatus()) : null;
        if (numValueOf == null || numValueOf.intValue() != 2 || (view2 = abstractClassicTemplateView.clickView) == null) {
            return;
        }
        abstractClassicTemplateView.cancelEdite(view2);
        abstractClassicTemplateView.clickSignatureFocus = false;
    }

    public static final void initListener$lambda$7$lambda$6(AbstractClassicTemplateView abstractClassicTemplateView, View view, View view2) {
        abstractClassicTemplateView.cancelEdite(view);
        abstractClassicTemplateView.clickSignatureFocus = false;
    }

    private final void setFontWeight(int i) {
        getCurrentClockBean().setClockWeight(i);
        this.foreClockBean.setClockWeight(i);
        onClockInfoUpdate();
    }

    private final void setupKeyboardListener(Activity activity) {
        final View viewFindViewById = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        final Debouncer debouncer = new Debouncer(0L, 1, null);
        this.keyboardListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                AbstractClassicTemplateView.setupKeyboardListener$lambda$28(this.f$0, debouncer, viewFindViewById);
            }
        };
        viewFindViewById.getViewTreeObserver().addOnGlobalLayoutListener(this.keyboardListener);
    }

    public static final void setupKeyboardListener$lambda$28(final AbstractClassicTemplateView abstractClassicTemplateView, Debouncer debouncer, final View view) {
        View view2 = abstractClassicTemplateView.clickView;
        if (view2 != null && view2.getVisibility() == 0 && abstractClassicTemplateView.clickSignatureFocus) {
            debouncer.debounce(new Function0() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return AbstractClassicTemplateView.setupKeyboardListener$lambda$28$lambda$27(view, abstractClassicTemplateView);
                }
            });
        }
    }

    public static final Unit setupKeyboardListener$lambda$28$lambda$27(View view, AbstractClassicTemplateView abstractClassicTemplateView) {
        KgFrame kgFrame;
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int height = (abstractClassicTemplateView.activelyClick || abstractClassicTemplateView.tapManually) ? 200 : view.getHeight() - rect.bottom;
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = abstractClassicTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (!keyboardUtils.keyboardIsShow(context) || height < abstractClassicTemplateView.getContext().getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_keypad_soft_keyboard_display_height)) {
            if (abstractClassicTemplateView.clickView != null && (kgFrame = abstractClassicTemplateView.mKgSignatureAreaFrame) != null && kgFrame.isSelected()) {
                EditText editText = abstractClassicTemplateView.signatureAreaView;
                if (editText != null) {
                    editText.clearFocus();
                }
                abstractClassicTemplateView.controlTopButtonState(true);
                abstractClassicTemplateView.clearSelectState();
                BaseTemplateView baseTemplateView = abstractClassicTemplateView.getBaseTemplateView();
                if (baseTemplateView != null) {
                    baseTemplateView.onDismissBottomSheet();
                }
                abstractClassicTemplateView.clickSignatureFocus = false;
            }
            abstractClassicTemplateView.onSignatureEditCompleted();
        }
        abstractClassicTemplateView.screenChanged = false;
        abstractClassicTemplateView.activelyClick = false;
        abstractClassicTemplateView.tapManually = false;
        return Unit.INSTANCE;
    }

    public static final void showClassicTextStyleEditor$lambda$19(AbstractClassicTemplateView abstractClassicTemplateView) {
        BaseTemplateView baseTemplateView = abstractClassicTemplateView.getBaseTemplateView();
        if (baseTemplateView != null) {
            baseTemplateView.onShowBottomSheet();
        }
        abstractClassicTemplateView.controlTopButtonState(false);
    }

    public static final void showClassicTextStyleEditor$lambda$20(AbstractClassicTemplateView abstractClassicTemplateView) {
        BaseTemplateView baseTemplateView = abstractClassicTemplateView.getBaseTemplateView();
        if (baseTemplateView != null) {
            baseTemplateView.onDismissBottomSheet();
        }
        abstractClassicTemplateView.controlTopButtonState(true);
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyPrimaryMode() {
        setClockExtraMarginTopWhenPrimaryMode();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyTemplateInfo(TemplateConfig templateConfig) {
        ClockInfo clockInfo;
        super.applyTemplateInfo(templateConfig);
        if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
            return;
        }
        clockInfo.setClassicLine1(getCurrentClockBean().getClassicLine1());
        clockInfo.setClassicLine2(getCurrentClockBean().getClassicLine2());
        clockInfo.setClassicLine3(getCurrentClockBean().getClassicLine3());
        clockInfo.setClassicLine4(getCurrentClockBean().getClassicLine4());
        clockInfo.setClassicLine5(getCurrentClockBean().getClassicLine5());
        String classicSignature = getCurrentClockBean().getClassicSignature();
        if (classicSignature == null) {
            classicSignature = "";
        }
        clockInfo.setClassicSignature(classicSignature);
        clockInfo.setEnableDiffusion(getCurrentClockBean().isEnableDiffusion());
        clockInfo.setClockWeight(getCurrentClockBean().getClockWeight());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return com.miui.keyguard.editor.R.layout.kg_layout_clock_back_content_layer;
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void cancelEdite(View view) {
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        keyboardUtils.hideSoftInput(context);
        if (view != null) {
            view.setVisibility(8);
        }
        EditText editText = this.signatureAreaView;
        if (editText != null) {
            editText.clearFocus();
        }
        controlTopButtonState(true);
        onSignatureEditCompleted();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void clearEditeStatus() {
        super.clearEditeStatus();
        View view = this.clickView;
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        cancelEdite(view);
    }

    public final AutoBottomSheet createBottomSheetView(ClassicTextStyleEditor classicTextStyleEditor, View view) {
        Context context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
        AutoBottomSheet autoBottomSheet = new AutoBottomSheet((EditorActivity) context);
        EditorDialogNoTitleBarView container = classicTextStyleEditor.getContainer();
        if (container != null) {
            container.setOnCloseDialogListener(this);
        } else {
            container = null;
        }
        AutoBottomSheet onBeforeShowListener = autoBottomSheet.setContentView(container).setAnchorView(view, 1280).setMargin(0, 0, 0, sheetMarginBottom()).setOnBeforeShowListener(new BottomSheetModal.OnShowListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda5
            @Override // miuix.bottomsheet.BottomSheetModal.OnShowListener
            public final void onShow() {
                this.f$0.controlTopButtonState(false);
            }
        });
        onBeforeShowListener.setSkipCollapsed(true);
        onBeforeShowListener.setSkipHalfExpanded(true);
        return onBeforeShowListener;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        return new ColorPickHandler(this, getCurrentClockBean(), this.foreClockBean);
    }

    public final int dialogHeight() {
        Resources resources = getContext().getResources();
        return DeviceUtil.INSTANCE.isQ18() ? resources.getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_classic_text_editor_max_height) : resources.getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_editor_dialog_content_height_classic);
    }

    public final int dialogWidth() {
        Resources resources = getContext().getResources();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isQ18()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context)) {
                return resources.getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_bottom_sheet_width_q18);
            }
        }
        if (!deviceUtil.isPad()) {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (!deviceUtil.isFoldLargeScreen(context2)) {
                Context context3 = getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                return deviceUtil.currentDisplaySize(context3).width();
            }
        }
        return resources.getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_editor_container_pad_width);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void dismissBottomSheet() {
        AutoBottomSheet autoBottomSheet;
        AutoBottomSheet clockCustomFontColorBottomSheetModal = getClockCustomFontColorBottomSheetModal();
        if ((clockCustomFontColorBottomSheetModal == null || !clockCustomFontColorBottomSheetModal.isShowing()) && ((autoBottomSheet = this.mTimeContentBottomSheetModal) == null || !autoBottomSheet.isShowing())) {
            closeDialog();
            return;
        }
        AutoBottomSheet clockCustomFontColorBottomSheetModal2 = getClockCustomFontColorBottomSheetModal();
        if (clockCustomFontColorBottomSheetModal2 != null) {
            clockCustomFontColorBottomSheetModal2.dismiss();
        }
        AutoBottomSheet autoBottomSheet2 = this.mTimeContentBottomSheetModal;
        if (autoBottomSheet2 != null) {
            autoBottomSheet2.dismiss();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int foreContentLayerId() {
        return com.miui.keyguard.editor.R.layout.kg_layout_clock_fore_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AutoBottomSheet[] getAutoBottomSheetList() {
        return new AutoBottomSheet[]{getClockBottomSheetModal(), getClockCustomFontColorBottomSheetModal(), this.mTimeContentBottomSheetModal, this.mTextAreaBottomSheetModal};
    }

    @Nullable
    public final View getClickView() {
        return this.clickView;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int getFilterBottomSheetAlignModel() {
        return 264;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public View getFontStyleContentView() {
        getOrCreateClockStyleEditor();
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.clock.ClassicTimeStyleEditor");
        return ((ClassicTimeStyleEditor) clockStyleEditor).createDialogView();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @Nullable
    public MiuiClockView getForeClock() {
        return this.foreMiuiClockView;
    }

    @NotNull
    public final ClockBean getForeClockBean() {
        return this.foreClockBean;
    }

    @NotNull
    public abstract String getForeClockStyleType();

    @Nullable
    public final MiuiClockView getForeMiuiClockView() {
        return this.foreMiuiClockView;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @Nullable
    public List<View> getHideViewInScreenShot() {
        Editable text;
        EditText editText = this.signatureAreaView;
        if (editText != null && (text = editText.getText()) != null && text.length() > 0) {
            return super.getHideViewInScreenShot();
        }
        EditText editText2 = this.signatureAreaView;
        ViewParent parent = editText2 != null ? editText2.getParent() : null;
        return CollectionsKt.listOf(parent instanceof View ? (View) parent : null);
    }

    @Nullable
    public final ClassicTextStyleEditor getMClassicTextStyleEditor() {
        return this.mClassicTextStyleEditor;
    }

    @Nullable
    public final EditorDialogNoTitleBarView getMEditorDialogNoTitleBarView() {
        return this.mEditorDialogNoTitleBarView;
    }

    @Nullable
    public final KgFrame getMKgSignatureAreaFrame() {
        return this.mKgSignatureAreaFrame;
    }

    @Nullable
    public final AutoBottomSheet getMTextAreaBottomSheetModal() {
        return this.mTextAreaBottomSheetModal;
    }

    @Nullable
    public final AutoBottomSheet getMTimeContentBottomSheetModal() {
        return this.mTimeContentBottomSheetModal;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractClassicTemplateView abstractClassicTemplateView;
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            abstractClassicTemplateView = this;
            clockStyleEditor = new ClassicTimeStyleEditor(abstractClassicTemplateView, this, getCurrentClockBean().getStyle(), isPlusMaxTemplate(), getCurrentClockBean().getClockWeight(), getFontStyleEditCallback());
        } else {
            abstractClassicTemplateView = this;
        }
        abstractClassicTemplateView.setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = abstractClassicTemplateView.getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
    }

    @Nullable
    public final EditText getSignatureAreaView() {
        return this.signatureAreaView;
    }

    @Nullable
    public final ClassicTextAreaView getTextAreaView() {
        return this.textAreaView;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void hideSignature(boolean z) {
        Boolean boolValueOf;
        Editable text;
        EditText editText;
        Editable text2;
        super.hideSignature(z);
        boolean z2 = z && (editText = this.signatureAreaView) != null && (text2 = editText.getText()) != null && text2.length() == 0;
        EditText editText2 = this.signatureAreaView;
        Boolean boolValueOf2 = null;
        if (editText2 == null || (text = editText2.getText()) == null) {
            boolValueOf = null;
        } else {
            boolValueOf = Boolean.valueOf(text.length() == 0);
        }
        EditText editText3 = this.signatureAreaView;
        if (editText3 != null) {
            boolValueOf2 = Boolean.valueOf(editText3.getVisibility() == 0);
        }
        Log.d("AbstractClassicTemplateView", "hideSignature: hide=" + z + ", textEmpty=" + boolValueOf + ", shouldHide=" + z2 + ", currentVisible=" + boolValueOf2);
        EditText editText4 = this.signatureAreaView;
        if (editText4 != null) {
            editText4.setVisibility(z2 ? 8 : 0);
        }
        if (z) {
            KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (keyboardUtils.keyboardIsShow(context)) {
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                keyboardUtils.hideSoftInput(context2);
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreMiuiClockView = (MiuiClockView) templateView.findViewById(com.miui.keyguard.editor.R.id.fore_clock);
    }

    public final void initListener() {
        CombineEditView combineEditView;
        EditText editText = this.signatureAreaView;
        if (editText != null) {
            editText.setOnClickListener(this);
            editText.addTextChangedListener(this);
        }
        final View view = this.clickView;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    AbstractClassicTemplateView.initListener$lambda$7$lambda$6(this.f$0, view, view2);
                }
            });
        }
        EditText editText2 = this.signatureAreaView;
        if (editText2 != null) {
            editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda7
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    AbstractClassicTemplateView.initListener$lambda$12(this.f$0, view2, z);
                }
            });
        }
        EditFragment editFragment = getEditFragment();
        if (editFragment == null || (combineEditView = editFragment.getCombineEditView()) == null) {
            return;
        }
        combineEditView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AbstractClassicTemplateView.initListener$lambda$14(this.f$0, view2);
            }
        });
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        String str;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        String classicSignature = templateConfig.getClockInfo().getClassicSignature();
        if (getTemplateSource() == -1) {
            String ownerInfo = getOwnerInfo();
            if (classicSignature == null) {
                str = ownerInfo == null ? "" : ownerInfo;
            } else {
                str = classicSignature;
            }
            getCurrentClockBean().setClassicSignature(str);
            this.foreClockBean.setClassicSignature(str);
            Log.d("AbstractClassicTemplateView", "initTemplateBean signature: source=CURRENT, rawSig=" + classicSignature + ", ownerSig=" + ownerInfo + ", resolved=" + str);
        } else {
            getCurrentClockBean().setClassicSignature(classicSignature);
            this.foreClockBean.setClassicSignature(classicSignature);
            Log.d("AbstractClassicTemplateView", "initTemplateBean signature: source=PRESET, rawSig=" + classicSignature);
        }
        String classicSignature2 = getCurrentClockBean().getClassicSignature();
        int i = (classicSignature2 == null || classicSignature2.length() == 0) ? 0 : 12;
        getCurrentClockBean().setClassicLine5(i);
        this.foreClockBean.setClassicLine5(i);
        super.initTemplateBean(templateConfig);
        Log.d("AbstractClassicTemplateView", "initTemplateBean classicLine (AbstractClassic): before=" + getCurrentClockBean().getClassicLine1() + ", config.classicLine1=" + templateConfig.getClockInfo().getClassicLine1() + ", templateId=" + templateConfig.getClockInfo().getTemplateId());
        getCurrentClockBean().setStyle(templateConfig.getClockInfo().getStyle());
        getCurrentClockBean().setPrimaryColor(templateConfig.getClockInfo().getPrimaryColor());
        getCurrentClockBean().setClassicLine1(templateConfig.getClockInfo().getClassicLine1());
        getCurrentClockBean().setClassicLine2(templateConfig.getClockInfo().getClassicLine2());
        getCurrentClockBean().setClassicLine3(templateConfig.getClockInfo().getClassicLine3());
        getCurrentClockBean().setClassicLine4(templateConfig.getClockInfo().getClassicLine4());
        getCurrentClockBean().setAutoPrimaryColor(templateConfig.getClockInfo().isAutoPrimaryColor());
        getCurrentClockBean().setSecondaryColor(templateConfig.getClockInfo().getSecondaryColor());
        getCurrentClockBean().setAutoSecondaryColor(templateConfig.getClockInfo().isAutoSecondaryColor());
        getCurrentClockBean().setBlendColor(templateConfig.getClockInfo().getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(templateConfig.getClockInfo().getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(getDisplayClockEffect() >= 0 ? getDisplayClockEffect() : templateConfig.getClockInfo().getClockEffect());
        getCurrentClockBean().setClockWeight(templateConfig.getClockInfo().getClockWeight());
        this.foreClockBean.setClassicLine1(templateConfig.getClockInfo().getClassicLine1());
        this.foreClockBean.setClassicLine2(templateConfig.getClockInfo().getClassicLine2());
        this.foreClockBean.setClassicLine3(templateConfig.getClockInfo().getClassicLine3());
        this.foreClockBean.setClassicLine4(templateConfig.getClockInfo().getClassicLine4());
        this.foreClockBean.setClockWeight(templateConfig.getClockInfo().getClockWeight());
        this.foreClockBean.setStyle(templateConfig.getClockInfo().getStyle());
        this.foreClockBean.setPrimaryColor(templateConfig.getClockInfo().getPrimaryColor());
        this.foreClockBean.setAutoPrimaryColor(templateConfig.getClockInfo().isAutoPrimaryColor());
        this.foreClockBean.setSecondaryColor(templateConfig.getClockInfo().getSecondaryColor());
        this.foreClockBean.setAutoSecondaryColor(templateConfig.getClockInfo().isAutoSecondaryColor());
        this.foreClockBean.setBlendColor(templateConfig.getClockInfo().getBlendColor());
        this.foreClockBean.setSecondaryBlendColor(templateConfig.getClockInfo().getSecondaryBlendColor());
        this.foreClockBean.setClockEffect(getDisplayClockEffect() >= 0 ? getDisplayClockEffect() : templateConfig.getClockInfo().getClockEffect());
        if (getCurrentClockBean().getClassicLine1() == 11) {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!deviceUtil.isDataCapable(context)) {
                getCurrentClockBean().setClassicLine1(0);
                this.foreClockBean.setClassicLine1(0);
            }
        }
        Bundle bundle = this.extraParameters;
        if (bundle == null) {
            return;
        }
        ClockBean currentClockBean = getCurrentClockBean();
        String string = bundle.getString("health");
        String str2 = null;
        if (string != null) {
            Log.i("AbstractClassicTemplateView", "health preseted");
        } else {
            string = null;
        }
        currentClockBean.setPresetHealthJson(string);
        ClockBean clockBean = this.foreClockBean;
        String string2 = bundle.getString("health");
        if (string2 != null) {
            Log.i("AbstractClassicTemplateView", "health preseted");
        } else {
            string2 = null;
        }
        clockBean.setPresetHealthJson(string2);
        ClockBean currentClockBean2 = getCurrentClockBean();
        String string3 = bundle.getString("weather");
        if (string3 != null) {
            Log.i("AbstractClassicTemplateView", "weather preseted");
        } else {
            string3 = null;
        }
        currentClockBean2.setPresetWeatherJson(string3);
        ClockBean clockBean2 = this.foreClockBean;
        String string4 = bundle.getString("weather");
        if (string4 != null) {
            Log.i("AbstractClassicTemplateView", "weather preseted");
            str2 = string4;
        }
        clockBean2.setPresetWeatherJson(str2);
    }

    public abstract boolean isPlusMaxTemplate();

    public final boolean isSignatureTextChanged() {
        String string;
        Editable text;
        EditText editText = this.signatureAreaView;
        if (editText == null || (text = editText.getText()) == null || (string = text.toString()) == null) {
            string = "";
        }
        String str = this.signatureTextBeforeEdit;
        boolean z = (str == null || Intrinsics.areEqual(str, string)) ? false : true;
        this.signatureTextBeforeEdit = null;
        return z;
    }

    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        resetPrimaryButtonBar();
        inTalkBackCloseDialogs();
        int id = v.getId();
        if (id == com.miui.keyguard.editor.R.id.kg_text_area_frame) {
            KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (keyboardUtils.keyboardIsShow(context)) {
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                keyboardUtils.hideSoftInput(context2);
            }
            showClassicTextStyleEditor(ClockViewType.TEXT_AREA, v);
            return;
        }
        if (id != com.miui.keyguard.editor.R.id.kg_time_area_frame) {
            if (id == com.miui.keyguard.editor.R.id.kg_signature_area_frame) {
                signatureClickAction();
            }
        } else {
            KeyboardUtils keyboardUtils2 = KeyboardUtils.INSTANCE;
            Context context3 = getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            keyboardUtils2.hideSoftInput(context3);
            onClickClockFrame(v);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.screenChanged = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        View rootView;
        ViewTreeObserver viewTreeObserver;
        super.onDetachedFromWindow();
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = this.keyboardListener;
        if (onGlobalLayoutListener == null || (rootView = getRootView()) == null || (viewTreeObserver = rootView.getViewTreeObserver()) == null) {
            return;
        }
        if (!viewTreeObserver.isAlive()) {
            viewTreeObserver = null;
        }
        if (viewTreeObserver != null) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        super.onEdited(i, obj);
        if (i == 20) {
            setStyle(obj instanceof Integer ? (Integer) obj : null);
            return;
        }
        if (i == 112) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            setFontWeight(((Integer) obj).intValue());
        } else {
            if (i != 113) {
                return;
            }
            View viewFindViewById = findViewById(com.miui.keyguard.editor.R.id.kg_time_area_frame);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            showComponentView(viewFindViewById);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onExitTransform() {
        Editable text;
        EditText editText = this.signatureAreaView;
        if (editText == null || (text = editText.getText()) == null || text.length() != 0) {
            return;
        }
        Log.d("AbstractClassicTemplateView", "onExitTransform: signature text empty, setting alpha to 0");
        EditText editText2 = this.signatureAreaView;
        if (editText2 != null) {
            AnimatorKt.alphaTo$default(editText2, 0.0f, 0.0f, 0.3f, null, 10, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        String classicSignature;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.onMiuiClockViewCreated(templateConfig);
        boolean z = true;
        if (editable()) {
            MiuiClockView miuiClockView = getMiuiClockView();
            if (miuiClockView != null) {
                miuiClockView.setEditMode(true);
            }
            MiuiClockView miuiClockView2 = this.foreMiuiClockView;
            if (miuiClockView2 != null) {
                miuiClockView2.setEditMode(true);
            }
            MiuiClockView miuiClockView3 = this.foreMiuiClockView;
            View iClockView = miuiClockView3 != null ? miuiClockView3.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
            this.signatureAreaView = iClockView instanceof EditText ? (EditText) iClockView : null;
        }
        if (!editable() && ((classicSignature = this.foreClockBean.getClassicSignature()) == null || classicSignature.length() == 0)) {
            z = false;
        }
        boolean zEditable = editable();
        String hexString = Integer.toHexString(System.identityHashCode(this.signatureAreaView));
        String classicSignature2 = this.foreClockBean.getClassicSignature();
        Log.d("AbstractClassicTemplateView", "AbstractClassic.onMiuiClockViewCreated: editable=" + zEditable + ", signatureAreaView@=" + hexString + ", classicSignature='" + (classicSignature2 != null ? StringsKt.take(classicSignature2, 20) : null) + "', initVisible=" + z);
        EditText editText = this.signatureAreaView;
        if (editText != null) {
            editText.setVisibility(z ? 0 : 8);
        }
        initListener();
        if (getContext() instanceof Activity) {
            KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!keyboardUtils.isSoftwareKeyboardActive(context) || isDualClock()) {
                return;
            }
            Context context2 = getContext();
            Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type android.app.Activity");
            setupKeyboardListener((Activity) context2);
        }
    }

    public void onSignatureEditCompleted() {
    }

    public void onSignatureNeedDispatchChanged(boolean z) {
        MultiTouchFrameLayout multiTouchFrameLayout = (MultiTouchFrameLayout) getForeContentLayer();
        if (multiTouchFrameLayout != null) {
            multiTouchFrameLayout.updateNeedDispatch(z);
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String string;
        String string2;
        ClockBean currentClockBean = getCurrentClockBean();
        String str = "";
        if (charSequence == null || (string = charSequence.toString()) == null) {
            string = "";
        }
        currentClockBean.setClassicSignature(string);
        ClockBean clockBean = this.foreClockBean;
        if (charSequence != null && (string2 = charSequence.toString()) != null) {
            str = string2;
        }
        clockBean.setClassicSignature(str);
        String classicSignature = getCurrentClockBean().getClassicSignature();
        int i4 = (classicSignature == null || classicSignature.length() == 0) ? 0 : 12;
        getCurrentClockBean().setClassicLine5(i4);
        this.foreClockBean.setClassicLine5(i4);
        markEdited();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void popFontFilterDialog(int i) {
        super.popFontFilterDialog(i);
        if (i == 0) {
            BaseClockView.popClockFontFilterDialog$default(this, com.miui.keyguard.editor.R.id.kg_time_area_frame, 0, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetAllBottomSheet() {
        super.resetAllBottomSheet();
        AutoBottomSheet autoBottomSheet = this.mTextAreaBottomSheetModal;
        if (autoBottomSheet != null) {
            AutoBottomSheet.reset$default(autoBottomSheet, false, 1, null);
        }
        this.mTextAreaBottomSheetModal = null;
        AutoBottomSheet autoBottomSheet2 = this.mTimeContentBottomSheetModal;
        if (autoBottomSheet2 != null) {
            AutoBottomSheet.reset$default(autoBottomSheet2, false, 1, null);
        }
        this.mTimeContentBottomSheetModal = null;
        this.mEditorDialogNoTitleBarView = null;
        this.mClassicTextStyleEditor = null;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetSpecialBottomSheet(boolean z) {
        super.resetSpecialBottomSheet(z);
        AutoBottomSheet autoBottomSheet = this.mTextAreaBottomSheetModal;
        if (autoBottomSheet != null) {
            autoBottomSheet.reset(z);
        }
        this.mTextAreaBottomSheetModal = null;
        AutoBottomSheet autoBottomSheet2 = this.mTimeContentBottomSheetModal;
        if (autoBottomSheet2 != null) {
            AutoBottomSheet.reset$default(autoBottomSheet2, false, 1, null);
        }
        this.mTimeContentBottomSheetModal = null;
        this.mEditorDialogNoTitleBarView = null;
        this.mClassicTextStyleEditor = null;
    }

    public final void setClickView(@Nullable View view) {
        this.clickView = view;
    }

    public final void setClockExtraMarginTopWhenPrimaryMode() {
        float dimension = getResources().getDimension(com.miui.keyguard.editor.R.dimen.kg_classic_clock_view_primary_mode_translate) / getResources().getFloat(com.miui.keyguard.editor.R.dimen.kg_template_view_scale);
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            ViewUtil.setMarginTop(miuiClockView, (int) dimension);
        }
    }

    public final void setForeMiuiClockView(@Nullable MiuiClockView miuiClockView) {
        this.foreMiuiClockView = miuiClockView;
    }

    public final void setMClassicTextStyleEditor(@Nullable ClassicTextStyleEditor classicTextStyleEditor) {
        this.mClassicTextStyleEditor = classicTextStyleEditor;
    }

    public final void setMEditorDialogNoTitleBarView(@Nullable EditorDialogNoTitleBarView editorDialogNoTitleBarView) {
        this.mEditorDialogNoTitleBarView = editorDialogNoTitleBarView;
    }

    public final void setMKgSignatureAreaFrame(@Nullable KgFrame kgFrame) {
        this.mKgSignatureAreaFrame = kgFrame;
    }

    public final void setMTextAreaBottomSheetModal(@Nullable AutoBottomSheet autoBottomSheet) {
        this.mTextAreaBottomSheetModal = autoBottomSheet;
    }

    public final void setMTimeContentBottomSheetModal(@Nullable AutoBottomSheet autoBottomSheet) {
        this.mTimeContentBottomSheetModal = autoBottomSheet;
    }

    public final void setSignatureAreaView(@Nullable EditText editText) {
        this.signatureAreaView = editText;
    }

    public void setStyle(@Nullable Integer num) {
        if (num != null) {
            getCurrentClockBean().setStyle(num.intValue());
            onClockInfoUpdate();
        }
    }

    public final void setTextAreaView(@Nullable ClassicTextAreaView classicTextAreaView) {
        this.textAreaView = classicTextAreaView;
    }

    public final void showBottomSheetView(AutoBottomSheet autoBottomSheet) {
        if (autoBottomSheet != null) {
            autoBottomSheet.setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda3
                @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
                public final void onDismiss() {
                    this.f$0.onClockBottomSheetDismiss();
                }
            });
        }
        setMShow(true);
        if (autoBottomSheet != null) {
            autoBottomSheet.show();
        }
    }

    public final void showClassicTextStyleEditor(ClockViewType clockViewType, View view) {
        final AbstractClassicTemplateView abstractClassicTemplateView;
        EditorDialogNoTitleBarView container;
        if (this.mTextAreaBottomSheetModal == null) {
            abstractClassicTemplateView = this;
            ClassicTextStyleEditor classicTextStyleEditor = new ClassicTextStyleEditor(abstractClassicTemplateView, this, clockViewType, getCurrentClockBean(), getStyleEditorFontColor(), true, getCurrentClockBean().getStyle() == 0, !isPlusMaxTemplate(), getFontStyleEditCallback());
            abstractClassicTemplateView.mClassicTextStyleEditor = classicTextStyleEditor;
            Intrinsics.checkNotNull(classicTextStyleEditor);
            abstractClassicTemplateView.mTextAreaBottomSheetModal = abstractClassicTemplateView.createBottomSheetView(classicTextStyleEditor, view).setOnBeforeShowListener(new BottomSheetModal.OnShowListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda1
                @Override // miuix.bottomsheet.BottomSheetModal.OnShowListener
                public final void onShow() {
                    AbstractClassicTemplateView.showClassicTextStyleEditor$lambda$19(this.f$0);
                }
            }).setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView$$ExternalSyntheticLambda2
                @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
                public final void onDismiss() {
                    AbstractClassicTemplateView.showClassicTextStyleEditor$lambda$20(this.f$0);
                }
            });
        } else {
            abstractClassicTemplateView = this;
        }
        ClassicTextStyleEditor classicTextStyleEditor2 = abstractClassicTemplateView.mClassicTextStyleEditor;
        if (classicTextStyleEditor2 != null && (container = classicTextStyleEditor2.getContainer()) != null) {
            container.sendAccessibilityEvent();
        }
        abstractClassicTemplateView.showBottomSheetView(abstractClassicTemplateView.mTextAreaBottomSheetModal);
    }

    public void showComponentView(View anchor) {
        Intrinsics.checkNotNullParameter(anchor, "anchor");
    }

    public final void signatureClickAction() {
        String string;
        Editable text;
        Editable text2;
        resetPrimaryButtonBar();
        EditText editText = this.signatureAreaView;
        if (editText != null) {
            this.clickSignatureFocus = true;
            this.tapManually = true;
            if (editText == null || (text2 = editText.getText()) == null || (string = text2.toString()) == null) {
                string = "";
            }
            this.signatureTextBeforeEdit = string;
            BaseClockView.hideAllFrame$default(this, false, 1, null);
            KgFrame kgFrame = this.mKgSignatureAreaFrame;
            if (kgFrame != null) {
                kgFrame.setVisibility(0);
            }
            editText.setFocusableInTouchMode(true);
            View view = this.clickView;
            if (view != null) {
                view.setVisibility(0);
            }
            controlTopButtonState(false);
            editText.requestFocus();
            EditText editText2 = this.signatureAreaView;
            if (editText2 == null || (text = editText2.getText()) == null) {
                editText.setSelection(0);
            } else {
                editText.setSelection(text.length());
            }
            KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
            Context context = editText.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            keyboardUtils.showSoftInput(context, editText);
            this.activelyClick = this.screenChanged;
        }
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

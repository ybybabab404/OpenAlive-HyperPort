package com.miui.keyguard.editor.edit.signature;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView;
import com.miui.keyguard.editor.edit.view.AlignSelectAdapter;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.FontSelectAdapter;
import com.miui.keyguard.editor.edit.view.StyleSelectorHorizontalView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.lockscreeninfo.LockScreenInfoLayout;
import com.miui.lockscreeninfo.model.SignatureInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.androidbasewidget.widget.ClearableEditText;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MagazineABSignatureEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagazineABSignatureEditor extends SignatureEditor implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public LinearLayout alignContainer;
    public StyleSelectorHorizontalView alignSelectorView;
    public TemplateConfig config;
    public LinearLayout fontSelectorContainer;
    public StyleSelectorHorizontalView fontSelectorView;
    public int fontType;
    public final InputMethodManager inputManager;
    public int mInitProgress;
    public ImageView mLetterPaceAdd;
    public TextView mLetterPaceSize;
    public ImageView mLetterPaceSubtract;
    public TextView mLinePaceSize;
    public ClearableEditText mSignatureEditeTextView;
    public miuix.androidbasewidget.widget.SeekBar mTextSizeSeekBar;
    public RelativeLayout mWordSpacing;
    public final String signatureContent;
    public SignatureInfo signatureInfo;
    public final ClearableEditText signatureInput;
    public LockScreenInfoLayout signatureView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Code duplicated, block: B:13:0x0083  */
    public MagazineABSignatureEditor(MagazineSignatureTemplateView templateView, SignatureInfo signatureInfo, int i, TemplateConfig config, LockScreenInfoLayout lockScreenInfoLayout) {
        super(templateView, templateView);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(config, "config");
        this.signatureInfo = signatureInfo;
        this.fontType = i;
        this.config = config;
        this.signatureView = lockScreenInfoLayout;
        String content = signatureInfo != null ? signatureInfo.getContent() : null;
        this.signatureContent = content;
        ClearableEditText clearableEditText = (ClearableEditText) getSignatureEditorScrollContainer().findViewById(R.id.signature_editor_input);
        this.signatureInput = clearableEditText;
        clearableEditText.addTextChangedListener(this);
        clearableEditText.setText(content, TextView.BufferType.EDITABLE);
        clearableEditText.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.signature.MagazineABSignatureEditor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MagazineABSignatureEditor._init_$lambda$0(this.f$0);
            }
        });
        Object systemService = getContext().getSystemService("input_method");
        this.inputManager = systemService instanceof InputMethodManager ? (InputMethodManager) systemService : null;
        EditorDialogTitleView container = getContainer();
        String string = container.getContext().getString(R.string.kg_signature_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, null, 0, 2, null);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
            Context context = container.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            layoutParams.height = (int) (deviceUtil.currentDisplaySize(context).height() * container.getResources().getFloat(R.dimen.kg_classic_clock_screen_height_proportion));
        } else {
            Context context2 = container.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context2)) {
                ViewGroup.LayoutParams layoutParams2 = container.getLayoutParams();
                Context context3 = container.getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                layoutParams2.height = (int) (deviceUtil.currentDisplaySize(context3).height() * container.getResources().getFloat(R.dimen.kg_classic_clock_screen_height_proportion));
            }
        }
        initView();
        initData();
        initListener();
    }

    public static final void _init_$lambda$0(MagazineABSignatureEditor magazineABSignatureEditor) {
        magazineABSignatureEditor.signatureInput.setText(magazineABSignatureEditor.signatureContent, TextView.BufferType.EDITABLE);
        ClearableEditText clearableEditText = magazineABSignatureEditor.signatureInput;
        String str = magazineABSignatureEditor.signatureContent;
        clearableEditText.setSelection(str != null ? str.length() : 0);
    }

    private final void initListener() {
        ImageView imageView = this.mLetterPaceSubtract;
        if (imageView != null) {
            imageView.setOnClickListener(this);
        }
        ImageView imageView2 = this.mLetterPaceAdd;
        if (imageView2 != null) {
            imageView2.setOnClickListener(this);
        }
        ((ImageView) getSignatureEditorScrollContainer().findViewById(R.id.line_pace_subtract)).setOnClickListener(this);
        ((ImageView) getSignatureEditorScrollContainer().findViewById(R.id.line_pace_add)).setOnClickListener(this);
    }

    public final void addLetterSpace() {
        SignatureInfo signatureInfo = this.signatureInfo;
        if (signatureInfo != null) {
            signatureInfo.setLetterSpaceUnit(0.01f);
        }
        updateLetterViewSpace();
    }

    public final void addLineSpace() {
        SignatureInfo signatureInfo = this.signatureInfo;
        if (signatureInfo != null) {
            signatureInfo.setLineSpaceUnit(10.0f);
        }
        updateLineViewSpace();
    }

    @Override // com.miui.keyguard.editor.edit.signature.SignatureEditor
    public int getLayoutById() {
        return R.layout.kg_editor_dialog_signature_scroll_view;
    }

    public final ClearableEditText getMSignatureEditeTextView() {
        return this.mSignatureEditeTextView;
    }

    public float getOriginalLetterSpace() {
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        if (lockScreenInfoLayout != null) {
            return lockScreenInfoLayout.getLetterSpaceValue();
        }
        return 0.0f;
    }

    public final void initData() {
        TextView textView = this.mLetterPaceSize;
        if (textView != null) {
            LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
            textView.setText(String.valueOf(lockScreenInfoLayout != null ? Float.valueOf(lockScreenInfoLayout.getLetterSpaceValue()) : null));
        }
        TextView textView2 = this.mLinePaceSize;
        if (textView2 != null) {
            LockScreenInfoLayout lockScreenInfoLayout2 = this.signatureView;
            textView2.setText(String.valueOf(lockScreenInfoLayout2 != null ? Float.valueOf(lockScreenInfoLayout2.getLineSpaceValue()) : null));
        }
        StyleSelectorHorizontalView styleSelectorHorizontalView = this.fontSelectorView;
        if (styleSelectorHorizontalView != null) {
            styleSelectorHorizontalView.setAdapter(new FontSelectAdapter(getEditCallback(), this.fontType, this.config.getClockInfo().getTemplateId(), getContext()));
        }
        StyleSelectorHorizontalView styleSelectorHorizontalView2 = this.alignSelectorView;
        if (styleSelectorHorizontalView2 != null) {
            SignatureInfo signatureInfo = this.signatureInfo;
            styleSelectorHorizontalView2.setAdapter(signatureInfo != null ? new AlignSelectAdapter(getContext(), getEditCallback(), signatureInfo.getAlignment()) : null);
        }
    }

    public final void initView() {
        String templateId;
        this.alignContainer = (LinearLayout) getSignatureEditorScrollContainer().findViewById(R.id.signature_editor_align_container);
        this.alignSelectorView = (StyleSelectorHorizontalView) getSignatureEditorScrollContainer().findViewById(R.id.signature_editor_align_selector_view);
        this.mSignatureEditeTextView = (ClearableEditText) getSignatureEditorScrollContainer().findViewById(R.id.signature_editor_input);
        this.mTextSizeSeekBar = (miuix.androidbasewidget.widget.SeekBar) getSignatureEditorScrollContainer().findViewById(R.id.font_size_seekbar);
        this.mLetterPaceSize = (TextView) getSignatureEditorScrollContainer().findViewById(R.id.letter_pace_size);
        this.mLinePaceSize = (TextView) getSignatureEditorScrollContainer().findViewById(R.id.line_pace);
        this.mWordSpacing = (RelativeLayout) getSignatureEditorScrollContainer().findViewById(R.id.word_spacing);
        this.mLetterPaceSubtract = (ImageView) getSignatureEditorScrollContainer().findViewById(R.id.letter_pace_subtract);
        this.mLetterPaceAdd = (ImageView) getSignatureEditorScrollContainer().findViewById(R.id.letter_pace_add);
        SignatureInfo signatureInfo = this.signatureInfo;
        if (signatureInfo == null || (templateId = signatureInfo.getTemplateId()) == null || !templateId.equals("magazine_a")) {
            LinearLayout linearLayout = this.alignContainer;
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
        } else {
            LinearLayout linearLayout2 = this.alignContainer;
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(0);
            }
        }
        miuix.androidbasewidget.widget.SeekBar seekBar = this.mTextSizeSeekBar;
        if (seekBar != null) {
            LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
            seekBar.setMax(lockScreenInfoLayout != null ? lockScreenInfoLayout.getMaxTextSize() : 400);
        }
        miuix.androidbasewidget.widget.SeekBar seekBar2 = this.mTextSizeSeekBar;
        if (seekBar2 != null) {
            seekBar2.setMin(10);
        }
        miuix.androidbasewidget.widget.SeekBar seekBar3 = this.mTextSizeSeekBar;
        if (seekBar3 != null) {
            LockScreenInfoLayout lockScreenInfoLayout2 = this.signatureView;
            seekBar3.setProgress(lockScreenInfoLayout2 != null ? (int) lockScreenInfoLayout2.getInitTextSize() : 200);
        }
        miuix.androidbasewidget.widget.SeekBar seekBar4 = this.mTextSizeSeekBar;
        this.mInitProgress = seekBar4 != null ? seekBar4.getProgress() : 0;
        miuix.androidbasewidget.widget.SeekBar seekBar5 = this.mTextSizeSeekBar;
        if (seekBar5 != null) {
            seekBar5.setOnSeekBarChangeListener(this);
        }
        StyleSelectorHorizontalView styleSelectorHorizontalView = this.alignSelectorView;
        if (styleSelectorHorizontalView != null) {
            styleSelectorHorizontalView.setLabelViewGone();
        }
        StyleSelectorHorizontalView styleSelectorHorizontalView2 = this.alignSelectorView;
        if (styleSelectorHorizontalView2 != null) {
            styleSelectorHorizontalView2.setLabelMarginBottom(0);
        }
        this.fontSelectorContainer = (LinearLayout) getEditorPagerContentWrapper().findViewById(R.id.signature_editor_font_selector_container);
        StyleSelectorHorizontalView styleSelectorHorizontalView3 = (StyleSelectorHorizontalView) getEditorPagerContentWrapper().findViewById(R.id.signature_editor_font_selector_view);
        this.fontSelectorView = styleSelectorHorizontalView3;
        if (styleSelectorHorizontalView3 != null) {
            styleSelectorHorizontalView3.setLabelViewGone();
        }
        LinearLayout linearLayout3 = this.fontSelectorContainer;
        Intrinsics.checkNotNull(linearLayout3, "null cannot be cast to non-null type android.widget.LinearLayout");
        linearLayout3.setVisibility(0);
    }

    public void limitWordSpacing(boolean z) {
        RelativeLayout relativeLayout = this.mWordSpacing;
        if (relativeLayout != null) {
            relativeLayout.setAlpha(z ? 0.3f : 1.0f);
        }
        ImageView imageView = this.mLetterPaceSubtract;
        if (imageView != null) {
            imageView.setClickable(!z);
        }
        ImageView imageView2 = this.mLetterPaceAdd;
        if (imageView2 != null) {
            imageView2.setClickable(!z);
        }
        TextView textView = this.mLetterPaceSize;
        if (textView != null) {
            LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
            textView.setText(String.valueOf(lockScreenInfoLayout != null ? Float.valueOf(lockScreenInfoLayout.getLetterSpaceValue()) : null));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
        int i = R.id.letter_pace_subtract;
        if (numValueOf != null && numValueOf.intValue() == i) {
            subtractLetterSpace();
            return;
        }
        int i2 = R.id.letter_pace_add;
        if (numValueOf != null && numValueOf.intValue() == i2) {
            addLetterSpace();
            return;
        }
        int i3 = R.id.line_pace_subtract;
        if (numValueOf != null && numValueOf.intValue() == i3) {
            subtractLineSpace();
            return;
        }
        int i4 = R.id.line_pace_add;
        if (numValueOf != null && numValueOf.intValue() == i4) {
            addLineSpace();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (this.mInitProgress != i) {
            getEditCallback().onEdited(66, "");
        }
        SignatureInfo signatureInfo = this.signatureInfo;
        if (signatureInfo != null) {
            signatureInfo.setTextsize(i);
        }
        updateTextViewSize();
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public void onTemplateRemoved() {
        super.onTemplateRemoved();
        StyleSelectorHorizontalView styleSelectorHorizontalView = this.alignSelectorView;
        if (styleSelectorHorizontalView != null) {
            styleSelectorHorizontalView.setAdapter(null);
        }
        StyleSelectorHorizontalView styleSelectorHorizontalView2 = this.fontSelectorView;
        if (styleSelectorHorizontalView2 != null) {
            styleSelectorHorizontalView2.setAdapter(null);
        }
    }

    public final void subtractLetterSpace() {
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        if ((lockScreenInfoLayout != null ? lockScreenInfoLayout.getLetterSpaceValue() : 0.0f) <= -9.99f) {
            return;
        }
        SignatureInfo signatureInfo = this.signatureInfo;
        if (signatureInfo != null) {
            signatureInfo.setLetterSpaceUnit(-0.01f);
        }
        updateLetterViewSpace();
    }

    public final void subtractLineSpace() {
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        if ((lockScreenInfoLayout != null ? lockScreenInfoLayout.getLineSpaceValue() : 0.0f) <= -90.0f) {
            return;
        }
        SignatureInfo signatureInfo = this.signatureInfo;
        if (signatureInfo != null) {
            signatureInfo.setLineSpaceUnit(-10.0f);
        }
        updateLineViewSpace();
    }

    public final void updateLetterSpaceInfo(Float f) {
        if (f != null) {
            float fFloatValue = f.floatValue();
            SignatureInfo signatureInfo = this.signatureInfo;
            if (signatureInfo != null) {
                signatureInfo.setLetterSpaceValue(fFloatValue);
            }
            TextView textView = this.mLetterPaceSize;
            if (textView != null) {
                textView.setText(String.valueOf(fFloatValue));
            }
        }
    }

    public final void updateLetterViewSpace() {
        getEditCallback().onEdited(67, "");
        FrameLayout templateView = getTemplateView();
        MagazineSignatureTemplateView magazineSignatureTemplateView = templateView instanceof MagazineSignatureTemplateView ? (MagazineSignatureTemplateView) templateView : null;
        if (magazineSignatureTemplateView != null) {
            magazineSignatureTemplateView.onSignatureInfoUpdate();
        }
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        updateLetterSpaceInfo(lockScreenInfoLayout != null ? Float.valueOf(lockScreenInfoLayout.getLetterSpaceValue()) : null);
    }

    public final void updateLineSpaceInfo(Float f) {
        if (f != null) {
            float fFloatValue = f.floatValue();
            SignatureInfo signatureInfo = this.signatureInfo;
            if (signatureInfo != null) {
                signatureInfo.setLineSpaceValue(fFloatValue);
            }
            TextView textView = this.mLinePaceSize;
            if (textView != null) {
                textView.setText(String.valueOf(fFloatValue));
            }
        }
    }

    public final void updateLineViewSpace() {
        getEditCallback().onEdited(68, "");
        FrameLayout templateView = getTemplateView();
        MagazineSignatureTemplateView magazineSignatureTemplateView = templateView instanceof MagazineSignatureTemplateView ? (MagazineSignatureTemplateView) templateView : null;
        if (magazineSignatureTemplateView != null) {
            magazineSignatureTemplateView.onSignatureInfoUpdate();
        }
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        updateLineSpaceInfo(lockScreenInfoLayout != null ? Float.valueOf(lockScreenInfoLayout.getLineSpaceValue()) : null);
    }

    public final void updateTextSizeInfo(Float f) {
        if (f != null) {
            float fFloatValue = f.floatValue();
            SignatureInfo signatureInfo = this.signatureInfo;
            if (signatureInfo != null) {
                signatureInfo.setTextsize(fFloatValue);
            }
        }
    }

    public final void updateTextViewSize() {
        FrameLayout templateView = getTemplateView();
        MagazineSignatureTemplateView magazineSignatureTemplateView = templateView instanceof MagazineSignatureTemplateView ? (MagazineSignatureTemplateView) templateView : null;
        if (magazineSignatureTemplateView != null) {
            magazineSignatureTemplateView.onSignatureInfoUpdate();
        }
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        updateTextSizeInfo(lockScreenInfoLayout != null ? Float.valueOf((int) lockScreenInfoLayout.getTextsize()) : null);
    }
}

package com.miui.keyguard.editor.edit.signature;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.androidbasewidget.widget.ClearableEditText;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: PadASignatureEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class PadASignatureEditor extends SignatureEditor {
    public final ClockBean foreClockBean;
    public final ClearableEditText mSignatureInput1;
    public final ClearableEditText mSignatureInput2;
    public final ClearableEditText mSignatureInput3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PadASignatureEditor(EditCallback editCallback, BaseClockView templateView, ClockBean clockBean) {
        super(templateView, editCallback);
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreClockBean = clockBean;
        View viewFindViewById = getSignatureEditorScrollContainer().findViewById(R.id.signature_editor_input1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mSignatureInput1 = (ClearableEditText) viewFindViewById;
        View viewFindViewById2 = getSignatureEditorScrollContainer().findViewById(R.id.signature_editor_input2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.mSignatureInput2 = (ClearableEditText) viewFindViewById2;
        View viewFindViewById3 = getSignatureEditorScrollContainer().findViewById(R.id.signature_editor_input3);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.mSignatureInput3 = (ClearableEditText) viewFindViewById3;
        setBaseTemplateView(templateView);
        initTextInput();
        EditorDialogTitleView container = getContainer();
        String string = container.getContext().getString(R.string.kg_signature_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, null, 0, 2, null);
    }

    public static final void setSelection$lambda$2(ClearableEditText clearableEditText, View view, boolean z) {
        Editable text;
        if (!z || clearableEditText == null || (text = clearableEditText.getText()) == null) {
            return;
        }
        clearableEditText.setSelection(text.length());
    }

    @Override // com.miui.keyguard.editor.edit.signature.SignatureEditor
    public int getLayoutById() {
        return R.layout.kg_layout_pad_a_signature_editor;
    }

    public final ClearableEditText getMSignatureInput1() {
        return this.mSignatureInput1;
    }

    public final ClearableEditText getMSignatureInput2() {
        return this.mSignatureInput2;
    }

    public final ClearableEditText getMSignatureInput3() {
        return this.mSignatureInput3;
    }

    public final void initTextInput() {
        ClearableEditText clearableEditText = this.mSignatureInput1;
        ClockBean clockBean = this.foreClockBean;
        clearableEditText.setText(clockBean != null ? clockBean.getSignatureLine1() : null);
        ClearableEditText clearableEditText2 = this.mSignatureInput2;
        ClockBean clockBean2 = this.foreClockBean;
        clearableEditText2.setText(clockBean2 != null ? clockBean2.getSignatureLine2() : null);
        ClearableEditText clearableEditText3 = this.mSignatureInput3;
        ClockBean clockBean3 = this.foreClockBean;
        clearableEditText3.setText(clockBean3 != null ? clockBean3.getSignatureLine3() : null);
        setSelection(this.mSignatureInput1);
        setSelection(this.mSignatureInput2);
        setSelection(this.mSignatureInput3);
        this.mSignatureInput1.addTextChangedListener(new TextWatcher() { // from class: com.miui.keyguard.editor.edit.signature.PadASignatureEditor.initTextInput.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
                PadASignatureEditor.this.getEditCallback().onEdited(63, s.toString());
            }
        });
        this.mSignatureInput2.addTextChangedListener(new TextWatcher() { // from class: com.miui.keyguard.editor.edit.signature.PadASignatureEditor.initTextInput.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
                PadASignatureEditor.this.getEditCallback().onEdited(64, s.toString());
            }
        });
        this.mSignatureInput3.addTextChangedListener(new TextWatcher() { // from class: com.miui.keyguard.editor.edit.signature.PadASignatureEditor.initTextInput.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
                PadASignatureEditor.this.getEditCallback().onEdited(65, s.toString());
            }
        });
    }

    public final void setSelection(final ClearableEditText clearableEditText) {
        if (clearableEditText != null) {
            clearableEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.keyguard.editor.edit.signature.PadASignatureEditor$$ExternalSyntheticLambda0
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view, boolean z) {
                    PadASignatureEditor.setSelection$lambda$2(clearableEditText, view, z);
                }
            });
        }
    }
}

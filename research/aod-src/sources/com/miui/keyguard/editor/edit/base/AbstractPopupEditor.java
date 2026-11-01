package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.PopupLayerView;
import com.miui.keyguard.editor.utils.DensityUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.FontFilterView;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: AbstractPopupEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class AbstractPopupEditor extends AbstractEditor implements PopupLayerView.OnPopupListener {
    public final Context context;
    public FontFilterView fontFilterView;
    public final FontStyleEditCallback fontStyleEditCallback;
    public final Lazy popupLayerView;
    public final FrameLayout templateView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractPopupEditor(FrameLayout templateView, EditCallback editCallback, FontStyleEditCallback fontStyleEditCallback) {
        super(editCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.templateView = templateView;
        this.fontStyleEditCallback = fontStyleEditCallback;
        Context context = templateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.context = context;
        templateView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.miui.keyguard.editor.edit.base.AbstractPopupEditor.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View v) {
                Intrinsics.checkNotNullParameter(v, "v");
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View v) {
                Intrinsics.checkNotNullParameter(v, "v");
                AbstractPopupEditor.this.onTemplateRemoved();
            }
        });
        this.popupLayerView = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.base.AbstractPopupEditor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AbstractPopupEditor.popupLayerView$lambda$1(this.f$0);
            }
        });
    }

    public /* synthetic */ AbstractPopupEditor(FrameLayout frameLayout, EditCallback editCallback, FontStyleEditCallback fontStyleEditCallback, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(frameLayout, editCallback, (i & 4) != 0 ? null : fontStyleEditCallback);
    }

    public static final PopupLayerView popupLayerView$lambda$1(AbstractPopupEditor abstractPopupEditor) {
        PopupLayerView popupLayerView = new PopupLayerView(abstractPopupEditor.context);
        ViewUtil.setBackgroundBlurModeWhenBlurEnabled$default(ViewUtil.INSTANCE, popupLayerView, DensityUtils.dp2Px(100), 0, 2, null);
        popupLayerView.setPopupListener(abstractPopupEditor);
        return popupLayerView;
    }

    public View createFontEditorContent() {
        return new View(this.context);
    }

    public EditorDialogTitleView editorViewContainer() {
        return null;
    }

    public final Context getContext() {
        return this.context;
    }

    public final FontFilterView getFontFilterView() {
        return this.fontFilterView;
    }

    public final FontStyleEditCallback getFontStyleEditCallback() {
        return this.fontStyleEditCallback;
    }

    public final FrameLayout getTemplateView() {
        return this.templateView;
    }

    public View loadFontEditorContentView() {
        View fontStyleView;
        FontStyleEditCallback fontStyleEditCallback = this.fontStyleEditCallback;
        return (fontStyleEditCallback == null || (fontStyleView = fontStyleEditCallback.getFontStyleView()) == null) ? createFontEditorContent() : fontStyleView;
    }

    public void onTemplateRemoved() {
    }

    public final PopupLayerView popupLayerView() {
        return (PopupLayerView) this.popupLayerView.getValue();
    }

    public final void setFontFilterView(FontFilterView fontFilterView) {
        this.fontFilterView = fontFilterView;
    }
}

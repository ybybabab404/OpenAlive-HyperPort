package com.miui.keyguard.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.BlendMode;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: KgFillVisualCheckBox.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class KgFillVisualCheckBox extends KgVisualCheckBox {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View background;
    public int blendColor1;
    public int blendColor2;
    public BlendMode blendMode1;
    public BlendMode blendMode2;
    public boolean isNormalButton;
    public int selectedBackgroundColor;

    public KgFillVisualCheckBox(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.blendMode1 = BlendMode.SRC_OVER;
        this.blendMode2 = BlendMode.COLOR;
    }

    @Override // android.view.View
    @NotNull
    public final View getBackground() {
        View view = this.background;
        if (view != null) {
            return view;
        }
        Intrinsics.throwUninitializedPropertyAccessException("background");
        return null;
    }

    @Override // com.miui.keyguard.editor.view.KgVisualCheckBox, miuix.visual.check.VisualCheckBox, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        setIcon((ImageView) findViewById(R.id.kg_func_button_icon));
        setBackground(findViewById(R.id.kg_func_icon_box));
    }

    public final void setBackground(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<set-?>");
        this.background = view;
    }

    public final void setNormalButton(boolean z) {
        this.isNormalButton = z;
    }

    public final void setSelectedBackgroundColor(int i) {
        this.selectedBackgroundColor = i;
    }

    public final void setUnselectedBlendStyle(int i, @NotNull BlendMode blendMode1, int i2, @NotNull BlendMode blendMode2) {
        Intrinsics.checkNotNullParameter(blendMode1, "blendMode1");
        Intrinsics.checkNotNullParameter(blendMode2, "blendMode2");
        this.blendColor1 = i;
        this.blendMode1 = blendMode1;
        this.blendColor2 = i2;
        this.blendMode2 = blendMode2;
    }

    @Override // com.miui.keyguard.editor.view.KgVisualCheckBox
    public void updateWhenCheckedChanged(boolean z) {
        if (this.isNormalButton) {
            return;
        }
        if (z) {
            getBackground().setBackgroundColor(this.selectedBackgroundColor);
            ViewStylerKt.clearBackgroundBlendColor(getBackground());
        } else {
            getBackground().setBackgroundColor(0);
            ViewUtil.decorateFrostedPured$default(ViewUtil.INSTANCE, getBackground(), getResources().getDimension(R.dimen.kg_function_button_icon_radius), false, getContext().getColor(R.color.kg_white_14), 2, null);
        }
    }
}

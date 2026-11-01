package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.BlendColorInfo;
import com.miui.keyguard.editor.utils.BlendMode;
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
/* JADX INFO: compiled from: StyleSelectorContainer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class StyleSelectorContainer extends LinearLayout {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public StyleSelectorContainer(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StyleSelectorContainer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        setOrientation(1);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StyleSelectorContainer);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyleSelectorContainer_fixedPaddingVetical, true);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyleSelectorContainer_smallCorner, false);
        typedArrayObtainStyledAttributes.recycle();
        setFixPaddingVertical(z);
        setDecorateBackground(z2);
        setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.view.StyleSelectorContainer$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StyleSelectorContainer._init_$lambda$0(view);
            }
        });
    }

    public static final void _init_$lambda$0(View view) {
    }

    public final void setDecorateBackground(boolean z) {
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        ViewUtil.decorateBackground$default(viewUtil, this, getResources().getDimension(z ? R.dimen.kg_style_selector_corner_radius_small : R.dimen.kg_style_selector_corner_radius_large), new BlendColorInfo[]{new BlendColorInfo(viewUtil.getColor(this, R.color.kg_popup_blend_color1), BlendMode.LINEAR_LIGHT), new BlendColorInfo(viewUtil.getColor(this, R.color.kg_popup_blend_color2), BlendMode.LAB)}, 0, false, 0.0f, 28, null);
    }

    public final void setFixPaddingVertical(boolean z) {
        if (z) {
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.kg_editor_container_padding);
            setPadding(0, dimensionPixelOffset, 0, dimensionPixelOffset);
        }
    }
}

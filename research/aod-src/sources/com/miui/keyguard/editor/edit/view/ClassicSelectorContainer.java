package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: ClassicSelectorContainer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClassicSelectorContainer extends FrameLayout {
    public final int containerMaxHeight;
    public final int containerMinHeight;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public ClassicSelectorContainer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ClassicSelectorContainer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClassicSelectorContainer);
        this.containerMaxHeight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ClassicSelectorContainer_containerMaxHeight, 0);
        this.containerMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ClassicSelectorContainer_containerMinHeight, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    public /* synthetic */ ClassicSelectorContainer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        if (getChildCount() > 1) {
            throw new IllegalArgumentException("only one child !!!");
        }
        if ((this.containerMaxHeight == 0 && this.containerMinHeight == 0) || getChildCount() == 0) {
            super.onMeasure(i, i2);
            return;
        }
        View childAt = getChildAt(0);
        childAt.measure(i, View.MeasureSpec.makeMeasureSpec(this.containerMaxHeight, Integer.MIN_VALUE));
        setMeasuredDimension(childAt.getMeasuredWidth(), Math.max(childAt.getMeasuredHeight(), this.containerMinHeight));
    }
}

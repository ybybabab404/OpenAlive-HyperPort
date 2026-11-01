package com.miui.keyguard.editor.edit.color;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
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
    */
/* JADX INFO: compiled from: ColorProgressDrawable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DefaultColorProgressDrawable extends ColorProgressDrawable {
    public int endColor;
    public final int startColor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultColorProgressDrawable(Context context, int i, int i2, int i3) {
        super(context, new int[0]);
        Intrinsics.checkNotNullParameter(context, "context");
        this.startColor = ContextCompat.getColor(context, i2);
        this.endColor = ContextCompat.getColor(context, i3);
        setProgressColor(i);
    }

    public /* synthetic */ DefaultColorProgressDrawable(Context context, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, i, (i4 & 4) != 0 ? R.color.kg_color_value_lightness_start_color : i2, (i4 & 8) != 0 ? R.color.kg_color_value_lightness_end_color : i3);
    }

    public final void setEndColor(int i) {
        this.endColor = i;
        setColors(new int[]{i, this.startColor});
    }

    public final void setProgressColor(int i) {
        setColors(new int[]{this.startColor, i, this.endColor});
    }
}

package com.miui.keyguard.editor.edit.color;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.DeviceUtil;
import kotlin.Metadata;
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
public abstract class ColorProgressDrawable extends GradientDrawable {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorProgressDrawable(Context context, int[] colors) {
        super(DeviceUtil.INSTANCE.isRtl(context) ? GradientDrawable.Orientation.LEFT_RIGHT : GradientDrawable.Orientation.RIGHT_LEFT, colors);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(colors, "colors");
        setShape(0);
        setCornerRadius(context.getResources().getDimensionPixelOffset(R.dimen.kg_color_value_editor_seekbar_radius));
    }
}

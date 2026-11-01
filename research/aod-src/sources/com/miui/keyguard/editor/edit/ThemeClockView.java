package com.miui.keyguard.editor.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
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
/* JADX INFO: compiled from: ThemeClockView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ThemeClockView extends FrameLayout {
    public AutoColorPicker autoColorPicker;
    public BaseClockView clockView;
    public CommonConfig templateConfig;
    public Bitmap wallpaperForPickColor;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ThemeClockView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ThemeClockView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThemeClockView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    }

    @Nullable
    public final AutoColorPicker getAutoColorPicker() {
        return this.autoColorPicker;
    }

    public final void setAutoColorPicker(@Nullable AutoColorPicker autoColorPicker) {
        this.autoColorPicker = autoColorPicker;
    }
}

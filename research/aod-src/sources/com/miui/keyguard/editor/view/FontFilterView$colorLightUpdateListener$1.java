package com.miui.keyguard.editor.view;

import androidx.core.graphics.ColorUtils;
import com.miui.keyguard.editor.data.preset.FilterColor;
import com.miui.keyguard.editor.data.preset.FontFilterSelectInfo;
import com.miui.keyguard.editor.homepage.view.adapter.FontFilterColorAdapter;
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
/* JADX INFO: compiled from: FontFilterView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontFilterView$colorLightUpdateListener$1 implements FontColorLightUpdateCallback {
    public final /* synthetic */ FontFilterView this$0;

    public FontFilterView$colorLightUpdateListener$1(FontFilterView fontFilterView) {
        this.this$0 = fontFilterView;
    }

    public void onColorLightChanged(int i, int i2, boolean z) {
        this.this$0.switchToTargetIndex(i);
        if (z) {
            FontFilterSelectInfo fontFilterSelectInfo = this.this$0.currentSelectFilter;
            FontFilterSelectInfo fontFilterSelectInfo2 = null;
            if (fontFilterSelectInfo == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo = null;
            }
            float[] currentHslColor = fontFilterSelectInfo.getCurrentHslColor(i);
            FontFilterSelectInfo fontFilterSelectInfo3 = this.this$0.currentSelectFilter;
            if (fontFilterSelectInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo3 = null;
            }
            FilterColor currentFilterColor = fontFilterSelectInfo3.getCurrentFilterColor(i);
            currentHslColor[2] = i2 / 100.0f;
            if (currentFilterColor != null) {
                currentFilterColor.setColor(ColorUtils.HSLToColor(currentHslColor));
            }
            FontFilterColorAdapter fontFilterColorAdapter = (FontFilterColorAdapter) this.this$0.colorAdapterList.get(i);
            if (fontFilterColorAdapter != null) {
                FontFilterSelectInfo fontFilterSelectInfo4 = this.this$0.currentSelectFilter;
                if (fontFilterSelectInfo4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                } else {
                    fontFilterSelectInfo2 = fontFilterSelectInfo4;
                }
                fontFilterColorAdapter.notifyItemChanged(fontFilterSelectInfo2.getCurrentColorIndex(i));
            }
            this.this$0.markUserModifiedColor(i);
            this.this$0.onSelectColorChange(i);
        }
    }

    public void onMutableChanged(int i, boolean z) {
        this.this$0.switchToTargetIndex(i);
        if (z) {
            this.this$0.showLightSeekBar(i);
        } else {
            this.this$0.dismissLightSeekBar(i);
        }
    }
}

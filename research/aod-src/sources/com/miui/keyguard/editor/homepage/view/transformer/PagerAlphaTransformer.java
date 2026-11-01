package com.miui.keyguard.editor.homepage.view.transformer;

import android.view.View;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
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
/* JADX INFO: compiled from: PagerAlphaTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PagerAlphaTransformer extends ScrollStateChangedPageTransformer {
    public final int orientation;

    public PagerAlphaTransformer(int i) {
        this.orientation = i;
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.PageTransformer
    public void transformPage(View page, float f) {
        Intrinsics.checkNotNullParameter(page, "page");
        Object tag = page.getTag(R.id.kg_template_holder);
        if ((tag instanceof BaseViewHolder) && this.orientation == 0 && getScrollState() != 0) {
            if (f <= -1.0f || f >= 1.0f) {
                ((BaseViewHolder) tag).onPageTransformed(this.orientation, 0.0f, 0.0f, getScrollState());
            } else if (f <= 0.0f) {
                float f2 = 1 + f;
                ((BaseViewHolder) tag).onPageTransformed(this.orientation, f2, f2, getScrollState());
            } else {
                float f3 = 1 - f;
                ((BaseViewHolder) tag).onPageTransformed(this.orientation, f3, f3, getScrollState());
            }
        }
    }
}

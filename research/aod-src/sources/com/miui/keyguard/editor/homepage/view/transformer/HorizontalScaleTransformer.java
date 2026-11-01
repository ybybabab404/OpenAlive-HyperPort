package com.miui.keyguard.editor.homepage.view.transformer;

import android.content.Context;
import android.view.View;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
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
/* JADX INFO: compiled from: HorizontalScaleTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HorizontalScaleTransformer implements ViewPager2.PageTransformer {
    public final float minScale;
    public View pageView;

    public HorizontalScaleTransformer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.minScale = ResourcesProvider.INSTANCE.provideMinScaleForNeighboringItem(context);
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.PageTransformer
    public void transformPage(View page, float f) {
        float f2;
        float f3;
        float f4;
        Intrinsics.checkNotNullParameter(page, "page");
        if (!Intrinsics.areEqual(page, this.pageView)) {
            page.setPivotX(page.getWidth() / 2);
            page.setPivotY(page.getHeight() / 2);
            this.pageView = page;
        }
        if (f <= -1.0f || f >= 1.0f) {
            page.setScaleX(this.minScale);
            page.setScaleY(this.minScale);
            return;
        }
        if (f < 0.0f) {
            f2 = 1;
            f3 = f + f2;
            f4 = this.minScale;
        } else {
            f2 = 1;
            f3 = f2 - f;
            f4 = this.minScale;
        }
        float f5 = (f3 * (f2 - f4)) + f4;
        page.setScaleX(f5);
        page.setScaleY(f5);
    }
}

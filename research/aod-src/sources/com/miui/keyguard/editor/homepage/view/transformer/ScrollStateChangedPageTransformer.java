package com.miui.keyguard.editor.homepage.view.transformer;

import androidx.recyclerview.widget.RecyclerView;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ScrollStateChangedPageTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class ScrollStateChangedPageTransformer extends ViewPager2.OnPageChangeCallback implements ViewPager2.PageTransformer {
    public int pageScrollState;
    public int totalDx;
    public int totalDy;

    public final void attachViewPager2(ViewPager2 viewPager2) {
        Intrinsics.checkNotNullParameter(viewPager2, "viewPager2");
        viewPager2.registerOnPageChangeCallback(this);
        ViewPager2.RecyclerViewImpl recyclerViewImpl = viewPager2.getRecyclerViewImpl();
        if (recyclerViewImpl != null) {
            recyclerViewImpl.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.miui.keyguard.editor.homepage.view.transformer.ScrollStateChangedPageTransformer.attachViewPager2.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
                    super.onScrolled(recyclerView, i, i2);
                    ScrollStateChangedPageTransformer.this.totalDx += i;
                    ScrollStateChangedPageTransformer.this.totalDy += i2;
                }
            });
        }
    }

    public final int getHorizontallyScrollDirection() {
        int i = this.totalDx;
        if (i > 0) {
            return 1;
        }
        return i < 0 ? 2 : 0;
    }

    public final int getScrollState() {
        return this.pageScrollState;
    }

    public final int getVerticallyScrollDirection() {
        int i = this.totalDy;
        if (i > 0) {
            return 1;
        }
        return i < 0 ? 2 : 0;
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
    public void onPageScrollStateChanged(int i) {
        super.onPageScrollStateChanged(i);
        this.pageScrollState = i;
        if (i == 0 || i == 1) {
            this.totalDx = 0;
            this.totalDy = 0;
        }
    }
}

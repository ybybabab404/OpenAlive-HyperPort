package com.miui.keyguard.editor.homepage.view.generator;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.homepage.view.viewpager.SpringLayout;
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
/* JADX INFO: compiled from: CrossViewPagerFactoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CrossViewPagerFactoryImpl extends DynamicViewFactory {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CrossViewPagerFactoryImpl(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final FrameLayout.LayoutParams createMatchParentLayoutParams() {
        return generateFrameLayoutParams(matchParent(), matchParent());
    }

    @Override // com.miui.keyguard.editor.homepage.view.generator.DynamicViewFactory
    public View create(FactoryParams factoryParams) {
        SpringLayout springLayoutGenerateSpringLayout = generateSpringLayout();
        springLayoutGenerateSpringLayout.addView(generateDampViewPager2());
        return springLayoutGenerateSpringLayout;
    }

    public final DampViewPager2 generateDampViewPager2() {
        DampViewPager2 dampViewPager2 = new DampViewPager2(getContext());
        dampViewPager2.setId(R.id.kg_viewpager_vertical);
        dampViewPager2.setLayoutParams(createMatchParentLayoutParams());
        dampViewPager2.setOrientation(1);
        return dampViewPager2;
    }

    public final SpringLayout generateSpringLayout() {
        SpringLayout springLayout = new SpringLayout(getContext());
        springLayout.setId(R.id.kg_spring_layout);
        springLayout.setLayoutParams(createMatchParentLayoutParams());
        return springLayout;
    }
}

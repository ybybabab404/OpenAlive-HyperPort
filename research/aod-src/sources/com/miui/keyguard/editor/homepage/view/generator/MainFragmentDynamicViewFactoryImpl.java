package com.miui.keyguard.editor.homepage.view.generator;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
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
/* JADX INFO: compiled from: MainFragmentDynamicViewFactoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MainFragmentDynamicViewFactoryImpl extends DynamicViewFactory {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MainFragmentDynamicViewFactoryImpl(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final FrameLayout generateRootLayout() {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(generateFrameLayoutParams(matchParent(), matchParent()));
        return frameLayout;
    }

    @Override // com.miui.keyguard.editor.homepage.view.generator.DynamicViewFactory
    public View create(FactoryParams factoryParams) {
        FrameLayout frameLayoutGenerateRootLayout = generateRootLayout();
        CrossViewPager crossViewPagerGenerateCrossViewPager = generateCrossViewPager();
        frameLayoutGenerateRootLayout.addView(crossViewPagerGenerateCrossViewPager);
        crossViewPagerGenerateCrossViewPager.performFinishInflated();
        frameLayoutGenerateRootLayout.addView(generateScreenshotContainer());
        return frameLayoutGenerateRootLayout;
    }

    public final CrossViewPager generateCrossViewPager() {
        CrossViewPager crossViewPager = new CrossViewPager(getContext(), null, 2, null);
        crossViewPager.setId(R.id.kg_viewpager_cross);
        crossViewPager.setLayoutParams(generateFrameLayoutParams(matchParent(), matchParent()));
        crossViewPager.setBackgroundColor(getColor(R.color.kg_editor_background));
        return crossViewPager;
    }

    public final FrameLayout generateScreenshotContainer() {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(R.id.kg_screenshot_container);
        FrameLayout.LayoutParams layoutParamsGenerateFrameLayoutParams = generateFrameLayoutParams(wrapContent(), wrapContent());
        layoutParamsGenerateFrameLayoutParams.gravity = 8388661;
        frameLayout.setLayoutParams(layoutParamsGenerateFrameLayoutParams);
        frameLayout.setVisibility(8);
        return frameLayout;
    }
}

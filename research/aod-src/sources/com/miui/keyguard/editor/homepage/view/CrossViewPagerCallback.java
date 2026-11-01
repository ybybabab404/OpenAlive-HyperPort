package com.miui.keyguard.editor.homepage.view;

import android.view.View;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: CrossViewPager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface CrossViewPagerCallback {

    /* JADX INFO: compiled from: CrossViewPager.kt */
    @Metadata
    public static final class DefaultImpls {
        public static void onAddTemplate(CrossViewPagerCallback crossViewPagerCallback) {
        }

        public static void onCurrentTemplateScreenshot(CrossViewPagerCallback crossViewPagerCallback, BaseTemplateView lockTemplateView, BaseTemplateView baseTemplateView, CommonConfig templateConfig) {
            Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
            Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        }

        public static void onHistoryTemplateReady(CrossViewPagerCallback crossViewPagerCallback) {
        }

        public static void onHomeWallpaperDarkResultReady(CrossViewPagerCallback crossViewPagerCallback) {
        }

        public static void onMyHomeTemplateCreated(CrossViewPagerCallback crossViewPagerCallback, BaseTemplateView baseTemplateView) {
        }

        public static void onMyLockTemplateCreated(CrossViewPagerCallback crossViewPagerCallback, BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        }

        public static /* synthetic */ void onMyLockTemplateCreated$default(CrossViewPagerCallback crossViewPagerCallback, BaseTemplateView baseTemplateView, CommonConfig commonConfig, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onMyLockTemplateCreated");
            }
            if ((i & 2) != 0) {
                commonConfig = null;
            }
            crossViewPagerCallback.onMyLockTemplateCreated(baseTemplateView, commonConfig);
        }

        public static void onPageDeleteCompleted(CrossViewPagerCallback crossViewPagerCallback, int i, int i2, TemplateItemBean templateItemBean) {
        }

        public static boolean onPageDeleted(CrossViewPagerCallback crossViewPagerCallback, int i, int i2, TemplateItemBean templateItemBean) {
            return false;
        }

        public static void onPageEnterDeleteMode(CrossViewPagerCallback crossViewPagerCallback, int i, int i2, View deleteView, boolean z) {
            Intrinsics.checkNotNullParameter(deleteView, "deleteView");
        }

        public static void onPageScrollStateChanged(CrossViewPagerCallback crossViewPagerCallback, int i, int i2, int i3, int i4) {
        }

        public static void onPageSelected(CrossViewPagerCallback crossViewPagerCallback, int i, int i2, int i3, int i4) {
        }

        public static void onPageTransformed(CrossViewPagerCallback crossViewPagerCallback, int i, BaseViewHolder pageHolder, View page, float f, float f2, int i2) {
            Intrinsics.checkNotNullParameter(pageHolder, "pageHolder");
            Intrinsics.checkNotNullParameter(page, "page");
        }

        public static void onTemplateCustomized(CrossViewPagerCallback crossViewPagerCallback, int i, int i2, TemplateItemBean templateItemBean, int i3) {
        }

        public static /* synthetic */ void onTemplateCustomized$default(CrossViewPagerCallback crossViewPagerCallback, int i, int i2, TemplateItemBean templateItemBean, int i3, int i4, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTemplateCustomized");
            }
            if ((i4 & 8) != 0) {
                i3 = 1;
            }
            crossViewPagerCallback.onTemplateCustomized(i, i2, templateItemBean, i3);
        }
    }

    void onAddTemplate();

    void onCurrentTemplateScreenshot(BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, CommonConfig commonConfig);

    void onHistoryTemplateReady();

    void onHomeWallpaperDarkResultReady();

    void onMyHomeTemplateCreated(BaseTemplateView baseTemplateView);

    void onMyLockTemplateCreated(BaseTemplateView baseTemplateView, CommonConfig commonConfig);

    void onPageDeleteCompleted(int i, int i2, TemplateItemBean templateItemBean);

    boolean onPageDeleted(int i, int i2, TemplateItemBean templateItemBean);

    void onPageEnterDeleteMode(int i, int i2, View view, boolean z);

    void onPageScrollStateChanged(int i, int i2, int i3, int i4);

    void onPageSelected(int i, int i2, int i3, int i4);

    void onPageTransformed(int i, BaseViewHolder baseViewHolder, View view, float f, float f2, int i2);

    void onTemplateCustomized(int i, int i2, TemplateItemBean templateItemBean, int i3);
}

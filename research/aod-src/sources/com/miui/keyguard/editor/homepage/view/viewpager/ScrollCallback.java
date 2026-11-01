package com.miui.keyguard.editor.homepage.view.viewpager;

import kotlin.Metadata;

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
/* JADX INFO: compiled from: ScrollHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface ScrollCallback {

    /* JADX INFO: compiled from: ScrollHelper.kt */
    @Metadata
    public static final class DefaultImpls {
        public static void onFling(ScrollCallback scrollCallback, int i, int i2) {
        }

        public static void onPageScrollStateChanged(ScrollCallback scrollCallback, int i) {
        }

        public static void onPageScrolled(ScrollCallback scrollCallback, int i, int i2, int i3) {
        }

        public static void onPageSelectChanged(ScrollCallback scrollCallback, int i) {
        }
    }

    void onFling(int i, int i2);

    void onPageScrollStateChanged(int i);

    void onPageScrolled(int i, int i2, int i3);

    void onPageSelectChanged(int i);
}

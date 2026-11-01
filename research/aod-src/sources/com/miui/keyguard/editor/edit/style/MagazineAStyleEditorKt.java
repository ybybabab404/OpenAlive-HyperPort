package com.miui.keyguard.editor.edit.style;

import com.miui.keyguard.editor.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MagazineAStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagazineAStyleEditorKt {
    public static final List MAGAZINE_A_STYLE_LIST = CollectionsKt.listOf((Object[]) new Integer[]{1, 2, 3});
    public static final List MAGAZINE_A_STYLE_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.string.kg_magazine_a_clock_type_left), Integer.valueOf(R.string.kg_magazine_a_clock_type_center), Integer.valueOf(R.string.kg_magazine_a_clock_type_right)});
    public static final List MAGAZINE_A_STYLE_ALIGN_DRAWABLE_LIST = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_ic_align_left), Integer.valueOf(R.drawable.kg_ic_align_center), Integer.valueOf(R.drawable.kg_ic_align_right)});
}

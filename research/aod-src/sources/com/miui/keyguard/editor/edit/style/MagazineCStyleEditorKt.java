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
/* JADX INFO: compiled from: MagazineCStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagazineCStyleEditorKt {
    public static final List MAGAZINE_C_STYLE_LIST = CollectionsKt.listOf((Object[]) new Integer[]{7, 6, 8, 5, 9});
    public static final List MAGAZINE_C_STYLE_NAMES;

    static {
        int i = R.string.kg_magazine_c_clock_type_extra_bold;
        MAGAZINE_C_STYLE_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(i), Integer.valueOf(R.string.kg_magazine_c_clock_type_bold_stroke_combinatio), Integer.valueOf(R.string.kg_magazine_c_clock_type_stroke), Integer.valueOf(R.string.kg_magazine_c_clock_type_three_lines_bold), Integer.valueOf(i)});
    }
}

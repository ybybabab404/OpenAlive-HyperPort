package com.miui.aod.widget;

import com.miui.aod.category.BigTimeCategoryInfo;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClockSVGTimeView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public enum ClockTimeStyle {
    CLOCK_TIME_STYLE_1(BigTimeCategoryInfo.STYLE_1),
    CLOCK_TIME_STYLE_2(BigTimeCategoryInfo.STYLE_2),
    CLOCK_TIME_STYLE_3(BigTimeCategoryInfo.STYLE_3),
    CLOCK_TIME_STYLE_4(BigTimeCategoryInfo.STYLE_4);

    public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    public final String value;

    ClockTimeStyle(String str) {
        this.value = str;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public final String getValue() {
        return this.value;
    }
}

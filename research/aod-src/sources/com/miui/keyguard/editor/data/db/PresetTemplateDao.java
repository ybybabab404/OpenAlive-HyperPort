package com.miui.keyguard.editor.data.db;

import androidx.room.Dao;
import java.util.List;
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
/* JADX INFO: compiled from: PresetTemplateDao.kt */
/* JADX INFO: loaded from: classes2.dex */
@Dao
@Metadata
public interface PresetTemplateDao {

    /* JADX INFO: compiled from: PresetTemplateDao.kt */
    @Metadata
    public static final class DefaultImpls {
        public static void updateAll(PresetTemplateDao presetTemplateDao, List list) {
            Intrinsics.checkNotNullParameter(list, "list");
            presetTemplateDao.deleteAllData();
            presetTemplateDao.insertAll(list);
        }
    }

    int count();

    int deleteAllData();

    void insertAll(List list);

    boolean needUpdate(String str);

    boolean support(String str);

    void updateAll(List list);
}

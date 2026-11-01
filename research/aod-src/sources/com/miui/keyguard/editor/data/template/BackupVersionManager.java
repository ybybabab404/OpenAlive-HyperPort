package com.miui.keyguard.editor.data.template;

import android.util.Log;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: BackupVersionManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BackupVersionManager {
    public static final BackupVersionManager INSTANCE = new BackupVersionManager();
    public static final Map legacyVersionNames = MapsKt.mapOf(TuplesKt.to(2, TemplateFilePathGenerator.TemplateBackupFileType.BACKUP_ZIP.getFileName()), TuplesKt.to(3, TemplateFilePathGenerator.TemplateBackupFileType.NEW_BACKUP_ZIP.getFileName()));

    public final boolean canCurrentVersionAccept(int i) {
        int iOsVersion = DeviceUtil.INSTANCE.osVersion();
        boolean z = iOsVersion >= i;
        Log.d("Keyguard-Editor-BackupVersionManager", "canCurrentVersionAccept: current=" + iOsVersion + ", backup=" + i + ", result=" + z);
        return z;
    }

    public final Integer extractVersionFromPath(String str) {
        List groupValues;
        String str2;
        Integer intOrNull = null;
        if (str != null && !StringsKt.isBlank(str)) {
            for (Map.Entry entry : legacyVersionNames.entrySet()) {
                int iIntValue = ((Number) entry.getKey()).intValue();
                if (StringsKt.endsWith$default(str, (String) entry.getValue(), false, 2, (Object) null)) {
                    Log.d("Keyguard-Editor-BackupVersionManager", "extractVersionFromPath: matched legacy, version=" + iIntValue);
                    return Integer.valueOf(iIntValue);
                }
            }
            MatchResult matchResultFind$default = Regex.find$default(new Regex("template_backup_os(\\d+)_zip$"), str, 0, 2, null);
            if (matchResultFind$default != null && (groupValues = matchResultFind$default.getGroupValues()) != null && (str2 = (String) groupValues.get(1)) != null) {
                intOrNull = StringsKt.toIntOrNull(str2);
            }
            Log.d("Keyguard-Editor-BackupVersionManager", "extractVersionFromPath: path=" + str + ", version=" + intOrNull);
        }
        return intOrNull;
    }

    public final String generateZipFileName(int i) {
        String str = (String) legacyVersionNames.get(Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        return "template_backup_os" + i + "_zip";
    }
}

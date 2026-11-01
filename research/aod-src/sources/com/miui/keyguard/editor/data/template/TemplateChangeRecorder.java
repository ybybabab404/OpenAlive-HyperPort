package com.miui.keyguard.editor.data.template;

import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Map;
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
/* JADX INFO: compiled from: TemplateChangeRecorder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateChangeRecorder {
    public Map mLastStatusMemory = new LinkedHashMap();

    public final void clearAllRecord() {
        Log.d("TemplateChangeRecorder", "clearAllRecord: mLastStatusMemory=" + this.mLastStatusMemory);
        this.mLastStatusMemory.clear();
    }

    public final void clearRecord(int i) {
        Log.d("TemplateChangeRecorder", "clearRecord for reason=" + i + " removeRecord=" + ((StatusRecord) this.mLastStatusMemory.remove(Integer.valueOf(i))));
    }

    public final void clearRecord(Integer[] reasons) {
        Intrinsics.checkNotNullParameter(reasons, "reasons");
        for (Integer num : reasons) {
            clearRecord(num.intValue());
        }
    }

    public final StatusRecord getLastStatusRecord(int i) {
        StatusRecord statusRecord = (StatusRecord) this.mLastStatusMemory.remove(Integer.valueOf(i));
        Log.d("TemplateChangeRecorder", "getLastStatusMemory: reason=" + i + " record=" + statusRecord);
        return statusRecord;
    }

    public final void putLastStatusRecord(int i, StatusRecord record) {
        Intrinsics.checkNotNullParameter(record, "record");
        if (this.mLastStatusMemory.containsKey(Integer.valueOf(i))) {
            Log.d("TemplateChangeRecorder", "putLastStatusRecord: reason=" + i + " already have record=" + this.mLastStatusMemory.get(Integer.valueOf(i)));
            return;
        }
        Log.d("TemplateChangeRecorder", "putLastStatusRecord: reason=" + i + " record=" + record);
        this.mLastStatusMemory.put(Integer.valueOf(i), record);
    }
}

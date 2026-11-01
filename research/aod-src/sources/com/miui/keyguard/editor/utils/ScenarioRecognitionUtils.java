package com.miui.keyguard.editor.utils;

import android.os.SystemClock;
import com.scenariorecognition.ScenarioRecognition;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ScenarioRecognitionUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ScenarioRecognitionUtils {
    public static final ScenarioRecognitionUtils INSTANCE = new ScenarioRecognitionUtils();

    public final long getCurrentTime() {
        return SystemClock.elapsedRealtime();
    }

    public final void notifyScenarioChanged(long j, long j2) {
        ScenarioRecognition.getInstance().notifyScenarioChanged(j, j2, false, null);
    }

    public final void setScenarioStateEnd(long j) {
        ScenarioRecognition.getInstance().setScenarioState(j, getCurrentTime(), false);
    }

    public final void setScenarioStateStart(long j) {
        ScenarioRecognition.getInstance().setScenarioState(j, getCurrentTime(), true);
    }
}

package com.miui.keyguard.editor.utils.segment;

import android.util.Log;
import com.miui.keyguard.editor.utils.task.Task;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.vision.imagesegment.ImageSegmentManager;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ImageSegmentHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ImageSegmentHandler$mAiServiceConnection$2$1 implements AiServiceConnection {
    public static final void onServiceDisconnected$lambda$3() throws InterruptedException {
        ImageSegmentManager imageSegmentManager = ImageSegmentHandler.segmentManager;
        if (imageSegmentManager != null) {
            imageSegmentManager.releaseConnect();
        }
        ImageSegmentHandler.segmentManager = null;
        int i = 0;
        while (!ImageSegmentHandler.INSTANCE.initializeInternal() && i < ImageSegmentHandler.CONNECT_MAX_COUNT) {
            ImageSegmentManager imageSegmentManager2 = ImageSegmentHandler.segmentManager;
            if (imageSegmentManager2 != null) {
                imageSegmentManager2.releaseConnect();
            }
            ImageSegmentHandler.segmentManager = null;
            i++;
            Thread.sleep(1000L);
        }
        Log.i("ImageSegmentHandler", "onServiceDisconnected retryConnext count:" + i);
    }

    @Override // com.xiaomi.aicr.common.AiServiceConnection
    public void onServiceConnected(int i) {
        super.onServiceConnected(i);
        Log.i("ImageSegmentHandler", "onServiceConnected connectStatus=" + i);
    }

    @Override // com.xiaomi.aicr.common.AiServiceConnection
    public void onServiceDisconnected() {
        super.onServiceDisconnected();
        Log.i("ImageSegmentHandler", "onServiceDisconnected");
        Iterator it = ImageSegmentHandler.serviceListeners.iterator();
        while (it.hasNext()) {
            ((VideoSegmentServiceListener) it.next()).onAiServiceDisconnected();
        }
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.utils.segment.ImageSegmentHandler$mAiServiceConnection$2$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                ImageSegmentHandler$mAiServiceConnection$2$1.onServiceDisconnected$lambda$3();
            }
        });
    }
}

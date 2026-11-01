package com.miui.keyguard.editor.edit.base;

import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.segment.ImageSegmentHandler;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import java.util.function.Consumer;
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
/* JADX INFO: compiled from: ImageSegmentEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ImageSegmentEditor {
    public final ImageSegmentHandler segmentHandler;
    public SegmentInfo subjectInfo;
    public final EditFragmentViewModel viewModel;

    public ImageSegmentEditor(EditFragmentViewModel viewModel) {
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        this.viewModel = viewModel;
        this.segmentHandler = ImageSegmentHandler.INSTANCE;
    }

    public static final void onWallpaperChanged$lambda$1$lambda$0(WallpaperChangedCallBack wallpaperChangedCallBack, ImageSegmentEditor imageSegmentEditor, SegmentInfo it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Bitmap bitmap = it.getBitmap();
        if (bitmap != null) {
            bitmap.setDensity(DeviceUtil.initialDensityDpi$default(DeviceUtil.INSTANCE, 0, 1, null));
        }
        if (it.getBitmap() == null || !it.isRectValid()) {
            it = new SegmentInfo(0L, null, null, 1, null);
        }
        wallpaperChangedCallBack.doCallBack(it);
        imageSegmentEditor.subjectInfo = it;
    }

    public final SegmentInfo getSubjectInfo() {
        return this.subjectInfo;
    }

    public final void onWallpaperChanged(Bitmap bitmap, final WallpaperChangedCallBack callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (bitmap != null) {
            this.segmentHandler.segment(bitmap, new Consumer() { // from class: com.miui.keyguard.editor.edit.base.ImageSegmentEditor$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImageSegmentEditor.onWallpaperChanged$lambda$1$lambda$0(callback, this, (SegmentInfo) obj);
                }
            });
        }
    }

    public final void setSubjectInfo(SegmentInfo segmentInfo) {
        if (segmentInfo != null) {
            Bitmap bitmap = segmentInfo.getBitmap();
            Integer numValueOf = bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null;
            Bitmap bitmap2 = segmentInfo.getBitmap();
            Log.d("ImageSegmentEditor", "setSubjectInfo: w=" + numValueOf + " h=" + (bitmap2 != null ? Integer.valueOf(bitmap2.getHeight()) : null));
        } else {
            segmentInfo = null;
        }
        this.subjectInfo = segmentInfo;
    }
}

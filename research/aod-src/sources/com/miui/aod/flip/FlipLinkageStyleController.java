package com.miui.aod.flip;

import android.content.Context;
import android.util.Log;
import com.miui.aod.Utils;
import com.miui.aod.resource.CachedSettings;
import com.miui.aod.widget.Direction;
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
/* JADX INFO: compiled from: FlipLinkageStyleController.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FlipLinkageStyleController {
    public static boolean isFlipped;
    public static final FlipLinkageStyleController INSTANCE = new FlipLinkageStyleController();
    public static Direction orientation = Direction.CAMERA_CUTOUT_ON_NONE;

    public final Direction getClockOrientation() {
        return orientation;
    }

    public final boolean isFlipAodEnable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return CachedSettings.Secure.getInt(context, "doze_always_on", 0) == 1;
    }

    public final boolean isFlipped() {
        return isFlipped;
    }

    public final boolean isUsingFlip(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Utils.isFlipDevice() && isFlipAodEnable(context);
    }

    public final void reset() {
        Log.d("FlipLinkageStyleController", "reset");
        isFlipped = false;
        orientation = Direction.CAMERA_CUTOUT_ON_NONE;
    }

    public final void setFlipped(boolean z) {
        isFlipped = z;
    }

    public final void updateClockOrientation(Direction direction) {
        Intrinsics.checkNotNullParameter(direction, "direction");
        orientation = direction;
    }
}

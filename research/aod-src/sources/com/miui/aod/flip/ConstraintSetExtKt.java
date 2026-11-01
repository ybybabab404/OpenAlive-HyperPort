package com.miui.aod.flip;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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
/* JADX INFO: compiled from: ConstraintSetExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ConstraintSetExtKt {
    public static final void ai_Pets(ConstraintSet constraintSet, Ids ids, Direction direction, ConstraintLayout layout) {
        Intrinsics.checkNotNullParameter(constraintSet, "<this>");
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(layout, "layout");
        constraintSet.clone(layout);
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getParent(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getParent(), 2);
        } else if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getGideLineVerticalId(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getGideLineVerticalId(), 2);
        }
        constraintSet.applyTo(layout);
    }

    public static final void bubble(ConstraintSet constraintSet, Ids ids, Direction direction, ConstraintLayout layout) {
        Intrinsics.checkNotNullParameter(constraintSet, "<this>");
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(layout, "layout");
        constraintSet.clone(layout);
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getParent(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getParent(), 2);
        } else if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getGideLineVerticalId(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getGideLineVerticalId(), 2);
        }
        constraintSet.applyTo(layout);
    }

    public static final void classic(ConstraintSet constraintSet, Ids ids, Direction direction, ConstraintLayout layout) {
        Intrinsics.checkNotNullParameter(constraintSet, "<this>");
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(layout, "layout");
        constraintSet.clone(layout);
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getParent(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getParent(), 2);
        } else if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getGideLineVerticalId(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getParent(), 1);
        }
        constraintSet.applyTo(layout);
    }

    public static final void doodle(ConstraintSet constraintSet, Ids ids, Direction direction, ConstraintLayout layout) {
        Intrinsics.checkNotNullParameter(constraintSet, "<this>");
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(layout, "layout");
        constraintSet.clone(layout);
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getParent(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getParent(), 2);
        } else if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getGideLineVerticalId(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getGideLineVerticalId(), 2);
        }
        constraintSet.applyTo(layout);
    }

    public static final void interactive_Pets(ConstraintSet constraintSet, Ids ids, Direction direction, ConstraintLayout layout) {
        Intrinsics.checkNotNullParameter(constraintSet, "<this>");
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(layout, "layout");
        constraintSet.clone(layout);
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getParent(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getGideLineVerticalId(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getParent(), 2);
        } else if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getGideLineVerticalId(), 2);
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getGideLineVerticalId(), 2);
        }
        constraintSet.applyTo(layout);
    }

    public static final void pets(ConstraintSet constraintSet, Ids ids, Direction direction, ConstraintLayout layout) {
        Intrinsics.checkNotNullParameter(constraintSet, "<this>");
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(layout, "layout");
        constraintSet.clone(layout);
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 2, ids.getParent(), 2);
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getClockContainerId(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getParent(), 2);
        } else if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            constraintSet.clear(ids.getClockContainerId(), 1);
            constraintSet.clear(ids.getClockContainerId(), 2);
            constraintSet.connect(ids.getClockContainerId(), 1, ids.getParent(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getClockContainerId(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getGideLineVerticalId(), 2);
        }
        constraintSet.applyTo(layout);
    }

    public static final void splicing(ConstraintSet constraintSet, Ids ids, Direction direction, ConstraintLayout layout) {
        Intrinsics.checkNotNullParameter(constraintSet, "<this>");
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(layout, "layout");
        constraintSet.clone(layout);
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getParent(), 2);
        } else if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            constraintSet.clear(ids.getClockInfoContainerId(), 1);
            constraintSet.clear(ids.getClockInfoContainerId(), 2);
            constraintSet.connect(ids.getClockInfoContainerId(), 1, ids.getParent(), 1);
            constraintSet.connect(ids.getClockInfoContainerId(), 2, ids.getGideLineVerticalId(), 2);
        }
        constraintSet.applyTo(layout);
    }
}

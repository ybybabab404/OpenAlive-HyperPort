package com.miui.keyguard.editor.data.bean;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperPositionInfo {
    private float bottom;
    private float left;

    @Nullable
    private float[] matrixArr;

    @Nullable
    private Float[] offset;

    @Nullable
    private Integer orientation;
    private float right;
    private float top;

    @Nullable
    private Boolean useSquareArea;

    public WallpaperPositionInfo(float f, float f2, float f3, float f4, @Nullable float[] fArr, @Nullable Boolean bool, @Nullable Float[] fArr2, @Nullable Integer num) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
        this.matrixArr = fArr;
        this.useSquareArea = bool;
        this.offset = fArr2;
        this.orientation = num;
    }

    public /* synthetic */ WallpaperPositionInfo(float f, float f2, float f3, float f4, float[] fArr, Boolean bool, Float[] fArr2, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, fArr, (i & 32) != 0 ? null : bool, (i & 64) != 0 ? null : fArr2, (i & 128) != 0 ? null : num);
    }

    public static /* synthetic */ WallpaperPositionInfo copy$default(WallpaperPositionInfo wallpaperPositionInfo, float f, float f2, float f3, float f4, float[] fArr, Boolean bool, Float[] fArr2, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            f = wallpaperPositionInfo.left;
        }
        if ((i & 2) != 0) {
            f2 = wallpaperPositionInfo.top;
        }
        if ((i & 4) != 0) {
            f3 = wallpaperPositionInfo.right;
        }
        if ((i & 8) != 0) {
            f4 = wallpaperPositionInfo.bottom;
        }
        if ((i & 16) != 0) {
            fArr = wallpaperPositionInfo.matrixArr;
        }
        if ((i & 32) != 0) {
            bool = wallpaperPositionInfo.useSquareArea;
        }
        if ((i & 64) != 0) {
            fArr2 = wallpaperPositionInfo.offset;
        }
        if ((i & 128) != 0) {
            num = wallpaperPositionInfo.orientation;
        }
        Float[] fArr3 = fArr2;
        Integer num2 = num;
        float[] fArr4 = fArr;
        Boolean bool2 = bool;
        return wallpaperPositionInfo.copy(f, f2, f3, f4, fArr4, bool2, fArr3, num2);
    }

    public final float component1() {
        return this.left;
    }

    public final float component2() {
        return this.top;
    }

    public final float component3() {
        return this.right;
    }

    public final float component4() {
        return this.bottom;
    }

    @Nullable
    public final float[] component5() {
        return this.matrixArr;
    }

    @Nullable
    public final Boolean component6() {
        return this.useSquareArea;
    }

    @Nullable
    public final Float[] component7() {
        return this.offset;
    }

    @Nullable
    public final Integer component8() {
        return this.orientation;
    }

    @NotNull
    public final WallpaperPositionInfo copy(float f, float f2, float f3, float f4, @Nullable float[] fArr, @Nullable Boolean bool, @Nullable Float[] fArr2, @Nullable Integer num) {
        return new WallpaperPositionInfo(f, f2, f3, f4, fArr, bool, fArr2, num);
    }

    @NotNull
    public final WallpaperPositionInfo deepCopy() {
        WallpaperPositionInfo wallpaperPositionInfoCopy$default = copy$default(this, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, 255, null);
        float[] fArr = this.matrixArr;
        wallpaperPositionInfoCopy$default.matrixArr = fArr != null ? (float[]) fArr.clone() : null;
        return wallpaperPositionInfoCopy$default;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(WallpaperPositionInfo.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.data.bean.WallpaperPositionInfo");
        WallpaperPositionInfo wallpaperPositionInfo = (WallpaperPositionInfo) obj;
        if (this.left != wallpaperPositionInfo.left || this.top != wallpaperPositionInfo.top || this.right != wallpaperPositionInfo.right || this.bottom != wallpaperPositionInfo.bottom) {
            return false;
        }
        float[] fArr = this.matrixArr;
        if (fArr != null) {
            float[] fArr2 = wallpaperPositionInfo.matrixArr;
            if (fArr2 == null || !Arrays.equals(fArr, fArr2)) {
                return false;
            }
        } else if (wallpaperPositionInfo.matrixArr != null) {
            return false;
        }
        return true;
    }

    public final float getBottom() {
        return this.bottom;
    }

    public final float getLeft() {
        return this.left;
    }

    @Nullable
    public final float[] getMatrixArr() {
        return this.matrixArr;
    }

    @Nullable
    public final Float[] getOffset() {
        return this.offset;
    }

    @Nullable
    public final Integer getOrientation() {
        return this.orientation;
    }

    public final float getRight() {
        return this.right;
    }

    public final float getTop() {
        return this.top;
    }

    @Nullable
    public final Boolean getUseSquareArea() {
        return this.useSquareArea;
    }

    public int hashCode() {
        int iHashCode = ((((((Float.hashCode(this.left) * 31) + Float.hashCode(this.top)) * 31) + Float.hashCode(this.right)) * 31) + Float.hashCode(this.bottom)) * 31;
        float[] fArr = this.matrixArr;
        return iHashCode + (fArr != null ? Arrays.hashCode(fArr) : 0);
    }

    public final int hashCodeWithPrecisionCorrection() {
        Float f;
        Float f2;
        int iHashCode = (((((Float.hashCode(this.left) * 31) + Float.hashCode(this.top)) * 31) + Float.hashCode(this.right)) * 31) + Float.hashCode(this.bottom);
        if (this.matrixArr == null) {
            return iHashCode;
        }
        WallpaperPositionInfo wallpaperPositionInfoCopy$default = copy$default(this, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, 255, null);
        float[] fArr = this.matrixArr;
        float[] fArr2 = fArr != null ? (float[]) fArr.clone() : null;
        wallpaperPositionInfoCopy$default.matrixArr = fArr2;
        if (fArr2 != null) {
            float f3 = fArr2[2];
            Float[] fArr3 = this.offset;
            float fFloatValue = 0.0f;
            fArr2[2] = f3 + ((fArr3 == null || (f2 = (Float) ArraysKt.getOrNull(fArr3, 0)) == null) ? 0.0f : f2.floatValue());
            float f4 = fArr2[5];
            Float[] fArr4 = this.offset;
            if (fArr4 != null && (f = (Float) ArraysKt.getOrNull(fArr4, 1)) != null) {
                fFloatValue = f.floatValue();
            }
            fArr2[5] = f4 + fFloatValue;
            int length = fArr2.length;
            for (int i = 0; i < length; i++) {
                fArr2[i] = (float) Math.rint(fArr2[i]);
            }
        }
        return (iHashCode * 31) + Arrays.hashCode(fArr2);
    }

    public final void setBottom(float f) {
        this.bottom = f;
    }

    public final void setLeft(float f) {
        this.left = f;
    }

    public final void setMatrixArr(@Nullable float[] fArr) {
        this.matrixArr = fArr;
    }

    public final void setOffset(@Nullable Float[] fArr) {
        this.offset = fArr;
    }

    public final void setOrientation(@Nullable Integer num) {
        this.orientation = num;
    }

    public final void setRight(float f) {
        this.right = f;
    }

    public final void setTop(float f) {
        this.top = f;
    }

    public final void setUseSquareArea(@Nullable Boolean bool) {
        this.useSquareArea = bool;
    }

    @NotNull
    public String toString() {
        return "WallpaperPositionInfo(left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + ", matrixArr=" + Arrays.toString(this.matrixArr) + ", useSquareArea=" + this.useSquareArea + ", offset=" + Arrays.toString(this.offset) + ", orientation=" + this.orientation + ")";
    }
}

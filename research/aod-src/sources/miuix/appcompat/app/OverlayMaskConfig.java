package miuix.appcompat.app;

import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class OverlayMaskConfig {
    public static final OverlayMaskConfig BLACK_LINEAR = new OverlayMaskConfig(Integer.MIN_VALUE, new float[]{1.0f, 0.0f}, new float[]{0.0f, 1.0f});
    public static final float[] DEFAULT_ALPHA_FACTORS;
    public static final float[] DEFAULT_POSITIONS;
    public static final OverlayMaskConfig GRAY;
    public static final OverlayMaskConfig WHITE;
    public final float[] alphaFactors;
    public final int maskColor;
    public final float[] positions;

    static {
        float[] fArr = {0.98f, 0.98f, 0.97f, 0.94f, 0.91f, 0.86f, 0.8f, 0.74f, 0.67f, 0.59f, 0.51f, 0.42f, 0.34f, 0.25f, 0.16f, 0.08f, 0.0f};
        DEFAULT_ALPHA_FACTORS = fArr;
        float[] fArr2 = {0.0f, 0.5f, 0.555f, 0.605f, 0.65f, 0.69f, 0.725f, 0.76f, 0.785f, 0.815f, 0.84f, 0.865f, 0.89f, 0.915f, 0.94f, 0.97f, 1.0f};
        DEFAULT_POSITIONS = fArr2;
        WHITE = new OverlayMaskConfig(-1, fArr, fArr2);
        GRAY = new OverlayMaskConfig(-789517, fArr, fArr2);
    }

    public OverlayMaskConfig(int i, float[] fArr, float[] fArr2) {
        if (fArr == null || fArr2 == null || fArr.length == 0 || fArr.length != fArr2.length) {
            throw new IllegalArgumentException("alphaFactors and positions must be non-null, non-empty, and of equal length");
        }
        this.maskColor = i;
        this.alphaFactors = Arrays.copyOf(fArr, fArr.length);
        this.positions = Arrays.copyOf(fArr2, fArr2.length);
    }

    public static float[] getDefaultAlphaFactors() {
        float[] fArr = DEFAULT_ALPHA_FACTORS;
        return Arrays.copyOf(fArr, fArr.length);
    }

    public static float[] getDefaultPositions() {
        float[] fArr = DEFAULT_POSITIONS;
        return Arrays.copyOf(fArr, fArr.length);
    }

    public float[] getAlphaFactors() {
        return this.alphaFactors;
    }

    public int getMaskColor() {
        return this.maskColor;
    }

    public float[] getPositions() {
        return this.positions;
    }
}

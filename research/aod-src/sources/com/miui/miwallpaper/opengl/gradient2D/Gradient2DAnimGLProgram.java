package com.miui.miwallpaper.opengl.gradient2D;

import android.content.Context;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient2DAnimGLProgram extends GlassAnimGLProgram {
    public float animSpeed;
    public float[][][][] cColor;
    public float[][][] cOffset;
    public float[][][] cPos;
    public int col;
    public float colorChangeDuration;
    public float[][][] currentColor;
    public float globalStartTime;
    public GridPoint[][] gridPoints;
    public float lightProgress;
    public Gradient2DAnimGLWallpaper mGradient2DGLWallpaper;
    public float offsetRange;
    public Patch[][] patches;
    public int row;
    public float time;
    public float[] uPatchB;
    public float[] uPatchG;
    public float[] uPatchR;
    public float[] uPatchX;
    public float[] uPatchY;

    public class GridPoint {
        public float b;
        public float dx;
        public float dy;
        public float g;
        public float r;
        public float x;
        public float y;

        public GridPoint() {
        }

        public void set(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            this.x = f;
            this.y = f2;
            this.r = f3;
            this.g = f4;
            this.b = f5;
            this.dx = f6;
            this.dy = f7;
        }
    }

    public class Patch {
        public float[][] b;
        public float[][] g;
        public float[][] r;
        public float[][] x;
        public float[][] y;

        public Patch() {
        }
    }

    public Gradient2DAnimGLProgram(Context context) {
        super(context);
        this.cPos = new float[][][]{new float[][]{new float[]{-0.14f, -0.03f}, new float[]{0.6f, -0.04f}, new float[]{1.08f, -0.05f}}, new float[][]{new float[]{-0.31f, 0.34f}, new float[]{0.39f, 0.42f}, new float[]{1.21f, 0.26f}}, new float[][]{new float[]{-0.13f, 0.66f}, new float[]{0.65f, 0.85f}, new float[]{1.3f, 0.66f}}, new float[][]{new float[]{-0.52f, 1.06f}, new float[]{0.4f, 1.06f}, new float[]{1.24f, 1.05f}}};
        this.cColor = new float[][][][]{new float[][][]{new float[][]{hexToRgb("#D6DDFF"), hexToRgb("#B8C0FF"), hexToRgb("#FFE9E5")}, new float[][]{hexToRgb("#FFD9CC"), hexToRgb("#FFB8C2"), hexToRgb("#FFF1E5")}, new float[][]{hexToRgb("#CCDDFF"), hexToRgb("#FFF8EB"), hexToRgb("#FFDFD1")}, new float[][]{hexToRgb("#FEE9DA"), hexToRgb("#FFEADB"), hexToRgb("#FFF2EB")}}, new float[][][]{new float[][]{hexToRgb("#D6DDFF"), hexToRgb("#B8C0FF"), hexToRgb("#FFE9E5")}, new float[][]{hexToRgb("#FFD9CC"), hexToRgb("#A3B2FF"), hexToRgb("#FFF1E5")}, new float[][]{hexToRgb("#CCDDFF"), hexToRgb("#FFF8EB"), hexToRgb("#FFDFD1")}, new float[][]{hexToRgb("#FEE9DA"), hexToRgb("#FFEADB"), hexToRgb("#FFF2EB")}}, new float[][][]{new float[][]{hexToRgb("#FFBDDD"), hexToRgb("#FFE8E0"), hexToRgb("#E6DBFF")}, new float[][]{hexToRgb("#FFD9CC"), hexToRgb("#D1B8FF"), hexToRgb("#FFF1E5")}, new float[][]{hexToRgb("#FFECE5"), hexToRgb("#FFBAB3"), hexToRgb("#FFDFD1")}, new float[][]{hexToRgb("#FEE9DA"), hexToRgb("#FFEADB"), hexToRgb("#FFF2EB")}}};
        this.cOffset = new float[][][]{new float[][]{new float[]{0.0f, 0.0f}, new float[]{0.1f, 0.0f}, new float[]{0.0f, 0.0f}}, new float[][]{new float[]{0.0f, 0.1f}, new float[]{0.25f, 0.15f}, new float[]{0.0f, 0.1f}}, new float[][]{new float[]{0.0f, 0.1f}, new float[]{0.2f, 0.1f}, new float[]{0.0f, 0.1f}}, new float[][]{new float[]{0.0f, 0.0f}, new float[]{0.1f, 0.0f}, new float[]{0.0f, 0.0f}}};
        this.currentColor = (float[][][]) Array.newInstance((Class<?>) Float.TYPE, 4, 3, 3);
        this.row = 4;
        this.col = 3;
        this.offsetRange = 0.6f;
        this.time = 0.0f;
        this.lightProgress = 1.0f;
        this.colorChangeDuration = 0.8f;
        this.animSpeed = 0.3f;
        this.globalStartTime = 0.0f;
        this.gridPoints = (GridPoint[][]) Array.newInstance((Class<?>) GridPoint.class, 4, 3);
        this.patches = (Patch[][]) Array.newInstance((Class<?>) Patch.class, 4, 3);
        this.uPatchX = new float[96];
        this.uPatchY = new float[96];
        this.uPatchR = new float[96];
        this.uPatchG = new float[96];
        this.uPatchB = new float[96];
        initParam();
        this.globalStartTime = System.nanoTime();
    }

    public static float[] hexToRgb(String str) {
        return new float[]{Integer.parseInt(str.substring(1, 3), 16) / 255.0f, Integer.parseInt(str.substring(3, 5), 16) / 255.0f, Integer.parseInt(str.substring(5, 7), 16) / 255.0f};
    }

    public static float lerp(float f, float f2, float f3) {
        return f + (f3 * (f2 - f));
    }

    public static float sinInRange(float f, float f2, float f3, float f4, float f5) {
        return ((f2 + f) / 2.0f) + (((f2 - f) / 2.0f) * ((float) Math.sin((f5 * f4) + f3)));
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        useCreateGLProgram();
        GLES30.glClear(16384);
        float fNanoTime = (System.nanoTime() - this.globalStartTime) / 1.0E9f;
        computeColor(this.animSpeed * fNanoTime);
        computeGrid(fNanoTime * this.animSpeed);
        getPatches();
        flattenArray(this.patches);
        GLES30.glUniformMatrix4fv(this.mGradient2DGLWallpaper.uPatchX, 6, false, this.uPatchX, 0);
        GLES30.glUniformMatrix4fv(this.mGradient2DGLWallpaper.uPatchY, 6, false, this.uPatchY, 0);
        GLES30.glUniformMatrix4fv(this.mGradient2DGLWallpaper.uPatchR, 6, false, this.uPatchR, 0);
        GLES30.glUniformMatrix4fv(this.mGradient2DGLWallpaper.uPatchG, 6, false, this.uPatchG, 0);
        GLES30.glUniformMatrix4fv(this.mGradient2DGLWallpaper.uPatchB, 6, false, this.uPatchB, 0);
        GLES30.glBindVertexArray(this.mGradient2DGLWallpaper.mVAO);
        GLES30.glDrawElements(4, this.mGradient2DGLWallpaper.index.length, 5125, 0);
        GLES30.glBindVertexArray(0);
    }

    public void computeColor(float f) {
        float f2 = f / this.colorChangeDuration;
        int iFloor = ((int) Math.floor(f2)) % 3;
        float f3 = f2 % 1.0f;
        int i = (iFloor + 1) % 3;
        for (int i2 = 0; i2 < this.row; i2++) {
            for (int i3 = 0; i3 < this.col; i3++) {
                for (int i4 = 0; i4 < 3; i4++) {
                    float[] fArr = this.currentColor[i2][i3];
                    float[][][][] fArr2 = this.cColor;
                    fArr[i4] = lerp(fArr2[iFloor][i2][i3][i4], fArr2[i][i2][i3][i4], f3);
                }
            }
        }
    }

    public void computeGrid(float f) {
        float f2 = 1.0f / (this.row - 1.0f);
        float f3 = 1.0f / (this.col - 1.0f);
        for (int i = 0; i < this.row; i++) {
            int i2 = 0;
            while (true) {
                int i3 = this.col;
                if (i2 < i3) {
                    int i4 = (i3 * i) + i2;
                    float[] fArr = this.cPos[i][i2];
                    float[] fArr2 = this.currentColor[i][i2];
                    float[] fArr3 = this.cOffset[i][i2];
                    this.gridPoints[i][i2].set(fArr[0] + (this.offsetRange * fArr3[0] * sinInRange(-1.0f, 1.0f, i4 + 50, 1.0f, f) * this.lightProgress), fArr[1] + (this.offsetRange * fArr3[1] * sinInRange(-1.0f, 1.0f, i4, 1.0f, 1.1f * f) * this.lightProgress), fArr2[0], fArr2[1], fArr2[2], f2, f3);
                    i2++;
                }
            }
        }
    }

    public void flattenArray(Patch[][] patchArr) {
        int i = 0;
        for (int i2 = 0; i2 < this.row - 1; i2++) {
            for (int i3 = 0; i3 < this.col - 1; i3++) {
                for (int i4 = 0; i4 < 4; i4++) {
                    for (int i5 = 0; i5 < 4; i5++) {
                        float[] fArr = this.uPatchX;
                        Patch patch = patchArr[i2][i3];
                        fArr[i] = patch.x[i4][i5];
                        this.uPatchY[i] = patch.y[i4][i5];
                        this.uPatchR[i] = patch.r[i4][i5];
                        this.uPatchG[i] = patch.g[i4][i5];
                        this.uPatchB[i] = patch.b[i4][i5];
                        i++;
                    }
                }
            }
        }
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.mesh_fragment_shader;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        Gradient2DAnimGLWallpaper gradient2DAnimGLWallpaper = new Gradient2DAnimGLWallpaper(this);
        this.mGradient2DGLWallpaper = gradient2DAnimGLWallpaper;
        return gradient2DAnimGLWallpaper;
    }

    public void getPatchAttribute(int i, int i2) {
        GridPoint[][] gridPointArr = this.gridPoints;
        GridPoint[] gridPointArr2 = gridPointArr[i];
        GridPoint gridPoint = gridPointArr2[i2];
        GridPoint[] gridPointArr3 = gridPointArr[i + 1];
        GridPoint gridPoint2 = gridPointArr3[i2];
        int i3 = i2 + 1;
        GridPoint gridPoint3 = gridPointArr2[i3];
        GridPoint gridPoint4 = gridPointArr3[i3];
        Patch patch = this.patches[i][i2];
        patch.x = new float[][]{new float[]{gridPoint.x, gridPoint2.x, 0.0f, 0.0f}, new float[]{gridPoint3.x, gridPoint4.x, 0.0f, 0.0f}, new float[]{gridPoint.dy, gridPoint2.dy, 0.0f, 0.0f}, new float[]{gridPoint3.dy, gridPoint4.dy, 0.0f, 0.0f}};
        patch.y = new float[][]{new float[]{gridPoint.y, gridPoint2.y, gridPoint.dx, gridPoint2.dx}, new float[]{gridPoint3.y, gridPoint4.y, gridPoint3.dx, gridPoint4.dx}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}};
        patch.r = new float[][]{new float[]{gridPoint.r, gridPoint2.r, 0.0f, 0.0f}, new float[]{gridPoint3.r, gridPoint4.r, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}};
        patch.g = new float[][]{new float[]{gridPoint.g, gridPoint2.g, 0.0f, 0.0f}, new float[]{gridPoint3.g, gridPoint4.g, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}};
        patch.b = new float[][]{new float[]{gridPoint.b, gridPoint2.b, 0.0f, 0.0f}, new float[]{gridPoint3.b, gridPoint4.b, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}};
    }

    public void getPatches() {
        for (int i = 0; i < this.row - 1; i++) {
            for (int i2 = 0; i2 < this.col - 1; i2++) {
                getPatchAttribute(i, i2);
            }
        }
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.mesh_vertex_shader;
    }

    public void initParam() {
        for (int i = 0; i < this.row; i++) {
            for (int i2 = 0; i2 < this.col; i2++) {
                this.gridPoints[i][i2] = new GridPoint();
                this.patches[i][i2] = new Patch();
            }
        }
    }
}

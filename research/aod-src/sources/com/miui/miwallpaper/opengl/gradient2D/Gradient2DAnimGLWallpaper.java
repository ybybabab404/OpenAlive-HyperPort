package com.miui.miwallpaper.opengl.gradient2D;

import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient2DAnimGLWallpaper extends AnimImageGLWallpaper {
    public int col;
    public int[] index;
    public int mEBO;
    public int mVAO;
    public int mVBO;
    public int meshCount;
    public int row;
    public int uPatchB;
    public int uPatchG;
    public int uPatchR;
    public int uPatchX;
    public int uPatchY;

    public Gradient2DAnimGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
        this.row = 4;
        this.col = 3;
        this.meshCount = 20;
        generateIndex();
    }

    public void generateIndex() {
        int i = (this.row - 1) * (this.col - 1);
        int i2 = this.meshCount;
        this.index = new int[i * (i2 - 1) * (i2 - 1) * 6];
        int i3 = 0;
        for (int i4 = 0; i4 < this.row - 1; i4++) {
            int i5 = 0;
            while (true) {
                int i6 = this.col;
                if (i5 < i6 - 1) {
                    int i7 = ((i6 - 1) * i4) + i5;
                    for (int i8 = 0; i8 < this.meshCount - 1; i8++) {
                        int i9 = 0;
                        while (true) {
                            int i10 = this.meshCount;
                            if (i9 < i10 - 1) {
                                int i11 = (i7 * i10 * i10) + (i8 * i10) + i9;
                                int[] iArr = this.index;
                                iArr[i3] = i11;
                                iArr[i3 + 1] = i11 + i10;
                                int i12 = i11 + 1;
                                iArr[i3 + 2] = i12;
                                iArr[i3 + 3] = i12;
                                int i13 = i3 + 5;
                                iArr[i3 + 4] = i11 + i10;
                                i3 += 6;
                                iArr[i13] = i11 + i10 + 1;
                                i9++;
                            }
                        }
                    }
                    i5++;
                }
            }
        }
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupAttributes() {
        int[] iArr = new int[1];
        GLES30.glGenBuffers(1, iArr, 0);
        this.mVBO = iArr[0];
        int[] iArr2 = new int[1];
        GLES30.glGenBuffers(1, iArr2, 0);
        this.mEBO = iArr2[0];
        int[] iArr3 = new int[1];
        GLES30.glGenVertexArrays(1, iArr3, 0);
        int i = iArr3[0];
        this.mVAO = i;
        GLES30.glBindVertexArray(i);
        GLES30.glBindBuffer(34962, this.mVBO);
        GLES30.glBindBuffer(34963, this.mEBO);
        int[] iArr4 = this.index;
        GLES30.glBufferData(34963, iArr4.length * 4, IntBuffer.wrap(iArr4), 35044);
        GLES30.glBindBuffer(34962, 0);
        GLES30.glBindBuffer(34963, 0);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uPatchX = this.mProgram.getUniformHandle("uPatchX");
        this.uPatchY = this.mProgram.getUniformHandle("uPatchY");
        this.uPatchR = this.mProgram.getUniformHandle("uPatchR");
        this.uPatchG = this.mProgram.getUniformHandle("uPatchG");
        this.uPatchB = this.mProgram.getUniformHandle("uPatchB");
    }
}

package effect_engine.utils;

import android.opengl.GLES30;

/* JADX INFO: loaded from: classes2.dex */
public class FBO {
    public boolean enableDepth;
    public int fboTexture;
    public int height;
    public int lastViewPortHeight;
    public int lastViewPortWidth;
    public int[] mFboId;
    public int width;

    public FBO(int i, int i2, int i3) {
        this.enableDepth = false;
        this.mFboId = new int[1];
        this.width = i;
        this.height = i2;
        init(false, false, i3);
    }

    public FBO(int i, int i2, boolean z) {
        this.enableDepth = false;
        this.mFboId = new int[1];
        this.width = i;
        this.height = i2;
        init(z, false, 6408);
    }

    public void bind() {
        bind(true);
    }

    public void bind(boolean z) {
        GLES30.glBindFramebuffer(36160, this.mFboId[0]);
        GLES30.glClear(16384);
        GLES30.glViewport(0, 0, this.width, this.height);
    }

    public int createRT(boolean z, int i, int i2, int i3) {
        int[] iArr = new int[1];
        GLES30.glGenTextures(1, iArr, 0);
        int i4 = iArr[0];
        if (i4 == 0) {
            return 0;
        }
        GLES30.glBindTexture(3553, i4);
        GLES30.glTexParameteri(3553, 10241, z ? 9987 : 9729);
        GLES30.glTexParameteri(3553, 10240, z ? 9987 : 9729);
        GLES30.glTexParameteri(3553, 10242, 33071);
        GLES30.glTexParameteri(3553, 10243, 33071);
        if (i3 == 33326) {
            GLES30.glTexImage2D(3553, 0, i3, i, i2, 0, 6403, 5126, null);
        } else {
            GLES30.glTexImage2D(3553, 0, i3, i, i2, 0, i3, 5121, null);
        }
        if (z) {
            GLES30.glGenerateMipmap(3553);
        }
        return iArr[0];
    }

    public void destroy() {
        GLES30.glDeleteFramebuffers(1, this.mFboId, 0);
        GLES30.glDeleteTextures(1, new int[]{this.fboTexture}, 0);
    }

    public int getFboTexture() {
        return this.fboTexture;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public final void init(boolean z, boolean z2, int i) {
        this.enableDepth = z2;
        this.fboTexture = createRT(false, this.width, this.height, i);
        GLES30.glGenFramebuffers(1, this.mFboId, 0);
        GLES30.glBindFramebuffer(36160, this.mFboId[0]);
        GLES30.glFramebufferTexture2D(36160, 36064, 3553, this.fboTexture, 0);
        GLES30.glBindFramebuffer(36160, 0);
    }

    public void recordLastViewPort() {
        int[] iArr = new int[4];
        GLES30.glGetIntegerv(2978, iArr, 0);
        this.lastViewPortWidth = iArr[2];
        this.lastViewPortHeight = iArr[3];
    }

    public void recoverLastViewPort() {
        GLES30.glViewport(0, 0, this.lastViewPortWidth, this.lastViewPortHeight);
    }

    public void unbind() {
        GLES30.glBindFramebuffer(36160, 0);
    }
}

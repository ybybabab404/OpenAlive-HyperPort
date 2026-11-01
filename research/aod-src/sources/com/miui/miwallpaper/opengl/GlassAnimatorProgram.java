package com.miui.miwallpaper.opengl;

import android.app.KeyguardManager;
import com.miui.maml.R;
import com.miui.miwallpaper.opengl.ordinary.AnimatorProgram;

/* JADX INFO: loaded from: classes2.dex */
public abstract class GlassAnimatorProgram extends AnimatorProgram {
    public final int HIGH_REFRESH_RATE;
    public volatile boolean isFolmeAnimIsRuning;
    public int mFrame;
    public final int mHashCode;
    public final int mInterval;
    public KeyguardManager mKeyguardManager;

    public GlassAnimatorProgram(GlassAnimGLProgram glassAnimGLProgram) {
        super(glassAnimGLProgram);
        this.mInterval = 2;
        this.HIGH_REFRESH_RATE = R.styleable.AppCompatTheme_windowFixedHeightMajor;
        this.mFrame = 2;
        this.isFolmeAnimIsRuning = false;
        this.mKeyguardManager = (KeyguardManager) glassAnimGLProgram.mContext.getSystemService("keyguard");
        this.mHashCode = hashCode();
    }

    public void resetFrameCount() {
        this.mFrame = 2;
    }
}

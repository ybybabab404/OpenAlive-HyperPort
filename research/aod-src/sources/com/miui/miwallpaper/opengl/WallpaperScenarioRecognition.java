package com.miui.miwallpaper.opengl;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes2.dex */
public class WallpaperScenarioRecognition {
    public static volatile WallpaperScenarioRecognition sInstance;
    public IScenarioRecognition iScenarioRecognition;

    public static WallpaperScenarioRecognition getInstance() {
        if (sInstance == null) {
            synchronized (WallpaperScenarioRecognition.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new WallpaperScenarioRecognition();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public void notifyScenarioChanged(int i) {
        if (this.iScenarioRecognition != null) {
            this.iScenarioRecognition.notifyScenarioChanged(i, SystemClock.elapsedRealtime());
        }
    }

    public void setScenarioState(long j, boolean z) {
        if (this.iScenarioRecognition != null) {
            this.iScenarioRecognition.setScenarioState(j, SystemClock.elapsedRealtime(), z);
        }
    }
}

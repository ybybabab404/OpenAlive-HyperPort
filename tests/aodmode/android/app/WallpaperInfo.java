package android.app;

import android.content.ComponentName;

public class WallpaperInfo {
    private final ComponentName component;

    public WallpaperInfo(ComponentName component) {
        this.component = component;
    }

    public ComponentName getComponent() { return component; }
}

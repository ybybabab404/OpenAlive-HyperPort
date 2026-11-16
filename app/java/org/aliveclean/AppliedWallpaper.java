package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.os.Build;

/**
 * 读锁屏槽上的壁纸组件（取不到时回落到系统槽），并判断它是否属于我们认识的那几个组件。
 * 两个槽「分别判断」由 {@code WallpaperPicker.applied} 负责，本类只提供读数与白名单。
 */
final class AppliedWallpaper {
    static WallpaperInfo lock(WallpaperManager manager){
        WallpaperInfo info=Build.VERSION.SDK_INT>=34
                ?manager.getWallpaperInfo(WallpaperManager.FLAG_LOCK):manager.getWallpaperInfo();
        if(info==null&&manager.getWallpaperId(WallpaperManager.FLAG_LOCK)<0)info=manager.getWallpaperInfo();
        return info;
    }

    static boolean supported(WallpaperInfo info){
        if(info==null)return false;
        ComponentName component=info.getComponent();
        if(!"org.aliveclean".equals(component.getPackageName()))return false;
        switch(component.getClassName()){
            case "org.aliveclean.CleanWallpaper":
            case "org.aliveclean.XiaomiWallpaper$Earth":
            case "org.aliveclean.XiaomiWallpaper$Moon":
            case "org.aliveclean.XiaomiWallpaper$Mars":
            case "org.aliveclean.XiaomiWallpaper$Saturn":
            case "org.aliveclean.XiaomiWallpaper$Snowmountain":
            case "org.aliveclean.XiaomiWallpaper$Geometry":return true;
            default:return false;
        }
    }
}

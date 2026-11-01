package org.aliveclean;

import android.content.Context;
import android.content.pm.PackageManager;

/** Which OEM personalization framework this process is talking to.
 *
 * OpenAlive grew up on ColorOS, where SystemUI owns the AOD surface and
 * com.oplus.wallpapers hosts the lock-screen clock editor. HyperOS ships a very
 * different stack: com.miui.miwallpaper renders its own AOD through the theme
 * framework, and the wallpaper picker lives in the live-picker / theme apps.
 * Everything platform specific is routed through here so a missing OEM package
 * degrades into "feature unavailable" instead of a hook that never fires.
 */
final class PlatformProfile {
    static final int UNKNOWN=0;
    static final int COLOROS=1;
    static final int HYPEROS=2;

    private static volatile int cached=-1;

    static int current(Context context){
        int value=cached;
        if(value<0){
            value=detect(context);
            cached=value;
        }
        return value;
    }

    static boolean isHyperOs(Context context){return current(context)==HYPEROS;}
    static boolean isColorOs(Context context){return current(context)==COLOROS;}

    static String name(Context context){
        switch(current(context)){
            case HYPEROS:return "HyperOS";
            case COLOROS:return "ColorOS";
            default:return "Android";
        }
    }

    private static int detect(Context context){
        if(installed(context,"com.miui.miwallpaper"))return HYPEROS;
        if(installed(context,"com.oplus.wallpapers"))return COLOROS;
        return UNKNOWN;
    }

    /** True when this process actually loaded the ColorOS personalization stack. */
    static boolean hasVendorClass(ClassLoader loader){
        try{
            return de.robv.android.xposed.XposedHelpers.findClassIfExists("com.oplus.systemui.keyguard.OplusKeyguardClock",loader)!=null
                || de.robv.android.xposed.XposedHelpers.findClassIfExists("com.oplus.wallpapers.WallpaperPickerActivity",loader)!=null;
        }catch(Throwable missing){
            return false;
        }
    }

    private static boolean installed(Context context,String pkg){
        try{
            context.getPackageManager().getPackageInfo(pkg,0);
            return true;
        }catch(PackageManager.NameNotFoundException absent){
            return false;
        }
    }

    private PlatformProfile(){}
}

package org.aliveclean;

import android.content.Context;
import android.content.pm.PackageManager;

/** Which OEM personalization framework this process is talking to.
 *
 * HyperOS renders AOD through com.miui.miwallpaper and keeps the wallpaper picker
 * in the live-picker / theme apps. Everything platform specific is routed through
 * here so a missing package degrades into "feature unavailable" instead of a hook
 * that never fires.
 */
final class PlatformProfile {
    static final int UNKNOWN=0;
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

    static String name(Context context){
        switch(current(context)){
            case HYPEROS:return "HyperOS";
            default:return "Android";
        }
    }

    private static int detect(Context context){
        if(installed(context,"com.miui.miwallpaper"))return HYPEROS;
        return UNKNOWN;
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

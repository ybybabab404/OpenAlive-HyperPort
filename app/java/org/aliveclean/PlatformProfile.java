package org.aliveclean;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 平台识别：只区分 HyperOS 与其它（没有 MIUI 分支），靠 {@code com.miui.miwallpaper} 是否存在判定，
 * 决定后续走系统接口还是 ColorOS 那套 root 桥。
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

package org.aliveclean;

import android.app.Activity;
import android.content.*;
import android.content.pm.*;
import android.widget.Toast;

/**
 * 主题商店跳转：{@code resolve} 依次探测两个 ColorOS 主题商店包，命中返回 Intent、都不在返回 null；
 * {@code open} 直接把 Intent 交给 Activity（异常与找不到目标都在内部 catch 并提示，调用方无需处理）。不区分 HyperOS 与其它平台。
 */
final class ThemeLinks {
    static Intent resolve(Context context,boolean font){
        String[] actions=font?new String[]{"com.oplus.themestore.action.SET_FONT_INDIVIDUATION","com.oplus.themestore.action.SET_FONT","com.nearme.themespace.SET_FONT"}
            :new String[]{"com.oplus.themestore.action.SET_THEME","com.nearme.themespace.SET_THEME","com.oplus.themestore.basic.action.SET_THEME"};
        for(String pkg:new String[]{"com.heytap.themestore","com.oplus.themestore"})for(String action:actions){
            Intent intent=new Intent(action).setPackage(pkg);
            ResolveInfo found=context.getPackageManager().resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY);
            if(found!=null&&found.activityInfo!=null&&found.activityInfo.exported&&found.activityInfo.enabled
                    &&(found.activityInfo.permission==null||context.checkSelfPermission(found.activityInfo.permission)==PackageManager.PERMISSION_GRANTED))return intent;
        }
        return null;
    }
    static void open(Activity activity,Intent intent){
        try{activity.startActivity(intent);}catch(ActivityNotFoundException|SecurityException error){Toast.makeText(activity,"系统页面暂时无法打开",Toast.LENGTH_SHORT).show();}
    }
}

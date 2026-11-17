package org.aliveclean;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.widget.Toast;
import java.util.HashSet;
import java.util.Set;

/**
 * 拉起系统壁纸选择器；跳不过去时只弹一次 Toast 让用户自己去列表里选（没有别的兜底），
 * 事后由 {@link #applied} 看当前槽上的组件是不是我们，来判定到底有没有换成。
 */
final class WallpaperPicker {
    private static final String[] PACKAGES={
        "com.oplus.wallpapers","com.android.wallpaper.livepicker",
        "com.google.android.apps.wallpaper","com.android.wallpaper",
        "com.heytap.themestore","com.oplus.themestore","com.android.thememanager"
    };

    private static final String[] HYPEROS_PACKAGES={
        "com.android.wallpaper.livepicker","com.android.thememanager",
        "com.miui.miwallpaper","com.android.wallpaper","com.google.android.apps.wallpaper"
    };

    static boolean open(Activity activity,ComponentName wallpaper,int request){
        if(start(activity,new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
                .putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,wallpaper),request))return true;
        Intent change=target(new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER),wallpaper);

        Set<ComponentName> tried=new HashSet<>();
        for(ResolveInfo result:activity.getPackageManager().queryIntentActivities(change,PackageManager.MATCH_ALL)){
            ActivityInfo info=result.activityInfo;
            if(info==null||!info.exported||!info.enabled)continue;
            ComponentName entry=new ComponentName(info.packageName,info.name);
            if(tried.add(entry)&&start(activity,new Intent(change).setComponent(entry),request))return true;
        }

        ComponentName legacy=new ComponentName("com.android.wallpaper.livepicker",
                "com.android.wallpaper.livepicker.LiveWallpaperChange");
        if(tried.add(legacy)&&start(activity,new Intent(change).setComponent(legacy),request))return true;
        if(start(activity,target(new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER),wallpaper),request)){
            manualSelection(activity);return false;
        }
        for(String name:PlatformProfile.isHyperOs(activity)?HYPEROS_PACKAGES:PACKAGES){
            Intent launch=activity.getPackageManager().getLaunchIntentForPackage(name);
            if(launch!=null){

                launch.setFlags(0);
                if(start(activity,target(launch,wallpaper),request)){manualSelection(activity);return false;}
            }
        }

        if(start(activity,target(new Intent(Intent.ACTION_SET_WALLPAPER),wallpaper),request)){
            manualSelection(activity);return false;
        }
        throw new ActivityNotFoundException("未找到可用的系统壁纸应用，请启用系统壁纸或主题应用后重试");
    }

    private static Intent target(Intent intent,ComponentName wallpaper){
        return intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,wallpaper);
    }

    private static void manualSelection(Activity activity){

        Toast.makeText(activity,"系统未提供直接预览入口，请在动态壁纸中选择 OpenAlive 对应壁纸；若列表没有它，请启用系统动态壁纸选择器",Toast.LENGTH_LONG).show();
    }

    private static boolean start(Activity activity,Intent intent,int request){
        try{activity.startActivityForResult(intent,request);return true;}
        catch(ActivityNotFoundException|SecurityException e){
            AliveLog.w("AliveClean","Wallpaper entry unavailable: "+intent,e);return false;
        }
    }

    /**
     * 判定「换成我们的壁纸了吗」：只看当前系统槽（SDK 34+ 再看锁屏槽）上的组件是不是 {@code expected}。
     * 选择器返回什么、槽位 id 有没有变，都不参与判定——那两项只用来写 {@link #logPicker} 的诊断日志。
     */
    static boolean applied(WallpaperManager manager,ComponentName expected){
        WallpaperInfo home=manager.getWallpaperInfo();
        String homeName=home==null?null:home.getComponent().flattenToShortString();
        int homeId=manager.getWallpaperId(WallpaperManager.FLAG_SYSTEM);
        AliveLog.i("AliveClean","wallpaper check: home="+homeName+" homeId="+homeId+" expected="+expected.flattenToShortString());

        if(home!=null&&expected.equals(home.getComponent()))return true;
        if(Build.VERSION.SDK_INT>=34){
            WallpaperInfo lock=manager.getWallpaperInfo(WallpaperManager.FLAG_LOCK);
            int lockId=manager.getWallpaperId(WallpaperManager.FLAG_LOCK);
            String lockName=lock==null?null:lock.getComponent().flattenToShortString();
            AliveLog.i("AliveClean","wallpaper check lock: "+lockName+" lockId="+lockId);
            if(lock!=null&&expected.equals(lock.getComponent()))return true;
        }
        return false;
    }

    /** 记一行「选择器返回了什么」，排查「看着成功其实没换成」的现场用；不参与判定。 */
    static void logPicker(int result,boolean reliableResult,int previousHomeId,int previousLockId){
        AliveLog.i("AliveClean","wallpaper picker: result="+result+" reliable="+reliableResult
                +" prevHome="+previousHomeId+" prevLock="+previousLockId);
    }

    static void appliedChecked(WallpaperManager manager,ComponentName expected,Runnable onApplied,Runnable onFailed){
        if(applied(manager,expected)){onApplied.run();return;}
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(()->{
            if(applied(manager,expected))onApplied.run();
            else onFailed.run();
        },900);
    }
}

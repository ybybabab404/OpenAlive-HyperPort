package org.aliveclean;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.*;

/**
 * 场景信号的 ContentProvider 通道（authority {@code org.aliveclean.scene}），
 * 只实现 {@code call}（{@code connect} 换通道 binder、{@code configuration} 读当前配置）；
 * query/insert/delete/update 一律抛 UnsupportedOperationException。调用方需持 STATUS_BAR 权限。
 */
public final class SceneProvider extends ContentProvider {
    static final Uri CONFIGURATION=Uri.parse("content://org.aliveclean.scene/configuration");
    static void changed(Context context){context.getContentResolver().notifyChange(CONFIGURATION,null);}
    @Override public boolean onCreate(){return true;}
    @Override public Bundle call(String method,String argument,Bundle extras){
        Context c=getContext();
        if(c.checkCallingOrSelfPermission("android.permission.STATUS_BAR")!=android.content.pm.PackageManager.PERMISSION_GRANTED)throw new SecurityException("System UI permission required");
        // 编辑页（息屏与锁屏编辑）点「应用」应用的是系统壁纸，MIUI 只写自己的模板数据，
        // 不会清掉我们的动态壁纸组件；不在这里交还，用户会看到"应用了系统壁纸却还是项目壁纸"。
        if("releaseWallpaper".equals(method)){
            // 交还壁纸组件要 SET_WALLPAPER_COMPONENT，应用进程没有这个权限，
            // 交给 system_server 那边的接收者去做（见 AodWallpaperModeHook）。
            android.content.Intent intent=new android.content.Intent(AodWallpaperMode.ACTION_RELEASE_WALLPAPER);
            intent.setPackage("android");
            intent.putExtra("source",argument==null?"edit_apply":argument);
            c.sendBroadcast(intent,AodWallpaperMode.PERMISSION_RELEASE_WALLPAPER);
            return new Bundle();
        }
        if(!"connect".equals(method)&&!"configuration".equals(method))throw new IllegalArgumentException("Unknown operation");
        try{
            Bundle result=new Bundle();
            if("connect".equals(method))result.putBinder("channel",SceneChannel.connect(c,extras==null?null:extras.getBinder("owner"),extras==null?0:extras.getInt("clock_api",0),extras==null?null:extras.getBinder("clock_feedback")));
            // provider 与壁纸服务同在 :wallpaper 进程，但配置由主进程写：跨进程读偏好表会拿到旧值，
        // 所以这里和壁纸渲染一样走磁盘快照。
        SceneOptions options=SceneOptions.load(c,SceneOptions.APPLIED);
            result.putInt("aod",options.aod);
            result.putBoolean("vivo_wallpaper",options.vivo!=null);

            result.putBoolean("wallpaper_running",CleanWallpaper.RUNNING);
            result.putBoolean("vivo_liquid",options.vivo!=null&&options.vivo.family==VivoOptions.LIQUID);
            result.putBoolean("continuous_aod",options.vivo!=null?options.vivo.animateInAod():options.cosmic!=0?options.cosmicContinuousAod:options.aod==0&&options.sailContinuousAod);
            Bundle xiaomiClock=XiaomiState.lockClock(c);
            if(xiaomiClock!=null){
                result.putInt("aod",101);result.putBoolean("vivo_wallpaper",false);
                result.putBoolean("continuous_aod",false);
                result.putBundle("xiaomi_clock",xiaomiClock);
            }
            return result;
        }catch(Exception error){throw new IllegalStateException("Scene channel unavailable",error);}
    }

    @Override public Cursor query(Uri uri,String[] projection,String selection,String[] args,String order){throw new UnsupportedOperationException();}
    @Override public String getType(Uri uri){return null;}
    @Override public Uri insert(Uri uri,ContentValues values){throw new UnsupportedOperationException();}
    @Override public int delete(Uri uri,String selection,String[] args){throw new UnsupportedOperationException();}
    @Override public int update(Uri uri,ContentValues values,String selection,String[] args){throw new UnsupportedOperationException();}
}

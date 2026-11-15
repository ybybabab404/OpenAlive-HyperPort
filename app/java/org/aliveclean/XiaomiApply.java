package org.aliveclean;

import android.app.*;
import android.content.*;
import android.os.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class XiaomiApply {
    static void apply(Context c,XiaomiPacks.Pack p)throws Exception {
        if(android.os.Process.myUid()/100000!=0)throw new IOException("目前仅支持主用户");
        AliveLog.i(AliveLog.APPLY,"xiaomi apply start "+p.id);
        if (aodSwitchFromShell()) AodWallpaperMode.enter(c); else AliveLog.i(AliveLog.APPLY, "aod mode enter skipped: AOD is disabled");
        String path=c.getApplicationInfo().sourceDir;
        String command="CLASSPATH='"+path.replace("'","'\"'\"'")+"' /system/bin/app_process /system/bin org.aliveclean.XiaomiApply "+p.id;
        java.lang.Process process=new ProcessBuilder("su","1000","-c",command).redirectErrorStream(true).start();StringBuilder result=new StringBuilder();
        Thread reader=new Thread(()->{try(BufferedReader in=new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"))){for(String s;(s=in.readLine())!=null;)synchronized(result){if(result.length()<8192)result.append(s).append('\n');}}catch(IOException ignored){}},"XiaomiApplyOutput");reader.start();
        if(!process.waitFor(25,TimeUnit.SECONDS)){process.destroyForcibly();AodWallpaperMode.restore(c);throw new IOException("壁纸应用超时，请检查 Root 授权");}reader.join(1000);
        String output;synchronized(result){output=result.toString();}
        if(process.exitValue()!=0||!output.contains("XIAOMI_APPLIED")){AliveLog.e(AliveLog.APPLY,"xiaomi apply rejected exit="+process.exitValue()+" output="+output.trim());AodWallpaperMode.restore(c);throw new IOException("系统未完成壁纸应用："+output.trim());}
        if(!HyperOsAod.aodEnabled(c)){AodWallpaperMode.restore(c);AliveLog.i(AliveLog.APPLY,"xiaomi applied with AOD off; full_screen_aod_on restored");}
    }
    private static boolean aodSwitchFromShell() {
        try {
            java.lang.Process process = new ProcessBuilder("/system/bin/settings", "get", "secure", "doze_always_on")
                    .redirectErrorStream(true).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = reader.readLine();
            process.waitFor(2, TimeUnit.SECONDS);
            return line != null && "1".equals(line.trim());
        } catch (Throwable error) {
            AliveLog.w(AliveLog.APPLY, "cannot read doze_always_on from shell", error);
            return true;
        }
    }

    public static void main(String[] args){try{
        if(android.os.Process.myUid()!=1000)throw new SecurityException("System UID required");
        AliveLog.i(AliveLog.APPLY,"xiaomi apply subprocess start "+(args.length==1?args[0]:"?"));
        if(args.length!=1||!Arrays.asList("moon","earth","mars","snowmountain","geometry","saturn").contains(args[0]))throw new IllegalArgumentException("Unsupported component");
        Looper.prepareMainLooper();Class<?> at=Class.forName("android.app.ActivityThread");Object thread=at.getMethod("systemMain").invoke(null);Context c=(Context)at.getMethod("getSystemContext").invoke(thread);
        WallpaperManager wm=WallpaperManager.getInstance(c);String family=args[0];ComponentName component=new ComponentName("org.aliveclean","org.aliveclean.XiaomiWallpaper$"+Character.toUpperCase(family.charAt(0))+family.substring(1));
        if (aodSwitchFromShell()) AodWallpaperMode.enter(c); else AliveLog.i(AliveLog.APPLY, "aod mode enter skipped: AOD is disabled");
        try{
            try{WallpaperManager.class.getMethod("setWallpaperComponentWithFlags",ComponentName.class,int.class).invoke(wm,component,WallpaperManager.FLAG_SYSTEM|WallpaperManager.FLAG_LOCK);}
            catch(InvocationTargetException e){
                WallpaperInfo actual=wm.getWallpaperInfo();if(!(e.getCause() instanceof SecurityException)||actual==null||!component.equals(actual.getComponent()))throw e;
            }
            WallpaperInfo actual=wm.getWallpaperInfo();if(actual==null||!component.equals(actual.getComponent()))throw new IllegalStateException("Wallpaper verification failed");
            if(Build.VERSION.SDK_INT>=34){WallpaperInfo lock=wm.getWallpaperInfo(WallpaperManager.FLAG_LOCK);if(lock==null&&wm.getWallpaperId(WallpaperManager.FLAG_LOCK)<0)lock=actual;if(lock==null||!component.equals(lock.getComponent()))throw new IllegalStateException("Lock wallpaper verification failed");}
        }catch(Throwable applyError){AodWallpaperMode.restore(c);throw applyError;}
        try{ModuleActivation.request(c);}catch(Throwable ignored){}
        AliveLog.i(AliveLog.APPLY,"xiaomi applied "+component.flattenToShortString());System.out.println("XIAOMI_APPLIED");System.exit(0);
    }catch(Throwable e){AliveLog.e(AliveLog.APPLY,"xiaomi apply failed",e);e.printStackTrace(System.out);System.exit(1);}}
}

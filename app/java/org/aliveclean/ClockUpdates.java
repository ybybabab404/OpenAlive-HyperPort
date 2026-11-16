package org.aliveclean;

import android.content.*;
import android.database.ContentObserver;
import android.os.*;
import android.provider.Settings;

/**
 * 时间变化的统一来源：监听系统的分钟/时区/日期变化并回调。
 *
 * <p>预览引擎没有系统广播可用，所以 {@code preview} 模式改成自己每分钟触发一次。</p>
 */
final class ClockUpdates {
    interface Listener {void refresh(long time);}
    private final Context context;
    private final Listener listener;
    private final Handler main=new Handler(Looper.getMainLooper());
    private final boolean preview;
    private boolean active;
    private final Runnable minute=()->refresh(System.currentTimeMillis());
    private final BroadcastReceiver receiver=new BroadcastReceiver(){@Override public void onReceive(Context c,Intent i){refresh(System.currentTimeMillis());}};
    private final ContentObserver format=new ContentObserver(main){@Override public void onChange(boolean self){refresh(System.currentTimeMillis());}};
    ClockUpdates(Context context,boolean preview,Listener listener){this.context=context;this.preview=preview;this.listener=listener;}
    void start(){
        if(active)return;
        IntentFilter filter=new IntentFilter();
        for(String action:new String[]{Intent.ACTION_TIME_TICK,Intent.ACTION_TIME_CHANGED,Intent.ACTION_TIMEZONE_CHANGED,Intent.ACTION_LOCALE_CHANGED,Intent.ACTION_CONFIGURATION_CHANGED})filter.addAction(action);
        if(Build.VERSION.SDK_INT>=33)context.registerReceiver(receiver,filter,null,main,Context.RECEIVER_NOT_EXPORTED);
        else context.registerReceiver(receiver,filter,null,main);
        try{context.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.TIME_12_24),false,format);}
        catch(RuntimeException error){context.unregisterReceiver(receiver);throw error;}
        active=true;refresh(System.currentTimeMillis());
    }
    void refresh(long time){
        if(!active)return;
        listener.refresh(time);main.removeCallbacks(minute);

        if(preview)main.postDelayed(minute,60000-Math.floorMod(System.currentTimeMillis(),60000L));
    }
    void stop(){if(!active)return;active=false;main.removeCallbacks(minute);context.unregisterReceiver(receiver);context.getContentResolver().unregisterContentObserver(format);}
}

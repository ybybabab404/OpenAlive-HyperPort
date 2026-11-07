package org.aliveclean;

import android.app.Application;
import android.content.*;
import android.os.Build;

final class ModuleActivationHook {
    private static boolean registered;
    static void install(){
        Xp.findAndHookMethod(Application.class,"attach",Context.class,new Xp.MethodHook(){
            @Override protected void afterHookedMethod(Xp.MethodHookParam call){
                Context context=(Context)call.thisObject;
                if(!"com.android.systemui".equals(context.getPackageName())||registered)return;
                registered=true;
                BroadcastReceiver receiver=new BroadcastReceiver(){
                    @Override public void onReceive(Context c,Intent intent){if(ModuleActivation.QUERY.equals(intent.getAction()))ModuleActivation.announce(context);KeyguardDepthHider.onActivation(context);}
                };
                IntentFilter filter=new IntentFilter(ModuleActivation.QUERY);filter.addAction(Intent.ACTION_WALLPAPER_CHANGED);
                if(Build.VERSION.SDK_INT>=33)context.registerReceiver(receiver,filter,Context.RECEIVER_EXPORTED);
                else context.registerReceiver(receiver,filter);
                ModuleActivation.announce(context);
            }
        });
    }
    private ModuleActivationHook(){}
}

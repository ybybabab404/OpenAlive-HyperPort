package org.aliveclean;

import android.app.WallpaperInfo;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/** Package-scoped wallpaper capability bridge. No checks run inside the render loop. */
public final class CleanHooks implements IXposedHookLoadPackage {
    private static boolean ambientHooked;
    @Override public void handleLoadPackage(XC_LoadPackage.LoadPackageParam p){
        boolean colorOsEditor="com.oplus.wallpapers".equals(p.packageName);
        boolean systemUi="com.android.systemui".equals(p.packageName);
        if(systemUi)ModuleActivationHook.install();
        if("com.miui.aod".equals(p.packageName))HyperOsAod.install(p.classLoader);
        if(systemUi)HyperOsAod.install(p.classLoader);
        if(colorOsEditor){
            NativeClockApplyPolicy.install(p.classLoader);
            ColorOsNativeClockFonts.install(p.classLoader);
        }
        // The vendor clock hooks only exist on ColorOS; on HyperOS or stock
        // Android the class lookups below simply find nothing and the module
        // keeps working as a plain live-wallpaper provider.
        if(systemUi&&PlatformProfile.hasVendorClass(p.classLoader))ColorOsNativeClockFonts.install(p.classLoader);
        if(!"android".equals(p.packageName)&&!systemUi&&!"com.miui.aod".equals(p.packageName))return;
        if(!ambientHooked){
            XposedHelpers.findAndHookMethod(WallpaperInfo.class,"supportsAmbientMode",new XC_MethodHook(){@Override protected void afterHookedMethod(MethodHookParam call){WallpaperInfo info=(WallpaperInfo)call.thisObject;if("org.aliveclean".equals(info.getPackageName()))call.setResult(true);}});
            ambientHooked=true;
        }
        if("android".equals(p.packageName)){
            // Verified against the wallpaper service and IPackageManagerBase Smali.
            // Binding checks this exact permission independently of supportsAmbientMode().
            Class<?> base=XposedHelpers.findClassIfExists("com.android.server.pm.IPackageManagerBase",p.classLoader);
            if(base!=null)XposedHelpers.findAndHookMethod(base,"checkPermission",String.class,String.class,int.class,new XC_MethodHook(){
                @Override protected void beforeHookedMethod(MethodHookParam call){
                    if("android.permission.AMBIENT_WALLPAPER".equals(call.args[0])&&"org.aliveclean".equals(call.args[1]))call.setResult(android.content.pm.PackageManager.PERMISSION_GRANTED);
                }
            });
            else XposedBridge.log("HyperAlive: ambient permission adapter unavailable on this framework");
        }else if(PlatformProfile.hasVendorClass(p.classLoader)) ColorOsBridge.install(p.classLoader);
        {if(Diagnostics.TRACE)XposedBridge.log("HyperAlive: platform hooks v27 loaded in "+p.packageName);}
    }
}

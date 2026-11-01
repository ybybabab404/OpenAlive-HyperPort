package com.miui.aod.superwallpaper;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ServiceInfo;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.service.wallpaper.IWallpaperConnection;
import android.service.wallpaper.IWallpaperEngine;
import android.service.wallpaper.IWallpaperService;
import android.util.Log;
import android.view.View;
import android.view.Window;
import androidx.core.os.BundleKt;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.util.ReflectUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: WallpaperConnectionUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class WallpaperConnectionUtils {
    public WallpaperConnection mWallpaperConnection;
    public String mWallpaperId;
    public WallpaperInfo mWallpaperInfo;
    public final Intent mWallpaperIntent;
    public String mWallpaperPackageName;
    public String mWallpaperTitle;
    public final String tag;

    /* JADX INFO: compiled from: WallpaperConnectionUtils.kt */
    @Metadata
    public final class WallpaperConnection extends MiWallpaperConnection implements ServiceConnection {
        public final WeakReference activity;
        public boolean mConnected;
        public IWallpaperEngine mEngine;
        public final Intent mIntent;
        public IWallpaperService mService;
        public final /* synthetic */ WallpaperConnectionUtils this$0;

        public WallpaperConnection(WallpaperConnectionUtils wallpaperConnectionUtils, Activity mActivity, Intent mIntent) {
            Intrinsics.checkNotNullParameter(mActivity, "mActivity");
            Intrinsics.checkNotNullParameter(mIntent, "mIntent");
            this.this$0 = wallpaperConnectionUtils;
            this.mIntent = mIntent;
            this.activity = new WeakReference(mActivity);
        }

        @Override // com.miui.aod.superwallpaper.MiWallpaperConnection, android.service.wallpaper.IWallpaperConnection
        public synchronized void attachEngine(IWallpaperEngine engine, int i) {
            try {
                Intrinsics.checkNotNullParameter(engine, "engine");
                try {
                    if (this.mConnected) {
                        this.mEngine = engine;
                        engine.setVisibility(true);
                        engine.dispatchWallpaperCommand("action_aod", 0, 0, 0, BundleKt.bundleOf(TuplesKt.to("package", "com.miui.aod")));
                    } else {
                        engine.destroy();
                    }
                } catch (Exception e) {
                    Log.wtf(this.this$0.tag, "error attach engine", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized boolean connect() {
            try {
                Activity activity = (Activity) this.activity.get();
                if (activity != null) {
                    WallpaperConnectionUtils wallpaperConnectionUtils = this.this$0;
                    if (!activity.bindService(this.mIntent, this, 1)) {
                        return false;
                    }
                    wallpaperConnectionUtils.sendBindBroadcast(activity);
                }
                this.mConnected = true;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized void disconnect() {
            this.mConnected = false;
            try {
                IWallpaperEngine iWallpaperEngine = this.mEngine;
                if (iWallpaperEngine != null) {
                    iWallpaperEngine.destroy();
                }
            } catch (Exception e) {
                Log.wtf(this.this$0.tag, "error destroy engine", e);
            }
            this.mEngine = null;
            Activity activity = (Activity) this.activity.get();
            if (activity != null) {
                activity.unbindService(this);
            }
            this.mService = null;
        }

        @Override // com.miui.aod.superwallpaper.MiWallpaperConnection, android.service.wallpaper.IWallpaperConnection
        public void engineShown(IWallpaperEngine engine) {
            Intrinsics.checkNotNullParameter(engine, "engine");
            try {
                engine.dispatchWallpaperCommand("action_aod", 0, 0, 0, BundleKt.bundleOf(TuplesKt.to("package", "com.miui.aod")));
            } catch (Exception e) {
                Log.wtf(this.this$0.tag, "error engine shown", e);
            }
        }

        public final IWallpaperEngine getMEngine() {
            return this.mEngine;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            Window window;
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(service, "service");
            if (this.this$0.mWallpaperConnection == this) {
                this.mService = IWallpaperService.Stub.asInterface(service);
                Activity activity = (Activity) this.activity.get();
                View decorView = (activity == null || (window = activity.getWindow()) == null) ? null : window.getDecorView();
                IWallpaperService iWallpaperService = this.mService;
                if (iWallpaperService != null) {
                    WallpaperConnectionUtils wallpaperConnectionUtils = this.this$0;
                    if (decorView != null) {
                        try {
                            if (!DeviceUtil.INSTANCE.isGteAndroidW()) {
                                View view = decorView;
                                if (Build.VERSION.SDK_INT >= 35) {
                                    Class cls = Integer.TYPE;
                                    ReflectUtil.callObjectMethod(iWallpaperService, "attach", new Class[]{IWallpaperConnection.class, IBinder.class, cls, Boolean.TYPE, cls, cls, Rect.class, cls, cls, WallpaperInfo.class}, this, view.getWindowToken(), 1001, Boolean.TRUE, Integer.valueOf(view.getWidth()), Integer.valueOf(view.getHeight()), new Rect(), 0, 1, wallpaperConnectionUtils.mWallpaperInfo);
                                    return;
                                } else {
                                    iWallpaperService.attach(this, view.getWindowToken(), 1001, true, view.getWidth(), view.getHeight(), new Rect(), 0, 1);
                                    Unit unit = Unit.INSTANCE;
                                    return;
                                }
                            }
                            Class<?> cls2 = Class.forName("android.app.wallpaper.WallpaperDescription");
                            Class<?> cls3 = Class.forName("android.app.wallpaper.WallpaperDescription$Builder");
                            Object objNewInstance = cls3.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                            Method method = cls3.getMethod("setComponent", ComponentName.class);
                            Intrinsics.checkNotNullExpressionValue(method, "getMethod(...)");
                            WallpaperInfo wallpaperInfo = wallpaperConnectionUtils.mWallpaperInfo;
                            method.invoke(objNewInstance, wallpaperInfo != null ? wallpaperInfo.getComponent() : null);
                            Method method2 = cls3.getMethod("build", new Class[0]);
                            Intrinsics.checkNotNullExpressionValue(method2, "getMethod(...)");
                            Object objInvoke = method2.invoke(objNewInstance, new Object[0]);
                            Class cls4 = Integer.TYPE;
                            View view2 = decorView;
                            ReflectUtil.callObjectMethod(iWallpaperService, "attach", new Class[]{IWallpaperConnection.class, IBinder.class, cls4, Boolean.TYPE, cls4, cls4, Rect.class, cls4, cls4, WallpaperInfo.class, cls2}, this, decorView.getWindowToken(), 1001, Boolean.TRUE, Integer.valueOf(view2.getWidth()), Integer.valueOf(view2.getHeight()), new Rect(), 0, 1, wallpaperConnectionUtils.mWallpaperInfo, objInvoke);
                        } catch (Exception e) {
                            Log.wtf(wallpaperConnectionUtils.tag, "error attach service", e);
                        }
                    }
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            Window window;
            Intrinsics.checkNotNullParameter(name, "name");
            try {
                if (Build.VERSION.SDK_INT >= 34) {
                    Activity activity = (Activity) this.activity.get();
                    View decorView = (activity == null || (window = activity.getWindow()) == null) ? null : window.getDecorView();
                    IWallpaperService iWallpaperService = this.mService;
                    if (iWallpaperService != null) {
                        iWallpaperService.detach(decorView != null ? decorView.getWindowToken() : null);
                    }
                } else {
                    ReflectUtil.callObjectMethod(this.mService, "detach", null, new Object[0]);
                }
            } catch (Exception e) {
                Log.wtf(this.this$0.tag, "service detach failed", e);
            }
            this.mService = null;
            this.mEngine = null;
            if (this.this$0.mWallpaperConnection == this) {
                Log.w(this.this$0.tag, "Wallpaper service gone: " + name);
            }
        }

        @Override // com.miui.aod.superwallpaper.MiWallpaperConnection, android.service.wallpaper.IWallpaperConnection
        public ParcelFileDescriptor setWallpaper(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            return null;
        }
    }

    public WallpaperConnectionUtils(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.tag = "WallpaperConnectionUtils";
        initSuperWallpaperNeedInfo();
        Intent intent = new Intent("android.service.wallpaper.WallpaperService");
        this.mWallpaperIntent = intent;
        String str = this.mWallpaperPackageName;
        if (str == null || this.mWallpaperId == null) {
            Log.wtf("WallpaperConnectionUtils", "warming for mWallpaperPackageName = " + (str == null) + ", mWallpaperId = " + (this.mWallpaperId == null), new Throwable());
            return;
        }
        Intrinsics.checkNotNull(str);
        intent.setClassName(str, this.mWallpaperPackageName + ".superwallpaper." + this.mWallpaperId + "PreviewSuperWallpaper");
        this.mWallpaperConnection = new WallpaperConnection(this, activity, intent);
    }

    public final String getWallpaperTitle() {
        return this.mWallpaperTitle;
    }

    public final void initSuperWallpaperNeedInfo() {
        WallpaperInfo wallpaperInfo;
        Bundle bundle;
        WallpaperManager wallpaperManager = (WallpaperManager) AODApplication.sInstance.getSystemService("wallpaper");
        if (wallpaperManager == null || (wallpaperInfo = wallpaperManager.getWallpaperInfo()) == null) {
            return;
        }
        this.mWallpaperInfo = wallpaperInfo;
        this.mWallpaperPackageName = wallpaperInfo.getPackageName();
        ServiceInfo serviceInfo = wallpaperInfo.getServiceInfo();
        if (serviceInfo == null || (bundle = serviceInfo.metaData) == null) {
            return;
        }
        String string = bundle.getString("id", "");
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        if (string.length() > 0) {
            char upperCase = Character.toUpperCase(string.charAt(0));
            String strSubstring = string.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            string = upperCase + strSubstring;
        }
        this.mWallpaperId = string;
        String string2 = bundle.getString("title", AODApplication.sInstance.getString(R.string.aod_style));
        this.mWallpaperTitle = string2;
        Log.i(this.tag, "packageName: " + this.mWallpaperPackageName + " wallpaperId: " + this.mWallpaperId + " wallpaperTitle:" + string2);
    }

    public final void sendBindBroadcast(Context context) {
        Intent intent = new Intent("com.miui.miwallpaper.action_load_preview");
        intent.putExtra("service_components", this.mWallpaperIntent.getComponent());
        context.sendBroadcast(intent);
    }

    public final void setEngineVisibility(boolean z) {
        IWallpaperEngine mEngine;
        try {
            WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
            if (wallpaperConnection == null || (mEngine = wallpaperConnection.getMEngine()) == null) {
                return;
            }
            mEngine.setVisibility(z);
        } catch (Exception e) {
            Log.wtf(this.tag, "error set engine visibility", e);
        }
    }

    public final void wallpaperConnect() {
        WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
        if (wallpaperConnection == null || wallpaperConnection.connect()) {
            return;
        }
        this.mWallpaperConnection = null;
    }

    public final void wallpaperDisconnect() {
        WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
        if (wallpaperConnection != null) {
            wallpaperConnection.disconnect();
        }
        this.mWallpaperConnection = null;
    }
}

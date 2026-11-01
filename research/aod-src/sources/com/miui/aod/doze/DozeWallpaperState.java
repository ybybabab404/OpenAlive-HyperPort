package com.miui.aod.doze;

import android.app.IWallpaperManager;
import android.content.Context;
import android.os.ServiceManager;
import android.util.Log;
import com.miui.aod.widget.AODSettings;

/* JADX INFO: loaded from: classes.dex */
public class DozeWallpaperState implements DozeMachine.Part {
    public boolean mIsAmbientMode;
    public final boolean mSupportAodWallpaper;
    public IWallpaperManager mWallpaperManagerService;

    /* JADX INFO: renamed from: com.miui.aod.doze.DozeWallpaperState$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public DozeWallpaperState(Context context) {
        this.mSupportAodWallpaper = AODSettings.isSupportAodWallpaper(context);
        try {
            this.mWallpaperManagerService = IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
        } catch (Exception e) {
            Log.e("DozeWallpaperState", "error...", e);
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        boolean z;
        switch (AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        boolean z2 = this.mSupportAodWallpaper;
        if (z != this.mIsAmbientMode) {
            this.mIsAmbientMode = z;
            if (this.mWallpaperManagerService != null) {
                long j = z2 ? 500L : 0L;
                try {
                    Log.i("DozeWallpaperState", "AOD wallpaper state changed to: " + this.mIsAmbientMode + ", animationDuration: " + j);
                    this.mWallpaperManagerService.setInAmbientMode(this.mIsAmbientMode, j);
                } catch (Exception e) {
                    Log.w("DozeWallpaperState", "Cannot notify state to WallpaperManagerService: " + this.mIsAmbientMode, e);
                }
            }
        }
    }
}

package com.miui.aod.doze;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import androidx.collection.ArrayMap;
import com.android.systemui.plugins.DozeServicePlugin;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.category.FlipLinkageClockCategoryInfoKt;
import com.miui.aod.db.NotificationProvider;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.maml.elements.AdvancedSlider;
import com.xiaomi.onetrack.api.a;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAodService implements DozeMachine.Part, MiuiSystemUiCallback {
    public final Context mContext;
    public final DozeHost mHost;
    public final DozeServicePlugin.RequestDoze mRequestDoze;
    public final DozeMachine.Service service;
    public final DozeHost.Callback mHostCallback = new DozeHost.Callback() { // from class: com.miui.aod.doze.MiuiAodService.2
        @Override // com.miui.aod.DozeHost.Callback
        public void onDozingRequested(boolean z) {
            MiuiAodService.this.mRequestDoze.onDozingRequested(z);
        }
    };
    public final String boolean_key = "flag";

    /* JADX INFO: renamed from: com.miui.aod.doze.MiuiAodService$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public MiuiAodService(Context context, DozeMachine.Service service) {
        this.mContext = context;
        this.service = service;
        this.mHost = service.getHost();
        this.mRequestDoze = service.getRequestDoze();
    }

    public void applyLinkageState(Bundle bundle) {
        this.mHost.updateLinkagePosition(bundle);
        this.mHost.applyLinkageState();
    }

    public void dispatchKeyEvent(KeyEvent keyEvent) {
        Log.i("MiuiAodService", "dispatchKeyEvent: " + keyEvent.getKeyCode());
        if (keyEvent.getKeyCode() != 354) {
            return;
        }
        if (keyEvent.getAction() == 0 && !Utils.aodGone(this.mContext) && Utils.isShowTemporary(this.mContext)) {
            this.mHost.requestDrawWakelock(Utils.getTemporaryStyleDuration(), "AODView#dispatchKeyEvent");
            if (this.mHost.getAODView() != null) {
                this.mHost.getAODView().setNotificationAnimFirstShow(true);
            }
            this.mHost.fireAodState(true, "reason_keycode_goto");
        }
        this.mHost.notifyKeycodeGoto();
    }

    public void fireAodState(boolean z) {
        Log.i("MiuiAodService", "fireAodState: " + z);
        this.mHost.fireAodState(z, "reason_fod");
    }

    public void fireFingerprintPressed(boolean z, boolean z2) {
        Log.i("MiuiAodService", "fireFingerprintPressed: " + z + ", turnScreenOn: " + z2);
        this.mHost.fireFingerprintPressed(z, z2);
    }

    public Map getAodViewTopAndBottom() {
        return this.mHost.getAodViewTopAndBottom();
    }

    public void onGxzwIconChanged(boolean z) {
        Log.i("MiuiAodService", "onGxzwIconChanged: " + z);
        this.mHost.onGxzwIconChanged(z);
    }

    public void onKeyguardTransparent() {
        Log.i("MiuiAodService", "onKeyguardTransparent: ");
        this.mHost.onKeyguardTransparent();
    }

    public void onScreenTurnOnDelayed(boolean z) {
        Log.i("MiuiAodService", "onScreenTurnOnDelayed: " + z);
        if (!z) {
            this.mHost.screenTurnOn();
        }
        this.mHost.onScreenTurnOnDelayed(z);
    }

    public void onSimPinSecureChanged(boolean z) {
        this.mHost.setSimPinSecure(z);
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public Map onSystemUIAction(int i, Bundle bundle) {
        Map map;
        if ((i & 1) != 0) {
            onKeyguardTransparent();
        } else if ((i & 2) != 0) {
            fireAodState(bundle.getBoolean("flag"));
        } else if ((i & 4) != 0) {
            onGxzwIconChanged(bundle.getBoolean("flag"));
        } else if ((i & 8) != 0) {
            fireFingerprintPressed(bundle.getBoolean("flag"), bundle.getBoolean("turnScreenOn", false));
        } else if ((i & 16) != 0) {
            stopDozing();
        } else if ((i & 32) != 0) {
            onSimPinSecureChanged(bundle.getBoolean("flag"));
        } else if ((i & 64) != 0) {
            sendCommand(bundle.getString(a.a), bundle.getInt(AdvancedSlider.STATE));
        } else if ((i & 128) != 0) {
            onScreenTurnOnDelayed(bundle.getBoolean("flag"));
        } else if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            dispatchKeyEvent((KeyEvent) bundle.getParcelable("keyEvent", KeyEvent.class));
        } else {
            if ((i & 512) != 0) {
                ArrayMap arrayMap = new ArrayMap();
                arrayMap.put("key_systemui_action_notification_provider", queryNotification((Uri) bundle.getParcelable("uri", Uri.class)));
                return arrayMap;
            }
            if ((i & 1024) != 0) {
                try {
                    map = (Map) bundle.getSerializable("fullImageMap");
                } catch (ClassCastException e) {
                    Log.wtf("MiuiAodService", "get fullImageMap error: " + e.getMessage());
                    map = null;
                }
                setAodClockColor(bundle.getBoolean("flag"), (HashMap) bundle.getSerializable("map"), map);
            } else {
                if ((i & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0) {
                    return getAodViewTopAndBottom();
                }
                if ((i & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0) {
                    updateClockTranslation(bundle);
                } else if ((131072 & i) != 0) {
                    updateClockInfo(bundle);
                } else if ((i & 262144) != 0) {
                    applyLinkageState(bundle);
                }
            }
        }
        return null;
    }

    public Cursor queryNotification(Uri uri) {
        Log.i("MiuiAodService", "queryNotification: " + uri.toString());
        return NotificationProvider.query(uri);
    }

    public void sendCommand(String str, int i) {
        this.mHost.onCommand(str, i);
    }

    public void setAodClockColor(boolean z, HashMap map, Map map2) {
        this.mHost.updateClockColor(z, map, map2);
    }

    public void stopDozing() {
        this.mHost.stopDozing();
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass3.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()];
        if (i == 1) {
            this.mHost.addCallback(this.mHostCallback);
            return;
        }
        if (i == 2) {
            this.mRequestDoze.onRequestShowDoze();
        } else if (i == 3) {
            this.mRequestDoze.onRequestHideDoze();
        } else {
            if (i != 4) {
                return;
            }
            new Handler().postDelayed(new Runnable() { // from class: com.miui.aod.doze.MiuiAodService.1
                @Override // java.lang.Runnable
                public void run() {
                    MiuiAodService.this.mHost.removeCallback(MiuiAodService.this.mHostCallback);
                }
            }, 20L);
        }
    }

    public void updateClockInfo(Bundle bundle) {
        FlipLinkageClockCategoryInfoKt.setClockInfoString(bundle.getString("clockInfo", FlipLinkageClockCategoryInfoKt.getDEFAULT_CLOCK_INFO()));
    }

    public void updateClockTranslation(Bundle bundle) {
        this.mHost.updateClockTranslation(bundle);
    }
}

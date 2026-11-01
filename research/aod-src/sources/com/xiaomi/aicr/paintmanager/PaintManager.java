package com.xiaomi.aicr.paintmanager;

import android.os.Bundle;
import android.os.IBinder;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.paintservice.IPaintService;

/* JADX INFO: loaded from: classes2.dex */
public class PaintManager extends ManagerBase {
    public IPaintService mService;
    public PaintManagerStatus paintManagerStatus;

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void downloadSpeed(String str, long j) {
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        PaintManager paintManager;
        SmartLog.i("PaintManager:", "registerPlugin");
        try {
            Bundle bundle = new Bundle();
            paintManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(paintManager, super.toString(), "com.xiaomi.aicr.paintservice.IPaintService", "", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    paintManager.mService = IPaintService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    paintManager.paintManagerStatus = PaintManagerStatus.UNDOWNLOADED;
                    SmartLog.i("PaintManager:", "paintManagerStatus is UNDOWNLOADED!");
                    paintManager.onFirstTimeUse("com.xiaomi.aicr.paintservice.IPaintService");
                }
            } catch (Exception e) {
                e = e;
                SmartLog.i("PaintManager:", "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            paintManager = this;
        }
        paintManager.connected = paintManager.mService != null;
        if (paintManager.paintManagerStatus == PaintManagerStatus.UNDOWNLOADED) {
            SmartLog.i("PaintManager:", "paintManagerStatus is UNDOWNLOADED!!");
        } else if (paintManager.connected) {
            paintManager.paintManagerStatus = PaintManagerStatus.CONNECTED;
            SmartLog.i("PaintManager:", "paintManagerStatus is CONNECTED");
        } else if (paintManager.connected || paintManager.isSupport("com.xiaomi.aicr.paintservice.IPaintService")) {
            paintManager.paintManagerStatus = PaintManagerStatus.UNCONNECTED;
            SmartLog.i("PaintManager:", "paintManagerStatus is UNCONNECTED");
        } else {
            paintManager.paintManagerStatus = PaintManagerStatus.UNSUPPORTED;
            SmartLog.i("PaintManager:", "paintManagerStatus is UNSUPPORTED");
        }
        AiServiceConnection aiServiceConnection = paintManager.mServiceConn;
        if (aiServiceConnection != null) {
            aiServiceConnection.onServiceConnected(0);
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void unregisterPlugin() {
        SmartLog.i("PaintManager:", "unregisterPlugin");
        AiServiceConnection aiServiceConnection = this.mServiceConn;
        if (aiServiceConnection != null) {
            aiServiceConnection.onServiceDisconnected();
        }
        this.paintManagerStatus = PaintManagerStatus.UNCONNECTED;
        this.mService = null;
    }
}

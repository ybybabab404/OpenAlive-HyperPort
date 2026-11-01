package com.xiaomi.aicr.vision;

import android.os.Bundle;
import android.os.IBinder;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.plugin.IVisionService;

/* JADX INFO: loaded from: classes2.dex */
public class VisionManager extends ManagerBase {
    public IVisionService mService;

    public interface VisionCallback {
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void downloadSpeed(String str, long j) {
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        VisionManager visionManager;
        try {
            Bundle bundle = new Bundle();
            visionManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(visionManager, super.toString(), "com.xiaomi.aicr.plugin.IVisionService", "", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    visionManager.mService = IVisionService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    visionManager.onFirstTimeUse("com.xiaomi.aicr.plugin.IVisionService");
                }
            } catch (Exception e) {
                e = e;
                SmartLog.d("VisionManager", "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            visionManager = this;
        }
        visionManager.connected = visionManager.mService != null;
        if (visionManager.mServiceConn != null && visionManager.connected) {
            visionManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
        } else {
            if (visionManager.mServiceConn == null || visionManager.connected || visionManager.isSupport("com.xiaomi.aicr.plugin.IVisionService")) {
                return;
            }
            visionManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void unregisterPlugin() {
        SmartLog.d("VisionManager", "Service 断开连接");
        AiServiceConnection aiServiceConnection = this.mServiceConn;
        if (aiServiceConnection != null) {
            aiServiceConnection.onServiceDisconnected();
        }
    }
}

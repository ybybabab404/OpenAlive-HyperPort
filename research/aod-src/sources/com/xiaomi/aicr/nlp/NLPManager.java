package com.xiaomi.aicr.nlp;

import android.os.Bundle;
import android.os.IBinder;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.plugin.INerService;

/* JADX INFO: loaded from: classes2.dex */
public class NLPManager extends ManagerBase {
    public INerService mService;

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void downloadSpeed(String str, long j) {
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        NLPManager nLPManager;
        try {
            Bundle bundle = new Bundle();
            nLPManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(nLPManager, super.toString(), "com.xiaomi.aicr.plugin.INerService", "", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    nLPManager.mService = INerService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    nLPManager.onFirstTimeUse("com.xiaomi.aicr.plugin.INerService");
                }
            } catch (Exception e) {
                e = e;
                SmartLog.d("NLPManager", "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            nLPManager = this;
        }
        nLPManager.connected = nLPManager.mService != null;
        if (nLPManager.mServiceConn != null && nLPManager.connected) {
            nLPManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
        } else {
            if (nLPManager.mServiceConn == null || nLPManager.connected || nLPManager.isSupport("com.xiaomi.aicr.plugin.INerService")) {
                return;
            }
            nLPManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void unregisterPlugin() {
        SmartLog.d("NLPManager", "Service 断开连接");
        AiServiceConnection aiServiceConnection = this.mServiceConn;
        if (aiServiceConnection != null) {
            aiServiceConnection.onServiceDisconnected();
        }
    }
}

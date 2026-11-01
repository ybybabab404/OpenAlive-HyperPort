package com.xiaomi.aicr.miot;

import android.os.Bundle;
import android.os.IBinder;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.plugin.IMIotAlgoService;

/* JADX INFO: loaded from: classes2.dex */
public class MIotManager extends ManagerBase {
    public IMIotAlgoService mService;

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void downloadSpeed(String str, long j) {
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        MIotManager mIotManager;
        try {
            Bundle bundle = new Bundle();
            mIotManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(mIotManager, super.toString(), "com.xiaomi.aicr.plugin.IMIotAlgoService", "", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    mIotManager.mService = IMIotAlgoService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    mIotManager.onFirstTimeUse("com.xiaomi.aicr.plugin.IMIotAlgoService");
                }
            } catch (Exception e) {
                e = e;
                SmartLog.d("HMindManager", "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            mIotManager = this;
        }
        mIotManager.connected = mIotManager.mService != null;
        if (mIotManager.mServiceConn != null && mIotManager.connected) {
            mIotManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
        } else {
            if (mIotManager.mServiceConn == null || mIotManager.connected || mIotManager.isSupport("com.xiaomi.aicr.plugin.IMIotAlgoService")) {
                return;
            }
            mIotManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void unregisterPlugin() {
        SmartLog.d("HMindManager", "Service 断开连接");
        AiServiceConnection aiServiceConnection = this.mServiceConn;
        if (aiServiceConnection != null) {
            aiServiceConnection.onServiceDisconnected();
        }
    }
}

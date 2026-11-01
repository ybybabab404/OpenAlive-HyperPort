package com.xiaomi.aicr.vision.imagesegment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.plugin.IImageSegmentService;
import com.xiaomi.aicr.vision.VisionAttribute;

/* JADX INFO: loaded from: classes2.dex */
public class ImageSegmentManager extends ManagerBase {
    public IImageSegmentService mService;

    public ImageSegmentManager(Context context, AiServiceConnection aiServiceConnection) {
        super(context, aiServiceConnection);
    }

    public String depth_getSegmentMainFuncVersion() {
        SmartLog.d("ImageSegmentManager", "depth_getSegmentMainFuncVersion");
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.getDepthSegmentMainFuncVersion();
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e("ImageSegmentManager", "depth_getSegmentMainFuncVersion exception: " + e);
            return null;
        }
    }

    public Bitmap depth_segmentMainFunc(Bitmap bitmap) {
        SmartLog.d("ImageSegmentManager", "depth_segmentMainFunc");
        if (bitmap == null) {
            throw new NullPointerException("bitmap is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.depthSegmentMainFunc(bitmap);
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e("ImageSegmentManager", "depth_segment exception: " + e);
            return null;
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void downloadSpeed(String str, long j) {
    }

    public int initAlgorithmCachePath() {
        try {
            SmartLog.d("ImageSegmentManager", "initAlgorithmCachePath");
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.initAlgorithmCachePath();
            }
            return -1;
        } catch (Exception e) {
            SmartLog.e("ImageSegmentManager", "initAlgorithmCachePath exception: " + e);
            return -1;
        }
    }

    public int initPersonModel() {
        SmartLog.d("ImageSegmentManager", "initPersonModel");
        IImageSegmentService iImageSegmentService = this.mService;
        if (iImageSegmentService != null) {
            return iImageSegmentService.initPersonModel();
        }
        return -1;
    }

    public int initSkyModel() {
        SmartLog.d("ImageSegmentManager", "initSkyModel");
        IImageSegmentService iImageSegmentService = this.mService;
        if (iImageSegmentService != null) {
            return iImageSegmentService.initSkyModel();
        }
        return -1;
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        ImageSegmentManager imageSegmentManager;
        try {
            Bundle bundle = new Bundle();
            imageSegmentManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(imageSegmentManager, super.toString(), "com.xiaomi.aicr.plugin.IImageSegmentService", "5.0", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    imageSegmentManager.mService = IImageSegmentService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    imageSegmentManager.onFirstTimeUse("com.xiaomi.aicr.plugin.IImageSegmentService");
                }
            } catch (Exception e) {
                e = e;
                SmartLog.d("ImageSegmentManager", "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            imageSegmentManager = this;
        }
        imageSegmentManager.connected = imageSegmentManager.mService != null;
        if (imageSegmentManager.mServiceConn != null && imageSegmentManager.connected) {
            imageSegmentManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
        } else {
            if (imageSegmentManager.mServiceConn == null || imageSegmentManager.connected || imageSegmentManager.isSupport("com.xiaomi.aicr.plugin.IImageSegmentService")) {
                return;
            }
            imageSegmentManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
        }
    }

    public SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute) {
        SmartLog.d("ImageSegmentManager", "segment bitmap");
        if (bitmap == null || visionAttribute == null) {
            throw new NullPointerException("bitmap or attr is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.segment(bitmap, visionAttribute);
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e("ImageSegmentManager", "segment exception: " + e);
            return null;
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    public void unregisterPlugin() {
        SmartLog.d("ImageSegmentManager", "Service 断开连接");
        AiServiceConnection aiServiceConnection = this.mServiceConn;
        if (aiServiceConnection != null) {
            aiServiceConnection.onServiceDisconnected();
        }
    }
}

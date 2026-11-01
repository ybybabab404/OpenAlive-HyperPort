package com.miui.support.drawable;

import miuix.device.DeviceUtils;

/* JADX INFO: loaded from: classes2.dex */
public class DrawableUtils {
    public static Boolean mIsCommonLiteStrategy;

    public static boolean isCommonLiteStrategy() {
        if (mIsCommonLiteStrategy == null) {
            mIsCommonLiteStrategy = Boolean.valueOf(DeviceUtils.isMiuiLiteV2() || DeviceUtils.isLiteV1StockPlus() || DeviceUtils.isMiuiMiddle());
        }
        return mIsCommonLiteStrategy.booleanValue();
    }
}

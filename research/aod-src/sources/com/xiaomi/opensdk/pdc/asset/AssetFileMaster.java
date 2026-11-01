package com.xiaomi.opensdk.pdc.asset;

import com.xiaomi.opensdk.pdc.Constants;

/* JADX INFO: loaded from: classes2.dex */
public final class AssetFileMaster {

    /* JADX INFO: renamed from: com.xiaomi.opensdk.pdc.asset.AssetFileMaster$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType;

        static {
            int[] iArr = new int[Constants.ErrorType.values().length];
            $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType = iArr;
            try {
                iArr[Constants.ErrorType.RETRIABLE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[Constants.ErrorType.UNRETRIABLE_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[Constants.ErrorType.AUTH_FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}

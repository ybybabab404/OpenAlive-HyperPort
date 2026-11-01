package miui.cloud.sync;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class MiCloudStatusInfo {

    public class ItemInfo {
        public String mLocalizedName;
        public String mName;
        public long mUsed;

        public String toString() {
            return "ItemInfo{mName=" + this.mName + ", mLocalizedName=" + this.mLocalizedName + ", mUsed='" + this.mUsed + '}';
        }
    }

    public class QuotaInfo {
        public ArrayList mItemInfoList;
        public long mTotal;
        public long mUsed;
        public String mWarn;
        public long mYearlyPackageCreateTime;
        public long mYearlyPackageExpireTime;
        public long mYearlyPackageSize;
        public String mYearlyPackageType;

        public String toString() {
            return "QuotaInfo{mTotal=" + this.mTotal + ", mUsed=" + this.mUsed + ", mWarn='" + this.mWarn + "', mYearlyPackageType='" + this.mYearlyPackageType + "', mYearlyPackageSize=" + this.mYearlyPackageSize + ", mYearlyPackageCreateTime=" + this.mYearlyPackageCreateTime + ", mYearlyPackageExpireTime=" + this.mYearlyPackageExpireTime + ", mItemInfoList=" + this.mItemInfoList + '}';
        }
    }
}

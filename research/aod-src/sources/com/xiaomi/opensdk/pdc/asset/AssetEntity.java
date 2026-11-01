package com.xiaomi.opensdk.pdc.asset;

/* JADX INFO: loaded from: classes2.dex */
public class AssetEntity {
    public final String assetID;
    public final long size;
    public final String type;

    public boolean equals(Object obj) {
        if (obj instanceof AssetEntity) {
            AssetEntity assetEntity = (AssetEntity) obj;
            if (this.type.equals(assetEntity.type) && this.assetID.equals(assetEntity.assetID) && this.size == assetEntity.size) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return "AssetEntity [type=" + this.type + ", assetID=" + this.assetID + ", size=" + this.size + "]";
    }
}

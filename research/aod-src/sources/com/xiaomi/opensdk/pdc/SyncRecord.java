package com.xiaomi.opensdk.pdc;

import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class SyncRecord {
    public final AssetEntity[] assetEntities;
    public final JSONObject contentJson;
    public final long eTag;
    public final String id;
    public final String parentId;
    public final Constants.RecordStatus status;
    public final String type;
    public final String uniqueKey;

    public boolean equals(Object obj) {
        if (!(obj instanceof SyncRecord)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SyncRecord syncRecord = (SyncRecord) obj;
        return new Object[]{Long.valueOf(this.eTag), this.id, this.uniqueKey, this.parentId, this.type, this.contentJson, this.assetEntities, this.status}.equals(new Object[]{Long.valueOf(syncRecord.eTag), syncRecord.id, syncRecord.uniqueKey, syncRecord.parentId, syncRecord.type, syncRecord.contentJson, syncRecord.assetEntities, syncRecord.status});
    }

    public int hashCode() {
        return new Object[]{Long.valueOf(this.eTag), this.id, this.uniqueKey, this.parentId, this.type, this.contentJson, this.assetEntities, this.status}.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("eTag=");
        sb.append(this.eTag);
        sb.append("; id=");
        sb.append(this.id);
        sb.append("; uniqueKey=");
        sb.append(this.uniqueKey);
        sb.append("; parentId=");
        sb.append(this.parentId);
        sb.append("; type=");
        sb.append(this.type);
        sb.append("; status=");
        sb.append(this.status);
        sb.append("; content=");
        sb.append(this.contentJson);
        AssetEntity[] assetEntityArr = this.assetEntities;
        if (assetEntityArr != null) {
            for (AssetEntity assetEntity : assetEntityArr) {
                sb.append("; asset=");
                sb.append(assetEntity);
            }
        } else {
            sb.append("; no assets");
        }
        return sb.toString();
    }
}

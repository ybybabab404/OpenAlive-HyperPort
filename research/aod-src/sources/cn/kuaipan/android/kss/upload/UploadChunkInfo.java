package cn.kuaipan.android.kss.upload;

import cn.kuaipan.android.kss.KssDef;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
class UploadChunkInfo implements KssDef {
    public static final HashSet sReRequestSet;
    public static final HashSet sReTrySet;
    public static final HashSet sSessionExpiredSet;

    static {
        HashSet hashSet = new HashSet();
        sReRequestSet = hashSet;
        HashSet hashSet2 = new HashSet();
        sReTrySet = hashSet2;
        HashSet hashSet3 = new HashSet();
        sSessionExpiredSet = hashSet3;
        hashSet.add("ERR_INVALID_FILE_META");
        hashSet.add("ERR_INVALID_BLOCK_META");
        hashSet.add("ERR_INVALID_UPLOAD_ID");
        hashSet.add("ERR_INVALID_CHUNK_POS");
        hashSet.add("ERR_INVALID_CHUNK_SIZE");
        hashSet.add("ERR_CHUNK_OUT_OF_RANGE");
        hashSet2.add("ERR_CHUNK_CORRUPTED");
        hashSet2.add("ERR_SERVER_EXCEPTION");
        hashSet2.add("ERR_STORAGE_REQUEST_ERROR");
        hashSet2.add("ERR_STORAGE_REQUEST_FAILED");
        hashSet3.add("ERR_INVALID_FILE_META");
        hashSet3.add("ERR_INVALID_BLOCK_META");
        hashSet3.add("ERR_INVALID_UPLOAD_ID");
    }
}

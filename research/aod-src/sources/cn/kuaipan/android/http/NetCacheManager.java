package cn.kuaipan.android.http;

import android.content.Context;
import android.text.TextUtils;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.utils.FileUtils;
import cn.kuaipan.android.utils.TwoKeyHashMap;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class NetCacheManager {
    public static final TwoKeyHashMap sCaches = new TwoKeyHashMap();
    public final Context mContext;
    public final String mDirName;
    public final boolean mExternal;
    public String mFolderPath;
    public int mLatestId;

    /* JADX INFO: renamed from: cn.kuaipan.android.http.NetCacheManager$1, reason: invalid class name */
    class AnonymousClass1 extends Thread {
        public final /* synthetic */ File val$folder;

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            FileUtils.deleteChildren(this.val$folder);
        }
    }

    public File assignCache() {
        File nextCache;
        do {
            nextCache = getNextCache();
        } while (nextCache.exists());
        nextCache.deleteOnExit();
        return nextCache;
    }

    public final File getNextCache() {
        int i;
        synchronized (this) {
            i = this.mLatestId + 1;
            this.mLatestId = i;
        }
        String str = String.format("%08d.tmp", Integer.valueOf(i));
        File cacheDir = FileUtils.getCacheDir(this.mContext, this.mDirName, this.mExternal);
        if (cacheDir == null) {
            throw new KscRuntimeException(500004);
        }
        this.mFolderPath = cacheDir.getAbsolutePath();
        return new File(cacheDir, str);
    }

    public void releaseCache(File file) {
        if (file == null || !TextUtils.equals(this.mFolderPath, file.getParent())) {
            return;
        }
        file.delete();
    }
}

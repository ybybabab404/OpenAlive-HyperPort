package com.miui.maml.util;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaDataSource;
import android.os.AsyncTask;
import android.os.MemoryFile;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.miui.maml.ResourceManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.util.zip.InflaterInputStream;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class MamlMediaDataSource extends MediaDataSource {
    private static final int MAX_VIDEO_SIZE = 52428800;
    private static final int MEMORY_THRESHOLD = 104857600;
    private static final String TAG = "MamlMediaDataSource";
    private Context mContext;
    private long mCurrentPosition;
    private MemoryFile mFile;
    private ResourceManager mManager;
    private String mPath;
    private long mSize;
    private InputStream mStream;
    private boolean mSupportMark;
    private final Object mLock = new Object();
    private ComponentCallbacks2 mComponentCallback = new ComponentCallbacks2() { // from class: com.miui.maml.util.MamlMediaDataSource.1
        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(@NonNull Configuration configuration) {
        }

        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
            MamlMediaDataSource.this.releaseMemoryFile();
        }

        @Override // android.content.ComponentCallbacks2
        public void onTrimMemory(int i) {
            if (i >= 10) {
                MamlMediaDataSource.this.releaseMemoryFile();
            }
        }
    };

    public static class MemoryFileAsyncTask extends AsyncTask<Void, Void, Void> {
        private String mFilePath;
        private ResourceManager mManager;
        private MamlMediaDataSource mSource;

        public MemoryFileAsyncTask(String str, ResourceManager resourceManager, MamlMediaDataSource mamlMediaDataSource) {
            this.mFilePath = str;
            this.mManager = resourceManager;
            this.mSource = mamlMediaDataSource;
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            MamlMediaDataSource mamlMediaDataSource;
            ResourceManager resourceManager = this.mManager;
            if (resourceManager == null || (mamlMediaDataSource = this.mSource) == null) {
                return null;
            }
            mamlMediaDataSource.setMemoryFile(resourceManager.getFile(this.mFilePath));
            return null;
        }
    }

    public MamlMediaDataSource(Context context, ResourceManager resourceManager, String str) {
        this.mContext = context;
        this.mManager = resourceManager;
        this.mPath = str;
        init();
        try {
            this.mContext.registerComponentCallbacks(this.mComponentCallback);
        } catch (Exception unused) {
        }
    }

    private void closeStream() {
        synchronized (this.mLock) {
            InputStream inputStream = this.mStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.mStream = null;
            }
        }
    }

    private void generateMemoryFile() {
        if (this.mFile != null) {
            return;
        }
        new MemoryFileAsyncTask(this.mPath, this.mManager, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private void init() {
        if (TextUtils.isEmpty(this.mPath)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                long[] jArr = new long[1];
                InputStream inputStream = this.mManager.getInputStream(this.mPath, jArr);
                this.mStream = inputStream;
                this.mSize = jArr[0];
                if (inputStream != null) {
                    boolean zMarkSupported = inputStream.markSupported();
                    this.mSupportMark = zMarkSupported;
                    if (zMarkSupported) {
                        this.mStream.mark(Integer.MAX_VALUE);
                    }
                    tryToGenerateMemoryFile();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private boolean isFileSizeValid() {
        return this.mSize < 52428800;
    }

    private boolean isMemoryEnough() {
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return !memoryInfo.lowMemory && memoryInfo.availMem - memoryInfo.threshold > 104857600;
    }

    private boolean resetStream() {
        InputStream inputStream = this.mStream;
        if (inputStream == null) {
            return false;
        }
        if (this.mSupportMark) {
            try {
                inputStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                inputStream.close();
                this.mStream = this.mManager.getInputStream(this.mPath, new long[1]);
            } catch (IOException e2) {
                e2.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMemoryFile(MemoryFile memoryFile) {
        synchronized (this.mLock) {
            this.mFile = memoryFile;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        closeStream();
        releaseMemoryFile();
        try {
            this.mContext.unregisterComponentCallbacks(this.mComponentCallback);
        } catch (Exception unused) {
        }
    }

    public void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public String getPath() {
        return this.mPath;
    }

    @Override // android.media.MediaDataSource
    public long getSize() {
        return this.mSize;
    }

    @Override // android.media.MediaDataSource
    public int readAt(long j, byte[] bArr, int i, int i2) {
        long j2;
        if (this.mFile == null && this.mStream == null) {
            init();
        }
        synchronized (this.mLock) {
            try {
                int bytes = 0;
                if (this.mFile != null) {
                    long j3 = ((long) i2) + j;
                    try {
                        long j4 = this.mSize;
                        if (j3 > j4) {
                            if (j >= j4) {
                                MamlLog.w(TAG, "readAt: position is larger than file size, return 0");
                                return 0;
                            }
                            MamlLog.w(TAG, "readAt: position+size is larger than file size, read left data");
                            i2 = (int) (this.mSize - j);
                        }
                        bytes = this.mFile.readBytes(bArr, (int) j, i, i2);
                    } catch (BufferUnderflowException unused) {
                        return 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.mFile.close();
                        this.mFile = null;
                        return 0;
                    }
                } else if (this.mStream != null) {
                    long j5 = this.mCurrentPosition;
                    if (j5 <= j) {
                        j2 = j - j5;
                    } else {
                        if (!resetStream()) {
                            return 0;
                        }
                        j2 = j;
                    }
                    if (j2 != 0) {
                        try {
                            this.mStream.skip(j2);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    bytes = this.mStream.read(bArr, i, i2);
                }
                if (bytes > 0) {
                    this.mCurrentPosition = j + ((long) bytes);
                }
                return bytes;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void releaseMemoryFile() {
        synchronized (this.mLock) {
            try {
                MemoryFile memoryFile = this.mFile;
                if (memoryFile != null) {
                    memoryFile.close();
                    this.mFile = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void tryToGenerateMemoryFile() {
        if (this.mFile == null && (this.mStream instanceof InflaterInputStream) && isMemoryEnough() && isFileSizeValid()) {
            generateMemoryFile();
        }
    }
}

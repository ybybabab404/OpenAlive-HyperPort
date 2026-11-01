package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.concurrent.futures.ResolvableFuture;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class ProfileVerifier {
    public static final ResolvableFuture sFuture = ResolvableFuture.create();
    public static final Object SYNC_OBJ = new Object();
    public static CompilationStatus sCompilationStatus = null;

    @RequiresApi
    public static class Api33Impl {
        @DoNotInline
        public static PackageInfo getPackageInfo(PackageManager packageManager, Context context) throws PackageManager.NameNotFoundException {
            return packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0L));
        }
    }

    @RestrictTo
    public static class Cache {
        public final long mInstalledCurrentProfileSize;
        public final long mPackageLastUpdateTime;
        public final int mResultCode;
        public final int mSchema;

        public Cache(int i, int i2, long j, long j2) {
            this.mSchema = i;
            this.mResultCode = i2;
            this.mPackageLastUpdateTime = j;
            this.mInstalledCurrentProfileSize = j2;
        }

        public static Cache readFromFile(File file) throws IOException {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            try {
                Cache cache = new Cache(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readLong(), dataInputStream.readLong());
                dataInputStream.close();
                return cache;
            } catch (Throwable th) {
                try {
                    dataInputStream.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof Cache)) {
                Cache cache = (Cache) obj;
                if (this.mResultCode == cache.mResultCode && this.mPackageLastUpdateTime == cache.mPackageLastUpdateTime && this.mSchema == cache.mSchema && this.mInstalledCurrentProfileSize == cache.mInstalledCurrentProfileSize) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mResultCode), Long.valueOf(this.mPackageLastUpdateTime), Integer.valueOf(this.mSchema), Long.valueOf(this.mInstalledCurrentProfileSize));
        }

        public void writeOnFile(File file) throws IOException {
            file.delete();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                dataOutputStream.writeInt(this.mSchema);
                dataOutputStream.writeInt(this.mResultCode);
                dataOutputStream.writeLong(this.mPackageLastUpdateTime);
                dataOutputStream.writeLong(this.mInstalledCurrentProfileSize);
                dataOutputStream.close();
            } catch (Throwable th) {
                try {
                    dataOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    public static class CompilationStatus {
        public final boolean mHasCurrentProfile;
        public final boolean mHasReferenceProfile;
        public final int mResultCode;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface ResultCode {
        }

        public CompilationStatus(int i, boolean z, boolean z2) {
            this.mResultCode = i;
            this.mHasCurrentProfile = z2;
            this.mHasReferenceProfile = z;
        }
    }

    public static long getPackageLastUpdateTime(Context context) {
        return Api33Impl.getPackageInfo(context.getApplicationContext().getPackageManager(), context).lastUpdateTime;
    }

    public static CompilationStatus setCompilationStatus(int i, boolean z, boolean z2) {
        CompilationStatus compilationStatus = new CompilationStatus(i, z, z2);
        sCompilationStatus = compilationStatus;
        sFuture.set(compilationStatus);
        return sCompilationStatus;
    }

    public static CompilationStatus writeProfileVerification(Context context, boolean z) {
        Cache fromFile;
        int i;
        CompilationStatus compilationStatus;
        if (!z && (compilationStatus = sCompilationStatus) != null) {
            return compilationStatus;
        }
        synchronized (SYNC_OBJ) {
            if (!z) {
                try {
                    CompilationStatus compilationStatus2 = sCompilationStatus;
                    if (compilationStatus2 != null) {
                        return compilationStatus2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            File file = new File(new File("/data/misc/profiles/ref/", context.getPackageName()), "primary.prof");
            long length = file.length();
            int i2 = 0;
            boolean z2 = file.exists() && length > 0;
            File file2 = new File(new File("/data/misc/profiles/cur/0/", context.getPackageName()), "primary.prof");
            long length2 = file2.length();
            boolean z3 = file2.exists() && length2 > 0;
            try {
                long packageLastUpdateTime = getPackageLastUpdateTime(context);
                File file3 = new File(context.getFilesDir(), "profileInstalled");
                if (file3.exists()) {
                    try {
                        fromFile = Cache.readFromFile(file3);
                    } catch (IOException unused) {
                        return setCompilationStatus(131072, z2, z3);
                    }
                } else {
                    fromFile = null;
                }
                if (fromFile != null && fromFile.mPackageLastUpdateTime == packageLastUpdateTime && (i = fromFile.mResultCode) != 2) {
                    i2 = i;
                } else if (z2) {
                    i2 = 1;
                } else if (z3) {
                    i2 = 2;
                }
                if (z && z3 && i2 != 1) {
                    i2 = 2;
                }
                if (fromFile != null && fromFile.mResultCode == 2 && i2 == 1 && length < fromFile.mInstalledCurrentProfileSize) {
                    i2 = 3;
                }
                int i3 = i2;
                Cache cache = new Cache(1, i3, packageLastUpdateTime, length2);
                if (fromFile == null || !fromFile.equals(cache)) {
                    try {
                        cache.writeOnFile(file3);
                    } catch (IOException unused2) {
                        i3 = 196608;
                    }
                }
                return setCompilationStatus(i3, z2, z3);
            } catch (PackageManager.NameNotFoundException unused3) {
                return setCompilationStatus(65536, z2, z3);
            }
        }
    }
}

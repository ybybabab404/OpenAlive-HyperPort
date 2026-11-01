package androidx.profileinstaller;

import android.content.res.AssetManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public class DeviceProfileWriter {
    public final String mApkName;
    public final AssetManager mAssetManager;
    public final File mCurProfile;
    public final ProfileInstaller.DiagnosticsCallback mDiagnostics;
    public final Executor mExecutor;
    public DexProfileData[] mProfile;
    public final String mProfileMetaSourceLocation;
    public final String mProfileSourceLocation;
    public byte[] mTranscodedProfile;
    public boolean mDeviceSupportsAotProfile = false;
    public final byte[] mDesiredVersion = desiredVersion();

    public DeviceProfileWriter(AssetManager assetManager, Executor executor, ProfileInstaller.DiagnosticsCallback diagnosticsCallback, String str, String str2, String str3, File file) {
        this.mAssetManager = assetManager;
        this.mExecutor = executor;
        this.mDiagnostics = diagnosticsCallback;
        this.mApkName = str;
        this.mProfileSourceLocation = str2;
        this.mProfileMetaSourceLocation = str3;
        this.mCurProfile = file;
    }

    public static byte[] desiredVersion() {
        int i = Build.VERSION.SDK_INT;
        if (i <= 33 && i == 33) {
            return ProfileVersion.V015_S;
        }
        return null;
    }

    public static boolean requiresMetadata() {
        int i = Build.VERSION.SDK_INT;
        return i <= 33 && i == 33;
    }

    public final DeviceProfileWriter addMetadata(DexProfileData[] dexProfileDataArr, byte[] bArr) {
        try {
            InputStream inputStreamOpenStreamFromAssets = openStreamFromAssets(this.mAssetManager, this.mProfileMetaSourceLocation);
            if (inputStreamOpenStreamFromAssets == null) {
                if (inputStreamOpenStreamFromAssets != null) {
                    inputStreamOpenStreamFromAssets.close();
                }
                return null;
            }
            try {
                this.mProfile = ProfileTranscoder.readMeta(inputStreamOpenStreamFromAssets, ProfileTranscoder.readHeader(inputStreamOpenStreamFromAssets, ProfileTranscoder.MAGIC_PROFM), bArr, dexProfileDataArr);
                inputStreamOpenStreamFromAssets.close();
                return this;
            } catch (Throwable th) {
                try {
                    inputStreamOpenStreamFromAssets.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            this.mDiagnostics.onResultReceived(9, e);
        } catch (IOException e2) {
            this.mDiagnostics.onResultReceived(7, e2);
        } catch (IllegalStateException e3) {
            this.mProfile = null;
            this.mDiagnostics.onResultReceived(8, e3);
        }
    }

    public final void assertDeviceAllowsProfileInstallerAotWritesCalled() {
        if (!this.mDeviceSupportsAotProfile) {
            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
        }
    }

    public boolean deviceAllowsProfileInstallerAotWrites() {
        if (this.mDesiredVersion == null) {
            result(3, Integer.valueOf(Build.VERSION.SDK_INT));
            return false;
        }
        if (this.mCurProfile.canWrite()) {
            this.mDeviceSupportsAotProfile = true;
            return true;
        }
        result(4, null);
        return false;
    }

    public final InputStream getProfileInputStream(AssetManager assetManager) {
        try {
            return openStreamFromAssets(assetManager, this.mProfileSourceLocation);
        } catch (FileNotFoundException e) {
            this.mDiagnostics.onResultReceived(6, e);
            return null;
        } catch (IOException e2) {
            this.mDiagnostics.onResultReceived(7, e2);
            return null;
        }
    }

    public final InputStream openStreamFromAssets(AssetManager assetManager, String str) {
        try {
            return assetManager.openFd(str).createInputStream();
        } catch (FileNotFoundException e) {
            String message = e.getMessage();
            if (message != null && message.contains("compressed")) {
                this.mDiagnostics.onDiagnosticReceived(5, null);
            }
            return null;
        }
    }

    public DeviceProfileWriter read() {
        DeviceProfileWriter deviceProfileWriterAddMetadata;
        assertDeviceAllowsProfileInstallerAotWritesCalled();
        if (this.mDesiredVersion != null) {
            InputStream profileInputStream = getProfileInputStream(this.mAssetManager);
            if (profileInputStream != null) {
                this.mProfile = readProfileInternal(profileInputStream);
            }
            DexProfileData[] dexProfileDataArr = this.mProfile;
            if (dexProfileDataArr != null && requiresMetadata() && (deviceProfileWriterAddMetadata = addMetadata(dexProfileDataArr, this.mDesiredVersion)) != null) {
                return deviceProfileWriterAddMetadata;
            }
        }
        return this;
    }

    public final DexProfileData[] readProfileInternal(InputStream inputStream) {
        try {
            try {
                try {
                    try {
                        DexProfileData[] profile = ProfileTranscoder.readProfile(inputStream, ProfileTranscoder.readHeader(inputStream, ProfileTranscoder.MAGIC_PROF), this.mApkName);
                        try {
                            return profile;
                        } catch (IOException e) {
                            return profile;
                        }
                    } catch (IOException e2) {
                        this.mDiagnostics.onResultReceived(7, e2);
                        return null;
                    }
                } catch (IllegalStateException e3) {
                    this.mDiagnostics.onResultReceived(8, e3);
                    inputStream.close();
                    return null;
                }
            } catch (IOException e4) {
                this.mDiagnostics.onResultReceived(7, e4);
                inputStream.close();
                return null;
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e5) {
                this.mDiagnostics.onResultReceived(7, e5);
            }
        }
    }

    public final void result(final int i, final Object obj) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.profileinstaller.DeviceProfileWriter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mDiagnostics.onResultReceived(i, obj);
            }
        });
    }

    public DeviceProfileWriter transcodeIfNeeded() {
        DexProfileData[] dexProfileDataArr = this.mProfile;
        byte[] bArr = this.mDesiredVersion;
        if (dexProfileDataArr != null && bArr != null) {
            assertDeviceAllowsProfileInstallerAotWritesCalled();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ProfileTranscoder.writeHeader(byteArrayOutputStream, bArr);
                    if (!ProfileTranscoder.transcodeAndWriteBody(byteArrayOutputStream, bArr, dexProfileDataArr)) {
                        this.mDiagnostics.onResultReceived(5, null);
                        this.mProfile = null;
                        byteArrayOutputStream.close();
                        return this;
                    }
                    this.mTranscodedProfile = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    this.mProfile = null;
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                this.mDiagnostics.onResultReceived(7, e);
            } catch (IllegalStateException e2) {
                this.mDiagnostics.onResultReceived(8, e2);
            }
        }
        return this;
    }

    public boolean write() {
        byte[] bArr = this.mTranscodedProfile;
        if (bArr == null) {
            return false;
        }
        assertDeviceAllowsProfileInstallerAotWritesCalled();
        try {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(this.mCurProfile);
                    try {
                        Encoding.writeAll(byteArrayInputStream, fileOutputStream);
                        result(1, null);
                        fileOutputStream.close();
                        byteArrayInputStream.close();
                        this.mTranscodedProfile = null;
                        this.mProfile = null;
                        return true;
                    } catch (Throwable th) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            } catch (Throwable th5) {
                this.mTranscodedProfile = null;
                this.mProfile = null;
                throw th5;
            }
        } catch (FileNotFoundException e) {
            result(6, e);
            this.mTranscodedProfile = null;
            this.mProfile = null;
            return false;
        } catch (IOException e2) {
            result(7, e2);
            this.mTranscodedProfile = null;
            this.mProfile = null;
            return false;
        }
    }
}

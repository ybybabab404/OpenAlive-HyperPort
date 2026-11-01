package com.miui.maml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.MemoryFile;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.Utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilderFactory;
import miuix.core.util.IOUtils;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ResourceLoader {
    private static final String CONFIG_FILE_NAME = "config.xml";
    private static final String IMAGES_FOLDER_NAME = "images";
    private static final String LOG_TAG = "ResourceLoader";
    private static final String MANIFEST_FILE_NAME = "manifest.xml";
    protected String mLanguageCountrySuffix;
    protected String mLanguageSuffix;
    protected Locale mLocale;
    protected String mManifestName = MANIFEST_FILE_NAME;
    protected String mConfigName = CONFIG_FILE_NAME;

    private byte[] getBytes(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    private String getPathForLanguage(String str, String str2) {
        if (!TextUtils.isEmpty(this.mLanguageCountrySuffix)) {
            String str3 = str2 + "_" + this.mLanguageCountrySuffix + "/" + str;
            if (resourceExists(str3)) {
                return str3;
            }
        }
        if (!TextUtils.isEmpty(this.mLanguageSuffix)) {
            String str4 = str2 + "_" + this.mLanguageSuffix + "/" + str;
            if (resourceExists(str4)) {
                return str4;
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            String str5 = str2 + "/" + str;
            if (resourceExists(str5)) {
                return str5;
            }
        }
        if (resourceExists(str)) {
            return str;
        }
        return null;
    }

    private Element getXmlRoot(String str) {
        InputStream inputStream = getInputStream(getPathForLanguage(str));
        try {
            if (inputStream != null) {
                return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).getDocumentElement();
            }
            MamlLog.i(LOG_TAG, "getXmlRoot local inputStream is null");
            return null;
        } catch (Exception | OutOfMemoryError e) {
            MamlLog.e(LOG_TAG, e.toString());
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public void finish() {
    }

    public ResourceManager.BitmapInfo getBitmapInfo(String str, BitmapFactory.Options options) {
        String pathForLanguage = getPathForLanguage(str, IMAGES_FOLDER_NAME);
        if (pathForLanguage == null) {
            MamlLog.d(LOG_TAG, "TRY AGAIN to get getPathForLanguage: " + str);
            pathForLanguage = getPathForLanguage(str, IMAGES_FOLDER_NAME);
            if (pathForLanguage == null) {
                MamlLog.i(LOG_TAG, "fail to get getPathForLanguage: " + str);
                return null;
            }
        }
        InputStream inputStream = getInputStream(pathForLanguage);
        if (inputStream == null) {
            MamlLog.d(LOG_TAG, "TRY AGAIN to get InputStream: " + str);
            inputStream = getInputStream(pathForLanguage);
            if (inputStream == null) {
                MamlLog.i(LOG_TAG, "fail to get InputStream: " + str);
                return null;
            }
        }
        try {
            try {
                Rect rect = new Rect();
                byte[] bytes = getBytes(inputStream);
                Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                if (bitmapDecodeByteArray != null) {
                    return new ResourceManager.BitmapInfo(bitmapDecodeByteArray, rect);
                }
                MamlLog.d(LOG_TAG, "TRY AGAIN to decode bitmap: " + str);
                if (BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options) == null) {
                    MamlLog.i(LOG_TAG, "fail to decode bitmap: " + str);
                    return null;
                }
                return null;
            } catch (Exception | OutOfMemoryError e) {
                MamlLog.e(LOG_TAG, e.toString());
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public Element getConfigRoot() {
        return getXmlRoot(this.mConfigName);
    }

    @Nullable
    public File getExtraFile(String str) {
        return null;
    }

    public MemoryFile getFile(String str) {
        long[] jArr = new long[1];
        InputStream inputStream = getInputStream(str, jArr);
        if (inputStream == null) {
            return null;
        }
        try {
            try {
                byte[] bArr = new byte[65536];
                MemoryFile memoryFile = new MemoryFile(str, (int) jArr[0]);
                int i = 0;
                while (true) {
                    int i2 = inputStream.read(bArr, 0, 65536);
                    if (i2 <= 0) {
                        break;
                    }
                    memoryFile.writeBytes(bArr, 0, i, i2);
                    i += i2;
                }
                if (memoryFile.length() > 0) {
                    IOUtils.closeQuietly(inputStream);
                    return memoryFile;
                }
            } catch (IOException | OutOfMemoryError e) {
                MamlLog.e(LOG_TAG, e.toString());
            }
            IOUtils.closeQuietly(inputStream);
            return null;
        } catch (Throwable th) {
            IOUtils.closeQuietly(inputStream);
            throw th;
        }
    }

    public String getID() {
        return "";
    }

    public final InputStream getInputStream(String str) {
        return getInputStream(str, null);
    }

    public abstract InputStream getInputStream(String str, long[] jArr);

    public Locale getLocale() {
        return this.mLocale;
    }

    public Element getManifestRoot() {
        return getXmlRoot(this.mManifestName);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002d A[PHI: r0
  0x002d: PHI (r0v4 java.lang.String) = (r0v3 java.lang.String), (r0v3 java.lang.String), (r0v6 java.lang.String) binds: [B:7:0x0016, B:9:0x001e, B:11:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:6:0x0015  */
    public String getPathForLanguage(String str) {
        String strAddFileNameSuffix;
        String str2;
        if (TextUtils.isEmpty(this.mLanguageCountrySuffix)) {
            strAddFileNameSuffix = null;
        } else {
            strAddFileNameSuffix = Utils.addFileNameSuffix(str, this.mLanguageCountrySuffix);
            if (!resourceExists(strAddFileNameSuffix)) {
                strAddFileNameSuffix = null;
            }
        }
        if (strAddFileNameSuffix == null && !TextUtils.isEmpty(this.mLanguageSuffix)) {
            strAddFileNameSuffix = Utils.addFileNameSuffix(str, this.mLanguageSuffix);
            str2 = resourceExists(strAddFileNameSuffix) ? strAddFileNameSuffix : null;
        }
        return str2 != null ? str2 : str;
    }

    public void init() {
    }

    public abstract boolean resourceExists(String str);

    public ResourceLoader setLocal(Locale locale) {
        if (locale != null) {
            this.mLanguageSuffix = locale.getLanguage();
            String string = locale.toString();
            this.mLanguageCountrySuffix = string;
            if (TextUtils.equals(this.mLanguageSuffix, string)) {
                this.mLanguageSuffix = null;
            }
        }
        this.mLocale = locale;
        return this;
    }
}

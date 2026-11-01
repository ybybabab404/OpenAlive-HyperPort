package com.miui.bugreport.logprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.bf;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DumpLogProvider extends ContentProvider {
    public static final File a = new File("/");
    public static volatile HashMap b = new HashMap();

    public static File a(File file, String... strArr) {
        for (String str : strArr) {
            if (str != null) {
                file = new File(file, str);
            }
        }
        return file;
    }

    public static HashMap a(Context context, String str) {
        HashMap map = new HashMap();
        try {
            XmlResourceParser xmlResourceParserLoadXmlMetaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).loadXmlMetaData(context.getPackageManager(), str);
            if (xmlResourceParserLoadXmlMetaData != null) {
                while (true) {
                    int next = xmlResourceParserLoadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2) {
                        String name = xmlResourceParserLoadXmlMetaData.getName();
                        File cacheDir = null;
                        String attributeValue = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, bf.a);
                        String attributeValue2 = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, "path");
                        if ("root-path".equals(name)) {
                            cacheDir = a;
                        } else if ("files-path".equals(name)) {
                            cacheDir = context.getFilesDir();
                        } else if ("cache-path".equals(name)) {
                            cacheDir = context.getCacheDir();
                        } else if ("external-files-path".equals(name)) {
                            File[] externalFilesDirs = context.getExternalFilesDirs(null);
                            if (externalFilesDirs.length > 0) {
                                cacheDir = externalFilesDirs[0];
                            }
                        } else if ("external-cache-path".equals(name)) {
                            File[] externalCacheDirs = context.getExternalCacheDirs();
                            if (externalCacheDirs.length > 0) {
                                cacheDir = externalCacheDirs[0];
                            }
                        } else if ("external-media-path".equals(name)) {
                            File[] externalMediaDirs = context.getExternalMediaDirs();
                            if (externalMediaDirs.length > 0) {
                                cacheDir = externalMediaDirs[0];
                            }
                        }
                        if (cacheDir != null) {
                            a(attributeValue, a(cacheDir, attributeValue2));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void a(String str, File file) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            b.put(str, file.getCanonicalFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean a(Context context) {
        try {
            return (context.getPackageManager().getPackageInfo("com.miui.bugreport", 0).applicationInfo.flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        b.putAll(a(getContext(), "com.miui.bugreport.DEFAULT_LOG_DIR"));
        b.putAll(a(getContext(), "com.miui.bugreport.LOG_DIR"));
        return true;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        if (!a(getContext())) {
            return null;
        }
        String path = Uri.parse(Uri.decode(uri.toString())).getPath();
        if (TextUtils.isEmpty(path)) {
            throw new FileNotFoundException();
        }
        try {
            File file = new File(path);
            String canonicalPath = file.getCanonicalPath();
            Iterator it = b.entrySet().iterator();
            while (it.hasNext()) {
                if (canonicalPath.startsWith(((File) ((Map.Entry) it.next()).getValue()).getPath())) {
                    return ParcelFileDescriptor.open(file, 268435456);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (!a(getContext())) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator it = b.entrySet().iterator();
        while (it.hasNext()) {
            File file = (File) ((Map.Entry) it.next()).getValue();
            if (file.exists()) {
                linkedList.offer(file);
                while (linkedList.size() != 0) {
                    File file2 = (File) linkedList.poll();
                    if (file2.isFile()) {
                        arrayList.add(file2.getPath());
                    } else {
                        File[] fileArrListFiles = file2.listFiles();
                        if (fileArrListFiles != null) {
                            for (File file3 : fileArrListFiles) {
                                if (file3.isFile()) {
                                    arrayList.add(file3.getPath());
                                } else if (file3.isDirectory()) {
                                    linkedList.offer(file3);
                                }
                            }
                        }
                    }
                }
            }
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[0]);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("logDir", arrayList);
        matrixCursor.setExtras(bundle);
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}

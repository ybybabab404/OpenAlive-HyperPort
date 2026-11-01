package com.miui.aod.part;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.util.CategoryPrefs;
import com.miui.aod.util.MMKVUtils;
import com.miui.keyguard.editor.utils.FileUtil;
import com.xiaomi.onetrack.api.am;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes.dex */
public class PartProvider extends ContentProvider {
    public static Uri URI_PART = Uri.parse("content://aod.part/part");
    public static final UriMatcher mMatcher;

    public interface id {
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        mMatcher = uriMatcher;
        uriMatcher.addURI("aod.part", "part", 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        Log.d("PartProvider", "call start: " + str);
        MMKVUtils.initialize(getContext().getApplicationContext());
        Bitmap bitmap = null;
        if (str.equals("provideFile") && str2 != null) {
            File file = new File(str2);
            try {
                bitmap = (Bitmap) Glide.with(getContext()).asBitmap().load(file).submit().get();
            } catch (InterruptedException | ExecutionException unused) {
                Log.wtf("PartProvider", "create bitmap error");
            }
            bundle2.putBoolean("fileExist", file.exists());
            bundle2.putParcelable("bitmap", bitmap);
            bundle2.putSerializable("provideFile", file);
        } else if (str.equals("getString") && str2 != null) {
            bundle2.putString("getString", CategoryPrefs.getString(getContext(), str2, null));
        } else if (str.equals("putString") && bundle != null) {
            String string = bundle.getString("key");
            String string2 = bundle.getString(am.t, "");
            if (string != null) {
                CategoryPrefs.putString(getContext(), string, string2);
            }
        }
        Log.d("PartProvider", "call end: " + str);
        return bundle2;
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
        return true;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        Log.d("PartProvider", "openFile " + uri + " " + str);
        if (uri.getPath() != null && uri.getPath().startsWith("/themes")) {
            try {
                File file = new File(ThemeUtils.getRootDir(getContext()) + "/" + uri.getLastPathSegment());
                StringBuilder sb = new StringBuilder();
                sb.append(getContext().getCacheDir().getAbsolutePath());
                sb.append("/themes");
                File file2 = new File(sb.toString());
                if (file2.exists()) {
                    file2.delete();
                }
                if (file.exists()) {
                    FileUtil.zipFolder(file, file2);
                }
                if (file2.exists()) {
                    return ParcelFileDescriptor.open(file2, 268435456);
                }
            } catch (Exception e) {
                Log.i("PartProvider", "openFile error: " + e.getMessage());
            }
        } else if (uri.getPath() != null && uri.getPath().startsWith("/select_image/")) {
            File file3 = new File(uri.getPath().substring(14));
            if (file3.exists()) {
                return ParcelFileDescriptor.open(file3, 268435456);
            }
            throw new FileNotFoundException("File not found");
        }
        return super.openFile(uri, str);
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}

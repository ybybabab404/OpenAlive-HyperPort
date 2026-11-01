package com.miui.maml;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.maml.util.LargeIconsHelper;
import com.miui.maml.util.MamlLog;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class MamlProvider extends ContentProvider {
    private static final String METHOD_CHANGE_LARGE_ICON = "changeLargeIcon";

    @Override // android.content.ContentProvider
    @Nullable
    public Bundle call(@NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        String string;
        boolean zEquals = METHOD_CHANGE_LARGE_ICON.equals(str);
        ArrayList<String> stringArrayList = null;
        if (!zEquals) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        MamlLog.i("LargeIconsConfigManager", "Large icon config file changed");
        if (bundle != null) {
            string = bundle.getString("mode");
            stringArrayList = bundle.getStringArrayList("relativePackageList");
        } else {
            string = null;
        }
        LargeIconsHelper.clearCache(stringArrayList);
        LargeIconsHelper.getLargeIconConfigFile(string, true);
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }
}

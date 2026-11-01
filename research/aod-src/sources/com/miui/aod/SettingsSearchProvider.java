package com.miui.aod;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.miui.aod.gemini.AodDisplayModeScreen;
import com.miui.aod.settings.AodShowModeSettingActivity;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SettingsSearchProvider extends ContentProvider {
    public static final String[] SEARCH_RESULT_COLUMNS = {"title", "summaryOn", "summaryOff", "keywords", "iconResId", "intentAction", "intentTargetPackage", "intentTargetClass", "uriString", "extras", "other"};

    public static class RawData {
        public String intentAction;
        public String intentTargetClass;
        public String intentTargetPackage;
        public String title;

        public RawData(String str, String str2, String str3, String str4) {
            this.title = str;
            this.intentAction = str2;
            this.intentTargetPackage = str3;
            this.intentTargetClass = str4;
        }
    }

    public static final class SearchResultColumn {
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
        return false;
    }

    public List prepareData() {
        ArrayList arrayList = new ArrayList();
        if (Utils.isAodEnable(AODApplication.sInstance)) {
            arrayList.add(new RawData(getContext().getString(R.string.disable_aod_in_power_save_mode), AodDisplayModeScreen.ACTION_SETTING, getContext().getPackageName(), AodShowModeSettingActivity.class.getName()));
        }
        return arrayList;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        MatrixCursor matrixCursor = new MatrixCursor(SEARCH_RESULT_COLUMNS);
        for (RawData rawData : prepareData()) {
            matrixCursor.newRow().add("title", rawData.title).add("intentAction", rawData.intentAction).add("intentTargetPackage", rawData.intentTargetPackage).add("intentTargetClass", rawData.intentTargetClass);
        }
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}

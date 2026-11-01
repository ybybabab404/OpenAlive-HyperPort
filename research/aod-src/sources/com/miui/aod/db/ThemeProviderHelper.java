package com.miui.aod.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ThemeProviderHelper {
    public static final Uri URI_THEME = Uri.parse("content://com.miui.aod.StyleDataProvider/theme");
    public static volatile ThemeProviderHelper sInstance;
    public final Context mContext;
    public final ContentResolver mResolver;

    public ThemeProviderHelper(Context context) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
    }

    public static ThemeProviderHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ThemeProviderHelper.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ThemeProviderHelper(context.getApplicationContext());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public void clearTheme() {
        this.mResolver.delete(URI_THEME, null, null);
    }

    public void delete(ThemeEntity themeEntity) {
        if (themeEntity == null) {
            return;
        }
        this.mResolver.delete(URI_THEME, "id", new String[]{themeEntity.getName()});
    }

    public List getAllThemes() {
        Cursor cursorQuery = this.mResolver.query(URI_THEME, null, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    ArrayList arrayList = new ArrayList();
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(ThemeDbController.getInstance(this.mContext).parseThemeEntity(cursorQuery));
                    }
                    cursorQuery.close();
                    return arrayList;
                }
            } catch (Throwable th) {
                if (cursorQuery == null) {
                    throw th;
                }
                try {
                    cursorQuery.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        }
        List list = Collections.EMPTY_LIST;
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return list;
    }

    public ThemeEntity getThemeByIdentity(String str) {
        Cursor cursorQuery = this.mResolver.query(URI_THEME, null, null, new String[]{str}, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    cursorQuery.moveToNext();
                    ThemeEntity themeEntity = ThemeDbController.getInstance(this.mContext).parseThemeEntity(cursorQuery);
                    cursorQuery.close();
                    return themeEntity;
                }
            } catch (Throwable th) {
                try {
                    cursorQuery.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    public void insert(ThemeEntity themeEntity) {
        if (themeEntity == null) {
            return;
        }
        this.mResolver.insert(URI_THEME, themeEntity.translateToContentValues());
    }
}

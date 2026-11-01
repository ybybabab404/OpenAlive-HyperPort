package com.miui.aod.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.common.StyleInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HistoryProviderHelper {
    public static Uri URI_HISTORY = Uri.parse("content://com.miui.aod.StyleDataProvider/history");
    public static volatile HistoryProviderHelper sInstance;
    public final Context mContext;
    public final ContentResolver mResolver;

    public HistoryProviderHelper(Context context) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
    }

    public static HistoryProviderHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (HistoryProviderHelper.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new HistoryProviderHelper(context.getApplicationContext());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public void clearHistory() {
        this.mResolver.delete(URI_HISTORY, null, null);
    }

    public void clearSuperWallpaperHistory() {
        this.mResolver.delete(URI_HISTORY, "cateName", new String[]{CategoryConstants.CATEGORY_SUPER_WALLPAPER});
    }

    public void delete(List list) {
        delete(list, true);
    }

    public final void delete(List list, boolean z) {
        if (list == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            HistoryEntity historyEntity = (HistoryEntity) it.next();
            StyleInfo styleInfoCreateStyleInfo = CategoryFactory.createStyleInfo(this.mContext, historyEntity.getCateName(), historyEntity.getJsonParameter());
            if (styleInfoCreateStyleInfo != null) {
                styleInfoCreateStyleInfo.onDeleteHistoryRecord(this.mContext);
                if (z && styleInfoCreateStyleInfo.getThemeBean() != null) {
                    arrayList.add(styleInfoCreateStyleInfo.getThemeBean().mIdentity);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = (String) arrayList.get(i);
            }
            this.mResolver.delete(ThemeProviderHelper.URI_THEME, "id", strArr);
        }
        String[] strArrCreateHistoryIdArray = StyleDataProvider.createHistoryIdArray(list);
        if (strArrCreateHistoryIdArray == null || strArrCreateHistoryIdArray.length <= 0) {
            return;
        }
        this.mResolver.delete(URI_HISTORY, "id", strArrCreateHistoryIdArray);
    }

    public final void deleteOldHistory(List list) {
        delete(list, false);
    }

    public List getAllHistories() {
        Cursor cursorQuery = this.mResolver.query(URI_HISTORY, null, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    ArrayList arrayList = new ArrayList();
                    HistoryDbController historyDbController = HistoryDbController.getInstance(this.mContext);
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(historyDbController.parseHistoryEntity(cursorQuery));
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

    public List getAllHistoriesWithCateName(String str) {
        Cursor cursorQuery = this.mResolver.query(URI_HISTORY, null, "cateName", new String[]{str}, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    ArrayList arrayList = new ArrayList();
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(HistoryDbController.getInstance(this.mContext).parseHistoryEntity(cursorQuery));
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

    public int getHistoryCount() {
        Cursor cursorQuery = this.mResolver.query(URI_HISTORY, null, null, null, null);
        if (cursorQuery == null) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return 0;
        }
        try {
            int count = cursorQuery.getCount();
            cursorQuery.close();
            return count;
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

    public List getPreHistories(int i) {
        Cursor cursorQuery = this.mResolver.query(URI_HISTORY, null, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    try {
                        ArrayList arrayList = new ArrayList();
                        while (cursorQuery.moveToNext()) {
                            arrayList.add(HistoryEntity.translateFromCursor(cursorQuery));
                            if (arrayList.size() >= i) {
                                break;
                            }
                        }
                        cursorQuery.close();
                        return arrayList;
                    } catch (Exception unused) {
                        List list = Collections.EMPTY_LIST;
                        cursorQuery.close();
                        return list;
                    }
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
        List list2 = Collections.EMPTY_LIST;
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return list2;
    }

    public void insert(HistoryEntity historyEntity) {
        if (historyEntity == null) {
            return;
        }
        this.mResolver.insert(URI_HISTORY, historyEntity.translateToContentValues());
    }

    public void insert(String str, String str2) {
        List<HistoryEntity> allHistoriesWithCateName = getAllHistoriesWithCateName(str);
        StyleInfo styleInfoCreateStyleInfo = CategoryFactory.createStyleInfo(this.mContext, str, str2);
        ArrayList arrayList = new ArrayList();
        for (HistoryEntity historyEntity : allHistoriesWithCateName) {
            if (CategoryFactory.createStyleInfo(this.mContext, historyEntity.getCateName(), historyEntity.getJsonParameter()).isSameHistoryRecord(styleInfoCreateStyleInfo)) {
                arrayList.add(historyEntity);
            }
        }
        deleteOldHistory(arrayList);
        insert(new HistoryEntity(null, Long.valueOf(System.currentTimeMillis()), str, str2));
        try {
            Cursor cursorQuery = this.mResolver.query(URI_HISTORY, null, null, null, "asc");
            if (cursorQuery != null) {
                try {
                    int count = cursorQuery.getCount() - 50;
                    arrayList.clear();
                    if (count > 0) {
                        int i = 0;
                        while (cursorQuery.moveToNext()) {
                            arrayList.add(HistoryDbController.getInstance(this.mContext).parseHistoryEntity(cursorQuery));
                            i++;
                            if (i >= count) {
                                break;
                            }
                        }
                        delete(arrayList);
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
        } catch (Exception e) {
            Log.e("HistoryProviderHelper", "error...", e);
        }
    }
}

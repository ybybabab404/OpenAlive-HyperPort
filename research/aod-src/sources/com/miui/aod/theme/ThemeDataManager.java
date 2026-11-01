package com.miui.aod.theme;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.CategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.db.HistoryEntity;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.db.ThemeEntity;
import com.miui.aod.theme.utils.ThemeUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class ThemeDataManager {
    public static volatile ThemeDataManager sInstance;
    public final Context mContext;
    public Scheduler mScheduler;
    public SoftReference mThemeLoadListener;
    public boolean mIsLoadingThemeStyle = false;
    public final Gson mGson = new Gson();

    public interface ThemeLoadListener {
        default void onComplete(StyleInfo styleInfo) {
        }

        default void onError(Throwable th) {
        }
    }

    public static /* synthetic */ void $r8$lambda$mBfbuG79lp_mWeEhvJyt7JbFCyo(final ThemeDataManager themeDataManager, String str, String str2, String str3, List list, boolean z, boolean z2, ObservableEmitter observableEmitter) {
        ThemeEntity themeEntity;
        final StyleInfo styleInfoAddNewTheme;
        CategoryInfo categoryInfo;
        themeDataManager.getClass();
        try {
            try {
                styleInfoAddNewTheme = ThemeUtils.addNewTheme(themeDataManager.mContext, str, str2, str3, list, z);
                themeEntity = null;
            } catch (ThemeExistException e) {
                themeEntity = e.mThemeEntity;
                styleInfoAddNewTheme = null;
            }
            if (styleInfoAddNewTheme != null) {
                final String json = themeDataManager.mGson.toJson(styleInfoAddNewTheme);
                if (!z && (styleInfoAddNewTheme.getThemeBean() == null || styleInfoAddNewTheme.getThemeBean().mThemeType == null || !styleInfoAddNewTheme.getThemeBean().mThemeType.equals(CategoryConstants.CATEGORY_MAML_STYLE) || (json.contains(CategoryConstants.CATEGORY_MAML_THUMBNAIL) && json.contains(CategoryConstants.CATEGORY_MAML_SMALL)))) {
                    Utils.setUsingSuperWallpaperStyleState(themeDataManager.mContext, z2);
                    Utils.setAodUsingStyleState(themeDataManager.mContext, 2);
                    Utils.setAodUsingCategory(themeDataManager.mContext, styleInfoAddNewTheme.getName(), json);
                    BackgroundThread.post(new Runnable() { // from class: com.miui.aod.theme.ThemeDataManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            HistoryProviderHelper.getInstance(this.f$0.mContext).insert(styleInfoAddNewTheme.getName(), json);
                        }
                    });
                }
                observableEmitter.onNext(styleInfoAddNewTheme);
            } else if (themeEntity == null) {
                observableEmitter.onError(new Exception("styleInfo null"));
            } else {
                Log.w("ThemeDataManager", "loadTheme: existTheme " + themeEntity.getJsonParameter());
                String cateNameByThemeType = ThemeUtils.getCateNameByThemeType(themeEntity.getTypeName());
                Iterator it = HistoryProviderHelper.getInstance(themeDataManager.mContext).getAllHistoriesWithCateName(cateNameByThemeType).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        categoryInfo = null;
                        break;
                    }
                    HistoryEntity historyEntity = (HistoryEntity) it.next();
                    String cateName = historyEntity.getCateName();
                    String jsonParameter = historyEntity.getJsonParameter();
                    Map<String, Class<?>> map = CategoryFactory.sCategoryMap;
                    if (!map.containsKey(cateNameByThemeType)) {
                        cateNameByThemeType = CategoryConstants.getDefaultCategoryName(themeDataManager.mContext);
                        jsonParameter = CategoryConstants.DEFAULT_PARAM;
                    }
                    categoryInfo = (CategoryInfo) themeDataManager.mGson.fromJson(jsonParameter, (Class) map.get(cateNameByThemeType));
                    if (categoryInfo.getThemeBean() != null && themeEntity.getName().equals(categoryInfo.getThemeBean().mIdentity)) {
                        if (categoryInfo.getThemeBean().mThemeType.equals(CategoryConstants.CATEGORY_MAML_STYLE) && (!jsonParameter.contains(CategoryConstants.CATEGORY_MAML_THUMBNAIL) || !jsonParameter.contains(CategoryConstants.CATEGORY_MAML_SMALL))) {
                            break;
                            break;
                        }
                        Utils.setUsingSuperWallpaperStyleState(themeDataManager.mContext, z2);
                        Utils.setAodUsingStyleState(themeDataManager.mContext, 2);
                        Utils.setAodUsingCategory(themeDataManager.mContext, cateName, jsonParameter);
                        break;
                    }
                }
                if (categoryInfo != null) {
                    Log.w("ThemeDataManager", "loadTheme: exist theme int history");
                    observableEmitter.onNext(styleInfoAddNewTheme);
                } else {
                    observableEmitter.onError(new Exception("theme exist but cannot find styleInfo in history"));
                }
            }
            observableEmitter.onComplete();
        } catch (Exception e2) {
            themeDataManager.mIsLoadingThemeStyle = false;
            Log.e("ThemeDataManager", "loadTheme exception: " + e2.getMessage());
            observableEmitter.onError(e2);
            Log.e("ThemeDataManager", "error...", e2);
        }
    }

    public ThemeDataManager(Context context) {
        this.mContext = context;
    }

    public static ThemeDataManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ThemeDataManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ThemeDataManager(context.getApplicationContext());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public boolean isLoadingTheme() {
        return this.mIsLoadingThemeStyle;
    }

    public final void loadTheme(final String str, final String str2, final String str3, final List list, final boolean z, final boolean z2) {
        Log.i("ThemeDataManager", "loadTheme: path " + str + " identity " + str2);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.mIsLoadingThemeStyle = true;
        if (this.mScheduler == null) {
            this.mScheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        }
        Log.i("ThemeDataManager", "executeLoadTheme: " + str);
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.theme.ThemeDataManager$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ThemeDataManager.$r8$lambda$mBfbuG79lp_mWeEhvJyt7JbFCyo(this.f$0, str, str2, str3, list, z, z2, observableEmitter);
            }
        }).subscribeOn(this.mScheduler).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<StyleInfo>() { // from class: com.miui.aod.theme.ThemeDataManager.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                ThemeDataManager.this.mIsLoadingThemeStyle = false;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                if (ThemeDataManager.this.mThemeLoadListener != null && ThemeDataManager.this.mThemeLoadListener.get() != null) {
                    ((ThemeLoadListener) ThemeDataManager.this.mThemeLoadListener.get()).onError(th);
                }
                ThemeDataManager.this.mIsLoadingThemeStyle = false;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(StyleInfo styleInfo) {
                if (ThemeDataManager.this.mThemeLoadListener != null && ThemeDataManager.this.mThemeLoadListener.get() != null) {
                    ((ThemeLoadListener) ThemeDataManager.this.mThemeLoadListener.get()).onComplete(styleInfo);
                }
                ThemeDataManager.this.mIsLoadingThemeStyle = false;
            }
        });
    }

    public void setThemeLoadListener(ThemeLoadListener themeLoadListener) {
        if (themeLoadListener != null) {
            this.mThemeLoadListener = new SoftReference(themeLoadListener);
        } else {
            this.mThemeLoadListener = null;
        }
    }
}

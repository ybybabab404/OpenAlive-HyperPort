package com.miui.aod.stylelist;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.reflect.TypeToken;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.aodannotation.JsonStringBinder;
import com.miui.aod.category.AnimateClockPanelCategoryInfo;
import com.miui.aod.category.CategoriesItemInfo;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.history.HistorySubViewModel;
import com.miui.aod.theme.OnLineAodListType;
import com.miui.aod.theme.OnlineAodViewModel;
import com.miui.aod.theme.beans.OnLineAodBean;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.maml.R;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import miui.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class StyleListViewModel implements DefaultLifecycleObserver, Observer<HistorySubViewModel.HistorySubData> {
    public String mCateListJson;
    public List mCategoriesInfoList;
    public FsAodViewModel mFsAodLockViewModel;
    public boolean mHasObserved;
    public HistorySubViewModel.HistorySubData mHistorySubData;
    public final HistorySubViewModel mHistoryViewModel;
    public String mInternationalCateListJson;
    public OnlineAodViewModel mOnlineAodViewModel;
    public PreviewType mPreviewType;
    public final MutableLiveData mStyleListData;
    public MyStyleListType myStyleListType;
    public final SettingsType mSettingsType = new SettingsType();
    public final Observer mPreviewObserver = new Observer() { // from class: com.miui.aod.stylelist.StyleListViewModel$$ExternalSyntheticLambda0
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            StyleListViewModel.m501$r8$lambda$_NPqqCEWZKyJEqVNBLcIIK5Gw(this.f$0, (Boolean) obj);
        }
    };
    public final List mOnlineList = new ArrayList();
    public final Observer mOnLineObserver = new Observer() { // from class: com.miui.aod.stylelist.StyleListViewModel$$ExternalSyntheticLambda1
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            StyleListViewModel.m502$r8$lambda$jaw5fOy61TDWUbNCxYbrGTnoY(this.f$0, (List) obj);
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.miui.aod.stylelist.StyleListViewModel.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 713) {
                return;
            }
            StyleListViewModel.this.loadStyleListData();
        }
    };

    public static class ButtonType implements StyleListType {
        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public String getListType() {
            return ButtonType.class.getSimpleName();
        }

        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public int getViewType() {
            return R.styleable.AppCompatTheme_windowMinWidthMajor;
        }
    }

    public static class PreviewType implements StyleListType {
        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public String getListType() {
            return PreviewType.class.getSimpleName();
        }

        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public int getViewType() {
            return R.styleable.AppCompatTheme_windowNoTitle;
        }
    }

    public static class SettingsType implements StyleListType {
        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public String getListType() {
            return "SettingsType";
        }

        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public int getViewType() {
            return R.styleable.AppCompatTheme_windowMinWidthMinor;
        }
    }

    public interface StyleListType {
        default RecyclerView.Adapter createAdapter(Context context, LifecycleOwner lifecycleOwner) {
            return null;
        }

        default String getKey() {
            return getListType();
        }

        String getListType();

        default String getValueRight() {
            return "";
        }

        int getViewType();

        default boolean isClickEnable() {
            return false;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$_NPqqC-EWZKyJEqVNBLcII-K5Gw, reason: not valid java name */
    public static /* synthetic */ void m501$r8$lambda$_NPqqCEWZKyJEqVNBLcIIK5Gw(StyleListViewModel styleListViewModel, Boolean bool) {
        if (bool != null) {
            styleListViewModel.getClass();
            styleListViewModel.mPreviewType = new PreviewType();
        }
        styleListViewModel.loadStyleListData();
    }

    /* JADX INFO: renamed from: $r8$lambda$jaw5fO-y61TDWUbNCxYbrG-TnoY, reason: not valid java name */
    public static /* synthetic */ void m502$r8$lambda$jaw5fOy61TDWUbNCxYbrGTnoY(StyleListViewModel styleListViewModel, List list) {
        styleListViewModel.getClass();
        Log.i("StyleListViewModel", "onLineAodBeans.size:" + list.size());
        styleListViewModel.mOnlineList.clear();
        styleListViewModel.mOnlineList.addAll(list);
        styleListViewModel.scheduleLoadData();
    }

    public static /* synthetic */ void $r8$lambda$ok_sPrXPr7oUPHT1HiQvQ1DgR68(StyleListViewModel styleListViewModel, ObservableEmitter observableEmitter) {
        styleListViewModel.getClass();
        ArrayList arrayList = new ArrayList();
        if (styleListViewModel.myStyleListType == null) {
            styleListViewModel.myStyleListType = new MyStyleListType();
        }
        arrayList.add(styleListViewModel.mSettingsType);
        if (Utils.isFsAodSameToLock(AODApplication.aodSettingDensityContext)) {
            PreviewType previewType = styleListViewModel.mPreviewType;
            if (previewType != null) {
                arrayList.add(previewType);
            }
        } else {
            styleListViewModel.loadNormalData(arrayList);
        }
        observableEmitter.onNext(arrayList);
        observableEmitter.onComplete();
    }

    public StyleListViewModel(Context context) {
        JsonStringBinder.bind(this);
        this.mStyleListData = new MutableLiveData();
        this.mHistoryViewModel = HistorySubViewModel.newInstance(context, 12);
        if (ThemeUtils.supportAodTheme(context)) {
            this.mOnlineAodViewModel = OnlineAodViewModel.newInstance();
        }
        if (Utils.isSupportFsAod()) {
            this.mFsAodLockViewModel = new FsAodViewModel(context);
        }
        this.mHasObserved = false;
    }

    public static StyleListViewModel newInstance(Context context) {
        return new StyleListViewModel(context);
    }

    public final void loadNormalData(List list) {
        List<StyleInfo> styleInfos;
        this.myStyleListType.updateData(this.mHistorySubData);
        list.add(this.myStyleListType);
        if (this.mCategoriesInfoList == null) {
            List<CategoriesItemInfo> list2 = (List) CategoryFactory.getCategoriesGson().fromJson(Build.IS_INTERNATIONAL_BUILD ? this.mInternationalCateListJson : this.mCateListJson, new TypeToken<List<CategoriesItemInfo>>() { // from class: com.miui.aod.stylelist.StyleListViewModel.2
            }.getType());
            boolean zIsSupportNoBlanking = AODSettings.isSupportNoBlanking();
            for (CategoriesItemInfo categoriesItemInfo : list2) {
                if (!zIsSupportNoBlanking && "panel_clock".equals(categoriesItemInfo.getCateroryName()) && (styleInfos = categoriesItemInfo.getStyleInfos()) != null) {
                    ListIterator<StyleInfo> listIterator = styleInfos.listIterator();
                    while (listIterator.hasNext()) {
                        StyleInfo next = listIterator.next();
                        if ((next instanceof AnimateClockPanelCategoryInfo) && "clock_panel_forth".equals(((AnimateClockPanelCategoryInfo) next).getPanel())) {
                            listIterator.remove();
                            break;
                        }
                    }
                }
            }
            this.mCategoriesInfoList = list2;
        }
        list.addAll(this.mCategoriesInfoList);
        Iterator it = this.mOnlineList.iterator();
        while (it.hasNext()) {
            list.add(OnLineAodListType.createStyleListType((OnLineAodBean) it.next()));
        }
    }

    public final void loadStyleListData() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.stylelist.StyleListViewModel$$ExternalSyntheticLambda2
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                StyleListViewModel.$r8$lambda$ok_sPrXPr7oUPHT1HiQvQ1DgR68(this.f$0, observableEmitter);
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<List<StyleListType>>() { // from class: com.miui.aod.stylelist.StyleListViewModel.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i("StyleListViewModel", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(List list) {
                if (StyleListViewModel.this.mStyleListData != null) {
                    StyleListViewModel.this.mStyleListData.setValue(list);
                }
            }
        });
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer observer) {
        if (this.mHasObserved) {
            return;
        }
        this.mHasObserved = true;
        lifecycleOwner.getLifecycle().addObserver(this);
        this.mHistoryViewModel.observe(lifecycleOwner, this);
        OnlineAodViewModel onlineAodViewModel = this.mOnlineAodViewModel;
        if (onlineAodViewModel != null) {
            onlineAodViewModel.observe(lifecycleOwner, null, this.mOnLineObserver);
        }
        this.mStyleListData.observe(lifecycleOwner, observer);
        FsAodViewModel fsAodViewModel = this.mFsAodLockViewModel;
        if (fsAodViewModel != null) {
            fsAodViewModel.observe(lifecycleOwner, this.mPreviewObserver);
        }
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(HistorySubViewModel.HistorySubData historySubData) {
        this.mHistorySubData = historySubData;
        scheduleLoadData();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        this.mStyleListData.removeObservers(lifecycleOwner);
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    public void scheduleLoadData() {
        this.mHandler.removeMessages(713);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(713), 100L);
    }
}

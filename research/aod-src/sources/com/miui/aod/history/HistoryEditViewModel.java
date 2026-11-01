package com.miui.aod.history;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.google.gson.Gson;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.CategoryInfo;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.db.HistoryEntity;
import com.miui.aod.db.HistoryProviderHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class HistoryEditViewModel implements Observer<List<HistoryEntity>>, DefaultLifecycleObserver {
    public final Context mContext;
    public final List mDataList = new ArrayList();
    public final HistoryViewModel mHistoryViewModel;
    public Collection mObservers;

    public static class DeleteEvent {
    }

    public static class HistoryEditData {
        public CategoryInfo mCategoryInfo;
        public final HistoryEntity mEntity;
        public boolean mSelected = false;
        public boolean mCanBeDelete = false;

        public HistoryEditData(HistoryEntity historyEntity) {
            this.mEntity = historyEntity;
        }

        public boolean canBeDelete() {
            return this.mCanBeDelete;
        }

        public boolean canBeShare() {
            return this.mCategoryInfo instanceof ImageSelectorCategoryInfo;
        }

        public HistoryEditData copy() {
            HistoryEditData historyEditData = new HistoryEditData(this.mEntity);
            historyEditData.mCategoryInfo = this.mCategoryInfo;
            historyEditData.mSelected = this.mSelected;
            historyEditData.mCanBeDelete = this.mCanBeDelete;
            return historyEditData;
        }

        public final void createCategoryInfo(Context context) {
            if (this.mCategoryInfo == null) {
                String cateName = this.mEntity.getCateName();
                String jsonParameter = this.mEntity.getJsonParameter();
                Map<String, Class<?>> map = CategoryFactory.sCategoryMap;
                if (!map.containsKey(cateName)) {
                    cateName = CategoryConstants.getDefaultCategoryName(context);
                    jsonParameter = CategoryConstants.DEFAULT_PARAM;
                }
                this.mCategoryInfo = (CategoryInfo) new Gson().fromJson(jsonParameter, (Class) map.get(cateName));
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof HistoryEditData)) {
                return false;
            }
            HistoryEditData historyEditData = (HistoryEditData) obj;
            if (isSelected() == historyEditData.isSelected() && canBeDelete() == historyEditData.canBeDelete()) {
                return this.mEntity.getId().equals(historyEditData.mEntity.getId());
            }
            return false;
        }

        public CategoryInfo getCategoryInfo() {
            return this.mCategoryInfo;
        }

        public boolean isSelected() {
            return this.mSelected;
        }

        public final void setCanBeDelete(boolean z) {
            this.mCanBeDelete = z;
        }

        public final void setSelected(boolean z) {
            this.mSelected = z;
        }
    }

    public static class NoDataEvent {
    }

    public static class SelectEvent {
        public final boolean mChecked;
        public final int mPosition;

        public SelectEvent(int i, boolean z) {
            this.mPosition = i;
            this.mChecked = z;
        }
    }

    public static class SwitchModeEvent {
        public final boolean mEnterEditMode;

        public SwitchModeEvent(boolean z) {
            this.mEnterEditMode = z;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$w436eVrLT1W-Kvywkgtq3jGRB7c, reason: not valid java name */
    public static /* synthetic */ void m401$r8$lambda$w436eVrLT1WKvywkgtq3jGRB7c(HistoryEditViewModel historyEditViewModel, List list, ObservableEmitter observableEmitter) {
        String aodUsingCategoryName = Utils.getAodUsingCategoryName(historyEditViewModel.mContext);
        if (TextUtils.isEmpty(aodUsingCategoryName) || TextUtils.equals(aodUsingCategoryName, CategoryConstants.CATEGORY_DUAL_CLOCK)) {
            aodUsingCategoryName = CategoryConstants.CATEGORY_ANIMATE_CLOCK_PANEL;
        }
        String aodUsingCategoryParam = Utils.getAodUsingCategoryParam(historyEditViewModel.mContext, aodUsingCategoryName);
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                HistoryEditData historyEditData = new HistoryEditData((HistoryEntity) it.next());
                historyEditData.createCategoryInfo(historyEditViewModel.mContext);
                historyEditData.setCanBeDelete(!(aodUsingCategoryName.equals(historyEditData.mEntity.getCateName()) && aodUsingCategoryParam.equals(historyEditData.mEntity.getJsonParameter())));
                arrayList.add(historyEditData);
            }
        }
        observableEmitter.onNext(arrayList);
        observableEmitter.onComplete();
    }

    public HistoryEditViewModel(Context context) {
        this.mContext = context;
        this.mHistoryViewModel = HistoryViewModel.newInstance(context);
    }

    public static HistoryEditViewModel newInstance(Context context) {
        return new HistoryEditViewModel(context);
    }

    public void delete() {
        ArrayList arrayList = new ArrayList();
        for (HistoryEditData historyEditData : this.mDataList) {
            if (historyEditData.isSelected() && historyEditData.canBeDelete()) {
                arrayList.add(historyEditData.mEntity);
            }
        }
        HistoryProviderHelper.getInstance(this.mContext).delete(arrayList);
    }

    public final void notifyChange() {
        Collection collection = this.mObservers;
        if (collection != null) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                ((Observer) it.next()).onChanged(this.mDataList);
            }
        }
        if (this.mDataList.size() == 0) {
            EventBus.getDefault().post(new NoDataEvent());
        }
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer observer) {
        lifecycleOwner.getLifecycle().addObserver(this);
        if (this.mObservers == null) {
            this.mObservers = new HashSet();
        }
        if (!this.mObservers.contains(observer)) {
            this.mObservers.add(observer);
        }
        this.mHistoryViewModel.observe(lifecycleOwner, this);
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(final List list) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.history.HistoryEditViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HistoryEditViewModel.m401$r8$lambda$w436eVrLT1WKvywkgtq3jGRB7c(this.f$0, list, observableEmitter);
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<List<HistoryEditData>>() { // from class: com.miui.aod.history.HistoryEditViewModel.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i("HistoryEditViewModel", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(List list2) {
                HistoryEditViewModel.this.mDataList.clear();
                HistoryEditViewModel.this.mDataList.addAll(list2);
                HistoryEditViewModel.this.notifyChange();
            }
        });
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        Collection collection = this.mObservers;
        if (collection != null) {
            collection.clear();
        }
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    public void selectAll(boolean z) {
        Iterator it = this.mDataList.iterator();
        while (it.hasNext()) {
            ((HistoryEditData) it.next()).setSelected(z);
        }
        notifyChange();
    }

    public void selectData(int i, boolean z) {
        if (i < 0) {
            selectAll(z);
            notifyChange();
        } else {
            if (i >= this.mDataList.size()) {
                return;
            }
            ((HistoryEditData) this.mDataList.get(i)).setSelected(z);
            notifyChange();
        }
    }

    public void switchSelectState(int i) {
        if (i < 0 || i >= this.mDataList.size()) {
            return;
        }
        HistoryEditData historyEditData = (HistoryEditData) this.mDataList.get(i);
        historyEditData.setSelected(!historyEditData.mSelected);
        notifyChange();
    }
}

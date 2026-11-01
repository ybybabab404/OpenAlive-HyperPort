package com.miui.aod.history;

import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.db.HistoryProviderHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HistorySubViewModel extends ContentObserver implements DefaultLifecycleObserver {
    public final Context mContext;
    public final MutableLiveData mHistoryData;
    public HistoryProviderHelper mHistoryProviderHelper;
    public final int mSubCount;

    public static class HistorySubData {
        public final int mCount;
        public final List mList;

        public HistorySubData(int i, List list) {
            this.mCount = i;
            this.mList = list;
        }
    }

    public static /* synthetic */ void $r8$lambda$32tSTeyDKxD9v11VnGFlr83Am2M(HistorySubViewModel historySubViewModel, ObservableEmitter observableEmitter) {
        if (historySubViewModel.mHistoryProviderHelper == null) {
            historySubViewModel.mHistoryProviderHelper = HistoryProviderHelper.getInstance(historySubViewModel.mContext);
        }
        observableEmitter.onNext(new HistorySubData(historySubViewModel.mHistoryProviderHelper.getHistoryCount(), historySubViewModel.mHistoryProviderHelper.getPreHistories(historySubViewModel.mSubCount)));
        observableEmitter.onComplete();
    }

    public HistorySubViewModel(Context context, int i) {
        super(BackgroundThread.getHandler());
        this.mSubCount = i <= 0 ? 1 : i;
        this.mContext = context;
        this.mHistoryData = new MutableLiveData();
    }

    public static HistorySubViewModel newInstance(Context context, int i) {
        return new HistorySubViewModel(context, i);
    }

    public final void loadSubHistoryData() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.history.HistorySubViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HistorySubViewModel.$r8$lambda$32tSTeyDKxD9v11VnGFlr83Am2M(this.f$0, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<HistorySubData>() { // from class: com.miui.aod.history.HistorySubViewModel.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i("HistorySubViewModel", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(HistorySubData historySubData) {
                if (HistorySubViewModel.this.mHistoryData != null) {
                    HistorySubViewModel.this.mHistoryData.setValue(historySubData);
                }
            }
        });
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer observer) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.mHistoryData.observe(lifecycleOwner, observer);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        loadSubHistoryData();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onCreate(LifecycleOwner lifecycleOwner) {
        this.mContext.getContentResolver().registerContentObserver(HistoryProviderHelper.URI_HISTORY, false, this);
        loadSubHistoryData();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        this.mContext.getContentResolver().unregisterContentObserver(this);
        this.mHistoryData.removeObservers(lifecycleOwner);
        lifecycleOwner.getLifecycle().removeObserver(this);
    }
}

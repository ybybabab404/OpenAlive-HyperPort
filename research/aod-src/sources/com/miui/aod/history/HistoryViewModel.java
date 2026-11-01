package com.miui.aod.history;

import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.db.HistoryEntity;
import com.miui.aod.db.HistoryProviderHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HistoryViewModel extends ContentObserver implements DefaultLifecycleObserver {
    public final Context mContext;
    public final MutableLiveData mHistoryData;
    public HistoryProviderHelper mHistoryProviderHelper;

    public static /* synthetic */ void $r8$lambda$WT1ujP9rX0teEARu1w4ijEiuupg(HistoryViewModel historyViewModel, ObservableEmitter observableEmitter) {
        if (historyViewModel.mHistoryProviderHelper == null) {
            historyViewModel.mHistoryProviderHelper = HistoryProviderHelper.getInstance(historyViewModel.mContext);
        }
        observableEmitter.onNext(historyViewModel.mHistoryProviderHelper.getAllHistories());
        observableEmitter.onComplete();
    }

    public HistoryViewModel(Context context) {
        super(BackgroundThread.getHandler());
        this.mContext = context;
        this.mHistoryData = new MutableLiveData();
    }

    public static HistoryViewModel newInstance(Context context) {
        return new HistoryViewModel(context);
    }

    public final void loadHistoryData() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.history.HistoryViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HistoryViewModel.$r8$lambda$WT1ujP9rX0teEARu1w4ijEiuupg(this.f$0, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<List<HistoryEntity>>() { // from class: com.miui.aod.history.HistoryViewModel.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i("HistoryViewModel", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(List list) {
                if (HistoryViewModel.this.mHistoryData != null) {
                    HistoryViewModel.this.mHistoryData.setValue(list);
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
        loadHistoryData();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onCreate(LifecycleOwner lifecycleOwner) {
        this.mContext.getContentResolver().registerContentObserver(HistoryProviderHelper.URI_HISTORY, false, this);
        loadHistoryData();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        this.mContext.getContentResolver().unregisterContentObserver(this);
        this.mHistoryData.removeObservers(lifecycleOwner);
        lifecycleOwner.getLifecycle().removeObserver(this);
    }
}

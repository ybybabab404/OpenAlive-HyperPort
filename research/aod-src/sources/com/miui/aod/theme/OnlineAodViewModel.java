package com.miui.aod.theme;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.miui.aod.AODApplication;
import com.miui.aod.theme.beans.OnLineAodBean;
import com.miui.aod.theme.utils.ThemeUtils;
import com.xiaomi.onetrack.a.c;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class OnlineAodViewModel implements DefaultLifecycleObserver {
    public final Context mContext = AODApplication.sInstance;
    public final MutableLiveData mAodBeanLiveData = new MutableLiveData();
    public final MutableLiveData mResponseLiveData = new MutableLiveData();

    public static class OnLineAodResponse {
        public final boolean mAgreement;
        public final String mJson;

        public OnLineAodResponse(boolean z, String str) {
            this.mAgreement = z;
            this.mJson = str;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof OnLineAodResponse)) {
                return false;
            }
            OnLineAodResponse onLineAodResponse = (OnLineAodResponse) obj;
            if (this.mAgreement != onLineAodResponse.mAgreement) {
                return false;
            }
            String str = this.mJson;
            if (str != null || onLineAodResponse.mJson == null) {
                return str == null || str.equals(onLineAodResponse.mJson);
            }
            return false;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$DELZ2xdUQVf-TqAuwz1bD3bDCTY, reason: not valid java name */
    public static /* synthetic */ void m522$r8$lambda$DELZ2xdUQVfTqAuwz1bD3bDCTY(OnlineAodViewModel onlineAodViewModel, ObservableEmitter observableEmitter) {
        ContentResolver contentResolver = onlineAodViewModel.mContext.getContentResolver();
        Bundle cachedBundle = ThemeUtils.getCachedBundle();
        String string = null;
        if (cachedBundle == null) {
            try {
                Log.d("OnlineAodViewModel", "loadAodBean, cache bundle is null");
                cachedBundle = contentResolver.call(Uri.parse("content://com.android.thememanager.settings_onlinewallpaper_provider"), "getOnlineAODs", (String) null, new Bundle());
                ThemeUtils.setCachedBundle(cachedBundle);
            } catch (Exception e) {
                e = e;
                Log.e("OnlineAodViewModel", "error...", e);
            }
        }
        e = null;
        if (e == null) {
            boolean z = false;
            if (cachedBundle != null) {
                z = cachedBundle.getBoolean("UserAgreement_Theme", false);
                string = cachedBundle.getString("json_list", null);
            }
            observableEmitter.onNext(new OnLineAodResponse(z, string));
        } else {
            observableEmitter.onError(e);
        }
        observableEmitter.onComplete();
    }

    private OnlineAodViewModel() {
    }

    public static String getThemeImageUri(String str, String str2) {
        String string = null;
        try {
            Bundle bundle = new Bundle();
            bundle.putString("id", str);
            bundle.putString(c.a.g, str2);
            Bundle bundleCall = AODApplication.sInstance.getContentResolver().call(Uri.parse("content://com.android.thememanager.settings_onlinewallpaper_provider"), "getWallpaperUri", (String) null, bundle);
            if (bundleCall != null) {
                string = bundleCall.getString("result_uri");
            }
        } catch (Exception e) {
            Log.e("OnlineAodViewModel", "error...", e);
        }
        Log.v("OnlineAodViewModel", "getThemeImageUri: " + str + " " + str2 + " " + string);
        return string;
    }

    public static OnlineAodViewModel newInstance() {
        return new OnlineAodViewModel();
    }

    public final void loadAodBean() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.theme.OnlineAodViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                OnlineAodViewModel.m522$r8$lambda$DELZ2xdUQVfTqAuwz1bD3bDCTY(this.f$0, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<OnLineAodResponse>() { // from class: com.miui.aod.theme.OnlineAodViewModel.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.e("OnlineAodViewModel", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(OnLineAodResponse onLineAodResponse) {
                OnLineAodResponse onLineAodResponse2 = (OnLineAodResponse) OnlineAodViewModel.this.mResponseLiveData.getValue();
                if (onLineAodResponse2 == null || !onLineAodResponse2.equals(onLineAodResponse)) {
                    OnlineAodViewModel.this.mResponseLiveData.setValue(onLineAodResponse);
                    if (TextUtils.isEmpty(onLineAodResponse.mJson)) {
                        OnlineAodViewModel.this.mAodBeanLiveData.setValue(Collections.EMPTY_LIST);
                        return;
                    }
                    try {
                        OnlineAodViewModel.this.mAodBeanLiveData.setValue((List) new Gson().fromJson(onLineAodResponse.mJson, new TypeToken<List<OnLineAodBean>>() { // from class: com.miui.aod.theme.OnlineAodViewModel.1.1
                        }.getType()));
                    } catch (JsonSyntaxException e) {
                        Log.e("OnlineAodViewModel", "error...", e);
                        OnlineAodViewModel.this.mAodBeanLiveData.setValue(Collections.EMPTY_LIST);
                    }
                }
            }
        });
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer observer, Observer observer2) {
        lifecycleOwner.getLifecycle().addObserver(this);
        if (observer != null) {
            this.mResponseLiveData.observe(lifecycleOwner, observer);
        }
        if (observer2 != null) {
            this.mAodBeanLiveData.observe(lifecycleOwner, observer2);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        this.mResponseLiveData.removeObservers(lifecycleOwner);
        this.mAodBeanLiveData.removeObservers(lifecycleOwner);
        ThemeImageLoader.getInstance(this.mContext).clear();
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onResume(LifecycleOwner lifecycleOwner) {
        loadAodBean();
    }
}

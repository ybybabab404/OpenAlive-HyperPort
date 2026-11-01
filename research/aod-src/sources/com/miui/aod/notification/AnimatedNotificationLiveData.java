package com.miui.aod.notification;

import androidx.lifecycle.MutableLiveData;

/* JADX INFO: loaded from: classes.dex */
public class AnimatedNotificationLiveData<T> extends MutableLiveData<T> {
    public boolean animation = false;

    public boolean needAnimate() {
        return this.animation;
    }

    public void postValue(Object obj, boolean z) {
        this.animation = z;
        postValue(obj);
    }

    public void setValue(Object obj, boolean z) {
        this.animation = z;
        setValue(obj);
    }
}

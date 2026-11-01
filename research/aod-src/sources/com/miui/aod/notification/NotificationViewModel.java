package com.miui.aod.notification;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.miui.aod.AODApplication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationViewModel extends ViewModel implements Observer<List<NotificationController.NotificationData>> {
    public final List callbacks = new ArrayList();
    public final FocusNotificationLiveData focusNotificationLiveData;
    public final MediatorLiveData notification;
    public final AnimatedNotificationLiveData notificationLiveData;

    public interface FocusInterface {
    }

    public static class FocusNotification extends Notification implements FocusInterface {
    }

    public static class Notification extends ArrayList<NotificationController.NotificationData> {
    }

    public NotificationViewModel() {
        AnimatedNotificationLiveData animatedNotificationLiveData = new AnimatedNotificationLiveData();
        this.notificationLiveData = animatedNotificationLiveData;
        FocusNotificationLiveData focusNotificationLiveData = new FocusNotificationLiveData();
        this.focusNotificationLiveData = focusNotificationLiveData;
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        this.notification = mediatorLiveData;
        Objects.requireNonNull(mediatorLiveData);
        mediatorLiveData.addSource(animatedNotificationLiveData, new Observer() { // from class: com.miui.aod.notification.NotificationViewModel$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                mediatorLiveData.setValue((List) obj);
            }
        });
        Objects.requireNonNull(mediatorLiveData);
        mediatorLiveData.addSource(focusNotificationLiveData, new Observer() { // from class: com.miui.aod.notification.NotificationViewModel$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                mediatorLiveData.setValue((List) obj);
            }
        });
        Log.v("NotificationViewModel", "notification view model init finished...");
    }

    public void addCallback(NotificationController.NotificationChangeListener notificationChangeListener) {
        this.callbacks.add(notificationChangeListener);
    }

    public final boolean diffNotifications(List list, List list2) {
        boolean z = false;
        if (list != null && !list.isEmpty() && list2 != null && !list2.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                NotificationController.NotificationData notificationData = (NotificationController.NotificationData) it.next();
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    NotificationController.NotificationData notificationData2 = (NotificationController.NotificationData) it2.next();
                    if (TextUtils.equals(notificationData2.mPackageName, notificationData.mPackageName) && TextUtils.equals(notificationData2.mKey, notificationData.mKey)) {
                        it.remove();
                        z = true;
                        break;
                    }
                }
            }
        }
        return z;
    }

    public final boolean isFocusNotification(List list) {
        for (Class<?> cls : list.getClass().getInterfaces()) {
            if (cls.getName().equals("com.miui.aod.notification.NotificationViewModel$FocusInterface")) {
                return true;
            }
        }
        return false;
    }

    public void observe(LifecycleOwner lifecycleOwner) {
        this.notification.observe(lifecycleOwner, this);
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(final List list) {
        if (isFocusNotification(list)) {
            this.callbacks.forEach(new Consumer() { // from class: com.miui.aod.notification.NotificationViewModel$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((NotificationController.NotificationChangeListener) obj).onUpdateFocusNotification(list);
                }
            });
        } else {
            this.callbacks.forEach(new Consumer() { // from class: com.miui.aod.notification.NotificationViewModel$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationController.NotificationChangeListener notificationChangeListener = (NotificationController.NotificationChangeListener) obj;
                    notificationChangeListener.onUpdate(list, this.f$0.notificationLiveData.needAnimate());
                }
            });
        }
    }

    public void postFocusNotificationValue(List list) {
        if (diffNotifications((List) this.notificationLiveData.getValue(), list)) {
            postValue((List) this.notificationLiveData.getValue(), this.notificationLiveData.needAnimate());
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.focusNotificationLiveData.setValue(list);
        } else {
            this.focusNotificationLiveData.postValue(list);
        }
    }

    public void postValue(final List list, boolean z) {
        diffNotifications(list, (List) this.focusNotificationLiveData.getValue());
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.notificationLiveData.setValue(list, z);
        } else {
            this.notificationLiveData.postValue(list, z);
        }
        if (NotificationController.getInstance(AODApplication.sysuiContext).isAodInitDelaying()) {
            this.callbacks.forEach(new Consumer() { // from class: com.miui.aod.notification.NotificationViewModel$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationController.NotificationChangeListener notificationChangeListener = (NotificationController.NotificationChangeListener) obj;
                    notificationChangeListener.onUpdate(list, this.f$0.notificationLiveData.needAnimate());
                }
            });
        }
    }

    public void removeCallback(NotificationController.NotificationChangeListener notificationChangeListener) {
        this.callbacks.remove(notificationChangeListener);
    }

    public void removeObserve() {
        this.notification.removeObserver(this);
    }
}

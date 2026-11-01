package com.miui.aod.notification;

import android.content.ComponentName;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.ViewModelProvider;
import com.miui.aod.AODApplication;
import com.miui.aod.DozeLifecycleOwner;
import com.miui.aod.R;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.db.NotificationProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import miui.os.Build;
import miui.securityspace.XSpaceUserHandle;

/* JADX INFO: loaded from: classes.dex */
public class NotificationController {
    public static final Uri URI = Uri.parse("content://keyguard.notification/notifications");
    public static NotificationController sInstance;
    public final Context mContext;
    public List mCustomAppIconPackages;
    public NotificationWrap mLastNotificationWrap;
    public QueryTask mQueryTask;
    public boolean aodInitDelaying = false;
    public boolean isRegister = false;
    public ContentObserver mNotificationChangeObserver = new ContentObserver(null) { // from class: com.miui.aod.notification.NotificationController.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            BackgroundThread.removeCallbacks(NotificationController.this.updateTask);
            BackgroundThread.postDelayed(NotificationController.this.updateTask, 300L);
        }
    };
    public final Runnable updateTask = new Runnable() { // from class: com.miui.aod.notification.NotificationController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.update();
        }
    };
    public final NotificationListenerService mNotificationService = new NotificationListenerService() { // from class: com.miui.aod.notification.NotificationController.2
        @Override // android.service.notification.NotificationListenerService
        public void onListenerConnected() {
            super.onListenerConnected();
            NotificationController.this.update(false);
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        }
    };

    public interface NotificationChangeListener {
        void onUpdate(List list, boolean z);

        void onUpdateFocusNotification(List list);
    }

    public static class NotificationData implements Comparable<NotificationData> {
        public String mClassName;
        public int mColor;
        public boolean mHasProgressBar;
        public Drawable mIconDrawable;
        public boolean mIsDefaultTheme;
        public boolean mIsForegroundService;
        public boolean mIsNightMode;
        public boolean mIsXSpace;
        public String mKey;
        public final String mPackageName;
        public long mPostTime;
        public final String mText;
        public final String mTitle;
        public boolean mTravelDisplay;
        public boolean mUseSmallIcon;

        public NotificationData(String str, String str2) {
            this(str, str2, str2);
        }

        public NotificationData(String str, String str2, Drawable drawable, long j) {
            this(str, str2, str2);
            this.mIconDrawable = drawable;
            this.mPostTime = j;
        }

        public NotificationData(String str, String str2, String str3) {
            this.mTitle = str2;
            this.mPackageName = str;
            this.mText = str3;
        }

        @Override // java.lang.Comparable
        public int compareTo(NotificationData notificationData) {
            return Long.compare(notificationData.mPostTime, this.mPostTime);
        }

        public boolean equals(Object obj) {
            String str;
            if (!(obj instanceof NotificationData)) {
                return false;
            }
            NotificationData notificationData = (NotificationData) obj;
            if (this.mHasProgressBar && notificationData.mHasProgressBar && (str = this.mKey) != null && str.equals(notificationData.mKey)) {
                return true;
            }
            String str2 = this.mPackageName;
            if (str2 == null && notificationData.mPackageName != null) {
                return false;
            }
            if (str2 != null && !str2.equals(notificationData.mPackageName)) {
                return false;
            }
            String str3 = this.mKey;
            if (str3 == null && notificationData.mKey != null) {
                return false;
            }
            if (str3 != null && !str3.equals(notificationData.mKey)) {
                return false;
            }
            String str4 = this.mTitle;
            if (str4 == null && notificationData.mTitle != null) {
                return false;
            }
            if (str4 != null && !str4.equals(notificationData.mTitle)) {
                return false;
            }
            String str5 = this.mText;
            if (str5 != null || notificationData.mText == null) {
                return (str5 == null || str5.equals(notificationData.mText)) && this.mPostTime == notificationData.mPostTime && this.mIsForegroundService == notificationData.mIsForegroundService;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mTitle, this.mText, this.mPackageName, Long.valueOf(this.mPostTime), this.mKey, Boolean.valueOf(this.mIsForegroundService), Boolean.valueOf(this.mHasProgressBar));
        }

        public boolean isForegroundService() {
            return this.mIsForegroundService;
        }

        public void setHasProgressBar(boolean z) {
            this.mHasProgressBar = z;
        }

        public void setIsForegroundService(boolean z) {
            this.mIsForegroundService = z;
        }

        public String toString() {
            return "NotificationData{mPackageName='" + this.mPackageName + "', mClassName='" + this.mClassName + "', mTravelDisplay=" + this.mTravelDisplay + ", mPostTime=" + this.mPostTime + ", mKey='" + this.mKey + "', mIsForegroundService=" + this.mIsForegroundService + ", mHasProgressBar=" + this.mHasProgressBar + '}';
        }
    }

    public static class NotificationWrap {
        public final NotificationViewModel.Notification mNotifications;

        public NotificationWrap(NotificationViewModel.Notification notification) {
            this.mNotifications = notification == null ? new NotificationViewModel.Notification() : notification;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mNotifications.equals(((NotificationWrap) obj).mNotifications);
        }

        public int hashCode() {
            return Objects.hash(this.mNotifications);
        }
    }

    public class QueryTask extends AsyncTask<Void, Void, NotificationWrap> {
        public boolean mNeedAnimate;

        /* JADX INFO: renamed from: $r8$lambda$Ny0zRsHP9dnHqXAOP1-uiTylXD8, reason: not valid java name */
        public static /* synthetic */ void m431$r8$lambda$Ny0zRsHP9dnHqXAOP1uiTylXD8(QueryTask queryTask) {
            queryTask.getClass();
            try {
                NotificationController.this.mContext.getContentResolver().notifyChange(NotificationUtils.maybeAddUserId(NotificationProvider.URI_NOTIFICATION, 0), null);
            } catch (Exception e) {
                Log.e("NotificationController", "onPostExecute", e);
            }
        }

        public QueryTask(boolean z) {
            this.mNeedAnimate = z;
        }

        @Override // android.os.AsyncTask
        public NotificationWrap doInBackground(Void... voidArr) {
            List sortedKey = NotificationController.this.getSortedKey();
            NotificationViewModel.Notification notification = new NotificationViewModel.Notification();
            if (sortedKey != null) {
                try {
                    NotificationController.this.fillNotificationData(notification, sortedKey);
                } catch (Exception e) {
                    Log.e("NotificationController", "fillNotificationData", e);
                }
            }
            return new NotificationWrap(notification);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(NotificationWrap notificationWrap) {
            int changeType = NotificationController.this.getChangeType(notificationWrap);
            Log.d("NotificationController", "onPostExecute getChangeType: " + changeType);
            if (changeType > 0) {
                if (changeType == 2) {
                    this.mNeedAnimate = false;
                }
                NotificationViewModel notificationViewModel = (NotificationViewModel) new ViewModelProvider(DozeLifecycleOwner.getInstance()).get(NotificationViewModel.class);
                NotificationViewModel.Notification notification = new NotificationViewModel.Notification();
                notification.addAll(notificationWrap.mNotifications);
                notificationViewModel.postValue(notification, this.mNeedAnimate);
            }
            NotificationController.this.mLastNotificationWrap = notificationWrap;
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.notification.NotificationController$QueryTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationController.QueryTask.m431$r8$lambda$Ny0zRsHP9dnHqXAOP1uiTylXD8(this.f$0);
                }
            });
        }
    }

    public NotificationController(Context context) {
        this.mContext = context;
        try {
            context.getContentResolver().registerContentObserver(NotificationUtils.maybeAddUserId(URI, 0), false, this.mNotificationChangeObserver);
        } catch (Exception e) {
            Log.e("NotificationController", "error register contentObserver", e);
        } finally {
            this.mCustomAppIconPackages = Arrays.asList(AODApplication.sInstance.getResources().getStringArray(R.array.config_canCustomNotificationAppIcon));
        }
    }

    public static synchronized NotificationController getInstance(Context context) {
        try {
            if (sInstance == null) {
                sInstance = new NotificationController(context);
            }
            NotificationController notificationController = sInstance;
            if (!notificationController.isRegister) {
                notificationController.register();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public boolean canCustomAppIcon(String str) {
        List list = this.mCustomAppIconPackages;
        if (list != null) {
            return list.contains(str);
        }
        return false;
    }

    public final void fillNotificationData(NotificationData notificationData, StatusBarNotification statusBarNotification, boolean z, boolean z2, boolean z3) {
        notificationData.mClassName = NotificationUtils.getTargetClass(statusBarNotification.getNotification());
        notificationData.mPostTime = statusBarNotification.getPostTime();
        notificationData.mKey = statusBarNotification.getKey();
        notificationData.mIsDefaultTheme = z;
        notificationData.mColor = statusBarNotification.getNotification().color;
        notificationData.mIsNightMode = z2;
        notificationData.mUseSmallIcon = Build.IS_INTERNATIONAL_BUILD && z && !z3;
        notificationData.mIsXSpace = XSpaceUserHandle.isXSpaceUser(statusBarNotification.getUser());
        Drawable appIcon = NotificationUtils.getAppIcon(this.mContext, notificationData, statusBarNotification.getNotification());
        if (notificationData.mUseSmallIcon || !notificationData.mIsDefaultTheme) {
            notificationData.mIconDrawable = appIcon;
        } else {
            notificationData.mIconDrawable = XSpaceUserHandle.getXSpaceIcon(this.mContext, appIcon, statusBarNotification.getUser());
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x007c  */
    public final void fillNotificationData(List list, List list2) {
        NotificationController notificationController;
        StatusBarNotification[] activeNotifications = this.mNotificationService.getActiveNotifications();
        ArrayList arrayList = new ArrayList();
        if (list2 == null) {
            return;
        }
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "theme_product_id");
        boolean zIsEmpty = string != null ? string.isEmpty() : true;
        boolean z = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
        boolean z2 = Settings.System.getInt(this.mContext.getContentResolver(), "status_bar_notification_style", getDefaultNotifStyle()) == 0;
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            int length = activeNotifications.length;
            int i = 0;
            while (i < length) {
                StatusBarNotification statusBarNotification = activeNotifications[i];
                if (str == null || !str.equals(statusBarNotification.getKey())) {
                    notificationController = this;
                } else {
                    String notificationPackageName = this.getNotificationPackageName(statusBarNotification);
                    if (TextUtils.isEmpty(notificationPackageName)) {
                        notificationController = this;
                    } else {
                        NotificationData notificationData = new NotificationData(notificationPackageName, NotificationUtils.getNotificationTitle(statusBarNotification.getNotification()));
                        notificationController = this;
                        notificationController.fillNotificationData(notificationData, statusBarNotification, zIsEmpty, z, z2);
                        notificationData.setIsForegroundService(notificationController.isForegroundService(statusBarNotification));
                        notificationData.setHasProgressBar(notificationController.hasProgressbar(statusBarNotification).booleanValue());
                        arrayList.add(notificationData);
                    }
                }
                i++;
                this = notificationController;
            }
        }
        list.clear();
        list.addAll(arrayList);
        this.printNotificationData(list);
    }

    public final int getChangeType(NotificationWrap notificationWrap) {
        boolean zIsNotificationEmpty = isNotificationEmpty(this.mLastNotificationWrap);
        boolean zIsNotificationEmpty2 = isNotificationEmpty(notificationWrap);
        if (zIsNotificationEmpty && zIsNotificationEmpty2) {
            return 0;
        }
        if (zIsNotificationEmpty) {
            return 1;
        }
        if (zIsNotificationEmpty2) {
            return 2;
        }
        if (notificationWrap.mNotifications.containsAll(this.mLastNotificationWrap.mNotifications)) {
            return notificationWrap.mNotifications.size() > this.mLastNotificationWrap.mNotifications.size() ? 1 : 0;
        }
        return (!this.mLastNotificationWrap.mNotifications.containsAll(notificationWrap.mNotifications) || this.mLastNotificationWrap.mNotifications.size() <= notificationWrap.mNotifications.size()) ? 3 : 2;
    }

    public final int getDefaultNotifStyle() {
        return Build.IS_INTERNATIONAL_BUILD ? 1 : 0;
    }

    public List getNotificationList() {
        NotificationViewModel.Notification notification;
        NotificationWrap notificationWrap = this.mLastNotificationWrap;
        return (notificationWrap == null || (notification = notificationWrap.mNotifications) == null) ? Collections.EMPTY_LIST : Collections.unmodifiableList(notification);
    }

    public final String getNotificationPackageName(StatusBarNotification statusBarNotification) {
        String packageName = statusBarNotification.getPackageName();
        if (!TextUtils.isEmpty(packageName) && !packageName.equals("com.xiaomi.xmsf")) {
            return packageName;
        }
        CharSequence targetPkg = NotificationUtils.getTargetPkg(statusBarNotification.getNotification());
        if (targetPkg == null) {
            return null;
        }
        return targetPkg.toString();
    }

    public final List getSortedKey() {
        ArrayList arrayList = null;
        try {
            Bundle bundleCall = this.mContext.getContentResolver().call(NotificationUtils.maybeAddUserId(Uri.parse("content://keyguard.notification"), 0), "getKeyguardNotificationSortedKeys", (String) null, (Bundle) null);
            if (bundleCall != null) {
                arrayList = new ArrayList(bundleCall.getStringArrayList("sortedKeys"));
            }
        } catch (Exception e) {
            Log.e("NotificationController", "getSortedKey: ", e);
        }
        if (arrayList == null) {
            Log.i("NotificationController", "getSortedKey: result null");
        } else {
            Log.i("NotificationController", "getSortedKey: size " + arrayList.size());
            if (!arrayList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    sb.append((String) it.next());
                    sb.append("__");
                }
                Log.i("NotificationController", "getSortedKey: " + ((Object) sb));
            }
        }
        return arrayList;
    }

    public final Boolean hasProgressbar(StatusBarNotification statusBarNotification) {
        Bundle bundle = statusBarNotification.getNotification().extras;
        if (bundle != null) {
            return Boolean.valueOf(bundle.getInt("android.progressMax", 0) != 0 || bundle.getBoolean("android.progressIndeterminate", false));
        }
        Log.i("NotificationController", "hasProgressbar: extras is null");
        return Boolean.FALSE;
    }

    public void initUpdate() {
        this.mLastNotificationWrap = null;
        update(false);
    }

    public boolean isAodInitDelaying() {
        return this.aodInitDelaying;
    }

    public final boolean isForegroundService(StatusBarNotification statusBarNotification) {
        return (statusBarNotification == null || statusBarNotification.getNotification() == null || (statusBarNotification.getNotification().flags & 64) == 0) ? false : true;
    }

    public final boolean isNotificationEmpty(NotificationWrap notificationWrap) {
        return notificationWrap == null || notificationWrap.mNotifications.isEmpty();
    }

    public final void printNotificationData(List list) {
        if (list == null) {
            Log.i("NotificationController", "printNotificationData: notificationDataList == null");
            return;
        }
        Log.i("NotificationController", "printNotificationData: notificationDataList " + list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Log.i("NotificationController", ((NotificationData) it.next()).toString());
        }
    }

    public final void register() {
        try {
            NotificationUtils.registerAsSystemService(this.mNotificationService, this.mContext, new ComponentName(getClass().getPackage().getName(), getClass().getCanonicalName()), -1);
            Log.i("NotificationController", "registerAsSystemService success.");
            this.isRegister = true;
        } catch (RemoteException e) {
            Log.i("NotificationController", "registerAsSystemService failed", e);
        }
    }

    public void setAodInitDelaying(boolean z) {
        this.aodInitDelaying = z;
    }

    public final void update() {
        update(true);
    }

    public final void update(boolean z) {
        QueryTask queryTask = this.mQueryTask;
        if (queryTask != null) {
            queryTask.cancel(true);
        }
        QueryTask queryTask2 = new QueryTask(z);
        this.mQueryTask = queryTask2;
        queryTask2.execute(new Void[0]);
    }
}

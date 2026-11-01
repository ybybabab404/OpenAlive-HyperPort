package miuix.appcompat.app.floatingactivity.multiapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import miuix.appcompat.R;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.floatingactivity.FloatingActivitySwitcher;
import miuix.appcompat.app.floatingactivity.FloatingAnimHelper;
import miuix.appcompat.app.floatingactivity.MemoryFileUtil;
import miuix.appcompat.app.floatingactivity.OnFloatingCallback;
import miuix.appcompat.app.floatingactivity.SnapShotViewHelper;
import miuix.appcompat.app.floatingactivity.helper.FloatingHelperFactory;

/* JADX INFO: loaded from: classes2.dex */
public final class MultiAppFloatingActivitySwitcher {
    public static String[] mAllowedPackageList;
    public static MultiAppFloatingActivitySwitcher sInstance;
    public long mCloseAllActivityTime;
    public IFloatingService mIFloatingService;
    public WeakReference mLastActivityPanel;
    public long mOnDragEndTime;
    public long mOnDragStartTime;
    public boolean mServiceConnected;
    public final Handler mExitAnimationHandler = new Handler(Looper.getMainLooper());
    public final SparseArray mActivityCache = new SparseArray();
    public boolean mEnableDragToDismiss = true;
    public final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: miuix.appcompat.app.floatingactivity.multiapp.MultiAppFloatingActivitySwitcher.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MFloatingSwitcher", "onServiceConnected");
            if (MultiAppFloatingActivitySwitcher.sInstance != null) {
                MultiAppFloatingActivitySwitcher.sInstance.setIFloatingService(IFloatingService.Stub.asInterface(iBinder));
                MultiAppFloatingActivitySwitcher.this.checkRegister();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("MFloatingSwitcher", "onServiceDisconnected");
            if (MultiAppFloatingActivitySwitcher.sInstance != null) {
                MultiAppFloatingActivitySwitcher.sInstance.unRegisterAll();
                MultiAppFloatingActivitySwitcher.this.clear();
                MultiAppFloatingActivitySwitcher.this.destroy();
            }
        }
    };

    public static class ActivitySpec implements Parcelable {
        public static final Parcelable.Creator<ActivitySpec> CREATOR = new Parcelable.Creator<ActivitySpec>() { // from class: miuix.appcompat.app.floatingactivity.multiapp.MultiAppFloatingActivitySwitcher.ActivitySpec.1
            @Override // android.os.Parcelable.Creator
            public ActivitySpec createFromParcel(Parcel parcel) {
                return new ActivitySpec(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public ActivitySpec[] newArray(int i) {
                return new ActivitySpec[i];
            }
        };
        public AppCompatActivity activity;
        public String identity;
        public int index;
        public boolean isOpenEnterAnimExecuted;
        public List pendingTasks;
        public boolean register;
        public boolean resumed;
        public ServiceNotify serviceNotify;
        public int serviceNotifyIndex;
        public int taskId;

        public ActivitySpec(Parcel parcel) {
            this.index = -1;
            this.register = false;
            this.isOpenEnterAnimExecuted = false;
            this.index = parcel.readInt();
            this.taskId = parcel.readInt();
            this.identity = parcel.readString();
            this.resumed = parcel.readByte() != 0;
            this.serviceNotifyIndex = parcel.readInt();
            this.register = parcel.readByte() != 0;
            this.isOpenEnterAnimExecuted = parcel.readByte() != 0;
            this.pendingTasks = new LinkedList();
        }

        public ActivitySpec(boolean z) {
            this.index = -1;
            this.register = false;
            this.isOpenEnterAnimExecuted = false;
            this.resumed = z;
            this.pendingTasks = new LinkedList();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "{ index : " + this.index + "; taskId : " + this.taskId + "; taskId : " + this.taskId + "; identity : " + this.identity + "; serviceNotifyIndex : " + this.serviceNotifyIndex + "; register : " + this.register + "; isOpenEnterAnimExecuted : " + this.isOpenEnterAnimExecuted + "; }";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.index);
            parcel.writeInt(this.taskId);
            parcel.writeString(this.identity);
            parcel.writeByte(this.resumed ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.serviceNotifyIndex);
            parcel.writeByte(this.register ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.isOpenEnterAnimExecuted ? (byte) 1 : (byte) 0);
        }
    }

    public class DefineOnFloatingActivityCallback implements OnFloatingCallback {
        public int mAppCompatActivityTaskId;
        public String mAppCompatIdentity;

        public DefineOnFloatingActivityCallback(AppCompatActivity appCompatActivity) {
            this.mAppCompatIdentity = appCompatActivity.getActivityIdentity();
            this.mAppCompatActivityTaskId = appCompatActivity.getTaskId();
        }

        private boolean checkFinishEnable(int i) {
            if (MultiAppFloatingActivitySwitcher.this.mEnableDragToDismiss) {
                return false;
            }
            return i == 1 || i == 2;
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public void closeAllPage() {
            MultiAppFloatingActivitySwitcher.this.notifyService(11);
        }

        public String getActivityIdentity() {
            return this.mAppCompatIdentity;
        }

        public int getActivityTaskId() {
            return this.mAppCompatActivityTaskId;
        }

        public int getPageCount() {
            return Math.max(MultiAppFloatingActivitySwitcher.this.getServicePageCount(getActivityTaskId()), MultiAppFloatingActivitySwitcher.this.getCurrentPageCount(getActivityTaskId()));
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public void getSnapShotAndSetPanel(AppCompatActivity appCompatActivity) throws Throwable {
            if (appCompatActivity != null) {
                try {
                    MultiAppFloatingActivitySwitcher multiAppFloatingActivitySwitcher = MultiAppFloatingActivitySwitcher.getInstance();
                    if (multiAppFloatingActivitySwitcher != null) {
                        multiAppFloatingActivitySwitcher.saveBitmap(SnapShotViewHelper.getSnapShot(appCompatActivity.getFloatingBrightPanel()), appCompatActivity.getTaskId(), appCompatActivity.getActivityIdentity());
                    }
                } catch (Exception e) {
                    Log.d("MFloatingSwitcher", "saveBitmap exception", e);
                }
            }
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public boolean isFirstPage() {
            ArrayList arrayList = (ArrayList) MultiAppFloatingActivitySwitcher.this.mActivityCache.get(getActivityTaskId());
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ActivitySpec activitySpec = (ActivitySpec) arrayList.get(i);
                    AppCompatActivity appCompatActivity = activitySpec.activity;
                    if (appCompatActivity != null && activitySpec.index == 0) {
                        return appCompatActivity.getActivityIdentity().equals(getActivityIdentity());
                    }
                }
            }
            return false;
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public boolean isFirstPageEnterAnimExecuteEnable() {
            ArrayList arrayList = (ArrayList) MultiAppFloatingActivitySwitcher.this.mActivityCache.get(getActivityTaskId());
            if (arrayList != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    ActivitySpec activitySpec = (ActivitySpec) arrayList.get(i);
                    if (activitySpec.index == 0) {
                        return !activitySpec.isOpenEnterAnimExecuted;
                    }
                }
            }
            return false;
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public boolean isFirstPageExitAnimExecuteEnable() {
            return getPageCount() == 1;
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public void markActivityOpenEnterAnimExecuted(AppCompatActivity appCompatActivity) {
            MultiAppFloatingActivitySwitcher.this.markActivityOpenEnterAnimExecutedInternal(appCompatActivity.getTaskId(), appCompatActivity.getActivityIdentity());
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public void onDragEnd() {
            MultiAppFloatingActivitySwitcher.this.notifyService(2);
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public void onDragStart() {
            MultiAppFloatingActivitySwitcher.this.notifyService(1);
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingActivityCallback
        public boolean onFinish(int i) {
            if (!checkFinishEnable(i) && MultiAppFloatingActivitySwitcher.this.shouldAllFloatingClose(i, getActivityTaskId())) {
                MultiAppFloatingActivitySwitcher.this.notifyService(5);
            }
            return false;
        }

        @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
        public void onHideBehindPage() {
            MultiAppFloatingActivitySwitcher.this.notifyService(5);
        }
    }

    public static class OpenExitAnimationExecutor implements Runnable {
        public WeakReference mAppCompatActivity;

        public OpenExitAnimationExecutor(AppCompatActivity appCompatActivity) {
            this.mAppCompatActivity = null;
            this.mAppCompatActivity = new WeakReference(appCompatActivity);
        }

        @Override // java.lang.Runnable
        public void run() {
            AppCompatActivity appCompatActivity = (AppCompatActivity) this.mAppCompatActivity.get();
            if (appCompatActivity != null) {
                appCompatActivity.executeOpenExitAnimation();
            }
        }
    }

    public class ServiceNotify extends IServiceNotify.Stub {
        public String mActivityIdentity;
        public int mActivityTaskId;

        public ServiceNotify(AppCompatActivity appCompatActivity) {
            this.mActivityIdentity = appCompatActivity.getActivityIdentity();
            this.mActivityTaskId = appCompatActivity.getTaskId();
        }

        public final AppCompatActivity getActivity() {
            MultiAppFloatingActivitySwitcher multiAppFloatingActivitySwitcher = MultiAppFloatingActivitySwitcher.getInstance();
            if (multiAppFloatingActivitySwitcher != null) {
                return multiAppFloatingActivitySwitcher.getActivity(getActivityTaskId(), getActivityIdentity());
            }
            return null;
        }

        public String getActivityIdentity() {
            return this.mActivityIdentity;
        }

        public int getActivityTaskId() {
            return this.mActivityTaskId;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // miuix.appcompat.app.floatingactivity.multiapp.IServiceNotify
        public Bundle notifyFromService(int i, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            if (i == 1) {
                MultiAppFloatingActivitySwitcher.sInstance.hideBehindPages();
                return bundle2;
            }
            if (i == 2) {
                MultiAppFloatingActivitySwitcher.sInstance.onDragEnd();
                return bundle2;
            }
            if (i == 3) {
                MultiAppFloatingActivitySwitcher.sInstance.closeAllActivity();
                AppCompatActivity activity = getActivity();
                if (activity != null) {
                    MultiAppFloatingActivitySwitcher.sInstance.unbindService(activity);
                }
            } else {
                if (i == 5) {
                    MultiAppFloatingActivitySwitcher.sInstance.hideBehindPages();
                    return bundle2;
                }
                switch (i) {
                    case 8:
                        AppCompatActivity activity2 = getActivity();
                        if (bundle != null && activity2 != null) {
                            View floatingBrightPanel = activity2.getFloatingBrightPanel();
                            MultiAppFloatingActivitySwitcher.this.setLastActivityPanel(SnapShotViewHelper.generateSnapShotView(floatingBrightPanel, MemoryFileUtil.readBitmap(bundle)));
                            if (MultiAppFloatingActivitySwitcher.this.mLastActivityPanel != null && MultiAppFloatingActivitySwitcher.this.mLastActivityPanel.get() != null) {
                                ((ViewGroup) floatingBrightPanel.getParent()).getOverlay().add((View) MultiAppFloatingActivitySwitcher.this.mLastActivityPanel.get());
                                return bundle2;
                            }
                        }
                        break;
                    case 9:
                        AppCompatActivity activity3 = getActivity();
                        bundle2.putBoolean("check_finishing", activity3 != null && activity3.isFinishing());
                        return bundle2;
                    case 10:
                        AppCompatActivity activity4 = getActivity();
                        if (activity4 != null) {
                            MultiAppFloatingActivitySwitcher.this.mExitAnimationHandler.postDelayed(new OpenExitAnimationExecutor(activity4), 160L);
                            return bundle2;
                        }
                        break;
                    case 11:
                        MultiAppFloatingActivitySwitcher.sInstance.closeAllPage();
                        return bundle2;
                }
            }
            return bundle2;
        }

        public void resetAppCompatActivity(AppCompatActivity appCompatActivity) {
            this.mActivityIdentity = appCompatActivity.getActivityIdentity();
            this.mActivityTaskId = appCompatActivity.getTaskId();
        }
    }

    public static MultiAppFloatingActivitySwitcher getInstance() {
        return sInstance;
    }

    public static void install(AppCompatActivity appCompatActivity, Intent intent, Bundle bundle) {
        if (!isFromMultiApp(intent)) {
            FloatingActivitySwitcher.install(appCompatActivity, bundle);
            return;
        }
        if (sInstance == null) {
            sInstance = new MultiAppFloatingActivitySwitcher();
            if (mAllowedPackageList == null) {
                mAllowedPackageList = appCompatActivity.getResources().getStringArray(R.array.multi_floating_package_allow_list);
            }
            sInstance.bindService(appCompatActivity, intent);
        }
        sInstance.init(appCompatActivity, intent, bundle);
    }

    public static boolean isFromMultiApp(Intent intent) {
        return (TextUtils.isEmpty(intent.getStringExtra("floating_service_pkg")) || TextUtils.isEmpty(intent.getStringExtra("floating_service_path"))) ? false : true;
    }

    public static void onSaveInstanceState(int i, String str, Bundle bundle) {
        ActivitySpec activitySpec;
        MultiAppFloatingActivitySwitcher multiAppFloatingActivitySwitcher = getInstance();
        if (multiAppFloatingActivitySwitcher == null || (activitySpec = multiAppFloatingActivitySwitcher.getActivitySpec(i, str)) == null) {
            return;
        }
        bundle.putParcelable("floating_switcher_saved_key", activitySpec);
    }

    public final void bindService(Context context, Intent intent) {
        Intent intent2 = new Intent();
        String stringExtra = intent.getStringExtra("floating_service_pkg");
        if (isPackageAllowed(stringExtra)) {
            intent2.setPackage(stringExtra);
            String stringExtra2 = intent.getStringExtra("floating_service_path");
            if (TextUtils.isEmpty(stringExtra2)) {
                return;
            }
            intent2.setComponent(new ComponentName(stringExtra, stringExtra2));
            context.getApplicationContext().bindService(intent2, this.mServiceConnection, 1);
        }
    }

    public void checkBg(int i, String str) {
        ActivitySpec activitySpec;
        AppCompatActivity appCompatActivity;
        ArrayList arrayList = (ArrayList) this.mActivityCache.get(i);
        if (((arrayList == null || arrayList.size() <= 1) && getServicePageCount(i) <= 1) || (activitySpec = getActivitySpec(i, str)) == null || activitySpec.serviceNotifyIndex <= 0 || (appCompatActivity = activitySpec.activity) == null) {
            return;
        }
        appCompatActivity.hideFloatingDimBackground();
    }

    public final void checkRegister() {
        for (int i = 0; i < this.mActivityCache.size(); i++) {
            for (ActivitySpec activitySpec : (ArrayList) this.mActivityCache.valueAt(i)) {
                if (!activitySpec.register) {
                    invokeRegister(activitySpec);
                    checkBg(activitySpec.taskId, activitySpec.identity);
                }
            }
        }
    }

    public void clear() {
        this.mActivityCache.clear();
        this.mLastActivityPanel = null;
    }

    public void clearActivitySpecTask(int i, String str) {
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec != null) {
            activitySpec.pendingTasks.clear();
        }
    }

    public final void closeAllActivity() {
        if (isCalled(this.mCloseAllActivityTime)) {
            return;
        }
        this.mCloseAllActivityTime = System.currentTimeMillis();
        for (int i = 0; i < this.mActivityCache.size(); i++) {
            ArrayList arrayList = (ArrayList) this.mActivityCache.valueAt(i);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                AppCompatActivity appCompatActivity = ((ActivitySpec) arrayList.get(size)).activity;
                int i2 = ((ActivitySpec) arrayList.get(size)).index;
                int servicePageCount = getServicePageCount(((ActivitySpec) arrayList.get(size)).taskId);
                if (appCompatActivity != null && i2 != servicePageCount - 1) {
                    appCompatActivity.realFinish();
                }
            }
        }
    }

    public final void closeAllPage() {
        if (isCalled(this.mCloseAllActivityTime)) {
            return;
        }
        this.mCloseAllActivityTime = System.currentTimeMillis();
        for (int i = 0; i < this.mActivityCache.size(); i++) {
            ArrayList arrayList = (ArrayList) this.mActivityCache.valueAt(i);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                AppCompatActivity appCompatActivity = ((ActivitySpec) arrayList.get(size)).activity;
                int i2 = ((ActivitySpec) arrayList.get(size)).index;
                int servicePageCount = getServicePageCount(((ActivitySpec) arrayList.get(size)).taskId);
                if (appCompatActivity != null && i2 != servicePageCount - 1) {
                    appCompatActivity.realFinish();
                }
            }
        }
    }

    public void destroy() {
        if (this.mActivityCache.size() == 0) {
            sInstance = null;
        }
    }

    public AppCompatActivity getActivity(int i, String str) {
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec != null) {
            return activitySpec.activity;
        }
        return null;
    }

    public final ActivitySpec getActivitySpec(int i, String str) {
        ArrayList<ActivitySpec> arrayList = (ArrayList) this.mActivityCache.get(i);
        if (arrayList == null) {
            return null;
        }
        for (ActivitySpec activitySpec : arrayList) {
            if (TextUtils.equals(activitySpec.identity, str)) {
                return activitySpec;
            }
        }
        return null;
    }

    public int getCurrentPageCount(int i) {
        ArrayList arrayList = (ArrayList) this.mActivityCache.get(i);
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public String getIdentity(Object obj, int i) {
        return obj.hashCode() + ":" + i;
    }

    public View getLastActivityPanel() {
        WeakReference weakReference = this.mLastActivityPanel;
        if (weakReference == null) {
            return null;
        }
        return (View) weakReference.get();
    }

    public int getServicePageCount(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("key_task_id", i);
        Bundle bundleNotifyService = notifyService(6, bundle);
        int i2 = bundleNotifyService != null ? bundleNotifyService.getInt(String.valueOf(6)) : 0;
        ArrayList arrayList = (ArrayList) this.mActivityCache.get(i);
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                int i3 = ((ActivitySpec) it.next()).index;
                if (i3 + 1 > i2) {
                    i2 = i3 + 1;
                }
            }
        }
        return i2;
    }

    public final void hideBehindPages() {
        final AppCompatActivity appCompatActivity;
        if (isCalled(this.mOnDragStartTime)) {
            return;
        }
        this.mOnDragStartTime = System.currentTimeMillis();
        for (int i = 0; i < this.mActivityCache.size(); i++) {
            for (ActivitySpec activitySpec : (ArrayList) this.mActivityCache.valueAt(i)) {
                if (!activitySpec.resumed && (appCompatActivity = activitySpec.activity) != null) {
                    appCompatActivity.runOnUiThread(new Runnable() { // from class: miuix.appcompat.app.floatingactivity.multiapp.MultiAppFloatingActivitySwitcher$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            appCompatActivity.hideFloatingBrightPanel();
                        }
                    });
                }
            }
        }
    }

    public final void hideTopBgs(int i) {
        ArrayList arrayList = (ArrayList) this.mActivityCache.get(i);
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                int i3 = ((ActivitySpec) arrayList.get(i2)).index;
                AppCompatActivity appCompatActivity = ((ActivitySpec) arrayList.get(i2)).activity;
                if (appCompatActivity != null && i3 != 0) {
                    appCompatActivity.hideFloatingDimBackground();
                }
            }
        }
    }

    public final void init(AppCompatActivity appCompatActivity, Intent intent, Bundle bundle) {
        if (FloatingHelperFactory.getFloatingHelperType(appCompatActivity) == 0) {
            return;
        }
        stashActivity(appCompatActivity, intent, bundle);
        registerActivityToService(appCompatActivity);
        appCompatActivity.getLifecycle().addObserver(new MultiAppFloatingLifecycleObserver(appCompatActivity));
        appCompatActivity.setEnableSwipToDismiss(this.mEnableDragToDismiss);
        appCompatActivity.setOnFloatingCallback(new DefineOnFloatingActivityCallback(appCompatActivity));
    }

    public final void invokeRegister(ActivitySpec activitySpec) {
        IFloatingService iFloatingService;
        if (activitySpec == null || (iFloatingService = this.mIFloatingService) == null) {
            return;
        }
        try {
            ServiceNotify serviceNotify = activitySpec.serviceNotify;
            iFloatingService.registerServiceNotify(serviceNotify, getIdentity(serviceNotify, activitySpec.taskId));
            updateServerActivityIndex(getIdentity(activitySpec.serviceNotify, activitySpec.taskId), activitySpec.index);
            if (!activitySpec.register) {
                activitySpec.register = true;
                activitySpec.serviceNotifyIndex = activitySpec.index;
            }
            Iterator it = activitySpec.pendingTasks.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
            activitySpec.pendingTasks.clear();
        } catch (RemoteException e) {
            Log.w("MFloatingSwitcher", "catch register service notify exception", e);
        }
    }

    public boolean isAboveActivityFinishing(int i, String str) {
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("key_request_identity", String.valueOf(activitySpec.serviceNotify.hashCode()));
        bundle.putInt("key_task_id", i);
        Bundle bundleNotifyService = notifyService(9, bundle);
        return bundleNotifyService != null && bundleNotifyService.getBoolean("check_finishing");
    }

    public boolean isActivityOpenEnterAnimExecuted(int i, String str) {
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec != null) {
            return activitySpec.isOpenEnterAnimExecuted;
        }
        return false;
    }

    public final boolean isActivityStashed(AppCompatActivity appCompatActivity) {
        return (appCompatActivity == null || getActivitySpec(appCompatActivity.getTaskId(), appCompatActivity.getActivityIdentity()) == null) ? false : true;
    }

    public final boolean isCalled(long j) {
        return System.currentTimeMillis() - j <= 100;
    }

    public final boolean isPackageAllowed(String str) {
        for (String str2 : mAllowedPackageList) {
            if (str2.equals(str)) {
                return true;
            }
        }
        Log.w("MFloatingSwitcher", "Package is not allowed:" + str + ". Please contact the MIUIX developer!");
        return false;
    }

    public boolean isServiceAvailable() {
        return this.mIFloatingService != null;
    }

    public void markActivityOpenEnterAnimExecutedInternal(int i, String str) {
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec != null) {
            activitySpec.isOpenEnterAnimExecuted = true;
        }
    }

    public void notifyPreviousActivitySlide(int i, String str) {
        final ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec == null) {
            return;
        }
        Runnable runnable = new Runnable() { // from class: miuix.appcompat.app.floatingactivity.multiapp.MultiAppFloatingActivitySwitcher.2
            @Override // java.lang.Runnable
            public void run() {
                String strValueOf = String.valueOf(activitySpec.serviceNotify.hashCode());
                Bundle bundle = new Bundle();
                bundle.putInt("key_task_id", activitySpec.taskId);
                bundle.putString("execute_slide", strValueOf);
                MultiAppFloatingActivitySwitcher.this.notifyService(10, bundle);
            }
        };
        if (isServiceAvailable()) {
            runnable.run();
        } else {
            activitySpec.pendingTasks.add(runnable);
        }
    }

    public final Bundle notifyService(int i) {
        return notifyService(i, null);
    }

    public final Bundle notifyService(int i, Bundle bundle) {
        IFloatingService iFloatingService = this.mIFloatingService;
        if (iFloatingService == null) {
            Log.d("MFloatingSwitcher", "ifloatingservice is null");
            return null;
        }
        try {
            return iFloatingService.callServiceMethod(i, bundle);
        } catch (RemoteException e) {
            Log.w("MFloatingSwitcher", "catch call service method exception", e);
            return null;
        }
    }

    public final void onDragEnd() {
        final AppCompatActivity appCompatActivity;
        if (isCalled(this.mOnDragEndTime)) {
            return;
        }
        this.mOnDragEndTime = System.currentTimeMillis();
        for (int i = 0; i < this.mActivityCache.size(); i++) {
            for (ActivitySpec activitySpec : (ArrayList) this.mActivityCache.valueAt(i)) {
                if (!activitySpec.resumed && (appCompatActivity = activitySpec.activity) != null) {
                    appCompatActivity.runOnUiThread(new Runnable() { // from class: miuix.appcompat.app.floatingactivity.multiapp.MultiAppFloatingActivitySwitcher$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            appCompatActivity.showFloatingBrightPanel();
                        }
                    });
                }
            }
        }
    }

    public void postEnterAnimationTask(int i, String str, Runnable runnable) {
        if (isActivityOpenEnterAnimExecuted(i, str)) {
            return;
        }
        if (getCurrentPageCount(i) > 1 || getServicePageCount(i) > 1) {
            markActivityOpenEnterAnimExecutedInternal(i, str);
        }
        if (isServiceAvailable()) {
            runnable.run();
            return;
        }
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec != null) {
            activitySpec.pendingTasks.add(runnable);
        }
    }

    public final void registerActivityToService(AppCompatActivity appCompatActivity) {
        ActivitySpec activitySpec = getActivitySpec(appCompatActivity.getTaskId(), appCompatActivity.getActivityIdentity());
        if (activitySpec != null && activitySpec.serviceNotify == null) {
            activitySpec.serviceNotify = new ServiceNotify(appCompatActivity);
        } else if (activitySpec != null) {
            activitySpec.serviceNotify.resetAppCompatActivity(appCompatActivity);
        }
        invokeRegister(activitySpec);
    }

    public void remove(int i, String str) {
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec == null || activitySpec.activity == null) {
            return;
        }
        unRegisterActivityFromService(i, str);
        ArrayList arrayList = (ArrayList) this.mActivityCache.get(i);
        if (arrayList != null) {
            arrayList.remove(activitySpec);
            if (arrayList.isEmpty()) {
                this.mActivityCache.remove(i);
            }
        }
        if (this.mActivityCache.size() == 0) {
            unbindService(activitySpec.activity);
            clear();
        }
    }

    public void saveBitmap(Bitmap bitmap, int i, String str) throws Throwable {
        ActivitySpec activitySpec;
        if (bitmap == null || (activitySpec = getActivitySpec(i, str)) == null) {
            return;
        }
        int byteCount = bitmap.getByteCount();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteCount);
        bitmap.copyPixelsToBuffer(byteBufferAllocate);
        MemoryFileUtil.sendToFdServer(this.mIFloatingService, byteBufferAllocate.array(), byteCount, bitmap.getWidth(), bitmap.getHeight(), String.valueOf(activitySpec.serviceNotify.hashCode()), i);
    }

    public final void setIFloatingService(IFloatingService iFloatingService) {
        this.mIFloatingService = iFloatingService;
        this.mServiceConnected = true;
    }

    public void setLastActivityPanel(View view) {
        this.mLastActivityPanel = new WeakReference(view);
    }

    public final boolean shouldAllFloatingClose(int i, int i2) {
        return !(i == 4 || i == 3) || getServicePageCount(i2) <= 1;
    }

    public final void stashActivity(AppCompatActivity appCompatActivity, Intent intent, Bundle bundle) {
        if (!isActivityStashed(appCompatActivity)) {
            ActivitySpec activitySpec = bundle != null ? (ActivitySpec) bundle.getParcelable("floating_switcher_saved_key") : null;
            int i = 0;
            if (activitySpec == null) {
                activitySpec = new ActivitySpec(true);
                if (intent == null) {
                    intent = appCompatActivity.getIntent();
                }
                activitySpec.index = intent.getIntExtra("service_page_index", 0);
            }
            activitySpec.activity = appCompatActivity;
            activitySpec.taskId = appCompatActivity.getTaskId();
            activitySpec.identity = appCompatActivity.getActivityIdentity();
            ArrayList arrayList = (ArrayList) this.mActivityCache.get(activitySpec.taskId);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mActivityCache.put(activitySpec.taskId, arrayList);
            }
            int i2 = activitySpec.index;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (i2 > ((ActivitySpec) arrayList.get(size)).index) {
                    i = size + 1;
                    break;
                }
            }
            arrayList.add(i, activitySpec);
            FloatingAnimHelper.markedPageIndex(appCompatActivity, activitySpec.index);
        }
        hideTopBgs(appCompatActivity.getTaskId());
    }

    public final void unRegisterActivityFromService(int i, String str) {
        if (this.mIFloatingService != null) {
            try {
                ActivitySpec activitySpec = getActivitySpec(i, str);
                if (activitySpec != null) {
                    IFloatingService iFloatingService = this.mIFloatingService;
                    ServiceNotify serviceNotify = activitySpec.serviceNotify;
                    iFloatingService.unregisterServiceNotify(serviceNotify, String.valueOf(serviceNotify.hashCode()));
                }
            } catch (RemoteException e) {
                Log.w("MFloatingSwitcher", "catch unregister service notify exception", e);
            }
        }
    }

    public final void unRegisterAll() {
        for (int i = 0; i < this.mActivityCache.size(); i++) {
            for (ActivitySpec activitySpec : (ArrayList) this.mActivityCache.valueAt(i)) {
                unRegisterActivityFromService(activitySpec.taskId, activitySpec.identity);
            }
        }
    }

    public final void unbindService(Context context) {
        if (this.mServiceConnected) {
            this.mServiceConnected = false;
            context.getApplicationContext().unbindService(this.mServiceConnection);
        }
    }

    public void updateResumeState(int i, String str, boolean z) {
        ActivitySpec activitySpec = getActivitySpec(i, str);
        if (activitySpec != null) {
            activitySpec.resumed = z;
        }
    }

    public final void updateServerActivityIndex(String str, int i) {
        IFloatingService iFloatingService = this.mIFloatingService;
        if (iFloatingService != null) {
            try {
                iFloatingService.upDateRemoteActivityInfo(str, i);
            } catch (RemoteException e) {
                Log.w("MFloatingSwitcher", "catch updateServerActivityIndex service notify exception", e);
            }
        }
    }
}

package com.miui.clock.tiny;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.Task;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class TinyMiuiClockController {
    public TinyClockBean mClockBean;
    public IClockView mClockView;
    public ViewGroup mContainer;
    public Context mContext;
    public volatile boolean mHasRegisterTimeListener;
    public BroadcastReceiver mTimezoneChangeReceiver;
    public volatile ITinyMiuiClockViewListener mTinyMiuiClockViewListener;
    public int mType;
    public BroadcastReceiver mUpdateTimeReceiver;
    public int mRotation = 0;
    public final Handler mHandler = new Handler();
    public final Runnable mAddListenerTask = new Runnable() { // from class: com.miui.clock.tiny.TinyMiuiClockController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            TinyMiuiClockController.m653$r8$lambda$tUZDkcr2L2bRb4mBIefo0C_DSc(this.f$0);
        }
    };
    public final Runnable mRemoveListenerTask = new Runnable() { // from class: com.miui.clock.tiny.TinyMiuiClockController$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            TinyMiuiClockController.$r8$lambda$k1QuPxgo7QvS3FsGsxIhpOgOuVs(this.f$0);
        }
    };
    public final Runnable mUpdateTimeRunnable = new Runnable() { // from class: com.miui.clock.tiny.TinyMiuiClockController.3
        @Override // java.lang.Runnable
        public void run() {
            TinyMiuiClockController.this.updateTimeUnchecked();
        }
    };

    public interface IClockView {
        Calendar getClockCalendar();

        TinyClockInfo getClockStyleInfo();

        View getIClockView(TinyClockViewType tinyClockViewType);

        int getMarginAOD();

        void setBatteryColor(int i);

        void setBatteryLevel(int i);

        void setClockStyleInfo(TinyClockInfo tinyClockInfo);

        default void toLinkageAodState() {
        }

        void updateTime();

        void updateTimeZone(String str);
    }

    public static /* synthetic */ void $r8$lambda$k1QuPxgo7QvS3FsGsxIhpOgOuVs(TinyMiuiClockController tinyMiuiClockController) {
        tinyMiuiClockController.getClass();
        Log.w("TinyMiuiClockController", "execute: mRemoveListenerTask" + tinyMiuiClockController);
        tinyMiuiClockController.removeTimeListener();
    }

    /* JADX INFO: renamed from: $r8$lambda$tUZDkcr2L-2bRb4mBIefo0C_DSc, reason: not valid java name */
    public static /* synthetic */ void m653$r8$lambda$tUZDkcr2L2bRb4mBIefo0C_DSc(TinyMiuiClockController tinyMiuiClockController) {
        tinyMiuiClockController.getClass();
        Log.w("TinyMiuiClockController", "execute: mAddListenerTask" + tinyMiuiClockController);
        tinyMiuiClockController.registerTimeListener();
    }

    public TinyMiuiClockController(Context context, ViewGroup viewGroup, TinyClockBean tinyClockBean, int i, int i2) {
        this.mContext = context;
        this.mContainer = viewGroup;
        this.mType = i;
        setRotation(i2);
        this.mClockBean = tinyClockBean;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void addClockView(TinyClockBean tinyClockBean) {
        this.mContainer.removeAllViews();
        TinyClockInfo tinyClockInfoConvertInfoFromClockBean = TinyClockInfo.convertInfoFromClockBean(tinyClockBean, this.mType);
        tinyClockInfoConvertInfoFromClockBean.setRotation(this.mRotation);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(tinyClockInfoConvertInfoFromClockBean.getLayoutId(this.mRotation), this.mContainer, false);
        IClockView iClockView = (IClockView) viewInflate;
        this.mClockView = iClockView;
        if (iClockView != null) {
            iClockView.setClockStyleInfo(tinyClockInfoConvertInfoFromClockBean);
        }
        this.mContainer.addView(viewInflate);
        Log.d("TinyMiuiClockController", "addClockView: clockInfo = " + tinyClockInfoConvertInfoFromClockBean);
    }

    public void build() {
        TinyClockBean tinyClockBean = this.mClockBean;
        if (tinyClockBean != null) {
            addClockView(tinyClockBean);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void changeRotation(int i) {
        if (this.mClockView == null || this.mClockBean == null) {
            return;
        }
        this.mContainer.removeAllViews();
        setRotation(i);
        TinyClockInfo clockStyleInfo = this.mClockView.getClockStyleInfo() != null ? this.mClockView.getClockStyleInfo() : TinyClockInfo.convertInfoFromClockBean(this.mClockBean, this.mType);
        if (clockStyleInfo == null) {
            Log.e("TinyMiuiClockController", "covert info error");
            return;
        }
        clockStyleInfo.setRotation(i);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(clockStyleInfo.getLayoutId(i), this.mContainer, false);
        IClockView iClockView = (IClockView) viewInflate;
        this.mClockView = iClockView;
        if (iClockView != null) {
            iClockView.setClockStyleInfo(clockStyleInfo);
        }
        this.mContainer.addView(viewInflate);
        if (this.mTinyMiuiClockViewListener != null) {
            this.mTinyMiuiClockViewListener.onRotateChange(i);
        }
        Log.d("TinyMiuiClockController", this.mClockView.getClockStyleInfo().getTemplateId() + " clock view rotation changed to " + i + " info=[" + clockStyleInfo + "]");
    }

    public TinyClockBean getClockBean() {
        return this.mClockBean;
    }

    public Calendar getClockCalendar() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getClockCalendar();
        }
        return null;
    }

    public TinyClockInfo getClockStyleInfo() {
        return this.mClockView.getClockStyleInfo();
    }

    public View getIClockView(TinyClockViewType tinyClockViewType) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getIClockView(tinyClockViewType);
        }
        return null;
    }

    public int getMarginAOD() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getMarginAOD();
        }
        return 0;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isAODType() {
        return this.mType == 1;
    }

    public void onAttachedToWindow(int i) {
        if (i % 2 == 0 && i != this.mRotation) {
            setRotation(i);
            changeRotation(this.mRotation);
        }
        if (getType() != 2) {
            if (isAODType()) {
                Task.Companion companion = Task.Companion;
                companion.removeBgTask(this.mRemoveListenerTask);
                companion.postBgTask(this.mAddListenerTask);
            } else {
                this.mAddListenerTask.run();
            }
        }
        updateTimeUnchecked();
        Log.d("TinyMiuiClockController", "onAttachedToWindow: " + this + " ClockBean = " + this.mClockBean + " rotation = " + this.mRotation);
    }

    public void onDetachedFromWindow() {
        if (getType() != 2) {
            if (isAODType()) {
                Task.Companion companion = Task.Companion;
                companion.removeBgTask(this.mAddListenerTask);
                companion.postBgTask(this.mRemoveListenerTask);
            } else {
                this.mRemoveListenerTask.run();
            }
        }
        Log.d("TinyMiuiClockController", "onDetachedFromWindow: " + this + " ClockBean = " + this.mClockBean + " rotation = " + this.mRotation);
    }

    public void refreshClockView() {
        addClockView(this.mClockBean);
    }

    public final void registerTimeListener() {
        if (this.mHasRegisterTimeListener) {
            return;
        }
        synchronized (this) {
            try {
                if (this.mHasRegisterTimeListener) {
                    Log.i("TinyMiuiClockController", "registerTimeListener, has register return");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("registerTimeListener, this = ");
                Object obj = this.mClockBean;
                if (obj == null) {
                    obj = " clockBean=null";
                }
                sb.append(obj);
                Log.i("TinyMiuiClockController", sb.toString());
                this.mHasRegisterTimeListener = true;
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
                if (this.mTimezoneChangeReceiver == null) {
                    this.mTimezoneChangeReceiver = new BroadcastReceiver() { // from class: com.miui.clock.tiny.TinyMiuiClockController.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            TinyMiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.tiny.TinyMiuiClockController.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (TinyMiuiClockController.this.mHasRegisterTimeListener) {
                                        TinyMiuiClockController.this.updateTimeUnchecked();
                                    }
                                }
                            });
                        }
                    };
                }
                Log.d("TinyMiuiClockController", "register " + this.mTimezoneChangeReceiver);
                this.mContext.registerReceiver(this.mTimezoneChangeReceiver, intentFilter);
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.TIME_TICK");
                intentFilter2.addAction("android.intent.action.TIME_SET");
                if (this.mUpdateTimeReceiver == null) {
                    this.mUpdateTimeReceiver = new BroadcastReceiver() { // from class: com.miui.clock.tiny.TinyMiuiClockController.2
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            TinyMiuiClockController.this.mHandler.post(TinyMiuiClockController.this.mUpdateTimeRunnable);
                        }
                    };
                }
                this.mContext.registerReceiver(this.mUpdateTimeReceiver, intentFilter2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeTimeListener() {
        synchronized (this) {
            try {
                if (this.mHasRegisterTimeListener) {
                    this.mHasRegisterTimeListener = false;
                    try {
                        if (this.mTimezoneChangeReceiver != null) {
                            Log.d("TinyMiuiClockController", "unregisterReceiver " + this.mTimezoneChangeReceiver);
                            this.mContext.unregisterReceiver(this.mTimezoneChangeReceiver);
                            this.mTimezoneChangeReceiver = null;
                        }
                        BroadcastReceiver broadcastReceiver = this.mUpdateTimeReceiver;
                        if (broadcastReceiver != null) {
                            this.mContext.unregisterReceiver(broadcastReceiver);
                            this.mUpdateTimeReceiver = null;
                            Log.d("TinyMiuiClockController", "unregisterReceiver " + this.mTimezoneChangeReceiver);
                        }
                    } catch (IllegalArgumentException e) {
                        Log.e("TinyMiuiClockController", "removeTimeListener exception activityDestroy ?" + e.getMessage(), e);
                    }
                    this.mTinyMiuiClockViewListener = null;
                    StringBuilder sb = new StringBuilder();
                    sb.append("removeTimeListener, this = ");
                    Object obj = this.mClockBean;
                    if (obj == null) {
                        obj = " clockBean=null";
                    }
                    sb.append(obj);
                    Log.i("TinyMiuiClockController", sb.toString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setBatteryColor(int i) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setBatteryColor(i);
        }
    }

    public void setBatteryLevel(int i) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setBatteryLevel(i);
        }
    }

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public void setTinyMiuiClockViewListener(ITinyMiuiClockViewListener iTinyMiuiClockViewListener) {
        this.mTinyMiuiClockViewListener = iTinyMiuiClockViewListener;
    }

    public void updateTime() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.updateTime();
        }
    }

    public final void updateTimeUnchecked() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.updateTimeZone(TimeZone.getDefault().getID());
            this.mClockView.updateTime();
        }
    }
}

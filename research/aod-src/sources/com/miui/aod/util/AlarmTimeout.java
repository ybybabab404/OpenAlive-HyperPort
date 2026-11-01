package com.miui.aod.util;

import android.app.AlarmManager;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class AlarmTimeout implements AlarmManager.OnAlarmListener {
    public final AlarmManager mAlarmManager;
    public final Handler mHandler;
    public AlarmManager.OnAlarmListener mListener;
    public boolean mScheduled;
    public final String mTag;

    public AlarmTimeout(AlarmManager alarmManager, AlarmManager.OnAlarmListener onAlarmListener, String str, Handler handler) {
        this.mAlarmManager = alarmManager;
        this.mListener = onAlarmListener;
        this.mTag = str;
        this.mHandler = handler;
    }

    public void cancel() {
        if (this.mScheduled) {
            this.mAlarmManager.cancel(this);
            Log.d("AlarmTimeout", "AlarmTimeout cancel " + this.mTag);
            this.mScheduled = false;
        }
    }

    public boolean isScheduled() {
        return this.mScheduled;
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
        if (this.mScheduled) {
            this.mScheduled = false;
            Log.d("AlarmTimeout", "AlarmTimeout executing " + this.mTag + ", " + this.mListener);
            AlarmManager.OnAlarmListener onAlarmListener = this.mListener;
            if (onAlarmListener != null) {
                onAlarmListener.onAlarm();
            }
        }
    }

    public void release() {
        this.mListener = null;
    }

    public boolean schedule(long j, int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalArgumentException("Illegal mode: " + i);
                }
                if (this.mScheduled) {
                    cancel();
                }
            } else if (this.mScheduled) {
                Log.d("AlarmTimeout", "AlarmTimeout " + this.mTag + " is already scheduled");
                return false;
            }
        } else if (this.mScheduled) {
            throw new IllegalStateException(this.mTag + " timeout is already scheduled");
        }
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, this.mTag, this, this.mHandler);
        Log.d("AlarmTimeout", "AlarmTimeout schedule " + this.mTag + " in " + j);
        this.mScheduled = true;
        return true;
    }
}

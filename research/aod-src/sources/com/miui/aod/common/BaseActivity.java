package com.miui.aod.common;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.miui.aod.Utils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.Calendar;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.LayoutUiModeHelper;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseActivity extends AppCompatActivity {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable mUpdateRunnable = new Runnable() { // from class: com.miui.aod.common.BaseActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            BaseActivity.m216$r8$lambda$_cWn3d8_S1imq2iLjIdQKpGoRw(this.f$0);
        }
    };
    protected WindowInsetsControllerCompat mWindowInsetsController;

    /* JADX INFO: renamed from: $r8$lambda$_cWn3d8_S1imq2iLjIdQKpGo-Rw, reason: not valid java name */
    public static /* synthetic */ void m216$r8$lambda$_cWn3d8_S1imq2iLjIdQKpGoRw(BaseActivity baseActivity) {
        baseActivity.onTimeTick();
        baseActivity.scheduleTimeUpdate();
    }

    private long roundToNextMinute(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.add(12, 1);
        return calendar.getTimeInMillis();
    }

    private void scheduleTimeUpdate() {
        if (this.mHandler.hasCallbacks(this.mUpdateRunnable)) {
            return;
        }
        this.mHandler.postDelayed(this.mUpdateRunnable, roundToNextMinute(System.currentTimeMillis()) - System.currentTimeMillis());
    }

    private void unScheduleTimeUpdate() {
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
    }

    public boolean needHandleUpdateTime() {
        return false;
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (AODSettings.isFullScreenGestureNav(this)) {
            window.addFlags(134217728);
        }
        window.setStatusBarColor(0);
        if (!DeviceUtil.INSTANCE.isPad()) {
            window.setNavigationBarColor(-16777216);
        }
        this.mWindowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        this.mWindowInsetsController.setSystemBarsBehavior(2);
        UserHandleUtils.refresh();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        unScheduleTimeUpdate();
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (Utils.reStartActivityMultiWindow(this)) {
            return;
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (needHandleUpdateTime()) {
            onTimeTick();
            scheduleTimeUpdate();
        }
    }

    public void onTimeTick() {
    }

    public boolean useEventBus() {
        return false;
    }
}

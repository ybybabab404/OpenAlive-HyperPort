package com.miui.aod.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.WindowManager;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.superwallpaper.SuperWallpaperReceiver;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.AppCompatActivity;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class RouterActivity extends AppCompatActivity {
    public String mPageSource;

    public interface RouterEvent {
        default boolean executeRouter() {
            return false;
        }
    }

    public static /* synthetic */ void $r8$lambda$9kYKVAfk93iM1XhY8waw6X0NiQc(RouterActivity routerActivity, RouterEvent routerEvent, DialogInterface dialogInterface) {
        routerActivity.getClass();
        if (routerEvent.executeRouter() || routerActivity.isFinishing()) {
            return;
        }
        routerActivity.finish();
    }

    public static /* synthetic */ void $r8$lambda$KlSQFpPK3eVbc9LG064WDHrkmJw(RouterActivity routerActivity, DialogInterface dialogInterface, int i) {
        routerActivity.setResult(0);
        routerActivity.finish();
    }

    /* JADX INFO: renamed from: $r8$lambda$afijs-cQv8C5lfzZcMoNJPa0WxE, reason: not valid java name */
    public static /* synthetic */ void m477$r8$lambda$afijscQv8C5lfzZcMoNJPa0WxE(RouterActivity routerActivity, DialogInterface dialogInterface, int i) {
        routerActivity.getClass();
        Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        intent.putExtra("extra_tab_position", 1);
        routerActivity.startActivity(intent);
    }

    public static /* synthetic */ void $r8$lambda$ePoQyIECUy06fpEl5PpB8GwSn_k(RouterActivity routerActivity, DialogInterface dialogInterface, int i) {
        Utils.setAodEnable(routerActivity.getApplicationContext(), true);
        Utils.setAodModeUserSet(routerActivity.getApplicationContext(), true);
        routerActivity.setResult(-1);
        Utils.showSuccessToast(routerActivity);
    }

    public static /* synthetic */ void $r8$lambda$oIG28zCTqeNe381yl9CB8xJJzFw(RouterActivity routerActivity, DialogInterface dialogInterface, int i) {
        routerActivity.getClass();
        routerActivity.startActivity(new Intent(routerActivity, (Class<?>) AodShowModeSettingActivity.class));
    }

    public static String createScheduledDialogMsg(Context context) {
        Calendar calendar = new Calendar();
        String string = DateFormat.is24HourFormat(context) ? context.getResources().getString(R.string.scheduled_mode_dialog_msg_24) : context.getResources().getString(R.string.scheduled_mode_dialog_msg_12);
        long aodStartTime = Utils.getAodStartTime(context);
        long aodEndTime = Utils.getAodEndTime(context);
        calendar.set(18, (int) (aodStartTime / 3600000));
        calendar.set(20, (int) ((aodStartTime % 3600000) / 60000));
        String str = calendar.format(context, string);
        calendar.set(18, (int) (aodEndTime / 3600000));
        calendar.set(20, (int) ((aodEndTime % 3600000) / 60000));
        return context.getResources().getString(R.string.scheduled_mode_dialog_msg, str + '-' + calendar.format(context, string));
    }

    public static void tryToEnableAod(Context context, String str) {
        if (!Utils.isAodEnable(context) || Utils.aodDisabledForScheduleMode(context)) {
            Intent intent = new Intent(context, (Class<?>) RouterActivity.class);
            intent.putExtra("intent_target", "try_enable_aod");
            intent.putExtra("page_source", str);
            intent.addFlags(268435456);
            intent.addFlags(32768);
            context.startActivity(intent);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Uri data;
        super.onCreate(bundle);
        AodStyleCategoriesActivity.updateActivityTitle(this);
        final Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        this.mPageSource = intent.getStringExtra("page_source");
        Log.i("RouterActivity", "onCreate: pageSource " + this.mPageSource);
        String stringExtra = intent.getStringExtra("intent_target");
        if ("style_list".equals(stringExtra)) {
            toAodStyleList();
            finish();
            return;
        }
        if ("style_select".equals(stringExtra)) {
            if (!Utils.isAodEnable(this)) {
                tryEnableAodAndThenExecuteRouter(new RouterEvent() { // from class: com.miui.aod.settings.RouterActivity.1
                    @Override // com.miui.aod.settings.RouterActivity.RouterEvent
                    public boolean executeRouter() {
                        if (!Utils.isAodEnable(RouterActivity.this)) {
                            return false;
                        }
                        SuperWallpaperReceiver.dealWithIntentForSuperWallpaperIfNeeded(intent);
                        RouterActivity.this.toAodStyleSelect();
                        return false;
                    }
                });
                return;
            } else {
                toAodStyleSelect();
                finish();
                return;
            }
        }
        if ("try_enable_aod".equals(stringExtra)) {
            tryEnableAodAndThenExecuteRouter(new RouterEvent() { // from class: com.miui.aod.settings.RouterActivity.2
                @Override // com.miui.aod.settings.RouterActivity.RouterEvent
                public boolean executeRouter() {
                    if (!Utils.isAodEnable(RouterActivity.this)) {
                        return false;
                    }
                    RouterActivity routerActivity = RouterActivity.this;
                    return routerActivity.showScheduleModeDialogIfNeeded(routerActivity);
                }
            });
            return;
        }
        if ("android.intent.action.VIEW".equals(getIntent().getAction()) && (data = getIntent().getData()) != null && "com.miui.aod".equals(data.getHost()) && "/style_select/custom_image".equals(data.getPath())) {
            AodStyleSelectActivity.start(this, ImageSelectorCategoryInfo.createStyleInfo(data.getQueryParameter("path")), this.mPageSource);
        }
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        finish();
    }

    public final boolean showScheduleModeDialogIfNeeded(Context context) {
        if (!Utils.aodDisabledForScheduleMode(context)) {
            finish();
            return false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton(R.string.btn_know, new DialogInterface.OnClickListener() { // from class: com.miui.aod.settings.RouterActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.finish();
            }
        });
        builder.setMessage(createScheduledDialogMsg(context));
        builder.setPositiveButton(R.string.modify_scheduled_time, new DialogInterface.OnClickListener() { // from class: com.miui.aod.settings.RouterActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                RouterActivity.$r8$lambda$oIG28zCTqeNe381yl9CB8xJJzFw(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.aod.settings.RouterActivity$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.finish();
            }
        });
        try {
            if (isFinishing()) {
                return true;
            }
            alertDialogCreate.show();
            return true;
        } catch (WindowManager.BadTokenException e) {
            Log.wtf("RouterActivity", "The Activity finished cause show dialog error", e);
            return true;
        }
    }

    public final void toAodStyleList() {
        Intent intent = new Intent(this, (Class<?>) AodStyleCategoriesActivity.class);
        intent.putExtra("page_source", this.mPageSource);
        startActivity(intent);
    }

    public final void toAodStyleSelect() {
        Intent intent = new Intent(this, (Class<?>) AodStyleSelectActivity.class);
        intent.putExtra("page_source", this.mPageSource);
        startActivity(intent);
    }

    public final void tryEnableAodAndThenExecuteRouter(final RouterEvent routerEvent) {
        if (routerEvent == null) {
            finish();
            return;
        }
        if (Utils.isAodEnable(getApplicationContext())) {
            setResult(-1);
            if (routerEvent.executeRouter() || isFinishing()) {
                return;
            }
            finish();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog_Theme_Dark);
        boolean zIsInvertColorsEnable = Utils.isInvertColorsEnable(this);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.aod.settings.RouterActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                RouterActivity.$r8$lambda$KlSQFpPK3eVbc9LG064WDHrkmJw(this.f$0, dialogInterface, i);
            }
        });
        if (zIsInvertColorsEnable) {
            builder.setMessage(R.string.aod_close_color_inversion);
            builder.setPositiveButton(R.string.aod_to_close, new DialogInterface.OnClickListener() { // from class: com.miui.aod.settings.RouterActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    RouterActivity.m477$r8$lambda$afijscQv8C5lfzZcMoNJPa0WxE(this.f$0, dialogInterface, i);
                }
            });
        } else {
            builder.setMessage(R.string.tip_to_open_aod_mode);
            builder.setPositiveButton(R.string.open_aod_mode, new DialogInterface.OnClickListener() { // from class: com.miui.aod.settings.RouterActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    RouterActivity.$r8$lambda$ePoQyIECUy06fpEl5PpB8GwSn_k(this.f$0, dialogInterface, i);
                }
            });
        }
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.aod.settings.RouterActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                RouterActivity.$r8$lambda$9kYKVAfk93iM1XhY8waw6X0NiQc(this.f$0, routerEvent, dialogInterface);
            }
        });
        alertDialogCreate.show();
    }
}

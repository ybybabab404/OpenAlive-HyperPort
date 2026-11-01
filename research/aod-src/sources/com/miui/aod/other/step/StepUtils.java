package com.miui.aod.other.step;

import android.content.ActivityNotFoundException;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.AndroidRuntimeException;
import android.util.Log;
import com.miui.aod.R;
import com.xiaomi.onetrack.g.a;
import java.net.URISyntaxException;
import miui.os.Build;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes.dex */
public class StepUtils {
    public static /* synthetic */ void $r8$lambda$Znbh5ql06btHWPv89FnonYubLrA(DialogInterface dialogInterface, int i) {
    }

    public static /* synthetic */ void $r8$lambda$yNy6r_yNMpw1kty1cYtJnAmm9QU(DialogInterface dialogInterface, int i) {
    }

    public static void dealWithActiveComponent(Context context, final Runnable runnable, final Runnable runnable2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.step_component_info_active_tip);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.aod.other.step.StepUtils$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                StepUtils.$r8$lambda$yNy6r_yNMpw1kty1cYtJnAmm9QU(dialogInterface, i);
            }
        });
        builder.setPositiveButton(R.string.continue_to_use, new DialogInterface.OnClickListener() { // from class: com.miui.aod.other.step.StepUtils$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                runnable.run();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.aod.other.step.StepUtils$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                runnable2.run();
            }
        });
        alertDialogCreate.show();
    }

    public static void dealWithEnableStepComponent(Context context, final Runnable runnable, final Runnable runnable2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.step_component_info_tip);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.aod.other.step.StepUtils$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                StepUtils.$r8$lambda$Znbh5ql06btHWPv89FnonYubLrA(dialogInterface, i);
            }
        });
        builder.setPositiveButton(R.string.continue_to_use, new DialogInterface.OnClickListener() { // from class: com.miui.aod.other.step.StepUtils$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                runnable.run();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.aod.other.step.StepUtils$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                runnable2.run();
            }
        });
        alertDialogCreate.show();
    }

    public static boolean hasAchieveTarget(StepBean stepBean, GoalLiveData.GoalBean goalBean) {
        if (stepBean != null && goalBean != null && "0".equals(stepBean.code)) {
            try {
                int i = Integer.parseInt(stepBean.content);
                int i2 = goalBean.goal;
                if (i2 >= 0 && i >= i2) {
                    return true;
                }
            } catch (NumberFormatException e) {
                Log.e("StepUtils", "error...", e);
            }
        }
        return false;
    }

    public static boolean isHealthProviderExist(Context context) {
        try {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = context.getContentResolver().acquireContentProviderClient("com.mi.health.provider.main");
            boolean z = contentProviderClientAcquireContentProviderClient != null;
            if (contentProviderClientAcquireContentProviderClient == null) {
                return z;
            }
            contentProviderClientAcquireContentProviderClient.close();
            return z;
        } catch (Exception e) {
            Log.e("StepUtils", "isHealthProviderExist: ", e);
            return false;
        }
    }

    public static boolean isStepPathValid(Context context) {
        String type = context.getContentResolver().getType(Uri.parse("content://com.mi.health.provider.main/widget/steps/simple"));
        Log.i("StepUtils", "checkPathValid: com.mi.health.provider.main/widget/steps/simple");
        return type != null;
    }

    public static StepBean queryStep(Context context) {
        Exception exc;
        StepBean stepBeanCreateStepBean = null;
        if (!isHealthProviderExist(context)) {
            return null;
        }
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.mi.health.provider.main/widget/steps/simple"), new String[]{a.d, "title", "content", "unit", "icon", "setup_uri", "privacy_grant_uri"}, null, null, null);
            try {
                if (cursorQuery != null) {
                    stepBeanCreateStepBean = StepBean.createStepBean(cursorQuery);
                    Log.i("StepUtils", "queryStep: " + stepBeanCreateStepBean.toBeanString());
                } else {
                    Log.e("StepUtils", "queryStep: cursor == null");
                }
                if (cursorQuery == null) {
                    return stepBeanCreateStepBean;
                }
                cursorQuery.close();
                return stepBeanCreateStepBean;
            } catch (Throwable th) {
                StepBean stepBean = stepBeanCreateStepBean;
                try {
                    if (cursorQuery == null) {
                        throw th;
                    }
                    try {
                        cursorQuery.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                } catch (Exception e) {
                    exc = e;
                    stepBeanCreateStepBean = stepBean;
                }
                exc = e;
                stepBeanCreateStepBean = stepBean;
            }
        } catch (Exception e2) {
            exc = e2;
        }
        Log.e("StepUtils", "queryStep: ", exc);
        return stepBeanCreateStepBean;
    }

    public static GoalLiveData.GoalBean queryStepGoal(Context context) {
        Exception exc;
        GoalLiveData.GoalBean goalBeanCreateGoalBean = null;
        if (!isHealthProviderExist(context)) {
            return null;
        }
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.mi.health.provider.main/widget/steps/goal"), new String[]{"goal"}, null, null, null);
            try {
                if (cursorQuery != null) {
                    goalBeanCreateGoalBean = GoalLiveData.GoalBean.createGoalBean(cursorQuery);
                    StringBuilder sb = new StringBuilder();
                    sb.append("queryStepGoal: ");
                    sb.append(goalBeanCreateGoalBean == null ? "null" : Integer.valueOf(goalBeanCreateGoalBean.goal));
                    Log.i("StepUtils", sb.toString());
                } else {
                    Log.e("StepUtils", "queryStepGoal: cursor == null");
                }
                if (cursorQuery == null) {
                    return goalBeanCreateGoalBean;
                }
                cursorQuery.close();
                return goalBeanCreateGoalBean;
            } catch (Throwable th) {
                if (cursorQuery == null) {
                    throw th;
                }
                try {
                    try {
                        cursorQuery.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                } catch (Exception e) {
                    exc = e;
                    goalBeanCreateGoalBean = null;
                }
                exc = e;
                goalBeanCreateGoalBean = null;
            }
        } catch (Exception e2) {
            exc = e2;
        }
        Log.e("StepUtils", "queryStepGoal: ", exc);
        return goalBeanCreateGoalBean;
    }

    public static void registerContentObserver(Context context, ContentObserver contentObserver, String str) {
        if (isHealthProviderExist(context)) {
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.mi.health.provider.main" + str), false, contentObserver);
        }
    }

    public static void startSetupUri(Context context, StepBean stepBean) {
        if (stepActive(stepBean) || stepBean == null) {
            return;
        }
        try {
            String str = stepBean.privacy_grant_uri;
            if (str != null) {
                Intent uri = Intent.parseUri(str, 2);
                uri.addFlags(268435456);
                context.startActivity(uri);
            }
        } catch (ActivityNotFoundException | AndroidRuntimeException | URISyntaxException e) {
            Log.e("StepUtils", "error...", e);
        }
    }

    public static void startToInstall(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.mi.health"));
            intent.setPackage("com.xiaomi.market");
            intent.addFlags(268435456);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("StepUtils", "error...", e);
        }
    }

    public static boolean stepActive(StepBean stepBean) {
        return stepBean != null && "0".equals(stepBean.code);
    }

    public static boolean supportStepComponent() {
        return !Build.IS_INTERNATIONAL_BUILD;
    }
}

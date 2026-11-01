package com.miui.aod.settings;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentTransaction;
import com.miui.aod.common.BaseActivity;

/* JADX INFO: loaded from: classes.dex */
public class AodShowModeSettingActivity extends BaseActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, (Class<?>) AodShowModeSettingActivity.class));
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(injectContext(context));
    }

    public final Context injectContext(Context context) {
        return context.createDeviceProtectedStorageContext();
    }

    @Override // com.miui.aod.common.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        window.setAttributes(attributes);
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.content, ShowModeFragment.newInstance());
        fragmentTransactionBeginTransaction.addToBackStack(null);
        fragmentTransactionBeginTransaction.commit();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) { // from class: com.miui.aod.settings.AodShowModeSettingActivity.1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                AodShowModeSettingActivity.this.finish();
            }
        });
    }
}

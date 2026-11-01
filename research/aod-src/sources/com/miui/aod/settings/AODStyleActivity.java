package com.miui.aod.settings;

import android.content.Intent;
import android.os.Bundle;
import com.miui.aod.Utils;
import miuix.appcompat.app.AppCompatActivity;

/* JADX INFO: loaded from: classes.dex */
public class AODStyleActivity extends AppCompatActivity {
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AodStyleCategoriesActivity.updateActivityTitle(this);
        Intent intent = new Intent(this, (Class<?>) AodStyleCategoriesActivity.class);
        if (Utils.inLargeScreen(this) || Utils.isFoldDevice()) {
            intent.addFlags(268435456);
        }
        startActivity(intent);
        finish();
    }
}

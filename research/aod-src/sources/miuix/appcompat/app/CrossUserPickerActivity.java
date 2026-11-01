package miuix.appcompat.app;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import miui.securityspace.CrossUserUtils;
import miuix.core.compat.ContextCompat;
import miuix.core.compat.UserHandleCompat;
import miuix.core.util.PackageHelper;

/* JADX INFO: loaded from: classes2.dex */
public class CrossUserPickerActivity extends AppCompatActivity {
    public volatile ContentResolver mCrossUserContentResolver;
    public volatile ContextWrapper mCrossUserContextWrapper;
    public final Object mLockObject = new Object();

    public class CrossUserContextWrapper extends ContextWrapper {
        public Context mBase;
        public UserHandle mCrossUser;

        public CrossUserContextWrapper(Context context, UserHandle userHandle) {
            super(context);
            this.mBase = context;
            this.mCrossUser = userHandle;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public ContentResolver getContentResolver() {
            return ContextCompat.getContentResolverForUser(this.mBase, this.mCrossUser);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context getApplicationContext() {
        if (!isCrossUserPick() || !PackageHelper.isMiuiSystem()) {
            Log.d("CrossUserPickerActivity", "getApplicationContext: NormalApplication");
            return super.getApplicationContext();
        }
        if (this.mCrossUserContextWrapper == null) {
            synchronized (this.mLockObject) {
                try {
                    if (this.mCrossUserContextWrapper == null) {
                        this.mCrossUserContextWrapper = new CrossUserContextWrapper(super.getApplicationContext(), UserHandleCompat.createNew(validateCrossUser()));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Log.d("CrossUserPickerActivity", "getApplicationContext: WrapperedApplication");
        return this.mCrossUserContextWrapper;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public ContentResolver getContentResolver() {
        if (!isCrossUserPick() || !PackageHelper.isMiuiSystem()) {
            Log.d("CrossUserPickerActivity", "getContentResolver: NormalContentResolver");
            return super.getContentResolver();
        }
        if (this.mCrossUserContentResolver == null) {
            synchronized (this.mLockObject) {
                try {
                    if (this.mCrossUserContentResolver == null) {
                        this.mCrossUserContentResolver = ContextCompat.getContentResolverForUser(this, UserHandleCompat.createNew(validateCrossUser()));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Log.d("CrossUserPickerActivity", "getContentResolver: CrossUserContentResolver");
        return this.mCrossUserContentResolver;
    }

    public boolean isCrossUserPick() {
        return validateCrossUser() != -1;
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        if (isCrossUserPick()) {
            intent.putExtra("android.intent.extra.picked_user_id", validateCrossUser());
        }
        super.startActivity(intent);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent, Bundle bundle) {
        if (isCrossUserPick()) {
            intent.putExtra("android.intent.extra.picked_user_id", validateCrossUser());
        }
        super.startActivity(intent, bundle);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i) {
        if (isCrossUserPick()) {
            intent.putExtra("android.intent.extra.picked_user_id", validateCrossUser());
        }
        super.startActivityForResult(intent, i);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (isCrossUserPick()) {
            intent.putExtra("android.intent.extra.picked_user_id", validateCrossUser());
        }
        super.startActivityForResult(intent, i, bundle);
    }

    @Override // android.app.Activity
    public void startActivityFromFragment(android.app.Fragment fragment, Intent intent, int i, Bundle bundle) {
        if (isCrossUserPick()) {
            intent.putExtra("android.intent.extra.picked_user_id", validateCrossUser());
        }
        super.startActivityFromFragment(fragment, intent, i, bundle);
    }

    public final boolean validateCallingPackage() {
        return getPackageName().equals(getCallingPackage()) || CrossUserUtils.checkUidPermission(this, getCallingPackage());
    }

    public final int validateCrossUser() {
        if (getIntent() == null) {
            return -1;
        }
        int intExtra = getIntent().getIntExtra("android.intent.extra.picked_user_id", -1);
        if (validateCallingPackage()) {
            return intExtra;
        }
        return -1;
    }
}

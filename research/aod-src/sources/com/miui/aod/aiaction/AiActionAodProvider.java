package com.miui.aod.aiaction;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import com.miui.aod.Utils;
import com.xiaomi.onetrack.api.am;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AiActionAodProvider.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AiActionAodProvider extends ContentProvider {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: AiActionAodProvider.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final void asyncNotifyResult(Context context, Bundle input, int i) {
        String string;
        Intrinsics.checkNotNullParameter(input, "input");
        String string2 = input.getString("action_request_id");
        String string3 = input.getString("action_callback_uri");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(am.M, i);
            if (Intrinsics.areEqual("urn:aiot-spec-v3:com.mi.phones:action:[com.miui.aod/aod/getaodstatus]:0:1.0", input.getString("type"))) {
                jSONObject.put(am.O, getAodStatus());
            }
            string = jSONObject.toString();
        } catch (JSONException e) {
            Log.e("AiActionAodProvider", "JSON exception when asyncNotifyResult:", e);
            string = null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("target_code", 0);
        bundle.putString("target_response_id", string2);
        bundle.putString("target_out", string);
        Uri uri = Uri.parse(string3);
        ContentResolver contentResolver = context != null ? context.getContentResolver() : null;
        if (contentResolver != null) {
            contentResolver.call(uri, "action_result", (String) null, bundle);
        }
    }

    @Override // android.content.ContentProvider
    public Bundle call(String method, String str, Bundle bundle) {
        int iDoAction;
        Intrinsics.checkNotNullParameter(method, "method");
        if (bundle == null) {
            Log.e("AiActionAodProvider", "extras is null");
            return null;
        }
        if (!checkPermissions(getContext())) {
            Log.e("AiActionAodProvider", "error: checkPermissions failed");
            return null;
        }
        String string = bundle.getString("type");
        String string2 = bundle.getString("in");
        if (Utils.supportAod(getContext())) {
            iDoAction = Intrinsics.areEqual("execute_action", method) ? doAction(string, string2) : -1;
        } else {
            iDoAction = -11;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            if (Intrinsics.areEqual("urn:aiot-spec-v3:com.mi.phones:action:[com.miui.aod/aod/getaodstatus]:0:1.0", string)) {
                jSONObject.put(am.O, getAodStatus());
            }
            jSONObject.put(am.M, iDoAction);
        } catch (JSONException e) {
            Log.e("AiActionAodProvider", "JSON exception when call:" + e.getMessage());
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("target_code", 0);
        bundle2.putString("target_out", jSONObject.toString());
        asyncNotifyResult(getContext(), bundle, iDoAction);
        return bundle2;
    }

    public final boolean checkPermissions(Context context) {
        int callingUid = Binder.getCallingUid();
        String str = null;
        PackageManager packageManager = context != null ? context.getPackageManager() : null;
        String[] packagesForUid = packageManager != null ? packageManager.getPackagesForUid(callingUid) : null;
        if (packagesForUid != null && packagesForUid.length > 0) {
            str = packagesForUid[0];
        }
        return Intrinsics.areEqual("com.xiaomi.aicr", str) && context != null && context.checkCallingPermission("hyperos.permission.READ_AIACTION") == 0;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    public final int doAction(String str, String str2) {
        Log.d("AiActionAodProvider", "doAction: type:" + str);
        if (str == null) {
            return -1;
        }
        int iHashCode = str.hashCode();
        if (iHashCode == 238106252) {
            if (str.equals("urn:aiot-spec-v3:com.mi.phones:action:[com.miui.aod/aod/setaodstatus]:0:1.0")) {
                return setAodStatus(str2);
            }
            return -1;
        }
        if (iHashCode == 1781834112) {
            return !str.equals("urn:aiot-spec-v3:com.mi.phones:action:[com.miui.aod/aod/getaodstatus]:0:1.0") ? -1 : 0;
        }
        if (iHashCode == 2033251159 && str.equals("urn:aiot-spec-v3:com.mi.phones:action:[com.miui.aod/aod/openaodsettings]:0:1.0")) {
            return openAodSettingsActivity();
        }
        return -1;
    }

    public final int getAodStatus() {
        return Utils.isAodSettingsEnabled(getContext()) ? 1 : 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return "";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    public final boolean isInAodSettingsActivity() {
        Context context = getContext();
        Object systemService = context != null ? context.getSystemService("activity") : null;
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) systemService).getRunningTasks(1);
        if (runningTasks.isEmpty()) {
            return false;
        }
        ComponentName componentName = runningTasks.get(0).topActivity;
        return Intrinsics.areEqual("com.miui.aod.settings.AodStyleCategoriesActivity", componentName != null ? componentName.getClassName() : null);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return false;
    }

    public final int openAodSettingsActivity() {
        if (isInAodSettingsActivity()) {
            return -10;
        }
        try {
            Intent intent = new Intent();
            intent.setClassName("com.miui.aod", "com.miui.aod.settings.AodStyleCategoriesActivity");
            intent.addFlags(335544320);
            Context context = getContext();
            if (context == null) {
                return 0;
            }
            context.startActivity(intent);
            return 0;
        } catch (Exception e) {
            Log.e("AiActionAodProvider", "occur exception when openTargetActivity:", e);
            return -1;
        }
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    public final int setAodStatus(String str) {
        if (str == null) {
            return -1;
        }
        try {
            boolean z = new JSONObject(str).getBoolean("enable");
            if (Utils.isAodSettingsEnabled(getContext()) == z) {
                return -10;
            }
            Utils.setAodEnable(getContext(), z);
            Utils.setAodModeUserSet(getContext(), z);
            return 0;
        } catch (Exception e) {
            Log.e("AiActionAodProvider", "occur exception when setAodStatus:", e);
            return -1;
        }
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }
}

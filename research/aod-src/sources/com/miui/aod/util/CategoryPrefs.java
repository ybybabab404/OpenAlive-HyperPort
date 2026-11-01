package com.miui.aod.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.miui.aod.Utils;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.part.PartProvider;
import com.miui.aod.utils.ContentProviderUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.tencent.mmkv.MMKV;
import com.xiaomi.onetrack.api.am;

/* JADX INFO: loaded from: classes.dex */
public class CategoryPrefs {
    public static final String TAG = "CategoryPrefs";

    public interface id {
    }

    public static SharedPreferences get(Context context) {
        return MMKV.mmkvWithID(BaseStyleSelectPresenter.KEY_CATE, 2);
    }

    public static int getInt(Context context, String str, int i) {
        return get(context).getInt(str, i);
    }

    public static String getString(Context context, String str, String str2) {
        if (UserHandleUtils.USER_CURRENT == 0 || Utils.isAodProcess()) {
            return get(context).getString(str, str2);
        }
        Bundle bundleCall = context.getContentResolver().call(ContentProviderUtils.maybeAddUserId(PartProvider.URI_PART, UserHandleUtils.USER_CURRENT), "getString", str, (Bundle) null);
        return bundleCall == null ? str2 : bundleCall.getString("getString", str2);
    }

    public static void putInt(Context context, String str, int i) {
        get(context).edit().putInt(str, i).apply();
    }

    public static void putString(Context context, String str, String str2) {
        Log.i(TAG, "key=" + str + "; value=" + str2 + "; UserHandleUtils.USER_CURRENT=" + UserHandleUtils.USER_CURRENT + "; Utils.isAodProcess()=" + Utils.isAodProcess());
        if (Utils.isAodProcess()) {
            get(context).edit().putString(str, str2).commit();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        bundle.putString(am.t, str2);
        context.getContentResolver().call(ContentProviderUtils.maybeAddUserId(PartProvider.URI_PART, UserHandleUtils.USER_CURRENT), "putString", (String) null, bundle);
    }
}

package com.miui.aod.icu;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class GetZoneNameTask {
    public static final String TAG = "GetZoneNameTask";

    public interface OnGetZoneNameCompleteListener {
        void onComplete(String str);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0066 A[Catch: Exception -> 0x006a, TRY_ENTER, TRY_LEAVE, TryCatch #1 {Exception -> 0x006a, blocks: (B:3:0x0007, B:29:0x0078, B:28:0x0075, B:19:0x0066, B:24:0x006f, B:5:0x0019, B:7:0x001f, B:9:0x0025, B:12:0x0031, B:14:0x0037, B:16:0x003d, B:17:0x0046), top: B:35:0x0007, inners: #0, #2 }] */
    /* JADX INFO: renamed from: $r8$lambda$xSVQuCMW1SccltkP-haijyRuC4c, reason: not valid java name */
    public static /* synthetic */ void m420$r8$lambda$xSVQuCMW1SccltkPhaijyRuC4c(Context context, String str, SingleEmitter singleEmitter) {
        String string = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse(str), null, null, null, null);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    try {
                        if (cursorQuery.getColumnIndex("zone_timename") >= 0) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex("zone_timename"));
                        }
                        if (TextUtils.isEmpty(string) && cursorQuery.getColumnIndex("zone_displayname") >= 0) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex("zone_displayname"));
                        }
                    } catch (Throwable th) {
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
                    }
                }
                Log.i(TAG, "get city name: " + string + ", from: " + str);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } else {
                Log.i(TAG, "get city name: " + string + ", from: " + str);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "get city from " + str + " exception", e);
        }
        singleEmitter.onSuccess(string);
    }

    public static void doTask(final OnGetZoneNameCompleteListener onGetZoneNameCompleteListener, final Context context, final String str) {
        Single.create(new SingleOnSubscribe() { // from class: com.miui.aod.icu.GetZoneNameTask$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.SingleOnSubscribe
            public final void subscribe(SingleEmitter singleEmitter) {
                GetZoneNameTask.m420$r8$lambda$xSVQuCMW1SccltkPhaijyRuC4c(context, str, singleEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() { // from class: com.miui.aod.icu.GetZoneNameTask.1
            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(String str2) {
                onGetZoneNameCompleteListener.onComplete(str2);
            }
        });
    }
}

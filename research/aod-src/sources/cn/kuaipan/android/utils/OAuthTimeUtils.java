package cn.kuaipan.android.utils;

import android.os.SystemClock;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class OAuthTimeUtils {
    public static final SimpleDateFormat sFormat;
    public static final StringBuffer sFormatBuffer;
    public static final FieldPosition sFormatFieldPos;
    public static final ParsePosition sFormatPosition;
    public static long sTimeDiff;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sFormat = simpleDateFormat;
        sFormatPosition = new ParsePosition(0);
        sFormatBuffer = new StringBuffer();
        sFormatFieldPos = new FieldPosition(0);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0800"));
    }

    public static long currentTime() {
        return sTimeDiff == 0 ? System.currentTimeMillis() : SystemClock.elapsedRealtime() + sTimeDiff;
    }
}

package miuix.theme;

import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class Typography {
    public static Typeface MISANS_LIGHT;
    public static Typeface MISANS_MEDIUM;

    public static void applyMiSansLight(TextView textView) {
        if (textView == null) {
            return;
        }
        try {
            if (MISANS_LIGHT == null) {
                MISANS_LIGHT = Typeface.create("sans-serif-light", 0);
            }
            textView.setTypeface(MISANS_LIGHT);
        } catch (Exception e) {
            Log.w("Typography", "Failed to set [sans-serif-medium normal] typeface!", e);
            textView.setTypeface(Typeface.DEFAULT);
        }
    }

    public static void applyMiSansMedium(TextView textView) {
        if (textView == null) {
            return;
        }
        try {
            if (MISANS_MEDIUM == null) {
                MISANS_MEDIUM = Typeface.create("sans-serif-medium", 0);
            }
            textView.setTypeface(MISANS_MEDIUM);
        } catch (Exception e) {
            Log.w("Typography", "Failed to set [sans-serif-medium normal] typeface!", e);
            textView.setTypeface(Typeface.DEFAULT);
        }
    }
}

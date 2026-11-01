package androidx.core.app;

import android.app.GrammaticalInflectionManager;
import android.content.Context;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public final class GrammaticalInflectionManagerCompat {

    @RequiresApi
    public static class Api34Impl {
        @DoNotInline
        public static int getApplicationGrammaticalGender(Context context) {
            return getGrammaticalInflectionManager(context).getApplicationGrammaticalGender();
        }

        public static GrammaticalInflectionManager getGrammaticalInflectionManager(Context context) {
            return (GrammaticalInflectionManager) context.getSystemService(GrammaticalInflectionManager.class);
        }

        @DoNotInline
        public static void setRequestedApplicationGrammaticalGender(Context context, int i) {
            getGrammaticalInflectionManager(context).setRequestedApplicationGrammaticalGender(i);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface GrammaticalGender {
    }
}

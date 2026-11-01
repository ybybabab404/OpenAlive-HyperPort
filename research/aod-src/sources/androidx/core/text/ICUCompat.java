package androidx.core.text;

import android.icu.util.ULocale;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class ICUCompat {

    @RequiresApi
    public static class Api21Impl {
        @DoNotInline
        public static String getScript(Locale locale) {
            return locale.getScript();
        }
    }

    @RequiresApi
    public static class Api24Impl {
        @DoNotInline
        public static ULocale addLikelySubtags(Object obj) {
            return ULocale.addLikelySubtags((ULocale) obj);
        }

        @DoNotInline
        public static ULocale forLocale(Locale locale) {
            return ULocale.forLocale(locale);
        }

        @DoNotInline
        public static String getScript(Object obj) {
            return ((ULocale) obj).getScript();
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        return Api24Impl.getScript(Api24Impl.addLikelySubtags(Api24Impl.forLocale(locale)));
    }
}

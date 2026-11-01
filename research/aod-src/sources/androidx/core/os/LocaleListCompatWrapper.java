package androidx.core.os;

import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import com.xiaomi.onetrack.h.d.a;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
final class LocaleListCompatWrapper implements LocaleListInterface {
    public final Locale[] mList;
    public final String mStringRepresentation;
    public static final Locale[] sEmptyList = new Locale[0];
    public static final Locale LOCALE_EN_XA = new Locale(a.r, "XA");
    public static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
    public static final Locale EN_LATN = LocaleListCompat.forLanguageTagCompat("en-Latn");

    @RequiresApi
    public static class Api21Impl {
        @DoNotInline
        public static String getScript(Locale locale) {
            return locale.getScript();
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocaleListCompatWrapper)) {
            return false;
        }
        Locale[] localeArr = ((LocaleListCompatWrapper) obj).mList;
        if (this.mList.length != localeArr.length) {
            return false;
        }
        int i = 0;
        while (true) {
            Locale[] localeArr2 = this.mList;
            if (i >= localeArr2.length) {
                return true;
            }
            if (!localeArr2[i].equals(localeArr[i])) {
                return false;
            }
            i++;
        }
    }

    @Override // androidx.core.os.LocaleListInterface
    public Object getLocaleList() {
        return null;
    }

    public int hashCode() {
        int iHashCode = 1;
        for (Locale locale : this.mList) {
            iHashCode = (iHashCode * 31) + locale.hashCode();
        }
        return iHashCode;
    }

    @Override // androidx.core.os.LocaleListInterface
    public boolean isEmpty() {
        return this.mList.length == 0;
    }

    @Override // androidx.core.os.LocaleListInterface
    public String toLanguageTags() {
        return this.mStringRepresentation;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i >= localeArr.length) {
                sb.append("]");
                return sb.toString();
            }
            sb.append(localeArr[i]);
            if (i < this.mList.length - 1) {
                sb.append(',');
            }
            i++;
        }
    }
}

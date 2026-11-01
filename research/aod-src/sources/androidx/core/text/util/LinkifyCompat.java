package androidx.core.text.util;

import android.text.Spannable;
import android.text.util.Linkify;
import android.widget.TextView;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class LinkifyCompat {
    public static final String[] EMPTY_STRING = new String[0];
    public static final Comparator COMPARATOR = new Comparator() { // from class: androidx.core.text.util.LinkifyCompat$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return LinkifyCompat.$r8$lambda$hMER0Rgi4iIgWDuRpnB13UAFJfk((LinkifyCompat.LinkSpec) obj, (LinkifyCompat.LinkSpec) obj2);
        }
    };

    @RequiresApi
    public static class Api24Impl {
        @DoNotInline
        public static void addLinks(TextView textView, Pattern pattern, String str, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
            Linkify.addLinks(textView, pattern, str, strArr, matchFilter, transformFilter);
        }

        @DoNotInline
        public static boolean addLinks(Spannable spannable, Pattern pattern, String str, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
            return Linkify.addLinks(spannable, pattern, str, strArr, matchFilter, transformFilter);
        }
    }

    public static class LinkSpec {
        public int end;
        public int start;
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface LinkifyMask {
    }

    public static /* synthetic */ int $r8$lambda$hMER0Rgi4iIgWDuRpnB13UAFJfk(LinkSpec linkSpec, LinkSpec linkSpec2) {
        int i = linkSpec.start;
        int i2 = linkSpec2.start;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        return Integer.compare(linkSpec2.end, linkSpec.end);
    }
}

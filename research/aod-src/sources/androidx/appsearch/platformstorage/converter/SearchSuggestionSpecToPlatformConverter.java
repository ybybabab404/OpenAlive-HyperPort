package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.SearchSuggestionSpec;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class SearchSuggestionSpecToPlatformConverter {

    @RequiresApi
    public static class ApiHelperForV {
        @DoNotInline
        public static void addFilterProperties(SearchSuggestionSpec.Builder builder, String str, Collection<String> collection) {
            builder.addFilterProperties(str, collection);
        }
    }
}

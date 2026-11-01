package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.JoinSpec;
import android.content.Context;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public class JoinSpecToPlatformConverter {
    public static JoinSpec toPlatformJoinSpec(Context context, androidx.appsearch.app.JoinSpec joinSpec) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(joinSpec);
        JoinSpecToPlatformConverter$$ExternalSyntheticApiModelOutline5.m();
        return JoinSpecToPlatformConverter$$ExternalSyntheticApiModelOutline4.m(joinSpec.getChildPropertyExpression()).setNestedSearch(joinSpec.getNestedQuery(), SearchSpecToPlatformConverter.toPlatformSearchSpec(context, joinSpec.getNestedSearchSpec())).setMaxJoinedResultCount(joinSpec.getMaxJoinedResultCount()).setAggregationScoringStrategy(joinSpec.getAggregationScoringStrategy()).build();
    }
}

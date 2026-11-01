package androidx.appsearch.ast;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.flags.FlaggedApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public interface FunctionNode extends Node {

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface FunctionName {
    }
}

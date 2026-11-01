package androidx.appsearch.app;

import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public interface AppSearchEnvironment {

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnvironmentType {
    }

    ExecutorService createCachedThreadPoolExecutor();

    boolean isInfoLoggingEnabled();
}

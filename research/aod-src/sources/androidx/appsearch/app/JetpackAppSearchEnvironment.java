package androidx.appsearch.app;

import androidx.annotation.RestrictTo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class JetpackAppSearchEnvironment implements AppSearchEnvironment {
    @Override // androidx.appsearch.app.AppSearchEnvironment
    public ExecutorService createCachedThreadPoolExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Override // androidx.appsearch.app.AppSearchEnvironment
    public boolean isInfoLoggingEnabled() {
        return true;
    }
}

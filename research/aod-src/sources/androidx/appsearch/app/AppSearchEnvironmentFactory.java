package androidx.appsearch.app;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class AppSearchEnvironmentFactory {
    public static volatile AppSearchEnvironment sAppSearchEnvironment;

    public static AppSearchEnvironment getEnvironmentInstance() {
        AppSearchEnvironment jetpackAppSearchEnvironment;
        AppSearchEnvironment appSearchEnvironment = sAppSearchEnvironment;
        if (appSearchEnvironment != null) {
            return appSearchEnvironment;
        }
        synchronized (AppSearchEnvironmentFactory.class) {
            try {
                jetpackAppSearchEnvironment = sAppSearchEnvironment;
                if (jetpackAppSearchEnvironment == null) {
                    jetpackAppSearchEnvironment = new JetpackAppSearchEnvironment();
                    sAppSearchEnvironment = jetpackAppSearchEnvironment;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return jetpackAppSearchEnvironment;
    }
}

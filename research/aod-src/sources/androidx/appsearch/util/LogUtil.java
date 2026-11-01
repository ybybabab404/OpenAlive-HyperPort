package androidx.appsearch.util;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.AppSearchEnvironmentFactory;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class LogUtil {
    public static final boolean INFO = AppSearchEnvironmentFactory.getEnvironmentInstance().isInfoLoggingEnabled();
}

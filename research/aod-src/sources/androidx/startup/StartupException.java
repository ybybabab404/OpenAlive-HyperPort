package androidx.startup;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class StartupException extends RuntimeException {
    public StartupException(String str) {
        super(str);
    }

    public StartupException(Throwable th) {
        super(th);
    }
}

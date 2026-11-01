package io.reactivex.rxjava3.internal.schedulers;

import com.xiaomi.onetrack.util.a;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes2.dex */
public final class SchedulerPoolFactory {
    public static final boolean PURGE_ENABLED = getBooleanProperty(true, "rx3.purge-enabled", true, true, new SystemPropertyAccessor());

    public static final class SystemPropertyAccessor implements Function<String, String> {
        @Override // io.reactivex.rxjava3.functions.Function
        public String apply(String str) {
            return System.getProperty(str);
        }
    }

    public static ScheduledExecutorService create(ThreadFactory threadFactory) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, threadFactory);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(PURGE_ENABLED);
        return scheduledThreadPoolExecutor;
    }

    public static boolean getBooleanProperty(boolean z, String str, boolean z2, boolean z3, Function function) {
        if (!z) {
            return z3;
        }
        try {
            String str2 = (String) function.apply(str);
            return str2 == null ? z2 : a.i.equals(str2);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return z2;
        }
    }
}

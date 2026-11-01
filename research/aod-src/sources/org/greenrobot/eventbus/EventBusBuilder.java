package org.greenrobot.eventbus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.android.AndroidComponents;

/* JADX INFO: loaded from: classes3.dex */
public class EventBusBuilder {
    public static final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    public boolean ignoreGeneratedIndex;
    public Logger logger;
    public MainThreadSupport mainThreadSupport;
    public boolean strictMethodVerification;
    public List subscriberInfoIndexes;
    public boolean throwSubscriberException;
    public boolean logSubscriberExceptions = true;
    public boolean logNoSubscriberMessages = true;
    public boolean sendSubscriberExceptionEvent = true;
    public boolean sendNoSubscriberEvent = true;
    public boolean eventInheritance = true;
    public ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;

    public Logger getLogger() {
        Logger logger = this.logger;
        return logger != null ? logger : Logger.Default.get();
    }

    public MainThreadSupport getMainThreadSupport() {
        MainThreadSupport mainThreadSupport = this.mainThreadSupport;
        if (mainThreadSupport != null) {
            return mainThreadSupport;
        }
        if (AndroidComponents.areAvailable()) {
            return AndroidComponents.get().defaultMainThreadSupport;
        }
        return null;
    }
}

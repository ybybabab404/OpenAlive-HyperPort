package org.greenrobot.eventbus.meta;

import org.greenrobot.eventbus.SubscriberMethod;

/* JADX INFO: loaded from: classes3.dex */
public class SimpleSubscriberInfo extends AbstractSubscriberInfo {
    public final SubscriberMethodInfo[] methodInfos;

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public synchronized SubscriberMethod[] getSubscriberMethods() throws Throwable {
        try {
            try {
                int length = this.methodInfos.length;
                SubscriberMethod[] subscriberMethodArr = new SubscriberMethod[length];
                int i = 0;
                while (i < length) {
                    SubscriberMethodInfo subscriberMethodInfo = this.methodInfos[i];
                    SimpleSubscriberInfo simpleSubscriberInfo = this;
                    subscriberMethodArr[i] = simpleSubscriberInfo.createSubscriberMethod(subscriberMethodInfo.methodName, subscriberMethodInfo.eventType, subscriberMethodInfo.threadMode, subscriberMethodInfo.priority, subscriberMethodInfo.sticky);
                    i++;
                    this = simpleSubscriberInfo;
                }
                return subscriberMethodArr;
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            SimpleSubscriberInfo simpleSubscriberInfo2 = this;
            Throwable th4 = th;
            throw th4;
        }
    }
}

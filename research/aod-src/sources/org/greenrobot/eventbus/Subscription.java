package org.greenrobot.eventbus;

/* JADX INFO: loaded from: classes3.dex */
final class Subscription {
    public volatile boolean active = true;
    public final Object subscriber;
    public final SubscriberMethod subscriberMethod;

    public Subscription(Object obj, SubscriberMethod subscriberMethod) {
        this.subscriber = obj;
        this.subscriberMethod = subscriberMethod;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Subscription) {
            Subscription subscription = (Subscription) obj;
            if (this.subscriber == subscription.subscriber && this.subscriberMethod.equals(subscription.subscriberMethod)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.subscriber.hashCode() + this.subscriberMethod.methodString.hashCode();
    }
}

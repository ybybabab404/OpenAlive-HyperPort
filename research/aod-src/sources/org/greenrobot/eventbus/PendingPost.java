package org.greenrobot.eventbus;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
final class PendingPost {
    public static final List pendingPostPool = new ArrayList();
    public Object event;
    public PendingPost next;
    public Subscription subscription;

    public PendingPost(Object obj, Subscription subscription) {
        this.event = obj;
        this.subscription = subscription;
    }

    public static PendingPost obtainPendingPost(Subscription subscription, Object obj) {
        List list = pendingPostPool;
        synchronized (list) {
            try {
                int size = list.size();
                if (size <= 0) {
                    return new PendingPost(obj, subscription);
                }
                PendingPost pendingPost = (PendingPost) list.remove(size - 1);
                pendingPost.event = obj;
                pendingPost.subscription = subscription;
                pendingPost.next = null;
                return pendingPost;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void releasePendingPost(PendingPost pendingPost) {
        pendingPost.event = null;
        pendingPost.subscription = null;
        pendingPost.next = null;
        List list = pendingPostPool;
        synchronized (list) {
            try {
                if (list.size() < 10000) {
                    list.add(pendingPost);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

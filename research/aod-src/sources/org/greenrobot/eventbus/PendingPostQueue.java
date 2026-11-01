package org.greenrobot.eventbus;

/* JADX INFO: loaded from: classes3.dex */
final class PendingPostQueue {
    public PendingPost head;
    public PendingPost tail;

    public synchronized void enqueue(PendingPost pendingPost) {
        try {
            if (pendingPost == null) {
                throw new NullPointerException("null cannot be enqueued");
            }
            PendingPost pendingPost2 = this.tail;
            if (pendingPost2 != null) {
                pendingPost2.next = pendingPost;
                this.tail = pendingPost;
            } else {
                if (this.head != null) {
                    throw new IllegalStateException("Head present, but no tail");
                }
                this.tail = pendingPost;
                this.head = pendingPost;
            }
            notifyAll();
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized PendingPost poll() {
        PendingPost pendingPost;
        pendingPost = this.head;
        if (pendingPost != null) {
            PendingPost pendingPost2 = pendingPost.next;
            this.head = pendingPost2;
            if (pendingPost2 == null) {
                this.tail = null;
            }
        }
        return pendingPost;
    }

    public synchronized PendingPost poll(int i) {
        try {
            if (this.head == null) {
                wait(i);
            }
        } catch (Throwable th) {
            throw th;
        }
        return poll();
    }
}

package org.greenrobot.eventbus;

/* JADX INFO: loaded from: classes3.dex */
class AsyncPoster implements Runnable, Poster {
    public final EventBus eventBus;
    public final PendingPostQueue queue = new PendingPostQueue();

    public AsyncPoster(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override // org.greenrobot.eventbus.Poster
    public void enqueue(Subscription subscription, Object obj) {
        this.queue.enqueue(PendingPost.obtainPendingPost(subscription, obj));
        this.eventBus.getExecutorService().execute(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        PendingPost pendingPostPoll = this.queue.poll();
        if (pendingPostPoll == null) {
            throw new IllegalStateException("No pending post available");
        }
        this.eventBus.invokeSubscriber(pendingPostPoll);
    }
}

package org.greenrobot.eventbus;

/* JADX INFO: loaded from: classes3.dex */
public interface MainThreadSupport {
    Poster createPoster(EventBus eventBus);

    boolean isMainThread();
}

package com.bumptech.glide;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.manager.TargetTracker;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class RequestManager implements ComponentCallbacks2, LifecycleListener, ModelTypes<RequestBuilder<Drawable>> {
    public static final RequestOptions DECODE_TYPE_BITMAP = (RequestOptions) RequestOptions.decodeTypeOf(Bitmap.class).lock();
    public static final RequestOptions DECODE_TYPE_GIF = (RequestOptions) RequestOptions.decodeTypeOf(GifDrawable.class).lock();
    public static final RequestOptions DOWNLOAD_ONLY_OPTIONS = (RequestOptions) ((RequestOptions) RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA).priority(Priority.LOW)).skipMemoryCache(true);
    public final Runnable addSelfToLifecycle;
    public boolean clearOnStop;
    public final ConnectivityMonitor connectivityMonitor;
    public final Context context;
    public final CopyOnWriteArrayList defaultRequestListeners;
    public final Glide glide;
    public final Lifecycle lifecycle;
    public boolean pauseAllRequestsOnTrimMemoryModerate;
    public RequestOptions requestOptions;
    public final RequestTracker requestTracker;
    public final TargetTracker targetTracker;
    public final RequestManagerTreeNode treeNode;

    public static class ClearTarget extends CustomViewTarget<View, Object> {
        public ClearTarget(View view) {
            super(view);
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadFailed(Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.CustomViewTarget
        public void onResourceCleared(Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onResourceReady(Object obj, Transition transition) {
        }
    }

    public class RequestManagerConnectivityListener implements ConnectivityMonitor.ConnectivityListener {
        public final RequestTracker requestTracker;

        public RequestManagerConnectivityListener(RequestTracker requestTracker) {
            this.requestTracker = requestTracker;
        }

        @Override // com.bumptech.glide.manager.ConnectivityMonitor.ConnectivityListener
        public void onConnectivityChanged(boolean z) {
            if (z) {
                synchronized (RequestManager.this) {
                    this.requestTracker.restartRequests();
                }
            }
        }
    }

    public RequestManager(Glide glide, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode, Context context) {
        this(glide, lifecycle, requestManagerTreeNode, new RequestTracker(), glide.getConnectivityMonitorFactory(), context);
    }

    public RequestManager(Glide glide, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode, RequestTracker requestTracker, ConnectivityMonitorFactory connectivityMonitorFactory, Context context) {
        this.targetTracker = new TargetTracker();
        Runnable runnable = new Runnable() { // from class: com.bumptech.glide.RequestManager.1
            @Override // java.lang.Runnable
            public void run() {
                RequestManager requestManager = RequestManager.this;
                requestManager.lifecycle.addListener(requestManager);
            }
        };
        this.addSelfToLifecycle = runnable;
        this.glide = glide;
        this.lifecycle = lifecycle;
        this.treeNode = requestManagerTreeNode;
        this.requestTracker = requestTracker;
        this.context = context;
        ConnectivityMonitor connectivityMonitorBuild = connectivityMonitorFactory.build(context.getApplicationContext(), new RequestManagerConnectivityListener(requestTracker));
        this.connectivityMonitor = connectivityMonitorBuild;
        glide.registerRequestManager(this);
        if (Util.isOnBackgroundThread()) {
            Util.postOnUiThread(runnable);
        } else {
            lifecycle.addListener(this);
        }
        lifecycle.addListener(connectivityMonitorBuild);
        this.defaultRequestListeners = new CopyOnWriteArrayList(glide.getGlideContext().getDefaultRequestListeners());
        setRequestOptions(glide.getGlideContext().getDefaultRequestOptions());
    }

    public RequestBuilder as(Class cls) {
        return new RequestBuilder(this.glide, this, cls, this.context);
    }

    public RequestBuilder asBitmap() {
        return as(Bitmap.class).apply((BaseRequestOptions) DECODE_TYPE_BITMAP);
    }

    public RequestBuilder asDrawable() {
        return as(Drawable.class);
    }

    public void clear(View view) {
        clear(new ClearTarget(view));
    }

    public void clear(Target target) {
        if (target == null) {
            return;
        }
        untrackOrDelegate(target);
    }

    public final synchronized void clearRequests() {
        try {
            Iterator it = this.targetTracker.getAll().iterator();
            while (it.hasNext()) {
                clear((Target) it.next());
            }
            this.targetTracker.clear();
        } catch (Throwable th) {
            throw th;
        }
    }

    public List getDefaultRequestListeners() {
        return this.defaultRequestListeners;
    }

    public synchronized RequestOptions getDefaultRequestOptions() {
        return this.requestOptions;
    }

    public TransitionOptions getDefaultTransitionOptions(Class cls) {
        return this.glide.getGlideContext().getDefaultTransitionOptions(cls);
    }

    public RequestBuilder load(Bitmap bitmap) {
        return asDrawable().load(bitmap);
    }

    public RequestBuilder load(Uri uri) {
        return asDrawable().load(uri);
    }

    public RequestBuilder load(File file) {
        return asDrawable().load(file);
    }

    public RequestBuilder load(Integer num) {
        return asDrawable().load(num);
    }

    public RequestBuilder load(String str) {
        return asDrawable().load(str);
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public synchronized void onDestroy() {
        this.targetTracker.onDestroy();
        clearRequests();
        this.requestTracker.clearRequests();
        this.lifecycle.removeListener(this);
        this.lifecycle.removeListener(this.connectivityMonitor);
        Util.removeCallbacksOnUiThread(this.addSelfToLifecycle);
        this.glide.unregisterRequestManager(this);
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public synchronized void onStart() {
        resumeRequests();
        this.targetTracker.onStart();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public synchronized void onStop() {
        try {
            this.targetTracker.onStop();
            if (this.clearOnStop) {
                clearRequests();
            } else {
                pauseRequests();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        if (i == 60 && this.pauseAllRequestsOnTrimMemoryModerate) {
            pauseAllRequestsRecursive();
        }
    }

    public synchronized void pauseAllRequests() {
        this.requestTracker.pauseAllRequests();
    }

    public synchronized void pauseAllRequestsRecursive() {
        pauseAllRequests();
        Iterator it = this.treeNode.getDescendants().iterator();
        while (it.hasNext()) {
            ((RequestManager) it.next()).pauseAllRequests();
        }
    }

    public synchronized void pauseRequests() {
        this.requestTracker.pauseRequests();
    }

    public synchronized void resumeRequests() {
        this.requestTracker.resumeRequests();
    }

    public synchronized void setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = (RequestOptions) ((RequestOptions) requestOptions.mo87clone()).autoClone();
    }

    public synchronized String toString() {
        return super.toString() + "{tracker=" + this.requestTracker + ", treeNode=" + this.treeNode + "}";
    }

    public synchronized void track(Target target, Request request) {
        this.targetTracker.track(target);
        this.requestTracker.runRequest(request);
    }

    public synchronized boolean untrack(Target target) {
        Request request = target.getRequest();
        if (request == null) {
            return true;
        }
        if (!this.requestTracker.clearAndRemove(request)) {
            return false;
        }
        this.targetTracker.untrack(target);
        target.setRequest(null);
        return true;
    }

    public final void untrackOrDelegate(Target target) {
        boolean zUntrack = untrack(target);
        Request request = target.getRequest();
        if (zUntrack || this.glide.removeFromManagers(target) || request == null) {
            return;
        }
        target.setRequest(null);
        request.clear();
    }
}

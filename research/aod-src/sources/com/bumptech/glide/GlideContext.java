package com.bumptech.glide;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.util.GlideSuppliers;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GlideContext extends ContextWrapper {
    public static final TransitionOptions DEFAULT_TRANSITION_OPTIONS = new GenericTransitionOptions();
    public final ArrayPool arrayPool;
    public final List defaultRequestListeners;
    public RequestOptions defaultRequestOptions;
    public final Glide.RequestOptionsFactory defaultRequestOptionsFactory;
    public final Map defaultTransitionOptions;
    public final Engine engine;
    public final GlideExperiments experiments;
    public final ImageViewTargetFactory imageViewTargetFactory;
    public final int logLevel;
    public final GlideSuppliers.GlideSupplier registry;

    public GlideContext(Context context, ArrayPool arrayPool, GlideSuppliers.GlideSupplier glideSupplier, ImageViewTargetFactory imageViewTargetFactory, Glide.RequestOptionsFactory requestOptionsFactory, Map map, List list, Engine engine, GlideExperiments glideExperiments, int i) {
        super(context.getApplicationContext());
        this.arrayPool = arrayPool;
        this.imageViewTargetFactory = imageViewTargetFactory;
        this.defaultRequestOptionsFactory = requestOptionsFactory;
        this.defaultRequestListeners = list;
        this.defaultTransitionOptions = map;
        this.engine = engine;
        this.experiments = glideExperiments;
        this.logLevel = i;
        this.registry = GlideSuppliers.memorize(glideSupplier);
    }

    public ViewTarget buildImageViewTarget(ImageView imageView, Class cls) {
        return this.imageViewTargetFactory.buildTarget(imageView, cls);
    }

    public ArrayPool getArrayPool() {
        return this.arrayPool;
    }

    public List getDefaultRequestListeners() {
        return this.defaultRequestListeners;
    }

    public synchronized RequestOptions getDefaultRequestOptions() {
        try {
            if (this.defaultRequestOptions == null) {
                this.defaultRequestOptions = (RequestOptions) this.defaultRequestOptionsFactory.build().lock();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.defaultRequestOptions;
    }

    public TransitionOptions getDefaultTransitionOptions(Class cls) {
        TransitionOptions transitionOptions = (TransitionOptions) this.defaultTransitionOptions.get(cls);
        if (transitionOptions == null) {
            for (Map.Entry entry : this.defaultTransitionOptions.entrySet()) {
                if (((Class) entry.getKey()).isAssignableFrom(cls)) {
                    transitionOptions = (TransitionOptions) entry.getValue();
                }
            }
        }
        return transitionOptions == null ? DEFAULT_TRANSITION_OPTIONS : transitionOptions;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public GlideExperiments getExperiments() {
        return this.experiments;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public Registry getRegistry() {
        return (Registry) this.registry.get();
    }
}

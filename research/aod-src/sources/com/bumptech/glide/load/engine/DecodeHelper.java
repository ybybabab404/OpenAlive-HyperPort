package com.bumptech.glide.load.engine;

import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.UnitTransformation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class DecodeHelper<Transcode> {
    public DecodeJob.DiskCacheProvider diskCacheProvider;
    public DiskCacheStrategy diskCacheStrategy;
    public GlideContext glideContext;
    public int height;
    public boolean isCacheKeysSet;
    public boolean isLoadDataSet;
    public boolean isScaleOnlyOrNoTransform;
    public boolean isTransformationRequired;
    public Object model;
    public Options options;
    public Priority priority;
    public Class resourceClass;
    public Key signature;
    public Class transcodeClass;
    public Map transformations;
    public int width;
    public final List loadData = new ArrayList();
    public final List cacheKeys = new ArrayList();

    public void clear() {
        this.glideContext = null;
        this.model = null;
        this.signature = null;
        this.resourceClass = null;
        this.transcodeClass = null;
        this.options = null;
        this.priority = null;
        this.transformations = null;
        this.diskCacheStrategy = null;
        this.loadData.clear();
        this.isLoadDataSet = false;
        this.cacheKeys.clear();
        this.isCacheKeysSet = false;
    }

    public ArrayPool getArrayPool() {
        return this.glideContext.getArrayPool();
    }

    public List getCacheKeys() {
        if (!this.isCacheKeysSet) {
            this.isCacheKeysSet = true;
            this.cacheKeys.clear();
            List loadData = getLoadData();
            int size = loadData.size();
            for (int i = 0; i < size; i++) {
                ModelLoader.LoadData loadData2 = (ModelLoader.LoadData) loadData.get(i);
                if (!this.cacheKeys.contains(loadData2.sourceKey)) {
                    this.cacheKeys.add(loadData2.sourceKey);
                }
                for (int i2 = 0; i2 < loadData2.alternateKeys.size(); i2++) {
                    if (!this.cacheKeys.contains(loadData2.alternateKeys.get(i2))) {
                        this.cacheKeys.add(loadData2.alternateKeys.get(i2));
                    }
                }
            }
        }
        return this.cacheKeys;
    }

    public DiskCache getDiskCache() {
        return this.diskCacheProvider.getDiskCache();
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return this.diskCacheStrategy;
    }

    public int getHeight() {
        return this.height;
    }

    public List getLoadData() {
        if (!this.isLoadDataSet) {
            this.isLoadDataSet = true;
            this.loadData.clear();
            List modelLoaders = this.glideContext.getRegistry().getModelLoaders(this.model);
            int size = modelLoaders.size();
            for (int i = 0; i < size; i++) {
                ModelLoader.LoadData loadDataBuildLoadData = ((ModelLoader) modelLoaders.get(i)).buildLoadData(this.model, this.width, this.height, this.options);
                if (loadDataBuildLoadData != null) {
                    this.loadData.add(loadDataBuildLoadData);
                }
            }
        }
        return this.loadData;
    }

    public LoadPath getLoadPath(Class cls) {
        return this.glideContext.getRegistry().getLoadPath(cls, this.resourceClass, this.transcodeClass);
    }

    public Class getModelClass() {
        return this.model.getClass();
    }

    public List getModelLoaders(File file) {
        return this.glideContext.getRegistry().getModelLoaders(file);
    }

    public Options getOptions() {
        return this.options;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public List getRegisteredResourceClasses() {
        return this.glideContext.getRegistry().getRegisteredResourceClasses(this.model.getClass(), this.resourceClass, this.transcodeClass);
    }

    public ResourceEncoder getResultEncoder(Resource resource) {
        return this.glideContext.getRegistry().getResultEncoder(resource);
    }

    public DataRewinder getRewinder(Object obj) {
        return this.glideContext.getRegistry().getRewinder(obj);
    }

    public Key getSignature() {
        return this.signature;
    }

    public Encoder getSourceEncoder(Object obj) {
        return this.glideContext.getRegistry().getSourceEncoder(obj);
    }

    public Class getTranscodeClass() {
        return this.transcodeClass;
    }

    public Transformation getTransformation(Class cls) {
        Transformation transformation = (Transformation) this.transformations.get(cls);
        if (transformation == null) {
            for (Map.Entry entry : this.transformations.entrySet()) {
                if (((Class) entry.getKey()).isAssignableFrom(cls)) {
                    transformation = (Transformation) entry.getValue();
                    break;
                }
            }
        }
        if (transformation != null) {
            return transformation;
        }
        if (!this.transformations.isEmpty() || !this.isTransformationRequired) {
            return UnitTransformation.get();
        }
        throw new IllegalArgumentException("Missing transformation for " + cls + ". If you wish to ignore unknown resource types, use the optional transformation methods.");
    }

    public int getWidth() {
        return this.width;
    }

    public boolean hasLoadPath(Class cls) {
        return getLoadPath(cls) != null;
    }

    public void init(GlideContext glideContext, Object obj, Key key, int i, int i2, DiskCacheStrategy diskCacheStrategy, Class cls, Class cls2, Priority priority, Options options, Map map, boolean z, boolean z2, DecodeJob.DiskCacheProvider diskCacheProvider) {
        this.glideContext = glideContext;
        this.model = obj;
        this.signature = key;
        this.width = i;
        this.height = i2;
        this.diskCacheStrategy = diskCacheStrategy;
        this.resourceClass = cls;
        this.diskCacheProvider = diskCacheProvider;
        this.transcodeClass = cls2;
        this.priority = priority;
        this.options = options;
        this.transformations = map;
        this.isTransformationRequired = z;
        this.isScaleOnlyOrNoTransform = z2;
    }

    public boolean isResourceEncoderAvailable(Resource resource) {
        return this.glideContext.getRegistry().isResourceEncoderAvailable(resource);
    }

    public boolean isScaleOnlyOrNoTransform() {
        return this.isScaleOnlyOrNoTransform;
    }

    public boolean isSourceKey(Key key) {
        List loadData = getLoadData();
        int size = loadData.size();
        for (int i = 0; i < size; i++) {
            if (((ModelLoader.LoadData) loadData.get(i)).sourceKey.equals(key)) {
                return true;
            }
        }
        return false;
    }
}

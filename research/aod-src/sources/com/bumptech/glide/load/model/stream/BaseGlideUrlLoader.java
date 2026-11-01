package com.bumptech.glide.load.model.stream;

import android.text.TextUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseGlideUrlLoader<Model> implements ModelLoader<Model, InputStream> {
    public final ModelLoader concreteLoader;
    public final ModelCache modelCache;

    public static List getAlternateKeys(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(new GlideUrl((String) it.next()));
        }
        return arrayList;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData buildLoadData(Object obj, int i, int i2, Options options) {
        ModelCache modelCache = this.modelCache;
        GlideUrl glideUrl = modelCache != null ? (GlideUrl) modelCache.get(obj, i, i2) : null;
        if (glideUrl == null) {
            String url = getUrl(obj, i, i2, options);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            GlideUrl glideUrl2 = new GlideUrl(url, getHeaders(obj, i, i2, options));
            ModelCache modelCache2 = this.modelCache;
            if (modelCache2 != null) {
                modelCache2.put(obj, i, i2, glideUrl2);
            }
            glideUrl = glideUrl2;
        }
        List alternateUrls = getAlternateUrls(obj, i, i2, options);
        ModelLoader.LoadData loadDataBuildLoadData = this.concreteLoader.buildLoadData(glideUrl, i, i2, options);
        return (loadDataBuildLoadData == null || alternateUrls.isEmpty()) ? loadDataBuildLoadData : new ModelLoader.LoadData(loadDataBuildLoadData.sourceKey, getAlternateKeys(alternateUrls), loadDataBuildLoadData.fetcher);
    }

    public List getAlternateUrls(Object obj, int i, int i2, Options options) {
        return Collections.EMPTY_LIST;
    }

    public Headers getHeaders(Object obj, int i, int i2, Options options) {
        return Headers.DEFAULT;
    }

    public abstract String getUrl(Object obj, int i, int i2, Options options);
}

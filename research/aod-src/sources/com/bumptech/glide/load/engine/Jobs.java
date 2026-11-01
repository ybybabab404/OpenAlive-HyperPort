package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class Jobs {
    public final Map jobs = new HashMap();
    public final Map onlyCacheJobs = new HashMap();

    public EngineJob get(Key key, boolean z) {
        return (EngineJob) getJobMap(z).get(key);
    }

    public final Map getJobMap(boolean z) {
        return z ? this.onlyCacheJobs : this.jobs;
    }

    public void put(Key key, EngineJob engineJob) {
        getJobMap(engineJob.onlyRetrieveFromCache()).put(key, engineJob);
    }

    public void removeIfCurrent(Key key, EngineJob engineJob) {
        Map jobMap = getJobMap(engineJob.onlyRetrieveFromCache());
        if (engineJob.equals(jobMap.get(key))) {
            jobMap.remove(key);
        }
    }
}

package com.bumptech.glide;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GlideExperiments {
    public final Map experiments;

    public static final class Builder {
        public final Map experiments = new HashMap();

        public GlideExperiments build() {
            return new GlideExperiments(this);
        }
    }

    public interface Experiment {
    }

    public GlideExperiments(Builder builder) {
        this.experiments = Collections.unmodifiableMap(new HashMap(builder.experiments));
    }

    public boolean isEnabled(Class cls) {
        return this.experiments.containsKey(cls);
    }
}

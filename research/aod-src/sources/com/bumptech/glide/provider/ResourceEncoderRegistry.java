package com.bumptech.glide.provider;

import com.bumptech.glide.load.ResourceEncoder;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ResourceEncoderRegistry {
    public final List encoders = new ArrayList();

    public static final class Entry<T> {
        public final ResourceEncoder encoder;
        public final Class resourceClass;

        public Entry(Class cls, ResourceEncoder resourceEncoder) {
            this.resourceClass = cls;
            this.encoder = resourceEncoder;
        }

        public boolean handles(Class cls) {
            return this.resourceClass.isAssignableFrom(cls);
        }
    }

    public synchronized void append(Class cls, ResourceEncoder resourceEncoder) {
        this.encoders.add(new Entry(cls, resourceEncoder));
    }

    public synchronized ResourceEncoder get(Class cls) {
        int size = this.encoders.size();
        for (int i = 0; i < size; i++) {
            Entry entry = (Entry) this.encoders.get(i);
            if (entry.handles(cls)) {
                return entry.encoder;
            }
        }
        return null;
    }
}

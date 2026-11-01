package com.bumptech.glide.provider;

import com.bumptech.glide.load.Encoder;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EncoderRegistry {
    public final List encoders = new ArrayList();

    public static final class Entry<T> {
        public final Class dataClass;
        public final Encoder encoder;

        public Entry(Class cls, Encoder encoder) {
            this.dataClass = cls;
            this.encoder = encoder;
        }

        public boolean handles(Class cls) {
            return this.dataClass.isAssignableFrom(cls);
        }
    }

    public synchronized void append(Class cls, Encoder encoder) {
        this.encoders.add(new Entry(cls, encoder));
    }

    public synchronized Encoder getEncoder(Class cls) {
        for (Entry entry : this.encoders) {
            if (entry.handles(cls)) {
                return entry.encoder;
            }
        }
        return null;
    }
}

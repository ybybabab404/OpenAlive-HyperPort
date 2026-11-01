package com.bumptech.glide.util;

/* JADX INFO: loaded from: classes.dex */
public final class GlideSuppliers {

    public interface GlideSupplier<T> {
        Object get();
    }

    public static GlideSupplier memorize(final GlideSupplier glideSupplier) {
        return new GlideSupplier<Object>() { // from class: com.bumptech.glide.util.GlideSuppliers.1
            public volatile Object instance;

            @Override // com.bumptech.glide.util.GlideSuppliers.GlideSupplier
            public Object get() {
                if (this.instance == null) {
                    synchronized (this) {
                        try {
                            if (this.instance == null) {
                                this.instance = Preconditions.checkNotNull(glideSupplier.get());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
                return this.instance;
            }
        };
    }
}

package com.miui.clock;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: SettingsObserver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class SettingsObserver {
    public static final SettingsObserver INSTANCE = new SettingsObserver();
    public static final Handler handler = new Handler(Looper.getMainLooper());
    public static final Map entries = new LinkedHashMap();

    /* JADX INFO: compiled from: SettingsObserver.kt */
    @Metadata
    public static final class Entry {
        public volatile boolean cachedValue;
        public final ContentResolver contentResolver;
        public final String key;
        public final CopyOnWriteArrayList listeners;
        public ContentObserver observer;
        public final Uri uri;

        public Entry(String key, Uri uri, ContentResolver contentResolver) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(uri, "uri");
            Intrinsics.checkNotNullParameter(contentResolver, "contentResolver");
            this.key = key;
            this.uri = uri;
            this.contentResolver = contentResolver;
            this.listeners = new CopyOnWriteArrayList();
        }

        public final boolean getCachedValue() {
            return this.cachedValue;
        }

        public final CopyOnWriteArrayList getListeners() {
            return this.listeners;
        }

        public final Uri getUri() {
            return this.uri;
        }

        public final void setCachedValue(boolean z) {
            this.cachedValue = z;
        }

        public final void setObserver(ContentObserver contentObserver) {
            this.observer = contentObserver;
        }
    }

    public static final void addListener(String key, Runnable listener) {
        CopyOnWriteArrayList listeners;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Entry entry = (Entry) entries.get(key);
        if (entry == null || (listeners = entry.getListeners()) == null) {
            return;
        }
        listeners.add(listener);
    }

    public static final boolean getBoolean(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Entry entry = (Entry) entries.get(key);
        if (entry != null) {
            return entry.getCachedValue();
        }
        return false;
    }

    public static final synchronized void init(Context appContext, final String key) {
        Intrinsics.checkNotNullParameter(appContext, "appContext");
        Intrinsics.checkNotNullParameter(key, "key");
        Map map = entries;
        if (map.containsKey(key)) {
            return;
        }
        Context applicationContext = appContext.getApplicationContext();
        if (applicationContext != null) {
            appContext = applicationContext;
        }
        final ContentResolver contentResolver = appContext.getContentResolver();
        Uri uriFor = Settings.Secure.getUriFor(key);
        Intrinsics.checkNotNullExpressionValue(uriFor, "getUriFor(...)");
        Intrinsics.checkNotNull(contentResolver);
        final Entry entry = new Entry(key, uriFor, contentResolver);
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.miui.clock.SettingsObserver.init.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri, int i) {
                int i2 = Settings.Secure.getInt(contentResolver, key, 0);
                boolean z2 = i2 != 0;
                Log.d("SettingsObserver", "onChange: key=" + key + " value=" + i2 + " userId=" + i);
                if (entry.getCachedValue() != z2) {
                    entry.setCachedValue(z2);
                    Iterator it = entry.getListeners().iterator();
                    while (it.hasNext()) {
                        ((Runnable) it.next()).run();
                    }
                }
            }
        };
        entry.setCachedValue(Settings.Secure.getInt(contentResolver, key, 0) != 0);
        contentResolver.registerContentObserver(entry.getUri(), false, contentObserver);
        entry.setObserver(contentObserver);
        map.put(key, entry);
        Log.d("SettingsObserver", "init: key=" + key + " cachedValue=" + entry.getCachedValue());
    }

    public static final void removeListener(String key, Runnable listener) {
        CopyOnWriteArrayList listeners;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Entry entry = (Entry) entries.get(key);
        if (entry == null || (listeners = entry.getListeners()) == null) {
            return;
        }
        listeners.remove(listener);
    }
}

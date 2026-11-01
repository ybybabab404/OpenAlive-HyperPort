package com.miui.clock.allInOne;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AllInOneBase.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AllInOneBase$RectQueryLruCache$map$1 extends LinkedHashMap<AllInOneBase.RectQueryKey, float[]> {
    public final /* synthetic */ AllInOneBase.RectQueryLruCache this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AllInOneBase$RectQueryLruCache$map$1(AllInOneBase.RectQueryLruCache rectQueryLruCache) {
        super(64, 0.75f, true);
        this.this$0 = rectQueryLruCache;
    }

    public /* bridge */ boolean containsKey(AllInOneBase.RectQueryKey rectQueryKey) {
        return super.containsKey((Object) rectQueryKey);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof AllInOneBase.RectQueryKey) {
            return containsKey((AllInOneBase.RectQueryKey) obj);
        }
        return false;
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof float[]) {
            return containsValue((float[]) obj);
        }
        return false;
    }

    public /* bridge */ boolean containsValue(float[] fArr) {
        return super.containsValue((Object) fArr);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set entrySet() {
        return getEntries();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object get(Object obj) {
        if (obj instanceof AllInOneBase.RectQueryKey) {
            return get((AllInOneBase.RectQueryKey) obj);
        }
        return null;
    }

    public /* bridge */ float[] get(AllInOneBase.RectQueryKey rectQueryKey) {
        return (float[]) super.get((Object) rectQueryKey);
    }

    public /* bridge */ Set getEntries() {
        return super.entrySet();
    }

    public /* bridge */ Set getKeys() {
        return super.keySet();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof AllInOneBase.RectQueryKey) ? obj2 : getOrDefault((AllInOneBase.RectQueryKey) obj, (float[]) obj2);
    }

    public /* bridge */ float[] getOrDefault(AllInOneBase.RectQueryKey rectQueryKey, float[] fArr) {
        return (float[]) super.getOrDefault((Object) rectQueryKey, fArr);
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    public /* bridge */ Collection getValues() {
        return super.values();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set keySet() {
        return getKeys();
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object remove(Object obj) {
        if (obj instanceof AllInOneBase.RectQueryKey) {
            return remove((AllInOneBase.RectQueryKey) obj);
        }
        return null;
    }

    public /* bridge */ boolean remove(AllInOneBase.RectQueryKey rectQueryKey, float[] fArr) {
        return super.remove((Object) rectQueryKey, (Object) fArr);
    }

    @Override // java.util.HashMap, java.util.Map
    public final /* bridge */ boolean remove(Object obj, Object obj2) {
        if ((obj instanceof AllInOneBase.RectQueryKey) && (obj2 instanceof float[])) {
            return remove((AllInOneBase.RectQueryKey) obj, (float[]) obj2);
        }
        return false;
    }

    public /* bridge */ float[] remove(AllInOneBase.RectQueryKey rectQueryKey) {
        return (float[]) super.remove((Object) rectQueryKey);
    }

    @Override // java.util.LinkedHashMap
    public boolean removeEldestEntry(Map.Entry<AllInOneBase.RectQueryKey, float[]> entry) {
        return size() > this.this$0.maxSize;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Collection values() {
        return getValues();
    }
}

package com.miui.clock.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
/* JADX INFO: compiled from: MutiDeviceDimenAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class MutiDeviceDimenAdapter {
    public static final MutiDeviceDimenAdapter INSTANCE = new MutiDeviceDimenAdapter();
    public static final Map sIdentifierCache = new ConcurrentHashMap();

    public static final void clearIdentifierCache() {
        Map map = sIdentifierCache;
        int size = map.size();
        map.clear();
        Log.d("MutiDeviceDimenAdapter", "clearIdentifierCache, size=" + size);
    }

    public static final int getMultiDeviceDimen(Context context, int i, boolean z) {
        String str;
        if (context == null) {
            return 0;
        }
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        float designDp = DeviceConfig.getDesignDp(context);
        if (designDp == 696.0f) {
            str = (configuration.orientation != 2 || z) ? "_fold" : "_fold_land";
        } else if (designDp == 711.0f) {
            str = (configuration.orientation != 2 || z) ? "_pad" : "_pad_land";
        } else {
            str = "";
        }
        String packageName = context.getPackageName();
        String strBuildIdentifierCacheKey = INSTANCE.buildIdentifierCacheKey(i, str, packageName);
        Map map = sIdentifierCache;
        Integer num = (Integer) map.get(strBuildIdentifierCacheKey);
        if (num != null) {
            if (num.intValue() != 0) {
                i = num.intValue();
            }
            return DeviceConfig.getDimen(i, context);
        }
        try {
            int identifier = resources.getIdentifier(resources.getResourceEntryName(i) + str, "dimen", packageName);
            map.put(strBuildIdentifierCacheKey, Integer.valueOf(identifier));
            if (identifier == 0) {
                identifier = i;
            }
            return DeviceConfig.getDimen(identifier, context);
        } catch (Exception unused) {
            return DeviceConfig.getDimen(i, context);
        }
    }

    public static final int resolveResIdWithSuffix(Context context, int i) {
        String str;
        if (context != null) {
            if (DeviceConfig.isFoldDevice()) {
                str = "_fold";
            } else {
                str = DeviceConfig.isPadDevice() ? "_pad" : "";
            }
            if (str.length() == 0) {
                return i;
            }
            String packageName = context.getPackageName();
            String strBuildIdentifierCacheKey = INSTANCE.buildIdentifierCacheKey(i, str, packageName);
            Map map = sIdentifierCache;
            Integer num = (Integer) map.get(strBuildIdentifierCacheKey);
            if (num == null) {
                try {
                    Resources resources = context.getResources();
                    int identifier = resources.getIdentifier(resources.getResourceEntryName(i) + str, "dimen", packageName);
                    map.put(strBuildIdentifierCacheKey, Integer.valueOf(identifier));
                    if (identifier != 0) {
                        return identifier;
                    }
                } catch (Exception e) {
                    Log.d("MutiDeviceDimenAdapter", "resolveResIdWithSuffix failed for id=" + i + ", suffix=" + str, e);
                }
            } else if (num.intValue() != 0) {
                return num.intValue();
            }
        }
        return i;
    }

    public final String buildIdentifierCacheKey(int i, String str, String str2) {
        return i + '|' + str + '|' + str2;
    }
}

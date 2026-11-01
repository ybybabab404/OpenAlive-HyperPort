package com.xiaomi.taiyi.sdk.base.utils;

import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DeviceUtil {
    public static final DeviceUtil INSTANCE = new DeviceUtil();
    public static final Lazy ROM$delegate = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.base.utils.DeviceUtil$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(DeviceUtil.ROM_delegate$lambda$0());
        }
    });
    public static final Lazy RAM$delegate = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.base.utils.DeviceUtil$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(DeviceUtil.RAM_delegate$lambda$1());
        }
    });

    public static final int RAM_delegate$lambda$1() {
        long sysTotalMem = ((long) getSysTotalMem()) / 1048576;
        if (0 <= sysTotalMem && sysTotalMem < 1) {
            return 1;
        }
        if (1 <= sysTotalMem && sysTotalMem < 2) {
            return 2;
        }
        if (2 <= sysTotalMem && sysTotalMem < 4) {
            return 4;
        }
        if (4 <= sysTotalMem && sysTotalMem < 6) {
            return 6;
        }
        if (6 <= sysTotalMem && sysTotalMem < 8) {
            return 8;
        }
        if (8 <= sysTotalMem && sysTotalMem < 10) {
            return 10;
        }
        if (10 <= sysTotalMem && sysTotalMem < 12) {
            return 12;
        }
        if (12 <= sysTotalMem && sysTotalMem < 16) {
            return 16;
        }
        if (16 <= sysTotalMem && sysTotalMem < 24) {
            return 24;
        }
        if (24 <= sysTotalMem && sysTotalMem < 32) {
            return 32;
        }
        if (32 <= sysTotalMem && sysTotalMem < 64) {
            return 64;
        }
        if (64 <= sysTotalMem && sysTotalMem < 128) {
            return 128;
        }
        if (128 <= sysTotalMem && sysTotalMem < 256) {
            return AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL;
        }
        if (256 <= sysTotalMem && sysTotalMem < 512) {
            return 512;
        }
        if (512 > sysTotalMem || sysTotalMem >= 1024) {
            return AutoBottomSheetKt.ALIGN_SCREEN_END;
        }
        return 1024;
    }

    public static final int ROM_delegate$lambda$0() {
        long totalStorage = getTotalStorage() / 1073741824;
        if (1 <= totalStorage && totalStorage < 17) {
            return 16;
        }
        if (17 <= totalStorage && totalStorage < 33) {
            return 32;
        }
        if (33 <= totalStorage && totalStorage < 65) {
            return 64;
        }
        if (65 <= totalStorage && totalStorage < 129) {
            return 128;
        }
        if (129 <= totalStorage && totalStorage < 257) {
            return AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL;
        }
        if (257 <= totalStorage && totalStorage < 513) {
            return 512;
        }
        if (513 > totalStorage || totalStorage >= 1025) {
            return (1025 > totalStorage || totalStorage >= 2049) ? AutoBottomSheetKt.ALIGN_SCREEN_TOP : AutoBottomSheetKt.ALIGN_SCREEN_END;
        }
        return 1024;
    }

    public static final int getAppTotalPss() {
        return readAppMemInfo().getTotalPss();
    }

    public static final int getSysAvailMem() {
        return ((Number) readSysMemInfo().getOrDefault("MemAvailable", 0)).intValue();
    }

    public static final int getSysTotalMem() {
        return ((Number) readSysMemInfo().getOrDefault("MemTotal", 0)).intValue();
    }

    public static final long getTotalStorage() {
        return new StatFs(Environment.getExternalStorageDirectory().getPath()).getTotalBytes();
    }

    public static final Debug.MemoryInfo readAppMemInfo() {
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    public static final Map readSysMemInfo() {
        final HashMap map = new HashMap();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"));
            try {
                TextStreamsKt.forEachLine(bufferedReader, new Function1() { // from class: com.xiaomi.taiyi.sdk.base.utils.DeviceUtil$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return DeviceUtil.readSysMemInfo$lambda$3$lambda$2(map, (String) obj);
                    }
                });
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedReader, null);
                return map;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            SdkLog.e("DeviceUtil", "readMemInfo IOException. " + e.getMessage());
            return map;
        }
    }

    public static final Unit readSysMemInfo$lambda$3$lambda$2(HashMap map, String line) {
        Intrinsics.checkNotNullParameter(line, "line");
        List listSplit$default = StringsKt.split$default(line, new String[]{":"}, false, 0, 6, null);
        if (listSplit$default.size() >= 2) {
            String string = StringsKt.trim((String) listSplit$default.get(0)).toString();
            try {
                map.put(string, Integer.valueOf(Integer.parseInt((String) new Regex("\\s+").split(StringsKt.trim((String) listSplit$default.get(1)).toString(), 0).get(0))));
                if (Intrinsics.areEqual(string, "MemAvailable")) {
                    return Unit.INSTANCE;
                }
            } catch (NumberFormatException e) {
                SdkLog.e("DeviceUtil", "readMemInfo parse " + line + " error. " + e.getMessage());
            }
        }
        return Unit.INSTANCE;
    }
}

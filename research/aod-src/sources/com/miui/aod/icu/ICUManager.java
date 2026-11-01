package com.miui.aod.icu;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.settings.AodStyleSelectActivity;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miui.os.Build;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ICUManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ICUManager {
    public static final ICUManager INSTANCE = new ICUManager();

    public static final void loadCity$lambda$0(TextView textView, String str) {
        if (str == null) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(str);
        }
    }

    public final int getThickInJapan() {
        return 400;
    }

    public final int getWeightInJapan() {
        return TextStyleCategoryInfo.MAX_FONT_WEIGHT;
    }

    public final boolean isBoldInJapan(int i) {
        return i >= 700;
    }

    public final boolean isJapanBuild() {
        String region = Build.getRegion();
        Intrinsics.checkNotNullExpressionValue(region, "getRegion(...)");
        return StringsKt.startsWith$default(region, "JP", false, 2, null);
    }

    public final void loadCity(Context context, TimeZone timeZone, final Consumer consumer) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        final boolean zAreEqual = Intrinsics.areEqual(context.getClass(), AodStyleSelectActivity.class);
        Log.v("ICUManager", String.valueOf(zAreEqual));
        if (timeZone != null) {
            if (Intrinsics.areEqual(timeZone.getID(), TimeZone.getDefault().getID())) {
                if (zAreEqual) {
                    ConcurrentHashMap zoneMap = AodStyleSelectActivity.getZoneMap();
                    if (zoneMap == null) {
                        Log.v("ICUManager", "mZoneMap init");
                        AodStyleSelectActivity.initZoneMap();
                    } else if (zoneMap.containsKey("content://com.android.settings.provider.MiuiSettingsDataProvider/zone_info")) {
                        String str = (String) zoneMap.get("content://com.android.settings.provider.MiuiSettingsDataProvider/zone_info");
                        Log.v("ICUManager", "useCache");
                        consumer.accept(str);
                        return;
                    }
                }
                GetZoneNameTask.doTask(new GetZoneNameTask.OnGetZoneNameCompleteListener() { // from class: com.miui.aod.icu.ICUManager$loadCity$2$3
                    @Override // com.miui.aod.icu.GetZoneNameTask.OnGetZoneNameCompleteListener
                    public void onComplete(String str2) {
                        if (zAreEqual && str2 != null) {
                            ConcurrentHashMap zoneMap2 = AodStyleSelectActivity.getZoneMap();
                            if (zoneMap2 != null) {
                            }
                            Log.v("ICUManager", "put value");
                        }
                        consumer.accept(str2);
                    }
                }, context, "content://com.android.settings.provider.MiuiSettingsDataProvider/zone_info");
                return;
            }
            if (zAreEqual) {
                ConcurrentHashMap zoneMap2 = AodStyleSelectActivity.getZoneMap();
                if (zoneMap2 == null) {
                    Log.v("ICUManager", "mZoneMap init city2");
                    AodStyleSelectActivity.initZoneMap();
                } else if (zoneMap2.containsKey("content://com.android.settings.provider.MiuiSettingsDataProvider/dual_zone_info")) {
                    String str2 = (String) zoneMap2.get("content://com.android.settings.provider.MiuiSettingsDataProvider/dual_zone_info");
                    Log.v("ICUManager", "useCache 2");
                    consumer.accept(str2);
                    return;
                }
            }
            GetZoneNameTask.doTask(new GetZoneNameTask.OnGetZoneNameCompleteListener() { // from class: com.miui.aod.icu.ICUManager$loadCity$2$6
                @Override // com.miui.aod.icu.GetZoneNameTask.OnGetZoneNameCompleteListener
                public void onComplete(String str3) {
                    if (zAreEqual && str3 != null) {
                        ConcurrentHashMap zoneMap3 = AodStyleSelectActivity.getZoneMap();
                        if (zoneMap3 != null) {
                        }
                        Log.v("ICUManager", "put value 2");
                    }
                    consumer.accept(str3);
                }
            }, context, "content://com.android.settings.provider.MiuiSettingsDataProvider/dual_zone_info");
        }
    }

    public final void loadCity(final TextView textView, Context context, TimeZone timeZone) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        loadCity(context, timeZone, new Consumer() { // from class: com.miui.aod.icu.ICUManager$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ICUManager.loadCity$lambda$0(textView, (String) obj);
            }
        });
    }

    public final boolean smartOnlySupportInnerDevice() {
        return Build.DEVICE.equals("ruyi");
    }

    public final boolean smartOnlySupportOuterDevice() {
        return Build.DEVICE.equals("babylon");
    }
}

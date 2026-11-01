package com.miui.aod.monitor.policy;

import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: VisibilityPolicyRegistryImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class VisibilityPolicyRegistryImpl implements VisibilityPolicyRegistry {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: VisibilityPolicyRegistryImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public static final CharSequence resolveTargets$lambda$0(ViewTarget it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getLabel();
    }

    public final ViewTargetSet classicGeneric() {
        return classicPlus();
    }

    public final ViewTargetSet classicMax() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("text_area"));
        arrayList.add(target("time_view"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet classicPlus() {
        return new ViewTargetSet(CollectionsKt.mutableListOf(target("text_area"), target("time_view")));
    }

    public final ViewTargetSet classicStyle21() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("text_area"));
        arrayList.add(target("time_view"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet classicStyle25() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("text_area"));
        arrayList.add(target("time_view"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet classicStyle26() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("text_area"));
        arrayList.add(target("time_view"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet doodle() {
        return new ViewTargetSet(CollectionsKt.listOf((Object[]) new ViewTarget[]{target("week_today"), target("time_hour"), target("time_minute"), target("data_month"), target("data_day")}));
    }

    public final ViewTargetSet easternA() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("tv_data"));
        arrayList.add(target("tv_data_week"));
        arrayList.add(target("tv_minute"));
        arrayList.add(target("tv_hour"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet easternB() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("eastern_art_b_h_aod_time_below_view_hour2"));
        arrayList.add(target("eastern_art_b_h_aod_time_below_view_hour1"));
        arrayList.add(target("eastern_art_b_h_aod_time_below_view_minute1"));
        arrayList.add(target("eastern_art_b_h_aod_time_below_view_minute2"));
        arrayList.add(target("eastern_art_b_h_aod_week_zh"));
        arrayList.add(target("eastern_art_b_h_aod_year_zh"));
        arrayList.add(target("eastern_art_b_h_aod_data_zh"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet easternC() {
        return new ViewTargetSet(CollectionsKt.listOf((Object[]) new ViewTarget[]{target("time_hour_1"), target("time_hour_2"), target("current_week"), target("date_and_time"), target("time_minute_1"), target("time_minute_2")}));
    }

    public final ViewTargetSet magazineA() {
        return new ViewTargetSet(CollectionsKt.mutableListOf(target("current_date"), target("current_time"), target("current_week")));
    }

    public final ViewTargetSet magazineB() {
        return new ViewTargetSet(CollectionsKt.mutableListOf(target("current_time"), target("current_week"), target("current_date")));
    }

    public final ViewTargetSet magazineC() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("current_time_hour_style1"));
        arrayList.add(target("current_time_minute_style1"));
        arrayList.add(target("current_week_style1"));
        arrayList.add(target("current_date_style1"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet oversizeA() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("hour1"));
        arrayList.add(target("hour2"));
        arrayList.add(target("minute1"));
        arrayList.add(target("minute2"));
        arrayList.add(target("current_date"));
        arrayList.add(target("current_week"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTargetSet oversizeB() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("hour1"));
        arrayList.add(target("hour2"));
        arrayList.add(target("oversize_b_date"));
        arrayList.add(target("oversize_b_week"));
        arrayList.add(target("minute1"));
        arrayList.add(target("minute2"));
        return new ViewTargetSet(arrayList);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:66:0x00f3  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.miui.aod.monitor.policy.VisibilityPolicyRegistry
    public ViewTargetSet resolveTargets(String str, int i) {
        ViewTargetSet viewTargetSet;
        if (str == null) {
            str = "";
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        switch (lowerCase.hashCode()) {
            case -1744945801:
                if (!lowerCase.equals("oversize_a")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = oversizeA();
                }
                break;
            case -1744945800:
                if (!lowerCase.equals("oversize_b")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = oversizeB();
                }
                break;
            case -1326135015:
                if (!lowerCase.equals("doodle")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = doodle();
                }
                break;
            case -567074186:
                if (!lowerCase.equals("magazine_a")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = magazineA();
                }
                break;
            case -567074185:
                if (!lowerCase.equals("magazine_b")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = magazineB();
                }
                break;
            case -567074184:
                if (!lowerCase.equals("magazine_c")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = magazineC();
                }
                break;
            case -138709961:
                if (!lowerCase.equals("classic_max")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = classicMax();
                }
                break;
            case -4941529:
                if (!lowerCase.equals("classic_plus")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = classicPlus();
                }
                break;
            case 853620882:
                if (!lowerCase.equals("classic")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else if (i == 21) {
                    viewTargetSet = classicStyle21();
                } else if (i == 25) {
                    viewTargetSet = classicStyle25();
                } else if (i == 26) {
                    viewTargetSet = classicStyle26();
                } else {
                    viewTargetSet = classicGeneric();
                }
                break;
            case 1179492204:
                if (!lowerCase.equals("rhombus")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = rhombus();
                }
                break;
            case 1786073894:
                if (!lowerCase.equals("eastern_a")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = easternA();
                }
                break;
            case 1786073895:
                if (!lowerCase.equals("eastern_b")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = easternB();
                }
                break;
            case 1786073896:
                if (!lowerCase.equals("eastern_c")) {
                    viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                } else {
                    viewTargetSet = easternC();
                }
                break;
            default:
                viewTargetSet = new ViewTargetSet(CollectionsKt.emptyList());
                break;
        }
        if (Log.isLoggable("KgVis.Policy", 3)) {
            Log.d("KgVis.Policy", "policy id=" + lowerCase + " style=" + i + " -> " + CollectionsKt.joinToString$default(viewTargetSet.getViewTargets(), null, "[", "]", 0, null, new Function1() { // from class: com.miui.aod.monitor.policy.VisibilityPolicyRegistryImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return VisibilityPolicyRegistryImpl.resolveTargets$lambda$0((ViewTarget) obj);
                }
            }, 25, null));
        }
        return viewTargetSet;
    }

    public final ViewTargetSet rhombus() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(target("current_date"));
        arrayList.add(target("current_week"));
        arrayList.add(target("hour1"));
        arrayList.add(target("hour2"));
        arrayList.add(target("minute1"));
        arrayList.add(target("minute2"));
        return new ViewTargetSet(arrayList);
    }

    public final ViewTarget target(String str) {
        return new ViewTarget("MiuiClock:id/" + str, new ViewSelector.ByIdName(str));
    }
}

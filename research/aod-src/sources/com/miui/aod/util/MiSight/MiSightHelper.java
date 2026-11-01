package com.miui.aod.util.MiSight;

import android.util.Log;
import com.miui.misight.mievent.MiEvent;
import com.miui.misight.mievent.MiSight;
import java.time.LocalDateTime;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MiSightHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class MiSightHelper {
    public static final MiSightHelper INSTANCE = new MiSightHelper();
    public static final Map msgTypeHandler = MapsKt.mapOf(TuplesKt.to(Reflection.getOrCreateKotlinClass(Integer.TYPE), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$2((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(Float.TYPE), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$3((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(Boolean.TYPE), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$4((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(Long.TYPE), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$5((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(Short.TYPE), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$6((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(String.class), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda6
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$7((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(int[].class), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda7
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$8((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(float[].class), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda8
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$9((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(boolean[].class), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda9
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$10((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(long[].class), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda10
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$11((MiEvent) obj, (String) obj2, obj3);
        }
    }), TuplesKt.to(Reflection.getOrCreateKotlinClass(short[].class), new Function3() { // from class: com.miui.aod.util.MiSight.MiSightHelper$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return MiSightHelper.msgTypeHandler$lambda$12((MiEvent) obj, (String) obj2, obj3);
        }
    }));

    public static final Unit msgTypeHandler$lambda$10(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addBoolArray(key, (boolean[]) value);
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$11(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addLongArray(key, (long[]) value);
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$12(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addShortArray(key, (short[]) value);
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$2(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addInt(key, ((Integer) value).intValue());
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$3(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addFloat(key, ((Float) value).floatValue());
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$4(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addBool(key, ((Boolean) value).booleanValue());
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$5(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addLong(key, ((Long) value).longValue());
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$6(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addShort(key, ((Short) value).shortValue());
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$7(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addStr(key, (String) value);
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$8(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addIntArray(key, (int[]) value);
        return Unit.INSTANCE;
    }

    public static final Unit msgTypeHandler$lambda$9(MiEvent to, String key, Object value) {
        Intrinsics.checkNotNullParameter(to, "$this$to");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        to.addFloatArray(key, (float[]) value);
        return Unit.INSTANCE;
    }

    public final void appendMiEventMessage(MiEvent miEvent, Map messageMap) {
        Intrinsics.checkNotNullParameter(miEvent, "<this>");
        Intrinsics.checkNotNullParameter(messageMap, "messageMap");
        for (Map.Entry entry : messageMap.entrySet()) {
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                Function3 function3 = (Function3) msgTypeHandler.get(Reflection.getOrCreateKotlinClass(value.getClass()));
                if (function3 != null) {
                    function3.invoke(miEvent, str, value);
                } else {
                    Log.e("MiSightHelper", "appendMiEventMessage: Unrecognized message type. Failed adding to miEvent!");
                }
            } else {
                Log.e("MiSightHelper", "appendMiEventMessage: Value is empty! Skip this message: key = " + str);
            }
        }
    }

    public final void applyMiEventBaseInfo(MiEvent miEvent) {
        Intrinsics.checkNotNullParameter(miEvent, "<this>");
        miEvent.addStr("currentTime", LocalDateTime.now().toString());
    }

    public final void createAndSendEvent(int i, Map messageMap) {
        Intrinsics.checkNotNullParameter(messageMap, "messageMap");
        MiEvent miEvent = new MiEvent(i);
        applyMiEventBaseInfo(miEvent);
        appendMiEventMessage(miEvent, messageMap);
        MiSight.sendEvent(miEvent);
    }
}

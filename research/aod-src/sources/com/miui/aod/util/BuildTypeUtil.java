package com.miui.aod.util;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import java.util.List;
import java.util.ListIterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: BuildTypeUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class BuildTypeUtil {
    public static final BuildTypeUtil INSTANCE = new BuildTypeUtil();
    public static final Regex DIGIT_PATTERN = new Regex("^\\d+$");
    public static final Lazy OS_VERSION$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.util.BuildTypeUtil$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return BuildTypeUtil.OS_VERSION_delegate$lambda$0();
        }
    });

    public static final String OS_VERSION_delegate$lambda$0() {
        return SystemProperties.get("rro.build.version.incremental", "");
    }

    public static final boolean isDebugOrBeta() {
        Object objPrevious;
        if (Intrinsics.areEqual("userdebug", Build.TYPE)) {
            return true;
        }
        BuildTypeUtil buildTypeUtil = INSTANCE;
        if (TextUtils.isEmpty(buildTypeUtil.getOS_VERSION()) || Intrinsics.areEqual("0.0.0", buildTypeUtil.getOS_VERSION())) {
            return true;
        }
        List listSplit$default = StringsKt.split$default(buildTypeUtil.getOS_VERSION(), new String[]{"."}, false, 0, 6, null);
        ListIterator listIterator = listSplit$default.listIterator(listSplit$default.size());
        do {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
        } while (!DIGIT_PATTERN.matches((String) objPrevious));
        String str = (String) objPrevious;
        if (str != null) {
            return !Intrinsics.areEqual(str, "0");
        }
        return false;
    }

    public final String getOS_VERSION() {
        Object value = OS_VERSION$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (String) value;
    }
}

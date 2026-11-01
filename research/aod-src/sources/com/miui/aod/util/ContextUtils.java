package com.miui.aod.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ContextUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class ContextUtils {
    public static final ContextUtils INSTANCE = new ContextUtils();

    public final Object fixClassLoader(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        try {
            ContextUtils contextUtils = INSTANCE;
            Resources resources = context.getResources();
            Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
            ClassLoader classLoader = context.getClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
            contextUtils.fixClassLoader(resources, classLoader);
            return Unit.INSTANCE;
        } catch (Exception e) {
            return Integer.valueOf(Log.e("ContextUtils", "fix classloader for resources & drawable inflater of plugin context failed: " + e));
        }
    }

    public final void fixClassLoader(Resources resources, ClassLoader classLoader) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Field declaredField = Resources.class.getDeclaredField("mClassLoader");
        declaredField.setAccessible(true);
        declaredField.set(resources, classLoader);
        Constructor<?> constructor = Class.forName("android.graphics.drawable.DrawableInflater").getConstructor(Resources.class, ClassLoader.class);
        constructor.setAccessible(true);
        Object objNewInstance = constructor.newInstance(resources, classLoader);
        Field declaredField2 = Resources.class.getDeclaredField("mDrawableInflater");
        declaredField2.setAccessible(true);
        declaredField2.set(resources, objNewInstance);
    }
}

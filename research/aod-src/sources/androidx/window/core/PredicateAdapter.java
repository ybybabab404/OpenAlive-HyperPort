package androidx.window.core;

import android.annotation.SuppressLint;
import android.util.Pair;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClasses;

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
/* JADX INFO: compiled from: PredicateAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SuppressLint({"BanUncheckedReflection"})
public final class PredicateAdapter {
    public final ClassLoader loader;

    /* JADX INFO: compiled from: PredicateAdapter.kt */
    @Metadata
    public static abstract class BaseHandler<T> implements InvocationHandler {
        public final KClass clazz;

        public BaseHandler(KClass clazz) {
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            this.clazz = clazz;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) {
            Intrinsics.checkNotNullParameter(obj, "obj");
            Intrinsics.checkNotNullParameter(method, "method");
            if (isTest(method, objArr)) {
                return Boolean.valueOf(invokeTest(obj, KClasses.cast(this.clazz, objArr != null ? objArr[0] : null)));
            }
            if (isEquals(method, objArr)) {
                Object obj2 = objArr != null ? objArr[0] : null;
                Intrinsics.checkNotNull(obj2);
                return Boolean.valueOf(obj == obj2);
            }
            if (isHashCode(method, objArr)) {
                return Integer.valueOf(hashCode());
            }
            if (isToString(method, objArr)) {
                return toString();
            }
            throw new UnsupportedOperationException("Unexpected method call object:" + obj + ", method: " + method + ", args: " + objArr);
        }

        public abstract boolean invokeTest(Object obj, Object obj2);

        public final boolean isEquals(Method method, Object[] objArr) {
            Intrinsics.checkNotNullParameter(method, "<this>");
            return Intrinsics.areEqual(method.getName(), "equals") && method.getReturnType().equals(Boolean.TYPE) && objArr != null && objArr.length == 1;
        }

        public final boolean isHashCode(Method method, Object[] objArr) {
            Intrinsics.checkNotNullParameter(method, "<this>");
            return Intrinsics.areEqual(method.getName(), "hashCode") && method.getReturnType().equals(Integer.TYPE) && objArr == null;
        }

        public final boolean isTest(Method method, Object[] objArr) {
            Intrinsics.checkNotNullParameter(method, "<this>");
            return Intrinsics.areEqual(method.getName(), "test") && method.getReturnType().equals(Boolean.TYPE) && objArr != null && objArr.length == 1;
        }

        public final boolean isToString(Method method, Object[] objArr) {
            Intrinsics.checkNotNullParameter(method, "<this>");
            return Intrinsics.areEqual(method.getName(), "toString") && method.getReturnType().equals(String.class) && objArr == null;
        }
    }

    /* JADX INFO: compiled from: PredicateAdapter.kt */
    @Metadata
    public static final class PairPredicateStubHandler<T, U> extends BaseHandler<Pair<?, ?>> {
        public final KClass clazzT;
        public final KClass clazzU;
        public final Function2 predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PairPredicateStubHandler(KClass clazzT, KClass clazzU, Function2 predicate) {
            super(Reflection.getOrCreateKotlinClass(Pair.class));
            Intrinsics.checkNotNullParameter(clazzT, "clazzT");
            Intrinsics.checkNotNullParameter(clazzU, "clazzU");
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            this.clazzT = clazzT;
            this.clazzU = clazzU;
            this.predicate = predicate;
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        @Override // androidx.window.core.PredicateAdapter.BaseHandler
        public boolean invokeTest(Object obj, Pair parameter) {
            Intrinsics.checkNotNullParameter(obj, "obj");
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            return ((Boolean) this.predicate.invoke(KClasses.cast(this.clazzT, parameter.first), KClasses.cast(this.clazzU, parameter.second))).booleanValue();
        }

        public String toString() {
            return this.predicate.toString();
        }
    }

    /* JADX INFO: compiled from: PredicateAdapter.kt */
    @Metadata
    public static final class PredicateStubHandler<T> extends BaseHandler<T> {
        public final Function1 predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PredicateStubHandler(KClass clazzT, Function1 predicate) {
            super(clazzT);
            Intrinsics.checkNotNullParameter(clazzT, "clazzT");
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            this.predicate = predicate;
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        @Override // androidx.window.core.PredicateAdapter.BaseHandler
        public boolean invokeTest(Object obj, Object parameter) {
            Intrinsics.checkNotNullParameter(obj, "obj");
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            return ((Boolean) this.predicate.invoke(parameter)).booleanValue();
        }

        public String toString() {
            return this.predicate.toString();
        }
    }

    public PredicateAdapter(ClassLoader loader) {
        Intrinsics.checkNotNullParameter(loader, "loader");
        this.loader = loader;
    }

    public final Object buildPairPredicate(KClass firstClazz, KClass secondClazz, Function2 predicate) {
        Intrinsics.checkNotNullParameter(firstClazz, "firstClazz");
        Intrinsics.checkNotNullParameter(secondClazz, "secondClazz");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Object objNewProxyInstance = Proxy.newProxyInstance(this.loader, new Class[]{predicateClassOrThrow()}, new PairPredicateStubHandler(firstClazz, secondClazz, predicate));
        Intrinsics.checkNotNullExpressionValue(objNewProxyInstance, "newProxyInstance(loader,…row()), predicateHandler)");
        return objNewProxyInstance;
    }

    public final Object buildPredicate(KClass clazz, Function1 predicate) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Object objNewProxyInstance = Proxy.newProxyInstance(this.loader, new Class[]{predicateClassOrThrow()}, new PredicateStubHandler(clazz, predicate));
        Intrinsics.checkNotNullExpressionValue(objNewProxyInstance, "newProxyInstance(loader,…row()), predicateHandler)");
        return objNewProxyInstance;
    }

    public final Class predicateClassOrNull$window_release() {
        try {
            return predicateClassOrThrow();
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public final Class predicateClassOrThrow() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("java.util.function.Predicate");
        Intrinsics.checkNotNullExpressionValue(clsLoadClass, "loader.loadClass(\"java.util.function.Predicate\")");
        return clsLoadClass;
    }
}

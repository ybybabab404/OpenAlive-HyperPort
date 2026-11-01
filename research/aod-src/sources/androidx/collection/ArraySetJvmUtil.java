package androidx.collection;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
class ArraySetJvmUtil {
    public static Object[] resizeForToArray(Object[] objArr, int i) {
        if (objArr.length < i) {
            return (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        }
        if (objArr.length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}

package miui.cloud.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class XDirectCallback<T> extends XCallback<T> {
    public Object mCallback;

    @Override // miui.cloud.common.XCallback
    public Object handleCallback(Method method, Object[] objArr) throws Throwable {
        Object obj = this.mCallback;
        if (obj == null) {
            return null;
        }
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            XLogger.loge("Bad callback. ");
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e2) {
            XLogger.loge("Bad callback. ");
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            if (e3.getCause() == null) {
                throw e3;
            }
            throw e3.getCause();
        }
    }
}

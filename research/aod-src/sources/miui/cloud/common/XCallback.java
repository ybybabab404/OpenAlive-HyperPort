package miui.cloud.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: classes2.dex */
public abstract class XCallback<T> {
    public Class mInterface;

    public final Object asInterface() {
        return Proxy.newProxyInstance(this.mInterface.getClassLoader(), new Class[]{this.mInterface}, new InvocationHandler() { // from class: miui.cloud.common.XCallback.1
            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj, Method method, Object[] objArr) {
                return XCallback.this.handleCallback(method, objArr);
            }
        });
    }

    public abstract Object handleCallback(Method method, Object[] objArr);
}

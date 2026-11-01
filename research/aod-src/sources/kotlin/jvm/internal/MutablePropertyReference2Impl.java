package kotlin.jvm.internal;

/* JADX INFO: loaded from: classes2.dex */
public class MutablePropertyReference2Impl extends MutablePropertyReference2 {
    @Override // kotlin.reflect.KProperty2
    public Object get(Object obj, Object obj2) {
        return getGetter().call(obj, obj2);
    }
}

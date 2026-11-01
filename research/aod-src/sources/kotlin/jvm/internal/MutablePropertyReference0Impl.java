package kotlin.jvm.internal;

/* JADX INFO: loaded from: classes2.dex */
public class MutablePropertyReference0Impl extends MutablePropertyReference0 {
    @Override // kotlin.reflect.KProperty0
    public Object get() {
        return getGetter().call(new Object[0]);
    }
}

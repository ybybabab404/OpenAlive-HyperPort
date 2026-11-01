package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.SinceKotlin;

/* JADX INFO: loaded from: classes2.dex */
@SinceKotlin
public class FunInterfaceConstructorReference extends FunctionReference implements Serializable {
    public final Class funInterface;

    @Override // kotlin.jvm.internal.FunctionReference
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FunInterfaceConstructorReference) {
            return this.funInterface.equals(((FunInterfaceConstructorReference) obj).funInterface);
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionReference
    public int hashCode() {
        return this.funInterface.hashCode();
    }

    @Override // kotlin.jvm.internal.FunctionReference
    public String toString() {
        return "fun interface " + this.funInterface.getName();
    }
}

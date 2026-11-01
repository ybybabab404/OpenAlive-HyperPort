package com.xiaomi.keychainsdk.storage.data;

import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class KeyBagAccount {
    public final short hsid;
    public final long huser;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            KeyBagAccount keyBagAccount = (KeyBagAccount) obj;
            if (this.hsid == keyBagAccount.hsid && this.huser == keyBagAccount.huser) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Short.valueOf(this.hsid), Long.valueOf(this.huser));
    }
}

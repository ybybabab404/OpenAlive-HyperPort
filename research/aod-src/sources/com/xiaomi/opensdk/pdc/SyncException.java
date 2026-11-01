package com.xiaomi.opensdk.pdc;

/* JADX INFO: loaded from: classes2.dex */
public class SyncException extends Exception {
    public String mDescription;

    @Override // java.lang.Throwable
    public String toString() {
        String simpleName = getClass().getSimpleName();
        Throwable cause = getCause();
        if (cause != null) {
            String str = simpleName + "[" + cause.getClass().getSimpleName();
            if (cause.getMessage() != null) {
                str = str + ":" + cause.getMessage();
            }
            simpleName = str + "]";
        }
        if (this.mDescription == null) {
            return simpleName;
        }
        return simpleName + ",  " + this.mDescription;
    }
}

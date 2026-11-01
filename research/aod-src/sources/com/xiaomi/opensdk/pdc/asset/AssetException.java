package com.xiaomi.opensdk.pdc.asset;

/* JADX INFO: loaded from: classes2.dex */
public class AssetException extends Exception {
    public String description;

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
        if (this.description == null) {
            return simpleName;
        }
        return simpleName + ",  " + this.description;
    }
}

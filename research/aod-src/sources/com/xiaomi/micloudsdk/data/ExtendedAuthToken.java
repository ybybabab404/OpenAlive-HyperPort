package com.xiaomi.micloudsdk.data;

import android.text.TextUtils;
import com.xiaomi.onetrack.util.ac;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class ExtendedAuthToken implements IAuthToken {
    public final String authToken;
    public final String security;

    /* JADX INFO: renamed from: com.xiaomi.micloudsdk.data.ExtendedAuthToken$1, reason: invalid class name */
    class AnonymousClass1 implements IAuthToken.HttpRequestBuilder {
    }

    public ExtendedAuthToken(String str, String str2) {
        this.authToken = str;
        this.security = str2;
    }

    public static ExtendedAuthToken parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(ac.b);
        if (strArrSplit.length != 2 || TextUtils.isEmpty(strArrSplit[0]) || TextUtils.isEmpty(strArrSplit[1])) {
            return null;
        }
        return new ExtendedAuthToken(strArrSplit[0], strArrSplit[1]);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ExtendedAuthToken.class != obj.getClass()) {
            return false;
        }
        ExtendedAuthToken extendedAuthToken = (ExtendedAuthToken) obj;
        return Objects.equals(this.authToken, extendedAuthToken.authToken) && Objects.equals(this.security, extendedAuthToken.security);
    }

    public int hashCode() {
        String str = this.authToken;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.security;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }
}

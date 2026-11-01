package cn.kuaipan.android.http;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpRequest {

    /* JADX INFO: renamed from: cn.kuaipan.android.http.KscHttpRequest$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$cn$kuaipan$android$http$KscHttpRequest$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$cn$kuaipan$android$http$KscHttpRequest$HttpMethod = iArr;
            try {
                iArr[HttpMethod.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$http$KscHttpRequest$HttpMethod[HttpMethod.POST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum HttpMethod {
        GET,
        POST
    }
}

package cn.kuaipan.kss;

/* JADX INFO: loaded from: classes.dex */
public class KssDef {

    public enum KssAPIResult {
        OK,
        Error,
        Cancel,
        NetTimeout,
        NeedRequest,
        DataCorrupted,
        SpaceOver,
        ServerDenyReadOnly
    }

    public enum NetState {
        Wifi,
        UNKNOWN,
        MOBILE
    }

    public interface OnUpDownload {
    }
}

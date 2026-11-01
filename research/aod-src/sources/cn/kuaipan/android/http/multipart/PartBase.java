package cn.kuaipan.android.http.multipart;

/* JADX INFO: loaded from: classes.dex */
public abstract class PartBase extends Part {
    public String charSet;
    public String contentType;
    public String name;
    public String transferEncoding;

    @Override // cn.kuaipan.android.http.multipart.Part
    public String getCharSet() {
        return this.charSet;
    }

    @Override // cn.kuaipan.android.http.multipart.Part
    public String getContentType() {
        return this.contentType;
    }

    @Override // cn.kuaipan.android.http.multipart.Part
    public String getName() {
        return this.name;
    }

    @Override // cn.kuaipan.android.http.multipart.Part
    public String getTransferEncoding() {
        return this.transferEncoding;
    }
}

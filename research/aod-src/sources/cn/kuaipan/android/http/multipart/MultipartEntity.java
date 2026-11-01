package cn.kuaipan.android.http.multipart;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;

/* JADX INFO: loaded from: classes.dex */
public class MultipartEntity extends AbstractHttpEntity {
    public static byte[] MULTIPART_CHARS = EncodingUtils.getAsciiBytes("-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public boolean contentConsumed;
    public byte[] multipartBoundary;
    public HttpParams params;
    public Part[] parts;

    public static byte[] generateMultipartBoundary() {
        Random random = new Random();
        int iNextInt = random.nextInt(11) + 30;
        byte[] bArr = new byte[iNextInt];
        for (int i = 0; i < iNextInt; i++) {
            byte[] bArr2 = MULTIPART_CHARS;
            bArr[i] = bArr2[random.nextInt(bArr2.length)];
        }
        return bArr;
    }

    @Override // org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        if (!isRepeatable() && this.contentConsumed) {
            throw new IllegalStateException("Content has been consumed");
        }
        this.contentConsumed = true;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Part.sendParts(byteArrayOutputStream, this.parts, this.multipartBoundary);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Override // org.apache.http.HttpEntity
    public long getContentLength() {
        try {
            return Part.getLengthOfParts(this.parts, getMultipartBoundary());
        } catch (Exception e) {
            Log.e("Multipart", "An exception occurred while getting the length of the parts", e);
            return 0L;
        }
    }

    @Override // org.apache.http.entity.AbstractHttpEntity, org.apache.http.HttpEntity
    public Header getContentType() {
        StringBuffer stringBuffer = new StringBuffer("multipart/form-data");
        stringBuffer.append("; boundary=");
        stringBuffer.append(EncodingUtils.getAsciiString(getMultipartBoundary()));
        return new BasicHeader("Content-Type", stringBuffer.toString());
    }

    public byte[] getMultipartBoundary() {
        if (this.multipartBoundary == null) {
            HttpParams httpParams = this.params;
            String str = httpParams != null ? (String) httpParams.getParameter("http.method.multipart.boundary") : null;
            if (str != null) {
                this.multipartBoundary = EncodingUtils.getAsciiBytes(str);
            } else {
                this.multipartBoundary = generateMultipartBoundary();
            }
        }
        return this.multipartBoundary;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isRepeatable() {
        int i = 0;
        while (true) {
            Part[] partArr = this.parts;
            if (i >= partArr.length) {
                return true;
            }
            if (!partArr[i].isRepeatable()) {
                return false;
            }
            i++;
        }
    }

    @Override // org.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        Part.sendParts(outputStream, this.parts, getMultipartBoundary());
    }
}

package miui.cloud.net;

import com.miui.maml.elements.MusicLyricParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class XMultipartProcessor implements XHttpClient.ISendDataProcessor {
    public final String mBoundary;
    public final String mCharset;

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public String getOutDataContentType(Object obj) {
        return "multipart/form-data;boundary=" + this.mBoundary;
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public int getOutDataLength(Object obj) {
        return -1;
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public void processOutData(Object obj, OutputStream outputStream) throws Throwable {
        for (Map.Entry entry : ((Map) obj).entrySet()) {
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (str == null || value == null) {
                throw new IllegalArgumentException("null key/value");
            }
            if (value instanceof File) {
                File file = (File) entry.getValue();
                writeBytes(outputStream, "--" + this.mBoundary + MusicLyricParser.CRLF);
                writeBytes(outputStream, "Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str + "\"" + MusicLyricParser.CRLF);
                writeBytes(outputStream, "Content-Type: stream/octet\r\n");
                writeBytes(outputStream, "Content-Transfer-Encoding: binary\r\n");
                writeBytes(outputStream, MusicLyricParser.CRLF);
                writeBytes(outputStream, file);
                writeBytes(outputStream, MusicLyricParser.CRLF);
            } else {
                if (!(value instanceof String)) {
                    throw new IllegalArgumentException("bad entry type " + value.getClass().getSimpleName() + " of key " + str);
                }
                writeBytes(outputStream, "--" + this.mBoundary + MusicLyricParser.CRLF);
                writeBytes(outputStream, "Content-Disposition: form-data; name=\"" + str + "\"" + MusicLyricParser.CRLF);
                writeBytes(outputStream, MusicLyricParser.CRLF);
                writeBytes(outputStream, (String) value);
                writeBytes(outputStream, MusicLyricParser.CRLF);
            }
        }
        writeBytes(outputStream, "--" + this.mBoundary + "--");
    }

    public final void writeBytes(OutputStream outputStream, File file) throws Throwable {
        byte[] bArr = new byte[1024];
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            while (true) {
                try {
                    int i = fileInputStream2.read(bArr);
                    if (i == -1) {
                        try {
                            fileInputStream2.close();
                            return;
                        } catch (IOException unused) {
                            return;
                        }
                    }
                    outputStream.write(bArr, 0, i);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void writeBytes(OutputStream outputStream, String str) throws IOException {
        outputStream.write(str.getBytes(this.mCharset));
    }
}

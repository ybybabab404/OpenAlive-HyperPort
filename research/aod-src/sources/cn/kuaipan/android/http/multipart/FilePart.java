package cn.kuaipan.android.http.multipart;

import android.util.Log;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

/* JADX INFO: loaded from: classes.dex */
public class FilePart extends PartBase {
    public static final byte[] FILE_NAME_BYTES = EncodingUtils.getAsciiBytes("; filename=");
    public PartSource source;

    @Override // cn.kuaipan.android.http.multipart.Part
    public long lengthOfData() {
        return this.source.getLength();
    }

    @Override // cn.kuaipan.android.http.multipart.Part
    public void sendData(OutputStream outputStream) throws IOException {
        if (lengthOfData() == 0) {
            Log.d("FilePart", "No data to send.");
            return;
        }
        byte[] bArr = new byte[AutoBottomSheetKt.ALIGN_SCREEN_TOP];
        InputStream inputStreamCreateInputStream = this.source.createInputStream();
        while (true) {
            try {
                int i = inputStreamCreateInputStream.read(bArr);
                if (i < 0) {
                    inputStreamCreateInputStream.close();
                    return;
                }
                outputStream.write(bArr, 0, i);
            } catch (Throwable th) {
                inputStreamCreateInputStream.close();
                throw th;
            }
        }
    }

    @Override // cn.kuaipan.android.http.multipart.Part
    public void sendDispositionHeader(OutputStream outputStream) throws IOException {
        super.sendDispositionHeader(outputStream);
        String fileName = this.source.getFileName();
        if (fileName != null) {
            outputStream.write(FILE_NAME_BYTES);
            byte[] bArr = Part.QUOTE_BYTES;
            outputStream.write(bArr);
            outputStream.write(EncodingUtils.getAsciiBytes(fileName));
            outputStream.write(bArr);
        }
    }
}

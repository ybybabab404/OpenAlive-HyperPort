package cn.kuaipan.android.http;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.HttpEntityWrapper;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpEntity extends HttpEntityWrapper {
    public final IKscDecoder mDecoder;

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public InputStream getContent() {
        return this.mDecoder == null ? super.getContent() : new DecoderInputStream(super.getContent(), this.mDecoder, AutoBottomSheetKt.ALIGN_SCREEN_START);
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream content = getContent();
        byte[] bArr = new byte[AutoBottomSheetKt.ALIGN_SCREEN_START];
        while (true) {
            int i = content.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }
}

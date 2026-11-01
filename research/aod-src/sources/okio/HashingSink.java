package okio;

import java.security.MessageDigest;
import javax.crypto.Mac;

/* JADX INFO: loaded from: classes3.dex */
public final class HashingSink extends ForwardingSink {
    public final Mac mac;
    public final MessageDigest messageDigest;

    @Override // okio.ForwardingSink, okio.Sink
    public void write(Buffer buffer, long j) {
        Util.checkOffsetAndCount(buffer.size, 0L, j);
        Segment segment = buffer.head;
        long j2 = 0;
        while (j2 < j) {
            int iMin = (int) Math.min(j - j2, segment.limit - segment.pos);
            MessageDigest messageDigest = this.messageDigest;
            if (messageDigest != null) {
                messageDigest.update(segment.data, segment.pos, iMin);
            } else {
                this.mac.update(segment.data, segment.pos, iMin);
            }
            j2 += (long) iMin;
            segment = segment.next;
        }
        super.write(buffer, j);
    }
}

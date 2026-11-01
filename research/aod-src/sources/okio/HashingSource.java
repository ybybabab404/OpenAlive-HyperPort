package okio;

import java.security.MessageDigest;
import javax.crypto.Mac;

/* JADX INFO: loaded from: classes3.dex */
public final class HashingSource extends ForwardingSource {
    public final Mac mac;
    public final MessageDigest messageDigest;

    @Override // okio.ForwardingSource, okio.Source
    public long read(Buffer buffer, long j) {
        long j2 = super.read(buffer, j);
        if (j2 != -1) {
            long j3 = buffer.size;
            long j4 = j3 - j2;
            Segment segment = buffer.head;
            while (j3 > j4) {
                segment = segment.prev;
                j3 -= (long) (segment.limit - segment.pos);
            }
            while (j3 < buffer.size) {
                int i = (int) ((((long) segment.pos) + j4) - j3);
                MessageDigest messageDigest = this.messageDigest;
                if (messageDigest != null) {
                    messageDigest.update(segment.data, i, segment.limit - i);
                } else {
                    this.mac.update(segment.data, i, segment.limit - i);
                }
                j4 = ((long) (segment.limit - segment.pos)) + j3;
                segment = segment.next;
                j3 = j4;
            }
        }
        return j2;
    }
}

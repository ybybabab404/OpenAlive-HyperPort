package androidx.emoji2.text.flatbuffer;

import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public class FlexBuffersBuilder {
    public final ReadWriteBuf bb;

    /* JADX INFO: renamed from: androidx.emoji2.text.flatbuffer.FlexBuffersBuilder$1, reason: invalid class name */
    public class AnonymousClass1 implements Comparator<Value> {
        public final /* synthetic */ FlexBuffersBuilder this$0;

        @Override // java.util.Comparator
        public int compare(Value value, Value value2) {
            byte b;
            byte b2;
            int i = value.key;
            int i2 = value2.key;
            do {
                b = this.this$0.bb.get(i);
                b2 = this.this$0.bb.get(i2);
                if (b == 0) {
                    return b - b2;
                }
                i++;
                i2++;
            } while (b == b2);
            return b - b2;
        }
    }

    public static class Value {
        public int key;
    }
}

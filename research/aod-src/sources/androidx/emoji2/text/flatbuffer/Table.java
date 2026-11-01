package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public class Table {
    public ByteBuffer bb;
    public int bb_pos;
    public Utf8 utf8 = Utf8.getDefault();
    public int vtable_size;
    public int vtable_start;

    /* JADX INFO: renamed from: androidx.emoji2.text.flatbuffer.Table$1, reason: invalid class name */
    class AnonymousClass1 implements Comparator<Integer> {
        public final /* synthetic */ Table this$0;
        public final /* synthetic */ ByteBuffer val$bb;

        @Override // java.util.Comparator
        public int compare(Integer num, Integer num2) {
            return this.this$0.keysCompare(num, num2, this.val$bb);
        }
    }

    public int __indirect(int i) {
        return i + this.bb.getInt(i);
    }

    public int __offset(int i) {
        if (i < this.vtable_size) {
            return this.bb.getShort(this.vtable_start + i);
        }
        return 0;
    }

    public void __reset(int i, ByteBuffer byteBuffer) {
        this.bb = byteBuffer;
        if (byteBuffer == null) {
            this.bb_pos = 0;
            this.vtable_start = 0;
            this.vtable_size = 0;
        } else {
            this.bb_pos = i;
            int i2 = i - byteBuffer.getInt(i);
            this.vtable_start = i2;
            this.vtable_size = this.bb.getShort(i2);
        }
    }

    public int __vector(int i) {
        int i2 = i + this.bb_pos;
        return i2 + this.bb.getInt(i2) + 4;
    }

    public int __vector_len(int i) {
        int i2 = i + this.bb_pos;
        return this.bb.getInt(i2 + this.bb.getInt(i2));
    }

    public int keysCompare(Integer num, Integer num2, ByteBuffer byteBuffer) {
        return 0;
    }
}

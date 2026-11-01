package androidx.emoji2.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class TypefaceEmojiSpan extends EmojiSpan {
    public static Paint sDebugPaint;

    public TypefaceEmojiSpan(EmojiMetadata emojiMetadata) {
        super(emojiMetadata);
    }

    public static Paint getDebugPaint() {
        if (sDebugPaint == null) {
            TextPaint textPaint = new TextPaint();
            sDebugPaint = textPaint;
            textPaint.setColor(EmojiCompat.get().getEmojiSpanIndicatorColor());
            sDebugPaint.setStyle(Paint.Style.FILL);
        }
        return sDebugPaint;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        Canvas canvas2;
        float f2;
        if (EmojiCompat.get().isEmojiSpanIndicatorEnabled()) {
            canvas2 = canvas;
            f2 = f;
            canvas2.drawRect(f2, i3, f + getWidth(), i5, getDebugPaint());
        } else {
            canvas2 = canvas;
            f2 = f;
        }
        getMetadata().draw(canvas2, f2, i4, paint);
    }
}

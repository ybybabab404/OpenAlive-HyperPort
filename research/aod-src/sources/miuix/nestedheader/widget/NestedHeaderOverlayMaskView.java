package miuix.nestedheader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
class NestedHeaderOverlayMaskView extends View {
    public NestedHeaderStickyMaskImpl mStickyMaskImpl;

    public NestedHeaderOverlayMaskView(Context context) {
        super(context);
    }

    public NestedHeaderOverlayMaskView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NestedHeaderOverlayMaskView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final void drawStickyMaskIfNeed(Canvas canvas) {
        if (isStickyMaskEnabled()) {
            getStickyMaskImpl().drawOverlayMask(canvas);
        }
    }

    public final NestedHeaderStickyMaskImpl getStickyMaskImpl() {
        if (this.mStickyMaskImpl == null) {
            this.mStickyMaskImpl = new NestedHeaderStickyMaskImpl(this);
        }
        return this.mStickyMaskImpl;
    }

    public boolean isStickyMaskEnabled() {
        return getStickyMaskImpl().isStickyMaskEnabled();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStickyMaskIfNeed(canvas);
    }

    public void setMaskColor(int i) {
        getStickyMaskImpl().setMaskColor(i);
    }

    public void setMaskExtraHeight(float f) {
        getStickyMaskImpl().setMaskExtraHeight(f);
    }

    public void setStickyMaskEnabled(boolean z) {
        getStickyMaskImpl().setStickyMaskEnabled(z);
    }
}

package com.miui.clock.tiny.pets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import org.libpag.PAGImageView;

/* JADX INFO: loaded from: classes.dex */
public class ChargePagView extends PagViewBase {
    public boolean backgroundAlignLeft;
    public volatile Bitmap backgroundBitmap;
    public final Paint paint;
    public int peatCount;
    public int peatFrame;

    /* JADX INFO: renamed from: com.miui.clock.tiny.pets.ChargePagView$1, reason: invalid class name */
    class AnonymousClass1 implements PAGImageView.PAGImageViewListener {
    }

    public ChargePagView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.peatCount = -1;
        this.peatFrame = 0;
        this.backgroundBitmap = null;
        this.paint = new Paint();
        this.backgroundAlignLeft = false;
        this.backgroundAlignLeft = this.mRotation == 0;
    }
}

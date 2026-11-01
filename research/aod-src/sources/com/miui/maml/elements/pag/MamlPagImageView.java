package com.miui.maml.elements.pag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.util.MamlLog;
import org.libpag.PAGImageView;

/* JADX INFO: loaded from: classes2.dex */
public class MamlPagImageView extends PAGImageView {
    public static final String LOG_TAG = "MamlPagImageView";
    private ScreenElementRoot mRoot;

    public MamlPagImageView(Context context) {
        super(context);
    }

    public MamlPagImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MamlPagImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void renderCurrentFrame(Canvas canvas) {
        canvas.save();
        Bitmap bitmapCurrentImage = currentImage();
        if (bitmapCurrentImage == null || bitmapCurrentImage.isRecycled()) {
            MamlLog.w(LOG_TAG, "currentImage is null/isRecycled");
        } else {
            canvas.drawBitmap(bitmapCurrentImage.copy(Bitmap.Config.ARGB_8888, true), 0.0f, 0.0f, new Paint());
        }
        canvas.restore();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot == null || Math.abs(screenElementRoot.getVariables().getDouble("drawPagImageView") - 1.0d) >= 1.0E-5d) {
            return;
        }
        MamlLog.w(LOG_TAG, "drawPagImageView");
        renderCurrentFrame(canvas);
    }

    public void setScreenElementRoot(ScreenElementRoot screenElementRoot) {
        this.mRoot = screenElementRoot;
    }
}

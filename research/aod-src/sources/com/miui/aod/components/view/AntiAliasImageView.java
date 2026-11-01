package com.miui.aod.components.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import com.miui.aod.widget.GradientImageView;

/* JADX INFO: loaded from: classes.dex */
public class AntiAliasImageView extends GradientImageView {
    private final PaintFlagsDrawFilter mPaintFlagsDrawFilter;

    public AntiAliasImageView(Context context) {
        this(context, null);
    }

    public AntiAliasImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AntiAliasImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
    }

    @Override // com.miui.aod.widget.GradientImageView, android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        super.onDraw(canvas);
    }
}

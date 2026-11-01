package com.miui.maml.elements.video;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
class NormalVideoView extends BaseVideoView {
    public NormalVideoView(Context context) {
        super(context);
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    public void addSurfaceHolderCallback() {
        getHolder().addCallback(this.mSHCallback);
    }

    @Override // android.view.SurfaceView, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        if (this.mScaleMode != 3) {
            super.onMeasure(i, i2);
            return;
        }
        int defaultSize = SurfaceView.getDefaultSize(this.mVideoWidth, i);
        int defaultSize2 = SurfaceView.getDefaultSize(this.mVideoHeight, i2);
        if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size2 = View.MeasureSpec.getSize(i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                int i4 = this.mVideoWidth;
                int i5 = i4 * size2;
                int i6 = this.mVideoHeight;
                if (i5 < size * i6) {
                    defaultSize = (i4 * size2) / i6;
                } else {
                    if (i4 * size2 > size * i6) {
                        defaultSize2 = (i6 * size) / i4;
                        defaultSize = size;
                    }
                    defaultSize = size;
                }
                defaultSize2 = size2;
            } else if (mode == 1073741824) {
                int i7 = (this.mVideoHeight * size) / this.mVideoWidth;
                if (mode2 != Integer.MIN_VALUE || i7 <= size2) {
                    defaultSize2 = i7;
                    defaultSize = size;
                }
                defaultSize = size;
                defaultSize2 = size2;
            } else {
                if (mode2 == 1073741824) {
                    i3 = (this.mVideoWidth * size2) / this.mVideoHeight;
                    if (mode == Integer.MIN_VALUE && i3 > size) {
                        defaultSize = size;
                    }
                    defaultSize2 = size2;
                } else {
                    int i8 = this.mVideoWidth;
                    int i9 = this.mVideoHeight;
                    if (mode2 != Integer.MIN_VALUE || i9 <= size2) {
                        i3 = i8;
                        size2 = i9;
                    } else {
                        i3 = (size2 * i8) / i9;
                    }
                    if (mode == Integer.MIN_VALUE && i3 > size) {
                        defaultSize2 = (i9 * size) / i8;
                        defaultSize = size;
                    }
                }
                defaultSize = i3;
                defaultSize2 = size2;
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurface = surfaceHolder.getSurface();
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    public void onSurfaceDestroyed() {
        this.mSurface = null;
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    public void setFormat(int i) {
        getHolder().setFormat(i);
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    public void updateVideoSize() {
        requestLayout();
    }
}

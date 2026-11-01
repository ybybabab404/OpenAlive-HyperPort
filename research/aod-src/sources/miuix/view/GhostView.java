package miuix.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RenderNode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.RequiresApi;
import miuix.core.R;

/* JADX INFO: loaded from: classes3.dex */
@RequiresApi
public class GhostView extends View {
    public int bottom;
    public int left;
    public View mOriginalView;
    public ViewGroup mOverlay;
    public RenderNode mRenderNode;
    public int right;
    public int top;

    public GhostView(Context context, View view) {
        super(context);
        this.mOriginalView = view;
        this.mRenderNode = new RenderNode("GhostView");
        int width = view.getWidth();
        int height = view.getHeight();
        this.right = width;
        this.bottom = height;
        this.mRenderNode.setPosition(0, 0, width, height);
        recordViewContent();
    }

    public static GhostView addGhost(View view, ViewGroup viewGroup) {
        int i = R.id.miuix_ghost_view_tag;
        if (view.getTag(i) instanceof GhostView) {
            return (GhostView) view.getTag(i);
        }
        GhostView ghostView = new GhostView(view.getContext(), view);
        view.setTag(i, ghostView);
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        layoutParams.leftMargin = iArr[0];
        layoutParams.topMargin = iArr[1];
        layoutParams.gravity = 51;
        ghostView.setLayoutParams(layoutParams);
        viewGroup.addView(ghostView);
        ghostView.mOverlay = viewGroup;
        return ghostView;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!canvas.isHardwareAccelerated() || this.mRenderNode == null) {
            return;
        }
        updateContent();
        canvas.drawRenderNode(this.mRenderNode);
    }

    public final void recordViewContent() {
        this.mRenderNode.setPosition(0, 0, this.right - this.left, this.bottom - this.top);
        RenderNode renderNode = this.mRenderNode;
        if (renderNode == null || this.mOriginalView == null) {
            return;
        }
        this.mOriginalView.draw(renderNode.beginRecording());
        this.mRenderNode.endRecording();
    }

    public void removeGhost() {
        this.mOverlay.removeView(this);
        RenderNode renderNode = this.mRenderNode;
        if (renderNode != null) {
            renderNode.discardDisplayList();
            this.mRenderNode = null;
        }
        View view = this.mOriginalView;
        if (view != null) {
            view.setTag(R.id.miuix_ghost_view_tag, null);
            this.mOriginalView = null;
        }
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
        setLeftTopRightBottom(i, i2, i3, i4);
    }

    public void updateContent() {
        recordViewContent();
        invalidate();
    }
}

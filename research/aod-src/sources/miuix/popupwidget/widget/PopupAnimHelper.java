package miuix.popupwidget.widget;

import android.view.View;
import androidx.annotation.RequiresApi;
import miuix.view.ITransformableView;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class PopupAnimHelper {
    public final View mAnchorView;
    public final IPopupAnim mPopupAnim;

    public PopupAnimHelper(View view, View view2) {
        this.mAnchorView = view2;
        if (view2 instanceof IPopupAnimView) {
            this.mPopupAnim = new ArcAnimation(view);
        } else {
            this.mPopupAnim = new DirectionAnimation(view);
        }
        initListener();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PopupAnimHelper(View view, View view2, ITransformableView iTransformableView) {
        this.mAnchorView = view2;
        if (view2 instanceof ITransformableView) {
            this.mPopupAnim = new TransformAnimation((ITransformableView) view2, iTransformableView);
        } else if (view2 instanceof IPopupAnimView) {
            this.mPopupAnim = new ArcAnimation(view);
        } else {
            this.mPopupAnim = new DirectionAnimation(view);
        }
        initListener();
    }

    public void addPopupAnimationListener(IPopupAnimationListener iPopupAnimationListener) {
        this.mPopupAnim.addPopupAnimationListener(iPopupAnimationListener);
    }

    public void dismiss() {
        this.mPopupAnim.dismissWithAnim();
    }

    public int getAnimGravity() {
        IPopupAnim iPopupAnim = this.mPopupAnim;
        if (iPopupAnim instanceof DirectionAnimation) {
            return ((DirectionAnimation) iPopupAnim).getAnimGravity();
        }
        if (iPopupAnim instanceof ArcAnimation) {
            return ((ArcAnimation) iPopupAnim).getAnimGravity();
        }
        return 0;
    }

    public final void initListener() {
        this.mPopupAnim.addPopupAnimationListener(new PopupAnimationListener() { // from class: miuix.popupwidget.widget.PopupAnimHelper.1
            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onForwardAnimationStart() {
                if (PopupAnimHelper.this.mAnchorView instanceof IPopupAnimView) {
                    ((IPopupAnimView) PopupAnimHelper.this.mAnchorView).applyAnchorTransition();
                }
            }

            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onReverseAnimationEnd() {
                if (PopupAnimHelper.this.mAnchorView instanceof IPopupAnimView) {
                    ((IPopupAnimView) PopupAnimHelper.this.mAnchorView).revertAnchorTransition();
                }
            }
        });
    }

    public void removePopupAnimationListener(IPopupAnimationListener iPopupAnimationListener) {
        this.mPopupAnim.removePopupAnimationListener(iPopupAnimationListener);
    }

    public void resetFromContentView() {
        IPopupAnim iPopupAnim = this.mPopupAnim;
        if (iPopupAnim instanceof TransformAnimation) {
            ((TransformAnimation) iPopupAnim).resetFromContentView();
        }
    }

    public void setBlurEnabled(boolean z) {
        this.mPopupAnim.setBlurEnabled(z);
    }

    public void setBlurView(View view) {
        this.mPopupAnim.setBlurView(view);
    }

    public void setCrossWindow(boolean z) {
        IPopupAnim iPopupAnim = this.mPopupAnim;
        if (iPopupAnim instanceof TransformAnimation) {
            ((TransformAnimation) iPopupAnim).setCrossWindow(z);
        }
    }

    public void setGravity(int i) {
        this.mPopupAnim.setGravity(i);
    }

    public void setOffset(int i, int i2) {
        IPopupAnim iPopupAnim = this.mPopupAnim;
        if (iPopupAnim instanceof DirectionAnimation) {
            ((DirectionAnimation) iPopupAnim).setOffset(i, i2);
        }
    }

    public void show() {
        this.mPopupAnim.showWithAnim();
    }

    public void update(int i) {
        this.mPopupAnim.update(i);
    }

    public void updateAnimationOptions() {
        IPopupAnim iPopupAnim = this.mPopupAnim;
        if (iPopupAnim instanceof TransformAnimation) {
            ((TransformAnimation) iPopupAnim).updateAnimationOptions();
        }
    }
}

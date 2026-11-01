package miuix.appcompat.app;

import android.content.Context;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.PopupWindow;
import miuix.appcompat.R;
import miuix.core.util.MiShadowUtils;
import miuix.popupwidget.widget.PopupAnimHelper;
import miuix.popupwidget.widget.PopupAnimationListener;
import miuix.smooth.SmoothFrameLayout2;
import miuix.theme.token.ShadowToken;

/* JADX INFO: loaded from: classes2.dex */
class SearchPopwindow extends PopupWindow {
    public final Context mContext;
    public float mElevationPx;
    public boolean mIsTransitioningToDismiss;
    public PopupAnimHelper mPopupAnimHelper;

    public final void applyPopupShadow(View view) {
        if (view == null) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
        installRoundRectOutline(view);
        if (!MiShadowUtils.SUPPORT_MI_SHADOW) {
            view.setElevation(this.mElevationPx);
        } else {
            ShadowToken shadowToken = ShadowToken.Regular;
            MiShadowUtils.setMiShadow(view, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius);
        }
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (isShowing()) {
            dismissWithAnim();
        }
    }

    public final void dismissWithAnim() {
        if (this.mIsTransitioningToDismiss) {
            return;
        }
        this.mIsTransitioningToDismiss = true;
        PopupAnimHelper popupAnimHelper = this.mPopupAnimHelper;
        if (popupAnimHelper != null) {
            popupAnimHelper.addPopupAnimationListener(new PopupAnimationListener() { // from class: miuix.appcompat.app.SearchPopwindow.3
                @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
                public void onReverseAnimationEnd() {
                    SearchPopwindow.this.mPopupAnimHelper.removePopupAnimationListener(this);
                    SearchPopwindow.super.dismiss();
                    SearchPopwindow.this.mIsTransitioningToDismiss = false;
                }
            });
            this.mPopupAnimHelper.dismiss();
        } else {
            super.dismiss();
            this.mIsTransitioningToDismiss = false;
        }
    }

    public final void installRoundRectOutline(View view) {
        final float dimension = this.mContext.getResources().getDimension(R.dimen.miuix_appcompat_search_popup_bg_radius);
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: miuix.appcompat.app.SearchPopwindow.1
            /* JADX WARN: Code duplicated, block: B:12:0x0021  */
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                float f;
                if (view2.getWidth() == 0 || view2.getHeight() == 0) {
                    return;
                }
                float f2 = dimension;
                if (view2 instanceof SmoothFrameLayout2) {
                    float cornerRadius = ((SmoothFrameLayout2) view2).getCornerRadius();
                    if (cornerRadius > 0.0f) {
                        f = cornerRadius;
                    } else {
                        f = f2;
                    }
                } else {
                    f = f2;
                }
                outline.setRoundRect(0, 0, view2.getWidth(), view2.getHeight(), f);
            }
        });
    }

    @Override // android.widget.PopupWindow
    public void setContentView(View view) {
        super.setContentView(view);
        applyPopupShadow(view);
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2) {
        super.showAsDropDown(view, i, i2);
        startWithAnim(view);
    }

    public void startWithAnim(View view) {
        View contentView = getContentView();
        if (contentView == null) {
            return;
        }
        if (this.mPopupAnimHelper == null) {
            this.mPopupAnimHelper = new PopupAnimHelper(contentView, view);
        }
        this.mPopupAnimHelper.addPopupAnimationListener(new PopupAnimationListener() { // from class: miuix.appcompat.app.SearchPopwindow.2
            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onForwardAnimationCancel() {
                onForwardAnimationEnd();
            }

            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onForwardAnimationEnd() {
                SearchPopwindow.this.mPopupAnimHelper.removePopupAnimationListener(this);
            }
        });
        this.mPopupAnimHelper.setGravity(49);
        this.mPopupAnimHelper.show();
    }

    @Override // android.widget.PopupWindow
    public void update() {
        if (this.mIsTransitioningToDismiss) {
            return;
        }
        super.update();
    }

    @Override // android.widget.PopupWindow
    public void update(int i, int i2) {
        if (this.mIsTransitioningToDismiss) {
            return;
        }
        super.update(i, i2);
    }

    @Override // android.widget.PopupWindow
    public void update(int i, int i2, int i3, int i4) {
        if (this.mIsTransitioningToDismiss) {
            return;
        }
        super.update(i, i2, i3, i4);
    }

    @Override // android.widget.PopupWindow
    public void update(int i, int i2, int i3, int i4, boolean z) {
        if (this.mIsTransitioningToDismiss) {
            return;
        }
        super.update(i, i2, i3, i4, z);
    }
}

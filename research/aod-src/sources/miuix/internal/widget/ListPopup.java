package miuix.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import miuix.animation.ViewHoverListener;
import miuix.animation.utils.LogUtils;
import miuix.appcompat.R;
import miuix.core.util.EnvStateManager;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.WindowUtils;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.ViewUtils;
import miuix.popupwidget.internal.util.SinglePopControl;
import miuix.theme.token.DimToken;

/* JADX INFO: loaded from: classes2.dex */
public class ListPopup extends PopupWindow {
    public ListAdapter mAdapter;
    public WeakReference mAnchor;
    public final Rect mBackgroundPadding;
    public ContentSize mContentSize;
    public View mContentView;
    public Context mContext;
    public int mDropDownGravity;
    public int mElevation;
    public int mElevationExtra;
    public WeakReference mFenceDecor;
    public boolean mHasShadow;
    public ListView mListView;
    public int mMaxAllowedHeight;
    public int mMaxAllowedWidth;
    public int mMinAllowedWidth;
    public int mMinSafeInset;
    public int mOffsetX;
    public boolean mOffsetXSet;
    public int mOffsetY;
    public boolean mOffsetYSet;
    public PopupWindow.OnDismissListener mOnDismissListener;
    public Rect mPositionSafeInsets;
    public FrameLayout mRootView;
    public int mShadowColor;
    public int mUserAnimationGravity;

    /* JADX INFO: renamed from: miuix.internal.widget.ListPopup$1, reason: invalid class name */
    public class AnonymousClass1 extends DataSetObserver {
        public final /* synthetic */ ListPopup this$0;

        public static /* synthetic */ void $r8$lambda$bH29sQw6qqLiqqHoRuFm53mBXcU(AnonymousClass1 anonymousClass1, View view) {
            FrameLayout frameLayout = anonymousClass1.this$0.mRootView;
            if (frameLayout == null || !frameLayout.isAttachedToWindow()) {
                return;
            }
            anonymousClass1.this$0.updatePosition(view);
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            final View anchor;
            this.this$0.mContentSize.mHasContentWidth = false;
            if (!this.this$0.isShowing() || (anchor = this.this$0.getAnchor()) == null) {
                return;
            }
            anchor.post(new Runnable() { // from class: miuix.internal.widget.ListPopup$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ListPopup.AnonymousClass1.$r8$lambda$bH29sQw6qqLiqqHoRuFm53mBXcU(this.f$0, anchor);
                }
            });
        }
    }

    /* JADX INFO: renamed from: miuix.internal.widget.ListPopup$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnLayoutChangeListener {
        public int lastContentHeight;
        public final /* synthetic */ ListPopup this$0;

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            boolean zIsNeedScroll;
            int measuredHeight = this.this$0.mContentView.getMeasuredHeight();
            int i9 = this.lastContentHeight;
            if (i9 == -1 || i9 != measuredHeight) {
                if (this.this$0.mListView.getAdapter() != null) {
                    View anchor = this.this$0.getAnchor();
                    Rect rect = new Rect();
                    if (anchor != null) {
                        ViewUtils.getBoundsInWindow(this.this$0.getDecorView(anchor), rect);
                    } else {
                        Point point = EnvStateManager.getWindowInfo(this.this$0.mContext).windowSize;
                        rect.set(0, 0, point.x, point.y);
                    }
                    zIsNeedScroll = this.this$0.isNeedScroll(i4 - i2, rect);
                } else {
                    zIsNeedScroll = true;
                }
                this.this$0.mContentView.setEnabled(zIsNeedScroll);
                this.this$0.mListView.setVerticalScrollBarEnabled(zIsNeedScroll);
                this.lastContentHeight = measuredHeight;
            }
        }
    }

    /* JADX INFO: renamed from: miuix.internal.widget.ListPopup$4, reason: invalid class name */
    class AnonymousClass4 implements View.OnTouchListener {
        public int lastIndex;
        public final /* synthetic */ ListPopup this$0;

        public static /* synthetic */ void $r8$lambda$s8jdeB0556g2CLbPum1Jn4MVnPo(View view) {
            if (view instanceof ViewGroup) {
                try {
                    int childCount = ((ViewGroup) view).getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        ((ViewGroup) view).getChildAt(i).setPressed(false);
                    }
                } catch (Exception e) {
                    Log.e("ListPopupWindow", "list onTouch error " + e);
                }
            }
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(final View view, MotionEvent motionEvent) {
            int firstVisiblePosition;
            int i;
            int iPointToPosition = this.this$0.mListView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            int action = motionEvent.getAction();
            if (action != 0) {
                if (action == 1 || action == 3 || action == 6) {
                    this.lastIndex = -1;
                    this.this$0.mListView.postDelayed(new Runnable() { // from class: miuix.internal.widget.ListPopup$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ListPopup.AnonymousClass4.$r8$lambda$s8jdeB0556g2CLbPum1Jn4MVnPo(view);
                        }
                    }, ViewConfiguration.getPressedStateDuration());
                }
            } else if (iPointToPosition != -1 && (firstVisiblePosition = iPointToPosition - this.this$0.mListView.getFirstVisiblePosition()) != (i = this.lastIndex)) {
                if (i != -1) {
                    this.this$0.mListView.getChildAt(this.lastIndex).setPressed(false);
                }
                this.this$0.mListView.getChildAt(firstVisiblePosition).setPressed(true);
                this.lastIndex = firstVisiblePosition;
            }
            return false;
        }
    }

    public class ContainerView extends FrameLayout {
        public final /* synthetic */ ListPopup this$0;

        @Override // android.view.View
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            this.this$0.configurationChanged(configuration);
        }
    }

    public static class ContentSize {
        public boolean mHasContentWidth;
        public int mHeight;
        public int mWidth;

        public String toString() {
            return "ContentSize{ w= " + this.mWidth + " h= " + this.mHeight + " }";
        }

        public void updateWidth(int i) {
            this.mWidth = i;
            this.mHasContentWidth = true;
        }
    }

    public static void changeWindowBackground(View view) {
        WindowManager.LayoutParams layoutParams;
        if (view == null || (layoutParams = (WindowManager.LayoutParams) view.getLayoutParams()) == null) {
            return;
        }
        layoutParams.flags |= 2;
        layoutParams.dimAmount = ViewUtils.isNightMode(view.getContext()) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT;
        ((WindowManager) view.getContext().getSystemService("window")).updateViewLayout(view, layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getAnchor() {
        WeakReference weakReference = this.mAnchor;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    public final int calculateXoffset(int i, Rect rect, Rect rect2) {
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mDropDownGravity, i) & 7;
        if (absoluteGravity != 1) {
            return absoluteGravity != 5 ? calculateXoffsetAlignLeft(rect, rect2) : calculateXoffsetAlignRight(rect, rect2);
        }
        return calculateXoffsetAlignCenterHorizontal(rect, rect2);
    }

    public final int calculateXoffsetAlignCenterHorizontal(Rect rect, Rect rect2) {
        int i;
        boolean z;
        int iCenterX = rect.centerX();
        int i2 = rect.left;
        int i3 = this.mContentSize.mWidth;
        int i4 = i2 + i3;
        int i5 = (i3 / 2) + i2;
        int i6 = rect2.right;
        Rect rect3 = this.mPositionSafeInsets;
        int i7 = rect3.right;
        if (i4 > i6 - i7) {
            i = (i6 - i7) - i4;
            z = true;
        } else {
            i = 0;
            z = false;
        }
        if (!z) {
            int i8 = iCenterX - i5;
            if (i2 + i8 >= rect2.left + rect3.left) {
                return i8;
            }
        }
        return i;
    }

    public final int calculateXoffsetAlignLeft(Rect rect, Rect rect2) {
        boolean z;
        int i;
        int i2 = rect.left;
        boolean z2 = this.mOffsetXSet;
        int i3 = (z2 ? this.mOffsetX : 0) + i2 + this.mContentSize.mWidth;
        int i4 = rect2.right;
        Rect rect3 = this.mPositionSafeInsets;
        int i5 = rect3.right;
        if (i3 > i4 - i5) {
            i = (i4 - i5) - i3;
            z = true;
        } else {
            z = false;
            i = 0;
        }
        if (z) {
            return i;
        }
        int i6 = z2 ? this.mOffsetX : 0;
        int i7 = i2 + i6;
        int i8 = rect2.left;
        int i9 = rect3.left;
        if (i7 < i8 + i9) {
            i6 = (i8 + i9) - i7;
        }
        return i6 != 0 ? i6 - this.mBackgroundPadding.left : i6;
    }

    public final int calculateXoffsetAlignRight(Rect rect, Rect rect2) {
        boolean z;
        int i;
        int i2 = rect.right;
        boolean z2 = this.mOffsetXSet;
        int i3 = ((z2 ? this.mOffsetX : 0) + i2) - this.mContentSize.mWidth;
        int i4 = rect2.left;
        Rect rect3 = this.mPositionSafeInsets;
        int i5 = rect3.left;
        if (i3 < i4 + i5) {
            i = (i4 + i5) - i3;
            z = true;
        } else {
            z = false;
            i = 0;
        }
        if (z) {
            return i;
        }
        int i6 = z2 ? this.mOffsetX : 0;
        int i7 = i2 + i6;
        int i8 = rect2.right;
        int i9 = rect3.right;
        if (i7 > i8 - i9) {
            i6 = (i8 - i9) - i7;
        }
        return i6 != 0 ? i6 + this.mBackgroundPadding.right : i6;
    }

    public final int calculateYoffset(Rect rect, Rect rect2) {
        int i = this.mOffsetYSet ? this.mOffsetY : (-rect.height()) - this.mBackgroundPadding.top;
        int iCheckMaxHeight = checkMaxHeight(rect2);
        int iMin = iCheckMaxHeight > 0 ? Math.min(this.mContentSize.mHeight, iCheckMaxHeight) : this.mContentSize.mHeight;
        int i2 = rect2.bottom;
        int i3 = this.mPositionSafeInsets.bottom;
        int i4 = (i2 - i3) - rect.bottom;
        int i5 = (rect.top - i3) - rect2.top;
        if (iMin + i > i4) {
            int iHeight = 0;
            if (i4 < i5) {
                iHeight = (this.mOffsetYSet ? rect.height() : 0) + iMin;
            } else if (this.mOffsetYSet) {
                iHeight = rect.height();
            }
            i -= iHeight;
        }
        int i6 = rect.bottom + i;
        int i7 = rect2.top;
        int i8 = this.mPositionSafeInsets.top;
        if (i6 < i7 + i8) {
            int i9 = (i7 + i8) - i6;
            setHeight(iMin - i9);
            i += i9;
        }
        int i10 = i6 + iMin;
        int i11 = rect2.bottom;
        int i12 = this.mPositionSafeInsets.bottom;
        if (i10 > i11 - i12) {
            setHeight(iMin - (i10 - (i11 - i12)));
        }
        return i;
    }

    public int checkMaxHeight(Rect rect) {
        int i = this.mMaxAllowedHeight;
        int iHeight = rect.height();
        Rect rect2 = this.mPositionSafeInsets;
        return Math.min(i, (iHeight - rect2.top) - rect2.bottom);
    }

    public int checkMaxWidth(Rect rect) {
        int i = this.mMaxAllowedWidth;
        int iWidth = rect.width();
        Rect rect2 = this.mPositionSafeInsets;
        return Math.min(i, (iWidth - rect2.left) - rect2.right);
    }

    public int checkMinWidth(Rect rect) {
        int i = this.mMinAllowedWidth;
        int iWidth = rect.width();
        Rect rect2 = this.mPositionSafeInsets;
        return Math.min(i, (iWidth - rect2.left) - rect2.right);
    }

    public int computePopupContentWidth(Rect rect) {
        if (!this.mContentSize.mHasContentWidth) {
            measureContentSize(this.mAdapter, null, this.mContext, checkMaxWidth(rect));
        }
        int iMax = Math.max(this.mContentSize.mWidth, checkMinWidth(rect));
        Rect rect2 = this.mBackgroundPadding;
        int i = iMax + rect2.left + rect2.right;
        this.mContentSize.updateWidth(i);
        return i;
    }

    public final void configurationChanged(Configuration configuration) {
        this.mRootView.post(new Runnable() { // from class: miuix.internal.widget.ListPopup.2
            @Override // java.lang.Runnable
            public void run() {
                FrameLayout frameLayout = ListPopup.this.mRootView;
                if (frameLayout == null || !frameLayout.isAttachedToWindow()) {
                    return;
                }
                ListPopup.this.updatePosition(ListPopup.this.mAnchor != null ? (View) ListPopup.this.mAnchor.get() : null);
            }
        });
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        super.dismiss();
        SinglePopControl.hidePop(this.mContext, this);
    }

    public final View getDecorView(View view) {
        View view2 = (View) this.mFenceDecor.get();
        return view2 != null ? view2 : view.getRootView();
    }

    public Rect getWindowDecorActualBounds() {
        Rect rect = new Rect();
        WindowUtils.getWindowBounds(WindowUtils.getWindowManager(this.mContext), this.mContext, rect);
        return rect;
    }

    public final Rect getWindowDecorVisibleBounds(View view) {
        Rect rect = new Rect();
        View rootView = view.getRootView();
        if (rootView != null) {
            view = rootView;
        }
        view.getWindowVisibleDisplayFrame(rect);
        return rect;
    }

    public boolean isNeedScroll(int i, Rect rect) {
        int iCheckMaxHeight = checkMaxHeight(rect);
        int i2 = this.mContentSize.mHeight;
        return i2 > i || i2 > iCheckMaxHeight;
    }

    public final void measureContentSize(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int i) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        int i2 = 0;
        int i3 = 0;
        int measuredHeight = 0;
        View view = null;
        for (int i4 = 0; i4 < count; i4++) {
            int itemViewType = listAdapter.getItemViewType(i4);
            if (itemViewType != i2) {
                view = null;
                i2 = itemViewType;
            }
            if (viewGroup == null) {
                viewGroup = new FrameLayout(context);
            }
            view = listAdapter.getView(i4, view, viewGroup);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            measuredHeight += view.getMeasuredHeight();
            if (!this.mContentSize.mHasContentWidth) {
                int measuredWidth = view.getMeasuredWidth();
                if (measuredWidth >= i) {
                    this.mContentSize.updateWidth(i);
                } else if (measuredWidth > i3) {
                    i3 = measuredWidth;
                }
            }
        }
        ContentSize contentSize = this.mContentSize;
        if (!contentSize.mHasContentWidth) {
            contentSize.updateWidth(i3);
        }
        this.mContentSize.mHeight = measuredHeight;
    }

    public void prepareWindowElevation(View view, int i) {
        if (shouldSetElevation()) {
            if (MiShadowUtils.SUPPORT_MI_SHADOW) {
                float f = view.getContext().getResources().getDisplayMetrics().density;
                MiShadowUtils.setMiShadow(view, this.mShadowColor, 0.0f * f, f * 26.0f, this.mElevation);
            } else {
                view.setElevation(i);
                setPopupShadowAlpha(view);
            }
        }
    }

    public final void setAnimationStyleByGravity(int i) {
        int i2 = R.style.Animation_PopupWindow_ImmersionMenu;
        if (i == 51) {
            i2 = R.style.Animation_PopupWindow_ImmersionMenu_LeftTop;
        } else if (i == 83) {
            i2 = R.style.Animation_PopupWindow_ImmersionMenu_LeftBottom;
        } else if (i == 53) {
            i2 = R.style.Animation_PopupWindow_ImmersionMenu_RightTop;
        } else if (i == 85) {
            i2 = R.style.Animation_PopupWindow_ImmersionMenu_RightBottom;
        } else if (i == 48) {
            i2 = R.style.Animation_PopupWindow_ImmersionMenu_Top;
        } else if (i == 80) {
            i2 = R.style.Animation_PopupWindow_ImmersionMenu_Bottom;
        } else if (i == 17) {
            i2 = R.style.Animation_PopupWindow_ImmersionMenu_Center;
        }
        setAnimationStyle(i2);
    }

    public void setContentHeight(int i) {
        this.mContentSize.mHeight = i;
    }

    @Override // android.widget.PopupWindow
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setPopupShadowAlpha(View view) {
        if (EnvStateManager.isFreeFormMode(this.mContext)) {
            view.setOutlineProvider(null);
        } else {
            view.setOutlineProvider(new ViewOutlineProvider() { // from class: miuix.internal.widget.ListPopup.5
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view2, Outline outline) {
                    if (view2.getWidth() == 0 || view2.getHeight() == 0) {
                        return;
                    }
                    outline.setAlpha(AttributeResolver.resolveFloat(view2.getContext(), R.attr.popupWindowShadowAlpha, 0.3f));
                    if (view2.getBackground() != null) {
                        view2.getBackground().getOutline(outline);
                    }
                }
            });
            view.setOutlineSpotShadowColor(this.mContext.getColor(R.color.miuix_appcompat_drop_down_menu_spot_shadow_color));
        }
    }

    public final boolean shouldSetElevation() {
        return this.mHasShadow;
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2, int i3) {
        super.showAsDropDown(view, i, i2, i3);
        this.mAnchor = new WeakReference(view);
        SinglePopControl.showPop(this.mContext, this);
    }

    @Override // android.widget.PopupWindow
    public void showAtLocation(View view, int i, int i2, int i3) {
        int i4;
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        int width = getWidth() > 0 ? getWidth() : this.mContentSize.mWidth;
        int height = getHeight() > 0 ? getHeight() : this.mContentSize.mHeight;
        Rect rect2 = new Rect();
        rect2.set(i2, i3, width + i2, height + i3);
        Log.d("ListPopup", "showAtLocation getWidth " + getWidth() + " getHeight " + getHeight());
        if (rect2.top > rect.centerY()) {
            i4 = 48;
        } else {
            i4 = rect2.bottom <= rect.centerY() ? 80 : 0;
        }
        int i5 = rect2.left;
        int i6 = rect.left;
        if (i5 >= i6 && rect2.right > rect.right) {
            i4 |= 3;
        } else if (rect2.right <= rect.right && i5 < i6) {
            i4 |= 5;
        }
        if (i4 == 0 && rect.contains(rect2)) {
            i4 = 17;
        }
        int i7 = this.mUserAnimationGravity;
        if (i7 != -1) {
            setAnimationStyleByGravity(i7);
        } else {
            setAnimationStyleByGravity(i4);
        }
        super.showAtLocation(view, i, i2, i3);
        prepareWindowElevation(this.mContentView, this.mElevation + this.mElevationExtra);
        this.mRootView.setElevation(0.0f);
        SinglePopControl.showPop(this.mContext, this);
    }

    @Override // android.widget.PopupWindow
    public void update(int i, int i2, int i3, int i4, boolean z) {
        KeyEvent.Callback anchor = getAnchor();
        if ((anchor instanceof ViewHoverListener) && ((ViewHoverListener) anchor).isHover()) {
            LogUtils.debug("popupWindow update return", anchor);
        } else {
            LogUtils.debug("popupWindow update execute", anchor);
            super.update(i, i2, i3, i4, z);
        }
    }

    public final void updatePosition(View view) {
        if (view == null) {
            return;
        }
        View decorView = getDecorView(view);
        Rect rect = new Rect();
        ViewUtils.getBoundsInWindow(decorView, rect);
        updateSafeInsetsByDecor(decorView, rect, getWindowDecorActualBounds(), getWindowDecorVisibleBounds(view));
        int iCheckMaxHeight = checkMaxHeight(rect);
        int iComputePopupContentWidth = computePopupContentWidth(rect);
        int i = this.mContentSize.mHeight;
        int i2 = (iCheckMaxHeight <= 0 || i <= iCheckMaxHeight) ? i : iCheckMaxHeight;
        Rect rect2 = new Rect();
        ViewUtils.getBoundsInWindow(view, rect2);
        update(view, calculateXoffset(view.getLayoutDirection(), rect2, rect), calculateYoffset(rect2, rect), iComputePopupContentWidth, i2);
    }

    public final void updateSafeInsetsByDecor(View view, Rect rect, Rect rect2, Rect rect3) {
        WindowInsets rootWindowInsets = view.getRootWindowInsets();
        if (rootWindowInsets != null) {
            Insets insets = rootWindowInsets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
            this.mPositionSafeInsets.set(insets.left, insets.top, insets.right, insets.bottom);
        }
        int i = rect3.left - rect2.left;
        int i2 = rect2.right - rect3.right;
        int i3 = rect3.top - rect2.top;
        int i4 = rect2.bottom - rect3.bottom;
        Rect rect4 = this.mPositionSafeInsets;
        rect4.left = Math.max(this.mMinSafeInset, (rect4.left - rect.left) - i);
        Rect rect5 = this.mPositionSafeInsets;
        rect5.right = Math.max(this.mMinSafeInset, (rect5.right - Math.max(0, rect3.width() - rect.right)) - i2);
        Rect rect6 = this.mPositionSafeInsets;
        rect6.top = Math.max(this.mMinSafeInset, (rect6.top - rect.top) - i3);
        Rect rect7 = this.mPositionSafeInsets;
        rect7.bottom = Math.max(this.mMinSafeInset, (rect7.bottom - Math.max(0, rect3.height() - rect.bottom)) - i4);
    }
}

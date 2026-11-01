package miuix.popupwidget.widget;

import android.R;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.core.view.DisplayCutoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Locale;
import miuix.core.util.WindowUtils;
import miuix.internal.util.DeviceHelper;

/* JADX INFO: loaded from: classes2.dex */
public class DropDownPopupWindow {
    public View mAnchorView;
    public ValueAnimator mAnimator;
    public int mBottomEdge;
    public ContainerView mContainer;
    public ContainerController mContainerController;
    public ContentController mContentController;
    public int mContentHeight;
    public View mContentView;
    public Context mContext;
    public boolean mDismissPending;
    public Controller mDropDownController;
    public int mEdgeDistance;
    public int mElevation;
    public int mMaxHeight;
    public int mMaxWidth;
    public int mMinWidth;
    public android.widget.PopupWindow mPopupWindow;
    public View mRealContainerView;
    public int mTopEdge;
    public int mWindowHeight;
    public int mWindowWidth;
    public int mShowDuration = 300;
    public int mDismissDuration = 300;
    public int mShadowColor = 0;
    public ValueAnimator.AnimatorUpdateListener mValueUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) DropDownPopupWindow.this.mAnimator.getAnimatedValue()).floatValue();
            if (DropDownPopupWindow.this.mContainerController != null) {
                DropDownPopupWindow.this.mContainerController.onAnimationUpdate(DropDownPopupWindow.this.mContainer, fFloatValue);
            }
            if (DropDownPopupWindow.this.mContentController != null) {
                DropDownPopupWindow.this.mContentController.onAnimationUpdate(DropDownPopupWindow.this.mContentView, fFloatValue);
            }
        }
    };
    public Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.2
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            tryDismiss();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            tryDismiss();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            if (!DropDownPopupWindow.this.mDismissPending || DropDownPopupWindow.this.mDropDownController == null) {
                return;
            }
            DropDownPopupWindow.this.mDropDownController.onDismiss();
        }

        public final void tryDismiss() {
            if (DropDownPopupWindow.this.mDismissPending) {
                DropDownPopupWindow.this.realDismiss();
            }
        }
    };

    /* JADX INFO: renamed from: miuix.popupwidget.widget.DropDownPopupWindow$5, reason: invalid class name */
    class AnonymousClass5 implements View.OnTouchListener {
        public final /* synthetic */ DropDownPopupWindow this$0;

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (x >= 0 && x < this.this$0.mRealContainerView.getWidth() && y >= 0 && y < this.this$0.mRealContainerView.getHeight()) {
                return false;
            }
            this.this$0.dismiss();
            return true;
        }
    }

    /* JADX INFO: renamed from: miuix.popupwidget.widget.DropDownPopupWindow$6, reason: invalid class name */
    class AnonymousClass6 extends ViewOutlineProvider {
        public final /* synthetic */ View val$contentView;

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            if (view.getWidth() == 0 || view.getHeight() == 0) {
                return;
            }
            outline.setAlpha(0.3f);
            if (this.val$contentView.getBackground() != null) {
                this.val$contentView.getBackground().getOutline(outline);
            }
        }
    }

    public interface ContainerController extends Controller {
    }

    public class ContainerView extends FrameLayout {
        public ContainerView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            setClipChildren(false);
            setClipToPadding(false);
        }

        @Override // android.view.View
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            DropDownPopupWindow.this.configurationChanged(configuration);
        }

        @Override // android.view.View
        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i != 4 || keyEvent.getAction() != 1) {
                return false;
            }
            DropDownPopupWindow.this.dismiss();
            return true;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (!super.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
                DropDownPopupWindow.this.dismiss();
            }
            return true;
        }
    }

    public interface ContentController extends Controller {
        View getContentView();
    }

    public interface Controller {
        void onAnimationUpdate(View view, float f);

        void onDismiss();
    }

    public static class DefaultContainerController implements ContainerController {
        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onAnimationUpdate(View view, float f) {
            Drawable background = view == null ? null : view.getBackground();
            if (background != null) {
                background.setAlpha((int) (f * 255.0f));
            }
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onDismiss() {
        }
    }

    public static class ListController extends ViewContentController {
        public ListView mListView;

        /* JADX INFO: renamed from: miuix.popupwidget.widget.DropDownPopupWindow$ListController$1, reason: invalid class name */
        public class AnonymousClass1 implements View.OnTouchListener {
            public int lastIndex = -1;

            public static /* synthetic */ void $r8$lambda$7poJecWn5lumCiORnshppieYBk8(View view) {
                if (view instanceof ViewGroup) {
                    try {
                        int childCount = ((ViewGroup) view).getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            ((ViewGroup) view).getChildAt(i).setPressed(false);
                        }
                    } catch (Exception e) {
                        Log.e("DropDownPopupWindow", "list onTouch error " + e);
                    }
                }
            }

            public AnonymousClass1() {
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(final View view, MotionEvent motionEvent) {
                int firstVisiblePosition;
                int i;
                int iPointToPosition = ListController.this.mListView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action == 1 || action == 3 || action == 6) {
                        this.lastIndex = -1;
                        ListController.this.mListView.postDelayed(new Runnable() { // from class: miuix.popupwidget.widget.DropDownPopupWindow$ListController$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DropDownPopupWindow.ListController.AnonymousClass1.$r8$lambda$7poJecWn5lumCiORnshppieYBk8(view);
                            }
                        }, ViewConfiguration.getPressedStateDuration());
                    }
                } else if (iPointToPosition != -1 && (firstVisiblePosition = iPointToPosition - ListController.this.mListView.getFirstVisiblePosition()) != (i = this.lastIndex)) {
                    if (i != -1) {
                        ListController.this.mListView.getChildAt(this.lastIndex).setPressed(false);
                    }
                    ListController.this.mListView.getChildAt(firstVisiblePosition).setPressed(true);
                    this.lastIndex = firstVisiblePosition;
                }
                return false;
            }
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.ViewContentController
        public void onContentInit(View view) {
            ListView listView = (ListView) view.findViewById(R.id.list);
            this.mListView = listView;
            listView.setOnTouchListener(new AnonymousClass1());
        }
    }

    public static class ViewContentController implements ContentController {
        public View mContent;
        public Context mContext;
        public int mLayoutId;

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.ContentController
        public View getContentView() {
            initContent();
            return this.mContent;
        }

        public void initContent() {
            if (this.mContent == null) {
                View viewInflate = LayoutInflater.from(this.mContext).inflate(this.mLayoutId, (ViewGroup) null);
                this.mContent = viewInflate;
                onContentInit(viewInflate);
            }
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onAnimationUpdate(View view, float f) {
            if (view != null) {
                view.setTranslationY((-view.getHeight()) * (1.0f - f));
            }
        }

        public void onContentInit(View view) {
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onDismiss() {
        }
    }

    public static /* synthetic */ void $r8$lambda$94kPJfsVBNyKP17NtPKpGgTyDR4(DropDownPopupWindow dropDownPopupWindow) {
        dropDownPopupWindow.initData();
        dropDownPopupWindow.adjustLocation(dropDownPopupWindow.getCutout(dropDownPopupWindow.mContainer), null);
    }

    public DropDownPopupWindow(Context context, AttributeSet attributeSet, int i) {
        this.mContext = context;
        this.mPopupWindow = new android.widget.PopupWindow(context, attributeSet, 0, i);
        ContainerView containerView = new ContainerView(context, attributeSet, i);
        this.mContainer = containerView;
        containerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.4
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                DropDownPopupWindow.this.adjustLocation(DropDownPopupWindow.this.getCutout(view), null);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
            }
        });
        this.mPopupWindow.setAnimationStyle(DeviceHelper.isFeatureWholeAnim() ? miuix.popupwidget.R.style.Animation_PopupWindow_DropDown : 0);
        initPopupWindow();
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0030  */
    public final void adjustLocation(Rect rect, WindowInsets windowInsets) {
        DropDownPopupWindow dropDownPopupWindow;
        if (this.mAnchorView == null || this.mRealContainerView == null) {
            return;
        }
        updateMaxWidth(rect);
        updateMaxHeight(windowInsets);
        int width = this.mPopupWindow.getWidth();
        ContentController contentController = this.mContentController;
        if (contentController != null) {
            View contentView = contentController.getContentView();
            this.mContentView = contentView;
            if (contentView != null) {
                dropDownPopupWindow = this;
                width = dropDownPopupWindow.setupContentView(this.mContainer, contentView, this.mElevation, this.mMinWidth, this.mContainerController);
            } else {
                dropDownPopupWindow = this;
            }
        } else {
            dropDownPopupWindow = this;
        }
        int i = dropDownPopupWindow.mMaxWidth;
        if (width > i) {
            width = i;
        }
        int i2 = dropDownPopupWindow.mContentHeight;
        int i3 = dropDownPopupWindow.mMaxHeight;
        if (i2 > i3) {
            dropDownPopupWindow.mPopupWindow.setHeight(i3);
        } else {
            dropDownPopupWindow.mPopupWindow.setHeight(-2);
        }
        dropDownPopupWindow.mPopupWindow.setWidth(width);
        int height = dropDownPopupWindow.mPopupWindow.getHeight();
        View view = dropDownPopupWindow.mAnchorView;
        if (view != null && view.isAttachedToWindow()) {
            int[] iArrComputeLocation = dropDownPopupWindow.computeLocation(width, rect);
            dropDownPopupWindow.mPopupWindow.update(iArrComputeLocation[0], iArrComputeLocation[1], width, height);
        } else if (dropDownPopupWindow.mContainer.isAttachedToWindow()) {
            dropDownPopupWindow.mPopupWindow.update(0, 0, width, height);
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0046 A[PHI: r5
  0x0046: PHI (r5v3 int) = (r5v2 int), (r5v5 int) binds: [B:40:0x0076, B:25:0x0044] A[DONT_GENERATE, DONT_INLINE]] */
    public final int[] computeLocation(int i, Rect rect) {
        int i2;
        boolean z;
        int width;
        int i3;
        int i4;
        int[] iArr = new int[2];
        this.mAnchorView.getLocationInWindow(iArr);
        int i5 = this.mMaxWidth;
        if (i > i5) {
            i = i5;
        }
        int i6 = iArr[1];
        if (i == i5) {
            i3 = rect.left;
            if (i3 <= 0) {
                i3 = this.mEdgeDistance;
            }
        } else if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 1) {
            width = this.mWindowWidth;
            int i7 = iArr[0];
            int i8 = width - (i7 + i);
            i2 = this.mEdgeDistance;
            boolean z2 = i8 < i2;
            z = i7 < i2;
            if (!z && z2) {
                i += i2;
                i3 = width - i;
            } else if (z2 || !z) {
                i3 = i7;
            } else {
                i3 = i2;
            }
        } else {
            boolean z3 = (iArr[0] + this.mAnchorView.getWidth()) - i < this.mEdgeDistance;
            int width2 = this.mWindowWidth - (iArr[0] + this.mAnchorView.getWidth());
            i2 = this.mEdgeDistance;
            z = width2 < i2;
            if (!z3 && z) {
                width = this.mWindowWidth;
                i += i2;
            } else if (z || !z3) {
                width = iArr[0] + this.mAnchorView.getWidth();
            } else {
                i3 = i2;
            }
            i3 = width - i;
        }
        int i9 = this.mWindowHeight;
        int i10 = this.mContentHeight;
        int i11 = (i9 - i6) - i10;
        int i12 = this.mBottomEdge;
        if (i11 < i12 && (i6 = i6 - (i12 - ((i9 - i6) - i10))) < (i4 = this.mTopEdge)) {
            i6 = i4;
        }
        return new int[]{i3, i6};
    }

    public final void configurationChanged(Configuration configuration) {
        Activity activityContextFromView = getActivityContextFromView(this.mContainer);
        View decorView = activityContextFromView != null ? activityContextFromView.getWindow().getDecorView() : null;
        if (decorView != null) {
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.3
                @Override // android.view.View.OnApplyWindowInsetsListener
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    DropDownPopupWindow dropDownPopupWindow = DropDownPopupWindow.this;
                    DropDownPopupWindow.this.adjustLocation(dropDownPopupWindow.getCutout(dropDownPopupWindow.mContainer), windowInsets);
                    return windowInsets;
                }
            });
        }
        this.mContainer.post(new Runnable() { // from class: miuix.popupwidget.widget.DropDownPopupWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DropDownPopupWindow.$r8$lambda$94kPJfsVBNyKP17NtPKpGgTyDR4(this.f$0);
            }
        });
    }

    public void dismiss() {
        this.mDismissPending = true;
        realDismiss();
    }

    public final Activity getActivityContextFromView(View view) {
        Context context = ((ViewGroup) view.getRootView()).getChildAt(0).getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public final Rect getCutout(View view) {
        DisplayCutout cutout;
        Rect rect = new Rect();
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
        if (rootWindowInsets != null) {
            DisplayCutoutCompat displayCutout = rootWindowInsets.getDisplayCutout();
            if (displayCutout == null) {
                Activity activityContextFromView = getActivityContextFromView(view);
                if (activityContextFromView != null && (cutout = activityContextFromView.getWindowManager().getDefaultDisplay().getCutout()) != null) {
                    rect.left = cutout.getSafeInsetLeft();
                    rect.right = cutout.getSafeInsetRight();
                    return rect;
                }
            } else {
                rect.left = displayCutout.getSafeInsetLeft();
                rect.right = displayCutout.getSafeInsetRight();
            }
        }
        return rect;
    }

    public final void initData() {
        this.mElevation = (int) (this.mContext.getResources().getDisplayMetrics().density * 32.0f);
        this.mShadowColor = this.mContext.getResources().getColor(miuix.popupwidget.R.color.miuix_appcompat_drop_down_menu_spot_shadow_color);
        this.mEdgeDistance = this.mContext.getResources().getDimensionPixelSize(miuix.popupwidget.R.dimen.miuix_appcompat_drop_down_horizontal_edge_margin);
        this.mMinWidth = this.mContext.getResources().getDimensionPixelSize(miuix.popupwidget.R.dimen.miuix_appcompat_drop_down_menu_min_width);
        this.mWindowWidth = WindowUtils.getWindowSize(this.mContext).x;
        this.mWindowHeight = WindowUtils.getWindowSize(this.mContext).y;
    }

    public final void initPopupWindow() {
        initData();
        this.mPopupWindow.setWidth(-2);
        this.mPopupWindow.setHeight(-2);
        this.mPopupWindow.setSoftInputMode(3);
        this.mPopupWindow.setOutsideTouchable(false);
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mContainer.setFocusableInTouchMode(true);
        this.mPopupWindow.setContentView(this.mContainer);
    }

    public final int measureListViewWidth(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = adapter.getCount();
        int measuredHeight = 0;
        int i = 0;
        int i2 = 0;
        View view = null;
        for (int i3 = 0; i3 < count; i3++) {
            int itemViewType = adapter.getItemViewType(i3);
            if (itemViewType != i2) {
                view = null;
                i2 = itemViewType;
            }
            view = adapter.getView(i3, view, listView);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            measuredHeight += view.getMeasuredHeight();
            if (measuredWidth > i) {
                i = measuredWidth;
            }
        }
        this.mContentHeight = measuredHeight;
        return i;
    }

    public final void realDismiss() {
        android.widget.PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        ContainerController containerController = this.mContainerController;
        if (containerController != null) {
            containerController.onDismiss();
        }
        ContentController contentController = this.mContentController;
        if (contentController != null) {
            contentController.onDismiss();
        }
        Controller controller = this.mDropDownController;
        if (controller != null) {
            controller.onDismiss();
        }
        this.mDismissPending = false;
    }

    public int setupContentView(FrameLayout frameLayout, View view, int i, int i2, ContainerController containerController) {
        int iMeasureListViewWidth;
        if (view == null) {
            return -2;
        }
        if (view instanceof ListView) {
            iMeasureListViewWidth = measureListViewWidth((ListView) view);
        } else {
            view.measure(0, 0);
            int measuredWidth = view.getMeasuredWidth();
            this.mContentHeight = view.getMeasuredHeight();
            iMeasureListViewWidth = measuredWidth;
        }
        return iMeasureListViewWidth < i2 ? i2 : iMeasureListViewWidth;
    }

    public final void updateMaxHeight(WindowInsets windowInsets) {
        View view = this.mAnchorView;
        if (view == null || this.mWindowHeight == 0) {
            return;
        }
        int height = view.getRootView().getHeight();
        if (windowInsets == null) {
            windowInsets = this.mAnchorView.getRootWindowInsets();
        }
        if (windowInsets != null) {
            Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
            this.mTopEdge = insets.top;
            this.mBottomEdge = insets.bottom;
        }
        this.mMaxHeight = (height - this.mTopEdge) - this.mBottomEdge;
    }

    public final void updateMaxWidth(Rect rect) {
        int i = rect.left;
        if (i > 0) {
            this.mMaxWidth = (this.mWindowWidth - i) - this.mEdgeDistance;
            return;
        }
        int i2 = rect.right;
        if (i2 > 0) {
            this.mMaxWidth = (this.mWindowWidth - i2) - this.mEdgeDistance;
        } else {
            this.mMaxWidth = this.mWindowWidth - (this.mEdgeDistance * 2);
        }
    }
}

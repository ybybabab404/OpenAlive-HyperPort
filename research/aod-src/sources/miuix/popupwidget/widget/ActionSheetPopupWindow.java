package miuix.popupwidget.widget;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.core.util.MiShadowUtils;
import miuix.popupwidget.internal.strategy.PopupWindowSpec;
import miuix.popupwidget.internal.widget.GravityCalculator;
import miuix.smooth.SmoothFrameLayout2;
import miuix.springback.view.SpringBackLayout;
import miuix.theme.token.ShadowToken;
import miuix.view.ITransformableView;

/* JADX INFO: loaded from: classes2.dex */
public class ActionSheetPopupWindow extends PopupWindow {
    public ActionSheetPopup.ActionSheetItemType[] mActionItemTypes;
    public CharSequence[] mActionItems;
    public boolean mAutoDismissOnItemClick;
    public SmoothFrameLayout2 mContainerView;
    public View mContentView;
    public final float mCorner;
    public ActionSheetPopup.OnItemClickListener mItemClickListener;
    public ActionSheetPopup.AccessibilityDelegateProvider mItemProvider;
    public ListView mListView;
    public CharSequence mMessage;
    public TextView mMessageView;
    public SpringBackLayout mSpringBackLayout;
    public final ITransformableView mTransformableDelegate;
    public final Map mTypeColorMap;

    /* JADX INFO: renamed from: miuix.popupwidget.widget.ActionSheetPopupWindow$2, reason: invalid class name */
    public class AnonymousClass2 implements ITransformableView {
        public final /* synthetic */ ActionSheetPopupWindow this$0;

        @Override // miuix.view.ITransformableView
        public View getContainerView() {
            return this.this$0.mContainerView;
        }

        @Override // miuix.view.ITransformableView
        public View getContentView() {
            return this.this$0.mContentView;
        }

        @Override // miuix.view.ITransformableView
        public float getCornerRadius() {
            return this.this$0.mCorner;
        }

        @Override // miuix.view.ITransformableView
        public View getElevationView() {
            return this.this$0.mContainerView;
        }

        @Override // miuix.view.ITransformableView
        public void setCornerRadius(float f) {
            this.this$0.mContainerView.setCornerRadius(f);
        }
    }

    public static class ActionSheetPopListViewAdapter extends ArrayAdapter<CharSequence> {
        public final Context mContext;
        public final ActionSheetPopup.ActionSheetItemType[] mItemTypes;
        public ActionSheetPopup.AccessibilityDelegateProvider mProvider;
        public final Map mTypeColorMap;

        public ActionSheetPopListViewAdapter(Context context, int i, int i2, CharSequence[] charSequenceArr, ActionSheetPopup.ActionSheetItemType[] actionSheetItemTypeArr, Map map) {
            super(context, i, i2, charSequenceArr);
            this.mContext = context;
            this.mItemTypes = actionSheetItemTypeArr;
            this.mTypeColorMap = map;
        }

        public static void addPressAnim(View view) {
            Folme.useAt(view).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).handleTouchOf(view, new AnimConfig[0]);
            Folme.useAt(view).hover().setEffect(IHoverStyle.HoverEffect.NORMAL).handleHoverOf(view, new AnimConfig[0]);
        }

        public static AccessibilityDelegateCompat getDefaultAccessibilityDelegateCompat() {
            return new AccessibilityDelegateCompat() { // from class: miuix.popupwidget.widget.ActionSheetPopupWindow.ActionSheetPopListViewAdapter.1
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                    accessibilityNodeInfoCompat.addAction(16);
                }
            };
        }

        public static void setAccessibilityDelegate(View view, ActionSheetPopup.AccessibilityDelegateProvider accessibilityDelegateProvider) {
            AccessibilityDelegateCompat accessibilityDelegate = accessibilityDelegateProvider != null ? accessibilityDelegateProvider.getAccessibilityDelegate() : getDefaultAccessibilityDelegateCompat();
            if (accessibilityDelegate != null) {
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegate);
            }
        }

        public final void adjustTypedItemColor(TextView textView, int i) {
            Map map;
            Integer num;
            ActionSheetPopup.ActionSheetItemType[] actionSheetItemTypeArr = this.mItemTypes;
            if (actionSheetItemTypeArr == null || textView == null || (map = this.mTypeColorMap) == null || i >= actionSheetItemTypeArr.length || (num = (Integer) map.get(actionSheetItemTypeArr[i])) == null) {
                return;
            }
            textView.setTextColor(this.mContext.getResources().getColorStateList(num.intValue()));
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            adjustTypedItemColor((TextView) view2.findViewById(R.id.text1), i);
            if (view == null) {
                addPressAnim(view2);
            }
            ActionSheetPopup.AccessibilityDelegateProvider accessibilityDelegateProvider = this.mProvider;
            if (accessibilityDelegateProvider != null) {
                setAccessibilityDelegate(view2, accessibilityDelegateProvider);
            }
            return view2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        public void setProvider(ActionSheetPopup.AccessibilityDelegateProvider accessibilityDelegateProvider) {
            this.mProvider = accessibilityDelegateProvider;
        }
    }

    public final void adjustSpringEnabled() {
        ListView listView;
        if (this.mSpringBackLayout == null || (listView = this.mListView) == null) {
            return;
        }
        listView.post(new Runnable() { // from class: miuix.popupwidget.widget.ActionSheetPopupWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActionSheetPopupWindow actionSheetPopupWindow = this.f$0;
                actionSheetPopupWindow.mSpringBackLayout.setSpringBackEnable(true ^ (actionSheetPopupWindow.getVisibleItemTotalHeight(actionSheetPopupWindow.mListView, (actionSheetPopupWindow.mListView.getLastVisiblePosition() - actionSheetPopupWindow.mListView.getFirstVisiblePosition()) + 1) == actionSheetPopupWindow.mListView.getHeight()));
            }
        });
    }

    @Override // miuix.popupwidget.widget.PopupWindow
    public int computeGravity(Rect rect, Rect rect2, int i, int i2) {
        return GravityCalculator.compute(this.mPopupWindowSpec, rect, rect2);
    }

    @Override // miuix.popupwidget.widget.PopupWindow
    public void dismissWithAnim() {
        if (this.mIsTransitioningToDismiss) {
            return;
        }
        this.mListView.setEnabled(false);
        this.mIsTransitioningToDismiss = true;
        PopupAnimHelper popupAnimHelper = this.mAnimHelper;
        if (popupAnimHelper == null) {
            realDismiss();
            this.mIsTransitioningToDismiss = false;
        } else {
            popupAnimHelper.updateAnimationOptions();
            this.mAnimHelper.addPopupAnimationListener(new PopupAnimationListener() { // from class: miuix.popupwidget.widget.ActionSheetPopupWindow.3
                @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
                public void onReverseAnimationEnd() {
                    ActionSheetPopupWindow.this.mAnimHelper.removePopupAnimationListener(this);
                    ActionSheetPopupWindow actionSheetPopupWindow = ActionSheetPopupWindow.this;
                    actionSheetPopupWindow.mIsTransitioningToDismiss = false;
                    actionSheetPopupWindow.realDismiss();
                }
            });
            this.mAnimHelper.dismiss();
        }
    }

    @Override // miuix.popupwidget.widget.PopupWindow
    public void getContentViewBounds(PopupWindowSpec popupWindowSpec) {
        if (this.mContainerView == null) {
            super.getContentViewBounds(popupWindowSpec);
            return;
        }
        int[] maxDimensions = getMaxDimensions(popupWindowSpec);
        int i = maxDimensions[0];
        int i2 = maxDimensions[1];
        int i3 = setupListViewInitialHeight();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContainerView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        reMeasure(View.MeasureSpec.makeMeasureSpec(this.mContainerView.getMeasuredWidth(), 1073741824), iMakeMeasureSpec2, i2, i3, popupWindowSpec);
    }

    public final int[] getMaxDimensions(PopupWindowSpec popupWindowSpec) {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        int i = popupWindowSpec.mMaxWidth;
        if (i <= 0) {
            i = displayMetrics.widthPixels;
        }
        int i2 = popupWindowSpec.mMaxHeight;
        if (i2 <= 0) {
            i2 = displayMetrics.heightPixels;
        }
        return new int[]{i, i2};
    }

    public final int getVisibleItemTotalHeight(ListView listView, int i) {
        if (listView == null || i <= 0) {
            return 0;
        }
        int height = 0;
        for (int i2 = 0; i2 < Math.min(listView.getChildCount(), i); i2++) {
            View childAt = listView.getChildAt(i2);
            if (childAt != null) {
                height += childAt.getHeight();
            }
        }
        return height;
    }

    public final int measureListItemsTotalHeight() {
        ListView listView = this.mListView;
        if (listView == null || listView.getAdapter() == null) {
            return 0;
        }
        ListAdapter adapter = this.mListView.getAdapter();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels, Integer.MIN_VALUE);
        int measuredHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, this.mListView);
            view.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, 0));
            measuredHeight += view.getMeasuredHeight();
        }
        return measuredHeight + (this.mListView.getDividerHeight() * Math.max(0, adapter.getCount() - 1));
    }

    @Override // miuix.popupwidget.widget.PopupWindow
    public boolean prepareShow(View view) {
        setupContent();
        if (!this.mWindowAnimationEnabled && this.mAnimHelper == null) {
            PopupAnimHelper popupAnimHelper = new PopupAnimHelper(this.mContainerView, view, this.mTransformableDelegate);
            this.mAnimHelper = popupAnimHelper;
            popupAnimHelper.setCrossWindow(true);
        }
        return super.prepareShow(view);
    }

    @Override // miuix.popupwidget.widget.PopupWindow
    public void prepareWindowElevation(View view, int i) {
        if (shouldSetElevation()) {
            if (!MiShadowUtils.SUPPORT_MI_SHADOW) {
                this.mContainerView.setElevation(i);
                setPopupShadowAlpha(this.mContainerView);
            } else {
                SmoothFrameLayout2 smoothFrameLayout2 = this.mContainerView;
                ShadowToken shadowToken = ShadowToken.Regular;
                MiShadowUtils.setMiShadow(smoothFrameLayout2, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius);
            }
        }
    }

    public final void reMeasure(int i, int i2, int i3, int i4, PopupWindowSpec popupWindowSpec) {
        ListView listView;
        int i5;
        this.mContainerView.measure(i, i2);
        int measuredWidth = this.mContainerView.getMeasuredWidth();
        int measuredHeight = this.mContainerView.getMeasuredHeight();
        if (measuredHeight > i3 && (listView = this.mListView) != null && i4 > 0 && (i5 = i3 - (measuredHeight - i4)) > 0) {
            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
            layoutParams.height = i5;
            this.mListView.setLayoutParams(layoutParams);
            this.mContainerView.measure(i, i2);
            measuredWidth = this.mContainerView.getMeasuredWidth();
            measuredHeight = this.mContainerView.getMeasuredHeight();
        }
        popupWindowSpec.mContentViewBounds.set(0, 0, measuredWidth, measuredHeight);
    }

    @Override // miuix.popupwidget.widget.PopupWindow
    public void realDismiss() {
        PopupAnimHelper popupAnimHelper = this.mAnimHelper;
        if (popupAnimHelper != null) {
            popupAnimHelper.resetFromContentView();
        }
        super.realDismiss();
    }

    public final void setupContent() {
        if (this.mMessageView != null) {
            if (TextUtils.isEmpty(this.mMessage)) {
                this.mMessageView.setVisibility(8);
            } else {
                this.mMessageView.setText(this.mMessage);
                this.mMessageView.setVisibility(0);
            }
        }
        setupListView();
    }

    public final void setupListView() {
        ListView listView = this.mListView;
        if (listView == null) {
            return;
        }
        CharSequence[] charSequenceArr = this.mActionItems;
        if (charSequenceArr == null || charSequenceArr.length <= 0) {
            listView.setVisibility(8);
            return;
        }
        ActionSheetPopListViewAdapter actionSheetPopListViewAdapter = new ActionSheetPopListViewAdapter(this.mContext, miuix.popupwidget.R.layout.miuix_appcompat_action_sheet_pop_list_item, R.id.text1, this.mActionItems, this.mActionItemTypes, this.mTypeColorMap);
        actionSheetPopListViewAdapter.setProvider(this.mItemProvider);
        this.mListView.setAdapter((ListAdapter) actionSheetPopListViewAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: miuix.popupwidget.widget.ActionSheetPopupWindow.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView adapterView, View view, int i, long j) {
                if (ActionSheetPopupWindow.this.mItemClickListener != null) {
                    ActionSheetPopupWindow.this.mItemClickListener.onItemClick(i);
                }
                if (ActionSheetPopupWindow.this.mAutoDismissOnItemClick) {
                    ActionSheetPopupWindow.this.dismiss();
                }
            }
        });
        this.mListView.setVisibility(0);
        adjustSpringEnabled();
    }

    public final int setupListViewInitialHeight() {
        ListView listView = this.mListView;
        if (listView == null || listView.getAdapter() == null || this.mListView.getAdapter().getCount() <= 0) {
            return 0;
        }
        int iMeasureListItemsTotalHeight = measureListItemsTotalHeight();
        ViewGroup.LayoutParams layoutParams = this.mListView.getLayoutParams();
        layoutParams.height = iMeasureListItemsTotalHeight;
        this.mListView.setLayoutParams(layoutParams);
        return iMeasureListItemsTotalHeight;
    }

    @Override // miuix.popupwidget.widget.PopupWindow
    public void showWithAnim(int i) {
        this.mListView.setEnabled(true);
        PopupAnimHelper popupAnimHelper = this.mAnimHelper;
        if (popupAnimHelper == null) {
            super.showWithAnim(i);
            return;
        }
        this.mIsTransitioningToShown = true;
        popupAnimHelper.addPopupAnimationListener(new PopupAnimationListener() { // from class: miuix.popupwidget.widget.ActionSheetPopupWindow.4
            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onForwardAnimationCancel() {
                onForwardAnimationEnd();
            }

            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onForwardAnimationEnd() {
                ActionSheetPopupWindow actionSheetPopupWindow = ActionSheetPopupWindow.this;
                actionSheetPopupWindow.mIsTransitioningToShown = false;
                actionSheetPopupWindow.mAnimHelper.removePopupAnimationListener(this);
            }
        });
        this.mAnimHelper.setGravity(i);
        this.mAnimHelper.show();
    }
}

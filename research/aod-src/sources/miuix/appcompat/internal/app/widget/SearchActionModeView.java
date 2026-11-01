package miuix.appcompat.internal.app.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.R;
import miuix.appcompat.app.OverlayMaskConfig;
import miuix.appcompat.app.SearchViewMaterialImpl;
import miuix.appcompat.app.SearchViewOverlayMaskImpl;
import miuix.appcompat.app.SearchViewShadowImpl;
import miuix.container.ExtraPaddingPolicy;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.view.NestedContentInsetObserver;
import miuix.core.view.NestedCoordinatorObserver;
import miuix.internal.util.DeviceHelper;
import miuix.internal.util.ViewUtils;
import miuix.view.ActionModeAnimationListener;
import miuix.view.CompatViewMethod;
import miuix.view.SearchActionMode;
import miuix.view.inputmethod.InputMethodHelper;
import miuix.viewpager.widget.ViewPager;

/* JADX INFO: loaded from: classes2.dex */
public class SearchActionModeView extends FrameLayout implements Animator.AnimatorListener, ActionModeView, TextWatcher, View.OnClickListener {
    public static final ViewProperty SELF_BLUR = new ViewProperty("self_blur", 1.0f) { // from class: miuix.appcompat.internal.app.widget.SearchActionModeView.2
        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            Object tag = view.getTag(R.id.miuix_animation_tag_view_self_blur);
            if (tag == null) {
                return 0.0f;
            }
            return ((Float) tag).floatValue();
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
            view.setTag(R.id.miuix_animation_tag_view_self_blur, Float.valueOf(f));
            MiuiBlurUtils.setSelfBlur(view, (int) f, null);
        }
    };
    public ActionBarContainer mActionBarContainer;
    public boolean mActionBarHiddenBeforeSearchMode;
    public int mActionBarTopMargin;
    public ActionBarView mActionBarView;
    public WeakReference mAnchorParentView;
    public WeakReference mAnchorView;
    public boolean mAnimateToVisible;
    public WeakReference mAnimateView;
    public SearchActionMode.AnimatedViewListener mAnimatedViewListener;
    public boolean mAnimationCanceled;
    public List mAnimationListeners;
    public float mAnimationProgress;
    public ViewUtils.RelativePadding mCancelBtnInitPaddings;
    public int mContentOriginPaddingBottom;
    public int mContentOriginPaddingTop;
    public WeakReference mContentView;
    public ObjectAnimator mCurrentAnimation;
    public FrameLayout mCustomFrameLayout;
    public View mCustomView;
    public View mDimView;
    public int mExtraPadding;
    public boolean mExtraPaddingApplyToAnchorByUser;
    public ExtraPaddingPolicy mExtraPaddingPolicy;
    public boolean mFirstLayout;
    public boolean mFitWindowInsetsEnabled;
    public boolean mHasPendingShowSoftInputTask;
    public boolean mHasSetCustomView;
    public int mHorizontalPaddingDp;
    public boolean mInActionMode;
    public ViewUtils.RelativePadding mInitPaddings;
    public int mInputPaddingRight;
    public int mInputPaddingTop;
    public EditText mInputView;
    public final Rect mInsetsPaddingRect;
    public int mLimitTextSizeDp;
    public int[] mLocation;
    public SearchViewMaterialImpl mMaterialImpl;
    public SearchViewShadowImpl mMiShadowImpl;
    public View.OnClickListener mOnBackClickListener;
    public boolean mOriginOverlayMode;
    public int mOriginalPaddingTop;
    public SearchViewOverlayMaskImpl mOverlayMaskImpl;
    public WeakReference mOverlayView;
    public int mParentLocationY;
    public int mPendingInsetTop;
    public WeakReference mResultView;
    public int mResultViewOriginMarginBottom;
    public int mResultViewOriginMarginTop;
    public int mResultViewOriginPaddingBottom;
    public int mResultViewOriginPaddingTop;
    public boolean mResultViewSet;
    public ViewGroup mSearchContainer;
    public int mSearchViewHeight;
    public final Runnable mShowSoftInputRunnable;
    public ActionBarContainer mSplitActionBarContainer;
    public View mTextCancel;
    public int mTextLengthBeforeChanged;
    public boolean mWindowInsetsPaddingAdded;

    public class ActionBarAnimationProcessor implements ActionModeAnimationListener {
        public ActionBarAnimationProcessor() {
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStart(boolean z) {
            if (z) {
                View tabContainer = SearchActionModeView.this.mActionBarContainer.getTabContainer();
                if (tabContainer != null) {
                    tabContainer.setVisibility(0);
                }
                SearchActionModeView.this.mActionBarContainer.setVisibility(0);
                return;
            }
            View tabContainer2 = SearchActionModeView.this.mActionBarContainer.getTabContainer();
            if (tabContainer2 != null) {
                tabContainer2.setVisibility(0);
            }
            SearchActionModeView.this.mActionBarContainer.setVisibility(0);
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            if (!z) {
                SearchActionModeView.this.mActionBarContainer.setVisibility(0);
                return;
            }
            SearchActionModeView.this.mActionBarContainer.setVisibility(SearchActionModeView.this.mOriginOverlayMode ? 4 : 8);
            View tabContainer = SearchActionModeView.this.mActionBarContainer.getTabContainer();
            if (tabContainer != null) {
                tabContainer.setVisibility(8);
            }
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
        }
    }

    public class ContentViewAnimationProcessor implements ActionModeAnimationListener {
        public ActionBarView mAnimationActionBarView;
        public View mAnimationAnchorView;
        public View mAnimationAnimateView;
        public NestedCoordinatorObserver mAnimationNestedCoordOb;
        public View mAnimationResultView;
        public int mContentViewTranslationYBeforeMode;
        public int mContentViewTranslationYLength;
        public boolean mIsActionBarNestedScrolledBeforeMode;
        public int mModeViewTranslationYBeforeMode;
        public int mModeViewTranslationYLength;
        public int mNestedCoordObTranslationYLength;
        public int mTmpAnchorAccessibilityMode = 0;
        public int mTmpAnimAccessibilityMode = 0;
        public int mTmpResultAccessibilityMode = 0;

        public ContentViewAnimationProcessor() {
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStart(boolean z) {
            ActionBarView actionBarView;
            this.mAnimationActionBarView = SearchActionModeView.this.getActionBarView();
            this.mAnimationAnchorView = SearchActionModeView.this.mAnchorView != null ? (View) SearchActionModeView.this.mAnchorView.get() : null;
            this.mAnimationAnimateView = SearchActionModeView.this.mAnimateView != null ? (View) SearchActionModeView.this.mAnimateView.get() : null;
            this.mAnimationResultView = SearchActionModeView.this.mResultView != null ? (View) SearchActionModeView.this.mResultView.get() : null;
            KeyEvent.Callback callback = SearchActionModeView.this.mAnchorParentView != null ? (View) SearchActionModeView.this.mAnchorParentView.get() : null;
            if (callback instanceof NestedCoordinatorObserver) {
                this.mAnimationNestedCoordOb = (NestedCoordinatorObserver) callback;
            }
            if (SearchActionModeView.this.mParentLocationY == Integer.MAX_VALUE) {
                ((View) SearchActionModeView.this.getParent()).getLocationInWindow(SearchActionModeView.this.mLocation);
                SearchActionModeView searchActionModeView = SearchActionModeView.this;
                searchActionModeView.mParentLocationY = searchActionModeView.mLocation[1];
            }
            View view = this.mAnimationAnchorView;
            if (view != null) {
                view.setAlpha(0.0f);
                this.mAnimationAnchorView.setVisibility(4);
            }
            if (z && (actionBarView = this.mAnimationActionBarView) != null) {
                this.mIsActionBarNestedScrolledBeforeMode = actionBarView.getExpandState() == 0;
            }
            if (this.mAnimationAnchorView != null) {
                updateAnimValues();
            }
            if (!z) {
                if (SearchActionModeView.this.mAnimatedViewListener != null) {
                    SearchActionModeView.this.mAnimatedViewListener.onInSearchMode(false);
                }
                View view2 = this.mAnimationAnchorView;
                if (view2 != null) {
                    view2.setImportantForAccessibility(this.mTmpAnchorAccessibilityMode);
                }
                View view3 = this.mAnimationAnimateView;
                if (view3 != null) {
                    view3.setImportantForAccessibility(this.mTmpAnimAccessibilityMode);
                }
                View view4 = this.mAnimationResultView;
                if (view4 != null) {
                    view4.setImportantForAccessibility(this.mTmpResultAccessibilityMode);
                }
                if (SearchActionModeView.this.mOriginOverlayMode || this.mAnimationNestedCoordOb == null) {
                    return;
                }
                SearchActionModeView searchActionModeView2 = SearchActionModeView.this;
                searchActionModeView2.setContentViewTranslation(searchActionModeView2.getViewHeight() + SearchActionModeView.this.mPendingInsetTop);
                this.mAnimationNestedCoordOb.updateCoordinatorHeightGapInfo(0, 0);
                SearchActionModeView.this.setContentViewPadding(0, 0);
                return;
            }
            View view5 = this.mAnimationAnchorView;
            if (view5 != null) {
                this.mTmpAnchorAccessibilityMode = view5.getImportantForAccessibility();
                this.mAnimationAnchorView.setImportantForAccessibility(4);
            }
            View view6 = this.mAnimationAnimateView;
            if (view6 != null) {
                this.mTmpAnimAccessibilityMode = view6.getImportantForAccessibility();
                this.mAnimationAnimateView.setImportantForAccessibility(4);
            }
            View view7 = this.mAnimationResultView;
            if (view7 != null) {
                this.mTmpResultAccessibilityMode = view7.getImportantForAccessibility();
                this.mAnimationResultView.setImportantForAccessibility(1);
            }
            SearchActionModeView.this.setTranslationY(this.mModeViewTranslationYBeforeMode);
            if (SearchActionModeView.this.mOriginOverlayMode) {
                return;
            }
            int i = this.mModeViewTranslationYBeforeMode - SearchActionModeView.this.mPendingInsetTop;
            this.mContentViewTranslationYBeforeMode = i;
            SearchActionModeView.this.setContentViewTranslation(i);
            SearchActionModeView searchActionModeView3 = SearchActionModeView.this;
            searchActionModeView3.setContentViewPadding(searchActionModeView3.mPendingInsetTop, 0);
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            if (z) {
                if (SearchActionModeView.this.mAnimatedViewListener != null) {
                    SearchActionModeView.this.mAnimatedViewListener.onUpdateOffsetY(this.mNestedCoordObTranslationYLength);
                    SearchActionModeView.this.mAnimatedViewListener.onInSearchMode(true);
                }
                if (!SearchActionModeView.this.mOriginOverlayMode) {
                    SearchActionModeView.this.setContentViewTranslation(0.0f);
                    NestedCoordinatorObserver nestedCoordinatorObserver = this.mAnimationNestedCoordOb;
                    if (nestedCoordinatorObserver != null) {
                        nestedCoordinatorObserver.updateCoordinatorHeightGapInfo(this.mNestedCoordObTranslationYLength, 0);
                        SearchActionModeView searchActionModeView = SearchActionModeView.this;
                        searchActionModeView.setContentViewPadding(searchActionModeView.mPendingInsetTop + SearchActionModeView.this.getViewHeight(), 0);
                    } else {
                        SearchActionModeView searchActionModeView2 = SearchActionModeView.this;
                        searchActionModeView2.setContentViewPadding(searchActionModeView2.mPendingInsetTop, 0);
                    }
                }
                if (this.mAnimationResultView != null && SearchActionModeView.this.mOriginOverlayMode) {
                    View view = this.mAnimationResultView;
                    view.setPadding(view.getPaddingLeft(), Math.max(SearchActionModeView.this.getViewHeight() + SearchActionModeView.this.mPendingInsetTop, SearchActionModeView.this.mResultViewOriginPaddingTop), this.mAnimationResultView.getPaddingRight(), SearchActionModeView.this.mResultViewOriginPaddingBottom);
                }
            } else {
                if (SearchActionModeView.this.mAnimatedViewListener != null) {
                    SearchActionModeView.this.mAnimatedViewListener.onUpdateOffsetY(0);
                }
                if (!SearchActionModeView.this.mOriginOverlayMode) {
                    NestedCoordinatorObserver nestedCoordinatorObserver2 = this.mAnimationNestedCoordOb;
                    if (nestedCoordinatorObserver2 != null) {
                        nestedCoordinatorObserver2.updateCoordinatorHeightGapInfo(0, 0);
                    }
                    SearchActionModeView.this.setContentViewTranslation(0.0f);
                    SearchActionModeView searchActionModeView3 = SearchActionModeView.this;
                    searchActionModeView3.setContentViewPadding(searchActionModeView3.mContentOriginPaddingTop, SearchActionModeView.this.mContentOriginPaddingBottom);
                }
                if (this.mAnimationResultView != null && SearchActionModeView.this.mOriginOverlayMode) {
                    View view2 = this.mAnimationResultView;
                    view2.setPadding(view2.getPaddingLeft(), SearchActionModeView.this.mResultViewOriginPaddingTop, this.mAnimationResultView.getPaddingRight(), SearchActionModeView.this.mResultViewOriginPaddingBottom);
                }
            }
            SearchActionModeView.this.setTranslationY(this.mModeViewTranslationYBeforeMode + this.mModeViewTranslationYLength);
            SearchActionModeView.this.mDimView.setTranslationY(SearchActionModeView.this.getTranslationY() + SearchActionModeView.this.getExpectedHeight());
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            SearchActionModeView.this.setTranslationY(this.mModeViewTranslationYBeforeMode + (this.mModeViewTranslationYLength * f));
            SearchActionModeView.this.mDimView.setTranslationY(SearchActionModeView.this.getTranslationY() + SearchActionModeView.this.getExpectedHeight());
            int i = this.mNestedCoordObTranslationYLength;
            int iMax = Math.max(i, Math.round(i * f));
            if (!SearchActionModeView.this.mOriginOverlayMode) {
                if (z) {
                    if (this.mAnimationNestedCoordOb != null) {
                        SearchActionModeView.this.setContentViewTranslation(((1.0f - f) * this.mContentViewTranslationYBeforeMode) + (f * SearchActionModeView.this.getViewHeight()));
                        this.mAnimationNestedCoordOb.updateCoordinatorHeightGapInfo(iMax, 0);
                    } else {
                        SearchActionModeView searchActionModeView = SearchActionModeView.this;
                        searchActionModeView.setContentViewTranslation(searchActionModeView.getTranslationY() - ((1.0f - f) * SearchActionModeView.this.mPendingInsetTop));
                    }
                } else if (this.mAnimationNestedCoordOb != null) {
                    SearchActionModeView.this.setContentViewTranslation((int) (SearchActionModeView.this.getViewHeight() + SearchActionModeView.this.mPendingInsetTop + ((1.0f - f) * ((this.mModeViewTranslationYBeforeMode - SearchActionModeView.this.getViewHeight()) - SearchActionModeView.this.mPendingInsetTop))));
                    this.mAnimationNestedCoordOb.updateCoordinatorHeightGapInfo(iMax, 0);
                } else {
                    SearchActionModeView searchActionModeView2 = SearchActionModeView.this;
                    searchActionModeView2.setContentViewTranslation(searchActionModeView2.getTranslationY() - ((1.0f - f) * SearchActionModeView.this.mPendingInsetTop));
                }
            }
            if (SearchActionModeView.this.mAnimatedViewListener != null) {
                SearchActionModeView.this.mAnimatedViewListener.onUpdateOffsetY(iMax);
            }
        }

        public final void updateAnimValues() {
            NestedCoordinatorObserver nestedCoordinatorObserver = this.mAnimationNestedCoordOb;
            if (nestedCoordinatorObserver != null) {
                this.mNestedCoordObTranslationYLength = nestedCoordinatorObserver.getNestedScrollableValue();
            }
            if (SearchActionModeView.this.mActionBarHiddenBeforeSearchMode) {
                this.mModeViewTranslationYBeforeMode = 0;
                this.mModeViewTranslationYLength = 0;
                this.mContentViewTranslationYLength = 0;
                return;
            }
            ActionBarView actionBarView = this.mAnimationActionBarView;
            if (actionBarView == null) {
                this.mAnimationAnchorView.getLocationInWindow(SearchActionModeView.this.mLocation);
                int i = SearchActionModeView.this.mLocation[1];
                this.mModeViewTranslationYBeforeMode = i;
                int i2 = i - SearchActionModeView.this.mParentLocationY;
                this.mModeViewTranslationYBeforeMode = i2;
                int i3 = -i2;
                this.mModeViewTranslationYLength = i3;
                this.mContentViewTranslationYLength = i3;
                return;
            }
            int top = actionBarView.getTop();
            int collapsedHeight = this.mAnimationActionBarView.getCollapsedHeight();
            int expandedHeight = this.mAnimationActionBarView.getExpandedHeight();
            int expandState = this.mAnimationActionBarView.getExpandState();
            if (expandState == 0 || expandState == 1) {
                top += collapsedHeight;
            } else if (expandState == 2) {
                top += expandedHeight;
            }
            this.mModeViewTranslationYBeforeMode = top;
            int i4 = -top;
            this.mModeViewTranslationYLength = i4;
            this.mContentViewTranslationYLength = i4 + this.mAnimationActionBarView.getTop();
            if (this.mAnimationNestedCoordOb == null || this.mIsActionBarNestedScrolledBeforeMode || !SearchActionModeView.this.mOriginOverlayMode) {
                return;
            }
            this.mNestedCoordObTranslationYLength += -(expandedHeight - collapsedHeight);
        }
    }

    public class DimViewAnimationProcessor implements ActionModeAnimationListener {
        public DimViewAnimationProcessor() {
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStart(boolean z) {
            if (z) {
                SearchActionModeView.this.mDimView.setOnClickListener(SearchActionModeView.this);
                SearchActionModeView.this.mDimView.setVisibility(0);
                SearchActionModeView.this.mDimView.setAlpha(0.0f);
            }
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            if (z) {
                if (SearchActionModeView.this.mInputView.getText().length() > 0) {
                    SearchActionModeView.this.mDimView.setVisibility(8);
                }
            } else {
                SearchActionModeView.this.mDimView.setVisibility(8);
                SearchActionModeView.this.mDimView.setAlpha(1.0f);
                SearchActionModeView.this.mDimView.setTranslationY(0.0f);
            }
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            SearchActionModeView.this.mDimView.setAlpha(f);
        }
    }

    public class SearchViewAnimationProcessor implements ActionModeAnimationListener {
        public SearchViewAnimationProcessor() {
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStart(boolean z) {
            updateCancelView(z ? 0.0f : 1.0f, SearchActionModeView.this.mInputPaddingRight);
            AnimState animState = new AnimState();
            ViewProperty viewProperty = ViewProperty.ALPHA;
            double d = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            AnimState animStateAdd = animState.add(viewProperty, z ? 0.0d : 1.0d);
            ViewProperty viewProperty2 = SearchActionModeView.SELF_BLUR;
            AnimState animStateAdd2 = animStateAdd.add(viewProperty2, z ? 50.0d : 0.0d);
            AnimState animStateAdd3 = new AnimState().add(viewProperty, z ? 1.0d : 0.0d);
            if (!z) {
                d = 50.0d;
            }
            AnimState animStateAdd4 = animStateAdd3.add(viewProperty2, d);
            if (z) {
                Folme.use(SearchActionModeView.this.mTextCancel).setTo(animStateAdd2).to(animStateAdd4, new AnimConfig().setDelay(100L).setEase(FolmeEase.linear(150L)));
                SearchActionModeView.this.mInputView.getText().clear();
                SearchActionModeView.this.mInputView.addTextChangedListener(SearchActionModeView.this);
            } else {
                Folme.use(SearchActionModeView.this.mTextCancel).setTo(animStateAdd2).to(animStateAdd4, new AnimConfig().setEase(FolmeEase.linear(150L)));
                SearchActionModeView.this.mInputView.removeTextChangedListener(SearchActionModeView.this);
                SearchActionModeView.this.mInputView.getText().clear();
            }
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            if (!z) {
                SearchActionModeView.this.mInputView.removeTextChangedListener(SearchActionModeView.this);
                return;
            }
            int i = SearchActionModeView.this.mPendingInsetTop;
            SearchActionModeView searchActionModeView = SearchActionModeView.this;
            searchActionModeView.setPaddingRelative(searchActionModeView.getPaddingStart(), SearchActionModeView.this.mOriginalPaddingTop + i, SearchActionModeView.this.getPaddingEnd(), SearchActionModeView.this.getPaddingBottom());
            ViewGroup.LayoutParams layoutParams = SearchActionModeView.this.getLayoutParams();
            layoutParams.height = SearchActionModeView.this.mSearchViewHeight + i;
            updateCancelView(1.0f, SearchActionModeView.this.mInputPaddingRight);
            SearchActionModeView.this.setLayoutParams(layoutParams);
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            int iRound = SearchActionModeView.this.mPendingInsetTop;
            if (!SearchActionModeView.this.mActionBarHiddenBeforeSearchMode) {
                iRound = Math.round(iRound * f);
            }
            SearchActionModeView searchActionModeView = SearchActionModeView.this;
            searchActionModeView.setPaddingRelative(searchActionModeView.getPaddingStart(), SearchActionModeView.this.mOriginalPaddingTop + iRound, SearchActionModeView.this.getPaddingEnd(), SearchActionModeView.this.getPaddingBottom());
            ViewGroup.LayoutParams layoutParams = SearchActionModeView.this.getLayoutParams();
            layoutParams.height = SearchActionModeView.this.mSearchViewHeight + iRound;
            updateCancelView(f, SearchActionModeView.this.mInputPaddingRight);
            SearchActionModeView.this.setLayoutParams(layoutParams);
        }

        public void updateCancelView(float f, int i) {
            float f2 = 1.0f - f;
            if (ViewUtils.isLayoutRtl(SearchActionModeView.this)) {
                f2 = f - 1.0f;
            }
            int measuredWidth = SearchActionModeView.this.mTextCancel.getMeasuredWidth();
            if (SearchActionModeView.this.mTextCancel.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) SearchActionModeView.this.mTextCancel.getLayoutParams();
                measuredWidth += marginLayoutParams.getMarginStart() + marginLayoutParams.getMarginEnd();
            }
            SearchActionModeView.this.mTextCancel.setTranslationX(measuredWidth * f2);
            SearchActionModeView.this.mTextCancel.setScaleX(f);
            SearchActionModeView.this.mTextCancel.setScaleY(f);
            int paddingStart = SearchActionModeView.this.getPaddingStart();
            if (SearchActionModeView.this.mFitWindowInsetsEnabled && SearchActionModeView.this.mWindowInsetsPaddingAdded) {
                paddingStart -= SearchActionModeView.this.mInsetsPaddingRect.left;
            }
            int iMax = Math.max(paddingStart, (int) (((measuredWidth - i) * f) + i));
            if (SearchActionModeView.this.mSearchContainer.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) SearchActionModeView.this.mSearchContainer.getLayoutParams();
                marginLayoutParams2.setMarginEnd(iMax);
                SearchActionModeView.this.mSearchContainer.setLayoutParams(marginLayoutParams2);
            }
        }
    }

    public class SplitActionBarAnimationProcessor implements ActionModeAnimationListener {
        public SplitActionBarAnimationProcessor() {
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStart(boolean z) {
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onStop(boolean z) {
        }

        @Override // miuix.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            ActionBarContainer splitActionBarContainer = SearchActionModeView.this.getSplitActionBarContainer();
            if (splitActionBarContainer != null) {
                splitActionBarContainer.setTranslationY(f * splitActionBarContainer.getHeight());
            }
        }
    }

    public SearchActionModeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInActionMode = false;
        this.mInitPaddings = null;
        this.mCancelBtnInitPaddings = null;
        this.mLocation = new int[2];
        this.mFirstLayout = true;
        this.mPendingInsetTop = -1;
        this.mParentLocationY = Integer.MAX_VALUE;
        this.mFitWindowInsetsEnabled = true;
        this.mWindowInsetsPaddingAdded = false;
        this.mInsetsPaddingRect = new Rect();
        this.mHasPendingShowSoftInputTask = false;
        this.mShowSoftInputRunnable = new Runnable() { // from class: miuix.appcompat.internal.app.widget.SearchActionModeView.1
            @Override // java.lang.Runnable
            public void run() {
                if (SearchActionModeView.this.mInputView == null || !SearchActionModeView.this.mInputView.isAttachedToWindow()) {
                    return;
                }
                InputMethodHelper.getInstance(SearchActionModeView.this.getContext()).showKeyBoard(SearchActionModeView.this.mInputView);
            }
        };
        this.mSearchViewHeight = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_search_view_default_height);
        this.mInputPaddingTop = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_search_mode_bg_padding_top);
        Resources resources = context.getResources();
        int i = R.dimen.miuix_appcompat_search_mode_bg_padding;
        this.mInputPaddingRight = resources.getDimensionPixelOffset(i);
        this.mHorizontalPaddingDp = MiuixUIUtils.getDefDimen(context, i);
        this.mLimitTextSizeDp = MiuixUIUtils.isTallFontLang(getContext()) ? 16 : 27;
        this.mExtraPadding = 0;
        this.mExtraPaddingApplyToAnchorByUser = false;
    }

    private View getContentView() {
        WeakReference weakReference = this.mContentView;
        if (weakReference != null && weakReference.get() != null) {
            return (View) this.mContentView.get();
        }
        WeakReference weakReference2 = this.mOverlayView;
        ViewGroup viewGroup = weakReference2 != null ? (ViewGroup) weakReference2.get() : null;
        if (viewGroup == null) {
            return null;
        }
        View viewFindViewById = viewGroup.findViewById(android.R.id.content);
        this.mContentView = new WeakReference(viewFindViewById);
        return viewFindViewById;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getExpectedHeight() {
        return this.mSearchViewHeight + Math.max(this.mPendingInsetTop, 0);
    }

    private Rect getWindowInsetsPaddingRect() {
        Rect rect = new Rect(0, 0, 0, 0);
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(this);
        Insets insets = rootWindowInsets != null ? rootWindowInsets.getInsets(WindowInsetsCompat.Type.systemBars()) : null;
        if (insets == null) {
            return rect;
        }
        rect.top = insets.top;
        rect.bottom = insets.bottom;
        if (ViewUtils.isLayoutRtl(this)) {
            rect.left = insets.right;
            rect.right = insets.left;
            return rect;
        }
        rect.left = insets.left;
        rect.right = insets.right;
        return rect;
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void addAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        if (actionModeAnimationListener == null) {
            return;
        }
        if (this.mAnimationListeners == null) {
            this.mAnimationListeners = new ArrayList();
        }
        if (this.mAnimationListeners.contains(actionModeAnimationListener)) {
            return;
        }
        this.mAnimationListeners.add(actionModeAnimationListener);
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        View view;
        if ((editable == null ? 0 : editable.length()) == 0) {
            View view2 = this.mDimView;
            if (view2 != null) {
                view2.setVisibility(0);
            }
            InputMethodHelper.getInstance(getContext()).showKeyBoard(this.mInputView);
            return;
        }
        if (this.mTextLengthBeforeChanged != 0 || (view = this.mDimView) == null) {
            return;
        }
        view.setVisibility(8);
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void animateToVisibility(boolean z) {
        ActionBarContainer actionBarContainer;
        pollViews();
        float f = getResources().getDisplayMetrics().density;
        updateExtraPadding(f);
        updateViewPadding(this.mExtraPadding, f);
        this.mAnimateToVisible = z;
        this.mCurrentAnimation = makeAnimation();
        if (z) {
            createAnimationListeners();
            WeakReference weakReference = this.mOverlayView;
            ActionBarOverlayLayout actionBarOverlayLayout = weakReference != null ? (ActionBarOverlayLayout) weakReference.get() : null;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setOverlayMode(true);
            }
            if (!this.mOriginOverlayMode && (actionBarContainer = this.mActionBarContainer) != null) {
                actionBarContainer.setTemporaryOverlayMode(true);
            }
        }
        if (isHostActivityDestroyed()) {
            finishAnimation();
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView != null) {
                actionBarView.setLifecycleOwner(null);
                this.mActionBarView = null;
            }
            this.mActionBarContainer = null;
            this.mSplitActionBarContainer = null;
        } else {
            notifyAnimationStart(z);
            this.mCurrentAnimation.start();
        }
        if (!this.mAnimateToVisible) {
            this.mHasPendingShowSoftInputTask = false;
            this.mInputView.clearFocus();
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mInputView.getWindowToken(), 0);
        } else {
            this.mInputView.setFocusable(true);
            this.mInputView.setFocusableInTouchMode(true);
            if (hasWindowFocus()) {
                this.mShowSoftInputRunnable.run();
            } else {
                this.mHasPendingShowSoftInputTask = true;
            }
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.mTextLengthBeforeChanged = charSequence == null ? 0 : charSequence.length();
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void closeMode() {
        this.mInputView.setFocusable(false);
        this.mInputView.setFocusableInTouchMode(false);
        ObjectAnimator objectAnimator = this.mCurrentAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        FrameLayout frameLayout = this.mCustomFrameLayout;
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
    }

    public void createAnimationListeners() {
        if (this.mAnimationListeners == null) {
            this.mAnimationListeners = new ArrayList();
        }
        this.mAnimationListeners.add(new SearchViewAnimationProcessor());
        if (shouldAnimateContent()) {
            this.mAnimationListeners.add(new ContentViewAnimationProcessor());
            if (!this.mActionBarHiddenBeforeSearchMode) {
                this.mAnimationListeners.add(new ActionBarAnimationProcessor());
            }
            this.mAnimationListeners.add(new SplitActionBarAnimationProcessor());
        }
        if (getDimView() != null) {
            this.mAnimationListeners.add(new DimViewAnimationProcessor());
        }
    }

    public final void ensureMaterialImpl() {
        if (this.mMaterialImpl == null) {
            this.mMaterialImpl = new SearchViewMaterialImpl(getMaterialTargetParent());
        }
    }

    public final void ensureMiShadowImpl() {
        if (this.mMiShadowImpl == null) {
            this.mMiShadowImpl = new SearchViewShadowImpl(getMiShadowTargetParent());
        }
    }

    public final void ensureOverlayMaskImpl() {
        if (this.mOverlayMaskImpl == null) {
            this.mOverlayMaskImpl = new SearchViewOverlayMaskImpl(this);
        }
    }

    public void finishAnimation() {
        ObjectAnimator objectAnimator = this.mCurrentAnimation;
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
            this.mCurrentAnimation.cancel();
            this.mCurrentAnimation.setTarget(null);
            this.mCurrentAnimation = null;
        }
    }

    public ActionBarContainer getActionBarContainer() {
        if (this.mActionBarContainer == null) {
            WeakReference weakReference = this.mOverlayView;
            ViewGroup viewGroup = weakReference != null ? (ViewGroup) weakReference.get() : null;
            if (viewGroup != null) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt.getId() == R.id.action_bar_container && (childAt instanceof ActionBarContainer)) {
                        this.mActionBarContainer = (ActionBarContainer) childAt;
                        break;
                    }
                }
            }
            ActionBarContainer actionBarContainer = this.mActionBarContainer;
            if (actionBarContainer != null) {
                int i2 = ((ViewGroup.MarginLayoutParams) actionBarContainer.getLayoutParams()).topMargin;
                this.mActionBarTopMargin = i2;
                if (i2 > 0) {
                    setPaddingRelative(getPaddingStart(), this.mOriginalPaddingTop + this.mPendingInsetTop + this.mActionBarTopMargin, getPaddingEnd(), getPaddingBottom());
                }
            }
        }
        return this.mActionBarContainer;
    }

    public ActionBarView getActionBarView() {
        if (this.mActionBarView == null) {
            WeakReference weakReference = this.mOverlayView;
            ViewGroup viewGroup = weakReference != null ? (ViewGroup) weakReference.get() : null;
            if (viewGroup != null) {
                this.mActionBarView = (ActionBarView) viewGroup.findViewById(R.id.action_bar);
            }
        }
        return this.mActionBarView;
    }

    public float getAnimationProgress() {
        return this.mAnimationProgress;
    }

    public View getCustomView() {
        return this.mCustomView;
    }

    public View getDimView() {
        if (this.mDimView == null) {
            WeakReference weakReference = this.mOverlayView;
            ViewStub viewStub = null;
            ViewGroup viewGroup = weakReference != null ? (ViewGroup) weakReference.get() : null;
            if (viewGroup != null) {
                for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                    if (viewGroup.getChildAt(childCount).getId() == R.id.search_mask_vs) {
                        viewStub = (ViewStub) viewGroup.getChildAt(childCount);
                        break;
                    }
                }
                if (viewStub != null) {
                    this.mDimView = viewStub.inflate();
                } else {
                    this.mDimView = viewGroup.findViewById(R.id.search_mask);
                }
            }
        }
        FrameLayout frameLayout = this.mCustomFrameLayout;
        if (frameLayout != null) {
            frameLayout.setVisibility(0);
        }
        return this.mDimView;
    }

    public ViewGroup getMaterialTargetParent() {
        return (ViewGroup) getChildAt(0);
    }

    public ViewGroup getMiShadowTargetParent() {
        return getMaterialTargetParent();
    }

    public EditText getSearchInput() {
        return this.mInputView;
    }

    public ActionBarContainer getSplitActionBarContainer() {
        if (this.mSplitActionBarContainer == null) {
            WeakReference weakReference = this.mOverlayView;
            ViewGroup viewGroup = weakReference != null ? (ViewGroup) weakReference.get() : null;
            if (viewGroup != null) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt.getId() == R.id.split_action_bar && (childAt instanceof ActionBarContainer)) {
                        this.mSplitActionBarContainer = (ActionBarContainer) childAt;
                        break;
                    }
                }
            }
        }
        return this.mSplitActionBarContainer;
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public int getViewHeight() {
        return this.mSearchViewHeight;
    }

    public ViewPager getViewPager() {
        WeakReference weakReference = this.mOverlayView;
        ActionBarOverlayLayout actionBarOverlayLayout = weakReference != null ? (ActionBarOverlayLayout) weakReference.get() : null;
        if (actionBarOverlayLayout == null || !((ActionBarImpl) actionBarOverlayLayout.getActionBar()).isFragmentViewPagerMode()) {
            return null;
        }
        return (ViewPager) actionBarOverlayLayout.findViewById(R.id.view_pager);
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void initForMode(ActionMode actionMode) {
        this.mInActionMode = true;
        updateResultViewMargin(true);
    }

    public final boolean isHostActivityDestroyed() {
        Context baseContext = getContext() instanceof ContextThemeWrapper ? ((ContextThemeWrapper) getContext()).getBaseContext() : null;
        return (baseContext instanceof Activity) && ((Activity) baseContext).isDestroyed();
    }

    public boolean isMaterialEnabled() {
        ensureMaterialImpl();
        return this.mMaterialImpl.isMaterialEnabled();
    }

    public boolean isMiShadowEnabled() {
        ensureMiShadowImpl();
        return this.mMiShadowImpl.isMiShadowEnabled();
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void killMode() {
        finishAnimation();
        this.mInActionMode = false;
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
        this.mActionBarContainer = null;
        this.mActionBarView = null;
        List list = this.mAnimationListeners;
        if (list != null) {
            list.clear();
            this.mAnimationListeners = null;
        }
        if (this.mAnimatedViewListener != null) {
            this.mAnimatedViewListener = null;
        }
        this.mSplitActionBarContainer = null;
    }

    public ObjectAnimator makeAnimation() {
        ObjectAnimator objectAnimator = this.mCurrentAnimation;
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
            this.mCurrentAnimation.cancel();
            this.mCurrentAnimation.setTarget(null);
            this.mCurrentAnimation = null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "AnimationProgress", 0.0f, 1.0f);
        objectAnimatorOfFloat.addListener(this);
        objectAnimatorOfFloat.setDuration(DeviceHelper.isFeatureWholeAnim() ? 400L : 0L);
        objectAnimatorOfFloat.setInterpolator(obtainInterpolator());
        return objectAnimatorOfFloat;
    }

    public void notifyAnimationEnd(boolean z) {
        List list = this.mAnimationListeners;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ActionModeAnimationListener) it.next()).onStop(z);
        }
    }

    public void notifyAnimationStart(boolean z) {
        List list = this.mAnimationListeners;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ActionModeAnimationListener) it.next()).onStart(z);
        }
    }

    public void notifyAnimationUpdate(boolean z, float f) {
        List list = this.mAnimationListeners;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ActionModeAnimationListener) it.next()).onUpdate(z, f);
        }
    }

    public TimeInterpolator obtainInterpolator() {
        return EaseManager.getInterpolator(0, 0.98f, 0.75f);
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        this.mAnimationCanceled = true;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        if (this.mAnimationCanceled) {
            return;
        }
        this.mCurrentAnimation = null;
        notifyAnimationEnd(this.mAnimateToVisible);
        if (this.mAnimateToVisible) {
            return;
        }
        ActionBarContainer actionBarContainer = this.mActionBarContainer;
        if (actionBarContainer != null) {
            actionBarContainer.setTemporaryOverlayMode(false);
        }
        WeakReference weakReference = this.mOverlayView;
        ActionBarOverlayLayout actionBarOverlayLayout = weakReference != null ? (ActionBarOverlayLayout) weakReference.get() : null;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setOverlayMode(this.mOriginOverlayMode);
            actionBarOverlayLayout.requestDispatchContentInset();
        }
        WeakReference weakReference2 = this.mAnchorView;
        View view = weakReference2 != null ? (View) weakReference2.get() : null;
        if (view != null) {
            view.setVisibility(0);
            view.setAlpha(1.0f);
        }
        setAlpha(0.0f);
        killMode();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.mAnimationCanceled = false;
        if (this.mAnimateToVisible) {
            setAlpha(1.0f);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.mOnBackClickListener != null) {
            if (view.getId() == R.id.search_text_cancel || view.getId() == R.id.search_mask) {
                this.mOnBackClickListener.onClick(view);
            }
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        resetLocationY();
        this.mFirstLayout = true;
        resetInputTextSize();
        this.mWindowInsetsPaddingAdded = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        finishAnimation();
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.setLifecycleOwner(null);
            this.mActionBarView = null;
        }
        this.mActionBarContainer = null;
        this.mSplitActionBarContainer = null;
        this.mHasPendingShowSoftInputTask = false;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        SearchViewOverlayMaskImpl searchViewOverlayMaskImpl = this.mOverlayMaskImpl;
        if (searchViewOverlayMaskImpl != null) {
            searchViewOverlayMaskImpl.drawOverlayMask(canvas);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mInitPaddings = new ViewUtils.RelativePadding(this);
        Drawable background = getBackground();
        if (background != null) {
            Rect rect = new Rect();
            background.getPadding(rect);
            ViewUtils.RelativePadding relativePadding = this.mInitPaddings;
            relativePadding.top = rect.top;
            relativePadding.bottom = rect.bottom;
        }
        ViewUtils.RelativePadding relativePadding2 = this.mInitPaddings;
        if (relativePadding2.top == 0) {
            relativePadding2.top = this.mInputPaddingTop;
        }
        View viewFindViewById = findViewById(R.id.search_text_cancel);
        this.mTextCancel = viewFindViewById;
        viewFindViewById.setOnClickListener(this);
        this.mCancelBtnInitPaddings = new ViewUtils.RelativePadding(this.mTextCancel);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.search_container);
        this.mSearchContainer = viewGroup;
        CompatViewMethod.setForceDarkAllowed(viewGroup, false);
        this.mInputView = (EditText) findViewById(android.R.id.input);
        resetInputTextSize();
        this.mOriginalPaddingTop = this.mInitPaddings.top;
        View contentView = getContentView();
        if (contentView != null) {
            this.mContentOriginPaddingTop = contentView.getPaddingTop();
            this.mContentOriginPaddingBottom = contentView.getPaddingBottom();
        }
    }

    public void onFloatingModeChanged() {
        resetLocationY();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View view = this.mDimView;
        if (view != null) {
            view.setTranslationY((getTranslationY() + i4) - i2);
        }
        ObjectAnimator objectAnimator = this.mCurrentAnimation;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            float f = getResources().getDisplayMetrics().density;
            updateExtraPadding(f);
            updateViewPadding(this.mExtraPadding, f);
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z && this.mHasPendingShowSoftInputTask) {
            this.mHasPendingShowSoftInputTask = false;
            this.mShowSoftInputRunnable.run();
        }
    }

    public void pollViews() {
        getActionBarView();
        getActionBarContainer();
        getSplitActionBarContainer();
    }

    public void rePaddingAndRelayout(Rect rect) {
        int i = this.mPendingInsetTop;
        int i2 = rect.top;
        if (i != i2) {
            this.mPendingInsetTop = i2;
            updateOnPaddingTopChanged();
            if (!this.mOriginOverlayMode) {
                WeakReference weakReference = this.mAnchorParentView;
                if ((weakReference != null ? (View) weakReference.get() : null) instanceof NestedCoordinatorObserver) {
                    setContentViewPadding(this.mPendingInsetTop + getViewHeight(), 0);
                } else {
                    setContentViewPadding(this.mPendingInsetTop, 0);
                }
            }
            updateResultViewMargin(this.mInActionMode);
            requestLayout();
        }
    }

    public final void resetInputTextSize() {
        EditText editText = this.mInputView;
        if (editText == null) {
            return;
        }
        Context context = editText.getContext();
        float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_search_edit_text_size) / context.getResources().getDisplayMetrics().density;
        int i = this.mLimitTextSizeDp;
        if (dimensionPixelSize > i) {
            this.mInputView.setTextSize(1, i);
        }
    }

    public final void resetLocationY() {
        this.mParentLocationY = Integer.MAX_VALUE;
    }

    public void setActionBarHiddenBeforeSearchMode(boolean z) {
        this.mActionBarHiddenBeforeSearchMode = z;
    }

    public void setAnchorApplyExtraPaddingByUser(boolean z) {
        if (this.mExtraPaddingApplyToAnchorByUser != z) {
            this.mExtraPaddingApplyToAnchorByUser = z;
            float f = getResources().getDisplayMetrics().density;
            updateExtraPadding(f);
            updateViewPadding(this.mExtraPadding, f);
        }
    }

    public void setAnchorView(View view) {
        if (view == null || view.findViewById(R.id.search_mode_stub) == null) {
            return;
        }
        this.mAnchorView = new WeakReference(view);
        if (view.getParent() != null) {
            this.mAnchorParentView = new WeakReference((View) view.getParent());
        }
    }

    public void setAnimateView(View view) {
        if (view != null) {
            this.mAnimateView = new WeakReference(view);
        }
    }

    public void setAnimatedViewListener(SearchActionMode.AnimatedViewListener animatedViewListener) {
        this.mAnimatedViewListener = animatedViewListener;
    }

    public void setAnimationProgress(float f) {
        this.mAnimationProgress = f;
        notifyAnimationUpdate(this.mAnimateToVisible, f);
    }

    public void setContentViewPadding(int i, int i2) {
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setPaddingRelative(contentView.getPaddingStart(), i + this.mContentOriginPaddingTop, contentView.getPaddingEnd(), i2 + this.mContentOriginPaddingBottom);
        }
    }

    public void setContentViewTranslation(float f) {
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setTranslationY(f);
        }
    }

    public void setCustomView(View view) {
        if (view == null || this.mHasSetCustomView) {
            return;
        }
        this.mCustomView = view;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.mCustomFrameLayout = frameLayout;
        frameLayout.setLayoutParams(layoutParams);
        this.mCustomFrameLayout.setId(R.id.searchActionMode_customFrameLayout);
        this.mCustomFrameLayout.addView(this.mCustomView, layoutParams);
        this.mCustomFrameLayout.setPadding(0, 0, 0, 0);
        getDimView();
        ((ViewGroup) this.mDimView).addView(this.mCustomFrameLayout, layoutParams);
        this.mHasSetCustomView = true;
    }

    public void setExtraPaddingPolicy(ExtraPaddingPolicy extraPaddingPolicy) {
        if (this.mExtraPaddingPolicy != extraPaddingPolicy) {
            this.mExtraPaddingPolicy = extraPaddingPolicy;
            float f = getResources().getDisplayMetrics().density;
            updateExtraPadding(f);
            updateViewPadding(this.mExtraPadding, f);
        }
    }

    public void setFitWindowInsetsEnabled(boolean z) {
        this.mFitWindowInsetsEnabled = z;
    }

    public void setFloatingMode(boolean z) {
        if (z) {
            if (HyperMaterialUtils.isFeatureEnableNoCache(getContext())) {
                setMaterialEnabled(true);
                return;
            } else {
                setMiShadowEnabled(true);
                return;
            }
        }
        if (isMaterialEnabled()) {
            setMaterialEnabled(false);
        }
        if (isMiShadowEnabled()) {
            setMiShadowEnabled(false);
        }
    }

    public void setMaskColor(int i) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setMaskColor(i);
    }

    public void setMaskExtraHeight(float f) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setMaskExtraHeight(f);
    }

    public void setMaterialEnabled(boolean z) {
        ensureMaterialImpl();
        this.mMaterialImpl.setMaterialEnabled(z);
    }

    public void setMiShadowEnabled(boolean z) {
        ensureMiShadowImpl();
        this.mMiShadowImpl.setMiShadowEnabled(z);
    }

    public void setOnBackClickListener(View.OnClickListener onClickListener) {
        this.mOnBackClickListener = onClickListener;
    }

    public void setOverlayMaskConfig(@Nullable OverlayMaskConfig overlayMaskConfig) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setOverlayMaskConfig(overlayMaskConfig);
    }

    public void setOverlayMaskEnabled(boolean z) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setOverlayMaskEnabled(z);
    }

    public void setOverlayModeView(ActionBarOverlayLayout actionBarOverlayLayout) {
        this.mOverlayView = new WeakReference(actionBarOverlayLayout);
        this.mOriginOverlayMode = actionBarOverlayLayout.isInOverlayMode();
    }

    public void setResultView(View view) {
        if (view == null || (((View) view.getParent()) instanceof NestedContentInsetObserver)) {
            return;
        }
        this.mResultView = new WeakReference(view);
        this.mResultViewOriginPaddingTop = view.getPaddingTop();
        this.mResultViewOriginPaddingBottom = view.getPaddingBottom();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            this.mResultViewOriginMarginTop = marginLayoutParams.topMargin;
            this.mResultViewOriginMarginBottom = marginLayoutParams.bottomMargin;
        }
        this.mResultViewSet = true;
    }

    public final boolean shouldAnimateContent() {
        return this.mAnchorView != null;
    }

    public final void updateExtraPadding(float f) {
        WeakReference weakReference = this.mOverlayView;
        ActionBarOverlayLayout actionBarOverlayLayout = weakReference != null ? (ActionBarOverlayLayout) weakReference.get() : null;
        boolean zIsExtraPaddingApplyToContentEnable = actionBarOverlayLayout != null ? actionBarOverlayLayout.isExtraPaddingApplyToContentEnable() : false;
        ExtraPaddingPolicy extraPaddingPolicy = this.mExtraPaddingPolicy;
        if (extraPaddingPolicy != null && extraPaddingPolicy.isEnable() && (zIsExtraPaddingApplyToContentEnable || this.mExtraPaddingApplyToAnchorByUser)) {
            this.mExtraPadding = (int) (this.mExtraPaddingPolicy.getExtraPaddingDp() * f);
        } else {
            this.mExtraPadding = 0;
        }
    }

    public final void updateOnPaddingTopChanged() {
        setPaddingRelative(getPaddingStart(), this.mOriginalPaddingTop + this.mPendingInsetTop, getPaddingEnd(), getPaddingBottom());
        getLayoutParams().height = this.mSearchViewHeight + this.mPendingInsetTop;
    }

    public final void updateResultViewMargin(boolean z) {
        if (z) {
            WeakReference weakReference = this.mResultView;
            View view = weakReference != null ? (View) weakReference.get() : null;
            WeakReference weakReference2 = this.mAnchorView;
            View view2 = weakReference2 != null ? (View) weakReference2.get() : null;
            if ((view2 == null || view == null || view2.getParent() == view.getParent()) && view != null && (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) && !this.mOriginOverlayMode) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams.topMargin = getViewHeight();
                marginLayoutParams.bottomMargin = 0;
                view.setLayoutParams(marginLayoutParams);
                view.requestLayout();
            }
        }
    }

    public final void updateViewPadding(int i, float f) {
        int paddingEnd;
        Rect rect = new Rect(0, 0, 0, 0);
        if (this.mFitWindowInsetsEnabled) {
            rect = getWindowInsetsPaddingRect();
            this.mInsetsPaddingRect.set(rect);
        }
        int i2 = ((int) (this.mHorizontalPaddingDp * f)) + i + rect.left;
        boolean z = this.mWindowInsetsPaddingAdded;
        if (z) {
            paddingEnd = getPaddingEnd();
        } else {
            paddingEnd = this.mFitWindowInsetsEnabled ? rect.right : getPaddingEnd();
            this.mWindowInsetsPaddingAdded = true;
        }
        setPaddingRelative(i2, getPaddingTop(), paddingEnd, getPaddingBottom());
        View view = this.mTextCancel;
        ViewUtils.RelativePadding relativePadding = this.mCancelBtnInitPaddings;
        view.setPaddingRelative(relativePadding.start, relativePadding.top, relativePadding.end, relativePadding.bottom);
        int measuredWidth = this.mTextCancel.getMeasuredWidth();
        if (this.mTextCancel.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTextCancel.getLayoutParams();
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_search_action_mode_cancel_text_margin_end) + i;
            if (marginLayoutParams.getMarginEnd() != dimensionPixelSize) {
                marginLayoutParams.setMarginEnd(dimensionPixelSize);
                this.mTextCancel.setLayoutParams(marginLayoutParams);
            }
            measuredWidth += marginLayoutParams.getMarginStart() + marginLayoutParams.getMarginEnd();
        }
        if (this.mSearchContainer.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mSearchContainer.getLayoutParams();
            int iMax = Math.max(getPaddingStart(), measuredWidth);
            if (marginLayoutParams2.getMarginEnd() == iMax && z) {
                return;
            }
            marginLayoutParams2.setMarginEnd(iMax);
            this.mSearchContainer.setLayoutParams(marginLayoutParams2);
        }
    }
}

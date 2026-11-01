package miuix.nestedheader.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import androidx.annotation.Nullable;
import androidx.core.view.ScrollingView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.RomUtils;
import miuix.device.DeviceUtils;
import miuix.internal.util.AttributeResolver;
import miuix.nestedheader.R;
import miuix.theme.token.hypermaterial.Blur;
import miuix.theme.token.hypermaterial.Mask;
import miuix.view.BlurableWidget;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class NestedHeaderLayout extends NestedScrollingLayout implements BlurableWidget {
    public boolean mAcceptHeaderRootViewAlpha;
    public boolean mAcceptTriggerRootViewAlpha;
    public boolean mAdsorptionToNoOverlay;
    public boolean mAutoAnim;
    public MiuiBlurUiHelper mBlurUiHelper;
    public Rect mClipOverBgBounds;
    public int mCoordinatorHeightGapInSearchMode;
    public boolean mEnableHeaderAutoClose;
    public int mHeaderBottomMargin;
    public int mHeaderContentBottomMargin;
    public int mHeaderContentId;
    public float mHeaderContentMinHeight;
    public View mHeaderContentView;
    public int mHeaderMeasuredHeight;
    public int mHeaderTopMargin;
    public int mHeaderTotalHeight;
    public View mHeaderView;
    public boolean mHeaderViewClipEnabled;
    public int mHeaderViewId;
    public int mHeaderVisibleHeight;
    public boolean mIsCommonLiteStrategy;
    public boolean mIsMaskBitmapFromWindowBg;
    public boolean mIsShowOverBg;
    public boolean mIsStickyBeyondTrigger;
    public boolean mIsTouchStart;
    public int mLastScrollingProgress;
    public Drawable mMaskBackground;
    public Drawable mMaskBackgroundInBlur;
    public MaterialDayNightConfig mMaterial;
    public NestedHeaderChangedListener mNestedHeaderChangedListener;
    public NestedScrollingLayout.OnNestedChangedListener mOnNestedChangedListener;
    public NestedHeaderOverlayMaskView mOverBgView;
    public float mRangeOffset;
    public View mRootView;
    public boolean mSaveStickyMaskEnabled;
    public boolean mScrollableViewClipEnabled;
    public int mStickyTotalHeight;
    public View mStickyView;
    public int mStickyViewId;
    public int mTriggerBottomMargin;
    public int mTriggerContentBottomMargin;
    public int mTriggerContentId;
    public float mTriggerContentMinHeight;
    public View mTriggerContentView;
    public int mTriggerMeasuredHeight;
    public int mTriggerTopMargin;
    public View mTriggerView;
    public int mTriggerViewId;
    public String mValueTag;

    public interface NestedHeaderChangedListener {
        default void onHeaderClosed(View view) {
        }

        default void onHeaderOpened(View view) {
        }

        default void onOverViewBlurStateChanged(boolean z) {
        }

        default void onScrollingProgressChanged(int i, boolean z, int i2, float f) {
        }

        default void onTriggerClosed(View view) {
        }

        default void onTriggerOpened(View view) {
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.nestedheader.widget.NestedHeaderLayout.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int scrollingProgress;
        public boolean stickyMaskEnabled;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.stickyMaskEnabled = parcel.readInt() != 0;
            this.scrollingProgress = parcel.readInt();
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.stickyMaskEnabled = parcel.readInt() != 0;
            this.scrollingProgress = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.stickyMaskEnabled ? 1 : 0);
            parcel.writeInt(this.scrollingProgress);
        }
    }

    public NestedHeaderLayout(Context context) {
        this(context, null);
    }

    public NestedHeaderLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedHeaderLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHeaderBottomMargin = 0;
        this.mHeaderTopMargin = 0;
        this.mTriggerBottomMargin = 0;
        this.mTriggerTopMargin = 0;
        this.mHeaderContentBottomMargin = 0;
        this.mTriggerContentBottomMargin = 0;
        this.mHeaderTotalHeight = 0;
        this.mHeaderMeasuredHeight = 0;
        this.mHeaderVisibleHeight = 0;
        this.mTriggerMeasuredHeight = 0;
        this.mStickyTotalHeight = 0;
        this.mLastScrollingProgress = 0;
        this.mIsTouchStart = false;
        this.mAutoAnim = true;
        this.mAdsorptionToNoOverlay = false;
        this.mAcceptTriggerRootViewAlpha = false;
        this.mAcceptHeaderRootViewAlpha = false;
        this.mClipOverBgBounds = new Rect();
        this.mIsShowOverBg = false;
        this.mCoordinatorHeightGapInSearchMode = 0;
        this.mScrollableViewClipEnabled = true;
        this.mHeaderViewClipEnabled = true;
        this.mValueTag = Long.toString(SystemClock.elapsedRealtime());
        this.mOnNestedChangedListener = new NestedScrollingLayout.OnNestedChangedListener() { // from class: miuix.nestedheader.widget.NestedHeaderLayout.3
            public final void autoAdsorptionForOverScrollTo() {
                final String string = Long.toString(SystemClock.elapsedRealtime());
                NestedHeaderLayout.this.mValueTag = string;
                Folme.useValue(new Object[0]).setTo(string, Integer.valueOf(NestedHeaderLayout.this.mOverScrollingTo)).to(string, 0, new AnimConfig().setEase(-2, 1.0f, 0.4f).addListeners(new TransitionListener() { // from class: miuix.nestedheader.widget.NestedHeaderLayout.3.1
                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, Collection collection) {
                        UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, string);
                        if (updateInfoFindByName == null || !NestedHeaderLayout.this.isScrolling(string)) {
                            return;
                        }
                        NestedHeaderLayout.this.syncOverScrollTo(updateInfoFindByName.getIntValue());
                    }
                }));
            }

            @Override // miuix.nestedheader.widget.NestedScrollingLayout.OnNestedChangedListener
            public void onStartNestedScroll(int i2) {
                if (i2 == 0) {
                    updateTouch(true);
                } else {
                    updateTag();
                }
            }

            @Override // miuix.nestedheader.widget.NestedScrollingLayout.OnNestedChangedListener
            public void onStopNestedScroll(int i2) {
                if (i2 == 0) {
                    updateTouch(false);
                }
            }

            @Override // miuix.nestedheader.widget.NestedScrollingLayout.OnNestedChangedListener
            public void onStopNestedScrollAccepted(int i2) {
                if (NestedHeaderLayout.this.mAutoAnim) {
                    updateAdsorption();
                }
            }

            public final void updateAdsorption() {
                int scrollingFrom = NestedHeaderLayout.this.getScrollingFrom();
                int scrollingTo = NestedHeaderLayout.this.getScrollingTo();
                NestedHeaderLayout nestedHeaderLayout = NestedHeaderLayout.this;
                int i2 = nestedHeaderLayout.mContentInsetTop + scrollingFrom;
                int scrollingProgress = nestedHeaderLayout.getScrollingProgress();
                if (scrollingProgress == 0 || scrollingProgress >= scrollingTo || scrollingProgress <= scrollingFrom) {
                    if (scrollingProgress != 0 && scrollingProgress < scrollingTo && scrollingProgress == scrollingFrom && NestedHeaderLayout.this.mAdsorptionToNoOverlay) {
                        NestedHeaderLayout nestedHeaderLayout2 = NestedHeaderLayout.this;
                        nestedHeaderLayout2.autoAdsorption(nestedHeaderLayout2.getStickyScrollToOnNested());
                        return;
                    } else {
                        if (NestedHeaderLayout.this.mOverScrollingTo > 0) {
                            autoAdsorptionForOverScrollTo();
                            return;
                        }
                        return;
                    }
                }
                if (NestedHeaderLayout.this.mEnableHeaderAutoClose && scrollingProgress < i2 * 0.33f) {
                    scrollingTo = (NestedHeaderLayout.this.isHeaderOpen() || scrollingProgress >= i2) ? NestedHeaderLayout.this.getHeaderCloseProgress() : NestedHeaderLayout.this.getScrollingFrom();
                } else if (scrollingProgress < scrollingTo * 0.5f) {
                    if (!NestedHeaderLayout.this.mEnableHeaderAutoClose && scrollingProgress < 0) {
                        return;
                    } else {
                        scrollingTo = 0;
                    }
                }
                if (NestedHeaderLayout.this.mAdsorptionToNoOverlay) {
                    scrollingTo = NestedHeaderLayout.this.getStickyScrollToOnNested();
                }
                NestedHeaderLayout.this.autoAdsorption(scrollingTo);
            }

            public final void updateTag() {
                NestedHeaderLayout.this.mValueTag = Long.toString(SystemClock.elapsedRealtime());
            }

            public final void updateTouch(boolean z) {
                NestedHeaderLayout.this.mIsTouchStart = z;
                if (NestedHeaderLayout.this.mIsTouchStart) {
                    updateTag();
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NestedHeaderLayout);
        this.mHeaderViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.NestedHeaderLayout_headerView, R.id.header_view);
        this.mStickyViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.NestedHeaderLayout_stickyView, R.id.sticky_view);
        this.mTriggerViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.NestedHeaderLayout_triggerView, R.id.trigger_view);
        this.mHeaderContentId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.NestedHeaderLayout_headerContentId, R.id.header_content_view);
        this.mTriggerContentId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.NestedHeaderLayout_triggerContentId, R.id.trigger_content_view);
        int i2 = R.styleable.NestedHeaderLayout_headerContentMinHeight;
        Resources resources = context.getResources();
        int i3 = R.dimen.miuix_nested_header_layout_content_min_height;
        this.mHeaderContentMinHeight = typedArrayObtainStyledAttributes.getDimension(i2, resources.getDimension(i3));
        this.mTriggerContentMinHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.NestedHeaderLayout_triggerContentMinHeight, context.getResources().getDimension(i3));
        this.mRangeOffset = typedArrayObtainStyledAttributes.getDimension(R.styleable.NestedHeaderLayout_rangeOffset, 0.0f);
        this.mEnableHeaderAutoClose = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NestedHeaderLayout_headerAutoClose, true);
        this.mIsStickyBeyondTrigger = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NestedHeaderLayout_stickyBeyondTrigger, false);
        this.mSaveStickyMaskEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NestedHeaderLayout_saveStickyMaskEnabled, false);
        try {
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.NestedHeaderLayout_maskBackground);
            this.mMaskBackground = drawable;
            if (drawable == null) {
                Drawable drawableMutate = AttributeResolver.resolveDrawable(getContext(), android.R.attr.windowBackground).mutate();
                this.mMaskBackground = drawableMutate;
                if ((drawableMutate instanceof BitmapDrawable) || (drawableMutate instanceof NinePatchDrawable)) {
                    this.mIsMaskBitmapFromWindowBg = true;
                }
            }
        } catch (Exception e) {
            Log.e("NestedHeaderLayout", "maskBackground error " + e);
        }
        typedArrayObtainStyledAttributes.recycle();
        addOnScrollListener(this.mOnNestedChangedListener);
    }

    private void applyContentAlpha(List list, float f) {
        if (list == null) {
            return;
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, f));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((View) it.next()).setAlpha(fMax);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autoAdsorption(int i) {
        final String string = Long.toString(SystemClock.elapsedRealtime());
        this.mValueTag = string;
        Folme.useValue(new Object[0]).setTo(string, Integer.valueOf(getScrollingProgress())).to(string, Integer.valueOf(i), new AnimConfig().setEase(-2, 1.0f, 0.35f).addListeners(new TransitionListener() { // from class: miuix.nestedheader.widget.NestedHeaderLayout.4
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, string);
                if (updateInfoFindByName == null || !NestedHeaderLayout.this.isScrolling(string)) {
                    return;
                }
                NestedHeaderLayout.this.syncScrollingProgress(updateInfoFindByName.getIntValue());
            }
        }));
    }

    private void checkSendHeaderChangeListener(int i, int i2, boolean z) {
        if (this.mNestedHeaderChangedListener == null) {
            return;
        }
        int iMax = 0;
        if (z) {
            if (i2 == getScrollingTo() && getTriggerViewVisible()) {
                this.mNestedHeaderChangedListener.onTriggerOpened(this.mTriggerView);
            }
            if ((i < getHeaderProgressTo() && i2 >= getHeaderProgressTo() && getHeaderViewVisible()) || i2 == getHeaderProgressTo()) {
                this.mNestedHeaderChangedListener.onHeaderOpened(this.mHeaderView);
            }
        } else {
            if (i2 == 0 && getTriggerViewVisible()) {
                this.mNestedHeaderChangedListener.onTriggerClosed(this.mTriggerView);
            } else if (i2 == getScrollingFrom() && !getHeaderViewVisible()) {
                this.mNestedHeaderChangedListener.onTriggerClosed(this.mTriggerView);
            }
            int scrollingFrom = getHeaderViewVisible() ? 0 : getScrollingFrom();
            if (i > getHeaderProgressFrom() && i2 <= getHeaderProgressFrom() && getHeaderViewVisible()) {
                this.mNestedHeaderChangedListener.onHeaderClosed(this.mHeaderView);
            }
            if (i > scrollingFrom && i2 < scrollingFrom && getTriggerViewVisible()) {
                this.mNestedHeaderChangedListener.onTriggerClosed(this.mTriggerView);
            }
        }
        boolean z2 = i2 < getHeaderProgressFrom();
        View view = this.mHeaderView;
        if (view != null) {
            int height = view.getHeight();
            Rect clipBounds = this.mHeaderView.getClipBounds();
            iMax = clipBounds != null ? Math.max(0, clipBounds.height()) : height;
        }
        this.mNestedHeaderChangedListener.onScrollingProgressChanged(i2, z2, iMax, Math.max(0.0f, 1.0f - ((iMax * 1.0f) / this.mHeaderTotalHeight)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isScrolling(String str) {
        return (this.mIsTouchStart || !this.mValueTag.equals(str) || getAcceptedNestedFlingInConsumedProgress()) ? false : true;
    }

    private List makeContentViewList(View view, boolean z) {
        if (view == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (!z) {
            arrayList.add(view);
            return arrayList;
        }
        if (!(view instanceof ViewGroup)) {
            arrayList.add(view);
            return arrayList;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            arrayList.add(viewGroup.getChildAt(i));
        }
        return arrayList;
    }

    private List makeHeaderContentViewList(View view) {
        return makeContentViewList(view, this.mHeaderContentId == R.id.header_content_view || this.mHeaderContentView != null);
    }

    private List makeTriggerContentViewList(View view) {
        return makeContentViewList(view, this.mTriggerContentId == R.id.trigger_content_view || this.mTriggerContentView != null);
    }

    private void relayoutContent(View view, View view2, int i, int i2, boolean z) {
        view.layout(view.getLeft(), i, view.getRight(), Math.max(i, view.getMeasuredHeight() + i + i2));
        if (view != view2) {
            int iMax = Math.max(view2.getTop(), 0);
            int top = view2.getTop();
            int measuredHeight = view2.getMeasuredHeight() + iMax;
            if (z) {
                i2 /= 2;
            }
            view2.layout(view2.getLeft(), iMax, view2.getRight(), Math.max(top, measuredHeight + i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncScrollingProgress(int i) {
        updateScrollingProgress(i);
        onScrollingProgressUpdated(i);
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.applyBlur(z);
        }
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialConfig getCurrentMaterial() {
        return super.getCurrentMaterial();
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public int getHeaderCloseProgress() {
        return this.mIsOverlayMode ? getScrollingFrom() + this.mContentInsetTop + this.mStickyTotalHeight : getScrollingFrom();
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public int getHeaderProgressFrom() {
        return this.mIsOverlayMode ? getScrollingFrom() + this.mContentInsetTop + this.mHeaderTotalHeight : getScrollingFrom();
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public int getHeaderProgressTo() {
        int scrollingFrom;
        int i;
        if (this.mIsOverlayMode) {
            scrollingFrom = getScrollingFrom() + this.mContentInsetTop + this.mHeaderTotalHeight;
            i = this.mStickyTotalHeight;
        } else {
            scrollingFrom = getScrollingFrom();
            i = this.mHeaderTotalHeight;
        }
        return scrollingFrom + i;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    public boolean getHeaderViewVisible() {
        View view = this.mHeaderView;
        return view != null && view.getVisibility() == 0;
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialDayNightConfig getMaterial() {
        return this.mMaterial;
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout, miuix.core.view.NestedCoordinatorObserver
    public int getNestedScrollableValue() {
        return -(this.mStickyTotalHeight + this.mHeaderTotalHeight);
    }

    public View getScrollableView() {
        return this.mScrollableView;
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public int getScrollableViewMaxHeightWithoutOverlay() {
        View view;
        int measuredHeight = getMeasuredHeight();
        if (!this.mInSearchMode || (view = this.mStickyView) == null || view.getVisibility() == 0) {
            View view2 = this.mStickyView;
            if (view2 != null && view2.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mStickyView.getLayoutParams();
                this.mStickyTotalHeight = this.mStickyView.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
            int iMax = (measuredHeight - this.mContentInsetTop) - Math.max(0, this.mStickyTotalHeight);
            if (iMax > 0) {
                return iMax;
            }
        } else {
            int i = measuredHeight - this.mContentInsetTop;
            if (i > 0) {
                return i;
            }
        }
        return measuredHeight;
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public int getStickyScrollToOnNested() {
        int scrollingFrom;
        int i;
        View view;
        if (this.mInSearchMode && (view = this.mStickyView) != null && view.getVisibility() == 4) {
            scrollingFrom = getScrollingFrom();
            i = this.mContentInsetTop;
        } else {
            scrollingFrom = getScrollingFrom() + this.mContentInsetTop;
            i = this.mStickyTotalHeight;
        }
        return scrollingFrom + i;
    }

    public View getStickyView() {
        return this.mStickyView;
    }

    public boolean getStickyViewVisible() {
        View view = this.mStickyView;
        return view != null && view.getVisibility() == 0;
    }

    public boolean getTriggerViewVisible() {
        View view = this.mTriggerView;
        return view != null && view.getVisibility() == 0;
    }

    public boolean isApplyBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            return miuiBlurUiHelper.isApplyBlur();
        }
        return false;
    }

    public boolean isEnableBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            return miuiBlurUiHelper.isEnableBlur();
        }
        return false;
    }

    public boolean isHeaderOpen() {
        return getHeaderViewVisible() && getScrollingProgress() >= getHeaderProgressTo();
    }

    public boolean isOverlayMode() {
        return this.mIsOverlayMode;
    }

    public boolean isStickyMaskEnabled() {
        return this.mOverBgView.isStickyMaskEnabled();
    }

    @Override // android.view.View
    public void offsetTopAndBottom(int i) {
        super.offsetTopAndBottom(i);
        updateOverBgState(getScrollingProgress(), this.mHeaderVisibleHeight);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mRootView = getRootView();
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.onConfigChanged();
            if (this.mIsCommonLiteStrategy || isOverlayMode() || this.mUserSetOverlayMode != null) {
                return;
            }
            this.mIsOverlayMode = true;
        }
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHeaderView = findViewById(this.mHeaderViewId);
        this.mStickyView = findViewById(this.mStickyViewId);
        this.mTriggerView = findViewById(this.mTriggerViewId);
        View view = this.mStickyView;
        if (view != null) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: miuix.nestedheader.widget.NestedHeaderLayout.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    int i9 = (i4 - i2) - (i8 - i6);
                    if (i9 != 0) {
                        NestedHeaderLayout nestedHeaderLayout = NestedHeaderLayout.this;
                        if (nestedHeaderLayout.mInSearchMode) {
                            nestedHeaderLayout.updateScrollingRange(true, false, false, false);
                            NestedHeaderLayout nestedHeaderLayout2 = NestedHeaderLayout.this;
                            nestedHeaderLayout2.updateScrollingProgressImmediately(Math.min(nestedHeaderLayout2.getScrollingProgress() + i9, -NestedHeaderLayout.this.mHeaderTotalHeight));
                        }
                    }
                }
            });
        }
        View view2 = this.mHeaderView;
        if (view2 == null && this.mTriggerView == null && this.mStickyView == null) {
            throw new IllegalArgumentException("The headerView or triggerView or stickyView attribute is required and must refer to a valid child.");
        }
        if (view2 != null) {
            View viewFindViewById = view2.findViewById(this.mHeaderContentId);
            this.mHeaderContentView = viewFindViewById;
            if (viewFindViewById == null) {
                this.mHeaderContentView = this.mHeaderView.findViewById(android.R.id.inputArea);
            }
        }
        View view3 = this.mTriggerView;
        if (view3 != null) {
            View viewFindViewById2 = view3.findViewById(this.mTriggerContentId);
            this.mTriggerContentView = viewFindViewById2;
            if (viewFindViewById2 == null) {
                this.mTriggerContentView = this.mTriggerView.findViewById(android.R.id.inputArea);
            }
        }
        if (this.mOverBgView == null) {
            NestedHeaderOverlayMaskView nestedHeaderOverlayMaskView = new NestedHeaderOverlayMaskView(getContext());
            this.mOverBgView = nestedHeaderOverlayMaskView;
            nestedHeaderOverlayMaskView.setVisibility(4);
            this.mOverBgView.setClickable(true);
            this.mOverBgView.setBackground(this.mMaskBackground);
            this.mOverBgView.setImportantForAccessibility(4);
            addView(this.mOverBgView, indexOfChild(this.mScrollableView) + 1, new ViewGroup.LayoutParams(-1, -2));
        }
        this.mIsOverlayMode = true;
        if (HyperMaterialUtils.isEnable()) {
            this.mMaterial = MaterialDayNightConfig.create(RomUtils.getHyperOsVersion() > 2 ? Mask.Pured_Regular : Blur.ExtraHeavy);
            this.mBlurUiHelper = new MiuiBlurUiHelper(getContext(), this.mOverBgView, false, false, new MiuiBlurUiHelper.BlurStateCallback() { // from class: miuix.nestedheader.widget.NestedHeaderLayout.2
                public final boolean isDarkThemeOverlay;

                {
                    this.isDarkThemeOverlay = MiuixUIUtils.isDarkThemeOverlay(NestedHeaderLayout.this.getContext(), R.color.miuix_default_color_on_surface_light);
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public Drawable getBackground() {
                    return NestedHeaderLayout.this.mMaskBackground;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public int getBackgroundColor() {
                    if (this.isDarkThemeOverlay) {
                        return AttributeResolver.resolveColor(NestedHeaderLayout.this.getContext(), miuix.theme.R.attr.colorSurface, 0);
                    }
                    return 0;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public MaterialConfig.BlurConfig getBlurConfig(boolean z) {
                    MaterialConfig.BlurConfig blurConfig;
                    MaterialConfig.ColorBlendConfig colorBlendConfig;
                    MaterialDayNightConfig materialDayNightConfig = NestedHeaderLayout.this.mMaterial;
                    if (materialDayNightConfig == null) {
                        return null;
                    }
                    return (!NestedHeaderLayout.this.mIsMaskBitmapFromWindowBg || (blurConfig = materialDayNightConfig.getBlurConfig(z)) == null || (colorBlendConfig = blurConfig.colorBlendConfig) == null) ? materialDayNightConfig.getBlurConfig(z) : new MaterialConfig.BlurConfig(blurConfig.blurBgMode, blurConfig.blurContentMode, blurConfig.blurType, blurConfig.blurRadius, new int[]{colorBlendConfig.blendColors[0]}, new int[]{colorBlendConfig.blendModes[0]});
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public boolean isLightTheme() {
                    Integer colorFromDrawable;
                    if (NestedHeaderLayout.this.mMaskBackground == null || (colorFromDrawable = MiuixUIUtils.getColorFromDrawable(NestedHeaderLayout.this.mMaskBackground)) == null) {
                        return !this.isDarkThemeOverlay && AttributeResolver.resolveBoolean(NestedHeaderLayout.this.getContext(), android.R.attr.isLightTheme, true);
                    }
                    return MiuixUIUtils.isLightColor(colorFromDrawable.intValue()) && !this.isDarkThemeOverlay;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurApplyStateChanged(boolean z) {
                    if (z) {
                        NestedHeaderLayout.this.mOverBgView.setBackground(NestedHeaderLayout.this.mMaskBackgroundInBlur);
                    } else {
                        NestedHeaderLayout.this.mOverBgView.setBackground(NestedHeaderLayout.this.mMaskBackground);
                    }
                    if (NestedHeaderLayout.this.mNestedHeaderChangedListener != null) {
                        NestedHeaderLayout.this.mNestedHeaderChangedListener.onOverViewBlurStateChanged(z);
                    }
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurEnableStateChanged(boolean z) {
                    if (z) {
                        NestedHeaderLayout.this.mMaskBackgroundInBlur = new ColorDrawable(0);
                    }
                }
            });
            boolean z = DeviceUtils.isMiuiLiteV2() || DeviceUtils.isLiteV1StockPlus() || DeviceUtils.isMiuiMiddle();
            this.mIsCommonLiteStrategy = z;
            if (z) {
                this.mIsOverlayMode = false;
            } else {
                setSupportBlur(true);
                setEnableBlur(true);
            }
        } else {
            this.mBlurUiHelper = null;
            this.mIsOverlayMode = false;
        }
        Boolean bool = this.mUserSetOverlayMode;
        if (bool != null) {
            this.mIsOverlayMode = bool.booleanValue();
        }
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        View childAt;
        super.onMeasure(i, i2);
        View view = this.mHeaderView;
        if (!(((view instanceof ViewGroup) && (view instanceof ScrollingView)) || (view instanceof ScrollView)) || (childAt = ((ViewGroup) view).getChildAt(0)) == null || childAt.getMeasuredHeight() <= this.mHeaderView.getMeasuredHeight()) {
            return;
        }
        this.mHeaderView.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0));
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!this.mSaveStickyMaskEnabled) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        if (!(parcelable instanceof SavedState)) {
            Log.w("NestedHeaderLayout", "Wrong state class, expecting SavedState! This usually happens when two views of different type have the same id in the same hierarchy.");
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setStickyMaskEnabled(savedState.stickyMaskEnabled);
        if (savedState.stickyMaskEnabled) {
            scheduleRestoreStickyMaskOverBgState(savedState.scrollingProgress);
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        if (!this.mSaveStickyMaskEnabled) {
            return parcelableOnSaveInstanceState;
        }
        SavedState savedState = new SavedState(parcelableOnSaveInstanceState);
        savedState.stickyMaskEnabled = isStickyMaskEnabled();
        savedState.scrollingProgress = getScrollingProgress();
        return savedState;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public void onScrollingProgressUpdated(int i) {
        int iMax;
        float f;
        int i2;
        int i3;
        int iMax2;
        ?? r15;
        int i4;
        int i5;
        float f2;
        float f3;
        float f4;
        float f5;
        super.onScrollingProgressUpdated(i);
        int paddingTop = !getClipToPadding() ? getPaddingTop() : 0;
        int i6 = this.mEnableOverScrollTo ? this.mOverScrollingTo : 0;
        View view = this.mHeaderView;
        Object[] objArr = (view == null || view.getVisibility() == 8) ? false : true;
        View view2 = this.mStickyView;
        Object[] objArr2 = (view2 == null || view2.getVisibility() == 8) ? false : true;
        View view3 = this.mTriggerView;
        Object[] objArr3 = (view3 == null || view3.getVisibility() == 8) ? false : true;
        int i7 = paddingTop + this.mContentInsetTop;
        int i8 = objArr != false ? this.mHeaderMeasuredHeight + this.mHeaderTopMargin + this.mHeaderBottomMargin : 0;
        if (objArr2 == true) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mStickyView.getLayoutParams();
            iMax = this.mStickyView.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
        } else {
            iMax = 0;
        }
        if (objArr3 == true) {
            int i9 = this.mTriggerTopMargin + this.mTriggerBottomMargin + this.mTriggerMeasuredHeight;
            int iMax3 = i - Math.max(0, Math.min(getScrollingTo(), i));
            int iMax4 = Math.max(getScrollingFrom(), Math.min(getScrollingTo(), i));
            int i10 = i7 + i6 + i8 + this.mTriggerTopMargin;
            if (this.mIsStickyBeyondTrigger) {
                i10 += iMax;
            }
            int i11 = i10;
            View view4 = this.mTriggerContentView;
            if (view4 == null) {
                view4 = this.mTriggerView;
            }
            View view5 = view4;
            f = 1.0f;
            relayoutContent(this.mTriggerView, view5, i11, iMax4 - i9, false);
            if (this.mTriggerContentView == null) {
                f4 = iMax4 - this.mTriggerBottomMargin;
                f5 = this.mTriggerContentMinHeight;
            } else {
                f4 = iMax4 - this.mTriggerContentBottomMargin;
                f5 = this.mTriggerContentMinHeight;
            }
            float f6 = f4 / f5;
            float fMax = Math.max(0.0f, Math.min(1.0f, f6));
            if (this.mAcceptTriggerRootViewAlpha) {
                this.mTriggerView.setAlpha(fMax);
            } else {
                View view6 = this.mTriggerView;
                if ((view6 instanceof ViewGroup) && ((ViewGroup) view6).getChildCount() > 0) {
                    for (int i12 = 0; i12 < ((ViewGroup) this.mTriggerView).getChildCount(); i12++) {
                        ((ViewGroup) this.mTriggerView).getChildAt(i12).setAlpha(fMax);
                    }
                }
            }
            applyContentAlpha(makeTriggerContentViewList(view5), f6 - 1.0f);
            i2 = iMax3;
        } else {
            f = 1.0f;
            i2 = i;
        }
        if (objArr == true) {
            i3 = i7 + i8;
            View view7 = this.mHeaderContentView;
            if (view7 == null) {
                view7 = this.mHeaderView;
            }
            View view8 = view7;
            if (getScrollType() == 1) {
                int i13 = -this.mHeaderView.getTop();
                int iMax5 = Math.max(-i8, i7 + i6 + i2);
                this.mHeaderView.offsetTopAndBottom(i13 + iMax5);
                int iMax6 = (this.mHeaderTopMargin + this.mHeaderMeasuredHeight) - Math.max(0, this.mContentInsetTop - iMax5);
                if (this.mHeaderViewClipEnabled) {
                    Rect clipBounds = this.mHeaderView.getClipBounds();
                    if (clipBounds == null) {
                        clipBounds = new Rect();
                    }
                    clipBounds.set(0, this.mHeaderMeasuredHeight - iMax6, this.mHeaderView.getMeasuredWidth(), this.mHeaderMeasuredHeight);
                    this.mHeaderView.setClipBounds(clipBounds);
                } else {
                    this.mHeaderView.setClipBounds(null);
                }
                this.mHeaderVisibleHeight = iMax6 + this.mHeaderTopMargin + this.mHeaderBottomMargin;
            } else {
                relayoutContent(this.mHeaderView, view8, this.mHeaderTopMargin + i7 + i6, i2, false);
                if (this.mHeaderContentView == null) {
                    f2 = i2 - this.mHeaderBottomMargin;
                    f3 = this.mHeaderContentMinHeight;
                } else {
                    f2 = i2 - this.mHeaderContentBottomMargin;
                    f3 = this.mHeaderContentMinHeight;
                }
                float f7 = (f2 + f3) / f3;
                float fMax2 = Math.max(0.0f, Math.min(f, f7 + f));
                if (this.mAcceptHeaderRootViewAlpha) {
                    this.mHeaderView.setAlpha(fMax2);
                } else {
                    View view9 = this.mHeaderView;
                    if ((view9 instanceof ViewGroup) && ((ViewGroup) view9).getChildCount() > 0) {
                        for (int i14 = 0; i14 < ((ViewGroup) this.mHeaderView).getChildCount(); i14++) {
                            ((ViewGroup) this.mHeaderView).getChildAt(i14).setAlpha(fMax2);
                        }
                    }
                }
                applyContentAlpha(makeHeaderContentViewList(view8), f7);
                this.mHeaderVisibleHeight = this.mHeaderView.getHeight() + this.mHeaderTopMargin + this.mHeaderBottomMargin;
            }
        } else {
            i3 = i7;
        }
        int i15 = i8 + i7 + i6;
        if (objArr2 == true) {
            i3 += iMax;
            int i16 = -this.mStickyView.getTop();
            iMax2 = this.mIsStickyBeyondTrigger ? Math.max(i7, i2 + i15) : Math.max(i7, i + i15);
            this.mStickyView.offsetTopAndBottom(i16 + iMax2);
        } else {
            iMax2 = this.mIsStickyBeyondTrigger ? Math.max(i7, i2 + i15) : Math.max(i7, i + i15);
        }
        int i17 = iMax2 + iMax;
        if (objArr2 != false) {
            if (this.mStickyView.getVisibility() == 4) {
                i17 = iMax2;
                iMax = 0;
            } else if (this.mInSearchMode && (i5 = this.mCurrentCoordinatorHeightGap) < 0) {
                iMax = Math.max(0, iMax + i5);
                i17 = iMax2 + iMax;
            }
        }
        int i18 = iMax2 + iMax;
        int iMax7 = i + i3;
        if (!this.mIsOverlayMode) {
            iMax7 = this.mInSearchMode ? Math.max(iMax7, i18) : Math.min(iMax7, i18);
        }
        this.mScrollableView.offsetTopAndBottom((-this.mScrollableView.getTop()) + iMax7);
        int i19 = this.mLastScrollingProgress;
        if (i - i19 > 0) {
            checkSendHeaderChangeListener(i19, i, true);
            r15 = 0;
        } else {
            r15 = 0;
            r15 = 0;
            if (i - i19 < 0) {
                checkSendHeaderChangeListener(i19, i, false);
            }
        }
        this.mLastScrollingProgress = i;
        updateHeaderOpen(isHeaderOpen());
        NestedHeaderOverlayMaskView nestedHeaderOverlayMaskView = this.mOverBgView;
        if (nestedHeaderOverlayMaskView != 0) {
            if (this.mIsMaskBitmapFromWindowBg) {
                nestedHeaderOverlayMaskView.setClickable(r15);
                if (this.mRootView != null) {
                    NestedHeaderOverlayMaskView nestedHeaderOverlayMaskView2 = this.mOverBgView;
                    i4 = 0;
                    nestedHeaderOverlayMaskView2.layout(nestedHeaderOverlayMaskView2.getLeft(), 0, this.mOverBgView.getLeft() + this.mRootView.getWidth(), this.mRootView.getHeight());
                } else {
                    i4 = 0;
                }
                this.mClipOverBgBounds.set(i4, i4, this.mOverBgView.getWidth(), i17);
                this.mOverBgView.setClipBounds(this.mClipOverBgBounds);
            } else {
                nestedHeaderOverlayMaskView.setClickable(true);
                NestedHeaderOverlayMaskView nestedHeaderOverlayMaskView3 = this.mOverBgView;
                nestedHeaderOverlayMaskView3.layout(nestedHeaderOverlayMaskView3.getLeft(), r15, this.mOverBgView.getRight(), i17);
            }
            updateOverBgState(i, this.mHeaderVisibleHeight);
        }
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public void onUpdateScrollingRangeOnLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onUpdateScrollingRangeOnLayout(z, i, i2, i3, i4);
        updateScrollingRange(true, false, false, false);
    }

    public final void scheduleRestoreStickyMaskOverBgState(final int i) {
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: miuix.nestedheader.widget.NestedHeaderLayout.5
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ViewTreeObserver viewTreeObserver = NestedHeaderLayout.this.getViewTreeObserver();
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnPreDrawListener(this);
                }
                if (!NestedHeaderLayout.this.isStickyMaskEnabled()) {
                    return true;
                }
                int iMax = Math.max(NestedHeaderLayout.this.getScrollingFrom(), Math.min(NestedHeaderLayout.this.getScrollingTo(), i));
                NestedHeaderLayout.this.updateScrollingProgress(iMax);
                NestedHeaderLayout.this.onScrollingProgressUpdated(iMax);
                if (NestedHeaderLayout.this.mOverBgView != null) {
                    NestedHeaderLayout.this.mOverBgView.invalidate();
                }
                return true;
            }
        });
    }

    public void setAcceptTriggerRootViewAlpha(boolean z) {
        this.mAcceptTriggerRootViewAlpha = z;
    }

    public void setAdsorptionToNoOverlay(boolean z) {
        this.mAdsorptionToNoOverlay = z;
    }

    public void setAutoAllClose(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        if (this.mIsSelfScrollFirst) {
            startNestedScroll(2, 1);
            nestedHeaderLayout = this;
            nestedHeaderLayout.dispatchNestedPreScroll(0, this.mCoordinatorHeightTotalGap, new int[2], new int[2], 1);
            nestedHeaderLayout.stopNestedScroll(1);
        } else {
            nestedHeaderLayout = this;
        }
        if (nestedHeaderLayout.getScrollingProgress() > nestedHeaderLayout.getHeaderCloseProgress()) {
            if (z) {
                nestedHeaderLayout.autoAdsorption(nestedHeaderLayout.getHeaderCloseProgress());
            } else {
                nestedHeaderLayout.syncScrollingProgress(nestedHeaderLayout.getHeaderCloseProgress());
            }
        }
    }

    public void setAutoAllOpen(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        if (this.mIsSelfScrollFirst) {
            startNestedScroll(2, 1);
            nestedHeaderLayout = this;
            nestedHeaderLayout.dispatchNestedScroll(0, 0, 0, -this.mCoordinatorHeightTotalGap, this.mParentOffsetInWindow, 1);
            nestedHeaderLayout.stopNestedScroll(1);
        } else {
            nestedHeaderLayout = this;
        }
        if (nestedHeaderLayout.getScrollingProgress() < nestedHeaderLayout.getScrollingTo()) {
            if (z) {
                nestedHeaderLayout.autoAdsorption(nestedHeaderLayout.getScrollingTo());
            } else {
                nestedHeaderLayout.syncScrollingProgress(nestedHeaderLayout.getScrollingTo());
            }
        }
    }

    public void setAutoAnim(boolean z) {
        this.mAutoAnim = z;
    }

    public void setAutoHeaderClose(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        if (this.mIsSelfScrollFirst) {
            startNestedScroll(2, 1);
            nestedHeaderLayout = this;
            nestedHeaderLayout.dispatchNestedPreScroll(0, this.mCoordinatorHeightTotalGap, new int[2], new int[2], 1);
            nestedHeaderLayout.stopNestedScroll(1);
        } else {
            nestedHeaderLayout = this;
        }
        if (!nestedHeaderLayout.getHeaderViewVisible() || nestedHeaderLayout.getScrollingProgress() <= nestedHeaderLayout.getScrollingFrom()) {
            return;
        }
        if (z) {
            nestedHeaderLayout.autoAdsorption(nestedHeaderLayout.getHeaderCloseProgress());
        } else if (nestedHeaderLayout.getHeaderViewVisible()) {
            nestedHeaderLayout.syncScrollingProgress(nestedHeaderLayout.getHeaderCloseProgress());
        }
    }

    public void setAutoHeaderOpen(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        if (this.mIsSelfScrollFirst) {
            startNestedScroll(2, 1);
            nestedHeaderLayout = this;
            nestedHeaderLayout.dispatchNestedScroll(0, 0, 0, -this.mCoordinatorHeightTotalGap, this.mParentOffsetInWindow, 1);
            nestedHeaderLayout.stopNestedScroll(1);
        } else {
            nestedHeaderLayout = this;
        }
        if (!nestedHeaderLayout.getHeaderViewVisible() || nestedHeaderLayout.getScrollingProgress() >= 0) {
            return;
        }
        if (z) {
            nestedHeaderLayout.autoAdsorption(nestedHeaderLayout.getHeaderProgressTo());
        } else {
            nestedHeaderLayout.syncScrollingProgress(nestedHeaderLayout.getHeaderProgressTo());
        }
    }

    public void setAutoTriggerClose(boolean z) {
        int scrollingFrom;
        if (getTriggerViewVisible() && getHeaderViewVisible() && getScrollingProgress() > 0) {
            scrollingFrom = 0;
        } else {
            scrollingFrom = (!getTriggerViewVisible() || getHeaderViewVisible() || getScrollingProgress() <= getScrollingFrom()) ? -1 : getScrollingFrom();
        }
        if (scrollingFrom != -1 && z) {
            autoAdsorption(scrollingFrom);
        } else if (scrollingFrom != -1) {
            syncScrollingProgress(scrollingFrom);
        }
    }

    public void setAutoTriggerOpen(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        if (!this.mIsSelfScrollFirst || isHeaderOpen()) {
            nestedHeaderLayout = this;
        } else {
            startNestedScroll(2, 1);
            nestedHeaderLayout = this;
            nestedHeaderLayout.dispatchNestedScroll(0, 0, 0, -this.mCoordinatorHeightTotalGap, this.mParentOffsetInWindow, 1);
            nestedHeaderLayout.stopNestedScroll(1);
        }
        if (!nestedHeaderLayout.getTriggerViewVisible() || nestedHeaderLayout.getScrollingProgress() >= nestedHeaderLayout.getScrollingTo()) {
            return;
        }
        if (z) {
            nestedHeaderLayout.autoAdsorption(nestedHeaderLayout.getScrollingTo());
        } else {
            nestedHeaderLayout.syncScrollingProgress(nestedHeaderLayout.getScrollingTo());
        }
    }

    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setEnableBlur(z);
        }
    }

    public void setHeaderAutoCloseEnable(boolean z) {
        this.mEnableHeaderAutoClose = z;
    }

    public void setHeaderRootViewAcceptAlpha(boolean z) {
        this.mAcceptHeaderRootViewAlpha = z;
    }

    public void setHeaderViewClipEnabled(boolean z) {
        View view;
        if (this.mHeaderViewClipEnabled != z) {
            this.mHeaderViewClipEnabled = z;
            if (!z && (view = this.mHeaderView) != null) {
                view.setClipBounds(null);
            }
            requestLayout();
        }
    }

    public void setHeaderViewVisible(boolean z) {
        View view = this.mHeaderView;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
            updateScrollingRange(false, false, false, z);
        }
    }

    public void setInSearchMode(boolean z) {
        this.mInSearchMode = z;
        if (z) {
            this.mCoordinatorHeightGapInSearchMode = getNestedScrollableValue();
        } else {
            this.mCoordinatorHeightGapInSearchMode = 0;
        }
        updateScrollingRange(false, false, false, false);
        requestLayout();
    }

    public void setMaskColor(int i) {
        this.mOverBgView.setMaskColor(i);
    }

    public void setMaskExtraHeight(float f) {
        this.mOverBgView.setMaskExtraHeight(f);
    }

    @Override // miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialConfig materialConfig) {
        super.setMaterial(materialConfig);
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        boolean z = this.mMaterial == null && materialDayNightConfig != null;
        if (materialDayNightConfig == null) {
            this.mMaterial = null;
            applyBlur(false);
            return;
        }
        this.mMaterial = materialDayNightConfig;
        if (this.mBlurUiHelper != null) {
            if (!isApplyBlur() && z) {
                applyBlur(true);
            }
            this.mBlurUiHelper.onConfigChanged();
        }
    }

    public void setNestedHeaderChangedListener(NestedHeaderChangedListener nestedHeaderChangedListener) {
        this.mNestedHeaderChangedListener = nestedHeaderChangedListener;
    }

    public void setOverlayMode(boolean z) {
        this.mUserSetOverlayMode = Boolean.valueOf(z);
        this.mIsOverlayMode = z;
    }

    public void setSaveStickyMaskEnabled(boolean z) {
        this.mSaveStickyMaskEnabled = z;
    }

    public void setScrollableViewClipEnabled(boolean z) {
        View view;
        if (this.mScrollableViewClipEnabled != z) {
            this.mScrollableViewClipEnabled = z;
            if (!z && (view = this.mScrollableView) != null) {
                view.setClipBounds(null);
            }
            requestLayout();
        }
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout
    public void setSelfScrollFirst(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        boolean z2 = this.mIsSelfScrollFirst;
        if (z2 == z || !z2 || isHeaderOpen()) {
            nestedHeaderLayout = this;
        } else {
            startNestedScroll(2, 1);
            nestedHeaderLayout = this;
            nestedHeaderLayout.dispatchNestedScroll(0, 0, 0, -this.mCoordinatorHeightTotalGap, this.mParentOffsetInWindow, 1);
            nestedHeaderLayout.stopNestedScroll(1);
            nestedHeaderLayout.syncScrollingProgress(0);
        }
        super.setSelfScrollFirst(z);
    }

    public void setStickyMaskEnabled(boolean z) {
        if (z && isEnableBlur()) {
            setEnableBlur(false);
        }
        this.mOverBgView.setStickyMaskEnabled(z);
    }

    public void setStickyViewVisible(boolean z) {
        View view = this.mStickyView;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
            updateScrollingRange(false, false, z, false);
        }
    }

    public void setSupportBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.setSupportBlur(z);
        }
    }

    public void setTriggerViewVisible(boolean z) {
        View view = this.mTriggerView;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
            updateScrollingRange(false, z, false, false);
        }
    }

    public final void syncOverScrollTo(int i) {
        this.mOverScrollingTo = i;
        updateScrollingProgress(getScrollingProgress());
        onScrollingProgressUpdated(getScrollingProgress());
    }

    @Override // miuix.nestedheader.widget.NestedScrollingLayout, miuix.core.view.NestedCoordinatorObserver
    public void updateCoordinatorHeightGapInfo(int i, int i2) {
        super.updateCoordinatorHeightGapInfo(i, i2);
        if (this.mInSearchMode) {
            updateScrollingProgressImmediately(Math.min(i, 0));
        } else {
            updateOverBgState(getScrollingProgress(), this.mHeaderVisibleHeight);
        }
    }

    public final void updateOverBgState(int i, int i2) {
        if (this.mOverBgView != null) {
            int i3 = this.mHeaderTotalHeight;
            if (!this.mInSearchMode) {
                i2 = i3;
            } else if (this.mCurrentCoordinatorHeightGap > this.mCoordinatorHeightGapInSearchMode || (i = i - getStickyScrollToOnNested()) > 0) {
                i = 0;
            }
            if (!this.mIsOverlayMode) {
                if (getTop() <= 0 && i < (-i2) && !this.mIsShowOverBg) {
                    this.mIsShowOverBg = true;
                    this.mOverBgView.setVisibility(0);
                } else if ((getTop() > 0 || i >= (-i2)) && this.mIsShowOverBg) {
                    this.mIsShowOverBg = false;
                    this.mOverBgView.setVisibility(4);
                }
                if (!this.mScrollableViewClipEnabled) {
                    this.mScrollableView.setClipBounds(null);
                    return;
                }
                Rect clipBounds = this.mScrollableView.getClipBounds();
                if (clipBounds == null) {
                    clipBounds = new Rect();
                }
                clipBounds.set(0, 0, this.mScrollableView.getWidth(), this.mScrollableView.getHeight());
                this.mScrollableView.setClipBounds(clipBounds);
                return;
            }
            if (getTop() <= 0 && i < (-i2) && !this.mIsShowOverBg) {
                this.mIsShowOverBg = true;
                this.mOverBgView.setVisibility(0);
                applyBlur(true);
            } else if ((getTop() > 0 || i >= (-i2)) && this.mIsShowOverBg) {
                this.mIsShowOverBg = false;
                this.mOverBgView.setVisibility(4);
                applyBlur(false);
            }
            if (this.mOverBgView.getVisibility() == 0) {
                this.mScrollableView.setClipBounds(null);
                return;
            }
            if (!this.mScrollableViewClipEnabled) {
                this.mScrollableView.setClipBounds(null);
                return;
            }
            int height = this.mOverBgView.getHeight();
            if (this.mIsMaskBitmapFromWindowBg && this.mOverBgView.getClipBounds() != null) {
                height = this.mOverBgView.getClipBounds().height();
            }
            Rect clipBounds2 = this.mScrollableView.getClipBounds();
            if (clipBounds2 == null) {
                clipBounds2 = new Rect();
            }
            clipBounds2.set(0, height - this.mScrollableView.getTop(), this.mScrollableView.getWidth(), this.mScrollableView.getHeight());
            this.mScrollableView.setClipBounds(clipBounds2);
        }
    }

    public void updateScrollingProgressImmediately(int i) {
        updateScrollingProgress(i);
        onScrollingProgressUpdated(i);
    }

    public final void updateScrollingRange(boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        boolean z6;
        int i = 0;
        int i2 = this.mIsOverlayMode ? -(this.mContentInsetTop + (getClipToPadding() ? 0 : getPaddingTop())) : 0;
        this.mHeaderTotalHeight = 0;
        View view = this.mHeaderView;
        boolean z7 = true;
        if (view == null || view.getVisibility() == 8) {
            z5 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mHeaderView.getLayoutParams();
            this.mHeaderBottomMargin = marginLayoutParams.bottomMargin;
            this.mHeaderTopMargin = marginLayoutParams.topMargin;
            int measuredHeight = this.mHeaderView.getMeasuredHeight();
            this.mHeaderMeasuredHeight = measuredHeight;
            this.mHeaderTotalHeight = measuredHeight + this.mHeaderTopMargin + this.mHeaderBottomMargin;
            View view2 = this.mHeaderContentView;
            if (view2 != null) {
                this.mHeaderContentBottomMargin = ((ViewGroup.MarginLayoutParams) view2.getLayoutParams()).bottomMargin;
            }
            i2 += (int) ((-this.mHeaderTotalHeight) + this.mRangeOffset);
            z5 = true;
        }
        this.mStickyTotalHeight = 0;
        View view3 = this.mStickyView;
        if (view3 == null || view3.getVisibility() == 8) {
            z6 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mStickyView.getLayoutParams();
            int measuredHeight2 = this.mStickyView.getMeasuredHeight() + marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin;
            this.mStickyTotalHeight = measuredHeight2;
            if (this.mIsOverlayMode) {
                i2 += -measuredHeight2;
            }
            z6 = true;
        }
        View view4 = this.mTriggerView;
        if (view4 == null || view4.getVisibility() == 8) {
            z7 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.mTriggerView.getLayoutParams();
            this.mTriggerBottomMargin = marginLayoutParams3.bottomMargin;
            this.mTriggerTopMargin = marginLayoutParams3.topMargin;
            this.mTriggerMeasuredHeight = this.mTriggerView.getMeasuredHeight();
            View view5 = this.mTriggerContentView;
            if (view5 != null) {
                this.mTriggerContentBottomMargin = ((ViewGroup.MarginLayoutParams) view5.getLayoutParams()).bottomMargin;
            }
            i = this.mTriggerMeasuredHeight + this.mTriggerTopMargin + this.mTriggerBottomMargin;
        }
        if (this.mInSearchMode) {
            i = -this.mHeaderTotalHeight;
            if (z6 && this.mStickyView.getVisibility() == 4) {
                i -= this.mStickyTotalHeight;
            }
        }
        setScrollingRange(i2, i, z5, z7, z6, z, z2, z3, z4);
    }
}

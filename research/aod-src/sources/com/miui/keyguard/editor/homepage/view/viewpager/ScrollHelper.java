package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ScrollHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ScrollHelper {
    public static final Companion Companion = new Companion(null);
    public static boolean inScroll;
    public OrientationHelper mHorizontalHelper;
    public RecyclerView.OnFlingListener mOnFlingListener;
    public ViewPager2.OnPageChangeCallback mOnPageChangedListener;
    public ViewPager2.OnPreFlingListener mOnPreFlingListener;
    public RecyclerView.OnScrollListener mOnScrollListener;
    public int mTouchSlop;
    public OrientationHelper mVerticalHelper;
    public ScrollCallback scrollCallback;
    public int scrollDirection;
    public int totalOffset;
    public ViewPager2 viewPager2;
    public int orientation = -1;
    public int startDragPositionOffsetPixels = -1;
    public int startDragClosestChildPosition = -1;
    public int settlePositionOffsetPixels = -1;

    /* JADX INFO: compiled from: ScrollHelper.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void setInScroll(boolean z) {
            ScrollHelper.inScroll = z;
        }
    }

    public static final void onViewPager2Attached$lambda$0(ScrollHelper scrollHelper, int i, int i2) {
        scrollHelper.settlePositionOffsetPixels = scrollHelper.totalOffset;
    }

    public final void attachToViewPager2(ViewPager2 viewPager2) {
        Intrinsics.checkNotNullParameter(viewPager2, "viewPager2");
        this.viewPager2 = viewPager2;
        this.orientation = viewPager2.getOrientation();
        onViewPager2Attached();
    }

    public final int findCenterViewIndex(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return -1;
        }
        int startAfterPadding = orientationHelper.getStartAfterPadding() + (orientationHelper.getTotalSpace() / 2);
        View view = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = layoutManager.getChildAt(i2);
            int iAbs = Math.abs((orientationHelper.getDecoratedStart(childAt) + (orientationHelper.getDecoratedMeasurement(childAt) / 2)) - startAfterPadding);
            if (iAbs < i) {
                view = childAt;
                i = iAbs;
            }
        }
        if (view != null) {
            return layoutManager.getPosition(view);
        }
        return -1;
    }

    public final int findClosestChildViewIndex() {
        ViewPager2.RecyclerViewImpl recyclerViewImpl;
        RecyclerView.LayoutManager layoutManager;
        try {
            ViewPager2 viewPager2 = this.viewPager2;
            if (viewPager2 == null || (recyclerViewImpl = viewPager2.getRecyclerViewImpl()) == null || (layoutManager = recyclerViewImpl.getLayoutManager()) == null) {
                throw new RuntimeException("layoutManager not found");
            }
            OrientationHelper orientationHelper = getOrientationHelper(layoutManager);
            if (orientationHelper != null) {
                return findCenterViewIndex(layoutManager, orientationHelper);
            }
            throw new RuntimeException("orientationHelper = null");
        } catch (Exception e) {
            Log.e("Keyguard-Editor-VerticalScrollHelper", "findClosestChildViewIndex", e);
            return -1;
        }
    }

    public final int getDragDistance() {
        Log.i("Keyguard-Editor-VerticalScrollHelper", "settlePixels = " + this.settlePositionOffsetPixels + ", dragPixels = " + this.startDragPositionOffsetPixels);
        return this.settlePositionOffsetPixels - this.startDragPositionOffsetPixels;
    }

    public final int getDragDistanceValue() {
        return Math.abs(getDragDistance());
    }

    /* JADX WARN: Code duplicated, block: B:8:0x000e  */
    public final OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mHorizontalHelper;
        if (orientationHelper == null) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        } else {
            if ((orientationHelper != null ? orientationHelper.getLayoutManager() : null) != layoutManager) {
                this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
            }
        }
        return this.mHorizontalHelper;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final OrientationHelper getOrientationHelper(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return getVerticalHelper(layoutManager);
        }
        if (layoutManager.canScrollHorizontally()) {
            return getHorizontalHelper(layoutManager);
        }
        return null;
    }

    public final int getScrollDirection() {
        return this.scrollDirection;
    }

    public final int getStartDragClosestChildPosition() {
        return this.startDragClosestChildPosition;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x000e  */
    public final OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mVerticalHelper;
        if (orientationHelper == null) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        } else {
            if ((orientationHelper != null ? orientationHelper.getLayoutManager() : null) != layoutManager) {
                this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
            }
        }
        return this.mVerticalHelper;
    }

    public final boolean isOverlayScrollVertically() {
        return getDragDistanceValue() > this.mTouchSlop;
    }

    public final void onViewPager2Attached() {
        Context context;
        ViewPager2 viewPager2;
        ViewPager2 viewPager3 = this.viewPager2;
        ViewPager2.RecyclerViewImpl recyclerViewImpl = viewPager3 != null ? viewPager3.getRecyclerViewImpl() : null;
        RecyclerView.OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null && recyclerViewImpl != null) {
            Intrinsics.checkNotNull(onScrollListener);
            recyclerViewImpl.removeOnScrollListener(onScrollListener);
        }
        RecyclerView.OnScrollListener onScrollListener2 = new RecyclerView.OnScrollListener() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper.onViewPager2Attached.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
                super.onScrolled(recyclerView, i, i2);
                int orientation = ScrollHelper.this.getOrientation();
                int i3 = 1;
                if (orientation != 0 && orientation != 1) {
                    Log.e("Keyguard-Editor-VerticalScrollHelper", "onViewPager2Attached#onScrolled skipped: invalid orientation: " + orientation);
                    return;
                }
                ScrollHelper.this.totalOffset += orientation == 1 ? i2 : i;
                ScrollHelper scrollHelper = ScrollHelper.this;
                if (scrollHelper.totalOffset - ScrollHelper.this.startDragPositionOffsetPixels <= 0) {
                    i3 = ScrollHelper.this.totalOffset - ScrollHelper.this.startDragPositionOffsetPixels < 0 ? 2 : 0;
                }
                scrollHelper.scrollDirection = i3;
                ScrollCallback scrollCallback = ScrollHelper.this.scrollCallback;
                if (scrollCallback != null) {
                    scrollCallback.onPageScrolled(i, i2, ScrollHelper.this.totalOffset);
                }
            }
        };
        this.mOnScrollListener = onScrollListener2;
        if (recyclerViewImpl != null) {
            Intrinsics.checkNotNull(onScrollListener2);
            recyclerViewImpl.addOnScrollListener(onScrollListener2);
        }
        ViewPager2.OnPreFlingListener onPreFlingListener = this.mOnPreFlingListener;
        if (onPreFlingListener != null && recyclerViewImpl != null) {
            recyclerViewImpl.removePreFlingListener(onPreFlingListener);
        }
        ViewPager2.OnPreFlingListener onPreFlingListener2 = new ViewPager2.OnPreFlingListener() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper$$ExternalSyntheticLambda0
            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPreFlingListener
            public final void onPreFling(int i, int i2) {
                ScrollHelper.onViewPager2Attached$lambda$0(this.f$0, i, i2);
            }
        };
        this.mOnPreFlingListener = onPreFlingListener2;
        if (recyclerViewImpl != null) {
            recyclerViewImpl.addPreFlingListener(onPreFlingListener2);
        }
        RecyclerView.OnFlingListener onFlingListener = this.mOnFlingListener;
        if (onFlingListener != null && recyclerViewImpl != null) {
            recyclerViewImpl.removeFlingListener(onFlingListener);
        }
        RecyclerView.OnFlingListener onFlingListener2 = new RecyclerView.OnFlingListener() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper.onViewPager2Attached.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnFlingListener
            public boolean onFling(int i, int i2) {
                ScrollCallback scrollCallback = ScrollHelper.this.scrollCallback;
                if (scrollCallback == null) {
                    return false;
                }
                scrollCallback.onFling(i, i2);
                return false;
            }
        };
        this.mOnFlingListener = onFlingListener2;
        if (recyclerViewImpl != null) {
            recyclerViewImpl.addFlingListener(onFlingListener2);
        }
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mOnPageChangedListener;
        if (onPageChangeCallback != null && (viewPager2 = this.viewPager2) != null) {
            Intrinsics.checkNotNull(onPageChangeCallback);
            viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback);
        }
        ViewPager2.OnPageChangeCallback onPageChangeCallback2 = new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper.onViewPager2Attached.4
            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i) {
                super.onPageScrollStateChanged(i);
                if (i == 0) {
                    ScrollHelper.this.totalOffset = 0;
                    ScrollHelper.this.startDragPositionOffsetPixels = 0;
                    ScrollHelper.this.scrollDirection = 0;
                    ScrollHelper.this.settlePositionOffsetPixels = 0;
                    ScrollHelper.this.startDragClosestChildPosition = -1;
                } else if (i == 1) {
                    ScrollHelper.this.totalOffset = 0;
                    ScrollHelper scrollHelper = ScrollHelper.this;
                    scrollHelper.startDragPositionOffsetPixels = scrollHelper.totalOffset;
                    ScrollHelper scrollHelper2 = ScrollHelper.this;
                    scrollHelper2.startDragClosestChildPosition = scrollHelper2.findClosestChildViewIndex();
                    Log.i("Keyguard-Editor-VerticalScrollHelper", "start drag ~ closest child index is " + ScrollHelper.this.getStartDragClosestChildPosition());
                }
                ScrollCallback scrollCallback = ScrollHelper.this.scrollCallback;
                if (scrollCallback != null) {
                    scrollCallback.onPageScrollStateChanged(i);
                }
            }

            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                super.onPageSelected(i);
                ScrollCallback scrollCallback = ScrollHelper.this.scrollCallback;
                if (scrollCallback != null) {
                    scrollCallback.onPageSelectChanged(i);
                }
            }
        };
        this.mOnPageChangedListener = onPageChangeCallback2;
        ViewPager2 viewPager4 = this.viewPager2;
        if (viewPager4 != null) {
            Intrinsics.checkNotNull(onPageChangeCallback2);
            viewPager4.registerOnPageChangeCallback(onPageChangeCallback2);
        }
        ViewPager2 viewPager5 = this.viewPager2;
        if (viewPager5 == null || (context = viewPager5.getContext()) == null) {
            return;
        }
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public final int predictSnapTargetPosition(int i, RecyclerView.LayoutManager layoutManager) {
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(layoutManager, "layoutManager");
        boolean zIsOverlayScrollVertically = isOverlayScrollVertically();
        boolean z = getScrollDirection() == 1;
        int itemCount = layoutManager.getItemCount();
        Log.i("Keyguard-Editor-VerticalScrollHelper", "predictSnapTargetPosition -> overlayScrollVertically: " + zIsOverlayScrollVertically + ", forwardDirection: " + z + ", itemCount: " + itemCount);
        if (z && zIsOverlayScrollVertically && (i3 = i + 1) < itemCount) {
            return i3;
        }
        return (z || !zIsOverlayScrollVertically || (i2 = i + (-1)) < 0) ? i : i2;
    }

    public final int predictSnapTargetPosition(RecyclerView.LayoutManager layoutManager) {
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(layoutManager, "layoutManager");
        boolean zIsOverlayScrollVertically = isOverlayScrollVertically();
        boolean z = getScrollDirection() == 1;
        int itemCount = layoutManager.getItemCount();
        int i3 = this.startDragClosestChildPosition;
        Log.i("Keyguard-Editor-VerticalScrollHelper", "predictSnapTargetPosition -> overlayScrollVertically: " + zIsOverlayScrollVertically + ", , forwardDirection: " + z + ",, itemCount: " + itemCount + ", closestIndex: " + i3);
        if (z && zIsOverlayScrollVertically && (i2 = i3 + 1) < itemCount) {
            return i2;
        }
        return (z || !zIsOverlayScrollVertically || (i = i3 + (-1)) < 0) ? i3 : i;
    }

    public final void reset() {
        this.totalOffset = 0;
        this.startDragPositionOffsetPixels = -1;
        this.startDragClosestChildPosition = -1;
        this.settlePositionOffsetPixels = -1;
        this.scrollDirection = 0;
        onViewPager2Attached();
    }

    public final void setPageScrollCallback(ScrollCallback scrollCallback) {
        this.scrollCallback = scrollCallback;
    }
}

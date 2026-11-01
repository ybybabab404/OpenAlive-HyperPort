package com.miui.keyguard.editor.edit.base;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: CombineSpringTouchManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CombineSpringTouchManager {
    public final WeakReference combineEditViewWeak;
    public float density;
    public float initialX;
    public float initialY;
    public boolean isWallpaperScale;
    public int lastOffsetX;
    public int lastOffsetY;
    public int mMaxFlingVelocity;
    public int mMinFlingVelocity;
    public volatile int mScrollState;
    public OverScroller mScroller;
    public VelocityTracker mVelocityTracker;
    public WallpaperScaleChangedListener onWallpaperScaleChangedListener;
    public int overOffsetX;
    public int selectViewPosition;
    public boolean shouldConsumeTouchEventBySelf;
    public final CombineEditSnapHelper snapHelper;
    public boolean startTouch;
    public int touchSlop;

    /* JADX INFO: compiled from: CombineSpringTouchManager.kt */
    @Metadata
    public interface WallpaperScaleChangedListener {
        void onWallpaperScaleChanged(boolean z);
    }

    public CombineSpringTouchManager(WeakReference combineEditViewWeak) {
        Intrinsics.checkNotNullParameter(combineEditViewWeak, "combineEditViewWeak");
        this.combineEditViewWeak = combineEditViewWeak;
        this.density = 1.0f;
        CombineEditView combineEditView = (CombineEditView) combineEditViewWeak.get();
        if (combineEditView != null) {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(combineEditView.getContext());
            this.touchSlop = (int) (((double) viewConfiguration.getScaledTouchSlop()) * 0.5d);
            this.mScroller = new OverScroller(combineEditView.getContext());
            this.mVelocityTracker = VelocityTracker.obtain();
            this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
            this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            this.density = combineEditView.getContext().getResources().getDisplayMetrics().density;
        }
        this.snapHelper = new CombineEditSnapHelper(combineEditViewWeak, this);
    }

    public final float calculateFriction(int i, int i2) {
        if (i2 <= 0) {
            return 0.0f;
        }
        int i3 = i >= 0 ? 1 : -1;
        float fMin = (float) Math.min((Math.abs(i) * ((double) 1.0f)) / ((double) i2), 1.0d);
        float f = fMin * fMin;
        return i3 * ((((f * fMin) / 3) - f) + fMin) * i2;
    }

    public final int calculateScrollableOffset(int i) {
        CombineEditView combineEditView = (CombineEditView) this.combineEditViewWeak.get();
        if (combineEditView == null) {
            return 0;
        }
        return (int) (calculateFriction(i, combineEditView.getScreenSize().y) * 0.4f);
    }

    public final float getDensity() {
        return this.density;
    }

    public final int getHorizontalRealOffset(int i) {
        CombineEditView combineEditView = (CombineEditView) this.combineEditViewWeak.get();
        if (combineEditView != null) {
            return combineEditView.getHorizontalRealOffset(i);
        }
        return 0;
    }

    public final int getMScrollState() {
        return this.mScrollState;
    }

    public final int getSelectViewPosition() {
        return this.selectViewPosition;
    }

    public final CombineEditSnapHelper getSnapHelper() {
        return this.snapHelper;
    }

    public final boolean isEditTouchEvent() {
        return this.shouldConsumeTouchEventBySelf || this.isWallpaperScale;
    }

    public final boolean isInterceptTouchEvent() {
        return this.shouldConsumeTouchEventBySelf;
    }

    public final boolean isValidTouch() {
        return this.startTouch;
    }

    public final boolean isWallpaperScale() {
        return this.isWallpaperScale;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return processTouchEventHorizontal(motionEvent);
    }

    public final void onTouchEventDispatch(MotionEvent motionEvent, boolean z) {
        if (motionEvent == null || ((CombineEditView) this.combineEditViewWeak.get()) == null) {
            return;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.snapHelper.stopFling();
            this.startTouch = true;
            this.initialX = motionEvent.getX();
            this.initialY = motionEvent.getY();
            this.lastOffsetX = 0;
            this.lastOffsetY = 0;
            this.overOffsetX = 0;
            this.shouldConsumeTouchEventBySelf = false;
            setWallpaperScale(false);
            return;
        }
        if (action != 1) {
            if (action == 2) {
                if (this.startTouch) {
                    float x = motionEvent.getX() - this.initialX;
                    float y = motionEvent.getY() - this.initialY;
                    if ((Math.abs(x) <= this.touchSlop && Math.abs(y) <= this.touchSlop) || this.isWallpaperScale || z) {
                        return;
                    }
                    CombineEditView combineEditView = (CombineEditView) this.combineEditViewWeak.get();
                    if (combineEditView != null) {
                        combineEditView.requestDisallowInterceptTouchEvent(false);
                    }
                    updateScrollState(1);
                    this.shouldConsumeTouchEventBySelf = true;
                    return;
                }
                return;
            }
            if (action != 3) {
                if (action == 261 && !this.shouldConsumeTouchEventBySelf) {
                    setWallpaperScale(true);
                    return;
                }
                return;
            }
        }
        this.snapHelper.snapToTargetExistingView();
        if (this.mScrollState != 0) {
            updateScrollState(0);
        }
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0073  */
    /* JADX WARN: Code duplicated, block: B:40:0x0077  */
    /* JADX WARN: Code duplicated, block: B:43:0x007e  */
    /* JADX WARN: Code duplicated, block: B:46:0x0090  */
    /* JADX WARN: Code duplicated, block: B:48:0x0094  */
    /* JADX WARN: Code duplicated, block: B:50:0x009d  */
    /* JADX WARN: Code duplicated, block: B:51:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:65:0x00cf  */
    public final boolean processTouchEventHorizontal(MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        VelocityTracker velocityTracker2;
        CombineEditView combineEditView;
        VelocityTracker velocityTracker3;
        float xVelocity;
        int i;
        if (motionEvent == null || !this.shouldConsumeTouchEventBySelf) {
            return false;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        int action = motionEvent.getAction();
        if (action == 1) {
            velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEventObtain);
            }
            velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
            }
            combineEditView = (CombineEditView) this.combineEditViewWeak.get();
            if (combineEditView == null) {
                motionEventObtain.recycle();
                return true;
            }
            Point screenSize = combineEditView.getScreenSize();
            velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                xVelocity = velocityTracker3.getXVelocity();
            } else {
                xVelocity = 0.0f;
            }
            if (xVelocity != 0.0f || this.snapHelper.getFlingDistance((int) xVelocity) <= screenSize.x / 5) {
                updateScrollState(0);
            } else if (ViewUtil.INSTANCE.isRtl(combineEditView) ? xVelocity >= 0.0f : xVelocity <= 0.0f) {
                snapToSelectTemplate(0);
            } else {
                snapToSelectTemplate(1);
            }
            resetScroll();
        } else {
            if (action == 2) {
                int x = (int) (motionEvent.getX() - this.initialX);
                int i2 = x - this.lastOffsetX;
                int horizontalRealOffset = getHorizontalRealOffset(i2);
                if (i2 != horizontalRealOffset) {
                    if ((horizontalRealOffset ^ i2) <= 0) {
                        horizontalRealOffset = 0;
                    }
                    int i3 = this.overOffsetX;
                    boolean z = i3 == this.lastOffsetX;
                    if (!z) {
                        x = (i3 + i2) - horizontalRealOffset;
                    }
                    int iCalculateScrollableOffset = calculateScrollableOffset(x);
                    i = z ? iCalculateScrollableOffset - this.lastOffsetX : (iCalculateScrollableOffset - this.overOffsetX) + horizontalRealOffset;
                    this.overOffsetX += i - horizontalRealOffset;
                } else {
                    i = x - this.lastOffsetX;
                }
                this.lastOffsetX += i;
                CombineEditView combineEditView2 = (CombineEditView) this.combineEditViewWeak.get();
                if (combineEditView2 != null) {
                    combineEditView2.offsetLeftAndRight(i);
                }
            } else if (action == 3) {
                velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEventObtain);
                }
                velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
                }
                combineEditView = (CombineEditView) this.combineEditViewWeak.get();
                if (combineEditView == null) {
                    motionEventObtain.recycle();
                    return true;
                }
                Point screenSize2 = combineEditView.getScreenSize();
                velocityTracker3 = this.mVelocityTracker;
                if (velocityTracker3 != null) {
                    xVelocity = velocityTracker3.getXVelocity();
                } else {
                    xVelocity = 0.0f;
                }
                if (xVelocity != 0.0f) {
                    updateScrollState(0);
                } else if (ViewUtil.INSTANCE.isRtl(combineEditView)) {
                    snapToSelectTemplate(0);
                } else {
                    snapToSelectTemplate(0);
                }
                resetScroll();
            }
            VelocityTracker velocityTracker4 = this.mVelocityTracker;
            if (velocityTracker4 != null) {
                velocityTracker4.addMovement(motionEventObtain);
            }
        }
        motionEventObtain.recycle();
        return true;
    }

    public final void resetScroll() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
    }

    public final void resetTouchFlow() {
        this.shouldConsumeTouchEventBySelf = false;
        setWallpaperScale(false);
        this.startTouch = false;
    }

    public final void setOnWallpaperScaleChangedListener(WallpaperScaleChangedListener wallpaperScaleChangedListener) {
        this.onWallpaperScaleChangedListener = wallpaperScaleChangedListener;
    }

    public final void setSelectViewPosition(int i) {
        this.selectViewPosition = i;
    }

    public final void setWallpaperScale(boolean z) {
        if (this.isWallpaperScale == z) {
            return;
        }
        this.isWallpaperScale = z;
        WallpaperScaleChangedListener wallpaperScaleChangedListener = this.onWallpaperScaleChangedListener;
        if (wallpaperScaleChangedListener != null) {
            wallpaperScaleChangedListener.onWallpaperScaleChanged(z);
        }
    }

    public final void snapToSelectTemplate(int i) {
        this.snapHelper.snapToSelectView(i);
    }

    public final void updateScrollState(int i) {
        Log.d("CombineSpringTouchManager", "updateScrollState: status=" + i + " mScrollState=" + this.mScrollState);
        if (i != this.mScrollState) {
            if (i != 0) {
                this.mScrollState = i;
                CombineEditView combineEditView = (CombineEditView) this.combineEditViewWeak.get();
                if (combineEditView != null) {
                    combineEditView.onScrollStatusChanged(i);
                    return;
                }
                return;
            }
            if (this.snapHelper.snapToTargetExistingView()) {
                return;
            }
            this.mScrollState = 0;
            EditViewHolder editViewHolderFindSnapViewHolder = this.snapHelper.findSnapViewHolder();
            this.selectViewPosition = editViewHolderFindSnapViewHolder != null ? editViewHolderFindSnapViewHolder.getViewType() : 0;
            CombineEditView combineEditView2 = (CombineEditView) this.combineEditViewWeak.get();
            if (combineEditView2 != null) {
                combineEditView2.updateTemplateRect();
                combineEditView2.onScrollStatusChanged(i);
            }
        }
    }
}

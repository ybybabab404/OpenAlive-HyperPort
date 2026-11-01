package miuix.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Collection;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.IntValueProperty;

/* JADX INFO: loaded from: classes2.dex */
public class HorizontalSnapHelper extends SpringSnapHelper {

    /* JADX INFO: renamed from: miuix.recyclerview.widget.HorizontalSnapHelper$1, reason: invalid class name */
    class AnonymousClass1 extends IntValueProperty {
        public final /* synthetic */ HorizontalSnapHelper this$0;

        @Override // miuix.animation.property.IntValueProperty, miuix.animation.property.IIntValueProperty
        public int getIntValue(Object obj) {
            return this.this$0.mCurrentPosition;
        }

        @Override // miuix.animation.property.IntValueProperty, miuix.animation.property.IIntValueProperty
        public void setIntValue(Object obj, int i) {
            HorizontalSnapHelper horizontalSnapHelper = this.this$0;
            int i2 = horizontalSnapHelper.mMin;
            int i3 = horizontalSnapHelper.mMax;
            horizontalSnapHelper.mCurrentPosition = i;
            if (horizontalSnapHelper.mRecyclerView.getSpringEnabled()) {
                HorizontalSnapHelper horizontalSnapHelper2 = this.this$0;
                int i4 = horizontalSnapHelper2.mCurrentPosition;
                int i5 = horizontalSnapHelper2.mLastPosition;
                int i6 = i4 - i5;
                if (i4 < i2 && i5 > i2) {
                    horizontalSnapHelper2.mRecyclerView.scrollBy(i2 - i5, 0);
                } else if (i4 > i3 && i5 < i3) {
                    horizontalSnapHelper2.mRecyclerView.scrollBy(i3 - i5, 0);
                } else if (i4 > i2 && i5 < i2) {
                    horizontalSnapHelper2.mRecyclerView.scrollBy(i4 - i2, 0);
                } else if (i4 < i3 && i5 > i3) {
                    horizontalSnapHelper2.mRecyclerView.scrollBy(i4 - i3, 0);
                } else if (i4 >= i2 && i4 <= i3 && i5 >= i2 && i5 <= i3) {
                    horizontalSnapHelper2.mRecyclerView.scrollBy(i6, 0);
                    this.this$0.mSpringHelper.resetDistance();
                }
                HorizontalSnapHelper horizontalSnapHelper3 = this.this$0;
                int i7 = horizontalSnapHelper3.mCurrentPosition;
                if (i7 < i2 || i7 > i3) {
                    horizontalSnapHelper3.mRecyclerView.dispatchNestedPreScroll(i6, 0, null, null, 1);
                    this.this$0.mRecyclerView.invalidate();
                    HorizontalSnapHelper horizontalSnapHelper4 = this.this$0;
                    int i8 = horizontalSnapHelper4.mCurrentPosition;
                    if (i8 < i2) {
                        horizontalSnapHelper4.setSpringHorizontalDistance(horizontalSnapHelper4.mSpringHelper, i8);
                    } else {
                        horizontalSnapHelper4.setSpringHorizontalDistance(horizontalSnapHelper4.mSpringHelper, i8 - i3);
                    }
                }
            } else {
                HorizontalSnapHelper horizontalSnapHelper5 = this.this$0;
                horizontalSnapHelper5.mCurrentPosition = Math.max(horizontalSnapHelper5.mCurrentPosition, i2);
                HorizontalSnapHelper horizontalSnapHelper6 = this.this$0;
                horizontalSnapHelper6.mCurrentPosition = Math.min(horizontalSnapHelper6.mCurrentPosition, i3);
                HorizontalSnapHelper horizontalSnapHelper7 = this.this$0;
                horizontalSnapHelper7.mRecyclerView.scrollBy(horizontalSnapHelper7.mCurrentPosition - horizontalSnapHelper7.mLastPosition, 0);
            }
            HorizontalSnapHelper horizontalSnapHelper8 = this.this$0;
            horizontalSnapHelper8.mLastPosition = horizontalSnapHelper8.mCurrentPosition;
        }
    }

    public int computeFinalDistance(int i, int i2, int i3) {
        if (this.mRecyclerView.getLayoutDirection() == 0) {
            if (i3 == 0) {
                int i4 = (i / i2) * i2;
                if (i % i2 <= i2 / 2) {
                    i2 = 0;
                }
                return i4 + i2;
            }
            if (i3 == 1) {
                return ((i / i2) * i2) + (i2 / 2);
            }
            if (i3 != 2) {
                return -1;
            }
            return (i / i2) * i2;
        }
        int i5 = this.mMax;
        int i6 = i5 - i;
        if (i3 == 0) {
            int i7 = (i6 / i2) * i2;
            if (i6 % i2 <= i2 / 2) {
                i2 = 0;
            }
            return i5 - (i7 + i2);
        }
        if (i3 == 1) {
            return i5 - (((i6 / i2) * i2) + (i2 / 2));
        }
        if (i3 != 2) {
            return -1;
        }
        return i5 - ((i6 / i2) * i2);
    }

    @Override // miuix.recyclerview.widget.SpringSnapHelper
    public void snapFromFling(androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager, int i) {
        this.mFolmeState.getTarget().setVelocity(this.mProperty, i);
        float f = i;
        float fPredictDistance = SpringSnapHelper.predictDistance(f, this.mProperty, this.mFriction, this.mVelocityThreshold);
        int i2 = this.mCurrentPosition;
        int i3 = (int) (i2 + fPredictDistance);
        if ((i2 == this.mMax || i2 == this.mMin) && fPredictDistance == 0.0f) {
            return;
        }
        int iComputeFinalDistance = computeFinalDistance(i3, this.mItemWidth, this.mSnapPreference);
        int i4 = this.mMin;
        this.mOutBounds = iComputeFinalDistance < i4 || iComputeFinalDistance > this.mMax;
        int iMin = Math.min(this.mMax, Math.max(i4, iComputeFinalDistance));
        float frictionTo = SpringSnapHelper.getFrictionTo(f, this.mCurrentPosition, this.mProperty, iMin, this.mVelocityThreshold);
        if (this.mOutBounds) {
            frictionTo = Math.min(frictionTo, this.mFriction);
        }
        final AnimConfig ease = new AnimConfig().setEase(-2, this.mDamping, this.mResponse);
        final AnimState animStateAdd = new AnimState().add(this.mProperty, iMin, 4);
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(-4, frictionTo).addListeners(new TransitionListener() { // from class: miuix.recyclerview.widget.HorizontalSnapHelper.2
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                HorizontalSnapHelper.this.mOutBounds = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                HorizontalSnapHelper.this.mOutBounds = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, HorizontalSnapHelper.this.mProperty);
                if (updateInfoFindBy == null) {
                    return;
                }
                if (updateInfoFindBy.getFloatValue() > HorizontalSnapHelper.this.mMax || updateInfoFindBy.getFloatValue() < HorizontalSnapHelper.this.mMin) {
                    HorizontalSnapHelper.this.mFolmeState.to(animStateAdd, ease);
                }
            }
        });
        if (Math.abs(i) < this.mVelocityThreshold || frictionTo <= 0.0f) {
            this.mFolmeState.to(animStateAdd, ease);
        } else {
            this.mFolmeState.to(animStateAdd, animConfigAddListeners);
        }
    }

    @Override // miuix.recyclerview.widget.SpringSnapHelper
    public void updateConstructData() {
        if (this.mRecyclerView.getLayoutManager() == null || this.mRecyclerView.getAdapter() == null) {
            return;
        }
        if (this.mItemWidth == Integer.MAX_VALUE) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mRecyclerView.getLayoutManager();
            View viewFindViewByPosition = linearLayoutManager.findViewByPosition(linearLayoutManager.findFirstVisibleItemPosition());
            if (viewFindViewByPosition != null) {
                linearLayoutManager.getDecoratedBoundsWithMargins(viewFindViewByPosition, this.mBounds);
            }
            this.mItemWidth = this.mBounds.width();
        }
        int itemCount = this.mRecyclerView.getAdapter().getItemCount();
        this.mMin = 0;
        this.mMax = Math.max((itemCount * this.mItemWidth) - this.mRecyclerView.getWidth(), 0);
        int iComputeHorizontalScrollOffset = this.mRecyclerView.computeHorizontalScrollOffset() + this.mSpringHelper.getHorizontalDistance();
        this.mCurrentPosition = iComputeHorizontalScrollOffset;
        this.mLastPosition = iComputeHorizontalScrollOffset;
    }
}

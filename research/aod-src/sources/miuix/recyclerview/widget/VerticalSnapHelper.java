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
public class VerticalSnapHelper extends SpringSnapHelper {

    /* JADX INFO: renamed from: miuix.recyclerview.widget.VerticalSnapHelper$1, reason: invalid class name */
    class AnonymousClass1 extends IntValueProperty {
        public final /* synthetic */ VerticalSnapHelper this$0;

        @Override // miuix.animation.property.IntValueProperty, miuix.animation.property.IIntValueProperty
        public int getIntValue(Object obj) {
            return this.this$0.mCurrentPosition;
        }

        @Override // miuix.animation.property.IntValueProperty, miuix.animation.property.IIntValueProperty
        public void setIntValue(Object obj, int i) {
            VerticalSnapHelper verticalSnapHelper = this.this$0;
            int i2 = verticalSnapHelper.mMin;
            int i3 = verticalSnapHelper.mMax;
            verticalSnapHelper.mCurrentPosition = i;
            if (verticalSnapHelper.mRecyclerView.getSpringEnabled()) {
                VerticalSnapHelper verticalSnapHelper2 = this.this$0;
                int i4 = verticalSnapHelper2.mCurrentPosition;
                int i5 = verticalSnapHelper2.mLastPosition;
                int i6 = i4 - i5;
                if (i4 < i2 && i5 > i2) {
                    verticalSnapHelper2.mRecyclerView.scrollBy(i2 - i5, 0);
                } else if (i4 > i3 && i5 < i3) {
                    verticalSnapHelper2.mRecyclerView.scrollBy(i3 - i5, 0);
                } else if (i4 > i2 && i5 < i2) {
                    verticalSnapHelper2.mRecyclerView.scrollBy(i4 - i2, 0);
                } else if (i4 < i3 && i5 > i3) {
                    verticalSnapHelper2.mRecyclerView.scrollBy(i4 - i3, 0);
                } else if (i4 >= i2 && i4 <= i3 && i5 >= i2 && i5 <= i3) {
                    verticalSnapHelper2.mRecyclerView.scrollBy(0, i6);
                    this.this$0.mSpringHelper.resetDistance();
                }
                VerticalSnapHelper verticalSnapHelper3 = this.this$0;
                int i7 = verticalSnapHelper3.mCurrentPosition;
                if (i7 < i2 || i7 > i3) {
                    verticalSnapHelper3.mRecyclerView.dispatchNestedPreScroll(0, i6, null, null, 1);
                    this.this$0.mRecyclerView.invalidate();
                    VerticalSnapHelper verticalSnapHelper4 = this.this$0;
                    int i8 = verticalSnapHelper4.mCurrentPosition;
                    if (i8 < i2) {
                        verticalSnapHelper4.setSpringVerticalDistance(verticalSnapHelper4.mSpringHelper, i8);
                    } else {
                        verticalSnapHelper4.setSpringVerticalDistance(verticalSnapHelper4.mSpringHelper, i8 - i3);
                    }
                }
            } else {
                VerticalSnapHelper verticalSnapHelper5 = this.this$0;
                verticalSnapHelper5.mCurrentPosition = Math.max(verticalSnapHelper5.mCurrentPosition, i2);
                VerticalSnapHelper verticalSnapHelper6 = this.this$0;
                verticalSnapHelper6.mCurrentPosition = Math.min(verticalSnapHelper6.mCurrentPosition, i3);
                VerticalSnapHelper verticalSnapHelper7 = this.this$0;
                verticalSnapHelper7.mRecyclerView.scrollBy(0, verticalSnapHelper7.mCurrentPosition - verticalSnapHelper7.mLastPosition);
            }
            VerticalSnapHelper verticalSnapHelper8 = this.this$0;
            verticalSnapHelper8.mLastPosition = verticalSnapHelper8.mCurrentPosition;
        }
    }

    public int computeFinalDistance(int i, int i2, int i3) {
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
        int iComputeFinalDistance = computeFinalDistance(i3, this.mItemHeight, this.mSnapPreference);
        int i4 = this.mMin;
        this.mOutBounds = iComputeFinalDistance < i4 || iComputeFinalDistance > this.mMax;
        int iMin = Math.min(this.mMax, Math.max(i4, iComputeFinalDistance));
        float frictionTo = SpringSnapHelper.getFrictionTo(f, this.mCurrentPosition, this.mProperty, iMin, this.mVelocityThreshold);
        if (this.mOutBounds) {
            frictionTo = Math.min(frictionTo, this.mFriction);
        }
        final AnimConfig ease = new AnimConfig().setEase(-2, this.mDamping, this.mResponse);
        final AnimState animStateAdd = new AnimState().add(this.mProperty, iMin, 4);
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(-4, frictionTo).addListeners(new TransitionListener() { // from class: miuix.recyclerview.widget.VerticalSnapHelper.2
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                VerticalSnapHelper.this.mOutBounds = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                VerticalSnapHelper.this.mOutBounds = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, VerticalSnapHelper.this.mProperty);
                if (updateInfoFindBy == null) {
                    return;
                }
                if (updateInfoFindBy.getFloatValue() > VerticalSnapHelper.this.mMax || updateInfoFindBy.getFloatValue() < VerticalSnapHelper.this.mMin) {
                    VerticalSnapHelper.this.mFolmeState.to(animStateAdd, ease);
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
        if (this.mItemHeight == Integer.MAX_VALUE) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mRecyclerView.getLayoutManager();
            View viewFindViewByPosition = linearLayoutManager.findViewByPosition(linearLayoutManager.findFirstVisibleItemPosition());
            if (viewFindViewByPosition != null) {
                linearLayoutManager.getDecoratedBoundsWithMargins(viewFindViewByPosition, this.mBounds);
            }
            this.mItemHeight = this.mBounds.height();
        }
        int itemCount = this.mRecyclerView.getAdapter().getItemCount();
        this.mMin = 0;
        this.mMax = Math.max((itemCount * this.mItemHeight) - this.mRecyclerView.getHeight(), 0);
        int iComputeVerticalScrollOffset = this.mRecyclerView.computeVerticalScrollOffset() + this.mSpringHelper.getVerticalDistance();
        this.mCurrentPosition = iComputeVerticalScrollOffset;
        this.mLastPosition = iComputeVerticalScrollOffset;
    }
}

package miuix.recyclerview.widget;

import android.graphics.Rect;
import miuix.animation.controller.FolmeState;
import miuix.animation.property.FloatProperty;
import miuix.spring.view.SpringHelper;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SpringSnapHelper extends androidx.recyclerview.widget.RecyclerView.OnFlingListener {
    public Rect mBounds;
    public int mCurrentPosition;
    public float mDamping;
    public FolmeState mFolmeState;
    public float mFriction;
    public int mItemHeight;
    public int mItemWidth;
    public int mLastPosition;
    public int mMax;
    public int mMin;
    public boolean mOutBounds;
    public FloatProperty mProperty;
    public RecyclerView mRecyclerView;
    public float mResponse;
    public int mSnapPreference;
    public SpringHelper mSpringHelper;
    public float mVelocityThreshold;

    /* JADX INFO: renamed from: miuix.recyclerview.widget.SpringSnapHelper$1, reason: invalid class name */
    class AnonymousClass1 extends androidx.recyclerview.widget.RecyclerView.OnScrollListener {
        public boolean mScrolled;
        public final /* synthetic */ SpringSnapHelper this$0;

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(androidx.recyclerview.widget.RecyclerView recyclerView, int i) {
            if (this.mScrolled) {
                this.this$0.updateConstructData();
                this.this$0.snapFromFling(recyclerView.getLayoutManager(), 0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
            if (i == 0 && i2 == 0) {
                return;
            }
            this.mScrolled = true;
        }
    }

    public static float getFrictionTo(float f, float f2, FloatProperty floatProperty, float f3, float f4) {
        float minVisibleChange = floatProperty.getMinVisibleChange();
        if (f * minVisibleChange < 0.0f) {
            minVisibleChange = -minVisibleChange;
        }
        float f5 = f3 - f2;
        if (Math.abs(f) < f4 || f * f5 <= 0.0f) {
            return -1.0f;
        }
        return (float) (((double) ((-(f - minVisibleChange)) / f5)) / (-4.2d));
    }

    public static float getPredict(float f, float f2) {
        return (-f) / (f2 * (-4.2f));
    }

    public static float predictDistance(float f, FloatProperty floatProperty, float f2, float f3) {
        float minVisibleChange = floatProperty.getMinVisibleChange();
        if (f * minVisibleChange < 0.0f) {
            minVisibleChange = -minVisibleChange;
        }
        float predict = getPredict(f, f2) - getPredict(minVisibleChange, f2);
        if (Math.abs(f) < f3) {
            return 0.0f;
        }
        return predict;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnFlingListener
    public boolean onFling(int i, int i2) {
        if (this.mRecyclerView.getLayoutManager() == null || this.mRecyclerView.getAdapter() == null) {
            return false;
        }
        updateConstructData();
        if (this instanceof HorizontalSnapHelper) {
            snapFromFling(this.mRecyclerView.getLayoutManager(), i);
            return true;
        }
        if (this instanceof VerticalSnapHelper) {
            snapFromFling(this.mRecyclerView.getLayoutManager(), i2);
        }
        return true;
    }

    public void setSpringHorizontalDistance(SpringHelper springHelper, int i) {
        if (springHelper == null) {
            return;
        }
        springHelper.setHorizontalDistance(i);
    }

    public void setSpringVerticalDistance(SpringHelper springHelper, int i) {
        if (springHelper == null) {
            return;
        }
        springHelper.setVerticalDistance(i);
    }

    public abstract void snapFromFling(androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager, int i);

    public abstract void updateConstructData();
}

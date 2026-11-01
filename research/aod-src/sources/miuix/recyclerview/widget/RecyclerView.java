package miuix.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.R;
import androidx.recyclerview.widget.SpringRecyclerView;
import miuix.recyclerview.tool.GetSpeedForDynamicRefreshRate;

/* JADX INFO: loaded from: classes2.dex */
public class RecyclerView extends SpringRecyclerView {
    private static final int MIN_FLING_VELOCITY = 300;
    private final GetSpeedForDynamicRefreshRate mGetSpeedForDynamicRefreshRate;

    public RecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.recyclerViewStyle);
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setItemAnimator(new MiuiDefaultItemAnimator());
        this.mGetSpeedForDynamicRefreshRate = new GetSpeedForDynamicRefreshRate(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        GetSpeedForDynamicRefreshRate getSpeedForDynamicRefreshRate = this.mGetSpeedForDynamicRefreshRate;
        if (getSpeedForDynamicRefreshRate != null) {
            getSpeedForDynamicRefreshRate.touchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public boolean fling(int i, int i2) {
        if (Math.abs(i) < MIN_FLING_VELOCITY) {
            i = 0;
        }
        if (Math.abs(i2) < MIN_FLING_VELOCITY) {
            i2 = 0;
        }
        if (i == 0 && i2 == 0) {
            return false;
        }
        return super.fling(i, i2);
    }

    @Override // android.view.View
    public void onFocusChanged(boolean z, int i, @Nullable Rect rect) {
        super.onFocusChanged(z, i, rect);
        GetSpeedForDynamicRefreshRate getSpeedForDynamicRefreshRate = this.mGetSpeedForDynamicRefreshRate;
        if (getSpeedForDynamicRefreshRate != null) {
            getSpeedForDynamicRefreshRate.onFocusChange(z);
        }
    }

    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RecyclerView
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
        GetSpeedForDynamicRefreshRate getSpeedForDynamicRefreshRate = this.mGetSpeedForDynamicRefreshRate;
        if (getSpeedForDynamicRefreshRate != null) {
            getSpeedForDynamicRefreshRate.scrollState(this, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void onScrolled(int i, int i2) {
        GetSpeedForDynamicRefreshRate getSpeedForDynamicRefreshRate = this.mGetSpeedForDynamicRefreshRate;
        if (getSpeedForDynamicRefreshRate != null) {
            getSpeedForDynamicRefreshRate.calculateSpeed(i, i2, getDragFlingVelocityX(), getDragFlingVelocityY());
        }
        super.onScrolled(i, i2);
    }
}

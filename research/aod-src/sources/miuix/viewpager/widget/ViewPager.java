package miuix.viewpager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.OriginalViewPager;

/* JADX INFO: loaded from: classes3.dex */
public class ViewPager extends OriginalViewPager {
    public boolean mDragEnabled;

    public ViewPager(@NonNull Context context) {
        super(context);
        this.mDragEnabled = true;
    }

    public ViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDragEnabled = true;
    }

    @Override // androidx.viewpager.widget.OriginalViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mDragEnabled) {
            return false;
        }
        try {
            return super.onInterceptTouchEvent(motionEvent);
        } catch (IllegalArgumentException e) {
            Log.e("ViewPager", "Catch IllegalArgumentException:" + e);
            return false;
        }
    }

    @Override // androidx.viewpager.widget.OriginalViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mDragEnabled) {
            return false;
        }
        try {
            return super.onTouchEvent(motionEvent);
        } catch (IllegalArgumentException e) {
            Log.e("ViewPager", "Catch IllegalArgumentException:" + e);
            return false;
        }
    }

    public void setDraggable(boolean z) {
        this.mDragEnabled = z;
    }
}

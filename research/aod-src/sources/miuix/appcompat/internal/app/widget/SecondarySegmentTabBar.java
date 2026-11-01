package miuix.appcompat.internal.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBar;
import com.miui.aod.category.TextStyleCategoryInfo;
import miuix.appcompat.R;
import miuix.core.util.EnvStateManager;
import miuix.miuixbasewidget.widget.FilterSortView;
import miuix.os.DeviceHelper;

/* JADX INFO: loaded from: classes2.dex */
public class SecondarySegmentTabBar extends FilterSortView implements SecondaryTabBar {
    public final int mDeviceType;
    public final int mLayoutConfig;

    public static class SecondarySegmentTabView extends FilterSortView.TabView {
        public ActionBar.Tab mTab;

        public SecondarySegmentTabView(Context context) {
            this(context, null);
        }

        public SecondarySegmentTabView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.segmentTabViewStyle);
        }

        public SecondarySegmentTabView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        public ActionBar.Tab getTab() {
            return this.mTab;
        }
    }

    public SecondarySegmentTabBar(Context context) {
        this(context, null);
    }

    public SecondarySegmentTabBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.segmentTabBarStyle);
    }

    public SecondarySegmentTabBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SecondarySegmentTabBar, i, 0);
        this.mLayoutConfig = typedArrayObtainStyledAttributes.getInt(R.styleable.SecondarySegmentTabBar_segmentTabBarLayoutConfig, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.mDeviceType = DeviceHelper.detectType(context);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void animateToTab(int i) {
        setFilteredTab(i);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public ViewGroup asViewGroup() {
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0053  */
    /* JADX WARN: Code duplicated, block: B:19:0x0063  */
    /* JADX WARN: Code duplicated, block: B:21:0x0069 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:22:0x006b  */
    /* JADX WARN: Code duplicated, block: B:23:0x006e  */
    /* JADX WARN: Code duplicated, block: B:26:0x0074 A[ADDED_TO_REGION] */
    @Override // miuix.miuixbasewidget.widget.FilterSortView, androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int tabCount;
        int i3;
        int i4;
        int i5;
        int size = View.MeasureSpec.getSize(i);
        float f = getContext().getResources().getDisplayMetrics().density;
        int i6 = this.mLayoutConfig;
        if (i6 == 0) {
            int i7 = (int) ((size * 1.0f) / f);
            int i8 = (int) ((EnvStateManager.getWindowSize(getContext()).x * 1.0f) / f);
            if (this.mDeviceType == 2 && i7 > 410 && i8 > 600) {
                tabCount = getTabCount();
                int paddingLeft = getPaddingLeft() + getPaddingRight();
                i3 = size - paddingLeft;
                if (tabCount <= 2) {
                    i4 = tabCount * 220;
                } else if (tabCount == 3) {
                    i4 = tabCount * 180;
                } else {
                    i4 = tabCount * TextStyleCategoryInfo.MIN_FONT_WEIGHT;
                }
                i5 = ((int) (i4 * f)) + paddingLeft;
                if (i3 >= i5 && tabCount > 0) {
                    i = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
                }
            }
        } else if (i6 == 1) {
            int i9 = (int) ((EnvStateManager.getWindowSize(getContext()).x * 1.0f) / f);
            if (this.mDeviceType == 2 && i9 > 600) {
                tabCount = getTabCount();
                int paddingLeft2 = getPaddingLeft() + getPaddingRight();
                i3 = size - paddingLeft2;
                if (tabCount <= 2) {
                    i4 = tabCount * 220;
                } else if (tabCount == 3) {
                    i4 = tabCount * 180;
                } else {
                    i4 = tabCount * TextStyleCategoryInfo.MIN_FONT_WEIGHT;
                }
                i5 = ((int) (i4 * f)) + paddingLeft2;
                if (i3 >= i5) {
                    i = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
                }
            }
        } else if (i6 == 3) {
            tabCount = getTabCount();
            int paddingLeft3 = getPaddingLeft() + getPaddingRight();
            i3 = size - paddingLeft3;
            if (tabCount <= 2) {
                i4 = tabCount * 220;
            } else if (tabCount == 3) {
                i4 = tabCount * 180;
            } else {
                i4 = tabCount * TextStyleCategoryInfo.MIN_FONT_WEIGHT;
            }
            i5 = ((int) (i4 * f)) + paddingLeft3;
            if (i3 >= i5) {
                i = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageSelected(int i) {
        setFilteredTab(i);
    }

    public void setBadgeVisibility(int i, boolean z) {
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void setParentBlurEnabled(boolean z) {
    }

    public void setTabBadgeDisappearOnClick(int i, boolean z) {
    }

    public void setTabIconWithPosition(int i, int i2, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void setTabSelected(int i) {
        setFilteredTab(i);
    }

    public void setTextAppearance(int i, int i2) {
    }

    public void setTextAppearance(int i, int i2, int i3) {
    }
}
